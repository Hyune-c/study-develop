package com.example.object._02;

import com.example.object._02.discount.AmountDiscountPolicy;
import com.example.object._02.discount.DiscountPolicy;
import com.example.object._02.discount.NoneDiscountPolicy;
import com.example.object._02.discount.PercentDiscountPolicy;
import com.example.object._02.discount.PeriodCondition;
import com.example.object._02.discount.SequenceCondition;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalTime;
import org.junit.jupiter.api.Test;

class MovieTest {

  @Test
  public void movie1() {
    // given
    DiscountPolicy discountPolicy = new AmountDiscountPolicy(
        Money.wons(800),
        new SequenceCondition(1),
        new SequenceCondition(10),
        new PeriodCondition(DayOfWeek.MONDAY, LocalTime.of(10, 0), LocalTime.of(11, 59)),
        new PeriodCondition(DayOfWeek.THURSDAY, LocalTime.of(10, 0), LocalTime.of(20, 59)));

    // when
    Movie avatar = new Movie(
        "아바타",
        Duration.ofMinutes(120),
        Money.wons(10000),
        discountPolicy);

    // then

  }

  @Test
  public void movie2() {
    // given
    DiscountPolicy discountPolicy = new PercentDiscountPolicy(
        0.1,
        new SequenceCondition(2),
        new PeriodCondition(DayOfWeek.MONDAY, LocalTime.of(14, 0), LocalTime.of(16, 59)),
        new PeriodCondition(DayOfWeek.THURSDAY, LocalTime.of(10, 0), LocalTime.of(13, 59)));

    // when
    Movie titanic = new Movie(
        "타이타닉",
        Duration.ofMinutes(180),
        Money.wons(11000),
        discountPolicy);

    // then

  }

  @Test
  public void movie3() {
    // given
    DiscountPolicy discountPolicy = new NoneDiscountPolicy();

    // when
    Movie titanic = new Movie(
        "스타워즈",
        Duration.ofMinutes(210),
        Money.wons(10000),
        discountPolicy);

    // then

  }
}
