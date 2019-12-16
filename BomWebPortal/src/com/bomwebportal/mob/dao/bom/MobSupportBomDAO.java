package com.bomwebportal.mob.dao.bom;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.bomwebportal.exception.DAOException;

public class MobSupportBomDAO extends BaseDAO {
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	public String getBomSimStatus (String sim) throws DAOException{
		
		logger.info("getBomSimStatus is called");
		
		String sql = "select BOR.RESOURCE_CD, "
				 + "       SO.OC_ID, "
				 + "       SO.SHOP_CODE, "
				 + "       DECODE(SO.STATUS, "
				 + "              '01', "
				 + "              'Pending', "
				 + "              '02', "
				 + "              'Completed', "
				 + "              '03', "
				 + "              'Cancelled', "
				 + "              'Others') \"order_status\" "
				 + "  from B_ORD_RESOURCE BOR, B_SRV_ORD SO "
				 + " where BOR.RESOURCE_CD = :sim "
				 + "   and BOR.IO_IND = 'I' "
				 + "   and NVL(MARK_DEL_IND, 'N') = 'N' "
				 + "   and BOR.OC_ID = SO.OC_ID "
				 + "   and SO.STATUS in ('01', '03') "
				 + "union "
				 + "select  "
				 + "ICCID, null, null, null "
				 + "  from B_SIM_ASSIGN_MOB "
				 + " where ICCID = :sim "
				 + "   and EFF_END_DATE is null ";
	
		logger.debug("getBomSimStatus SQL: " + sql);
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		
		params.addValue("sim", sim);
		
		logger.debug("getBomSimStatus SQL param SIM: " + sim);
		
		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
			public String mapRow(ResultSet rs, int row) throws SQLException {
				return rs.getString("order_status");
			}
		};
		
		try {
			List<String> result = simpleJdbcTemplate.query(sql, mapper, params);
			
			logger.debug("getBomSimStatus SQL result list: " + result);
			
			if (result == null || result.isEmpty()) {
				return "N";
			} else {
				return "Y";
			}
			
		} catch (EmptyResultDataAccessException erdae) {
			logger.error("getBomSimStatus EmptyResultDataAccessException: " + erdae);
		} catch (Exception e) {
			logger.error("getBomSimStatus Exception: ", e);
			throw new DAOException(e.getMessage(), e);
		}
		
		return "Y";
	}
	//updated by Athena 20130821 fix bug of MRT pend in BOM indicator
	public String getBomMrtStatus (String mrt) throws DAOException{
		
		logger.info("getBomMrtStatus is called");
		

		String sql =" select BS.SRV_NUM || ',BOM ACTIVE ORDER BY MOBILE NUM' SRV_NUM " +
		"FROM B_CUSTOMER BC , " +
		"  B_ACCOUNT BA , " +
		"  B_ACCOUNT_SERVICE_ASSGN BASA , " +
		"  B_SERVICE BS " +
		"WHERE BC.SYSTEM_ID     = 'MOB' " +
		"AND BC.CUST_NUM        = BA.CUST_NUM " +
		"AND BA.ACCT_NUM        = BASA.ACCT_NUM " +
		"AND BASA.SERVICE_ID    = BS.SERVICE_ID " +
		"AND BASA.EFF_END_DATE IS NULL " +
		"AND BS.SYSTEM_ID       = 'MOB' " +
		"AND BS.SRV_NUM         = :mrt " +
		"AND BS.EFF_END_DATE   IS NULL " +
		"UNION " +
		"SELECT B.SRV_ID" +
		"|| ',BOM PENDING ORDER BY MOBILE NUM, OCID='" +
		"||A.OC_ID SRV_NUM " +
		"FROM B_SRV_ORD A, " +
		"  B_ORD_SRV B " +
		"WHERE A.OC_ID       = B.OC_ID " +
		"AND A.STATUS        = '01' " +
		"AND A.BUS_TXN_TYPE IN ('ACT', 'CMN', 'TOO1', 'TOO2') " +
		"AND B.SRV_ID        = :mrt " +
		"AND B.SRV_ID_TYPE   = 'S' " +
		"AND ROWNUM          = 1";


	
		logger.debug("getBomMrtStatus SQL: " + sql);
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		
		params.addValue("mrt", mrt);
		
		logger.debug("getBomMrtStatus SQL param MRT: " + mrt);
		
		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
			public String mapRow(ResultSet rs, int row) throws SQLException {
				return rs.getString("SRV_NUM");
			}
		};
		
		try {
			List<String> result = simpleJdbcTemplate.query(sql, mapper, params);
			
			logger.debug("getBomMrtStatus SQL result list: " + result);
			
			if (result == null || result.isEmpty()) {
				return "N";
			} else {
				return "Y";
			}
			
		} catch (EmptyResultDataAccessException erdae) {
			logger.error("getBomMrtStatus EmptyResultDataAccessException: " + erdae, erdae);
		} catch (Exception e) {
			logger.error("getBomMrtStatus Exception: " + e, e);
			throw new DAOException(e.getMessage(), e);
		}
		
		return "Y";
	}
	
	public String getOcid (String orderId, String checkPt, String reqStatus) throws DAOException {
		
		String sql = "select oc_id from b_mob_bulk_ord_ctrl "
					 + "where ref_num = :orderId -- sb order id "
					 + "and check_pt = :checkPt -- check point complete "
					 + "and bulk_req_status = :reqStatus -- status complete ";

		logger.debug("getOcid SQL: " + sql);

		MapSqlParameterSource params = new MapSqlParameterSource();
		
		params.addValue("orderId", orderId);
		params.addValue("checkPt", checkPt);
		params.addValue("reqStatus", reqStatus);
		
		logger.debug("getOcid SQL param ORDERID: " + orderId);
		logger.debug("getOcid SQL param CHECKPT: " + checkPt);
		logger.debug("getOcid SQL param REQSTATUS: " + reqStatus);
		
		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
			public String mapRow(ResultSet rs, int row) throws SQLException {
				return rs.getString("oc_id");
			}
		};
		
		try {
			List<String> result = simpleJdbcTemplate.query(sql, mapper, params);
			
			logger.debug("getOcid SQL result list: " + result);
			
			if (result == null || result.isEmpty()) {
				return null;
			} else {
				return result.get(0);
			}
			
		} catch (EmptyResultDataAccessException erdae) {
			logger.error("getOcid EmptyResultDataAccessException: " + erdae);
		} catch (Exception e) {
			logger.error("getOcid Exception: " + e, e);
			throw new DAOException(e.getMessage(), e);
		}
		
		return null;
	}
	
}
