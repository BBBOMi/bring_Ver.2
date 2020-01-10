package com.wonder.bring.service;

import com.wonder.bring.dto.Order;
import com.wonder.bring.dto.OrderDetail;
import com.wonder.bring.model.DefaultResponse;
import com.wonder.bring.model.OrderRequest;

public interface OrderService {
    //주문하기 저장
    DefaultResponse createOrder(final int userIdx, final OrderRequest orderRequest);

    //전체주문내역
    DefaultResponse<Order> getOrderList(final int userIdx);

    //주문내역 상세 조회
    DefaultResponse<OrderDetail> getOrderDetailList(final int orderIdx);
}
