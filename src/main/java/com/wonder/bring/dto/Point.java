package com.wonder.bring.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by bomi on 2019-01-02.
 */

@Getter
@Setter
public class Point {
    private int storeIdx; // 매장 idx
    private String storeName; // 매장 이름
    private double latitude; // 위도
    private double longitude; // 경도
    private String distance; // 약 예상 거리
}
