package com.bomwebportal.lts.dto.activity;

import com.activity.dto.ActivityDTO;

public class LtsActivityDTO extends ActivityDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5115221348285137774L;
	public LtsActivityDTO(String pActvType) {
		super(pActvType);
		// TODO Auto-generated constructor stub
	}
	
	public void setSrvType(String pTpCode){
		this.keyA = pTpCode;
	}
	public String getSrvType(){
		return this.keyA;
	}
	
	public void setSrvNum(String pSrvNum){
		this.keyB = pSrvNum;
	}
	public String getSrvNum(){
		return this.keyB;
	}
	
	public void setRelatedActivityId(String pRelatedActvId){
		this.keyC = pRelatedActvId;
	}
	public String getRelatedActivityId(){
		return this.keyC;
	}
	
	public void setRelatedActivity(ActivityDTO pRelatedActv){
		if (pRelatedActv == null){
			return;
		}
		this.keyC = pRelatedActv.getActvId();
	}
}
