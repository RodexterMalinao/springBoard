package com.bomwebportal.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import com.bomwebportal.dto.ActualUserDTO;
import com.bomwebportal.dto.CustomerProfileDTO;
import com.bomwebportal.dto.MobBillMediaDTO;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.util.Utility;

public class CustomerProfileDAO extends BaseDAO {

	protected final Log logger = LogFactory.getLog(getClass());
	public void insertBomOrderContact(CustomerProfileDTO dto)
			throws DAOException {

		logger.debug("insertBomOrderContact() is called");
		logger.info("dto.getOrderId():" + dto.getOrderId());
		insertBomWebCustomer(dto);
		if (StringUtils.isNotEmpty(dto.getSameAsCustInd()) && !dto.isSameAsCust()){
			insertBomWebMobActualUser(dto.getActualUserDTO());
		}
		insertBomWebContact(dto);
		insertBomWebCustomerAddress(dto);
		if (dto.isNoBillingAddressFlag() == false) { // add 20110517
			insertBomWebCustomerBillingAddress(dto);
		}
		logger.debug("insertBomOrderContact() is finish");
	}

	public void deleteBomOrderContact(String orderId) throws DAOException {

		logger.debug("deleteBomOrderContact() is called");
		deleteBomWebCustomer(orderId);
		deleteBomWebActualUser(orderId);
		deleteBomWebContact(orderId);
		deleteBomWebCustomerAddress(orderId);
		logger.debug("deleteBomOrderContact() is finish");
	}

	private void insertBomWebCustomer(CustomerProfileDTO dto)
			throws DAOException {
		logger.debug("insertBomWebCustomer() is called");

		// define SQL string
		String SQL = "insert into bomweb_customer\n"
				+ "  (ORDER_ID,\n"
				+ "   FIRST_NAME,\n"
				+ "   LAST_NAME,\n"
				+ "   ID_DOC_TYPE,\n"
				+ "   ID_DOC_NUM,\n"
				+ "   DOB,\n"
				+ "   TITLE,\n"
				+ "   COMPANY_NAME,\n"
				+ "   IND_TYPE,\n"
				+ "   IND_SUB_TYPE,\n"
				+ "   NATIONALITY,\n"
				+ "   CUST_NO,\n"
				+ "   MOB_CUST_NO,\n"
				+ "   ADDR_PROOF_IND,\n"
				+ "   LOB,\n"
				+ "   service_num,\n"
				+ "   CREATE_DATE, DOMESTIC_HELPER_IND, BYPASS_IND, CS_PORTAL_IND, CS_PORTAL_STATUS, DS_MISS_DOC,\n"
				+ "   AUTH_ID_DOC_TYPE, AUTH_ID_DOC_NUM, COMPANY_DOC,\n"
				+ "   BOM_CUST_ADDR_OVERRIDE_IND,\n"
				+ "   DUMMY_EMAIL, HKT_OPT_OUT, CLUB_OPT_OUT, CLUB_OPT_REA, CLUB_OPT_RMK, STUDENT_PLAN_SUB_IND, THE_CLUB_LOGIN, CLUB_MEM_ID)\n"
				+ "values\n"
				+ "  (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,sysdate,?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

		try {
			if(dto.isHktClubOptOut()){
				dto.setHktOptOut(true);
				dto.setClubOptOut(true);
			}
			simpleJdbcTemplate.update(SQL, dto.getOrderId(),
					dto.getCustFirstName(), dto.getCustLastName(),
					dto.getIdDocType(), dto.getIdDocNum(), dto.getDob(),
					dto.getTitle(), dto.getCompanyName(),
					dto.getIndustryType(), dto.getIndustrySubType(),
					dto.getNationality(), dto.getCustomerNum(),
					dto.getBomCustNum(), dto.getAddrProofInd(), dto.getLob(),
					dto.getServiceNum(),
					dto.isForeignDomesticHelperInd() == true ? "Y" : "N",
					dto.isByPassValidation() == true ? "Y" : "N",
					dto.getCsPortalInd(),
					dto.getCsPortalStatus(),
					dto.getDsMissDoc(),
					dto.getRepIdDocType(), dto.getRepIdDocNum(), dto.getCompanyDoc(),
					dto.getBomCustAddrOverrideInd(),
					dto.getDummyEmail(), (dto.isHktOptOut() || dto.isHktClubOptOut()) ? "Y":"N", (dto.isClubOptOut() || dto.isHktClubOptOut()) ? "Y":"N", dto.getClubOptRea(), dto.getClubOptRmk(), (dto.isStudentPlanSubInd()) ? "Y":"N",
					dto.getTheClubLogin(), dto.getClubMemberId());
		} catch (Exception e) {
			logger.error("Exception caught in insertBomWebCustomer()", e);
			throw new DAOException(e.getMessage(), e);
		}

	}

	private void insertBomWebCustomerAddress(CustomerProfileDTO dto)
			throws DAOException {
		logger.debug("insertBomWebCustomerAddress() is called");

		// define SQL string
		String SQL = "insert into BOMWEB_CUST_ADDR\n"
				+ "  (ORDER_ID,\n"
				+ "   ADDR_USAGE,\n"
				+ "   AREA_CD,\n"
				+ "   DIST_NO,\n"
				+ "   SECT_CD,\n"
				+ "   STR_NAME,\n"
				+ "   HI_LOT_NO,\n"
				+ "   STR_CAT_CD,\n"
				+ "   BUILD_NO,\n"
				+ "   FOREIGN_ADDR_FLAG,\n"
				+ "   FLOOR_NO,\n"
				+ "   UNIT_NO,\n"
				+ "   PO_BOX_NO,\n"
				+ "   ADDR_TYPE,\n"
				+ "   STR_NO,\n"
				+ "   SECT_DEP_IND,\n"
				+ "   area_desc,\n"
				+ "   dist_desc,\n"
				+ "   sect_desc,\n"
				+ "   STR_CAT_DESC,\n"
				+ "   CREATE_DATE,\n"
				+ "   PH_IND)\n " 
				+ "values\n"
				+ "  (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?,?,?, sysdate, ?)";

		// insert to table
		try {
			simpleJdbcTemplate.update(SQL, dto.getOrderId(), "MA",
					dto.getAreaCode(), dto.getDistrictCode(),
					dto.getSectionCode(), dto.getStreetName(), dto.getLotNum(),
					dto.getStreetCatgCode(), dto.getBuildingName(), "N",
					dto.getFloor(), dto.getFlat(), "", "S",
					dto.getStreetNum(), "N", dto.getAreaDesc(),
					dto.getDistrictDesc(), dto.getSectionDesc(),
					dto.getStreetCatgDesc(),// add by wilson 20110224
					dto.getPhInd()
					);

		} catch (Exception e) {
			logger.error("Exception caught in insertBomWebCustomerAddress()", e);
			throw new DAOException(e.getMessage(), e);
		}

	}

	private void insertBomWebContact(CustomerProfileDTO dto)
			throws DAOException {
		logger.debug("insertBomWebContact() is called");

		// define SQL
		String SQL = "insert into BOMWEB_CONTACT\n" + "  (ORDER_ID,\n"
				+ "   CONTACT_NAME,\n" + "   TITLE,\n" + "   CONTACT_PHONE,\n"
				+ " OTHER_PHONE,\n" //add by Herbert 20110720
				+ "   EMAIL_ADDR,\n" + "   ACTION_IND,\n" + "   CREATE_DATE, contact_type)\n"
				+ "values\n" + "  (?, ?, ?, ?, ?, ?, ?, sysdate, 'CC')";

		// insert to table
		try {
			simpleJdbcTemplate.update(SQL, dto.getOrderId(),
					dto.getContactName(), dto.getTitle(),
					dto.getContactPhone(),
					dto.getOtherContactPhone(), //add by Herbert 20110720
					dto.getEmailAddr(), "A");

		} catch (Exception e) {
			logger.error("Exception caught in insertBomWebContact()", e);
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

	private void deleteBomWebCustomer(String orderId) throws DAOException {
		logger.debug("deleteBomWebCustomer() is called");

		String SQL = "delete bomweb_customer where order_id= ?";

		try {
			simpleJdbcTemplate.update(SQL, orderId);
		} catch (Exception e) {
			logger.error("Exception caught in deleteBomWebCustomer()", e);
			throw new DAOException(e.getMessage(), e);
		}

	}

	private void deleteBomWebActualUser(String orderId) throws DAOException {
		logger.debug("deleteBomWebActualUser() is called");

		String SQL = "delete bomweb_mob_actual_user where order_id= ?";

		try {
			simpleJdbcTemplate.update(SQL, orderId);
		} catch (Exception e) {
			logger.error("Exception caught in deleteBomWebActualUser()", e);
			throw new DAOException(e.getMessage(), e);
		}

	}
	

	private void deleteBomWebContact(String orderId) throws DAOException {
		logger.debug("deleteBomWebContact() is called");

		String SQL = "delete BOMWEB_CONTACT where order_id= ?";

		try {
			simpleJdbcTemplate.update(SQL, orderId);
		} catch (Exception e) {
			logger.error("Exception caught in deleteBomWebContact()", e);
			throw new DAOException(e.getMessage(), e);
		}

	}

	private void deleteBomWebCustomerAddress(String orderId)
			throws DAOException {
		logger.debug("deleteBomWebCustomerAddress() is called");

		String SQL = "delete BOMWEB_CUST_ADDR where order_id= ?";
		try {
			simpleJdbcTemplate.update(SQL, orderId);
		} catch (Exception e) {
			logger.error("Exception caught in deleteBomWebCustomerAddress()", e);
			throw new DAOException(e.getMessage(), e);
		}

	}

	public CustomerProfileDTO getCustomerProfile(String orderId) throws DAOException {
		logger.debug("getCustomerProfile() is called");
		
		List<CustomerProfileDTO> customerList = new ArrayList<CustomerProfileDTO>();
		
		String SQL =
				"select C.ORDER_ID\n" +
				"      ,C.FIRST_NAME\n" + 
				"      ,C.LAST_NAME\n" + 
				"      ,C.ID_DOC_TYPE\n" + 
				"      ,C.ID_DOC_NUM\n" + 
				"      ,C.DOB\n" + 
				"      ,C.TITLE\n" + 
				"      ,C.COMPANY_NAME\n" + 
				"      ,C.AUTH_ID_DOC_TYPE\n" + 
				"      ,C.AUTH_ID_DOC_NUM\n" + 
				"      ,C.COMPANY_DOC\n" + 
				"      ,C.IND_TYPE\n" + 
				"      ,C.IND_SUB_TYPE\n" + 
				"      ,C.NATIONALITY\n" + 
				"      ,C.CUST_NO\n" + 
				"      ,C.MOB_CUST_NO\n" + 
				"      ,C.ADDR_PROOF_IND\n" + 
				"      ,C.LOB\n" + 
				"      ,C.SERVICE_NUM\n" +
				"      ,C.CLUB_OPT_RMK\n" + 
				"      ,C.CLUB_OPT_REA\n" + 
				"      ,C.CLUB_OPT_OUT\n" + 
				"      ,C.HKT_OPT_OUT\n" +
				"      ,C.DUMMY_EMAIL\n" + 
				"      ,C.THE_CLUB_LOGIN\n" + 
				"      ,A.ADDR_USAGE\n" + 
				"      ,A.AREA_CD\n" + 
				"      ,A.DIST_NO\n" + 
				"      ,A.SECT_CD\n" + 
				"      ,A.STR_NAME\n" + 
				"      ,A.HI_LOT_NO\n" + 
				"      ,A.STR_CAT_CD\n" + 
				"      ,A.BUILD_NO\n" + 
				"      ,A.FOREIGN_ADDR_FLAG\n" + 
				"      ,A.FLOOR_NO\n" + 
				"      ,A.UNIT_NO\n" + 
				"      ,A.PO_BOX_NO\n" + 
				"      ,A.ADDR_TYPE\n" + 
				"      ,A.STR_NO\n" + 
				"      ,A.SECT_DEP_IND\n" + 
				"      ,A.AREA_DESC\n" + 
				"      ,A.DIST_DESC\n" + 
				"      ,A.SECT_DESC\n" + 
				"      ,A.STR_CAT_DESC\n" + 
				"      ,NVL(A.PH_IND, 'N') PH_IND\n" + 
				"      ,CT.CONTACT_NAME\n" + 
				"      ,CT.TITLE CONTACT_TITLE\n" + 
				"      ,CT.CONTACT_PHONE\n" + 
				"      ,CT.OTHER_PHONE\n" + 
				"      ,CT.EMAIL_ADDR\n" + 
				"      ,CT.ACTION_IND\n" + 
				"      ,BS.SMS_LANG\n" + 
				"      ,C.DOMESTIC_HELPER_IND\n" + 
				"      ,C.BYPASS_IND\n" + 
				"      ,ACCT.BILL_LANG\n" + 
				"      ,ACCT.BILL_PERIOD\n" + 
				"      ,BS.PRIVACY_IND\n" + 
				"      ,BS.PRIVACY_STAMP_DATE\n" + 
				"      ,C.CS_PORTAL_IND\n" +
				"      ,C.CS_PORTAL_STATUS\n" +
				"      ,BS.SUPPRESS_CUST_LOCAL_TOPUP\n" +
				"      ,BS.SUPPRESS_CUST_ROAM_TOPUP\n" +
				"      ,C.DS_MISS_DOC\n" + 
				"      ,BS.MOB0060_OPT_OUT_IND\n" + 
				"      ,C.BOM_CUST_ADDR_OVERRIDE_IND\n" +
				"      ,BS.ACTIVATION_CD\n" +
				"      ,ACCT.BRAND\n" +
				"      ,C.STUDENT_PLAN_SUB_IND\n" +
				"      ,ACCT.SAME_AS_CUST_IND\n" +
				"      ,SI.SIM_TYPE\n" +
				"      ,BS.PCRF_ALERT_EMAIL\n" +
				"      ,BS.SAME_AS_EBILL_ADDR_IND\n" +
				"      ,BS.SMS_OPT_OUT_FIRST_ROAM\n" +
				"      ,BS.SMS_OPT_OUT_ROAM_HU\n" +
				"      ,BS.PCRF_MUP_ALERT\n" +
				"      ,BS.PCRF_SMS_NUM\n" +
				"      ,BS.PCRF_SMS_RECIPIENT\n" +
				"      ,BS.PCRF_ALERT_TYPE\n" +
				"      ,BS.SEC_SRV_NUM\n" +
				"      ,ACCT.IS_NEW\n" +
				"      ,ACCT.ACCT_NO\n" +
				"      ,ACCT.ACCT_NAME\n" +
				"      ,ACCT.EXIST_ACTIVE_MOBILE_NO\n" +
				"from BOMWEB_CUSTOMER  C\n" + 
				"    ,BOMWEB_CUST_ADDR A\n" + 
				"    ,BOMWEB_CONTACT   CT\n" + 
				"    ,BOMWEB_SUB       BS\n" + 
				"    ,BOMWEB_ACCT      ACCT\n" +
				"    ,BOMWEB_SIM       SI\n" +  
				"where C.ORDER_ID = A.ORDER_ID\n" + 
				"and C.ORDER_ID = CT.ORDER_ID\n" + 
				"and C.ORDER_ID = BS.ORDER_ID\n" + 
				"and C.ORDER_ID = ACCT.ORDER_ID\n" + 
				"and C.ORDER_ID = SI.ORDER_ID(+)\n" + 
				"and C.ORDER_ID = ?\n" + 
				"and A.ADDR_USAGE = 'MA'\n" + 
				"and CT.CONTACT_TYPE = 'CC'";
	
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
				customer.setRepIdDocType(rs.getString("AUTH_ID_DOC_TYPE"));
				customer.setRepIdDocNum(rs.getString("AUTH_ID_DOC_NUM"));
				customer.setCompanyDoc(rs.getString("COMPANY_DOC"));
				customer.setIndustryType(rs.getString("IND_TYPE"));
				customer.setIndustrySubType(rs.getString("IND_SUB_TYPE"));
				customer.setNationality(rs.getString("NATIONALITY"));
				customer.setCustomerNum(rs.getString("CUST_NO"));
				customer.setBomCustNum(rs.getString("MOB_CUST_NO"));
				customer.setAreaCode(rs.getString("AREA_CD"));
				customer.setDistrictCode(rs.getString("DIST_NO"));
				customer.setSectionCode(rs.getString("SECT_CD"));
				customer.setStreetName(rs.getString("STR_NAME"));
				customer.setLotNum(rs.getString("HI_LOT_NO"));
				customer.setStreetCatgCode(rs.getString("STR_CAT_CD"));
				customer.setBuildingName(rs.getString("BUILD_NO"));
				customer.setFloor(rs.getString("FLOOR_NO"));
				customer.setFlat(rs.getString("UNIT_NO"));
				customer.setStreetNum(rs.getString("STR_NO"));
				customer.setAreaDesc(rs.getString("area_desc"));
				customer.setDistrictDesc(rs.getString("dist_desc"));
				customer.setSectionDesc(rs.getString("sect_desc"));
				customer.setContactName(rs.getString("CONTACT_NAME"));
				customer.setContactPhone(rs.getString("CONTACT_PHONE"));
				customer.setOtherContactPhone(rs.getString("OTHER_PHONE")); 
				customer.setEmailAddr(rs.getString("EMAIL_ADDR"));
				customer.setStreetCatgDesc(rs.getString("STR_CAT_DESC"));
				customer.setDobStr(Utility.date2String(rs.getTimestamp("DOB"),"dd/MM/yyyy"));
				customer.setAddrProofInd(rs.getString("ADDR_PROOF_IND"));
				customer.setLob(rs.getString("LOB"));
				customer.setServiceNum(rs.getString("SERVICE_NUM"));
				customer.setQuickSearch(customer.getAddress());
				customer.setSmsLang(rs.getString("sms_lang"));
				customer.setPhInd(rs.getString("ph_ind"));
				customer.setForeignDomesticHelperInd("Y".equalsIgnoreCase(rs.getString("DOMESTIC_HELPER_IND")));
				customer.setByPassValidation("Y".equalsIgnoreCase(rs.getString("BYPASS_IND")));
				customer.setBillLang(rs.getString("BILL_LANG"));
				customer.setPrivacyInd("Y".equalsIgnoreCase(rs.getString("PRIVACY_IND")));
				customer.setPrivacyStampDate(rs.getTimestamp("PRIVACY_STAMP_DATE"));
				customer.setCsPortalBool("Y".equalsIgnoreCase(rs.getString("CS_PORTAL_IND")) || "D".equalsIgnoreCase(rs.getString("CS_PORTAL_IND")));
				customer.setCsPortalInd(rs.getString("CS_PORTAL_IND"));
				customer.setCsPortalStatus(rs.getString("CS_PORTAL_STATUS"));
				customer.setSuppressLocalTopUpInd("Y".equalsIgnoreCase(rs.getString("SUPPRESS_CUST_LOCAL_TOPUP")));
				customer.setSuppressRoamTopUpInd("Y".equalsIgnoreCase(rs.getString("SUPPRESS_CUST_ROAM_TOPUP")));
				customer.setDsMissDoc(rs.getString("DS_MISS_DOC"));
				customer.setMob0060OptOutInd("Y".equalsIgnoreCase(rs.getString("MOB0060_OPT_OUT_IND")));
				customer.setBomCustAddrOverrideInd(rs.getString("BOM_CUST_ADDR_OVERRIDE_IND"));
				customer.setActivationCd(rs.getString("ACTIVATION_CD"));
				customer.setBrand(rs.getString("BRAND"));				
				customer.setDummyEmail(rs.getString("DUMMY_EMAIL"));
				customer.setTheClubLogin(rs.getString("THE_CLUB_LOGIN"));
				customer.setHktOptOut("Y".equalsIgnoreCase(rs.getString("HKT_OPT_OUT")));
				customer.setClubOptOut("Y".equalsIgnoreCase(rs.getString("CLUB_OPT_OUT")));
				customer.setHktClubOptOut("Y".equalsIgnoreCase(rs.getString("CLUB_OPT_OUT")) && "Y".equalsIgnoreCase(rs.getString("HKT_OPT_OUT")));
				customer.setClubOptRea(rs.getString("CLUB_OPT_REA"));
				customer.setClubOptRmk(rs.getString("CLUB_OPT_RMK"));
				customer.setStudentPlanSubInd("Y".equalsIgnoreCase(rs.getString("STUDENT_PLAN_SUB_IND")));
				customer.setSameAsCustInd(rs.getString("SAME_AS_CUST_IND"));
				customer.setSimType(rs.getString("SIM_TYPE"));
				customer.setNumType(rs.getString("SIM_TYPE"));
				customer.setPcrfAlertEmail(rs.getString("PCRF_ALERT_EMAIL"));
				customer.setSameAsEbillAddrInd(rs.getString("SAME_AS_EBILL_ADDR_IND"));
				customer.setSmsOptOutFirstRoam(rs.getString("SMS_OPT_OUT_FIRST_ROAM"));
				customer.setSmsOptOutRoamHu(rs.getString("SMS_OPT_OUT_ROAM_HU"));
				customer.setPcrfMupAlert(rs.getString("PCRF_MUP_ALERT"));
				customer.setPcrfSmsNum(rs.getString("PCRF_SMS_NUM"));
				customer.setPcrfSmsRecipient(rs.getString("PCRF_SMS_RECIPIENT"));
				customer.setPcrfAlertType(rs.getString("PCRF_ALERT_TYPE"));
				customer.setSecSrvNum(rs.getString("SEC_SRV_NUM"));
				customer.setIsNew(rs.getString("IS_NEW"));
				if("N".equalsIgnoreCase(customer.getIsNew()))//OLIVER MIP4
					customer.setAcctType("current");//oliver
				else
					customer.setAcctType("new");
				customer.setAcctNum(rs.getString("ACCT_NO"));
				customer.setAcctName(rs.getString("ACCT_NAME"));
				customer.setActiveMobileNum(rs.getString("EXIST_ACTIVE_MOBILE_NO"));
				customer.setSrvNum(rs.getString("EXIST_ACTIVE_MOBILE_NO"));
				customer.setBillPeriod(rs.getString("BILL_PERIOD"));
				customer.setCustomerNum(rs.getString("CUST_NO"));
				try {
					customer.setSelectedBillMedia(getBomWebBillMedia(rs.getString("ORDER_ID")));
				} catch (DAOException e) {
					
					e.printStackTrace();
				}
				return customer;
			}
		};
		CustomerProfileDTO customerProfile = new CustomerProfileDTO();
		try {
			
			logger.debug("getCustomerProfile() @ CustomerProfileDAO: " + SQL);
			logger.debug("getCustomerProfile() @ CustomerProfileDAO: ");
			customerList = simpleJdbcTemplate.query(SQL, mapper, orderId);
			if (customerList != null && customerList.size() > 0) {
				customerProfile = customerList.get(0);
			} else {
				return null;
			}
			
			CustomerProfileDTO customerBillingAddress = getCustomerProfileBillingAddress(orderId);
			
			if (customerBillingAddress != null) {
				if (customerBillingAddress.isNoBillingAddressFlag() == false) {
					customerProfile.setNoBillingAddressFlag(false);
					customerProfile.setBillingAreaCode(customerBillingAddress.getBillingAreaCode());
					customerProfile.setBillingDistrictCode(customerBillingAddress.getBillingDistrictCode());
					customerProfile.setBillingSectionCode(customerBillingAddress.getBillingSectionCode());
					customerProfile.setBillingStreetName(customerBillingAddress.getBillingStreetName());
					customerProfile.setBillingLotNum(customerBillingAddress.getBillingLotNum());
					customerProfile.setBillingStreetCatgCode(customerBillingAddress.getBillingStreetCatgCode());
					customerProfile.setBillingBuildingName(customerBillingAddress.getBillingBuildingName());
					customerProfile.setBillingFloor(customerBillingAddress.getBillingFloor());
					customerProfile.setBillingFlat(customerBillingAddress.getBillingFlat());
					customerProfile.setBillingStreetNum(customerBillingAddress.getBillingStreetNum());
					customerProfile.setBillingAreaDesc(customerBillingAddress.getBillingAreaDesc());
					customerProfile.setBillingDistrictDesc(customerBillingAddress.getBillingDistrictDesc());
					customerProfile.setBillingSectionDesc(customerBillingAddress.getBillingSectionDesc());
					customerProfile.setBillingQuickSearch(customerBillingAddress.getBillingAddress());
					customerProfile.setBillingStreetCatgDesc(customerBillingAddress.getBillingStreetCatgDesc());
				}
			} else {
		
				customerProfile.setNoBillingAddressFlag(true);
				customerProfile.setBillingQuickSearch("");
				
				customerProfile.setBillingFlat(customerProfile.getFlat());
				customerProfile.setBillingFloor(customerProfile.getFloor());
				customerProfile.setBillingLotNum(customerProfile.getLotNum());
				customerProfile.setBillingBuildingName(customerProfile.getBuildingName());
				customerProfile.setBillingStreetNum(customerProfile.getStreetNum());
				customerProfile.setBillingStreetName(customerProfile.getStreetName());
				customerProfile.setBillingStreetCatgDesc(customerProfile.getStreetCatgDesc());
				customerProfile.setBillingStreetCatgCode(customerProfile.getStreetCatgCode());
				customerProfile.setBillingSectionDesc(customerProfile.getSectionDesc());
				customerProfile.setBillingSectionCode(customerProfile.getSectionCode());
				customerProfile.setBillingDistrictDesc(customerProfile.getDistrictDesc());
				customerProfile.setBillingDistrictCode(customerProfile.getDistrictCode());
				customerProfile.setBillingAreaDesc(customerProfile.getAreaDesc());
				customerProfile.setBillingAreaCode(customerProfile.getAreaCode());
				
				customerProfile.setBillingUnlinkSectionFlag(false);
				customerProfile.setBillingCustAddressFlag(false);
			}
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException");
			customerProfile = null;
		} catch (Exception e) {
			logger.info("Exception caught in getCustomerProfile():", e);
			throw new DAOException(e.getMessage(), e);
		}
		return customerProfile;
	}
	
	// Get Customer Profile for cases without Mailing Address (MA), e.g. RET CSI, Call Log
	public CustomerProfileDTO getCustomerProfileAll(String orderId) throws DAOException {
		logger.debug("getCustomerProfileAll() is called");
		List<CustomerProfileDTO> customerList = new ArrayList<CustomerProfileDTO>();
		
		String SQL =
				"select C.ORDER_ID\n" +
				"      ,C.FIRST_NAME\n" + 
				"      ,C.LAST_NAME\n" + 
				"      ,C.ID_DOC_TYPE\n" + 
				"      ,C.ID_DOC_NUM\n" + 
				"      ,C.DOB\n" + 
				"      ,C.TITLE\n" + 
				"      ,C.COMPANY_NAME\n" + 
				"      ,C.IND_TYPE\n" + 
				"      ,C.IND_SUB_TYPE\n" + 
				"      ,C.NATIONALITY\n" + 
				"      ,C.CUST_NO\n" + 
				"      ,C.MOB_CUST_NO\n" + 
				"      ,C.ADDR_PROOF_IND\n" + 
				"      ,C.LOB\n" + 
				"      ,C.SERVICE_NUM\n" + 
				"      ,C.COMPANY_NAME\n" + 
				"      ,C.AUTH_ID_DOC_TYPE\n" + 
				"      ,C.AUTH_ID_DOC_NUM\n" + 
				"      ,C.COMPANY_DOC\n" + 
				"      ,C.CLUB_OPT_RMK\n" + 
				"      ,C.CLUB_OPT_REA\n" + 
				"      ,C.CLUB_OPT_OUT\n" + 
				"      ,C.HKT_OPT_OUT\n" +
				"      ,C.DUMMY_EMAIL\n" +
				"      ,A.ADDR_USAGE\n" + 
				"      ,A.AREA_CD\n" + 
				"      ,A.DIST_NO\n" + 
				"      ,A.SECT_CD\n" + 
				"      ,A.STR_NAME\n" + 
				"      ,A.HI_LOT_NO\n" + 
				"      ,A.STR_CAT_CD\n" + 
				"      ,A.BUILD_NO\n" + 
				"      ,A.FOREIGN_ADDR_FLAG\n" + 
				"      ,A.FLOOR_NO\n" + 
				"      ,A.UNIT_NO\n" + 
				"      ,A.PO_BOX_NO\n" + 
				"      ,A.ADDR_TYPE\n" + 
				"      ,A.STR_NO\n" + 
				"      ,A.SECT_DEP_IND\n" + 
				"      ,A.AREA_DESC\n" + 
				"      ,A.DIST_DESC\n" + 
				"      ,A.SECT_DESC\n" + 
				"      ,A.STR_CAT_DESC\n" + 
				"      ,NVL(A.PH_IND, 'N') PH_IND\n" + 
				"      ,NVL(CT.CONTACT_NAME, C.FIRST_NAME||' '||C.LAST_NAME) CONTACT_NAME \n" + 
				"      ,NVL(CT.TITLE, C.TITLE) CONTACT_TITLE\n" + 
				"      ,NVL(CT.CONTACT_PHONE, O.MSISDN) CONTACT_PHONE \n" + 
				"      ,CT.OTHER_PHONE\n" + 
				"      ,CT.EMAIL_ADDR\n" + 
				"      ,CT.ACTION_IND\n" + 
				"      ,BS.SMS_LANG\n" + 
				"      ,C.DOMESTIC_HELPER_IND\n" + 
				"      ,C.BYPASS_IND\n" + 
				"      ,ACCT.BILL_LANG\n" + 
				"      ,ACCT.BILL_PERIOD\n" + 
				"      ,BS.PRIVACY_IND\n" + 
				"      ,BS.PRIVACY_STAMP_DATE\n" + 
				"      ,C.CS_PORTAL_IND\n" +
				"      ,C.CS_PORTAL_STATUS\n" + 
				"      ,BS.SUPPRESS_CUST_LOCAL_TOPUP\n" +
				"      ,BS.SUPPRESS_CUST_ROAM_TOPUP\n" +
				"      ,C.DS_MISS_DOC\n" + 
				"      ,BS.MOB0060_OPT_OUT_IND\n" + 
				"      ,C.BOM_CUST_ADDR_OVERRIDE_IND\n" +
				"      ,BS.ACTIVATION_CD\n" +
				"      ,ACCT.BRAND\n" +
				"      ,C.STUDENT_PLAN_SUB_IND\n" +
				"      ,ACCT.SAME_AS_CUST_IND\n" +
				"      ,SI.SIM_TYPE\n" +
				"      ,BS.SEC_SRV_NUM\n" +
				"from BOMWEB_ORDER O \n" +
				"left join BOMWEB_CUSTOMER  C on O.ORDER_ID = C.ORDER_ID\n" + 
				"left join BOMWEB_CUST_ADDR A on C.ORDER_ID = A.ORDER_ID \n" + 
				"left join BOMWEB_CONTACT   CT on C.ORDER_ID = CT.ORDER_ID \n" + 
				"left join BOMWEB_SUB       BS on C.ORDER_ID = BS.ORDER_ID \n" + 
				"left join BOMWEB_ACCT      ACCT on C.ORDER_ID = ACCT.ORDER_ID \n" + 
				"left join BOMWEB_SIM      SI on C.ORDER_ID = SI.ORDER_ID \n" + 
				"where C.ORDER_ID = ? \n" +
				"and decode(A.ADDR_USAGE, 'MA', 1, 'DA', 2, 'BA', 1, 3) = decode(CT.CONTACT_TYPE, 'CC', 1, 'DC', 2, 3) \n" +
				"order by decode(A.ADDR_USAGE, 'MA', 1, 'BA', 2, 'DA', 3, 4)";
	
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
				customer.setAreaCode(rs.getString("AREA_CD"));
				customer.setDistrictCode(rs.getString("DIST_NO"));
				customer.setSectionCode(rs.getString("SECT_CD"));
				customer.setStreetName(rs.getString("STR_NAME"));
				customer.setLotNum(rs.getString("HI_LOT_NO"));
				customer.setStreetCatgCode(rs.getString("STR_CAT_CD"));
				customer.setBuildingName(rs.getString("BUILD_NO"));
				customer.setFloor(rs.getString("FLOOR_NO"));
				customer.setFlat(rs.getString("UNIT_NO"));
				customer.setStreetNum(rs.getString("STR_NO"));
				customer.setAreaDesc(rs.getString("area_desc"));
				customer.setDistrictDesc(rs.getString("dist_desc"));
				customer.setSectionDesc(rs.getString("sect_desc"));
				customer.setContactName(rs.getString("CONTACT_NAME"));
				customer.setContactPhone(rs.getString("CONTACT_PHONE"));
				customer.setOtherContactPhone(rs.getString("OTHER_PHONE")); 
				customer.setEmailAddr(rs.getString("EMAIL_ADDR"));
				
				customer.setStreetCatgDesc(rs.getString("STR_CAT_DESC"));
				customer.setDobStr(Utility.date2String(rs.getTimestamp("DOB"),"dd/MM/yyyy"));
				customer.setAddrProofInd(rs.getString("ADDR_PROOF_IND"));
				customer.setLob(rs.getString("LOB"));
				customer.setServiceNum(rs.getString("SERVICE_NUM"));
				customer.setRepIdDocType(rs.getString("AUTH_ID_DOC_TYPE"));
				customer.setRepIdDocNum(rs.getString("AUTH_ID_DOC_NUM"));
				customer.setCompanyDoc(rs.getString("COMPANY_DOC"));
				customer.setQuickSearch(customer.getAddress());
				customer.setSmsLang(rs.getString("sms_lang"));
				customer.setPhInd(rs.getString("ph_ind"));
				customer.setForeignDomesticHelperInd("Y".equalsIgnoreCase(rs.getString("DOMESTIC_HELPER_IND")));
				customer.setByPassValidation("Y".equalsIgnoreCase(rs.getString("BYPASS_IND")));
				customer.setBillLang(rs.getString("BILL_LANG"));
				customer.setPrivacyInd("Y".equalsIgnoreCase(rs.getString("PRIVACY_IND")));
				customer.setPrivacyStampDate(rs.getTimestamp("PRIVACY_STAMP_DATE"));
				customer.setCsPortalBool("Y".equalsIgnoreCase(rs.getString("CS_PORTAL_IND")) || "D".equalsIgnoreCase(rs.getString("CS_PORTAL_IND")));
				customer.setCsPortalInd(rs.getString("CS_PORTAL_IND"));
				customer.setCsPortalStatus(rs.getString("CS_PORTAL_STATUS"));
				customer.setSuppressLocalTopUpInd("Y".equalsIgnoreCase(rs.getString("SUPPRESS_CUST_LOCAL_TOPUP")));
				customer.setSuppressRoamTopUpInd("Y".equalsIgnoreCase(rs.getString("SUPPRESS_CUST_ROAM_TOPUP")));
				customer.setDsMissDoc(rs.getString("DS_MISS_DOC"));
				customer.setMob0060OptOutInd("Y".equalsIgnoreCase(rs.getString("MOB0060_OPT_OUT_IND"))); 
				customer.setBomCustAddrOverrideInd(rs.getString("BOM_CUST_ADDR_OVERRIDE_IND"));
				customer.setActivationCd(rs.getString("ACTIVATION_CD"));
				customer.setBrand(rs.getString("BRAND"));
				
				customer.setDummyEmail(rs.getString("DUMMY_EMAIL"));
				customer.setHktOptOut("Y".equalsIgnoreCase(rs.getString("HKT_OPT_OUT")));
				customer.setClubOptOut("Y".equalsIgnoreCase(rs.getString("CLUB_OPT_OUT")));
				customer.setHktClubOptOut("Y".equalsIgnoreCase(rs.getString("CLUB_OPT_OUT")) && "Y".equalsIgnoreCase(rs.getString("HKT_OPT_OUT")));
				customer.setClubOptRea(rs.getString("CLUB_OPT_REA"));
				customer.setClubOptRmk(rs.getString("CLUB_OPT_RMK"));
				customer.setStudentPlanSubInd("Y".equalsIgnoreCase(rs.getString("STUDENT_PLAN_SUB_IND")));
				customer.setSameAsCustInd(rs.getString("SAME_AS_CUST_IND"));
				customer.setSimType(rs.getString("SIM_TYPE"));
				customer.setNumType(rs.getString("SIM_TYPE"));
				customer.setSecSrvNum(rs.getString("SEC_SRV_NUM"));
				try {
					customer.setSelectedBillMedia(getBomWebBillMedia(rs.getString("ORDER_ID")));
				} catch (DAOException e) {
					
					e.printStackTrace();
				}
				return customer;
			}
		};
		CustomerProfileDTO customerProfile = new CustomerProfileDTO();
		try {
			
			logger.debug("getCustomerProfileAll() @ CustomerProfileDAO: " + SQL);
			logger.debug("getCustomerProfileAll() @ CustomerProfileDAO: ");
			customerList = simpleJdbcTemplate.query(SQL, mapper, orderId);
			if (customerList != null && customerList.size() > 0) {
				customerProfile = customerList.get(0);
			} else {
				return null;
			}
			
			CustomerProfileDTO customerBillingAddress = getCustomerProfileBillingAddress(orderId);
			
			if (customerBillingAddress != null) {
				if (customerBillingAddress.isNoBillingAddressFlag() == false) {
					customerProfile.setNoBillingAddressFlag(false);
					customerProfile.setBillingAreaCode(customerBillingAddress.getBillingAreaCode());
					customerProfile.setBillingDistrictCode(customerBillingAddress.getBillingDistrictCode());
					customerProfile.setBillingSectionCode(customerBillingAddress.getBillingSectionCode());
					customerProfile.setBillingStreetName(customerBillingAddress.getBillingStreetName());
					customerProfile.setBillingLotNum(customerBillingAddress.getBillingLotNum());
					customerProfile.setBillingStreetCatgCode(customerBillingAddress.getBillingStreetCatgCode());
					customerProfile.setBillingBuildingName(customerBillingAddress.getBillingBuildingName());
					customerProfile.setBillingFloor(customerBillingAddress.getBillingFloor());
					customerProfile.setBillingFlat(customerBillingAddress.getBillingFlat());
					customerProfile.setBillingStreetNum(customerBillingAddress.getBillingStreetNum());
					customerProfile.setBillingAreaDesc(customerBillingAddress.getBillingAreaDesc());
					customerProfile.setBillingDistrictDesc(customerBillingAddress.getBillingDistrictDesc());
					customerProfile.setBillingSectionDesc(customerBillingAddress.getBillingSectionDesc());
					customerProfile.setBillingQuickSearch(customerBillingAddress.getBillingAddress());
					customerProfile.setBillingStreetCatgDesc(customerBillingAddress.getBillingStreetCatgDesc());
				}
			} else {
		
				customerProfile.setNoBillingAddressFlag(true);
				customerProfile.setBillingQuickSearch("");
				
				customerProfile.setBillingFlat(customerProfile.getFlat());
				customerProfile.setBillingFloor(customerProfile.getFloor());
				customerProfile.setBillingLotNum(customerProfile.getLotNum());
				customerProfile.setBillingBuildingName(customerProfile.getBuildingName());
				customerProfile.setBillingStreetNum(customerProfile.getStreetNum());
				customerProfile.setBillingStreetName(customerProfile.getStreetName());
				customerProfile.setBillingStreetCatgDesc(customerProfile.getStreetCatgDesc());
				customerProfile.setBillingStreetCatgCode(customerProfile.getStreetCatgCode());
				customerProfile.setBillingSectionDesc(customerProfile.getSectionDesc());
				customerProfile.setBillingSectionCode(customerProfile.getSectionCode());
				customerProfile.setBillingDistrictDesc(customerProfile.getDistrictDesc());
				customerProfile.setBillingDistrictCode(customerProfile.getDistrictCode());
				customerProfile.setBillingAreaDesc(customerProfile.getAreaDesc());
				customerProfile.setBillingAreaCode(customerProfile.getAreaCode());
				
				customerProfile.setBillingUnlinkSectionFlag(false);
				customerProfile.setBillingCustAddressFlag(false);
			}
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException");
			customerProfile = null;
		} catch (Exception e) {
			logger.info("Exception caught in getCustomerProfileAll():", e);
			throw new DAOException(e.getMessage(), e);
		}
		return customerProfile;
	}
	
	// Get Customer Profile for cases without Mailing Address (MA), e.g. RET CSI, Call Log
	public CustomerProfileDTO getCurrentCustomerProfile(String orderId) throws DAOException {
		logger.debug("getCurrentCustomerProfile() is called");
		List<CustomerProfileDTO> customerList = new ArrayList<CustomerProfileDTO>();
		
		String SQL =
				"Select \n"
				+ "FIRST_NAME,\n "
				+ "LAST_NAME ,\n "
				+ "ID_DOC_TYPE , \n"
				+ "ID_DOC_NUM , \n "
				+ "TITLE ,\n"
				+ " COMPANY_NAME \n"
				+ "from bomweb_ord_mob_cur_customer \n"
				+ "where ORDER_ID = ? \n" ;

	
		ParameterizedRowMapper<CustomerProfileDTO> mapper = new ParameterizedRowMapper<CustomerProfileDTO>() {
			public CustomerProfileDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				CustomerProfileDTO customer = new CustomerProfileDTO();
		
				customer.setCustFirstName(rs.getString("FIRST_NAME"));
				customer.setCustLastName(rs.getString("LAST_NAME"));
				customer.setIdDocType(rs.getString("ID_DOC_TYPE"));
				customer.setIdDocNum(rs.getString("ID_DOC_NUM"));
				customer.setTitle(rs.getString("TITLE"));
				customer.setCompanyName(rs.getString("COMPANY_NAME"));
				return customer;
			}
		};
		CustomerProfileDTO customerProfile = new CustomerProfileDTO();
		try {
			
			logger.debug("getCurrentCustomerProfile() @ CustomerProfileDAO: " + SQL);
			logger.debug("getCurrentCustomerProfile() @ CustomerProfileDAO: ");
			customerList = simpleJdbcTemplate.query(SQL, mapper, orderId);
			if (customerList != null && customerList.size() > 0) {
				customerProfile = customerList.get(0);
			} else {
				return null;
			}
			
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException");
			customerProfile = null;
		} catch (Exception e) {
			logger.info("Exception caught in getCurrentCustomerProfile():", e);
			throw new DAOException(e.getMessage(), e);
		}
		return customerProfile;
	}
	
	// add 20110517
	private void insertBomWebCustomerBillingAddress(CustomerProfileDTO dto)
			throws DAOException {
		logger.debug("insertBomWebCustomerBillingAddress() is called");

		// define SQL string
		String SQL = "insert into BOMWEB_CUST_ADDR\n"
				+ "  (ORDER_ID,\n"
				+ "   ADDR_USAGE,\n"
				+ "   AREA_CD,\n"
				+ "   DIST_NO,\n"
				+ "   SECT_CD,\n"
				+ "   STR_NAME,\n"
				+ "   HI_LOT_NO,\n"
				+ "   STR_CAT_CD,\n"
				+ "   BUILD_NO,\n"
				+ "   FOREIGN_ADDR_FLAG,\n"
				+ "   FLOOR_NO,\n"
				+ "   UNIT_NO,\n"
				+ "   PO_BOX_NO,\n"
				+ "   ADDR_TYPE,\n"
				+ "   STR_NO,\n"
				+ "   SECT_DEP_IND,\n"
				+ "   area_desc,\n"
				+ "   dist_desc,\n"
				+ "   sect_desc,\n"
				+ "   STR_CAT_DESC,\n"
				+ "   CREATE_DATE)\n "
				+ "values\n"
				+ "  (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?,?,?, sysdate)";

		// insert to table
		try {
			simpleJdbcTemplate.update(SQL, dto.getOrderId(), "BA",
					dto.getBillingAreaCode(), dto.getBillingDistrictCode(),
					dto.getBillingSectionCode(), dto.getBillingStreetName(),
					dto.getBillingLotNum(), dto.getBillingStreetCatgCode(),
					dto.getBillingBuildingName(), "N", dto.getBillingFloor(),
					dto.getBillingFlat(), "", "S", dto.getBillingStreetNum(),
					"N", dto.getBillingAreaDesc(),
					dto.getBillingDistrictDesc(), dto.getBillingSectionDesc(),
					dto.getBillingStreetCatgDesc() // add by wilson 20110224
					);

		} catch (Exception e) {
			logger.error(
					"Exception caught in insertBomWebCustomerBillingAddress()",
					e);
			throw new DAOException(e.getMessage(), e);
		}

	}

	public CustomerProfileDTO getCustomerProfileBillingAddress(String orderId)
			throws DAOException {
		logger.debug("getCustomerProfileBillingAddress() is called");

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
				+ " from	 BOMWEB_CUST_ADDR a \n"
				+ "   where a.order_id=?  and a.ADDR_USAGE ='BA'\n";

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
			logger.debug("getCustomerProfileBillingAddress() @ CustomerProfileDAO: "	+ SQL);
			logger.debug("getCustomerProfileBillingAddress() @ CustomerProfileDAO: ");
			
			customerList = simpleJdbcTemplate.query(SQL, mapper, orderId);
		

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
	public void insertBomWebBillMedia(MobBillMediaDTO dto) //paper bill Athena 20130925
			throws DAOException {
		logger.debug("insertBomWebBillMedia() is called");

		// define SQL string
		String SQL = "insert into BOMWEB_BILL_MEDIA_MOB\n"
				+ "  (ORDER_ID,\n"
				+ "   BILL_MEDIA_CODE,\n"
				+ "   CREATE_DATE,\n"
				+ "   CREATE_BY,\n"
				+ "   LAST_UPD_DATE,\n"
				+ "   LAST_UPD_BY)\n"
				+ "values\n"
				+ "  (:orderId,:billMediaCode, sysdate, :createBy, sysdate, :lastUpdBy)";

		// insert to table
		try {			
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("orderId", dto.getOrderId());
			params.addValue("billMediaCode", dto.getBillMediaCode());
			params.addValue("createBy", dto.getCreateBy());
			params.addValue("lastUpdDate", dto.getLastUpdDate());
			params.addValue("lastUpdBy", dto.getLastUpdBy());	
			simpleJdbcTemplate.update(SQL, params);
		} catch (Exception e) {
			logger.error("Exception caught in insertBomWebBillMedia()", e);
			throw new DAOException(e.getMessage(), e);
		}

	}
	public void deleteBomWebBillMedia(String orderId) throws DAOException { //Paper bill Athena 20130925
		logger.debug("deleteBomWebBillMedia() is called");

		String SQL = "delete bomweb_bill_media_mob where order_id= :orderId";
		try {
			simpleJdbcTemplate.update(SQL, orderId);
		} catch (Exception e) {
			logger.error("Exception caught in deleteBomWebBillMedia()", e);
			throw new DAOException(e.getMessage(), e);
		}

	}
	public List<String> getBomWebBillMedia(String orderId) throws DAOException { //Paper bill Athena 20130925
		logger.debug("getBomWebBillMedia() is called");
		List<String> selectedBillMediaList = new ArrayList<String>();
		String SQL = "select bill_media_code from bomweb_bill_media_mob where order_id= :orderId";
		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {

		public String mapRow(ResultSet rs, int rowNum) throws SQLException {
			return rs.getString("BILL_MEDIA_CODE");
			}
		};
		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("orderId",orderId);
			selectedBillMediaList = simpleJdbcTemplate.query(SQL, mapper,params);
				
	
		} catch (EmptyResultDataAccessException erdae) {
			selectedBillMediaList = null;
		}
		catch (Exception e) {
			logger.error("Exception caught in getBomWebBillMedia()", e);
			throw new DAOException(e.getMessage(), e);
		}
		return selectedBillMediaList;
	}

	public CustomerProfileDTO getCosCustomerProfile(String orderId)
			throws DAOException {
			logger.debug("getCosCustomerProfile() is called");
			List<CustomerProfileDTO> customerList = new ArrayList<CustomerProfileDTO>();
			
				String SQL =
						"select C.ORDER_ID\n" +
						"      ,C.FIRST_NAME\n" + 
						"      ,C.LAST_NAME\n" + 
						"      ,C.ID_DOC_TYPE\n" + 
						"      ,C.ID_DOC_NUM\n" + 
						"      ,C.DOB\n" + 
						"      ,C.TITLE\n" + 
						"      ,C.COMPANY_NAME\n" + 
						"      ,C.IND_TYPE\n" + 
						"      ,C.IND_SUB_TYPE\n" + 
						"      ,C.NATIONALITY\n" + 
						"      ,C.CUST_NO\n" + 
						"      ,C.MOB_CUST_NO\n" + 
						"      ,C.ADDR_PROOF_IND\n" + 
						"      ,C.LOB\n" + 
						"      ,C.SERVICE_NUM\n" + 
						"      ,C.CLUB_OPT_RMK\n" + 
						"      ,C.CLUB_OPT_REA\n" + 
						"      ,C.CLUB_OPT_OUT\n" + 
						"      ,C.HKT_OPT_OUT\n" +
						"      ,C.DUMMY_EMAIL\n" +
						"      ,CT.CONTACT_NAME\n" + 
						"      ,CT.TITLE CONTACT_TITLE\n" + 
						"      ,CT.CONTACT_PHONE\n" + 
						"      ,CT.OTHER_PHONE\n" + 
						"      ,CT.EMAIL_ADDR\n" + 
						"      ,CT.ACTION_IND\n" + 
						"      ,BS.SMS_LANG\n" + 
						"      ,C.DOMESTIC_HELPER_IND\n" + 
						"      ,C.BYPASS_IND\n" + 
						"      ,ACCT.BILL_LANG\n" + 
						"      ,ACCT.BILL_PERIOD\n" + 
						"      ,BS.PRIVACY_IND\n" + 
						"      ,BS.PRIVACY_STAMP_DATE\n" + 
						"      ,C.CS_PORTAL_IND\n" + 
						"      ,C.CS_PORTAL_STATUS\n" + 
						"      ,BS.SUPPRESS_CUST_LOCAL_TOPUP\n" +
						"      ,BS.SUPPRESS_CUST_ROAM_TOPUP\n" +
						"      ,C.DS_MISS_DOC\n" + 
						"      ,BS.MOB0060_OPT_OUT_IND\n" + 
						"      ,C.BOM_CUST_ADDR_OVERRIDE_IND\n" +
						"      ,BS.ACTIVATION_CD\n" +
						"      ,ACCT.BRAND\n" +
						"      ,C.STUDENT_PLAN_SUB_IND\n" +
						"      ,SIM.SIM_TYPE\n" + 
						"from BOMWEB_CUSTOMER  C\n" + 
						"    ,(select ORDER_ID, CONTACT_NAME, TITLE, CONTACT_PHONE, OTHER_PHONE, EMAIL_ADDR, ACTION_IND " +
						"    from BOMWEB_CONTACT where CONTACT_TYPE = 'CC')  CT\n" + 
						"    ,BOMWEB_SUB       BS\n" + 
						"    ,BOMWEB_ACCT      ACCT\n" +
						"    ,BOMWEB_SIM SIM\n" + 
						"where C.ORDER_ID = CT.ORDER_ID (+)\n" + 
						"and C.ORDER_ID = BS.ORDER_ID\n" + 
						"and C.ORDER_ID = ACCT.ORDER_ID (+)\n" +
						"and C.ORDER_ID = SIM.ORDER_ID (+)\n" + 
						"and C.ORDER_ID = ?\n";
	
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
					customer.setContactName(rs.getString("CONTACT_NAME"));
					customer.setContactPhone(rs.getString("CONTACT_PHONE"));
					customer.setOtherContactPhone(rs.getString("OTHER_PHONE")); 
					customer.setEmailAddr(rs.getString("EMAIL_ADDR"));
					customer.setDobStr(Utility.date2String(rs.getTimestamp("DOB"),"dd/MM/yyyy"));
					customer.setAddrProofInd(rs.getString("ADDR_PROOF_IND"));
					customer.setLob(rs.getString("LOB"));
					customer.setServiceNum(rs.getString("SERVICE_NUM"));
					//customer.setActiveMobileNum(rs.getString("ACITVE_MOBILE_NUM"));
					
					customer.setQuickSearch(customer.getAddress());
					customer.setSmsLang(rs.getString("sms_lang"));
					customer.setForeignDomesticHelperInd("Y".equalsIgnoreCase(rs.getString("DOMESTIC_HELPER_IND")));
					customer.setByPassValidation("Y".equalsIgnoreCase(rs.getString("BYPASS_IND")));
					customer.setBillLang(rs.getString("BILL_LANG"));
					customer.setPrivacyInd("Y".equalsIgnoreCase(rs.getString("PRIVACY_IND")));
					customer.setPrivacyStampDate(rs.getTimestamp("PRIVACY_STAMP_DATE"));
					customer.setCsPortalBool("Y".equalsIgnoreCase(rs.getString("CS_PORTAL_IND")) || "D".equalsIgnoreCase(rs.getString("CS_PORTAL_IND")));
					customer.setCsPortalInd(rs.getString("CS_PORTAL_IND"));
					customer.setCsPortalStatus(rs.getString("CS_PORTAL_STATUS"));
					customer.setSuppressLocalTopUpInd("Y".equalsIgnoreCase(rs.getString("SUPPRESS_CUST_LOCAL_TOPUP")));
					customer.setSuppressRoamTopUpInd("Y".equalsIgnoreCase(rs.getString("SUPPRESS_CUST_ROAM_TOPUP")));
					customer.setDsMissDoc(rs.getString("DS_MISS_DOC"));
					customer.setMob0060OptOutInd("Y".equalsIgnoreCase(rs.getString("MOB0060_OPT_OUT_IND")));
					customer.setBomCustAddrOverrideInd(rs.getString("BOM_CUST_ADDR_OVERRIDE_IND"));
					customer.setActivationCd(rs.getString("ACTIVATION_CD"));
					customer.setBrand(rs.getString("BRAND"));
					
					customer.setDummyEmail(rs.getString("DUMMY_EMAIL"));
					customer.setHktOptOut("Y".equalsIgnoreCase(rs.getString("HKT_OPT_OUT")));
					customer.setClubOptOut("Y".equalsIgnoreCase(rs.getString("CLUB_OPT_OUT")));
					customer.setHktClubOptOut("Y".equalsIgnoreCase(rs.getString("CLUB_OPT_OUT")) && "Y".equalsIgnoreCase(rs.getString("HKT_OPT_OUT")));
					customer.setClubOptRea(rs.getString("CLUB_OPT_REA"));
					customer.setClubOptRmk(rs.getString("CLUB_OPT_RMK"));
					customer.setStudentPlanSubInd("Y".equalsIgnoreCase(rs.getString("STUDENT_PLAN_SUB_IND")));
					customer.setSimType(rs.getString("SIM_TYPE"));
					customer.setNumType(rs.getString("SIM_TYPE"));
					try {
						customer.setSelectedBillMedia(getBomWebBillMedia(rs.getString("ORDER_ID")));
					} catch (DAOException e) {
						System.out.println(e.getMessage());
						e.printStackTrace();
					}
					return customer;
				}
			};
			
			try {
				logger.debug("getCosCustomerProfile() @ CustomerProfileDAO: " + SQL);
				logger.debug("getCosCustomerProfile() @ CustomerProfileDAO: ");
				customerList = simpleJdbcTemplate.query(SQL, mapper, orderId);
			
				if (customerList == null || customerList.isEmpty()) {
					return null;
				}
				
			} catch (EmptyResultDataAccessException erdae) {
				logger.info("EmptyResultDataAccessException");
			
				customerList = null;
			} catch (Exception e) {
				logger.info("Exception caught in getCosCustomerProfile():", e);
				throw new DAOException(e.getMessage(), e);
			}
			
			return customerList.get(0);// only return the first one
			
			}
	
	
	public ActualUserDTO getActualUser(String orderId)
			throws DAOException {
			logger.debug("getActualUser() is called");
			List<ActualUserDTO> actualUserList = new ArrayList<ActualUserDTO>();
			
				String SQL =
						"select A.ORDER_ID\n" +
						"      ,A.ORDER_TYPE\n" + 
						"      ,A.MEMBER_NUM\n" + 
						"      ,A.SUB_DOC_NUM\n" + 
						"      ,A.SUB_DOC_TYPE\n" + 
						"      ,A.SUB_COMPANY_NAME\n" + 
						"      ,A.SUB_TITLE\n" + 
						"      ,A.SUB_LAST_NAME\n" + 
						"      ,A.SUB_FIRST_NAME\n" + 
						"      ,A.SUB_CONTACT_TEL\n" + 
						"      ,A.SUB_EMAIL_ADDR\n" + 
						"      ,trunc(A.SUB_DATE_OF_BIRTH) SUB_DATE_OF_BIRTH\n" + 
						"from BOMWEB_MOB_ACTUAL_USER  A\n" + 
						"where A.ORDER_TYPE = 'P'\n" + 
						"and A.ORDER_ID = ?\n";
	
			ParameterizedRowMapper<ActualUserDTO> mapper = new ParameterizedRowMapper<ActualUserDTO>() {
				public ActualUserDTO mapRow(ResultSet rs, int rowNum)
						throws SQLException {
					ActualUserDTO actualUser = new ActualUserDTO();
			
					actualUser.setOrderId(rs.getString("ORDER_ID"));
					actualUser.setOrderType(rs.getString("ORDER_TYPE"));
					actualUser.setMemberNum(rs.getString("MEMBER_NUM"));
					actualUser.setSubDocNum(rs.getString("SUB_DOC_NUM"));
					actualUser.setSubDocType(rs.getString("SUB_DOC_TYPE"));
					actualUser.setSubCompanyName(rs.getString("SUB_COMPANY_NAME"));
					actualUser.setSubTitle(rs.getString("SUB_TITLE"));
					actualUser.setSubLastName(rs.getString("SUB_LAST_NAME"));
					actualUser.setSubFirstName(rs.getString("SUB_FIRST_NAME"));
					actualUser.setSubContactTel(rs.getString("SUB_CONTACT_TEL"));
					actualUser.setSubEmailAddr(rs.getString("SUB_EMAIL_ADDR"));			
					actualUser.setSubDateOfBirthStr(Utility.date2String(rs.getDate("SUB_DATE_OF_BIRTH"), "dd/MM/yyyy"));

					return actualUser;
				}
			};
			
			try {
				logger.debug("getActualUser() @ CustomerProfileDAO: " + SQL);
				logger.debug("getActualUser() @ CustomerProfileDAO: ");
				actualUserList = simpleJdbcTemplate.query(SQL, mapper, orderId);
			
				if (actualUserList == null || actualUserList.isEmpty()) {
					return null;
				}
				
			} catch (EmptyResultDataAccessException erdae) {
				logger.info("EmptyResultDataAccessException");
			
				actualUserList = null;
			} catch (Exception e) {
				logger.info("Exception caught in getCosCustomerProfile():", e);
				throw new DAOException(e.getMessage(), e);
			}
			
			return actualUserList.get(0);// only return the first one
			
			}
	
	public boolean isStudentPlan(String orderId) throws DAOException {
		logger.debug("isStudentPlan() is called");
		List<String> result = new ArrayList<String>();
		
		String SQL =
				"select C.STUDENT_PLAN_SUB_IND\n" +
				"from BOMWEB_CUSTOMER  C\n" + 
				"where C.ORDER_ID = ?\n";
	
		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
			public String mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return rs.getString("STUDENT_PLAN_SUB_IND");
			}
		};
		
		try {
			logger.debug("isStudentPlan() @ CustomerProfileDAO: " + SQL);
			logger.debug("isStudentPlan() @ CustomerProfileDAO: ");
			result = simpleJdbcTemplate.query(SQL, mapper, orderId);
			if (result != null && result.size() > 0) {
				String studentPlanSubInd = result.get(0);
				return "Y".equalsIgnoreCase(studentPlanSubInd);
			} else {
				return false;
			}
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException");
			return false;
		} catch (Exception e) {
			logger.info("Exception caught in getCustomerProfile():", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public String getCareCashStartDate(String codeType) throws DAOException {
		logger.debug("isStudentPlan() is called");
		List<String> result = new ArrayList<String>();
		
		String SQL =
				"select code_id\n" +
				"from bomweb_code_lkup  C\n" + 
				"where CODE_TYPE = ?\n";
	
		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
			public String mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return rs.getString("code_id");
			}
		};
		
		try {
			logger.debug("getCareCashStartDate() @ CustomerProfileDAO: " + SQL);
			logger.debug("getCareCashStartDate() @ CustomerProfileDAO: ");
			result = simpleJdbcTemplate.query(SQL, mapper, codeType);
			
			String code_id = result.get(0);
			return code_id;

		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException");
			return null;
		} catch (Exception e) {
			logger.info("Exception caught in getCustomerProfile():", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
}
