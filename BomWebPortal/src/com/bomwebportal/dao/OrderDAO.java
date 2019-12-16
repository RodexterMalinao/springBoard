package com.bomwebportal.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.ListUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.core.support.AbstractLobCreatingPreparedStatementCallback;
import org.springframework.jdbc.support.lob.DefaultLobHandler;
import org.springframework.jdbc.support.lob.LobCreator;
import org.springframework.jdbc.support.lob.LobHandler;

import com.bomwebportal.dto.AccountDTO;
import com.bomwebportal.dto.ActualUserDTO;
import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.dto.BomWebMupDTO;
import com.bomwebportal.dto.ComponentDTO;
import com.bomwebportal.dto.CNMRTSupportDocDTO;
import com.bomwebportal.dto.MnpDTO;
import com.bomwebportal.dto.MobileSimInfoDTO;
import com.bomwebportal.dto.MultiSimInfoDTO;
import com.bomwebportal.dto.MultiSimInfoMemberDTO;
import com.bomwebportal.dto.MultipleMrtSimDTO;
import com.bomwebportal.dto.OrderDTO;
import com.bomwebportal.dto.OrderDTO.CollectMethod;
import com.bomwebportal.dto.OrderDTO.DisMode;
import com.bomwebportal.dto.OrderDTO.EsigEmailLang;
import com.bomwebportal.dto.PaymentDTO;
import com.bomwebportal.dto.SubscriberDTO;
import com.bomwebportal.dto.VasDetailDTO;
import com.bomwebportal.dto.VasMrtSelectionDTO;
import com.bomwebportal.dto.VasOnetimeAmtDTO;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.mob.ccs.dto.SalesInfoDTO;
import com.bomwebportal.mob.ccs.dto.StockDTO;
import com.bomwebportal.mob.ccs.dto.ui.MobCcsFalloutUI;
import com.bomwebportal.mob.ccs.util.BomWebPortalCcsConstant;
import com.bomwebportal.mob.ds.dto.MobDsPaymentTransDTO;
import com.bomwebportal.util.BomWebPortalConstant;
import com.bomwebportal.util.Utility;
import com.jcraft.jsch.Logger;
import com.bomwebportal.dto.ClubPointDetailDTO;
import com.pccw.bom.mob.schemas.ProductDTO;

import oracle.jdbc.OracleTypes;

public class OrderDAO extends BaseOrderDAO {

	protected final Log logger = LogFactory.getLog(getClass());

	// add by Eliot 20110831
	private LobHandler lobHandler;

	public LobHandler getLobHandler() {
		return lobHandler;
	}

	public void setLobHandler(LobHandler lobHandler) {
		this.lobHandler = lobHandler;
	}
	
	public List<ProductDTO> getBomProductList(String orderId)
			throws DAOException {
		List<ProductDTO> productList = new ArrayList<ProductDTO>();

		String SQL = "select ioa.item_id,\n" + "       ioa.offer_id,\n"
				+ "       ioa.offer_sub_id,\n" + "       ioa.offer_type,\n"
				+ "       ioa.select_qty    OFFER_QTY,\n"
				+ "       iopa.product_id,\n" + "       iopa.product_type,\n"
				+ "       iopa.select_qty   PRODUCT_QTY,\n"
				+ "       ippa.compt_id,\n" + "       ippa.pos_item_cd\n"
				+ "  from w_item_offer_assgn         ioa,\n"
				+ "       w_item_offer_product_assgn iopa,\n"
				+ "       w_item_product_pos_assgn   ippa\n"
				+ " where ioa.item_id = iopa.item_id\n"
				+ "   and ioa.item_offer_seq = iopa.item_offer_seq\n"
				+ "   and ippa.item_id(+) = iopa.item_id\n"
				+ "   and ippa.item_offer_seq(+) = iopa.item_offer_seq\n"
				+ "   and ippa.item_product_seq(+) = iopa.item_product_seq\n"
				+ "   and ioa.item_id in\n" + "       (select id\n"
				+ "          from bomweb_subscribed_item bsi\n"
				+ "         where bsi.order_id = ?)";

		ParameterizedRowMapper<ProductDTO> mapper = new ParameterizedRowMapper<ProductDTO>() {
			public ProductDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				ProductDTO product = new ProductDTO();
				product.setOfferId(rs.getString("offer_id"));
				product.setOfferType(rs.getString("offer_type"));
				product.setProdId(rs.getString("product_id"));
				product.setProdType(rs.getString("product_type"));
				product.setIoInd("I");
				return product;
			}
		};

		try {
			logger.debug("getBomProductList @ OrderDAO: " + SQL);

			productList = simpleJdbcTemplate.query(SQL, mapper, orderId);

		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException");
			productList = null;
		} catch (Exception e) {
			logger.error("Exception caught in getBomProductList()", e);
			throw new DAOException(e.getMessage(), e);
		}
		return productList;

	}

	public void updateBomWebPaymentTransStatus(String orderId, String status)
			throws DAOException {
		logger.debug("updateBomWebPaymentTransStatus is called");
		String SQL = "update bomweb_payment_trans " + "set trans_status=?, "
				+ "last_upd_by='SBMDS', " + "last_upd_date=sysdate "
				+ "where order_id=? ";
		try {
			simpleJdbcTemplate.update(SQL, status, orderId);
		} catch (DataAccessException e) {
			logger.error(
					"Exception caught in updateBomWebPaymentTransStatus()", e);
		}
	}

	public void insertBomWebPaymentTrans(MobDsPaymentTransDTO dto)
			throws DAOException {
		logger.debug("insertBomWebPaymentTrans is called");

		String SQL = "insert into bomweb_payment_trans (order_id, msisdn, third_party_ind, "
				+ "pay_mtd_type, cc_num, cc_exp_date, cc_issue_bank, cc_inst_schedule, "
				+ "approve_code, trans_date, trans_status, payment_amount, lob, "
				+ "create_by, create_date, last_upd_by, last_upd_date, store_cd, payment_shop_cd, "
				+ "payment_item_cd, invoice_no, remark, cc_hold_name, cc_type"
				+ ") values (?, ?, ?, "
				+ "?, ?, ?, ?, ?, "
				+ "?, to_date(?, 'DD/MM/YYYY'), ?, ?, 'MOB', "
				+ "?, ?, 'SBMDS', sysdate, ?, ?, "
				+ "?, ?, ?, ?, ?)";

		try {
			if (dto.getCreateBy() == null || dto.getCreateBy().length() == 0) {
				dto.setCreateBy("SBMDS");
				dto.setCreateDate(new Date());
			}
			if ("M".equals(dto.getPaymentType())) {
				simpleJdbcTemplate.update(SQL, dto.getOrderId(),
						dto.getMsisdn(), dto.getThirdPartyInd(),
						dto.getPaymentType(), null, null, null, null,
						dto.getApproveCode(), dto.getTransDate(), "SETTLED",
						dto.getPaymentAmount(), dto.getCreateBy(), dto.getCreateDate(), dto.getStoreCd(),
						dto.getPaymentStoreCd(), dto.getPaymentItemCd(),
						dto.getInvoiceNo(), dto.getRemark(), null, null);
			} else if ("C".equals(dto.getPaymentType())) {
				simpleJdbcTemplate.update(
						SQL,
						dto.getOrderId(),
						dto.getMsisdn(),
						dto.getThirdPartyInd(),
						dto.getPaymentType(),
						dto.getCcNum(),
						((dto.getCcExpiryMonth() == null || dto
								.getCcExpiryYear() == null) ? null : dto
								.getCcExpiryMonth()
								+ "/"
								+ dto.getCcExpiryYear()), dto.getCcIssueBank(),
						null, dto.getApproveCode(), dto.getTransDate(),
						"SETTLED", dto.getPaymentAmount(), dto.getCreateBy(), dto.getCreateDate(), dto.getStoreCd(),
						null, dto.getPaymentItemCd(), dto.getInvoiceNo(), dto
								.getRemark(), dto.getCcHolderName(), dto
								.getCcType());
			} else if ("I".equals(dto.getPaymentType())) {
				simpleJdbcTemplate.update(
						SQL,
						dto.getOrderId(),
						dto.getMsisdn(),
						dto.getThirdPartyInd(),
						dto.getPaymentType(),
						dto.getCcNum(),
						((dto.getCcExpiryMonth() == null || dto
								.getCcExpiryYear() == null) ? null : dto
								.getCcExpiryMonth()
								+ "/"
								+ dto.getCcExpiryYear()), dto.getCcIssueBank(),
						dto.getCcInstSchedule(), dto.getApproveCode(), dto
								.getTransDate(), "SETTLED", dto
								.getPaymentAmount(), dto.getCreateBy(), dto.getCreateDate(), dto.getStoreCd(), null,
						dto.getPaymentItemCd(), dto.getInvoiceNo(), dto
								.getRemark(), dto.getCcHolderName(), dto
								.getCcType());
			}
		} catch (Exception e) {
			logger.error("Exception caught in insertBomWebPayment()", e);
			e.printStackTrace();
			// throw new DAOException(e.getMessage(), e);
		}
	}

	public void insertBomWebPayment(PaymentDTO dto) throws DAOException {
		logger.debug("insertBomWebPayment is called");

		String SQL = "insert into bomweb_payment (order_id, pay_mtd_key, pay_mtd_type, third_party_ind, "
				+ "cc_type, cc_num, cc_hold_name, cc_exp_date, cc_issue_bank, cc_id_doc_type, cc_id_doc_no, "
				+ "b_acct_hold_id_type ,b_acct_hold_id_num ,bank_cd ,branch_cd ,b_acct_hold_name, "
				+ "autopay_up_lim_amt ,b_acct_no ,autopay_app_date, "
				+ "CC_VERIFIED_IND, BYPASS_IND,"
				+ "create_date) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?,?,?,?,?,to_date(?, 'DD/MM/YYYY'),?,?, sysdate)";

		try {

			logger.info("dto.getCreditCardDocNum(): "
					+ dto.getCreditCardDocNum());
			simpleJdbcTemplate.update(SQL, dto.getOrderId(),
					dto.getPayMethodKey(), dto.getPayMethodType(),
					dto.getThirdPartyInd(), dto.getCreditCardType(),
					dto.getCreditCardNum(), dto.getCreditCardHolderName(),
					dto.getCreditExpiryDate(), dto.getCreditCardIssueBankCd(),
					dto.getCreditCardDocType(), dto.getCreditCardDocNum(),
					dto.getBankAcctHolderIdType(),// autopay:b_acct_hold_id_type
					dto.getBankAcctHolderIdNum(),// autopay:b_acct_hold_id_num
					dto.getBankCode(), // autopay:bank_cd
					dto.getBranchCode(), // autopay:branch_cd
					dto.getBankAcctHolderName(),// autopay:b_acct_hold_name
					dto.getAutopayUpperLimitAmt(),// autopay:autopay_up_lim_amt
					dto.getBankAcctNum(),// autopay:b_acct_no
					dto.getAutopayApplDateStr(),// autopay:autopay_app_date
					dto.getCreditCardVerifiedInd(), // CC_VERIFIED_IND
					(dto.isByPassValidation() == true ? "Y" : "N")// BYPASS_IND
					);

		} catch (Exception e) {
			logger.error("Exception caught in insertBomWebPayment()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}

	public void insertBomWebAccount(AccountDTO dto) throws DAOException {
		logger.debug("insertBomWebAccount is called");

		String SQL = "insert into bomweb_acct\n" + "  (order_id,\n" + "   acct_name,\n" + "   bill_freq,\n"
				+ "   bill_lang,\n" + "   sms_no,\n" + "   email_addr,\n" + "   bill_period,\n" + "   create_date,\n"
				+ "brand,\n" + "acct_no,\n" + "is_new,\n" + "cust_no,\n" + "same_as_cust_ind,\n"
				+ "exist_active_mobile_no)\n" + "values\n" + "  (?,\n" + "   ?,\n" + "   ?,\n" + "   ?,\n" + "   ?,\n"
				+ "   ?,\n" + "   ?,\n" + "   sysdate, ?, ?, ?, ?, ?, ?)";

		try {

			simpleJdbcTemplate.update(SQL, dto.getOrderId(), // v_order_id,
					dto.getAcctName(),// / v_acct_name,
					dto.getBillFreq(),// v_bill_freq,
					dto.getBillLang(), // v_bill_lang,
					dto.getSmsNum(), // v_sms_no,
					dto.getEmailAddr(), // v_email_addr,
					dto.getBillPeriod(),// add by wilson 20110228
					dto.getBrand(), 
					dto.getAcctNum(), 
					dto.getIsNew(), 
					dto.getMobCustNum(), 
					dto.getSameAsCustInd(),
					dto.getSrvNum()); // exist_active_mobile_no

		} catch (Exception e) {
			logger.error("Exception caught in insertBomWebAccount()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}

	// modify by eliot 20110627
	// modify by herbert 20110707, for advance order
	// modify by eliot 20110829
	// modify by wilson 20110208, add checkpoint
	// modify by erichui 20120727, add supportDoc
	// modify by wilson 20130529 , add ONLINE_REQ_ID
	public void insertBomWebOrder(OrderDTO dto) throws DAOException {
		logger.debug("insertBomWebOrder is called");

		String SQL = "insert into bomweb_order\n"
				+ "  (order_id,\n"
				+ "   ocid,\n"
				+ "   err_cd,\n"
				+ "   err_msg,\n"
				+ "   src,\n"
				+ "   order_type,\n"
				+ "   msisdn,\n"
				+ "   msisdnlvl,\n"
				+ "   mnp_ind,\n"
				+ "   shop_cd,\n"
				+ "   bom_cust_no,\n"
				+ "   mob_cust_no,\n"
				+ "   acct_no,\n"
				+ "   srv_req_date,\n"
				+ "   agree_num,\n"
				+ "   app_date,\n"
				+ "   sales_type,\n"
				+ "   sales_cd,\n"
				+ "   sales_contact_num,\n"
				+ "   dep_waive,\n"
				+ "   on_hold_ind,\n"
				+ "   on_hold_rea_cd,\n"
				+ "   imei,\n"
				+ "   sales_name, \n"
				+ "   warr_start_date,\n"
				+ "   warr_period,\n"
				+ "   super_app_ind,\n"
				+ "   order_app_ind,\n"
				+ "   paymt_chk_ind,\n"
				+ "   paymt_rec_date,\n"
				+ "   order_status,\n"
				+ "   AO_IND,\n"
				+ "   create_date, last_update_by, last_update_date, lob, check_point, CLONE_ORDER_ID"
				+ " , dis_mode, collect_method, esig_email_addr, esig_email_lang,BOM_CREATION_DATE, ONLINE_REQ_ID"
				+ ")\n" + "values\n" + "  (?,\n" + "   ?,\n" + "   ?,\n"
				+ "   ?,\n" + "   ?,\n" + "   ?,\n" + "   ?,\n" + "   ?,\n"
				+ "   ?,\n" + "   ?,\n" + "   ?,\n" + "   ?,\n" + "   ?,\n"
				+ "   ?,\n" + "   ?,\n" + "   ?,\n" + "   ?,\n" + "   ?,\n"
				+ "   ?,\n" + "   ?,\n" + "   ?,\n" + "   ?,\n" + "   ?,\n"
				+ "   ?,\n" + "   ?,\n" + "   ?,\n" + "   ?,\n" + "   ?,\n"
				+ "   ?,\n" + "   ?,\n" + "   ?,\n" + "   ?,\n" + "   ?,\n"
				+ "?,\n" + "	sysdate, ?, ? ,?" + " , ?, ?, ?, ?,?, ?" + ")";

		// insert to table BOMWEB_CUSTOMER
		try {
			simpleJdbcTemplate.update(SQL,
					dto.getOrderId(),// v_order_id,
					dto.getOcid(),// v_ocid,--add by wilson 20120523
					dto.getErrorCode(), dto.getErrorMessage(),
					dto.getSource(),// v_src,
					dto.getBusTxnType(),// v_order_type,
					dto.getMsisdn(),// v_msisdn,
					dto.getMsisdnLvl(),// v_msisdnlvl,
					dto.getMnpInd(),// v_mnp_ind,
					dto.getShopCode(),// v_shop_cd,
					dto.getBomCustNum(),// v_bom_cust_no,
					dto.getMobCustNum(),// v_mob_cust_no,
					dto.getAcctNum(),// v_acct_no,
					dto.getSrvReqDate(),// v_srv_req_date,
					dto.getAgreementNum(),// v_agree_num,
					dto.getAppInDate(),
					dto.getSalesType(),// v_sales_type,
					dto.getSalesCd(),// v_sales_cd,
					dto.getSalesContactNum(),// v_sales_contact_num,
					dto.getDepositWaiveInd(),// v_dep_waive,
					dto.getOnHoldInd(),// v_on_hold_ind,
					dto.getOnHoldReaCd(),// v_on_hold_rea_cd,
					dto.getImei(),// v_imei,
					dto.getSalesName(),
					dto.getWarrantyStartDate(),// v_warr_start_date,
					dto.getWarrantPeriod(),// v_warr_period,
					dto.getSuperAppInd(), dto.getOrderAppInd(), dto
							.getPaymentCheck(), dto.getPaymtRecDate(),
					dto.getOrderStatus(),// v_order_status,
					dto.getAoInd(), // v_ao_ind
					dto.getCreateDate(), dto.getLastUpdateBy(),
					dto.getOrderSumLob(),
					dto.getCheckPoint(), dto.getCloneOrderId(), dto
							.getDisMode() == null ? null : dto.getDisMode()
							.toString(), dto.getCollectMethod() == null ? null
							: dto.getCollectMethod().toString(), dto
							.getEsigEmailAddr(),
					dto.getEsigEmailLang() == null ? null : dto
							.getEsigEmailLang().toString(), dto
							.getBomCreationDate(), dto.getReqId() == 0 ? null
							: dto.getReqId());

		} catch (Exception e) {
			logger.error("Exception caught in insertBomWebOrder()", e);
			throw new DAOException(e.getMessage(), e);
		}

	}

	public void insertBomWebSub(SubscriberDTO dto) throws DAOException {
		logger.debug("insertBomWebSub is called");
		String SQL = "insert into bomweb_sub\n"
				+ "  (order_id,\n"
				+ "   sms_lang,\n"
				+ "   ivrs_lang,\n"
				+ "   ad_sup_ind,\n"
				+ "   pwd,\n"
				+ "   pccw_sup_ind,\n"
				+ "   tel_mk_sup_ind,\n"
				+ "   bypass_ad_pwd_ind,\n"
				+ "   child_lock,\n"
				+ "   email_sup_ind,\n"
				+ "   sms_sup_ind,\n"
				+ "   dm_sup_ind,\n"
				+ "   sip_ind,\n"
				+ "   addr_pf_ind,\n"
				+ "   addr_pf_ref,\n"
				+ "   sub_tier,\n"
				+ "   doc_copy_ind,\n"
				+ "   usr_sa_cust_ind,\n"
				+ "   create_date,\n"
				+ "   PRIVACY_IND, PRIVACY_STAMP_DATE, SUPPRESS_CUST_LOCAL_TOPUP, SUPPRESS_CUST_ROAM_TOPUP, MOB0060_OPT_OUT_IND, ORIG_ACT_DATE,\n"
				+ "   activation_cd,\n"
				+ "   brand,\n"		
				+ "   pcrf_alert_email,\n"
				+ "   same_as_ebill_addr_ind,\n"
				+ "   sms_opt_out_first_roam,\n"
				+ "   sms_opt_out_roam_hu,\n"
				+ "   pcrf_mup_alert,\n"
				+ "   pcrf_sms_num,\n"
				+ "   pcrf_sms_recipient,\n"
				+ "   pcrf_alert_type,\n"
				+ "   sec_srv_num,\n"
				+ "   old_sec_srv_num)\n"
				+ "values\n" + "  (?,\n" + "   ?,\n" + "   ?,\n" + "   ?,\n"
				+ "   ?,\n" + "   ?,\n" + "   ?,\n" + "   ?,\n" + "   ?,\n"
				+ "   ?,\n" + "   ?,\n" + "   ?,\n" + "   ?,\n" + "   ?,\n"
				+ "   ?,\n" + "   ?,\n" + "   ?,\n" + "   ?,\n"
				+ "   sysdate, ?, ?, ?, ?, ? , ?, ?, ?, ?, ?, ?, ?, ? , ?, ?, ?, ?, ?)";

		try {
			simpleJdbcTemplate.update(
					SQL,
					dto.getOrderId(),// v_order_id,
					dto.getSmsLang(),// v_sms_lang,
					dto.getIvrsLang(),// v_ivrs_lang,
					dto.getAdultSuppressValue(),// v_ad_sup_ind,
					dto.getPwd(),// v_pwd,
					dto.getPccwSuppressValue(),// v_pccw_sup_ind,
					dto.getTelemkSuppressValue(),// v_tel_mk_sup_ind,
					dto.getBypassAdultPwdValue(),// v_bypass_ad_pwd_ind,
					dto.getChildLockValue(),// v_child_lock,
					dto.getEmailSuppressValue(),// v_email_sup_ind,
					dto.getSmsSuppressValue(),// v_sms_sup_ind,
					dto.getDmSuppressValue(),// v_dm_sup_ind,
					dto.getSipValue(),// v_sip_ind,
					dto.getAddrProofInd(),// v_addr_pf_ind,
					dto.getAddrProofReferrer(),// v_addr_pf_ref,
					dto.getSubTier(),// v_sub_tier,
					dto.getDocCopyInd(),// v_doc_copy_ind,
					dto.getActUsrSameAsCustInd(),// v_usr_sa_cust_ind,
					dto.getPrivacyInd(), dto.getPrivacyStampDate(),
					dto.getSuppressLocalTopUpInd(),
					dto.getSuppressRoamTopUpInd(), dto.getMob0060OptOutInd(),
					dto.getOrigActDate(),
					dto.getActivationCd(),
					dto.getBrand(),

					dto.getPcrfAlertEmail(),
					dto.getSameAsEbillAddrInd(),
					dto.getSmsOptOutFirstRoam(),
					dto.getSmsOptOutRoamHu(),
					dto.getPcrfMupAlert(),
					dto.getPcrfSmsNum(),
					dto.getPcrfSmsRecipient(),
					dto.getPcrfAlertType(),
					
					dto.getSecSrvNum(),
					dto.getOldSecSrvNum()
					);
		} catch (Exception e) {
			logger.error("Exception caught in insertBomWebSub()", e);
			throw new DAOException(e.getMessage(), e);
		}

	}

	public void insertBomWebMnp(MnpDTO dto) throws DAOException {
		logger.debug("insertBomWebMnp is called");

		String SQL = "insert into bomweb_mnp\n" + "  (order_id,\n"
				+ "   cut_over_date,\n" + "   cut_over_time,\n" + "   rno,\n"
				+ "   dno,\n" + "   act_dno,\n" + "   cust_name,\n" + "   doc_no,\n"
				+ "   mnp_ticket_num,\n"
				+ "   create_date, CUST_NAME_CHI, PREPAID_SIM_DOC_IND,OPSS_IND,STARTER_PACK)\n"
				+ "values\n" + "  (?,\n" + "   ?,\n" + "   ?,\n" + "   ?,\n"
				+ "   ?,\n" + "   ?,\n" + "   ?,\n" + "   ?,\n" + "   ?,\n"
				+ "   sysdate, ?, ?, ?, ?)";

		try {
			simpleJdbcTemplate.update(SQL, dto.getOrderId(),// (v_order_id,
					dto.getCutoverDate(),// v_cut_over_date,
					dto.getCutoverTime(),// v_cut_over_time,
					dto.getRno(),// v_rno,
					dto.getDno(),// v_dno,
					dto.getActualDno(), //DENNIS MIP3
					dto.getCustName(),// v_cust_name,
					dto.getCustIdDocNum(),// v_doc_no,
					dto.getMnpTicketNum(), // add by wilson 20110225
					dto.getCustNameChi(),// add by wilson 20120718
					dto.getPrePaidSimDocInd(),// add by wilson 20120718
					dto.getOpssInd(),
					dto.getStarterPack()
					);

		} catch (Exception e) {
			logger.error("Exception caught in insertBomWebMnp()", e);
			throw new DAOException(e.getMessage(), e);
		}

	}

	public void insertBomWebMobActualUser(ActualUserDTO dto) throws DAOException {
		logger.debug("insertBomWebMobActualUser is called");

		String SQL = "insert into bomweb_mob_actual_user\n" 
				+ "  (order_id,\n"
				+ "   order_type,\n" 
				+ "   member_num,\n" 
				+ "   sub_doc_num,\n"
				+ "   sub_doc_type,\n" 
				+ "   sub_company_name,\n" 
				+ "   sub_title,\n"
				+ "   sub_last_name,\n"
				+ "   sub_first_name,\n"
				+ "   sub_contact_tel,\n"
				+ "   sub_email_addr,\n"
				+ "   sub_date_of_birth,\n"
				+ "   create_date)\n"
				+ "values\n" 
				+ "  (:orderId,\n" + "   :orderType,\n" + "   :memberNum,\n" + "   :subDocNum,\n"
				+ "   :subDocType,\n" + "   :subCompanyName,\n" + "   :subTitle,\n" + "   :subLastName,\n"
				+ "   :subFirstName,\n" + "   :subContactTel,\n" + "   :subEmailAddr,\n" + "   to_date(:subDateOfBirth, 'DD/MM/YYYY'), \n"
				+ "   sysdate)";
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("orderId", dto.getOrderId());
		params.addValue("orderType", dto.getOrderType());
		params.addValue("memberNum", dto.getMemberNum());
		params.addValue("subDocNum", dto.getSubDocNum());
		params.addValue("subDocType", dto.getSubDocType());
		params.addValue("subCompanyName", dto.getSubCompanyName());	
		params.addValue("subTitle", dto.getSubTitle());	
		params.addValue("subLastName", dto.getSubLastName());	
		params.addValue("subFirstName", dto.getSubFirstName());	
		params.addValue("subContactTel", dto.getSubContactTel());	
		params.addValue("subEmailAddr", dto.getSubEmailAddr());	
		params.addValue("subDateOfBirth", dto.getSubDateOfBirthStr());	
		
		try {
			simpleJdbcTemplate.update(SQL, params);

		} catch (Exception e) {
			logger.error("Exception caught in insertBomWebMobActualUser()", e);
			throw new DAOException(e.getMessage(), e);
		}

	}


	public void insertBomWebSim(MobileSimInfoDTO dto) throws DAOException {
		logger.debug("insertBomWebSim is called");
		String SQL = "insert into bomweb_sim\n" + "  (order_id,\n"
				+ "  iccid,\n" + "  imsi,\n" + "  puk1,\n" + "  puk2,\n"
				+ "  item_code,\n" + "  create_date, sim_type)\n" + "values\n"
				+ "  (?,\n" + "  ?,\n" + "  ?,\n" + "  ?,\n" + "  ?,\n"
				+ "  ?,\n" + "  sysdate,\n" + " ?)";

		try {
			simpleJdbcTemplate.update(SQL, dto.getOrderId(),// v_order_id,
					dto.getIccid(),// v_iccid,
					dto.getImsi(),// v_imsi,
					dto.getPuk1(),// v_puk1,
					dto.getPuk2(),// v_puk2,
					dto.getItemCd(),// v_item_code
					dto.getSimBrandType()
					);

		} catch (Exception e) {
			logger.error("Exception caught in insertBomWebSim()", e);
			throw new DAOException(e.getMessage(), e);
		}

	}

	public void insertBomWebSubscribedItem(String orderId, String itemId,
			String itemType, List exclusiveItemIdList, String simWaiveReason, String rpWaiveReason, String sysAssignInd,
			String mipBrand, String mipSimType) throws DAOException {
		logger.debug("insertBomWebSim is called");

		if (!"BASKET".equals(itemType)) {
			if (!"-9999".equals(itemId)) {
				String SQL ="insert into bomweb_subscribed_item\n"
						+ "  ("
						+ "	  order_id\n"
						+ "  ,id\n"
						+ "  ,mip_brand\n"
						+ "  ,mip_sim_type\n"
						+ "  ,type\n"
						+ "  ,create_date"
						+ "  ,sys_assgn_ind\n"
						+ ")\n" 
						+ "  select :orderId, I.ID, NVL(I.MIP_BRAND, '1') MIP_BRAND, NVL(I.MIP_SIM_TYPE, 'H') MIP_SIM_TYPE, I.TYPE, sysdate, :sysAssignInd\n"
						+ "    from W_ITEM I\n"
						+ "   where I.ID = :itemId\n"
						+ "     and I.TYPE = :itemType\n"
						+ "     and nvl(decode (I.MIP_BRAND, '9', :mipBrand, I.MIP_BRAND ), '1') = :mipBrand \n"
						+ "     and nvl(decode (I.MIP_SIM_TYPE, 'X', :mipSimType, I.MIP_SIM_TYPE ), 'H') = :mipSimType ";
				
				MapSqlParameterSource params = new MapSqlParameterSource();
				params.addValue("orderId", orderId);
				params.addValue("sysAssignInd", sysAssignInd);
				params.addValue("itemId", itemId);
				params.addValue("itemType", itemType);
				params.addValue("mipBrand", mipBrand);
				params.addValue("mipSimType", mipSimType);
				
				try {
					simpleJdbcTemplate.update(SQL, params);

				} catch (Exception e) {
					logger.error(
							"Exception caught in insertBomWebSubscribedItem()",
							e);
					throw new DAOException(e.getMessage(), e);
				}
			}
		} else {

			insertBomWebBasketSubscribedItem(orderId, itemId,
					exclusiveItemIdList, simWaiveReason, rpWaiveReason, 
					mipBrand, mipSimType);

		}

	}

	// add by wilson 20110126, INSERT A TYPE OF ID TO bomweb_subscribed_item
	public void insertBomWebSubscribedItembyItemType(String orderId,
				String basketId, String itemType, 
			String mipBrand, String mipSimType) throws DAOException {
		logger.debug("insertBomWebSubscribedItembyItemType is called");

		// type: MNP_INC /AP_INC

		// MNP_INC
		if ("MNP_INC".equals(itemType)) {
			insertBomWebSubscribedItemMNPItem(orderId, basketId, itemType, mipBrand, mipSimType);
			return;
		} else if ("AP_INC".equals(itemType)) {

			// type: AP_INC
			String SQL = "insert into bomweb_subscribed_item\n"
					+ "  (order_id, id, MIP_BRAND, MIP_SIM_TYPE, type, BASKET_ID, create_date)\n"
					+ "  SELECT ?, i.ID, NVL(I.MIP_BRAND, '1') MIP_BRAND, NVL(I.MIP_SIM_TYPE, 'H') MIP_SIM_TYPE, i.TYPE, bia.basket_id, sysdate\n"
					+ "    FROM w_basket_item_assgn bia,\n"
					+ "         w_item i,\n"
					+ "         (SELECT o.order_id, o.app_date eff_date  FROM bomweb_order o WHERE o.order_id = ?) d\n"
					+ "   WHERE bia.item_id = i.ID\n"
					+ "     and bia.basket_id = ?\n"
					+ "     AND i.type = ?\n"
					+ "     AND bia.eff_start_date <= d.eff_date\n"
					+ "     AND (bia.eff_end_date >= d.eff_date OR bia.eff_end_date IS NULL)\n"
					+ "     and nvl(decode (i.MIP_BRAND, '9', ?, i.MIP_BRAND ), '1') = ? \n"
					+ "     and nvl(decode (I.MIP_SIM_TYPE, 'X', ?, I.MIP_SIM_TYPE ), 'H') = ? ";

			try {

				simpleJdbcTemplate.update(SQL, orderId, orderId, basketId,
						itemType, mipBrand, mipBrand, mipSimType, mipSimType); // edit by wilson 20110211, change the date
									// to app_date

			} catch (Exception e) {
				logger.error(
						"Exception caught in insertBomWebSubscribedItembyItemType()",
						e);
				throw new DAOException(e.getMessage(), e);
			}
		}

	}

	public void insertBomWebBasketSubscribedItem(String orderId,
			String basketId, List exclusiveItemIdList, String simWaiveReason, String rpWaiveReason,
			String mipBrand, String mipSimType) throws DAOException {
		logger.debug("insertBomWebBasketSubscribedItem is called");

		/*
		 * String SQL= "insert into bomweb_subscribed_item\n" +
		 * "  (order_id, id, type, create_date)\n" + "  SELECT ?,\n" +
		 * "         i.ID,\n" + "         i.TYPE,\n" + "         sysdate\n" +
		 * "    FROM w_basket_item_assgn bia,\n" + "         w_item i,\n" +
		 * "         (SELECT sysdate eff_date FROM DUAL) d\n" +
		 * "   WHERE bia.item_id = i.ID\n" + "     and bia.basket_id = ?\n" +
		 * "     AND bia.eff_start_date <= d.eff_date\n" +
		 * "     AND (bia.eff_end_date >= d.eff_date OR bia.eff_end_date IS NULL)"
		 * ;
		 */
		/*
		 * String SQL = "insert into bomweb_subscribed_item\n" +
		 * "  (order_id, id, type, BASKET_ID, create_date)\n" +
		 * "  SELECT ?, i.ID, i.TYPE, bia.basket_id, sysdate\n" +
		 * "    FROM w_basket_item_assgn bia,\n" + "         w_item i,\n" +
		 * "         (SELECT sysdate eff_date FROM DUAL) d\n" +
		 * "   WHERE bia.item_id = i.ID\n" + "     and bia.basket_id = ?\n" +
		 * "     AND i.type not in ('MNP_INC', 'AP_INC')" + // add by wilson
		 * 20110126 "     AND bia.eff_start_date <= d.eff_date\n" +
		 * "     AND (bia.eff_end_date >= d.eff_date OR bia.eff_end_date IS NULL)"
		 * ; // add // basketId // 20100125
		 */
		// edit by wilson 20121005, use replace sysdate to appDate
			String SQL = "insert into BOMWEB_SUBSCRIBED_ITEM \n"
				+ "  (ORDER_ID, ID, MIP_BRAND, MIP_SIM_TYPE, type, BASKET_ID, CREATE_DATE, WAIVE_REASON)\n"
				+ "  select O.ORDER_ID, I.ID, NVL(I.MIP_BRAND, '1') MIP_BRAND, NVL(I.MIP_SIM_TYPE, 'H') MIP_SIM_TYPE, I.TYPE, BIA.BASKET_ID, sysdate, decode(i.type, 'SIM', ?, 'RP', ?, null)\n"
				+ "    from W_BASKET_ITEM_ASSGN BIA, W_ITEM I, BOMWEB_ORDER O\n"
				+ "   where BIA.ITEM_ID = I.ID\n" 
				+ "     and O.ORDER_ID = ?\n"
				+ "     and BIA.BASKET_ID = ?\n"
				+ "     and I.TYPE not in ('MNP_INC', 'AP_INC')\n"
				+ "     and TRUNC(O.APP_DATE) between BIA.EFF_START_DATE and\n"
				+ "         TRUNC(NVL(BIA.EFF_END_DATE, sysdate)) \n"
				+ "     and nvl(decode (I.MIP_BRAND, '9', ?, I.MIP_BRAND ), '1') = ? \n"
				+ "     and nvl(decode (I.MIP_SIM_TYPE, 'X', ?, I.MIP_SIM_TYPE ), 'H') = ? ";

		if (exclusiveItemIdList.size() > 0) {
			String appendSQL = " AND i.ID not in (";
			for (int i = 0; i < exclusiveItemIdList.size(); i++) {
				appendSQL = (new StringBuilder(String.valueOf(appendSQL)))
						.append((String) exclusiveItemIdList.get(i))
						.append(",").toString();
			}

			appendSQL = (new StringBuilder(String.valueOf(appendSQL))).append(
					")").toString();
			appendSQL = appendSQL.replace(",)", ")");
			SQL = (new StringBuilder(String.valueOf(SQL))).append(appendSQL)
					.toString();
		}
		// herbert 20111110 - remove useless SQL logger
		logger.debug((new StringBuilder("SQL: ")).append(SQL).toString());
		// logger.info((new StringBuilder("SQL: ")).append(SQL).toString());
		try {
			simpleJdbcTemplate.update(SQL, simWaiveReason,rpWaiveReason, orderId, basketId, mipBrand, mipBrand, mipSimType, mipSimType);

		} catch (Exception e) {
			logger.error(
					"Exception caught in insertBomWebBasketSubscribedItem()", e);
			throw new DAOException(e.getMessage(), e);
		}

	}

	public void deleteBomWebAllOrder(String orderId) throws DAOException {
		logger.debug("deleteBomWebAllOrder is called");
		deleteBomWebSub(orderId);
		deleteBomWebMnp(orderId);
		deleteBomWebSim(orderId);
		deleteBomWebOrdMobItem(orderId);
		deleteBomWebSubscribedItem(orderId);

		deleteBomWebPayment(orderId);
		deleteBomWebAccount(orderId);
		deleteBomWebComponent(orderId);

		deleteBomWebMobCcsMRT(orderId);
		deleteBomWebMobCcsSupportDoc(orderId);
		deleteBomWebMobCcsUpfrontPayment(orderId);
		deleteBomWebMobCcsStaffInfo(orderId);
		deleteBomWebDelivery(orderId);// add by wilson 20120210
		deleteBomWebCustQuotaInUse(orderId); // add by wilson 20120215
		deleteBomWebPaymentTransDS(orderId);
		deleteBomWebMember(orderId);
		deleteBomWebMemberVAS(orderId);

		deleteBomWebOrder(orderId);
		logger.info("deleteBomWebAllOrder is finished");
	}

	private void deleteBomWebOrdMobItem(String orderId) throws DAOException {
		logger.debug("deleteBomWebOrdMobItem is called");
		String sql = "delete from bomweb_ord_mob_item omi where omi.order_id = ?";
		try {
			simpleJdbcTemplate.update(sql, orderId);

		} catch (Exception e) {
			logger.error("Exception caught in deleteBomWebOrdMobItem()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}

	// delete function
	public void deleteBomWebPaymentTransDS(String orderId) throws DAOException {
		logger.debug("deleteBomWebPaymentTransDS is called");
		String SQL = "delete bomweb_payment_trans where order_id =? and order_id like 'D%'";

		try {
			simpleJdbcTemplate.update(SQL, orderId);

		} catch (Exception e) {
			logger.error("Exception caught in deleteBomWebPaymentTransDS()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}

	public void deleteBomWebMember(String orderId) throws DAOException {
		logger.debug("deleteBomWebMember is called");
		String SQL = "delete bomweb_ord_mob_member where parent_order_id =?";

		try {
			simpleJdbcTemplate.update(SQL, orderId);

		} catch (Exception e) {
			logger.error("Exception caught in deleteBomWebMember()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}

	public void deleteBomWebMemberVAS(String orderId) throws DAOException {
		logger.debug("deleteBomWebMemberVAS is called");
		String SQL = "delete bomweb_ord_mob_member_vas where parent_order_id =?";

		try {
			simpleJdbcTemplate.update(SQL, orderId);

		} catch (Exception e) {
			logger.error("Exception caught in deleteBomWebMemberVAS()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}

	public void deleteBomWebComponent(String orderId) throws DAOException {
		logger.debug("deleteBomWebComponent is called");
		String SQL = "delete  bomweb_component where order_id =?";

		try {
			simpleJdbcTemplate.update(SQL, orderId);

		} catch (Exception e) {
			logger.error("Exception caught in deleteBomWebComponent()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}

	private void deleteBomWebOrder(String orderId) throws DAOException {
		logger.debug("deleteBomWebOrder is called");

		String SQL = "delete  bomweb_order where order_id =?";

		try {
			simpleJdbcTemplate.update(SQL, orderId);

		} catch (Exception e) {
			logger.error("Exception caught in deleteBomWebOrder()", e);
			throw new DAOException(e.getMessage(), e);
		}

	}

	private void deleteBomWebPayment(String orderId) throws DAOException {
		logger.debug("deleteBomWebPayment is called");
		String SQL = "delete bomweb_payment where order_id= ?";

		try {
			simpleJdbcTemplate.update(SQL, orderId);

		} catch (Exception e) {
			logger.error("Exception caught in deleteBomWebPayment()", e);
			throw new DAOException(e.getMessage(), e);
		}

	}

	private void deleteBomWebSub(String orderId) throws DAOException {
		logger.debug("deleteBomWebSub is called");
		String SQL = "delete bomweb_sub where order_id= ?";

		try {
			simpleJdbcTemplate.update(SQL, orderId);

		} catch (Exception e) {
			logger.error("Exception caught in deleteBomWebSub()", e);
			throw new DAOException(e.getMessage(), e);
		}

	}

	private void deleteBomWebMnp(String orderId) throws DAOException {
		logger.debug("deleteBomWebMnp is called");
		String SQL = "delete bomweb_mnp where order_id= ?";

		try {
			simpleJdbcTemplate.update(SQL, orderId);

		} catch (Exception e) {
			logger.error("Exception caught in deleteBomWebMnp()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}

	private void deleteBomWebSim(String orderId) throws DAOException {
		logger.debug("deleteBomWebSim is called");
		String SQL = "delete bomweb_sim where order_id= ?";

		try {
			simpleJdbcTemplate.update(SQL, orderId);

		} catch (Exception e) {
			logger.error("Exception caught in deleteBomWebSim()", e);
			throw new DAOException(e.getMessage(), e);
		}

	}

	private void deleteBomWebSubscribedItem(String orderId) throws DAOException {
		logger.debug("deleteBomWebSubscribedItem is called");
		String SQL = "delete bomweb_subscribed_item where order_id= ?";

		try {
			simpleJdbcTemplate.update(SQL, orderId);

		} catch (Exception e) {
			logger.error("Exception caught in deleteBomWebSubscribedItem()", e);
			throw new DAOException(e.getMessage(), e);
		}

	}

	private void deleteBomWebAccount(String orderId) throws DAOException {
		logger.debug("deleteBomWebAccount is called");
		String SQL = "delete bomweb_acct where order_id= ?";

		try {
			simpleJdbcTemplate.update(SQL, orderId);

		} catch (Exception e) {
			logger.error("Exception caught in deleteBomWebAccount()", e);
			throw new DAOException(e.getMessage(), e);
		}

	}

	public void deleteDsBomWebStockAssgn(String orderId) throws DAOException {
		logger.debug("deleteBomWebStockAssgn is called");
		String SQL = "delete bomweb_stock_assgn where order_id =? and order_id like 'D%'";
		try {
			simpleJdbcTemplate.update(SQL, orderId);
		} catch (Exception e) {
			logger.error("Exception caught in deleteBomWebStockAssgn()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}

	// modify by eliot 20110816 for prevent diplicate order
	// add sign_off_date
	public void updateBomWebOrderStatus(String orderId, String status)
			throws DAOException {
		logger.debug("updateBomWebOrderStatus is called");
		logger.info("update orderID : " + orderId + "; status : " + status);

		String SQL = "update bomweb_order set order_status = ?,  sign_off_date = sysdate, last_update_by = 'AutoProcess', last_update_date = sysdate where order_id = ? "
				+ "and order_status = '"
				+ BomWebPortalConstant.BWP_ORDER_INITIAL + "'";

		try {
			simpleJdbcTemplate.update(SQL, status, orderId);

		} catch (Exception e) {
			logger.error("Exception caught in updateBomWebOrderStatus()", e);
			throw new DAOException(e.getMessage(), e);
		}

	}

	public void updateBomWebOrderStatusDS(String orderId, String status,
			String supervisor_approve, String mobsupport_approve)
			throws DAOException {
		logger.debug("updateBomWebOrderStatusDS is called");
		logger.info("update orderID : " + orderId + "; status : " + status);
		
		String SQL = "update bomweb_order set order_status = ?, super_app_ind = ?, order_app_ind = ?, "
				+ "last_update_by = 'SBMDS', last_update_date = sysdate where order_id = ? ";

		try {
			simpleJdbcTemplate.update(SQL, status, supervisor_approve,
					mobsupport_approve, orderId);

		} catch (Exception e) {
			logger.error("Exception caught in updateBomWebOrderStatusDS()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}

	public void updateBomWebOrderStatusDS(String orderId, String status,
			String supervisor_approve, String mobsupport_approve,
			String checkPoint) throws DAOException {
		logger.debug("updateBomWebOrderStatusDS is called");
		logger.info("update orderID : " + orderId + "; status : " + status);

		String SQL = "update bomweb_order set order_status = ?, super_app_ind = ?, order_app_ind = ?, check_point = ?, "
				+ "last_update_by = 'SBMDS', last_update_date = sysdate where order_id = ? ";

		try {
			simpleJdbcTemplate.update(SQL, status, supervisor_approve,
					mobsupport_approve, checkPoint, orderId);

		} catch (Exception e) {
			logger.error("Exception caught in updateBomWebOrderStatusDS()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}

	// add eliot 20110816
	// modify by Eliot 04102011
	public void updateBomWebOrderStatusToProcess(String orderId, String status)
			throws DAOException {
		logger.debug("updateBomWebOrderStatus is called");
		logger.info("update orderID : " + orderId + "; status : " + status);

		String SQL = "update bomweb_order set order_status = ?, last_update_by = 'AutoProcess', last_update_date = sysdate where order_id = ? ";

		try {
			simpleJdbcTemplate.update(SQL, status, orderId);

		} catch (Exception e) {
			logger.error("Exception caught in updateBomWebOrderStatus()", e);
			throw new DAOException(e.getMessage(), e);
		}

	}

	public long getHandsetDeviceAmount(String orderId) throws DAOException {

		logger.debug("getHandsetDeviceAmount is called");
		String SQL = "select sum(ip.onetime_amt) hs_amount\n"
				+ "  from bomweb_subscribed_item i, w_item_pricing ip, bomweb_order bo\n"
				+ " where i.id = ip.id\n"
				+ "   and i.order_id = bo.order_id\n"
				+ "   and trunc(bo.app_date) between ip.eff_start_date and nvl(ip.eff_end_date, sysdate)\n"
				+ "   and i.type = 'HS'\n" + "   and i.order_id = ?";

		try {
			return simpleJdbcTemplate.queryForLong(SQL, orderId);

		} catch (Exception e) {
			logger.error("Exception caught in getHandsetDeviceAmount()", e);
			throw new DAOException(e.getMessage(), e);
		}

	}

	// 20110722 (not include BFEE)
	public long getFirstMonthFee(String orderId) throws DAOException {
		logger.debug("getFirstMonthFee is called");
		String SQL = "select sum(ip.onetime_amt) first_month_fee\n"
				+ "  from bomweb_subscribed_item i, w_item_pricing ip, bomweb_order bo\n"
				+ " where i.id = ip.id\n"
				+ "   and i.order_id = bo.order_id\n"
				+ "   and trunc(bo.app_date) between ip.eff_start_date and nvl(ip.eff_end_date, sysdate)\n"
				// + "   and i.type not in ( 'HS' , 'BFEE') and i.order_id = ?";
				+ "   and i.type = 'RP' and i.order_id = ?\n"
				+ "   and i.waive_reason is null";

		try {
			return simpleJdbcTemplate.queryForLong(SQL, orderId);

		} catch (Exception e) {
			logger.error("Exception caught in getFirstMonthFee()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}

	public long getPrepaymentWithoutHandset(String orderId) throws DAOException {
		logger.debug("getPrepaymentWithoutHandset is called");
		/*String SQL = "select sum(ip.onetime_amt) pre_payment\n"
				+ "  from bomweb_subscribed_item i, w_item_pricing ip, bomweb_order bo\n"
				+ " where i.id = ip.id\n"
				+ "   and i.order_id = bo.order_id\n"
				+ "   and trunc(bo.app_date) between ip.eff_start_date and nvl(ip.eff_end_date, sysdate)\n"
				+ "   and i.type not in ('HS') and i.order_id = ?\n"
				+ "   and i.waive_reason is null";*/
		
		String SQL=" SELECT NVL(SUM(ONETIME_AMT),0) PREPAYMENT " 
					+"FROM BOMWEB_SUBSCRIBED_ITEM A, " 
					+"  W_ITEM_PRICING B, " 
					+"  BOMWEB_ORDER BO " 
					+"WHERE A.ORDER_ID= ? \n " 
					+"AND A.ID=B.ID " 
					+"AND A.ORDER_ID=BO.ORDER_ID " 
					+"AND TRUNC(BO.APP_DATE) BETWEEN TRUNC(NVL(B.EFF_START_DATE,SYSDATE)) AND TRUNC(NVL(B.EFF_END_DATE,SYSDATE)) " 
					+"AND NVL(B.ONETIME_TYPE,'#')!='A' " 
					+"AND NVL(B.PAYMENT_GROUP,'#')!='HANDSET' "
					+"AND A.WAIVE_REASON IS NULL ";

		try {
			return simpleJdbcTemplate.queryForLong(SQL, orderId);

		} catch (Exception e) {
			logger.error("Exception caught in getPrepaymentWithoutHandset()", e);
			throw new DAOException(e.getMessage(), e);
		}

	}

	// 20110722 (only BFEE)
	public long getFirstMonthServiceLicenceFee(String orderId)
			throws DAOException {

		logger.debug("getFirstMonthServiceLicenceFee is called");
		String SQL = "select sum(ip.onetime_amt) first_month_fee\n"
				+ "  from bomweb_subscribed_item i, w_item_pricing ip, bomweb_order bo\n"
				+ " where i.id = ip.id\n"
				+ "   and i.order_id = bo.order_id\n"
				+ "   and trunc(bo.app_date) between ip.eff_start_date and nvl(ip.eff_end_date, sysdate)\n"
				+ "   and i.type = 'BFEE'\n" + "   and i.order_id = ?";

		try {
			return simpleJdbcTemplate.queryForLong(SQL, orderId);

		} catch (Exception e) {
			logger.error(
					"Exception caught in getFirstMonthServiceLicenceFee()", e);
			throw new DAOException(e.getMessage(), e);
		}

	}

	public List<VasOnetimeAmtDTO> getVasOnetimeAmtList(String locale,
			String orderId) throws DAOException {

		String SQL = "SELECT isp.html item_desc, "
				+ "  ip.onetime_amt first_month_fee "
				+ "FROM bomweb_subscribed_item i, " + "  w_item_pricing ip , "
				+ "  w_item_display_lkup isp, bomweb_order o "
				+ "WHERE i.ID                 = ip.ID "
				+ "AND i.ID                   = isp.item_id "
				+ "AND I.ORDER_ID             = O.ORDER_ID "
				+ "AND ISP.DISPLAY_TYPE       ='SS_FORM_VAS' "
				+ "AND isp.locale             = :locale "
				+ "AND trunc(o.app_date)     between ip.eff_start_date "
				+ "                          and nvl(ip.eff_end_date, sysdate) \n "
				+ "AND I.type                IN ('BVAS','VAS') "
				+ "AND O.ORDER_ID             = :ORDER_ID "
				+ "AND NVL(IP.ONETIME_AMT,0) <> 0";

		ParameterizedRowMapper<VasOnetimeAmtDTO> mapper = new ParameterizedRowMapper<VasOnetimeAmtDTO>() {
			public VasOnetimeAmtDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				VasOnetimeAmtDTO temp = new VasOnetimeAmtDTO();
				temp.setItemDesc(rs.getString("item_desc"));
				temp.setVasOnetimeAmt(rs.getString("first_month_fee"));
				return temp;
			}
		};

		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("locale", locale);
		params.addValue("ORDER_ID", orderId);

		try {
			logger.debug("getVasOnetimeAmtList@ OrderDAO: " + SQL);
			return simpleJdbcTemplate.query(SQL, mapper, params);

		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException");

		} catch (Exception e) {
			logger.error("Exception caught in getVasOnetimeAmtList()", e);
			throw new DAOException(e.getMessage(), e);
		}
		return null;

	}
	
	public List<VasOnetimeAmtDTO> getRetChgSimAdmChargeList(String locale, String orderId) throws DAOException {

		String SQL = "SELECT D.description, T.admin_charge "
				+ "FROM BOMWEB_ORD_MOB_CHG_SIM_TXN T, BOMWEB_CODE_LKUP CL, W_DISPLAY_LKUP D "
				+ "WHERE (T.ORDER_ID = :orderId "
				+ "       OR T.ORDER_ID in (SELECT M.MEMBER_ORDER_ID "
				+ "                         FROM BOMWEB_ORD_MOB_MEMBER M "
				+ "                         WHERE PARENT_ORDER_ID = :orderId)) "
				+ "AND NVL(T.MARK_DEL_IND,'N')='N' "
				+ "AND T.WAIVE_REASON_CD IS NULL "
				+ "AND T.STATUS IN ('D','P') "
				+ "AND CL.CODE_TYPE = 'MOB_RET_SIM_ADM_CHANGE_REPORT_LKUP' "
				+ "AND CL.CODE_ID = D.ID "
				+ "AND D.TYPE = 'MOB_REPORT' "
				+ "AND D.LOCALE = :locale ";

		ParameterizedRowMapper<VasOnetimeAmtDTO> mapper = new ParameterizedRowMapper<VasOnetimeAmtDTO>() {
			public VasOnetimeAmtDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				VasOnetimeAmtDTO temp = new VasOnetimeAmtDTO();
				temp.setItemDesc(rs.getString("description"));
				temp.setVasOnetimeAmt(rs.getString("admin_charge"));
				return temp;
			}
		};

		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("locale", locale);
		params.addValue("orderId", orderId);

		try {
			logger.debug("getRetChgSimAdmChargeList@ OrderDAO: " + SQL);
			return simpleJdbcTemplate.query(SQL, mapper, params);

		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException");

		} catch (Exception e) {
			logger.error("Exception caught in getRetChgSimAdmChargeList()", e);
			throw new DAOException(e.getMessage(), e);
		}
		return null;
	}
	
	public List<VasOnetimeAmtDTO> getRetToo1AdmChargeList(String locale, String orderId) throws DAOException {
		String SQL = "SELECT D.description, nvl(M.too1_admin_charge,0) as too1_admin_charge "
				+ "FROM BOMWEB_ORD_MOB_MEMBER M, BOMWEB_CODE_LKUP CL, W_DISPLAY_LKUP D "
				+ "WHERE M.PARENT_ORDER_ID = :orderId "
				+ "AND M.TOO1_IND = 'Y' "
				+ "AND M.TOO1_WAIVE_REASON_CD IS NULL "
				+ "AND CL.CODE_TYPE = 'MOB_RET_TOO1_ADM_CHANGE_REPORT_LKUP' "
				+ "AND CL.CODE_ID = D.ID "
				+ "AND D.TYPE = 'MOB_REPORT' "
				+ "AND D.LOCALE = :locale";

		ParameterizedRowMapper<VasOnetimeAmtDTO> mapper = new ParameterizedRowMapper<VasOnetimeAmtDTO>() {
			public VasOnetimeAmtDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				VasOnetimeAmtDTO temp = new VasOnetimeAmtDTO();
				temp.setItemDesc(rs.getString("description"));
				temp.setVasOnetimeAmt(rs.getString("too1_admin_charge"));
				return temp;
			}
		};

		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("locale", locale);
		params.addValue("orderId", orderId);

		try {
			logger.debug("getRetToo1AdmChargeList@ OrderDAO: " + SQL);
			return simpleJdbcTemplate.query(SQL, mapper, params);

		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException");

		} catch (Exception e) {
			logger.error("Exception caught in getRetToo1AdmChargeList()", e);
			throw new DAOException(e.getMessage(), e);
		}
		return null;

	}

	public List<MobDsPaymentTransDTO> getPaymentTransList(String orderId)
			throws DAOException {
		String SQL = "SELECT * FROM bomweb_payment_trans WHERE order_id=:ORDER_ID order by trans_date, payment_amount desc";
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("ORDER_ID", orderId);

		ParameterizedRowMapper<MobDsPaymentTransDTO> mapper = new ParameterizedRowMapper<MobDsPaymentTransDTO>() {
			public MobDsPaymentTransDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				MobDsPaymentTransDTO row = new MobDsPaymentTransDTO();
				row.setOrderId(rs.getString("ORDER_ID"));
				row.setMsisdn(rs.getString("MSISDN"));
				row.setPaymentType(rs.getString("pay_mtd_type"));
				row.setCcNum(rs.getString("CC_NUM"));
				try {
					String[] temp = rs.getString("CC_EXP_DATE").split("/");
					row.setCcExpiryMonth(temp[0]);
					row.setCcExpiryYear(temp[1]);
				} catch (Exception e) {

				}
				row.setCcIssueBank(rs.getString("cc_issue_bank"));
				row.setCcInstSchedule(rs.getInt("cc_inst_schedule"));
				row.setApproveCode(rs.getString("approve_code"));
				Date transDate = rs.getDate("trans_date");
				if (transDate != null) {
					row.setTransDate(transDate.getDate() + "/"
							+ (transDate.getMonth() + 1) + "/"
							+ (transDate.getYear() + 1900));
				}
				row.setTransStatus(rs.getString("trans_status"));
				row.setPaymentAmount(rs.getDouble("payment_amount"));
				row.setStoreCd(rs.getString("store_cd"));
				row.setPaymentItemCd(rs.getString("payment_item_cd"));
				row.setPaymentStoreCd(rs.getString("payment_shop_cd"));
				row.setInvoiceNo(rs.getString("invoice_no"));
				row.setRemark(rs.getString("remark"));
				row.setCcHolderName(rs.getString("cc_hold_name"));
				row.setCcType(rs.getString("cc_type"));
				row.setThirdPartyInd(rs.getString("third_party_ind"));
				row.setCreateDate(rs.getDate("create_date"));
				row.setCreateBy(rs.getString("create_by"));
				return row;
			}
		};

		try {
			return simpleJdbcTemplate.query(SQL, mapper, params);
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public List<VasDetailDTO> getRebateList(String locale, String orderId)
			throws DAOException {
		List<VasDetailDTO> rebateList = new ArrayList<VasDetailDTO>();

		// edit by wilson 20110228
		/*
		 * String SQL = " SELECT bsi.order_id, i.ID, i.TYPE, \n" +
		 * "           DECODE (i.TYPE, \n" +
		 * "                   'HSRB', DECODE (idrrv.rp_type, 1, 'Smartphone', 2, 'Tablet', 'HandSet'), \n"
		 * + "                   'MNP_INC', 'MNP', \n" +
		 * "                   'AP_INC', 'Autopay', \n" +
		 * "                   'Extra' \n" + "                  ) \n" +
		 * "        || ': ' \n" +
		 * "        || NVL (idrrv.rebate_schedule, ' ') rebate_schedule, \n" +
		 * "        NVL (TO_CHAR (idrrv.rebate_amt), '0') rebate_amt \n" +
		 * "   FROM w_item i, w_item_dtl_rp_rb_vas idrrv, bomweb_subscribed_item bsi \n"
		 * + "  WHERE i.ID = idrrv.ID \n" + "    AND i.ID = bsi.ID \n" +
		 * "    AND idrrv.rebate_amt IS NOT NULL \n" +
		 * "    AND idrrv.rebate_amt <> 0 \n" + "      AND bsi.order_id = ? \n";
		 */
		// edit rebate schedule text 20110722
		String SQL = " SELECT bsi.order_id, i.ID, i.TYPE, \n"
				+ " NVL (idrrv.rebate_schedule, ' ') rebate_schedule, \n"
				+ "        NVL (TO_CHAR (idrrv.rebate_amt), '0') rebate_amt \n"
				+ "   FROM w_item i, w_item_dtl_rp_rb_vas idrrv, bomweb_subscribed_item bsi \n"
				+ "  WHERE i.ID = idrrv.ID \n" + "    AND i.ID = bsi.ID \n"
				+ "    AND idrrv.rebate_amt IS NOT NULL \n"
				+ "    AND idrrv.rebate_amt <> 0 \n"
				+ "      AND bsi.order_id = ? \n";

		String SQL_en = " SELECT bsi.order_id, i.ID, i.TYPE, \n"
				+ " NVL (idrrv.rebate_schedule, ' ') rebate_schedule, \n"
				+ "        NVL (TO_CHAR (idrrv.rebate_amt), '0') rebate_amt \n"
				+ "   FROM w_item i, w_item_dtl_rp_rb_vas idrrv, bomweb_subscribed_item bsi \n"
				+ "  WHERE i.ID = idrrv.ID \n" + "    AND i.ID = bsi.ID \n"
				+ "    AND idrrv.rebate_amt IS NOT NULL \n"
				+ "    AND idrrv.rebate_amt <> 0 \n"
				+ "      AND bsi.order_id = ? \n";

		String SQL_chi = " SELECT bsi.order_id, i.ID, i.TYPE, \n"
				+ " NVL (idrrv.rebate_schedule_chi, ' ') rebate_schedule, \n"
				+ "        NVL (TO_CHAR (idrrv.rebate_amt), '0') rebate_amt \n"
				+ "   FROM w_item i, w_item_dtl_rp_rb_vas idrrv, bomweb_subscribed_item bsi \n"
				+ "  WHERE i.ID = idrrv.ID \n" + "    AND i.ID = bsi.ID \n"
				+ "    AND idrrv.rebate_amt IS NOT NULL \n"
				+ "    AND idrrv.rebate_amt <> 0 \n"
				+ "      AND bsi.order_id = ? \n";

		ParameterizedRowMapper<VasDetailDTO> mapper = new ParameterizedRowMapper<VasDetailDTO>() {

			public VasDetailDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				VasDetailDTO rebate = new VasDetailDTO();
				rebate.setItemId(rs.getString("id"));
				rebate.setItemType(rs.getString("type"));
				rebate.setItemHtml(rs.getString("rebate_schedule"));
				rebate.setItemRebateAmt(rs.getString("rebate_amt"));
				return rebate;
			}
		};

		try {

			if ("en".equals(locale)) {
				logger.debug("getRebateList en@ OrderDAO: " + SQL_en);
				rebateList = simpleJdbcTemplate.query(SQL_en, mapper, orderId);
			} else {
				logger.debug("getRebateList chi@ OrderDAO: " + SQL_chi);
				rebateList = simpleJdbcTemplate.query(SQL_chi, mapper, orderId);
			}

			// rebateList = simpleJdbcTemplate.query(SQL, mapper, orderId);
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException");
			rebateList = null;
		} catch (Exception e) {
			logger.error("Exception caught in getRebateList()", e);
			throw new DAOException(e.getMessage(), e);
		}
		return rebateList;

	}

	// get idrrv.contract_period, idrrv.penalty_type,idrrv.penalty_amt
	public String[] getPenaltyInfoList(String orderId) throws DAOException {
		List<String[]> selectList = new ArrayList<String[]>();
		String SQL = "";

		SQL = "select bsi.order_id,\n"
				+ "       idrrv.contract_period,\n"
				+ "       idrrv.penalty_type,\n"
				+ "       idrrv.penalty_amt\n"
				+ "  from w_item i, w_item_dtl_rp_rb_vas idrrv, bomweb_subscribed_item bsi\n"
				+ " where i.id = idrrv.id\n" + "   and i.id = bsi.id\n"
				+ "   AND i.TYPE = 'RP'\n" + "   and bsi.order_id = ?";

		ParameterizedRowMapper<String[]> mapper = new ParameterizedRowMapper<String[]>() {

			public String[] mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				String[] brand = new String[3];
				brand[0] = rs.getString("contract_period");
				brand[1] = rs.getString("penalty_type");
				brand[2] = rs.getString("penalty_amt");
				return brand;
			}
		};

		try {
			logger.debug("getPenaltyInfoList : " + SQL);
			selectList = simpleJdbcTemplate.query(SQL, mapper, orderId);
			if (selectList != null && selectList.size() > 0) {
				return selectList.get(0);
			} else {
				return new String[3];
			}

		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException caught in getPenaltyInfoList()");
			selectList = null;
		} catch (Exception e) {
			logger.error("Exception caught in getPenaltyInfoList()", e);
			throw new DAOException(e.getMessage(), e);
		}

		return null;

	}

	public List<StockDTO> getStockAssignment(String orderId)
			throws DAOException {
		logger.debug("getStockAssignment is called");

		String sql = "select C.CODE_DESC ITEM_TYPE, A.ITEM_CODE, B.ITEM_DESC, NVL(A.ITEM_SERIAL_CCS_REAL, A.ITEM_SERIAL) ITEM_SERIAL , "
				+ "A.DOA_OLD_ITEM_CODE, A.DOA_OLD_ITEM_SERIAL, A.SEQ "
				+ "from BOMWEB_STOCK_ASSGN A, BOMWEB_STOCK_CATALOG B, BOMWEB_CODE_LKUP C "
				+ "where A.ORDER_ID = ? "
				+ "and A.ITEM_CODE = B.ITEM_CODE "
				+ "and NVL(A.STATUS_ID, 0) <> '24' "
				+ "and C.CODE_TYPE = 'STOCK_TYPE' "
				+ "and C.CODE_ID = B.ITEM_TYPE" + " order by A.ITEM_CODE";

		ParameterizedRowMapper<StockDTO> mapper = new ParameterizedRowMapper<StockDTO>() {

			public StockDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				StockDTO stock = new StockDTO();
				stock.setType(rs.getString("item_type"));
				stock.setItemCode(rs.getString("item_code"));
				stock.setItemDesc(rs.getString("item_desc"));
				stock.setItemSerial(rs.getString("item_serial"));
				stock.setDoaItemCode(rs.getString("DOA_OLD_ITEM_CODE"));
				stock.setDoaItemSerial(rs.getString("DOA_OLD_ITEM_SERIAL"));
				stock.setSeq(rs.getString("SEQ"));
				return stock;
			}
		};

		try {
			logger.debug("getStockAssignment : " + sql);
			return simpleJdbcTemplate.query(sql, mapper, orderId);

		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException");

		} catch (Exception e) {
			logger.error("Exception caught in getStockAssignment()", e);
			throw new DAOException(e.getMessage(), e);
		}

		return null;
	}
	
	public List<StockDTO> getParentOrderStockAssignment(String orderId)
			throws DAOException {
		logger.debug("getParentOrderStockAssignment is called");

		String sql = "select C.CODE_DESC ITEM_TYPE, A.ITEM_CODE, B.ITEM_DESC, NVL(A.ITEM_SERIAL_CCS_REAL, A.ITEM_SERIAL) item_serial "
				+ "from BOMWEB_STOCK_ASSGN A, BOMWEB_STOCK_CATALOG B, BOMWEB_CODE_LKUP C "
				+ "where A.ORDER_ID = ? "
				+ "and A.ITEM_CODE = B.ITEM_CODE "
				+ "and NVL(A.STATUS_ID, 0) <> '24' "
				+ "and C.CODE_TYPE = 'STOCK_TYPE' "
				+ "and C.CODE_ID = B.ITEM_TYPE " 
				+ "and member_num is null "
				+ " order by A.ITEM_CODE";

		ParameterizedRowMapper<StockDTO> mapper = new ParameterizedRowMapper<StockDTO>() {

			public StockDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				StockDTO stock = new StockDTO();
				stock.setType(rs.getString("item_type"));
				stock.setItemCode(rs.getString("item_code"));
				stock.setItemDesc(rs.getString("item_desc"));
				stock.setItemSerial(rs.getString("item_serial"));
				return stock;
			}
		};

		try {
			logger.debug("getParentOrderStockAssignment : " + sql);
			return simpleJdbcTemplate.query(sql, mapper, orderId);

		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException");

		} catch (Exception e) {
			logger.error("Exception caught in getParentOrderStockAssignment()", e);
			throw new DAOException(e.getMessage(), e);
		}

		return null;
	}

	// add by Eliot 20120509
	public List<StockDTO> getDOAStockAssignment(String orderId)
			throws DAOException {
		logger.debug("getDOAStockAssignment is called");

		String sql = "SELECT bsa.order_id, "
				+ "  bsi.item_type, "
				+ "  bsa.item_code, "
				+ "  bsi.item_desc, "
				+ "  nvl(bsa.item_serial_ccs_real,bsa.item_serial) item_serial, "
				+ "  nvl(BSA.DOA_OLD_ITEM_SERIAL_CCS_REAL,bsa.doa_old_item_serial)doa_old_item_serial "
				+ "FROM bomweb_stock_assgn bsa, "
				+ "  bomweb_stock_catalog bsi "
				+ "WHERE bsa.order_id                           = ? "
				+ "AND bsa.item_code                            = bsi.item_code "
				+ "AND bsa.status_id                            in ('19','35')  "
				+ "AND (bsa.item_code,bsa.DOA_OLD_ITEM_SERIAL_CCS_REAL) IN "
				+ "  (SELECT DISTINCT doa_item_code, " + "    doa_item_serial "
				+ "  FROM bomweb_doa_item bdi, "
				+ "    bomweb_doa_request dbr " + "  WHERE dbr.order_id = ? "
				+ "  AND dbr.status     in( 'N005','N995') "
				+ "  AND dbr.order_id   = bdi.order_id "
				+ "  AND dbr.seq_no     = bdi.seq_no " + "  )";

		ParameterizedRowMapper<StockDTO> mapper = new ParameterizedRowMapper<StockDTO>() {

			public StockDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				StockDTO stock = new StockDTO();
				stock.setType(rs.getString("item_type"));
				stock.setItemCode(rs.getString("item_code"));
				stock.setItemDesc(rs.getString("item_desc"));
				stock.setItemSerial(rs.getString("item_serial"));
				stock.setDoaItemSerial(rs.getString("doa_old_item_serial"));
				return stock;
			}
		};

		try {
			logger.debug("getDOAStockAssignment : " + sql);
			return simpleJdbcTemplate.query(sql, mapper, orderId, orderId);

		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException");

		} catch (Exception e) {
			logger.error("Exception caught in getDOAStockAssignment()", e);
			throw new DAOException(e.getMessage(), e);
		}

		return null;
	}

	public String[] getHandsetDeviceDescription(String locale, String brandId,
			String modelId, String colorId) throws DAOException {
		List<String[]> selectList = new ArrayList<String[]>();
		String SQL = "";

		SQL = "select a.brand_name, b.model_name, c.color_name, NVL(D.ORIGINAL_PRICE, 0) ORIGINAL_PRICE\n"
				+ "   from (SELECT brand.description BRAND_name\n"
				+ "           FROM w_display_lkup brand\n"
				+ "          WHERE brand.type = 'BRAND'\n"
				+ "            and brand.locale = ?\n"
				+ "            and brand.id = ?) a,\n"
				+ "        (SELECT model.description model_name\n"
				+ "           FROM w_display_lkup model\n"
				+ "          WHERE model.type = 'MODEL'\n"
				+ "            and model.locale = ?\n"
				+ "            and model.id = ?) b,\n"
				+ "        (SELECT color.description color_name\n"
				+ "           FROM w_display_lkup color\n"
				+ "          WHERE color.type = 'COLOR'\n"
				+ "            and color.locale = ?\n"
				+ "            and color.id = ?) c,"
				+ "       (select HDL.ORIGINAL_PRICE\n"
				+ "          from W_HS_DISPLAY_LKUP HDL\n"
				+ "         where HDL.BRAND_ID = ?\n"
				+ "           and HDL.MODEL_ID = ?\n"
				+ "           and HDL.LOCALE = ?\n"
				+ "           and HDL.DISPLAY_TYPE = 'DETAIL') D";

		ParameterizedRowMapper<String[]> mapper = new ParameterizedRowMapper<String[]>() {

			public String[] mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				String[] brand = new String[8];
				brand[0] = rs.getString("brand_name");
				brand[1] = rs.getString("model_name");
				brand[2] = rs.getString("color_name");
				brand[7] = rs.getString("ORIGINAL_PRICE");

				return brand;
			}
		};

		try {
			logger.debug("getHandsetDeviceDescription : " + SQL);
			selectList = simpleJdbcTemplate.query(SQL, mapper, locale, brandId,
					locale, modelId, locale, colorId, brandId, modelId, locale);
			selectList.get(0)[3] = brandId;
			selectList.get(0)[4] = modelId;
			selectList.get(0)[5] = colorId;
			selectList.get(0)[6] = selectList.get(0)[0] + " "
					+ selectList.get(0)[1] + " " + selectList.get(0)[2];

			return selectList.get(0);
			// return selectList.get(0)[0] + " " + selectList.get(0)[1] + " " +
			// selectList.get(0)[2];

		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException");
			selectList = null;

		} catch (Exception e) {
			logger.error("Exception caught in getHandsetDeviceDescription()", e);
			throw new DAOException(e.getMessage(), e);
		}

		return null;
	}

	public void updateOrderStatus(String refNum, String status, String errCode,
			String errString, String lastUpdateBy) throws DAOException {

		logger.debug("updateOrderStatus is called");
		logger.info("update refNum : " + refNum + "; status : " + status + "; errString : " + errString);

		String SQL = "update bomweb_order set order_status = ?, err_cd = ?, err_msg = ?, last_update_by = ?, last_update_date = sysdate where order_id = ?";

		try {
			simpleJdbcTemplate.update(SQL, status, errCode, errString,
					lastUpdateBy, refNum);
		} catch (Exception e) {
			logger.error("Exception caught in updateOrderStatus()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}

	// modify by Eliot 20110829
	public void updateBulkReqStatusSuccess(String refNum, String ocid,
			String status, String errCode, String lastUpdateBy)
			throws DAOException {

		logger.debug("updateBulkReqStatusSuccess is called");
		logger.info("update refNum : " + refNum + "; ocid : " + ocid + "; status : " + status + "; errCode : " + errCode);

		String SQL = "update bomweb_order set ocid = ?, order_status = ?, err_cd = ?, last_update_by = 'AutoProcess', last_update_date = sysdate where order_id = ?";

		try {
			simpleJdbcTemplate.update(SQL, ocid, status, errCode, refNum);
		} catch (Exception e) {
			logger.error("Exception caught in updateOrderStatus()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}

	// will remove later 2011-12-19, replace by [public String getShopSeq(String
	// shopCd, String appMode)]
	/*
	 * public String getShopSeq(String shopCd) throws DAOException { String
	 * orderId; String SQL =
	 * "select ?|| LPAD(seq_no, 7, '0') from bomweb_shop where shop_cd = ?";
	 * 
	 * try { orderId = (String) simpleJdbcTemplate.queryForObject(SQL,
	 * String.class, shopCd, shopCd);
	 * 
	 * } catch (Exception e) { throw new DAOException(e.getMessage(), e); }
	 * logger.info("getShopSeq [OrderDAO] Get Order ID: " + orderId); return
	 * orderId; }
	 */

	public void updateShopSeq(String shopCd) throws DAOException {
		String SQL = "update bomweb_shop set seq_no = seq_no+1 where shop_cd = ?";
		try {
			simpleJdbcTemplate.update(SQL, shopCd);
		} catch (Exception e) {
			throw new DAOException(e.getMessage(), e);
		}
	}

	// 20110324 change sql
	private static final String insertBomWebSubscribedItemMNPItemSQL = " INSERT INTO bomweb_subscribed_item \n"
			+ "             (order_id, ID, MIP_BRAND, MIP_SIM_TYPE, TYPE, basket_id, create_date) \n"
			+ "    SELECT distinct ? ,ID, MIP_BRAND, MIP_SIM_TYPE, TYPE, basket_id, SYSDATE                            --order_id-- \n"
			+ "      FROM (SELECT i.ID, i.TYPE, NVL(I.MIP_BRAND, '1') MIP_BRAND, NVL(I.MIP_SIM_TYPE, 'H') MIP_SIM_TYPE, bia.basket_id, ipro.required_item_id, IPRO.EFF_START_DATE, IPRO.EFF_END_DATE , d.eff_date app_date\n"
			+ "              FROM w_basket_item_assgn bia, \n"
			+ "                   w_item i, \n"
			+ "                   (SELECT o.order_id, o.app_date eff_date \n"
			+ "                      FROM bomweb_order o \n"
			+ "                     WHERE o.order_id = ?) d,      --order_id-- \n"
			+ "                   w_item_dtl_rp_rb_vas idrrv, \n"
			+ "                   (SELECT bhhm.basket_id, bhhm.hs_onetime, \n"
			+ "                           bhhm.hsrb_rebate_amt, \n"
			+ "                           (bhhm.hs_onetime - bhhm.hsrb_rebate_amt) net_amt \n"
			+ "                      FROM w_basket_hs_hsrb_mv bhhm, \n"
			+ "                           (SELECT o.order_id, o.app_date eff_date \n"
			+ "                              FROM bomweb_order o \n"
			+ "                             WHERE o.order_id = ?) d --order_id-- \n"
			+ "                     --hs \n"
			+ "                    WHERE  bhhm.hs_eff_start_date <= d.eff_date \n"
			+ "                       AND (   bhhm.hs_eff_end_date >= d.eff_date \n"
			+ "                            OR bhhm.hs_eff_end_date IS NULL \n"
			+ "                           ) \n"
			+ "                       --price \n"
			+ "                       AND bhhm.hs_price_eff_start_date <= d.eff_date \n"
			+ "                       AND (   bhhm.hs_price_eff_end_date >= d.eff_date \n"
			+ "                            OR bhhm.hs_price_eff_end_date IS NULL \n"
			+ "                           ) \n"
			+ "                       --HSRB \n"
			+ "                       AND bhhm.hsrb_eff_start_date <= d.eff_date \n"
			+ "                       AND (   bhhm.hsrb_eff_end_date >= d.eff_date \n"
			+ "                            OR bhhm.hsrb_eff_end_date IS NULL \n"
			+ "                           )) hs_net_amt_t, \n"
			+ "                   w_basket b, \n"
			+ "                   w_item_pre_requisite_or ipro \n"
			+ "             WHERE b.ID = bia.basket_id \n"
			+ "               AND b.TYPE IN (1, 4) \n"
			+ "               AND bia.item_id = i.ID \n"
			+ "               AND bia.item_id = idrrv.ID \n"
			+ "               AND hs_net_amt_t.basket_id = bia.basket_id \n"
			+ "               AND bia.basket_id = ?                 ----basket_id------------ \n"
			+ "               AND i.TYPE = 'MNP_INC' \n"
			+ "               AND bia.eff_start_date <= d.eff_date \n"
			+ "               AND (bia.eff_end_date >= d.eff_date OR bia.eff_end_date IS NULL \n"
			+ "                   ) \n"
			+ "               AND (   idrrv.rebate_amt <= hs_net_amt_t.net_amt \n"
			+ "                    OR (idrrv.rebate_amt > 0 AND hs_net_amt_t.net_amt >= 200) \n"
			+ "                    OR idrrv.rebate_amt IS NULL \n"
			+ "                   ) \n"
			+ "               AND i.ID = ipro.item_id(+) \n"
			+ "				  AND nvl(decode (i.MIP_BRAND, '9', ?, i.MIP_BRAND ), '1') = ? \n"
			+ "     		  AND nvl(decode (i.MIP_SIM_TYPE, 'X', ?, i.MIP_SIM_TYPE ), 'H') = ? \n"
			+ "            UNION \n"
			+ "            SELECT i.ID, i.TYPE, NVL(I.MIP_BRAND, '1') MIP_BRAND, NVL(I.MIP_SIM_TYPE, 'H') MIP_SIM_TYPE, bia.basket_id, ipro.required_item_id, IPRO.EFF_START_DATE, IPRO.EFF_END_DATE, d.eff_date app_date \n"
			+ "              FROM w_basket_item_assgn bia, \n"
			+ "                   w_item i, \n"
			+ "                   w_basket b, \n"
			+ "                   (SELECT o.order_id, o.app_date eff_date \n"
			+ "                      FROM bomweb_order o \n"
			+ "                     WHERE o.order_id = ?) d,      --order_id------- \n"
			+ "                   w_item_pre_requisite_or ipro \n"
			+ "             WHERE b.ID = bia.basket_id \n"
			+ "               AND b.TYPE IN (2, 3) \n"
			+ "               AND bia.item_id = i.ID \n"
			+ "               AND bia.basket_id = ?                ----basket_id------------ \n"
			+ "               AND i.TYPE = 'MNP_INC' \n"
			+ "               AND bia.eff_start_date <= d.eff_date \n"
			+ "               AND (bia.eff_end_date >= d.eff_date OR bia.eff_end_date IS NULL \n"
			+ "                   ) \n"
			+ "               AND i.ID = ipro.item_id(+)"
			+ "				  AND nvl(decode (i.MIP_BRAND, '9', ?, i.MIP_BRAND ), '1') = ? \n"
			+ "     		  AND nvl(decode (i.MIP_SIM_TYPE, 'X', ?, i.MIP_SIM_TYPE ), 'H') = ? )\n"
			+ "     WHERE required_item_id IS NULL \n"
			+ "        OR required_item_id IN (SELECT ID \n"
			+ "                                  FROM bomweb_subscribed_item \n"
			+ "                                 WHERE order_id = ?) --order_id------- \n "
			+ " and app_date between EFF_START_DATE and EFF_end_DATE";

	public void insertBomWebSubscribedItemMNPItem(String orderId,
			String basketId, String itemType,
			String mipBrand, String mipSimType) throws DAOException {
		logger.debug("insertBomWebSubscribedItemMNPItem is called");

		// type: MNP_INC
		// edit sql 20110324
		/*
		 * String SQL = "INSERT INTO bomweb_subscribed_item\n" +
		 * "            (order_id, ID, TYPE, basket_id, create_date)\n" +
		 * "   SELECT ?, i.ID, i.TYPE, bia.basket_id, SYSDATE\n" +
		 * "     FROM w_basket_item_assgn bia,\n" + "          w_item i,\n" +
		 * "          (SELECT o.order_id, o.app_date eff_date  FROM bomweb_order o WHERE o.order_id = ?) d,\n"
		 * + "          w_item_dtl_rp_rb_vas idrrv,\n" +
		 * "          (SELECT bhhm.basket_id, bhhm.hs_onetime, bhhm.hsrb_rebate_amt,\n"
		 * +
		 * "                  (bhhm.hs_onetime - bhhm.hsrb_rebate_amt) net_amt\n"
		 * +
		 * "             FROM w_basket_hs_hsrb_mv bhhm, (SELECT o.order_id, o.app_date eff_date  FROM bomweb_order o WHERE o.order_id = ?) d\n"
		 * + "			--hs\n" +
		 * "           WHERE  bhhm.hs_eff_start_date <= d.eff_date\n" +
		 * "              AND (   bhhm.hs_eff_end_date >= d.eff_date\n" +
		 * "                   OR bhhm.hs_eff_end_date IS NULL\n" +
		 * "                  )\n" + "              --price\n" +
		 * "              AND bhhm.hs_price_eff_start_date <= d.eff_date\n" +
		 * "              AND (   bhhm.hs_price_eff_end_date >= d.eff_date\n" +
		 * "                   OR bhhm.hs_price_eff_end_date IS NULL\n" +
		 * "                  )\n" + "              --HSRB\n" +
		 * "              AND bhhm.hsrb_eff_start_date <= d.eff_date\n" +
		 * "              AND (   bhhm.hsrb_eff_end_date >= d.eff_date\n" +
		 * "                   OR bhhm.hsrb_eff_end_date IS NULL\n" +
		 * "                  )) hs_net_amt_t,\n" + "          w_basket b" +
		 * "    WHERE b.id = bia.basket_id\n" + "      AND b.type in (1, 4)" +
		 * "      AND bia.item_id = i.ID\n" +
		 * "      AND bia.item_id = idrrv.ID\n" +
		 * "      AND hs_net_amt_t.basket_id = bia.basket_id\n" +
		 * "      AND bia.basket_id = ?\n" + "      AND i.TYPE = 'MNP_INC'\n" +
		 * "      AND bia.eff_start_date <= d.eff_date\n" +
		 * "      AND (bia.eff_end_date >= d.eff_date OR bia.eff_end_date IS NULL)\n"
		 * +
		 * "      AND (idrrv.rebate_amt <= hs_net_amt_t.net_amt or (idrrv.rebate_amt > 0 and hs_net_amt_t.net_amt >= 200) or idrrv.rebate_amt is null)\n"
		 * + "UNION\n" + "  SELECT ?, i.ID, i.TYPE, bia.basket_id, sysdate\n" +
		 * "    FROM w_basket_item_assgn bia,\n" + "         w_item i,\n" +
		 * "         w_basket b,\n" +
		 * "         (SELECT o.order_id, o.app_date eff_date  FROM bomweb_order o WHERE o.order_id = ?) d\n"
		 * + "   WHERE b.id = bia.basket_id\n" + "     AND b.type IN (2, 3)\n" +
		 * "      AND bia.item_id = i.ID\n" + "     and bia.basket_id = ?\n" +
		 * "     AND i.type = 'MNP_INC'\n" +
		 * "     AND bia.eff_start_date <= d.eff_date\n" +
		 * "     AND (bia.eff_end_date >= d.eff_date OR bia.eff_end_date IS NULL)"
		 * ;
		 */

		// +
		// "      AND (idrrv.rebate_amt <= hs_net_amt_t.net_amt or idrrv.rebate_amt is null)\n";
		// edit 20110324
		/*--order_id1--
		--order_id2--
		--order_id3--
		----basket_id4------------
		--order_id5-------
		---basket_id6------------
		--order_id7-------*/
		try {
			simpleJdbcTemplate.update(insertBomWebSubscribedItemMNPItemSQL,
					orderId, orderId, orderId, basketId, mipBrand, mipBrand, mipSimType, mipSimType, orderId, basketId,
					mipBrand, mipBrand, mipSimType, mipSimType,
					orderId);

		} catch (Exception e) {
			logger.error(
					"Exception caught in insertBomWebSubscribedItembyItemType()",
					e);
			throw new DAOException(e.getMessage(), e);
		}

	}

	public String getSecSrvContractPeriod(String orderId) throws DAOException {

		String contractPeriod = null;
		logger.info("getSecSrvContractPeriod is called [orderId]: " + orderId);
		// sql add decode , 20110608
		/*
		 * String SQL =
		 * "  SELECT decode(nvl(CONTRACT_PERIOD, '-1'), -1, '-',CONTRACT_PERIOD) CONTRACT_PERIOD FROM W_ITEM_DTL_RP_RB_VAS IDRRV \n"
		 * + " WHERE ID IN (SELECT bsi.id \n" +
		 * "   FROM BOMWEB_SUBSCRIBED_ITEM BSI, W_ITEM_OFFER_ASSGN IOA, W_ITEM_OFFER_PRODUCT_ASSGN IOPA \n"
		 * + "  WHERE BSI.ORDER_ID = ? \n" + "    And bsi.Id = Ioa.Item_Id \n" +
		 * "    AND IOA.ITEM_ID = IOPA.ITEM_ID \n" +
		 * "    AND IOA.ITEM_OFFER_SEQ = IOPA.ITEM_OFFER_SEQ \n" +
		 * "    AND IOPA.PRODUCT_ID IN (SELECT TO_NUMBER(CODE)  \n" +
		 * "    FROM W_CODE_LKUP WHERE GRP_ID = 'SECRETARIAL_PROD_ID')) \n";
		 */
		// 20110811, only load one record
		String SQL = "select CONTRACT_PERIOD\n"
				+ "  from (select DECODE(NVL(CONTRACT_PERIOD, '-1'), -1, '-', CONTRACT_PERIOD) CONTRACT_PERIOD\n"
				+ "          from W_ITEM_DTL_RP_RB_VAS IDRRV\n"
				+ "         where ID in (select BSI.ID\n"
				+ "                        from BOMWEB_SUBSCRIBED_ITEM     BSI,\n"
				+ "                             W_ITEM_OFFER_ASSGN         IOA,\n"
				+ "                             W_ITEM_OFFER_PRODUCT_ASSGN IOPA\n"
				+ "                       where BSI.ORDER_ID = ?\n"
				+ "                         and BSI.ID = IOA.ITEM_ID\n"
				+ "                         and IOA.ITEM_ID = IOPA.ITEM_ID\n"
				+ "                         and IOA.ITEM_OFFER_SEQ = IOPA.ITEM_OFFER_SEQ\n"
				+ "                         and IOPA.PRODUCT_ID in\n"
				+ "                             (select TO_NUMBER(CODE)\n"
				+ "                                from W_CODE_LKUP\n"
				+ "                               where GRP_ID = 'SECRETARIAL_PROD_ID'))\n"
				+ "         order by CONTRACT_PERIOD desc)\n"
				+ " where ROWNUM = 1";

		try {
			contractPeriod = (String) simpleJdbcTemplate.queryForObject(SQL,
					String.class, orderId);

		} catch (EmptyResultDataAccessException e) {

			return null;
		} catch (Exception e) {
			logger.info("Exception caught in getSecSrvContractPeriod()", e);
			throw new DAOException(e.getMessage(), e);
		}
		return contractPeriod;
	}

	public List<OrderDTO> getOrderSummaryList(String shopCode, String dateStr,
			String orderIdStr) throws DAOException {
		List<OrderDTO> orderList = new ArrayList<OrderDTO>();

		// dateStr format in dd/mm/yyyy
		// System.out.println("shopCode:"+shopCode);
		// System.out.println("dateStr:"+dateStr);
		/*
		 * String SQL =
		 * " SELECT bo.order_id, bo.shop_cd, bo.order_status, bo.ocid, bo.app_date  , bo.ERR_MSG\n"
		 * + "   FROM bomweb_order bo \n" +
		 * "  WHERE TRUNC (bo.create_date) = TRUNC (to_date(?, 'dd/mm/yyyy')) AND bo.shop_cd = ? \n"
		 * + "  order by bo.shop_cd,bo.app_date, bo.order_id\n";
		 */
		// add decode, 20110301, for summary page use
		/*
		 * String SQL =
		 * " SELECT bo.order_id, decode(trunc(bo.APP_DATE), trunc(sysdate), 'Y','N') today_order_flag, bo.shop_cd, bo.order_status, bo.ocid, bo.app_date  , bo.ERR_MSG\n"
		 * + "   FROM bomweb_order bo \n" +
		 * "  WHERE TRUNC (bo.create_date) = TRUNC (to_date(?, 'dd/mm/yyyy')) AND bo.shop_cd = ? \n"
		 * + "  order by bo.shop_cd,bo.app_date, bo.order_id\n";
		 */
		// concierge decode(trunc(bo.APP_DATE), trunc(sysdate), 'Y','N')

		// add column LOB, Service Num, IMS Login id, BOM Status)
		String SQLsearchByDate = "SELECT bo.order_id,   \n"
				+ "     DECODE(basket_type_t.basket_type, 6, 'Y', DECODE(TRUNC(bo.APP_DATE), TRUNC(sysdate), 'Y', 'N')) today_order_flag,   \n"
				+ "     bo.shop_cd,   \n"
				+ "     bo.order_status,   \n"
				+ "     bo.ocid,   \n"
				+ "     bo.app_date,   \n"
				+ "     bo.ERR_MSG,   \n"
				+ "     basket_type_t.basket_type,   \n"
				+ "     bo.lob,   \n"
				+ "     DECODE(BO.LOB, 'MOB', BO.MSISDN, BC.SERVICE_NUM) SERVICE_NUM,  \n"
				+ "     boi.login_id IMS_LOGIN_ID,   \n"
				+ "     bo.reason_cd,  \n"
				+ "	   bo.check_point,		\n"
				+ "     BC.LAST_NAME ||' ' ||BC.FIRST_NAME cust_full_name   \n"
				+ "   FROM bomweb_order bo,   \n"
				+ "     (SELECT DISTINCT bsi.order_id,   \n"
				+ "       b.type basket_type   \n"
				+ "     FROM bomweb_subscribed_item bsi,   \n"
				+ "       w_basket b   \n"
				+ "     WHERE b.id = bsi.basket_id  \n"
				+ "     ) basket_type_t,  \n"
				+ "     bomweb_customer bc,   \n"
				+ "     bomweb_order_ims boi   \n"
				+ "   WHERE bo.order_id         = basket_type_t.order_id  \n"
				+ "   AND TRUNC(bo.create_date) = nvl(TRUNC(to_date(?, 'dd/mm/yyyy')),TRUNC(bo.create_date) )  \n"
				+ "   AND bo.shop_cd            = ?   \n"
				+ "   AND bo.ORDER_ID           = bc.ORDER_ID   \n"
				+ "   AND bo.ORDER_ID           = boi.ORDER_ID(+)   \n"
				+ "   AND bo.lob != 'LTS' \n"
				+ "  UNION \n"
				+ "     -- LTS Order \n"
				+ "   SELECT bo.order_id,   \n"
				+ "     DECODE(bols.sb_status, 'S', 'Y', 'N') today_order_flag,  -- allow recall LTS suspend order   \n"
				+ "     bo.shop_cd,   \n"
				+ "     bols.sb_status order_status,   \n"
				+ "     bo.ocid,   \n"
				+ "     bo.app_date,   \n"
				+ "     bo.ERR_MSG,   \n"
				+ "     null BASKET_TYPE,   \n"
				+ "     bo.lob,   \n"
				+ "     bos.srv_num SERVICE_NUM,   \n"
				+ "     null IMS_LOGIN_ID,  \n"
				+ "     bols.rea_cd reason_cd,  \n"
				+ "	   bo.check_point,		\n"
				+ "     BC.LAST_NAME ||' ' ||BC.FIRST_NAME cust_full_name   \n"
				+ "   FROM bomweb_order bo,   \n"
				+ "        bomweb_customer bc,   \n"
				+ "        bomweb_order_service bos, \n"
				+ "        bomweb_order_latest_status bols, \n"
				+ "        bomweb_acct ba     \n"
				+ "   WHERE bo.order_id = bos.order_id \n"
				+ "   AND bos.order_id = bols.order_id \n"
				+ "   AND bos.dtl_id =  bols.dtl_id \n"
				+ "   AND TRUNC(bo.create_date) = nvl(TRUNC(to_date(?, 'dd/mm/yyyy')),TRUNC(bo.create_date) )  \n"
				+ "   AND bo.shop_cd            = ?   \n"
				+ "   AND bos.ORDER_ID = ba.ORDER_ID \n"
				+ "   AND bos.acct_no = ba.acct_no  \n"
				+ "   AND bc.ORDER_ID = ba.ORDER_ID  \n"
				+ "   AND bc.cust_no = ba.cust_no  \n"
				+ "   AND bo.lob = 'LTS' \n" + "   ORDER BY order_id ";

		String SQLsearchByOrderId = "SELECT bo.order_id,   \n"
				+ "     DECODE(basket_type_t.basket_type, 6, 'Y', DECODE(TRUNC(bo.APP_DATE), TRUNC(sysdate), 'Y', 'N')) today_order_flag,   \n"
				+ "     bo.shop_cd,   \n"
				+ "     bo.order_status,   \n"
				+ "     bo.ocid,   \n"
				+ "     bo.app_date,   \n"
				+ "     bo.ERR_MSG,   \n"
				+ "     basket_type_t.basket_type,   \n"
				+ "     bo.lob,   \n"
				+ "     DECODE(BO.LOB, 'MOB', BO.MSISDN, BC.SERVICE_NUM) SERVICE_NUM,  \n"
				+ "     boi.login_id IMS_LOGIN_ID,   \n"
				+ "     bo.reason_cd,  \n"
				+ "	   bo.check_point,		\n"
				+ "     BC.LAST_NAME ||' ' ||BC.FIRST_NAME cust_full_name   \n"
				+ "   FROM bomweb_order bo,   \n"
				+ "     (SELECT DISTINCT bsi.order_id,   \n"
				+ "       b.type basket_type   \n"
				+ "     FROM bomweb_subscribed_item bsi,   \n"
				+ "       w_basket b   \n"
				+ "     WHERE b.id = bsi.basket_id   \n"
				+ "     ) basket_type_t,   \n"
				+ "     bomweb_customer bc,   \n"
				+ "     bomweb_order_ims boi   \n"
				+ "   WHERE bo.order_id = basket_type_t.order_id   \n"
				+ "   AND bo.order_id   = ?  \n"
				+ "   AND bo.shop_cd    = ?   \n"
				+ "   AND bo.ORDER_ID   = bc.ORDER_ID   \n"
				+ "   AND bo.ORDER_ID   = boi.ORDER_ID(+)   \n"
				+ "   AND bo.lob != 'LTS' \n"
				+ " UNION \n"
				+ "     -- LTS Order \n"
				+ "     SELECT bo.order_id,   \n"
				+ "     DECODE(bols.sb_status, 'S', 'Y', 'N') today_order_flag,  -- allow recall LTS suspend order \n"
				+ "     bo.shop_cd,   \n"
				+ "     bols.sb_status order_status,   \n"
				+ "     bo.ocid,   \n" + "     bo.app_date,   \n"
				+ "     bo.ERR_MSG,   \n" + "     null BASKET_TYPE,   \n"
				+ "     bo.lob,  \n" + "     bos.srv_num SERVICE_NUM,  \n"
				+ "     null IMS_LOGIN_ID,   \n"
				+ "     bols.rea_cd reason_cd,  \n" + "	   bo.check_point,		\n"
				+ "     BC.LAST_NAME ||' ' ||BC.FIRST_NAME cust_full_name   \n"
				+ "   FROM bomweb_order bo,   \n"
				+ "        bomweb_customer bc,   \n"
				+ "        bomweb_order_service bos, \n"
				+ "        bomweb_order_latest_status bols, \n"
				+ "        bomweb_acct ba     \n"
				+ "   WHERE bo.order_id = bos.order_id \n"
				+ "   AND bos.order_id = bols.order_id \n"
				+ "   AND bos.dtl_id =  bols.dtl_id \n"
				+ "   AND bo.order_id   = ?   \n"
				+ "   AND bo.shop_cd    = ?   \n"
				+ "   AND bos.ORDER_ID = ba.ORDER_ID \n"
				+ "   AND bos.acct_no = ba.acct_no  \n"
				+ "   AND bc.ORDER_ID = ba.ORDER_ID  \n"
				+ "   AND bc.cust_no = ba.cust_no  \n"
				+ "   AND bo.lob = 'LTS' \n" + "   ORDER BY order_id ";

		ParameterizedRowMapper<OrderDTO> mapper = new ParameterizedRowMapper<OrderDTO>() {

			public OrderDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				OrderDTO order = new OrderDTO();

				order.setOrderId(rs.getString("order_id"));
				order.setShopCode(rs.getString("shop_cd"));
				order.setOrderStatus(rs.getString("order_status"));
				order.setOcid(rs.getString("ocid"));
				order.setAppInDate(rs.getTimestamp("app_date"));
				order.setErrorMessage(rs.getString("ERR_MSG"));
				order.setTodayOrderFlag(rs.getString("today_order_flag"));
				order.setOrderSumLob(rs.getString("LOB"));
				order.setOrderSumServiceNum(rs.getString("SERVICE_NUM"));
				order.setImsLoginId(rs.getString("IMS_LOGIN_ID"));
				order.setReasonCd(rs.getString("reason_cd"));
				order.setCheckPoint(rs.getString("check_point"));
				order.setOrderSumCustName(rs.getString("cust_full_name"));

				return order;
			}
		};

		try {
			logger.debug("getOrderSummaryList @ OrderDAO: " + SQLsearchByDate);

			if (!"".equals(orderIdStr)) {
				orderList = simpleJdbcTemplate.query(SQLsearchByOrderId,
						mapper, orderIdStr, shopCode, orderIdStr, shopCode);
			} else {
				orderList = simpleJdbcTemplate.query(SQLsearchByDate, mapper,
						dateStr, shopCode, dateStr, shopCode);
			}
			if (!"".equals(orderIdStr)) {
				dateStr = "";

			}

			// orderList = simpleJdbcTemplate
			// .query(SQLsearchByDate, mapper, dateStr, shopCode,orderIdStr);

		} catch (EmptyResultDataAccessException erdae) {
			logger.info("getOrderSummaryList EmptyResultDataAccessException");

			orderList = null;
		} catch (Exception e) {
			logger.error("Exception caught in getOrderSummaryList()", e);

			throw new DAOException(e.getMessage(), e);
		}
		return orderList;

	}

	public MnpDTO getMnp(String orderId) throws DAOException {
		logger.debug("getMnp() is called");

		List<MnpDTO> mnpList = new ArrayList<MnpDTO>();

		/*
		 * String SQL = " SELECT bm.ORDER_ID, \n" + " 	bm.CUT_OVER_DATE, \n" +
		 * " 	bm.CUT_OVER_TIME, \n" + " 	bm.RNO, \n" + " 	bm.DNO, \n" +
		 * " 	bm.CUST_NAME, \n" + " 	bm.DOC_NO, \n" + " 	bo.msisdn, \n" +
		 * " 	bm.mnp_ticket_num, \n" + " 	bo.mnp_ind, \n" + " 	bo.msisdn, \n" +
		 * " 	bo.shop_cd, \n" + "		bsa.channel_id, \n" // add by wilson 20110308
		 * + " 	bo.SRV_REQ_DATE , bm.CUST_NAME_CHI, bm.PREPAID_SIM_DOC_IND\n" +
		 * " FROM BOMWEB_MNP bm, BOMWEB_ORDER BO, BOMWEB_SALES_ASSIGNMENT BSA \n"
		 * + " where bsa.staff_id=bo.sales_cd \n" +
		 * " and bm.ORDER_ID=bO.ORDER_ID \n" + " and bm.ORDER_ID=? \n";
		 */

		String SQL = "select BM.ORDER_ID\n" + "      ,BM.CUT_OVER_DATE\n"
				+ "      ,BM.CUT_OVER_TIME\n" + "      ,BM.RNO\n"
				+ "      ,BM.ACT_DNO\n"
				+ "      ,BM.DNO\n" + "      ,BM.CUST_NAME\n"
				+ "      ,BM.DOC_NO\n" + "      ,BO.MSISDN\n"
				+ "      ,BM.MNP_TICKET_NUM\n" + "      ,BO.MNP_IND\n"
				+ "      ,BO.MSISDN\n" + "      ,BO.SHOP_CD\n"
				+ "      ,SHOP.CHANNEL_ID\n" + "      ,BO.SRV_REQ_DATE\n"
				+ "      ,BM.CUST_NAME_CHI\n"
				+ "      ,BM.PREPAID_SIM_DOC_IND\n"
				+ "      ,S.ORIG_ACT_DATE\n"
				+ "      ,(select M.NUM_TYPE FROM BOMWEB_MRT M WHERE M.ORDER_ID = BO.ORDER_ID and M.SEQ_ID = 1) NUM_TYPE\n"
				+ "      ,BM.OPSS_IND\n"
				+ "      ,BM.STARTER_PACK\n"
				+ "      ,csub.MRT\n"
				+ "from BOMWEB_MNP              BM\n"
				+ "    ,BOMWEB_SHOP SHOP\n"
				+ "    ,BOMWEB_SUB              S\n"
				+ "    ,BOMWEB_ORDER            BO\n"
				+ "left join bomweb_csub_list csub on csub.MRT = BO.MSISDN\n"
				+ "where BO.SHOP_CD = SHOP.SHOP_CD\n"
				+ "and BM.ORDER_ID = BO.ORDER_ID\n"
				+ "and BO.ORDER_ID = S.ORDER_ID\n" + "and BM.ORDER_ID = ?\n";

		ParameterizedRowMapper<MnpDTO> mapper = new ParameterizedRowMapper<MnpDTO>() {
			public MnpDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				MnpDTO mnp = new MnpDTO();

				mnp.setOrderId(rs.getString("ORDER_ID"));
				mnp.setRno(rs.getString("RNO"));
				mnp.setDno(rs.getString("DNO"));
				mnp.setActualDno(rs.getString("ACT_DNO"));
				mnp.setCustName(rs.getString("CUST_NAME"));
				mnp.setCustIdDocNum(rs.getString("DOC_NO"));
				mnp.setMsisdn(rs.getString("msisdn"));
				mnp.setMnpTicketNum(rs.getString("mnp_ticket_num")); // add
																		// 20110225
				mnp.setMnp(rs.getString("mnp_ind")); // add 20110225
				mnp.setChannelId(rs.getString("channel_id"));
				mnp.setNumType(rs.getString("num_type"));
				mnp.setOpssInd(rs.getString("OPSS_IND"));
				mnp.setStarterPack(rs.getString("STARTER_PACK"));
				
				if (StringUtils.isNotEmpty(rs.getString("MRT"))){
					mnp.setCheckIsWhiteList(true);
				} else {
					mnp.setCheckIsWhiteList(false);
				}
				
				if ("Y".equals(rs.getString("mnp_ind"))) {// add 20110228
					mnp.setMnpType("MNP");
					mnp.setMnpMsisdn(rs.getString("msisdn"));//
					mnp.setCutoverDate(rs.getTimestamp("CUT_OVER_DATE"));
					mnp.setCutoverTime(rs.getString("CUT_OVER_TIME"));
					mnp.setCutoverDateStr(Utility.date2String(
							rs.getTimestamp("CUT_OVER_DATE"), "dd/MM/yyyy"));
					mnp.setPrePaidSimDocInd(rs.getString("PREPAID_SIM_DOC_IND"));// add
																					// by
																					// wilson
																					// 20120723
					mnp.setCustNameChi(rs.getString("CUST_NAME_CHI"));// add by
																		// wilson
																		// 20120723
					mnp.setOrigActDate(rs.getTimestamp("ORIG_ACT_DATE"));
					mnp.setOrigActDateStr(Utility.date2String(
							rs.getTimestamp("ORIG_ACT_DATE"), "dd/MM/yyyy"));
		

				} else {
					mnp.setMnpType("New Number");
					mnp.setNewMsisdn(rs.getString("msisdn"));
					mnp.setServiceReqDate(rs.getTimestamp("SRV_REQ_DATE"));
					mnp.setServiceReqDateStr(Utility.date2String(
							rs.getTimestamp("SRV_REQ_DATE"), "dd/MM/yyyy"));
				}
				mnp.setShopCd(rs.getString("shop_cd"));// add 2011328

				return mnp;
			}
		};

		try {
			// herbert 20111110 - remove useless SQL logger
			logger.debug("getMnp() @ OrderDAO: " + SQL);
			logger.info("getMnp() @ OrderDAO: ");
			mnpList = simpleJdbcTemplate.query(SQL, mapper, orderId);

		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException");

			mnpList = null;
		} catch (Exception e) {
			logger.info("Exception caught in getMnp():", e);
			throw new DAOException(e.getMessage(), e);
		}
		if (mnpList == null || mnpList.size() == 0) {
			return null;
		} else {
			return mnpList.get(0);// only return the first one
		}

	}

	// add by wilson 20110222, for recall order
	public PaymentDTO getPayment(String orderId) throws DAOException {
		logger.debug("getPayment() is called");

		List<PaymentDTO> paymentList = null;

		/*
		 * String SQL =
		 * "SELECT ORDER_ID, PAY_MTD_KEY, PAY_MTD_TYPE,   THIRD_PARTY_IND,    CC_TYPE,\n"
		 * +
		 * "       CC_NUM, CC_HOLD_NAME, CC_EXP_DATE, CC_ISSUE_BANK, CC_ID_DOC_TYPE,\n"
		 * +
		 * "       CC_ID_DOC_NO, B_ACCT_HOLD_ID_TYPE,  B_ACCT_HOLD_ID_NUM, BANK_CD, BRANCH_CD,\n"
		 * +
		 * "       B_ACCT_HOLD_NAME, AUTOPAY_UP_LIM_AMT, B_ACCT_NO, to_char(AUTOPAY_APP_DATE, 'DD/MM/YYYY') AUTOPAY_APP_DATE,\n"
		 * + "       CREATE_DATE\n" +
		 * "  FROM BOMWEB_PAYMENT WHERE ORDER_ID = ?";
		 */

		String SQL = "SELECT p.ORDER_ID,\n"
				+ "       p.PAY_MTD_KEY,\n"
				+ "       p.PAY_MTD_TYPE,\n"
				+ "       p.THIRD_PARTY_IND,\n"
				+ "       p.CC_TYPE,\n"
				+ "       p.CC_NUM,\n"
				+ "       p.CC_HOLD_NAME,\n"
				+ "       p.CC_EXP_DATE,\n"
				+ "       p.CC_ISSUE_BANK,\n"
				+
				// add by herbert 20110721 - start -
				"       (select bank_name from W_ISSUEBANKLKUP cb\n"
				+ "         where cb.bank_code = p.CC_ISSUE_BANK) CC_ISSUE_BANK_NAME,\n"
				+
				// add by herbert 20110721 - end -
				"       p.CC_ID_DOC_TYPE,\n"
				+ "       p.CC_ID_DOC_NO,\n"
				+ "       p.B_ACCT_HOLD_ID_TYPE,\n"
				+ "       p.B_ACCT_HOLD_ID_NUM,\n"
				+ "	p.CC_VERIFIED_IND, \n" // add by herbert 20120201
				+ "       p.BANK_CD,\n"
				+ "       (select bank_name || '(' || ab.bank_code || ')'\n"
				+ "          from w_ap_issuebanklkup ab\n"
				+ "         where ab.bank_code = p.BANK_CD) bank_name,\n"
				+ "       (select abb.branch_name || '(' || abb.branch_code || ')'\n"
				+ "          from w_ap_bankbranchlkup abb\n"
				+ "         where abb.bank_code = p.BANK_CD\n"
				+ "           and abb.branch_code = p.branch_cd) branch_name,\n"
				+ "       p.BRANCH_CD,\n"
				+ "       p.B_ACCT_HOLD_NAME,\n"
				+ "       p.AUTOPAY_UP_LIM_AMT,\n"
				+ "       p.B_ACCT_NO,\n"
				+ "       to_char(p.AUTOPAY_APP_DATE, 'DD/MM/YYYY') AUTOPAY_APP_DATE,\n"
				+ "       p.CREATE_DATE,\n" + "       p.BYPASS_IND\n" // add by
																		// herbert
																		// 20120302
				+ "  FROM BOMWEB_PAYMENT p\n" + " WHERE p.ORDER_ID = ?";

		ParameterizedRowMapper<PaymentDTO> mapper = new ParameterizedRowMapper<PaymentDTO>() {
			public PaymentDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				PaymentDTO dto = new PaymentDTO();

				dto.setOrderId(rs.getString("ORDER_ID"));
				dto.setPayMethodKey(rs.getString("PAY_MTD_KEY"));
				dto.setPayMethodType(rs.getString("PAY_MTD_TYPE"));
				dto.setThirdPartyInd(rs.getString("THIRD_PARTY_IND"));

				dto.setByPassValidation("Y".equalsIgnoreCase(rs
						.getString("BYPASS_IND")));// add by herbert 20120302

				if ("C".equals(rs.getString("PAY_MTD_TYPE"))) {
					dto.setCreditCardType(rs.getString("CC_TYPE"));
					dto.setCreditCardNum(rs.getString("CC_NUM"));
					dto.setCreditCardHolderName(rs.getString("CC_HOLD_NAME"));
					dto.setCreditExpiryDate(rs.getString("CC_EXP_DATE"));
					dto.setCreditCardIssueBankCd(rs.getString("CC_ISSUE_BANK"));
					dto.setCreditCardIssueBankName(rs
							.getString("CC_ISSUE_BANK_NAME")); // add by herbert
																// 20110721
					dto.setCreditCardVerifiedInd(rs
							.getString("CC_VERIFIED_IND"));
					dto.setCreditCardDocType(rs.getString("CC_ID_DOC_TYPE"));
					dto.setCreditCardDocNum(rs.getString("CC_ID_DOC_NO"));
					if (!"".equals(dto.getCreditExpiryDate())
							&& dto.getCreditExpiryDate() != null
							|| "".equals(dto.getCreditExpiryDate())) {

						dto.setCreditExpiryMonth(dto.getCreditExpiryDate()
								.substring(0, 2));
						dto.setCreditExpiryYear(dto.getCreditExpiryDate()
								.substring(3));

					}
				}

				if ("A".equals(rs.getString("PAY_MTD_TYPE"))) {// add 20110608

					dto.setBankAcctHolderIdType(rs
							.getString("B_ACCT_HOLD_ID_TYPE"));
					dto.setBankAcctHolderIdNum(rs
							.getString("B_ACCT_HOLD_ID_NUM"));
					dto.setBankCode(rs.getString("BANK_CD"));
					dto.setBranchCode(rs.getString("BRANCH_CD"));
					dto.setBankAcctHolderName(rs.getString("B_ACCT_HOLD_NAME"));
					dto.setAutopayUpperLimitAmt(rs
							.getString("AUTOPAY_UP_LIM_AMT"));
					dto.setBankAcctNum(rs.getString("B_ACCT_NO"));
					dto.setAutopayApplDateStr(rs.getString("AUTOPAY_APP_DATE"));
					dto.setBankName(rs.getString("BANK_NAME"));
					dto.setBranchName(rs.getString("BRANCH_NAME"));

					dto.setAutopayApplDate(Utility.string2Date(rs
							.getString("AUTOPAY_APP_DATE")));
					dto.setAutopayApplDateStr(rs.getString("AUTOPAY_APP_DATE"));// add
																				// 20110613
				}

				return dto;
			}
		};

		try {
			// herbert 20111110 - remove useless SQL logger
			logger.debug("getPayment() @ OrderDAO: ");
			logger.debug("getPayment() @ OrderDAO: " + SQL);
			paymentList = simpleJdbcTemplate.query(SQL, mapper, orderId);

		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException");

			paymentList = null;
			return null;
		} catch (Exception e) {
			logger.info("Exception caught in getPayment():", e);
			throw new DAOException(e.getMessage(), e);
		}
		
		if (paymentList.isEmpty())
			return null;// only return the first one
		else
			return paymentList.get(0);// only return the first one

	}

	// add by wilson 20110222, for recall order
	// modify by herbert 20110707, for advance order use
	public MobileSimInfoDTO getSim(String orderId) throws DAOException {
		logger.debug("getSim() is called");

		List<MobileSimInfoDTO> simList = new ArrayList<MobileSimInfoDTO>();

		String SQL = " SELECT bs.order_id, bs.iccid, bs.imsi, bs.puk1, bs.puk2, bs.item_code, bo.app_date, \n" +
				"         bs.create_date, bo.shop_cd , bo.SALES_CD, bo.SALES_TYPE, bo.imei, bo.AO_IND, bo.sales_name, bo.sales_contact_num,  \n" +
				"         bsi.WAIVE_REASON SIM_WAIVE_REASON, \n" +
				"         ip.waivable, ip.onetime_amt, \n" +
				"         bs.sim_type \n" +
				"    FROM bomweb_sim bs \n" +
				"    JOIN bomweb_order bo ON bs.order_id = bo.order_id \n" +
				"    LEFT JOIN bomweb_subscribed_item bsi ON bo.order_id = bsi.order_id \n" +
				"    LEFT JOIN w_item_pricing ip on bsi.id = ip.id \n" +
				"   WHERE bs.order_id = ? \n" +
				"     AND bsi.type = 'SIM' \n" +
				"     AND trunc(bo.app_date) between trunc(ip.eff_start_date) and trunc(nvl(ip.eff_end_date, sysdate)) ";

		ParameterizedRowMapper<MobileSimInfoDTO> mapper = new ParameterizedRowMapper<MobileSimInfoDTO>() {
			public MobileSimInfoDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				MobileSimInfoDTO dto = new MobileSimInfoDTO();

				dto.setOrderId(rs.getString("ORDER_ID"));
				dto.setIccid(rs.getString("ICCID"));
				dto.setImsi(rs.getString("IMSI"));
				dto.setPuk1(rs.getString("PUK1"));
				dto.setPuk2(rs.getString("PUK2"));
				dto.setItemCd(rs.getString("ITEM_CODE"));
				// dto.setBomWebItemCd(dto.getItemCd());
				dto.setSalesCd(rs.getString("SALES_CD"));// add by wilson
															// 20110302
				dto.setSalesType(rs.getString("SALES_TYPE"));// add by wilson
																// 20110302
				dto.setImei(rs.getString("imei"));// add by wilson 20110302

				dto.setShopCd("P" + rs.getString("shop_cd"));// add by wilson
																// 20110228, add
																// P by wilson
																// 20110303

				dto.setSalesName(rs.getString("sales_name"));
				dto.setSalesContactNum(rs.getString("sales_contact_num"));

				// add by herbert 20110707, for advance order use **start
				if (rs.getString("AO_IND") == null
						|| rs.getString("AO_IND").equalsIgnoreCase("N")) {
					dto.setAoInd(null);
				} else {
					dto.setAoInd("Y");
				}
				dto.setAppDate(rs.getDate("app_date"));
				// add by herbert 20110707, for advance order use **end
				
				dto.setSimCharge(rs.getDouble("ONETIME_AMT"));
				dto.setSimWaivable("Y".equals(rs.getString("WAIVABLE"))? true:false);
				dto.setSimWaiveReason(rs.getString("SIM_WAIVE_REASON"));
				dto.setSimBrandType(rs.getString("sim_type"));
				return dto;
			}
		};

		try {
			// herbert 20111110 - remove useless SQL logger
			logger.debug("getSim() @ OrderDAO: ");
			logger.debug("getSim() @ OrderDAO: " + SQL);

			simList = simpleJdbcTemplate.query(SQL, mapper, orderId);

		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException");

			simList = null;
		} catch (Exception e) {
			logger.info("Exception caught in getPayment():", e);

			throw new DAOException(e.getMessage(), e);
		}
		return simList.get(0);// only return the first one

	}
	
	public MobileSimInfoDTO getTooMobileSimInfo(String orderId) throws DAOException {
		logger.debug("getTooMobileSimInfo() is called");

		List<MobileSimInfoDTO> simList = new ArrayList<MobileSimInfoDTO>();

		String SQL = "SELECT bo.order_id, bo.app_date,  bo.create_date,  bo.shop_cd , bo.SALES_CD, \n"
				+ "  bo.SALES_TYPE,  bo.imei,  bo.AO_IND,  bo.sales_name, bo.sales_contact_num, \n"
				+ "  bsi.WAIVE_REASON SIM_WAIVE_REASON, ip.waivable,  ip.onetime_amt \n" + "FROM bomweb_order bo \n"
				+ "LEFT JOIN bomweb_subscribed_item bsi \n" + "ON bo.order_id = bsi.order_id \n"
				+ "LEFT JOIN w_item_pricing ip \n" + "ON bsi.id         = ip.id \n" + "WHERE bo.order_id = ? \n"
				+ "AND TRUNC(bo.app_date) BETWEEN TRUNC(ip.eff_start_date) AND TRUNC(NVL(ip.eff_end_date, sysdate)) ";

		ParameterizedRowMapper<MobileSimInfoDTO> mapper = new ParameterizedRowMapper<MobileSimInfoDTO>() {
			public MobileSimInfoDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				MobileSimInfoDTO dto = new MobileSimInfoDTO();

				dto.setOrderId(rs.getString("ORDER_ID"));
				// dto.setBomWebItemCd(dto.getItemCd());
				dto.setSalesCd(rs.getString("SALES_CD"));// add by wilson
															// 20110302
				dto.setSalesType(rs.getString("SALES_TYPE"));// add by wilson
																// 20110302
				dto.setImei(rs.getString("imei"));// add by wilson 20110302

				dto.setShopCd("P" + rs.getString("shop_cd"));// add by wilson
																// 20110228, add
																// P by wilson
																// 20110303

				dto.setSalesName(rs.getString("sales_name"));
				dto.setSalesContactNum(rs.getString("sales_contact_num"));

				// add by herbert 20110707, for advance order use **start
				if (rs.getString("AO_IND") == null || rs.getString("AO_IND").equalsIgnoreCase("N")) {
					dto.setAoInd(null);
				} else {
					dto.setAoInd("Y");
				}
				dto.setAppDate(rs.getDate("app_date"));
				// add by herbert 20110707, for advance order use **end

				dto.setSimCharge(rs.getDouble("ONETIME_AMT"));
				dto.setSimWaivable("Y".equals(rs.getString("WAIVABLE")) ? true : false);
				dto.setSimWaiveReason(rs.getString("SIM_WAIVE_REASON"));
				// dto.setSimBrandType(rs.getString("sim_type"));
				return dto;
			}
		};

		try {
			// herbert 20111110 - remove useless SQL logger
			logger.debug("getTooMobileSimInfo() @ OrderDAO: ");
			logger.debug("getTooMobileSimInfo() @ OrderDAO: " + SQL);

			simList = simpleJdbcTemplate.query(SQL, mapper, orderId);

		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException");

			simList = null;
		} catch (Exception e) {
			logger.info("Exception caught in getTooMobileSimInfo():", e);

			throw new DAOException(e.getMessage(), e);
		}
		if (simList.get(0) == null) {
			return null;
		} else {
			return simList.get(0);// only return the first one
		}

	}

	// add by wilson 20110222, for recall order
	// modify by eliot 20110627, add salesname
	// modify by herbert 20110707, add advance order
	// modify by eliot 20110829, add lastUpdateBy
	// modify by erichui 20120313, add checkPoint
	// modify by wilson 20121009, add BOMWEB_ORDER_MOB.IGUARD_SN
	// modify by wilson 20130529, add ONLINE_REQ_ID
	public OrderDTO getOrder(String orderId) throws DAOException {
		logger.debug("getOrder() is called");

		List<OrderDTO> orderList = new ArrayList<OrderDTO>();

		/*
		 * String SQL="select O.ORDER_ID,\n" + "       O.OCID,\n" +
		 * "       O.SRC,\n" + "       O.ORDER_TYPE,\n" + "       O.MSISDN,\n" +
		 * "       O.MSISDNLVL,\n" + "       O.MNP_IND,\n" +
		 * "       O.SHOP_CD,\n" + "       O.BOM_CUST_NO,\n" +
		 * "       O.MOB_CUST_NO,\n" + "       O.ACCT_NO,\n" +
		 * "       O.SRV_REQ_DATE,\n" + "       O.AGREE_NUM,\n" +
		 * "       O.APP_DATE,\n" + "       O.SALES_TYPE,\n" +
		 * "       O.SALES_CD,\n" + "       O.DEP_WAIVE,\n" +
		 * "       O.ON_HOLD_IND,\n" + "       O.ON_HOLD_REA_CD,\n" +
		 * "       O.IMEI,\n" + "       O.WARR_START_DATE,\n" +
		 * "       O.WARR_PERIOD,\n" + "       O.ORDER_STATUS,\n" +
		 * "       O.CREATE_DATE,\n" + "       O.SALES_NAME,\n" +
		 * "       O.AO_IND,\n" + "       O.LAST_UPDATE_BY,\n" +
		 * "       O.LOB,\n" + "       O.CHECK_POINT,\n" +
		 * "       O.REASON_CD,\n" + "       O.CLONE_ORDER_ID,\n" +
		 * "  		O.BOM_CREATION_DATE ,\n" +
		 * "       (select DECODE(count(*), 0, 'N', 'Y')\n" +
		 * "          from BOMWEB_PAYMENT_TRANS PT\n" +
		 * "         where PT.ORDER_ID = O.ORDER_ID\n" +
		 * "           and PT.TRANS_STATUS = 'SETTLED' and PT.PAY_MTD_TYPE in ('C', 'I')) TRX_IND\n"
		 * + " , O.DIS_MODE" + " , O.COLLECT_METHOD" + " , O.ESIG_EMAIL_ADDR" +
		 * " , O.ESIG_EMAIL_LANG" + " , O.DMS_IND" + " , O.BOM_CREATION_DATE" +
		 * "  from BOMWEB_ORDER O\n" + " where O.ORDER_ID = ?";
		 */

		String SQL = "select O.ORDER_ID,\n" + "       O.OCID,\n"
				+ "       O.SRC,\n" + "       O.ORDER_TYPE,\n"
				+ "       O.MSISDN,\n" + "       O.MSISDNLVL,\n"
				+ "       O.MNP_IND,\n" + "       O.SHOP_CD,\n"
				+ "       O.BOM_CUST_NO,\n" + "		O.ERR_CD,\n"
				+ "		O.ERR_MSG,\n" + "       O.MOB_CUST_NO,\n"
				+ "       O.ACCT_NO,\n" + "       O.SRV_REQ_DATE,\n"
				+ "       O.AGREE_NUM,\n" + "       O.APP_DATE,\n"
				+ "       O.SALES_TYPE,\n" + "       O.SALES_CD,\n"
				+ "       O.SALES_CONTACT_NUM,\n" + "       O.DEP_WAIVE,\n"
				+ "       O.ON_HOLD_IND,\n" + "       O.ON_HOLD_REA_CD,\n"
				+ "       O.IMEI,\n" + "       O.WARR_START_DATE,\n"
				+ "       O.WARR_PERIOD,\n" + "       O.SUPER_APP_IND,\n"
				+ "       O.ORDER_APP_IND,\n" + "       O.ORDER_STATUS,\n"
				+ "       O.CREATE_DATE,\n" + "       O.SALES_NAME,\n"
				+ "       O.AO_IND,\n" + "       O.LAST_UPDATE_BY,\n"
				+ "       O.LOB,\n" + "       O.CHECK_POINT,\n"
				+ "       O.REASON_CD,\n" + "       O.CLONE_ORDER_ID,\n"
				+ "       O.BOM_CREATION_DATE,\n"
				+ "		S.REF_SALES_NAME,\n" + "S.REF_SALES_CD,\n"
				+ "		S.REF_CENTRE_CD,\n" + "S.REF_TEAM_CD,\n"
				+ "       (select DECODE(count(*), 0, 'N', 'Y')\n"
				+ "          from BOMWEB_PAYMENT_TRANS PT\n"
				+ "         where PT.ORDER_ID = O.ORDER_ID\n"
				+ "           and PT.TRANS_STATUS = 'SETTLED'\n"
				+ "           and PT.PAY_MTD_TYPE in ('C', 'I')) TRX_IND,\n"
				+ "       O.DIS_MODE,\n" + "       O.COLLECT_METHOD,\n"
				+ "       O.ESIG_EMAIL_ADDR,\n" + "       O.ESIG_EMAIL_LANG,\n"
				+ "       O.DMS_IND,\n" + "       O.BOM_CREATION_DATE,\n"
				+ "       M.IGUARD_SN,\n" + "       O.ONLINE_REQ_ID,\n"
				+ "       nvl(O.PAYMT_CHK_IND, 'N') PAYMT_CHK_IND,\n"
				+ "       nvl(O.SUPDOC_CHK_IND, 'N') SUPDOC_CHK_IND,\n"
				+ "       O.PAYMT_REC_DATE,\n"
				+ "		  nvl( M.CARE_OPT_IND, 'N') CARE_OPT_IND,\n"
				+ "      (select DECODE(count(*), 0, 'N', 'Y')\n"
				+ "        from BOMWEB_MRT M\n"
				+ "        where M.SEQ_ID = 1\n"
				+ "        and M.ORDER_ID = O.ORDER_ID\n"
				+ "        and M.RESERVE_ID is not null) RESERVE_MRT_IND,\n "
				+ "        NVL(M.BRM_HS_HL_IND,'N')BRM_HS_HL_IND,\n"
				+ "        (M.CUR_CONTRACT_END_DATE +1) as CUR_CONTRACT_END_DATE,\n"
				+ "        M.CE_IND"			
				+ "  from BOMWEB_ORDER O, BOMWEB_ORDER_MOB M, BOMWEB_STAFF_INFO S\n"
				+ " where O.ORDER_ID = M.ORDER_ID(+)\n"
				+ " and O.ORDER_ID = S.ORDER_ID(+)\n"
				+ "   and O.ORDER_ID = ?";

		ParameterizedRowMapper<OrderDTO> mapper = new ParameterizedRowMapper<OrderDTO>() {
			public OrderDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				OrderDTO dto = new OrderDTO();

				dto.setOrderId(rs.getString("ORDER_ID"));
				dto.setOcid(rs.getString("OCID"));
				dto.setErrorCode(rs.getString("ERR_CD"));
				dto.setErrorMessage(rs.getString("ERR_MSG"));
				dto.setSource(rs.getString("SRC"));
				dto.setBusTxnType(rs.getString("ORDER_TYPE"));
				dto.setMsisdn(rs.getString("MSISDN"));
				dto.setMsisdnLvl(rs.getString("MSISDNLVL"));
				dto.setMnpInd(rs.getString("MNP_IND"));
				dto.setShopCode(rs.getString("SHOP_CD"));
				dto.setBomCustNum(rs.getString("BOM_CUST_NO"));
				dto.setMobCustNum(rs.getString("MOB_CUST_NO"));
				dto.setAcctNum(rs.getString("ACCT_NO"));
				dto.setSrvReqDate(rs.getTimestamp("SRV_REQ_DATE"));
				dto.setAgreementNum(rs.getString("AGREE_NUM"));
				dto.setAppInDate(rs.getTimestamp("APP_DATE"));
				dto.setSalesType(rs.getString("SALES_TYPE"));
				dto.setSalesCd(rs.getString("SALES_CD"));
				dto.setSalesContactNum(rs.getString("SALES_CONTACT_NUM"));
				dto.setDepositWaiveInd(rs.getString("DEP_WAIVE"));
				dto.setOnHoldInd(rs.getString("ON_HOLD_IND"));
				dto.setOnHoldReaCd(rs.getString("ON_HOLD_REA_CD"));
				dto.setImei(rs.getString("IMEI"));
				dto.setWarrantyStartDate(rs.getString("WARR_START_DATE"));
				dto.setWarrantPeriod(rs.getString("WARR_PERIOD"));
				dto.setSuperAppInd(rs.getString("SUPER_APP_IND"));
				dto.setOrderAppInd(rs.getString("ORDER_APP_IND"));
				dto.setOrderStatus(rs.getString("ORDER_STATUS"));
				// add by eliot 20110627
				dto.setSalesName(rs.getString("SALES_NAME"));
				// add by herbert 20110707
				dto.setAoInd(rs.getString("AO_IND"));
				// add by eliot 20110829
				dto.setLastUpdateBy(rs.getString("LAST_UPDATE_BY"));
				dto.setOrderSumLob(rs.getString("LOB"));
				// add by erichui 20120313
				dto.setCheckPoint(rs.getString("CHECK_POINT"));
				dto.setCreateDate(rs.getTimestamp("create_date"));
				// add by erichui 20120405
				dto.setReasonCd(rs.getString("REASON_CD"));
				dto.setCloneOrderId(rs.getString("CLONE_ORDER_ID"));
				// dto.setHistSeqNo(rs.getString("MAX_SEQ_NO"));
				// max = (a > b) ? a : b;
				// dto.setCreditCardTrxInd(!"0".equals(rs.getString("MAX_SEQ_NO"))?
				// "Y":"N");
				dto.setCreditCardTrxInd(rs.getString("TRX_IND"));
				/*
				 * if (!"0".equals(rs.getString("MAX_SEQ_NO"))){
				 * dto.setCreditCardTrxInd("Y"); }else{
				 * dto.setCreditCardTrxInd("N"); }
				 */
				String disMode = rs.getString("DIS_MODE");
				if (disMode instanceof String) {
					dto.setDisMode(DisMode.valueOf(disMode));
				}
				String collectMethod = rs.getString("COLLECT_METHOD");
				if (collectMethod instanceof String) {
					dto.setCollectMethod(CollectMethod.valueOf(collectMethod));
				}
				// ESIG_EMAIL_LANG can be null in Paper disMode
				dto.setEsigEmailAddr(rs.getString("ESIG_EMAIL_ADDR"));
				String esigEmailLang = rs.getString("ESIG_EMAIL_LANG");
				if (esigEmailLang instanceof String) {
					dto.setEsigEmailLang(EsigEmailLang.valueOf(esigEmailLang));
				}

				dto.setDmsInd(rs.getString("DMS_IND"));
				dto.setBomCreationDate(rs.getTimestamp("BOM_CREATION_DATE"));// add
																				// by
																				// wilson
																				// 20120830
				dto.setiGuardSerialNo(rs.getString("IGUARD_SN")); // add by
																	// wilson
																	// 2012
				dto.setReqId(rs.getInt("ONLINE_REQ_ID")); // add by wilson
															// 20130529
				dto.setPaymentCheck(rs.getString("PAYMT_CHK_IND"));// add by
																	// nancy
																	// 20131004
				dto.setSupportDocCheck(rs.getString("SUPDOC_CHK_IND"));
				
				
				dto.setPaymtRecDate(rs.getTimestamp("PAYMT_REC_DATE"));// add by
																		// nancy
																		// 20131112
				/*Add by Whitney 20160826 */
				dto.setReserveMrtInd(rs.getString("RESERVE_MRT_IND"));
				dto.setRefSalesName(rs.getString("REF_SALES_NAME"));
				dto.setRefSalesId(rs.getString("REF_SALES_CD"));
				dto.setRefSalesCentre(rs.getString("REF_CENTRE_CD"));
				dto.setRefSalesTeam(rs.getString("REF_TEAM_CD"));
				
				dto.setBrmHsHlInd(rs.getString("BRM_HS_HL_IND"));
				dto.setCurContractEndDate(rs.getDate("CUR_CONTRACT_END_DATE"));  // jill
				dto.setCeInd(rs.getString("CE_IND"));  // jill
				return dto;
			}
		};

		try {
			// herbert 20111110 - remove useless SQL logger
			logger.debug("getOrder() @ OrderDAO: ");
			logger.debug("getOrder() @ OrderDAO: " + SQL);

			orderList = simpleJdbcTemplate.query(SQL, mapper, orderId);

		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException");

			orderList = null;
		} catch (Exception e) {
			logger.info("Exception caught in getOrder():", e);

			throw new DAOException(e.getMessage(), e);
		}

		if (orderList == null || orderList.size() == 0) {
			return null;
		} else {
			return orderList.get(0);
		}

	}

	// add by wilson 20110222, for recall order
	public AccountDTO getAccount(String orderId) throws DAOException {
		logger.debug("getAccount() is called");

		List<AccountDTO> orderList = new ArrayList<AccountDTO>();

		String SQL = " select  \n" + " ORDER_ID, \n" + " ACCT_NAME, \n" + " BILL_FREQ, \n" + " BILL_LANG, \n"
				+ " SMS_NO, \n" + " EMAIL_ADDR, \n" + " BILL_PERIOD, \n" + " CREATE_DATE, \n" + " BRAND, \n"
				+ " ACCT_NO, \n" + " IS_NEW, \n" + " CUST_NO, \n" + " EXIST_ACTIVE_MOBILE_NO \n"
				+ " from BOMWEB_ACCT \n" + " where  ORDER_ID=? \n";

		ParameterizedRowMapper<AccountDTO> mapper = new ParameterizedRowMapper<AccountDTO>() {
			public AccountDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				AccountDTO dto = new AccountDTO();
				dto.setOrderId(rs.getString("ORDER_ID"));
				dto.setAcctName(rs.getString("ACCT_NAME"));
				dto.setBillFreq(rs.getString("BILL_FREQ"));
				dto.setBillLang(rs.getString("BILL_LANG"));
				dto.setSmsNum(rs.getString("SMS_NO"));
				dto.setEmailAddr(rs.getString("EMAIL_ADDR"));
				dto.setBillPeriod(rs.getString("BILL_PERIOD"));// 20110228
				dto.setBrand(rs.getString("BRAND"));
				dto.setAcctNum(rs.getString("ACCT_NO"));
				dto.setIsNew(rs.getString("IS_NEW"));
				dto.setMobCustNum(rs.getString("CUST_NO"));
				dto.setSrvNum(rs.getString("EXIST_ACTIVE_MOBILE_NO"));
				return dto;
			}
		};

		try {
			// 20111110 herbert remove all
			logger.debug("getAccount() @ OrderDAO: ");
			logger.debug("getAccount() @ OrderDAO: " + SQL);
			orderList = simpleJdbcTemplate.query(SQL, mapper, orderId);

		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException");

			orderList = null;
		} catch (Exception e) {
			logger.info("Exception caught in getAccount():", e);

			throw new DAOException(e.getMessage(), e);
		}
		return orderList.get(0);// only return the first one

	}

	public String[] getBasketBrandModelInfoList(String orderId)
			throws DAOException {
		List<String[]> selectList = new ArrayList<String[]>();
		// 0:basket_id, 1:brand_id, 2:model_id

		String SQL = " select sim.order_id, sim.basket_id , hs.item_id, hs.model_id, hs.brand_id,  hs.COLOR_ID, hs.CONTRACT_PERIOD  from 	  (SELECT bsi.order_id, m.basket_id , m.item_id, m.model_id, m.brand_id,  m.COLOR_ID, m.CONTRACT_PERIOD \n"
				+ " 		           FROM (SELECT bia.item_id, bia.BASKET_ID, idh.model_id, idh.brand_id , idh.COLOR_ID, idh.CONTRACT_PERIOD \n"
				+ " 		                   FROM w_basket_item_assgn bia, w_item_dtl_hs idh  \n"
				+ " 		                  WHERE bia.item_id = idh.ID) m,  \n"
				+ " 		                bomweb_subscribed_item bsi  \n"
				+ " 		          WHERE m.item_id = bsi.ID  \n"
				+ " 				  and m.basket_id=bsi.basket_id \n"
				+ " 				  AND bsi.TYPE = 'HS') hs, \n"
				+ " 				 ( select bsi.order_id, bsi.BASKET_ID from bomweb_subscribed_item bsi  \n"
				+ " 				  where bsi.TYPE ='SIM' \n"
				+ " 				  and bsi.basket_id is not null ) sim \n"
				+ " 				  where hs.order_id(+) =sim.order_id \n"
				+ " 				  and sim.order_id=? \n";

		ParameterizedRowMapper<String[]> mapper = new ParameterizedRowMapper<String[]>() {

			public String[] mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				String[] brand = new String[4];
				brand[0] = rs.getString("basket_id");
				brand[1] = rs.getString("brand_id");
				brand[2] = rs.getString("model_id");
				brand[3] = rs.getString("COLOR_ID");
				return brand;
			}
		};

		try {
			logger.debug("getBasketBrandModelInfoList: " + SQL);
			selectList = simpleJdbcTemplate.query(SQL, mapper, orderId);
			if (selectList != null && selectList.size() > 0) {
				return selectList.get(0);
			} else {
				return null;
			}

		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException");
			selectList = null;
		} catch (Exception e) {
			logger.error("Exception caught in getBasketBrandModelInfoList()", e);
			throw new DAOException(e.getMessage(), e);
		}

		return null;

	}
	
	public String[] getTOOBRMBasketBrandModelInfoList(String orderId) throws DAOException {
		List<String[]> selectList = new ArrayList<String[]>();
		// 0:basket_id, 1:brand_id, 2:model_id

		String SQL = " select sim.order_id, sim.basket_id , hs.item_id, hs.model_id, hs.brand_id,  hs.COLOR_ID, hs.CONTRACT_PERIOD "
				+ " from (SELECT bsi.order_id, m.basket_id , m.item_id, m.model_id, m.brand_id,  m.COLOR_ID, m.CONTRACT_PERIOD \n"
				+ " FROM (SELECT bia.item_id, bia.BASKET_ID, idh.model_id, idh.brand_id , idh.COLOR_ID, idh.CONTRACT_PERIOD \n"
				+ " FROM w_basket_item_assgn bia, w_item_dtl_hs idh  \n"
				+ " WHERE bia.item_id = idh.ID) m,  \n"
				+ "bomweb_subscribed_item bsi  \n"
				+ "WHERE m.item_id = bsi.ID  \n"
				+ "and m.basket_id=bsi.basket_id \n"
				+ "AND bsi.TYPE = 'HS') hs, \n"
				+ "( select m.order_id, m.basket_id from bomweb_order_mob m \n"
				+ " where  m.order_id =? ) sim \n"
				+ " where hs.order_id(+) =sim.order_id \n"
				+ " and sim.order_id=? \n";

		ParameterizedRowMapper<String[]> mapper = new ParameterizedRowMapper<String[]>() {

			public String[] mapRow(ResultSet rs, int rowNum) throws SQLException {
				String[] brand = new String[4];
				brand[0] = rs.getString("basket_id");
				brand[1] = rs.getString("brand_id");
				brand[2] = rs.getString("model_id");
				brand[3] = rs.getString("COLOR_ID");
				return brand;
			}
		};

		try {
			logger.debug("getTOOBRMBasketBrandModelInfoList: " + SQL);
			selectList = simpleJdbcTemplate.query(SQL, mapper, orderId, orderId);
			if (selectList != null && selectList.size() > 0) {
				return selectList.get(0);
			} else {
				return null;
			}

		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException");
			selectList = null;
		} catch (Exception e) {
			logger.error("Exception caught in getTOOBRMBasketBrandModelInfoList()", e);
			throw new DAOException(e.getMessage(), e);
		}

		return null;

	}
	public List<String> getBundleVASList(String basketId, String mipBrand, String mipSimType) throws DAOException {
		logger.debug("getExcludedBundleVASList is called");
		List<String> bundleVASList = new ArrayList();
		String SQL = "SELECT i.ID as ID FROM w_basket_item_assgn bia, w_item i, (SELECT sysdate eff_da"
				+ "te FROM DUAL) d  "
				+ "WHERE bia.item_id = i.ID "
				+ "and bia.basket_id = :basketId "
				+ "and nvl(decode (i.MIP_BRAND, '9', :mipBrand , i.MIP_BRAND ), '1') = :mipBrand "
				+ "and nvl(decode (i.MIP_SIM_TYPE, 'X', :mipSimType, i.MIP_SIM_TYPE ), 'H') = :mipSimType "
				+ "AND i.type = 'BVAS' "
				+ "AND bia.eff_start_date <= d.eff_date AND (bia.eff_end_date >= d.eff_date OR bia.eff_end_date IS NULL)";

		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("basketId", basketId);
		params.addValue("mipBrand", mipBrand);
		params.addValue("mipSimType", mipSimType);
		
		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {

			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				String brand = new String();
				brand = rs.getString("ID");
				return brand;
			}
		};
		try {
			bundleVASList = simpleJdbcTemplate.query(SQL, mapper,
					params);
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("getBundleVASList() EmptyResultDataAccessException");
			bundleVASList = null;
		} catch (Exception e) {
			logger.error("Exception caught in getBundleVASList()", e);
			throw new DAOException(e.getMessage(), e);
		}
		return bundleVASList;
	}
	
	public List<String> getBasketItemList(String basketId, String mipBrand, String mipSimType) throws DAOException {
		logger.debug("getExcludedBundleVASList is called");
		List<String> bundleVASList = new ArrayList();
		String SQL = "SELECT i.ID as ID FROM w_basket_item_assgn bia, w_item i, (SELECT sysdate eff_da"
				+ "te FROM DUAL) d  "
				+ "WHERE bia.item_id = i.ID "
				+ "and bia.basket_id = :basketId "
				+ "and nvl(decode (i.MIP_BRAND, '9', :mipBrand , i.MIP_BRAND ), '1') = :mipBrand "
				+ "and nvl(decode (i.MIP_SIM_TYPE, 'X', :mipSimType, i.MIP_SIM_TYPE ), 'H') = :mipSimType "
				+ "and I.TYPE not in ('MNP_INC', 'AP_INC', 'SIM') "
				+ "AND bia.eff_start_date <= d.eff_date AND (bia.eff_end_date >= d.eff_date OR bia.eff_end_date IS NULL)";
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("basketId", basketId);
		params.addValue("mipBrand", mipBrand);
		params.addValue("mipSimType", mipSimType);
		
		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {

			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				String brand = new String();
				brand = rs.getString("ID");
				return brand;
			}
		};
		try {
			bundleVASList = simpleJdbcTemplate.query(SQL, mapper, params);
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("getBundleVASList() EmptyResultDataAccessException");
			bundleVASList = null;
		} catch (Exception e) {
			logger.error("Exception caught in getBundleVASList()", e);
			throw new DAOException(e.getMessage(), e);
		}
		return bundleVASList;
	}

	public List<String> getSelectedItemList(String orderId) throws DAOException {
		logger.debug("getSelectedItemList is called");
		List<String> bundleVASList = new ArrayList();
		// SQL modify by wilson, 20110810 - fixed bugs duplicate VAS after
		// Recall [and basket_id is null]
		String SQL = "select id from bomweb_subscribed_item where type='VAS' and basket_id is null and order_id=?";

		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {

			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				String brand = new String();
				brand = rs.getString("ID");
				return brand;
			}
		};
		try {
			bundleVASList = simpleJdbcTemplate.query(SQL, mapper,
					new Object[] { orderId });
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException");
			bundleVASList = null;
		} catch (Exception e) {
			logger.error("Exception caught in getSelectedItemList()", e);
			throw new DAOException(e.getMessage(), e);
		}
		return bundleVASList;
	}

	public void insertBomWebComponent(String orderId, ComponentDTO component)
			throws DAOException {
		StringBuffer SQL = new StringBuffer();
		SQL.append(
				"insert into BOMWEB_COMPONENT (ORDER_ID, ATTB_ID, ATTB_VALUE, CREATE_DATE) ")
				.append(" values (?, ?, ?,  sysdate)");

		try {
			simpleJdbcTemplate.update(SQL.toString(), orderId,
					component.getCompAttbId(), component.getCompAttbVal());

		} catch (Exception e) {
			logger.error("Exception caught in insertBomWebComponent()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}

	public List<ComponentDTO> getComponentList(String orderId)
			throws DAOException {
		logger.debug("getComponentList() is called");

		List<ComponentDTO> componentList = new ArrayList<ComponentDTO>();

		String SQL = "SELECT ORDER_ID, ATTB_ID, ATTB_VALUE, CREATE_DATE FROM BOMWEB_COMPONENT WHERE ORDER_ID = ?";

		ParameterizedRowMapper<ComponentDTO> mapper = new ParameterizedRowMapper<ComponentDTO>() {
			public ComponentDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				ComponentDTO dto = new ComponentDTO();
				dto.setOrderId(rs.getString("ORDER_ID"));
				dto.setCompAttbId(rs.getString("ATTB_ID"));
				dto.setCompAttbValue(rs.getString("ATTB_VALUE"));
				dto.setCreateDate(rs.getDate("CREATE_DATE"));

				return dto;
			}
		};

		try {
			// herbert 20111110 - remove useless SQL logger
			logger.debug("getComponentList() @ OrderDAO: " + SQL);
			componentList = simpleJdbcTemplate.query(SQL, mapper, orderId);

		} catch (EmptyResultDataAccessException erdae) {
			logger.info("getComponentList() EmptyResultDataAccessException");
			return null;
		} catch (Exception e) {
			logger.info("Exception caught in getComponentList():", e);
			throw new DAOException(e.getMessage(), e);
		}
		return componentList;

	}

	// add by wilson 20110503
	public void deleteBomWebNoneSuitableSim(String orderId, String simKeepItemId)
			throws DAOException {
		logger.debug("deleteBomWebNoneSuitableSim() is called");
		String SQL = "delete from bomweb_subscribed_item where order_id = ? and type = 'SIM' and id != ?";

		try {
			simpleJdbcTemplate.update(SQL, orderId, simKeepItemId);
		} catch (Exception e) {
			logger.error("Exception caught in deleteBomWebNoneSuitableSim()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}

	public List<String> getSignoffOrderList() throws DAOException {
		List<String> orderList = new ArrayList<String>();
		// logger.info("getSignOffOrderList is called");
		String SQL = "select ORDER_ID from BOMWEB_ORDER where ORDER_STATUS = '"
				+ BomWebPortalConstant.BWP_ORDER_SIGNOFF + "'";

		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				String orderId = new String();
				orderId = rs.getString("ORDER_ID");
				return orderId;
			}
		};
		try {
			orderList = simpleJdbcTemplate.query(SQL, mapper);
		} catch (EmptyResultDataAccessException erdae) {
			// logger.error("EmptyResultDataAccessException caught in getSignOffOrderList()",
			// erdae);
			orderList = null;
		} catch (Exception e) {
			// logger.error("Exception caught in getSignOffOrderList()", e);
			// throw new DAOException(e.getMessage(), e);
		}
		return orderList;
	}

	public SubscriberDTO getBomWebSub(String orderId) throws DAOException {
		logger.debug("getBomWebSub() is called");

		List<SubscriberDTO> subList = new ArrayList<SubscriberDTO>();
		String SQL = "select order_id,\n" + "                 sms_lang,\n"
				+ "                 ivrs_lang,\n"
				+ "                 ad_sup_ind,\n" + "                 pwd,\n"
				+ "                 pccw_sup_ind,\n"
				+ "                 tel_mk_sup_ind,\n"
				+ "                 bypass_ad_pwd_ind,\n"
				+ "                 child_lock,\n"
				+ "                 email_sup_ind,\n"
				+ "                 sms_sup_ind,\n"
				+ "                 dm_sup_ind,\n"
				+ "                 sip_ind,\n"
				+ "                 addr_pf_ind,\n"
				+ "                 addr_pf_ref,\n"
				+ "                 sub_tier,\n"
				+ "                 doc_copy_ind,\n"
				+ "                 usr_sa_cust_ind,\n"
				+ "                 create_date,\n"
				+ "                 suppress_cust_local_topup,\n"
				+ "                 suppress_cust_roam_topup,\n"
				+ "                 mob0060_opt_out_ind,\n"
				+ "                 ORIG_ACT_DATE,\n"
				+ "                 activation_cd,\n"
				+ "                 brand"
				+ "            from bomweb_sub\n"
				+ "           where order_id = ?";

		ParameterizedRowMapper<SubscriberDTO> mapper = new ParameterizedRowMapper<SubscriberDTO>() {
			public SubscriberDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				SubscriberDTO dto = new SubscriberDTO();
				dto.setOrderId(rs.getString("order_id"));// ,
				dto.setSmsLang(rs.getString("sms_lang"));// sms_lang,
				dto.setIvrsLang(rs.getString("ivrs_lang"));// ivrs_lang,
				dto.setAdultSuppressValue(rs.getString("ad_sup_ind"));// ad_sup_ind,
				dto.setPwd(rs.getString("pwd"));// pwd,
				dto.setPccwSuppressValue(rs.getString("pccw_sup_ind"));// pccw_sup_ind,
				dto.setTelemkSuppressValue(rs.getString("tel_mk_sup_ind"));// tel_mk_sup_ind,
				dto.setBypassAdultPwdValue(rs.getString("bypass_ad_pwd_ind"));// bypass_ad_pwd_ind,
				dto.setChildLockValue(rs.getString("child_lock"));// child_lock,
				dto.setEmailSuppressValue(rs.getString("email_sup_ind"));// email_sup_ind,
				dto.setSmsSuppressValue(rs.getString("sms_sup_ind"));// sms_sup_ind,
				dto.setDmSuppressValue(rs.getString("dm_sup_ind"));// dm_sup_ind,
				dto.setSipValue(rs.getString("sip_ind"));// sip_ind,
				dto.setAddrProofInd(rs.getString("addr_pf_ind"));// addr_pf_ind,
				dto.setAddrProofReferrer(rs.getString("addr_pf_ref"));// addr_pf_ref,
				dto.setSubTier(rs.getString("sub_tier"));// sub_tier,
				dto.setDocCopyInd(rs.getString("doc_copy_ind"));// doc_copy_ind,
				dto.setActUsrSameAsCustInd(rs.getString("usr_sa_cust_ind"));// usr_sa_cust_ind,
				dto.setMob0060OptOutInd("Y".equals(rs
						.getString("mob0060_opt_out_ind")) ? "Y" : "N"); // mob0060_opt_out_ind
				dto.setOrigActDate(rs.getTimestamp("ORIG_ACT_DATE"));
				dto.setActivationCd(rs.getString("activation_cd"));
				dto.setBrand(rs.getString("brand"));
				return dto;
			}
		};

		try {
			// herbert 20111110 - remove useless SQL logger
			logger.debug("getBomWebSub() @ OrderDAO: " + SQL);
			logger.debug("getBomWebSub() @ OrderDAO: ");
			subList = simpleJdbcTemplate.query(SQL, mapper, orderId);
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException");
			subList = null;
		} catch (Exception e) {
			logger.info("Exception caught in getBomWebSub():", e);
			throw new DAOException(e.getMessage(), e);
		}
		return subList.get(0);// only return the first one
	}

	public String getConciergeInd(String orderId) throws DAOException {

		String contractPeriod = null;
		logger.debug("getConciergeInd is called [orderId]: " + orderId);

		/*
		 * "select distinct bsi.order_id,\n" +
		 * "                 b.type basket_type,\n" +
		 * "                 decode(b.type, 6, 'Y', 'N') concierge_ind\n" +
		 * "   from bomweb_subscribed_item bsi, w_basket b\n" +
		 * "  where b.id = bsi.basket_id\n" + "    and bsi.order_id = ?";
		 */
		String SQL = "select distinct decode(b.type, 6, 'Y', 'N') concierge_ind\n"
				+ "   from bomweb_subscribed_item bsi, w_basket b\n"
				+ "  where b.id = bsi.basket_id\n"
				+ "    and bsi.order_id = ? and rownum=1";

		try {
			contractPeriod = (String) simpleJdbcTemplate.queryForObject(SQL,
					String.class, orderId);

		} catch (EmptyResultDataAccessException e) {

			return null;
		} catch (Exception e) {
			logger.info("Exception caught in getConciergeInd()", e);
			throw new DAOException(e.getMessage(), e);
		}
		return contractPeriod;
	}

	// add eliot 20110810
	// modify by eliot 20110815 for prevent diplicate order
	// modify by wilson 20111101 for optimus the query for mnp case
	// add LOB = 'MOB' by Herbert 20120424
	public String getAutoProcessOrderId() throws DAOException {

		String orderId = null;
		logger.debug("getAutoProcessOrderId is called ");

		/*
		 * String SQL = "select order_id from (" + " select order_id" +
		 * " from bomweb_order" + " where order_status = '" +
		 * BomWebPortalConstant.BWP_ORDER_SIGNOFF + "' and " + "ocid is null " +
		 * " order by create_date)" + " where rownum = 1";
		 */
		String SQL = "select ORDER_ID " + "from (select O.ORDER_ID, "
				+ " O.ORDER_STATUS, " + " O.SRV_REQ_DATE, "
				+ "M.CUT_OVER_DATE, " + "M.CUT_OVER_TIME "
				+ "from BOMWEB_ORDER O, BOMWEB_MNP M "
				+ "where O.ORDER_ID = M.ORDER_ID " + "and ORDER_STATUS = '"
				+ BomWebPortalConstant.BWP_ORDER_SIGNOFF + "'  "
				+ "and OCID is null " + "and O.LOB = 'MOB' "
				+ "order by M.CUT_OVER_DATE, M.CUT_OVER_TIME, O.SRV_REQ_DATE) "
				+ "where ROWNUM = 1";
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

	public String getBasketType(String orderId) throws DAOException {

		String baksetType = null;
		logger.info("getBaksetType is called [orderId]: " + orderId);

		String SQL = "select distinct b.type \n"
				+ "   from bomweb_subscribed_item bsi, w_basket b\n"
				+ "  where b.id = bsi.basket_id\n"
				+ "    and bsi.order_id = ? and rownum=1";

		try {
			baksetType = (String) simpleJdbcTemplate.queryForObject(SQL,
					String.class, orderId);

		} catch (EmptyResultDataAccessException e) {

			return null;
		} catch (Exception e) {
			logger.info("Exception caught in getBaksetType()", e);
			throw new DAOException(e.getMessage(), e);
		}
		return baksetType;
	}

	public void insertPDF_To_DB(final String orderId, byte[] pdfData)
			throws DAOException {
		logger.info("insertPDF_To_DB is called [orderId]: " + orderId);

		final byte[] _pdfData = pdfData;

		String SQL = "insert into w_ssform_pdf_store (order_Id, pdf_File) values (?, ?)";

		LobHandler lobHandler = new DefaultLobHandler();

		try {
			jdbcTemplate
					.execute(SQL,
							new AbstractLobCreatingPreparedStatementCallback(
									lobHandler) {
								protected void setValues(PreparedStatement ps,
										LobCreator lobCreator)
										throws SQLException {
									ps.setString(1, orderId);
									lobCreator.setBlobAsBytes(ps, 2, _pdfData);
								}
							});
		} catch (Exception e) {
			logger.error("Exception caught in insertPDF_To_DB()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}

	// ************************ START 
	// ************************
	// for SummaryController, to load list for checking
	// whether the sim no is used or available
	// parameter - orderId
	// - iccid - sim no.
	// return - List<String[]> - using sim no. list

	public List<String[]> getUsingSim(String orderId, String iccid)
			throws DAOException {

		List<String[]> simItemList = new ArrayList(); // [0]ORDER_ID, [1]ICCID

		String SQL = "select o.order_id, s.iccid\n"
				+ "  from bomweb_order o, bomweb_sim s\n"
				+ " where o.order_id = s.order_id\n" + "   and o.order_id != ?"
				+ "   and s.iccid = ? and o.order_status not in ( '"
				+ BomWebPortalConstant.BWP_ORDER_INITIAL + "', '"
				+ BomWebPortalConstant.BWP_ORDER_SUCCESS + "', '"
				+ BomWebPortalConstant.BWP_ORDER_REJECTED + "', '"
				+ BomWebPortalConstant.BWP_ORDER_FAILED + "', '"
				+ BomWebPortalConstant.BWP_ORDER_VOID + "', '"
				+ BomWebPortalConstant.BWP_ORDER_CANCELLED + "', '"
				+ BomWebPortalConstant.BWP_ORDER_CANCELLING + "')\n";

		ParameterizedRowMapper<String[]> mapper = new ParameterizedRowMapper<String[]>() {

			public String[] mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				String[] item = new String[2];
				item[0] = rs.getString("ORDER_ID");
				item[1] = rs.getString("ICCID");
				return item;
			}
		};
		try {
			simItemList = simpleJdbcTemplate.query(SQL, mapper, orderId, iccid);
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException");
			simItemList = null;
		} catch (Exception e) {
			logger.error("Exception caught in getUsingSim()", e);
			throw new DAOException(e.getMessage(), e);
		}

		return simItemList;

	}

	// ************************ END 
	// ************************
	public String getOrderStatus(String orderId) throws DAOException {

		logger.debug("getOrderStatus is called");
		String SQL = "select order_status from bomweb_order where order_id = ?";

		try {
			return (String) simpleJdbcTemplate.queryForObject(SQL,
					String.class, orderId);

		} catch (EmptyResultDataAccessException erdae) {
			logger.info("empty result received from getOrderStatus @ OrderDAO");
			return "-1";

		} catch (Exception e) {
			logger.error("Exception caught in getOrderStatus()", e);
			throw new DAOException(e.getMessage(), e);
		}

	}

	// get existing order id using same MRT
	// remove
	// +"AND trunc(O.CREATE_DATE) BETWEEN trunc(SYSDATE-7) AND trunc(SYSDATE) ",
	// 20120903 by wilson
	public String getOrderIdUsingSameMRT(String mrt, String orderId)
			throws DAOException {

		List<String> orderIdList = new ArrayList<String>();

		logger.debug("getOrderIdUsingSameMRT is called");
		String SQL = "SELECT MAX(ORDER_ID) ORDER_ID "
				+ "FROM "
				+ "( "
				+ "SELECT O.ORDER_ID FROM BOMWEB_ORDER O "
				+ "WHERE O.MSISDN  = :msisdn "
				+ "AND O.ORDER_ID != :orderId "
				+ "AND O.LOB = 'MOB' "
				+ "AND O.ORDER_STATUS not in ('VOID','04', 'CANCELLED') "
				+ "UNION "
				+ "SELECT M.MEMBER_ORDER_ID FROM BOMWEB_ORDER O "
				+ "JOIN BOMWEB_ORD_MOB_MEMBER M ON O.ORDER_ID = M.PARENT_ORDER_ID "
				+ "WHERE M.MSISDN  = :msisdn "
				+ "AND O.LOB = 'MOB' "
				+ "AND O.ORDER_ID != :orderId "
				+ "AND O.ORDER_STATUS not in ('VOID','04', 'CANCELLED') "
				+ ")";

		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("msisdn", mrt);
		params.addValue("orderId", orderId);
		
		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {

			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getString("ORDER_ID");
			}
		};

		try {
			orderIdList = simpleJdbcTemplate.query(SQL, mapper, params);
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException");
			orderIdList.add(0, "");
		} catch (Exception e) {
			logger.error("Exception caught in getOrderIdUsingSameMRT()", e);
			throw new DAOException(e.getMessage(), e);
		}

		if (orderIdList.size() == 0 || orderIdList == null) {
			orderIdList.add(0, "");
		}

		return orderIdList.get(0);

	}
	
	public String getOrderIdUsingSameMRTShop(String mrt, String orderId, String shopCd)
			throws DAOException {

		List<String> orderIdList = new ArrayList<String>();

		logger.debug("getOrderIdUsingSameMRT is called");
		String SQL = "SELECT MAX(ORDER_ID) ORDER_ID "
				+ "FROM "
				+ "( "
				+ "SELECT O.ORDER_ID FROM BOMWEB_ORDER O "
				+ "WHERE O.MSISDN  = :msisdn "
				+ "AND O.ORDER_ID != :orderId "
				+ "AND O.SHOP_CD = :shopCd "
				+ "AND O.LOB = 'MOB' "
				+ "AND O.ORDER_STATUS not in ('VOID','04', 'CANCELLING', 'CANCELLED','SUCCESS') "
				+ "UNION "
				+ "SELECT M.MEMBER_ORDER_ID FROM BOMWEB_ORDER O "
				+ "JOIN BOMWEB_ORD_MOB_MEMBER M ON O.ORDER_ID = M.PARENT_ORDER_ID "
				+ "WHERE M.MSISDN  = :msisdn "
				+ "AND O.LOB = 'MOB' "
				+ "AND O.ORDER_ID != :orderId "
				+ "AND O.SHOP_CD = :shopCd "
				+ "AND O.ORDER_STATUS not in ('VOID','04', 'CANCELLING', 'CANCELLED','SUCCESS') "
				+ ")";

		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("msisdn", mrt);
		params.addValue("orderId", orderId);
		params.addValue("shopCd", shopCd);
		
		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {

			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getString("ORDER_ID");
			}
		};

		try {
			orderIdList = simpleJdbcTemplate.query(SQL, mapper, params);
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException");
			orderIdList.add(0, "");
		} catch (Exception e) {
			logger.error("Exception caught in getOrderIdUsingSameMRT()", e);
			throw new DAOException(e.getMessage(), e);
		}

		if (orderIdList.size() == 0 || orderIdList == null) {
			orderIdList.add(0, "");
		}

		return orderIdList.get(0);

	}

	// add 2011-12-19 new order id format, eg.eg.RTP1M0000001
	// ***123456789012
	// eg.RTP1M0000001
	// eg.CTP1M0000001
	// eg.RTP1P0000001
	// rule
	// 1, first char R=retail shop, C=call center
	// 2-4, shop code
	// 5, M=MOB, P=LTS, ?=IMS
	// 6-12, sequence from bomweb_shop table
	public String getShopSeq(String shopCd, String channelId)
			throws DAOException {
		String orderId;
		String SQL = "select ?|| LPAD(seq_no, 7, '0') from bomweb_shop where shop_cd = ?";
		if (BomWebPortalConstant.CHANNEL_ID_MOBCCS.equals(channelId)) {
			SQL = "select 'C'||?||'M'|| LPAD(seq_no, 6, '0') from bomweb_shop where shop_cd = ?";
		} else {
			SQL = "select 'R'||?||'M'|| LPAD(seq_no, 6, '0') from bomweb_shop where shop_cd = ?";
		}
		try {
			orderId = (String) simpleJdbcTemplate.queryForObject(SQL,
					String.class, shopCd, shopCd);

		} catch (Exception e) {
			throw new DAOException(e.getMessage(), e);
		}
		logger.info("getShopSeq [OrderDAO] Get Order ID: " + orderId);
		return orderId;
	}

	public void deleteBomWebMobCcsMRT(String orderId) throws DAOException {
		logger.debug("deleteBomWebMobCcsMRT is called");
		String SQL = "delete  bomweb_mrt where order_id =?";

		try {
			simpleJdbcTemplate.update(SQL, orderId);

		} catch (Exception e) {
			logger.error("Exception caught in deleteBomWebMobCcsMRT()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}

	public void deleteBomWebMobCcsSupportDoc(String orderId)
			throws DAOException {
		logger.debug("deleteBomWebMobCcsSupportDoc is called");
		String SQL = "delete  bomweb_support_doc where order_id =?";

		try {
			simpleJdbcTemplate.update(SQL, orderId);

		} catch (Exception e) {
			logger.error("Exception caught in deleteBomWebMobCcsSupportDoc()",
					e);
			throw new DAOException(e.getMessage(), e);
		}
	}

	public void deleteBomWebMobCcsUpfrontPayment(String orderId)
			throws DAOException {
		logger.debug("deleteBomWebMobCcsUpfrontPayment is called");
		String SQL = "delete  bomweb_upfront_payment where order_id =?";

		try {
			simpleJdbcTemplate.update(SQL, orderId);

		} catch (Exception e) {
			logger.error(
					"Exception caught in deleteBomWebMobCcsUpfrontPayment()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}

	public void deleteBomWebMobCcsStaffInfo(String orderId) throws DAOException {
		logger.debug("deleteBomWebMobCcsStaffInfo is called");
		String SQL = "delete  bomweb_staff_info where order_id =?";

		try {
			simpleJdbcTemplate.update(SQL, orderId);

		} catch (Exception e) {
			logger.error("Exception caught in deleteBomWebMobCcsStaffInfo()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}

	public void deleteBomWebDelivery(String orderId) throws DAOException {
		logger.debug("deleteBomWebDelivery is called");
		String SQL = "delete bomweb_delivery where order_id =?";

		try {
			simpleJdbcTemplate.update(SQL, orderId);

		} catch (Exception e) {
			logger.error("Exception caught in deleteBomWebDelivery()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}

	public String findBasketId(String orderId) throws DAOException {
		logger.debug("findBasketId is called");

		List<String> resultList = new ArrayList<String>();

		String sql = "select distinct basket_id from bomweb_subscribed_item "
				+ "where order_id = ? and BASKET_ID is not null";// add and
																	// BASKET_ID
																	// is not
																	// null by
																	// wilson
																	// 20120726

		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {

			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getString("basket_id");
			}
		};

		try {
			resultList = simpleJdbcTemplate.query(sql, mapper, orderId);
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException");
		} catch (Exception e) {
			logger.error("Exception caught in getOrderIdUsingSameMRT()", e);
			throw new DAOException(e.getMessage(), e);
		}

		if (resultList != null && !resultList.isEmpty()) {
			return (String) resultList.get(0);
		} else {
			return null;
		}
	}

	// for mobccs inser quota info
	public int insertBomwebCustQuotaInUse(String idDocType, String idDocNum,
			String orderId, String userId, String basketId) throws DAOException {
		logger.debug("insertBomwebCustQuotaInUse is called");

		String SQL = "insert into BOMWEB_CUST_QUOTA_IN_USE BCQIU\n"
				+ "  (BCQIU.ID_DOC_TYPE,\n" + "   BCQIU.ID_DOC_NUM,\n"
				+ "   BCQIU.QUOTA_ID,\n" + "   BCQIU.ORDER_ID,\n"
				+ "   BCQIU.START_DATE,\n" + "   BCQIU.END_DATE,\n"
				+ "   BCQIU.LAST_UPD_BY,\n" + "   BCQIU.CREATE_BY)\n"
				+ "  select ? ID_DOC_TYPE,\n" + "         ? ID_DOC_NUM,\n"
				+ "         BP.PARM_TYPE_VAL QUOTA_ID,\n"
				+ "         ? ORDER_ID,\n" + "         sysdate START_DATE,\n"
				+ "         null END_DATE,\n" + "         ? LAST_UPD_BY,\n"
				+ "         ? CREATE_BY\n" + "    from W_BASKET_PARM BP\n"
				+ "   where BP.PARM_TYPE = 'QUOTA'\n"
				+ "     and BP.BASKET_ID = ?";

		try {
			return simpleJdbcTemplate.update(SQL, idDocType, idDocNum, orderId,
					userId, userId, basketId);

		} catch (Exception e) {
			logger.error("Exception caught in insertBomwebCustQuotaInUse()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}

	// add 20120214 wilson, preview, summary
	public String getOrderPaidAmount(String orderId) throws DAOException {

		logger.debug("getOrderPaidAmount is called");
		String SQL = "select nvl(sum(a.payment_amount),0) payment_amount from bomweb_payment_trans a\n"
				+ "where a.order_id =?";

		try {
			return Long.toString(simpleJdbcTemplate.queryForLong(SQL, orderId));

		} catch (Exception e) {
			logger.error("Exception caught in getOrderPaidAmount()", e);
			throw new DAOException(e.getMessage(), e);
		}

	}

	public void deleteBomWebCustQuotaInUse(String orderId) throws DAOException {
		logger.debug("deleteBomWebCustQuotaInUse is called");
		String SQL = "delete bomweb_cust_quota_in_use where order_id =?";

		try {
			simpleJdbcTemplate.update(SQL, orderId);

		} catch (Exception e) {
			logger.error("Exception caught in deleteBomWebCustQuotaInUse()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}

	// *Add by Herbert 20120220*//
	public void updateBomWebSim(MobileSimInfoDTO dto) throws DAOException {
		logger.debug("updateBomWebSim is called");
		String SQL = "UPDATE BOMWEB_SIM SET " + " ICCID = ?,\n"
				+ " IMSI = ?,\n" + " PUK1 = ?,\n" + " PUK2 = ?,\n"
				+ " ITEM_CODE = ?,\n" + " Last_UPD_DATE = sysdate, \n"
				+ " SIM_TYPE = ?\n"
				+ " WHERE order_id = ?";

		try {
			logger.info(dto.getIccid() + ":" + dto.getImsi() + ":"
					+ dto.getPuk1() + ":" + dto.getPuk2() + ":"
					+ dto.getItemCd() + ":" + dto.getOrderId());
			simpleJdbcTemplate.update(SQL, dto.getIccid(),// v_iccid,
					dto.getImsi(),// v_imsi,
					dto.getPuk1(),// v_puk1,
					dto.getPuk2(),// v_puk2,
					dto.getItemCd(),// v_item_code,
					dto.getSimBrandType(),
					dto.getOrderId()// v_order_id
					);

		} catch (Exception e) {
			logger.error("Exception caught in updateBomWebSim()", e);
			throw new DAOException(e.getMessage(), e);
		}

	}

	public String getOrderSimItemId(String orderId) throws DAOException {
		logger.debug("getOrderSimItemId is called");

		List<String> resultList = new ArrayList<String>();

		String sql = "select id from bomweb_subscribed_item "
				+ "where type='SIM' and order_id = ?";

		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {

			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getString("id");
			}
		};

		try {
			resultList = simpleJdbcTemplate.query(sql, mapper, orderId);
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException");
		} catch (Exception e) {
			logger.error("Exception caught in getOrderSimItemId()", e);
			throw new DAOException(e.getMessage(), e);
		}

		if (resultList != null && !resultList.isEmpty()) {
			return (String) resultList.get(0);
		} else {
			return null;
		}
	}

	public void updatePayment(PaymentDTO dto) throws DAOException {
		logger.debug("updatePayment is called");

		String SQLBOMWEBPAYMENT = "update BOMWEB_PAYMENT P\n"
				+ "   set P.CC_VERIFIED_IND = ?, P.LAST_UPD_BY = ?, P.LAST_UPD_DATE = sysdate\n"
				+ " where P.ORDER_ID = ?";

		try {
			simpleJdbcTemplate.update(SQLBOMWEBPAYMENT,
					dto.getCreditCardVerifiedInd(), dto.getLastUpdateBy(),
					dto.getOrderId());

		} catch (Exception e) {
			logger.error("Exception caught in updatePayment()", e);
			throw new DAOException(e.getMessage(), e);
		}

	}

	public void updateOrderFallOut(OrderDTO dto) throws DAOException {

		String sql = "update BOMWEB_ORDER O set O.REASON_CD = ?, "
				+ "O.LAST_UPDATE_BY = ?, O.LAST_UPDATE_DATE = sysdate, "
				+ "O.ORDER_STATUS = '99', O.CHECK_POINT = '999' "
				+ "where O.ORDER_ID = ?";

		try {
			simpleJdbcTemplate.update(sql, dto.getReasonCd(),
					dto.getLastUpdateBy(), dto.getOrderId());

		} catch (Exception e) {
			logger.error("Exception caught in updateOrderFallOut()", e);
			throw new DAOException(e.getMessage(), e);
		}

	}

	public String manualOrderStatusProcess(String orderId) throws DAOException {

		try {
			SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
					.withSchemaName("OPS$CNM")
					.withCatalogName("PKG_SB_MOB_ORDER")
					.withProcedureName("MANUAL_ORDER_STATUS_PROCESS");
			jdbcCall.setAccessCallParameterMetaData(false);
			jdbcCall.declareParameters(new SqlParameter("i_order_id",
					Types.VARCHAR), new SqlOutParameter("errCode",
					Types.INTEGER), new SqlOutParameter("errText",
					Types.VARCHAR));

			MapSqlParameterSource inMap = new MapSqlParameterSource();
			inMap.addValue("i_order_id", orderId);
			SqlParameterSource in = inMap;

			Map out = jdbcCall.execute(in);
			int error_code = BomWebPortalConstant.ERRCODE_FAIL;

			if (((Integer) out.get("errCode")) != null) {
				error_code = ((Integer) out.get("errCode")).intValue();
			}

			String error_text = (String) out.get("errText");

			logger.info("PKG_SB_MOB_ORDER.ORDER_STATUS_PROCESS()() output error_code = "
					+ error_code);
			logger.info("PKG_SB_MOB_ORDER.ORDER_STATUS_PROCESS()() output error_text = "
					+ error_text);

			return error_text;

		} catch (Exception e) {
			logger.error("Exception caught in orderStatusProcess()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}

	public String orderSubmitProcess(String orderId) throws DAOException {

		try {
			SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
					.withSchemaName("OPS$CNM")
					.withCatalogName("PKG_SB_MOB_ORDER")
					.withProcedureName("ORDER_SUBMIT_PROCESS");
			jdbcCall.setAccessCallParameterMetaData(false);
			jdbcCall.declareParameters(new SqlParameter("i_process_date",
					Types.VARCHAR), new SqlParameter("i_batch_no",
					Types.INTEGER), new SqlParameter("i_order_id",
					Types.VARCHAR), new SqlOutParameter("errCode",
					Types.INTEGER), new SqlOutParameter("errText",
					Types.VARCHAR));

			MapSqlParameterSource inMap = new MapSqlParameterSource();
			inMap.addValue("i_process_date",
					Utility.date2String(new Date(), "yyyyMMdd"));
			inMap.addValue("i_batch_no", 0);
			inMap.addValue("i_order_id", orderId);
			SqlParameterSource in = inMap;

			Map out = jdbcCall.execute(in);
			int error_code = BomWebPortalConstant.ERRCODE_FAIL;

			if (((Integer) out.get("errCode")) != null) {
				error_code = ((Integer) out.get("errCode")).intValue();
			}

			String error_text = (String) out.get("errText");

			logger.info("PKG_SB_MOB_ORDER.ORDER_SUBMIT_PROCESS()() output error_code = "
					+ error_code);
			logger.info("PKG_SB_MOB_ORDER.ORDER_SUBMIT_PROCESS()() output error_text = "
					+ error_text);

			return error_text;

		} catch (Exception e) {
			logger.error("Exception caught in orderStatusProcess()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}

	/*
	 * //add by wilson 20120320, for dummy basket display original selected vas
	 * item list public List<VasDetailDTO> getSelectedVasList(String locale,
	 * String orderId) throws DAOException { List<VasDetailDTO> vasDetailList =
	 * new ArrayList<VasDetailDTO>();
	 * 
	 * String SQL = "select idl.item_id,\n" + "       i.lob item_lob,\n" +
	 * "       i.type item_type,\n" + "       bia.mdo_ind item_mdo_ind,\n" +
	 * "       idl.html,\n" + "       idl.html2,\n" + "       idl.locale,\n" +
	 * "       idl.display_type,\n" + "       ip.onetime_amt,\n" +
	 * "       ip.recurrent_amt,\n" + "       bia.display_seq\n" +
	 * "  from (select bwsi.order_id,\n" + "               item_id,\n" +
	 * "               display_type,\n" + "               html,\n" +
	 * "               html2,\n" + "               locale,\n" +
	 * "               basket_id,\n" +
	 * "               dense_rank() OVER(PARTITION BY item_id order by item_id asc, display_type desc) rank\n"
	 * +
	 * "          from w_item_display_lkup widl, bomweb_subscribed_item bwsi\n"
	 * +
	 * "         where widl.item_id = bwsi.id and bwsi.type='VAS' and bwsi.basket_id is null\n"
	 * + "           AND bwsi.order_id = ?\n" +
	 * "           and widl.display_type in ('ITEM_SELECT', 'QUOTATION')\n" +
	 * "           and widl.locale = ?) idl, -----------\n" +
	 * "       w_basket_item_assgn bia,\n" + "       w_item i,\n" +
	 * "       w_item_pricing ip,\n" + "       bomweb_order bwo\n" +
	 * " where bwo.order_id = idl.order_id\n" +
	 * "   and idl.item_id = bia.item_id(+)\n" +
	 * "   and idl.basket_id = bia.basket_id(+)\n" +
	 * "   and idl.item_id = i.id\n" + "   and i.id = ip.id\n" +
	 * "   and ip.eff_start_date <= TRUNC(bwo.app_date)\n" +
	 * "   and (ip.eff_end_date is null or ip.eff_end_date >= TRUNC(bwo.app_date))\n"
	 * + "   and idl.rank = 1\n" + " order by bia.display_seq";
	 * 
	 * ParameterizedRowMapper<VasDetailDTO> mapper = new
	 * ParameterizedRowMapper<VasDetailDTO>() {
	 * 
	 * // notice the return type with respect to Java 5 covariant return //
	 * types public VasDetailDTO mapRow(ResultSet rs, int rowNum) throws
	 * SQLException { VasDetailDTO vasDto = new VasDetailDTO();
	 * vasDto.setItemHtml(rs.getString("html"));
	 * vasDto.setItemId(rs.getString("item_id"));
	 * vasDto.setItemLob(rs.getString("item_lob"));
	 * vasDto.setItemType(rs.getString("item_type"));
	 * vasDto.setItemMdoInd(rs.getString("item_mdo_ind"));
	 * vasDto.setItemHtml2(rs.getString("html2"));
	 * vasDto.setItemLocale(rs.getString("locale"));
	 * vasDto.setItemDisplayType(rs.getString("display_type"));
	 * vasDto.setItemOnetimeAmt(rs.getString("onetime_amt"));
	 * vasDto.setItemRecurrentAmt(rs.getString("recurrent_amt"));
	 * 
	 * return vasDto; } };
	 * 
	 * try { logger.debug("getSelectedVasList @ OrderDAO: " + SQL);
	 * vasDetailList = simpleJdbcTemplate.query(SQL, mapper, orderId, locale);
	 * 
	 * } catch (EmptyResultDataAccessException erdae) {
	 * logger.info("EmptyResultDataAccessException"); vasDetailList = null; }
	 * catch (Exception e) {
	 * logger.error("Exception caught in getSelectedVasList()", e); throw new
	 * DAOException(e.getMessage(), e); } return vasDetailList;
	 * 
	 * }
	 */

	public void insertOrderFallout(String orderId, String userName,
			String failType, String reasonCd, String remark)
			throws DAOException {
		logger.debug("insertOrderFallout is called");

		String SQL = "insert into BOMWEB_ORDER_FALLOUT " + "  (ORDER_ID, "
				+ "	REPORT_BY, " + "	REMARK, " + "   OCCURANCE_DATE,"
				+ "   FALLOUT_CAT," + "   REASON_CODE," + "   CREATE_BY,"
				+ "   CREATE_DATE," + "   LAST_UPD_BY," + "   LAST_UPD_DATE) "
				+ "  values ("
				+ "  ?, ?, ?, sysdate, ?, ?, ?, sysdate, ?, sysdate)";

		try {
			simpleJdbcTemplate.update(SQL, orderId, userName, remark, failType,
					reasonCd, userName, userName);

		} catch (Exception e) {
			logger.error("Exception caught in insertOrderFallout()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}

	// add by wilson 20120320, for dummy basket display original selected vas
	// item list
	// MIP.P4 modification
	public List<VasDetailDTO> getSelectedVasList(String locale, String orderId,
			String channelId, String basketId, String appDate, String channelCd, String offerNature)
			throws DAOException {
		List<VasDetailDTO> vasDetailList = new ArrayList<VasDetailDTO>();
		// MIP.P4 modification
		logger.debug("MIP.P4 modification: pkg_sb_mob_stock.get_stock_count_disp_mip4 (X)");
		String SQL = "select ITEM_DISP.ITEM_ID,\n"
				+ "       ITEM_DISP.ITEM_LOB,\n"
				+ "       ITEM_DISP.ITEM_TYPE,\n"
				+ "       ITEM_DISP.ITEM_MDO_IND,\n"
				+ "       ITEM_DISP.HTML,\n"
				+ "       ITEM_DISP.HTML2,\n"
				+ "       ITEM_DISP.LOCALE,\n"
				+ "       ITEM_DISP.DISPLAY_TYPE,\n"
				+ "       ITEM_DISP.ONETIME_AMT,\n"
				+ "       ITEM_DISP.RECURRENT_AMT,\n"
				+ "       NVL(NVL((select IPRO.DISPLAY_SEQ\n"
				+ "                 from W_ITEM_PRE_REQUISITE_OR IPRO, W_BASKET_ITEM_ASSGN BIA\n"
				+ "                where IPRO.REQUIRED_ITEM_ID = BIA.ITEM_ID\n"
				+ "                  and BIA.BASKET_ID = :basketId ---------------------100000738\n"
				+ "                  and IPRO.ITEM_ID = ITEM_DISP.ITEM_ID\n"
				+ "                  and TO_DATE(:appDate, 'DD/MM/YYYY') between\n"
				+ "                      BIA.EFF_START_DATE and NVL(BIA.EFF_END_DATE, sysdate) --':INPUT_DATE'--app_date--\n"
				+ "                  and TO_DATE(:appDate, 'DD/MM/YYYY') between\n"
				+ "                      IPRO.EFF_START_DATE and\n"
				+ "                      NVL(IPRO.EFF_END_DATE, sysdate) --app_date--\n"
				+ "               ),\n"
				+ "               (select NVL(VIGA.DISPLAY_SEQ, 5)\n"
				+ "                  from W_VAS_ITEM_GRP_ASSGN VIGA, W_CHANNEL_BASKET_ASSGN CBA\n"
				+ "                 where CBA.CHANNEL_ID = :channelId ------------------2\n"
				+ "                   and CBA.BASKET_ID = :basketId ------------------100000738\n"
				+ "                   and CBA.VAS_ITEM_GRP_ID = VIGA.GRP_ID\n"
				+ "                   and VIGA.ITEM_ID = ITEM_DISP.ITEM_ID)),\n"
				+ "           0) DISPLAY_SEQ,\n"
				+ "       ----STOCK_COUNT sql\n"
				+ "       (select pkg_sb_mob_stock.get_stock_count_disp_mip4('PEND', :channelCd, IPPA.POS_ITEM_CD, :offerNature) STOCK_COUNT\n"
				+ "          from W_ITEM_PRODUCT_POS_ASSGN IPPA\n"
				+ "         where IPPA.ITEM_ID = ITEM_DISP.ITEM_ID) STOCK_COUNT\n"
				+ "-----END STOCK_COUNT sql\n"
				+ "  from ( --item display,pricing info\n"
				+ "        select I.ID ITEM_ID,\n"
				+ "                I.LOB ITEM_LOB,\n"
				+ "                I.TYPE ITEM_TYPE,\n"
				+ "                'O' ITEM_MDO_IND,\n"
				+ "                IDL.HTML,\n"
				+ "                IDL.HTML2,\n"
				+ "                IDL.LOCALE,\n"
				+ "                IDL.DISPLAY_TYPE,\n"
				+ "                IP.ONETIME_AMT,\n"
				+ "                IP.RECURRENT_AMT\n"
				+ "          from W_ITEM I, W_ITEM_DISPLAY_LKUP IDL, W_ITEM_PRICING IP\n"
				+ "         where I.ID = IDL.ITEM_ID\n"
				+ "           and IDL.LOCALE = :locale ------------------- LOCALE\n"
				+ "           and IDL.DISPLAY_TYPE = 'ITEM_SELECT'\n"
				+ "           and I.ID = IP.ID\n"
				+ "           and TO_DATE(:appDate, 'DD/MM/YYYY') between IP.EFF_START_DATE and\n"
				+ "               NVL(IP.EFF_END_DATE, sysdate) ------------------app_date--\n"
				+ "        ) ITEM_DISP,\n"
				+ "       ((select ID ITEM_ID\n"
				+ "           from BOMWEB_SUBSCRIBED_ITEM BWSI\n"
				+ "          where BWSI.TYPE = 'VAS'\n"
				+ "            and BWSI.BASKET_ID is null\n"
				+ "            and BWSI.ORDER_ID = :orderId--------------orderId\n"
				+ "\n"
				+ "         )\n"
				+ "        minus( --basket vas group item\n"
				+ "              (select VIGA.ITEM_ID\n"
				+ "                 from W_CHANNEL_BASKET_ASSGN CBA, W_VAS_ITEM_GRP_ASSGN VIGA\n"
				+ "                where CBA.VAS_ITEM_GRP_ID = VIGA.GRP_ID\n"
				+ "                  and CBA.CHANNEL_ID = :channelId ------------------- CHANNEL_ID\n"
				+ "                  and TO_DATE(:appDate, 'DD/MM/YYYY') between\n"
				+ "                      VIGA.EFF_START_DATE and NVL(VIGA.EFF_END_DATE, sysdate) ------------------app_date--\n"
				+ "                  and CBA.BASKET_ID = :basketId -------------------100000738\n"
				+ "               union\n"
				+ "               --item pre item\n"
				+ "               select IPRO.ITEM_ID\n"
				+ "                 from W_CHANNEL_BASKET_ASSGN  CBA,\n"
				+ "                      W_BASKET_ITEM_ASSGN     BIA,\n"
				+ "                      W_ITEM_PRE_REQUISITE_OR IPRO\n"
				+ "                where BIA.ITEM_ID = IPRO.REQUIRED_ITEM_ID\n"
				+ "                  and CBA.CHANNEL_ID = :channelId ------------------- CHANNEL_ID\n"
				+ "                  and CBA.BASKET_ID = BIA.BASKET_ID \n"   //ADD 20160304
				+ "                  and TO_DATE(:appDate, 'DD/MM/YYYY') between\n"
				+ "                      BIA.EFF_START_DATE and NVL(BIA.EFF_END_DATE, sysdate) ------------------app_date--\n"
				+ "                  and TO_DATE(:appDate, 'DD/MM/YYYY') between\n"
				+ "                      IPRO.EFF_START_DATE and NVL(IPRO.EFF_END_DATE, sysdate) ------------------app_date--\n"
				+ "                  and BIA.BASKET_ID = :basketId -------------------100000738\n"
				+ "               ) minus\n"
				+ "              (select ITEM_ID_A\n"
				+ "                 from W_MOB_ITEM_EXCLUSIVE_LKUP A\n"
				+ "                where ITEM_ID_B in\n"
				+ "                      (select ITEM_ID\n"
				+ "                         from W_BASKET_ITEM_ASSGN BIA\n"
				+ "                        where BIA.BASKET_ID = :basketId ------------- 100000738\n"
				+ "                          and BIA.MDO_IND = 'M'\n"
				+ // mark by wilson 2011-11-07, no care MDO_IND, add back
					// 20120509
				"                          and TO_DATE(:appDate, 'DD/MM/YYYY') between\n"
				+ "                              BIA.EFF_START_DATE and\n"
				+ "                              NVL(BIA.EFF_END_DATE, sysdate)) ------------------app_date--\n"
				+ "                  and ITEM_TYPE_B = 'BVAS'\n"
				+ "                  and ITEM_TYPE_A = 'VAS'\n"
				+ "               union (select distinct A.ITEM_ID\n"
				+ "                       from W_ITEM_PRE_REQUISITE_OR A\n"
				+ "                      where TO_DATE(:appDate, 'DD/MM/YYYY') between\n"
				+ "                            A.EFF_START_DATE and NVL(A.EFF_END_DATE, sysdate) ------------------app_date--\n"
				+ "                     minus\n"
				+ "                     select IPRO.ITEM_ID\n"
				+ "                       from W_ITEM_PRE_REQUISITE_OR IPRO,\n"
				+ "                            W_BASKET_ITEM_ASSGN     BIA\n"
				+ "                      where IPRO.REQUIRED_ITEM_ID = BIA.ITEM_ID\n"
				+ "                        and TO_DATE(:appDate, 'DD/MM/YYYY') between\n"
				+ "                            BIA.EFF_START_DATE and\n"
				+ "                            NVL(BIA.EFF_END_DATE, sysdate) ------------------app_date--\n"
				+ "                        and TO_DATE(:appDate, 'DD/MM/YYYY') between\n"
				+ "                            IPRO.EFF_START_DATE and\n"
				+ "                            NVL(IPRO.EFF_END_DATE, sysdate) ------------------app_date--\n"
				+ "                        and BIA.BASKET_ID = :basketId ------------- 100000738\n"
				+ "                     ))) ---original vas group\n"
				+ "       ) ITEM_LIST\n"
				+ " where ITEM_DISP.ITEM_ID = ITEM_LIST.ITEM_ID\n"
				+ " order by DISPLAY_SEQ";

		ParameterizedRowMapper<VasDetailDTO> mapper = new ParameterizedRowMapper<VasDetailDTO>() {
			public VasDetailDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				VasDetailDTO vasDto = new VasDetailDTO();
				vasDto.setItemHtml(rs.getString("html"));
				vasDto.setItemId(rs.getString("item_id"));
				vasDto.setItemLob(rs.getString("item_lob"));
				vasDto.setItemType(rs.getString("item_type"));
				vasDto.setItemMdoInd(rs.getString("item_mdo_ind"));
				vasDto.setItemHtml2(rs.getString("html2"));
				vasDto.setItemLocale(rs.getString("locale"));
				vasDto.setItemDisplayType(rs.getString("display_type"));
				vasDto.setItemOnetimeAmt(rs.getString("onetime_amt"));
				vasDto.setItemRecurrentAmt(rs.getString("recurrent_amt"));

				return vasDto;
			}
		};
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("locale", locale);
		params.addValue("orderId", orderId);
		params.addValue("channelId", channelId);
		params.addValue("basketId", basketId);
		params.addValue("appDate", appDate);
		params.addValue("channelCd", channelCd);
		// MIP.P4 modification
		params.addValue("offerNature", offerNature);

		try {
			logger.debug("getSelectedVasList @ OrderDAO: " + SQL);
			vasDetailList = simpleJdbcTemplate.query(SQL, mapper, params);

		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException");
			vasDetailList = null;
		} catch (Exception e) {
			logger.error("Exception caught in getSelectedVasList()", e);
			throw new DAOException(e.getMessage(), e);
		}
		return vasDetailList;

	}

	public void synBomOrderStatus(String orderId, String orderStatus,
			String lastUpdateBy) throws DAOException {

		logger.debug("synBomOrderStatus is called");

		String SQL = "update bomweb_order set order_status = ?, "
				+ "last_update_by = ?, last_update_date = sysdate where order_id = ?";

		try {
			simpleJdbcTemplate.update(SQL, orderStatus, lastUpdateBy, orderId);
		} catch (Exception e) {
			logger.error("Exception caught in synBomOrderStatus()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}

	public List<MobCcsFalloutUI> getOrderFalloutHist(String orderId)
			throws DAOException {
		logger.debug("getOrderFalloutHist is called");

		String SQL = "select reason_code, remark, report_by, create_date, occurance_date "
				+ "from BOMWEB_ORDER_FALLOUT "
				+ "where order_id = ? "
				+ "order by create_date desc";

		ParameterizedRowMapper<MobCcsFalloutUI> mapper = new ParameterizedRowMapper<MobCcsFalloutUI>() {

			public MobCcsFalloutUI mapRow(ResultSet rs, int rowNum)
					throws SQLException {

				MobCcsFalloutUI dto = new MobCcsFalloutUI();

				dto.setFalloutCode(rs.getString("reason_code"));
				dto.setRemarks(rs.getString("remark"));
				dto.setStaffId(rs.getString("report_by"));
				dto.setCreateDate(rs.getDate("create_date"));
				dto.setOccuranceDate(rs.getTimestamp("occurance_date"));

				return dto;
			}
		};

		try {
			return simpleJdbcTemplate.query(SQL, mapper, orderId);

		} catch (Exception e) {
			logger.error("Exception caught in getOrderFalloutHist()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}

	public void updateOrderSrvReqDate(String orderId,
			java.util.Date srvReqDate, String username) throws DAOException {
		logger.debug("updateOrderSerReqDate is called");

		String SQL = "update bomweb_order set srv_req_date = ?, "
				+ "last_update_by = ?, last_update_date = sysdate where order_id = ? ";

		try {
			simpleJdbcTemplate.update(SQL, srvReqDate, username, orderId);

		} catch (Exception e) {
			logger.error("Exception caught in updateOrderSerReqDate()", e);
			throw new DAOException(e.getMessage(), e);
		}

	}

	public void updateOrderReasonCode(String orderId, String reasonCode,
			String username) throws DAOException {
		logger.debug("updateOrderReasonCode is called");

		String SQL = "update bomweb_order set reason_cd = ?, "
				+ "last_update_by = ?, last_update_date = sysdate where order_id = ? ";

		try {
			simpleJdbcTemplate.update(SQL, reasonCode, username, orderId);

		} catch (Exception e) {
			logger.error("Exception caught in updateOrderReasonCode()", e);
			throw new DAOException(e.getMessage(), e);
		}

	}

	/**
	 * Get Order ID by OCID
	 * 
	 * @return
	 * @throws DAOException
	 */
	public String getOrderId(String ocid) throws DAOException {
		logger.debug("getOrderId is called");

		String SQL = "select order_id from bomweb_order where ocid = ?";

		try {
			return (String) simpleJdbcTemplate.queryForObject(SQL,
					String.class, ocid);
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("getOrderId() EmptyResultDataAccessException");
		} catch (Exception e) {
			logger.error("Exception caught in getOrderId()", e);
			throw new DAOException(e.getMessage(), e);
		}
		return null;
	}

	// not include attb_id in W_ITEM_ATTB_ASSGN table, by wilson 20120508
	// copy from getComponentList function
	public List<ComponentDTO> getPassToBomComponentList(String orderId)
			throws DAOException {
		logger.debug("getPassToBomComponentList() is called");

		List<ComponentDTO> componentList = new ArrayList<ComponentDTO>();

		String SQL = "select C.ORDER_ID, C.ATTB_ID, C.ATTB_VALUE, C.CREATE_DATE\n"
				+ "  from BOMWEB_COMPONENT C\n"
				+ " where C.ORDER_ID = ?\n"
				+ "   and C.ATTB_ID not in (select IA.ATTB_ID from W_ITEM_ATTB_ASSGN IA)";
		String SQLchoice2 = "select C.ORDER_ID, C.ATTB_ID, C.ATTB_VALUE, C.CREATE_DATE\n"
				+ "  from BOMWEB_COMPONENT C, W_PRODUCT_ATTB_ASSGN PAA\n"
				+ " where C.ORDER_ID = ?\n" + "   and C.ATTB_ID = PAA.ATTB_ID";

		ParameterizedRowMapper<ComponentDTO> mapper = new ParameterizedRowMapper<ComponentDTO>() {
			public ComponentDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				ComponentDTO dto = new ComponentDTO();
				dto.setOrderId(rs.getString("ORDER_ID"));
				dto.setCompAttbId(rs.getString("ATTB_ID"));
				dto.setCompAttbValue(rs.getString("ATTB_VALUE"));
				dto.setCreateDate(rs.getDate("CREATE_DATE"));

				return dto;
			}
		};

		try {
			logger.debug("getPassToBomComponentList() @ OrderDAO: ");
			logger.debug("getPassToBomComponentList() @ OrderDAO: " + SQL);
			componentList = simpleJdbcTemplate.query(SQL, mapper, orderId);

		} catch (EmptyResultDataAccessException erdae) {
			logger.info("getPassToBomComponentList() EmptyResultDataAccessException");
			return null;
		} catch (Exception e) {
			logger.info("Exception caught in getPassToBomComponentList():", e);
			throw new DAOException(e.getMessage(), e);
		}
		return componentList;

	}

	public List<MultipleMrtSimDTO> getMultipleMrtSimDTOList(String orderId)
			throws DAOException {
		logger.debug("getMultipleMrtSimDTOList() is called");

		List<MultipleMrtSimDTO> dtoList = new ArrayList<MultipleMrtSimDTO>();

		String SQL = "select ROWNUM, ZZ.*\n"
				+ "from (select A.ORDER_ID\n"
				+ "            ,A.ITEM_ID    MRT_ITEM_ID\n"
				+ "            ,A.ATTB_ID    MRT_ATTB_ID\n"
				+ "            ,A.ATTB_VALUE MRT_MSISDN\n"
				+ "            ,B.ITEM_ID    SIM_ITEM_ID\n"
				+ "            ,B.ATTB_ID    SIM_ATTB_ID\n"
				+ "            ,B.ATTB_VALUE SIM_ICCID\n"
				+ "      from (select distinct C.ORDER_ID\n"
				+ "                           ,IAA.ATTB_ID ITEM_ID\n"
				+ "                           ,IAA.ATTB_ID\n"
				+ "                           ,C.ATTB_VALUE\n"
				+ "            from BOMWEB_COMPONENT C, W_MAPPING_LKUP ML, W_ITEM_ATTB_ASSGN IAA\n"
				+ "            where ML.MAP_TYPE = 'MULTI_MRT_SIM_MAP_BY_ATTB_ID'\n"
				+ "            and ML.MAP_FROM = IAA.ATTB_ID\n"
				+ "            and IAA.ATTB_ID = C.ATTB_ID\n"
				+ "            and C.ORDER_ID = ? --orderId\n"
				+ "            ) A\n"
				+ "          ,(select distinct C.ORDER_ID\n"
				+ "                           ,IAA.ATTB_ID ITEM_ID\n"
				+ "                           ,IAA.ATTB_ID\n"
				+ "                           ,C.ATTB_VALUE\n"
				+ "            from BOMWEB_COMPONENT  C\n"
				+ "                ,W_MAPPING_LKUP    ML\n"
				+ "                ,W_ITEM_ATTB_ASSGN IAA\n"
				+ "            where ML.MAP_TYPE = 'MULTI_MRT_SIM_MAP_BY_ATTB_ID'\n"
				+ "            and ML.MAP_TO = IAA.ATTB_ID\n"
				+ "            and IAA.ATTB_ID = C.ATTB_ID\n"
				+ "            and C.ORDER_ID = ? --orderId\n"
				+ "            ) B\n" + "          ,W_MAPPING_LKUP C\n"
				+ "      where A.ATTB_ID = C.MAP_FROM\n"
				+ "      and B.ATTB_ID = C.MAP_TO\n"
				+ "      and C.MAP_TYPE = 'MULTI_MRT_SIM_MAP_BY_ATTB_ID'\n"
				+ "      order by MRT_ITEM_ID) ZZ";

		ParameterizedRowMapper<MultipleMrtSimDTO> mapper = new ParameterizedRowMapper<MultipleMrtSimDTO>() {
			public MultipleMrtSimDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				MultipleMrtSimDTO dto = new MultipleMrtSimDTO();

				dto.setRownum(rs.getString("rownum"));
				dto.setOrderId(rs.getString("ORDER_ID"));
				dto.setMrtItemId(rs.getString("MRT_ITEM_ID"));
				dto.setMrtAttbId(rs.getString("MRT_ATTB_ID"));
				dto.setMrtMsisdn(rs.getString("MRT_MSISDN"));
				dto.setSimItemId(rs.getString("SIM_ITEM_ID"));
				dto.setSimAttbId(rs.getString("SIM_ATTB_ID"));
				dto.setSimIccid(rs.getString("SIM_ICCID"));

				return dto;
			}
		};

		try {
			logger.debug("getMultipleMrtSimDTOList() @ OrderDAO: ");
			logger.debug("getMultipleMrtSimDTOList() @ OrderDAO: " + SQL);
			dtoList = simpleJdbcTemplate.query(SQL, mapper, orderId, orderId);

		} catch (EmptyResultDataAccessException erdae) {
			logger.info("getMultipleMrtSimDTOList() EmptyResultDataAccessException");
			return null;
		} catch (Exception e) {
			logger.info("Exception caught in getMultipleMrtSimDTOList():", e);
			throw new DAOException(e.getMessage(), e);
		}
		return dtoList;

	}

	public void removeUrgentInd(OrderDTO dto) throws DAOException {

		String sql = "update BOMWEB_DELIVERY DL\n"
				+ "          set DL.URGENT_IND    = 'N',\n"
				+ "              DL.LAST_UPD_BY   = ?,\n"
				+ "              DL.LAST_UPD_DATE = sysdate\n"
				+ "        where DL.ORDER_ID = ?";

		try {
			simpleJdbcTemplate.update(sql, dto.getLastUpdateBy(),
					dto.getOrderId());

		} catch (Exception e) {
			logger.error("Exception caught in removeUrgentInd()", e);
			throw new DAOException(e.getMessage(), e);
		}

	}

	private static final String insertfutherMrtSQL = "insert into BOMWEB_MRT\n"
			+ "  (ORDER_ID,\n"
			+ "   SEQ_ID,\n"
			+
			// "   MNP_IND,\n" +
			"   MSISDN,\n"
			+
			// "   MSISDNLVL,\n" +
			// "   SRV_REQ_DATE,\n" +
			"   CUT_OVER_DATE,\n"
			+ "   CUT_OVER_TIME,\n"
			+
			// "   RNO,\n" +
			// "   DNO,\n" +
			"   CUST_NAME,\n"
			+ " CUST_NAME_CHI,\n"
			+ "   DOC_NO,\n"
			+ "   PREPAID_SIM_DOC_IND,\n"
			+ "   MNP_TICKET_NUM,\n"
			+
			// "   CREATE_BY,\n" +
			// "   LAST_UPD_BY,\n" +
			"   LAST_UPD_DATE\n"
			+
			// "   CREATE_DATE\n" +
			")values(\n"
			+ "  ? ,-- ORDER_ID,\n"
			+ "   ? ,-- SEQ_ID,\n"
			+
			// "   ? -- MNP_IND,\n" +
			"   ?, -- MSISDN,\n"
			+
			// "   ? -- MSISDNLVL,\n" +
			// "   ? -- SRV_REQ_DATE,\n" +
			"   ?, -- CUT_OVER_DATE,\n"
			+ "   ?, -- CUT_OVER_TIME,\n"
			+
			// "   ? -- RNO,\n" +
			// "   ? -- DNO,\n" +
			"   ?, -- CUST_NAME,\n" + "   ?, --CUST_NAME_CHI,\n"
			+ "   ?, -- DOC_NO,\n" + "   ?, -- PREPAID_SIM_DOC_IND,\n"
			+ "   ?, --MNP_TICKET_NUM,\n" +
			// "   ? --CREATE_BY,\n" +
			// "   ? -- LAST_UPD_BY,\n" +
			"   sysdate --LAST_UPD_DATE\n" +
			// "   sysdate -- CREATE_DATE\n" +
			"   )";

	public int insertFutherMrt(MnpDTO dto, OrderDTO orderDto)
			throws DAOException {
		logger.debug("insertMobCcsMrt is called");

		// mrtType=MNP_IND=Y==>NEW_NUMBER
		// mrtType=MNP_IND=N==>MNP_NUMBER
		// mrtType=MNP_IND=A==>FUTHER_MNP_NUMBER
		try {
			logger.info("insertFutherMrt() @ orderDao: " + insertfutherMrtSQL);
			return simpleJdbcTemplate.update(
					insertfutherMrtSQL,
					// start sql mapping
					dto.getOrderId(),
					2,
					// orderDto.getMnpInd(),
					dto.getFutherMnpNumber(),
					// dto.getMsisdnLvl(),
					// dto.getServiceReqDate(),
					dto.getFutherCutoverDate(),
					dto.getFutherCutoverTime(),
					// dto.getRno(),
					// dto.getDno(),
					dto.getFuthercustName(), dto.getFuthercustNameChi(),
					dto.getFuthercustIdDocNum(), dto.getPrePaidSimDocInd(),
					dto.getFutherMnpTicketNum()

			// end sql mapping
					);
		} catch (Exception e) {
			logger.error("Exception caught in insertFutherMrt()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}

	public MnpDTO getFutherMnp(String orderId) throws DAOException {
		logger.debug("getFutherMnp() is called");

		List<MnpDTO> mnpList = new ArrayList<MnpDTO>();

		String SQL = "select M.ORDER_ID, M.MSISDN, M.CUT_OVER_DATE,\n"
				+ "  M.CUT_OVER_TIME, M.MNP_TICKET_NUM,"
				+ "  M.CUST_NAME, nvl(M.CUST_NAME_CHI,' ') CUST_NAME_CHI, M.DOC_NO, M.PREPAID_SIM_DOC_IND\n, M.OPSS_IND,M.STARTER_PACK"
				+ "  from BOMWEB_MRT M\n" + " where M.ORDER_ID = ?\n"
				+ "   and M.SEQ_ID = 2"
				+ "   and nvl(china_ind, 'N') = 'N' ";

		ParameterizedRowMapper<MnpDTO> mapper = new ParameterizedRowMapper<MnpDTO>() {
			public MnpDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				MnpDTO mnp = new MnpDTO();

				mnp.setOrderId(rs.getString("ORDER_ID"));
				mnp.setMsisdn(rs.getString("msisdn"));
				mnp.setCutoverDate(rs.getTimestamp("CUT_OVER_DATE"));
				mnp.setCutoverDateStr(Utility.date2String(
						rs.getTimestamp("CUT_OVER_DATE"), "dd/MM/yyyy"));
				mnp.setCutoverTime(rs.getString("CUT_OVER_TIME"));
				mnp.setCustName(rs.getString("CUST_NAME"));
				mnp.setCustNameChi(rs.getString("CUST_NAME_CHI"));
				mnp.setCustIdDocNum(rs.getString("DOC_NO"));
				mnp.setPrePaidSimDocInd(rs.getString("PREPAID_SIM_DOC_IND"));
				mnp.setMnpTicketNum(rs.getString("MNP_TICKET_NUM"));
				mnp.setOpssInd(rs.getString("OPSS_IND"));
				mnp.setStarterPack(rs.getString("STARTER_PACK"));
				return mnp;
			}
		};

		try {
			logger.debug("getFutherMnp() @ OrderDAO: " + SQL);
			logger.debug("getFutherMnp() @ OrderDAO: ");
			mnpList = simpleJdbcTemplate.query(SQL, mapper, orderId);
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException");
			mnpList = null;
		} catch (Exception e) {
			logger.info("Exception caught in getFutherMnp():", e);
			throw new DAOException(e.getMessage(), e);
		}
		if (mnpList != null && mnpList.size() > 0) {
			return mnpList.get(0);// only return the first one
		}
		return null;

	}

	/**
	 * 
	 * @param orderId
	 * @param templateId
	 * @param pdfFileName
	 * @param createBy
	 * @throws DAOException
	 * 
	 *             Indicate the pdf file (S&S form) is generated and wait for
	 *             email sending to customers
	 */
	public void insertEmailReq(String orderId, String templateId,
			String pdfFileName, String createBy) throws DAOException {
		logger.debug("insertEmailReq is called");

		String SQL = "INSERT " + "INTO bomweb_ord_email_req " + "  ( "
				+ "    order_id, " + "    template_id, "
				+ "    file_path_name_1, " + "    request_date, "
				+ "    create_by, " + "    last_upd_by, " + "    create_date "
				+ "  ) " + "  VALUES " + "  ( " + "    ?, " + "    ?, "
				+ "    ?, " + "    SYSDATE, " + "    ?, " + "    ?, "
				+ "    SYSDATE " + "  )";

		try {
			simpleJdbcTemplate.update(SQL, orderId, templateId, pdfFileName,
					createBy, createBy);
		} catch (Exception e) {
			logger.error("Exception caught in insertEmailReq()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}

	public int updateEsigEmailAddr(String orderId, String esigEmailAddr,
			String lastUpdateBy) throws DAOException {
		String sql = "update bomweb_order"
				+ " set esig_email_addr = :esigEmailAddr"
				+ " , LAST_UPDATE_BY = :lastUpdateBy"
				+ " , LAST_UPDATE_DATE = sysdate"
				+ " where ORDER_ID = :orderId" + " and DIS_MODE = :disMode";
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("esigEmailAddr", esigEmailAddr);
		params.addValue("lastUpdateBy", lastUpdateBy);
		params.addValue("orderId", orderId);
		params.addValue("disMode", DisMode.E.toString());
		try {
			return this.simpleJdbcTemplate.update(sql, params);
		} catch (Exception e) {
			logger.error("Exception caught in updateEsigEmailAddr()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}

	public int updateSMSNo(String orderId, String SMSno, String lastUpdateBy)
			throws DAOException {
		String sql = "update bomweb_order" + " set SMS_NO = :SMSno"
				+ " , LAST_UPDATE_BY = :lastUpdateBy"
				+ " , LAST_UPDATE_DATE = sysdate"
				+ " where ORDER_ID = :orderId" + " and DIS_MODE = :disMode";
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("SMSno", SMSno);
		params.addValue("lastUpdateBy", lastUpdateBy);
		params.addValue("orderId", orderId);
		params.addValue("disMode", DisMode.S.toString());
		try {
			return this.simpleJdbcTemplate.update(sql, params);
		} catch (Exception e) {
			logger.error("Exception caught in updateSMSNo()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}

	public void insertBomWebMrt(MnpDTO dto) throws DAOException {
		logger.info("insertBomWebMrtMnp is called");
		// syn data from bomweb_mnp to bomweb_MRT

		String SQL = "insert into BOMWEB_MRT\n" + "  (ORDER_ID,\n"
				+ "   CUT_OVER_DATE,\n" + "   CUT_OVER_TIME,\n" + "   RNO,\n"
				+ "   DNO,\n" + "   ACT_DNO,\n" + "   CUST_NAME,\n" + "   DOC_NO,\n"
				+ "   MNP_TICKET_NUM,\n" + "   CREATE_DATE,\n"
				+ "   SEQ_ID, msisdn, msisdnlvl, srv_req_date , RESERVE_ID , NUM_TYPE )\n"
				+ "values\n"
				+ "  (?, ?, ?, ?, ?, ?, ?, ?, ?, sysdate, 1, ?, ?, ?, ?, ?)";

		try {
			simpleJdbcTemplate.update(SQL, dto.getOrderId(),
					dto.getCutoverDate(), dto.getCutoverTime(), dto.getRno(),
					dto.getDno(),dto.getActualDno(), dto.getCustName(), dto.getCustIdDocNum(),
					dto.getMnpTicketNum(), dto.getMsisdn(), dto.getMsisdnLvl(),
					dto.getServiceReqDate(), dto.getReserveId(),dto.getNumType()

			);

		} catch (Exception e) {
			logger.error("Exception caught in insertBomWebMrt()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}

	// Refactor to BaseOrderDAO in SpringboradService By Karen Ma 20130509
	/*
	 * public int updateDmsInd(String orderId, String dmsInd, String
	 * lastUpdateBy) throws DAOException { String sql = "update bomweb_order" +
	 * " set dms_ind = :dmsInd" + " , LAST_UPDATE_BY = :lastUpdateBy" +
	 * " , LAST_UPDATE_DATE = sysdate" + " where ORDER_ID = :orderId";
	 * MapSqlParameterSource params = new MapSqlParameterSource();
	 * params.addValue("dmsInd", dmsInd); params.addValue("lastUpdateBy",
	 * lastUpdateBy); params.addValue("orderId", orderId);
	 * 
	 * try { return this.simpleJdbcTemplate.update(sql, params); } catch
	 * (Exception e) { logger.error("Exception caught in updateDmsInd()", e);
	 * throw new DAOException(e.getMessage(), e); } }
	 */
	/**
	 * Retrieve OCID of an order using its order ID
	 * 
	 * @param orderId
	 * @return
	 * @throws DAOException
	 */
	public String getOcidByOrderID(String orderId) throws DAOException {

		String sql = "SELECT OCID FROM BOMWEB_ORDER WHERE lob = 'MOB' AND ORDER_ID = :orderId";

		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("orderId", orderId);

		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getString("OCID");
			}
		};

		try {
			List<String> tempList = this.simpleJdbcTemplate.query(sql, mapper,
					params);
			if (tempList != null && !tempList.isEmpty()) {
				return tempList.get(0);
			} else {
				return "";
			}
		} catch (Exception e) {
			logger.error("Exception caught in getOcidByOrderID()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}

	// add 2011-12-19 new order id format, eg.eg.RTP1M0000001
	// ***123456789012
	// eg.RTP1M0000001
	// eg.CTP1M0000001
	// eg.RTP1P0000001
	// rule
	// 1, first char R=retail shop, C=call center
	// 2-4, shop code
	// 5, M=MOB, P=LTS, ?=IMS
	// 6-12, sequence from bomweb_shop table
	public String getShopSeqStoredProcedure(String shopCd, String channelId)
			throws DAOException {

		/*
		 * begin -- Call the procedure
		 * pkg_sb_mob_order.get_mob_order_id(i_shop_code => :i_shop_code,
		 * i_channel_id => :i_channel_id, o_order_id => :o_order_id, o_errcode
		 * => :o_errcode, o_errtext => :o_errtext); end;
		 */
		logger.info("getShopSeqStoredProcedure [shopCd]= " + shopCd
				+ " [channelId]" + channelId);
		String orderId = "";

		try {
			SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
					.withSchemaName("OPS$CNM")
					.withCatalogName("PKG_SB_MOB_ORDER")
					.withProcedureName("GET_MOB_ORDER_ID");
			jdbcCall.setAccessCallParameterMetaData(false);
			jdbcCall.declareParameters(new SqlParameter("i_shop_code",
					Types.VARCHAR), new SqlParameter("i_channel_id",
					Types.VARCHAR), new SqlOutParameter("o_order_id",
					Types.VARCHAR), new SqlOutParameter("o_errcode",
					Types.INTEGER), new SqlOutParameter("o_errtext",
					Types.VARCHAR));
			MapSqlParameterSource inMap = new MapSqlParameterSource();
			inMap.addValue("i_shop_code", shopCd);
			inMap.addValue("i_channel_id", channelId);
			SqlParameterSource in = inMap;

			Map out = jdbcCall.execute(in);

			orderId = (String) out.get("o_order_id");

			logger.info("getShopSeqStoredProcedure [orderId]= " + orderId);

			int errcode = 0;
			if (((Integer) out.get("o_errcode")) != null) {
				errcode = ((Integer) out.get("o_errcode")).intValue();
			}

			String errorText = (String) out.get("o_errtext");
			logger.info("getShopSeqStoredProcedure output [errcode]= "
					+ errcode + " [errorText]" + errorText);
			return orderId;

		} catch (Exception e) {
			logger.error("Exception caught in getShopSeqStoredProcedure()", e);
			throw new DAOException(e.getMessage(), e);
		}

	}

	public String[] getBOMandSBOrderInfo(String orderId) throws DAOException {

		String sql = "SELECT ORDER_ID, " + "  OCID, " + "  COLLECT_METHOD, "
				+ "  BOM_ORDER_STATUS, " + "  CASE "
				+ "    WHEN (TRUNC(sysdate) <= TRUNC(SRV_REQ_DATE) + 30) "
				+ "    THEN 'Y' " + "    ELSE 'N' "
				+ "  END BEFORE_A_PLUS_14_DAYS " + "FROM "
				+ "  (SELECT O.ORDER_ID, " + "    O.OCID, "
				+ "    O.COLLECT_METHOD, "
				+ "    NVL(OO.BOM_ORDER_STATUS, '01') BOM_ORDER_STATUS, "
				+ "    NVL(OO.SRV_REQ_DATE, O.SRV_REQ_DATE) SRV_REQ_DATE "
				+ "  FROM BOMWEB_ORDER O, " + "    BOMWEB_SB_BOM_ORDER OO "
				+ "  WHERE O.ORDER_ID    = OO.ORDER_ID(+) "
				+ "  AND O.ORDER_ID      = :ORDERID "
				+ "  AND O.ORDER_ID NOT IN " + "    (SELECT ORDER_ID "
				+ "    FROM BOMWEB_ALL_ORD_DOC_ASSGN "
				+ "    WHERE ORDER_ID = :ORDERID "
				+ "    AND DOC_TYPE   = 'M012' " + "    ) " + "  )";

		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("ORDERID", orderId);

		ParameterizedRowMapper<String[]> mapper = new ParameterizedRowMapper<String[]>() {
			public String[] mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				String[] tempArray = new String[5];
				tempArray[0] = rs.getString("ORDER_ID");
				tempArray[1] = rs.getString("OCID");
				tempArray[2] = rs.getString("COLLECT_METHOD");
				tempArray[3] = rs.getString("BOM_ORDER_STATUS");
				tempArray[4] = rs.getString("BEFORE_A_PLUS_14_DAYS");
				return tempArray;
			}
		};

		try {
			List<String[]> tempList = this.simpleJdbcTemplate.query(sql,
					mapper, params);
			if (tempList != null && !tempList.isEmpty()) {
				return tempList.get(0);
			} else {
				return null;
			}
		} catch (Exception e) {
			logger.error("Exception caught in getBOMandSBOrderInfo()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}

	public List<String> getFrozenWindow(String inDate) throws DAOException {

		logger.debug("getFrozenWindow is called");
		logger.debug("getFrozenWindow inDate = " + inDate);

		String sql = "select frozen_window from w_mnp_frozen_window "
				+ "	where frozen_date = to_date(:inDate, 'DD/MM/YYYY') ";

		MapSqlParameterSource mapSql = new MapSqlParameterSource();
		mapSql.addValue("inDate", inDate);

		ParameterizedRowMapper<String> params = new ParameterizedRowMapper<String>() {

			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getString("frozen_window");
			}
		};

		try {
			List<String> resultList = this.simpleJdbcTemplate.query(sql,
					params, mapSql);
			return resultList;
		} catch (Exception e) {
			logger.error("Exception caught in getFrozenWindow()", e);
			throw new DAOException(e.getMessage(), e);
		}

	}

	public void orderTierAttbProcess(String orderId) throws DAOException {
		if (logger.isDebugEnabled()) {
			logger.debug("orderId: " + orderId);
		}
		if (StringUtils.isBlank(orderId)) {
			return;
		}
		try {
			SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
					.withSchemaName("OPS$CNM")
					.withCatalogName("pkg_sb_mob_order")
					.withProcedureName("order_tier_attb_process");
			// declare procedure parameters
			simpleJdbcCall.declareParameters(new SqlParameter("i_order_id",
					OracleTypes.VARCHAR), new SqlOutParameter("errCode",
					Types.INTEGER), new SqlOutParameter("errText",
					Types.VARCHAR));

			// procedure parameters value
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("i_order_id", orderId);

			// execute
			Map<String, Object> out = simpleJdbcCall.execute(params);

			// retrieve
			Integer errCode = null;
			String errText = null;
			if (out.get("errCode") instanceof Integer) {
				errCode = (Integer) out.get("errCode");
			}
			if (out.get("errText") instanceof String) {
				errText = (String) out.get("errText");
			}

			if (logger.isDebugEnabled()) {
				logger.debug("errCode: " + errCode);
				logger.debug("errText: " + errText);
			}

		} catch (Exception e) {
			logger.error("Exception caught in orderTierAttbProcess()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}

	public boolean hasMobileSafetyPhoneOffer(String orderId)
			throws DAOException {
		logger.debug("hasMobileSafetyPhoneOffer is called");
		List<String> bundleVASList = new ArrayList();
		String SQL = "SELECT id FROM bomweb_subscribed_item "
				+ "WHERE order_id=? " + "AND id in "
				+ "(SELECT DISTINCT item_id "
				+ "FROM w_item_selection_grp_assgn "
				+ "WHERE grp_id='100000103')";

		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {

			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				String brand = new String();
				brand = rs.getString("ID");
				return brand;
			}
		};
		try {
			bundleVASList = simpleJdbcTemplate.query(SQL, mapper,
					new Object[] { orderId });
			if ((bundleVASList != null) && (bundleVASList.size() > 0))
				return true;
			else {
				return false;
			}
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException");
			return false;
		} catch (Exception e) {
			logger.error("Exception caught in getSelectedItemList()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	public boolean isStaffOffer(String basketId)
			throws DAOException {
		logger.debug("isStaffOffer is called");
	String staffOffer ="";
		
		String SQL = "SELECT DECODE(COUNT(*),0,'N','Y') " +
				"FROM w_basket_attribute_mv " +
				"WHERE basket_id= :basketId " +
				"AND basket_id IN " +
				"  (SELECT distinct CODE_ID FROM BOMWEB_CODE_LKUP WHERE CODE_TYPE='STAFF_OFFER'" +
				"  )";

		try {
			logger.debug("isStaffOffer @ OrderDAO: " + SQL);
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("basketId",basketId);
			staffOffer = (String) simpleJdbcTemplate.queryForObject(SQL,
					String.class, params);
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException");
			staffOffer = "";
		} catch (Exception e) {
			logger.error("Exception caught in hasTNGServiceDummyVas()", e);
			throw new DAOException(e.getMessage(), e);
		}
		if ("Y".equalsIgnoreCase(staffOffer)) {
			return true;
		} else {
			return false;
		}
	}
	public String hasTradeInHS(String orderId) throws DAOException {
		logger.debug("hasTradeInHS is called");
		List<String> resultList = new ArrayList();
		String SQL = "SELECT id FROM bomweb_subscribed_item "
				+ "WHERE order_id=? " + "AND id in "
				+ "(SELECT DISTINCT item_id "
				+ "FROM w_item_selection_grp_assgn "
				+ "WHERE grp_id='100000104')";

		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {

			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				String id = new String();
				id = rs.getString("ID");
				return id;
			}
		};
		try {
			resultList = simpleJdbcTemplate.query(SQL, mapper,
					new Object[] { orderId });
			if ((resultList != null) && (resultList.size() > 0))
				return resultList.get(0);
			else {
				return null;
			}
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException");
			return null;
		} catch (Exception e) {
			logger.error("Exception caught in hasTradeInHS()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}

	public String getTradeInRBSchedule(String tradeInItemID, String locale)
			throws DAOException {
		logger.info("getTradeInRBSchedule is called");
		List<String> resultList = new ArrayList();
		String desc_field = (BomWebPortalCcsConstant.REPORT_LOCALE_ZH_HK
				.equals(locale) ? "REBATE_SCHEDULE_CHI" : "REBATE_SCHEDULE");
		String SQL = "SELECT " + desc_field
				+ " description FROM w_item_dtl_rp_rb_vas " + "WHERE id = ? ";

		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {

			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				String id = new String();
				id = rs.getString("description");
				return id;
			}
		};
		try {
			resultList = simpleJdbcTemplate.query(SQL, mapper,
					new Object[] { tradeInItemID });
			if ((resultList != null) && (resultList.size() > 0))
				return resultList.get(0);
			else {
				return null;
			}
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException");
			return null;
		} catch (Exception e) {
			logger.error("Exception caught in getTradeInRBSchedule()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}

	public String getTradeInIMEIAttributeID() throws DAOException {
		String sql = "select code_id from bomweb_code_lkup  "
				+ " where code_type like 'TRADE_IN_IMEI_ATTB' ";

		ParameterizedRowMapper<String> params = new ParameterizedRowMapper<String>() {

			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getString("code_id");
			}
		};

		try {
			List<String> resultList = this.simpleJdbcTemplate
					.query(sql, params);
			if ((resultList != null) && (resultList.size() > 0))
				return resultList.get(0);
			else {
				return null;
			}
		} catch (Exception e) {
			logger.error("Exception caught in getTradeInIMEIAttributeID()", e);
			throw new DAOException(e.getMessage(), e);
		}

	}

	public boolean hasNFCSim(String orderId) throws DAOException {
		logger.debug("hasNFCSim is called");
		List<String> bundleVASList = new ArrayList<String>();
		String SQL = "select * from bomweb_stock_catalog"
				+ " where item_type = '02'"
				+ " and item_code in"
				+ " (select IPPA.POS_ITEM_CD"
				+ " from BOMWEB_SUBSCRIBED_ITEM SI, W_ITEM_PRODUCT_POS_ASSGN IPPA"
				+ " where SI.ID = IPPA.ITEM_ID"
				+ " and SI.TYPE = 'SIM'"
				+ " and SI.ORDER_ID = ?)"
				+ " and hs_extra_function in"
				+ " (select distinct code_id from bomweb_code_lkup where code_type = 'HS_EXTRA_FUNCTION' and code_id in ('1','3'))";

		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				String itemId = new String();
				itemId = rs.getString("ITEM_CODE");
				return itemId;
			}
		};
		try {
			bundleVASList = simpleJdbcTemplate.query(SQL, mapper,
					new Object[] { orderId });
			if ((bundleVASList != null) && (bundleVASList.size() > 0)) {
				return true;
			} else {
				return false;
			}
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException");
			return false;
		} catch (Exception e) {
			logger.error("Exception caught in hasNFCSim()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}

	public boolean hasOctopusSim(String orderId) throws DAOException {
		logger.debug("hasOctopusSim is called");
		List<String> bundleVASList = new ArrayList<String>();
		String SQL = "select * from bomweb_stock_catalog"
				+ " where item_type = '02'"
				+ " and item_code in"
				+ " (select IPPA.POS_ITEM_CD"
				+ " from BOMWEB_SUBSCRIBED_ITEM SI, W_ITEM_PRODUCT_POS_ASSGN IPPA"
				+ " where SI.ID = IPPA.ITEM_ID"
				+ " and SI.TYPE = 'SIM'"
				+ " and SI.ORDER_ID = ?)"
				+ " and hs_extra_function in"
				+ " (select distinct code_id from bomweb_code_lkup where code_type = 'HS_EXTRA_FUNCTION' and code_id in ('2','3'))";

		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				String itemId = new String();
				itemId = rs.getString("ITEM_CODE");
				return itemId;
			}
		};
		try {
			bundleVASList = simpleJdbcTemplate.query(SQL, mapper,
					new Object[] { orderId });
			if ((bundleVASList != null) && (bundleVASList.size() > 0))
				return true;
			else {
				return false;
			}
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException");
			return false;
		} catch (Exception e) {
			logger.error("Exception caught in hasOctopusSim()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}

	public boolean isNFCSim(String itemCd) throws DAOException {
		logger.debug("hasNFCSim is called");
		List<String> bundleVASList = new ArrayList<String>();
		String SQL = "select * from bomweb_stock_catalog"
				+ " where item_type = '02'"
				+ " and item_code = ?"
				+ " and hs_extra_function in"
				+ " (select distinct code_id from bomweb_code_lkup where code_type = 'HS_EXTRA_FUNCTION' and code_id in ('1','3'))";

		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				String itemId = new String();
				itemId = rs.getString("ITEM_CODE");
				return itemId;
			}
		};
		try {
			bundleVASList = simpleJdbcTemplate.query(SQL, mapper,
					new Object[] { itemCd });
			if ((bundleVASList != null) && (bundleVASList.size() > 0)) {
				return true;
			} else {
				return false;
			}
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException");
			return false;
		} catch (Exception e) {
			logger.error("Exception caught in hasNFCSim()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}

	public boolean isOctopusSim(String itemCd) throws DAOException {
		logger.debug("hasNFCSim is called");
		List<String> bundleVASList = new ArrayList<String>();
		String SQL = "select * from bomweb_stock_catalog"
				+ " where item_type = '02'"
				+ " and item_code = ?"
				+ " and hs_extra_function in"
				+ " (select distinct code_id from bomweb_code_lkup where code_type = 'HS_EXTRA_FUNCTION' and code_id in ('2','3'))";

		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				String itemId = new String();
				itemId = rs.getString("ITEM_CODE");
				return itemId;
			}
		};
		try {
			bundleVASList = simpleJdbcTemplate.query(SQL, mapper,
					new Object[] { itemCd });
			if ((bundleVASList != null) && (bundleVASList.size() > 0)) {
				return true;
			} else {
				return false;
			}
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException");
			return false;
		} catch (Exception e) {
			logger.error("Exception caught in hasNFCSim()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}

	public boolean isOctopusWaived(String basketId, String appDate)
			throws DAOException {
		List<String> resultList = new ArrayList<String>();
		String SQL = " select * "
				+ " from W_HS_ADMIN_WAIVE_LKUP WL "
				+ " left join W_ITEM_PRODUCT_POS_ASSGN IPPA ON (WL.ITEM_CD = IPPA.POS_ITEM_CD) "
				+ " left join W_ITEM I ON (IPPA.ITEM_ID = I.ID) "
				+ " left join W_BASKET_ITEM_ASSGN BIA ON (BIA.ITEM_ID = I.ID) "
				+ " left join W_BASKET_ATTRIBUTE_MV BAMV ON (BIA.BASKET_ID = BAMV.BASKET_ID) "
				+ " where IPPA.ITEM_ID = BIA.ITEM_ID "
				+ " and TO_DATE(?, 'DD/MM/YYYY') between BIA.EFF_START_DATE and NVL(BIA.EFF_END_DATE, sysdate) "
				+ " and TO_DATE(?, 'DD/MM/YYYY') between WL.EFF_START_DATE and NVL(WL.EFF_END_DATE, sysdate) "
				+ " and I.TYPE = 'HS' " + " and WAIVE_TYPE = 'OCTOPUS_WAIVE' "
				+ " and NVL(BAMV.NATURE, 'ACQ') = 'ACQ' "
				+ " and BAMV.BASKET_ID = ?";

		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {

			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				String waiveType = new String();
				waiveType = rs.getString("WAIVE_TYPE");
				return waiveType;
			}
		};
		try {
			resultList = simpleJdbcTemplate.query(SQL, mapper, new Object[] {
					appDate, appDate, basketId });
			if ((resultList != null) && (resultList.size() > 0))
				return true;
			else {
				return false;
			}
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException");
			return false;
		} catch (Exception e) {
			logger.error("Exception caught in isOctopusWaived()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}

	public String getHSWaiveType(String basketId, String appDate)
			throws DAOException {
		String waiveType = "";
		String SQL = " select WL.WAIVE_TYPE "
				+ " from W_HS_ADMIN_WAIVE_LKUP WL "
				+ " left join W_ITEM_PRODUCT_POS_ASSGN IPPA ON (WL.ITEM_CD = IPPA.POS_ITEM_CD) "
				+ " left join W_ITEM I ON (IPPA.ITEM_ID = I.ID) "
				+ " left join W_BASKET_ITEM_ASSGN BIA ON (BIA.ITEM_ID = I.ID) "
				+ " left join W_BASKET_ATTRIBUTE_MV BAMV ON (BIA.BASKET_ID = BAMV.BASKET_ID) "
				+ " where IPPA.ITEM_ID = BIA.ITEM_ID "
				+ " and TO_DATE(?, 'DD/MM/YYYY') between BIA.EFF_START_DATE and NVL(BIA.EFF_END_DATE, sysdate) "
				+ " and TO_DATE(?, 'DD/MM/YYYY') between WL.EFF_START_DATE and NVL(WL.EFF_END_DATE, sysdate) "
				+ " and I.TYPE = 'HS' " + " and BAMV.BASKET_ID = ?"
				+ " and NVL(BAMV.NATURE, 'ACQ') = 'ACQ' ";
		try {
			logger.debug("getHSWaiveType @ VasDetailDAO: " + SQL);
			waiveType = (String) simpleJdbcTemplate.queryForObject(SQL,
					String.class, appDate, appDate, basketId);
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException");
			waiveType = "";
		} catch (Exception e) {
			logger.error("Exception caught in getHSWaiveType()", e);
			throw new DAOException(e.getMessage(), e);
		}
		return waiveType;
	}

	public String getSimType(String itemCd) throws DAOException {
		logger.debug("getSimType is called");
		String hsExtraFunction = "";
		String SQL = "select hs_extra_function from bomweb_stock_catalog "
				+ " where item_type = '02'" + " and item_code = ?";

		try {
			logger.debug("getSimType @ OrderDAO: " + SQL);
			hsExtraFunction = (String) simpleJdbcTemplate.queryForObject(SQL,
					String.class, itemCd);
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException");
			hsExtraFunction = "";
		} catch (Exception e) {
			logger.error("Exception caught in getSimType()", e);
			throw new DAOException(e.getMessage(), e);
		}
		return hsExtraFunction;
	}

	public boolean getOctFlag() throws DAOException {// Athena 20130923
		logger.info("getOctFlag is called");
		String octflag = "";
		String SQL = "select code_id from bomweb_code_lkup where code_type='OCTOPUS_ACTIVE'";

		try {
			logger.debug("getOctFlag @ OrderDAO: " + SQL);
			octflag = (String) simpleJdbcTemplate.queryForObject(SQL,
					String.class);
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException");
			octflag = "";
		} catch (Exception e) {
			logger.error("Exception caught in getOctFlag()", e);
			throw new DAOException(e.getMessage(), e);
		}
		if ("Y".equalsIgnoreCase(octflag.trim())) {
			return true;
		} else {
			return false;
		}
	}

	public String getOneTimeChargeAmount(String basketId, String appDate,
			String channelId) throws DAOException {
		logger.debug("getOneTimeChargeAmount is called");
		String adminFee = "";
	
		String SQL = "select NVL(IP.ONETIME_AMT,0) "
				+ "from W_CHANNEL_BASKET_ASSGN CBA, "
				+ "W_VAS_ITEM_GRP_ASSGN VIGA, "
				+ "W_ITEM_PRICING IP, "
				+ "BOMWEB_CODE_LKUP CL "
				+ "where CBA.VAS_ITEM_GRP_ID = VIGA.GRP_ID "
				+ "and VIGA.ITEM_ID = IP.ID "
				+ "and VIGA.ITEM_ID = CL.CODE_ID "
				+ "and CBA.BASKET_ID = ? "
				+ "and CL.CODE_TYPE = 'OCTOPUS_ADMIN_WAIVE' "
				+ "and to_date(?, 'DD/MM/YYYY') between CBA.EFF_START_DATE and nvl(CBA.EFF_END_DATE, sysdate) "
				+ "and to_date(?, 'DD/MM/YYYY') between VIGA.EFF_START_DATE and nvl(VIGA.EFF_END_DATE, sysdate) "
				+ "and to_date(?, 'DD/MM/YYYY') between IP.EFF_START_DATE and nvl(IP.EFF_END_DATE, sysdate) "
				+ "and IP.ONETIME_TYPE = 'A' " + "and CBA.CHANNEL_ID = ?";
		try {
			logger.debug("getOneTimeChargeAmount @ OrderDAO: " + SQL);
			adminFee = simpleJdbcTemplate.queryForObject(SQL, String.class,
					basketId, appDate, appDate, appDate, channelId);
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException");
			adminFee = "";
		} catch (Exception e) {
			logger.error("Exception caught in getOneTimeChargeAmount()", e);
			throw new DAOException(e.getMessage(), e);
		}
		return adminFee;
	}

	public void updateDsMsisdnStatus(String msisdn, int status, String orderId, SalesInfoDTO salesInfoDTO)
			throws DAOException {
		logger.debug("updateDsMsisdnStatus is called");

		StringBuffer sql = new StringBuffer("update BOMWEB_MRT_INVENTORY "
				+ "set MSISDN_STATUS = :status "
				+ "where (MSISDN in" 
				+ "( SELECT MSISDN "
				+ "  FROM BOMWEB_ORDER BO"
				+ "  WHERE BO.ORDER_ID = :orderId "
				+ ")"
				+ "or MSISDN in "
				+ "( SELECT BOMM.MSISDN "
				+ "  FROM BOMWEB_ORD_MOB_MEMBER BOMM"
				+ "  WHERE BOMM.PARENT_ORDER_ID = :orderId )) "
				+ "AND ( staff_id = :staffId ");
		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("orderId", orderId);
			params.addValue("status", status);
			params.addValue("msisdn", msisdn);
			params.addValue("staffId", salesInfoDTO.getStaffId());
			
			sql.append("OR ( channel_cd = :channelCd ");
			params.addValue("channelCd", salesInfoDTO.getChannelCd());
			if ("MDV".equalsIgnoreCase(salesInfoDTO.getChannelCd())) {
				sql.append("AND centre_cd = :areaCd ");
				params.addValue("areaCd", salesInfoDTO.getCentreCd());
				sql.append("AND team_cd = :shopCd ");
				params.addValue("shopCd", salesInfoDTO.getTeamCd());
			} else if ("SIS".equalsIgnoreCase(salesInfoDTO.getChannelCd())) {
				sql.append("AND centre_cd = :areaCd ");
				params.addValue("areaCd", salesInfoDTO.getCentreCd());
			}
			sql.append("AND staff_id is NULL)) ");
			
			simpleJdbcTemplate.update(sql.toString(), params);

		} catch (Exception e) {
			logger.error("Exception caught in updateDsMsisdnStatus()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}

	public List<String> getCodeIdList(String codeType) throws DAOException {

		logger.debug("getCodeIdList is called");

		String SQL = "select code_id from bomweb_code_lkup where code_type = :codeType";

		MapSqlParameterSource mapSql = new MapSqlParameterSource();
		mapSql.addValue("codeType", codeType);

		ParameterizedRowMapper<String> params = new ParameterizedRowMapper<String>() {

			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getString("code_id");
			}
		};

		try {
			List<String> resultList = this.simpleJdbcTemplate.query(SQL,
					params, mapSql);
			return resultList;
		} catch (Exception e) {
			logger.error("Exception caught in getCodeIdList()", e);
			throw new DAOException(e.getMessage(), e);
		}

	}

	public void updateOrderPayCheck(String orderId, String staffId,
			String paymentCheck) throws DAOException {
		logger.debug("updateBomWebOrderStatus is called");

		String SQL = "update bomweb_order set " + "	paymt_chk_ind = ?, "
				+ "	paymt_rec_date = trunc(sysdate), "
				+ "	last_update_by = ?, " + "	last_update_date = sysdate "
				+ "where order_id = ? ";

		try {
			simpleJdbcTemplate.update(SQL, paymentCheck, staffId, orderId);

		} catch (Exception e) {
			logger.error("Exception caught in updateOrderPayCheck()", e);
			throw new DAOException(e.getMessage(), e);
		}

	}
	
	public void updateSupportDocCheck(String orderId, String staffId,
			String supportDocCheck) throws DAOException {
		logger.debug("updateSupportDocCheck is called");

		String SQL = "update bomweb_order set " + "	supdoc_chk_ind = ?, "
				+ "	last_update_by = ?, " + "	last_update_date = sysdate "
				+ "where order_id = ? ";

		try {
			simpleJdbcTemplate.update(SQL, supportDocCheck, staffId, orderId);

		} catch (Exception e) {
			logger.error("Exception caught in updateSupportDocCheck()", e);
			throw new DAOException(e.getMessage(), e);
		}

	}

	public BomSalesUserDTO getSalesUserInfo(String staffId, String appDate)
			throws DAOException {
		if (logger.isDebugEnabled()) {
			logger.debug("getSalesUserInfo() is called");
		}
		List<BomSalesUserDTO> itemList = Collections.emptyList();

		String sql = "SELECT channel_id, "
				+ "  channel_cd, "
				+ "  centre_cd, "
				+ "  team_cd, "
				+ "  staff_id "
				+ "FROM bomweb_sales_assignment "
				+ "WHERE staff_id = :staffId "
				+ "AND TRUNC(to_date(:appDate, 'DD/MM/YYYY')) "
				+ "between TRUNC(START_DATE) and TRUNC(NVL(END_DATE, sysdate)) ";

		ParameterizedRowMapper<BomSalesUserDTO> mapper = new ParameterizedRowMapper<BomSalesUserDTO>() {

			public BomSalesUserDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				BomSalesUserDTO temp = new BomSalesUserDTO();
				temp.setChannelId(rs.getInt("CHANNEL_ID"));
				temp.setChannelCd(rs.getString("CHANNEL_CD"));
				temp.setAreaCd(rs.getString("CENTRE_CD"));
				temp.setShopCd(rs.getString("TEAM_CD"));
				temp.setSalesCd(rs.getString("STAFF_ID"));
				return temp;
			}
		};

		try {
			if (logger.isDebugEnabled()) {
				logger.debug("getSalesInfoDTOByID() @ MobCcsSalesInfoDAO: "
						+ sql);
			}
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("staffId", staffId);
			if (StringUtils.isBlank(appDate)) {
				appDate = Utility.date2String(new Date(),
						BomWebPortalConstant.DATE_FORMAT);
			}
			params.addValue("appDate", appDate);
			itemList = simpleJdbcTemplate.query(sql, mapper, params);
		} catch (EmptyResultDataAccessException erdae) {
			if (logger.isInfoEnabled()) {
				logger.info("EmptyResultDataAccessException in getSalesUserInfo()");
			}
			itemList = null;
		} catch (Exception e) {
			if (logger.isInfoEnabled()) {
				logger.info("Exception caught in getSalesUserInfo():", e);
			}
			throw new DAOException(e.getMessage(), e);
		}

		if (itemList.size() > 0) {
			return itemList.get(0);
		} else {
			return null;
		}
	}

	public String getDsReserveId(String orderId) throws DAOException {
		logger.debug("getDsReserveId is called");
		List<String> reserveIdList = new ArrayList<String>();

		String SQL = "SELECT reserve_id FROM bomweb_mrt WHERE order_id = :orderId and seq_id = 1 and reserve_id is not null ";

		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("orderId", orderId);

		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getString("reserve_id");
			}
		};
		try {
			logger.debug("getDsReserveId @ OrderDAO: " + SQL);
			reserveIdList = simpleJdbcTemplate.query(SQL, mapper, params);
			if (reserveIdList != null && reserveIdList.size() > 0) {
				return reserveIdList.get(0);
			} else {
				return null;
			}
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException");

		} catch (Exception e) {
			logger.error("Exception caught in getDsReserveId()", e);
			throw new DAOException(e.getMessage(), e);
		}
		return null;
	}

	public void updateBomWebMemberMNP(MultiSimInfoMemberDTO msim, String salesId)
			throws DAOException {
		logger.debug("updateBomWebMemberMNP @ orderDAO is called");

		String SQL = "UPDATE bomweb_ord_mob_member SET " + "mnp_ind=:mnpInd, "
				+ "mnp_no=:mnpNo, " + "mnp_cut_over_date=:mnpCutOverDate, "
				+ "mnp_cut_over_time=:mnpCutOverTime, " + "mnp_dno=:mnpDno, "
				+ "mnp_cust_name=:mnpCustName, " + "mnp_doc_no=:mnpDocNo, "
				+ "mnp_ticket_num=:mnpTicketNum, "
				+ "prepaid_sim_doc_ind=:prepaidSimDocInd, "
				+ "member_order_type=:memberOrderType, "
				+ "last_upd_date=sysdate, last_upd_by=:salesId "
				+ "WHERE parent_order_id=:orderId AND member_num=:memberNum";
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("mnpInd", msim.getMnpInd());
		params.addValue("mnpNo", msim.getMnpNumber());
		params.addValue(
				"mnpCutOverDate",
				msim.getMnpCutOverDate() == null ? null : Utility
						.string2Date(msim.getMnpCutOverDate()));
		params.addValue("mnpCutOverTime", msim.getMnpCutOverTime());
		params.addValue("mnpDno", msim.getMnpDno());
		params.addValue("mnpCustName", msim.getMnpCustName());
		params.addValue("mnpDocNo", msim.getMnpDocNo());
		params.addValue("mnpTicketNum", msim.getMnpTicketNum());
		params.addValue("prepaidSimDocInd", msim.getPrePaidSimDocInd());
		params.addValue("memberOrderType", msim.getMemberOrderType());
		params.addValue("orderId", msim.getParentOrderId());
		params.addValue("memberNum", msim.getMemberNum());
		params.addValue("salesId", salesId);
		try {
			simpleJdbcTemplate.update(SQL, params);
		} catch (DataAccessException e) {
			logger.error("Exception caught in updateBomWebMemberMNP()", e);
		}
	}
	
	public void updateBomWebMUP(String orderId, String userId) throws DAOException {
		logger.debug("updateBomWebMUP @ orderDAO is called");
		String SQL = "UPDATE BOMWEB_ORD_MOB_MUP OMP " + "SET OMP.IO_IND        ='D', "
				+ "  OMP.LAST_UPD_BY     =:userId, " + "  OMP.LAST_UPD_DATE   =sysdate "
				+ "WHERE (OMP.ORDER_ID   =:orderId " + "OR OMP.PARENT_ORDER_ID=:orderId)";
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("userId", userId);
		params.addValue("orderId", orderId);
		try {
			simpleJdbcTemplate.update(SQL, params);
		} catch (DataAccessException e) {
			logger.error("Exception caught in updateBomWebMUP()", e);
		}
	}

	public void insertBomWebMUP(BomWebMupDTO bwm) throws DAOException {
		logger.debug("insertBomWebMUP @ orderDAO is called");
		String SQL = "INSERT INTO bomweb_ord_mob_mup ( " + "	order_id , " + "    mup_grp_id , " + "    mup_ind , "
				+ "    new_pri_ind , " + "    io_ind , " + "    p_oc_id , " + "    p_service_id , "
				+ "    parent_order_id , " + "    create_by , " + "    create_date , " + "    last_upd_by , "
				+ "    last_upd_date " + ") VALUES ( " + "    ? , " + "    ? , " + "    ? , " + "    ? , " + "    ? , "
				+ "    ? , " + "    ? , " + "    ? , " + "    ? , " + "    sysdate , " + "    ? , " + "    sysdate "
				+ ")";
		try {
			simpleJdbcTemplate.update(SQL, bwm.getOrderId(), bwm.getMupGrpId(), bwm.getPrimary(), bwm.getNewPriInd(),
					bwm.getIoInd(), bwm.getpOcId(), bwm.getpServiceId(), bwm.getParentOrderId(), bwm.getCreateBy(),
					bwm.getLastUpdateBy());
		} catch (Exception e) {
			logger.error("Exception caught in insertBomWebMUP()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}


		public void insertBomWebMember(MultiSimInfoMemberDTO msim)
			throws DAOException {
		logger.debug("insertBomWebMember @ orderDAO is called");
		
		String SQL = "INSERT INTO BOMWEB_ORD_MOB_MEMBER ( " + "    PARENT_ORDER_ID, MEMBER_NUM, MEMBER_ORDER_ID, "
				+ "    MSISDN , MSISDNLVL, MNP_IND, MNP_NO, " + "	  MNP_CUT_OVER_DATE, MNP_CUT_OVER_TIME, "
				+ "    MNP_RNO, MNP_DNO, ACT_DNO, MNP_CUST_NAME, MNP_DOC_NO, " + "   MNP_TICKET_NUM, ICCID, IMSI, "
				+ "    PUK1, PUK2, ITEM_CODE, " + "   OCID, MEMBER_STATUS, ERR_MSG, ERR_CD, "
				+ "   CREATE_BY, CREATE_DATE, LAST_UPD_BY, LAST_UPD_DATE, PREPAID_SIM_DOC_IND, NUM_TYPE, SIM_TYPE, SAME_AS_CUST_IND, OPSS_IND,STARTER_PACK, MEMBER_ORDER_TYPE, "
				+ "		CUR_CUST_NO, CUR_MOB_CUST_NO, CUR_ACCT_NO, "
				+ "		CUR_ID_DOC_TYPE, CUR_ID_DOC_NUM, CUR_TITLE, "
				+ "		CUR_FIRST_NAME, CUR_LAST_NAME, CUR_SIM_ICCID, "
				+ "		CUR_SIM_ITEM_CD, CUR_SIM_ITEM_DESC, AUTO_IND, " + "		COS_IND, TOO1_IND, CMN_IND, "
				+ "		BRM_IND, CUR_PENALTY_WAIVE_IND, TOO1_ADMIN_CHARGE, " + "		TOO1_WAIVE_REASON_CD, "
				+ "		CUR_BRAND, CUR_SIM_TYPE, BRM_CHG_SIM_IND) " 
				+ "  	VALUES ( " + "    ?, ?, ?, ?, " + "    ?, ?, ?, ?, ?, ?, "
				+ "    ?, ?, ?, ?, ?, ?, ?, " + "    ?, ?, ?, ?, ?, "
				+ "    ?, ?, 'SBMOB', sysdate, 'SBMOB', sysdate, ?, ?, ?, ?, ?, ?, ?, " + "		?, ?, ?,"
				+ "		?, ?, ?," + "		?, ?, ?," + "		?, ?, ?," + "		?, ?, ?," + "		?, ?, ?,"
				+ "		?, ?, ?, ?)";

		try {
			Date mnpCutOverDate = msim.getMnpCutOverDate() == null ? null : Utility.string2Date(msim.getMnpCutOverDate());
			simpleJdbcTemplate.update(SQL, 
					msim.getParentOrderId(), msim.getMemberNum(), msim.getMemberOrderId(),
					msim.getMsisdn(), msim.getMsisdnlvl(), msim.getMnpInd(), msim.getMnpNumber(),
					mnpCutOverDate, msim.getMnpCutOverTime(),
					msim.getMnpRno(), msim.getMnpDno(), msim.getActualDno(), msim.getMnpCustName(), msim.getMnpDocNo(),
					msim.getMnpTicketNum(), msim.getSim().getIccid(), msim.getSim().getImsi(),
					msim.getSim().getPuk1(), msim.getSim().getPuk2(), msim.getSim().getItemCode(),
					msim.getOcid(), msim.getMemberStatus(), msim.getErrMsg(), msim.getErrCd(), msim.getPrePaidSimDocInd(), msim.getSim().getSimType(),
					msim.getSim().getSimType(),	msim.getSameAsCustInd(), msim.getOpssInd(), msim.getStarterPack(), msim.getMemberOrderType(),
					msim.getCurCustNo(), msim.getCurMobCustNo(), msim.getCurAcctNo(), msim.getCurIdDocType(),
					msim.getCurIdDocNum(), msim.getCurTitle(), msim.getCurFirstName(), msim.getCurLastName(),
					msim.getCurSimIccid(), msim.getCurSimItemCd(), msim.getCurSimItemDesc(), msim.getAutoInd(),
					msim.getCosInd(), msim.getToo1Ind(), msim.getCmnInd(), msim.getBrmInd(),
					msim.getCurPenaltyWaiveInd(), msim.getToo1AdminCharge(), msim.getToo1WaiveReasonCd(), msim.getSubBrand(), msim.getSubSimType(), msim.getBrmChgSimInd());

		} catch (Exception e) {
			logger.error("Exception caught in insertBomWebMember()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}

	public void insertBomWebMemberVas(MultiSimInfoDTO msi) throws DAOException {
		logger.debug("insertBomWebMemberVas @ orderDAO is called");

		String SQL = "INSERT " + "INTO BOMWEB_ORD_MOB_MEMBER_VAS ( " + "		PARENT_ORDER_ID , MEMBER_NUM , "
				+ "		ITEM_ID , " + "		ITEM_TYPE, " + "		BASKET_ID, " + "    	CREATE_BY, "
				+ "		CREATE_DATE, " + "		LAST_UPD_BY, " + "		LAST_UPD_DATE, " + "		MIP_BRAND, "
				+ "		MIP_SIM_TYPE " + ") VALUES ( " + "		?, " + "		?, " + "		?, "
				+ "		(select type from w_item where id=?), " + "		?, " + "    'SBMOB', " + "		sysdate, "
				+ "		'SBMOB', " + "		sysdate, " + "		(select mip_brand from w_item where id=?), "
				+ "		(select mip_sim_type from w_item where id=?)" + ")";
		
		try {
			for (MultiSimInfoMemberDTO msim : msi.getMembers()) {
				logger.info("Parent Order ID = " + msim.getParentOrderId());

				for (String itemId : msim.getBundleVasItemList()) {
					simpleJdbcTemplate.update(SQL, msim.getParentOrderId(),
							msim.getMemberNum(), itemId, itemId, msi
									.getBasket().getBasketId(), itemId, itemId);
				}
				for (String itemId : msim.getSelectedVasItemList()) {
					simpleJdbcTemplate.update(SQL, msim.getParentOrderId(),
							msim.getMemberNum(), itemId, itemId, msi
									.getBasket().getBasketId(), itemId, itemId);
				}
				if ("MUPS01".equalsIgnoreCase(msim.getMemberOrderType())
						|| "MUPS02".equalsIgnoreCase(msim.getMemberOrderType())
						|| "MUPS05".equalsIgnoreCase(msim.getMemberOrderType())
						|| ("MUPS04".equalsIgnoreCase(msim.getMemberOrderType()) && "Y".equalsIgnoreCase(msim.getBrmInd()) && "Y".equalsIgnoreCase(msim.getBrmChgSimInd()))) {
					simpleJdbcTemplate.update(SQL, 
							msim.getParentOrderId(), 
							msim.getMemberNum(),
							msim.getSelectedSimItemId(), 
							msim.getSelectedSimItemId(), 
							msi.getBasket().getBasketId(),
							msim.getSelectedSimItemId(), 
							msim.getSelectedSimItemId());
				}
			}
		} catch (Exception e) {
			logger.error("Exception caught in insertBomWebMemberVas()", e);
			e.printStackTrace();
			throw new DAOException(e.getMessage(), e);
		}
	}

	public boolean isMultiSim(String orderId) throws DAOException {
		logger.debug("isMultiSim is called");
		List<String> resultList = new ArrayList();
		String SQL = "select member_order_id from bomweb_ord_mob_member "
				+ "where parent_order_id = ?";

		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {

			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				String id = new String();
				id = rs.getString("member_order_id");
				return id;
			}
		};
		try {
			resultList = simpleJdbcTemplate.query(SQL, mapper,
					new Object[] { orderId });
			if ((resultList != null) && (resultList.size() > 0))
				return true;
			else {
				return false;
			}
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException");
			return false;
		} catch (Exception e) {
			logger.error("Exception caught in isMultiSim()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}

	
	public void updateMemOrder(String memOrderId, String checkPoint, String errCode,
			String errString, String lastUpdBy) throws DAOException {

		logger.debug("updateMemOrder is called");
		logger.info("update memOrderId : " + memOrderId + "; checkPoint : " + checkPoint + "; errString : " + errString);

		String SQL = "update bomweb_ord_mob_member set member_status = ?, err_cd = ?, err_msg = ?, last_upd_by = ?, last_upd_date = sysdate where member_order_id = ?";

		try {
			simpleJdbcTemplate.update(SQL, checkPoint, errCode, errString, lastUpdBy, memOrderId);
		} catch (Exception e) {
			logger.error("Exception caught in updateMemOrder()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public String getMdoInd(String orderId) throws DAOException {
		
		logger.debug("getMdoInd() is called");
		
		List<String> result = new ArrayList<String>();
		
		String SQL =	"SELECT "
						+ "NVL(brm_chg_sim_ind, 'N') brm_chg_sim_ind "
						+ "FROM "
						+ "bomweb_order_mob "
						+ "WHERE "
						+ "order_id = ?";
		
		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				String mdoInd = new String();
				mdoInd = rs.getString("brm_chg_sim_ind");
				return mdoInd;
			}
		};
		
		try {
			result = simpleJdbcTemplate.query(SQL, mapper, orderId);
			if (!CollectionUtils.isEmpty(result)) {
				return result.get(0);
			} else {
				return null;
			}
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException");
			return null;
		} catch (Exception e) {
			logger.error("Exception caught in hasSimExtraFunctionCos()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}

	public String getOrderNature(String orderId) throws DAOException {
		logger.debug("getOrderNature() is called");

		List<String> list = new ArrayList<String>();

		String SQL = " select nvl(order_nature,'ACQ') ORDER_NATURE from bomweb_order_mob "
					+ " where order_id = ? ";

		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
			public String mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				String result = rs.getString("ORDER_NATURE");
				return result;
			}
		};

		try {
			logger.info("getOrderNature() @ OrderDAO: " );
			logger.debug("getOrderNature() @ OrderDAO: " + SQL);
			list = simpleJdbcTemplate.query(SQL, mapper, orderId);

		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException");

			list = null;
		} catch (Exception e) {
			logger.info("Exception caught in getOrderNature():", e);

			throw new DAOException(e.getMessage(), e);
		}
		if (list == null || list.isEmpty()) {
			return null;
		}
		return list.get(0);

	}
	
	public Map<String,String> getOrderType(String orderId) throws DAOException {
		String SQL = " select a.order_id  , decode(a.order_type,'COS','COS','CPM','CPM','CSIM','CSIM','BRM','BRM','TOO1','TOO1','ACQ') order_type, b.channel_id "
				+ " from bomweb_order a, bomweb_shop b "
				+ " where a.order_id=? "
				+ " and (substrb(a.order_id,2,3)=b.shop_cd"
				+ "  or  substrb(a.order_id,2,4)=b.shop_cd) ";
 

		ParameterizedRowMapper<Map<String,String>> mapper = new ParameterizedRowMapper<Map<String,String>>() {
			public Map<String,String> mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				HashMap<String,String> result = new HashMap<String,String>();
				result.put("order_id", rs.getString("order_id"));
				result.put("order_type", rs.getString("order_type"));
				result.put("channel_id", rs.getString("channel_id"));
				return result;
			}
		};

		try {
			logger.debug("getOrderType() @ OrderDAO: " );
			logger.debug("getOrderType() @ OrderDAO: " + SQL);
			List<Map<String,String>> list = simpleJdbcTemplate.query(SQL, mapper, orderId);
			if (CollectionUtils.isNotEmpty(list)) {
				return list.get(0);
			}
			return null;
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException");
	
			return null;
		} catch (Exception e) {
			logger.info("Exception caught in getOrderNature():", e);
	
			throw new DAOException(e.getMessage(), e);
		}
	}

	
	public String manualCosOrderStatusProcess(String orderId) throws DAOException {

		try {
			SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
					.withSchemaName("OPS$CNM")
					.withCatalogName("PKG_SB_MOB_COS_ORDER")
					.withProcedureName("MANUAL_ORDER_STATUS_PROCESS");
			jdbcCall.setAccessCallParameterMetaData(false);
			jdbcCall.declareParameters(new SqlParameter("i_order_id",
					Types.VARCHAR), new SqlOutParameter("errCode",
					Types.INTEGER), new SqlOutParameter("errText",
					Types.VARCHAR));

			MapSqlParameterSource inMap = new MapSqlParameterSource();
			inMap.addValue("i_order_id", orderId);
			SqlParameterSource in = inMap;

			Map out = jdbcCall.execute(in);
			int error_code = BomWebPortalConstant.ERRCODE_FAIL;

			if (((Integer) out.get("errCode")) != null) {
				error_code = ((Integer) out.get("errCode")).intValue();
			}

			String error_text = (String) out.get("errText");

			logger.info("PKG_SB_MOB_COS_ORDER.ORDER_STATUS_PROCESS()() output error_code = "
					+ error_code);
			logger.info("PKG_SB_MOB_COS_ORDER.ORDER_STATUS_PROCESS()() output error_text = "
					+ error_text);

			return error_text;

		} catch (Exception e) {
			logger.error("Exception caught in manualCosOrderStatusProcess()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	
	
	public String cosOrderSubmitProcess(String orderId) throws DAOException {

		try {
			SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
					.withSchemaName("OPS$CNM")
					.withCatalogName("PKG_SB_MOB_COS_ORDER")
					.withProcedureName("ORDER_SUBMIT_PROCESS");
			jdbcCall.setAccessCallParameterMetaData(false);
			jdbcCall.declareParameters(new SqlParameter("i_process_date",
					Types.VARCHAR), new SqlParameter("i_batch_no",
					Types.INTEGER), new SqlParameter("i_order_id",
					Types.VARCHAR), new SqlOutParameter("errCode",
					Types.INTEGER), new SqlOutParameter("errText",
					Types.VARCHAR));

			MapSqlParameterSource inMap = new MapSqlParameterSource();
			inMap.addValue("i_process_date",
					Utility.date2String(new Date(), "yyyyMMdd"));
			inMap.addValue("i_batch_no", 0);
			inMap.addValue("i_order_id", orderId);
			SqlParameterSource in = inMap;

			Map out = jdbcCall.execute(in);
			int error_code = BomWebPortalConstant.ERRCODE_FAIL;

			if (((Integer) out.get("errCode")) != null) {
				error_code = ((Integer) out.get("errCode")).intValue();
			}

			String error_text = (String) out.get("errText");

			logger.info("PKG_SB_MOB_COS_ORDER.ORDER_SUBMIT_PROCESS()() output error_code = "
					+ error_code);
			logger.info("PKG_SB_MOB_COS_ORDER.ORDER_SUBMIT_PROCESS()() output error_text = "
					+ error_text);

			return error_text;

		} catch (Exception e) {
			logger.error("Exception caught in orderStatusProcess()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public String getSimTypeByCosOrder(String orderId) throws DAOException {
		logger.debug("hasSimExtraFunctionCos is called");
		List<String> result = new ArrayList<String>();
		String SQL = 
				  " select nfc_ind "
				+ " from BOMWEB_ORDER_MOB "
				+ " where order_id = :orderId";
		
		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				String simType = new String();
				simType = rs.getString("nfc_ind");
				return simType;
			}
		};
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("orderId", orderId);
		
		try {
			result = simpleJdbcTemplate.query(SQL, mapper, params);
			if (!CollectionUtils.isEmpty(result)) {
				return result.get(0);
			} else {
				return null;
			}
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException");
			return null;
		} catch (Exception e) {
			logger.error("Exception caught in hasSimExtraFunctionCos()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	
	public void saveCslOrderRecord(String orderId, String staffId) throws DAOException {
		logger.debug("saveCslOrderRecord is called");

		String SQL = 
				"UPDATE BOMWEB_ORDER O " +
						"SET O.ORDER_STATUS = '20' " +
						"  , " +
						"  O.LAST_UPDATE_BY = :staffId " +
						"  , " +
						"  O.LAST_UPDATE_DATE = sysdate " +
						"WHERE O.ORDER_TYPE   = 'CSL' " +
						"AND O.LOB            = 'CSL' " +
						"AND O.ORDER_ID       = :orderId " +
						"AND NOT EXISTS " +
						"  (SELECT 'X' " +
						"  FROM BOMWEB_ALL_ORD_DOC_ASSGN AODA " +
						"  WHERE AODA.COLLECTED_IND = 'N' " +
						"  AND AODA.ORDER_ID        = O.ORDER_ID " +
						"  )";


		
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("orderId", orderId);
		params.addValue("staffId", staffId);
		try {
			simpleJdbcTemplate.update(SQL, params);
		} catch (DataAccessException e) {
			logger.error("Exception caught in saveCslOrderRecord()", e);
		}
	}
	
	public void updateDsStockMrtAfterSuccess(String orderId) throws DAOException {

		logger.debug("updateDsStockMrtAfterSuccess is called");

		//insert record to bomweb_stock_inventory_hist for mobile direct sales
		String insertStockInventoryHistSQL = "insert into bomweb_stock_inventory_hist "
				+ "(item_code, item_serial, status_id_from, status_id_to, "
				+ "event_cd, store_cd, team_cd, book_out_date, staff_id, "
				+ "create_by, create_date) "
				+ "(select item_code, item_serial, status_id, 20, "
				+ "event_cd, store_cd, team_cd, book_out_date, staff_id, "
				+ "'SBMDS', sysdate "
				+ "from bomweb_stock_inventory "
				+ "where (item_code, item_serial) in "
				+ "	  (select item_code, item_serial from bomweb_stock_assgn "
				+ "	  where order_id in "
				+ "	  		(select order_id from bomweb_order "
				+ "			where order_id = ? "
				+ "	  		and order_status = 'SUCCESS' "
				+ "			and order_id like 'D%M%')"
				+ "	   ) "
				+ ")";
		
		// update bomweb_stock_inventory for mobile direct sales
		String updateStockInventorySQL = "update bomweb_stock_inventory "
				+ "set status_id = 20, "
				+ "last_upd_by = 'DSMAmend', "
				+ "last_upd_date = sysdate "
				+ "where (item_code, item_serial) in "
				+ "	  (select item_code, item_serial from bomweb_stock_assgn "
				+ "	  where order_id in "
				+ "	  		(select order_id from bomweb_order "
				+ "			where order_id = ? "
				+ "	  		and order_status = 'SUCCESS' "
				+ "			and order_id like 'D%M%')) "
				+ "and status_id = 19";
		
		// update bomweb_stock_assgn for mobile direct sales
		String updateStockAssgnSQL = "update bomweb_stock_assgn "
				+ "set status_id = 20, "
				+ "last_upd_by = 'DSMAmend', "
				+ "last_upd_date = sysdate "
				+ "where order_id in "
				+ "	  (select order_id from bomweb_order where "
				+ "	  order_id = ? "
				+ "	  and order_status = 'SUCCESS' "
				+ "	  and order_id like 'D%M%') "
				+ "and status_id = 19";
		
		String updateMrtInventorySQL = "update BOMWEB_MRT_INVENTORY "
				+ "set MSISDN_STATUS = 20, "
				+ "last_upd_by = 'DSMAmend', "
				+ "last_upd_date = sysdate "
				+ "where (MSISDN in" 
				+ "( SELECT MSISDN "
				+ "  FROM BOMWEB_ORDER BO"
				+ "  WHERE BO.ORDER_ID = ? "
				+ "	 AND   BO.order_status = 'SUCCESS' "
				+ "	 AND   BO.order_id like 'D%M%') "
				+ "or MSISDN in "
				+ "( SELECT BOMM.MSISDN "
				+ "  FROM BOMWEB_ORD_MOB_MEMBER BOMM JOIN BOMWEB_ORDER BO"
				+ "  ON BOMM.PARENT_ORDER_ID = BO.ORDER_ID "
				+ "  WHERE BOMM.PARENT_ORDER_ID = ? "
				+ "	 AND   BO.ORDER_STATUS = 'SUCCESS' "
				+ "	 AND   BO.ORDER_ID like 'D%M%')) "
				+ "and msisdn_status = 19";
		
		try {
			simpleJdbcTemplate.update(insertStockInventoryHistSQL, orderId);
			simpleJdbcTemplate.update(updateStockInventorySQL, orderId);
			simpleJdbcTemplate.update(updateStockAssgnSQL, orderId);
			simpleJdbcTemplate.update(updateMrtInventorySQL, orderId, orderId);
		} catch (Exception e) {
			logger.error("Exception caught in updateDsStockMrtAfterSuccess()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public MobileSimInfoDTO getChangedSim(String orderId) throws DAOException {
		logger.debug("getSim() is called");

		List<MobileSimInfoDTO> simList = new ArrayList<MobileSimInfoDTO>();

		String SQL = "SELECT SIM_ICCID " 
				+ "FROM BOMWEB_ORD_MOB_CHG_SIM_TXN " 
				+ "WHERE mark_del_ind  <> 'Y' " 
				+ "AND waive_reason_cd IS NULL " 
				+ "AND order_id         = :orderId";

		ParameterizedRowMapper<MobileSimInfoDTO> mapper = new ParameterizedRowMapper<MobileSimInfoDTO>() {
			public MobileSimInfoDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				MobileSimInfoDTO dto = new MobileSimInfoDTO();

				dto.setIccid(rs.getString("SIM_ICCID"));
				
				return dto;
			}
		};

		try {
			logger.debug("getChangedSim() @ OrderDAO: ");
			logger.debug("getChangedSim() @ OrderDAO: " + SQL);

			simList = simpleJdbcTemplate.query(SQL, mapper, orderId);
			if (!CollectionUtils.isEmpty(simList)) {
				return simList.get(0);
			} else {
				return null;
			}
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException");

			return null;
		} catch (Exception e) {
			logger.info("Exception caught in getPayment():", e);

			throw new DAOException(e.getMessage(), e);
		}

	}
	
	public String getOrderRpWaiveReason(String orderId)
			throws DAOException {

		String rpWaiveReason = "";

		String SQL = "select WAIVE_REASON from bomweb_subscribed_item "
				+ "where order_id = :orderId "
				+ "and type='RP' "
				+ "and rownum = 1";

		try {
			logger.debug("getSimWaiveReason @ MobileSimInfoDAO: " + SQL);
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("orderId",orderId);
			rpWaiveReason = (String) simpleJdbcTemplate.queryForObject(SQL,
					String.class, params);

		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException in getOrderRpWaiveReason()");
			rpWaiveReason = "";
		} catch (Exception e) {
			logger.error("Exception caught in getOrderRpWaiveReason()", e);
			throw new DAOException(e.getMessage(), e);
		}
		return rpWaiveReason;
	}
	
	
	public Double getOrderRpCharge(String orderId) throws DAOException {
		String SQL= "SELECT NVL(SUM(ONETIME_AMT),0) rpCharge " +
				"FROM BOMWEB_SUBSCRIBED_ITEM A, " +
				"  W_ITEM_PRICING B, " +
				"  BOMWEB_ORDER BO " +
				"WHERE A.ORDER_ID= :orderId " +
				"AND A.ID        =B.ID " +
				"AND A.ORDER_ID  =BO.ORDER_ID " +
				"AND TRUNC(BO.APP_DATE) BETWEEN TRUNC(NVL(B.EFF_START_DATE,SYSDATE)) AND TRUNC(NVL(B.EFF_END_DATE,SYSDATE)) " +
				"AND NVL(A.TYPE,'#')='RP'";
		
		try {
			Double result = (Double) simpleJdbcTemplate.queryForObject(SQL, Double.class, orderId);
			return (result == null ? new Double(0) : result);
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("getVasHSAmt() EmptyResultDataAccessException");
			return new Double(0);
		} catch (Exception e) {
			logger.error("Exception caught in getOrderRpCharge()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}

	/*public boolean updateDsCashPay(String orderId, MobDsPaymentTransDTO paymentTrans) 
			throws DAOException{
		logger.info("updateDsCashPay is called");

		String SQL = "update bomweb_payment_trans"
				+ "set payment_shop_cd=?, invoice_no=?, remark=?"
				+ "where order_id=? ";

		try {
			boolean result = false;
			if ("M".equals(paymentTrans.getPaymentType())) {
				simpleJdbcTemplate.update(SQL, paymentTrans.getStoreCd(),paymentTrans.getInvoiceNo(),
						paymentTrans.getRemark(), paymentTrans.getOrderId());
				result = true;
			}
			return result;
		} catch (Exception e) {
			logger.error("Exception caught in updateDsCashPay()", e);
			//throw new DAOException(e.getMessage(), e);
			return false;
		}
	}*/
	
	
	public boolean hasTNGServiceDummyVas(String orderId)
			throws DAOException {
		logger.debug("hasTNGServiceDummyVas is called");
		String tngServiceFlag ="";
		
		String SQL = "SELECT DECODE(COUNT(*),0,'N','Y') " +
				"FROM bomweb_subscribed_item " +
				"WHERE order_id= :orderId " +
				"AND id       IN " +
				"  (SELECT DISTINCT code_id FROM bomweb_code_lkup WHERE code_type in ('TNG_CARD','TNG_SIM') " +
				"  )";

		try {
			logger.debug("hasTNGServiceDummyVas @ OrderDAO: " + SQL);
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("orderId",orderId);
			tngServiceFlag = (String) simpleJdbcTemplate.queryForObject(SQL,
					String.class, params);
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException");
			tngServiceFlag = "";
		} catch (Exception e) {
			logger.error("Exception caught in hasTNGServiceDummyVas()", e);
			throw new DAOException(e.getMessage(), e);
		}
		if ("Y".equalsIgnoreCase(tngServiceFlag)) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean hasStudentPlanTNGStock(String orderId) throws DAOException {
		logger.debug("hasStudentPlanTNGStock is called");
		String studentPlanFlag ="";
		
		String SQL = "SELECT DECODE(COUNT(*),0,'N','Y') " +
				"FROM bomweb_stock_assgn bsa " +
				"WHERE bsa.order_id= :orderId " +
				"AND bsa.status_id = '19' " +
				"AND bsa.item_code in (" +
				"  select code_id from bomweb_code_lkup where code_type = 'TG_PACK') ";

		try {
			logger.debug("hasStudentPlanTNGStock @ OrderDAO: " + SQL);
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("orderId",orderId);
			studentPlanFlag = (String) simpleJdbcTemplate.queryForObject(SQL, String.class, params);
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException");
			studentPlanFlag = "";
		} catch (Exception e) {
			logger.error("Exception caught in hasStudentPlanTNGStock()", e);
			throw new DAOException(e.getMessage(), e);
		}
		if ("Y".equalsIgnoreCase(studentPlanFlag)) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean isCosOrderFrozen(String orderId) throws DAOException {
		logger.debug("isCosOrderFrozen() is called");

		List<String> list = new ArrayList<String>();

		String SQL = " select order_id from bomweb_cos_order_frozen "
				   + " where order_id = ? ";

		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
			public String mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				String result = rs.getString("order_id");
				return result;
			}
		};

		try {
			logger.info("isCosOrderFrozen() @ OrderDAO: " );
			logger.debug("isCosOrderFrozen() @ OrderDAO: " + SQL);
			list = simpleJdbcTemplate.query(SQL, mapper, orderId);

		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException");

			list = null;
		} catch (Exception e) {
			logger.info("Exception caught in isCosOrderFrozen():", e);

			throw new DAOException(e.getMessage(), e);
		}
		if (list == null || list.isEmpty()) {
			return false;
		} else {
			return true;
		}
	}
	
	
	public VasMrtSelectionDTO getVasMrtSelectionDTO(String orderId) throws DAOException {
		logger.debug("getVasMrtSelectionDTO is called");
		String SQL = 
				  "select \n"
				+ "  order_id\n"
				+ ", seq_id\n"
				+ ", mnp_ind\n"
				+ ", golden_ind\n"
				+ ", china_ind\n"
				+ ", msisdn\n"
				+ ", msisdnlvl\n"
				+ ", city_cd\n"
				+ ", srv_req_date\n"
				+ ", cut_over_date\n"
				+ ", cut_over_time\n"
				+ ", rno\n"
				+ ", dno\n"
				+ ", num_type\n"
				+ "from bomweb_mrt \n"
				+ "where seq_id = 2 \n"
				+ "and china_ind = 'Y'\n"
				+ "and order_id = :orderId\n";

		ParameterizedRowMapper<VasMrtSelectionDTO> mapper = new ParameterizedRowMapper<VasMrtSelectionDTO>() {
			public VasMrtSelectionDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				VasMrtSelectionDTO dto = new VasMrtSelectionDTO();
				dto.setMsisdn(rs.getString("msisdn"));
				dto.setMsisdnLvl(rs.getString("msisdnlvl"));
				dto.setCityCd(rs.getString("city_cd"));
				dto.setChinaInd(rs.getString("china_ind"));
				dto.setNumType(rs.getString("num_type"));
				dto.setChinaNumberSubscribed(true);
				return dto;
			}
		};
		
		try {
			logger.debug("getVasMrtSelectionDTO @ OrderDAO: " + SQL);
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("orderId",orderId);
			
			List<VasMrtSelectionDTO> result = simpleJdbcTemplate.query(SQL, mapper, params);
			if (CollectionUtils.isNotEmpty(result)) {
				return result.get(0);
			} else {
				return null;
			}
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException");
			return null;
		} catch (Exception e) {
			logger.error("Exception caught in getVasMrtSelectionDTO()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	
	public VasMrtSelectionDTO getSsMrtSelectionDTO(String orderId) throws DAOException {
		logger.debug("getSsMrtSelectionDTO is called");
		String SQL = 
				  "select s.sec_srv_num\n"
				+ ", s.old_sec_srv_num\n"
				+ ", si.sim_type\n"
				+ "from bomweb_sub s\n"
				+ ",BOMWEB_SIM SI\n"
				+ "where s.order_id = si.order_id(+)\n"
				+ "and s.order_id = :orderId\n";

		ParameterizedRowMapper<VasMrtSelectionDTO> mapper = new ParameterizedRowMapper<VasMrtSelectionDTO>() {
			public VasMrtSelectionDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				VasMrtSelectionDTO dto = new VasMrtSelectionDTO();
				dto.setSecSrvNum(rs.getString("sec_srv_num"));
				dto.setDbSecSrvNum(rs.getString("sec_srv_num"));
				dto.setOldSecSrvNum(rs.getString("old_sec_srv_num"));
				dto.setNumType(rs.getString("sim_type"));
				if (StringUtils.isNotBlank(dto.getSecSrvNum())){
					dto.setSsSubscribed(true);
				}
			
				dto.setMsisdn(rs.getString("sec_srv_num"));
				return dto;
			}
		};
		
		try {
			logger.debug("getSsMrtSelectionDTO @ OrderDAO: " + SQL);
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("orderId",orderId);
			
			List<VasMrtSelectionDTO> result = simpleJdbcTemplate.query(SQL, mapper, params);
			if (CollectionUtils.isNotEmpty(result)) {
				return result.get(0);
			} else {
				return null;
			}
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException");
			return null;
		} catch (Exception e) {
			logger.error("Exception caught in getVasMrtSelectionDTO()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	
	public String getOrderBrand(String orderId) throws DAOException {
		List<String> list = new ArrayList<String>();

		String SQL = "SELECT BRAND FROM BOMWEB_SUB WHERE order_id = :orderId";

		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {

			public String mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return rs.getString("BRAND");
			}
		};
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("orderId", orderId);

		try {
			list = simpleJdbcTemplate.query(SQL,mapper,  params);
		} catch (EmptyResultDataAccessException erdae) {
			logger.error("EmptyResultDataAccessException caught in getOrderBrand() @ ShopDAO", erdae);
			return null;
		} catch (Exception e) {
			logger.error("Exception caught in getOrderBrand() @ orderDAO", e);
			throw new DAOException(e.getMessage(), e);
		}
		return (list == null || list.isEmpty()) ? null : list.get(0);
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
	
	// update CNMRT_SUPPORT_DOC TABLE for upload image
	public int updateCNMRTSupportDoc(CNMRTSupportDocDTO dto) throws DAOException {
		logger.debug("updateCNMRTSupportDoc @ OrderDAO is called");
		String sql = "UPDATE "
				+ "CNMRT_SUPPORT_DOC "
				+ "SET STATUS = :status "
				+ "WHERE MRT_CN = :mrtCn AND "
				+ "IMAGE_TYPE = :imageType AND "
				+ "SEQ_NO = :seqNo";
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("status", dto.getStatus());
		params.addValue("mrtCn", dto.getMrtCn());
		params.addValue("imageType", dto.getImageType());
		params.addValue("seqNo", dto.getSeqNo());
		try {
			//logger.info("success");
			return simpleJdbcTemplate.update(sql, params);
		} catch (Exception e) {
			logger.error("Exception caught in updateCNMRTSupportDoc()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	// get CNMRT_SUPPORT_DOC TABLE
	public List<CNMRTSupportDocDTO> getCNMRTSupportDocList(CNMRTSupportDocDTO dto) throws DAOException{
		logger.debug("getCNMRTSupportDocList @ SchedulerDAO is called");
		List<CNMRTSupportDocDTO> resultList = new ArrayList<CNMRTSupportDocDTO> ();
		String sql = "select "
				+ "(SYSDATE - UPLOAD_DATE) * 24 AS DURATION, "
				+ "MRT_CN, "
				+ "IMAGE_TYPE, "
				+ "SEQ_NO, "
				+ "STATUS, "
				+ "UPLOAD_DATE "
				+ "from CNMRT_SUPPORT_DOC "
				+ "WHERE MRT_CN = :mrtCn";
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("mrtCn", dto.getMrtCn());
		ParameterizedRowMapper<CNMRTSupportDocDTO> mapper = new ParameterizedRowMapper<CNMRTSupportDocDTO>() {
			public CNMRTSupportDocDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				
				CNMRTSupportDocDTO temp = new CNMRTSupportDocDTO();
				temp.setMrtCn(rs.getString("MRT_CN"));
				temp.setImageType(rs.getString("IMAGE_TYPE"));
				temp.setSeqNo(rs.getInt("SEQ_NO"));
				temp.setStatus(rs.getString("STATUS"));
				temp.setUploadDate(rs.getDate("UPLOAD_DATE"));
				temp.setDuration(rs.getDouble("DURATION"));
				return temp;
			}
		};
		try {
			resultList = simpleJdbcTemplate.query(sql, mapper, params);
			return resultList;
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException in getCNMRTSupportDocList()");
			return null;
		} catch (Exception e) {
			logger.info("Exception caught in getCNMRTSupportDocList()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	// get CNMRT_SUPPORT_DOC TABLE New Seq
	public String getCNMRTSupportDocNewSeqNo(CNMRTSupportDocDTO dto) throws DAOException{
		logger.debug("getCNMRTSupportDocNewSeqNo @ SchedulerDAO is called");
		String sql = "select MAX(SEQ_NO) + 1 from CNMRT_SUPPORT_DOC WHERE MRT_CN = :mrtCn AND IMAGE_TYPE = :imageType";
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("mrtCn", dto.getMrtCn());
		params.addValue("imageType", dto.getImageType());
		try {
			return (String) this.simpleJdbcTemplate.queryForObject(
					sql,  String.class, params);
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException in getCNMRTSupportDocNewSeqNo()");
			return null;
		} catch (Exception e) {
			logger.info("Exception caught in getCNMRTSupportDocNewSeqNo()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	// create CNMRT_SUPPORT_DOC TABLE for upload image
	public int createCNMRTSupportDoc(CNMRTSupportDocDTO dto) throws DAOException {
		logger.debug("createCNMRTSupportDoc @ OrderDAO is called");
		String sql = "INSERT INTO CNMRT_SUPPORT_DOC (MRT_CN, IMAGE_TYPE, SEQ_NO, STATUS, UPLOAD_DATE) VALUES (:mrtCn,:imageType,:seqNo,:status,SYSDATE)";
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("status", dto.getStatus());
		params.addValue("mrtCn", dto.getMrtCn());
		params.addValue("imageType", dto.getImageType());
		params.addValue("seqNo", dto.getSeqNo());
		try {
			return simpleJdbcTemplate.update(sql, params);
		} catch (Exception e) {
			logger.error("Exception caught in createCNMRTSupportDoc()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	private static final String getPendingOrderListByMsisdnSQL = "(SELECT o.order_id " + "FROM bomweb_order o "
			+ "WHERE (o.order_status IN ('01', '03', '99') " + "OR (o.order_status     = 'SUCCESS' "
			+ "AND o.srv_req_date     > sysdate) " + "OR (o.order_status     = '02' "
			+ "AND o.srv_req_date     > sysdate)) " + "AND o.msisdn           = :V_MSISDN "
			+ "AND o.order_id        != NVL(:V_ORDER_ID, 'XXXXX') " + "AND o.lob              = 'MOB' )" + "UNION "
			+ "(SELECT mm.parent_order_id order_id " + "FROM bomweb_ord_mob_member mm, " + "  bomweb_order o "
			+ "WHERE o.order_id        = mm.parent_order_id " + "AND (o.order_status    IN ('01', '03', '99') "
			+ "OR (o.order_status      = 'SUCCESS' " + "AND o.srv_req_date      > sysdate) "
			+ "OR (o.order_status      = '02' " + "AND o.srv_req_date      > sysdate)) "
			+ "AND mm.msisdn           = :V_MSISDN " + "AND mm.parent_order_id != NVL(:V_ORDER_ID, 'XXXXX') "
			+ "AND o.lob               = 'MOB')";

	public List<OrderDTO> getSbPendingOrderListByMsisdn(String msisdn, String orderId) throws DAOException {
		if (logger.isDebugEnabled()) {
			logger.debug("getSbPendingOrderListByMsisdn is called");
		}
		List<OrderDTO> itemList = Collections.emptyList();
		try {
			if (logger.isDebugEnabled()) {
				logger.debug("getSbPendingOrderListByMsisdn() @ OrderRemarkDAO: " + getPendingOrderListByMsisdnSQL);
			}

			ParameterizedRowMapper<OrderDTO> mapper = new ParameterizedRowMapper<OrderDTO>() {
				public OrderDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
					OrderDTO dto = new OrderDTO();
					dto.setOrderId(rs.getString("ORDER_ID"));

					return dto;
				}
			};

			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("V_MSISDN", msisdn);
			params.addValue("V_ORDER_ID", orderId);
			itemList = simpleJdbcTemplate.query(getPendingOrderListByMsisdnSQL, mapper, params);
		} catch (EmptyResultDataAccessException erdae) {
			if (logger.isInfoEnabled()) {
				logger.info("EmptyResultDataAccessException in getSbPendingOrderListByMsisdn()");
			}
			itemList = Collections.emptyList();
		} catch (Exception e) {
			if (logger.isInfoEnabled()) {
				logger.info("Exception caught in getSbPendingOrderListByMsisdn():", e);
			}
			throw new DAOException(e.getMessage(), e);
		}
		return itemList;
	}
	private static final String getTooOrderMrtSql = "Select msisdn from bomweb_mrt where order_id = :orderId and seq_id = '1' ";

	public String getTooOrderMrt(String orderId) throws DAOException {
		if (logger.isInfoEnabled()) {
			logger.info("getTooOrderMrt is called");
		}
		List<String> msisdnList = Collections.emptyList();
		try {
			if (logger.isDebugEnabled()) {
				logger.debug("getTooOrderMrt() @ OrderDao: " + getTooOrderMrtSql);
			}

			ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
				public String mapRow(ResultSet rs, int rowNum) throws SQLException {
					String msisdn = rs.getString("msisdn");

					return msisdn;
				}
			};

			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("orderId", orderId);
			msisdnList = simpleJdbcTemplate.query(getTooOrderMrtSql, mapper, params);
		} catch (EmptyResultDataAccessException erdae) {
			if (logger.isInfoEnabled()) {
				logger.info("EmptyResultDataAccessException in getTooOrderMrt()");
			}
			msisdnList = Collections.emptyList();
		} catch (Exception e) {
			if (logger.isInfoEnabled()) {
				logger.info("Exception caught in getTooOrderMrt():", e);
			}
			throw new DAOException(e.getMessage(), e);
		}
		if (msisdnList.size() > 0) {
			return msisdnList.get(0);
		} else {
			return null;
		}
	}
	
	private static final String getMnpIndSql = "Select nvl(mnp_ind,'N') mnp_ind from bomweb_order where order_id = :orderId  ";

	public String getMnpInd(String orderId) throws DAOException {
		if (logger.isDebugEnabled()) {
			logger.debug("getMnpInd is called");
		}
		String mnpInd= "N";
		try {
			if (logger.isDebugEnabled()) {
				logger.debug("getMnpIndSql() @ OrderDao: " + getMnpIndSql);
			}

			ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
				public String mapRow(ResultSet rs, int rowNum) throws SQLException {
					String mnpInd = rs.getString("mnp_ind");

					return mnpInd;
				}
			};

			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("orderId", orderId);
			mnpInd = simpleJdbcTemplate.queryForObject(getMnpIndSql, mapper, params);
		} catch (EmptyResultDataAccessException erdae) {
			if (logger.isInfoEnabled()) {
				logger.info("EmptyResultDataAccessException in getMnpInd()");
			}
		} catch (Exception e) {
			if (logger.isInfoEnabled()) {
				logger.info("Exception caught in getMnpInd():", e);
			}
		}
		return mnpInd;
	}
	

	private static final String getClubPointDetailsByOrderIdSQL = 
			"select c.the_club_login\n" +
					"      ,club_mem_id\n" + 
					"      ,i.id item_id\n" + 
					"      ,ioa.offer_id\n" + 
					"      ,pca.product_id\n" + 
					"      ,pca.sub_pack_cd\n" + 
					"      ,pca.sub_club_pt\n" + 
					"      ,pca.com_pack_cd\n" + 
					"      ,pca.com_club_pt\n" + 
					"      ,o.order_id\n" + 
					"      ,o.shop_cd\n" + 
					"      ,o.sales_cd\n" + 
					"      ,o.msisdn\n" + 
					"  from bomweb_order               o\n" + 
					"      ,bomweb_customer            c\n" + 
					"      ,bomweb_subscribed_item     bsi\n" + 
					"      ,w_item                     i\n" + 
					"      ,w_item_offer_assgn         ioa\n" + 
					"      ,w_item_offer_product_assgn iopa\n" + 
					"      ,w_product_clubpt_assgn     pca\n" + 
					" where o.order_id = bsi.order_id\n" + 
					"   and o.order_id = :v_order_id\n" + 
					"   and o.order_id = c.order_id\n" + 
					"   and bsi.id = i.id\n" + 
					"   and i.id = ioa.item_id\n" + 
					"   and ioa.item_id = iopa.item_id\n" + 
					"   and ioa.item_offer_seq = iopa.item_offer_seq\n" + 
					"   and iopa.product_id = pca.product_id\n" + 
					"   and trunc(o.app_date) between pca.start_date and trunc(nvl(pca.end_date, sysdate))";

	public List<ClubPointDetailDTO> getClubPointDetailsByOrderId(String orderId)
			throws DAOException {
		
		if (logger.isDebugEnabled()) {
			logger.debug("getClubPointDetailsByOrderId is called");
		}
		List<ClubPointDetailDTO> resultList = Collections.emptyList();
		try {
			if (logger.isDebugEnabled()) {
				logger.debug("getClubPointDetailsByOrderId() @ OrderDAO: "
						+ getPendingOrderListByMsisdnSQL);
			}
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("v_order_id", orderId);
			
			ParameterizedRowMapper<ClubPointDetailDTO> mapper = new ParameterizedRowMapper<ClubPointDetailDTO>() {
				public ClubPointDetailDTO mapRow(ResultSet rs, int rowNum)
						throws SQLException {
					ClubPointDetailDTO dto = new ClubPointDetailDTO();
					dto.setOrderId(rs.getString("order_id"));
					dto.setTheClubLogin(rs.getString("the_club_login"));
					dto.setClubMemberId(rs.getString("club_mem_id"));
					dto.setItemId(rs.getString("item_id"));
					dto.setOfferId(rs.getString("offer_id"));
					dto.setProductId(rs.getString("product_id"));
					dto.setSubPackageCode(rs.getString("sub_pack_cd"));
					dto.setSubClubPoint(rs.getInt("sub_club_pt"));
					dto.setComPackageCode(rs.getString("com_pack_cd"));
					dto.setComClubPoint(rs.getInt("com_club_pt"));
					dto.setShopCd(rs.getString("shop_cd"));
					dto.setSalesCd(rs.getString("sales_cd"));
					dto.setMsisdn(rs.getString("msisdn"));
					return dto;
				}
			};
			
			resultList = simpleJdbcTemplate.query(getClubPointDetailsByOrderIdSQL, mapper, params);
			
		} catch (EmptyResultDataAccessException erdae) {
			if (logger.isInfoEnabled()) {
				logger.info("EmptyResultDataAccessException in getClubPointDetailsByOrderId()");
			}
			resultList = Collections.emptyList();
		} catch (Exception e) {
			if (logger.isInfoEnabled()) {
				logger.info("Exception caught in getClubPointDetailsByOrderId():", e);
			}
			throw new DAOException(e.getMessage(), e);
		}
		return resultList;
	}
	
	private static final String getBasketIdSql = "select distinct(basket_id) from BOMWEB_SUBSCRIBED_ITEM where order_id = :orderId and basket_id is not null ";

	public String getBasketId(String orderId) throws DAOException {
		if (logger.isInfoEnabled()) {
			logger.info("getBasketId is called");
		}
		String basketId= null;
		try {
			if (logger.isDebugEnabled()) {
				logger.debug("getBasketId() @ OrderDao: " + getBasketIdSql);
			}

			ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
				public String mapRow(ResultSet rs, int rowNum) throws SQLException {
					String basketId = rs.getString("basket_id");
					return basketId;
				}
			};

			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("orderId", orderId);
			basketId = simpleJdbcTemplate.queryForObject(getBasketIdSql, mapper, params);
		} catch (EmptyResultDataAccessException erdae) {
			if (logger.isInfoEnabled()) {
				logger.info("EmptyResultDataAccessException in getBasketId()");
			}
		} catch (Exception e) {
			if (logger.isInfoEnabled()) {
				logger.info("Exception caught in getBasketId():", e);
			}
		}
		return basketId;
	}
	
	private static final String getOnlineIndSql = "Select nvl(ol_ind,'N') ol_ind from bomweb_order_mob where order_id = :orderId  ";

	public String getOnlineInd(String orderId) throws DAOException {
		if (logger.isDebugEnabled()) {
			logger.debug("getOnlineInd is called");
		}
		String olInd= "N";
		try {
			if (logger.isDebugEnabled()) {
				logger.debug("getOnlineInd() @ OrderDao: " + getOnlineIndSql);
			}

			ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
				public String mapRow(ResultSet rs, int rowNum) throws SQLException {
					String mnpInd = rs.getString("ol_ind");

					return mnpInd;
				}
			};

			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("orderId", orderId);
			olInd = simpleJdbcTemplate.queryForObject(getOnlineIndSql, mapper, params);
		} catch (EmptyResultDataAccessException erdae) {
			if (logger.isInfoEnabled()) {
				logger.info("EmptyResultDataAccessException in getOnlineInd()");
			}
		} catch (Exception e) {
			if (logger.isInfoEnabled()) {
				logger.info("Exception caught in getOnlineInd():", e);
			}
		}
		return olInd;
	}

	public void insertBomWebOrdMobItem(String orderId, String lastUpdateBy) throws DAOException {
		logger.debug("insertBomWebOrdMobItem is called");
		// get hs item id, if request multiple device, need to revise this sql
		String sql = "insert into bomweb_ord_mob_item\n"+
		"  (order_id\n"+
		"  ,item_id\n"+
		"  ,eagle_ind\n"+
		"  ,eagle_hs_item_id\n"+
		"  ,eagle_reg_status\n"+
		"  ,eagle_cust_id\n"+
		"  ,eagle_sub_id\n"+
		"  ,create_by\n"+
		"  ,last_upd_by)\n"+
		"select si.order_id as order_id\n"+
		",si.id as item_id\n"+
		",'Y' as eagle_ind\n"+
		",(select sii.id\n"+
		"from bomweb_subscribed_item sii\n"+
		"where sii.type = 'HS'\n"+
		"and sii.order_id = si.order_id) as eagle_hs_item_id\n"+
		",'REG_INITIAL' as eagle_reg_status\n"+
		",null as eagle_cust_id\n"+
		",null as eagle_sub_id\n"+
		",:lastUpdateBy as create_by\n"+
		",:lastUpdateBy as last_upd_by\n"+
		"from bomweb_subscribed_item si, w_item_selection_grp_assgn isga\n"+
		"where si.id = isga.item_id\n"+
		"and isga.grp_id = 6666666663\n"+
		"and si.order_id = :orderId";
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("orderId", orderId);
		params.addValue("lastUpdateBy", lastUpdateBy);

		try {
			simpleJdbcTemplate.update(sql, params);
		} catch (Exception e) {
			logger.error("Exception caught in insertBomWebOrdMobItem()", e);
			throw new DAOException(e.getMessage(), e);
		}
		
	}

}
