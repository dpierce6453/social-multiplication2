package microservices.book.multiplication.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;
@Entity
public class User {
    @Id
    @GeneratedValue
    @Column(name = "USER_ID")
    private long id;
    public final String alias;

    public User(String alias) {
        this.alias = alias;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        if (alias != null) {
            return alias.equals(user.alias);
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(alias);
    }

    protected User() {
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
