package jpabook.jpashop.api.response;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import lombok.Getter;

@Getter
public class OrderDto {

	private final Long orderId;
	private final String name;
	private final LocalDateTime orderDate;
	private final OrderStatus status;
	private final Address address;
	private final List<OrderItemDto> orderItems;

	public OrderDto(Order order) {
		this.orderId = order.getId();
		this.name = order.getMember().getName();
		this.orderDate = order.getOrderDate();
		this.status = order.getStatus();
		this.address = order.getDelivery().getAddress();
		this.orderItems = order.getOrderItems().stream()
			.map(OrderItemDto::new)
			.collect(Collectors.toList());
	}
}
