package com.wonder.bring.order.api.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderInfo {
    private int orderIdx;
    private String storeName; //매장이름
    private int state;
    private String time;
}
