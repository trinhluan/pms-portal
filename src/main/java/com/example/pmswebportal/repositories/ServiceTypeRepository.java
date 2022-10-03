package com.example.pmswebportal.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.pmswebportal.model.ServiceType;

@Repository
public interface ServiceTypeRepository extends JpaRepository<ServiceType, String> {

    /**
     * get all service type
     * 
     * @return
     */
    List<ServiceType> findAllByOrderByFldSeqAsc();
}
