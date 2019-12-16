package com.bomwebportal.dao;

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

import com.bomwebportal.dto.MobSupportDTO;
import com.bomwebportal.exception.DAOException;

public class MobSupportDAO extends BaseDAO {

	protected final Log logger = LogFactory.getLog(getClass());
	
	public List<String[]> getAlertOrders() throws DAOException{
		
		String sql = "(SELECT trim(order_id) order_id, " +
				"  ocid, " +
				"  order_status, " +
				"  check_point, " +
				"  reason_cd, " +
				"  trim(TO_CHAR(create_date,'yyyy-mm-dd hh24:mi:ss')) create_date, " +
				"  trim(TO_CHAR(last_update_date,'yyyy-mm-dd hh24:mi:ss')) last_upd_date, " +
				"  trim(TO_CHAR(SYSDATE,'yyyy-mm-dd hh24:mi:ss')) extract_date, " +
				"  trim(TO_CHAR((sysdate - LAST_UPDATE_DATE)*1440, 9999999)) elapse_min, " +
				"  trim(err_cd) err_cd, " +
				"  trim(err_msg) err_msg " +
				"FROM bomweb_order " +
				"WHERE lob         = 'MOB' " +
				"AND order_type	   not in ('COS','CPM','CSIM') " +
				"AND (((create_date > sysdate-5 OR order_id like 'D%') " +
				"AND order_status          IN ('FAILED', 'PARTIAL', 'PROCESS')) " +
				"OR (check_point           IN ('449', '479', 'C449') " +
				"OR (order_status  = '99' " +
				"AND check_point   = '999' " +
				"AND (reason_cd LIKE 'D%' " +
				"OR reason_cd LIKE 'L%')))) " +
				") " +
				"UNION " +
				"  (SELECT om.member_order_id order_id, " +
				"    om.ocid, " +
				"    DECODE(om.member_status, '400', '01', '500', '02', '800', '03', '899', '04', '79') order_status, " +
				"    om.member_status check_point, " +
				"    NULL reason_cd, " +
				"    trim(TO_CHAR(om.create_date,'yyyy-mm-dd hh24:mi:ss')) create_date, " +
				"    trim(TO_CHAR(om.last_upd_date,'yyyy-mm-dd hh24:mi:ss')) last_upd_date, " +
				"    trim(TO_CHAR(sysdate,'yyyy-mm-dd hh24:mi:ss')) extract_date, " +
				"    trim(TO_CHAR((sysdate - om.last_upd_date)*1440, 9999999)) elapse_min, " +
				"    om.err_cd, " +
				"    om.err_msg " +
				"  FROM bomweb_ord_mob_member om, " +
				"    bomweb_order o " +
				"  WHERE om.parent_order_id = o.order_id " +
				"  AND o.lob                = 'MOB' " +
				"  AND o.order_type	   not in ('COS','CPM','CSIM') " +
				"  AND ((o.create_date      > sysdate-5 " +
				"  AND o.order_status       = 'PARTIAL') " +
				"  OR o.check_point         = '479') " +
				"  AND om.member_status    IN ('449', '999') " +
				"  ) " +
				"ORDER BY create_date DESC";
		
		logger.info(" getAlertOrders is called");
				
		ParameterizedRowMapper<String[]> mapper = new ParameterizedRowMapper<String[]>() {

			public String[] mapRow(ResultSet rs, int row) throws SQLException {
				String[] results = new String[2];
				results[0] = rs.getString("order_id");
				results[1] = rs.getString("elapse_min");
				return results;
			}
		};
		
		try {
			logger.debug("getAlertOrders SQL: " + sql);
			List<String[]> resultList = simpleJdbcTemplate.query(sql, mapper);
			logger.debug("getAlertOrders SQL result: " + resultList);
			return resultList;

		} catch (EmptyResultDataAccessException erdae) {
			logger.error("getAlertOrders EmptyResultDataAccessException: " + erdae);
		} catch (Exception e) {
			logger.error("getAlertOrders Exception: " + e, e);
			throw new DAOException(e.getMessage(), e);
		}
		
		return null;
	}
	
	/**
	 * Extract order basic info from Springboard side<br>
	 * Info includes:<br>
	 * order_id, ocid, status, app date, sim no., mrt, shop_cd, sales_name, contact_phone, email_addr
	 * @return MobSupportDTO
	 * @throws DAOException
	 */
	public MobSupportDTO getSBOrderBasicInfo(String orderId) throws DAOException{
		
		String sql ="(SELECT ORD.ORDER_ID, " +
				"  NVL(ORD.OCID, '') OCID, " +
				"  NVL(ORD.ORDER_STATUS, '') ORDER_STATUS, " +
				"  NVL(ORD.CHECK_POINT, '') CHECK_POINT, " +
				"  NVL(ORD.REASON_CD, '') REASON_CD, " +
				"  NVL(TO_CHAR(ORD.APP_DATE, 'DD/MM/YYYY'), '') APP_DATE_STR, " +
				"  ORD.APP_DATE, " +
				"  NVL(ORD.ERR_MSG, '') ERR_MSG, " +
				"  NVL(SIM.ICCID, '') ICCID, " +
				"  NVL(ORD.MNP_IND, '') MNP_IND, " +
				"  NVL(ORD.MSISDN, '') MSISDN, " +
				"  DECODE(SUBSTR(ord.order_id, 1, 1), 'C', shop.bom_shop_cd, NVL(ORD.SHOP_CD, '')) SHOP_CD, " +
				"  NVL(ORD.SALES_NAME, '') SALES_NAME, " +
				"  NVL(ORD.CLONE_ORDER_ID, '') CLONE_ORDER_ID, " +
				"  NVL(MRT.RESERVE_ID, '') RESERVE_ID, " +
				"  NVL(DECODE(SUBSTR(:ORDER_ID, 1, 1), 'D', ORD.SALES_CONTACT_NUM, SHOP.CONTACT_PHONE), '') CONTACT_PHONE, " +
				"  NVL(SHOP.EMAIL_ADDR, '') EMAIL_ADDR, " +
				"  NVL(SHOP.CHANNEL_ID, DECODE(SUBSTR(:ORDER_ID, 1, 1), 'D', '10', 'R', '1', 'C', '2', 'P', '3', '')) CHANNEL_ID " +
				"FROM " +
				"  (SELECT * FROM BOMWEB_ORDER WHERE ORDER_ID = :ORDER_ID " +
				"  ) ORD " +
				"INNER JOIN " +
				"  (SELECT * FROM BOMWEB_SIM WHERE ORDER_ID = :ORDER_ID " +
				"  ) SIM " +
				"ON ORD.ORDER_ID = SIM.ORDER_ID " +
				"LEFT JOIN " +
				"  (SELECT * FROM BOMWEB_MRT WHERE ORDER_ID = :ORDER_ID " +
				"  ) MRT " +
				"ON ORD.ORDER_ID = MRT.ORDER_ID " +
				"LEFT JOIN bomweb_shop shop " +
				"ON DECODE(ORD.lob, 'MOB', SUBSTR(ORD.order_id, 2, INSTR(ORD.order_id, 'M', -1)-2), ORD.shop_cd) = SHOP.shop_cd " +
				") " +
				"UNION " +
				"  (SELECT om.member_order_id order_id, " +
				"    om.ocid, " +
				"    DECODE(om.member_status, '400', '01', '500', '02', '800', '03', '899', '04', '79') order_status, " +
				"    om.member_status check_point, " +
				"    NULL reason_cd, " +
				"    NVL(TO_CHAR(o.app_date, 'DD/MM/YYYY'), '') app_date_str, " +
				"    o.app_date, " +
				"    om.err_msg, " +
				"    om.iccid, " +
				"    om.mnp_ind, " +
				"    om.msisdn, " +
				"    DECODE(SUBSTR(o.order_id, 1, 1), 'C', s.bom_shop_cd, NVL(O.SHOP_CD, '')) SHOP_CD, " +
				"    NVL(O.SALES_NAME, '') SALES_NAME, " +
				"    NVL(O.CLONE_ORDER_ID, '') CLONE_ORDER_ID, " +
				"    NULL RESERVE_ID, " +
				"    NVL(DECODE(SUBSTR(:ORDER_ID, 1, 1), 'D', O.SALES_CONTACT_NUM, S.CONTACT_PHONE), '') CONTACT_PHONE, " +
				"    NVL(s.email_addr, '') email_addr, " +
				"    NVL(s.channel_id, DECODE(SUBSTR(:ORDER_ID, 1, 1), 'D', '10', 'R', '1', 'C', '2', 'P', '3', '')) channel_id " +
				"  FROM bomweb_ord_mob_member om, " +
				"    bomweb_order o, " +
				"    bomweb_shop s " +
				"  WHERE om.parent_order_id                                   = o.order_id " +
				"  AND SUBSTR(o.order_id, 2 , instr(o.order_id, 'M', -1) - 2) = s.shop_cd(+) " +
				"  AND om.member_order_id                                     = :ORDER_ID " +
				"  AND o.lob                                                  = 'MOB' " +
				"  AND ((o.create_date                                        > sysdate-5 " +
				"  AND o.order_status                                         = 'PARTIAL') " +
				"  OR o.check_point                                           = '479') " +
				"  )";
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("ORDER_ID", orderId);
		
		logger.info("getSBOrderBasicInfo is called");
				
		ParameterizedRowMapper<MobSupportDTO> mapper = new ParameterizedRowMapper<MobSupportDTO>() {

			public MobSupportDTO mapRow(ResultSet rs, int row) throws SQLException {
				MobSupportDTO tempResult = new MobSupportDTO();
				tempResult.setOrderId(rs.getString("ORDER_ID"));
				tempResult.setoOcid(rs.getString("OCID"));
				tempResult.setoOrderStatus(rs.getString("ORDER_STATUS"));
				tempResult.setoCheckPoint(rs.getString("CHECK_POINT"));
				tempResult.setoReasonCd(rs.getString("REASON_CD"));
				tempResult.setoAppDate(rs.getString("APP_DATE_STR"));
				tempResult.setnAppDate(rs.getDate("APP_DATE"));
				tempResult.setErrMsg(rs.getString("ERR_MSG"));
				tempResult.setSim(rs.getString("ICCID"));
				tempResult.setMrt(rs.getString("MSISDN"));
				tempResult.setShopCode(rs.getString("SHOP_CD"));
				tempResult.setSalesName(rs.getString("SALES_NAME"));
				tempResult.setCloneOrderId(rs.getString("CLONE_ORDER_ID"));
				tempResult.setReserveId(rs.getString("RESERVE_ID"));
				tempResult.setShopTel(rs.getString("CONTACT_PHONE"));
				tempResult.setShopEmail(rs.getString("EMAIL_ADDR"));
				tempResult.setMnpInd(rs.getString("MNP_IND"));
				tempResult.setChannelId(rs.getString("CHANNEL_ID"));
				return tempResult;
			}
		};
		
		try {
			logger.debug("getSBOrderBasicInfo SQL: " + sql);
			logger.debug("getSBOrderBasicInfo SQL param ORDERID: " + orderId);
			List<MobSupportDTO> resultList = simpleJdbcTemplate.query(sql, mapper, params);
			logger.debug("getSBOrderBasicInfo SQL result list: " + resultList);
			if (resultList == null || resultList.size() == 0) {
				return new MobSupportDTO();
			} else {
				return resultList.get(0);
			}
		} catch (EmptyResultDataAccessException erdae) {
			logger.error("getSBOrderBasicInfo EmptyResultDataAccessException: " + erdae);
		} catch (Exception e) {
			logger.error("getSBOrderBasicInfo Exception: " + e, e);
			throw new DAOException(e.getMessage(), e);
		}
		
		return null;
	}
	
	public MobSupportDTO getSBMUPOrderBasicInfo(String orderId) throws DAOException{
		
		String sql = "SELECT " +
					"  bomm.member_order_id order_id, " +
					"  NVL(bomm.ocid, '') ocid, " +
					"  NVL(bomm.member_order_type, '') mnp_ind, " +
					"  NVL(bomm.member_status, '') check_point," +
					"  NVL(bomm.err_msg, '') err_msg," +
					"  NVL(bomm.iccid, '') iccid," +
					"  NVL(bomm.msisdn, '') msisdn, " +
					"  NVL(TO_CHAR(bomm.create_date, 'DD/MM/YYYY'), '') APP_DATE_STR, " +
					"  bomm.create_date APP_DATE, " +
					"  DECODE(SUBSTR(bo.order_id, 1, 1), 'C', bs.bom_shop_cd, NVL(bo.SHOP_CD, '')) SHOP_CD " +
					"FROM " +
					"  bomweb_ord_mob_member bomm, " +
					"  bomweb_order bo, " +
					"  bomweb_shop bs " +
					"WHERE " +
					"  bomm.parent_order_id = bo.order_id and " +
					"  bomm.member_order_id = :ORDER_ID";
				
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("ORDER_ID", orderId);
		
		logger.info("getSBMUPOrderBasicInfo is called");
				
		ParameterizedRowMapper<MobSupportDTO> mapper = new ParameterizedRowMapper<MobSupportDTO>() {

			public MobSupportDTO mapRow(ResultSet rs, int row) throws SQLException {
				MobSupportDTO tempResult = new MobSupportDTO();
				tempResult.setOrderId(rs.getString("ORDER_ID"));
				tempResult.setoOcid(rs.getString("OCID"));
				tempResult.setMnpInd(rs.getString("MNP_IND"));
				tempResult.setoOrderStatus(rs.getString("CHECK_POINT"));
				tempResult.setErrMsg(rs.getString("ERR_MSG"));
				tempResult.setSim(rs.getString("ICCID"));
				tempResult.setMrt(rs.getString("MSISDN"));
				tempResult.setoAppDate(rs.getString("APP_DATE_STR"));
				tempResult.setnAppDate(rs.getDate("APP_DATE"));
				tempResult.setShopCode(rs.getString("SHOP_CD"));
				return tempResult;
			}
		};
		
		try {
			logger.debug("getSBMUPOrderBasicInfo SQL: " + sql);
			logger.debug("getSBMUPOrderBasicInfo SQL param ORDERID: " + orderId);
			List<MobSupportDTO> resultList = simpleJdbcTemplate.query(sql, mapper, params);
			logger.debug("getSBMUPOrderBasicInfo SQL result list: " + resultList);
			if (resultList == null || resultList.size() == 0) {
				return new MobSupportDTO();
			} else {
				return resultList.get(0);
			}
		} catch (EmptyResultDataAccessException erdae) {
			logger.error("getSBMUPOrderBasicInfo EmptyResultDataAccessException: " + erdae);
		} catch (Exception e) {
			logger.error("getSBMUPOrderBasicInfo Exception: " + e, e);
			throw new DAOException(e.getMessage(), e);
		}
		
		return null;
	}
	
	/**
	 * Update order status to SB
	 * @param String orderId
	 * @param String orderStatus
	 * @return no. of rows updated
	 * @throws DAOException
	 */
	public int updateSBorderStatus(String orderId, String newOrderStatus, String oldOrderStatus) throws DAOException{
		
		String sql = "UPDATE BOMWEB_ORDER " 
				+"SET ORDER_STATUS   = :NEW_ORDER_STATUS, " 
				+"  ERR_CD           = NULL, " 
				+"  ERR_MSG          = NULL, " 
				+"  CHECK_POINT      = NULL, " 
				+"  LAST_UPDATE_BY   = 'PSBMPATCH', " 
				+"  LAST_UPDATE_DATE = sysdate " 
				+"	WHERE ORDER_ID   = :ORDER_ID " 
				+"	AND ORDER_STATUS = :OLD_ORDER_STATUS";
		
		logger.info("updateSBorderStatus is called");
				
		try {
			logger.info("updateSBorderStatus SQL: " + sql);
			
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("NEW_ORDER_STATUS", newOrderStatus);
			params.addValue("ORDER_ID", orderId);
			params.addValue("OLD_ORDER_STATUS", oldOrderStatus);
			
			logger.info("updateSBorderStatus SQL param NEW_ORDER_STATUS: " + newOrderStatus);
			logger.info("updateSBorderStatus SQL param ORDER_ID: " + orderId);
			logger.info("updateSBorderStatus SQL param OLD_ORDER_STATUS: " + oldOrderStatus);
			
			int result = simpleJdbcTemplate.update(sql, params);
			
			logger.info("updateSBorderStatus SQL result: " + result);
			
			return result;
		} catch (EmptyResultDataAccessException erdae) {
			logger.error("updateSBorderStatus EmptyResultDataAccessException: " + erdae);
		} catch (Exception e) {
			logger.error("updateSBorderStatus Exception: " + e, e);
			throw new DAOException(e.getMessage(), e);
		}
		
		return 0;
	}
	
	public int updateSBCcsOrderStatus(String orderId, String newOrderStatus, String newCheckPoint, String newReasonCd, 
			String oldOrderStatus, String oldCheckPoint) throws DAOException{
		
		String sql = "UPDATE BOMWEB_ORDER " 
				+"SET ORDER_STATUS   = :NEW_ORDER_STATUS, "
				+"  CHECK_POINT      = :NEW_CHECK_POINT, " 
				+"  REASON_CD        = :NEW_REASON_CD, " 
				+"  ERR_CD           = NULL, " 
				+"  ERR_MSG          = NULL, " 
				+"  LAST_UPDATE_BY   = 'PSBMPATCH', " 
				+"  LAST_UPDATE_DATE = sysdate " 
				+"	WHERE ORDER_ID   = :ORDER_ID " 
				+"	AND ORDER_STATUS = :OLD_ORDER_STATUS "
				+"	AND CHECK_POINT  = :OLD_CHECK_POINT ";
		
		logger.info("updateSBCcsOrderStatus is called");
				
		try {
			logger.info("updateSBorderStatus SQL: " + sql);
			
			MapSqlParameterSource params = new MapSqlParameterSource();
			
			params.addValue("NEW_ORDER_STATUS", newOrderStatus);
			params.addValue("NEW_CHECK_POINT", newCheckPoint);
			params.addValue("NEW_REASON_CD", newReasonCd);
			params.addValue("ORDER_ID", orderId);
			params.addValue("OLD_ORDER_STATUS", oldOrderStatus);
			params.addValue("OLD_CHECK_POINT", oldCheckPoint);
			
			int result = simpleJdbcTemplate.update(sql, params);
			
			logger.info("updateSBCcsOrderStatus SQL result: " + result);
			
			return result;
		} catch (EmptyResultDataAccessException erdae) {
			logger.error("updateSBCcsOrderStatus EmptyResultDataAccessException: " + erdae);
		} catch (Exception e) {
			logger.error("updateSBCcsOrderStatus Exception: " + e, e);
			throw new DAOException(e.getMessage(), e);
		}
		
		return 0;
	}
	
	/**
	 * Update order app date to SB, most for "Recall"
	 * @param String orderId
	 * @param Date appDate
	 * @return no. of rows updated
	 * @throws DAOException
	 */
	public int updateSBorderAppDate(String orderId, Date appDate) throws DAOException{
		
		String sql = "UPDATE BOMWEB_ORDER " +
				"SET APP_DATE = :APP_DATE, " +
				"  LAST_UPDATE_BY   = 'PSBMPATCH', " +
				"  LAST_UPDATE_DATE = sysdate " +
				"WHERE ORDER_ID = :ORDER_ID";
		
		logger.info("updateSBorderAppDate is called");
				
		try {
			logger.debug("updateSBorderAppDate SQL: " + sql);
			
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("APP_DATE", appDate);
			params.addValue("ORDER_ID", orderId);
			
			logger.debug("updateSBorderAppDate SQL param APPDATE: " + appDate);
			logger.debug("updateSBorderAppDate SQL param ORDERID: " + orderId);
			
			int result = simpleJdbcTemplate.update(sql, params);
			
			return result;
		} catch (EmptyResultDataAccessException erdae) {
			logger.error("EmptyResultDataAccessException in updateSBorderAppDate()");
		} catch (Exception e) {
			logger.error("Exception caught in updateSBorderAppDate():", e);
			throw new DAOException(e.getMessage(), e);
		}
		
		return 0;
	}
	
public int updateSbErrMsg(String orderId, String errMsg) throws DAOException{
		
		String sql = "UPDATE BOMWEB_ORDER " +
				"SET ERR_MSG = :ERR_MSG, " +
				"  LAST_UPDATE_BY   = 'PSBMPATCH', " +
				"  LAST_UPDATE_DATE = sysdate " +
				"WHERE ORDER_ID = :ORDER_ID";
		
		logger.info("updateSbErrMsg is called");
				
		try {
			logger.debug("updateSbErrMsg SQL: " + sql);
			
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("ERR_MSG", errMsg);
			params.addValue("ORDER_ID", orderId);
			
			logger.debug("updateSbErrMsg SQL param APPDATE: " + errMsg);
			logger.debug("updateSbErrMsg SQL param ORDERID: " + orderId);
			
			int result = simpleJdbcTemplate.update(sql, params);
			
			return result;
		} catch (EmptyResultDataAccessException erdae) {
			logger.error("EmptyResultDataAccessException in updateSbErrMsg()");
		} catch (Exception e) {
			logger.error("Exception caught in updateSbErrMsg():", e);
			throw new DAOException(e.getMessage(), e);
		}
		
		return 0;
	}
	
	public String getSbOcid (String orderId) throws DAOException {
		
		String sql = "select ocid from bomweb_order "
					 + "where order_id = :orderId  ";

		logger.debug("getSbOcid SQL: " + sql);

		MapSqlParameterSource params = new MapSqlParameterSource();
		
		params.addValue("orderId", orderId);
		
		logger.debug("getSbOcid SQL param ORDERID: " + orderId);
		
		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
			public String mapRow(ResultSet rs, int row) throws SQLException {
				return rs.getString("ocid");
			}
		};
		
		try {
			List<String> result = simpleJdbcTemplate.query(sql, mapper, params);
			
			logger.debug("getSbOcid SQL result list: " + result);
			
			if (result == null || result.isEmpty()) {
				return null;
			} else {
				return result.get(0);
			}
			
		} catch (EmptyResultDataAccessException erdae) {
			logger.error("getSbOcid EmptyResultDataAccessException: " + erdae);
		} catch (Exception e) {
			logger.error("getSbOcid Exception: " + e, e);
			throw new DAOException(e.getMessage(), e);
		}
		
		return null;
	}

	public List<String[]> getConflictOrder(String orderId, String simNumber,
			String mrtNumber) throws DAOException {
		logger.info("getConflictOrder is called");
		List<String[]> conflictOrderList = new ArrayList<String[]>();

		String SQL = "select order_id, ocid, order_status from bomweb_order where order_id in "
				+ "(select distinct order_id "
				+ "from "
				+ "	(select order_id from bomweb_mrt "
				+ "	where msisdn=:mrtNumber "
				+ "	and order_id != :orderId) "
				+ "UNION"
				+ "	(select order_id from bomweb_sim "
				+ "	where iccid=:simNumber "
				+ "	and order_id != :orderId) "
				+ ") ";
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("orderId", orderId);
		params.addValue("mrtNumber", mrtNumber);
		params.addValue("simNumber", simNumber);
		
		ParameterizedRowMapper<String[]> mapper = new ParameterizedRowMapper<String[]>() {

			public String[] mapRow(ResultSet rs, int rowNum) throws SQLException {
				String[] order = new String[3];
				order[0] = rs.getString("order_id");
				order[1] = rs.getString("order_status");
				order[2] = rs.getString("ocid");
				return order;
			}
		};
		try {
			conflictOrderList = simpleJdbcTemplate.query(SQL, mapper, params);
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException");
			conflictOrderList = null;
		} catch (Exception e) {
			logger.error("Exception caught in getConflictOrder()", e);
			throw new DAOException(e.getMessage(), e);
		}
		return conflictOrderList;
	}
	public int updateCcsReasonCd(String orderId) throws DAOException{
		String sql = "update bomweb_order "
				+ "set reason_cd = 'C006' "
				+ "where order_id = :ORDER_ID";
		logger.debug("updateCcsReasonCd SQL: " + sql);
		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("ORDER_ID", orderId);
			logger.info("updateCcsReasonCd SQL param ORDER_ID: " + orderId);
			
			int result = simpleJdbcTemplate.update(sql, params);
			logger.info("updateCcsReasonCd SQL result: " + result);
			
			return result;
		} catch (EmptyResultDataAccessException erdae) {
			logger.error("updateCcsReasonCd EmptyResultDataAccessException: " + erdae);
		} catch (Exception e) {
			logger.error("updateCcsReasonCd Exception: " + e, e);
			throw new DAOException(e.getMessage(), e);
		}
		
		return 0;
	}
	
	public int updateMemberOrderStatus(String orderId, String newCheckPoint, String oldCheckPoint) throws DAOException{
		
		String sql = "UPDATE BOMWEB_ORD_MOB_MEMBER " +
				"SET MEMBER_STATUS     = :NEW_CHECK_POINT, " +
				"  ERR_CD              = NULL, " +
				"  ERR_MSG             = NULL, " +
				"  LAST_UPD_BY         = 'PSBMPATCH', " +
				"  LAST_UPD_DATE       = sysdate " +
				"WHERE MEMBER_ORDER_ID = :ORDER_ID " +
				"AND MEMBER_STATUS     = :OLD_CHECK_POINT";
		
		logger.info("updateMemberOrderStatus is called");
				
		try {
			logger.info("updateMemberOrderStatus SQL: " + sql);
			
			MapSqlParameterSource params = new MapSqlParameterSource();
			
			params.addValue("NEW_CHECK_POINT", newCheckPoint);
			params.addValue("ORDER_ID", orderId);
			params.addValue("OLD_CHECK_POINT", oldCheckPoint);
			
			int result = simpleJdbcTemplate.update(sql, params);
			logger.info("updateMemberOrderStatus SQL result: " + result);
			
			return result;
		} catch (EmptyResultDataAccessException erdae) {
			logger.error("updateMemberOrderStatus EmptyResultDataAccessException: " + erdae);
		} catch (Exception e) {
			logger.error("updateMemberOrderStatus Exception: " + e, e);
			throw new DAOException(e.getMessage(), e);
		}
		return 0;
	}
}
