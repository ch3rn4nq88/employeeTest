package com.pnc.services.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pnc.entities.CreditCardManagedAccount;
import com.pnc.entities.DebitCard;
import com.pnc.services.CreditCardSummaryService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.io.InputStream;
import java.util.List;

@Service
@Slf4j
public class CreditCardSummaryImpl implements CreditCardSummaryService {

    @SneakyThrows
    @Override
    public Flux<CreditCardManagedAccount.CreditCard> getCreditCardResults(String mdmPartyId){
        ObjectMapper mapper2 = new ObjectMapper();
        InputStream is2 = Thread.currentThread().getContextClassLoader()
                .getResourceAsStream("credit-card-summary.json");
        var creditCardSummary = mapper2.readValue(is2, CreditCardManagedAccount.class);

        return Flux.fromIterable(creditCardSummary.getManagedAccount());
    }
    @SneakyThrows
    @Override
    public Flux<DebitCard> getDebitCard(String mdmPartyId){
        ObjectMapper mapper3 = new ObjectMapper();
        InputStream is3 =  Thread.currentThread().getContextClassLoader()
                .getResourceAsStream("debit-card-summary.json");
        var debitCardSummary  =
                mapper3.readValue(is3, new TypeReference<List<DebitCard>>(){});
        return Flux.fromIterable(debitCardSummary);
    }
}
