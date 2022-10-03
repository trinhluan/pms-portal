package com.example.pmswebportal.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.pmswebportal.model.FuncSecurityGroupItems;

@Repository
public interface FuncSecurityGroupItemsRepository extends JpaRepository<FuncSecurityGroupItems, String> {

}
