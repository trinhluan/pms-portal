package com.example.pmswebportal.services.implement;

import java.time.Clock;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class LoginAttempts {

    private int attempts = 0;

    private LocalDateTime unblockTime = null;

    public boolean isBlocked(Clock clock) {
        return unblockTime != null && LocalDateTime.now(clock).isBefore(unblockTime);
    }

    public void unblock() {
        this.unblockTime = null;
    }

    public void addAttempt() {
        this.attempts += 1;
    }

}
