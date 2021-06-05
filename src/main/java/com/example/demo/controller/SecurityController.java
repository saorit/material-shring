package com.example.demo.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.model.File;
import com.example.demo.model.SiteUser;
import com.example.demo.model.impl.UserDetailsImpl;
import com.example.demo.util.Role;
import com.example.demo.form.sub.UserCreateForm;
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
	private final String HOME_URL = "index";

	/**
	 * ログイン画面
	 */
    @GetMapping("/login")
    public String login() {
        return "login";
    }
    
    /**
	 * ホーム画面
	 */
    @RequestMapping("/")
    public String top(@ModelAttribute("user") SiteUser user,Model model,
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
    
    /**
	 * ユーザー登録画面
	 */
    @GetMapping("/register")
    private String readForm(@ModelAttribute UserCreateForm userCreateForm) {
        return "register";
    }

    @PostMapping("/register")
    private String confirm(@Valid @ModelAttribute UserCreateForm userCreateForm, 
    		final BindingResult bindingResult)  {
    	// 入力チェック
    	if (bindingResult.hasErrors()) {
    	// 入力チェックエラー時の処理
    	return "register";
    	}
    	userCreateForm.setPassword(passwordEncoder.encode(userCreateForm.getPassword()));

    	SiteUser user = userCreateForm.toEntity();

    	// ユーザー情報を保存
    	userService.save(user);
        
        return "redirect:/login?register";
    }
    
    /**
	 * ユーザー登録確認画面
	 */
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
