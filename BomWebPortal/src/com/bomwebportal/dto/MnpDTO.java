package com.bomwebportal.dto;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MnpDTO implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6348017209014876918L;
	
	private String orderId;
	private String mnpType;
	private String mnp;// for mnp.html control 
	private String msisdn;
	private int cnmStatus;
	private String shopCd;
	private Date cutoverDate;
	private Date serviceReqDate;
	private String mnpMsisdn;
	private String newMsisdn;
	private String oldMsisdn;
	private String dno;
	private String rno;
	private String cutoverTime;
	private String custName;
	private String custIdDocNum;
	private String msisdnLvl;
	
	private String cutoverDateStr;
	private String serviceReqDateStr;
	private String mnpTicketNum;
	
	//add 20120221 eliot
	private String unicomMsisdn;
	
	//add by herbert 20111019
	private String actionType;
	
	private String custNameChi;// add by wilson, 20120718
	private String prePaidSimDocInd;// add by wilson, 20120718
	private boolean prePaidSimDocWithCert=false;//add by wilson 20120718
	private boolean prePaidSimDocWithoutCert=false;//add by wilson 20120718
	
	private boolean futherMnp=false;// add by wilson, 20120718
	private String futherMnpNumber;// add by wilson, 20120718
	private Date futherCutoverDate;// add by wilson, 20120718
	private String futherCutoverDateStr;// add by wilson, 20120718
	
	private boolean ignoreSameMRTinSBcheckbox=false;//add by wilson, 20120830, false= need to check, 
	
	private boolean mnpIncentive=false;// add by wilson, 20121210, for order save insert MNP_INC item or order.
	
	private String cutOeverTimeError;
	
	private boolean amend;
	
	//add by nancy
	private String futherMnpTicketNum;
	private String futherCutoverTime;
	private String futherCutOeverTimeError;
	private String futhercustName;
	private String futhercustNameChi;
	private String futhercustIdDocNum;
	private boolean futherprePaidSimDocWithCert=false;
	private boolean futherprePaidSimDocWithoutCert=false;
	private String channelId;
		
	private String reserveId; //add by nancy
	private String isReserveMrt;
	
	//CSL origActDate 
	private Date origActDate;
	private String origActDateStr;

	//numberType
	private String numType;
	private String originalNewMsisdn;
	
	
	//DENNIS201606
	private String mnpExtendAuthInd;
	
	
	//Dennis MIP3
	private String actualDno;
	private boolean ignorePostpaidcheckbox=false;
	private boolean ignoreFutherPostpaidcheckbox = false;
	
	private boolean isChinaMobile = false;
	
	private boolean prepaidSimInd=false;
	private boolean futherPrepaidSimInd=false;
	
	private String opssInd;
	private String starterPack;
	
	private boolean checkIsWhiteList;
	private String customerTier;
	
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
	
	public Date getOrigActDate() {
		return origActDate;
	}

	public void setOrigActDate(Date origActDate) {
		this.origActDate = origActDate;
	}

	public String getOrigActDateStr() {
		return origActDateStr;
	}

	public void setOrigActDateStr(String origActDateStr) {
		this.origActDateStr = origActDateStr;
	}
	
	public String getCutOeverTimeError() {
		return cutOeverTimeError;
	}

	public void setCutOeverTimeError(String cutOeverTimeError) {
		this.cutOeverTimeError = cutOeverTimeError;
	}

	public boolean isMnpIncentive() {
		return mnpIncentive;
	}

	public void setMnpIncentive(boolean mnpIncentive) {
		this.mnpIncentive = mnpIncentive;
	}

	public String getActionType() {
		return actionType;
	}

	public void setActionType(String actionType) {
		this.actionType = actionType;
	}
	
	public String getMnpTicketNum() {
		return mnpTicketNum;
	}

	public void setMnpTicketNum(String mnpTicketNum) {
		this.mnpTicketNum = mnpTicketNum;
	}

	public String getMsisdnLvl() {
		return msisdnLvl;
	}

	public void setMsisdnLvl(String msisdnLvl) {
		this.msisdnLvl = msisdnLvl;
	}

	public String getMnpType() {
		return mnpType;
	}
	
	public void setMnpType(String mnpType) {
		this.mnpType = mnpType;
	}
	public String getRno() {
		return rno;
	}
	public void setRno(String rno) {
		this.rno = rno;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	
	public String getMnpMsisdn() {
		return mnpMsisdn;
	}
	public void setMnpMsisdn(String mnpMsisdn) {
		this.mnpMsisdn = mnpMsisdn;
	}
	public String getNewMsisdn() {
		return newMsisdn;
	}
	public void setNewMsisdn(String newMsisdn) {
		this.newMsisdn = newMsisdn;
	}
	/*public String getServiceReqYear() {
		return serviceReqYear;
	}
	public void setServiceReqYear(String serviceReqYear) {
		this.serviceReqYear = serviceReqYear;
	}
	public String getServiceReqMonth() {
		return serviceReqMonth;
	}
	public void setServiceReqMonth(String serviceReqMonth) {
		this.serviceReqMonth = serviceReqMonth;
	}
	public String getServiceReqDay() {
		return serviceReqDay;
	}
	public void setServiceReqDay(String serviceReqDay) {
		this.serviceReqDay = serviceReqDay;
	}*/
	
	public String getOldMsisdn() {
		return oldMsisdn;
	}

	public void setOldMsisdn(String oldMsisdn) {
		this.oldMsisdn = oldMsisdn;
	}

	public String getMnp() {
		return mnp;
	}
	public void setMnp(String mnp) {
		this.mnp = mnp;
	}
	public String getMsisdn() {
		return msisdn;
	}
	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}
	public Date getCutoverDate() {
		return cutoverDate;
	}
	public void setCutoverDate(Date cutoverDate) {
		this.cutoverDate = cutoverDate;
	}
	public int getCnmStatus() {
		return cnmStatus;
	}
	public void setCnmStatus(int cnmStatus) {
		this.cnmStatus = cnmStatus;
	}
	public String getShopCd() {
		return shopCd;
	}
	public void setShopCd(String shopCd) {
		this.shopCd = shopCd;
	}
	/*public String getCutoverYear() {
		return cutoverYear;
	}
	public void setCutoverYear(String cutoverYear) {
		this.cutoverYear = cutoverYear;
	}
	public String getCutoverMonth() {
		return cutoverMonth;
	}
	public void setCutoverMonth(String cutoverMonth) {
		this.cutoverMonth = cutoverMonth;
	}
	public String getCutoverDay() {
		return cutoverDay;
	}
	public void setCutoverDay(String cutoverDay) {
		this.cutoverDay = cutoverDay;
	}*/
	public String getCustName() {
		return custName;
	}
	public void setCustName(String custName) {
		this.custName = custName;
	}
	public String getCustIdDocNum() {
		return custIdDocNum;
	}
	public void setCustIdDocNum(String custIdDocNum) {
		this.custIdDocNum = custIdDocNum;
	}
	public Date getServiceReqDate() {
		return serviceReqDate;
	}
	public void setServiceReqDate(Date serviceReqDate) {
		this.serviceReqDate = serviceReqDate;
	}
	public String getDno() {
		return dno;
	}
	public void setDno(String dno) {
		this.dno = dno;
	}

	public String getCutoverDateStr() {
		return cutoverDateStr;
	}

	public void setCutoverDateStr(String cutoverDateStr) {
		this.cutoverDateStr = cutoverDateStr;
	}

	public String getServiceReqDateStr() {
		return serviceReqDateStr;
	}

	public void setServiceReqDateStr(String serviceReqDateStr) {
		this.serviceReqDateStr = serviceReqDateStr;
	}

	public String getUnicomMsisdn() {
	    return unicomMsisdn;
	}

	public void setUnicomMsisdn(String unicomMsisdn) {
	    this.unicomMsisdn = unicomMsisdn;
	}

	public String getCustNameChi() {
		return custNameChi;
	}

	public void setCustNameChi(String custNameChi) {
		this.custNameChi = custNameChi;
	}

	public String getPrePaidSimDocInd() {
		if((prePaidSimDocWithCert && !futherMnp) || (futherprePaidSimDocWithCert && futherMnp)){
			prePaidSimDocInd="Y";
		}
		if((prePaidSimDocWithoutCert && !futherMnp) || (futherprePaidSimDocWithoutCert && futherMnp)){
			prePaidSimDocInd="N";
		}
		
		return prePaidSimDocInd;
	}

	public void setPrePaidSimDocInd(String prePaidSimDocInd) {
		this.prePaidSimDocInd = prePaidSimDocInd;
	}

	public boolean isPrePaidSimDocWithCert() {
		if("Y".equals(prePaidSimDocInd) && !futherMnp){
			prePaidSimDocWithCert=true;
		}
		return prePaidSimDocWithCert;
	}

	public void setPrePaidSimDocWithCert(boolean prePaidSimDocWithCert) {
		this.prePaidSimDocWithCert = prePaidSimDocWithCert;
	}

	public boolean isPrePaidSimDocWithoutCert() {
		if("N".equals(prePaidSimDocInd) && !futherMnp){
			prePaidSimDocWithoutCert=true;
		}
		return prePaidSimDocWithoutCert;
	}

	public void setPrePaidSimDocWithoutCert(boolean prePaidSimDocWithoutCert) {
		this.prePaidSimDocWithoutCert = prePaidSimDocWithoutCert;
	}

	public boolean isFutherMnp() {
		return futherMnp;
	}

	public void setFutherMnp(boolean futherMnp) {
		this.futherMnp = futherMnp;
	}

	public String getFutherMnpNumber() {
		return futherMnpNumber;
	}

	public void setFutherMnpNumber(String futherMnpNumber) {
		this.futherMnpNumber = futherMnpNumber;
	}

	public Date getFutherCutoverDate() {
		return futherCutoverDate;
	}

	public void setFutherCutoverDate(Date futherCutoverDate) {
		this.futherCutoverDate = futherCutoverDate;
	}

	public String getFutherCutoverDateStr() {
		return futherCutoverDateStr;
	}

	public void setFutherCutoverDateStr(String futherCutoverDateStr) {
		this.futherCutoverDateStr = futherCutoverDateStr;
	}

	/**
	 * @return the ignoreSameMRTinSBcheckbox
	 */
	public boolean isIgnoreSameMRTinSBcheckbox() {
		return ignoreSameMRTinSBcheckbox;
	}

	/**
	 * @param ignoreSameMRTinSBcheckbox the ignoreSameMRTinSBcheckbox to set
	 */
	public void setIgnoreSameMRTinSBcheckbox(boolean ignoreSameMRTinSBcheckbox) {
		this.ignoreSameMRTinSBcheckbox = ignoreSameMRTinSBcheckbox;
	}

	public String getCutoverTime() {
		return cutoverTime;
	}

	public void setCutoverTime(String cutoverTime) {
		this.cutoverTime = cutoverTime;
	}

	public boolean isAmend() {
		return amend;
	}

	public void setAmend(boolean amend) {
		this.amend = amend;
	}

	public String getFutherMnpTicketNum(){
		return futherMnpTicketNum;
	}
	
	public void setFutherMnpTicketNum(String futherMnpTicketNum){
		this.futherMnpTicketNum = futherMnpTicketNum;
	}
	
	public String getFutherCutoverTime(){
		return futherCutoverTime;
	}
	
	public void setFutherCutoverTime(String futherCutoverTime){
		this.futherCutoverTime = futherCutoverTime;
	}
	
	public String getFutherCutOeverTimeError() {
		return futherCutOeverTimeError;
	}
	
	public void setFutherCutOeverTimeError(String futherCutOeverTimeError){
		this.futherCutOeverTimeError = futherCutOeverTimeError;
	}
	
	public String getFuthercustName(){
		return futhercustName;
	}
	
	public void setFuthercustName(String futhercustName){
		this.futhercustName = futhercustName;
	}
	
	public String getFuthercustNameChi(){
		return futhercustNameChi;
	}
	
	public void setFuthercustNameChi(String futhercustNameChi){
		this.futhercustNameChi = futhercustNameChi;
	}
	
	public String getFuthercustIdDocNum(){
		return futhercustIdDocNum;
	}
	
	public void setFuthercustIdDocNum(String futhercustIdDocNum){
		this.futhercustIdDocNum = futhercustIdDocNum;
	}
	
	public boolean isFutherprePaidSimDocWithCert() {
		if("Y".equals(prePaidSimDocInd) && futherMnp){
			futherprePaidSimDocWithCert=true;
		}
		return futherprePaidSimDocWithCert;
	}

	public void setFutherprePaidSimDocWithCert(boolean futherPrePaidSimDocWithCert) {
		this.futherprePaidSimDocWithCert = futherPrePaidSimDocWithCert;
	}

	public boolean isFutherprePaidSimDocWithoutCert() {
		if("N".equals(prePaidSimDocInd) && futherMnp){
			futherprePaidSimDocWithoutCert=true;
		}
		return futherprePaidSimDocWithoutCert;
	}

	public void setFutherprePaidSimDocWithoutCert(boolean futherPrePaidSimDocWithoutCert) {
		this.futherprePaidSimDocWithoutCert = futherPrePaidSimDocWithoutCert;
	}
	
	public String getChannelId(){
		return channelId;
	}
	
	public void setChannelId(String channelId){
		this.channelId = channelId;
	}
	
	public String getReserveId(){
		return reserveId;
	}
	
	public void setReserveId(String reserveId){
		this.reserveId = reserveId;
	}

	public String getIsReserveMrt() {
		return isReserveMrt;
	}

	public void setIsReserveMrt(String isReserveMrt) {
		this.isReserveMrt = isReserveMrt;
	}

	public String getNumType() {
		return numType;
	}

	public void setNumType(String numType) {
		this.numType = numType;
	}

	public String getOriginalNewMsisdn() {
		return originalNewMsisdn;
	}

	public void setOriginalNewMsisdn(String originalNewMsisdn) {
		this.originalNewMsisdn = originalNewMsisdn;
	}
	public String getActualDno() {
		return actualDno;
	}
	public void setActualDno(String actualDno) {
		this.actualDno = actualDno;
	}
	public boolean isIgnorePostpaidcheckbox() {
		return ignorePostpaidcheckbox;
	}
	public void setIgnorePostpaidcheckbox(boolean ignorePostpaidcheckbox) {
		this.ignorePostpaidcheckbox = ignorePostpaidcheckbox;
	}
	public boolean isIgnoreFutherPostpaidcheckbox() {
		return ignoreFutherPostpaidcheckbox;
	}
	public void setIgnoreFutherPostpaidcheckbox(boolean ignoreFutherPostpaidcheckbox) {
		this.ignoreFutherPostpaidcheckbox = ignoreFutherPostpaidcheckbox;
	}
	public boolean isChinaMobile() {
		return isChinaMobile;
	}
	public void setChinaMobile(boolean isChinaMobile) {
		this.isChinaMobile = isChinaMobile;
	}
	public String getMnpExtendAuthInd() {
		return mnpExtendAuthInd;
	}
	public void setMnpExtendAuthInd(String mnpExtendAuthInd) {
		this.mnpExtendAuthInd = mnpExtendAuthInd;
	}
	public boolean isPrepaidSimInd() {
		return prepaidSimInd;
	}
	public void setPrepaidSimInd(boolean prepaidSimInd) {
		this.prepaidSimInd = prepaidSimInd;
	}
	
	public boolean isFutherPrepaidSimInd() {
		return futherPrepaidSimInd;
	}
	public void setFutherPrepaidSimInd(boolean futherPrepaidSimInd) {
		this.futherPrepaidSimInd = futherPrepaidSimInd;
	}
	public String getOpssInd() {
		return opssInd;
	}
	public void setOpssInd(String opssInd) {
		this.opssInd = opssInd;
	}
	public String getStarterPack() {
		return starterPack;
	}
	public void setStarterPack(String starterPack) {
		this.starterPack = starterPack;
	}
	public boolean getCheckIsWhiteList() {
		return checkIsWhiteList;
	}
	public void setCheckIsWhiteList(boolean checkIsWhiteList) {
		this.checkIsWhiteList = checkIsWhiteList;
	}
	public String getCustomerTier() {
		return customerTier;
	}
	public void setCustomerTier(String customerTier) {
		this.customerTier = customerTier;
	}
}

