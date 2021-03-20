package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
@Table(name = "orders")
@Getter @Setter
public class Order {

    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    // order랑 member는 N:1 관계
    @ManyToOne(fetch = LAZY) // Many to One은 기본 Fetch가 EAGER, 즉, EAGER가 Default이기 때문에 꼭 LAZY로 바꾸어주어야 한다.
    @JoinColumn(name = "member_id") // member와 어떤거로 join을 할 것인지? => member_id를 FK로 한다.
    private Member member;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL) // One to Many는 기본 Fetch 전략이 LAZY 그냥 두어도 됨
    private List<OrderItem> orderItems = new ArrayList<>();

    // FK가 Order에 있기 때문에 연관관계 주인을 Order로 잡음
    @OneToOne(fetch = LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;  // persist 연쇄

    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)    // ORDINAL 사용 금지
    private OrderStatus status; // 주문상태 [ORDER, CANCEL]

    //==연관관계 메소드==// :: 이렇게만 짜도 Order <-> Member 서로 연관관계가 형성된다.
    public void setMember(Member member) {
        this.member = member;
        member.getOrders().add(this);
    }

    // Order와 OrderItem 양방향 연관관계 형성
    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    // Order와 Delivery 양방향 연관관계 형셩
    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
        delivery.setOrder(this);
    }




}
