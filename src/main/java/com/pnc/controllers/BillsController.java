package com.pnc.controllers;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.pnc.ResponseDto;
import com.pnc.services.CustomerRelationshipManagementAdapter;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/bills/v1")
public class BillsController {

    @Autowired
    private CustomerRelationshipManagementAdapter adapter;


    BillsController(){

        var x=Prueba.builder().build();
        System.out.println(x);

    }

    @PostConstruct
    public void init() throws IOException {
        //var x=getBills();
        //var y = getPayees();
        //System.out.println();
    }

    @GetMapping("/bills")
    public ResponseEntity<ResponseDto<List<Bill>>> getBills() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        InputStream is = Bill.class.getResourceAsStream("/bills.json");
        var bill = mapper.readValue(is, Bill.class);
        return ResponseEntity.ok(ResponseDto.forSuccess(List.of(bill)));

    }

    @GetMapping("/payees")
    public ResponseEntity<ResponseDto<List<Payee>>> getPayees() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        InputStream is = Payee.class.getResourceAsStream("/payee.json");
        var payee = mapper.readValue(is, Payee.class);
        return ResponseEntity.ok(ResponseDto.forSuccess(List.of(payee)));
    }



    @Builder
    @Getter
    static class CustomerRelationshipManagement{
        private String activeEnrollment;
        private String mdmPartyId;
        private List<String> mdmContractId;
    }
    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Bill implements Serializable {
        private BigDecimal amountDue;
        private BigDecimal minAmountDue;
        private BigDecimal minimumAmountDue;
        private BigDecimal balanceAmountDue;
        private String billType;
        private String reasonForFiling;
        private String billUri;
        private String billIdentifier;
        private String paymentStatus;
        private String payeeUri;
        private String mdmPartyId;
        private String actionByDate;
        private String payeeLogoUrl;
        private String payeeTeaserAdImageUrl;
        private String payeeTeaserAdUrl;
        private String payeeTeaserAdText;
        private String dueDateText;
        private String filerNote;
        private Boolean detailUrlIframeSupportedIndicator;
        private Boolean useDueDateTextIndicator;
        private Integer replacedBillIdentifier;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        @JsonDeserialize(using = LocalDateDeserializer.class)
        @JsonSerialize(using = LocalDateSerializer.class)
        private LocalDate dateFirstViewed;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        @JsonDeserialize(using = LocalDateDeserializer.class)
        @JsonSerialize(using = LocalDateSerializer.class)
        private LocalDate dueDate;

    }

    @Builder
    @Getter
    public static class PaymentPayeeResponse{
        List<Payee> payees;
    }
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Payee{
        private String fullName;
        private String payeeIdentifier;
        private String payeeUri;
        private String nickName;
        private String maskedAccountNumber;
        private String category;
        private String status;
    }


    @Builder
    public static class Prueba{
        @Builder.Default
        private List<String> x= new ArrayList<>();
    }
}
