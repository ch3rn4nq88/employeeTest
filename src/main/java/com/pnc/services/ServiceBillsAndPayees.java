package com.pnc.services;

import com.pnc.entities.Bill;
import com.pnc.entities.Payee;
import lombok.SneakyThrows;
import reactor.core.publisher.Mono;

import java.util.List;

public interface ServiceBillsAndPayees {
    @SneakyThrows
    Mono<Payee> getPayee(String mdmPartyId);

    @SneakyThrows
    Mono<List<Bill>> getBills(String mdmPartyId);
}
