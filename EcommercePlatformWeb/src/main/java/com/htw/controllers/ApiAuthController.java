package com.htw.controllers;

import com.htw.pojo.User;
import com.htw.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class ApiAuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> loginRequest) {
        try {
            String username = loginRequest.get("username");
            String password = loginRequest.get("password");

            User user = userService.getUserByUsername(username);
            if (user == null) {
                return ResponseEntity.badRequest().body(Map.of("error", "Invalid username or password"));
            }

            // Kiểm tra password
            if (!userService.authenticate(username, password)) {
                return ResponseEntity.badRequest().body(Map.of("error", "Invalid username or password"));
            }

            // Tạo response
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Login successful");
            response.put("user", Map.of(
                "id", user.getId(),
                "username", user.getUsername(),
                "fullName", user.getFullName(),
                "role", user.getRole(),
                "avatar", user.getAvatar()
            ));

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", "Login failed: " + e.getMessage()));
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String, String> registerRequest) {
        try {
            Map<String, String> params = new HashMap<>();
            params.put("username", registerRequest.get("username"));
            params.put("password", registerRequest.get("password"));
            params.put("fullname", registerRequest.get("fullName"));
            params.put("number_phone", registerRequest.get("phoneNumber"));

            User newUser = userService.addUser(params, null);

            Map<String, Object> response = new HashMap<>();
            response.put("message", "Registration successful");
            response.put("user", Map.of(
                "id", newUser.getId(),
                "username", newUser.getUsername(),
                "fullName", newUser.getFullName(),
                "role", newUser.getRole()
            ));

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", "Registration failed: " + e.getMessage()));
        }
    }

    @PostMapping("/register-seller")
    public ResponseEntity<?> registerSeller(@RequestBody Map<String, String> registerRequest) {
        try {
            Map<String, String> params = new HashMap<>();
            params.put("username", registerRequest.get("username"));
            params.put("password", registerRequest.get("password"));
            params.put("fullname", registerRequest.get("fullName"));
            params.put("number_phone", registerRequest.get("phoneNumber"));

            User newUser = userService.addUser(params, null);
            
            // Set role as SELLER (cần approval)
            newUser.setRole("ROLE_SELLER_PENDING");
            userService.updateUser(newUser);

            Map<String, Object> response = new HashMap<>();
            response.put("message", "Seller registration submitted for approval");
            response.put("user", Map.of(
                "id", newUser.getId(),
                "username", newUser.getUsername(),
                "fullName", newUser.getFullName(),
                "role", newUser.getRole()
            ));

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", "Registration failed: " + e.getMessage()));
        }
    }

    @GetMapping("/profile")
    public ResponseEntity<?> getProfile() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            
            User user = userService.getUserByUsername(username);
            if (user == null) {
                return ResponseEntity.badRequest().body(Map.of("error", "User not found"));
            }

            Map<String, Object> response = new HashMap<>();
            response.put("user", Map.of(
                "id", user.getId(),
                "username", user.getUsername(),
                "fullName", user.getFullName(),
                "role", user.getRole(),
                "avatar", user.getAvatar(),
                "phoneNumber", user.getNumberPhone(),
                "isVerified", user.getIsVerified()
            ));

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", "Failed to get profile: " + e.getMessage()));
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        try {
            SecurityContextHolder.clearContext();
            return ResponseEntity.ok(Map.of("message", "Logout successful"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", "Logout failed: " + e.getMessage()));
        }
    }
}