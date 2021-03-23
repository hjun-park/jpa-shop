package jpabook.jpashop.domain.item;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;

@Entity
@DiscriminatorColumn(name = "B")    // 컬럼에서 Dtype에 B가 들어감
@Getter @Setter
public class Book extends Item {

    private String author;
    private String isbn;
}
