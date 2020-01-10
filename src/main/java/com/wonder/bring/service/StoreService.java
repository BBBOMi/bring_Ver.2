package com.wonder.bring.service;

import com.wonder.bring.dto.Store;
import com.wonder.bring.model.DefaultResponse;

/**
 * Create by YoungEun on 2018-12-29.
 */

public interface StoreService {
    DefaultResponse<Store> getStoreInfo(final int storeIdx); //매장 상세 정보 조회
}
