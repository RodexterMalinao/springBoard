package com.bomwebportal.lts.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.bomwebportal.dao.BaseDAO;
import com.bomwebportal.dto.LookupItemDTO;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;

public class LtsPaymentBomDAO extends BaseDAO {
	public String getBankNameByCode(String code) throws DAOException {
		String SQL = "SELECT BANK_NAME FROM B_BANK " +
				"WHERE BANK_CODE = ? " +
				"AND BANK_STATUS = 'A'";
		
		String bankName = null;
		try {
			bankName = simpleJdbcTemplate.queryForObject(SQL, String.class, code);
		}catch(Exception e){
			logger.error("Exception caught in getBankNameByCode()", e);
			throw new DAOException(e.getMessage(), e);
		}
		
		return bankName;
	}

	public String getBranchNameByCode(String bankCode, String code) throws DAOException {
		String SQL = "SELECT BRANCH_NAME FROM B_BRANCH " +
				"WHERE BANK_CODE = ? " +
				"AND BRANCH_CODE = ? " +
				"AND BRANCH_STATUS = 'A'";
		
		String branchName = null;
		try {
			branchName = simpleJdbcTemplate.queryForObject(SQL, String.class, bankCode, code);
		}catch(Exception e){
			logger.error("Exception caught in getBranchNameByCode()", e);
			throw new DAOException(e.getMessage(), e);
		}
		
		return branchName;
	}
	
	public List<LookupItemDTO> getBankCode() throws DAOException {
		List<LookupItemDTO> list = new ArrayList<LookupItemDTO>();
		String SQL = "SELECT * FROM B_BANK WHERE bank_status = 'A'";
		ParameterizedRowMapper<LookupItemDTO> mapper = 
				new ParameterizedRowMapper<LookupItemDTO>() {
			public LookupItemDTO mapRow(ResultSet rs, int rowNum)	
					throws SQLException {
				LookupItemDTO bankInfo = new LookupItemDTO();
				bankInfo.setItemKey(rs.getString("bank_code"));
				bankInfo.setItemValue(rs.getString("bank_code")+" "+rs.getString("bank_name"));
				
				return bankInfo;
			}
		};
		
		try {
			list = simpleJdbcTemplate.query(SQL, mapper);
		} catch (EmptyResultDataAccessException erdae) {
			throw new AppRuntimeException(erdae);
		} catch (Exception e) {
			logger.error("Exception caught in getBankCode()", e);
			throw new DAOException(e.getMessage(), e);
		}
		if (list.size() == 0){
			return null;
		}
		return list;
	}
	
	
	public List<LookupItemDTO> getBranchCode(String bankCode) throws DAOException {

		List<LookupItemDTO> list = new ArrayList<LookupItemDTO>();
		String SQL = "SELECT * FROM B_BRANCH" + 
		" WHERE bank_code = '" + bankCode +"' AND branch_status = 'A' " +
		" ORDER BY branch_code ";
		
		ParameterizedRowMapper<LookupItemDTO> mapper = 
				new ParameterizedRowMapper<LookupItemDTO>() {
			public LookupItemDTO mapRow(ResultSet rs, int rowNum)	
					throws SQLException {
				LookupItemDTO branchInfo = new LookupItemDTO();
				branchInfo.setItemKey(rs.getString("branch_code"));
				branchInfo.setItemValue(rs.getString("branch_code")+" " + rs.getString("branch_name"));
				
				return branchInfo;
			}
		};
		
		try {
			list = simpleJdbcTemplate.query(SQL, mapper);
		} catch (EmptyResultDataAccessException erdae) {
			throw new AppRuntimeException(erdae);
		} catch (Exception e) {
			logger.error("Exception caught in getBranchCode(String)", e);
			throw new DAOException(e.getMessage(), e);
		}
		if (list.size() == 0){
			return null;
		}
		return list;
	}
}
