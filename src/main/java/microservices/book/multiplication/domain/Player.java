package microservices.book.multiplication.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.util.Objects;
@Entity
public class Player {

        @Id
        @GeneratedValue
        @Column(name = "PLAYER_ID")
        private Long id;

        public final String alias;
        public Player(String alias) {
            this.alias = alias;
        }
        public Player() {
            alias = null;
        }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return getAlias().equals(player.getAlias());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAlias());
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
