package com.pnc.services.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pnc.entities.*;
import com.pnc.services.CurrentAccountSummaryService;
import com.pnc.services.PreferenceWaiverService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class PreferenceWaiverServiceImpl implements PreferenceWaiverService {

    private final CurrentAccountSummaryService currentAccountSummaryService;
    @Override
    @SneakyThrows
    public Mono<ServiceRemindersImpl.PreferenceWaiverDetails> getPreferenceWaiverDetails(String mdmPartyId) {

        var studentWaiverResult= getStudentWaiver(mdmPartyId)
                .flatMap(studentWaiver -> Mono.zip(studentWaiver, objects ->
                    Arrays.stream(objects)
                            .map(student-> (StudentGraduation.Account)student)
                            .collect(Collectors.toList())
                ).onErrorReturn(List.of()));

        var feeWaiverResult= getFeeWaiver(mdmPartyId)
                .flatMap(feeWaiver -> Mono.zip(feeWaiver, objects ->
                    Arrays.stream(objects)
                                    .map(feeWaive-> (FeeWaiver.Account)feeWaive)
                                    .collect(Collectors.toList())
               ).onErrorReturn(List.of()));

        return Mono.zip(studentWaiverResult, feeWaiverResult, (studentGraduations, feeWaivers) ->
            ServiceRemindersImpl.PreferenceWaiverDetails.builder()
                    .feeWaiver(FeeWaiver.builder()
                            .message("Your Monthly service Charge has been waived")
                            .messageId(8)
                            .accounts(feeWaivers).build())
                    .studentGraduations(StudentGraduation.builder().accounts(studentGraduations).build())
                    .build()
        );
    }


    private Mono<List<Mono<FeeWaiver.Account>>> getFeeWaiver(String mdmPartyId){
        return getMdmContractIds(mdmPartyId).map(mdmContractIds->
                mdmContractIds.stream().map(mdmContractId->
                        Mono.zip(getPreferencesByMdmPartyAndContractid(mdmPartyId,mdmContractId),Mono.just(mdmContractId),
                                (preferences, s) ->{
                                    Optional<Preference> opt= preferences.stream()
                                    .filter(preference -> "VW_SHOW_WAIVER".equalsIgnoreCase(preference.getPreferenceName()))
                                    .filter(Preference::getPreferenceValue).findFirst();
                                    if (opt.isPresent())
                                        return FeeWaiver.Account.builder()
                                                .wbbAccountId(mdmContractId).build();
                                    else
                                        return null;
                                })
                ).collect(Collectors.toList())
        );
    }

    private Mono<List<Mono<StudentGraduation.Account>>> getStudentWaiver(String mdmPartyId){
        return getMdmContractIds(mdmPartyId).map(mdmContractIds->
                mdmContractIds.stream().map(mdmContractId->
                        Mono.zip(getPreferencesByMdmPartyAndContractid(mdmPartyId,mdmContractId),Mono.just(mdmContractId),
                                (preferences, s) ->{
                                    Optional<Preference> opt= preferences.stream()
                                            .filter(preference -> "VW_STUDENT_EXPIRE_MESSAGE".equalsIgnoreCase(preference.getPreferenceName()))
                                            .filter(Preference::getPreferenceValue).findFirst();
                                    if (opt.isPresent())
                                        return StudentGraduation.Account.builder()
                                                .studentFlag(Boolean.TRUE)
                                                .wbbAccountId(mdmContractId).build();
                                    else
                                        return null;
                                })
                ).collect(Collectors.toList())
        );
    }


    private Mono<List<String>> getMdmContractIds(String mdmPartyId){
        return currentAccountSummaryService.getCurrentAccountSummary(mdmPartyId)
                .flatMap(currentAccountSummary ->
                        Mono.just(currentAccountSummary.getAccountSummary())
                ).flatMap(currentAccounts ->
                        Mono.just(currentAccounts
                                .stream()
                                .map(CurrentAccountSummary.CurrentAccount::getMdmContractIdentifier)
                                .collect(Collectors.toList()))
                );
    }

    @SneakyThrows
    private Mono<List<Preference>> getPreferencesByMdmPartyAndContractid(String mdmParty, String mdmContractId){
        ObjectMapper mapper3 = new ObjectMapper();
        InputStream is3 =  Thread.currentThread().getContextClassLoader()
                .getResourceAsStream("preferences.json");
        return Mono.just(mapper3.readValue(is3, new TypeReference<List<Preference>>(){}));

    }


}

