package com.example.customLogOutHandler.web;

import com.example.customLogOutHandler.services.UserCache;
import com.example.customLogOutHandler.user.UserUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * created by Atiye Mousavi
 * Date: 10/1/2021
 * Time: 5:51 PM
 */
@Service
public class CustomLogoutHandler implements LogoutHandler {

    private final UserCache userCache;

    public CustomLogoutHandler(UserCache userCache){
        this.userCache=userCache;
    }
    @Override
    public void logout(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) {
        String username= UserUtils.getAuthenticatedUserName();
        userCache.evictuser(username);
    }
}
