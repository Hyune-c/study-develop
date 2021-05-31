package com.example.object._09.discount.condition;

import com.example.object._09.Screening;

public interface DiscountCondition {

  boolean isSatisfiedBy(Screening screening);
}
