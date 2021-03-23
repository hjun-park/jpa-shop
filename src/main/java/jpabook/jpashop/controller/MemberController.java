package jpabook.jpashop.controller;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor    // 생성자
public class MemberController {

    private final MemberService memberService;

    /*
         01. 회원 등록
     */

    // memberForm 컨트롤러에서 뷰 넘어갈 때 new MemberForm() 데이터를 실어서 넘겨준다. ( 도메인 )
    // model addattribute통해서 memberForm 이라는 걸 넘겼기 때문에 화면에서는 MemberForm() 객채에 접근이 가능하다.
    //   => createMemberForm 에서 MemberForm에 있는 변수와 매핑해는 것을 볼 수 있다.
    //  JPA 활용 1강좌 -> 웹 계층 개발 : 회원 등록 파트 확인 영상 보기( 6분부터 )
    @GetMapping("/members/new")     // GET 방식
    public String createForm(Model model) {
        model.addAttribute("memberForm", new MemberForm());
        return "members/createMemberForm";
    }

    @PostMapping("/members/new")        // POST 방식
    public String create(@Valid MemberForm form, BindingResult result) {  // Valid를 붙이게 되면 MemberForm안에 있는 조건들(not null)있으면 적용해준다.
                                                                          // BindingResult는 오류가 담겨서 실행이 된다.
        // 에러가 있다면,
        if(result.hasErrors()) {
            return "members/createMemberForm";  // 이렇게 하면 에러가 떠도 입력한 내용은 그대로 유지된다.
        }

        Address address = new Address(form.getCity(), form.getStreet(), form.getZipcode());

        Member member = new Member();
        member.setName(form.getName());
        member.setAddress(address);

        memberService.join(member);
        return "redirect:/";        // 등록 후 home으로 이동
    }




    /*
        02. 회원 목록 조회
     */
    @GetMapping("/members")
    public String list(Model model) {
        // Command + Option + N 해서 refactoring 하면 짧은 코드 완성
        model.addAttribute("members", memberService.findMembers());

        return "members/memberList";
    }


}
