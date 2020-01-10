package com.wonder.bring.store.api.dto;

import com.wonder.bring.menu.api.dto.Menu;
import lombok.Data;

import java.util.List;

@Data
public class StoreMenu {
    // 매장 고유 번호
    private int storeIdx;
    // 매장 대표 사진
    private String bgPhotoUrl;
    // 메뉴
    private List<Menu> menuList;
}
