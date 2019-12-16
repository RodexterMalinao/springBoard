package com.bomwebportal.mob.ccs.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.bomwebportal.dao.BaseDAO;
import com.bomwebportal.dto.AccountDTO;
import com.bomwebportal.dto.ComponentDTO;
import com.bomwebportal.dto.CustomerProfileDTO;
import com.bomwebportal.dto.MnpDTO;
import com.bomwebportal.dto.MobileSimInfoDTO;
import com.bomwebportal.dto.OrderDTO;
import com.bomwebportal.dto.PaymentDTO;
import com.bomwebportal.dto.SubscriberDTO;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.mob.ccs.dto.MobCcsMrtBaseDTO;
import com.bomwebportal.mob.ccs.dto.MobCcsMrtChinaDTO;
import com.bomwebportal.mob.ccs.dto.MobCcsMrtDTO;
import com.bomwebportal.mob.ccs.dto.MobCcsMrtMnpDTO;
import com.bomwebportal.util.BomWebPortalConstant;
import com.bomwebportal.util.Utility;
import com.pccw.bom.mob.schemas.ProductDTO;

public class MobCcsOrderHistoryDAO extends BaseDAO{

	protected final Log logger = LogFactory.getLog(getClass());
	
	public OrderDTO getOrder(String orderId, String seqNo) throws DAOException {
		logger.info("getOrder() is called");

		List<OrderDTO> orderList = new ArrayList<OrderDTO>();

		String SQL = " select ORDER_ID, \n" + " OCID, \n" + " SRC, \n"
				+ " ORDER_TYPE, \n" + " MSISDN, \n" + " MSISDNLVL, \n"
				+ " MNP_IND, \n" + " SHOP_CD, \n" + " BOM_CUST_NO, \n"
				+ " MOB_CUST_NO, \n" + " ACCT_NO, \n" + " SRV_REQ_DATE, \n"
				+ " AGREE_NUM, \n" + " APP_DATE, \n" + " SALES_TYPE, \n"
				+ " SALES_CD, \n" + " DEP_WAIVE, \n" + " ON_HOLD_IND, \n"
				+ " ON_HOLD_REA_CD, \n" + " IMEI, \n" + " WARR_START_DATE, \n"
				+ " WARR_PERIOD, \n" + " ORDER_STATUS, \n" + " CREATE_DATE, \n"
				+ " SALES_NAME, \n" + "AO_IND, \n" + "LAST_UPDATE_BY, lob \n"
				+ " , CHECK_POINT \n"
				+ " , REASON_CD \n"
				+ " from BOMWEB_ORDER_HIST \n"
				+ " where ORDER_ID=? \n" 
				+ " AND SEQ_NO =? ";

		ParameterizedRowMapper<OrderDTO> mapper = new ParameterizedRowMapper<OrderDTO>() {
			public OrderDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				OrderDTO dto = new OrderDTO();

				dto.setOrderId(rs.getString("ORDER_ID"));
				dto.setOcid(rs.getString("OCID"));
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
				dto.setDepositWaiveInd(rs.getString("DEP_WAIVE"));
				dto.setOnHoldInd(rs.getString("ON_HOLD_IND"));
				dto.setOnHoldReaCd(rs.getString("ON_HOLD_REA_CD"));
				dto.setImei(rs.getString("IMEI"));
				dto.setWarrantyStartDate(rs.getString("WARR_START_DATE"));
				dto.setWarrantPeriod(rs.getString("WARR_PERIOD"));
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
				return dto;
			}
		};

		try {
			//herbert 20111110 - remove useless SQL logger
			logger.info("getOrder() @ MobCcsOrderHistoryDAO: " );
			logger.debug("getOrder() @ MobCcsOrderHistoryDAO: " + SQL);

			orderList = simpleJdbcTemplate.query(SQL, mapper, orderId, seqNo);

		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException");

			orderList = null;
		} catch (Exception e) {
			logger.info("Exception caught in getOrder():", e);

			throw new DAOException(e.getMessage(), e);
		}

		return orderList.get(0);// only return the first one

	}
	

	public CustomerProfileDTO getCustomerProfile(String orderId, String seqNo)
			throws DAOException {
		logger.info("getCustomerProfile() is called");

		List<CustomerProfileDTO> customerList = new ArrayList<CustomerProfileDTO>();
		
			//edit sql add 20110610 for sms lang
			String SQL ="select c.ORDER_ID,\n" +
			"       c.FIRST_NAME,\n" + 
			"       c.LAST_NAME,\n" + 
			"       c.ID_DOC_TYPE,\n" + 
			"       c.ID_DOC_NUM,\n" + 
			"       c.DOB,\n" + 
			"       c.TITLE,\n" + 
			"       c.COMPANY_NAME,\n" + 
			"       c.IND_TYPE,\n" + 
			"       c.IND_SUB_TYPE,\n" + 
			"       c.NATIONALITY,\n" + 
			"       c.CUST_NO,\n" + 
			"       c.MOB_CUST_NO,\n" + 
			"       c.ADDR_PROOF_IND,\n" + 
			"       c.LOB,\n" + 
			"       c.SERVICE_NUM,\n" + 
			"       a.ADDR_USAGE,\n" + 
			"       a.AREA_CD,\n" + 
			"       a.DIST_NO,\n" + 
			"       a.SECT_CD,\n" + 
			"       a.STR_NAME,\n" + 
			"       a.HI_LOT_NO,\n" + 
			"       a.STR_CAT_CD,\n" + 
			"       a.BUILD_NO,\n" + 
			"       a.FOREIGN_ADDR_FLAG,\n" + 
			"       a.FLOOR_NO,\n" + 
			"       a.UNIT_NO,\n" + 
			"       a.PO_BOX_NO,\n" + 
			"       a.ADDR_TYPE,\n" + 
			"       a.STR_NO,\n" + 
			"       a.SECT_DEP_IND,\n" + 
			"       a.AREA_DESC,\n" + 
			"       a.DIST_DESC,\n" + 
			"       a.SECT_DESC,\n" + 
			"       a.STR_CAT_DESC,\n" +
			"		nvl(a.PH_IND, 'N') PH_IND,\n" + //add nvl by wilson 20120215
			"       ct.CONTACT_NAME,\n" + 
			"       ct.TITLE CONTACT_TITLE,\n" + 
			"       ct.CONTACT_PHONE,\n" + 
			"       ct.OTHER_PHONE,\n" +  //add by herbert 20110720
			"       ct.EMAIL_ADDR,\n" + 
			"       ct.ACTION_IND,\n" + 
			"       bs.sms_lang, C.DOMESTIC_HELPER_IND, c.BYPASS_IND\n" + 
			"  from BOMWEB_CUSTOMER_HIST  c,\n" + 
			"       BOMWEB_CUST_ADDR_HIST a,\n" + 
			"       BOMWEB_CONTACT_HIST   ct,\n" + 
			"       bomweb_sub_HIST       bs\n" + 
			" where c.order_id = a.order_id\n" + 
			"   and c.order_id = ct.order_id\n" + 
			"   and c.order_id = bs.order_id\n" + 
			"   and c.order_id = ?\n" + 
			"   and a.ADDR_USAGE = 'MA' \n " +
			"	and c.seq_no =? \n" +
			"	and c.seq_no = a.seq_no \n" +
			"	and c.seq_no = ct.seq_no \n" +
			"	and c.seq_no = bs.seq_no ";


		ParameterizedRowMapper<CustomerProfileDTO> mapper = new ParameterizedRowMapper<CustomerProfileDTO>() {
			public CustomerProfileDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				CustomerProfileDTO customer = new CustomerProfileDTO();

				customer.setCustFirstName(rs.getString("FIRST_NAME"));
				customer.setCustLastName(rs.getString("LAST_NAME"));
				customer.setIdDocType(rs.getString("ID_DOC_TYPE"));
				customer.setIdDocNum(rs.getString("ID_DOC_NUM"));
				customer.setDob(rs.getTimestamp("DOB"));
				customer.setTitle(rs.getString("TITLE"));
				customer.setCompanyName(rs.getString("COMPANY_NAME"));
				customer.setIndustryType(rs.getString("IND_TYPE"));
				customer.setIndustrySubType(rs.getString("IND_SUB_TYPE"));
				customer.setNationality(rs.getString("NATIONALITY"));
				customer.setCustomerNum(rs.getString("CUST_NO"));
				customer.setBomCustNum(rs.getString("MOB_CUST_NO"));
				/*
				 * if (rs.getString("STR_NAME")==null)
				 * System.out.println("null STR_NAME:"
				 * +rs.getString("STR_NAME")); if
				 * (rs.getString("STR_NAME")!=null)
				 * System.out.println("not null STR_NAME:"
				 * +rs.getString("STR_NAME"));
				 */
				// "MA"rs.getString("ADDR_USAGE"));//alway MA
				customer.setAreaCode(rs.getString("AREA_CD"));
				customer.setDistrictCode(rs.getString("DIST_NO"));
				customer.setSectionCode(rs.getString("SECT_CD"));
				customer.setStreetName(rs.getString("STR_NAME"));
				customer.setLotNum(rs.getString("HI_LOT_NO"));
				customer.setStreetCatgCode(rs.getString("STR_CAT_CD"));
				customer.setBuildingName(rs.getString("BUILD_NO"));

				// "N"rs.getString("FOREIGN_ADDR_FLAG"));
				customer.setFloor(rs.getString("FLOOR_NO"));
				customer.setFlat(rs.getString("UNIT_NO"));
				// ""rs.getString("PO_BOX_NO"));
				// "S"rs.getString("ADDR_TYPE"));
				customer.setStreetNum(rs.getString("STR_NO"));
				// "N"rs.getString("SECT_DEP_IND"));
				customer.setAreaDesc(rs.getString("area_desc"));
				customer.setDistrictDesc(rs.getString("dist_desc"));
				customer.setSectionDesc(rs.getString("sect_desc"));

				customer.setContactName(rs.getString("CONTACT_NAME"));
				// customer.setTitle(rs.getString("CONTACT_TITLE"));
				customer.setContactPhone(rs.getString("CONTACT_PHONE"));
				customer.setOtherContactPhone(rs.getString("OTHER_PHONE")); //add by herbert 20110720
				customer.setEmailAddr(rs.getString("EMAIL_ADDR"));
				// "A"rs.getString("ACTION_IND"));
				customer.setStreetCatgDesc(rs.getString("STR_CAT_DESC"));
				customer.setDobStr(Utility.date2String(rs.getTimestamp("DOB"),
						"dd/MM/yyyy"));

				customer.setAddrProofInd(rs.getString("ADDR_PROOF_IND"));// add
																			// by
																			// wilson
																			// 20110228
				customer.setLob(rs.getString("LOB"));// add by wilson 20110228
				customer.setServiceNum(rs.getString("SERVICE_NUM"));// add by
																	// wilson
																	// 20110228

				customer.setQuickSearch(customer.getAddress());// add by wilson
																// 20110301
				
				customer.setSmsLang(rs.getString("sms_lang")); //add 20110610 for sms lang
				customer.setPhInd(rs.getString("ph_ind"));
				customer.setForeignDomesticHelperInd("Y".equalsIgnoreCase(rs.getString("DOMESTIC_HELPER_IND")));//add by wilson 20120222
				customer.setByPassValidation("Y".equalsIgnoreCase(rs.getString("BYPASS_IND")));//add by wilson 20120301
				
				return customer;
			}
		};

		try {
			//herbert 20111110 - remove useless SQL logger
			logger.debug("getCustomerProfile() @ MobCcsOrderHistoryDAO: " + SQL);
			logger.info("getCustomerProfile() @ MobCcsOrderHistoryDAO: ");
			
			customerList = simpleJdbcTemplate.query(SQL, mapper, orderId, seqNo);

			CustomerProfileDTO customerBillingAddress = getCustomerProfileBillingAddress(orderId, seqNo);
			if (customerBillingAddress != null) {
				if (customerBillingAddress.isNoBillingAddressFlag() == false) {
					customerList.get(0).setNoBillingAddressFlag(false);

					customerList.get(0).setBillingAreaCode(
							customerBillingAddress.getBillingAreaCode());
					customerList.get(0).setBillingDistrictCode(
							customerBillingAddress.getBillingDistrictCode());
					customerList.get(0).setBillingSectionCode(
							customerBillingAddress.getBillingSectionCode());
					customerList.get(0).setBillingStreetName(
							customerBillingAddress.getBillingStreetName());
					customerList.get(0).setBillingLotNum(
							customerBillingAddress.getBillingLotNum());
					customerList.get(0).setBillingStreetCatgCode(
							customerBillingAddress.getBillingStreetCatgCode());
					customerList.get(0).setBillingBuildingName(
							customerBillingAddress.getBillingBuildingName());
					customerList.get(0).setBillingFloor(
							customerBillingAddress.getBillingFloor());
					customerList.get(0).setBillingFlat(
							customerBillingAddress.getBillingFlat());
					customerList.get(0).setBillingStreetNum(
							customerBillingAddress.getBillingStreetNum());
					customerList.get(0).setBillingAreaDesc(
							customerBillingAddress.getBillingAreaDesc());
					customerList.get(0).setBillingDistrictDesc(
							customerBillingAddress.getBillingDistrictDesc());
					customerList.get(0).setBillingSectionDesc(
							customerBillingAddress.getBillingSectionDesc());
					customerList.get(0).setBillingQuickSearch(
							customerBillingAddress.getBillingAddress());
					customerList.get(0).setBillingStreetCatgDesc(
							customerBillingAddress.getBillingStreetCatgDesc());//add 20110525
				}

			} else {

				customerList.get(0).setNoBillingAddressFlag(true);
				customerList.get(0).setBillingQuickSearch("");
				
				customerList.get(0).setBillingFlat(customerList.get(0).getFlat());//edit 20110527 copy address to billing address
				customerList.get(0).setBillingFloor(customerList.get(0).getFloor());//edit 20110527
				customerList.get(0).setBillingLotNum(customerList.get(0).getLotNum());//edit 20110527
				customerList.get(0).setBillingBuildingName(customerList.get(0).getBuildingName());//edit 20110527
				customerList.get(0).setBillingStreetNum(customerList.get(0).getStreetNum());//edit 20110527
				customerList.get(0).setBillingStreetName(customerList.get(0).getStreetName());//edit 20110527
				customerList.get(0).setBillingStreetCatgDesc(customerList.get(0).getStreetCatgDesc());//edit 20110527
				customerList.get(0).setBillingStreetCatgCode(customerList.get(0).getStreetCatgCode());//edit 20110527
				customerList.get(0).setBillingSectionDesc(customerList.get(0).getSectionDesc());//edit 20110527
				customerList.get(0).setBillingSectionCode(customerList.get(0).getSectionCode());//edit 20110527
				customerList.get(0).setBillingDistrictDesc(customerList.get(0).getDistrictDesc());//edit 20110527
				customerList.get(0).setBillingDistrictCode(customerList.get(0).getDistrictCode());//edit 20110527
				customerList.get(0).setBillingAreaDesc(customerList.get(0).getAreaDesc());//edit 20110527
				customerList.get(0).setBillingAreaCode(customerList.get(0).getAreaCode());//edit 20110527
				
				customerList.get(0).setBillingUnlinkSectionFlag(false);
				customerList.get(0).setBillingCustAddressFlag(false);
			}

		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException");

			customerList = null;
		} catch (Exception e) {
			logger.info("Exception caught in getCustomerProfile():", e);
			throw new DAOException(e.getMessage(), e);
		}
		return customerList.get(0);// only return the first one

	}
	
	public CustomerProfileDTO getCustomerProfileBillingAddress(String orderId, String seqNo)
			throws DAOException {
		logger.info("getCustomerProfileBillingAddress() is called");

		List<CustomerProfileDTO> customerList = new ArrayList<CustomerProfileDTO>();

		String SQL = " select A.ORDER_ID, \n" + " 	a.ADDR_USAGE, \n"
				+ " 	a.AREA_CD, \n" + " 	a.DIST_NO, \n" + " 	a.SECT_CD, \n"
				+ " 	a.STR_NAME , \n" + " 	a.HI_LOT_NO, \n"
				+ " 	a.STR_CAT_CD, \n" + " 	a.BUILD_NO, \n"
				+ " 	a.FOREIGN_ADDR_FLAG, \n" + " 	a.FLOOR_NO, \n"
				+ " 	a.UNIT_NO, \n" + " 	a.PO_BOX_NO, \n" + " 	a.ADDR_TYPE, \n"
				+ " 	a.STR_NO, \n" + " 	a.SECT_DEP_IND, \n"
				+ " 	a.AREA_DESC, \n" + " 	a.DIST_DESC, \n"
				+ " 	a.SECT_DESC, \n" + " 	a.STR_CAT_DESC \n"
				+ " from	 BOMWEB_CUST_ADDR_HIST a \n"
				+ "   where a.order_id=?  " 
				+ "		and a.ADDR_USAGE ='BA' \n" 
				+ "		and a.seq_no =? \n";

		ParameterizedRowMapper<CustomerProfileDTO> mapper = new ParameterizedRowMapper<CustomerProfileDTO>() {
			public CustomerProfileDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				CustomerProfileDTO customer = new CustomerProfileDTO();

				customer.setBillingAreaCode(rs.getString("AREA_CD"));
				customer.setBillingDistrictCode(rs.getString("DIST_NO"));
				customer.setBillingSectionCode(rs.getString("SECT_CD"));
				customer.setBillingStreetName(rs.getString("STR_NAME"));
				customer.setBillingLotNum(rs.getString("HI_LOT_NO"));
				customer.setBillingStreetCatgCode(rs.getString("STR_CAT_CD"));
				customer.setBillingBuildingName(rs.getString("BUILD_NO"));
				customer.setBillingFloor(rs.getString("FLOOR_NO"));
				customer.setBillingFlat(rs.getString("UNIT_NO"));
				customer.setBillingStreetNum(rs.getString("STR_NO"));
				customer.setBillingAreaDesc(rs.getString("area_desc"));
				customer.setBillingDistrictDesc(rs.getString("dist_desc"));
				customer.setBillingSectionDesc(rs.getString("sect_desc"));

				customer.setBillingStreetCatgDesc(rs.getString("STR_CAT_DESC")); //add 20110525

				customer.setBillingQuickSearch(customer.getBillingAddress());
				customer.setNoBillingAddressFlag(false);

				return customer;
			}
		};

		try {
			//herbert 20111110 - remove useless SQL logger
			logger.debug("getCustomerProfileBillingAddress() @ MobCcsOrderHistoryDAO: "	+ SQL);
			logger.info("getCustomerProfileBillingAddress() @ MobCcsOrderHistoryDAO: ");
			
			customerList = simpleJdbcTemplate.query(SQL, mapper, orderId,seqNo);
		

		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException");
			
			customerList = null;

		} catch (Exception e) {
			
			logger.info(
					"Exception caught in getCustomerProfileBillingAddress():",
					e);
			throw new DAOException(e.getMessage(), e);
		}
		
		if (customerList.size() == 0) {
			return null;
		} else if (customerList.size() > 0) {
			return customerList.get(0);// only return the first one
		}
		return null;

	}
	
	public MnpDTO getMnp(String orderId, String seqNo) throws DAOException {
		logger.info("getMnp() is called");

		List<MnpDTO> mnpList = new ArrayList<MnpDTO>();

		String SQL = " SELECT bm.ORDER_ID, \n" + " 	bm.CUT_OVER_DATE, \n"
				+ " 	bm.CUT_OVER_TIME, \n" + " 	bm.RNO, \n" + " 	bm.DNO, \n"
				+ " 	bm.CUST_NAME, \n" + " 	bm.DOC_NO, \n" + " 	bo.msisdn, \n"
				+ " 	bm.mnp_ticket_num, \n"
				+ " 	bo.mnp_ind, \n"
				+ " 	bo.msisdn, \n"
				+ " 	bo.shop_cd, \n" // add by wilson 20110308
				+ " 	bo.SRV_REQ_DATE \n"
				+ " FROM BOMWEB_MNP_HIST bm, BOMWEB_ORDER_HIST BO \n"
				+ " where bm.ORDER_ID=bO.ORDER_ID \n"
				+ " and bm.ORDER_ID=? \n" 
				+ " and bm.seq_no =? \n" 
				+ " and bm.seq_no =bO.seq_no ";

		ParameterizedRowMapper<MnpDTO> mapper = new ParameterizedRowMapper<MnpDTO>() {
			public MnpDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				MnpDTO mnp = new MnpDTO();

				mnp.setOrderId(rs.getString("ORDER_ID"));
				mnp.setRno(rs.getString("RNO"));
				mnp.setDno(rs.getString("DNO"));
				mnp.setCustName(rs.getString("CUST_NAME"));
				mnp.setCustIdDocNum(rs.getString("DOC_NO"));
				mnp.setMsisdn(rs.getString("msisdn"));
				mnp.setMnpTicketNum(rs.getString("mnp_ticket_num")); // add
																		// 20110225
				mnp.setMnp(rs.getString("mnp_ind")); // add 20110225

				if ("Y".equals(rs.getString("mnp_ind"))) {// add 20110228
					mnp.setMnpType("MNP");
					mnp.setMnpMsisdn(rs.getString("msisdn"));//
					mnp.setCutoverDate(rs.getTimestamp("CUT_OVER_DATE"));
					mnp.setCutoverTime(rs.getString("CUT_OVER_TIME"));
					mnp.setCutoverDateStr(Utility.date2String(
							rs.getTimestamp("CUT_OVER_DATE"), "dd/MM/yyyy"));

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
			//herbert 20111110 - remove useless SQL logger
			logger.debug("getMnp() @ MobCcsOrderHistoryDAO: " + SQL);
			logger.info("getMnp() @ MobCcsOrderHistoryDAO: ");
			mnpList = simpleJdbcTemplate.query(SQL, mapper, orderId,seqNo);

		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException");

			mnpList = null;
		} catch (Exception e) {
			logger.info("Exception caught in getMnp():", e);
			throw new DAOException(e.getMessage(), e);
		}
		return mnpList.get(0);// only return the first one

	}
	
	public PaymentDTO getPayment(String orderId, String seqNo) throws DAOException {
		logger.info("getPayment() is called");

		List<PaymentDTO> paymentList = new ArrayList<PaymentDTO>();

		String SQL = "SELECT p.ORDER_ID,\n"
				+ "       p.PAY_MTD_KEY,\n"
				+ "       p.PAY_MTD_TYPE,\n"
				+ "       p.THIRD_PARTY_IND,\n"
				+ "       p.CC_TYPE,\n"
				+ "       p.CC_NUM,\n"
				+ "       p.CC_HOLD_NAME,\n"
				+ "       p.CC_EXP_DATE,\n"
				+ "       p.CC_ISSUE_BANK,\n"
				+ "       (select bank_name from W_ISSUEBANKLKUP cb\n"
				+ "         where cb.bank_code = p.CC_ISSUE_BANK) CC_ISSUE_BANK_NAME,\n"
				+ "       p.CC_ID_DOC_TYPE,\n"
				+ "       p.CC_ID_DOC_NO,\n"
				+ "       p.B_ACCT_HOLD_ID_TYPE,\n"
				+ "       p.B_ACCT_HOLD_ID_NUM,\n"
				+ "			p.CC_VERIFIED_IND, \n" 
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
				+ "       p.CREATE_DATE,\n"
				+ "       p.BYPASS_IND\n" 
				+ "  FROM BOMWEB_PAYMENT_HIST p\n"
				+ " WHERE p.ORDER_ID = ? \n" 
				+ "  and p.seq_no =? ";

		ParameterizedRowMapper<PaymentDTO> mapper = new ParameterizedRowMapper<PaymentDTO>() {
			public PaymentDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				PaymentDTO dto = new PaymentDTO();

				dto.setOrderId(rs.getString("ORDER_ID"));
				dto.setPayMethodKey(rs.getString("PAY_MTD_KEY"));
				dto.setPayMethodType(rs.getString("PAY_MTD_TYPE"));
				dto.setThirdPartyInd(rs.getString("THIRD_PARTY_IND"));
				
				dto.setByPassValidation("Y".equalsIgnoreCase(rs.getString("BYPASS_IND")));//add by herbert 20120302

				if ("C".equals(rs.getString("PAY_MTD_TYPE"))) {
					dto.setCreditCardType(rs.getString("CC_TYPE"));
					dto.setCreditCardNum(rs.getString("CC_NUM"));
					dto.setCreditCardHolderName(rs.getString("CC_HOLD_NAME"));
					dto.setCreditExpiryDate(rs.getString("CC_EXP_DATE"));
					dto.setCreditCardIssueBankCd(rs.getString("CC_ISSUE_BANK"));
					dto.setCreditCardIssueBankName(rs
							.getString("CC_ISSUE_BANK_NAME")); // add by herbert
																// 20110721
					dto.setCreditCardVerifiedInd(rs.getString("CC_VERIFIED_IND"));
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
			//herbert 20111110 - remove useless SQL logger
			logger.info("getPayment() @ MobCcsOrderHistoryDAO: ");
			logger.debug("getPayment() @ MobCcsOrderHistoryDAO: " + SQL);
			paymentList = simpleJdbcTemplate.query(SQL, mapper, orderId,seqNo);

		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException");

			paymentList = null;
		} catch (Exception e) {
			logger.info("Exception caught in getPayment():", e);
			throw new DAOException(e.getMessage(), e);
		}
		return paymentList.get(0);// only return the first one

	}
	
	public MobileSimInfoDTO getSim(String orderId, String seqNo) throws DAOException {
		logger.info("getSim() is called");

		List<MobileSimInfoDTO> simList = new ArrayList<MobileSimInfoDTO>();

		String SQL = " SELECT bs.order_id, bs.iccid, bs.imsi, bs.puk1, bs.puk2, bs.item_code, \n"
				+ "        bs.create_date, bo.shop_cd , bo.SALES_CD, bo.SALES_TYPE, bo.imei, bo.AO_IND  \n"
				+ "   FROM bomweb_sim_hist bs, bomweb_order_hist bo \n"
				+ "  WHERE bs.order_id = bo.order_id " 
				+ "		AND bs.order_id = ? \n" 
				+ "		and bs.seq_no =? \n" 
				+ "		and bs.seq_no = bo.seq_no ";

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

				// add by herbert 20110707, for advance order use **start
				if (rs.getString("AO_IND") == null
						|| rs.getString("AO_IND").equalsIgnoreCase("N")) {
					dto.setAoInd(null);
				} else {
					dto.setAoInd("Y");
				}
				// add by herbert 20110707, for advance order use **end
				return dto;
			}
		};

		try {
			//herbert 20111110 - remove useless SQL logger
			logger.info("getSim() @ MobCcsOrderHistoryDAO: " );
			logger.debug("getSim() @ MobCcsOrderHistoryDAO: " + SQL);

			simList = simpleJdbcTemplate.query(SQL, mapper, orderId,seqNo);

		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException");

			simList = null;
		} catch (Exception e) {
			logger.info("Exception caught in getPayment():", e);

			throw new DAOException(e.getMessage(), e);
		}
		return simList.get(0);// only return the first one

	}
	
	public SubscriberDTO getBomWebSub(String orderId, String seqNo) throws DAOException {
		logger.info("getBomWebSub() is called");

		List<SubscriberDTO> subList = new ArrayList<SubscriberDTO>();
		String SQL = "select order_id,\n" 
				+ "                 sms_lang,\n"
				+ "                 ivrs_lang,\n"
				+ "                 ad_sup_ind,\n" 
				+ "                 pwd,\n"
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
				+ "                 create_date\n"
				+ "            from bomweb_sub_hist\n"
				+ "           where order_id = ? \n" 
				+"				and seq_no =? ";

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

				return dto;
			}
		};

		try {
			//herbert 20111110 - remove useless SQL logger
			logger.debug("getBomWebSub() @ MobCcsOrderHistoryDAO: " + SQL);
			logger.info("getBomWebSub() @ MobCcsOrderHistoryDAO: ");
			subList = simpleJdbcTemplate.query(SQL, mapper, orderId,seqNo);
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException");
			subList = null;
		} catch (Exception e) {
			logger.info("Exception caught in getBomWebSub():", e);
			throw new DAOException(e.getMessage(), e);
		}
		return subList.get(0);// only return the first one
	}

	public AccountDTO getAccount(String orderId,String seqNo) throws DAOException {
		logger.info("getAccount() is called");

		List<AccountDTO> orderList = new ArrayList<AccountDTO>();

		String SQL = " select  \n" + " ORDER_ID, \n" + " ACCT_NAME, \n"
				+ " BILL_FREQ, \n" + " BILL_LANG, \n" + " SMS_NO, \n"
				+ " EMAIL_ADDR, \n" + " BILL_PERIOD, \n" + " CREATE_DATE \n"
				+ " from BOMWEB_ACCT_HIST \n" 
				+ " where  ORDER_ID=? \n"
				+ "	 and seq_no =? ";

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

				return dto;
			}
		};

		try {
			//20111110 herbert remove all 
			logger.info("getAccount() @ MobCcsOrderHistoryDAO: " );
			logger.debug("getAccount() @ MobCcsOrderHistoryDAO: " + SQL);
			orderList = simpleJdbcTemplate.query(SQL, mapper, orderId,seqNo);

		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException");

			orderList = null;
		} catch (Exception e) {
			logger.info("Exception caught in getAccount():", e);

			throw new DAOException(e.getMessage(), e);
		}
		return orderList.get(0);// only return the first one

	}
	
	public List<ComponentDTO> getComponentList(String orderId,String seqNo)
			throws DAOException {
		logger.info("getComponentList() is called");

		List<ComponentDTO> componentList = new ArrayList<ComponentDTO>();

		String SQL = "SELECT ORDER_ID, ATTB_ID, ATTB_VALUE, CREATE_DATE " +
				"FROM BOMWEB_COMPONENT_HIST " +
				"WHERE ORDER_ID = ? " +
				"and seq_no =? ";

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
			//herbert 20111110 - remove useless SQL logger
			logger.info("getComponentList() @ MobCcsOrderHistoryDAO: ");
			logger.debug("getComponentList() @ MobCcsOrderHistoryDAO: " + SQL);
			componentList = simpleJdbcTemplate.query(SQL, mapper, orderId,seqNo);

		} catch (EmptyResultDataAccessException erdae) {
			logger.info("getComponentList() EmptyResultDataAccessException");
			return null;
		} catch (Exception e) {
			logger.info("Exception caught in getComponentList():", e);
			throw new DAOException(e.getMessage(), e);
		}
		return componentList;

	}

    public ArrayList<MobCcsMrtBaseDTO> getMobCcsMrtDTO(String orderId,String seqNo)
    	    throws DAOException {
    	logger.info(" getMobCcsMrtDTO is called");
    	ArrayList<MobCcsMrtBaseDTO> itemList = new ArrayList<MobCcsMrtBaseDTO>();
    	
    	String getMobCcsMrtDTOSQL = "SELECT \n" + "     ORDER_ID,\n"
        	    + "     MNP_IND,\n" + "     GOLDEN_IND,\n" + "     CHINA_IND,\n"
        	    + "     MSISDN,\n" + "     MSISDNLVL,\n" + "     SEQ_ID,\n"
        	    + "     CITY_CD,\n" + "     SRV_REQ_DATE,\n"
        	    + "     CUT_OVER_DATE,\n" + "     CUT_OVER_TIME,\n" + "     RNO,\n"
        	    + "     DNO,\n" + "     CUST_NAME,\n" + "     DOC_NO,\n"
        	    + "     MNP_TICKET_NUM,\n" + "     CREATE_BY,\n"
        	    + "     CREATE_DATE,\n" + "     RESERVE_ID,\n" + "     OPER_ID,\n"
        	    + "     LAST_UPD_BY,\n" + "     LAST_UPD_DATE, \n"
        	    + " BYPASS_IND \n"
        	    + " from BOMWEB_MRT_HIST " 
        	    + " Where ORDER_ID=? \n" 
        	    + "   and seq_no =? " 
        	    + " order by SEQ_ID\n";
    	
    	/**** ==ParameterizedRowMapper start== *********************************************************/
    	ParameterizedRowMapper<MobCcsMrtBaseDTO> mapper = new ParameterizedRowMapper<MobCcsMrtBaseDTO>() {
    	    public MobCcsMrtBaseDTO mapRow(ResultSet rs, int rowNum)
    		    throws SQLException {

	    	MobCcsMrtBaseDTO dto = null;
		    
		    if ("Y".equalsIgnoreCase(rs.getString("CHINA_IND")) && 2 == rs.getInt("SEQ_ID")) {
		    	dto = new MobCcsMrtChinaDTO();
		    } else if ("A".equalsIgnoreCase(rs.getString("MNP_IND")) && 2 == rs.getInt("SEQ_ID")) {
		    	dto = new MobCcsMrtMnpDTO();
		    } else if ("Y".equalsIgnoreCase(rs.getString("MNP_IND"))) {
		    	dto = new MobCcsMrtMnpDTO();
		    } else {
		    	dto = new MobCcsMrtDTO();
		    }
    	    	
    		dto.setOrderId(rs.getString("ORDER_ID"));
    		dto.setMnpInd(rs.getString("MNP_IND"));
    		dto.setGoldenInd(rs.getString("GOLDEN_IND"));
    		dto.setChinaInd(rs.getString("CHINA_IND"));
    		dto.setMsisdn(rs.getString("MSISDN"));
    		dto.setMsisdnLvl(rs.getString("MSISDNLVL"));
    		dto.setRno(rs.getString("RNO"));
    		dto.setDno(rs.getString("DNO"));
    		dto.setCreatedBy(rs.getString("CREATE_BY"));
    		dto.setCreatedDate(rs.getDate("CREATE_DATE"));
    		dto.setLastUpdBy(rs.getString("LAST_UPD_BY"));
    		dto.setSeqId(rs.getInt("SEQ_ID"));
    		dto.setReserveId(rs.getString("RESERVE_ID"));
    		dto.setOperId(rs.getString("OPER_ID"));
    		dto.setByPassValidation("Y".equalsIgnoreCase(rs.getString("BYPASS_IND")) ? true
    			: false);
    		
    		if (dto instanceof MobCcsMrtChinaDTO) {
    			((MobCcsMrtChinaDTO) dto).setCityCd(rs.getString("CITY_CD"));
    		} else if (dto instanceof MobCcsMrtMnpDTO) {
    			((MobCcsMrtMnpDTO) dto).setCutOverDate(rs.getDate("CUT_OVER_DATE"));
    			((MobCcsMrtMnpDTO) dto).setCutOverTime(rs.getString("CUT_OVER_TIME"));
    			((MobCcsMrtMnpDTO) dto).setCustName(rs.getString("CUST_NAME"));
    			((MobCcsMrtMnpDTO) dto).setDocNum(rs.getString("DOC_NO"));
    			((MobCcsMrtMnpDTO) dto).setMnpTicketNum(rs.getString("MNP_TICKET_NUM"));
    		} else if (dto instanceof MobCcsMrtDTO) {
    			((MobCcsMrtDTO) dto).setServiceReqDate(rs.getDate("SRV_REQ_DATE"));
    		}
    		return dto;
    	    }
    	};
    	/**** ==ParameterizedRowMapper end== *********************************************************/
    	try {
    	    logger.info("getMobCcsMrtDTO() @ MobCcsOrderHistoryDAO: "
    		    + getMobCcsMrtDTOSQL);
    	    itemList = (ArrayList<MobCcsMrtBaseDTO>) simpleJdbcTemplate.query(
    		    getMobCcsMrtDTOSQL, mapper, orderId,seqNo);
    	} catch (EmptyResultDataAccessException erdae) {
    	    logger.info("EmptyResultDataAccessException in MobCcsOrderHistoryDAO()");

    	    itemList = null;
    	} catch (Exception e) {
    	    logger.info("Exception caught in MobCcsOrderHistoryDAO():", e);

    	    throw new DAOException(e.getMessage(), e);
    	}
    	return itemList;
        }
    
	public List<ProductDTO> getBomProductList(String orderId, String seqNo)
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
				+ "          from bomweb_subscribed_item_HIST bsi\n"
				+ "         where bsi.order_id = ? and bsi.seq_no = ?)";

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

			productList = simpleJdbcTemplate.query(SQL, mapper, orderId,seqNo);

		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException");
			productList = null;
		} catch (Exception e) {
			logger.error("Exception caught in getBomProductList()", e);
			throw new DAOException(e.getMessage(), e);
		}
		return productList;

	}
	
	public void updateBomWebSim(MobileSimInfoDTO dto, String seqNo) throws DAOException {
		logger.info("updateBomWebSim is called");
		String SQL = "UPDATE BOMWEB_SIM_HIST SET " + 
						" ICCID = ?,\n" + 
						" IMSI = ?,\n" + 
						" PUK1 = ?,\n" +
						" PUK2 = ?,\n" + 
						" ITEM_CODE = ?,\n" + 
						" Last_UPD_DATE = sysdate\n"+ 
						" WHERE order_id = ? \n" +
						" AND seq_no = ? ";

		try {
			logger.info(dto.getIccid() + ":" + dto.getImsi()+ ":" +
					dto.getPuk1()+ ":" + dto.getPuk2()+ ":" +
					dto.getItemCd()+ ":" + dto.getOrderId()+":"+seqNo);
			simpleJdbcTemplate.update(SQL,dto.getIccid(),// v_iccid,
					dto.getImsi(),// v_imsi,
					dto.getPuk1(),// v_puk1,
					dto.getPuk2(),// v_puk2,
					dto.getItemCd(),// v_item_code,
					dto.getOrderId(),// v_order_id
					seqNo);

		} catch (Exception e) {
			logger.error("Exception caught in updateBomWebSim()", e);
			throw new DAOException(e.getMessage(), e);
		}

	}
	public String getMultiIMEI(String orderId,String seqNo)throws DAOException {
		logger.info("getMultiIMEI is called");
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		
		StringBuffer sql =  new StringBuffer("select sa.item_code, sa.item_serial\n " 
							+ "from bomweb_stock_assgn sa, \n"
							+ "BOMWEB_SUBSCRIBED_ITEM_HIST SI, \n"
							+ "W_ITEM_PRODUCT_POS_ASSGN IPPA \n"
							+ "where sa.order_id = SI.ORDER_ID \n"
							+ "and sa.item_code = ippa.pos_item_cd \n"
							+ "and SI.ID = IPPA.ITEM_ID \n"
							+ "and sa.status_id = '19'  \n"
							+ "and si.type != 'SIM' \n");
				
		sql.append(" and si.order_id = upper (trim(:orderId)) "); //add trim 20120307
		orderId = orderId.replace(" ", "");
		orderId = orderId.trim().toUpperCase();
		params.addValue("orderId", orderId);
		
		sql.append(" and si.seq_no = trim(:seqNo) "); 
		seqNo = seqNo.replace(" ", "");
		seqNo = seqNo.trim();
		params.addValue("seqNo", seqNo);

		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {

			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				
				String dto;
				
				//the pattern should be: <item cd 1>/<imei1>;
				dto = rs.getString("item_code") + "/" 
					+ rs.getString("item_serial")+ ";" ;
				
				return dto;
			}
		};
		
		try {
			List<String> dto = simpleJdbcTemplate.query(sql.toString(), mapper, params);
			
			//the pattern should be: <item cd 1>/<imei1>;<item cd 2>/<imei2>;
			String out = "";	
			for  (int i = 0; i < dto.size(); i++) {
				out = out + dto.get(i);
			}
			logger.debug("getMultiIMEI out:" + out);
			return out;
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException");
		} catch (Exception e) {
			logger.error("Exception caught in getMultiIMEI()", e);
			throw new DAOException(e.getMessage(), e);
		}
		
		return null;
		
	}
	
	// add LOB = 'MOB' by Herbert 20120424
	public String getAutoProcessHistOrderId() throws DAOException {

		String orderId = null;
		logger.info("getAutoProcessHistOrderId is called ");

		String SQL = "select ORDER_ID " +
					"from (select O.ORDER_ID, "+
					" O.ORDER_STATUS, "+
					" O.SRV_REQ_DATE " +
					"from BOMWEB_ORDER O "+
					"where ORDER_STATUS = '"+ BomWebPortalConstant.BWP_MOBCCS_ORDER_CANCELLING + "'  "+
					"and O.CHECK_POINT = '"+ BomWebPortalConstant.BWP_MOBCCS_HIST_CHECK_POINT_PENDING+ "'  "+
					"and OCID is null "+
					"and O.LOB = 'MOB' "+
					"order by O.SRV_REQ_DATE) "+
					"where ROWNUM = 1";
		try {
			orderId = (String) simpleJdbcTemplate.queryForObject(SQL,
					String.class);

		} catch (EmptyResultDataAccessException erdae) {
			orderId = null;
		} catch (Exception e) {
			logger.error("Exception caught in getAutoProcessHistOrderId()", e);
			throw new DAOException(e.getMessage(), e);
		}
		return orderId;
	}
	
	public String getAutoProcessHistOrderSeqNo(String orderId) throws DAOException {

		String seqNo = null;
		logger.info("getAutoProcessHistOrderSeqNo is called ");

		String SQL = "select Hist_SEQ_NO " +
					"from (select Hist_SEQ_NO, O.ORDER_ID, "+
					" O.ORDER_STATUS, "+
					" O.SRV_REQ_DATE " +
					"from BOMWEB_ORDER O "+
					"where ORDER_STATUS = '"+ BomWebPortalConstant.BWP_MOBCCS_ORDER_CANCELLING + "'  "+
					"and O.CHECK_POINT = '"+ BomWebPortalConstant.BWP_MOBCCS_HIST_CHECK_POINT_PENDING+ "'  "+
					"and OCID is null "+
					"order by O.SRV_REQ_DATE) "+
					"where ORDER_ID = ?";
		try {
			seqNo = (String) simpleJdbcTemplate.queryForObject(SQL,
					String.class,orderId);

		} catch (EmptyResultDataAccessException erdae) {
			seqNo = null;
		} catch (Exception e) {
			logger.error("Exception caught in getAutoProcessHistOrderSeqNo()", e);
			throw new DAOException(e.getMessage(), e);
		}
		return seqNo;
	}
	
	public List<ComponentDTO> getPassToBomComponentList(String orderId, String seqNo)
			throws DAOException {
		logger.info("getPassToBomComponentList() is called");

		List<ComponentDTO> componentList = new ArrayList<ComponentDTO>();

		/*String SQL = "SELECT ORDER_ID, ATTB_ID, ATTB_VALUE, CREATE_DATE "
				+ "FROM BOMWEB_COMPONENT_HIST " + "WHERE ORDER_ID = ? "
				+ "and seq_no =? ";*/
		String SQL =
			"select C.ORDER_ID, C.ATTB_ID, C.ATTB_VALUE, C.CREATE_DATE\n" +
			"  from BOMWEB_COMPONENT_HIST C\n" + 
			" where C.ORDER_ID = ?\n" + 
			"   and C.SEQ_NO = ?\n" + 
			"   and C.ATTB_ID not in (select IA.ATTB_ID from W_ITEM_ATTB_ASSGN IA)";


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
			logger.info("getPassToBomComponentList() @ MobCcsOrderHistoryDAO: ");
			logger.debug("getPassToBomComponentList() @ MobCcsOrderHistoryDAO: " + SQL);
			componentList = simpleJdbcTemplate.query(SQL, mapper, orderId,
					seqNo);

		} catch (EmptyResultDataAccessException erdae) {
			logger.info("getPassToBomComponentList() EmptyResultDataAccessException");
			return null;
		} catch (Exception e) {
			logger.info("Exception caught in getPassToBomComponentList():", e);
			throw new DAOException(e.getMessage(), e);
		}
		return componentList;

	}
	
}

