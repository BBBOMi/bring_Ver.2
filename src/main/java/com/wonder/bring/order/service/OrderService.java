package com.wonder.bring.order.service;

import com.wonder.bring.order.api.dto.Order;
import com.wonder.bring.order.api.dto.OrderDetail;
import com.wonder.bring.common.dto.DefaultResponse;
import com.wonder.bring.order.api.dto.OrderRequest;

public interface OrderService {
    //주문하기 저장
    DefaultResponse createOrder(final int userIdx, final OrderRequest orderRequest);

    //전체주문내역
    DefaultResponse<Order> getOrderList(final int userIdx);

    //주문내역 상세 조회
    DefaultResponse<OrderDetail> getOrderDetailList(final int orderIdx);
}
