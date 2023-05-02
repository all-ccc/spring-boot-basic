package com.chaminju.board.common.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.chaminju.board.dto.response.ResponseDto;

public class CustomResponse {

    public static ResponseEntity<ResponseDto> success() {
        ResponseDto body = new ResponseDto("SU", "Success");
        return ResponseEntity.status(HttpStatus.OK).body(body);
    }
    
    public static ResponseEntity<ResponseDto> databaseError() {
        ResponseDto errorBody = new ResponseDto("DE", "Database Error");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorBody);
    }

    public static ResponseEntity<ResponseDto> validationFailed() {
        ResponseDto errorBody = new ResponseDto("VF", "Request Parameter Validation");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorBody);
    }

    public static ResponseEntity<ResponseDto> notExistBoardNumber() {
        ResponseDto errorBody = new ResponseDto("NB", "Non-Existent Board Number");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorBody);
    }

    public static ResponseEntity<ResponseDto> notExistUserEmail() {
        ResponseDto errorBody = new ResponseDto("NU", "Non-Existent User Email");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorBody); // 인증할 수 없음(누군지 모른다)
    }

    public static ResponseEntity<ResponseDto> noPermissions() {
        ResponseDto errorBody = new ResponseDto("NP", "No Permissions");
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorBody); // 인가할 수 없음(누군지 알지만 권한 X)
    }
}
