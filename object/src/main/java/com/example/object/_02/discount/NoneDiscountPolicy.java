package com.example.object._02.discount;

import com.example.object._02.Money;
import com.example.object._02.Screening;

public class NoneDiscountPolicy implements DiscountPolicy {

  @Override
  public Money calculateDiscountAmount(Screening screening) {
    return Money.ZERO;
  }
}
