package com.board.study.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.board.study.entity.board.member.Member;
import com.board.study.entity.board.member.MemberRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MemberService implements UserDetailsService {

	private final MemberRepository memberRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
			
		// メールアドレスを基にメンバーエンティティをデータベースから取得します。
		Member member = memberRepository.findByEmail(email);
		
		// メンバーが見つからない場合、UsernameNotFoundException がスローされます。
		if (member == null) throw new UsernameNotFoundException("アカウントが見つかりません。"); 
		
		return member;
	}
}
