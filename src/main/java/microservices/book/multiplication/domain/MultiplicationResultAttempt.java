package microservices.book.multiplication.domain;

import jakarta.persistence.*;
import java.util.Objects;
@Entity
public class MultiplicationResultAttempt {
    @Id
    @GeneratedValue
    private Long id;

    public Long getId() {
        return id;
    }

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "PLAYER_ID")
    private final Player player;
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "MULTIPLICATION_ID")
    private final Multiplication multiplication;
    private final int resultAttempt;
    private final boolean correct;

    public boolean isCorrect() {
        return correct;
    }

    public Player getPlayer() {
        return player;
    }

    public Multiplication getMultiplication() {
        return multiplication;
    }

    public int getResultAttempt() {
        return resultAttempt;
    }

    public MultiplicationResultAttempt(Player player, Multiplication multiplication, int resultAttempt, boolean correct) {
        this.player = player;
        this.multiplication = multiplication;
        this.resultAttempt = resultAttempt;
        this.correct = correct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MultiplicationResultAttempt that = (MultiplicationResultAttempt) o;
        return getResultAttempt() == that.getResultAttempt() && getPlayer().equals(that.getPlayer()) && getMultiplication().equals(that.getMultiplication());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPlayer(), getMultiplication(), getResultAttempt());
    }

    public MultiplicationResultAttempt() {
        player = null;
        multiplication = null;
        resultAttempt = -1;
        correct = false;
    }
}
