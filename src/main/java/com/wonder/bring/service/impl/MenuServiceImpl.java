package com.wonder.bring.service.impl;

import com.wonder.bring.dto.Menu;
import com.wonder.bring.dto.SizePrice;
import com.wonder.bring.dto.StoreMenu;
import com.wonder.bring.mapper.MenuMapper;
import com.wonder.bring.mapper.StoreMapper;
import com.wonder.bring.model.DefaultResponse;
import com.wonder.bring.service.MenuService;
import com.wonder.bring.utils.Message;
import com.wonder.bring.utils.Status;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Create by YoungEun on 2018-12-29.
 */

@Service
public class MenuServiceImpl implements MenuService {

    private final MenuMapper menuMapper;
    private final StoreMapper storeMapper;

    public MenuServiceImpl(MenuMapper menuMapper, StoreMapper storeMapper) {
        this.menuMapper = menuMapper;
        this.storeMapper = storeMapper;
    }

    /**
     * 메뉴 리스트 조회
     * @param storeIdx
     * @return 메뉴 리스트
     */
    @Override
    public DefaultResponse<StoreMenu> findMenuByStoreIdx(int storeIdx) {
        // storeIdx로 메뉴 리스트 조회
        final StoreMenu storeMenu = storeMapper.findStoreByStoreIdx(storeIdx);
        List<Menu> menuList = menuMapper.findMenusByStoreIdx(storeIdx);

        // 해당 매장이 없을 때
        if(storeMenu == null) {
            return DefaultResponse.res(Status.NOT_FOUND, Message.NOT_FOUND_STORE);
        } else if(menuList.isEmpty()) {
            return DefaultResponse.res(Status.NOT_FOUND, Message.NOT_FOUND_LIST_MENU);
        } else {
            storeMenu.setMenus(menuList);
        }
        return DefaultResponse.res(Status.OK, Message.FIND_LIST_MENU, storeMenu);
    }

    /**
     * 메뉴 상세 정보 조회
     * @param storeIdx
     * @param menuIdx
     * @return 메뉴 상세 정보
     */
    @Override
    public DefaultResponse findDetailMenu(int storeIdx, int menuIdx) {
        int count  = menuMapper.getMenuCount(storeIdx, menuIdx);

        // 해당 매장에 메뉴가 없거나
        // 매장이 없거나
        // 메뉴가 없을 경우
        if(count == 0) {
            return DefaultResponse.res(Status.NOT_FOUND, Message.NOT_FOUND_MENU_DETAIL);
        }
        // 메뉴 사이즈-가격 조회
        final List<SizePrice> sizePrices = menuMapper.findSizePricesByMenuIdx(menuIdx);

        // 메뉴 정보가 없을 때
        if(sizePrices.isEmpty()) {
            return DefaultResponse.res(Status.NOT_FOUND, Message.NOT_FOUND_SIZE_PRICE);
        } else {
            return DefaultResponse.res(Status.OK, Message.FIND_MENU_DETAIL, sizePrices);
        }
    }
}
