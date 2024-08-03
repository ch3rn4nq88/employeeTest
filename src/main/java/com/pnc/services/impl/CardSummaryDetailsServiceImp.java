package com.pnc.services.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pnc.controllers.RemindersController;
import com.pnc.entities.CreditCardDto;
import com.pnc.entities.CreditCardManagedAccount;
import com.pnc.entities.DebitCard;
import com.pnc.services.CardSummaryDetailsService;
import com.pnc.services.CreditCardSummaryService;
import com.pnc.services.CurrentAccountSummaryService;
import jakarta.annotation.PostConstruct;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class CardSummaryDetailsServiceImp implements CardSummaryDetailsService {
    private final CreditCardSummaryService creditCardSummaryService;

    @PostConstruct
    private void init(){

       // getMultipleElements();
      //  getSingleElement();

        Function<String, Publisher<String>> mapper = s -> Flux.just(s.toUpperCase().split(""));
        Flux<String> inFlux = Flux.just("baeldung", ".", "com");
        Flux<String> outFlux = inFlux.flatMap(mapper);

        Publisher<String> publisher= new Publisher<String>() {
            @Override
            public void subscribe(Subscriber<? super String> subscriber) {
                log.info(subscriber.toString());
            }
        };



        Function<String, String> mapperMono2 = s -> s.toLowerCase();

        Mono<String> inMono = Mono.just("my String");
        Mono<String> outMono = inMono.flatMap(mapperMono);
        Mono<String> blockMono = outMono.map(mapperMono2);




    }

    static Function<String, Mono<String>> mapperMono = s -> Mono.just(s.toUpperCase());

    private void getSingleElement(){
        var application = new Application();
        application.setApplicationId(List.of("A"));

        Function<Application, Publisher<String>> mapper = app -> Flux.fromIterable(app.applicationId);



        var result = Mono.just(application)
                .flatMap(application1 -> getProductsPerOrder(application1.getApplicationId().get(0)));



        var stringFinale= result.block();
        System.out.println(stringFinale);

    }
    private void getMultipleElements(){
        var application = new Application();
        application.setApplicationId(List.of("1","2","3","4","5"));
        var monoApplication =  Mono.just(application)
                .flatMapIterable(applicationVar ->
                        (applicationVar.getApplicationId()
                                .stream()
                                .map(CardSummaryDetailsServiceImp::getProductsPerOrder).toList())

                );

        var list = Flux.zip(monoApplication, (result)->{
            return Arrays.stream(result.toArray()).toList();
        }).blockLast();

        System.out.println(list);


    }

    static Mono<String> getProductsPerOrder(String id){
        return Mono.just(id);
    }
    @SneakyThrows
    @Override
    public Mono<ServiceRemindersImpl.CardDetails> getCardDetails(String mdmPartyId){
        return creditCardSummaryService.getCreditCardResults(mdmPartyId)
                .zipWith(creditCardSummaryService.getDebitCard(mdmPartyId)).collectList().flatMap(
                (values) ->{
                    var creditCards = values.stream()
                            .map(record-> mapCreditCard(record.getT1()))
                            .toList();
                    var debitCards  =values.stream()
                            .map(Tuple2::getT2)
                            .toList().stream()
                            .filter(debitCard -> debitCard.getAccountCardActivatorIndicator().equalsIgnoreCase("N"))
                            .map(this::mapDebitCard)
                            .toList();
                    ServiceRemindersImpl.CardDetails cardDetails= ServiceRemindersImpl.CardDetails.builder()
                            .creditCard(creditCards)
                            .debitCardDto(debitCards)
                            .build();
                    return Mono.just(cardDetails);
                }
        ).doOnError(throwable -> {
            log.error("Error on mdmPartyId {}",mdmPartyId,throwable);
        });

    }
    private CreditCardDto mapCreditCard(CreditCardManagedAccount.CreditCard creditCard){

        var openingDateMapped= Optional.ofNullable(creditCard.getOpeningDate())
                .map(openingDate-> LocalDateTime.parse(openingDate).atZone(ZoneId.systemDefault()))
                .map(openingDate-> openingDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'00:mm:ss.SSS-05:00")))
                .orElse(null);
        return CreditCardDto.builder().cardType(RemindersController.CardType.CREDIT_CARD)
                .maskedCardNumber(maskAccountNUmber(creditCard.getAccountNumber()))
                .accountId(creditCard.getAccountNumber())
                .accountName(creditCard.getAccountProductName())
                .openDate(openingDateMapped)
                .build();
    }

    @Data
    static class Application{
        private List<String> applicationId;
    }



    private RemindersController.DebitCardDto mapDebitCard(DebitCard debitCard){

        var openingDate= LocalDateTime.parse(debitCard.getOpeningDate()).atZone(ZoneId.systemDefault());

        return RemindersController.DebitCardDto.builder()
                .accountId(debitCard.getAccountNumber())
                .maskedCardNumber(maskAccountNUmber(debitCard.getCardNumber()))
                .accountName(debitCard.getAccountProductName())
                .cardType(RemindersController.CardType.DEBIT_CARD)
                .openDate(openingDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'00:mm:ss.SSS-05:00")))
                .build();
    }

    private String maskAccountNUmber(String number){
        return "x" +number.substring(number.length()-4);
    }

}
