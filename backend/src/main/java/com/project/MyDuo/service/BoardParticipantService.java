package com.project.MyDuo.service;

import com.project.MyDuo.dao.BoardParticipantsRepository;
import com.project.MyDuo.dao.BoardRepository;
import com.project.MyDuo.dto.BoardParticipantsDto;
import com.project.MyDuo.entity.Member;
import com.project.MyDuo.entity.Board;
import com.project.MyDuo.entity.BoardParticipants;
import com.project.MyDuo.entity.redis.ChatMessage;
import com.project.MyDuo.security.CustomUser;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class BoardParticipantService {
	private final Logger logger = LoggerFactory.getLogger(BoardParticipantService.class);


	private final BoardParticipantsRepository participantsRepository;
	private final BoardService boardService;
	private final BoardRepository boardRepository;
	private final ChatService chatService;
	private final ChatMessageService chatMessageService;

	public Map<String, Object> setChat(String boardUuid, String chatRoomId, Authentication authentication) throws Exception {
		logger.info("setChat start");

		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		Member member = ((CustomUser) userDetails).getMember();
		Board board = boardService.getOne(boardUuid);
		if(board.getBoardParticipantsList().stream().anyMatch(info -> info.getUserId().equals(member.getId())) && board.getBoardParticipantsList() != null){
			throw new Exception("이미 존재하는 회원입니다.");
		}else if(board.getClosingStatus()) {
			chatService.deleteRoom(chatRoomId);
			throw new Exception("이미 해당 유저는 듀오를 결성했습니다.");
		}else {
			BoardParticipants boardParticipants = BoardParticipants.builder()
					.board(board).participantUuid(UUID.randomUUID().toString())
					.userName(member.getName())
					.userId(member.getId())
					.roomId(chatRoomId)
					.build();
			participantsRepository.save(boardParticipants);

			Map<String, Object> result = new HashMap<>();
			result.put("boardId",boardParticipants.getBoard().getId());
			result.put("participants", boardParticipants);

			logger.info("setChat start");
			return result;
		}
	}

	public List<BoardParticipantsDto> myChatRoom(Authentication authentication){
		logger.info("myChatRoom start");
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		Member member = ((CustomUser) userDetails).getMember();

		List<BoardParticipantsDto> boardList = participantsRepository.findByBoard(member.getId())
				.stream().map(BoardParticipantsDto::new)
				.collect(Collectors.toList());

		logger.info("myChatRoom complete");
		return boardList;
	}

	public List<BoardParticipantsDto> otherChatRoom(Authentication authentication){
		logger.info("otherChatRoom start");

		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		Member member = ((CustomUser) userDetails).getMember();

		List<BoardParticipantsDto> participantsList = participantsRepository.findByUserId(member.getId())
				.get().stream().map(BoardParticipantsDto::new).collect(Collectors.toList());


		logger.info("otherChatRoom complete");
		return participantsList;
	}

	public void deleteRoom(String boardUuid, String participantUuid, Member member) {
		logger.info("deleteRoom start");
		Board board = boardRepository.findByUuid(boardUuid).get();
		board.changeStatus(false);
		List<BoardParticipants> boardParticipantsList = board.getBoardParticipantsList()
				.stream().filter(info-> info.getParticipantUuid() !=participantUuid).collect(Collectors.toList());

		boardParticipantsList.stream().forEach(info -> info.setBoard(null));

		for(BoardParticipants participants : boardParticipantsList){
			chatMessageService.sendChatMessage(
					ChatMessage.builder()
							.sender(board.getName())
							.message(board.getMember().getName() + "님이 다른회원과 매칭이 되었습니다. 다음기회에 도전해주세요!")
							.roomId(participants.getRoomId())
							.build(),
					member);
		}

		participantsRepository.saveAll(boardParticipantsList);
		logger.info("deleteRoom end");
	}

	public BoardParticipantsDto findByRoomId(String roomId) {
		Optional<BoardParticipants> participantsOptional = participantsRepository.findByRoomId(roomId);
		if(participantsOptional.isPresent()){
			return new BoardParticipantsDto(participantsOptional.get());
		}else{
			return null;
		}
	}
}
