package com.example.object._12;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;

class LectureTest {

  @Test
  public void test() {
    // given
    Lecture lecture = new Lecture("객체지향 프로그래밍", 70, List.of(81, 95, 75, 50, 45));

    // when
    String evaluration = lecture.evaluate();

    // then
    assertThat(evaluration).isEqualTo("Pass: 3 Fail: 2");
  }
}
