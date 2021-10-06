package com.example.customLogOutHandler.web;

import com.example.customLogOutHandler.services.UserCache;
import com.example.customLogOutHandler.user.User;
import com.example.customLogOutHandler.user.UserUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * created by Atiye Mousavi
 * Date: 10/2/2021
 * Time: 12:59 PM
 */
@Controller
@RequestMapping(path = "/user")
public class UserController {

    private final UserCache userCache;

    public UserController(UserCache userCache) {
        this.userCache = userCache;
    }

    @GetMapping(path = "/language")
    @ResponseBody
    public String getLanguage(){
        String userName= UserUtils.getAuthenticatedUserName();
        User user=userCache.getByUserName(userName);
        return user.getLanguage();
    }

}
