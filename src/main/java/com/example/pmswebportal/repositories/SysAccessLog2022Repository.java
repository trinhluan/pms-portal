package com.example.pmswebportal.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.pmswebportal.model.SysAccessLog2022;

@Repository
public interface SysAccessLog2022Repository extends JpaRepository<SysAccessLog2022, String> {

}
