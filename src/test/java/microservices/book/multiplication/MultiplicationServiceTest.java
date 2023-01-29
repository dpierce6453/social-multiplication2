package microservices.book.multiplication;

import microservices.book.multiplication.service.MultiplicationService;
import microservices.book.multiplication.service.RandomGeneratorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@ComponentScan(basePackages = {
        "microservices.book.multiplication.domain",
        "microservices.book.multiplication.service",
        "microservices.book.multiplication.repository",
        "microservices.book.multiplication.controller"
})
@EnableJpaRepositories("microservices.book.multiplication.domain")
@EntityScan("microservices.book.multiplication.domain")
class MultiplicationServiceTest {
    @MockBean
    private RandomGeneratorService randomGeneratorService;

    @Autowired
    MultiplicationService multiplicationService;

    @Test
    public void autoWireTest() {
        assertThat(multiplicationService != null);
    }
}