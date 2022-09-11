package com.project.MyDuo.dao;

import com.project.MyDuo.dto.Board.BoardBarDto;
import com.project.MyDuo.dto.Board.BoardDetailBaseDto;
import com.project.MyDuo.entity.Board;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board,Long> {

	@Query(value = "select * from board b " +
			"where b.board_id = (select board_id from board_participants p "+
			"where p.participant_uuid = :participant_uuid)",nativeQuery = true)
	Optional<Board> findByParticipantUuId(@Param("participant_uuid") String participantUuid);

	Optional<Board> findByUuid(String boardUuid);

	@Query(value = "select * from board b " +
			"where b.board_id = (select board_id from board_participants p "+
			"where p.user_id = :user_id)",nativeQuery = true)
	List<Board> findByOtherChat(@Param("user_id") Long id);

	@Query(value = "SELECT " +
			"new com.project.MyDuo.dto.Board.BoardDetailBaseDto(b.micEnabled, b.content, b.lolPuuid, m.name, m.email, m.heart, m.valid)" +
			"FROM Board b " +
			"INNER JOIN Member m " +
			"ON b.member.id = m.id AND b.uuid = :boardUUID")
	BoardDetailBaseDto findValidBoardDetailInfo(@Param("boardUUID") String boardUUID);

	@Override
	@EntityGraph(attributePaths = "member")
	List<Board> findAll();

	@Query(value = "SELECT " +
			"new com.project.MyDuo.dto.Board.BoardBarDto(b.closingStatus, l.name, l.tier, l.rank, b.myPositions, b.otherPositions, b.content, b.registrationTime, b.uuid)" +
			"FROM Board b " +
			"INNER JOIN LoLAccount l " +
			"ON b.lolPuuid = l.puuid AND b.registrationTime > :registrationTime")
	Slice<BoardBarDto> findNewBoardBars(@Param("registrationTime") Timestamp timestamp);

	@Query(value = "SELECT " +
			"new com.project.MyDuo.dto.Board.BoardBarDto(b.closingStatus, l.name, l.tier, l.rank, b.myPositions, b.otherPositions, b.content, b.registrationTime, b.uuid)" +
			"FROM Board b " +
			"INNER JOIN LoLAccount l " +
			"ON b.lolPuuid = l.puuid AND b.registrationTime < :registrationTime")
	Slice<BoardBarDto> findLatestBoardBars(@Param("registrationTime") Timestamp timestamp, Pageable pageable);

	@Query(value = "SELECT " +
			"new com.project.MyDuo.dto.Board.BoardBarDto(b.closingStatus, l.name, l.tier, l.rank, b.myPositions, b.otherPositions, b.content, b.registrationTime, b.uuid)" +
			"FROM Board b " +
			"INNER JOIN LoLAccount l " +
			"ON b.lolPuuid = l.puuid AND b.registrationTime < :registrationTime")
	Slice<BoardBarDto> findOldBoardBars(@Param("registrationTime") Timestamp timestamp, Pageable pageable);
}
