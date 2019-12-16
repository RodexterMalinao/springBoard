package com.bomwebportal.ims.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.sql.Types;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import com.bomwebportal.dao.BaseDAO;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.ims.dto.ImsAllOrdDocDTO;
import com.bomwebportal.ims.dto.ImsAllOrdDocWaiveDTO;
import com.bomwebportal.dto.AllOrdDocWaiveDTO;
import com.bomwebportal.dto.AllDocDTO.DocType;
import com.google.gson.Gson;

public class ImsOrderDocumentDAO extends BaseDAO {
	

    private Gson gson = new Gson();
	public void updateWqFaxSerial(String orderId, String serial,String user) throws DAOException {
		String wqId  = null;
		String wqBatchId  = null;
		String SQL="select * from q_wq_wp_search_id_v where wq_nature_id = 61 and sb_id = ? and rownum=1";

		String sql = "Insert into OPS$CNM.Q_WQ_REMARKS" +
				"		   (WQ_ID, REMARK_SEQ, WQ_BATCH_ID, WQ_NATURE_ID, CREATE_BY, CREATE_DATE, LAST_UPD_BY, LAST_UPD_DATE, remarks)" +
				"		 Values" +
				"		   (:wq_id, (select nvl(max(REMARK_SEQ)+1,1) from Q_WQ_REMARKS where wq_id = :wq_id), :wqBatchId, 61, :user, sysdate, :user, sysdate," +
				" :remarks )" ;
		try {
			List<Map<String, Object>> rows = simpleJdbcTemplate.queryForList(SQL,orderId);
			logger.info("orderId:"+orderId+"  q_wq_wp_search_id_v ROWS:"+gson.toJson(rows));
			for (Map row : rows) {	
				wqId = row.get("WQ_ID").toString();
				wqBatchId = row.get("WQ_BATCH_ID").toString();
			}	
			try {
				if(wqId!=null && wqBatchId!=null){
					MapSqlParameterSource params = new MapSqlParameterSource();
					params.addValue("wq_id", wqId);
					params.addValue("wqBatchId", wqBatchId);
					params.addValue("remarks", "Fax Serail:"+serial);
					params.addValue("user", user);
					logger.info(simpleJdbcTemplate.update(sql, params));	
				}else{
					if(wqId==null){
						logger.error("wqId is null");						
					}
					if(wqBatchId==null){
						logger.error("wqBatchId is null");								
					}
				}
			}catch (Exception e) {
				logger.error("updateWqFaxSerial error when insert remarks:",e);
			}
		}catch (Exception e) {
			logger.error("updateWqFaxSerial error when select wq info:",e);
		}		 
	}
	
	
	
	public List<ImsAllOrdDocWaiveDTO> getWaiveReasonList(String docType, String DisMode) throws DAOException {
		
		ParameterizedRowMapper<ImsAllOrdDocWaiveDTO> mapper = new ParameterizedRowMapper<ImsAllOrdDocWaiveDTO>() {
			public ImsAllOrdDocWaiveDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				ImsAllOrdDocWaiveDTO dto = new ImsAllOrdDocWaiveDTO();
				dto.setDocType(DocType.valueOf(rs.getString("DOC_TYPE")));
				dto.setLob(rs.getString("LOB"));
				dto.setWaiveReason(rs.getString("WAIVE_REASON"));
				dto.setWaiveDesc(rs.getString("WAIVE_DESC"));
				dto.setCreateBy(rs.getString("CREATE_BY"));
				dto.setCreateDate(rs.getTimestamp("CREATE_DATE"));
				dto.setLastUpdBy(rs.getString("LAST_UPD_BY"));
				dto.setLastUpdDate(rs.getTimestamp("LAST_UPD_DATE"));
				dto.setRowId(rs.getString("ROW_ID"));
				dto.setDEFAULT_WAIVE_IND(rs.getString("DEFAULT_WAIVE_IND"));
				return dto;
			}
		};
		
		StringBuilder sql= new StringBuilder();
		sql.append("SELECT DOC_TYPE, WAIVE_REASON, WAIVE_DESC, DEFAULT_WAIVE_IND ")
		.append(", LOB , CREATE_BY , CREATE_DATE , LAST_UPD_BY, LAST_UPD_DATE , rowid ROW_ID ")	   
			.append(" FROM BOMWEB_ALL_ORD_DOC_WAIVE")	   
			.append(" WHERE lob = 'IMS'")
			.append(" AND doc_type = '"+docType+"' ")
   			.append(" and default_waive_ind in ( '"+DisMode+"','Y')")
			.append(" ORDER BY WAIVE_REASON ");
			

		try {
//			if (logger.isDebugEnabled()) {
//				logger.debug("getWaiveReasonList() Sql: " + sql.toString());	
//			}
			
			return simpleJdbcTemplate.query(sql.toString(), mapper);
			
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException in getWaiveReasonList()");
			return null;
		} catch (Exception e) {
			logger.info("Exception caught in getWaiveReasonList():", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public List<String> getImsDocTypeList() throws DAOException {
		
		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
			public String mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				String tempResult = rs.getString("DOC_TYPE");
				return tempResult;
			}
		};
		
		String sql= "select doc_type from BOMWEB_ALL_DOC where lob = 'IMS' ";

		try {
			if (logger.isDebugEnabled()) {
				logger.debug("getImsDocTypeList() Sql: " + sql.toString());	
			}
			
			return simpleJdbcTemplate.query(sql, mapper);
			
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException in getImsDocTypeList()");
			return null;
		} catch (Exception e) {
			logger.info("Exception caught in getImsDocTypeList():", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	//Added by Tony
	public int insertImsAllOrderDocDTO(ImsAllOrdDocDTO dto) throws DAOException {
		if (logger.isInfoEnabled()) {
			logger.info("insertImsAllOrderDocDTO @ ImsOrderDocumentDAO is called");
		}
		String sql = "INSERT INTO bomweb_all_ord_doc" +
				" (" +
				" ORDER_ID" +
				" , DOC_TYPE" +
				" , SEQ_NUM" +
				" , FILE_PATH_NAME" +
				" , PROCESS_DATE" +
				" , CREATE_BY" +
				" , CREATE_DATE" +
				" , LAST_UPD_BY" +
				" , LAST_UPD_DATE" +
				" , SERIAL" +
				" ) VALUES (" +
				" :orderId" +
				" , :docType" +
				" , (SELECT NVL(MAX(SEQ_NUM), 0) + 1 FROM bomweb_all_ord_doc WHERE ORDER_ID = :orderId AND DOC_TYPE = :docType)" +
				" , :filePathName" +
				" , trunc(:processDate)" +
				" , :createBy" +
				" , sysdate" +
				" , :lastUpdBy" +
				" , sysdate" +
				" , :serial" +
				" )";
		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("orderId", dto.getOrderId());
			params.addValue("docType", dto.getDocType());
			params.addValue("filePathName", dto.getFilePathName());
			params.addValue("processDate", dto.getProcessDate(), Types.DATE);
			params.addValue("createBy", dto.getCreateBy());
			params.addValue("lastUpdBy", dto.getLastUpdBy());
			params.addValue("serial", dto.getSerial());
			if (logger.isInfoEnabled()) {
				logger.info("insertImsAllOrderDocDTO @ ImsOrderDocumentDAO: " + sql);
			}
			return simpleJdbcTemplate.update(sql, params);
		} catch (Exception e) {
			if (logger.isInfoEnabled()) {
				logger.info("Exception caught in insertImsAllOrderDocDTO: ", e);
			}
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public String getImsAllOrderDocFaxSerial(String orderId, String docType) throws DAOException{
		logger.info("getImsAllOrderDocFaxSerial is called");
		
		String faxSerial = null;
		
		try{
			SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate);
			
			//String SQL = "select org_staff_id from BOMWEB_SALES_PROFILE where staff_id=?";
			String SQL = "	SELECT serial "+
					"	    FROM   bomweb_all_ord_doc	"+
					"	    WHERE  order_id = ?	"+
					"	    AND    doc_type = ?	"+
					"		AND outdated_ind is null ";			
			
			faxSerial = simpleJdbcTemplate.queryForObject(SQL, String.class, orderId, docType);
		}catch (EmptyResultDataAccessException erdae){
			logger.debug("EmptyResultDataAccessException");
			faxSerial = null;
		}catch (Exception e) {
				logger.error("Exception caught in getOrgSalesCd()", e);
				throw new DAOException(e.getMessage(), e);
		}
		return faxSerial;
	}

	public int insertImsEdfRef(String orderId, String edfRef, String user) throws DAOException {
		if (logger.isInfoEnabled()) {
			logger.info("insertImsAllOrderDocDTO @ ImsOrderDocumentDAO is called");
		}
		String sql = "INSERT INTO BOMWEB_ORDER_SERVICE_OTHER" +
				" (" +
				" ORDER_ID" +
				" , DTL_ID" +
				" , CREATE_BY" +
				" , CREATE_DATE" +
				" , LAST_UPD_BY" +
				" , LAST_UPD_DATE" +
				" , EDF_REF" +
				" ) VALUES (" +
				" :orderId" +
				" , 1" +
				" , :createBy" +
				" , sysdate" +
				" , :lastUpdBy" +
				" , sysdate" +
				" , :edfRef" +
				" )";
		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("orderId", orderId);
			params.addValue("createBy", user);
			params.addValue("lastUpdBy", user);
			params.addValue("edfRef", edfRef);
			if (logger.isInfoEnabled()) {
				logger.info("insertImsEdfRef @ ImsOrderDocumentDAO: " + sql);
			}
			return simpleJdbcTemplate.update(sql, params);
		} catch (Exception e) {
			if (logger.isInfoEnabled()) {
				logger.info("Exception caught in insertImsEdfRef: ", e);
			}
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public int updateImsEdfRef(String orderId, String dtlId, String edfRef, String user) throws DAOException {
		
		String sql = "UPDATE BOMWEB_ORDER_SERVICE_OTHER SET " +
				" EDF_REF = :edfRef , " +
				" LAST_UPD_DATE = SYSDATE, " +
				" LAST_UPD_BY = :user " +
				" WHERE ORDER_ID = :orderId " +
				" AND DTL_ID = :dtlId ";
		
		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("edfRef", edfRef);
			params.addValue("user", user);
			params.addValue("orderId", orderId);
			params.addValue("dtlId", dtlId);
			return simpleJdbcTemplate.update(sql.toString(), params);
			
		} catch (Exception e) {
			logger.error("Exception caught in updateImsEdfRef(): ", e);
			throw new DAOException(e.getMessage(), e);
		}
		
	}
	
	public boolean checkImsEdfRef(String orderId, String dtlId) throws DAOException {
		
		String sql = "SELECT count(1) FROM BOMWEB_ORDER_SERVICE_OTHER" +
				" WHERE ORDER_ID = ? " +
				" AND DTL_ID = ? ";
		
		try {
			
			int count = simpleJdbcTemplate.queryForInt(sql, orderId, dtlId);
			
			return count > 0 ;
			
		} catch (Exception e) {
			logger.error("Exception caught in updateImsEdfRef(): ", e);
			throw new DAOException(e.getMessage(), e);
		}
		
	}
	//Added by Tony end
}
