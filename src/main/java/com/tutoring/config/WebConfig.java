package com.tutoring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

/**
 * Created by himanshu.agarwal on 21-02-2017.
 */

@Configuration
@EnableWebMvc
public class WebConfig extends WebMvcConfigurerAdapter{

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        if (!registry.hasMappingForPattern("/static/**")) {
            registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        }
        if (!registry.hasMappingForPattern("/css/**")) {
            registry.addResourceHandler("/css/**").addResourceLocations("classpath:/static/css/");
        }
        if (!registry.hasMappingForPattern("/custom/**")) {
            registry.addResourceHandler("/custom/**").addResourceLocations("classpath:/static/custom/");
        }
        if (!registry.hasMappingForPattern("/images/**")) {
            registry.addResourceHandler("/images/**").addResourceLocations("classpath:/static/images/");
        }
        if (!registry.hasMappingForPattern("/js/**")) {
            registry.addResourceHandler("/js/**").addResourceLocations("classpath:/static/js/");
        }
        if (!registry.hasMappingForPattern("/views*")) {
            registry.addResourceHandler("/views/*").addResourceLocations("classpath:/static/views/");
        }

    }

    @Bean
    public InternalResourceViewResolver internalViewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/static/");
        viewResolver.setSuffix(".html");
        return viewResolver;
    }
}
