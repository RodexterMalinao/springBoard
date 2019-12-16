package com.bomltsportal.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.bomwebportal.dao.BaseDAO;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.lts.dto.ItemDetailDTO;
import com.bomwebportal.lts.util.LtsBackendConstant;

public class ApplicantInfoDAO extends BaseDAO{
	
	private static final String SQL_GET_BASKET_ITEM =
			"select wi.id as item_id, wi.lob as item_lob, " +
	        "wi.type as item_type, widl.description as item_desc, " +
	        "widl.html, widl.html2, widl.locale, widl.image_path, widl.image_detail_path, " +
	        "wip.onetime_amt, wipdl.onetime_amt_txt, " +
	        "wip.recurrent_amt, wipdl.recurrent_amt_txt, " +
	        "wip.mth_to_mth_rate, wipdl.mth_to_mth_rate_txt, " +
	        "wbia.mdo_ind, wbia.item_set_id, " +
	        "widrrv.penalty_amt, wipdl.penalty_amt_txt, " +
	        "widrrv.rebate_amt,  wipdl.rebate_amt_txt " +
            "from w_basket wb, w_basket_item_assgn wbia, w_item wi, " +
            "w_item_pricing wip, w_item_display_lkup widl, " +
            "w_item_dtl_rp_rb_vas widrrv, (select * from w_item_pricing_display_lkup where locale = ?) wipdl, " +
            "w_channel_item_assgn wcia " +
            "where wb.id = ? " +
            "and wb.id = wbia.basket_id " +
            "and wbia.eff_start_date <= sysdate " +
            "and (wbia.eff_end_date is null or wbia.eff_end_date >= sysdate) " +
            "and wbia.item_id = wi.id " +
            "and wi.id = wip.id (+) " +
            "and wcia.item_id = wi.id " +
            "and wcia.channel_id = ? " +
//            "and wcia.eff_start_date <= sysdate " +
//            "and (wcia.eff_end_date is null or wcia.eff_end_date >= sysdate) " +
            "and widl.item_id = wi.id " +  
            "and widl.display_type = ? " +
            "and wi.lob = ? " +
            "and wi.type = ? " +
	        "and widl.locale = ? " +
	        "and wi.id = widrrv.id (+) " +
            "and wi.id = wipdl.item_id (+) " +
            "and wip.eff_start_date <= sysdate " +
            "and wipdl.eff_start_date <= sysdate " +
            "and (wipdl.eff_end_date is null or wipdl.eff_end_date >= sysdate) " +
            "and (wip.eff_end_date is null or wip.eff_end_date >= sysdate) ";
	
	private static final String SQL_GET_TENURE_CODE = 
			"SELECT tenure_cd FROM bomweb_tenure_lkup" +
			" WHERE upper_tenure >= ?" + 
			" AND lower_tenure <= ?";

	private static final String SQL_GET_ALL_PREPAY_ITEM = 
			"SELECT new_pay_mtd_key, prepay_item_id FROM bomweb_tenure_prepay_lkup " + 
			"WHERE tenure_cd = ?" +
			" AND house_type = ?" +
			" AND device_type = ?";

	protected final Log logger = LogFactory.getLog(getClass());
	
	public List<String> getServiceNumbers(int amount) {
		logger.info("RegIdentificationDAO getServiceNumbers");
		ArrayList<String> srvNumList = new ArrayList<String>();
		int currNum = (int) (Math.random() * 100000000);
		for(int i = 0; i < amount; i++){
			srvNumList.add(Integer.toString(currNum));
			currNum++;
		}
		return srvNumList;
	}
	
	private static final String SQL = "SELECT HOLIDAY FROM B_HOLIDAY";
	
	public List<String> getHolidayList() throws DAOException{
		
		List<String> list = new ArrayList<String>();
		
		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
			public String mapRow(ResultSet rs, int rowNum)	
					throws SQLException {
				String s = rs.getString("HOLIDAY");
				return s.split(" ")[0];
			}
		};
		
		try {
			list = simpleJdbcTemplate.query(SQL, mapper);
		} catch (EmptyResultDataAccessException erdae) {
			throw new AppRuntimeException(erdae);
		} catch (Exception e) {
			logger.error("Exception caught in getHolidayList()", e);
			throw new DAOException(e.getMessage(), e);
		}
		return list;
	}
	
	public String getLtsTenureCode(double tenure) throws DAOException{
		List<String> list = new ArrayList<String>();
		
		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
			public String mapRow(ResultSet rs, int rowNum)	
					throws SQLException {
				return rs.getString("TENURE_CD");
			}
		};
		
		try {
			list = simpleJdbcTemplate.query(SQL_GET_TENURE_CODE, mapper, tenure, tenure);
		} catch (EmptyResultDataAccessException erdae) {
			throw new AppRuntimeException(erdae);
		} catch (Exception e) {
			logger.error("Exception caught in getLtsTenureCode(int)", e);
			throw new DAOException(e.getMessage(), e);
		}
		return list.get(0);
		
	}
	
    private ParameterizedRowMapper<ItemDetailDTO> getLtsItemMapper(final boolean novTvInd, final boolean selectDefaultInd, final boolean includeItemSetId) {
    	return new ParameterizedRowMapper<ItemDetailDTO>() {
    		public ItemDetailDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
    			ItemDetailDTO defaultSetItem = new ItemDetailDTO();
    			defaultSetItem.setItemId(rs.getString("ITEM_ID"));
    			defaultSetItem.setItemLob(rs.getString("ITEM_LOB"));
    			defaultSetItem.setItemType(rs.getString("ITEM_TYPE"));
    			defaultSetItem.setItemDesc(rs.getString("ITEM_DESC"));
    			defaultSetItem.setItemDisplayHtml(rs.getString("HTML"));
    			defaultSetItem.setLocale(rs.getString("LOCALE"));
    			defaultSetItem.setOneTimeAmt(rs.getString("ONETIME_AMT"));
    			defaultSetItem.setOneTimeAmtTxt(rs.getString("ONETIME_AMT_TXT"));
    			defaultSetItem.setRecurrentAmt(rs.getString("RECURRENT_AMT"));
    			defaultSetItem.setRecurrentAmtTxt(rs.getString("RECURRENT_AMT_TXT"));
    			defaultSetItem.setMthToMthAmt(rs.getString("MTH_TO_MTH_RATE"));
    			defaultSetItem.setMthToMthAmtTxt(rs.getString("MTH_TO_MTH_RATE_TXT"));
    			defaultSetItem.setPenaltyAmt(rs.getString("PENALTY_AMT"));
    			defaultSetItem.setPenaltyAmtTxt(rs.getString("PENALTY_AMT_TXT"));
    			defaultSetItem.setRebateAmt(rs.getString("REBATE_AMT"));
    			defaultSetItem.setRebateAmtTxt(rs.getString("REBATE_AMT_TXT"));
    			defaultSetItem.setImagePath(rs.getString("IMAGE_PATH"));
    			defaultSetItem.setImageDetailPath(rs.getString("IMAGE_DETAIL_PATH"));
    			defaultSetItem.setUrl(rs.getString("HTML2"));
 
    			String mdoInd = rs.getString("MDO_IND");
    			defaultSetItem.setMdoInd(mdoInd);

    			if (includeItemSetId) {
    				defaultSetItem.setItemSetId(rs.getString("ITEM_SET_ID"));
    			}
    			
    			if (novTvInd) {
    				defaultSetItem.setCredit(rs.getString("CREDIT"));
    			}
    			if (selectDefaultInd) {
    				if (StringUtils.equalsIgnoreCase(LtsBackendConstant.ITEM_MDO_MANDATORY, mdoInd) 
    					|| StringUtils.equalsIgnoreCase(LtsBackendConstant.ITEM_MDO_DEFAULT, mdoInd)){
    					defaultSetItem.setSelected(true);
    				} 
    			}
    			return defaultSetItem;
    		}
        };
    }
    
	public List<ItemDetailDTO> getLtsItem(String pSql, ParameterizedRowMapper<ItemDetailDTO> pMapper, Object... arg) throws DAOException {
	    
	    try{
	    	return simpleJdbcTemplate.query(pSql, pMapper, arg);
	    	
	    } catch (EmptyResultDataAccessException erdae) {
			throw new AppRuntimeException(erdae);
		} catch (Exception e) {
			logger.error("Error in getLtsItem - ", e);
			logger.debug("getLtsItem: " + pSql);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public List<ItemDetailDTO> getLtsBasketItemList(String pBasketId, String pItemType, String pLocale, boolean pSelectDefault, String pChannelId, String pApplDate)throws DAOException {
		StringBuilder sql = new StringBuilder(SQL_GET_BASKET_ITEM);
		
		if (StringUtils.isNotBlank(pApplDate)) {
			sql.append(" and TRUNC(wcia.eff_start_date) <= to_date('" + StringUtils.split(pApplDate, " ")[0] + "', 'dd/MM/yyyy') ");
			sql.append(" and (wcia.eff_end_date is null or TRUNC(wcia.eff_end_date) >= to_date('" + StringUtils.split(pApplDate, " ")[0] + "', 'dd/MM/yyyy') ) ");
		}
		else {
			sql.append(" and TRUNC(wcia.eff_start_date) <= sysdate ");
			sql.append(" and (wcia.eff_end_date is null or wcia.eff_end_date >= sysdate) " );
		}
		
		sql.append(" order by wi.id ");
		return this.getLtsItem(sql.toString(), this.getLtsItemMapper(false, pSelectDefault, true), pLocale, pBasketId, pChannelId, LtsBackendConstant.DISPLAY_TYPE_ITEM_SELECT, LtsBackendConstant.LOB_LTS, pItemType, pLocale);
	}
	
	public List<String[]> getLtsPrePayItem(String tenureCode, String houseType, String deviceType) throws DAOException{
		List<String[]> list = new ArrayList<String[]>();
		
		ParameterizedRowMapper<String[]> mapper = new ParameterizedRowMapper<String[]>() {
			public String[] mapRow(ResultSet rs, int rowNum)	
					throws SQLException {
				String[] item = {rs.getString("prepay_item_id"), rs.getString("new_pay_mtd_key")};
				return item;
			}
		};
		
		try {
			list = simpleJdbcTemplate.query(SQL_GET_ALL_PREPAY_ITEM, mapper, tenureCode, houseType, deviceType);
		} catch (EmptyResultDataAccessException erdae) {
			throw new AppRuntimeException(erdae);
		} catch (Exception e) {
			logger.error("Exception caught in getPrePayItem(String, String)", e);
			throw new DAOException(e.getMessage(), e);
		}
		if (list.size() == 0){
			return null;
		}
		return list;
	}
	
}
