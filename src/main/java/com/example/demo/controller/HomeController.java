package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.data.domain.Sort;

import com.example.demo.form.sub.UserCreateForm;
import com.example.demo.model.File;
import com.example.demo.model.SiteUser;
import com.example.demo.model.impl.UserDetailsImpl;
import com.example.demo.repository.SiteUserRepository;
import com.example.demo.service.FileService;
import com.example.demo.service.UserService;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * HOME画面のControllerクラス.
 */
@Controller
public class HomeController {
	
	
	@Autowired
	private UserService userService;

	/**
	 * File(Entityクラス)を操作するServiceクラス.
	 */
	@Autowired
	private FileService fileService;

	/**
	 * HOME画面パス.
	 */
	private final String HOME_TEMPLATE_PATH = "file/home";

	/**
	 * HOME画面URL.
	 */
	private final String HOME_URL = "/file/home/{id}";

	/**
	 * Redirect用HOME画面パス.
	 */
	private final String REDIRECT_HOME_URL = "redirect:/file/home";

	

	/**
	 * HOME画面表示.
	 * 
	 * @param model       Modelクラス
	 * @param pageable    ページネーション情報（デフォルト[1ページ目、6件表示、更新日時の新しい順でソート])
	 * @param userDetails ログインユーザーの詳細情報
	 * @return 遷移先
	 */
	
	@GetMapping("/file/mypage")
	public String home(@ModelAttribute("user") SiteUser user,Model model,
			@AuthenticationPrincipal UserDetailsImpl userDetails) {

		// ログインユーザーの詳細情報を判定
		if (userDetails == null) {
			// ログインユーザーの詳細情報がNULLの場合
			model.addAttribute("loginUsername", "");
			model.addAttribute("files", new ArrayList<File>());
		} else {
			// ログインユーザーの詳細情報がNULL以外の場合
			// 1ページに表示するファイル情報を取得
			SiteUser siteUser = userService.findOne(userDetails.getId());
			model.addAttribute("user", siteUser);
			
			List<File> filesPage = fileService.findMyFile(siteUser);
			
			model.addAttribute("files", filesPage);
			model.addAttribute("loginUsername", userDetails.getUsername());
		}

		return "file/mypage";
	}
	
	@GetMapping("/file/home")
	public String fileHome(@ModelAttribute("user") SiteUser user,Model model,
			@PageableDefault(page = 0, size = 6, sort = {
					"updateDate" }, direction = Sort.Direction.DESC) Pageable pageable,
			@AuthenticationPrincipal UserDetailsImpl userDetails) {
		
		

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

		return "file/home";
	}
	
	
	/**
	 * HOME画面表示.
	 * 
	 * @param model       Modelクラス
	 * @param pageable    ページネーション情報（デフォルト[1ページ目、6件表示、更新日時の新しい順でソート])
	 * @param userDetails ログインユーザーの詳細情報
	 * @return 遷移先
	 */
	@RequestMapping("/index")
	public String index(@ModelAttribute("user") SiteUser user,Model model,
			@PageableDefault(page = 0, size = 6, sort = {
			"updateDate" }, direction = Sort.Direction.DESC) Pageable pageable,
	        @AuthenticationPrincipal UserDetailsImpl userDetails) {

            // 1ページに表示するファイル情報を取得
            Page<File> filesPage = fileService.findAll(pageable);

            // ファイル一覧のページ情報を設定
            PageWrapper<File> page = new PageWrapper<File>(filesPage, "index");

            model.addAttribute("files", filesPage);
            model.addAttribute("page", page);
            model.addAttribute("url", "index");

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

}

