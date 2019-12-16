package com.bomwebportal.mob.ccs.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.util.CollectionUtils;

import com.bomwebportal.dao.BaseDAO;
import com.bomwebportal.dto.MnpDTO;
import com.bomwebportal.dto.OrderDTO;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.mob.ccs.util.BomWebPortalCcsConstant;
import com.bomwebportal.util.BomWebPortalConstant;
import com.bomwebportal.util.Utility;

public class MobCcsOrderDAO extends BaseDAO{

	protected final Log logger = LogFactory.getLog(getClass());
	
	public String orderStatusProcess(String orderId) throws DAOException {
		//String contextId = null;

		try {			
			SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
				.withSchemaName("OPS$CNM")
            	.withCatalogName("PKG_SB_MOB_ORDER")
            	.withProcedureName("ORDER_STATUS_PROCESS");
			jdbcCall.setAccessCallParameterMetaData(false);
			jdbcCall.declareParameters(
					new SqlParameter("i_order_id", Types.VARCHAR),
					new SqlOutParameter("errCode", Types.INTEGER),
					new SqlOutParameter("errText", Types.VARCHAR));
			
		
			
			MapSqlParameterSource inMap = new MapSqlParameterSource();
			inMap.addValue("i_order_id", orderId);
			SqlParameterSource in = inMap;
			
			Map out = jdbcCall.execute(in);
			int error_code = BomWebPortalConstant.ERRCODE_FAIL;
		//	int ret_val = BomWebPortalConstant.ERRCODE_FAIL; 
			
			if (((Integer) out.get("errCode"))!= null ) {
				error_code = ((Integer) out.get("errCode")).intValue();
			}

			String error_text = (String)out.get("errText");
			
			//logger.info("PKG_SB_MOB_ORDER.ORDER_STATUS_PROCESS() output ret_val = " + ret_val);
			logger.info("PKG_SB_MOB_ORDER.ORDER_STATUS_PROCESS()() output error_code = " + error_code);
			logger.info("PKG_SB_MOB_ORDER.ORDER_STATUS_PROCESS()() output error_text = " + error_text);
			//logger.info("PKG_SB_MOB_ORDER.ORDER_STATUS_PROCESS()() output contextId = " + contextId);
			
			/*if ( (error_code != BomWebPortalConstant.ERRCODE_SUCCESS)) {
				contextId = null;
			}*/
			
			//logger.info("initialCeksAccess contextId = " + contextId);
			
			return error_text;
			
		} catch (Exception e) {
			logger.error("Exception caught in orderStatusProcess()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public String orderMrtUpdate(String orderId) throws DAOException {
		//String contextId = null;

		try {			
			SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
				.withSchemaName("OPS$CNM")
            	.withCatalogName("PKG_SB_MOB_ORDER")
            	.withProcedureName("ORDER_MRT_PROCESS");
			jdbcCall.setAccessCallParameterMetaData(false);
			jdbcCall.declareParameters(
					new SqlParameter("i_order_id", Types.VARCHAR),
				
					new SqlOutParameter("errCode", Types.INTEGER),
					new SqlOutParameter("errText", Types.VARCHAR));

			MapSqlParameterSource inMap = new MapSqlParameterSource();
			inMap.addValue("i_order_id", orderId);
			SqlParameterSource in = inMap;
			
			Map out = jdbcCall.execute(in);
			int error_code = BomWebPortalConstant.ERRCODE_FAIL;
			//int ret_val = BomWebPortalConstant.ERRCODE_FAIL; 
			
			if (((Integer) out.get("errCode"))!= null ) {
				error_code = ((Integer) out.get("errCode")).intValue();
			}

			String error_text = (String)out.get("errText");
			
			//logger.info("PKG_SB_MOB_ORDER.ORDER_MRT_PROCESS() output ret_val = " + ret_val);
			logger.info("PKG_SB_MOB_ORDER.ORDER_MRT_PROCESS() output error_code = " + error_code);
			logger.info("PKG_SB_MOB_ORDER.ORDER_MRT_PROCESS() output error_text = " + error_text);
			//logger.info("PKG_SB_MOB_ORDER.ORDER_MRT_PROCESS() output contextId = " + contextId);

			return error_text;
			
		} catch (Exception e) {
			logger.error("Exception caught in orderMrtUpdate()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	
	public String getMobCcsOrderStatusDesc(String orderId) throws DAOException {
		logger.info("getMobCcsOrderStatusDesc is called");
		
		List<String> resultList = new ArrayList<String>();
		
		String sql = 
			"select nvl(TEMP.ORDER_STATUS_DESC,TEMP.ORDER_STATUS ) || ':' ||\n" +
			"       DECODE(TEMP.CHECK_POINT,\n" + 
			"              999,\n" + 
			"              '',\n" + 
			"              NVL(TEMP.CHEKC_POINT_DESC, TEMP.CHECK_POINT) ||\n" + 
			"              DECODE(NVL(TEMP.FALLOUT_DESC, TEMP.REASON_CD), null, '', ':')) ||\n" + 
			"       NVL(TEMP.FALLOUT_DESC, TEMP.REASON_CD) ORDER_STATUS_DESC\n" + 
			"  from (select O.ORDER_ID,\n" + 
			"               O.ORDER_STATUS,\n" + 
			"               O.CHECK_POINT,\n" + 
			"               O.REASON_CD,\n" + 
			"               O.CREATE_DATE,\n" + 
			"               O.LOB,\n" + 
			"               (select CL.CODE_DESC\n" + 
			"                  from BOMWEB_CODE_LKUP CL\n" + 
			"                 where CL.CODE_TYPE = 'ORD_STATUS'\n" + 
			"                   and CL.CODE_ID = O.ORDER_STATUS) ORDER_STATUS_DESC,\n" + 
			"               (select CL2.CODE_DESC\n" + 
			"                  from BOMWEB_CODE_LKUP CL2\n" + 
			"                 where CL2.CODE_TYPE = 'ORD_CHECK_POINT'\n" + 
			"                   and CL2.CODE_ID = O.CHECK_POINT) CHEKC_POINT_DESC,\n" + 
			"               (select CL3.CODE_DESC\n" + 
			"                  from BOMWEB_CODE_LKUP CL3\n" + 
			"                 where CL3.CODE_TYPE = 'ORD_FALLOUT_CODE'\n" + 
			"                   and CL3.CODE_ID = O.REASON_CD) FALLOUT_DESC\n" + 
			"          from BOMWEB_ORDER O) TEMP\n" + 
			" where TEMP.ORDER_ID = ?\n" + 
			"   and TEMP.LOB = 'MOB'";

		
		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {

			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getString("ORDER_STATUS_DESC");
			}
		};
		
		try {
			resultList = simpleJdbcTemplate.query(sql, mapper, orderId);
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException");
		} catch (Exception e) {
			logger.error("Exception caught in getMobCcsOrderStatusDesc()", e);
			throw new DAOException(e.getMessage(), e);
		}
		
		if (resultList != null && !resultList.isEmpty()) {
			return (String) resultList.get(0);
		} else {
			return null;
		}
	}
	
	public String[] checkAuthority(String orderId, String staffId) throws DAOException {
		
		String[] resultArray = new String[] {String.valueOf(BomWebPortalConstant.ERRCODE_FAIL), ""};
		
		try {			
			SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
				.withSchemaName("OPS$CNM")
            	.withCatalogName("PKG_SB_MOB_ORDER")
            	.withProcedureName("ORDER_RECALL_CHECK");
			jdbcCall.setAccessCallParameterMetaData(false);
			jdbcCall.declareParameters(
					new SqlParameter("i_order_id", Types.VARCHAR),
					new SqlParameter("i_staff_id", Types.VARCHAR),
					new SqlOutParameter("o_errCode", Types.INTEGER),
					new SqlOutParameter("o_errText", Types.VARCHAR));
		
			MapSqlParameterSource inMap = new MapSqlParameterSource();
			inMap.addValue("i_order_id", orderId);
			inMap.addValue("i_staff_id", staffId);
			SqlParameterSource in = inMap;
			
			Map out = jdbcCall.execute(in);
			
			if (((Integer) out.get("o_errCode"))!= null ) {
				resultArray[0] = ((Integer) out.get("o_errCode")).toString();
			}

			resultArray[1] = (String)out.get("o_errText");
			
			logger.info("PKG_SB_MOB_ORDER.ORDER_RECALL_CHECK() output error_code = " + resultArray[0]);
			logger.info("PKG_SB_MOB_ORDER.ORDER_RECALL_CHECK() output error_text = " + resultArray[1]);
			
			return resultArray;
			
		} catch (Exception e) {
			logger.error("Exception caught in orderStatusProcess()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public String getMultiSimErrorMsg(String orderId) throws DAOException {
		logger.debug("getMultiSimErrorMsg is called");
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		
		String sql =  "select * from bomweb_ord_mob_member "
				+ "where parent_order_id = :orderId "
				+ "and err_msg is not null";
				
		params.addValue("orderId", orderId);

		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {

			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				
				String errmsg = "<b>Member " + rs.getString("MEMBER_NUM") + " " + rs.getString("MSISDN") + ":</b> " 
						+ rs.getString("MEMBER_STATUS") + " - " + rs.getString("ERR_MSG");
				return errmsg;
			}
		};
		
		try {
			List<String> dto = simpleJdbcTemplate.query(sql, mapper, params);
			
			String out = "";	
			for  (int i = 0; i < dto.size(); i++) {
				if (out.length() > 0) {
					out += "<br/>";
				}
				out = out + dto.get(i).trim();
			}
			return out;
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException");
		} catch (Exception e) {
			logger.error("Exception caught in getMultiSimErrorMsg()", e);
			throw new DAOException(e.getMessage(), e);
		}
		
		return null;
	}
	
	// add LOB = 'MOB' by Herbert 20120424
	public List<OrderDTO> findOrderEnquiry (String startDate, String endDate, 
			String orderId, String orderStatus, String variousDateType, String channel, 
			String staffId, String orderType, String category, String areaCd, 
			String shopCd, String group, String mrt,
			boolean enableAlertMessageForceCancellationRemainingDay) throws DAOException {
		logger.info("findOrderEnquiry is called");
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		
		StringBuffer sql =  new StringBuffer("SELECT O.ORDER_ID, " +
						"  O.OCID, " +
						"   (select BRAND " + 
						"        from BOMWEB_ACCT BA " + 
						"        where BA.ORDER_ID = O.ORDER_ID) BRAND," +
						"  trim(o.err_msg " +
						"  || DECODE(DECODE(o.err_msg, NULL, NULL, " +
						"  (SELECT DISTINCT parent_order_id " +
						"  FROM bomweb_ord_mob_member " +
						"  WHERE parent_order_id=o.order_id " +
						"  AND err_msg         IS NOT NULL " +
						"  )), NULL, '', '<br/>') " +
						"  || " +
						"  (SELECT LISTAGG(err_msg, '<br/>') WITHIN GROUP ( " +
						"  ORDER BY parent_order_id) " +
						"  FROM " +
						"    (SELECT parent_order_id, " +
						"      '<b>Member ' " +
						"      ||member_num " +
						"      ||' ' " +
						"      ||msisdn " +
						"      ||':</b> ' " +
						"      ||member_status " +
						"      ||' - ' " +
						"      ||err_msg err_msg " +
						"    FROM bomweb_ord_mob_member " +
						"    WHERE err_msg IS NOT NULL " +
						"    ) " +
						"  WHERE parent_order_id = o.order_id " +
						"  )) err_msg, " +
						"  C.LAST_NAME " +
						"  || ' ' " +
						"  || C.FIRST_NAME CUST_FULL_NAME, " +
						"  O.MSISDN SERVICE_NUM, " +
						"  O.APP_DATE, " +
						"  O.shop_Cd, " +
						"  D.DELIVERY_DATE, " +
						"  O.SRV_REQ_DATE, " +
						"  O.ORDER_STATUS, " +
						"  O.CHECK_POINT, " +
						"  O.REASON_CD, " +
						"  O.ORDER_TYPE, " +
						"  O.SALES_CD, " +
						"  (SELECT T.TIMEFROM " +
						"    || '-' " +
						"    || T.TIMETO " +
						"  FROM w_delivery_timeslot T " +
						"  WHERE d.delivery_time_slot = t.timeslot " +
						"  ) DELIVERY_TIME_SLOT, " +
						"  FLOOR(sysdate - O.LAST_UPDATE_DATE) LAST_UPDATE_DATE_DIFF, " +
						"  (SELECT DECODE(COUNT(1), 0, 'N', 'Y') " +
						"  FROM BOMWEB_CODE_LKUP CL, " +
						"    BOMWEB_ORDER OC " +
						"  WHERE OC.REASON_CD = CL.CODE_ID " +
						"  AND CL.CODE_TYPE  IN ( 'ALLOW_CANCEL_FO_CODE', 'FORCE_CANCEL_FO_CODE') " +
						"  AND OC.ORDER_ID    = o.order_id " +
						"  ) ALLOW_CANCEL_IND, " +
						"  S.BOM_ORDER_STATUS, " +
						"  S.SRV_REQ_DATE ACT_SRV_REQ_DATE , " +
						"  O.LAST_UPDATE_DATE , " +
						"  (SELECT OM.BOM_FROZEN_IND " +
						"  FROM BOMWEB_ORDER_MOB OM " +
						"  WHERE OM.ORDER_ID = O.ORDER_ID " +
						"  AND ROWNUM        = 1 " +
						"  ) BOM_FROZEN_IND " +
						"FROM bomweb_order o " +
						"LEFT JOIN bomweb_customer c " +
						"ON (o.order_id = c.order_id) " +
						"LEFT JOIN bomweb_delivery d " +
						"ON (o.order_id = d.order_id) " +
						"LEFT JOIN bomweb_sb_bom_order s " +
						"ON (o.order_id = s.order_id) " +
						"LEFT JOIN bomweb_sales_assignment bsa on (o.sales_cd = bsa.staff_id) " +
						"WHERE o.lob = 'MOB' " +
						"AND o.shop_cd in (SELECT DISTINCT SHOP_CD FROM BOMWEB_SHOP WHERE CHANNEL_ID in ('2')) " +
					    "AND substrb(o.ORDER_ID,1,1) ='C' " + 
						"AND trunc(o.app_date) BETWEEN BSA.START_DATE AND NVL(BSA.END_DATE, trunc(sysdate)) " );
		
		if (!"ALL".equalsIgnoreCase(orderStatus) && !"ALERT".equalsIgnoreCase(orderStatus)) {
						
			if (Utility.isInteger(orderStatus)) {
				if (!"000".equalsIgnoreCase(orderStatus) && Integer.valueOf(orderStatus) < 100) {
					sql.append(" and O.ORDER_STATUS = :orderStatus ");
				} else if (!orderStatus.equals("999")) {
					sql.append(" and O.CHECK_POINT = :orderStatus ");
					sql.append(" and O.ORDER_STATUS = '01' ");
				}
			} else {
				sql.append(" and O.REASON_CD = :orderStatus ");
				sql.append(" and O.ORDER_STATUS = '99' ");
			}
			params.addValue("orderStatus", orderStatus);
		} else if ("ALERT".equalsIgnoreCase(orderStatus)) {
			sql.append(" and O.ORDER_STATUS = '99' ");
		}
				
		if (!"ALL".equalsIgnoreCase(orderId) && !orderId.isEmpty()) {
			sql.append(" and O.ORDER_ID = upper (trim(:orderId)) "); //add trim 20120307
			orderId = orderId.replace(" ", "");
			orderId = orderId.trim().toUpperCase();
			params.addValue("orderId", orderId);
		}
		
		if (!"ALL".equalsIgnoreCase(orderType) && !orderType.isEmpty()) {
			sql.append(" and O.ORDER_TYPE = :orderType");
			params.addValue("orderType", orderType);
		}
		
		if (StringUtils.isNotBlank(mrt)) {
			sql.append(" and O.ORDER_ID in ("
					+ "(select M.ORDER_ID from BOMWEB_MRT M where MSISDN = TRIM(:mrt)) "
					+ "union "
					+ "(select PARENT_ORDER_ID from BOMWEB_ORD_MOB_MEMBER WHERE MSISDN = TRIM(:mrt) OR MNP_NO = TRIM(:mrt)))");
			params.addValue("mrt", mrt);
		}
		
		if (startDate != null && !startDate.isEmpty() && endDate != null && !endDate.isEmpty()) {
			if ("APP".equalsIgnoreCase(variousDateType)) {
				sql.append(" and trunc(O.APP_DATE) between TO_DATE(:startDate, 'DD/MM/YYYY') and TO_DATE(:endDate, 'DD/MM/YYYY')");
			} else if ("DEL".equalsIgnoreCase(variousDateType)) {
				sql.append(" and trunc(D.DELIVERY_DATE) between TO_DATE(:startDate, 'DD/MM/YYYY') and TO_DATE(:endDate, 'DD/MM/YYYY')");
			} else if ("SR".equalsIgnoreCase(variousDateType)) {
				sql.append(" and trunc(O.SRV_REQ_DATE) between TO_DATE(:startDate, 'DD/MM/YYYY') and TO_DATE(:endDate, 'DD/MM/YYYY')");
			}
			params.addValue("startDate", startDate);
			params.addValue("endDate", endDate);
		}		
		
		//staff hierarchy
		if (group == null || "CH".equalsIgnoreCase(group)) {
			sql.append(" and BSA.CHANNEL_CD = :channel ");
			params.addValue("channel", channel);
			if (BomWebPortalCcsConstant.USER_CATEGORY_SALESMANAGER.equalsIgnoreCase(category)) {
				sql.append("");
			} else if (BomWebPortalCcsConstant.USER_CATEGORY_MANAGER.equalsIgnoreCase(category)) {
				sql.append(" and BSA.CENTRE_CD = :areaCd ");
				sql.append(" and BSA.TEAM_CD = :shopCd ");
				params.addValue("areaCd", areaCd);
				params.addValue("shopCd", shopCd);
			}else if (BomWebPortalCcsConstant.USER_CATEGORY_FRONTLINE.equalsIgnoreCase(category)) {
				sql.append(" and BSA.CENTRE_CD = :areaCd ");
				sql.append(" and BSA.TEAM_CD = :shopCd ");
				sql.append(" and BSA.staff_id = :staffId ");
				
				//add String staffId
				params.addValue("areaCd", areaCd);
				params.addValue("shopCd", shopCd);
				params.addValue("staffId", staffId);
			} 
		} else {
			// F/S only
			if (!"ALL".equalsIgnoreCase(channel)) {
				sql.append(" and O.SHOP_CD=:channel");
				params.addValue("channel", channel);
			}
		}
		
		sql.append(" order by O.create_date desc");
		
		System.out.println(sql.toString());
		
		
		ParameterizedRowMapper<OrderDTO> mapper = new ParameterizedRowMapper<OrderDTO>() {

			public OrderDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				
				OrderDTO dto = new OrderDTO();
				
				dto.setOrderId(rs.getString("order_id"));
				dto.setOcid(rs.getString("ocid"));
				dto.setErrorMessage(rs.getString("err_msg"));
				
				
				dto.setOrderSumCustName(rs.getString("cust_full_name"));
				dto.setMsisdn(rs.getString("service_num"));
				dto.setAppInDate(rs.getDate("app_date"));
				dto.setShopCode(rs.getString("shop_Cd"));
				dto.setDeliveryDate(rs.getDate("delivery_date"));
				dto.setSrvReqDate(rs.getDate("srv_req_date"));
				dto.setOrderStatus(rs.getString("order_status"));
				dto.setCheckPoint(rs.getString("check_point"));
				dto.setReasonCd(rs.getString("reason_Cd"));
				dto.setSalesCd(rs.getString("sales_cd"));
				dto.setBusTxnType(rs.getString("order_type"));
				dto.setAllowCancelInd(rs.getString("ALLOW_CANCEL_IND"));//add by wilson 20120405
				dto.setBomOrderStatus(rs.getString("bom_order_status"));
				dto.setActSrvReqDate(rs.getDate("act_srv_req_date"));
		
				dto.setLastUpdateDate(rs.getTimestamp("LAST_UPDATE_DATE")); //add by wilson 20120529
				dto.setDeliveryTimeSlot(rs.getString("delivery_time_slot"));
				dto.setBomFrozenInd("Y".equals(rs.getString("BOM_FROZEN_IND")) ? "Y" : "N");
				
				dto.setBrand(rs.getString("BRAND"));
				
				return dto;
			}
		};
		try {

			List<OrderDTO> result = simpleJdbcTemplate.query(sql.toString(), mapper, params);
			if (result != null && result.size() > 0) {	
				
				boolean isDirectFromAlertMessage = "ALERT".equalsIgnoreCase(orderStatus);
				
				for (OrderDTO dto: result) {
					try {
						String multiSimErrMsg = getMultiSimErrorMsg(dto.getOrderId());
						if (multiSimErrMsg != null && multiSimErrMsg.length() > 0) {
							if (dto.getErrorMessage() != null && dto.getErrorMessage().length() > 0) {
								dto.setErrorMessage(dto.getErrorMessage() + "<br/>" + multiSimErrMsg);
							} else {
								dto.setErrorMessage(multiSimErrMsg);
							}
						}
						
						// Start INC000000200671, INC000000201869
						if (!isDirectFromAlertMessage
								|| (isDirectFromAlertMessage && enableAlertMessageForceCancellationRemainingDay)) {
							dto.setForceCancelRemainDays(getforceCancelRemainDays(dto.getOrderId(),  dto.getBusTxnType(),  dto.getOrderStatus(),  dto.getReasonCd()));
						}
						// End INC000000200671, INC000000201869
						
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			return result;

	
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException");
		} catch (Exception e) {
			logger.error("Exception caught in findOrderEnquiry()", e);
			throw new DAOException(e.getMessage(), e);
		}
		
		return null;
		
	}
	
	// copy from OrderDAO.java - Herbert 20120229
	public void updateCcsOrderStatus(String refNum, String status, String checkPoint, String reasonCd,
			String errCode, String errString, String lastUpdateBy) throws DAOException {

		logger.info("updateOrderStatus is called");
		logger.info("update refNum : " + refNum);
		logger.info("update status : " + status);
		logger.info("update checkPoint : " + checkPoint);
		logger.info("update reasonCd : " + reasonCd);
		logger.info("update errCode : " + errCode);
		logger.info("update errString : " + errString);

		String SQL = "update bomweb_order set order_status = ?, "
					+ "err_cd = ?, err_msg = ?, "
					+ "CHECK_POINT = ?, REASON_CD = ?, "
					+ "last_update_by = ?, last_update_date = sysdate "
					+ "where order_id = ?";

		try {
			simpleJdbcTemplate.update(SQL, status, errCode, errString, checkPoint, reasonCd, lastUpdateBy, refNum);
		} catch (Exception e) {
			logger.error("Exception caught in updateCcsOrderStatus()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public void updateCcsOrderStatusToCancel(String orderId, String status, String checkPoint, String reasonCd
			 , String lastUpdateBy, String histSeqNo) throws DAOException {

		logger.info("updateOrderStatus is called");
		logger.info("update refNum : " + orderId);
		logger.info("update status : " + status);
		logger.info("update checkPoint : " + checkPoint);
		logger.info("update reasonCd : " + reasonCd);
		logger.info("update histSeqNo : " + histSeqNo);
		

		String SQL = "update bomweb_order set order_status = ?, "
					+ "CHECK_POINT = ?, REASON_CD = ? "
					+ ", last_update_by = ?, last_update_date = sysdate , HIST_SEQ_NO=? "
					+ "where order_id = ?";

		try {
			simpleJdbcTemplate.update(SQL, status,  checkPoint,  reasonCd, lastUpdateBy, histSeqNo, orderId);
		} catch (Exception e) {
			logger.error("Exception caught in updateCcsOrderStatusToCancel()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}

	private static String updatePendingOrderCheckPointSQL = "UPDATE" +
			" bomweb_order" +
			" SET" +
			" CHECK_POINT = :toCheckPoint" +
			" WHERE ORDER_ID = :orderId" +
			" AND ORDER_STATUS = :orderStatus" +
			" AND CHECK_POINT = :fromCheckPoint";
	public void updatePendingOrderCheckPoint(String orderId, String fromCheckPoint, String toCheckPoint) throws DAOException {
		logger.info("updatePendingOrderCheckPointSQL @ MobCcsOrderDAO is called");
		try {
			logger.info("updatePendingOrderCheckPoint() @ MobCcsOrderDAO: " + updatePendingOrderCheckPointSQL);
			
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("orderId", orderId);
			params.addValue("orderStatus", "01");
			params.addValue("fromCheckPoint", fromCheckPoint);
			params.addValue("toCheckPoint", toCheckPoint);

			simpleJdbcTemplate.update(updatePendingOrderCheckPointSQL, params);
		} catch (Exception e) {
			logger.error("Exception caught in updatePendingOrderCheckPoint()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	//copy from OrderDAO.java - Herbert 20120301
	public void updateCcsBulkReqStatusSuccess(String refNum, String ocid, String status, String checkPoint, String reasonCd,
			String errCode, String lastUpdateBy) throws DAOException {

		logger.info("updateCcsBulkReqStatusSuccess is called");
		logger.info("update refNum : " + refNum);
		logger.info("update ocid : " + ocid);
		logger.info("update status : " + status);
		logger.info("update checkPoint : " + checkPoint);
		logger.info("update reasonCd : " + reasonCd);
		logger.info("update errCode : " + errCode);

		String SQL = "update bomweb_order set " +
					"ocid = ?, " +
					"order_status = ?, " +
					"CHECK_POINT = ?, " +
					"REASON_CD = ?, " +
					"err_cd = ?, "+
					"last_update_by = 'AutoProcess', last_update_date = sysdate, " +
					"BOM_CREATION_DATE = sysdate " +
					"where order_id = ?";

		try {
			simpleJdbcTemplate.update(SQL, ocid, status, checkPoint, reasonCd, errCode, refNum);
		} catch (Exception e) {
			logger.error("Exception caught in updateCcsBulkReqStatusSuccess()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}

	
	// copy from OrderDAO.java - Herbert 20120301
	// add LOB = 'MOB' by Herbert 20120424
	public String getAutoProcessOrderId() throws DAOException {

		String orderId = null;
		logger.info("getAutoProcessOrderId is called ");

		String SQL = "select ORDER_ID " +
					"from (select O.ORDER_ID, "+
					" O.ORDER_STATUS, "+
					" O.SRV_REQ_DATE " +
					"from BOMWEB_ORDER O "+
					"where ORDER_STATUS = '"+ BomWebPortalConstant.BWP_MOBCCS_ORDER_PENDING + "'  "+
					"and O.CHECK_POINT = '"+ BomWebPortalConstant.BWP_MOBCCS_CHECK_POINT_PENDING + "'  "+
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
			logger.error("Exception caught in getAutoProcessOrderId()", e);
			throw new DAOException(e.getMessage(), e);
		}
		return orderId;
	}
	
	public void updateCcsOrderCreateStatus(String refNum,String status,String checkPoint,String reasonCd) throws DAOException {
		
		String[] resultArray = new String[] {String.valueOf(BomWebPortalConstant.ERRCODE_FAIL), ""};
		
		try {			
			SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
				.withSchemaName("OPS$CNM")
            	.withCatalogName("PKG_SB_MOB_ORDER")
            	.withProcedureName("update_order_create_status");
			jdbcCall.setAccessCallParameterMetaData(false);
			jdbcCall.declareParameters(
					new SqlParameter("i_order_id", Types.VARCHAR),
					new SqlParameter("i_order_status", Types.VARCHAR),
					new SqlParameter("i_check_point", Types.VARCHAR),
					new SqlParameter("i_reason_cd", Types.VARCHAR),
					new SqlOutParameter("o_errCode", Types.INTEGER),
					new SqlOutParameter("o_errText", Types.VARCHAR));
		
			MapSqlParameterSource inMap = new MapSqlParameterSource();
			inMap.addValue("i_order_id", refNum);
			inMap.addValue("i_order_status", status);
			inMap.addValue("i_check_point", checkPoint);
			inMap.addValue("i_reason_cd", reasonCd);
			SqlParameterSource in = inMap;
			
			Map out = jdbcCall.execute(in);
			
			if (((Integer) out.get("o_errCode"))!= null ) {
				resultArray[0] = ((Integer) out.get("o_errCode")).toString();
			}

			resultArray[1] = (String)out.get("o_errText");
			
			logger.info("PKG_SB_MOB_ORDER.update_order_create_status() output error_code = " + resultArray[0]);
			logger.info("PKG_SB_MOB_ORDER.update_order_create_status() output error_text = " + resultArray[1]);
			
			//return resultArray;
			
		} catch (Exception e) {
			logger.error("Exception caught in updateCcsOrderCreateStatus()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	//James 20120313
	// add LOB = 'MOB' by Herbert 20120424
	public List<OrderDTO> getUrgentOrder (String deliveryDate, String orderId) throws DAOException {
		logger.info("findUrgentOrder is called");
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		
		StringBuffer sql =  new StringBuffer("select O.ORDER_ID,\n" +
				"       O.OCID,\n" + 
				"       O.ERR_MSG,\n" + 
				"       C.FIRST_NAME || ' ' || C.LAST_NAME CUST_FULL_NAME,\n" + 
				"		T.TIMEFROM || '-' || T.TIMETO DELIVERY_TIME_SLOT," +
				"       O.MSISDN SERVICE_NUM,\n" + 
				"       D.DELIVERY_DATE,\n" +
				"       O.SRV_REQ_DATE,\n" + 
				"       O.ORDER_STATUS,\n" + 
				"       O.CHECK_POINT,\n" + 
				"       O.REASON_CD,\n" + 
				"       O.ORDER_TYPE,\n" +
				"       O.SHOP_CD,\n" +
				"       O.SALES_CD\n" + 
				"  from BOMWEB_ORDER O, BOMWEB_DELIVERY D, BOMWEB_CUSTOMER C, W_DELIVERY_TIMESLOT T\n" + 
				" where O.ORDER_ID = D.ORDER_ID\n" + 
				"   and O.ORDER_ID = C.ORDER_ID\n" +
				"	and D.DELIVERY_TIME_SLOT = T.TIMESLOT\n" +
				"	and D.URGENT_IND = 'Y' "+ 
				"   and O.LOB = 'MOB' "
			);
				
		if (orderId != null && !orderId.isEmpty()) {
			sql.append(" and O.ORDER_ID = upper (trim(:orderId)) "); //add trim 20120307
			orderId = orderId.replace(" ", "");
			orderId = orderId.trim().toUpperCase();
			params.addValue("orderId", orderId);
		}
				
		if (deliveryDate != null && !deliveryDate.isEmpty()) {
				sql.append(" and D.DELIVERY_DATE = TO_DATE(:deliveryDate, 'DD/MM/YYYY')");
			params.addValue("deliveryDate", deliveryDate);
		}		
		
		ParameterizedRowMapper<OrderDTO> mapper = new ParameterizedRowMapper<OrderDTO>() {

			public OrderDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
								
				OrderDTO dto = new OrderDTO();
				
				dto.setOrderId(rs.getString("order_id"));
				dto.setOcid(rs.getString("ocid"));
				dto.setOrderStatus(rs.getString("order_status"));
				dto.setErrorMessage(rs.getString("err_msg"));
				dto.setOrderSumCustName(rs.getString("cust_full_name"));
				dto.setMsisdn(rs.getString("service_num"));
				dto.setDeliveryDate(rs.getDate("delivery_date"));
				dto.setDeliveryTimeSlot(rs.getString("delivery_time_slot"));
				dto.setSrvReqDate(rs.getDate("srv_req_date"));
				dto.setCheckPoint(rs.getString("check_point"));
				dto.setReasonCd(rs.getString("reason_Cd"));
				dto.setSalesCd(rs.getString("sales_cd"));
				dto.setShopCode(rs.getString("shop_cd"));
				dto.setBusTxnType(rs.getString("order_type"));
				
				return dto;
			}
		};
		
		try {
			return simpleJdbcTemplate.query(sql.toString(), mapper, params);
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException");
		} catch (Exception e) {
			logger.error("Exception caught in findUrgentOrder()", e);
			throw new DAOException(e.getMessage(), e);
		}
		
		return null;
		
	}

	//add by Herbert 20120314
	public String getMultiIMEI(String orderId)throws DAOException {
			logger.info("getMultiIMEI is called");
			
			MapSqlParameterSource params = new MapSqlParameterSource();
			
			StringBuffer sql =  new StringBuffer("select sa.item_code, sa.item_serial\n " 
								+ "from bomweb_stock_assgn sa, \n"
								+ "BOMWEB_SUBSCRIBED_ITEM SI, \n"
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
	
	public String cancelOrderMrtProcess(String orderId) throws DAOException {

		try {			
			SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
				.withSchemaName("OPS$CNM")
            	.withCatalogName("PKG_SB_MOB_ORDER")
            	.withProcedureName("cancel_order_mrt_process");
			jdbcCall.setAccessCallParameterMetaData(false);
			jdbcCall.declareParameters(
					new SqlParameter("i_order_id", Types.VARCHAR),
					new SqlOutParameter("errCode", Types.INTEGER),
					new SqlOutParameter("errText", Types.VARCHAR));

			MapSqlParameterSource inMap = new MapSqlParameterSource();
			inMap.addValue("i_order_id", orderId);
			SqlParameterSource in = inMap;
			
			Map out = jdbcCall.execute(in);
			int error_code = BomWebPortalConstant.ERRCODE_FAIL;

			if (((Integer) out.get("errCode"))!= null ) {
				error_code = ((Integer) out.get("errCode")).intValue();
			}
			String error_text = (String)out.get("errText");

			logger.info("PKG_SB_MOB_ORDER.cancel_order_mrt_process() output error_code = " + error_code);
			logger.info("PKG_SB_MOB_ORDER.cancel_order_mrt_process() output error_text = " + error_text);

			return error_text;
			
		} catch (Exception e) {
			logger.error("Exception caught in cancelOrderMrtProcess()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public String cancelOrderStockProcess(String orderId) throws DAOException {
		try {			
			SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
				.withSchemaName("OPS$CNM")
            	.withCatalogName("pkg_stock_assign")
            	.withProcedureName("release_stock_assgn_fe");
			jdbcCall.setAccessCallParameterMetaData(false);
			jdbcCall.declareParameters(
					new SqlParameter("i_order_id", Types.VARCHAR),
					new SqlOutParameter("gnRetVal", Types.INTEGER),
					new SqlOutParameter("gnErrCode", Types.INTEGER),
					new SqlOutParameter("gsErrText", Types.VARCHAR));

			MapSqlParameterSource inMap = new MapSqlParameterSource();
			inMap.addValue("i_order_id", orderId);
			SqlParameterSource in = inMap;
			
			Map out = jdbcCall.execute(in);
			int error_code = BomWebPortalConstant.ERRCODE_FAIL;

			if (((Integer) out.get("gnErrCode"))!= null ) {
				error_code = ((Integer) out.get("gnErrCode")).intValue();
			}
			String error_text = (String)out.get("gsErrText");

			logger.info("PKG_SB_MOB_ORDER.cancelOrderStockProcess() output error_code = " + error_code);
			logger.info("PKG_SB_MOB_ORDER.cancelOrderStockProcess() output error_text = " + error_text);

			return error_text;
			
		} catch (Exception e) {
			logger.error("Exception caught in cancelOrderStockProcess()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public String cancelCourierQuotaProcess(String orderId, String username) throws DAOException {

		try {			
			SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
				.withSchemaName("OPS$CNM")
            	.withCatalogName("pkg_sb_mob_ccs_util")
            	.withProcedureName("courier_vendor_deassign_fe");
			jdbcCall.setAccessCallParameterMetaData(false);
			jdbcCall.declareParameters(
					new SqlParameter("i_order_id", Types.VARCHAR),
					new SqlParameter("i_update_by", Types.VARCHAR),
					new SqlOutParameter("o_ret_value", Types.INTEGER),
					new SqlOutParameter("o_errcode", Types.INTEGER),
					new SqlOutParameter("o_errtext", Types.VARCHAR));

			MapSqlParameterSource inMap = new MapSqlParameterSource();
			inMap.addValue("i_order_id", orderId);
			inMap.addValue("i_update_by", username);
			SqlParameterSource in = inMap;
			
			Map out = jdbcCall.execute(in);
			int error_code = BomWebPortalConstant.ERRCODE_FAIL;
			
			if ((Integer)out.get("o_ret_value")!=null){
				
			}
			if (((Integer) out.get("o_errcode"))!= null ) {
				error_code = ((Integer) out.get("o_errcode")).intValue();
			}
			String error_text = (String)out.get("o_errcode");

			logger.info("pkg_sb_mob_ccs_util.cancelCourierQuotaProcess() output error_code = " + error_code);
			logger.info("pkg_sb_mob_ccs_util.cancelCourierQuotaProcess() output error_text = " + error_text);

			return error_text;
			
		} catch (Exception e) {
			logger.error("Exception caught in cancelCourierQuotaProcess()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	

	public String orderHistoryProcess(String orderId) throws DAOException {

		try {			
			SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
				.withSchemaName("OPS$CNM")
            	.withCatalogName("PKG_SB_MOB_ORDER")
            	.withProcedureName("order_hist_process");
			jdbcCall.setAccessCallParameterMetaData(false);
			jdbcCall.declareParameters(
					new SqlParameter("i_order_id", Types.VARCHAR),
					new SqlOutParameter("o_errCode", Types.INTEGER),
					new SqlOutParameter("o_errText", Types.VARCHAR));

			MapSqlParameterSource inMap = new MapSqlParameterSource();
			inMap.addValue("i_order_id", orderId);
			SqlParameterSource in = inMap;
			
			Map out = jdbcCall.execute(in);
			int error_code = BomWebPortalConstant.ERRCODE_FAIL;

			if (((Integer) out.get("o_errCode"))!= null ) {
				error_code = ((Integer) out.get("o_errCode")).intValue();
			}
			String error_text = (String)out.get("o_errText");

			logger.info("PKG_SB_MOB_ORDER.cancel_order_mrt_process() output error_code = " + error_code);
			logger.info("PKG_SB_MOB_ORDER.cancel_order_mrt_process() output error_text = " + error_text);

			return error_text;
			
		} catch (Exception e) {
			logger.error("Exception caught in orderHistoryProcess()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	//add BATCH_NUM, 20121112 by wilson
	private static String bomwebPaymentTransSql = "insert into BOMWEB_PAYMENT_TRANS\n"
			+ "  (ORDER_ID,\n"
			+ "   MSISDN,\n"
			+ "   DELIVERY_DATE,\n"
			+ "   PAY_MTD_TYPE,\n"
			+ "   CC_NUM,\n"
			+ "   CC_EXP_DATE,\n"
			+ "   CC_ISSUE_BANK,\n"
			+ "   CC_INST_SCHEDULE,\n"
			+ "   APPROVE_CODE,\n"
			+ "   TRANS_DATE,\n"
			+ "   TRANS_STATUS,\n"
			+ "   PAYMENT_AMOUNT,\n"
			+ "   LOB,\n"
			+ "   BATCH_NUM,\n"
			+ "   CREATE_BY,\n"
			+ "   CREATE_DATE,\n"
			+ "   LAST_UPD_BY,\n"
			+ "   LAST_UPD_DATE)\n"
			+ "  select :newOrderId,\n"
			+ "         PT.MSISDN,\n"
			+ "         PT.DELIVERY_DATE,\n"
			+ "         PT.PAY_MTD_TYPE,\n"
			+ "         PT.CC_NUM,\n"
			+ "         PT.CC_EXP_DATE,\n"
			+ "         PT.CC_ISSUE_BANK,\n"
			+ "         PT.CC_INST_SCHEDULE,\n"
			+ "         PT.APPROVE_CODE,\n"
			+ "         PT.TRANS_DATE,\n"
			+ "         PT.TRANS_STATUS,\n"
			+ "         PT.PAYMENT_AMOUNT,\n"
			+ "         PT.LOB,\n"
			+ "         PT.BATCH_NUM,\n"
			+ "         PT.CREATE_BY,\n"
			+ "         PT.CREATE_DATE,\n"
			+ "         :lastUpdBy,\n"
			+ "         sysdate\n"
			+ "    from BOMWEB_PAYMENT_TRANS PT\n"
			+ "   where PT.ORDER_ID = :oldOrderId\n"
			+ "     and PT.TRANS_STATUS = 'SETTLED'";

	public void copyBomwebPaymentTrans(String oldOrderId, String newOrderId,
			String lastUpdBy) throws DAOException {
		logger.info("copyBomwebPaymentTrans @ MobCcsOrderDAO is called");
		try {
			logger.info("copyBomwebPaymentTrans() @ MobCcsOrderDAO: "
					+ bomwebPaymentTransSql);

			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("oldOrderId", oldOrderId);
			params.addValue("newOrderId", newOrderId);
			params.addValue("lastUpdBy", lastUpdBy);

			simpleJdbcTemplate.update(bomwebPaymentTransSql, params);
		} catch (Exception e) {
			logger.error("Exception caught in copyBomwebPaymentTrans()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	private static String updateBomwebPaymentTransToTRANSFERREDSql = "update BOMWEB_PAYMENT_TRANS PT\n"
			+ "   set PT.TRANS_STATUS  = 'TRANSFERRED',\n"
			+ "       PT.LAST_UPD_BY   = :lastUpdBy,\n"
			+ "       PT.LAST_UPD_DATE = sysdate\n"
			+ " where PT.ORDER_ID = :oldOrderId";

	public void updateBomwebPaymentTransStatus(String oldOrderId,
			String lastUpdBy) throws DAOException {
		logger.info("updateBomwebPaymentTransStatus @ MobCcsOrderDAO is called");
		try {
			logger.info("updateBomwebPaymentTransStatus() @ MobCcsOrderDAO: "
					+ updateBomwebPaymentTransToTRANSFERREDSql);

			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("oldOrderId", oldOrderId);
			params.addValue("lastUpdBy", lastUpdBy);

			simpleJdbcTemplate.update(updateBomwebPaymentTransToTRANSFERREDSql,
					params);
		} catch (Exception e) {
			logger.error("Exception caught in updateBomwebPaymentTransStatus()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public String getOrderHistSeqNo(String orderId) throws DAOException {
		String result = "";
		
		String SQLnone399 = "select nvl(max(OH.SEQ_NO),1)\n"
				+ "  from BOMWEB_ORDER_HIST OH\n" + " where OH.ORDER_ID = ?\n"
				+ "   and OH.LAST_UPDATE_DATE <=\n"
				+ "       (select max(PT.TRANS_DATE)\n"
				+ "          from BOMWEB_PAYMENT_TRANS PT\n"
				+ "         where PT.ORDER_ID = ?)";

		String SQL399 = "select NVL(max(OH.SEQ_NO), 0)\n"
				+ "         from BOMWEB_ORDER_HIST OH\n"
				+ "        where OH.CHECK_POINT = '399'\n"
				+ "          and OH.ORDER_ID = ?";

		try {
			String SQL399Seq = (String) simpleJdbcTemplate.queryForObject(
					SQL399, String.class, orderId);
			String SQLnone399Seq = (String) simpleJdbcTemplate.queryForObject(
					SQLnone399, String.class, orderId, orderId);
			if ("0".equals(SQL399Seq)) {
				result = SQLnone399Seq;
			} else {
				result = SQL399Seq;
			}

		} catch (Exception e) {
			throw new DAOException(e.getMessage(), e);
		}
		logger.info(orderId + "getOrderHistSeqNo==>" + result);

		return result;
	}
	
	
	private static String copyBomwebStockAssignSql = "insert into BOMWEB_STOCK_ASSGN\n"
			+ "  (ORDER_ID,\n"
			+ "   ITEM_CODE,\n"
			+ "   ITEM_SERIAL,\n"
			+ "   STATUS_ID,\n"
			+ "   APPLY_DATE,\n"
			+ "   CREATE_BY,\n"
			+ "   CREATE_DATE,\n"
			+ "   LAST_UPD_BY,\n"
			+ "   LAST_UPD_DATE)\n"
			+ "  select :newOrderId, --new order id\n"
			+ "         SA.ITEM_CODE,\n"
			+ "         SA.ITEM_SERIAL,\n"
			+ "         SA.STATUS_ID,\n"
			+ "         SA.APPLY_DATE,\n"
			+ "         :lastUpdBy,\n"
			+ "         sysdate,\n"
			+ "         :lastUpdBy,\n"
			+ "         sysdate\n"
			+ "    from BOMWEB_STOCK_ASSGN SA\n"
			+ "   where SA.ORDER_ID = :oldOrderId --old_order_id\n"
			+ "     and SA.STATUS_ID = '19'";

	public void copyBomwebStockAssign(String oldOrderId, String newOrderId,
			String lastUpdBy) throws DAOException {
		logger.info("copyBomwebStockAssign @ MobCcsOrderDAO is called");
		try {
			logger.info("copyBomwebStockAssign() @ MobCcsOrderDAO: "
					+ bomwebPaymentTransSql);

			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("oldOrderId", oldOrderId);
			params.addValue("newOrderId", newOrderId);
			params.addValue("lastUpdBy", lastUpdBy);

			simpleJdbcTemplate.update(copyBomwebStockAssignSql, params);
		} catch (Exception e) {
			logger.error("Exception caught in copyBomwebStockAssign()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public String insStockAssgnByCloneOrder(String oldOrderId, String newOrderId, String lastUpdBy) throws DAOException {
		logger.info("insStockAssgnByCloneOrder @ MobCcsOrderDAO is called");
		try {
			SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
											.withSchemaName("OPS$CNM")
											.withCatalogName("PKG_STOCK_ASSIGN")
											.withProcedureName("ins_stock_assgn_by_clone_order");
			jdbcCall.setAccessCallParameterMetaData(false);
			jdbcCall.declareParameters(
					new SqlParameter("i_old_order_id", Types.VARCHAR),
					new SqlParameter("i_new_order_id", Types.VARCHAR),
					new SqlParameter("i_last_upd_by", Types.VARCHAR),
					new SqlOutParameter("gnRetVal", Types.INTEGER),
					new SqlOutParameter("gnErrCode", Types.VARCHAR),
					new SqlOutParameter("gsErrText", Types.VARCHAR));
			
			MapSqlParameterSource inMap = new MapSqlParameterSource();
			inMap.addValue("i_old_order_id", oldOrderId);
			inMap.addValue("i_new_order_id", newOrderId);
			inMap.addValue("i_last_upd_by", lastUpdBy);
			SqlParameterSource in = inMap;
			Map out = jdbcCall.execute(in);
			int error_code = BomWebPortalConstant.ERRCODE_FAIL;
			if (((Integer) out.get("o_errCode"))!= null ) {
				error_code = ((Integer) out.get("o_errCode")).intValue();
			}
			String error_text = (String) out.get("o_errText");
			logger.info("PKG_STOCK_ASSIGN.ins_stock_assgn_by_clone_order() output error_code = " + error_code);
			logger.info("PKG_STOCK_ASSIGN.ins_stock_assgn_by_clone_order() output error_text = " + error_text);
			return error_text;
		} catch (Exception e) {
			logger.error("Exception caught in insStockAssgnByCloneOrder()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public String mrtTransfer(String oldOrderId, String newOrderId) throws DAOException {

		try {			
			SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
				.withSchemaName("OPS$CNM")
            	.withCatalogName("PKG_SB_MOB_ORDER")
            	.withProcedureName("mrt_transfer");
			jdbcCall.setAccessCallParameterMetaData(false);
			jdbcCall.declareParameters(
					new SqlParameter("old_order_id", Types.VARCHAR),
					new SqlParameter("new_order_id", Types.VARCHAR),
					new SqlOutParameter("o_errCode", Types.INTEGER),
					new SqlOutParameter("o_errText", Types.VARCHAR));

			MapSqlParameterSource inMap = new MapSqlParameterSource();
			inMap.addValue("old_order_id", oldOrderId);
			inMap.addValue("new_order_id", newOrderId);
			SqlParameterSource in = inMap;
			
			Map out = jdbcCall.execute(in);
			int error_code = BomWebPortalConstant.ERRCODE_FAIL;

			if (((Integer) out.get("o_errCode"))!= null ) {
				error_code = ((Integer) out.get("o_errCode")).intValue();
			}
			String error_text = (String)out.get("o_errText");

			logger.info("PKG_SB_MOB_ORDER.mrt_transfer() output error_code = " + error_code);
			logger.info("PKG_SB_MOB_ORDER.mrt_transfer() output error_text = " + error_text);

			return error_text;
			
		} catch (Exception e) {
			logger.error("Exception caught in mrtTransfer()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public String getOrderEnquiryCount (String startDate, String endDate, 
			String orderId, String orderStatus, String variousDateType, String channel, 
			String staffId, String orderType, String category, String areaCd, 
			String shopCd, String group) throws DAOException {
		logger.info("getOrderEnquiryCount is called");
		
		/*String startDate, 	null, 
		String endDate, 	null, 
		String orderId, 	ALL, 
		String orderStatus, 	ALERT, 
		String variousDateType, 	null, 
		String channel, 	salesUserDto.getChannelCd(), 
		String staffId, 	salesUserDto.getUsername(), 
		String orderType, 	ALL, 
		String category, 	salesUserDto.getCategory(), 
		String areaCd, 	salesUserDto.getAreaCd(), 
		String shopCd, 	salesUserDto.getShopCd(), 
		String group)	null)*/

		
		MapSqlParameterSource params = new MapSqlParameterSource();
		
		StringBuffer sql =  new StringBuffer("SELECT COUNT(O.ORDER_ID ) " +
				"FROM BOMWEB_ORDER O " +
				"LEFT JOIN BOMWEB_DELIVERY D " +
				"ON (O.ORDER_ID = D.ORDER_ID) " +
				"LEFT JOIN BOMWEB_CUSTOMER C " +
				"ON (O.ORDER_ID = C.ORDER_ID) " +
				"WHERE O.LOB    = 'MOB' " +
				"AND O.SHOP_CD in (SELECT DISTINCT SHOP_CD FROM BOMWEB_SHOP WHERE CHANNEL_ID in ('2'))");
		
		if (!"ALL".equalsIgnoreCase(orderStatus) && !"ALERT".equalsIgnoreCase(orderStatus)) {
			if (Utility.validateNumber(orderStatus) && !orderStatus.equals("999")) {
				sql.append(" and O.CHECK_POINT = :orderStatus ");
			} else {
				sql.append(" and O.REASON_CD = :orderStatus ");
			}
			params.addValue("orderStatus", orderStatus);
		} else if ("ALERT".equalsIgnoreCase(orderStatus)) {
			sql.append(" and O.ORDER_STATUS = '99' ");
		}
				
		if (!"ALL".equalsIgnoreCase(orderId) && !orderId.isEmpty()) {
			sql.append(" and O.ORDER_ID = upper (trim(:orderId)) "); //add trim 20120307
			orderId = orderId.replace(" ", "");
			orderId = orderId.trim().toUpperCase();
			params.addValue("orderId", orderId);
		}
		
		if (!"ALL".equalsIgnoreCase(orderType) && !orderType.isEmpty()) {
			sql.append(" and O.ORDER_TYPE = :orderType");
			params.addValue("orderType", orderType);
		}
		
		if (startDate != null && !startDate.isEmpty() && endDate != null && !endDate.isEmpty()) {
			if ("APP".equalsIgnoreCase(variousDateType)) {
				sql.append(" and trunc(O.APP_DATE) between TO_DATE(:startDate, 'DD/MM/YYYY') and TO_DATE(:endDate, 'DD/MM/YYYY')");
			} else if ("DEL".equalsIgnoreCase(variousDateType)) {
				sql.append(" and trunc(D.DELIVERY_DATE) between TO_DATE(:startDate, 'DD/MM/YYYY') and TO_DATE(:endDate, 'DD/MM/YYYY')");
			} else if ("SR".equalsIgnoreCase(variousDateType)) {
				sql.append(" and trunc(O.SRV_REQ_DATE) between TO_DATE(:startDate, 'DD/MM/YYYY') and TO_DATE(:endDate, 'DD/MM/YYYY')");
			}
			params.addValue("startDate", startDate);
			params.addValue("endDate", endDate);
		}		
		
		//staff hierarchy
		if (group == null || "CH".equalsIgnoreCase(group)) {
			sql.append(" and O.SALES_CD IN (select distinct STAFF_ID from BOMWEB_SALES_ASSIGNMENT BSA where BSA.CHANNEL_CD = :channel" +
					" AND trunc(O.APP_DATE) BETWEEN BSA.START_DATE AND NVL(BSA.END_DATE, trunc(sysdate))");
			params.addValue("channel", channel);
			if (BomWebPortalCcsConstant.USER_CATEGORY_SALESMANAGER.equalsIgnoreCase(category)) {
				sql.append(")");
			} else if (BomWebPortalCcsConstant.USER_CATEGORY_MANAGER.equalsIgnoreCase(category)) {
				sql.append(" and BSA.CENTRE_CD = :areaCd ");
				sql.append(" and BSA.TEAM_CD = :shopCd )");
				params.addValue("areaCd", areaCd);
				params.addValue("shopCd", shopCd);
			}else if (BomWebPortalCcsConstant.USER_CATEGORY_FRONTLINE.equalsIgnoreCase(category)) {
				sql.append(" and BSA.CENTRE_CD = :areaCd ");
				sql.append(" and BSA.TEAM_CD = :shopCd ");
				sql.append(" and BSA.staff_id = :staffId )");
				
				//add String staffId
				params.addValue("areaCd", areaCd);
				params.addValue("shopCd", shopCd);
				params.addValue("staffId", staffId);
			}else{
				sql.append(" ) ");
			}
		} else {
			if (!"ALL".equalsIgnoreCase(channel)) {
				sql.append(" and O.SALES_CD IN (select distinct STAFF_ID from BOMWEB_SALES_ASSIGNMENT where CHANNEL_CD = :channel) ");
				params.addValue("channel", channel);
			}
		}

		try {
			
			return (String) simpleJdbcTemplate.queryForObject(
					sql.toString(), String.class, params);
		
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException");
		} catch (Exception e) {
			logger.error("Exception caught in getOrderEnquiryCount()", e);
			throw new DAOException(e.getMessage(), e);
		}
		
		return "-1";
		
	}
	
	public String getforceCancelRemainDays(String orderId) throws DAOException {
		String result = "";
		
		String SQLnone399 = "select nvl(max(OH.SEQ_NO),1)\n"
				+ "  from BOMWEB_ORDER_HIST OH\n" + " where OH.ORDER_ID = ?\n"
				+ "   and OH.LAST_UPDATE_DATE <=\n"
				+ "       (select max(PT.TRANS_DATE)\n"
				+ "          from BOMWEB_PAYMENT_TRANS PT\n"
				+ "         where PT.ORDER_ID = ?)";

		String SQL399 = "select NVL(max(OH.SEQ_NO), 0)\n"
				+ "         from BOMWEB_ORDER_HIST OH\n"
				+ "        where OH.CHECK_POINT = '399'\n"
				+ "          and OH.ORDER_ID = ?";

		try {
			String SQL399Seq = (String) simpleJdbcTemplate.queryForObject(
					SQL399, String.class, orderId);
			String SQLnone399Seq = (String) simpleJdbcTemplate.queryForObject(
					SQLnone399, String.class, orderId, orderId);
			if ("0".equals(SQL399Seq)) {
				result = SQLnone399Seq;
			} else {
				result = SQL399Seq;
			}

		} catch (Exception e) {
			throw new DAOException(e.getMessage(), e);
		}
		logger.info(orderId + "getOrderHistSeqNo==>" + result);

		return result;
	}
	
	public String getforceCancelRemainDays(String orderId, String orderType, String orderStatus, String reasonCd) {
		
		// orderType="DRAF" ==> lastUpdateDate+7
		// orderType="PRE" ==> O.SHOP_CD:OBS ==> 72 Hours, other O.SHOP_CD=>48 Hours
		// orderType="PEND" and OrderStatus=99
		// hottestModel max fallout day+3
		// normatModel max fallout day+7
		
		String result = "";
		// o.order_status, '03', 'Cancelling : N/A', '04', 'Cancelled : N/A'
		if (orderStatus != null && "03".equals(orderStatus)) {
			return "Cancelling : N/A";
		}

		if (orderStatus != null && "04".equals(orderStatus)) {
			return "Cancelled : N/A";
		}

		String SQL_DRAF_PRE_PEND =

				"select DECODE(O.ORDER_TYPE, 'DRAF', 'DRAF Order:' ||\n" +
				"               (FLOOR((O.LAST_UPDATE_DATE + 7 - sysdate) * 24)) ||\n" + 
				"               ' hr ' ||\n" + 
				"               mod((FLOOR(((O.LAST_UPDATE_DATE + 7) -\n" + 
				"                          sysdate) * 24 * 60)), 60) ||\n" + 
				"               ' min ', 'PRE', 'PRE Order :' ||\n" + 
				"               (FLOOR((O.APP_DATE +\n" + 
				"                      DECODE(O.SHOP_CD, 'OBS', 3, 2) -\n" + 
				"                      sysdate) * 24)) || ' hr ' ||\n" + 
				"               mod(FLOOR((((O.APP_DATE +\n" + 
				"                         DECODE(O.SHOP_CD, 'OBS', 3, 2)) -\n" + 
				"                         sysdate)) * 24 * 60), 60) || ' min ', 'PEND', 'PEND', 'COS', 'COS')\n" + 
				"from BOMWEB_ORDER O\n" + 
				"where O.ORDER_ID = ?";


			
	


		if (orderType != null
				&& ("DRAF".equals(orderType) || "PRE".equals(orderType))) {
			String resuleDrafPrePend = (String) simpleJdbcTemplate
					.queryForObject(SQL_DRAF_PRE_PEND, String.class, orderId);
			return resuleDrafPrePend;
		}
		String hottestModelInd = "N";

		String SQLHottestModelIND = "select (select DECODE(count(*), 0, 'N', 'Y')\n"
				+ "             from BOMWEB_HOTTEST_MODEL     HM,\n"
				+ "                  BOMWEB_SUBSCRIBED_ITEM   SII,\n"
				+ "                  W_ITEM_PRODUCT_POS_ASSGN IPPA\n"
				+ "            where SII.ORDER_ID = O.ORDER_ID\n"
				+ "              and SII.ID = IPPA.ITEM_ID\n"
				+ "              and IPPA.POS_ITEM_CD = HM.ITEM_CODE\n"
				+ "              and TRUNC(O.APP_DATE) between HM.START_DATE and\n"
				+ "                  TRUNC(NVL(HM.END_DATE, sysdate)))\n"
				+ "     from BOMWEB_ORDER O\n" + "    where O.ORDER_ID = ?";

		hottestModelInd = (String) simpleJdbcTemplate.queryForObject(
				SQLHottestModelIND, String.class, orderId);

		int plusDays = 7;

		if ("Y".equals(hottestModelInd)) {
			plusDays = 3;
		}

		String SQLmaxFallOutDate =

			"select DECODE(MAX_DATE, null, null, floor((MAX_DATE + ? - sysdate) * 24) ||\n" +
			"       ' hr ' || mod(FLOOR(((MAX_DATE + ?) - sysdate) * 24 * 60), 60) ||\n" + 
			"       ' min ')\n" + 
			"  from (select (select max(F.OCCURANCE_DATE)\n" + 
			"                  from BOMWEB_ORDER_FALLOUT F, BOMWEB_CODE_LKUP CL\n" + 
			"                 where CL.CODE_TYPE = 'FORCE_CANCEL_FO_CODE'\n" + 
			"                   and F.ORDER_ID = O.ORDER_ID\n" + 
			"                   and F.REASON_CODE = CL.CODE_ID) MAX_DATE\n" + 
			"          from BOMWEB_ORDER O\n" + 
			"         where O.LOB = 'MOB'\n" + 
			"           and O.ORDER_ID = ?)";



		/*"select  FLOOR((select max(F.OCCURANCE_DATE)\n"
				+ "                from BOMWEB_ORDER_FALLOUT F, BOMWEB_CODE_LKUP CL\n"
				+ "               where CL.CODE_TYPE = 'FORCE_CANCEL_FO_CODE'\n"
				+ "                 and F.ORDER_ID = O.ORDER_ID\n"
				+ "                 and F.REASON_CODE = CL.CODE_ID) + ? - sysdate) *24\n"
				+ "  from BOMWEB_ORDER O\n" + " where O.ORDER_ID = ?";
				*/
		
		String SQLgetForceCancelInd="select DECODE(count(1), 0, 'N', 'Y') FORCE_CANCEL_IND\n" +
			"  from BOMWEB_CODE_LKUP CL\n" + 
			" where CL.CODE_TYPE = 'FORCE_CANCEL_FO_CODE'\n" + 
			"   and CL.CODE_ID = ?";


		if (orderType != null && ("COS".equals(orderType) ||"BRM".equals(orderType)  || "TOO1".equals(orderType) || "PEND".equals(orderType) && "99".equals(orderStatus))) {
			
			//need to check reason_cd not in force cancel cat.
			String forceCancelInd = (String) simpleJdbcTemplate.queryForObject(
					SQLgetForceCancelInd, String.class, reasonCd);
			
			if(forceCancelInd != null && "N".equals(forceCancelInd)){
				return result = "PEND Order : N/A";
				
			}else{
				String resulePend = (String) simpleJdbcTemplate.queryForObject(
						SQLmaxFallOutDate, String.class, plusDays,plusDays, orderId);
				if (resulePend != null || "".equals(resulePend)) {
					if ("Y".equals(hottestModelInd)) {
						result = "COS/PEND Order Hottest Model :" + resulePend ;
					} else {
						result = "COS/PEND Order Normal Model :" + resulePend ;
					}
				} else {
	
					result = "PEND Order : N/A";
				}
				return result;
			}
		}
		if (orderType != null && "PEND".equals(orderType)){
			result = "PEND Order : N/A";
		}else if  (orderType != null && ("COS".equals(orderType) ||"BRM".equals(orderType)  || "TOO1".equals(orderType))){
			result = "COS Order : N/A";
		}
			

		return "PEND Order : N/A";
	}
	
	
	
	
	
	public MnpDTO checkCCSPendingOrder(String orderId, String msisdn) throws DAOException {
		logger.info("checkCCSPendingOrder is called");
		
		List<MnpDTO> resultList = new ArrayList<MnpDTO>();
		
		String sql = "SELECT o.order_id, " +
			"  o.msisdn " +
			"FROM BOMWEB_ORDER O " +
			"WHERE O.LOB = 'MOB' " +
			"and O.ORDER_ID != decode(:orderId, null, 'NEW ORDER', :orderId) " +
			"AND O.MSISDN        = :msisdn " +
			"AND O.ORDER_TYPE    = 'PEND' " +
			"AND O.ORDER_STATUS IN ('01', '99') " +
			"AND O.OCID         IS NULL " +
			"AND O.SHOP_CD      IN " +
			"  (SELECT CL.CODE_ID FROM BOMWEB_CODE_LKUP CL WHERE CL.CODE_TYPE = 'CCS_CH' " +
			"  ) ";
				
	
		ParameterizedRowMapper<MnpDTO> mapper = new ParameterizedRowMapper<MnpDTO>() {

			public MnpDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
								
				MnpDTO dto = new MnpDTO();
				
				dto.setOrderId(rs.getString("order_id"));
				dto.setMsisdn(rs.getString("msisdn"));
				
				return dto;
			}
		};
		
		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("msisdn", msisdn);
			params.addValue("orderId", orderId);
			resultList = simpleJdbcTemplate.query(sql.toString(), mapper, params);
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException");
		} catch (Exception e) {
			logger.error("Exception caught in checkCCSPendingOrder()", e);
			throw new DAOException(e.getMessage(), e);
		}
		
		return CollectionUtils.isEmpty(resultList) ? null : resultList.get(0);
		
	}
	
	
	
	private static String copyBomwebMobOrderHsrmLogSql = "insert into BOMWEB_MOB_ORDER_HSRM_LOG\n"
			+ "  (ORDER_ID,\n"
			+ "   REF_ID,\n"
			+ "   ITEM_CODE,\n"
			+ "   SALES_CD,\n"
			+ "   ACTION_IND,\n"
			+ "   STATUS,\n"
			+ "   ERR_CD,\n"
			+ "   ERR_MSG,\n"
			+ "   CREATE_BY,\n"
			+ "   CREATE_DATE,\n"
			+ "   LAST_UPD_BY,\n"
			+ "   LAST_UPD_DATE,\n"
			+ "   QUEUE_STATUS,\n"
			+ "   QUEUE_BRAND,\n"
			+ "   OFFER_BRAND)\n"
			+ "  select :newOrderId, --new order id\n"
			+ "         BMOHL.REF_ID,\n"
			+ "         BMOHL.ITEM_CODE,\n"
			+ "         BMOHL.SALES_CD,\n"
			+ "         BMOHL.ACTION_IND,\n"
			+ "         BMOHL.STATUS,\n"
			+ "         BMOHL.ERR_CD,\n"
			+ "         BMOHL.ERR_MSG,\n"
			+ "         :lastUpdBy,\n"
			+ "         sysdate,\n"
			+ "         :lastUpdBy,\n"
			+ "         sysdate, \n"
			+ "         BMOHL.QUEUE_STATUS\n"
			+ "         BMOHL.QUEUE_BRAND\n"
			+ "         BMOHL.OFFER_BRAND\n"
			+ "    from BOMWEB_MOB_ORDER_HSRM_LOG BMOHL\n"
			+ "   where BMOHL.ORDER_ID = :oldOrderId --old_order_id\n";

	public void copyBomwebMobOrderHsrmLog(String oldOrderId, String newOrderId,
			String lastUpdBy) throws DAOException {
		logger.info("copyBomwebMobOrderHsrmLog @ MobCcsOrderDAO is called");
		try {
			logger.info("copyBomwebMobOrderHsrmLogSql() @ MobCcsOrderDAO: "
					+ copyBomwebMobOrderHsrmLogSql);

			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("oldOrderId", oldOrderId);
			params.addValue("newOrderId", newOrderId);
			params.addValue("lastUpdBy", lastUpdBy);

			simpleJdbcTemplate.update(copyBomwebMobOrderHsrmLogSql, params);
		} catch (Exception e) {
			logger.error("Exception caught in copyBomwebStockAssign()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public String assignSimForCancel(String orderId) throws DAOException {
		//String contextId = null;

		try {			
			SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
				.withSchemaName("OPS$CNM")
            	.withCatalogName("PKG_STOCK_ASSIGN")
            	.withProcedureName("ASSIGN_SIM_FOR_CANCEL_ORDER");
			jdbcCall.setAccessCallParameterMetaData(false);
			jdbcCall.declareParameters(
					new SqlParameter("i_order_id", Types.VARCHAR),
					new SqlOutParameter("gnretval", Types.INTEGER),
					new SqlOutParameter("gnerrcode", Types.INTEGER),
					new SqlOutParameter("gserrtext", Types.VARCHAR));
			
		
			
			MapSqlParameterSource inMap = new MapSqlParameterSource();
			inMap.addValue("i_order_id", orderId);
			SqlParameterSource in = inMap;
			
			Map out = jdbcCall.execute(in);
			int error_code = BomWebPortalConstant.ERRCODE_FAIL;
		//	int ret_val = BomWebPortalConstant.ERRCODE_FAIL; 
			
			if (((Integer) out.get("gnerrcode"))!= null ) {
				error_code = ((Integer) out.get("gnerrcode")).intValue();
			}

			String error_text = (String)out.get("gserrtext");
			
			//logger.info("PKG_SB_MOB_ORDER.ORDER_STATUS_PROCESS() output ret_val = " + ret_val);
			logger.info("PKG_SB_MOB_ORDER.AssignSimForCancel()() output error_code = " + error_code);
			logger.info("PKG_SB_MOB_ORDER.AssignSimForCancel()() output error_text = " + error_text);
			//logger.info("PKG_SB_MOB_ORDER.ORDER_STATUS_PROCESS()() output contextId = " + contextId);
			
			/*if ( (error_code != BomWebPortalConstant.ERRCODE_SUCCESS)) {
				contextId = null;
			}*/
			
			//logger.info("initialCeksAccess contextId = " + contextId);
			
			return error_text;
			
		} catch (Exception e) {
			logger.error("Exception caught in assignSimForCancel()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}	
}

