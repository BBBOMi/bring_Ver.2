package com.wonder.bring.order.service.implement;

import com.wonder.bring.order.api.dto.*;
import com.wonder.bring.order.mapper.OrderMapper;
import com.wonder.bring.common.dto.DefaultResponse;
import com.wonder.bring.fcm.FcmService;
import com.wonder.bring.order.service.OrderService;
import com.wonder.bring.common.utils.Message;
import com.wonder.bring.common.utils.Status;
import com.wonder.bring.user.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.List;

@Slf4j
@Service
public class  OrderServiceImpl implements OrderService {
    private final OrderMapper orderMapper;
    private final FcmService fcmService;
    private final UserMapper userMapper;

    public OrderServiceImpl(final OrderMapper orderMapper, final FcmService fcmService, final UserMapper userMapper) {
        this.orderMapper = orderMapper;
        this.fcmService = fcmService;
        this.userMapper = userMapper;
    }

    /**
     * 주문하기 생성
     */
    @Transactional
    @Override
    public DefaultResponse createOrder(final int userIdx, final OrderRequest orderRequest) {
        if (!orderRequest.checkEmpty()) {
            try {
                orderMapper.saveOrderList(orderRequest, userIdx);

                int orderIdx = orderRequest.getOrderIdx();

                for (OrderMenu orderMenu : orderRequest.getOrderMenuList()) {
                    orderMapper.saveOrderMenu(orderIdx, orderMenu);
                }
            } catch (Exception e) {
                log.info(e.getMessage());
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();

                return DefaultResponse.of(Status.DB_ERROR, Message.DB_ERROR);
            }

            String fcmToken = orderMapper.findOwnerTokenByStoreIdx(orderRequest.getStoreIdx());
            String title = "주문 " + orderRequest.getOrderIdx();
            String message = orderMapper.findAll(userIdx) + " 님이 주문 접수를 요청하셨습니다.";
            //주문번호로 fcmToken값을 찾아 전송
            fcmService.sendPush(fcmToken, title, message);

            return DefaultResponse.of(Status.CREATED, Message.CREATE_ORDER_SUCCESS);
        }
        return DefaultResponse.of(Status.BAD_REQUEST, Message.FAIL_CREATE_ORDER);
    }

    /**
     * 주문내역 전체조회
     */
    @Override
    public DefaultResponse<Order> getOrderList(final int userIdx) {
        String nick = userMapper.findNickByUserIdx(userIdx);
        List<OrderInfo> orderList = orderMapper.findAll(userIdx);

        if(orderList.isEmpty())
            return DefaultResponse.of(Status.NO_CONTENT, "주문내역이 존재하지 않습니다");

        Order order = new Order();
        order.setNick(nick);
        order.setOrderList(orderList);

        return DefaultResponse.of(Status.OK, "주문내역 조회 성공", order);
    }


    /**
     * 주문내역 상세조회
     */
    @Override
    public DefaultResponse<OrderDetail> getOrderDetailList(final int orderIdx) {
        String storeName = orderMapper.findStoreNameByOrderIdx(orderIdx);
        if(storeName.isEmpty()) {
            return DefaultResponse.of(Status.NOT_FOUND, Message.NOT_FOUND_ORDER_LIST);
        }

        List<OrderDetailInfo> orderDetailInfos = orderMapper.findOrderDetailInfoByOrderIdx(orderIdx);

        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setStoreName(storeName);
        orderDetail.setOrderDetailInfos(orderDetailInfos);

        return DefaultResponse.of(Status.OK, "주문 상세내역 조회 성공", orderDetail);
    }
}
