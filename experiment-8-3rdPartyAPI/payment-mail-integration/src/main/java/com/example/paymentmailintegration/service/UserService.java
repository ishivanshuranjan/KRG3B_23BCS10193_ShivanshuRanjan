package com.example.paymentmailintegration.service;

import com.example.paymentmailintegration.entity.User;
import com.example.paymentmailintegration.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Signup
    public User register(User user) {
        return userRepository.save(user);
    }

    // Login
    public User login(String email, String password) {
        Optional<User> user = userRepository.findByEmail(email);

        if (user.isPresent() && user.get().getPassword().equals(password)) {
            return user.get();
        }
        return null;
    }
}