package com.example.demo.model.impl;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.demo.model.SiteUser;

/**
 * ユーザー詳細情報の拡張クラス.
 *
 */
public class UserDetailsImpl implements UserDetails {

	/** シリアルバージョンUID. */
	private static final long serialVersionUID = 1L;

	/** ユーザーEntityクラス. */
	private SiteUser user;

	/** ユーザーの権限情報. */
	private Collection<GrantedAuthority> authorities;

	/**
	 * コンストラクタ.
	 *
	 * @param user        User(Entityクラス)
	 * @param authorities ユーザーの権限情報
	 */
	public UserDetailsImpl(SiteUser user, Collection<GrantedAuthority> authorities) {
		this.user = user;
		this.authorities = authorities;
	}

	/**
	 * ユーザーIDを取得する.
	 *
	 * @return ユーザーID
	 */
	public Long getId() {
		return user.getId();
	}

	/**
	 * ユーザーの表示名を取得する.
	 *
	 * @return ユーザーの表示名
	 */
	public String getDisplayName() {
		return user.getDisplayname();
	}

	/**
	 * ユーザーのパスワードを取得する.
	 *
	 * @return ユーザーのパスワード
	 */
	@Override
	public String getPassword() {
		return user.getPassword();
	}


	/**
	 * ユーザーの権限情報を取得する.
	 *
	 * @return ユーザーの権限情報
	 */
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	/**
	 * ユーザー名を取得する.
	 *
	 * @return ユーザー名
	 */
	@Override
	public String getUsername() {
		return user.getUsername();
	}
	
	/* 一応オーバーライド	    */
	    @Override
	    public boolean isAccountNonExpired() {
	        return true;
	    }
	    @Override
	    public boolean isAccountNonLocked() {
	        return true;
	    }
	    @Override
	    public boolean isCredentialsNonExpired() {
	        return true;
	    }
	    @Override
	    public boolean isEnabled() {
	        return true;
	    }
	    



}