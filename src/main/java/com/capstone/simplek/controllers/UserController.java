package com.capstone.simplek.controllers;

import com.capstone.simplek.User.User;
import com.capstone.simplek.User.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {
    private User user;
    @Autowired
    private UserRepository userDao;

    @GetMapping("/user/register")
    public String register(){
        return"user/registration";
    }

    @PostMapping("/user/register")
    public String saveUser(@ModelAttribute User user){
        userDao.save(user);
        return "redirect:user/login/registered";
    }
}
