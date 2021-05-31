package com.example.object._02.discount;

import com.example.object._02.Money;
import com.example.object._02.Screening;

public class AmountDiscountPolicy extends DefaultDiscountPolicy {

  private Money discountAmount;

  public AmountDiscountPolicy(Money discountAmount, DiscountCondition... conditions) {
    super(conditions);
    this.discountAmount = discountAmount;
  }

  @Override
  protected Money getDiscountAmount(Screening screening) {
    return discountAmount;
  }
}
