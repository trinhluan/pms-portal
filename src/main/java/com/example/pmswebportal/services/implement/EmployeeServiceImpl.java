package com.example.pmswebportal.services.implement;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.example.pmswebportal.constants.AppConstants;
import com.example.pmswebportal.dto.EmailDetail;
import com.example.pmswebportal.dto.EmployeeDetail;
import com.example.pmswebportal.dto.EmployeeProfile;
import com.example.pmswebportal.dto.UpdatePassReponse;
import com.example.pmswebportal.dto.UpdatePassReq;
import com.example.pmswebportal.model.EmpServiceType;
import com.example.pmswebportal.model.Employee;
import com.example.pmswebportal.model.SysOtp;
import com.example.pmswebportal.model.SysSecpwdBlist;
import com.example.pmswebportal.repositories.EmployeeRepository;
import com.example.pmswebportal.security.CustomAccountDetail;
import com.example.pmswebportal.services.EmpServiceTypeService;
import com.example.pmswebportal.services.EmployeeService;
import com.example.pmswebportal.services.SecurityPolicyService;
import com.example.pmswebportal.services.SysOtpService;
import com.example.pmswebportal.services.SysSecpwdBlistService;
import com.example.pmswebportal.utilities.MailUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private MailUtil mailUtil;

    @Autowired
    private SecurityPolicyService securityPolicyService;

    @Autowired
    private SysSecpwdBlistService sysSecpwdBlistService;

    @Autowired
    private SysOtpService sysOtpService;

    @Autowired
    private EmpServiceTypeService empServiceTypeService;

    @Value("${countdown.otp}")
    private int countdownOtp;

    /**
     * Check authenticated
     * 
     * @param loginId
     * @return
     */
    @Override
    public Employee checkAuthenticated(String loginId) {
        return employeeRepository.checkAuthenticated(loginId);
    }

    /**
     * Get Detail UI
     * 
     * @param fldEmpNo
     * @return Employee
     */
    @Override
    public Employee getDetailEmployee(String fldEmpNo) {
        return employeeRepository.findByFldEmpNo(fldEmpNo).get();
    }

    /**
     * Search Employee
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
    @Override
    public List<Employee> searchEmployee(String fldSiteCode, String fldEmpLoginID, String fldEmpName,
            String fldEmpEmail, String fldAllowLogIn, String fldPreferredLang, String fldEmpStatus) {
        List<Employee> employees = new ArrayList<>();
        // search no conditions
        if (fldSiteCode == null && fldEmpLoginID == null
                && fldEmpName == null && fldEmpEmail == null
                && fldAllowLogIn == null && fldPreferredLang == null
                && fldEmpStatus == null) {
            employees = employeeRepository.findAll();
            return employees;
        }

        Employee emplSearch = new Employee();
        if (fldSiteCode != null && fldSiteCode != "") {
            emplSearch.setFldSiteCode(fldSiteCode);
        }
        if (fldEmpLoginID != null && fldEmpLoginID != "") {
            emplSearch.setFldEmpLoginID(fldEmpLoginID);
        }
        if (fldEmpName != null && fldEmpName != "") {
            emplSearch.setFldEmpName(fldEmpName);
        }
        if (fldEmpEmail != null && fldEmpEmail != "") {
            emplSearch.setFldEmpEmail(fldEmpEmail);
        }
        if (fldAllowLogIn != null && fldAllowLogIn != "" && fldAllowLogIn != "All") {
            emplSearch.setFldAllowLogIn(fldAllowLogIn);
        }
        if (fldPreferredLang != null && fldPreferredLang != "" && fldPreferredLang != "All") {
            emplSearch.setFldPreferredLang(fldPreferredLang);
        }
        if (fldEmpStatus != null && fldEmpStatus != "") {
            emplSearch.setFldEmpStatus(fldEmpStatus);
        }
        employees = employeeRepository.findAll(Example.of(emplSearch));

        return employees;
    }

    /**
     * crete new employee
     * 
     * @param employee
     */
    @Override
    @Transactional
    public void registerEmployee(EmployeeDetail employeeDto) {
        long number = employeeRepository.count() + 1;
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDto, employee);

        if (employeeDto.getEmpPwd() != null && employeeDto.getEmpPwd() != "") {
            employee.setFldEmpPwd(new BCryptPasswordEncoder().encode(employeeDto.getEmpPwd()));
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomAccountDetail account = (CustomAccountDetail) authentication.getPrincipal();
        employee.setFldEmpNameC(account.getUsername());
        employee.setFldFirstCDate(new Timestamp(new Date().getTime()));
        employee.setFldEmpNameM(account.getUsername());
        employee.setFldLastMDate(new Timestamp(new Date().getTime()));
        employee.setFldEmpNo(new StringBuilder("user").append(String.valueOf(number)).toString());

        employeeRepository.save(employee);

        // update empl service type
        processEmplServicesType(employeeDto);

    }

    /**
     * Update employee
     * 
     * @param employee
     */
    @Override
    @Transactional
    public void updateEmployee(EmployeeDetail employeeDto) {
        Optional<Employee> employee = employeeRepository.findByFldEmpNo(employeeDto.getFldEmpNo());
        if (employee.isPresent()) {
            Employee employee2 = employee.get();
            BeanUtils.copyProperties(employeeDto, employee2);

            if (employeeDto.getEmpPwd() != null && employeeDto.getEmpPwd() != "") {
                employee2.setFldEmpPwd(new BCryptPasswordEncoder().encode(employeeDto.getEmpPwd()));
            }

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            CustomAccountDetail account = (CustomAccountDetail) authentication.getPrincipal();
            employee2.setFldEmpNameM(account.getUsername());
            employee2.setFldLastMDate(new Timestamp(new Date().getTime()));
            employeeRepository.save(employee2);

            // update empl service type
            processEmplServicesType(employeeDto);
        }
    }

    /**
     * update empl service type
     * 
     * @param employeeDto
     */
    private void processEmplServicesType(EmployeeDetail employeeDto) {
        // process table tblempservicetype
        String[] serviceTypes = employeeDto.getEmpServiceType();
        if (serviceTypes != null && serviceTypes.length > 0) {
            // delete list service type in table tblempservicetype
            empServiceTypeService.deleteByFldEmpNo(employeeDto.getFldEmpNo());
            // add all data user select serivce type in screen
            List<EmpServiceType> servTypeList = new ArrayList<>();
            EmpServiceType empServiceType = new EmpServiceType();
            for (String serviceType : serviceTypes) {
                empServiceType = new EmpServiceType();
                empServiceType.setFldEmpNo(employeeDto.getFldEmpNo());
                empServiceType.setFldServiceType(serviceType);
                servTypeList.add(empServiceType);
            }
            empServiceTypeService.addEmpServiceType(servTypeList);
        }
    }

    /**
     * Send OPT to email of user
     * 
     * @param loginId
     * @param request
     */
    @Transactional
    @Override
    public boolean sendEmailOtp(String loginId, HttpServletRequest request) {
        Employee employee = checkAuthenticated(loginId);
        if (employee == null)
            return false;
        int min = 10000;
        int max = 999999;
        // generate otp 6 digit
        int otp = (int) Math.floor(Math.random() * (max - min + 1) + min);
        LocalDateTime current = LocalDateTime.now();
        // delete all expiryDate
        sysOtpService.deleteAllRecordExpiryDate(Timestamp.valueOf(current));
        // insert new record
        SysOtp sysOtp = new SysOtp(loginId, String.valueOf(otp), Timestamp.valueOf(current.plusSeconds(countdownOtp)),
                Timestamp.valueOf(current));
        sysOtpService.insertOTP(sysOtp);
        // send mail
        EmailDetail emailDetail = new EmailDetail();
        emailDetail.setMsgBody(
                String.format(AppConstants.OTP_MSG_BODY, String.valueOf(otp), String.valueOf(countdownOtp)));
        emailDetail.setRecipient(employee.getFldEmpEmail());
        emailDetail.setSubject(AppConstants.OTP_SUBJECT);
        return mailUtil.sendSimpleMail(emailDetail);
    }

    /**
     * Update employee
     * 
     * @param employee
     */
    @Override
    public void updateProfile(EmployeeProfile employeeProfile) {
        Optional<Employee> employee = employeeRepository.findByFldEmpNo(employeeProfile.getFldEmpNo());
        if (employee.isPresent()) {
            Employee employee2 = employee.get();
            BeanUtils.copyProperties(employeeProfile, employee2);

            if (employeeProfile.getEmpNewPwd() != null && employeeProfile.getEmpNewPwd() != "") {
                employee2.setFldEmpPwd(new BCryptPasswordEncoder().encode(employeeProfile.getEmpNewPwd()));
            }

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            CustomAccountDetail account = (CustomAccountDetail) authentication.getPrincipal();
            employee2.setFldEmpNameM(account.getUsername());
            employee2.setFldLastMDate(new Timestamp(new Date().getTime()));
            employeeRepository.save(employee2);
        }
    }

    @Override
    public UpdatePassReponse updatePassword(UpdatePassReq info) {
        UpdatePassReponse updatePassReponse = new UpdatePassReponse();
        Employee employee = employeeRepository.checkAuthenticated(info.getLoginId());
        if (employee == null) {
            updatePassReponse.setStatus(false);
            updatePassReponse.setMessage("User not exist");
            return updatePassReponse;
        }
        // check OTP
        SysOtp sysOtp = sysOtpService.getOtpByLoginId(info.getLoginId());
        if (sysOtp == null) {
            updatePassReponse.setStatus(false);
            updatePassReponse.setMessage("OPT not found");
            return updatePassReponse;
        }
        Timestamp current = Timestamp.valueOf(LocalDateTime.now());
        if (current.after(sysOtp.getFldExpiryDateTime())) {
            updatePassReponse.setStatus(false);
            updatePassReponse.setMessage("Expired OPT");
            return updatePassReponse;
        }

        if(!sysOtp.getFldOTP().equals(info.getOtp())){
            updatePassReponse.setStatus(false);
            updatePassReponse.setMessage("OTP is incorrect");
            return updatePassReponse;
        }
        
        HashMap<String, Object> policy = securityPolicyService.getSecurityPolicy();
        if (policy.get("fldSecPCP").toString().toUpperCase().equals("YES") && (int) policy.get("fldPwdMPRT") == 1) {
            boolean check = new BCryptPasswordEncoder().matches(info.getPassword(), employee.getFldEmpPwd());
            if (check) {
                updatePassReponse.setStatus(false);
                updatePassReponse.setMessage("Cannot be the same as pervious");
                return updatePassReponse;
            }
        }
        if (policy.get("fldSecPCP").toString().toUpperCase().equals("YES")
                && policy.get("fldSecPCP").toString().toUpperCase().equals("YES")) {
            List<SysSecpwdBlist> allBlist = sysSecpwdBlistService.getAllBlackListPass();
            for (SysSecpwdBlist sysSecpwdBlist : allBlist) {
                if (sysSecpwdBlist.getFldName().equals(info.getPassword())) {
                    updatePassReponse.setStatus(false);
                    updatePassReponse.setMessage("Cannot be in black list password");
                    return updatePassReponse;
                }
            }

        }
        //save pass
        employee.setFldEmpPwd(new BCryptPasswordEncoder().encode(info.getPassword()));
        employeeRepository.save(employee);
        updatePassReponse.setStatus(true);
        return updatePassReponse;
    }
}
