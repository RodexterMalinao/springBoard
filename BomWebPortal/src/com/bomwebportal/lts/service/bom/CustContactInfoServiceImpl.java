package com.bomwebportal.lts.service.bom;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.lts.dao.bom.BCustContactInfoLtsDAO;
import com.bomwebportal.lts.dto.order.ContactLtsDTO;

public class CustContactInfoServiceImpl implements CustContactInfoService {
	
	protected final Log logger = LogFactory.getLog(getClass());
	private BCustContactInfoLtsDAO bCustContactInfoLtsDAO;

	
	public void updateCustContactInfo(String orderId, ContactLtsDTO contactLtsDTO, String userId, String salesChannel) {
		try {
			bCustContactInfoLtsDAO.updateCustContactInfo(orderId, contactLtsDTO, userId, salesChannel);
		}
		catch (DAOException e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
			throw new AppRuntimeException(e.getMessage());
		}
	}


	public BCustContactInfoLtsDAO getbCustContactInfoLtsDAO() {
		return bCustContactInfoLtsDAO;
	}

	public void setbCustContactInfoLtsDAO(
			BCustContactInfoLtsDAO bCustContactInfoLtsDAO) {
		this.bCustContactInfoLtsDAO = bCustContactInfoLtsDAO;
	}
	
	
}
