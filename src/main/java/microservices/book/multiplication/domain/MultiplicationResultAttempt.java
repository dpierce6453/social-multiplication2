package microservices.book.multiplication.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class MultiplicationResultAttempt {
    @Id
    @GeneratedValue
    private Long id;

   @ManyToOne(cascade = CascadeType.PERSIST)
   @JoinColumn(name = "USER_ID")
    private final User user;

   @ManyToOne(cascade = CascadeType.PERSIST)
   @JoinColumn(name = "MULTIPLICATION_ID")
    private final Multiplication multiplication;

    private final int resultAttempt;
    private final boolean correct;



    public MultiplicationResultAttempt(User user, Multiplication multiplication, int resultAttempt, boolean correct) {
        this.user = user;
        this.multiplication = multiplication;
        this.resultAttempt = resultAttempt;
        this.correct = correct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MultiplicationResultAttempt attempt = (MultiplicationResultAttempt) o;
        return getResultAttempt() == attempt.getResultAttempt() && isCorrect() == attempt.isCorrect() && getUser().equals(attempt.getUser()) && getMultiplication().equals(attempt.getMultiplication());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUser(), getMultiplication(), getResultAttempt(), isCorrect());
    }

    public MultiplicationResultAttempt() {
        user = null;
        multiplication = null;
        resultAttempt = -1;
        correct = false;
    }

    public User getUser() {
        return user;
    }

    public boolean isCorrect() {
        return correct;
    }

    public Multiplication getMultiplication() {
        return multiplication;
    }

    public int getResultAttempt() {
        return resultAttempt;
    }
}
