package microservices.book.multiplication.domain;

import jakarta.persistence.*;
import org.springframework.lang.NonNull;

import java.util.Objects;
@Entity
public class DBUser {
    @Id
    @GeneratedValue
    @Column(name = "DBUSER_ID", updatable = false)
    private Long id;
    public final String alias;

    public DBUser(String alias) {
        this.alias = alias;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DBUser DBUser = (DBUser) o;
        return Objects.equals(id, DBUser.id) && Objects.equals(getAlias(), DBUser.getAlias());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, getAlias());
    }

    protected DBUser() {
        alias = null;
    }

    public String getAlias() {
        return alias;
    }

    @Override
    public String toString() {
        return "User{" +
                "alias='" + alias + '\'' +
                '}';
    }
}
