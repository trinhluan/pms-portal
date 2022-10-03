package com.example.pmswebportal.services.implement;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.pmswebportal.model.ServiceType;
import com.example.pmswebportal.repositories.ServiceTypeRepository;
import com.example.pmswebportal.services.ServiceTypeService;

@Service
public class ServiceTypeServiceImpl implements ServiceTypeService {

    @Autowired
    private ServiceTypeRepository serviceTypeRepository;

    @Override
    public List<ServiceType> getAllOrderByfldSeq() {
        return serviceTypeRepository.findAllByOrderByFldSeqAsc();
    }

}
