package com.example.object._12;

public class Professor {

  private final String name;
  private final Lecture lecture;

  public Professor(String name, Lecture lecture) {
    this.name = name;
    this.lecture = lecture;
  }

  public String compileStatistics() {
    return String.format("[%s] %s - Avg: %.1f", name, lecture.evaluate(), lecture.average());
  }
}
