package com.pnc.entities;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CustomerRelationshipManagementDetail {
    private String mdmPartyIdentifier;
    private String mdmContractIdentifier;
    private String vendorName;
    private String product;
    private String userVendorIdentifier;
    private String accountVendorNumber;
    private String enrollmentIndicator;
    private Boolean isPreferedAccount;
    private String createdDateTime;
    private String updatedDateTime;
    private String originalSourceSystem;
}
