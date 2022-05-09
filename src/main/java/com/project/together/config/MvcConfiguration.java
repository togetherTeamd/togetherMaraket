package com.project.together.config;

import org.hibernate.resource.jdbc.ResourceRegistry;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
@EnableWebMvc
public class MvcConfiguration implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler("/**")
                .addResourceLocations("file:src/main/resources/templates/", "file:src/main/resources/static/");
    }

    public void addViewControllers(ViewControllerRegistry registry) {
        //registry.addViewController("/admin").setViewName("admin/adminHome");
        /*registry.addViewController("/").setViewName("home");
        registry.addViewController("/user").setViewName("loginHome");*/
    }
}
