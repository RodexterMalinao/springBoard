package com.bomwebportal.sbs.dao;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;

import com.bomwebportal.dao.BaseDAO;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.sbs.dto.DeliveryDateRangeDTO;
import com.bomwebportal.sbs.dto.DeliveryTimeSlotDTO;


public class SbsDeliveryTimeSlotDAO extends BaseDAO{
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	public List<DeliveryTimeSlotDTO> getDeliveryTimeSlotList (String distNo, Date appDate) throws DAOException {
		
		
		String sql = "select SLOT.TIMESLOT TIMESLOT, SLOT.TIMEFROM || '-' || SLOT.TIMETO DESCRIPTION" +
				" from W_DISTRICT_TIMESLOT_ASSGN ADDR, W_DELIVERY_TIMESLOT SLOT" +
				" where ADDR.DISTDESC = (SELECT DISTDSC FROM W_ADDRLKUP_DISTRICT WHERE CODE = :distNo) " +
				" and trunc(:appDate) between trunc(addr.start_date) and trunc(nvl(addr.end_date, sysdate)) " +
				" and ADDR.TIMESLOT = SLOT.TIMESLOT";
		
		List<DeliveryTimeSlotDTO> list= null;
		
		try {
			logger.info("sql: " + sql);
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("distNo", distNo);
			params.addValue("appDate", appDate);
			list = simpleJdbcTemplate.query(sql, ParameterizedBeanPropertyRowMapper.newInstance(DeliveryTimeSlotDTO.class), params);
		} catch (EmptyResultDataAccessException erdae) {
			logger.debug("empty result", erdae);
		}
		
		return list;
		
	}
	//new sp
	/*begin
	  -- Call the procedure
	  pkg_sb_mob_order.online_delivery_date_range(i_appn_date => :i_appn_date,
	                                              o_e_del_date => :o_e_del_date,
	                                              o_l_del_date => :o_l_del_date,
	                                              o_ph_date => :o_ph_date);
	end;*/
	
	public Date[] normalDeliveryDateRange(String orderId, String payMethod, String itemCode, java.util.Date appDate) throws DAOException {

		try {			
			SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
				.withSchemaName("OPS$CNM")
            	.withCatalogName("PKG_SB_MOB_ORDER")
            	.withProcedureName("ORDER_DELIVERY_DATE_RANGE");
			jdbcCall.setAccessCallParameterMetaData(false);
			jdbcCall.declareParameters(
					new SqlParameter("i_order_id", Types.VARCHAR),
					new SqlParameter("i_appn_date", Types.DATE),
					new SqlParameter("i_pay_method", Types.VARCHAR),
					new SqlParameter("i_item_code", Types.VARCHAR),
					new SqlOutParameter("o_e_del_date", Types.DATE),
					new SqlOutParameter("o_l_del_date", Types.DATE));
			
		
			
			MapSqlParameterSource inMap = new MapSqlParameterSource();
			inMap.addValue("i_order_id", orderId);
			inMap.addValue("i_pay_method", payMethod);
			inMap.addValue("i_item_code", itemCode);
			inMap.addValue("i_appn_date", appDate);
			SqlParameterSource in = inMap;
			
			Map out = jdbcCall.execute(in);
			
			Date[] resultDate = new Date[2];
			
			Date startDate = (Date)out.get("o_e_del_date");
			Date endDate = (Date)out.get("o_l_del_date");
			
			resultDate[0] = startDate;
			resultDate[1] = endDate;
			
			logger.info("PKG_SB_MOB_ORDER.NORMAL_DELIVERY_DATE_RANGE() o_e_del_date = " + startDate);
			logger.info("PKG_SB_MOB_ORDER.NORMAL_DELIVERY_DATE_RANGE() o_l_del_date = " + endDate);
			
			return resultDate;
			
		} catch (Exception e) {
			logger.error("Exception caught in normalDeliveryDateRange()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public String getDateType(String date) throws DAOException{
		
		List<String> orderIdList = new ArrayList<String>();
		String sql = "Select day_type from w_job_schedule " +
				"where job_date = TO_DATE(:V_DATE, 'DD/MM/YYYY')";
		
		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {

			public String mapRow(ResultSet rs, int rowNum)
					throws SQLException {
								
				return rs.getString("day_type");
			}
		};
		
		
		
		try {
			
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("V_DATE", date);
		
			orderIdList = simpleJdbcTemplate.query(sql,  mapper, params);
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException");
			orderIdList.add(0, "");
		} catch (Exception e) {
			logger.error("Exception caught in getOrderIdUsingSameMRT()", e);
			throw new DAOException(e.getMessage(), e);
		}

		if (orderIdList.size() == 0 || orderIdList == null) {
			orderIdList.add(0, "");	
		}

		return orderIdList.get(0);
	}
	//new sp
	/*begin
	  -- Call the procedure
	  pkg_sb_mob_order.online_delivery_date_range(i_appn_date => :i_appn_date,
	                                              o_e_del_date => :o_e_del_date,
	                                              o_l_del_date => :o_l_del_date,
	                                              o_ph_date => :o_ph_date);
	end;*/
	
	public DeliveryDateRangeDTO getDeliveryDate( Date appDate) throws DAOException {
		DeliveryDateRangeDTO result= new DeliveryDateRangeDTO();
		try {			
			SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
				.withSchemaName("OPS$CNM")
            	.withCatalogName("PKG_SB_MOB_ORDER")
            	.withProcedureName("online_delivery_date_range");
			jdbcCall.setAccessCallParameterMetaData(false);
			jdbcCall.declareParameters(
					
					new SqlParameter("i_appn_date", Types.DATE),
			
					new SqlOutParameter("o_e_del_date", Types.DATE),
					new SqlOutParameter("o_l_del_date", Types.DATE),
					new SqlOutParameter("o_ph_date", Types.VARCHAR));
			
		
			
			MapSqlParameterSource inMap = new MapSqlParameterSource();
			//inMap.addValue("i_order_id", orderId);
		//	inMap.addValue("i_pay_method", payMethod);
			//inMap.addValue("i_item_code", itemCode);
			inMap.addValue("i_appn_date", appDate);
			SqlParameterSource in = inMap;
			
			Map out = jdbcCall.execute(in);

			Date startDate = (Date)out.get("o_e_del_date");
			Date endDate = (Date)out.get("o_l_del_date");
			String phDateString = (String)out.get("o_ph_date");
			result.setStartDate(startDate);
			result.setEndDate(endDate);
			result.setPhDateString(phDateString);
			
			logger.info("PKG_SB_MOB_ORDER.online_delivery_date_range() o_e_del_date = " + startDate);
			logger.info("PKG_SB_MOB_ORDER.online_delivery_date_range() o_l_del_date = " + endDate);
			
			return result;
			
		} catch (Exception e) {
			logger.error("Exception caught in getDeliveryDate()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	
	
	
	
	

}
