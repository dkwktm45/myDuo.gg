package com.project.MyDuo.service;

import com.project.MyDuo.dao.BoardRepository;
import com.project.MyDuo.dto.BoardDto;
import com.project.MyDuo.entity.Account;
import com.project.MyDuo.entity.Board;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service @Transactional
@RequiredArgsConstructor
public class BoardService {
	private final Logger logger = LoggerFactory.getLogger(BoardService.class);
	private final BoardRepository boardRepository;

	/*public void createBoard(BoardDto boardDto ,Authentication authentication){
		logger.info("[서비스] : 게시판 생성 시작");
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		Account account = ((CustomUser) userDetails).getAccount();

		boardDto.setBoardRegDt(LocalDate.now());
		AccountDto accountDto = new AccountDto(account);
		boardDto.setBoardUuid(UUID.randomUUID().toString());
		boardDto.setAccountDto(accountDto);

		Board board = new Board(boardDto);
		boardRepository.save(board);
		logger.info("[서비스] : 게시판 생성 끝");
	}*/
	public void save(Board board, Account owner) {
		owner.addBoard(boardRepository.save(board));
	}

	public List<BoardDto> findAllBoard(){
		return boardRepository.findAll().stream().map(BoardDto::new).collect(Collectors.toList());
	}
	public Board getOne(String boardUuid){
		return boardRepository.findByUuid(boardUuid).orElseThrow(NullPointerException::new);
	}
	public BoardDto getBoardId(String participantUuid){
		BoardDto board = new BoardDto(boardRepository.findByParticipantUuId(participantUuid).get());
		return board;
	}
}