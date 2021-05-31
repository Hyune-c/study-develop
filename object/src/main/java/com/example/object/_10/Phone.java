package com.example.object._10;

import java.util.ArrayList;
import java.util.List;

public abstract class Phone {

  private double taxRate;
  private List<Call> calls = new ArrayList<>();

  public Phone(double taxRate) {
    this.taxRate = taxRate;
  }

  public void call(Call call) {
    calls.add(call);
  }

  public Money calculateFee() {
    Money result = Money.ZERO;

    for (Call call : calls) {
      result = calculateCallFee(call);
    }

    return result.plus(result.times(taxRate));
  }

  protected abstract Money calculateCallFee(Call call);
}
