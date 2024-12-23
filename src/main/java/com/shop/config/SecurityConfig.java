package com.shop.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@Log4j2
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        log.info("--------filterChain-------");

        // permitAll() 인증 없이 해당 경로 접근가능 / hasRole() 권한이 있는 자만 접근가능 하도록 URL설정
        http
                .authorizeHttpRequests(config->config
                        .requestMatchers("/css/**","/js/**","/img/**").permitAll()
                        .requestMatchers("/","/members/**","/item/**","/images/**").permitAll()
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                );

        http
                .formLogin(config->
                        config.loginPage("/members/login")
                        .defaultSuccessUrl("/")
                        .usernameParameter("email")     //로그인화면에서 name=username 이면 생략가능 --> name=email
                        .failureUrl("/members/login/error")
                )
                .logout(logout->
                        logout.logoutRequestMatcher(new AntPathRequestMatcher("/members/logout"))
                        .logoutSuccessUrl("/")
                );


        //        http.csrf().disable();

        //3.0이상 부터는 람다식으로 작성해야함.
        http
                .csrf(config->config.disable());
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
