package com.bomwebportal.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.bomwebportal.dto.ImsCustomerDTO;
import com.bomwebportal.dto.ImsWqDTO;
import com.bomwebportal.dto.VasParmDTO;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.service.MobilePINService;
import com.pccw.util.spring.SpringApplicationContext;

public class ImsWorkQueueStatusDAO extends BaseDAO {
	
	protected final Log logger = LogFactory.getLog(getClass());
	private MobilePINService mobilePINService;

	public void setMobilePINService(MobilePINService mobilePINService) {
		this.mobilePINService = mobilePINService;
	}

	public MobilePINService getMobilePINService() {
		return mobilePINService;
	}

	public void updateOnlineSalesDiffNameCancel(String orderId) throws DAOException{
		logger.debug("updateOnlineSalesDiffNameCancel, update order "+orderId);
		String SQL;
			 SQL = 	"	UPDATE bomweb_order				"+
			 		"	SET order_status = '10',		"+
			 		"	last_update_by = 'QM',			"+
			 		"	last_update_date = SYSDATE,		"+
			 		"	reason_cd = 'S009'				"+
			 		"	WHERE order_id = '"+orderId+"'	";
		
		try {
				simpleJdbcTemplate.update(SQL);
				insertOnlineSalesRMK(orderId);//when cancel, RMK have to be later than update status
		} catch (Exception e) {
			logger.error("Exception caught in updateOnlineSalesStatus()", e);
			throw new DAOException(e.getMessage(), e);
		}
		List<VasParmDTO> dtoList = getBomwebVasParmIMS(orderId);
		if(dtoList!=null && dtoList.size()>0){
			String sysid= "NONONLINE";
			if(orderId.contains("SBO")){
				sysid ="ONLINE";
			}
			for(VasParmDTO dto:dtoList){
				mobilePINService.releaseMobilePIN(dto.getVas_parm_a(), dto.getVas_parm_b(), "QM", orderId,sysid) ;
			}				
		}		
	}
	
	
	public List<VasParmDTO> getBomwebVasParmIMS(String orderId) throws DAOException{
		
		logger.info("getBomwebVasParmIMS");
		String SQL = "	SELECT ORDER_ID, PRD_ID, ITEM_ID, VAS_TYPE, " +
				"  PARM_A, PARM_A_CD," +
				"  PARM_B, PARM_B_CD," +
				"  PARM_C, PARM_C_CD    "	 +
		"	  FROM BOMWEB_VAS_PARM_IMS	" +
		"	 WHERE order_id = ?	" ;

		
		ParameterizedRowMapper<VasParmDTO> mapper = new ParameterizedRowMapper<VasParmDTO>() {

			public VasParmDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				VasParmDTO dto = new VasParmDTO();
				dto.setOrder_id(rs.getString("ORDER_ID"));
				dto.setItem_id(rs.getString("ITEM_ID"));
				dto.setVas_parm_a(rs.getString("PARM_A"));
				dto.setVas_parm_b(rs.getString("PARM_B"));
				dto.setVas_parm_c(rs.getString("PARM_C"));
				dto.setVas_parm_a_cd(rs.getString("PARM_A_CD"));
				dto.setVas_parm_a_cd(rs.getString("PARM_B_CD"));
				dto.setVas_parm_a_cd(rs.getString("PARM_C_CD"));
				dto.setProd_id(rs.getString("PRD_ID"));
				dto.setVas_type(rs.getString("VAS_TYPE"));
				return dto;
			}
		};
		
		try {
			logger.debug("getBomWebCustomer @ OrderDAO: " + SQL);
			List<VasParmDTO> result = simpleJdbcTemplate.query(SQL, mapper, orderId);
			
			return result;
		
		} catch (Exception e) {
			logger.error("Exception caught in getBomwebVasParmIMS()", e);
			throw new DAOException(e.getMessage(), e);
		}
		
	}
	
	public void updateonlineSalesDiffNameSignOff(String orderId) throws DAOException{
		logger.debug("onlineSalesDiffNameSignOff, update order "+orderId);
		String SQL;
			 SQL = 	"	UPDATE bomweb_order																					"+
			 		"	SET order_status = DECODE ((SELECT reason_cd FROM bomweb_order WHERE order_id = '"+orderId+"') ,	"+
					"	'R006', '10',																						"+
					"	'R005', '00',																						"+ 
					"	'R004', '00'),																						"+
					"	reason_cd = DECODE ((SELECT reason_cd FROM bomweb_order WHERE order_id = '"+orderId+"') ,			"+
					"	'R006', 'R005',																						"+
					"	'R005', 'R005',																						"+
					"	'R004', 'R004'),																					"+
					"	src = DECODE ((SELECT reason_cd FROM bomweb_order WHERE order_id = '"+orderId+"') ,					"+
					"	'R006', 'CO',																						"+
					"	'R005', 'CO',																						"+
					"	(select src FROM bomweb_order WHERE order_id = '"+orderId+"')),										"+
					"	last_update_by = 'QM',																				"+
					"	last_update_date = SYSDATE																			"+
					"	WHERE order_id = '"+orderId+"'																		";
					
		try {
				insertOnlineSalesRMK(orderId);
				simpleJdbcTemplate.update(SQL);
		} catch (Exception e) {
			logger.error("Exception caught in updateOnlineSalesStatus()", e);
			throw new DAOException(e.getMessage(), e);
		}
		
	}
	
	public void updateonlineSalesCOrderClosed(String orderId) throws DAOException{
		logger.debug("onlineSalesCOrderClosed, update order "+orderId);
		String SQL;
			 SQL = 	"		UPDATE bomweb_order				"+
			 		"		SET order_status = '01', 		"+
			 		"	    src = 'CO',						"+
			 		"	    last_update_by = 'QM',			"+
			 		"	    last_update_date = SYSDATE		"+
			 		"	 	WHERE order_id = '"+orderId+"'	";
					
		try {
				insertOnlineSalesRMK(orderId);
				simpleJdbcTemplate.update(SQL);
		} catch (Exception e) {
			logger.error("Exception caught in updateOnlineSalesStatus()", e);
			throw new DAOException(e.getMessage(), e);
		}
		
	}
	
	public void updateCOrderIgnored(String orderId) throws DAOException{
		logger.debug("onlineSalesCOrderClosed, update order "+orderId);
		String SQL;
			 SQL = 	"		UPDATE bomweb_order				"+
			 		"		SET order_status = '22', 		"+
			 		"	    src = 'CO',						"+
			 		"	    last_update_by = 'QM',			"+
			 		"	    last_update_date = SYSDATE		"+
			 		"	 	WHERE order_id = '"+orderId+"'	";
					
		try {
				simpleJdbcTemplate.update(SQL);
		} catch (Exception e) {
			logger.error("Exception caught in updateCOrderIgnored()", e);
			throw new DAOException(e.getMessage(), e);
		}
		
	}
	
	public void imsDSWaiveQCAlertSettled(String orderId) throws DAOException{
		logger.debug("imsDSWaiveQCAlertSettled, update order "+orderId);
		String SQL = "UPDATE bomweb_order_ims" +
			 		"   SET waive_qc_ok = 'Y'," +
			 		"       LAST_UPD_BY  = 'QM'," +
			 		"       LAST_UPD_DATE  = SYSDATE" +
			 		" WHERE order_id = '"+orderId+"'";
					
		try {
				simpleJdbcTemplate.update(SQL);
		} catch (Exception e) {
			logger.error("Exception caught in imsDSWaiveQCAlertSettled()", e);
			throw new DAOException(e.getMessage(), e);
		}
		
	}
	
	public void imsDSWaiveQCAlertCancelled(String orderId) throws DAOException{
		logger.debug("imsDSWaiveQCAlertCancelled, update order "+orderId);
		String SQL = "UPDATE bomweb_order_ims" +
			 		"   SET waive_qc_ok = 'N'," +
			 		"       LAST_UPD_BY  = 'QM'," +
			 		"       LAST_UPD_DATE  = SYSDATE" +
			 		" WHERE order_id = '"+orderId+"'";
					
		try {
				simpleJdbcTemplate.update(SQL);
		} catch (Exception e) {
			logger.error("Exception caught in imsDSWaiveQCAlertCancelled()", e);
			throw new DAOException(e.getMessage(), e);
		}
		
	}
	
	public void insertOnlineSalesRMK(String orderId) throws DAOException{
		logger.debug("insertOnlineSalesRMK, update order "+orderId);
		
		String RMK_SQL = 	"	INSERT INTO bomweb_remark																	"+
							"	(order_id, dtl_id, rmk_type, create_by, create_date, last_upd_by, last_upd_date, rmk_dtl)	"+
							"	SELECT '"+orderId+"', '1', 'wq', 'QM', SYSDATE, 'QM', SYSDATE,								"+
							"	(SELECT    description																		"+
							"	|| ' on ' || TO_CHAR (SYSDATE, 'DD-MM-YYYY')													"+
							"	FROM w_code_lkup																			"+
							"	WHERE grp_id = 'ONLINE_SALES_RMK'															"+
							"	and code = (SELECT reason_cd	FROM bomweb_order	WHERE order_id = '"+orderId+"'))		"+
							"	FROM DUAL																					";
	
		try {
				simpleJdbcTemplate.update(RMK_SQL);
		} catch (Exception e) {
			logger.error("Exception caught in updateOnlineSalesStatus()", e);
			throw new DAOException(e.getMessage(), e);
		}
		
	}
	
	public void insertBomwebRemark(String orderId, String remark, String createBy) throws DAOException{
		logger.debug("insertBomwebRemark, orderId:"+orderId+" rmk:"+remark+" createBy:"+createBy);
		String RMK_SQL = 	"Insert into bomweb_remark" +
				"   (ORDER_ID, DTL_ID, RMK_TYPE, RMK_DTL, CREATE_BY, CREATE_DATE, LAST_UPD_BY, LAST_UPD_DATE)" +
				" Values" +
				"   (?, 1, 'WQ', ?, ?, sysdate, ?, sysdate)";
		try {
				simpleJdbcTemplate.update(RMK_SQL,orderId,remark,createBy,createBy);
		} catch (Exception e) {
			logger.error("Exception caught in insertBomwebRemark()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}

	
	public void imsVerDocApproved(String orderId) throws DAOException{
		logger.debug("imsVerDocApproved, orderId:"+orderId);
		String RMK_SQL = 	"" +
				"UPDATE   bomweb_order " +
				"   SET   order_status = 13, last_update_date = SYSDATE, last_update_by = 'QM'" +
				" WHERE   order_id = ? ";
		try {
				simpleJdbcTemplate.update(RMK_SQL,orderId);
		} catch (Exception e) {
			logger.error("Exception caught in imsVerDocApproved()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public void imsVerDocFailed(String orderId) throws DAOException{
		logger.debug("imsVerDocFailed, orderId:"+orderId);
		String RMK_SQL = 	"" +
				"UPDATE   bomweb_order " +
				"   SET   order_status = 24, last_update_date = SYSDATE, last_update_by = 'QM'" +
				" WHERE   order_id = ? ";
		try {
				simpleJdbcTemplate.update(RMK_SQL,orderId);
		} catch (Exception e) {
			logger.error("Exception caught in imsVerDocFailed()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public void updateApprovalStatus(String orderId, String statusCd) throws DAOException{
		logger.debug("updateApprovalStatus");
		logger.debug("update order "+orderId+" status "+statusCd);
		
		String SQL = "	UPDATE bomweb_order	"+
		"	   SET order_status = ?,	"+
		"	       last_update_by = 'WQ',	"+
		"	       last_update_date = SYSDATE	"+
		"	 WHERE order_id = ?	";
		
		String RMK_SQL = "	INSERT INTO bomweb_remark	"+
		"	            (order_id, dtl_id, rmk_type, create_by, create_date, rmk_dtl)	"+
		"	   SELECT '"+orderId+"', '1',	"+
		"	          (SELECT description	"+
		"	             FROM w_code_lkup	"+
		"	            WHERE grp_id =	"+
		"	                     DECODE ('"+statusCd+"',	"+
		"	                             '31', 'IMS_APP_RMK_TYPE',	"+
		"	                             '32', 'IMS_REJ_RMK_TYPE'	"+
		"	                            )	"+
		"	              AND code = (SELECT reason_cd	"+
		"	                            FROM bomweb_order	"+
		"	                           WHERE order_id = '"+orderId+"')),	"+
		"	          'WQ', SYSDATE,	"+
		"	          (SELECT    description	"+
		"	                  || DECODE ('"+statusCd+"',	"+
		"	                             '31', ' is approved on ',	"+
		"	                             '32', ' is rejected on '	"+
		"	                            )	"+
		"	                  || TO_CHAR (SYSDATE, 'DDMMYYYY')	"+
		"	             FROM w_code_lkup	"+
		"	            WHERE grp_id = 'SB_IMS_ACQ_REQ'	"+
		"	              AND code = (SELECT reason_cd	"+
		"	                            FROM bomweb_order	"+
		"	                           WHERE order_id = '"+orderId+"'))	"+
		"	     FROM DUAL	";

		
		try {
			
			simpleJdbcTemplate.update(SQL, statusCd, orderId);
			simpleJdbcTemplate.update(RMK_SQL);
		
		} catch (Exception e) {
			logger.error("Exception caught in updateApprovalStatus()", e);
			throw new DAOException(e.getMessage(), e);
		}
		
	}
	
	public void updateBomOrderId(String orderId, String bomOrderId) throws DAOException{
		logger.debug("updateApprovalStatus");
		logger.debug("update sb order "+orderId+" with ocid "+bomOrderId);
		
		String SQL = "	UPDATE bomweb_order	"+
		"	   SET ocid = ?,	"+
		"	       last_update_by = 'WQ',	"+
		"	       last_update_date = SYSDATE	"+
		"	 WHERE order_id = ?	";
		
		try {
			
			simpleJdbcTemplate.update(SQL, bomOrderId, orderId);
		
		} catch (Exception e) {
			logger.error("Exception caught in updateBomOrderId()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public ImsCustomerDTO getCustInfoByOrderID (String orderId)	throws DAOException {
		logger.debug("getCustInfoByOrderID");
		logger.debug("orderId: "+orderId);
	
		String SQL=	"select FIRST_NAME  ,LAST_NAME  , id_doc_num, ID_DOC_TYPE  " +
				"from bomweb_customer " +
				"where order_id = '" + orderId +"' ";
	
		ParameterizedRowMapper<ImsCustomerDTO> mapper = new ParameterizedRowMapper<ImsCustomerDTO>() {
		    public ImsCustomerDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		    	ImsCustomerDTO customer = new ImsCustomerDTO();
		    	 customer.setLastName(rs.getString("LAST_NAME"));
		    	 customer.setFirstName(rs.getString("FIRST_NAME"));
		    	 customer.setID_DOC_NUM(rs.getString("id_doc_num"));
		    	 customer.setID_DOC_TYPE(rs.getString("ID_DOC_TYPE"));
		        return customer;
		    }
		};

		List<ImsCustomerDTO> cust = new ArrayList<ImsCustomerDTO>();
		
		try {
			logger.debug("getCustInfoByOrderID @ ImsWorkQueueStatusDAO: " + SQL);
			cust = simpleJdbcTemplate.query(SQL, mapper);
		} catch (EmptyResultDataAccessException erdae) {
			logger.debug("EmptyResultDataAccessException");
			cust = null;
		} catch (Exception e) {
			logger.debug("Exception caught in getCustInfoByOrderID@ImsWorkQueueStatusDAO():", e);
			throw new DAOException(e.getMessage(), e);
		}	
		return (cust.size()>0 ? cust.get(0):null);
	}
	
	public void updateCustNo(String orderId, String CustNo) throws DAOException{
		logger.debug("updateCustNo");
		logger.debug("update orderId: "+orderId+" with CustNo: "+CustNo);
		
		String SQL = "	UPDATE bomweb_customer	"+
		"	   SET CUST_NO = ?,	"+
		"	       LAST_UPD_BY  = 'WQ',	"+
		"	       LAST_UPD_DATE = SYSDATE	"+
		"	 WHERE order_id = ?	";
		
		try {
			
			simpleJdbcTemplate.update(SQL, CustNo, orderId);
		
		} catch (Exception e) {
			logger.error("Exception caught in updateCustNo()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	
	public void imsDSMisMatchSignoff(String orderId) throws DAOException{
		logger.debug("imsDSMisMatchSignoff, update order "+orderId);
		String SQL = 	" UPDATE bomweb_order " +
				"			   SET order_status =" +
				"	                  DECODE (reason_cd," +
				"	                          'R006', '05'," +
				"	                          'R005', '00'," +
				"	                          'R004', '00'" +
				"	                         )," +
				"	       reason_cd =" +
				"	            DECODE (reason_cd," +
				"	                    'R006', 'R005'," +
				"	                    'R005', 'R005'," +
				"	                    'R004', 'R004'" +
				"	                   )," +
				"	       src = DECODE (reason_cd, 'R006', 'CO', 'R005', 'CO', src)," +
				"	       last_update_by = 'QM'," +
				"	       last_update_date = SYSDATE" +
				"	 WHERE order_id = ? ";
		
		try {
				insertBomwebRemark(orderId,"ims DS MisMatch Signoff","ImsDsWq");
				simpleJdbcTemplate.update(SQL,orderId);
				markImsDSMisMatchHKIDOut(orderId);
		} catch (Exception e) {
			logger.error("Exception caught in imsDSMisMatchSignoff()", e);
			throw new DAOException(e.getMessage(), e);
		}
		
	}
	

	public void markImsDSMisMatchHKIDOut(String orderId) throws DAOException{
		logger.debug("markImsDSMisMatchHKIDOut, update order "+orderId);
				String SQL2 ="		UPDATE q_wq_wp_document" +
						"		   SET wq_wp_assgn_id = -1 * wq_wp_assgn_id," +
						"       last_upd_by = 'QM'," +
						"       last_upd_date = SYSDATE" +
						"		 WHERE wq_document_id =" +
						"		          (SELECT wq_document_id" +
						"		             FROM q_wq_document" +
						"		            WHERE wq_id = (SELECT wq_id" +
						"		                             FROM q_work_queue" +
						"		                            WHERE sb_id = ?)" +
						"		              AND attachment_path LIKE '%I007%')" +
						"		   AND wq_wp_assgn_id > 0 ";
		
		try {
				simpleJdbcTemplate.update(SQL2,orderId);
		} catch (Exception e) {
			logger.error("Exception caught in markImsDSMisMatchHKIDOut()", e);
			throw new DAOException(e.getMessage(), e);
		}
		
	}

	
	public void imsDSMisMatchCancel(String orderId) throws DAOException{
		logger.debug("imsDSMisMatchCancel, update order "+orderId);
		String SQL=	"	UPDATE bomweb_order				"+
			 		"	SET order_status = '23',		"+
			 		"	last_update_by = 'QM',			"+
			 		"	last_update_date = SYSDATE,		"+
			 		"	reason_cd = 'S009'				"+
			 		"	WHERE order_id = ?	";
		
		try {
				simpleJdbcTemplate.update(SQL,orderId);
				markImsDSMisMatchHKIDOut(orderId);
				insertBomwebRemark(orderId,"ims DS MisMatch Cancel","ImsDsWq");
		} catch (Exception e) {
			logger.error("Exception caught in imsDSMisMatchCancel()", e);
			throw new DAOException(e.getMessage(), e);
		}
		
	}


	
	public boolean isAmendTvOnly(String order_id) throws DAOException{
		String sql = "select count(*) isAmendTvOnly from bomweb_amend_category where send = 'P' and order_id = ?  ";
		try {
			int count = simpleJdbcTemplate.queryForInt(sql, order_id);			
			return count > 0 ;		
		} catch (Exception e) {
			logger.error("Exception caught in isAmendTvOnly()", e);	
			throw new DAOException(e.getMessage(), e);
		}		
	}	
	
	public void updateAmendNowRetTvOnly(String orderId) throws DAOException{
		logger.info("updateAmendNowRetTvOnly, orderId:"+orderId);		
		String SQL = " update bomweb_amend_category set send = 'N' where nature = 368 and send = 'P' and order_id = ? ";	
		try {
			simpleJdbcTemplate.update(SQL, orderId);
		} catch (Exception e) {
			logger.error("Exception caught in updateAmendNowRetTvOnly()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public void imsDSFSPendingSettled(String orderId) throws DAOException{
		
		Boolean isAmendNowRetTvOnly = false;
		try{
			isAmendNowRetTvOnly = this.isAmendTvOnly(orderId);
		}catch (Exception e){
			logger.error("Exception caught in isAmendTvOnly() "+orderId, e);				
		}
		logger.debug("imsDSFSPendingSettled, update order "+orderId+ "  isAmendNowRetTvOnly:"+isAmendNowRetTvOnly);
		
		if(isAmendNowRetTvOnly){
			updateAmendNowRetTvOnly(orderId);
		}else{
		
		String firstMthSQL= "UPDATE q_wq_wp_assgn h " +
				"   SET RECEIVE_DATE =sysdate , wp_id =" +
				"          (SELECT wp_id" +
				"             FROM (SELECT   a.wp_id, COUNT (*) cnt" +
				"                       FROM (SELECT *" +
				"                               FROM q_wp_wq_nature_assgn_v" +
				"                              WHERE wq_nature_id = TO_NUMBER (:wq_nature_id)) a," +
				"                            (SELECT *" +
				"                            		FROM q_wq_wq_nature_assgn" +
				"                              WHERE wq_id = TO_NUMBER (:wq_id)" +
				"                                AND wq_batch_id = TO_NUMBER (:wq_batch_id)" +
				"                                AND wq_nature_id = TO_NUMBER (:wq_nature_id)" +
				"                                AND dispatch_wq_wp_assgn_id = TO_NUMBER (:dispatch_wq_wp_assgn_id)) b," +
				"                            (SELECT *" +
				"                               FROM TABLE" +
				"                                       (q_work_queue_pkg.get_avl_wp_structure" +
				"                                                            (TO_NUMBER (:wq_id)," +
				"                                                             TO_NUMBER (:wq_batch_id)" +
				"                                                            )" +
				"                                       )) c" +
				"                      WHERE a.wq_type IN ('*', b.wq_type)" +
				"                        AND a.wq_subtype IN ('*', b.wq_subtype)" +
				"                        AND a.wp_channel_cd IN ('*', c.channel_cd)" +
				"                        AND a.wp_sales_channel IN ('*', c.sales_channel)" +
				"                        AND a.wp_centre_cd IN ('*', c.centre_cd)" +
				"		GROUP BY a.wp_id" +
				"                   ORDER BY cnt DESC)" +
				"            WHERE ROWNUM = 1)" +
				" WHERE wp_id = '15023' AND h.wq_wp_assgn_id = TO_NUMBER (:dispatch_wq_wp_assgn_id)";


		List<ImsWqDTO> wqDtoList = new ArrayList<ImsWqDTO>();
		String getTargetWqRecordInfoSQL = "SELECT b.wq_batch_id, a.wq_id, wq_wp_assgn_id , wq_nature_id" +
				"   FROM q_work_queue a, q_wq_wq_nature_assgn b, q_wq_wp_assgn_status_log c" +
				"  WHERE sb_id = :order_id " +
				"    AND a.wq_id = b.wq_id" +
				"    AND b.dispatch_wq_wp_assgn_id = c.wq_wp_assgn_id" +
				"    AND b.wq_subtype = 'ORDER_AMEND'" +
				"    AND c.latest_status_ind = 'Y'" +
				"    AND c.status_cd NOT IN (SELECT code" +
				"                              FROM q_dic_code_lkup" +
				"                             WHERE grp_id IN ('WQ_ENDING_STATUS'))";
		
		
		ParameterizedRowMapper<ImsWqDTO> mapper = new ParameterizedRowMapper<ImsWqDTO>() {
		    public ImsWqDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		    	ImsWqDTO dto = new ImsWqDTO();
		    	dto.setWqBatchId(rs.getString("wq_batch_id"));
		    	dto.setWqId(rs.getString("wq_id"));
		    	dto.setWqWpAssgnId(rs.getString("wq_wp_assgn_id"));
		    	dto.setWqNatureId(rs.getString("wq_nature_id"));
		        return dto;
		    }
		};

		
		
		String secondMthSQL = "UPDATE q_wq_wp_assgn h   SET RECEIVE_DATE =sysdate ,  wp_id =" +
				"          (SELECT description wp" +
				"             FROM w_code_lkup" +
				"            WHERE code =" +
				"                     (SELECT channel_cd" +
				"                        FROM bomweb_sales_assignment" +
				"                       WHERE staff_id LIKE" +
				"                                 '%' || (SELECT sales_cd" +
				"                                		 FROM bomweb_order" +
				"                                          WHERE order_id = ? )" +
				"                                 || '%'" +
				"                         AND (end_date IS NULL OR end_date >= SYSDATE))" +
				"              AND grp_id = 'IMS_DS_FD_PENDING')" +
				" WHERE wp_id = '15023'" +
				"   AND h.wq_wp_assgn_id IN (" +
				"          SELECT dispatch_wq_wp_assgn_id" +
				"            FROM q_work_queue a," +
				"                 q_wq_wq_nature_assgn b," +
				"                 q_wq_wp_assgn_status_log c" +
				"           WHERE sb_id = ? " +
				"             AND a.wq_id = b.wq_id" +
				"             AND b.dispatch_wq_wp_assgn_id = c.wq_wp_assgn_id" +
				"             AND b.wq_subtype = 'ORDER_AMEND'" +
				"             AND c.latest_status_ind = 'Y'" +
				"             AND c.status_cd NOT IN (SELECT code" +
				"                                       FROM q_dic_code_lkup" +
				"                                      WHERE grp_id IN ('WQ_ENDING_STATUS')))";
				
		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("order_id", orderId);
			
			wqDtoList = simpleJdbcTemplate.query(getTargetWqRecordInfoSQL,mapper, params);

			logger.info("WqId():"+wqDtoList.get(0).getWqId()+
					"  WqBatchId():"+wqDtoList.get(0).getWqBatchId()+
					"  WqWpAssgnId():"+wqDtoList.get(0).getWqWpAssgnId()+
					"  WqNatureId():"+wqDtoList.get(0).getWqNatureId());
			
			params = new MapSqlParameterSource();
			params.addValue("wq_id", wqDtoList.get(0).getWqId());
			params.addValue("wq_batch_id", wqDtoList.get(0).getWqBatchId());
			params.addValue("dispatch_wq_wp_assgn_id", wqDtoList.get(0).getWqWpAssgnId());
			params.addValue("wq_nature_id", wqDtoList.get(0).getWqNatureId());
			
				simpleJdbcTemplate.update(firstMthSQL,params);
				insertBomwebRemark(orderId,"ims DS FS pending settled","ImsDsWq");
				
		} catch (Exception e) {
			logger.error("Exception caught in imsDSFSPendingSettled() SQL1 ", e);
			try {
				simpleJdbcTemplate.update(secondMthSQL,orderId,orderId);
				insertBomwebRemark(orderId,"ims DS FS pending settled","ImsDsWq");
			}catch (Exception e2) {
				logger.error("Exception caught in imsDSFSPendingSettled() SQL2 ", e);
				throw new DAOException(e.getMessage(), e2);
			}
		}
		
		}
	}
	
	
	public void imsDSFSPendingCancelled(String orderId) throws DAOException{
		logger.debug("imsDSFSPendingCancelled, update order "+orderId);
		String SQL= "UPDATE q_wq_wp_assgn_status_log" +
				"   SET status_cd = '130'" +
				" WHERE wq_wp_assgn_id IN (" +
				"          SELECT a.wq_wp_assgn_id" +
				"            FROM q_wq_wp_assgn a, q_wq_wp_assgn b" +
				"           WHERE a.wq_id IN (SELECT wq_id" +
				"                               FROM q_work_queue" +
				"                              WHERE sb_id LIKE (?))" +
				"             AND a.wq_wp_assgn_id = b.wq_wp_assgn_id" +
				"             AND b.wp_id = '15023')" +
				"   AND latest_status_ind = 'Y'" +
				"   AND status_cd NOT IN (SELECT code" +
				"                           FROM q_dic_code_lkup" +
				"                          WHERE grp_id IN ('WQ_ENDING_STATUS'))";
		
		try {
				simpleJdbcTemplate.update(SQL,orderId);
				insertBomwebRemark(orderId,"ims DS FS Pending Cancelled","ImsDsWq");
		} catch (Exception e) {
			logger.error("Exception caught in imsDSFSPendingCancelled()", e);
			throw new DAOException(e.getMessage(), e);
		}
		
	}
	
	public void imsDSCashPrepaySettled(String orderId) throws DAOException{
		logger.debug("imsDSCashPrepaySettled, update order "+orderId);
		String SQL;
			 SQL = 	"UPDATE q_wq_wp_assgn_status_log" +
			 		"   SET status_cd = '090'," +
			 		"       last_upd_by = 'CloseBoth'," +
			 		"       last_upd_date = SYSDATE" +
			 		" WHERE wq_wp_assgn_id IN (" +
			 		"          SELECT b.wq_wp_assgn_id" +
			 		"            FROM q_wq_wq_nature_assgn a," +
			 		"                 q_wq_wp_assgn_status_log b," +
			 		"                 q_work_queue c" +
			 		"           WHERE wq_nature_id IN (273, 275,292,293)" +
			 		"             AND a.dispatch_wq_wp_assgn_id = b.wq_wp_assgn_id" +
			 		"             AND a.wq_id = c.wq_id" +
			 		"             AND b.latest_status_ind = 'Y'" +
			 		"             AND b.status_cd NOT IN (SELECT code" +
			 		"                                       FROM q_dic_code_lkup" +
			 		"                                      WHERE grp_id IN ('WQ_ENDING_STATUS'))" +
			 		"             AND sb_id = ? )";
		
		try {
				simpleJdbcTemplate.update(SQL, orderId);
		} catch (Exception e) {
			logger.error("Exception caught in imsDSCashPrepaySettled()", e);
			throw new DAOException(e.getMessage(), e);
		}
		
	}
	
}
