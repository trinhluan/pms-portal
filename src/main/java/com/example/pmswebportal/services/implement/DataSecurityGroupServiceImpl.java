package com.example.pmswebportal.services.implement;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.pmswebportal.model.DataSecurityGroup;
import com.example.pmswebportal.repositories.DataSecurityGroupRepository;
import com.example.pmswebportal.services.DataSecurityGroupService;

@Service
public class DataSecurityGroupServiceImpl implements DataSecurityGroupService {

    @Autowired
    private DataSecurityGroupRepository dataSecurityGroupRepository;

    @Override
    public List<DataSecurityGroup> getAllOrderByfldSeq() {
        return dataSecurityGroupRepository.findAllByOrderByFldSeqAsc();
    }

}
