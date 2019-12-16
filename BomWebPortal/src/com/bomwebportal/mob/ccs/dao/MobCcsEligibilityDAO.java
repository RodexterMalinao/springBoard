package com.bomwebportal.mob.ccs.dao;

import java.util.Collections;
import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;

import com.bomwebportal.dao.BaseDAO;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.mob.ccs.dto.MobCcsEligibilityDTO;
import com.bomwebportal.mob.ccs.dto.MobCcsEligibilityDTO.IdDocType;
import com.bomwebportal.mob.ccs.dto.MobCcsValuePropAssgnDTO;

public class MobCcsEligibilityDAO extends BaseDAO {
	public List<MobCcsValuePropAssgnDTO> getMobCcsValuePropAssgnDTOALL() throws DAOException {
		final String sql = "SELECT" +
				" VALUE_PROP_ID" +
				" , VALUE_PROP_DESC" +
				" , CUSTOMER_TIER_ID" +
				" , CUSTOMER_TIER_DESC" +
				" , CREATE_BY" +
				" , CREATE_DATE" +
				" , LAST_UPD_BY" +
				" , LAST_UPD_DATE" +
				" FROM b_value_prop_assgn" +
				" ORDER BY LOWER(VALUE_PROP_DESC)";
		try {
			return this.simpleJdbcTemplate.query(sql, ParameterizedBeanPropertyRowMapper.newInstance(MobCcsValuePropAssgnDTO.class));
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException in getMobCcsEligibilityDTOALL()");
			return Collections.emptyList();
		} catch (Exception e) {
			logger.info("Exception caught in getMobCcsEligibilityDTOALL():", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public List<MobCcsEligibilityDTO> getMobCcsEligibilityDTOALL(IdDocType idDocType, String idDocNum) throws DAOException {
		final String sql = "SELECT" +
				" ct.ID_DOC_TYPE" +
				" , ct.ID_DOC_NUM" +
				" , VALUE_PROP_ID" +
				" , vpa.VALUE_PROP_DESC" +
				" , ct.CREATE_BY" +
				" , ct.CREATE_DATE" +
				" , ct.LAST_UPD_BY" +
				" , ct.LAST_UPD_DATE" +
				" FROM b_customer_tier ct" +
				" LEFT JOIN b_value_prop_assgn vpa USING (VALUE_PROP_ID)" +
				" WHERE ct.ID_DOC_TYPE = :idDocType" +
				" AND ct.ID_DOC_NUM = :idDocNum" +
				" ORDER BY ct.CREATE_DATE";
		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("idDocType", idDocType.toString());
			params.addValue("idDocNum", idDocNum);
			return this.simpleJdbcTemplate.query(sql, ParameterizedBeanPropertyRowMapper.newInstance(MobCcsEligibilityDTO.class), params);
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException in getMobCcsEligibilityDTOALL()");
			return Collections.emptyList();
		} catch (Exception e) {
			logger.info("Exception caught in getMobCcsEligibilityDTOALL():", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public MobCcsEligibilityDTO getMobCcsEligibilityDTO(IdDocType idDocType, String idDocNum, String valuePropId) throws DAOException {
		final String sql = "SELECT" +
				" ct.ID_DOC_TYPE" +
				" , ct.ID_DOC_NUM" +
				" , VALUE_PROP_ID" +
				" , vpa.VALUE_PROP_DESC" +
				" , ct.CREATE_BY" +
				" , ct.CREATE_DATE" +
				" , ct.LAST_UPD_BY" +
				" , ct.LAST_UPD_DATE" +
				" FROM b_customer_tier ct" +
				" LEFT JOIN b_value_prop_assgn vpa USING (VALUE_PROP_ID)" +
				" WHERE ct.ID_DOC_TYPE = :idDocType" +
				" AND ct.ID_DOC_NUM = :idDocNum" +
				" AND VALUE_PROP_ID = :valuePropId";
		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("idDocType", idDocType.toString());
			params.addValue("idDocNum", idDocNum);
			params.addValue("valuePropId", valuePropId);
			return this.simpleJdbcTemplate.queryForObject(sql, ParameterizedBeanPropertyRowMapper.newInstance(MobCcsEligibilityDTO.class), params);
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException in getMobCcsEligibilityDTO()");
			return null;
		} catch (Exception e) {
			logger.info("Exception caught in getMobCcsEligibilityDTO():", e);
			throw new DAOException(e.getMessage(), e);
		}
	}

	public int insertMobCcsEligibilityDTO(MobCcsEligibilityDTO dto) throws DAOException {
		final String sql = "INSERT INTO b_customer_tier" +
				" (ID_DOC_TYPE" +
				" , ID_DOC_NUM" +
				" , VALUE_PROP_ID" +
				" , CREATE_BY" +
				" , CREATE_DATE" +
				" , LAST_UPD_BY" +
				" , LAST_UPD_DATE" +
				" ) VALUES (" +
				" :idDocType" +
				" , :idDocNum" +
				" , :valuePropId" +
				" , :createBy" +
				" , sysdate" +
				" , :lastUpdBy" +
				" , sysdate" +
				" )";
		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("idDocType", dto.getIdDocType().toString());
			params.addValue("idDocNum", dto.getIdDocNum());
			params.addValue("valuePropId", dto.getValuePropId());
			params.addValue("createBy", dto.getCreateBy());
			params.addValue("lastUpdBy", dto.getLastUpdBy());
			return this.simpleJdbcTemplate.update(sql, params);
		} catch (Exception e) {
			logger.info("Exception caught in getMobCcsEligibilityDTOALL():", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
}
