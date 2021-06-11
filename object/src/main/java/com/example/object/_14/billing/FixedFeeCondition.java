package com.example.object._14.billing;

import com.example.object._14.time.DateTimeInterval;
import java.util.Arrays;
import java.util.List;

public class FixedFeeCondition implements FeeCondition {

  @Override
  public List<DateTimeInterval> findTimeIntervals(Call call) {
    return Arrays.asList(call.getInterval());
  }
}
