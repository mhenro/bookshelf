package ru.khadzhinov.bookshelf;

import java.util.Locale;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

@SpringBootApplication // same as @Configuration @EnableAutoConfiguration @ComponentScan
public class BookShelf extends WebMvcConfigurerAdapter {
    public static void main(String[] args) throws Exception {
    	SpringApplication app = new SpringApplication(BookShelf.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.run(args);
    }
    
    /* for i18n */ 
    @Bean
    public MessageSource messageSource() {
    	ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("locale/messages");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }
    @Bean
    public LocaleResolver localeResolver() {
    	SessionLocaleResolver slr = new SessionLocaleResolver();
    	slr.setDefaultLocale(Locale.US);
    	return slr;
    }    
    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
        lci.setParamName("lang");
        return lci;
    }    
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
    }
}
