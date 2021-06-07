package com.example.demo.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.form.FileUploadForm;
import com.example.demo.model.File;
import com.example.demo.model.Publish;
import com.example.demo.model.SiteUser;
import com.example.demo.model.impl.UserDetailsImpl;
import com.example.demo.service.FileService;
import com.example.demo.service.UserService;
import com.example.demo.service.PublishService;

@Controller
public class FileController {

	/**
	 * FileEntityクラスを操作するServiceクラス.
	 */
	@Autowired
	private FileService fileService;
	
	/**
	 * PublishEntityクラスを操作するServiceクラス.
	 */
	@Autowired
	private PublishService publishService;

	/**
	 * UserEntityクラスを操作するServiceクラス.
	 */
	@Autowired
	private UserService userService;

	/**
	 * 新規登録画面のTemplateHTMLのパス.
	 */
	private final String NEW_TEMPLATE_PATH = "file/new";

	/**
	 * 編集画面のTemplateHTMLのパス.
	 */
	private final String EDIT_TEMPLATE_PATH = "file/edit";

	/**
	 * 詳細画面のTemplateHTMLのパス.
	 */
	private final String SHOW_TEMPLATE_PATH = "file/show";
	
	/**
	 * ファイル新規登録画面表示.
	 * 
	 * @param fileUploadForm アップロードファイルのFormクラス
	 * @return ファイル新規登録画面のテンプレートパス
	 */
	@GetMapping("/file/new")
	public String newFile(@ModelAttribute FileUploadForm fileUploadForm) {
		return NEW_TEMPLATE_PATH;
	}
	
	/**
	 * ファイルのアップロード処理.
	 * 
	 * @param fileForm      ファイルのアップロード情報
	 * @param bindingResult 入力チェック結果
	 * @param userDetails   ユーザーの詳細情報
	 * @return 遷移先パス(エラーの場合、新規登録画面のテンプレートパス。成功の場合、HOME画面)
	 */
	@PostMapping("/file/upload")
	public String create(@Validated @ModelAttribute FileUploadForm fileForm, final BindingResult bindingResult,
			@AuthenticationPrincipal UserDetailsImpl userDetails){

		// 入力チェック
		if (bindingResult.hasErrors()) {
			// 入力チェックエラー時の処理
			return NEW_TEMPLATE_PATH;
		}
		
		// 公開範囲の情報処理
		Publish publish = new Publish();
			
			// 教材の名前を取得
			String publishItemname = fileForm.getItemname();
			publish.setPublishItemname(publishItemname);
			
			// 公開範囲を取得
            String publicPreference = fileForm.getPublicPreference();
            publish.setPublicPreference(publicPreference);
            
            // ユーザー名に紐づくユーザー情報を取得
            SiteUser makeUser = userService.findOneUsername(userDetails.getUsername());
            publish.setMakeUser(makeUser); // 登録ユーザーを設定
            
           // 教材Idに紐づく教材情報を取得
            File fileId = fileService.findOne(fileForm.getId());
            publish.setFileId(fileId); // 登録教材を設定

			
		// 公開範囲の情報を保存
		publishService.save(publish);
		
					
		// 教材の情報処理
		File file = new File();	
		try {
			file.setData(fileForm.getMultipartFile().getBytes());

			// ファイル名を取得
			String fileName = fileForm.getMultipartFile().getOriginalFilename();
			file.setName(fileName);
			
			// 教材の名前を取得
			String itemname = fileForm.getItemname();
			file.setItemname(itemname);
			
			// 教材の内容を取得
			String description = fileForm.getDescription();
			file.setDescription(description);
			
			// 画像ファイル拡張子フラグを取得
			boolean isImageExtension = this.isImageExtension(fileName);

			file.setImageExtension(isImageExtension);

			// 現在日時を取得
			Timestamp currentTime = new Timestamp(System.currentTimeMillis());
			file.setCreateDate(currentTime); // 登録日時を設定
			file.setUpdateDate(currentTime); // 更新日時を設定

			// ユーザー名に紐づくユーザー情報を取得
			SiteUser loginUser = userService.findOneUsername(userDetails.getUsername());
			file.setCreateUser(loginUser); // 登録ユーザーを設定
			file.setUpdateUser(loginUser); // 更新ユーザーを設定

		} catch (IOException e) {
			// TODO 例外処理を実装する
		}

		// ファイル情報を保存
		fileService.save(file);

		return "redirect:/index?upload";
	}

	/**
	 * ファイル編集画面表示.
	 * 
	 * @param id             ファイルID
	 * @param fileUploadForm アップロードファイルのFormクラス
	 * @return ファイル編集画面のテンプレートパス
	 */
	@GetMapping("/file/edit/{id}")
	public String editFile(@PathVariable int id, Model model) {
		// ファイル情報を取得
		File file = fileService.findOne(id);
		model.addAttribute("file", file);
		
		FileUploadForm fileUploadForm = new FileUploadForm();
		fileUploadForm.setId(file.getId());
		fileUploadForm.setItemname(file.getItemname());
		fileUploadForm.setDescription(file.getDescription());
        model.addAttribute("fileUploadForm", fileUploadForm);
		
		return EDIT_TEMPLATE_PATH;
	}

	/**
	 * ファイル情報の更新処理.
	 * 
	 * @param id             ファイルID
	 * @param userUpdateForm 編集画面の入力情報
	 * @param bindingResult  入力チェック結果
	 * @param userDetails    ログインユーザーの詳細情報
	 * @return 遷移先(エラーの場合、編集画面。成功の場合、HOME画面)
	 */
	@PostMapping("/file/update/{id}")
	public String update(@PathVariable int id, @Validated FileUploadForm fileForm, final BindingResult result,
			@AuthenticationPrincipal UserDetailsImpl userDetails, Model model) {

		// 入力チェック
		if (result.hasErrors()) {
		// 入力チェックエラー時の処理
		return "file/edit";
		}

		// ファイル情報を取得
		File file = fileService.findOne(id);
		try {
			// ファイルデータを設定
			file.setData(fileForm.getMultipartFile().getBytes());
			
			// ファイルnameを設定
			file.setItemname(fileForm.getItemname());
			
			// descriptionを設定
			file.setDescription(fileForm.getDescription());

			// ファイル名を取得
			String fileName = fileForm.getMultipartFile().getOriginalFilename();
			file.setName(fileName);

			// 画像ファイルフラグを取得
			boolean isImage = this.isImageExtension(fileName);
			file.setImageExtension(isImage);

			// 現在日時を取得
			Timestamp currentTime = new Timestamp(System.currentTimeMillis());
			// 更新日時を設定
			file.setUpdateDate(currentTime);

			// ユーザー名に紐づくユーザー情報を取得
			SiteUser loginUser = userService.findOne(userDetails.getId());
			// 更新ユーザーを設定
			file.setUpdateUser(loginUser);

			// ファイル情報を保存
			fileService.save(file);
		} catch (IOException e) {
			// TODO 例外処理を実装する

		}
		return "redirect:/index?fileupdate";
	}
	
	/**
	 * 公開範囲画面.
	 * 
	 * @param id    ファイルID
	 * @param model Modelクラス
	 * @return ファイル公開範囲のテンプレートパス
	 */
	@GetMapping("/file/release/{id}")
	public String releaseFile(@PathVariable int id, Model model,
			@ModelAttribute FileUploadForm fileUploadForm) {
		// ファイル情報を取得
				File file = fileService.findOne(id);
				model.addAttribute("file", file);

		return "file/release";
	}

	/**
	 * ファイル詳細画面表示.
	 * 
	 * @param id    ファイルID
	 * @param model Modelクラス
	 * @return ファイル詳細画面のテンプレートパス
	 */
	@GetMapping("/file/show/{id}")
	public String show(@PathVariable int id, Model model) {
		// ファイル情報を取得
		File file = fileService.findOne(id);
		model.addAttribute("file", file);

		return SHOW_TEMPLATE_PATH;
	}

	/**
	 * 画像表示.
	 * 
	 * @param id  ファイルID
	 * @param res HTTPレスポンス
	 */
	@GetMapping("/file/show/image/{id}")
	@ResponseBody
	public void showImage(@PathVariable int id, HttpServletResponse res) {
		// ファイル情報を取得
		File file = fileService.findOne(id);

		try (
				// ResponseのOutputStreamを代入
				OutputStream os = res.getOutputStream();) {
			// OutputStreamにファイルデータを書き出す
			os.write(file.getData());
		} catch (IOException e) {
			// TODO 例外処理を実装
		}
	}

	/**
	 * ファイルの削除処理.
	 * 
	 * @param id ファイルID
	 * @return 遷移先(HOME画面のURL)
	 */
	@GetMapping("/file/delete/{id}")
	public String destroy(@PathVariable int id) {
		fileService.delete(id);
		return "redirect:/index?delete";
	}

	/**
	 * ファイルのダウンロード処理.
	 * 
	 * @param id ファイルID
	 * @return 遷移先(null)
	 * @throws UnsupportedEncodingException
	 */
	@GetMapping("/file/download/{id}")
	@ResponseBody
	public String download(@PathVariable int id, HttpServletResponse res) throws UnsupportedEncodingException {
		// ファイル情報を取得
		File file = fileService.findOne(id);
		// ファイルデータを取得
		byte[] fileData = file.getData();

		// ファイル名を取得
		String fileName = new String(file.getName().getBytes("Windows-31J"), "ISO8859_1");

		// レスポンスオブジェクトのヘッダー情報を設定
		res.setContentType("application/octet-stream");
		// ヘッダーにファイル名を設定
		res.setHeader("Content-Disposition", "attachment; filename=" + fileName);

		// ファイルサイズをResponseのメッセージボディのサイズに設定
		res.setContentLength(fileData.length);

		try (
			// ResponseのOutputStreamを代入
            OutputStream os = res.getOutputStream();) {
			// OutputStreamにファイルデータを書き出す
		    os.write(file.getData());
		    // OutputStreamを強制的に書き込み
		    os.flush();
		} catch (IOException e) {
			// TODO 例外処理を実装
		}
		return null;
	}

	/**
	 * ファイル名から画像の拡張子かを判定するメソッド.
	 * 
	 * @param fileName ファイル名
	 * @return 画像の拡張子か判定した結果（画像ファイルの拡張子の場合、true）
	 */
	private boolean isImageExtension(String fileName) {

		// ファイルの拡張子が画像ファイルか判定
		if (fileName.endsWith(".gif") || fileName.endsWith(".GIF") || fileName.endsWith(".jpeg")
				|| fileName.endsWith(".JPEG") || fileName.endsWith(".jpg") || fileName.endsWith(".JPG")
				|| fileName.endsWith(".jfif") || fileName.endsWith(".JFIF") || fileName.endsWith(".png")
				|| fileName.endsWith(".PNG")) {
			// 画像ファイルの場合
			return true;
		}
		return false;
	}
}