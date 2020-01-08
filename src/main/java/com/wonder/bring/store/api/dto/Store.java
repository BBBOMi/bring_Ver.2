package com.wonder.bring.store.api.dto;

import lombok.Data;

@Data
public class Store {
    // 운영시간
    private String time;
    // 휴무일
    private String breakDays;
    // 전화번호
    private String number;
}
