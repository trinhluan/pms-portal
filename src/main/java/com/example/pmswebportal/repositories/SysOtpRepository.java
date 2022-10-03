package com.example.pmswebportal.repositories;

import java.sql.Timestamp;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import com.example.pmswebportal.model.SysOtp;

@Repository
public interface SysOtpRepository extends JpaRepository<SysOtp, String> {

    /**
     * delete record by ldExpiryDateTime <= expDate
     * @param expDate
     */
    void deleteAllByfldExpiryDateTimeLessThanEqual(Timestamp expDate);

    /**
     * findAllByfldLoginID
     * @param expDate
     */
    SysOtp findAllByfldLoginID(String loginId);
    

}
