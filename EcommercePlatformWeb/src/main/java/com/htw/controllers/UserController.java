package com.htw.controllers;

import com.htw.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.htw.services.UserService;
import java.util.List;
import java.util.Map;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    private Map<String, String> roles = Map.of(
            "ROLE_ADMIN", "Quản trị viên",
            "ROLE_STAFF", "Nhân viên",
            "ROLE_CUSTOMER", "Khách hàng",
            "ROLE_SELLER", "Người bán"
    );

    @GetMapping("/users")
    public String listUser(Model model) {
        model.addAttribute("roles", this.roles);
        model.addAttribute("users", userService.getUser());
        return "user-list";
    }

    @GetMapping("/users/add")
    public String addUser(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("roles", this.roles);

        return "user-form";
    }

    @PostMapping("users/save")
    public String add(@ModelAttribute(value = "user") User user) {
        this.userService.addOrUpdateUserInfo(user);
        return "redirect:/users";
    }

    @GetMapping("users/{userId}")
    public String viewDetail(Model model, @PathVariable(value = "userId") int id) {
        model.addAttribute("user", this.userService.getUserById(id));

        model.addAttribute("roles", this.roles);

        return "user-form";
    }

    @GetMapping("/login")
    public String loginView() {
        return "login";
    }

}
