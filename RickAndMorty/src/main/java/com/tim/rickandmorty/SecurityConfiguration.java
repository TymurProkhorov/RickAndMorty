package com.tim.rickandmorty;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
                auth
                .inMemoryAuthentication()  //пароль будет сохраняться каждый раз, как апп стартует(удаляется при закрытии апп)
                .withUser("Admin").password(passwordEncoder().encode("Admin123")).roles("ADMIN")
                .and()
                .withUser("Rick").password(passwordEncoder().encode("Rick123")).roles("USER")
                .and()
                .withUser("Morty").password(passwordEncoder().encode("Morty123")).roles("USER");
    }

    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeRequests()
                .anyRequest().authenticated() //доступ только авторизованным юзерам.      Любой запрос
                .and()
                .httpBasic(); //используем базовую аут
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}