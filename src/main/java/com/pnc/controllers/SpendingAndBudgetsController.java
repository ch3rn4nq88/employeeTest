package com.pnc.controllers;

import com.pnc.ResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/spendingAndBudgets/v1")
public class SpendingAndBudgetsController {

    @GetMapping
    public ResponseEntity<ResponseDto<CalendarController.CalendarDayViewResponse>> getBudgets(){
        return ResponseEntity.ok(ResponseDto.forSuccess(CalendarController.CalendarDayViewResponse.builder().build()));
    }

    public static class SpendingBudget{

        private Map<LocalDate, SpendBudget> spendingBudgetDetails;
        private String groupId;

    }

    public static class SpendBudget{
        private BigDecimal totalSpending;
        private BigDecimal amountReceived;
        private List<Category> categories;

    }

    public static class Category{
        private Long categoryId;
        private BigDecimal amountReceived;
        private BigDecimal amountSpent;
        private BigDecimal amountRemaining;
        private BigDecimal overBudget;
    }
}
