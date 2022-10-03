
package com.example.pmswebportal.security;

import javax.transaction.Transactional;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.pmswebportal.model.Employee;
import com.example.pmswebportal.services.EmployeeService;


@Service
public class AccountDetailsService implements UserDetailsService {


    @Autowired
    private EmployeeService employeeService;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
        Employee employee = employeeService.checkAuthenticated(loginId);
        if(employee == null) {
            throw new UsernameNotFoundException("");
        }
        return new CustomAccountDetail(employee);
    }

}
