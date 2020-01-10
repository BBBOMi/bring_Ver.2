package com.wonder.bring.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderDetail {
    private String storeName;
    private List<OrderDetailInfo> orderDetailInfos;
}