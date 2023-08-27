package com.board.study.dto.board;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import com.board.study.entity.board.Board;
import lombok.Getter;

@Getter
public class BoardResponseDto {
    
    // ボードの識別子
    private Long id;
    
    // ボードのタイトル
    private String title;
    
    // ボードの内容
    private String content;
    
    // ボードの閲覧回数
    private int readCnt;
    
    // 登録したユーザーの識別子
    private String registerId;
    
    // 登録時刻
    private LocalDateTime registerTime;
    
    // Boardエンティティを受け取って初期化するコンストラクタ
    public BoardResponseDto(Board entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.readCnt = entity.getReadCnt();
        this.registerId = entity.getRegisterId();
        this.registerTime = entity.getRegisterTime();
    }

    // オブジェクトの文字列表現を返すメソッド
    @Override
    public String toString() {
        return "BoardResponseDto [id=" + id + ", title=" + title + ", content=" + content + ", readCnt=" + readCnt
                + ", registerId=" + registerId + ", registerTime=" + registerTime + "]";
    }
    
    // 登録時刻を指定の書式の文字列に変換して返すメソッド
    public String getRegisterTime() {
        return toStringDateTime(this.registerTime);
    }
    
    // LocalDateTimeを文字列に変換する静的メソッド
    public static String toStringDateTime(LocalDateTime localDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return Optional.ofNullable(localDateTime)
                .map(formatter::format)
                .orElse("");
    }
}