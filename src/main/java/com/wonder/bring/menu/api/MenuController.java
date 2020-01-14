package com.wonder.bring.menu.api;

import com.wonder.bring.store.api.dto.StoreMenu;
import com.wonder.bring.common.dto.DefaultResponse;
import com.wonder.bring.menu.service.MenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.wonder.bring.common.dto.DefaultResponse.FAIL_DEFAULT_RESPONSE;

/**
 * Create by YoungEun on 2018-12-29.
 */

@Slf4j
@RequestMapping("stores/{storeIdx}")
@RestController
public class MenuController {

    private MenuService menuService;

    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    /**
     * 메뉴 리스트 조회
     * @param storeIdx 해당 매장
     * @return 메뉴 리스트
     */
    @GetMapping("menu")
    public ResponseEntity getMenuList(@PathVariable(value = "storeIdx") final int storeIdx) {
        try {
            DefaultResponse<StoreMenu> defaultResponse = menuService.findMenuByStoreIdx(storeIdx);
            return new ResponseEntity<>(defaultResponse, HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(FAIL_DEFAULT_RESPONSE, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 메뉴 상세 정보 조회
     * @param storeIdx
     * @param menuIdx
     * @return 메뉴 상세 정보
     */
    @GetMapping("menu/{menuIdx}")
    public ResponseEntity getMenuDetail(@PathVariable(value = "storeIdx") final int storeIdx,
                                        @PathVariable(value = "menuIdx") final int menuIdx) {
        try {
            DefaultResponse defaultResponse = menuService.findDetailMenu(storeIdx, menuIdx);
            return new ResponseEntity<>(defaultResponse, HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(FAIL_DEFAULT_RESPONSE, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
