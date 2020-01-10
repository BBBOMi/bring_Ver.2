package com.wonder.bring.menu.api.dto;

import lombok.Data;

@Data
public class Menu {
    // 메뉴 고유 번호
    private int menuIdx;
    // 메뉴 이름
    private String name;
    // 사진 url
    private String photoUrl;
    // 가격
    private int price;
    // 사이즈
    private int size;
}
