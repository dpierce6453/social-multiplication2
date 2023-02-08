package microservices.book.multiplication;

import microservices.book.multiplication.domain.Multiplication;
import microservices.book.multiplication.repository.MultiplicationRepository;
import microservices.book.multiplication.service.MultiplicationService;
import microservices.book.multiplication.service.RandomGeneratorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@SpringBootTest
class SocialMultiplicationApplicationTests {

	@MockBean
	private RandomGeneratorService randomGeneratorService;

	@MockBean
	private MultiplicationRepository multiplicationRepository;

	@Autowired
	private MultiplicationService multiplicationService;


	@Test
	public void autowireTest() {
		assertThat(multiplicationService != null);
	}
	@Test
	public void createRandomMultiplicationTest() {
		given(randomGeneratorService.generateRandomFactor()).willReturn(50, 30);
		given(multiplicationRepository.findByHashCodeValue(any())).willReturn(Optional.empty());

		Multiplication multiplication = multiplicationService.createRandomMultiplication();

		assertThat(multiplication.getFactorA()).isEqualTo(50);
		assertThat(multiplication.getFactorB()).isEqualTo(30);
	}

}
