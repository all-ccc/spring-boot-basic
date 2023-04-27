package com.chaminju.board.service.implement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.chaminju.board.dto.request.board.patchBoardRequestDto;
import com.chaminju.board.dto.request.board.postBoardRequestDto;
import com.chaminju.board.dto.response.ResponseDto;
import com.chaminju.board.dto.response.board.GetBoardListResponseDto;
import com.chaminju.board.dto.response.board.GetBoardResponseDto;
import com.chaminju.board.entity.BoardEntity;
import com.chaminju.board.repository.BoardRepository;
import com.chaminju.board.repository.UserRepository;
import com.chaminju.board.service.BoardService;

@Service
public class BoardServiceImplement implements BoardService {

    private UserRepository userRepository;
    private BoardRepository boardRepository;

    @Autowired
    public BoardServiceImplement(
        UserRepository userRepository,
        BoardRepository boardRepository
    ) {
        this.userRepository = userRepository;
        this.boardRepository = boardRepository;
    }

    @Override
    public ResponseEntity<ResponseDto> postBoard(postBoardRequestDto dto) {
        // service 작업 전 기본틀 맞추고 시작
        ResponseDto body = null;
        String boardWriterEmail = dto.getBoardWriterEmail();

        try {
            //* 존재하지 않는 유저 오류 반환 */
            boolean existedUserEmail = userRepository.existsByEmail(boardWriterEmail);
            if (!existedUserEmail) {
                ResponseDto errorBody = new ResponseDto("NU", "Non-Existent User Email");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorBody);
            }

            BoardEntity boardEntity = new BoardEntity(dto);
            boardRepository.save(boardEntity);

            body = new ResponseDto("SU", "Success");

        } catch (Exception exception) {
            //* 데이터베이스 오류 반환 */
            exception.printStackTrace();
            ResponseDto errorBody = new ResponseDto("DE", "Database Error");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorBody);
        }

        // TODO: 성공 반환 //
        return ResponseEntity.status(HttpStatus.OK).body(body);
    }

    @Override
    public ResponseEntity<? super GetBoardResponseDto> getBoard(Integer boardNumber) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getBoard'");
    }

    @Override
    public ResponseEntity<? super GetBoardListResponseDto> getBoardList() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getBoardList'");
    }

    @Override
    public ResponseEntity<? super GetBoardListResponseDto> getBoardTop3() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getBoardTop3'");
    }

    @Override
    public ResponseEntity<ResponseDto> patchBoard(patchBoardRequestDto dto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'patchBoard'");
    }

    @Override
    public ResponseEntity<ResponseDto> deleteBoard(String userEmail, Integer boardNumber) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteBoard'");
    }
    
}
