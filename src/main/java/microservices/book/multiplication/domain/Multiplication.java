package microservices.book.multiplication.domain;

import java.util.Objects;

public class Multiplication {
    private final int factorA;
    private final int factorB;


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
