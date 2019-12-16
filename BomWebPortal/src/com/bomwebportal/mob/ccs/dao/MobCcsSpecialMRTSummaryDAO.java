package com.bomwebportal.mob.ccs.dao;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;

import com.bomwebportal.dao.BaseDAO;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.mob.ccs.dto.MaintParmLkupDTO;
import com.bomwebportal.mob.ccs.dto.SpecialMrtRequestDTO;
import org.apache.commons.lang.StringUtils;

public class MobCcsSpecialMRTSummaryDAO extends BaseDAO{
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	
	public List<SpecialMrtRequestDTO> getSpecialMRTRequests(String requestStatus,
			String requestDateFrom, String requestDateTo, String channel,
			String requestedBy, String mobNum) throws DAOException{
	
		String sql = "select request_id, request_date, request_by, channel, first_name, last_name, msisdn_pattern" +
					", approval_result, msisdn from bomweb_special_mrt_request where 1=1 ";
		
	
		if (StringUtils.isNotBlank(requestDateFrom)){
		
			if (requestDateFrom.equals(requestDateTo)) {
				
				sql = sql + " and trunc(request_date) = to_date(:requestDateFrom, 'DD/MM/YYYY')";
				
			} else {
			
				sql = sql + " and trunc(request_date) between to_date(:requestDateFrom, 'DD/MM/YYYY') and to_date(:requestDateTo, 'DD/MM/YYYY')";
	
			}
		
		}
		
		if (StringUtils.isNotBlank(requestStatus)){

			sql = sql + " and approval_result=:requestStatus";
			
		}
		
		if (StringUtils.isNotBlank(channel)){

			sql = sql + " and channel=:channel";
			
		}
		
		if (StringUtils.isNotBlank(requestedBy)){

			sql = sql + " and request_by=:requestedBy";
			
		}
		
		if (StringUtils.isNotBlank(mobNum)){

			sql = sql + " and MSISDN=:mobNum";
			
		}
		
		sql = sql + " order by request_id desc";

		
		List<SpecialMrtRequestDTO> list = null;
		
		try {
			System.out.println(sql);
			logger.info("sql: "+sql);
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("requestDateFrom", requestDateFrom);
			params.addValue("requestDateTo", requestDateTo);
			params.addValue("channel", channel);
			params.addValue("requestedBy", requestedBy);
			params.addValue("mobNum", mobNum);
			params.addValue("requestStatus", requestStatus);
			list = simpleJdbcTemplate.query(sql, ParameterizedBeanPropertyRowMapper.newInstance(SpecialMrtRequestDTO.class),params);
			
		} catch (EmptyResultDataAccessException erdae){
			logger.debug("empty result", erdae);
		}
			
		
		return list;
	
	}
	
	public List<SpecialMrtRequestDTO> getSpecialMRTRequestsByManager(String requestStatus, String requestDateFrom, String requestDateTo, String channelCd, String teamCd) throws DAOException{
		
		String sql = "select c.request_id, c.request_date, c.request_by, c.channel, c.first_name, c.last_name, c.msisdn_pattern" +
				", c.approval_result, c.msisdn" +
				" from bomweb_sales_assignment a, bomweb_sales_profile b, bomweb_special_mrt_request c" +
				" where a.channel_cd=:channelCd" +
				" and a.team_cd=:teamCd" +
				" and sysdate between a.start_date and nvl(a.end_date,sysdate)" +
				" and a.staff_id=b.staff_id" +
				" and sysdate between b.start_date and nvl(b.end_date,sysdate)" +
				" and c.channel = a.channel_cd" +
				" and c.request_by = a.staff_id";
		
		if (StringUtils.isNotBlank(requestDateFrom)){
		
			if (requestDateFrom.equals(requestDateTo)) {
				
				sql = sql + " and trunc(request_date) = to_date(:requestDateFrom, 'DD/MM/YYYY')";
				
			} else {
			
				sql = sql + " and trunc(request_date) between to_date(:requestDateFrom, 'DD/MM/YYYY') and to_date(:requestDateTo, 'DD/MM/YYYY')";
	
			}
		
		}
		
		if (StringUtils.isNotBlank(requestStatus)){

			sql = sql + " and approval_result=:requestStatus";
			
		}
		
		List<SpecialMrtRequestDTO> list = null;
		
		try {
			System.out.println(sql);
			logger.info("sql: "+sql);
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("requestDateFrom", requestDateFrom);
			params.addValue("requestDateTo", requestDateTo);
			params.addValue("requestStatus", requestStatus);
			params.addValue("channelCd", channelCd);
			params.addValue("teamCd", teamCd);
			list = simpleJdbcTemplate.query(sql, ParameterizedBeanPropertyRowMapper.newInstance(SpecialMrtRequestDTO.class),params);
			
		} catch (EmptyResultDataAccessException erdae){
			logger.debug("empty result", erdae);
		}
			
		
		return list;
		
	}
	
	public List<MaintParmLkupDTO> getResultStatusTypes(String channelCd)throws DAOException{
		
		String sql = "select distinct parm_value from bomweb_maint_parm_lkup where function_cd = 'SPECIAL_MRT_REQUEST' and parm_type='APPROVE_RESULT' and channel_cd=:channelCd order by parm_value";
		
		List<MaintParmLkupDTO> list = null;
		
		try {
			System.out.println(sql);
			logger.info("sql: "+sql);
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("channelCd", channelCd);
			list = simpleJdbcTemplate.query(sql, ParameterizedBeanPropertyRowMapper.newInstance(MaintParmLkupDTO.class),params);
			
		} catch (EmptyResultDataAccessException erdae){
			logger.debug("empty result", erdae);
		}
		
			
		return list;
		
	}
	
	public List<MaintParmLkupDTO> getChannelTypes()throws DAOException{
		
		String sql = "select distinct parm_value from bomweb_maint_parm_lkup where function_cd = 'SPECIAL_MRT_REQUEST' and parm_type='CHANNEL' order by parm_value";
		
		List<MaintParmLkupDTO> list = null;
		
		list = simpleJdbcTemplate.query(sql, ParameterizedBeanPropertyRowMapper.newInstance(MaintParmLkupDTO.class));
		
		
		return list;
		
	}
	
	
}
