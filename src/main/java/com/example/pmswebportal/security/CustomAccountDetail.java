package com.example.pmswebportal.security;

import java.util.Collection;
import java.util.Collections;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.pmswebportal.model.Employee;

import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class CustomAccountDetail implements UserDetails {

    private Employee employee;

    public CustomAccountDetail(Employee employee) {
        this.employee = employee;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"));

    }

    @Override
    public String getPassword() {
        return employee.getFldEmpPwd();
    }

    @Override
    public String getUsername() {
        return employee.getFldEmpLoginID();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public Employee getEmployee() {
        return this.employee;
    }
}
