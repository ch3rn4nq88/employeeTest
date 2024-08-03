package com.pnc.entities;

import lombok.*;

import java.util.List;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CustomerRelationshipManagement {
    private List<CustomerRelationshipManagementDetail> externalBillPayDetails;
}
