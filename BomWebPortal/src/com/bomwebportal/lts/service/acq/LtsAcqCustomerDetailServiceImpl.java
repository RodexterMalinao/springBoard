package com.bomwebportal.lts.service.acq;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.lts.dao.acq.LtsAcqCustomerDetailDAO;
import com.bomwebportal.lts.util.LtsConstant;

public class LtsAcqCustomerDetailServiceImpl implements LtsAcqCustomerDetailService { 
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	private LtsAcqCustomerDetailDAO ltsAcqCustomerDetailDAO;
	
	public void updateDummyCustNum(String sbOrderId, String custNum, String userId) {
		try {
			ltsAcqCustomerDetailDAO.updateDummyCustNum(sbOrderId, custNum, userId);
		} catch (DAOException de) {
			logger.error(de);
			throw new AppRuntimeException(de);
		}
	}
	
	public String getBomDummyCustNum() {
		try {
			String seq = this.ltsAcqCustomerDetailDAO.getBomDummyCustNumSeq();
			return LtsConstant.DUMMY_CUST_NUM_PREFIX + StringUtils.leftPad(seq, 7, "0");
		} catch (DAOException de) {
			logger.error("Fail in LtsAcqCustomerDetailService.getDummyCustNum()", de);
			throw new AppRuntimeException(de);
		}		
	}

	/**
	 * @return the ltsAcqCustomerDetailDAO
	 */
	public LtsAcqCustomerDetailDAO getLtsAcqCustomerDetailDAO() {
		return ltsAcqCustomerDetailDAO;
	}

	/**
	 * @param ltsAcqCustomerDetailDAO the ltsAcqCustomerDetailDAO to set
	 */
	public void setLtsAcqCustomerDetailDAO(
			LtsAcqCustomerDetailDAO ltsAcqCustomerDetailDAO) {
		this.ltsAcqCustomerDetailDAO = ltsAcqCustomerDetailDAO;
	}
	
}
