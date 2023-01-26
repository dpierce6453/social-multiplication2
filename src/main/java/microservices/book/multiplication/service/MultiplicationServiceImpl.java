package microservices.book.multiplication.service;

import microservices.book.multiplication.domain.Multiplication;
import microservices.book.multiplication.domain.MultiplicationResultAttempt;
import microservices.book.multiplication.domain.User;
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

    @Autowired public MultiplicationServiceImpl(final RandomGeneratorService randomGeneratorService,
        final MultiplicationResultAttemptRepository attemptRepository,
        final UserRepository userRepository) {
            this.randomGeneratorService = randomGeneratorService;
            this.attemptRepository = attemptRepository;
            this.userRepository = userRepository;
    }

    @Override
    public Multiplication createRandomMultiplication() {
        int FactorA = randomGeneratorService.generateRandomFactor();
        int FactorB = randomGeneratorService.generateRandomFactor();
        return new Multiplication(FactorA, FactorB);
    }

    @Transactional
    @Override
    public boolean checkAttempt(final MultiplicationResultAttempt attempt) {
        Optional<User> user = userRepository.findByAlias(attempt.getUser().getAlias());

        Assert.isTrue(!attempt.isCorrect(), "You can't send an attempt marked as correct");

        boolean isCorrect =  attempt.getResultAttempt() ==
            attempt.getMultiplication().getFactorA() * attempt.getMultiplication().getFactorB();


        MultiplicationResultAttempt checkedAttempt = new MultiplicationResultAttempt(
            user.orElse(attempt.getUser()),
            attempt.getMultiplication(),
            attempt.getResultAttempt(),
            isCorrect);

        attemptRepository.save(checkedAttempt);

        return isCorrect;
    }
}
