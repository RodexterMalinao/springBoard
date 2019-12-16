package com.bomltsportal.dao;

import org.apache.commons.lang.StringUtils;

import com.bomltsportal.dto.RequestLogDTO;
import com.bomltsportal.util.BomLtsConstant;
import com.bomwebportal.dao.BaseDAO;
import com.bomwebportal.exception.DAOException;

public class OnlineSalesLogDAO extends BaseDAO {

	public void insertOnlineSalesLog(String channel,String action, String ip_addr)
			throws Exception {
		

		String SQL = "	INSERT INTO w_online_sales_log "
				+ " (id, channel, action, ip_address, CREATE_DATE"
				+ "	)	"
				+ "	VALUES (w_online_sales_seq.nextVal, ?, ?, ?, SYSDATE)";

		logger.debug("INSERT INTO w_online_sales_request: channel=" 
				+ channel);
		try {
			if (simpleJdbcTemplate==null)logger.debug("simpleJdbcTemplate is null");
			simpleJdbcTemplate.update(SQL, channel,action,ip_addr);

		} catch (Exception e) {
			logger.error("Exception caught in insertOnlineSalesLog()", e);
		}
	}
	
	public void insertOnlineExceptionLog(String reqId, String pageId, String msg) throws DAOException{
		logger.debug("OnlineSalesLogDAO insertOnlineExceptionLog");
		
		String SQL = " insert into w_online_exception_log "+
					" (req_id, page_id, java_exception, create_date, lob) values "+
					" ( ?, ?, ?, sysdate, ?) ";
		
		try{
			simpleJdbcTemplate.update(SQL, reqId, pageId, StringUtils.substring(msg, 0, 3900), BomLtsConstant.LOB_LTS);
			
		} catch(Exception e){
			logger.error("Exception caught in insertOnlineExceptionLog()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	/*
	public String getNewOnlineExceptionLogId() throws DAOException{
		logger.debug("OnlineSalesLogDAO getNewOnlineExceptionLogId");
		
		String SQL = "SELECT NVL(TO_NUMBER(MAX(req_id)) + 1, 1000000) FROM w_online_exception_log " +
					" WHERE req_id >= 1000000";
		
		try{
			return simpleJdbcTemplate.queryForObject(SQL, String.class);
		}catch (EmptyResultDataAccessException erdae) {
			logger.error("EmptyResultDataAccessException in getNewOnlineExceptionLogId()");
			return null;
		} catch (Exception e) {
			logger.error("Exception caught in getNewOnlineExceptionLogId():", e);
			throw new DAOException();
		}
		
	}
	*/
	
	public String getRequestId() throws DAOException{			

		logger.debug("getRequestId");
		String reqId;
		String Sql = "select W_ONLINE_IMS_REQ_SEQ.nextval from dual ";
//		String Sql = "SELECT NVL(TO_NUMBER(MAX(req_id)) + 1, 1000000) FROM w_online_request " +
//					" WHERE req_id >= 1000000";
		
		try {
			reqId = (String) simpleJdbcTemplate.queryForObject(Sql,
					String.class);

		} catch (Exception e) {
			logger.info("Exception caught in getRequestId():", e);
			throw new DAOException(e.getMessage(), e);
		}

		return reqId;
		
	}
	
	public void insertOnlineRequest(RequestLogDTO dto) throws DAOException{
		logger.debug("insertOnlineRequest");
		
		String SQL = "	insert into w_online_request	"+
		"	(req_id, ip_address, session_id, create_date, " +
		"	useragent, lob, sub_type, device_type, channel ) " +	
		"   values	"+
		"	(?, ?, ?, sysdate, " +
		"	substr(?, 1, 500), ?, ?, ?, substr(?, 1, 20) )	";
		
		try{
			
			simpleJdbcTemplate.update(SQL,
					dto.getReqId(), dto.getIpAddress(), dto.getSessionId(),
					dto.getUseragent(), dto.getLob(), dto.getSubType(), dto.getDeviceType(), dto.getChannel()
					);
			
		}catch (Exception e) {
			logger.error("Exception caught in insertOnlineRequest()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public void insertOnlinePageTrack(String reqId, String pageId, String exPageId,String reqSeq) throws DAOException{
		logger.debug("insertOnlinePageTrack");
		
		String SQL = " insert into w_online_page_track "+
					" (req_id, page_id, ex_page_id, time_stamp, req_seq) values "+
					" (?, ?, ?, sysdate, ?) ";
		
		try{		
			simpleJdbcTemplate.update(SQL,
					reqId, pageId, exPageId, reqSeq
					);						
		}catch(Exception e){
			logger.error("Exception caught in insertOnlinePageTrack()", e);
			throw new DAOException(e.getMessage(), e);
		}
		
	}
	
	public void insertOnlineDetailTrack(String reqId, String pageId, String itemCd, String itemVal, String reqSeq) throws DAOException{
		logger.debug("insertOnlineDetailTrack");				
		
		String SQL = " insert into w_online_detail_track "+
					" (req_id, page_id, item_cd, item_val, create_date, req_seq) values "+
					" (?, ?, ?, ?, sysdate, ?) ";
		
		try{			
			simpleJdbcTemplate.update(SQL,
					reqId, pageId, itemCd, itemVal, reqSeq
					);
			
		}catch(Exception e){
			logger.error("Exception caught in insertOnlineDetailTrack()", e);
			throw new DAOException(e.getMessage(), e);
		}
		
	}
	
}
