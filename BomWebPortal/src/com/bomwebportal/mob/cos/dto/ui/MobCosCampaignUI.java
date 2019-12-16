package com.bomwebportal.mob.cos.dto.ui;

import java.util.ArrayList;
import java.util.List;

import com.bomwebportal.mob.cos.dto.MobCosCampaignDtlDTO;
import com.bomwebportal.mob.cos.dto.MobCosCampaignHdrDTO;

public class MobCosCampaignUI implements java.io.Serializable {

	private static final long serialVersionUID = -5280880547513169835L;
	
	private String actionType;
	private String secCpgTitle;
	private String secCpgName;
	private String secHandset;
	private String secResult;
	
	private MobCosCampaignHdrDTO cpgHdrDTO = new MobCosCampaignHdrDTO();
	private List<MobCosCampaignDtlDTO> cpgDtlDTOList = new ArrayList<MobCosCampaignDtlDTO>();
	
	private String curCpgId;
	private String curBasketSeq;
	
	public String getActionType() {
		return actionType;
	}
	public void setActionType(String actionType) {
		this.actionType = actionType;
	}
	public String getSecCpgTitle() {
		return secCpgTitle;
	}
	public void setSecCpgTitle(String secCpgTitle) {
		this.secCpgTitle = secCpgTitle;
	}
	public String getSecCpgName() {
		return secCpgName;
	}
	public void setSecCpgName(String secCpgName) {
		this.secCpgName = secCpgName;
	}
	public String getSecHandset() {
		return secHandset;
	}
	public void setSecHandset(String secHandset) {
		this.secHandset = secHandset;
	}
	public String getSecResult() {
		return secResult;
	}
	public void setSecResult(String secResult) {
		this.secResult = secResult;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public MobCosCampaignHdrDTO getCpgHdrDTO() {
		return cpgHdrDTO;
	}
	public void setCpgHdrDTO(MobCosCampaignHdrDTO cpgHdrDTO) {
		this.cpgHdrDTO = cpgHdrDTO;
	}
	public List<MobCosCampaignDtlDTO> getCpgDtlDTOList() {
		return cpgDtlDTOList;
	}
	public void setCpgDtlDTOList(List<MobCosCampaignDtlDTO> cpgDtlDTOList) {
		this.cpgDtlDTOList = cpgDtlDTOList;
	}
	
	public String getCurCpgId() {
		return curCpgId;
	}
	public void setCurCpgId(String curCpgId) {
		this.curCpgId = curCpgId;
	}
	public String getCurBasketSeq() {
		return curBasketSeq;
	}
	public void setCurBasketSeq(String curBasketSeq) {
		this.curBasketSeq = curBasketSeq;
	}
}
