package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.model.SiteUser;
import com.example.demo.util.Role;
import com.example.demo.service.UserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class SecurityController {
	
	/**
	 * UserEntityクラスを操作するServiceクラス.
	 */
	@Autowired
	private UserService userService;

    private final BCryptPasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/")
    public String showList(Authentication loginUser, Model model) {
        model.addAttribute("username", loginUser.getName());
        model.addAttribute("role", loginUser.getAuthorities());
        return "index";
    }
    @GetMapping("/register")
    private String readForm(@ModelAttribute("user") SiteUser user) {
        return "register";
    }

    @PostMapping("/register")
    private String confirm(@Validated @ModelAttribute("user") SiteUser user,
            BindingResult result) {
    	if (result.hasErrors()) {
            return "register";
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if (user.isAdmin()) {
            user.setRole(Role.ADMIN.name());
        } else {
            user.setRole(Role.USER.name());
        }
        
        return "userconfirm";
    }

    @GetMapping("/userconfirm")
    public String register(@ModelAttribute("user") SiteUser user) {
        return "userconfirm";
    }
    
    @PostMapping("/userconfirm")
    public String process(@ModelAttribute("user") SiteUser user) {
    	

    	// ユーザー情報を保存
    			userService.save(user);

        return "redirect:/login?register";
    }
}

