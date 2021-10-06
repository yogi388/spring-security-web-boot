package com.example.multipleLogin;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

/**
 * created by Atiye Mousavi
 * Date: 10/3/2021
 * Time: 10:59 AM
 */

@EnableWebMvc
@Configuration
public class MultipleLoginMvcConfig implements WebMvcConfigurer {

    public MultipleLoginMvcConfig() {
        super();
    }

    @Override
    public void addViewControllers(final ViewControllerRegistry registry) {
        registry.addViewController("/anonymousa.html");
        registry.addViewController("/login.html");
        registry.addViewController("/homepage.html");
        registry.addViewController("/console.html");

    }
    @Bean
    public ViewResolver viewResolver(){
        final InternalResourceViewResolver bean=new InternalResourceViewResolver();
        bean.setViewClass(JstlView.class);
        bean.setPrefix("/WEB-INF/view/");
        bean.setSuffix(".jsp");

        return bean;
    }
}
