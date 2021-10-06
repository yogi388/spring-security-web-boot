package com.example.LogInRedirect;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * created by Atiye Mousavi
 * Date: 10/3/2021
 * Time: 9:35 AM
 */
@Configuration
@EnableWebSecurity
public class LoginRedirectSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("user").password(encoder().encode("user")).roles("USER");
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        //همچنین باید کلاس UsernamePasswordAuthenticationFilter در فیلتر اد کنیم

        http.addFilterAfter(new LoginPageFilter(), UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers("/loginUser").permitAll()
                .antMatchers("/user*").hasRole("USER")
                .and()
                .formLogin()
                .loginPage("/loginUser")
                .loginProcessingUrl("/user_login")
                .failureUrl("/loginUser?error=loginError")
                .defaultSuccessUrl("/userMainPage").permitAll()
                .and()
                .logout()
                .logoutUrl("/user_logout")
                .logoutSuccessUrl("/loginUser")
                .deleteCookies("JSESSIONID")
                .and()
                .csrf().disable();

    }
    public static PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

}
