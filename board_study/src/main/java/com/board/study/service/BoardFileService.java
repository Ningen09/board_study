package com.board.study.service;

import java.io.File;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.board.study.dto.board.file.BoardFileResponseDto;
import com.board.study.entity.board.file.BoardFile;
import com.board.study.entity.board.file.BoardFileRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BoardFileService {
	
	private final BoardFileRepository boardFileRepository;
	
	public BoardFileResponseDto findById(Long id) throws Exception {
		return new BoardFileResponseDto(boardFileRepository.findById(id).get());
	}
	
	public List<Long> findByBoardId(Long boardId) throws Exception {
		return boardFileRepository.findByBoardId(boardId);
	}
	
	public boolean uploadFile(MultipartHttpServletRequest multiRequest, Long boardId) throws Exception {
		
		if (boardId == null) throw new NullPointerException("Empty BOARD_ID.");
		
		// パラメータ名をキーとして、ファイル情報を値とする Map を取得します。
		Map<String, MultipartFile> files = multiRequest.getFileMap();
		
		// files.entrySet() の要素を読み込みます。
		Iterator<Entry<String, MultipartFile>> itr = files.entrySet().iterator();
		
		MultipartFile mFile;
		
		String savaFilePath = "", randomFileName = "";
		
		Calendar cal = Calendar.getInstance();

		List<Long> resultList = new ArrayList<Long>();
		
		while (itr.hasNext()) {
		
			Entry<String, MultipartFile> entry = itr.next();
	
			mFile = entry.getValue();
			
			int fileSize = (int) mFile.getSize();
			
			if (fileSize > 0) {
				
				String filePath = "C:\\dev_tools\\eclipse\\workspace\\uploadFiles\\";
				
				// ファイルアップロードパス + 現在の年月（月ごとの管理）
				filePath = filePath + File.separator + String.valueOf(cal.get(Calendar.YEAR)) + File.separator + String.valueOf(cal.get(Calendar.MONTH) + 1);
				randomFileName = "FILE_" + RandomStringUtils.random(8, 0, 0, false, true, null, new SecureRandom());
				
				String realFileName = mFile.getOriginalFilename();
				String fileExt = realFileName.substring(realFileName.lastIndexOf(".") + 1);
				String saveFileName = randomFileName + "." + fileExt;
				String saveFilePath = filePath + File.separator + saveFileName;
				
				File filePyhFolder = new File(filePath);
				
				if (!filePyhFolder.exists()) {
					// 親フォルダも含めてパスにフォルダを作成します。
					if (!filePyhFolder.mkdirs()) {
						throw new Exception("File.mkdir() : Fail."); 
					}
				}
				
				File saveFile = new File(saveFilePath);
				
				// saveFile が File の場合は true、それ以外は false
				// ファイル名が重複する場合は、ファイル名(1).拡張子、ファイル名(2).拡張子 などの形式で生成します。
				if (saveFile.isFile()) {
					boolean _exist = true;
					
					int index = 0;
					
					// 同じファイル名が存在しない限りループします。
					while (_exist) {
						index++;
						
						saveFileName = randomFileName + "(" + index + ")." + fileExt;
						
						String dictFile = filePath + File.separator + saveFileName;
						
						_exist = new File(dictFile).isFile();
						
						if (!_exist) {
							savaFilePath = dictFile;
						}
					}
					
					mFile.transferTo(new File(savaFilePath));
				} else {
					// 作成したファイルオブジェクトをアップロード処理しない場合、一時ファイルに保存されたファイルは自動的に削除されるため、transferTo(File f) メソッドを使用してアップロード処理します。
					mFile.transferTo(saveFile);
				}
				
				BoardFile boardFile = BoardFile.builder()
						.boardId(boardId)
						.origFileName(realFileName)
						.saveFileName(saveFileName)
						.fileSize(fileSize)
						.fileExt(fileExt)
						.filePath(filePath)
						.deleteYn("N")
						.build();
				
				resultList.add(boardFileRepository.save(boardFile).getId());
			}
		}
		
		return (files.size() == resultList.size()) ? true : false;
	}
	
	public int updateDeleteYn(Long[] deleteIdList) throws Exception {
		return boardFileRepository.updateDeleteYn(deleteIdList);
	}
	
	public int deleteBoardFileYn(Long[] boardIdList) throws Exception {
		return boardFileRepository.deleteBoardFileYn(boardIdList);
	}
}
