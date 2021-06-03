package com.example.demo.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.model.File;
import com.example.demo.model.SiteUser;
import com.example.demo.model.impl.UserDetailsImpl;
import com.example.demo.repository.SiteUserRepository;
import com.example.demo.service.FileService;
import com.example.demo.service.UserService;
import com.example.demo.form.sub.UserUpdateForm;

@Controller
public class UserMasterController {
	
	SiteUserRepository repository;
	
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

		return "redirect:/index?upldate";
	}
	
	/**
	 * 公開範囲設定画面表示.
	 *
	 * @param username ユーザー名
	 * @param model    Modelクラス
	 * @return 公開範囲設定画面のテンプレートパス
	 */
	@GetMapping("/user_master/publish/{username}")
	public String publish(@ModelAttribute("user") SiteUser user) {
		
		return "user_master/publish";
	}
	
	/**
	 * 投稿者の詳細画面表示.
	 *
	 * @param id ユーザー
	 * @param model    Modelクラス
	 * @return 投稿者の詳細画面のテンプレートパス
	 */
	@GetMapping("/file/contributor/{userId}")
	public String contributor(@ModelAttribute("user") SiteUser user,Model model,
			@PageableDefault(page = 0, size = 6, sort = {
					"updateDate" }, direction = Sort.Direction.DESC) Pageable pageable,
			@AuthenticationPrincipal UserDetailsImpl userDetails,
			@PathVariable Integer userId) {
		model.addAttribute("user",  userService.findOne(userId));
		// 1ページに表示するファイル情報を取得
		Page<File> filesPage = fileService.findAll(pageable);
		// ファイル一覧のページ情報を設定
		PageWrapper<File> page = new PageWrapper<File>(filesPage, "file/home/{user.id}");
		model.addAttribute("files", filesPage);
		model.addAttribute("page", page);
		model.addAttribute("url", "file/home/{user.id}");
		// ログインユーザーの詳細情報を判定
		if (userDetails == null) {
			// ログインユーザーの詳細情報がNULLの場合
			model.addAttribute("loginUsername", "");
		} else {
			// ログインユーザーの詳細情報がNULL以外の場合
			model.addAttribute("loginUsername", userDetails.getUsername());
		}
		return "user_master/contributor";
	}

}
