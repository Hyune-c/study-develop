package jpabook.jpashop.repository.order.simplequery;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import jpabook.jpashop.domain.Order;
import jpabook.jpashop.service.OrderSearch;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class OrderSimpleQueryRepository {

	private final EntityManager em;

	public List<OrderSimpleQueryDto> findOrderDtos() {
		return em.createQuery(
			"select new jpabook.jpashop.dto.OrderSimpleQueryDto(o.id, m.name, o.orderDate, o.status, d.address) from Order o"
				+ " join o.member m"
				+ " join o.delivery d", OrderSimpleQueryDto.class
		).getResultList();
	}
}
