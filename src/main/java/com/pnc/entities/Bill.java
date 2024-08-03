package com.pnc.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Bill {

    private BigDecimal amountDue;
    private BigDecimal balanceAmountDue;
    private String payeeLogoUrl;
    private String payeeTeaserAdImageUrl;
    private String payeeTeaserAdUrl;
    private String payeeTeaserAdText;
    private String billType;
    private String dateFirstViewed;
    private String payeeUri;
    private String reasonForFiling;
    private String filerNote;
    private String detailUrlIframeSupportedIndicator;
    private BigDecimal minimumAmountDue;
    private Integer replacedBillIdentifier;
    private Boolean useDueDateTextIndicator;
    private String billUri;
    private String dueDate;
    private String billIdentifier;
    private String dueDateText;
    private String paymentStatus;
    private String actionByDate;

}
