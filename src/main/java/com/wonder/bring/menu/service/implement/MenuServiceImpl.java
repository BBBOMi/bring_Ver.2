package com.wonder.bring.menu.service.implement;

import com.wonder.bring.menu.api.dto.Menu;
import com.wonder.bring.menu.api.dto.SizePrice;
import com.wonder.bring.store.api.dto.StoreMenu;
import com.wonder.bring.menu.mapper.MenuMapper;
import com.wonder.bring.store.mapper.StoreMapper;
import com.wonder.bring.common.dto.DefaultRes;
import com.wonder.bring.menu.service.MenuService;
import com.wonder.bring.common.utils.Message;
import com.wonder.bring.common.utils.Status;
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
    public DefaultRes<StoreMenu> findMenuByStoreIdx(int storeIdx) {
        // storeIdx로 메뉴 리스트 조회
        final StoreMenu storeMenu = storeMapper.findStoreByStoreIdx(storeIdx);
        List<Menu> menuList = menuMapper.findMenuByStoreIdx(storeIdx);

        // 해당 매장이 없을 때
        if(storeMenu == null) {
            return DefaultRes.res(Status.NOT_FOUND, Message.NOT_FOUND_STORE);
        } else if(menuList.isEmpty()) {
            return DefaultRes.res(Status.NOT_FOUND, Message.NOT_FOUND_LIST_MENU);
        } else {
            storeMenu.setMenuList(menuList);
        }
        return DefaultRes.res(Status.OK, Message.FIND_LIST_MENU, storeMenu);
    }

    /**
     * 메뉴 상세 정보 조회
     * @param storeIdx
     * @param menuIdx
     * @return 메뉴 상세 정보
     */
    @Override
    public DefaultRes findDetailMenu(int storeIdx, int menuIdx) {
        int count  = menuMapper.findStoreMenu(storeIdx, menuIdx);

        // 해당 매장에 메뉴가 없거나
        // 매장이 없거나
        // 메뉴가 없을 경우
        if(count == 0) {
            return DefaultRes.res(Status.NOT_FOUND, Message.NOT_FOUND_MENU_DETAIL);
        }
        // 메뉴 사이즈-가격 조회
        final List<SizePrice> sizePrices = menuMapper.findSizePriceByMenuIdx(menuIdx);

        // 메뉴 정보가 없을 때
        if(sizePrices.isEmpty()) {
            return DefaultRes.res(Status.NOT_FOUND, Message.NOT_FOUND_SIZE_PRICE);
        } else {
            return DefaultRes.res(Status.OK, Message.FIND_MENU_DETAIL, sizePrices);
        }
    }
}
