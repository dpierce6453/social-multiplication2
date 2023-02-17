package microservices.book.multiplication.repository;

import microservices.book.multiplication.domain.DBUser;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<DBUser, Long>
{
    Optional<DBUser> findByAlias(final String alias);
}
