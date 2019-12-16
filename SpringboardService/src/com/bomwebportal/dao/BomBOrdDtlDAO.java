package com.bomwebportal.dao;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Transactional;

import com.bomwebportal.exception.DAOException;

public class BomBOrdDtlDAO extends BaseDAO {
	
	protected final Log logger = LogFactory.getLog(getClass());

	private static final String updateSbIdToBomSQL = 
	"	UPDATE B_ORD_DTL "+
	"	   SET agreement_num = ?, "+
	"	       last_update_by = 'SB', "+
	"	       last_update_date = SYSDATE "+
	"	 WHERE OC_ID = ? " +
	"      AND TYPE_OF_SRV = 'TEL' " +
	"      AND AGREEMENT_NUM IS NULL ";

	@Transactional(rollbackFor={Exception.class})
	public void updateSbIdToBom(String pSbId, String pOcId) throws DAOException{
		logger.info("Update BOM (B_ORD_DTL)");
		logger.debug("update B_ORD_DTL.agreement_num " + pSbId + " with ocid = " + pOcId);
		
		try {
			simpleJdbcTemplate.update(updateSbIdToBomSQL, pSbId, pOcId);
		
		} catch (Exception e) {
			logger.error("Exception caught in updateSbIdToBom()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
}