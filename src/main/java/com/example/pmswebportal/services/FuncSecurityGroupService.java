package com.example.pmswebportal.services;

import com.example.pmswebportal.model.FuncSecurityGroup;
import java.util.List;

public interface FuncSecurityGroupService {

    /**
     * get all Func Security Group
     * 
     * @return
     */
    List<FuncSecurityGroup> getAllOrderByfldSeq();

}
