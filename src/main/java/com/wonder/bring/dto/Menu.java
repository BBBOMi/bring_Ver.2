package com.wonder.bring.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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
