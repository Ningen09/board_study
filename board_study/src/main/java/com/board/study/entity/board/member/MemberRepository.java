package com.board.study.entity.board.member;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

// JpaRepository を継承して、Member エンティティに関する CRUD 機能を提供する MemberRepository インターフェース
public interface MemberRepository extends JpaRepository<Member, Long> {
	
    // 最終ログイン時刻の更新クエリ
	static final String UPDATE_MEMBER_LAST_LOGIN = "UPDATE Member "
			+ "SET LAST_LOGIN_TIME = :lastLoginTime "
			+ "WHERE EMAIL = :email";
	
	// 最終ログイン時刻の更新メソッド
	@Transactional
	@Modifying
	@Query(value=UPDATE_MEMBER_LAST_LOGIN, nativeQuery = true)
	public int updateMemberLastLogin(@Param("email") String email, @Param("lastLoginTime") LocalDateTime lastLoginTime);
	
	// メールアドレスによる会員の検索メソッド
	public Member findByEmail(String email);
}