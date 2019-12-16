package com.bomwebportal.lts.dto.form;

import java.io.Serializable;
import java.util.List;

import com.bomwebportal.lts.dto.ItemSetDetailDTO;

public class LtsPremiumSelectionFormDTO implements Serializable {

	
	private static final long serialVersionUID = -1651686404433391296L;
	
	private String action;
	private String giftCode;
	private String pcdSbid;
	private String pcdSbidErrMsg;
	private List<ItemSetDetailDTO> premiumSetList;
	private List<ItemSetDetailDTO> giftSetList;

	public List<ItemSetDetailDTO> getPremiumSetList() {
		return premiumSetList;
	}

	public void setPremiumSetList(List<ItemSetDetailDTO> premiumSetList) {
		this.premiumSetList = premiumSetList;
	}	

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getGiftCode() {
		return giftCode;
	}

	public void setGiftCode(String giftCode) {
		this.giftCode = giftCode;
	}

	public String getPcdSbid() {
		return pcdSbid;
	}

	public void setPcdSbid(String pcdSbid) {
		this.pcdSbid = pcdSbid;
	}

	public String getPcdSbidErrMsg() {
		return pcdSbidErrMsg;
	}

	public void setPcdSbidErrMsg(String pcdSbidErrMsg) {
		this.pcdSbidErrMsg = pcdSbidErrMsg;
	}

	public List<ItemSetDetailDTO> getGiftSetList() {
		return giftSetList;
	}

	public void setGiftSetList(List<ItemSetDetailDTO> giftSetList) {
		this.giftSetList = giftSetList;
	}

}
