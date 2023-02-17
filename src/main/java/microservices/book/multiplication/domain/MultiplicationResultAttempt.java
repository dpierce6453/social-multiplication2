package microservices.book.multiplication.domain;

import jakarta.persistence.*;
import java.util.Objects;
@Entity
public class MultiplicationResultAttempt {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "DBUSER_ID", nullable = false)
    private final DBUser dbUser;
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "MULTIPLICATION_ID", nullable = false)
    private final Multiplication multiplication;
    private final int resultAttempt;
    private final boolean correct;

    public boolean isCorrect() {
        return correct;
    }

    public DBUser getDbUser() {
        return dbUser;
    }

    public Multiplication getMultiplication() {
        return multiplication;
    }

    public int getResultAttempt() {
        return resultAttempt;
    }

    public MultiplicationResultAttempt(DBUser user, Multiplication multiplication, int resultAttempt, boolean correct) {
        this.dbUser = user;
        this.multiplication = multiplication;
        this.resultAttempt = resultAttempt;
        this.correct = correct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MultiplicationResultAttempt that = (MultiplicationResultAttempt) o;
        return getResultAttempt() == that.getResultAttempt() && getDbUser().equals(that.getDbUser()) && getMultiplication().equals(that.getMultiplication());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDbUser(), getMultiplication(), getResultAttempt());
    }

    public MultiplicationResultAttempt() {
        dbUser = null;
        multiplication = null;
        resultAttempt = -1;
        correct = false;
    }
}
