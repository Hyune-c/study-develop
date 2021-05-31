package com.example.object._11;

import java.util.ArrayList;
import java.util.List;

public class Phone {

  private RatePolicy ratePolicy;
  private List<Call> calls = new ArrayList<>();

  public Phone(RatePolicy ratePolicy) {
    this.ratePolicy = ratePolicy;
  }

  protected Money calculateFee() {
    return ratePolicy.calculateFee(this);
  }

  public List<Call> getCalls() {
    return calls;
  }
}
