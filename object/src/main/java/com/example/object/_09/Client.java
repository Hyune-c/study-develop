package com.example.object._09;

import com.example.object._09.discount.Factory;

public class Client {

  private Factory factory;

  public Client(Factory factory) {
    this.factory = factory;
  }

  public Money getAvatarFee() {
    Movie avatar = factory.createAvatarMovie();
    return avatar.getFee();
  }
}
