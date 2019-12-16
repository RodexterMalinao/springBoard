package com.bomwebportal.ims.dao;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.bomwebportal.exception.DAOException;
import com.pccw.wq.schema.dto.WorkQueueNatureDTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ImsCOrderWQDAO extends BaseDAO{
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	public WorkQueueNatureDTO get_TYPE_SUBTYPE_by_NATURE_ID (String WQ_NATURE_ID)
	throws DAOException {
		logger.debug("get_TYPE_SUBTYPE_by_NATURE_ID @ OnlineSalesWQDAO WQ_NATURE_ID: " + WQ_NATURE_ID);
		

		String SQL=	"select WQ_TYPE, WQ_SUBTYPE,WQ_NATURE_ID  from Q_WP_WQ_NATURE_ASSGN where WQ_NATURE_ID = '" + WQ_NATURE_ID +"' and rownum=1";
		
		
		ParameterizedRowMapper<WorkQueueNatureDTO> mapper = new ParameterizedRowMapper<WorkQueueNatureDTO>() {
		    public WorkQueueNatureDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		    	WorkQueueNatureDTO wq = new WorkQueueNatureDTO();
		    	wq.setWorkQueueType(rs.getString("WQ_TYPE"));
		    	wq.setWorkQueueSubType(rs.getString("WQ_SUBTYPE"));
		    	wq.setWorkQueueNatureId(rs.getString("WQ_NATURE_ID"));
		        return wq;
		    }
		};
		
    	List<WorkQueueNatureDTO> wqNatureDTO = new ArrayList<WorkQueueNatureDTO>();
		
		try {
			logger.debug("get_TYPE_SUBTYPE_by_NATURE_ID: " + SQL);
			wqNatureDTO = simpleJdbcTemplate.query(SQL, mapper);
		} catch (EmptyResultDataAccessException erdae) {
			logger.debug("EmptyResultDataAccessException");
			wqNatureDTO = null;
		} catch (Exception e) {
			logger.debug("Exception caught in get_TYPE_SUBTYPE_by_NATURE_ID:", e);
			throw new DAOException(e.getMessage(), e);
		}	
		if(wqNatureDTO.size()>0){
			logger.info("getWorkQueueNatureId:"+wqNatureDTO.get(0).getWorkQueueNatureId());
			logger.info("getWorkQueueType:"+wqNatureDTO.get(0).getWorkQueueType());
			logger.info("getWorkQueueSubType:"+wqNatureDTO.get(0).getWorkQueueSubType());
		}
		return (wqNatureDTO.size()>0 ? wqNatureDTO.get(0):null);
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
	
	public Boolean checkPendingAlertWQExist (String orderId)
	throws DAOException {
		logger.debug("checkPendingAlertWQExist orderId: " + orderId);
		
		String SQL=	" select count(*) pending from Q_WQ_WP_ASSGN_STATUS_LOG a , Q_WORK_QUEUE b, Q_WQ_WP_ASSGN c, Q_WQ_WQ_NATURE_ASSGN d" +
				" where b.SB_ID = '" + orderId +"'" +
				" and a.WQ_WP_ASSGN_ID=c.WQ_WP_ASSGN_ID" +
				" and b.WQ_ID=d.WQ_ID" +
				" and b.WQ_ID=c.WQ_ID" +
				" and d.WQ_NATURE_ID = 420" +
				" and a.LATEST_STATUS_IND='Y'" +
				" and a.STATUS_CD <> '090'" ;
		
		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
		    public String mapRow(ResultSet rs, int rowNum) throws SQLException {
		    	String numOfPendingWQ = "";
		    	numOfPendingWQ=(rs.getString("pending"));
		        return numOfPendingWQ;
		    }
		};
		
    	List<String> temp = new ArrayList<String>();
		
		try {
			logger.debug("checkPendingAlertWQExist SQL: " + SQL);
			temp = simpleJdbcTemplate.query(SQL, mapper);
		} catch (EmptyResultDataAccessException erdae) {
			logger.debug("EmptyResultDataAccessException");
			temp = null;
		} catch (Exception e) {
			logger.debug("Exception caught in checkPendingAlertWQExist:", e);
			throw new DAOException(e.getMessage(), e);
		}	
		if(temp.size()>0&&temp.get(0).equalsIgnoreCase("0")){
			return false;
		}else{
			return true;
		}
	}
	
	public void insertAlertWQRemark(String rmk, String orderId, String salesCd) throws DAOException{
		logger.debug("insert AlertWQRemark order: "+orderId+"  rmk: "+rmk+"  salesCd:"+salesCd);

		String RMK_SQL = "INSERT INTO q_wq_remarks" +
				"            (wq_id, remark_seq, wq_batch_id, wq_nature_id, remarks, create_by," +
				"             create_date, last_upd_by, last_upd_date)" +
				"   (SELECT a.wq_id, " +
				"           (SELECT MAX (remark_seq) + 1" +
				"              FROM q_work_queue a, q_wq_remarks b" +
				"             WHERE sb_id IN ('"+orderId+"')" +
				"               AND wq_nature_id = 420" +
				"               AND a.wq_id = b.wq_id)," +
				"            wq_batch_id, 420, '"+rmk+"', '"+salesCd+"', SYSDATE," +
				"           '"+salesCd+"', SYSDATE" +
				"      FROM q_work_queue a, q_wq_remarks b" +
				"     WHERE sb_id IN ('"+orderId+"') AND wq_nature_id = 420" +
				"           AND a.wq_id = b.wq_id " +
				" 	and rownum = 1)";
		
		logger.debug("insert AlertWQRemark RMK_SQL:"+RMK_SQL);

		try {
				simpleJdbcTemplate.update(RMK_SQL);
		} catch (Exception e) {
			logger.error("Exception caught in insertAlertWQRemark()", e);
			throw new DAOException(e.getMessage(), e);
		}
		
	}
	
	public String getChannelCd(String salesCd) 
	throws DAOException {
		logger.debug("getChannelCd salesCd: " + salesCd);
		
		String SQL=	"    SELECT channel_cd, centre_cd, team_cd" +
				"     FROM bomweb_sales_assignment" +
				"    WHERE staff_id = '"+salesCd+"' " ;
		
		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
		    public String mapRow(ResultSet rs, int rowNum) throws SQLException {
		    	String channelCd = "";
		    	channelCd=(rs.getString("channel_cd"));
		        return channelCd;
		    }
		};
		
    	List<String> temp = new ArrayList<String>();
		
		try {
			logger.debug("getChannelCd SQL: " + SQL);
			temp = simpleJdbcTemplate.query(SQL, mapper);
		} catch (EmptyResultDataAccessException erdae) {
			logger.debug("EmptyResultDataAccessException");
			temp = null;
		} catch (Exception e) {
			logger.debug("Exception caught in getChannelCd:", e);
			throw new DAOException(e.getMessage(), e);
		}	
		if(temp.size()>0){
			return temp.get(0);
		}else{
			return null;
		}
	}

	public void updateStatusAlertWQ(String orderId, String assignee) throws DAOException{
		logger.debug(" updateStatusAlertWQ order: "+orderId);

		String RMK_SQL = " update Q_WQ_WP_ASSGN_STATUS_LOG a set STATUS_CD = '030' , ASSIGNEE = '"+assignee+"'  " +
				" where a.WQ_WP_ASSGN_ID=(" +
				" select WQ_WP_ASSGN_ID from Q_WORK_QUEUE b, Q_WQ_WP_ASSGN c, Q_WQ_WQ_NATURE_ASSGN d " +
				" where b.SB_ID = '"+orderId+"'" +
				" and b.WQ_ID=d.WQ_ID" +
				" and b.WQ_ID=c.WQ_ID" +
				" and d.DISPATCH_WQ_WP_ASSGN_ID is not null" +
				" and d.WQ_NATURE_ID = 420)" +
				" and a.LATEST_STATUS_IND='Y'" +
				" and a.STATUS_CD = '020' ";
		
		logger.debug(" updateStatusAlertWQ RMK_SQL:"+RMK_SQL);


		try {
				simpleJdbcTemplate.update(RMK_SQL);
		} catch (Exception e) {
			logger.error("Exception caught in updateStatusAlertWQ()", e);
			throw new DAOException(e.getMessage(), e);
		}
		
		
	}
	
	public String getSuspendReasonByCode(String code) 
	throws DAOException {
		logger.debug("getSuspendReasonByCode code: " + code);
		
		String SQL=	"    select DESCRIPTION from w_code_lkup" +
				" where code = '"+code+"'" +
				" and Grp_id = 'SB_PCD_SUSPEND_REA'" ;
		
		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
		    public String mapRow(ResultSet rs, int rowNum) throws SQLException {
		    	String temp = "";
		    	temp=(rs.getString("DESCRIPTION"));
		        return temp;
		    }
		};
		
    	List<String> tempList = new ArrayList<String>();
		
		try {
			logger.debug("getSuspendReasonByCode SQL: " + SQL);
			tempList = simpleJdbcTemplate.query(SQL, mapper);
		} catch (EmptyResultDataAccessException erdae) {
			logger.debug("EmptyResultDataAccessException");
			tempList = null;
		} catch (Exception e) {
			logger.debug("Exception caught in getSuspendReasonByCode:", e);
			throw new DAOException(e.getMessage(), e);
		}	
		if(tempList.size()>0){
			return tempList.get(0);
		}else{
			return null;
		}
	}

	 public Boolean needSuspendWQ(String orderStatus, String createBy)
	throws DAOException {
		logger.debug("needSuspendWQ orderStatus: " + orderStatus+"  createBy:"+createBy);
		
		String checkOrderStatusSQL=	"select count(*) count from w_code_lkup " +
				"where grp_id = 'IMS_ALERT_ORD_STATUS'" +
				"			and code = 'ORD_STATUS'" +
				"			and DESCRIPTION = '"+orderStatus+"'";
		
		
		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
		    public String mapRow(ResultSet rs, int rowNum) throws SQLException {
		    	String temp = "";
		    	temp=(rs.getString("count"));
		        return temp;
		    }
		};
		
    	List<String> tempList = new ArrayList<String>();
		
		try {
			logger.debug("needSuspendWQ checkOrderStatusSQL: " + checkOrderStatusSQL);
			tempList = simpleJdbcTemplate.query(checkOrderStatusSQL, mapper);
		} catch (EmptyResultDataAccessException erdae) {
			logger.debug("EmptyResultDataAccessException");
			tempList = null;
		} catch (Exception e) {
			logger.debug("Exception caught in needSuspendWQ checkOrderStatusSQL:", e);
			throw new DAOException(e.getMessage(), e);
		}	
		
		
		if(tempList.size()>0&&Integer.parseInt(tempList.get(0))<1){
			logger.debug("needSuspendWQ orderStatus:" + orderStatus+" not okay, no need SuspendWQ");
			return false;
		}else{
			logger.debug("needSuspendWQ orderStatus:" + orderStatus+" okay, need SuspendWQ");
		}
		

		String checkUserChannelSQL=		"select count(*) count from w_code_lkup" +
				"			where grp_id = 'IMS_ALERT_ORD_STATUS'" +
				"			and code = 'CHANNEL_ID'" +
				"			and DESCRIPTION = (SELECT channel_id" +
				"             FROM bomweb_sales_assignment" +
				"            WHERE staff_id = '"+createBy+"'" +
				"              AND (end_date IS NULL OR TRUNC (end_date) >= TRUNC (SYSDATE))" +
				"              AND ROWNUM = 1) ";
		
		try {
			logger.debug("needSuspendWQ checkUserChannelSQL: " + checkUserChannelSQL);
			tempList = simpleJdbcTemplate.query(checkUserChannelSQL, mapper);
		} catch (EmptyResultDataAccessException erdae) {
			logger.debug("EmptyResultDataAccessException");
			tempList = null;
		} catch (Exception e) {
			logger.debug("Exception caught in needSuspendWQ checkUserChannelSQL:", e);
			throw new DAOException(e.getMessage(), e);
		}	
    	

		if(tempList.size()>0&&Integer.parseInt(tempList.get(0))>0){
			logger.debug("needSuspendWQ checkUserChannel:" + createBy+" okay, need SuspendWQ");
			return true;
		}else{
			logger.debug("needSuspendWQ checkUserChannel:" + createBy+" not okay, no need SuspendWQ");
			return false;
		}
		
	}
	 

		
		public String getSupportDocFaxSerial(String orderId, String docType)throws DAOException {
			logger.debug("getSupportDocFaxSerial orderId: " + orderId +" docType"+docType);			
			String SQL=	"   select * from " +
					" (" +
					" select serial from bomweb_all_ord_doc where " +
					" order_id = ? and DOC_TYPE = ? and serial is not null order by seq_num desc" +
					" ) " +
					" where rownum = 1" ;
			
			ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
			    public String mapRow(ResultSet rs, int rowNum) throws SQLException {
			        return rs.getString("serial");
			    }
			};			
	    	List<String> tempList = new ArrayList<String>();			
			try {
				tempList = simpleJdbcTemplate.query(SQL, mapper,orderId,docType);
			} catch (EmptyResultDataAccessException erdae) {
				logger.debug("EmptyResultDataAccessException");
				tempList = null;
			} catch (Exception e) {
				logger.debug("Exception caught in getSuspendReasonByCode:", e);
				throw new DAOException(e.getMessage(), e);
			}	
			if(tempList.size()>0&&tempList.get(0)!=null&&!"".equals(tempList.get(0))){
				return tempList.get(0);
			}else{
				return null;
			}
		}
}
