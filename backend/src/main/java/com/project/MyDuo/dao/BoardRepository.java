package com.project.MyDuo.dao;

import com.project.MyDuo.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board,Long> {

	@Query(value = "select * from board b " +
			"where b.board_id = (select board_id from board_participants p "+
			"where p.participant_id = :participant_id)",nativeQuery = true)
	Optional<Board> findByParticipantId(@Param("participant_id") Long participantId);
}
