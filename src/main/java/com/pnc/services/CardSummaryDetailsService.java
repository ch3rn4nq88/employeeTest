package com.pnc.services;

import com.pnc.services.impl.ServiceRemindersImpl;
import lombok.SneakyThrows;
import reactor.core.publisher.Mono;

public interface CardSummaryDetailsService {
    @SneakyThrows
    Mono<ServiceRemindersImpl.CardDetails> getCardDetails(String mdmPartyId);
}
