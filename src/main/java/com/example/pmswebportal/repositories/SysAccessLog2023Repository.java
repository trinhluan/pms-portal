package com.example.pmswebportal.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.pmswebportal.model.SysAccessLog2023;

@Repository
public interface SysAccessLog2023Repository extends JpaRepository<SysAccessLog2023, String> {

}
