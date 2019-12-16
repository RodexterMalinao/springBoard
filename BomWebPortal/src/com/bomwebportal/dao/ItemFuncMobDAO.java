package com.bomwebportal.dao;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;

import com.bomwebportal.dto.ItemFuncAssgnMobDTO;
import com.bomwebportal.exception.DAOException;

public class ItemFuncMobDAO extends BaseDAO {
	protected final Log logger = LogFactory.getLog(getClass());

	
	private static String GetItemFuncAssgnMobSql = "SELECT "
			+ " F.ITEM_ID, "
			+ " F.FUNC_DESC, "
			+ " F.FUNC_ID, "
			+ " F.EFF_START_DATE, "
			+ " F.EFF_END_DATE, "
			+ " C.CODE_DESC "
			+ " FROM "
			//+ " W_ITEM_FUNC_ASSGN_MOB_VIEW "
			+ " W_ITEM_FUNC_ASSGN_MOB_VIEW F, BOMWEB_CODE_LKUP C "			
			+ " WHERE "
			//+ " ITEM_ID=:itemId "
			+ " ITEM_ID=:itemId "			
			+ " AND FUNC_ID=:funcId "
			+ " AND F.FUNC_ID=C.CODE_ID(+) "
			+ " AND C.CODE_TYPE(+)='EXTRA_INFO_FUNC'"

			;
	
	public ItemFuncAssgnMobDTO getItemFuncAssgnMobDTO(String itemId, String funcId) throws DAOException {
		logger.debug("getItemFuncAssgnMobDTO is called");

		if (logger.isDebugEnabled()) {
			logger.debug(GetItemFuncAssgnMobSql);
			logger.debug("itemId: " + itemId + ", funcId: " + funcId);
		}
		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("itemId", itemId);
			params.addValue("funcId", funcId);
			List<ItemFuncAssgnMobDTO> list = this.simpleJdbcTemplate.query(GetItemFuncAssgnMobSql,
					ParameterizedBeanPropertyRowMapper.newInstance(ItemFuncAssgnMobDTO.class), params);
			
			return CollectionUtils.isEmpty(list) ? null : list.get(0);
			
		} catch (EmptyResultDataAccessException erdae) {
			if (logger.isDebugEnabled()) {
				logger.debug("EmptyResultDataAccessException in getItemFuncAssgnMobDTO()");
			}
			return null;
		} catch (Exception e) {
			logger.info("Exception caught in getItemFuncAssgnMobDTO():", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	private static String FindItemFuncAssgnMobByItemIdSql = "SELECT "
			+ " F.ITEM_ID, "
			+ " F.FUNC_DESC, "
			+ " F.FUNC_ID, "
			+ " F.EFF_START_DATE, "
			+ " F.EFF_END_DATE, "
			+ " C.CODE_DESC AS EXTRA_INFO "
			+ " FROM "
			+ " W_ITEM_FUNC_ASSGN_MOB_VIEW F,  "
			+ " BOMWEB_CODE_LKUP C "
			+ " WHERE "
			+ " F.ITEM_ID=:itemId "
			+ " AND TO_DATE(:appDate, 'DD/MM/YYYY') BETWEEN TRUNC(NVL(F.EFF_START_DATE, sysdate)) AND TRUNC(NVL(F.EFF_END_DATE, sysdate)) " 			
			+ " AND F.FUNC_ID=C.CODE_ID(+) "
			+ " AND C.CODE_TYPE(+)='EXTRA_INFO_FUNC'"
			;


	public List<ItemFuncAssgnMobDTO> findItemFuncAssgnMobDTO(String itemId, String appDate) throws DAOException {
		logger.debug("findItemFuncAssgnMobDTO is called");

		if (logger.isDebugEnabled()) {
			logger.debug(FindItemFuncAssgnMobByItemIdSql);
			logger.debug("itemId: " + itemId + ", appDate:" + appDate);
		}
		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("itemId", itemId);
			params.addValue("appDate", appDate);
			List<ItemFuncAssgnMobDTO> list = this.simpleJdbcTemplate.query(FindItemFuncAssgnMobByItemIdSql,
					ParameterizedBeanPropertyRowMapper.newInstance(ItemFuncAssgnMobDTO.class), params);
			return list;
		} catch (EmptyResultDataAccessException erdae) {
			if (logger.isDebugEnabled()) {
				logger.debug("EmptyResultDataAccessException in findItemFuncAssgnMobDTO()");
			}
			return null;
		} catch (Exception e) {
			logger.info("Exception caught in findItemFuncAssgnMobDTO():", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
}
