package com.bomwebportal.mob.ccs.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.bomwebportal.exception.DAOException;
import com.bomwebportal.mob.ccs.dto.BomOrderPposDTO;
import com.bomwebportal.mob.dao.bom.BaseDAO;

public class MobCcsBomOrderInfoDAO extends BaseDAO {
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	public void updateBomOrderInfo(BomOrderPposDTO dto) throws DAOException{

		logger.info("updateTemp @ BomOrderPposDAO is called");
		
		String sql = "update bomweb_sb_bom_order_info " +
					 "set " +
					 "sm_total_amt = :smTotalAmt, " +
					 "ppos_payment_status = :pposPaymentStatus, " +
					 "sm_type = :smType, " +
					 "sm_type_desc = :smTypeDesc, " +
					 "last_upd_date = sysdate " +
					 "where ocid = :ocid " +
					 "and sm_num = :smNum ";
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("smNum", dto.getSmNum());
		params.addValue("smTotalAmt", dto.getSmTotalAmt());
		params.addValue("pposPaymentStatus", dto.getPposPaymentStatus());
		params.addValue("smType", dto.getSmType());
		params.addValue("smTypeDesc", dto.getSmTypeDesc());
		params.addValue("ocid", dto.getOcid());
		try {
			simpleJdbcTemplate.update(sql, params);
		} catch (Exception e) {
			logger.error("Exception caught in updateBomOrderInfo()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public void insertBomOrderInfo(BomOrderPposDTO dto) throws DAOException {
		logger.info("insertBomOrderInfo is called");

		String SQL = "insert into bomweb_sb_bom_order_info " +
		"  (order_id, " +
		"   ocid, " + 
		"	sm_num, " +
		"	sm_total_amt, " +
		"   ppos_payment_status," + 
		"   sm_type," + 
		"   bom_status," + 
		"   bom_status_desc," + 
		"   sm_type_desc, " + 
		"   create_date, " + 
		"   last_upd_date )" + 
		"  values (" + 
		"  ?, ?, ?, ?, ?, ?, ?, ?, ?, sysdate, sysdate)";

		try {
			simpleJdbcTemplate.update(SQL, dto.getOrderId(), dto.getOcid(), dto.getSmNum(), dto.getSmTotalAmt(),
					dto.getPposPaymentStatus(), dto.getSmType(), dto.getBomStatus(), dto.getBomStatusDesc(), dto.getSmTypeDesc());

		} catch (Exception e) {
			logger.error("Exception caught in insertBomOrderInfo()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public boolean isBomOrderPposInfoExist(String ocid) throws DAOException {
		logger.info("isBomOrderPposInfoExist is called");
		
		String SQL = "select ocid from bomweb_sb_bom_order_info where ocid = ?";
		
		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {

			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				String brand = new String();
				brand = rs.getString("ocid");
				return brand;
			}
		};
		
		try {
			List<String> result = simpleJdbcTemplate.query(SQL, mapper, ocid);
			
			if (result == null || result.isEmpty()) {
				return false;
			} else {
				return true;
			}
			
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("isBomOrderPposInfoExist() EmptyResultDataAccessException");
		} catch (Exception e) {
			logger.error("Exception caught in isBomOrderPposInfoExist()", e);
			throw new DAOException(e.getMessage(), e);
		}
		return false;
	}

}
