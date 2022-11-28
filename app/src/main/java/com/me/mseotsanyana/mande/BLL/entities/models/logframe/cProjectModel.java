package com.me.mseotsanyana.mande.BLL.entities.models.logframe;

import com.me.mseotsanyana.mande.BLL.entities.models.common.cRecordPermissionModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class cProjectModel extends cRecordPermissionModel {
    private String projectServerID;
    private String parentServerID;
    private List<String> children;

    // meta attributes
    private String code;
    private String name;
    private String description;
    private String status;
    private String location;
    private Date startDate;
    private Date endDate;

    public cProjectModel(){
        super();
        parentServerID = null;
        children = new ArrayList<>();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getProjectServerID() {
        return projectServerID;
    }

    public void setProjectServerID(String projectServerID) {
        this.projectServerID = projectServerID;
    }

    public String getParentServerID() {
        return parentServerID;
    }

    public void setParentServerID(String parentServerID) {
        this.parentServerID = parentServerID;
    }

    public List<String> getChildren() {
        return children;
    }

    public void setChildren(List<String> children) {
        this.children = children;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

}
