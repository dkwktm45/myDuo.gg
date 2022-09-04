package com.project.MyDuo.controller;

import com.project.MyDuo.dto.BoardDto;
import com.project.MyDuo.entity.Board;
import com.project.MyDuo.service.BoardService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(value = "board")
public class BoardController {
	private final Logger logger = LoggerFactory.getLogger(BoardController.class);

	private final BoardService boardService;
//	private final JwtTokenProvider jwtTokenProvider;

	@PostMapping(value = "/create")
	@Operation(summary = "게시판 저장", description = "userId를 포함한 데이터를 넘겨야 합니다.")
	public void createBoard(@RequestBody @Valid BoardDto boardDto){
		logger.info("board-create");
		boardService.createBoard(boardDto);
	}

	@Operation(summary = "게시판 불러오기", description = "모든 게시판을 불러옵니다.")
	@PostMapping(value = "/all")
	public ResponseEntity<List<BoardDto>> findAll(){
		logger.info("board-all");
		return ResponseEntity.ok(boardService.findAllBoard());
	}

	@Operation(summary = "채팅 버튼권한 확인용", description = "participantId를 권한 확인을 위한 게시판 정보 ")
	@PostMapping(value = "/one")
	public ResponseEntity<Map<String,Object>> findById(@RequestBody Long participantId){
		logger.info("board-Id");
		Map<String,Object> result = new HashMap<>();
		BoardDto board = boardService.getBoardId(participantId);
		result.put("board" , board);
		result.put("userName",board.getAccountDto().getName());
		return ResponseEntity.ok(result);
	}
}
