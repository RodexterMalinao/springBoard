package com.bomwebportal.mob.ccs.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.bomwebportal.dao.BaseDAO;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.mob.ccs.dto.MaintParmLkupDTO;
import com.bomwebportal.mob.ccs.dto.MrtInventoryDTO;

public class MrtInventoryDAO extends BaseDAO {
    protected final Log logger = LogFactory.getLog(getClass());
    private static String getMrtInventoryDTOSQL = "SELECT \n"
	    + "     SRV_NUM_TYPE,\n" + "     MSISDN,\n" + "     MSISDNLVL,\n"
	    + "     MSISDN_STATUS,\n" + "     CITY_CD,\n"
	    + "     CHANNEL_CD,\n"
	    + "     STOCK_IN_DATE,\n" + "     CREATED_BY,\n"
	    + "     CREATED_DATE,\n" + "     LAST_UPD_BY,\n"
	    + "     LAST_UPD_DATE\n"
	    + "     , rowid ROW_ID \n"
	    + "     , reserve_id \n"	//added by Fiona Chan 20131015
	    + "     , res_oper_id \n"	//added by Fiona Chan 20131015
	    + "     , num_type \n"
	    + " from BOMWEB_MRT_INVENTORY Where rowid=?\n";

    public MrtInventoryDTO getMrtInventoryDTO(String rowId)
	    throws DAOException {
		logger.info(" getMrtInventoryDTO is called");
		List<MrtInventoryDTO> itemList = new ArrayList<MrtInventoryDTO>();
		/**** ==ParameterizedRowMapper start== *********************************************************/
		ParameterizedRowMapper<MrtInventoryDTO> mapper =  this.getRowMapper();
		/**** ==ParameterizedRowMapper end== *********************************************************/
		try {
		    logger.info("getMrtInventoryDTO() @ MrtInventoryDTO: " + getMrtInventoryDTOSQL);
		    itemList = simpleJdbcTemplate.query(getMrtInventoryDTOSQL, mapper, rowId);
		} catch (EmptyResultDataAccessException erdae) {
		    logger.info("EmptyResultDataAccessException in getMrtInventoryDTO()");
	
		    itemList = null;
		} catch (Exception e) {
		    logger.info("Exception caught in getMrtInventoryDTO():", e);
	
		    throw new DAOException(e.getMessage(), e);
		}
		// only return the first one
		return isEmpty(itemList) ? null : itemList.get(0);
    }
    
    private static String getMrtInventoryDTOByOrderIdSQL = "SELECT \n"
    	    + "     d.SRV_NUM_TYPE,\n" + "     d.MSISDN,\n" + "     d.MSISDNLVL,\n"
    	    + "     d.MSISDN_STATUS,\n" + "     d.CITY_CD,\n"
    	    + "     d.CHANNEL_CD,\n"
    	    + "     d.STOCK_IN_DATE,\n" + "     d.CREATED_BY,\n"
    	    + "     d.CREATED_DATE,\n" + "     d.LAST_UPD_BY,\n"
    	    + "     d.LAST_UPD_DATE\n"
    	    + "     , d.rowid ROW_ID \n"
    	    + "     , d.reserve_id \n"	//added by Fiona Chan 20131015
    	    + "     , d.res_oper_id \n"	//added by Fiona Chan 20131015
    	    + "     , d.num_type \n"
    		+ " FROM bomweb_order a, bomweb_code_lkup b, bomweb_mrt_assgn c, BOMWEB_MRT_INVENTORY d\n"
    		+ " WHERE a.ORDER_ID = :orderId \n"
    		+ " AND a.ORDER_ID = c.ORDER_ID \n"
    		+ " AND c.stock_in_date = d.stock_in_date \n"
    		+ " AND b.code_type= :codeType \n"
    		+ " AND a.order_status=b.code_id \n" 
    		+ " AND d.CHANNEL_CD in (:channelCds)";

    public MrtInventoryDTO getMrtInventoryDTOByOrderId(String orderId, List<String> channelCds) throws DAOException {
		logger.info(" getMrtInventoryDTOByOrderId is called");
		List<MrtInventoryDTO> itemList = new ArrayList<MrtInventoryDTO>();
		/**** ==ParameterizedRowMapper start== *********************************************************/
		ParameterizedRowMapper<MrtInventoryDTO> mapper = this.getRowMapper();
		/**** ==ParameterizedRowMapper end== *********************************************************/
		try {
		    logger.info("getMrtInventoryDTOByOrderId() @ MrtInventoryOrderDTO: " + getMrtInventoryDTOByOrderIdSQL);
		    MapSqlParameterSource params = new MapSqlParameterSource();
		    params.addValue("orderId", orderId);
		    params.addValue("codeType", "ORDER_STS");
		    params.addValue("channelCds", channelCds);
		    itemList = simpleJdbcTemplate.query(getMrtInventoryDTOByOrderIdSQL, mapper, params);
		} catch (EmptyResultDataAccessException erdae) {
		    logger.info("EmptyResultDataAccessException in getMrtInventoryDTOByOrderId()");
	
		    itemList = null;
		} catch (Exception e) {
		    logger.info("Exception caught in getMrtInventoryDTOByOrderId():", e);
	
		    throw new DAOException(e.getMessage(), e);
		}
		return isEmpty(itemList) ? null : itemList.get(0);// only return the first one
	}
/*
    private static String getMrtInventoryDTOAllSQL = "SELECT \n"
	    + "     SRV_NUM_TYPE,\n" + "     MSISDN,\n" + "     MSISDNLVL,\n"
	    + "     MSISDN_STATUS,\n" + "     CITY_CD,\n"
	    + "     CHANNEL_CD,\n"
	    + "     STOCK_IN_DATE,\n" + "     CREATED_BY,\n"
	    + "     CREATED_DATE,\n" + "     LAST_UPD_BY,\n"
	    + "     LAST_UPD_DATE\n"
	    + "     , rowid ROW_ID \n"
	    + " from BOMWEB_MRT_INVENTORY\n" 
	    + " WHERE MSISDN = ? \n" 
	    + " AND CHANNEL_CD in (?) \n"
	    + " ORDER BY SRV_NUM_TYPE, MSISDNLVL, MSISDN_STATUS, CHANNEL_CD, MSISDN\n";
*/
    public List<MrtInventoryDTO> getMrtInventoryDTOALL(String msisdn, List<String> channelCds)
	    throws DAOException {
	logger.info(" getMrtInventoryDTOALL is called");
	List<MrtInventoryDTO> itemList = new ArrayList<MrtInventoryDTO>();
	/**** ==ParameterizedRowMapper start== *********************************************************/
	ParameterizedRowMapper<MrtInventoryDTO> mapper = this.getRowMapper();
	/**** ==ParameterizedRowMapper end== *********************************************************/
	String sql = "SELECT \n"
		    + "     SRV_NUM_TYPE,\n" + "     MSISDN,\n" + "     MSISDNLVL,\n"
		    + "     MSISDN_STATUS,\n" + "     CITY_CD,\n"
		    + "     CHANNEL_CD,\n"
		    + "     STOCK_IN_DATE,\n" + "     CREATED_BY,\n"
		    + "     CREATED_DATE,\n" + "     LAST_UPD_BY,\n"
		    + "     LAST_UPD_DATE\n"
		    + "     , rowid ROW_ID \n"
		    + "     , reserve_id \n"	//added by Fiona Chan 20131015
		    + "     , res_oper_id \n"	//added by Fiona Chan 20131015
		    + "     , num_type \n"
		    + " from BOMWEB_MRT_INVENTORY\n" 
		    + " WHERE MSISDN = :msisdn \n" ;
	try {
	    logger.info("getMrtInventoryDTOALL() @ MrtInventoryDTO: " + sql);
	    MapSqlParameterSource params = new MapSqlParameterSource();
	    params.addValue("msisdn", msisdn);
		if (channelCds != null && !channelCds.isEmpty()) {
		    sql += " AND CHANNEL_CD in (:channelCds) \n";
		    params.addValue("channelCds", channelCds);
		}
		sql += " ORDER BY SRV_NUM_TYPE, MSISDNLVL, MSISDN_STATUS, CHANNEL_CD, MSISDN\n";
	    itemList = simpleJdbcTemplate.query(sql, mapper, params);
	} catch (EmptyResultDataAccessException erdae) {
	    logger.info("EmptyResultDataAccessException in getMrtInventoryDTOALL()");

	    itemList = null;
	} catch (Exception e) {
	    logger.info("Exception caught in getMrtInventoryDTO():", e);

	    throw new DAOException(e.getMessage(), e);
	}
	return itemList;// only return the first one
    }

    private static String getMrtInventoryDTOBySearchSQL = "SELECT \n"
	    + "     SRV_NUM_TYPE,\n" + "     MSISDN,\n" + "     MSISDNLVL,\n"
	    + "     MSISDN_STATUS,\n" + "     CITY_CD,\n"
	    + "     CHANNEL_CD,\n"
	    + "     STOCK_IN_DATE,\n" + "     CREATED_BY,\n"
	    + "     CREATED_DATE,\n" + "     LAST_UPD_BY,\n"
	    + "     LAST_UPD_DATE\n"
	    + "     , rowid ROW_ID \n"
	    + "     , reserve_id \n"	//added by Fiona Chan 20131015
	    + "     , res_oper_id \n"	//added by Fiona Chan 20131015
	    + "     , num_type \n"
	    + " FROM BOMWEB_MRT_INVENTORY WHERE 1=1\n";

    public List<MrtInventoryDTO> getMrtInventoryDTOBySearch(MrtInventoryDTO dto, List<String> channelCds) throws DAOException {
		logger.info(" getMrtInventoryDTOBySearch is called");
		List<MrtInventoryDTO> itemList = new ArrayList<MrtInventoryDTO>();
		/**** ==ParameterizedRowMapper start== *********************************************************/
		ParameterizedRowMapper<MrtInventoryDTO> mapper = this.getRowMapper();
		/**** ==ParameterizedRowMapper end== *********************************************************/
		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			StringBuilder searchSQL = new StringBuilder(getMrtInventoryDTOBySearchSQL);
			if (StringUtils.isNotBlank(dto.getNumType())) {
				searchSQL.append(" AND NUM_TYPE = :numType\n");
				params.addValue("numType", dto.getNumType());
			}
			if (StringUtils.isNotBlank(dto.getSrvNumType())) {
				searchSQL.append(" AND SRV_NUM_TYPE = :srvNumType\n");
				params.addValue("srvNumType", dto.getSrvNumType());
			}
			if (StringUtils.isNotBlank(dto.getMsisdnlvl())) {
				searchSQL.append(" AND MSISDNLVL = :msisdnlvl\n");
				params.addValue("msisdnlvl", dto.getMsisdnlvl());
			}
			if (StringUtils.isBlank(dto.getChannelCd())) {
				searchSQL.append(" AND CHANNEL_CD IN (:channelCds)\n");
				params.addValue("channelCds", channelCds);
			} else {
				searchSQL.append(" AND CHANNEL_CD = :channelCd\n");
				params.addValue("channelCd", dto.getChannelCd());
			}
			if (dto.getMsisdnStatus() != null) {
				searchSQL.append(" AND MSISDN_STATUS = :msisdnStatus\n");
				params.addValue("msisdnStatus", dto.getMsisdnStatus());
			}
/*			if (StringUtils.isNotBlank(dto.getMsisdn())) {
				searchSQL.append(" AND MSISDN = :msisdn\n");
				params.addValue("msisdn", dto.getMsisdn());
			}*/
			searchSQL.append(" ORDER BY SRV_NUM_TYPE, MSISDNLVL, MSISDN_STATUS, CHANNEL_CD, MSISDN\n");
		    logger.info("getMrtInventoryDTOBySearch() @ MrtInventoryDTO: "
			    + searchSQL);
		    itemList = simpleJdbcTemplate.query(searchSQL.toString(), mapper, params);
		} catch (EmptyResultDataAccessException erdae) {
		    logger.info("EmptyResultDataAccessException in getMrtInventoryDTOBySearch()");
	
		    itemList = null;
		} catch (Exception e) {
		    logger.info("Exception caught in getMrtInventoryDTOBySearch():", e);
	
		    throw new DAOException(e.getMessage(), e);
		}
		return itemList;// only return the first one
    }
    private ParameterizedRowMapper<MrtInventoryDTO> getRowMapper() {
    	return new ParameterizedRowMapper<MrtInventoryDTO>() {
    	    public MrtInventoryDTO mapRow(ResultSet rs, int rowNum)
    		    throws SQLException {
    		MrtInventoryDTO dto = new MrtInventoryDTO();
    		dto.setNumType(rs.getString("NUM_TYPE"));
    		dto.setSrvNumType(rs.getString("SRV_NUM_TYPE"));
    		dto.setMsisdn(rs.getString("MSISDN"));
    		dto.setMsisdnlvl(rs.getString("MSISDNLVL"));
    		dto.setMsisdnStatus(rs.getInt("MSISDN_STATUS"));
    		dto.setCityCd(rs.getString("CITY_CD"));
    		dto.setChannelCd(rs.getString("CHANNEL_CD"));
    		dto.setStockInDate(rs.getTimestamp("STOCK_IN_DATE"));
    		dto.setCreatedBy(rs.getString("CREATED_BY"));
    		dto.setCreatedDate(rs.getTimestamp("CREATED_DATE"));
    		dto.setLastUpdBy(rs.getString("LAST_UPD_BY"));
    		dto.setLastUpdDate(rs.getTimestamp("LAST_UPD_DATE"));
    		dto.setRowId(rs.getString("ROW_ID"));
    		dto.setReserveId(rs.getString("RESERVE_ID"));
    		dto.setResOperId(rs.getString("RES_OPER_ID"));
    		return dto;
    	    }
    	};
    }
    // === Start of INSERT function ===
    private static final String insertMrtInventorySQL = " INSERT INTO BOMWEB_MRT_INVENTORY ( \n"
	    + "    SRV_NUM_TYPE,  \n"
	    + "    MSISDN,  \n"
	    + "    MSISDNLVL,  \n"
	    + "    MSISDN_STATUS,  \n"
	    + "    CITY_CD,  \n"
	    + "    CHANNEL_CD,  \n"
	    + "    STOCK_IN_DATE,  \n"
	    + "    RESERVE_ID,  \n" 			//added by Dennis 20131015
	    + "    RES_OPER_ID,  \n" 				//added by Dennis 20131015
	    + "    APPROVAL_SERIAL,  \n"		//added by Dennis 20131015
	    + "    REQUEST_ID,  \n"				//added by Dennis 20131015
	    + "    CREATED_BY,  \n"
	    + "    CREATED_DATE,  \n"
	    + "    LAST_UPD_BY,  \n"
	    + "    LAST_UPD_DATE,  \n"
	    + "    NUM_TYPE  \n"
	    + ") VALUES ( \n"
	    + "? , \n"
	    + "? , \n"
	    + "? , \n"
	    + "? , \n"
	    + "? , \n"
	    + "? , \n"
	    + "? , \n"
	    + "? , \n"
	    + "? , \n"
	    + "? , \n"
	    + "? , \n"
	    + "? , \n"
	    + "? , \n" 
	    + "? , \n" 
	    + "? , \n" 
	    + "?  \n" 
	    + " ) \n";

    public int insertMrtInventory(MrtInventoryDTO dto) throws DAOException {
	logger.info("insertMrtInventory is called");

	try {
	    logger.info("insertMrtInventory() @ MrtInventoryDAO: "
		    + insertMrtInventorySQL);
	    return simpleJdbcTemplate.update(
		    insertMrtInventorySQL,
		    // start sql mapping
		    dto.getSrvNumType(), dto.getMsisdn(), dto.getMsisdnlvl(),
		    dto.getMsisdnStatus(), dto.getCityCd(), dto.getChannelCd(),
		    dto.getStockInDate(), dto.getReserveId() , dto.getResOperId(),
		    dto.getApprovalSerial() , dto.getRequestId(),
		    dto.getCreatedBy(), dto.getCreatedDate(),
		    dto.getLastUpdBy(), dto.getLastUpdDate(), dto.getNumType()
	    // end sql mapping
		    );
	} catch (Exception e) {
	    logger.error("Exception caught in insertMrtInventory()", e);
	    throw new DAOException(e.getMessage(), e);
	}
    }

    // === End of INSERT function ===
    // === Start of UPDATE function ===
    private static final String updateMrtInventorySQL = " UPDATE BOMWEB_MRT_INVENTORY SET \n"
	    + "     SRV_NUM_TYPE = ? ,\n"
	    + "     MSISDN = ? ,\n"
	    + "     MSISDNLVL = ? ,\n"
	    + "     MSISDN_STATUS = ? ,\n"
	    + "     CITY_CD = ? ,\n"
	    + "     CHANNEL_CD = ? ,\n"
	    + "     STOCK_IN_DATE = ? ,\n"
	    + "     CREATED_BY = ? ,\n"
	    + "     CREATED_DATE = ? ,\n"
	    + "     LAST_UPD_BY = ? ,\n"
	    + "     LAST_UPD_DATE = ? \n" + " WHERE  rowid = ? \n";

    public int updateMrtInventory(MrtInventoryDTO dto) throws DAOException {
	logger.info("updateMrtInventory is called");

	try {
	    logger.info("updateMrtInventory() @ MrtInventoryDAO: "
		    + updateMrtInventorySQL);
	    return simpleJdbcTemplate.update(
		    updateMrtInventorySQL,
		    // start sql mapping
		    dto.getSrvNumType(), dto.getMsisdn(), dto.getMsisdnlvl(),
		    dto.getMsisdnStatus(), dto.getCityCd(), dto.getChannelCd(),
		    dto.getStockInDate(),
		    dto.getCreatedBy(), dto.getCreatedDate(),
		    dto.getLastUpdBy(), dto.getLastUpdDate(), dto.getRowId());

	} catch (Exception e) {
	    logger.error("Exception caught in updateMrtInventory()", e);
	    throw new DAOException(e.getMessage(), e);
	}

    }

    // === End of UPDATE function ===
    // === Start of DELETE function ===
    private static final String deleteMrtInventorySQL = "  delete from BOMWEB_MRT_INVENTORY where msisdn= ?";

    public int deleteMrtInventory(MrtInventoryDTO dto) throws DAOException {
	logger.info("deleteMrtInventory is called");

	try {
	    logger.info("deleteMrtInventory() @ MrtInventoryDAO: "
		    + deleteMrtInventorySQL);
	    return simpleJdbcTemplate.update(deleteMrtInventorySQL,
		    dto.getMsisdn());
	} catch (Exception e) {
	    logger.error("Exception caught in deleteMrtInventory()", e);
	    throw new DAOException(e.getMessage(), e);
	}
    }
    // === End of DELETE function ===

    
    
 // === Start of UPDATE function ===
    private static final String updateMrtInventoryStatusSQL = 
    	"UPDATE bomweb_mrt_inventory " 
        +"SET msisdn_status = '18', " 
        +"  last_upd_by     = ?, " 
        +"  last_upd_date   = sysdate " 
        +"WHERE msisdn      = ?";

    public int updateMrtInventoryStatus(MrtInventoryDTO dto) throws DAOException {
	logger.info("updateMrtInventoryStatus is called");

	try {
	    logger.info("updateMrtInventoryStatus() @ MrtInventoryDAO: "
		    + updateMrtInventoryStatusSQL);
	    return simpleJdbcTemplate.update(
	    		updateMrtInventoryStatusSQL,
		    // start sql mapping
		    dto.getLastUpdBy(), dto.getMsisdn());

	} catch (Exception e) {
	    logger.error("Exception caught in updateMrtInventoryStatus()", e);
	    throw new DAOException(e.getMessage(), e);
	}

    }

    // === End of UPDATE function ===
    
    private static String getStsFmMRTSQL = "SELECT MSISDN_STATUS from BOMWEB_MRT_INVENTORY Where MSISDN=?";

    public String getStsFmMRT(String msisdn) throws DAOException {
		logger.info(" getStsFmMRT is called");
		List<String> itemList = new ArrayList<String>();
		
		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {

			public String mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return rs.getString("msisdn_status");
			}
		};
		
		try {
		    logger.info("getStsFmMRT() @ MrtInventoryDTO: " + getStsFmMRTSQL);
		    itemList = simpleJdbcTemplate.query(getStsFmMRTSQL, mapper, msisdn);
		} catch (EmptyResultDataAccessException erdae) {
		    logger.info("EmptyResultDataAccessException in getStsFmMRT()");
	
		    itemList = null;
		} catch (Exception e) {
		    logger.info("Exception caught in getStsFmMRT():", e);
	
		    throw new DAOException(e.getMessage(), e);
		}
		
		return isEmpty(itemList) ? "" : itemList.get(0);
    }
    
    
// === Start of Search MRT function ===    //added by Dennis 20131015
 
    
    private static String getMrtInventoryByMrtSQL = "SELECT" +
			" SRV_NUM_TYPE, " +
    		" MSISDN, " +
			" MSISDNLVL, " +
    		" MSISDN_STATUS, " +
			" CHANNEL_CD, " +
    		" STOCK_IN_DATE, " +
			" RESERVE_ID, " +
    		" RES_OPER_ID, " +
			" APPROVAL_SERIAL, " +
    		" REQUEST_ID " +
			" FROM BOMWEB_MRT_INVENTORY" +
			" WHERE MSISDN = :msisdn" +
			" AND (msisdn_status = 18 or msisdn_status = 2)";
	
    public MrtInventoryDTO getMrtInventoryByMrt(String msisdn) throws DAOException {
		logger.info("getMrtInventoryByMrt is called");
		
		List<MrtInventoryDTO> mrtInventoryDTO = new ArrayList<MrtInventoryDTO>();
		
		if (logger.isDebugEnabled()) {
			logger.debug(getMrtInventoryByMrtSQL);
			logger.debug("msisdn: " + msisdn);
		}
		
		ParameterizedRowMapper<MrtInventoryDTO> mapper = new ParameterizedRowMapper<MrtInventoryDTO>() {
			public MrtInventoryDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				MrtInventoryDTO mrtInventoryDTO = new MrtInventoryDTO();
				mrtInventoryDTO.setSrvNumType(rs.getString("SRV_NUM_TYPE"));
				mrtInventoryDTO.setMsisdn(rs.getString("MSISDN"));
				mrtInventoryDTO.setMsisdnlvl(rs.getString("MSISDNLVL"));
				mrtInventoryDTO.setMsisdnStatus(Integer.parseInt((rs.getString("MSISDN_STATUS"))));
				mrtInventoryDTO.setChannelCd(rs.getString("CHANNEL_CD"));
				mrtInventoryDTO.setStockInDate(rs.getTimestamp("STOCK_IN_DATE"));
				mrtInventoryDTO.setReserveId(rs.getString("RESERVE_ID"));
				mrtInventoryDTO.setResOperId(rs.getString("RES_OPER_ID"));
				mrtInventoryDTO.setApprovalSerial(rs.getString("APPROVAL_SERIAL"));
				mrtInventoryDTO.setRequestId(rs.getString("REQUEST_ID"));
				
				
				return mrtInventoryDTO;
			}
		};
		
		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("msisdn", msisdn);
			mrtInventoryDTO = simpleJdbcTemplate.query(getMrtInventoryByMrtSQL, mapper, params);
			
		} catch (EmptyResultDataAccessException erdae) {
			if (logger.isDebugEnabled()) {
				logger.debug("EmptyResultDataAccessException in getMrtInventoryByMrt()");
			}
			return null;
		} catch (Exception e) {
			logger.info("Exception caught in getMrtInventoryByMrt():", e);
			throw new DAOException(e.getMessage(), e);
		}
		if (mrtInventoryDTO == null || mrtInventoryDTO.isEmpty()) {
    		return null;
    	}
		return mrtInventoryDTO.get(0);
	}
    
    
    
   
    private static String getMrtInventoryByRequestIdSQL = "SELECT" +
			" SRV_NUM_TYPE, " +
    		" MSISDN, " +
			" MSISDNLVL, " +
    		" MSISDN_STATUS, " +
			" CHANNEL_CD, " +
    		" STOCK_IN_DATE, " +
			" RESERVE_ID, " +
    		" RES_OPER_ID, " +
			" APPROVAL_SERIAL, " +
    		" REQUEST_ID " +
			" FROM BOMWEB_MRT_INVENTORY" +
			" WHERE REQUEST_ID = :requestId " +
    		" AND (MSISDN_STATUS = '2' OR MSISDN_STATUS = '18') \n";
	
	
	public MrtInventoryDTO getMrtInventoryByRequestId(String requestId) throws DAOException {
		logger.info("getMrtInventoryByRequestId is called");
		
		List<MrtInventoryDTO> mrtInventoryDTO = new ArrayList<MrtInventoryDTO>();
		
		if (logger.isDebugEnabled()) {
			logger.debug(getMrtInventoryByRequestIdSQL);
			logger.debug("requestId: " + requestId);
		}
		
		ParameterizedRowMapper<MrtInventoryDTO> mapper = new ParameterizedRowMapper<MrtInventoryDTO>() {
			public MrtInventoryDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				MrtInventoryDTO mrtInventoryDTO = new MrtInventoryDTO();
				mrtInventoryDTO.setSrvNumType(rs.getString("SRV_NUM_TYPE"));
				mrtInventoryDTO.setMsisdn(rs.getString("MSISDN"));
				mrtInventoryDTO.setMsisdnlvl(rs.getString("MSISDNLVL"));
				mrtInventoryDTO.setMsisdnStatus(Integer.parseInt((rs.getString("MSISDN_STATUS"))));
				mrtInventoryDTO.setChannelCd(rs.getString("CHANNEL_CD"));
				mrtInventoryDTO.setStockInDate(rs.getTimestamp("STOCK_IN_DATE"));
				mrtInventoryDTO.setReserveId(rs.getString("RESERVE_ID"));
				mrtInventoryDTO.setResOperId(rs.getString("RES_OPER_ID"));
				mrtInventoryDTO.setApprovalSerial(rs.getString("APPROVAL_SERIAL"));
				mrtInventoryDTO.setRequestId(rs.getString("REQUEST_ID"));
				
				
				return mrtInventoryDTO;
			}
		};
		
		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("requestId", requestId);
			mrtInventoryDTO = simpleJdbcTemplate.query(getMrtInventoryByRequestIdSQL, mapper, params);
			
		} catch (EmptyResultDataAccessException erdae) {
			if (logger.isDebugEnabled()) {
				logger.debug("EmptyResultDataAccessException in getMrtInventoryByRequestId()");
			}
			return null;
		} catch (Exception e) {
			logger.info("Exception caught in getMrtInventoryByRequestId():", e);
			throw new DAOException(e.getMessage(), e);
		}
		if (mrtInventoryDTO == null || mrtInventoryDTO.isEmpty()) {
    		return null;
    	}
		return mrtInventoryDTO.get(0);
	}
    
    
 // === End of Search MRT function === 
    
 // === Start of UPDATE function ===    	//added by Dennis 20131015
    private static final String updateMrtInventoryByRequestIdSQL = " UPDATE BOMWEB_MRT_INVENTORY SET \n"
	    + "     SRV_NUM_TYPE = :srvNumType ,\n"
	    + "     MSISDN = :msisdn ,\n"
	    + "     MSISDNLVL = :msisdnlvl ,\n"
	    + "     MSISDN_STATUS = :msisdnStatus ,\n"
	    + "     CITY_CD = :cityCd ,\n"
	    + "     CHANNEL_CD = :channelCd ,\n"
	   	+ "     RESERVE_ID = :reserveId , \n"
	    + "     RES_OPER_ID = :resOperId , \n"
	    + "     APPROVAL_SERIAL = :approvalSerial , \n"
	   	+ "     REQUEST_ID = :requestId ,\n"
	    + "     LAST_UPD_BY = :lastUpdBy ,\n"
	    + "     LAST_UPD_DATE = sysdate \n" + " WHERE  REQUEST_ID = :requestId \n"
		+ "     AND (MSISDN_STATUS = '2' OR MSISDN_STATUS = '18') \n";

    public int updateMrtInventoryByRequestId(MrtInventoryDTO dto) throws DAOException {
    	logger.info("updateMrtInventoryByRequestId is called");

	
    	try {
    		if (logger.isInfoEnabled()) {
    			logger.info(updateMrtInventoryByRequestIdSQL);
    		}
    		MapSqlParameterSource params = new MapSqlParameterSource();
    		params.addValue("srvNumType", dto.getSrvNumType());		
    		params.addValue("msisdn", dto.getMsisdn());
    		params.addValue("msisdnlvl", dto.getMsisdnlvl());		
    		params.addValue("msisdnStatus", dto.getMsisdnStatus());
    		params.addValue("cityCd", dto.getCityCd());   		
    		params.addValue("channelCd", dto.getChannelCd());
    		params.addValue("reserveId", dto.getReserveId());
    		params.addValue("resOperId", dto.getResOperId());
    		params.addValue("approvalSerial", dto.getApprovalSerial());
    		params.addValue("requestId", dto.getRequestId());
    		params.addValue("lastUpdBy", dto.getLastUpdBy());
    		return simpleJdbcTemplate.update(updateMrtInventoryByRequestIdSQL, params);
    	} catch (Exception e) {
    		if (logger.isErrorEnabled()) {
    			logger.error("Exception caught in updateMrtInventoryByRequestId()", e);
    		}
    		throw new DAOException(e.getMessage(), e);
    	}
    }

    private static final String updateMrtInventoryByMrtSQL = " UPDATE BOMWEB_MRT_INVENTORY SET \n"
    	    + "     SRV_NUM_TYPE = :srvNumType ,\n"
    	    + "     MSISDN = :msisdn ,\n"
    	    + "     MSISDNLVL = :msisdnlvl ,\n"
    	    + "     MSISDN_STATUS = :msisdnStatus ,\n"
    	    + "     CITY_CD = :cityCd ,\n"
    	    /*+ "     CHANNEL_CD = :channelCd ,\n"*/
    	    + "     RESERVE_ID = :reserveId , \n"
    	    + "     RES_OPER_ID = :resOperId , \n"
    	    + "     APPROVAL_SERIAL = :approvalSerial , \n"
    	   	+ "     REQUEST_ID = :requestId ,\n"
    	    + "     LAST_UPD_BY = :lastUpdBy ,\n"
    	    + "     LAST_UPD_DATE = sysdate \n" + " WHERE  MSISDN = :msisdn \n"
    		+ "     AND (MSISDN_STATUS = '2' OR MSISDN_STATUS = '18') \n";
    
    
    public int updateMrtInventoryByMrt(MrtInventoryDTO dto) throws DAOException {
    	logger.info("updateMrtInventoryByMrt is called");
    	try {
    		if (logger.isInfoEnabled()) {
    			logger.info(updateMrtInventoryByMrtSQL);
    		}
    		MapSqlParameterSource params = new MapSqlParameterSource();
    		params.addValue("srvNumType", dto.getSrvNumType());		
    		params.addValue("msisdn", dto.getMsisdn());
    		params.addValue("msisdnlvl", dto.getMsisdnlvl());		
    		params.addValue("msisdnStatus", dto.getMsisdnStatus());
    		params.addValue("cityCd", dto.getCityCd());   		
    		/*params.addValue("channelCd", dto.getChannelCd());*/
    		params.addValue("reserveId", dto.getReserveId());
    		params.addValue("resOperId", dto.getResOperId());
    		params.addValue("approvalSerial", dto.getApprovalSerial());
    		params.addValue("requestId", dto.getRequestId());
    		params.addValue("lastUpdBy", dto.getLastUpdBy());
    		return simpleJdbcTemplate.update(updateMrtInventoryByMrtSQL, params);
    	} catch (Exception e) {
    		if (logger.isErrorEnabled()) {
    			logger.error("Exception caught in updateMrtInventoryByMrt()", e);
    		}
    		throw new DAOException(e.getMessage(), e);
    	}

    }
    
    private static final String updateMrtInventoryStatusByMrtSQL = 
        	"UPDATE bomweb_mrt_inventory " 
            +"SET msisdn_status = :msisdnStatus, "
           // +"  request_id = null, "
            +"  last_upd_by     = :staffId, " 
            +"  last_upd_date   = sysdate " 
            +"	WHERE msisdn      = :msisdn"
            +" 	and request_id = :requestId"
        	+"  AND (MSISDN_STATUS = '2' OR MSISDN_STATUS = '18') \n";

   public int updateMrtInventoryStatusByMrt(String msisdnStatus, String staffId, String msisdn, String requestId) throws DAOException {
		if (logger.isInfoEnabled()) {
			logger.info("updateMrtInventoryStatusByMrt @ MrtInventoryDAO is called");
		}
		try {
			if (logger.isInfoEnabled()) {
				logger.info(updateMrtInventoryStatusByMrtSQL);
			}
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("msisdnStatus", msisdnStatus);		
			params.addValue("staffId", staffId);
			params.addValue("msisdn", msisdn);
			params.addValue("requestId", requestId);
			return simpleJdbcTemplate.update(updateMrtInventoryStatusByMrtSQL, params);
		} catch (Exception e) {
			if (logger.isErrorEnabled()) {
				logger.error("Exception caught in updateMrtInventoryStatusByMrt()", e);
			}
			throw new DAOException(e.getMessage(), e);
		}
   }
    
    
    // === End of UPDATE function ===
    
   private static final String getParmControlSQL = 
		   "select control.channel_cd, control.lob, control.function_cd, control.parm_type, control.parm_value "
		   + "from BOMWEB_MAINT_PARM_LKUP parm "
		   + "join BOMWEB_MAINT_PARM_LKUP control "
		   + "on parm.parm_type = control.parm_type "
		   + "and parm.parm_value = control.parm_value "
		   + "and parm.lob = control.lob "
		   + "where control.lob = 'MOB' "
		   + "and parm.channel_cd = :channelCd "
		   + "and parm.function_cd = :functionCd "
		   + "and control.function_cd = :controlFunctionCd "
		   + "and control.parm_type = :parmType "
		   + "and control.channel_cd = :controlType";

   public List<MaintParmLkupDTO> getParmControl(String channelCd, String controlType, String functionCd, String controlFunctionCd, String parmType) throws DAOException {
		if (logger.isInfoEnabled()) {
			logger.info("updateMrtInventoryStatusByMrt @ MrtInventoryDAO is called");
		}
		
		ParameterizedRowMapper<MaintParmLkupDTO> mapper = new ParameterizedRowMapper<MaintParmLkupDTO>() {
			public MaintParmLkupDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				MaintParmLkupDTO maintParmLkupDTO = new MaintParmLkupDTO();
				maintParmLkupDTO.setChannelCd(rs.getString("channel_cd"));
				maintParmLkupDTO.setLob(rs.getString("lob"));
				maintParmLkupDTO.setFunctionCd(rs.getString("function_cd"));
				maintParmLkupDTO.setParmType(rs.getString("parm_type"));
				maintParmLkupDTO.setParmValue(rs.getString("parm_value"));
				return maintParmLkupDTO;
			}
		};
		
		try {
			if (logger.isInfoEnabled()) {
				logger.info(getParmControlSQL);
			}
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("channelCd", channelCd);
			params.addValue("functionCd", functionCd);
			params.addValue("controlFunctionCd", controlFunctionCd);
			params.addValue("parmType", parmType);
			params.addValue("controlType", controlType);
			return simpleJdbcTemplate.query(getParmControlSQL, mapper, params);
		} catch (Exception e) {
			if (logger.isErrorEnabled()) {
				logger.error("Exception caught in updateMrtInventoryStatusByMrt()", e);
			}
			throw new DAOException(e.getMessage(), e);
		}
  }
    
    private boolean isEmpty(List<?> list) {
    	return list == null || list.isEmpty();
    }
}