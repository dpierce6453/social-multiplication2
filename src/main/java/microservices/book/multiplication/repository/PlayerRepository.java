package microservices.book.multiplication.repository;

import microservices.book.multiplication.domain.Player;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PlayerRepository extends CrudRepository<Player, Long> {
    Optional<Player> findByAlias(final String alias);
}
