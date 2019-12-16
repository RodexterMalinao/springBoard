package com.bomwebportal.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

public class VasMrtSelectionDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 560513764695272124L;
	
	private String vasItemId;
	private String funcId;
	
	private String msisdn;
	private String msisdnLvl;
	//private String mnpInd;
	private String chinaInd;
	private String goldenInd;
	private String numType;
	private	String cityCd;
	//private Date serviceReqDate;
	
	private String channelCd;
	private String msisdnStatus;
	private String srvNumType;
	
	private String shopCd;
	
	private HttpSession session;
	
	
    private String secSrvNum;
    private String dbSecSrvNum;
    private String oldSecSrvNum;
	
    
    private boolean ssSubscribed;
    private boolean chinaNumberSubscribed;
    
	public String getVasItemId() {
		return vasItemId;
	}
	public void setVasItemId(String vasItemId) {
		this.vasItemId = vasItemId;
	}
	public String getFuncId() {
		return funcId;
	}
	public void setFuncId(String funcId) {
		this.funcId = funcId;
	}
	public String getChannelCd() {
		return channelCd;
	}
	public void setChannelCd(String channelCd) {
		this.channelCd = channelCd;
	}
	public String getMsisdnStatus() {
		return msisdnStatus;
	}
	public void setMsisdnStatus(String msisdnStatus) {
		this.msisdnStatus = msisdnStatus;
	}
	public String getSrvNumType() {
		return srvNumType;
	}
	public void setSrvNumType(String srvNumType) {
		this.srvNumType = srvNumType;
	}
	public String getMsisdn() {
		return msisdn;
	}
	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}
	public String getMsisdnLvl() {
		return msisdnLvl;
	}
	public void setMsisdnLvl(String msisdnLvl) {
		this.msisdnLvl = msisdnLvl;
	}
	public String getChinaInd() {
		return chinaInd;
	}
	public void setChinaInd(String chinaInd) {
		this.chinaInd = chinaInd;
	}
	public String getGoldenInd() {
		return goldenInd;
	}
	public void setGoldenInd(String goldenInd) {
		this.goldenInd = goldenInd;
	}
	public String getNumType() {
		return numType;
	}
	public void setNumType(String numType) {
		this.numType = numType;
	}
	public String getCityCd() {
		return cityCd;
	}
	public void setCityCd(String cityCd) {
		this.cityCd = cityCd;
	}
	public String getShopCd() {
		return shopCd;
	}
	public void setShopCd(String shopCd) {
		this.shopCd = shopCd;
	}
	public HttpSession getSession() {
		return session;
	}
	public void setSession(HttpSession session) {
		this.session = session;
	}
	public String getSecSrvNum() {
		return secSrvNum;
	}
	public void setSecSrvNum(String secSrvNum) {
		this.secSrvNum = secSrvNum;
	}
	public String getOldSecSrvNum() {
		return oldSecSrvNum;
	}
	public void setOldSecSrvNum(String oldSecSrvNum) {
		this.oldSecSrvNum = oldSecSrvNum;
	}
	public String getDbSecSrvNum() {
		return dbSecSrvNum;
	}
	public void setDbSecSrvNum(String dbSecSrvNum) {
		this.dbSecSrvNum = dbSecSrvNum;
	}
	public boolean isSsSubscribed() {
		return ssSubscribed;
	}
	public void setSsSubscribed(boolean ssSubscribed) {
		this.ssSubscribed = ssSubscribed;
	}
	public boolean isChinaNumberSubscribed() {
		return chinaNumberSubscribed;
	}
	public void setChinaNumberSubscribed(boolean chinaNumberSubscribed) {
		this.chinaNumberSubscribed = chinaNumberSubscribed;
	}
	
	/**
	 * An object map key to DTO value
	 */
	private Map<String, Object> objectsMap;
	
	/**
	 * Get specific DTO object value which map to certain key
	 * @param key
	 * @return DTO object
	 */
	public Object getValue(String key) {
		if (this.objectsMap == null || this.objectsMap.isEmpty()) {
			return null;
		} else {
			return this.objectsMap.get(key);
		}
	}
	/**
	 * Set specific DTO object value into map which match with a unique key
	 * @param key
	 * @param value DTO object
	 */
	public void setValue(String key, Object value) {
		if (this.objectsMap == null) {
			objectsMap = new HashMap<String, Object>();
		}
		
		objectsMap.put(key, value);
	}
	
	/**
	 * @return the objectsMap
	 */
	public Map<String, Object> getObjectsMap() {
		return objectsMap;
	}
	/**
	 * @param objectsMap the objectsMap to set
	 */
	public void setObjectsMap(Map<String, Object> objectsMap) {
		this.objectsMap = objectsMap;
	}

	
}
