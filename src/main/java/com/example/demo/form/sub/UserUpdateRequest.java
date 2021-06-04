package com.example.demo.form.sub;

import java.io.Serializable;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.example.demo.form.UserRequest;
import com.example.demo.model.SiteUser;
import com.sun.istack.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class UserUpdateRequest extends UserRequest implements Serializable {
	 /**
	   * ユーザーID
	   */
	  @NotNull
	  private Integer id;

}