package jpabook.jpashop;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter @Setter  // lombok이 있으면 어노테이션만으로도 가능
public class Member {

    @Id @GeneratedValue
    private Long id;
    private String username;

}
