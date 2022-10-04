package com.example.pmswebportal.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.sql.Connection;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import javax.persistence.TemporalType;

import com.example.pmswebportal.security.CustomAccountDetail;
import com.example.pmswebportal.services.LoginAttemptService;
import com.example.pmswebportal.utilities.HttpReqRespUtils;

@Component
public class AuthenticationSuccessListener implements ApplicationListener<AuthenticationSuccessEvent> {

    @Autowired
    private LoginAttemptService loginAttemptService;

    @PersistenceContext
    EntityManager entityManager;

    @Transactional
    @Override
    public void onApplicationEvent(AuthenticationSuccessEvent event) {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes instanceof ServletRequestAttributes) {
            CustomAccountDetail account = (CustomAccountDetail) event.getAuthentication().getPrincipal();
            loginAttemptService.loginSucceeded(account.getUsername());
            System.out.println(entityManager);
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            String tableName = String.format("tblsysaccesslog%s", String.valueOf(LocalDate.now().getYear()));
            entityManager.createNativeQuery(String.format(
                    "INSERT INTO %s (fldEmpNo, fldEmpName, fldHost, fldAction, fldAuditLog, fldDateTime) values(?,?,?,?,?,?)",
                    tableName))
                    .setParameter(1, account.getUsername())
                    .setParameter(2, account.getEmployee().getFldEmpName())
                    .setParameter(3, HttpReqRespUtils.getClientIpAddress())
                    .setParameter(4, "Login")
                    .setParameter(5, dateTimeFormatter.format(LocalDateTime.now()))
                    .setParameter(6, Timestamp.valueOf(LocalDateTime.now()), TemporalType.TIMESTAMP)
                    .executeUpdate();
        }
    }
}