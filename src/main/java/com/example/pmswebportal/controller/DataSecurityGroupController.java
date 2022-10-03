package com.example.pmswebportal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.pmswebportal.services.DataSecurityGroupService;

@RestController
public class DataSecurityGroupController extends BaseController {

    @Autowired
    private DataSecurityGroupService service;

    @GetMapping("/getDataSercurityGroupList")
    ResponseEntity<?> getDetailEmployee() {
        return new ResponseEntity<>(
                service.getAllOrderByfldSeq(),
                HttpStatus.OK);
    }
}
