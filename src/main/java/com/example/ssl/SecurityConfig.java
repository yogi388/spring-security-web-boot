package com.example.ssl;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    //دو نوع keyStore داریم
    //PKCS12: Public Key Cryptographic Standards
    //برای این ساخت این keyStore از command زیر استفاده میشود
    //keytool -genkeypair -alias baeldung -keyalg RSA -keysize 2048 -storetype PKCS12 -keystore baeldung.p12 -validity 3650
    //ما توصیه می کنیم از فرمت PKCS12 استفاده کنید ، که یک قالب استاندارد صنعت است. بنابراین اگر ما در حال حاضر یک keystore JKS داریم ، می توانیم آن را با استفاده از دستور زیر به قالب PKCS12 تبدیل کنیم:
    //keytool -importkeystore -srckeystore baeldung.jks -destkeystore baeldung.p12 -deststoretype pkcs12




    //JKS: Java KeyStore
    //برای ساخت این keyStore از command زیر استفاده میشود
    //keytool -genkeypair -alias baeldung -keyalg RSA -keysize 2048 -keystore baeldung.jks -validity 3650

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/**")
                .permitAll();
    }
}
