package com.example.pmswebportal.services.implement;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.pmswebportal.services.SecurityPolicyService;
import com.example.pmswebportal.model.Site;
import com.example.pmswebportal.repositories.SiteRepository;
import com.example.pmswebportal.services.SiteService;

@Service
public class SiteServiceImpl implements SiteService {

    @Autowired
    private SiteRepository siteRepository;

    @Autowired
    private SecurityPolicyService securityPolicyService;

    @Value("${countdown.otp}")
    private int countdownOtp;

    @Override
    public List<Site> getAllSites() {
        return siteRepository.findAll();
    }

    @Override
    public HashMap<String, Object> getAllPropertiesOfSite() {
        HashMap<String,Object>allProperties = new HashMap<>();
        allProperties = securityPolicyService.getSecurityPolicy();
        allProperties.put("countdownOtp", countdownOtp);
        return allProperties ;
    }

}
