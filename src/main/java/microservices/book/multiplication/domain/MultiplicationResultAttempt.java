package microservices.book.multiplication.domain;

import jakarta.persistence.*;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@Getter
@ToString
@EqualsAndHashCode

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

    public MultiplicationResultAttempt() {
        player = null;
        multiplication = null;
        resultAttempt = -1;
        correct = false;
    }
}
