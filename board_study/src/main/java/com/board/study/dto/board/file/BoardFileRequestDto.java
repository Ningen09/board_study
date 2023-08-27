package com.board.study.dto.board.file;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BoardFileRequestDto {
    
    // ID
    private Long id;
    
    // IDの配列
    private Long[] idArr;
    
    // ファイルID
    private String fileId;
}