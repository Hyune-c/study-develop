package jpabook.jpashop.api;

import static java.util.stream.Collectors.*;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jpabook.jpashop.api.response.OrderDto;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.repository.order.OrderRepository;
import jpabook.jpashop.repository.order.query.OrderFlatDto;
import jpabook.jpashop.repository.order.query.OrderItemQueryDto;
import jpabook.jpashop.repository.order.query.OrderQueryDto;
import jpabook.jpashop.repository.order.query.OrderQueryRepository;
import jpabook.jpashop.service.OrderSearch;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class OrderApiController {

	private final OrderRepository orderRepository;
	private final OrderQueryRepository orderQueryRepository;

	@GetMapping("/api/v1/orders")
	public List<Order> ordersV1() {
		return orderRepository.findAllByString(new OrderSearch()).stream()
			.peek(order -> {
				order.getMember().getName();
				order.getDelivery().getAddress();
				order.getOrderItems().stream()
					.forEach(orderItem -> orderItem.getItem().getName());
			})
			.collect(Collectors.toList());
	}

	@GetMapping("/api/v2/orders")
	public List<OrderDto> ordersV2() {
		return orderRepository.findAllByString(new OrderSearch()).stream()
			.map(OrderDto::new)
			.collect(Collectors.toList());
	}

	@GetMapping("/api/v3/orders")
	public List<OrderDto> ordersV3() {
		return orderRepository.findAllWithItem().stream()
			.map(OrderDto::new)
			.collect(Collectors.toList());
	}

	@GetMapping("/api/v3.1/orders")
	public List<OrderDto> ordersV3_page(
		@RequestParam(defaultValue = "0") Integer offset,
		@RequestParam(defaultValue = "100") Integer limit) {
		return orderRepository.findAllWithMemberDelivery(offset, limit).stream()
			.map(OrderDto::new)
			.collect(Collectors.toList());
	}

	@GetMapping("/api/v4/orders")
	public List<OrderQueryDto> ordersV4() {
		return orderQueryRepository.findOrderQueryDtos();
	}

	@GetMapping("/api/v5/orders")
	public List<OrderQueryDto> ordersV5() {
		return orderQueryRepository.findAllByDto_optimization();
	}

	@GetMapping("/api/v6/orders")
	public List<OrderQueryDto> ordersV6() {
		List<OrderFlatDto> flats = orderQueryRepository.findAllByDto_flat();
		return flats.stream()
			.collect(
				groupingBy(o -> new OrderQueryDto(o.getOrderId(), o.getName(), o.getOrderDate(), o.getOrderStatus(),
						o.getAddress()),
					mapping(
						o -> new OrderItemQueryDto(o.getOrderId(), o.getItemName(), o.getOrderPrice(), o.getCount()),
						toList())
				)).entrySet().stream()
			.map(e -> new OrderQueryDto(e.getKey().getOrderId(), e.getKey().getName(), e.getKey().getOrderDate(),
				e.getKey().getOrderStatus(), e.getKey().getAddress(), e.getValue()))
			.collect(toList());
	}
}
