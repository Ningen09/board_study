package com.board.study.web;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URLEncoder;

import jakarta.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.board.study.dto.board.file.BoardFileRequestDto;
import com.board.study.dto.board.file.BoardFileResponseDto;
import com.board.study.service.BoardFileService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class BoardFileController {
	
	private final BoardFileService boardFileService;
	
	@GetMapping("/file/download")
	public void downloadFile(@RequestParam() Long id, HttpServletResponse response) throws Exception {
		try {
			// ファイル情報を取得します。
			BoardFileResponseDto fileInfo = boardFileService.findById(id);
			
			if (fileInfo == null) throw new FileNotFoundException("ファイルデータがありません。");
			
			// ファイルパスとファイル名でファイルオブジェクトを作成します。
			File dFile  = new File(fileInfo.getFilePath(), fileInfo.getSaveFileName());
			
			// ファイルの長さを取得します。
			int fSize = (int) dFile.length();
			
			// ファイルが存在する場合
			if (fSize > 0) {
				// ファイル名をURLEncoderしてattachment、Content-Dispositionヘッダーに設定します。
				String encodedFilename = "attachment; filename*=" + "UTF-8" + "''" + URLEncoder.encode(fileInfo.getOrigFileName(), "UTF-8");
				
				// ContentTypeを設定します。
				response.setContentType("application/octet-stream; charset=utf-8");
				
				// ヘッダーを設定します。
				response.setHeader("Content-Disposition", encodedFilename);
				
				// ContentLengthを設定します。
				response.setContentLengthLong(fSize);
	
				BufferedInputStream in = null;
				BufferedOutputStream out = null;
				
				/* BufferedInputStream
				 * 
				 * java.ioの基本的なファイル入出力クラス
				 * 入力ストリーム（パイプライン）を生成します
				 * 使用方法は簡単ですが、バッファを使用しないため、遅いです
				 * パフォーマンスの問題を解決するために、バッファを使用する別のクラスと一緒に使用されることが多いです */
				in = new BufferedInputStream(new FileInputStream(dFile));
				
				/* BufferedOutputStream
				 * 
				 * java.ioの基本的なファイル入出力クラス
				 * 出力ストリーム（パイプライン）を生成します
				 * 使用方法は簡単ですが、バッファを使用しないため、遅いです
				 * パフォーマンスの問題を解決するために、バッファを使用する別のクラスと一緒に使用されることが多いです */
				out = new BufferedOutputStream(response.getOutputStream());
				
				try {
					byte[] buffer = new byte[4096];
				 	int bytesRead=0;
				 	
				 	/*
					 * すべて現在のファイルポインタ位置を基準にします（ファイルポインタの前に内容はないように動作します）
					 * int read()：1バイトずつ内容を読み取り、整数として返します
					 * int read(byte[] b)：ファイルの内容を一度にすべて読み取り、配列に保存します
					 * int read(byte[] b、int off、int len)：'len'だけを読み取って配列の'off'番目の位置に保存します */
				 	while ((bytesRead = in.read(buffer))!=-1) {
						out.write(buffer, 0, bytesRead);
					}
					
				 	// バッファに残っている内容がある場合、すべてファイルに出力します					
				 	out.flush();
				}
				finally {
					/*
					 * 現在開かれたin、outストリームを閉じます
					 * メモリリークを防ぎ、他の場所でリソースを使用できるようにします */
					in.close();
					out.close();
				}
		    } else {
		    	throw new FileNotFoundException("ファイルデータがありません。");
		    }
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}
	
	@PostMapping("/file/delete.ajax")
	public String updateDeleteYn(Model model, BoardFileRequestDto boardFileRequestDto) throws Exception {
		try {
			model.addAttribute("result", boardFileService.updateDeleteYn(boardFileRequestDto.getIdArr()));
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		
		return "jsonView";
	}
}
