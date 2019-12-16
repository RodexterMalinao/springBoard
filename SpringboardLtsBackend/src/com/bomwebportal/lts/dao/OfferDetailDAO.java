package com.bomwebportal.lts.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.lts.dto.ChannelAttbDTO;
import com.bomwebportal.lts.dto.ChannelDetailDTO;
import com.bomwebportal.lts.dto.ChannelGroupDetailDTO;
import com.bomwebportal.lts.dto.ChannelIconDTO;
import com.bomwebportal.lts.dto.ExclusiveChannelDetailDTO;
import com.bomwebportal.lts.dto.ExclusiveItemDetailDTO;
import com.bomwebportal.lts.dto.ItemAttbDTO;
import com.bomwebportal.lts.dto.ItemAttbInfoDTO;
import com.bomwebportal.lts.dto.ItemCriteriaDTO;
import com.bomwebportal.lts.dto.ItemCriteriaDTO.ItemCriteriaBuilder;
import com.bomwebportal.lts.dto.ItemDetailDTO;
import com.bomwebportal.lts.dto.ItemSetAttbDTO;
import com.bomwebportal.lts.dto.ItemSetCriteriaDTO;
import com.bomwebportal.lts.dto.ItemSetDetailDTO;
import com.bomwebportal.lts.dto.UpgradeEyeInfoDTO;
import com.bomwebportal.lts.util.LtsBackendConstant;
import com.bomwebportal.lts.util.LtsDateFormatHelper;

public class OfferDetailDAO extends OfferDetailProfileDAO {
	
	private static final String SQL_GET_ITEM_ATTB_INFO = 
			"select attb_info_key, attb_value, attb_value_desc " +
			"from w_attb_info_dic " +
			"where attb_info_key = :attbInfoKey";
	
	
	private static final String SQL_GET_SET_ATTB =
			"select attb_cd, attb_desc from w_item_set_attb " +
			"where item_set_id = ? ";
	
	private static final String SQL_GET_ITEM = 
			"select wi.id as item_id, wi.lob as item_lob, " +
	        "wi.type as item_type, widl.description as item_desc, " +
	        "widl.html, widl.html2, widl.locale, widl.image_path, widl.image_detail_path, " +
	        "wip.onetime_amt, wipdl.onetime_amt_txt, " +
	        "wip.recurrent_amt, wipdl.recurrent_amt_txt, " +
	        "wip.mth_to_mth_rate, wipdl.mth_to_mth_rate_txt, " +
	        "wi.mdo_ind, " +
	        "widrrv.penalty_amt, wipdl.penalty_amt_txt, " +
	        "widrrv.rebate_amt,  wipdl.rebate_amt_txt, widtll.af_type, widtll.eligible_doc_type, widtll.program_cd " +
			"from w_item wi, w_item_pricing wip, w_item_display_lkup widl, " +
			"w_item_dtl_rp_rb_vas widrrv, (select * from w_item_pricing_display_lkup where locale = ?) wipdl,  " +
			"w_channel_item_assgn wcia " +
			", w_item_dtl_lts widtll " +
		    "where wi.type = ? " +
		    "and wi.lob = ? " +
		    "and wi.id = wip.id (+) " +
		    "and widl.item_id = wi.id " +
		    "and widl.display_type = ? " +
		    "and widl.locale = ? " +
		    "and wcia.item_id = wi.id " +
		    "and wcia.channel_id = ? " +
//            "and (wcia.eff_end_date is null or wcia.eff_end_date >= sysdate) " +
            "and wi.id = widrrv.id (+) " +
            "and wi.id = wipdl.item_id (+) " +
            "and wipdl.eff_start_date <= sysdate " +
            "and wip.eff_start_date <= sysdate " +
            "and (wipdl.eff_end_date is null or wipdl.eff_end_date >= sysdate) " +
            "and (wip.eff_end_date is null or wip.eff_end_date >= sysdate) " +
            "and wi.id = widtll.item_id (+) ";


	
	private static final String SQL_GET_BASKET_ITEM_DTL_LTS =
		"select wi.id as item_id, wi.lob as item_lob, " +
        "wi.type as item_type, widl.description as item_desc, " +
        "widl.html, widl.html2, widl.locale, widl.image_path, widl.image_detail_path, " +
        "wip.onetime_amt, wipdl.onetime_amt_txt, " +
        "wip.recurrent_amt, wipdl.recurrent_amt_txt, " +
        "wip.mth_to_mth_rate, wipdl.mth_to_mth_rate_txt, " +
        "wbia.mdo_ind, wbia.item_set_id, " +
        "widrrv.penalty_amt, wipdl.penalty_amt_txt, " +
        "widrrv.rebate_amt,  wipdl.rebate_amt_txt, widlts.af_type, widlts.eligible_doc_type, widlts.program_cd " +
        "from w_basket wb, w_basket_item_assgn wbia, w_item wi, " +
        "w_item_pricing wip, w_item_display_lkup widl, " +
        "w_item_dtl_rp_rb_vas widrrv, (select * from w_item_pricing_display_lkup where locale = ?) wipdl, " +
        "w_item_dtl_lts widlts, " +
        "w_channel_item_assgn wcia " +
        "where wb.id = ? " +
        "and wb.id = wbia.basket_id " +
        "and wbia.eff_start_date <= sysdate " +
        "and (wbia.eff_end_date is null or wbia.eff_end_date >= sysdate) " +
        "and wbia.item_id = wi.id " +
        "and wi.id = wip.id (+) " + 
        "and wcia.item_id = wi.id " +
        "and wcia.channel_id = ? " +
//        "and wcia.eff_start_date <= sysdate" +
//        "and (wcia.eff_end_date is null or wcia.eff_end_date >= sysdate) " +        
        "and widl.item_id = wi.id " +  
        "and widl.display_type = ? " +
        "and wi.lob = ? " +
        "and wi.type = ? " +
        "and widl.locale = ? " +
        "and wi.id = widrrv.id (+) " +
        "and wi.id = wipdl.item_id (+) " +
        "and wip.eff_start_date <= sysdate" +
        "and wipdl.eff_start_date <= sysdate" +
        "and (wipdl.eff_end_date is null or wipdl.eff_end_date >= sysdate) " +
        "and (wip.eff_end_date is null or wip.eff_end_date >= sysdate) " +
		"and wi.id = widlts.item_id (+) ";
	
	private static final String SQL_GET_BASKET_ITEM =
			"select wi.id as item_id, wi.lob as item_lob, " +
	        "wi.type as item_type, widl.description as item_desc, " +
	        "widl.html, widl.html2, widl.locale, widl.image_path, widl.image_detail_path, " +
	        "wip.onetime_amt, wipdl.onetime_amt_txt, " +
	        "wip.recurrent_amt, wipdl.recurrent_amt_txt, " +
	        "wip.mth_to_mth_rate, wipdl.mth_to_mth_rate_txt, " +
	        "wbia.mdo_ind, wbia.item_set_id, " +
	        "widrrv.penalty_amt, wipdl.penalty_amt_txt, " +
	        "widrrv.rebate_amt,  wipdl.rebate_amt_txt, widlts.af_type, widlts.eligible_doc_type, widlts.program_cd " +
            "from w_basket wb, w_basket_item_assgn wbia, w_item wi, " +
            "w_item_pricing wip, w_item_display_lkup widl, " +
            "w_item_dtl_rp_rb_vas widrrv, (select * from w_item_pricing_display_lkup where locale = ?) wipdl, " +
            "w_channel_item_assgn wcia, w_item_dtl_lts widlts " +
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
            "and (wip.eff_end_date is null or wip.eff_end_date >= sysdate) " +
            "and wi.id = widlts.item_id (+) ";
	
	private static final String SQL_GET_BASKET_NOWTV_ITEM =
			"select wi.id as item_id, wi.lob as item_lob, " + 
			"wi.type as item_type, widl.description as item_desc, " +
			"widl.html, widl.html2, widl.locale, widl.image_path, widl.image_detail_path, " +
	        "wip.onetime_amt, wipdl.onetime_amt_txt, " +
	        "wip.recurrent_amt, wipdl.recurrent_amt_txt, " +
	        "wip.mth_to_mth_rate, wipdl.mth_to_mth_rate_txt, " +
			"wbia.mdo_ind, widt.credit, " +
	        "widrrv.penalty_amt, wipdl.penalty_amt_txt, " +
	        "widrrv.rebate_amt,  wipdl.rebate_amt_txt, '' as af_type, '' as eligible_doc_type, '' as program_cd " +
		    "from w_basket wb, (select distinct basket_id, item_id, mdo_ind, display_seq, eff_end_date from w_basket_item_assgn) wbia, w_item wi, " +
		    "w_item_pricing wip, w_item_display_lkup widl, " +
			"w_item_dtl_tv widt, " +
            "w_item_dtl_rp_rb_vas widrrv, (select * from w_item_pricing_display_lkup where locale = :locale) wipdl, " +
            "w_channel_item_assgn wcia " +
            //"where wb.id in (?, (select base_basket_id from w_basket where id = ?)) " +
			"where wb.id = :basketId " +
		    "and wb.id = wbia.basket_id " +
		    "and (wbia.eff_end_date is null or wbia.eff_end_date >= sysdate) " +
		    "and wbia.item_id = wi.id " +
		    "and wi.id = wip.id (+) " +
		    "and widl.item_id = wi.id " +  
		    "and widl.display_type = :displayType " +
		    "and widl.locale = :locale " +
		    "and wi.lob = :lob " +
		    "and wi.type = :itemType " +
		    "and wi.id  = widt.item_id (+) " + 
	        "and wi.id = widrrv.id (+) " +
            "and wi.id = wipdl.item_id (+) " +
            "and (wipdl.eff_end_date is null or wipdl.eff_end_date >= sysdate) " +
            "and (wip.eff_end_date is null or wip.eff_end_date >= sysdate) " +
            "and wcia.item_id = wi.id " +
            "and wcia.channel_id = :channelId ";
//            "and wcia.eff_start_date <= sysdate " +
//            "and (wcia.eff_end_date is null or wcia.eff_end_date >= sysdate) "
//            " order by wbia.display_seq ";
	
	
	private static final String SQL_SELECT_GET_ITEM = 
			"select wi.id as item_id, wi.lob as item_lob, " + 
			"wi.type as item_type, widl.description as item_desc, " + 
			"widl.html, widl.html2, widl.locale, widl.image_path, widl.image_detail_path, " + 
	        "wip.onetime_amt, wipdl.onetime_amt_txt, " +
	        "wip.recurrent_amt, wipdl.recurrent_amt_txt, " +
	        "wip.mth_to_mth_rate, wipdl.mth_to_mth_rate_txt, " +
			"wi.mdo_ind, " +
	        "widrrv.penalty_amt, wipdl.penalty_amt_txt, " +
	        "widrrv.rebate_amt,  wipdl.rebate_amt_txt, widlts.af_type, widlts.eligible_doc_type, widlts.program_cd " +
			"from w_item wi, w_item_pricing wip, w_item_display_lkup widl, " +
			"w_item_dtl_rp_rb_vas widrrv, (select * from w_item_pricing_display_lkup where locale = ?) wipdl, w_item_dtl_lts widlts ";
	
	private static final String SQL_GET_CHANNEL_GROUP_DETAIL = 
			"select wtf.form_id, wtcg.channel_group_id, " +
            "wtcg.channel_group_cd, " +
	        "wtdl.description as channel_group_desc, wtdl.html as channel_group_html, " +
	        "wtdl.image_path " + 
            "from w_tv_form wtf, w_tv_channel_group wtcg, w_tv_display_lkup wtdl " + 
            "where wtf.form_type = ? " +
            "and wtf.lob = ? " +
            "and wtf.eye_module = ? " +
            "and wtcg.form_id = wtf.form_id " +
            "and wtdl.id = wtcg.channel_group_id " +
            "and wtdl.type = 'GROUP' " +
            "and wtdl.locale = ? " +
            " order by wtcg.display_seq ";
	
	private static final String SQL_GET_CHANNEL_DETAIL =
			"select wtc.channel_id, wtc.channel_cd, " +
		    "wtc.credit, wtc.tv_type, wtc.is_adult_channel, wtc.mdo_ind, " +
			"wtdl.html as channel_html " + 
		    "from w_tv_channel wtc, w_tv_display_lkup wtdl " +
		    "where wtc.channel_group_id = ? " +
		    "and (wtc.eff_end_date is null or wtc.eff_end_date >= sysdate) " +
		    "and wtdl.id = wtc.channel_id " +
		    "and wtdl.type = 'CHANNEL' " +
		    "and wtdl.locale = ? " +
		    " order by wtc.display_seq ";
	
	private static final String SQL_GET_ITEM_ATTB =
	        "select wal.attb_id, wal.description as attb_desc, " + 
	        "wal.input_method, wal.input_format, " + 
	        "wal.min_length, wal.max_length, " + 
	        "wal.default_value, wal.attb_info_key, " + 
	        "wal.visual_ind, wpaa.compt_id, " +
	        "wioa.offer_id, wiopa.product_id " + 
	        "from w_item_offer_product_assgn wiopa, " + 
	        "w_product_attb_assgn wpaa, w_attb_lkup wal, " +
	        "w_item_offer_assgn wioa " +
	        "where wioa.item_id  = ? " +
	        "and wioa.item_id = wiopa.item_id " +
	        "and wiopa.product_id = wpaa.product_id " + 
	        "and wpaa.attb_id = wal.attb_id " +
	        "and wioa.item_offer_seq = wiopa.item_offer_seq ";
	
	private static final String SQL_GET_CONTRACT_MTH =
			"select new_contract_mth, extend_contract_period " +
			"from w_lts_contract_mth_lkup " +
			"where lts_srv_type = ? " +
		    "and tp_catg = 'PREMIUM' " +
			"and ? between lower_remain_mth and upper_remain_mth " +
			"and rownum = 1 ";
	
	private static final String SQL_GET_UPGRADE_EYE_INFO = 
			"select new_contract_mth, extend_contract_period, " +
			"admin_charge_item_id, admin_charge, return_device_ind, " +
			"cancel_charge_item_id, cancel_charge, warning_msg  " +
			"from w_lts_contract_mth_lkup " +
			"where lts_srv_type = ? " +
			"and UPPER(exist_srv_type) = UPPER(?) " +
			"and ? between lower_remain_mth and upper_remain_mth " +
			"and from_staff_plan_ind = ? " +
			"and to_staff_plan_ind = ? " +
			"and tp_catg = UPPER(?) " +
			"and rownum = 1 ";
	
	
	private static final String SQL_GET_NOWTV_MIRR_ITEM_LIST = 
			"select wi.id as item_id, wi.lob as item_lob, " +
	        "wi.type as item_type, widl.description as item_desc, " +
	        "widl.html, widl.html2, widl.locale, widl.image_path, widl.image_detail_path, " +
	        "wip.onetime_amt, wipdl.onetime_amt_txt, " +
	        "wip.recurrent_amt, wipdl.recurrent_amt_txt, " +
	        "wip.mth_to_mth_rate, wipdl.mth_to_mth_rate_txt, " +
	        "wi.mdo_ind, " +
	        "widrrv.penalty_amt, wipdl.penalty_amt_txt, " +
	        "widrrv.rebate_amt,  wipdl.rebate_amt_txt " +
			"from w_item wi, w_item_pricing wip, w_item_display_lkup widl, " +
			"w_item_dtl_rp_rb_vas widrrv, (select * from w_item_pricing_display_lkup where locale = ?) wipdl, " +
			"w_lts_arpu_plan_lkup wlapl, " +
			"w_channel_item_assgn wcia " +
		    "where wi.type = ? " +
		    "and wi.lob = ? " +
		    "and wi.id = wcia.item_id " +
		    "and wcia.channel_id = ?  " +
		    "and wi.id = wip.id (+) " +
		    "and widl.item_id = wi.id " +
		    "and widl.display_type = ? " +
		    "and widl.locale = ? " +
            "and wi.id = widrrv.id (+) " +
            "and wi.id = wipdl.item_id (+) " +
            "and (wipdl.eff_end_date is null or wipdl.eff_end_date >= sysdate) " +
            "and (wip.eff_end_date is null or wip.eff_end_date >= sysdate) " +
            "and wlapl.lts_srv_type = ? " +
			"and wi.id = wlapl.item_id " +
			"and ? between wlapl.lower_arpu and wlapl.upper_arpu " +
			" order by wi.id ";
	
	private static final String SQL_GET_CHANNEL_DESC = 
		"select tvc.channel_cd, tvdl.html " +
		"from w_tv_display_lkup tvdl,w_tv_channel tvc " +  
		"where tvdl.id = tvc.channel_id " +
		"and tvdl.id = ? and tvdl.locale = ?";
	
	private static final String SQL_GET_ITEM_CONTRACT_PERIOD = 
			"select contract_period " +
			"from w_item_dtl_rp_rb_vas " +
			"where id = ?";
	
	private static final String SQL_GET_CHANNEL_ATTB =
	        "select wcaa.attb_id, wcaa.display_seq, wcaa.display_sub_seq, " +
	        "wal.description, wal.input_method, wal.input_format, " +
	        "wal.min_length, wal.max_length, wal.default_value, " +
	        "wal.attb_info_key, wal.visual_ind " +
	        "from w_tv_channel_attb_assgn wcaa, W_ATTB_LKUP wal " +
	        "where wcaa.channel_id = ? " +
	        "and wal.attb_id = wcaa.attb_id " +
	        "and wal.locale = ? " +
	        " order by wcaa.display_seq, wcaa.display_sub_seq ";
	
	private static final String SQL_GET_CHANNEL_ICON = 
		"select wtci.id as channel_id, wtci.type, wtci.description, " +
		"wtci.image_id, wtcia.display_seq, wtci.html " +
		"from w_tv_channel_icon_assgn wtcia, " +
		"w_tv_channel_icon wtci " +
		"where wtcia.channel_id = ? " +
		"and wtcia.icon_id = wtci.id " +
		"and wtci.locale = ? " +
		"and wtci.type = 'LTS_ICON' " +
		" order by wtcia.display_seq";
	
	private static final String SQL_GET_ITEM_ATTB_BY_ITEM_ATTB_ASSIGN = 
			"select wal.attb_id, " +
			"wal.description as attb_desc, wal.input_method, wal.input_format, " +
			"wal.min_length, wal.max_length, wal.default_value, " +
			"wal.attb_info_key, wal.visual_ind " +
			"from w_item_attb_assgn wiaa, W_ATTB_LKUP wal " +
			"where wiaa.item_id = ? " +
			"and wiaa.attb_id = wal.attb_id " +
			" order by wal.attb_id ";
	
	private static final String SQL_GET_SERVICE_PLAN_CD = 
			"select exist_tp_cd from w_lts_vas_mapping" +
			" where exist_item_id = :itemId";
	
	private static final String SQL_GET_VAS_PLAN_CD = 
			"select exist_tp_cd from w_lts_vas_lkup" +
			" where exist_item_id = :itemId";	
	
	private static final String SQL_GET_ITEM_OFFER_CD = 
		"select offer_cd from w_item_offer_assgn" +
		" where item_id = :itemId";	

	private static final String SQL_GET_ITEM_PSEF_CD = 
		"select substr(offer_cd, 3, 4) from w_item_offer_assgn" +
		" where item_id = :itemId";	

	private static final String SQL_GET_ITEM_OFFER_ID = 
		" select offer_id " +
		"  from w_item_offer_assgn " +
		"  where item_id = :itemId";
	
	private static final String SQL_GET_BUNDLETV_MAP =
			"select pack_psef_cd, tv_channel_num from W_EYE_BUNDLE_TV_CODE_LKUP" + 
	        " WHERE pack_psef_cd IS NOT NULL";
	
	private static final String SQL_GET_NOWTV_SET =
		"select distinct psef_cd from W_EYE_BUNDLE_TV_CODE_LKUP";
	
	private static final String SQL_GET_ITEM_DISPLAY_DESC = 
			"SELECT description FROM w_item_display_lkup " +
			" WHERE Item_iD = ? " +
			" AND locale = ? ";
	
	public List<String> getItemOfferIdList(String itemId) throws DAOException {
		try {
			
			MapSqlParameterSource paramSource = new MapSqlParameterSource();
			paramSource.addValue("itemId", itemId);
			
			return this.simpleJdbcTemplate.query(SQL_GET_ITEM_OFFER_ID, new ParameterizedRowMapper<String>() {
				public String mapRow(ResultSet rs, int rowNum) throws SQLException {
					return rs.getString("offer_id");
				}
			}, paramSource);
			
		} catch (EmptyResultDataAccessException erdae) {
			throw new AppRuntimeException(erdae);
		} catch (Exception e) {
			logger.error("Error in getItemOfferIdList()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<String> getItemOfferCodes(String itemId) throws DAOException {
		
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue("itemId", itemId);
		
		try {
			return 	simpleJdbcTemplate.getNamedParameterJdbcOperations().queryForList(SQL_GET_ITEM_OFFER_CD, paramSource, String.class);
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException in getItemOfferCodes()");
			return null;
		} catch (Exception e) {
			logger.info("Exception caught in getItemOfferCodes():", e);
			throw new DAOException(e.getMessage(), e);
		}
	}

	@SuppressWarnings("unchecked")
	public List<String> getItemPsefCodes(String itemId) throws DAOException {
		
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue("itemId", itemId);
		
		try {
			return 	simpleJdbcTemplate.getNamedParameterJdbcOperations().queryForList(SQL_GET_ITEM_PSEF_CD, paramSource, String.class);
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException in getItemPsefCodes()");
			return null;
		} catch (Exception e) {
			logger.info("Exception caught in getItemPsefCodes():", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public String getServicePlanCode(String itemId) throws DAOException {
		
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue("itemId", itemId);
		
		try {
			return (String)simpleJdbcTemplate.getNamedParameterJdbcOperations().queryForObject(SQL_GET_SERVICE_PLAN_CD, paramSource, String.class);
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException in getServicePlanCode()");
			return null;
		} catch (Exception e) {
			logger.info("Exception caught in getServicePlanCode():", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public String getVasPlanCode(String itemId) throws DAOException {
		
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue("itemId", itemId);
		
		try {
			return (String)simpleJdbcTemplate.getNamedParameterJdbcOperations().queryForObject(SQL_GET_VAS_PLAN_CD, paramSource, String.class);
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException in getVasPlanCode()");
			return null;
		} catch (Exception e) {
			logger.info("Exception caught in getVasPlanCode():", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<String> getBasketItemSetId(String basketId, String itemId) throws DAOException {
		
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT item_set_id")
			.append(" FROM w_basket_item_assgn")
			.append(" WHERE basket_id = :basketId")
			.append(" AND item_id = :itemId");
		
		paramSource.addValue("basketId", basketId);
		paramSource.addValue("itemId", itemId);
		
		try {
			if (logger.isDebugEnabled()) {
				logger.debug("getBasketItemSetId() Sql: " + sql.toString());	
			}
			return simpleJdbcTemplate.getNamedParameterJdbcOperations().queryForList(sql.toString(), paramSource, String.class);

		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException in getUpperApru()");
			return null;
		} catch (Exception e) {
			logger.info("Exception caught in getUpperApru():", e);
			throw new DAOException(e.getMessage(), e);
		}
		
	}
	
	
	public String getUpperApru(String itemId) throws DAOException{
		
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		
		StringBuilder sql= new StringBuilder();
		
		sql.append("SELECT upper_arpu") 
		 	.append(" FROM w_lts_arpu_plan_lkup") 
			.append(" WHERE item_id = :itemId");
		
		paramSource.addValue("itemId", itemId);
		
		try {
			if (logger.isDebugEnabled()) {
				logger.debug("getUpperApru() Sql: " + sql.toString());	
			}
			return (String)simpleJdbcTemplate.getNamedParameterJdbcOperations().queryForObject(sql.toString(), paramSource, String.class);

		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException in getUpperApru()");
			return null;
		} catch (Exception e) {
			logger.info("Exception caught in getUpperApru():", e);
			throw new DAOException(e.getMessage(), e);
		}
		
	}
	
    private ParameterizedRowMapper<ItemDetailDTO> getItemMapper(final boolean novTvInd, final boolean selectDefaultInd, final boolean includeItemSetId) {
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
    			defaultSetItem.setAfType(rs.getString("AF_TYPE"));
    			defaultSetItem.setEligibleDocType(rs.getString("ELIGIBLE_DOC_TYPE"));
    			defaultSetItem.setProgramCd(rs.getString("PROGRAM_CD"));
 
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
	
	public List<ItemSetAttbDTO> getSetAttbList(String pItemSetId) throws DAOException{

	    ParameterizedRowMapper<ItemSetAttbDTO> attbMapper = new ParameterizedRowMapper<ItemSetAttbDTO>() {
	    	public ItemSetAttbDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
	    		ItemSetAttbDTO defaultSetAttb = new ItemSetAttbDTO();
	    		defaultSetAttb.setAttbCode(rs.getString("ATTB_CD"));
	    		defaultSetAttb.setAttbDesc(rs.getString("ATTB_DESC"));
	    		return defaultSetAttb;
	    	}
	    };
	    
	    try{
	    	return simpleJdbcTemplate.query(SQL_GET_SET_ATTB, attbMapper, pItemSetId);
	    } catch (EmptyResultDataAccessException erdae) {
			return null;
		} catch (Exception e) {
			logger.error("Error in getSetAttbList - ", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public List<ItemDetailDTO> getSetItemList(String pItemSetId, String pLocale, boolean pSelectDefault, String pChannelId, String pApplDate, String pEligibleDocType) throws DAOException{
		return this.getSetItemList(pItemSetId, pLocale, pSelectDefault, pChannelId, LtsBackendConstant.DISPLAY_TYPE_ITEM_SELECT, null, pApplDate, pEligibleDocType, null);
	}
	
	public List<ItemDetailDTO> getSetItemList(String pItemSetId, String pLocale, boolean pSelectDefault, String pChannelId, String pDisplayType, String pApplDate, String pEligibleDocType) throws DAOException{
		return this.getSetItemList(pItemSetId, pLocale, pSelectDefault, pChannelId, pDisplayType, null, pApplDate, pEligibleDocType, null);
	}
	
	public List<ItemDetailDTO> getSetItemList(String pItemSetId, String pLocale, boolean pSelectDefault, String pChannelId, String pDisplayType, String pBaseContractPeriod, String pApplDate, String pEligibleDocType, String pOsType) throws DAOException{

		String SQL_GET_SET_ITEM_DETAIL =
			"select wi.id as item_id, wi.lob as item_lob, " +
	        "wi.type as item_type, widl.description as item_desc, " +
	        "widl.html, widl.html2, widl.locale, widl.image_path, widl.image_detail_path, " +
	        "wip.onetime_amt, wipdl.onetime_amt_txt, " +
	        "wip.recurrent_amt, wipdl.recurrent_amt_txt, " +
	        "wip.mth_to_mth_rate, wipdl.mth_to_mth_rate_txt, " +
	        "wisa.mdo_ind, widt.credit, " +
	        "widrrv.penalty_amt, wipdl.penalty_amt_txt, " +
	        "widrrv.rebate_amt,  wipdl.rebate_amt_txt, widlts.af_type, widlts.eligible_doc_type, widlts.program_cd " +
            "from w_item_set_assgn wisa, w_item wi, " + 
            "w_item_pricing wip, w_item_display_lkup widl, " +  
            "w_item_dtl_rp_rb_vas widrrv, (select * from w_item_pricing_display_lkup where locale = ?) wipdl, " +
            "w_item_dtl_tv widt, " +
            "w_channel_item_assgn wcia, " +
            "w_item_dtl_lts widlts " +
            "where wisa.item_set_id = ? " +
            "and wisa.item_id = wi.id " +
            "and wi.id = wip.id (+) " +
            "and wi.lob = ? " +
            "and wisa.item_id = widl.item_id " +
            "and wisa.eff_start_date <= sysdate " +
            "and (wisa.eff_end_date is null or wisa.eff_end_date >= sysdate) " +
            "and wcia.item_id = wi.id " +
            "and wcia.channel_id = ? " +
//            "and wcia.eff_start_date <= sysdate " +
//            "and (wcia.eff_end_date is null or wcia.eff_end_date >= sysdate) " +
            "and widl.display_type = ? " +
            "and widl.locale = ? " +
            "and wi.id = widrrv.id (+) " +
            "and wi.id = wipdl.item_id (+) " +
            "and wi.id  = widt.item_id (+) " +
            "and wi.id = widlts.item_id (+) ";
		
		StringBuffer sql = new StringBuffer(SQL_GET_SET_ITEM_DETAIL);
		
		if (StringUtils.isNotBlank(pApplDate)) {
			sql.append(" and TRUNC(wip.eff_start_date) <= to_date('" + StringUtils.split(pApplDate, " ")[0] + "', 'dd/MM/yyyy') ");
			sql.append(" and (wip.eff_end_date is null or TRUNC(wip.eff_end_date) >= to_date('" + StringUtils.split(pApplDate, " ")[0] + "', 'dd/MM/yyyy') ) ");
			
			sql.append(" and TRUNC(wipdl.eff_start_date) <= to_date('" + StringUtils.split(pApplDate, " ")[0] + "', 'dd/MM/yyyy') ");
			sql.append(" and (wipdl.eff_end_date is null or TRUNC(wipdl.eff_end_date) >= to_date('" + StringUtils.split(pApplDate, " ")[0] + "', 'dd/MM/yyyy') ) ");
			
			sql.append(" and TRUNC(wcia.eff_start_date) <= to_date('" + StringUtils.split(pApplDate, " ")[0] + "', 'dd/MM/yyyy') ");
			sql.append(" and (wcia.eff_end_date is null or TRUNC(wcia.eff_end_date) >= to_date('" + StringUtils.split(pApplDate, " ")[0] + "', 'dd/MM/yyyy') ) ");
		}
		else {
			sql.append("and TRUNC(wip.eff_start_date) <= TRUNC(sysdate) ");
			sql.append("and (wip.eff_end_date is null or TRUNC(wip.eff_end_date) >= TRUNC(sysdate)) ");
			
			sql.append("and TRUNC(wipdl.eff_start_date) <= TRUNC(sysdate) ");
			sql.append("and (wipdl.eff_end_date is null or TRUNC(wipdl.eff_end_date) >= TRUNC(sysdate)) ");
			
			sql.append(" and TRUNC(wcia.eff_start_date) <= TRUNC(sysdate) ");
			sql.append("and (wcia.eff_end_date is null or TRUNC(wcia.eff_end_date) >= TRUNC(sysdate)) ");
		}
		
		if (StringUtils.isNotEmpty(pBaseContractPeriod)) {
			sql.append("and widrrv.contract_period = '").append(pBaseContractPeriod).append("' ");
		}
		
		if (StringUtils.isNotEmpty(pEligibleDocType)) {
			sql.append("and (widlts.eligible_doc_type like '%" + pEligibleDocType + "%'");
			sql.append(" or widlts.eligible_doc_type is null)");
		}
		
		if (StringUtils.isNotBlank(pOsType)){
				sql.append(" and (widlts.os_type in ");
				sql.append(" ('" + pOsType + "', '" + LtsBackendConstant.OS_TYPE_ALL + "') ");
				sql.append(" or widlts.os_type is null) ");
		}
		
		sql.append(" order by wi.type, wip.mth_to_mth_rate, widrrv.penalty_amt, wi.id desc");
		
		List<ItemDetailDTO> itemList = getItem(sql.toString(), this.getItemMapper(true, pSelectDefault, false),pLocale, pItemSetId, LtsBackendConstant.LOB_LTS, pChannelId, pDisplayType, pLocale);
		
		if (itemList != null && !itemList.isEmpty()) {
			for (ItemDetailDTO item : itemList) {
				List<ItemAttbDTO> itemAttbList = getItemAttb(item.getItemId());
				if (itemAttbList != null && !itemAttbList.isEmpty() ) {
					item.setItemAttbs(itemAttbList.toArray(new ItemAttbDTO[itemAttbList.size()]));
				}
			}
		}
		return itemList;
	}
	
	
	public ItemSetDetailDTO getItemSetDetail(ItemSetCriteriaDTO itemSetCriteria) throws DAOException {
		
	    try{

	    	MapSqlParameterSource paramSource = new MapSqlParameterSource();
	    	StringBuffer getItemSetListSql = new StringBuffer();
			getItemSetListSql.append("select wis.item_set_id, wis.item_set_type, ");
			getItemSetListSql.append("wisdl.html as display_html, wisdl.description as display_desc , ");
			getItemSetListSql.append("wis.item_set_cd, wis.default_ind, wis.max_qty, wis.min_qty ");
			getItemSetListSql.append("from w_item_set wis, w_item_set_display_lkup wisdl ");
			getItemSetListSql.append("where wis.item_set_id = :itemSetId ");
			getItemSetListSql.append("and wis.item_set_id = wisdl.item_set_id ");
			getItemSetListSql.append("and wisdl.locale = :locale ");
			getItemSetListSql.append("and wisdl.display_type = :displayType ");


						
			paramSource.addValue("itemSetId", itemSetCriteria.getItemSetId());
			paramSource.addValue("locale", StringUtils.defaultIfEmpty(itemSetCriteria.getLocale(), LtsBackendConstant.LOCALE_ENG));
			paramSource.addValue("displayType", StringUtils.defaultIfEmpty(itemSetCriteria.getDisplayType(), LtsBackendConstant.DISPLAY_TYPE_ITEM_SELECT));
			
			//BOM2017135
			if(StringUtils.isNotBlank(itemSetCriteria.getLtsHousingType())){
				getItemSetListSql.append(" AND (wis.LTS_HOUSING_CAT is null OR UPPER(wis.LTS_HOUSING_CAT) like UPPER(:ltsHousingType)) ");
				paramSource.addValue("ltsHousingType", "%|" + itemSetCriteria.getLtsHousingType() + "|%");
				
			}else if(StringUtils.isNotBlank(itemSetCriteria.getHousingType())){
				getItemSetListSql.append(" AND (wis.ELIGIBLE_HOUSING_TYPE is null OR UPPER(wis.ELIGIBLE_HOUSING_TYPE) like UPPER(:housingType)) ");
				paramSource.addValue("housingType", "%" + itemSetCriteria.getHousingType() + "%");
			}else{
				getItemSetListSql.append(" AND (wis.ELIGIBLE_HOUSING_TYPE is null AND wis.LTS_HOUSING_CAT) ");
			}
			
			
			if(StringUtils.isNotBlank(itemSetCriteria.getSrvBoundary())){
				getItemSetListSql.append(" AND (UPPER(WIS.HOUSING_TYPE) in (select UPPER(HOUSING_TYPE) from W_LTS_HOUSING_SRV_BOUNDARY where SERVICE_BOUNDARY = :srvBoundary) ");
				getItemSetListSql.append(" 		OR WIS.HOUSING_TYPE IS NULL) ");
				paramSource.addValue("srvBoundary", itemSetCriteria.getSrvBoundary());
			}
			
	    	List<ItemSetDetailDTO> itemSetDetailList = simpleJdbcTemplate.query(getItemSetListSql.toString(), getItemSetRowMapper(false), paramSource);
	    	
	    	if (itemSetDetailList == null || itemSetDetailList.isEmpty()) {
	    		return null;
	    	}
	    	
			List<ItemDetailDTO> itemDetailList = this.getSetItemList(
					itemSetCriteria.getItemSetId(),
					itemSetCriteria.getLocale(),
					itemSetCriteria.isSelectDefault(),
					itemSetCriteria.getChannelId(),
					itemSetCriteria.getDisplayType(),
					itemSetCriteria.getApplnDate(), null);
	    	
	    	if (itemDetailList == null || itemDetailList.isEmpty()) {
	    		return null;
	    	}
	    	
	    	itemSetDetailList.get(0).setItemDetails(itemDetailList.toArray(new ItemDetailDTO[itemDetailList.size()]));
	    	
	    	List<ItemSetAttbDTO> itemSetAttbList = this.getSetAttbList(itemSetCriteria.getItemSetId());
	    	
	    	if (itemSetAttbList != null && !itemSetAttbList.isEmpty()) {
	    		itemSetDetailList.get(0).setItemSetAttbs(itemSetAttbList.toArray(new ItemSetAttbDTO[itemSetAttbList.size()]));
	    	}
	    	
	    	return itemSetDetailList.get(0);
	    } catch (EmptyResultDataAccessException erdae) {
	    	return null;
		} catch (Exception e) {
			logger.error("Error in getSetAttbList - ", e);
			throw new DAOException(e.getMessage(), e);
		}
		
	}
	
	private ParameterizedRowMapper<ItemSetDetailDTO> getItemSetRowMapper(final boolean isBasketItemSet) {
	    
		return new ParameterizedRowMapper<ItemSetDetailDTO>() {
			public ItemSetDetailDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				ItemSetDetailDTO basketSet = new ItemSetDetailDTO();
				basketSet.setItemSetId(rs.getString("ITEM_SET_ID"));
				basketSet.setItemSetCode(rs.getString("ITEM_SET_CD"));
				basketSet.setItemSetType(rs.getString("ITEM_SET_TYPE"));
				basketSet.setItemSetDesc(rs.getString("SET_DESCRIPTION"));
				basketSet.setDefaultInd(rs.getString("DEFAULT_IND").equalsIgnoreCase("Y")? true : false);
				basketSet.setDisplayDesc(rs.getString("DISPLAY_DESC"));
				basketSet.setDisplayHtml(rs.getString("DISPLAY_HTML"));
				
				String basketMaxQty = isBasketItemSet ? rs.getString("BASKET_MAX_QTY") : null;
				String setMaxQty = rs.getString("MAX_QTY");
				basketSet.setMaxQty(Integer.parseInt(basketMaxQty != null ? StringUtils.defaultIfEmpty(basketMaxQty, "0") : StringUtils.defaultIfEmpty(setMaxQty, "0")));

				String basketMinQty = isBasketItemSet ? rs.getString("BASKET_MIN_QTY") : null;
				String setMinQty = rs.getString("MIN_QTY");
				basketSet.setMinQty(Integer.parseInt(basketMinQty != null ? StringUtils.defaultIfEmpty(basketMinQty, "0") : StringUtils.defaultIfEmpty(setMinQty, "0")));
				
				return basketSet;
			}
	    };
	    
	}
	
	//TODO BOM2019014 Item set not belong to basket by Type
	public List<ItemSetDetailDTO> getItemSetListByType(ItemSetCriteriaDTO itemSetCriteria) throws DAOException {
		List<ItemSetDetailDTO> itemSetList = new ArrayList<ItemSetDetailDTO>();
		List<ItemSetDetailDTO> rtnItemSetList = new ArrayList<ItemSetDetailDTO>();
		List<ItemDetailDTO> itemList = new ArrayList<ItemDetailDTO>();
		List<ItemSetAttbDTO> attbList = new ArrayList<ItemSetAttbDTO>();
		
		try {
			StringBuilder sql = new StringBuilder();
			
			sql.append(" SELECT WIS.ITEM_SET_ID, WIS.ITEM_SET_CD, WIS.ITEM_SET_TYPE, WIS.SET_DESCRIPTION, ");
			sql.append(" WIS.MAX_QTY, WIS.MIN_QTY, WIS.DEFAULT_IND,  ");
			sql.append(" wisdl.html as display_html, wisdl.description as display_desc ");
			sql.append(" FROM W_ITEM_SET WIS, W_ITEM_SET_DISPLAY_LKUP WISDL ");
			sql.append(" WHERE WIS.ITEM_SET_ID = WISDL.ITEM_SET_ID ");
			sql.append(" AND WIS.ITEM_SET_TYPE = :itemSetType ");
			sql.append(" AND WISDL.LOCALE = :locale ");
			sql.append(" AND wisdl.display_type = :displayType ");
			sql.append(" AND wis.lob = 'LTS' ");
			sql.append(" ORDER BY wis.item_set_id");
			
			MapSqlParameterSource parameterSource = new MapSqlParameterSource();
			parameterSource.addValue("itemSetType", itemSetCriteria.getItemSetType());
			parameterSource.addValue("locale", StringUtils.defaultIfEmpty(itemSetCriteria.getLocale(), LtsBackendConstant.LOCALE_ENG));
			parameterSource.addValue("displayType", StringUtils.defaultIfEmpty(itemSetCriteria.getDisplayType(), LtsBackendConstant.DISPLAY_TYPE_ITEM_SELECT));
			
			itemSetList = simpleJdbcTemplate.query(sql.toString(), getItemSetRowMapper(false), parameterSource);
			
			for (int i = 0; itemSetList != null && i < itemSetList.size(); i++) {
				
				itemList = this.getSetItemList(itemSetList.get(i).getItemSetId(), itemSetCriteria.getLocale(), itemSetCriteria.isSelectDefault(), itemSetCriteria.getChannelId(), itemSetCriteria.getDisplayType(), itemSetCriteria.getBaseContractPeriod(), itemSetCriteria.getApplnDate(), itemSetCriteria.getEligibleDocType(), itemSetCriteria.getOsType());
				
				if (itemList == null || itemList.isEmpty()) {
					continue;
				}
	
				itemSetList.get(i).setItemDetails(itemList.toArray(new ItemDetailDTO[0]));
	
				attbList = this.getSetAttbList(itemSetList.get(i).getItemSetId());
				itemSetList.get(i).setItemSetAttbs(attbList.toArray(new ItemSetAttbDTO[0]));
				rtnItemSetList.add(itemSetList.get(i));
			}
			
	    } catch (EmptyResultDataAccessException erdae) {
			throw new AppRuntimeException(erdae);
		} catch (Exception e) {
			logger.error("Error in getBasketItemSetList - get basket item set", e);
			throw new DAOException(e.getMessage(), e);
		}
		return rtnItemSetList;
	}
	
	// Item set belong to basket
	public List<ItemSetDetailDTO> getBasketItemSetList(ItemSetCriteriaDTO itemSetCriteria) throws DAOException {
	    List<ItemSetDetailDTO> itemSetList = new ArrayList<ItemSetDetailDTO>();
	    List<ItemSetDetailDTO> rtnItemSetList = new ArrayList<ItemSetDetailDTO>();
		List<ItemDetailDTO> itemList = new ArrayList<ItemDetailDTO>();
		List<ItemSetAttbDTO> attbList = new ArrayList<ItemSetAttbDTO>();
	    
	    try {
			StringBuilder sql = new StringBuilder();
			
			sql.append(" SELECT wb.id as basket_id, wb.description as basket_desc, ");
			sql.append(" wbisa.max_qty as basket_max_qty, wbisa.min_qty as basket_min_qty, ");
			sql.append(" wis.item_set_id, wis.item_set_cd, wis.item_set_type, ");
			sql.append(" wis.set_description, wis.max_qty, wis.min_qty, ");
			sql.append(" wis.default_ind, wisdl.html as display_html, wisdl.description as display_desc  ");
			sql.append(" FROM w_basket wb, ");
			sql.append(" w_basket_item_set_assgn wbisa, ");
			sql.append(" w_item_set wis, w_item_set_display_lkup wisdl ");
			sql.append(" WHERE wb.id in (:basketId, (select base_basket_id from w_basket where id = :basketId)) ");
			sql.append(" AND wb.id = wbisa.basket_id ");
			sql.append(" AND wis.eligible_item_type like :eligibleItemType ");
			sql.append(" AND wbisa.item_set_id = wis.item_set_id ");
			sql.append(" AND wis.default_ind = 'N' ");
			sql.append(" AND wis.lob = 'LTS' ");
			sql.append(" AND wis.item_set_type = :itemSetType ");
			sql.append(" AND ( (wis.lower_exist_net_price is null and wis.upper_exist_net_price is null) ");
			sql.append("      or (:profileArpu between wis.lower_exist_net_price and wis.upper_exist_net_price) ) ");
			sql.append(" AND ( (wis.lower_additional_charge is null and wis.upper_additional_charge is null) ");
			sql.append("      or (:additionalFee between wis.lower_additional_charge and wis.upper_additional_charge) ) ");
			sql.append(" AND wisdl.item_set_id = wis.item_set_id ");
			sql.append(" AND wisdl.locale = :locale ");
			sql.append(" AND wisdl.display_type = :displayType ");
			sql.append(" AND TRUNC(wbisa.eff_start_date) <= TO_DATE(:appDate, 'dd/MM/yyyy') ");
			sql.append(" AND (wbisa.eff_end_date is null or TRUNC(wbisa.eff_end_date) >= TO_DATE(:appDate, 'dd/MM/yyyy') ) ");
			sql.append(" AND ( (wis.lower_arpu_increase is null and wis.upper_arpu_increase is null) ");
			sql.append("      or (:arpuIncrease between wis.lower_arpu_increase and wis.upper_arpu_increase) ) ");
			
	    	MapSqlParameterSource parameterSource = new MapSqlParameterSource();
			parameterSource.addValue("basketId", itemSetCriteria.getBasketId());
			parameterSource.addValue("eligibleItemType", "%" + StringUtils.defaultIfEmpty(itemSetCriteria.getEligibleItemType(), LtsBackendConstant.ITEM_TYPE_PLAN) + "%");
			parameterSource.addValue("itemSetType", itemSetCriteria.getItemSetType());
			parameterSource.addValue("profileArpu", itemSetCriteria.getProfileArpu());
			parameterSource.addValue("additionalFee", itemSetCriteria.getAdditionalFee());
			parameterSource.addValue("locale", StringUtils.defaultIfEmpty(itemSetCriteria.getLocale(), LtsBackendConstant.LOCALE_ENG));
			parameterSource.addValue("displayType", StringUtils.defaultIfEmpty(itemSetCriteria.getDisplayType(), LtsBackendConstant.DISPLAY_TYPE_ITEM_SELECT));
			parameterSource.addValue("appDate", StringUtils.split(StringUtils.defaultIfEmpty(itemSetCriteria.getApplnDate() , LtsDateFormatHelper.getSysDate("dd/MM/yyyy")), " ")[0]);
			parameterSource.addValue("arpuIncrease", (itemSetCriteria.getNewArpu() - itemSetCriteria.getProfileArpu()));
			
			//BOM2017135
			if(StringUtils.isNotBlank(itemSetCriteria.getLtsHousingType())){
				sql.append(" AND (wis.LTS_HOUSING_CAT is null OR UPPER(wis.LTS_HOUSING_CAT) like UPPER(:ltsHousingType))");
				parameterSource.addValue("ltsHousingType", "%|" + itemSetCriteria.getLtsHousingType() + "|%");	
			}else if(StringUtils.isNotBlank(itemSetCriteria.getHousingType())){
				sql.append(" AND (wis.ELIGIBLE_HOUSING_TYPE is null OR UPPER(wis.ELIGIBLE_HOUSING_TYPE) like UPPER(:housingType))");
				parameterSource.addValue("housingType", "%" + itemSetCriteria.getHousingType() + "%");
			}else {
				sql.append(" AND (wis.ELIGIBLE_HOUSING_TYPE is null AND wis.LTS_HOUSING_CAT IS NULL) ");
			}
			
			sql.append(" AND (wis.ELIGIBLE_TEAM IS NULL ");
			if (StringUtils.isNotBlank(itemSetCriteria.getAreaCode())) {
				sql.append(" OR UPPER(wis.ELIGIBLE_TEAM) LIKE UPPER(:likeAreaCode) "); 
				parameterSource.addValue("likeAreaCode", "%| " + itemSetCriteria.getAreaCode() + " |%");
			}
			if (StringUtils.isNotBlank(itemSetCriteria.getTeamCode())) {
				sql.append(" OR UPPER(wis.ELIGIBLE_TEAM) LIKE UPPER(:likeTeamCode) "); 
				parameterSource.addValue("likeTeamCode", "%| " + itemSetCriteria.getTeamCode() + " |%");
			}
			sql.append(" ) ");
			
			sql.append(" AND (wis.NON_ELIGIBLE_TEAM IS NULL ");
			if (StringUtils.isNotBlank(itemSetCriteria.getAreaCode()) || StringUtils.isNotBlank(itemSetCriteria.getTeamCode())) {
				sql.append(" OR (wis.NON_ELIGIBLE_TEAM IS NOT NULL ");
				if (StringUtils.isNotBlank(itemSetCriteria.getAreaCode())) {
					sql.append(" AND UPPER(wis.NON_ELIGIBLE_TEAM) NOT LIKE UPPER(:likeAreaCode) "); 
					parameterSource.addValue("likeAreaCode", "%| " + itemSetCriteria.getAreaCode() + " |%");
				}
				if (StringUtils.isNotBlank(itemSetCriteria.getTeamCode())) {
					sql.append(" AND UPPER(wis.NON_ELIGIBLE_TEAM) NOT LIKE UPPER(:likeTeamCode) "); 
					parameterSource.addValue("likeTeamCode", "%| " + itemSetCriteria.getTeamCode() + " |%");
				}
				sql.append(" ) ");
			}
			sql.append(" ) ");
			
			if (StringUtils.isNotBlank(itemSetCriteria.getCustCampaignCd())) {
				sql.append(" AND (wis.CUST_DEDICATED_CAMPAIGN_CD IS NULL OR UPPER(wis.CUST_DEDICATED_CAMPAIGN_CD) LIKE UPPER(:custCampaignCd)) ");
				parameterSource.addValue("custCampaignCd", "%" + itemSetCriteria.getCustCampaignCd() + "%");
			}
			else {
				sql.append(" AND wis.CUST_DEDICATED_CAMPAIGN_CD IS NULL ");
			}
				
			if (StringUtils.isNotEmpty(itemSetCriteria.getTpExpiredMonths())) {
				sql.append(" AND ( (:tpExpiredMonths BETWEEN wis.UPPER_TP_EXPIRE_MTH AND wis.LOWER_TP_EXPIRE_MTH) OR (wis.UPPER_TP_EXPIRE_MTH IS NULL AND wis.LOWER_TP_EXPIRE_MTH IS NULL) ) ");
				parameterSource.addValue("tpExpiredMonths", itemSetCriteria.getTpExpiredMonths());
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
				parameterSource.addValue("srvBoundary", itemSetCriteria.getSrvBoundary());
			}
			
			if (StringUtils.isNotBlank(Double.toString(itemSetCriteria.getSrvTenure()))) {
				sql.append(" AND ((:srvTenure BETWEEN WIS.LOWER_SRV_TENURE AND WIS.UPPER_SRV_TENURE) ");
				sql.append(" OR WIS.LOWER_SRV_TENURE IS NULL");
				sql.append(" OR WIS.UPPER_SRV_TENURE IS NULL) ");
				parameterSource.addValue("srvTenure", itemSetCriteria.getSrvTenure());
			}
			
			sql.append(" ORDER BY wis.item_set_id");
			
			itemSetList = simpleJdbcTemplate.query(sql.toString(), getItemSetRowMapper(true), parameterSource);
			
			for (int i = 0; itemSetList != null && i < itemSetList.size(); i++) {
				
				itemList = this.getSetItemList(itemSetList.get(i).getItemSetId(), itemSetCriteria.getLocale(), itemSetCriteria.isSelectDefault(), itemSetCriteria.getChannelId(), itemSetCriteria.getDisplayType(), itemSetCriteria.getBaseContractPeriod(), itemSetCriteria.getApplnDate(), itemSetCriteria.getEligibleDocType(), itemSetCriteria.getOsType());
				
				if (itemList == null || itemList.isEmpty()) {
					continue;
				}

				itemSetList.get(i).setItemDetails(itemList.toArray(new ItemDetailDTO[0]));

				attbList = this.getSetAttbList(itemSetList.get(i).getItemSetId());
				itemSetList.get(i).setItemSetAttbs(attbList.toArray(new ItemSetAttbDTO[0]));
				rtnItemSetList.add(itemSetList.get(i));
			}
			
	    } catch (EmptyResultDataAccessException erdae) {
			throw new AppRuntimeException(erdae);
		} catch (Exception e) {
			logger.error("Error in getBasketItemSetList - get basket item set", e);
			throw new DAOException(e.getMessage(), e);
		}
		return rtnItemSetList;
	}
	
	
	public List<ItemDetailDTO> getItemList(String pItemType, String pLocale, boolean pSelectDefault, boolean pGetAttrInd, String pChannelId, String pApplDate) throws DAOException {
		return getItemList(pItemType, pLocale, pSelectDefault, pGetAttrInd, null, pChannelId, LtsBackendConstant.DISPLAY_TYPE_ITEM_SELECT, null, pApplDate);
	}

	public List<ItemDetailDTO> getItemList(String pItemType, String pLocale, boolean pSelectDefault, boolean pGetAttrInd, String baseContractPeriod, String pChannelId, String pApplDate) throws DAOException {
		return getItemList(pItemType, pLocale, pSelectDefault, pGetAttrInd, baseContractPeriod, pChannelId, LtsBackendConstant.DISPLAY_TYPE_ITEM_SELECT, null, pApplDate);
	}
	
	public List<ItemDetailDTO> getItemList(String pItemType, String pLocale, boolean pSelectDefault, boolean pGetAttrInd, String baseContractPeriod, String pChannelId, String pDisplayType, String pApplDate) throws DAOException {
		return getItemList(pItemType, pLocale, pSelectDefault, pGetAttrInd, baseContractPeriod, pChannelId, pDisplayType, null, pApplDate);
	}
	
	public List<ItemDetailDTO> getVasOtherCommitmentPeriodItemList(String pItemType, String pLocale, boolean pSelectDefault, boolean pGetAttrInd, String baseContractPeriod, String pChannelId, String pDisplayType, String pCampaignCd, String pApplDate, String pOsType) throws DAOException {	
		StringBuilder sql = new StringBuilder(); 
		sql.append(SQL_GET_ITEM);
		
		if (StringUtils.isNotBlank(pApplDate)) {
			sql.append(" and TRUNC(wcia.eff_start_date) <= to_date('" + StringUtils.split(pApplDate, " ")[0] + "', 'dd/MM/yyyy') ");
			sql.append(" and (wcia.eff_end_date is null or TRUNC(wcia.eff_end_date) >= to_date('" + StringUtils.split(pApplDate, " ")[0] + "', 'dd/MM/yyyy') ) ");
		}
		else {
			sql.append(" and TRUNC(wcia.eff_start_date) <= sysdate ");
			sql.append(" and (wcia.eff_end_date is null or wcia.eff_end_date >= sysdate) " );
		}
		
		if (StringUtils.isNotEmpty(pCampaignCd)) {
			sql.append(" and wi.customer_campaign_cd like ");
			sql.append("'%" + pCampaignCd + "%'");
		}
		
		if (StringUtils.isNotEmpty(baseContractPeriod)) {
			sql.append(" and widrrv.contract_period < ");
			sql.append(baseContractPeriod);
		}
		if (StringUtils.isNotBlank(pOsType)){
				sql.append(" and (widtll.os_type in ");
				sql.append(" ('" + pOsType + "', '"+ LtsBackendConstant.OS_TYPE_ALL +"') ");
				sql.append(" or widtll.os_type is null) ");
		}
		sql.append(" order by wip.recurrent_amt, wip.mth_to_mth_rate, wi.id desc");
		
		List<ItemDetailDTO> itemDetail	= this.getItem(sql.toString(), this.getItemMapper(false, pSelectDefault, false), pLocale, pItemType, LtsBackendConstant.LOB_LTS, pDisplayType, pLocale, pChannelId);
		List<ItemAttbDTO> itemAttbList = null;
		if (pGetAttrInd){
			for (int i = 0; itemDetail != null && i < itemDetail.size(); i++){
				itemAttbList = this.getItemAttb(itemDetail.get(i).getItemId());				
				
				if (itemAttbList != null && !itemAttbList.isEmpty()) {
					for (ItemAttbDTO itemAttb : itemAttbList) {
						if (StringUtils.isEmpty(itemAttb.getAttbInfoKey())) {
							continue;
						}
						List<ItemAttbInfoDTO> itemAttbInfoList = getItemAttbInfoList(itemAttb.getAttbInfoKey());
						itemAttb.setItemAttbInfoList(itemAttbInfoList);
					}
				}
				
				if (itemAttbList !=  null && !itemAttbList.isEmpty()) {
					itemDetail.get(i).setItemAttbs(itemAttbList.toArray(new ItemAttbDTO[itemAttbList.size()]));
				}
				
			}
			
		}
		return itemDetail;
	}
	
	public List<ItemDetailDTO> getItemList(String pItemType, String pLocale, boolean pSelectDefault, boolean pGetAttrInd, String baseContractPeriod, String pChannelId, String pDisplayType, String pCampaignCd, String pApplDate) throws DAOException {
		return getItemList(pItemType, pLocale, pSelectDefault, pGetAttrInd, baseContractPeriod, pChannelId, pDisplayType, pCampaignCd, null, pApplDate, null);
	}
	
	public List<ItemDetailDTO> getItemList(String pItemType, String pLocale, boolean pSelectDefault, boolean pGetAttrInd, String baseContractPeriod, String pChannelId, String pDisplayType, String pCampaignCd, String pEligibleDevice, String pApplDate) throws DAOException {
		return getItemList(pItemType, pLocale, pSelectDefault, pGetAttrInd, baseContractPeriod, pChannelId, pDisplayType, pCampaignCd, null, pApplDate, null);
	}
	
	public List<ItemDetailDTO> getItemList(String pItemType, String pLocale, boolean pSelectDefault, boolean pGetAttrInd, String baseContractPeriod, String pChannelId, String pDisplayType, String pCampaignCd, String pEligibleDevice, String pApplDate, String pEligibleDocType) throws DAOException {
		return getItemList(
				new ItemCriteriaBuilder()
					.setApplnDate(pApplDate)
					.setBaseContractPeriod(baseContractPeriod)
					.setCampaignCd(pCampaignCd)
					.setChannelId(pChannelId)
					.setDisplayType(pDisplayType)
					.setEligibleDevice(pEligibleDevice)
					.setEligibleDocType(pEligibleDocType)
					.setGetAttrInd(pGetAttrInd)
					.setItemType(pItemType)
					.setLocale(pLocale)
					.setSelectDefault(pSelectDefault)
					.build()
				);
	}
	
	public List<ItemDetailDTO> getItemList(ItemCriteriaDTO itemCriteria) throws DAOException {		
		StringBuilder sql = new StringBuilder(); 
		sql.append(SQL_GET_ITEM);
		
		if (StringUtils.isNotBlank(itemCriteria.getApplnDate())) {
			sql.append(" and TRUNC(wcia.eff_start_date) <= to_date('" + StringUtils.split(itemCriteria.getApplnDate(), " ")[0] + "', 'dd/MM/yyyy') ");
			sql.append(" and (wcia.eff_end_date is null or TRUNC(wcia.eff_end_date) >= to_date('" + StringUtils.split(itemCriteria.getApplnDate(), " ")[0] + "', 'dd/MM/yyyy') ) ");
		}
		else {
			sql.append(" and TRUNC(wcia.eff_start_date) <= sysdate ");
			sql.append(" and (wcia.eff_end_date is null or wcia.eff_end_date >= sysdate) " );
		}
		
		if (StringUtils.isNotEmpty(itemCriteria.getCampaignCd())) {
			sql.append(" and wi.customer_campaign_cd like ");
			sql.append("'%" + itemCriteria.getCampaignCd() + "%'");
		}
		
		if (StringUtils.isNotEmpty(itemCriteria.getEligibleDevice())) {
			sql.append(" and (widtll.eligible_device_type = 'ALL' or widtll.eligible_device_type IS NULL or UPPER(widtll.eligible_device_type) = '").append(itemCriteria.getEligibleDevice().toUpperCase()).append("' ) ");
		}
		
		if (StringUtils.isNotEmpty(itemCriteria.getEligibleDocType())) {
			sql.append(" and (widtll.eligible_doc_type like '%" + itemCriteria.getEligibleDocType().toUpperCase() + "%' ");
			sql.append(" OR widtll.eligible_doc_type is null) ");
		}
		
		if (StringUtils.isNotEmpty(itemCriteria.getBaseContractPeriod()) && !StringUtils.equals(itemCriteria.getItemType(), LtsBackendConstant.ITEM_TYPE_VAS_PRE_WIRING)) {
			sql.append(" and widrrv.contract_period = ");
			sql.append(itemCriteria.getBaseContractPeriod());
		}
		if (StringUtils.isNotBlank(itemCriteria.getOsType())){
				sql.append(" and (widtll.os_type in ");
				sql.append(" ('" + itemCriteria.getOsType() + "', '" + LtsBackendConstant.OS_TYPE_ALL + "') ");
				sql.append(" or widtll.os_type is null) ");
		}
		sql.append(" order by wip.recurrent_amt, wip.mth_to_mth_rate, wi.id desc");
		
		List<ItemDetailDTO> itemDetail	= this.getItem(sql.toString(), this.getItemMapper(false, itemCriteria.isSelectDefault(), false), itemCriteria.getLocale(), itemCriteria.getItemType(), LtsBackendConstant.LOB_LTS, itemCriteria.getDisplayType(), itemCriteria.getLocale(), itemCriteria.getChannelId());
		List<ItemAttbDTO> itemAttbList = null;
		if (itemCriteria.isGetAttrInd()){
			for (int i = 0; itemDetail != null && i < itemDetail.size(); i++){
				//try to get attb according to locale
				itemAttbList = this.getItemAttbByLocale(itemDetail.get(i).getItemId(), itemCriteria.getLocale());

				//if not found, get by en locale
				if(itemAttbList == null || itemAttbList.isEmpty()){
					itemAttbList = this.getItemAttbByLocale(itemDetail.get(i).getItemId(), LtsBackendConstant.LOCALE_ENG);
				}
				
				if (itemAttbList != null && !itemAttbList.isEmpty()) {
					for (ItemAttbDTO itemAttb : itemAttbList) {
						if (StringUtils.isEmpty(itemAttb.getAttbInfoKey())) {
							continue;
						}
						List<ItemAttbInfoDTO> itemAttbInfoList = getItemAttbInfoList(itemAttb.getAttbInfoKey());
						itemAttb.setItemAttbInfoList(itemAttbInfoList);
					}
				}
				
				if (itemAttbList !=  null && !itemAttbList.isEmpty()) {
					itemDetail.get(i).setItemAttbs(itemAttbList.toArray(new ItemAttbDTO[itemAttbList.size()]));
				}
				
			}
			
		}
		return itemDetail;
	}
	

	public List<ItemDetailDTO> getItem(String pSql, ParameterizedRowMapper<ItemDetailDTO> pMapper, Object... arg) throws DAOException {
	    
	    try{
	    	return simpleJdbcTemplate.query(pSql, pMapper, arg);
	    	
	    } catch (EmptyResultDataAccessException erdae) {
			throw new AppRuntimeException(erdae);
		} catch (Exception e) {
			logger.error("Error in getItem - ", e);
			logger.debug("getItem: " + pSql);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public List<ItemDetailDTO> getBasketItemList(String pBasketId, String pItemType, String pLocale, boolean pSelectDefault, String pChannelId, String pApplDate)throws DAOException {
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
		return this.getItem(sql.toString(), this.getItemMapper(false, pSelectDefault, true), pLocale, pBasketId, pChannelId, LtsBackendConstant.DISPLAY_TYPE_ITEM_SELECT, LtsBackendConstant.LOB_LTS, pItemType, pLocale);
	}
	
	public List<ItemDetailDTO> getBasketItemList(ItemCriteriaDTO itemCriteria)throws DAOException {
		return getBasketItemList(itemCriteria, true);
	}
	
	public List<ItemDetailDTO> getBasketItemList(ItemCriteriaDTO itemCriteria, boolean pGetAttrInd)throws DAOException {
		StringBuilder sql = new StringBuilder(SQL_GET_BASKET_ITEM);
		
		if (StringUtils.isNotBlank(itemCriteria.getApplnDate())) {
			sql.append(" and TRUNC(wcia.eff_start_date) <= to_date('" + StringUtils.split(itemCriteria.getApplnDate(), " ")[0] + "', 'dd/MM/yyyy') ");
			sql.append(" and (wcia.eff_end_date is null or TRUNC(wcia.eff_end_date) >= to_date('" + StringUtils.split(itemCriteria.getApplnDate(), " ")[0] + "', 'dd/MM/yyyy') ) ");
		}
		else {
			sql.append(" and TRUNC(wcia.eff_start_date) <= sysdate ");
			sql.append(" and (wcia.eff_end_date is null or wcia.eff_end_date >= sysdate) " );
		}
		
		if (StringUtils.isNotBlank(itemCriteria.getOsType())){
				sql.append(" and (widlts.os_type in ");
				sql.append(" ('" + itemCriteria.getOsType() + "', '" + LtsBackendConstant.OS_TYPE_ALL + "') ");
				sql.append(" or widlts.os_type is null) ");
		}
		
		
		sql.append(" order by wi.id ");
		
		List<ItemDetailDTO> itemDetail = this.getItem(sql.toString(), this.getItemMapper(false, itemCriteria.isSelectDefault(), true), 
				itemCriteria.getLocale(), itemCriteria.getBasketId(), itemCriteria.getChannelId(), 
				LtsBackendConstant.DISPLAY_TYPE_ITEM_SELECT, LtsBackendConstant.LOB_LTS, itemCriteria.getItemType(), itemCriteria.getLocale());
		
		List<ItemAttbDTO> itemAttbList = null;
		if (pGetAttrInd){
			for (int i = 0; itemDetail != null && i < itemDetail.size(); i++){
				itemAttbList = this.getItemAttb(itemDetail.get(i).getItemId());
				
				
				if (itemAttbList != null && !itemAttbList.isEmpty()) {
					for (ItemAttbDTO itemAttb : itemAttbList) {
						if (StringUtils.isEmpty(itemAttb.getAttbInfoKey())) {
							continue;
						}
						List<ItemAttbInfoDTO> itemAttbInfoList = getItemAttbInfoList(itemAttb.getAttbInfoKey());
						itemAttb.setItemAttbInfoList(itemAttbInfoList);
					}
				}
				
				if (itemAttbList !=  null && !itemAttbList.isEmpty()) {
					itemDetail.get(i).setItemAttbs(itemAttbList.toArray(new ItemAttbDTO[itemAttbList.size()]));
				}
			}
		}
		return itemDetail;
		
		//return this.getItem(sql.toString(), this.getItemMapper(false, itemCriteria.isSelectDefault(), true), itemCriteria.getLocale(), itemCriteria.getBasketId(), itemCriteria.getChannelId(), LtsBackendConstant.DISPLAY_TYPE_ITEM_SELECT, LtsBackendConstant.LOB_LTS, itemCriteria.getItemType(), itemCriteria.getLocale());
	}
	
	public List<ItemDetailDTO> getBasketItemList(String pBasketId, String pItemType, String pLocale, boolean pSelectDefault, boolean pGetAttrInd, String pChannelId, String pApplDate)throws DAOException {
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
		List<ItemDetailDTO> itemDetail = this.getItem(sql.toString(), this.getItemMapper(false, pSelectDefault, true), pLocale, pBasketId, pChannelId, LtsBackendConstant.DISPLAY_TYPE_ITEM_SELECT, LtsBackendConstant.LOB_LTS, pItemType, pLocale);
		
		List<ItemAttbDTO> itemAttbList = null;
		if (pGetAttrInd){
			for (int i = 0; itemDetail != null && i < itemDetail.size(); i++){
				itemAttbList = this.getItemAttb(itemDetail.get(i).getItemId());
				
				
				if (itemAttbList != null && !itemAttbList.isEmpty()) {
					for (ItemAttbDTO itemAttb : itemAttbList) {
						if (StringUtils.isEmpty(itemAttb.getAttbInfoKey())) {
							continue;
						}
						List<ItemAttbInfoDTO> itemAttbInfoList = getItemAttbInfoList(itemAttb.getAttbInfoKey());
						itemAttb.setItemAttbInfoList(itemAttbInfoList);
					}
				}
				
				if (itemAttbList !=  null && !itemAttbList.isEmpty()) {
					itemDetail.get(i).setItemAttbs(itemAttbList.toArray(new ItemAttbDTO[itemAttbList.size()]));
				}
			}
		}
		return itemDetail;
	}
	
	public List<ItemDetailDTO> getBasketItemList(String pBasketId, String pItemType, String pLocale, boolean pSelectDefault, String displayType, String pChannelId, String pApplDate)throws DAOException {
		return getBasketItemList(pBasketId, pItemType, pLocale, pSelectDefault, displayType, pChannelId, null, pApplDate);
	}
	
	public List<ItemDetailDTO> getBasketItemList(String pBasketId, String pItemType, String pLocale, boolean pSelectDefault, String displayType, String pChannelId, String pAreaCode, String pApplDate)throws DAOException {
		
		StringBuilder sql = new StringBuilder(SQL_GET_BASKET_ITEM);
		if (StringUtils.isNotBlank(pAreaCode)) {
			sql.append(" and (wbia.eligible_team is null || UPPER(wbia.eligible_team) like UPPER('%| "+ pAreaCode +" |%')) ");
		}
		else {
			sql.append(" and wbia.eligible_team is null ");
		}
		if (StringUtils.isNotBlank(pApplDate)) {
			sql.append(" and TRUNC(wcia.eff_start_date) <= to_date('" + StringUtils.split(pApplDate, " ")[0] + "', 'dd/MM/yyyy') ");
			sql.append(" and (wcia.eff_end_date is null or TRUNC(wcia.eff_end_date) >= to_date('" + StringUtils.split(pApplDate, " ")[0] + "', 'dd/MM/yyyy') ) ");
		}
		else {
			sql.append(" and TRUNC(wcia.eff_start_date) <= sysdate ");
			sql.append(" and (wcia.eff_end_date is null or wcia.eff_end_date >= sysdate) " );
		}
		return this.getItem(sql.toString(), this.getItemMapper(false, pSelectDefault, true), pLocale, pBasketId, pChannelId, displayType, LtsBackendConstant.LOB_LTS, pItemType, pLocale);
	}
	
	public List<ItemDetailDTO> getBasketItemList(String pBasketId, String pItemType, String pLocale, boolean pSelectDefault, Double arpuIncrease, String pChannelId, String pApplDate)throws DAOException {
		StringBuilder sql = new StringBuilder();
		sql.append(SQL_GET_BASKET_ITEM_DTL_LTS);
		if (StringUtils.isNotBlank(pApplDate)) {
			sql.append(" and TRUNC(wcia.eff_start_date) <= to_date('" + StringUtils.split(pApplDate, " ")[0] + "', 'dd/MM/yyyy') ");
			sql.append(" and (wcia.eff_end_date is null or TRUNC(wcia.eff_end_date) >= to_date('" + StringUtils.split(pApplDate, " ")[0] + "', 'dd/MM/yyyy') ) ");
		}
		else {
			sql.append(" and TRUNC(wcia.eff_start_date) <= sysdate ");
			sql.append(" and (wcia.eff_end_date is null or wcia.eff_end_date >= sysdate) " );
		}
		if (arpuIncrease != null) {
			sql.append("and ").append(arpuIncrease).append(" between lower_arpu_increase and upper_arpu_increase ");
		}
		
		sql.append(" ORDER BY wi.id ");
		
		return this.getItem(sql.toString(), this.getItemMapper(false, pSelectDefault, true), pLocale, pBasketId, pChannelId, LtsBackendConstant.DISPLAY_TYPE_ITEM_SELECT, LtsBackendConstant.LOB_LTS, pItemType, pLocale);
	}
	
	
	public List<ItemDetailDTO> getBasketNowTvItemList(String pBasketId, String pItemType, String pLocale, boolean pSelectDefault, String pChannelId, String pApplDate) throws DAOException {
		
		MapSqlParameterSource parameterSource = new MapSqlParameterSource();
		parameterSource.addValue("locale", pLocale);
		parameterSource.addValue("basketId", pBasketId);
		parameterSource.addValue("itemType", pItemType);
		parameterSource.addValue("channelId", pChannelId);
		parameterSource.addValue("lob", LtsBackendConstant.LOB_LTS);
		parameterSource.addValue("displayType", LtsBackendConstant.DISPLAY_TYPE_ITEM_SELECT);
		StringBuilder sql = new StringBuilder(SQL_GET_BASKET_NOWTV_ITEM);
		if (StringUtils.isNotBlank(pApplDate)) {
			sql.append(" and TRUNC(wcia.eff_start_date) <= to_date('" + StringUtils.split(pApplDate, " ")[0] + "', 'dd/MM/yyyy') ");
			sql.append(" and (wcia.eff_end_date is null or TRUNC(wcia.eff_end_date) >= to_date('" + StringUtils.split(pApplDate, " ")[0] + "', 'dd/MM/yyyy') ) ");
		}
		else {
			sql.append(" and TRUNC(wcia.eff_start_date) <= sysdate ");
			sql.append(" and (wcia.eff_end_date is null or wcia.eff_end_date >= sysdate) " );
		}
		sql.append(" order by wbia.display_seq ");
		return simpleJdbcTemplate.query(sql.toString(), this.getItemMapper(true, pSelectDefault, false), parameterSource);
//		return this.getItem(SQL_GET_BASKET_NOWTV_ITEM, );
	}
	
	public List<ItemDetailDTO> getItem(String[] pItemId, String pDisplayType, String pLocale, boolean pSelectDefault) throws DAOException {
		return getItem(pItemId, pDisplayType, pLocale, pSelectDefault, false);
	}
	
	public List<ItemDetailDTO> getItem(String[] pItemId, String pDisplayType, String pLocale, boolean pSelectDefault, boolean pIgnoreEffDate) throws DAOException {
		StringBuffer strSqlGetItemList = new StringBuffer();
		strSqlGetItemList.append(SQL_SELECT_GET_ITEM);
		strSqlGetItemList.append(" where wi.id in ( ");
		
		for (int i=0; pItemId != null && i < pItemId.length; i++){
			strSqlGetItemList.append("'" + pItemId[i] + "'");
			if (i != pItemId.length-1){
				strSqlGetItemList.append(", ");
			}
		}
		
		strSqlGetItemList.append(" ) and wi.lob = 'LTS' " +
								 " and wi.id = wip.id (+) " +
								 " and widl.item_id = wi.id " +
								 " and widl.display_type = '" +  pDisplayType + "' " +
								 " and widl.locale = '" + pLocale + "' " +
							     "and wi.id = widrrv.id (+) " +
						         "and wi.id = wipdl.item_id (+) " + 
						         "and wi.id = widlts.item_id (+) ");
		if(!pIgnoreEffDate){
			strSqlGetItemList.append(
	         "and (wipdl.eff_end_date is null or wipdl.eff_end_date >= sysdate) " +
	         "and (wip.eff_end_date is null or wip.eff_end_date >= sysdate) ");
		}
		
		strSqlGetItemList.append(" ORDER BY WI.ID ");
		return this.getItem(strSqlGetItemList.toString(), this.getItemMapper(false, pSelectDefault, false), pLocale);
	}
	
	public List<ChannelGroupDetailDTO> getChannelGroupList(String pFormType, String pDeviceType, String pLocale) throws DAOException {
	    ParameterizedRowMapper<ChannelGroupDetailDTO> channelGroupMapper = new ParameterizedRowMapper<ChannelGroupDetailDTO>() {
			public ChannelGroupDetailDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				ChannelGroupDetailDTO channelGroup = new ChannelGroupDetailDTO();
				channelGroup.setFormId(rs.getString("FORM_ID"));
				channelGroup.setChannelGroupId(rs.getString("CHANNEL_GROUP_ID"));
				channelGroup.setChannelGroupCd(rs.getString("CHANNEL_GROUP_CD"));
				channelGroup.setChannelGroupDesc(rs.getString("CHANNEL_GROUP_DESC"));
				channelGroup.setChannelGroupHtml(rs.getString("CHANNEL_GROUP_HTML"));
				channelGroup.setImagePath(rs.getString("IMAGE_PATH"));
				return channelGroup;
			}
	    };
	    
	    try{
			return simpleJdbcTemplate.query(SQL_GET_CHANNEL_GROUP_DETAIL, channelGroupMapper, pFormType, LtsBackendConstant.LOB_LTS, pDeviceType, pLocale);
			
	    } catch (EmptyResultDataAccessException erdae) {
			throw new AppRuntimeException(erdae);
		} catch (Exception e) {
			logger.error("Error in getChannelGroupList - ", e);
			logger.debug("getChannelGroupList: " + SQL_GET_CHANNEL_GROUP_DETAIL);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	
	public List<ChannelDetailDTO> getGroupChannel(String pChannelGroupId, String pLocale, final boolean pSelectDefault) throws DAOException {
		return getGroupChannel(pChannelGroupId, pLocale, pSelectDefault, null);
	}
	
	@SuppressWarnings("unchecked")
	public List<ChannelDetailDTO> getGroupChannel(String pChannelGroupId, String pLocale, final boolean pSelectDefault, String credit) throws DAOException {
	    ParameterizedRowMapper<ChannelDetailDTO> channelMapper = new ParameterizedRowMapper<ChannelDetailDTO>() {
			public ChannelDetailDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				ChannelDetailDTO channelDetail = new ChannelDetailDTO();
				channelDetail.setChannelId(rs.getString("CHANNEL_ID"));
				channelDetail.setChannelCd(rs.getString("CHANNEL_CD"));
				channelDetail.setCredit(rs.getString("CREDIT"));
				channelDetail.setTvType(rs.getString("TV_TYPE"));
				channelDetail.setIsAdultChannel(rs.getString("IS_ADULT_CHANNEL"));
				String mdoInd = rs.getString("MDO_IND");
				channelDetail.setMdoInd(mdoInd);
				if (pSelectDefault){
					if (StringUtils.equalsIgnoreCase(LtsBackendConstant.ITEM_MDO_MANDATORY, mdoInd) 
							|| StringUtils.equalsIgnoreCase(LtsBackendConstant.ITEM_MDO_DEFAULT, mdoInd)){
							channelDetail.setSelected(true);
						} 
				}
				channelDetail.setChannelHtml(rs.getString("CHANNEL_HTML"));
				return channelDetail;
			}
	    };
	    
	    MapSqlParameterSource paramSource = new MapSqlParameterSource();
	    
	    StringBuffer sql = new StringBuffer();
	    sql.append("select wtc.channel_id, wtc.channel_cd, ");
	    sql.append("wtc.credit, wtc.tv_type, wtc.is_adult_channel, wtc.mdo_ind, ");
	    sql.append("wtdl.html as channel_html ");
	    sql.append("from w_tv_channel wtc, w_tv_display_lkup wtdl ");
	    sql.append("where wtc.channel_group_id = :groupId ");
	    sql.append("and (wtc.eff_end_date is null or wtc.eff_end_date >= sysdate) ");
	    sql.append("and wtdl.id = wtc.channel_id ");
	    sql.append("and wtdl.type = 'CHANNEL' ");
	    sql.append("and wtdl.locale = :locale ");
		
		if (StringUtils.isNotBlank(credit)) {
			sql.append("and wtc.credit <= :credit ");
			paramSource.addValue("credit", credit);
		}
		sql.append(" order by wtc.display_seq ");

		paramSource.addValue("groupId", pChannelGroupId);
		paramSource.addValue("locale", pLocale);
		
	    
	    try{
			return simpleJdbcTemplate.getNamedParameterJdbcOperations().query(sql.toString(), paramSource, channelMapper);

	    } catch (EmptyResultDataAccessException erdae) {
			throw new AppRuntimeException(erdae);
		} catch (Exception e) {
			logger.error("Error in getGroupChannel - ", e);
			logger.debug("getGroupChannel: " + sql.toString());
			throw new DAOException(e.getMessage(), e);
		}
	}

	public List<ChannelAttbDTO> getChannelAttb(String pChannelId, String pLocale) throws DAOException {
	    ParameterizedRowMapper<ChannelAttbDTO> channelAttbMapper = new ParameterizedRowMapper<ChannelAttbDTO>() {
			public ChannelAttbDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				ChannelAttbDTO channelAttb = new ChannelAttbDTO();
				channelAttb.setAttbID(rs.getString("ATTB_ID"));
				channelAttb.setAttbDesc(rs.getString("DESCRIPTION"));
				channelAttb.setInputMethod(rs.getString("INPUT_METHOD"));
				channelAttb.setInputFormat(rs.getString("INPUT_FORMAT"));
				channelAttb.setMinLength(rs.getInt("MIN_LENGTH"));
				channelAttb.setMaxLength(rs.getInt("MAX_LENGTH"));
				channelAttb.setDefaultValue(rs.getString("DEFAULT_VALUE"));
				channelAttb.setAttbInfoKey(rs.getString("ATTB_INFO_KEY"));
				channelAttb.setVisualInd(rs.getString("VISUAL_IND"));
				channelAttb.setDisplaySeq(rs.getInt("DISPLAY_SEQ"));
				channelAttb.setDisplaySubSeq(rs.getInt("DISPLAY_SUB_SEQ"));
				return channelAttb;
			}
	    };
	    try{
			return simpleJdbcTemplate.query(SQL_GET_CHANNEL_ATTB, channelAttbMapper, pChannelId, pLocale);

	    } catch (EmptyResultDataAccessException erdae) {
			throw new AppRuntimeException(erdae);
		} catch (Exception e) {
			logger.error("Error in getChannelAttb - ", e);
			logger.debug("getChannelAttb: " + SQL_GET_CHANNEL_ATTB);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public List<ChannelIconDTO> getChannelIcon(String pChannelId, String pLocale) throws DAOException {
		
		ParameterizedRowMapper<ChannelIconDTO> channelIconMapper = new ParameterizedRowMapper<ChannelIconDTO>() {

			@Override
			public ChannelIconDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				ChannelIconDTO channelIcon = new ChannelIconDTO();
				channelIcon.setChannelId(rs.getString("CHANNEL_ID"));
				channelIcon.setType(rs.getString("TYPE"));
				channelIcon.setDisplaySeq(rs.getString("DISPLAY_SEQ"));
				channelIcon.setImageDesc(rs.getString("DESCRIPTION"));
				channelIcon.setImageHtml(rs.getString("HTML"));
				channelIcon.setImagePath(rs.getString("IMAGE_ID"));
				return channelIcon;
			}
			
		};
		
		try {
			return simpleJdbcTemplate.query(SQL_GET_CHANNEL_ICON,
					channelIconMapper, pChannelId, pLocale);

		} catch (EmptyResultDataAccessException erdae) {
			throw new AppRuntimeException(erdae);
		} catch (Exception e) {
			logger.error("Error in getChannelIcon - ", e);
			logger.debug("getChannelIcon: " + SQL_GET_CHANNEL_ICON);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	
	public List<ItemAttbInfoDTO> getItemAttbInfoList(String attbInfoKey) throws DAOException {
		
		if (StringUtils.isEmpty(attbInfoKey)) {
			return null;
		}
		
		ParameterizedRowMapper<ItemAttbInfoDTO> itemAttbMapper = new ParameterizedRowMapper<ItemAttbInfoDTO>() {
			public ItemAttbInfoDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				ItemAttbInfoDTO itemAttbInfo = new ItemAttbInfoDTO();
				itemAttbInfo.setAttbDesc(rs.getString("ATTB_VALUE_DESC"));
				itemAttbInfo.setAttbValue(rs.getString("ATTB_VALUE"));
				itemAttbInfo.setAttbInfoKey(rs.getString("ATTB_INFO_KEY"));
				return itemAttbInfo;
			}
	    };
		
	    try{
			return simpleJdbcTemplate.query(SQL_GET_ITEM_ATTB_INFO, itemAttbMapper, attbInfoKey);
	    } catch (EmptyResultDataAccessException erdae) {
			throw new AppRuntimeException(erdae);
		} catch (Exception e) {
			logger.error("Error in getItemAttbInfoList - ", e);
			logger.debug("getItemAttbInfoList: " + SQL_GET_ITEM_ATTB_INFO);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	
	public List<ItemAttbDTO> getItemAttb(String pItemId) throws DAOException {
	    ParameterizedRowMapper<ItemAttbDTO> itemAttbMapper = new ParameterizedRowMapper<ItemAttbDTO>() {
			public ItemAttbDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				ItemAttbDTO itemAttb = new ItemAttbDTO();
				itemAttb.setAttbID(rs.getString("ATTB_ID"));
				itemAttb.setAttbDesc(rs.getString("ATTB_DESC"));
				itemAttb.setInputMethod(rs.getString("INPUT_METHOD"));
				itemAttb.setInputFormat(rs.getString("INPUT_FORMAT"));
				itemAttb.setMinLength(rs.getInt("MIN_LENGTH"));
				itemAttb.setMaxLength(rs.getInt("MAX_LENGTH"));
				itemAttb.setDefaultValue(rs.getString("DEFAULT_VALUE"));
				itemAttb.setAttbInfoKey(rs.getString("ATTB_INFO_KEY"));
				itemAttb.setVisualInd(rs.getString("VISUAL_IND"));
				itemAttb.setComptId(rs.getString("COMPT_ID"));
				itemAttb.setOfferId(rs.getString("OFFER_ID"));
				itemAttb.setProdId(rs.getString("PRODUCT_ID"));
				
				if (StringUtils.isNotEmpty(itemAttb.getDefaultValue())) {
					itemAttb.setAttbValue(itemAttb.getDefaultValue());
				}
				
				return itemAttb;
			}
	    };
	    try{
			StringBuilder sql = new StringBuilder(); 
			sql.append(SQL_GET_ITEM_ATTB);
			sql.append(" order by wal.attb_id ");
			return simpleJdbcTemplate.query(sql.toString(), itemAttbMapper, pItemId);
	    } catch (EmptyResultDataAccessException erdae) {
			throw new AppRuntimeException(erdae);
		} catch (Exception e) {
			logger.error("Error in getItemAttb - ", e);
			logger.debug("getItemAttb: " + SQL_GET_ITEM_ATTB);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public List<ItemAttbDTO> getItemAttbByLocale(String pItemId, String pLocale) throws DAOException {
	    ParameterizedRowMapper<ItemAttbDTO> itemAttbMapper = new ParameterizedRowMapper<ItemAttbDTO>() {
			public ItemAttbDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				ItemAttbDTO itemAttb = new ItemAttbDTO();
				itemAttb.setAttbID(rs.getString("ATTB_ID"));
				itemAttb.setAttbDesc(rs.getString("ATTB_DESC"));
				itemAttb.setInputMethod(rs.getString("INPUT_METHOD"));
				itemAttb.setInputFormat(rs.getString("INPUT_FORMAT"));
				itemAttb.setMinLength(rs.getInt("MIN_LENGTH"));
				itemAttb.setMaxLength(rs.getInt("MAX_LENGTH"));
				itemAttb.setDefaultValue(rs.getString("DEFAULT_VALUE"));
				itemAttb.setAttbInfoKey(rs.getString("ATTB_INFO_KEY"));
				itemAttb.setVisualInd(rs.getString("VISUAL_IND"));
				itemAttb.setComptId(rs.getString("COMPT_ID"));
				itemAttb.setOfferId(rs.getString("OFFER_ID"));
				itemAttb.setProdId(rs.getString("PRODUCT_ID"));
				
				if (StringUtils.isNotEmpty(itemAttb.getDefaultValue())) {
					itemAttb.setAttbValue(itemAttb.getDefaultValue());
				}
				
				return itemAttb;
			}
	    };
	    try{
			StringBuilder sql = new StringBuilder(); 
			sql.append(SQL_GET_ITEM_ATTB);
			sql.append(" and wal.locale = ? ");
			sql.append(" order by wal.attb_id ");
			return simpleJdbcTemplate.query(sql.toString(), itemAttbMapper, pItemId, pLocale);
	    } catch (EmptyResultDataAccessException erdae) {
			throw new AppRuntimeException(erdae);
		} catch (Exception e) {
			logger.error("Error in getItemAttb - ", e);
			logger.debug("getItemAttb: " + SQL_GET_ITEM_ATTB);
			throw new DAOException(e.getMessage(), e);
		}
	}	
	
	public String getContractMth(String deviceType, String remainMth) throws DAOException {
		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getString("NEW_CONTRACT_MTH");
			}
		};
		try {
			return simpleJdbcTemplate.query(SQL_GET_CONTRACT_MTH, mapper, deviceType, remainMth).toArray(new String[0])[0];
		} catch (EmptyResultDataAccessException erdae) {
			throw new AppRuntimeException(erdae);
		} catch (Exception e) {
			logger.error("Error in getContractMth()", e);
			logger.debug("getContractMth: " + SQL_GET_CONTRACT_MTH);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public String getExtendContractMth(String deviceType, String remainMth) throws DAOException {
		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getString("EXTEND_CONTRACT_PERIOD");
			}
		};
		try {
			return simpleJdbcTemplate.query(SQL_GET_CONTRACT_MTH, mapper, deviceType, remainMth).toArray(new String[0])[0];
		} catch (EmptyResultDataAccessException erdae) {
			throw new AppRuntimeException(erdae);
		} catch (Exception e) {
			logger.error("Error in getContractMth()", e);
			logger.debug("getContractMth: " + SQL_GET_CONTRACT_MTH);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public UpgradeEyeInfoDTO getUpdateEyeInfo(String existSrvType,
			String upgradeDeviceType, String remainMth, boolean isExistStaffPlan, boolean isUpgradeToStaffPlan, String tpCatg) throws DAOException {

		ParameterizedRowMapper<UpgradeEyeInfoDTO> mapper = new ParameterizedRowMapper<UpgradeEyeInfoDTO>() {
			public UpgradeEyeInfoDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				UpgradeEyeInfoDTO upgradeEyeInfo = new UpgradeEyeInfoDTO();
				upgradeEyeInfo.setAdminCharge(rs.getString("ADMIN_CHARGE"));
				upgradeEyeInfo.setAdminChargeItemId(rs.getString("ADMIN_CHARGE_ITEM_ID"));
				upgradeEyeInfo.setContractPeriod(rs.getString("NEW_CONTRACT_MTH"));
				upgradeEyeInfo.setExtendContractPeriod(rs.getString("EXTEND_CONTRACT_PERIOD"));
				upgradeEyeInfo.setReturnDeviceInd(rs.getString("return_device_ind"));
				upgradeEyeInfo.setCancelCharge(rs.getString("cancel_charge"));
				upgradeEyeInfo.setCancelChargeItemId(rs.getString("cancel_charge_item_id"));
				upgradeEyeInfo.setWarningMsg(rs.getString("warning_msg"));
				return upgradeEyeInfo;
			}
		};

		try {
			UpgradeEyeInfoDTO upgradeEyeInfo = simpleJdbcTemplate
					.queryForObject(SQL_GET_UPGRADE_EYE_INFO, mapper,
							upgradeDeviceType, existSrvType, remainMth,
							isExistStaffPlan ? "Y" : "N",
							isUpgradeToStaffPlan ? "Y" : "N", tpCatg);

			return upgradeEyeInfo;
		} catch (EmptyResultDataAccessException erdae) {
			throw new AppRuntimeException(erdae);
		} catch (Exception e) {
			logger.error("Error in getUpdateEyeInfo()", e);
			logger.debug("getUpdateEyeInfo: " + SQL_GET_UPGRADE_EYE_INFO);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	
	public List<ItemDetailDTO> getNowTvMirrItemList( String pItemType, String pLocale, boolean pSelectDefault, String pArpu, String pChannelId) throws DAOException {
		return this.getItem(SQL_GET_NOWTV_MIRR_ITEM_LIST, this.getItemMapper(false, pSelectDefault, false), pLocale, pItemType, LtsBackendConstant.LOB_LTS, pChannelId, LtsBackendConstant.DISPLAY_TYPE_ITEM_SELECT, pLocale, LtsBackendConstant.ITEM_TYPE_EYE_MIRROR, pArpu);
	}
	
	public String getChannelDescription(String pChannelId, String pLocale) throws DAOException {
		
		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getString("CHANNEL_CD") + " " +  rs.getString("HTML");
			}
		};
		try {
			List<String> channelDescList = simpleJdbcTemplate.query(SQL_GET_CHANNEL_DESC, mapper, pChannelId, pLocale);
			
			if (channelDescList.size() == 0) {
				return null;
			}
			return channelDescList.get(0);
		} catch (EmptyResultDataAccessException erdae) {
			return null;
		} catch (Exception e) {
			logger.error("Error in getChannelDescription()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public List<ExclusiveChannelDetailDTO> getExclusiveChannelList(
			List<ChannelGroupDetailDTO> channelGroupList, String locale) throws DAOException {
		
		if (channelGroupList == null || channelGroupList.isEmpty()) {
			return null;
		}
		
		StringBuilder selectedChannel = new StringBuilder();
		
		for (ChannelGroupDetailDTO channelGroup : channelGroupList) {
			if (ArrayUtils.isEmpty(channelGroup.getChannelDetails())) {
				continue;
			}
			for (ChannelDetailDTO channel : channelGroup.getChannelDetails()) {
				if (channel.isSelected()) {
					if (selectedChannel.length() != 0) {
						selectedChannel.append(", ");
					}
					selectedChannel.append(channel.getChannelId());
				}
			}
		}

		if (selectedChannel.length() == 0) {
			return null;
		}
			
		List<ExclusiveChannelDetailDTO> exclusiveChannelList = new ArrayList<ExclusiveChannelDetailDTO>();
		StringBuilder sql = new StringBuilder();
		
		sql.append("SELECT TCE.CHANNEL_ID_A, TCE.CHANNEL_ID_B,");
		sql.append(" TA.CHANNEL_CD CHANNEL_CD_A,");
		sql.append(" TB.CHANNEL_CD CHANNEL_CD_B,");
		sql.append(" TA.CH_DESC CH_DESC_A,");
		sql.append(" TB.CH_DESC CH_DESC_B");
		sql.append(" FROM W_TV_CHANNEL_EXCLUSIVE TCE,");
		sql.append(" (SELECT TC.CHANNEL_ID, TC.CHANNEL_CD, TDL.HTML CH_DESC");
		sql.append(" FROM W_TV_CHANNEL TC, W_TV_DISPLAY_LKUP TDL");
		sql.append(" WHERE TC.CHANNEL_ID = TDL.ID");
		sql.append(" AND TDL.LOCALE = ?");
		sql.append(" AND NVL(TC.EFF_END_DATE,SYSDATE) >= SYSDATE");
		sql.append(" AND TC.EFF_START_DATE <= SYSDATE");
		sql.append(" AND TDL.TYPE = 'CHANNEL') TA,");
		sql.append(" (SELECT TC.CHANNEL_ID, TC.CHANNEL_CD, TDL.HTML CH_DESC");
		sql.append(" FROM W_TV_CHANNEL TC, W_TV_DISPLAY_LKUP TDL");
		sql.append(" WHERE TC.CHANNEL_ID = TDL.ID");
		sql.append(" AND TDL.LOCALE = ?");
		sql.append(" AND NVL(TC.EFF_END_DATE,SYSDATE) >= SYSDATE");
		sql.append(" AND TC.EFF_START_DATE <= SYSDATE");
		sql.append(" AND TDL.TYPE = 'CHANNEL') TB");
		sql.append(" WHERE TA.CHANNEL_ID = TCE.CHANNEL_ID_A");
		sql.append(" AND TB.CHANNEL_ID = TCE.CHANNEL_ID_B");
		sql.append(" AND TCE.CHANNEL_ID_A IN (").append(selectedChannel.toString()).append(")");
		sql.append(" AND TCE.CHANNEL_ID_B IN (").append(selectedChannel.toString()).append(")");
		
		ParameterizedRowMapper<ExclusiveChannelDetailDTO> mapper = new ParameterizedRowMapper<ExclusiveChannelDetailDTO>() {
			public ExclusiveChannelDetailDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				ExclusiveChannelDetailDTO exclusiveChannelDetail = new ExclusiveChannelDetailDTO();
				exclusiveChannelDetail.setChannelAId(rs.getString("CHANNEL_ID_A"));
				exclusiveChannelDetail.setChannelBId(rs.getString("CHANNEL_ID_B"));
				exclusiveChannelDetail.setChannelACd(rs.getString("CHANNEL_CD_A"));
				exclusiveChannelDetail.setChannelBCd(rs.getString("CHANNEL_CD_B"));
				exclusiveChannelDetail.setChannelADesc(rs.getString("CH_DESC_A"));
				exclusiveChannelDetail.setChannelBDesc(rs.getString("CH_DESC_B"));
				return exclusiveChannelDetail;
			}
		};

		try {
			logger.debug("getExclusiveChannelList : " + sql.toString());
			exclusiveChannelList = simpleJdbcTemplate.query(sql.toString(), mapper, locale, locale);
		} catch (EmptyResultDataAccessException erdae) {
			logger.debug("EmptyResultDataAccessException");
			return null;
		} catch (Exception e) {
			logger.debug("Exception caught in getExclusiveChannelList():", e);
			throw new DAOException(e.getMessage(), e);
		}
		
		return exclusiveChannelList;
	}

	public List<ExclusiveItemDetailDTO> getExclusiveItemList(
			List<ItemDetailDTO> itemDetailListA, List<ItemDetailDTO> itemDetailListB, 
			 String locale, boolean isSelectedItemOnly) throws DAOException {
		return getExclusiveItemList(itemDetailListA, itemDetailListB, LtsBackendConstant.DISPLAY_TYPE_ITEM_SELECT, locale, isSelectedItemOnly);
	}

	public List<ExclusiveItemDetailDTO> getExclusiveItemList(
			List<ItemDetailDTO> itemDetailListA, List<ItemDetailDTO> itemDetailListB, 
			String displayType, String locale, boolean isSelectedItemOnly) throws DAOException {
		
		if (itemDetailListA == null || itemDetailListA.isEmpty() 
				|| itemDetailListB == null || itemDetailListB.isEmpty()) {
			return null;
		}
		
		StringBuilder selectedItemA = new StringBuilder();
		
		for (ItemDetailDTO itemDetailA : itemDetailListA) {
			if (itemDetailA.isSelected() || !isSelectedItemOnly) {
				if (selectedItemA.length() != 0) {
					selectedItemA.append(", ");
				}
				selectedItemA.append(itemDetailA.getItemId());
			}
		}
		
		StringBuilder selectedItemB = new StringBuilder();
		
		for (ItemDetailDTO itemDetailB : itemDetailListB) {
			if (itemDetailB.isSelected() || !isSelectedItemOnly) {
				if (selectedItemB.length() != 0) {
					selectedItemB.append(", ");
				}
				selectedItemB.append(itemDetailB.getItemId());
			}
		}
		
		if (selectedItemA.length() == 0 || selectedItemB.length() == 0) {
			return null;
		}

		List<ExclusiveItemDetailDTO> exclusiveItemList = new ArrayList<ExclusiveItemDetailDTO>();
		StringBuilder sql = new StringBuilder();
		
		sql.append("SELECT IELM.ITEM_ID_A ITEM_ID_A, ");
		sql.append("  IELM.ITEM_ID_B ITEM_ID_B,");
		sql.append("  IA.DESCRIPTION ITEM_DESCRIPTION_A,");
		sql.append("  IB.DESCRIPTION ITEM_DESCRIPTION_B");
		sql.append(" FROM W_DIC_ITEM_EXCLUSIVE IELM,");
		sql.append("  (SELECT ITEM_ID ID, HTML DESCRIPTION");
		sql.append("   FROM W_ITEM_DISPLAY_LKUP");
		sql.append("   WHERE DISPLAY_TYPE = ?");
		sql.append("   AND LOCALE = ? ) IA,");
		sql.append("  (SELECT ITEM_ID ID, HTML DESCRIPTION");
		sql.append("   FROM W_ITEM_DISPLAY_LKUP");
		sql.append("   WHERE DISPLAY_TYPE = ?");
		sql.append("   AND LOCALE = ? ) IB");
		sql.append(" WHERE IA.ID = IELM.ITEM_ID_A");
		sql.append(" AND IB.ID = IELM.ITEM_ID_B");
		sql.append(" AND ((IELM.ITEM_ID_A IN (").append(selectedItemA.toString()).append(")");
		sql.append(" AND IELM.ITEM_ID_B IN (").append(selectedItemB.toString()).append("))");
		sql.append(" OR (IELM.ITEM_ID_A IN (").append(selectedItemB.toString()).append(")");
		sql.append(" AND IELM.ITEM_ID_B IN (").append(selectedItemA.toString()).append(")))");
		
		ParameterizedRowMapper<ExclusiveItemDetailDTO> mapper = new ParameterizedRowMapper<ExclusiveItemDetailDTO>() {
			public ExclusiveItemDetailDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				ExclusiveItemDetailDTO exclusiveItemDetail = new ExclusiveItemDetailDTO();
				exclusiveItemDetail.setItemAId(rs.getString("ITEM_ID_A"));
				exclusiveItemDetail.setItemBId(rs.getString("ITEM_ID_B"));
				exclusiveItemDetail.setItemADesc(rs.getString("ITEM_DESCRIPTION_A"));
				exclusiveItemDetail.setItemBDesc(rs.getString("ITEM_DESCRIPTION_B"));
				return exclusiveItemDetail;
			}
		};
		
		try {
			logger.debug("getExclusiveItemList : " + sql.toString());
			exclusiveItemList = simpleJdbcTemplate.query(sql.toString(), mapper, displayType, locale, displayType, locale);
		} catch (EmptyResultDataAccessException erdae) {
			logger.debug("EmptyResultDataAccessException");
			return null;
		} catch (Exception e) {
			logger.debug("Exception caught in getExclusiveItemList():", e);
			throw new DAOException(e.getMessage(), e);
		}
		
		return exclusiveItemList;
	}
	
	public List<ExclusiveItemDetailDTO> getExclusiveItemSetList(
			List<ItemSetDetailDTO> itemSetDetailListA, List<ItemSetDetailDTO> itemSetDetailListB, 
			String displayType, String locale) throws DAOException {
		
		if (itemSetDetailListA == null || itemSetDetailListA.isEmpty() 
				|| itemSetDetailListB == null || itemSetDetailListB.isEmpty()) {
			return null;
		}
		
		StringBuilder selectedItemSetA = new StringBuilder();
		
		for (ItemSetDetailDTO itemSetDetailA : itemSetDetailListA) {
			if (ArrayUtils.isEmpty(itemSetDetailA.getItemDetails())) {
				continue;
			}
			for (ItemDetailDTO itemDetail : itemSetDetailA.getItemDetails()) {
				if (itemDetail.isSelected()) {
					if (selectedItemSetA.length() != 0) {
						selectedItemSetA.append(", ");
					}
					selectedItemSetA.append(itemSetDetailA.getItemSetId());
					break;
				}
			}
		}
		
		StringBuilder selectedItemSetB = new StringBuilder();
		
		for (ItemSetDetailDTO itemSetDetailB : itemSetDetailListB) {
			if (ArrayUtils.isEmpty(itemSetDetailB.getItemDetails())) {
				continue;
			}
			for (ItemDetailDTO itemDetail : itemSetDetailB.getItemDetails()) {
				if (itemDetail.isSelected()) {
					if (selectedItemSetB.length() != 0) {
						selectedItemSetB.append(", ");
					}
					selectedItemSetB.append(itemSetDetailB.getItemSetId());
					break;
				}
			}			
		}
		
		if (selectedItemSetA.length() == 0 || selectedItemSetB.length() == 0) {
			return null;
		}

		List<ExclusiveItemDetailDTO> exclusiveItemList = new ArrayList<ExclusiveItemDetailDTO>();
		StringBuilder sql = new StringBuilder();
		
		sql.append("SELECT exclu.ITEM_SET_ID_A ITEM_SET_ID_A, ");
		sql.append("  exclu.ITEM_SET_ID_B ITEM_SET_ID_B,");
		sql.append("  set_a.SET_DESCRIPTION ITEM_DESCRIPTION_A,");
		sql.append("  set_b.SET_DESCRIPTION ITEM_DESCRIPTION_B");
		sql.append(" FROM W_DIC_ITEM_SET_EXCLUSIVE exclu,");
		sql.append("  (SELECT ITEM_SET_ID, SET_DESCRIPTION");
		sql.append("   FROM W_ITEM_SET ) set_a,");
		sql.append("  (SELECT ITEM_SET_ID, SET_DESCRIPTION");
		sql.append("   FROM W_ITEM_SET ) set_b");
		sql.append(" WHERE set_a.ITEM_SET_ID = exclu.ITEM_SET_ID_A");
		sql.append(" AND set_b.ITEM_SET_ID = exclu.ITEM_SET_ID_B");
		sql.append(" AND ((exclu.ITEM_SET_ID_A IN (").append(selectedItemSetA.toString()).append(")");
		sql.append(" AND exclu.ITEM_SET_ID_B IN (").append(selectedItemSetB.toString()).append("))");
		sql.append(" OR (exclu.ITEM_SET_ID_A IN (").append(selectedItemSetB.toString()).append(")");
		sql.append(" AND exclu.ITEM_SET_ID_B IN (").append(selectedItemSetA.toString()).append(")))");
		
		ParameterizedRowMapper<ExclusiveItemDetailDTO> mapper = new ParameterizedRowMapper<ExclusiveItemDetailDTO>() {
			public ExclusiveItemDetailDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				ExclusiveItemDetailDTO exclusiveItemDetail = new ExclusiveItemDetailDTO();
				exclusiveItemDetail.setItemAId(rs.getString("ITEM_SET_ID_A"));
				exclusiveItemDetail.setItemBId(rs.getString("ITEM_SET_ID_B"));
				exclusiveItemDetail.setItemADesc(rs.getString("ITEM_DESCRIPTION_A"));
				exclusiveItemDetail.setItemBDesc(rs.getString("ITEM_DESCRIPTION_B"));
				return exclusiveItemDetail;
			}
		};
		
		try {
			logger.debug("getExclusiveItemList : " + sql.toString());
			
			MapSqlParameterSource paramMap = new MapSqlParameterSource();
			paramMap.addValue("displayType", displayType);
			paramMap.addValue("locale", locale);
			exclusiveItemList = simpleJdbcTemplate.query(sql.toString(), mapper, paramMap);
		} catch (EmptyResultDataAccessException erdae) {
			logger.debug("EmptyResultDataAccessException");
			return null;
		} catch (Exception e) {
			logger.debug("Exception caught in getExclusiveItemSetList():", e);
			throw new DAOException(e.getMessage(), e);
		}
		
		return exclusiveItemList;
	}
	
	public String getItemContractPeriod(String pItemId) throws DAOException {
		
		try {
			return simpleJdbcTemplate.queryForObject(SQL_GET_ITEM_CONTRACT_PERIOD, String.class, pItemId);
		} catch (EmptyResultDataAccessException erdae) {
			return null;
		} catch (Exception e) {
			logger.error("Error in getItemContractPeriod()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public List<ItemDetailDTO> getDummyBasketItemList(String channelId,
			String itemType, String displayType, String locale,
			boolean selectDefault, String pApplDate) throws DAOException {
		
		String grpId = "DUMMY_BASKET_" + channelId;
		
		StringBuilder sql = new StringBuilder();
		
		sql.append("select wi.id as item_id, wi.lob as item_lob, "); 
		sql.append("wi.type as item_type, widl.description as item_desc,  ");
		sql.append("widl.html, widl.html2, widl.locale, widl.image_path, widl.image_detail_path, ");
		sql.append("wip.onetime_amt, wipdl.onetime_amt_txt, "); 
		sql.append("wip.recurrent_amt, wipdl.recurrent_amt_txt, "); 
		sql.append("wip.mth_to_mth_rate, wipdl.mth_to_mth_rate_txt, "); 
		sql.append("wbia.mdo_ind, wbia.item_set_id,  ");
		sql.append("widrrv.penalty_amt, wipdl.penalty_amt_txt, "); 
		sql.append("widrrv.rebate_amt,  wipdl.rebate_amt_txt, widlts.af_type, widlts.eligible_doc_type  ");
		sql.append("from w_code_lkup wcl,   ");
		sql.append("w_basket_item_assgn wbia,  ");
		sql.append("w_item wi,  ");
		sql.append("w_item_pricing wip, "); 
		sql.append("w_item_display_lkup widl, "); 
		sql.append("w_item_dtl_rp_rb_vas widrrv, (select * from w_item_pricing_display_lkup where locale = ?) wipdl, ");
		sql.append("w_item_dtl_lts widlts ");
		sql.append("where wcl.grp_id = ?  ");
		sql.append("and wcl.code = wbia.basket_id "); 
		sql.append("and (wbia.eff_end_date is null or wbia.eff_end_date >= sysdate) "); 
		sql.append("and wbia.item_id = wi.id  ");
		sql.append("and wi.id = wip.id (+) ");
		sql.append("and wcia.item_id = wi.id "); 
		sql.append("and wcia.channel_id = ?  ");
//		sql.append("and wcia.eff_start_date <= sysdate ");
		sql.append("and widl.item_id = wi.id  ");
		sql.append("and widl.display_type = ?  ");
		sql.append("and wi.lob = 'LTS'  ");
		sql.append("and wi.type = ?  ");
		sql.append("and widl.locale = ?  ");
		sql.append("and wi.id = widrrv.id (+) "); 
		sql.append("and wi.id = wipdl.item_id (+)  ");
		sql.append("and (wipdl.eff_end_date is null or wipdl.eff_end_date >= sysdate) "); 
		sql.append("and (wip.eff_end_date is null or wip.eff_end_date >= sysdate)  ");
		sql.append("and wi.id = widlts.item_id (+) ");
		if (StringUtils.isNotBlank(pApplDate)) {
			sql.append(" and TRUNC(wcia.eff_start_date) <= to_date('" + StringUtils.split(pApplDate, " ")[0] + "', 'dd/MM/yyyy') ");
			sql.append(" and (wcia.eff_end_date is null or TRUNC(wcia.eff_end_date) >= to_date('" + StringUtils.split(pApplDate, " ")[0] + "', 'dd/MM/yyyy') ) "); 
		}
		else {
			sql.append(" and TRUNC(wcia.eff_start_date) <= sysdate ");
			sql.append(" and (wcia.eff_end_date is null or wcia.eff_end_date >= sysdate) "); 
		}
		return getItem(sql.toString(), getItemMapper(false, selectDefault, false), locale, grpId, channelId, displayType, itemType, locale);
	}

	public String getNowtvCredit(String itemId) throws DAOException {
		
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT credit")
			.append(" FROM w_item_dtl_tv ")
			.append(" WHERE item_id = :itemId");
		
		paramSource.addValue("itemId", itemId);
		
		try {
			if (logger.isDebugEnabled()) {
				logger.debug("getNowtvCredit() Sql: " + sql.toString());	
			}
			return (String)simpleJdbcTemplate.getNamedParameterJdbcOperations().queryForObject(sql.toString(), paramSource, String.class);

		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException in getNowtvCredit()");
			return null;
		} catch (Exception e) {
			logger.info("Exception caught in getNowtvCredit():", e);
			throw new DAOException(e.getMessage(), e);
		}

	}
	
	public String getItemEligibleCardPrefix(String itemId) throws DAOException {
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT eligible_card_prefix")
			.append(" FROM w_item_dtl_lts ")
			.append(" WHERE item_id = :itemId");
		
		paramSource.addValue("itemId", itemId);
		
		try {
			if (logger.isDebugEnabled()) {
				logger.debug("getItemEligibleCardPrefix() Sql: " + sql.toString());	
			}
			return (String)simpleJdbcTemplate.getNamedParameterJdbcOperations().queryForObject(sql.toString(), paramSource, String.class);

		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException in getItemEligibleCardPrefix()");
			return null;
		} catch (Exception e) {
			logger.info("Exception caught in getItemEligibleCardPrefix():", e);
			throw new DAOException(e.getMessage(), e);
		}
		
	}
	
	public List<String> getItemSpecPath(List<String> itemIdList) throws DAOException {
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT RPT_NAME ")
			.append(" FROM W_ITEM_REPORT_ASSGN ")
			.append(" WHERE RPT_TYPE = 'ITEM_SPEC' ")
			.append(" AND ITEM_ID in (:item_Ids) ")
			.append(" ORDER BY ITEM_ID, RPT_SEQ ");
		
		paramSource.addValue("item_Ids", itemIdList);
		
		try {
			if (logger.isDebugEnabled()) {
				logger.debug("getItemSpecPath() Sql: " + sql.toString());	
			}
			return (List<String>)simpleJdbcTemplate.getNamedParameterJdbcOperations().queryForList(sql.toString(), paramSource, String.class);

		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException in getItemSpecPath()");
			return null;
		} catch (Exception e) {
			logger.info("Exception caught in getItemSpecPath():", e);
			throw new DAOException(e.getMessage(), e);
		}
		
	}
	
	public List<String> getBasketProductSpecPath(String basketId) throws DAOException {
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT rpt_name ")
			.append(" FROM w_basket_report_assgn ")
			.append(" WHERE rpt_type = 'PRODUCT_SPEC' ")
			.append(" AND base_basket_id in ( ")
			.append("  SELECT base_basket_id ")
			.append("   FROM w_basket WHERE id = :basketId )");
		
		paramSource.addValue("basketId", basketId);
		
		try {
			if (logger.isDebugEnabled()) {
				logger.debug("getBasketProductSpecPath() Sql: " + sql.toString());	
			}
			return (List<String>)simpleJdbcTemplate.getNamedParameterJdbcOperations().queryForList(sql.toString(), paramSource, String.class);

		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException in getBasketProductSpecPath()");
			return null;
		} catch (Exception e) {
			logger.info("Exception caught in getBasketProductSpecPath():", e);
			throw new DAOException(e.getMessage(), e);
		}
		
	}
	//Smart Warranty Set assign
	public List<String> getSmartWarrantyPath(String orderId) throws DAOException {
			MapSqlParameterSource paramSource = new MapSqlParameterSource();
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT item_type ")
				.append(" FROM bomweb_subc_item_lts ")
				.append(" WHERE item_type = 'S-WARRANTY' ")
				.append(" AND order_id = :orderId");
			
			paramSource.addValue("orderId", orderId);
			
			try {
				if (logger.isDebugEnabled()) {
					logger.debug("getBasketProductSpecPath() Sql: " + sql.toString());	
				}
				return (List<String>)simpleJdbcTemplate.getNamedParameterJdbcOperations().queryForList(sql.toString(), paramSource, String.class);

			} catch (EmptyResultDataAccessException erdae) {
				logger.info("EmptyResultDataAccessException in getBasketProductSpecPath()");
				return null;
			} catch (Exception e) {
				logger.info("Exception caught in getBasketProductSpecPath():", e);
				throw new DAOException(e.getMessage(), e);
			}
			
	}
		
	//get by w_item_attb_assgn
	public List<ItemAttbDTO> getItemAttbByItemAttbAssign(String pItemId) throws DAOException {
	    ParameterizedRowMapper<ItemAttbDTO> itemAttbMapper = new ParameterizedRowMapper<ItemAttbDTO>() {
			public ItemAttbDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				ItemAttbDTO itemAttb = new ItemAttbDTO();
				itemAttb.setAttbID(rs.getString("ATTB_ID"));
				itemAttb.setAttbDesc(rs.getString("ATTB_DESC"));
				itemAttb.setInputMethod(rs.getString("INPUT_METHOD"));
				itemAttb.setInputFormat(rs.getString("INPUT_FORMAT"));
				itemAttb.setMinLength(rs.getInt("MIN_LENGTH"));
				itemAttb.setMaxLength(rs.getInt("MAX_LENGTH"));
				itemAttb.setDefaultValue(rs.getString("DEFAULT_VALUE"));
				itemAttb.setAttbInfoKey(rs.getString("ATTB_INFO_KEY"));
				itemAttb.setVisualInd(rs.getString("VISUAL_IND"));
				
				if (StringUtils.isNotEmpty(itemAttb.getDefaultValue())) {
					itemAttb.setAttbValue(itemAttb.getDefaultValue());
				}
				
				return itemAttb;
			}
	    };
	    try{
			return simpleJdbcTemplate.query(SQL_GET_ITEM_ATTB_BY_ITEM_ATTB_ASSIGN, itemAttbMapper, pItemId);
	    } catch (EmptyResultDataAccessException erdae) {
			throw new AppRuntimeException(erdae);
		} catch (Exception e) {
			logger.error("Error in getItemAttb - ", e);
			logger.debug("getItemAttb: " + SQL_GET_ITEM_ATTB_BY_ITEM_ATTB_ASSIGN);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
    public Map<String, String> getBundleTvMap()  throws DAOException {
	    
	    try{
	    	 List<Map<String, Object>> results = simpleJdbcTemplate.queryForList(SQL_GET_BUNDLETV_MAP);
	    	 Map<String, String> bundleTvMap = new HashMap<String, String>();
	    	 for(int i = 0; i<results.size(); i++){
	    		 results.get(i).get("pack_psef_cd");
	    		 results.get(i).get("tv_channel_num");
	    		 bundleTvMap.put(results.get(i).get("pack_psef_cd").toString(),  results.get(i).get("tv_channel_num").toString());
	    	 }
	    	 
	    	return bundleTvMap;
	    	
	    } catch (EmptyResultDataAccessException erdae) {
			throw new AppRuntimeException(erdae);
		} catch (Exception e) {
			logger.error("Error in getBundleTvList - ", e);
			logger.debug("getBundleTvList: " + SQL_GET_BUNDLETV_MAP);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public ItemSetDetailDTO getGiftItemSetDetail(String pGiftCd, String locale, String channelId, String pApplDate) throws DAOException {
		return getGiftItemSetDetail( pGiftCd, locale, channelId, LtsBackendConstant.DISPLAY_TYPE_ITEM_SELECT, pApplDate, null, null, null);
	}
	
	public ItemSetDetailDTO getGiftItemSetDetail(String pGiftCd, String locale, String channelId, String displayType, String pApplDate) throws DAOException {
		return getGiftItemSetDetail( pGiftCd, locale, channelId, displayType, pApplDate, null, null, null);
	}
		
	public ItemSetDetailDTO getGiftItemSetDetail(String pGiftCd, String locale, String channelId, String displayType, String pApplDate, String basketId, String teamCd, String srvBdry) throws DAOException {

		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		
		StringBuffer getItemSetListSql = new StringBuffer();
		getItemSetListSql.append("select wis.item_set_id, wis.item_set_type, wis.set_description, ");
		getItemSetListSql.append("wisdl.html as display_html, wisdl.description as display_desc , ");
		getItemSetListSql.append("wis.item_set_cd, wis.default_ind, wis.max_qty, wis.min_qty  ");
		getItemSetListSql.append("from w_item_set wis, w_item_set_display_lkup wisdl, w_item_set_attb wisa ");
		if(StringUtils.isNotBlank(basketId)){
			getItemSetListSql.append(", W_BASKET_ITEM_SET_ASSGN wbisa ");
		}
		getItemSetListSql.append("where wis.item_set_type = 'PREM-GIFT' ");
		getItemSetListSql.append("and wis.item_set_id = wisdl.item_set_id ");
		getItemSetListSql.append("and wis.item_set_id = wisa.item_set_id ");
		getItemSetListSql.append("and wisa.attb_cd = 'GIFT_CODE' ");
		getItemSetListSql.append("and wisdl.locale = :locale ");
		getItemSetListSql.append("and wisdl.display_type = :displayType ");
		getItemSetListSql.append("and wisa.attb_value = :giftCd ");
		paramSource.addValue("locale", locale);
		paramSource.addValue("displayType", displayType);
		paramSource.addValue("giftCd", pGiftCd);
		if(StringUtils.isNotBlank(basketId)){
			getItemSetListSql.append(" AND wis.item_set_id = wbisa.item_set_id ");
			getItemSetListSql.append(" AND (wbisa.basket_id = :basketId OR wbisa.basket_id = (select base_basket_id From w_basket where id = :basketId)) ");
			paramSource.addValue("basketId", basketId);
		}
		if(StringUtils.isNotBlank(teamCd)){
			getItemSetListSql.append(" AND (wis.eligible_team like :likeTeamCd OR wis.eligible_team IS NULL) ");
			paramSource.addValue("likeTeamCd", "%" + teamCd + "%");
		}
		if(StringUtils.isNotBlank(srvBdry)){
			getItemSetListSql.append(" AND (UPPER(WIS.HOUSING_TYPE) in (select UPPER(HOUSING_TYPE) from W_LTS_HOUSING_SRV_BOUNDARY where SERVICE_BOUNDARY = :srvBoundary) ");
			getItemSetListSql.append(" 		OR WIS.HOUSING_TYPE IS NULL) ");
			paramSource.addValue("srvBoundary", srvBdry);
		}
		
	    ParameterizedRowMapper<ItemSetDetailDTO> itemSetMapper = new ParameterizedRowMapper<ItemSetDetailDTO>() {
	    	public ItemSetDetailDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
	    		ItemSetDetailDTO itemSetDetail = new ItemSetDetailDTO();
	    		
	    		itemSetDetail.setItemSetId(rs.getString("ITEM_SET_ID"));
	    		itemSetDetail.setItemSetType(rs.getString("ITEM_SET_TYPE"));
	    		itemSetDetail.setItemSetDesc(rs.getString("SET_DESCRIPTION"));
	    		itemSetDetail.setItemSetCode(rs.getString("ITEM_SET_CD"));
	    		itemSetDetail.setDefaultInd(StringUtils.equals(rs.getString("DEFAULT_IND"), "Y") );
	    		itemSetDetail.setDisplayHtml(rs.getString("DISPLAY_HTML"));
	    		itemSetDetail.setDisplayDesc(rs.getString("DISPLAY_DESC"));
	    		String maxQty = rs.getString("MAX_QTY");
	    		if (StringUtils.isNotBlank(maxQty)) {
	    			itemSetDetail.setMaxQty(Integer.parseInt(maxQty));
	    		}
	    		
	    		String minQty = rs.getString("MIN_QTY");
	    		if (StringUtils.isNotBlank(minQty)) {
	    			itemSetDetail.setMinQty(Integer.parseInt(minQty));
	    		}
	    		return itemSetDetail;
	    	}
	    };
		
	    try{
	    	List<ItemSetDetailDTO> itemSetDetailList = null;
	    	itemSetDetailList = simpleJdbcTemplate.query(getItemSetListSql.toString(), itemSetMapper, paramSource);
	    	
	    	if (itemSetDetailList == null || itemSetDetailList.isEmpty()) {
	    		return null;
	    	}
	    	
	    	ItemSetDetailDTO giftItemSet = null;
	    	List<ItemDetailDTO> itemDetailList = null;
	    	for(ItemSetDetailDTO giftItemSetTmp : itemSetDetailList){
		    	itemDetailList = this.getSetItemList(giftItemSetTmp.getItemSetId(), locale, true, channelId, displayType, pApplDate, null);
		    	if(itemDetailList == null || itemDetailList.isEmpty()){
		    		continue;
		    	}
	    		giftItemSet = giftItemSetTmp;
	    		break;
	    	}
	    	
	    	if (giftItemSet == null || itemDetailList == null || itemDetailList.isEmpty()) {
	    		return null;
	    	}
	    	
	    	giftItemSet.setItemDetails(itemDetailList.toArray(new ItemDetailDTO[itemDetailList.size()]));
	    	
	    	List<ItemSetAttbDTO> itemSetAttbList = this.getSetAttbList(itemSetDetailList.get(0).getItemSetId());
	    	
	    	if (itemSetAttbList != null && !itemSetAttbList.isEmpty()) {
	    		giftItemSet.setItemSetAttbs(itemSetAttbList.toArray(new ItemSetAttbDTO[itemSetAttbList.size()]));
	    	}
	    	
	    	return giftItemSet;
	    } catch (EmptyResultDataAccessException erdae) {
	    	return null;
		} catch (Exception e) {
			logger.error("Error in getSetAttbList - ", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public List<ItemSetDetailDTO> getPcdBundleGiftItemSetDetail(String pPcdSbid, String locale, String channelId, String pApplDate, String basketId, String teamCd, String srvBdry) throws DAOException {
		return getPcdBundleGiftItemSetDetail( pPcdSbid, locale, channelId, LtsBackendConstant.DISPLAY_TYPE_ITEM_SELECT, pApplDate, basketId, teamCd, srvBdry);
	}
	
	public List<ItemSetDetailDTO> getPcdBundleGiftItemSetDetail(String pPcdSbid, String locale, String channelId, String displayType, String pApplDate, String basketId, String teamCd, String srvBdry) throws DAOException {
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		
		StringBuffer getItemSetListSql = new StringBuffer();
		getItemSetListSql.append("select wis.item_set_id, wis.item_set_type, wis.set_description, ");
		getItemSetListSql.append("wisdl.html as display_html, wisdl.description as display_desc , ");
		getItemSetListSql.append("wis.item_set_cd, wis.default_ind, wis.max_qty, wis.min_qty  ");
		getItemSetListSql.append("from w_item_set wis, w_item_set_display_lkup wisdl, w_item_set_attb wisa, w_basket_item_set_assgn wbisa ");
		getItemSetListSql.append("where wis.item_set_type = 'PREM-PCD' ");
		getItemSetListSql.append("and wis.item_set_id = wisdl.item_set_id ");
		getItemSetListSql.append("and wis.item_set_id = wisa.item_set_id ");
		getItemSetListSql.append("and wisa.attb_cd = 'NEW_PCD' ");
		getItemSetListSql.append("and wisdl.locale = :locale ");
		getItemSetListSql.append("and wisdl.display_type = :displayType ");
		getItemSetListSql.append("and wisa.attb_value = :pcdSbid ");
		getItemSetListSql.append("and wis.item_set_id = wbisa.item_set_id ");
		getItemSetListSql.append("and wbisa.basket_id in ( :basketId , (select base_basket_id from w_basket where id = :basketId )) ");

		paramSource.addValue("locale", locale);
		paramSource.addValue("displayType", displayType);
		paramSource.addValue("pcdSbid", pPcdSbid);
		paramSource.addValue("basketId", basketId);
		
		if(StringUtils.isNotBlank(teamCd)){
			getItemSetListSql.append(" AND (wis.eligible_team like :likeTeamCd OR wis.eligible_team IS NULL) ");
			paramSource.addValue("likeTeamCd", "%" + teamCd + "%");
		}
		if(StringUtils.isNotBlank(srvBdry)){
			getItemSetListSql.append(" AND (UPPER(WIS.HOUSING_TYPE) in (select UPPER(HOUSING_TYPE) from W_LTS_HOUSING_SRV_BOUNDARY where SERVICE_BOUNDARY = :srvBoundary) ");
			getItemSetListSql.append(" 		OR WIS.HOUSING_TYPE IS NULL) ");
			paramSource.addValue("srvBoundary", srvBdry);
		}
		
		
	    ParameterizedRowMapper<ItemSetDetailDTO> itemSetMapper = new ParameterizedRowMapper<ItemSetDetailDTO>() {
	    	public ItemSetDetailDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
	    		ItemSetDetailDTO itemSetDetail = new ItemSetDetailDTO();
	    		
	    		itemSetDetail.setItemSetId(rs.getString("ITEM_SET_ID"));
	    		itemSetDetail.setItemSetType(rs.getString("ITEM_SET_TYPE"));
	    		itemSetDetail.setItemSetDesc(rs.getString("SET_DESCRIPTION"));
	    		itemSetDetail.setItemSetCode(rs.getString("ITEM_SET_CD"));
	    		itemSetDetail.setDefaultInd(StringUtils.equals(rs.getString("DEFAULT_IND"), "Y") );
	    		itemSetDetail.setDisplayHtml(rs.getString("DISPLAY_HTML"));
	    		itemSetDetail.setDisplayDesc(rs.getString("DISPLAY_DESC"));
	    		String maxQty = rs.getString("MAX_QTY");
	    		if (StringUtils.isNotBlank(maxQty)) {
	    			itemSetDetail.setMaxQty(Integer.parseInt(maxQty));
	    		}
	    		
	    		String minQty = rs.getString("MIN_QTY");
	    		if (StringUtils.isNotBlank(minQty)) {
	    			itemSetDetail.setMinQty(Integer.parseInt(minQty));
	    		}
	    		return itemSetDetail;
	    	}
	    };
		
	    try{
	    	List<ItemSetDetailDTO> itemSetDetailList = simpleJdbcTemplate.query(getItemSetListSql.toString(), itemSetMapper, paramSource);
	    	
	    	if (itemSetDetailList == null || itemSetDetailList.isEmpty()) {
	    		return null;
	    	}
	    	
	    	String tempItemSetIds = "";
	    	for (int i=0; i<itemSetDetailList.size(); i++)
	    	{
	    		
	    		List<ItemDetailDTO> itemDetailList = this.getSetItemList(itemSetDetailList.get(i).getItemSetId(), locale, true, channelId, displayType, pApplDate, null);
		    	
		    	itemSetDetailList.get(i).setItemDetails(itemDetailList.toArray(new ItemDetailDTO[itemDetailList.size()]));
		    	
		    	List<ItemSetAttbDTO> itemSetAttbList = this.getSetAttbList(itemSetDetailList.get(i).getItemSetId());
		    	
		    	if (itemSetAttbList != null && !itemSetAttbList.isEmpty()) {
		    		itemSetDetailList.get(i).setItemSetAttbs(itemSetAttbList.toArray(new ItemSetAttbDTO[itemSetAttbList.size()]));
		    	}
		    			    	
	    	}
	    		    	
	    	return itemSetDetailList;
	    	
	    } catch (EmptyResultDataAccessException erdae) {
	    	return null;
		} catch (Exception e) {
			logger.error("Error in getSetAttbList - ", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
		
    public Set<String> getNowTvSet() throws DAOException {
	    
	    try{
	    	 List<Map<String, Object>> results = simpleJdbcTemplate.queryForList(SQL_GET_NOWTV_SET);
	    	 Set<String> nowTvSet =  new HashSet<String>();
	    	 for(int i = 0; i<results.size(); i++){
	    		 results.get(i).get("psef_cd");
	    		 nowTvSet.add(results.get(i).get("psef_cd").toString());
	    	 }
	    	 
	    	return nowTvSet;
	    	
	    } catch (EmptyResultDataAccessException erdae) {
			throw new AppRuntimeException(erdae);
		} catch (Exception e) {
			logger.error("Error in getNowTvSet - ", e);
			logger.debug("getNowTvSet: " + SQL_GET_NOWTV_SET);
			throw new DAOException(e.getMessage(), e);
		}
	}
    
    public String getItemDisplayDesc(String itemId, String locale) throws DAOException{
	    try{
	    	String desc = simpleJdbcTemplate.queryForObject(SQL_GET_ITEM_DISPLAY_DESC, String.class, itemId, locale);	
	    	return desc;
	    } catch (EmptyResultDataAccessException erdae) {
			throw new AppRuntimeException(erdae);
		} catch (Exception e) {
			logger.error("Error in getItemDisplayDesc - ", e);
			logger.debug("getItemDisplayDesc: " + SQL_GET_ITEM_DISPLAY_DESC);
			throw new DAOException(e.getMessage(), e);
		}
    }
    
    public List<String> getOsType(List<String> pOfferCdList, boolean isBasketLevel) throws DAOException {
		ParameterizedRowMapper<String> rowMapper = new ParameterizedRowMapper<String>() {
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getString("os_type");
			}			
		};
		
		StringBuilder sql = new StringBuilder();
		sql.append("select distinct os_type from w_feature_os_lkup ");
		sql.append("where psef_cd in (:psef_cd) ");
		
		if (isBasketLevel) {
			sql.append("and os_type <> 'ALL' "); //Basket level exclude 'ALL' os_type
		}
		
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue("psef_cd", pOfferCdList);
		
		try {
			return simpleJdbcTemplate.query(sql.toString(), rowMapper, paramSource);	
		}
		catch (EmptyResultDataAccessException erdae) {
			return null;
		} catch (Exception e) {
			logger.error("Error in getOsTypeList - ", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
    
    public String getOsTypeChangePenalty(List<String> pOfferCdList) throws DAOException {		
		StringBuilder sql = new StringBuilder();
		sql.append("select penalty from w_feature_os_lkup ");
		sql.append("where psef_cd in (:psef_cd) ");
		sql.append("and penalty is not null ");
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue("psef_cd", pOfferCdList);
		
		try {
			return simpleJdbcTemplate.queryForObject(sql.toString(), String.class, paramSource);	
		}
		catch (EmptyResultDataAccessException erdae) {
			return null;
		}
		catch (IncorrectResultSizeDataAccessException irsdae){
			logger.error("Error in getOsTypeChangePenalty - ", irsdae);
			throw new DAOException(irsdae.getMessage(), irsdae);
		}
		catch (Exception e) {
			logger.error("Error in getOsTypeChangePenalty - ", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
}
