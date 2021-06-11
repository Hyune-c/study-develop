package com.example.object._14.billing;

import com.example.object._14.time.DateTimeInterval;
import java.util.List;

public interface FeeCondition {

  List<DateTimeInterval> findTimeIntervals(Call call);
}
