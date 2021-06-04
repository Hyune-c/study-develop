package com.example.object._12;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;

class ProfessorTest {

  @Test
  public void test() {
    // given
    Professor professor = new Professor(("다익스트라"), new GradeLecture("객체지향 프로그래밍", 70, List.of(
        new Grade("A", 100, 95),
        new Grade("B", 94, 80),
        new Grade("C", 79, 70),
        new Grade("D", 69, 50),
        new Grade("E", 49, 0)),
        List.of(81, 95, 75, 50, 45)));

    // when
    String statistics = professor.compileStatistics();

    // then
    assertThat(statistics).isEqualTo("[다익스트라] Pass: 3 Fail: 2, A: 1 B: 1 C: 1 D: 1 E: 1 - Avg: 69.2");
  }
}
