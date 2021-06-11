package com.example.object._14.billing;

import com.example.object._14.money.Money;

public class RateDiscountablePolicy extends AdditionalRatePolicy {

  private final Money discountAmount;

  public RateDiscountablePolicy(Money discountAmount, RatePolicy next) {
    super(next);
    this.discountAmount = discountAmount;
  }

  @Override
  protected Money afterCalculated(Money fee) {
    return fee.minus(discountAmount);
  }
}
