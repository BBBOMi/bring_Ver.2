package com.wonder.bring.mapper;

import com.wonder.bring.dto.Store;
import com.wonder.bring.dto.StoreMenu;
import com.wonder.bring.dto.StorePreview;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;


/**
 * Create by YoungEun on 2018-12-29.
 */

@Mapper
public interface StoreMapper {

    /**
     * 매장 정보 상세 조회
     * @return
     */
    @Select("SELECT number, time, break_days FROM STORES WHERE store_idx = #{storeIdx}")
    Store findDetailByStoreIdx(@Param("storeIdx") final int storeIdx);

    /**
     * 매장 배경사진 조회
     * @param storeIdx
     *      조회할 매장의 고유 idx
     * @return
     */
    @Select("SELECT store_idx, bg_photoUrl FROM STORES WHERE store_idx = #{store_idx}")
    StoreMenu findStoreByStoreIdx(@Param("store_idx") final int storeIdx);

    // 매장 정보 받아오기
    @Select("SELECT name, address, type, number FROM STORES WHERE store_idx = #{storeIdx}")
    StorePreview findStoreInfoByStoreIdx(@Param("storeIdx") final int storeIdx);

    // 매장 사진 3장 받아오기
    @Select("SELECT photo_url FROM TAB_PHOTOS WHERE store_idx = #{storeIdx} LIMIT 0, 3")
    List<String> findStorePhotosByStoreIdx(@Param("storeIdx") final int storeIdx);
}
