package com.wonder.bring.config.security.auth;

import com.wonder.bring.user.api.dto.User;
import com.wonder.bring.user.mapper.UserMapper;
import com.wonder.bring.common.dto.DefaultRes;
import com.wonder.bring.config.security.jwt.JwtServiceImpl;
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

    private final static String AUTHORIZATION = "Authorization";

    /**
     * 실패 시 기본 반환 Response
     */
    private final static DefaultRes DEFAULT_RES = DefaultRes.builder().status(401).message("인증 실패").build();
    private final static ResponseEntity<DefaultRes> RES_RESPONSE_ENTITY = new ResponseEntity<>(DEFAULT_RES, HttpStatus.OK);
    private final HttpServletRequest httpServletRequest;

    private final UserMapper userMapper;

    private final JwtServiceImpl jwtServiceImpl;

    public AuthAspect(final HttpServletRequest httpServletRequest, final UserMapper userMapper, final JwtServiceImpl jwtServiceImpl) {
        this.httpServletRequest = httpServletRequest;
        this.userMapper = userMapper;
        this.jwtServiceImpl = jwtServiceImpl;
    }


    @Around("@annotation(com.wonder.bring.config.security.auth.Auth)")
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
