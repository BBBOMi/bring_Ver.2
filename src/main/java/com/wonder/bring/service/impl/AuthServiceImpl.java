package com.wonder.bring.service.impl;

import com.wonder.bring.dto.User;
import com.wonder.bring.mapper.UserMapper;
import com.wonder.bring.model.DefaultResponse;
import com.wonder.bring.model.LoginRequest;
import com.wonder.bring.service.AuthService;
import com.wonder.bring.service.JwtService;
import com.wonder.bring.utils.Message;
import com.wonder.bring.utils.Status;
import org.springframework.stereotype.Service;

import static com.wonder.bring.utils.Encryption.encrypt;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserMapper userMapper;
    private final JwtService jwtService;

    public AuthServiceImpl(final UserMapper userMapper, final JwtService jwtService) {
        this.userMapper = userMapper;
        this.jwtService = jwtService;
    }

    @Override
    public DefaultResponse<JwtServiceImpl.TokenResponse> login(final LoginRequest loginRequest) {
        final User user = userMapper.findByIdAndPassword(loginRequest.getId(),
                encrypt(loginRequest.getPassword()));

        if (user != null) {
            //토큰 생성
            final JwtServiceImpl.TokenResponse token = new JwtServiceImpl.TokenResponse(jwtService.create(user.getUserIdx()));

            //user의 fcmToken값 저장
            int userIdx = userMapper.findByUserId(loginRequest.getId());
            userMapper.saveFcmToken(loginRequest.getFcmToken(), userIdx);

            return DefaultResponse.res(Status.OK, Message.LOGIN_SUCCESS, token);
        }
        return DefaultResponse.res(Status.BAD_REQUEST, Message.LOGIN_FAIL);
    }

    @Override
    public DefaultResponse<Boolean> checkToken() {
        return DefaultResponse.res(Status.OK, "유효한 토큰입니다");
    }

}
