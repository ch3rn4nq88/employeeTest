package com.pnc.adapters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pnc.entities.CustomerRelationshipManagement;
import com.pnc.services.CustomerRelationshipManagementAdapter;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import reactor.core.publisher.Mono;

import java.io.InputStream;
import java.util.List;

@Builder
public class CustomerRelationshipManagementAdapterImpl implements CustomerRelationshipManagementAdapter {

    @SneakyThrows
    public Mono<CustomerRelationshipManagement> getCustomerRelationshipManagement(String mdmPartyId){
        ObjectMapper mapper = new ObjectMapper();
        InputStream is = Thread.currentThread().getContextClassLoader()
                .getResourceAsStream("customer-relationship-management.json");
        return Mono.just(mapper.readValue(is, CustomerRelationshipManagement .class));
    }

}
