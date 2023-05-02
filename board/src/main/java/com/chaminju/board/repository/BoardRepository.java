package com.chaminju.board.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.chaminju.board.entity.BoardEntity;
import com.chaminju.board.entity.resultSet.BoardListResultSet;

@Repository
public interface BoardRepository extends JpaRepository<BoardEntity, Integer> {
    
    public BoardEntity findByBoardNumber(int boardNumber); // 하나만 반환돼서 리스트 지정 필요 X

    @Query(
        value =
        "SELECT " + // 띄어쓰기 잘 해주기!
        "B.board_number AS boardNumber," +
        "B.title AS boardTitle," +
        "B.content AS boardContent," +
        "B.board_image_url AS boardImageUrl," +
        "B.write_datetime AS boardWriteDatetime," +
        "B.view_count AS viewCount," +
        "U.email AS boardWriterEmail," +
        "U.nickname AS boardWriterNickname," +
        "U.profile_image_url AS boardWriterProfileImageUrl," +
        "count(DISTINCT C.comment_number) AS commentCount," +
        "count(DISTINCT L.user_email) AS likeCount " +
        "FROM Board B, Comment C, Likey L, User U " +
        "WHERE B.board_number = C.board_number " +
        "AND B.board_number = L.board_number " +
        "AND B.writer_email = U.email " +
        "GROUP BY B.board_number " +
        "ORDER BY boardWriteDatetime DESC ",
        nativeQuery = true
    )
    public List<BoardListResultSet> getList(); // 우리가 쿼리 짤 거임
    @Query(
        value =
        "SELECT " + // 띄어쓰기!
        "B.board_number AS boardNumber," +
        "B.title AS boardTitle," +
        "B.content AS boardContent," +
        "B.board_image_url AS boardImageUrl," +
        "B.write_datetime AS boardWriteDatetime," +
        "B.view_count AS viewCount," +
        "U.email AS boardWriterEmail," +
        "U.nickname AS boardWriterNickname," +
        "U.profile_image_url AS boardWriterProfileImageUrl," +
        "count(DISTINCT C.comment_number) AS commentCount," +
        "count(DISTINCT L.user_email) AS likeCount " +
        "FROM Board B, Comment C, Likey L, User U " +
        "WHERE B.board_number = C.board_number " +
        "AND B.board_number = L.board_number " +
        "AND B.writer_email = U.email " +
        "GROUP BY B.board_number " +
        "ORDER BY likeCount DESC " +
        "LIMIT 3", // 상위 3개만 뽑겠다
        nativeQuery = true
    )
    public List<BoardListResultSet> getTop3List();
}
