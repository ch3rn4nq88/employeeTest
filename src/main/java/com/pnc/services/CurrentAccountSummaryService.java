package com.pnc.services;

import com.pnc.entities.CurrentAccountSummary;
import reactor.core.publisher.Mono;

public interface CurrentAccountSummaryService {
    Mono<CurrentAccountSummary> getCurrentAccountSummary(String mdmPartyId);

}
