package com.wonder.bring.model;

import lombok.Data;

@Data
public class OrderMenu {
    private int menuIdx; //주문한 메뉴인덱스
    private int size;
    private int orderCount; //주문한 메뉴갯수
    private String memo; //주문 메모
}