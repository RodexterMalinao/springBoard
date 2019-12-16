package com.bomwebportal.ims.dao;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;

import com.bomwebportal.dao.BaseDAO;
import com.bomwebportal.exception.DAOException;

public class ValidateFixedLineDAO extends BaseDAO{
	protected final Log logger = LogFactory.getLog(getClass());
	
	public String validateFixedLine(String srvNum, String serbdyno, String unit, String floor) throws DAOException{
		logger.info("validateFixedLine is called");
		
		try{
			SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate);
			
			String valid = "N";
			String SQL = "select decode(count(*),0,'N','Y') is_valid from B_SERVICE_TYPE_ASSGN bsta, b_service bs, b_address_dtl bat" +
						 " where bs.service_type = 'TEL'" +
						 " and bsta.service_id = bs.service_id" +
						 " and bsta.dat_cd = 'DEL'" +
						 " and bs.srv_num= LPAD(?, 12, '0')" +
						 " and bs.eff_start_date<=trunc(sysdate)" +
						 " and nvl(bs.eff_end_date,to_date('20991231','yyyyMMdd'))>trunc(sysdate)" +
						 " and bsta.eff_start_date<=trunc(sysdate)" +
						 " and nvl(bsta.eff_end_date,to_date('20991231','yyyyMMdd'))>trunc(sysdate)" +
						 " and bat.addr_Id=bs.install_addr" +
						 " and bat.srvbdry_num=?" +
						 " and nvl(bat.unit_num,' ')=nvl(?,' ') and nvl(bat.floor_num,' ')=nvl(?,' ')";

			valid = simpleJdbcTemplate.queryForObject(SQL, String.class, srvNum, serbdyno, unit, floor);
			
			return valid;
		}catch (Exception e) {
				logger.error("Exception caught in validateFixedLine()", e);
				throw new DAOException(e.getMessage(), e);
		}
	}
}
