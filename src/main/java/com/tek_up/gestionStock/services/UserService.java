package com.tek_up.gestionStock.services;

import com.tek_up.gestionStock.config.JwtResponse;
import com.tek_up.gestionStock.config.jwt.JwtUtils;
import com.tek_up.gestionStock.dao.UserRepository;
import com.tek_up.gestionStock.entities.User;
import com.tek_up.gestionStock.enums.Roles;
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

        public ResponseEntity<?> login(User loginRequest) {
            try {
                // Authenticate the user
                Authentication authentication = authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
                );

                // Set authentication in the security context
                SecurityContextHolder.getContext().setAuthentication(authentication);

                // Generate JWT token
                String jwt = jwtUtils.generateJwtToken(authentication);

                // Extract user details from the authentication object
                User userDetails = (User) authentication.getPrincipal();

                // Map roles from authorities
                List<String> roles = userDetails.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .toList();

                // Return the JWT token in the response
                return ResponseEntity.ok(new JwtResponse(jwt));
            } catch (Exception e) {
                return ResponseEntity.status(401).body(Map.of("error", "Authentication failed: " + e.getMessage()));
            }
        }
}
