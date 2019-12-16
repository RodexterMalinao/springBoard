package com.bomwebportal.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.bomwebportal.dto.DtoPropertyDTO;
import com.bomwebportal.dto.FieldPropertyDTO;
import com.bomwebportal.dto.LookupItemDTO;
import com.bomwebportal.exception.DAOException;

public class FieldPropertyDAO extends CodeLkupDAOImpl {

	private static final String SQL_GET_FIELD_PROP = 
		"select o.java_package || '.' || o.java_class class_name, f.field_name field_name, f.field_type, nvl(f.field_length,-1) field_length, " +
		"f.default_value, f.description, f.mandatory, f.validation_rule " +  
		"from sb_dto_lkup o, sb_dto_field_lkup f " +
		"where o.dto_id = f.dto_id";
	
	
	public LookupItemDTO[] getCodeLkup() throws DAOException {
			
		final Map<String,LookupItemDTO> fieldPropertryMap = new TreeMap<String,LookupItemDTO>();
		
		ParameterizedRowMapper<Object> mapper = new ParameterizedRowMapper<Object>() {
			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				String className = rs.getString("CLASS_NAME");
				DtoPropertyDTO dtoProperty = null;
				LookupItemDTO lookup = null;
				FieldPropertyDTO fieldProperty = null;
				
				if (fieldPropertryMap.containsKey(className)) {
					dtoProperty = (DtoPropertyDTO)fieldPropertryMap.get(className).getItemValue();
				} else {
					dtoProperty = new DtoPropertyDTO(className);
					lookup = new LookupItemDTO();
					lookup.setItemKey(className);
					lookup.setItemValue(dtoProperty);
					fieldPropertryMap.put(className, lookup);
				}
				fieldProperty = new FieldPropertyDTO(rs.getString("FIELD_NAME"), rs.getString("FIELD_TYPE"), rs.getInt("FIELD_LENGTH"), 
						rs.getString("DEFAULT_VALUE"), rs.getString("DESCRIPTION"), "Y".equals(rs.getString("MANDATORY")), rs.getString("VALIDATION_RULE"));
				dtoProperty.setFieldProperty(fieldProperty.getFieldName(), fieldProperty);
				return null;
			}
		};
		try {
			this.simpleJdbcTemplate.query(SQL_GET_FIELD_PROP, mapper).toArray(new LookupItemDTO[0]);
			return fieldPropertryMap.values().toArray(new LookupItemDTO[fieldPropertryMap.size()]);
		} catch (Exception e) {
			logger.error("Error in getCodeLkup()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
}
