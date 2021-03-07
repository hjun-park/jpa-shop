package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter @Setter
public class Order {

    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    // order랑 member는 N:1 관계
    @ManyToOne
    @JoinColumn(name = "member_id") // member와 어떤거로 join을 할 것인지? => member_id를 FK로 한다.
    private Member member;

    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems = new ArrayList<>();

    // FK가 Order에 있기 때문에 연관관계 주인을 Order로 잡음
    @OneToOne
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)    // ORDINAL 사용 금지
    private OrderStatus status; // 주문상태 [ORDER, CANCEL]
}
