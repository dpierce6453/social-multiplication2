package microservices.book.multiplication.repository;

import microservices.book.multiplication.domain.Multiplication;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MultiplicationRepository extends CrudRepository<Multiplication, Long> {
     List<Multiplication> findByFactorA(int FactorA);
}
