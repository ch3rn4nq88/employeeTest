package com.pnc.controllers;

import com.pnc.ResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDate;

@RestController
@RequestMapping("/moneyBar/v1")
public class MoneyBarController {

    @GetMapping
    public ResponseEntity<ResponseDto<CalendarController.CalendarDayViewResponse>> getMoneyBar(){
        return ResponseEntity.ok(ResponseDto.forSuccess(CalendarController.CalendarDayViewResponse.builder().build()));
    }

    public static class MoneyBarBalanceDto{
        private BigDecimal spendAvailableBalance;
        private BigDecimal reserveAvailableBalance;
        private BigDecimal totalAmountSavedSoFar;
        private BigDecimal scheduledOut;
        private BigDecimal freeBalance;
        private Boolean hasDangerDays;
        private LocalDate fromDangerDayDate;
        private LocalDate toDangerDayDate;
        private BigDecimal dangerDayScheduledOut;
        private BigDecimal expectedIncome;
        private BigDecimal dangerDayOverdraft;

    }
}
