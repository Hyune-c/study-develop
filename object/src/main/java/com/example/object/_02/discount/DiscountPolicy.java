package com.example.object._02.discount;

import com.example.object._02.Money;
import com.example.object._02.Screening;

public interface DiscountPolicy {

  Money calculateDiscountAmount(Screening screening);
}

