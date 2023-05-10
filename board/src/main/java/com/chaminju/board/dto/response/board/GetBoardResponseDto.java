package com.chaminju.board.dto.response.board;

import java.util.ArrayList;
import java.util.List;

import com.chaminju.board.dto.response.ResponseDto;
import com.chaminju.board.entity.BoardEntity;
import com.chaminju.board.entity.CommentEntity;
import com.chaminju.board.entity.LikeyEntity;
import com.chaminju.board.entity.UserEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetBoardResponseDto extends ResponseDto { // 공통적으로 code, message가 들어가기 때문에 상속 받음
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

    public GetBoardResponseDto(
        BoardEntity boardEntity, UserEntity userEntity, 
        List<CommentEntity> commentEntities, List<LikeyEntity> likeyEntities
        // entity를 반환하면 보안 상 잘못된 결과 초래 가능성 있음(ex. 개인정보)
    ) {
        super("SU", "Success");
         
        this.boardNumber = boardEntity.getBoardNumber();
        this.boardTitle = boardEntity.getTitle();
        this.boardContent = boardEntity.getContent();
        this.boardImageUrl = boardEntity.getBoardImageUrl();
        this.boardWriteDatetime = boardEntity.getWriteDatetime();
        this.viewCount = boardEntity.getViewCount();
        this.boardWriterEmail = userEntity.getEmail(); 
        this.boardWriterNickname = userEntity.getNickname();
        this.boardWriterProfileImageUrl = userEntity.getProfileImageUrl();
        this.commentList = Comment.createList(commentEntities);
        this.likeList = Likey.createList(likeyEntities);
    }
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

    Comment(CommentEntity commentEntity) {
        this.commentNumber = commentEntity.getCommentNumber();
        this.boardNumber = commentEntity.getBoardNumber();
        this.commentWriterEmail = commentEntity.getUserEmail();
        this.commentContent = commentEntity.getCommentContent();
        this.commentWriterNickname = commentEntity.getUserNickname();
        this.commentWriterProfileImageUrl = commentEntity.getUserProfileImageUrl();
        this.commentWriterDatetime = commentEntity.getWriteDatetime();
    }

    static List<Comment> createList(List<CommentEntity> commentEntities) {
        List<Comment> commentList = new ArrayList<>();
        for (CommentEntity commentEntity: commentEntities) {
            Comment comment = new Comment(commentEntity);
            commentList.add(comment);
        }
        return commentList;
    }
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

    Likey(LikeyEntity likeyEntity) {
        this.boardNumber = likeyEntity.getBoardNumber();
        this.userEmail = likeyEntity.getUserEmail();
        this.userNickname = likeyEntity.getUserNickname();
        this.userProfileImageUrl = likeyEntity.getUserProfileImageUrl();
    }

    static List<Likey> createList(List<LikeyEntity> likeyEntities) {
        List<Likey> likeList = new ArrayList<>();
        for (LikeyEntity likeEntity: likeyEntities) {
            Likey likey = new Likey(likeEntity);
            likeList.add(likey);
        }
        return likeList;
    }
}
