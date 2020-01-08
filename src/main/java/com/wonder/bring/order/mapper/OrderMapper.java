package com.wonder.bring.order.mapper;

import com.wonder.bring.order.api.dto.OrderDetailInfo;
import com.wonder.bring.order.api.dto.OrderInfo;
import com.wonder.bring.order.api.dto.OrderMenu;
import com.wonder.bring.order.api.dto.OrderReq;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface OrderMapper {

    /**
     * 주문하기 생성
     */
    //ORDER_LIST에 추가
    @Insert("INSERT INTO ORDER_LISTS(store_idx, user_idx) VALUES(#{orderReq.storeIdx}, #{userIdx})")
    @Options(useGeneratedKeys = true, keyProperty="orderReq.orderIdx")
    int createOrderLIst(@Param("orderReq") final OrderReq orderReq, @Param("userIdx") final int userIdx);


    //ORDER_MENU에 추가
    @Insert("INSERT INTO ORDER_MENU VALUES(#{orderMenu.menuIdx}, #{orderIdx}, #{orderMenu.orderCount}, " +
            "#{orderMenu.menuTotalPrice}, #{orderMenu.memo}, #{orderMenu.size})")
    void createOrderMenu(@Param("orderIdx") final int orderIdx, @Param("orderMenu") final OrderMenu orderMenu);


    /**
     * 주문내역 전체 조회
     */
    //주문내역 전체찾기
    @Select("SELECT ORDER_LISTS.order_idx, ORDER_LISTS.time, ORDER_LISTS.state, STORES.name " +
            "FROM ORDER_LISTS JOIN STORES ON STORES.store_idx = ORDER_LISTS.store_idx " +
            "WHERE user_idx = #{userIdx} AND (state NOT IN (3, 4)) ORDER BY time DESC")
    List<OrderInfo> findOrderAll(@Param("userIdx") final int userIdx);

    //닉네임조회
    @Select("SELECT nick FROM USERS WHERE user_idx = #{userIdx}")
    String findOrderNick(@Param("userIdx") final int userIdx);


    /**
     * 주문 내역 상세 조회
     * @return
     */
    //매장이름
    @Select("SELECT s.name FROM STORES s inner join ORDER_LISTS o ON (s.store_idx = o.store_idx) " +
            "WHERE o.order_idx = #{order_idx}")
    String findStoreByOrderIdx(@Param("order_idx") final int orderIdx);

    //메뉴이름 사이즈 수량 총가격 요청사항
    @Select("SELECT m.name, o.size, o.order_count, o.total_price, o.memo " +
            "FROM MENU m inner join ORDER_MENU o ON (m.menu_idx = o.menu_idx) " +
            "WHERE o.order_idx = #{order_idx}")
    List<OrderDetailInfo> findOrderByOrderIdx(@Param("order_idx") final int orderIdx);

    /**
     * 점주의 fcmToken값 가져오기
     */
    @Select("SELECT fcm_token FROM OWNER INNER JOIN STORES ON (STORES.owner_idx = OWNER.owner_idx) " +
            "WHERE STORES.store_idx = #{storeIdx}")
    String getOwnerToken(final int storeIdx);
}
