package com.bomwebportal.dto.ui;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.bomwebportal.dto.DepositDTO;
import com.bomwebportal.dto.DepositLkupDTO;

public class DepositUI implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4231584342143619388L;
	
	private List<DepositDTO> depositDTOList;
	//private List<DepositLkupDTO> depositLkupList;
	
	public DepositUI() {
		depositDTOList = new ArrayList<DepositDTO>();
		//depositLkupList = new ArrayList<DepositLkupDTO>();
	}
	public List<DepositDTO> getDepositDTOList() {
		return depositDTOList;
	}

	public void setDepositDTOList(List<DepositDTO> depositDTOList) {
		this.depositDTOList = depositDTOList;
	}
	
	/*
	public List<DepositLkupDTO> getDepositLkupList() {
		System.out.println("GGGGGGG");
		return depositLkupList;
	}
	public void setDepositLkupList(List<DepositLkupDTO> depositLkupList) {
		this.depositLkupList = depositLkupList;
	}
	*/
	
	
}
