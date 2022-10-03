package com.example.pmswebportal.services;


public interface LoginAttemptService {

    void addUserToCache(String username);

    boolean isExistsCache(String username);

    boolean loginFailed(String username);

    void loginSucceeded(String username);

    boolean isBlocked(String username);

    void reset();
}
