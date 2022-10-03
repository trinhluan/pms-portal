package com.example.pmswebportal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.pmswebportal.services.FuncSecurityGroupService;

@RestController
public class FuncSecurityGroupController extends BaseController {

    @Autowired
    private FuncSecurityGroupService funcSecurityGroupService;

    @GetMapping("/getFuncSecurityGroupList")
    ResponseEntity<?> getDetailEmployee() {
        return new ResponseEntity<>(
                funcSecurityGroupService.getAllOrderByfldSeq(),
                HttpStatus.OK);
    }
}
