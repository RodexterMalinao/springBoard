package com.bomwebportal.lts.dao.acq;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.bomwebportal.dao.BaseDAO;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.lts.dto.acq.BomwebDsOrderInfoDTO;

public class LtsDsOrderDAO extends BaseDAO {
	
	public void updateDsInfo(String pQcCallTimePeriod, String pWaiveQcCd, String pOrderId, String pUserId) throws DAOException {	
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE BOMWEB_ORDER_LTS ");
		sql.append("SET QC_CALL_TIME = ?, WAIVE_QC_CD = ?, LAST_UPD_BY = ?, LAST_UPD_DATE = sysdate ");
		sql.append("WHERE ORDER_ID = ? ");
		try {			
			simpleJdbcTemplate.update(sql.toString(), pQcCallTimePeriod, pWaiveQcCd, pUserId, pOrderId);
		} catch (Exception e) {
			logger.error("Exception caught in updateDsInfo():", e);
			throw new DAOException(e.getMessage(), e);
		}		
	}
	
	public void updatePrepayInfo(String pOrderId, String pPrepayType, String pBillType, String pMerchantCode, String pShopCd, String pTranCode, String pUserId, String pPaymentStatus, String pPaymentSettleDate) throws DAOException {	
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE BOMWEB_ORDER_LTS_PREPAY ");
		sql.append("SET BILL_TYPE = ?, MERCAHNT_CODE = ?, SHOP_CODE = ?, TRAN_CODE = ?, LAST_UPD_BY = ?, PAYMENT_STATUS = ?, LAST_UPD_DATE = sysdate, PREPAY_DATE = TO_DATE(?,'yyyymmddHH24MiSS') ");
		sql.append("WHERE ORDER_ID = ? and PREPAY_TYPE = ? ");
		try {			
			simpleJdbcTemplate.update(sql.toString(), pBillType, pMerchantCode, pShopCd, pTranCode, pUserId, pPaymentStatus, pPaymentSettleDate, pOrderId, pPrepayType);
		} catch (Exception e) {
			logger.error("Exception caught in updatePrepayInfo():", e);
			throw new DAOException(e.getMessage(), e);
		}		
	}
	
	public List<String> isDuplicatedShopCodeTranCode(String pOrderId, String pShopCode, String pTranCode, String pPrepaySettleDate) throws DAOException {
		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getString("ORDER_ID");
			}
		};
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT ORDER_ID FROM BOMWEB_ORDER_LTS_PREPAY ");
		sql.append("WHERE ORDER_ID != ? AND SHOP_CODE = ? AND TRAN_CODE = ? AND PREPAY_DATE = TO_DATE(?,'yyyymmddHH24MiSS')");
		try {			
			List<String> result = simpleJdbcTemplate.query(sql.toString(), mapper, pOrderId, pShopCode, pTranCode, pPrepaySettleDate);
			
			if(result != null && result.size() > 0){
				return result;
			}
			else{
				return null;
			}
		} catch (Exception e) {
			logger.error("Exception caught in isDuplicatedShopCode():", e);
			throw new DAOException(e.getMessage(), e);
		}		
	}
	
	public String canReuseShopCodeTranCode(String pAccNo, String pOrderId, String pCancelOrderDate)  throws DAOException {
		ParameterizedRowMapper<ReusePayDTO> mapper = new ParameterizedRowMapper<ReusePayDTO>() {
			public ReusePayDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				ReusePayDTO tempTempReusePayDTO = new ReusePayDTO();
				
				tempTempReusePayDTO.setAcct_no(rs.getString("ACCT_NO"));
				tempTempReusePayDTO.setOrder_id(rs.getString("ORDER_ID"));
				tempTempReusePayDTO.setSb_status(rs.getString("SB_STATUS"));
				
				return tempTempReusePayDTO;
				//return rs.getString("SB_STATUS").equalsIgnoreCase("C")?(rs.getString("ACCT_NO").equalsIgnoreCase(pAccNo)?rs.getString("ORDER_ID"):"DIFF_ACC"):"NOT_CANCELLED";
			}
		};
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT distinct bolp.ORDER_ID, bols.SB_STATUS, bolp.ACCT_NO FROM BOMWEB_ORDER_LTS_PREPAY bolp, BOMWEB_ORDER_LATEST_STATUS bols ");
		sql.append("WHERE bolp.ORDER_ID = bols.ORDER_ID and bolp.ORDER_ID = ? AND bols.LAST_HIST_DATE > TO_DATE(?,'yyyymmddHH24MiSS') AND bolp.PAYMENT_STATUS = 'S' ");
		try {			
			List<ReusePayDTO> result = simpleJdbcTemplate.query(sql.toString(), mapper, pOrderId, pCancelOrderDate);
			
			if(result != null && result.size() == 1){
				//if(!result.get(0).equalsIgnoreCase("NOT_CANCELLED"))
				if(result.get(0).getSb_status()!=null && result.get(0).getSb_status().equalsIgnoreCase("C"))
				{
					if(result.get(0).getAcct_no()!=null && result.get(0).getAcct_no().equalsIgnoreCase(pAccNo))
						return result.get(0).getOrder_id();
					else
						return "DIFF_ACC";
				}
				else 
					return null;
			}
			else{
				return null;
			}
		} catch (Exception e) {
			logger.error("Exception caught in canReuseShopCodeTranCode():", e);
			throw new DAOException(e.getMessage(), e);
		}		
	}
	
	private class ReusePayDTO implements Serializable {
		
		private String order_id;
		private String acct_no;
		private String sb_status;
		
		public String getOrder_id() {
			return order_id;
		}
		public void setOrder_id(String order_id) {
			this.order_id = order_id;
		}
		public String getAcct_no() {
			return acct_no;
		}
		public void setAcct_no(String acct_no) {
			this.acct_no = acct_no;
		}
		public String getSb_status() {
			return sb_status;
		}
		public void setSb_status(String sb_status) {
			this.sb_status = sb_status;
		}		
	}
	
}
