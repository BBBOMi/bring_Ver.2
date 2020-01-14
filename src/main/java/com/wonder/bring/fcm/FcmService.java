package com.wonder.bring.fcm;

public interface FcmService {
    void sendPush(final String fcmToken, final String title, final String message);
}
