package com.example.pmswebportal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.pmswebportal.services.EmpServiceTypeService;

@RestController
public class EmpServiceTypeController extends BaseController {

    @Autowired
    private EmpServiceTypeService empServiceTypeService;

    @GetMapping("/getEmpServiceTypeList")
    ResponseEntity<?> getDetailEmployee(@RequestParam("fldEmpNo") String fldEmpNo) {
        return new ResponseEntity<>(
                empServiceTypeService.getDataByFldEmpNo(fldEmpNo),
                HttpStatus.OK);
    }
}
