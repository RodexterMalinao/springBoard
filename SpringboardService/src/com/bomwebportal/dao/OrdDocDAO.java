package com.bomwebportal.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.bomwebportal.dao.BaseDAO;
import com.bomwebportal.dto.DocTypeDTO;
import com.bomwebportal.dto.OrdDocAssgnDTO;
import com.bomwebportal.dto.OrdDocDTO;
import com.bomwebportal.dto.OrdDocDTO.VerifyInd;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.service.OrdDocService.AuditLogActionCd;

public class OrdDocDAO extends BaseDAO {
	
	
	private final static ParameterizedRowMapper<DocTypeDTO> docTypeDTOMapper = new ParameterizedRowMapper<DocTypeDTO>() {
		public DocTypeDTO mapRow(ResultSet rs, int rowNum)
				throws SQLException {
			DocTypeDTO dto = new DocTypeDTO();
			dto.setDocType(rs.getString("doc_type"));
			dto.setLob(rs.getString("lob"));
			dto.setType(rs.getString("type"));
			dto.setDocName(rs.getString("doc_name"));
			dto.setDocNameChi(rs.getString("doc_name_chi"));
			dto.setDocDesc(rs.getString("doc_desc"));
			dto.setStartDate(rs.getTimestamp("start_date"));
			dto.setEndDate(rs.getTimestamp("end_date"));
			dto.setWaterMark(rs.getString("water_mark"));
			return dto;
		}
	};
	
	private final static ParameterizedRowMapper<OrdDocAssgnDTO> ordDocAssgnDTOMapper = new ParameterizedRowMapper<OrdDocAssgnDTO>() {
		public OrdDocAssgnDTO mapRow(ResultSet rs, int rowNum)
				throws SQLException {
			OrdDocAssgnDTO dto = new OrdDocAssgnDTO();
			dto.setOrderId(rs.getString("order_id"));
			dto.setDocType(rs.getString("doc_type"));
			dto.setDocName(rs.getString("doc_name"));
			dto.setDocNameChi(rs.getString("doc_name_chi"));
			dto.setCollectedInd(rs.getString("collected_ind"));
			dto.setLastUpdBy(rs.getString("last_upd_by"));
			dto.setLastUpdDate(rs.getTimestamp("last_upd_date"));
			
			return dto;
		}
	};
	
	private final static ParameterizedRowMapper<OrdDocDTO> ordDocDTOMapper = new ParameterizedRowMapper<OrdDocDTO>() {
		public OrdDocDTO mapRow(ResultSet rs, int rowNum)
				throws SQLException {
			OrdDocDTO dto = new OrdDocDTO();
			dto.setOrderId(rs.getString("order_id"));
			dto.setDocType(rs.getString("doc_type"));
			dto.setSeqNum(rs.getInt("seq_num"));
			dto.setFilePathName(rs.getString("file_path_name"));
			dto.setCaptureBy(rs.getString("create_by"));
			dto.setCaptureDate(rs.getTimestamp("create_date"));
			dto.setDocName(rs.getString("doc_name"));
			dto.setDocNameChi(rs.getString("doc_name_chi"));
			dto.setLastUpdBy(rs.getString("last_upd_by"));
			dto.setLastUpdDate(rs.getTimestamp("last_upd_date"));
			dto.setVerifyBy(rs.getString("verify_by"));
			dto.setVerifyDate(rs.getTimestamp("verify_date"));
			String verifyInd = rs.getString("verify_ind");
			if (StringUtils.isNotBlank(verifyInd)) {
				dto.setVerifyInd(VerifyInd.valueOf(verifyInd));
			}
			return dto;
		}
	};
	
	public List<DocTypeDTO> getAllDocTypes() throws DAOException {
		List<DocTypeDTO> list = new ArrayList<DocTypeDTO>();
		
		String SQL = "select dt.doc_type, dt.lob, dt.type, dt.doc_name, dt.doc_name_chi,\n"
			+ " dt.doc_desc, dt.start_date, dt.end_date, dt.water_mark \n"
			+ " from bomweb_all_doc dt \n"
			+ " order by doc_type";



		try {
			logger.debug("getAllDocTypes @ OrdDocDAO: \n" + SQL);

			list = simpleJdbcTemplate.query(SQL, docTypeDTOMapper);

		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException");
			list = null;
		} catch (Exception e) {
			logger.error("Exception caught in getRequiredDoc()", e);
			throw new DAOException(e.getMessage(), e);
		}
		return list;
	}
	
	
	public DocTypeDTO getDocType(String docType, String lob) throws DAOException {
		String SQL = "select dt.doc_type, dt.lob, dt.type, dt.doc_name, dt.doc_name_chi,\n"
			+ " dt.doc_desc, dt.start_date, dt.end_date, dt.water_mark \n"
			+ " from bomweb_all_doc dt \n"
			+ " where dt.doc_type=?";
		
		List<DocTypeDTO> list = new ArrayList<DocTypeDTO>();

		try {
			logger.debug("getDocType @ OrdDocDAO: \n" + SQL);

			list = simpleJdbcTemplate.query(SQL, docTypeDTOMapper, docType);

		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException");
			list = null;
			return null;
		} catch (Exception e) {
			logger.error("Exception caught in getRequiredDoc()", e);
			throw new DAOException(e.getMessage(), e);
		}

		return (list.size() > 0 )? list.get(0) : null;		
	}
	
	public DocTypeDTO getDocTypeForOrder(String docType, String orderId) throws DAOException {
		String SQL = "select dt.doc_type, dt.lob, dt.type, dt.doc_name, dt.doc_name_chi,\n"
			+ " dt.doc_desc, dt.start_date, dt.end_date, dt.water_mark \n"
			+ " from bomweb_all_doc dt, bomweb_order o \n"
			+ " WHERE dt.lob=o.lob \n"
			+ " AND dt.doc_type=:docType \n"
			+ " AND o.order_id=:orderId ";
		
		List<DocTypeDTO> list = new ArrayList<DocTypeDTO>();

		try {
			logger.debug("getDocType @ OrdDocDAO: \n" + SQL);

			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("docType", docType);
			params.addValue("orderId", orderId);
			list = simpleJdbcTemplate.query(SQL, docTypeDTOMapper, params);

		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException");
			list = null;
			return null;
		} catch (Exception e) {
			logger.error("Exception caught in getRequiredDoc()", e);
			throw new DAOException(e.getMessage(), e);
		}

		return (list.size() > 0 )? list.get(0) : null;		
	}
	
	

	public List<OrdDocAssgnDTO> getRequiredDoc(String orderId) throws DAOException {
		
		List<OrdDocAssgnDTO> list = new ArrayList<OrdDocAssgnDTO>();
		
		String SQL = "select ass.order_id, ass.doc_type doc_type, ass.collected_ind,\n"
			+" dt.doc_name, dt.doc_name_chi, dt.last_upd_by, dt.last_upd_date \n"
			+" from bomweb_all_ord_doc_assgn ass, bomweb_all_doc dt, bomweb_order o \n"
			+" where ass.doc_type=dt.doc_type \n"
			+"   and ass.order_id=o.order_id \n"
			+"   and dt.lob=o.lob \n"
			+"   and ass.order_id=? \n"
			+"   and nvl(ass.mark_del_ind,'N') <> 'Y' \n"
			+"   and ass.waive_reason is null \n"
			+" order by ass.doc_type";


		try {
			logger.debug("getRequiredDoc @ OrdDocDAO: \n" + SQL);

			list = simpleJdbcTemplate.query(SQL, ordDocAssgnDTOMapper, orderId);

		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException");
			list = null;
			
		} catch (Exception e) {
			logger.error("Exception caught in getRequiredDoc()", e);
			throw new DAOException(e.getMessage(), e);
		}
		return list;

	}
	
	
	public List<OrdDocDTO> getOrdDoc(String orderId) throws DAOException {

		List<OrdDocDTO> list = new ArrayList<OrdDocDTO>();
		
		String SQL = "select od.order_id, od.doc_type doc_type, od.seq_num, \n"
			+" od.file_path_name, od.create_by, od.create_date, \n"
			+" dt.doc_name, dt.doc_name_chi \n" +
			" , od.last_upd_by, od.last_upd_date" +
			" , od.verify_ind, od.verify_by, od.verify_date" +
			" from bomweb_all_ord_doc od, bomweb_all_doc dt, bomweb_order o \n"
			+" where od.doc_type=dt.doc_type \n"
			+"   and od.order_id=o.order_id \n"
			+"   and dt.lob=o.lob \n"
			+"   and od.order_id=? \n"
			+" order by od.doc_type, od.seq_num";



		try {
			logger.debug("getOrdDoc @ OrdDocDAO: \n" + SQL);

			list = simpleJdbcTemplate.query(SQL, ordDocDTOMapper, orderId);

		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException");
			list = null;
		} catch (Exception e) {
			logger.error("Exception caught in getRequiredDoc()", e);
			throw new DAOException(e.getMessage(), e);
		}
		return list;
		
	}
	
	public List<OrdDocDTO> getOrdDoc(String orderId, String docType) throws DAOException {
		final String sql = "select od.order_id, od.doc_type doc_type, od.seq_num, " +
			" od.file_path_name, od.create_by, od.create_date " +
			" , dt.doc_name, dt.doc_name_chi " +
			" , od.last_upd_by, od.last_upd_date" +
			" , od.verify_ind, od.verify_by, od.verify_date" +
			" from bomweb_all_ord_doc od, bomweb_all_doc dt, bomweb_order o " +
			" where od.doc_type=dt.doc_type " +
			"   and od.order_id=o.order_id " +
			"   and dt.lob=o.lob " +
			"   and od.order_id = :orderId" +
			"   and od.doc_type = :docType" +
			" order by od.seq_num";



		try {
			if (logger.isDebugEnabled()) {
				logger.debug("getOrdDoc @ OrdDocDAO: " + sql);
				logger.debug("orderId: " + orderId + ", docType: " + docType);
			}
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("orderId", orderId);
			params.addValue("docType", docType);

			return simpleJdbcTemplate.query(sql, ordDocDTOMapper, params);
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException");
			return Collections.emptyList();
		} catch (Exception e) {
			logger.error("Exception caught in getRequiredDoc()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public OrdDocDTO getOrdDoc(String orderId, String docType, int seqNum) throws DAOException {

		List<OrdDocDTO> list = new ArrayList<OrdDocDTO>();
		
		String SQL = "select od.order_id, od.doc_type doc_type, od.seq_num, " +
			" od.file_path_name, od.create_by, od.create_date " +
			" , dt.doc_name, dt.doc_name_chi " +
			" , od.last_upd_by, od.last_upd_date" +
			" , od.verify_ind, od.verify_by, od.verify_date" +
			" from bomweb_all_ord_doc od, bomweb_all_doc dt, bomweb_order o " +
			" where od.doc_type=dt.doc_type " +
			"   and od.order_id=o.order_id " +
			"   and dt.lob=o.lob " +
			"   and od.order_id = :orderId" +
			"   and od.doc_type = :docType" +
			"   and od.seq_num = :seqNum" +
			" order by od.doc_type, od.seq_num";



		try {
			logger.debug("getOrdDoc @ OrdDocDAO: \n" + SQL);
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("orderId", orderId);
			params.addValue("docType", docType);
			params.addValue("seqNum", seqNum);

			list = simpleJdbcTemplate.query(SQL, ordDocDTOMapper, params);

		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException");
			list = null;
		} catch (Exception e) {
			logger.error("Exception caught in getRequiredDoc()", e);
			throw new DAOException(e.getMessage(), e);
		}
		return isEmpty(list) ? null : list.get(0);
		
	}
	
	public int getLastSeqNum(String orderId, String docType) throws DAOException {
		String SQL = "select max(seq_num) \n"
			+ " from bomweb_all_ord_doc \n"
			+ " where order_id=? and doc_type=?";
		int seqnum = simpleJdbcTemplate.queryForInt(SQL, orderId, docType);
		return seqnum;
	}
	
	public int getImsNumberOfCollectedDoc(String orderId, String docType) throws DAOException {
		String SQL = "select count(*) total_num_of_doc "
					+"from BOMWEB_ALL_ORD_DOC "
					+"where order_id =? "
					+"and doc_type =? "
					+"and (OUTDATED_IND <> 'Y' "
					+"or OUTDATED_IND is null) ";
		int NumberOfCollectedDoc = simpleJdbcTemplate.queryForInt(SQL, orderId, docType);
		return NumberOfCollectedDoc;
	}
	
	public void markOrdDocCollected(String orderId, String docType, String collectedInd, String username) throws DAOException {
		String SQL = "update bomweb_all_ord_doc_assgn \n"
			+ " set collected_ind=?, \n"
			+ " last_upd_by=?, last_upd_date=sysdate \n"
			+ " where order_id=? \n"
			+ " and doc_type=? \n"
			+ " and nvl(mark_del_ind,'N') <> 'Y'";
		
		try {
			logger.debug("markOrdDocCollected @ OrdDocDAO: \n" + SQL);
			
			simpleJdbcTemplate.update(SQL, collectedInd, username, orderId, docType);

		} catch (Exception e) {
			logger.error("Exception caught in markOrdDocCollected()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public void insertOrdDoc(OrdDocDTO ordDoc) throws DAOException {
		String SQL = "insert into bomweb_all_ord_doc ( \n"
			+ " order_id, doc_type, seq_num, file_path_name, \n"
			+ " create_by, create_date, last_upd_by, last_upd_date )\n"
			+ " values \n"
			+ " ( ?, ?, ?, ?, ?, sysdate, ?, sysdate )";
		
		try {
			logger.debug("insertOrdDoc @ OrdDocDAO: \n" + SQL);
			
			simpleJdbcTemplate.update(SQL,
					ordDoc.getOrderId(),
					ordDoc.getDocType(),
					ordDoc.getSeqNum(),
					ordDoc.getFilePathName(),
					ordDoc.getCaptureBy(),
					ordDoc.getCaptureBy());

		} catch (Exception e) {
			logger.error("Exception caught in insertOrdDoc()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}


	
	public List<OrdDocDTO> getImsOrdDoc(String orderId) throws DAOException {

		List<OrdDocDTO> list = new ArrayList<OrdDocDTO>();
		
		String SQL = "select od.order_id, od.doc_type doc_type, od.seq_num, \n"
			+" od.file_path_name, od.create_by, od.create_date, \n"
			+" dt.doc_name, dt.doc_name_chi \n" +
			" , od.last_upd_by, od.last_upd_date" +
			" , od.verify_ind, od.verify_by, od.verify_date" +
			" from bomweb_all_ord_doc od, bomweb_all_doc dt \n"
			+" where od.doc_type=dt.doc_type \n"
			+"   and od.order_id=? \n"
			+" and (od.OUTDATED_IND <> 'Y'"
			+" or od.OUTDATED_IND is null)"
			+" order by od.doc_type, od.seq_num";



		try {
			logger.debug("getImsOrdDoc @ OrdDocDAO: \n" + SQL);

			list = simpleJdbcTemplate.query(SQL, ordDocDTOMapper, orderId);

		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException getImsOrdDoc");
			list = null;
		} catch (Exception e) {
			logger.error("Exception caught in getImsOrdDoc()", e);
			throw new DAOException(e.getMessage(), e);
		}
		return list;
		
	}
	
	private boolean isEmpty(List<?> list) {
		return list == null || list.isEmpty();
	}
	
	public int updateVerifyInd(String orderId, String docType, int seqNum, VerifyInd verifyInd, String username) throws DAOException {
		try {
			final String sql = "update" +
					"  bomweb_all_ord_doc" +
					" set" +
					"  verify_ind = :verifyInd" +
					"  , verify_by = :username" +
					"  , verify_date = sysdate" +
					" WHERE " +
					"  ORDER_ID = :orderId" +
					"  AND DOC_TYPE = :docType" +
					"  AND SEQ_NUM = :seqNum";
			if (logger.isDebugEnabled()) {
				logger.debug("sql: " + sql);
				logger.debug("orderId: " + orderId + ", docType: " + docType + ", seqNum: " + seqNum + ", verifyInd: " + verifyInd + ", username: " + username);
			}
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("orderId", orderId);
			params.addValue("docType", docType);
			params.addValue("seqNum", seqNum);
			params.addValue("verifyInd", verifyInd.toString());
			params.addValue("username", username);
			return this.simpleJdbcTemplate.update(sql, params);
		} catch (Exception e) {
			logger.error("Exception caught in updateVerifyInd()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public int insertAuditLog(String orderId, String docType, Integer seqNum, AuditLogActionCd actionCd, String username) throws DAOException {
		try {
			final String sql = "insert into" +
					"  BOMWEB_ALL_ORD_DOC_AUDIT_LOG" +
					" (" +
					"  ORDER_ID" +
					"  , DOC_TYPE" +
					"  , SEQ_NUM" +
					"  , ACTION_CD" +
					"  , LOGUSRID" +
					"  , LOGDATE" +
					" ) VALUES (" +
					"  :orderId" +
					"  , :docType" +
					"  , :seqNum" +
					"  , :actionCd" +
					"  , :username" +
					"  , sysdate" +
					" )";
			if (logger.isDebugEnabled()) {
				logger.debug("sql: " + sql);
				logger.debug("orderId: " + orderId + ", docType: " + docType + ", seqNum: " + seqNum + ", actionCd: " + actionCd + ", username: " + username);
			}
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("orderId", orderId);
			params.addValue("docType", docType);
			params.addValue("seqNum", seqNum);
			params.addValue("actionCd", actionCd.toString());
			params.addValue("username", username);
			return this.simpleJdbcTemplate.update(sql, params);
		} catch (Exception e) {
			logger.error("Exception caught in insertAuditLog()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
}
