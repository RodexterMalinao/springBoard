package com.smartliving.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.dao.EmptyResultDataAccessException;

import com.bomwebportal.dao.BaseDAO;
import com.bomwebportal.exception.DAOException;
import com.pccw.springboard.svc.server.dto.OrderHistoryDTO;
import com.pccw.springboard.svc.server.dto.slv.SlvCustomerDTO;
import com.pccw.springboard.svc.server.dto.slv.SlvServiceDTO;
import com.pccw.util.dao.ParameterizedRowMapperByFieldName;
import com.pccw.util.db.OracleSelectHelper;
import com.pccw.util.db.stringOracleType.OraArrayVarChar2;
import com.smartliving.dto.DropDown;
import com.smartliving.dto.SlvOrderSummaryDTO;

public class SmartLivingDAO extends BaseDAO {
	
	private static final String SQL_GET_ORDER_SEARCH_RESULT = "SELECT SOEV.order_id \"orderId\", " + 
			"  SOEV.profile_id \"profileId\", " + 
			"  SOEV.order_type \"orderType\", " + 
			"  SOEV.end_user_name \"endUserName\", " + 
			"  SOEV.contractor_name \"contractorName\", " + 
			"  TO_CHAR(SOEV.app_date, 'DD-MM-YYYY') \"applicationDate\", " + 
			"  SOEV.service \"service\", " + 
			"  SOEV.referer_staff_id \"refererStaffId\", " + 
			"  (SELECT wdl.description " + 
			"  FROM dic_code_lkup dcl, " + 
			"    w_display_lkup wdl " + 
			"  WHERE wdl.locale   ='en' " + 
			"  AND dcl.grp_id     =wdl.type " + 
			"  AND dcl.description=wdl.id " + 
			"  AND dcl.code       = SOEV.order_status " + 
			"  AND dcl.grp_id     = 'SB_ACTV_STATUS' " + 
			"  ) \"orderStatus\" , " + 
			"  SOEV.sales_staff_id \"salesStaffId\", " + 
			"  SOEV.pending_visit_ind \"pendingVisitInd\", " + 
			"  SOEV.pending_payment_ind \"pendingPaymentInd\", " + 
			"  SOEV.tc_staff_id \"tcStaffId\", " + 
			"  SOEV.discard_by \"discardBy\", " + 
			"  SOEV.sl_cube_id \"slCubeId\", " + 
			"  SOEV.related_order_id \"relatedOrderId\", " + 
			"  TO_CHAR(SOEV.discard_date, 'DD-MM-YYYY') \"discardDate\", " + 
			"  SOEV.cancel_by \"cancelBy\", " + 
			"  TO_CHAR(SOEV.cancel_date, 'DD-MM-YYYY') \"cancelDate\" " + 
			"FROM SLV_ORDER_ENQUIRY_V SOEV, " + 
			"  (SELECT DISTINCT ORDER_ID, " + 
			"    DTL_ID " + 
			"  FROM _SEARCH_V_ " + 
			"  WHERE 1=1 _CRITERIA_ " + 
			"  ) SOSV " + 
			"WHERE SOEV.ORDER_ID = SOSV.ORDER_ID " + 
			"AND SOEV.DTL_ID     = sosv.DTL_ID ";
	private static final String CUS_V = "SLV_ORDER_SEARCH_CUS_V";
	private static final String ACTV_V = "SLV_ORDER_SEARCH_ACTV_V";
	private static final String SM_V = "(SELECT order_id,dtl_id,sales_memo_num FROM BOMWEB_ORD_PPOS_DTL_SLV " + 
			"UNION " + 
			"SELECT sa.order_id, " + 
			"  spd.dtl_id, " + 
			"  sp.sales_memo_num " + 
			"FROM slv_payment sp, " + 
			"  slv_payment_dtl spd, " + 
			"  sb_actv sa " + 
			"WHERE sa.actv_id = sp.actv_id " + 
			"AND sa.actv_id   = spd.actv_id " + 
			"UNION " + 
			"SELECT sa.order_id, " + 
			"  spd.dtl_id, " + 
			"  sp.void_sales_memo_num " + 
			"FROM slv_payment sp, " + 
			"  slv_payment_dtl spd, " + 
			"  sb_actv sa " + 
			"WHERE sa.actv_id = sp.actv_id " + 
			"AND sa.actv_id   = spd.actv_id " + 
			")";
	private static final String PROFILE_V = "SLV_ORDER_SEARCH_PROFILE_V";
	private static final String ORD_V = "SLV_ORDER_SEARCH_ORD_V";
	private static final String TMP_CUS_V = "slv_tmp_order_search_cus_v";
	private static final String TMP_ORD_V = "slv_tmp_order_search_ord_v";
	
	private static final String SQL_GET_TMP_ORDER_SEARCH_RESULT = "SELECT SOEV.order_id \"orderId\", " + 
			"  SOEV.profile_id \"profileId\", " + 
			"  null \"orderType\", " + 
			"  SOEV.end_user_name \"endUserName\", " + 
			"  SOEV.contractor_name \"contractorName\", " + 
			"  TO_CHAR(SOEV.app_date, 'DD-MM-YYYY') \"applicationDate\", " + 
			"  SOEV.service \"service\", " + 
			"  SOEV.referer_staff_id \"refererStaffId\", " + 
			"  (SELECT wdl.description " + 
			"  FROM dic_code_lkup dcl, " + 
			"    w_display_lkup wdl " + 
			"  WHERE wdl.locale   ='en' " + 
			"  AND dcl.grp_id     =wdl.type " + 
			"  AND dcl.description=wdl.id " + 
			"  AND dcl.code       = SOEV.order_status " + 
			"  AND dcl.grp_id     = 'SB_ACTV_STATUS' " + 
			"  ) \"orderStatus\" , " + 
			"  SOEV.sales_staff_id \"salesStaffId\", " + 
			"  SOEV.pending_visit_ind \"pendingVisitInd\", " + 
			"  SOEV.pending_payment_ind \"pendingPaymentInd\", " + 
			"  SOEV.tc_staff_id \"tcStaffId\", " + 
			"  SOEV.discard_by \"discardBy\", " + 
			"  null \"slCubeId\", " + 
			"  null \"relatedOrderId\", " + 
			"  TO_CHAR(SOEV.discard_date, 'DD-MM-YYYY') \"discardDate\", " + 
			"  SOEV.cancel_by \"cancelBy\", " + 
			"  TO_CHAR(SOEV.cancel_date, 'DD-MM-YYYY') \"cancelDate\" " + 
			"FROM slv_tmp_order_enquiry_v SOEV, " + 
			"  (SELECT DISTINCT ORDER_ID, " + 
			"    DTL_ID " + 
			"  FROM _SEARCH_V_ " + 
			"  WHERE 1=1 _CRITERIA_ " + 
			"  ) SOSV " + 
			"WHERE SOEV.ORDER_ID = SOSV.ORDER_ID " + 
			"AND SOEV.DTL_ID     = sosv.DTL_ID";
	
	private static final String SQL_GET_DISCARD_ORDER_SEARCH_RESULT = "SELECT bdo.order_id \"orderId\", " + 
			"  bdo.profile_id \"profileId\", " + 
			"  null \"orderType\", " + 
			"  bdo.cust_name \"endUserName\", " + 
			"  bdo.dev_name \"contractorName\", " + 
			"  TO_CHAR(bdo.create_date, 'DD-MM-YYYY') \"applicationDate\", " + 
			"  bdo.service \"service\", " + 
			"  bdo.ref_sales_id \"refererStaffId\", " + 
			"  'Discarded' \"orderStatus\" , " + 
			"  bdo.sales_id \"salesStaffId\", " + 
			"  null \"pendingVisitInd\", " + 
			"  null \"pendingPaymentInd\", " + 
			"  bdo.tc_staff_id \"tcStaffId\", " + 
			"  bdo.discard_by \"discardBy\", " + 
			"  null \"slCubeId\", " +
			"  null \"relatedOrderId\", " + 
			"  TO_CHAR(bdo.discard_date, 'DD-MM-YYYY') \"discardDate\", " + 
			"  null \"cancelBy\", " + 
			"  null \"cancelDate\" " + 
			"  from bomweb_discarded_order bdo,slv_customer sc where bdo.cust_num = sc.cust_no ";
	
	private static final String SQL_GET_STATUS_LIST = "SELECT wdl.description \"value\", dcl.code \"key\" FROM dic_code_lkup dcl,w_display_lkup wdl WHERE wdl.locale='en' AND dcl.grp_id=wdl.type AND dcl.description=wdl.id AND dcl.code in ('I','D','L','U','A','R','C') AND dcl.grp_id = 'SB_ACTV_STATUS'  ";
	private static final String SQL_GET_TEAM_CODE = "select distinct team_cd \"key\",team_cd \"value\" from bomweb_sales_lkup_v where channel_cd ";
	private static final String SQL_GET_CHANNEL_CODE = "SELECT code_desc " + 
			"FROM bomweb_code_lkup " + 
			"WHERE CODE_TYPE = 'SLV_CHANNEL_PATCH' " + 
			"AND code_id     = ? ";
	
	private static final String SQL_SEARCH_CUST_FIELDS = 
			"SELECT CUST_NO \"custNum\", BOM_CUST_NUM \"parentCustNum\", 'SLV' SYSTEM_ID, " +
			"       ID_DOC_TYPE \"idDocType\", ID_DOC_NUM \"idDocNum\", " + 
			"       TITLE \"title\", LAST_NAME \"lastName\", " +
			"       FIRST_NAME \"firstName\", COMPANY_NAME \"companyName\" "+
			"  FROM SLV_CUSTOMER ";
	
	private static final String SQL_SEARCH_CUST_BY_ID_DOC =
			SQL_SEARCH_CUST_FIELDS +
			" WHERE ID_DOC_TYPE= ? " +  
			"   AND ID_DOC_NUM like ? ";

	private static final String SQL_SEARCH_CUST_BY_ID_DOC_BOM_PARENT_CUST =
			SQL_SEARCH_CUST_FIELDS +
			" WHERE (ID_DOC_TYPE= ? AND ID_DOC_NUM like ?) " +
			"    OR BOM_CUST_NUM = ? ";

	private static final String SQL_SEARCH_CUST_BY_CONTACT_NUM = 
			SQL_SEARCH_CUST_FIELDS +
		    " WHERE contact_no = ? ";
	
	private static final String SQL_SEARCH_CUST_BY_BOM_CUST_NUM = 
			SQL_SEARCH_CUST_FIELDS +
		    " WHERE BOM_CUST_NUM = ? ";

	private static final String SQL_SEARCH_CUST_BY_PROFILE_ID = 
			SQL_SEARCH_CUST_FIELDS +
          	" WHERE cust_no IN (SELECT DISTINCT MAX(CUST_NO) KEEP (DENSE_RANK LAST ORDER BY CUST_TYPE) OVER (PARTITION BY ssca.PROFILE_ID) " +
            "                     FROM slv_srv_cust_assgn ssca, slv_service ss " +
            "                    WHERE ssca.profile_id = ss.profile_id " +
            "                      AND (     SSCA.PROFILE_ID = TO_NUMBER(REGEXP_REPLACE(:PROFILE_ID , '[^0-9]+', '')) " +
            "                             OR SS.SL_CUBE_ID = :PROFILE_ID ) ) ";
		
	public List<SlvCustomerDTO> getSlvCustomer(String pGrpIdDocType, String pGrpIdDocNum, String pBomParentCustNum,
			String pProfileID, String pContactNumber) throws DAOException {
		try {			
			if (StringUtils.isNotEmpty(pGrpIdDocType) && StringUtils.isNotEmpty(pGrpIdDocNum) && StringUtils.isEmpty(pBomParentCustNum)) {
				return simpleJdbcTemplate.query(SQL_SEARCH_CUST_BY_ID_DOC, new ParameterizedRowMapperByFieldName<SlvCustomerDTO>(SlvCustomerDTO.class), pGrpIdDocType, pGrpIdDocNum + "%");
			} else if (StringUtils.isNotEmpty(pGrpIdDocType) && StringUtils.isNotEmpty(pGrpIdDocNum) && StringUtils.isNotEmpty(pBomParentCustNum)) {
				return simpleJdbcTemplate.query(SQL_SEARCH_CUST_BY_ID_DOC_BOM_PARENT_CUST, new ParameterizedRowMapperByFieldName<SlvCustomerDTO>(SlvCustomerDTO.class), pGrpIdDocType, pGrpIdDocNum + "%", pBomParentCustNum);
			} else if (StringUtils.isNotEmpty(pBomParentCustNum)) {
				return simpleJdbcTemplate.query(SQL_SEARCH_CUST_BY_BOM_CUST_NUM, new ParameterizedRowMapperByFieldName<SlvCustomerDTO>(SlvCustomerDTO.class), pBomParentCustNum);
			} else if (StringUtils.isNotEmpty(pProfileID)) {
				Map<String, String> paramMap = new HashMap<String, String>();
				paramMap.put("PROFILE_ID", pProfileID);
				return simpleJdbcTemplate.query (SQL_SEARCH_CUST_BY_PROFILE_ID, new ParameterizedRowMapperByFieldName<SlvCustomerDTO>(SlvCustomerDTO.class), paramMap);
			} else if (StringUtils.isNotEmpty(pContactNumber)) {
				return simpleJdbcTemplate.query(SQL_SEARCH_CUST_BY_CONTACT_NUM, new ParameterizedRowMapperByFieldName<SlvCustomerDTO>(SlvCustomerDTO.class), pContactNumber);
			}
			throw new DAOException("INPUT PARAMETER ERROR - NOT ALLOW EMPTY INPUT - pGrpIdDocType: " + pGrpIdDocType + 
										" / pGrpIdDocNum: " + pGrpIdDocNum + " / pBomParentCustNum: " + pBomParentCustNum + 
										" / pProfileID: " + pProfileID + " / pContactNumber: " + pContactNumber);
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException in getSLVCustSearchResult()");
			return null;	
		} catch (Exception e) {
			logger.info("Exception caught in getSLVCustSearchResult():", e);
			throw new DAOException(e.getMessage(), e);
		}		
	}
	
	private static final String SQL_SEARCH_SLV_PROFILE_BY_CUST_NUM = 
			"SELECT PROFILE_ID \"profileID\", SERVICE \"service\", PENDING_VISIT_IND \"pendingVisit\", " +
			"       TO_CHAR(PROFILE_CREATE_DATE, 'YYYYMMDD') \"profileCreateDate\", MTCE_TICKETS \"tickets\", " + 
	        "       TO_CHAR(SVC_WARR_START_DATE, 'YYYYMMDD') \"warrantyStartDate\", " +
	        "       TO_CHAR(SVC_WARR_END_DATE, 'YYYYMMDD') \"warrantyEndDate\", EQUIP_SERIAL_NUMS \"equipmentSerial\", " +
	        "       PENDING_VISIT_IND \"pendingVisit\" " +
			"  FROM SLV_SERVICE_SUMMARY_V sssv" + 
	        " WHERE PROFILE_ID IN (select profile_id from slv_srv_cust_assgn " +
            "                       where cust_type = 'OWN' and CUST_NO = ? " +
            "                      UNION " +
            "                      select profile_id from slv_srv_cust_assgn ssca " +
            "                       where cust_type != 'OWN' AND CUST_NO = ? "+
            "                         and not exists (select 1 from slv_srv_cust_assgn where cust_type = 'OWN' AND profile_id = ssca.profile_id) ) ";

	
	public List<SlvServiceDTO> getSlvServiceSummary(String pSlvCustNum) throws DAOException {
		try {
			return simpleJdbcTemplate.query(SQL_SEARCH_SLV_PROFILE_BY_CUST_NUM, new ParameterizedRowMapperByFieldName<SlvServiceDTO>(SlvServiceDTO.class), pSlvCustNum, pSlvCustNum);
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException in getSLVCustSearchResult()");
			return null;	
		} catch (Exception e) {
			logger.info("Exception caught in getSLVCustSearchResult():", e);
			throw new DAOException(e.getMessage(), e);
		}		
	}
	
	private static final String SQL_SVC_SEARCH_ORDER_BY_CUST_NUM =
			"SELECT * " + 
			"FROM " + 
			"  (SELECT TO_CHAR(APP_DATE, 'YYYYMMDD') \"applicationDate\", " + 
			"    LOB \"lob\", " + 
			"    ORDER_ID \"sbOrderID\", " + 
			"    LOB \"serviceType\", " + 
			"    SERVICE \"serviceNo\", " + 
			"    END_USER_NAME \"customerName\" " + 
			"  FROM slv_order_enquiry_v e " + 
			"  WHERE (e.order_id,e.dtl_id) IN " + 
			"    (SELECT DISTINCT order_id, " + 
			"      dtl_id " + 
			"    FROM SLV_ORDER_SEARCH_CUS_V " + 
			"       WHERE cust_no = ? " + 
			"    ) " + 
			"  UNION " + 
			"  SELECT TO_CHAR(APP_DATE, 'YYYYMMDD') \"applicationDate\", " + 
			"    LOB \"lob\", " + 
			"    ORDER_ID \"sbOrderID\", " + 
			"    LOB \"serviceType\", " + 
			"    SERVICE \"serviceNo\", " + 
			"    END_USER_NAME \"customerName\" " + 
			"  FROM slv_tmp_order_enquiry_v e " + 
			"  WHERE (e.order_id,e.dtl_id) IN " + 
			"    (SELECT DISTINCT order_id, " + 
			"      dtl_id " + 
			"    FROM slv_tmp_order_search_cus_v " + 
			"       WHERE cust_no = ? " + 
			"    ) " + 
			"  ) " + 
			"ORDER BY 1 ";

	public List<OrderHistoryDTO> getSlvOrderSummarySvc(String pSlvCustNum) throws DAOException {
		try {			
			return simpleJdbcTemplate.query(SQL_SVC_SEARCH_ORDER_BY_CUST_NUM, new ParameterizedRowMapperByFieldName<OrderHistoryDTO>(OrderHistoryDTO.class), pSlvCustNum,pSlvCustNum);
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException in getSLVCustSearchResult()");
			return null;	
		} catch (Exception e) {
			logger.info("Exception caught in getSLVCustSearchResult():", e);
			throw new DAOException(e.getMessage(), e);
		}		
	}

	private static final String SQL_SEARCH_ORDER_BY_CUST_NUM =
			"SELECT * " + 
			"FROM " + 
			"  (SELECT DISTINCT TO_CHAR(e.APP_DATE, 'YYYYMMDD') \"applicationDate\", " + 
			"    TO_CHAR(e.SIGN_OFF_DATE, 'YYYYMMDD') \"signOffDate\", " + 
			"    e.ORDER_ID \"orderId\", " + 
			"    e.SERVICE \"service\", " + 
			"    wcl.description \"orderStatus\" " + 
			"  FROM slv_order_enquiry_v e, " + 
			"    w_code_lkup wcl " + 
			"  WHERE (e.order_id,e.dtl_id) IN " + 
			"    (SELECT DISTINCT order_id, " + 
			"      dtl_id " + 
			"    FROM SLV_ORDER_SEARCH_CUS_V " + 
			"       WHERE cust_no = ? " + 
			"    ) " + 
			"  AND wcl.GRP_ID = 'SB_ORD_STATUS' " + 
			"  AND wcl.code   = e.order_status " + 
			"  UNION " + 
			"  SELECT DISTINCT TO_CHAR(e.APP_DATE, 'YYYYMMDD') \"applicationDate\", " + 
			"    TO_CHAR(e.SIGN_OFF_DATE, 'YYYYMMDD') \"signOffDate\", " + 
			"    e.ORDER_ID \"orderId\", " + 
			"    e.SERVICE \"service\", " + 
			"    wcl.description \"orderStatus\" " + 
			"  FROM slv_tmp_order_enquiry_v e, " + 
			"    w_code_lkup wcl " + 
			"  WHERE (e.order_id,e.dtl_id) IN " + 
			"    (SELECT DISTINCT order_id, " + 
			"      dtl_id " + 
			"    FROM slv_tmp_order_search_cus_v " + 
			"    WHERE cust_no = ? " + 
			"    ) " + 
			"  AND wcl.GRP_ID = 'SB_ORD_STATUS' " + 
			"  AND wcl.code   = e.order_status " + 
			"  ) " + 
			"ORDER BY 1";

	public List<SlvOrderSummaryDTO> getSlvOrderSummary(String pSlvCustNum) throws DAOException {
		try {			
			return simpleJdbcTemplate.query(SQL_SEARCH_ORDER_BY_CUST_NUM, new ParameterizedRowMapperByFieldName<SlvOrderSummaryDTO>(SlvOrderSummaryDTO.class), pSlvCustNum, pSlvCustNum);
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException in getSLVCustSearchResult()");
			return null;	
		} catch (Exception e) {
			logger.info("Exception caught in getSlvOrderSummary():", e);
			throw new DAOException(e.getMessage(), e);
		}		
	}
	
	public SlvOrderSummaryDTO[] getOrderSearchResults(String idDocType, String idDocNum, String orderId, String profileId, String contactNum, String paymentNoticeNum, List<String> serviceType,
			String startDate, String endDate, String orderStatus, String orderCreator, String sales, String teamCD, String Channel) throws Exception {
		return getOrderSearchResults(idDocType, idDocNum, orderId, profileId, contactNum, paymentNoticeNum, serviceType, startDate, endDate, orderStatus, orderCreator, sales, teamCD, Channel, null, null, null);
	}
	public SlvOrderSummaryDTO[] getOrderSearchResults(String idDocType, String idDocNum,String orderId,String profileId,String contactNum,
			String paymentNoticeNum,List<String> serviceType,String startDate,String endDate,String orderStatus,String orderCreator,String sales,String teamCD,String Channel,String userChannelCode, String slCubeId, String smNum) throws Exception {
		
		SlvOrderSummaryDTO[] results = null;
		//search in enquiry_v
		{
			String sql = SQL_GET_ORDER_SEARCH_RESULT;
			String criteria = "";
			String search_v = "";
			Object[] bind = new Object[] {};	
			if (StringUtils.isNotBlank(idDocNum)) {
				
				criteria = criteria + " AND id_doc_type = ? AND id_doc_num = ? ";
				bind = ArrayUtils.add(bind, idDocType);
				bind = ArrayUtils.add(bind, idDocNum);
				search_v = CUS_V;
			}
			else if (StringUtils.isNotBlank(smNum)) {
				criteria = criteria + " AND sales_memo_num = ? ";
				bind = ArrayUtils.add(bind, smNum);
				search_v = SM_V;
			}
			else if (StringUtils.isNotBlank(contactNum)) {
				criteria = criteria + " AND contact_no = ? ";
				bind = ArrayUtils.add(bind, contactNum);
				search_v = CUS_V;
			}
			else if (StringUtils.isNotBlank(orderId)) {
				criteria = criteria + " AND order_id = ? ";
				bind = ArrayUtils.add(bind, orderId);
				search_v = ORD_V;
			}
			else if (StringUtils.isNotBlank(profileId)) {
				criteria = criteria + " AND profile_id = ? ";
				bind = ArrayUtils.add(bind, profileId);
				search_v = PROFILE_V;
			}
			else if (StringUtils.isNotBlank(slCubeId)) {
				criteria = criteria + " AND sl_cube_id = ? ";
				bind = ArrayUtils.add(bind, slCubeId);
				search_v = PROFILE_V;
			}
			else if (StringUtils.isNotBlank(paymentNoticeNum)) {
				criteria = criteria + " AND actv_id = ? ";
				bind = ArrayUtils.add(bind, paymentNoticeNum);
				search_v = ACTV_V;
			}
			else {
				if (StringUtils.isBlank(teamCD)) {} 
				else if ("ALL".equals(teamCD)&&"ALL".equals(Channel)){}
				else if ("ALL".equals(teamCD))
				{
					DropDown[] c = getTeamCode(Channel, userChannelCode);
					Set<String> s = new HashSet<String>();
					for (DropDown item : c) {
						if (StringUtils.isNotBlank(item.getKey()))
						{
							s.add(item.getKey());
						}
					}
					criteria = criteria + " AND (sales_team is null or sales_team MEMBER OF ? ) ";
					bind = ArrayUtils.add(bind, new OraArrayVarChar2("OPS$CNM.TABLE_VARCHAR2", s.toArray(new String[0])));
				} else 
				{
					criteria = criteria + " AND sales_team = ? ";
					bind = ArrayUtils.add(bind, teamCD);
				}
				
				if (StringUtils.isBlank(Channel)) {}
				else if ("ALL".equals(Channel)) 
				{
					Set<String> channels = new HashSet<String>();
					channels.add(userChannelCode);
					for (DropDown dropDown : getChannelCD(userChannelCode)) {
						channels.add(dropDown.getKey());
					}
					criteria += " AND sales_channel MEMBER OF ? ";
					bind = ArrayUtils.add(bind, new OraArrayVarChar2("OPS$CNM.TABLE_VARCHAR2", channels.toArray(new String[0])));
				} 
				else 
				{
					criteria = criteria + " AND sales_channel = ? ";
					bind = ArrayUtils.add(bind, Channel);
				}
				search_v = ORD_V;
			}
			sql=sql.replaceAll("_CRITERIA_", criteria);
			sql=sql.replaceAll("_SEARCH_V_", search_v);
			
			if (serviceType!=null && serviceType.size() > 0) {
				sql = sql + " AND srv_type IN (" + StringUtils.join(serviceType, ",") + ")";
			}
			if (StringUtils.isNotBlank(startDate) && StringUtils.isNotBlank(endDate)) {
				if (startDate.equals(endDate)) {
					sql = sql + " AND TO_CHAR(app_date, 'dd/mm/yyyy') = '" + startDate + "' ";
				} else {
					sql = sql + " AND app_date BETWEEN TO_DATE ('" + startDate + " 00:00:00','dd/mm/yyyy HH24:MI:SS') AND TO_DATE ('" + endDate + " 23:59:59','dd/mm/yyyy HH24:MI:SS')";
				}
			}
			if (StringUtils.isNotBlank(orderStatus)) {
				sql = sql + " AND order_status = ? ";
				bind = ArrayUtils.add(bind, orderStatus);
			}
			if (StringUtils.isNotBlank(orderCreator)) {
				sql = sql + " AND create_by = (select staff_id from bomweb_sales_lkup_v where org_staff_id = ?) ";
				bind = ArrayUtils.add(bind, orderCreator);
			}
			if (StringUtils.isNotBlank(sales)) {
				sql = sql + " AND sales_staff_id = ? ";
				bind = ArrayUtils.add(bind, sales);
			}
			
			
				if ("I".equals(orderStatus)) {
					results = new SlvOrderSummaryDTO[]{};
				}
				else {
					results = OracleSelectHelper.getSqlResultObjects(this.getDataSource(), sql, bind, SlvOrderSummaryDTO.class);
				}
			
		}
		//search in temptable
		{
			String sql = SQL_GET_TMP_ORDER_SEARCH_RESULT;
			String criteria = "";
			String search_v = "";
			Object[] bind = new Object[] {};	
			if (StringUtils.isNotBlank(idDocNum)) {
				
				criteria = criteria + " AND id_doc_type = ? AND id_doc_num = ? ";
				bind = ArrayUtils.add(bind, idDocType);
				bind = ArrayUtils.add(bind, idDocNum);
				search_v = TMP_CUS_V;
			}
			else if (StringUtils.isNotBlank(smNum)) {
				//select nothing on purpose
				criteria = criteria + " AND contact_no = ? ";
				bind = ArrayUtils.add(bind, "-1");
				search_v = TMP_CUS_V;
			}
			else if (StringUtils.isNotBlank(contactNum)) {
				criteria = criteria + " AND contact_no = ? ";
				bind = ArrayUtils.add(bind, contactNum);
				search_v = TMP_CUS_V;
			}
			//20170707
			else if (StringUtils.isNotBlank(profileId)) {
				search_v = TMP_CUS_V;
			}
			else {
				if (StringUtils.isBlank(teamCD)) {} 
				else if ("ALL".equals(teamCD)&&"ALL".equals(Channel)){}
				else if ("ALL".equals(teamCD))
				{
					DropDown[] c = getTeamCode(Channel, userChannelCode);
					Set<String> s = new HashSet<String>();
					for (DropDown item : c) {
						if (StringUtils.isNotBlank(item.getKey()))
						{
							s.add(item.getKey());
						}
					}
					criteria = criteria + " AND (sales_team is null or sales_team MEMBER OF ? ) ";
					bind = ArrayUtils.add(bind, new OraArrayVarChar2("OPS$CNM.TABLE_VARCHAR2", s.toArray(new String[0])));
				} else 
				{
					criteria = criteria + " AND sales_team = ? ";
					bind = ArrayUtils.add(bind, teamCD);
				}
				
				if (StringUtils.isBlank(Channel)) {}
				else if ("ALL".equals(Channel)) 
				{
					Set<String> channels = new HashSet<String>();
					channels.add(userChannelCode);
					for (DropDown dropDown : getChannelCD(userChannelCode)) {
						channels.add(dropDown.getKey());
					}
					criteria += " AND sales_channel MEMBER OF ? ";
					bind = ArrayUtils.add(bind, new OraArrayVarChar2("OPS$CNM.TABLE_VARCHAR2", channels.toArray(new String[0])));
				} 
				else 
				{
					criteria = criteria + " AND sales_channel = ? ";
					bind = ArrayUtils.add(bind, Channel);
				}
				
				if (StringUtils.isNotBlank(startDate) && StringUtils.isNotBlank(endDate)) {
					if (startDate.equals(endDate)) {
						criteria = criteria + " AND TO_CHAR(app_date, 'dd/mm/yyyy') = '" + startDate + "' ";
					} else {
						criteria = criteria + " AND app_date BETWEEN TO_DATE ('" + startDate + " 00:00:00','dd/mm/yyyy HH24:MI:SS') AND TO_DATE ('" + endDate + " 23:59:59','dd/mm/yyyy HH24:MI:SS')";
					}
				}
				if (StringUtils.isNotBlank(orderId)) {
					search_v = TMP_ORD_V;
				}
			}
			sql=sql.replaceAll("_CRITERIA_", criteria);
			sql=sql.replaceAll("_SEARCH_V_", search_v);
			
			if (StringUtils.isNotBlank(orderId)) {
				sql = sql + " AND SOEV.order_id = ? ";
				bind = ArrayUtils.add(bind, orderId);
			}
			if (StringUtils.isNotBlank(profileId)) {
				sql = sql + " AND SOEV.profile_id = ? ";
				bind = ArrayUtils.add(bind, profileId);
			}
			if (serviceType!=null && serviceType.size() > 0) {
				sql = sql + " AND service IN (" + StringUtils.join(serviceType, ",") + ")";
			}
			if (StringUtils.isNotBlank(startDate) && StringUtils.isNotBlank(endDate)) {
				if (startDate.equals(endDate)) {
					sql = sql + " AND TO_CHAR(app_date, 'dd/mm/yyyy') = '" + startDate + "' ";
				} else {
					sql = sql + " AND app_date BETWEEN TO_DATE ('" + startDate + " 00:00:00','dd/mm/yyyy HH24:MI:SS') AND TO_DATE ('" + endDate + " 23:59:59','dd/mm/yyyy HH24:MI:SS')";
				}
			}
			if (StringUtils.isNotBlank(orderStatus)) {
				sql = sql + " AND order_status = ? ";
				bind = ArrayUtils.add(bind, orderStatus);
			}
			if (StringUtils.isNotBlank(orderCreator)) {
				sql = sql + " AND create_by = (select staff_id from bomweb_sales_lkup_v where org_staff_id = ?) ";
				bind = ArrayUtils.add(bind, orderCreator);
			}
			if (StringUtils.isNotBlank(sales)) {
				sql = sql + " AND sales_staff_id = ? ";
				bind = ArrayUtils.add(bind, sales);
			}
				//20170707
				if (StringUtils.isNotBlank(search_v) && (StringUtils.isNotBlank(orderId) || StringUtils.isNotBlank(sales) || StringUtils.isNotBlank(profileId))) {
					SlvOrderSummaryDTO[] result = OracleSelectHelper.getSqlResultObjects(this.getDataSource(), sql, bind, SlvOrderSummaryDTO.class);
					results = (SlvOrderSummaryDTO[]) ArrayUtils.addAll(results, result);
				}
			
		}
		//search in discard order
		{
			String sql = SQL_GET_DISCARD_ORDER_SEARCH_RESULT;
			Object[] bind = new Object[] {};
			if (StringUtils.isNotBlank(idDocNum)) {

				sql = sql + " AND id_doc_type = ? AND id_doc_num = ? ";
				bind = ArrayUtils.add(bind, idDocType);
				bind = ArrayUtils.add(bind, idDocNum);
			}
			if (StringUtils.isNotBlank(contactNum)) {
				sql = sql + " AND contact_no = ? ";
				bind = ArrayUtils.add(bind, contactNum);
			}
			if (StringUtils.isNotBlank(smNum)) {
				//select noting on purpose
				sql = sql + " AND contact_no = ? ";
				bind = ArrayUtils.add(bind, "-1");
			}

			if (StringUtils.isBlank(teamCD)) {
			} else if ("ALL".equals(teamCD) && "ALL".equals(Channel)) {
			} else if ("ALL".equals(teamCD)) {
				DropDown[] c = getTeamCode(Channel, userChannelCode);
				Set<String> s = new HashSet<String>();
				for (DropDown item : c) {
					if (StringUtils.isNotBlank(item.getKey())) {
						s.add(item.getKey());
					}
				}
				sql = sql + " AND (team_code is null or team_code MEMBER OF ? ) ";
				bind = ArrayUtils.add(bind, new OraArrayVarChar2("OPS$CNM.TABLE_VARCHAR2", s.toArray(new String[0])));
			} else {
				sql = sql + " AND team_code = ? ";
				bind = ArrayUtils.add(bind, teamCD);
			}

			if (StringUtils.isBlank(Channel)) {
			} else if ("ALL".equals(Channel)) {
				Set<String> channels = new HashSet<String>();
				channels.add(userChannelCode);
				for (DropDown dropDown : getChannelCD(userChannelCode)) {
					channels.add(dropDown.getKey());
				}
				sql = sql + " AND channel MEMBER OF ? ";
				bind = ArrayUtils.add(bind, new OraArrayVarChar2("OPS$CNM.TABLE_VARCHAR2", channels.toArray(new String[0])));
			} else {
				sql = sql + " AND channel = ? ";
				bind = ArrayUtils.add(bind, Channel);
			}

			if (StringUtils.isNotBlank(startDate) && StringUtils.isNotBlank(endDate)) {
				if (startDate.equals(endDate)) {
					sql = sql + " AND TO_CHAR(bdo.create_date, 'dd/mm/yyyy') = '" + startDate + "' ";
				} else {
					sql = sql + " AND bdo.create_date BETWEEN TO_DATE ('" + startDate + " 00:00:00','dd/mm/yyyy HH24:MI:SS') AND TO_DATE ('" + endDate + " 23:59:59','dd/mm/yyyy HH24:MI:SS')";
				}
			}

			if (StringUtils.isNotBlank(orderId)) {
				sql = sql + " AND order_id = ? ";
				bind = ArrayUtils.add(bind, orderId);
			}
			if (StringUtils.isNotBlank(profileId)) {
				sql = sql + " AND profile_id = ? ";
				bind = ArrayUtils.add(bind, profileId);
			}
			if (serviceType != null && serviceType.size() > 0) {
				sql = sql + " AND service IN (" + StringUtils.join(serviceType, ",") + ")";
			}
			if (StringUtils.isNotBlank(orderCreator)) {
				sql = sql + " AND bdo.create_by = (select staff_id from bomweb_sales_lkup_v where org_staff_id = ?) ";
				bind = ArrayUtils.add(bind, orderCreator);
			}
			if (StringUtils.isNotBlank(sales)) {
				sql = sql + " AND sales_id = ? ";
				bind = ArrayUtils.add(bind, sales);
			}

			
				if (StringUtils.isBlank(paymentNoticeNum)&&StringUtils.isBlank(slCubeId)&&StringUtils.isBlank(orderStatus)) {
					SlvOrderSummaryDTO[] result = OracleSelectHelper.getSqlResultObjects(this.getDataSource(), sql, bind, SlvOrderSummaryDTO.class);
					results = (SlvOrderSummaryDTO[]) ArrayUtils.addAll(results, result);
				}
			
		}
		
		return results;

	}
	
	public DropDown[] getChannelCD(String channel)
	{
		List<DropDown> result = new ArrayList<DropDown>();
		try {
			String[] res = OracleSelectHelper.getSqlFirstColumnStrings(this.getDataSource(), SQL_GET_CHANNEL_CODE,new Object[]{channel});
			if (res.length>0) {
				for(String str : StringUtils.split(res[0],","))
				{
					DropDown temp = new DropDown();
					temp.setKey(str);
					temp.setValue(str);
					result.add(temp);
				}
			}
		} catch (Exception e) {
			logger.error("fail to get channels /n",e);
		}
		return result.toArray(new DropDown[result.size()]);
	}
	public DropDown[] getStatus()
	{
		DropDown[] result = null;
		try {
			result = OracleSelectHelper.getSqlResultObjects(this.getDataSource(), SQL_GET_STATUS_LIST, null, DropDown.class);
		} catch (Exception e) {
			logger.error("fail to get status /n",e);
		}
		return result;
	}

	public DropDown[] getTeamCode(String channelCode, String userChannelCode) {
		DropDown[] result = null;
		try {
			if ("ALL".equals(channelCode)) {
				Set<String> channels = new HashSet<String>();
				channels.add(userChannelCode);
				for (DropDown dropDown : getChannelCD(userChannelCode)) {
					channels.add(dropDown.getKey());
				}		
				result = OracleSelectHelper.getSqlResultObjects(this.getDataSource(), SQL_GET_TEAM_CODE + " MEMBER OF ? ", new Object[] {new OraArrayVarChar2("OPS$CNM.TABLE_VARCHAR2", channels.toArray(new String[0]))}, DropDown.class);
			} else {
				result = OracleSelectHelper.getSqlResultObjects(this.getDataSource(), SQL_GET_TEAM_CODE + " =? ", new Object[] { channelCode }, DropDown.class);
			}
		} catch (Exception e) {
			logger.error("fail to get teamCode /n", e);
		}
		return result;
	}
	

}