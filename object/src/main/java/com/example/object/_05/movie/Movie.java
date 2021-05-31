package com.example.object._05.movie;

import com.example.object._05.Money;
import com.example.object._05.Screening;
import com.example.object._05.discount.condition.DiscountCondition;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;

public abstract class Movie {

  private String title;
  private Duration runningTime;
  private Money fee;
  private List<DiscountCondition> discountConditions;

  public Movie(String title, Duration runningTime, Money fee, DiscountCondition... discountConditions) {
    this.title = title;
    this.runningTime = runningTime;
    this.fee = fee;
    this.discountConditions = Arrays.asList(discountConditions);
  }

  public Money calculateMovieFee(Screening screening) {
    if (isDiscountable(screening)) {
      return fee.minus(calculateDiscountAmount());
    }

    return fee;
  }

  public boolean isDiscountable(Screening screening) {
    return discountConditions.stream()
        .anyMatch(condition -> condition.isSatisfiedBy(screening));
  }

  public Money getFee() {
    return fee;
  }

  protected abstract Money calculateDiscountAmount();
}
