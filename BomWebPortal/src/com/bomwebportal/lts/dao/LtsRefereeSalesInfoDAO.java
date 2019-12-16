package com.bomwebportal.lts.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.bomwebportal.dao.BaseDAO;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.lts.dto.acq.LtsRefereeSaleDTO;

public class LtsRefereeSalesInfoDAO extends BaseDAO {
	
	@SuppressWarnings("unchecked")
	public LtsRefereeSaleDTO searchReferreeStaffInformation(String pOrderId) throws DAOException {
		
		MapSqlParameterSource paramSource = new MapSqlParameterSource();		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT REF_SALES_CD, REF_SALES_NAME, REF_TEAM_CD, REF_CENTRE_CD ");
		sql.append("FROM BOMWEB_STAFF_INFO ");
		sql.append("WHERE ORDER_ID = :orderId ");

		ParameterizedRowMapper<LtsRefereeSaleDTO> mapper = new ParameterizedRowMapper<LtsRefereeSaleDTO>() {
			public LtsRefereeSaleDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				LtsRefereeSaleDTO refStaff = new LtsRefereeSaleDTO();

				refStaff.setRefSalesCode(rs.getString("REF_SALES_CD"));
				refStaff.setRefSalesName(rs.getString("REF_SALES_NAME"));
				refStaff.setRefSalesTeam(rs.getString("REF_TEAM_CD"));
				refStaff.setRefSalesCentre(rs.getString("REF_CENTRE_CD"));

				return refStaff;
			}
		};
		
		try {
			paramSource.addValue("orderId", pOrderId);
			List<LtsRefereeSaleDTO> list = simpleJdbcTemplate.query(sql.toString(), mapper, paramSource);
			return (list == null || list.isEmpty()||list.size()==0) ? null : list.get(0);
		} catch (EmptyResultDataAccessException erdae) {
			logger.error("EmptyResultDataAccessException in searchDnListFromPoolByNoCriteria()");
			return null;
		} catch (Exception e) {
			logger.error("Exception caught in searchDnListFromPoolByNoCriteria():", e);
			throw new DAOException(e.getMessage(), e);
		}		
	}
}
