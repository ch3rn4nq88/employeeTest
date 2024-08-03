package com.pnc.services;

import com.pnc.controllers.RemindersController;
import reactor.core.publisher.Mono;

public interface ServiceReminders {
    Mono<RemindersController.RemindersDto> getReminders(String mdmPartyId);
}
