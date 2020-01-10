package com.wonder.bring.service;

import com.wonder.bring.model.DefaultResponse;
import com.wonder.bring.model.LoginRequest;
import com.wonder.bring.service.impl.JwtServiceImpl;

public interface AuthService {
     DefaultResponse<JwtServiceImpl.TokenResponse> login(final LoginRequest loginRequest);
     DefaultResponse<Boolean> checkToken();
}

