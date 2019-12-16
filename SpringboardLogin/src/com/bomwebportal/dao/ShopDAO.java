package com.bomwebportal.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.dto.LoginLogDTO;

import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;

public class ShopDAO extends BaseDAO{

	protected final Log logger = LogFactory.getLog(getClass());
	
	public List<String> getShopList()throws DAOException {
		List<String> shopList = new ArrayList<String>();
			
		String SQL = 
			"SELECT CHANNEL_ID, CENTRE_CD, SHOP_CD, SEQ_NO " +
			"FROM BOMWEB_SHOP " +
			"WHERE CHANNEL_ID = 1 " +
			"ORDER BY SHOP_CD";	
		
		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
		    	       
	        public String mapRow(ResultSet rs, int rowNum) throws SQLException {
	        
	            return rs.getString("SHOP_CD");
	        }
	    };
	    
		try {	
		
			shopList = simpleJdbcTemplate.query(SQL,mapper);
			
		} catch (EmptyResultDataAccessException erdae) {
			throw new AppRuntimeException(erdae);		
		} catch (Exception e) {
			logger.error("Exception caught in getShopList()", e);
			throw new DAOException(e.getMessage(), e);
		}
		return shopList;
	}
	
	// add by Joyce 20111026
	// modified by Joyce 20111122, 20120312
	public BomSalesUserDTO getSalesAssign(String username) {
		logger.debug("getSalesAssign() is called");

		List<BomSalesUserDTO> salesList = new ArrayList<BomSalesUserDTO>();

		String SQL = 
			"SELECT sa.channel_id, " 
			+"  sa.CHANNEL_CD, " 
			+"  sa.CENTRE_CD, " 
			+"  sa.TEAM_CD, " 
			+"  sp.staff_name, " 
			+"  sp.sales_code, " 
			+"  sp.category, " 
			+"  sa.staff_id, "
			+"  sp.org_staff_id "
			+"FROM BOMWEB_SALES_ASSIGNMENT sa, " 
			+"  BOMWEB_SALES_PROFILE sp " 
			+"WHERE sa.STAFF_ID = ? " 
			+"AND trunc(sysdate) BETWEEN sa.START_DATE AND NVL(sa. END_DATE,sysdate) " 
			+"AND sa.staff_id=sp.staff_id " 
			+"AND trunc(sysdate) BETWEEN sp.START_DATE AND NVL(sp. END_DATE,sysdate)";

		ParameterizedRowMapper<BomSalesUserDTO> mapper = new ParameterizedRowMapper<BomSalesUserDTO>() {
			public BomSalesUserDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				BomSalesUserDTO dto = new BomSalesUserDTO();

				dto.setChannelId(rs.getInt("channel_id"));
				dto.setChannelCd(rs.getString("CHANNEL_CD"));
				dto.setAreaCd(rs.getString("CENTRE_CD"));
				dto.setShopCd(rs.getString("TEAM_CD"));
				dto.setStaffName(rs.getString("staff_name"));
				dto.setSalesCd(rs.getString("sales_code"));
				dto.setCategory(rs.getString("category"));
				dto.setUsername(rs.getString("STAFF_ID"));
				dto.setOrgStaffId(rs.getString("ORG_STAFF_ID"));

				return dto;
			}
		};

		try {
			//herbert 20111110 - remove useless SQL logger
			logger.debug("getSalesAssign() @ ShopDAO: " + SQL);
//			logger.debug("getSalesAssign() @ ShopDAO: " );

			salesList = simpleJdbcTemplate.query(SQL, mapper, username);

		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException");

			salesList = null;
		} catch (Exception e) {
			logger.info("Exception caught in getSalesAssign():", e);

			try {
				throw new DAOException(e.getMessage(), e);
			} catch (DAOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

		if (salesList == null || (salesList != null && salesList.size() <= 0)) {
			return null;
		} else {
			if (salesList.get(0).getChannelId() == 2 || salesList.get(0).getChannelId() == 3) {
				salesList.set(0, 
						getSalesAssignDetail(salesList.get(0).getChannelCd(), salesList.get(0)));
			} else {
				salesList.set(0, 
						getSalesAssignDetail(salesList.get(0).getShopCd(), salesList.get(0)));
			}
		}
		return salesList.get(0);// only return the first one
	}
	
	// add by Joyce 20111122
	// for retail, add email, contact phone & BOC
	private BomSalesUserDTO getSalesAssignDetail(String shopCd, BomSalesUserDTO inputDto) {
		logger.debug("getSalesAssignDetail() is called");

		List<BomSalesUserDTO> salesList = new ArrayList<BomSalesUserDTO>();

		// modified by Joyce 20111215, add pilot status to distinguish active shop(s) for IMS
		String SQL = 
			"SELECT email_addr, contact_phone, boc, pilot_status, lts_pilot_status, mob_pilot_status, bom_shop_cd FROM bomweb_shop WHERE shop_cd = ?";

		ParameterizedRowMapper<BomSalesUserDTO> mapper = new ParameterizedRowMapper<BomSalesUserDTO>() {
			public BomSalesUserDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				
				BomSalesUserDTO dto = new BomSalesUserDTO();

				dto.setShopEmailAddr(rs.getString("EMAIL_ADDR"));
				dto.setShopContactPhone(rs.getString("CONTACT_PHONE"));
				dto.setBoc(rs.getString("boc"));
				dto.setPilotStatus(rs.getString("pilot_status"));
				dto.setLtsPilotStatus(rs.getString("lts_pilot_status"));
				dto.setMobPilotStatus(rs.getString("mob_pilot_status"));
				dto.setBomShopCd(rs.getString("bom_shop_cd"));
				return dto;
			}
		};

		try {
			//herbert 20111110 - remove useless SQL logger
			logger.debug("getSalesAssignDetail() @ ShopDAO: " + SQL);
//			logger.info("getSalesAssignDetail() @ ShopDAO: " );

			salesList = simpleJdbcTemplate.query(SQL, mapper, shopCd);

		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException");

			salesList = null;
		} catch (Exception e) {
			logger.info("Exception caught in getSalesAssignDetail():", e);

			try {
				throw new DAOException(e.getMessage(), e);
			} catch (DAOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

		if (salesList == null || (salesList != null && salesList.size() <= 0)) {
			return inputDto;
		} else {
			salesList.get(0).setChannelId(inputDto.getChannelId());
			salesList.get(0).setChannelCd(inputDto.getChannelCd());
			salesList.get(0).setAreaCd(inputDto.getAreaCd());
			salesList.get(0).setShopCd(inputDto.getShopCd());
			salesList.get(0).setStaffName(inputDto.getStaffName());
			salesList.get(0).setSalesCd(inputDto.getSalesCd());
			salesList.get(0).setCategory(inputDto.getCategory());
			salesList.get(0).setUsername(inputDto.getUsername());
			salesList.get(0).setOrgStaffId(inputDto.getOrgStaffId());
		}
		return salesList.get(0);// only return the first one
	}
	
	// add by Joyce 20111108
	// CENTRE_CD = AREA_CD
	// TEAM_CD = SHOP_CD
	public BomSalesUserDTO getCentreCdFromTeamCd(String teamCd) {
		
		logger.debug("getCentreCdFromTeamCd() is called");

		List<BomSalesUserDTO> centreCdList = new ArrayList<BomSalesUserDTO>();

		// modified by Joyce 20111215, add pilot status to distinguish active shop(s) for IMS
		String SQL = "select sales_channel, shop_cd, centre_cd, pilot_status, lts_pilot_status, mob_pilot_status, bom_shop_cd from bomweb_shop where shop_cd = ?";

		ParameterizedRowMapper<BomSalesUserDTO> mapper = new ParameterizedRowMapper<BomSalesUserDTO>() {
			public BomSalesUserDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				
				BomSalesUserDTO tempDTO = new BomSalesUserDTO();
				tempDTO.setAreaCd(rs.getString("CENTRE_CD"));
				tempDTO.setPilotStatus(rs.getString("pilot_status"));
				tempDTO.setLtsPilotStatus(rs.getString("lts_pilot_status"));
				tempDTO.setMobPilotStatus(rs.getString("mob_pilot_status"));
				tempDTO.setBomShopCd(rs.getString("bom_shop_cd"));
				tempDTO.setChannelCd(rs.getString("sales_channel"));
				tempDTO.setShopCd(rs.getString("shop_cd"));
				return tempDTO;
			}
		};

		try {
			//herbert 20111110 - remove useless SQL logger
//			logger.info("getCentreCdFromTeamCd() @ ShopDAO: " + SQL);
			logger.debug("getCentreCdFromTeamCd() @ ShopDAO: " + SQL);
			centreCdList = simpleJdbcTemplate.query(SQL, mapper, teamCd);

		} catch (EmptyResultDataAccessException erdae) {
			
			logger.info("EmptyResultDataAccessException");
			BomSalesUserDTO dummyDTO = new BomSalesUserDTO();
			dummyDTO.setAreaCd("UNDEFINED");
			dummyDTO.setPilotStatus("N");
			dummyDTO.setLtsPilotStatus("N");
			centreCdList.add(dummyDTO);
			
		} catch (BadSqlGrammarException bsge) {
			
			logger.info("BadSqlGrammarException", bsge);
			BomSalesUserDTO dummyDTO = new BomSalesUserDTO();
			dummyDTO.setAreaCd("UNDEFINED");
			dummyDTO.setPilotStatus("N");
			dummyDTO.setLtsPilotStatus("N");
			centreCdList.add(dummyDTO);
			
		} catch (Exception e) {
			
			logger.info("Exception caught in getCentreCdFromTeamCd():", e);

			try {
				throw new DAOException(e.getMessage(), e);
			} catch (DAOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		if (centreCdList.size() == 0) {
			BomSalesUserDTO dummyDTO = new BomSalesUserDTO();
			dummyDTO.setAreaCd("UNDEFINED");
			dummyDTO.setPilotStatus("N");
			dummyDTO.setLtsPilotStatus("N");
			centreCdList.add(dummyDTO);
		} else if (centreCdList.get(0).getAreaCd() == null || "null".equalsIgnoreCase(centreCdList.get(0).getAreaCd())) {
			BomSalesUserDTO dummyDTO = new BomSalesUserDTO();
			dummyDTO.setAreaCd("UNDEFINED");
			dummyDTO.setPilotStatus("N");
			dummyDTO.setLtsPilotStatus("N");
			centreCdList.add(dummyDTO);
		}

		return centreCdList.get(0);// only return the first one
	}
	
	//add by wilson , 20120718, for single login
		public int updateSessionId(String staffId, String sessionId) throws DAOException {

			String sql = "update BOMWEB_SALES_PROFILE SP\n" +
				"   set SP.SESSION_ID    = ?,\n" + 
				"       SP.LAST_UPD_BY   = ?,\n" + 
				"       SP.LAST_UPD_DATE = sysdate\n" + 
				" where SP.STAFF_ID = ?\n" + 
				"   and TRUNC(sysdate) between SP.START_DATE and\n" + 
				"       NVL(SP.END_DATE, TRUNC(sysdate))";

			try {
				return simpleJdbcTemplate.update(sql, sessionId,staffId,staffId);

			} catch (Exception e) {
				logger.error("Exception caught in updateSessionId()", e);
				throw new DAOException(e.getMessage(), e);
			}

		}
		
		//add by wilson , 20120718, for single login
		public String getDbRecordSessionId(String staffId) throws DAOException {
			String result = "";
			String sql = "select SP.SESSION_ID\n"
					+ "  from BOMWEB_SALES_PROFILE SP\n"
					+ " where SP.STAFF_ID = ?\n"
					+ "   and TRUNC(sysdate) between SP.START_DATE and\n"
					+ "       NVL(SP.END_DATE, TRUNC(sysdate))\n"
					+ "       and rownum=1";

			try {
				result = (String) simpleJdbcTemplate.queryForObject(sql,
						String.class, staffId);

			} catch (Exception e) {
				logger.error("Exception caught in updateSessionId()", e);
				throw new DAOException(e.getMessage(), e);
			}
			return result;

		}
		
		//add by wilson , 20120718, for single login
		private static final String insertLoginLogSQL = "insert into BOMWEB_LOGIN_LOG\n"
				+ "  (STAFF_ID,\n"
				+ "   IP_ADDRESS,\n"
				+ "   SESSION_ID,\n"
				+ "   CREATE_BY,\n"
				+ "   CREATE_DATE,\n"
				+ "   LAST_UPD_BY,\n"
				+ "   LAST_UPD_DATE)\n"
				+ "values\n"
				+ "  (:staffId,\n"
				+ "   :ipAddress,\n"
				+ "   :sessionId,\n"
				+ "   :staffId,\n"
				+ "   sysdate,\n" 
				+ "   :staffId,\n" 
				+ "   sysdate)";

		public int insertLoginLog(String staffId, String sessionId, String ipAddress)
				throws DAOException {
			if (logger.isInfoEnabled()) {
				logger.debug("insertLoginLog is called");
			}
			try {
				if (logger.isDebugEnabled()) {
					logger.debug("insertLoginLog() @ ShopDAO: " + insertLoginLogSQL);
				}
				MapSqlParameterSource params = new MapSqlParameterSource();
				params.addValue("staffId", staffId);
				params.addValue("ipAddress", ipAddress);
				params.addValue("sessionId", sessionId);

				return simpleJdbcTemplate.update(insertLoginLogSQL, params);
			} catch (Exception e) {
				logger.error("Exception caught in insertLoginLog()", e);
				throw new DAOException(e.getMessage(), e);
			}
		}
		
	public LoginLogDTO getLoginLogDTO(String staffId, String sessionId) throws DAOException {
		final String sql = "select" +
				" STAFF_ID" +
				" , IP_ADDRESS" +
				" , SESSION_ID" +
				" , CREATE_BY" +
				" , CREATE_DATE" +
				" , LAST_UPD_BY" +
				" , LAST_UPD_DATE" +
				" , rowid ROW_ID" +
				" FROM BOMWEB_LOGIN_LOG" +
				" WHERE " +
				" STAFF_ID = :staffId" +
				" AND SESSION_ID = :sessionId";
		List<LoginLogDTO> list = Collections.emptyList();
		try {
			if (logger.isInfoEnabled()) {
				logger.info("staffId: " + staffId + ", sessionId: " + sessionId);
			}
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("staffId", staffId);
			params.addValue("sessionId", sessionId);
			list = this.simpleJdbcTemplate.query(sql, this.getLoginLogDTORowMapper(), params);
		} catch (EmptyResultDataAccessException e) {
			if (logger.isDebugEnabled()) {
				logger.debug("EmptyResultDataAccessException caught in getLoginLogDTO()", e);
			}
		} catch (Exception e) {
			logger.error("Exception caught in getLoginLogDTO()", e);
			throw new DAOException(e.getMessage(), e);
		}
		return this.isEmpty(list) ? null : list.get(0);
	}
	
	private ParameterizedRowMapper<LoginLogDTO> getLoginLogDTORowMapper() {
		return new ParameterizedRowMapper<LoginLogDTO>() {
			@Override
			public LoginLogDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				LoginLogDTO dto = new LoginLogDTO();
				dto.setStaffId(rs.getString("STAFF_ID"));
				dto.setIpAddress(rs.getString("IP_ADDRESS"));
				dto.setSessionId(rs.getString("SESSION_ID"));
				dto.setCreateBy(rs.getString("CREATE_BY"));
				dto.setCreateDate(rs.getTimestamp("CREATE_DATE"));
				dto.setLastUpdBy(rs.getString("LAST_UPD_BY"));
				dto.setLastUpdDate(rs.getTimestamp("LAST_UPD_DATE"));
				dto.setRowId(rs.getString("ROW_ID"));
				return dto;
			}
			
		};
	}
	
	private boolean isEmpty(List<?> list) {
		return list == null || list.isEmpty();
	}
}

