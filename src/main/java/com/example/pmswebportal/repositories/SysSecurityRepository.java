package com.example.pmswebportal.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.pmswebportal.model.SysSecurity;

@Repository
public interface SysSecurityRepository extends JpaRepository<SysSecurity, String> {

    SysSecurity findTopBy();
}
