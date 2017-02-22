package com.tutoring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by himanshu.agarwal on 21-02-2017.
 */

@Controller
public class HomeController {

    @RequestMapping(value = {"/"}, method = RequestMethod.GET)
    public String home() {
        return "index";
    }
}
