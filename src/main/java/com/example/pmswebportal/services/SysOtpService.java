package com.example.pmswebportal.services;

import java.sql.Timestamp;

import com.example.pmswebportal.model.SysOtp;


public interface SysOtpService {

    /**
     * deleteAllRecordExpiryDate
     * 
     * @param expiryDate
     * @return
     */
    void deleteAllRecordExpiryDate(Timestamp expiryDate);

    /**
     * insert one record
     * 
     * @param expiryDate
     * @return
     */
    void insertOTP(SysOtp sysotp);

    /**
     * getOtpByLoginId
     * 
     * @param loginId
     * @return
     */
    SysOtp getOtpByLoginId(String loginId);
}
