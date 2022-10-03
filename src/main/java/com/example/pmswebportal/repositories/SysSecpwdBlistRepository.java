package com.example.pmswebportal.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.pmswebportal.model.SysSecpwdBlist;

@Repository
public interface SysSecpwdBlistRepository extends JpaRepository<SysSecpwdBlist, String> {

}
