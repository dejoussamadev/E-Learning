package com.tek_up.elearning.RestController;

import com.tek_up.elearning.entities.User;
import com.tek_up.elearning.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/users")
public class UserRestController {

    @Autowired
    UserService userService;


    // ADMIN
    @GetMapping("admin/getAllUsers")
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }

    @DeleteMapping("admin/deleteUser")
    public void deleteUser(Long id){
        userService.deleteUser(id);
    }

    // USER
    @PostMapping("user/createUser")
    public User createUser(@RequestBody User user){
        return userService.createUser(user);
    }

    @GetMapping("user/getUserById")
    public User getUserById(@RequestParam(name = "user_id") Long id){
        return userService.getUserById(id);
    }

    @PutMapping("user/updateUser")
    public User updateUser(@RequestParam(name = "user_id") Long userId, @RequestBody User user){
        return userService.updateUser(user, userId);
    }
}
