package com.pnc.services.impl;

import com.pnc.entities.FeeWaiver;
import com.pnc.entities.StudentGraduation;
import com.pnc.services.PreferenceWaiverService;
import com.pnc.controllers.RemindersController;
import com.pnc.services.ServiceReminders;
import com.pnc.services.UnschedulledBillService;
import com.pnc.entities.CreditCardDto;
import com.pnc.services.CardSummaryDetailsService;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@Slf4j
public class ServiceRemindersImpl implements ServiceReminders {

    @Autowired
    private CardSummaryDetailsService cardSummaryDetailsService;

    @Autowired
    private PreferenceWaiverService preferenceWaiverService;

    @Autowired
    private UnschedulledBillService unschedulledBillService;
    @Override
    public Mono<RemindersController.RemindersDto> getReminders(String mdmPartyId) {

        var cardDetailsSummary= cardSummaryDetailsService.getCardDetails(mdmPartyId);
        var preferenceWaiverSummary = preferenceWaiverService.getPreferenceWaiverDetails(mdmPartyId);
        var unschedulledBillSummary = unschedulledBillService.getUnschedulledBills(mdmPartyId);

        return Mono.zip(cardDetailsSummary,preferenceWaiverSummary,
                (cardDetails, preferenceWaiverDetails) ->
                    RemindersController.RemindersDto.builder()
                                    .activateCard(
                                            RemindersController.ActivateCardDto.builder()
                                                    .creditCard(cardDetails.getCreditCard())
                                                    .debitCard(cardDetails.getDebitCardDto())
                                            .build()
                                    )
                                    .feeWaiver(preferenceWaiverDetails.getFeeWaiver())
                                    .studentWaiverDetails(preferenceWaiverDetails.getStudentGraduations())
                                    .build()
                ).zipWith(unschedulledBillSummary)
                .flatMap(tuple->{
                    var reminders= tuple.getT1();
                    var unschedulledBills = tuple.getT2();
                    reminders.setUnschedulledBills(unschedulledBills);
                    return Mono.just(reminders);
                });
    }

    @Builder
    @Getter
    public static class CardDetails{
        List<CreditCardDto> creditCard;
        List<RemindersController.DebitCardDto> debitCardDto;
    }

    @Builder
    @Getter
    public static class PreferenceWaiverDetails{
        private StudentGraduation studentGraduations;
        private FeeWaiver feeWaiver;
    }

}
