package com.example.pmswebportal.services;

import com.example.pmswebportal.model.Site;

import java.util.HashMap;
import java.util.List;

public interface SiteService {

    /**
     * Get All data
     * 
     * @param fldEmpNo
     * @return Employee
     */
    List<Site> getAllSites();

    /**
     * get all properties or config of site 
     */
    HashMap<String, Object> getAllPropertiesOfSite();

}
