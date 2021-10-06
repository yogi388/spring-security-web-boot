package com.example.LogInRedirect;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * created by Atiye Mousavi
 * Date: 10/3/2021
 * Time: 9:52 AM
 */
@Controller
public class UsersController {

    @GetMapping("/userMainPage")
    public String getUserPage(){
        return "userMainPage";
    }
    @GetMapping("/loginUser")
    public String getUserLoginPage(){
        if (isAuthenticated()){
            return "redirect:userMainPage";
        }
        return "loginUser";
    }
    private boolean isAuthenticated() {
        //به عبارت دیگر ، ما باید اطلاعات احراز هویت را از SecurityContext دریافت کرده و بررسی کنیم که آیا کاربر وارد سیستم شده است:
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || AnonymousAuthenticationToken.class.isAssignableFrom(authentication.getClass())) {
            return false;
        }
        return authentication.isAuthenticated();
    }
}
