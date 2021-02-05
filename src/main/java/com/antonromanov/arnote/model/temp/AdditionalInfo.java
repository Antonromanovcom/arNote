package com.antonromanov.arnote.model.temp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AdditionalInfo {
    private String name;
    private String typeLic;
    private String typeName;
    private String segmentLic;
    private String segmentName;
    private String ucpId;
    private String id;
    private String inn;
    private String kpp;
    private String clientManagerFullName;
}
