package com.example.object._14.billing;

import com.example.object._14.money.Money;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Phone {

  private final RatePolicy ratePolicy;
  private final List<Call> calls = new ArrayList<>();

  public Phone(RatePolicy ratePolicy) {
    this.ratePolicy = ratePolicy;
  }

  public void call(Call call) {
    calls.add(call);
  }

  public List<Call> getCalls() {
    return Collections.unmodifiableList(calls);
  }

  public Money calculateFee() {
    return ratePolicy.calculateFee(this);
  }
}
