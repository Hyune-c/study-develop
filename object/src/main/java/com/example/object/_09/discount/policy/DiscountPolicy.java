package com.example.object._09.discount.policy;

import com.example.object._09.Money;
import com.example.object._09.Screening;

public interface DiscountPolicy {

  Money calculateDiscountAmount(Screening screening);
}

