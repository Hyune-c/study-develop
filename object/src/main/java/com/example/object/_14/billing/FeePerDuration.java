package com.example.object._14.billing;

import com.example.object._14.money.Money;
import com.example.object._14.time.DateTimeInterval;
import java.time.Duration;

public class FeePerDuration {

  private final Money fee;
  private final Duration duration;

  public FeePerDuration(Money fee, Duration duration) {
    this.fee = fee;
    this.duration = duration;
  }

  public Money calculate(DateTimeInterval interval) {
    return fee.times(Math.ceil((double) interval.duration().toNanos() / duration.toNanos()));
  }
}

