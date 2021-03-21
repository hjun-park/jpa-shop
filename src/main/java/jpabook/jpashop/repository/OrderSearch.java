package jpabook.jpashop.repository;

import jpabook.jpashop.domain.OrderStatus;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class OrderSearch {

    private String memberName;  // 회원 이름 ( 파라미터 조건이 있으면 얘로 검색 )
    private OrderStatus orderStatus; // 주문 상태 [ ORDER, CANCEL ]
}
