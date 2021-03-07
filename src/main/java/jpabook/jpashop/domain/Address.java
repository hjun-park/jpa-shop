package jpabook.jpashop.domain;

import javax.persistence.Embeddable;

@Embeddable // JPA의 내장 타입으로써 어딘가에 내장 될 수 있다는 것을 명시
public class Address {

    private String city;
    private String street;
    private String zipcode;
}
