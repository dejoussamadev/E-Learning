package com.tek_up.gestionStock.RestController;

import com.tek_up.gestionStock.entities.User;
import com.tek_up.gestionStock.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("api/auth")
public class AuthRestController {

    @Autowired
    private UserService userService;

    @PostMapping("login")
    public ResponseEntity<?> authenticateUser(@RequestBody User loginRequest) {
        return userService.login(loginRequest);
    }

    @PostMapping("register")
    public User register(@RequestBody User user){
        return userService.createUser(user);
    }
}
