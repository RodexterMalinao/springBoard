package com.bomwebportal.dao;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.util.CollectionUtils;

import com.bomwebportal.dto.AllDocDTO.DocType;
import com.bomwebportal.dto.AllOrdDocAssgnDTO;
import com.bomwebportal.dto.AllOrdDocAssgnDTO.CollectedInd;
import com.bomwebportal.dto.AllOrdDocAssgnDTO.MarkDelInd;
import com.bomwebportal.dto.BasketDTO;
import com.bomwebportal.dto.IGuardDTO;
import com.bomwebportal.dto.PaymentDTO;
import com.bomwebportal.dto.VasDetailDTO;
import com.bomwebportal.dto.report.IPhoneTradeInFormDTO;
import com.bomwebportal.dto.report.MobAppFormDTO;
import com.bomwebportal.dto.report.RptConciergeServiPadiPhoneDTO;
import com.bomwebportal.dto.report.RptCreditCardDDAuthDTO;
import com.bomwebportal.dto.report.RptKeyInformationSheetDTO;
import com.bomwebportal.dto.report.RptMobileSafetyPhoneAFCDTO;
import com.bomwebportal.dto.report.RptMobileSafetyPhoneDTO;
import com.bomwebportal.dto.report.RptMobileSafetyPhoneSuppAppDTO;
import com.bomwebportal.dto.report.RptMobileSafetyPhoneTnCDTO;
import com.bomwebportal.dto.report.RptNFCConsentDTO;
import com.bomwebportal.dto.report.RptOctopusConsentDTO;
import com.bomwebportal.dto.report.RptPrintIndDTO;
import com.bomwebportal.dto.report.RptRetAppMobileServDTO;
import com.bomwebportal.dto.report.RptRetConfirmForCopDTO;
import com.bomwebportal.dto.report.RptRetCosDTO;
import com.bomwebportal.dto.report.RptRetCosDeliveryNoteDTO;
import com.bomwebportal.dto.report.RptRetCountRejDTO;
import com.bomwebportal.dto.report.RptRetCourierDeliveryGuidelineDTO;
import com.bomwebportal.dto.report.RptSecretarialServDTO;
import com.bomwebportal.dto.report.RptTnGServiceFormDTO;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.mob.ccs.dto.MobCcsSupportDocDTO;
import com.bomwebportal.mob.ccs.dto.ui.DeliveryUI;
import com.bomwebportal.mob.ccs.dto.ui.PaymentUpFrontUI;
import com.bomwebportal.util.Utility;
import com.bomwebportal.web.util.GenericReportHelper;
import com.bomwebportal.web.util.ProjectEagleReportHelper;


public class MobCosRptDAO extends BaseDAO{
	
	protected final Log logger = LogFactory.getLog(getClass());
	public RptRetAppMobileServDTO retrieveRptRetAppMobileServSecAtoC(String orderId) throws DAOException { 
		logger.debug("retrieveRptRetAppMobileServSecAtoC() is called");
		//RptRetAppMobileServDTO rptRetAppMobileServDTO = new RptRetAppMobileServDTO();
		List<RptRetAppMobileServDTO> list = new ArrayList<RptRetAppMobileServDTO>();
		String SQL="select O.ORDER_ID , " 
				+"  O.MSISDN , " 
				+"  C.ID_DOC_TYPE , " 
				+"  C.ID_DOC_NUM , " 
				+"  C.COMPANY_NAME , " 
				+"  C.TITLE , " 
				+"  C.FIRST_NAME , " 
				+"  C.LAST_NAME , " 
				+"  C.CS_PORTAL_IND, "
				+"  C.CS_PORTAL_STATUS, "
				+"  C.CS_PORTAL_LOGIN, "
				+"  C.DUMMY_EMAIL, "
				+"  C.HKT_OPT_OUT, "
				+"  C.CLUB_OPT_OUT, "
				+"  nvl(o.esig_email_addr ,A.EMAIL_ADDR )EMAIL_ADDR , " 
				+"  O.ORDER_TYPE , " 
				+"  O.APP_DATE , " 
				+"  O.SRV_REQ_DATE , " 
				+"  O.IMEI , " 
				+"  A.BILL_PERIOD , " 
				+"  A.BILL_LANG , " 
				+"  CHINA_MRT.MSISDN CN_MRT , " 
				+"  CHINA_MRT.MSISDNLVL CN_MSISDN_LVL , " 
				+"  CHINA_MRT.RESERVE_ID CN_RESERVE_ID , " 
				+"  CHINA_MRT.CITY_CD , " 
				+"  SIMNO.ICCID , " 
				+"  BC.CONTACT_PHONE, "
				+"  OM.ORDER_NATURE, " 
				+"  (CASE WHEN ORDN.ORDNAT=1 THEN 'APPEND' WHEN ORDN.ORDNAT=0 THEN 'RENEWAL' END) CONTRACT_NATURE, " //RET - RENEWAL, UPG/CRP APPEND
				+"  (CASE WHEN CHGSIM.CHGSIMCNT=1 THEN 'UPD' WHEN CHGSIM.CHGSIMCNT=0 THEN 'NONE' END) CHGSIMIND, "
				+"  TC.APPEND_TC_START_DATE " 
				+"  ,pkg_sb_mob_cos_order.get_sec_srv_num(:orderId) sec_srv_num "
				+"FROM BOMWEB_ORDER O , " 
				+"  (select ORDER_ID, " 
				+"    MSISDN, " 
				+"    MSISDNLVL, " 
				+"    RESERVE_ID, " 
				+"    CITY_CD " 
				+"  from BOMWEB_MRT M " 
				+"  where M.SEQ_ID = 2 " 
				+"  ) CHINA_MRT , " 
				+"  BOMWEB_ORDER_MOB OM , " 
				+"  BOMWEB_CUSTOMER C , " 
				+"  BOMWEB_ACCT A , " 
				+"  (select M.order_id, " 
				+"    M.Sim_Iccid iccid " 
				+"  from BOMWEB_ORD_MOB_CHG_SIM_TXN M " 
				+"  where M.ORDER_ID              = :orderId " 
				+"  and NVL(M.MARK_DEL_IND, 'N') != 'Y' " 
				+"  and M.CHG_SIM_TXN_ID          = " 
				+"    (select max(A.CHG_SIM_TXN_ID) " 
				+"    FROM BOMWEB_ORD_MOB_CHG_SIM_TXN A " 
				+"    where A.ORDER_ID = :orderId " 
				+"    ) " 
				+"  )SIMNO, " 
				+"  (SELECT ORDER_ID, " 
				+"    CONTACT_PHONE " 
				+"  FROM BOMWEB_CONTACT M " 
				+"  WHERE M.contact_type='DC') BC, " 
				+"  (select count(*) ORDNAT " 
				+"  from bomweb_ord_mob_cur_tc t, " 
				+"    BOMWEB_ORD_MOB_CUR_PROD P " 
				+"  where t.order_id =:orderId " 
				+"  and t.order_id   =p.order_id " 
				+"  and t.cr_prod_id =p.prod_id " 
				+"  AND P.PROD_TYPE  ='T' " 
				+"  AND P.IO_IND     =' ' ) ORDN, " 
				+"  (select max(term_tc_date) + 1 APPEND_TC_START_DATE "
				+"   from bomweb_ord_mob_cur_prod p, bomweb_ord_mob_cur_tc t, bomweb_order_mob m "
				+"   where m.order_id = p.order_id "
				+"   and m.order_id = t.order_id "
				+"   and (m.order_nature = 'UPG'  or (m.ordeR_nature = 'RET' and m.ol_ind = 'Y') or m.order_nature = 'RET') "
				+"   and p.prod_type = 'T' "
				+"   and p.io_ind = ' ' "
				+"   and p.prod_id = t.cr_prod_id "
				+"   and p.cc_offer_sub_key = t.cc_offer_sub_key "
				+"   and m.order_id = :orderId "
				+"  ) TC, " 
				+"  (select count(*) CHGSIMCNT " 
				+"  from bomweb_ord_mob_chg_sim_txn t " 
				+"  WHERE T.ORDER_ID              =:orderId " 
				+"  and nvl(t.mark_del_ind, 'N') !='Y' " 
				+"  ) CHGSIM " 
				+"WHERE O.ORDER_ID = :orderId " 
				+"and O.ORDER_ID   = CHINA_MRT.ORDER_ID(+) " 
				+"and O.ORDER_ID   = OM.ORDER_ID(+) " 
				+"and O.ORDER_ID   = C.ORDER_ID(+) " 
				+"AND O.ORDER_ID   = A.ORDER_ID(+) " 
				+"AND O.ORDER_ID   = SIMNO.ORDER_ID(+) " 
				+"AND O.ORDER_ID   = BC.ORDER_ID(+)";
		ParameterizedRowMapper<RptRetAppMobileServDTO> mapper = new ParameterizedRowMapper<RptRetAppMobileServDTO>() {

			public RptRetAppMobileServDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				RptRetAppMobileServDTO rptRetAppMobileServDTO = new RptRetAppMobileServDTO();
				rptRetAppMobileServDTO.setOrderNature(rs.getString("ORDER_NATURE"));
				rptRetAppMobileServDTO.setContractNature(rs.getString("CONTRACT_NATURE"));
				rptRetAppMobileServDTO.setChgSimInd(rs.getString("CHGSIMIND"));
				rptRetAppMobileServDTO.setTitle(rs.getString("TITLE"));
				rptRetAppMobileServDTO.setCustLastName(rs.getString("LAST_NAME"));
				rptRetAppMobileServDTO.setCustFirstName(rs.getString("FIRST_NAME"));
				rptRetAppMobileServDTO.setContactPhone(rs.getString("MSISDN"));
				rptRetAppMobileServDTO.setOtherContactPhone(rs.getString("CONTACT_PHONE"));
				rptRetAppMobileServDTO.setEmailAddr(rs.getString("EMAIL_ADDR"));
				rptRetAppMobileServDTO.setServiceReqDate(rs.getTimestamp("SRV_REQ_DATE"));
				rptRetAppMobileServDTO.setIccid(rs.getString("ICCID"));
				rptRetAppMobileServDTO.setBillDate(rs.getString("BILL_PERIOD"));
				rptRetAppMobileServDTO.setImei(rs.getString("IMEI"));
				rptRetAppMobileServDTO.setIdDocType(rs.getString("ID_DOC_TYPE"));
				rptRetAppMobileServDTO.setIdDocNum(rs.getString("ID_DOC_NUM"));
				rptRetAppMobileServDTO.setCompanyName(rs.getString("COMPANY_NAME"));
				rptRetAppMobileServDTO.setBillLang(rs.getString("BILL_LANG"));
				rptRetAppMobileServDTO.setUnicomMsisdn(rs.getString("CN_MRT"));
				rptRetAppMobileServDTO.setMsisdn(rs.getString("MSISDN"));
				rptRetAppMobileServDTO.setAppendTcStartDate(rs.getDate("APPEND_TC_START_DATE"));
				rptRetAppMobileServDTO.setCsPortalBool("Y".equalsIgnoreCase(rs.getString("CS_PORTAL_IND")) || "D".equalsIgnoreCase(rs.getString("CS_PORTAL_IND")));
				rptRetAppMobileServDTO.setCsPortalInd(rs.getString("CS_PORTAL_IND"));
				rptRetAppMobileServDTO.setCsPortalStatus(rs.getString("CS_PORTAL_STATUS"));
				rptRetAppMobileServDTO.setCsPortalLogin(rs.getString("CS_PORTAL_LOGIN"));
				rptRetAppMobileServDTO.setDummyEmail(rs.getString("DUMMY_EMAIL"));
				rptRetAppMobileServDTO.setHktOptOut("Y".equalsIgnoreCase(rs.getString("HKT_OPT_OUT")));
				rptRetAppMobileServDTO.setClubOptOut("Y".equalsIgnoreCase(rs.getString("CLUB_OPT_OUT")));
				rptRetAppMobileServDTO.setHktClubOptOut(rptRetAppMobileServDTO.isClubOptOut() && rptRetAppMobileServDTO.isHktOptOut());
				rptRetAppMobileServDTO.setSecSrvNum(rs.getString("sec_srv_num"));
				return rptRetAppMobileServDTO;
			}
		};

		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("orderId",orderId);
			list = simpleJdbcTemplate.query(SQL, mapper,params);
			
		} catch (EmptyResultDataAccessException erdae) {
			throw new AppRuntimeException(erdae);
		} catch (Exception e) {
			logger.error("Exception caught in retrieveRptRetAppMobileServSecAtoC()", e);
			throw new DAOException(e.getMessage(), e);
		}
		return (list == null || list.isEmpty()||list.size()==0) ? null : list.get(0);
	}
	
	public RptKeyInformationSheetDTO retrieveRptKeyInformationSheetDTO(String orderId) throws DAOException { 
		logger.debug("retrieveRptKeyInformationSheetDTO() is called");
		//RptKeyInformationSheetDTO RptKeyInformationSheetDTO = new RptKeyInformationSheetDTO();
		List<RptKeyInformationSheetDTO> list = new ArrayList<RptKeyInformationSheetDTO>();
		String SQL="select O.ORDER_ID , " 
				+"  O.MSISDN , " 
				+"  C.TITLE , " 
				+"  C.FIRST_NAME , " 
				+"  C.LAST_NAME , " 
				+"  O.APP_DATE , " 
				+"  O.ORDER_ID " 
				+"from BOMWEB_ORDER O , " 
				+"  BOMWEB_CUSTOMER C " 
				+"WHERE O.ORDER_ID = :orderId " 
				+"and O.ORDER_ID = C.ORDER_ID(+) ";

		ParameterizedRowMapper<RptKeyInformationSheetDTO> mapper = new ParameterizedRowMapper<RptKeyInformationSheetDTO>() {

			public RptKeyInformationSheetDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				RptKeyInformationSheetDTO rptKeyInformationSheetDTO = new RptKeyInformationSheetDTO();
				rptKeyInformationSheetDTO.setAgreementNum(rs.getString("ORDER_ID"));
				rptKeyInformationSheetDTO.setAppInDate(rs.getDate("APP_DATE"));
				rptKeyInformationSheetDTO.setTitle(rs.getString("TITLE"));
				rptKeyInformationSheetDTO.setCustLastName(rs.getString("LAST_NAME"));
				rptKeyInformationSheetDTO.setCustFirstName(rs.getString("FIRST_NAME"));
				rptKeyInformationSheetDTO.setMsisdn(rs.getString("MSISDN"));
				return rptKeyInformationSheetDTO;
			}
		};

		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("orderId",orderId);
			list = simpleJdbcTemplate.query(SQL, mapper,params);
			
		} catch (EmptyResultDataAccessException erdae) {
			throw new AppRuntimeException(erdae);
		} catch (Exception e) {
			logger.error("Exception caught in retrieveRptKeyInformationSheetDTO()", e);
			throw new DAOException(e.getMessage(), e);
		}
		return (list == null || list.isEmpty()||list.size()==0) ? null : list.get(0);
	}
	
	public RptCreditCardDDAuthDTO getRptCreditCardDDAuthDTO(String orderId) throws DAOException { 
		logger.debug("getRptCreditCardDDAuthDTO() is called");
		List<RptCreditCardDDAuthDTO> list = new ArrayList<RptCreditCardDDAuthDTO>();
		String SQL="SELECT O.ORDER_ID, O.MSISDN, O.SHOP_CD, O.APP_DATE, O.SALES_CD, "
				+ "P.CC_HOLD_NAME, P.CUST_NAME_CHI, P.CC_TYPE, P.CC_NUM, "
				+ "SUBSTR(P.CC_EXP_DATE, 1, 2) CC_EXP_MONTH, SUBSTR(P.CC_EXP_DATE, 4, 4) CC_EXP_YAER, "
				+ "P.CC_ISSUE_BANK, P.CC_ID_DOC_TYPE, P.CC_ID_DOC_NO, "
				+ "(SELECT BANK_NAME FROM W_ISSUEBANKLKUP CB WHERE CB.BANK_CODE = P.CC_ISSUE_BANK) CC_ISSUE_BANK_NAME, "
				+ "C.COMPANY_NAME, C.LAST_NAME, C.FIRST_NAME, C.ID_DOC_TYPE "
				+ "FROM BOMWEB_ORDER O, "
				+ "BOMWEB_PAYMENT P, "
				+ "BOMWEB_CUSTOMER C "
				+ "WHERE O.ORDER_ID = P.ORDER_ID "
				+ "AND O.ORDER_ID = C.ORDER_ID "
				+ "AND O.ORDER_ID = :orderId ";

		ParameterizedRowMapper<RptCreditCardDDAuthDTO> mapper = new ParameterizedRowMapper<RptCreditCardDDAuthDTO>() {

			public RptCreditCardDDAuthDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				RptCreditCardDDAuthDTO rptCreditCardDDAuthDTO = new RptCreditCardDDAuthDTO();
				rptCreditCardDDAuthDTO.setCreditCardHolderName(rs.getString("CC_HOLD_NAME"));
				rptCreditCardDDAuthDTO.setCustNameChi(rs.getString("CUST_NAME_CHI"));
				rptCreditCardDDAuthDTO.setCreditCardType(rs.getString("CC_TYPE"));
				rptCreditCardDDAuthDTO.setCreditCardNum(rs.getString("CC_NUM"));
				rptCreditCardDDAuthDTO.setCreditExpiryMonth(rs.getString("CC_EXP_MONTH"));
				rptCreditCardDDAuthDTO.setCreditExpiryYear(rs.getString("CC_EXP_YAER"));
				rptCreditCardDDAuthDTO.setCreditCardIssueBank(rs.getString("CC_ISSUE_BANK_NAME"));
				rptCreditCardDDAuthDTO.setCreditCardDocType(rs.getString("CC_ID_DOC_TYPE"));
				rptCreditCardDDAuthDTO.setCreditCardDocNum(rs.getString("CC_ID_DOC_NO"));
				//rptCreditCardDDAuthDTO.setUsername(rs.getString("ORDER_ID"));
				rptCreditCardDDAuthDTO.setCompanyName(rs.getString("COMPANY_NAME"));
				rptCreditCardDDAuthDTO.setShopCd(rs.getString("SHOP_CD"));
				rptCreditCardDDAuthDTO.setMsisdn(rs.getString("MSISDN"));
				//rptCreditCardDDAuthDTO.setContactPhone(rs.getString("CONTACT_PHONE"));
				rptCreditCardDDAuthDTO.setCustLastName(rs.getString("LAST_NAME"));
				rptCreditCardDDAuthDTO.setCustFirstName(rs.getString("FIRST_NAME"));
				rptCreditCardDDAuthDTO.setAgreementNum(rs.getString("ORDER_ID"));
				//rptCreditCardDDAuthDTO.setCompanyNameChi(rs.getString("ORDER_ID"));
				rptCreditCardDDAuthDTO.setIdDocType(rs.getString("ID_DOC_TYPE"));
				rptCreditCardDDAuthDTO.setAppInDate(rs.getDate("APP_DATE"));
				rptCreditCardDDAuthDTO.setUsername(rs.getString("SALES_CD"));

				return rptCreditCardDDAuthDTO;
			}
		};

		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("orderId",orderId);
			list = simpleJdbcTemplate.query(SQL, mapper,params);
			
		} catch (EmptyResultDataAccessException erdae) {
			list = null;
			throw new AppRuntimeException(erdae);
		} catch (Exception e) {
			logger.error("Exception caught in getRptCreditCardDDAuthDTO()", e);
			throw new DAOException(e.getMessage(), e);
		}
		return (list == null || list.isEmpty()) ? null : list.get(0);
	}
	
	public RptRetCourierDeliveryGuidelineDTO retrieveRptRetCourierDeliveryGuidelineDTO(String orderId) throws DAOException { 
		logger.debug("retrieveRptRetCourierDeliveryGuidelineDTO() is called");
		List<RptRetCourierDeliveryGuidelineDTO> list = new ArrayList<RptRetCourierDeliveryGuidelineDTO>();
		String SQL="SELECT BO.ORDER_ID, " 
				+"  SIMNO.SIM_ICCID, " 
				+"  BO.IMEI, " 
				+"  BD.DELIVERY_IND, " 
				+"  TO_CHAR(BD.DELIVERY_DATE, 'DD/MM/YYYY')||' '||DT.TIMEFROM||'-'||DT.TIMETO DELIVERY_DATETIME, " 
				+"  BC.TITLE||' '|| BC.CONTACT_NAME CONTACT_NAME, " 
				+"  BC.CONTACT_PHONE, " 
				+"  BC.OTHER_PHONE, " 
				+"  BC.CONTACT_TYPE, "
				+ " CUST.ID_DOC_NUM, "
				+ " CUST.ID_DOC_TYPE " 
				+"FROM BOMWEB_ORDER BO, " 
				+"  (select M.order_ID, " 
				+"    M.Sim_Iccid " 
				+"  from BOMWEB_ORD_MOB_CHG_SIM_TXN M " 
				+"  where M.ORDER_ID = :orderId " 
				+"  and NVL(M.MARK_DEL_IND, 'N') != 'Y' " 
				+"  and M.CHG_SIM_TXN_ID          = " 
				+"    (select max(A.CHG_SIM_TXN_ID) " 
				+"    FROM BOMWEB_ORD_MOB_CHG_SIM_TXN A " 
				+"    where A.ORDER_ID = :orderId) " 
				+"  )SIMNO, " 
				+"  BOMWEB_DELIVERY BD, " 
				+"  BOMWEB_CONTACT BC, " 
				+"  W_DELIVERY_TIMESLOT DT, "
				+ " BOMWEB_CUSTOMER CUST " 
				+"WHERE BO.ORDER_ID         = SIMNO.ORDER_ID (+) " 
				+"AND BO.ORDER_ID           = BD.ORDER_ID " 
				+"AND BO.ORDER_ID           = BC.ORDER_ID "
				+"AND CUST.ORDER_ID         = BO.ORDER_ID " 
				+"AND BC.CONTACT_TYPE IN ('DC', '3C') " 
				+"AND BD.DELIVERY_TIME_SLOT = DT.TIMESLOT " 
				+"AND DT.SLOT_TYPE          = DECODE(BD.URGENT_IND, 'Y', 'DED', 'SCH') " 
				+"AND BO.ORDER_ID           = :orderId ";

		ParameterizedRowMapper<RptRetCourierDeliveryGuidelineDTO> mapper = new ParameterizedRowMapper<RptRetCourierDeliveryGuidelineDTO>() {

			public RptRetCourierDeliveryGuidelineDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				RptRetCourierDeliveryGuidelineDTO rptRetCourierDeliveryGuidelineDTO = new RptRetCourierDeliveryGuidelineDTO();
				rptRetCourierDeliveryGuidelineDTO.setOrderId(rs.getString("ORDER_ID"));
				rptRetCourierDeliveryGuidelineDTO.setIccid(rs.getString("SIM_ICCID"));
				rptRetCourierDeliveryGuidelineDTO.setImei(rs.getString("IMEI"));
				rptRetCourierDeliveryGuidelineDTO.setDeliveryDayStr(rs.getString("DELIVERY_DATETIME"));
				rptRetCourierDeliveryGuidelineDTO.setDeliveryContactName(rs.getString("CONTACT_NAME"));
				if (StringUtils.isNotBlank(rs.getString("OTHER_PHONE"))) {
					rptRetCourierDeliveryGuidelineDTO.setDeliveryContactNum(rs.getString("CONTACT_PHONE") + " / " + rs.getString("OTHER_PHONE"));
				} else {
					rptRetCourierDeliveryGuidelineDTO.setDeliveryContactNum(rs.getString("CONTACT_PHONE"));
				}
				rptRetCourierDeliveryGuidelineDTO.setContactType(rs.getString("CONTACT_TYPE"));
				
				if ("D".equalsIgnoreCase(rs.getString("DELIVERY_IND"))) {
					rptRetCourierDeliveryGuidelineDTO.setThroughDelivery(true);
					rptRetCourierDeliveryGuidelineDTO.setThroughPickUp(false);
				} else { //"P"
					rptRetCourierDeliveryGuidelineDTO.setThroughDelivery(false);
					rptRetCourierDeliveryGuidelineDTO.setThroughPickUp(true);
				}
				
				rptRetCourierDeliveryGuidelineDTO.setMnpApplicationForm(false);
				rptRetCourierDeliveryGuidelineDTO.setSsFormRenewalInd(true);
				
				rptRetCourierDeliveryGuidelineDTO.setMnpInd(false);
				rptRetCourierDeliveryGuidelineDTO.setCustIdDocNum(rs.getString("ID_DOC_NUM"));
				rptRetCourierDeliveryGuidelineDTO.setCustIdDocType(rs.getString("ID_DOC_TYPE"));
				
				return rptRetCourierDeliveryGuidelineDTO;
			}
		};

		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("orderId",orderId);
			list = simpleJdbcTemplate.query(SQL, mapper,params);
			
		} catch (EmptyResultDataAccessException erdae) {
			list = null;
			throw new AppRuntimeException(erdae);
		} catch (Exception e) {
			logger.error("Exception caught in retrieveRptRetCourierDeliveryGuidelineDTO()", e);
			throw new DAOException(e.getMessage(), e);
		}
		
		if (list == null || list.isEmpty()) {
			return null;
		} else {
			for (RptRetCourierDeliveryGuidelineDTO dto : list) {
				if ("3C".equalsIgnoreCase(dto.getContactType())) {
					return dto;
				}
			}
		}
		
		return list.get(0);
	}
	
	public RptSecretarialServDTO retrieveRptSecretarialServDTO(String orderId) throws DAOException { 
		logger.debug("retrieveRptSecretarialServDTO() is called");
		List<RptSecretarialServDTO> list = new ArrayList<RptSecretarialServDTO>();
		String SQL="SELECT "
				+ "   BO.ORDER_ID, "
				+ "   BO.MSISDN, "
				+ "   BO.APP_DATE, "
				+ "   BO.SRV_REQ_DATE, "
				+ "   BO.SALES_CD, "
				+ "   BO.SHOP_CD, "
				+ "   BC.TITLE, "
				+ "   BC.LAST_NAME, "
				+ "   BC.FIRST_NAME, "
				+ "   BC.COMPANY_NAME, "
				+ "   DECODE(BC.ID_DOC_TYPE, 'PASS', 'Passport', BC.ID_DOC_TYPE) ID_DOC_TYPE, "
				+ "   DECODE(BS.SMS_LANG, "
				+ "            '00', 'TRADITIONAL CHINESE', "
				+ "            '01', 'SIMPLIFIED CHINESE', "
				+ "            '02', 'ENGLISH') SMS_LANG "
				+ "FROM BOMWEB_ORDER BO, "
				+ "     BOMWEB_CUSTOMER BC, "
				+ "     BOMWEB_SUB BS "
				+ "WHERE BO.ORDER_ID = BC.ORDER_ID "
				+ "AND BO.ORDER_ID = BS.ORDER_ID "
				+ "AND BO.ORDER_ID = :orderId";

		ParameterizedRowMapper<RptSecretarialServDTO> mapper = new ParameterizedRowMapper<RptSecretarialServDTO>() {

			public RptSecretarialServDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				RptSecretarialServDTO rptSecretarialServDTO = new RptSecretarialServDTO();
				rptSecretarialServDTO.setAgreementNum(rs.getString("ORDER_ID"));
				rptSecretarialServDTO.setMsisdn(rs.getString("MSISDN"));
				rptSecretarialServDTO.setAppInDate(rs.getDate("APP_DATE"));
				rptSecretarialServDTO.setSrvReqDate(rs.getDate("SRV_REQ_DATE"));
				rptSecretarialServDTO.setSalesCd(rs.getString("SALES_CD"));
				rptSecretarialServDTO.setShopCode(rs.getString("SHOP_CD"));
				rptSecretarialServDTO.setTitle(rs.getString("TITLE"));
				rptSecretarialServDTO.setCustLastName(rs.getString("LAST_NAME"));
				rptSecretarialServDTO.setCustFirstName(rs.getString("FIRST_NAME"));
				rptSecretarialServDTO.setCompanyName(rs.getString("COMPANY_NAME"));
				rptSecretarialServDTO.setIdDocType(rs.getString("ID_DOC_TYPE"));
				rptSecretarialServDTO.setSmsBillLanguage(rs.getString("SMS_LANG"));
				return rptSecretarialServDTO;
			}
		};

		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("orderId",orderId);
			list = simpleJdbcTemplate.query(SQL, mapper,params);
			
		} catch (EmptyResultDataAccessException erdae) {
			list = null;
			throw new AppRuntimeException(erdae);
		} catch (Exception e) {
			logger.error("Exception caught in retrieveRptSecretarialServDTO()", e);
			throw new DAOException(e.getMessage(), e);
		}
		return (list == null || list.isEmpty()) ? null : list.get(0);
	}
	
	public RptNFCConsentDTO retrieveRptNFCConsentDTO(String orderId) throws DAOException { 
		logger.debug("retrieveRptNFCConsentDTO() is called");
		List<RptNFCConsentDTO> list = new ArrayList<RptNFCConsentDTO>();
		String SQL="SELECT "
				+ "   BO.ORDER_ID, "
				+ "   BO.MSISDN, "
				+ "   BO.APP_DATE, "
				+ "   BC.TITLE, "
				+ "   BC.LAST_NAME, "
				+ "   BC.FIRST_NAME "
				+ "FROM BOMWEB_ORDER BO, "
				+ "     BOMWEB_CUSTOMER BC "
				+ "WHERE BO.ORDER_ID = BC.ORDER_ID "
				+ "AND BO.ORDER_ID = :orderId";

		ParameterizedRowMapper<RptNFCConsentDTO> mapper = new ParameterizedRowMapper<RptNFCConsentDTO>() {

			public RptNFCConsentDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				RptNFCConsentDTO rptNFCConsentDTO = new RptNFCConsentDTO();
				rptNFCConsentDTO.setOrderId(rs.getString("ORDER_ID"));
				rptNFCConsentDTO.setMsisdn(rs.getString("MSISDN"));
				rptNFCConsentDTO.setAppInDate(rs.getDate("APP_DATE"));
				rptNFCConsentDTO.setTitle(rs.getString("TITLE"));
				rptNFCConsentDTO.setCustLastName(rs.getString("LAST_NAME"));
				rptNFCConsentDTO.setCustFirstName(rs.getString("FIRST_NAME"));;
				return rptNFCConsentDTO;
			}
		};

		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("orderId",orderId);
			list = simpleJdbcTemplate.query(SQL, mapper,params);
			
		} catch (EmptyResultDataAccessException erdae) {
			list = null;
			throw new AppRuntimeException(erdae);
		} catch (Exception e) {
			logger.error("Exception caught in retrieveRptNFCConsentDTO()", e);
			throw new DAOException(e.getMessage(), e);
		}
		return (list == null || list.isEmpty()) ? null : list.get(0);
	}
	
	public RptOctopusConsentDTO retrieveRptOctopusConsentDTO(String orderId) throws DAOException { 
		logger.debug("retrieveRptOctopusConsentDTO() is called");
		List<RptOctopusConsentDTO> list = new ArrayList<RptOctopusConsentDTO>();
		String SQL="SELECT "
				+ "   BO.ORDER_ID, "
				+ "   BO.MSISDN, "
				+ "   BO.APP_DATE, "
				+ "   BC.TITLE, "
				+ "   BC.LAST_NAME, "
				+ "   BC.FIRST_NAME "
				+ "FROM BOMWEB_ORDER BO, "
				+ "     BOMWEB_CUSTOMER BC "
				+ "WHERE BO.ORDER_ID = BC.ORDER_ID "
				+ "AND BO.ORDER_ID = :orderId";

		ParameterizedRowMapper<RptOctopusConsentDTO> mapper = new ParameterizedRowMapper<RptOctopusConsentDTO>() {

			public RptOctopusConsentDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				RptOctopusConsentDTO rptOctopusConsentDTO = new RptOctopusConsentDTO();
				rptOctopusConsentDTO.setOrderId(rs.getString("ORDER_ID"));
				rptOctopusConsentDTO.setMsisdn(rs.getString("MSISDN"));
				rptOctopusConsentDTO.setAppInDate(rs.getDate("APP_DATE"));
				rptOctopusConsentDTO.setTitle(rs.getString("TITLE"));
				rptOctopusConsentDTO.setCustLastName(rs.getString("LAST_NAME"));
				rptOctopusConsentDTO.setCustFirstName(rs.getString("FIRST_NAME"));;
				return rptOctopusConsentDTO;
			}
		};

		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("orderId",orderId);
			list = simpleJdbcTemplate.query(SQL, mapper,params);
			
		} catch (EmptyResultDataAccessException erdae) {
			list = null;
			throw new AppRuntimeException(erdae);
		} catch (Exception e) {
			logger.error("Exception caught in retrieveRptOctopusConsentDTO()", e);
			throw new DAOException(e.getMessage(), e);
		}
		return (list == null || list.isEmpty()) ? null : list.get(0);
	}
	
	public RptPrintIndDTO retrieveAdditionalServiceInd(String orderId) throws DAOException {
		logger.debug("retrieveAdditionalServiceInd() is called");
		String SQL = "SELECT A.GRP_ID "
					+ "FROM W_ITEM_SELECTION_GRP_ASSGN A, "
					+ "     BOMWEB_SUBSCRIBED_ITEM B "
					+ "WHERE A.ITEM_ID = B.ID "
					+ "AND B.ORDER_ID = :orderId";

		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {

			public String mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				
				return rs.getString("GRP_ID");
			}
		};

		RptPrintIndDTO dto = new RptPrintIndDTO();
		
		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("orderId", orderId);
			List<String> list = simpleJdbcTemplate.query(SQL, mapper,params);
			if (list == null || list.isEmpty()) {
				return dto;
			} else {
				for (String grpId : list) {
					if ("100000103".equals(grpId)) {
						dto.setHasMobileSafePhoneSafety(true);
					} else if ("100000004".equals(grpId)) {
						dto.setHasHandsetTradeIn(true);
					} else if ("100000005".equals(grpId)) {
						dto.setHasSecretarialService(true);
					} else if ("6666666666".equals(grpId)) {
						dto.setiGuardLDS(true);
					} else if ("6666666667".equals(grpId)) {
						dto.setiGuardAD(true);
					}else if ("6666666669".equals(grpId)) {
						dto.setiGuardUAD(true);
					} else if (GenericReportHelper.TRAVEL_INSURANCE_FORM_ITEM_SELECTTION_GROUP_ID.equals(grpId)) {
						dto.setHasTravelInsurance(true);
					} else if (GenericReportHelper.HELPERCARE_INSURANCE_FORM_ITEM_SELECTTION_GROUP_ID.equals(grpId)) {
						dto.setHasHelperCareInsurance(true);
					} else if (ProjectEagleReportHelper.ITEM_SELECTTION_GROUP_ID.equals(grpId)) {
						dto.setHasProjectEagleInsurance(true);
					}
				}
				return dto;
			}
			
		} catch (EmptyResultDataAccessException erdae) {
			return dto;
		} catch (Exception e) {
			logger.error("Exception caught in retrieveAdditionalServiceInd()", e);
			throw new DAOException(e.getMessage(), e);
		}
		
	}
	
	public RptTnGServiceFormDTO retrieveRptTnGServiceFormDTO(String orderId) throws DAOException { 
		logger.debug("retrieveRptTnGServiceFormDTO() is called");
		List<RptTnGServiceFormDTO> list = new ArrayList<RptTnGServiceFormDTO>();
		String SQL="SELECT "
				+ "   BO.ORDER_ID, "
				+ "   BO.APP_DATE, "
				+ "   BC.ID_DOC_NUM, "
				+ "   BC.TITLE, "
				+ "   BC.LAST_NAME, "
				+ "   BC.FIRST_NAME "
				+ "FROM BOMWEB_ORDER BO, "
				+ "     BOMWEB_CUSTOMER BC "
				+ "WHERE BO.ORDER_ID = BC.ORDER_ID "
				+ "AND BO.ORDER_ID = :orderId";

		ParameterizedRowMapper<RptTnGServiceFormDTO> mapper = new ParameterizedRowMapper<RptTnGServiceFormDTO>() {

			public RptTnGServiceFormDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				RptTnGServiceFormDTO rptTnGServiceFormDTO = new RptTnGServiceFormDTO();
				rptTnGServiceFormDTO.setOrderId(rs.getString("ORDER_ID"));
				rptTnGServiceFormDTO.setCustName(rs.getString("TITLE")+ " " +rs.getString("LAST_NAME")+ " " +rs.getString("FIRST_NAME"));
				rptTnGServiceFormDTO.setIdDocNum(rs.getString("ID_DOC_NUM"));
				rptTnGServiceFormDTO.setAppInDate(rs.getDate("APP_DATE"));
				rptTnGServiceFormDTO.setAppInDateStr(Utility.date2String(rs.getTimestamp("APP_DATE"), "dd/MM/yyyy"));

				return rptTnGServiceFormDTO;
			}
		};

		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("orderId",orderId);
			list = simpleJdbcTemplate.query(SQL, mapper,params);
			
		} catch (EmptyResultDataAccessException erdae) {
			list = null;
			throw new AppRuntimeException(erdae);
		} catch (Exception e) {
			logger.error("Exception caught in retrieveRptTnGServiceFormDTO()", e);
			throw new DAOException(e.getMessage(), e);
		}
		return (list == null || list.isEmpty()) ? null : list.get(0);
	}
	
	public IPhoneTradeInFormDTO retrieveIPhoneTradeInFormDTO(String orderId) throws DAOException { 
		logger.debug("retrieveRptTnGServiceFormDTO() is called");
		List<IPhoneTradeInFormDTO> list = new ArrayList<IPhoneTradeInFormDTO>();
		String SQL="SELECT o.msisdn, " +
				"  nvl(o.imei,'') imei, " +
				"  o.srv_req_date , " +
				"  TC.APPEND_TC_START_DATE , " +
				"  C.TITLE , " +
				"  C.FIRST_NAME , " +
				"  C.LAST_NAME , " +
				"  o.ACCT_NO  " +
				"FROM bomweb_order o , " +
				"  (SELECT MAX(term_tc_date)+ 1 APPEND_TC_START_DATE " +
				"  FROM bomweb_ord_mob_cur_prod p, " +
				"    bomweb_ord_mob_cur_tc t, " +
				"    bomweb_order_mob m " +
				"  WHERE m.order_id       = p.order_id " +
				"  AND m.order_id         = t.order_id " +
				"  AND m.order_nature     = 'UPG' " +
				"  AND p.prod_type        = 'T' " +
				"  AND p.io_ind           = ' ' " +
				"  AND p.prod_id          = t.cr_prod_id " +
				"  AND p.cc_offer_sub_key = t.cc_offer_sub_key " +
				"  AND m.order_id         = :orderId " +
				"  )TC, " +
				"  bomweb_customer c " +
				"WHERE c.order_id = o.order_id(+) " +
				"AND o.order_id   = :orderId ";

		ParameterizedRowMapper<IPhoneTradeInFormDTO> mapper = new ParameterizedRowMapper<IPhoneTradeInFormDTO>() {

			public IPhoneTradeInFormDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				IPhoneTradeInFormDTO iPhoneTradeInFormDTO = new IPhoneTradeInFormDTO();
				iPhoneTradeInFormDTO.setCustEngName(rs.getString("TITLE") + " " + rs.getString("LAST_NAME") + " " + rs.getString("FIRST_NAME")) ;
				iPhoneTradeInFormDTO.setImei(rs.getString("imei"));
				iPhoneTradeInFormDTO.setMsisdn(rs.getString("msisdn"));
				iPhoneTradeInFormDTO.setMobCustNum(rs.getString("ACCT_NO"));
				if (rs.getDate("APPEND_TC_START_DATE") == null){
					iPhoneTradeInFormDTO.setServiceReqDate(rs.getDate("srv_req_date"));
				}else{
					iPhoneTradeInFormDTO.setServiceReqDate(rs.getDate("APPEND_TC_START_DATE"));
				}
				return iPhoneTradeInFormDTO;
			}
		};

		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("orderId",orderId);
			list = simpleJdbcTemplate.query(SQL, mapper,params);
			
		} catch (EmptyResultDataAccessException erdae) {
			list = null;
			throw new AppRuntimeException(erdae);
		} catch (Exception e) {
			logger.error("Exception caught in IPhoneTradeInFormDTO()", e);
			throw new DAOException(e.getMessage(), e);
		}
		return (list == null || list.isEmpty()) ? null : list.get(0);
	}
	
	public boolean hasThirdPartyAutopayViaCreditCard(String orderId) throws DAOException { 
		logger.debug("hasThirdPartyAutopayViaCreditCard() is called");
		List<PaymentDTO> list = new ArrayList<PaymentDTO>();
		String SQL="SELECT P.PAY_MTD_TYPE, P.THIRD_PARTY_IND "
				+ "FROM BOMWEB_PAYMENT P "
				+ "WHERE P.ORDER_ID = ? ";

		ParameterizedRowMapper<PaymentDTO> mapper = new ParameterizedRowMapper<PaymentDTO>() {

			public PaymentDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				PaymentDTO paymentDTO = new PaymentDTO();
				paymentDTO.setPayMethodType(rs.getString("PAY_MTD_TYPE"));
				paymentDTO.setThirdPartyInd(rs.getString("THIRD_PARTY_IND"));
				
				return paymentDTO;
			}
		};

		try {
			list = simpleJdbcTemplate.query(SQL, mapper,orderId);
			
		} catch (EmptyResultDataAccessException erdae) {
			throw new AppRuntimeException(erdae);
		} catch (Exception e) {
			logger.error("Exception caught in hasThirdPartyAutopayViaCreditCard()", e);
			throw new DAOException(e.getMessage(), e);
		}
		
		if (list == null || list.isEmpty()) {
			return false;
		} else if ("C".equalsIgnoreCase(list.get(0).getPayMethodType()) 
				&& "Y".equalsIgnoreCase(list.get(0).getThirdPartyInd())) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean hasWarmTips(String orderId) throws DAOException { 
		logger.debug("hasWarmTips() is called");
		List<String> list = new ArrayList<String>();
		String SQL = "SELECT ORDER_ID "
					+ "FROM BOMWEB_ORD_MOB_CUR_OFFER BOMCO, W_MOB_2G_RP_LKUP RL "
					+ "WHERE BOMCO.OFFER_TYPE = 'R' "
					+ "AND BOMCO.OFFER_ID = RL.OFFER_ID "
					+ "AND BOMCO.ORDER_ID = :orderId ";

		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {

			public String mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				String result = null;
				result = rs.getString("ORDER_ID");
				
				return result;
			}
		};

		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("orderId", orderId);
			list = simpleJdbcTemplate.query(SQL, mapper,params);
			
		} catch (EmptyResultDataAccessException erdae) {
			throw new AppRuntimeException(erdae);
		} catch (Exception e) {
			logger.error("Exception caught in hasWarmTips()", e);
			throw new DAOException(e.getMessage(), e);
		}
		
		return (list == null || list.isEmpty()) ? false : true;
	}
	
	public boolean hasDeliveryItem(String orderId) throws DAOException { 
		logger.debug("hasDeliveryItem() is called");
		List<String> list = new ArrayList<String>();
		String SQL="SELECT ORDER_ID FROM BOMWEB_DELIVERY WHERE ORDER_ID = :orderId AND DELIVERY_IND IS NOT NULL ";

		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {

			public String mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				String result = null;
				result = rs.getString("ORDER_ID");
				
				return result;
			}
		};

		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("orderId", orderId);
			list = simpleJdbcTemplate.query(SQL, mapper,params);
			
		} catch (EmptyResultDataAccessException erdae) {
			throw new AppRuntimeException(erdae);
		} catch (Exception e) {
			logger.error("Exception caught in hasDeliveryItem()", e);
			throw new DAOException(e.getMessage(), e);
		}
		
		return (list == null || list.isEmpty()) ? false : true;
	}
	
	public boolean hasDoaRequest(String orderId) throws DAOException { 
		logger.debug("hasDoaRequest() is called");
		List<Integer> list = new ArrayList<Integer>();
		String SQL="SELECT R.SEQ_NO " +
				   "FROM BOMWEB_ORDER O JOIN BOMWEB_DOA_REQUEST R " +
				   "ON O.ORDER_ID = R.ORDER_ID " +
				   "WHERE O.ORDER_ID = :orderId " +
				   "AND O.REASON_CD LIKE 'N%' " +
				   "AND R.DOA_TYPE = 'DOA' " +
				   "AND R.STATUS != '03' " +
				   "ORDER BY SEQ_NO";

		ParameterizedRowMapper<Integer> mapper = new ParameterizedRowMapper<Integer>() {

			public Integer mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				int result = rs.getInt("SEQ_NO");
				return result;
			}
		};

		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("orderId", orderId);
			list = simpleJdbcTemplate.query(SQL, mapper, params);
			
		} catch (EmptyResultDataAccessException erdae) {
			return false;
		} catch (Exception e) {
			logger.error("Exception caught in hasDoaRequest()", e);
			throw new DAOException(e.getMessage(), e);
		}
		
		return (CollectionUtils.isEmpty(list)) ? false : (list.get(0) > 0);
	}
	
	public RptRetConfirmForCopDTO retrieveRptRetConfirmForCopDTO(String orderId) throws DAOException {
		logger.debug("retrieveRptRetConfirmForCopDTO() is called");
		List<RptRetConfirmForCopDTO> rptRetConfirmForCopDTOList = new ArrayList<RptRetConfirmForCopDTO>();
		
			String SQL ="SELECT O.APP_DATE "								 
							+" FROM BOMWEB_ORDER O " 						 
							+" WHERE O.ORDER_ID   = :orderId";
		
		ParameterizedRowMapper<RptRetConfirmForCopDTO> mapper = new ParameterizedRowMapper<RptRetConfirmForCopDTO>() {
			public RptRetConfirmForCopDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				RptRetConfirmForCopDTO rptRetConfirmForCopDTO = new RptRetConfirmForCopDTO();
				rptRetConfirmForCopDTO.setAppInDate(rs.getDate("APP_DATE"));
				return rptRetConfirmForCopDTO;
			}
		};
		
		try {
			logger.debug("retrieveRptRetConfirmForCopDTO() @ MobCosRptDAO: " + SQL);
			logger.info("retrieveRptRetConfirmForCopDTO() @ MobCosRptDAO: ");
			
			rptRetConfirmForCopDTOList = simpleJdbcTemplate.query(SQL, mapper, orderId);
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException");
		
			rptRetConfirmForCopDTOList = null;
		} catch (Exception e) {
			logger.info("Exception caught in retrieveRptRetConfirmForCopDTO():", e);
			throw new DAOException(e.getMessage(), e);
		}
		if (rptRetConfirmForCopDTOList.isEmpty())
			return null;
		else
			return rptRetConfirmForCopDTOList.get(0);// only return the first one
		
		}

	public RptRetCosDTO retrieveCosCustDetailDTO(String orderId)
			throws DAOException {
			logger.debug("retrieveCosCustDetailDTO() is called");
			List<RptRetCosDTO> rptRetCosDTOList = new ArrayList<RptRetCosDTO>();
			
				String SQL ="select O.ORDER_ID , " 
						+"  O.MSISDN , " 
						+"  C.ID_DOC_TYPE , " 
						+"  C.ID_DOC_NUM , " 
						+"  C.COMPANY_NAME , " 
						+"  C.TITLE , " 
						+"  C.FIRST_NAME , " 
						+"  C.LAST_NAME , " 
						+"  O.APP_DATE , " 
						+"  O.SRV_REQ_DATE , " 
						+"  A.ACCT_NO " 
						+"from BOMWEB_ORDER O , " 
						+"  BOMWEB_CUSTOMER C , " 
						+"  BOMWEB_ACCT A " 
						+"WHERE O.ORDER_ID = :orderId " 
						+"and O.ORDER_ID   = C.ORDER_ID(+) " 
						+"AND O.ORDER_ID   = A.ORDER_ID(+) " ;
			
			ParameterizedRowMapper<RptRetCosDTO> mapper = new ParameterizedRowMapper<RptRetCosDTO>() {
			public RptRetCosDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				RptRetCosDTO rptRetCosDTO = new RptRetCosDTO();

				// "MA"rs.getString("ADDR_USAGE"));//alway MA
				rptRetCosDTO.setOrderId(rs.getString("ORDER_ID"));
				rptRetCosDTO.setMsisdn(rs.getString("MSISDN"));
				rptRetCosDTO.setIdDocNum(rs.getString("ID_DOC_NUM"));
				//rptRetCosDTO.setIdDocType(rs.getString("ID_DOC_TYPE"));
				if ("PASS".equals(rs.getString("ID_DOC_TYPE"))) {
					rptRetCosDTO.setIdDocType("Passport:");
					rptRetCosDTO.setCustomerNameLabelDisp("Customer Name:");
					rptRetCosDTO.setCustomerName(rs.getString("TITLE")
									+ " " + rs.getString("LAST_NAME")
									+ " " + rs.getString("FIRST_NAME"));

				} else if ("BS".equals(rs.getString("ID_DOC_TYPE"))) {
					rptRetCosDTO.setIdDocType("BR No.:");
					rptRetCosDTO.setCustomerNameLabelDisp("Company Name:");
					rptRetCosDTO.setCustomerName(rs.getString("COMPANY_NAME"));

				} else {
					rptRetCosDTO.setIdDocType("HKID:");
					rptRetCosDTO.setCustomerNameLabelDisp("Customer Name:");
					rptRetCosDTO.setCustomerName(rs.getString("TITLE")
							+ " " + rs.getString("LAST_NAME")
							+ " " + rs.getString("FIRST_NAME"));
				}
				rptRetCosDTO.setAppInDateStr(Utility.date2String(rs.getTimestamp("APP_DATE"), "dd/MM/yyyy"));
				rptRetCosDTO.setServiceReqDate(Utility.date2String(rs.getTimestamp("SRV_REQ_DATE"), "dd/MM/yyyy"));
				rptRetCosDTO.setAccNo(rs.getString("ACCT_NO"));

				return rptRetCosDTO;
			}
		};
		
		try {
			//herbert 20111110 - remove useless SQL logger
			logger.debug("retrieveCosCustDetailDTO() @ MobCosRptDAO: " + SQL);
			
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("orderId",orderId);

			rptRetCosDTOList = simpleJdbcTemplate.query(SQL, mapper,params);

		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException");
		
			rptRetCosDTOList = null;
		} catch (Exception e) {
			logger.info("Exception caught in retrieveCosCustDetailDTO():", e);
			throw new DAOException(e.getMessage(), e);
		}
		if (rptRetCosDTOList.isEmpty()){
			
			return null;
		} else {
			return rptRetCosDTOList.get(0);// only return the first one
		}
	}
	
	public List<VasDetailDTO> retrieveCosRPDetail(String orderId,String basketId,String locale)
			throws DAOException {
		List<VasDetailDTO> rpDetailList = new ArrayList<VasDetailDTO>();


		String SQL = " select idl.item_id, \n"
				+ " 	       i.lob item_lob,  \n"
				+ " 	       i.type item_type,  \n"
				+ " 	       bia.mdo_ind item_mdo_ind,  \n"
				+ " 	       idl.html,  \n"
				+ " 	       idl.html2,  \n"
				+ " 	       idl.locale,  \n"
				+ " 	       idl.display_type,  \n"
				+ " 	       ip.onetime_amt,  \n"
				+ " 	       ip.recurrent_amt,  \n"
				+ " 	       bia.display_seq  \n"
				+ " 	  from (select bwsi.order_id,  \n"
				+ " 	               item_id,  \n"
				+ " 	               display_type,  \n"
				+ " 	               html,  \n"
				+ " 	               html2,  \n"
				+ " 	               locale \n"
				+ " 	          from w_item_display_lkup widl, bomweb_subscribed_item bwsi  \n"
				+ " 	         where widl.item_id = bwsi.id  \n"
				+ " 	           AND bwsi.order_id = :orderId \n"
				+ " 	           and widl.display_type = 'SS_FORM_RP'  \n"
				+ " 	           and widl.locale = :locale) idl, ------------ \n"
				+ " 	       w_basket_item_assgn bia,  \n"
				+ " 	       w_item i,  \n"
				+ " 	       w_item_pricing ip,  \n"
				+ " 	       bomweb_order bwo  \n"
				+ " 	 where bwo.order_id = idl.order_id  \n"
				+ " 	   and idl.item_id = bia.item_id(+)  \n"
				+ " 	   and bia.basket_id(+) = :basketId ------------ \n"
				+ " 	   and idl.item_id = i.id  \n"
				+ " 	   and i.id = ip.id  \n"
				+ "        and trunc(bwo.app_date) between ip.eff_start_date and nvl(ip.eff_end_date, sysdate) \n"
				+ " 	 order by bia.display_seq \n";

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

		try {
			logger.debug("getBrandList @ HandsetModelDTO: " + SQL);
			logger.debug(rpDetailList.size());
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("orderId",orderId);
			params.addValue("basketId",basketId);
			params.addValue("locale",locale);
			rpDetailList = simpleJdbcTemplate.query(SQL, mapper,params);

		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException");
			rpDetailList = null;
		} catch (Exception e) {
			logger.error("Exception caught in retrieveCosRPDetail()", e);
			throw new DAOException(e.getMessage(), e);
		}
		if (rpDetailList.isEmpty())
			return null;
		else
			return rpDetailList;// only return the first one
		

	}
	
	public List<MobCcsSupportDocDTO> getAllSupportDocList(String orderId) throws DAOException {
		logger.debug("getAllSupportDocList() is called");
		List<MobCcsSupportDocDTO> list = new ArrayList<MobCcsSupportDocDTO>();
		String SQL = "SELECT DOC_ID, REC_IND "
				+ "FROM BOMWEB_SUPPORT_DOC "
				+ "WHERE ORDER_ID = :orderId "
				+ "ORDER BY DOC_ID";

		ParameterizedRowMapper<MobCcsSupportDocDTO> mapper = new ParameterizedRowMapper<MobCcsSupportDocDTO>() {

			public MobCcsSupportDocDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				MobCcsSupportDocDTO dto = new MobCcsSupportDocDTO();
				dto.setDocId(rs.getString("DOC_ID"));
				dto.setReceivedByFax("Y".equalsIgnoreCase(rs.getString("REC_IND")));
				
				return dto;
			}
		};

		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("orderId", orderId);
			list = simpleJdbcTemplate.query(SQL, mapper,params);
			
		} catch (EmptyResultDataAccessException erdae) {
			throw new AppRuntimeException(erdae);
		} catch (Exception e) {
			logger.error("Exception caught in getAllSupportDocList()", e);
			throw new DAOException(e.getMessage(), e);
		}
		
		return (list == null || list.isEmpty()) ? null : list;
	}
	
	public String[] getCreditCardAutoPayNumOfSign(String orderId) throws DAOException {
		logger.debug("getAllSupportDocList() is called");
		List<String[]> list = new ArrayList<String[]>();
		String SQL = "SELECT W.BANK_NAME_CHI, W.NO_OF_SIGNATURE FROM "
				+ "BOMWEB_UPFRONT_PAYMENT P, "
				+ "W_INST_ISSUEBANKLKUP W "
				+ "WHERE P.ORDER_ID=:orderId "
				+ "AND P.CC_ISSUE_BANK = W.BANK_CODE "
				+ "AND P.PAY_MTD_TYPE='I' "
				+ "AND P.THIRD_PARTY_IND = 'N' "
				+ "AND P.CC_ISSUE_BANK IN (SELECT DISTINCT CODE_ID FROM BOMWEB_CODE_LKUP WHERE CODE_TYPE='INST_FORM_BANK')";

		ParameterizedRowMapper<String[]> mapper = new ParameterizedRowMapper<String[]>() {

			public String[] mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				String[] result = new String[2];
				result[0] = rs.getString("BANK_NAME_CHI");
				result[1] = rs.getString("NO_OF_SIGNATURE");
				return result;
			}
		};

		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("orderId", orderId);
			list = simpleJdbcTemplate.query(SQL, mapper,params);
			
		} catch (EmptyResultDataAccessException erdae) {
			throw new AppRuntimeException(erdae);
		} catch (Exception e) {
			logger.error("Exception caught in getCreditCardAutoPayNumOfSign()", e);
			throw new DAOException(e.getMessage(), e);
		}
		
		return (list == null || list.isEmpty()) ? null : list.get(0);
	}
	
	public RptConciergeServiPadiPhoneDTO retrieveRptConciergeServiPadiPhoneDTO(String orderId, String locale) throws DAOException { 
		logger.debug("retrieveRptConciergeServiPadiPhoneDTO() is called");
		List<RptConciergeServiPadiPhoneDTO> list = new ArrayList<RptConciergeServiPadiPhoneDTO>();
		String SQL="SELECT "
				+ "BO.APP_DATE, "
				+ "BC.FIRST_NAME, "
				+ "BC.LAST_NAME, "
				+ "BO.MSISDN, "
				+ "BO.IMEI, "
				+ "(SELECT MODEL_DL.DESCRIPTION "
				+ "   FROM W_DISPLAY_LKUP MODEL_DL "
				+ "   WHERE MODEL_DL.ID = BAMV.MODEL_ID "
				+ "   AND MODEL_DL.TYPE = 'MODEL' "
				+ "   AND MODEL_DL.LOCALE = :locale) MODEL_NAME, "
				+ "(SELECT COLOR_DL.DESCRIPTION "
				+ "   FROM W_DISPLAY_LKUP COLOR_DL "
				+ "   WHERE COLOR_DL.ID = BAMV.COLOR_ID "
				+ "   AND COLOR_DL.TYPE = 'COLOR' "
				+ "   AND COLOR_DL.LOCALE = :locale) COLOR_NAME, "
				+ "(SELECT NVL(HDL.ORIGINAL_PRICE, 0) "
				+ "   FROM W_HS_DISPLAY_LKUP HDL "
				+ "   WHERE HDL.BRAND_ID = BAMV.BRAND_ID "
				+ "   AND HDL.MODEL_ID = BAMV.MODEL_ID "
				+ "   AND HDL.LOCALE = :locale "
				+ "   AND HDL.DISPLAY_TYPE = 'DETAIL') ORIGINAL_PRICE "
				+ "FROM BOMWEB_ORDER BO, "
				+ "BOMWEB_CUSTOMER BC, "
				+ "W_BASKET_ATTRIBUTE_MV BAMV "
				+ "WHERE BO.ORDER_ID = BC.ORDER_ID "
				+ "AND BO.ORDER_ID = :orderId "
				+ "AND BAMV.BASKET_ID = (SELECT DISTINCT BASKET_ID FROM BOMWEB_SUBSCRIBED_ITEM WHERE ORDER_ID = BO.ORDER_ID)";

		ParameterizedRowMapper<RptConciergeServiPadiPhoneDTO> mapper = new ParameterizedRowMapper<RptConciergeServiPadiPhoneDTO>() {

			public RptConciergeServiPadiPhoneDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				RptConciergeServiPadiPhoneDTO rptConciergeServiPadiPhoneDTO = new RptConciergeServiPadiPhoneDTO();
				rptConciergeServiPadiPhoneDTO.setAppInDate(rs.getDate("APP_DATE"));
				rptConciergeServiPadiPhoneDTO.setCustFirstName(rs.getString("FIRST_NAME"));
				rptConciergeServiPadiPhoneDTO.setCustLastName(rs.getString("LAST_NAME"));
				rptConciergeServiPadiPhoneDTO.setMsisdn(rs.getString("MSISDN"));
				rptConciergeServiPadiPhoneDTO.setImei(rs.getString("IMEI"));
				rptConciergeServiPadiPhoneDTO.setModelName(rs.getString("MODEL_NAME"));
				rptConciergeServiPadiPhoneDTO.setColorName(rs.getString("COLOR_NAME"));
				rptConciergeServiPadiPhoneDTO.setOriginalPrice(rs.getString("ORIGINAL_PRICE"));
				return rptConciergeServiPadiPhoneDTO;
			}
		};

		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("orderId",orderId);
			params.addValue("locale",locale);
			list = simpleJdbcTemplate.query(SQL, mapper,params);
			
		} catch (EmptyResultDataAccessException erdae) {
			list = null;
			throw new AppRuntimeException(erdae);
		} catch (Exception e) {
			logger.error("Exception caught in retrieveRptConciergeServiPadiPhoneDTO()", e);
			throw new DAOException(e.getMessage(), e);
		}
		return (list == null || list.isEmpty()) ? null : list.get(0);
	}
	
	public RptRetCosDeliveryNoteDTO retrieveCosDeliveryNoteCustDTO(String orderId) throws DAOException {
		logger.debug("retrieveCosDeliveryNoteCustDTO() is called");
		List<RptRetCosDeliveryNoteDTO> rptRetCosDeliveryNoteDTOList = new ArrayList<RptRetCosDeliveryNoteDTO>();
		
			String SQL ="SELECT O.ORDER_ID, " 
					+"  O.APP_DATE, " 
					+"  O.MSISDN, "
					+"  DC.CONTACT_TYPE, " 
					+"  DC.CONTACT_NAME, " 
					+"  DC.TITLE, " 
					+"  DC.CONTACT_PHONE, " 
					+"  DC.OTHER_PHONE, " 
					+"  BSI.SALES_NAME, " 
					+"  BSI.SALES_CD, " 
					+"  BSI.CONTACT_PHONE SCONTACT_PHONE, " 
					+"  BA.BASKET_TYPE, " 
					+"  (select acct.brand from bomweb_acct acct where acct.order_id = :orderId) BRAND, " 
					+"  (select a.HOTLINE from bomweb_shop a "  
					+"	LEFT JOIN bomweb_order b on (a.SHOP_CD=b.SHOP_CD) " 
					+"  where b.ORDER_ID = :orderId ) HotLine, " 
					+"  (select a.HOTLINE_1010 from bomweb_shop a "  
					+"	LEFT JOIN bomweb_order b on (a.SHOP_CD=b.SHOP_CD) " 
					+"  where b.ORDER_ID = :orderId ) HotLine1010 " 
					+"FROM BOMWEB_ORDER O, " 
					+"  BOMWEB_STAFF_INFO BSI, " 
					+"  (SELECT * FROM BOMWEB_CONTACT M WHERE M.CONTACT_TYPE in ('DC', '3C') " 
					+"  ) DC, " 
					+"  (select distinct b.basket_type " 
					+"  from bomweb_subscribed_item a, " 
					+"    W_BASKET_ATTRIBUTE_MV b " 
					+"  where a.order_id =:orderId " 
					+"  AND A.BASKET_ID IS NOT NULL " 
					+"  and a.basket_id  =b.basket_id " 
					+"  ) BA "
					+"WHERE O.ORDER_ID = :orderId " 
					+"AND O.ORDER_ID   = DC.ORDER_ID(+) " 
					+"and O.ORDER_ID   = BSI.ORDER_ID(+)";
		
		ParameterizedRowMapper<RptRetCosDeliveryNoteDTO> mapper = new ParameterizedRowMapper<RptRetCosDeliveryNoteDTO>() {
			public RptRetCosDeliveryNoteDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				RptRetCosDeliveryNoteDTO rptRetCosDeliveryNoteDTO = new RptRetCosDeliveryNoteDTO();
		

				// "MA"rs.getString("ADDR_USAGE"));//alway MA
				rptRetCosDeliveryNoteDTO.setOrderId(rs.getString("ORDER_ID"));
				rptRetCosDeliveryNoteDTO.setMsisdn(rs.getString("MSISDN"));
				rptRetCosDeliveryNoteDTO.setContactName(rs.getString("CONTACT_NAME"));
				rptRetCosDeliveryNoteDTO.setTitle(rs.getString("TITLE"));
				if (StringUtils.isNotBlank(rs.getString("OTHER_PHONE"))) {
					rptRetCosDeliveryNoteDTO.setContactPhone(rs.getString("CONTACT_PHONE") + " / " + rs.getString("OTHER_PHONE"));
				} else {
					rptRetCosDeliveryNoteDTO.setContactPhone(rs.getString("CONTACT_PHONE"));
				}
				rptRetCosDeliveryNoteDTO.setSalesName(rs.getString("SALES_NAME"));
				rptRetCosDeliveryNoteDTO.setSalesCd(rs.getString("SALES_CD"));
				rptRetCosDeliveryNoteDTO.setsContactPhone(rs.getString("SCONTACT_PHONE"));
				rptRetCosDeliveryNoteDTO.setBasketType(rs.getString("BASKET_TYPE"));
				rptRetCosDeliveryNoteDTO.setAppInDateStr(Utility.date2String(rs.getTimestamp("APP_DATE"), "dd/MM/yyyy"));
				
				rptRetCosDeliveryNoteDTO.setContactType(rs.getString("CONTACT_TYPE"));
				rptRetCosDeliveryNoteDTO.setHotLine(rs.getString("HotLine"));
				if ("0".equals(rs.getString("BRAND"))){
					rptRetCosDeliveryNoteDTO.setHotLine(rs.getString("HotLine1010"));
				}
				

				return rptRetCosDeliveryNoteDTO;
			}
		};
		
		try {
			logger.debug("retrieveCosDeliveryNoteCustDTO() @ MobCosRptDAO: " + SQL);
			logger.info("retrieveCosDeliveryNoteCustDTO() @ MobCosRptDAO: ");
			
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("orderId",orderId);

			rptRetCosDeliveryNoteDTOList = simpleJdbcTemplate.query(SQL, mapper,params);

		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException");
		
			rptRetCosDeliveryNoteDTOList = null;
		} catch (Exception e) {
			logger.info("Exception caught in retrieveCosDeliveryNoteCustDTO():", e);
			throw new DAOException(e.getMessage(), e);
		}
		if (rptRetCosDeliveryNoteDTOList == null || rptRetCosDeliveryNoteDTOList.isEmpty()) {
			return null;
		} else {
			for (RptRetCosDeliveryNoteDTO dto : rptRetCosDeliveryNoteDTOList) {
				if ("3C".equalsIgnoreCase(dto.getContactType())) {
					return dto;
				}
			}
		}
		
		return rptRetCosDeliveryNoteDTOList.get(0);
	}
	
	public RptRetCosDeliveryNoteDTO retrieveSBSCosDeliveryNoteCustDTO(String orderId) throws DAOException {
		logger.debug("retrieveCosDeliveryNoteCustDTO() is called");
		List<RptRetCosDeliveryNoteDTO> rptRetCosDeliveryNoteDTOList = new ArrayList<RptRetCosDeliveryNoteDTO>();
		
			String SQL ="SELECT O.ORDER_ID, " 
					+"  O.APP_DATE, " 
					+"  O.MSISDN, "
					+"  DC.CONTACT_TYPE, " 
					+"  DC.CONTACT_NAME, " 
					+"  DC.TITLE, " 
					+"  DC.CONTACT_PHONE, " 
					+"  DC.OTHER_PHONE, " 
					+"  BSI.SALES_NAME, " 
					+"  BSI.SALES_CD, " 
					+"  BSI.CONTACT_PHONE SCONTACT_PHONE, " 
					+"  (select acct.brand from bomweb_acct acct where acct.order_id = :orderId) BRAND, " 
					+"  (select a.HOTLINE from bomweb_shop a "  
					+"	LEFT JOIN bomweb_order b on (a.SHOP_CD=b.SHOP_CD) " 
					+"  where b.ORDER_ID = :orderId ) HotLine, " 
					+"  (select a.HOTLINE_1010 from bomweb_shop a "  
					+"	LEFT JOIN bomweb_order b on (a.SHOP_CD=b.SHOP_CD) " 
					+"  where b.ORDER_ID = :orderId ) HotLine1010 " 
					+"FROM BOMWEB_ORDER O, " 
					+"  BOMWEB_STAFF_INFO BSI, " 
					+"  (SELECT * FROM BOMWEB_CONTACT M WHERE M.CONTACT_TYPE in ('DC', '3C') " 
					+"  ) DC " 
					+"WHERE O.ORDER_ID = :orderId " 
					+"AND O.ORDER_ID   = DC.ORDER_ID(+) " 
					+"and O.ORDER_ID   = BSI.ORDER_ID(+)";
		
		ParameterizedRowMapper<RptRetCosDeliveryNoteDTO> mapper = new ParameterizedRowMapper<RptRetCosDeliveryNoteDTO>() {
			public RptRetCosDeliveryNoteDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				RptRetCosDeliveryNoteDTO rptRetCosDeliveryNoteDTO = new RptRetCosDeliveryNoteDTO();
		

				// "MA"rs.getString("ADDR_USAGE"));//alway MA
				rptRetCosDeliveryNoteDTO.setOrderId(rs.getString("ORDER_ID"));
				rptRetCosDeliveryNoteDTO.setMsisdn(rs.getString("MSISDN"));
				rptRetCosDeliveryNoteDTO.setContactName(rs.getString("CONTACT_NAME"));
				rptRetCosDeliveryNoteDTO.setTitle(rs.getString("TITLE"));
				if (StringUtils.isNotBlank(rs.getString("OTHER_PHONE"))) {
					rptRetCosDeliveryNoteDTO.setContactPhone(rs.getString("CONTACT_PHONE") + " / " + rs.getString("OTHER_PHONE"));
				} else {
					rptRetCosDeliveryNoteDTO.setContactPhone(rs.getString("CONTACT_PHONE"));
				}
				rptRetCosDeliveryNoteDTO.setSalesName(rs.getString("SALES_NAME"));
				rptRetCosDeliveryNoteDTO.setSalesCd(rs.getString("SALES_CD"));
				rptRetCosDeliveryNoteDTO.setsContactPhone(rs.getString("SCONTACT_PHONE"));
			//	rptRetCosDeliveryNoteDTO.setBasketType(rs.getString("BASKET_TYPE"));
				rptRetCosDeliveryNoteDTO.setAppInDateStr(Utility.date2String(rs.getTimestamp("APP_DATE"), "dd/MM/yyyy"));
				
				rptRetCosDeliveryNoteDTO.setContactType(rs.getString("CONTACT_TYPE"));
				rptRetCosDeliveryNoteDTO.setHotLine(rs.getString("HotLine"));
				if ("0".equals(rs.getString("BRAND"))){
					rptRetCosDeliveryNoteDTO.setHotLine(rs.getString("HotLine1010"));
				}
				

				return rptRetCosDeliveryNoteDTO;
			}
		};
		
		try {
			logger.debug("retrieveCosDeliveryNoteCustDTO() @ MobCosRptDAO: " + SQL);
			logger.info("retrieveCosDeliveryNoteCustDTO() @ MobCosRptDAO: ");
			
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("orderId",orderId);

			rptRetCosDeliveryNoteDTOList = simpleJdbcTemplate.query(SQL, mapper,params);

		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException");
		
			rptRetCosDeliveryNoteDTOList = null;
		} catch (Exception e) {
			logger.info("Exception caught in retrieveCosDeliveryNoteCustDTO():", e);
			throw new DAOException(e.getMessage(), e);
		}
		if (rptRetCosDeliveryNoteDTOList == null || rptRetCosDeliveryNoteDTOList.isEmpty()) {
			return null;
		} else {
			for (RptRetCosDeliveryNoteDTO dto : rptRetCosDeliveryNoteDTOList) {
				if ("3C".equalsIgnoreCase(dto.getContactType())) {
					return dto;
				}
			}
		}
		
		return rptRetCosDeliveryNoteDTOList.get(0);
	}
	

	
	public PaymentUpFrontUI getBomwebUpfrontPayment(String orderId) throws DAOException {
		logger.debug(" getBomwebUpfrontPayment is called");
	
		List<PaymentUpFrontUI> resultList = new ArrayList<PaymentUpFrontUI>();
		String getBomwebUpfrontPaymentSQL = "select up.ORDER_ID, \n"
				+ "up.PAY_MTD_TYPE,			\n"
				+ "up.THIRD_PARTY_IND,		\n"
				+ "up.CC_TYPE,		\n"
				+ "up.CC_NUM,		\n"
				+ "up.CC_HOLD_NAME,		\n"
				+ "up.CC_EXP_DATE,		\n"
				+ "up.CC_ISSUE_BANK,		\n"
				+ "up.PAY_METHOD,		\n"
				+ "up.PAY_AMT,			\n"
				+ "up.CREATE_BY,			\n"
				+ "up.CC_INST_SCHEDULE,				\n" 
				+ "up.BYPASS_IND,				\n"
				+ "up.CC_VERIFIED_IND,				\n"
				+ "  (select cb.bank_name from W_ISSUEBANKLKUP cb\n"
				+ "      where cb.bank_code = up.CC_ISSUE_BANK) CC_ISSUE_BANK_NAME \n"
				+ "from BOMWEB_UPFRONT_PAYMENT up		\n"
				+ "where up.ORDER_ID = ?    \n";
		ParameterizedRowMapper<PaymentUpFrontUI> mapper = new ParameterizedRowMapper<PaymentUpFrontUI>() {
			public PaymentUpFrontUI mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				PaymentUpFrontUI dto = new PaymentUpFrontUI();
				dto.setOrderId(rs.getString("ORDER_ID"));
				dto.setPayMethodType(rs.getString("PAY_MTD_TYPE"));
				dto.setThirdPartyInd(rs.getString("THIRD_PARTY_IND"));
				dto.setCreditCardType(rs.getString("CC_TYPE"));
				dto.setCreditCardNum(rs.getString("CC_NUM"));
				dto.setCreditCardHolderName(rs.getString("CC_HOLD_NAME"));
				
				if (rs.getString("CC_EXP_DATE") != null) {
					dto.setCreditExpiryMonth(rs.getString("CC_EXP_DATE").split("/")[0]);
					dto.setCreditExpiryYear(rs.getString("CC_EXP_DATE").split("/")[1]);
				}
				
				dto.setCreditCardIssueBankCd(rs.getString("CC_ISSUE_BANK"));
				dto.setPaymentCombination(rs.getString("PAY_METHOD"));
				dto.setInAdvanceAmount(rs.getString("PAY_AMT"));
				dto.setUsername(rs.getString("CREATE_BY"));
				dto.setCcInstSchedule(rs.getString("CC_INST_SCHEDULE"));
				dto.setByPassValidation("Y".equalsIgnoreCase(rs.getString("BYPASS_IND")));
				dto.setCreditCardIssueBankName(rs.getString("CC_ISSUE_BANK_NAME"));
				dto.setCreditCardVerifiedInd(rs.getString("CC_VERIFIED_IND"));
				return dto;
			}
		};
	
		try {
			logger.debug("getBomwebUpfrontPaymentSQL() @ PaymentUpfrontDAO: "
					+ getBomwebUpfrontPaymentSQL);
			
			resultList = simpleJdbcTemplate.query(getBomwebUpfrontPaymentSQL, mapper, orderId);
			
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException in getBomwebUpfrontPayment()");
		} catch (Exception e) {
			logger.info("Exception caught in getBomwebUpfrontPayment():",e);
			throw new DAOException(e.getMessage(), e);
		}
	
		if (resultList != null && !resultList.isEmpty()) {
			return resultList.get(0);
		} else {
			return null;
		}
	}
	
	public String checkUpfrontPaymentInd(String orderId) throws DAOException { 
		logger.debug("checkUpfrontPaymentInd() is called");
		List<String> list = new ArrayList<String>();
		String SQL = "select UPFRONT_PAYMENT_IND from  BOMWEB_ORDER_MOB where ORDER_ID = :orderId";
	
		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
	
			public String mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				String result = null;
				result = rs.getString("UPFRONT_PAYMENT_IND");
				
				return result;
			}
		};
	
		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("orderId", orderId);
			list = simpleJdbcTemplate.query(SQL, mapper,params);
			
		} catch (EmptyResultDataAccessException erdae) {
			throw new AppRuntimeException(erdae);
		} catch (Exception e) {
			logger.error("Exception caught in checkUpfrontPaymentInd()", e);
			throw new DAOException(e.getMessage(), e);
		}
		
		return (list == null || list.isEmpty()) ? null : list.get(0);
	}	
	
	public BigDecimal getHSPayment(String orderId) throws DAOException {
		String sql = "SELECT NVL(SUM(ONETIME_AMT),0) HSPAYMENT " 
				+"FROM BOMWEB_SUBSCRIBED_ITEM A, " 
				+"  W_ITEM_PRICING B, " 
				+"  BOMWEB_ORDER BO " 
				+"WHERE A.ORDER_ID= :orderId " 
				+"AND A.ID=B.ID " 
				+"AND A.ORDER_ID=BO.ORDER_ID " 
				+"AND TRUNC(BO.APP_DATE) BETWEEN TRUNC(NVL(B.EFF_START_DATE,SYSDATE)) AND TRUNC(NVL(B.EFF_END_DATE,SYSDATE)) "  
				+"AND NVL(B.ONETIME_TYPE,'#')!='A' " 
				+"AND NVL(B.PAYMENT_GROUP,'#')='HANDSET'";
		try {
			logger.debug("getHSPayment() @ MobCosRptDAO: " + sql);

			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("orderId", orderId);
			BigDecimal amount = this.simpleJdbcTemplate.queryForObject(sql, BigDecimal.class, params);
			return amount;
			
		} catch (EmptyResultDataAccessException erdae) {
			if (logger.isDebugEnabled()) {
				logger.debug("EmptyResultDataAccessException in getHSPayment()");
			}
			return null;
		} catch (Exception e) {
			logger.info("Exception caught in getHSPayment():", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public BigDecimal getPrePayment(String orderId) throws DAOException {
		String sql = "SELECT NVL(SUM(ONETIME_AMT),0) PREPAYMENT " 
				+"FROM BOMWEB_SUBSCRIBED_ITEM A, " 
				+"  W_ITEM_PRICING B, " 
				+"  BOMWEB_ORDER BO " 
				+"WHERE A.ORDER_ID= :orderId " 
				+"AND A.ID=B.ID " 
				+"AND A.ORDER_ID=BO.ORDER_ID " 
				+"AND TRUNC(BO.APP_DATE) BETWEEN TRUNC(NVL(B.EFF_START_DATE,SYSDATE)) AND TRUNC(NVL(B.EFF_END_DATE,SYSDATE)) " 
				+"AND NVL(B.ONETIME_TYPE,'#')!='A' " 
				+"AND NVL(B.PAYMENT_GROUP,'#')!='HANDSET'   "
				+"AND A.WAIVE_REASON IS NULL ";
		try {
			logger.debug("getPrePayment() @ MobCosRptDAO: " + sql);

			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("orderId", orderId);
			BigDecimal amount = this.simpleJdbcTemplate.queryForObject(sql, BigDecimal.class, params);
			return amount;
			
		} catch (EmptyResultDataAccessException erdae) {
			if (logger.isDebugEnabled()) {
				logger.debug("EmptyResultDataAccessException in getPrePayment()");
			}
			return null;
		} catch (Exception e) {
			logger.info("Exception caught in getPrePayment():", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public BigDecimal getAdminChargeChgSIM(String orderId) throws DAOException {
		String sql = "SELECT ADMIN_CHARGE " +
				"FROM BOMWEB_ORD_MOB_CHG_SIM_TXN " +
				"WHERE ORDER_ID =:orderId " +
				"AND NVL(MARK_DEL_IND,'N')='N' " +
				"AND WAIVE_REASON_CD IS NULL " +
				"AND STATUS IN ('D','P')";
		try {
			logger.debug("getAdminChargeChgSIM() @ MobCosRptDAO: " + sql);

			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("orderId", orderId);
			BigDecimal amount = this.simpleJdbcTemplate.queryForObject(sql, BigDecimal.class, params);
			return amount;
			
		} catch (EmptyResultDataAccessException erdae) {
			if (logger.isDebugEnabled()) {
				logger.debug("EmptyResultDataAccessException in getAdminChargeChgSIM()");
			}
			return null;
		} catch (Exception e) {
			logger.info("Exception caught in getAdminChargeChgSIM():", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public BigDecimal getAdminChargeItem(String orderId) throws DAOException {
		String sql = "SELECT nvl(sum(onetime_amt),0) ADMINCHARGEITEM  " 
				+"FROM BOMWEB_SUBSCRIBED_ITEM A, " 
				+"  W_ITEM_PRICING B, " 
				+"  BOMWEB_ORDER BO " 
				+"WHERE A.ORDER_ID= :orderId " 
				+"AND A.ID=B.ID " 
				+"AND A.ORDER_ID=BO.ORDER_ID " 
				+"AND TRUNC(BO.APP_DATE) BETWEEN TRUNC(NVL(B.EFF_START_DATE,SYSDATE)) AND TRUNC(NVL(B.EFF_END_DATE,SYSDATE)) " 
				+"AND B.ONETIME_TYPE='A'";
		try {
			logger.debug("getAdminChargeItem() @ MobCosRptDAO: " + sql);

			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("orderId", orderId);
			BigDecimal amount = this.simpleJdbcTemplate.queryForObject(sql, BigDecimal.class, params);
			return amount;
			
		} catch (EmptyResultDataAccessException erdae) {
			if (logger.isDebugEnabled()) {
				logger.debug("EmptyResultDataAccessException in getAdminChargeItem()");
			}
			return null;
		} catch (Exception e) {
			logger.info("Exception caught in getAdminChargeItem():", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
		
	public BigDecimal getPaidAmt(String orderId) throws DAOException {
		String sql = "select nvl(sum(PAYMENT_AMOUNT),0) PAIDAMT " 
				+"from BOMWEB_PAYMENT_TRANS PT " 
				+"where PT.ORDER_ID    = :orderId " 
				+"and PT.TRANS_STATUS  = 'SETTLED' ";
		try {
			logger.debug("getPaidAmt() @ MobCosRptDAO: " + sql);

			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("orderId", orderId);
			BigDecimal amount = this.simpleJdbcTemplate.queryForObject(sql, BigDecimal.class, params);
			return amount;
			
		} catch (EmptyResultDataAccessException erdae) {
			if (logger.isDebugEnabled()) {
				logger.debug("EmptyResultDataAccessException in getPaidAmt()");
			}
			return null;
		} catch (Exception e) {
			logger.info("Exception caught in getPaidAmt():", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public DeliveryUI retrieveCosDeliveryUI(String orderId) throws DAOException {
		logger.debug("retrieveCosDeliveryUI() is called");
		List<DeliveryUI> deliveryUIList = new ArrayList<DeliveryUI>();
		
			String SQL ="SELECT DELIVERY_IND, " 
					+"  DELIVERY_CENTRE, " 
					+"  URGENT_IND, " 
					+"  DELIVERY_DATE, " 
					+"  LOCATION, " 
					+"  DELIVERY_TIME_SLOT " 
					+"FROM BOMWEB_DELIVERY " 
					+"WHERE ORDER_ID=:orderId ";
		
		ParameterizedRowMapper<DeliveryUI> mapper = new ParameterizedRowMapper<DeliveryUI>() {
			public DeliveryUI mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				DeliveryUI dto = new DeliveryUI();

				dto.setDeliveryInd(rs.getString("DELIVERY_IND"));
				dto.setDeliveryDate(rs.getDate("DELIVERY_DATE"));
				dto.setDeliveryTimeSlot(rs.getString("DELIVERY_TIME_SLOT"));
				dto.setDeliveryCentre(rs.getString("DELIVERY_CENTRE"));
				dto.setLocation(rs.getString("LOCATION"));
				if (rs.getString("URGENT_IND")!=null&&"Y".equalsIgnoreCase(rs.getString("URGENT_IND")))
					dto.setUrgentInd(true);
				else if (rs.getString("URGENT_IND")!=null&&"N".equalsIgnoreCase(rs.getString("URGENT_IND")))
					dto.setUrgentInd(false);
				//rptRetCosDeliveryNoteDTO.setAppInDateStr(Utility.date2String(rs.getTimestamp("APP_DATE"), "dd/MM/yyyy"));
				return dto;
			}
		};
		
		try {
			logger.debug("retrieveCosDeliveryUI() @ MobCosRptDAO: " + SQL);
			logger.info("retrieveCosDeliveryUI() @ MobCosRptDAO: ");
			
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("orderId",orderId);

			deliveryUIList = simpleJdbcTemplate.query(SQL, mapper,params);

		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException");
		
			deliveryUIList = null;
		} catch (Exception e) {
			logger.info("Exception caught in retrieveCosDeliveryUI():", e);
			throw new DAOException(e.getMessage(), e);
		}
		if (deliveryUIList.isEmpty()){
			
			return null;
		}
		else{
			return deliveryUIList.get(0);// only return the first one
		}
	}

	public String getTimeSlot(String slotType, String timeSlot) throws DAOException { 
		logger.debug("getTimeSlot() is called");
		List<String> list = new ArrayList<String>();
		String SQL = "select (TIMEFROM||'-'||TIMETO) TIMEFROMTO " 
				+"from W_DELIVERY_TIMESLOT " 
				+"where SLOT_TYPE=:slotType " 
				+"and TIMESLOT   =:timeSlot";

		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {

			public String mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				String result = null;
				result = rs.getString("TIMEFROMTO");
				
				return result;
			}
		};

		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("slotType", slotType);
			params.addValue("timeSlot", timeSlot);
			list = simpleJdbcTemplate.query(SQL, mapper,params);
			
		} catch (EmptyResultDataAccessException erdae) {
			throw new AppRuntimeException(erdae);
		} catch (Exception e) {
			logger.error("Exception caught in getTimeSlot()", e);
			throw new DAOException(e.getMessage(), e);
		}
		
		return (list == null || list.isEmpty()) ? null : list.get(0);
	}
		
	public DeliveryUI retrieveCosDeliveryAddrUI(String orderId)
			throws DAOException {
			logger.debug("retrieveCosDeliveryAddrUI() is called");
			List<DeliveryUI> deliveryUIList = new ArrayList<DeliveryUI>();
			
				String SQL ="SELECT CA.ADDR_USAGE, " 
						+"  CA.AREA_CD, " 
						+"  CA.DIST_NO, " 
						+"  CA.SECT_CD, " 
						+"  CA.STR_NAME, " 
						+"  CA.HI_LOT_NO, " 
						+"  CA.STR_CAT_CD, " 
						+"  CA.BUILD_NO, " 
						+"  CA.FOREIGN_ADDR_FLAG, " 
						+"  CA.FLOOR_NO, " 
						+"  CA.UNIT_NO, " 
						+"  CA.PO_BOX_NO, " 
						+"  CA.ADDR_TYPE, " 
						+"  CA.STR_NO, " 
						+"  CA.SECT_DEP_IND, " 
						+"  CA.AREA_DESC, " 
						+"  CA.DIST_DESC, " 
						+"  CA.SECT_DESC, " 
						+"  CA.STR_CAT_DESC, "
						+"  CA.FREE_INPUT_IND, " 
						+"  CA.FREE_ADDR_1," 
						+"  CA.FREE_ADDR_2," 
						+"  CA.FREE_ADDR_3 " 
						+"FROM BOMWEB_CUST_ADDR CA " 
						+"WHERE ADDR_USAGE='DA' " 
						+"AND ORDER_ID    = :orderId";
			
			ParameterizedRowMapper<DeliveryUI> mapper = new ParameterizedRowMapper<DeliveryUI>() {
				public DeliveryUI mapRow(ResultSet rs, int rowNum)
						throws SQLException {
					DeliveryUI dto = new DeliveryUI();

					//address info
					dto.setAreaCode(rs.getString("AREA_CD"));
					dto.setDistrictCode(rs.getString("DIST_NO"));
					dto.setSectionCode(rs.getString("SECT_CD"));
					dto.setStreetName(rs.getString("STR_NAME"));
					dto.setLotNum(rs.getString("HI_LOT_NO"));
					dto.setStreetCatgCode(rs.getString("STR_CAT_CD"));
					dto.setStreetCatgDesc(rs.getString("STR_CAT_DESC"));
					dto.setBuildingName(rs.getString("BUILD_NO"));
					// "N"rs.getString("FOREIGN_ADDR_FLAG"));
					dto.setFloor(rs.getString("FLOOR_NO"));
					dto.setFlat(rs.getString("UNIT_NO"));
					// ""rs.getString("PO_BOX_NO"));
					// "S"rs.getString("ADDR_TYPE"));
					dto.setStreetNum(rs.getString("STR_NO"));
					// "N"rs.getString("SECT_DEP_IND"));
					dto.setAreaDesc(rs.getString("AREA_DESC"));
					dto.setDistrictDesc(rs.getString("DIST_DESC"));
					dto.setSectionDesc(rs.getString("SECT_DESC"));
					dto.setCustAddressFlag2(("Y".equalsIgnoreCase(rs.getString("FREE_INPUT_IND"))));
					dto.setAddress1(rs.getString("FREE_ADDR_1"));
					dto.setAddress2(rs.getString("FREE_ADDR_2"));
					dto.setAddress3(rs.getString("FREE_ADDR_3"));
					return dto;
				}
			};
			
			try {
				logger.debug("retrieveCosDeliveryAddrUI() @ MobCosRptDAO: " + SQL);
				logger.info("retrieveCosDeliveryAddrUI() @ MobCosRptDAO: ");
				
				MapSqlParameterSource params = new MapSqlParameterSource();
				params.addValue("orderId",orderId);

				deliveryUIList = simpleJdbcTemplate.query(SQL, mapper,params);

			} catch (EmptyResultDataAccessException erdae) {
				logger.info("EmptyResultDataAccessException");
			
				deliveryUIList = null;
			} catch (Exception e) {
				logger.info("Exception caught in retrieveCosDeliveryAddrUI():", e);
				throw new DAOException(e.getMessage(), e);
			}
			if (deliveryUIList.isEmpty()){
				
				return null;
			}
			else{
				return deliveryUIList.get(0);// only return the first one
			}
		}
		
	public BigDecimal getDepositAmountForOrder(String orderId) throws DAOException {
		String sql = "SELECT nvl(sum(DEPOSIT_AMOUNT),0)TOTAL_DEPOSIT "
				+ " FROM BOMWEB_DEPOSIT "
				+ " WHERE "
				+ " ORDER_ID=:orderId "
				+ " AND WAIVE_IND != 'Y' ";
		try {
			logger.debug("getDepositAmountForOrder() @ MobCosRptDAO: " + sql);

			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("orderId", orderId);
			BigDecimal amount = this.simpleJdbcTemplate.queryForObject(sql, BigDecimal.class, params);
			return amount;
			
		} catch (EmptyResultDataAccessException erdae) {
			if (logger.isDebugEnabled()) {
				logger.debug("EmptyResultDataAccessException in getDepositAmountForOrder()");
			}
			return null;
		} catch (Exception e) {
			logger.info("Exception caught in getDepositAmountForOrder():", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
		
	public String[] retrieveAuthorizationLetteIDInfo(String orderId) throws DAOException {
		logger.debug("getAllSupportDocList() is called");
		List<String[]> list = new ArrayList<String[]>();
		String SQL = "SELECT TITLE, " 
				+"  CONTACT_NAME, " 
				+"  CONTACT_PHONE " 
				+"FROM BOMWEB_CONTACT " 
				+"WHERE ORDER_ID  = :orderId " 
				+"AND CONTACT_TYPE='3C'";

		ParameterizedRowMapper<String[]> mapper = new ParameterizedRowMapper<String[]>() {

			public String[] mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				String[] result = new String[3];
				result[0] = rs.getString("TITLE");
				result[1] = rs.getString("CONTACT_NAME");
				result[2] = rs.getString("CONTACT_PHONE");
				return result;
			}
		};

		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("orderId", orderId);
			list = simpleJdbcTemplate.query(SQL, mapper,params);
			
		} catch (EmptyResultDataAccessException erdae) {
			throw new AppRuntimeException(erdae);
		} catch (Exception e) {
			logger.error("Exception caught in retrieveAuthorizationLetteIDInfo()", e);
			throw new DAOException(e.getMessage(), e);
		}
		
		return (list == null || list.isEmpty()) ? null : list.get(0);
	}
		
	public RptRetCourierDeliveryGuidelineDTO getBSContact(String orderId) throws DAOException { 
		logger.debug("getBSContact() is called");
		List<RptRetCourierDeliveryGuidelineDTO> list = new ArrayList<RptRetCourierDeliveryGuidelineDTO>();
		String SQL="SELECT BCT.TITLE||' '||BCT.CONTACT_NAME CONTACT_NAME, " 
				+"  BCT.CONTACT_TYPE " 
				+" FROM BOMWEB_CUSTOMER BC , " 
				+"  BOMWEB_CONTACT BCT " 
				+" WHERE BC.ID_DOC_TYPE='BS' " 
				+" AND BC.ORDER_ID     =BCT.ORDER_ID "
				+" AND BCT.CONTACT_TYPE IN ('3C','DC')"
				+" AND BCT.ORDER_ID    = :orderId";

		ParameterizedRowMapper<RptRetCourierDeliveryGuidelineDTO> mapper = new ParameterizedRowMapper<RptRetCourierDeliveryGuidelineDTO>() {

			public RptRetCourierDeliveryGuidelineDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				RptRetCourierDeliveryGuidelineDTO rptRetCourierDeliveryGuidelineDTO = new RptRetCourierDeliveryGuidelineDTO();
				
				rptRetCourierDeliveryGuidelineDTO.setBsDeliveryContactName(rs.getString("CONTACT_NAME"));
				rptRetCourierDeliveryGuidelineDTO.setContactType(rs.getString("CONTACT_TYPE"));
				System.out.println("ccccc"+rptRetCourierDeliveryGuidelineDTO.getBsDeliveryContactName());
				System.out.println("csddddd"+rptRetCourierDeliveryGuidelineDTO.getContactType());
				return rptRetCourierDeliveryGuidelineDTO;
			}
		};

		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("orderId",orderId);
			list = simpleJdbcTemplate.query(SQL, mapper,params);
			
		} catch (EmptyResultDataAccessException erdae) {
			list = null;
			throw new AppRuntimeException(erdae);
		} catch (Exception e) {
			logger.error("Exception caught in getBSContact()", e);
			throw new DAOException(e.getMessage(), e);
		}
		
		if (list == null || list.isEmpty()) {
			return null;
		} else {
			for (RptRetCourierDeliveryGuidelineDTO dto : list) {
				if ("3C".equalsIgnoreCase(dto.getContactType())) {
					return dto;
				}
			}
		}
		
		return list.get(0);
	}
		
	public IGuardDTO retrieveIGuardDTOCustomerInfo(String orderId, String channel) throws DAOException { 
		logger.debug("retrieveIGuardDTOCustomerInfo() is called");
		List<IGuardDTO> list = new ArrayList<IGuardDTO>();
		String SQL="SELECT "
				+ "   BO.ORDER_ID, "
				+ "   TO_CHAR(BO.SRV_REQ_DATE, 'dd/mm/yyyy') SRV_REQ_DATE, "
				+ "   BO.MSISDN, "
				+ "   BO.IMEI, "
				+ "   BO.SALES_CD, "
				+ "   BO.SHOP_CD, "
				+ "   BC.TITLE, "
				+ "   BC.FIRST_NAME, "
				+ "   BC.LAST_NAME, "
				+ "   BC.ID_DOC_NUM, "
				+ "   BO.MSISDN CONTACT_PHONE, "
				+ "   HS.SS_FORM_DESC, "
				+ "   UPPER(M.IGUARD_SN) IGUARD_SN, "
				+ "   DECODE(:channel, '2', null, TO_CHAR(BO.APP_DATE, 'dd/mm/yyyy')) HS_RECEIVED_DATE ,"
				+ "   BO.ESIG_EMAIL_ADDR "
				+ "FROM BOMWEB_ORDER BO, "
				+ "     BOMWEB_CUSTOMER BC, "
				+ "     (SELECT BSI.ORDER_ID, IDHS.SS_FORM_DESC FROM BOMWEB_SUBSCRIBED_ITEM BSI "
				+ "             JOIN W_ITEM_DTL_HS IDHS ON BSI.TYPE = 'HS' "
				+ "             AND BSI.ID = IDHS.ID) HS, "
				+ "     BOMWEB_ORDER_MOB M, "
				+ "     BOMWEB_CONTACT C "
				+ "WHERE BO.ORDER_ID = BC.ORDER_ID "
				+ "AND BO.ORDER_ID = HS.ORDER_ID (+) "
				+ "AND BO.ORDER_ID = M.ORDER_ID "
				+ "AND BO.ORDER_ID = C.ORDER_ID "
				+ "AND BO.ORDER_ID = :orderId ";

		ParameterizedRowMapper<IGuardDTO> mapper = new ParameterizedRowMapper<IGuardDTO>() {
			public IGuardDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				IGuardDTO iGuardDTO = new IGuardDTO();
				iGuardDTO.setOrderId(rs.getString("ORDER_ID"));
				iGuardDTO.setSerialNo(rs.getString("IGUARD_SN"));
				iGuardDTO.setCustFirstName(rs.getString("FIRST_NAME"));
				iGuardDTO.setCustLastName(rs.getString("LAST_NAME"));
				iGuardDTO.setTitle(rs.getString("TITLE"));
				iGuardDTO.setIdDocNum(rs.getString("ID_DOC_NUM"));
				iGuardDTO.setContactNo(rs.getString("CONTACT_PHONE"));
				iGuardDTO.setImei(StringUtils.isBlank(rs.getString("IMEI")) ? "" : rs.getString("IMEI"));
				iGuardDTO.setTgtEffDate(rs.getString("SRV_REQ_DATE"));
				iGuardDTO.setMsisdn(rs.getString("MSISDN"));
				iGuardDTO.setShopCd(rs.getString("SHOP_CD"));
				iGuardDTO.setSalesCd(rs.getString("SALES_CD"));
				iGuardDTO.setHandsetDeviceDescription(rs.getString("SS_FORM_DESC"));
				iGuardDTO.setHsReceivedDate(StringUtils.isBlank(rs.getString("HS_RECEIVED_DATE")) ? "" : rs.getString("HS_RECEIVED_DATE"));
				iGuardDTO.setEmail(rs.getString("ESIG_EMAIL_ADDR"));
				return iGuardDTO;
			}
		};

		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("orderId",orderId);
			params.addValue("channel",channel);
			list = simpleJdbcTemplate.query(SQL, mapper,params);
			
		} catch (EmptyResultDataAccessException erdae) {
			list = null;
			throw new AppRuntimeException(erdae);
		} catch (Exception e) {
			logger.error("Exception caught in retrieveIGuardDTOCustomerInfo()", e);
			throw new DAOException(e.getMessage(), e);
		}
		return (list == null || list.isEmpty()) ? null : list.get(0);
	}
		
	public RptMobileSafetyPhoneDTO retrieveRptMobileSafetyPhoneDTO(String orderId) throws DAOException { 
		logger.debug("retrieveRptMobileSafetyPhoneDTO() is called");
		List<RptMobileSafetyPhoneDTO> list = new ArrayList<RptMobileSafetyPhoneDTO>();
		String SQL="SELECT "
				+ "   BO.ORDER_ID, "
				+ "   BO.MSISDN, "
				+ "   BO.APP_DATE, "
				+ "   BO.SRV_REQ_DATE, "
				+ "   BC.TITLE, "
				+ "   BC.LAST_NAME, "
				+ "   BC.FIRST_NAME "
				+ "FROM BOMWEB_ORDER BO, "
				+ "     BOMWEB_CUSTOMER BC "
				+ "WHERE BO.ORDER_ID = BC.ORDER_ID "
				+ "AND BO.ORDER_ID = :orderId";

		ParameterizedRowMapper<RptMobileSafetyPhoneDTO> mapper = new ParameterizedRowMapper<RptMobileSafetyPhoneDTO>() {

			public RptMobileSafetyPhoneDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				RptMobileSafetyPhoneDTO rptMobileSafetyPhoneDTO = new RptMobileSafetyPhoneDTO();
				rptMobileSafetyPhoneDTO.setOrderId(rs.getString("ORDER_ID"));
				rptMobileSafetyPhoneDTO.setMsisdn(rs.getString("MSISDN"));
				rptMobileSafetyPhoneDTO.setAppInDate(rs.getDate("APP_DATE"));
				rptMobileSafetyPhoneDTO.setTargetCommencementDate(rs.getDate("SRV_REQ_DATE"));
				rptMobileSafetyPhoneDTO.setTitle(rs.getString("TITLE"));
				rptMobileSafetyPhoneDTO.setCustLastName(rs.getString("LAST_NAME"));
				rptMobileSafetyPhoneDTO.setCustFirstName(rs.getString("FIRST_NAME"));;
				return rptMobileSafetyPhoneDTO;
			}
		};

		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("orderId",orderId);
			list = simpleJdbcTemplate.query(SQL, mapper,params);
			
		} catch (EmptyResultDataAccessException erdae) {
			list = null;
			throw new AppRuntimeException(erdae);
		} catch (Exception e) {
			logger.error("Exception caught in retrieveRptMobileSafetyPhoneDTO()", e);
			throw new DAOException(e.getMessage(), e);
		}
		return (list == null || list.isEmpty()) ? null : list.get(0);
	}
	
	public RptMobileSafetyPhoneTnCDTO retrieveRptMobileSafetyPhoneTnCDTO(String orderId) throws DAOException { 
		logger.debug("retrieveRptMobileSafetyPhoneTnCDTO() is called");
		List<RptMobileSafetyPhoneTnCDTO> list = new ArrayList<RptMobileSafetyPhoneTnCDTO>();
		String SQL="SELECT "
				+ "   BO.ORDER_ID, "
				+ "   BO.MSISDN, "
				+ "   BO.APP_DATE, "
				+ "   BO.SRV_REQ_DATE, "
				+ "   BC.TITLE, "
				+ "   BC.LAST_NAME, "
				+ "   BC.FIRST_NAME "
				+ "FROM BOMWEB_ORDER BO, "
				+ "     BOMWEB_CUSTOMER BC "
				+ "WHERE BO.ORDER_ID = BC.ORDER_ID "
				+ "AND BO.ORDER_ID = :orderId";

		ParameterizedRowMapper<RptMobileSafetyPhoneTnCDTO> mapper = new ParameterizedRowMapper<RptMobileSafetyPhoneTnCDTO>() {

			public RptMobileSafetyPhoneTnCDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				RptMobileSafetyPhoneTnCDTO rptMobileSafetyPhoneTnCDTO = new RptMobileSafetyPhoneTnCDTO();
				rptMobileSafetyPhoneTnCDTO.setOrderId(rs.getString("ORDER_ID"));
				rptMobileSafetyPhoneTnCDTO.setMsisdn(rs.getString("MSISDN"));
				rptMobileSafetyPhoneTnCDTO.setAppInDate(rs.getDate("APP_DATE"));
				rptMobileSafetyPhoneTnCDTO.setTargetCommencementDate(rs.getDate("SRV_REQ_DATE"));
				rptMobileSafetyPhoneTnCDTO.setTitle(rs.getString("TITLE"));
				rptMobileSafetyPhoneTnCDTO.setCustLastName(rs.getString("LAST_NAME"));
				rptMobileSafetyPhoneTnCDTO.setCustFirstName(rs.getString("FIRST_NAME"));;
				return rptMobileSafetyPhoneTnCDTO;
			}
		};

		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("orderId",orderId);
			list = simpleJdbcTemplate.query(SQL, mapper,params);
			
		} catch (EmptyResultDataAccessException erdae) {
			list = null;
			throw new AppRuntimeException(erdae);
		} catch (Exception e) {
			logger.error("Exception caught in retrieveRptMobileSafetyPhoneTnCDTO()", e);
			throw new DAOException(e.getMessage(), e);
		}
		return (list == null || list.isEmpty()) ? null : list.get(0);
	}
	
	public RptMobileSafetyPhoneSuppAppDTO retrieveRptMobileSafetyPhoneSuppAppDTO(String orderId) throws DAOException { 
		logger.debug("retrieveRptMobileSafetyPhoneSuppAppDTO() is called");
		List<RptMobileSafetyPhoneSuppAppDTO> list = new ArrayList<RptMobileSafetyPhoneSuppAppDTO>();
		String SQL="SELECT "
				+ "   BO.ORDER_ID, "
				+ "   BO.MSISDN, "
				+ "   BO.APP_DATE, "
				+ "   BC.TITLE, "
				+ "   BC.LAST_NAME, "
				+ "   BC.FIRST_NAME "
				+ "FROM BOMWEB_ORDER BO, "
				+ "     BOMWEB_CUSTOMER BC "
				+ "WHERE BO.ORDER_ID = BC.ORDER_ID "
				+ "AND BO.ORDER_ID = :orderId";

		ParameterizedRowMapper<RptMobileSafetyPhoneSuppAppDTO> mapper = new ParameterizedRowMapper<RptMobileSafetyPhoneSuppAppDTO>() {

			public RptMobileSafetyPhoneSuppAppDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				RptMobileSafetyPhoneSuppAppDTO rptMobileSafetyPhoneSuppAppDTO = new RptMobileSafetyPhoneSuppAppDTO();
				rptMobileSafetyPhoneSuppAppDTO.setOrderId(rs.getString("ORDER_ID"));
				rptMobileSafetyPhoneSuppAppDTO.setMsisdn(rs.getString("MSISDN"));
				rptMobileSafetyPhoneSuppAppDTO.setAppInDate(rs.getDate("APP_DATE"));
				rptMobileSafetyPhoneSuppAppDTO.setTitle(rs.getString("TITLE"));
				rptMobileSafetyPhoneSuppAppDTO.setCustLastName(rs.getString("LAST_NAME"));
				rptMobileSafetyPhoneSuppAppDTO.setCustFirstName(rs.getString("FIRST_NAME"));;
				return rptMobileSafetyPhoneSuppAppDTO;
			}
		};

		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("orderId",orderId);
			list = simpleJdbcTemplate.query(SQL, mapper,params);
			
		} catch (EmptyResultDataAccessException erdae) {
			list = null;
			throw new AppRuntimeException(erdae);
		} catch (Exception e) {
			logger.error("Exception caught in retrieveRptMobileSafetyPhoneSuppAppDTO()", e);
			throw new DAOException(e.getMessage(), e);
		}
		return (list == null || list.isEmpty()) ? null : list.get(0);
	}
	
	public RptMobileSafetyPhoneAFCDTO retrieveRptMobileSafetyPhoneAFCDTO(String orderId) throws DAOException { 
		logger.debug("retrieveRptMobileSafetyPhoneAFCDTO() is called");
		List<RptMobileSafetyPhoneAFCDTO> list = new ArrayList<RptMobileSafetyPhoneAFCDTO>();
		String SQL="SELECT "
				+ "   BO.ORDER_ID, "
				+ "   BO.MSISDN, "
				+ "   BO.APP_DATE, "
				+ "   BC.TITLE, "
				+ "   BC.LAST_NAME, "
				+ "   BC.FIRST_NAME "
				+ "FROM BOMWEB_ORDER BO, "
				+ "     BOMWEB_CUSTOMER BC "
				+ "WHERE BO.ORDER_ID = BC.ORDER_ID "
				+ "AND BO.ORDER_ID = :orderId";

		ParameterizedRowMapper<RptMobileSafetyPhoneAFCDTO> mapper = new ParameterizedRowMapper<RptMobileSafetyPhoneAFCDTO>() {

			public RptMobileSafetyPhoneAFCDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				RptMobileSafetyPhoneAFCDTO rptMobileSafetyPhoneAFCDTO = new RptMobileSafetyPhoneAFCDTO();
				rptMobileSafetyPhoneAFCDTO.setOrderId(rs.getString("ORDER_ID"));
				rptMobileSafetyPhoneAFCDTO.setMsisdn(rs.getString("MSISDN"));
				rptMobileSafetyPhoneAFCDTO.setAppInDate(rs.getDate("APP_DATE"));
				rptMobileSafetyPhoneAFCDTO.setTitle(rs.getString("TITLE"));
				rptMobileSafetyPhoneAFCDTO.setCustLastName(rs.getString("LAST_NAME"));
				rptMobileSafetyPhoneAFCDTO.setCustFirstName(rs.getString("FIRST_NAME"));;
				return rptMobileSafetyPhoneAFCDTO;
			}
		};

		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("orderId",orderId);
			list = simpleJdbcTemplate.query(SQL, mapper,params);
			
		} catch (EmptyResultDataAccessException erdae) {
			list = null;
			throw new AppRuntimeException(erdae);
		} catch (Exception e) {
			logger.error("Exception caught in retrieveRptMobileSafetyPhoneAFCDTO()", e);
			throw new DAOException(e.getMessage(), e);
		}
		return (list == null || list.isEmpty()) ? null : list.get(0);
	}
	public BasketDTO getBasketAttributeCOS(String basketId) throws DAOException {
		List<BasketDTO> basketList = new ArrayList<BasketDTO>();
		String SQL="SELECT CONTRACT_PERIOD_ID,BRAND,MODEL,COLOR FROM W_BASKET_ATTRIBUTE_MV where basket_id= :basketId";

		ParameterizedRowMapper<BasketDTO> mapper = new ParameterizedRowMapper<BasketDTO>() {

			public BasketDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				BasketDTO basket = new BasketDTO();
				basket.setContractPeriod(rs.getString("CONTRACT_PERIOD_ID"));
				basket.setBrandDesc(rs.getString("BRAND"));
				basket.setColorDesc(rs.getString("COLOR"));
				basket.setModelDesc(rs.getString("MODEL"));
				return basket;
			}
		};

		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("basketId",basketId);
			//params.addValue("appDate", appDate);
			basketList = simpleJdbcTemplate.query(SQL, mapper,params);
			
		} catch (EmptyResultDataAccessException erdae) {
			basketList = null;
			throw new AppRuntimeException(erdae);
		} catch (Exception e) {
			logger.error("Exception caught in getBasketAttributeCOS()", e);
			throw new DAOException(e.getMessage(), e);
		}
		return (basketList == null || basketList.isEmpty()) ? null : basketList.get(0);
	}
	public String getHSItemCode(String basketId,String appDate) throws DAOException { 
		logger.debug("getHSItemCode() is called");
		List<String> list = new ArrayList<String>();
		String SQL = "select IPPA.POS_ITEM_CD " 
				+"from W_BASKET_ITEM_ASSGN BIA, " 
				+"  W_ITEM I, " 
				+"  W_ITEM_OFFER_ASSGN IOA, " 
				+"  W_ITEM_OFFER_PRODUCT_ASSGN IOPA, " 
				+"  W_ITEM_PRODUCT_POS_ASSGN IPPA " 
				+"where BIA.ITEM_ID         = I.ID " 
				+"and I.ID                  = IOA.ITEM_ID " 
				+"and IOA.ITEM_ID           = IOPA.ITEM_ID " 
				+"and IOA.ITEM_OFFER_SEQ    = IOPA.ITEM_OFFER_SEQ " 
				+"and IOPA.ITEM_ID          = IPPA.ITEM_ID " 
				+"and IOPA.ITEM_OFFER_SEQ   = IPPA.ITEM_PRODUCT_SEQ " 
				+"AND IOPA.ITEM_PRODUCT_SEQ = IPPA.ITEM_PRODUCT_SEQ " 
				+"and TO_DATE(:appDate, 'DD/MM/YYYY') between BIA.EFF_START_DATE and NVL(BIA.EFF_END_DATE, sysdate) " 
				+"AND I.TYPE       = 'HS' " 
				+"and BIA.BASKET_ID=:basketId ";

		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {

			public String mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				String result = null;
				result = rs.getString("POS_ITEM_CD");
				
				return result;
			}
		};

		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("basketId",basketId);
			params.addValue("appDate", appDate);
			list = simpleJdbcTemplate.query(SQL, mapper,params);
			
		} catch (EmptyResultDataAccessException erdae) {
			throw new AppRuntimeException(erdae);
		} catch (Exception e) {
			logger.error("Exception caught in getHSItemCode()", e);
			throw new DAOException(e.getMessage(), e);
		}
		
		return (list == null || list.isEmpty()) ? null : list.get(0);
	}
	private ParameterizedRowMapper<AllOrdDocAssgnDTO> getRowMapper() {
		return new ParameterizedRowMapper<AllOrdDocAssgnDTO>() {
			public AllOrdDocAssgnDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				AllOrdDocAssgnDTO dto = new AllOrdDocAssgnDTO();
				dto.setOrderId(rs.getString("ORDER_ID"));
				try {
					dto.setDocType(DocType.valueOf(rs.getString("DOC_TYPE")));
				} catch (Exception e) {
					dto.setDocType(DocType.valueOf("M001"));
				}
				dto.setDocTypeMob(rs.getString("DOC_TYPE"));
				dto.setDocName(rs.getString("DOC_NAME"));
				dto.setWaiveReason(rs.getString("WAIVE_REASON"));
				dto.setWaivedBy(rs.getString("WAIVED_BY"));
				String collectedInd = rs.getString("COLLECTED_IND");
				if (collectedInd instanceof String) {
					dto.setCollectedInd(CollectedInd.valueOf(collectedInd));
				}
				String markDelInd = rs.getString("MARK_DEL_IND");
				if (markDelInd instanceof String) {
					dto.setMarkDelInd(MarkDelInd.valueOf(markDelInd));
				}
				dto.setCreateBy(rs.getString("CREATE_BY"));
				dto.setCreateDate(rs.getTimestamp("CREATE_DATE"));
				dto.setLastUpdBy(rs.getString("LAST_UPD_BY"));
				dto.setLastUpdDate(rs.getTimestamp("LAST_UPD_DATE"));
				dto.setRowId(rs.getString("ROW_ID"));
				return dto;
			}
		};
	}
	public List<AllOrdDocAssgnDTO> getRetAppDocCollected(String orderId) throws DAOException {
		if (logger.isDebugEnabled()) {
			logger.debug("getRetAppDocCollected @ MobCosRptDAO is called");
		}
		List<AllOrdDocAssgnDTO> list = Collections.emptyList();
		String sql = "SELECT" +
				" a.ORDER_ID" +
				" , a.DOC_TYPE" +
				" , d.DOC_NAME" +
				" , a.WAIVE_REASON" +
				" , a.WAIVED_BY" +
				" , a.COLLECTED_IND" +
				" , a.MARK_DEL_IND" +
				" , a.CREATE_BY" +
				" , a.CREATE_DATE" +
				" , a.LAST_UPD_BY" +
				" , a.LAST_UPD_DATE" +
				" , a.rowid ROW_ID" +
				" FROM bomweb_all_ord_doc_assgn a" +
				" LEFT JOIN bomweb_order o ON (a.ORDER_ID = o.ORDER_ID)" +
				" LEFT JOIN bomweb_all_doc d ON (a.DOC_TYPE = d.DOC_TYPE AND d.LOB = o.LOB)" +
				" WHERE a.ORDER_ID = :orderId" +
				" AND NVL(a.MARK_DEL_IND, :defaultMarkDelInd) <> :markDelInd" +
				" ORDER BY a.DOC_TYPE";
		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("orderId", orderId);
			params.addValue("defaultMarkDelInd", MarkDelInd.N.toString());
			params.addValue("markDelInd", MarkDelInd.Y.toString());
			if (logger.isInfoEnabled()) {
				logger.info("getRetAppDocCollected @ MobCosRptDAO: " + sql);
			}
			list = simpleJdbcTemplate.query(sql, this.getRowMapper(), params);
			//list = simpleJdbcTemplate.query(SQL, mapper,params);
		} catch (EmptyResultDataAccessException erdae) {
			if (logger.isInfoEnabled()) {
				logger.info("EmptyResultDataAccessException in getRetAppDocCollected @ MobCosRptDAO");
			}
			list = null;
		} catch (Exception e) {
			if (logger.isInfoEnabled()) {
				logger.info("Exception caught in getRetAppDocCollected @ MobCosRptDAO: ", e);
			}
			throw new DAOException(e.getMessage(), e);
		}
		return  (list == null || list.isEmpty()) ? null : list;
	}
	
	public RptRetCountRejDTO retrieveRptRetCountRejDTO(String orderId, int seqNo) throws DAOException {
		if (logger.isDebugEnabled()) {
			logger.debug("retrieveRptRetCountRejDTO is called");
		}
		final String getInitDoaRequestDTO = "SELECT" +
				" o.ORDER_ID" +
				" , o.ORDER_STATUS" +
				" , o.REASON_CD" +
				" , c.TITLE || ' ' || c.LAST_NAME || ' ' || c.FIRST_NAME CUSTOMER_NAME" +
				" , o.MSISDN" +
				" , d.ACTUAL_DELIVERY_DATE" +
				" , d.DELIVERY_DATE" +
				" , req.REQUEST_DATE" +
				" , req.REMARKS" +
				" , req.AUTHORIZED_ID" +
				" , req.APPROVAL_SERIAL" +
				" , req.MKT_SERIAL_ID" +
				" , req.ACCT_CODE" +
				" , req.CCC" +
				" , nvl(a.item_serial_ccs_real,a.ITEM_SERIAL  ) ITEM_SERIAL " +
				" , nvl(a.doa_old_item_serial_ccs_real,a.DOA_OLD_ITEM_SERIAL )DOA_OLD_ITEM_SERIAL " +
				" , sc.ITEM_CODE " +
				" , sc.ITEM_DESC" +
				" , item.HS_DEFECT_OTHERS" +
				" , item.AC_DEFECT_OTHERS" +
				" , item.AC_DEFECT_REASON" +
				" , sales.STAFF_ID" +
				" , sales.STAFF_NAME" +
				" , sales_assign.CENTRE_CD STAFF_LOC" +
				" , approver.STAFF_ID APPROVER_ID" +
				" , approver.STAFF_NAME APPROVER_NAME" +
				" , approver_assign.CENTRE_CD APPROVER_LOC" +
				" FROM bomweb_order o" +
				" LEFT JOIN bomweb_delivery d ON (o.ORDER_ID = d.ORDER_ID)" +
				" LEFT JOIN bomweb_customer c ON (o.ORDER_ID = c.ORDER_ID)" +
				
				//JOIN HS ITEM
				" LEFT JOIN bomweb_subscribed_item i ON (o.ORDER_ID = i.ORDER_ID)" +
				" LEFT JOIN w_item_product_pos_assgn pos ON (i.ID = pos.ITEM_ID)" +
				" LEFT JOIN bomweb_stock_assgn a ON (o.ORDER_ID = a.ORDER_ID AND a.ITEM_CODE = pos.POS_ITEM_CD AND a.STATUS_ID in( '19','35')) " +
				" LEFT JOIN bomweb_stock_catalog sc ON (a.ITEM_CODE = sc.ITEM_CODE)" +
				
				//JOIN DOA REQUEST
				" LEFT JOIN bomweb_doa_request req ON (o.ORDER_ID = req.ORDER_ID AND req.SEQ_NO = :seqNo AND req.status = o.reason_cd)" +
				" LEFT JOIN bomweb_doa_item item ON (req.ORDER_ID = item.ORDER_ID AND item.SEQ_NO = :seqNo)" +
				
				//JOIN SALES / APPROVER
				" LEFT JOIN bomweb_sales_profile sales ON (sales.STAFF_ID = req.CREATE_BY AND SYSDATE BETWEEN sales.START_DATE AND nvl(sales.END_DATE, SYSDATE))" +
				" LEFT JOIN bomweb_sales_assignment sales_assign ON (sales_assign.STAFF_ID = sales.STAFF_ID AND SYSDATE BETWEEN sales_assign.START_DATE AND nvl(sales_assign.END_DATE, SYSDATE))" +
				" LEFT JOIN bomweb_approval_log l ON (l.ORDER_ID = o.ORDER_ID AND l.AUTHORIZED_ID = req.AUTHORIZED_ID)" +
				" LEFT JOIN bomweb_sales_profile approver ON (approver.STAFF_ID = l.AUTHORIZED_BY AND SYSDATE BETWEEN approver.START_DATE AND nvl(approver.END_DATE, SYSDATE))" +
				" LEFT JOIN bomweb_sales_assignment approver_assign ON (approver_assign.STAFF_ID = approver.STAFF_ID AND SYSDATE BETWEEN approver_assign.START_DATE AND nvl(approver_assign.END_DATE, SYSDATE))" +
				
				" WHERE o.ORDER_ID = :orderId" +
				" AND o.LOB = 'MOB'" +
				" AND i.TYPE = 'HS'" +
				" AND sc.ITEM_TYPE = '01'";
		List<RptRetCountRejDTO> itemList = Collections.emptyList();
		
		ParameterizedRowMapper<RptRetCountRejDTO> mapper = new ParameterizedRowMapper<RptRetCountRejDTO>() {
			public RptRetCountRejDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				RptRetCountRejDTO dto = new RptRetCountRejDTO();
				dto.setOrderId(rs.getString("ORDER_ID"));
				dto.setSpecialReuqest(rs.getString("REMARKS"));
				dto.setCustName(rs.getString("CUSTOMER_NAME"));
				dto.setMobileNo(rs.getString("MSISDN"));
				dto.setFirstDeliveryDate(Utility.date2String(rs.getTimestamp("ACTUAL_DELIVERY_DATE"), "dd/MM/yyyy"));		
				dto.setDeliveryDate(rs.getDate("DELIVERY_DATE"));			
				dto.setRequestClaimDate(Utility.date2String(rs.getTimestamp("REQUEST_DATE"), "dd/MM/yyyy HH:mm:ss"));
				dto.setHandsetModel(rs.getString("ITEM_DESC"));
				dto.setImei(rs.getString("DOA_OLD_ITEM_SERIAL"));
				dto.setChangeImei(rs.getString("ITEM_SERIAL"));
				dto.setHsDefectOthers(rs.getString("HS_DEFECT_OTHERS"));
				dto.setAcDefectOthers(rs.getString("AC_DEFECT_OTHERS"));
				dto.setAcDefectReason(rs.getString("AC_DEFECT_REASON"));
				dto.setStaffId(rs.getString("STAFF_ID"));
				dto.setStaffName(rs.getString("STAFF_NAME"));
				dto.setStaffLoc(rs.getString("STAFF_LOC"));
				dto.setApproverId(rs.getString("APPROVER_ID"));
				dto.setApproverName(rs.getString("APPROVER_NAME"));
				dto.setApproverLoc(rs.getString("APPROVER_LOC"));
				dto.setApprovalSerial(rs.getString("APPROVAL_SERIAL"));
				dto.setMktSerialId(rs.getString("MKT_SERIAL_ID"));
				dto.setAcctCode(rs.getString("ACCT_CODE"));
				dto.setCcc(rs.getString("CCC"));
				return dto;
			}
		};
		
		try {
			if (logger.isDebugEnabled()) {
				logger.debug("retrieveRptRetCountRejDTO() @ MobCcsDoaRequestDAO: " + getInitDoaRequestDTO);
			}
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("orderId", orderId);
			params.addValue("seqNo", seqNo);
			
			itemList = simpleJdbcTemplate.query(getInitDoaRequestDTO, mapper, params);
		} catch (EmptyResultDataAccessException erdae) {
			if (logger.isInfoEnabled()) {
				logger.info("EmptyResultDataAccessException in retrieveRptRetCountRejDTO()");
			}
			itemList = Collections.emptyList();
		} catch (Exception e) {
			if (logger.isInfoEnabled()) {
				logger.info("Exception caught in retrieveRptRetCountRejDTO():", e);
			}
			throw new DAOException(e.getMessage(), e);
		}
		return (itemList == null || itemList.isEmpty()) ? null : itemList.get(0);
	}
	
	public List<RptRetCountRejDTO> retrieveSBSRptRetCountRejDTO(String orderId, int seqNo) throws DAOException {
		if (logger.isDebugEnabled()) {
			logger.debug("retrieveSBSRptRetCountRejDTO is called");
		}
		final String getSBSInitDoaRequestDTO ="SELECT "
				+ "o.ORDER_ID , "
				+ "o.ORDER_STATUS , "
				+ "o.REASON_CD , "
				+ "nvl(bo.ORDER_NATURE,'ACQ') ORDER_NATURE, "
				+ "nvl(req.DOA_TYPE, 'DOA') DOA_TYPE , req.STATUS, "
				+ "c.TITLE || ' ' || c.LAST_NAME || ' ' || c.FIRST_NAME CUSTOMER_NAME  , "
				+ "o.MSISDN, "
				+ "d.ACTUAL_DELIVERY_DATE ,"
				+" d.DELIVERY_DATE , "
				+ "req.ROWID , "
				+ "req.REQUEST_DATE , "
				+ "req.REMARKS, "
				+ "req.AUTHORIZED_ID ,"
				+ "req.MKT_SERIAL_ID , "
				+ "req.APPROVAL_SERIAL , "
				+ "req.ACCT_CODE, req.CCC, "
				+ "bsc.ITEM_DESC, "
				+" item.HS_DEFECT_OTHERS, " 
				+" item.AC_DEFECT_OTHERS, " 
				+" item.AC_DEFECT_REASON,"
				+ "item.DOA_ITEM_SERIAL, " 
				+ "sales.STAFF_ID , sales.STAFF_NAME, "
				+ "sales_assign.CENTRE_CD STAFF_LOC, "
				+ "approver.STAFF_ID APPROVER_ID ,"
				+ "approver.STAFF_NAME APPROVER_NAME ,"
				+ "approver_assign.CENTRE_CD APPROVER_LOC "
				+ "FROM bomweb_order o "
				+ "LEFT JOIN bomweb_order_mob bo ON (o.ORDER_ID = bo.ORDER_ID) "
				+ "LEFT JOIN bomweb_delivery d ON (o.ORDER_ID = d.ORDER_ID) "
				+ "LEFT JOIN bomweb_customer c ON (o.ORDER_ID = c.ORDER_ID) "
				+ "LEFT JOIN bomweb_doa_request req ON (o.ORDER_ID = req.ORDER_ID AND req.SEQ_NO = :seqNo AND req.status = o.reason_cd) "
				+ "LEFT JOIN bomweb_doa_item item on (o.order_id = item.order_id and item.SEQ_NO = :seqNo) "
				+ "LEFT JOIN bomweb_stock_catalog bsc on (item.DOA_ITEM_CODE = bsc.ITEM_CODE) "
				+ "LEFT JOIN bomweb_approval_log l ON (l.ORDER_ID = o.ORDER_ID AND l.AUTHORIZED_ID = req.AUTHORIZED_ID) "
				+ "LEFT JOIN bomweb_sales_profile sales ON (sales.STAFF_ID = req.CREATE_BY AND l.LAST_UPD_DATE  BETWEEN sales.START_DATE AND nvl(sales.END_DATE, SYSDATE)) "
				+ "LEFT JOIN bomweb_sales_assignment sales_assign ON (sales_assign.STAFF_ID = sales.STAFF_ID AND l.LAST_UPD_DATE BETWEEN sales_assign.START_DATE AND nvl(sales_assign.END_DATE, SYSDATE)) "	
				+ "LEFT JOIN bomweb_sales_profile approver ON (approver.STAFF_ID = l.AUTHORIZED_BY AND l.LAST_UPD_DATE  BETWEEN approver.START_DATE AND nvl(approver.END_DATE, SYSDATE)) "
				+ "LEFT JOIN bomweb_sales_assignment approver_assign ON (approver_assign.STAFF_ID = approver.STAFF_ID AND l.LAST_UPD_DATE  BETWEEN approver_assign.START_DATE AND nvl(approver_assign.END_DATE, SYSDATE)) "
				+ "WHERE o.ORDER_ID = :orderId AND o.LOB = 'MOB'";
				
		List<RptRetCountRejDTO> itemList = Collections.emptyList();
		
		ParameterizedRowMapper<RptRetCountRejDTO> mapper = new ParameterizedRowMapper<RptRetCountRejDTO>() {
			public RptRetCountRejDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				RptRetCountRejDTO dto = new RptRetCountRejDTO();
				dto.setOrderId(rs.getString("ORDER_ID"));
				dto.setSpecialReuqest(rs.getString("REMARKS"));
				dto.setCustName(rs.getString("CUSTOMER_NAME"));
				dto.setMobileNo(rs.getString("MSISDN"));
				dto.setFirstDeliveryDate(Utility.date2String(rs.getTimestamp("ACTUAL_DELIVERY_DATE"), "dd/MM/yyyy"));		
				dto.setDeliveryDate(rs.getDate("DELIVERY_DATE"));			
				dto.setRequestClaimDate(Utility.date2String(rs.getTimestamp("REQUEST_DATE"), "dd/MM/yyyy HH:mm:ss"));
				dto.setHandsetModel(rs.getString("ITEM_DESC"));
				dto.setImei(rs.getString("DOA_ITEM_SERIAL"));
				//dto.setChangeImei(rs.getString("ITEM_SERIAL"));
				dto.setHsDefectOthers(rs.getString("HS_DEFECT_OTHERS"));
			    dto.setAcDefectOthers(rs.getString("AC_DEFECT_OTHERS"));
				dto.setAcDefectReason(rs.getString("AC_DEFECT_REASON"));
				dto.setStaffId(rs.getString("STAFF_ID"));
				dto.setStaffName(rs.getString("STAFF_NAME"));
				dto.setStaffLoc(rs.getString("STAFF_LOC"));
				dto.setApproverId(rs.getString("APPROVER_ID"));
				dto.setApproverName(rs.getString("APPROVER_NAME"));
				dto.setApproverLoc(rs.getString("APPROVER_LOC"));
				dto.setApprovalSerial(rs.getString("APPROVAL_SERIAL"));
				dto.setMktSerialId(rs.getString("MKT_SERIAL_ID"));
				dto.setAcctCode(rs.getString("ACCT_CODE"));
				dto.setCcc(rs.getString("CCC"));
				return dto;
			}
		};
		
		try {
			if (logger.isDebugEnabled()) {
				logger.debug("retrieveSBSRptRetCountRejDTO() @ MobCcsDoaRequestDAO: " + getSBSInitDoaRequestDTO);
			}
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("orderId", orderId);
			params.addValue("seqNo", seqNo);
			
			itemList = simpleJdbcTemplate.query(getSBSInitDoaRequestDTO, mapper, params);
		} catch (EmptyResultDataAccessException erdae) {
			if (logger.isInfoEnabled()) {
				logger.info("EmptyResultDataAccessException in retrieveSBSRptRetCountRejDTO()");
			}
			itemList = Collections.emptyList();
		} catch (Exception e) {
			if (logger.isInfoEnabled()) {
				logger.info("Exception caught in retrieveSBSRptRetCountRejDTO():"+ e.getMessage());
			}
			throw new DAOException(e.getMessage(), e);
		}
		//return (itemList == null || itemList.isEmpty()) ? null : itemList.get(0);
		return itemList;
	}
	
	public List<MobAppFormDTO> getReportListByFormIds(String channelId, String orderNature, List<String> formIds) throws DAOException { 
		logger.debug("getReportListByFormIds() is called");
		List<MobAppFormDTO> list = new ArrayList<MobAppFormDTO>();
		List<MobAppFormDTO> sortResult = new ArrayList<MobAppFormDTO>();
		
		String SQL= "select MA.FORM_ID, MA.FORM_DESC, MA.JASPER_TEMPLATE_NAME, :channelId CHANNEL_ID, 'O' MO_IND, MA.NATURE, " +
				 " NVL(MA.COMPANY_COPY_IND, 'N') COMPANY_COPY_IND, " +
				 " NVL(MA.COP_IND, 'N') COP_IND, " +
				 " NVL(MA.DEL_IND, 'N') DEL_IND, " +
				 " NVL(MA.DMS_IND, 'N') DMS_IND " + 
			     " from BOMWEB_MOB_APPFORM MA " + 
			     " where MA.nature = :orderNature " +
			     " and MA.form_id in (:formIds) " +
			     " order by MA.form_id ";

		ParameterizedRowMapper<MobAppFormDTO> mapper = new ParameterizedRowMapper<MobAppFormDTO>() {
			public MobAppFormDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				MobAppFormDTO mobAppForm = new MobAppFormDTO();
				mobAppForm.setAppFormId(rs.getString("FORM_ID"));
				mobAppForm.setAppFormDesc(rs.getString("FORM_DESC"));
				mobAppForm.setJasperTemplateName(rs.getString("JASPER_TEMPLATE_NAME"));
				mobAppForm.setChannelId(rs.getString("CHANNEL_ID"));
				mobAppForm.setNature(rs.getString("NATURE"));
				mobAppForm.setCompanyCopyInd(rs.getString("COMPANY_COPY_IND"));
				mobAppForm.setCopInd(rs.getString("COP_IND"));
				mobAppForm.setDelInd(rs.getString("DEL_IND"));
				mobAppForm.setDmsInd(rs.getString("DMS_IND"));
				if("M".equalsIgnoreCase(rs.getString("MO_IND"))){
					mobAppForm.setMandatory(true);
				}else{
					mobAppForm.setMandatory(false);
				}
				return mobAppForm;
			}
		};

		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("formIds",formIds);
			params.addValue("orderNature",orderNature);
			params.addValue("channelId",channelId);
			
			list = simpleJdbcTemplate.query(SQL, mapper,params);
			
			if (list == null || list.isEmpty()) {
				return sortResult;
			} else {
				for (MobAppFormDTO temp : list) {
					int i = formIds.indexOf(temp.getAppFormId());
					if (i >= sortResult.size()) {
						sortResult.add(temp);
					} else {
						sortResult.add(i, temp);
					}
					
				}
				return sortResult;
			}
			
		} catch (EmptyResultDataAccessException erdae) {
			throw new AppRuntimeException(erdae);
		} catch (Exception e) {
			logger.error("Exception caught in getReportListByFormIds()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	public boolean isMob0060Optout(String orderId) throws DAOException { 
		logger.debug("isMob0060Optout() is called");
		List<String> list = new ArrayList<String>();
		String SQL="select mob0060_opt_out_ind from  BOMWEB_SUB where order_id = :orderId";
		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {

			public String mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				String result = null;
				result = rs.getString("mob0060_opt_out_ind");
				
				return result;
			}
		};

		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("orderId", orderId);
			list = simpleJdbcTemplate.query(SQL, mapper,params);
		} catch (EmptyResultDataAccessException erdae) {
			throw new AppRuntimeException(erdae);
		} catch (Exception e) {
			logger.error("Exception caught in hasDeliveryItem()", e);
			throw new DAOException(e.getMessage(), e);
		}
		if (!(list == null || list.isEmpty())&& "Y".equalsIgnoreCase(list.get(0)))
			return true;
		else 
			return false;
	}
	
}
