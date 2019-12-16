package com.bomwebportal.mob.ccs.dto;

import java.io.Serializable;
import java.util.Date;

public class MrtInventoryDTO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -4790960409345461605L;
    private String numType;
    private String srvNumType;
    private String msisdn;
    private String msisdnlvl;
    private Integer msisdnStatus;
    private String cityCd;
    private String channelCd;
    private Date stockInDate;
    private String createdBy;
    private Date createdDate;
    private String lastUpdBy;
    private Date lastUpdDate;
    private String rowId;
    private String reserveId;
    private String resOperId;
    private String approvalSerial;
    private String requestId;
    
    public String getNumType() {
		return numType;
	}

	public void setNumType(String numType) {
		this.numType = numType;
	}

	public String getSrvNumType() {
	return srvNumType;
    }

    public void setSrvNumType(String srvNumType) {
	this.srvNumType = srvNumType;
    }

    public String getMsisdn() {
	return msisdn;
    }

    public void setMsisdn(String msisdn) {
	this.msisdn = msisdn;
    }

    public String getMsisdnlvl() {
	return msisdnlvl;
    }

    public void setMsisdnlvl(String msisdnlvl) {
	this.msisdnlvl = msisdnlvl;
    }

    public Integer getMsisdnStatus() {
	return msisdnStatus;
    }

    public void setMsisdnStatus(Integer msisdnStatus) {
	this.msisdnStatus = msisdnStatus;
    }

    public String getCityCd() {
	return cityCd;
    }

    public void setCityCd(String cityCd) {
	this.cityCd = cityCd;
    }

    public String getChannelCd() {
	return channelCd;
    }

    public void setChannelCd(String channelCd) {
	this.channelCd = channelCd;
    }

    public Date getStockInDate() {
	return stockInDate;
    }

    public void setStockInDate(Date stockInDate) {
	this.stockInDate = stockInDate;
    }

    public String getCreatedBy() {
	return createdBy;
    }

    public void setCreatedBy(String createdBy) {
	this.createdBy = createdBy;
    }

    public Date getCreatedDate() {
	return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
	this.createdDate = createdDate;
    }

    public String getLastUpdBy() {
	return lastUpdBy;
    }

    public void setLastUpdBy(String lastUpdBy) {
	this.lastUpdBy = lastUpdBy;
    }

    public Date getLastUpdDate() {
	return lastUpdDate;
    }

    public void setLastUpdDate(Date lastUpdDate) {
	this.lastUpdDate = lastUpdDate;
    }

	public String getRowId() {
		return rowId;
	}

	public void setRowId(String rowId) {
		this.rowId = rowId;
	}

	public String getReserveId() {
		return reserveId;
	}

	public void setReserveId(String reserveId) {
		this.reserveId = reserveId;
	}

	public String getResOperId() {
		return resOperId;
	}

	public void setResOperId(String resOperId) {
		this.resOperId = resOperId;
	}

	public String getApprovalSerial() {
		return approvalSerial;
	}

	public void setApprovalSerial(String approvalSerial) {
		this.approvalSerial = approvalSerial;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	
}
