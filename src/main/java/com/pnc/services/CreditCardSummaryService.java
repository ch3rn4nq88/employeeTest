package com.pnc.services;

import com.pnc.entities.CreditCardManagedAccount;
import com.pnc.entities.DebitCard;
import lombok.SneakyThrows;
import reactor.core.publisher.Flux;

public interface CreditCardSummaryService {
    @SneakyThrows
    Flux<CreditCardManagedAccount.CreditCard> getCreditCardResults(String mdmPartyId);

    @SneakyThrows
    Flux<DebitCard> getDebitCard(String mdmPartyId);
}
