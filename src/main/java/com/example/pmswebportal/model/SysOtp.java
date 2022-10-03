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
@Table(name = "tblsysotp")
public class SysOtp {

    /** */
    @Id
    @Column(name = "fldLoginID", nullable = false, length = 20)
    private String fldLoginID;

    /** */
    @Column(name = "fldOTP", nullable = false, length = 10)
    private String fldOTP;

    /** */
    @Column(name = "fldExpiryDateTime", nullable = false)
    private Timestamp fldExpiryDateTime;

    /** */
    @Column(name = "fldDateTime", nullable = false)
    private Timestamp fldDateTime;

    public SysOtp() {
        
    }

    public SysOtp(String fldLoginID, String fldOTP, Timestamp fldExpiryDateTime, Timestamp fldDateTime) {
        this.fldLoginID = fldLoginID;
        this.fldOTP = fldOTP;
        this.fldExpiryDateTime = fldExpiryDateTime;
        this.fldDateTime = fldDateTime;
    }

}