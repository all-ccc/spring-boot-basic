package com.chaminju.board.service.implement;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.chaminju.board.common.util.CustomResponse;
import com.chaminju.board.dto.request.board.PatchBoardRequestDto;
import com.chaminju.board.dto.request.board.PostBoardRequestDto;
import com.chaminju.board.dto.request.board2.PatchBoardRequestDto2;
import com.chaminju.board.dto.request.board2.PostBoardRequestDto2;
import com.chaminju.board.dto.response.ResponseDto;
import com.chaminju.board.dto.response.board.GetBoardListResponseDto;
import com.chaminju.board.dto.response.board.GetBoardResponseDto;
import com.chaminju.board.entity.BoardEntity;
import com.chaminju.board.entity.CommentEntity;
import com.chaminju.board.entity.LikeyEntity;
import com.chaminju.board.entity.UserEntity;
import com.chaminju.board.entity.resultSet.BoardListResultSet;
import com.chaminju.board.repository.BoardRepository;
import com.chaminju.board.repository.CommentRepository;
import com.chaminju.board.repository.LikeyRepository;
import com.chaminju.board.repository.UserRepository;
import com.chaminju.board.service.BoardService;

@Service
public class BoardServiceImplement implements BoardService {
    // 생성자 우리가 만들지 않는 방법: 여기 autowired 걸거나 final 지정하고 @requiredArgConst
    // 근데 공부할 때는 생성자 걍 만들어서 함
    private UserRepository userRepository;
    private BoardRepository boardRepository;
    private CommentRepository commentRepository;
    private LikeyRepository likeyRepository;

    @Autowired
    public BoardServiceImplement(
        UserRepository userRepository,
        BoardRepository boardRepository,
        CommentRepository commentRepository,
        LikeyRepository likeyRepository
    ) {
        this.userRepository = userRepository;
        this.boardRepository = boardRepository;
        this.commentRepository = commentRepository;
        this.likeyRepository = likeyRepository;
    }

    @Override
    public ResponseEntity<ResponseDto> postBoard(PostBoardRequestDto dto) {
        // service 작업 전 기본틀 맞추고 시작
        String boardWriterEmail = dto.getBoardWriterEmail();
        PostBoardRequestDto2 dto2 = new PostBoardRequestDto2(dto);

        ResponseEntity<ResponseDto> response = postBoard(boardWriterEmail, dto2);

        //* 성공 반환 */
        return response;
    }

    @Override
    public ResponseEntity<ResponseDto> postBoard(String userEmail, PostBoardRequestDto2 dto) {
        // service 작업 전 기본틀 맞추고 시작
        ResponseDto body = null;

        try {
            //* 존재하지 않는 유저 오류 반환 */
            boolean existedUserEmail = userRepository.existsByEmail(userEmail);
            if (!existedUserEmail) {
                ResponseDto errorBody = new ResponseDto("NU", "Non-Existent User Email");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorBody);
            }

            BoardEntity boardEntity = new BoardEntity(userEmail, dto);
            boardRepository.save(boardEntity);

            body = new ResponseDto("SU", "Success");

        } catch (Exception exception) {
            //* 데이터베이스 오류 반환 */
            exception.printStackTrace();
            ResponseDto errorBody = new ResponseDto("DE", "Database Error");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorBody);
        }

        //* 성공 반환 */
        return ResponseEntity.status(HttpStatus.OK).body(body);
    }

    @Override
    public ResponseEntity<? super GetBoardResponseDto> getBoard(Integer boardNumber) {
       
        GetBoardResponseDto body = null;

        try {
            if (boardNumber == null) return CustomResponse.validationFailed(); // 매개변수 검증 작업

            BoardEntity boardEntity = boardRepository.findByBoardNumber(boardNumber);
            if (boardEntity == null) return CustomResponse.notExistBoardNumber();
            
            int viewCount = boardEntity.getViewCount();
            boardEntity.setViewCount(++viewCount);
            boardRepository.save(boardEntity);

            String boardWriterEmail = boardEntity.getWriterEmail(); // 이메일 가져오고
            UserEntity userEntity = userRepository.findByEmail(boardWriterEmail);
            List<CommentEntity> commentEntities = commentRepository.findByBoardNumber(boardNumber);
            List<LikeyEntity> likeyEntities = likeyRepository.findByBoardNumber(boardNumber);

            // 여기서 생성자 만들면 너무 지저분하니까 직접 가서
            body = new GetBoardResponseDto(boardEntity, userEntity, commentEntities, likeyEntities); 
             
        } catch(Exception exception) { // 계속 반복됨 -> 미리 만들어놓고 부를 수 있도록
            exception.printStackTrace();
            return CustomResponse.databaseError();
        }

        return ResponseEntity.status(HttpStatus.OK).body(body);

    }

    @Override
    public ResponseEntity<? super GetBoardListResponseDto> getBoardList() {
       GetBoardListResponseDto body = null;

       try {

            List<BoardListResultSet> resultSet = boardRepository.getList();
            body = new GetBoardListResponseDto(resultSet);

       } catch(Exception exception) {
            exception.printStackTrace();
            return CustomResponse.databaseError();
       } 

       return ResponseEntity.status(HttpStatus.OK).body(body);
    }

    @Override
    public ResponseEntity<? super GetBoardListResponseDto> getBoardTop3() {
        GetBoardListResponseDto body = null;

       try {

            List<BoardListResultSet> resultSet = boardRepository.getTop3List();
            body = new GetBoardListResponseDto(resultSet);

       } catch(Exception exception) {
            exception.printStackTrace();
            return CustomResponse.databaseError();
       } 

       return ResponseEntity.status(HttpStatus.OK).body(body);
    }

    @Override
    public ResponseEntity<ResponseDto> patchBoard(PatchBoardRequestDto dto) {
        
        String userEmail = dto.getUserEmail();
        PatchBoardRequestDto2 dto2 = new PatchBoardRequestDto2();

        ResponseEntity<ResponseDto> response = patchBoard(userEmail, dto2);
       
        return response;

    }

    @Override
    public ResponseEntity<ResponseDto> patchBoard(String userEmail, PatchBoardRequestDto2 dto) {
        
        int boardNumber = dto.getBoardNumber();
        String boardTitle = dto.getBoardTitle();
        String boardContent = dto.getBoardContent();
        String boardImageUrl = dto.getBoardImageUrl();

        try {
            //* 존재하지 않는 게시물 번호 반환 
            BoardEntity boardEntity = boardRepository.findByBoardNumber(boardNumber);
            if (boardEntity == null) return CustomResponse.notExistBoardNumber();

            //* 존재하지 않는 유저 이메일 반환 
            boolean existedUserEmail = userRepository.existsByEmail(userEmail);
            if (!existedUserEmail) return CustomResponse.notExistUserEmail();

            //* 권한 없음 
            boolean equalWriter = boardEntity.getWriterEmail().equals(userEmail);
            if (!equalWriter) return CustomResponse.noPermissions();

            boardEntity.setTitle(boardTitle);
            boardEntity.setContent(boardContent);
            boardEntity.setBoardImageUrl(boardImageUrl);

            boardRepository.save(boardEntity);

        } catch(Exception exception) {
            exception.printStackTrace();
            return CustomResponse.databaseError();
        }

        return CustomResponse.success();

    }

    @Override
    public ResponseEntity<ResponseDto> deleteBoard(String userEmail, Integer boardNumber) {
        
        try {
            if (boardNumber == null) return CustomResponse.validationFailed();

            //* 존재하지 않는 게시물 번호 반환 
            BoardEntity boardEntity = boardRepository.findByBoardNumber(boardNumber);
            if (boardEntity == null) return CustomResponse.notExistBoardNumber();

            //* 존재하지 않는 유저 이메일 반환 
            boolean existedUserEmail = userRepository.existsByEmail(userEmail);
            if (!existedUserEmail) return CustomResponse.notExistUserEmail();

            //* 권한 없음 반환 
            boolean equalWriter = boardEntity.getWriterEmail().equals(userEmail);
            if (!equalWriter) return CustomResponse.noPermissions();

            commentRepository.deleteByBoardNumber(boardNumber);
            likeyRepository.deleteByBoardNumber(boardNumber);
            boardRepository.delete(boardEntity);

        } catch(Exception exception) {
            exception.printStackTrace();
            return CustomResponse.databaseError();
        }

        return CustomResponse.success();
    }
    
}
