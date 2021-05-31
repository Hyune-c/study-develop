package com.example.object._11.ratepolicy;

import com.example.object._11.Money;
import com.example.object._11.RatePolicy;

public class TaxablePolicy extends AdditionalRatePolicy {

  private double taxRatio;

  public TaxablePolicy(RatePolicy next, double taxRatio) {
    super(next);
    this.taxRatio = taxRatio;
  }

  @Override
  public Money afterCalculated(Money fee) {
    return fee.plus(fee.times(taxRatio));
  }
}
