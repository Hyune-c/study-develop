package com.example.object._09.discount.condition;

import com.example.object._09.Screening;

public class SequenceCondition implements DiscountCondition {

  private int sequence;

  public SequenceCondition(int sequence) {
    this.sequence = sequence;
  }

  @Override
  public boolean isSatisfiedBy(Screening screening) {
    return screening.isSequence(sequence);
  }
}
