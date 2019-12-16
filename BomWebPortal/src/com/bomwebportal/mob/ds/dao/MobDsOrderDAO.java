package com.bomwebportal.mob.ds.dao;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.bomwebportal.dao.BaseDAO;
import com.bomwebportal.dto.OrderDTO;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.mob.ds.dto.MobDsStockItemDTO;
import com.bomwebportal.mob.ds.ui.MobDsCancelOrderUI;
import com.bomwebportal.util.BomWebPortalConstant;

public class MobDsOrderDAO extends BaseDAO{
	
	public List<OrderDTO> findOrderEnquiry (String startDate, String endDate, String orderId, 
			String orderStatus, String variousDateType, String channel, String staffId, 
			String orderType, String category, String areaCd, String shopCd, String group, 
			String mrt,String channelCd, String aoInd, String aoItemCode, String orderNature) throws DAOException {
		
		logger.debug("findOrderEnquiry is called");

		MapSqlParameterSource params = new MapSqlParameterSource();
				
		StringBuffer sql =  new StringBuffer("select O.ORDER_ID, O.OCID, "
				 //+ "       O.ERR_MSG, "
				 + "       trim(o.err_msg|| \n"
				 + "           decode(decode(o.err_msg, null, null, (select distinct parent_order_id \n"
				 + "               from bomweb_ord_mob_member \n"
				 + "               where parent_order_id=o.order_id \n"
				 + "               and err_msg is not null)), null, '', ' / ')|| \n"
				 + "		     (select LISTAGG(err_msg, ' / ') WITHIN GROUP (ORDER BY parent_order_id) from \n"
				 + "               (select parent_order_id, \n"
				 + "               'Member '||member_num||' '||msisdn||': '||member_status||' - '||err_msg err_msg \n"
				 + "               from bomweb_ord_mob_member \n"
				 + "               where err_msg is not null) \n"
				 + "          where parent_order_id = o.order_id)) err_msg,\n"
				 + "       C.LAST_NAME || ' ' || C.FIRST_NAME CUST_FULL_NAME, "
				 + "       O.MSISDN SERVICE_NUM, "
				 + "       O.APP_DATE, "
				 + "       O.SRV_REQ_DATE, "
				 + "       O.ORDER_STATUS, "
				 + "       O.CHECK_POINT, "
				 + "       O.REASON_CD, "
				 + "       O.ORDER_TYPE, "
				 + "       case when length(O.ORDER_ID)=13 then substr(O.ORDER_ID, 2, length(O.ORDER_ID)-9) else substr(O.ORDER_ID, 2, length(O.ORDER_ID)-8) END as SHOP_CD,"   
				// + "       SUBSTR(O.ORDER_ID, 2, length(O.ORDER_ID) - 8) SHOP_CD,"
				 + "       O.SALES_CD,	(select distinct basket_id from bomweb_subscribed_item "
				 + "	   		where order_id = O.ORDER_ID"
				 + "		   	and basket_id is not null "
				 + "			and rownum <=1) basket_id,"
				 + "	   (select distinct basket_type from w_basket_attribute_mv"
				 + "			where basket_id in (select distinct basket_id from bomweb_subscribed_item "
				 + "			where order_id = O.ORDER_ID"
				 + "			and basket_id is not null "
				 + "			and rownum <=1) and rownum <=1) basket_type," 
				 + "       FLOOR(sysdate - O.LAST_UPDATE_DATE) LAST_UPDATE_DATE_DIFF, "
				 + "       S.BOM_ORDER_STATUS, S.SRV_REQ_DATE ACT_SRV_REQ_DATE , O.LAST_UPDATE_DATE, "
				 + "	   O.SUPER_APP_IND, O.ORDER_APP_IND, O.REJECT_REMARK, O.PAYMT_CHK_IND, O.PAYMT_REC_DATE, "
				 + "       M.ORDER_NATURE"
				 + "  from BOMWEB_ORDER O, BOMWEB_CUSTOMER C, BOMWEB_SB_BOM_ORDER S, BOMWEB_ORDER_MOB M"
				 + " where O.ORDER_ID = C.ORDER_ID "
				 + "   and O.LOB = 'MOB' "
				 + "   and S.ORDER_ID(+) = O.ORDER_ID "
				 + "   and M.ORDER_ID = O.ORDER_ID ");
		
			params.addValue("orderNature", orderNature);
		
		if(!"ALL".equalsIgnoreCase(orderType)){
			sql.append(" and O.ORDER_TYPE = :orderType ");
			params.addValue("orderType", orderType);
		}
			
			
		if(!"ALL".equalsIgnoreCase(orderNature)){
			sql.append(" and M.ORDER_NATURE = :orderNature ");
			params.addValue("orderNature", orderNature);
		}
			
		
		if (!"ALL".equalsIgnoreCase(orderStatus) && !"ALERT".equalsIgnoreCase(orderStatus)) {
			sql.append(" and O.ORDER_STATUS = :orderStatus ");
			params.addValue("orderStatus", orderStatus);
		} else if ("ALL".equalsIgnoreCase(orderStatus) && StringUtils.isNotBlank(aoInd)) {
			sql.append(" and O.ORDER_STATUS in ('" 
					+ BomWebPortalConstant.BWP_ORDER_FAILED + "', '" 
					+ BomWebPortalConstant.BWP_ORDER_FALLOUT + "', '" 
					+ BomWebPortalConstant.BWP_ORDER_PROCESS + "', '" 
					+ BomWebPortalConstant.BWP_ORDER_QAPROCESS + "', '" 
					+ BomWebPortalConstant.BWP_ORDER_SUCCESS + "') ");
		}
	
		if (!"ALL".equalsIgnoreCase(orderId) && !orderId.isEmpty()) {
			sql.append(" and O.ORDER_ID = upper (trim(:orderId)) "); //add trim 20120307
			orderId = orderId.replace(" ", "");
			orderId = orderId.trim().toUpperCase();
			params.addValue("orderId", orderId);
		}
		
		if (StringUtils.isNotBlank(mrt)) {
			sql.append(" and (O.MSISDN = trim(:mrt) or trim(:mrt) in (select msisdn from BOMWEB_ORD_MOB_MEMBER where PARENT_ORDER_ID = O.ORDER_ID))");
			params.addValue("mrt", mrt);
		}
		
		if (startDate != null && !startDate.isEmpty() && endDate != null && !endDate.isEmpty()) {
			if ("APP".equalsIgnoreCase(variousDateType)) {
				sql.append(" and trunc(O.APP_DATE) between TO_DATE(:startDate, 'DD/MM/YYYY') and TO_DATE(:endDate, 'DD/MM/YYYY')");
			} else if ("SR".equalsIgnoreCase(variousDateType)) {
				sql.append(" and trunc(O.SRV_REQ_DATE) between TO_DATE(:startDate, 'DD/MM/YYYY') and TO_DATE(:endDate, 'DD/MM/YYYY')");
			}
			params.addValue("startDate", startDate);
			params.addValue("endDate", endDate);
		}
		
		if (StringUtils.isNotBlank(aoInd)) {
			/*sql.append(" and O.AO_IND = 'Y' ");
			if (StringUtils.isNotBlank(aoItemCode)) {
				sql.append(" and exists (SELECT ORDER_ID FROM BOMWEB_STOCK_ASSGN A where A.AO_IND = 'Y' and A.ORDER_ID = O.ORDER_ID and A.ITEM_CODE = :aoItemCode) ");
				params.addValue("aoItemCode", aoItemCode.trim());
			}*/
			sql.append(" and o.order_id in (select order_id from bomweb_stock_assgn a where a.item_serial is null ");
			if (StringUtils.isNotBlank(aoItemCode)) {
				sql.append(" and a.item_code = :aoItemCode ");
				params.addValue("aoItemCode", aoItemCode.trim());
			}
			sql.append(" ) ");
		}
		
		//staff hierarchy
		if (group == null || "CH".equalsIgnoreCase(group)) {

//			if ("MOB SUPPORT TEAM".equalsIgnoreCase(category)) { //BomWebPortalCcsConstant.USER_CATEGORY_SALESMANAGER
//				sql.append(" and O.SALES_CD IN (select distinct STAFF_ID from BOMWEB_SALES_ASSIGNMENT BSA where CHANNEL_CD in ('MDV, 'SIS')" +
//						" AND trunc(O.APP_DATE) BETWEEN BSA.START_DATE AND NVL(BSA.END_DATE, trunc(sysdate))");
//				sql.append(")");
//			} else 
			if ("SUPERVISOR".equalsIgnoreCase(category)) { //BomWebPortalCcsConstant.USER_CATEGORY_MANAGER
				if ("MDV".equals(channel)) {
					sql.append(" and O.SALES_CD IN (select distinct STAFF_ID from BOMWEB_SALES_ASSIGNMENT BSA "
							+ "where CHANNEL_CD = :channel "
							+ "AND trunc(O.APP_DATE) BETWEEN BSA.START_DATE AND NVL(BSA.END_DATE, trunc(sysdate)) "
							+ "AND team_cd=:shopCd) ");
					params.addValue("channel", channel);
					//sql.append("and  O.ORDER_ID like 'D'||:shopCd||'%'");
					params.addValue("shopCd", shopCd);
				}else if("SIS".equals(channel)) {
					/*sql.append(" and O.SALES_CD IN (select distinct bsa1.staff_id from bomweb_sales_assignment bsa1 "
							+ "inner join bomweb_sales_assignment bsa2 "
							+ "on bsa1.centre_cd = bsa2.centre_cd and bsa1.team_cd = bsa2.team_cd "
							+ "where bsa1.channel_id in (10, 11) "
							+ "AND trunc(O.APP_DATE) BETWEEN BSA1.START_DATE AND NVL(BSA1.END_DATE, trunc(sysdate)) "
							+ "AND trunc(O.APP_DATE) BETWEEN BSA2.START_DATE AND NVL(BSA2.END_DATE, trunc(sysdate)) "
							+ "and bsa2.staff_id = :staffId "
							+ "and bsa1.channel_cd = :channel) ");*/
					sql.append("and decode(O.lob, 'MOB', substr(O.order_id, 2, INSTR(O.order_id, 'M', -1)-2), O.shop_cd) in "
							+ "(select team_cd from bomweb_sales_assignment bsa "
							+ "where bsa.staff_id = :staffId)");
					params.addValue("staffId", staffId);
					//params.addValue("channel", channel);
					
					sql.append("and O.ORDER_ID like 'D'||'%'");
				}
			}else if ("FRONTLINE".equalsIgnoreCase(category)) { //BomWebPortalCcsConstant.USER_CATEGORY_FRONTLINE
				//sql.append(" and CENTRE_CD = :areaCd ");
				//sql.append(" and TEAM_CD = :shopCd ");
				sql.append(" and O.SALES_CD IN (select distinct STAFF_ID from BOMWEB_SALES_ASSIGNMENT BSA"
						+ " where CHANNEL_CD = :channel"
						+ " AND trunc(O.APP_DATE) BETWEEN BSA.START_DATE AND NVL(BSA.END_DATE, trunc(sysdate))");
				params.addValue("channel", channel);
				sql.append(" and staff_id = :staffId )");
				params.addValue("staffId", staffId);
			} else{
				//sql.append(")");
			}
		}else if ("SUPPORT".equalsIgnoreCase(group)) {
			//if ("MOB SUPPORT TEAM".equalsIgnoreCase(category)) { //BomWebPortalCcsConstant.USER_CATEGORY_SALESMANAGER
				sql.append(" and O.SALES_CD IN (select distinct STAFF_ID from BOMWEB_SALES_ASSIGNMENT BSA where CHANNEL_CD in ('MDV', 'SIS', 'DSA', 'MOB')" +
						" AND trunc(O.APP_DATE) BETWEEN BSA.START_DATE AND NVL(BSA.END_DATE, trunc(sysdate))");
				sql.append(") ");
				sql.append("and O.ORDER_ID like 'D'||'%'");
			//}
		}else {
			if (!"ALL".equalsIgnoreCase(channel)) {
				sql.append(" and O.SALES_CD IN (select distinct STAFF_ID from BOMWEB_SALES_ASSIGNMENT where CHANNEL_CD = :channel) ");
				params.addValue("channel", channel);
				sql.append("and O.ORDER_ID like 'D'||'%'");
			}
		}
		
		sql.append(" order by O.SRV_REQ_DATE ASC, O.APP_DATE ASC");
		
		ParameterizedRowMapper<OrderDTO> mapper = new ParameterizedRowMapper<OrderDTO>() {

			public OrderDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				
				OrderDTO dto = new OrderDTO();
				
				dto.setOrderId(rs.getString("order_id"));
				dto.setOcid(rs.getString("ocid"));
				dto.setErrorMessage(rs.getString("err_msg"));
				dto.setOrderSumCustName(rs.getString("cust_full_name"));
				dto.setMsisdn(rs.getString("service_num"));
				dto.setAppInDate(rs.getDate("app_date"));
				//dto.setDeliveryDate(rs.getDate("delivery_date"));
				dto.setSrvReqDate(rs.getDate("srv_req_date"));
				dto.setOrderStatus(rs.getString("order_status"));
				dto.setCheckPoint(rs.getString("check_point"));
				dto.setReasonCd(rs.getString("reason_Cd"));
				dto.setSalesCd(rs.getString("sales_cd"));
				dto.setBasketId(rs.getString("basket_id"));
				dto.setBasketType(rs.getString("basket_type"));
				dto.setBusTxnType(rs.getString("order_type"));
				//dto.setAllowCancelInd(rs.getString("ALLOW_CANCEL_IND"));//add by wilson 20120405
				dto.setBomOrderStatus(rs.getString("bom_order_status"));
				dto.setActSrvReqDate(rs.getDate("act_srv_req_date"));
		
				dto.setLastUpdateDate(rs.getTimestamp("LAST_UPDATE_DATE")); //add by wilson 20120529
				dto.setSuperAppInd(rs.getString("SUPER_APP_IND"));
				dto.setOrderAppInd(rs.getString("ORDER_APP_IND"));
				dto.setRejectReason(rs.getString("REJECT_REMARK"));
				dto.setPaymentCheck(rs.getString("PAYMT_CHK_IND"));
				dto.setPaymtRecDate(rs.getTimestamp("PAYMT_REC_DATE"));
				dto.setShopCode(rs.getString("SHOP_CD"));
				dto.setOrderType(rs.getString("ORDER_TYPE"));
				dto.setOrderNature(rs.getString("ORDER_NATURE"));
				
				//dto.setDeliveryTimeSlot(rs.getString("delivery_time_slot"));
				//String orderId=rs.getString("order_id");
				//String orderType=rs.getString("order_type");
				//String orderStatus=rs.getString("order_status"); 
				//String reasonCd=rs.getString("reason_Cd");
				//dto.setForceCancelRemainDays(getforceCancelRemainDays( orderId,  orderType,  orderStatus,  reasonCd));

				return dto;
			}
		};
		
		try {
			logger.debug("MOBDS FinalSQL = " + sql.toString());
			System.out.println(sql.toString());
			return simpleJdbcTemplate.query(sql.toString(), mapper, params);
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException");
		} catch (Exception e) {
			logger.error("Exception caught in findOrderEnquiry()", e);
			throw new DAOException(e.getMessage(), e);
		}
		
		return null;
		
	}
	
	public List<OrderDTO> findOrderReview(String staffId, String channelId, String channelCd, String category) throws DAOException {
		logger.debug("findOrderReview is called");
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		
		StringBuffer sql =  new StringBuffer(
				 "select o.order_id \n"
				+",o.order_status \n"
				+",o.super_app_ind \n"
				+",o.order_app_ind \n"
				+",c.last_name || ' ' || c.first_name cust_full_name \n"
				+",o.msisdn service_num \n"
				+",o.app_date \n"
				+",o.srv_req_date \n"
				+",o.order_status \n"
				+",o.sales_cd \n"
				+",basket.basket_id \n"
				+",mv.basket_type \n"
				+",floor(sysdate - o.last_update_date) last_update_date_diff \n"
				+",o.last_update_date \n"
				+",o.supdoc_chk_ind \n"
				+",o.check_point \n"
				+",case when \n"
				+"upfront.payment = (upfront.vas_upfront + sim_charge + deposit) THEN 'Y' \n"
				+"else 'N' \n"
				+"end as upfrontInd \n"
				+",case when length(O.ORDER_ID)=13 then substr(O.ORDER_ID, 2, length(O.ORDER_ID)-9) else substr(O.ORDER_ID, 2, length(O.ORDER_ID)-8) END as SHOP_CD \n"
				+"from bomweb_order o \n"
				+"join bomweb_customer c on o.order_id = c.order_id \n"
				+"join (select order_id, basket_id \n"
				+"from bomweb_subscribed_item \n"
				+"where order_id like 'D%' \n"
				+"and basket_id is not null \n"
				+"and type = 'RP') basket on o.order_id = basket.order_id  \n"
				+"join w_basket_attribute_mv mv on basket.basket_id = mv.basket_id \n"
				+"join bomweb_order_mob m on m.order_id = o.order_id \n"
				+"join ( \n"
				+"select order_id, \n"
				+"(select sum(onetime_amt) \n"
				+"from bomweb_subscribed_item bsi\n"
				+"join w_item_pricing wip on bsi.id = wip.id\n"
				+"where bsi.order_id = bo.order_id \n"
				+"and bsi.waive_reason is null \n"
				+"and bo.app_date between wip.eff_start_date and nvl(wip.eff_end_date, sysdate)) vas_upfront, \n"
				+"(select nvl(sum(admin_charge),0) \n"
				+"from BOMWEB_ORD_MOB_CHG_SIM_TXN \n"
				+"where order_id like bo.order_id||'%' \n"
				+"and mark_del_ind = 'N' \n"
				+"and admin_charge > 0 \n"
				+"and waive_reason_cd is null) sim_charge, \n"
				+"(select nvl(sum(deposit_amount),0) \n"
				+"from BOMWEB_DEPOSIT \n"
				+"where order_id = bo.order_id \n"
				+"and waive_ind = 'N') deposit, \n"
				+"(select nvl(sum(payment_amount),0) \n"
				+"from BOMWEB_PAYMENT_TRANS \n"
				+"where order_id = bo.order_id) payment \n"
				+"from bomweb_order bo \n"
				+"where bo.order_id like 'D%' \n"
				+"and (order_status in ('REVIEWING', 'QAPROCESS') or (order_status in ('01') and check_point in ('610'))) \n"
				+") upfront on upfront.order_id = o.order_id \n"
				+"where o.order_id like 'D%' \n"
				+"and  (o.order_status in ('REVIEWING', 'QAPROCESS') or (o.order_status in ('01') and o.check_point in ('610'))) \n"
		);
		
		if ("SUPERVISOR".equals(category)) {//SUPERVISOR
			//42
			if ("SIS".equals(channelCd)){
				sql.append(" and O.SALES_CD IN (select distinct STAFF_ID from BOMWEB_SALES_ASSIGNMENT BSA where CHANNEL_ID = :channelId " 
						+ " and trunc(O.APP_DATE) BETWEEN BSA.START_DATE AND NVL(BSA.END_DATE, trunc(sysdate)) ");
				params.addValue("channelId", channelId);	
				sql.append(" and CHANNEL_CD = :channelCd) ");
				params.addValue("channelCd", channelCd);
			} else if ("MDV".equals(channelCd)) {
				sql.append(" and O.SALES_CD IN (select distinct bsa1.staff_id from bomweb_sales_assignment bsa1 "
						+ "inner join bomweb_sales_assignment bsa2 on bsa1.centre_cd = bsa2.centre_cd and bsa1.team_cd = bsa2.team_cd "
						+ "where bsa1.channel_id in (10, 11)  "
						+ "AND trunc(O.APP_DATE) BETWEEN BSA1.START_DATE AND NVL(BSA1.END_DATE, trunc(sysdate)) "
						+ "AND trunc(O.APP_DATE) BETWEEN BSA2.START_DATE AND NVL(BSA2.END_DATE, trunc(sysdate)) "
						+ "AND bsa2.staff_id = :staffId) "
						);
				params.addValue("staffId", staffId);
			}
			//sql.append(")");
		} else if ("ORDER SUPPORT".equals(category)) {//MOB Support Team
			//43 support
			//sql.append(" and O.SUPER_APP_IND = 'Y' ");
		} 
		sql.append(" order by O.create_date desc");	

		ParameterizedRowMapper<OrderDTO> mapper = new ParameterizedRowMapper<OrderDTO>() {
			public OrderDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				
				OrderDTO dto = new OrderDTO();
				
				dto.setOrderId(rs.getString("order_id"));
				dto.setOrderStatus(rs.getString("order_status"));
				dto.setSuperAppInd(rs.getString("super_app_ind") == null ? "N" : rs.getString("super_app_ind"));
				dto.setOrderAppInd(rs.getString("order_app_ind") == null ? "N" : rs.getString("order_app_ind"));
				dto.setOrderSumCustName(rs.getString("cust_full_name"));
				dto.setSupportDocCheck(rs.getString("supdoc_chk_ind") == null ? "N" : rs.getString("supdoc_chk_ind"));
				dto.setMsisdn(rs.getString("service_num"));
				dto.setAppInDate(rs.getDate("app_date"));
				dto.setSrvReqDate(rs.getDate("srv_req_date"));
				dto.setOrderStatus(rs.getString("order_status"));
				dto.setSalesCd(rs.getString("sales_cd"));		
				dto.setBasketId(rs.getString("basket_id"));
				dto.setBasketType(rs.getString("basket_type"));
				dto.setLastUpdateDate(rs.getTimestamp("LAST_UPDATE_DATE")); //add by wilson 20120529
				dto.setCheckPoint(rs.getString("check_point"));
				dto.setUpfrontInd(rs.getString("upfrontInd"));
				dto.setShopCode(rs.getString("SHOP_CD"));
				return dto;
			}
		};
		
		try {
			//System.out.println(sql.toString());
			return simpleJdbcTemplate.query(sql.toString(), mapper, params);
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException");
		} catch (Exception e) {
			logger.error("Exception caught in findOrderEnquiry()", e);
			throw new DAOException(e.getMessage(), e);
		}
		return null;	
	}
	
	
	public String getAllDocAssgn(String orderId) throws DAOException {
		logger.debug("getAllDocAssgn is called");
	
		String sql = "select decode(count(1), 0, 'Y', 'N') collect_ind"
				+ " from bomweb_all_ord_doc_assgn a "
				+ " where a.order_id = :orderId"
				+ " and a.waive_reason is null"
				+ " and NVL(a.collected_ind, 'N') <> 'Y'"
				+ " and nvl(a.mark_del_ind, 'N') = 'N'"
				+ " and a.doc_type in ("
				+ " select code_id from bomweb_code_lkup"
				+ " where code_type = 'DS_COLLECT_SUPPORT_DOC') ";
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("orderId", orderId);
		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
			public String mapRow(ResultSet rs, int row) throws SQLException {
				return rs.getString("collect_ind");
			}
		};
		try {
			List<String> result = simpleJdbcTemplate.query(sql, mapper, params);			
			logger.debug("getSalesChannelId SQL result list: " + result);
			
			if (result == null || result.isEmpty()) {
				return null;
			} else {
				return result.get(0);
			}
			
		} catch (EmptyResultDataAccessException erdae) {
			logger.error("getAllDocAssgn EmptyResultDataAccessException: " + erdae);
		} catch (Exception e) {
			logger.error("getAllDocAssgn Exception: " + e, e);
			throw new DAOException(e.getMessage(), e);
		}
		
		return null;
	}
	
	
	
	public String getSalesChannelId(String orderId) throws DAOException {
		logger.debug("getSalesChannelId is called");
		String sql = "select S.CHANNEL_ID from BOMWEB_SALES_ASSIGNMENT S, BOMWEB_ORDER O "
				+ "where O.SALES_CD = S.STAFF_ID "
				+ "and O.ORDER_ID = :orderId and trunc(O.APP_DATE) between S.START_DATE and NVL(S.END_DATE, sysdate) ";
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("orderId", orderId);
		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
			public String mapRow(ResultSet rs, int row) throws SQLException {
				return rs.getString("CHANNEL_ID");
			}
		};
		try {
			List<String> result = simpleJdbcTemplate.query(sql, mapper, params);			
			logger.debug("getSalesChannelId SQL result list: " + result);
			
			if (result == null || result.isEmpty()) {
				return null;
			} else {
				return result.get(0);
			}
			
		} catch (EmptyResultDataAccessException erdae) {
			logger.error("getSalesChannelId EmptyResultDataAccessException: " + erdae);
		} catch (Exception e) {
			logger.error("getSalesChannelId Exception: " + e, e);
			throw new DAOException(e.getMessage(), e);
		}
		
		return null;
	}
	
	public int approveOrderReview(String orderId, String channelId, String category, String staffId) throws DAOException {
		logger.debug("approveOrderReview is called");
		MapSqlParameterSource params = new MapSqlParameterSource();
		StringBuffer sql = new StringBuffer ("update BOMWEB_ORDER set last_update_date = sysdate"
						+ ", last_update_by = :staffId");
		params.addValue("staffId", staffId);	
		if ("10".equals(channelId) && "SUPERVISOR".equals(category)) {
			sql.append(", SUPER_APP_IND = 'Y' ");
		} else if ("11".equals(channelId) && "ORDER SUPPORT".equals(category)) {
			sql.append(", ORDER_APP_IND = 'Y' ");
		}
		sql.append("where ORDER_ID = :orderId "
				+ "and ORDER_STATUS in ('" + BomWebPortalConstant.BWP_ORDER_REVIEWING + "', '" + BomWebPortalConstant.BWP_ORDER_QAPROCESS + "') ");
		params.addValue("orderId", orderId);
		if ("11".equals(channelId) && "ORDER SUPPORT".equals(category)) {
			sql.append("and PAYMT_CHK_IND = 'Y' ");
		}
		try {				
			return simpleJdbcTemplate.update(sql.toString(), params);
		} catch (Exception e) {
			logger.info("Exception caught in approveOrderReview: ", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public int rejectOrderReview(String orderId, String reason, String category, String staffId) throws DAOException {
		logger.debug("rejectOrderReview is called");
		MapSqlParameterSource params = new MapSqlParameterSource();
		StringBuffer sql = new StringBuffer ("update BOMWEB_ORDER "
						+ "set ORDER_STATUS = '" + BomWebPortalConstant.BWP_ORDER_REJECTED + "'"
						+ ", last_update_date = sysdate"
						+ ", last_update_by = :staffId");
		params.addValue("staffId", staffId);
		if ("SUPERVISOR".equalsIgnoreCase(category)) {
			sql.append(", REJECT_REMARK = :reason");
			params.addValue("reason", reason);	
		}
		sql.append(" where ORDER_ID = :orderId "
				+ "and ORDER_STATUS in ('" + BomWebPortalConstant.BWP_ORDER_REVIEWING + "', '" + BomWebPortalConstant.BWP_ORDER_QAPROCESS + "')");
		params.addValue("orderId", orderId);
		try {				
			return simpleJdbcTemplate.update(sql.toString(), params);
		} catch (Exception e) {
			logger.info("Exception caught in rejectOrderReview: ", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public int updateOrderReviewStatus() {
		logger.debug("updateOrderReviewStatus is called");
		StringBuffer sql = new StringBuffer ("update BOMWEB_ORDER set ORDER_STATUS = 'SIGNOFF' "
				//+ "where ORDER_ID in (select ORDER_ID from BOMWEB_ORDER "
				+ "where SUPER_APP_IND = 'Y' and ORDER_APP_IND = 'Y' "
				+ "and ORDER_STATUS in ('" + BomWebPortalConstant.BWP_ORDER_REVIEWING + "', '" + BomWebPortalConstant.BWP_ORDER_QAPROCESS + "') ");

			MapSqlParameterSource params = new MapSqlParameterSource();				
			return simpleJdbcTemplate.update(sql.toString(), params);
	}
	
	public String getDsOrderReviewCount(String staffId, String channelId, String channelCd, String category) throws DAOException {
		logger.debug("findReviewOrderCount is called");

		MapSqlParameterSource params = new MapSqlParameterSource();
		
		StringBuffer sql =  new StringBuffer("select count(O.ORDER_ID) "
				 + "from BOMWEB_ORDER O, BOMWEB_CUSTOMER C "
				 + "where O.ORDER_ID = C.ORDER_ID "
				 + "and O.ORDER_ID like 'D%' ");
		if ("SUPERVISOR".equals(category)) {//SUPERVISOR
			if("SIS".equals(channelCd)){
				sql.append(" and O.ORDER_STATUS = '" + BomWebPortalConstant.BWP_ORDER_REVIEWING + "' "
						+ " and O.SUPER_APP_IND = 'N' "
						+ " and O.ORDER_APP_IND = 'N' "
						+ " and O.SALES_CD IN (select distinct STAFF_ID from BOMWEB_SALES_ASSIGNMENT BSA where CHANNEL_ID = :channelId " 
						+ " and trunc(O.APP_DATE) BETWEEN BSA.START_DATE AND NVL(BSA.END_DATE, trunc(sysdate)) ");
				params.addValue("channelId", channelId);	
				sql.append(" and CHANNEL_CD = :channelCd) ");
				params.addValue("channelCd", channelCd);
			} else if ("MDV".equals(channelCd)) {
				sql.append(" and O.ORDER_STATUS = '" + BomWebPortalConstant.BWP_ORDER_REVIEWING + "' "
						+ " and O.SUPER_APP_IND = 'N' "
						+ " and O.ORDER_APP_IND = 'N' "
						+ " and O.SALES_CD IN (select distinct bsa1.staff_id from bomweb_sales_assignment bsa1 "
						+ "inner join bomweb_sales_assignment bsa2 on bsa1.centre_cd = bsa2.centre_cd and bsa1.team_cd = bsa2.team_cd "
						+ "where bsa1.channel_id in (10, 11)  "
						+ "AND trunc(O.APP_DATE) BETWEEN BSA1.START_DATE AND NVL(BSA1.END_DATE, trunc(sysdate)) "
						+ "AND trunc(O.APP_DATE) BETWEEN BSA2.START_DATE AND NVL(BSA2.END_DATE, trunc(sysdate)) "
						+ "and bsa2.staff_id = :staffId) ");
				params.addValue("staffId", staffId);
				/*sql.append(" and CENTRE_CD = :areaCd ");
				params.addValue("areaCd", areaCd);
				sql.append(" and TEAM_CD = :shopCd ");
				params.addValue("shopCd", shopCd);*/
			}
			//sql.append(")");
		} else if ("11".equals(channelId) && "ORDER SUPPORT".equals(category)) {//MOB Support Team
			sql.append(" and O.ORDER_STATUS = '" + BomWebPortalConstant.BWP_ORDER_REVIEWING + "' "
					+ " and O.SUPER_APP_IND = 'Y' "
					+ " and O.ORDER_APP_IND = 'N' ");
		} 
		sql.append(" order by O.create_date desc");		
		
		try {
			return (String) simpleJdbcTemplate.queryForObject(
					sql.toString(), String.class, params);
		
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException");
		} catch (Exception e) {
			logger.error("Exception caught in getOrderReviewCount()", e);
			throw new DAOException(e.getMessage(), e);
		}
		
		return "-1";
	}
	public String getforceCancelRemainDays(String orderId, String orderType, String orderStatus, String reasonCd) {
		
		
		String result = "";
		if (orderStatus != null && "03".equals(orderStatus)) {
			return "Cancelling : N/A";
		}

		if (orderStatus != null && "04".equals(orderStatus)) {
			return "Cancelled : N/A";
		}
		

		String SQL_DRAF_PRE_PEND =
			"select DECODE(O.ORDER_TYPE,\n" +
			"              'DRAF',\n" + 
			"              'DRAF Order:' ||\n" + 
			"              (FLOOR((O.LAST_UPDATE_DATE + 7 - sysdate) * 24)) || ' hr ' ||\n" + 
			"              mod((FLOOR(((O.LAST_UPDATE_DATE + 7) - sysdate) * 24 * 60)),\n" + 
			"                  60) || ' min ',\n" + 
			"              'PRE',\n" + 
			"              'PRE Order :' ||\n" + 
			"              (FLOOR((O.app_date + DECODE(O.SHOP_CD, 'OBS', 3, 2) -\n" + 
			"                     sysdate) * 24)) || ' hr ' ||\n" + 
			"              mod(FLOOR((((O.APP_DATE + DECODE(O.SHOP_CD, 'OBS', 3, 2)) -\n" + 
			"                        sysdate)) * 24 * 60),\n" + 
			"                  60) || ' min ',\n" + 
			"              'PEND')\n" + 
			"  from BOMWEB_ORDER O\n" + 
			" where O.ORDER_ID = ?";



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

		
		String SQLgetForceCancelInd="select DECODE(count(1), 0, 'N', 'Y') FORCE_CANCEL_IND\n" +
			"  from BOMWEB_CODE_LKUP CL\n" + 
			" where CL.CODE_TYPE = 'FORCE_CANCEL_FO_CODE'\n" + 
			"   and CL.CODE_ID = ?";


		if (orderType != null && ("PEND".equals(orderType) && "99".equals(orderStatus))) {
			
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
						result = "PEND Order Hottest Model :" + resulePend ;
					} else {
						result = "PEND Order Normal Model :" + resulePend ;
					}
				} else {
	
					result = "PEND Order : N/A";
				}
				return result;
			}
		}
		if (orderType != null && "PEND".equals(orderType)){
			result = "PEND Order : N/A";
		}

		return "PEND Order : N/A";
	}
	
	public MobDsCancelOrderUI getMobDsCancelOrderUI (String orderId) throws DAOException {
		logger.debug("getMobDsCancelOrderUI is called");
		
		String sql = "select bsa.item_code, bsa.item_serial, bsc.item_type, bsc.item_desc, bsi.status_id, bcl.code_desc, bsa.ao_ind "
				+ "from bomweb_stock_assgn bsa "
				+ "left join bomweb_stock_inventory bsi on (bsa.item_code = bsi.item_code and bsa.item_serial = bsi.item_serial) "
				+ "left join bomweb_stock_catalog bsc on bsa.item_code = bsc.item_code "
				+ "left join bomweb_code_lkup bcl on (bsi.status_id = bcl.code_id and bcl.code_type = 'STOCK_STS') "
				+ "where bsa.order_id=?";
		ParameterizedRowMapper<MobDsStockItemDTO> mapper = new ParameterizedRowMapper<MobDsStockItemDTO>() {
		    public MobDsStockItemDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		    	MobDsStockItemDTO dto = new MobDsStockItemDTO();
		    	dto.setItemCode(rs.getString("item_code"));
		    	dto.setItemDesc(rs.getString("item_desc"));
		    	dto.setItemSerial(rs.getString("item_serial"));
		    	dto.setItemType(rs.getString("item_type"));
		    	dto.setStatusDesc(rs.getString("code_desc"));
		    	dto.setOldStatusId(rs.getString("status_id"));
		    	dto.setStatusId(rs.getString("status_id"));
		    	dto.setAoInd(rs.getString("ao_ind"));
		    	return dto;
		    }
		};
		
		String sqlui = "select bo.order_id, bo.ocid, bo.order_status, bo.mnp_ind, bo.msisdn, bmi.msisdn_status, bcl.code_desc, "
				+ " bo.clone_order_id, (select order_status from bomweb_order where order_id = bo.clone_order_id) clone_order_status "
				+ " from bomweb_order bo "
				+ " left join bomweb_mrt_inventory bmi on bo.msisdn = bmi.msisdn "
				+ " left join bomweb_code_lkup bcl on (bmi.msisdn_status = bcl.code_id and bcl.code_type = 'MRT_STS') "
				+ " where bo.order_id=?";
		ParameterizedRowMapper<MobDsCancelOrderUI> mapperui = new ParameterizedRowMapper<MobDsCancelOrderUI>() {
		    public MobDsCancelOrderUI mapRow(ResultSet rs, int rowNum) throws SQLException {
		    	MobDsCancelOrderUI ui = new MobDsCancelOrderUI();
		    	ui.setOrderId(rs.getString("order_id"));
		    	ui.setOcid(rs.getString("ocid"));
		    	ui.setOrderStatus(rs.getString("order_status"));
		    	ui.setMnpInd(rs.getString("mnp_ind"));
		    	ui.setMsisdn(rs.getString("msisdn"));
		    	ui.setMsisdnStatus(rs.getInt("msisdn_status"));
		    	ui.setMsisdnStatusDesc(rs.getString("code_desc"));
		    	ui.setCloneOrderId(rs.getString("clone_order_id"));
		    	ui.setCloneOrderStatus(rs.getString("clone_order_status"));
		    	return ui;
		    }
		};
				
		try {
			List<MobDsCancelOrderUI> uiList = simpleJdbcTemplate.query(sqlui, mapperui, orderId);
			if (uiList.size() > 0) {
				MobDsCancelOrderUI ui = uiList.get(0);
				ui.setStockItemList(simpleJdbcTemplate.query(sql, mapper, orderId));
				return ui;
			} else {
				System.out.println("No record return for " + orderId);
				return null;
			}
		} catch (DataAccessException e) {
			logger.error("Exception caught in getMobDsCancelOrderUI()", e);
			throw new DAOException(e.getMessage(), e);
		}
		
	}
	
	public String getDsOrderStatus(String orderId)
			throws DAOException {
		logger.debug("getDsOrderStatus is called");
		logger.info("orderID : " + orderId);
		
		String sql = "select order_status from bomweb_order where order_id = :orderId ";
		
		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("orderId", orderId);				
			return (String) simpleJdbcTemplate.queryForObject(sql.toString(), String.class, params);
		} catch (Exception e) {
			logger.info("Exception caught in getDsOrderStatus: ", e);
			throw new DAOException(e.getMessage(), e);
		}
		
	}
	
	public void updateDsOrderStatus(String orderId, String orderStatus)
			throws DAOException {
		logger.info("updateDsOrderStatus is called");
		logger.info("update orderID : " + orderId);
		logger.info("update status : " + orderStatus);

		String SQL = "update bomweb_order set order_status = ? , last_update_date = sysdate where order_id = ? ";

		try {
			simpleJdbcTemplate.update(SQL, orderStatus, orderId);

		} catch (Exception e) {
			logger.error("Exception caught in updateDsOrderStatus()", e);
			throw new DAOException(e.getMessage(), e);
		}

	}
	
	public String getDsPrevOrderStatus(String orderId)
			throws DAOException {
		logger.debug("getDsPrevOrderStatus is called");
		logger.info("orderID : " + orderId);
		
		String sql = "select prev_order_status from bomweb_order where order_id = :orderId ";
		
		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("orderId", orderId);				
			return (String) simpleJdbcTemplate.queryForObject(sql.toString(), String.class, params);
		} catch (Exception e) {
			logger.info("Exception caught in getDsPrevOrderStatus: ", e);
			throw new DAOException(e.getMessage(), e);
		}
		
	}
	
	public void updateDsPrevOrderStatus(String orderId, String orderStatus)
			throws DAOException {
		
		logger.debug("updateDsPrevOrderStatus is called");
		logger.info("update orderID : " + orderId + "; status : " + orderStatus);
		
		String SQL = "update bomweb_order set prev_order_status = ? , last_update_date = sysdate where order_id = ? ";
		
		try {
			simpleJdbcTemplate.update(SQL, orderStatus, orderId);

		} catch (Exception e) {
			logger.error("Exception caught in updateDsPrevOrderStatus()", e);
			throw new DAOException(e.getMessage(), e);
		}
		
	}
	
	public String getCloneOrderSts(String orderId) throws DAOException {
		logger.debug("getCloneOrderSts is called @ MobDsOrderDAO");
		List<String> stsList = new ArrayList<String>();
		String sql = "SELECT order_status " 
				+"FROM bomweb_order " 
				+"WHERE order_id IN " 
				+"  (SELECT clone_order_id FROM bomweb_order WHERE order_id = :orderId )";
		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getString("order_status");
			}
		};
		try {
			stsList = simpleJdbcTemplate.query(sql, mapper, orderId);logger.debug("getCloneOrderSts SQL result list: " + stsList);
		} catch (DataAccessException de) {
			logger.error("Exception caught in getMobDsCancelOrderUI()", de);
			throw new DAOException(de.getMessage(), de);
		} catch (Exception e) {
			logger.error("Exception caught in getCloneOrderSts()", e);
			throw new DAOException(e.getMessage(), e);
		}
		if (stsList != null && !stsList.isEmpty()) {
			return (String) stsList.get(0);
		} else {
			return null;
		}
	}
	
}
