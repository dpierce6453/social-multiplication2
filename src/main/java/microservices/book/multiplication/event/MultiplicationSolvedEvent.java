package microservices.book.multiplication.event;

import java.io.Serializable;
import java.util.Objects;

public class MultiplicationSolvedEvent implements Serializable {
    private final Long multiplicationResultAttemptId;
    private final Long playerId;
    private final boolean correct;

    public MultiplicationSolvedEvent(Long multiplicationResultAttemptId, Long playerId, boolean correct) {
        this.multiplicationResultAttemptId = multiplicationResultAttemptId;
        this.playerId = playerId;
        this.correct = correct;
    }

    public Long getMultiplicationResultAttemptId() {
        return multiplicationResultAttemptId;
    }

    public Long getPlayerId() {
        return playerId;
    }

    public boolean isCorrect() {
        return correct;
    }

    @Override
    public String toString() {
        return "MultiplicationSolvedEvent{" +
                "multiplicationResultAttemptId=" + multiplicationResultAttemptId +
                ", playerId=" + playerId +
                ", correct=" + correct +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MultiplicationSolvedEvent that = (MultiplicationSolvedEvent) o;
        return correct == that.correct && Objects.equals(multiplicationResultAttemptId, that.multiplicationResultAttemptId) && Objects.equals(playerId, that.playerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(multiplicationResultAttemptId, playerId, correct);
    }
}
