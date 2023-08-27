package com.board.study.entity.board.file;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import org.springframework.data.annotation.CreatedDate;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class BoardFile {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;  // ファイルの識別子
	
	private Long boardId;  // 掲示板の識別子
	private String origFileName;  // 元ファイル名
	private String saveFileName;  // 保存されたファイル名
	private int fileSize;  // ファイルのサイズ
	private String fileExt;  // ファイルの拡張子
	private String filePath;  // ファイルのパス
	private String deleteYn;  // 削除の可否
	
	@CreatedDate
	private LocalDateTime registerTime;  // 登録時刻
	
	@Builder
	public BoardFile(Long id, Long boardId, String origFileName, String saveFileName, int fileSize, String fileExt,
			String filePath, String deleteYn, LocalDateTime registerTime) {
		this.id = id;
		this.boardId = boardId;
		this.origFileName = origFileName;
		this.saveFileName = saveFileName;
		this.fileSize = fileSize;
		this.fileExt = fileExt;
		this.filePath = filePath;
		this.deleteYn = deleteYn;
		this.registerTime = registerTime;
	}
}