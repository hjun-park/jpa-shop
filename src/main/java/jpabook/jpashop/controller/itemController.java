package jpabook.jpashop.controller;

import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class itemController {

    private final ItemService itemService;


    /*
        01. 상품 등록
     */

    @GetMapping("/items/new")
    public String createForm(Model model) {
        model.addAttribute("form", new BookForm());
        return "items/createItemForm";
    }

    @PostMapping("/items/new")
    public String create(BookForm form) {
        Book book = new Book();

        book.setName(form.getName());
        book.setPrice(form.getPrice());
        book.setStockQuantity(form.getStockQuantity());
        book.setAuthor(form.getAuthor());
        book.setIsbn(form.getIsbn());

        itemService.saveItem(book);
        return "redirect:/";
    }


    /*
        02. 상품 목록
     */
    @GetMapping("/items")
    public String list(Model model) {
        List<Item> items = itemService.findItems();
        model.addAttribute("items", items);

        return "items/itemList";
    }


    /*
        03. 상품 수정
     */
    @GetMapping("items/{itemId}/edit")
    public String updateItemForm(@PathVariable("itemId") Long itemId, Model model) {
        Book item = (Book) itemService.findOne(itemId);  // 예제 단순화 위해 캐스팅 사용

        BookForm form = new BookForm();     // update 하는데 Book 엔터티를 보내는 것이 아니라 BookForm 엔터티를 보낼 것 =
        form.setId(item.getId());
        form.setName(item.getName());
        form.setPrice(item.getPrice());
        form.setStockQuantity(item.getStockQuantity());
        form.setAuthor(item.getAuthor());
        form.setIsbn(item.getIsbn());

        model.addAttribute("form", form);
        return "items/updateItemForm";
    }

    @PostMapping("items/{itemId}/edit")
    public String updateItem(@PathVariable String itemId, @ModelAttribute("form") BookForm form) {
        // updateItemForm 7번째 줄에 Object "form"이 그대로 넘어와서 ModelAttribute 사용

        Book book = new Book();

        // 위에 updateItemForm 처럼 하나하나 입력하기 귀찮으니까 간단하게
        /*
            1. Command + Shift + 8  누르고 Shift 누른상태로 BookForm 에 있는 변수명만 복사  ( Option키 누르고 방향키 누르면 스마트하게 이동 )
            2. Command + Shift + U 눌러서 변수 앞자리만 대문자 만들기
         */

        book.setId(form.getId());
        book.setName(form.getName());
        book.setPrice(form.getPrice());
        book.setStockQuantity(form.getStockQuantity());
        book.setAuthor(form.getAuthor());
        book.setIsbn(form.getIsbn());

        itemService.saveItem(book); // saveitem => save => merge가 있는데 그거는 어떤 건지 ?
        return "redirect:/items";        // edit 하고나면 item 리스트로 리다이렉트

    }


}
