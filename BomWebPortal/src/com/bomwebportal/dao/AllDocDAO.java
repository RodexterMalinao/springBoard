package com.bomwebportal.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.bomwebportal.dto.AccessRoleDTO;
import com.bomwebportal.dto.AllDocDTO;
import com.bomwebportal.dto.LookupItemDTO;
import com.bomwebportal.dto.AllDocDTO.DocType;
import com.bomwebportal.dto.AllDocDTO.Type;
import com.bomwebportal.exception.DAOException;

public class AllDocDAO extends BaseDAO {
	protected final Log logger = LogFactory.getLog(getClass());
	
	@Deprecated
	public List<AllDocDTO> getAllDocDTOByType(String lob, Type type) throws DAOException {
		if (logger.isDebugEnabled()) {
			logger.debug("getAllDocDTOByType @ AllDocDAO is called");
		}
		String sql = "SELECT" +
				" DOC_TYPE" +
				" , LOB" +
				" , TYPE" +
				" , DOC_NAME" +
				" , DOC_NAME_CHI" +
				" , DOC_DESC" +
				" , START_DATE" +
				" , END_DATE" +
				" , CREATE_BY" +
				" , CREATE_DATE" +
				" , LAST_UPD_BY" +
				" , LAST_UPD_DATE" +
				" FROM bomweb_all_doc" +
				" WHERE LOB = :lob" +
				" AND TYPE = :type" +
				" ORDER BY DOC_TYPE";
		List<AllDocDTO> itemList = Collections.emptyList();
		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("lob", lob);
			params.addValue("type", type == null ? null : type.toString());
			if (logger.isDebugEnabled()) {
				logger.debug("getAllDocDTOByType() @ AllDocDAO: " + sql);
			}
			itemList = simpleJdbcTemplate.query(sql, this.getAllDocDTORowMapper(), params);
		} catch (EmptyResultDataAccessException erdae) {
			if (logger.isInfoEnabled()) {
				logger.info("EmptyResultDataAccessException in getAllDocDTOByType()");
			}
			itemList = null;
		} catch (Exception e) {
			if (logger.isInfoEnabled()) {
				logger.info("Exception caught in getAllDocDTOByType(): ", e);
			}
			throw new DAOException(e.getMessage(), e);
		}
		return itemList;
	}
	
	public List<AllDocDTO> getAllDocDTOByTypeAndAppDate(String lob, Type type, Date appDate) throws DAOException {
		if (logger.isDebugEnabled()) {
			logger.debug("getAllDocDTOByTypeAndAppDate @ AllDocDAO is called");
		}
		String sql = "SELECT" +
				" DOC_TYPE" +
				" , LOB" +
				" , TYPE" +
				" , DOC_NAME" +
				" , DOC_NAME_CHI" +
				" , DOC_DESC" +
				" , START_DATE" +
				" , END_DATE" +
				" , CREATE_BY" +
				" , CREATE_DATE" +
				" , LAST_UPD_BY" +
				" , LAST_UPD_DATE" +
				" FROM bomweb_all_doc" +
				" WHERE LOB = :lob" +
				" AND TYPE = :type" +
				" AND trunc(:appDate) BETWEEN trunc(NVL(START_DATE, trunc(:appDate))) AND trunc(NVL(END_DATE, trunc(:appDate)))" +
				" ORDER BY DOC_TYPE";
		List<AllDocDTO> itemList = Collections.emptyList();
		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("lob", lob);
			params.addValue("type", type == null ? null : type.toString());
			params.addValue("appDate", appDate, Types.DATE);
			if (logger.isDebugEnabled()) {
				logger.debug("getAllDocDTOByTypeAndAppDate() @ AllDocDAO: " + sql);
			}
			itemList = simpleJdbcTemplate.query(sql, this.getAllDocDTORowMapper(), params);
		} catch (EmptyResultDataAccessException erdae) {
			if (logger.isInfoEnabled()) {
				logger.info("EmptyResultDataAccessException in getAllDocDTOByTypeAndAppDate()");
			}
			itemList = null;
		} catch (Exception e) {
			if (logger.isInfoEnabled()) {
				logger.info("Exception caught in getAllDocDTOByTypeAndAppDate(): ", e);
			}
			throw new DAOException(e.getMessage(), e);
		}
		return itemList;
	}
	
	/**
	 * Sometimes supports insert extra DOC_TYPE into database `BOMWEB_ALL_DOC` table without adding
	 * corresponding enum com.bomwebportal.dto.AllDocDTO.DocType value, which can cause 
	 * Exception when evaluating DocType.valueOf() in getAllDocDTORowMapper  
	 */
	public List<AllDocDTO> getAllDocDTOKnownByTypeAndAppDate(String lob, Type type, Date appDate) throws DAOException {
		if (logger.isDebugEnabled()) {
			logger.debug("getAllDocDTOKnownByTypeAndAppDate @ AllDocDAO is called");
		}
		String sql = "SELECT" +
				" DOC_TYPE" +
				" , LOB" +
				" , TYPE" +
				" , DOC_NAME" +
				" , DOC_NAME_CHI" +
				" , DOC_DESC" +
				" , START_DATE" +
				" , END_DATE" +
				" , CREATE_BY" +
				" , CREATE_DATE" +
				" , LAST_UPD_BY" +
				" , LAST_UPD_DATE" +
				" FROM bomweb_all_doc" +
				" WHERE LOB = :lob" +
				" AND TYPE = :type" +
				" AND trunc(:appDate) BETWEEN trunc(NVL(START_DATE, sysdate)) AND trunc(NVL(END_DATE, sysdate))" +
				//" AND DOC_TYPE IN (:docTypes)" +
				" ORDER BY DOC_TYPE";
		List<AllDocDTO> itemList = Collections.emptyList();
		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("lob", lob);
			params.addValue("type", type == null ? null : type.toString());
			params.addValue("appDate", appDate, Types.DATE);
			//params.addValue("docTypes", getDocTypes());
			if (logger.isDebugEnabled()) {
				logger.debug("getAllDocDTOKnownByTypeAndAppDate() @ AllDocDAO: " + sql);
			}
			itemList = simpleJdbcTemplate.query(sql, this.getAllDocDTORowMapper(), params);
		} catch (EmptyResultDataAccessException erdae) {
			if (logger.isInfoEnabled()) {
				logger.info("EmptyResultDataAccessException in getAllDocDTOKnownByTypeAndAppDate()");
			}
			itemList = null;
		} catch (Exception e) {
			if (logger.isInfoEnabled()) {
				logger.info("Exception caught in getAllDocDTOKnownByTypeAndAppDate(): ", e);
			}
			throw new DAOException(e.getMessage(), e);
		}
		return itemList;
	}
	
	public boolean isSupportDocRequired(String docType, String basketID, String[] vasItemList, String channel) throws DAOException {
		logger.debug("isSupportDocRequired is called");
		List<String> formList = new ArrayList<String>();
		String SQL = "select * from bomweb_support_doc_control " +
				"where channel = '" + channel + "' " +
				"and doc_type='" + docType + "' " +
				"and (1=0 ";
		if (basketID != null) {
			SQL += "OR (type='BASKET' and id='" + basketID + "') ";
		}
		if ((vasItemList != null) && (vasItemList.length > 0)) {
			SQL +="OR (type='VAS' and id in ('" + vasItemList[0] + "'";
			if (vasItemList.length > 1) {
				for (int i=1; i<vasItemList.length; i++) {
					SQL +=", '" + vasItemList[i] + "'";
				}
			}
			SQL +=")) ";
		}
		SQL +=")";

		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {

			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				String brand = new String();
				brand = rs.getString("ID");
				return brand;
			}
		};
		try {
			formList = simpleJdbcTemplate.query(SQL, mapper);
			//System.out.println(SQL);
			if ((formList != null) && (formList.size() > 0))
				return true;
			else {
				return false;
			}
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException");
			return false;
		} catch (Exception e) {
			logger.error("Exception caught in getSelectedItemList()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
    private ParameterizedRowMapper<AllDocDTO> getAllDocDTORowMapper() {
		return new ParameterizedRowMapper<AllDocDTO>() {
			public AllDocDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				AllDocDTO obj = new AllDocDTO();
				try {
					obj.setDocType(DocType.valueOf(rs.getString("DOC_TYPE")));
				} catch (Exception e) {
					obj.setDocType(DocType.valueOf("M001"));
				}
				obj.setDocTypeMob(rs.getString("DOC_TYPE"));
				obj.setLob(rs.getString("LOB"));
				obj.setType(Type.valueOf(rs.getString("TYPE")));
				obj.setDocName(rs.getString("DOC_NAME"));
				obj.setDocNameChi(rs.getString("DOC_NAME_CHI"));
				obj.setStartDate(rs.getDate("START_DATE"));
				obj.setEndDate(rs.getDate("END_DATE"));
				obj.setCreateBy(rs.getString("CREATE_BY"));
				obj.setCreateDate(rs.getTimestamp("CREATE_DATE"));
				obj.setLastUpdBy(rs.getString("LAST_UPD_BY"));
				obj.setLastUpdDate(rs.getTimestamp("LAST_UPD_DATE"));
				return obj;
			}
		};
	}
    
   
    
    private List<String> getDocTypes() {
    	List<String> docTypes = new ArrayList<String>();
    	for (DocType docType : DocType.values()) {
    		docTypes.add(docType.toString());
    	}
    	return docTypes;
    }
    
    public List<String> getMissingDocReasonList() throws DAOException {
		if (logger.isDebugEnabled()) {
			logger.debug("getMissingDocReasonList @ AllDocDAO is called");
		}
		String sql = "select code_id from bomweb_code_lkup "
				+ "where code_type = 'MISSING_DOC_REASON'";
		List<String> reasonList = new ArrayList<String>();
		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				String reason = rs.getString("code_id");
				return reason;
			}
		};
		try {
			if (logger.isDebugEnabled()) {
				logger.debug("getMissingDocReasonList() @ AllDocDAO: " + sql);
			}
			reasonList = simpleJdbcTemplate.query(sql, mapper);
		} catch (EmptyResultDataAccessException erdae) {
			if (logger.isInfoEnabled()) {
				logger.info("EmptyResultDataAccessException in getMissingDocReasonList()");
			}
			reasonList = null;
		} catch (Exception e) {
			if (logger.isInfoEnabled()) {
				logger.info("Exception caught in getMissingDocReasonList(): ", e);
			}
			throw new DAOException(e.getMessage(), e);
		}
		return reasonList;
	}
}
