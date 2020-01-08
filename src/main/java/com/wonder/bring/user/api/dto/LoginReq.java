package com.wonder.bring.user.api.dto;

import lombok.Data;

@Data
public class LoginReq {
    private String id;
    private String password;
    private String fcmToken;
}
