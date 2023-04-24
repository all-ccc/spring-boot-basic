package com.chaminju.firstproject.service.implement;

import org.springframework.stereotype.Service;

import com.chaminju.firstproject.service.RestApiService;

@Service // auto-wired로 직접 인스턴스 생성하지 않아도 됨
public class RestApiServiceImplement implements RestApiService {
    
    public String getMethod() {
        return "Return to Service Layer";
    }

}
