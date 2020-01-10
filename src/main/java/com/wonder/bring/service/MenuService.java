package com.wonder.bring.service;

import com.wonder.bring.dto.StoreMenu;
import com.wonder.bring.model.DefaultResponse;

/**
 * Create by YoungEun on 2018-12-29.
 */

public interface MenuService {
    // 메뉴 리스트 조회
    DefaultResponse<StoreMenu> findMenuByStoreIdx(final int storeIdx);
    // 메뉴 상세 정보 조회
    DefaultResponse findDetailMenu(final int storeIdx, final int menuIdx);

}
