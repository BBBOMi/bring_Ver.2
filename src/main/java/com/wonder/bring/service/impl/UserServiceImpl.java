package com.wonder.bring.service.impl;

import com.wonder.bring.dto.User;
import com.wonder.bring.mapper.UserMapper;
import com.wonder.bring.model.DefaultResponse;
import com.wonder.bring.model.SignUpRequest;
import com.wonder.bring.service.S3FileUploadService;
import com.wonder.bring.service.UserService;
import com.wonder.bring.utils.Message;
import com.wonder.bring.utils.Status;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.Optional;

import static com.wonder.bring.utils.Encryption.encrypt;

/**
 * Created by bomi on 2018-12-28.
 */

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    // UserService에서 파일 업로드(사진 업로드)를 위해 추가
    private final S3FileUploadService s3FileUploadService;

    /**
     * 생성자 의존성 주입
     * @param userMapper
     * @param s3FileUploadService
     */
    public UserServiceImpl(final UserMapper userMapper, final S3FileUploadService s3FileUploadService) {
        this.s3FileUploadService = s3FileUploadService;
        this.userMapper = userMapper;
    }

    /**
     * 회원 조회
     * @param userIdx
     *      조회할 회원 고유 idx
     * @return 결과 데이터
     */
    @Override
    public DefaultResponse getUser(final int userIdx) {
        final User user = userMapper.findByUserIdx(userIdx);
        if(user != null) {
            return DefaultResponse.res(Status.OK, Message.FIND_USER_SUCCESS, user);
        } else {
            return DefaultResponse.res(Status.NOT_FOUND, Message.FIND_USER_FAIL);
        }
    }

    /**
     * 회원 가입
     * @param signUpRequest
     *      가입할 회원 데이터
     * @return 결과 데이터
     */
    @Transactional
    @Override
    public DefaultResponse createUser(SignUpRequest signUpRequest) {
        try {
            // 빈칸 검사
            if(signUpRequest.getId().isEmpty() || signUpRequest.getPassword().isEmpty() || signUpRequest.getNick().isEmpty()) {
                return DefaultResponse.res(Status.BAD_REQUEST, Message.SIGN_UP_FAIL);
            }

            // 가입 전 id, nick 중복 검사
            if(repetitionCheckId(Optional.ofNullable(signUpRequest.getId())).getStatus() != 200) {
                return DefaultResponse.res(Status.BAD_REQUEST, Message.ID_DUPLICATION);
            }
            if(repetitionCheckNick(Optional.ofNullable(signUpRequest.getNick())).getStatus() != 200) {
                return DefaultResponse.res(Status.BAD_REQUEST, Message.NICK_DUPLICATION);
            }

            // 중복되지 않았다면 저장

            //패스워드 인코딩

            String rawPassword =  signUpRequest.getPassword();
            String encodedPassword = encrypt(rawPassword);
            signUpRequest.setPassword(encodedPassword);

            userMapper.save(signUpRequest);
            // 프로필 사진이 있을 경우
            if(signUpRequest.getProfile() != null) {
                String url = s3FileUploadService.upload(signUpRequest.getProfile());
                int idx = userMapper.findByUserId(signUpRequest.getId());
                userMapper.savePhoto(url, idx);
            }
            return DefaultResponse.res(Status.CREATED, Message.SIGN_UP_SUCCESS);
        } catch(Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            log.error(e.getMessage());
            return DefaultResponse.res(Status.DB_ERROR, Message.DB_ERROR);
        }
    }

    /**
     * id 중복 검사
     * @param id
     *      중복 검사할 id
     * @return 결과 데이터
     */
    @Override
    public DefaultResponse repetitionCheckId(final Optional<String> id) {
        // id가 null이 아니고 ""이 아닐 때,
        if(id.isPresent() && !id.get().equals("")) {
            // 이미 존재하는 id일 경우
            if(isRepetition(userMapper.checkId(id.get()))) {
                return DefaultResponse.res(Status.BAD_REQUEST, Message.ID_DUPLICATION);
            }
            return DefaultResponse.res(Status.OK, Message.CHECK_SUCCESS);
        }
        // id가 null이거나 ""일 경우
        return DefaultResponse.res(Status.BAD_REQUEST, Message.NO_CONTENT);
    }

    /**
     * 닉네임 중복 검사
     * @param nick
     *      중복 검사할 닉네임
     * @return 결과 데이터
     */
    @Override
    public DefaultResponse repetitionCheckNick(final Optional<String> nick) {
        // nick의 값이 null이 아니고 ""이 아닐 때,
        if(nick.isPresent() && !nick.get().equals("")) {
            // 이미 존재하는 nick일 경우
            if(isRepetition(userMapper.checkNick(nick.get()))) {
                return DefaultResponse.res(Status.BAD_REQUEST, Message.NICK_DUPLICATION);
            }
            return DefaultResponse.res(Status.OK, Message.CHECK_SUCCESS);
        }
        // nick이 null이거나 ""이면
        return DefaultResponse.res(Status.BAD_REQUEST, Message.NO_CONTENT);
    }

    private boolean isRepetition(int count) {
        return count > 0;
    }
}
