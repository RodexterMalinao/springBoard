package com.bomltsportal.dto;

import java.io.Serializable;

public class ListItemDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5474153358322048777L;

	private String indexKey;
	private String searchType;
	private String searchKey;
	private String resultKey;
	private String resultEn;
	private String resultCh;
	private String displaySeq;
	private String lat;
	private String lng;

	public String getSearchType() {
		return searchType;
	}

	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}

	public String getSearchKey() {
		return searchKey;
	}

	public void setSearchKey(String searchKey) {
		this.searchKey = searchKey;
	}

	public String getResultKey() {
		return resultKey;
	}

	public void setResultKey(String resultKey) {
		this.resultKey = resultKey;
	}

	public String getResultEn() {
		return resultEn;
	}

	public void setResultEn(String resultEn) {
		this.resultEn = resultEn;
	}

	public String getResultCh() {
		return resultCh;
	}

	public void setResultCh(String resultCh) {
		this.resultCh = resultCh;
	}

	public String getDisplaySeq() {
		return displaySeq;
	}

	public void setDisplaySeq(String displaySeq) {
		this.displaySeq = displaySeq;
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public String getLng() {
		return lng;
	}

	public void setLng(String lng) {
		this.lng = lng;
	}

	public String getIndexKey() {
		return indexKey;
	}

	public void setIndexKey(String indexKey) {
		this.indexKey = indexKey;
	}

}
