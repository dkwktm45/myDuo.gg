package com.project.MyDuo.dao;

import com.project.MyDuo.dto.BoardParticipantsDto;
import com.project.MyDuo.entity.BoardParticipants;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BoardParticipantsRepository extends JpaRepository<BoardParticipants, Long> {
	@EntityGraph(attributePaths = "board")
	Optional<List<BoardParticipants>> findByUserId(Long userId);

	Optional<BoardParticipants> findByRoomId(String roomId);

	@Query(value = "select * from board_participants p " +
			"where p.board_id in (select b.board_id from board b "+
			"where b.user_id = :user_id)" , nativeQuery = true)
	List<BoardParticipants> findByBoard(@Param("user_id") Long userId);


}
