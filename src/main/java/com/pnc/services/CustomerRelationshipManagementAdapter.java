package com.pnc.services;

import com.pnc.entities.CustomerRelationshipManagement;
import reactor.core.publisher.Mono;

public interface CustomerRelationshipManagementAdapter {
    Mono<CustomerRelationshipManagement> getCustomerRelationshipManagement(String mdmPartyId);

}
