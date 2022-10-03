package com.example.pmswebportal.dto;

import javax.persistence.Entity;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class EmailDetail {

    /**
     * recipient
     */
    private String recipient;

    /**
     * subject
     */
    private String subject;

    /**
     * msgBody
     */
    private String msgBody;

}