package microservices.book.multiplication.service;

import microservices.book.multiplication.domain.Player;
import microservices.book.multiplication.domain.Multiplication;
import microservices.book.multiplication.domain.MultiplicationResultAttempt;
import microservices.book.multiplication.event.EventDispatcher;
import microservices.book.multiplication.event.MultiplicationSolvedEvent;
import microservices.book.multiplication.repository.MultiplicationRepository;
import microservices.book.multiplication.repository.MultiplicationResultAttemptRepository;
import microservices.book.multiplication.repository.PlayerRepository;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

@Service
public class MultiplicationServiceImpl implements MultiplicationService {
    private final RandomGeneratorService randomGeneratorService;
    private final MultiplicationResultAttemptRepository attemptRepository;
    private final MultiplicationRepository multiplicationRepository;
    private final PlayerRepository playerRepository;
    private final EventDispatcher eventDispatcher;

    @Autowired public MultiplicationServiceImpl(RandomGeneratorService randomGeneratorService,
                                                final MultiplicationResultAttemptRepository attemptRepository,
                                                final MultiplicationRepository multiplicationRepository,
                                                final PlayerRepository playerRepository,
                                                final EventDispatcher eventDispatcher)
    {
        this.randomGeneratorService = randomGeneratorService;
        this.attemptRepository = attemptRepository;
        this.multiplicationRepository = multiplicationRepository;
        this.playerRepository = playerRepository;
        this.eventDispatcher = eventDispatcher;
    }



    @Override
    public Multiplication createRandomMultiplication() {
        int FactorA = randomGeneratorService.generateRandomFactor();
        int FactorB = randomGeneratorService.generateRandomFactor();
        Multiplication ret = new Multiplication(FactorA, FactorB);
        // Has this multiplication been seen before?
        Optional<Multiplication> match = seeIfMultiplicationExists(ret);
        return match.orElse(ret);
    }

    @SuppressWarnings("ReassignedVariable")
    private Optional<Multiplication> seeIfMultiplicationExists(Multiplication multiplication) {
        Optional<Multiplication> match = Optional.empty();
        List<Multiplication> listOfMultiplications = multiplicationRepository.findByFactorA(multiplication.getFactorA());
        for(Multiplication mult: listOfMultiplications) {
            if(mult.getFactorB() == multiplication.getFactorB()) {
                match = Optional.of(mult);
                break;
            }
        }
        return match;
    }

    @Transactional
    @Override
    public boolean checkAttempt(final MultiplicationResultAttempt attempt) {
        Optional<Player> player = playerRepository.findByAlias(attempt.getPlayer().getAlias());
        Player newPlayer = new Player(attempt.getPlayer().getAlias());
        Optional<Multiplication> multiplication = seeIfMultiplicationExists(attempt.getMultiplication());
        Multiplication newMultiplication = new Multiplication(
                attempt.getMultiplication().getFactorA(),
                attempt.getMultiplication().getFactorB());


        // Avoids 'hack' attempts
        Assert.isTrue(!attempt.isCorrect(), "You can't send an attempt marked as correct!!");

        // Checks if it's correct
        boolean isCorrect = attempt.getResultAttempt() ==
                attempt.getMultiplication().getFactorA() *
                attempt.getMultiplication().getFactorB();
        // Creates a copy, now setting the 'correct' field accordingly
        MultiplicationResultAttempt checkedAttempt = new MultiplicationResultAttempt(
                player.orElse(newPlayer),
                multiplication.orElse(newMultiplication),
                attempt.getResultAttempt(),
                isCorrect);


        //Stores the attempt
        attemptRepository.save(checkedAttempt);

        eventDispatcher.send(new MultiplicationSolvedEvent(checkedAttempt.getId(),
                checkedAttempt.getPlayer().getId(),
                checkedAttempt.isCorrect())
        );

        return isCorrect;

    }

    @Override
    public List<MultiplicationResultAttempt> getStatsForUser(String playerAlias) {
        return attemptRepository.findTop5ByPlayerAliasOrderByIdDesc(playerAlias);
    }

    @Override
    public MultiplicationResultAttempt getResultById(final Long resultId) {
        Optional<MultiplicationResultAttempt> resultAttempt = attemptRepository.findById(resultId);
        return resultAttempt.orElseGet(MultiplicationResultAttempt::new);
    }
}
