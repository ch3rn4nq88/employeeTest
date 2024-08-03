package com.pnc.entities;

import lombok.*;

import java.util.List;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CurrentAccountSummary {

    private List<CurrentAccount> accountSummary;

    @Builder
    @AllArgsConstructor
    @Data
    @NoArgsConstructor
    public static class CurrentAccount{
        private String mdmContractIdentifier;
        private String mdmPartyId;
        private String accountTypeExtension;
    }

}
