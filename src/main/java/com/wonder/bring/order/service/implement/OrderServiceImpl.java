package com.wonder.bring.order.service.implement;

import com.wonder.bring.order.api.dto.Order;
import com.wonder.bring.order.api.dto.OrderDetail;
import com.wonder.bring.order.api.dto.OrderDetailInfo;
import com.wonder.bring.order.api.dto.OrderInfo;
import com.wonder.bring.order.mapper.OrderMapper;
import com.wonder.bring.common.dto.DefaultRes;
import com.wonder.bring.order.api.dto.OrderMenu;
import com.wonder.bring.order.api.dto.OrderReq;
import com.wonder.bring.fcm.FcmService;
import com.wonder.bring.order.service.OrderService;
import com.wonder.bring.common.utils.Message;
import com.wonder.bring.common.utils.Status;
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

    public OrderServiceImpl(final OrderMapper orderMapper, final FcmService fcmService) {
        this.orderMapper = orderMapper;
        this.fcmService = fcmService;
    }

    /**
     * 주문하기 생성
     */
    @Transactional
    @Override
    public DefaultRes createOrder(final int userIdx, final OrderReq orderReq) {
        if (!orderReq.checkEmpty()) {
            try {
                orderMapper.createOrderLIst(orderReq, userIdx);

                int orderIdx = orderReq.getOrderIdx();

                for (OrderMenu orderMenu : orderReq.getOrderMenuList()) {
                    orderMapper.createOrderMenu(orderIdx, orderMenu);
                }
            } catch (Exception e) {
                log.info(e.getMessage());
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();

                return DefaultRes.res(Status.DB_ERROR, Message.DB_ERROR);
            }

            String fcmToken = orderMapper.getOwnerToken(orderReq.getStoreIdx());
            String title = "주문 " + orderReq.getOrderIdx();
            String message = orderMapper.findOrderNick(userIdx) + " 님이 주문 접수를 요청하셨습니다.";
            //주문번호로 fcmToken값을 찾아 전송
            fcmService.sendPush(fcmToken, title, message);

            return DefaultRes.res(Status.CREATED, Message.CREATE_ORDER_SUCCESS);
        }
        return DefaultRes.res(Status.BAD_REQUEST, Message.FAIL_CREATE_ORDER);
    }

    /**
     * 주문내역 전체조회
     */
    @Override
    public DefaultRes<Order> getOrderList(final int userIdx) {

        String nick = orderMapper.findOrderNick(userIdx);
        List<OrderInfo> orderList = orderMapper.findOrderAll(userIdx);
        final Order order = new Order(nick, orderList);
        if(orderList.isEmpty())
            return DefaultRes.res(Status.NO_CONTENT, "주문내역이 존재하지 않습니다");

        return DefaultRes.res(Status.OK, "주문내역 조회 성공", order);
    }


    /**
     * 주문내역 상세조회
     */
    @Override
    public DefaultRes<OrderDetail> getOrderDetailList(final int orderIdx) {
        String store = orderMapper.findStoreByOrderIdx(orderIdx);
        if(store == null) {
            return DefaultRes.res(Status.NOT_FOUND, Message.NOT_FOUND_ORDER_LIST);
        }

        List<OrderDetailInfo> orderDetailList = orderMapper.findOrderByOrderIdx(orderIdx);

        final OrderDetail orderDetail = new OrderDetail(store, orderDetailList);
        return DefaultRes.res(Status.OK, "주문 상세내역 조회 성공", orderDetail);
    }
}
