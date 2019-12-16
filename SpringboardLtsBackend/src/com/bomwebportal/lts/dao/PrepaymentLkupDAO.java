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
import com.bomwebportal.lts.dto.PrepaymentLkupCriteriaDTO;
import com.bomwebportal.lts.dto.PrepaymentLkupResultDTO;

public class PrepaymentLkupDAO extends BaseDAO {
	
	public List<PrepaymentLkupResultDTO> getPrepaymentItemId(PrepaymentLkupCriteriaDTO criteria) throws DAOException{
		
		StringBuilder sqlBuilder = new StringBuilder("SELECT prepay_item_id, pay_mtd, psef_code FROM bomweb_prepay_lkup ");

		MapSqlParameterSource paramSource = new MapSqlParameterSource();

		sqlBuilder.append("WHERE (lower_tenure <= :tenure AND upper_tenure >= :tenure) ");
		sqlBuilder.append("AND (lower_eye_profile <= :eyeCnt AND upper_eye_profile >= :eyeCnt) ");
		paramSource.addValue("tenure", criteria.getTenure());
		paramSource.addValue("eyeCnt", criteria.getEyeProfileCount());
		
		if(StringUtils.isNotBlank(criteria.getPsefCd())){
			sqlBuilder.append("AND psef_code LIKE :likePsef ");
			paramSource.addValue("likePsef", "%"+criteria.getPsefCd()+"%");
		}

		if(StringUtils.isNotBlank(criteria.getChannel())){
			sqlBuilder.append("AND (channel LIKE :likeChannel OR channel = '*') ");
			paramSource.addValue("likeChannel", "%"+criteria.getChannel()+"%");
		}

		if(StringUtils.isNotBlank(criteria.getOrderType())){
			sqlBuilder.append("AND (ord_type LIKE :likeOrdType OR ord_type = '*') ");
			paramSource.addValue("likeOrdType", "%"+criteria.getOrderType()+"%");
		}

		if(StringUtils.isNotBlank(criteria.getDocType())){
			sqlBuilder.append("AND (doc_type LIKE :likeDocType OR doc_type = '*') ");
			paramSource.addValue("likeDocType", "%"+criteria.getDocType()+"%");
		}

		if(StringUtils.isNotBlank(criteria.getPaymentMethod())){
			sqlBuilder.append("AND (pay_mtd LIKE :likePayMtd OR pay_mtd = '*') ");
			paramSource.addValue("likePayMtd", "%"+criteria.getPaymentMethod()+"%");
		}

		if(StringUtils.isNotBlank(criteria.getHouseType())){
			sqlBuilder.append("AND (house_type LIKE :likeHouseType OR house_type = '*') ");
			paramSource.addValue("likeHouseType", "%"+criteria.getHouseType()+"%");
		}

		if(StringUtils.isNotBlank(criteria.getAppDate())){
			sqlBuilder.append("AND (eff_start_date < TO_DATE(:appDate, 'dd/MM/yyyy HH24:mi') OR eff_start_date IS NULL) ");
			sqlBuilder.append("AND (eff_end_date > TO_DATE(:appDate, 'dd/MM/yyyy HH24:mi') OR eff_end_date IS NULL) ");
			paramSource.addValue("appDate", criteria.getAppDate());
		}

		String sql = sqlBuilder.toString();
		criteria.setReferenceSql(sql);
		
		ParameterizedRowMapper<PrepaymentLkupResultDTO> rowMapper = new ParameterizedRowMapper<PrepaymentLkupResultDTO>() {
			public PrepaymentLkupResultDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				PrepaymentLkupResultDTO result = new PrepaymentLkupResultDTO();
				result.setPrepayItemId(rs.getString("prepay_item_id"));
				result.setPaymentMethod(rs.getString("pay_mtd"));
				result.setPsefCd(rs.getString("psef_code"));
				return result;
			}			
		};
		
		try {
			return this.simpleJdbcTemplate.query(sql, rowMapper, paramSource);
		}
		catch (EmptyResultDataAccessException erdae) {
			return null;
		} catch (Exception e) {
			logger.error("Error in getPrepaymentItemId - ", e);
			throw new DAOException(e.getMessage(), e);
		}
		
	}

}
