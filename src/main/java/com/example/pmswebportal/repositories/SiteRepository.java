package com.example.pmswebportal.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.pmswebportal.model.Site;

@Repository
public interface SiteRepository extends JpaRepository<Site, String> {

}
