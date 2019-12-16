package com.bomwebportal.mob.ccs.dto.ui;

import java.io.Serializable;
import java.util.Date;

public class HottestModelManagementUI implements Serializable{
        
    /**
     * 
     */
    private static final long serialVersionUID = -5313846465175113636L;
    private String type;
    private String itemCode;
    private String model;
    private Date startDate;
    private String startDateStr;    
    private Date endDate;
    private String endDateStr;
    private String user;
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getItemCode() {
        return itemCode;
    }
    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }
    public String getModel() {
        return model;
    }
    public void setModel(String model) {
        this.model = model;
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
    public String getUser() {
        return user;
    }
    public void setUser(String user) {
        this.user = user;
    }
    public String getStartDateStr() {
        return startDateStr;
    }
    public void setStartDateStr(String startDateStr) {
        this.startDateStr = startDateStr;
    }
    public String getEndDateStr() {
        return endDateStr;
    }
    public void setEndDateStr(String endDateStr) {
        this.endDateStr = endDateStr;
    }
    
}
