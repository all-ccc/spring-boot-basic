package com.chaminju.board.service.implement;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.chaminju.board.common.util.CustomResponse;
import com.chaminju.board.dto.request.auth.SignInRequestDto;
import com.chaminju.board.dto.request.auth.SignUpRequestDto;
import com.chaminju.board.dto.response.ResponseDto;
import com.chaminju.board.dto.response.auth.SignInResponseDto;
import com.chaminju.board.entity.UserEntity;
import com.chaminju.board.provider.JwtProvider;
import com.chaminju.board.repository.UserRepository;
import com.chaminju.board.service.AuthService;

@Service
public class AuthServiceImplement implements AuthService {
    
    private UserRepository userRepository;
    private JwtProvider jwtProvider;
    private PasswordEncoder passwordEncoder;

    public AuthServiceImplement(UserRepository userRepository, JwtProvider jwtProvider) {
        this.userRepository = userRepository;
        this.jwtProvider = jwtProvider;
        this.passwordEncoder = new BCryptPasswordEncoder(); // 직접 만들어 줄 거임
    }

    @Override
    public ResponseEntity<ResponseDto> signUp(SignUpRequestDto dto) {
        
        String email = dto.getUserEmail();
        String nickname = dto.getUserNickname();
        String phoneNumber = dto.getUserPhoneNumber();
        String password = dto.getUserPassword();

        try {
            //* 존재하는 유저 이메일 반환 
            boolean existedUserEmail = userRepository.existsByEmail(email);
            if (existedUserEmail) return CustomResponse.existUserEmail();

            //* 존재하는 유저 닉네임 반환 
            boolean existedUserNickname = userRepository.existsByNickname(nickname);
            if (existedUserNickname) return CustomResponse.existUserNickname();

            //* 존재하는 유저 휴대전화 번호 반환 
            boolean existedUserPhoneNumber = userRepository.existsByPhoneNumber(phoneNumber);
            if (existedUserPhoneNumber) return CustomResponse.existUserPhoneNumber();

            String encodedPassword = passwordEncoder.encode(password); // 암호화 작업
            dto.setUserPassword(encodedPassword); // 암호화한 패스워드로 갈아끼움
            
            UserEntity userEntity = new UserEntity(dto);
            userRepository.save(userEntity);


        } catch (Exception exception) {
            exception.printStackTrace();
            return CustomResponse.databaseError();
        }

        return CustomResponse.success();

    }

    @Override
    public ResponseEntity<? super SignInResponseDto> signIn(SignInRequestDto dto) {
        
        SignInResponseDto body = null;

        String email = dto.getUserEmail();
        String password = dto.getUserPassword();

        try {
            //* 로그인 실패 (이메일 X)
            UserEntity userEntity = userRepository.findByEmail(email);
            if (userEntity == null) return CustomResponse.signInFailed();

            //* 로그인 실패 (패스워드 X)
            String encodedPassword = userEntity.getPassword();
            boolean equaledPassword = passwordEncoder.matches(password, encodedPassword); // 평문의 패스워드, 암호화된 패스워드
            if (!equaledPassword) return CustomResponse.signInFailed();

            String jwt = jwtProvider.create(email);
            body = new SignInResponseDto(jwt);

        } catch (Exception exception) {
            exception.printStackTrace();
            return CustomResponse.databaseError();
        }

        return ResponseEntity.status(HttpStatus.OK).body(body);

    }

}
