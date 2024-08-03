package com.pnc.adapters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pnc.entities.CustomerRelationshipManagement;
import com.pnc.entities.ManualReminder;
import com.pnc.services.CustomerRelationshipManagementAdapter;
import com.pnc.services.ManualReminderService;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import reactor.core.publisher.Mono;

import java.io.InputStream;

@RequiredArgsConstructor
public class ManualReminderAdapterImpl implements ManualReminderService {
    @Override
    @SneakyThrows
    public Mono<ManualReminder> getManualReminders(String mdmPartyId){
        ObjectMapper mapper = new ObjectMapper();
        InputStream is = Thread.currentThread().getContextClassLoader()
                .getResourceAsStream("manual-reminders.json");
        return Mono.just(mapper.readValue(is, ManualReminder .class));
    }
}
