package com.project.MyDuo.controller;

import com.project.MyDuo.customException.boardNotExistException;
import com.project.MyDuo.customException.userInvalidException;
import com.project.MyDuo.dto.Board.BoardBarDto;
import com.project.MyDuo.dto.Board.BoardDetailBaseDto;
import com.project.MyDuo.dto.Board.BoardCreationDto;
import com.project.MyDuo.dto.Board.BoardDetailDto;
import com.project.MyDuo.dto.BoardDto;
import com.project.MyDuo.dto.LoL.Info.LoLNameAndPuuidDto;
import com.project.MyDuo.dto.Mapper;
import com.project.MyDuo.dto.PageableDto;
import com.project.MyDuo.entity.Member;
import com.project.MyDuo.entity.Board;
import com.project.MyDuo.entity.LoLAccount.LoLAccount;
import com.project.MyDuo.security.AuthUser;
import com.project.MyDuo.service.BoardService;
import com.project.MyDuo.service.LoLAccountService;
import com.project.MyDuo.service.MemberRepositoryService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Slice;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(value = "/board")
public class BoardController<UserService> {
	private final Logger logger = LoggerFactory.getLogger(BoardController.class);

	private final LoLAccountService loLAccountService;
	private final BoardService boardService;
	private final MemberRepositoryService memberRepositoryService;
	private final Mapper mapper;

	@GetMapping("/create") @ResponseBody
	public List<LoLNameAndPuuidDto> getLoLAccountInfos(@AuthUser Member member) {
		return loLAccountService.getSimpleLoLAccountInfos(member.getEmail());
	}

	@PostMapping("/create") @ResponseBody @Transactional
	@Operation(summary = "게시판 저장", description = "userId를 포함한 데이터를 넘겨야 합니다.")
	public String createBoard(@AuthUser Member member, @RequestBody BoardCreationDto boardCreationDto){
		//user가 비활성화 된 경우 처리.
		if(!member.getValid())
			throw new userInvalidException("회원 계정에 이상이 있습니다.");

		//@Auth...로 업데이트 되면 업데이트 예정.
		Member user = memberRepositoryService.findMember(member.getEmail());

		//user정보를 가지고 Board객체를 생성하는 과정.
		Board board = mapper.toBoard(user, boardCreationDto);
		boardService.save(board, user);

		return board.getUuid();
	}

	@GetMapping("/detail/{boardUUID}") @ResponseBody @Transactional
	public BoardDetailDto getBoardInfo(@PathVariable String boardUUID) {
		BoardDetailBaseDto boardDetailBaseDtoDto = boardService.findBoardDetailBaseDto(boardUUID);
		if (boardDetailBaseDtoDto == null || !boardDetailBaseDtoDto.getUserValidStatus())
			throw new boardNotExistException("게시글이 유효하지 않습니다.");

		log.info("{}", boardDetailBaseDtoDto);
		LoLAccount loLAccount = loLAccountService.findByPuuid(boardDetailBaseDtoDto.getLolPuuid());
		log.info("{},{}", loLAccount.getUser().getEmail(), boardDetailBaseDtoDto.getEmail());
		if(!loLAccount.isValid() || !loLAccount.getUser().getEmail().equals(boardDetailBaseDtoDto.getEmail()))
			throw new boardNotExistException("게시글이 유효하지 않습니다.");

		return new BoardDetailDto(
				boardDetailBaseDtoDto.getUserName(),
				boardDetailBaseDtoDto.getContent(),
				(long) boardDetailBaseDtoDto.getHeart(),
				mapper.toLoLAccountInfoDto(loLAccount),
				boardDetailBaseDtoDto.getMicEnabled()
		);
	}

	@PostMapping("/all") @ResponseBody
	public List<BoardDto> deleteBoard() {
		return boardService.findAllBoard();
	}

	@Operation(summary = "채팅 버튼권한 확인용", description = "participantId를 권한 확인을 위한 게시판 정보")
	@PostMapping(value = "/one")
	public ResponseEntity<Map<String,Object>> findById(@RequestBody String participantUuid){
		logger.info("board-Id");
		Map<String,Object> result = new HashMap<>();
		BoardDto board = boardService.getBoardId(participantUuid);
		result.put("board" , board);
		result.put("userName",board.getAccountDto().getName());
		return ResponseEntity.ok(result);
	}

	@GetMapping("/list")
	public Slice<BoardBarDto> getBoardBarLists(PageableDto pageableDto) {
		return boardService.getLatestBoardBars(mapper.toInitPageable(pageableDto));
	}

	@GetMapping("/scroll")
	public Slice<BoardBarDto> getMoreBoardBarLists(PageableDto pageableDto) {
		return boardService.getOldBoardBars(pageableDto.getRegistrationTime(), mapper.toInitPageable(pageableDto));
	}

	@GetMapping("/renew")
	public Slice<BoardBarDto> getNewBoardBarLists(Long registrationTime) {
		return boardService.getNewBoardBars(registrationTime);
	}
}
