package com.example.object._14.billing;

import com.example.object._14.money.Money;

public abstract class AdditionalRatePolicy implements RatePolicy {

  private final RatePolicy next;

  public AdditionalRatePolicy(RatePolicy next) {
    this.next = next;
  }

  @Override
  public Money calculateFee(Phone phone) {
    Money fee = next.calculateFee(phone);
    return afterCalculated(fee);
  }

  abstract protected Money afterCalculated(Money fee);
}
