package com.example.pmswebportal.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.pmswebportal.dto.AccountReq;
import com.example.pmswebportal.dto.CheckUserByLogIdReq;
import com.example.pmswebportal.dto.EmployeeDetail;
import com.example.pmswebportal.dto.EmployeeProfile;
import com.example.pmswebportal.dto.EmployeeResponse;
import com.example.pmswebportal.dto.LoginResponse;
import com.example.pmswebportal.dto.UpdatePassReponse;
import com.example.pmswebportal.dto.UpdatePassReq;
import com.example.pmswebportal.model.Employee;
import com.example.pmswebportal.security.CustomAccountDetail;
import com.example.pmswebportal.security.JwtTokenProvider;
import com.example.pmswebportal.services.EmployeeService;

@RestController
public class EmployeeController extends BaseController {

    @Autowired
    private AuthenticationManager authenticateManager;

    @Autowired
    private JwtTokenProvider authenticationService;

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("login")
    public ResponseEntity<?> authenticateAccount(@RequestBody @Validated AccountReq req) {
        Authentication authentication = authenticateManager
                .authenticate(new UsernamePasswordAuthenticationToken(req.getLoginId(), req.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = authenticationService.generateJwtTokenLogin(req.getLoginId());

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setAccessToken(token);

        CustomAccountDetail account = (CustomAccountDetail) authentication.getPrincipal();
        EmployeeResponse employeeResponse = new EmployeeResponse();
        BeanUtils.copyProperties(account.getEmployee(), employeeResponse);
        loginResponse.setEmployee(employeeResponse);

        return new ResponseEntity<>(loginResponse, HttpStatus.OK);
    }

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
        try {
            status = employeeService.sendEmailOtp(req.getLoginId(), request);
        } catch (Exception e) {
            // Luan: TODO
            e.printStackTrace();
        }
        return new ResponseEntity<>(
                status,
                HttpStatus.OK);
    }

    @PostMapping("/addEmployee")
    ResponseEntity<?> addEmployee(@RequestBody EmployeeDetail employee) {
        employeeService.registerEmployee(employee);
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }

    @PostMapping("/updateEmployee")
    ResponseEntity<?> updateEmployee(@RequestBody EmployeeDetail employee) {
        employeeService.updateEmployee(employee);
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }

    @PostMapping("/updateMyprofile")
    ResponseEntity<?> updateMyprofile(@RequestBody EmployeeProfile employeeProfile) {
        employeeService.updateProfile(employeeProfile);
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }

    @PostMapping("updatepassword")
    ResponseEntity<?> updatePassword(@RequestBody UpdatePassReq req) {
        UpdatePassReponse updatePassReponse = employeeService.updatePassword(req);
        return new ResponseEntity<>(updatePassReponse, HttpStatus.OK);
    }    
}
