package jpabook.jpashop.service;

import jpabook.jpashop.domain.Delivery;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderItem;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import jpabook.jpashop.repository.MemberRepository;
import jpabook.jpashop.repository.OrderRepository;
import jpabook.jpashop.repository.OrderSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor    // lombok
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    /*
        주문
     */
    @Transactional
    public Long order(Long memberId, Long itemId, int count) {

        // 엔티티 조회
        Member member = memberRepository.findOne(memberId);
        Item item = itemRepository.findOne(itemId);

        // 배송정보 생성
        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());

        // 주문상품 생성 : 예제 단순화를 위해 주문은 1개로 제한함
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

        // 주문 생성
        Order order = Order.createOrder(member, delivery, orderItem);

        // 주문 저장
        // Order 코드 들어가서 보면 각 엔티티에 CASCADE가 걸려있는데 이렇게 하나만 저장해도 자동으로 다 저장이 된다. (persist)
        // 그럼 어디까지 cascade 해야하나 ? :: 다른 곳에서 참조하는 경우가 없는 경우
        // 즉, 현재는 OrderItem과 Delivery에 Cascade가 걸려있는데 ( Order 코드 보면 알 수 있음 )
        // Delivery와 OrderItem을 참조하는 다른 엔티티는 없는 상태 => 이 경우 CASCADE 사용하면 좋음
        orderRepository.save(order);

        return order.getId();

    }


    /*
        주문 취소
     */
    @Transactional
    public void cancelOrder(Long orderId){

        // 주문 엔티티 조회
        Order order = orderRepository.findOne(orderId);

        // 주문 취소
        order.cancel();
    }


    // 검색
    public List<Order> findOrders(OrderSearch orderSearch) {
        return orderRepository.findAllbyString(orderSearch);
    }

}
