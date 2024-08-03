package com.pnc.services.impl;

import com.pnc.controllers.RemindersController;
import com.pnc.entities.CustomerRelationshipManagementDetail;
import com.pnc.services.CustomerRelationshipManagementAdapter;
import com.pnc.services.ManualReminderService;
import com.pnc.services.ServiceBillsAndPayees;
import com.pnc.services.UnschedulledBillService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UnscheduledBillServiceImpl implements UnschedulledBillService {

    private final CustomerRelationshipManagementAdapter customerRelationshipManagement;
    private final ManualReminderService manualRemindersService;
    private final ServiceBillsAndPayees serviceBillsAndPayees;


    private Mono<List<RemindersController.Ebill>> getEbills(String mdmParty){
        return serviceBillsAndPayees.getBills(mdmParty)
                .zipWith(serviceBillsAndPayees.getPayee(mdmParty)).flatMap(tuple->{
            var bill= tuple.getT1();
            var payee =  tuple.getT2();
            return Mono.just(bill.stream().map(currentBill->
                    RemindersController.Ebill.builder()
                    .payeeId(payee.getPayeeIdentifier())
                    .billId(currentBill.getBillIdentifier())
                    .balance(currentBill.getBalanceAmountDue())
                    .billerName(payee.getFullName())
                    .paymentDate(currentBill.getDueDate())
                    .status(currentBill.getPaymentStatus())
                    .minAmountDue(currentBill.getMinimumAmountDue())
                    .amountDue(currentBill.getAmountDue())
                    .build()).toList());
        });
    }

    @Override
    @SuppressWarnings("unchecked")
    public Mono<RemindersController.UnschedulledBillsDto> getUnschedulledBills(String mdmPartyId) {
        return customerRelationshipManagement.getCustomerRelationshipManagement(mdmPartyId)
            .flatMap(customerRelationshipManagementDetails ->{
                var enrolledActive=customerRelationshipManagementDetails.getExternalBillPayDetails().stream()
                        .filter(crm-> "ACTIVE".equalsIgnoreCase(crm.getEnrollmentIndicator())).toList();
                return Mono.just(enrolledActive);
            })
            .doOnNext(list->
                    System.out.println(((List<CustomerRelationshipManagementDetail>)list).size())
            ).zipWith(getCreditCardTransactions(mdmPartyId))
            .flatMap(tuple-> {
                var result =((List<CustomerRelationshipManagementDetail>)tuple.getT1()).stream().map(obj->getEbills(mdmPartyId)).toList();
                return Mono.zip(result,objects -> {
                var ebills= Arrays.stream(objects)
                        .map((Object element) -> (List<RemindersController.Ebill>) element)
                        .flatMap(Collection::stream).collect(Collectors.toList());
                return RemindersController.UnschedulledBillsDto.builder()
                        .transactions(RemindersController.TransactionsDto.builder()
                                .ebills(ebills)
                                .creditCard(tuple.getT2())
                                .build())
                        .build();
                });
            }).zipWith(getManualReminders(mdmPartyId))
            .flatMap(tuple->{
                tuple.getT1().getTransactions().setManualReminders(tuple.getT2());
                return Mono.just(tuple.getT1());
            })
            .doOnNext(unschedulledBillsDto ->
                            System.out.println(unschedulledBillsDto.getTotalAmount()))
              .doOnError(throwable ->
                      log.error("Error getting unscheduled transactions for mdmmParty {}",mdmPartyId, throwable));

    }


    private Mono<List<RemindersController.CreditCardTransaction>> getCreditCardTransactions(String mdmPartyId) {
        return Mono.just(List.of(RemindersController.CreditCardTransaction.builder().build()));
    }
    private Mono<List<RemindersController.ManualReminder>> getManualReminders(String mdmPartyId){
        var payeesMono =serviceBillsAndPayees.getPayee(mdmPartyId);
        var manualRemindersMono = manualRemindersService.getManualReminders(mdmPartyId);
        return Mono.zip(payeesMono, manualRemindersMono,(payees, manualReminders) ->
             List.of(RemindersController.ManualReminder.builder().build())
        );
    }
}
