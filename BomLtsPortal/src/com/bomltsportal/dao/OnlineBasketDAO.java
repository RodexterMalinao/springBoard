package com.bomltsportal.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.bomwebportal.dao.BaseDAO;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.lts.dto.BasketDetailDTO;

public class OnlineBasketDAO extends BaseDAO {

	
	public List<BasketDetailDTO> getBasketDetailList(String channelId,
			boolean parallelExtension, String displayType, String locale) throws DAOException {
		
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT WB.ID, WB.TYPE, WB.BASE_BASKET_ID, WBDL.EXTEND_CONTRACT_PERIOD, ");
		sql.append(" WBDLKUP.LOCALE, WBDLKUP.DESCRIPTION, WBASEBDLKUP.DESCRIPTION AS BASE_BASKET_DESC, ");
		sql.append(" WBDLKUP.HTML, WBDLKUP.IMAGE_PATH, WBDLKUP.DISPLAY_TYPE, WBDLKUP.HTML2, "); 
		sql.append(" WBDL.IDD_FREE_MIN, WBDL.PE_IND, ");
		sql.append(" WBDL.CONTRACT_PERIOD, BP.RECURRENT_AMT, BP.RECURRENT_AMT_TXT, ");
		sql.append(" BP.MTH_TO_MTH_RATE, BP.MTH_TO_MTH_RATE_TXT ");
		sql.append("FROM W_CHANNEL_BASKET_ASSGN WCBS, ");
		sql.append(" W_BASKET WB, W_BASKET_DTL_LTS WBDL, ");
		sql.append(" W_BASKET_DISPLAY_LKUP WBDLKUP,  W_BASKET_DISPLAY_LKUP WBASEBDLKUP, ");
		sql.append(" ( SELECT WBIA.BASKET_ID, WIP.RECURRENT_AMT, WIPDL.RECURRENT_AMT_TXT, ");
		sql.append("  WIP.MTH_TO_MTH_RATE, WIPDL.MTH_TO_MTH_RATE_TXT ");
		sql.append("  FROM W_BASKET_ITEM_ASSGN WBIA, ");
		sql.append("  W_ITEM WI, ");
		sql.append("  W_ITEM_PRICING WIP, ");
		sql.append("  W_ITEM_PRICING_DISPLAY_LKUP WIPDL");
		sql.append("  WHERE WI.ID = WIP.ID ");
		sql.append("  AND WIP.ID = WIPDL.ITEM_ID ");
		sql.append("  AND WBIA.ITEM_ID = WI.ID ");
		sql.append("  AND WI.TYPE = 'PLAN' ");
		sql.append("  AND WIPDL.locale = :locale ");
		sql.append("  AND WI.LOB = 'LTS') BP ");
		sql.append("WHERE WCBS.CHANNEL_ID = :channelId ");
		sql.append(" AND WCBS.BASKET_ID = WB.ID ");
		sql.append(" AND WBDL.ID = WB.ID ");
		sql.append(" AND WBDL.PE_IND = :parallelExtension ");
		sql.append(" AND WBDLKUP.BASKET_ID = WB.ID ");
		sql.append(" AND WBDLKUP.DISPLAY_TYPE = :displayType ");
		sql.append(" AND WBDLKUP.LOCALE = :locale ");
		sql.append(" AND WBASEBDLKUP.BASKET_ID = WB.BASE_BASKET_ID ");
		sql.append(" AND WBASEBDLKUP.DISPLAY_TYPE = :displayType ");
		sql.append(" AND WBASEBDLKUP.LOCALE = :locale ");
		sql.append(" AND BP.BASKET_ID = WB.ID ");
		sql.append("ORDER BY BP.RECURRENT_AMT DESC, WBDL.CONTRACT_PERIOD DESC ");
		
		paramSource.addValue("locale", locale);
		paramSource.addValue("channelId", channelId);
		paramSource.addValue("parallelExtension", parallelExtension ? "Y" : "N");
		paramSource.addValue("displayType", displayType);
		paramSource.addValue("locale", locale);
		paramSource.addValue("displayType", displayType);
		paramSource.addValue("locale", locale);
		
		try {
			return (List<BasketDetailDTO>) simpleJdbcTemplate.getNamedParameterJdbcOperations().query(sql.toString(), paramSource, getBasketMapper(true, true));

		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException in getBasketList()");
			return null;
		} catch (Exception e) {
			logger.error("Exception caught in getEyeDeviceList():", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public List<BasketDetailDTO> getBasketDetailHousingList(String channelId,
			boolean parallelExtension, String displayType, String locale, String housingType, String pServiceBoundary) throws DAOException {
		
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT WB.ID, WB.TYPE, WB.BASE_BASKET_ID, WBDL.EXTEND_CONTRACT_PERIOD, ");
		sql.append(" WBDLKUP.LOCALE, WBDLKUP.DESCRIPTION, WBASEBDLKUP.DESCRIPTION AS BASE_BASKET_DESC, ");
		sql.append(" WBDLKUP.HTML, WBDLKUP.IMAGE_PATH, WBDLKUP.DISPLAY_TYPE, WBDLKUP.HTML2, "); 
		sql.append(" WBDL.IDD_FREE_MIN, WBDL.PE_IND, ");
		sql.append(" WBDL.CONTRACT_PERIOD, BP.RECURRENT_AMT, BP.RECURRENT_AMT_TXT, ");
		sql.append(" BP.MTH_TO_MTH_RATE, BP.MTH_TO_MTH_RATE_TXT ");
		sql.append("FROM W_CHANNEL_BASKET_ASSGN WCBS, ");
		sql.append(" W_BASKET WB, W_BASKET_DTL_LTS WBDL, ");
		sql.append(" W_BASKET_DISPLAY_LKUP WBDLKUP,  W_BASKET_DISPLAY_LKUP WBASEBDLKUP, ");
		sql.append(" ( SELECT WBIA.BASKET_ID, WIP.RECURRENT_AMT, WIPDL.RECURRENT_AMT_TXT, ");
		sql.append("  WIP.MTH_TO_MTH_RATE, WIPDL.MTH_TO_MTH_RATE_TXT ");
		sql.append("  FROM W_BASKET_ITEM_ASSGN WBIA, ");
		sql.append("  W_ITEM WI, ");
		sql.append("  W_ITEM_PRICING WIP, ");
		sql.append("  W_ITEM_PRICING_DISPLAY_LKUP WIPDL");
		sql.append("  WHERE WI.ID = WIP.ID ");
		sql.append("  AND WIP.ID = WIPDL.ITEM_ID ");
		sql.append("  AND WBIA.ITEM_ID = WI.ID ");
		sql.append("  AND WI.TYPE = 'PLAN' ");
		sql.append("  AND WIPDL.locale = :locale ");
		sql.append("  AND WI.LOB = 'LTS') BP ");
		sql.append("WHERE WCBS.CHANNEL_ID = :channelId ");
		sql.append(" AND WCBS.BASKET_ID = WB.ID ");
		sql.append(" AND WBDL.ID = WB.ID ");
		sql.append(" AND WBDL.PE_IND = :parallelExtension ");
		sql.append(" AND WBDLKUP.BASKET_ID = WB.ID ");
		sql.append(" AND WBDLKUP.DISPLAY_TYPE = :displayType ");
		sql.append(" AND WBDLKUP.LOCALE = :locale ");
		sql.append(" AND WBASEBDLKUP.BASKET_ID = WB.BASE_BASKET_ID ");
		sql.append(" AND WBASEBDLKUP.DISPLAY_TYPE = :displayType ");
		sql.append(" AND WBASEBDLKUP.LOCALE = :locale ");
		sql.append(" AND BP.BASKET_ID = WB.ID ");
		
		if(StringUtils.isNotBlank(housingType)){
			sql.append(" AND (WBDL.AVAIL_HOUSING_TYPE is null OR WBDL.AVAIL_HOUSING_TYPE = :availHousingType)");
			paramSource.addValue("availHousingType", housingType);
		}
		
		if(StringUtils.isNotBlank(housingType)){
			sql.append(" AND (WBDL.HOUSING_TYPE is null OR UPPER(WBDL.HOUSING_TYPE) like UPPER(:housingType))");
			paramSource.addValue("housingType", "%" + housingType + "%");
		}
		
		if(StringUtils.isNotBlank(pServiceBoundary)){
			sql.append(" AND (WBDL.ELIGIBLE_HOUSING_TYPE in (select HOUSING_TYPE from W_LTS_HOUSING_SRV_BOUNDARY where SERVICE_BOUNDARY = :srvBoundary) ");
			sql.append(" 		OR WBDL.ELIGIBLE_HOUSING_TYPE IS NULL)");
			paramSource.addValue("srvBoundary", pServiceBoundary);
		}
		
		sql.append("ORDER BY BP.RECURRENT_AMT DESC, WBDL.CONTRACT_PERIOD DESC ");
		
		paramSource.addValue("locale", locale);
		paramSource.addValue("channelId", channelId);
		paramSource.addValue("parallelExtension", parallelExtension ? "Y" : "N");
		paramSource.addValue("displayType", displayType);
		paramSource.addValue("locale", locale);
		paramSource.addValue("displayType", displayType);
		paramSource.addValue("locale", locale);
		
		try {
			return (List<BasketDetailDTO>) simpleJdbcTemplate.getNamedParameterJdbcOperations().query(sql.toString(), paramSource, getBasketMapper(true, true));

		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException in getBasketList()");
			return null;
		} catch (Exception e) {
			logger.error("Exception caught in getEyeDeviceList():", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	
	public BasketDetailDTO getBasket(String basketId, String locale, String displayType) throws DAOException {

		MapSqlParameterSource paramSource = new MapSqlParameterSource();

		StringBuilder sql = new StringBuilder();

		sql.append("SELECT WB.ID, WB.BASE_BASKET_ID, WBDL.EXTEND_CONTRACT_PERIOD,")
				.append(" WB.TYPE, WBDLKUP.LOCALE, WBDLKUP.DESCRIPTION,")
				.append(" WBDL.IDD_FREE_MIN, WBDL.CONTRACT_PERIOD, WBDL.PE_IND,")
				.append(" WBDLKUP.HTML, WBDLKUP.IMAGE_PATH, WBDLKUP.DISPLAY_TYPE, WBDLKUP.HTML2 ")
				.append(" FROM").append(" W_BASKET WB,")
				.append(" W_BASKET_DTL_LTS WBDL,")
				.append(" W_BASKET_DISPLAY_LKUP WBDLKUP")
				.append(" WHERE WB.ID = WBDL.ID ")
				.append(" AND WB.ID = WBDLKUP.BASKET_ID")
				.append(" AND WB.ID = :basketId")
				.append(" AND WBDLKUP.LOCALE = :locale")
				.append(" AND WBDLKUP.DISPLAY_TYPE = :displayType");


		paramSource.addValue("basketId", basketId);
		paramSource.addValue("locale", locale);
		paramSource.addValue("displayType", displayType);

		
		try {
			return (BasketDetailDTO) simpleJdbcTemplate
					.getNamedParameterJdbcOperations().queryForObject(
							sql.toString(), paramSource, getBasketMapper(false, false));

		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException in getBasketList() - " + basketId);
			return null;
		} catch (Exception e) {
			logger.error("Exception caught in getBasketList() - " + basketId, e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	private ParameterizedRowMapper<BasketDetailDTO> getBasketMapper(final boolean getRecurrentAmt, final boolean getBaseBasketDesc) {
		return  new ParameterizedRowMapper<BasketDetailDTO>() {
			public BasketDetailDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				BasketDetailDTO basketDetail = new BasketDetailDTO();
				basketDetail.setBasketId(rs.getString("ID"));
				basketDetail.setType(rs.getString("TYPE"));
				basketDetail.setLocale(rs.getString("LOCALE"));
				basketDetail.setDesc(rs.getString("DESCRIPTION"));
				basketDetail.setHtml(rs.getString("HTML"));
				basketDetail.setImagePath(rs.getString("IMAGE_PATH"));
				basketDetail.setDisplayType(rs.getString("DISPLAY_TYPE"));
				basketDetail.setIddFreeMin(rs.getString("IDD_FREE_MIN"));
				basketDetail.setContractPeriod(rs.getString("CONTRACT_PERIOD"));
				basketDetail.setPeInd(rs.getString("PE_IND"));
				basketDetail.setBaseBasketId(rs.getString("BASE_BASKET_ID"));
				basketDetail.setExtendContractPeriod(rs.getString("EXTEND_CONTRACT_PERIOD"));
				basketDetail.setUrl(rs.getString("HTML2"));

				
				if (getBaseBasketDesc) {
					basketDetail.setBaseBasketDesc(rs.getString("BASE_BASKET_DESC"));	
				}
				
				if (getRecurrentAmt) {
					basketDetail.setRecurrentAmt(rs.getString("RECURRENT_AMT"));
					basketDetail.setRecurrentAmtTxt(rs.getString("RECURRENT_AMT_TXT"));
					basketDetail.setMthToMthAmt(rs.getString("MTH_TO_MTH_RATE"));
					basketDetail.setMthToMthAmtTxt(rs.getString("MTH_TO_MTH_RATE_TXT"));
				}
				
				return basketDetail;
			}
		};
	}

}
