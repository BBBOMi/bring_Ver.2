package com.wonder.bring.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class StoreMenu {
    // 매장 고유 번호
    private int storeIdx;
    // 매장 대표 사진
    private String bgPhotoUrl;
    // 메뉴
    private List<Menu> menus;
}
