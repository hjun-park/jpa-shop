package jpabook.jpashop.controller;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;


@Getter @Setter
public class MemberForm {

    @NotEmpty(message = "회원 이름은 필수입니다.")   // name이 비어 있으면 오류를 출력하도록 설정
    private String name;

    private String city;
    private String street;
    private String zipcode;
}
