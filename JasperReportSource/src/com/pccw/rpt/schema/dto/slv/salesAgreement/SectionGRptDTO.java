package com.pccw.rpt.schema.dto.slv.salesAgreement;

import java.io.Serializable;
import java.util.ArrayList;

import com.pccw.rpt.util.ReportUtil;

public class SectionGRptDTO extends FixContentRptDTO {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6052622046813884006L;

	private WebSite[] slvWebSites = new WebSite[20];
	
	private ArrayList<WebSite> slvWebSiteList = new ArrayList<WebSite>();
	
	public SectionGRptDTO() {
		for (int i = 0; i < this.slvWebSites.length; i++) {
			this.slvWebSites[i] = new WebSite();
		}
	}

	public void addSlvWebSite(String pSiteName, String pSiteUrl) {
		this.slvWebSiteList.add(new WebSite(pSiteName, pSiteUrl));
	}
		
	public ArrayList<WebSite> getSlvWebSiteList() {
		return this.slvWebSiteList;
	}

	public void setSlvWebSiteList(ArrayList<WebSite> pSlvWebSiteList) {
		this.slvWebSiteList = pSlvWebSiteList;
	}

	public boolean isSlvWebSiteListEmpty() {
		return this.slvWebSiteList.isEmpty();
	}
	
	public WebSite[] getSlvWebSites() {
		return this.slvWebSites;
	}

	public void setSlvWebSites(WebSite[] pSlvWebSites) {
		this.slvWebSites = pSlvWebSites;
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
