package com.example.LogInRedirect;

import org.apache.http.HttpStatus;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.util.UrlPathHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * created by Atiye Mousavi
 * Date: 10/2/2021
 * Time: 6:41 PM
 */
public class LoginPageInterceptor implements HandlerInterceptor {
    //راه دیگر جهت تغییر مسیر کاربران ، interceptor در URI صفحه ورود است.

    //رهگیر درخواست را قبل از رسیدن به کنترل کننده رهگیری می کند. بنابراین ، ما می توانیم بر اساس احراز هویت تصمیم بگیریم که اجازه دهیم بیشتر پیش برود یا آن را مسدود کرده و یک پاسخ هدایت را بازگردانیم.

    //اگر کاربر احراز هویت شود ، باید در پاسخ دو مورد را تغییر دهیم:
    //Set the status code to HttpStatus.SC_TEMPORARY_REDIRECT
    //Add the Location header with the redirect URL
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        UrlPathHelper urlPathHelper=new UrlPathHelper();
        if ("/loginUser".equals(urlPathHelper.getLookupPathForRequest(request)) && isAuthentication()){
            String encodedRedirectURL=response.encodeRedirectURL(
                    request.getContextPath() + "/userMainPage");
            response.setStatus(HttpStatus.SC_TEMPORARY_REDIRECT);
            response.setHeader("Location",encodedRedirectURL);
            return false;
        }else {
            return true;
        }
    }

    private boolean isAuthentication(){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        if (authentication ==null || AnonymousAuthenticationToken.class.isAssignableFrom(authentication.getClass())){
            return false;
        }
        return authentication.isAuthenticated();
    }
}
