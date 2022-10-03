package com.example.pmswebportal.services.implement;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.pmswebportal.model.FuncSecurityGroup;
import com.example.pmswebportal.repositories.FuncSecurityGroupRepository;
import com.example.pmswebportal.services.FuncSecurityGroupService;

@Service
public class FuncSecurityGroupServiceImpl implements FuncSecurityGroupService {

    @Autowired
    private FuncSecurityGroupRepository funcSecurityGroupRepository;

    @Override
    public List<FuncSecurityGroup> getAllOrderByfldSeq() {
        return funcSecurityGroupRepository.findAllByOrderByFldSeqAsc();
    }

}
