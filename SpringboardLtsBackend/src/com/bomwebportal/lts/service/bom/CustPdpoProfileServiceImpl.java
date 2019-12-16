package com.bomwebportal.lts.service.bom;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.lts.dao.bom.BCustDataPrivacyLtsDAO;
import com.bomwebportal.lts.dto.order.BillingAddressLtsDTO;
import com.bomwebportal.lts.dto.order.CustOptOutInfoLtsDTO;
import com.bomwebportal.lts.dto.profile.CustPdpoProfileDTO;
import com.bomwebportal.lts.util.LtsBackendConstant;

public class CustPdpoProfileServiceImpl implements CustPdpoProfileService {
	
	protected final Log logger = LogFactory.getLog(getClass());
	private BCustDataPrivacyLtsDAO bCustDataPrivacyLtsDAO;

	public CustPdpoProfileDTO[] getCustDataPrivacyInfo(String custNum, String systemId) {
		try {
			return bCustDataPrivacyLtsDAO.getCustDataPrivacyInfo(custNum, systemId);	
		}
		catch (DAOException e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
			throw new AppRuntimeException(e.getMessage());
		}
	}
	
	public String updateCustDataPrivayInfo(CustOptOutInfoLtsDTO custOptOutInfo, BillingAddressLtsDTO billAddress, String userId) {
		try {
			String fullBillAddress = null;
			if (billAddress != null 
					&& LtsBackendConstant.BILLING_ADDR_ACTION_NEW.equals(billAddress.getAction())
					&& StringUtils.equals("Y", billAddress.getInstantUpdateInd())) {
				fullBillAddress = billAddress.getFullBillAddr();
			}
			return bCustDataPrivacyLtsDAO.updateCustDataPrivayInfo(custOptOutInfo, fullBillAddress, userId, true);
		}
		catch (DAOException e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
			throw new AppRuntimeException(e.getMessage());
		}
	}
	
	public String updateCustDataPrivayInfoWithLob(CustOptOutInfoLtsDTO custOptOutInfo, BillingAddressLtsDTO billAddress, String userId, String lob) {
		try {
			String fullBillAddress = null;
			if (billAddress != null 
					&& LtsBackendConstant.BILLING_ADDR_ACTION_NEW.equals(billAddress.getAction())
					&& StringUtils.equals("Y", billAddress.getInstantUpdateInd())) {
				fullBillAddress = billAddress.getFullBillAddr();
			}
			return bCustDataPrivacyLtsDAO.updateCustDataPrivayInfoWithLob(custOptOutInfo, fullBillAddress, userId, true, lob);
		}
		catch (DAOException e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
			throw new AppRuntimeException(e.getMessage());
		}
	}
	
	public BCustDataPrivacyLtsDAO getbCustDataPrivacyLtsDAO() {
		return bCustDataPrivacyLtsDAO;
	}

	public void setbCustDataPrivacyLtsDAO(
			BCustDataPrivacyLtsDAO bCustDataPrivacyLtsDAO) {
		this.bCustDataPrivacyLtsDAO = bCustDataPrivacyLtsDAO;
	}
	
}
