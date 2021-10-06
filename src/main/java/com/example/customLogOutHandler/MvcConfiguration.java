package com.example.customLogOutHandler;

import com.example.customLogOutHandler.web.CustomLogoutHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

import javax.sql.DataSource;

/**
 * created by Atiye Mousavi
 * Date: 10/2/2021
 * Time: 3:17 PM
 */
@Configuration
@EnableWebSecurity
//یکی از راه های استفاده از multiple security realm پیاده سازی کلاس  WebSecurityConfigurerAdapter است در کلاسی که Configuration@ دارد
public class MvcConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private CustomLogoutHandler logoutHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //این از طریق basic authentication برای کانفیگ کردن استفاده میکنه
        http
                .httpBasic()
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET,"/user/**")
                .hasRole("USER")
                .and()
                .logout()
                .logoutUrl("/user/logout")
                //قسمت مهمی که در پیکربندی بالا باید به آن توجه کنید ، روش addLogoutHandler است. در پایان پردازش خروج ، CustomLogoutHandler خود را منتقل و فعال می کنیم. تنظیمات باقی مانده HTTP Basic Auth را دقیق تنظیم می کند.
                .addLogoutHandler(logoutHandler)
                .logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler())
                .permitAll()
                .and()
                .csrf()
                .disable()
                .formLogin()
                .disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery("select login, password, true from users where login=?")
                .authoritiesByUsernameQuery("select login, role from users where login=?");
    }
}
