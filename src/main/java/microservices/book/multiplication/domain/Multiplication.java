package microservices.book.multiplication.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import java.util.Objects;
@Entity
public class Multiplication {

    @Id
    @GeneratedValue
    @Column(name = "MULTIPLICATION_ID")
    private long id;

    private final int factorA;
    private final int factorB;

    public Multiplication() {
        this(0,0);
    }

    public Multiplication(int factorA, int factorB)
    {
        this.factorA = factorA;
        this.factorB = factorB;
    }

    public int getFactorA() {
        return factorA;
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

    @Override
    public String toString() {
        return "Multiplication{" +
                "factorA=" + factorA +
                ", factorB=" + factorB +
                '}';
    }
}
