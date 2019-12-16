package com.bomwebportal.dao;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;

import com.bomwebportal.dto.DepositDTO;
import com.bomwebportal.dto.DepositLkupDTO;
import com.bomwebportal.exception.DAOException;

public class DepositDAO extends BaseDAO {
	protected final Log logger = LogFactory.getLog(getClass());
	
	
	private static String GetDepositDTOSql = 
			" SELECT d.ORDER_ID, "
			+ " d.DEPOSIT_ID, d.DEPOSIT_AMOUNT, "
			+ " d.ITEM_CD, d.WAIVE_IND, d.REASON_CD, w.REASON_DESC, "
			+ " d.CREATE_BY, d.CREATE_DATE, "
			+ " d.LAST_UPD_BY, d.LAST_UPD_DATE, "
			+ " lk.DEPOSIT_CD, lk.DEPOSIT_DESC, "
			+ " lk.WAIVABLE, lk.WAIVE_ON_AUTOPAY, lk.DEPOSIT_LEVEL "
			+ " FROM BOMWEB_DEPOSIT d, W_DEPOSIT_LKUP lk, W_WAIVE_LKUP w "
			+ " WHERE "
			+ " d.DEPOSIT_ID=lk.DEPOSIT_ID "
			+ " AND d.REASON_CD=w.REASON_CD(+) "
			+ " AND w.REASON_TYPE(+)='WAIVE_DEP' "
			+ " AND d.ORDER_ID=:orderId "
			+ " AND d.DEPOSIT_ID=:depositId ";
	public DepositDTO getDepositDTO(String orderId, String depositId) throws DAOException {
		logger.debug("getDepositDTO is called");
		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("orderId", orderId);
			params.addValue("depositId", depositId);
			List<DepositDTO> list = this.simpleJdbcTemplate.query(GetDepositDTOSql,
					ParameterizedBeanPropertyRowMapper.newInstance(DepositDTO.class), params);
			
			return CollectionUtils.isEmpty(list) ? null : list.get(0);
			
		} catch (EmptyResultDataAccessException erdae) {
			if (logger.isDebugEnabled()) {
				logger.debug("EmptyResultDataAccessException in getDepositDTO()");
			}
			return null;
		} catch (Exception e) {
			logger.info("Exception caught in getDepositDTO():", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public List<DepositDTO> findDepositDTOByOrderId(String orderId, String lang) throws DAOException {
//		logger.info("findDepositDTOByOrderId is called");
		String sql = 
				" SELECT d.ORDER_ID, "
						+ " d.DEPOSIT_ID, d.DEPOSIT_AMOUNT, "
						+ " d.ITEM_CD, d.WAIVE_IND, d.REASON_CD, w.REASON_DESC,"
						+ " d.CREATE_BY, d.CREATE_DATE, "
						+ " d.LAST_UPD_BY, d.LAST_UPD_DATE, "
						+ " lk.DEPOSIT_CD, wdl.DESCRIPTION as DEPOSIT_DESC, "
						+ " lk.WAIVABLE, lk.WAIVE_ON_AUTOPAY, lk.DEPOSIT_LEVEL "						
						+ " FROM BOMWEB_DEPOSIT d, W_DEPOSIT_LKUP lk, W_WAIVE_LKUP w, W_DISPLAY_LKUP wdl "
						+ " WHERE "
						+ " d.DEPOSIT_ID=lk.DEPOSIT_ID "
						+ " AND d.DEPOSIT_ID=wdl.ID "
						+ " AND wdl.TYPE='DEPOSIT' "
						+ " AND d.REASON_CD=w.REASON_CD(+) "
						+ " AND w.REASON_TYPE(+)='WAIVE_DEP' "					
						+ " AND d.ORDER_ID=:orderId "
						+ " AND wdl.LOCALE=:lang ";
		try {
			logger.debug("findDepositDTOByOrderId() @ DepositDAO: " + sql);

			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("orderId", orderId)
				.addValue("lang", lang);
			List<DepositDTO> list = this.simpleJdbcTemplate.query(sql,
					ParameterizedBeanPropertyRowMapper.newInstance(DepositDTO.class), params);
			
			return list;
			
		} catch (EmptyResultDataAccessException erdae) {
			if (logger.isDebugEnabled()) {
				logger.debug("EmptyResultDataAccessException in findDepositDTOByOrderId()");
			}
			return null;
		} catch (Exception e) {
			logger.info("Exception caught in findDepositDTOByOrderId():", e);
			throw new DAOException(e.getMessage(), e);
		}	
	}
	
	
	public int insertBomwebDeposit(DepositDTO dto) throws DAOException {
		logger.debug("insertBomwebDeposit is called");

		final String sql = "insert into" +
				" BOMWEB_DEPOSIT " +
				" (" +
				" ORDER_ID " +
				" , DEPOSIT_ID " +
				" , DEPOSIT_AMOUNT " +
				" , ITEM_CD " +
				" , WAIVE_IND " +
				" , REASON_CD " +
				" , CREATE_BY " +
				" , CREATE_DATE " +
				" , LAST_UPD_BY " + 
				" , LAST_UPD_DATE " +
				" ) values (" +
				" :orderId" +
				" , :depositId" +
				" , :depositAmount" +
				" , :itemCd" +
				" , :waiveInd" +
				" , :reasonCd" +
				" , :createBy" +
				" , sysdate" +
				" , :lastUpdBy" +
				" , sysdate" +
				" )";
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("orderId", dto.getOrderId());
		params.addValue("depositId", dto.getDepositId());
		params.addValue("depositAmount", dto.getDepositAmount());
		params.addValue("itemCd", dto.getItemCd());
		params.addValue("waiveInd", dto.getWaiveInd());
		params.addValue("reasonCd", dto.getReasonCd());
		params.addValue("createBy", dto.getCreateBy());
		params.addValue("lastUpdBy", dto.getLastUpdBy());

		try {
			logger.debug("insertBomwebDeposit() @ DepositDAO: " + sql);
			return simpleJdbcTemplate.update(sql, params);
		} catch (Exception e) {
			logger.error("Exception caught in insertBomwebDeposit()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public void deleteBomwebDeposit(String orderId) throws DAOException {
		logger.debug("deleteBomwebDeposit is called");
		String sql = "delete  BOMWEB_DEPOSIT where ORDER_ID = :orderId";
		
		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("orderId", orderId);
			simpleJdbcTemplate.update(sql, params);

		} catch (Exception e) {
			logger.error("Exception caught in deleteBomwebDeposit()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	
	
	public DepositLkupDTO getDepositLkup(String depositId) throws DAOException {
		logger.debug("getDepositLkup is called");

		final String sql = " select DEPOSIT_ID, "
				+ " DEPOSIT_CD, DEPOSIT_DESC , "
				+ " DEPOSIT_AMOUNT, DEPOSIT_LEVEL, "
				+ " WAIVABLE, WAIVE_ON_AUTOPAY, "
				+ " ITEM_CD, ITEM_DESC, "
				+ " START_DATE, END_DATE, "
				+ " CREATE_BY, CREATE_DATE, "
				+ " LAST_UPD_BY, LAST_UPD_DATE "
				+ " FROM W_DEPOSIT_LKUP "
				+ " WHERE "
				+ " DEPOSIT_ID=:depositId ";		
		try {
			logger.debug("getDepositLkup() @ DepositDAO: " + sql);

			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("depositId", depositId);
			List<DepositLkupDTO> list = this.simpleJdbcTemplate.query(sql,
					ParameterizedBeanPropertyRowMapper.newInstance(DepositLkupDTO.class), params);
			
			return CollectionUtils.isEmpty(list) ? null : list.get(0);
			
		} catch (EmptyResultDataAccessException erdae) {
			if (logger.isDebugEnabled()) {
				logger.debug("EmptyResultDataAccessException in getDepositLkup()");
			}
			return null;
		} catch (Exception e) {
			logger.info("Exception caught in getDepositLkup():", e);
			throw new DAOException(e.getMessage(), e);
		}		
	}
	
	public List<DepositLkupDTO> findDepositLkupByDepositCd(String depositCd, Date appDate) throws DAOException {
		logger.debug("findDepositLkupByDepositCd is called");

		final String sql = " select DEPOSIT_ID, "
				+ " DEPOSIT_CD, DEPOSIT_DESC, "
				+ " DEPOSIT_AMOUNT, DEPOSIT_LEVEL, "
				+ " WAIVABLE, WAIVE_ON_AUTOPAY, "
				+ " ITEM_CD, ITEM_DESC, "
				+ " START_DATE, END_DATE, "
				+ " CREATE_BY, CREATE_DATE, "
				+ " LAST_UPD_BY, LAST_UPD_DATE "
				+ " FROM W_DEPOSIT_LKUP "
				+ " WHERE "
				+ " DEPOSIT_CD=:depositCd "
				+ " AND TRUNC(:appDate) BETWEEN TRUNC(NVL(START_DATE, sysdate)) AND TRUNC(NVL(END_DATE, sysdate)) "
				;

		try {
			logger.debug("findDepositLkupByDepositCd() @ DepositDAO: " + sql);

			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("depositCd", depositCd);
			params.addValue("appDate", appDate);
			List<DepositLkupDTO> list = this.simpleJdbcTemplate.query(sql,
					ParameterizedBeanPropertyRowMapper.newInstance(DepositLkupDTO.class), params);
			
			return list;
			
		} catch (EmptyResultDataAccessException erdae) {
			if (logger.isDebugEnabled()) {
				logger.debug("EmptyResultDataAccessException in findDepositLkupByDepositCd()");
			}
			return null;
		} catch (Exception e) {
			logger.info("Exception caught in findDepositLkupByDepositCd():", e);
			throw new DAOException(e.getMessage(), e);
		}		
	}	
	
	
	public List<DepositLkupDTO> findDepositLkupByProductId(String productId, Date appDate) throws DAOException {
		return findDepositLkupByProductId(new String[]{productId}, appDate);
	}

	public List<DepositLkupDTO> findDepositLkupByProductId(String productIds[], Date appDate) throws DAOException {
		
		logger.debug("findDepositLkupByProductId is called");

		
		final String sql = " select d.DEPOSIT_ID, "
				+ " d.DEPOSIT_CD, d.DEPOSIT_DESC, "
				+ " d.DEPOSIT_AMOUNT, d.DEPOSIT_LEVEL, "
				+ " d.WAIVABLE, d.WAIVE_ON_AUTOPAY, "
				+ " d.ITEM_CD, d.ITEM_DESC, "
				+ " d.START_DATE, d.END_DATE, "
				+ " d.CREATE_BY, d.CREATE_DATE, "
				+ " d.LAST_UPD_BY, d.LAST_UPD_DATE "
				+ " FROM W_DEPOSIT_LKUP d, W_PRODUCT_DEPOSIT_ASSGN p "
				+ " WHERE "
				+ " d.DEPOSIT_ID=p.DEPOSIT_ID "
				+ " AND p.PRODUCT_ID IN (:productIds) "
				+ " AND TRUNC(:appDate) BETWEEN TRUNC(NVL(d.START_DATE, sysdate)) AND TRUNC(NVL(d.END_DATE, sysdate)) "
				+ " AND TRUNC(:appDate) BETWEEN TRUNC(NVL(p.EFF_START_DATE, sysdate)) AND TRUNC(NVL(p.EFF_END_DATE, sysdate)) "
				;

		try {
			logger.debug("findDepositLkupByProductId() @ DepositDAO: " + sql);

			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("productIds", Arrays.asList(productIds));
			params.addValue("appDate", appDate);
			List<DepositLkupDTO> list = this.simpleJdbcTemplate.query(sql,
					ParameterizedBeanPropertyRowMapper.newInstance(DepositLkupDTO.class), params);
			
			return list;
			
		} catch (EmptyResultDataAccessException erdae) {
			if (logger.isDebugEnabled()) {
				logger.debug("EmptyResultDataAccessException in findDepositLkupByProductId()");
			}
			return null;
		} catch (Exception e) {
			logger.info("Exception caught in findDepositLkupByProductId():", e);
			throw new DAOException(e.getMessage(), e);
		}		
	}
	
	public List<DepositLkupDTO> findAllDepositLkup(Date appDate) throws DAOException {
		logger.debug("findAllDepositLkup is called");

		final String sql = " select DEPOSIT_ID, "
				+ " DEPOSIT_CD, DEPOSIT_DESC, "
				+ " DEPOSIT_AMOUNT, DEPOSIT_LEVEL, "
				+ " WAIVABLE, WAIVE_ON_AUTOPAY, "
				+ " ITEM_CD, ITEM_DESC, "
				+ " START_DATE, END_DATE, "
				+ " CREATE_BY, CREATE_DATE, "
				+ " LAST_UPD_BY, LAST_UPD_DATE "
				+ " FROM W_DEPOSIT_LKUP "
				+ " WHERE "
				+ " TRUNC(:appDate) BETWEEN TRUNC(NVL(START_DATE, sysdate)) AND TRUNC(NVL(END_DATE, sysdate)) "
				;

		try {
			logger.debug("findAllDepositLkup() @ DepositDAO: " + sql);

			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("appDate", appDate);
			List<DepositLkupDTO> list = this.simpleJdbcTemplate.query(sql,
					ParameterizedBeanPropertyRowMapper.newInstance(DepositLkupDTO.class), params);
			
			return list;
			
		} catch (EmptyResultDataAccessException erdae) {
			if (logger.isDebugEnabled()) {
				logger.debug("EmptyResultDataAccessException in findDepositLkupByDepositCd()");
			}
			return null;
		} catch (Exception e) {
			logger.info("Exception caught in findAllDepositLkup():", e);
			throw new DAOException(e.getMessage(), e);
		}		
	}
	
	public BigDecimal getDepositAmountForOrder(String orderId) throws DAOException {
		String sql = "SELECT SUM(DEPOSIT_AMOUNT) TOTAL_DEPOSIT "
				+ " FROM BOMWEB_DEPOSIT "
				+ " WHERE "
				+ " ORDER_ID=:orderId "
				+ " AND WAIVE_IND != 'Y' ";
		try {
			logger.info("findAllDepositLkup() @ DepositDAO: " + sql);

			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("orderId", orderId);
			BigDecimal amount = this.simpleJdbcTemplate.queryForObject(sql, BigDecimal.class, params);
			return amount;
			
		} catch (EmptyResultDataAccessException erdae) {
			if (logger.isDebugEnabled()) {
				logger.debug("EmptyResultDataAccessException in findDepositLkupByDepositCd()");
			}
			return null;
		} catch (Exception e) {
			logger.info("Exception caught in findAllDepositLkup():", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
}
