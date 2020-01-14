package com.wonder.bring.config.security.auth.service;

import com.wonder.bring.common.dto.DefaultResponse;
import com.wonder.bring.config.security.jwt.JwtServiceImpl;
import com.wonder.bring.user.api.dto.LoginRequest;

public interface AuthService {
     DefaultResponse<JwtServiceImpl.TokenResponse> login(final LoginRequest loginRequest);
     DefaultResponse<Boolean> checkToken();
}

