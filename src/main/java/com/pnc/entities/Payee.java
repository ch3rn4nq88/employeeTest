package com.pnc.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Payee {
    private String fullName;
    private String maskedAccountNumber;
    private String category;
    private String status;
    private String payeeUri;
    private String payeeIdentifier;
    private String nickName;

}
