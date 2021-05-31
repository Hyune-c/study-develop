package com.example.object._11.ratepolicy;

import com.example.object._11.Money;
import com.example.object._11.Phone;
import com.example.object._11.RatePolicy;

public abstract class AdditionalRatePolicy implements RatePolicy {

  private RatePolicy next;

  public AdditionalRatePolicy(RatePolicy next) {
    this.next = next;
  }

  @Override
  public Money calculateFee(Phone phone) {
    Money fee = next.calculateFee(phone);
    return afterCalculated(fee);
  }

  protected abstract Money afterCalculated(Money fee);
}
