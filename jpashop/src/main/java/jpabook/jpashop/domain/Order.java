package jpabook.jpashop.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    public void setMember(Member member) {
        this.member = member;
        member.getOrders().add(this);
    }

    public void addOrderItem(OrderItem orderItem) {
        this.orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
        delivery.setOrder(this);
    }

    private Order(Member member, Delivery delivery, OrderItem... orderItems) {
        this.member = member;
        this.setDelivery(delivery);
        Arrays.stream(orderItems).forEach(this::addOrderItem);
        this.status = OrderStatus.ORDER;
        this.orderDate = LocalDateTime.now();
    }

    public static Order of(Member member, Delivery delivery, OrderItem... orderItems) {
        return new Order(member, delivery, orderItems);
    }

    public void cancel() {
        if (delivery.getStatus().equals(DeliveryStatus.COMP)) {
            throw new IllegalStateException("이미 배송된 상품은 취소가 불가합니다.");
        }
        this.setStatus(OrderStatus.CANCEL);

        orderItems.forEach(OrderItem::cancel);
    }

    public int getTotalPrice() {
        return orderItems.stream()
                .mapToInt(OrderItem::getTotalPrice)
                .sum();
    }
}
