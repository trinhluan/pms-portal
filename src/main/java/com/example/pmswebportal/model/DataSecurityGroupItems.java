package com.example.pmswebportal.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import com.example.pmswebportal.model.idclass.DataSecurityGroupItemsId;

import java.sql.Timestamp;
import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Entity
@IdClass(DataSecurityGroupItemsId.class)
@Getter
@Setter
@Table(name = "tbldatasecuritygroupitems")
public class DataSecurityGroupItems implements Serializable {

    /** */
    @Id
    @Column(name = "fldGroupCode", length = 50, nullable = false)
    private String fldGroupCode;

    /** */
    @Id
    @Column(name = "fldSiteBuilding", length = 50, nullable = false)
    private String fldSiteBuilding;

    /** */
    @Column(name = "fldWorkflowTypeT", length = 10, nullable = false)
    private String fldWorkflowTypeT;

    /** */
    @Column(name = "fldWorkflowTypeNT", length = 10, nullable = false)
    private String fldWorkflowTypeNT;

    /** */
    @Column(name = "fldEmpNameC", length = 50, nullable = false)
    private String fldEmpNameC;

    /** */
    @Column(name = "fldFirstCDate", nullable = false)
    private Timestamp fldFirstCDate;

    /** */
    @Column(name = "fldEmpNameM", length = 50, nullable = false)
    private String fldEmpNameM;

    /** */
    @Column(name = "fldLastMDate", nullable = false)
    private Timestamp fldLastMDate;

    /** */
    @Column(name = "fldComID", length = 100, nullable = false)
    private String fldComID;

}