package jpabook.jpashop.service;

import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
@Transactional(readOnly = true) // JPA 사용 할 예정
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    @Transactional  // 없으면 기본값 readOnly = true 때문에 save가 안 되어서 추가
    public void saveItem(Item item) {
        itemRepository.save(item);
    }

    @Transactional
    public void updateItem(Long itemId, String name, int price, int stockQuantity) {
        Item findItem = itemRepository.findOne(itemId); // itemId기반으로 실제 영속상태의 엔티티를 찾아옴
        findItem.setName(name);
        findItem.setPrice(price);
        findItem.setStockQuantity(stockQuantity);       // 엔티티의 데이터를 직접 변경하면 트랜잭션 변경감지가 되면서 update 쿼리가 DB에 전달된다.
        // 위에 setter를 쓰기보다는 차라리 findItem.change 메소드를 만들어서 하면 어디서 변경되는지 손쉽게 알 수 있다.

        /* Dirty Checking 동작( = 변경 감지 기능 ) ( 영속성 컨텍스트에서 엔티티를 다시 조회 후에 데이터를 수정하는 방법 ) */
        // 아래 문구는 이미 위에서 set하면 Transactional에 의해서 commit이 되고 JPA는 flush를 날린다,
        // flush : 영속성 컨텍스트에 있는 엔티티 중에서 변경된 것이 무엇인지 찾는다.
        // 찾아서 저 위에가 바뀌었으니 이 부분에 대해서 update 쿼리 날려서 반영하게 된다.
        // 알아서 저장이 되므로, 굳이 아래처럼 DB 내용 반영이 필요하지 않다.

        // itemRepository.save(findItem); // 필요하지 않다.
    }

    public List<Item> findItems() {
        return itemRepository.findAll();
    }

    public Item findOne(Long itemId) {
        return itemRepository.findOne(itemId);
    }

}
