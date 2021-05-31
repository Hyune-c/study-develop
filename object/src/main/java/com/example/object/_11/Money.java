package com.example.object._11;

import java.math.BigDecimal;
import java.util.Objects;

public class Money {

  public static final Money ZERO = Money.wons(0);

  private final BigDecimal amount;

  public Money(BigDecimal amount) {
    this.amount = amount;
  }

  private static Money wons(long amount) {
    return new Money(BigDecimal.valueOf(amount));
  }

  public static Money wons(double amount) {
    return new Money(BigDecimal.valueOf(amount));
  }

  public Money plus(Money amount) {
    return new Money(this.amount.add((amount.amount)));
  }

  public Money minus(Money amount) {
    return new Money(this.amount.subtract((amount.amount)));
  }

  public Money times(double percent) {
    return new Money(this.amount.multiply(BigDecimal.valueOf(percent)));
  }

  public boolean isLessThen(Money other) {
    return amount.compareTo(other.amount) < 0;
  }

  public boolean isGreaterThanOrEqual(Money other) {
    return amount.compareTo(other.amount) >= 0;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Money)) {
      return false;
    }
    Money money = (Money) o;
    return Objects.equals(amount.toBigInteger(), money.amount.toBigInteger());
  }

  @Override
  public int hashCode() {
    return Objects.hash(amount);
  }
}
