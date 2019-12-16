package com.bomwebportal.lts.dao.order;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.bomwebportal.dao.CodeLkupDAOImpl;
import com.bomwebportal.dto.LookupItemDTO;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.lts.dto.profile.OfferActionLtsDTO;

public class OfferActionLookupDAO extends CodeLkupDAOImpl {

	private static final String SQL_GET_OFFER_ACTION = 
		"select lob, from_prod, to_prod, code, action, type, description " +
		"from W_OFFER_ACTION_LKUP where ";
	
	
	public LookupItemDTO [] getCodeLkup() throws DAOException {

		ParameterizedRowMapper<LookupItemDTO> mapper = new ParameterizedRowMapper<LookupItemDTO>() {
			public LookupItemDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				OfferActionLtsDTO offerAction = new OfferActionLtsDTO();
				offerAction.setAction(rs.getString("ACTION"));
				offerAction.setCode(rs.getString("CODE"));
				offerAction.setFromProd(rs.getString("FROM_PROD"));
				offerAction.setLob(rs.getString("LOB"));
				offerAction.setToProd(rs.getString("TO_PROD"));
				offerAction.setType(rs.getString("TYPE"));
				offerAction.setDescription(rs.getString("DESCRIPTION"));
				
				StringBuilder keySb = new StringBuilder();
				keySb.append(offerAction.getFromProd());
				keySb.append("|");
				keySb.append(offerAction.getToProd());
				keySb.append("|");
				keySb.append(offerAction.getCode());

            	LookupItemDTO lookupItemDTO = new LookupItemDTO();
            	lookupItemDTO.setItemKey(keySb.toString());
            	lookupItemDTO.setItemValue(offerAction);
                return lookupItemDTO;                
			}};

		try {
			StringBuilder sqlBuilder = new StringBuilder(SQL_GET_OFFER_ACTION);
	        sqlBuilder.append(this.getLkupCode());	            
			return this.simpleJdbcTemplate.query(sqlBuilder.toString(),mapper).toArray(new LookupItemDTO[0]);
		} catch (Exception e) {
			logger.error("Error in getCodeLkup() get offer action\n", e);
			throw new DAOException(e.getMessage(), e);
		}						
	}	 
}
