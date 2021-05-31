package jpabook.jpashop.repository.order;

import static jpabook.jpashop.domain.QMember.*;
import static jpabook.jpashop.domain.QOrder.*;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.service.OrderSearch;

@Repository
public class OrderRepository {

	private final EntityManager em;
	private final JPAQueryFactory query;

	public OrderRepository(EntityManager em) {
		this.em = em;
		this.query = new JPAQueryFactory(em);
	}

	public Long save(Order order) {
		em.persist(order);
		return order.getId();
	}

	public Order findOne(Long id) {
		return em.find(Order.class, id);
	}

	public List<Order> findAllByString(OrderSearch orderSearch) {
		String jpql = "select o From Order o join o.member m";
		boolean isFirstCondition = true;

		if (orderSearch.getOrderStatus() != null) {
			if (isFirstCondition) {
				jpql += " where";
				isFirstCondition = false;
			} else {
				jpql += " and";
			}
			jpql += " o.status = :status";
		}

		if (StringUtils.hasText(orderSearch.getMemberName())) {
			if (isFirstCondition) {
				jpql += " where";
				isFirstCondition = false;
			} else {
				jpql += " and";
			}
			jpql += " m.name like :name";
		}

		TypedQuery query = em.createQuery(jpql, Order.class).setMaxResults(1000);

		if (orderSearch.getOrderStatus() != null) {
			query = query.setParameter("status", orderSearch.getOrderStatus());
		}
		if (StringUtils.hasText(orderSearch.getMemberName())) {
			query = query.setParameter("name", orderSearch.getMemberName());
		}

		return query.getResultList();
	}

	public List<Order> findAll(OrderSearch orderSearch) {
		return query
			.select(order)
			.from(order)
			.join(order.member, member)
			.where(statusEq(orderSearch.getOrderStatus()), nameLike(orderSearch.getMemberName()))
			.limit(1000)
			.fetch();
	}

	private BooleanExpression nameLike(String memberName) {
		if (!StringUtils.hasText(memberName)) {
			return null;
		}

		return member.name.like(memberName);
	}

	private Predicate statusEq(OrderStatus orderStatus) {
		if (orderStatus == null) {
			return null;
		}

		return order.status.eq(orderStatus);
	}

	public List<Order> findAllWithMemberDelivery() {
		return em.createQuery(
			"select o from Order o"
				+ " join fetch o.member m"
				+ " join fetch  o.delivery d", Order.class)
			.getResultList();
	}

	public List<Order> findAllWithMemberDelivery(Integer offset, Integer limit) {
		return em.createQuery(
			"select o from Order o"
				+ " join fetch o.member m"
				+ " join fetch  o.delivery d", Order.class)
			.setFirstResult(offset)
			.setMaxResults(limit)
			.getResultList();
	}

	public List<Order> findAllWithItem() {
		return em.createQuery(
			"select distinct o from Order o"
				+ " join fetch o.member m"
				+ " join fetch o.delivery d"
				+ " join fetch o.orderItems oi"
				+ " join fetch oi.item i", Order.class)
			.setFirstResult(1)
			.setMaxResults(100)
			.getResultList();
	}
}
