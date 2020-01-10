package com.wonder.bring.user.service;

import com.wonder.bring.common.dto.DefaultRes;
import com.wonder.bring.user.api.dto.SignUpReq;

import java.util.Optional;

/**
 * Created by bomi on 2018-12-28.
 */

public interface UserService {
    DefaultRes saveUser(final SignUpReq signUpReq);
    DefaultRes dupleCheckId(final Optional<String> id);
    DefaultRes dupleCheckNick(final Optional<String> nick);
    DefaultRes getUser(final int userIdx);
}
