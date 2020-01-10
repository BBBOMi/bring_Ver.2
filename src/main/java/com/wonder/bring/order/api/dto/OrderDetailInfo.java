package com.wonder.bring.order.api.dto;

import lombok.Data;

@Data
public class OrderDetailInfo {
    //메뉴 이름
    private String name;
    //사이즈
    private int size;
    //수량
    private int orderCount;
    //총가격
    private int totalPrice;
    //요청사항
    private String memo;
}
