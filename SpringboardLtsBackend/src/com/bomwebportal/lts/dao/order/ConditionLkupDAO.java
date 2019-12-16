package com.bomwebportal.lts.dao.order;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.bomwebportal.dao.CodeLkupDAOImpl;
import com.bomwebportal.dto.LookupItemDTO;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.lts.dto.order.ConditionLtsDTO;

public class ConditionLkupDAO extends CodeLkupDAOImpl {

	private static final String SQL_GET_CONDITION =  
		"select condition_key, condition_seq, condition_type, condition, value, result " +
		"from w_condition_lkup " +
		"order by condition_seq";
	
	
	public LookupItemDTO[] getCodeLkup() throws DAOException {
		
		final Map<String,List<ConditionLtsDTO>> conditionMap = new HashMap<String,List<ConditionLtsDTO>>();
		
		ParameterizedRowMapper<ConditionLtsDTO> mapper = new ParameterizedRowMapper<ConditionLtsDTO>() {
			public ConditionLtsDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				ConditionLtsDTO condition = new ConditionLtsDTO();
				condition.setCondition(rs.getString("CONDITION"));
				condition.setConditionKey(rs.getString("CONDITION_KEY"));
				condition.setConditionSeq(rs.getString("CONDITION_SEQ"));
				condition.setConditionType(rs.getString("CONDITION_TYPE"));
				condition.setResult(rs.getString("RESULT"));
				condition.setValue(rs.getString("VALUE"));
				List<ConditionLtsDTO> conditionList = null;
				
				if (conditionMap.containsKey(condition.getConditionKey())) {
					conditionList = conditionMap.get(condition.getConditionKey());
				} else {
					conditionList = new ArrayList<ConditionLtsDTO>();
					conditionMap.put(condition.getConditionKey(), conditionList);
				}
				conditionList.add(condition);
                return condition;                
			}};
		try {
			this.simpleJdbcTemplate.query(SQL_GET_CONDITION, mapper);
			
			Iterator<String> it = conditionMap.keySet().iterator();
			List<LookupItemDTO> lookupList = new ArrayList<LookupItemDTO>();
			LookupItemDTO lookupItem = null;
			String conditionKey = null;
			
			while (it.hasNext()) {
				conditionKey = it.next();
				lookupItem = new LookupItemDTO();
				lookupItem.setItemKey(conditionKey);
				lookupItem.setItemValue(conditionMap.get(conditionKey));
				lookupList.add(lookupItem);
			}
			return lookupList.toArray(new LookupItemDTO[lookupList.size()]);
		} catch (Exception e) {
			logger.error("Error in getCodeLkup() get conditions\n", e);
			throw new DAOException(e.getMessage(), e);
		}	
	}
}
