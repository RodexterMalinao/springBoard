package com.bomwebportal.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.bomwebportal.dto.EligibilityDTO;
import com.bomwebportal.exception.DAOException;

public class EligibilityDAO extends com.bomwebportal.mob.dao.bom.BaseDAO{
	protected final Log logger = LogFactory.getLog(getClass());
	
	
	private static String getEligibilityDTOListSQL = "select distinct a.id_doc_type,     \n"
			+ "                a.id_doc_num,     \n"
			+ "                b.CUSTOMER_TIER_ID,     \n"
			+ "                b.CUSTOMER_TIER_DESC     \n"
			+ "  from b_customer_tier a, b_value_prop_assgn b     \n"
			+ " where a.VALUE_PROP_ID = b.VALUE_PROP_ID     \n"
			+ "   and a.id_doc_type = ?     \n"
			+ "   and a.id_doc_num = ?     ";
	
	public List<EligibilityDTO> getEligibilityDTOList(
			String idDocType, String idDocNum) throws DAOException {
		logger.info(" getEligibilityDTOList is called");
		List<EligibilityDTO> itemList = new ArrayList<EligibilityDTO>();

		ParameterizedRowMapper<EligibilityDTO> mapper = new ParameterizedRowMapper<EligibilityDTO>() {
			public EligibilityDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				EligibilityDTO dto = new EligibilityDTO();
				dto.setIdDocType(rs.getString("ID_DOC_TYPE"));
				dto.setIdDocNum(rs.getString("ID_DOC_NUM"));
				dto.setCustomerTierId(rs.getString("CUSTOMER_TIER_ID"));
				dto.setCustomerTierDesc(rs.getString("CUSTOMER_TIER_DESC"));

				return dto;
			}
		};

		try {
			logger.info("getEligibilityDTOList() @ EligibilityDTO: "
					+ getEligibilityDTOListSQL);

			itemList = simpleJdbcTemplate.query(
					getEligibilityDTOListSQL, mapper, idDocType,
					idDocNum);
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException in getEligibilityDTOList()");

			itemList = null;
		} catch (Exception e) {
			logger.info(
					"Exception caught in getEligibilityDTOList():",
					e);

			throw new DAOException(e.getMessage(), e);
		}

		return itemList;

	}	
	
}
