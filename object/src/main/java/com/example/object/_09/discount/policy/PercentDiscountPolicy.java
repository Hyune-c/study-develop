package com.example.object._09.discount.policy;

import com.example.object._09.Money;
import com.example.object._09.Screening;
import com.example.object._09.discount.condition.DiscountCondition;

public class PercentDiscountPolicy extends DefaultDiscountPolicy {

  private double percent;

  public PercentDiscountPolicy(double percent, DiscountCondition... conditions) {
    super(conditions);
    this.percent = percent;
  }

  @Override
  protected Money getDiscountAmount(Screening screening) {
    return screening.getMovieFee().times(percent);
  }
}
