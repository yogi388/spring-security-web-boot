package com.example.LogInRedirect;

import org.apache.http.HttpStatus;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * created by Atiye Mousavi
 * Date: 10/2/2021
 * Time: 5:45 PM
 */
//برای استفاده از فیلتر ابتدا باید از کلاس GenericFilterBean ارث بری شود
public class LoginPageFilter extends GenericFilterBean {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        //در اینجا احراز هویت را تایید میکینم
        HttpServletRequest servletRequest= (HttpServletRequest) request;
        HttpServletResponse servletResponse= (HttpServletResponse) response;

        if(isAuthenticated() && "/loginUser".equals(servletRequest.getRequestURI())){
            String encodedRedirectURL=((HttpServletResponse) response).encodeRedirectURL(
                    servletRequest.getContextPath() + "/userMainPage");
            servletResponse.setStatus(HttpStatus.SC_TEMPORARY_REDIRECT);
            servletResponse.setHeader("Location",encodedRedirectURL);
        }
        filterChain.doFilter(servletRequest,servletResponse);
    }

    private boolean isAuthenticated(){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null || AnonymousAuthenticationToken.class.isAssignableFrom(authentication.getClass())){
            return false;
        }
        return authentication.isAuthenticated();

    }
}
