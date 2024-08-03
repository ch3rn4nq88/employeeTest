package com.pnc.entities;

import com.pnc.controllers.RemindersController;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreditCardDto{
    private String maskedCardNumber;
    private String accountId;
    private String accountName;
    private String openDate;
    private RemindersController.CardType cardType;

}
