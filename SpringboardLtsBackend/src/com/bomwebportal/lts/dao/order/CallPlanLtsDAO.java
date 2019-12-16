package com.bomwebportal.lts.dao.order;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.bomwebportal.dao.BaseDAO;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.lts.dto.profile.IddCallPlanProfileLtsDTO;

public class CallPlanLtsDAO extends BaseDAO {
	
	
	private static String SQL_GET_CALL_PLAN_BY_ITEM_ID = 
		"select call_plan_cd " +
		" from w_item_call_plan_assgn " +
		" where item_id = :itemId " +
		" order by item_call_plan_seq ";
	
	private static String SQL_GET_IDD_CALL_PLAN = 
			"select call_plan_cd, description, plan_charge, gift_ind " +
			"from w_idd_call_plan_lkup " +
			"where eff_start_date <= sysdate and (eff_end_date is null or eff_end_date > sysdate) " +
			"and call_plan_cd in (:planCdList)";
	
	private static String SQL_GET_CALL_PLAN_TYPE = 
			"select plan_type " +
			" from w_idd_fix_fee_plan_lkup" +
			" where eff_start_date <= sysdate and (eff_end_date is null or eff_end_date > sysdate) " +
			" and call_plan_cd = :callPlanCd";
	
	private static String SQL_GET_CALL_PLAN_CONTRACT_PERIOD = 
			"select contract_month " +
			" from w_idd_fix_fee_plan_lkup" +
			" where eff_start_date <= sysdate and (eff_end_date is null or eff_end_date > sysdate) " +
			" and call_plan_cd = :callPlanCd";
	
	public String[] getCallPlanCd(String itemId) throws DAOException {
		if (StringUtils.isEmpty(itemId)) {
			return null;
		}
		
		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getString("call_plan_cd"); 
			}
		};
		
		try {
			MapSqlParameterSource paramSource = new MapSqlParameterSource();
			paramSource.addValue("itemId", itemId);
			
			List<String> callPlanCdList = simpleJdbcTemplate.query(SQL_GET_CALL_PLAN_BY_ITEM_ID, mapper, paramSource);
			if (callPlanCdList == null || callPlanCdList.isEmpty()) {
				return null;
			}
			
			return callPlanCdList.toArray(new String[callPlanCdList.size()]);
		} catch (EmptyResultDataAccessException erdae) {
			return null;
		} catch (Exception e) {
			throw new DAOException(e.getMessage() + "Fail to getCallPlanCd", e);
		}
	}
	
	
	public IddCallPlanProfileLtsDTO[] getIddCallPlan(String[] pIddCallPlanCds) throws DAOException {
		
		if (ArrayUtils.isEmpty(pIddCallPlanCds)) {
			return null;
		}
		ParameterizedRowMapper<IddCallPlanProfileLtsDTO> mapper = new ParameterizedRowMapper<IddCallPlanProfileLtsDTO>() {
			public IddCallPlanProfileLtsDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				IddCallPlanProfileLtsDTO callPlan = new IddCallPlanProfileLtsDTO();
				callPlan.setDescription(rs.getString("description"));
				callPlan.setPlanCd(rs.getString("call_plan_cd"));
				callPlan.setPlanCharge(rs.getString("plan_charge"));
				callPlan.setGiftInd(rs.getString("gift_Ind"));
				return callPlan;
			}
		};
		List<String> callPlanCdList = new ArrayList<String>();
		
		for (int i=0; i<pIddCallPlanCds.length; ++i) {
			callPlanCdList.add(pIddCallPlanCds[i].trim());
		}
		try {
			List<IddCallPlanProfileLtsDTO> callPlanList = simpleJdbcTemplate.query(SQL_GET_IDD_CALL_PLAN, mapper, Collections.singletonMap("planCdList", callPlanCdList));
			return callPlanList.toArray(new IddCallPlanProfileLtsDTO[callPlanList.size()]);
		} catch (EmptyResultDataAccessException erdae) {
			return null;
		} catch (Exception e) {
			throw new DAOException(e.getMessage() + "Fail to getIddCallPlan", e);
		}
	}
	
	public String getCallPlanType(String callPlanCd) throws DAOException {
		
		try {
			MapSqlParameterSource paramSource = new MapSqlParameterSource();
			paramSource.addValue("callPlanCd", callPlanCd);
			return simpleJdbcTemplate.queryForObject(SQL_GET_CALL_PLAN_TYPE, String.class, paramSource);
		} catch (EmptyResultDataAccessException erdae) {
			return null;
		} catch (Exception e) {
			throw new DAOException(e.getMessage() + "Fail to getCallPlanType [callPlanCd:" + callPlanCd + "]", e);
		}
	}
	
	public String getCallPlanContractPeriod(String callPlanCd) throws DAOException {
		
		try {
			MapSqlParameterSource paramSource = new MapSqlParameterSource();
			paramSource.addValue("callPlanCd", callPlanCd);
			return simpleJdbcTemplate.queryForObject(SQL_GET_CALL_PLAN_CONTRACT_PERIOD, String.class, paramSource);
		} catch (EmptyResultDataAccessException erdae) {
			return null;
		} catch (Exception e) {
			throw new DAOException(e.getMessage() + "Fail to getCallPlanContractPeriod [callPlanCd:" + callPlanCd + "]", e);
		}
	}
	
}
