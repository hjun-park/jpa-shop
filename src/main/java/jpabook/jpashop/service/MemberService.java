package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true) // JPA의 모든 데이터 변경이나 로직은 해당 어노테이션 내에서 실행 되어야 한다. 그래야 LAZY loading이 된다.
@RequiredArgsConstructor // final 붙은 것들에 대해서 자동으로 injection을 해줌 생성자 injection (아래 주석처리한 부분) 생략 가능
public class MemberService {

//    @Autowired  // 자동 주입 ( field injection )
    private final MemberRepository memberRepository;

    // 요즘 권하는 방법 : 생성자 injection
//    @Autowired  // 생성자가 하나만 있는 경우 안 써도 됨
//    public MemberService(MemberRepository memberRepository) {
//        this.memberRepository = memberRepository;
//    }



    // 우리가 만들 것들은 2가지 ( 가입과 조회 )

    /*
     회원 가입
    */
    @Transactional
    public Long join(Member member) {

        validateDuplicateMember(member);    // 중복 회원 검증
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        // EXCEPTION : 해당 멤버를 찾았는디 무언가 나온다면 중복되는 회원 -> 가입 불가
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }

    }

    // 회원 전체 조회  -  조회는 데이터 변경이 일어나지 않으므로 read only true 해도 된다.
//    @Transactional(readOnly = true) // JPA 조회 성능 최적화 ( dirty checking 제거 등등 )
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }

}
