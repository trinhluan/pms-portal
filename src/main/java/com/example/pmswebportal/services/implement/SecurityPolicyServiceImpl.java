package com.example.pmswebportal.services.implement;

import java.util.HashMap;
import com.example.pmswebportal.model.SysSecurity;
import com.example.pmswebportal.repositories.SysSecurityRepository;
import com.example.pmswebportal.services.SecurityPolicyService;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SecurityPolicyServiceImpl  implements SecurityPolicyService {
    
    private HashMap<String, Object> pwdPolicy;

    @Autowired
    private SysSecurityRepository securityRepository;
    
    public SecurityPolicyServiceImpl() {
        pwdPolicy = new HashMap<>();
        System.out.println("vao day");
    }

    @Override
    public HashMap<String, Object> getSecurityPolicy() {
        if(pwdPolicy.size() == 0) {
            SysSecurity sysSecurity = securityRepository.findTopBy();
            if(sysSecurity != null) {
                pwdPolicy = sysSecurity.toHasMap();
            }
        }
        return pwdPolicy;
    }
}
