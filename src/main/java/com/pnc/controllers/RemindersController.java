package com.pnc.controllers;

import com.pnc.ResponseDto;
import com.pnc.entities.CreditCardDto;
import com.pnc.entities.FeeWaiver;
import com.pnc.entities.StudentGraduation;
import com.pnc.services.ServiceReminders;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.List;

@RestController
@RequestMapping("/reminders/v1")
@Slf4j
public class RemindersController {

    @Autowired
    private ServiceReminders serviceReminders;

    @GetMapping("/reminders")
    public Mono<ResponseEntity<ResponseDto<RemindersDto>>> getReminders(String mdmPartyId){
        return serviceReminders.getReminders(mdmPartyId)
                .flatMap(reminders ->
                        Mono.just(ResponseEntity.ok(ResponseDto.forSuccess(reminders)))
                );
    }

    /**
     * Solo visible en un navegador
     * @return chuncked data
     */
    @GetMapping(value = "/data/flux", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Flux<ManualReminder> streamDataFlux() {
        return Flux.interval(Duration.ofSeconds(3)).map(i -> {
            log.info("Creating object");
            return ManualReminder.builder().build();
        });
    }

    @Builder
    @Getter
    @Setter
    public static class RemindersDto implements Serializable {
        private ActivateCardDto activateCard;
        private FeeWaiver feeWaiver;
        private StudentGraduation studentWaiverDetails;
        private UnschedulledBillsDto unschedulledBills;
    }

    @Builder
    @Getter
    public static class ActivateCardDto{
        List<DebitCardDto> debitCard;
        List<CreditCardDto> creditCard;
    }


    @Builder
    @Getter
    public static class UnschedulledBillsDto{
        private BigDecimal totalAmount;
        private TransactionsDto transactions;
    }

    @Builder
    @Getter
    @Setter
    public static class TransactionsDto{
        private List<Ebill> ebills;
        private List<CreditCardTransaction> creditCard;
        private List<ManualReminder> manualReminders;
    }

    @Builder
    @Getter
    public static class CreditCardTransaction{
        private BigDecimal minAmountDue;
        private BigDecimal amountDue;
        private String dueDate;
        private String status;
    }

    @Builder
    @Getter
    public static class ManualReminder{
        private BigDecimal minAmountDue;
        private BigDecimal amountDue;
        private String dueDate;
    }

    @Builder
    @Getter
    public static class Ebill{
        private String billId;
        private String billerName;
        private BigDecimal amountDue;
        private BigDecimal minAmountDue;
        private BigDecimal balance;
        private String status;
        private String payeeId;
        private String paymentDate;

    }

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DebitCardDto{
        private String maskedCardNumber;
        private String accountId;
        private String accountName;
        private String openDate;
        private CardType cardType;

    }

    public enum CardType{
        CREDIT_CARD,
        DEBIT_CARD
    }
}
