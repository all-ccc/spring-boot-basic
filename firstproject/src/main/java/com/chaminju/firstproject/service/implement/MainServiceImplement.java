package com.chaminju.firstproject.service.implement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.chaminju.firstproject.provider.JwtTokenProvider;
import com.chaminju.firstproject.provider.UserRole;
import com.chaminju.firstproject.service.MainService;

@Component
public class MainServiceImplement implements MainService {

    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    public MainServiceImplement(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public String hello() {
        return "Hello";
    }

    @Override
    public String getJwt(String data) {
       String jwt = jwtTokenProvider.create(data);
       return jwt;
    }

    @Override
    public UserRole validJwt(String jwt) {
       UserRole subject = jwtTokenProvider.validate(jwt);
       return subject;
    }
    
}
