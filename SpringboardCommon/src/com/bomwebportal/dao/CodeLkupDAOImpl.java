package com.bomwebportal.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.bomwebportal.dto.LookupItemDTO;
import com.bomwebportal.exception.DAOException;

/*
 * Created on May, 2012
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

/**
 * @author Alfredo P. Ricafort
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */


public class CodeLkupDAOImpl extends BaseDAO implements CodeLkupDAO{
	
	public static final String DEFAULT_ITEM_KEY = "CODE";
	
	private String lkupCode;
	private String lkupTable;
	private String order;
	private String orderBy;
	
	private String itemKey = DEFAULT_ITEM_KEY;

	
	private static final String BOMWEB_CODE_LKUP = "BOMWEB_CODE_LKUP";	
	private static final String W_CODE_LKUP = "W_CODE_LKUP";
	private static final String W_CODE_LKUP_REVERSE = "W_CODE_LKUP_REVERSE";
	private static final String Q_WQ_NATURE = "Q_WQ_NATURE";
	private static final String B_LOOKUP = "B_LOOKUP";
	private static final String B_LOOKUP_LTS = "B_LOOKUP_LTS";
	private static final String Q_DIC_CODE_LKUP = "Q_DIC_CODE_LKUP";
	private static final String BOMWEB_ALL_DOC = "BOMWEB_ALL_DOC";
	private static final String BOMWEB_ALL_DOC_WAIVE = "BOMWEB_ALL_ORD_DOC_WAIVE";
	private static final String W_DISPLAY_LKUP = "W_DISPLAY_LKUP";
	private static final String B_ORD_STATUS_LKUP = "B_ORD_STATUS_LKUP";
	private static final String W_DISPLAY_LKUP_A = "W_DISPLAY_LKUP_A";
	
	
	private static final HashMap <String,String> sqlMap = new HashMap<String,String>();
	{
		sqlMap.put(BOMWEB_CODE_LKUP,
				"SELECT CODE_ID AS CODE,CODE_DESC AS DESCRIPTION FROM bomweb_code_lkup WHERE code_type = ?");
		sqlMap.put(W_CODE_LKUP,
	            "SELECT CODE AS CODE, DESCRIPTION AS DESCRIPTION FROM w_code_lkup WHERE grp_id = ?");
		sqlMap.put(W_CODE_LKUP_REVERSE,
		        "SELECT DESCRIPTION AS CODE, CODE AS DESCRIPTION FROM w_code_lkup WHERE grp_id = ?");
		sqlMap.put(Q_WQ_NATURE,
	            "SELECT wq_nature_id AS CODE,wq_nature_desc AS DESCRIPTION FROM Q_WQ_NATURE WHERE wq_nature_type = ?");
		sqlMap.put(B_LOOKUP,
	            "SELECT bom_code AS CODE, bom_desc AS DESCRIPTION FROM B_LOOKUP WHERE bom_grp_id = ?");
		sqlMap.put(B_LOOKUP_LTS,
	            "SELECT lts_code AS CODE, bom_desc AS DESCRIPTION FROM B_LOOKUP WHERE lts_grp_id = ?");
		sqlMap.put(Q_DIC_CODE_LKUP,
	            "SELECT CODE AS CODE, DESCRIPTION AS DESCRIPTION FROM Q_DIC_CODE_LKUP WHERE grp_id = ?");
		sqlMap.put(BOMWEB_ALL_DOC,
	            "SELECT DOC_TYPE AS CODE, DOC_NAME AS DESCRIPTION FROM BOMWEB_ALL_DOC WHERE LOB = ?");
		sqlMap.put(BOMWEB_ALL_DOC_WAIVE,
	            "SELECT WAIVE_REASON AS CODE, WAIVE_DESC AS DESCRIPTION FROM BOMWEB_ALL_ORD_DOC_WAIVE WHERE LOB = ?");
		sqlMap.put(W_DISPLAY_LKUP,
	            "SELECT TYPE AS CODE, DESCRIPTION AS DESCRIPTION FROM W_DISPLAY_LKUP WHERE LOCALE = 'en' and ID = ?");
		sqlMap.put(B_ORD_STATUS_LKUP,
	            "SELECT BOM_CODE || '^' || STATUS_CD AS CODE, nvl(trim(STATUS_DESC), BOM_CODE_DISP_VAL) AS DESCRIPTION FROM B_ORD_STATUS_LKUP WHERE SYSTEM_ID = ?");
		sqlMap.put(W_DISPLAY_LKUP_A,
	            "SELECT ID AS CODE, DESCRIPTION AS DESCRIPTION FROM W_DISPLAY_LKUP WHERE LOCALE = 'en' and TYPE = ?");
		
	}	
	
	private String sqlQry;
	private boolean isTableDefined = true;
	

	public LookupItemDTO[] getCodeLkup() throws DAOException {
		
		if (this.sqlQry == null) {
			this.sqlQry = sqlMap.get(this.getLkupTable()==null?BOMWEB_CODE_LKUP:this.getLkupTable());
		}		
		
		if (this.sqlQry == null) {
			isTableDefined = false;
			this.sqlQry = "SELECT CODE, DESCRIPTION FROM "
					+ this.getLkupTable();
            if (StringUtils.isNotBlank(this.getLkupCode())) {
            	this.sqlQry = this.sqlQry + " WHERE " + this.getLkupCode();	
            }

    		if(StringUtils.equalsIgnoreCase(this.order, "ASC") || StringUtils.equalsIgnoreCase(this.order, "DESC")){
    			if(StringUtils.isNotBlank(this.orderBy)){
    				this.sqlQry = this.sqlQry + " ORDER BY " + this.orderBy + " " + this.order;
    			}else{
    				this.sqlQry = this.sqlQry + " ORDER BY CODE " + this.order;
    			}
    		}
		}
		
		ParameterizedRowMapper<LookupItemDTO> mapper = new ParameterizedRowMapper<LookupItemDTO>() {
			public LookupItemDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            	LookupItemDTO lookupItemDTO = new LookupItemDTO();
            	lookupItemDTO.setItemKey(rs.getString(CodeLkupDAOImpl.this.getItemKey()));
            	lookupItemDTO.setItemValue(rs.getString("DESCRIPTION"));
                return lookupItemDTO;
			}
		};

		try {
			if (isTableDefined) {
			    return (LookupItemDTO [])simpleJdbcTemplate.query(sqlQry, mapper, this.getLkupCode()).toArray(new LookupItemDTO[0]);
			} else {
			    return (LookupItemDTO [])simpleJdbcTemplate.query(sqlQry, mapper).toArray(new LookupItemDTO[0]);				
			}
		} catch (Exception e) {
			logger.error("Error in getStatus()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}	
	
	public void setLkupCode(String pLkupCode) {
		this.lkupCode = pLkupCode;
	}
	
	public String getLkupCode() {
		return this.lkupCode;
	}

	public String getLkupTable() {
		return lkupTable;
	}

	public void setLkupTable(String lkupTable) {
		this.lkupTable = lkupTable;
	}

	public String getItemKey() {
		return itemKey;
	}

	public void setItemKey(String itemKey) {
		this.itemKey = itemKey;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}
 
}
