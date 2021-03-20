package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

// 컴포넌트 스캔에 의해서 자동으로 스프링 빈에 등록
@Repository
@RequiredArgsConstructor    // final에 붙은 것에 대해 자동으로 생성자 injection을 해줌, 스프링 데이터 JPA만 지원하는 기능
public class MemberRepository {

    // JPA를 사용하기 때문에 JPA가 제공하는 표준 Annotation 사용
    // 스프링이 엔티티 매니저 만들어서 em에 주입
    //  @PersistenceContext // @RequiredArgsConstructor가 있으면 생략 가능 ( 알아서 생성자 의존관계 injection을 해줌 )
    private final EntityManager em;


    public void save(Member member) {
        em.persist(member); // jpa가 member를 저장
    }

    // 하나만 조회
    public Member findOne(Long id) {
        return em.find(Member.class, id);
    }

    // 리스트로 조회
    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();// (쿼리, 반환타입)
    }

    // 이름으로 조회
    public List<Member> findByName(String name) {
        return em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
    }
}
