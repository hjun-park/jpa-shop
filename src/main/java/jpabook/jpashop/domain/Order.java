package jpabook.jpashop.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
@Table(name = "orders")
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)  // order 생성할 때 new로 생성 말고 static 메소드(createOrder)가 있음을 알려줌
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


    //==생성 메소드==// :: 주문 생성
    public static Order createOrder(Member member, Delivery delivery, OrderItem... orderItems) {    // ... 여러개 넘기기 위함
        Order order = new Order();      // 주문샏성
        order.setMember(member);        // 셋팅
        order.setDelivery(delivery);    // 셋팅

        for (OrderItem orderItem : orderItems) {
            order.addOrderItem(orderItem);      // 현재 orderitem을 집어넣어줌
        }

        order.setStatus(OrderStatus.ORDER);         // orderstatus를 ORDER 상태로 셋팅
        order.setOrderDate(LocalDateTime.now());   // 주문시간을 셋팅
        return order;
    }


    //== 비즈니스 로직 ==//
    /*
        주문 취소
     */
    public void cancel() {
        if (delivery.getStatus() == DeliveryStatus.COMP) { // 만약 배송 완료 상태라면
            throw new IllegalStateException("이미 배송완료된 상품은 취소가 불가능합니다");
        }


        this.setStatus(OrderStatus.CANCEL);     // 주문 취소로 설정
        for (OrderItem orderItem : orderItems) {
            orderItem.cancel();     // 주문 상품들 취소 - 이건 OrderItem에 설계해주는 것이 좋을 듯함
        }
    }


    //== 조회 로직 ==//
    /*
        전체 주문 가격 조회
     */
    public int getTotalPrice() {
//        int totalPrice = 0;
//        for (OrderItem orderItem : orderItems) {
//            totalPrice += orderItem.getTotalPrice();    // 주문 총 가격에는 주문 가격과 주문 수량이 필요하므로 orderItem에서 로직 생성
//        }
//        return totalPrice;

        // 위에 주석처리된 부분을 아래와 같이 짧게 표현이 가능
        int totalPrice = orderItems.stream().mapToInt(OrderItem::getTotalPrice).sum();

        return totalPrice;
    }







}
