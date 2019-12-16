package com.bomwebportal.dto.report;

import java.io.InputStream;
import java.util.ArrayList;

import com.bomwebportal.dto.VasDetailDTO;
import com.bomwebportal.dto.report.ReportDTO;

public class RptRetCosDTO extends ReportDTO{
    
    /**
     * 
     */
    private static final long serialVersionUID = -8980672078304167391L;

    public static final String JASPER_TEMPLATE = "MobRetCos";
    
    private String orderId;
    private String idDocType;
    private String idDocNum;
    private String customerName;
    private String msisdn;
    private String accNo;
    private String appInDateStr;
    private String staffcodeName;
    private String customerNameLabelDisp;
    private String customerCopyInd;
    private String serviceReqDate;
    private InputStream custSignature; 
    private String mthRate;
    private ArrayList<VasDetailDTO> rpDescList;
    
    
	public String getOrderId() {
        return orderId;
    }
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
    public String getAccNo() {
		return accNo;
	}
	public void setAccNo(String accNo) {
		this.accNo = accNo;
	}
	public String getIdDocType() {
        return idDocType;
    }
    public void setIdDocType(String idDocType) {
        this.idDocType = idDocType;
    }    
  
    public String getCustomerName() {
        return customerName;
    }
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }   
    public String getMsisdn() {
		return msisdn;
	}
	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}
	public String getAppInDateStr() {
        return appInDateStr;
    }
    public void setAppInDateStr(String appInDateStr) {
        this.appInDateStr = appInDateStr;
    }
    public String getStaffcodeName() {
        return staffcodeName;
    }
    public void setStaffcodeName(String staffcodeName) {
        this.staffcodeName = staffcodeName;
    }
    public String getCustomerNameLabelDisp() {
        return customerNameLabelDisp;
    }
    public void setCustomerNameLabelDisp(String customerNameLabelDisp) {
        this.customerNameLabelDisp = customerNameLabelDisp;
    }
    public String getCustomerCopyInd() {
        return customerCopyInd;
    }
    public void setCustomerCopyInd(String customerCopyInd) {
        this.customerCopyInd = customerCopyInd;
    }
    public String getIdDocNum() {
        return idDocNum;
    }
    public void setIdDocNum(String idDocNum) {
        this.idDocNum = idDocNum;
    }
   
	/**
	 * @return the custSignature
	 */
	public InputStream getCustSignature() {
		return custSignature;
	}
	/**
	 * @param custSignature the custSignature to set
	 */
	public void setCustSignature(InputStream custSignature) {
		this.custSignature = custSignature;
	}
	public String getServiceReqDate() {
		return serviceReqDate;
	}
	public void setServiceReqDate(String serviceReqDate) {
		this.serviceReqDate = serviceReqDate;
	}
	public String getMthRate() {
		return mthRate;
	}
	public void setMthRate(String mthRate) {
		this.mthRate = mthRate;
	}
	public ArrayList<VasDetailDTO> getRpDescList() {
		return rpDescList;
	}
	public void setRpDescList(ArrayList<VasDetailDTO> rpDescList) {
		this.rpDescList = rpDescList;
	}

    
}
