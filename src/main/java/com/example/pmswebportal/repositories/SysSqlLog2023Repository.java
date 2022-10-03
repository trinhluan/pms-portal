package com.example.pmswebportal.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.pmswebportal.model.SysSqlLog2023;

@Repository
public interface SysSqlLog2023Repository extends JpaRepository<SysSqlLog2023, String> {

}
