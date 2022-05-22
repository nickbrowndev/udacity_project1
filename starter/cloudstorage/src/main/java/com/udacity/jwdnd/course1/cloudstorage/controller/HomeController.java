package com.udacity.jwdnd.course1.cloudstorage.controller;


import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Controller
@RequestMapping("/home")
public class HomeController {

    public HomeController() {
    }

    @GetMapping
    public String getHomePage(Model model) {
         return "home";
    }

    @PostMapping
    public String postChatMessage(Authentication authentication, Model model) {
        return "home";
    }
}

