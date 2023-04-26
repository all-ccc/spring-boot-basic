package com.chaminju.board.service;

import org.springframework.http.ResponseEntity;

import com.chaminju.board.dto.request.user.PostUserRequestDto;
import com.chaminju.board.dto.response.ResponseDto;

public interface UserService {
    
    public ResponseEntity<ResponseDto> postUser(PostUserRequestDto dto);

}
