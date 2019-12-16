package com.bomwebportal.mob.ccs.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.bomwebportal.dao.BaseDAO;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.mob.ccs.dto.MrtReserveDTO;
import com.bomwebportal.mob.ccs.dto.ui.MRTReserveUI;

public class MrtReserveDAO extends BaseDAO {

	protected final Log logger = LogFactory.getLog(getClass());
	
	// === Start of INSERT function ===
	private static final String insertMrtReserveSQL = 
		"INSERT INTO bomweb_mrt_status \n"
		+"(reserve_type, staff_id, srv_num_type, msisdn, msisdnlvl, status, apply_date, \n"
		+"created_by, created_date, last_upd_by, last_upd_date \n"
		+") \n"
		+"VALUES ('NORMAL',?, ?, ?, ?, '18', SYSDATE, \n"
		+"?, SYSDATE, ?, SYSDATE \n"
		+")";

	public int insertMrtReserve(MrtReserveDTO dto) throws DAOException {
		logger.info("insertMrtReserve is called");

		try {

			logger.debug("insertMrtReserve() @ MrtReserveDAO: "
					+ insertMrtReserveSQL);
			
			int result = simpleJdbcTemplate.update(
					insertMrtReserveSQL,
					// start sql mapping
					dto.getStaffId(), dto.getSrvNumType(), dto.getMsisdn(), 
					dto.getMsisdnlvl(), dto.getCreatedBy(), dto.getLastUpdBy()
					// end sql mapping
					);
			logger.debug("insertMrtReserve result: " + result);
			return result;
		} catch (Exception e) {
			logger.error("Exception caught in insertMrtReserve()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	// === End of INSERT function ===
	
	private final String getReserveMRTListSQL = 
		"SELECT BMS.msisdn,   \n"
		+"   to_char(BMS.apply_date, 'DD/MM/YYYY hh24:mi:ss') reserve_date,   \n"
		+"   floor((BMS.apply_date + 2 - sysdate) * 24)   \n"
		+"   || ' HRS '   \n"
		+"   || mod(floor((BMS.apply_date + 2 - sysdate) * 24 * 60), 60)   \n"
		+"   || ' MINS '   \n"
		+"   || MOD(FLOOR((BMS.apply_date + 2 - SYSDATE) * 24 * 60 * 60), 60)   \n"
		+"   || ' SECS ' REMAIN_TIME   \n"
		+" , BMS.msisdnlvl \n"
		+" , BMI.num_type \n"
		
		+" FROM BOMWEB_MRT_STATUS BMS  \n"
		+" JOIN BOMWEB_MRT_INVENTORY BMI ON BMS.MSISDN = BMI.MSISDN AND BMS.STATUS = BMI.MSISDN_STATUS \n"
		+" WHERE BMS.STAFF_ID = ?   \n"
		+" AND BMS.STATUS     = '18'   \n"
		+" AND BMS.RESERVE_TYPE     = 'NORMAL'   \n"
		+" ORDER BY RESERVE_DATE";

	public List<MRTReserveUI> getReserveMRTList(String staffId) throws DAOException {
		logger.info(" getReserveMRTList is called");

		List<MRTReserveUI> outDTO = new ArrayList<MRTReserveUI>();

		ParameterizedRowMapper<MRTReserveUI> mapper = new ParameterizedRowMapper<MRTReserveUI>() {

			public MRTReserveUI mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				MRTReserveUI ui = new MRTReserveUI();
				ui.setdReservedMrt(rs.getString("msisdn"));
				ui.setReserveDate(rs.getString("reserve_date"));
				ui.setRemainingTime(rs.getString("REMAIN_TIME"));
				ui.setMsisdnlvl(rs.getString("msisdnlvl"));
				ui.setNumType(rs.getString("num_type"));
				return ui;
			}
		};

		try {
			logger.debug("getReserveMRTList() @ MrtReserveDAO: "
					+ getReserveMRTListSQL);

			outDTO = (List<MRTReserveUI>) simpleJdbcTemplate.query(getReserveMRTListSQL, mapper, staffId);
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException in getReserveMRTList()");
			outDTO = null;
		} catch (Exception e) {
			logger.info("Exception caught in getReserveMRTList():",e);
			throw new DAOException(e.getMessage(), e);
		}

		return outDTO;
	}

}
