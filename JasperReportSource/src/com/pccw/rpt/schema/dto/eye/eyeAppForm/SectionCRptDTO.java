package com.pccw.rpt.schema.dto.eye.eyeAppForm;

import java.io.Serializable;
import java.util.ArrayList;

import com.pccw.rpt.util.ReportUtil;

public class SectionCRptDTO extends SectionRptDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8048576099832864439L;

	private String eyeContractPeriod;

	private String eyeInstallDate;

	private String eyeApptTime;

	private String eyeSrvNum;

	private String eyeSrvNumStatus;
	
	private String eyePrewiringDate;
	
	private String eyePrewiringTime;
	
	private String eyeCutOrderDate;
	
	private String eyeCutOrderTime;

	private ArrayList<ChargingItem> eyePlanList = new ArrayList<ChargingItem>();

	private String resTelContractPeriod;

	private String resTelInstallDate;

	private String resTelApptTime;

	private String resTelSrvNum;

	private String resTelSrvNumStatus;
	
	private String resTelPrewiringDate;
	
	private String resTelPrewiringTime;

	private String resTelCutOrderDate;
	
	private String resTelCutOrderTime;
	
	private boolean workingDNInd;

	private ArrayList<ChargingItem> resTelPlanList = new ArrayList<ChargingItem>();
	
	private boolean exDirectory;
	
	private String exDirectoryText;
	
	private String notExDirectoryText;
	
	private boolean portIn;
	
	private boolean fieldVisitInd;
	
	private String pipbType;
	
	private String switchingSrvNum;
	
	private String reuseSocketInd;
	
	private String pipbTargetSwitchTime;
	
	private String pipbTargetSwitchDate;
	
    private String pipbTargetInstallTime;
	
	private String pipbTargetInstallDate;
	
	private String newSrvNum;
	
	private String orderSubType;
	
	private boolean eyePreinstallation;
	
	private boolean resTelPreinstallation;
	
	//Modified by Max.R.Meng
	private String duplexNum;
	private String newDuplexNum;

	public String getPipbType() {
		return pipbType;
	}

	public void setPipbType(String pipbType) {
		this.pipbType = pipbType;
	}

	public String getSwitchingSrvNum() {
		return switchingSrvNum;
	}

	public void setSwitchingSrvNum(String switchingSrvNum) {
		this.switchingSrvNum = switchingSrvNum;
	}

//	public String getTargetSwitchTime() {
//		return targetSwitchTime;
//	}
//
//	public void setTargetSwitchTime(String targetSwitchTime) {
//		this.targetSwitchTime = targetSwitchTime;
//	}
//
//	public String getTargetSwitchDate() {
//		return targetSwitchDate;
//	}
//
//	public void setTargetSwitchDate(String targetSwitchDate) {
//		this.targetSwitchDate = targetSwitchDate;
//	}

	public String getEyeContractPeriod() {
		return ReportUtil.defaultString(this.eyeContractPeriod);
	}

	public void setEyeContractPeriod(String pEyeContractPeriod) {
		this.eyeContractPeriod = pEyeContractPeriod;
	}
	
	public String getEyeInstallDate() {
		return ReportUtil.defaultString(this.eyeInstallDate);
	}

	public void setEyeInstallDate(String pEyeInstallDate) {
		this.eyeInstallDate = pEyeInstallDate;
	}

	public String getEyeApptTime() {
		return ReportUtil.defaultString(this.eyeApptTime);
	}

	public void setEyeApptTime(String pEyeApptTime) {
		this.eyeApptTime = pEyeApptTime;
	}

	public String getEyeSrvNum() {
		return ReportUtil.defaultString(this.eyeSrvNum);
	}

	public void setEyeSrvNum(String pEyeSrvNum) {
		this.eyeSrvNum = pEyeSrvNum;
	}

	public ArrayList<ChargingItem> getEyePlanList() {
		return this.eyePlanList;
	}

	public void addEyePlan(String pDescription, String pMonthlyFixedTermRate,
			String pMonthToMonthRate) {
		this.eyePlanList.add(new ChargingItem(this.replaceEyeDeviceKeyword(pDescription),
				pMonthlyFixedTermRate, pMonthToMonthRate));
	}

	public String getResTelContractPeriod() {
		return ReportUtil.defaultString(this.resTelContractPeriod);
	}

	public void setResTelContractPeriod(String pResTelContractPeriod) {
		this.resTelContractPeriod = pResTelContractPeriod;
	}
	
	public String getResTelInstallDate() {
		return ReportUtil.defaultString(this.resTelInstallDate);
	}

	public void setResTelInstallDate(String pResTelInstallDate) {
		this.resTelInstallDate = pResTelInstallDate;
	}

	public String getResTelApptTime() {
		return ReportUtil.defaultString(this.resTelApptTime);
	}

	public void setResTelApptTime(String pResTelApptTime) {
		this.resTelApptTime = pResTelApptTime;
	}

	public String getResTelSrvNum() {
		return ReportUtil.defaultString(this.resTelSrvNum);
	}

	public void setResTelSrvNum(String pResTelSrvNum) {
		this.resTelSrvNum = pResTelSrvNum;
	}

	public ArrayList<ChargingItem> getResTelPlanList() {
		return this.resTelPlanList;
	}

	public void addResTelPlan(String pDescription, String pMonthlyFixedTermRate,
			String pMonthToMonthRate) {
		this.resTelPlanList.add(new ChargingItem(pDescription,
				pMonthlyFixedTermRate, pMonthToMonthRate));
	}

	public String getEyeSrvNumStatus() {
		return ReportUtil.defaultString(this.eyeSrvNumStatus);
	}

	public void setEyeSrvNumStatus(String pEyeSrvNumStatus) {
		this.eyeSrvNumStatus = pEyeSrvNumStatus;
	}

	public String getResTelSrvNumStatus() {
		return ReportUtil.defaultString(this.resTelSrvNumStatus);
	}

	public void setResTelSrvNumStatus(String pResTelSrvNumStatus) {
		this.resTelSrvNumStatus = pResTelSrvNumStatus;
	}

	public String getEyePrewiringDate() {
		return ReportUtil.defaultString(this.eyePrewiringDate);
	}

	public void setEyePrewiringDate(String eyePrewiringDate) {
		this.eyePrewiringDate = eyePrewiringDate;
	}

	public String getEyePrewiringTime() {
		return ReportUtil.defaultString(this.eyePrewiringTime);
	}

	public void setEyePrewiringTime(String eyePrewiringTime) {
		this.eyePrewiringTime = eyePrewiringTime;
	}

	public String getResTelPrewiringDate() {
		return ReportUtil.defaultString(this.resTelPrewiringDate);
	}

	public void setResTelPrewiringDate(String resTelPrewiringDate) {
		this.resTelPrewiringDate = resTelPrewiringDate;
	}

	public String getResTelPrewiringTime() {
		return ReportUtil.defaultString(this.resTelPrewiringTime);
	}

	public void setResTelPrewiringTime(String resTelPrewiringTime) {
		this.resTelPrewiringTime = resTelPrewiringTime;
	}

	public String getEyeCutOrderDate() {
		return ReportUtil.defaultString(this.eyeCutOrderDate);
	}

	public void setEyeCutOrderDate(String eyeCutOrderDate) {
		this.eyeCutOrderDate = eyeCutOrderDate;
	}

	public String getEyeCutOrderTime() {
		return ReportUtil.defaultString(this.eyeCutOrderTime);
	}

	public void setEyeCutOrderTime(String eyeCutOrderTime) {
		this.eyeCutOrderTime = eyeCutOrderTime;
	}

	public String getResTelCutOrderDate() {
		return ReportUtil.defaultString(this.resTelCutOrderDate);
	}

	public void setResTelCutOrderDate(String resTelCutOrderDate) {
		this.resTelCutOrderDate = resTelCutOrderDate;
	}

	public String getResTelCutOrderTime() {
		return ReportUtil.defaultString(this.resTelCutOrderTime);
	}

	public void setResTelCutOrderTime(String resTelCutOrderTime) {
		this.resTelCutOrderTime = resTelCutOrderTime;
	}

	public boolean isWorkingDNInd() {
		return this.workingDNInd;
	}

	public void setWorkingDNInd(boolean workingDNInd) {
		this.workingDNInd = workingDNInd;
	}

	public static class ChargingItem implements Serializable {

		/**
		 * 
		 */
		private static final long serialVersionUID = -3608859819133780544L;

		private String itemType;
		
		private String description;

		private String monthlyFixedTermRate;

		private String monthToMonthRate;

		public ChargingItem() {
			super();
		}

		public ChargingItem(String pDescription, String pMonthlyFixedTermRate,
				String pMonthToMonthRate) {
			super();
			this.description = pDescription;
			this.monthlyFixedTermRate = pMonthlyFixedTermRate;
			this.monthToMonthRate = pMonthToMonthRate;
		}

		public ChargingItem(String pItemType, String pDescription, String pMonthlyFixedTermRate,
				String pMonthToMonthRate) {
			super();
			this.itemType = pItemType;
			this.description = pDescription;
			this.monthlyFixedTermRate = pMonthlyFixedTermRate;
			this.monthToMonthRate = pMonthToMonthRate;
		}
		
		public String getDescription() {
			return ReportUtil.defaultString(this.description);
		}

		public void setDescription(String pDescription) {
			this.description = pDescription;
		}

		public String getMonthlyFixedTermRate() {
			return ReportUtil.defaultString(this.monthlyFixedTermRate);
		}

		public void setMonthlyFixedTermRate(String pMonthlyFixedTermRate) {
			this.monthlyFixedTermRate = pMonthlyFixedTermRate;
		}

		public String getMonthToMonthRate() {
			return ReportUtil.defaultString(this.monthToMonthRate);
		}

		public void setMonthToMonthRate(String pMonthToMonthRate) {
			this.monthToMonthRate = pMonthToMonthRate;
		}

		public String getItemType() {
			return ReportUtil.defaultString(itemType);
		}

		public void setItemType(String itemType) {
			this.itemType = itemType;
		}
	}

	public boolean isExDirectory() {
		return this.exDirectory;
	}

	public void setExDirectory(boolean pExDirectory) {
		this.exDirectory = pExDirectory;
	}

	public String getExDirectoryText() {
		return this.exDirectoryText;
	}

	public void setExDirectoryText(String pExDirectoryText) {
		this.exDirectoryText = pExDirectoryText;
	}

	public String getNotExDirectoryText() {
		return this.notExDirectoryText;
	}

	public void setNotExDirectoryText(String pNotExDirectoryText) {
		this.notExDirectoryText = pNotExDirectoryText;
	}

	public boolean isPortIn() {
		return this.portIn;
	}

	public void setPortIn(boolean pPortIn) {
		this.portIn = pPortIn;
	}

	public boolean isFieldVisitInd() {
		return fieldVisitInd;
	}

	public void setFieldVisitInd(boolean fieldVisitInd) {
		this.fieldVisitInd = fieldVisitInd;
	}

	public String getReuseSocketInd() {
		return reuseSocketInd;
	}

	public void setReuseSocketInd(String reuseSocketInd) {
		this.reuseSocketInd = reuseSocketInd;
	}

	public String getPipbTargetSwitchTime() {
		return pipbTargetSwitchTime;
	}

	public void setPipbTargetSwitchTime(String pipbTargetSwitchTime) {
		this.pipbTargetSwitchTime = pipbTargetSwitchTime;
	}

	public String getPipbTargetSwitchDate() {
		return pipbTargetSwitchDate;
	}

	public void setPipbTargetSwitchDate(String pipbTargetSwitchDate) {
		this.pipbTargetSwitchDate = pipbTargetSwitchDate;
	}

	public String getPipbTargetInstallTime() {
		return pipbTargetInstallTime;
	}

	public void setPipbTargetInstallTime(String pipbTargetInstallTime) {
		this.pipbTargetInstallTime = pipbTargetInstallTime;
	}

	public String getPipbTargetInstallDate() {
		return pipbTargetInstallDate;
	}

	public void setPipbTargetInstallDate(String pipbTargetInstallDate) {
		this.pipbTargetInstallDate = pipbTargetInstallDate;
	}

	public String getNewSrvNum() {
		return newSrvNum;
	}

	public void setNewSrvNum(String newSrvNum) {
		this.newSrvNum = newSrvNum;
	}

	public String getDuplexNum() {
		return duplexNum;
	}

	public void setDuplexNum(String duplexNum) {
		this.duplexNum = duplexNum;
	}

	public String getNewDuplexNum() {
		return newDuplexNum;
	}

	public void setNewDuplexNum(String newDuplexNum) {
		this.newDuplexNum = newDuplexNum;
	}

	public String getOrderSubType() {
		return orderSubType;
	}

	public void setOrderSubType(String orderSubType) {
		this.orderSubType = orderSubType;
	}

	public boolean isEyePreinstallation() {
		return eyePreinstallation;
	}

	public void setEyePreinstallation(boolean eyePreinstallation) {
		this.eyePreinstallation = eyePreinstallation;
	}

	public boolean isResTelPreinstallation() {
		return resTelPreinstallation;
	}

	public void setResTelPreinstallation(boolean resTelPreinstallation) {
		this.resTelPreinstallation = resTelPreinstallation;
	}

}
