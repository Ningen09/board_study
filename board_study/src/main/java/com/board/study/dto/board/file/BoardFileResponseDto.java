package com.board.study.dto.board.file;

import com.board.study.entity.board.file.BoardFile;
import lombok.Getter;

@Getter
public class BoardFileResponseDto {
    
    // オリジナルファイル名
    private String origFileName;
    
    // 保存ファイル名
    private String saveFileName;
    
    // ファイルのパス
    private String filePath;
    
    // BoardFileエンティティを受け取って初期化するコンストラクタ
    public BoardFileResponseDto(BoardFile entity) {
        this.origFileName = entity.getOrigFileName();
        this.saveFileName = entity.getSaveFileName();
        this.filePath = entity.getFilePath();
    }

    // オブジェクトの文字列表現を返すメソッド
    @Override
    public String toString() {
        return "BoardFileResponseDto [origFileName=" + origFileName + ", saveFileName=" + saveFileName + ", filePath="
                + filePath + "]";
    }
}