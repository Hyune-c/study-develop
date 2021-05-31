package com.example.object._02.discount;

import com.example.object._02.Screening;

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
