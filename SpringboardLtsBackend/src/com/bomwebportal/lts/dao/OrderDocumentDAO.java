package com.bomwebportal.lts.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Arrays;
import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.bomwebportal.dao.BaseDAO;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.lts.dto.DocWaiveReasonDTO;
import com.bomwebportal.lts.dto.OrderDocDTO;
import com.bomwebportal.lts.dto.order.AllOrdDocDTO;

public class OrderDocumentDAO extends BaseDAO {
	
	
	public List<DocWaiveReasonDTO> getWaiveReasonList(String docType) throws DAOException {
		
		ParameterizedRowMapper<DocWaiveReasonDTO> mapper = new ParameterizedRowMapper<DocWaiveReasonDTO>() {
			public DocWaiveReasonDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				DocWaiveReasonDTO waiveReason = new DocWaiveReasonDTO();
				waiveReason.setDocType(rs.getString("DOC_TYPE"));
				waiveReason.setWaiveReasonCd(rs.getString("WAIVE_REASON"));
				waiveReason.setWaiveReasonDesc(rs.getString("WAIVE_DESC"));
				waiveReason.setDefaultWaiveInd(rs.getString("DEFAULT_WAIVE_IND"));
				return waiveReason;
			}
		};
		
		StringBuilder sql= new StringBuilder();
		
		sql.append("SELECT DOC_TYPE, WAIVE_REASON, WAIVE_DESC, DEFAULT_WAIVE_IND ")
			.append(" FROM BOMWEB_ALL_ORD_DOC_WAIVE")	   
			.append(" WHERE lob = 'LTS'")
			.append(" AND doc_type = ? ")
			.append(" ORDER BY WAIVE_REASON ");
			

		try {
			if (logger.isDebugEnabled()) {
				logger.debug("getWaiveReasonList() Sql: " + sql.toString());	
			}
			
			return simpleJdbcTemplate.query(sql.toString(), mapper, docType);
			
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException in getWaiveReasonList()");
			return null;
		} catch (Exception e) {
			logger.info("Exception caught in getWaiveReasonList():", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public OrderDocDTO getOrderDoc(String docType) throws DAOException {
		
		ParameterizedRowMapper<OrderDocDTO> mapper = new ParameterizedRowMapper<OrderDocDTO>() {
			public OrderDocDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				OrderDocDTO orderDoc = new OrderDocDTO();
				orderDoc.setDocType(rs.getString("DOC_TYPE"));
				orderDoc.setDocName(rs.getString("DOC_NAME"));
				orderDoc.setDocNameChi(rs.getString("DOC_NAME_CHI"));
				orderDoc.setDocDesc(rs.getString("DOC_DESC"));
				orderDoc.setStartDate(rs.getString("START_DATE"));
				orderDoc.setEndDate(rs.getString("END_DATE"));
				orderDoc.setMdoInd(rs.getString("MDO_IND"));
				orderDoc.setFaxUploadInd(rs.getString("FAX_UPLOAD_IND"));
				return orderDoc;
			}
		};
		
		StringBuilder sql= new StringBuilder();
		
		sql.append("SELECT DOC_TYPE, TYPE, DOC_NAME, DOC_NAME_CHI, DOC_DESC")
			.append(" , TO_CHAR(START_DATE, 'MM/DD/YYYY') START_DATE")
			.append(" , TO_CHAR(END_DATE, 'MM/DD/YYYY') END_DATE")
			.append(" , MDO_IND")
			.append(" , FAX_UPLOAD_IND")
			.append(" FROM bomweb_all_doc")	   
			.append(" WHERE lob = 'LTS'")
			.append(" AND doc_type = ? ");
			

		try {
			if (logger.isDebugEnabled()) {
				logger.debug("getOrderDoc() Sql: " + sql.toString());	
			}
			
			List<OrderDocDTO> orderDocList = simpleJdbcTemplate.query(sql.toString(), mapper, docType); 
			
			if (orderDocList == null || orderDocList.isEmpty()) {
				return null;
			}
			
			return orderDocList.get(0);
			
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException in getOrderDoc()");
			return null;
		} catch (Exception e) {
			logger.info("Exception caught in getOrderDoc():", e);
			throw new DAOException(e.getMessage(), e);
		}
	
	}
	
	public int insertAllOrderDocDTO(AllOrdDocDTO dto) throws DAOException {
		if (logger.isInfoEnabled()) {
			logger.info("insertAllOrderDocDTO @ AllOrdDocDAO is called");
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
				logger.info("insertAllOrderDocDTO() @ AllOrdDocDAO: " + sql);
			}
			return simpleJdbcTemplate.update(sql, params);
		} catch (Exception e) {
			if (logger.isInfoEnabled()) {
				logger.info("Exception caught in insertAllOrderDocDTO(): ", e);
			}
			throw new DAOException(e.getMessage(), e);
		}
	}
	public int updateOutdatedInd(String orderId, String docType,
			boolean isOutdated) throws DAOException {

		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE BOMWEB_ALL_ORD_DOC SET");
		sql.append(" OUTDATED_IND = :outdatedInd");
		sql.append(" WHERE ORDER_ID = :orderId");
		sql.append(" AND DOC_TYPE = :docType");

		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("outdatedInd", isOutdated ? "Y" : "NULL");
			params.addValue("orderId", orderId);
			params.addValue("docType", docType);
			return simpleJdbcTemplate.update(sql.toString(), params);
		} catch (Exception e) {
			logger.error("Exception caught in updateOutdatedInd(): ", e);
			throw new DAOException(e.getMessage(), e);
		}

	}

	public int updateAllOrdDocAssgnDTOOutdatedInd(String order_id, String doc_type) throws DAOException {
		if (logger.isInfoEnabled()) {
			logger.info("updateAllOrdDocAssgnDTOOutdatedInd @ AllOrdDocAssgnDAO is called");
		}
		String sql = "UPDATE bomweb_all_ord_doc" +
				" SET" +
				" OUTDATED_IND = :outdatedInd" +
				" WHERE ORDER_ID = :orderId" +
				" and DOC_TYPE = :docType";
		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("outdatedInd", "Y");
			params.addValue("docType", doc_type);
			params.addValue("orderId", order_id);
			if (logger.isInfoEnabled()) {
				logger.info("updateAllOrdDocAssgnDTOOutdatedInd() @ AllOrdDocAssgnDAO: " + sql);
			}
			return simpleJdbcTemplate.update(sql, params);
		} catch (Exception e) {
			if (logger.isInfoEnabled()) {
				logger.info("Exception caught in updateAllOrdDocAssgnDTOOutdatedInd(): ", e);
			}
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public List<AllOrdDocDTO> getCollectedDocListByOrderId(String orderId) throws DAOException {
		String sql = " SELECT a.* " +
				" FROM bomweb_all_ord_doc a, bomweb_all_ord_doc_assgn b "+
				" WHERE a.order_id = ? " +
				" AND a.order_id = b.order_id " +
				" AND a.doc_type = b.doc_type " +
				" AND b.collected_ind = 'Y'" +
				" AND b.waive_reason IS NULL " +
				" AND (b.mark_del_ind IS NULL OR b.mark_del_ind != 'Y') " +
				" AND (a.outdated_ind IS NULL " +
				" OR a.outdated_ind != 'Y')" + 
				" order by a.doc_type, a.seq_num ";
				
		try{
			if (logger.isDebugEnabled()) {
				logger.debug("getCollectedDocListByOrderId() Sql: " + sql.toString());	
			}
			
			return simpleJdbcTemplate.query(sql.toString(), getRowMapper(), orderId);
			
		} catch (Exception e){
			if (logger.isInfoEnabled()) {
				logger.info("Exception caught in getCollectedDocListByOrderId(): ", e);
			}
			throw new DAOException(e.getMessage(), e);
		}
		
	}
	
	public List<AllOrdDocDTO> getCollectedDocListByOrderIdAndDocType(String orderId, String docType) throws DAOException {
		String sql = " SELECT a.* " +
				" FROM bomweb_all_ord_doc a, bomweb_all_ord_doc_assgn b "+
				" WHERE a.order_id = ? " +
				" AND a.order_id = b.order_id " +
				" AND a.doc_type = ? " +
				" AND a.doc_type = b.doc_type " +
				" AND b.collected_ind = 'Y'" +
				" AND b.waive_reason IS NULL " +
				" AND (b.mark_del_ind IS NULL OR b.mark_del_ind != 'Y') " +
				" AND (a.outdated_ind IS NULL " +
				" OR a.outdated_ind != 'Y')" + 
				" order by a.doc_type, a.seq_num ";
				
		try{
			if (logger.isDebugEnabled()) {
				logger.debug("getCollectedDocListByOrderIdAndDocType() Sql: " + sql.toString());	
			}
			
			return simpleJdbcTemplate.query(sql.toString(), getRowMapper(), orderId, docType);
			
		} catch (Exception e){
			if (logger.isInfoEnabled()) {
				logger.info("Exception caught in getCollectedDocListByOrderIdAndDocType(): ", e);
			}
			throw new DAOException(e.getMessage(), e);
		}
		
	}
	
    public String getMaxCollectedDocSeqByOrderIdAndDocType(String orderId, String docType) throws DAOException {
    	String sql = " SELECT MAX(a.seq_num) " +
				" FROM bomweb_all_ord_doc a, bomweb_all_ord_doc_assgn b "+
				" WHERE a.order_id = ? " +
				" AND a.order_id = b.order_id " +
				" AND a.doc_type = ? " +
				" AND a.doc_type = b.doc_type " +
				" AND b.collected_ind = 'Y'" +
				" AND b.waive_reason IS NULL " +
				" AND (b.mark_del_ind IS NULL OR b.mark_del_ind != 'Y') " +
				" AND (a.outdated_ind IS NULL " +
				" OR a.outdated_ind != 'Y')" ;
    	
		try {
			if (logger.isDebugEnabled()) {
				logger.debug("getMaxCollectedDocSeqByOrderIdAndDocType() Sql: " + sql.toString());	
			}
			
			return simpleJdbcTemplate.queryForObject(sql, String.class, orderId, docType);
			
		} catch (EmptyResultDataAccessException erdae) {
			return null;
		} catch (Exception e) {
			logger.error("Error in getMaxCollectedDocSeqByOrderIdAndDocType()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public List<AllOrdDocDTO> getLatestCollectedDocListByOrderIdAndDocType(String orderId, String docType, String maxSeq) throws DAOException {
		String sql = " SELECT a.* " +
				" FROM bomweb_all_ord_doc a, bomweb_all_ord_doc_assgn b "+
				" WHERE a.order_id = ? " +
				" AND a.order_id = b.order_id " +
				" AND a.doc_type = ? " +
				" AND a.doc_type = b.doc_type " +
				" AND b.collected_ind = 'Y'" +
				" AND b.waive_reason IS NULL " +
				" AND (b.mark_del_ind IS NULL OR b.mark_del_ind != 'Y') " +
				" AND (a.outdated_ind IS NULL " +
				" OR a.outdated_ind != 'Y')" + 
				" AND a.seq_num = ? ";
				
		try{
			if (logger.isDebugEnabled()) {
				logger.debug("getLatestCollectedDocListByOrderIdAndDocType() Sql: " + sql.toString());	
			}
			
			return simpleJdbcTemplate.query(sql.toString(), getRowMapper(), orderId, docType, maxSeq);
			
		} catch (Exception e){
			if (logger.isInfoEnabled()) {
				logger.info("Exception caught in getCollectedDocListByOrderIdAndDocType(): ", e);
			}
			throw new DAOException(e.getMessage(), e);
		}
		
	}
	
	public List<AllOrdDocDTO> getAllOrdDocListByDocTypeOrderId(String pOrderId, String pDocType) throws DAOException {
		String sql = " SELECT ORDER_ID, DOC_TYPE, SEQ_NUM, " +
				" FILE_PATH_NAME, PROCESS_DATE, CREATE_BY, CREATE_DATE, " +
				" LAST_UPD_BY, LAST_UPD_DATE, SERIAL" +
				" FROM bomweb_all_ord_doc"+
				" WHERE order_id = ? " +
				" and doc_type = ? " +
				" order by seq_num ";
		try{
			if (logger.isDebugEnabled()) {
				logger.debug("getAllOrdDocListByDocTypeOrderId() Sql: " + sql.toString());	
			}
			
			return simpleJdbcTemplate.query(sql.toString(), getRowMapper(), pOrderId, pDocType);
			
		} catch (Exception e){
			if (logger.isInfoEnabled()) {
				logger.info("Exception caught in getAllOrdDocListByDocTypeOrderId(): ", e);
			}
			throw new DAOException(e.getMessage(), e);
		}		
	}	
	
	public List<AllOrdDocDTO> getAllOrdDocListByOrderId(String orderId) throws DAOException {
		String sql = " SELECT ORDER_ID, DOC_TYPE, SEQ_NUM, " +
				" FILE_PATH_NAME, PROCESS_DATE, CREATE_BY, CREATE_DATE, " +
				" LAST_UPD_BY, LAST_UPD_DATE, SERIAL" +
				" FROM bomweb_all_ord_doc"+
				" WHERE order_id = ? " +
				" AND (outdated_ind IS NULL " +
				" OR outdated_ind != 'Y')" + 
				" order by doc_type, seq_num ";
				
//				" AND NVL(outdated_ind, 'N') = 'N'";
		
		try{
			if (logger.isDebugEnabled()) {
				logger.debug("getAllOrdDocListByOrderId() Sql: " + sql.toString());	
			}
			
			return simpleJdbcTemplate.query(sql.toString(), getRowMapper(), orderId);
			
		} catch (Exception e){
			if (logger.isInfoEnabled()) {
				logger.info("Exception caught in getAllOrdDocListByOrderId(): ", e);
			}
			throw new DAOException(e.getMessage(), e);
		}
		
	}
	
	public void markCollectedByDocType(String orderId, String docType) throws DAOException {
		
	}
	
	private ParameterizedRowMapper<AllOrdDocDTO> getRowMapper() {
		return new ParameterizedRowMapper<AllOrdDocDTO>() {
			public AllOrdDocDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				AllOrdDocDTO dto = new AllOrdDocDTO();
				dto.setOrderId(rs.getString("ORDER_ID"));
				dto.setDocType(rs.getString("DOC_TYPE"));
				dto.setSeqNum(rs.getInt("SEQ_NUM"));
				dto.setFilePathName(rs.getString("FILE_PATH_NAME"));
				dto.setProcessDate(rs.getDate("PROCESS_DATE"));
				dto.setCreateBy(rs.getString("CREATE_BY"));
				dto.setCreateDate(rs.getTimestamp("CREATE_DATE"));
				dto.setLastUpdBy(rs.getString("LAST_UPD_BY"));
				dto.setLastUpdDate(rs.getTimestamp("LAST_UPD_DATE"));
//				dto.setRowId(rs.getString("ROW_ID"));
				dto.setSerial(rs.getString("SERIAL"));
				return dto;
			}
		};
	}
	
	public int updateAllOrdDocFilePath(String pOrderId, String pDocType, String pSeq, String pFileName) throws DAOException {
		if (logger.isInfoEnabled()) {
			logger.info("updateAllOrdDocFilePath @ AllOrdDocAssgnDAO is called");
		}
		String sql = "UPDATE bomweb_all_ord_doc" +
				" SET" +
				" FILE_PATH_NAME = :fileName" +
				" WHERE ORDER_ID = :orderId" +
				" and DOC_TYPE = :docType" +
				" and SEQ_NUM = (SELECT MAX(SEQ_NUM) from bomweb_all_ord_doc where ORDER_ID = :ordId and DOC_TYPE = :dtype and OUTDATED_IND is null)";
		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("fileName", pFileName);
			params.addValue("orderId", pOrderId);
			params.addValue("docType", pDocType);
			params.addValue("ordId", pOrderId);
			params.addValue("dtype", pDocType);
			//params.addValue("seqNum", pSeq);
			
			if (logger.isInfoEnabled()) {
				logger.info("updateAllOrdDocFilePath() @ AllOrdDocAssgnDAO: " + sql);
			}
			return simpleJdbcTemplate.update(sql, params);
		} catch (Exception e) {
			if (logger.isInfoEnabled()) {
				logger.info("Exception caught in updateAllOrdDocFilePath(): ", e);
			}
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public List<OrderDocDTO> getOrderDocBySubcItem(String[] subcItemIds) throws DAOException {

		ParameterizedRowMapper<OrderDocDTO> mapper = new ParameterizedRowMapper<OrderDocDTO>() {
			public OrderDocDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				OrderDocDTO orderDoc = new OrderDocDTO();
				orderDoc.setDocType(rs.getString("DOC_TYPE"));
				orderDoc.setDocName(rs.getString("DOC_NAME"));
				orderDoc.setDocDesc(rs.getString("DOC_DESC"));
				orderDoc.setStartDate(rs.getString("START_DATE"));
				orderDoc.setEndDate(rs.getString("END_DATE"));
				orderDoc.setMdoInd(rs.getString("MDO_IND"));
				orderDoc.setFaxUploadInd(rs.getString("FAX_UPLOAD_IND"));
				return orderDoc;
			}
		};
		
		StringBuilder sql= new StringBuilder();
		
		sql.append("SELECT wida.DOC_TYPE DOC_TYPE, TYPE, DOC_NAME, DOC_NAME_CHI, DOC_DESC")
			.append(" , TO_CHAR(START_DATE, 'MM/DD/YYYY') START_DATE")
			.append(" , TO_CHAR(END_DATE, 'MM/DD/YYYY') END_DATE")
			.append(" , MDO_IND")
			.append(" , FAX_UPLOAD_IND")
			.append(" FROM bomweb_all_doc bad, w_item_doc_assgn wida")	   
			.append(" WHERE bad.doc_type = wida.doc_type ")
			.append(" AND wida.item_id in ( :subcItemIds ) ")
			.append(" ORDER BY wida.item_id, wida.item_doc_seq ");

		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("subcItemIds", Arrays.asList(subcItemIds));

		try {
			if (logger.isDebugEnabled()) {
				logger.debug("getOrderDocBySubcItem() Sql: " + sql.toString());	
			}
			
			List<OrderDocDTO> orderDocList = simpleJdbcTemplate.query(sql.toString(), mapper, params); 
			
			if (orderDocList == null || orderDocList.isEmpty()) {
				return null;
			}
			
			return orderDocList;
			
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException in getOrderDocBySubcItem()");
			return null;
		} catch (Exception e) {
			logger.info("Exception caught in getOrderDocBySubcItem():", e);
			throw new DAOException(e.getMessage(), e);
		}
	}

}
