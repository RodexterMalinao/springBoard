package com.pccw.wq.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;

import com.pccw.wq.schema.dto.SelectionItemDTO;


public class ConstantLkupDAOImpl extends NamedParameterJdbcDaoSupport implements ConstantLkupDAO{

	private static final String LKUP_TYPE_WQ_NATURE = "WQ_NATURE";
	
	private static final String LKUP_TYPE_WORKING_PARTY = "WORKING_PARTY";
	
	private static final HashMap <String,String> sqlMap = new HashMap<String,String>();
	
	{
		sqlMap.put(LKUP_TYPE_WQ_NATURE,
						"SELECT TO_CHAR(WQ_NATURE_ID) AS CODE, WQ_NATURE_DESC AS DESCRIPTION FROM q_wq_nature order by WQ_NATURE_DESC");

		sqlMap.put(LKUP_TYPE_WORKING_PARTY,
				"SELECT TO_CHAR(WP_ID) AS CODE, DESCRIPTION AS DESCRIPTION FROM Q_WORKING_PARTY order by DESCRIPTION");
	}
	
	private String lkupCode;
	
	private String sqlQry;
	
	private synchronized String buildSql() {
		if (this.sqlQry != null) {
			return this.sqlQry;
		}
		
		if (sqlMap.containsKey(this.getLkupCode())) {
			return sqlMap.get(this.getLkupCode());
		} else {
			StringBuilder sqlSb = new StringBuilder();			
			sqlSb = new StringBuilder();
			sqlSb.append("SELECT TO_CHAR(CODE) AS CODE, DESCRIPTION FROM q_dic_code_lkup WHERE grp_id in ");
			sqlSb.append("('");
			sqlSb.append(this.getLkupCode());
			sqlSb.append("')");
			sqlSb.append(" ORDER by DESCRIPTION");
			return sqlSb.toString();			
		}
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<SelectionItemDTO> getCodeLkup() {
			
		if (this.sqlQry == null) {
            sqlQry = this.buildSql();
		}

		@SuppressWarnings("rawtypes")
		List sqlResult = getJdbcTemplate().query(sqlQry, new RowMapper() {
	            @Override
	            public SelectionItemDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
	            	SelectionItemDTO selectionItem = new SelectionItemDTO();
	            	selectionItem.setItemKey(rs.getString("CODE"));
	            	selectionItem.setItemValue(rs.getString("DESCRIPTION"));
	                return selectionItem;
	            }

	    });
		List<SelectionItemDTO> rtnList = new ArrayList<SelectionItemDTO>();
		rtnList.addAll(sqlResult);
		return rtnList;
	}
	
	public void setLkupCode(String pLkupCode) {
		this.lkupCode = pLkupCode;
	}
	
	public String getLkupCode() {
		return this.lkupCode;
	}    
}
