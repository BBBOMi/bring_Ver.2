package com.wonder.bring.order.api.dto;

import lombok.Data;

@Data
public class OrderMenu {
    private int menuIdx; //주문한 메뉴인덱스
    private int size;
    private int orderCount; //주문한 메뉴갯수
    private int menuTotalPrice; //메뉴가격 * 갯수
    private String memo; //주문 메모
}
