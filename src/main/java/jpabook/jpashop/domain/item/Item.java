package jpabook.jpashop.domain.item;

import jpabook.jpashop.domain.Category;
import jpabook.jpashop.exception.NotEnoughStockException;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)  // single table : 한 테이블에 다 때려넣음
@DiscriminatorColumn(name = "dtype") // 싱글테이블이기 때문에 저장할 때 이게 책인지 앨범인지 등등 구분하기 위한 값
@Getter @Setter // : setter는 쓰지 않는 것이 좋다.
public abstract class Item {    // item을 상속받아서 앨범/책/음악 구현 할 것이므로 추상화 클래스

    @Id @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;

    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<Category>();

    //== 비즈니스 로직==//
    // 데이터를 가지고 있는 쪽에 비즈니스 메소드가 있는게 좋다.
    // 현재 stockQuantity를 가지고 재고를 표현하는데 이에 대한 로직 구현을 같은 곳에서 하는 것이 좋다.

    /*
        stock 증가
     */
    public void addStock(int quantity) {
        this.stockQuantity += quantity;
    }

    /*
        stock 감소
     */
    public void removeStock(int quantity) {
        int restStock = this.stockQuantity - quantity;
        // 재고를 없앴는데 나머지가 마이너스인 경우 문제 발생
        if(restStock < 0) {
            throw new NotEnoughStockException("need more stock");
        }

        this.stockQuantity = restStock;
    }

}
