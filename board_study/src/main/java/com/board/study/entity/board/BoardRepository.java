package com.board.study.entity.board;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.board.study.dto.board.BoardRequestDto;

// JpaRepositoryを継承してBoardエンティティに関するCRUD機能を提供するBoardRepositoryインターフェース
public interface BoardRepository extends JpaRepository<Board, Long> {
    
    // 記事の更新クエリ
    static final String UPDATE_BOARD = "UPDATE Board "
            + "SET TITLE = :#{#boardRequestDto.title}, "
            + "CONTENT = :#{#boardRequestDto.content}, "
            + "REGISTER_ID = :#{#boardRequestDto.registerId}, "
            + "UPDATE_TIME = NOW() "
            + "WHERE ID = :#{#boardRequestDto.id}";
    
    // 閲覧数の増加クエリ
    static final String UPDATE_BOARD_READ_CNT_INC = "UPDATE Board "
            + "SET READ_CNT = READ_CNT + 1 "
            + "WHERE ID = :id";
    
    // 記事の削除クエリ
    static final String DELETE_BOARD = "DELETE FROM Board "
            + "WHERE ID IN (:deleteIdList)";
    
    // 記事の更新メソッド
    @Transactional
    @Modifying
    @Query(value = UPDATE_BOARD, nativeQuery = true)
    public int updateBoard(@Param("boardRequestDto") BoardRequestDto boardRequestDto);
    
    // 閲覧数の増加メソッド
    @Transactional
    @Modifying
    @Query(value = UPDATE_BOARD_READ_CNT_INC, nativeQuery = true)
    public int updateBoardReadCntInc(@Param("id") Long id);
    
    // 記事の削除メソッド
    @Transactional
    @Modifying
    @Query(value = DELETE_BOARD, nativeQuery = true)
    public int deleteBoard(@Param("deleteIdList") Long[] deleteIdList);
}