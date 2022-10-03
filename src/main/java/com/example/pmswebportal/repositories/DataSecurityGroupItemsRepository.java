package com.example.pmswebportal.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.pmswebportal.model.DataSecurityGroupItems;

@Repository
public interface DataSecurityGroupItemsRepository extends JpaRepository<DataSecurityGroupItems, String> {

}
