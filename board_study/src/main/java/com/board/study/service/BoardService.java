package com.board.study.service;

import java.util.HashMap;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.board.study.dto.board.BoardRequestDto;
import com.board.study.dto.board.BoardResponseDto;
import com.board.study.entity.board.Board;
import com.board.study.entity.board.BoardRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BoardService {
	
	private final BoardRepository boardRepository = null;
	
	@Transactional
	public Long save(BoardRequestDto boardSaveDto) {
		return boardRepository.save(boardSaveDto.toEntity()).getId();
	}
	
	/*기존의 서비스에서 페이징 처리를 위한 설정 추가,
	 * Spring Data JPA에서는 PageRequest객체를 지원하기에 간단한 페이징 처리가 가능하다*/
	
	@Transactional(readOnly = true)
//	public List < BoardResponseDto > findAll(){
//		return boardRepository.findAll().stream().map(BoardResponseDto::new).collect(Collectors.toList());
//	}
	
	public HashMap<String, Object> findAll(Integer page, Integer size) {
		
		HashMap <String, Object> resultMap = new HashMap<String, Object>();
		
		Page <Board> list = boardRepository.findAll(PageRequest.of(page, size));
		
		resultMap.put("list", list.stream().map(BoardResponseDto::new).collect(Collectors.toList()));
		resultMap.put("paging", list.getPageable());
		resultMap.put("totalCnt", list.getTotalElements());
		resultMap.put("totalPage", list.getTotalPages());
		
		return resultMap;
		
	}
	
	public BoardResponseDto findById(Long id) {
		return new BoardResponseDto(boardRepository.findById(id).get());
	}
	
	public int updateBoard(BoardRequestDto boardRequestDto) {
		return boardRepository.updateBoard(boardRequestDto);
	}
	
	public int updateBoardReadCntInc(Long id) {
		return boardRepository.updateBoardReadCntInc(id);
	}
	
	public void deleteById(Long id) {
		boardRepository.deleteById(id);
	}
	
	public void deleteAll(Long[] deleteId) {
		boardRepository.deleteBoard(deleteId);
	}
	
}
