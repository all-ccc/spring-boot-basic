package com.chaminju.firstproject.service;

import com.chaminju.firstproject.provider.UserRole;

public interface MainService {
    public String hello();
    public String getJwt(String data);
    public UserRole validJwt(String jwt);
}
