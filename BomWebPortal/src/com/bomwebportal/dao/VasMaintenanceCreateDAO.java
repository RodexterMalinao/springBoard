package com.bomwebportal.dao;

import java.sql.Types;
import java.util.Map;

import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;

import com.bomwebportal.dto.WCreateItemDTO;
import com.bomwebportal.dto.WCreateItemDtlDTO;
import com.bomwebportal.exception.DAOException;

public class VasMaintenanceCreateDAO extends BaseDAO {
	
	//insertion for W_CREATE_ITEM
	
	public int insertWCreateItem(WCreateItemDTO dto)
					throws DAOException {
		String sql = "insert into W_CREATE_ITEM" +
				" (BATCH_NO, SEQ, MTH_TO_MTH_RATE, OFFER_TYPE" +
				", OFFER_ID, OFFER_CD, OFFER_DESC, OFFER_SELECT_QTY, OFFER_SUB_ID" +
				", PROD_TYPE, PROD_ID, PROD_CD, PROD_DESC, PROD_SELECT_QTY" +
				", CREATE_BY, CREATE_DATE, LAST_UPD_BY" +
				", LAST_UPD_DATE, POS_ITEM_CD, MIP_BRAND, MIP_SIM_TYPE, OFFER_BRAND, OFFER_SIM_TYPE, PROD_BRAND, PROD_SIM_TYPE)" +
				" values" +
				" (:batchNo, :seq, :mth2MthRate, :offerType" +
				", :offerId, :offerCd, :offerDesc, :offerSelectQty, :offerSubId" +
				", :prodType, :prodId, :prodCd, :prodDesc, :prodSelectQty" +
				", 'VAS' || TO_CHAR(sysdate, 'YYMMDD'), trunc(sysdate), 'VAS' || TO_CHAR(sysdate, 'YYMMDD')" +
				", trunc(sysdate), :posItemCd, :mipBrand, :mipSimType, :offerBrand, :offerSimType, :prodBrand, :prodSimType)";
		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("batchNo", dto.getBatchNo());
			params.addValue("seq", dto.getSeq());
			params.addValue("mth2MthRate", dto.getMth2MthRate());
			params.addValue("offerType", dto.getOfferType());
			params.addValue("offerId", dto.getOfferId());
			params.addValue("offerCd", dto.getOfferCd());
			params.addValue("offerDesc", dto.getOfferDesc());
			params.addValue("offerSelectQty", dto.getOfferSelectQty());
			params.addValue("offerSubId", dto.getOfferSubId());
			params.addValue("prodType", dto.getProdType());
			params.addValue("prodId", dto.getProdId());
			params.addValue("prodCd", dto.getProdCd());
			params.addValue("prodDesc", dto.getProdDesc());
			params.addValue("prodSelectQty", dto.getProdSelectQty());
			params.addValue("posItemCd", dto.getPosItemCd());
			params.addValue("mipBrand", dto.getMipBrand());
			params.addValue("mipSimType",dto.getMipSimType());
			params.addValue("offerBrand", dto.getOfferBrand());
			params.addValue("offerSimType",dto.getOfferSimType());
			params.addValue("prodBrand", dto.getProdBrand());
			params.addValue("prodSimType",dto.getProdSimType());
			return simpleJdbcTemplate.update(sql, params);
		} catch (Exception e) {
			logger.info("Exception caught in insertWCreateItem():", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	//insertion for W_CREATE_ITEM_DTL
	
	public int insertWCreateItemDtl(WCreateItemDtlDTO dto) throws DAOException{
		String sql = "insert into W_CREATE_ITEM_DTL" +
				" (batch_no, type, contract_period, onetime_amt" +
				", recurrent_amt, display_eng, display_chi, display_ss_form_eng, display_ss_form_chi" +
				", create_by, create_date" +
				", last_upd_by, last_upd_date, item_desc, eff_start_date, mip_brand, mip_sim_type)" +
				" values" +
				" (:batchNo, :type, null, :oneTimeAmt" +
				", :recurrentAmt, :displayEng, :displayChi, :displaySSEng, :displaySSChi" +
				", 'VAS' || TO_CHAR(sysdate, 'YYMMDD'), trunc(sysdate)" +
				", 'VAS' || TO_CHAR(sysdate, 'YYMMDD'), trunc(sysdate), :itemDesc, to_date(:effStartDate, 'DD/MM/YYYY'), :mipBrand, :mipSimType)";
		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("batchNo", dto.getBatchNo());
			params.addValue("type", dto.getType());
			params.addValue("recurrentAmt", dto.getRecurrentAmt());
			params.addValue("displayEng", dto.getDisplayEng());
			params.addValue("displayChi", dto.getDisplayChi());
			params.addValue("displaySSEng", dto.getDisplaySSEng());
			params.addValue("displaySSChi", dto.getDisplaySSChi());
			params.addValue("itemDesc", dto.getItemDesc());
			params.addValue("oneTimeAmt", dto.getOneTimeAmt());
			params.addValue("effStartDate", dto.getEffStartDate());
			params.addValue("mipBrand", dto.getMipBrand());
			params.addValue("mipSimType",dto.getMipSimType());
			return simpleJdbcTemplate.update(sql, params);
		} catch (Exception e) {
			logger.info("Exception caught in insertWCreateItemDtl():", e);
			throw new DAOException(e.getMessage(), e);
		}
		
	}
	
	public Long createNewVasItemM(String batchNo) throws DAOException{
		if (logger.isInfoEnabled()) {
			logger.info("createNewVasItemM() is called");
			logger.info("batchId: " + batchNo);
		}
		try{
			SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
				.withSchemaName("OPS$CNM")
				.withCatalogName("BOMWEB_ITEM_PKG_W")
				.withFunctionName("CREATE_NEW_VAS_ITEM_M"); 
			jdbcCall.setAccessCallParameterMetaData(false);
			jdbcCall.declareParameters(
					new SqlOutParameter("RESULT", Types.BIGINT)
					, new SqlParameter("iv_batch_no", Types.VARCHAR)
					//new SqlParameter("iv_item_type", Types.VARCHAR)
					, new SqlOutParameter("on_retval", Types.INTEGER)
					, new SqlOutParameter("on_errcode", Types.INTEGER)
					,new SqlOutParameter("ov_errtext", Types.VARCHAR));
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("iv_batch_no", batchNo);
			//params.addValue("iv_item_type", itemType);
		
			Map<String, Object> out = jdbcCall.execute(params);
			Long result = (Long) out.get("RESULT");
			Integer onRetval = (Integer) out.get("on_retval");
			Integer onErrcode = (Integer) out.get("on_errcode");
			String ovErrtext = (String) out.get("ov_errtext");
			if (logger.isInfoEnabled()) {
				logger.info("result: " + result);
				logger.info("onRetval: " + onRetval);
				logger.info("onErrcode: " + onErrcode);
				logger.info("ovErrtext: " + ovErrtext);
			}
			return result;
		} catch (Exception e) {
			logger.error("Exception caught in createNewVasItemM()", e);
			throw new DAOException(e);
		}
	}
	
//	// "<span class=\"item_title_vas\">" + itemDisplayEng + "</span>"
//	public int updateWItemDisplayLkupEng(String itemId, String html) throws DAOException {
//		if (logger.isInfoEnabled()) {
//			logger.info("itemId: " + itemId + ", html: " + html);
//		}
//		//String concatenatedHtml = "<span class="item_title_vas">" + itemDisplayEng + "</span>";
//		
//		String sql = "update W_ITEM_DISPLAY_LKUP" +
//				" set html = :html" +
//				" where display_type = 'ITEM_SELECT'" +
//				" and locale = 'en'" +
//				" and item_id = :itemId";
//		try {
//			MapSqlParameterSource params = new MapSqlParameterSource();
//			params.addValue("itemId", itemId);
//			params.addValue("html", html);
//			return this.simpleJdbcTemplate.update(sql, params);
//		} catch (Exception e){
//			logger.info("Exception caught in updateWItemDisplayLkupEng():", e);
//			throw new DAOException(e.getMessage(), e);
//		}
//	}
	//update rebate info in  w_item_dtl_rp_rb_vas
		public int updateRpRbVasDtl(WCreateItemDtlDTO dto) throws DAOException{
			String sql = "update w_item_dtl_rp_rb_vas " +
					" set rebate_schedule = :rebate_schedule, rebate_schedule_chi = :rebate_schedule_chi, rebate_amt = :rebate_amt " +
					" where id = :item_id ";
			try {
				MapSqlParameterSource params = new MapSqlParameterSource();

				params.addValue("rebate_amt", dto.getRebateAmt());
				params.addValue("item_id", dto.getItemId());
				if(dto.getRebateDisplayChi() ==""){
				    
				    params.addValue("rebate_schedule_chi", dto.getDisplaySSChi());
				}
				else{
					params.addValue("rebate_schedule_chi", dto.getRebateDisplayChi());
				}
				
				if(dto.getRebateDisplayEng() ==""){
			
				    params.addValue("rebate_schedule", dto.getDisplaySSEng());
				}
				else{
					params.addValue("rebate_schedule", dto.getRebateDisplayEng());
				}
				logger.info("updateRpRbVasDtl() is called" +sql);
				
				return simpleJdbcTemplate.update(sql, params);
			} catch (Exception e) {
				logger.info("Exception caught in updateRpRbVasDtl():", e);
				throw new DAOException(e.getMessage(), e);
			}
			
		}
		
		public int updateOnlineInd(WCreateItemDtlDTO dto) throws DAOException{
			String sql = "update w_item " +
					" set online_ind='Y' " +
					" where id = :item_id ";
			
			
			try {
				MapSqlParameterSource params = new MapSqlParameterSource();

				params.addValue("item_id", dto.getItemId());
				logger.info("updateOnlineInd() is called" +sql);
				
				return simpleJdbcTemplate.update(sql, params);
			} catch (Exception e) {
				logger.info("Exception caught in updateOnlineInd():", e);
				throw new DAOException(e.getMessage(), e);
			}
			
		}
}
