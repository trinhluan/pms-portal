package com.example.pmswebportal.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "tblemployee")
public class Employee {

    /** */
    @Id
    @Column(name = "fldEmpNo", nullable = false, length = 20)
    private String fldEmpNo = "";

    /** */
    @Column(name = "fldEmpName", nullable = false, length = 50)
    private String fldEmpName;

    /** */
    @Column(name = "fldEmpStatus", nullable = false, length = 10)
    private String fldEmpStatus;

    /** */
    @Column(name = "fldEmpEmail", nullable = false, length = 50)
    private String fldEmpEmail;

    /** */
    @Column(name = "fldEmpMobile", nullable = false, length = 20)
    private String fldEmpMobile = "";

    /** */
    @Column(name = "fldAllowLogIn", nullable = false, length = 20)
    private String fldAllowLogIn;

    /** */
    @Column(name = "fldEmpLoginID", nullable = false, length = 20)
    private String fldEmpLoginID;

    /** */
    @Column(name = "fldEmpPwd", nullable = false, length = 100)
    private String fldEmpPwd = "";

    /** */
    @Column(name = "fldPreferredLang", nullable = false, length = 10)
    private String fldPreferredLang;

    /** */
    @Column(name = "fldSiteCode", nullable = false, length = 10)
    private String fldSiteCode;

    /** */
    @Column(name = "fldIsPatrolStaff", nullable = false, length = 10)
    private String fldIsPatrolStaff = "";

    /** */
    @Column(name = "fldRespPersonTech", nullable = false, length = 10)
    private String fldRespPersonTech = "";

    /** */
    @Column(name = "fldRespPersonNonTech", nullable = false, length = 20)
    private String fldRespPersonNonTech = "";

    /** */
    @Column(name = "fldFuncSecurityGroup", nullable = false, length = 20)
    private String fldFuncSecurityGroup = "";

    /** */
    @Column(name = "fldDataSecurityGroup", nullable = false, length = 20)
    private String fldDataSecurityGroup = "";

    /** */
    @Column(name = "fldHomePage", nullable = false, length = 20)
    private String fldHomePage = "";

    /** */
    @Column(name = "fldCompanyID", nullable = false, length = 11)
    private Integer fldCompanyID = -1;

    /** */
    @Column(name = "fldRole", nullable = false, length = 100)
    private String fldRole = "";

    /** */
    @Column(name = "fldVarchar01", nullable = false, length = 100)
    private String fldVarchar01 = "";

    /** */
    @Column(name = "fldVarchar02", nullable = false, length = 100)
    private String fldVarchar02 = "";

    /** */
    @Column(name = "fldVarchar03", nullable = false, length = 100)
    private String fldVarchar03 = "";

    /** */
    @Column(name = "fldVarchar04", nullable = false, length = 100)
    private String fldVarchar04 = "";

    /** */
    @Column(name = "fldVarchar05", nullable = false, length = 100)
    private String fldVarchar05 = "";

    /** */
    @Column(name = "fldNum01", nullable = false)
    private Double fldNum01 = 0.0;

    /** */
    @Column(name = "fldNum02", nullable = false)
    private Double fldNum02 = 0.0;

    /** */
    @Column(name = "fldNum03", nullable = false)
    private Double fldNum03 = 0.0;

    /** */
    @Column(name = "fldNum04", nullable = false)
    private Double fldNum04 = 0.0;

    /** */
    @Column(name = "fldNum05", nullable = false)
    private Double fldNum05 = 0.0;

    /** */
    @Column(name = "fldLongtext01", nullable = false)
    private String fldLongtext01 = "";

    /** */
    @Column(name = "fldLongtext02", nullable = false)
    private String fldLongtext02 = "";

    /** */
    @Column(name = "fldEmpNameC", nullable = false, length = 50)
    private String fldEmpNameC = "";

    /** */
    @Column(name = "fldFirstCDate", nullable = false)
    private Timestamp fldFirstCDate = Timestamp.valueOf("0001-01-01 00:00:00");

    /** */
    @Column(name = "fldEmpNameM", nullable = false, length = 50)
    private String fldEmpNameM = "";

    /** */
    @Column(name = "fldLastMDate", nullable = false)
    private Timestamp fldLastMDate = Timestamp.valueOf("0001-01-01 00:00:00");

    /** */
    @Column(name = "fldComID", nullable = false, length = 100)
    private String fldComID = "";

}