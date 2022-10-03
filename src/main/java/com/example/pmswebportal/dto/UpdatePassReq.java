
package com.example.pmswebportal.dto;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UpdatePassReq {

    private String loginId;

    private String password;
    
    private String otp;
}
