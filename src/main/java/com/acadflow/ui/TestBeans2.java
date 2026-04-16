package com.acadflow.ui;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import com.acadflow.module1.Module1Application;

public class TestBeans2 {
    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(Module1Application.class, args);
        String[] beans = ctx.getBeanDefinitionNames();
        for (String bean : beans) {
            if (bean.toLowerCase().contains("controller")) {
                System.out.println("FIND BEAN: " + bean);
            }
        }
        System.exit(0);
    }
}
