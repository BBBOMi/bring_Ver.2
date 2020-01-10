package com.wonder.bring.mapper;

import com.wonder.bring.dto.Point;
import com.wonder.bring.dto.StorePreview;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by bomi on 2019-01-02.
 */

@Mapper
public interface MapMapper {
    // 반경 1km 이내의 매장 조회
    @Select("SELECT store_idx, name AS store_name, ST_X(location) AS longitude, ST_Y(location) AS latitude, " +
            "ROUND(ST_DISTANCE_SPHERE(POINT(#{longitude}, #{latitude}), location)) AS distance " +
            "FROM STORES WHERE abs(ST_DISTANCE_SPHERE(POINT(#{longitude}, #{latitude}), location)) <= 1000")
    List<Point> findStorePointsByLongitudeAndLatitude(@Param("longitude") final double longitude, @Param("latitude") final double latitude);

}
