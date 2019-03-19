package com.capstone.simplek.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MapsController {
    @GetMapping("/test")
    public String transportation (){
        return "results";
    }
}
