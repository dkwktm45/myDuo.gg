package com.project.MyDuo.service;

import com.project.MyDuo.dao.BoardRepository;
import com.project.MyDuo.dto.BoardDto;
import com.project.MyDuo.entity.Board;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardService {
	private final BoardRepository boardRepository;

	public void createBoard(BoardDto boardDto){
		boardDto.setBoardRegDt(LocalDate.now());
		boardDto.setBoardUuid(UUID.randomUUID().toString());
		Board board = new Board(boardDto);
		boardRepository.save(board);
	}

	public List<BoardDto> findAllBoard(){
		return boardRepository.findAll().stream().map(BoardDto::new).collect(Collectors.toList());
	}
	public Board getOne(String boardUuid){
		return boardRepository.findByBoardUuid(boardUuid).orElseThrow(NullPointerException::new);
	}

	public BoardDto getBoardId(Long participantId){
		BoardDto board = new BoardDto(boardRepository.findByParticipantId(participantId).get());
		return board;
	}

}