package microservices.book.multiplication.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.util.Objects;
@Entity
public class Multiplication {

    @Id
    @GeneratedValue
    @Column(name = "MULTIPLICATION_ID")
    private Long id;

    private final int factorA;

    private final int factorB;

    private final int hashCodeValue;

    public Multiplication(int factorA, int factorB)
    {
        this.factorA = factorA;
        this.factorB = factorB;
        hashCodeValue = hashCode();
    }

    public Multiplication() {
        this(0,0);
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Multiplication that = (Multiplication) o;
        return factorA == that.factorA &&
                factorB == that.factorB;
    }

    @Override
    public int hashCode() {
        return Objects.hash(factorA, factorB);
    }

    public int getFactorB() {
        return factorB;
    }

    public int getFactorA() {
        return factorA;
    }

    public int getHashCodeValue() {
        return hashCodeValue;
    }

    @Override
    public String toString() {
        return "Multiplication{" +
                "factorA=" + factorA +
                ", factorB=" + factorB +
                '}';
    }
}
