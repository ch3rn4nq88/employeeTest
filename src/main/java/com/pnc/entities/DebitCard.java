package com.pnc.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public  class DebitCard{

    private String mdmContractIdentifier;
    private String mdmPartyIdentifier;
    private String accountFamily;
    private String accountProductName;
    private String accountNumber;
    private String accountCardActivatorIndicator;
    private String openingDate;
    private String originatingSourceSystem;
    private String cardNumber;

}
