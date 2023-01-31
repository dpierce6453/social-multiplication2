package microservices.book.multiplication.domain;

import java.util.Objects;

public class MultiplicationResultAttempt {

    private final User user;
    private final Multiplication multiplication;
    private final int resultAttempt;
    private final boolean correct;

    public boolean isCorrect() {
        return correct;
    }

    public User getUser() {
        return user;
    }

    public Multiplication getMultiplication() {
        return multiplication;
    }

    public int getResultAttempt() {
        return resultAttempt;
    }

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
        MultiplicationResultAttempt that = (MultiplicationResultAttempt) o;
        return getResultAttempt() == that.getResultAttempt() && getUser().equals(that.getUser()) && getMultiplication().equals(that.getMultiplication());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUser(), getMultiplication(), getResultAttempt());
    }

    public MultiplicationResultAttempt() {
        user = null;
        multiplication = null;
        resultAttempt = -1;
        correct = false;
    }
}
