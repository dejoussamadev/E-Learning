package com.tek_up.elearning.Controllers;

import com.tek_up.elearning.entities.User;
import com.tek_up.elearning.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("api/users")
public class UserController {

    @Autowired
    UserService userService;
}
