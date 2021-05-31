package jpabook.jpashop.api.response;

import java.time.LocalDateTime;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import lombok.Getter;

@Getter
public class SimpleOrderDto {

	private final Long orderId;
	private final String name;
	private final LocalDateTime orderDate;
	private final OrderStatus orderStatus;
	private final Address address;

	public SimpleOrderDto(Order order) {
		this.orderId = order.getId();
		this.name = order.getMember().getName();
		this.orderDate = order.getOrderDate();
		this.orderStatus = order.getStatus();
		this.address = order.getDelivery().getAddress();
	}
}
