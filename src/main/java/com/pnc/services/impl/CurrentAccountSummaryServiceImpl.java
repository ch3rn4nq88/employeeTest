package com.pnc.services.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pnc.entities.CurrentAccountSummary;
import com.pnc.services.CurrentAccountSummaryService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.io.InputStream;

@Service
@Slf4j
public class CurrentAccountSummaryServiceImpl implements CurrentAccountSummaryService {

    @SneakyThrows
    @Override
    public Mono<CurrentAccountSummary> getCurrentAccountSummary(String mdmPartyId){
        ObjectMapper mapper = new ObjectMapper();
        InputStream is =Thread.currentThread().getContextClassLoader()
                .getResourceAsStream("current-account-summary.json");
        return Mono.just(mapper.readValue(is, CurrentAccountSummary.class));
    }
}
