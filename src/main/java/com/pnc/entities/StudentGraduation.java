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
public class StudentGraduation {
    private List<Account> accounts;

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Account{
        private Boolean studentFlag;
        private String wbbAccountId;
    }
}

