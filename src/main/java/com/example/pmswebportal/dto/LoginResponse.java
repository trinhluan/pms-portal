
package com.example.pmswebportal.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponse {

    private String accessToken;

    private EmployeeResponse employee;

}
