package com.wonder.bring.store.api.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Created by bomi on 2019-01-05.
 */

@Getter
@Setter
public class StorePreview {
    private String storeName; // 매장 이름
    private String storeType; // 매장 타입
    private String storeAddress; // 주소
    private String tel; // 전화번호
    private List<String> storePhotoUrls; // 대표 사진
}
