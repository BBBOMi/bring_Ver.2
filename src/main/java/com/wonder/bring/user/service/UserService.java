package com.wonder.bring.user.service;

import com.wonder.bring.common.dto.DefaultResponse;
import com.wonder.bring.user.api.dto.SignUpRequest;

import java.util.Optional;

/**
 * Created by bomi on 2018-12-28.
 */

public interface UserService {
    DefaultResponse createUser(final SignUpRequest signUpRequest);
    DefaultResponse repetitionCheckId(final Optional<String> id);
    DefaultResponse repetitionCheckNick(final Optional<String> nick);
    DefaultResponse getUser(final int userIdx);
}
