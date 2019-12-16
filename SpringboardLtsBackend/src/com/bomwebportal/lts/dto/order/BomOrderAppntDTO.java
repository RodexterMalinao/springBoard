package com.bomwebportal.lts.dto.order;

import java.io.Serializable;
import java.util.Date;
import java.text.SimpleDateFormat;

public class BomOrderAppntDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8605497537470761496L;

	
	private String ocId;
	private String dtlId;
	private String toFromSide;
	private Date   appntStartTime;
	private Date   appntEndTime;
	private String appntType;
	private String delayReaCd;
	private String delayReaDesc;
	private Date   lastUpdDate;
	
	private static final SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyyMMddHHmmss");

	public String getOcId() {
		return ocId;
	}
	public void setOcId(String ocId) {
		this.ocId = ocId;
	}
	public String getDtlId() {
		return dtlId;
	}
	public void setDtlId(String dtlId) {
		this.dtlId = dtlId;
	}
	public String getToFromSide() {
		return toFromSide;
	}
	public void setToFromSide(String toFromSide) {
		this.toFromSide = toFromSide;
	}
	public String getAppntType() {
		return appntType;
	}
	public void setAppntType(String appntType) {
		this.appntType = appntType;
	}
	/**
	 * @return the delayReaCd
	 */
	public String getDelayReaCd() {
		return delayReaCd;
	}
	/**
	 * @param delayReaCd the delayReaCd to set
	 */
	public void setDelayReaCd(String delayReaCd) {
		this.delayReaCd = delayReaCd;
	}
	/**
	 * @return the delayReaDesc
	 */
	public String getDelayReaDesc() {
		return delayReaDesc;
	}
	/**
	 * @param delayReaDesc the delayReaDesc to set
	 */
	public void setDelayReaDesc(String delayReaDesc) {
		this.delayReaDesc = delayReaDesc;
	}
	/**
	 * @return the appntStartTime
	 */
	public Date getAppntStartTime() {
		return appntStartTime;
	}
	
	/**
	 * @return the appntStartTime as String
	 */
	public String getAppntStartTimeAsStr() {
		try {
			return dateFormatter.format(appntStartTime);
		} catch (Exception e) {
			return null;
		}
	}	
	
	/**
	 * @param appntStartTime the appntStartTime to set
	 */
	public void setAppntStartTime(Date appntStartTime) {
		this.appntStartTime = appntStartTime;
	}
	/**
	 * @return the appntEndTime
	 */
	public Date getAppntEndTime() {
		return appntEndTime;
	}
	
	/**
	 * @return the appntEndTime as String
	 */
	public String getAppntEndTimeAsStr() {
		try {
			return dateFormatter.format(appntEndTime);
		} catch (Exception e) {
			return null;
		}
	}	
	
	/**
	 * @param appntEndTime the appntEndTime to set
	 */
	public void setAppntEndTime(Date appntEndTime) {
		this.appntEndTime = appntEndTime;
	}
	
	/**
	 * @return the lastUpdDate
	 */
	public Date getLastUpdDate() {
		return lastUpdDate;
	}
	
	/**
	 * @return the lastUpdDate as String
	 */
	public String getLastUpdDateAsStr() {
		return dateFormatter.format(lastUpdDate);
	}	
	
	/**
	 * @param lastUpdDate the lastUpdDate to set
	 */
	public void setLastUpdDate(Date lastUpdDate) {
		this.lastUpdDate = lastUpdDate;
	}
	
}
