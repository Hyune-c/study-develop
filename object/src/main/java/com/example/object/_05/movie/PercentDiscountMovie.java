package com.example.object._05.movie;

import com.example.object._05.Money;
import com.example.object._05.discount.condition.DiscountCondition;
import java.time.Duration;

public class PercentDiscountMovie extends Movie {

  private double percent;

  public PercentDiscountMovie(String title, Duration runningTime, Money fee, double percent, DiscountCondition... discountConditions) {
    super(title, runningTime, fee, discountConditions);
    this.percent = percent;
  }

  @Override
  protected Money calculateDiscountAmount() {
    return getFee().times(percent);
  }
}
