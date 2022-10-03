package com.example.pmswebportal.services.implement;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.pmswebportal.model.SysOtp;
import com.example.pmswebportal.repositories.SysOtpRepository;
import com.example.pmswebportal.services.SysOtpService;


@Service
public class SysOtpServiceImpl implements SysOtpService {

    @Autowired
    private SysOtpRepository sysRepository;

    @Override
    public void deleteAllRecordExpiryDate(Timestamp expiryDate) {
        sysRepository.deleteAllByfldExpiryDateTimeLessThanEqual(expiryDate);
    }

    @Override
    public void insertOTP(SysOtp sysotp) {
        sysRepository.save(sysotp);        
    }

    @Override
    public SysOtp getOtpByLoginId(String loginId) {
        return sysRepository.findAllByfldLoginID(loginId);
    }
}
