package jpabook.jpashop.domain.item;

import jpabook.jpashop.domain.Category;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)  // single table : 한 테이블에 다 때려넣음
@DiscriminatorColumn(name = "dtype") // 싱글테이블이기 때문에 저장할 때 이게 책인지 앨범인지 등등 구분하기 위한 값
@Getter @Setter
public abstract class Item {    // item을 상속받아서 앨범/책/음악 구현 할 것이므로 추상화 클래스

    @Id @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;

    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<Category>();

}
