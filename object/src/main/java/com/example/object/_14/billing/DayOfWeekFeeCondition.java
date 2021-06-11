package com.example.object._14.billing;

import com.example.object._14.time.DateTimeInterval;
import java.time.DayOfWeek;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class DayOfWeekFeeCondition implements FeeCondition {

  private final List<DayOfWeek> dayOfWeeks;

  public DayOfWeekFeeCondition(DayOfWeek... dayOfWeeks) {
    this.dayOfWeeks = Arrays.asList(dayOfWeeks);
  }

  @Override
  public List<DateTimeInterval> findTimeIntervals(Call call) {
    return call.getInterval()
        .splitByDay()
        .stream()
        .filter(each -> dayOfWeeks.contains(each.getFrom().getDayOfWeek()))
        .collect(Collectors.toList());
  }
}
