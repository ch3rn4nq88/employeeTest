package com.pnc.config;

import com.pnc.adapters.ManualReminderAdapterImpl;
import com.pnc.controllers.RemindersController;
import com.pnc.entities.ManualReminder;
import com.pnc.services.ManualReminderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;


@Slf4j
@Configuration
public class ManualRemindersConfiguration {

    @ConditionalOnProperty(name="feature-manual-reminders", havingValue = "true")
    @Bean
    public ManualReminderAdapterImpl getCustomerRelationshipManagement(){
        return new ManualReminderAdapterImpl();
    }
    @Bean
    @ConditionalOnMissingBean
    public ManualReminderService getManualReminders(){
        log.debug("Manual reminders management disabled");
        return new ManualReminderService() {
            @Override
            public Mono<ManualReminder> getManualReminders(String mdmPartyId) {
                return Mono.just(ManualReminder.builder().build());
            }
        };
    }
}
