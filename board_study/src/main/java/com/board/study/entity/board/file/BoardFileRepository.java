package com.board.study.entity.board.file;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

// JpaRepository を継承して BoardFile エンティティに関するCRUD機能を提供する BoardFileRepository インターフェース
public interface BoardFileRepository extends JpaRepository<BoardFile, Long> {
	
    // 指定した掲示板に関連する添付ファイルの ID を取得するクエリ
    static final String SELECT_FILE_ID= "SELECT ID FROM board_file "
            + "WHERE BOARD_ID = :boardId AND DELETE_YN != 'Y'";
    
    // 指定した添付ファイルの DELETE_YN を 'Y' に更新するクエリ
    static final String UPDATE_DELETE_YN= "UPDATE board_file "
            + "SET DELETE_YN = 'Y' "
            + "WHERE ID IN (:deleteIdList)";
    
    // 指定した掲示板に関連するすべての添付ファイルの DELETE_YN を 'Y' に更新するクエリ
    static final String DELETE_BOARD_FILE_YN= "UPDATE board_file "
            + "SET DELETE_YN = 'Y' "
            + "WHERE BOARD_ID IN (:boardIdList)";
    
    // 指定した掲示板に関連する添付ファイルの ID を取得するメソッド
    @Query(value = SELECT_FILE_ID, nativeQuery = true)
    public List<Long> findByBoardId(@Param("boardId") Long boardId);
    
    // 指定した添付ファイルの DELETE_YN を 'Y' に更新するメソッド
    @Transactional
    @Modifying
    @Query(value = UPDATE_DELETE_YN, nativeQuery = true)
    public int updateDeleteYn(@Param("deleteIdList") Long[] deleteIdList);
    
    // 指定した掲示板に関連するすべての添付ファイルの DELETE_YN を 'Y' に更新するメソッド
    @Transactional
    @Modifying
    @Query(value = DELETE_BOARD_FILE_YN, nativeQuery = true)
    public int deleteBoardFileYn(@Param("boardIdList") Long[] boardIdList);
}