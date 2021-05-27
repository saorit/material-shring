package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.model.File;
import com.example.demo.model.SiteUser;
import com.example.demo.model.impl.UserDetailsImpl;
import com.example.demo.util.Role;
import com.example.demo.service.FileService;
import com.example.demo.service.UserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class SecurityController {
	
	/**
	 * File(Entityクラス)を操作するServiceクラス.
	 */
	@Autowired
	private FileService fileService;
	
	/**
	 * UserEntityクラスを操作するServiceクラス.
	 */
	@Autowired
	private UserService userService;

    private final BCryptPasswordEncoder passwordEncoder;
    
 
	/**
	 * HOME画面URL.
	 */
	private final String HOME_URL = "/file/home";

	
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping("/")
    public String top(Model model,
			@PageableDefault(page = 0, size = 6, sort = {
					"updateDate" }, direction = Sort.Direction.DESC) Pageable pageable,
			@AuthenticationPrincipal UserDetailsImpl userDetails) {

		// 1ページに表示するファイル情報を取得
		Page<File> filesPage = fileService.findAll(pageable);

		// ファイル一覧のページ情報を設定
		PageWrapper<File> page = new PageWrapper<File>(filesPage, HOME_URL);

		model.addAttribute("files", filesPage);
		model.addAttribute("page", page);
		model.addAttribute("url", HOME_URL);

		// ログインユーザーの詳細情報を判定
		if (userDetails == null) {
			// ログインユーザーの詳細情報がNULLの場合
			model.addAttribute("loginUsername", "");
		} else {
			// ログインユーザーの詳細情報がNULL以外の場合
			model.addAttribute("loginUsername", userDetails.getUsername());
		}

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
    	
        
        return "userconfirm";
    }

    @GetMapping("/userconfirm")
    public String register(@ModelAttribute("user") SiteUser user) {
        return "userconfirm";
    }
    
    @PostMapping("/userconfirm")
    public String process(@ModelAttribute("user") SiteUser user) {
    	
    	user.setPassword(passwordEncoder.encode(user.getPassword()));
        if (user.isAdmin()) {
            user.setRole(Role.ADMIN.name());
        } else {
            user.setRole(Role.USER.name());
        }

    	// ユーザー情報を保存
        userService.save(user);

        return "redirect:/login?register";
    }
}
