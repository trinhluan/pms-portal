package com.example.pmswebportal.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.pmswebportal.model.SysSqlLog2022;

@Repository
public interface SysSqlLog2022Repository extends JpaRepository<SysSqlLog2022, String> {

}
