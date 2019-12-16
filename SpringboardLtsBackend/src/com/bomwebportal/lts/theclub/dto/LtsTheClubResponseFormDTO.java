package com.bomwebportal.lts.theclub.dto;

import java.util.ArrayList;
import java.util.List;

public class LtsTheClubResponseFormDTO {
	//common params - start
	private String rtnCode;
	private String rtnMsg;
	//common params - end
	// getMemberBasicInfoWithMaskedID - start	
	private String memberId;
	private String phantom;
	private String tier;
	private String tierStartDate;
	private String tierEndDate;
	private String idDocType;
	private String maskedIdDocNum;
	private String currentPointBal;
	private String status;
	private String statusDesc;
//	private List<BLtsTheClubTxnDTO> existingTxn;
	private List<PointQuarter> pointQuarter;
	// getMemberBasicInfoWithMaskedID - end
	
	private String name;
	private String loginId;
	// doInstantEarnPoint & doInstantEarnReversePoint - start
	private String transId;
	// doInstantEarnPoint & doInstantEarnReversePoint - end
	
	public String getTransId() {
		return transId;
	}

	public void setTransId(String transId) {
		this.transId = transId;
	}


	public List<PointQuarter> getPointQuarter() {
		if(pointQuarter==null){
			 pointQuarter = new ArrayList<PointQuarter>();
			 pointQuarter.add(new PointQuarter());
		}
		return pointQuarter;
	}

	public void setPointQuarter(List<PointQuarter> pointQuarter) {
		this.pointQuarter = pointQuarter;
	}

	public String getRtnCode() {
		return rtnCode;
	}

	public String getRtnMsg() {
		return rtnMsg;
	}

	public String getMemberId() {
		return memberId;
	}

	public String getPhantom() {
		return phantom;
	}

	public String getTier() {
		return tier;
	}

	public String getTierStartDate() {
		return tierStartDate;
	}

	public String getTierEndDate() {
		return tierEndDate;
	}

	public String getIdDocType() {
		return idDocType;
	}

	public String getMaskedIdDocNum() {
		return maskedIdDocNum;
	}

	public String getCurrentPointBal() {
		return currentPointBal;
	}

	public String getStatus() {
		return status;
	}

	public String getStatusDesc() {
		return statusDesc;
	}

	public void setRtnCode(String rtnCode) {
		this.rtnCode = rtnCode;
	}

	public void setRtnMsg(String rtnMsg) {
		this.rtnMsg = rtnMsg;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public void setPhantom(String phantom) {
		this.phantom = phantom;
	}

	public void setTier(String tier) {
		this.tier = tier;
	}

	public void setTierStartDate(String tierStartDate) {
		this.tierStartDate = tierStartDate;
	}

	public void setTierEndDate(String tierEndDate) {
		this.tierEndDate = tierEndDate;
	}

	public void setIdDocType(String idDocType) {
		this.idDocType = idDocType;
	}

	public void setMaskedIdDocNum(String maskedIdDocNum) {
		this.maskedIdDocNum = maskedIdDocNum;
	}

	public void setCurrentPointBal(String currentPointBal) {
		this.currentPointBal = currentPointBal;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setStatusDesc(String statusDesc) {
		this.statusDesc = statusDesc;
	}

	public class PointQuarter{
		private String start_date;
		private String end_date;
		private String expiry_date;
		public String getStart_date() {
			return start_date;
		}
		public String getEnd_date() {
			return end_date;
		}
		public String getExpiry_date() {
			return expiry_date;
		}
		public String getPoint_bal() {
			return point_bal;
		}
		public void setStart_date(String start_date) {
			this.start_date = start_date;
		}
		public void setEnd_date(String end_date) {
			this.end_date = end_date;
		}
		public void setExpiry_date(String expiry_date) {
			this.expiry_date = expiry_date;
		}
		public void setPoint_bal(String point_bal) {
			this.point_bal = point_bal;
		}
		private String point_bal;
		@Override
		public String toString() {
			return "PointQuarter [start_date=" + start_date + ", end_date=" + end_date + ", expiry_date=" + expiry_date
					+ ", point_bal=" + point_bal + "]";
		}
	}

	public String getName() {
		return name;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

//	@Override
//	public String toString() {
//		return "LtsTheClubResponseFormDTO [rtnCode=" + rtnCode + ", rtnMsg=" + rtnMsg + ", memberId=" + memberId
//				+ ", phantom=" + phantom + ", tier=" + tier + ", tierStartDate=" + tierStartDate + ", tierEndDate="
//				+ tierEndDate + ", idDocType=" + idDocType + ", maskedIdDocNum=" + maskedIdDocNum + ", currentPointBal="
//				+ currentPointBal + ", status=" + status + ", statusDesc=" + statusDesc + ", pointQuarter="
//				+ pointQuarter + ", transId=" + transId + "]";
//	}

//	public List<BLtsTheClubTxnDTO> getExistingTxn() {
//		return existingTxn;
//	}
//
//	public void setExistingTxn(List<BLtsTheClubTxnDTO> existingTxn) {
//		this.existingTxn = existingTxn;
//	}
	
}
