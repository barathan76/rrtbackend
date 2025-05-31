package com.rrt.rrtbackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rrt.rrtbackend.entity.User;
import com.rrt.rrtbackend.entity.UserProfile;
import com.rrt.rrtbackend.service.UserService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/profile")
    public ResponseEntity<UserProfile> getUserProfile(HttpServletRequest request) {
        User user = userService.getUserFromRequest(request);
        return ResponseEntity.ok(userService.getUserProfile(user));
    }

    @PutMapping("/profile")
    public ResponseEntity<String> updateUserProfile(@RequestBody UserProfile profile,
            HttpServletRequest request) {
        User user = userService.getUserFromRequest(request);
        userService.updateUserProfile(user, profile);
        return ResponseEntity.ok("Profile updated");
    }
}
