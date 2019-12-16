package com.bomwebportal.ims.dto;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

public class NtvInfo implements Serializable{
	private String OrisumNum;
	private String DscsumNum;
    private String fsa;
	private boolean success;
    private String responseCode;
    private String callerReferenceNo;
    private String serverReferenceNo;
    private String elapsedTime;
    private List<Campaign> campaigns;
    public String getFsa() {
		return fsa;
	}

	public void setFsa(String fsa) {
		this.fsa = fsa;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}

	public String getCallerReferenceNo() {
		return callerReferenceNo;
	}

	public void setCallerReferenceNo(String callerReferenceNo) {
		this.callerReferenceNo = callerReferenceNo;
	}

	public String getServerReferenceNo() {
		return serverReferenceNo;
	}

	public void setServerReferenceNo(String serverReferenceNo) {
		this.serverReferenceNo = serverReferenceNo;
	}

	public String getElapsedTime() {
		return elapsedTime;
	}

	public void setElapsedTime(String elapsedTime) {
		this.elapsedTime = elapsedTime;
	}

	public List<Campaign> getCampaigns() {
		return campaigns;
	}

	public void setCampaigns(List<Campaign> campaigns) {
		this.campaigns = campaigns;
	}

	public void setOrisumNum(String orisumNum) {
		OrisumNum = orisumNum;
	}

	public String getOrisumNum() {
		return OrisumNum;
	}

	public void setDscsumNum(String dscsumNum) {
		DscsumNum = dscsumNum;
	}

	public String getDscsumNum() {
		return DscsumNum;
	}

	public class Campaign {
    	private String campCode;
		private String campDesc;
    	private String origPrice;
    	private String discPrice;
    	private long effDate;
		private long termDate;
    	private List<Pack> packs;

    	public String getCampCode() {
			return campCode;
		}

		public void setCampCode(String campCode) {
			this.campCode = campCode;
		}

		public String getCampDesc() {
			return campDesc;
		}

		public void setCampDesc(String campDesc) {
			this.campDesc = campDesc;
		}

		public String getOrigPrice() {
			return origPrice;
		}

		public void setOrigPrice(String origPrice) {
			this.origPrice = origPrice;
		}

		public String getDiscPrice() {
			return discPrice;
		}

		public void setDiscPrice(String discPrice) {
			this.discPrice = discPrice;
		}

		public long getEffDate() {
			return effDate;
		}

		public void setEffDate(long effDate) {
			this.effDate = effDate;
		}

		public List<Pack> getPacks() {
			return packs;
		}

		public void setPacks(List<Pack> packs) {
			this.packs = packs;
		}


        public void setTermDate(long termDate) {
			this.termDate = termDate;
		}

		public long getTermDate() {
			return termDate;
		}

		public String getTermDateFmt(){
			if(termDate!=0){
				Date date = new Date(termDate);
				DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
				return formatter.format(date);
			}else
				return "";
		}

		public String getEffDateFmt(){
			if(effDate!=0){
				Date date = new Date(effDate);
				DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
				return formatter.format(date);
			}else
				return "";
		}		
		
		public class Pack {
        	private String packCode;
        	private String packDesc;
			public String getPackCode() {
				return packCode;
			}
			public void setPackCode(String packCode) {
				this.packCode = packCode;
			}
			public String getPackDesc() {
				return packDesc;
			}
			public void setPackDesc(String packDesc) {
				this.packDesc = packDesc;
			}
      
        }
    }
}


