package com.bomwebportal.mob.ccs.dto.ui;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

public class MRTUI implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 624295873587941593L;

	public static String ERROR_PATH = "mnpInd";

    private String orderId;
    /**
     * <p>C is 1C2N</p>
     * <p>A is New Mobile Number And MNP</p>
     * <p>O is New	Mobile Number or MNP</p>
     */
    private String mrtInd;
    private String mnpInd;
    private String msisdnLvl;
    private String mobMsisdn;
    private String serviceReqDateStr;
    private String mnpMsisdn;
    private String mnpTicketNum;
    private String cutOverDateStr;
    private String custName;
    private String docNum;
    private String unicomMobGrade;
    private String cityCd;
    private String unicomMobMsisdn;
    private String goldenInd;
    private String unicomMobStatus;
    private String chinaInd;
    private String actionType;
    private String cutOverTime;
    
    
    private String numType; //Dennis MIP3
    private boolean ignorePostpaidcheckbox=false;
    private String dno;
    private String rno;
    private String actualDno;
    
    private String simType;
    
    //DENNIS201606
  	private String mnpExtendAuthInd;
  	
  	private boolean prepaidSimInd = false;
  	
  	private String opssInd;
  	private String starterPack;
  	
  	//golden number Auth VAS offer
	private String showGoldenNumAuth = "N";
	private String byPassGoldenNum = "N";
    
    public String getNumType() {
		return numType;
	}
	public void setNumType(String numType) {
		this.numType = numType;
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
	
	
	   
    private HttpSession session;
    
    //add by Eliot 20120320
    private int contractPeriod;
    private int rpRecurChange;
    
 
    
    //add by Eliot 20120418
    private Date appDate;
    //add by Eliot 20120515
    private boolean specialNumInd;
    
    //add by Vincy 20141030
    private int grossPlanFee;

    private boolean byPassValidation = false;// add by wilson 20120301, for
					     // draft-pre-pend order
    /**
     * Object retrieved from session
     */
    private MRTUI previousMrtUi;
    /**
     * if this DTO is amend or alter
     */
    private boolean mnpAmend;
    /**
     * if Service Request Date is changed
     */
    private boolean mrtAmend;
    
    //CSL origActDate 
   	private String origActDateStr;

	public String getOrigActDateStr() {
  		return origActDateStr;
  	}

  	public void setOrigActDateStr(String origActDateStr) {
  		this.origActDateStr = origActDateStr;
  	}

    private List<String> msiServiceDateList;
    private List<String> multiSimDnoList;
    private List<String> multiSimOpssInd;
    private List<String> multiSimMemberOrderTypeList;

	/**
	 * @return the mnpAmend
	 */
	public boolean isMnpAmend() {
		return mnpAmend;
	}

	/**
	 * @param mnpAmend the mnpAmend to set
	 */
	public void setMnpAmend(boolean mnpAmend) {
		this.mnpAmend = mnpAmend;
	}

	/**
	 * @return the mrtAmend
	 */
	public boolean isMrtAmend() {
		return mrtAmend;
	}

	/**
	 * @param mrtAmend the mrtAmend to set
	 */
	public void setMrtAmend(boolean mrtAmend) {
		this.mrtAmend = mrtAmend;
	}

	/**
	 * @return the previousMrtUi
	 */
	public MRTUI getPreviousMrtUi() {
		return previousMrtUi;
	}

	/**
	 * @param previousMrtUi the previousMrtUi to set
	 */
	public void setPreviousMrtUi(MRTUI previousMrtUi) {
		this.previousMrtUi = previousMrtUi;
	}

	public boolean isByPassValidation() {
	return byPassValidation;
    }

    public void setByPassValidation(boolean byPassValidation) {
	this.byPassValidation = byPassValidation;
    }

    public String getOrderId() {
	return orderId;
    }

    public void setOrderId(String orderId) {
	this.orderId = orderId;
    }
    /**
     * <p>C is 1C2N</p>
     * <p>A is New Mobile Number And MNP</p>
     * <p>O is New	Mobile Number or MNP</p>
     */
    public String getMrtInd() {
	return mrtInd;
    }

    public void setMrtInd(String mrtInd) {
	this.mrtInd = mrtInd;
    }

    public String getMnpInd() {
	return mnpInd;
    }

    public void setMnpInd(String mnpInd) {
	this.mnpInd = mnpInd;
    }

    public String getMsisdnLvl() {
	return msisdnLvl;
    }

    public void setMsisdnLvl(String msisdnLvl) {
	this.msisdnLvl = msisdnLvl;
    }

    public String getMobMsisdn() {
	return mobMsisdn;
    }

    public void setMobMsisdn(String mobMsisdn) {
	this.mobMsisdn = mobMsisdn;
    }

    public String getServiceReqDateStr() {
	return serviceReqDateStr;
    }

    public void setServiceReqDateStr(String serviceReqDateStr) {
	this.serviceReqDateStr = serviceReqDateStr;
    }

    public String getMnpMsisdn() {
	return mnpMsisdn;
    }

    public void setMnpMsisdn(String mnpMsisdn) {
	this.mnpMsisdn = mnpMsisdn;
    }

    public String getMnpTicketNum() {
	return mnpTicketNum;
    }

    public void setMnpTicketNum(String mnpTicketNum) {
	this.mnpTicketNum = mnpTicketNum;
    }

    public String getCutOverDateStr() {
	return cutOverDateStr;
    }

    public void setCutOverDateStr(String cutOverDateStr) {
	this.cutOverDateStr = cutOverDateStr;
    }

    public String getCustName() {
	return custName;
    }

    public void setCustName(String custName) {
	this.custName = custName;
    }

    public String getDocNum() {
	return docNum;
    }

    public void setDocNum(String docNum) {
	this.docNum = docNum;
    }

//    public String getGoldenSuffix() {
//	return goldenSuffix;
//    }
//
//    public void setGoldenSuffix(String goldenSuffix) {
//	this.goldenSuffix = goldenSuffix;
//    }
//
//    public String getGoldenMsisdn() {
//	return goldenMsisdn;
//    }
//
//    public void setGoldenMsisdn(String goldenMsisdn) {
//	this.goldenMsisdn = goldenMsisdn;
//    }
//
//    public String getGoldenReserveId() {
//	return goldenReserveId;
//    }
//
//    public void setGoldenReserveId(String goldenReserveId) {
//	this.goldenReserveId = goldenReserveId;
//    }

    public String getUnicomMobGrade() {
	return unicomMobGrade;
    }

    public void setUnicomMobGrade(String unicomMobGrade) {
	this.unicomMobGrade = unicomMobGrade;
    }

    public String getCityCd() {
	return cityCd;
    }

    public void setCityCd(String cityCd) {
	this.cityCd = cityCd;
    }

    public String getUnicomMobMsisdn() {
	return unicomMobMsisdn;
    }

    public void setUnicomMobMsisdn(String unicomMobMsisdn) {
	this.unicomMobMsisdn = unicomMobMsisdn;
    }


    /**
	 * @return the goldenInd
	 */
	public String getGoldenInd() {
		return goldenInd;
	}

	/**
	 * @param goldenInd the goldenInd to set
	 */
	public void setGoldenInd(String goldenInd) {
		this.goldenInd = goldenInd;
	}

	public String getUnicomMobStatus() {
	return unicomMobStatus;
    }

    public void setUnicomMobStatus(String unicomMobStatus) {
	this.unicomMobStatus = unicomMobStatus;
    }

    public String getChinaInd() {
	return chinaInd;
    }

    public void setChinaInd(String chinaInd) {
	this.chinaInd = chinaInd;
    }

    public String getActionType() {
	return actionType;
    }

    public void setActionType(String actionType) {
	this.actionType = actionType;
    }

    public String getCutOverTime() {
	return cutOverTime;
    }

    public void setCutOverTime(String cutOverTime) {
	this.cutOverTime = cutOverTime;
    }

//    public String getGoldenRequestStatus() {
//	return goldenRequestStatus;
//    }
//
//    public void setGoldenRequestStatus(String goldenRequestStatus) {
//	this.goldenRequestStatus = goldenRequestStatus;
//    }
//
//    public String getGoldenServiceReqDateStr() {
//	return goldenServiceReqDateStr;
//    }
//
//    public void setGoldenServiceReqDateStr(String goldenServiceReqDateStr) {
//	this.goldenServiceReqDateStr = goldenServiceReqDateStr;
//    }
//
//    public String getGoldenMsisdnLvl() {
//	return goldenMsisdnLvl;
//    }
//
//    public void setGoldenMsisdnLvl(String goldenMsisdnLvl) {
//	this.goldenMsisdnLvl = goldenMsisdnLvl;
//    }

    public int getContractPeriod() {
	return contractPeriod;
    }

    public void setContractPeriod(int contractPeriod) {
	this.contractPeriod = contractPeriod;
    }

    public int getRpRecurChange() {
	return rpRecurChange;
    }

    public void setRpRecurChange(int rpRecurChange) {
	this.rpRecurChange = rpRecurChange;
    }

    public Date getAppDate() {
        return appDate;
    }

    public void setAppDate(Date appDate) {
        this.appDate = appDate;
    }

	public boolean isSpecialNumInd() {
		return specialNumInd;
	}

	public void setSpecialNumInd(boolean specialNumInd) {
		this.specialNumInd = specialNumInd;
	}


	
	public HttpSession getSession() {
		return session;
	}

	public void setSession(HttpSession session) {
		this.session = session;
	}
    
	public List<String> getMsiServiceDateList() {
		return msiServiceDateList;
	}

	public void setMsiServiceDateList(List<String> msiServiceDateList) {
		this.msiServiceDateList = msiServiceDateList;
	}
	public List<String> getMultiSimDnoList() {
		return multiSimDnoList;
	}
	public void setMultiSimDnoList(List<String> multiSimDnoList) {
		this.multiSimDnoList = multiSimDnoList;
	}
	public List<String> getMultiSimOpssInd() {
		return multiSimOpssInd;
	}
	public void setMultiSimOpssInd(List<String> multiSimOpssInd) {
		this.multiSimOpssInd = multiSimOpssInd;
	}
	public List<String> getMultiSimMemberOrderTypeList() {
		return multiSimMemberOrderTypeList;
	}
	public void setMultiSimMemberOrderTypeList(List<String> multiSimMemberOrderTypeList) {
		this.multiSimMemberOrderTypeList = multiSimMemberOrderTypeList;
	}
	public void setGrossPlanFee(int grossPlanFee) {
		this.grossPlanFee = grossPlanFee;
	}
	public int getGrossPlanFee() {
		return grossPlanFee;
	}
	public boolean isIgnorePostpaidcheckbox() {
		return ignorePostpaidcheckbox;
	}
	public void setIgnorePostpaidcheckbox(boolean ignorePostpaidcheckbox) {
		this.ignorePostpaidcheckbox = ignorePostpaidcheckbox;
	}
	public String getDno() {
		return dno;
	}
	public void setDno(String dno) {
		this.dno = dno;
	}
	public String getRno() {
		return rno;
	}
	public void setRno(String rno) {
		this.rno = rno;
	}
	public String getActualDno() {
		return actualDno;
	}
	public void setActualDno(String actualDno) {
		this.actualDno = actualDno;
	}
	public String getSimType() {
		return simType;
	}
	public void setSimType(String simType) {
		this.simType = simType;
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
	public Map<String, Object> getObjectsMap() {
		return objectsMap;
	}
	public void setObjectsMap(Map<String, Object> objectsMap) {
		this.objectsMap = objectsMap;
	}
	public String getShowGoldenNumAuth() {
		return showGoldenNumAuth;
	}
	public void setShowGoldenNumAuth(String showGoldenNumAuth) {
		this.showGoldenNumAuth = showGoldenNumAuth;
	}
	public String getByPassGoldenNum() {
		return byPassGoldenNum;
	}
	public void setByPassGoldenNum(String byPassGoldenNum) {
		this.byPassGoldenNum = byPassGoldenNum;
	}
}