package com.chaminju.board.service;

import org.springframework.http.ResponseEntity;

import com.chaminju.board.dto.request.board.patchBoardRequestDto;
import com.chaminju.board.dto.request.board.postBoardRequestDto;
import com.chaminju.board.dto.response.board.GetBoardResponseDto;
import com.chaminju.board.dto.response.board.GetBoardListResponseDto;
import com.chaminju.board.dto.response.ResponseDto;

public interface BoardService {
    public ResponseEntity<ResponseDto> postBoard(postBoardRequestDto dto);
    public ResponseEntity<? super GetBoardResponseDto> getBoard(Integer boardNumber);
    public ResponseEntity<? super GetBoardListResponseDto> getBoardList();
    public ResponseEntity<? super GetBoardListResponseDto> getBoardTop3();
    public ResponseEntity<ResponseDto> patchBoard(patchBoardRequestDto dto);
    public ResponseEntity<ResponseDto> deleteBoard(String userEmail, Integer boardNumber);
}
