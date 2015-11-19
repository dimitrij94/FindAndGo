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

                .antMatchers(HttpMethod.POST, "/registration")
                .permitAll()

                .antMatchers(HttpMethod.GET, "/resend", "/page/login", "/registration", "/place/")
                .permitAll();

        http
                .logout()
                .logoutUrl("/logout")
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
