package com.example.pmswebportal.services;

import com.example.pmswebportal.model.ServiceType;
import java.util.List;

public interface ServiceTypeService {

    /**
     * get all service type
     * 
     * @return
     */
    List<ServiceType> getAllOrderByfldSeq();

}
