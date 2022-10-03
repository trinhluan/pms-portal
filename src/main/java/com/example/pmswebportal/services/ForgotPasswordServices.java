package com.example.pmswebportal.services;

import com.example.pmswebportal.model.Employee;
import java.util.List;

public interface ForgotPasswordServices {

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
    void registerEmployee(Employee employee);

    /**
     * Update employee
     * 
     * @param employee
     */
    void updateEmployee(Employee employee);
}
