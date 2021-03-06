package jpabook.jpashop.domain;

import jpabook.jpashop.domain.item.Item;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem {

    @Id @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = LAZY)              // Order Item : Order
    @JoinColumn(name = "order_id")  // FK가 있는 OrderItem에서 매핑 시켜줌
    private Order order;

    private int orderPrice; // 주문 당시 가격
    private int count;      // 주문 당시 수량


    //== 생성 메소드 ==//
    public static OrderItem createOrderItem(Item item, int orderPrice, int count) {
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setOrderPrice(orderPrice);
        orderItem.setCount(count);

        item.removeStock(count);
        return orderItem;

    }

    //== 비즈니스 로직 ==//
    public void cancel() {
        getItem().addStock(count);  // 재고 수량을 해제한 만큼 증가 (  재고수량 원복 )
    }

    //== 조회 로직 ==//
    /*
        주문상품 전체 가격조회
     */
    public int getTotalPrice() {
        return getOrderPrice() * getCount();
    }
}
