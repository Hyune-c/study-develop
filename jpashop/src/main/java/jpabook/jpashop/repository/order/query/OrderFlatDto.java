package jpabook.jpashop.repository.order.query;

import java.time.LocalDateTime;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class OrderFlatDto {

	private final Long orderId;
	private final String name;
	private final LocalDateTime orderDate;
	private final OrderStatus orderStatus;
	private final Address address;

	private String itemName;
	private Integer orderPrice;
	private Integer count;
}
