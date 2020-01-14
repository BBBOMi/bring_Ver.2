package com.wonder.bring.map.service;

import com.wonder.bring.common.dto.DefaultResponse;

import java.util.Optional;

/**
 * Created by bomi on 2019-01-02.
 */

public interface MapService {
    DefaultResponse getStores(final Optional<Double> latitude, final Optional<Double> longitude); // 위도, 경도 받아오기
    DefaultResponse getStoreInfo(final int storeIdx);
}
