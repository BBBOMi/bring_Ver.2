package com.wonder.bring.model;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by bomi on 2018-12-28.
 */

@Data
public class SignUpRequest {
    private String id; // id
    private String password; // password
    private String nick; // nickname
    private MultipartFile profile; // 프로필 사진
    private String profileUrl; // 프로필 사진 url
}
