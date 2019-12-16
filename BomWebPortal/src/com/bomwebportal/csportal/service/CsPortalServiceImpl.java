package com.bomwebportal.csportal.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URLConnection;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bomwebportal.csportal.dao.OnlineCSPortalTxnDAO;
import com.bomwebportal.csportal.object.CareCashOptInArq;
import com.bomwebportal.csportal.object.CsldInqArq;
import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.dto.CustomerProfileDTO;
import com.bomwebportal.dto.OrderMobDTO;
import com.bomwebportal.util.ServerUtils;
import com.google.gson.Gson;

public class CsPortalServiceImpl implements CsPortalService {
	protected final Log logger = LogFactory.getLog(getClass());

	private String sysId;
	private String sysPwd;
	private String idckUrl;
	private String careCashOptUrl;
	
	private OnlineCSPortalTxnDAO onlineCSPortalTxnDao;
	
	public OnlineCSPortalTxnDAO getOnlineCSPortalTxnDao() {
		return onlineCSPortalTxnDao;
	}

	public void setOnlineCSPortalTxnDao(OnlineCSPortalTxnDAO onlineCSPortalTxnDao) {
		this.onlineCSPortalTxnDao = onlineCSPortalTxnDao;
	}

	public String getSysId() {
		return sysId;
	}

	public void setSysId(String sysId) {
		this.sysId = sysId;
	}

	public String getSysPwd() {
		return sysPwd;
	}

	public void setSysPwd(String sysPwd) {
		this.sysPwd = sysPwd;
	}

	public String getIdckUrl() {
		return idckUrl;
	}

	public void setIdckUrl(String idckUrl) {
		this.idckUrl = idckUrl;
	}
	
	public String getCareCashOptUrl() {
		return careCashOptUrl;
	}

	public void setCareCashOptUrl(String careCashOptUrl) {
		this.careCashOptUrl = careCashOptUrl;
	}

	public CsldInqArq idCheck(String idDocType, String idDocNum, String myHktId, String theClubId) {
		URLConnection rConn;
		OutputStreamWriter rOSW;
		BufferedReader rBR;
		String rStr;
		StringBuffer rSB;
		CsldInqArq rArq;

		rBR = null;
		rOSW = null;

		try {
			Gson gson = new Gson();

			rArq = new CsldInqArq();
			rArq.apiTy = "CSLD_IDCK";
			rArq.sysId = sysId;
			rArq.sysPwd = sysPwd;

			rArq.iDocTy = idDocType;
			rArq.iDocNum = idDocNum;

			rArq.iLi4MyHkt = (myHktId == null ? null : myHktId.toLowerCase());
			rArq.iLi4Club = (theClubId == null ? null : theClubId.toLowerCase());
			
			rConn = ServerUtils.getDummySSLConnection(idckUrl);
			logger.debug(idckUrl);
			rConn.setDoOutput(true);

			rOSW = new OutputStreamWriter(rConn.getOutputStream(), "UTF-8");
			rOSW.write(gson.toJson(rArq));
			logger.debug("Calling CS Portal API: " + gson.toJson(rArq));
			rOSW.flush();

			rBR = new BufferedReader(new InputStreamReader(rConn.getInputStream(), "UTF-8"));
			rSB = new StringBuffer();

			while ((rStr = rBR.readLine()) != null) {
				logger.debug(rStr);
				rSB.append(rStr);
			}

			rArq = (CsldInqArq) gson.fromJson(rSB.toString(), CsldInqArq.class);
			
			logger.debug("API RESPONSE: " + rArq.reply);
			onlineCSPortalTxnDao.insertTxn("", rArq.apiTy, rArq.sysId, rArq.reply, rSB.toString(), "SYSCSP");
			return rArq;
		} catch (RuntimeException rEx) {
			logger.error(rEx);
			throw rEx;
		} catch (Exception rEx) {
			logger.error(rEx);
			throw new RuntimeException(rEx);
		} finally {
			try {
				if (rBR != null)
					rBR.close();
				if (rOSW != null)
					rOSW.close();
			} catch (Exception rIgnEx) {
			}
		}
	}
	
	static String getSalesChannelCd(String orderId) {
		if (orderId.startsWith("R")) {
			return "RET";
		} else if (orderId.startsWith("CSBO")) {
			return "SBO";
		} else if (orderId.startsWith("C")) {
			return "CCS";
		} else if (orderId.startsWith("D")) {
			return "DS";
		} else if (orderId.startsWith("P")) {
			return "PT";
		}
		return "";
	}
	
	static String getContactMobileNum(String contactPhone, String msisdn) {
		String result = msisdn;

		for (String prefix : new String[] { /*"3",*/ "5", "6", "9" }) { //remove 3 prefix
			if (contactPhone.startsWith(prefix)) {
				return contactPhone;
			}
		}
		return result;
	}
}
