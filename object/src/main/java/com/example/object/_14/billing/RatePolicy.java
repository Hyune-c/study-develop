package com.example.object._14.billing;

import com.example.object._14.money.Money;

public interface RatePolicy {

  Money calculateFee(Phone phone);
}
