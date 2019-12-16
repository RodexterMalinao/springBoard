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

public class MobCcsBomOrderDAO extends BaseDAO {
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	public List<String> getCrossBomAndMobOrderStatus(String mobOrderSts, String bomOrderSts) throws DAOException {
		logger.info("getCrossBomAndMobOrderStatus is called");
		
		String SQL = "select distinct(bo.order_id) from bomweb_order bo, bomweb_sb_bom_order bsbo "
					 + "where bo.order_id = bsbo.order_id "
					 + "and bo.ocid = bsbo.ocid "
					 + "and bo.order_status = ? "
					 + "and bsbo.bom_order_status = ? ";
		
		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {

			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				String orderId = new String();
				orderId = rs.getString("order_Id");
				return orderId;
			}
		};
		
		try {
			return simpleJdbcTemplate.query(SQL, mapper, mobOrderSts, bomOrderSts);

		} catch (Exception e) {
			logger.error("Exception caught in getCrossBomAndMobOrderStatus()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public List<BomOrderPposDTO> getBomOrderPposList(List<String> ocidList) throws DAOException{
		logger.info("getBomOrderPposList is called");
		
		if (ocidList == null || ocidList.isEmpty()) {
			return null;
		}
		
		StringBuffer SQL = new StringBuffer();
		
		SQL.append("select bom.order_id, bom.BOM_ORDER_STATUS, code.CODE_DESC from bomweb_sb_bom_order bom, bomweb_code_lkup code  "
				 + "where bom.bom_order_status = code.code_id "
				 + "and code.code_type = 'BOM_MOB_ORD_STATUS' ");
		
		StringBuffer inOrderId = new StringBuffer(" and bom.ocid in (");
		for (int i = 0; i < ocidList.size(); i++) {
			
			if (ocidList.size() == 1 || ocidList.size() == i + 1) {
				inOrderId.append("'" + ocidList.get(i) + "'");
			} else {
				inOrderId.append("'" + ocidList.get(i) + "',");
			}
			
		}
		inOrderId.append(")");
		
		SQL.append(inOrderId);
		
		ParameterizedRowMapper<BomOrderPposDTO> mapper = new ParameterizedRowMapper<BomOrderPposDTO>() {
			
			public BomOrderPposDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				
				BomOrderPposDTO dto = new BomOrderPposDTO();
				
				dto.setOrderId(rs.getString("order_id"));
				dto.setBomStatus(rs.getString("bom_order_status"));
				dto.setBomStatusDesc(rs.getString("code_desc"));
				
				return dto;
			}
		};
		
		try {
			return simpleJdbcTemplate.query(SQL.toString(), mapper);

		} catch (Exception e) {
			logger.error("Exception caught in getBomOrderPposList()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public String getEnvironmentCcsMode(String mode) throws DAOException{
		logger.info("getEnvironmentCcsMode is called");
		
		String SQL = "select code_id from bomweb_code_lkup where code_type = ?";
		
		try {
			return (String) simpleJdbcTemplate.queryForObject(SQL, String.class, mode);
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("getEnvironmentCcsMode() EmptyResultDataAccessException");
		} catch (Exception e) {
			logger.error("Exception caught in getEnvironmentCcsMode()", e);
			throw new DAOException(e.getMessage(), e);
		}
		return null;
	}
	
	public void updateBomOrder(BomOrderPposDTO dto) throws DAOException{

		logger.info("updateBomOrder @ MobCcsBomOrderDAO is called");
		
		String sql = "update bomweb_sb_bom_order " +
					 "set " +
					 "bom_order_status = :bomOrderStatus, " +
					 "srv_req_date = :srvReqDate, " +
					 "last_upd_date = sysdate " +
					 "where order_id = :orderId " +
					 "and ocid = :ocid";
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("bomOrderStatus", dto.getBomStatus());
		params.addValue("srvReqDate", dto.getBomServReqDate());
		params.addValue("orderId", dto.getOrderId());
		params.addValue("ocid", dto.getOcid());
		try {
			simpleJdbcTemplate.update(sql, params);
		} catch (Exception e) {
			logger.error("Exception caught in updateBomOrderInfo()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	
	public void insertBomOrder(BomOrderPposDTO dto) throws DAOException {
		logger.info("insertBomOrder is called");

		String SQL = "insert into bomweb_sb_bom_order " +
		"  (order_id, " +
		"   ocid, " + 
		"	bom_order_status, " +
		"   srv_req_date, " +
		"	create_date, " +
		"	last_upd_date) " + 
		"  values (" + 
		"  ?, ?, ?, ?, sysdate, sysdate)";

		try {
			simpleJdbcTemplate.update(SQL, dto.getOrderId(), dto.getOcid(), dto.getBomStatus(), dto.getBomServReqDate());

		} catch (Exception e) {
			logger.error("Exception caught in insertBomOrder()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public boolean isBomOrderPposExist(String orderId) throws DAOException {
		logger.info("isBomOrderPposExist is called");
		
		String SQL = "select ocid from bomweb_sb_bom_order where order_id = ?";
		
		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {

			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				String brand = new String();
				brand = rs.getString("ocid");
				return brand;
			}
		};
		
		try {
			List<String> result = simpleJdbcTemplate.query(SQL, mapper, orderId);
			
			if (result == null || result.isEmpty()) {
				return false;
			} else {
				return true;
			}
			
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("isBomOrderPposExist() EmptyResultDataAccessException");
		} catch (Exception e) {
			logger.error("Exception caught in isBomOrderPposExist()", e);
			throw new DAOException(e.getMessage(), e);
		}
		return false;
	}
	
}
