package com.example.pmswebportal.services.implement;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.example.pmswebportal.services.LoginAttemptService;
import com.example.pmswebportal.services.SecurityPolicyService;

/**
 * LoginAttempt Service implement of LoginAttempt Service.).
 */
@Primary
@Service
public class LoginAttemptServiceImpl implements LoginAttemptService {
    private final ConcurrentHashMap<String, LoginAttempts> userCache;

    private Long maxLoginFailedAttempts = 5L;

    private Long lockTimeDurationMinus = 10L;

    @Autowired
    private SecurityPolicyService securityPolicyService;

    public LoginAttemptServiceImpl() {
        this.userCache = new ConcurrentHashMap<>();
    }

    @Override
    public void addUserToCache(String username) {
        userCache.putIfAbsent(username, new LoginAttempts());
    }

    @Override
    public boolean isExistsCache(String username) {
        return userCache.containsKey(username);
    }

    @Override
    public boolean loginFailed(String username) {
        if (isBlocked(username)) {
            return true;
        }

        LoginAttempts loginAttempt = getLoginAttempts(username);
        loginAttempt.addAttempt();
        userCache.putIfAbsent(username, loginAttempt);

        if (hasReachedLoginAttemptLimit(loginAttempt)) {
            LocalDateTime unblockTime = LocalDateTime.now(Clock.systemDefaultZone()).plusMinutes(lockTimeDurationMinus);
            loginAttempt.setAttempts(0);
            loginAttempt.setUnblockTime(unblockTime);
            return true;
        }

        return false;
    }

    @Override
    public void loginSucceeded(String username) {
        LoginAttempts loginAttempt = getLoginAttempts(username);
        loginAttempt.setAttempts(0);
        loginAttempt.setUnblockTime(null);
    }

    @Override
    public synchronized boolean isBlocked(String username) {
        LoginAttempts loginAttempt = getLoginAttempts(username);
        if (loginAttempt.isBlocked(Clock.systemDefaultZone())) {
            return true;
        } else {
            loginAttempt.unblock();
            return false;
        }
    }

    @Override
    @Scheduled(cron = "0 0 0 * * *")
    public synchronized void reset() {
        userCache.clear();
    }

    private LoginAttempts getLoginAttempts(String username) {
        return userCache.getOrDefault(username, new LoginAttempts());
    }

    private boolean hasReachedLoginAttemptLimit(LoginAttempts loginAttempt) {
        
        return loginAttempt.getAttempts() >= Long.parseLong(securityPolicyService.getSecurityPolicy().getOrDefault("fldSecMLA", maxLoginFailedAttempts).toString());
    }

}
