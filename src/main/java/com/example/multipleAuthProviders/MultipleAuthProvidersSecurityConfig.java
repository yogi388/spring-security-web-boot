package com.example.multipleAuthProviders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * created by Atiye Mousavi
 * Date: 10/3/2021
 * Time: 10:22 AM
 */
@EnableWebSecurity
public class MultipleAuthProvidersSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    CustomAuthenticationProvider customAuthProvider;

    @Override
    //برای کانفیگ کردن authentication باید از AuthenticationManagerBuilder استفاده کنیم
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //تنظیمات authentication برای حالت memory
        auth.authenticationProvider(customAuthProvider);

        auth.inMemoryAuthentication()
                .withUser("memuser")
                .password(passwordEncoder().encode("pass"))
                .roles("USER");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
       http.httpBasic()
               .and()
               .authorizeRequests()
               .antMatchers("/api/**")//در اینجا داریم سطح دسترسی مشخص میکنیم که حتما شامل آدرس api باشد
               .authenticated();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
