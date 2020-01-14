package com.wonder.bring.order.api.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Order {
    private String nick;
    private List<OrderInfo> orderList;
}
