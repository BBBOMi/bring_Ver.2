package com.wonder.bring.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Store {
    // 운영시간
    private String openingHours;
    // 휴무일
    private String breakDays;
    // 전화번호
    private String number;
}
