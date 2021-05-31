package com.example.object._02.discount;

import com.example.object._02.Money;
import com.example.object._02.Screening;

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
