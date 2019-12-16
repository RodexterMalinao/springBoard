package com.pccw.rpt.schema.dto.eye.eyeAppForm;

import java.io.Serializable;
import java.util.ArrayList;

import org.apache.commons.lang.StringUtils;

import com.pccw.rpt.util.ReportUtil;

public class SectionHRptDTO extends FixContentEyeResTelSectionRptDTO {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7556705572865014430L;

	private WebSite[] eyeWebSites = new WebSite[20];

	private WebSite[] resTelWebSites = new WebSite[20];
	
	private WebSite[] eyeResTelWebSites = new WebSite[20];

	public SectionHRptDTO() {
		for (int i = 0; i < this.eyeWebSites.length; i++) {
			this.eyeWebSites[i] = new WebSite();
		}
		for (int i = 0; i < this.resTelWebSites.length; i++) {
			this.resTelWebSites[i] = new WebSite();
		}
		for (int i = 0; i < this.eyeResTelWebSites.length; i++) {
			this.eyeResTelWebSites[i] = new WebSite();
		}
	}

	private void appendToArrayList(ArrayList<WebSite> pList, WebSite[] pWebSites) {
		for (int i = 0; i < pWebSites.length; i++) {
			if (StringUtils.isEmpty(pWebSites[i].getSiteName())) {
				continue;
			}
			pList.add(pWebSites[i]);
		}
	}
	
	public ArrayList<WebSite> getEyeWebSiteList() {
		ArrayList<WebSite> eyeWebSiteList = new ArrayList<WebSite>();
		appendToArrayList(eyeWebSiteList, this.eyeWebSites);
		return eyeWebSiteList;
	}

	public ArrayList<WebSite> getResTelWebSiteList() {
		ArrayList<WebSite> resTelWebSiteList = new ArrayList<WebSite>();
		appendToArrayList(resTelWebSiteList, this.resTelWebSites);
		return resTelWebSiteList;
	}
	
	public ArrayList<WebSite> getEyeResTelWebSiteList() {
		ArrayList<WebSite> eyeResTelWebSiteList = new ArrayList<WebSite>();
		appendToArrayList(eyeResTelWebSiteList, this.eyeResTelWebSites);
		return eyeResTelWebSiteList;
	}

	public WebSite[] getEyeWebSites() {
		return this.eyeWebSites;
	}

	public void setEyeWebSites(WebSite[] pEyeWebSites) {
		this.eyeWebSites = pEyeWebSites;
	}

	public WebSite[] getResTelWebSites() {
		return this.resTelWebSites;
	}

	public void setResTelWebSites(WebSite[] pResTelWebSites) {
		this.resTelWebSites = pResTelWebSites;
	}

	public WebSite[] getEyeResTelWebSites() {
		return this.eyeResTelWebSites;
	}

	public void setEyeResTelWebSites(WebSite[] pEyeResTelWebSites) {
		this.eyeResTelWebSites = pEyeResTelWebSites;
	}


	/**
	 * 
	 */
	public static class WebSite implements Serializable {
		
		/**
		 * 
		 */
		private static final long serialVersionUID = -2942500934764529246L;

		private String siteName;
		
		private String siteUrl;

		public WebSite() {
			
		}
		
		public WebSite(String pSiteName, String pSiteUrl) {
			super();
			this.siteName = pSiteName;
			this.siteUrl = pSiteUrl;
		}

		public String getSiteName() {
			return ReportUtil.defaultString(this.siteName);
		}

		public void setSiteName(String pSiteName) {
			this.siteName = pSiteName;
		}

		public String getSiteUrl() {
			return ReportUtil.defaultString(this.siteUrl);
		}

		public void setSiteUrl(String pSiteUrl) {
			this.siteUrl = pSiteUrl;
		}
	}
}
