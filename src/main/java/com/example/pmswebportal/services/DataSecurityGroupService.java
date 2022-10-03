package com.example.pmswebportal.services;

import com.example.pmswebportal.model.DataSecurityGroup;
import java.util.List;

public interface DataSecurityGroupService {

    /**
     * get all Data Security Group
     * 
     * @return
     */
    List<DataSecurityGroup> getAllOrderByfldSeq();

}
