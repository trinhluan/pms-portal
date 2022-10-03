package com.example.pmswebportal.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "tblsyssecpwdblist")
public class SysSecpwdBlist {

    /** */
    @Id
    @Column(name = "fldID", nullable = false)
    private int fldID;

    /** */
    @Column(name = "fldName", nullable = false, length = 50)
    private String fldName;

    /** */
    @Column(name = "fldEmpNameM", nullable = false, length = 5)
    private String fldEmpNameM;

    /** */
    @Column(name = "fldLastMDate", nullable = false)
    private Timestamp fldLastMDate;

    /** */
    @Column(name = "fldComID", nullable = false, length = 20)
    private String fldComID;

}