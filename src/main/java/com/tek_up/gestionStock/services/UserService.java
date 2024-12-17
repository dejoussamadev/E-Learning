package com.tek_up.gestionStock.services;

import com.tek_up.gestionStock.dao.UserRepository;
import com.tek_up.gestionStock.entities.Cours;
import com.tek_up.gestionStock.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public User loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email).orElseThrow(()->new UsernameNotFoundException(String.format("User not found", email)));
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.getReferenceById(id);
    }

    public List<User> getUsersByName(String keyword) {
        return userRepository.getUsersByName(keyword);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public User updateUser(User user, Long id) {
        if(!userRepository.existsById(id)) {
            throw new IllegalArgumentException("User not found");
        }
        User userToUpdate = userRepository.getReferenceById(id);

        userToUpdate.setFirstName(user.getFirstName());
        userToUpdate.setLastName(user.getLastName());
        userToUpdate.setEmail(user.getEmail());
        userToUpdate.setBirthDate(user.getBirthDate());

        return userRepository.save(userToUpdate);
    }
}
