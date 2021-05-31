package jpabook.jpashop.repository.order.query;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class OrderItemQueryDto {

	private Long orderId;
	private String itemName;
	private Integer orderPrice;
	private Integer count;
}
