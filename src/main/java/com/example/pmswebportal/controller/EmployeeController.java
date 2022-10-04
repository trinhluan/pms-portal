package com.example.pmswebportal.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.pmswebportal.dto.CheckUserByLogIdReq;
import com.example.pmswebportal.dto.EmployeeDetail;
import com.example.pmswebportal.dto.EmployeeProfile;
import com.example.pmswebportal.dto.UpdatePassReponse;
import com.example.pmswebportal.dto.UpdatePassReq;
import com.example.pmswebportal.model.Employee;
import com.example.pmswebportal.services.EmployeeService;

@RestController
public class EmployeeController extends BaseController {
    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/searchEmployee")
    ResponseEntity<?> searchEmployee(@RequestBody Employee employee) {
        return new ResponseEntity<>(
                employeeService.searchEmployee(employee.getFldSiteCode(),
                        employee.getFldEmpLoginID(),
                        employee.getFldEmpName(), employee.getFldEmpEmail(),
                        employee.getFldAllowLogIn(),
                        employee.getFldPreferredLang(), employee.getFldEmpStatus()),
                HttpStatus.OK);
    }

    @GetMapping("/detailEmployee")
    ResponseEntity<?> getDetailEmployee(@RequestParam("fldEmpNo") String fldEmpNo) {
        return new ResponseEntity<>(
                employeeService.getDetailEmployee(fldEmpNo),
                HttpStatus.OK);
    }

    @PostMapping("finduserbyloginid")
    ResponseEntity<?> findUserByLoginId(@RequestBody CheckUserByLogIdReq req) {
        Employee employee = employeeService.checkAuthenticated(req.getLoginId());
        return new ResponseEntity<>(
                employee == null ? false : true,
                HttpStatus.OK);
    }

    @PostMapping("sendmailotp")
    ResponseEntity<?> sendMailOtp(@RequestBody CheckUserByLogIdReq req, HttpServletRequest request) {
        boolean status = false;
        status = employeeService.sendEmailOtp(req.getLoginId(), request);
        return new ResponseEntity<>(
                status,
                HttpStatus.OK);
    }

    @PostMapping("/addEmployee")
    ResponseEntity<?> addEmployee(@RequestBody EmployeeDetail employee) {
        return new ResponseEntity<>(employeeService.registerEmployee(employee), HttpStatus.OK);
    }

    @PostMapping("/updateEmployee")
    ResponseEntity<?> updateEmployee(@RequestBody EmployeeDetail employee) {
        return new ResponseEntity<>(employeeService.updateEmployee(employee), HttpStatus.OK);
    }

    @PostMapping("/updateMyprofile")
    ResponseEntity<?> updateMyprofile(@RequestBody EmployeeProfile employeeProfile) {
        return new ResponseEntity<>(employeeService.updateProfile(employeeProfile), HttpStatus.OK);
    }

    @PostMapping("updatepassword")
    ResponseEntity<?> updatePassword(@RequestBody UpdatePassReq req) {
        UpdatePassReponse updatePassReponse = employeeService.updatePassword(req);
        return new ResponseEntity<>(updatePassReponse, HttpStatus.OK);
    }
}
