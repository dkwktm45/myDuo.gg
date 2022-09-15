package com.project.MyDuo.service;

import com.project.MyDuo.dao.BoardRepository;
import com.project.MyDuo.dto.Board.BoardBarDto;
import com.project.MyDuo.dto.Board.BoardDetailBaseDto;
import com.project.MyDuo.dto.BoardDto;
import com.project.MyDuo.entity.Member;
import com.project.MyDuo.entity.Board;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Service @Transactional
@RequiredArgsConstructor
public class BoardService {
	private final Logger logger = LoggerFactory.getLogger(BoardService.class);
	private final BoardRepository boardRepository;

	public void save(Board board, Member owner) {
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

    public Slice<BoardBarDto> getLatestBoardBars(Pageable pageable) {
		return boardRepository.findLatestBoardBars(new Timestamp(7974251905000L), pageable);
    }

	public Slice<BoardBarDto> getNewBoardBars(Long timeStamp) {
		return boardRepository.findNewBoardBars(new Timestamp(timeStamp));
	}

	public Slice<BoardBarDto> getOldBoardBars(Long timeStamp, Pageable pageable) {
		return boardRepository.findOldBoardBars(new Timestamp(timeStamp), pageable);
	}

	public List<BoardBarDto> getMyBoardBars(String email) {
		return boardRepository.findMyBoardBars(email);
	}
	public BoardDetailBaseDto findBoardDetailBaseDto(String boardUUID) {
		return boardRepository.findValidBoardDetailInfo(boardUUID);
	}
}