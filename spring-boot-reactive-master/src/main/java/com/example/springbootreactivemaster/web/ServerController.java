package com.example.springbootreactivemaster.web;

import com.example.springbootreactivemaster.domain.Dish;
import com.example.springbootreactivemaster.domain.Kitchen;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RequiredArgsConstructor
@RestController
public class ServerController {

  private final Kitchen kitchen;

  @GetMapping(value = "/server", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
  public Flux<Dish> serveDishes() {
    return kitchen.getDishes();
  }

  @GetMapping(value = "/served-dishes", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
  public Flux<Dish> deliverDishes() {
    return kitchen.getDishes()
        .map(Dish::deliver);
  }
}
