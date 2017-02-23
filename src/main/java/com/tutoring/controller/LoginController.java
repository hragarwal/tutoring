package com.tutoring.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tutoring.model.Profile;
import com.tutoring.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Created by himanshu.agarwal on 23-02-2017.
 */
@RestController
@RequestMapping("/api/login")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public Map<String,String> validateUser(@RequestBody Profile profile) throws JsonProcessingException{
        System.out.println("inside validateUser");
        return loginService.validateUser(profile);
    }
    
    
}
