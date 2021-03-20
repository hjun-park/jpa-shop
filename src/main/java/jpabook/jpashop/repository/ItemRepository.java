package jpabook.jpashop.repository;

import jpabook.jpashop.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

    private final EntityManager em;

    public void save(Item item) {
        if (item.getId() == null) {     // id가 없으면 새로운거라고 판단, item은 데이터 저장할 때 처음에 id가 없음. 그래서 persist 사용
            em.persist(item);           // 이거가 실행된다는 거는 Item을 완전히 새로 등록한다는 것을 의미
        } else {
            em.merge(item);             // 아니면 merge.  merge는 update 비슷하다고 볼 수 있음
        }
    }

    public Item findOne(Long id) {
        return em.find(Item.class, id);
    }

    public List<Item> findAll() {
        return em.createQuery("select i from Item i", Item.class)
                .getResultList();
    }

}
