package com.project.MyDuo.service;

import com.project.MyDuo.dao.BoardRepository;
import com.project.MyDuo.dto.BoardDto;
import com.project.MyDuo.entity.Board;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {
	private final BoardRepository boardRepository;

	public void createBoard(BoardDto boardDto){
		boardDto.setBoardRegDt(LocalDate.now());
		Board board = new Board(boardDto);
		boardRepository.save(board);
	}

	public List<Board> findAllBoard(){
		return boardRepository.findAll();
	}

	public Board getOne(Long boardId){
		return boardRepository.findById(boardId).orElseThrow(NullPointerException::new);
	}

	public Board getBoardId(Long participantId){
		Board board = boardRepository.findByParticipantId(participantId).orElseThrow(NullPointerException::new);
		return board;
	}

}