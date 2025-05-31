package com.rrt.rrtbackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rrt.rrtbackend.entity.User;
import com.rrt.rrtbackend.entity.UserProfile;
import com.rrt.rrtbackend.repository.UserRepository;
import com.rrt.rrtbackend.utility.JwtUtil;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class UserService {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

    public User getUserFromRequest(HttpServletRequest request) {
        String token = request.getHeader("Authorization").substring(7);
        Long userId = jwtUtil.extractUserId(token);
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public UserProfile getUserProfile(User user) {
        return new UserProfile(user);
    }

    public void updateUserProfile(User user, UserProfile profile) {
        user.setFirstName(profile.getFirstName());
        user.setLastName(profile.getLastName());
        user.setMobileNumber(profile.getMobileNumber());
        userRepository.save(user);
    }
}
