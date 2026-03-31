package com.example.paymentmailintegration.controller;

import com.example.paymentmailintegration.entity.User;
import com.example.paymentmailintegration.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    // Show signup page
    @GetMapping("/signup")
    public String signupPage() {
        return "signup";
    }

    // Handle signup
    @PostMapping("/signup")
    public String signup(@RequestParam String email,
                         @RequestParam String password) {
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);

        userService.register(user);
        return "redirect:/login";
    }

    // Show login page
    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    // Handle login
    @PostMapping("/login")
    public String login(@RequestParam String email,
                        @RequestParam String password,
                        HttpSession session) {

        User user = userService.login(email, password);

        if (user != null) {
            session.setAttribute("userEmail", user.getEmail());
            return "redirect:/";
        }

        return "login";
    }

    // Logout
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

    @GetMapping("/cancel")
    public String cancelPage() {
        return "cancel"; // looks for cancel.html in templates
    }
}