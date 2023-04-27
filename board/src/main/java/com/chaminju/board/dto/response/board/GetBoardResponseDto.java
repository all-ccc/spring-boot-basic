package com.chaminju.board.dto.response.board;

import java.util.List;

import com.chaminju.board.dto.response.ResponseDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetBoardResponseDto extends ResponseDto { // 공통적으로 code, message가 들어가기 떄문에 상속 받음
    private int boardNumber;
    private String boardTitle;
    private String boardContent;
    private String boardImageUrl;
    private String boardWriteDatetime;
    private int viewCount;
    private String boardWriterEmail;
    private String boardWriterNickname;
    private String boardWriterProfileImageUrl;
    private List<Comment> commentList;
    private List<Likey> likeList;
}

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
class Comment { // 여기서만 쓸 거
    private int commentNumber;
    private int boardNumber;
    private String commentWriterEmail;
    private String commentContent;
    private String commentWriterNickname;
    private String commentWriterProfileImageUrl;
    private String commentWriterDatetime;
}

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
class Likey {
    private int boardNumber;
    private String userEmail;
    private String userNickname;
    private String userProfileImageUrl;
}
