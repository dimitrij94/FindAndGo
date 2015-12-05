package com.example.configuration;

import com.example.dao.DBBean;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheFactoryBean;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.view.JstlView;

import java.util.Locale;

/**
 * Created by Dmitrij on 08.10.2015.
 */

@Configuration
@EnableAutoConfiguration
@EnableTransactionManagement
@EnableWebMvc
@EnableCaching

@ComponentScan(basePackages = "com.example.*")

public class DemoJPAConfig extends WebMvcConfigurerAdapter {


    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        registry.jsp()
                .suffix(".jsp")
                .prefix("/WEB-INF/pages/")
                .viewClass(JstlView.class);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("/static/");
    }

    @Bean
    ConcurrentMapCacheFactoryBean cacheFactoryBean() {
        return new ConcurrentMapCacheFactoryBean();
    }

    @Bean
    CacheManager cacheManager() {
        return new ConcurrentMapCacheManager("maincahe");
    }

    @Bean
    MultipartResolver multipartResolver() {
        CommonsMultipartResolver resolver = new CommonsMultipartResolver();
        resolver.setMaxUploadSize(1000000);
        return resolver;
    }

    @Bean
    LocalContainerEntityManagerFactoryBean entityManagerFactory() {

        DriverManagerDataSource dataSource = new DriverManagerDataSource();

        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUsername("root");
        dataSource.setPassword("");
        dataSource.setUrl("jdbc:mysql://127.0.0.1:3306/myplacetogo");

        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
        factoryBean.setPersistenceUnitName("myplacetogo");
        factoryBean.setDataSource(dataSource);

        return factoryBean;
    }

    @Bean
    LocaleResolver localeResolver() {
        SessionLocaleResolver sessionLocaleResolver = new SessionLocaleResolver();
        sessionLocaleResolver.setDefaultLocale(Locale.US);
        return sessionLocaleResolver;
    }

    @Bean
    LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor interceptor = new LocaleChangeInterceptor();
        interceptor.setParamName("lang");
        return interceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
    }
}
