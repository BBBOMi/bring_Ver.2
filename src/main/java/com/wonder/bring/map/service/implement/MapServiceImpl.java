package com.wonder.bring.map.service.implement;

import com.wonder.bring.map.api.dto.Point;
import com.wonder.bring.store.api.dto.StorePreview;
import com.wonder.bring.map.mapper.MapMapper;
import com.wonder.bring.common.dto.DefaultRes;
import com.wonder.bring.map.service.MapService;
import com.wonder.bring.common.utils.Message;
import com.wonder.bring.common.utils.Status;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Created by bomi on 2019-01-02.
 */

@Slf4j
@Service
public class MapServiceImpl implements MapService {

    private final MapMapper mapMapper;

    public MapServiceImpl(final MapMapper mapMapper) {
        this.mapMapper = mapMapper;
    }

    /**
     * 주위의 매장 위도 경도 좌표 조회
     * @param la
     *      지금 현재 나의 위도
     * @param lo
     *      지금 현재 나의 경도
     * @return 1km 이내의 매장 위치 좌표
     */
   @Override
    public DefaultRes getStores(final Optional<Double> la, final Optional<Double> lo) {
       // 파라미터가 존재하면
       if(la.isPresent() && lo.isPresent()) {
           // 빈 값이 아니라면
           if(!la.get().equals("") && !lo.get().equals("")) {
               List<Point> list = mapMapper.getStorePoints(lo.get(), la.get());
               if(list.isEmpty()) {
                   return DefaultRes.res(Status.OK, Message.FIND_STORE_SUCCESS);
               }
               return DefaultRes.res(Status.OK, Message.FIND_POINT_SUCCESS, list);
               // 빈 값이면
           } else {
               return DefaultRes.res(Status.BAD_REQUEST, Message.NOT_GET_MY_POINT);
           }
       }
       // 존재하지 않으면
       return DefaultRes.res(Status.BAD_REQUEST, Message.NOT_GET_MY_POINT);
   }

    /**
     * 선택한 매장의 정보 조회
     * @param storeIdx
     *      선택한 매장의 고유 idx
     * @return 결과 데이터
     */
    @Override
    public DefaultRes getStoreInfo(final int storeIdx) {
        StorePreview store = mapMapper.getStoreInfo(storeIdx);

        if(store == null) {
            return DefaultRes.res(Status.NOT_FOUND, Message.FIND_STORE_FAIL);
        } else {
            store.setPhotoUrl(mapMapper.getStorePhotos(storeIdx));
            return DefaultRes.res(Status.OK, Message.FIND_STORE_SUCCESS, store);
        }
    }
}
