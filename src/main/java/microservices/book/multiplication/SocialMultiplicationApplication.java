package microservices.book.multiplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = {"microservices.book.multiplication.domain"})
public class SocialMultiplicationApplication {

	public static void main(String[] args) {
		SpringApplication.run(SocialMultiplicationApplication.class, args);
	}

}
