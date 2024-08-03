package com.pnc.config;

import com.pnc.adapters.CustomerRelationshipManagementAdapterImpl;
import com.pnc.entities.CustomerRelationshipManagement;
import com.pnc.services.CustomerRelationshipManagementAdapter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;

@Slf4j
@Configuration
public class CustomerManagementRelationshipConfiguration {
    @ConditionalOnProperty(name="feature-customer-relationship-management", havingValue = "true")
    @Bean
    public CustomerRelationshipManagementAdapter getOnConditional(){
        return CustomerRelationshipManagementAdapterImpl.builder().build();
    }
    @Bean
    @ConditionalOnMissingBean
    CustomerRelationshipManagementAdapter getOnMissing(){
        log.debug("Customer relationship management disabled");
        return (mdmPartyId)-> Mono.just(CustomerRelationshipManagement.builder().build());
    }
}
