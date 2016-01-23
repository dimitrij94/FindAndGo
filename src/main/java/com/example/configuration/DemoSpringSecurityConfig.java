package com.example.configuration;

import com.example.filters.CsrfParamToHeaderFilter;
import com.example.handlers.MySimpleUrlAuthenticationSuccessHendler;
import com.example.services.authorization.EmployeeDetailServiceImpl;
import com.example.services.authorization.OwnerDetailsService;
import com.example.services.authorization.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;

/**
 * Created by Dmitrij on 08.10.2015.
 */
@Configuration
public class DemoSpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(ownerAuthenticationProvider());
        auth.authenticationProvider(userAuthenticationProvider());
        auth.authenticationProvider(employeeAuthenticationProvider());
    }



    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.httpBasic()

                .and().sessionManagement()
                .maximumSessions(1).and()
                .sessionFixation().changeSessionId()

                .and().logout()

                .and().rememberMe()
                .key("remember")

                .and().addFilterAfter(new CsrfParamToHeaderFilter(), CsrfFilter.class)
                .csrf()
                .csrfTokenRepository(csrfTokenRepository())

                .and().authorizeRequests()

                .regexMatchers(HttpMethod.GET, "rating/place/[0-9]{0,}", "/place/[0-9]{0,}/liked/", "/rating/place/[0-9]{0,}")
                .hasRole("USER")

                .antMatchers(HttpMethod.GET, "/user", "/user/orders", "/user/places")
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

                .antMatchers(HttpMethod.GET, "/user/test", "/resend", "/", "/registration", "/place/")
                .permitAll();

    }


    @Bean
    public OwnerDetailsService ownerDetailsService() {
        return new OwnerDetailsService();
    }

    @Bean
    public EmployeeDetailServiceImpl employeeDetailService() {
        return new EmployeeDetailServiceImpl();
    }

    @Bean
    public UserDetailsServiceImpl userDetailsService() {
        return new UserDetailsServiceImpl();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {return new MySimpleUrlAuthenticationSuccessHendler();}


    private AuthenticationProvider userAuthenticationProvider() {

        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService());
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    private AuthenticationProvider ownerAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(ownerDetailsService());
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    private AuthenticationProvider employeeAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(employeeDetailService());
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    private CsrfTokenRepository csrfTokenRepository() {
        HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
        repository.setHeaderName("X-XSRF-TOKEN");
        return repository;
    }


}
