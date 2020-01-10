package com.wonder.bring.order.api.dto;

import lombok.Data;

import java.util.List;

@Data
public class OrderReq {
    private int orderIdx;
    private int storeIdx; //주문한 매장인덱스
    private List<OrderMenu> orderMenuList; //주문한 메뉴리스트

    public boolean checkEmpty() {
        return (orderMenuList.isEmpty() || orderMenuList == null);
    }

}
