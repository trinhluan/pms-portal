package com.example.pmswebportal.model.idclass;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class DataSecurityGroupItemsId implements Serializable{
    private String fldGroupCode;
    private String fldSiteBuilding;
}
