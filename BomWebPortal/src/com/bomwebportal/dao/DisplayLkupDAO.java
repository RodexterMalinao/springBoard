package com.bomwebportal.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.bomwebportal.dto.DisplayLkupDTO;
import com.bomwebportal.exception.DAOException;

public class DisplayLkupDAO extends BaseDAO {
	
    protected final Log logger = LogFactory.getLog(getClass());
    
    private ParameterizedRowMapper<DisplayLkupDTO> getDisplayLkupDTORowMapper() {
    	
		return new ParameterizedRowMapper<DisplayLkupDTO>() {
			
			public DisplayLkupDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				
				DisplayLkupDTO obj = new DisplayLkupDTO();
				
				obj.setType(rs.getString("type"));
				obj.setId(rs.getInt("id"));
				obj.setLocale(rs.getString("locale"));
				obj.setDescription(rs.getString("description"));
				
				return obj;
			}
			
		};
		
	}
	
	private static String getDisLkupByTypeIdLocaleSQL 
		= "SELECT dl.TYPE, " 
				+"  dl.ID, " 
				+"  dl.locale, " 
				+"  dl.description " 
				+"FROM w_display_lkup dl " 
				+"WHERE 1=1";
	
	public List<DisplayLkupDTO> getDisLkupByTypeIdLocale(DisplayLkupDTO dto) throws DAOException {

		logger.info("getDisLkupByTypeIdLocale @ DisplayLkupDAO is called");
		List<DisplayLkupDTO> itemList = new ArrayList<DisplayLkupDTO>();

		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			StringBuilder searchSQL = new StringBuilder(getDisLkupByTypeIdLocaleSQL);
			
			if (StringUtils.isNotEmpty(dto.getType())) {
				searchSQL.append(" AND dl.TYPE = :type");
				params.addValue("type", dto.getType());
			}
			
			if (dto.getId() != 0) {
				searchSQL.append(" AND dl.ID = :id");
				params.addValue("id", dto.getId());
			}
			
			if (StringUtils.isNotEmpty(dto.getLocale())) {
				searchSQL.append(" AND dl.locale = :locale");
				params.addValue("locale", dto.getLocale());
			}
			
			searchSQL.append(" ORDER BY dl.TYPE, dl.ID, dl.locale");
			
			logger.info("getDisLkupByTypeIdLocale() @ DisplayLkupDAO: " + searchSQL);
			
			itemList = simpleJdbcTemplate.query(searchSQL.toString(), this.getDisplayLkupDTORowMapper(), params);
		
		} catch (BadSqlGrammarException bsge) {
			
			logger.info("BadSqlGrammarException in getDisLkupByTypeIdLocale()");
			itemList = null;
			
		} catch (EmptyResultDataAccessException erdae) {
			
			logger.info("EmptyResultDataAccessException in getDisLkupByTypeIdLocale()");
			itemList = null;
			
		} catch (Exception e) {
			
			logger.info("Exception caught in getDisLkupByTypeIdLocale(): ", e);
			throw new DAOException(e.getMessage(), e);
		}
		
		return itemList;
	}
}
