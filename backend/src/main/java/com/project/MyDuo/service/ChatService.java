package com.project.MyDuo.service;

import com.project.MyDuo.dao.FriendRepository;
import com.project.MyDuo.dao.MemberRepository;
import com.project.MyDuo.entity.Friend;
import com.project.MyDuo.entity.Member;
import com.project.MyDuo.entity.redis.ChatRoom;
import com.project.MyDuo.service.redis.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChatService {


	private final ChatRoomRepository chatRoomRepository;
	private final FriendRepository friendRepository;
	private final MemberRepository memberRepository;
	/**
	 * destination정보에서 roomId 추출
	 */
	public String getRoomId(String destination) {
		int lastIndex = destination.lastIndexOf('/');
		if (lastIndex != -1)
			return destination.substring(lastIndex + 1);
		else
			return "";
	}
	public void createFriendChat(Member member, String email) {

		ChatRoom chatRoom = ChatRoom.create();
		Set<String> userList = new HashSet<>();
		userList.add(email);
		userList.add(member.getEmail());
		chatRoom.setUserList(userList);
		chatRoomRepository.save(chatRoom);

		Friend friend = friendRepository.findByMemberAndPriendEmail(member,email);
		friend.toSetRoomId(chatRoom.getRoomId());
		friendRepository.save(friend);


		Friend otherFriend = memberRepository.findMemberByEmail(email).getFriends()
				.stream().filter(info -> info.getPriendEmail().equals(member.getEmail())).findFirst()
				.orElseThrow(() -> new IllegalArgumentException());

		otherFriend.toSetRoomId(chatRoom.getRoomId());

		friendRepository.save(otherFriend);
	}

	public ChatRoom createDuoChat(){
		return chatRoomRepository.createChatRoom();
	}

	public void deleteRoom(String chatRoomId) {
		chatRoomRepository.deleteRoom(chatRoomId);
	}

	public ChatRoom findRoom(String roomId){return chatRoomRepository.findRoomById(roomId);}

	public void updateRoom(ChatRoom chatRoom){
		chatRoomRepository.updateChatRoom(chatRoom);
	}

	public long infoCount(String roomId) {return chatRoomRepository.getUserCount(roomId);}

	public long plusCount(String roomId) {return chatRoomRepository.plusUserCount(roomId);}

	public long minusCount(String roomId) {return chatRoomRepository.minusUserCount(roomId);}

}
