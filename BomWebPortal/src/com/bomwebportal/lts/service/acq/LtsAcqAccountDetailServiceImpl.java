package com.bomwebportal.lts.service.acq;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.lts.dao.acq.LtsAcqAccountDetailDAO;
import com.bomwebportal.lts.util.LtsConstant;

public class LtsAcqAccountDetailServiceImpl implements LtsAcqAccountDetailService {
	
	protected final Log logger = LogFactory.getLog(getClass());

	private LtsAcqAccountDetailDAO ltsAcqAccountDetailDAO;
	
	public void updateDummyAcctNum(String sbOrderId, String acctNum, String userId) {
		try {
			ltsAcqAccountDetailDAO.updateDummyAcctNum(sbOrderId, acctNum, userId);
		} catch (DAOException de) {
			logger.error(de);
			throw new AppRuntimeException(de);
		}		
	}
	
	public String getBomDummyAcctNum() {
		try {
			String seq = this.ltsAcqAccountDetailDAO.getBomDummyAcctNumSeq();
			return LtsConstant.DUMMY_ACCT_NUM_PREFIX + StringUtils.leftPad(seq, 13, "0");
		} catch (DAOException de) {
			logger.error("Fail in LtsAcqAccountDetailService.getDummyAcctNum()", de);
			throw new AppRuntimeException(de);
		}		
	}

	/**
	 * @return the ltsAcqAccountDetailDAO
	 */
	public LtsAcqAccountDetailDAO getLtsAcqAccountDetailDAO() {
		return ltsAcqAccountDetailDAO;
	}

	/**
	 * @param ltsAcqAccountDetailDAO the ltsAcqAccountDetailDAO to set
	 */
	public void setLtsAcqAccountDetailDAO(
			LtsAcqAccountDetailDAO ltsAcqAccountDetailDAO) {
		this.ltsAcqAccountDetailDAO = ltsAcqAccountDetailDAO;
	}

}
