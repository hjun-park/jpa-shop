package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.fail;


/*
    회원가입 그리고 중복된 회원가입의 경우에 대해서 테스트
 */

@RunWith(SpringRunner.class)    // Junit 실행 시 Spring이랑 같이 엮어서 실행할래
@SpringBootTest                 // Spring boot 띄운 상태로 테스트 하기 위해서
@Transactional                  // Service, Repository 애노테이션이 붙으면 롤백하지 않지만 테스트에서 붙으면 끝나고 롤백을 해 줌
public class MemberServiceTest {

    // 테스트니까 필드 인젝션으로 간단하게 의존관계 주입
    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;
    @Autowired EntityManager em;

    @Test
    public void 회원가입() throws Exception {
        //given : 주어졌을 때
        Member member = new Member();
        member.setName("Kim");

        //when : 이렇게 실행하면
        Long savedId = memberService.join(member);

        //then : 결과는 이렇게 나와야 한다.
       // em.flush(); // DB에 쿼리로 날리게 됨 ( insert문 나가는 것을 볼 수 있음 ) 이후에 rollback 하게됨
        Assertions.assertEquals(member, memberRepository.findOne(savedId));
    }

    @Test(expected = IllegalStateException.class)   // 해당 예외가 발생할 것으로 기대
    public void 중복_회원_예외() throws Exception {
        //given
        Member member1 = new Member();
        member1.setName("Kim");

        Member member2 = new Member();
        member2.setName("Kim");

        //when
        memberService.join(member1);
        memberService.join(member2);    // IllegalStateException 발생

        //then
        fail("예외가 발생해야 한다.");
    }

}