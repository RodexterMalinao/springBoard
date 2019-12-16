package com.bomwebportal.lts.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.web.util.HtmlUtils;

import com.bomwebportal.dao.BaseDAO;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.lts.dto.BasketPremiumDTO;
import com.bomwebportal.lts.dto.ItemSetCriteriaDTO;
import com.bomwebportal.lts.dto.ItemSetDetailDTO;
import com.bomwebportal.lts.util.LtsConstant;

public class PremiumSetDetailDAO extends BaseDAO {
	
	private static final String SQL_GET_DEFAULT_ITEM_SET = 
			"select wis.item_set_id, wis.item_set_cd, wis.item_set_type, " +
		    "wis.set_description, wis.max_qty as set_max_qty, wis.min_qty as set_min_qty, " +
			"wis.default_ind, " +
			"wisdl.html as display_html, wisdl.description as display_desc " +
		    "from w_item_set wis, w_item_set_display_lkup wisdl " +
		    "where wis.item_set_id = wisdl.item_set_id " +
		    "and wis.default_ind = :defaultInd " +
		    "and wisdl.locale = :locale " +
		    "and wisdl.display_type = :displayType " +
		    "and item_set_type = :itemSetType " +
		    "and lob = 'LTS' " +
			"and wis.eff_start_date <= sysdate " +
			"and (wis.eff_end_date IS NULL or wis.eff_end_date >= sysdate) " +
			"and (wis.eligible_team IS NULL or UPPER(wis.eligible_team) like UPPER(:areaCode) or UPPER(wis.eligible_team) like UPPER(:teamCode)) " +
			"and (wis.non_eligible_team IS NULL or " +
				"(wis.non_eligible_team IS NOT NULL and " +
					"(UPPER(wis.non_eligible_team) not like UPPER(:areaCode) or :areaCode is NULL) and " +
					"(UPPER(wis.non_eligible_team) not like UPPER(:teamCode) or :teamCode is NULL)" +
				")" +
			") " +
			"";
	
	private static final String SQL_GET_ARPU_CHANGE_PREMIUM = 
			"select premium_qty " +
			" from w_lts_arpu_change_lkup " +
			" where UPPER(to_prod) = :toProd " +
			" and exist_arpu = :existArpu " +
			" and new_arpu = :newArpu ";
	
	
	private static final String SQL_GET_EXTRA_PREMIUM_QTY = 
			"SELECT EXTRA_PREMIUM_QTY " +
			" FROM W_LTS_ARPU_PREMIUM_LKUP " +
			" WHERE TO_PROD = :toProd " +
			" AND :newArpu BETWEEN LOWER_NEW_ARPU AND UPPER_NEW_ARPU " +
			" AND :arpuDifferent BETWEEN LOWER_ADDITIONAL_ARPU AND UPPER_ADDITIONAL_ARPU " +
			" AND ROWNUM = 1"; 
	
	
	private static final String SQL_SELECT_GET_BASKET_PREMIUM_LIST =
			"select distinct wi.id as item_id, widl.description as item_desc, " +
            "RTRIM (XMLAGG (XMLELEMENT (E,XMLATTRIBUTES (wbdl.description|| '<br><br>- ' AS \"Wb\"))ORDER BY wbdl.description ASC).EXTRACT ('./E[not(@Wb = preceding-sibling::E/@Wb)]/@Wb'),'<br><br>- ')as basket_desc " +
            "from w_basket wb, w_basket_item_set_assgn wbisa, " + 
            "w_basket_display_lkup wbdl, w_item_set wis, " + 
	        "w_item_set_assgn wisa, w_item wi, w_item_display_lkup widl "; 
	

	private static final String SQL_GET_RESOURCE_SHORTAGE_IND =
			"select RESOURCE_SHORTAGE_IND " +
			"from bomweb_addr_inventory " +
			"where order_id = ? ";
	
	public String getArpuChangePremiumQty(String toProd, String existArpu, String newArpu) throws DAOException {
		
		MapSqlParameterSource mapSqlSource = new MapSqlParameterSource();
		mapSqlSource.addValue("toProd", toProd.toUpperCase());
		mapSqlSource.addValue("newArpu", newArpu);
		mapSqlSource.addValue("existArpu", existArpu);
		
		try {
			return simpleJdbcTemplate.queryForObject(SQL_GET_ARPU_CHANGE_PREMIUM, String.class, mapSqlSource);
		} catch (EmptyResultDataAccessException erdae) {
			return null;
		} catch (Exception e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
			throw new DAOException(e.getMessage(), e);
		}
		
	}
	
	public String getExtraPremiumQty(String toProd, String newArpu, String arpuDifferent) throws DAOException {
		
		
		MapSqlParameterSource mapSqlSource = new MapSqlParameterSource();
		mapSqlSource.addValue("toProd", toProd);
		mapSqlSource.addValue("newArpu", newArpu);
		mapSqlSource.addValue("arpuDifferent", arpuDifferent);
		
		try {
			return simpleJdbcTemplate.queryForObject(SQL_GET_EXTRA_PREMIUM_QTY, String.class, mapSqlSource);
		} catch (EmptyResultDataAccessException erdae) {
			return null;
		} catch (Exception e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public List<ItemSetDetailDTO> getDefaultItemSetList(ItemSetCriteriaDTO itemSetCriteria) throws DAOException{
		// Default Premium set - default ind = 'Y'
	    ParameterizedRowMapper<ItemSetDetailDTO> itemSetMapper = new ParameterizedRowMapper<ItemSetDetailDTO>() {
			public ItemSetDetailDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				ItemSetDetailDTO defaultSet = new ItemSetDetailDTO();
		     	defaultSet.setItemSetId(rs.getString("ITEM_SET_ID"));
				defaultSet.setItemSetCode(rs.getString("ITEM_SET_CD"));
				defaultSet.setItemSetType(rs.getString("ITEM_SET_TYPE"));
				defaultSet.setItemSetDesc(rs.getString("SET_DESCRIPTION"));
				defaultSet.setMaxQty(rs.getInt("SET_MAX_QTY"));
				defaultSet.setMinQty(rs.getInt("SET_MIN_QTY"));
				defaultSet.setDefaultInd(rs.getString("DEFAULT_IND").equalsIgnoreCase("Y")? true : false);
				defaultSet.setDisplayDesc(rs.getString("DISPLAY_DESC"));
				defaultSet.setDisplayHtml(rs.getString("DISPLAY_HTML"));
				
				return defaultSet;
			}
	    };
	    
		StringBuilder sql = new StringBuilder();
		sql.append(SQL_GET_DEFAULT_ITEM_SET);
		
		List<ItemSetDetailDTO> itemSetList = new ArrayList<ItemSetDetailDTO>();
		MapSqlParameterSource mapSqlSource = new MapSqlParameterSource();
		mapSqlSource.addValue("itemSetType", StringUtils.defaultIfEmpty(itemSetCriteria.getItemSetType(), LtsConstant.ITEM_TYPE_PREM_SET));
		mapSqlSource.addValue("defaultInd", StringUtils.defaultIfEmpty(itemSetCriteria.getDefaultInd(), "N"));
		mapSqlSource.addValue("locale", StringUtils.defaultIfEmpty(itemSetCriteria.getLocale(), LtsConstant.LOCALE_ENG));
		mapSqlSource.addValue("displayType", StringUtils.defaultIfEmpty(itemSetCriteria.getDisplayType(), LtsConstant.DISPLAY_TYPE_ITEM_SELECT));
		mapSqlSource.addValue("areaCode", StringUtils.isEmpty(itemSetCriteria.getAreaCode()) ? "NULL" : "%| " + itemSetCriteria.getAreaCode() + " |%");
		mapSqlSource.addValue("teamCode", StringUtils.isEmpty(itemSetCriteria.getTeamCode()) ? "NULL" : "%| " + itemSetCriteria.getTeamCode() + " |%");
		
		if (StringUtils.isNotBlank(itemSetCriteria.getCustCampaignCd())) {
			sql.append(" AND (wis.CUST_DEDICATED_CAMPAIGN_CD IS NULL OR UPPER(wis.CUST_DEDICATED_CAMPAIGN_CD) LIKE UPPER(:custCampaignCd)) ");
			mapSqlSource.addValue("custCampaignCd", "%" + itemSetCriteria.getCustCampaignCd() + "%");
		}
		else {
			sql.append(" AND wis.CUST_DEDICATED_CAMPAIGN_CD IS NULL ");
		}
			
		if (StringUtils.isNotEmpty(itemSetCriteria.getTpExpiredMonths())) {
			sql.append(" AND ( (:tpExpiredMonths BETWEEN wis.UPPER_TP_EXPIRE_MTH AND wis.LOWER_TP_EXPIRE_MTH) OR (wis.UPPER_TP_EXPIRE_MTH IS NULL AND wis.LOWER_TP_EXPIRE_MTH IS NULL) ) ");
			mapSqlSource.addValue("tpExpiredMonths", itemSetCriteria.getTpExpiredMonths());
		}
		else {
			sql.append(" AND (wis.UPPER_TP_EXPIRE_MTH IS NULL AND wis.LOWER_TP_EXPIRE_MTH IS NULL) ");
		}
		
		if (ArrayUtils.isNotEmpty(itemSetCriteria.getProfilePsefCds())) {
			sql.append(" AND ( ");
			
			for (String profilePsefCd : itemSetCriteria.getProfilePsefCds()) {
				sql.append(" WIS.eligible_subc_psef like '%").append(profilePsefCd).append("%' OR ");	
			}
			sql.append(" WIS.eligible_subc_psef IS NULL ) ");
		}
		else {
			sql.append(" AND WIS.eligible_subc_psef IS NULL ");
		}

		if(StringUtils.isNotBlank(itemSetCriteria.getSrvBoundary())){
			sql.append(" AND (UPPER(WIS.HOUSING_TYPE) in (select UPPER(HOUSING_TYPE) from W_LTS_HOUSING_SRV_BOUNDARY where SERVICE_BOUNDARY = :srvBoundary) ");
			sql.append(" 		OR WIS.HOUSING_TYPE IS NULL) ");
			mapSqlSource.addValue("srvBoundary", itemSetCriteria.getSrvBoundary());
		}
		
		if (StringUtils.isNotBlank(Double.toString(itemSetCriteria.getSrvTenure()))) {
			sql.append(" AND ((:srvTenure BETWEEN WIS.LOWER_SRV_TENURE AND WIS.UPPER_SRV_TENURE) ");
			sql.append(" OR WIS.LOWER_SRV_TENURE IS NULL ");
			sql.append(" OR WIS.UPPER_SRV_TENURE IS NULL) ");
			mapSqlSource.addValue("srvTenure", itemSetCriteria.getSrvTenure());
		}
		
		sql.append(" ORDER BY wis.item_set_id");
		
		try {
			itemSetList = simpleJdbcTemplate.query(sql.toString(), itemSetMapper, mapSqlSource);
			
		} catch (EmptyResultDataAccessException erdae) {
			return null;
		} catch (Exception e) {
			logger.error("Error in getPremiunSetList - get default item set", e);
			throw new DAOException(e.getMessage(), e);
		}
		return itemSetList;
	}
	
	public List<BasketPremiumDTO> getBasketPremiumList(String[] pBasketId, String pLocale) throws DAOException{
		List<BasketPremiumDTO> basketPremiumList = new ArrayList<BasketPremiumDTO>();
		StringBuffer strSqlGetBasketPremiumList = new StringBuffer();
		strSqlGetBasketPremiumList.append(SQL_SELECT_GET_BASKET_PREMIUM_LIST);
		strSqlGetBasketPremiumList.append(" where wb.id in ( ");
		
		for (int i=0; pBasketId != null && i < pBasketId.length; i++){
			//strSqlGetBasketPremiumList.append("'" + pBasketId[i] + "'");
			strSqlGetBasketPremiumList.append("'" + pBasketId[i] + "', (select base_basket_id from w_basket where id = '" + pBasketId[i] + "')");
			if (i != pBasketId.length-1){
				strSqlGetBasketPremiumList.append(", ");
			}
		}
		
		strSqlGetBasketPremiumList.append(" ) and wb.id = wbisa.basket_id " +
				                          " and wb.id = wbdl.basket_id " +
				                          " and wbdl.locale = '" + pLocale + "' " +
				                          " and wbdl.display_type = 'RP_PROMOT' " +
				                          " and wbisa.item_set_id = wis.item_set_id " +
				                          " and wis.default_ind = 'N' " +
				                          " and wis.lob = 'LTS' " +
				                          " and wis.item_set_type = 'PREM-SET' " +
				                          " and wis.item_set_id = wisa.item_set_id " +
				                          " and wisa.item_id = wi.id " +
				                          " and wi.id = widl.item_id " +
				                          " and widl.locale = '" + pLocale + "' " +
				                          " and widl.display_type = 'ITEM_SELECT' " +
				                          " group by wi.id, widl.description "); 
		
	    ParameterizedRowMapper<BasketPremiumDTO> basketPremiumMapper = new ParameterizedRowMapper<BasketPremiumDTO>() {
			public BasketPremiumDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				BasketPremiumDTO premiumList = new BasketPremiumDTO();
				premiumList.setItemId(rs.getString("ITEM_ID"));
				premiumList.setItemDesc(rs.getString("ITEM_DESC"));
				premiumList.setBasketDesc(HtmlUtils.htmlUnescape(rs.getString("BASKET_DESC")));
				return premiumList;
			}
	    };
		
	    try {
	    	basketPremiumList = simpleJdbcTemplate.query(strSqlGetBasketPremiumList.toString(), basketPremiumMapper);
		} catch (EmptyResultDataAccessException erdae) {
			return null;
		} catch (Exception e) {
			logger.error("Error in getBasketPremiumList - ", e);
			throw new DAOException(e.getMessage(), e);
		}
		return basketPremiumList;
	}
	
	
	public String getResourceShortageInd(String orderId) throws DAOException{
		
		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getString("RESOURCE_SHORTAGE_IND");
			}
		};
		
		try {
			return simpleJdbcTemplate.query(SQL_GET_RESOURCE_SHORTAGE_IND, mapper, orderId).toArray(new String[0])[0];
		} catch (EmptyResultDataAccessException erdae) {
			return null;
			//throw new AppRuntimeException(erdae);
		} catch (Exception e) {
			logger.error("Error in getResourceShortageInd - ", e);
			throw new DAOException(e.getMessage(), e);
		}
		
	}
	
}
