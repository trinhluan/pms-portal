package com.example.pmswebportal.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.pmswebportal.model.DataSecurityGroup;

@Repository
public interface DataSecurityGroupRepository extends JpaRepository<DataSecurityGroup, String> {

    List<DataSecurityGroup> findAllByOrderByFldSeqAsc();
}
