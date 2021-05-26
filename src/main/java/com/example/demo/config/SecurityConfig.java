package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.example.demo.service.impl.UserDetailsServiceImpl;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	UserDetailsServiceImpl userDetailsService;

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		// 「/js/**」を追加
		web.ignoring().antMatchers("/js/**", "/css/**", "/webjars/**");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				// 認証不要でアクセス可能
				.antMatchers("/login", "/register", "/userconfirm").permitAll().
				anyRequest().authenticated()
				.and().sessionManagement().sessionFixation().none().and().formLogin().loginPage("/login")
				.loginProcessingUrl("/login") // ログインフォームのアクションに指定したURL[action="@{/login}"]を設定
				.usernameParameter("username") // ログインフォームのユーザー欄のname属性を設定
				.passwordParameter("password") // ログインフォームのパスワード欄のname属性を設定
				.successForwardUrl("/") // ログイン成功時に遷移するURL
				.failureUrl("/login?error") // ログイン失敗時に遷移するURL
				.permitAll().and().logout().logoutUrl("/logout").permitAll()
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout"));
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// userDetailsServiceを使用して、DBからユーザを参照できるようにします
		auth.userDetailsService(userDetailsService);
	}
}