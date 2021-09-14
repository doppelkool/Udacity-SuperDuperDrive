package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/result")
public class ResultController {

    private final UserService userService;

    public ResultController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public String signupView() {
        return "result";
    }

    @PostMapping()
    public String signupUser(@ModelAttribute User user, Model model) {
        return "result";
    }
}
