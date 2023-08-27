package com.board.study.member.handler;

import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthFailureHandler extends SimpleUrlAuthenticationFailureHandler {
	
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
		
	    String msg = "無効なメールアドレスまたはパスワードです";
	
	    // 例外関連メッセージの処理
	    if (exception instanceof DisabledException) {
        	msg = "アカウントが無効です";
        } else if(exception instanceof CredentialsExpiredException) {
        	msg = "パスワードの有効期限が切れています";
        } else if(exception instanceof BadCredentialsException ) {
        	msg = "無効なメールアドレスまたはパスワードです";
        }
	
	    setDefaultFailureUrl("/login?error=true&exception=" + msg);
	
	    super.onAuthenticationFailure(request, response, exception);
	}
}