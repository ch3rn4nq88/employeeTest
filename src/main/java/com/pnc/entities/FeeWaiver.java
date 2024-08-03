package com.pnc.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FeeWaiver{
    private Integer messageId;
    private String message;
    private List<Account> accounts;



    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Account{
        private String wbbAccountId;
    }
}

