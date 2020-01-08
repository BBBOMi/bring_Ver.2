package com.wonder.bring.user.api;

import com.wonder.bring.common.dto.DefaultRes;
import com.wonder.bring.user.api.dto.LoginReq;
import com.wonder.bring.config.security.auth.service.AuthService;
import com.wonder.bring.common.utils.Message;
import com.wonder.bring.common.utils.Status;
import com.wonder.bring.config.security.auth.Auth;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequestMapping("login")
@RestController
public class LoginController {

    private static final DefaultRes FAIL_DEFAULT_RES = new DefaultRes(Status.INTERNAL_SERVER_ERROR, Message.INTERNAL_SERVER_ERROR);

    private final AuthService authServiceImpl;

    public LoginController(final AuthService authServiceImpl) {
        this.authServiceImpl = authServiceImpl;
    }

    @PostMapping("")
    public ResponseEntity login(@RequestBody final LoginReq loginReq) {
        try {
            return new ResponseEntity<>(authServiceImpl.login(loginReq), HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(FAIL_DEFAULT_RES, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

   @Auth
    @GetMapping("")
    public ResponseEntity checkToken() {
        try {
            return new ResponseEntity<>(authServiceImpl.checkToken(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(FAIL_DEFAULT_RES, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
