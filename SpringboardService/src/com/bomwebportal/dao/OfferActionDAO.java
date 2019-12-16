package com.bomwebportal.dao;

import com.bomwebportal.exception.DAOException;

public class OfferActionDAO extends BaseDAO {

	private static final String SQL_UPDATE_IMS_OFFER_PENALTY = 
		"update bomweb_subc_offer_ims " +
		"set penalty_waive_ind = ?, penalty_handling = ?, last_upd_by = ?, last_upd_date = sysdate " +
		"where order_id = ? and penalty_handling = 'PA'";
	
	private static final String SQL_UPDATE_LTS_ITEM_PENALTY = 
		"update bomweb_subc_item_lts " +
		"set penalty_waive_ind = ?, penalty_handling = ?, last_upd_by = ?, last_upd_date = sysdate "+
		"where order_id = ? and penalty_handling = 'PA'";
	
	private static final String SQL_UPDATE_APPROVED_IND = 
		"update bomweb_order_service " +
		"set approved_ind = ?, last_upd_by = ?, last_upd_date = sysdate " +
		"where order_id = ?";
	
	
	public void setImsPenaltyHandling(String pOrderId, String pPenaltyHandling, String pWaivePenaltyInd, String pUser) throws DAOException {
		
		try {
			simpleJdbcTemplate.update(SQL_UPDATE_IMS_OFFER_PENALTY, pWaivePenaltyInd, pPenaltyHandling, pUser, pOrderId);
		} catch (Exception e) {
			logger.error("Exception caught in setImsPenaltyHandling()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public void setLtsPenaltyHandling(String pOrderId, String pPenaltyHandling, String pWaivePenaltyInd, String pUser) throws DAOException {
		
		try {
			simpleJdbcTemplate.update(SQL_UPDATE_LTS_ITEM_PENALTY, pWaivePenaltyInd, pPenaltyHandling, pUser, pOrderId);
		} catch (Exception e) {
			logger.error("Exception caught in setLtsPenaltyHandling()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public void setApprovedInd(String pOrderId, String pApprovedInd, String pUser) throws DAOException {
		
		try {
			simpleJdbcTemplate.update(SQL_UPDATE_APPROVED_IND, pApprovedInd, pUser, pOrderId);
		} catch (Exception e) {
			logger.error("Exception caught in setApprovedInd()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
}