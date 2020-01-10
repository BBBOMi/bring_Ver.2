package com.wonder.bring.user.api.dto;

import lombok.Data;

@Data
public class User {
    private int userIdx; // 회원 고유 idx
    private String nick; // 회원 닉네임
    private String profileUrl; // 프로필 사진 url
    private boolean auth; // 회원 권한
}
