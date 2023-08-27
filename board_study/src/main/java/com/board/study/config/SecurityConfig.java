package com.board.study.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import com.board.study.member.handler.AuthFailureHandler;
import com.board.study.member.handler.AuthSucessHandler;
import com.board.study.service.MemberService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@EnableWebSecurity // セキュリティフィルター登録
@EnableGlobalMethodSecurity(prePostEnabled = true) // 特定のページに特定の権限があるユーザーだけがアクセスを許可する場合、権限および認証を事前にチェックするという設定を有効化する。
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	private final MemberService memberService;
	private final AuthSucessHandler authSucessHandler;
	private final AuthFailureHandler authFailureHandler;
	
	// BCryptPasswordEncoderはSpringSecurityが提供するパスワード暗号化オブジェクト(BCryptというハッシュ関数を用いてパスワードを暗号化する。)
	// 会員パスワード登録時に該当メソッドを利用して暗号化しなければ、ログイン処理時に同じハッシュで比較できない。
	@Bean
	public BCryptPasswordEncoder encryptPassword() {
		return new BCryptPasswordEncoder();
	}
	
	// セキュリティがログイン過程でpasswordを横取りする時、該当ハッシュで暗号化して比較する。
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(memberService).passwordEncoder(encryptPassword());
	}
	
	@Override
    protected void configure(HttpSecurity http) throws Exception {
		/*
		 csrfトークンの活性化に使用
		 クッキーを生成する時、HttpOnlyタグを使用すれば、クライアントスクリプトが保護されたクッキーにアクセスする危険性を減らすことができるので、クッキーのセキュリティを強化できる。
		*/
		//http.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
		
        http.csrf().disable()	// csrfトークンを無効にする
        	.authorizeRequests() // リクエストURLに従ってアクセス権限を設定
			.antMatchers("/","/login/**","/js/**","/css/**","/image/**").permitAll() // 経路のアクセスを許可
			.anyRequest() // 他のすべての要請は
			.authenticated() // 認証されたユーザーのみアクセスを許可
		.and()
			.formLogin() // ログインフォームは
			.loginPage("/login") // 該当アドレスにログインページを呼び出す。
			.loginProcessingUrl("/login/action") // 該当URLに要請があればスプリングセキュリティが横取りしてログイン処理をする。 -> loadUserByName
			.successHandler(authSucessHandler) // 成功時に要求を処理するハンドラー
			.failureHandler(authFailureHandler) // 失敗時に要求を処理するハンドラー
		.and()
			.logout()
			.logoutRequestMatcher(new AntPathRequestMatcher("/logout")) // ログアウトURL
		    .logoutSuccessUrl("/login") // 成功時リターンURL
		    .invalidateHttpSession(true) // 認証情報を消去し、セッションを無効化
		    .deleteCookies("JSESSIONID") // JSESSIONID クッキー 削除
			.permitAll()
		.and()
        	.sessionManagement()
            .maximumSessions(1) // セッション最大許容数1,-1の場合、無制限セッション許容
            .maxSessionsPreventsLogin(false) // trueなら重複ログインを防ぎ、falseなら以前のログインのセッションを解除
            .expiredUrl("/login?error=true&exception=Have been attempted to login from a new place. or session expired")  // セッションが満了した場合、移動するページを指定
        .and()
	        .and().rememberMe() // ログイン維持
	        .alwaysRemember(false) // いつも覚えているかどうかについての可否
	        .tokenValiditySeconds(43200) // in seconds、12時間維持
	        .rememberMeParameter("remember-me");
    }
}
