package com.rrt.rrtbackend.service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.rrt.rrtbackend.entity.authentication.AuthRequest;
import com.rrt.rrtbackend.entity.authentication.AuthenticatedDevice;
import com.rrt.rrtbackend.entity.authentication.ChangePassword;
import com.rrt.rrtbackend.entity.authentication.ForgotPassword;
import com.rrt.rrtbackend.entity.authentication.ResetPassword;
import com.rrt.rrtbackend.entity.user.User;
import com.rrt.rrtbackend.repository.AuthenticatedDeviceRepository;
import com.rrt.rrtbackend.repository.UserRepository;
import com.rrt.rrtbackend.utility.JwtUtil;

import jakarta.transaction.Transactional;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticatedDeviceRepository authenticatedDeviceRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private Map<String, String> otpStorage = new HashMap<>();

    @Autowired
    private JwtUtil jwtUtil;

    public ResponseEntity<String> register(AuthRequest authRequest) {
        try {
            Optional<User> existingUserOpt = userRepository.findByEmail(authRequest.getEmail());
            if (existingUserOpt.isPresent()) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already exists");
            }

            User user = new User();
            user.setEmail(authRequest.getEmail());
            user.setPassword(passwordEncoder.encode(authRequest.getPassword()));
            userRepository.save(user);

            String token = generateAuthResponse(user, authRequest.getDeviceId());
            return ResponseEntity.ok(token);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Registration failed: " + e.getMessage());
        }
    }

    public ResponseEntity<String> login(AuthRequest authRequest) {
        try {
            User user = userRepository.findByEmail(authRequest.getEmail()).orElse(null);
            if (user != null && passwordEncoder.matches(authRequest.getPassword(), user.getPassword())) {
                String token = generateAuthResponse(user, authRequest.getDeviceId());
                return ResponseEntity.ok(token);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Login failed: " + e.getMessage());
        }
    }

    @Transactional
    public void logout(String token) {
        authenticatedDeviceRepository.deleteByToken(token);
    }

    private String generateAuthResponse(User user, String deviceId) {
        String token = jwtUtil.generateToken(user.getId());
        AuthenticatedDevice device = new AuthenticatedDevice();
        device.setUser(user);
        device.setToken(token);
        device.setDevice(deviceId);
        device.setLoginTime(LocalDateTime.now());
        authenticatedDeviceRepository.save(device);
        return token;
    }

    public ResponseEntity<String> changePassword(String token, ChangePassword request) {
        Long userId = jwtUtil.extractUserId(token);
        User user = userRepository.findById(userId).orElse(null);
        if (user == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");

        if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Incorrect current password");
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
        return ResponseEntity.ok("Password changed successfully");
    }

    public ResponseEntity<String> forgotPassword(ForgotPassword request) {
        Optional<User> optionalUser = userRepository.findByEmail(request.getEmail());
        if (optionalUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        String otp = String.valueOf((int) (Math.random() * 9000 + 1000));
        otpStorage.put(request.getEmail(), otp);

        System.out.println("Generated OTP for " + request.getEmail() + ": " + otp);
        return ResponseEntity.ok("OTP sent to registered email (simulated)");
    }

    public ResponseEntity<String> resetPassword(ResetPassword request) {
        String storedOtp = otpStorage.get(request.getEmail());
        if (storedOtp == null || !storedOtp.equals(request.getOtp())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or expired OTP");
        }

        User user = userRepository.findByEmail(request.getEmail()).orElseThrow();
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);

        otpStorage.remove(request.getEmail()); // Clean up OTP
        return ResponseEntity.ok("Password reset successful");
    }

}
