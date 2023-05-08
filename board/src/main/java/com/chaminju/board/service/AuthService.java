package com.chaminju.board.service;

import org.springframework.http.ResponseEntity;

import com.chaminju.board.dto.request.auth.SignInRequestDto;
import com.chaminju.board.dto.request.auth.SignUpRequestDto;
import com.chaminju.board.dto.response.ResponseDto;
import com.chaminju.board.dto.response.auth.SignInResponseDto;

public interface AuthService {
    public ResponseEntity<ResponseDto> signUp(SignUpRequestDto dto);
    public ResponseEntity<? super SignInResponseDto> signIn(SignInRequestDto dto);
}
