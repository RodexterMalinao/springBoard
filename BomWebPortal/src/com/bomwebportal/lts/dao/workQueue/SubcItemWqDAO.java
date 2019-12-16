package com.bomwebportal.lts.dao.workQueue;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.bomwebportal.dao.BaseDAO;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.lts.dto.workQueue.SubcItemWqDTO;

public class SubcItemWqDAO extends BaseDAO {
	
	public List<SubcItemWqDTO> getSubcItemWorkQueue() throws DAOException {
		
		StringBuilder sql= new StringBuilder();
		sql.append("SELECT * ")
			.append(" FROM w_subc_item_wq_lkup");	   
		
		try {
			return simpleJdbcTemplate.query(sql.toString(), mapper);
		} catch (EmptyResultDataAccessException erdae) {
			return null;
		} catch (Exception e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
			throw new DAOException(e);
		}
	
	}
	
	public List<SubcItemWqDTO> getSubcItemWorkQueue(String itemId, String ioInd) throws DAOException {
		
			StringBuilder sql= new StringBuilder();
			sql.append("SELECT * ")
				.append(" FROM w_subc_item_wq_lkup")	   
				.append(" WHERE item_id = :itemId")
				.append(" AND io_ind = :ioInd");
			
			MapSqlParameterSource paramSource = new MapSqlParameterSource();
			paramSource.addValue("itemId", itemId);
			paramSource.addValue("ioInd", ioInd);
			
		try {
			return simpleJdbcTemplate.query(sql.toString(), mapper, paramSource);
		} catch (EmptyResultDataAccessException erdae) {
			return null;
		} catch (Exception e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
			throw new DAOException(e);
		}
		
	}
	
	ParameterizedRowMapper<SubcItemWqDTO> mapper = new ParameterizedRowMapper<SubcItemWqDTO>() {
		public SubcItemWqDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
			SubcItemWqDTO subcItemWq = new SubcItemWqDTO();
			subcItemWq.setItemId(rs.getString("item_id"));
			subcItemWq.setIoInd(rs.getString("io_ind"));
			subcItemWq.setOrderType(rs.getString("order_type"));
			subcItemWq.setActionType(rs.getString("action_type"));
			subcItemWq.setWqNatureId(rs.getString("wq_nature_id"));
			subcItemWq.setWqSubType(rs.getString("wq_subtype"));
			subcItemWq.setWqType(rs.getString("wq_type"));
			subcItemWq.setWqRemarks(rs.getString("wq_remarks"));
			subcItemWq.setDesc(rs.getString("description"));
            return subcItemWq;                
		}};
}
