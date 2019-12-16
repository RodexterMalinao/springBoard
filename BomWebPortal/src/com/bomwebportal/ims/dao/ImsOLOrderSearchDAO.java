package com.bomwebportal.ims.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.bomwebportal.dao.BaseDAO;

import com.bomwebportal.dto.OrderDTO;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.ims.dto.ImsCustomerOrderHistoryDTO;

public class ImsOLOrderSearchDAO extends BaseDAO {
	public List<OrderDTO> getOrderList(String shopCode, String lob,
			String orderStatus, String startDate, String endDate,
			String saleCd, String orderIdStr) throws DAOException {
		List<OrderDTO> orderList = new ArrayList<OrderDTO>();

		String SQLsearchByOrderId = "SELECT bo.order_id,    "
				+ "     DECODE(basket_type_t.basket_type, 6, 'Y', DECODE(TRUNC(bo.APP_DATE), TRUNC(sysdate), 'Y', 'N')) today_order_flag,    "
				+ "     bo.shop_cd,    "
				+ "     bo.order_status,    "
				+ "     bo.ocid,    "
				+ "     bo.app_date,    "
				+ "     bo.ERR_MSG,    "
				+ "     basket_type_t.basket_type,    "
				+ "     bo.lob,    "
				+ "     DECODE(BO.LOB, 'MOB', BO.MSISDN, BC.SERVICE_NUM) SERVICE_NUM,   "
				+ "     boi.login_id IMS_LOGIN_ID,    "
				+ "     bo.reason_cd,   "
				+ "	   bo.check_point,		 "
				+ "     BC.LAST_NAME ||' ' ||BC.FIRST_NAME cust_full_name    "
				+ "   FROM bomweb_order bo,    "
				+ "     (SELECT DISTINCT bsi.order_id,    "
				+ "       b.type basket_type    "
				+ "     FROM bomweb_subscribed_item bsi,    "
				+ "       w_basket b    "
				+ "     WHERE b.id = bsi.basket_id    "
				+ "     ) basket_type_t,    "
				+ "     bomweb_customer bc,    "
				+ "     bomweb_order_ims boi    "
				+ "   WHERE bo.order_id = basket_type_t.order_id    "
				+ "   AND bo.order_id   = :orderId   "
				+ "   AND bo.shop_cd    = DECODE(:shopCd, 'ALL', BO.SHOP_CD, :shopCd)    "
				+ "   AND bo.ORDER_ID   = bc.ORDER_ID    "
				+ "   AND bo.ORDER_ID   = boi.ORDER_ID(+)    "
				+ "   AND bo.lob != 'LTS'  "

				+" and BO.SHOP_CD not in " +
				"      (select CL.CODE_ID " + 
				"         from BOMWEB_CODE_LKUP CL " + 
				"        where CL.CODE_TYPE like 'CCS_CH')   " //--ex CCS order

				+ " UNION  "
				+ "       "//-- LTS Order
				+ "     SELECT bo.order_id,    "
				+ "     DECODE(bols.sb_status, 'S', 'Y', 'N') today_order_flag,    "//-- allow recall LTS suspend order
				+ "     bo.shop_cd,    "
				+ "     bols.sb_status order_status,    "
				+ "     bo.ocid,    " + "     bo.app_date,    "
				+ "     bo.ERR_MSG,    " + "     null BASKET_TYPE,    "
				+ "     bo.lob,   " + "     bos.srv_num SERVICE_NUM,   "
				+ "     null IMS_LOGIN_ID,    "
				+ "     bols.rea_cd reason_cd,   " + "	   bo.check_point,		 "
				+ "     BC.LAST_NAME ||' ' ||BC.FIRST_NAME cust_full_name    "
				+ "   FROM bomweb_order bo,    "
				+ "        bomweb_customer bc,    "
				+ "        bomweb_order_service bos,  "
				+ "        bomweb_order_latest_status bols,  "
				+ "        bomweb_acct ba      "
				+ "   WHERE bo.order_id = bos.order_id  "
				+ "   AND bos.order_id = bols.order_id  "
				+ "   AND bos.dtl_id =  bols.dtl_id  "
				+ "   AND bo.order_id   = :orderId    "
				+ "   AND bo.shop_cd    = DECODE(:shopCd, 'ALL', BO.SHOP_CD, :shopCd)    "
				+ "   AND bos.ORDER_ID = ba.ORDER_ID  "
				+ "   AND bos.acct_no = ba.acct_no   "
				+ "   AND bc.ORDER_ID = ba.ORDER_ID   "
				+ "   AND bc.cust_no = ba.cust_no   "
				+ "   AND bo.lob = 'LTS'  " + "   ORDER BY order_id ";

		String SQLSearchByMOB = "select BO.ORDER_ID, "
				+ "       DECODE(BASKET_TYPE_T.BASKET_TYPE, "
				+ "              6, "
				+ "              'Y', "
				+ "              DECODE(TRUNC(BO.APP_DATE), TRUNC(sysdate), 'Y', 'N')) TODAY_ORDER_FLAG, "
				+ "       BO.SHOP_CD, "
				+ "       BO.ORDER_STATUS, "
				+ "       BO.OCID, "
				+ "       BO.APP_DATE, "
				+ "       BO.ERR_MSG, "
				+ "       BASKET_TYPE_T.BASKET_TYPE, "
				+ "       BO.LOB, "
				+ "       DECODE(BO.LOB, 'MOB', BO.MSISDN, BC.SERVICE_NUM) SERVICE_NUM, "
				+ "       null IMS_LOGIN_ID, "
				+ "       BO.REASON_CD, "
				+ "       BO.CHECK_POINT, "
				+ "       BC.LAST_NAME || ' ' || BC.FIRST_NAME CUST_FULL_NAME "
				+ "  from BOMWEB_ORDER BO, "
				+ "       (select distinct BSI.ORDER_ID, B.TYPE BASKET_TYPE "
				+ "          from BOMWEB_SUBSCRIBED_ITEM BSI, W_BASKET B "
				+ "         where B.ID = BSI.BASKET_ID) BASKET_TYPE_T, "
				+ "       BOMWEB_CUSTOMER BC " + " where BO.LOB = 'MOB'  "
				+ "   AND bo.shop_cd    = DECODE(:shopCd, 'ALL', BO.SHOP_CD, :shopCd)    "

				+" and BO.SHOP_CD not in " +
				"      (select CL.CODE_ID " + 
				"         from BOMWEB_CODE_LKUP CL " + 
				"        where CL.CODE_TYPE like 'CCS_CH')   "//--ex CCS shop order

				+ "   and BO.sales_cd = nvl(:saleCd, BO.sales_cd) "
				+ "   and TRUNC(BO.app_DATE) between  "//--range search
				+ "       TRUNC(TO_DATE(:startDate, 'dd/mm/yyyy')) and "
				+ "       TRUNC(TO_DATE(:endDate, 'dd/mm/yyyy')) "
				+ "   and BO.ORDER_ID = BASKET_TYPE_T.ORDER_ID "
				+ "   and BO.ORDER_ID = BC.ORDER_ID ";

		if ("PENDING".equalsIgnoreCase(orderStatus)) {
			SQLSearchByMOB = SQLSearchByMOB
					+ "   and BO.ORDER_STATUS  in( 'INITIAL' ,'SIGNOFF','PENDING','PROCESS','FAILED' )";
		}else if ("CANCEL".equalsIgnoreCase(orderStatus)){
			SQLSearchByMOB = SQLSearchByMOB
			+ "   and BO.ORDER_STATUS  ='VOID'";
		}

		String SQLSearchByIMS = "select BO.ORDER_ID, "
				+ "       DECODE(TRUNC(BO.APP_DATE), TRUNC(sysdate), 'Y', 'N') TODAY_ORDER_FLAG, "
				+ "       BO.SHOP_CD, "
				+ "       BO.ORDER_STATUS, "
				+ "       BO.OCID, "
				+ "       BO.APP_DATE, "
				+ "       BO.ERR_MSG, "
				+ "       null BASKET_TYPE, "
				+ "       BO.LOB, "
				+ "       DECODE(BO.LOB, 'MOB', BO.MSISDN, BC.SERVICE_NUM) SERVICE_NUM, "
				+ "       BOI.LOGIN_ID IMS_LOGIN_ID, "
				+ "       BO.REASON_CD, "
				+ "       BO.CHECK_POINT, "
				+ "       BC.LAST_NAME || ' ' || BC.FIRST_NAME CUST_FULL_NAME "
				+ "  from BOMWEB_ORDER BO, BOMWEB_CUSTOMER BC, BOMWEB_ORDER_IMS BOI "
				+ " where BO.LOB = 'IMS'  "//-- IMS query
				+ "   AND bo.shop_cd    = DECODE(:shopCd, 'ALL', BO.SHOP_CD, :shopCd)    "
				+ "   and TRUNC(BO.APP_DATE) between  "//--range search
				+ "       TRUNC(TO_DATE(:startDate, 'dd/mm/yyyy')) and "
				+ "       TRUNC(TO_DATE(:endDate, 'dd/mm/yyyy')) "
				+ "   and BO.ORDER_ID = BC.ORDER_ID "
				+ "   and BO.ORDER_ID = BOI.ORDER_ID(+) ";

		if (StringUtils.isNotBlank(saleCd)) {
			String SQLsubIMSsaleCd = "   and BO.SALES_CD in  " //-- first convert to original staff id and then search
					+ "       (select ORG_STAFF_ID "
					+ "          from (select ORG_STAFF_ID, STAFF_ID "
					+ "                  from BOMWEB_SALES_PROFILE "
					+ "                 where STAFF_ID = :saleCd "
					+ "                 order by END_DATE desc) TMP "
					+ "         where ROWNUM = 1) ";

			SQLSearchByIMS += SQLsubIMSsaleCd;
		}

		if ("PENDING".equalsIgnoreCase(orderStatus)) {
			SQLSearchByIMS = SQLSearchByIMS
					+ "   and BO.ORDER_STATUS in  "
					+ "       (select CODE from W_CODE_LKUP where GRP_ID = 'SB_IMS_ACQ_PENDING')";
		}else if ("CANCEL".equalsIgnoreCase(orderStatus)){
			SQLSearchByIMS = SQLSearchByIMS
			+ "   and BO.order_status='20'   "; //-- cancelled Springboard IMS order and displayed as cancelled
		}

		String SQLSearchByLTS =

		"select  "
				+ "       BO.ORDER_ID, "
				+ "       DECODE(BOLS.SB_STATUS, 'S', 'Y', 'N') TODAY_ORDER_FLAG, "
				+ "       BO.SHOP_CD, "
				+ "       BOLS.SB_STATUS ORDER_STATUS, "
				+ "       BO.OCID, "
				+ "       BO.APP_DATE, "
				+ "       BO.ERR_MSG, "
				+ "       null BASKET_TYPE, "
				+ "       BO.LOB, "
				+ "       BOS.SRV_NUM SERVICE_NUM, "
				+ "       null IMS_LOGIN_ID, "
				+ "       BOLS.REA_CD REASON_CD, "
				+ "       BO.CHECK_POINT, "
				+ "       BC.LAST_NAME || ' ' || BC.FIRST_NAME CUST_FULL_NAME "
				+ "  from BOMWEB_ORDER               BO, "
				+ "       BOMWEB_CUSTOMER            BC, "
				+ "       BOMWEB_ORDER_SERVICE       BOS, "
				+ "       BOMWEB_ORDER_LATEST_STATUS BOLS, "
				+ "       BOMWEB_ACCT                BA "
				+ " where BO.LOB = 'LTS' " 
				+ "   AND bo.shop_cd    = DECODE(:shopCd, 'ALL', BO.SHOP_CD, :shopCd)    "
				+ "   and TRUNC(BO.APP_DATE) between  " //--range search
				+ "       TRUNC(TO_DATE(:startDate, 'dd/mm/yyyy')) and "
				+ "       TRUNC(TO_DATE(:endDate, 'dd/mm/yyyy')) "
				+ "   and BO.ORDER_ID = BOS.ORDER_ID "
				+ "   and BOS.ORDER_ID = BOLS.ORDER_ID "
				+ "   and BOS.DTL_ID = BOLS.DTL_ID "
				+ "   and BOS.ORDER_ID = BA.ORDER_ID "
				+ "   and BOS.ACCT_NO = BA.ACCT_NO "
				+ "   and BC.ORDER_ID = BA.ORDER_ID "
				+ "   and BC.CUST_NO = BA.CUST_NO "
				+ "   and BO.order_id =BA.ORDER_ID ";

		if (StringUtils.isNotBlank(saleCd)) {
			String subSQLSearchByLTS = "   and BO.STAFF_NUM = (select ORG_STAFF_ID "
					+ "                         from BOMWEB_SALES_PROFILE "
					+ "                        where STAFF_ID = :saleCd "
					+ "                          and ROWNUM = 1) ";

			SQLSearchByLTS += subSQLSearchByLTS;
		}

		if ("PENDING".equalsIgnoreCase(orderStatus)) {
			SQLSearchByLTS = SQLSearchByLTS
					+ "   and BOLS.SB_STATUS in (select CODE from W_CODE_LKUP where GRP_ID = 'LTS_RT_PENDING_STS')";
		}else if ("CANCEL".equalsIgnoreCase(orderStatus)){
			SQLSearchByLTS = SQLSearchByLTS
					+ "   and BOLS.SB_STATUS IN (SELECT CODE FROM W_CODE_LKUP WHERE GRP_ID = 'LTS_RT_CANCEL_STS')	 "; //-- FILTER CANCEL STATUS)
		}

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
		MapSqlParameterSource params = new MapSqlParameterSource();

		// parm
		if (StringUtils.isNotBlank(orderIdStr)) {
			params.addValue("orderId", orderIdStr);
			params.addValue("shopCd", shopCode);
		} else {

			params.addValue("shopCd", shopCode);
			params.addValue("startDate", startDate);
			params.addValue("endDate", endDate);
			params.addValue("saleCd", saleCd);

		}

		try {
			logger.debug("getOrderSummaryList @ OrderDAO: "
					+ SQLsearchByOrderId);

			if (StringUtils.isNotBlank(orderIdStr)) {
				logger.info("orderSearch by orderId :" + orderIdStr);
				orderList = simpleJdbcTemplate.query(SQLsearchByOrderId,
						mapper, params);
			} else {
				logger.info("orderSearch by other parameter");
				logger.info("shopCode :" + shopCode);
				logger.info("lob :" + lob);
				logger.info("orderStatus :" + orderStatus);
				logger.info("startDate :" + startDate);
				logger.info("endDate :" + endDate);
				logger.info("saleCd :" + saleCd);
				logger.info("SQLSearchByMOB :" + SQLSearchByMOB);
				logger.info("SQLSearchByIMS :" + SQLSearchByIMS);
				logger.info("SQLSearchByIMS :" + SQLSearchByLTS);
				String finialSQL = "";
				if ("MOB".equals(lob)) {
					finialSQL = SQLSearchByMOB;

				} else if ("IMS".equals(lob)) {
					finialSQL = SQLSearchByIMS;

				} else if ("LTS".equals(lob)) {
					finialSQL = SQLSearchByLTS;

				} else {
					finialSQL = SQLSearchByMOB + "union " + SQLSearchByIMS
							+ "union " + SQLSearchByLTS;
				}

				finialSQL += "order by order_id";
				logger.info("finialSQL :" + finialSQL);

				orderList = simpleJdbcTemplate.query(finialSQL, mapper, params);
			}

		} catch (EmptyResultDataAccessException erdae) {
			logger.info("getOrderList EmptyResultDataAccessException");

			orderList = null;
		} catch (Exception e) {
			logger.error("Exception caught in getOrderList()", e);

			throw new DAOException(e.getMessage(), e);
		}
		return orderList;

	}
	
	private static String getCustomerOrderHistoryDTOAllSQL = 
		"SELECT " +
		" decode(O.ORDER_STATUS, '10',decode(o.REASON_CD,'R004',' for Profile Mismatch','R005',' for C Order','R006',' for both Profile Mismatch and C Order',''),'') suspend_reason, " +
		"O.ORDER_ID, " 
		+"  O.OCID, " 
		+"  O.LOB, "
//		+"  O.ESIG_EMAIL_ADDR, "
		+"  BC.EMAIL_ADDR, "
		+"  OI.LOGIN_ID IMS_LOGIN_ID, " 
		+"  O.APP_DATE, " 
		+"  DECODE(O.LOB, 'LTS', OS.ORDER_STATUS, O.ORDER_STATUS) ORDER_STATUS, " 
		+"  DECODE(O.LOB, 'LTS', OS.ERR_MSG, O.ERR_MSG) ERR_MSG, " 
		+"  C.ID_DOC_NUM, " 
		+"  C.ID_DOC_TYPE, " 
		+"  DECODE(O.LOB, 'MOB', O.MSISDN, 'LTS', OS.SRV_NUM, C.SERVICE_NUM) SERVICE_NUM, " 
		+"  O.MSISDN, " 
		+"  C.LAST_NAME " 
		+"  ||' ' " 
		+"  ||C.FIRST_NAME cust_full_name, " 
		+"  o.check_point, o.reason_cd , "
		+"  (Select case when count(*) > 0 then'Y' ELSE'N' END " 
		+" from bomweb_ord_email_req boer where boer.order_id = O.ORDER_ID and SENT_DATE is not null) Email_sent "
		+"FROM BOMWEB_ORDER O, " 
		+"  BOMWEB_CUSTOMER C, " 
		+"  BOMWEB_ORDER_IMS OI, " 
		+"  BOMWEB_CONTACT BC, "
		+"  (SELECT bos.order_id ORDER_ID, " 
		+"    bos.srv_num SRV_NUM, " 
		+"    bols.sb_status ORDER_STATUS, " 
		+"    bols.rea_cd ERR_MSG " 
		+"  FROM BOMWEB_ORDER_SERVICE bos, " 
		+"    BOMWEB_ORDER_LATEST_STATUS bols " 
		+"  WHERE bos.order_id  = bols.order_id " 
		+"  AND bos.dtl_id      = bols.dtl_id " 
		+"  AND bos.type_of_srv = 'TEL' " 
		+"  ) OS " 
		+"WHERE O.ORDER_ID              = C.ORDER_ID " 
		+"AND O.SHOP_CD  in (Select CODE_ID from BOMWEB_CODE_LKUP sbof	where sbof.code_type = 'IMS_NON_RETAIL_SHOP_CD')  "
		+"AND O.LOB = 'IMS' "
		+"AND O.ORDER_ID                = OI.ORDER_ID " 
		+"AND O.ORDER_ID                = OS.ORDER_ID(+) "
		+"AND O.ORDER_ID                = BC.ORDER_ID(+) "
		+"AND (:docNum IS NULL OR NVL(C.ID_DOC_NUM, 'XXX')      = NVL(:docNum, NVL(C.ID_DOC_NUM, 'XXX'))) " 
		+"AND (:docType IS NULL OR NVL(C.ID_DOC_TYPE, 'XXX')    = NVL(:docType, NVL(C.ID_DOC_TYPE, 'XXX'))) " 
		+"AND (:loginId IS NULL OR NVL(OI.LOGIN_ID, 'XXX')      like '%'||NVL(:loginId, NVL(OI.LOGIN_ID, 'XXX'))||'%') "
		+"AND (:srvNum IS NULL OR NVL(OS.SRV_NUM, 'XXX')        = NVL(:srvNum, NVL(OS.SRV_NUM, 'XXX'))) "
//		+"AND NVL(O.ESIG_EMAIL_ADDR, 'XXX')   like '%'||NVL(?, NVL(O.ESIG_EMAIL_ADDR, 'XXX'))||'%' "
		+"AND (:emailAddr IS NULL OR NVL(BC.EMAIL_ADDR, 'XXX')  like '%'||NVL(:emailAddr, NVL(BC.EMAIL_ADDR, 'XXX'))||'%') "
		+"AND (:contactNum IS NULL OR ( "
		+"       BC.CONTACT_PHONE = :contactNum OR "
		+"       BC.CONTACT_MOBILE = :contactNum )) "
		+"AND (:serviceNum IS NULL OR NVL(C.SERVICE_NUM, 'XXX') = NVL(:serviceNum, NVL(C.SERVICE_NUM, 'XXX'))) "
		+"AND (:orderId IS NULL OR NVL(O.ORDER_ID, 'XXX')       = NVL(:orderId, NVL(O.ORDER_ID, 'XXX'))) "
		+"AND (NVL(OI.IMS_ORDER_TYPE, 'XXX') = NVL(:orderType, 'XXX')) "
		+"order by O.CREATE_DATE desc";

	
	public boolean IsSaleResendEmailAllowed(String SalesCategory) throws DAOException{
		
		logger.info("IsSaleResendEmailAllowed is called");
		String is_resend_email_allowed = "";
		String sql = "Select count(*) IS_RESEND_EMAIL_ALLOWED " +
				" from w_code_lkup " +
				" where grp_id = 'SB_IMS_OE_SALES_CA' " +
				" and code = ? " +
				" and description = 'Y'";
		
		try{
			
			is_resend_email_allowed = simpleJdbcTemplate.queryForObject(sql, String.class, SalesCategory);
	
		}catch (Exception e) {
				logger.error("Exception caught in IsSaleResendEmailAllowed()", e);
				throw new DAOException(e.getMessage(), e);
		}
		
		return ("1").equals(is_resend_email_allowed);
		
	}
	
	
	public List<ImsCustomerOrderHistoryDTO> getCustomerOrderHistoryDTOALL(
			String idDocNum, String serviceNum, String idDocType, String loginId, String serviceType, 
			String emailAddress,String orderId, String orderType, String contactNum) throws DAOException {
		
		logger.info(" getCustomerOrderHistoryDTO is called");
		List<ImsCustomerOrderHistoryDTO> itemList = new ArrayList<ImsCustomerOrderHistoryDTO>();

		ParameterizedRowMapper<ImsCustomerOrderHistoryDTO> mapper = new ParameterizedRowMapper<ImsCustomerOrderHistoryDTO>() {
			public ImsCustomerOrderHistoryDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				ImsCustomerOrderHistoryDTO dto = new ImsCustomerOrderHistoryDTO();
				dto.setSuspendReason(rs.getString("suspend_reason"));
				dto.setOrderId(rs.getString("ORDER_ID"));
				dto.setOcid(rs.getString("OCID"));
				dto.setLob(rs.getString("LOB"));
				dto.setImsLoginId(rs.getString("IMS_LOGIN_ID"));
				dto.setAppDate(rs.getTimestamp("APP_DATE"));
				dto.setOrderStatus(rs.getString("ORDER_STATUS"));
				dto.setErrMsg(rs.getString("ERR_MSG"));
				dto.setIdDocNum(rs.getString("ID_DOC_NUM"));
				dto.setServiceNum(rs.getString("SERVICE_NUM"));
				dto.setOrderHistCustName(rs.getString("cust_full_name"));
				dto.setCheckPoint(rs.getString("check_point"));
				dto.setReasonCd(rs.getString("reason_cd"));
//				dto.setEmailAddress(rs.getString("ESIG_EMAIL_ADDR"));
				dto.setEmailAddress(rs.getString("EMAIL_ADDR"));
				dto.setEmailSent(rs.getString("Email_sent"));
				
				return dto;
			}
		};

		try {
			logger.info("getCustomerOrderHistoryDTO() @ CustomerOrderHistoryDTO: "
					+ getCustomerOrderHistoryDTOAllSQL);
			
			idDocNum = idDocNum.trim();
			serviceNum = serviceNum.trim();
			idDocType = idDocType.trim();
			
			if ("BR".equals(idDocType)) {
				idDocType = "BS";
			} else if ("Passport".equals(idDocType)) {
				idDocType = "PASS";
			} else if ("Certificate No".equals(idDocType)) {
				idDocType = "CERT";
			} else if ("NONE".equals(idDocType)) {
				idDocType = null;
			}
			
			if ("".equals(idDocNum)) {
				idDocNum = null;
			}
			
			if ("".equals(serviceNum)) {
				serviceNum = null;
			}
			
			if ("".equals(loginId)) {
				loginId = null;
			}
			
			if(orderType == null || orderType.trim().equals("") ||orderType.equals("PCD"))
				orderType = null;
			
			/*
			 * Eric Ng debug
			 */
			logger.debug("Eric Ng debug, serviceType: "+ serviceType + " serviceNum: " + serviceNum);
			logger.debug("idDocNum: " + idDocNum + " idDocType: " + idDocType + " loginId: " + loginId + " emailAddress: " + emailAddress);
			logger.debug("orderType: " + orderType + " orderId: " + orderId + " contactNum: " + contactNum);
			
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("docNum", idDocNum);
			//params.addValue("msisdn", null);
			params.addValue("docType", idDocType);
			params.addValue("loginId", loginId);
			params.addValue("srvNum", null);
			params.addValue("emailAddr", emailAddress);
			params.addValue("serviceNum", serviceNum);
			params.addValue("orderId", orderId);
			params.addValue("orderType", orderType);
			params.addValue("contactNum", contactNum);
			
//			if ("MRT".equalsIgnoreCase(serviceType)) {
//				itemList = simpleJdbcTemplate.query(
//						getCustomerOrderHistoryDTOAllSQL, mapper, idDocNum,
//						serviceNum, idDocType, loginId, null,emailAddress,null);
//			} else if ("TEL".equalsIgnoreCase(serviceType)){
//				itemList = simpleJdbcTemplate.query(
//						getCustomerOrderHistoryDTOAllSQL, mapper, idDocNum,
//						null, idDocType, loginId, serviceNum,emailAddress,null);
//			} else {
//				
//				itemList = simpleJdbcTemplate.query(
//						getCustomerOrderHistoryDTOAllSQL, mapper, idDocNum,
//						null, idDocType, loginId, null,emailAddress,serviceNum);
//			}

			itemList = simpleJdbcTemplate.query(getCustomerOrderHistoryDTOAllSQL, mapper, params);
			
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException in getCustomerOrderHistoryDTO()");

			itemList = null;
		} catch (Exception e) {
			logger.info("Exception caught in getCustomerOrderHistoryDTO():", e);

			throw new DAOException(e.getMessage(), e);
		}
		return itemList;
	}
}
