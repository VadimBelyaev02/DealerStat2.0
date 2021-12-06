package com.leverx.dealerstat.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class ApplicationInit extends AbstractAnnotationConfigDispatcherServletInitializer {
    // Этот метод должен содержать конфигурации которые инициализируют Beans
    // для инициализации бинов у нас использовалась аннотация @Bean
    @Override
    protected Class<?>[] getRootConfigClasses() {
//        return new Class<?>[]{
//                AppConfig.class
//        };
        return null;
    }

    // Тут добавляем конфигурацию, в которой инициализируем ViewResolver
    @Override
    protected Class<?>[] getServletConfigClasses() {

        return new Class<?>[]{
                AppConfig.class
        };
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }

}