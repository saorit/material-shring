package com.example.demo.form.sub;

import java.io.Serializable;

import com.example.demo.form.UserRequest;
import com.sun.istack.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class UserUpdateRequest extends UserRequest implements Serializable {
	
	/** シリアルバージョンUID. */
	private static final long serialVersionUID = 1L;
	
	 /**
	   * ユーザーID
	   */
	  @NotNull
	  private Integer id;

}