package com.pnc.services.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pnc.entities.Bill;
import com.pnc.entities.Payee;
import com.pnc.services.ServiceBillsAndPayees;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.io.InputStream;
import java.util.List;

@Service
public class ServiceBillsAndPayeesImpl implements ServiceBillsAndPayees {

    @SneakyThrows
    @Override
    public Mono<Payee> getPayee(String mdmPartyId){
        ObjectMapper mapper2 = new ObjectMapper();
        InputStream is2 = Thread.currentThread().getContextClassLoader()
                .getResourceAsStream("payee.json");
        var payee = mapper2.readValue(is2, Payee.class);
        return Mono.just(payee);

    }

    @SneakyThrows
    @Override
    public Mono<List<Bill>> getBills(String mdmPartyId){
        ObjectMapper mapper3 = new ObjectMapper();
        InputStream is3 =  Thread.currentThread().getContextClassLoader()
                .getResourceAsStream("bills.json");
        var bills  =
                mapper3.readValue(is3, new TypeReference<List<Bill>>(){});
        return Mono.just(bills);
    }
}
