package com.example.pmswebportal.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.pmswebportal.services.SiteService;

@RestController
public class SiteController extends BaseController {

    @Autowired
    private SiteService siteService;

    @PostMapping("/allSites")
    ResponseEntity<?> searchEmployee() {
        return new ResponseEntity<>(siteService.getAllSites(), HttpStatus.OK);
    }

    @PostMapping("getpropertiesofsite")
    ResponseEntity<?> getPropertiesOfSite()  {
        HashMap<String,Object>allProperties = new HashMap<>();
        try {
            allProperties = siteService.getAllPropertiesOfSite();
        } catch (Exception e) {
            // Luan: TODO
            e.printStackTrace();
        }
        return new ResponseEntity<>(allProperties, HttpStatus.OK);
    }

}
