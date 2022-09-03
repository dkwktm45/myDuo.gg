package com.project.MyDuo.controller;

import com.project.MyDuo.dto.BoardDto;
import com.project.MyDuo.entity.Board;
import com.project.MyDuo.entity.User;
import com.project.MyDuo.service.BoardService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequiredArgsConstructor
public class BoardController {
	private final Logger logger = LoggerFactory.getLogger(BoardController.class);

	private final BoardService boardService;
//	private final JwtTokenProvider jwtTokenProvider;

	@PostMapping(value = "/board-create")
	@Operation(summary = "게시판 저장", description = "userId를 포함한 데이터를 넘겨야 합니다.")
	public void createBoard(@RequestBody @Valid BoardDto boardDto){
		logger.info("board-create");
		boardService.createBoard(boardDto);
	}

	@PostMapping(value = "/board-all")
	public ResponseEntity<List<Board>> findAll(){
		logger.info("board-all");
		return ResponseEntity.ok(boardService.findAllBoard());
	}

	@PostMapping(value = "/board-id")
	public ResponseEntity<Map<String,Object>> findById(@RequestBody Long participantId){
		logger.info("board-Id");
		Map<String,Object> result = new HashMap<>();
		Board board = boardService.getBoardId(participantId);
		result.put("board" , board);
		result.put("userName",board.getUser().getName());
		return ResponseEntity.ok(result);
	}

}
