package com.bomwebportal.ims.dto.report;

import java.io.Serializable;

import com.bomwebportal.util.NTVUtil;
import com.pccw.rpt.util.ReportUtil;

public class RptServiceDetailDTO implements Serializable{

	private String itemHtml ;
	private String itemHtml2 ;
	private String itemRecurrentAmt;
	private String itemType;
	private String itemRecurrentAmt2;
	private String itemRecurrentAmt3;
	private String itemId ;
	
	
	public RptServiceDetailDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public RptServiceDetailDTO(String pItemHtml, String pItemRecurrentAmt) {
		itemHtml = pItemHtml;
		itemRecurrentAmt = pItemRecurrentAmt;
	}

	public String getItemHtml() {
		StringBuilder sbContents = null;
		sbContents = new StringBuilder(NTVUtil.defaultStringRpt(this.itemHtml));
		return sbContents.toString();	
	}

	public void setItemHtml(String itemHtml) {
		this.itemHtml = itemHtml;
	}
	public String getItemHtml2() {
		return itemHtml2;
	}

	public void setItemHtml2(String itemHtml2) {
		this.itemHtml2 = itemHtml2;
	}

	public String getItemRecurrentAmt() {
		return itemRecurrentAmt;
	}

	public void setItemRecurrentAmt(String itemRecurrentAmt) {
		this.itemRecurrentAmt = itemRecurrentAmt;
	}

	public String getItemType() {
		return itemType;
	}

	public void setItemType(String itemType) {
		this.itemType = itemType;
	}

	public String getItemRecurrentAmt2() {
		return itemRecurrentAmt2;
	}

	public void setItemRecurrentAmt2(String itemRecurrentAmt2) {
		this.itemRecurrentAmt2 = itemRecurrentAmt2;
	}

	public void setItemRecurrentAmt3(String itemRecurrentAmt3) {
		this.itemRecurrentAmt3 = itemRecurrentAmt3;
	}

	public String getItemRecurrentAmt3() {
		return itemRecurrentAmt3;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}


	
	

	
}
