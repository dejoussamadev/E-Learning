package com.tek_up.gestionStock.RestController;

import com.tek_up.gestionStock.entities.Cours;
import com.tek_up.gestionStock.entities.User;
import com.tek_up.gestionStock.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserRestController {

    @Autowired
    UserService userService;

    @GetMapping("getAllUsers")
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }

    @GetMapping("getUserById")
    public User getUserById(Long id){
        return userService.getUserById(id);
    }

    @DeleteMapping("deleteUser")
    public void deleteUser(Long id){
        userService.deleteUser(id);
    }

    @PostMapping("createUser")
    public User createUser(@RequestBody User user){
        return userService.createUser(user);
    }

    @PutMapping("updateUser")
    public User updateUser(@RequestBody User user, Long id){
        return userService.updateUser(user, id);
    }
}
