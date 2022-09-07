package com.project.MyDuo.controller;

import com.project.MyDuo.customException.boardNotExistException;
import com.project.MyDuo.customException.userInvalidException;
import com.project.MyDuo.dto.Board.BoardCreationDto;
import com.project.MyDuo.dto.Board.BoardDetailDto;
import com.project.MyDuo.dto.Mapper;
import com.project.MyDuo.entity.Account;
import com.project.MyDuo.entity.Board;
import com.project.MyDuo.entity.LoLAccount.LoLAccount;
import com.project.MyDuo.jwt.JwtTokenUtil;
import com.project.MyDuo.service.BoardService;
import com.project.MyDuo.service.LoLAccoutService.LoLAccountService;
import com.project.MyDuo.service.UserRepositoryService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(value = "/board")
public class BoardController<UserService> {
	private final Logger logger = LoggerFactory.getLogger(BoardController.class);

	private final LoLAccountService loLAccountService;
	private final BoardService boardService;
	private final JwtTokenUtil jwtTokenUtil;
	private final UserRepositoryService userRepositoryService;
	private final Mapper mapper;

	/*@PostMapping(value = "/create")
	@Operation(summary = "게시판 저장", description = "userId를 포함한 데이터를 넘겨야 합니다.")
	public void createBoard(@RequestBody @Valid BoardDto boardDto, Authentication authentication){
		logger.info("board-create");
		boardService.createBoard(boardDto,authentication);
	}*/

	@GetMapping("/create") @ResponseBody
	public Map<String, String> getLoLAccountInfos(@RequestHeader String authorization) {
		//@Auth...로 업데이트 되면 업데이트 예정.
		String email = jwtTokenUtil.getEmail(authorization.split(" ")[1]);
		return loLAccountService.getSimpleLoLAccountInfos(email);
	}

	@PostMapping("/create") @ResponseBody @Transactional
	@Operation(summary = "게시판 저장", description = "userId를 포함한 데이터를 넘겨야 합니다.")
	public String createBoard(@RequestBody BoardCreationDto boardCreationDto, @RequestHeader String authorization){
		//@Auth...로 업데이트 되면 업데이트 예정.
		String email = jwtTokenUtil.getEmail(authorization.split(" ")[1]);
		Account user = userRepositoryService.findByEmail(email);

		//user가 비활성화 된 경우 처리.
		if(!user.getValid())
			throw new userInvalidException("회원 계정에 이상이 있습니다.");

		//user정보를 가지고 Board객체를 생성하는 과정.
		Board board = mapper.toBoard(user, boardCreationDto);
		boardService.save(board, user);
		return board.getUuid();
	}

	@GetMapping("/detail/{boardUUID}") @ResponseBody @Transactional
	public BoardDetailDto getBoardInfo(@PathVariable String boardUUID) {
		Board board = boardService.getOne(boardUUID);
		if (board == null)
			throw new boardNotExistException("게시글이 유효하지 않습니다.");

		Account user = userRepositoryService.findByBoardList_Uuid(boardUUID);
		if (!user.getValid())
			throw new boardNotExistException("게시글이 유효하지 않습니다.");

		LoLAccount loLAccount = loLAccountService.findByPuuid(board.getLolPuuid());
		if(!loLAccount.isValid() || !loLAccount.getUser().getEmail().equals(user.getEmail()))
			throw new boardNotExistException("게시글이 유효하지 않습니다.");

		return new BoardDetailDto(
				user.getName(),
				board.getContent(),
				(long) user.getHeart(),
				mapper.toLoLAccountInfoDto(loLAccount),
				board.getMicEnabled()
		);
	}

	/*@PostMapping("/{boarduuid}") @ResponseBody
	public String deleteBoard(@PathVariable String boarduuid) {
		boardService.deleteByuuid(boarduuid);
		return "게시글 삭제가 완료되었습니다";
	}*/
	/*@Operation(summary = "게시판 불러오기", description = "모든 게시판을 불러옵니다.")
	@PostMapping(value = "/all")
	public ResponseEntity<List<BoardDto>> findAll(){
		logger.info("board-all");
		return ResponseEntity.ok(boardService.findAllBoard());
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
	}*/
}
