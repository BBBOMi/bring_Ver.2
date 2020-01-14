package com.wonder.bring.config.security.auth.service.implement;

import com.wonder.bring.config.security.jwt.JwtService;
import com.wonder.bring.config.security.jwt.JwtServiceImpl;
import com.wonder.bring.config.security.Encryption;
import com.wonder.bring.user.api.dto.LoginRequest;
import com.wonder.bring.user.api.dto.User;
import com.wonder.bring.user.mapper.UserMapper;
import com.wonder.bring.common.dto.DefaultResponse;
import com.wonder.bring.config.security.auth.service.AuthService;
import com.wonder.bring.common.utils.Message;
import com.wonder.bring.common.utils.Status;
import org.springframework.stereotype.Service;

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
                Encryption.encrypt(loginRequest.getPassword()));

        if (user != null) {
            //토큰 생성
            final JwtServiceImpl.TokenResponse token = new JwtServiceImpl.TokenResponse(jwtService.create(user.getUserIdx()));

            //user의 fcmToken값 저장
            int userIdx = userMapper.findByUserId(loginRequest.getId());
            userMapper.saveFcmToken(loginRequest.getFcmToken(), userIdx);

            return DefaultResponse.of(Status.OK, Message.LOGIN_SUCCESS, token);
        }
        return DefaultResponse.of(Status.BAD_REQUEST, Message.LOGIN_FAIL);
    }

    @Override
    public DefaultResponse<Boolean> checkToken() {
        return DefaultResponse.of(Status.OK, "유효한 토큰입니다");
    }

}
