package com.htw.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.htw.services.UserService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;


@Controller

public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("")
    public String listUser(Model model) {
        model.addAttribute("users", userService.getUser());
        return "user-list";
    }


    @GetMapping("/login")
    public String loginView(){
        return "login";
    }

}
