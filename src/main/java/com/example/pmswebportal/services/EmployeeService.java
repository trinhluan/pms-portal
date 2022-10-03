package com.example.pmswebportal.services;

import com.example.pmswebportal.dto.EmployeeDetail;
import com.example.pmswebportal.dto.EmployeeProfile;
import com.example.pmswebportal.dto.UpdatePassReponse;
import com.example.pmswebportal.dto.UpdatePassReq;
import com.example.pmswebportal.model.Employee;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

public interface EmployeeService {

    /**
     * Check authenticated
     * 
     * @param loginId
     * @return
     */
    Employee checkAuthenticated(String loginId);

    /**
     * Get Detail UI
     * 
     * @param fldEmpNo
     * @return Employee
     */
    Employee getDetailEmployee(String fldEmpNo);

    /**
     * 
     * @param fldSiteCode
     * @param fldEmpLoginID
     * @param fldEmpName
     * @param fldEmpEmail
     * @param fldAllowLogIn
     * @param fldPreferredLang
     * @param fldEmpStatus
     * @return
     */
    List<Employee> searchEmployee(String fldSiteCode, String fldEmpLoginID, String fldEmpName,
            String fldEmpEmail, String fldAllowLogIn,
            String fldPreferredLang, String fldEmpStatus);

    /**
     * crete new employee
     * 
     * @param employee
     */
    void registerEmployee(EmployeeDetail employee);

    /**
     * Send OPT to email of user
     * 
     * @param loginId
     * @param request
     */
    boolean sendEmailOtp(String loginId, HttpServletRequest request);

    /**
     * Update employee
     * 
     * @param employee
     */
    void updateEmployee(EmployeeDetail employee);

    public void updateProfile(EmployeeProfile employeeProfile);

    /**
     * Update Password
     * 
     * @param info
     */
    UpdatePassReponse updatePassword(UpdatePassReq info);
}
