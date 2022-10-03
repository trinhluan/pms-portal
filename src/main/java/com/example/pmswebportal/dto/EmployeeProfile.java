package com.example.pmswebportal.dto;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeProfile {

    /** */
    private String fldEmpNo;

    /** */
    private String fldEmpLoginID;

    /** */
    private String fldEmpName;

    /** */
    private String fldEmpEmail;

    /** */
    private String fldPreferredLang;

    /** */
    private String empNewPwd;

    /** */
    private String empConfirmNewPwd;

    /** */
    private String fldHomePage;

}