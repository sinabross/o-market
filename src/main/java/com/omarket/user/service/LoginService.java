package com.omarket.user.service;

import com.omarket.user.dto.SignInRequestDto;

public interface LoginService {

    void login(SignInRequestDto dto);

    void logout();

    long getLoginUserId();
}
