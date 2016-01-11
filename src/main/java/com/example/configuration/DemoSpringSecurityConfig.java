package com.example.configuration;

import com.example.filters.CsrfParamToHeaderFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Dmitrij on 08.10.2015.
 */
@Configuration
public class DemoSpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    AuthenticationSuccessHandler authenticationSuccessHandler;
    @Autowired
    UserDetailsService userDetailsService;


    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http

                .authorizeRequests()

                .regexMatchers(HttpMethod.GET, "rating/place/[0-9]{0,}", "/place/[0-9]{0,}/liked/", "/rating/place/[0-9]{0,}")
                .hasRole("USER")

                .antMatchers(HttpMethod.GET, "/user/orders",
                        "/user/places")
                .hasRole("USER")

                .regexMatchers(HttpMethod.POST, "/menu/[0-9]{0,}/comment",
                        "/place/[0-9]{0,}/menu/[0-9]{0,}")
                .hasRole("USER")

                .regexMatchers(HttpMethod.POST, "/place/menu/[0-9]{0,}")
                .hasRole("OWNER")

                .antMatchers(HttpMethod.GET, "/newplace")
                .authenticated()

                .antMatchers(HttpMethod.POST, "/newplace")
                .authenticated()

                .antMatchers(HttpMethod.POST, "/user", "/registration")
                .permitAll()

                .antMatchers(HttpMethod.GET, "/resend", "/page/login", "/registration", "/place/")
                .permitAll();

        http.requiresChannel()
                .antMatchers(HttpMethod.POST, "/user")
                .requiresSecure();

        http
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/");

        http
                .rememberMe()
                .key("remember");

        http
                .formLogin()
                .loginPage("/")
                .loginProcessingUrl("/login")
                .usernameParameter("username")
                .passwordParameter("password");

        http.
                userDetailsService(userDetailsService);


        http
                .addFilterAfter(new CsrfParamToHeaderFilter(), CsrfFilter.class)
                .csrf()
                .csrfTokenRepository(csrfTokenRepository());
    }

    private CsrfTokenRepository csrfTokenRepository() {
        HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
        repository.setHeaderName("X-XSRF-TOKEN");
        return repository;
    }


}
