package com.example.object._10;

import java.time.Duration;

public class RegularPhone extends Phone {

  private Money amount;
  private Duration seconds;

  public RegularPhone(double taxRate, Money amount, Duration seconds) {
    super(taxRate);
    this.amount = amount;
    this.seconds = seconds;
  }

  @Override
  protected Money calculateCallFee(Call call) {
    return amount.times(call.getDuration().getSeconds() / seconds.getSeconds());
  }
}
