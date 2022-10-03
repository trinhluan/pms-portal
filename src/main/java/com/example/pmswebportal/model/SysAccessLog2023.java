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
@Table(name = "tblsysaccesslog2022")
public class SysAccessLog2023 {

    /** */
    @Id
    @Column(name = "fldSysAccessLogID", nullable = false)
    private Integer fldSysAccessLogID;

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
    @Column(name = "fldVersion", length = 25, nullable = false)
    private String fldVersion;

    /** */
    @Column(name = "fldAction", length = 100, nullable = false)
    private String fldAction;

    /** */
    @Column(name = "fldAuditLog", nullable = false)
    private String fldAuditLog;

    /** */
    @Column(name = "fldAuditComID", length = 10, nullable = false)
    private String fldAuditComID;

    /** */
    @Column(name = "fldDateTime", nullable = false)
    private Timestamp fldDateTime;

}