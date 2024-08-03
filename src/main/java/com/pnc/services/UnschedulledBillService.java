package com.pnc.services;

import com.pnc.controllers.RemindersController;
import reactor.core.publisher.Mono;

public interface UnschedulledBillService {
    Mono<RemindersController.UnschedulledBillsDto> getUnschedulledBills(String mdmPartyId);

}
