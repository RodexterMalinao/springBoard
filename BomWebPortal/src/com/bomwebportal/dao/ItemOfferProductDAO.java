package com.bomwebportal.dao;

import java.sql.Types;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;

import com.bomwebportal.dto.ItemOfferProductDTO;
import com.bomwebportal.exception.DAOException;

public class ItemOfferProductDAO extends BaseDAO {
	public List<ItemOfferProductDTO> getItemOfferProductDTOs(String basketId, Date appInDate, String[] vasItemIds, String mipBrand, String mipSimType ) throws DAOException {
		final String basketSql = "SELECT" +
				"  I.ID ITEM_ID" +
				"  , I.DESCRIPTION ITEM_DESC" +
				"  , IOA.OFFER_ID" +
				"  , IOA.OFFER_CD" +
				"  , IOPA.PRODUCT_ID" +
				"  , IOPA.PROD_CD PRODUCT_CD" +
				" FROM" +
				"  W_BASKET B" +
				"  INNER JOIN W_BASKET_ITEM_ASSGN BIA ON (B.ID = BIA.BASKET_ID)" +
				"  INNER JOIN W_ITEM I ON (BIA.ITEM_ID = I.ID)" +
				"  INNER JOIN W_ITEM_OFFER_ASSGN IOA ON (I.ID = IOA.ITEM_ID)" +
				"  INNER JOIN W_ITEM_OFFER_PRODUCT_ASSGN IOPA ON (IOA.ITEM_ID = IOPA.ITEM_ID AND IOA.ITEM_OFFER_SEQ = IOPA.ITEM_OFFER_SEQ)" +
				" WHERE" +
				"  B.ID = :basketId" +
				"		    and nvl(decode (I.MIP_BRAND, '9', :mipBrand, I.MIP_BRAND ), '1') = :mipBrand \n" +
				"			and nvl(decode (I.MIP_SIM_TYPE, 'X', :mipSimType, I.MIP_SIM_TYPE ), 'H') = :mipSimType \n" +
				"  AND I.TYPE <> 'SIM'" + // exclude W_ITEM.TYPE = 'SIM'
				"  AND TRUNC(:appInDate) BETWEEN TRUNC(NVL(BIA.EFF_START_DATE, sysdate)) AND TRUNC(NVL(BIA.EFF_END_DATE, sysdate))";
		List<ItemOfferProductDTO> list = Collections.emptyList();
		try {
			StringBuilder sql = new StringBuilder(basketSql);
			
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("basketId", basketId);
			params.addValue("appInDate", appInDate, Types.DATE);
			params.addValue("mipBrand", mipBrand);
			params.addValue("mipSimType", mipSimType);
			
			if (!this.isEmpty(vasItemIds)) {
				final String basketExcludeBVasSql = " AND BIA.ITEM_ID NOT IN" +
						" (" +
						"  SELECT" +
						"   BIA.ITEM_ID" +
						"  FROM" +
						"   W_BASKET_ITEM_ASSGN BIA" +
						"  WHERE" +
						"   BIA.BASKET_ID = :basketId" +
						"   AND BIA.ITEM_ID IN" +
						"   (" +
						"    SELECT E.ITEM_ID_B FROM W_MOB_ITEM_EXCLUSIVE_LKUP E WHERE E.ITEM_ID_A IN (:vasItemIds) AND E.ITEM_TYPE_B = 'BVAS'" +
						"     union" +
						"    SELECT E.ITEM_ID_A FROM W_MOB_ITEM_EXCLUSIVE_LKUP E WHERE E.ITEM_ID_B IN (:vasItemIds) AND E.ITEM_TYPE_A = 'BVAS'" +
						"   )" +
						" )";
				final String vasItemSql = "SELECT" +
						"  I.ID ITEM_ID" +
						"  , I.DESCRIPTION ITEM_DESC" +
						"  , IOA.OFFER_ID" +
						"  , IOA.OFFER_CD" +
						"  , IOPA.PRODUCT_ID" +
						"  , IOPA.PROD_CD PRODUCT_CD" +
						" FROM" +
						"  W_ITEM I" +
						"  INNER JOIN W_ITEM_OFFER_ASSGN IOA ON (I.ID = IOA.ITEM_ID)" +
						"  INNER JOIN W_ITEM_OFFER_PRODUCT_ASSGN IOPA ON (IOA.ITEM_ID = IOPA.ITEM_ID AND IOA.ITEM_OFFER_SEQ = IOPA.ITEM_OFFER_SEQ)" +
						" WHERE" +
						"  I.ID IN (:vasItemIds)";
				sql.append(basketExcludeBVasSql);
				sql.append(" union ");
				sql.append(vasItemSql);
				params.addValue("vasItemIds", Arrays.asList(vasItemIds));
			}
			
			if (logger.isDebugEnabled()) {
				logger.debug("sql: " + sql);
				logger.debug("basketId: " + basketId);
				logger.debug("appInDate: " + appInDate);
				logger.debug("vasItemIds: " + (this.isEmpty(vasItemIds) ? "null" : StringUtils.join(vasItemIds, ",")));
			}
			list = this.simpleJdbcTemplate.query(sql.toString(), ParameterizedBeanPropertyRowMapper.newInstance(ItemOfferProductDTO.class), params);
		} catch (Exception e) {
			logger.warn("Exception in getItemOfferProductDTOs", e);
			throw new DAOException(e);
		}
		return list;
	}
/*
	private ParameterizedRowMapper<ItemOfferProductDTO> getRowMapper() {
		return new ParameterizedRowMapper<ItemOfferProductDTO>() {
		public ItemOfferProductDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
			ItemOfferProductDTO dto = new ItemOfferProductDTO();
			dto.setItemId(rs.getString("ITEM_ID"));
			dto.setItemDesc(rs.getString("ITEM_DESC"));
			dto.setOfferId(rs.getString("OFFER_ID"));
			dto.setOfferCd(rs.getString("OFFER_CD"));
			dto.setProductId(rs.getString("PRODUCT_ID"));
			dto.setProductCd(rs.getString("PRODUCT_CD"));
			return dto;
		}};
	}
*/	
	private <T> boolean isEmpty(T[] values) {
		return values == null || values.length == 0;
	}
}
