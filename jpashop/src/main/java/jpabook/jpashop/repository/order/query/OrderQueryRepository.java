package jpabook.jpashop.repository.order.query;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class OrderQueryRepository {

	private final EntityManager em;

	public List<OrderQueryDto> findOrderQueryDtos() {
		return findOrders().stream()
			.peek(o -> {
				List<OrderItemQueryDto> orderItems = findOrderItems(o.getOrderId());
				o.setOrderItems(orderItems);
			})
			.collect(Collectors.toList());
	}

	private List<OrderQueryDto> findOrders() {
		return em.createQuery(
			"select new jpabook.jpashop.repository.order.query.OrderQueryDto(o.id, m.name, o.orderDate, o.status, d.address)"
				+ " from Order o"
				+ " join o.member m"
				+ " join o.delivery d", OrderQueryDto.class)
			.getResultList();
	}

	private List<OrderItemQueryDto> findOrderItems(Long orderId) {
		return em.createQuery(
			"select new jpabook.jpashop.repository.order.query.OrderItemQueryDto(oi.order.id, i.name, oi.orderPrice, oi.count)"
				+ " from OrderItem oi"
				+ " join oi.item i"
				+ " where oi.order.id = :orderId", OrderItemQueryDto.class)
			.setParameter("orderId", orderId)
			.getResultList();
	}

	public List<OrderQueryDto> findAllByDto_optimization() {
		List<OrderQueryDto> result = findOrders();

		Map<Long, List<OrderItemQueryDto>> orderItemsMap = findOrderItemMap(toOrderIds(result));

		result.forEach(o -> o.setOrderItems(orderItemsMap.get(o.getOrderId())));

		return result;
	}

	private Map<Long, List<OrderItemQueryDto>> findOrderItemMap(List<Long> orderIds) {
		List<OrderItemQueryDto> orderItems = em.createQuery(
			"select new jpabook.jpashop.repository.order.query.OrderItemQueryDto(oi.order.id, i.name, oi.orderPrice, oi.count)"
				+ " from OrderItem oi"
				+ " join oi.item i"
				+ " where oi.order.id in :orderIds", OrderItemQueryDto.class)
			.setParameter("orderIds", orderIds)
			.getResultList();

		return orderItems.stream()
			.collect(Collectors.groupingBy(OrderItemQueryDto::getOrderId));
	}

	private List<Long> toOrderIds(List<OrderQueryDto> result) {
		return result.stream()
			.map(OrderQueryDto::getOrderId)
			.collect(Collectors.toList());
	}

	public List<OrderFlatDto> findAllByDto_flat() {
		return em.createQuery(
			"select new jpabook.jpashop.repository.order.query.OrderFlatDto(oi.order.id, m.name, o.orderDate, o.status, d.address, i.name, oi.orderPrice, oi.count)"
				+ " from Order o"
				+ " join o.member m"
				+ " join o.delivery d"
				+ " join o.orderItems oi"
				+ " join oi.item i", OrderFlatDto.class)
			.getResultList();
	}
}
