package com.example.pmswebportal.services.implement;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.pmswebportal.model.EmpServiceType;
import com.example.pmswebportal.repositories.EmpServiceTypeRepository;
import com.example.pmswebportal.services.EmpServiceTypeService;

@Service
public class EmpServiceTypeServiceImpl implements EmpServiceTypeService {

    @Autowired
    private EmpServiceTypeRepository empServiceTypeRepository;

    @Override
    public List<EmpServiceType> getDataByFldEmpNo(String emplNo) {
        return empServiceTypeRepository.findByFldEmpNo(emplNo);
    }

    @Override
    public void addEmpServiceType(List<EmpServiceType> empServiceTypeList) {
        empServiceTypeRepository.saveAll(empServiceTypeList);
    }

    @Override
    public void deleteByFldEmpNo(String emplNo) {
        empServiceTypeRepository.deleteByFldEmpNo(emplNo);
    }

}
