package com.wonder.bring.fcm.implement;

import com.wonder.bring.fcm.FcmService;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Slf4j
@Service
public class FcmServiceImpl implements FcmService {
    private final String FIREBASE_API_URL = "https://fcm.googleapis.com/fcm/send";

    @Value("${FIREBASE.SERVER.KEY}")
    private String FIREBASE_SERVER_KEY;

    @Override
    public void sendPush(final String fcmToken, final String title, final String body) {
        JSONObject msg = new JSONObject();

        try {
            msg.put("title", URLEncoder.encode(title ,"UTF-8"));
            msg.put("body", URLEncoder.encode(body ,"UTF-8"));
        } catch (UnsupportedEncodingException e) {
          log.error(e.getMessage());
        }
        String response = callToFcmServer(msg, fcmToken); //파이어베이스 서버에 요청
        System.out.println("Got response from fcm Server : " + response + "\n\n");

    }

    private String callToFcmServer(JSONObject message, String receiverFcmKey) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Authorization", "key=" + FIREBASE_SERVER_KEY);
        httpHeaders.set("Content-Type", "application/json");
        httpHeaders.set("Content-Encoding", "UTF-8");

        JSONObject json = new JSONObject();

        json.put("to", receiverFcmKey);
        json.put("data", message);
        json.put("sound", "default");

        System.out.println("Sending :" + json.toString());

        HttpEntity<String> httpEntity = new HttpEntity<>(json.toString(), httpHeaders);
        return restTemplate.postForObject(FIREBASE_API_URL, httpEntity, String.class);
    }
}
