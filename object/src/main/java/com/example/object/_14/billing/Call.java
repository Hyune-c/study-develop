package com.example.object._14.billing;

import com.example.object._14.time.DateTimeInterval;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

public class Call {

  private final DateTimeInterval interval;

  public Call(LocalDateTime from, LocalDateTime to) {
    this.interval = DateTimeInterval.of(from, to);
  }

  public Duration getDuration() {
    return interval.duration();
  }

  public LocalDateTime getFrom() {
    return interval.getFrom();
  }

  public LocalDateTime getTo() {
    return interval.getTo();
  }

  public DateTimeInterval getInterval() {
    return interval;
  }

  public List<DateTimeInterval> splitByDay() {
    return interval.splitByDay();
  }
}
