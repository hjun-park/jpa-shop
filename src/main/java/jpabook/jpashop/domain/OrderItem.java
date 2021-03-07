package jpabook.jpashop.domain;

import jpabook.jpashop.domain.item.Item;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class OrderItem {

    @Id @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne               // Order Item : Order
    @JoinColumn(name = "order_id")  // FK가 있는 OrderItem에서 매핑 시켜줌
    private Order order;

    private int orderPrice; // 주문 당시 가격
    private int count;      // 주문 당시 수량
}
