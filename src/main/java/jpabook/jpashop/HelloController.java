package jpabook.jpashop;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {

    @GetMapping("hello")    // 컨트롤러에서 데이터를 뷰로 넘길 수 있음
    public String hello(Model model) {
        model.addAttribute("data", "hello!!!"); // name이 data라는 키에 hello!!!라는 값을 넘김
        return "hello";     // return : 화면 이름 ( resources/template ) // 렌더링 하면서 hello.html로 이동
    }

}
