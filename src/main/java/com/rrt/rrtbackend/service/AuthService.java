package com.rrt.rrtbackend.service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.rrt.rrtbackend.entity.AuthRequest;
import com.rrt.rrtbackend.entity.AuthenticatedDevice;
import com.rrt.rrtbackend.entity.User;
import com.rrt.rrtbackend.repository.AuthenticatedDeviceRepository;
import com.rrt.rrtbackend.repository.UserRepository;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.transaction.Transactional;



@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticatedDeviceRepository authenticatedDeviceRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${jwt.secret}")
    private String jwtSecret;

    public ResponseEntity<String> register(AuthRequest authRequest) {
        try{
            User existingUser = userRepository.findByEmail(authRequest.getEmail()).orElse(null);
            if(existingUser == null){
                User user = new User();
                user.setEmail(authRequest.getEmail());

                user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return ResponseEntity.ok(generateAuthResponse(user,authRequest.getDeviceId()));
            }else{
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already exists");
            }
        }catch(Exception e){
           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Registration failed : "+e.getMessage());
        }   
    }
    @Transactional
    public void logout(String token){
        authenticatedDeviceRepository.deleteByToken(token);
    }

    public  ResponseEntity<String> login(AuthRequest authRequest){
    
         try{
            User exUser = userRepository.findByEmail(authRequest.getEmail()).orElse(null);
            if(exUser!=null && passwordEncoder.matches(authRequest.getPassword(), exUser.getPassword())){
                return ResponseEntity.ok(generateAuthResponse(exUser,authRequest.getDeviceId()));
            }else{
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Credentials");
            }
         }catch(Exception e){
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Login failed: " + e.getMessage());
         }
         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Login failed: ");
        
    }

    private String generateAuthResponse(User user, String deviceId){
        String token =  UUID.randomUUID().toString();
        // String token = generateToken(user.getEmail());
        AuthenticatedDevice device = new AuthenticatedDevice();
        device.setUser(user);
        device.setToken(token);
        device.setDevice(deviceId);
        device.setLoginTime(LocalDateTime.now());
        authenticatedDeviceRepository.save(device);
        return token;

    }

    

    private String generateToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())// 1 hour
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }
}

