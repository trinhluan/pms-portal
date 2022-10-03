package com.example.pmswebportal.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import com.example.pmswebportal.model.idclass.EmpServiceTypeId;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Entity
@IdClass(EmpServiceTypeId.class)
@Getter
@Setter
@Table(name = "tblempservicetype")
public class EmpServiceType implements Serializable {

    /** */
    @Id
    @Column(name = "fldEmpNo", length = 20, nullable = false)
    private String fldEmpNo;

    /** */
    @Id
    @Column(name = "fldServiceType", length = 50, nullable = false)
    private String fldServiceType;

}