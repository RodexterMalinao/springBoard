package com.bomltsportal.dto;

import java.io.Serializable;

public class SearchAddressResultDTO implements Serializable{
	
	private static final long serialVersionUID = -1909752221626416044L;
	
	private int resultSize;
	private MarkerDTO[] resultList;
	
	public int getResultSize() {
		return resultSize;
	}
	public void setResultSize(int resultSize) {
		this.resultSize = resultSize;
	}
	public MarkerDTO[] getResultList() {
		return resultList;
	}
	public void setResultList(MarkerDTO[] resultList) {
		this.resultList = resultList;
	}
	
	

}
