package jpabook.jpashop.repository.order.simplequery;

import java.time.LocalDateTime;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.OrderStatus;
import lombok.Getter;

@Getter
public class OrderSimpleQueryDto {

	private final Long orderId;
	private final String name;
	private final LocalDateTime orderDate;
	private final OrderStatus orderStatus;
	private final Address address;

	public OrderSimpleQueryDto(Long orderId, String name, LocalDateTime orderDate, OrderStatus orderStatus,
		Address address) {
		this.orderId = orderId;
		this.name = name;
		this.orderDate = orderDate;
		this.orderStatus = orderStatus;
		this.address = address;
	}
}
