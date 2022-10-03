package com.example.pmswebportal.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "tblsyssqllog2022")
public class SysSqlLog2022 {

    /** */
    @Id
    @Column(name = "fldSysSQLLogID", nullable = false)
    private Integer fldSysSQLLogID;

    /** */
    @Column(name = "fldEmpNo", length = 20, nullable = false)
    private String fldEmpNo;

    /** */
    @Column(name = "fldEmpName", length = 50, nullable = false)
    private String fldEmpName;

    /** */
    @Column(name = "fldHost", length = 50, nullable = false)
    private String fldHost;

    /** */
    @Column(name = "fldAction", nullable = false)
    private String fldAction;

    /** */
    @Column(name = "fldDateTime", nullable = false)
    private Timestamp fldDateTime;

}