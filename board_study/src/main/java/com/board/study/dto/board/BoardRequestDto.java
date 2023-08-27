package com.board.study.dto.board;

import com.board.study.entity.board.Board;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BoardRequestDto {
    
    // ボードの識別子
    private Long id;
    
    // ボードのタイトル
    private String title;
    
    // ボードの内容
    private String content;
    
    // 登録したユーザーの識別子
    private String registerId;
    
    // Boardエンティティに変換するメソッド
    public Board toEntity() {
        return Board.builder()
            .title(title)
            .content(content)
            .registerId(registerId)
            .build();
    }

    // オブジェクトの文字列表現を返すメソッド
    @Override
    public String toString() {
        return "BoardRequestDto [id=" + id + ", title=" + title + ", content=" + content + ", registerId=" + registerId
                + "]";
    }
}


