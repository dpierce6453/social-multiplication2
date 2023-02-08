package microservices.book.multiplication.service;

import microservices.book.multiplication.domain.Multiplication;
import microservices.book.multiplication.domain.MultiplicationResultAttempt;
import microservices.book.multiplication.domain.User;
import microservices.book.multiplication.repository.MultiplicationRepository;
import microservices.book.multiplication.repository.MultiplicationResultAttemptRepository;
import microservices.book.multiplication.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Optional;

@Service
public class MultiplicationServiceImpl implements MultiplicationService {
    private final RandomGeneratorService randomGeneratorService;
    private final MultiplicationResultAttemptRepository attemptRepository;
    private final UserRepository userRepository;
    private final MultiplicationRepository multiplicationRepository;

    @Autowired public MultiplicationServiceImpl(RandomGeneratorService randomGeneratorService,
                                                final MultiplicationResultAttemptRepository attemptRepository,
                                                final UserRepository userRepository,
                                                MultiplicationRepository multiplicationRepository)
    {
        this.randomGeneratorService = randomGeneratorService;
        this.attemptRepository = attemptRepository;
        this.userRepository = userRepository;
        this.multiplicationRepository = multiplicationRepository;
    }


    @Override
    public Multiplication createRandomMultiplication() {
        int FactorA = randomGeneratorService.generateRandomFactor();
        int FactorB = randomGeneratorService.generateRandomFactor();
        Multiplication ret = new Multiplication(FactorA, FactorB);
        // Has this multiplication been seen before?
        Optional<Multiplication> multiplication = multiplicationRepository.findByHashCodeValue(ret);
        return multiplication.orElse(ret);
    }
    @Transactional
    @Override
    public boolean checkAttempt(final MultiplicationResultAttempt attempt) {
        Optional<User> user = userRepository.findByAlias(attempt.getUser().getAlias());

        // Avoids 'hack' attempts
        Assert.isTrue(!attempt.isCorrect(), "You can't send an attempt marked as correct!!");

        // Checks if it's correct
        boolean isCorrect = attempt.getResultAttempt() ==
                attempt.getMultiplication().getFactorA() *
                attempt.getMultiplication().getFactorB();
        // Creates a copy, now setting the 'correct' field accordingly
        MultiplicationResultAttempt checkedAttempt = new MultiplicationResultAttempt(
                user.orElse(attempt.getUser()),
                attempt.getMultiplication(),
                attempt.getResultAttempt(),
                isCorrect);

        //Stores the attempt
        attemptRepository.save(checkedAttempt);

        return isCorrect;

    }
}
