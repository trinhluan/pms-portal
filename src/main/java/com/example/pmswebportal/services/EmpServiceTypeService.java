package com.example.pmswebportal.services;

import com.example.pmswebportal.model.EmpServiceType;
import java.util.List;

public interface EmpServiceTypeService {

    /**
     * get all service type
     * 
     * @return
     */
    List<EmpServiceType> getDataByFldEmpNo(String emplNo);

    /**
     * add data List<EmpServiceType>
     */
    void addEmpServiceType(List<EmpServiceType> empServiceTypeList);

    /**
     * delete data by emplNo
     * 
     * @param emplNo
     */
    void deleteByFldEmpNo(String emplNo);

}
