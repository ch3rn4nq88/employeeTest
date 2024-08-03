package com.pnc.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreditCardManagedAccount {

    private List<CreditCard> managedAccount;

    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    public static class CreditCard{

        private String mdmContractIdentifier;
        private String mdmPartyIdentifier;
        private String accountFamily;
        private String accountProductName;
        private String accountNumber;
        private String maskedCardNumber;
        private String openingDate;
        private CreditCardAccount creditCardAccount;

    }

    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    public static class CreditCardAccount{
        private String  accountReissueControlCode;
        private AccountStatement accountStatement;
        private PaymentInfo paymentInfo;
    }

    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    private static class AccountStatement{
        private BigDecimal lastStatementBalance;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        @JsonDeserialize(using = LocalDateDeserializer.class)
        @JsonSerialize(using = LocalDateSerializer.class)
        private LocalDate lastStatementDate;
    }

    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    private static class PaymentInfo{
        private BigDecimal minimumPaymentDue;
    }

}
