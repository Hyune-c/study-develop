package com.example.object._11.ratepolicy;

import com.example.object._11.Call;
import com.example.object._11.Money;
import com.example.object._11.Phone;
import com.example.object._11.RatePolicy;

public abstract class BasicRatePolicy implements RatePolicy {

  @Override
  public Money calculateFee(Phone phone) {
    Money result = Money.ZERO;

    for (Call call : phone.getCalls()) {
      result.plus(calculateCallFee(call));
    }

    return result;
  }

  protected abstract Money calculateCallFee(Call call);
}
