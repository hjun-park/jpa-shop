package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter @Setter
public class Delivery {

    @Id @GeneratedValue
    @Column(name = "delivery_id")
    private Long id;

    // 매핑 시켜줄 변수 ( 하나의 주문 당 하나의 배송정보 ) - 거울
    @OneToOne(mappedBy = "delivery", fetch = LAZY)    // One to One도 Default가 EAGER이기 때문에 LAZY로 변경
    private Order order;

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING)   // ordinal : 절대 X 순서밀림 문제 발생
    private DeliveryStatus status; // READY, COMP

}
