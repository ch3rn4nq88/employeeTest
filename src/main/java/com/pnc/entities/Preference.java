package com.pnc.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Preference {

    private String mdmContractIdentifier;
    private String preferenceIdentifier;
    private String preferenceName;
    private String createdDateTime;
    private String modifiedDateTime;
    private Boolean preferenceValue;

}
