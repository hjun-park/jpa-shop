package jpabook.jpashop;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)  // 나 스프링 부트와 관련된 테스트 할거야 라고 알려줌
@SpringBootTest  // 스프링 부트와 관련된 테스트
public class MemberRepositoryTest {

    @Autowired MemberRepository memberRepository;   // member repository를 자동으로 주입받음


    @Test
    @Transactional  // 트랜잭션 매니저가 없으면 테스트 실행되지 않음
    // @Rollback(false) // 트랜잭션이 테스트에 들어가 있으면 실행 후에 Rollback을 하기 때문에 DB가서 확인해도 아무것도 없음
    public void testMember() throws Exception {
        //given
        Member member = new Member();
        member.setUsername("memberA");

        // when
        Long saveId = memberRepository.save(member);    // Command + Alt + V => extract from function result
        Member findMember = memberRepository.find(saveId);

        //then
        Assertions.assertThat(findMember.getId()).isEqualTo(member.getId());
        Assertions.assertThat(findMember.getUsername()).isEqualTo(member.getUsername());
        Assertions.assertThat(findMember).isEqualTo(member); // 영속성 컨텍스트에 의해서 멤버 찾은 것과 저장하는 멤버의 객체는 같은 객체
    }
}