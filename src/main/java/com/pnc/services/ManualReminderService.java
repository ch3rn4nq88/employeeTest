package com.pnc.services;

import com.pnc.adapters.ManualReminderAdapterImpl;
import com.pnc.entities.ManualReminder;
import reactor.core.publisher.Mono;

public interface ManualReminderService{

    Mono<ManualReminder> getManualReminders(String mdmPartyId);

}
