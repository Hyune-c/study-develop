package com.example.object._09.discount;

import com.example.object._09.Money;
import com.example.object._09.Movie;
import com.example.object._09.discount.policy.AmountDiscountPolicy;
import java.time.Duration;

public class Factory {

  public Movie createAvatarMovie() {
    return new Movie("아바타",
        Duration.ofMinutes(120),
        Money.wons(1000),
        new AmountDiscountPolicy(null));
  }
}
