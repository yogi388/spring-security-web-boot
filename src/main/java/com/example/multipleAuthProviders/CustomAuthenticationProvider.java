package com.example.multipleAuthProviders;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

/**
 * created by Atiye Mousavi
 * Date: 10/3/2021
 * Time: 9:56 AM
 */
@Component
//برای پیاده سازی custom authentication باید اینترفیس AuthenticationProvider را پیاده سازی کنیم
public class CustomAuthenticationProvider implements AuthenticationProvider {
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        final String userName=authentication.getName();
        final String password=authentication.getCredentials().toString();

        if ("externaluser".equals(userName) && "pass".equals(password)){
            //اگر authentication به درستی انجام شود این را بر میگرداند
            return new UsernamePasswordAuthenticationToken(userName,password);
        }else {
            //اگر authentication بدرستی انجام نشود یعنی fail شود این خطا را برمیگرداند
            throw new BadCredentialsException("External system authentication failed");
        }
    }

    @Override
    public boolean supports(Class<?> auth) {
        return auth.equals(UsernamePasswordAuthenticationToken.class);
    }
}
