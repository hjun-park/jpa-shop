package jpabook.jpashop;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository // 컴포넌트 스캔 대상 어노테이션 중 하나 -> 즉, 자동으로 스프링 빈으로 등록
public class MemberRepository {

    @PersistenceContext // 자동으로 엔티티 매니저 생성 다 해줌
    private EntityManager em;

    public Long save(Member member) {   // Shift + Command + T = 테스트 생성
        em.persist(member);
        return member.getId();  // 저장은 리턴값이 굳이 필요없기도 하지만 다음에 다시 조회 할 때 유용히 쓸 수 있다는 장점
    }

    // 회원 조회
    public Member find(Long id) {
        return em.find(Member.class, id);
    }

}
