package com.wonder.bring.user.api;

import com.wonder.bring.user.api.dto.User;
import com.wonder.bring.common.dto.DefaultRes;
import com.wonder.bring.user.api.dto.SignUpReq;
import com.wonder.bring.config.security.jwt.JwtService;
import com.wonder.bring.user.service.UserService;
import com.wonder.bring.common.utils.Message;
import com.wonder.bring.common.utils.Status;
import com.wonder.bring.config.security.auth.Auth;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static com.wonder.bring.common.dto.DefaultRes.*;

/**
 * Created by bomi on 2018-12-28.
 */

@Slf4j
@RequestMapping("users")
@RestController
public class UserController {
    private final UserService userService;
    private final JwtService jwtService;

    private static final DefaultRes NO_CONTENT_RES = new DefaultRes(Status.BAD_REQUEST, Message.NO_CONTENT);

    // 생성자 의존성 주입
    public UserController(final UserService userService, final JwtService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }

    /**
     * 마이페이지 조회
     * @param header
     *      token
     * @return 결과 데이터
     */
    @Auth
    @GetMapping("")
    public ResponseEntity getMyPage(@RequestHeader(value = "Authorization") final String header) {
        try {
            final int userIdx = jwtService.decode(header).getUser_idx();
            DefaultRes<User> defaultRes = userService.getUser(userIdx);
            defaultRes.getData().setAuth(true);
            return new ResponseEntity(defaultRes, HttpStatus.OK);
        } catch(Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity(FAIL_DEFAULT_RES, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 회원 가입
     * @param signUpReq
     *      가입할 객체
     * @return 결과 데이터
     */
    @PostMapping("")
    public ResponseEntity signUp(SignUpReq signUpReq) {
        try {
            return new ResponseEntity(userService.saveUser(signUpReq), HttpStatus.OK);
        } catch(Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity(FAIL_DEFAULT_RES, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * id, nickname 중복 체크
     * @param id
     *      중복체크할 id
     * @param nick
     *      중복체크할 nick
     * @return 결과 데이터
     */
    @GetMapping("check")
    public ResponseEntity dupleCheck(@RequestParam(value = "id", required = false) final Optional<String> id,
                                     @RequestParam(value = "nick", required = false) final Optional<String> nick) {
        try {
            if(id.isPresent()) {
                // id 중복검사
                return new ResponseEntity(userService.dupleCheckId(Optional.of(id.get())), HttpStatus.OK);
            } else if(nick.isPresent()) {
                // 닉네임 중복검사
                return new ResponseEntity(userService.dupleCheckNick(Optional.of(nick.get())), HttpStatus.OK);
            } else {
                // 값이 없을 경우
                return new ResponseEntity(NO_CONTENT_RES, HttpStatus.OK);
            }
        } catch(Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity(FAIL_DEFAULT_RES, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}