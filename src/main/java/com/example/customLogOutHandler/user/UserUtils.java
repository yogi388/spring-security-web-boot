package com.example.customLogOutHandler.user;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * created by Atiye Mousavi
 * Date: 10/1/2021
 * Time: 5:40 PM
 */
public class UserUtils {
    public static String getAuthenticatedUserName() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth != null ? ((org.springframework.security.core.userdetails.User) auth.getPrincipal()).getUsername() : null;
    }
}
