package com.example.pmswebportal.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.pmswebportal.model.EmpServiceType;

@Repository
public interface EmpServiceTypeRepository extends JpaRepository<EmpServiceType, String> {

    /**
     * Get all service type by emplNo
     * 
     * @param fldEmpNo
     * @return
     */
    List<EmpServiceType> findByFldEmpNo(String fldEmpNo);

    /**
     * delete By FldEmpNo
     * 
     * @param fldEmpNo
     */
    void deleteByFldEmpNo(String fldEmpNo);
}
