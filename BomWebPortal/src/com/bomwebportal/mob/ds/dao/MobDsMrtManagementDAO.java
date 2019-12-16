package com.bomwebportal.mob.ds.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.bomwebportal.dao.BaseDAO;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.mob.ds.dto.MobDsMrtManagementDTO;

public class MobDsMrtManagementDAO extends BaseDAO{

	public int updateMrtInventory(MobDsMrtManagementDTO dto, String updateStaffId) throws DAOException {
		String sql =  " update bomweb_mrt_inventory set staff_id = :staffId, msisdn_status = :msisdnStatus, " +
					  " team_cd = :teamCd, centre_cd = :centreCd, last_upd_by = :updateStaffId, last_upd_date = sysdate " + 
				 	  " where msisdn in (:requestNum) "
				 	  + "and channel_cd in (select distinct sales_channel from bomweb_shop where channel_id in (10, 11)) ";
	
		logger.debug("updateMrtInventory SQL: " + sql);
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("staffId", dto.getStaffId());
		params.addValue("requestNum", dto.getSelectMsisdn());
		params.addValue("msisdnStatus", dto.getMsisdnStatus());
		params.addValue("teamCd", dto.getTeamCode());
		params.addValue("centreCd", dto.getStoreCode());
		params.addValue("updateStaffId",updateStaffId);
		
		try {
			return simpleJdbcTemplate.update(sql, params);
		}  catch (Exception e) {
			System.out.println(e.getMessage());
			logger.error("updateMrtInventory Exception: " + e, e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
public String getStoreChannelCd (String storeCd) throws DAOException {
		
		String sql = "select distinct sales_channel from bomweb_shop "
					 + "where centre_cd = :storeCd ";
		
		logger.debug("getStoreChannelCd SQL: " + sql);
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("storeCd", storeCd);
		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
			public String mapRow(ResultSet rs, int row) throws SQLException {
				return rs.getString("sales_channel");
			}
		};
		try {
			List<String> result = simpleJdbcTemplate.query(sql, mapper, params);
			
			logger.debug("getStoreChannelCd SQL result list: " + result);
			
			if (result == null || result.isEmpty()) {
				return null;
			} else {
				return result.get(0);
			}
			
		} catch (EmptyResultDataAccessException erdae) {
			logger.error("getStoreChannelCd EmptyResultDataAccessException: " + erdae);
		} catch (Exception e) {
			logger.error("getStoreChannelCd Exception: " + e, e);
			throw new DAOException(e.getMessage(), e);
		}
		
		return null;
	}
	
	public String getSalesChannelCd (String staffId, Date appDate) throws DAOException {
		
		String sql = "select distinct channel_cd from bomweb_sales_assignment "
					 + "where staff_id = :staffId "
					 + "AND trunc(:appDate) BETWEEN trunc(NVL(START_DATE, trunc(:appDate))) AND trunc(NVL(END_DATE, trunc(:appDate))) "
					 + "order by channel_cd ";
		
		logger.debug("getSalesChannelCd SQL: " + sql);
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("staffId", staffId);
		params.addValue("appDate", appDate);
		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
			public String mapRow(ResultSet rs, int row) throws SQLException {
				return rs.getString("channel_cd");
			}
		};
		try {
			List<String> result = simpleJdbcTemplate.query(sql, mapper, params);
			
			logger.debug("getSalesChannelCd SQL result list: " + result);
			
			if (result == null || result.isEmpty()) {
				return null;
			} else {
				return result.get(0);
			}
			
		} catch (EmptyResultDataAccessException erdae) {
			logger.error("getSalesChannelCd EmptyResultDataAccessException: " + erdae);
		} catch (Exception e) {
			logger.error("getSalesChannelCd Exception: " + e, e);
			throw new DAOException(e.getMessage(), e);
		}
		
		return null;
	}
	
	public String getSalesCentreCd (String staffId, Date appDate) throws DAOException {
		
		String sql = "select distinct centre_cd from bomweb_sales_assignment "
					 + "where staff_id = :staffId "
					 + "AND trunc(:appDate) BETWEEN trunc(NVL(START_DATE, trunc(:appDate))) AND trunc(NVL(END_DATE, trunc(:appDate))) "
					 + "order by centre_cd ";
		
		logger.debug("getSalesCentreCd SQL: " + sql);
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("staffId", staffId);
		params.addValue("appDate", appDate);
		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
			public String mapRow(ResultSet rs, int row) throws SQLException {
				return rs.getString("centre_cd");
			}
		};
		try {
			List<String> result = simpleJdbcTemplate.query(sql, mapper, params);
			
			logger.debug("getSalesCentreCd SQL result list: " + result);
			
			if (result == null || result.isEmpty()) {
				return null;
			} else {
				return result.get(0);
			}
			
		} catch (EmptyResultDataAccessException erdae) {
			logger.error("getSalesCentreCd EmptyResultDataAccessException: " + erdae);
		} catch (Exception e) {
			logger.error("getSalesCentreCd Exception: " + e, e);
			throw new DAOException(e.getMessage(), e);
		}
		
		return null;
	}
	
	public String getSalesTeamCd (String staffId, Date appDate) throws DAOException {
		
		String sql = "select distinct team_cd from bomweb_sales_assignment "
					 + "where staff_id = :staffId "
					 + "AND trunc(:appDate) BETWEEN trunc(NVL(START_DATE, trunc(:appDate))) AND trunc(NVL(END_DATE, trunc(:appDate))) "
					 + "order by team_cd ";
		
		logger.debug("getSalesTeamCd SQL: " + sql);
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("staffId", staffId);
		params.addValue("appDate", appDate);
		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
			public String mapRow(ResultSet rs, int row) throws SQLException {
				return rs.getString("team_cd");
			}
		};
		try {
			List<String> result = simpleJdbcTemplate.query(sql, mapper, params);
			
			logger.debug("getSalesTeamCd SQL result list: " + result);
			
			if (result == null || result.isEmpty()) {
				return null;
			} else {
				return result.get(0);
			}
			
		} catch (EmptyResultDataAccessException erdae) {
			logger.error("getSalesTeamCd EmptyResultDataAccessException: " + erdae);
		} catch (Exception e) {
			logger.error("getSalesTeamCd Exception: " + e, e);
			throw new DAOException(e.getMessage(), e);
		}
		
		return null;
	}
	
	public List<MobDsMrtManagementDTO> getMrtSummaryList(MobDsMrtManagementDTO searchDto, String staffId, String category, String channelCd) throws DAOException {
		
		StringBuffer sql =  new StringBuffer( " select bmi.msisdn, bmi.msisdnlvl, bmi.msisdn_status, bcl.code_desc msisdnStatusDesc, " +
					 						  "   bmi.centre_cd, bmi.team_cd, bmi.staff_id, bmi.reserve_id, bo.order_id, bmi.num_type " +
					 						  " from bomweb_mrt_inventory bmi, " +
					 						  " (select * from bomweb_code_lkup where code_type = 'MRT_STS') bcl," +
					 						  " (select max(order_id) order_id, msisdn from bomweb_order where order_id like 'D%' " +
					 						  " and order_status != 'CANCELLED' " +
					 						  " group by msisdn) bo, "
					 						  + "(select distinct sales_channel from bomweb_shop where channel_id in (10, 11)) bs " +
											  " where bmi.msisdn_status = bcl.code_id " +
											  " and bmi.msisdn = bo.msisdn (+) "
											  + "and bmi.channel_cd = bs.sales_channel ");
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		
		if (searchDto.getSearchMrt() == null || "".equalsIgnoreCase(searchDto.getSearchMrt())) {
			sql.append(" AND bmi.msisdn_status = :status ");
			params.addValue("status", searchDto.getSearchStatus());
		}
		
		// MRT search restrictions
		if ("FRONTLINE".equalsIgnoreCase(category)) {
			// team constraint
			if ("MDV".equals(channelCd)) {
				// MDV FRONTLINE
				sql.append(" and bmi.centre_cd = :centreCd and bmi.team_cd = :teamCd ");
				params.addValue("centreCd", searchDto.getSearchStoreCode());
				params.addValue("teamCd", searchDto.getSearchTeamCode());
			} else if ("SIS".equals(channelCd)) {
				// SIS FRONTLINE is able to pick other team's MRT
				sql.append(" and bmi.channel_cd = :channelCd and bmi.centre_cd = :centreCd ");
				params.addValue("channelCd", channelCd);
				params.addValue("centreCd", searchDto.getSearchStoreCode());
			}
			
			// search assigned or team MRT
			sql.append(" and (bmi.staff_id is null or bmi.staff_id = :staffId) ");
			params.addValue("staffId", searchDto.getStaffId());
			System.out.println(searchDto.getStaffId());
		} else if ("SUPERVISOR".equalsIgnoreCase(category)) {
			// SUPERVISOR's team MRT number 
			// SIS FRONTLINE is able to pick other team's MRT
			// No assignment is necessary for SIS SUPERVISOR
			
			// supervisor can only search in his teams
			if ("ALL".equalsIgnoreCase(searchDto.getSearchStoreCode())) {
				sql.append(" AND bmi.team_cd IN (select distinct bsa.team_cd from bomweb_sales_assignment bsa "
						+ "WHERE bsa.staff_id = :staffId "
						+ "AND trunc(sysdate) BETWEEN bsa.start_date AND trunc(NVL(bsa.end_date, sysdate)) "
						+ "AND bsa.channel_id = 10) ");
				params.addValue("staffId", staffId);
			} else {
				sql.append(" and bmi.centre_cd = :centreCd and bmi.team_cd = nvl(:teamCd, bmi.team_cd) ");
				params.addValue("centreCd", searchDto.getSearchStoreCode());
				params.addValue("teamCd", searchDto.getSearchTeamCode());
			}
			
		} else if ("MOB".equals(channelCd)) {
			if (searchDto.getSearchMrt() == null || "".equalsIgnoreCase(searchDto.getSearchMrt())&&
					(searchDto.getSearchStaffId() == null || "".equalsIgnoreCase(searchDto.getSearchStaffId()))) {
				// search by store and team code
				sql.append(" and bmi.centre_cd = :centreCd and bmi.team_cd = nvl(:teamCd, bmi.team_cd) ");
				params.addValue("centreCd", searchDto.getSearchStoreCode());
				params.addValue("teamCd", searchDto.getSearchTeamCode());
			}
			
		}

		// MRT search criteria
		if (searchDto.getSearchMrt() != null && !"".equalsIgnoreCase(searchDto.getSearchMrt())) {
			// search by mrt
			sql.append(" AND bmi.msisdn = :searchMrt ");
			params.addValue("searchMrt", searchDto.getSearchMrt());
		} else {
			
			if (!"FRONTLINE".equalsIgnoreCase(category) && searchDto.getSearchStaffId() != null && !"".equalsIgnoreCase(searchDto.getSearchStaffId())) {
				// search by staff id
				sql.append(" AND bmi.staff_id = :staffId ");
				params.addValue("staffId", searchDto.getSearchStaffId());
			}
			
			if (searchDto.getSearchMsisdnlvl() != null && searchDto.getSearchMsisdnlvl().size() != 0) {
				if (!"ALL".equalsIgnoreCase(searchDto.getSearchMsisdnlvl().get(0))) {
					sql.append(" AND bmi.msisdnlvl in (:searchMsisdnlvl) ");
					params.addValue("searchMsisdnlvl", searchDto.getSearchMsisdnlvl());
				}
			}
			
			if (StringUtils.isNotBlank(searchDto.getSearchNumType())) {
				sql.append(" AND bmi.num_type = :numType ");
				params.addValue("numType", searchDto.getSearchNumType());
			}
			
		}
		if ("FRONTLINE".equalsIgnoreCase(category)) {
			sql.append(" order by bmi.staff_id nulls last, bmi.centre_cd, bmi.team_cd, bmi.msisdn ");
		} else {
			sql.append(" order by bmi.centre_cd, bmi.team_cd, bmi.msisdn ");
		}
		
		ParameterizedRowMapper<MobDsMrtManagementDTO> mapper = new ParameterizedRowMapper<MobDsMrtManagementDTO>() {

			public MobDsMrtManagementDTO mapRow(ResultSet rs, int row) throws SQLException {
				MobDsMrtManagementDTO result = new MobDsMrtManagementDTO();
				result.setMsisdn(rs.getString("msisdn"));
				result.setMsisdnlvl(rs.getString("msisdnlvl"));
				result.setMsisdnStatus(rs.getString("msisdn_status"));
				result.setMsisdnStatusDesc(rs.getString("msisdnStatusDesc"));
				result.setStoreCode(rs.getString("centre_cd"));
				result.setTeamCode(rs.getString("team_cd"));
				result.setStaffId(rs.getString("staff_id"));
				result.setReserveId(rs.getString("reserve_id"));
				result.setOrderId(rs.getString("order_id"));
				result.setNumType(rs.getString("num_type"));
				return result;
			}
		};
		
		try {
			logger.debug("MobDsMrtManagementDAO getMrtSummaryList() SQL: " + sql.toString());
			List<MobDsMrtManagementDTO> resultList = simpleJdbcTemplate.query(sql.toString(), mapper, params);
			return resultList;

		} catch (EmptyResultDataAccessException erdae) {
			logger.error("getAlertOrders EmptyResultDataAccessException: " + erdae);
		} 
		return null;
	}
	
	public boolean isValidMrtStore(List<String> msisdn, String teamCode) throws DAOException {
		
		String sql = 
				"select count(1) valid_count from bomweb_mrt_inventory bmi " +
				" where bmi.msisdn in (:msisdn) " +
				" and bmi.channel_cd in ( " +
				"   select sales_channel from bomweb_shop bs " +
				"   where bs.channel_id = 10 and bs.shop_cd = :teamCode)";
		
		logger.debug("isValidMrtStore SQL: " + sql);
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("msisdn", msisdn);
		params.addValue("teamCode", teamCode);
		
		ParameterizedRowMapper<Integer> mapper = new ParameterizedRowMapper<Integer>() {
			public Integer mapRow(ResultSet rs, int row) throws SQLException {
				return rs.getInt("valid_count");
			}
		};
		
		try {
			List<Integer> result = simpleJdbcTemplate.query(sql, mapper, params);
			
			logger.debug("isValidMrtStore SQL result list: " + result);
			if (result == null || result.isEmpty()) {
				return false;
			} else {
				return result.get(0) == msisdn.size();
			}
			
		} catch (EmptyResultDataAccessException erdae) {
			logger.error("isValidMrtStore EmptyResultDataAccessException: " + erdae);
			return false;
		} catch (Exception e) {
			logger.error("isValidMrtStore Exception: " + e, e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public boolean allowUpdateMrtStatus(List<String> msisdn) throws DAOException {
		
		String sql = 
				"select count(1) free_count from bomweb_mrt_inventory bmi " +
				" where bmi.msisdn in (:msisdn) " +
				" and bmi.msisdn_status in ('2', '5') " +
				" and bmi.channel_cd in (select distinct sales_channel from bomweb_shop where channel_id = 10)";
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("msisdn", msisdn);
		
		ParameterizedRowMapper<Integer> mapper = new ParameterizedRowMapper<Integer>() {
			public Integer mapRow(ResultSet rs, int row) throws SQLException {
				return rs.getInt("free_count");
			}
		};
		
		try {
			List<Integer> result = simpleJdbcTemplate.query(sql, mapper, params);
			
			if (result == null || result.isEmpty()) {
				return false;
			} else {
				return result.size() == 1;
			}
			
		} catch (Exception e) {
			logger.error("allowUpdateMrtStatus Exception: " + e, e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public List<String> getMdvTeamList () throws DAOException {
		
		String sql = "select distinct shop_cd from bomweb_shop "
					 + "where sales_channel = 'MDV' " +
					 " order by shop_cd ";
		
		logger.debug("getMdvTeamList SQL: " + sql);
		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
			public String mapRow(ResultSet rs, int row) throws SQLException {
				return rs.getString("shop_cd");
			}
		};
		try {
			List<String> result = simpleJdbcTemplate.query(sql, mapper);
			
			logger.debug("getMdvTeamList SQL result list: " + result);
			
			if (result == null || result.isEmpty()) {
				return null;
			} else {
				return result;
			}
			
		} catch (EmptyResultDataAccessException erdae) {
			logger.error("getMdvTeamList EmptyResultDataAccessException: " + erdae);
		} catch (Exception e) {
			logger.error("getMdvTeamList Exception: " + e, e);
			throw new DAOException(e.getMessage(), e);
		}
		
		return null;
	}

	public String getAssignResId (String msisdn, String shopCd) throws DAOException {
		MapSqlParameterSource params = new MapSqlParameterSource();
		StringBuffer sql = new StringBuffer("SELECT nvl(reserve_id, ' ') reserve_id FROM bomweb_mrt_inventory WHERE msisdn = :msisdn ");
		params.addValue("msisdn", msisdn);
		sql.append("AND centre_cd = :shopCd");
		params.addValue("shopCd", shopCd);
			
		logger.debug("getAssignResId SQL: " + sql.toString());
			
		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
			public String mapRow(ResultSet rs, int row) throws SQLException {
				return rs.getString("reserve_id");
			}
		};
		try {
			List<String> result = simpleJdbcTemplate.query(sql.toString(), mapper, params);
			logger.debug("getAssignResId SQL result list: " + result);			
			if (result == null || result.isEmpty()) {
				return null;
			} else {
				return result.get(0);
			}
			
		} catch (EmptyResultDataAccessException erdae) {
			logger.error("getAssignResId EmptyResultDataAccessException: " + erdae);
		} catch (Exception e) {
			logger.error("getAssignResId Exception: " + e, e);
			throw new DAOException(e.getMessage(), e);
		}
		return null;
	}
	
	private static final String deleteDsReuseMrtInventorySQL = 
			  " delete from bomweb_mrt_inventory \n"
			+ " where msisdn in \n"
			+ "   ((select msisdn from bomweb_mrt_inventory \n"
			+ "       where msisdn_status = 2 \n"
			+ "       and channel_cd in ('MDV', 'SIS')) \n"
			+ "    INTERSECT \n"
			+ "      (select msisdn from bomweb_mrt_inventory \n"
			+ "      where msisdn_status = 20)) \n"
			+ " and msisdn_status = 20 \n"
			+ " and msisdn = ? ";

    public int deleteDsReuseMrtInventory(String msisdn) throws DAOException {
		logger.info("deleteDsReuseMrtInventory is called");
	
		try {
		    logger.info("deleteDsReuseMrtInventory() @ MrtInventoryDAO: " + deleteDsReuseMrtInventorySQL);
		    return simpleJdbcTemplate.update(deleteDsReuseMrtInventorySQL, msisdn);
		} catch (Exception e) {
		    logger.error("Exception caught in deleteDsReuseMrtInventory()", e);
		    throw new DAOException(e.getMessage(), e);
		}
    }
}
