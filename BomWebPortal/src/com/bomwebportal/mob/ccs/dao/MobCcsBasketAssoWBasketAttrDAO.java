package com.bomwebportal.mob.ccs.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.bomwebportal.dao.BaseDAO;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.mob.ccs.dto.BasketAssoWBasketAttrBasketTypeDTO;
import com.bomwebportal.mob.ccs.dto.BasketAssoWBasketAttrBrandModelDTO;
import com.bomwebportal.mob.ccs.dto.BasketAssoWBasketAttrCustomerTierDTO;
import com.bomwebportal.mob.ccs.dto.BasketAssoWBasketAttrDTO;
import com.bomwebportal.mob.ccs.dto.BasketAssoWBasketAttrRatePlanDTO;

public class MobCcsBasketAssoWBasketAttrDAO extends BaseDAO {
    protected final Log logger = LogFactory.getLog(getClass());
    
	public List<BasketAssoWBasketAttrCustomerTierDTO> getWBasketAttrCustomerTierDTOALL() throws DAOException {
		logger.info("getWBasketAttrCustomerTierDTOALL @ MobCcsBasketAssoWBasketAttrDAO is called");
		List<BasketAssoWBasketAttrCustomerTierDTO> itemList = Collections.emptyList();
		String sql = "SELECT" +
				" DISTINCT b.customer_tier AS customer_tier, b.customer_tier_id" +
				" FROM W_CHANNEL_BASKET_ASSGN c" +
				" JOIN W_BASKET_ATTRIBUTE_MV b ON (c.basket_id = b.basket_id)" +
				" WHERE c.channel_id = 2" +
				" AND NVL(b.NATURE, 'ACQ') = 'ACQ' " +
				" AND TRUNC(sysdate) BETWEEN NVL(c.EFF_START_DATE, sysdate) AND NVL(c.EFF_END_DATE, sysdate)" +
				" ORDER BY b.customer_tier";
		try {
			logger.info("getWBasketAttrCustomerTierDTOALL() @ MobCcsBasketAssoWBasketAttrDAO: " + sql);

			itemList = simpleJdbcTemplate.query(sql, this.getWBasketAttrCustomerTierDTORowMapper());
		} catch (BadSqlGrammarException bsge) {
			logger.info("BadSqlGrammarException in getWBasketAttrCustomerTierDTOALL()");

			itemList = null;
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException in getWBasketAttrCustomerTierDTOALL()");

			itemList = null;
		} catch (Exception e) {
			logger.info("Exception caught in getWBasketAttrCustomerTierDTOALL(): ", e);

			throw new DAOException(e.getMessage(), e);
		}
		return itemList;
	}

	public List<BasketAssoWBasketAttrBasketTypeDTO> getWBasketAttrBasketTypeDTOALL() throws DAOException {
		logger.info("getWBasketAttrBasketTypeDTOALL @ MobCcsBasketAssoWBasketAttrDAO is called");
		List<BasketAssoWBasketAttrBasketTypeDTO> itemList = Collections.emptyList();
		String sql = "SELECT" +
				" DISTINCT b.basket_type AS basket_type, b.basket_type_id" +
				" FROM W_CHANNEL_BASKET_ASSGN c" +
				" JOIN W_BASKET_ATTRIBUTE_MV b ON (c.basket_id = b.basket_id)" +
				" WHERE c.channel_id = 2" +
				" AND NVL(b.NATURE, 'ACQ') = 'ACQ' " +
				" AND TRUNC(sysdate) BETWEEN NVL(c.EFF_START_DATE, sysdate) AND NVL(c.EFF_END_DATE, sysdate)" +
				" ORDER BY b.basket_type";
		try {
			logger.info("getWBasketAttrBasketTypeDTOALL() @ MobCcsBasketAssoWBasketAttrDAO: " + sql);

			itemList = simpleJdbcTemplate.query(sql, this.getWBasketAttrBasketTypeDTORowMapper());
		} catch (BadSqlGrammarException bsge) {
			logger.info("BadSqlGrammarException in getWBasketAttrBasketTypeDTOALL()");

			itemList = null;
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException in getWBasketAttrBasketTypeDTOALL()");

			itemList = null;
		} catch (Exception e) {
			logger.info("Exception caught in getWBasketAttrBasketTypeDTOALL(): ", e);

			throw new DAOException(e.getMessage(), e);
		}
		return itemList;
	}
	
	public List<BasketAssoWBasketAttrRatePlanDTO> getWBasketAttrRatePlanDTOALL() throws DAOException {
		logger.info("getWBasketAttrRatePlanDTOALL @ MobCcsBasketAssoWBasketAttrDAO is called");
		List<BasketAssoWBasketAttrRatePlanDTO> itemList = Collections.emptyList();
		String sql = "SELECT" +
				" DISTINCT b.rate_plan AS rate_plan, b.rate_plan_id" +
				" FROM W_CHANNEL_BASKET_ASSGN c" +
				" JOIN W_BASKET_ATTRIBUTE_MV b ON (c.basket_id = b.basket_id)" +
				" WHERE c.channel_id = 2" +
				" AND NVL(b.NATURE, 'ACQ') = 'ACQ' " +
				" AND TRUNC(sysdate) BETWEEN NVL(c.EFF_START_DATE, sysdate) AND NVL(c.EFF_END_DATE, sysdate)" +
				" AND b.rate_plan_id IS NOT NULL" +
				" ORDER BY b.rate_plan_id";
		try {
			logger.info("getWBasketAttrRatePlanDTOALL() @ MobCcsBasketAssoWBasketAttrDAO: " + sql);

			itemList = simpleJdbcTemplate.query(sql, this.getWBasketAttrRatePlanDTORowMapper());
		} catch (BadSqlGrammarException bsge) {
			logger.info("BadSqlGrammarException in getWBasketAttrRatePlanDTOALL()");

			itemList = null;
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException in getWBasketAttrRatePlanDTOALL()");

			itemList = null;
		} catch (Exception e) {
			logger.info("Exception caught in getWBasketAttrRatePlanDTOALL(): ", e);

			throw new DAOException(e.getMessage(), e);
		}
		return itemList;
	}
	
	public List<BasketAssoWBasketAttrRatePlanDTO> getWBasketAttrRatePlanDTOBySearch(BasketAssoWBasketAttrDTO dto) throws DAOException {
		logger.info("getWBasketAttrRatePlanDTOBySearch @ MobCcsBasketAssoWBasketAttrDAO is called");
		List<BasketAssoWBasketAttrRatePlanDTO> itemList = Collections.emptyList();
		String sql = "SELECT" +
				" DISTINCT b.rate_plan AS rate_plan, b.rate_plan_id" +
				" FROM W_CHANNEL_BASKET_ASSGN c" +
				" JOIN W_BASKET_ATTRIBUTE_MV b ON (c.basket_id = b.basket_id)" +
				" WHERE c.channel_id = 2" +
				" AND NVL(b.NATURE, 'ACQ') = 'ACQ' " +
				" AND TRUNC(sysdate) BETWEEN NVL(c.EFF_START_DATE, sysdate) AND NVL(c.EFF_END_DATE, sysdate)" +
				" AND b.rate_plan_id IS NOT NULL";
		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			StringBuilder searchSQL = new StringBuilder(sql);
			if (StringUtils.isNotBlank(dto.getCustomerTierId())) {
				searchSQL.append(" AND customer_tier_id = :customerTierId");
				params.addValue("customerTierId", dto.getCustomerTierId());
			}
			if (StringUtils.isNotBlank(dto.getBasketTypeId())) {
				searchSQL.append(" AND basket_type_id = :basketTypeId");
				params.addValue("basketTypeId", dto.getBasketTypeId());
			}
			searchSQL.append(" ORDER BY rate_plan_id");
			logger.info("getWBasketAttrRatePlanDTOBySearch() @ MobCcsBasketAssoWBasketAttrDAO: " + searchSQL);

			itemList = simpleJdbcTemplate.query(searchSQL.toString(), this.getWBasketAttrRatePlanDTORowMapper(), params);
		} catch (BadSqlGrammarException bsge) {
			logger.info("BadSqlGrammarException in getWBasketAttrRatePlanDTOBySearch()");

			itemList = null;
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException in getWBasketAttrRatePlanDTOBySearch()");

			itemList = null;
		} catch (Exception e) {
			logger.info("Exception caught in getWBasketAttrRatePlanDTOBySearch(): ", e);

			throw new DAOException(e.getMessage(), e);
		}
		return itemList;
	}

	public List<BasketAssoWBasketAttrBrandModelDTO> getWBasketAttrBrandModelDTOALL() throws DAOException {
		logger.info("getWBasketAttrBrandModelDTOALL @ MobCcsBasketAssoWBasketAttrDAO is called");
		List<BasketAssoWBasketAttrBrandModelDTO> itemList = Collections.emptyList();
		String sql = "SELECT" +
				" DISTINCT b.brand||' '||b.MODEL AS brand_mode_desc, b.brand_id, b.model_id " +
				" FROM W_CHANNEL_BASKET_ASSGN c" +
				" JOIN W_BASKET_ATTRIBUTE_MV b ON (c.basket_id = b.basket_id)" +
				" WHERE c.channel_id = 2" +
				" AND NVL(b.NATURE, 'ACQ') = 'ACQ' " +
				" AND TRUNC(sysdate) BETWEEN NVL(c.EFF_START_DATE, sysdate) AND NVL(c.EFF_END_DATE, sysdate)" +
				" AND b.brand_id IS NOT NULL" +
				" ORDER BY b.brand_mode_desc";
		try {
			logger.info("getWBasketAttrBrandModelDTOALL() @ MobCcsBasketAssoWBasketAttrDAO: " + sql);

			itemList = simpleJdbcTemplate.query(sql, this.getWBasketAttrBrandModelDTORowMapper());
		} catch (BadSqlGrammarException bsge) {
			logger.info("BadSqlGrammarException in getWBasketAttrBrandModelDTOALL()");

			itemList = null;
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException in getWBasketAttrBrandModelDTOALL()");

			itemList = null;
		} catch (Exception e) {
			logger.info("Exception caught in getWBasketAttrBrandModelDTOALL(): ", e);

			throw new DAOException(e.getMessage(), e);
		}
		return itemList;
	}
	
	public List<BasketAssoWBasketAttrBrandModelDTO> getWBasketAttrBrandModelDTOBySearch(BasketAssoWBasketAttrDTO dto) throws DAOException {
		logger.info("getWBasketAttrBrandModelDTOBySearch @ MobCcsBasketAssoWBasketAttrDAO is called");
		List<BasketAssoWBasketAttrBrandModelDTO> itemList = Collections.emptyList();
		String sql = "SELECT" +
				" DISTINCT b.brand||' '||b.MODEL AS brand_mode_desc, b.brand_id, b.model_id " +
				" FROM W_CHANNEL_BASKET_ASSGN c" +
				" JOIN W_BASKET_ATTRIBUTE_MV b ON (c.basket_id = b.basket_id)" +
				" WHERE c.channel_id = 2" +
				" AND NVL(b.NATURE, 'ACQ') = 'ACQ' " +
				" AND TRUNC(sysdate) BETWEEN NVL(c.EFF_START_DATE, sysdate) AND NVL(c.EFF_END_DATE, sysdate)" +
				" AND b.brand_id IS NOT NULL";
		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			StringBuilder searchSQL = new StringBuilder(sql);

			if (StringUtils.isNotBlank(dto.getCustomerTierId())) {
				searchSQL.append(" AND customer_tier_id = :customerTierId");
				params.addValue("customerTierId", dto.getCustomerTierId());
			}
			if (StringUtils.isNotBlank(dto.getBasketTypeId())) {
				searchSQL.append(" AND basket_type_id = :basketTypeId");
				params.addValue("basketTypeId", dto.getBasketTypeId());
			}
			searchSQL.append(" ORDER BY brand_mode_desc");
			logger.info("getWBasketAttrBrandModelDTOBySearch() @ MobCcsBasketAssoWBasketAttrDAO: " + searchSQL);
			
			itemList = simpleJdbcTemplate.query(searchSQL.toString(), this.getWBasketAttrBrandModelDTORowMapper(), params);
		} catch (BadSqlGrammarException bsge) {
			logger.info("BadSqlGrammarException in getWBasketAttrBrandModelDTOBySearch()");

			itemList = null;
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException in getWBasketAttrBrandModelDTOBySearch()");

			itemList = null;
		} catch (Exception e) {
			logger.info("Exception caught in getWBasketAttrBrandModelDTOBySearch(): ", e);

			throw new DAOException(e.getMessage(), e);
		}
		return itemList;
	}

	public List<BasketAssoWBasketAttrDTO> getWBasketAttrDTOBySearch(BasketAssoWBasketAttrDTO dto, String basketDesc) throws DAOException {
		logger.info("getWBasketAttrDTOBySearch @ MobCcsBasketAssoWBasketAttrDAO is called");
		List<BasketAssoWBasketAttrDTO> itemList = Collections.emptyList();
		String sql = "SELECT" +
				" c.basket_id, ba.description" +
				" FROM W_CHANNEL_BASKET_ASSGN c" +
				" JOIN W_BASKET_ATTRIBUTE_MV ba ON (c.basket_id = ba.basket_id)" +
				" WHERE c.channel_id = 2" +
				" AND NVL(ba.NATURE, 'ACQ') = 'ACQ' " +
				" AND TRUNC(sysdate) BETWEEN NVL(c.EFF_START_DATE, sysdate) AND NVL(c.EFF_END_DATE, sysdate)";
		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			StringBuilder searchSQL = new StringBuilder(sql);
			
			if (dto != null && StringUtils.isNotBlank(dto.getCustomerTierId())) {
				searchSQL.append(" AND ba.customer_tier_id = :customerTierId");
				params.addValue("customerTierId", dto.getCustomerTierId());
			}
			if (dto != null && StringUtils.isNotBlank(dto.getBasketTypeId())) {
				searchSQL.append(" AND ba.basket_type_id = :basketTypeId");
				params.addValue("basketTypeId", dto.getBasketTypeId());
			}
			if (dto != null && StringUtils.isNotBlank(dto.getRatePlanId())) {
				searchSQL.append(" AND ba.rate_plan_id = :ratePlanId");
				params.addValue("ratePlanId", dto.getRatePlanId());
			}
			if (dto != null && StringUtils.isNotBlank(dto.getBrandId())) {
				searchSQL.append(" AND ba.brand_id = :brandId");
				params.addValue("brandId", dto.getBrandId());
			}
			if (dto != null && StringUtils.isNotBlank(dto.getModelId())) {
				searchSQL.append(" AND ba.model_id = :modelId");
				params.addValue("modelId", dto.getModelId());
			}
			if (StringUtils.isNotBlank(basketDesc)) {
				searchSQL.append(" AND UPPER(ba.description) LIKE UPPER(:basketDesc)");
				params.addValue("basketDesc", basketDesc.replaceAll("\\*", "%"));
			}
			
			searchSQL.append(" ORDER BY c.basket_id, ba.customer_tier_id, ba.brand_id, ba.model_id");
			
			logger.info("getWBasketAttrDTOBySearch() @ MobCcsBasketAssoWBasketAttrDAO: " + searchSQL);

			itemList = simpleJdbcTemplate.query(searchSQL.toString(), this.getWBasketAttrDTORowMapper(), params);
		} catch (BadSqlGrammarException bsge) {
			logger.info("BadSqlGrammarException in getWBasketAttrDTOBySearch()");

			itemList = null;
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException in getWBasketAttrDTOBySearch()");

			itemList = null;
		} catch (Exception e) {
			logger.info("Exception caught in getWBasketAttrDTOBySearch(): ", e);

			throw new DAOException(e.getMessage(), e);
		}
		return itemList;
	}
	
	public ParameterizedRowMapper<BasketAssoWBasketAttrCustomerTierDTO> getWBasketAttrCustomerTierDTORowMapper() {
		return new ParameterizedRowMapper<BasketAssoWBasketAttrCustomerTierDTO>() {
			public BasketAssoWBasketAttrCustomerTierDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				BasketAssoWBasketAttrCustomerTierDTO dto = new BasketAssoWBasketAttrCustomerTierDTO();
				dto.setCustomerTierId(rs.getString("customer_tier_id"));
				dto.setCustomerTier(rs.getString("customer_tier"));
				return dto;
			}
		};
	}

	public ParameterizedRowMapper<BasketAssoWBasketAttrBasketTypeDTO> getWBasketAttrBasketTypeDTORowMapper() {
		return new ParameterizedRowMapper<BasketAssoWBasketAttrBasketTypeDTO>() {
			public BasketAssoWBasketAttrBasketTypeDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				BasketAssoWBasketAttrBasketTypeDTO dto = new BasketAssoWBasketAttrBasketTypeDTO();
				dto.setBasketTypeId(rs.getString("basket_type_id"));
				dto.setBasketType(rs.getString("basket_type"));
				return dto;
			}
		};
	}
	
	public ParameterizedRowMapper<BasketAssoWBasketAttrRatePlanDTO> getWBasketAttrRatePlanDTORowMapper() {
		return new ParameterizedRowMapper<BasketAssoWBasketAttrRatePlanDTO>() {
			public BasketAssoWBasketAttrRatePlanDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				BasketAssoWBasketAttrRatePlanDTO dto = new BasketAssoWBasketAttrRatePlanDTO();
				dto.setRatePlanId(rs.getString("rate_plan_id"));
				dto.setRatePlan(rs.getString("rate_plan"));
				return dto;
			}
		};
	}
	
	public ParameterizedRowMapper<BasketAssoWBasketAttrBrandModelDTO> getWBasketAttrBrandModelDTORowMapper() {
		return new ParameterizedRowMapper<BasketAssoWBasketAttrBrandModelDTO>() {
			public BasketAssoWBasketAttrBrandModelDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				BasketAssoWBasketAttrBrandModelDTO dto = new BasketAssoWBasketAttrBrandModelDTO();
				dto.setBrandId(rs.getString("brand_id"));
				dto.setModelId(rs.getString("model_id"));
				dto.setBrandModelDesc(rs.getString("brand_mode_desc"));
				return dto;
			}
		};
	}
	
	public ParameterizedRowMapper<BasketAssoWBasketAttrDTO> getWBasketAttrDTORowMapper() {
		return new ParameterizedRowMapper<BasketAssoWBasketAttrDTO>() {
			public BasketAssoWBasketAttrDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				BasketAssoWBasketAttrDTO dto = new BasketAssoWBasketAttrDTO();
				dto.setBasketId(rs.getString("basket_id"));
				dto.setBasketDesc(rs.getString("description"));
				return dto;
			}
		};
	}
}
