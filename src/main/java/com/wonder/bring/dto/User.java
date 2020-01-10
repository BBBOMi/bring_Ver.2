package com.wonder.bring.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {
    private int userIdx; // 회원 고유 idx
    private String nick; // 회원 닉네임
    private String profilePhotoUrl; // 프로필 사진 url
    private boolean auth; // 회원 권한
}
