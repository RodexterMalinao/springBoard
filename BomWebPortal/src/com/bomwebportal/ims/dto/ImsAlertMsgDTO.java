package com.bomwebportal.ims.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bomwebportal.dto.BomSalesUserDTO;

public class ImsAlertMsgDTO implements Serializable{
	

	protected final Log logger = LogFactory.getLog(getClass());
	/**
	 * created by steven
	 */
	private static final long serialVersionUID = 3101488332783993790L;
	private String orderId;
	private String salesTeam;
	private String alertStatus;
	private String ocid;
	private String custName;
	private String serviceNum;
	private String loginId;
	private String appDate;
	private String orderStatus;
	private String error;
	private String salesCd;
	private String salesChannel;
	private String createBy;
	private String recall;
	private String reasonCD;
	private String isRetention;
	private String isTermination;
	private String imsOrderType;
//added for QC
	private String signoffDate;
	private String serviceReqDate;
	private String sourceCD;
	private String qcRemarks;
	private String waiveQC;
	private String waiveQCapproved;
	private String amendment;
	private String assignDate;
	private String sysF;
	private String idDocNum;
	private String paymentMtd;
	private String is3rdParty;
	private String housingType;
	private String staffType;
	private String assignee;
	private String amendmentHistory;
	private Date lastUpdDate;
	private String maskName;
	private String orderType;
	
	private Date createDate;
	
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	public String getSysF() {
		return sysF;
	}
	public void setSysF(String sysF) {
		this.sysF = sysF;
	}
	public String getQcStatus() {
		return qcStatus;
	}
	public void setQcStatus(String qcStatus) {
		this.qcStatus = qcStatus;
	}
	private String qcStatus;

	
	public String getWaiveQCapproved() {
		return waiveQCapproved;
	}
	public void setWaiveQCapproved(String waiveQCapproved) {
		this.waiveQCapproved = waiveQCapproved;
	}
	public void setImsOrderType(String imsOrderType) {
		this.imsOrderType = imsOrderType;
	}
	public String getImsOrderType() {
		return imsOrderType;
	}
	public void setIsRetention(String isRetention) {
		this.isRetention = isRetention;
	}
	public String getIsRetention() {
		return isRetention;
	}
	public String getSalesTeam() {
		return salesTeam;
	}
	public void setSalesTeam(String salesTeam) {
		this.salesTeam = salesTeam;
	}
	public String getSalesCd() {
		return salesCd;
	}
	public void setSalesCd(String salesCd) {
		this.salesCd = salesCd;
	}
	public String getSalesChannel() {
		return salesChannel;
	}
	public void setSalesChannel(String salesChannel) {
		this.salesChannel = salesChannel;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getAlertStatus() {
		return alertStatus;
	}
	public void setAlertStatus(String alertStatus) {
		this.alertStatus = alertStatus;
	}
	public String getOcid() {
		return ocid;
	}
	public void setOcid(String ocid) {
		this.ocid = ocid;
	}
	public String getCustName() {
		return custName;
	}
	public void setCustName(String custName) {
		this.custName = custName;
	}
	public String getServiceNum() {
		return serviceNum;
	}
	public void setServiceNum(String serviceNum) {
		this.serviceNum = serviceNum;
	}
	public String getLoginId() {
		return loginId;
	}
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}
	public String getAppDate() {
		return appDate;
	}
	public void setAppDate(String appDate) {
		this.appDate = appDate;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	public void checkRecallAmend(List<String> roleCodeList, BomSalesUserDTO userDto, List<String> channelCodeListOfChannelId) {
		checkRecallAmend(roleCodeList, userDto, channelCodeListOfChannelId,null);
	}//ims direct sales Celia 20150318
	public void checkRecallAmend(List<String> roleCodeList, BomSalesUserDTO userDto, List<String> channelCodeListOfChannelId,List<String> teamCodeListOfCentreCd) {
//		if(orderStatus.equalsIgnoreCase("02")||
//				orderStatus.equalsIgnoreCase("10")||
//				orderStatus.equalsIgnoreCase("31")||
//				orderStatus.equalsIgnoreCase("32")){
//			//do nth
//		}else{
//			return;
//		}
		
		if(this.getCustName()!=null && this.getCustName().length()>20){
			this.setCustName(this.getCustName().substring(0, 18)+"...");
		}
		
		if(this.orderId!=null && this.orderId.length()>5){
//			logger.info("this.orderId:"+this.orderId);
//			logger.info("this.orderId.substring(5,6):"+this.orderId.substring(5,6));
			if("P_R_T".equals(this.imsOrderType)||"PCD_V".equals(this.imsOrderType)||"PCD_R".equals(this.imsOrderType)||"R".equals(this.orderId.substring(5,6))){
				this.setIsRetention("Y");
				this.setIsTermination("N");
			}else if("PCD_T".equals(this.imsOrderType)||"T".equals(this.orderId.substring(5, 6))){
				this.setIsRetention("N");
				this.setIsTermination("Y");
			}else{
				this.setIsRetention("N");
				this.setIsTermination("N");
			}
		}
		Boolean needCentreCD = false; 
		Boolean needChannelID = false;
		Boolean needChannelCD = false;
		Boolean needTeam = false;
		Boolean needSales = false;
		
		for(String code : roleCodeList){
			if(code.equalsIgnoreCase("SALES_CD")){
				needSales=true;
			}
			if(code.equalsIgnoreCase("TEAM_CD")){
				needTeam=true;
			}
			if(code.equalsIgnoreCase("CHANNEL_CD")){
				needChannelCD=true;
			}
			if(code.equalsIgnoreCase("CHANNEL_ID")){
				needChannelID=true;
			}
			if(code.equalsIgnoreCase("CENTRE_CD")){
				needCentreCD=true;
			}
		}
		
		if(this.salesChannel==null){
			this.salesChannel="";
		}
		if(this.salesCd==null){
			this.salesCd="";
		}
		if(this.salesTeam==null){
			this.salesTeam="";
		}
		if(this.createBy==null){
			this.createBy="";
		}
		
		if(this.createBy.equalsIgnoreCase(userDto.getUsername())){
				this.setRecall("Y");
//				logger.info("Recall / amend, case 5 createBy");
				return;
		}
		if(needSales){
			if(this.salesCd.equalsIgnoreCase(userDto.getUsername()) || userDto.getUsername().contains(this.salesCd)){
				this.setRecall("Y");
//				logger.info("Recall / amend, case 4 salesCd");
				return;
			}
		}
		if(needTeam){
			if(this.salesTeam.equalsIgnoreCase(userDto.getShopCd())){
				this.setRecall("Y");
//				logger.info("Recall / amend, case 3 salesTeam");
				return;
			}
		}
		if(needChannelCD){
			if(this.salesChannel.equalsIgnoreCase(userDto.getChannelCd())){
				this.setRecall("Y");
//				logger.info("Recall / amend, case 2 salesChannel");
				return;
			}
		}
		if(needChannelID){
			if(channelCodeListOfChannelId != null){ 
				for(String channelCode : channelCodeListOfChannelId){
					if(this.salesChannel.equalsIgnoreCase(channelCode)){
						this.setRecall("Y");
//						logger.info("Recall / amend, case 1 salesChannel id");
						return;
					}
				}
			}	
		}
		if(needCentreCD){
			if(teamCodeListOfCentreCd != null){ 
				for(String teamCode : teamCodeListOfCentreCd){
					if(this.salesTeam.equalsIgnoreCase(teamCode)){
						this.setRecall("Y");
//						logger.info("Recall / amend, case 0 salesCentre cd");
						return;
					}
				}
			}
		}
		logger.debug(
				"\nuser : Channel:"+userDto.getChannelCd()+
				" \tteam:"+userDto.getShopCd()+
				" \tid:"+userDto.getUsername()+
				" \tid2:"+userDto.getUsername()+
				
				"\norder: Channel:"+this.salesChannel+
				" \tTeam:"+this.salesTeam+
				" \tid:"+this.salesCd+
				" \tid2:"+this.createBy+
				" \tcannot recall:"+this.orderId);
		
		setRecall("N");
	}
	public void setRecall(String recall) {
		this.recall = recall;
	}
	public String getRecall() {
		return recall;
	}
	public void setReasonCD(String reasonCD) {
		this.reasonCD = reasonCD;
	}
	public String getReasonCD() {
		return reasonCD;
	}
	public void setIsTermination(String isTermination) {
		this.isTermination = isTermination;
	}
	public String getIsTermination() {
		return isTermination;
	}
	
	public void setSignoffDate(String signoffDate) {
		this.signoffDate = signoffDate;
	}
	public String getSignoffDate() {
		return signoffDate;
	}
	public void setServiceReqDate(String serviceReqDate) {
		this.serviceReqDate = serviceReqDate;
	}
	public String getServiceReqDate() {
		return serviceReqDate;
	}
	public void setSourceCD(String sourceCD) {
		this.sourceCD = sourceCD;
	}
	public String getSourceCD() {
		return sourceCD;
	}
	public void setQcRemarks(String qcRemarks) {
		this.qcRemarks = qcRemarks;
	}
	public String getQcRemarks() {
		return qcRemarks;
	}
	public void setWaiveQC(String waiveQC) {
		this.waiveQC = waiveQC;
	}
	public String getWaiveQC() {
		return waiveQC;
	}
	public void setAssignDate(String assignDate) {
		this.assignDate = assignDate;
	}
	public String getAssignDate() {
		return assignDate;
	}
	public void setAmendment(String amendment) {
		this.amendment = amendment;
	}
	public String getAmendment() {
		return amendment;
	}
	public void setIdDocNum(String idDocNum) {
		this.idDocNum = idDocNum;
	}
	public String getIdDocNum() {
		return idDocNum;
	}
	public void setPaymentMtd(String paymentMtd) {
		this.paymentMtd = paymentMtd;
	}
	public String getPaymentMtd() {
		return paymentMtd;
	}
	public void setIs3rdParty(String is3rdParty) {
		this.is3rdParty = is3rdParty;
	}
	public String getIs3rdParty() {
		return is3rdParty;
	}
	public void setHousingType(String housingType) {
		this.housingType = housingType;
	}
	public String getHousingType() {
		return housingType;
	}
	public void setStaffType(String staffType) {
		this.staffType = staffType;
	}
	public String getStaffType() {
		return staffType;
	}
	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}
	public String getAssignee() {
		return assignee;
	}
	public void setAmendmentHistory(String amendmentHistory) {
		this.amendmentHistory = amendmentHistory;
	}
	public String getAmendmentHistory() {
		return amendmentHistory;
	}
	public void setLastUpdDate(Date lastUpdDate) {
		this.lastUpdDate = lastUpdDate;
	}
	public Date getLastUpdDate() {
		return lastUpdDate;
	}
	public void setMaskName(String maskName) {
		this.maskName = maskName;
	}
	public String getMaskName() {
		return maskName;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	

}
