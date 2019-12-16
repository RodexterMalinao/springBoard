package com.pccw.rpt.schema.dto.slv.fieldJob;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.pccw.rpt.schema.dto.ReportDTO;

public class FieldJobRptDTO extends ReportDTO {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8924119828660849116L;
	public String slvFieldJobTitle;
	public String workRegNo;
	public String visitType;
	public String orderId;
	public String custName;
	public String actvId;
	public String packageName;
	public String profileId;
	public String address;
	public String premierInd;
	public String appDateTime;
	public String contactName;
	public String contactNum;
	public String remark;
	public String staffName;
	public String tcContactNum;
	public String sStaffName;
	public String sTcContactNum;
	public String salesStaffName;
	public String salesContactNum;
	public String sSalesStaffName;
	public String sSalesContactNum;
	public String contractorName;
	public String assignStaffId;
	public String assignStaffContactNum;
	public String prepareById;
	public String prepareByName;
	public String sleAuthorizedName;
	public List<EquipmentItem> equipItemList = new ArrayList<FieldJobRptDTO.EquipmentItem>();
	public List<JobItem> jobItemList = new ArrayList<FieldJobRptDTO.JobItem>();
	public String slvLogo;
	public String hereToServeLogo;
	public String groupLogo;
	public String mainNo;
	public String mainDate;
	public String warrEndDate;
	public String fsa;

	public String getSlvFieldJobTitle() {
		return this.slvFieldJobTitle;
	}

	public void setSlvFieldJobTitle(String pSlvFieldJobTitle) {
		this.slvFieldJobTitle = pSlvFieldJobTitle;
	}

	public String getVisitType() {
		return this.visitType;
	}

	public void setVisitType(String pVisitType) {
		this.visitType = pVisitType;
	}

	public String getOrderId() {
		return this.orderId;
	}

	public void setOrderId(String pOrderId) {
		this.orderId = pOrderId;
	}

	public String getCustName() {
		return this.custName;
	}

	public void setCustName(String pCustName) {
		this.custName = pCustName;
	}

	public String getActvId() {
		return this.actvId;
	}

	public void setActvId(String pActvId) {
		this.actvId = pActvId;
	}

	public String getPackageName() {
		return this.packageName;
	}

	public void setPackageName(String pPackageName) {
		this.packageName = pPackageName;
	}

	public String getProfileId() {
		return this.profileId;
	}

	public void setProfileId(String pProfileId) {
		this.profileId = pProfileId;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String pAddress) {
		this.address = pAddress;
	}

	public String getPremierInd() {
		return premierInd;
	}

	public void setPremierInd(String premierInd) {
		this.premierInd = premierInd;
	}

	public String getAppDateTime() {
		return this.appDateTime;
	}

	public void setAppDateTime(String pAppDateTime) {
		this.appDateTime = pAppDateTime;
	}

	public String getContactName() {
		return this.contactName;
	}

	public void setContactName(String pContactName) {
		this.contactName = pContactName;
	}

	public String getContactNum() {
		return this.contactNum;
	}

	public void setContactNum(String pContactNum) {
		this.contactNum = pContactNum;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String pRemark) {
		this.remark = pRemark;
	}

	public String getStaffName() {
		return this.staffName;
	}

	public void setStaffName(String pStaffName) {
		this.staffName = pStaffName;
	}

	public String getTcContactNum() {
		return this.tcContactNum;
	}

	public void setTcContactNum(String pTcContactNum) {
		this.tcContactNum = pTcContactNum;
	}

	public String getsStaffName() {
		return this.sStaffName;
	}

	public void setsStaffName(String pSStaffName) {
		this.sStaffName = pSStaffName;
	}

	public String getsTcContactNum() {
		return this.sTcContactNum;
	}

	public void setsTcContactNum(String pSTcContactNum) {
		this.sTcContactNum = pSTcContactNum;
	}

	public String getContractorName() {
		return this.contractorName;
	}

	public void setContractorName(String pContractorName) {
		this.contractorName = pContractorName;
	}

	public String getAssignStaffId() {
		return this.assignStaffId;
	}

	public void setAssignStaffId(String pAssignStaffId) {
		this.assignStaffId = pAssignStaffId;
	}

	public String getAssignStaffContactNum() {
		return this.assignStaffContactNum;
	}

	public void setAssignStaffContactNum(String pAssignStaffContactNum) {
		this.assignStaffContactNum = pAssignStaffContactNum;
	}

	public String getPrepareById() {
		return this.prepareById;
	}

	public void setPrepareById(String pPrepareById) {
		this.prepareById = pPrepareById;
	}

	public String getPrepareByName() {
		return this.prepareByName;
	}

	public void setPrepareByName(String pPrepareByName) {
		this.prepareByName = pPrepareByName;
	}

	public String getSleAuthorizedName() {
		return this.sleAuthorizedName;
	}

	public void setSleAuthorizedName(String pSleAuthorizedName) {
		this.sleAuthorizedName = pSleAuthorizedName;
	}

	public List<JobItem> getJobItemList() {
		return this.jobItemList;
	}

	public void setJobItemList(List<JobItem> pJobItemList) {
		this.jobItemList = pJobItemList;
	}

	public static class JobItem implements Serializable {
		/**
		 * 
		 */
		private static final long serialVersionUID = -8334046063279642966L;
		public String description;
		public String quantity;
		public String actualQuantity;
		public String remarks;

		public String getDescription() {
			return this.description;
		}

		public void setDescription(String pDescription) {
			this.description = pDescription;
		}

		public String getQuantity() {
			return this.quantity;
		}

		public void setQuantity(String pQuantity) {
			this.quantity = pQuantity;
		}

		public String getActualQuantity() {
			return this.actualQuantity;
		}

		public void setActualQuantity(String pActualQuantity) {
			this.actualQuantity = pActualQuantity;
		}

		public String getRemarks() {
			return this.remarks;
		}

		public void setRemarks(String pRemarks) {
			this.remarks = pRemarks;
		}
	}

	public static class EquipmentItem implements Serializable {
		/**
		 * 
		 */
		private static final long serialVersionUID = -110715530309634328L;
		public String description;
		public String quantity;
		public String actualQuantity;
		public String serialNo;
		public String remarks;
		public String effStartDate;
		public String effEndDate;

		public String getDescription() {
			return this.description;
		}

		public void setDescription(String pDescription) {
			this.description = pDescription;
		}

		public String getQuantity() {
			return this.quantity;
		}

		public void setQuantity(String pQuantity) {
			this.quantity = pQuantity;
		}


		public String getSerialNo() {
			return this.serialNo;
		}

		public void setSerialNo(String pSerialNo) {
			this.serialNo = pSerialNo;
		}

		public String getActualQuantity() {
			return this.actualQuantity;
		}

		public void setActualQuantity(String pActualQuantity) {
			this.actualQuantity = pActualQuantity;
		}

		public String getRemarks() {
			return this.remarks;
		}

		public void setRemarks(String pRemarks) {
			this.remarks = pRemarks;
		}

		public String getEffStartDate() {
			return this.effStartDate;
		}

		public void setEffStartDate(String pEffStartDate) {
			this.effStartDate = pEffStartDate;
		}

		public String getEffEndDate() {
			return this.effEndDate;
		}

		public void setEffEndDate(String pEffEndDate) {
			this.effEndDate = pEffEndDate;
		}

	}

	public String getSlvLogo() {
		return this.slvLogo;
	}

	public void setSlvLogo(String pSlvLogo) {
		this.slvLogo = pSlvLogo;
	}

	public String getHereToServeLogo() {
		return this.hereToServeLogo;
	}

	public void setHereToServeLogo(String pHereToServeLogo) {
		this.hereToServeLogo = pHereToServeLogo;
	}

	public String getGroupLogo() {
		return this.groupLogo;
	}

	public void setGroupLogo(String pGroupLogo) {
		this.groupLogo = pGroupLogo;
	}

	public List<EquipmentItem> getEquipItemList() {
		return this.equipItemList;
	}

	public void setEquipItemList(List<EquipmentItem> pEquipItemList) {
		this.equipItemList = pEquipItemList;
	}

	public String getWorkRegNo() {
		return this.workRegNo;
	}

	public void setWorkRegNo(String pWorkRegNo) {
		this.workRegNo = pWorkRegNo;
	}

	public String getMainNo() {
		return this.mainNo;
	}

	public void setMainNo(String pMainNo) {
		this.mainNo = pMainNo;
	}

	public String getMainDate() {
		return this.mainDate;
	}

	public void setMainDate(String pMainDate) {
		this.mainDate = pMainDate;
	}

	public String getWarrEndDate() {
		return this.warrEndDate;
	}

	public void setWarrEndDate(String pWarrEndDate) {
		this.warrEndDate = pWarrEndDate;
	}

	public String getFsa() {
		return this.fsa;
	}

	public void setFsa(String pFsa) {
		this.fsa = pFsa;
	}

	public String getSalesStaffName() {
		return this.salesStaffName;
	}

	public void setSalesStaffName(String pSalesStaffName) {
		this.salesStaffName = pSalesStaffName;
	}

	public String getSalesContactNum() {
		return this.salesContactNum;
	}

	public void setSalesContactNum(String pSalesContactNum) {
		this.salesContactNum = pSalesContactNum;
	}

	public String getsSalesStaffName() {
		return this.sSalesStaffName;
	}

	public void setsSalesStaffName(String pSSalesStaffName) {
		this.sSalesStaffName = pSSalesStaffName;
	}

	public String getsSalesContactNum() {
		return this.sSalesContactNum;
	}

	public void setsSalesContactNum(String pSSalesContactNum) {
		this.sSalesContactNum = pSSalesContactNum;
	}
	
}
