package com.hust.admin.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class WebSecurityConfig {
    @Bean
    public UserDetailsService userDetailsService() {
        return new ShopmeUserDetailsService();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    protected SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/login")
                                .permitAll()
                                .requestMatchers("/users/**", "/settings/**", "/countries/**", "/states/**", "/categories/**", "/brands/**").hasAuthority("Admin")
                                .requestMatchers("/categories/**", "/brands/**").hasAnyAuthority("Admin", "Salesperson")
                                .requestMatchers("/products/new", "/products/delete/**").hasAnyAuthority("Admin", "Salesperson")
                                .requestMatchers("/products/edit/**", "/products/save", "/products/check_unique")
                                .hasAnyAuthority("Admin", "Salesperson")
                                .requestMatchers("/products", "/products/", "/products/detail/**", "/products/page/**")
                                .hasAnyAuthority("Admin", "Salesperson")
                                .requestMatchers("/products/**").hasAnyAuthority("Admin", "Salesperson")
                                .requestMatchers("/customers/**", "/orders/**", "/get_shipping_cost" ).hasAnyAuthority("Admin", "Salesperson")
                                .anyRequest().authenticated()
                )
                .formLogin(form -> form.loginPage("/login").usernameParameter("email"))
                .rememberMe(rememberMe -> rememberMe.key("uniqueAndSecret").tokenValiditySeconds(7*24*60*60));

        http.authenticationProvider(authenticationProvider());
        http.headers(headers -> headers.frameOptions(frameOptions -> frameOptions.sameOrigin()));

        return http.build();
    }
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers("/images/**", "/js/**", "/webjars/**");
    }
}


