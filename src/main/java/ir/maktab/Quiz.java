package ir.maktab;

import ir.maktab.model.entity.Course;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;


@SpringBootApplication

public class Quiz {
    public static void main(String[] args) {
        SpringApplication.run(ir.maktab.Quiz.class, args);

    }
}
