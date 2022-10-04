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
import com.example.pmswebportal.dto.EmployeeDetailReponse;
import com.example.pmswebportal.dto.EmployeeProfile;
import com.example.pmswebportal.dto.MyprofileReponse;
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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
        if (fldAllowLogIn != null && fldAllowLogIn != "" && !"All".equals(fldAllowLogIn)) {
            emplSearch.setFldAllowLogIn(fldAllowLogIn);
        }
        if (fldPreferredLang != null && fldPreferredLang != "" && !"All".equals(fldPreferredLang)) {
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
    public EmployeeDetailReponse registerEmployee(EmployeeDetail employeeDto) {
        long number = employeeRepository.count() + 1;
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDto, employee);

        EmployeeDetailReponse employeeDetailReponse = new EmployeeDetailReponse();
        if (employeeDto.getEmpPwd() != null && employeeDto.getEmpPwd() != "") {
            HashMap<String, Object> policy = securityPolicyService.getSecurityPolicy();
            if (policy.get("fldSecPBL").toString().toUpperCase().equals("YES")) {
                List<SysSecpwdBlist> allBlist = sysSecpwdBlistService.getAllBlackListPass();
                for (SysSecpwdBlist sysSecpwdBlist : allBlist) {
                    if (sysSecpwdBlist.getFldName().equals(employeeDto.getEmpPwd())) {
                        employeeDetailReponse.setStatus(false);
                        employeeDetailReponse.setMessage("Cannot be in blacklist password");
                        return employeeDetailReponse;
                    }
                }

            }

            employee.setFldEmpPwd(new BCryptPasswordEncoder().encode(employeeDto.getEmpPwd()));
        } else {
            employee.setFldEmpPwd("");
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomAccountDetail account = (CustomAccountDetail) authentication.getPrincipal();
        employee.setFldEmpNameC(account.getUsername());
        employee.setFldFirstCDate(new Timestamp(new Date().getTime()));
        employee.setFldEmpNameM(account.getUsername());
        employee.setFldLastMDate(new Timestamp(new Date().getTime()));
        employee.setFldEmpNo(new StringBuilder("user").append(String.valueOf(number)).toString());

        // set empty
        employee.setFldEmpMobile("");
        employee.setFldIsPatrolStaff("");
        employee.setFldRespPersonTech("");
        employee.setFldRespPersonNonTech("");
        employee.setFldFuncSecurityGroup("");
        employee.setFldDataSecurityGroup("");
        employee.setFldHomePage("");
        employee.setFldCompanyID(-1);
        employee.setFldRole("");
        employee.setFldVarchar01("");
        employee.setFldVarchar02("");
        employee.setFldVarchar03("");
        employee.setFldVarchar04("");
        employee.setFldVarchar05("");
        employee.setFldLongtext01("");
        employee.setFldLongtext02("");
        employee.setFldComID("");

        employeeRepository.save(employee);

        // update empl service type
        processEmplServicesType(employeeDto);

        employeeDetailReponse.setStatus(true);
        return employeeDetailReponse;

    }

    /**
     * Update employee
     * 
     * @param employee
     */
    @Override
    @Transactional
    public EmployeeDetailReponse updateEmployee(EmployeeDetail employeeDto) {
        EmployeeDetailReponse employeeDetailReponse = new EmployeeDetailReponse();
        employeeDetailReponse.setStatus(true);

        Optional<Employee> employee = employeeRepository.findByFldEmpNo(employeeDto.getFldEmpNo());
        if (employee.isPresent()) {
            Employee employee2 = employee.get();

            // check policy
            HashMap<String, Object> policy = securityPolicyService.getSecurityPolicy();
            if (employeeDto.getEmpPwd() != null && !employeeDto.getEmpPwd().equals("")) {
                if (policy.get("fldSecPCP").toString().toUpperCase().equals("YES")
                        && (int) policy.get("fldPwdMPRT") == 1) {
                    boolean check = new BCryptPasswordEncoder().matches(employeeDto.getEmpPwd(),
                            employee.get().getFldEmpPwd());
                    if (check) {
                        employeeDetailReponse.setStatus(false);
                        employeeDetailReponse.setMessage("Do not reuse your old password");
                        return employeeDetailReponse;
                    }
                }

                if (policy.get("fldSecPBL").toString().toUpperCase().equals("YES")) {
                    List<SysSecpwdBlist> allBlist = sysSecpwdBlistService.getAllBlackListPass();
                    for (SysSecpwdBlist sysSecpwdBlist : allBlist) {
                        if (sysSecpwdBlist.getFldName().equals(employeeDto.getEmpPwd())) {
                            employeeDetailReponse.setStatus(false);
                            employeeDetailReponse.setMessage("Cannot be in blacklist password");
                            return employeeDetailReponse;
                        }
                    }

                }
            }

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
        return employeeDetailReponse;
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
    @Override
    @Transactional
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
        ExecutorService es = Executors.newSingleThreadExecutor();
        Runnable runnable = new Runnable() {
            @Override            
            public void run() {                
                // send mail
                EmailDetail emailDetail = new EmailDetail();
                emailDetail.setMsgBody(
                        String.format(AppConstants.OTP_MSG_BODY, String.valueOf(otp), String.valueOf(countdownOtp)));
                emailDetail.setRecipient(employee.getFldEmpEmail());
                emailDetail.setSubject(AppConstants.OTP_SUBJECT);
                mailUtil.sendSimpleMail(emailDetail);
            }
        };
        es.execute(runnable);
        es.shutdown();
        return true;
    }

    /**
     * Update employee
     * 
     * @param employee
     */
    @Override
    public MyprofileReponse updateProfile(EmployeeProfile employeeProfile) {
        Optional<Employee> employee = employeeRepository.findByFldEmpNo(employeeProfile.getFldEmpNo());
        MyprofileReponse myProfileReponse = new MyprofileReponse();
        if (employee.isPresent()) {
            if (employeeProfile.getEmpOldPwd() != null) {
                boolean checkOldpwd = new BCryptPasswordEncoder().matches(employeeProfile.getEmpOldPwd(),
                        employee.get().getFldEmpPwd());
                if (!checkOldpwd) {
                    myProfileReponse.setStatus(false);
                    myProfileReponse.setMessage("Old password don't match");
                    return myProfileReponse;
                }
            }

            if (employeeProfile.getEmpNewPwd() != null) {
                // check pass new không trùng password mới
                HashMap<String, Object> policy = securityPolicyService.getSecurityPolicy();
                if (policy.get("fldSecPCP").toString().toUpperCase().equals("YES")
                        && (int) policy.get("fldPwdMPRT") == 1) {
                    boolean check = new BCryptPasswordEncoder().matches(employeeProfile.getEmpNewPwd(),
                            employee.get().getFldEmpPwd());
                    if (check) {
                        myProfileReponse.setStatus(false);
                        myProfileReponse.setMessage("Do not reuse your old password");
                        return myProfileReponse;
                    }
                }

                if (policy.get("fldSecPBL").toString().toUpperCase().equals("YES")) {
                    List<SysSecpwdBlist> allBlist = sysSecpwdBlistService.getAllBlackListPass();
                    for (SysSecpwdBlist sysSecpwdBlist : allBlist) {
                        if (sysSecpwdBlist.getFldName().equals(employeeProfile.getEmpNewPwd())) {
                            myProfileReponse.setStatus(false);
                            myProfileReponse.setMessage("Cannot be in blacklist password");
                            return myProfileReponse;
                        }
                    }

                }
            }

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
            myProfileReponse.setStatus(true);
        }
        return myProfileReponse;
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
            updatePassReponse.setMessage("Expired OTP");
            return updatePassReponse;
        }

        if (!sysOtp.getFldOTP().equals(info.getOtp())) {
            updatePassReponse.setStatus(false);
            updatePassReponse.setMessage("OTP is incorrect");
            return updatePassReponse;
        }

        HashMap<String, Object> policy = securityPolicyService.getSecurityPolicy();
        if (policy.get("fldSecPCP").toString().toUpperCase().equals("YES") && (int) policy.get("fldPwdMPRT") == 1) {
            boolean check = new BCryptPasswordEncoder().matches(info.getPassword(), employee.getFldEmpPwd());
            if (check) {
                updatePassReponse.setStatus(false);
                updatePassReponse.setMessage("Do not reuse your old password");
                return updatePassReponse;
            }
        }
        if (policy.get("fldSecPBL").toString().toUpperCase().equals("YES")) {
            List<SysSecpwdBlist> allBlist = sysSecpwdBlistService.getAllBlackListPass();
            for (SysSecpwdBlist sysSecpwdBlist : allBlist) {
                if (sysSecpwdBlist.getFldName().equals(info.getPassword())) {
                    updatePassReponse.setStatus(false);
                    updatePassReponse.setMessage("Cannot be in blacklist password");
                    return updatePassReponse;
                }
            }

        }
        // save pass
        employee.setFldEmpPwd(new BCryptPasswordEncoder().encode(info.getPassword()));
        employeeRepository.save(employee);
        updatePassReponse.setStatus(true);
        return updatePassReponse;
    }
}
