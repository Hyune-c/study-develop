package com.example.object._11.ratepolicy;

import com.example.object._11.Money;
import com.example.object._11.RatePolicy;

public class RateDiscountablePolicy extends AdditionalRatePolicy {

  private Money discountAmount;

  public RateDiscountablePolicy(RatePolicy next, Money discountAmount) {
    super(next);
    this.discountAmount = discountAmount;
  }

  @Override
  public Money afterCalculated(Money fee) {
    return fee.minus(discountAmount);
  }
}
