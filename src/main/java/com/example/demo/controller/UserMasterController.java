package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

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
import org.springframework.validation.ObjectError;
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
import com.example.demo.form.sub.UserCreateForm;
import com.example.demo.form.sub.UserUpdateForm;
import com.example.demo.form.sub.UserUpdateRequest;

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
	
	 private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	
	/**
	 * ユーザー編集画面表示.
	 *
	 * @param username ユーザー名
	 * @param model    Modelクラス
	 * @return ユーザー編集画面のテンプレートパス
	 */
	@GetMapping("/user_master/edit/{userId}")
	public String edit(@PathVariable Integer userId, Model model) {
		
		 SiteUser user = userService.findOne(userId);
		 
	        UserUpdateRequest userUpdateRequest = new UserUpdateRequest();
	        userUpdateRequest.setId(user.getId());
	        userUpdateRequest.setUsername(user.getUsername());
	        userUpdateRequest.setDisplayname(user.getDisplayname());
	        userUpdateRequest.setProfile(user.getProfile());
	        userUpdateRequest.setPassword(user.getPassword());
	        model.addAttribute("userUpdateRequest", userUpdateRequest);
		
		return "user_master/edit";
	}
	

	/**
	 * ユーザーの更新処理.
	 *
	 * @param userUpdateForm 編集画面の入力情報
	 * @param bindingResult  入力チェック結果
	 * @return 遷移先パス(エラーの場合、編集画面のテンプレートパス。成功の場合、一覧画面のテンプレートパス)
	 */
	@PostMapping("/user/update")
	public String update(@Validated @ModelAttribute UserUpdateRequest userUpdateRequest, BindingResult result, Model model) {
        if (result.hasErrors()) {
            List<String> errorList = new ArrayList<String>();
            for(ObjectError error : result.getAllErrors()) {
                errorList.add(error.getDefaultMessage());
            }
              model.addAttribute("validationError", errorList);
              return "user_master/edit";
            }
        
        userUpdateRequest.setPassword(passwordEncoder.encode(userUpdateRequest.getPassword()));
        
        // ユーザー情報の更新
        userService.update(userUpdateRequest);
        
        

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
	@GetMapping("/user_master/contributor/{userId}")
	public String contributor(Model model,
			@AuthenticationPrincipal UserDetailsImpl userDetails,
			@PathVariable Integer userId) {
		
		model.addAttribute("user",  userService.findOne(userId));

		// ログインユーザーの詳細情報を判定
		if (userDetails == null) {
			// ログインユーザーの詳細情報がNULLの場合
			model.addAttribute("loginUsername", "");
			model.addAttribute("files", new ArrayList<File>());
		} else {
			// ログインユーザーの詳細情報がNULL以外の場合
			// 1ページに表示するファイル情報を取得
			SiteUser siteUser = userService.findOne(userId);
			model.addAttribute("user", siteUser);
			
			List<File> filesPage = fileService.findMyFile(siteUser);
			
			model.addAttribute("files", filesPage);
			model.addAttribute("loginUsername", userDetails.getUsername());
		}
		
		return "user_master/contributor";
	}

}
