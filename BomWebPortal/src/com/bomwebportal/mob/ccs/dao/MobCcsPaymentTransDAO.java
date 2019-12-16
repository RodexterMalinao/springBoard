package com.bomwebportal.mob.ccs.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.util.CollectionUtils;

import com.bomwebportal.dao.BaseDAO;
import com.bomwebportal.dto.MultiSimInfoMemberDTO;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.mob.ccs.dto.MobCcsMagentoCouponDTO;
import com.bomwebportal.mob.ccs.dto.MobCcsPaymentNewTransUI;
import com.bomwebportal.mob.ccs.dto.MobCcsPaymentTransDTO;
import com.bomwebportal.mob.ccs.dto.MobCcsPaymentUpfrontDTO;
import com.bomwebportal.mob.ccs.dto.OnlinePaymentTxn;
import com.bomwebportal.util.Utility;

public class MobCcsPaymentTransDAO extends BaseDAO {
	protected final Log logger = LogFactory.getLog(getClass());
	public void insertMobCcsPaymentNewTransUI(MobCcsPaymentNewTransUI dto)
			throws DAOException {

		logger.info("insertMobCcsPaymentNewTransUI() is called");
		String sql = "insert into bomweb_payment_trans     \n"
				+ "  (order_id,     \n" + "   payment_ref_no,     \n"
				+ "   trans_date,     \n" + "   payment_code,     \n"
				+ "   payment_amount,     \n" + "   create_by,     \n"
				+ "   create_date,     \n" + "   last_upd_by)     \n"
				+ "values     \n" + "  (?, ?, ?, ?, ?, ?, sysdate, ?)     \n";

		try {
			simpleJdbcTemplate.update(sql, dto.getOrder_id(), dto.getRef_no(),
					dto.getTransDate(), dto.getPay_comb(), dto.getPay_amount(),
					dto.getLastUpdateBy(), dto.getLastUpdateBy());
		} catch (Exception e) {
			logger.error("Exception caught in insertBOMWebPaymentTrans()", e);
			throw new DAOException(e.getMessage(), e);
		}

	}

	private static String getMobCcsUpfrontPaymentDTOListSQL = "select bo.order_id,     \n"
			+ "       bo.msisdn,     \n"
			/* + "       bup.cc_type,     \n" */
			+ "       (select code_desc     \n"
			+ "          from bomweb_code_lkup clkup     \n"
			+ "         where clkup.code_type = 'CARD_TYPE'     \n"
			+ "           and clkup.code_id = bup.cc_type) cc_type,     \n"
			+ "       bup.cc_num,     \n"
			+ "       bup.cc_hold_name,     \n"
			+ "       bup.cc_exp_date,     \n"
			+ "       (select code_desc     \n"
			+ "          from bomweb_code_lkup clkup     \n"
			+ "         where clkup.code_type = 'PAY_METHOD'     \n"
			+ "           and clkup.code_id = bup.pay_method) pay_method,     \n"
			+ "       bup.pay_amt,     \n"
			+ "       4988 up_amount,     \n"
			+ "       4988 - (select nvl(sum(pt.payment_amount), 0)     \n"
			+ "                 from bomweb_payment_trans pt     \n"
			+ "                where pt.order_id = bo.order_id) out_amount     \n"
			+ "  from bomweb_order bo     \n"
			+ " inner join bomweb_upfront_payment bup     \n"
			+ "    on (bo.order_id = bup.order_id and bup.pay_mtd_type = 'C')     \n"
			+ " where bup.order_id = nvl(?, bup.order_id)     \n";

	public List<MobCcsPaymentUpfrontDTO> getMobCcsUpfrontPaymentDTOList(
			String order_id) throws DAOException {
		logger.info(" getMobCcsUpfrontPaymentDTOList is called");
		List<MobCcsPaymentUpfrontDTO> upfrontPaymentList = new ArrayList<MobCcsPaymentUpfrontDTO>();

		ParameterizedRowMapper<MobCcsPaymentUpfrontDTO> mapper = new ParameterizedRowMapper<MobCcsPaymentUpfrontDTO>() {
			public MobCcsPaymentUpfrontDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
			    	MobCcsPaymentUpfrontDTO dto = new MobCcsPaymentUpfrontDTO();
				dto.setOrder_id(rs.getString("ORDER_ID"));
				dto.setMrt(rs.getString("MSISDN"));
				dto.setCc_type(rs.getString("CC_TYPE"));
				dto.setCc_num(rs.getString("CC_NUM"));
				dto.setCc_hold_name(rs.getString("CC_HOLD_NAME"));
				dto.setCc_exp_date(rs.getString("CC_EXP_DATE"));
				dto.setPay_method(rs.getString("PAY_METHOD"));
				dto.setAd_amount(rs.getDouble("PAY_AMT"));
				dto.setUp_amount(rs.getDouble("UP_AMOUNT"));
				dto.setOut_amout(rs.getDouble("OUT_AMOUNT"));

				return dto;
			}
		};

		try {
			logger.info("getMobCcsUpfrontPaymentDTOList() @ MobCcsUpfrontPaymentDTO: "
					+ getMobCcsUpfrontPaymentDTOListSQL);

			upfrontPaymentList = simpleJdbcTemplate.query(
					getMobCcsUpfrontPaymentDTOListSQL, mapper, order_id);
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException in getMobCCSBOMWebUpfrontPaymentDTO()");
			upfrontPaymentList = null;
		} catch (Exception e) {
			logger.info(
					"Exception caught in getMobCCSBOMWebUpfrontPaymentDTO():",
					e);

			throw new DAOException(e.getMessage(), e);
		}
		return upfrontPaymentList;

	}

	private static String getMobCcsPaymentTransDTOListSQL = "select bo.msisdn,     \n"
			+ "       bo.order_id,     \n"
			+ "       pt.trans_date,     \n"
			+ "       (select code_desc     \n"
			+ "          from bomweb_code_lkup clkup     \n"
			+ "         where clkup.code_type = 'CARD_TYPE'     \n"
			+ "           and clkup.code_id = up.cc_type) cc_type,     \n"
			+ "       (select code_desc     \n"
			+ "          from bomweb_code_lkup clkup     \n"
			+ "         where clkup.code_type = 'PAY_METHOD'     \n"
			+ "           and clkup.code_id = pt.payment_code) payment_code,     \n"
			+ "       pt.payment_amount,     \n"
			+ "       pt.payment_ref_no,     \n"
			+ "       pt.last_upd_by     \n"
			+ "  from bomweb_order bo     \n"
			+ " inner join bomweb_upfront_payment up     \n"
			+ "    on (bo.order_id = up.order_id and up.pay_mtd_type = 'C')     \n"
			+ " inner join bomweb_payment_trans pt     \n"
			+ "    on (up.order_id = pt.order_id)     \n"
			+ " where trim(pt.trans_date) between nvl(?, trim(pt.trans_date)) and     \n"
			+ "       nvl(?, trim(pt.trans_date))     \n"
			+ "   and pt.payment_code = nvl(?, pt.payment_code)     \n"
			+ "   and bo.msisdn = nvl(?, bo.msisdn)     \n";

	public List<MobCcsPaymentTransDTO> getMobCcsPaymentTransDTOList(
			Date startDate, Date endDate, String paymentMethod, String mrt)
			throws DAOException {

		logger.info(" getMobCcsPaymentTransDTOList is called");

		List<MobCcsPaymentTransDTO> itemList = new ArrayList<MobCcsPaymentTransDTO>();

		ParameterizedRowMapper<MobCcsPaymentTransDTO> mapper = new ParameterizedRowMapper<MobCcsPaymentTransDTO>() {

			public MobCcsPaymentTransDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				MobCcsPaymentTransDTO dto = new MobCcsPaymentTransDTO();
				dto.setMrt(rs.getString("MSISDN"));
				dto.setOrder_id(rs.getString("ORDER_ID"));
				dto.setTrans_date(rs.getDate("TRANS_DATE"));
				dto.setCc_type(rs.getString("CC_TYPE"));
				dto.setPay_method(rs.getString("PAYMENT_CODE"));
				dto.setPay_amount(rs.getBigDecimal("PAYMENT_AMOUNT"));
				dto.setRef_no(rs.getString("PAYMENT_REF_NO"));
				dto.setHandled_by(rs.getString("LAST_UPD_BY"));

				return dto;
			}
		};

		try {
			logger.info("getMobCcsPaymentTransDTOList() @ MobCcsPaymentTransDTO: "
					+ getMobCcsPaymentTransDTOListSQL);

			itemList = simpleJdbcTemplate.query(
					getMobCcsPaymentTransDTOListSQL, mapper, startDate,
					endDate, paymentMethod, mrt);
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException in getMobCCSPaymentTransResultDTOList()");

			itemList = null;
		} catch (Exception e) {
			logger.info(
					"Exception caught in getMobCCSPaymentTransResultDTOList():",
					e);

			throw new DAOException(e.getMessage(), e);
		}
		return itemList;

	}

	private static String getMobCcsPaymentTransDTOByOrderIdSQL = "SELECT " +
			" ORDER_ID" +
			", TRANS_DATE" +
			", NVL(PAYMENT_AMOUNT, 0) PAYMENT_AMOUNT" +
			", PAY_MTD_TYPE" +
			", CC_NUM" +
			", CC_INST_SCHEDULE" +
			", APPROVE_CODE" +
			", BATCH_NUM" +
			", BANK_IN_DATE" +
			", EPAYLINK_TXN_ID" +
			" FROM bomweb_payment_trans" +
			" WHERE ORDER_ID = :orderId" +
			" AND LOB = 'MOB'" +
			" AND TRANS_STATUS = 'SETTLED'" +
			" ORDER BY TRANS_DATE";

	public List<MobCcsPaymentTransDTO> getMobCcsPaymentTransDTOByOrderId(String orderId) throws DAOException {
		logger.info("getMobCcsPaymentTransDTOByOrderId is called");
		List<MobCcsPaymentTransDTO> itemList = new ArrayList<MobCcsPaymentTransDTO>();
		try {
			logger.info("getMobCcsPaymentTransDTOByOrderId() @ MobCcsPaymentTransDTO: " + getMobCcsPaymentTransDTOByOrderIdSQL);
			
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("orderId", orderId);
			
			itemList = simpleJdbcTemplate.query(getMobCcsPaymentTransDTOByOrderIdSQL
					, new ParameterizedRowMapper<MobCcsPaymentTransDTO>() {
						public MobCcsPaymentTransDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
							MobCcsPaymentTransDTO dto = new MobCcsPaymentTransDTO();
							dto.setOrder_id(rs.getString("ORDER_ID"));
							dto.setTrans_date(rs.getDate("TRANS_DATE"));
							dto.setPay_amount(rs.getBigDecimal("PAYMENT_AMOUNT"));
							dto.setPay_method(rs.getString("PAY_MTD_TYPE"));
							dto.setCcNum(rs.getString("CC_NUM"));
							int ccInstSchedule = rs.getInt("CC_INST_SCHEDULE");
							if (!rs.wasNull()) {
								dto.setCcInstSchedule(ccInstSchedule);
							}
							dto.setApproveCode(rs.getString("APPROVE_CODE"));
							dto.setBatchNum(rs.getString("BATCH_NUM"));
							dto.setBankInDate(rs.getDate("BANK_IN_DATE"));
							
							dto.setEpaylinkTxnId(rs.getString("EPAYLINK_TXN_ID"));
							
							return dto;
						}
					}, params);
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException in getMobCcsPaymentTransDTOByOrderId()");
			itemList = null;
		} catch (Exception e) {
			logger.info("Exception caught in getMobCcsPaymentTransDTOByOrderId():", e);
			throw new DAOException(e.getMessage(), e);
		}
		return itemList;
	}
	
	/*
	 * Add by Herbert 20120315 - Find out the how many Payment Trans by Credit Card
	 */
	private static String getMobCcsPaymentTransCCCntByOrderIdSQL = "select count(bpt.order_id) CNT\n "
			+ "from BOMWEB_PAYMENT_TRANS bpt\n "
			+ "where pay_mtd_type in ('C' ,'I')\n "
			+ "and order_id = :orderId\n "
			+ "AND LOB = 'MOB'\n "
			+ "AND TRANS_STATUS = 'SETTLED' ";

	public int getMobCcsPaymentTransCCCntByOrderId(String orderId) throws DAOException {
		logger.info("getMobCcsPaymentTransCCCntByOrderId is called");
		List<String> itemList = new ArrayList<String>();
		int out = -1;
		try {
			logger.info("getMobCcsPaymentTransCCCntByOrderId() @ MobCcsPaymentTransDTO: " + getMobCcsPaymentTransCCCntByOrderIdSQL);
			
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("orderId", orderId);
			
			itemList = simpleJdbcTemplate.query(getMobCcsPaymentTransCCCntByOrderIdSQL
					, new ParameterizedRowMapper<String>() {
						public String mapRow(ResultSet rs, int rowNum) throws SQLException {
							String dto = rs.getString("CNT");
							return dto;
						}
					}, params);
			out = Integer.parseInt(itemList.get(0));
			logger.debug("CNT :" + out);
			
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException in getMobCcsPaymentTransCCCntByOrderId()");
		} catch (Exception e) {
			logger.info("Exception caught in getMobCcsPaymentTransCCCntByOrderId():", e);
			throw new DAOException(e.getMessage(), e);
		}
		return out;
	}
	
	public void insertPaymentTrans(MobCcsPaymentTransDTO dto)
			throws DAOException {

		logger.info("insertPaymentTrans() is called");
		String sql = "insert into bomweb_payment_trans     \n"
				+ "  (order_id,     \n" + " msisdn, \n" 
				+"   delivery_date, \n " + " pay_mtd_type, \n" 
				+ "	 cc_num, \n " + "cc_exp_date, \n"
				+ "  cc_issue_bank, \n" + "cc_inst_schedule, \n" 
				+ "  approve_code, \n" + " trans_status, \n" 
				+ "	 lob, \n" + "   trans_date,     \n"
				+ "   payment_amount,     \n" + "   create_by,     \n"
				+ "   create_date,     \n" + "   last_upd_by, batch_num)     \n"
				+ "	  values\n" + "  (?, ?, TO_DATE(?, 'DD/MM/YYYY'), ?, ?, ?, ?, ?, ?, ?, ?, sysdate, ?, ?, sysdate, ?, ?)     \n";

		try {
			simpleJdbcTemplate.update(sql, dto.getOrder_id(), dto.getMsisdn(), dto.getDeliveryDate(), dto.getPay_method(),
					dto.getCcNum(), dto.getCcExpDate(), dto.getCcIssueBank(), dto.getCcInstSchedule(),
					dto.getApproveCode(), "SETTLED", "MOB", dto.getPay_amount(), dto.getCreateBy(),
					dto.getLastUpdateBy(), dto.getBatchNum());
		} catch (Exception e) {
			logger.error("Exception caught in insertPaymentTrans()", e);
			throw new DAOException(e.getMessage(), e);
		}

	}
	
	private static String getOnlinePaymentTransDTOByOrderIdSQL = 
		"select T.ORDER_ID\n" +
		"      ,T.REFERENCE_NO\n" + 
		"      ,DECODE(T.STATUS, 'Y', 'Success', 'C', 'Cancel', 'E', 'Reject', T.STATUS) STATUS\n" + 
		"      ,T.PAY_AMOUNT\n" + 
		"      ,T.CARD_PAN\n" + 
		"      ,T.EXP_DATE\n" + 
		"      ,T.RET_CODE\n" + 
		"      ,DECODE(T.STATUS, 'Y', T.Approve_Code, 'N/A') AUTH_CODE\n" + 
		"      ,T.EPAYLINK_TXN_ID\n" + 
		"from W_ONLINE_PAYMENT_TXN T\n" + 
		"where T.ORDER_ID = :V_ORDER_ID";

	public List<OnlinePaymentTxn> getOnlinePaymentTransDTOByOrderId(
			String orderId) throws DAOException {
		logger.info("getMobCcsPaymentTransDTOByOrderId is called");
		List<OnlinePaymentTxn> itemList = new ArrayList<OnlinePaymentTxn>();
		try {
			logger.info("getOnlinePaymentTransDTOByOrderId() : "
					+ getOnlinePaymentTransDTOByOrderIdSQL);

			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("V_ORDER_ID", orderId);

			itemList = simpleJdbcTemplate.query(
					getOnlinePaymentTransDTOByOrderIdSQL,
					new ParameterizedRowMapper<OnlinePaymentTxn>() {
						public OnlinePaymentTxn mapRow(ResultSet rs, int rowNum)
								throws SQLException {
							OnlinePaymentTxn dto = new OnlinePaymentTxn();
							dto.setOrderId(rs.getString("ORDER_ID"));
							dto.setReferenceNo(rs.getString("REFERENCE_NO"));
							dto.setStatus(rs.getString("STATUS"));
							dto.setPayAmount(rs.getBigDecimal("PAY_AMOUNT"));
							dto.setCardPan(rs.getString("CARD_PAN"));
							dto.setExpDate(rs.getString("EXP_DATE"));
							dto.setRetCode(rs.getString("RET_CODE"));
							dto.setAuthCode(rs.getString("AUTH_CODE"));
							dto.setEpaylinkTxnId(rs.getString("EPAYLINK_TXN_ID"));
							return dto;
						}
					}, params);
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException in getOnlinePaymentTransDTOByOrderId()");
			itemList = null;
		} catch (Exception e) {
			logger.info(
					"Exception caught in getOnlinePaymentTransDTOByOrderId():",
					e);
			throw new DAOException(e.getMessage(), e);
		}
		return itemList;
	}

	public Double getPaidAmtStsDeliveryByOrderId(String orderId) {
		//Athena 20131111 online sales report
		logger.info("getPaidAmtStsDeliveryByOrderId is called");
		String sql ="SELECT SUM(T.Payment_Amount) " 
			+"FROM Bomweb_Payment_Trans T " 
			+"WHERE T.Order_Id  = :orderId " 
			+"AND t.trans_status='SETTLED'";
		Double paid= 0.0;
		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("orderId", orderId);
			paid=(double) simpleJdbcTemplate.queryForLong(sql, params);
			return paid;
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException in getPaidAmtStsDeliveryByOrderId()");
			return paid;
		} catch (Exception de) {
			logger.error("Exception caught in getPaidAmtStsDeliveryByOrderId()", de);
			throw new AppRuntimeException(de);
		}
	}

	public Double getTotalPaidAmtStsDeliveryByOrderId(String orderId) {
		//Athena 20131111 online sales report
		logger.info("getPaidAmtStsDeliveryByOrderId is called");
		String sql = "SELECT SUM(Ssi.Onetime_Amt) " 
				+"FROM Bomweb_St_Subscribed_Item Ssi " 
				+"WHERE ssi.order_id = :orderId ";
		Double totalPaid= 0.0;
		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("orderId", orderId);
			totalPaid=(double) simpleJdbcTemplate.queryForLong(sql, params);
			return totalPaid;
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException in getTotalPaidAmtStsDeliveryByOrderId()");
			return totalPaid;
		} catch (Exception de) {
			logger.error("Exception caught in getTotalPaidAmtStsDeliveryByOrderId()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	public Double getStsDeliveryChargeByOrderId(String orderId, String posItemCd) {
		logger.info("getStsDeliveryChargeByOrderId is called");
		String sql = "SELECT SUM(Ssi.Onetime_Amt) " 
				+"FROM Bomweb_St_Subscribed_Item Ssi " 
				+"WHERE ssi.order_id = :orderId and pos_item_cd = :posItemCd ";
		Double totalDeliveryAmount= 0.0;
		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("orderId", orderId);
			params.addValue("posItemCd", posItemCd);
			totalDeliveryAmount=(double) simpleJdbcTemplate.queryForLong(sql, params);
			return totalDeliveryAmount;
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException in getStsDeliveryChargeByOrderId()");
			return totalDeliveryAmount;
		} catch (Exception de) {
			logger.error("Exception caught in getStsDeliveryChargeByOrderId()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	private static String getOrderCouponInfoSQL = "SELECT order_id, " 
			+"  item_serial coupon_number, " 
			+"  (onetime_amt * -1) face_value " 
			+"FROM bomweb_st_subscribed_item " 
			+"WHERE order_id   = :orderId " 
			+"AND pos_item_cd IN " 
			+"  (SELECT code_id " 
			+"  FROM bomweb_code_lkup " 
			+"  WHERE code_type LIKE 'ST_COUPON_POS_ITEM_CD' " 
			+"  ) ";
	
	
	public MobCcsMagentoCouponDTO getOrderCouponInfo(String orderId) {
		logger.info("getOrderCouponInfo is called");
		
		List<MobCcsMagentoCouponDTO> itemList = new ArrayList<MobCcsMagentoCouponDTO>();
		
		ParameterizedRowMapper<MobCcsMagentoCouponDTO> mapper = new ParameterizedRowMapper<MobCcsMagentoCouponDTO>() {

			public MobCcsMagentoCouponDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				MobCcsMagentoCouponDTO dto = new MobCcsMagentoCouponDTO();
				dto.setOrderId(rs.getString("ORDER_ID"));
				dto.setCouponNum(rs.getString("COUPON_NUMBER"));
				dto.setFaceVal(rs.getDouble("FACE_VALUE"));
				
				return dto;
			}
		};
		
		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("orderId", orderId);
			itemList = simpleJdbcTemplate.query(getOrderCouponInfoSQL, mapper, params);
			
			if(!CollectionUtils.isEmpty(itemList)){
				
				return itemList.get(0);
			}
			else{ 
				return null;
			}
		}  catch (Exception de) {
			logger.error("Exception caught in getOrderCouponInfo()", de);
			throw new AppRuntimeException(de);
		}
	}
	
}
