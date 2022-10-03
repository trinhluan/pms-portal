package com.example.pmswebportal.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import java.sql.Timestamp;
import java.util.HashMap;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "tblsyssecurity")
public class SysSecurity {

    /** */
    @Column(name = "fldSecMLA", nullable = false)
    private Integer fldSecMLA;

    /** */
    @Column(name = "fldSecMPV", nullable = false)
    private Integer fldSecMPV;

    /** */
    @Column(name = "fldSecMCP", length = 10, nullable = false)
    private String fldSecMCP;

    /** */
    @Column(name = "fldSecPCP", length = 10, nullable = false)
    private String fldSecPCP;

    /** */
    @Column(name = "fldSecPBL", length = 10, nullable = false)
    private String fldSecPBL;

    /** */
    @Column(name = "fldPwdMinLen", nullable = false)
    private Integer fldPwdMinLen;

    /** */
    @Column(name = "fldPwdConUCC", length = 10, nullable = false)
    private String fldPwdConUCC;

    /** */
    @Column(name = "fldPwdConLCC", length = 10, nullable = false)
    private String fldPwdConLCC;

    /** */
    @Column(name = "fldPwdConNC", length = 10, nullable = false)
    private String fldPwdConNC;

    /** */
    @Column(name = "fldPwdConSC", length = 10, nullable = false)
    private String fldPwdConSC;

    /** */
    @Column(name = "fldPwdNotUN", length = 10, nullable = false)
    private String fldPwdNotUN;

    /** */
    @Column(name = "fldPwdMPRT", nullable = false)
    private Integer fldPwdMPRT;

    /** */
    @Id
    @Column(name = "fldRole", length = 50, nullable = false)
    private String fldRole;

    /** */
    @Column(name = "fldRemarks", nullable = false)
    private String fldRemarks;

    /** */
    @Column(name = "fldEmpNameM", length = 50, nullable = false)
    private String fldEmpNameM;

    /** */
    @Column(name = "fldLastMDate", nullable = false)
    private Timestamp fldLastMDate;

    /** */
    @Column(name = "fldComID", length = 100, nullable = false)
    private String fldComID;

    public HashMap<String, Object> toHasMap() {
        HashMap<String, Object> tmp = new HashMap<String, Object>();
        tmp.put("fldSecMLA", this.fldSecMLA);
        tmp.put("fldSecMPV", fldSecMPV);
        tmp.put("fldSecMCP", this.fldSecMCP);
        tmp.put("fldSecPCP", this.fldSecPCP);
        tmp.put("fldSecPBL", this.fldSecPBL);
        tmp.put("fldPwdMinLen", fldPwdMinLen);
        tmp.put("fldPwdConUCC", this.fldPwdConUCC);
        tmp.put("fldPwdConLCC", this.fldPwdConLCC);
        tmp.put("fldPwdConNC", this.fldPwdConNC);
        tmp.put("fldPwdConSC", this.fldPwdConSC);
        tmp.put("fldPwdNotUN", this.fldPwdNotUN);
        tmp.put("fldPwdMPRT", fldPwdMPRT);
        tmp.put("fldRole", this.fldRole);
        tmp.put("fldRemarks", this.fldRemarks);
        tmp.put("fldEmpNameM", this.fldEmpNameM);
        tmp.put("fldLastMDate", this.fldLastMDate);
        tmp.put("fldComID", this.fldComID);
        return tmp;
    }

}