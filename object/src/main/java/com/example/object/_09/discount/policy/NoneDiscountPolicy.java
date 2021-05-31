package com.example.object._09.discount.policy;

import com.example.object._09.Money;
import com.example.object._09.Screening;

public class NoneDiscountPolicy implements DiscountPolicy {

  @Override
  public Money calculateDiscountAmount(Screening screening) {
    return Money.ZERO;
  }
}
