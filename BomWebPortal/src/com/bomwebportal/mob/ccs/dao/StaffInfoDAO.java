package com.bomwebportal.mob.ccs.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.bomwebportal.dao.BaseDAO;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.mob.ccs.dto.ui.StaffInfoUI;
import com.bomwebportal.util.BomWebPortalConstant;
import com.bomwebportal.util.Utility;

public class StaffInfoDAO extends BaseDAO {

	protected final Log logger = LogFactory.getLog(getClass());
	
	private static final String insertBomwebStaffInfoSQL = 
		"INSERT INTO bomweb_staff_info \n"
		+"             (order_id, sales_type, sales_cd, sales_name, ref_sales_type, \n"
		+"              ref_sales_cd, ref_sales_name, contact_phone, POSITION, call_date, call_list, call_list_desc ,\n"
		+"              lock_ind, create_by, create_date, last_upd_by, last_upd_date, \n"
		+"              centre_cd, team_cd, ref_centre_cd, ref_team_cd \n"
		+"             ) \n"
		+"      VALUES (?, ?, ?, ?, ?, \n"
		+"              ?, ?, ?, ?, TO_DATE (?, 'DD/MM/YYYY hh24:mi'), ?, ?, \n"
		+"              ?, ?, SYSDATE, ?, SYSDATE, \n"
		+"              ?, ?, ?, ? \n"
		+"             )";
	
	public int insertBomwebStaffInfo(StaffInfoUI dto) throws DAOException {
		logger.debug("insertBomwebStaffInfo is called");
	
		String callDate = null;
		
		if (dto.getCallDateStr() != null && dto.getCallTimeStr() != null) {
			callDate = dto.getCallDateStr()+" "+dto.getCallTimeStr();
		}
		
		try {
			
			logger.debug("insertBomwebStaffInfo() @ StaffInfoDAO: "
					+ insertBomwebStaffInfoSQL);
			
			return simpleJdbcTemplate.update(
					insertBomwebStaffInfoSQL,
					dto.getOrderId(), dto.getSalesType(), dto.getSalesId(), dto.getSalesName(),
					dto.getRefSalesType(), dto.getRefSalesId(), dto.getRefSalesName(),
					dto.getContactPhone(),
					dto.getPosition(), callDate, dto.getCallList(), dto.getCallListDesc(),
					dto.getLockInd(), dto.getCreateBy(), dto.getLastUpdBy(),
					dto.getSalesCentre(), dto.getSalesTeam(), dto.getRefSalesCentre(), dto.getRefSalesTeam()
					);
		} catch (Exception e) {
			logger.error("Exception caught in insertBomwebStaffInfo()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	private static String getStaffInfoDTOSQL 
		= " select order_id orderId, sales_type salesType, sales_cd salesCd, sales_name salesName,  \n"
		+" ref_sales_type refSalesType, ref_sales_cd refSalesCd, ref_sales_name refSalesName, \n"
		+" position, to_Char(call_date, 'DD/MM/YYYY') callDateStr, to_Char(call_date, 'HH24:mi') callTimeStr, \n"
		+" call_list callList, call_list_desc, lock_ind lockInd, contact_phone,  \n"
		+" (select acct.brand from bomweb_acct acct where acct.order_id = ?) BRAND, " 
		+" centre_cd, team_cd, ref_centre_cd, ref_team_cd,  \n"
		+" (select a.HOTLINE from bomweb_shop a   \n"
		+" LEFT JOIN bomweb_order b on (a.SHOP_CD=b.SHOP_CD)  \n"
		+" where b.ORDER_ID = ?) HotLine,  \n"	
		+" (select a.HOTLINE_1010 from bomweb_shop a   \n"
		+" LEFT JOIN bomweb_order b on (a.SHOP_CD=b.SHOP_CD)  \n"
		+" where b.ORDER_ID = ?) HotLine1010  \n"
		+" from bomweb_staff_info \n"
		+" where order_id = ?";

	public StaffInfoUI getStaffInfoDTO(String orderId) throws DAOException {
		
		logger.debug("getStaffInfoDTO is called");
		
		List<StaffInfoUI> itemList = new ArrayList<StaffInfoUI>();
		
		/**** ==ParameterizedRowMapper start== *********************************************************/
		ParameterizedRowMapper<StaffInfoUI> mapper = new ParameterizedRowMapper<StaffInfoUI>() {
			
			public StaffInfoUI mapRow(ResultSet rs, int rowNum) throws SQLException {
				StaffInfoUI dto = new StaffInfoUI();
				dto.setOrderId(rs.getString("orderId"));
				dto.setSalesType(rs.getString("salesType"));
				dto.setSalesId(rs.getString("salesCd"));
				dto.setSalesName(rs.getString("salesName"));
				dto.setRefSalesType(rs.getString("refSalesType"));
				dto.setRefSalesId(rs.getString("refSalesCd"));
				dto.setRefSalesName(rs.getString("refSalesName"));
				dto.setPosition(rs.getString("position"));
				dto.setCallDateStr(rs.getString("callDateStr"));
				dto.setCallTimeStr(rs.getString("callTimeStr"));
				dto.setCallList(rs.getString("callList"));
				dto.setCallListDesc(rs.getString("call_list_desc"));
				dto.setLockInd(rs.getString("lockInd"));
				dto.setContactPhone(rs.getString("contact_phone")); 
				dto.setSalesCentre(rs.getString("centre_cd")); 
				dto.setSalesTeam(rs.getString("team_cd")); 
				dto.setRefSalesCentre(rs.getString("ref_centre_cd")); 
				dto.setRefSalesTeam(rs.getString("ref_team_cd")); 
				dto.setHotLine(rs.getString("HotLine"));
				if ("0".equals(rs.getString("BRAND"))){
					dto.setHotLine(rs.getString("HotLine1010"));
				}
				
				return dto;
			}
		};
		/**** ==ParameterizedRowMapper end== *********************************************************/
		
		try {
			
			logger.debug("getStaffName() @ StaffInfoDAO: " + getStaffInfoDTOSQL);
			itemList = simpleJdbcTemplate.query(getStaffInfoDTOSQL, mapper, orderId, orderId, orderId, orderId);
			
		} catch (EmptyResultDataAccessException erdae) {
			
			logger.info("EmptyResultDataAccessException in getStaffInfoDTO()");
			itemList = null;
			
		} catch (Exception e) {
			logger.info("Exception caught in getStaffInfoDTO():", e);
	
			throw new DAOException(e.getMessage(), e);
		}
		
		if (itemList.size() == 0 || itemList == null) {
			itemList.add(0, new StaffInfoUI());
		}
		return itemList.get(0);// only return the first one
	}

	private static String getStaffNameSQL 
		= "select sp.staff_name "
				+"  from bomweb_sales_profile sp "
				+"where sp.staff_id = ? "
				+"   and to_date(?, 'DD/MM/YYYY') between "
				+"       trunc(nvl(sp.start_date, sysdate)) and "
				+"       trunc(nvl(sp.end_date, sysdate)) ";

	/**
	 * 
	 * @param staffId
	 * @param appDate Format = DD/MM/YYYY
	 * @return staffName
	 * @throws DAOException
	 */
	public String getStaffName(String staffId, String appDate) throws DAOException {
		
		logger.debug("getStaffName is called");
		
		if (StringUtils.isBlank(appDate)) {
			appDate = Utility.date2String(new Date(), BomWebPortalConstant.DATE_FORMAT);
		}
		
		List<String> itemList = new ArrayList<String>();
		
		/**** ==ParameterizedRowMapper start== *********************************************************/
		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
			
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getString("staff_name");
			}
		};
		/**** ==ParameterizedRowMapper end== *********************************************************/
		
		try {
			
			logger.debug("getStaffName() @ StaffInfoDAO: " + getStaffNameSQL);
			itemList = simpleJdbcTemplate.query(getStaffNameSQL, mapper, staffId, appDate);
			
		} catch (EmptyResultDataAccessException erdae) {
			
			logger.info("EmptyResultDataAccessException in getStaffName()");
			itemList = null;
			
		} catch (Exception e) {
			logger.info("Exception caught in getStaffName():", e);

			throw new DAOException(e.getMessage(), e);
		}
		
		if (CollectionUtils.isEmpty(itemList)) {
			itemList.add(0, "");
		}
		return itemList.get(0);// only return the first one
	}
	
	private static String getChannelCdSQL 
		= "SELECT channel_cd " 
				+"FROM bomweb_sales_assignment " 
				+"WHERE staff_id = ? " 
				+"AND NVL(to_date(?, 'DD/MM/YYYY'), SYSDATE) BETWEEN start_date AND NVL(end_date, to_date('01/01/2132', 'DD/MM/YYYY'))";
	
	public String getChannelCd(String staffId, String appDate) throws DAOException {
		
		logger.debug("getChannelCd is called");
		
		List<String> itemList = new ArrayList<String>();
		
		/**** ==ParameterizedRowMapper start== *********************************************************/
		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
			
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getString("channel_cd");
			}
		};
		/**** ==ParameterizedRowMapper end== *********************************************************/
		
		try {
			
			logger.debug("getChannelCd() @ StaffInfoDAO: " + getChannelCdSQL);
			if (StringUtils.isBlank(appDate)) {
				appDate = Utility.date2String(new Date(), BomWebPortalConstant.DATE_FORMAT);
			}
			itemList = simpleJdbcTemplate.query(getChannelCdSQL, mapper, staffId, appDate);
			
		} catch (EmptyResultDataAccessException erdae) {
			
			logger.info("EmptyResultDataAccessException in getChannelCd()");
			itemList = null;
			
		} catch (Exception e) {
			logger.info("Exception caught in getChannelCd():", e);
	
			throw new DAOException(e.getMessage(), e);
		}
		
		if (itemList.size() == 0 || itemList == null) {
			itemList.add(0, "");
		}
		return itemList.get(0);// only return the first one
	}
	
	private static String getCcsHotlineSQL 
	= "select hotline from bomweb_ccs_staff_hotline where staff_id =:staffId  and brand =:brand ";

public String getCcsHotline(String staffId, String brand) throws DAOException {
	
	logger.debug("getCcsHotline is called");
	
	String hotline = "";
	
	MapSqlParameterSource params = new MapSqlParameterSource();
	params.addValue("staffId", staffId);
	params.addValue("brand", brand);
	
	/**** ==ParameterizedRowMapper start== *********************************************************/
	ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
		
		public String mapRow(ResultSet rs, int rowNum) throws SQLException {
			return rs.getString("hotline");
		}
	};
	/**** ==ParameterizedRowMapper end== *********************************************************/
	
	try {
		
		logger.debug("getCcsHotline() @ StaffInfoDAO: " + getCcsHotlineSQL);
		hotline = simpleJdbcTemplate.queryForObject(getCcsHotlineSQL, mapper, params);
		
	} catch (EmptyResultDataAccessException erdae) {
		
		logger.info("EmptyResultDataAccessException in getCcsHotline()");
		hotline = null;
		
	} catch (Exception e) {
		logger.info("Exception caught in getCcsHotline():", e);

		throw new DAOException(e.getMessage(), e);
	}
	
	return hotline;// only return the first one
}

}
