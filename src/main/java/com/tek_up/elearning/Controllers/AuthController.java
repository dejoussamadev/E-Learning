package com.tek_up.elearning.Controllers;

import com.tek_up.elearning.entities.User;
import com.tek_up.elearning.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/auth/login", method = RequestMethod.GET)
    public String showLoginPage(Model model) {
        model.addAttribute("loginRequest", new User()); // Initialize a blank User object
        return "login"; // Returns the login.html page
    }

    @RequestMapping("/authlogin")
    public String login() {
        return "login";
    }



    @RequestMapping("auth/register")
    public String register(@RequestBody User user){
        return "views/register";
    }
}
