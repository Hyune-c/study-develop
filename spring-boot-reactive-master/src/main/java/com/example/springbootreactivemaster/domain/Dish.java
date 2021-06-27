package com.example.springbootreactivemaster.domain;

import lombok.Getter;
import lombok.Setter;

public class Dish {

  @Getter
  @Setter
  private String description;
  private boolean delivered = false;

  public static Dish deliver(Dish dish) {
    Dish deliveredDish = new Dish(dish.description);
    deliveredDish.delivered = true;
    return deliveredDish;
  }

  public Dish(String description) {
    this.description = description;
  }

  public boolean isDelivered() {
    return delivered;
  }

  @Override
  public String toString() {
    return "Dish{" +
        "description='" + description + '\'' +
        ", delivered=" + delivered +
        '}';
  }
}
