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
@Table(name = "tblfuncsecuritygroup")
public class FuncSecurityGroup {

    /** */
    @Id
    @Column(name = "fldGroupCode", length = 50, nullable = false)
    private String fldGroupCode;

    /** */
    @Column(name = "fldGroupName", length = 50, nullable = false)
    private String fldGroupName;

    /** */
    @Column(name = "fldStatus", length = 10, nullable = false)
    private String fldStatus;

    /** */
    @Column(name = "fldSeq", nullable = false)
    private Integer fldSeq;

    /** */
    @Column(name = "fldEmpNameC", length = 50, nullable = false)
    private String fldEmpNameC;

    /** */
    @Column(name = "fldFirstCDate", nullable = false)
    private Timestamp fldFirstCDate;

    /** */
    @Column(name = "fldEmpNameM", length = 50, nullable = false)
    private String fldEmpNameM;

    /** */
    @Column(name = "fldLastMDate", nullable = false)
    private Timestamp fldLastMDate;

    /** */
    @Column(name = "fldComID", length = 100, nullable = false)
    private String fldComID;

}