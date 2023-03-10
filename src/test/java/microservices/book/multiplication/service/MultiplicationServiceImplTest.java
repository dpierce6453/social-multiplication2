package microservices.book.multiplication.service;

import microservices.book.multiplication.domain.Player;
import microservices.book.multiplication.domain.Multiplication;
import microservices.book.multiplication.domain.MultiplicationResultAttempt;
import microservices.book.multiplication.event.EventDispatcher;
import microservices.book.multiplication.event.MultiplicationSolvedEvent;
import microservices.book.multiplication.repository.MultiplicationRepository;
import microservices.book.multiplication.repository.MultiplicationResultAttemptRepository;
import microservices.book.multiplication.repository.PlayerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.collections.Lists;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

class MultiplicationServiceImplTest {

    private MultiplicationServiceImpl multiplicationServiceImpl;

    @Mock
    private RandomGeneratorService randomGeneratorService;
    @Mock
    private MultiplicationResultAttemptRepository attemptRepository;
    @Mock
    private MultiplicationRepository multiplicationRepository;
    @Mock
    private PlayerRepository playerRepository;
    @Mock
    private EventDispatcher eventDispatcher;



    @BeforeEach
    public void setUp() { // With this call to initMocks we tell Mockito to process the annotations
        MockitoAnnotations.initMocks(this);
        multiplicationServiceImpl = new MultiplicationServiceImpl(randomGeneratorService, attemptRepository, multiplicationRepository, playerRepository, eventDispatcher);
    }

    @Test
    public void createRandomMultiplicationTest() {
        // given (our mocked Random Generator service will return first 50, then 30)
        given(randomGeneratorService.generateRandomFactor()).willReturn(50, 30);
        // when
        Multiplication multiplication = multiplicationServiceImpl.createRandomMultiplication();
        // assert
        assertThat(multiplication.getFactorA()).isEqualTo(50);
        assertThat(multiplication.getFactorB()).isEqualTo(30);

    }

    @Test
    public void checkCorrectAttemptTest() {
        // given
        Multiplication multiplication = new Multiplication(50, 60);
        Player player = new Player("john_doe");

        MultiplicationResultAttempt attempt = new MultiplicationResultAttempt(player, multiplication, 3000, false);
        MultiplicationResultAttempt verifiedAttempt = new MultiplicationResultAttempt(player, multiplication, 3000, true);
        given(playerRepository.findByAlias("john_doe")).willReturn(Optional.empty());
        MultiplicationSolvedEvent event = new MultiplicationSolvedEvent(attempt.getId(),
                attempt.getPlayer().getId(), true);

        // when
        boolean attemptResult = multiplicationServiceImpl.checkAttempt(attempt);
        // assert
        assertThat(attemptResult).isTrue();
        verify(attemptRepository).save(verifiedAttempt);
        verify(eventDispatcher).send(event);

    }

    @Test public void checkWrongAttemptTest() {
        // given
        Multiplication multiplication = new Multiplication(50, 60);
        Player player = new Player("john_doe");
        MultiplicationResultAttempt attempt = new MultiplicationResultAttempt(player, multiplication, 3010, false);
        given(playerRepository.findByAlias("john_doe")).willReturn(Optional.empty());

        MultiplicationSolvedEvent event = new MultiplicationSolvedEvent(attempt.getId(),
                attempt.getPlayer().getId(), false);
        // when
        boolean attemptResult = multiplicationServiceImpl.checkAttempt(attempt);
        // assert
        assertThat(attemptResult).isFalse();
        verify(attemptRepository).save(attempt);
        verify(eventDispatcher).send(event);

    }

    @Test public void retrieveStatsTest() {
        // given
        Multiplication multiplication = new Multiplication(50, 60);
        Player player = new Player("john_doe");
        MultiplicationResultAttempt attempt1 = new MultiplicationResultAttempt( player, multiplication, 3010, false);
        MultiplicationResultAttempt attempt2 = new MultiplicationResultAttempt( player, multiplication, 3051, false);
        List<MultiplicationResultAttempt> latestAttempts = Lists.newArrayList(attempt1, attempt2);

        given(playerRepository.findByAlias("john_doe")).willReturn(Optional.empty());
        given(attemptRepository.findTop5ByPlayerAliasOrderByIdDesc("john_doe")) .willReturn(latestAttempts);

        // when
        List<MultiplicationResultAttempt> latestAttemptsResult = multiplicationServiceImpl.getStatsForUser("john_doe");
        // then
        assertThat(latestAttemptsResult).isEqualTo(latestAttempts);
    }
}