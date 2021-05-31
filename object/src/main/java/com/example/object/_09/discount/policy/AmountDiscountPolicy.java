package com.example.object._09.discount.policy;

import com.example.object._09.Money;
import com.example.object._09.Screening;
import com.example.object._09.discount.condition.DiscountCondition;

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
