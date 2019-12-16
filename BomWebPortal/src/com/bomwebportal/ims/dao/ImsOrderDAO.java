package com.bomwebportal.ims.dao;



import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import oracle.jdbc.driver.OracleTypes;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.openuri.www.CustomerBasicInfoDTO;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.datasource.DataSourceUtils;

import com.bomwebportal.dao.BaseDAO;
import com.bomwebportal.dto.AllOrdDocAssgnDTO;
import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.dto.AllOrdDocAssgnDTO.CollectedInd;
import com.bomwebportal.dto.OrderDTO.CollectMethod;
import com.bomwebportal.dto.OrderDTO.DisMode;
import com.bomwebportal.dto.OrderDTO.EsigEmailLang;
import com.bomwebportal.dto.VasParmDTO;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.ims.dao.ImsOrderDAO.Mapper1;
import com.bomwebportal.ims.dao.ImsOrderDAO.Mapper2;
import com.bomwebportal.ims.dto.AcctDTO;
import com.bomwebportal.ims.dto.AddrInventoryDTO;
import com.bomwebportal.ims.dto.AppointmentDTO;
import com.bomwebportal.ims.dto.BomwebBillingAddrDTO;
import com.bomwebportal.ims.dto.BomwebOrderServiceImsDTO;
import com.bomwebportal.dto.BomwebSubscribedOfferImsDTO;
import com.bomwebportal.ims.dto.BomwebThirdPartyApplnDTO;
import com.bomwebportal.ims.dto.CancelOrderDTO;
import com.bomwebportal.ims.dto.ComponentDTO;
import com.bomwebportal.ims.dto.ContactDTO;
import com.bomwebportal.ims.dto.CustAddrDTO;
import com.bomwebportal.ims.dto.CustOptoutInfoDTO;
import com.bomwebportal.ims.dto.CustomerDTO;
import com.bomwebportal.ims.dto.ImsAllOrdDocAssgnDTO;
import com.bomwebportal.ims.dto.IOOfferOTCSchemeDTO;
import com.bomwebportal.ims.dto.ImsCollectDocDTO;
import com.bomwebportal.ims.dto.NtvReplaceSelfPickHddDTO;
import com.bomwebportal.ims.dto.OrderDTO;
import com.bomwebportal.ims.dto.OrderImsDTO;
import com.bomwebportal.ims.dto.OrderImsSystemFindingDTO;
import com.bomwebportal.ims.dto.PaymentDTO;
import com.bomwebportal.ims.dto.RemarkDTO;
import com.bomwebportal.ims.dto.SalesReferrerDTO;
import com.bomwebportal.ims.dto.SubscItemDiscDTO;
import com.bomwebportal.ims.dto.SubscribedChannelDTO;
import com.bomwebportal.ims.dto.SubscribedCslOfferDTO;
import com.bomwebportal.ims.dto.SubscribedItemDTO;
import com.bomwebportal.ims.dto.VimBundleProfileDTO;
import com.bomwebportal.ims.dto.ui.CustomerUI;
import com.bomwebportal.ims.dto.ui.OrderImsUI;
import com.bomwebportal.ims.dto.ui.SubscribedChannelUI;
import com.bomwebportal.ims.dto.ui.SubscribedItemUI;
import com.bomwebportal.util.Utility;
import com.pccw.util.db.OracleSpHelper;
import com.pccw.util.db.stringOracleType.OraArrayNumber;
import com.pccw.util.db.stringOracleType.OraArrayVarChar2;
import com.pccw.util.spring.SpringApplicationContext;
import com.bomwebportal.ims.dto.BomwebOrderL2JobDTO;
import com.google.gson.Gson;

public class ImsOrderDAO extends BaseDAO{

	private Gson gson = new Gson();
	private static final String ORACLE_NUM_ARRAY = "OPS$CNM.TABLE_VARCHAR2";
	
	public List<NtvReplaceSelfPickHddDTO> getReplaceSelfPickHddLkup(){
		List<NtvReplaceSelfPickHddDTO> result  = new ArrayList<NtvReplaceSelfPickHddDTO>();	
		String SQL=" select b.id, b.type, c.prod_cd, c.prod_cd OfferCode, 0 bw , " +
				" replace_item.id replace_id,  replace_item.type replace_type, replace_prod.prod_cd replace_prod_cd, " +
				" replace_prod.prod_cd replace_OfferCode, 0 replace_bw " +
				" from w_cpq_basket_item_assgn a, w_item b , W_ITEM_OFFER_PRODUCT_ASSGN c, " +
				" w_code_lkup replace_lkup, w_cpq_basket_item_assgn replace_cpq, w_item replace_item, " +
				" W_ITEM_OFFER_PRODUCT_ASSGN replace_prod" +
				" where replace_lkup.CODE = A.cpq_vid" +
				" AND replace_lkup.grp_id = 'SBIMS_NTV_CHANGE_SELF_HDD'" +
				" AND replace_lkup.DESCRIPTION = replace_cpq.cpq_vid" +
				" and replace_cpq.item_id = replace_item.id" +
				" and replace_cpq.item_id = replace_prod.item_id" +
				" and a.item_id = b.id" +
				" and a.item_id = c.item_id" +
				" ORDER BY b.TYPE ";				
		
		try {
			List<Map<String, Object>> rows = simpleJdbcTemplate.queryForList(SQL);
			logger.info("ROWS:"+gson.toJson(rows));		
			for (Map row : rows) {	
				NtvReplaceSelfPickHddDTO dto = new NtvReplaceSelfPickHddDTO();	
				dto.setId(row.get("id").toString());
				dto.setType((String)row.get("type"));
				dto.setProdCd((String)row.get("prod_cd"));
				dto.setOfferCode((String)row.get("OfferCode"));
				dto.setBw(row.get("bw").toString());
				dto.setReplaceId(row.get("replace_id").toString());
				dto.setReplaceType((String)row.get("replace_type"));
				dto.setReplaceProdCd((String)row.get("replace_prod_cd"));
				dto.setReplaceOfferCode((String)row.get("replace_OfferCode"));
				dto.setReplaceBw(row.get("replace_bw").toString());
				result.add(dto);
			}	
		}catch (Exception e) {
			logger.error("getReplaceSelfPickHddLkup error:",e);
		}		
		logger.info("getReplaceSelfPickHddLkup result:"+gson.toJson(result));		
		return result;
	}
	
	public String getCodeFromWCodeLkup (String grp_id)	throws DAOException {
		logger.info("getCodeFromWCodeLkup grp_id:"+grp_id);
		List<String> result = new ArrayList<String>();		
		String SQL="select code from  w_code_lkup where grp_id = :input";		
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("input", grp_id);
		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				String dto = new String();
				dto = (String) rs.getString("code");
				return dto;
			}
		};
		try {
			result = simpleJdbcTemplate.query(SQL, mapper,params);	
		} catch (EmptyResultDataAccessException erdae) {
			logger.error("EmptyResultDataAccessException getCodeFromWCodeLkup");
			return "N";
		} catch (Exception e) {
			logger.error("Exception caught in getCodeFromWCodeLkup():", e);
			throw new DAOException(e.getMessage(), e);
		}	

		if(result==null||result.size()<1){
			return "N";
		}else{
			logger.info("result.get(0):"+result.get(0));
			return result.get(0);
		}
	}	


	public List<ImsAllOrdDocAssgnDTO> getSupportDocFromDB(String orderId) throws DAOException {
		logger.info("getSupportDocFromDB:"+orderId);		
		String SQL = "SELECT   (SELECT   DOC_NAME" +
				"            FROM   bomweb_all_doc t_docName" +
				"           WHERE   t_docName.doc_Type = t_req_doc.doc_Type)" +
				"            DOC_NAME," +
				"			t_req_doc.doc_Type," +
				"         t_PATH.FILE_PATH_NAME," +
				"         t_PATH.SERIAL," +
				"         COLLECTED_IND," +
				"         (SELECT   waive_desc" +
				"            FROM   BOMWEB_ALL_ORD_DOC_WAIVE d" +
				"            WHERE   d.waive_reason = t_req_doc.WAIVE_REASON)" +
				"            WAIVE_REASON," +
				"			 t_req_doc.order_id " +
				"  FROM   bomweb_all_ord_doc_assgn t_req_doc," +
				"         (SELECT   FILE_PATH_NAME, t_path_a.doc_Type, SERIAL" +
				"            FROM   bomweb_all_ord_doc t_path_a," +
				"                   (  SELECT   MAX (B.SEQ_NUM) SEQ_NUM, b.doc_Type" +
				"                        FROM   bomweb_all_ord_doc b" +
				"                        WHERE   b.order_id = ? " +
				"                    GROUP BY   b.doc_Type) t_path_b_lastest" +
				"           WHERE       t_path_a.DOC_TYPE = t_path_b_lastest.doc_type" +
				"                   AND t_path_a.SEQ_NUM = t_path_b_lastest.SEQ_NUM" +
				"                   AND t_path_a.order_id = ?) t_PATH" +
				" WHERE   t_req_doc.order_id = ?" +
				"         AND t_req_doc.doc_Type = t_PATH.doc_Type(+) ";
		
		ParameterizedRowMapper<ImsAllOrdDocAssgnDTO> mapper = new ParameterizedRowMapper<ImsAllOrdDocAssgnDTO>() {
			public ImsAllOrdDocAssgnDTO mapRow(ResultSet rs, int rowNum)	throws SQLException {
				ImsAllOrdDocAssgnDTO dto = new ImsAllOrdDocAssgnDTO();					
				dto.setDocName(rs.getString("DOC_NAME"));
				dto.setWaiveReason(rs.getString("WAIVE_REASON"));
				dto.setCollectedInd("Y".equals(rs.getString("COLLECTED_IND"))?CollectedInd.Y:CollectedInd.N);
				dto.setFilePathName(rs.getString("FILE_PATH_NAME"));
				dto.setFaxSerial(rs.getString("SERIAL"));
				dto.setOrderId(rs.getString("order_id"));
				dto.setImsDocType(rs.getString("doc_Type"));
				return dto;
			}
		};		
		try {
			List<ImsAllOrdDocAssgnDTO> results = simpleJdbcTemplate.query(SQL, mapper, orderId, orderId,orderId);		
			logger.debug("getSupportDocFromDB results:"+gson.toJson(results));		
			for(ImsAllOrdDocAssgnDTO a:results){
				a.setPdfPaths(this.getSupportDocPdfPathsFromDB(a.getOrderId(), a.getImsDocType()));
			}
			logger.info("getSupportDocFromDB results with pdf paths:"+gson.toJson(results));		
			return results;
		
		} catch (Exception e) {
			logger.error("Exception caught in getSupportDocFromDB()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public List<String> getSupportDocPdfPathsFromDB(String orderId, String docType) throws DAOException {
		logger.info("getSupportDocFromDB:"+orderId +" docType:"+docType);		
		String SQL = "select file_path_name from bomweb_all_ord_doc where order_id = ? and doc_type = ? " +
				" order by file_path_name desc";		
		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
			public String mapRow(ResultSet rs, int rowNum)	throws SQLException {
				return rs.getString("file_path_name");
			}
		};		
		try {
			List<String> results = simpleJdbcTemplate.query(SQL, mapper, orderId, docType);		
			logger.info("getSupportDoc Pdf Paths FromDB results:"+gson.toJson(results));			
			return results;
		
		} catch (Exception e) {
			logger.error("Exception caught in getSupportDoc Pdf Paths FromDB()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}

	public void insertBomWebSubscribedOfferImsAmd(BomwebSubscribedOfferImsDTO dto, String amendSeq) throws DAOException{
		logger.info("insertBomWebSubscribedOfferIms amd:"+gson.toJson(dto));
		
		String SQL = "	insert into bomweb_sub_offer_ims_amd (	" +
		"	ORDER_ID, DTL_ID,  IO_IND, OFFER_ID,	" +
		"	 PRODUCT_ID, TC_ID, TC_CD,	" +
		"	TC_START_DATE, TC_END_DATE, PENALTY_AMT,	" +
		"	WAIVE_CD, CREATE_BY, CREATE_DATE,	" +
		"	UPDATE_BY, UPDATE_DATE, VI_IND, OFFER_SUB_KEY, OFFER_CD	," +
		"	SB_OFFER_TYPE,TC_DURATION,IMS_SERVICE_TYPE, VI_CAMPAIGN," +
		"	TC_REMAIN_MTH, amend_seq, offer_desc	" +
		"	) values(	" +
		"	?, ?, ?, ?, 	" +
		"	?, ?, ?, 	" +
		"	?, ?, ?,	" +
		"	?, ?, sysdate,	" +
		"	?, ?, ?, ?, ? ," +
		"	?,?,?, ?, " +
		"	?, ?,?	" +
		"	)	" ;
		
		try{
			simpleJdbcTemplate.update(SQL,
					dto.getOrderId(), "1",  dto.getIoInd(), dto.getOfferId(),
					dto.getProductId(), dto.getTcId(), dto.getTcCd(),
					dto.getTcStartDate(), dto.getTcEndDate(), dto.getPenaltyAmt(),
					dto.getWaiveCd(), dto.getCreateBy(),//sysdate,
					dto.getUpdateBy(), dto.getUpdateDate(),dto.getViInd(),dto.getOfferSubKey(), dto.getOfferCd(),
					dto.getOfferType(),dto.getDuration(),dto.getImsserviceType(), dto.getViCampaign(),
					dto.getTcRemainMth(), amendSeq, dto.getOfferDesc()
					);
		}catch (Exception e) {
			logger.error("Exception caught in insertBomWebSubscribedOfferImsAmd()", e);
			throw new DAOException(e.getMessage(), e);
		}
		
	}
	public void deleteBomWebSubscribedOfferImsAmd(OrderImsUI order) throws DAOException{
		String orderId = order.getOrderId();
		logger.info("deleteBomWebSubscribedOfferImsAmd amd:"+orderId);		
		try{
			String updateSQL = "delete bomweb_sub_offer_ims_amd where ORDER_ID = ? " +
					"  and AMEND_SEQ=? ";
			simpleJdbcTemplate.update(updateSQL,orderId,order.getAmendSeq());
		}catch (Exception e) {
			logger.error("Exception caught in deleteBomWebSubscribedOfferImsAmd()", e);
			throw new DAOException(e.getMessage(), e);
		}
		
	}
	
	public void insertBomWebSubscribedOfferIms(BomwebSubscribedOfferImsDTO dto) throws DAOException{
//		logger.info("insertBomWebSubscribedOfferIms");
		
		String SQL = "	insert into BOMWEB_SUBSCRIBED_OFFER_IMS(	" +
		"	ORDER_ID, DTL_ID, ITEM_ID, IO_IND, OFFER_ID,	" +
		"	OFFER_SUB_ID, PRODUCT_ID, TC_ID, TC_CD,	" +
		"	TC_START_DATE, TC_END_DATE, PENALTY_AMT,	" +
		"	WAIVE_CD, CREATE_BY, CREATE_DATE,	" +
		"	UPDATE_BY, UPDATE_DATE, VI_IND, OFFER_SUB_KEY, OFFER_CD	,SB_OFFER_TYPE,TC_DURATION,IMS_SERVICE_TYPE, VI_CAMPAIGN," +
		"	TC_REMAIN_MTH, 	" +
		"   offer_desc, offer_desc_chi, price, eff_price " +
		"	) values(	" +
		"	?, ?, ?, ?, ?,	" +
		"	?, ?, ?, ?,	" +
		"	?, ?, ?,	" +
		"	?, ?, sysdate,	" +
		"	?, ?, ?, ?, ? ,?,?,?, ?, ?," +
		"   ?,?,?,?	" +
		"	)	" ;
		
		try{
			simpleJdbcTemplate.update(SQL,
					dto.getOrderId(), "1", dto.getItemId(), dto.getIoInd(), dto.getOfferId(),
					dto.getOfferSubId(), dto.getProductId(), dto.getTcId(), dto.getTcCd(),
					dto.getTcStartDate(), dto.getTcEndDate(), dto.getPenaltyAmt(),
					dto.getWaiveCd(), dto.getCreateBy(),
					dto.getUpdateBy(), dto.getUpdateDate(),dto.getViInd(),dto.getOfferSubKey(), dto.getOfferCd(),dto.getOfferType(),dto.getDuration(),dto.getImsserviceType(), dto.getViCampaign(),
					dto.getTcRemainMth(), 
					dto.getOfferDesc(), dto.getOfferDescChi(), dto.getPrice(), dto.getArpuPrice()
					);
		}catch (Exception e) {
			logger.error("Exception caught in insertBomWebSubscribedOfferIms()", e);
			throw new DAOException(e.getMessage(), e);
		}
		
	}
	public void insertBomWebOrder(OrderDTO dto) throws DAOException{
		logger.debug("insertBomWebOrder");
		String SQL = "	INSERT INTO bomweb_order	" +
		"	            (order_id, ocid, src, order_type, msisdn, msisdnlvl, mnp_ind,	" +
		"	             shop_cd, bom_cust_no, mob_cust_no, acct_no, srv_req_date,	" +
		"	             agree_num, app_date, sales_type, sales_cd, dep_waive,	" +
		"	             on_hold_ind, on_hold_rea_cd, imei, warr_start_date, warr_period,	" +
		"	             create_date, order_status,	" +
		"	             err_cd, err_msg, sales_name, ao_ind, last_update_by,	" +
		"	             last_update_date, create_by, reason_cd, LOB, sign_off_date,	" +
		"	             staff_num, sales_channel, sales_contact_num, submit_date, cpq_txn_id, online_req_id, centre_cd	" +
		"	            )	" +
		"	     VALUES (?, ?, ?, ?, ?, ?, ?,	" +
		"	             ?, ?, ?, ?, ?,	" +
		"	             ?, ?, ?, ?, ?,	" +
		"	             ?, ?, ?, ?, ?,	" +		
		"	             sysdate, ?,	" +
		"	             ?, ?, ?, ?, null,	" +
		"	             null, ?, ?, ?, ?,	" +
		"	             ?, ?, ?, ?, ?, ?, ?	" +
		"	            )	";

		
		try{
			simpleJdbcTemplate.update(SQL,
					dto.getOrderId(), dto.getOcId(), dto.getSrc(), dto.getOrderType(), dto.getMsisdn(), dto.getMsisdnlvl(), dto.getMnpInd(),
					dto.getShopCd(), dto.getBomCustNo(), dto.getMobCustNo(), dto.getAcctNo(), dto.getSrvReqDate(),
					dto.getAgreeNum(), dto.getAppDate(), dto.getSalesType(), dto.getSalesCd(), dto.getDepWaive(),
					dto.getOnHoldInd(), dto.getOnHoldReaCd(), dto.getImei(), dto.getWarrStartDate(), dto.getWarrPeriod(),
					dto.getOrderStatus(),
					dto.getErrCd(), dto.getErrMsg(), dto.getSalesName(), dto.getAoInd(),
					dto.getCreateBy(), dto.getReasonCd(), dto.getLob(), dto.getSignOffDate(),
					dto.getStaffNum(), dto.getSalesChannel(), dto.getSalesContactNum(), dto.getSubmitDate(),
					dto.getCpqTxnId(), dto.getSessionReqId(), dto.getCentreCd()
					);
		}catch (Exception e) {
			logger.error("Exception caught in insertBomWebOrder()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public void insertBomWebOrderIms(OrderImsDTO dto) throws DAOException{
		logger.debug("insertBomWebOrderIms");
		String SQL = "	INSERT INTO bomweb_order_ims	" +
		"	            (order_id, login_id, decoder_type, adult_view_allow,	" +
		"	             target_comm_date, prepay_amt, moving_chrg, OT_INSTALL_CHRG ,waived_prepay,	" +
		"	             cash_fs_prepay, now_tv_form_type, fixed_line_no, is_cc_offer,	" +
		"	             lang_preference, process_cc_prepay, process_vim, process_vms,	" +
		"	             process_wifi, create_by, create_date, last_upd_by,	" +
		"	             last_upd_date, tv_credit, bandwidth, primary_acct_no,  INSTALL_INSTALMENT_AMT, INSTALL_INSTALMENT_MTH, " +
		"				 ot_chrg_type, APPLMTHD, SOURCE_CD, " +
		"				 CALL_DATE, POSITION_NO, FAX_SERIAL, APPLMTHD_desc, " +
		"				 INSTALL_OT_CODE, INSTALL_OT_DESC_EN, INSTALL_OT_DESC_ZH,INSTALL_INSTALMENT_CODE, INSTALL_OT_QTY, " +
		"				 DS_TYPE, DS_LOCATION, COOLOFF ,WAIVE_CLOFF, QC_CALL_TIME, WAIVE_QC, ims_order_type, SYS_F,is_new_cust_ind, TV_PRICE_IND, " +
		"				 deposit_amt, arpu_in, arpu_out, ride_on_fsa_ind, must_qc_ind, qr_submit_date, " +
		"                PCD_LIKE_IND, PCD_NOW_ONE_IND, PRE_INST_IND, MOBILE_OFFER_IND, MRT, " +
		//"				 deposit_amt, arpu_in, arpu_out, ride_on_fsa_ind, must_qc_ind, qr_submit_date, special_request_cd,COLLECT_VI_STB_1,FIELD_IND " +
		"				 special_request_cd,COLLECT_VI_STB_1,FIELD_IND, PRE_USE_IND, RELATED_FSA, RIDE_ON_FSA_REASON_CD, SERVICE_WAIVER, SALES_MODE, SPECIAL_OTC_ID," +
		"                COLLECT_VI_STB_2, HK_QR_STRING, HK_QR_URL, OUT_TRADE_NO, CASH_PAY_MTD_TYPE, TRADE_NO, FPS_REFERENCE_ID  " +
		"	            )	" +
		"	     VALUES (?, ?, ?, ?,	" +
		"	             ?, ?, ?, ?,?,	" +
		"	             ?, ?, ?, ?,	" +
		"	             ?, ?, ?, ?,	" +
		"	             ?, ?, sysdate, null,	" +
		"	             null, ?, ?, ?, ?, ?,	" +
		"				 ?, ?, ?,	" +
		"				 ?, ?, ?,?,	" +
		"				 ?, ?, ?,?,?, " +
		"				 ?, ?, ?,?, ?, ?, ?,?,?,? ," +
		"				 ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ," +
		"				 ?, ?, ?, ?, ?, ?, ?, ?, ?,	" +
		"	             ?, ?, ?, ?, ?, ?, ? )	" ;
		
		try{
			simpleJdbcTemplate.update(SQL,
					dto.getOrderId(), dto.getLoginId(), dto.getDecodeType(), dto.getAdultViewAllow(),
					dto.getTargetCommDate(), dto.getPrepayAmt(), dto.getMovingChrg(), dto.getOtInstallChrg(), dto.getWaivedPrepay(),
					dto.getCashFsPrepay(), dto.getNowTvFormType(), dto.getFixedLineNo(), dto.getIsCreditCardOffer(),
					dto.getLangPreference(), dto.getProcessCreditCard(), dto.getProcessVim(), dto.getProcessVms(),
					dto.getProcessWifi(), dto.getCreateBy(),
					dto.getTvCredit(), dto.getBandwidth(), dto.getPrimaryAcctNo(),dto.getInstallmentChrg(),dto.getInstallmentMonth(),
					dto.getOtChrgType(), dto.getAppMethod(), dto.getSourceCd(),
					dto.getCallDate(), dto.getPositionNo(), dto.getFAXno(), dto.getAppMethodDesc(),
					dto.getInstallOtCode(), dto.getInstallOTDesc_en(), dto.getInstallOTDesc_zh(),dto.getInstallmentCode(),dto.getInstallOtQty(), 
					dto.getDsType(), dto.getDsLocation(), dto.getDsCoolOff(), dto.getDsWaiveCoolOff(), dto.getDsQcCallTime(), dto.getDsWaiveQC(), dto.getImsOrderType(),
					dto.getSysF(),dto.getIsNewCust(),dto.getTvPriceInd(),
					dto.getDepositAmt(), dto.getARPU_in(), dto.getARPU_out(), dto.getRide_on_FSA_Ind(), dto.getMust_QC_Ind(), dto.getQr_submit_date(),
					dto.getPcdLike100Ind(), dto.getPcdNowOneBoxInd(), dto.getPreInstallInd(), dto.getMobileOfferInd(), dto.getMrt(), 
					dto.getSpecialRequestCd(), dto.getCollectViStb1(), dto.getFieldInd(), dto.getPreUseInd(), dto.getRelated_FSA(), dto.getRide_on_FSA_reason_Cd(), dto.getServiceWaiver(), dto.getMode(), dto.getSpecialOTCId(),
					dto.getCollectViStb2(), dto.getHkqrString(), dto.getHkqrUrl(), dto.getOutTradeNo(), dto.getCashPayMtdType(), dto.getTradeNo(), dto.getFpsReferenceId()
					);
			
		}catch (Exception e) {
			logger.error("Exception caught in insertBomWebOrderIms()", e);
			throw new DAOException(e.getMessage(), e);
		}

	}
	
	public void insertBomWebAppointment(AppointmentDTO dto) throws DAOException{
		logger.debug("insertBomWebAppointment");
		String SQL = "	INSERT INTO bomweb_appointment	" +
		"	            (order_id, dtl_id, serial_num, appnt_start_time, appnt_end_time,	" +
		"	             inst_contact_name, inst_contact_num, inst_sms_num,	" +
		"	             exact_appnt_time, forced_delay_ind, pre_wiring_start_time,	" +
		"	             pre_wiring_end_time, pre_wiring_type, tid_ind, create_by,	" +
		"	             create_date, last_upd_by, last_upd_date, appnt_type, bmo_lead_day, align_bill_date		" +
		"	            )	" +
		"	     VALUES (?, ?, ?, ?, ?,	" +
		"	             ?, ?, ?,	" +
		"	             ?, ?, ?,	" +
		"	             ?, ?, ?, ?,	" +
		"	             sysdate, null, null, ?, ?, ?	" +
		"	            )	";
		
		try{
			simpleJdbcTemplate.update(SQL,
					dto.getOrderId(), "1", dto.getSerialNum(), dto.getAppntStartDate(), dto.getAppntEndDate(),
					dto.getInstContactName(), dto.getInstContactNum(), dto.getInstSmsNum(),
					dto.getExactAppntTime(), dto.getForcedDelayInd(), dto.getPreWiringStartTime(),
					dto.getPreWiringEndTime(), dto.getPreWiringEndTime(), dto.getTidInd(), dto.getCreateBy(),
					dto.getAppntType(), dto.getBmoLeadDay(), dto.getAlignWithBillDate()
					);

					
		}catch (Exception e) {
			logger.error("Exception caught in insertBomWebAppointment()", e);
			throw new DAOException(e.getMessage(), e);
		}
		
	}
	
	public void insertRemark(RemarkDTO dto) throws DAOException{
		logger.debug("insertRemark");
		String SQL = "	INSERT INTO bomweb_remark	" +
		"	            (order_id, dtl_id, rmk_type, rmk_seq, rmk_dtl, create_by,	" +
		"	             create_date, last_upd_by, last_upd_date	" +
		"	            )	" +
		"	     VALUES (?, ?, ?, ?, ?, ?,	" +
		"	            to_date(nvl(?, to_char(sysdate, 'dd/mm/yyyy hh24:mi:ss')), 'dd/mm/yyyy hh24:mi:ss'), null, null	" +
		"	            )	";

		try{
			System.out.println(SQL);
			simpleJdbcTemplate.update(SQL,
					dto.getOrderId(), "1", dto.getRmkType(), dto.getRmkSeq(), dto.getRmkDtl(), dto.getCreateBy(),
					//dto.getCreateDate()
					Utility.date2String(dto.getCreateDate(), "dd/MM/yyyy HH:mm:ss")
					);
			
		}catch (Exception e) {
			logger.error("Exception caught in insertRemark()", e);
			throw new DAOException(e.getMessage(), e);
		}				
		
	}
	
	public void insertBomWebCustomer(CustomerDTO dto) throws DAOException{
		logger.debug("insertBomWebCustomer");
		String SQL = "	INSERT INTO bomweb_customer	" +
		"	            (order_id, cust_no, mob_cust_no, first_name, last_name,	" +
		"	             id_doc_type, id_doc_num, dob, title, company_name, ind_type,	" +
		"	             ind_sub_type, nationality, create_date, addr_proof_ind, LOB,	" +
		"	             service_num, create_by, last_upd_by, last_upd_date,	" +
		"	             id_verified_ind, blacklist_ind, " +
		"				 cs_portal_ind, cs_portal_login, cs_portal_mobile, mismatch_ind, the_club_login, " +
		"				 the_club_ind, the_club_mobile, NOWID_IND, NOWID_LOGIN, hkt_opt_out," +
		"				 club_opt_out, club_opt_rea, club_opt_rmk 	" +
		"	            )	" +
		"	     VALUES (?, ?, ?, ?, ?,	" +
		"	             ?, ?, ?, ?, ?, ?,	" +
		"	             ?, ?, sysdate, ?, ?,	" +
		"	             ?, ?, null, null,	" +
		"	             ?, ?,	" +
		"	             ?, ?, ?, ?, ?, " +
		"				 ?, ?, ?, ?, ?, " +
		"				 ?, ?, ?	" +
		"	            )	";
		
		try{
			
			simpleJdbcTemplate.update(SQL,
					dto.getOrderId(), dto.getCustNo(), dto.getMobCustNo(), dto.getFirstName(), dto.getLastName(),
					dto.getIdDocType(), dto.getIdDocNum(), dto.getDob(), dto.getTitle(), dto.getCompanyName(), dto.getIndType(),
					dto.getIndSubType(), dto.getNationality(), dto.getAddrProofInd(), dto.getLob(),
					dto.getServiceNum(), dto.getCreateBy(),
					dto.getIdVerified(), dto.getBlacklistInd(),
					dto.getCsPortalInd(), dto.getCsPortalLogin(), dto.getCsPortalMobile(), dto.getExistingCustomerConflictWithName(), dto.getTheClubLogin(), 
					dto.getTheClubInd(), dto.getTheClubMoblie(), dto.getIsRegNowId(), dto.getNowId(), dto.getCsPortalOptOutInd(),
					dto.getTheClubOptOutInd(), dto.getOptoutTheClubReason(), dto.getOptoutTheClubRmk()
					);
					
			
		}catch (Exception e) {
			logger.error("Exception caught in insertBomWebCustomer()", e);
			throw new DAOException(e.getMessage(), e);
		}

	}
	
	public void insertBomWebAcct(AcctDTO dto) throws DAOException{
		logger.debug("insertBomWebAcct");
		String SQL = "	INSERT INTO bomweb_acct	" +
		"	            (order_id, cust_no, acct_name, bill_freq, bill_lang, sms_no,	" +
		"	             email_addr, acct_no, create_date, bill_period, create_by,	" +
		//"	             last_upd_by, last_upd_date, dtl_id, bill_media	" +
		"	             last_upd_by, last_upd_date, bill_media, exist_bill_media, action	" +
		"	            )	" +
		"	     VALUES (?, ?, ?, ?, ?, ?,	" +
		"	             ?, ?, sysdate, ?, ?,	" +
		"	             null, null, ?, ?, ?	" +
		"	            )	";
		
		try{
			
			simpleJdbcTemplate.update(SQL,
					dto.getOrderId(), dto.getCustNo(), dto.getAcctName(), dto.getBillFreq(), dto.getBillLang(), dto.getSmsNo(),
					dto.getEmailAddr(), dto.getAcctNo(), dto.getBillPeriod(), dto.getCreateBy(),
					dto.getBillMedia(), dto.getExistingBillingMedia(), dto.getUpdateBillingMethod()
					);

		}catch (Exception e) {
			logger.error("Exception caught in insertBomWebAcct()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public void insertBomWebPayment(PaymentDTO dto) throws DAOException{
		logger.debug("insertBomWebPayment");
		String SQL = "	INSERT INTO bomweb_payment	" +
		"	            (order_id, cust_no, pay_mtd_key, acct_no, autopay_app_date,	" +
		"	             autopay_up_lim_amt, b_acct_no, b_acct_hold_name,	" +
		"	             b_acct_hold_id_type, b_acct_hold_id_num, branch_cd, bank_cd,	" +
		"	             pay_mtd_type, third_party_ind, cc_type, cc_num, cc_hold_name,	" +
		"	             cc_exp_date, cc_issue_bank, cc_id_doc_type, cc_id_doc_no,	" +
		"	             create_date, create_by, last_upd_by, last_upd_date,	" +
		"	             cc_verified_ind, dtl_id, action	" +
		"	            )	" +
		"	     VALUES (?, ?, ?, ?, ?,	" +
		"	             ?, ?, ?,	" +
		"	             ?, ?, ?, ?,	" +
		"	             ?, ?, ?, ?, ?,	" +
		"	             ?, ?, ?, ?,	" +
		"	             sysdate, ?, null, null,	" +
		"	             ?, ?, ?	" +
		"	            )	";

		try{
			
			simpleJdbcTemplate.update(SQL,
					dto.getOrderId(), dto.getCustNo(), dto.getPayMtdKey(), dto.getAcctNo(), dto.getAutopayAppDate(),
					dto.getAutopayUpLimAmt(), dto.getBAcctNo(), dto.getBAcctHoldName(),
					dto.getBAcctHoldIdType(), dto.getBAcctHoldIdNum(), dto.getBranchCd(), dto.getBankCd(),
					dto.getPayMtdType(), dto.getThirdPartyInd(), dto.getCcType(), dto.getCcNum(), dto.getCcHoldName(),
					dto.getCcExpDate(), dto.getCcIssueBank(), dto.getCcIdDocType(), dto.getCcIdDocNo(),
					dto.getCreateBy(),
					dto.getCcVerified(), "1", dto.getAction()
					);

		}catch (Exception e) {
			logger.error("Exception caught in insertBomWebPayment()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public void insertBomWebCustOptoutInfo(CustOptoutInfoDTO dto) throws DAOException{
		
		logger.debug("insertBomWebCustOptoutInfo");
		String SQL ="	INSERT INTO bomweb_cust_optout_info	" +
		"	            (order_id, cust_no, direct_mailing, outbound, sms, email,	" +
		"	             web_bill, nonsales_sms, internet, create_by, create_date,	" +
		"	             last_upd_by, last_upd_date	" +
		"	            )	" +
		"	     VALUES (?, ?, ?, ?, ?, ?,	" +
		"	             ?, ?, ?, ?, sysdate,	" +
		"	             null, null	" +
		"	            )	";

			
		try{
			
			simpleJdbcTemplate.update(SQL,
					dto.getOrderId(), dto.getCustNo(), dto.getDirectMailing(), dto.getOutbound(), dto.getSms(), dto.getEmail(),
					dto.getWebBill(), dto.getNonsalesSms(), dto.getInternet(), dto.getCreateBy()					
					);

		}catch (Exception e) {
			logger.error("Exception caught in insertBomWebCustOptoutInfo()", e);
			throw new DAOException(e.getMessage(), e);
		}	
		
	}
	
	public void insertBomWebContact(ContactDTO dto) throws DAOException{
		logger.debug("insertBomWebContact");
		String SQL ="	INSERT INTO bomweb_contact	" +
		"	            (order_id, title, contact_name, contact_phone, email_addr,	" +
		"	             action_ind, create_date, other_phone, create_by, last_upd_by,	" +
		"	             last_upd_date	" +
		"	             , contact_type	" + //eSignature Cr by Eric Ng @ 2012-10-29
		"	             , NOWTV_DELIVERY_MOBILE_NUM	" + //by Jerry @ 2019-03-18
		"	            )	" +
		"	     VALUES (?, ?, ?, ?, ?,	" +
		"	             ?, sysdate, ?, ?, null,	" +
		"	             null	" +
		"	             , ?	" +
		"	             , ?	" +
		"	            )	";
		
		try{
			
			simpleJdbcTemplate.update(SQL,
					dto.getOrderId(), dto.getTitle(), dto.getContactName(), dto.getContactPhone(), dto.getEmailAddr(),
					dto.getActionInd(), dto.getOtherPhone(), dto.getCreateBy(), "CC"//dto.getContactType()
					,dto.getDeliveryPhone()
					);

		}catch (Exception e) {
			logger.error("Exception caught in insertBomWebContact()", e);
			throw new DAOException(e.getMessage(), e);
		}	

		
	}
	
	public void insertBomWebCustAddr(CustAddrDTO dto) throws DAOException{
		logger.debug("insertBomWebCustAddr");
		String SQL ="	INSERT INTO bomweb_cust_addr	" +
		//"	            (order_id, addr_usage, flat, area_cd, dist_no, sect_cd, str_name,	" +
		"	            (order_id, addr_usage, area_cd, dist_no, sect_cd, str_name,	" +
		"	             hi_lot_no, str_cat_cd, build_no, foreign_addr_flag, floor_no,	" +
		"	             unit_no, po_box_no, addr_type, str_no, sect_dep_ind,	" +
		"	             create_date, area_desc, dist_desc, sect_desc, str_cat_desc,	" +
		"	             create_by, last_upd_by, last_upd_date, serbdyno, blacklist_ind,	" +
		"	             dtl_id	" +
		"	            )	" +
		//"	     VALUES (?, ?, ?, ?, ?, ?, ?,	" +
		"	     VALUES (?, ?, ?, ?, ?, ?, 	" +
		"	             ?, ?, ?, ?, ?,	" +
		"	             ?, ?, ?, ?, ?,	" +
		"	             sysdate, ?, ?, ?, ?,	" +
		"	             ?, null, null, ?, ?,	" +
		"	             ?	" +
		"	            )	";
		
		try{
			
			simpleJdbcTemplate.update(SQL,
					//dto.getOrderId(), dto.getAddrUsage(), dto.getFlat(), dto.getAreaCd(), dto.getDistNo(), dto.getSectCd(), dto.getStrName(),
					dto.getOrderId(), dto.getAddrUsage(), dto.getAreaCd(), dto.getDistNo(), dto.getSectCd(), dto.getStrName(),
					dto.getHiLotNo(), dto.getStrCatCd(), dto.getBuildNo(), dto.getForeignAddrFlag(), dto.getFloorNo(),
					dto.getUnitNo(), dto.getPoBoxNo(), dto.getAddrType(), dto.getStrNo(), dto.getSectDepInd(),
					dto.getAreaDesc(), dto.getDistDesc(), dto.getSectDesc(), dto.getStrCatDesc(),
					dto.getCreateBy(), dto.getSerbdyno(), dto.getBlacklistInd(),
					"1"
					);

		}catch (Exception e) {
			logger.error("Exception caught in insertBomWebCustAddr()", e);
			throw new DAOException(e.getMessage(), e);
		}

	}
	
	public void insertBomWebCustAddrAmd(CustAddrDTO dto, String p_wq_batch_id) throws DAOException{
		logger.debug("insertBomWebCustAddr");
		String SQL ="	INSERT INTO bomweb_cust_addr_amd	" +
		//"	            (order_id, addr_usage, flat, area_cd, dist_no, sect_cd, str_name,	" +
		"	            (order_id, addr_usage, area_cd, dist_no, sect_cd, str_name,	" +
		"	             hi_lot_no, str_cat_cd, build_no, foreign_addr_flag, floor_no,	" +
		"	             unit_no, po_box_no, addr_type, str_no, sect_dep_ind,	" +
		"	             create_date, area_desc, dist_desc, sect_desc, str_cat_desc,	" +
		"	             create_by, last_upd_by, last_upd_date, serbdyno, blacklist_ind,	" +
		"	             dtl_id	, AMEND_SEQ" +
		"	            )	" +
		//"	     VALUES (?, ?, ?, ?, ?, ?, ?,	" +
		"	     VALUES (?, ?, ?, ?, ?, ?, 	" +
		"	             ?, ?, ?, ?, ?,	" +
		"	             ?, ?, ?, ?, ?,	" +
		"	             sysdate, ?, ?, ?, ?,	" +
		"	             ?, null, null, ?, ?,	" +
		"	             ?,  (select amend_seq from bomweb_amend_category where order_id = ? and wq_batch_id = ? and nature = '362')	" +	//tmp
		"	            )	";
		
		try{
			
			simpleJdbcTemplate.update(SQL,
					//dto.getOrderId(), dto.getAddrUsage(), dto.getFlat(), dto.getAreaCd(), dto.getDistNo(), dto.getSectCd(), dto.getStrName(),
					dto.getOrderId(), dto.getAddrUsage(), dto.getAreaCd(), dto.getDistNo(), dto.getSectCd(), dto.getStrName(),
					dto.getHiLotNo(), dto.getStrCatCd(), dto.getBuildNo(), dto.getForeignAddrFlag(), dto.getFloorNo(),
					dto.getUnitNo(), dto.getPoBoxNo(), dto.getAddrType(), dto.getStrNo(), dto.getSectDepInd(),
					dto.getAreaDesc(), dto.getDistDesc(), dto.getSectDesc(), dto.getStrCatDesc(),
					dto.getCreateBy(), dto.getSerbdyno(), dto.getBlacklistInd(),
					"1", dto.getOrderId() ,p_wq_batch_id
					);

		}catch (Exception e) {
			logger.error("Exception caught in insertBomWebCustAddrAmd()", e);
			throw new DAOException(e.getMessage(), e);
		}

	}
	
	public void insertBomWebComponentAmd(ComponentDTO dto, String p_wq_batch_id) throws DAOException{
		logger.debug("insertBomWebComponentAmd");
		String SQL ="	INSERT INTO bomweb_component_amd	" +
		"	            (order_id, attb_id, attb_value, create_date, create_by,	" +
		"	             last_upd_by, last_upd_date, amend_seq, io_ind	" +
		"	            )	" +
		"	     VALUES (?, ?, ?, sysdate, ?,	" +
		"	             null, null, (select amend_seq from bomweb_amend_category where order_id = ? and wq_batch_id = ? and nature = '362'), ?	" +
		"	            )	";
		
		try{
			
			simpleJdbcTemplate.update(SQL,
					dto.getOrderId(), dto.getAttbId(), dto.getAttbValue(), dto.getCreateBy(), dto.getOrderId(), p_wq_batch_id, "I"
					);

		}catch (Exception e) {
			logger.error("Exception caught in insertBomWebComponentAmd()", e);
			throw new DAOException(e.getMessage(), e);
		}

	}
	
	public void insertBomWebVasParmImsAmd(SubscribedCslOfferDTO dto, String p_wq_batch_id) throws DAOException{
		logger.info("insertBomWebVasParmImsAmd");
		String SQL ="	INSERT INTO bomweb_vas_parm_ims_amd	" +
		"	            (ORDER_ID, PRD_ID, ITEM_ID, VAS_TYPE, PARM_A, PARM_A_CD,	" +
		"				PARM_B, PARM_B_CD,	" +
		"				PARM_C, PARM_C_CD,	" +
		"	            CREATE_BY, CREATE_DATE,	" +
		"	            last_upd_by, last_upd_date, amend_seq, io_ind " +
		"	            )	" +
		"	     VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, sysdate, " +
		"	             null, null, (select amend_seq from bomweb_amend_category where order_id = ? and wq_batch_id = ? and nature = '362'), ?	" +
		"	            )	";
		
		try{
			
			simpleJdbcTemplate.update(SQL, dto.getOrder_id(),
					dto.getProd_id(),dto.getItem_id(), dto.getVas_type(), dto.getVas_parm_a(), dto.getVas_parm_a_cd(),
					dto.getVas_parm_b(), dto.getVas_parm_b_cd(),
					dto.getVas_parm_c(), dto.getVas_parm_c_cd(),
					dto.getCreateBy(), dto.getOrder_id(), p_wq_batch_id, "I"
					);

		}catch (Exception e) {
			logger.error("Exception caught in insertBomWebVasParmImsAmd()", e);
			throw new DAOException(e.getMessage(), e);
		}

	}
	
	public void insertBomWebAddrInventory(AddrInventoryDTO dto) throws DAOException{
		logger.debug("insertBomWebAddrInventory");
		String SQL ="	INSERT INTO bomweb_addr_inventory	" +
		"	            (order_id, dtl_id, addr_usage, resource_shortage_ind, technology,	" +
		"	             building_type, n2_building_ind, field_work_permit_day, addr_id,	" +
		"	             max_bandwidth, fttc_ind, h264_ind, adsl_18m_ind, create_by, create_date, last_upd_by,	" +
		"	             last_upd_date, adsl_8m_ind, ntv_coverage	" +
		"	            )	" +
		"	     VALUES (?, ?, ?, ?, ?,	" +
		"	             ?, ?, ?, ?,	" +
		"	             ?, ?, ?, ?, ?, sysdate, null,	" +
		"	             null, ?, ?	" +
		"	            )	";

		try{
			
			simpleJdbcTemplate.update(SQL,
					dto.getOrderId(), "1", dto.getAddrUsage(), dto.getResourceShortage(), dto.getTechnology(),
					dto.getBuildingType(), dto.getN2BuildingInd(), dto.getFieldPermitDay(), dto.getAddrId(),
					dto.getMaxBandwidth(), dto.getFttcInd(), dto.getH264Ind(), dto.getAdsl18MInd(), dto.getCreateBy(), dto.getAdsl8MInd(),dto.getNtvCoverage()
					);

		}catch (Exception e) {
			logger.error("Exception caught in insertBomWebAddrInventory()", e);
			throw new DAOException(e.getMessage(), e);
		}
		
	}
	
	public void insertBomWebSubscribedItem(SubscribedItemDTO dto) throws DAOException{
		logger.debug("insertBomWebSubscribedItem");
		String SQL ="   INSERT INTO bomweb_subscribed_item  " +

        "               (order_id, ID, TYPE, create_date, basket_id, create_by, " +

        "                last_upd_by, last_upd_date, campaign_cd, pack_cd, top_up   " +

        "               )   " +

        "        VALUES (?, ?, ?, sysdate, ?, ?,    " +

        "                null, null, ?, ?, ?    " +

        "               )   ";

 
		try{
			
			simpleJdbcTemplate.update(SQL,

                    dto.getOrderId(), dto.getId(), dto.getType(), dto.getBasketId(), dto.getCreateBy(),

                    dto.getCampaignCd(), dto.getPackCd(), dto.getTopUpCd()

                    );

		}catch (Exception e) {
			logger.error("Exception caught in insertBomWebSubscribedItem()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
		  
	//kinman  
	public void insertBomWebVasParmIms(SubscribedCslOfferDTO dto) throws DAOException{
		logger.debug("insertBomWebVasParmIms");
		String SQL ="	INSERT INTO bomweb_vas_parm_ims	" +
		"	            (ORDER_ID, PRD_ID, ITEM_ID, VAS_TYPE, PARM_A, PARM_A_CD,	" +
		"				PARM_B, PARM_B_CD,	" +
		"				PARM_C, PARM_C_CD,	" +
		"	            CREATE_BY, CREATE_DATE,	" +
		"	            last_upd_by, last_upd_date	" +
		"	            )	" +
		"	     VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, sysdate, " +
		"	             null, null	" +
		"	            )	";
		
		try{
			
			simpleJdbcTemplate.update(SQL, dto.getOrder_id(),
					dto.getProd_id(),dto.getItem_id(), dto.getVas_type(), dto.getVas_parm_a(), dto.getVas_parm_a_cd(),
					dto.getVas_parm_b(), dto.getVas_parm_b_cd(),
					dto.getVas_parm_c(), dto.getVas_parm_c_cd(),
					dto.getCreateBy()
					);

		}catch (Exception e) {
			logger.error("Exception caught in insertBomWebVasParmIms()", e);
			throw new DAOException(e.getMessage(), e);
		}

	}
	
	
	public void insertBomWebComponent(ComponentDTO dto) throws DAOException{
		logger.debug("insertBomWebComponent");
		String SQL ="	INSERT INTO bomweb_component	" +
		"	            (order_id, attb_id, attb_value, create_date, create_by,	" +
		"	             last_upd_by, last_upd_date	" +
		"	            )	" +
		"	     VALUES (?, ?, ?, sysdate, ?,	" +
		"	             null, null	" +
		"	            )	";
		
		try{
			
			simpleJdbcTemplate.update(SQL,
					dto.getOrderId(), dto.getAttbId(), dto.getAttbValue(), dto.getCreateBy()
					);

		}catch (Exception e) {
			logger.error("Exception caught in insertBomWebComponent()", e);
			throw new DAOException(e.getMessage(), e);
		}

	}
	
	public void insertBomWebSubscribedChannel(SubscribedChannelDTO dto) throws DAOException{
		logger.debug("insertBomWebSubscribedChannel");
		String SQL ="	INSERT INTO bomweb_subscribed_channel	" +
		"	            (order_id, dtl_id, channel_id, create_by, create_date,	" +
		"	             last_upd_by, last_upd_date	" +
		"	            )	" +
		"	     VALUES (?, ?, ?, ?, sysdate,	" +
		"	             null, null	" +
		"	            )	";
		
		try{
			
			simpleJdbcTemplate.update(SQL,
					dto.getOrderId(), "1", dto.getChannelId(), dto.getCreateBy() 
					);

		}catch (Exception e) {
			logger.error("Exception caught in insertBomWebSubscribedChannel()", e);
			throw new DAOException(e.getMessage(), e);
		}

	}
	
	public void insertBomWebSalesReferrerIms(SalesReferrerDTO dto) throws DAOException{
		logger.debug("insertBomWebSalesReferrerIms");
		String SQL ="	INSERT INTO bomweb_sales_referrer_ims	" +
		"	            (order_id, REF_APPLMTHD, REF_APPLMTHD_DESC, REF_SOURCE_CD, REF_STAFF_NUM, REF_SALES_NAME, " +
		"				 create_by, create_date,	" +
		"	             last_upd_by, last_upd_date	" +
		"	            )	" +
		"	     VALUES (?, ?, ?, ?, ?, ?, ?, sysdate,	" +
		"	             null, null	" +
		"	            )	";
		
		try{
			
			simpleJdbcTemplate.update(SQL,
								dto.getOrderId(), dto.getReferrerAppMethod(),
								dto.getReferrerAppMethodDesc(),dto.getReferrerSourcecode(),
								dto.getReferrerStaffNo(),dto.getReferrerStaffName(),
								dto.getCreateBy() 
								);

		}catch (Exception e) {
			logger.error("Exception caught in insertBomWebSalesReferrerIms()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	
	
	public String generateNewOrderId(String shopCd, String staffId,String orderType) throws DAOException{
		logger.debug("generateNewOrderId shopcd:"+shopCd+" staffid:"+staffId);
		String orderId;
		
//		String SELECT_SQL = "	SELECT 'R' || shop_cd || 'P' || LPAD (nvl(ims_seq_no, 1), 6, '0')	"+
//		"	  FROM bomweb_shop	"+
//		"	 WHERE shop_cd = ? AND channel_id = 1	";
		
//		String SELECT_SQL = "SELECT DISTINCT p.ord_prefix || s.shop_cd || 'P' || LPAD (nvl(s.ims_seq_no, 1), 6, '0')  "+      
//		"FROM bomweb_shop s, bomweb_order_prefix_v p   "+
//		"WHERE s.shop_cd = ? AND s.channel_id = 1  "+
//		"AND p.staff_id = ? ";

//		String UPDATE_SQL = "UPDATE bomweb_shop	"+
//		"	   SET ims_seq_no = nvl(ims_seq_no, 1) + 1	"+
//		"	 WHERE shop_cd = ? AND channel_id = 1	";
		
		
		

		try {
//			orderId = (String) simpleJdbcTemplate.queryForObject(SELECT_SQL,String.class, shopCd, staffId);
		
//			simpleJdbcTemplate.update(UPDATE_SQL, shopCd);
			
			SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
			.withSchemaName("OPS$CNM")
			.withCatalogName("PKG_SB_IMS_LTS")
			.withProcedureName("GET_NEW_ORDER_ID");
			jdbcCall.setAccessCallParameterMetaData(false);
			jdbcCall.declareParameters(
			new SqlParameter("i_shop_code", Types.VARCHAR),
			new SqlParameter("i_staff_id", Types.VARCHAR),
			new SqlParameter("i_order_type", Types.VARCHAR),
			new SqlOutParameter("o_order_id", Types.VARCHAR),
			new SqlOutParameter("o_returnvalue", Types.INTEGER),
			new SqlOutParameter("o_errcode", Types.INTEGER),
			new SqlOutParameter("o_errtext", Types.VARCHAR));
			MapSqlParameterSource inMap = new MapSqlParameterSource();
			inMap.addValue("i_shop_code", shopCd);
			inMap.addValue("i_staff_id", staffId);
			inMap.addValue("i_order_type", orderType);
			SqlParameterSource in = inMap;
			
			Map out = jdbcCall.execute(in);

			orderId = (String) out.get("o_order_id");
			
			logger.info("GET_NEW_ORDER_ID [orderId]= "+orderId );
			
			int errcode=0;
			if (((Integer) out.get("o_errcode"))!= null ) {
				errcode = ((Integer) out.get("o_errcode")).intValue();
			}
			
			String errorText = (String) out.get("o_errtext");
			logger.info("GET_NEW_ORDER_ID output [errcode]= "+errcode +" [errorText]"+ errorText);
				
		} catch (Exception e) {
			throw new DAOException(e.getMessage(), e);
		}
		logger.info("Get Order ID: " + orderId);
		return orderId;
	}
	
	public List<OrderDTO> getBomWebOrder(String orderId) throws DAOException{
		logger.debug("getBomWebOrder");
		String SQL = "	SELECT order_id, ocid, src, order_type, msisdn, msisdnlvl, mnp_ind, shop_cd,	" +
					"	       bom_cust_no, mob_cust_no, acct_no, srv_req_date, agree_num, app_date,	" +
					"	       sales_type, sales_cd, dep_waive, on_hold_ind, on_hold_rea_cd, imei,	" +
					"	       warr_start_date, warr_period, create_date, order_status, err_cd,	" +
					"	       err_msg, sales_name, ao_ind, last_update_by, last_update_date,	" +
					"	       create_by, reason_cd, LOB, sign_off_date, staff_num, sales_channel,	" +
					"	       sales_contact_num, boc, sms_no	" +
//					eSignature CR by Eric Ng
					"	       , DIS_MODE, COLLECT_METHOD, ESIG_EMAIL_ADDR, ESIG_EMAIL_LANG	" +
					"	       , SALES_TEAM, CREATE_CHANNEL, submit_date, cpq_txn_id, centre_cd	" +
					"	  FROM bomweb_order	" +
					"	 WHERE order_id = ?	";
		
		ParameterizedRowMapper<OrderDTO> mapper = new ParameterizedRowMapper<OrderDTO>() {

			public OrderDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				OrderDTO dto = new OrderDTO();

				dto.setOrderId(rs.getString("order_id"));
				dto.setOcId(rs.getString("ocid"));
				dto.setSrc(rs.getString("src"));
				dto.setOrderType(rs.getString("order_type"));
				dto.setMsisdn(rs.getString("msisdn"));
				dto.setMsisdnlvl(rs.getString("msisdnlvl"));
				dto.setMnpInd(rs.getString("mnp_ind"));
				dto.setShopCd(rs.getString("shop_cd"));
				dto.setBomCustNo(rs.getString("bom_cust_no"));
				dto.setMobCustNo(rs.getString("mob_cust_no"));
				dto.setAcctNo(rs.getString("acct_no"));
				dto.setSrvReqDate(rs.getDate("srv_req_date"));
				dto.setAgreeNum(rs.getString("agree_num"));
				dto.setAppDate(rs.getTimestamp("app_date"));
				dto.setSalesType(rs.getString("sales_type"));
				dto.setSalesCd(rs.getString("sales_cd"));
				dto.setDepWaive(rs.getString("dep_waive"));
				dto.setOnHoldInd(rs.getString("on_hold_ind"));
				dto.setOnHoldReaCd(rs.getString("on_hold_rea_cd"));
				dto.setImei(rs.getString("imei"));
				dto.setWarrStartDate(rs.getDate("warr_start_date"));
				dto.setWarrPeriod(rs.getString("warr_period"));				
				dto.setOrderStatus(rs.getString("order_status"));
				dto.setErrCd(rs.getString("err_cd"));
				dto.setErrMsg(rs.getString("err_msg"));
				dto.setSalesName(rs.getString("sales_name"));
				dto.setAoInd(rs.getString("ao_ind"));				
				dto.setReasonCd(rs.getString("reason_cd"));
				dto.setLob(rs.getString("lob"));
				dto.setSignOffDate(rs.getTimestamp("sign_off_date"));
				dto.setStaffNum(rs.getString("staff_num"));
				dto.setSalesChannel(rs.getString("sales_channel"));
				dto.setSalesContactNum(rs.getString("sales_contact_num"));
				dto.setCreateBy(rs.getString("create_by"));
				dto.setCreateDate(rs.getTimestamp("create_date"));
				dto.setSalesTeam(rs.getString("sales_team"));
				dto.setCreateChannel(rs.getString("create_channel"));
				dto.setSubmitDate(rs.getTimestamp("submit_date"));
				dto.setCpqTxnId(rs.getString("cpq_txn_id"));
				dto.setCentreCd(rs.getString("centre_cd"));

				//eSignature CR by Eric Ng
				if (rs.getString("DIS_MODE")== null)
					dto.setDisMode(null); 
				else	
					dto.setDisMode(DisMode.valueOf(rs.getString("DIS_MODE")));
					
				if (rs.getString("COLLECT_METHOD") == null)
					dto.setCollectMethod(null);
				else
					dto.setCollectMethod(CollectMethod.valueOf(rs.getString("COLLECT_METHOD")));
				
				dto.setEsigEmailAddr(rs.getString("ESIG_EMAIL_ADDR"));
				
				if (rs.getString("ESIG_EMAIL_LANG") == null)
					dto.setEsigEmailLang(null);
				else
					dto.setEsigEmailLang(EsigEmailLang.valueOf(rs.getString("ESIG_EMAIL_LANG")));
				
				dto.setSMSno(rs.getString("sms_no")); 
				
				return dto;
			}
		};
		
		try {
			logger.debug("getBomWebOrder @ OrderDAO: " + SQL);
			List<OrderDTO> orders = simpleJdbcTemplate
					.query(SQL, mapper, orderId);
			
			return orders;
		
		} catch (Exception e) {
			logger.error("Exception caught in getBomWebOrder()", e);

			throw new DAOException(e.getMessage(), e);
		}		
		
	}
	
	public List<OrderImsDTO> getBomWebOrderIms(String orderId) throws DAOException{
		logger.debug("getBomWebOrder");
		
		String SQL = "	SELECT order_id, login_id, decoder_type, adult_view_allow, target_comm_date,					"+
		"			       prepay_amt, moving_chrg, OT_INSTALL_CHRG ,waived_prepay, cash_fs_prepay,			"+
		"			       now_tv_form_type, fixed_line_no, is_cc_offer, lang_preference,			"+
		"			       process_cc_prepay, process_vim, process_vms, process_wifi, create_by,			"+
		"			       create_date, last_upd_by, last_upd_date, tv_credit, bandwidth,			"+
		"			       primary_acct_no,	INSTALL_INSTALMENT_AMT, INSTALL_INSTALMENT_MTH, 		" +
		"					ot_chrg_type, APPLMTHD, SOURCE_CD,	" +
		"					CALL_DATE, POSITION_NO, FAX_SERIAL,"+
		"					INSTALL_OT_CODE, INSTALL_OT_DESC_EN, INSTALL_OT_DESC_ZH,INSTALL_INSTALMENT_CODE,INSTALL_OT_QTY, " +
		"				   (select decode(count(*),0,'N','Y') chooseNowtvCoupon		"+
		"					from bomweb_subscribed_item bsi,w_item_dtl_ims widi,bomweb_order_ims boi 	"+
		"					where bsi.order_Id=a.order_id	"+
		"					and bsi.id=widi.item_id and widi.is_coupon_plan='Y' 	"+
		"					and boi.order_id=bsi.order_Id	"+
		"					and boi.tv_credit>0) chooseNowtvCoupon	"+
		"					,IMS_ORDER_TYPE,  " +
		"				 DS_TYPE, DS_LOCATION, COOLOFF ,WAIVE_CLOFF, QC_CALL_TIME, WAIVE_QC, SYS_F, TV_PRICE_IND  " +
		"					,tran_code,shop_code,deposit_amt,arpu_in,arpu_out,ride_on_fsa_ind,must_qc_ind,qr_submit_date,PCD_LIKE_IND,PCD_NOW_ONE_IND,PRE_INST_IND,  " +
		//"					,tran_code,shop_code,deposit_amt,arpu_in,arpu_out,ride_on_fsa_ind,must_qc_ind,qr_submit_date,special_request_cd, COLLECT_VI_STB_1, FIELD_IND   " +
		"				 MOBILE_OFFER_IND, MRT, special_request_cd, COLLECT_VI_STB_1, FIELD_IND, PRE_USE_IND, RELATED_FSA, RIDE_ON_FSA_REASON_CD, SERVICE_WAIVER, SALES_MODE, SPECIAL_OTC_ID,  " +
		"				 HK_QR_STRING, HK_QR_URL, OUT_TRADE_NO, CASH_PAY_MTD_TYPE, TRADE_NO, FPS_REFERENCE_ID  " +
		"			  FROM bomweb_order_ims a			"+
		"	 		  WHERE order_id = ?	";

		
		ParameterizedRowMapper<OrderImsDTO> mapper = new ParameterizedRowMapper<OrderImsDTO>() {

			public OrderImsDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				OrderImsDTO dto = new OrderImsDTO();
					
				dto.setOrderId(rs.getString("order_id"));
				dto.setLoginId(rs.getString("login_id"));
				dto.setDecodeType(rs.getString("decoder_type"));
				dto.setAdultViewAllow(rs.getString("adult_view_allow"));
				dto.setTargetCommDate(rs.getDate("target_comm_date"));
				dto.setPrepayAmt(rs.getString("prepay_amt"));
				dto.setMovingChrg(rs.getString("moving_chrg"));
				dto.setOtInstallChrg(rs.getString("OT_INSTALL_CHRG"));
				dto.setWaivedPrepay(rs.getString("waived_prepay"));
				dto.setCashFsPrepay(rs.getString("cash_fs_prepay"));
				dto.setNowTvFormType(rs.getString("now_tv_form_type"));
				dto.setFixedLineNo(rs.getString("fixed_line_no"));
				dto.setIsCreditCardOffer(rs.getString("is_cc_offer"));
				dto.setLangPreference(rs.getString("lang_preference"));
				dto.setProcessCreditCard(rs.getString("process_cc_prepay"));
				dto.setProcessVim(rs.getString("process_vim"));
				dto.setProcessVms(rs.getString("process_vms"));
				dto.setProcessWifi(rs.getString("process_wifi"));				
				dto.setTvCredit(rs.getString("tv_credit"));
				dto.setBandwidth(rs.getString("bandwidth"));
				dto.setPrimaryAcctNo(rs.getString("primary_acct_no"));
				dto.setInstallmentChrg(rs.getString("INSTALL_INSTALMENT_AMT"));
				dto.setInstallmentMonth(rs.getString("INSTALL_INSTALMENT_MTH"));
				dto.setChooseNowtvCoupon(rs.getString("chooseNowtvCoupon"));
				dto.setImsOrderType(rs.getString("IMS_ORDER_TYPE"));
				dto.setOtChrgType(rs.getString("OT_CHRG_TYPE"));
				dto.setAppMethod(rs.getString("APPLMTHD"));
				dto.setSourceCd(rs.getString("SOURCE_CD"));
				dto.setPositionNo(rs.getString("POSITION_NO"));
				dto.setCallDate(rs.getTimestamp("CALL_DATE"));
				dto.setFAXno(rs.getString("FAX_SERIAL"));
				dto.setInstallOtCode(rs.getString("install_ot_code"));
		    	dto.setInstallOTDesc_en(rs.getString("install_ot_desc_en"));
		    	dto.setInstallOTDesc_zh(rs.getString("install_ot_desc_zh"));
		    	dto.setInstallmentCode(rs.getString("INSTALL_INSTALMENT_CODE"));
		    	dto.setInstallOtQty(rs.getString("INSTALL_OT_QTY"));
		    	dto.setDsType(rs.getString("DS_TYPE"));
		    	dto.setDsLocation(rs.getString("DS_LOCATION"));
		    	dto.setDsCoolOff(rs.getString("COOLOFF"));
		    	dto.setDsWaiveCoolOff(rs.getString("WAIVE_CLOFF"));
		    	dto.setDsQcCallTime(rs.getString("QC_CALL_TIME"));
		    	dto.setDsWaiveQC(rs.getString("WAIVE_QC"));
		    	dto.setSysF(rs.getString("SYS_F"));
		    	dto.setTvPriceInd(rs.getString("TV_PRICE_IND"));
		    	dto.setTranCode(rs.getString("tran_code"));
		    	dto.setShopCode(rs.getString("shop_code"));
		    	dto.setDepositAmt(rs.getString("deposit_amt"));
		    	dto.setARPU_in(rs.getString("arpu_in"));
		    	dto.setARPU_out(rs.getString("arpu_out"));
		    	dto.setRide_on_FSA_Ind(rs.getString("ride_on_fsa_ind"));
		    	dto.setMust_QC_Ind(rs.getString("must_qc_ind"));
		    	dto.setQr_submit_date(rs.getTimestamp("qr_submit_date"));
		    	dto.setPcdLike100Ind(rs.getString("PCD_LIKE_IND"));
		    	dto.setPcdNowOneBoxInd(rs.getString("PCD_NOW_ONE_IND"));
		    	dto.setPreInstallInd(rs.getString("PRE_INST_IND"));
		    	dto.setMobileOfferInd(rs.getString("MOBILE_OFFER_IND"));
		    	dto.setMrt(rs.getString("MRT"));
		    	dto.setSpecialRequestCd(rs.getString("special_request_cd"));
		    	dto.setCollectViStb1(rs.getString("COLLECT_VI_STB_1"));
		    	dto.setFieldInd(rs.getString("FIELD_IND"));
		    	dto.setPreUseInd(rs.getString("PRE_USE_IND"));
		    	dto.setRelated_FSA(rs.getString("RELATED_FSA"));
		    	dto.setRide_on_FSA_reason_Cd(rs.getString("RIDE_ON_FSA_REASON_CD"));
		    	dto.setServiceWaiver(rs.getString("SERVICE_WAIVER"));
		    	dto.setMode(rs.getString("SALES_MODE"));
		    	dto.setSpecialOTCId(rs.getString("SPECIAL_OTC_ID"));
		    	dto.setHkqrString(rs.getString("HK_QR_STRING"));
		    	dto.setHkqrUrl(rs.getString("HK_QR_URL"));
		    	dto.setOutTradeNo(rs.getString("OUT_TRADE_NO"));
		    	dto.setCashPayMtdType(rs.getString("CASH_PAY_MTD_TYPE"));
		    	dto.setTradeNo(rs.getString("TRADE_NO"));
		    	dto.setFpsReferenceId(rs.getString("FPS_REFERENCE_ID"));
				return dto;
			}
		};
		
		try {
			logger.debug("getBomWebOrder @ OrderDAO: " + SQL);
			List<OrderImsDTO> orders = simpleJdbcTemplate
					.query(SQL, mapper, orderId);
			
			return orders;
		
		} catch (Exception e) {
			logger.error("Exception caught in getBomWebOrder()", e);

			throw new DAOException(e.getMessage(), e);
		}

	}
	
	public List<AppointmentDTO> getBomWebAppointment(String orderId) throws DAOException{
		logger.debug("getBomWebAppointment");
		String SQL = "	SELECT order_id, dtl_id, serial_num, appnt_start_time, appnt_end_time,	" +
		"	       inst_contact_name, inst_contact_num, inst_sms_num, exact_appnt_time,	" +
		"	       forced_delay_ind, pre_wiring_start_time, pre_wiring_end_time,	" +
		"	       pre_wiring_type, tid_ind, create_by, create_date, last_upd_by,	" +
		"	       last_upd_date, appnt_type, bmo_lead_day, align_bill_date	" +
		"	  FROM bomweb_appointment	" +
		"	 WHERE order_id = ?	";
		
		ParameterizedRowMapper<AppointmentDTO> mapper = new ParameterizedRowMapper<AppointmentDTO>() {

			public AppointmentDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				AppointmentDTO dto = new AppointmentDTO();
					
				dto.setOrderId(rs.getString("order_id"));
				dto.setDtlId(rs.getString("dtl_id"));
				dto.setSerialNum(rs.getString("serial_num"));
				dto.setAppntStartDate(rs.getTimestamp("appnt_start_time"));
				dto.setAppntEndDate(rs.getTimestamp("appnt_end_time"));
				dto.setInstContactName(rs.getString("inst_contact_name"));
				dto.setInstContactNum(rs.getString("inst_contact_num"));
				dto.setInstSmsNum(rs.getString("inst_sms_num"));
				dto.setExactAppntTime(rs.getTimestamp("exact_appnt_time"));
				dto.setForcedDelayInd(rs.getString("forced_delay_ind"));
				dto.setPreWiringStartTime(rs.getTimestamp("pre_wiring_start_time"));
				dto.setPreWiringEndTime(rs.getTimestamp("pre_wiring_end_time"));
				dto.setPreWiringType(rs.getString("pre_wiring_type"));
				dto.setTidInd(rs.getString("tid_ind"));				
				dto.setAppntType(rs.getString("appnt_type"));
				dto.setBmoLeadDay(rs.getString("bmo_lead_day"));
				dto.setAlignWithBillDate(rs.getString("align_bill_date"));
				return dto;
			}
		};
		
		try {
			logger.debug("getBomWebAppointment @ OrderDAO: " + SQL);
			List<AppointmentDTO> orders = simpleJdbcTemplate
					.query(SQL, mapper, orderId);
			
			return orders;
		
		} catch (Exception e) {
			logger.error("Exception caught in getBomWebAppointment()", e);
			throw new DAOException(e.getMessage(), e);
		}

	}
	
	public List<RemarkDTO> getRemark(String orderId) throws DAOException{
		logger.debug("getRemark");
		String SQL = "	SELECT order_id, dtl_id, rmk_type, rmk_seq, rmk_dtl, create_by, create_date,	" +
		"	       last_upd_by, last_upd_date	" +
		"	  FROM bomweb_remark	" +
		"	 WHERE order_id = ?	" ;
		
		ParameterizedRowMapper<RemarkDTO> mapper = new ParameterizedRowMapper<RemarkDTO>() {

			public RemarkDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				RemarkDTO dto = new RemarkDTO();
					
				dto.setOrderId(rs.getString("order_id"));
				dto.setDtlId(rs.getString("dtl_id"));
				dto.setRmkType(rs.getString("rmk_type"));
				dto.setRmkSeq(rs.getString("rmk_seq"));
				dto.setRmkDtl(rs.getString("rmk_dtl"));
				dto.setCreateDate(rs.getTimestamp("create_date"));
				dto.setCreateBy(rs.getString("create_by"));

				return dto;
			}
		};
		
		try {
			logger.debug("getRemark @ OrderDAO: " + SQL);
			List<RemarkDTO> orders = simpleJdbcTemplate
					.query(SQL, mapper, orderId);
			
			return orders;
		
		} catch (Exception e) {
			logger.error("Exception caught in getRemark()", e);
			throw new DAOException(e.getMessage(), e);
		}

	}
	
	public List<CustomerDTO> getBomWebCustomer(String orderId) throws DAOException{
		
		logger.debug("getBomWebCustomer");
		String SQL = "	SELECT bc.order_id, bc.cust_no, bc.mob_cust_no, bc.first_name, bc.last_name, bc.id_doc_type,	" +
		"	       bc.id_doc_num, bc.dob, bc.title, bc.company_name, bc.ind_type, bc.ind_sub_type,	" +
		"	       bc.nationality, bc.create_date, bc.addr_proof_ind, LOB, bc.service_num,bc.create_by,	" +
		"	       bc.last_upd_by, bc.last_upd_date, bc.id_verified_ind, bc.blacklist_ind,	" +
		"          bc.cs_portal_ind, bc.cs_portal_login, bc.cs_portal_mobile , bc.mismatch_ind, bc.the_club_login, " +
		"		   bc.the_club_ind, bc.the_club_mobile, bc.NOWID_IND, bc.NOWID_LOGIN, bc.hkt_opt_out, " +
		"		   bc.club_opt_out, bc.club_opt_rea, bc.club_opt_rmk, "	 +
		"		   bcir.CARECASH_IND, bcir.CARECASH_DM_IND, bcir.EMAIL_ADDR, bcir.CONTACT_NUM,bcir.STATUS,bcir.RTN_MSG	"+	
		"	  FROM bomweb_customer bc "+
		"     left join BOMWEB_CUST_IGUARD_REG bcir on (bc.order_id = bcir.order_id) " +
		"	 WHERE bc.order_id = ?	" ;

		
		ParameterizedRowMapper<CustomerDTO> mapper = new ParameterizedRowMapper<CustomerDTO>() {

			public CustomerDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				CustomerDTO dto = new CustomerDTO();
					
				dto.setOrderId(rs.getString("order_id"));
				dto.setCustNo(rs.getString("cust_no"));
				dto.setMobCustNo(rs.getString("mob_cust_no"));
				dto.setFirstName(rs.getString("first_name"));
				dto.setLastName(rs.getString("last_name"));
				dto.setIdDocType(rs.getString("id_doc_type"));
				dto.setIdDocNum(rs.getString("id_doc_num"));
				dto.setDob(rs.getDate("dob"));
				dto.setTitle(rs.getString("title"));
				dto.setCompanyName(rs.getString("company_name"));
				dto.setIndType(rs.getString("ind_type"));
				dto.setIndSubType(rs.getString("ind_sub_type"));
				dto.setNationality(rs.getString("nationality"));				
				dto.setAddrProofInd(rs.getString("addr_proof_ind"));
				dto.setLob(rs.getString("lob"));
				dto.setServiceNum(rs.getString("service_num"));				
				dto.setIdVerified(rs.getString("id_verified_ind"));
				dto.setBlacklistInd(rs.getString("blacklist_ind"));	
				dto.setCsPortalInd(rs.getString("cs_portal_ind"));///Gary
				dto.setCsPortalLogin(rs.getString("cs_portal_login"));
				dto.setTheClubLogin(rs.getString("the_club_login"));
				dto.setTheClubInd(rs.getString("the_club_ind"));
				dto.setTheClubMoblie(rs.getString("the_club_mobile"));
				dto.setCsPortalMobile(rs.getString("cs_portal_mobile"));
				dto.setExistingCustomerConflictWithName(rs.getString("mismatch_ind"));
				dto.setIsRegNowId(rs.getString("NOWID_IND"));
				dto.setNowId(rs.getString("NOWID_LOGIN"));
				dto.setCsPortalOptOutInd(rs.getString("hkt_opt_out"));
				dto.setTheClubOptOutInd(rs.getString("club_opt_out"));
				dto.setOptoutTheClubReason(rs.getString("club_opt_rea"));
				dto.setOptoutTheClubRmk(rs.getString("club_opt_rmk"));
				
				dto.setCareCashInd(rs.getString("CARECASH_IND"));
				dto.setCareCashOptOutInd(rs.getString("CARECASH_DM_IND"));
				dto.setCareCashEmail(rs.getString("EMAIL_ADDR"));
				dto.setCareCashMobile(rs.getString("CONTACT_NUM"));		
				dto.setCareCashRegStatus(rs.getString("STATUS"));		
				dto.setCareCashRtnMsg(rs.getString("RTN_MSG"));		
				
				return dto;
			}
		};
		
		try {
			logger.debug("getBomWebCustomer @ OrderDAO: " + SQL);
			List<CustomerDTO> orders = simpleJdbcTemplate
					.query(SQL, mapper, orderId);
			
			return orders;
		
		} catch (Exception e) {
			logger.error("Exception caught in getBomWebCustomer()", e);
			throw new DAOException(e.getMessage(), e);
		}
		
	}
	
	public List<AcctDTO> getBomWebAcct(String orderId) throws DAOException{
		logger.debug("getBomWebAcct");
		String SQL = "	SELECT order_id, cust_no, acct_name, bill_freq, bill_lang, sms_no, email_addr,	" +
		"	       acct_no, create_date, bill_period, create_by, last_upd_by,	" +
		//"	       last_upd_date, dtl_id, bill_media	" +
		"	       last_upd_date, '1' dtl_id, bill_media, exist_bill_media, action	" +
		"	  FROM bomweb_acct	" +
		"	 WHERE order_id = ?	" ;


		
		ParameterizedRowMapper<AcctDTO> mapper = new ParameterizedRowMapper<AcctDTO>() {

			public AcctDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				AcctDTO dto = new AcctDTO();
					
				dto.setOrderId(rs.getString("order_id"));
				dto.setCustNo(rs.getString("cust_no"));
				dto.setAcctName(rs.getString("acct_name"));
				dto.setBillFreq(rs.getString("bill_freq"));
				dto.setBillLang(rs.getString("bill_lang"));
				dto.setSmsNo(rs.getString("sms_no"));
				dto.setEmailAddr(rs.getString("email_addr"));
				dto.setAcctNo(rs.getString("acct_no"));				
				dto.setBillPeriod(rs.getString("bill_period"));				
				dto.setDtlId(rs.getString("dtl_id"));
				dto.setBillMedia(rs.getString("bill_media"));
				dto.setExistingBillingAddress(rs.getString("exist_bill_media"));
				dto.setUpdateBillingMethod(rs.getString("action"));			

				return dto;
			}
		};
		
		try {
			logger.debug("getBomWebAcct @ OrderDAO: " + SQL);
			List<AcctDTO> orders = simpleJdbcTemplate
					.query(SQL, mapper, orderId);
			
			return orders;
		
		} catch (Exception e) {
			logger.error("Exception caught in getBomWebAcct()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public List<PaymentDTO> getBomWebPayment(String orderId) throws DAOException{
		logger.debug("getBomWebPayment");
		String SQL = "	SELECT order_id, cust_no, pay_mtd_key, acct_no, autopay_app_date,	" +
		"	       autopay_up_lim_amt, b_acct_no, b_acct_hold_name, b_acct_hold_id_type,	" +
		"	       b_acct_hold_id_num, branch_cd, bank_cd, pay_mtd_type, third_party_ind,	" +
		"	       cc_type, cc_num, cc_hold_name, cc_exp_date, cc_issue_bank,	" +
		"	       cc_id_doc_type, cc_id_doc_no, create_date, create_by, last_upd_by,	" +
		"	       last_upd_date, cc_verified_ind, dtl_id, action	" +
		"	  FROM bomweb_payment	" +
		"	 WHERE order_id = ?	";


		
		ParameterizedRowMapper<PaymentDTO> mapper = new ParameterizedRowMapper<PaymentDTO>() {

			public PaymentDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				PaymentDTO dto = new PaymentDTO();
					
				dto.setOrderId(rs.getString("order_id"));
				dto.setCustNo(rs.getString("cust_no"));
				dto.setPayMtdKey(rs.getString("pay_mtd_key"));
				dto.setAcctNo(rs.getString("acct_no"));
				dto.setAutopayAppDate(rs.getDate("autopay_app_date"));
				dto.setAutopayUpLimAmt(rs.getString("autopay_up_lim_amt"));
				dto.setBAcctNo(rs.getString("b_acct_no"));
				dto.setBAcctHoldName(rs.getString("b_acct_hold_name"));
				dto.setBAcctHoldIdType(rs.getString("b_acct_hold_id_type"));
				dto.setBAcctHoldIdNum(rs.getString("b_acct_hold_id_num"));
				dto.setBranchCd(rs.getString("branch_cd"));
				dto.setBankCd(rs.getString("bank_cd"));
				dto.setPayMtdType(rs.getString("pay_mtd_type"));
				dto.setThirdPartyInd(rs.getString("third_party_ind"));
				dto.setCcType(rs.getString("cc_type"));
				dto.setCcNum(rs.getString("cc_num"));
				dto.setCcHoldName(rs.getString("cc_hold_name"));
				dto.setCcExpDate(rs.getString("cc_exp_date"));
				dto.setCcIssueBank(rs.getString("cc_issue_bank"));
				dto.setCcIdDocType(rs.getString("cc_id_doc_type"));
				dto.setCcIdDocNo(rs.getString("cc_id_doc_no"));				
				dto.setCcVerified(rs.getString("cc_verified_ind"));
				dto.setDtlId(rs.getString("dtl_id"));
				dto.setAction(rs.getString("action"));			

				return dto;
			}
		};
		
		try {
			logger.debug("getBomWebPayment @ OrderDAO: " + SQL);
			List<PaymentDTO> orders = simpleJdbcTemplate
					.query(SQL, mapper, orderId);
			
			return orders;
		
		} catch (Exception e) {
			logger.error("Exception caught in getBomWebPayment()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public List<CustOptoutInfoDTO> getCustOptoutInfo(String orderId) throws DAOException{
		logger.debug("getCustOptoutInfo");
		String SQL = "	SELECT order_id, cust_no, direct_mailing, outbound, sms, email, web_bill,	" +
		"	       nonsales_sms, internet, create_by, create_date, last_upd_by,	" +
		"	       last_upd_date	" +
		"	  FROM bomweb_cust_optout_info	" +
		"	 WHERE order_id = ?	";


		
		ParameterizedRowMapper<CustOptoutInfoDTO> mapper = new ParameterizedRowMapper<CustOptoutInfoDTO>() {

			public CustOptoutInfoDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				CustOptoutInfoDTO dto = new CustOptoutInfoDTO();
					
				dto.setOrderId(rs.getString("order_id"));
				dto.setCustNo(rs.getString("cust_no"));
				dto.setDirectMailing(rs.getString("direct_mailing"));
				dto.setOutbound(rs.getString("outbound"));
				dto.setSms(rs.getString("sms"));
				dto.setEmail(rs.getString("email"));
				dto.setWebBill(rs.getString("web_bill"));
				dto.setNonsalesSms(rs.getString("nonsales_sms"));
				dto.setInternet(rs.getString("internet"));				

				return dto;
			}
		};
		
		try {
			logger.debug("getCustOptoutInfo @ OrderDAO: " + SQL);
			List<CustOptoutInfoDTO> orders = simpleJdbcTemplate
					.query(SQL, mapper, orderId);
			
			return orders;
		
		} catch (Exception e) {
			logger.error("Exception caught in getCustOptoutInfo()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public List<ContactDTO> getBomWebContact(String orderId) throws DAOException{
		logger.debug("getBomWebContact");
		String SQL = "	SELECT order_id, title, contact_name, contact_phone, email_addr, action_ind,	" +
		"	       create_date, other_phone, create_by, last_upd_by, last_upd_date, NOWTV_DELIVERY_MOBILE_NUM	" +
		"	  FROM bomweb_contact	" +
		"	 WHERE order_id = ?	" ;



		
		ParameterizedRowMapper<ContactDTO> mapper = new ParameterizedRowMapper<ContactDTO>() {

			public ContactDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				ContactDTO dto = new ContactDTO();
					
				dto.setOrderId(rs.getString("order_id"));
				dto.setTitle(rs.getString("title"));
				dto.setContactName(rs.getString("contact_name"));
				dto.setContactPhone(rs.getString("contact_phone"));
				dto.setEmailAddr(rs.getString("email_addr"));
				dto.setActionInd(rs.getString("action_ind"));
				dto.setOtherPhone(rs.getString("other_phone"));
				dto.setDeliveryPhone(rs.getString("NOWTV_DELIVERY_MOBILE_NUM"));

				return dto;
			}
		};
		
		try {
			logger.debug("getBomWebContact @ OrderDAO: " + SQL);
			List<ContactDTO> orders = simpleJdbcTemplate
					.query(SQL, mapper, orderId);
			
			return orders;
		
		} catch (Exception e) {
			logger.error("Exception caught in getBomWebContact()", e);
			throw new DAOException(e.getMessage(), e);
		}
		
	}
	
	public List<CustAddrDTO> getBomWebCustAddr(String orderId) throws DAOException{
		logger.debug("getBomWebCustAddr");
		//String SQL = "	SELECT order_id, addr_usage, flat, area_cd, dist_no, sect_cd, str_name,	" +
		String SQL = "	SELECT order_id, addr_usage, area_cd, dist_no, sect_cd, str_name,	" +
		"	       hi_lot_no, str_cat_cd, build_no, foreign_addr_flag, floor_no, unit_no,	" +
		"	       po_box_no, addr_type, str_no, sect_dep_ind, create_date, area_desc,	" +
		"	       dist_desc, sect_desc, str_cat_desc, create_by, last_upd_by,	" +
		"	       last_upd_date, serbdyno, blacklist_ind, dtl_id	" +
		"	  FROM bomweb_cust_addr	" +
		"	 WHERE order_id = ?	" ;
		
		ParameterizedRowMapper<CustAddrDTO> mapper = new ParameterizedRowMapper<CustAddrDTO>() {

			public CustAddrDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				CustAddrDTO dto = new CustAddrDTO();
					
				dto.setOrderId(rs.getString("order_id"));
				dto.setAddrUsage(rs.getString("addr_usage"));
				//dto.setFlat(rs.getString("flat"));
				dto.setAreaCd(rs.getString("area_cd"));
				dto.setDistNo(rs.getString("dist_no"));
				dto.setSectCd(rs.getString("sect_cd"));
				dto.setStrName(rs.getString("str_name"));
				dto.setHiLotNo(rs.getString("hi_lot_no"));
				dto.setStrCatCd(rs.getString("str_cat_cd"));
				dto.setBuildNo(rs.getString("build_no"));
				dto.setForeignAddrFlag(rs.getString("foreign_addr_flag"));
				dto.setFloorNo(rs.getString("floor_no"));
				dto.setUnitNo(rs.getString("unit_no"));
				dto.setPoBoxNo(rs.getString("po_box_no"));
				dto.setAddrType(rs.getString("addr_type"));
				dto.setStrNo(rs.getString("str_no"));
				dto.setSectDepInd(rs.getString("sect_dep_ind"));				
				dto.setAreaDesc(rs.getString("area_desc"));
				dto.setDistDesc(rs.getString("dist_desc"));
				dto.setSectDesc(rs.getString("sect_desc"));
				dto.setStrCatDesc(rs.getString("str_cat_desc"));				
				dto.setSerbdyno(rs.getString("serbdyno"));
				dto.setBlacklistInd(rs.getString("blacklist_ind"));
				dto.setDtlId(rs.getString("dtl_id"));
			

				return dto;
			}
		};
		
		try {
			logger.debug("getBomWebCustAddr @ OrderDAO: " + SQL);
			List<CustAddrDTO> orders = simpleJdbcTemplate
					.query(SQL, mapper, orderId);
			
			return orders;
		
		} catch (Exception e) {
			logger.error("Exception caught in getBomWebCustAddr()", e);
			throw new DAOException(e.getMessage(), e);
		}
		
	}
		
	public List<AddrInventoryDTO> getAddrInventory(String orderId) throws DAOException{
		logger.debug("getAddrInventory");
		String SQL = "	SELECT order_id, dtl_id, addr_usage, resource_shortage_ind, technology,	" +
		"	       building_type, n2_building_ind, field_work_permit_day, addr_id,	" +
		"	       max_bandwidth, fttc_ind, h264_ind, adsl_18m_ind, create_by, create_date, last_upd_by,	" +
		"	       last_upd_date, ntv_coverage, adsl_8m_ind " +
		"	  FROM bomweb_addr_inventory	" +
		"	 WHERE order_id = ?	" ;
		
		ParameterizedRowMapper<AddrInventoryDTO> mapper = new ParameterizedRowMapper<AddrInventoryDTO>() {

			public AddrInventoryDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				AddrInventoryDTO dto = new AddrInventoryDTO();
					
				dto.setOrderId(rs.getString("order_id"));
				dto.setDtlId(rs.getString("dtl_id"));
				dto.setAddrUsage(rs.getString("addr_usage"));
				dto.setResourceShortage(rs.getString("resource_shortage_ind"));
				dto.setTechnology(rs.getString("technology"));
				dto.setBuildingType(rs.getString("building_type"));
				dto.setN2BuildingInd(rs.getString("n2_building_ind"));
				dto.setFieldPermitDay(rs.getString("field_work_permit_day"));
				dto.setAddrId(rs.getString("addr_id"));
				dto.setMaxBandwidth(rs.getString("max_bandwidth"));
				dto.setFttcInd(rs.getString("fttc_ind"));				
				dto.setH264Ind(rs.getString("h264_ind"));		
				dto.setAdsl18MInd(rs.getString("adsl_18m_ind"));				
				dto.setNtvCoverage(rs.getString("ntv_coverage"));		
				dto.setAdsl8MInd(rs.getString("adsl_8m_ind"));

				return dto;
			}
		};
		
		try {
			logger.debug("getAddrInventory @ OrderDAO: " + SQL);
			List<AddrInventoryDTO> orders = simpleJdbcTemplate
					.query(SQL, mapper, orderId);
			
			return orders;
		
		} catch (Exception e) {
			logger.error("Exception caught in getAddrInventory()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public List<SubscribedItemDTO> getSubscribedItem(String orderId) throws DAOException{
		logger.debug("getSubscribedItem");
		String SQL = "	SELECT order_id, ID, TYPE, create_date, basket_id, create_by, last_upd_by,	" +
		"	       last_upd_date	" +
		" , CAMPAIGN_CD, PACK_CD, TOP_UP " +
		"	  FROM bomweb_subscribed_item	" +
		"	 WHERE order_id = ?	";
		
		ParameterizedRowMapper<SubscribedItemDTO> mapper = new ParameterizedRowMapper<SubscribedItemDTO>() {

			public SubscribedItemDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				SubscribedItemDTO dto = new SubscribedItemDTO();
					
				dto.setOrderId(rs.getString("order_id"));
				dto.setId(rs.getString("id"));
				dto.setType(rs.getString("type"));				
				dto.setBasketId(rs.getString("basket_id"));
				dto.setCreateDate(rs.getTimestamp("create_date"));
				dto.setCreateBy(rs.getString("create_by"));
				dto.setLastUpdBy(rs.getString("last_upd_by"));
				dto.setLastUpdDate(rs.getTimestamp("last_upd_date"));
				dto.setCampaignCd(rs.getString("campaign_cd"));
				dto.setPackCd(rs.getString("pack_cd"));
				dto.setTopUpCd(rs.getString("top_up"));				

				return dto;
			}
		};
		
		try {
			logger.debug("getSubscribedItem @ OrderDAO: " + SQL);
			List<SubscribedItemDTO> orders = simpleJdbcTemplate
					.query(SQL, mapper, orderId);
			
			return orders;
		
		} catch (Exception e) {
			logger.error("Exception caught in getSubscribedItem()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public List<SubscribedItemDTO> getSubscribedItemSync(String orderId) throws DAOException{
		logger.debug("getSubscribedItemSync");
		String SQL = "	SELECT bsi.order_id, bsi.ID, bsi.TYPE, bsi.create_date, bsi.basket_id,	"+
		"	       bsi.create_by, bsi.last_upd_by, bsi.last_upd_date, wioa.offer_id,	"+
		"	       wioa.offer_sub_id, wiopa.product_id, widi.plan_id, widi.program_id,	"+
		"	       widi.incentive_id	"+
		"	  FROM bomweb_subscribed_item bsi,	"+
		"	       w_item_offer_assgn wioa,	"+
		"	       w_item_offer_product_assgn wiopa,	"+
		"	       w_item_dtl_ims widi	"+
		"	 WHERE bsi.order_id = ?	"+
		"	   AND bsi.ID = wioa.item_id	"+
		"	   AND wioa.item_id = wiopa.item_id	"+
		"	   AND wioa.item_offer_seq = wiopa.item_offer_seq	"+
		"	   AND bsi.ID = widi.item_id(+)	";
		
		ParameterizedRowMapper<SubscribedItemDTO> mapper = new ParameterizedRowMapper<SubscribedItemDTO>() {

			public SubscribedItemDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				SubscribedItemDTO dto = new SubscribedItemDTO();
					
				dto.setOrderId(rs.getString("order_id"));
				dto.setId(rs.getString("id"));
				dto.setType(rs.getString("type"));				
				dto.setBasketId(rs.getString("basket_id"));
				dto.setOfferId(rs.getString("offer_id"));
				dto.setOfferSubId(rs.getString("offer_sub_id"));
				dto.setProductId(rs.getString("product_id"));
				dto.setPlanId(rs.getString("plan_id"));
				dto.setProgramId(rs.getString("program_id"));
				dto.setIncentiveId(rs.getString("incentive_id"));

				return dto;
			}
		};
		
		try {
			logger.debug("getSubscribedItem @ OrderDAO: " + SQL);
			List<SubscribedItemDTO> orders = simpleJdbcTemplate
					.query(SQL, mapper, orderId);
			
			return orders;
		
		} catch (Exception e) {
			logger.error("Exception caught in getSubscribedItem()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public List<ComponentDTO> getComponent(String orderId) throws DAOException{
		logger.debug("getComponent");
		String SQL = "	SELECT a.order_id, a.attb_id, a.attb_value, a.create_date, a.create_by, a.last_upd_by,	" +
		"	       a.last_upd_date, b.id item_id	" +
		"	  FROM w_product_attb_assgn d, w_item_offer_product_assgn c, bomweb_subscribed_item b,	bomweb_component a	" +
		"	 WHERE a.order_id = ?	" +
		"	 and a.order_id = b.order_id " +
		"	 and b.id = c.item_id " +
		"	 and c.product_id = d.product_id " +	
		"	 and a.attb_id = d.attb_id ";

		
		ParameterizedRowMapper<ComponentDTO> mapper = new ParameterizedRowMapper<ComponentDTO>() {

			public ComponentDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				ComponentDTO dto = new ComponentDTO();
					
				dto.setOrderId(rs.getString("order_id"));
				dto.setAttbId(rs.getString("attb_id"));
				dto.setAttbValue(rs.getString("attb_value"));						
				dto.setItemId(rs.getString("item_id"));

				return dto;
			}
		};
		
		try {
			logger.debug("getComponent @ OrderDAO: " + SQL);
			List<ComponentDTO> orders = simpleJdbcTemplate
					.query(SQL, mapper, orderId);
			
			return orders;
		
		} catch (Exception e) {
			logger.error("Exception caught in getComponent()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	
	public List<SubscribedCslOfferDTO> getSubscribedCslOffer(String orderId) throws DAOException{
		logger.debug("getSubscribedCslOffer");
		String SQL = "	SELECT ORDER_ID, PRD_ID, ITEM_ID, VAS_TYPE, PARM_A, PARM_A_CD," +
					" PARM_B, PARM_B_CD, CREATE_BY, CREATE_DATE, LAST_UPD_BY, LAST_UPD_DATE	" +
		"	  FROM bomweb_vas_parm_ims	" +
		"	 WHERE ORDER_ID = ?	" +
		"	 and vas_type = 'MOBILEPIN'" ;
		
		ParameterizedRowMapper<SubscribedCslOfferDTO> mapper = new ParameterizedRowMapper<SubscribedCslOfferDTO>() {

			public SubscribedCslOfferDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				SubscribedCslOfferDTO dto = new SubscribedCslOfferDTO();
					
				dto.setOrder_id(rs.getString("ORDER_ID"));
				dto.setProd_id(rs.getString("PRD_ID"));
				dto.setItem_id(rs.getString("ITEM_ID"));
				dto.setVas_type(rs.getString("VAS_TYPE"));
				dto.setVas_parm_a(rs.getString("PARM_A"));
				dto.setVas_parm_a_cd(rs.getString("PARM_A_CD"));
				dto.setVas_parm_b(rs.getString("PARM_B"));
				dto.setVas_parm_b_cd(rs.getString("PARM_B_CD"));
				dto.setCreateDate(rs.getTimestamp("CREATE_DATE"));
				dto.setCreateBy(rs.getString("CREATE_BY"));
				dto.setLastUpdBy(rs.getString("LAST_UPD_BY"));
				dto.setLastUpdDate(rs.getTimestamp("LAST_UPD_DATE"));
				
				return dto;
			}
		};
		
		try {
			logger.debug("getSubscribedCslOffer @ OrderDAO: " + SQL);
			List<SubscribedCslOfferDTO> orders = simpleJdbcTemplate
					.query(SQL, mapper, orderId);
			
			return orders;
		
		} catch (Exception e) {
			logger.error("Exception caught in getSubscribedCslOffer()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
			
	public List<SubscribedCslOfferDTO> getSubscribedImsVasParm(String orderId) throws DAOException{
		logger.debug("getSubscribedCslOffer");
		String SQL = "	SELECT ORDER_ID, PRD_ID, ITEM_ID, VAS_TYPE, PARM_A, PARM_A_CD," +
					" PARM_B, PARM_B_CD, PARM_C, PARM_C_CD, CREATE_BY, CREATE_DATE, LAST_UPD_BY, LAST_UPD_DATE	" +
		"	  FROM bomweb_vas_parm_ims	" +
		"	 WHERE ORDER_ID = ?	" +
		"	 and vas_type <> 'MOBILEPIN'" ;
		
		ParameterizedRowMapper<SubscribedCslOfferDTO> mapper = new ParameterizedRowMapper<SubscribedCslOfferDTO>() {

			public SubscribedCslOfferDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				SubscribedCslOfferDTO dto = new SubscribedCslOfferDTO();
					
				dto.setOrder_id(rs.getString("ORDER_ID"));
				dto.setProd_id(rs.getString("PRD_ID"));
				dto.setItem_id(rs.getString("ITEM_ID"));
				dto.setVas_type(rs.getString("VAS_TYPE"));
				dto.setVas_parm_a(rs.getString("PARM_A"));
				dto.setVas_parm_a_cd(rs.getString("PARM_A_CD"));
				dto.setVas_parm_b(rs.getString("PARM_B"));
				dto.setVas_parm_b_cd(rs.getString("PARM_B_CD"));
				dto.setVas_parm_c(rs.getString("PARM_C"));
				dto.setVas_parm_c_cd(rs.getString("PARM_C_CD"));
				dto.setCreateDate(rs.getTimestamp("CREATE_DATE"));
				dto.setCreateBy(rs.getString("CREATE_BY"));
				dto.setLastUpdBy(rs.getString("LAST_UPD_BY"));
				dto.setLastUpdDate(rs.getTimestamp("LAST_UPD_DATE"));
				
				return dto;
			}
		};
		
		try {
			logger.debug("getSubscribedCslOffer @ OrderDAO: " + SQL);
			List<SubscribedCslOfferDTO> orders = simpleJdbcTemplate
					.query(SQL, mapper, orderId);
			
			return orders;
		
		} catch (Exception e) {
			logger.error("Exception caught in getSubscribedCslOffer()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public List<ComponentDTO> getComponentSync(String orderId) throws DAOException{
		logger.debug("getComponentSync");
		String SQL = "	SELECT   bc.order_id, bc.attb_id, bc.attb_value, bc.create_date, bc.create_by,	"+
		"	         bc.last_upd_by, bc.last_upd_date, bsi.ID item_id, wiopa.product_id	"+
		"	    FROM bomweb_subscribed_item bsi,	"+
		"	         w_item_offer_product_assgn wiopa,	"+
		"	         w_product_attb_assgn wpaa,	"+
		"	         bomweb_component bc	"+
		"	   WHERE bsi.order_id = ?	"+
		"	     AND bsi.ID = wiopa.item_id	"+
		"	     AND wpaa.product_id = wiopa.product_id	"+
		"	     AND bc.order_id = bsi.order_id	"+
		"	     AND bc.attb_id = wpaa.attb_id	";
		
		ParameterizedRowMapper<ComponentDTO> mapper = new ParameterizedRowMapper<ComponentDTO>() {

			public ComponentDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				ComponentDTO dto = new ComponentDTO();
					
				dto.setOrderId(rs.getString("order_id"));
				dto.setAttbId(rs.getString("attb_id"));
				dto.setAttbValue(rs.getString("attb_value"));
				dto.setItemId(rs.getString("item_id"));
				dto.setProductId(rs.getString("product_id"));

				return dto;
			}
		};
		
		try {
			logger.debug("getComponentSync @ OrderDAO: " + SQL);
			List<ComponentDTO> orders = simpleJdbcTemplate
					.query(SQL, mapper, orderId);
			
			return orders;
		
		} catch (Exception e) {
			logger.error("Exception caught in getComponentSync()", e);
			throw new DAOException(e.getMessage(), e);
		}

	}
	
	
	
	
	
	public List<SubscribedChannelDTO> getSubscribedChannel(String orderId) throws DAOException{
		logger.debug("getSubscribedChannel");
		String SQL = "	SELECT order_id, dtl_id, channel_id, create_by, create_date, last_upd_by,	" +
		"	       last_upd_date	" +
		"	  FROM bomweb_subscribed_channel	" +
		"	 WHERE order_id = ?	" ;

		
		ParameterizedRowMapper<SubscribedChannelDTO> mapper = new ParameterizedRowMapper<SubscribedChannelDTO>() {

			public SubscribedChannelDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				SubscribedChannelDTO dto = new SubscribedChannelDTO();
					
				dto.setOrderId(rs.getString("order_id"));
				dto.setDtlId(rs.getString("dtl_id"));
				dto.setChannelId(rs.getString("channel_id"));
				dto.setCreateBy(rs.getString("create_by"));
				dto.setCreateDate(rs.getTimestamp("create_date"));
				dto.setLastUpdBy(rs.getString("last_upd_by"));
				dto.setLastUpdDate(rs.getTimestamp("last_upd_date"));

				return dto;
			}
		};
		
		try {
			logger.debug("getSubscribedChannel @ OrderDAO: " + SQL);
			List<SubscribedChannelDTO> orders = simpleJdbcTemplate
					.query(SQL, mapper, orderId);
			
			return orders;
		
		} catch (Exception e) {
			logger.error("Exception caught in getSubscribedChannel()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public List<SubscribedChannelDTO> getSubscribedChannelSync(String orderId) throws DAOException{
		logger.debug("getSubscribedChannelSync");
		String SQL = 
		
		/*"	SELECT bsc.order_id, bsc.dtl_id, bsc.channel_id, bsc.create_by, bsc.create_date,	"+
		"	       bsc.last_upd_by, bsc.last_upd_date, wtca.campaign_cd, wtca.plan_cd,	"+
		"	       wtc.channel_cd, wtc.tv_type	"+
		"	  FROM bomweb_subscribed_channel bsc,	"+
		"	       w_tv_channel_assgn wtca,	"+
		"	       w_tv_channel wtc	"+
		"	 WHERE bsc.order_id = ?	"+
		"	   AND bsc.channel_id = wtca.channel_id(+)	"+
		"	   AND NVL (wtca.eff_start_date, TRUNC (SYSDATE)) <= TRUNC (SYSDATE)	"+
		"	   AND NVL (wtca.eff_end_date, TO_DATE ('20991231', 'yyyyMMdd')) >=	"+
		"	                                                               TRUNC (SYSDATE)	"+
		"	   AND bsc.channel_id = wtc.channel_id	";*/
		
		//Modified by Nanon Lai on 29th March, 2012 for NowTV Campaign Code and Plan Code Automation
			
		" SELECT   bsc.order_id, bsc.dtl_id, bsc.channel_id, bsc.create_by, bsc.create_date,	"+
		"	       bsc.last_upd_by, bsc.last_upd_date, wtca.campaign_cd, wtca.plan_cd,	"+
		"	       wtc.channel_cd, wtc.tv_type	"+
		" FROM 	   bomweb_subscribed_channel bsc,	"+
		"	       bomweb_subscribed_item bsi,	"+
		"	       bomweb_order bo,	"+
		"	       bomweb_order_ims boi,	"+
		"	       w_item_dtl_ims widi,	"+
		"	       w_tv_channel_assgn wtca,	"+
		"	       w_tv_channel wtc	"+
		" WHERE    bsc.order_id = ?	"+
		"	       AND bsc.channel_id = wtca.channel_id(+)	"+
		"	       AND bsi.ORDER_ID = bsc.ORDER_ID	"+
		"	       AND bsc.ORDER_ID = bo.order_id	"+
		"	       AND bsc.ORDER_ID = boi.order_id	"+
		"	       AND bsi.type = 'PROG'	"+
		"	       AND bsi.id = widi.ITEM_ID	"+
		"	       AND NVL (TRUNC(wtca.eff_start_date), TRUNC (SYSDATE)) <= TRUNC (SYSDATE)	"+
		"	       AND NVL (TRUNC(wtca.eff_end_date), TO_DATE ('20991231', 'yyyyMMdd')) >=	"+
		"	                                                                   TRUNC (SYSDATE)	"+
		"	       AND bsc.channel_id = wtc.channel_id	"+
		"	       AND NVL (widi.IS_COUPON_PLAN, 'N') = NVL (wtca.IS_COUPON_PLAN, 'N')	"+
		"	       AND NVL (bo.WARR_PERIOD, 0) = NVL (wtca.CONTRACT_PERIOD, 0)	"+
		"	       AND NVL (boi.TV_CREDIT, '0') = NVL (wtca.OFFER_CREDIT , '0')";
		
		ParameterizedRowMapper<SubscribedChannelDTO> mapper = new ParameterizedRowMapper<SubscribedChannelDTO>() {

			public SubscribedChannelDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				SubscribedChannelDTO dto = new SubscribedChannelDTO();
					
				dto.setOrderId(rs.getString("order_id"));
				dto.setDtlId(rs.getString("dtl_id"));
				dto.setChannelId(rs.getString("channel_id"));
				dto.setCampaignCd(rs.getString("campaign_cd"));
				dto.setPlanCd(rs.getString("plan_cd"));
				dto.setChannelCd(rs.getString("channel_cd"));
				dto.setTvTypt(rs.getString("tv_type"));

				return dto;
			}
		};
		
		try {
			logger.debug("getSubscribedChannelSync @ OrderDAO: " + SQL);
			List<SubscribedChannelDTO> orders = simpleJdbcTemplate
					.query(SQL, mapper, orderId);
			
			return orders;
		
		} catch (Exception e) {
			logger.error("Exception caught in getSubscribedChannelSync()", e);
			throw new DAOException(e.getMessage(), e);
		}

	}
	
	
	
	public List<SubscItemDiscDTO> getSubscItemDisc(String orderId) throws DAOException{
		logger.debug("getSubscItemDisc");
		String SQL = "	SELECT bsi.order_id, bsi.id, bsi.type, bsi.create_date, bsi.basket_id,	" +
		"	       bsi.create_by, bsi.last_upd_by, bsi.last_upd_date, wioa.offer_id,	" +
		"	       wioa.offer_sub_id, wiopa.product_id, wipd.dis_id, wipd.dis_cd	" +
		"	  FROM bomweb_subscribed_item bsi,	" +
		"	       w_item_offer_assgn wioa,	" +
		"	       w_item_offer_product_assgn wiopa,	" +
		"	       w_item_product_discount wipd	" +
		"	 WHERE bsi.order_id = ?	" +
		"	   AND bsi.ID = wioa.item_id	" +
		"	   AND wioa.item_id = wiopa.item_id	" +
		"	   AND wioa.item_offer_seq = wiopa.item_offer_seq	" +
		"	   AND wipd.item_id = wiopa.item_id	" +
		"	   AND wipd.item_offer_seq = wiopa.item_offer_seq	" +
		"	   AND wipd.item_product_seq = wiopa.item_product_seq	" ;
		
		ParameterizedRowMapper<SubscItemDiscDTO> mapper = new ParameterizedRowMapper<SubscItemDiscDTO>() {

			public SubscItemDiscDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				SubscItemDiscDTO dto = new SubscItemDiscDTO();
					
				dto.setOrderId(rs.getString("order_id"));
				dto.setId(rs.getString("id"));
				dto.setType(rs.getString("type"));
				dto.setBasketId(rs.getString("basket_id"));
				dto.setOfferId(rs.getString("offer_id"));
				dto.setOfferSubId(rs.getString("offer_sub_id"));
				dto.setProductId(rs.getString("product_id"));
				dto.setDisId(rs.getString("dis_id"));
				dto.setDisCd(rs.getString("dis_cd"));

				return dto;
			}
		};
		
		try {
			logger.debug("getSubscItemDisc @ OrderDAO: " + SQL);
			List<SubscItemDiscDTO> orders = simpleJdbcTemplate
					.query(SQL, mapper, orderId);
			
			return orders;
		
		} catch (Exception e) {
			logger.error("Exception caught in getSubscItemDisc()", e);
			throw new DAOException(e.getMessage(), e);
		}

	}
	
	
	public List<SalesReferrerDTO> getBomWebSalesReferrerIms(String orderId) throws DAOException{
		logger.debug("getBomWebSalesReferrerIms");
		String SQL = "	SELECT order_id, REF_APPLMTHD, REF_APPLMTHD_DESC,     " +
				     "  REF_SOURCE_CD, REF_STAFF_NUM, REF_SALES_NAME,  	      " +
				     "	create_date, create_by, last_upd_by, last_upd_date	  " +
				     "	FROM bomweb_sales_referrer_ims	" +
				     "	 WHERE order_id = ?	" ;



		
		ParameterizedRowMapper<SalesReferrerDTO> mapper = new ParameterizedRowMapper<SalesReferrerDTO>() {

			public SalesReferrerDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				SalesReferrerDTO dto = new SalesReferrerDTO();
					
				dto.setOrderId(rs.getString("order_id"));
				dto.setReferrerAppMethod(rs.getString("REF_APPLMTHD"));
				dto.setReferrerAppMethodDesc(rs.getString("REF_APPLMTHD_DESC"));
				dto.setReferrerSourcecode(rs.getString("REF_SOURCE_CD"));
				dto.setReferrerStaffNo(rs.getString("REF_STAFF_NUM"));
				dto.setReferrerStaffName(rs.getString("REF_SALES_NAME"));
				
				return dto;
			}
		};
		
		
		try {
			logger.debug("getBomWebSalesReferrerIms @ OrderDAO: " + SQL);
			List<SalesReferrerDTO> dto = simpleJdbcTemplate.query(SQL, mapper, orderId);
			
			return dto;
		
		} catch (EmptyResultDataAccessException erdae) {
			return new ArrayList<SalesReferrerDTO>();
		} catch (Exception e) {
			logger.error("Exception caught in getBomWebSalesReferrerIms()", e);
			throw new DAOException(e.getMessage(), e);
		}
		
	}
	
	
	// eSignature CR by Eric Ng
	public void updateBomWebOrder(OrderDTO dto) throws DAOException{
		logger.debug("updateBomWebOrder");
		String SQL = "	UPDATE bomweb_order	" +
		"	   SET order_id = ?,	" +
		"	       ocid = ?,	" +
		"	       src = ?,	" +
		"	       order_type = ?,	" +
		"	       msisdn = ?,	" +
		"	       msisdnlvl = ?,	" +
		"	       mnp_ind = ?,	" +
		"	       shop_cd = ?,	" +
		"	       bom_cust_no = ?,	" +
		"	       mob_cust_no = ?,	" +
		"	       acct_no = ?,	" +
		"	       srv_req_date = ?,	" +
		"	       agree_num = ?,	" +
		"	       app_date = ?,	" +
		"	       sales_type = ?,	" +
		"	       sales_cd = ?,	" +
		"	       dep_waive = ?,	" +
		"	       on_hold_ind = ?,	" +
		"	       on_hold_rea_cd = ?,	" +
		"	       imei = ?,	" +
		"	       warr_start_date = ?,	" +
		"	       warr_period = ?,	" +
		//"	       create_date = ?,	" +
		"	       order_status = ?,	" +
		"	       err_cd = ?,	" +
		"	       err_msg = ?,	" +
		"	       sales_name = ?,	" +
		"	       ao_ind = ?,	" +
		"	       last_update_by = ?,	" +
		"	       last_update_date = sysdate,	" +
		//"	       create_by = ?,	" +
		"	       reason_cd = ?,	" +
		"	       LOB = ?,	" +
		"	       sign_off_date = ?,	" +
		"	       staff_num = ?,	" +
		"	       sales_channel = ?,	" +
		"	       sales_contact_num = ?,	" +
//		eSignature CR by Eric Ng
		"	       DIS_MODE = ?,	" +
		"	       COLLECT_METHOD = ?,	" +
		"	       ESIG_EMAIL_ADDR = ?,	" +
		"	       ESIG_EMAIL_LANG = ?," +
		"		   SMS_NO = ?	," +
		"			submit_date = ?," +
		"		   cpq_txn_id = ?,	" +
		"		   online_req_id = ?,	" +
		"		   centre_cd = ?	" +
		
		//"	       boc = ?	" +
		"	 WHERE order_id = ?	" ;

		//steven added
		String dismode = null;
		if(dto.getDisMode()!=null){
			dismode=dto.getDisMode().toString();
		}
		String CollectMethod = null;
		if(dto.getCollectMethod()!=null){
			CollectMethod=dto.getCollectMethod().toString();
		}
		String EsigEmailAddr = null;
		if(dto.getEsigEmailAddr()!=null){
			EsigEmailAddr=dto.getEsigEmailAddr().toString();
		}
		String EsigEmailLang = null;
		if(dto.getEsigEmailLang()!=null){
			EsigEmailLang=dto.getEsigEmailLang().toString();
		}
		//steven added end
		try {
			simpleJdbcTemplate.update(SQL,
					dto.getOrderId(),
					dto.getOcId(),
					dto.getSrc(),
					dto.getOrderType(),
					dto.getMsisdn(),
					dto.getMsisdnlvl(),
					dto.getMnpInd(),
					dto.getShopCd(),
					dto.getBomCustNo(),
					dto.getMobCustNo(),
					dto.getAcctNo(),
					dto.getSrvReqDate(),
					dto.getAgreeNum(),
					dto.getAppDate(),
					dto.getSalesType(),
					dto.getSalesCd(),
					dto.getDepWaive(),
					dto.getOnHoldInd(),
					dto.getOnHoldReaCd(),
					dto.getImei(),
					dto.getWarrStartDate(),
					dto.getWarrPeriod(),
					//dto.getCreateDate(),
					dto.getOrderStatus(),
					dto.getErrCd(),
					dto.getErrMsg(),
					dto.getSalesName(),
					dto.getAoInd(),
					dto.getLastUpdBy(),
					//dto.getLastUpdateDate(),
					//dto.getCreateBy(),
					dto.getReasonCd(),
					dto.getLob(),
					dto.getSignOffDate(),
					dto.getStaffNum(),
					dto.getSalesChannel(),
					dto.getSalesContactNum(),
					
//					eSignature CR by Eric Ng
					dismode,//steven modified
					CollectMethod,//steven modified
					EsigEmailAddr,//steven modified
					EsigEmailLang,//steven modified
					dto.getSMSno(),
					//dto.getBoc,
					dto.getSubmitDate(), 
					dto.getCpqTxnId(),
					dto.getSessionReqId(),
					dto.getCentreCd(),
					
					dto.getOrderId()
					);

		} catch (Exception e) {
			logger.error("Exception caught in updateBomWebOrder()", e);
			throw new DAOException(e.getMessage(), e);
		}
		
	}
	
	public void updateBomWebOrderIms(OrderImsDTO dto) throws DAOException{
		logger.info("updateBomWebOrderIms getSysF(): " + dto.getSysF());
		
		String SQL = "	UPDATE bomweb_order_ims	" +
		"	   SET order_id = ?,	" +
		"	       login_id = ?,	" +
		"	       decoder_type = ?,	" +
		"	       adult_view_allow = ?,	" +
		"	       target_comm_date = ?,	" +
		"	       prepay_amt = ?,	" +
		"	       moving_chrg = ?,	" +
		"	       OT_INSTALL_CHRG = ?,	" +
		"	       waived_prepay = ?,	" +
		"	       cash_fs_prepay = ?,	" +
		"	       now_tv_form_type = ?,	" +
		"	       fixed_line_no = ?,	" +
		"	       is_cc_offer = ?,	" +
		"	       lang_preference = ?,	" +
		"	       process_cc_prepay = ?,	" +
		"	       process_vim = ?,	" +
		"	       process_vms = ?,	" +
		"	       process_wifi = ?,	" +
		//"	       create_by = ?,	" +
		//"	       create_date = ?,	" +
		"	       last_upd_by = ?,	" +
		"	       last_upd_date = sysdate,	" +
		"	       tv_credit = ?,	" +
		"	       bandwidth = ?,	" +
		"	       primary_acct_no = ?,	" +
		"	       INSTALL_INSTALMENT_AMT = ?,	" +
		"	       INSTALL_INSTALMENT_MTH  = ?,	" +
		"			OT_CHRG_TYPE = ?, " +
		"			APPLMTHD = ?,	" +
		"			SOURCE_CD = ?," +
		"			CALL_DATE = ?," +
		"			POSITION_NO = ?," +
		"			FAX_SERIAL = ?," +
		"			APPLMTHD_desc = ?," +
		"			INSTALL_OT_CODE = ?," +
		"			INSTALL_OT_DESC_EN = ?," +
		"			INSTALL_OT_DESC_ZH = ?," +
		"			INSTALL_INSTALMENT_CODE = ?," +
		"			INSTALL_OT_QTY = ?," +
		"			DS_TYPE = ?," +
		"			DS_LOCATION = ?," +
		"			COOLOFF = ?," +
		"			WAIVE_CLOFF = ?," +
		"			QC_CALL_TIME = ?," +
		"			WAIVE_QC = ?," +
		"			ims_order_type = ?," +
		"			SYS_F = ?, " +
		"			shop_code = ?, " +
		"			tran_code = ?, " +
		"			is_new_cust_ind = ?, " +
		"			deposit_amt = ?, " +
		"			arpu_in = ?, " +
		"			arpu_out = ?, " +
		"			ride_on_fsa_ind = ?, " +
		"			must_qc_ind = ?, " +
		"			TV_PRICE_IND =?, " +
		"			qr_submit_date = ?, " +
		"			PCD_LIKE_IND = ?, " +
		"			PCD_NOW_ONE_IND = ?, " +
		"			PRE_INST_IND = ?, " +
		"			MOBILE_OFFER_IND = ?, " +
		"			MRT = ?, " +
		"			COLLECT_VI_STB_1 = ?, " +
		"			FIELD_IND = ?, " +
		"			special_request_cd = ?, "+
		"			PRE_USE_IND = ?, "+
		"			RELATED_FSA = ?, "+
		"			RIDE_ON_FSA_REASON_CD = ?, "+
		"			SERVICE_WAIVER = ?, "+
		"			SALES_MODE = ?, "+
		"			SPECIAL_OTC_ID = ?, "+
		"			COLLECT_VI_STB_2 = ?, " +
		"			HK_QR_STRING = ?, HK_QR_URL = ?, OUT_TRADE_NO = ?, CASH_PAY_MTD_TYPE = ?, TRADE_NO = ?, FPS_REFERENCE_ID = ?  " +
		"	 WHERE order_id = ?	" ;
		
		try {
			simpleJdbcTemplate.update(SQL,
					dto.getOrderId(),
					dto.getLoginId(),
					dto.getDecodeType(),
					dto.getAdultViewAllow(),
					dto.getTargetCommDate(),
					dto.getPrepayAmt(),
					dto.getMovingChrg(),
					dto.getOtInstallChrg(),
					dto.getWaivedPrepay(),
					dto.getCashFsPrepay(),
					dto.getNowTvFormType(),
					dto.getFixedLineNo(),
					dto.getIsCreditCardOffer(),
					dto.getLangPreference(),
					dto.getProcessCreditCard(),
					dto.getProcessVim(),
					dto.getProcessVms(),
					dto.getProcessWifi(),
					//dto.getCreateBy(),
					//dto.getCreateDate(),
					dto.getLastUpdBy(),
					//dto.getLastUpdDate(),
					dto.getTvCredit(),
					dto.getBandwidth(),
					dto.getPrimaryAcctNo(),
					dto.getInstallmentChrg(),
					dto.getInstallmentMonth(),
					dto.getOtChrgType(),
					dto.getAppMethod(),
					dto.getSourceCd(),
					dto.getCallDate(),
					dto.getPositionNo(),
					dto.getFAXno(),
					dto.getAppMethodDesc(),
					dto.getInstallOtCode(),
					dto.getInstallOTDesc_en(),
					dto.getInstallOTDesc_zh(),
					dto.getInstallmentCode(),
					dto.getInstallOtQty(),
					dto.getDsType(),
					dto.getDsLocation(),
					dto.getDsCoolOff(),
					dto.getDsWaiveCoolOff(),
					dto.getDsQcCallTime(),
					dto.getDsWaiveQC(),
					dto.getImsOrderType(),
					dto.getSysF(),
					dto.getShopCode(),
					dto.getTranCode(),
					dto.getIsNewCust(),
					dto.getDepositAmt(),
					dto.getARPU_in(),
					dto.getARPU_out(),
					dto.getRide_on_FSA_Ind(),
					dto.getMust_QC_Ind(),
					dto.getTvPriceInd(),
					dto.getQr_submit_date(),
					dto.getPcdLike100Ind(),
					dto.getPcdNowOneBoxInd(),
					dto.getPreInstallInd(),
					dto.getMobileOfferInd(),
					dto.getMrt(),
					dto.getCollectViStb1(),
					dto.getFieldInd(),
					dto.getSpecialRequestCd(),
					dto.getPreUseInd(),
					dto.getRelated_FSA(),
					dto.getRide_on_FSA_reason_Cd(),
					dto.getServiceWaiver(),
					dto.getMode(),
					dto.getSpecialOTCId(),
					dto.getCollectViStb2(),
					dto.getHkqrString(), dto.getHkqrUrl(), dto.getOutTradeNo(), dto.getCashPayMtdType(), dto.getTradeNo(), dto.getFpsReferenceId(),
					dto.getOrderId()
					);

		} catch (Exception e) {
			logger.error("Exception caught in updateBomWebOrderIms()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public void updateAppointment(AppointmentDTO dto) throws DAOException{
		logger.debug("updateAppointment");
		String SQL ="	UPDATE bomweb_appointment	" +
		"	   SET order_id = ?,	" +
		"	       dtl_id = ?,	" +
		"	       serial_num = ?,	" +
		"	       appnt_start_time = ?,	" +
		"	       appnt_end_time = ?,	" +
		"	       inst_contact_name = ?,	" +
		"	       inst_contact_num = ?,	" +
		"	       inst_sms_num = ?,	" +
		"	       exact_appnt_time = ?,	" +
		"	       forced_delay_ind = ?,	" +
		"	       pre_wiring_start_time = ?,	" +
		"	       pre_wiring_end_time = ?,	" +
		"	       pre_wiring_type = ?,	" +
		"	       tid_ind = ?,	" +
		"	       appnt_type = ?,	" +
		"	       bmo_lead_day = ?,	" +
		"	       align_bill_date = ?,	" +
		//"	       create_by = ?,	" +
		//"	       create_date = ?,	" +
		"	       last_upd_by = ?,	" +
		"	       last_upd_date = sysdate	" +
		"	 WHERE order_id = ?	" ;

		try {
			simpleJdbcTemplate.update(SQL,
					dto.getOrderId(),
					dto.getDtlId(),
					dto.getSerialNum(),
					dto.getAppntStartDate(),
					dto.getAppntEndDate(),
					dto.getInstContactName(),
					dto.getInstContactNum(),
					dto.getInstSmsNum(),
					dto.getExactAppntTime(),
					dto.getForcedDelayInd(),
					dto.getPreWiringStartTime(),
					dto.getPreWiringEndTime(),
					dto.getPreWiringType(),
					dto.getTidInd(),
					dto.getAppntType(),
					dto.getBmoLeadDay(),
					dto.getAlignWithBillDate(),
					//dto.getCreateBy(),
					//dto.getCreateDate(),
					dto.getLastUpdBy(),
					//dto.getLastUpdDate(),
					dto.getOrderId()
					);

		} catch (Exception e) {
			logger.error("Exception caught in updateAppointment()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public void updateRemark(RemarkDTO dto) throws DAOException{
		logger.debug("updateRemarks");
		String SQL = "	UPDATE bomweb_remark	" +
		"	   SET order_id = ?,	" +
		"	       dtl_id = ?,	" +
		"	       rmk_type = ?,	" +
		"	       rmk_seq = ?,	" +
		"	       rmk_dtl = ?,	" +
		//"	       create_by = ?,	" +
		//"	       create_date = ?,	" +
		"	       last_upd_by = ?,	" +
		"	       last_upd_date = sysdate	" +
		"	 WHERE order_id = ? AND rmk_seq = ?	";
		
		try {
			simpleJdbcTemplate.update(SQL,
					dto.getOrderId(),
					dto.getDtlId(),
					dto.getRmkType(),
					dto.getRmkSeq(),
					dto.getRmkDtl(),
					//dto.getCreateBy(),
					//dto.getCreateDate(),
					dto.getLastUpdBy(),
					//dto.getLastUpdDate(),
					dto.getOrderId(), dto.getRmkSeq()
					);

		} catch (Exception e) {
			logger.error("Exception caught in updateRemarks()", e);
			throw new DAOException(e.getMessage(), e);
		}
		
	}
	
	public void updateBomWebCustomer(CustomerDTO dto) throws DAOException{
		logger.debug("updateBomWebCustomer");
		String SQL = "	UPDATE bomweb_customer	" +
		"	   SET order_id = ?,	" +
		"	       cust_no = ?,	" +
		"	       mob_cust_no = ?,	" +
		"	       first_name = ?,	" +
		"	       last_name = ?,	" +
		"	       id_doc_type = ?,	" +
		"	       id_doc_num = ?,	" +
		"	       dob = ?,	" +
		"	       title = ?,	" +
		"	       company_name = ?,	" +
		"	       ind_type = ?,	" +
		"	       ind_sub_type = ?,	" +
		"	       nationality = ?,	" +
		//"	       create_date = ?,	" +
		"	       addr_proof_ind = ?,	" +
		"	       LOB = ?,	" +
		"	       service_num = ?,	" +
		//"	       create_by = ?,	" +
		"	       last_upd_by = ?,	" +
		"	       last_upd_date = sysdate,	" +
		"	       id_verified_ind = ?,	" +
		"	       blacklist_ind = ?,	" +
		"	       cs_portal_ind = ?,	" +//Gary
		"	       cs_portal_login = ?,	" +
		"	       cs_portal_mobile = ?,	" +
		"	       mismatch_ind = ?,	" + 
		"		   the_club_ind = ?, " +
		"		   the_club_login = ?, " +
		"		   the_club_mobile = ?, " +
		"		   NOWID_IND = ?, " +
		"		   NOWID_LOGIN = ?, " +
		"		   hkt_opt_out = ?, " +
		"		   club_opt_out = ?, " +
		"		   club_opt_rea = ?, " +
		"		   club_opt_rmk = ? " +
		"	 WHERE order_id = ?	";
		
		try {
			simpleJdbcTemplate.update(SQL,
					dto.getOrderId(),
					dto.getCustNo(),
					dto.getMobCustNo(),
					dto.getFirstName(),
					dto.getLastName(),
					dto.getIdDocType(),
					dto.getIdDocNum(),
					dto.getDob(),
					dto.getTitle(),
					dto.getCompanyName(),
					dto.getIndType(),
					dto.getIndSubType(),
					dto.getNationality(),
					//dto.getCreateDate(),
					dto.getAddrProofInd(),
					dto.getLob(),
					dto.getServiceNum(),
					//dto.getCreateBy(),
					dto.getLastUpdBy(),
					//dto.getLastUpdDate(),
					dto.getIdVerified(),
					dto.getBlacklistInd(),					
					dto.getCsPortalInd(), //Gary
					dto.getCsPortalLogin(), 
					dto.getCsPortalMobile(),
					dto.getExistingCustomerConflictWithName(),
					dto.getTheClubInd(),
					dto.getTheClubLogin(),
					dto.getTheClubMoblie(),
					dto.getIsRegNowId(),
					dto.getNowId(),
					dto.getCsPortalOptOutInd(),
					dto.getTheClubOptOutInd(),
					dto.getOptoutTheClubReason(),
					dto.getOptoutTheClubRmk(),
					dto.getOrderId()
					);

		} catch (Exception e) {
			logger.error("Exception caught in updateBomWebCustomer()", e);
			throw new DAOException(e.getMessage(), e);
		}
		
	}
	
	public void updateAccount(AcctDTO dto) throws DAOException{
		logger.debug("updateAccount");
		String SQL = "	UPDATE bomweb_acct	" +
		"	   SET order_id = ?,	" +
		"	       cust_no = ?,	" +
		"	       acct_name = ?,	" +
		"	       bill_freq = ?,	" +
		"	       bill_lang = ?,	" +
		"	       sms_no = ?,	" +
		"	       email_addr = ?,	" +
		"	       acct_no = ?,	" +
		//"	       create_date = ?,	" +
		"	       bill_period = ?,	" +
		//"	       create_by = ?,	" +
		"	       last_upd_by = ?,	" +
		"	       last_upd_date = sysdate,	" +
		//"	       dtl_id = ?,	" +
		"	       bill_media = ?,	" +
		"	       exist_bill_media = ?,	" +
		"	       action = ?	" +
		"	 WHERE order_id = ?	" ;
		
		try {
			simpleJdbcTemplate.update(SQL,
					dto.getOrderId(),
					dto.getCustNo(),
					dto.getAcctName(),
					dto.getBillFreq(),
					dto.getBillLang(),
					dto.getSmsNo(),
					dto.getEmailAddr(),
					dto.getAcctNo(),
					//dto.getCreateDate(),
					dto.getBillPeriod(),
					//dto.getCreateBy(),
					dto.getLastUpdBy(),
					//dto.getLastUpdDate(),
					//dto.getDtlId(),
					dto.getBillMedia(),
					dto.getExistingBillingMedia(),
					dto.getUpdateBillingMethod(),
					dto.getOrderId()
					);

		} catch (Exception e) {
			logger.error("Exception caught in updateAccount()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public void updatePayment(PaymentDTO dto) throws DAOException{
		logger.debug("updatePayment");
		String SQL = "	UPDATE bomweb_payment	" +
		"	   SET order_id = ?,	" +
		"	       cust_no = ?,	" +
		"	       pay_mtd_key = ?,	" +
		"	       acct_no = ?,	" +
		"	       autopay_app_date = ?,	" +
		"	       autopay_up_lim_amt = ?,	" +
		"	       b_acct_no = ?,	" +
		"	       b_acct_hold_name = ?,	" +
		"	       b_acct_hold_id_type = ?,	" +
		"	       b_acct_hold_id_num = ?,	" +
		"	       branch_cd = ?,	" +
		"	       bank_cd = ?,	" +
		"	       pay_mtd_type = ?,	" +
		"	       third_party_ind = ?,	" +
		"	       cc_type = ?,	" +
		"	       cc_num = ?,	" +
		"	       cc_hold_name = ?,	" +
		"	       cc_exp_date = ?,	" +
		"	       cc_issue_bank = ?,	" +
		"	       cc_id_doc_type = ?,	" +
		"	       cc_id_doc_no = ?,	" +
		//"	       create_date = ?,	" +
		//"	       create_by = ?,	" +
		"	       last_upd_by = ?,	" +
		"	       last_upd_date = sysdate,	" +
		"	       cc_verified_ind = ?,	" +
		"	       dtl_id = ?,	" +
		"	       action = ?	" +
		"	 WHERE order_id = ?	" ;
		
		try {
			simpleJdbcTemplate.update(SQL,
					dto.getOrderId(),
					dto.getCustNo(),
					dto.getPayMtdKey(),
					dto.getAcctNo(),
					dto.getAutopayAppDate(),
					dto.getAutopayUpLimAmt(),
					dto.getBAcctNo(),
					dto.getBAcctHoldName(),
					dto.getBAcctHoldIdType(),
					dto.getBAcctHoldIdNum(),
					dto.getBranchCd(),
					dto.getBankCd(),
					dto.getPayMtdType(),
					dto.getThirdPartyInd(),
					dto.getCcType(),
					dto.getCcNum(),
					dto.getCcHoldName(),
					dto.getCcExpDate(),
					dto.getCcIssueBank(),
					dto.getCcIdDocType(),
					dto.getCcIdDocNo(),
					//dto.getCreateDate(),
					//dto.getCreateBy(),
					dto.getLastUpdBy(),
					//dto.getLastUpdDate(),
					dto.getCcVerified(),
					dto.getDtlId(),
					dto.getAction(),
					dto.getOrderId()
					);

		} catch (Exception e) {
			logger.error("Exception caught in updatePayment()", e);
			throw new DAOException(e.getMessage(), e);
		}
		
	}
	
	public void updateCustOptoutInfo(CustOptoutInfoDTO dto) throws DAOException{
		logger.debug("updateCustOptoutInfo");
		String SQL = "	UPDATE bomweb_cust_optout_info	" +
		"	   SET order_id = ?,	" +
		"	       cust_no = ?,	" +
		"	       direct_mailing = ?,	" +
		"	       outbound = ?,	" +
		"	       sms = ?,	" +
		"	       email = ?,	" +
		"	       web_bill = ?,	" +
		"	       nonsales_sms = ?,	" +
		"	       internet = ?,	" +
		//"	       create_by = ?,	" +
		//"	       create_date = ?,	" +
		"	       last_upd_by = ?,	" +
		"	       last_upd_date = sysdate	" +
		"	 WHERE order_id = ?	" ;
		
		try {
			simpleJdbcTemplate.update(SQL,
					dto.getOrderId(),
					dto.getCustNo(),
					dto.getDirectMailing(),
					dto.getOutbound(),
					dto.getSms(),
					dto.getEmail(),
					dto.getWebBill(),
					dto.getNonsalesSms(),
					dto.getInternet(),
					//dto.getCreateBy(),
					//dto.getCreateDate(),
					dto.getLastUpdBy(),
					//dto.getLastUpdDate(),
					dto.getOrderId()
					);

		} catch (Exception e) {
			logger.error("Exception caught in updateCustOptoutInfo()", e);
			throw new DAOException(e.getMessage(), e);
		}

	}
	
	public void updateContact(ContactDTO dto) throws DAOException{
		logger.debug("updateContact");
		String SQL = "	UPDATE bomweb_contact	" +
		"	   SET order_id = ?,	" +
		"	       title = ?,	" +
		"	       contact_name = ?,	" +
		"	       contact_phone = ?,	" +
		"	       email_addr = ?,	" +
		"	       action_ind = ?,	" +
		//"	       create_date = ?,	" +
		"	       other_phone = ?,	" +
		//"	       create_by = ?,	" +
		"	       last_upd_by = ?,	" +
		"	       last_upd_date = sysdate	" +
//		"	       ,contact_type = ?	" + //eSignature Cr by Eric Ng @2012-10-29
		"	       , NOWTV_DELIVERY_MOBILE_NUM = ?	" + //by Jerry @ 2019-03-18
		"	 WHERE order_id = ?	" ;
		
		try {
			simpleJdbcTemplate.update(SQL,
					dto.getOrderId(),
					dto.getTitle(),
					dto.getContactName(),
					dto.getContactPhone(),
					dto.getEmailAddr(),
					dto.getActionInd(),
					//dto.getCreateDate(),
					dto.getOtherPhone(),
					//dto.getCreateBy(),
					dto.getLastUpdBy(),
					//dto.getLastUpdDate(),
//					dto.getContactType(),
					dto.getDeliveryPhone(),
					dto.getOrderId()
					);

		} catch (Exception e) {
			logger.error("Exception caught in updateContact()", e);
			throw new DAOException(e.getMessage(), e);
		}

	}
	
	public void updateCustAddr(CustAddrDTO dto) throws DAOException{
		logger.debug("updateCustAddr");
		String SQL = "	UPDATE bomweb_cust_addr	" +
		"	   SET order_id = ?,	" +
		"	       addr_usage = ?,	" +
		//"	       flat = ?,	" +
		"	       area_cd = ?,	" +
		"	       dist_no = ?,	" +
		"	       sect_cd = ?,	" +
		"	       str_name = ?,	" +
		"	       hi_lot_no = ?,	" +
		"	       str_cat_cd = ?,	" +
		"	       build_no = ?,	" +
		"	       foreign_addr_flag = ?,	" +
		"	       floor_no = ?,	" +
		"	       unit_no = ?,	" +
		"	       po_box_no = ?,	" +
		"	       addr_type = ?,	" +
		"	       str_no = ?,	" +
		"	       sect_dep_ind = ?,	" +
		//"	       create_date = ?,	" +
		"	       area_desc = ?,	" +
		"	       dist_desc = ?,	" +
		"	       sect_desc = ?,	" +
		"	       str_cat_desc = ?,	" +
		//"	       create_by = ?,	" +
		"	       last_upd_by = ?,	" +
		"	       last_upd_date = sysdate,	" +
		"	       serbdyno = ?,	" +
		"	       blacklist_ind = ?,	" +
		"	       dtl_id = ?	" +
		"	 WHERE order_id = ?	AND addr_usage = ? " ;
		
		try {
			simpleJdbcTemplate.update(SQL,
					dto.getOrderId(),
					dto.getAddrUsage(),
					//dto.getFlat(),
					dto.getAreaCd(),
					dto.getDistNo(),
					dto.getSectCd(),
					dto.getStrName(),
					dto.getHiLotNo(),
					dto.getStrCatCd(),
					dto.getBuildNo(),
					dto.getForeignAddrFlag(),
					dto.getFloorNo(),
					dto.getUnitNo(),
					dto.getPoBoxNo(),
					dto.getAddrType(),
					dto.getStrNo(),
					dto.getSectDepInd(),
					//dto.getCreateDate(),
					dto.getAreaDesc(),
					dto.getDistDesc(),
					dto.getSectDesc(),
					dto.getStrCatDesc(),
					//dto.getCreateBy(),
					dto.getLastUpdBy(),
					//dto.getLastUpdDate(),
					dto.getSerbdyno(),
					dto.getBlacklistInd(),
					dto.getDtlId(),
					dto.getOrderId(), dto.getAddrUsage()
					);

		} catch (Exception e) {
			logger.error("Exception caught in updateCustAddr()", e);
			throw new DAOException(e.getMessage(), e);
		}
		
	}
	
	public void updateAddrInventory(AddrInventoryDTO dto) throws DAOException{
		logger.debug("updateAddrInventory");
		String SQL = "	UPDATE bomweb_addr_inventory	" +
		"	   SET order_id = ?,	" +
		"	       dtl_id = ?,	" +
		"	       addr_usage = ?,	" +
		"	       resource_shortage_ind = ?,	" +
		"	       technology = ?,	" +
		"	       building_type = ?,	" +
		"	       n2_building_ind = ?,	" +
		"	       field_work_permit_day = ?,	" +
		"	       addr_id = ?,	" +
		"	       max_bandwidth = ?,	" +
		"	       fttc_ind = ?,	" +
		"	       h264_ind = ?,	" +
		"	       adsl_18m_ind = ?,	" +
		"	       ntv_coverage = ?,	" +
		"	       adsl_8m_ind = ?,	" +
		//"	       create_by = ?,	" +
		//"	       create_date = ?,	" +
		"	       last_upd_by = ?,	" +
		"	       last_upd_date = sysdate	" +
		"	 WHERE order_id = ?	" ;
		
		try {
			simpleJdbcTemplate.update(SQL,
					dto.getOrderId(),
					dto.getDtlId(),
					dto.getAddrUsage(),
					dto.getResourceShortage(),
					dto.getTechnology(),
					dto.getBuildingType(),
					dto.getN2BuildingInd(),
					dto.getFieldPermitDay(),
					dto.getAddrId(),
					dto.getMaxBandwidth(),
					dto.getFttcInd(),
					dto.getH264Ind(),
					dto.getAdsl18MInd(),
					dto.getNtvCoverage(),
					dto.getAdsl8MInd(), 
					//dto.getCreateBy(),
					//dto.getCreateDate(),
					dto.getLastUpdBy(),
					//dto.getLastUpdDate(),
					dto.getOrderId()
					);

		} catch (Exception e) {
			logger.error("Exception caught in updateAddrInventory()", e);
			throw new DAOException(e.getMessage(), e);
		}

	}
	
	public void updateSubscribedItem(SubscribedItemDTO dto) throws DAOException{
		logger.debug("updateSubscribedItem");
		String SQL = "	UPDATE bomweb_subscribed_item	" +
		"	   SET order_id = ?,	" +
		"	       ID = ?,	" +
		"	       TYPE = ?,	" +
		//"	       create_date = ?,	" +
		"	       basket_id = ?,	" +
		//"	       create_by = ?,	" +
		"	       last_upd_by = ?,	" +
		"	       last_upd_date = sysdate	" +
		"	 WHERE order_id = ?	AND id = ? " ;
		
		try {
			simpleJdbcTemplate.update(SQL,
					dto.getOrderId(),
					dto.getId(),
					dto.getType(),
					//dto.getCreateDate(),
					dto.getBasketId(),
					//dto.getCreateBy(),
					dto.getLastUpdBy(),
					//dto.getLastUpdDate(),
					dto.getOrderId(), dto.getId()
					);

		} catch (Exception e) {
			logger.error("Exception caught in updateSubscribedItem()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public void updateComponent(ComponentDTO dto) throws DAOException{
		logger.debug("updateComponent");
		String SQL = "	UPDATE bomweb_component	" +
		"	   SET order_id = ?,	" +
		"	       attb_id = ?,	" +
		"	       attb_value = ?,	" +
		//"	       create_date = ?,	" +
		//"	       create_by = ?,	" +
		"	       last_upd_by = ?,	" +
		"	       last_upd_date = sysdate	" +
		"	 WHERE order_id = ?	AND attb_id = ? " ;
		
		try {
			simpleJdbcTemplate.update(SQL,
					dto.getOrderId(),
					dto.getAttbId(),
					dto.getAttbValue(),
					//dto.getCreateDate(),
					//dto.getCreateBy(),
					dto.getLastUpdBy(),
					//dto.getLastUpdDate(),
					dto.getOrderId(), dto.getAttbId()
					);

		} catch (Exception e) {
			logger.error("Exception caught in updateComponent()", e);
			throw new DAOException(e.getMessage(), e);
		}

	}
	
	
	public void updateVasParmIms(SubscribedCslOfferDTO dto) throws DAOException{
		logger.debug("updateVasParmIms");
		String SQL = "	UPDATE bomweb_vas_parm_ims	" +
		"	   SET order_id = ?,	" +
		"	       PRD_ID = ?,	" +
		"	       VAS_TYPE = ?,	" +
		"	       PARM_A = ?,	" +
		"	       PARM_A_CD = ?,	" +
		"	       PARM_B = ?,	" +
		"	       PARM_B_CD = ?,	" +
		"	       PARM_C = ?,	" +
		"	       PARM_C_CD = ?,	" +
		//"	       create_date = ?,	" +
		//"	       create_by = ?,	" +
		"	       last_upd_by = ?,	" +
		"	       last_upd_date = sysdate	" +
		"	 WHERE order_id = ? " ;
		
		try {
			simpleJdbcTemplate.update(SQL,
					dto.getOrder_id(),
					dto.getProd_id(),
					dto.getVas_type(),
					dto.getVas_parm_a(),
					dto.getVas_parm_a_cd(),
					dto.getVas_parm_b(),
					dto.getVas_parm_b_cd(),
					dto.getVas_parm_c(),
					dto.getVas_parm_c_cd(),
					dto.getLastUpdBy(),
					dto.getOrder_id()
					);

		} catch (Exception e) {
			logger.error("Exception caught in updateVasParmIms()", e);
			throw new DAOException(e.getMessage(), e);
		}

	}
	
	public void updateSubscribedChannel(SubscribedChannelDTO dto) throws DAOException{
		logger.debug("updateSubscribedChannel");
		String SQL = "	UPDATE bomweb_subscribed_channel	" +
		"	   SET order_id = ?,	" +
		"	       dtl_id = ?,	" +
		"	       channel_id = ?,	" +
		//"	       create_by = ?,	" +
		//"	       create_date = ?,	" +
		"	       last_upd_by = ?,	" +
		"	       last_upd_date = sysdate	" +
		"	 WHERE order_id = ? AND channel_id = ?	" ;
		
		try {
			simpleJdbcTemplate.update(SQL,
					dto.getOrderId(),
					dto.getDtlId(),
					dto.getChannelId(),
					//dto.getCreateBy(),
					//dto.getCreateDate(),
					dto.getLastUpdBy(),
					//dto.getLastUpdDate(),
					dto.getOrderId(), dto.getChannelId()
					);

		} catch (Exception e) {
			logger.error("Exception caught in updateSubscribedChannel()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public void deleteRemark(RemarkDTO dto) throws DAOException{
		logger.debug("deleteRemark");
		String SQL = "DELETE bomweb_remark " +
					"WHERE order_id = ? " +
					"AND rmk_seq = ? ";
		
		try {
			simpleJdbcTemplate.update(SQL, 
					dto.getOrderId(), dto.getRmkSeq());

		} catch (Exception e) {
			logger.error("Exception caught in deleteBomWebSub()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public void deleteCustOptoutInfo(CustOptoutInfoDTO dto) throws DAOException{
		logger.info("deleteCustOptoutInfo");
		String SQL = "DELETE bomweb_cust_optout_info " +
					"WHERE order_id = ? ";
		try {
			simpleJdbcTemplate.update(SQL, 
					dto.getOrderId());

		} catch (Exception e) {
			logger.error("Exception caught in deleteCustOptoutInfo()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public void deleteCustAddr(CustAddrDTO dto) throws DAOException{
		logger.debug("deleteCustAddr");
		String SQL = "DELETE bomweb_cust_addr " +
					"WHERE order_id = ? AND addr_usage = ? ";
		
		try {
			simpleJdbcTemplate.update(SQL, 
					dto.getOrderId(), dto.getAddrUsage());

		} catch (Exception e) {
			logger.error("Exception caught in deleteCustAddr()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public void deleteSubscribedItem(SubscribedItemDTO dto) throws DAOException{
		logger.debug("deleteSubscribedItem");
		String SQL = "DELETE bomweb_subscribed_item " +
					"WHERE order_id = ? AND id = ? ";
		
		try {
			simpleJdbcTemplate.update(SQL, 
					dto.getOrderId(), dto.getId());

		} catch (Exception e) {
			logger.error("Exception caught in deleteSubscribedItem()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public void deleteComponent(ComponentDTO dto) throws DAOException{
		logger.debug("deleteComponent");
		String SQL = "DELETE bomweb_component " +
					"WHERE order_id = ? AND attb_id = ? ";
		
		try {
			simpleJdbcTemplate.update(SQL, 
					dto.getOrderId(), dto.getAttbId());

		} catch (Exception e) {
			logger.error("Exception caught in deleteComponent()", e);
			throw new DAOException(e.getMessage(), e);
		}		
	}
	
	public void deleteSubscribedChannel(SubscribedChannelDTO dto) throws DAOException{
		logger.debug("deleteSubscribedChannel");
		String SQL = "DELETE bomweb_subscribed_channel " +
					"WHERE order_id = ? AND channel_id = ? ";
		
		try {
			simpleJdbcTemplate.update(SQL, 
					dto.getOrderId(), dto.getChannelId());

		} catch (Exception e) {
			logger.error("Exception caught in deleteSubscribedChannel()", e);
			throw new DAOException(e.getMessage(), e);
		}
		
	}
	
	
	public String getAutoProcessOrderId() throws DAOException {
		
		String orderId = null;
		logger.debug("getAutoProcessOrderId is called ");

		String SQL = "	SELECT order_id	" +
		"	  FROM (SELECT   order_id	" +
		"	            FROM bomweb_order	" +
		"	           WHERE LOB = 'IMS' " +
		"              AND order_status in ('00', '01') " +
		//"              AND (src IS NULL or src = 'CO') " +
		"	        ORDER BY create_date)	" +
		"	 WHERE ROWNUM = 1	" ;


		try {
			orderId = (String) simpleJdbcTemplate.queryForObject(SQL,
					String.class);

		} catch (EmptyResultDataAccessException erdae) {
			orderId = null;
		} catch (Exception e) {
			logger.error("Exception caught in getAutoProcessOrderId()", e);
			throw new DAOException(e.getMessage(), e);
		}
		return orderId;
	}
	
	public Date getSystemDate() throws DAOException{
		logger.debug("getSystemDate");
		String SQL = "SELECT sysdate FROM dual";
		
		Date sysdate = null;
		
		try {
			sysdate = (Timestamp) simpleJdbcTemplate.queryForObject(SQL,
					Timestamp.class);

		} catch (EmptyResultDataAccessException erdae) {
			sysdate = null;
		} catch (Exception e) {
			logger.error("Exception caught in getAutoProcessOrderId()", e);
			throw new DAOException(e.getMessage(), e);
		}
		
		return sysdate;
	}
	
	public void updateSyncOrderStatus(String orderId) throws DAOException{
		logger.debug("updateSyncOrderStatus");
		
		String SQL = "	UPDATE bomweb_order	" +
		"	   SET order_status = decode(order_status, '00', '03', '01', '04'),	" +
		"	       last_update_date = SYSDATE,	" +
		"	       last_update_by = 'IMSAUTO'	" +
		"	 WHERE order_id = ?	" ;
		
		try {
			
			simpleJdbcTemplate.update(SQL, orderId);
		
		} 
		catch (Exception e) {
			logger.error("Exception caught in updateSyncOrderStatus()", e);
			throw new DAOException(e.getMessage(), e);
		}
		
		
	}
	
	public List<OrderImsDTO> getSyncBackPendingOrder() throws DAOException{
		logger.debug("getSyncBackPendingOrder");
		String SQL = "	SELECT bo.order_id,	"+
		"	       CASE	"+
		"	          WHEN (SELECT COUNT (*)	"+
		"	                  FROM w_code_lkup	"+
		"	                 WHERE grp_id = 'SB_IMS_WQ_BYPASS'	"+
		"	                   AND code = 'PAY_CHANNEL'	"+
		"	                   AND description = 'Y') > 0	"+
		"	             THEN 'N'	"+
		"	          ELSE boi.process_vim	"+
		"	       END process_vim	"+
		"	  FROM bomweb_order_ims boi, bomweb_order bo	"+
		"	 WHERE bo.LOB = 'IMS'	"+
		"	   AND bo.ocid IS NULL	"+
		"	   AND bo.order_status = '03'	"+
		"	   AND boi.order_id = bo.order_id	";

		
		ParameterizedRowMapper<OrderImsDTO> mapper = new ParameterizedRowMapper<OrderImsDTO>() {

			public OrderImsDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				OrderImsDTO dto = new OrderImsDTO();
					
				dto.setOrderId(rs.getString("order_id"));			
				dto.setProcessVim(rs.getString("process_vim"));				

				return dto;
			}
		};
		
		try {
			logger.debug("getSyncBackPendingOrder @ OrderDAO: " + SQL);
			List<OrderImsDTO> orders = simpleJdbcTemplate
					.query(SQL, mapper);
			
			return orders;
		
		}catch (EmptyResultDataAccessException erdae) {
			return null;
		}catch (Exception e) {
			logger.error("Exception caught in getSyncBackPendingOrder()", e);
			throw new DAOException(e.getMessage(), e);
		}
		
	}
	
	public List<VimBundleProfileDTO> getVimBundleProfile(String orderId) throws DAOException{
		logger.debug("getVimBundleProfile");
		
		String SQL = "	SELECT bo.order_id, bo.app_date, bo.shop_cd, bo.sales_cd, bc.service_num, bo.sales_channel,	"+
		"	       boi.login_id, bc.title, bc.first_name, bc.last_name,	"+
		"	       DECODE (bc.id_doc_type, 'PASS', 'PS', bc.id_doc_type) id_doc_type,	"+
		//"	       bc.id_doc_num, bo.srv_req_date, bo.warr_period, e.dervied_campaign_cd	"+
		"		   bc.id_doc_num, bo.warr_period, e.dervied_campaign_cd, "+
		"		   nvl(trunc(ba.appnt_start_time), bo.srv_req_date) srv_req_date "+
		"	  FROM (SELECT bo.order_id, wcl.description dervied_campaign_cd	"+
		"	          FROM bomweb_order bo,	"+
		"	               bomweb_order_ims boi,	"+
		"	               bomweb_subscribed_item bsi_prog,	"+
		"	               bomweb_subscribed_item bsi_ntv_pay,	"+
		"	               w_item_dtl_ims widi,	"+
		"	               w_item_dtl_tv widt,	"+
		"	               w_code_lkup wcl	"+
		"	         WHERE bo.order_id = boi.order_id	"+
		"	           AND bo.order_id = bsi_prog.order_id	"+
		"	           AND bo.order_id = ?	"+
		"	           AND bsi_prog.TYPE = 'PROG'	"+
		"	           AND bsi_prog.ID = widi.item_id	"+
		"	           AND bo.order_id = bsi_ntv_pay.order_id	"+
		//"	           AND bsi_ntv_pay.TYPE = 'NTV_PAY'	"+
		"	           AND bsi_ntv_pay.TYPE in ('NTV_PAY', 'NTV_P_30F6')	"+		
		"	           AND bsi_ntv_pay.ID = widt.item_id	"+
		"	           AND wcl.grp_id = 'SB_IMS_VIM_CAMP_CD'	"+
		"	           AND wcl.code =	"+
		"	                     boi.now_tv_form_type	"+
		"	                  || '-'	"+
		"	                  || NVL (widi.is_coupon_plan, 'N')	"+
		"	                  || '-'	"+
		"	                  || widt.credit	"+
		"	                  || '-'	"+
		"	                  || widt.tv_type	"+
		"	                  || '-'	"+
		"	                  || bo.warr_period) e,	"+
		"	       bomweb_order bo,	"+
		"	       bomweb_order_ims boi,	"+
		"	       bomweb_customer bc,	"+
		"	       bomweb_appointment ba	"+
		"	 WHERE bo.order_id = boi.order_id	"+
		"	   AND bo.order_id = ?	"+
		"	   AND bo.order_id = bc.order_id	"+
		"      AND ba.order_id = bo.order_id  "+
		"	   AND e.order_id(+) = bo.order_id	";
		
		ParameterizedRowMapper<VimBundleProfileDTO> mapper = new ParameterizedRowMapper<VimBundleProfileDTO>() {

			public VimBundleProfileDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				VimBundleProfileDTO dto = new VimBundleProfileDTO();
					
				dto.setOrderId(rs.getString("order_id"));
				dto.setAppDate(rs.getTimestamp("app_date"));				
				dto.setShopCd(rs.getString("shop_cd"));				
				dto.setSalesCd(rs.getString("sales_cd"));
				dto.setSalesChannel(rs.getString("sales_channel"));
				dto.setFSA(rs.getString("service_num"));
				dto.setLoginID(rs.getString("login_id"));
				dto.setTitle(rs.getString("title"));
				dto.setFirstName(rs.getString("first_name"));				
				dto.setLastName(rs.getString("last_name"));
				dto.setIDDocType(rs.getString("id_doc_type"));
				dto.setIDDocNo(rs.getString("id_doc_num"));
				dto.setSRD(rs.getTimestamp("srv_req_date"));
				dto.setCampaignCd(rs.getString("dervied_campaign_cd"));
				dto.setCommitPeriod(rs.getString("warr_period"));				

				return dto;
			}
		};
		
		try {
			logger.debug("getVimBundleProfile @ OrderDAO: " + SQL);
			List<VimBundleProfileDTO> orders = simpleJdbcTemplate
					.query(SQL, mapper, orderId, orderId);
			
			return orders;
		
		}catch (EmptyResultDataAccessException erdae) {
			return null;
		}catch (Exception e) {
			logger.error("Exception caught in getVimBundleProfile()", e);
			throw new DAOException(e.getMessage(), e);
		}		
	}
	
	public List<Map<String, String>> getVIMBundleFreeHDChannel(String orderId) throws DAOException{
		logger.debug("getVIMBundleFreeHDChannel");
		String SQL = "	SELECT wtc.channel_cd, wtdl.html	"+
		"	  FROM bomweb_order bo,	"+
		"	       bomweb_subscribed_channel bsc,	"+
		"	       w_tv_channel wtc,	"+
		"	       w_tv_channel_group wtcg,	"+
		"	       w_tv_display_lkup wtdl	"+
		"	 WHERE bo.order_id = bsc.order_id	"+
		"	   AND bo.order_id = ?	"+
		"	   AND bsc.channel_id = wtc.channel_id	"+
		"	   AND wtc.channel_group_id = wtcg.channel_group_id	"+
		"	   AND wtdl.ID = bsc.channel_id	"+
		"	   AND wtdl.TYPE = 'CHANNEL'	"+
		"	   AND wtdl.locale = 'en'	"+
		"	   AND wtcg.channel_group_cd = 'FREE2HD'	";
		
		ParameterizedRowMapper<Map<String, String>> mapper = new ParameterizedRowMapper<Map<String, String>>() {

			public Map<String, String> mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				Map<String, String> map = new HashMap<String, String>();
				
				map.put("channel_id", rs.getString("channel_cd"));
				map.put("channel_desc", rs.getString("html"));

				return map;
			}
		};
		
		try {
			logger.debug("getVIMBundleFreeHDChannel @ OrderDAO: " + SQL);
			List<Map<String, String>> map = simpleJdbcTemplate
					.query(SQL, mapper, orderId);
			
			return map;
		
		}catch (EmptyResultDataAccessException erdae) {
			return null;
		}catch (Exception e) {
			logger.error("Exception caught in getVIMBundleFreeHDChannel()", e);
			throw new DAOException(e.getMessage(), e);
		}				
	}
	
	public List<Map<String, String>> getVIMBundleChannel(String orderId) throws DAOException{
		logger.debug("getVIMBundleChannel");
		String SQL = "	SELECT wtc.channel_cd, wtdl.html	"+
		"	  FROM bomweb_order bo,	"+
		"	       bomweb_subscribed_channel bsc,	"+
		"	       w_tv_channel wtc,	"+
		"	       w_tv_channel_group wtcg,	"+
		"	       w_tv_display_lkup wtdl	"+
		"	 WHERE bo.order_id = bsc.order_id	"+
		"	   AND bo.order_id = ?	"+
		"	   AND bsc.channel_id = wtc.channel_id	"+
		"	   AND wtc.channel_group_id = wtcg.channel_group_id	"+
		"	   AND wtdl.ID = bsc.channel_id	"+
		"	   AND wtdl.TYPE = 'CHANNEL'	"+
		"	   AND wtdl.locale = 'en'	"+
		"	   AND wtcg.channel_group_cd NOT IN ('STARTERPACK', 'ENTPACK', 'FREE2HD')	";
		
		ParameterizedRowMapper<Map<String, String>> mapper = new ParameterizedRowMapper<Map<String, String>>() {

			public Map<String, String> mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				Map<String, String> map = new HashMap<String, String>();
				
				map.put("channel_id", rs.getString("channel_cd"));
				map.put("channel_desc", rs.getString("html"));

				return map;
			}
		};
		
		try {
			logger.debug("getVIMBundleChannel @ OrderDAO: " + SQL);
			List<Map<String, String>> map = simpleJdbcTemplate
					.query(SQL, mapper, orderId);
			
			return map;
		
		}catch (EmptyResultDataAccessException erdae) {
			return null;
		}catch (Exception e) {
			logger.error("Exception caught in getVIMBundleChannel()", e);
			throw new DAOException(e.getMessage(), e);
		}

	}
	
	public void updateBomOcDetail(OrderImsDTO dto) throws DAOException{
		logger.debug("updateBomOcDetail");
		String SQL = "	UPDATE bomweb_order	" +
		"	   SET ocid = ?,	" +		
		"		   order_status = '02',	" +	
		"	       last_update_by = 'IMSAUTO',	" +
		"	       last_update_date = SYSDATE	" +
		"	 WHERE order_id = ?	" ;
		
		String SQL2 = "	UPDATE bomweb_customer	"+
		"	   SET service_num = ?,	cust_no = nvl(cust_no, ?), "+
		"	       last_upd_by = 'IMSAUTO',	"+
		"	       last_upd_date = SYSDATE	"+
		"	 WHERE order_id = ?	";
		
		String SQL3 = "	UPDATE q_work_queue	"+
		"	   SET BOM_OC_ID = ?, "+
		"	       last_upd_by = 'IMSAUTO',	"+
		"	       last_upd_date = SYSDATE	"+
		"	 WHERE sb_id = ?	";

		
		
		try {
			simpleJdbcTemplate.update(SQL, 
					dto.getOcId(),
					dto.getOrderId());
			
			simpleJdbcTemplate.update(SQL2, 
					dto.getMsisdn(),
					dto.getBomCustNo(),
					dto.getOrderId());
		} catch (Exception e) {
			logger.error("Exception caught in updateBomOcDetail()", e);
			throw new DAOException(e.getMessage(), e);
		}
		
		
		try {
			simpleJdbcTemplate.update(SQL3, 
					dto.getOcId(),
					dto.getOrderId());
		
		} catch (Exception e) {
			logger.error("Exception caught in updateBomOcDetail()", e);
			throw new DAOException(e.getMessage(), e);
		}

	}		
	
	public void updateVimBundleChannelStatus(String orderId) throws DAOException{
		logger.debug("updateVimBundleChannelStatus");
		String SQL = "	UPDATE bomweb_order_ims	"+
		"	   SET process_vim = 'D',	"+
		"	       last_upd_by = 'IMSAUTO',	"+
		"	       last_upd_date = SYSDATE	"+
		"	 WHERE order_id = ?	";
		
		try {
			
			simpleJdbcTemplate.update(SQL, orderId);
		
		} catch (Exception e) {
			logger.error("Exception caught in updateVimBundleChannelStatuss()", e);
			throw new DAOException(e.getMessage(), e);
		}

	}
	
	public void updateSuspendedOrderStatus(String reacd, int dur) throws DAOException{
		logger.debug("updateSuspendedOrderStatus");
		String SQL = "	UPDATE bomweb_order	"+
		"	   SET order_status = '20',	"+
		"	       reason_cd = 'C002',	"+
		"	       last_update_by = 'IMSAUTO',	"+
		"	       last_update_date = SYSDATE	"+
		"	 WHERE LOB = 'IMS'	"+
		"	   AND reason_cd = ?	"+
		"	   AND order_status = '10'	"+
		"	   AND nvl(last_update_date, create_date) + ? < SYSDATE	";

		
		try {
			
			simpleJdbcTemplate.update(SQL, reacd, dur);
		
		} catch (Exception e) {
			logger.error("Exception caught in updateSuspendedOrderStatus()", e);
			throw new DAOException(e.getMessage(), e);
		}
		
	}
	
	public void updateWaitingOrderStatus(String orderstatus, int dur) throws DAOException{
		logger.debug("updateWaitingOrderStatus");
		String SQL = "	UPDATE bomweb_order	"+
		"	   SET order_status = '20',	"+
		"	       reason_cd = 'C002',	"+
		"	       last_update_by = 'IMSAUTO',	"+
		"	       last_update_date = SYSDATE	"+
		"	 WHERE LOB = 'IMS'	"+
		"	   AND order_status = ?	"+
		"	   AND nvl(last_update_date, create_date) + ? < SYSDATE	";
		
		try {
			
			simpleJdbcTemplate.update(SQL, orderstatus, dur);
		
		} catch (Exception e) {
			logger.error("Exception caught in updateWaitingOrderStatus()", e);
			throw new DAOException(e.getMessage(), e);
		}
		
	}
	
	public List<CancelOrderDTO> getCancelOrderDetail() throws DAOException{
		logger.debug("getCancelOrderDetail");
		String SQL = "	SELECT a.order_id, a.login_id, b.id_doc_type, b.id_doc_num	"+
		"  FROM bomweb_customer b, bomweb_order_ims a" +
		" WHERE b.order_id = a.order_id" +
		"   AND a.order_id IN (" +
		"          SELECT order_id" +
		"            FROM bomweb_order bo, w_code_lkup wcl" +
		"           WHERE LOB = 'IMS'" +
		"             AND bo.reason_cd = wcl.code" +
		"             AND bo.order_status = '10'" +
		"             AND wcl.grp_id = decode(order_type, 'NTV-A','DS_NOW_AUTO_DEL_DAY', 'NTVAO','DS_NOW_AUTO_DEL_DAY', 'NTVUS','DS_NOW_AUTO_DEL_DAY', 'NTVRE','DS_NOW_AUTO_DEL_DAY', 'SB_IMS_AUTO_DEL_DAY') " +
		"             AND NVL (bo.last_update_date, bo.create_date) + wcl.description < SYSDATE" +
		"          UNION ALL" +
		"          SELECT order_id" +
		"            FROM bomweb_order bo, w_code_lkup wcl" +
		"           WHERE LOB = 'IMS'" +
		"             AND order_status = wcl.code" +
		"             AND wcl.grp_id = decode(order_type, 'NTV-A','DS_NOW_AUTO_DEL_DAY', 'NTVAO','DS_NOW_AUTO_DEL_DAY', 'NTVUS','DS_NOW_AUTO_DEL_DAY', 'NTVRE','DS_NOW_AUTO_DEL_DAY', 'SB_IMS_AUTO_DEL_DAY') " +
		"             AND NVL (bo.last_update_date, bo.create_date) + wcl.description < SYSDATE " +
		"          UNION ALL " +
        "          SELECT order_id " +
        "            FROM bomweb_order bo, w_code_lkup wcl " +
        "           WHERE LOB = 'IMS' " +
        "             AND bo.reason_cd = wcl.code " +
        "             AND bo.order_status = '10' " +
        "             AND wcl.grp_id = decode(order_type, 'NTV-A','DS_NOW_AUTO_DEL_ADAY', 'NTVAO','DS_NOW_AUTO_DEL_ADAY', 'NTVUS','DS_NOW_AUTO_DEL_ADAY', 'NTVRE','DS_NOW_AUTO_DEL_ADAY', 'SB_IMS_AUTO_DEL_ADAY') " +
        "             AND bo.app_date + wcl.description < SYSDATE  " +
        "          UNION ALL " +
        "          SELECT order_id " +
        "            FROM bomweb_order bo, w_code_lkup wcl " +
        "           WHERE LOB = 'IMS' " +
        "             AND order_status = wcl.code " +
        "             AND wcl.grp_id = decode(order_type, 'NTV-A','DS_NOW_AUTO_DEL_ADAY', 'NTVAO','DS_NOW_AUTO_DEL_ADAY', 'NTVUS','DS_NOW_AUTO_DEL_ADAY', 'NTVRE','DS_NOW_AUTO_DEL_ADAY', 'SB_IMS_AUTO_DEL_ADAY') " +
        "            AND bo.app_date + wcl.description < SYSDATE ) ";
		
		ParameterizedRowMapper<CancelOrderDTO> mapper = new ParameterizedRowMapper<CancelOrderDTO>() {

			public CancelOrderDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				CancelOrderDTO dto = new CancelOrderDTO();
					
				dto.setOrderId(rs.getString("order_id"));			
				dto.setLoginId(rs.getString("login_id"));
				dto.setIdDocType(rs.getString("id_doc_type"));
				dto.setIdDocNum(rs.getString("id_doc_num"));

				return dto;
			}
		};
		
		try {
			logger.debug("getCancelOrderDetail @ OrderDAO: " + SQL);
			List<CancelOrderDTO> orders = simpleJdbcTemplate
					.query(SQL, mapper);
			
			return orders;
		
		}catch (EmptyResultDataAccessException erdae) {
			return null;
		}catch (Exception e) {
			logger.error("Exception caught in getCancelOrderDetail()", e);
			throw new DAOException(e.getMessage(), e);
		}
		
	}
	
	public void updateCanceledOrderStatus(String orderId) throws DAOException{
		logger.debug("updateCanceledOrderStatus");
		String SQL = "	UPDATE bomweb_order	"+
				"	   SET order_status = '20',	"+
				"	       reason_cd = 'C002',	"+
				"	       last_update_by = 'IMSAUTO',	"+
				"	       last_update_date = SYSDATE	"+
				"	 WHERE order_id = ? AND LOB = 'IMS'	";
		
		try {
			
			simpleJdbcTemplate.update(SQL, orderId);			

		} catch (Exception e) {
			logger.error("Exception caught in updateCanceledOrderStatus()", e);
			throw new DAOException(e.getMessage(), e);
		}
		

	}
	
	public void updateCanceledOrderFailStatus(String orderId) throws DAOException{
		logger.debug("updateCanceledOrderFailStatus");
		
		String SQL = "	UPDATE bomweb_order	"+
				"	   SET order_status = '21',	"+
				"	       reason_cd = 'C002',	"+
				"	       last_update_by = 'IMSAUTO',	"+
				"	       last_update_date = SYSDATE	"+
				"	 WHERE order_id = ? AND LOB = 'IMS'	";
		
		try {
			
			simpleJdbcTemplate.update(SQL, orderId);			

		} catch (Exception e) {
			logger.error("Exception caught in updateCanceledOrderFailStatus()", e);
			throw new DAOException(e.getMessage(), e);
		}
		

	}
	
	public void deleteUpdateTable(String orderId) throws DAOException{
		logger.debug("deleteUpdateTable");
		String REMARKSQL = "DELETE bomweb_remark WHERE order_id = ? ";
		//String INVENTSQL = "DELETE bomweb_addr_inventory WHERE order_id = ? ";
		//String ACCTSQL = "DELETE bomweb_acct WHERE order_id = ? ";
		String ADDRSQL = "DELETE bomweb_cust_addr WHERE order_id = ? ";
		String COMPSQL = "DELETE bomweb_component WHERE order_id = ? ";
		String VASPARMSQL = "DELETE bomweb_vas_parm_ims WHERE order_id = ? ";
		String ITEMSQL = "DELETE bomweb_subscribed_item WHERE order_id = ? ";
		String CHANNELSQL = "DELETE bomweb_subscribed_channel WHERE order_id = ? ";
		String OPTOUTSQL = "DELETE bomweb_cust_optout_info WHERE order_id = ? ";
		String SALESREFERRERSQL = "DELETE bomweb_sales_referrer_ims WHERE order_id = ? ";
		String L2JOBSQL = "DELETE bomweb_order_l2_job WHERE order_id = ? ";
		String SUBSCRIBEDOFFERIMS = "DELETE BOMWEB_SUBSCRIBED_OFFER_IMS WHERE order_id = ? ";
		String THIRDPARTY = "DELETE BOMWEB_THIRD_PARTY_APPLN WHERE order_id = ? ";
		String BILLADDRESS = "DELETE BOMWEB_BILLING_ADDR WHERE order_id = ? ";
		String ORDERSERVICEIMS = "DELETE BOMWEB_ORDER_SERVICE_IMS WHERE order_id = ? ";
		
		try {
			
			simpleJdbcTemplate.update(REMARKSQL, orderId);
			//simpleJdbcTemplate.update(INVENTSQL, orderId);
			//simpleJdbcTemplate.update(ACCTSQL, orderId);
			simpleJdbcTemplate.update(ADDRSQL, orderId);
			simpleJdbcTemplate.update(COMPSQL, orderId);
			simpleJdbcTemplate.update(VASPARMSQL, orderId);
			simpleJdbcTemplate.update(ITEMSQL, orderId);
			simpleJdbcTemplate.update(CHANNELSQL, orderId);
			simpleJdbcTemplate.update(OPTOUTSQL, orderId);
			simpleJdbcTemplate.update(SALESREFERRERSQL, orderId);
			simpleJdbcTemplate.update(L2JOBSQL, orderId);
			simpleJdbcTemplate.update(SUBSCRIBEDOFFERIMS, orderId);
			simpleJdbcTemplate.update(THIRDPARTY, orderId);
			simpleJdbcTemplate.update(BILLADDRESS, orderId);
			simpleJdbcTemplate.update(ORDERSERVICEIMS, orderId);

		} catch (Exception e) {
			logger.error("Exception caught in deleteUpdateTable()", e);
			throw new DAOException(e.getMessage(), e);
		}
		
	}
	
	public void updateSalesChannel(String orderId) throws DAOException{
		logger.debug("updateSalesChannel");
//		String SQL = "	UPDATE bomweb_order a	"+
//		 "	   SET sales_channel = (SELECT sales_channel	"+
//		 "	                          FROM bomweb_shop	"+
//		 "	                         WHERE shop_cd = a.shop_cd AND channel_id = 1)	"+
//		 "	 WHERE order_id = ?	";
		
//		String SQL = "	UPDATE bomweb_order a	"+
//		"	   SET sales_channel =	"+
//		"	          NVL ((SELECT sales_channel	"+
//		"	                  FROM bomweb_shop	"+
//		"	                 WHERE shop_cd = a.shop_cd AND channel_id = 1),	"+
//		"	               (SELECT channel_cd	"+
//		"	                  FROM bomweb_sales_assignment	"+
//		"	                 WHERE staff_id = a.sales_cd	"+
//		"	                   AND TRUNC (SYSDATE) BETWEEN start_date	"+
//		"	                                           AND NVL (end_date, SYSDATE)	"+
//		"	                   AND ROWNUM = 1)	"+
//		"	              ),	"+
//		"	       sales_team =	"+
//		"	          (SELECT team_cd	"+
//		"	             FROM bomweb_sales_assignment	"+
//		"	            WHERE staff_id = a.sales_cd	"+
//		"	              AND TRUNC (SYSDATE) BETWEEN start_date AND NVL (end_date,	"+
//		"	                                                              SYSDATE	"+
//		"	                                                             )	"+
//		"	              AND ROWNUM = 1)	"+
//		"	 WHERE order_id = ?	";
		
		String SQL = "			UPDATE bomweb_order a                                                               "+
		"			    SET sales_channel =                                                             "+
		"			     NVL ((SELECT sales_channel                                                     "+
		"			                   FROM bomweb_shop                                                 "+
		"			                  WHERE shop_cd = a.shop_cd AND channel_id = 1),                    "+
		"			                (SELECT channel_cd                                                  "+
		"			                   FROM bomweb_sales_assignment x, bomweb_sales_profile y           "+
		"			                  WHERE (y.staff_id = a.sales_cd or y.org_staff_id = a.sales_cd)    "+
		"			         and x.staff_id = y.staff_id                                                "+
		"			      AND TRUNC (SYSDATE) BETWEEN x.start_date                                      "+
		"			                                            AND NVL (x.end_date, SYSDATE)           "+
		"			                    AND TRUNC (SYSDATE) BETWEEN y.start_date                        "+
		"			                                            AND NVL (y.end_date, SYSDATE)           "+
		"			                    AND ROWNUM = 1)                                                 "+
		"			               ),                                                                   "+
		"			        sales_team =                                                                "+
		"			           (SELECT team_cd                                                          "+
		"			              FROM bomweb_sales_assignment x, bomweb_sales_profile y                "+
		"			             WHERE (y.staff_id = a.sales_cd or y.org_staff_id = a.sales_cd)         "+
		"			    and x.staff_id = y.staff_id                                                     "+
		"			                   AND TRUNC (SYSDATE) BETWEEN y.start_date AND NVL (y.end_date,    "+
		"			                                                               SYSDATE              "+
		"			                                                              )                     "+
		"			               AND TRUNC (SYSDATE) BETWEEN x.start_date AND NVL (x.end_date,        "+
		"			                                                               SYSDATE              "+
		"			                                                              )                     "+
		"			               AND ROWNUM = 1)                                                      "+
		"     , create_channel =               "+
		  "      (select z.bom_shop_cd             "+
		  "       from bomweb_shop z, bomweb_sales_assignment x, bomweb_sales_profile y "+
		  "                     WHERE (y.staff_id = a.sales_cd or y.org_staff_id = a.sales_cd)    "+
		  "               and x.staff_id = y.staff_id                                     "+
		  "             AND TRUNC (SYSDATE) BETWEEN x.start_date                        "+
		  "                        AND NVL (x.end_date, SYSDATE)                      "+
		  "                       AND TRUNC (SYSDATE) BETWEEN y.start_date                        "+
		  "                        AND NVL (y.end_date, SYSDATE)                               "+
		  "        AND z.shop_cd = x.channel_cd         "+
		  "                       AND ROWNUM = 1)                                                 "+ 
		"			  WHERE order_id = ?  																															";
		
		try {
			
			simpleJdbcTemplate.update(SQL, orderId);			

		} catch (Exception e) {
			logger.error("Exception caught in updateSalesChannel()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public List<OrderDTO> getPendingOrderList(int withinMinute) throws DAOException{
		logger.debug("getPendingOrderList");
		
		String SQL = "	SELECT order_id " + 
				"	FROM bomweb_order	" +
				"	WHERE lob='IMS'	" +
				"	and order_status in ('00','03', '02') " +
				"	and ocid is null " +
				"	and (sysdate- sign_off_date )*60*24 >= 30 " +
				"	and (sysdate- sign_off_date )*60*24 <= ? "
				;
	
		ParameterizedRowMapper<OrderDTO> mapper = new ParameterizedRowMapper<OrderDTO>() {

		public OrderDTO mapRow(ResultSet rs, int rowNum)
				throws SQLException {
				OrderDTO dto = new OrderDTO();
				dto.setOrderId(rs.getString("order_id"));
				return dto;
			}
		};
		
		try {
			logger.debug("getPendingOrderList @ OrderDAO: " + SQL.toString());
			List<OrderDTO> orders = simpleJdbcTemplate
					.query(SQL, mapper,withinMinute);
			
			return orders;
		
		} catch (Exception e) {
			logger.error("Exception caught in getPendingOrderList()", e);
	
			throw new DAOException(e.getMessage(), e);
		}		
	}
	
	public boolean isLoginIdUsed(String loginId) throws DAOException{
		String sql = "Select count(*) from bomweb_order_ims " +
				" where login_id = ? ";

		try {
//			logger.debug("isLoginIdUsed @ ImsOrderDAO: " + sql);
			int count = simpleJdbcTemplate.queryForInt(sql, loginId);
			
			return count > 0 ;
		
		} catch (Exception e) {
			logger.error("Exception caught in isLoginIdUsed()", e);
	
			throw new DAOException(e.getMessage(), e);
		}		
	}
	
	
	public String getImsEdfRef(String orderId, String dtlId) throws DAOException {
		
		String edf = null;
		logger.debug("getImsEdfRef is called ");

		String SQL = "SELECT edf_ref FROM BOMWEB_ORDER_SERVICE_OTHER" +
		" WHERE ORDER_ID = ? " +
		" AND DTL_ID = ? ";


		try {
			edf = (String) simpleJdbcTemplate.queryForObject(SQL, 
					String.class, orderId, dtlId);

		} catch (EmptyResultDataAccessException erdae) {
			orderId = null;
		} catch (Exception e) {
			logger.error("Exception caught in getImsEdfRef()", e);
			throw new DAOException(e.getMessage(), e);
		}
		return edf;
	}	
	
	public List<ImsCollectDocDTO> getImsCollectDocList(String orderId) throws DAOException{
		logger.debug("getPendingOrderList");
		
		String SQL = "select a.DOC_TYPE, b.WAIVE_REASON, b.COLLECTED_IND, a.SERIAL from BOMWEB_ALL_ORD_DOC a, BOMWEB_ALL_ORD_DOC_ASSGN b " +
				"where a.order_id = ? " +
				"and a.ORDER_ID = b.ORDER_ID " +
				"and a.DOC_TYPE = b.DOC_TYPE " +
				"and a.OUTDATED_IND is null " +
				"and a.DOC_TYPE in ('I005', 'I006') "
				;
		
		ParameterizedRowMapper<ImsCollectDocDTO> mapper = new ParameterizedRowMapper<ImsCollectDocDTO>() {

			public ImsCollectDocDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				ImsCollectDocDTO dto = new ImsCollectDocDTO();
				
				String doctype = rs.getString("DOC_TYPE");
				if (doctype.equalsIgnoreCase("I005")){
					dto.setDocTypeDisplay("Third Party Credit Card Authorization Form");
				}else if (doctype.equalsIgnoreCase("I006")){
					dto.setDocTypeDisplay("Business Registration");
				}
				
				String waiveReason = rs.getString("WAIVE_REASON");
				
				if(waiveReason != null && !waiveReason.isEmpty()){
					dto.setWaiveReason(waiveReason);
				}else{
					dto.setWaiveReason("--");
				}
				
				String collectedInd = rs.getString("COLLECTED_IND");
				
				if(collectedInd != null && !collectedInd.isEmpty()){
					dto.setCollectedInd(collectedInd);
				}else{
					dto.setCollectedInd("N");
				}
				
				String faxSerial = rs.getString("SERIAL");
				
				if(faxSerial != null && !faxSerial.isEmpty()){
					dto.setFaxSerial(faxSerial);
				}else{
					dto.setFaxSerial("");
				}

				return dto;
			}
		};
		
		try {
			logger.debug("getImsCollectDocList @ OrderDAO: " + SQL.toString());
			List<ImsCollectDocDTO> imsCollectDocList = simpleJdbcTemplate
					.query(SQL, mapper, orderId);
			
			return imsCollectDocList;
		
		} catch (Exception e) {
			logger.error("Exception caught in getImsCollectDocList()", e);
	
			throw new DAOException(e.getMessage(), e);
		}		
	}
	
	public void insertBomWebOrderServiceIms(BomwebOrderServiceImsDTO dto) throws DAOException{
//		logger.info("insertBomWebOrderServiceIms");
		
		String SQL = "	insert into BOMWEB_ORDER_SERVICE_IMS(	"+
		"	ORDER_ID, FSA, BOM_ORDER_TYPE, LINE_TYPE, APP_LINE_TYPE,	"+
		"	PCD_TYPE, NOW_TV_TYPE, EYE_TYPE, NOW_TV_IND, PCD_IND,	"+
		"	REMOVE_WITH_EYE, ER_IND, PRO_RATA,DISC_REASON_CODE, CREATE_BY,	"+
		"	CREATE_DATE, UPDATE_BY, UPDATE_DATE, now_tv_pay_ch_ind	,BANDWIDTH"+
		"	) values(	"+
		"	?, ?, ?, ?, ?,	"+
		"	?, ?, ?, ?, ?,	"+
		"	?, ?, ?, ?,?,	"+
		"	sysdate, ?, ?,?,?	"+
		"	)	";
		
		try{
			simpleJdbcTemplate.update(SQL,
					dto.getOrderId(), dto.getFsa(), dto.getBomOrderType(), dto.getLineType(), dto.getAppLineType(),
					dto.getPcdType(), dto.getNowTvType(), dto.getEyeType(), dto.getNowTvInd(), dto.getPcdInd(),
					dto.getRemoveWithEye(), dto.getErInd(), dto.getProRata(),dto.getDiscReasonCode(), dto.getCreateBy(),
					dto.getUpdateBy(), dto.getUpdateDate(),dto.getNowTvPayChInd(),dto.getBandwidth()
					);
		}catch (Exception e) {
			logger.error("Exception caught in insertBomWebOrderServiceIms()", e);
			throw new DAOException(e.getMessage(), e);
		}
		
	}
	
	public void updateBomwebOrderServiceIms(BomwebOrderServiceImsDTO dto) throws DAOException{
		logger.debug("updateBomwebOrderServiceIms");
		
		String SQL ="	update BOMWEB_ORDER_SERVICE_IMS set	" +
		"	ORDER_ID = ?,	" +
		"	FSA = ?,	" +
		"	BOM_ORDER_TYPE = ?,	" +
		"	LINE_TYPE = ?,	" +
		"	APP_LINE_TYPE = ?,	" +
		"	PCD_TYPE = ?,	" +
		"	NOW_TV_TYPE = ?,	" +
		"	EYE_TYPE = ?,	" +
		"	NOW_TV_IND = ?,	" +
		"	PCD_IND = ?,	" +
		"	REMOVE_WITH_EYE = ?,	" +
		"	ER_IND = ?,	" +
		"	PRO_RATA = ?,	" +
		"   DISC_REASON_CODE = ?," +
		//"	CREATE_BY = ?,	" +
		//"	CREATE_DATE = ?,	" +
		"	UPDATE_BY = ?,	" +
		"	UPDATE_DATE = sysdate	," +
		"	now_tv_pay_ch_ind = ?	," +
		"	BANDWIDTH = ?	," +
		"	where ORDER_ID = ?	";

		try {
			simpleJdbcTemplate.update(SQL,
					dto.getOrderId(),
					dto.getFsa(),
					dto.getBomOrderType(),
					dto.getLineType(),
					dto.getAppLineType(),
					dto.getPcdType(),
					dto.getNowTvType(),
					dto.getEyeType(),
					dto.getNowTvInd(),
					dto.getPcdInd(),
					dto.getRemoveWithEye(),
					dto.getErInd(),
					dto.getProRata(),
					//dto.getCreateBy(),
					//dto.getCreateDate(),
					dto.getDiscReasonCode(),
					dto.getUpdateBy(),
					//dto.getUpdateDate(),
					dto.getNowTvPayChInd(),
					dto.getBandwidth(),
					dto.getOrderId()
					);

		} catch (Exception e) {
			logger.error("Exception caught in updateBomwebOrderServiceIms()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public List<BomwebOrderServiceImsDTO> getBomwebOrderServiceIms(String orderId) throws DAOException{
		logger.debug("getBomwebOrderServiceIms");
		
		String SQL = "	select	" +
		"	ORDER_ID, FSA, BOM_ORDER_TYPE, LINE_TYPE, APP_LINE_TYPE, PCD_TYPE,	" +
		"	NOW_TV_TYPE, EYE_TYPE, NOW_TV_IND, PCD_IND, REMOVE_WITH_EYE, ER_IND,	" +
		"	PRO_RATA, CREATE_BY, CREATE_DATE, UPDATE_BY, UPDATE_DATE, DISC_REASON_CODE	,now_tv_pay_ch_ind" +
		"	from BOMWEB_ORDER_SERVICE_IMS	" +
		"	where ORDER_ID = ?	";

		
		ParameterizedRowMapper<BomwebOrderServiceImsDTO> mapper = new ParameterizedRowMapper<BomwebOrderServiceImsDTO>() {

			public BomwebOrderServiceImsDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				BomwebOrderServiceImsDTO dto = new BomwebOrderServiceImsDTO();

				dto.setOrderId(rs.getString("ORDER_ID"));
				dto.setFsa(rs.getString("FSA"));
				dto.setBomOrderType(rs.getString("BOM_ORDER_TYPE"));
				dto.setLineType(rs.getString("LINE_TYPE"));
				dto.setAppLineType(rs.getString("APP_LINE_TYPE"));
				dto.setPcdType(rs.getString("PCD_TYPE"));
				dto.setNowTvType(rs.getString("NOW_TV_TYPE"));
				dto.setEyeType(rs.getString("EYE_TYPE"));
				dto.setNowTvInd(rs.getString("NOW_TV_IND"));
				dto.setPcdInd(rs.getString("PCD_IND"));
				dto.setRemoveWithEye(rs.getString("REMOVE_WITH_EYE"));
				dto.setErInd(rs.getString("ER_IND"));
				dto.setProRata(rs.getString("PRO_RATA"));
				dto.setCreateBy(rs.getString("CREATE_BY"));
				dto.setCreateDate(rs.getTimestamp("CREATE_DATE"));
				dto.setUpdateBy(rs.getString("UPDATE_BY"));
				dto.setUpdateDate(rs.getTimestamp("UPDATE_DATE"));
				dto.setDiscReasonCode(rs.getString("DISC_REASON_CODE"));
				dto.setNowTvPayChInd(rs.getString("now_tv_pay_ch_ind"));
			
				return dto;
			}
		};
		
		try {
			logger.debug("getBomwebOrderServiceIms @ OrderDAO: " + SQL);
			List<BomwebOrderServiceImsDTO> dtos = simpleJdbcTemplate
					.query(SQL, mapper, orderId);
			
			return dtos;
		
		} catch (Exception e) {
			logger.error("Exception caught in getBomwebOrderServiceIms()", e);

			throw new DAOException(e.getMessage(), e);
		}		
		
	}
		
	public List<CustomerBasicInfoDTO> getBomWebCustomerByCustId(String custId) {//wrongly done, should use BOM but not SpringBoard, Steven 20140612
		
		logger.info("getBomWebCustomerByCustId:"+custId);
		String SQL = "	SELECT order_id, cust_no, mob_cust_no, first_name, last_name, id_doc_type,	" +
		"	       id_doc_num, TO_CHAR(dob,'dd/MM/yyyy') dob, title, company_name, ind_type, ind_sub_type,	" +
		"	       nationality, create_date, addr_proof_ind, LOB, service_num, create_by,	" +
		"	       last_upd_by, last_upd_date, id_verified_ind, blacklist_ind,	" +
		"          cs_portal_ind, cs_portal_login, cs_portal_mobile, mismatch_ind  "	 +//Gary
		"	  FROM bomweb_customer	" +
		"	 WHERE cust_no = ?	" ;


		
		ParameterizedRowMapper<CustomerBasicInfoDTO> mapper = new ParameterizedRowMapper<CustomerBasicInfoDTO>() {

			public CustomerBasicInfoDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				CustomerBasicInfoDTO dto = new CustomerBasicInfoDTO();
					
//				dto.setOrderId(rs.getString("order_id"));
//				dto.setCustNo(rs.getString("cust_no"));
//				dto.setMobCustNo(rs.getString("mob_cust_no"));
				dto.setFirstName(rs.getString("first_name"));
				dto.setLastName(rs.getString("last_name"));
				dto.setIdDocType(rs.getString("id_doc_type"));
				dto.setIdDocNum(rs.getString("id_doc_num"));
				dto.setDob(rs.getString("dob"));
				dto.setTitle(rs.getString("title"));
				dto.setCompanyName(rs.getString("company_name"));
				dto.setSystemId(rs.getString("lob"));
//				dto.setIndType(rs.getString("ind_type"));
//				dto.setIndSubType(rs.getString("ind_sub_type"));
//				dto.setNationality(rs.getString("nationality"));				
//				dto.setAddrProofInd(rs.getString("addr_proof_ind"));
//				dto.setLob(rs.getString("lob"));
//				dto.setServiceNum(rs.getString("service_num"));				
//				dto.setIdVerified(rs.getString("id_verified_ind"));
//				dto.setBlacklistInd(rs.getString("blacklist_ind"));	
//				dto.setCsPortalInd(rs.getString("cs_portal_ind"));///Gary
//				dto.setCsPortalLogin(rs.getString("cs_portal_login"));
//				dto.setCsPortalMobile(rs.getString("cs_portal_mobile"));

				return dto;
			}
		};
		
		try {
			logger.debug("getBomWebCustomerByCustId @ OrderDAO: " + SQL+" custId:"+custId);
			List<CustomerBasicInfoDTO> cust = simpleJdbcTemplate.query(SQL, mapper, custId);
			
			return cust;
		
		} catch (Exception e) {
			logger.error("Exception caught in getBomWebCustomerByCustId()", e);
			try {
				throw new DAOException(e.getMessage(), e);
			} catch (DAOException e1) {
				e1.printStackTrace();
			}
		}
		return null;
	}
	
	public String genProgOfferChangeRmk(String i_staff_id, String i_related_sb, String i_price_ind, String i_old_install_fee, String i_new_install_fee, String[] i_old_item_list, String[] i_new_item_list, 
		String[] i_old_basket_list, String[] i_new_basket_list,
		int i_old_contract_period, int i_new_contract_period, 
		int i_old_credit, int i_new_credit, String[] i_old_channel_list, String[] i_new_channel_list, String[] i_old_backend_offer_list, String[] i_new_backend_offer_list, 
		String[] i_old_backend_channel_list, String[] i_new_backend_channel_list,
		String[] i_old_basket_item_id_list, String[] i_new_basket_item_id_list,
		String i_l1_order_num, OrderImsUI order, String i_pre_inst_ind) throws SQLException{
		
		String o_remark = "";
		int gnretval;
		int gnerrcode;
		String gserrtext;
		
		OraArrayVarChar2 a = new OraArrayVarChar2(ORACLE_NUM_ARRAY, i_old_item_list);
		OraArrayVarChar2 b = new OraArrayVarChar2(ORACLE_NUM_ARRAY, i_new_item_list);
		OraArrayVarChar2 c = new OraArrayVarChar2(ORACLE_NUM_ARRAY, i_old_channel_list);
		OraArrayVarChar2 d = new OraArrayVarChar2(ORACLE_NUM_ARRAY, i_new_channel_list);

		OraArrayVarChar2 f = new OraArrayVarChar2(ORACLE_NUM_ARRAY, i_old_backend_offer_list);
		OraArrayVarChar2 g = new OraArrayVarChar2(ORACLE_NUM_ARRAY, i_new_backend_offer_list);
		OraArrayVarChar2 j = new OraArrayVarChar2(ORACLE_NUM_ARRAY, i_old_backend_channel_list);
		OraArrayVarChar2 k = new OraArrayVarChar2(ORACLE_NUM_ARRAY, i_new_backend_channel_list);
		
		OraArrayVarChar2 l = new OraArrayVarChar2(ORACLE_NUM_ARRAY, i_old_basket_list);
		OraArrayVarChar2 m = new OraArrayVarChar2(ORACLE_NUM_ARRAY, i_new_basket_list);
		
		OraArrayVarChar2 n = new OraArrayVarChar2(ORACLE_NUM_ARRAY, i_old_basket_item_id_list);
		OraArrayVarChar2 o = new OraArrayVarChar2(ORACLE_NUM_ARRAY, i_new_basket_item_id_list);
		
		Connection conn = DataSourceUtils.getConnection(jdbcTemplate.getDataSource());
		

		SimpleJdbcCall jdbcCall =null;
		try {	
//			if(order.isOrderTypeNowRet()){
					jdbcCall = new SimpleJdbcCall(jdbcTemplate)
					.withSchemaName("ops$cnm")
					.withCatalogName("pkg_sb_ims_amend")
					.withProcedureName("gen_amend_remarks_ret_acq")
					.declareParameters(
							new SqlParameter("i_staff_id", Types.VARCHAR),
							new SqlParameter("i_related_sb", Types.VARCHAR),
							new SqlParameter("i_price_ind", Types.VARCHAR),
							new SqlParameter("i_old_install_fee", Types.VARCHAR),
							new SqlParameter("i_new_install_fee", Types.VARCHAR),
							new SqlParameter("i_old_item_list", Types.ARRAY),
							new SqlParameter("i_new_item_list", Types.ARRAY),
							new SqlParameter("i_old_basket_list", Types.ARRAY),
							new SqlParameter("i_new_basket_list", Types.ARRAY),
							new SqlParameter("i_old_contract_period", Types.INTEGER),
							new SqlParameter("i_new_contract_period", Types.INTEGER),
							new SqlParameter("i_old_credit", Types.INTEGER),
							new SqlParameter("i_new_credit", Types.INTEGER),
							new SqlParameter("i_old_channel_list", Types.ARRAY),
							new SqlParameter("i_new_channel_list", Types.ARRAY),
							new SqlParameter("i_old_backend_offer_list", Types.ARRAY),
							new SqlParameter("i_new_backend_offer_list", Types.ARRAY),
							new SqlParameter("i_old_backend_channel_list", Types.ARRAY),
							new SqlParameter("i_new_backend_channel_list", Types.ARRAY),
							new SqlParameter("i_old_basket_item_id_list", Types.ARRAY),
							new SqlParameter("i_new_basket_item_id_list", Types.ARRAY),
							new SqlParameter("i_l1_order_num", Types.VARCHAR),
							new SqlParameter("i_pre_inst_ind", Types.VARCHAR),
							new SqlParameter("i_order_id", Types.VARCHAR),
							new SqlParameter("i_txn_id", Types.VARCHAR),
							new SqlParameter("i_last_time_success_amend_seq", Types.VARCHAR),
							new SqlParameter("i_pre_inst_ind", Types.VARCHAR),
							new SqlOutParameter("o_remark", Types.VARCHAR),
							new SqlOutParameter("gnretval", Types.INTEGER), 
							new SqlOutParameter("gnerrcode", Types.INTEGER),
							new SqlOutParameter("gserrtext", Types.VARCHAR)
							);
//			}else{
//					jdbcCall = new SimpleJdbcCall(jdbcTemplate)
//					.withSchemaName("ops$cnm")
//					.withCatalogName("pkg_sb_ims_amend")
//					.withProcedureName("gen_amend_remarks_acq")
//					.declareParameters(
//							new SqlParameter("i_staff_id", Types.VARCHAR),
//							new SqlParameter("i_related_sb", Types.VARCHAR),
//							new SqlParameter("i_price_ind", Types.VARCHAR),
//							new SqlParameter("i_old_install_fee", Types.VARCHAR),
//							new SqlParameter("i_new_install_fee", Types.VARCHAR),
//							new SqlParameter("i_old_item_list", Types.ARRAY),
//							new SqlParameter("i_new_item_list", Types.ARRAY),
//							new SqlParameter("i_old_basket_list", Types.ARRAY),
//							new SqlParameter("i_new_basket_list", Types.ARRAY),
//							new SqlParameter("i_old_contract_period", Types.INTEGER),
//							new SqlParameter("i_new_contract_period", Types.INTEGER),
//							new SqlParameter("i_old_credit", Types.INTEGER),
//							new SqlParameter("i_new_credit", Types.INTEGER),
//							new SqlParameter("i_old_channel_list", Types.ARRAY),
//							new SqlParameter("i_new_channel_list", Types.ARRAY),
//							new SqlParameter("i_old_backend_offer_list", Types.ARRAY),
//							new SqlParameter("i_new_backend_offer_list", Types.ARRAY),
//							new SqlParameter("i_old_backend_channel_list", Types.ARRAY),
//							new SqlParameter("i_new_backend_channel_list", Types.ARRAY),
//							new SqlParameter("i_old_basket_item_id_list", Types.ARRAY),
//							new SqlParameter("i_new_basket_item_id_list", Types.ARRAY),
//							new SqlParameter("i_l1_order_num", Types.VARCHAR),
//							new SqlOutParameter("o_remark", Types.VARCHAR),
//							new SqlOutParameter("gnretval", Types.INTEGER), 
//							new SqlOutParameter("gnerrcode", Types.INTEGER),
//							new SqlOutParameter("gserrtext", Types.VARCHAR)
//							);
//			}
			logger.info("*******calling pkg_sb_ims_amend.gen_amend_remarks*******");
			logger.info("i_staff_id : " + i_staff_id);
			logger.info("i_related_sb : " + i_related_sb);
			logger.info("i_price_ind : " + i_price_ind);
			logger.info("i_old_install_fee : " + i_old_install_fee);
			logger.info("i_new_install_fee : " + i_new_install_fee);
			logger.info("i_old_item_list :" +  Arrays.toString(i_old_item_list));
			logger.info("i_new_item_list :" +  Arrays.toString(i_new_item_list));
			logger.info("i_old_basket_list :" +  Arrays.toString(i_old_basket_list));
			logger.info("i_new_basket_list :" +  Arrays.toString(i_new_basket_list));
			logger.info("i_old_contract_period : " + i_old_contract_period);
			logger.info("i_new_contract_period : " + i_new_contract_period);
			logger.info("i_old_credit : " + i_old_credit);
			logger.info("i_new_credit : " + i_new_credit);
			logger.info("i_old_channel_list : " + Arrays.toString(i_old_channel_list));
			logger.info("i_new_channel_list : " + Arrays.toString(i_new_channel_list));
			logger.info("i_old_backend_offer_list : " + Arrays.toString(i_old_backend_offer_list));
			logger.info("i_new_backend_offer_list : " + Arrays.toString(i_new_backend_offer_list));
			logger.info("i_old_backend_channel_list : " + Arrays.toString(i_old_backend_channel_list));
			logger.info("i_new_backend_channel_list : " + Arrays.toString(i_new_backend_channel_list));
			logger.info("i_old_basket_item_id_list : " + Arrays.toString(i_old_basket_item_id_list));
			logger.info("i_new_basket_item_id_list : " + Arrays.toString(i_new_basket_item_id_list));
			logger.info("i_l1_order_num : " + i_l1_order_num);			
			logger.info("i_pre_inst_ind : " + i_pre_inst_ind);

			MapSqlParameterSource in = new MapSqlParameterSource();
			in.addValue("i_staff_id",  i_staff_id);
			in.addValue("i_related_sb",  i_related_sb);
			in.addValue("i_price_ind",  i_price_ind);
			in.addValue("i_old_install_fee",  i_old_install_fee);
			in.addValue("i_new_install_fee",  i_new_install_fee);
			in.addValue("i_old_item_list",  a.getOracleArray(conn));
			in.addValue("i_new_item_list",  b.getOracleArray(conn));
			in.addValue("i_old_basket_list",  l.getOracleArray(conn));
			in.addValue("i_new_basket_list",  m.getOracleArray(conn));
			in.addValue("i_old_contract_period",  i_old_contract_period);
			in.addValue("i_new_contract_period",  i_new_contract_period);
			in.addValue("i_old_credit",  i_old_credit);
			in.addValue("i_new_credit",  i_new_credit);
			in.addValue("i_old_channel_list",  c.getOracleArray(conn));
			in.addValue("i_new_channel_list",  d.getOracleArray(conn));
			in.addValue("i_old_backend_offer_list",  f.getOracleArray(conn));
			in.addValue("i_new_backend_offer_list",  g.getOracleArray(conn));
			in.addValue("i_old_backend_channel_list",  j.getOracleArray(conn));
			in.addValue("i_new_backend_channel_list",  k.getOracleArray(conn));
			in.addValue("i_old_basket_item_id_list",  n.getOracleArray(conn));
			in.addValue("i_new_basket_item_id_list",  o.getOracleArray(conn));
			in.addValue("i_l1_order_num", i_l1_order_num);
			in.addValue("i_pre_inst_ind", i_pre_inst_ind);
			if(order.isOrderTypeNowRet()){
				in.addValue("i_order_id",  order.getOrderId());
				in.addValue("i_txn_id",  order.getCpqTxnId());
				in.addValue("i_last_time_success_amend_seq",  order.getLastTimeSuccessAmendSeq());
				logger.info("i_order_id() : " + order.getOrderId());
				logger.info("i_txn_id : " + order.getCpqTxnId());
				logger.info("i_last_time_success_amend_seq : " + order.getLastTimeSuccessAmendSeq());
				in.addValue("i_pre_inst_ind", null);
			} else {
				in.addValue("i_order_id",  "null");
				in.addValue("i_txn_id",  "null");
				in.addValue("i_last_time_success_amend_seq",  "null");
			}
			
			Map out = jdbcCall.execute(in); 
			
			o_remark = (String) out.get("o_remark");
			if (out.get("o_remark")==null)
			{
				o_remark = "";
			}
			gnretval = (Integer) out.get("gnretval");
			gnerrcode = (Integer) out.get("gnerrcode");
			gserrtext = (String) out.get("gserrtext");
			
			logger.info("o_remark : " + o_remark);
			logger.info("gnretval : " + gnretval);
			logger.info("gnerrcode : " + gnerrcode);
			logger.info("gserrtext : " + gserrtext);
			
			logger.info("*******calling pkg_sb_ims_amend.gen_amend_remarks(end)*******");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block 
			e.printStackTrace();
		} 
		
		return o_remark; 
	}
	
	public String getFileNameOfLatestSupportDocHKID(String orderId, String tmpFilePath) throws DAOException{
		logger.info("getFileNameOfLatestSupportDocHKID, orderId:"+orderId);		
		String SQL = " SELECT *" +
				"  FROM (SELECT   file_path_name" +
				"            FROM bomweb_all_ord_doc" +
				"           WHERE order_id = ? AND doc_type = 'I007'" +
				"        ORDER BY create_date DESC)" +
				" WHERE ROWNUM = 1";		
		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
			public String mapRow(ResultSet rs, int rowNum)	throws SQLException {
				String dto = rs.getString("file_path_name");
				return dto;
			}
		};		
		try {
			logger.debug("getFileNameOfLatestSupportDocHKID @ OrderDAO: " + SQL);
			List<String> orders = simpleJdbcTemplate.query(SQL, mapper, orderId);
			if(orders.size()>0){
				return tmpFilePath+orderId+"/"+orders.get(0);
			}else{
				logger.debug("error getFileNameOfLatestSupportDocHKID, no support doc:" + orderId);
				return tmpFilePath+"IMS_I007_0.pdf";
			}		
		} catch (Exception e) {
			logger.error("Exception caught in getFileNameOfLatestSupportDocHKID()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public void updateWqAttachmentDSMisMatch(String orderId, String newFilePath) throws DAOException{
		logger.info("updateWqAttachmentDSMisMatch, orderId:"+orderId+"  newFilePath:"+newFilePath);		
		String SQL = " UPDATE q_wq_document" +
				"   SET attachment_path = ?" +
				" WHERE wq_id IN (SELECT wq_id" +
				"                   FROM q_work_queue" +
				"                  WHERE sb_id = ?) " +
				"					AND document_type_id = '8'";		
		
		try {
			logger.debug("updateWqAttachmentDSMisMatch @ OrderDAO: " + SQL);
			simpleJdbcTemplate.update(SQL, newFilePath, orderId);
		} catch (Exception e) {
			logger.error("Exception caught in updateWqAttachmentDSMisMatch()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}

	public void insertBomwebAmendTables(String i_order_id, String i_wq_batch_id, String i_related_sb,  String isNtvOrder,
			String[] i_old_item_list, String[] i_new_item_list, 
			String[] i_old_channel_list, String[] i_new_channel_list, String[] i_old_backend_offer_list, String[] i_new_backend_offer_list,
			String[] i_old_backend_channel_list, String[] i_new_backend_channel_list, String[] i_old_basket_list, String[] i_new_basket_list,
			String[] i_old_basket_item_id_list, String[] i_new_basket_item_id_list) throws SQLException{
			
			String o_remark = "";
			int gnretval;
			int gnerrcode;
			String gserrtext;
			
			OraArrayVarChar2 a = new OraArrayVarChar2(ORACLE_NUM_ARRAY, i_old_item_list);
			OraArrayVarChar2 b = new OraArrayVarChar2(ORACLE_NUM_ARRAY, i_new_item_list);
			OraArrayVarChar2 c = new OraArrayVarChar2(ORACLE_NUM_ARRAY, i_old_channel_list);
			OraArrayVarChar2 d = new OraArrayVarChar2(ORACLE_NUM_ARRAY, i_new_channel_list);
			OraArrayVarChar2 f = new OraArrayVarChar2(ORACLE_NUM_ARRAY, i_old_backend_offer_list);
			OraArrayVarChar2 g = new OraArrayVarChar2(ORACLE_NUM_ARRAY, i_new_backend_offer_list);
			OraArrayVarChar2 j = new OraArrayVarChar2(ORACLE_NUM_ARRAY, i_old_backend_channel_list);
			OraArrayVarChar2 k = new OraArrayVarChar2(ORACLE_NUM_ARRAY, i_new_backend_channel_list);
			OraArrayVarChar2 l = new OraArrayVarChar2(ORACLE_NUM_ARRAY, i_old_basket_list);
			OraArrayVarChar2 m = new OraArrayVarChar2(ORACLE_NUM_ARRAY, i_new_basket_list);
			OraArrayVarChar2 n = new OraArrayVarChar2(ORACLE_NUM_ARRAY, i_old_basket_item_id_list);
			OraArrayVarChar2 o = new OraArrayVarChar2(ORACLE_NUM_ARRAY, i_new_basket_item_id_list);
			
			Connection conn = DataSourceUtils.getConnection(jdbcTemplate.getDataSource());
			
			try {	
				SimpleJdbcCall jdbcCall = null;
				if ("Y".equals(isNtvOrder)) {
					jdbcCall = new SimpleJdbcCall(jdbcTemplate)
						.withSchemaName("ops$cnm")
						.withCatalogName("pkg_sb_ims_amend")
						.withProcedureName("insert_amend_tables_acq_ret")
						.declareParameters(
								new SqlParameter("i_order_id", Types.VARCHAR),
								new SqlParameter("i_wq_batch_id", Types.VARCHAR),
								new SqlParameter("i_related_sb", Types.VARCHAR),
								new SqlParameter("i_old_basket_list", Types.ARRAY),
								new SqlParameter("i_new_basket_list", Types.ARRAY),
								new SqlParameter("i_old_item_list", Types.ARRAY),
								new SqlParameter("i_new_item_list", Types.ARRAY),
								new SqlParameter("i_old_channel_list", Types.ARRAY),
								new SqlParameter("i_new_channel_list", Types.ARRAY),
								new SqlParameter("i_old_backend_offer_list", Types.ARRAY),
								new SqlParameter("i_new_backend_offer_list", Types.ARRAY),
								new SqlParameter("i_old_backend_channel_list", Types.ARRAY),
								new SqlParameter("i_new_backend_channel_list", Types.ARRAY),
								new SqlParameter("i_old_basket_item_id_list", Types.ARRAY),
								new SqlParameter("i_new_basket_item_id_list", Types.ARRAY),
								new SqlOutParameter("gnretval", Types.INTEGER), 
								new SqlOutParameter("gnerrcode", Types.INTEGER),
								new SqlOutParameter("gserrtext", Types.VARCHAR)
								);
				} else {
					jdbcCall = new SimpleJdbcCall(jdbcTemplate)
					.withSchemaName("ops$cnm")
					.withCatalogName("pkg_sb_ims_amend")
					.withProcedureName("insert_amend_tables")
					.declareParameters(
							new SqlParameter("i_order_id", Types.VARCHAR),
							new SqlParameter("i_wq_batch_id", Types.VARCHAR),
							new SqlParameter("i_related_sb", Types.VARCHAR),
							new SqlParameter("i_old_basket_list", Types.ARRAY),
							new SqlParameter("i_new_basket_list", Types.ARRAY),
							new SqlParameter("i_old_item_list", Types.ARRAY),
							new SqlParameter("i_new_item_list", Types.ARRAY),
							new SqlParameter("i_old_channel_list", Types.ARRAY),
							new SqlParameter("i_new_channel_list", Types.ARRAY),
							new SqlParameter("i_old_backend_offer_list", Types.ARRAY),
							new SqlParameter("i_new_backend_offer_list", Types.ARRAY),
							new SqlParameter("i_old_backend_channel_list", Types.ARRAY),
							new SqlParameter("i_new_backend_channel_list", Types.ARRAY),
							new SqlOutParameter("gnretval", Types.INTEGER), 
							new SqlOutParameter("gnerrcode", Types.INTEGER),
							new SqlOutParameter("gserrtext", Types.VARCHAR)
							);
				}
				
				logger.info("*******calling pkg_sb_ims_amend.insert_amend_tables*******");
				logger.info("i_order_id : " + i_order_id);
				logger.info("i_wq_batch_id : " + i_wq_batch_id);
				logger.info("i_related_sb : " + i_related_sb);
				logger.info("i_old_item_list :" +  Arrays.toString(i_old_item_list));
				logger.info("i_new_item_list :" +  Arrays.toString(i_new_item_list));
				logger.info("i_old_channel_list : " + Arrays.toString(i_old_channel_list));
				logger.info("i_new_channel_list : " + Arrays.toString(i_new_channel_list));
				logger.info("i_old_backend_offer_list : " + Arrays.toString(i_old_backend_offer_list));
				logger.info("i_new_backend_offer_list : " + Arrays.toString(i_new_backend_offer_list));
				logger.info("i_old_backend_channel_list : " + Arrays.toString(i_old_backend_channel_list));
				logger.info("i_new_backend_channel_list : " + Arrays.toString(i_new_backend_channel_list));
				logger.info("i_old_basket_list : " + Arrays.toString(i_old_basket_list));
				logger.info("i_new_basket_list : " + Arrays.toString(i_new_basket_list));
				logger.info("i_old_basket_item_id_list : " + Arrays.toString(i_old_basket_item_id_list));
				logger.info("i_new_basket_item_id_list : " + Arrays.toString(i_new_basket_item_id_list));
				

				MapSqlParameterSource in = new MapSqlParameterSource();
				in.addValue("i_order_id",  i_order_id);
				in.addValue("i_wq_batch_id",  i_wq_batch_id);
				in.addValue("i_related_sb",  i_related_sb);
				in.addValue("i_old_basket_list",  l.getOracleArray(conn));
				in.addValue("i_new_basket_list",  m.getOracleArray(conn));
				in.addValue("i_old_item_list",  a.getOracleArray(conn));
				in.addValue("i_new_item_list",  b.getOracleArray(conn));
				in.addValue("i_old_channel_list",  c.getOracleArray(conn));
				in.addValue("i_new_channel_list",  d.getOracleArray(conn));
				in.addValue("i_old_backend_offer_list",  f.getOracleArray(conn));
				in.addValue("i_new_backend_offer_list",  g.getOracleArray(conn));
				in.addValue("i_old_backend_channel_list",  j.getOracleArray(conn));
				in.addValue("i_new_backend_channel_list",  k.getOracleArray(conn));
				if ("Y".equals(isNtvOrder)) {
					in.addValue("i_old_basket_item_id_list",  n.getOracleArray(conn));
					in.addValue("i_new_basket_item_id_list",  o.getOracleArray(conn));
				}
				
				Map out = jdbcCall.execute(in); 
				
				//gnerrcode = (Integer) out.get("gnerrcode");
				//gserrtext = (String) out.get("gserrtext");
				
				logger.info("*******calling pkg_sb_ims_amend.insert_amend_tables(end)*******");
				
			} catch (Exception e) {
				// TODO Auto-generated catch block 
				e.printStackTrace();
			} 
		}
	

	
	public String isDsStaffMobbile(String staffId, String mobNum) throws DAOException{
		logger.info("isDsStaffMobbile, staffId:"+staffId+"  mobNum:"+mobNum);		
		String SQL = " select staff_code " +
				//" from bomweb_sales_type where staff_type = 'MOBILE' and staff_id = ? ";
		" from bomweb_sales_type where staff_type = 'MOBILE' and staff_id = ? and (eff_end_date is null or eff_end_date > sysdate ) ";
		
		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
			public String mapRow(ResultSet rs, int rowNum)	throws SQLException {
				String dto = rs.getString("staff_code");
				return dto;
			}
		};		
		try {
			logger.debug("isDsStaffMobbile @ OrderDAO SQL: " + SQL);//find if staff exist
			List<String> staffMobNumbers = simpleJdbcTemplate.query(SQL, mapper, staffId);
			if(staffMobNumbers.size()>0){
				for(String staffMobNum:staffMobNumbers){
					if(mobNum!=null && mobNum.indexOf(staffMobNum)!=-1){
//						logger.info("is Ds Staff Mobile, staffId:"+staffId+"  mobNum:"+mobNum);						
						logger.info("is valid Ds Staff Mobile, staffId:"+staffId+"  mobNum:"+mobNum);
						return "Y";
					}
				}
//				logger.info("not Ds Staff Mobile, staffId:"+staffId+"  mobNum:"+mobNum);
				logger.info("not valid Ds Staff Mobile, staffId:"+staffId+"  mobNum:"+mobNum);
				return "N";
			}else{
				logger.info("no tied-up record found in DB, so let him/her pass, staffId:"+staffId+"  mobNum:"+mobNum);
//				logger.info("no record found in DB, so let him/her pass, staffId:"+staffId+"  mobNum:"+mobNum);
				return "Y";
			}
		} catch (Exception e) {
			logger.error("Exception caught in isDsStaffMobbile()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	public boolean acqOrdBackendOfferChange(String orderId, String sysId) throws DAOException{
		logger.info("acqordBackendOfferChange is called order:"+orderId+" sysId:"+sysId);
		try{
			long starttime = System.currentTimeMillis();
//			logger.info("Start time - ord_backend_offer_change: " + starttime);
			
			SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
			.withSchemaName("OPS$CNM")
			.withCatalogName("PKG_SB_IMS_LTS")
			.withProcedureName("acqord_backend_offer_change")
			.declareParameters(						
					new SqlParameter("i_order_id", Types.VARCHAR),
					new SqlParameter("i_sys_id", Types.VARCHAR),
					new SqlOutParameter("gnRetVal", Types.INTEGER),
					new SqlOutParameter("gnErrCode", Types.INTEGER),
					new SqlOutParameter("gsErrText", Types.VARCHAR)
					);
		
			
			MapSqlParameterSource inMap = new MapSqlParameterSource();
			inMap.addValue("i_order_id",  orderId);
			inMap.addValue("i_sys_id", sysId);
			SqlParameterSource in = inMap;
			Map out = jdbcCall.execute(in);
			
			long endtime = System.currentTimeMillis();
			long callTime = endtime - starttime;
//			logger.info("End time - acqOrdBackendOfferChange: " + endtime);
			logger.info("Total call time - acqOrdBackendOfferChange: " + callTime + " ms");
			
			if((Integer)out.get("gnRetVal")!=0){
				logger.debug((String)out.get("gsErrText"));
				throw new Exception((String)out.get("gsErrText"));
			}
			
			return true;
			
		}catch (Exception e) {
			logger.error("Exception caught in acqOrdBackendOfferChange()", e);
			throw new DAOException(e.getMessage(), e);
		}			
		
	}
	
	public String getLatestAmendSeq (String orderId) throws DAOException {
		
		String amendSeq = null;
		logger.debug("getImsEdfRef is called ");

		String SQL = 
			"select amend_seq from bomweb_amend_category where order_id = ? and wq_batch_id = (		"+
			"select max(wq_batch_id) from q_wq_wp_search_id_v a where                             "+
			"sb_id = ? and status_cd = '090' and wq_nature_id = 362)                              ";


		try {
			amendSeq = (String) simpleJdbcTemplate.queryForObject(SQL, 
					String.class, orderId, orderId);

		} catch (EmptyResultDataAccessException erdae) {
			amendSeq = null;
		} catch (Exception e) {
			logger.error("Exception caught in getImsEdfRef()", e);
			throw new DAOException(e.getMessage(), e);
		}
		return amendSeq;
	}
	
	public String getLatestAmendSeq4Ntv (String orderId) throws DAOException {
		
		String amendSeq = null;
		logger.debug("getImsEdfRef is called ");

		String SQL = 
			"select amend_seq from bomweb_amend_category where order_id = ? and wq_batch_id = (		"+
			"select max(wq_batch_id) from q_wq_wp_search_id_v a where                             "+
			"sb_id = ? and status_cd = '090' and wq_nature_id = 368)                              ";


		try {
			amendSeq = (String) simpleJdbcTemplate.queryForObject(SQL, 
					String.class, orderId, orderId);

		} catch (EmptyResultDataAccessException erdae) {
			amendSeq = null;
		} catch (Exception e) {
			logger.error("Exception caught in getImsEdfRef()", e);
			throw new DAOException(e.getMessage(), e);
		}
		return amendSeq;
	}
	
	public String getNewAmendSeq4Ntv (String orderId) throws DAOException {
		String amendSeq = null;
		logger.debug("getNewAmendSeq4Ntv is called ");

		String SQL = 
			"select nvl(max(amend_seq),0)+1 from BOMWEB_AMEND_CATEGORY where order_id = :orderId";
		try {
			amendSeq = (String) simpleJdbcTemplate.queryForObject(SQL, 
					String.class, orderId);

		} catch (EmptyResultDataAccessException erdae) {
			amendSeq = null;
		} catch (Exception e) {
			logger.error("Exception caught in getNewAmendSeq4Ntv()", e);
			throw new DAOException(e.getMessage(), e);
		}
		return amendSeq;
	}
	
	public List<SubscribedItemDTO> getSubscribedItemAmd(String orderId, String amendSeq) throws DAOException{
		logger.debug("getSubscribedItemAmd");
		String SQL = 
			"select order_id, ID, TYPE, create_date, basket_id, create_by, last_upd_by, last_upd_date, campaign_cd	"+
			"from bomweb_sub_item_amd where order_id = ?                                              "+
			"and amend_seq = " +amendSeq +"                                                            "+
			"and io_ind in ('I',' ')                                                                 " +
			"and create_by not in ('BACKEND')";
		
		ParameterizedRowMapper<SubscribedItemDTO> mapper = new ParameterizedRowMapper<SubscribedItemDTO>() {

			public SubscribedItemDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				SubscribedItemDTO dto = new SubscribedItemDTO();
					
				dto.setOrderId(rs.getString("order_id"));
				dto.setId(rs.getString("id"));
				dto.setType(rs.getString("type"));				
				dto.setBasketId(rs.getString("basket_id"));
				dto.setCreateDate(rs.getTimestamp("create_date"));
				dto.setCreateBy(rs.getString("create_by"));
				dto.setLastUpdBy(rs.getString("last_upd_by"));
				dto.setLastUpdDate(rs.getTimestamp("last_upd_date"));
				dto.setCampaignCd(rs.getString("campaign_cd"));

				return dto;
			}
		};
		
		try {
			logger.debug("getSubscribedItemAmd @ OrderDAO: " + SQL);
			List<SubscribedItemDTO> orders = simpleJdbcTemplate
					.query(SQL, mapper, orderId);
			
			return orders;
		
		} catch (EmptyResultDataAccessException erdae) {
			return new ArrayList<SubscribedItemDTO>();
	
		}catch (Exception e) {
			logger.error("Exception caught in getSubscribedItemAmd()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public List<SubscribedChannelDTO> getSubscribedChannelAmd(String orderId, String amendSeq) throws DAOException{
		logger.debug("getSubscribedChannelAmd");
		String SQL = 
			"select order_id, dtl_id, channel_id, create_by, create_date, last_upd_by, last_upd_date	"+
			"from bomweb_sub_channel_amd                                                              "+
			"where order_id = ?                                                                       "+
			"and amend_seq = " +amendSeq +"                                                            "+
			"and io_ind in ('I',' ')       " +
			"and create_by not in ('BACKEND')                                                          ";

		
		ParameterizedRowMapper<SubscribedChannelDTO> mapper = new ParameterizedRowMapper<SubscribedChannelDTO>() {

			public SubscribedChannelDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				SubscribedChannelDTO dto = new SubscribedChannelDTO();
					
				dto.setOrderId(rs.getString("order_id"));
				dto.setDtlId(rs.getString("dtl_id"));
				dto.setChannelId(rs.getString("channel_id"));
				dto.setCreateBy(rs.getString("create_by"));
				dto.setCreateDate(rs.getTimestamp("create_date"));
				dto.setLastUpdBy(rs.getString("last_upd_by"));
				dto.setLastUpdDate(rs.getTimestamp("last_upd_date"));

				return dto;
			}
		};
		
		try {
			logger.debug("getSubscribedChannelAmd @ OrderDAO: " + SQL);
			List<SubscribedChannelDTO> orders = simpleJdbcTemplate
					.query(SQL, mapper, orderId);
			
			return orders;
		
		} catch (EmptyResultDataAccessException erdae) {
			return new ArrayList<SubscribedChannelDTO>();
	
		}catch (Exception e) {
			logger.error("Exception caught in getSubscribedChannelAmd()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	

	
	public List<ComponentDTO> getComponentAmd(String orderId, String amendSeq) throws DAOException{
		logger.debug("getComponentAmd");
		String SQL = "	SELECT order_id, attb_id, attb_value, create_date, create_by, last_upd_by,	" +
		"	       last_upd_date	" +
		"	  FROM bomweb_component_amd	" +
		"	 WHERE order_id = ?	"+
	    "	 AND amend_seq = " +amendSeq +"                                                            "+
		"	 AND io_ind in ('I',' ')                                                                 ";

		
		ParameterizedRowMapper<ComponentDTO> mapper = new ParameterizedRowMapper<ComponentDTO>() {

			public ComponentDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				ComponentDTO dto = new ComponentDTO();
					
				dto.setOrderId(rs.getString("order_id"));
				dto.setAttbId(rs.getString("attb_id"));
				dto.setAttbValue(rs.getString("attb_value"));						

				return dto;
			}
		};
		
		try {
			logger.debug("getComponentAmd @ OrderDAO: " + SQL);
			List<ComponentDTO> orders = simpleJdbcTemplate
					.query(SQL, mapper, orderId);
			
			return orders;
		
		} catch (Exception e) {
			logger.error("Exception caught in getComponent()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public List<SubscribedCslOfferDTO> getSubscribedImsVasParmAmd(String orderId, String amendSeq) throws DAOException{
		logger.info("getSubscribedImsVasParmAmd");
		String SQL = "	SELECT ORDER_ID, PRD_ID, ITEM_ID, VAS_TYPE, PARM_A, PARM_A_CD," +
					" PARM_B, PARM_B_CD, PARM_C, PARM_C_CD, CREATE_BY, CREATE_DATE, LAST_UPD_BY, LAST_UPD_DATE	" +
		"	  FROM bomweb_vas_parm_ims_amd	" +
		"	 WHERE ORDER_ID = ?	" +
		"	 and vas_type <> 'MOBILEPIN'"+
	    "	 AND amend_seq = " +amendSeq +"                                                            "+
		"	 AND io_ind in ('I',' ')                                                                 ";
			
		
		
		ParameterizedRowMapper<SubscribedCslOfferDTO> mapper = new ParameterizedRowMapper<SubscribedCslOfferDTO>() {

			public SubscribedCslOfferDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				SubscribedCslOfferDTO dto = new SubscribedCslOfferDTO();
					
				dto.setOrder_id(rs.getString("ORDER_ID"));
				dto.setProd_id(rs.getString("PRD_ID"));
				dto.setItem_id(rs.getString("ITEM_ID"));
				dto.setVas_type(rs.getString("VAS_TYPE"));
				dto.setVas_parm_a(rs.getString("PARM_A"));
				dto.setVas_parm_a_cd(rs.getString("PARM_A_CD"));
				dto.setVas_parm_b(rs.getString("PARM_B"));
				dto.setVas_parm_b_cd(rs.getString("PARM_B_CD"));
				dto.setVas_parm_c(rs.getString("PARM_C"));
				dto.setVas_parm_c_cd(rs.getString("PARM_C_CD"));
				dto.setCreateDate(rs.getTimestamp("CREATE_DATE"));
				dto.setCreateBy(rs.getString("CREATE_BY"));
				dto.setLastUpdBy(rs.getString("LAST_UPD_BY"));
				dto.setLastUpdDate(rs.getTimestamp("LAST_UPD_DATE"));
				
				return dto;
			}
		};
		
		try {
			logger.debug("getSubscribedImsVasParmAmd @ OrderDAO: " + SQL);
			List<SubscribedCslOfferDTO> orders = simpleJdbcTemplate
					.query(SQL, mapper, orderId);
			
			return orders;
		
		} catch (Exception e) {
			logger.error("Exception caught in getSubscribedImsVasParmAmd()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public List<CustAddrDTO> getBomWebCustAddrAmd(String orderId, String amendSeq) throws DAOException{
		logger.debug("getBomWebCustAddr");
		//String SQL = "	SELECT order_id, addr_usage, flat, area_cd, dist_no, sect_cd, str_name,	" +
		String SQL = 
			"select order_id, addr_usage, area_cd, dist_no, sect_cd, str_name,				"+
			"hi_lot_no, str_cat_cd, build_no, foreign_addr_flag, floor_no, unit_no,	  "+
			"po_box_no, addr_type, str_no, sect_dep_ind, create_date, area_desc,	    "+
			"dist_desc, sect_desc, str_cat_desc, create_by, last_upd_by,	            "+
			"last_upd_date, serbdyno, blacklist_ind, dtl_id	                          "+
			"from bomweb_cust_addr_amd                                                "+
			"where order_id = ?           						                                "+
			"and amend_seq = " +amendSeq +"                                                            ";
		
		ParameterizedRowMapper<CustAddrDTO> mapper = new ParameterizedRowMapper<CustAddrDTO>() {

			public CustAddrDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				CustAddrDTO dto = new CustAddrDTO();
					
				dto.setOrderId(rs.getString("order_id"));
				dto.setAddrUsage(rs.getString("addr_usage"));
				//dto.setFlat(rs.getString("flat"));
				dto.setAreaCd(rs.getString("area_cd"));
				dto.setDistNo(rs.getString("dist_no"));
				dto.setSectCd(rs.getString("sect_cd"));
				dto.setStrName(rs.getString("str_name"));
				dto.setHiLotNo(rs.getString("hi_lot_no"));
				dto.setStrCatCd(rs.getString("str_cat_cd"));
				dto.setBuildNo(rs.getString("build_no"));
				dto.setForeignAddrFlag(rs.getString("foreign_addr_flag"));
				dto.setFloorNo(rs.getString("floor_no"));
				dto.setUnitNo(rs.getString("unit_no"));
				dto.setPoBoxNo(rs.getString("po_box_no"));
				dto.setAddrType(rs.getString("addr_type"));
				dto.setStrNo(rs.getString("str_no"));
				dto.setSectDepInd(rs.getString("sect_dep_ind"));				
				dto.setAreaDesc(rs.getString("area_desc"));
				dto.setDistDesc(rs.getString("dist_desc"));
				dto.setSectDesc(rs.getString("sect_desc"));
				dto.setStrCatDesc(rs.getString("str_cat_desc"));				
				dto.setSerbdyno(rs.getString("serbdyno"));
				dto.setBlacklistInd(rs.getString("blacklist_ind"));
				dto.setDtlId(rs.getString("dtl_id"));
			

				return dto;
			}
		};
		
		try {
			logger.debug("getBomWebCustAddr @ OrderDAO: " + SQL);
			List<CustAddrDTO> orders = simpleJdbcTemplate.query(SQL, mapper, orderId);
			
			return orders;
		
		} catch (Exception e) {
			logger.error("Exception caught in getBomWebCustAddr()", e);
			throw new DAOException(e.getMessage(), e);
		}
		
	}
	
public boolean checkifAddressExactMatchwService(String sbNum, String floor, String unitNo) throws DAOException{
		
		logger.debug("checkifAddressExactMatchwService is called");
		boolean check=false;
		List<String> osOrderList = new ArrayList<String>();
		
		StringBuilder SQL= new StringBuilder();
		SQL.append("	SELECT oc_id	 ");
		SQL.append("	  FROM b_ord_latest_status	 ");
		SQL.append("	 WHERE (oc_id, dtl_id) IN (	 ");
		SQL.append("	          SELECT oc_id, dtl_id	 ");
		SQL.append("	            FROM b_ord_srv_addr	 ");
		SQL.append("	           WHERE addr_id IN (	 ");
		SQL.append("	                    SELECT addrid || ''	 ");
		SQL.append("	                      FROM address	 ");
		SQL.append("	                     WHERE serbdyno = ?	 ");
		SQL.append("	                       AND NVL (floornb, ' ') = NVL (?, ' ')	 ");
		SQL.append("	                       AND NVL (unitnb, ' ') = NVL (?, ' ')))	 ");
		SQL.append("	   AND NVL (status, '00') <> '06'	 ");
		SQL.append("	   AND NVL (bom_status, '00') <> '04'	 ");
		SQL.append("	   AND NVL (bom_status, '00') <> '07'	 ");

		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
			public String mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				String dto = new String();
				dto = (String) rs.getString("OC_ID");

				return dto;
			}
		};
		
		try {
			logger.info("checkifAddressExactMatchwService() @ ImsAddressInfoDAO: " + SQL);
			osOrderList = simpleJdbcTemplate.query(SQL.toString(), mapper, sbNum, floor, unitNo);

		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException in getOsOrder()");

			osOrderList = null;
		} catch (Exception e) {
			logger.info("Exception caught in getOsOrder():", e);

			throw new DAOException(e.getMessage(), e);
		}
		if (osOrderList.size() > 0)
			check = true;
		else check =false;
		return check;
		
	}

	public boolean checkPCDretentionPeriod(String serviceNum) throws DAOException{
		
		logger.debug("checkPCDretentionPeriod is called");
		// TODO Auto-generated method stub
		String SQL =  " select count(*) count from SVC_DW_IMS_DAY where service_term_DATE < SYSDATE " +
					  "AND service_term_DATE > ADD_Months(SYSDATE,-4) and fsa = :fsa "; 
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("fsa", serviceNum);
		
		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
		    public String mapRow(ResultSet rs, int rowNum) throws SQLException {
		    	String temp = new String();
		    	temp=(rs.getString("count"));
		        return temp;
		    }
		};
		
    	List<String> resultList = new ArrayList<String>();
		
		try {
			logger.debug("checkPCDretentionPeriod SQL:" + SQL);
			resultList = simpleJdbcTemplate.query(SQL, mapper,params);
		} catch (EmptyResultDataAccessException erdae) {
			logger.debug("EmptyResultDataAccessException");
			resultList = null;
		} catch (Exception e) {
			logger.debug("Exception caught in checkPCDretentionPeriod:", e);
			throw new DAOException(e.getMessage(), e);
		}	
		return (resultList.size()>0 ? !"0".equals(resultList.get(0)):false);
		
	}
	
public boolean checkLTSretentionPeriod(String serviceNum) throws DAOException{
		
		logger.debug("checkLTSretentionPeriod is called");
		// TODO Auto-generated method stub
		

		String SQL =  " select count(*) count from SVC_DW_LTS_DAY where curr_plan_end_date < SYSDATE " +
					  "	AND curr_plan_end_date > ADD_Months(SYSDATE,-4) and service_id = :serviceID ";
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("serviceID", serviceNum);
		
		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
		    public String mapRow(ResultSet rs, int rowNum) throws SQLException {
		    	String temp = new String();
		    	temp=(rs.getString("count"));
		        return temp;
		    }
		};
		List<String> resultList = new ArrayList<String>();
	
		try {
			logger.debug("checkLTSretentionPeriod SQL:" + SQL);
			resultList = simpleJdbcTemplate.query(SQL, mapper, params);
		} catch (EmptyResultDataAccessException erdae) {
			logger.debug("EmptyResultDataAccessException");
			resultList = null;
		} catch (Exception e) {
			logger.debug("Exception caught in checkLTSretentionPeriod:", e);
			throw new DAOException(e.getMessage(), e);
		}	
		return (resultList.size()>0 ? !"0".equals(resultList.get(0)):false);
		
	}

public boolean checkPCDexistingServcie(String serviceNum) throws DAOException{
	
		logger.debug("checkPCDexistingServcie is called");
		
		String SQL = " select count(*) count " +  
					" from SVC_DW_IMS_DAY " + 
					" where  trunc(nvl(service_term_DATE,sysdate)) >= trunc(sysdate)  " + 
					" AND trunc(service_start_DATE) <= trunc(sysdate) " +
					" and fsa = :fsa " ; 
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("fsa", serviceNum);
		
		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
		    public String mapRow(ResultSet rs, int rowNum) throws SQLException {
		    	String temp = new String();
		    	temp=(rs.getString("count"));
		        return temp;
		    }
		};
		
		List<String> resultList = new ArrayList<String>();
		
		try {
			logger.debug("checkPCDexistingServcie SQL:" + SQL);
			resultList = simpleJdbcTemplate.query(SQL, mapper,params);
		} catch (EmptyResultDataAccessException erdae) {
			logger.debug("EmptyResultDataAccessException");
			resultList = null;
		} catch (Exception e) {
			logger.debug("Exception caught in checkPCDexistingServcie:", e);
			throw new DAOException(e.getMessage(), e);
		}	
		return (resultList.size()>0 ? !"0".equals(resultList.get(0)):false);
		
	}

	public List<OrderImsSystemFindingDTO> sysFchecking() throws DAOException{
		logger.debug("sysFchecking");
		
		String SQL =    " select a.order_id, d.CUST_NO,C.SERBDYNO,C.FLOOR_NO, C.UNIT_NO,C.HI_LOT_NO,D.SERVICE_NUM,D.ID_DOC_TYPE,D.ID_DOC_NUM ,b.IS_NEW_CUST_IND "+
						" from bomweb_order_ims b, bomweb_order a,bomweb_cust_addr c,bomweb_customer d             "+
						" where a.order_id = b.order_id                                                            "+
						" and a.order_id = c.order_id                                                              "+
						" and a.order_id = d.order_id                                                              "+
						" and b.ims_order_type = 'DS'                                                              "+
						" and a.order_id like 'DD%'                                                                "+
						" and A.SIGN_OFF_DATE is not null                                                          "+
						" and a.order_status <> '06'          													   "+
						" and b.SYS_F is null                                                     				   "+
						" AND trunc(a.create_date) = trunc(sysdate)                                                ";   
		
		ParameterizedRowMapper<OrderImsSystemFindingDTO> mapper = new ParameterizedRowMapper<OrderImsSystemFindingDTO>() {

			public OrderImsSystemFindingDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				OrderImsSystemFindingDTO dto = new OrderImsSystemFindingDTO();

				dto.setOrderID(rs.getString("order_id"));
				dto.setBomCustNo(rs.getString("CUST_NO"));
				dto.setSerbdyno(rs.getString("SERBDYNO"));
				dto.setFloorNo(rs.getString("FLOOR_NO"));
				dto.setUnitNo(rs.getString("UNIT_NO"));
				dto.setHiLotNo(rs.getString("HI_LOT_NO"));
				dto.setIdDocType(rs.getString("ID_DOC_TYPE"));
				dto.setIdDocNum(rs.getString("ID_DOC_NUM"));
				dto.setServiceNum(rs.getString("SERVICE_NUM"));
				dto.setIsNewCustomer(rs.getString("IS_NEW_CUST_IND"));
//				dto.getAddressDTO().setSerbdyno(rs.getString("SERBDYNO"));
//				dto.getAddressDTO().setFloorNo(rs.getString("FLOOR_NO"));
//				dto.getAddressDTO().setUnitNo(rs.getString("UNIT_NO"));
//				dto.getAddressDTO().setHiLotNo(rs.getString("HI_LOT_NO"));
			
				return dto;
			}
		};
		
		try {
			logger.debug("sysFchecking @ OrderDAO: " + SQL);
			List<OrderImsSystemFindingDTO> orders = simpleJdbcTemplate.query(SQL, mapper);
			
			return orders;
		
		} catch (Exception e) {
			logger.error("Exception caught in sysFchecking()", e);

			throw new DAOException(e.getMessage(), e);
		}		
	}
	
	public int updateDSSysFinding(OrderImsSystemFindingDTO dto) throws DAOException{
		logger.info("updateDSSysFinding getSysF(): " + dto.getSysF());
		int row=0;
		
		String SQL = "	UPDATE bomweb_order_ims	" +
					 "  SET    SYS_F = ? 	    " +
					 "	WHERE order_id = ?	" ;
		
		try {
			row =  simpleJdbcTemplate.update(SQL,
									  dto.getSysF(),
									  dto.getOrderID()
									  );

		} catch (Exception e) {
			logger.error("Exception caught in updateDSSysFinding()", e);
			throw new DAOException(e.getMessage(), e);
		}
		
		return row;
	}

	public String[] integerListToIntArray(List<String> a){
		String[] rtn=null;
		if(a==null || a.isEmpty()) return new String[0];

		rtn = new String[a.size()];
		int i = 0;

		//for (Integer f : a) {
		//	rtn[i++] = (f != null ? f.intValue() : 0); 
		//}
		for (String f : a) {
		rtn[i++] = f;
		}

		return rtn;
	};

	public void getBackendOfferChannel(OrderImsUI order) throws SQLException{
	
		int gnretval;
		int gnerrcode;
		String gserrtext;
	
		List<SubscribedItemUI> subItem = null ;
		List<SubscribedChannelUI> subChannel = null;
	
		List<String> a = new ArrayList<String>();
		List<String> b = new ArrayList<String>();
	
		for (SubscribedItemUI i:order.getSubscribedItems()) {
			a.add(i.getId());
		}
		if(order.getSubscribedChannels()!=null){
			for (SubscribedChannelUI i:order.getSubscribedChannels()) {
				b.add(i.getChannelId());
			}
		}
		
		Connection conn = DataSourceUtils.getConnection(jdbcTemplate.getDataSource());
	
		OraArrayVarChar2 va = new OraArrayVarChar2(ORACLE_NUM_ARRAY, integerListToIntArray(a));
		OraArrayVarChar2 vb = new OraArrayVarChar2(ORACLE_NUM_ARRAY, integerListToIntArray(b));
	
		try {	
			SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
			.withSchemaName("ops$cnm")
			.withCatalogName("PKG_SB_IMS_LTS")
			.withProcedureName("backend_offer_change_by_item")
			.declareParameters(
					new SqlParameter("i_item_id_list", Types.ARRAY),
					new SqlParameter("i_channel_id_list", Types.ARRAY),
					new SqlParameter("i_sys_id", Types.VARCHAR),
					new SqlParameter("i_processVim", Types.VARCHAR),
					new SqlParameter("i_h264_IND", Types.VARCHAR),
					new SqlParameter("i_BANDWIDTH", Types.INTEGER),
					new SqlParameter("i_ADSL_18M_IND", Types.VARCHAR),
					new SqlParameter("i_technology", Types.VARCHAR),
					new SqlParameter("i_decoder_type", Types.VARCHAR),
					new SqlOutParameter("backend_offer_list_cursor", OracleTypes.CURSOR, new Mapper1()),
					new SqlOutParameter("backend_channel_list_cursor", OracleTypes.CURSOR, new Mapper2()),
					new SqlOutParameter("gnretval", Types.INTEGER), 
					new SqlOutParameter("gnerrcode", Types.INTEGER),
					new SqlOutParameter("gserrtext", Types.VARCHAR)
					);
		 
			logger.info("*******calling PKG_SB_IMS_LTS.backend_offer_change_by_item*******");
			logger.info("i_item_id_list :" +  a.toString());
			logger.info("i_channel_id_list :" +  b.toString());
			logger.info("i_sys_id : " + "RETAIL");
			logger.info("i_processVim : " + order.getProcessVim());
			logger.info("i_h264_IND : " + order.getInstallAddress().getAddrInventory().getH264Ind());
			logger.info("i_BANDWIDTH : " + order.getBandwidth());
			logger.info("i_ADSL_18M_IND : " + order.getInstallAddress().getAddrInventory().getAdsl18MInd());
			logger.info("i_technology : " + order.getInstallAddress().getAddrInventory().getTechnology());
			logger.info("i_decoder_type : " + order.getDecodeType());
	
			MapSqlParameterSource in = new MapSqlParameterSource();
			in.addValue("i_item_id_list",  va.getOracleArray(conn));
			in.addValue("i_channel_id_list",  vb.getOracleArray(conn));
			in.addValue("i_sys_id",  "RETAIL");
			in.addValue("i_processVim",  order.getProcessVim());
			in.addValue("i_h264_IND",  order.getInstallAddress().getAddrInventory().getH264Ind());
			in.addValue("i_BANDWIDTH",  Integer.parseInt(order.getBandwidth()));
			in.addValue("i_ADSL_18M_IND", order.getInstallAddress().getAddrInventory().getAdsl18MInd());
			in.addValue("i_technology", order.getInstallAddress().getAddrInventory().getTechnology());
			in.addValue("i_decoder_type", order.getDecodeType());
		
			Map out = jdbcCall.execute(in); 
		
			subItem = (List)out.get("backend_offer_list_cursor");
			subChannel = (List)out.get("backend_channel_list_cursor");
		
			if(subItem!=null){			
				SubscribedItemUI[] backendVas = new SubscribedItemUI[subItem.size()];
				for(int j=0;j<subItem.size();j++){
					backendVas[j] = subItem.get(j);
					logger.info(subItem.get(j).getOfferCode());
				}
				order.setSubscribedBackEndItems(backendVas);
			}
		
			if(subChannel!=null){			
				SubscribedChannelUI[] backendChannel = new SubscribedChannelUI[subChannel.size()];
				for(int j=0;j<subChannel.size();j++){
					backendChannel[j] = subChannel.get(j);
					logger.info(subChannel.get(j).getCampaignCd()+" "+subChannel.get(j).getPlanCd());
				}
				order.setSubscribedBackEndChannels(backendChannel);
			}
		
			gnretval = (Integer) out.get("gnretval");
			gnerrcode = (Integer) out.get("gnerrcode");
			gserrtext = (String) out.get("gserrtext");
		
			logger.info(gnretval);
			logger.info(gnerrcode);
			logger.info(gserrtext);
		
			logger.info("*******calling PKG_SB_IMS_LTS.backend_offer_change_by_item(end)*******");
		
		} catch (Exception e) {
			// TODO Auto-generated catch block 
			e.printStackTrace();
		}
	}
	
	public final class Mapper1 implements RowMapper {
	
		public Object mapRow(ResultSet rs1, int rowNum) throws SQLException {
			SubscribedItemUI subItem = new SubscribedItemUI();
			subItem.setOfferCode(rs1.getString("offer_cd"));
			subItem.setImsServiceType(rs1.getString("ims_service_type"));
			return subItem;
			
		}
	
	}
	
	public final class Mapper2 implements RowMapper {
	
		public Object mapRow(ResultSet rs2, int rowNum) throws SQLException {
			SubscribedChannelUI subChannel = new SubscribedChannelUI();
			subChannel.setCampaignCd(rs2.getString("campaign_cd"));
			subChannel.setPlanCd(rs2.getString("plan_cd"));
			return subChannel;
		}
	
	}

	public List<SubscribedItemDTO> getBackendSubscribedItem(String orderId) throws DAOException{
		logger.debug("getSubscribedItem");
		String SQL = "	SELECT order_id, offer_cd, ims_service_type	" +
		"	  FROM bomweb_subscribed_offer_ims	" +
		"	 WHERE order_id = ?	" +
		"	 AND create_by = 'BACKEND'" +
		"	 AND nvl(sb_offer_type,'X') not in ('NTVCH')";
		
		ParameterizedRowMapper<SubscribedItemDTO> mapper = new ParameterizedRowMapper<SubscribedItemDTO>() {
	
			public SubscribedItemDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				SubscribedItemDTO dto = new SubscribedItemDTO();
					
				dto.setOrderId(rs.getString("order_id"));
				dto.setOfferCode(rs.getString("offer_cd"));
				dto.setImsServiceType(rs.getString("ims_service_type"));
	
				return dto;
			}
		};
		
		try {
			logger.debug("getBackendSubscribedItem @ OrderDAO: " + SQL);
			List<SubscribedItemDTO> orders = simpleJdbcTemplate
					.query(SQL, mapper, orderId);
			
			return orders;
		
		} catch (Exception e) {
			logger.error("Exception caught in getBackendSubscribedItem()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public List<SubscribedChannelDTO> getBackendSubscribedChannel(String orderId) throws DAOException{
		logger.debug("getSubscribedItem");
		String SQL = "	SELECT order_id, vi_campaign, offer_cd, sb_offer_type	" +
		"	  FROM bomweb_subscribed_offer_ims	" +
		"	 WHERE order_id = ?	" +
		"	 AND create_by = 'BACKEND'" +
		"	 AND sb_offer_type in ('NTVCH')";
		
		ParameterizedRowMapper<SubscribedChannelDTO> mapper = new ParameterizedRowMapper<SubscribedChannelDTO>() {
	
			public SubscribedChannelDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				SubscribedChannelDTO dto = new SubscribedChannelDTO();
					
				dto.setOrderId(rs.getString("order_id"));
				dto.setCampaignCd(rs.getString("vi_campaign"));
				dto.setPlanCd(rs.getString("offer_cd"));
				dto.setSbOfferType(rs.getString("sb_offer_type"));
	
				return dto;
			}
		};
		
		try {
			logger.debug("getBackendSubscribedChannel @ OrderDAO: " + SQL);
			List<SubscribedChannelDTO> orders = simpleJdbcTemplate
					.query(SQL, mapper, orderId);
			
			return orders;
		
		} catch (Exception e) {
			logger.error("Exception caught in getBackendSubscribedChannel()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public List<SubscribedItemDTO> getSubscribedBackendItemAmd(String orderId, String amendSeq) throws DAOException{
		logger.debug("getSubscribedBackendItemAmd");
		String SQL = 
			"select order_id, offer_cd, ims_service_type	"+
			"from bomweb_sub_item_amd where order_id = ?                                              "+
			"and amend_seq = " +amendSeq +"                                                            "+
			"and io_ind in ('I',' ')                                                                 " +
			"and create_by in ('BACKEND')";
		
		ParameterizedRowMapper<SubscribedItemDTO> mapper = new ParameterizedRowMapper<SubscribedItemDTO>() {
	
			public SubscribedItemDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				SubscribedItemDTO dto = new SubscribedItemDTO();
					
				dto.setOrderId(rs.getString("order_id"));
				dto.setOfferCode(rs.getString("offer_cd"));
				dto.setImsServiceType(rs.getString("ims_service_type"));
	
				return dto;
			}
		};
		
		try {
			logger.debug("getSubscribedBackendItemAmd @ OrderDAO: " + SQL);
			List<SubscribedItemDTO> orders = simpleJdbcTemplate
					.query(SQL, mapper, orderId);
			
			return orders;
		
		} catch (EmptyResultDataAccessException erdae) {
			return new ArrayList<SubscribedItemDTO>();
	
		}catch (Exception e) {
			logger.error("Exception caught in getSubscribedBackendItemAmd()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public List<SubscribedChannelDTO> getSubscribedBackendChannelAmd(String orderId, String amendSeq) throws DAOException{
		logger.debug("getSubscribedBackendChannelAmd");
		String SQL = 
			"select order_id, campaign_cd, plan_cd, sb_offer_type	"+
			"from bomweb_sub_channel_amd                                                              "+
			"where order_id = ?                                                                       "+
			"and amend_seq = " +amendSeq +"                                                            "+
			"and io_ind in ('I',' ')                                                                 " +
			"and create_by in ('BACKEND')";
	
		
		ParameterizedRowMapper<SubscribedChannelDTO> mapper = new ParameterizedRowMapper<SubscribedChannelDTO>() {
	
			public SubscribedChannelDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				SubscribedChannelDTO dto = new SubscribedChannelDTO();
					
				dto.setOrderId(rs.getString("order_id"));
				dto.setCampaignCd(rs.getString("campaign_cd"));
				dto.setPlanCd(rs.getString("plan_cd"));
				dto.setSbOfferType(rs.getString("sb_offer_type"));
	
				return dto;
			}
		};
		
		try {
			logger.debug("getSubscribedBackendChannelAmd @ OrderDAO: " + SQL);
			List<SubscribedChannelDTO> orders = simpleJdbcTemplate
					.query(SQL, mapper, orderId);
			
			return orders;
		
		} catch (EmptyResultDataAccessException erdae) {
			return new ArrayList<SubscribedChannelDTO>();
	
		}catch (Exception e) {
			logger.error("Exception caught in getSubscribedBackendChannelAmd()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}

	public void insertBomWebOrderImsAmd(OrderImsDTO dto, String p_wq_batch_id) throws DAOException{
		logger.debug("insertBomWebOrderImsAmd");
		String SQL = "	INSERT INTO bomweb_order_ims_amd	" +
		"	            (order_id, login_id, decoder_type, adult_view_allow,	" +
		"	             target_comm_date, prepay_amt, moving_chrg, OT_INSTALL_CHRG ,waived_prepay,	" +
		"	             cash_fs_prepay, now_tv_form_type, fixed_line_no, is_cc_offer,	" +
		"	             lang_preference, process_cc_prepay, process_vim, process_vms,	" +
		"	             process_wifi, create_by, create_date, last_upd_by,	" +
		"	             last_upd_date, tv_credit, bandwidth, primary_acct_no,  INSTALL_INSTALMENT_AMT, INSTALL_INSTALMENT_MTH, " +
		"				 ot_chrg_type, APPLMTHD, SOURCE_CD, " +
		"				 CALL_DATE, POSITION_NO, FAX_SERIAL, APPLMTHD_desc, " +
		"				 INSTALL_OT_CODE, INSTALL_OT_DESC_EN, INSTALL_OT_DESC_ZH,INSTALL_INSTALMENT_CODE, " +
		"				 DS_TYPE, DS_LOCATION, COOLOFF ,WAIVE_CLOFF, QC_CALL_TIME, WAIVE_QC, ims_order_type, SYS_F, amend_seq, tv_price_ind," +
		"				 PRE_INST_IND, PRE_USE_IND, SERVICE_WAIVER " +
		"	            )	" +
		"	     VALUES (?, ?, ?, ?,	" +
		"	             ?, ?, ?, ?,?,	" +
		"	             ?, ?, ?, ?,	" +
		"	             ?, ?, ?, ?,	" +
		"	             ?, ?, sysdate, null,	" +
		"	             null, ?, ?, ?, ?, ?,	" +
		"				 ?, ?, ?,	" +
		"				 ?, ?, ?,?,	" +
		"				 ?, ?, ?,?, " +
		"				 ?, ?, ?,?, ?, ?, ?,?,(select amend_seq from bomweb_amend_category where order_id = ? and wq_batch_id = ? and nature = '362'),?," +
		"				 ?, ?, ? " +
		"	            )	" ;
		
		try{
			simpleJdbcTemplate.update(SQL,
					dto.getOrderId(), dto.getLoginId(), dto.getDecodeType(), dto.getAdultViewAllow(),
					dto.getTargetCommDate(), dto.getPrepayAmt(), dto.getMovingChrg(), dto.getOtInstallChrg(), dto.getWaivedPrepay(),
					dto.getCashFsPrepay(), dto.getNowTvFormType(), dto.getFixedLineNo(), dto.getIsCreditCardOffer(),
					dto.getLangPreference(), dto.getProcessCreditCard(), dto.getProcessVim(), dto.getProcessVms(),
					dto.getProcessWifi(), dto.getCreateBy(),
					dto.getTvCredit(), dto.getBandwidth(), dto.getPrimaryAcctNo(),dto.getInstallmentChrg(),dto.getInstallmentMonth(),
					dto.getOtChrgType(), dto.getAppMethod(), dto.getSourceCd(),
					dto.getCallDate(), dto.getPositionNo(), dto.getFAXno(), dto.getAppMethodDesc(),
					dto.getInstallOtCode(), dto.getInstallOTDesc_en(), dto.getInstallOTDesc_zh(),dto.getInstallmentCode(), 
					dto.getDsType(), dto.getDsLocation(), dto.getDsCoolOff(), dto.getDsWaiveCoolOff(), dto.getDsQcCallTime(), dto.getDsWaiveQC(), dto.getImsOrderType(),
					dto.getSysF(), dto.getOrderId(), p_wq_batch_id ,dto.getTvPriceInd(),
					dto.getPreInstallInd(), dto.getPreUseInd(), dto.getServiceWaiver()
					);
			
		}catch (Exception e) {
			logger.error("Exception caught in insertBomWebOrderImsAmd()", e);
			throw new DAOException(e.getMessage(), e);
		}

	}
	
	public List<OrderImsDTO> getBomWebOrderImsAmd(String orderId, String amendSeq) throws DAOException{
		logger.debug("getBomWebOrder");
		
		String SQL = "	SELECT order_id, login_id, decoder_type, adult_view_allow, target_comm_date,					"+
		"			       prepay_amt, moving_chrg, OT_INSTALL_CHRG ,waived_prepay, cash_fs_prepay,			"+
		"			       now_tv_form_type, fixed_line_no, is_cc_offer, lang_preference,			"+
		"			       process_cc_prepay, process_vim, process_vms, process_wifi, create_by,			"+
		"			       create_date, last_upd_by, last_upd_date, tv_credit, bandwidth,			"+
		"			       primary_acct_no,	INSTALL_INSTALMENT_AMT, INSTALL_INSTALMENT_MTH, 		" +
		"					ot_chrg_type, APPLMTHD, SOURCE_CD,	" +
		"					CALL_DATE, POSITION_NO, FAX_SERIAL,"+
		"					INSTALL_OT_CODE, INSTALL_OT_DESC_EN, INSTALL_OT_DESC_ZH,INSTALL_INSTALMENT_CODE," +
		"				   (select decode(count(*),0,'N','Y') chooseNowtvCoupon		"+
		"					from bomweb_subscribed_item bsi,w_item_dtl_ims widi,bomweb_order_ims boi 	"+
		"					where bsi.order_Id=a.order_id	"+
		"					and bsi.id=widi.item_id and widi.is_coupon_plan='Y' 	"+
		"					and boi.order_id=bsi.order_Id	"+
		"					and boi.tv_credit>0) chooseNowtvCoupon	"+
		"					,IMS_ORDER_TYPE,  " +
		"				 DS_TYPE, DS_LOCATION, COOLOFF ,WAIVE_CLOFF, QC_CALL_TIME, WAIVE_QC, SYS_F , tv_price_ind, " +
		"				 PRE_INST_IND, PRE_USE_IND " +
		"			  FROM bomweb_order_ims_amd a			"+
		"	 		  WHERE order_id = ?	" + 
		"			and amend_seq = " +amendSeq +"                                                             ";

		
		ParameterizedRowMapper<OrderImsDTO> mapper = new ParameterizedRowMapper<OrderImsDTO>() {

			public OrderImsDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				OrderImsDTO dto = new OrderImsDTO();
					
				dto.setOrderId(rs.getString("order_id"));
				dto.setLoginId(rs.getString("login_id"));
				dto.setDecodeType(rs.getString("decoder_type"));
				dto.setAdultViewAllow(rs.getString("adult_view_allow"));
				dto.setTargetCommDate(rs.getDate("target_comm_date"));
				dto.setPrepayAmt(rs.getString("prepay_amt"));
				dto.setMovingChrg(rs.getString("moving_chrg"));
				dto.setOtInstallChrg(rs.getString("OT_INSTALL_CHRG"));
				dto.setWaivedPrepay(rs.getString("waived_prepay"));
				dto.setCashFsPrepay(rs.getString("cash_fs_prepay"));
				dto.setNowTvFormType(rs.getString("now_tv_form_type"));
				dto.setFixedLineNo(rs.getString("fixed_line_no"));
				dto.setIsCreditCardOffer(rs.getString("is_cc_offer"));
				dto.setLangPreference(rs.getString("lang_preference"));
				dto.setProcessCreditCard(rs.getString("process_cc_prepay"));
				dto.setProcessVim(rs.getString("process_vim"));
				dto.setProcessVms(rs.getString("process_vms"));
				dto.setProcessWifi(rs.getString("process_wifi"));				
				dto.setTvCredit(rs.getString("tv_credit"));
				dto.setBandwidth(rs.getString("bandwidth"));
				dto.setPrimaryAcctNo(rs.getString("primary_acct_no"));
				dto.setInstallmentChrg(rs.getString("INSTALL_INSTALMENT_AMT"));
				dto.setInstallmentMonth(rs.getString("INSTALL_INSTALMENT_MTH"));
				dto.setChooseNowtvCoupon(rs.getString("chooseNowtvCoupon"));
				dto.setImsOrderType(rs.getString("IMS_ORDER_TYPE"));
				dto.setOtChrgType(rs.getString("OT_CHRG_TYPE"));
				dto.setAppMethod(rs.getString("APPLMTHD"));
				dto.setSourceCd(rs.getString("SOURCE_CD"));
				dto.setPositionNo(rs.getString("POSITION_NO"));
				dto.setCallDate(rs.getTimestamp("CALL_DATE"));
				dto.setFAXno(rs.getString("FAX_SERIAL"));
				dto.setInstallOtCode(rs.getString("install_ot_code"));
		    	dto.setInstallOTDesc_en(rs.getString("install_ot_desc_en"));
		    	dto.setInstallOTDesc_zh(rs.getString("install_ot_desc_zh"));
		    	dto.setInstallmentCode(rs.getString("INSTALL_INSTALMENT_CODE"));
		    	dto.setDsType(rs.getString("DS_TYPE"));
		    	dto.setDsLocation(rs.getString("DS_LOCATION"));
		    	dto.setDsCoolOff(rs.getString("COOLOFF"));
		    	dto.setDsWaiveCoolOff(rs.getString("WAIVE_CLOFF"));
		    	dto.setDsQcCallTime(rs.getString("QC_CALL_TIME"));
		    	dto.setDsWaiveQC(rs.getString("WAIVE_QC"));
		    	dto.setSysF(rs.getString("SYS_F"));
		    	dto.setTvPriceInd(rs.getString("tv_price_ind"));
		    	dto.setPreInstallInd(rs.getString("PRE_INST_IND"));
		    	dto.setPreUseInd(rs.getString("PRE_USE_IND"));
				return dto;
			}
		};
		
		try {
			logger.debug("getBomWebOrder @ OrderDAO: " + SQL);
			List<OrderImsDTO> orders = simpleJdbcTemplate
					.query(SQL, mapper, orderId);
			
			return orders;
		
		} catch (Exception e) {
			logger.error("Exception caught in getBomWebOrder()", e);

			throw new DAOException(e.getMessage(), e);
		}

	}
	
		public int updatePromoGiftQuota(String itemId) throws DAOException {
		
		logger.debug("updatePromoGiftQuota @ OrderDAO");
		
		String sql = "UPDATE w_item_dtl_tv SET " +
				" GIFT_QTY = GIFT_QTY -1 , " +
				" LAST_UPD_DATE = SYSDATE, " +
				" LAST_UPD_BY = 'SBIMS' " +
				" WHERE item_id = :itemId ";
		
		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("itemId", itemId);
			return simpleJdbcTemplate.update(sql.toString(), params);
			
		} catch (Exception e) {
			logger.error("Exception caught in updatePromoGiftQuota(): ", e);
			throw new DAOException(e.getMessage(), e);
		}
		
		}
		
		
		public String isDs5DummyStaff(String orgStaffId) throws DAOException {
			logger.debug(" isDs5DummyStaff @ OrderDAO");
			String isDs5DummyStaff = null;
			
			String SQL = "	select decode(count(*), '0', 'N', 'Y') isDs5DummyStaff " +
			"			from bomweb_sales_profile bsp, BOMWEB_SALES_ASSIGNMENT bsa " +
			"			where bsa.STAFF_ID = bsp.STAFF_ID " +
			"			and trunc(sysdate) BETWEEN bsp.START_DATE AND NVL( bsp.END_DATE,sysdate) " +
			"			and trunc(sysdate) BETWEEN bsa.START_DATE AND NVL( bsa.END_DATE,sysdate) " +
			"			and bsa.channel_cd = 'DS5'" +
			"			and bsp.staff_name like '%DUMMY%' " +
			"			and bsp.org_staff_id = ? ";
			
			
			try{
			
				isDs5DummyStaff = simpleJdbcTemplate.queryForObject(SQL, String.class, orgStaffId);
			
			}catch(Exception e) {
				logger.error("Exception caught in isDs5DummyStaff()", e);
				throw new DAOException(e.getMessage(), e);
			}
			
			return isDs5DummyStaff;
		}
		
		
		public String isDsMobileAgent(String teamCd, String mobileNo) throws DAOException {
			logger.debug(" isDsMobileAgent @ OrderDAO");
			String isDsMobileAgent = null;
			
			String SQL = " select decode(count(*), '0', 'N', 'Y') isDsMobileAgent" +
						 " from  bomweb_agent_mobile_chk where " +
						 " team_cd = ? and mobile_num = ?";
			
			
			try{
			
				isDsMobileAgent = simpleJdbcTemplate.queryForObject(SQL, String.class, teamCd, mobileNo);
			
			}catch(Exception e) {
				logger.error("Exception caught in isDsMobileAgent()", e);
				throw new DAOException(e.getMessage(), e);
			}
			
			return isDsMobileAgent;
		}
		
		public void insertBomwebOrderL2Job(BomwebOrderL2JobDTO dto) throws DAOException{
			logger.debug("insertBomwebOrderL2Job");
			String SQL = "	INSERT INTO bomweb_order_l2_job	" +
			"	            (ORDER_ID,DTL_ID,DTL_SEQ,PRODUCT_ID,L2_CD,FT_IND,L2_OTY,ACT_IND,LAST_UPD_DATE,LAST_UPD_BY	" +
			"	            )	" +
			"	     VALUES (?, ?, ?, ?, ?,	" +
			"	             ?, ?, ?,	" +
			"	             sysdate, ?  " +
			"	            )	";
			
			try{
				simpleJdbcTemplate.update(SQL,
						dto.getOrderId(), "1",dto.getDtlSeq(),dto.getProductId(),dto.getL2Cd(),dto.getFtInd(),
						dto.getL2Qty(),dto.getActInd(),dto.getLastUpdBy()
						);
			}catch (Exception e) {
				logger.error("Exception caught in insertBomwebOrderL2Job()", e);
				throw new DAOException(e.getMessage(), e);
			}			
		}

		
		public final class Mapper3 implements RowMapper {
			
			public Object mapRow(ResultSet rs3, int rowNum) throws SQLException {
				SubscribedItemUI subscribedItem = new SubscribedItemUI();
				subscribedItem.setId(rs3.getString("ID"));
				subscribedItem.setType(rs3.getString("Type"));
				subscribedItem.setBasketId(rs3.getString("basket_id"));
				subscribedItem.setCampaignCd(rs3.getString("campaign_cd"));			
				subscribedItem.setPackCd(rs3.getString("pack_cd"));
				subscribedItem.setTopUpCd(rs3.getString("top_up"));

				return subscribedItem;
			}
		
		}

		public class CursorMapper implements RowMapper {
		    
		    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		    	Map<String, String> map = new HashMap<String, String>();
		    	for(int i =1;i<=rs.getMetaData().getColumnCount();i++){
		    		map.put(rs.getMetaData().getColumnName(i), rs.getString(i));
		    	}
		        return map;
		    }
	   }

		private void setAttributes(Map<String, String> m, Object pObject){
			if (m == null) return;
			
			for (Map.Entry<String, String> entry : m.entrySet()) {
			    try {
					BeanUtils.setProperty(pObject, entry.getKey(), entry.getValue()); 
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		
		public void addBackendOffers(String i_order_id) throws  SQLException{
			logger.info("addBackendOffers(NTV) is called:"+i_order_id);
			
			Connection conn = jdbcTemplate.getDataSource().getConnection();
			
			try {	
				SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
				.withSchemaName("ops$cnm")
				.withCatalogName("pkg_sb_ims_ntv_baskets")
				.withProcedureName("insert_ntv_backend_offers")
				.declareParameters(
						new SqlParameter("i_order_id", Types.VARCHAR),						
						new SqlOutParameter("gnretval", Types.INTEGER), 
						new SqlOutParameter("gnerrcode", Types.INTEGER),
						new SqlOutParameter("gserrtext", Types.VARCHAR)
						);	
				logger.info("*******calling pkg_sb_ims_ntv_baskets.insert_ntv_backend_offers*******i_order_id :" +  i_order_id);			
				
				MapSqlParameterSource in = new MapSqlParameterSource();
				in.addValue("i_order_id",  i_order_id);					
			
				Map out = jdbcCall.execute(in); 	
				logger.info("pkg_sb_ims_ntv_baskets out:"+gson.toJson(out));			
			
				logger.info("*******calling pkg_sb_ims_ntv_baskets.insert_ntv_backend_offers(end)*******");
			
			} catch (Exception e) {
				logger.error("catch Exception addBackendOffers:"+i_order_id,e);
				e.printStackTrace();
			} finally{
				if(conn!=null)	conn.close(); 
			} 		
		}
		
	public void addBackendOffersDtl(OrderImsUI order) throws  SQLException{			
			logger.info("addBackendOffersDtl(NTV) is called:"+order.getOrderId());
			
			SubscribedItemUI[] subscribedItems = null;
			
			String i_technology = order.getInstallAddress().getAddrInventory().getTechnology();
			String i_is_h264= order.getInstallAddress().getAddrInventory().getH264Ind();
			String i_has_adsl8 = order.getInstallAddress().getHasADSL8();
			String i_has_adsl18 = order.getInstallAddress().getHasADSL18();
			String i_is_support_hd = order.getInstallAddress().getNtvCoverage().contains("HD")?"Y":"N";			
			String i_is_1l2b = "N";
			String i_is_sd = "N";
			String i_is_hd = "N";
			String i_is_shd = "N";
			String i_is_nowone = "N";
			String i_is_nowone_rental = "N";
			
			if(order.getSubscribedItems() != null){
				for(SubscribedItemUI dto :order.getSubscribedItems()){
					if(dto.getType().equalsIgnoreCase("NTV_SHD")){
						i_is_shd = "Y";					
					}else if(dto.getType().equalsIgnoreCase("NTV_HD")){
						i_is_hd = "Y";
					}else if (dto.getType().equalsIgnoreCase("NTV_SD")) {
						i_is_sd = "Y";
					}else if (dto.getType().equalsIgnoreCase("AIO_SUBOWN")) {
						i_is_nowone = "Y";
					}else if (dto.getType().equalsIgnoreCase("AIO_RENTAL")) {
						i_is_nowone = "Y";
						i_is_nowone_rental = "Y";
					}else if(dto.getType().equalsIgnoreCase("NTV_BOX")){
						i_is_1l2b="Y";
					}
				}
			}
			Connection conn = DataSourceUtils.getConnection(jdbcTemplate.getDataSource());
			
			MapSqlParameterSource in = new MapSqlParameterSource();
			in.addValue("i_order_id",  order.getOrderId());
			in.addValue("i_technology",  i_technology);
			in.addValue("i_is_h264",  i_is_h264);
			in.addValue("i_has_adsl8",  i_has_adsl8);
			in.addValue("i_has_adsl18",  i_has_adsl18);
			in.addValue("i_is_support_hd",  i_is_support_hd);
			in.addValue("i_is_1l2b",  i_is_1l2b);
			in.addValue("i_is_sd",  i_is_sd);
			in.addValue("i_is_hd",  i_is_hd);
			in.addValue("i_is_shd",  i_is_shd);
			in.addValue("i_is_nowone",  i_is_nowone);
			in.addValue("i_is_nowone_rental",  i_is_nowone_rental);
			
			try {	
				SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
				.withSchemaName("ops$cnm")
				.withCatalogName("pkg_sb_ims_ntv_baskets")
				.withProcedureName("insert_ntv_backend_offers_dtl")
				.declareParameters(
						new SqlParameter("i_order_id", Types.VARCHAR),
						new SqlParameter("i_technology", Types.VARCHAR),
						new SqlParameter("i_is_h264", Types.VARCHAR),
						new SqlParameter("i_has_adsl8", Types.VARCHAR),
						new SqlParameter("i_has_adsl18", Types.VARCHAR),
						new SqlParameter("i_is_support_hd", Types.VARCHAR),
						new SqlParameter("i_is_1l2b", Types.VARCHAR),
						new SqlParameter("i_is_sd", Types.VARCHAR),
						new SqlParameter("i_is_hd", Types.VARCHAR),
						new SqlParameter("i_is_shd", Types.VARCHAR),
						new SqlParameter("i_is_nowone", Types.VARCHAR),
						new SqlParameter("i_is_nowone_rental", Types.VARCHAR),
						new SqlOutParameter("o_subscribed_item_list_cur", OracleTypes.CURSOR, new CursorMapper()),
						new SqlOutParameter("o_decoder_type", Types.VARCHAR),
						new SqlOutParameter("gnretval", Types.INTEGER), 
						new SqlOutParameter("gnerrcode", Types.INTEGER),
						new SqlOutParameter("gserrtext", Types.VARCHAR)
						);

				logger.info("insert_ntv_backend_offers_dtl in:"+gson.toJson(in));	
				Map out = jdbcCall.execute(in); 
				logger.info("insert_ntv_backend_offers_dtl out:"+gson.toJson(out));	
				
				List<Map<String, String>> subitems = (List)out.get("o_subscribed_item_list_cur");
				String decoderType = (String) out.get("o_decoder_type");
				
				if (subitems!=null && order.getSubscribedItems() != null) {
					subscribedItems = new SubscribedItemUI[subitems.size()+order.getSubscribedItems().length];
					for(int i=0;i<order.getSubscribedItems().length;i++)
						subscribedItems[i]=order.getSubscribedItems()[i];

					logger.info("subitems:"+gson.toJson(subitems));
					for(int j=order.getSubscribedItems().length;j<subscribedItems.length;j++){
						SubscribedItemUI s = new SubscribedItemUI();
						setAttributes(subitems.get(j - order.getSubscribedItems().length), s);
						subscribedItems[j] = s;
					}
					logger.info("subscribedItems:"+gson.toJson(subscribedItems));
					
					order.setSubscribedItems(subscribedItems);
				}
				
				if (i_is_nowone.equals("Y")) {
					if (order.getSubscribedItems() != null) {
						for (int i = 0; i < order.getSubscribedItems().length; i++) {
							if ("AIO_SUBOWN".equals(order.getSubscribedItems()[i].getType()) ||
									"AIO_RENTAL".equals(order.getSubscribedItems()[i].getType())) {
								order.setDecodeType(order.getSubscribedItems()[i].getType());
							}
						}
					}
				} else {
					if(StringUtils.isNotBlank(decoderType))
						order.setDecodeType(decoderType);
				}
				
				
			} catch (Exception e) {
				logger.error("catch Exception insert_ntv_backend_offers_dtl:"+order.getOrderId()+" in:"+gson.toJson(in),e);
				e.printStackTrace();
			} 					
		}
	
	public void insertNowRetAmendTvOnly(OrderImsUI order) throws DAOException{
	
		try {	
			SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
			.withSchemaName("ops$cnm")
			.withCatalogName("pkg_sb_ims_ntv_baskets")
			.withProcedureName("insert_now_ret_amend_tv_only")
			.declareParameters(
					new SqlParameter("i_TXN_ID", Types.VARCHAR),
					new SqlParameter("i_amend_seq", Types.VARCHAR),
					new SqlParameter("i_order_id", Types.VARCHAR),
					new SqlParameter("i_CREATE_BY", Types.VARCHAR),
					new SqlOutParameter("gnretval", Types.INTEGER), 
					new SqlOutParameter("gnerrcode", Types.INTEGER),
					new SqlOutParameter("gserrtext", Types.VARCHAR)
					);
	
			MapSqlParameterSource in = new MapSqlParameterSource();
			in.addValue("i_txn_id",  order.getCpqTxnId());	
			in.addValue("i_order_id",  order.getOrderId());
			in.addValue("i_amend_seq",  order.getAmendSeq());		
			in.addValue("i_CREATE_BY",  order.getCreateByUser().getUsername());	
			logger.info("insertNowRetAmendTvOnly out:"+gson.toJson(in));	
			Map out = jdbcCall.execute(in); 
			logger.info("insertNowRetAmendTvOnly out:"+gson.toJson(out));								
			
		} catch (Exception e) {
			// TODO Auto-generated catch block 
			e.printStackTrace();
		} 
	}
	
	public String isNowRetAmendTvOnly(OrderImsUI order) throws DAOException{
	
		String result = "N";
		try {	
			String lastTimeSuccessAmendSeq = "null";
			try {	
				if(order.getLastTimeSuccessAmendSeq()!=null&&
						(!"null".equals(order.getLastTimeSuccessAmendSeq())&&Integer.parseInt(order.getLastTimeSuccessAmendSeq())>0)){
					lastTimeSuccessAmendSeq=order.getLastTimeSuccessAmendSeq();
				} 
			}catch (Exception e) {
				// TODO Auto-generated catch block 
				e.printStackTrace();
				lastTimeSuccessAmendSeq = "null";
			}
			SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
			.withSchemaName("ops$cnm")
			.withCatalogName("pkg_sb_ims_amend")
			.withProcedureName("is_now_ret_only_amend_tv")
			.declareParameters(
					new SqlParameter("i_TXN_ID", Types.VARCHAR),
					new SqlParameter("i_last_time_success_amend_seq", Types.VARCHAR),
					new SqlOutParameter("o_is_only_amend_tv", Types.VARCHAR),
					new SqlOutParameter("gnretval", Types.INTEGER), 
					new SqlOutParameter("gnerrcode", Types.INTEGER),
					new SqlOutParameter("gserrtext", Types.VARCHAR)
					);
	
			MapSqlParameterSource in = new MapSqlParameterSource();
			in.addValue("i_txn_id",  order.getCpqTxnId());	
			in.addValue("i_last_time_success_amend_seq",  lastTimeSuccessAmendSeq);
			logger.info(order.getOrderId()+" isNowRetAmendTvOnly in:"+gson.toJson(in));	
			Map out = jdbcCall.execute(in); 
			logger.info(order.getOrderId()+" isNowRetAmendTvOnly out:"+gson.toJson(out));	
			result = (String) out.get("o_is_only_amend_tv");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block 
			e.printStackTrace();
		}
		return result; 
	}
		
		public OrderImsUI getCPQNTVDetail(String TXN_ID,OrderImsUI order) throws SQLException{		
			List<SubscribedItemUI> subItem = null ;
			SubscribedItemUI[] subscribedItems = null;			
			Connection conn = DataSourceUtils.getConnection(jdbcTemplate.getDataSource());
		
			try {	
				SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
				.withSchemaName("ops$cnm")
				.withCatalogName("pkg_sb_ims_ntv_baskets")
				.withProcedureName("convert_cpq_baskets")
				.declareParameters(
						new SqlParameter("i_TXN_ID", Types.VARCHAR),
						new SqlParameter("i_is_ret_flow", Types.VARCHAR),
						
						new SqlOutParameter("o_subscribed_item_list_cur", OracleTypes.CURSOR, new CursorMapper()),
						new SqlOutParameter("o_arpu", Types.INTEGER),
						new SqlOutParameter("o_deposit", Types.INTEGER),
						new SqlOutParameter("o_is_cash_only", Types.VARCHAR),
						new SqlOutParameter("o_is_cc_only", Types.VARCHAR),
						new SqlOutParameter("o_is_adult_channel", Types.VARCHAR),
						new SqlOutParameter("o_contract_period", Types.VARCHAR),
						new SqlOutParameter("o_is_goods_delivery", Types.VARCHAR),
						
						new SqlOutParameter("gnretval", Types.INTEGER),
						new SqlOutParameter("gnerrcode", Types.INTEGER),
						new SqlOutParameter("gserrtext", Types.VARCHAR)
						);
			 			
		
				MapSqlParameterSource in = new MapSqlParameterSource();
				in.addValue("i_TXN_ID",  TXN_ID);
				if(order.isOrderTypeNowRet()){
					in.addValue("i_is_ret_flow",  "Y");					
				}else{
					in.addValue("i_is_ret_flow",  "N");					
				}			

				logger.info("convert_cpq_baskets in:"+gson.toJson(in));
				Map out = jdbcCall.execute(in); 
				logger.info("convert_cpq_baskets out:"+gson.toJson(out));
			
				List<Map<String, String>> subitems = (List)out.get("o_subscribed_item_list_cur");
				
				if (subitems!=null) {
					subscribedItems = new SubscribedItemUI[subitems.size()];
					for(int j=0;j<subitems.size();j++){
						SubscribedItemUI s = new SubscribedItemUI();
						setAttributes(subitems.get(j), s);
						subscribedItems[j] = s;
					}
				}					
			
				order.setSubscribedItems(subscribedItems);
				order.setARPU_in(out.get("o_arpu").toString());
				order.setDepositAmt(out.get("o_deposit").toString());
				order.setIsCashOnly((String) out.get("o_is_cash_only"));
				order.setIsCreditCardOnly((String) out.get("o_is_cc_only"));
				order.setIsAdultChannelSelected((String) out.get("o_is_adult_channel"));
				order.setWarrPeriod(out.get("o_contract_period").toString());
				order.setIsIncludeGoodsDelivery((String) out.get("o_is_goods_delivery"));
				
				if(order.isOrderTypeNowRet()){
					List<BomwebSubscribedOfferImsDTO> offerList=this.getBomwebSubOfferIms(TXN_ID);
					logger.info("db offerList(not using):"+gson.toJson(offerList));					
				}			
								
			} catch (Exception e) {
				logger.error("getCPQNTVDetail error TXN_ID:"+TXN_ID+"  order:"+gson.toJson(order),e);
			} 			
			
			return order;
		}
		

		public List<BomwebSubscribedOfferImsDTO> getBomwebSubOfferIms(String txnId) throws DAOException {			
			List<BomwebSubscribedOfferImsDTO> result = null;
//			logger.info("getBomwebSubOfferIms: "+txnId);
			String SQL = "select CAMPAIGN_CD , PACK_CD , VAS_CD, IO_IND  from W_CPQ_TXN where txn_id = ? and io_ind in ('O',' ') ";			
			ParameterizedRowMapper<BomwebSubscribedOfferImsDTO> mapper = new ParameterizedRowMapper<BomwebSubscribedOfferImsDTO>() {				
				public BomwebSubscribedOfferImsDTO mapRow(ResultSet rs, int rowNum)throws SQLException {
					BomwebSubscribedOfferImsDTO dto = new BomwebSubscribedOfferImsDTO();						
					dto.setViCampaign(rs.getString("CAMPAIGN_CD"));
					dto.setOfferCd(rs.getString("PACK_CD"));
					if(rs.getString("VAS_CD")!=null && !"".equals(rs.getString("VAS_CD"))){
						dto.setOfferCd(rs.getString("VAS_CD"));	
					}
					dto.setIoInd(rs.getString("IO_IND"));
					return dto;
				}
			};

			try {
				result =  simpleJdbcTemplate.query(SQL, mapper, txnId);
			} catch (EmptyResultDataAccessException erdae) {
				result = null;
			} catch (Exception e) {
				logger.error("Exception caught in getBomwebSubOfferIms()", e);
				throw new DAOException(e.getMessage(), e);
			}
			logger.info("getBomwebSubOfferIms txnId:"+txnId+" result:"+gson.toJson(result));
			return result;
		}
		

		public List<BomwebSubscribedOfferImsDTO> getBomwebSubOfferSophieNowRet(String txnId) throws DAOException {			
			List<BomwebSubscribedOfferImsDTO> result = null;
			String SQL = "select CAMPAIGN_CD , PACK_CD , IO_IND  from W_CPQ_TXN where txn_id = ? and io_ind in ('O',' ') and VAS_CD is null ";			
			ParameterizedRowMapper<BomwebSubscribedOfferImsDTO> mapper = new ParameterizedRowMapper<BomwebSubscribedOfferImsDTO>() {				
				public BomwebSubscribedOfferImsDTO mapRow(ResultSet rs, int rowNum)throws SQLException {
					BomwebSubscribedOfferImsDTO dto = new BomwebSubscribedOfferImsDTO();						
					dto.setViCampaign(rs.getString("CAMPAIGN_CD"));
					dto.setOfferCd(rs.getString("PACK_CD"));
					dto.setIoInd(rs.getString("IO_IND"));
					return dto;
				}
			};

			try {
				result =  simpleJdbcTemplate.query(SQL, mapper, txnId);
			} catch (EmptyResultDataAccessException erdae) {
				result = null;
			} catch (Exception e) {
				logger.error("Exception caught in getBomwebSubOfferIms()", e);
				throw new DAOException(e.getMessage(), e);
			}
			logger.info("getBomwebSubOfferSophie txnId:"+txnId+" result:"+gson.toJson(result));
			return result;
		}
		

		public List<BomwebSubscribedOfferImsDTO> getBomwebSubOfferImsAmd(String txnId, String amendSeq) throws DAOException {			
			List<BomwebSubscribedOfferImsDTO> result = null;
			logger.info("getBomwebSubOfferIms: "+txnId+" amendSeq:"+amendSeq);
			String SQL = "select CAMPAIGN_CD , PACK_CD , VAS_CD, IO_IND  from w_cpq_txn_amend where txn_id = ? and io_ind in ('O',' ') and amend_seq = ?";			
			ParameterizedRowMapper<BomwebSubscribedOfferImsDTO> mapper = new ParameterizedRowMapper<BomwebSubscribedOfferImsDTO>() {				
				public BomwebSubscribedOfferImsDTO mapRow(ResultSet rs, int rowNum)throws SQLException {
					BomwebSubscribedOfferImsDTO dto = new BomwebSubscribedOfferImsDTO();						
					dto.setViCampaign(rs.getString("CAMPAIGN_CD"));
					dto.setOfferCd(rs.getString("PACK_CD"));
					if(rs.getString("VAS_CD")!=null && !"".equals(rs.getString("VAS_CD"))){
						dto.setOfferCd(rs.getString("VAS_CD"));	
					}
					dto.setIoInd(rs.getString("IO_IND"));
					return dto;
				}
			};

			try {
				result =  simpleJdbcTemplate.query(SQL, mapper, txnId, amendSeq);
			} catch (EmptyResultDataAccessException erdae) {
				result = null;
			} catch (Exception e) {
				logger.error("Exception caught in getBomwebSubOfferIms()", e);
				throw new DAOException(e.getMessage(), e);
			}
			logger.info("getBomwebSubOfferIms result:"+gson.toJson(result));
			return result;
		}
		
		public OrderImsUI getCPQNTVDetailAmd(String TXN_ID,OrderImsUI order, String i_amend_seq) throws SQLException{		
			SubscribedItemUI[] subscribedItems = null;
			
			Connection conn = DataSourceUtils.getConnection(jdbcTemplate.getDataSource());
		
			try {	
				SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
				.withSchemaName("ops$cnm")
				.withCatalogName("pkg_sb_ims_ntv_baskets")
				.withProcedureName("convert_cpq_baskets_amd_cp13")
				.declareParameters(
						new SqlParameter("i_TXN_ID", Types.VARCHAR),
						new SqlParameter("i_amend_seq", Types.VARCHAR),
						new SqlParameter("i_is_ret_flow", Types.VARCHAR),
						
						new SqlOutParameter("o_subscribed_item_list_cur", OracleTypes.CURSOR, new CursorMapper()),
						new SqlOutParameter("o_arpu", Types.INTEGER),
						new SqlOutParameter("o_deposit", Types.INTEGER),
						new SqlOutParameter("o_is_cash_only", Types.VARCHAR),
						new SqlOutParameter("o_is_cc_only", Types.VARCHAR),
						new SqlOutParameter("o_is_adult_channel", Types.VARCHAR),
						new SqlOutParameter("o_contract_period", Types.VARCHAR),
						new SqlOutParameter("o_is_goods_delivery", Types.VARCHAR),
						
						new SqlOutParameter("gnretval", Types.INTEGER),
						new SqlOutParameter("gnerrcode", Types.INTEGER),
						new SqlOutParameter("gserrtext", Types.VARCHAR)
						);
			 
				logger.info("Order ID: " + order.getOrderId() + " i_TXN_ID: " + TXN_ID + 
						" i_amend_seq: " + i_amend_seq + " i_is_ret_flow: " + (order.isOrderTypeNowRet()?"Y":"N"));

				MapSqlParameterSource in = new MapSqlParameterSource();
				in.addValue("i_TXN_ID",  TXN_ID);
				in.addValue("i_amend_seq",  i_amend_seq);
				if(order.isOrderTypeNowRet()){
					in.addValue("i_is_ret_flow",  "Y");					
				}else{
					in.addValue("i_is_ret_flow",  "N");					
				}
				
				logger.info("ntv order amend convert_cpq_baskets_amd_cp13 in:"+gson.toJson(in));	
				Map out = jdbcCall.execute(in); 
				logger.info("ntv order amend convert_cpq_baskets_amd_cp13 out:"+gson.toJson(out));	
			
				//subItem = (List)out.get("o_subscribed_item_list_cur");

				List<Map<String, String>> subitems = (List)out.get("o_subscribed_item_list_cur");
				
				if (subitems!=null) {
					subscribedItems = new SubscribedItemUI[subitems.size()];
					for(int j=0;j<subitems.size();j++){
						SubscribedItemUI s = new SubscribedItemUI();
						setAttributes(subitems.get(j), s);
						subscribedItems[j] = s;
					}
				}					
			
				order.setSubscribedItems(subscribedItems);
				order.setARPU_in(out.get("o_arpu").toString());
				order.setDepositAmt(out.get("o_deposit").toString());
				order.setIsCashOnly((String) out.get("o_is_cash_only"));
				order.setIsCreditCardOnly((String) out.get("o_is_cc_only"));
				order.setIsAdultChannelSelected((String) out.get("o_is_adult_channel"));
				order.setWarrPeriod(out.get("o_contract_period").toString());
				order.setIsIncludeGoodsDelivery((String) out.get("o_is_goods_delivery"));

				if(order.isOrderTypeNowRet()){
					List<BomwebSubscribedOfferImsDTO> offerList=this.getBomwebSubOfferImsAmd(TXN_ID,i_amend_seq);
					order.setSubscribedOffersIms(offerList.toArray(new BomwebSubscribedOfferImsDTO[offerList.size()]));
					logger.info("getBomwebSubOfferImsAmd: " + gson.toJson(order.getSubscribedOffersIms()));
				}
				
			} catch (Exception e) {
				logger.error("error in ntv order amend(), TXN_ID:"+TXN_ID+"  order:"+gson.toJson(order), e);
			}
			
			return order;
		}
		
		public String getSystemFinding(OrderImsUI order, String staffId, String dob) throws SQLException {
			int gnretval;
			int gnerrcode;
			String gserrtext;
			String sys_f = "";
			
			Connection conn = DataSourceUtils.getConnection(jdbcTemplate.getDataSource());
			try {	
				
				SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
				.withSchemaName("ops$cnm")
				.withCatalogName("pkg_sb_ims_qc")
				.withProcedureName("get_system_finding")
				.withoutProcedureColumnMetaDataAccess()
				.declareParameters(
						new SqlParameter("i_staff_id", Types.VARCHAR),
						new SqlParameter("i_dob", Types.VARCHAR),
						new SqlParameter("i_third_party", Types.VARCHAR),
						new SqlParameter("i_txn_id", Types.VARCHAR),
						new SqlOutParameter("o_system_finding", Types.VARCHAR), 
		
						new SqlOutParameter("gnretval", Types.INTEGER), 
						new SqlOutParameter("gnerrcode", Types.INTEGER),
						new SqlOutParameter("gserrtext", Types.VARCHAR)
						);
				logger.info("*******calling pkg_sb_ims_qcget_system_finding(start)*******");

				MapSqlParameterSource in = new MapSqlParameterSource();
				in.addValue("i_staff_id", staffId);
				in.addValue("i_dob", dob);
				in.addValue("i_third_party", order.getCustomer().getAccount().getPayment().getThirdPartyInd());
				in.addValue("i_txn_id", order.getCpqTxnId());
				
				Map out = jdbcCall.execute(in); 
			
				sys_f = (String)out.get("o_system_finding");
			
//				if (!StringUtils.isEmpty(sys_f)) {
//					order.setSysF(sys_f);
//					order.setMust_QC_Ind("Y");
//				} else {
//					order.setMust_QC_Ind("N");
//				}
				
				gnretval = (Integer) out.get("gnretval");
				gnerrcode = (Integer) out.get("gnerrcode");
				gserrtext = (String) out.get("gserrtext");
			
				logger.info("gnretval= " + gnretval + ", gnerrcode=" + gnerrcode + ", gserrtext=" + gserrtext);
				logger.info("*******calling pkg_sb_ims_qcget_system_finding(end)*******");
			} catch (Exception e) {
				// TODO Auto-generated catch block 
				e.printStackTrace();
			}
			return sys_f;
		}
		
		public List<OrderImsSystemFindingDTO> sysFcheckingNowTV() throws DAOException{
			logger.info("sysFcheckingNowTV");
			
			String SQL =    " select a.order_id, d.CUST_NO,C.SERBDYNO,C.FLOOR_NO, C.UNIT_NO,C.HI_LOT_NO,D.SERVICE_NUM,D.ID_DOC_TYPE,D.ID_DOC_NUM ,b.IS_NEW_CUST_IND "+
							" from bomweb_order_ims b, bomweb_order a,bomweb_cust_addr c,bomweb_customer d             "+
							" where a.order_id = b.order_id                                                            "+
							" and a.order_id = c.order_id                                                              "+
							" and a.order_id = d.order_id                                                              "+
							" and b.ims_order_type = 'DS'                                                              "+
							" and a.order_id like 'VV%'                                                                "+
							" AND (a.order_type = 'NTV-A' or a.order_type is null)																		 "+
							" and A.SIGN_OFF_DATE is not null                                                          "+
							" and a.order_status not in ('05','06','07','08','20')									   "+
							" and b.SYS_F is null                                                     				   "+
							" AND trunc(a.create_date) = trunc(sysdate)                                                ";   
			
			ParameterizedRowMapper<OrderImsSystemFindingDTO> mapper = new ParameterizedRowMapper<OrderImsSystemFindingDTO>() {

				public OrderImsSystemFindingDTO mapRow(ResultSet rs, int rowNum)
						throws SQLException {
					OrderImsSystemFindingDTO dto = new OrderImsSystemFindingDTO();

					dto.setOrderID(rs.getString("order_id"));
					dto.setBomCustNo(rs.getString("CUST_NO"));
					dto.setSerbdyno(rs.getString("SERBDYNO"));
					dto.setFloorNo(rs.getString("FLOOR_NO"));
					dto.setUnitNo(rs.getString("UNIT_NO"));
					dto.setHiLotNo(rs.getString("HI_LOT_NO"));
					dto.setIdDocType(rs.getString("ID_DOC_TYPE"));
					dto.setIdDocNum(rs.getString("ID_DOC_NUM"));
					dto.setServiceNum(rs.getString("SERVICE_NUM"));
					dto.setIsNewCustomer(rs.getString("IS_NEW_CUST_IND"));
				
					return dto;
				}
			};
			
			try {
				logger.debug("sysFcheckingNowTV @ OrderDAO: " + SQL);
				List<OrderImsSystemFindingDTO> orders = simpleJdbcTemplate.query(SQL, mapper);
				
				return orders;
			
			} catch (Exception e) {
				logger.error("Exception caught in sysFcheckingNowTV()", e);
				throw new DAOException(e.getMessage(), e);
			}		
		}
		
		public List<BomwebOrderL2JobDTO> getBomwebOrderL2Job(String orderId) throws DAOException{
			logger.debug("getBomwebOrderL2Job");
			String SQL = "	SELECT ORDER_ID, DTL_ID, DTL_SEQ, PRODUCT_ID, L2_CD, FT_IND, L2_OTY,ACT_IND,	" +
			"	       LAST_UPD_BY, LAST_UPD_DATE	" +
			"	  FROM bomweb_order_l2_job	" +
			"	 WHERE order_id = ?	" ;
			
			ParameterizedRowMapper<BomwebOrderL2JobDTO> mapper = new ParameterizedRowMapper<BomwebOrderL2JobDTO>() {
				public BomwebOrderL2JobDTO mapRow(ResultSet rs, int rowNum)
						throws SQLException {
					BomwebOrderL2JobDTO dto = new BomwebOrderL2JobDTO();
						
					dto.setOrderId(rs.getString("ORDER_ID"));
					dto.setDtlId(rs.getString("DTL_ID"));
					dto.setDtlSeq(rs.getString("DTL_SEQ"));
					dto.setProductId(rs.getString("PRODUCT_ID"));
					dto.setL2Cd(rs.getString("L2_CD"));
					dto.setFtInd(rs.getString("FT_IND"));
					dto.setL2Qty(rs.getString("L2_OTY"));
					dto.setActInd(rs.getString("ACT_IND"));
					return dto;
				}
			};
			try {
				logger.debug("getBomwebOrderL2Job @ OrderDAO: " + SQL);
				List<BomwebOrderL2JobDTO> orders = simpleJdbcTemplate.query(SQL, mapper, orderId);
				return orders;
			} catch (Exception e) {
				logger.error("Exception caught in getBomwebOrderL2Job()", e);
				throw new DAOException(e.getMessage(), e);
			}
		}
		public void insertPreGenAccount(OrderImsUI dto) throws DAOException{
			logger.debug("insertBomWebCustomer");
			String SQL = " Insert into bomweb_amend_category "+
				"   (ORDER_ID, DTL_ID, AMEND_SEQ, NATURE, CC_THIRD_PARTY_IND, CC_TYPE, CC_NUM, CC_HOLD_NAME, " +
						"CC_EXP_DATE, CC_ISSUE_BANK, CC_ID_DOC_TYPE, CC_ID_DOC_NO, CC_VERIFIED_IND, PRE_WIRING_START_TIME, " +
						"PRE_WIRING_END_TIME, CUT_OVER_START_TIME, CUT_OVER_END_TIME, APPNT_START_TIME, APPNT_END_TIME, SERIAL_NUM, " +
						"DELAY_REASON_CD, CREATE_BY, CREATE_DATE, LAST_UPD_BY, LAST_UPD_DATE, SEND, RMK2, WQ_BATCH_ID, FAX_SERIAL, RMK, " +
						"EMAIL_ADDR, LOGIN_ID, TARGET_COMM_DATE) "+
						"   select boi.order_id, 1,  (select nvl(max(amend_seq),0)+1 from BOMWEB_AMEND_CATEGORY where order_id = :orderID ),'10001',null, "
						+ " null,bc.cust_no,:preGenAcct_no ,null,null, "
						+ " null,bc.service_num, "
						+ " null,null,null,null,null,null,null,null, "
						+ " null,boi.create_by,sysdate,null,null, "
						+ "'N',null,null,null,null,null, null,null  "
						+ "from bomweb_customer bc,  bomweb_order_ims boi "
						+ " where bc.order_id=boi.order_id  "
						+ " and boi.order_id = :orderId  ";

			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("orderID", dto.getOrderId());
//			params.addValue("contact_Email", dto.getEmailAddress());
			params.addValue("preGenAcct_no", dto.getPrimaryAcctNo());
			params.addValue("orderId", dto.getOrderId());
			
			try{
				logger.debug("key search: " + params.getValues());
				logger.debug("insertPreGenAccount: " + SQL);
				simpleJdbcTemplate.update(SQL,params);
				
			}catch (Exception e) {
				logger.error("Exception caught in insertPreGenAccount()", e);
				throw new DAOException(e.getMessage(), e);
			}
			

		}
		
		public List<OrderImsDTO> getBomWebOrderFlag(String orderId) throws DAOException{
			logger.debug("getBomWebOrderFlag");
		
			String SQL = "	SELECT nvl(PRE_INST_IND,'N') PRE_INST_IND 	" +
						 "	FROM bomweb_order_ims	  					" +
						 "	WHERE order_id = :orderID 					";
			
			ParameterizedRowMapper<OrderImsDTO> mapper = new ParameterizedRowMapper<OrderImsDTO>() {

				public OrderImsDTO mapRow(ResultSet rs, int rowNum)
						throws SQLException {
					OrderImsDTO dto = new OrderImsDTO();
					dto.setPreInstallInd(rs.getString("PRE_INST_IND"));
					return dto;
				}
			};

					
			try {
				logger.debug("getBomWebOrderFlag @ OrderDAO: " + SQL);
				List<OrderImsDTO> orders = simpleJdbcTemplate
						.query(SQL, mapper, orderId);
				
				return orders;
			
			} catch (Exception e) {
				logger.error("Exception caught in getBomWebOrderFlag()", e);
				throw new DAOException(e.getMessage(), e);
			}

		}
		
		public List<BomwebSubscribedOfferImsDTO> getBomwebSubscribedOfferIms(String orderId) throws DAOException{
//			logger.info("getBomwebSubscribedOfferIms");
			
			String SQL = "	select	" +
			"	ORDER_ID, DTL_ID, ITEM_ID ,IO_IND ,OFFER_ID ,OFFER_SUB_ID,	" +
			"	PRODUCT_ID, TC_ID, TC_CD, TC_START_DATE, TC_END_DATE,	" +
			"	PENALTY_AMT, WAIVE_CD, CREATE_BY, CREATE_DATE, UPDATE_BY, UPDATE_DATE, VI_IND, OFFER_SUB_KEY, OFFER_CD,SB_OFFER_TYPE,TC_DURATION,IMS_SERVICE_TYPE, VI_CAMPAIGN," +
			"	TC_REMAIN_MTH, " +
			"   offer_desc, offer_desc_chi, price, eff_price " +
			"	from BOMWEB_SUBSCRIBED_OFFER_IMS	" +
			"	where ORDER_ID = ?	";


			
			ParameterizedRowMapper<BomwebSubscribedOfferImsDTO> mapper = new ParameterizedRowMapper<BomwebSubscribedOfferImsDTO>() {

				public BomwebSubscribedOfferImsDTO mapRow(ResultSet rs, int rowNum)
						throws SQLException {
					BomwebSubscribedOfferImsDTO dto = new BomwebSubscribedOfferImsDTO();
					dto.setOfferDesc(rs.getString("offer_desc")); 
					dto.setOfferDescChi(rs.getString("offer_desc_chi")); 
					dto.setPrice(rs.getString("price")); 
					dto.setArpuPrice(rs.getString("eff_price"));
					dto.setOrderId(rs.getString("ORDER_ID"));
					dto.setDtlId(rs.getString("DTL_ID"));
					dto.setItemId(rs.getString("ITEM_ID"));
					dto.setIoInd(rs.getString("IO_IND"));
					dto.setOfferId(rs.getString("OFFER_ID"));
					dto.setOfferSubId(rs.getString("OFFER_SUB_ID"));
					dto.setProductId(rs.getString("PRODUCT_ID"));
					dto.setTcId(rs.getString("TC_ID"));
					dto.setTcCd(rs.getString("TC_CD"));
					dto.setTcStartDate(rs.getTimestamp("TC_START_DATE"));
					dto.setTcEndDate(rs.getTimestamp("TC_END_DATE"));
					dto.setPenaltyAmt(rs.getString("PENALTY_AMT"));
					dto.setWaiveCd(rs.getString("WAIVE_CD"));
					dto.setCreateBy(rs.getString("CREATE_BY"));
					dto.setCreateDate(rs.getTimestamp("CREATE_DATE"));
					dto.setUpdateBy(rs.getString("UPDATE_BY"));
					dto.setUpdateDate(rs.getTimestamp("UPDATE_DATE"));
					dto.setViInd(rs.getString("VI_IND")); 
					dto.setOfferSubKey(rs.getString("OFFER_SUB_KEY"));
					dto.setOfferCd(rs.getString("OFFER_CD"));
					dto.setOfferType(rs.getString("SB_OFFER_TYPE"));
					dto.setDuration(rs.getString("TC_DURATION"));
					dto.setImsserviceType(rs.getString("IMS_SERVICE_TYPE"));
					dto.setViCampaign(rs.getString("VI_CAMPAIGN"));
					dto.setTcRemainMth(rs.getInt("TC_REMAIN_MTH"));
					return dto;
				}
			};
			
			try {
				logger.debug("getBomwebSubscribedOfferIms @ OrderDAO: " + SQL);
				List<BomwebSubscribedOfferImsDTO> dtos = simpleJdbcTemplate
						.query(SQL, mapper, orderId);
				
				return dtos;
			
			} catch (Exception e) {
				logger.error("Exception caught in getBomwebSubscribedOfferIms()", e);

				throw new DAOException(e.getMessage(), e);
			}		
			
		}
		
		public List<BomwebSubscribedOfferImsDTO> getBomwebSubscribedOfferImsAmd(String orderId, String amendSeq) throws DAOException{
//			logger.info("getBomwebSubscribedOfferIms");
			
			String SQL = "	select	" +
			"	ORDER_ID, DTL_ID, IO_IND ,OFFER_ID,	" +
			"	PRODUCT_ID, TC_ID, TC_CD, TC_START_DATE, TC_END_DATE,	" +
			"	PENALTY_AMT, WAIVE_CD, CREATE_BY, CREATE_DATE, UPDATE_BY, UPDATE_DATE, " +
			"   VI_IND, OFFER_SUB_KEY, OFFER_CD,SB_OFFER_TYPE,TC_DURATION,IMS_SERVICE_TYPE, VI_CAMPAIGN," +
			"	TC_REMAIN_MTH" +
			"	from BOMWEB_SUB_OFFER_IMS_amd	" +
			"	where ORDER_ID = ?	and amend_seq = ? ";


			
			ParameterizedRowMapper<BomwebSubscribedOfferImsDTO> mapper = new ParameterizedRowMapper<BomwebSubscribedOfferImsDTO>() {

				public BomwebSubscribedOfferImsDTO mapRow(ResultSet rs, int rowNum)
						throws SQLException {
					BomwebSubscribedOfferImsDTO dto = new BomwebSubscribedOfferImsDTO();
					dto.setOrderId(rs.getString("ORDER_ID"));
					dto.setDtlId(rs.getString("DTL_ID"));
					dto.setIoInd(rs.getString("IO_IND"));
					dto.setOfferId(rs.getString("OFFER_ID"));
					dto.setProductId(rs.getString("PRODUCT_ID"));
					dto.setTcId(rs.getString("TC_ID"));
					dto.setTcCd(rs.getString("TC_CD"));
					dto.setTcStartDate(rs.getTimestamp("TC_START_DATE"));
					dto.setTcEndDate(rs.getTimestamp("TC_END_DATE"));
					dto.setPenaltyAmt(rs.getString("PENALTY_AMT"));
					dto.setWaiveCd(rs.getString("WAIVE_CD"));
					dto.setCreateBy(rs.getString("CREATE_BY"));
					dto.setCreateDate(rs.getTimestamp("CREATE_DATE"));
					dto.setUpdateBy(rs.getString("UPDATE_BY"));
					dto.setUpdateDate(rs.getTimestamp("UPDATE_DATE"));
					dto.setViInd(rs.getString("VI_IND")); 
					dto.setOfferSubKey(rs.getString("OFFER_SUB_KEY"));
					dto.setOfferCd(rs.getString("OFFER_CD"));
					dto.setOfferType(rs.getString("SB_OFFER_TYPE"));
					dto.setDuration(rs.getString("TC_DURATION"));
					dto.setImsserviceType(rs.getString("IMS_SERVICE_TYPE"));
					dto.setViCampaign(rs.getString("VI_CAMPAIGN"));
					dto.setTcRemainMth(rs.getInt("TC_REMAIN_MTH"));
					return dto;
				}
			};
			
			try {
//				logger.debug("getBomwebSubscribedOfferImsAmd @ OrderDAO: " + SQL);
				List<BomwebSubscribedOfferImsDTO> dtos = simpleJdbcTemplate.query(SQL, mapper, orderId, amendSeq);
				
				return dtos;
			
			} catch (Exception e) {
				logger.error("Exception caught in getBomwebSubscribedOfferIms()", e);

				throw new DAOException(e.getMessage(), e);
			}		
			
		}

		
		public void insertBomWebThirdPartyAppln(BomwebThirdPartyApplnDTO dto) throws DAOException{
			logger.debug("insertBomWebThirdPartyAppln");
			
			String SQL = "	insert into BOMWEB_THIRD_PARTY_APPLN(	" +
			"	ORDER_ID, DTL_ID, APPNT_FIRST_NAME, APPNT_LAST_NAME,	" +
			"	APPNT_DOC_TYPE, APPNT_DOC_ID, RELATIONSHIP_CD,	" +
			"	APPNT_ID_VERIFIED_IND, APPNT_CONTACT_NUM,	" +
			"	CREATE_BY, CREATE_DATE, LAST_UPD_BY,	" +
			"	LAST_UPD_DATE, TITLE,REMARKS	" +
			"	) values(	" +
			"	?, ?, ?, ?,	" +
			"	?, ?, ?,	" +
			"	?, ?,	" +
			"	?, sysdate, ?,	" +
			"	?, ?,?	" +
			"	)	" ;
			
			try{
				simpleJdbcTemplate.update(SQL,
						dto.getOrderId(), "1", dto.getAppntFirstName(), dto.getAppntLastName(),
						dto.getAppntDocType(), dto.getAppntDocId(), dto.getRelationshipCd(),
						dto.getAppntIdVerifiedInd(), dto.getAppntContactNum(),
						dto.getCreateBy(), dto.getLastUpdBy(),
						dto.getLastUpdDate(), dto.getTitle(),dto.getRemarks()
						);
			}catch (Exception e) {
				logger.error("Exception caught in insertBomWebThirdPartyAppln()", e);
				throw new DAOException(e.getMessage(), e);
			}
			
		}
		
		public List<BomwebThirdPartyApplnDTO> getBomWebThirdPartyAppln(String orderId) throws DAOException{
//			logger.info("getBomWebThirdPartyAppln");
			
			String SQL = "	select	" +
			"	ORDER_ID, DTL_ID, APPNT_FIRST_NAME, APPNT_LAST_NAME, APPNT_DOC_TYPE,	" +
			"	APPNT_DOC_ID, RELATIONSHIP_CD, APPNT_ID_VERIFIED_IND, APPNT_CONTACT_NUM,	" +
			"	CREATE_BY, CREATE_DATE, LAST_UPD_BY, LAST_UPD_DATE, TITLE,REMARKS	" +
			"	from BOMWEB_THIRD_PARTY_APPLN	" +
			"	where ORDER_ID = ?	";

			
			ParameterizedRowMapper<BomwebThirdPartyApplnDTO> mapper = new ParameterizedRowMapper<BomwebThirdPartyApplnDTO>() {

				public BomwebThirdPartyApplnDTO mapRow(ResultSet rs, int rowNum)
						throws SQLException {
					BomwebThirdPartyApplnDTO dto = new BomwebThirdPartyApplnDTO();

					dto.setOrderId(rs.getString("ORDER_ID"));
					dto.setDtlId(rs.getString("DTL_ID"));
					dto.setAppntFirstName(rs.getString("APPNT_FIRST_NAME"));
					dto.setAppntLastName(rs.getString("APPNT_LAST_NAME"));
					dto.setAppntDocType(rs.getString("APPNT_DOC_TYPE"));
					dto.setAppntDocId(rs.getString("APPNT_DOC_ID"));
					dto.setRelationshipCd(rs.getString("RELATIONSHIP_CD"));
					dto.setAppntIdVerifiedInd(rs.getString("APPNT_ID_VERIFIED_IND"));
					dto.setAppntContactNum(rs.getString("APPNT_CONTACT_NUM"));
					dto.setCreateBy(rs.getString("CREATE_BY"));
					dto.setCreateDate(rs.getTimestamp("CREATE_DATE"));
					dto.setLastUpdBy(rs.getString("LAST_UPD_BY"));
					dto.setLastUpdDate(rs.getTimestamp("LAST_UPD_DATE"));
					dto.setTitle(rs.getString("TITLE"));
					dto.setRemarks(rs.getString("REMARKS"));

				
					return dto;
				}
			};
			
			try {
				logger.debug("getBomWebThirdPartyAppln @ OrderDAO: " + SQL);
				List<BomwebThirdPartyApplnDTO> dtos = simpleJdbcTemplate
						.query(SQL, mapper, orderId);
				
				return dtos;
			
			} catch (Exception e) {
				logger.error("Exception caught in getBomWebThirdPartyAppln()", e);

				throw new DAOException(e.getMessage(), e);
			}		
			
		}
		public void updateBomwebThirdPartyAppln(BomwebThirdPartyApplnDTO dto) throws DAOException{
			logger.debug("updateBomwebThirdPartyAppln");
			
			String SQL ="	update BOMWEB_THIRD_PARTY_APPLN set	" +
			"	ORDER_ID = ?,	" +
			"	DTL_ID = ?,	" +
			"	APPNT_FIRST_NAME = ?,	" +
			"	APPNT_LAST_NAME = ?,	" +
			"	APPNT_DOC_TYPE = ?,	" +
			"	APPNT_DOC_ID = ?,	" +
			"	RELATIONSHIP_CD = ?,	" +
			"	APPNT_ID_VERIFIED_IND = ?,	" +
			"	APPNT_CONTACT_NUM = ?,	" +
			//"	CREATE_BY = ?,	" +
			//"	CREATE_DATE = ?,	" +
			"	LAST_UPD_BY = ?,	" +
			"	LAST_UPD_DATE = sysdate,	" +
			"	TITLE = ?,	" +
			"	REMARKS = ?	" +
			"	where ORDER_ID = ?	";


			try {
				simpleJdbcTemplate.update(SQL,
						dto.getOrderId(),
						"1",
						dto.getAppntFirstName(),
						dto.getAppntLastName(),
						dto.getAppntDocType(),
						dto.getAppntDocId(),
						dto.getRelationshipCd(),
						dto.getAppntIdVerifiedInd(),
						dto.getAppntContactNum(),
						//dto.getCreateBy(),
						//dto.getCreateDate(),
						dto.getLastUpdBy(),
						//dto.getLastUpdDate(),
						dto.getTitle(),
						dto.getRemarks(),
						dto.getOrderId()
						);

			} catch (Exception e) {
				logger.error("Exception caught in updateBomwebThirdPartyAppln()", e);
				throw new DAOException(e.getMessage(), e);
			}
		}
		public void insertBomWebBillingAddr(BomwebBillingAddrDTO dto) throws DAOException{
			logger.debug("insertBomWebBillingAddr");
			
			String SQL = "	insert into BOMWEB_BILLING_ADDR(	" +
			"	ORDER_ID, ACCT_NO, ACTION, INSTANT_UPDATE_IND,	" +
			"	ADDR_LINE1, ADDR_LINE2, ADDR_LINE3, ADDR_LINE4,	" +
			"	ADDR_LINE5, ADDR_LINE6, CREATE_BY, CREATE_DATE,	" +
			"	LAST_UPD_BY, LAST_UPD_DATE, ADDR_DTL	" +
			"	) values(	" +
			"	?, ?, ?, ?,	" +
			"	?, ?, ?, ?,	" +
			"	?, ?, ?, sysdate,	" +
			"	?, ?, ?	" +
			"	)	" ;
			
			try{
				simpleJdbcTemplate.update(SQL,
						dto.getOrderId(), dto.getAcctNo(), dto.getAction(), dto.getInstantUpdateInd(),
						dto.getAddrLine1(), dto.getAddrLine2(), dto.getAddrLine3(), dto.getAddrLine4(),
						dto.getAddrLine5(), dto.getAddrLine6(), dto.getCreateBy(),
						dto.getLastUpdBy(), dto.getLastUpdDate(), dto.getAddrDtl()
						);
			}catch (Exception e) {
				logger.error("Exception caught in insertBomWebBillingAddr()", e);
				throw new DAOException(e.getMessage(), e);
			}
			
		}
		
		public List<BomwebBillingAddrDTO> getBomwebBillingAddr(String orderId) throws DAOException{
//			logger.info("getBomwebBillingAddr");
			
			String SQL = "	select	" +
			"	ORDER_ID, ACCT_NO, ACTION, INSTANT_UPDATE_IND, ADDR_LINE1,	" +
			"	ADDR_LINE2, ADDR_LINE3, ADDR_LINE4, ADDR_LINE5, ADDR_LINE6,	" +
			"	CREATE_BY, CREATE_DATE, LAST_UPD_BY, LAST_UPD_DATE, ADDR_DTL	" +
			"	from BOMWEB_BILLING_ADDR	" +
			"	where ORDER_ID = ?	";

			
			ParameterizedRowMapper<BomwebBillingAddrDTO> mapper = new ParameterizedRowMapper<BomwebBillingAddrDTO>() {

				public BomwebBillingAddrDTO mapRow(ResultSet rs, int rowNum)
						throws SQLException {
					BomwebBillingAddrDTO dto = new BomwebBillingAddrDTO();

					dto.setOrderId(rs.getString("ORDER_ID"));
					dto.setAcctNo(rs.getString("ACCT_NO"));
					dto.setAction(rs.getString("ACTION"));
					dto.setInstantUpdateInd(rs.getString("INSTANT_UPDATE_IND"));
					dto.setAddrLine1(rs.getString("ADDR_LINE1"));
					dto.setAddrLine2(rs.getString("ADDR_LINE2"));
					dto.setAddrLine3(rs.getString("ADDR_LINE3"));
					dto.setAddrLine4(rs.getString("ADDR_LINE4"));
					dto.setAddrLine5(rs.getString("ADDR_LINE5"));
					dto.setAddrLine6(rs.getString("ADDR_LINE6"));
					dto.setCreateBy(rs.getString("CREATE_BY"));
					dto.setCreateDate(rs.getTimestamp("CREATE_DATE"));
					dto.setLastUpdBy(rs.getString("LAST_UPD_BY"));
					dto.setLastUpdDate(rs.getTimestamp("LAST_UPD_DATE"));
					dto.setAddrDtl(rs.getString("ADDR_DTL"));
				
					return dto;
				}
			};
			
			try {
				logger.debug("getBomwebBillingAddr @ OrderDAO: " + SQL);
				List<BomwebBillingAddrDTO> dtos = simpleJdbcTemplate
						.query(SQL, mapper, orderId);
				
				return dtos;
			
			} catch (Exception e) {
				logger.error("Exception caught in getBomwebBillingAddr()", e);

				throw new DAOException(e.getMessage(), e);
			}		
			
		}
		
		
		public void updateBomwebBillingAddr(BomwebBillingAddrDTO dto) throws DAOException{
			logger.debug("updateBomwebBillingAddr");
			
			String SQL ="	update BOMWEB_BILLING_ADDR set	" +
			"	ORDER_ID = ?,	" +
			"	ACCT_NO = ?,	" +
			"	ACTION = ?,	" +
			"	INSTANT_UPDATE_IND = ?,	" +
			"	ADDR_LINE1 = ?,	" +
			"	ADDR_LINE2 = ?,	" +
			"	ADDR_LINE3 = ?,	" +
			"	ADDR_LINE4 = ?,	" +
			"	ADDR_LINE5 = ?,	" +
			"	ADDR_LINE6 = ?,	" +
			//"	CREATE_BY = ?,	" +
			//"	CREATE_DATE = ?,	" +
			"	LAST_UPD_BY = ?,	" +
			"	LAST_UPD_DATE = sysdate,	" +
			"	ADDR_DTL = ?	" +
			"	where ORDER_ID = ?	";


			try {
				simpleJdbcTemplate.update(SQL,
						dto.getOrderId(),
						dto.getAcctNo(),
						dto.getAction(),
						dto.getInstantUpdateInd(),
						dto.getAddrLine1(),
						dto.getAddrLine2(),
						dto.getAddrLine3(),
						dto.getAddrLine4(),
						dto.getAddrLine5(),
						dto.getAddrLine6(),
						//dto.getCreateBy(),
						//dto.getCreateDate(),
						dto.getLastUpdBy(),
						//dto.getLastUpdDate(),
						dto.getAddrDtl(),
						dto.getOrderId()
						);

			} catch (Exception e) {
				logger.error("Exception caught in updateBomwebBillingAddr()", e);
				throw new DAOException(e.getMessage(), e);
			}
		}
		
		

		
		public BomSalesUserDTO getCreateByStaff(String staffId, String date) throws DAOException{
			logger.info("getCreateByStaff is called staffId:"+staffId+" date:"+date);	
			BomSalesUserDTO result =null;
			try{				
				String SQL = "	select CHANNEL_ID, CHANNEL_CD, CENTRE_CD, TEAM_CD, BSP.STAFF_ID, category" +
						"    from bomweb_sales_profile bsp, BOMWEB_SALES_ASSIGNMENT bsa " +
						"    where bsa.STAFF_ID = bsp.STAFF_ID " +
						"    and to_date(?,'ddMMyyyy') BETWEEN bsp.START_DATE AND NVL( bsp.END_DATE,sysdate) " +
						"    and to_date(?,'ddMMyyyy') BETWEEN bsa.START_DATE AND NVL( bsa.END_DATE,sysdate) " +
						"    and (bsp.org_staff_id = ? or bsp.staff_id =  ? ) and rownum=1 ";				
				ParameterizedRowMapper<BomSalesUserDTO> mapper = new ParameterizedRowMapper<BomSalesUserDTO>() {
					public BomSalesUserDTO mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						BomSalesUserDTO dto = new BomSalesUserDTO();
						dto.setChannelId(rs.getInt("CHANNEL_ID"));
						dto.setChannelCd(rs.getString("CHANNEL_CD"));
						dto.setAreaCd(rs.getString("CENTRE_CD"));
						dto.setShopCd(rs.getString("TEAM_CD"));
						dto.setSalesCd(rs.getString("STAFF_ID"));		
						dto.setCategory(rs.getString("category"));
						return dto;
					}
				};
				result = (BomSalesUserDTO) simpleJdbcTemplate.queryForObject(SQL, mapper, date,date,staffId, staffId);	
				logger.info("getCreateByStaff:"+gson.toJson(result));	
			}catch (EmptyResultDataAccessException erdae){
				logger.debug("EmptyResultDataAccessException");
				result = null;
			}catch (Exception e) {
					logger.error("Exception caught in getCreateByStaff()", e);
					throw new DAOException(e.getMessage(), e);
			}
			return result;
		}
		
		public void updateShortenURL(String order_id, String shortenURL) throws DAOException{
			logger.info("updateShortenURL");
			
			String SQL ="	update BOMWEB_ORDER_IMS set	" +
			"	SHORTENED_URL_AF = ?	" +
			"	where ORDER_ID = ?	";


			try {
				simpleJdbcTemplate.update(SQL,
						shortenURL,
						order_id
						);

			} catch (Exception e) {
				logger.error("Exception caught in updateShortenURL()", e);
				throw new DAOException(e.getMessage(), e);
			}
		}
		
		public void updateShortenURLQrCode(String order_id, String shortenURL) throws DAOException{
			logger.debug("updateShortenURLQrCode");
			String SQL ="	update BOMWEB_ORDER_IMS set	" +
			"	SHORTENED_URL_QR_CD = ?	" +
			"	where ORDER_ID = ?	";

			try {
				simpleJdbcTemplate.update(SQL, shortenURL, order_id);
			} catch (Exception e) {
				logger.error("Exception caught in updateShortenURLQrCode()", e);
				throw new DAOException(e.getMessage(), e);
			}
		}
		
		public String getShortenUrl(String order_id) throws DAOException {
			logger.debug(" getShortenUrl @ OrderDAO");
			String shortenUrl = null;
			
			String SQL = " select shortened_url_af " +
						 " from BOMWEB_order_ims where " +
						 " order_id = ? ";
			
			
			try{
			
				shortenUrl = simpleJdbcTemplate.queryForObject(SQL, String.class, order_id);
				if(shortenUrl==null || "".equals(shortenUrl)){
					shortenUrl="Not Exist";
				}
			
			}catch(Exception e) {
				logger.error("Exception caught in getShortenUrl()", e);
				throw new DAOException(e.getMessage(), e);
			}
			
			return shortenUrl;
		}
		
		public String getShortenUrlQrCode(String order_id) throws DAOException {
			logger.debug(" getShortenUrlQrCode @ OrderDAO");
			String shortenUrlQrCode = null;
			
			String SQL = " select SHORTENED_URL_QR_CD " +
						 " from BOMWEB_order_ims where " +
						 " order_id = ? ";
			try{			
				shortenUrlQrCode = simpleJdbcTemplate.queryForObject(SQL, String.class, order_id);
				if(shortenUrlQrCode==null || "".equals(shortenUrlQrCode)){
					shortenUrlQrCode="Not Exist";
				}
			}catch(Exception e) {
				logger.error("Exception caught in getShortenUrlQrCode()", e);
				throw new DAOException(e.getMessage(), e);
			}
			return shortenUrlQrCode;
		}
		
		public void insertBomwebCustIguardReg(CustomerUI dto) throws DAOException{
			logger.debug("insertBomwebCustIguardReg");
			String SQL = "	INSERT INTO BOMWEB_CUST_IGUARD_REG 	" +
			"	              (ORDER_ID, CARECASH_IND, CARECASH_DM_IND, EMAIL_ADDR, CONTACT_NUM, CREATE_BY, CREATE_DATE, LAST_UPD_BY, LAST_UPD_DATE, 	" +
			"	           STATUS, RTN_MSG) 	" +
			"	     VALUES (?, ?, ?, ?, ?,	" +
			"	             ?, sysdate, null, null,	" +
			"	             ? ,?  " +
			"	            )	";
			
			try{
				simpleJdbcTemplate.update(SQL,
						dto.getOrderId(),dto.getCareCashInd(),dto.getCareCashOptOutInd(),dto.getCareCashEmail(),dto.getCareCashMobile(),
						dto.getCreateBy(),dto.getCareCashRegStatus(),
						dto.getCareCashRtnMsg()
						);
			}catch (Exception e) {
				logger.error("Exception caught in insertBomwebCustIguardReg()", e);
				throw new DAOException(e.getMessage(), e);
			}			
		}
		
		
		public void updateBomwebCustIguardReg(CustomerUI dto) throws DAOException{
			logger.debug("updateBomwebCustIguardReg");
			String SQL = "	UPDATE  BOMWEB_CUST_IGUARD_REG 	" +
			"	   SET CARECASH_IND = ?,	" +
			"	       CARECASH_DM_IND = ?,	" +
			"	       EMAIL_ADDR = ?,	" +
			"	       CONTACT_NUM = ?,	" +
			"	       LAST_UPD_BY = ?,	" +
			"	       LAST_UPD_DATE = sysdate,	" +
			"	       STATUS = ?,	" +
			"	       RTN_MSG = ?	" +
			"	 WHERE order_id = ?	" ;
			
			try {
				simpleJdbcTemplate.update(SQL,
						dto.getCareCashInd(),dto.getCareCashOptOutInd(),dto.getCareCashEmail(),dto.getCareCashMobile(),
						dto.getLastUpdBy(),
						dto.getCareCashRegStatus(),
						dto.getCareCashRtnMsg(),
						dto.getOrderId()
						);

			} catch (Exception e) {
				logger.error("Exception caught in updateBomwebCustIguardReg()", e);
				throw new DAOException(e.getMessage(), e);
			}
			
		}
		

		
		public String getHourFromDB (String grp_id)
		throws DAOException {

			List<String> result = new ArrayList<String>();
			
			String hour="";
			
			String SQL = 
					" select code from w_code_lkup " +
					" where grp_id = :grp_id " ;

			MapSqlParameterSource params = new MapSqlParameterSource();
				params.addValue("grp_id", grp_id);
				
			ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
				public String mapRow(ResultSet rs, int rowNum) throws SQLException {
					String dto = new String();
					dto = (String) rs.getString("code");
					return dto;
				}
			};
			
			try {
				logger.debug("key search: " + params.getValues());
				logger.info("dBNotSignOff() @ ImsReport1DAO: " + SQL);
				result = simpleJdbcTemplate.query(SQL, mapper, params);
				if(result.size()>0){
					logger.info("dBNotSignOff result.get(0):"+result.get(0));
					hour=result.get(0);
				}else{
					logger.info("dBNotSignOff result.size()<0");
				}
			} catch (Exception e) {
				logger.info("Exception caught in getHourFromDB():", e);
			}
			
			return hour;
		}
		
		public String getSignOffDate (String orderId)	throws DAOException {
			List<String> result = new ArrayList<String>();
			
			String SQL = " select to_char(sign_off_date,'YYYYMM') signOffDate from bomweb_order where order_id = :orderId   " ;
			
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("orderId", orderId);
	
		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				String dto = new String();
				dto = (String) rs.getString("signOffDate");
				return dto;
			}
		};
	
		try {
			logger.debug("getSignOffDate orderId: " + params.getValues());
			result = simpleJdbcTemplate.query(SQL, mapper,params);
	
		} catch (EmptyResultDataAccessException erdae) {
			logger.debug("EmptyResultDataAccessException");
			result = null;
		} catch (Exception e) {
			logger.debug("Exception caught in getOrderStatusList():", e);
			throw new DAOException(e.getMessage(), e);
		}	
			if(result==null||result.size()<1){
				return null;
			}else{
				return result.get(0);
			}
		}
		
		public List<IOOfferOTCSchemeDTO> getIOProdId (String oldItemString, String newItemSrting)	throws DAOException {
			
			String SQL = "	select 'I' io_ind, product_id from w_item_offer_product_assgn " +
						 "  where item_id in (" + newItemSrting + ") " +  
						 "	and item_id not in (" + oldItemString + ") " +
						 "	union " +
						 "	select 'O' io_ind, product_id from w_item_offer_product_assgn " +
						 "	where item_id in (" + oldItemString + ") " +
						 "	and item_id not in (" + newItemSrting + ")";

			
			ParameterizedRowMapper<IOOfferOTCSchemeDTO> mapper = new ParameterizedRowMapper<IOOfferOTCSchemeDTO>() {

				public IOOfferOTCSchemeDTO mapRow(ResultSet rs, int rowNum)
						throws SQLException {
					IOOfferOTCSchemeDTO dto = new IOOfferOTCSchemeDTO();

					dto.setProdId(rs.getString("product_id"));
					dto.setIOInd(rs.getString("io_ind"));
				
					return dto;
				}
			};
			
			try {
				logger.debug("getIOProdId @ OrderDAO: " + SQL);
				List<IOOfferOTCSchemeDTO> dtos = simpleJdbcTemplate
						.query(SQL, mapper);
				
				return dtos;
			
			} catch (Exception e) {
				logger.error("Exception caught in getIOProdId()", e);

				throw new DAOException(e.getMessage(), e);
			}		
			
		}
		
		
		public String isMatchRole( String channelCd, String catogery) throws DAOException {
			logger.debug(" isMatchRole @ ImsOrderDAO");
			String isMatchRole = null;
			
			String SQL = " select decode(count(*), '0', 'N', 'Y') isMatchRole " +
						 " from bomweb_code_lkup where CODE_type = 'DS_MOBILE_ROLE' and CODE_id = ? and CODE_DESC = ?";
			
			
			try{
			
				isMatchRole = simpleJdbcTemplate.queryForObject(SQL, String.class, channelCd,catogery);
			
			}catch(Exception e) {
				logger.error("Exception caught in isMatchRole()", e);
				throw new DAOException(e.getMessage(), e);
			}
			
			return isMatchRole;
		}
		
		public String isDS6User(String staffID) throws DAOException {
			logger.debug("isDS6User @ ImsOrderDAO");
			String isDS6User = null;
			
			String SQL = "select decode(count(*), '0', 'N', 'Y') isDS6User " +
			"from bomweb_sales_profile a, bomweb_sales_profile b " +
			"where a.staff_id = ? " +
			"and b.staff_id in (select staff_id  from bomweb_sales_assignment " +
			"where channel_id = 12 " +
			"and channel_cd = 'DS6' " +
			"and end_date is null " +
			"and staff_id = 'P'||substr(a.staff_id,2,9)) " +
			"and exists (select 1 from bomweb_sales_assignment " +
			"where staff_id = a.staff_id and end_date is null " +
			"and channel_id in (select code from w_code_lkup where grp_id = 'IMS_DS6_MOB_CHNL_ID')) " +
			"and a.end_date is null and b.end_date is null " +
			"and upper(REPLACE(REPLACE(REPLACE(a.STAFF_NAME,'(',''),')',''),' ','')) = upper(REPLACE(REPLACE(REPLACE(b.STAFF_NAME,'(',''),')',''),' ',''))";
			
			
			try{
			
				isDS6User = simpleJdbcTemplate.queryForObject(SQL, String.class, staffID);
			
			}catch(Exception e) {
				logger.error("Exception caught in isDS6User()", e);
				throw new DAOException(e.getMessage(), e);
			}
			
			return isDS6User;
		}
		
		public boolean updateImsOrderReasonCd_R014(String orderId, String updateBy) throws DAOException{
			logger.debug("updateSuspendedOrderStatus");
			String SQL = "	UPDATE bomweb_order	"+
			"	   SET reason_cd = 'R014',	"+
			"	       last_update_by = ?,	"+
			"	       last_update_date = SYSDATE	"+
			"	 WHERE LOB = 'IMS'	"+
			"	   AND order_status = '08'	"+
			"	   AND order_id = ? 	" +
			"	   AND order_type = 'NTV-A' 	" +
			"	   AND reason_cd = 'R008' 	";

			
			try {
				int num = simpleJdbcTemplate.update(SQL, updateBy, orderId);
				if (num == 0) {
					return false;
				} else if (num == 1) {
					return true;
				} else {
					logger.error("Num of records updated is not reasonable " + orderId + ": order_status=08, reason_cd=R008");
					throw new DAOException("Num of records updated is not reasonable " + orderId + ": order_status=08, reason_cd=R008");
				}
			} catch (Exception e) {
				logger.error("Exception caught in updateOrderReasonCd_R014()", e);
				throw new DAOException(e.getMessage(), e);
			}
			
		}
		
		public boolean updateImsOrderReasonCd_R008(String orderId, String updateBy) throws DAOException{
			logger.debug("updateSuspendedOrderStatus");
			
			String SQL = null;
			if ("IMSAUTO".equals(updateBy)) {
				SQL = "	UPDATE bomweb_order	"+
					"	   SET reason_cd = 'R008',	"+
					"	       last_update_by = :userName,	"+
					"	       last_update_date = SYSDATE	"+
					"	 WHERE LOB = 'IMS'	"+
					"	   AND order_status = '08'	"+
					"	   AND order_id = :orderId 	" +
					"	   AND order_type = 'NTV-A' 	" +
					"	   AND reason_cd = 'R014' 	";
			} else {
				SQL = "	UPDATE bomweb_order	"+
					"	   SET reason_cd = 'R008',	"+
					"	       last_update_by = :userName,	"+
					"	       last_update_date = SYSDATE	"+
					"	 WHERE LOB = 'IMS'	"+
					"	   AND order_status = '08'	"+
					"	   AND order_id = :orderId 	" +
					"	   AND order_type = 'NTV-A' 	" +
					"	   AND reason_cd = 'R014' 	" +
					"	   AND last_update_by = :userName 	";
			}
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("userName", updateBy);
			params.addValue("orderId", orderId);
			
			try {
				int num = simpleJdbcTemplate.update(SQL, params);
				if (num == 0) {
					return false;
				} else if (num == 1) {
					return true;
				} else {
					logger.error("Num of records updated is not reasonable " + orderId + ": order_status=08, reason_cd=R014");
					throw new DAOException("Num of records updated is not reasonable " + orderId + ": order_status=08, reason_cd=R014");
				}
			} catch (Exception e) {
				logger.error("Exception caught in updateOrderReasonCd_R008()", e);
				throw new DAOException(e.getMessage(), e);
			}
			
		}
		
		public void updateImsOrderLast2Hours_R014() throws DAOException{
			logger.debug("updateSuspendedOrderStatus");
			String SQL = "	UPDATE bomweb_order	"+
			"	   SET reason_cd = 'R008',	"+
			"	       last_update_by = 'IMSAUTO',	"+
			"	       last_update_date = SYSDATE	"+
			"	 WHERE LOB = 'IMS'	"+
			"	   AND order_status = '08'	"+
			"	   AND order_type = 'NTV-A' 	" +
			"	   AND reason_cd = 'R014' 	" +
			"	   AND sign_off_date >= sysdate - 30 	" +
			"	   AND last_update_date <= sysdate - 2/24 ";

			try {
				simpleJdbcTemplate.update(SQL);				
			} catch (Exception e) {
				logger.error("Exception caught in updateImsOrderLast2Hours_R014()", e);
				throw new DAOException(e.getMessage(), e);
			}
		}
}
