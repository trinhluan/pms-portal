package com.example.pmswebportal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.pmswebportal.services.ServiceTypeService;

@RestController
public class ServiceTypeController extends BaseController {

    @Autowired
    private ServiceTypeService serviceTypeService;

    @GetMapping("/getServiceTypeList")
    ResponseEntity<?> getDetailEmployee() {
        return new ResponseEntity<>(
                serviceTypeService.getAllOrderByfldSeq(),
                HttpStatus.OK);
    }
}
