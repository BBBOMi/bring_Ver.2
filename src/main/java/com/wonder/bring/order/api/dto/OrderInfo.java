package com.wonder.bring.order.api.dto;

import lombok.Data;

@Data
public class OrderInfo {
    private int orderIdx;
    private String name; //매장이름
    private int state;
    private String time;
}
