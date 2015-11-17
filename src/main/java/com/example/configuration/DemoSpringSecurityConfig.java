package com.example.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

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

        http.authorizeRequests()


                .antMatchers(HttpMethod.GET,"rating/place/{pId}","/place/{id}/liked/", "/user/orders",
                        "/user/places", "/rating/place/{pId}")
                .hasRole("USER")

                .antMatchers(HttpMethod.POST,"/menu/{id}/comment",
                        "/place/{placeId}/menu/{menuId}")
                .hasRole("USER")

                .antMatchers(HttpMethod.POST, "/place/{placeId}/menu/service", "/place/{id}/menu")
                .hasRole("OWNER")

                .antMatchers(HttpMethod.GET, "/newplace")
                .authenticated()

                .antMatchers(HttpMethod.POST, "/newplace")
                .authenticated()

                .antMatchers(HttpMethod.POST, "/registration")
                .permitAll()

                .antMatchers(HttpMethod.GET, "/resend", "/page/login", "/registration", "/place/")
                .permitAll();

        http
                .logout()
                .deleteCookies("JSESSIONID")
                .logoutSuccessUrl("/");

        http
                .rememberMe()
                .key("rememberme");

        http
                .formLogin()
                .loginPage("/page/login")
                .failureUrl("/page/login")
                .loginProcessingUrl("/login")
                .usernameParameter("j_username")
                .passwordParameter("j_password")
                .defaultSuccessUrl("/");

        http.
                userDetailsService(userDetailsService);


        http.
                csrf().disable();

    }

}
