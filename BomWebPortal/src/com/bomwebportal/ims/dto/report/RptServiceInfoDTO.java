package com.bomwebportal.ims.dto.report;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bomwebportal.util.NTVUtil;
import com.pccw.rpt.util.ReportUtil;

public class RptServiceInfoDTO {	

	protected final Log logger = LogFactory.getLog(getClass());
	private String itemHtml ;
	private String itemHtml2 ;
	private String itemType;
	
	public RptServiceInfoDTO(){
		super();
	}
	
	public RptServiceInfoDTO(String pItemHtml) {
		itemHtml = pItemHtml;
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

	public String getItemType() {
		return itemType;
	}

	public void setItemType(String itemType) {
		this.itemType = itemType;
	}

	public void copy(RptServiceInfoDTO temp) {
		if(temp!=null){
			this.itemHtml=temp.getItemHtml();
			this.itemHtml2=temp.getItemHtml2();
			this.itemType=temp.getItemType();
		}else{
			logger.error("copying a word that DB do not have in RptServiceInfoDTO");
		}
	}

}
