package com.wonder.bring.order.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class Order {
    private String nick;
    private List<OrderInfo> orderList;
}
