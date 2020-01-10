package com.wonder.bring.model;

import lombok.Data;

@Data
public class LoginRequest {
    private String id;
    private String password;
    private String fcmToken;
}
