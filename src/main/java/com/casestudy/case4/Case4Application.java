package com.casestudy.case4;

import com.casestudy.case4.model.fomatter.FormatterLocalDate;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
//import org.springframework.security.crypto.password.PasswordEncoder;


@SpringBootApplication
public class Case4Application {

    public static void main(String[] args) {
        SpringApplication.run(Case4Application.class, args);
    }
    @Configuration
    static class MyConfig extends WebMvcConfigurerAdapter {
        @Override
        public void addFormatters(FormatterRegistry registry) {
//            FormatterLocalDate formatterLocalDate = new FormatterLocalDate("yyyy-MM-dd");
            registry.addFormatter(new FormatterLocalDate("yyyy-MM-dd"));
        }
    }
}
