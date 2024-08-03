package com.pnc.controllers;

import com.pnc.ResponseDto;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/calendar/v1")
public class CalendarController {

    @GetMapping
    public ResponseEntity<ResponseDto<CalendarDayViewResponse>> getCalendarDayResponse(){
        return ResponseEntity.ok(ResponseDto.forSuccess(CalendarDayViewResponse.builder().build()));
    }

    @Builder
    @Getter
    public static class CalendarDayViewResponse{
        private Map<LocalDate, CalendarDayViewItem> dateEvents;
        private boolean hasDangerDays;
        private LocalDate fromDangerDayDate;
        private LocalDate toDangerDayDate;
        private BigDecimal dangerDayScheduledOut;
        private BigDecimal expectedIncome;
        private BigDecimal dangerDayOverdraft;
        private LocalDate toFreeBalanceDate;
        private BigDecimal avaiableBalance;
    }
    @Builder
    @Getter
    public static class CalendarDayViewItem{
        private String bankHoliday;
        private UnSchedulledBill unSchedulledBill;
        private SavingsGoal savingGoals;
        private TotalIn totalIn;
        private TotalOut totalOut;
    }

    @Builder
    @Getter
    public static class UnSchedulledBill{
        private BigDecimal totalAmount;
        private Map<String, Object> transactions;
    }
    @Builder
    @Getter
    public static class SavingsGoal{
        private BigDecimal totalAmount;
        private BigDecimal totalAmountSavedSoFar;
        private BigDecimal freeToSaveBalance;
        private Map<String, Object> savingGoals;
    }
    @Builder
    @Getter
    public static class TotalIn{
        private BigDecimal totalAmount;
        private Map<String, Object> transactions;    //
    }
    @Builder
    @Getter
    public static class TotalOut{
        private BigDecimal totalAmount;
        private Map<String, Object> transactions;
    }


}
