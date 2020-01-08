package com.wonder.bring.order.service;

import com.wonder.bring.order.api.dto.Order;
import com.wonder.bring.order.api.dto.OrderDetail;
import com.wonder.bring.common.dto.DefaultRes;
import com.wonder.bring.order.api.dto.OrderReq;

public interface OrderService {
    //주문하기 저장
    DefaultRes createOrder(final int userIdx, final OrderReq orderReq);

    //전체주문내역
    DefaultRes<Order> getOrderList(final int userIdx);

    //주문내역 상세 조회
    DefaultRes<OrderDetail> getOrderDetailList(final int orderIdx);
}
