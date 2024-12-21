package com.tek_up.elearning.services;

import com.tek_up.elearning.config.JwtResponse;
import com.tek_up.elearning.config.jwt.JwtUtils;
import com.tek_up.elearning.dao.UserRepository;
import com.tek_up.elearning.entities.User;
import com.tek_up.elearning.enums.Roles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

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
        User userToCreate = new User();
        userToCreate.setFirstName(user.getFirstName());
        userToCreate.setLastName(user.getLastName());
        userToCreate.setEmail(user.getEmail());
        userToCreate.setIsEnabled(true);
        userToCreate.setRole(Roles.STUDENT);
        userToCreate.setPassword(passwordEncoder.encode(user.getPassword()));
        userToCreate.setCreatedAt(new Date());
        userToCreate.setBirthDate(user.getBirthDate());

        return userRepository.save(userToCreate);
    }

    public User updateUser(User user, Long id) {
        try {
            if(!userRepository.existsById(id)) {
                throw new IllegalArgumentException("User not found");
            }
            User userToUpdate = userRepository.getReferenceById(id);

            userToUpdate.setFirstName(user.getFirstName());
            userToUpdate.setLastName(user.getLastName());
            userToUpdate.setEmail(user.getEmail());
            userToUpdate.setBirthDate(user.getBirthDate());

            return userRepository.save(userToUpdate);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error:" + e.getMessage());
        }
    }

        public ResponseEntity<?> login(User loginRequest) {
            try {
                System.out.println(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
                Authentication authentication = authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
                );
                System.out.println("ffffff");

                SecurityContextHolder.getContext().setAuthentication(authentication);

                String jwt = jwtUtils.generateJwtToken(authentication);

                User userDetails = (User) authentication.getPrincipal();

                List<String> roles = userDetails.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .toList();

                return ResponseEntity.ok(new JwtResponse(jwt));
            } catch (Exception e) {
                return ResponseEntity.status(401).body(Map.of("error", "Authentication failed: " + e.getMessage()));
            }
        }

    public List<User> generateUsers(List<User> users) {
        users.forEach(this::createUser);
        return users;
    }
}