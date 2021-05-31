package com.example.object._09.discount.policy;

import com.example.object._09.Money;
import com.example.object._09.Screening;
import com.example.object._09.discount.condition.DiscountCondition;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class DefaultDiscountPolicy implements DiscountPolicy {

  List<DiscountCondition> conditions = new ArrayList<>();

  public DefaultDiscountPolicy(DiscountCondition... conditions) {
    this.conditions = Arrays.asList(conditions);
  }

  @Override
  public Money calculateDiscountAmount(Screening screening) {
    for (DiscountCondition each : conditions) {
      if (each.isSatisfiedBy(screening)) {
        return getDiscountAmount(screening);
      }
    }

    return Money.ZERO;
  }

  protected abstract Money getDiscountAmount(Screening screening);
}

