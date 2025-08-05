package com.htw.controllers;

import com.htw.pojo.User;
import com.htw.services.UserService;
import com.htw.utils.JwtUtils;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class ApiUserController {

    @Autowired
    private UserService userService;

    @PostMapping(path = "/users",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> create(@RequestParam Map<String, String> params,
            @RequestParam(value = "avatar") MultipartFile avatar) {
        System.err.println("Data gui len: " + params);
        return new ResponseEntity<>(this.userService.addUser(params, avatar), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User u) {
        if (this.userService.authenticate(u.getUsername(), u.getPassword())) {
            try {
                String token = JwtUtils.generateToken(u.getUsername());
                User user = this.userService.getUserByUsername(u.getUsername());
                Map<String, Object> response = Map.of(
                        "message", "Login successful",
                        "token", token,
                        "user", Map.of(
                                "id", user.getId(),
                                "username", user.getUsername(),
                                "fullName", user.getFullName(),
                                "role", user.getRole(),
                                "avatar", user.getAvatar()
                        )
                );
                return ResponseEntity.ok().body(response);
            } catch (Exception e) {
                return ResponseEntity.status(500).body("Lỗi khi xử lý đăng nhập");
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Sai thông tin đăng nhập");
    }

    @RequestMapping("/secure/profile")
    @ResponseBody
    @CrossOrigin
    public ResponseEntity<User> getProfile(Principal principal) {
        return new ResponseEntity<>(this.userService.getUserByUsername(principal.getName()), HttpStatus.OK);
    }

    @PostMapping(path = "/register",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> register(@RequestBody Map<String, String> registerRequest) {
        try {
            Map<String, String> params = new HashMap<>();
            params.put("username", registerRequest.get("username"));
            params.put("password", registerRequest.get("password"));
            params.put("fullname", registerRequest.get("fullName"));
            params.put("number_phone", registerRequest.get("phoneNumber"));
            params.put("role", registerRequest.get("role"));

            User newUser = userService.addUser(params, null);

            Map<String, Object> response = Map.of(
                    "message", "Thành công",
                    "user", Map.of(
                            "id", newUser.getId(),
                            "username", newUser.getUsername(),
                            "fullName", newUser.getFullName(),
                            "role", newUser.getRole()
                    )
            );

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

            newUser.setRole("ROLE_SELLER_PENDING");
            userService.updateUser(newUser);

            Map<String, Object> response = Map.of(
                    "message", "Chỉ seller mới được đăng ký",
                    "user", Map.of(
                            "id", newUser.getId(),
                            "username", newUser.getUsername(),
                            "fullName", newUser.getFullName(),
                            "role", newUser.getRole()
                    )
            );

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", "Registration failed: " + e.getMessage()));
        }
    }
}
