package com.Bee.Store.BeeStore.service;

import com.Bee.Store.BeeStore.dto.SignupRequest;
import com.Bee.Store.BeeStore.dto.LoginRequest;
import com.Bee.Store.BeeStore.model.User;
import com.Bee.Store.BeeStore.repository.UserRepository;
import com.Bee.Store.BeeStore.security.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtils jwtUtils;

    public String signup(SignupRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email is already in use.");
        }

        User user = new User();
        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        userRepository.save(user);

        return "User registered successfully!";
    }

    public String login(LoginRequest request) {
        Optional<User> userOpt = userRepository.findByEmail(request.getEmail());
        if (userOpt.isPresent() && passwordEncoder.matches(request.getPassword(), userOpt.get().getPassword())) {
            return jwtUtils.generateJwtToken(request.getEmail());
        } else {
            throw new RuntimeException("Invalid email or password.");
        }
    }
}
