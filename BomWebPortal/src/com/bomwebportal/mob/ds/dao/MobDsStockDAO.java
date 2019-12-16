package com.bomwebportal.mob.ds.dao;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.bomwebportal.exception.DAOException;
import com.bomwebportal.mob.ccs.dto.StockDTO;
import com.bomwebportal.mob.ccs.dto.StockQuantityEnquiryDTO;
import com.bomwebportal.mob.ccs.dto.StockResultDTO;
import com.bomwebportal.mob.ccs.dto.StockUpdateDTO;
import com.bomwebportal.dao.BaseDAO;
import com.bomwebportal.dto.BomSalesUserDTO;

public class MobDsStockDAO extends BaseDAO {
	
	private static String getUserMultiStoreTeamCodeSQL = "SELECT DISTINCT bs.centre_cd, bs.shop_cd "
    		+ "FROM bomweb_shop bs, bomweb_sales_assignment bsa "
    		+ "WHERE bs.channel_id = 10 "
    		+ "AND bsa.staff_id = ? "
    	    + "AND trunc(sysdate) BETWEEN trunc(bsa.start_date) and trunc(nvl(bsa.end_date, sysdate)) ";

    public List<StockDTO> getUserMultiStoreTeamCode(String username, int channelId)
	    throws DAOException {
	logger.info("getUserMultiStoreTeamCode @ MobDsStockDAO is called");
	List<StockDTO> shopList = new ArrayList<StockDTO>();

	ParameterizedRowMapper<StockDTO> mapper = new ParameterizedRowMapper<StockDTO>() {
	    public StockDTO mapRow(ResultSet rs, int rowNum)
		    throws SQLException {

	    	StockDTO tempDto = new StockDTO();
    		tempDto.setStoreCode(rs.getString("centre_cd"));
    		tempDto.setTeamCode(rs.getString("shop_cd"));
    		return tempDto;
	    }
	};
	
	StringBuilder sql = new StringBuilder(getUserMultiStoreTeamCodeSQL);
	if (channelId == 10) {
		sql.append("AND bs.shop_cd = bsa.team_cd ");
	} else {
		sql.append("AND bs.sales_channel = decode(bsa.team_cd, 'MOB', bs.sales_channel, bsa.team_cd) ");
	}
	sql.append("ORDER BY bs.centre_cd, bs.shop_cd");

	try {
	    logger.info("getUserMultiStoreTeamCode() @ StockDAO: "
		    + sql.toString());

	    shopList = simpleJdbcTemplate.query(sql.toString(),
		    mapper, username);
	} catch (EmptyResultDataAccessException erdae) {
	    logger.info("EmptyResultDataAccessException in getUserMultiStoreTeamCode()");

	    shopList = null;
	} catch (Exception e) {
	    logger.info("Exception caught in getUserMultiStoreTeamCode():", e);

	    throw new DAOException(e.getMessage(), e);
	}
		return shopList;
    }
    
    private static String getUserMultiStoreCodeSQL = "SELECT DISTINCT bs.centre_cd "
    		+ "FROM bomweb_shop bs, bomweb_sales_assignment bsa "
    		+ "WHERE bs.channel_id = 10 "
    		+ "AND bsa.staff_id = ? "
    	    + "AND trunc(sysdate) BETWEEN trunc(bsa.start_date) and trunc(nvl(bsa.end_date, sysdate)) ";

    public List<StockDTO> getUserMultiStoreCode(String username, int channelId)
	    throws DAOException {
	logger.info("getUserMultiStoreCode @ StockDAO is called");
	List<StockDTO> storeList = new ArrayList<StockDTO>();

	ParameterizedRowMapper<StockDTO> mapper = new ParameterizedRowMapper<StockDTO>() {
	    public StockDTO mapRow(ResultSet rs, int rowNum)
		    throws SQLException {

	    	StockDTO tempDto = new StockDTO();
    		tempDto.setStoreCode(rs.getString("centre_cd"));
    		return tempDto;
	    }
	};

	StringBuilder sql = new StringBuilder(getUserMultiStoreCodeSQL);
	if (channelId == 10) {
		sql.append("AND bs.centre_cd = bsa.centre_cd ");
	} else {
		sql.append("AND bs.sales_channel = decode(bsa.team_cd, 'MOB', bs.sales_channel, bsa.team_cd) ");
	}
	sql.append("ORDER BY bs.centre_cd");
	
	try {
	    logger.info("getUserMultiStoreCode() @ MobDsStockDAO: "
		    + sql.toString());

	    storeList = simpleJdbcTemplate.query(sql.toString(),
		    mapper, username);
	} catch (EmptyResultDataAccessException erdae) {
	    logger.info("EmptyResultDataAccessException in getUserMultiStoreCode()");

	    storeList = null;
	} catch (Exception e) {
	    logger.info("Exception caught in getUserMultiStoreCode():", e);

	    throw new DAOException(e.getMessage(), e);
	}
		return storeList;
    }
	
    private static String getDSStockUpdateDTObyImeiSQL 
    = "SELECT DISTINCT st.item_type, " 
    		+"  bsi.item_code, " 
    		+"  bsc.item_desc, " 
    		+"  bsi.item_serial, " 
    		+"  ss.status_id, " 
    		+"  bsa.order_id, " 
    		+"  bsi.create_date stock_in_date, " 
    		+"  bsi.remarks, " 
    		+"  bsi.batch_ref, "
    		+"  bsi.STOCK_POOL, " 
    		+"  bsi.event_cd, "
    		+"  bsi.store_cd, "
    		+"  bsi.team_cd, "
    		+"  bsi.staff_id, "
    		+"  bs.sales_channel "
    		+"FROM bomweb_stock_inventory bsi, " 
    		+"  bomweb_stock_catalog bsc, " 
    		+"  (SELECT DISTINCT code_id item_type, " 
    		+"    code_desc stock_type " 
    		+"  FROM bomweb_code_lkup " 
    		+"  WHERE code_type='STOCK_TYPE' " 
    		+"  ) st, " 
    		+"  (SELECT DISTINCT code_id status_id, " 
    		+"    code_desc status " 
    		+"  FROM bomweb_code_lkup " 
    		+"  WHERE code_type='STOCK_STS' " 
    		+"  ) ss, " 
    		+"  (SELECT DISTINCT order_id, " 
    		+"    item_code, " 
    		+"    status_id , " 
    		+"    item_serial " 
    		+"  FROM bomweb_stock_assgn " 
    		+"  WHERE item_serial = ? " 
    		+"  AND status_id    <>'24' " 
    		+"  ) bsa, " 
    		+"  (SELECT DISTINCT staff_id, " 
    		+"    centre_cd, " 
    		+"    team_cd " 
    		+"  FROM bomweb_sales_assignment "  
    		+"  WHERE channel_id = '10' "
    		+"  ) bsaa, "
    		+"  (select sales_channel, shop_cd from bomweb_shop where channel_id = '10') bs " 
    		+"WHERE bsi.item_code = bsc.item_code " 
    		+"AND bsc.item_type   = st.item_type " 
    		+"AND bsi.status_id   = ss.status_id " 
    		+"AND bsi.staff_id    = bsaa.staff_id (+) " 
    		+"AND bsi.store_cd    = bsaa.centre_cd (+) "
    		+"AND bsi.team_cd     = bs.shop_cd (+) " 
    		+"AND bsi.team_cd     = bsaa.team_cd (+) " 
    		+"AND bsi.item_serial = bsa.item_serial (+) " 
    		+"AND bsi.item_serial = ?";

    public List<StockUpdateDTO> getDSStockUpdateDTObyImei(String serialNumber)
	    throws DAOException {

		logger.info("getDSStockUpdateDTObyImei @ MobDsStockDAO is called");
		List<StockUpdateDTO> itemList = new ArrayList<StockUpdateDTO>();
	
		ParameterizedRowMapper<StockUpdateDTO> mapper = new ParameterizedRowMapper<StockUpdateDTO>() {
		    public StockUpdateDTO mapRow(ResultSet rs, int rowNum)
			    throws SQLException {
	
			StockUpdateDTO tempDto = new StockUpdateDTO();
			tempDto.setType(rs.getString("ITEM_TYPE"));
			tempDto.setItemCode(rs.getString("item_code"));
			tempDto.setModel(rs.getString("ITEM_DESC"));
			tempDto.setImei(rs.getString("ITEM_SERIAL"));
			tempDto.setStatus(rs.getString("status_id"));
			tempDto.setOrderId(rs.getString("ORDER_ID"));
			tempDto.setRemarks(rs.getString("remarks"));
			tempDto.setBatchRef(rs.getString("batch_ref"));
			tempDto.setStockPool(rs.getString("STOCK_POOL"));
			tempDto.setEventCode(rs.getString("event_cd"));
			tempDto.setStoreCode(rs.getString("store_cd"));
			tempDto.setTeamCode(rs.getString("team_cd"));
			tempDto.setStaffId(rs.getString("staff_id"));
			tempDto.setChannelCd(rs.getString("sales_channel"));
			return tempDto;
		    }
		};
	
		try {
		    logger.info("getDSStockUpdateDTObyImei() @ MobDsStockDAO: "
			    + getDSStockUpdateDTObyImeiSQL);
	
		    itemList = simpleJdbcTemplate.query(getDSStockUpdateDTObyImeiSQL,
			    mapper, serialNumber, serialNumber);
		} catch (EmptyResultDataAccessException erdae) {
		    logger.error("EmptyResultDataAccessException in getDSStockUpdateDTObyImei()");
	
		    itemList = null;
		} catch (Exception e) {
		    logger.error("Exception caught in getDSStockUpdateDTObyImei():", e);
	
		    throw new DAOException(e.getMessage(), e);
		}
		return itemList;
    }
	
    private static String getValidEventListSQL = "SELECT DISTINCT event_cd "
    	    + "from bomweb_event " 
    	    + "where trunc(eff_end_date) >= trunc(sysdate) OR "
    	    + "eff_end_date is null "
    	    + "order by event_cd";
    
    public List<StockDTO> getValidEventList() throws DAOException {

	logger.info("getValidEventList @ MobccsstockmodelDAO is called");
	List<StockDTO> itemList = new ArrayList<StockDTO>();

	ParameterizedRowMapper<StockDTO> mapper = new ParameterizedRowMapper<StockDTO>() {
	    public StockDTO mapRow(ResultSet rs, int rowNum)
		    throws SQLException {

	    	StockDTO tempDto = new StockDTO();
		tempDto.setEventCode(rs.getString("event_cd"));
		return tempDto;
	    }
	};
	
	try {
	    logger.info("getValidEventList() @ MobDsStockDAO: "
		    + getValidEventListSQL);

	    itemList = simpleJdbcTemplate.query(getValidEventListSQL,
		    mapper);
	} catch (EmptyResultDataAccessException erdae) {
	    logger.error("EmptyResultDataAccessException in getValidEventList()");

	    itemList = null;
	} catch (Exception e) {
	    logger.error("Exception caught in getValidEventList():", e);

	    throw new DAOException(e.getMessage(), e);
	}
	return itemList;
    }
    
    private static String getStockStoreCodeListSQL = "SELECT DISTINCT bs.centre_cd store_cd "
    	    + "from bomweb_shop bs " 
    	    + "where bs.channel_id = '10' "
    	    + "order by bs.centre_cd";
    
    public List<StockUpdateDTO> getStockStoreCodeList() throws DAOException {

	logger.info("getStockStoreCodeList @ MobDsStockDAO is called");
	List<StockUpdateDTO> itemList = new ArrayList<StockUpdateDTO>();

	ParameterizedRowMapper<StockUpdateDTO> mapper = new ParameterizedRowMapper<StockUpdateDTO>() {
	    public StockUpdateDTO mapRow(ResultSet rs, int rowNum)
		    throws SQLException {

	    StockUpdateDTO tempDto = new StockUpdateDTO();
		tempDto.setStoreCode(rs.getString("store_cd"));
		return tempDto;
	    }
	};
	
	try {
	    logger.info("getStockStoreCodeList() @ MobDsStockDAO: "
		    + getStockStoreCodeListSQL);

	    itemList = simpleJdbcTemplate.query(getStockStoreCodeListSQL,
		    mapper);
	} catch (EmptyResultDataAccessException erdae) {
	    logger.error("EmptyResultDataAccessException in getStockStoreCodeList()");

	    itemList = null;
	} catch (Exception e) {
	    logger.error("Exception caught in getStockStoreCodeList():", e);

	    throw new DAOException(e.getMessage(), e);
	}
	return itemList;
    }
    
    private static String getTeamListByStoreSQL = "SELECT DISTINCT bs.shop_cd "
    	    + "from bomweb_shop bs " 
    	    + "where bs.channel_id = '10' "
    	    + "and centre_cd = :centreCode "
    	    + "order by bs.shop_cd";
    
    public List<StockDTO> getTeamListByStore(String storeCode) throws DAOException {

	logger.info("getTeamListByStore @ MobDsStockDAO is called");
	List<StockDTO> itemList = new ArrayList<StockDTO>();

	ParameterizedRowMapper<StockDTO> mapper = new ParameterizedRowMapper<StockDTO>() {
	    public StockDTO mapRow(ResultSet rs, int rowNum)
		    throws SQLException {

	    	StockDTO tempDto = new StockDTO();
	    	tempDto.setTeamCode(rs.getString("shop_cd"));
		return tempDto;
	    }
	};
	
	try {
	    logger.info("getTeamListByStore() @ MobDsStockDAO: "
		    + getTeamListByStoreSQL);

	    itemList = simpleJdbcTemplate.query(getTeamListByStoreSQL,
		    mapper, storeCode);
	} catch (EmptyResultDataAccessException erdae) {
	    logger.error("EmptyResultDataAccessException in getTeamListByStore()");

	    itemList = null;
	} catch (Exception e) {
	    logger.error("Exception caught in getTeamListByStore():", e);

	    throw new DAOException(e.getMessage(), e);
	}
	return itemList;
    }
    
    private static String getDSStockResultDTOSQL = 
    	  "SELECT DISTINCT bsc.item_type, st.code_desc stock_type, bsi.item_code, bsc.item_desc, bsi.item_serial, ss.code_desc status, \n"
    	+ "       decode(bsi.item_code, 'C000000', nvl(bsa1.order_id, bsa2.order_id), bsa.order_id) order_id, "
    	+ "       bsi.create_date stock_in_date, bsi.remarks, bsi.batch_ref, \n"
    	+ "       bsi.stock_pool, bsi.event_cd, bsi.store_cd, bsi.team_cd, bsi.staff_id \n"
    	+ "FROM bomweb_stock_inventory bsi \n"
    	+ "JOIN bomweb_stock_catalog bsc on bsi.item_code = bsc.item_code \n"
    	+ "JOIN bomweb_code_lkup st ON st.code_type='STOCK_TYPE' AND bsc.item_type = st.code_id \n"
    	+ "JOIN bomweb_code_lkup ss ON ss.code_type='STOCK_STS' AND bsi.status_id = ss.code_id \n"
    	/*+ "LEFT JOIN bomweb_stock_assgn bsa ON (bsi.item_serial = bsa.item_serial or bsi.item_serial = bsa.sm_no or bsi.item_serial = bsa.sm2_no) and bsa.status_id != '24' \n"*/
    	+ "LEFT JOIN bomweb_stock_assgn bsa ON bsi.item_serial = bsa.item_serial AND bsa.status_id != '24'\n" 
    	+ "LEFT JOIN bomweb_stock_assgn bsa1 ON bsi.item_serial = bsa1.sm_no AND bsa1.status_id != '24' \n"
    	+ "LEFT JOIN bomweb_stock_assgn bsa2 ON bsi.item_serial = bsa2.sm2_no AND bsa2.status_id != '24' \n"
    	+ "WHERE 1=1 \n";

    public List<StockResultDTO> getDSStockResultDTO(StockDTO dto)
	    throws DAOException {

    	logger.info("getDSStockResultDTO @ MobDsStockDAO is called");
		List<StockResultDTO> itemList = new ArrayList<StockResultDTO>();
		
		ParameterizedRowMapper<StockResultDTO> mapper = new ParameterizedRowMapper<StockResultDTO>() {
		    public StockResultDTO mapRow(ResultSet rs, int rowNum)
			    throws SQLException {
	
			StockResultDTO dto = new StockResultDTO();
	
			dto.setType(rs.getString("stock_type"));
			dto.setItemCode(rs.getString("item_code"));
			dto.setModel(rs.getString("item_desc"));
			dto.setImei(rs.getString("item_serial"));
			dto.setStatus(rs.getString("status"));
			dto.setOrderId(rs.getString("ORDER_ID"));
			dto.setStockInDate(rs.getDate("stock_in_date"));
			dto.setRemarks(rs.getString("remarks"));
			dto.setBatchRef(rs.getString("batch_ref"));
			dto.setStockPool(rs.getString("stock_pool"));
			dto.setEventCode(rs.getString("event_cd"));
			dto.setStoreCode(rs.getString("store_cd"));
			dto.setTeamCode(rs.getString("team_cd"));
			dto.setStaffID(rs.getString("staff_id"));
			return dto;
		    }
		};
	
		try {
	
		    StringBuilder sql = new StringBuilder(getDSStockResultDTOSQL);
		    MapSqlParameterSource params = new MapSqlParameterSource();
		    
		    if (dto.getSk() == 1 || dto.getSk() == 2) {
		    	// search by item code
		    	sql.append("AND bsi.item_code in (:itemCode) \n");
		    	params.addValue("itemCode", dto.getSelectedItemCodeList());
		    } else if (dto.getSk() == 3) {
		    	// search by item serial
		    	sql.append("AND bsi.item_serial = trim(:itemSerial) \n");
		    	params.addValue("itemSerial", dto.getItemSerial());
		    } else if (dto.getSk() == 4) {
		    	// search by order id
		    	sql.append("AND (bsa.order_id = trim(:orderId) OR bsa1.order_id = trim(:orderId) OR bsa2.order_id = trim(:orderId)) \n");
		    	params.addValue("orderId", dto.getOrderId());
		    } else {
		    	return null;
		    }
		    
		    if (dto.getSk() != 4) {
			    if (dto.getChannelId() == 10) {
			    	if ("FRONTLINE".equalsIgnoreCase(dto.getCategory())) {
			    		//Frontline -> assigned stock only
				    	sql.append(" AND bsi.staff_id = :staffId \n");
					} else {
						//Supervisor -> team stock
						sql.append(" AND (bsi.store_cd, bsi.team_cd) in ( ");
						sql.append("     select centre_cd, team_cd from bomweb_sales_assignment \n");
						sql.append("     where staff_id = :staffId \n");
						sql.append(" and sysdate between start_date and nvl(end_date, sysdate) \n");
						sql.append(" ) \n");
					}
	    		} else {
	    			//MObile Support -> all, Warehouse -> channel stock
    				/*sql.append("AND bsi.store_cd in ( ");
					sql.append("    select distinct bs.centre_cd \n");
					sql.append("    from bomweb_shop bs \n");
					sql.append("    join bomweb_sales_assignment sales on bs.sales_channel = decode(sales.team_cd, 'MOB', bs.sales_channel, sales.team_cd) \n");
					sql.append("    where bs.channel_id = 10 \n");
					sql.append("    and sysdate between sales.start_date and nvl(sales.end_date, sysdate) \n");
					sql.append("    and sales.staff_id = :staffId) \n");*/
					sql.append("and bsi.store_cd in (select decode(team_cd, \n" + 
							"            'MOB', bsi.store_cd, \n" + 
							"            (select distinct centre_cd from bomweb_shop bs where bs.sales_channel = team_cd and bs.centre_cd = bsi.store_cd)) \n" + 
							"    from bomweb_sales_assignment bsa where staff_id = :staffId \n" + 
							"    and sysdate between bsa.start_date and nvl(bsa.end_date, sysdate) \n" + 
							")");
	    		}
			    params.addValue("staffId", dto.getStaffId());
		    }
		    
		    if (StringUtils.isNotBlank(dto.getStockPool())) {
		    	sql.append(" AND bsi.stock_pool = :stockPool \n");
		    	params.addValue("stockPool", dto.getStockPool());
			}
		    if (StringUtils.isNotBlank(dto.getStoreCode())) {
	    		sql.append("AND bsi.store_cd = :storeCode \n");
	    		params.addValue("storeCode", dto.getStoreCode());
	    	}
	    	if (StringUtils.isNotBlank(dto.getTeamCode())) {
	    		sql.append("AND bsi.team_cd = :teamCode \n");
	    		params.addValue("teamCode", dto.getTeamCode());
	    	}
		    if (StringUtils.isNotBlank(dto.getEventCode())) {
		    	sql.append(" AND bsi.event_cd = :eventCode \n");
		    	params.addValue("eventCode", dto.getEventCode());
			}
		    if (StringUtils.isNotBlank(dto.getStatus()) && !"ALL".equalsIgnoreCase(dto.getStatus())) {
		    	sql.append("AND bsi.status_id in (:status) \n");
				params.addValue("status", dto.getStatus());
		    }
		    sql.append("ORDER BY  bsc.item_type, bsi.item_code, bsi.item_serial ");
		    
		    logger.debug("getDSStockResultDTO() @ MobDsStockDAO: " + sql.toString());
		    itemList = simpleJdbcTemplate.query(sql.toString(), mapper, params);
		} catch (EmptyResultDataAccessException erdae) {
		    logger.error("EmptyResultDataAccessException in getDSStockResultDTO()");
		    itemList = null;
		} catch (Exception e) {
		    logger.error("Exception caught in getDSStockResultDTO():", e);
		    throw new DAOException(e.getMessage(), e);
		}
		return itemList;
    }
    
	public boolean validateEffEndDate(String eventCode) throws DAOException {
		
		logger.info("validateEffEndDate @ MobDsStockDAO is called");
		
		if (eventCode == null || "".equals(eventCode)) {
			return false;
		}
		
		String sql = "select event_cd from bomweb_event " +
					 "where event_cd = :eventCode " +
					 "and (trunc(eff_end_date) >= trunc(sysdate) or eff_end_date is null) " +
					 "order by event_cd ";

		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("eventCode", eventCode);
		
		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
			public String mapRow(ResultSet rs, int row) throws SQLException {
				return rs.getString("event_cd");
			}
		};
		try {
			logger.info("validateEffEndDate() @ MobDsStockDAO: " + sql);
			
			List<String> result = simpleJdbcTemplate.query(sql, mapper, params);
			
			if (result == null || result.isEmpty()) {
				return false;
			} else {
				return !result.get(0).isEmpty();
			}
			
		} catch (EmptyResultDataAccessException erdae) {
			logger.error("EmptyResultDataAccessException in validateEffEndDate()");
		} catch (Exception e) {
			logger.error("Exception caught in validateEffEndDate():", e);
			throw new DAOException(e.getMessage(), e);
		}
		
		return false;
	}
	
	public String[] getStaffAssignTeam(String staffID) throws DAOException {
		
		logger.info("getStaffAssignTeam @ MobDsStockDAO is called");
		
		String sql = "select bsa.centre_cd, bsa.team_cd " +
				 	 "from bomweb_sales_profile bsp, bomweb_sales_assignment bsa " +
					 "where bsp.staff_id = bsa.staff_id " +
				 	 "and bsp.staff_id  = :staffID " +
					 "and bsa.team_cd in (select shop_cd from bomweb_shop where channel_id = '10')";
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("staffID", staffID);
		
		ParameterizedRowMapper<String[]> mapper = new ParameterizedRowMapper<String[]>() {
			public String[] mapRow(ResultSet rs, int row) throws SQLException {
				String[] result = new String[2];
				result[0] = rs.getString("centre_cd");
				result[1] = rs.getString("team_cd");
				return result;
			}
		};
		
		try {
			logger.info("getStaffAssignTeam() @ MobDsStockDAO: " + sql);
			
			List<String[]> result = simpleJdbcTemplate.query(sql.toString(), mapper, params);
			
			if (result == null || result.isEmpty()) {
				return null;
			} else {
				return result.get(0);
			}

		} catch (EmptyResultDataAccessException erdae) {
			logger.error("EmptyResultDataAccessException in getStaffAssignTeam()");
		} catch (Exception e) {
			logger.error("Exception caught in getStaffAssignTeam():", e);
			throw new DAOException(e.getMessage(), e);
		}
		return null;
	}
	
	public String getTeamCode (String staffID) throws DAOException {
		
		logger.info("getTeamCode @ MobDsStockDAO is called");
		
		String sql = "select distinct team_cd from bomweb_sales_assignment "
					 + "where staff_id = :staffID order by team_cd ";
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("staffID", staffID);
		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
			public String mapRow(ResultSet rs, int row) throws SQLException {
				return rs.getString("team_cd");
			}
		};
		try {
			logger.info("getTeamCode() @ MobDsStockDAO: " + sql);
			
			List<String> result = simpleJdbcTemplate.query(sql, mapper, params);
			
			if (result == null || result.isEmpty()) {
				return null;
			} else {
				return result.get(0);
			}
			
		} catch (EmptyResultDataAccessException erdae) {
			logger.error("EmptyResultDataAccessException in getTeamCode()");
		} catch (Exception e) {
			logger.error("Exception caught in getTeamCode():", e);
			throw new DAOException(e.getMessage(), e);
		}
		
		return null;
	}
	
	public String getStoreCodeByTeamCode(String teamCode) throws DAOException {
		
		logger.info("getStoreCodeByTeamCode @ MobDsStockDAO is called");
		
		String sql = "select distinct shop_cd from bomweb_shop "
				 + "where centre_cd = :teamCode order by shop_cd ";

		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("teamCode", teamCode);
		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
			public String mapRow(ResultSet rs, int row) throws SQLException {
				return rs.getString("shop_cd");
			}
		};
		
		try {
			logger.info("getStoreCodeByTeamCode() @ MobDsStockDAO: " + sql);
			
			List<String> result = simpleJdbcTemplate.query(sql, mapper, params);
			
			if (result == null || result.isEmpty()) {
				return null;
			} else {
				return result.get(0);
			}
			
		} catch (EmptyResultDataAccessException erdae) {
			logger.error("EmptyResultDataAccessException in getStoreCodeByTeamCode()");
		} catch (Exception e) {
			logger.error("Exception caught in getStoreCodeByTeamCode():", e);
			throw new DAOException(e.getMessage(), e);
		}
		
		return null;
	}
	
	private static String getEventListSQL = "select event_cd from bomweb_event order by event_cd";
	
	public List<StockDTO> getEventList() throws DAOException {
		logger.info("getEventList @ MobDsStockDAO is called");
		List<StockDTO> eventList = new ArrayList<StockDTO>();
		
		ParameterizedRowMapper<StockDTO> mapper = new ParameterizedRowMapper<StockDTO>() {
			public StockDTO mapRow(ResultSet rs, int row) throws SQLException {
				StockDTO dto = new StockDTO();
				dto.setEventCode(rs.getString("event_cd"));
				return dto;
			}
		};
		
		try {
			logger.info("getEventList() @ MobDsStockDAO: "
				    + getEventListSQL);
			
			eventList = simpleJdbcTemplate.query(getEventListSQL, mapper);
			
		} catch (EmptyResultDataAccessException erdae) {
		    logger.error("EmptyResultDataAccessException in getEventList()");

		    eventList = null;
		} catch (Exception e) {
		    logger.error("Exception caught in getEventList():", e);

		    throw new DAOException(e.getMessage(), e);
		}
		return eventList;
	}
	
	private static String getStoreListSQL = 
		"SELECT DISTINCT centre_cd FROM bomweb_shop " +
		"WHERE sales_channel = decode(?, 'MOB', sales_channel, ?) " +
		"AND channel_id = 10 " +
		"ORDER BY centre_cd";
	
	public List<StockDTO> getStoreList(String channelCd) throws DAOException {
		logger.info("getStoreList @ MobDsStockDAO is called");
		List<StockDTO> storeList = new ArrayList<StockDTO>();
		
		ParameterizedRowMapper<StockDTO> mapper = new ParameterizedRowMapper<StockDTO>() {
			public StockDTO mapRow(ResultSet rs, int row) throws SQLException {
				StockDTO dto = new StockDTO();
				dto.setStoreCode(rs.getString("centre_cd"));
				return dto;
			}
		};
		try {
			logger.info("getStoreListSQL() @ MobDsStockDAO: "
				    + getEventListSQL);
			storeList = simpleJdbcTemplate.query(getStoreListSQL, mapper, channelCd, channelCd);
		} catch (EmptyResultDataAccessException erdae) {
		    logger.error("EmptyResultDataAccessException in getStoreList()");
		    storeList = null;
		} catch (Exception e) {
		    logger.error("Exception caught in getStoreList():", e);
		    throw new DAOException(e.getMessage(), e);
		}
		return storeList;
	}
	
	private static String getTeamListSQL = 
			"SELECT DISTINCT centre_cd, shop_cd FROM bomweb_shop " +
			"WHERE sales_channel = decode(?, 'MOB', sales_channel, ?) " +
			"AND channel_id = 10 " +
			"ORDER BY centre_cd, shop_cd";
		
		public List<StockDTO> getTeamList(String channelCd) throws DAOException {
			logger.info("getTeamList @ MobDsStockDAO is called");
			List<StockDTO> teamList = new ArrayList<StockDTO>();
			
			ParameterizedRowMapper<StockDTO> mapper = new ParameterizedRowMapper<StockDTO>() {
				public StockDTO mapRow(ResultSet rs, int row) throws SQLException {
					StockDTO dto = new StockDTO();
					dto.setStoreCode(rs.getString("centre_cd"));
					dto.setTeamCode(rs.getString("shop_cd"));
					return dto;
				}
			};
			try {
				logger.info("getTeamListSQL() @ MobDsStockDAO: "
					    + getEventListSQL);
				teamList = simpleJdbcTemplate.query(getTeamListSQL, mapper, channelCd, channelCd);
			} catch (EmptyResultDataAccessException erdae) {
			    logger.error("EmptyResultDataAccessException in getTeamList()");
			    teamList = null;
			} catch (Exception e) {
			    logger.error("Exception caught in getTeamList():", e);
			    throw new DAOException(e.getMessage(), e);
			}
			return teamList;
		}
	
	public int updateDSStockInventory(StockUpdateDTO dto, String originalStatus, String username)
			throws DAOException {
		logger.info("updateDSStockInventory @ MobDsStockDAO is called");
		
		String sql = "update bomweb_stock_inventory " +
				"set status_id = ?, " +
				"event_cd = ?, " +
				"store_cd = ?, " +
				"team_cd = ?, " +
				"staff_id = ?, " +
				"last_upd_by = ?, " +
				"last_upd_date = sysdate, " +
				"remarks = ?" +
				", stock_pool = ? " +
				"where item_serial = ? " +
				"and   item_code = ?";

		try {
			logger.info("updateDSStockInventory() @ MobDsStockDAO: "
				    + sql);
			
			 int updateResult = simpleJdbcTemplate.update(sql, 
					dto.getStatus(), 
					dto.getEventCode(),
					dto.getStoreCode(),
					dto.getTeamCode(),
					dto.getStaffId(),
					username,
					dto.getRemarks(),
					dto.getStockPool(),
					dto.getImei(), 
					dto.getItemCode()
					);
			 
			 if (updateResult == 1 && !originalStatus.equals(dto.getStatus())) {
				 int histResult = insertDSStockInventoryHistory(dto, originalStatus, username);
				 return histResult;
			 }
			 
			 return updateResult;
		} catch (Exception e) {
			logger.error("Exception caught in updateDSStockInventory():", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public int insertDSStockInventoryHistory(StockUpdateDTO dto, String originalStatus, String username)
			throws DAOException {
		logger.info("insertDSStockInventoryHistory @ MobDsStockDAO is called");
		
		String sql = "insert into bomweb_stock_inventory_hist " +
				" (item_code, item_serial, status_id_from, status_id_to, remarks, " +
				"  create_by, create_date, batch_ref_from, batch_ref_to, event_cd, store_cd, " +
				"  team_cd, book_out_date, staff_id) values (" +
				":itemCode, " +
				":imei, " +
				":statusIDFrom, " +
				":statusIDTo, " +
				":remarks, " +
				":createBy, " +
				"sysdate, " +
				":batchRefFrom, " +
				"'SBMOB', " +
				":eventCode, " +
				":storeCode, " +
				":teamCode, " +
				":bookOutDate, " +
				":staffID) ";

		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("itemCode", dto.getItemCode());
		params.addValue("imei", dto.getImei());
		params.addValue("statusIDFrom", originalStatus);
		params.addValue("statusIDTo", dto.getStatus());
		params.addValue("remarks", dto.getRemarks());
		params.addValue("createBy", username);
		params.addValue("batchRefFrom", this.getBatchRef(dto.getImei()));
		params.addValue("eventCode", dto.getEventCode());
		params.addValue("storeCode", dto.getStoreCode());
		params.addValue("teamCode", dto.getTeamCode());
		params.addValue("bookOutDate", this.getBookOutDate(dto.getImei()));
		params.addValue("staffID", dto.getStaffId());
		
		try {
			logger.info("insertDSStockInventoryHistory() @ MobDsStockDAO: "
				    + sql);
			return simpleJdbcTemplate.update(sql, params);

		} catch (Exception e) {
			logger.error("Exception caught in insertDSStockInventoryHistory():", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public Date getBookOutDate(String imei) throws DAOException {
		logger.info("getBookOutDate @ MobDsStockDAO is called");
		
		String sql = "select distinct book_out_date from bomweb_stock_inventory "
				 + "where item_serial = :imei ";

		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("imei", imei);
		ParameterizedRowMapper<Date> mapper = new ParameterizedRowMapper<Date>() {
			public Date mapRow(ResultSet rs, int row) throws SQLException {
				return rs.getDate("book_out_date");
			}
		};
		try {
			logger.info("getBookOutDate() @ MobDsStockDAO: "
				    + sql);
			
			List<Date> result = simpleJdbcTemplate.query(sql, mapper, params);
			
			if (result == null || result.isEmpty()) {
				return null;
			} else {
				return result.get(0);
			}
		} catch (EmptyResultDataAccessException erdae) {
			logger.error("EmptyResultDataAccessException in getBookOutDate()");
		} catch (Exception e) {
			logger.error("Exception caught in getBookOutDate():", e);
			throw new DAOException(e.getMessage(), e);
		}
		return null;
	}
	
	public String getBatchRef(String imei) throws DAOException {
		logger.info("getBatchRef @ MobDsStockDAO is called");
		
		String sql = "select distinct batch_ref from bomweb_stock_inventory "
				 + "where item_serial = :imei ";

		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("imei", imei);
		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
			public String mapRow(ResultSet rs, int row) throws SQLException {
				return rs.getString("batch_ref");
			}
		};
		try {
			logger.info("getBatchRef() @ MobDsStockDAO: "
				    + sql);
			
			List<String> result = simpleJdbcTemplate.query(sql, mapper, params);
			
			if (result == null || result.isEmpty()) {
				return null;
			} else {
				return result.get(0);
			}
		} catch (EmptyResultDataAccessException erdae) {
			logger.error("EmptyResultDataAccessException in getBatchRef()");
		} catch (Exception e) {
			logger.error("Exception caught in getBatchRef():", e);
			throw new DAOException(e.getMessage(), e);
		}
		return null;
	}
	
	private final String getDsStockQuantityEnquirySQL 
	= "SELECT SI.STOCK_POOL, SI.ITEM_TYPE, SI.ITEM_DESC, SI.ITEM_CODE, nvl(CNT.SOS,0) SOS_COUNT, " +
			"nvl(CNT.RSS,0) RSS_COUNT, nvl(CNT.RWH,0) RWH_COUNT, " +
			"nvl(CNT.ASSIGN,0) ASSIGN_COUNT, nvl(CNT.SOLD,0) SOLD_COUNT " +
			"FROM " +
			"(SELECT distinct bsc.item_code, item_lkup.code_desc item_type, bsc.item_desc, " +
			" stock_lkup.parm_value stock_pool  " +
			" FROM bomweb_stock_catalog bsc, bomweb_maint_parm_lkup stock_lkup, bomweb_code_lkup item_lkup  " +
			" WHERE bsc.item_type = item_lkup.code_id (+)  " +
			" AND item_lkup.code_type = 'STOCK_TYPE'  " +
			"  AND channel_cd='MOB'  " +
			" AND function_cd='STOCK QUANTITY ENQUIRY'  " +
			" AND parm_type='STOCK_POOL') SI,  " +
			"(SELECT item_code, " +
			"  SUM(sum(decode(status_id, '27', 1, 0))) OVER (PARTITION BY item_code) SOS, " +
			"  SUM(sum(decode(status_id, '28', 1, 0))) OVER (PARTITION BY item_code) RSS, " +
			"  SUM(sum(decode(status_id, '29', 1, 0))) OVER (PARTITION BY item_code) RWH, " +
			"  SUM(sum(decode(status_id, '19', 1, 0))) OVER (PARTITION BY item_code) ASSIGN, " +
			"  SUM(sum(decode(status_id, '20', 1, 0))) OVER (PARTITION BY item_code) SOLD " +
			"  FROM bomweb_stock_inventory bsi " /*+
			"  WHERE STORE_CD IN (SELECT DISTINCT centre_cd FROM bomweb_shop  " +
			"    WHERE sales_channel = DECODE(:channelCd, 'MOB', sales_channel, :channelCd)  " +
			"  AND store_cd = nvl(:storeCode, store_cd))  " +
			"  AND team_cd = nvl(:teamCode, team_cd)  " +
			"  AND nvl(staff_id, '#') = nvl(:staffId, nvl(staff_id, '#')) " +
			"  AND nvl(event_cd, '#') = nvl(:eventCode, nvl(event_cd, '#')) " +
			"  AND stock_pool = :stockPool " +
			"  GROUP BY item_code) CNT " +
			"WHERE SI.item_code = CNT.item_code "*/;
	
	public List<StockQuantityEnquiryDTO> getDsStockQuantityEnquiry(StockDTO dto)
		    throws DAOException {
			logger.info("getDsStockQuantityEnquiry @ MobDsStockDAO is called");
			
			List<StockQuantityEnquiryDTO> itemList = new ArrayList<StockQuantityEnquiryDTO>();
		
			ParameterizedRowMapper<StockQuantityEnquiryDTO> mapper = new ParameterizedRowMapper<StockQuantityEnquiryDTO>() {
			    public StockQuantityEnquiryDTO mapRow(ResultSet rs, int rowNum)
				    throws SQLException {
		
				StockQuantityEnquiryDTO dto = new StockQuantityEnquiryDTO();
		
				dto.setType(rs.getString("ITEM_TYPE"));
				dto.setItemDesc(rs.getString("ITEM_DESC"));
				dto.setItemCode(rs.getString("ITEM_CODE"));
				dto.setSos(rs.getString("SOS_COUNT"));
				dto.setRss(rs.getString("RSS_COUNT"));
				dto.setRwh(rs.getString("RWH_COUNT"));
				dto.setAssign(rs.getString("ASSIGN_COUNT"));
				dto.setSold(rs.getString("SOLD_COUNT"));
		
				return dto;
			    }
			};
		
			try {
		
			    logger.info("getDsStockQuantityEnquiry() @ MobDsStockDAO: "
				    + getDsStockQuantityEnquirySQL);
			    
			    MapSqlParameterSource params = new MapSqlParameterSource();
			    
			    StringBuilder sql = new StringBuilder(getDsStockQuantityEnquirySQL);
			    
			    if (dto.getChannelId() == 10) {
			    	if ((dto.getStoreCode() == null || "".equalsIgnoreCase(dto.getStoreCode())) && 
			    			(dto.getTeamCode() == null || "".equalsIgnoreCase(dto.getTeamCode()))) {
			    		sql.append(" WHERE bsi.team_cd IN (select distinct bsa.team_cd from bomweb_sales_assignment bsa "
								+ "WHERE bsa.staff_id = :staffId "
								+ "AND trunc(sysdate) BETWEEN bsa.start_date AND NVL(bsa.end_date, trunc(sysdate)) "
								+ "AND channel_id = 10) ");
			    	} else if ((dto.getStoreCode() != null && !"".equalsIgnoreCase(dto.getStoreCode())) && 
			    			(dto.getTeamCode() == null || "".equalsIgnoreCase(dto.getTeamCode()))) {
			    		sql.append(" WHERE bsi.team_cd IN (select distinct bsa.team_cd from bomweb_sales_assignment bsa "
								+ "where bsa.staff_id = :staffId "
								+ "AND trunc(sysdate) BETWEEN bsa.start_date AND NVL(bsa.end_date, trunc(sysdate))"
								+ "AND bsa.centre_cd = :storeCode "
								+ "AND channel_id = 10) ");
			    	} else {
			    		sql.append("WHERE bsi.store_cd = :storeCode ");
			    		sql.append("AND bsi.team_cd = :teamCode ");
			    	}
	    		} else {
	    			if (dto.getStoreCode() == null || "".equalsIgnoreCase(dto.getStoreCode())) {
	    				sql.append("WHERE bsi.store_cd in (" +
	    						"SELECT distinct bs.centre_cd from bomweb_sales_assignment bsa, bomweb_shop bs " +
	    						"WHERE bsa.staff_id = :staffId " +
								"AND trunc(sysdate) BETWEEN bsa.start_date AND NVL(bsa.end_date, trunc(sysdate)) " +
								"AND bs.sales_channel = decode(bsa.team_cd, 'MOB', bs.sales_channel, bsa.team_cd) " +
								"AND bs.channel_id = 10) ");
	    			} else if ((dto.getStoreCode() != null && !"".equalsIgnoreCase(dto.getStoreCode())) && 
			    			(dto.getTeamCode() == null || "".equalsIgnoreCase(dto.getTeamCode()))) {
	    				sql.append("WHERE bsi.store_cd = :storeCode ");
	    			} else {
	    				sql.append("WHERE bsi.store_cd = :storeCode ");
			    		sql.append("AND bsi.team_cd = :teamCode ");
	    			}
	    		}
			    params.addValue("staffId", dto.getStaffId());
			    params.addValue("storeCode", dto.getStoreCode());
				params.addValue("teamCode", dto.getTeamCode());
			    
				if ("FRONTLINE".equalsIgnoreCase(dto.getCategory())) {
			    	sql.append(" AND bsi.staff_id = :staffId ");
				}
				
				if (dto.getEventCode() != null && !"".equalsIgnoreCase(dto.getEventCode())) {
					sql.append(" AND bsi.event_cd = :eventCode ");
			    	params.addValue("eventCode", dto.getEventCode());
				}
				
				sql.append("  AND stock_pool = :stockPool " +
				"  GROUP BY item_code) CNT " +
				"WHERE SI.item_code = CNT.item_code ");
				params.addValue("stockPool", dto.getStockPool());
				
			    if (dto.getType() != null && !"".equals(dto.getType())) {
			    	sql.append("AND SI.ITEM_TYPE = (SELECT code_desc FROM bomweb_code_lkup " +
			    			"WHERE code_id = :type AND code_type = 'STOCK_TYPE') ");
			    	params.addValue("type", dto.getType());
			    }
			    if (dto.getItemCode() != null && !"".equals(dto.getItemCode())) {
			    	sql.append("AND SI.ITEM_CODE = :itemCode ");
			    	params.addValue("itemCode", dto.getItemCode());
			    }
			    if (dto.getModel() != null && !"".equals(dto.getModel())) {
			    	sql.append("AND upper(SI.item_desc) LIKE upper(:model) ");
			    	params.addValue("model", "%" + dto.getModel() + "%");
			    }
			    sql.append("ORDER BY SI.item_type, SI.item_code ");
			    
			    itemList = simpleJdbcTemplate.query(sql.toString(), mapper, params);
			} catch (EmptyResultDataAccessException erdae) {
			    logger.error("EmptyResultDataAccessException in getDsStockQuantityEnquiry()");
		
			    itemList = null;
			} catch (BadSqlGrammarException bsge) {
			    logger.error("BadSqlGrammarException in getDsStockQuantityEnquiry()",
				    bsge);
		
			    itemList = null;
			} catch (Exception e) {
			    logger.error("Exception caught in getDsStockQuantityEnquiry():", e);
		
			    throw new DAOException(e.getMessage(), e);
			}
			return itemList;
	    }
	
	public boolean validateStaff (String staffID, String storeCode, String teamCode) throws DAOException {
		logger.info("validateStaff @ MobDsStockDAO is called");
		
		String sql = "select staff_id from bomweb_sales_assignment "
					 + "where staff_id = :staffID "
					 + "and centre_cd = :storeCode "
					 + "and team_cd = :teamCode ";

		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("staffID", staffID);
		params.addValue("storeCode", storeCode);
		params.addValue("teamCode", teamCode);
		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
			public String mapRow(ResultSet rs, int row) throws SQLException {
				return rs.getString("staff_id");
			}
		};
		try {
			logger.info("validateStaff() @ MobDsStockDAO: "
				    + sql);
			
			List<String> result = simpleJdbcTemplate.query(sql, mapper, params);
			
			if (result == null || result.isEmpty()) {
				return false;
			} else {
				return !result.get(0).isEmpty();
			}
			
		} catch (EmptyResultDataAccessException erdae) {
			logger.error("EmptyResultDataAccessException in validateStaff()");
		} catch (Exception e) {
			logger.error("Exception caught in validateStaff():", e);
			throw new DAOException(e.getMessage(), e);
		}
		
		return false;
	}

	private static String updateSalesShopCodeSQL = "UPDATE bomweb_sales_assignment " +
			"SET centre_cd = :centreCode, " +
			"team_cd = :teamCode " +
			"WHERE staff_id = :staffId " +
			"AND (end_date is null OR end_date >= sysdate) ";

    public int updateSalesShopCode(String staffId, String storeCode, String teamCode)
	    throws DAOException {
		logger.info("updateSalesShopCode @ MobDsStockDAO is called");
	
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("staffId", staffId);
		params.addValue("centreCode", storeCode);
		params.addValue("teamCode", teamCode);
		
		try {
		    logger.info("updateSalesShopCode() @ StockDAO: " +
		    		updateSalesShopCodeSQL);
		    return simpleJdbcTemplate.update(updateSalesShopCodeSQL, params);
		} catch (Exception e) {
		    logger.info("Exception caught in updateSalesShopCode():", e);
	
		    throw new DAOException(e.getMessage(), e);
		}
    }
	
}
