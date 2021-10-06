package com.example.jdbcAuthentication.h2.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * created by Atiye Mousavi
 * Date: 10/2/2021
 * Time: 4:13 PM
 */
@RestController
@RequestMapping("/principal")
public class UserController {

    @GetMapping
    public Principal retrievePrincipal(Principal principal){
        return principal;
    }
}
