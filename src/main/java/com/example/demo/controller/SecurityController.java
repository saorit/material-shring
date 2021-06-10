package com.example.demo.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.model.SiteUser;
import com.example.demo.util.Role;
import com.example.demo.form.sub.UserCreateForm;
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
	public String top() {

		return "redirect:index";
	}

	/**
	 * ユーザー登録画面
	 */
	@GetMapping("/register")
	private String readForm(@ModelAttribute UserCreateForm userCreateForm) {
		return "register";
	}

	@PostMapping("/register")
	private String confirm(@Valid @ModelAttribute UserCreateForm userCreateForm, final BindingResult bindingResult) {
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
