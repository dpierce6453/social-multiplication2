package microservices.book.multiplication.domain;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@RequiredArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
@Entity
public class Multiplication {

    @Id
    @GeneratedValue
    @Column(name = "MULTIPLICATION_ID")
    private Long id;

    private final int factorA;

    private final int factorB;

//    private final int hashCodeValue;

//    public Multiplication(int factorA, int factorB)
//    {
//        this.factorA = factorA;
//        this.factorB = factorB;
////        hashCodeValue = hashCode();
//    }
    public Multiplication() {
        this(0,0);
    }

//    public int getHashCodeValue() {
//        return hashCodeValue;
//    }

}
