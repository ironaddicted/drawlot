package com.avoinovan.drawlot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author by avoinovan
 */
@Controller
@RequestMapping("/register")
public class RegisterController {

    @RequestMapping(method = RequestMethod.GET)
    public String register() {
        return "register";
    }
}
