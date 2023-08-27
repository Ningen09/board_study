package com.board.study.entity.board.member;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.board.study.entity.BaseTimeEntity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(of= {"id"}) // equals, hashCode の自動生成
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Member extends BaseTimeEntity implements UserDetails {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String email;
	private String pwd;
	private LocalDateTime lastLoginTime;
	
    @Builder
	public Member(Long id, String email, String pwd, LocalDateTime lastLoginTime) {
		super();
		this.id = id;
		this.email = email;
		this.pwd = pwd;
		this.lastLoginTime = lastLoginTime;
	}
	
	@Override
	public String getPassword() {
		return this.getPwd();
	}
	
	@Override
	public String getUsername() {
		return this.getEmail();
	}
	
    // アカウントが保持する権限のリストを返す
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(() -> {
            return "アカウントごとに登録する権限";
        });
        
        // authorities.add(new SimpleGrantedAuthority("Role"));
        
        return authorities;
    }
    
	// アカウントの有効期限が切れていないかを返す（true: 有効期限切れていない）
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // アカウントがロックされていないかを返す（true: ロックされていない）
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // パスワードの有効期限が切れていないかを返す（true: 有効期限切れていない）
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // アカウントが有効かどうかを返す（true: 有効）
    @Override
    public boolean isEnabled() {
        return true;
    }
}