package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Member {

    @Id @GeneratedValue // ID 값을 sequence value로 사용하겠다는 의미
    @Column(name = "member_id")
    private Long id;

    private String name;

    @Embedded   // 어딘가에 내장 타입으로 Address 타입 만들어 놓았다는 의미
    private Address address;

    // Order에 있는 member를 연관관계 주인으로 설정하였기 때문에 여기서는 MappedBy를 추가해준다.
    // 그러면 order 테이블에 있는 member 필드와 매핑이 되고
    // 마치 그 값을 거울처럼 바라보는 값이 된다. ( 즉, 읽기 전용 )
    // Order에 있는 Member member값을 변경해야지만 바뀌게 된다.
    @OneToMany(mappedBy = "member")  // member : orders = 1 : N
    private List<Order> orders = new ArrayList<>();
}
