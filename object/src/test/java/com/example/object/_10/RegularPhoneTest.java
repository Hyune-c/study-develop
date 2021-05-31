package com.example.object._10;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Duration;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

class RegularPhoneTest {

  @Test
  public void phone() {
    // given
    Phone regularPhone = new RegularPhone(0.1, (Money.wons(5)), Duration.ofSeconds(10));

    // when
    regularPhone.call(new Call(
        LocalDateTime.of(2018, 1, 1, 12, 10, 0),
        LocalDateTime.of(2018, 1, 1, 12, 11, 0)));

    regularPhone.call(new Call(
        LocalDateTime.of(2018, 1, 2, 12, 10, 0),
        LocalDateTime.of(2018, 1, 2, 12, 11, 0)));

    // then
    assertThat(regularPhone.calculateFee()).isEqualTo(Money.wons(30));
  }
}
