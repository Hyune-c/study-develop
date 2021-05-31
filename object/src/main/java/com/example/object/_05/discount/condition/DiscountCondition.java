package com.example.object._05.discount.condition;

import com.example.object._05.Screening;

public interface DiscountCondition {

  boolean isSatisfiedBy(Screening screening);
}
