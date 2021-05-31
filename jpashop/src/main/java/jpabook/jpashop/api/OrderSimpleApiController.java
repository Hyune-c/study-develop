package jpabook.jpashop.api;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import jpabook.jpashop.api.response.SimpleOrderDto;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.repository.order.OrderRepository;
import jpabook.jpashop.repository.order.simplequery.OrderSimpleQueryDto;
import jpabook.jpashop.repository.order.simplequery.OrderSimpleQueryRepository;
import jpabook.jpashop.service.OrderSearch;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class OrderSimpleApiController {

	private final OrderRepository orderRepository;
	private final OrderSimpleQueryRepository orderSimpleQueryRepository;

	@GetMapping("/api/v1/simple-orders")
	public List<Order> ordersV1() {
		return orderRepository.findAllByString(new OrderSearch());
	}

	@GetMapping("/api/v2/simple-orders")
	public List<SimpleOrderDto> ordersV2() {
		return orderRepository.findAllByString(new OrderSearch()).stream()
			.map(SimpleOrderDto::new)
			.collect(Collectors.toList());
	}

	@GetMapping("/api/v3/simple-orders")
	public List<SimpleOrderDto> ordersV3() {
		return orderRepository.findAllWithMemberDelivery().stream()
			.map(SimpleOrderDto::new)
			.collect(Collectors.toList());
	}

	@GetMapping("/api/v4/simple-orders")
	public List<OrderSimpleQueryDto> ordersV4() {
		return orderSimpleQueryRepository.findOrderDtos();
	}
}
