package com.rrt.rrtbackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.rrt.rrtbackend.entity.User;
import com.rrt.rrtbackend.repository.UserRepository;



@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${jwt.secret}")
    private String jwtSecret;

    public ResponseEntity<String> register(User user) {
        try{
            User existingUser = userRepository.findByEmail(user.getEmail()).orElse(null);
            if(existingUser == null){
                user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return ResponseEntity.ok(user.getId()+"");
            }else{
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already exists");
            }
        }catch(Exception e){
           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Registration failed : "+e.getMessage());
        }   
    }

    public  ResponseEntity<String> login(User user){
         System.err.println(user);
    
         try{
            User exUser = userRepository.findByEmail(user.getEmail()).orElse(null);
            System.err.println(exUser);
            if(exUser!=null && passwordEncoder.matches(user.getPassword(), exUser.getPassword())){
                return ResponseEntity.ok(exUser.getId()+"");
            }else{
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Credentials");
            }
         }catch(Exception e){
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Login failed: " + e.getMessage());
         }
         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Login failed: ");
        
    }

    

    // private String generateToken(String email) {
    //     return Jwts.builder()
    //             .setSubject(email)
    //             .setIssuedAt(new Date())
    //             .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 1 hour
    //             .signWith(SignatureAlgorithm.HS512, jwtSecret)
    //             .compact();
    // }
}

