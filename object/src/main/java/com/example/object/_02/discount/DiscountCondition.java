package com.example.object._02.discount;

import com.example.object._02.Screening;

public interface DiscountCondition {

  boolean isSatisfiedBy(Screening screening);
}
