package jpabook.jpashop.domain;

import jpabook.jpashop.domain.item.Item;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Category {

    @Id @GeneratedValue
    @Column(name = "category_id")
    private Long id;

    private String name;

//    @ManyToMany(mappedBy = "items")
//    private List<Item> items = new ArrayList<>();

    @ManyToMany         // 다대다 관계는 되도록이면 하지 않는게 좋음 ( 필드 추가가 안 되는 문제 )
    @JoinTable(name = "category_item",          // 1:다 / 다:1 로 풀어내기 위해 중간 테이블인 CATEGORY_ITEM을 만들어 줌
            joinColumns = @JoinColumn(name = "category_id"),    // category_item 테이블에 있는 category_id
            inverseJoinColumns = @JoinColumn(name = "item_id"))  // category_item 테이블에 있는 item_id
    private List<Item> items = new ArrayList<>();

    // 셀프로 연관관계 생성
    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Category parent;

    @OneToMany(mappedBy = "parent")
    private List<Category> child = new ArrayList<>();

}
