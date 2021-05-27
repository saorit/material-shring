package com.example.demo.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.model.SiteUser;
import com.example.demo.repository.SiteUserRepository;
import com.example.demo.service.UserService;
import com.example.demo.form.sub.UserUpdateForm;

@Controller
public class UserMasterController {
	
	SiteUserRepository repository;
	
	/**
	 * UserEntityクラスを操作するServiceクラス.
	 */
	@Autowired
	private UserService userService;
	
	/**
	 * ユーザー編集画面表示.
	 *
	 * @param username ユーザー名
	 * @param model    Modelクラス
	 * @return ユーザー編集画面のテンプレートパス
	 */
	@GetMapping("/user_master/edit/{username}")
	public String edit(@ModelAttribute("user") SiteUser user) {
		
		return "user_master/edit";
	}
	

	/**
	 * ユーザーの更新処理.
	 *
	 * @param userUpdateForm 編集画面の入力情報
	 * @param bindingResult  入力チェック結果
	 * @return 遷移先パス(エラーの場合、編集画面のテンプレートパス。成功の場合、一覧画面のテンプレートパス)
	 */
	@PostMapping("/user_master/update")
	public String update(@Validated @ModelAttribute("user") SiteUser user,
            BindingResult result) {

		// 入力チェック
		if (result.hasErrors()) {
            return "user_master/edit";
        }
		// ユーザー情報を保存
        userService.save(user);

		return "redirect:/file/home?upload";
	}

}
