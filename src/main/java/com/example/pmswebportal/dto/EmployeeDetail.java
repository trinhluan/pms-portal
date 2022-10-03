package com.example.pmswebportal.dto;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeDetail {

    /** */
    private String fldEmpNo;

    /** */
    private String fldEmpLoginID;

    /** */
    private String fldEmpName;

    /** */
    private String fldEmpEmail;

    /** */
    private String fldSiteCode;

    /** */
    private String fldAllowLogIn;

    /** */
    private String fldPreferredLang;

    /** */
    private String fldEmpStatus;

    /** */
    private String fldIsPatrolStaff;

    /** */
    private String fldRespPersonTech;

    /** */
    private String fldRespPersonNonTech;

    /** */
    private String[] empServiceType;

    /** */
    private String empPwd;

    /** */
    private String empConfirmPwd;

    /** */
    private String fldFuncSecurityGroup;

    /** */
    private String fldDataSecurityGroup;

    /** */
    private String fldHomePage;

}