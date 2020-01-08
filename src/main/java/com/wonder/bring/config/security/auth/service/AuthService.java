package com.wonder.bring.config.security.auth.service;

import com.wonder.bring.common.dto.DefaultRes;
import com.wonder.bring.config.security.jwt.JwtServiceImpl;
import com.wonder.bring.user.api.dto.LoginReq;

public interface AuthService {
     DefaultRes<JwtServiceImpl.TokenRes> login(final LoginReq loginReq);
     DefaultRes<Boolean> checkToken();
}

