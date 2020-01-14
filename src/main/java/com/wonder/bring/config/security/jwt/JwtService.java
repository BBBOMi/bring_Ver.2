package com.wonder.bring.config.security.jwt;

public interface JwtService {
    String create(final int userIdx);
    JwtServiceImpl.Token decode(final String token);
    boolean checkAuth(final String header, final int userIdx);
}
