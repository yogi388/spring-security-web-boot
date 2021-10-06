package com.example.multipleAuthProviders;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * created by Atiye Mousavi
 * Date: 10/3/2021
 * Time: 10:15 AM
 */
@RestController
public class MultipleAuthController {

    @GetMapping("/api/ping")
    public String getPing(){
        return "Ok";
    }
}
