package com.example.multipleLogin;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

/**
 * created by Atiye Mousavi
 * Date: 10/3/2021
 * Time: 11:06 AM
 */

@Configuration
@EnableWebSecurity
public class MultipleLoginSecurityConfig {
    //در این کلاس یه نوع دسترسی تنظیم میشود یکی برای admin یکی برای user و یکی هم برای مهمان.کاربر مهمان نیاز بع تنظیم دسترسی ندارد

    @Bean
    public UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withUsername("user").password(encoder().encode("userPass")).roles("USER").build());
        manager.createUser(User.withUsername("admin").password(encoder().encode("adminPass")).roles("ADMIN").build());
        return manager;
    }

    @Bean
    public static PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Configuration
    //حاشیه نویسیOrder در هر کلاس استاتیک نشان می دهد که ترتیب پیکربندی برای پیدا کردن کدام یک با URL درخواستی مطابقت دارد. ارزش سفارش برای هر کلاس باید منحصر به فرد باشد.
    @Order(1)
    public static class App1ConfigurationAdapter extends WebSecurityConfigurerAdapter {
        //تنظیم دسترسی ادمین

        public App1ConfigurationAdapter() {
            super();
        }

        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.inMemoryAuthentication().withUser("admin").password(encoder().encode("admin")).roles("ADMIN");
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.antMatcher("/admin*").authorizeRequests().anyRequest().hasRole("ADMIN")
                    //log in
                    .and().formLogin().loginPage("/loginAdmin").loginProcessingUrl("/admin_login").failureUrl("/loginAdmin?error=loginError").defaultSuccessUrl("/adminPage")
                    //log out
                    .and().logout().logoutUrl("/admin_logout").logoutSuccessUrl("/protectedLinks").deleteCookies("JSESSIONID").and().exceptionHandling().accessDeniedPage("/403").and().csrf().disable();
        }
    }

    @Configuration
    @Order(2)
    public static class App2ConfigurationAdapter extends WebSecurityConfigurerAdapter {
        //تنظیم دسترسی یوزر
        public App2ConfigurationAdapter() {
            super();
        }

        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.inMemoryAuthentication().withUser("user").password(encoder().encode("user")).roles("USER");
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.antMatcher("/user*").authorizeRequests().anyRequest().hasRole("USER")
                    //log in
                    .and().formLogin().loginPage("/loginUser").loginProcessingUrl("/user_login").failureUrl("/loginUser?error=loginError").defaultSuccessUrl("/userPage")
                    // logout
                    .and().logout().logoutUrl("/user_logout").logoutSuccessUrl("/protectedLinks").deleteCookies("JSESSIONID").and().exceptionHandling().accessDeniedPage("/403").and().csrf().disable();
        }
    }
}
