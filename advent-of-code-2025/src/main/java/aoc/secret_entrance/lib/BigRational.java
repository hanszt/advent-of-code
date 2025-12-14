package aoc.secret_entrance.lib;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

public record BigRational(
        BigInteger numerator,
        BigInteger denominator
) implements Comparable<BigRational> {

    public static final BigRational ZERO = new BigRational(0);
    public static final BigRational ONE = new BigRational(1);
    public static final BigRational MINUS_ONE = new BigRational(-1);

    public BigRational(final long numerator) {
        this(BigInteger.valueOf(numerator), BigInteger.ONE);
    }

    public BigRational(BigInteger numerator, BigInteger denominator) {
        if (BigInteger.ZERO.equals(denominator)) {
            throw new ArithmeticException("Denominator may not be zero");
        }
        final var g = numerator.gcd(denominator);
        numerator = numerator.divide(g);
        denominator = denominator.divide(g);
        if (denominator.signum() > 0) {
            this.numerator = numerator;
            this.denominator = denominator;
        } else {
            this.numerator = numerator.negate();
            this.denominator = denominator.negate();
        }
    }

    @Override
    public String toString() {
        return isIntegral() ? numerator + "" : numerator + "/" + denominator;
    }

    @Override
    public int compareTo(final BigRational b) {
        final var a = this;
        return a.numerator.multiply(b.denominator).compareTo(a.denominator.multiply(b.numerator));
    }

    public boolean isZero() {
        return numerator.signum() == 0;
    }

    public boolean isPositive() {
        return numerator.signum() > 0;
    }

    public boolean isNegative() {
        return numerator.signum() < 0;
    }

    public boolean isIntegral() {
        return BigInteger.ONE.equals(denominator);
    }

    public BigRational add(final BigRational b) {
        final var a = this;
        return new BigRational(a.numerator.multiply(b.denominator).add(b.numerator.multiply(a.denominator)), a.denominator.multiply(b.denominator));
    }

    public BigRational subtract(final BigRational b) {
        final var a = this;
        return new BigRational(a.numerator.multiply(b.denominator).subtract(b.numerator.multiply(a.denominator)), a.denominator.multiply(b.denominator));
    }

    public BigRational negate() {
        return new BigRational(numerator.negate(), denominator);
    }

    public BigRational multiply(final BigRational b) {
        final var a = this;
        return new BigRational(a.numerator.multiply(b.numerator), a.denominator.multiply(b.denominator));
    }

    public BigRational divide(final BigRational b) {
        if (b.isZero()) {
            throw new ArithmeticException("Division by zero");
        }
        final var a = this;
        return new BigRational(a.numerator.multiply(b.denominator), a.denominator.multiply(b.numerator));
    }

    public BigRational reciprocal() {
        return new BigRational(denominator, numerator);
    }

    public BigRational floor() {
        final var d = new BigDecimal(numerator).divide(new BigDecimal(denominator), 0, RoundingMode.FLOOR);
        return new BigRational(d.toBigInteger(), BigInteger.ONE);
    }

    public BigRational ceil() {
        final var d = new BigDecimal(numerator).divide(new BigDecimal(denominator), 0, RoundingMode.CEILING);
        return new BigRational(d.toBigInteger(), BigInteger.ONE);
    }

    public long longValueExact() {
        if (!isIntegral()) {
            throw new ArithmeticException("Not an integral number");
        }
        return numerator.longValueExact();
    }
}
