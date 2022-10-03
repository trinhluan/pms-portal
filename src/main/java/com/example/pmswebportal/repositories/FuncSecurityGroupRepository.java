package com.example.pmswebportal.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.pmswebportal.model.FuncSecurityGroup;

@Repository
public interface FuncSecurityGroupRepository extends JpaRepository<FuncSecurityGroup, String> {

    List<FuncSecurityGroup> findAllByOrderByFldSeqAsc();
}
