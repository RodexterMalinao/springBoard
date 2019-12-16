package com.bomwebportal.lts.dao.order;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.dao.CannotAcquireLockException;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.bomwebportal.dao.BaseDAO;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.lts.dto.order.OrderStatusSynchDTO;
import com.bomwebportal.lts.util.LtsConstant;

public class OrderStatusSynchDAO extends BaseDAO {
	
	private static final SimpleDateFormat dateFormatterYYYYMMDD = new SimpleDateFormat("yyyyMMdd");
		
	private static final String SQL_UPDATE_SB_SRV_REQ_DATE = 
	    "update BOMWEB_ORDER_SERVICE " +
	    "set bom_srv_req_date = to_date(?,'YYYYMMDD'), " + 
		"    last_upd_by   = ?, " +
		"    last_upd_date = sysdate " +
		"where order_id = ? " +
		"and   dtl_id   = ? ";
	
	private static final String SQL_UPDATE_NN_LEGACY_ORD = 
		    "update BOMWEB_ORDER_SERVICE_LTS " +
		    "set srv_nn = ?, " +
			"    legacy_ord_num   = ?, " +
			"    legacy_actv_seq  = ?, " +		    		
			"    last_upd_by   = ?, " +
			"    last_upd_date = sysdate " +
			"where order_id = ? " +
			"and   dtl_id   = ? ";
		
	private static final String SQL_UPDATE_SB_OCID = 
		"update BOMWEB_ORDER " +
		"set ocid    = ?, " +
		"    err_cd  = ?, " +
		"    err_msg = ?, " +
		"    last_update_by   = ?, " +
		"    last_update_date = sysdate " +
		"where order_id = ?";	
	
	private static final String SQL_TOUCH_SB_STATUS = 
		"update BOMWEB_ORDER_LATEST_STATUS " +
		"set last_upd_date = sysdate " +
		"where rowid = ?";	
	
	private static final String SQL_UPDATE_SB_STATUS = 
		"update BOMWEB_ORDER_LATEST_STATUS " +
		"set sb_status     = ?, " +
		"    bom_status    = ?, " +
		"    legacy_status = ?, " +
		"    last_upd_by   = ?, " +
		"    wq_type       = ?, " +
		"    rea_cd        = ?, " +
		"    l1_rea_cd     = ?, " +
		"    l1_ord_status = ?, " +
		"    last_hist_date = sysdate, " +
		"    last_upd_date  = sysdate  " +
		"where rowid = ?";
	
	private static final String SQL_GET_RELATED_TEL_LINES = 
		"select" +
		"	bwols.rowid," +
		"	bwols.order_id," +
		"	bwols.dtl_id," +
		"	bwols.sb_status," +
		"	bwols.bom_status," +
		"	bwols.legacy_status," +
		"	bwols.rea_cd," +
		"	bwols.l1_rea_cd," +
		"	bwols.l1_ord_status," +
		"	bwos.type_of_srv," +
		"	bwos.srv_num,bwos.order_type," +
		"	bwos.cc_service_id," +
		"	bwos.cc_service_mem_num," +
		"   bwo.err_cd,  " +
		"   bwo.err_msg, " +		
		"	bwo.shop_cd," +
		"   bwos.bom_srv_req_date, " +
		"   bwo.back_date_ind, " +		
	    "   bwosl.legacy_ord_num, " +
	    "   bwosl.legacy_actv_seq, " +
	    "   bwosl.srv_nn, " +
	    "   bwosl.dn_source, " +		
	    "   null is_pipb, " +
	    "   bwos.from_prod, " +	    
	    "   bwos.to_prod, " +
		"	bwo.create_by " +
		"from" +
		"	bomweb_order_latest_status bwols," +
		"	bomweb_order_service bwos," +
	    "   bomweb_order_service_lts bwosl," +		
		"	bomweb_order bwo " +
		"where  bwo.order_id  = ? " +
		" and   bwo.order_id  = bwols.order_id " +
		" and   bwos.order_id = bwols.order_id " +
		" and   bwos.dtl_id   = bwols.dtl_id " +
	    " and   bwosl.order_id= bwos.order_id " + 
	    " and   bwosl.dtl_id  = bwos.dtl_id " +		
		" and   bwos.type_of_srv != 'IMS'";
	
	/*
	 * (non-pipb) N=14
	 * Orders that is 'E' or 'F' or 'P' and not N days old, OR
	 * (removed)Orders that is 'B' and not 2*N days old if legacy status is not yet 'D' or '01', OR
	 * (removed)Orders that is 'B' and not updated within the last 6 hours day if  'D' or '01'(i.e. wait the next day to update again)
	 * (removed)                 and not 2*N old (last_upd_date >= 2*N)
	 *  
	 * Orders that is 'B' and and not updated within the last 6 hours (i.e. wait the next day to update again)
	 *                           and (not passed SRD+2*N or has not been updated since 2*N) 
	 *                           or no. of months since the last update is between 2 and 6 then check every half month 
	 * 
	 *Ensure we process all TEL first before IMS
	 *
	 * (pipb) M=90
	 * Orders that is 'E' or 'F' or 'P' and not M days old, OR
	 * 
	 * Orders that is 'B' and and not updated within the last 6 hours (i.e. wait the next day to update again)
	 *                           and (not passed SRD+2*M or has not been updated since 2*M) 
	 *                           or no. of months since the last update is between 2 and 6 then check every half month
	 * 
	*/
	
	private static final String SQL_GET_PENDING_LTS_SB_ORDERS = 
		"select " +
		"bwols.rowid," +
		"bwols.order_id," +
		"bwols.dtl_id, " +
		"bwols.sb_status, " +
		"bwols.bom_status, " +
		"bwols.legacy_status, " +
		"bwols.rea_cd, " +
		"bwols.l1_rea_cd, " +		
		"bwols.l1_ord_status, " +		
		"bwos.type_of_srv," +
		"bwos.srv_num,bwos.order_type," +
		"bwos.cc_service_id, " +
		"bwos.cc_service_mem_num, " +
		"bwos.bom_srv_req_date, " +		
		"bwo.err_cd,  " +
		"bwo.err_msg,  " +
		"bwo.shop_cd, " +
		"bwo.back_date_ind, " +
		"bwosl.srv_nn, " +
		"bwosl.legacy_ord_num, " +
		"bwosl.legacy_actv_seq, " +
		"bwosl.dn_source, " +
		"decode(bwopl.order_id,null,'N','Y') is_pipb, " +
	    "bwos.from_prod, " +	    
	    "bwos.to_prod, " +		
		"bwo.create_by " +
		"from " +
		"bomweb_order_latest_status bwols," +
		"bomweb_order_service bwos, " +
		"bomweb_order_service_lts bwosl," +
		"bomweb_order bwo, " +
		"bomweb_code_lkup bwcl, " +
		"bomweb_order_pipb_lts bwopl, " +		
		"bomweb_appointment bwap " +
	    "where "+ 
		
	    "( "+
	    
			"(     bwols.sb_status in ('E','F','P') and bwols.last_hist_date >= SYSDATE - ? OR " +
			"      bwols.sb_status = 'B' " +
		    "      and (" +
		    "       ((nvl(bwos.bom_srv_req_date,bwap.appnt_end_time)>= SYSDATE - 2*?  OR bwols.last_hist_date >= SYSDATE-(2*?)) " +		
			"        and SYSDATE > bwols.last_upd_date + nvl(to_number(code_desc)/24,4/24) ) " +
		    "       or round(mod(months_between(trunc(sysdate),trunc(last_hist_date)),1),2) in (0,0.5) and  floor(months_between(sysdate, last_hist_date)) between 2 and 6 " +
			"      ) " +
		    "      and NOT (nvl(bwols.bom_status,' ') in (?,?) or (bwols.bom_status = ? and bwols.legacy_status is not null)) " +
		    "     and bwopl.order_id is null " +
			") " +		
	    
		"or "+
		
			"(     bwols.sb_status in ('E','F','P') and bwols.last_hist_date >= SYSDATE - ?  OR " +
			"         bwols.sb_status = 'B' " +
		    "     and (nvl(bwos.bom_srv_req_date,bwap.appnt_end_time)>= SYSDATE - 2*?  OR bwols.last_hist_date >= SYSDATE-(2*?)) " +		
			"     and SYSDATE > bwols.last_upd_date + nvl(to_number(code_desc)/24,4/24) " + 
		    "     and NOT (nvl(bwols.bom_status,' ') in (?,?) or (bwols.bom_status = ? and bwols.legacy_status is not null)) " +
			"     and bwopl.order_id is not null " +
			") " +	
	    
		") "+
		
		"and   bwo.order_id  = bwols.order_id " +
		"and   bwos.order_id = bwols.order_id " +
		"and   bwopl.order_id(+)= bwols.order_id " +
	    "and   bwap.order_id(+) = bwols.order_id " + 
	    "and   bwap.dtl_id(+)   = bwols.dtl_id " +	
	    "and   bwosl.order_id(+)= bwols.order_id " +
	    "and   bwosl.dtl_id(+)  = bwols.dtl_id " +
	    "and   bwcl.code_type(+) = decode(bwols.order_id,bwols.order_id,'LTS_SB_SYNCH_PARM') " +
	    "and   bwcl.code_id(+)   = decode(bwols.order_id,bwols.order_id,'BAGE') " +		
		"and   bwo.order_id like ? " + 
		"and   bwos.dtl_id   = bwols.dtl_id " +
		"order by bwos.type_of_srv desc " +
		"FOR UPDATE OF bwols.sb_status, bwo.err_cd, bwos.bom_srv_req_date NOWAIT";
	
	public void updateSbStatus(String pRowId, String pSbStatus,
			String pBomStatus, String pLegacyStatus, String pUser,
			String pWqType, String pRea_Cd, String pL1ReaCd, String pL1OrdStatus)
			throws DAOException {
		
		try {
			simpleJdbcTemplate.update(SQL_UPDATE_SB_STATUS, pSbStatus,
					pBomStatus, pLegacyStatus, pUser, pWqType, pRea_Cd, pL1ReaCd, pL1OrdStatus, pRowId);
		} catch (Exception e) {
			logger.error("Error in updateSbStatus()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public void touchSbStatus(String pRowId)
			throws DAOException {
		
		try {
			simpleJdbcTemplate.update(SQL_TOUCH_SB_STATUS, pRowId);
		} catch (Exception e) {
			logger.error("Error in touchSbStatus()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}	
	
	public void updateOcId(String pOrderId, String pOcId, String pErrCd,
			String pErrMsg, String pUser) throws DAOException {
		
		try {
			simpleJdbcTemplate.update(SQL_UPDATE_SB_OCID, pOcId, pErrCd,
					pErrMsg, pUser, pOrderId);
		} catch (Exception e) {
			logger.error("Error in updateOcId()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}	
	
	public void updateSrvReqDate(String pOrderId, String pDtlId,
			Date pSrvReqDate, String pUser) throws DAOException {

		String srvReqDate = dateFormatterYYYYMMDD.format(pSrvReqDate);
		
		try {
			simpleJdbcTemplate.update(SQL_UPDATE_SB_SRV_REQ_DATE, srvReqDate,
					pUser, pOrderId, pDtlId);
		} catch (Exception e) {
			logger.error("Error in updateSrvReqDate()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}	
	
	public void updateNNLegacyOrd(String pOrderId, String pDtlId,
			long pSrvNN, String pLegacyOrdNum, int pLegacyActvSeq, String pUser) throws DAOException {
		
		try {
			simpleJdbcTemplate.update(SQL_UPDATE_NN_LEGACY_ORD, pSrvNN,
					pLegacyOrdNum, pLegacyActvSeq, pUser, pOrderId, pDtlId);
		} catch (Exception e) {
			logger.error("Error in updateNNLegacyOrd()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}		
	
	ParameterizedRowMapper<OrderStatusSynchDTO> mapper = new ParameterizedRowMapper<OrderStatusSynchDTO>() {
		public OrderStatusSynchDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
			OrderStatusSynchDTO status = new OrderStatusSynchDTO();
			status.setRowId(rs.getString("ROWID"));
			status.setCcServiceId(rs.getString("CC_SERVICE_ID"));
			status.setCcServiceMemNum(rs.getString("CC_SERVICE_MEM_NUM"));
			status.setBomSrvReqDate(rs.getTimestamp("BOM_SRV_REQ_DATE"));
			status.setDtlId(rs.getString("DTL_ID"));
			status.setSbStatus(rs.getString("SB_STATUS"));
			status.setBomStatus(rs.getString("BOM_STATUS"));
			status.setBomLegacyStatus(rs.getString("LEGACY_STATUS"));
			status.setReaCd(rs.getString("REA_CD"));
			status.setL1ReaCd(rs.getString("L1_REA_CD"));
			status.setL1OrdStatus(rs.getString("L1_ORD_STATUS"));				
			status.setOrderId(rs.getString("ORDER_ID"));
			status.setOrderType(rs.getString("ORDER_TYPE"));
			status.setSrvNum(rs.getString("SRV_NUM"));
			status.setTypeOfSrv(rs.getString("TYPE_OF_SRV"));
			status.setErrCd(rs.getString("ERR_CD"));
			status.setErrMsg(rs.getString("ERR_MSG"));
			status.setShopCd(rs.getString("SHOP_CD"));
			status.setBackDateInd(rs.getString("BACK_DATE_IND"));
			status.setCreateBy(rs.getString("CREATE_BY"));
			status.setSrvNN(rs.getLong("SRV_NN"));
			status.setLegacyOrdNum(rs.getString("LEGACY_ORD_NUM"));
			status.setLegacyActvSeq(rs.getInt("LEGACY_ACTV_SEQ"));		
			status.setDnSource(rs.getString("DN_SOURCE"));
			status.setPIPB(rs.getString("IS_PIPB"));
			status.setFromProd(rs.getString("FROM_PROD"));
			status.setToProd(rs.getString("TO_PROD"));
			return status;
		}
	};
		
	
	public OrderStatusSynchDTO[] getPendingSbOrders(int pOrderAge, String pOrderSuffix, int pipbOrderAge) throws DAOException {
		try {
			return (OrderStatusSynchDTO[]) simpleJdbcTemplate.query
			        (
					 SQL_GET_PENDING_LTS_SB_ORDERS, 
					 mapper,
					 pOrderAge,
					 pOrderAge,
					 pOrderAge,
					 LtsConstant.BOM_ORDER_STATUS_COMPLETED,
					 LtsConstant.BOM_ORDER_STATUS_CANCELLED_WO_ORDER, 
					 LtsConstant.BOM_ORDER_STATUS_CANCELLED_W_ORDER,
					 pipbOrderAge,
					 pipbOrderAge,
					 pipbOrderAge,
					 LtsConstant.BOM_ORDER_STATUS_COMPLETED,
					 LtsConstant.BOM_ORDER_STATUS_CANCELLED_WO_ORDER, 
					 LtsConstant.BOM_ORDER_STATUS_CANCELLED_W_ORDER,
					 pOrderSuffix
					).toArray(
					new OrderStatusSynchDTO[0]);
		} catch (CannotAcquireLockException e) {
			logger.info("RECORDS LOCKED FOR ORDER_ID LIKE " + pOrderSuffix + " - " + e);
			throw e;
			//return new OrderStatusSynchDTO[0];
		} catch (Exception e) {
			logger.error("Error in getStatus()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public OrderStatusSynchDTO[] getRelatedTelLines(String pOrderId) throws DAOException {	
		try {
			return (OrderStatusSynchDTO[]) simpleJdbcTemplate.query
			        (
			         SQL_GET_RELATED_TEL_LINES, 
					 mapper,pOrderId).toArray(
					new OrderStatusSynchDTO[0]);
		} catch (Exception e) {
			logger.error("Error in getStatus()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}	
	
}
