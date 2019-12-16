package com.bomwebportal.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.bomwebportal.exception.DAOException;

public class MnpNumDAO extends BaseDAO{

	protected final Log logger = LogFactory.getLog(getClass());

	public List<String[]> getMrtNum(String staffId, String channelCd,
			String areaCd, String shopCd, List<String> grade, String numType) throws DAOException {
		
		logger.debug(" getMrtNum is called");
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		
		StringBuffer sql = new StringBuffer("SELECT DISTINCT msisdn, msisdnlvl "
		    + "FROM bomweb_mrt_inventory "
		    + "WHERE ( staff_id = :staffId ");
		params.addValue("staffId", staffId);
		sql.append("OR ( channel_cd = :channelCd ");
		params.addValue("channelCd", channelCd);
		if ("MDV".equalsIgnoreCase(channelCd)) {
			sql.append("AND centre_cd = :areaCd ");
			params.addValue("areaCd", areaCd);
			sql.append("AND team_cd = :shopCd ");
			params.addValue("shopCd", shopCd);
		} else if ("SIS".equalsIgnoreCase(channelCd)) {
			sql.append("AND centre_cd = :areaCd ");
			params.addValue("areaCd", areaCd);
		}
		sql.append("AND staff_id is NULL)) ");
	    if (CollectionUtils.isNotEmpty(grade)) {
			sql.append("AND msisdnlvl in (:grade) ");
			params.addValue("grade", grade);
		}
	    
	    if (StringUtils.isNotBlank(numType)) {
			sql.append("AND num_type in (:numType) ");
			params.addValue("numType", numType);
		}
		    sql.append("AND msisdn_status = 2 order by dbms_random.value ");
		   
		List<String[]> itemList = new ArrayList<String[]>();
		
		ParameterizedRowMapper<String[]> mapper = new ParameterizedRowMapper<String[]>() {
		    public String[] mapRow(ResultSet rs, int rowNum) throws SQLException {
				String temp[] = new String[2];
				temp[0] = rs.getString("msisdn");
				temp[1] = rs.getString("msisdnlvl");
				return temp;
		    }
		};
		
		try {
		    logger.info("getMrtNum() @ ArrayList<String[]>: " + sql.toString());
		    itemList = simpleJdbcTemplate.query(sql.toString(), mapper, params);
		} catch (EmptyResultDataAccessException erdae) {
		    logger.info("EmptyResultDataAccessException in getMrtNum()");
		    itemList = null;
		} catch (Exception e) {
		    logger.info("Exception caught in getMrtNum():", e);
		    throw new DAOException(e.getMessage(), e);
		}
		return itemList;
	}
	
	public List<String[]> getCCSMrtNum(String channelCd, String grade, String numType) throws DAOException {
		
		logger.debug(" getCCSMrtNum is called");
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		
		StringBuffer sql = new StringBuffer("SELECT msisdn, msisdnlvl, channel_cd, centre_cd, team_cd, num_type "
		    + "FROM bomweb_mrt_inventory "
		    + "WHERE channel_cd = :channelCd "
		    + "AND length(msisdn)=8 ");
		params.addValue("channelCd", channelCd);
		if (grade != null) {
			sql.append(" AND msisdnlvl = :grade ");
			params.addValue("grade", grade);
		}
		if (numType != null) {
			sql.append(" AND num_type = :numType ");
			params.addValue("numType", numType);
		}
		sql.append(" AND msisdn_status = 2 order by dbms_random.value ");
		   
		List<String[]> itemList = new ArrayList<String[]>();
		
		ParameterizedRowMapper<String[]> mapper = new ParameterizedRowMapper<String[]>() {
		    public String[] mapRow(ResultSet rs, int rowNum) throws SQLException {
				String temp[] = new String[6];
				temp[0] = rs.getString("msisdn");
				temp[1] = rs.getString("msisdnlvl");
				temp[2] = rs.getString("channel_cd");
				temp[3] = rs.getString("centre_cd");
				temp[4] = rs.getString("team_cd");
				temp[5] = rs.getString("num_type");
				return temp;
		    }
		};
		
		try {
		    logger.info("getCCSMrtNum() @ ArrayList<String[]>: " + sql.toString());
		    itemList = simpleJdbcTemplate.query(sql.toString(), mapper, params);
		} catch (EmptyResultDataAccessException erdae) {
		    logger.info("EmptyResultDataAccessException in getCCSMrtNum()");
		    itemList = null;
		} catch (Exception e) {
		    logger.info("Exception caught in getCCSMrtNum():", e);
		    throw new DAOException(e.getMessage(), e);
		}
		return itemList;
	}
	
	public String[] getMrtNum(String msisdn, String channelCd, String numType) throws DAOException { 
		logger.debug(" getMrtNum is called");
		List<String[]> itemList = new ArrayList<String[]>();
		
		String getMrtNumSQL = "SELECT msisdn, msisdnlvl, msisdn_status, channel_cd, centre_cd, team_cd, num_type "
			    + "FROM bomweb_mrt_inventory "
			    + "WHERE msisdn = :msisdn ";
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("msisdn", msisdn);
		if (StringUtils.isNotBlank(channelCd)) {
			getMrtNumSQL += " AND nvl(channel_cd, '#') = :channelCd ";
			params.addValue("channelCd", channelCd);
			System.out.println("channelCd parm = " + channelCd);
		}
		
		if (StringUtils.isNotBlank(numType)) {  //Dennis MIP3
			getMrtNumSQL += " AND nvl(num_type, '#') = :numType ";
			params.addValue("numType", numType);
			System.out.println("numType parm = " + numType);
		}
		
		/**** ==ParameterizedRowMapper start== *********************************************************/
		ParameterizedRowMapper<String[]> mapper = new ParameterizedRowMapper<String[]>() {
		    public String[] mapRow(ResultSet rs, int rowNum)
			    throws SQLException {
			String temp[] = new String[7];
			temp[0] = rs.getString("msisdn");
			temp[1] = rs.getString("msisdnlvl");
			temp[2] = rs.getString("channel_cd");
			temp[3] = rs.getString("centre_cd");
			temp[4] = rs.getString("team_cd");
			temp[5] = rs.getString("msisdn_status");
			temp[6] = rs.getString("num_type");
			return temp;
		    }
		};
		/**** ==ParameterizedRowMapper end== *********************************************************/
		try {
		    logger.info("getMrtNum() @ ArrayList<String[]>: "
			    + getMrtNumSQL);
		    itemList = simpleJdbcTemplate.query(getMrtNumSQL, mapper, params);
		} catch (EmptyResultDataAccessException erdae) {
		    logger.info("EmptyResultDataAccessException in getMrtNum()");

		    itemList = null;
		} catch (Exception e) {
		    logger.info("Exception caught in getMrtNum():", e);

		    throw new DAOException(e.getMessage(), e);
		}
		if (itemList != null && itemList.size() > 0) {
			return itemList.get(0);
		} else {
			return null;
		}
	}
	
	public Integer checkIsWhiteList(String msisdn) {
		if (logger.isDebugEnabled()) {
			logger.debug("checkIsWhiteList is called");
		}
		String sql = "SELECT COUNT(1) FROM bomweb_csub_list where MRT = :msisdn";

		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("msisdn", msisdn);
		int count = this.simpleJdbcTemplate.queryForInt(sql, params);
		return count;
	}
	
	public Integer getCSLBillPeriod(String msisdn) {
		if (logger.isDebugEnabled()) {
			logger.debug("checkIsWhiteList is called");
		}
		String sql = "SELECT BILL_PERIOD FROM bomweb_csub_list where MRT = :msisdn";

		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("msisdn", msisdn);
		
		Integer result = this.simpleJdbcTemplate.queryForInt(sql, params);

		return result;
	}
	
	public Integer checkPendingOrder(String msisdn, String orderId) {
		String sql = "select count(*) "
				+ "from bomweb_order where MSISDN = :msisdn "
				+ "and ORDER_STATUS not in ('02', '04', 'SUCCESS', 'CANCELLED', 'VOID') "
				+ "and order_id like 'D%' "
				+ "and order_id <> :orderId";

		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("msisdn", msisdn);
		params.addValue("orderId", orderId);
		
		Integer result = this.simpleJdbcTemplate.queryForInt(sql, params);

		return result;
	}
	
	public Integer checkPendingMUPOrder(String msisdn, String orderId) {
		String sql = "select count(*) "
				+ "from bomweb_ord_mob_member "
				+ "where MSISDN = :msisdn "
				+ "and MEMBER_STATUS not in ('500', '899') "
				+ "and PARENT_ORDER_ID like 'D%' "
				+ "and PARENT_ORDER_ID <> :orderId";

		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("msisdn", msisdn);
		params.addValue("orderId", orderId);
		
		Integer result = this.simpleJdbcTemplate.queryForInt(sql, params);

		return result;
	}
}
		
