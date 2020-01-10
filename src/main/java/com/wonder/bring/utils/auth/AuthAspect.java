package com.wonder.bring.utils.auth;

import com.wonder.bring.dto.User;
import com.wonder.bring.mapper.UserMapper;
import com.wonder.bring.model.DefaultResponse;
import com.wonder.bring.service.impl.JwtServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;

@Slf4j
@Component
@Aspect
public class AuthAspect {

    public static final String AUTHORIZATION = "Authorization";

    /**
     * 실패 시 기본 반환 Response
     */
    private final static DefaultResponse DEFAULT_RES = DefaultResponse.builder().status(401).message("인증 실패").build();
    private final static ResponseEntity<DefaultResponse> RES_RESPONSE_ENTITY = new ResponseEntity<>(DEFAULT_RES, HttpStatus.OK);
    private final HttpServletRequest httpServletRequest;

    private final UserMapper userMapper;

    private final JwtServiceImpl jwtServiceImpl;

    public AuthAspect(final HttpServletRequest httpServletRequest, final UserMapper userMapper, final JwtServiceImpl jwtServiceImpl) {
        this.httpServletRequest = httpServletRequest;
        this.userMapper = userMapper;
        this.jwtServiceImpl = jwtServiceImpl;
    }


    @Around("@annotation(com.wonder.bring.utils.auth.Auth)")
    public Object around(final ProceedingJoinPoint pjp) throws Throwable {
        final String jwt = httpServletRequest.getHeader(AUTHORIZATION);

        //토큰 존재 여부 확인
        if (jwt == null) {
            return RES_RESPONSE_ENTITY;
        }

        //토큰 해독
        final JwtServiceImpl.Token token = jwtServiceImpl.decode(jwt);

        //토큰 검사
        if (token == null) {
            return RES_RESPONSE_ENTITY;
        } else {
            final User user = userMapper.findByUserIdx(token.getUser_idx());

            //유효 사용자 검사
            if (user == null) {
                return RES_RESPONSE_ENTITY;
            }

            return pjp.proceed(pjp.getArgs());
        }

    }
}
