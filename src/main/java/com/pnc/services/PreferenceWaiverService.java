package com.pnc.services;

import com.pnc.services.impl.ServiceRemindersImpl;
import reactor.core.publisher.Mono;

public interface PreferenceWaiverService {
    Mono<ServiceRemindersImpl.PreferenceWaiverDetails> getPreferenceWaiverDetails(String mdmPartyId);

}
