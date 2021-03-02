package jpabook.jpashop;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// lombok 테스트
@Getter @Setter
public class Hello {
    private String data;
}