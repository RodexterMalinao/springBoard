package com.bomwebportal.lts.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.bomwebportal.dao.BaseDAO;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.lts.dto.ItemDetailDTO;
import com.bomwebportal.lts.util.LtsProfileConstant;

public class OfferDetailProfileDAO extends BaseDAO {

	private static final String SQL_GET_TP_VAS_ITEM_DETAIL = 
			"select wtvl.item_id as item_id, wtvl.lob as item_lob, " +
			"wtvl.type as item_type, " +
			"widl.description as item_desc, " +
			"widl.html, widl.html2, widl.locale, widl.image_path, widl.image_detail_path, " +
			"wtvl.type, wtvl.description, wtvl.nature, wtvl.tp_catg, " + 
			"wtvl.contract_month, wtvl.paying_month, " + 
			"wtvl.gross_eff_price, wtvl.net_eff_price, " + 
			"wltcl.display_tp_catg_eng, wltcl.display_tp_catg_chi, " +
			"wltcl.is_premium_tp, " +
			"wipdl.recurrent_amt_txt, wipdl.mth_to_mth_rate_txt " +
			"from w_item_display_lkup widl, w_tp_vas_lkup wtvl, " + 
			"w_lts_tp_catg_lkup wltcl, " +
			"w_item_pricing_display_lkup wipdl " +
			"where widl.item_id  = ? " +
			"and wtvl.lob  = ? " + 
			"and widl.item_id = wtvl.item_id " + 
			"and wipdl.item_id(+) = wtvl.item_id " +
			"and widl.display_type = ? " + 
			"and widl.locale = ? " +
			"and wipdl.locale(+) = ? " +
			"and wtvl.tp_catg = wltcl.tp_catg (+) ";
	
	
	public ItemDetailDTO getTpVasItemDetail(String pItemId, final String pLocale) throws DAOException {
		ParameterizedRowMapper<ItemDetailDTO> tpVasItemMapper = new ParameterizedRowMapper<ItemDetailDTO>() {
			public ItemDetailDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				ItemDetailDTO itemDtl = new ItemDetailDTO();
				itemDtl.setItemId(rs.getString("ITEM_ID"));
				itemDtl.setItemLob(rs.getString("ITEM_LOB"));
				itemDtl.setItemType(rs.getString("ITEM_TYPE"));
				itemDtl.setItemDesc(rs.getString("ITEM_DESC"));
				itemDtl.setItemDisplayHtml(rs.getString("HTML"));
				itemDtl.setLocale(rs.getString("LOCALE"));
				if (StringUtils.equalsIgnoreCase(pLocale, LtsProfileConstant.LOCALE_ENG)){ 
					itemDtl.setTpCatg(StringUtils.isNotEmpty(rs.getString("DISPLAY_TP_CATG_ENG")) ? rs.getString("DISPLAY_TP_CATG_ENG") : rs.getString("TP_CATG"));
				} else if (StringUtils.equalsIgnoreCase(pLocale, LtsProfileConstant.LOCALE_CHI)){ 
					itemDtl.setTpCatg(StringUtils.isNotEmpty(rs.getString("DISPLAY_TP_CATG_CHI")) ? rs.getString("DISPLAY_TP_CATG_CHI") : rs.getString("TP_CATG"));
				} else {
					itemDtl.setTpCatg(rs.getString("TP_CATG"));
				}
				itemDtl.setContractMonth(rs.getString("CONTRACT_MONTH"));
				itemDtl.setPayingMonth(rs.getString("PAYING_MONTH"));
				itemDtl.setGrossEffPrice(rs.getString("GROSS_EFF_PRICE"));
				itemDtl.setNetEffPrice(rs.getString("NET_EFF_PRICE"));
				itemDtl.setIsPremiumTp(rs.getString("IS_PREMIUM_TP"));
				itemDtl.setMthToMthAmtTxt(rs.getString("MTH_TO_MTH_RATE_TXT"));
				itemDtl.setRecurrentAmtTxt(rs.getString("RECURRENT_AMT_TXT"));
				itemDtl.setNature(rs.getString("NATURE"));
				return itemDtl;
			}
		};
	    try{
	    	List<ItemDetailDTO> itemList = simpleJdbcTemplate.query(SQL_GET_TP_VAS_ITEM_DETAIL, tpVasItemMapper, pItemId, LtsProfileConstant.LOB_LTS, LtsProfileConstant.DISPLAY_TYPE_ITEM_SELECT, pLocale, pLocale);
	    	return itemList.isEmpty() ? null : itemList.toArray(new ItemDetailDTO[0])[0];
	    } catch (EmptyResultDataAccessException erdae) {
			throw new AppRuntimeException(erdae);
		} catch (Exception e) {
			logger.error("Error in getTpVasItemDetail - ", e);
			logger.debug("getItemAttb: " + SQL_GET_TP_VAS_ITEM_DETAIL);
			throw new DAOException(e.getMessage(), e);
		}
	}
}
