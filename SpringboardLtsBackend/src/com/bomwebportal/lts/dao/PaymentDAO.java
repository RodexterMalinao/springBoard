package com.bomwebportal.lts.dao;

import org.springframework.dao.EmptyResultDataAccessException;

import com.bomwebportal.dao.BaseDAO;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;

public class PaymentDAO extends BaseDAO {

	public String getBankNameByCode(String pBankCd) throws DAOException {
		
		String sql = "SELECT bank_name FROM W_ISSUEBANKLKUP where bank_code = ?";
		
		try {
			return (String)simpleJdbcTemplate.queryForObject(sql, String.class, pBankCd);
		} catch (EmptyResultDataAccessException erdae) {
			throw new AppRuntimeException(erdae);
		} catch (Exception e) {
			logger.error("Exception caught in getBankNameByCode()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public String getBranchNameByCode(String pBankCd, String pBranchCd) throws DAOException {
		
		String sql = "select branch_name from W_AP_BANKBRANCHLKUP where bank_code = ? and branch_code = ?";
		
		try {
			return (String)simpleJdbcTemplate.queryForObject(sql, String.class, pBankCd, pBranchCd);
		} catch (EmptyResultDataAccessException erdae) {
			throw new AppRuntimeException(erdae);
		} catch (Exception e) {
			logger.error("Exception caught in getBranchNameByCode()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
}
