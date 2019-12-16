package com.bomwebportal.lts.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.bomwebportal.dao.BaseDAO;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.lts.dto.UpgradePcdSrvDTO;


public class UpgradePcdSrvDAO extends BaseDAO {

	@SuppressWarnings("unchecked")
	public List<UpgradePcdSrvDTO> getUpgradePcdSrv(String pAddrCoverage, String pImsExitSrv, String pImsNewSrv) throws DAOException{
		
		
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		
		StringBuilder sql= new StringBuilder();
		
		sql.append("SELECT ADDR_COVERAGE,") 
	 	.append(" IMS_EXIST_SRV, IMS_NEW_SRV, MODEM_ARRANGEMENT,") 
		.append(" MIN_BANDWIDTH, IMS_ORDER_TYPE ")
		.append(" FROM")
		.append(" w_upgrade_pcd_srv_lkup") 
		.append(" WHERE ADDR_COVERAGE = :AddrCoverage ");
		paramSource.addValue("AddrCoverage", StringUtils.defaultIfEmpty(pAddrCoverage, "NULL"));
		
		if (StringUtils.isNotEmpty(pImsExitSrv)) {
			sql.append(" AND IMS_EXIST_SRV = :ImsExitSrv");
			paramSource.addValue("ImsExitSrv", pImsExitSrv);
		}
		else {
			sql.append(" AND IMS_EXIST_SRV IS NULL");
		}
		if (StringUtils.isNotEmpty(pImsNewSrv)) {
			sql.append(" AND IMS_NEW_SRV = :ImsNewSrv");
			paramSource.addValue("ImsNewSrv", pImsNewSrv);
		}
		

		
		ParameterizedRowMapper<UpgradePcdSrvDTO> mapper = new ParameterizedRowMapper<UpgradePcdSrvDTO>() {
			public UpgradePcdSrvDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				UpgradePcdSrvDTO upgradePcdSrvDTO = new UpgradePcdSrvDTO();
            	upgradePcdSrvDTO.setAddrCoverage(rs.getString("ADDR_COVERAGE"));
            	upgradePcdSrvDTO.setImsExitSrv(rs.getString("IMS_EXIST_SRV"));
            	upgradePcdSrvDTO.setImsNewSrv(rs.getString("IMS_NEW_SRV"));
            	upgradePcdSrvDTO.setModemArrangement(rs.getString("MODEM_ARRANGEMENT"));
            	upgradePcdSrvDTO.setMinBandwidth(rs.getInt("MIN_BANDWIDTH"));
            	upgradePcdSrvDTO.setImsOrderType(rs.getString("IMS_ORDER_TYPE"));
				return upgradePcdSrvDTO;
			}
		};
		

		try {
			if (logger.isDebugEnabled()) {
				logger.debug("getUpgradePcdSrv() Sql: " + sql.toString());	
			}
			return simpleJdbcTemplate.getNamedParameterJdbcOperations().query(sql.toString(), paramSource, mapper);

		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException in getUpgradePcdSrv()");
			return null;
		} catch (Exception e) {
			logger.info("Exception caught in getUpgradePcdSrv():", e);
			throw new DAOException(e.getMessage(), e);
		}
		
	}
}
