package com.bomwebportal.lts.dto.activity;

import com.activity.dto.ActivityTaskDTO;

public class LtsActivityTaskDTO extends ActivityTaskDTO {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7209262497606807885L;
	public void setSrvType(String pSrvType){
		this.keyA = pSrvType;
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
	
	public void setRelatedSrvType(String pRelatedSrvType){
		this.keyC = pRelatedSrvType;
	}
	public String getRelatedSrvType(){
		return this.keyC;
	}
	
	public void setRelatedSrvNum(String pRelatedSrvNum){
		this.keyD = pRelatedSrvNum;
	}
	public String getRelatedSrvNum(){
		return this.keyD;
	}
	
	public void setSrd(String pSrd){
		this.keyE = pSrd;
	}
	
	public String getSrd(){
		return this.keyE;
	}
}
