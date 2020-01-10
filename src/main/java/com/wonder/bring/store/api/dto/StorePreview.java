package com.wonder.bring.store.api.dto;

import lombok.Data;

import java.util.List;

/**
 * Created by bomi on 2019-01-05.
 */

@Data
public class StorePreview {
    private String name; // 매장 이름
    private String type; // 매장 타입
    private String address; // 주소
    private String number; // 전화번호
    private List<String> photoUrl; // 대표 사진
}
