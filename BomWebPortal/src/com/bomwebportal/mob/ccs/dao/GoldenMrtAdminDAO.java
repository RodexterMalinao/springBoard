package com.bomwebportal.mob.ccs.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.bomwebportal.dao.BaseDAO;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.mob.ccs.dto.GoldenMrtAdminDTO;
import com.bomwebportal.mob.ccs.util.BomWebPortalCcsConstant;

public class GoldenMrtAdminDAO extends BaseDAO {
    protected final Log logger = LogFactory.getLog(getClass());
    private static String getGoldenMrtAdminDTOSQL = "SELECT \n"
	    + "     ORDER_ID,\n" + "     REQUEST_DATE,\n"
	    + "     FIRST_NAME,\n" + "     LAST_NAME,\n"
	    + "     GOLDEN_SUFFIX,\n" + "     MSISDN,\n" + "     MSISDNLVL,\n"
	    + "     MONTHLY_CHARGE,\n" + "     CONTRACT_PERIOD,\n"
	    + "     RESERVE_ID,\n" + "     APPROVED_BY,\n"
	    + "     APPROVED_DATE,\n" + "     REMARKS,\n"
	    + "     CREATED_BY,\n" + "     CREATED_DATE,\n"
	    + "     LAST_UPD_BY,\n" + "     LAST_UPD_DATE,\n"
	    + "     TITLE,\n" + "     SALES_NAME,\n"
	    + "     REQUEST_STATUS\n"
	    + "     , rowid ROW_ID\n"
	    + " from BOMWEB_GOLDEN_MRT_ADMIN Where rowid=?\n";

    public GoldenMrtAdminDTO getGoldenMrtAdminDTO(String rowId)
	    throws DAOException {
	logger.info(" getGoldenMrtAdminDTO is called");
	List<GoldenMrtAdminDTO> itemList = new ArrayList<GoldenMrtAdminDTO>();
	/**** ==ParameterizedRowMapper start== *********************************************************/
	ParameterizedRowMapper<GoldenMrtAdminDTO> mapper = this.getRowMapper();
	/**** ==ParameterizedRowMapper end== *********************************************************/
	try {
	    logger.info("getGoldenMrtAdminDTO() @ GoldenMrtAdminDTO: "
		    + getGoldenMrtAdminDTOSQL);
	    itemList = simpleJdbcTemplate.query(getGoldenMrtAdminDTOSQL,
		    mapper, rowId);
	} catch (EmptyResultDataAccessException erdae) {
	    logger.info("EmptyResultDataAccessException in getGoldenMrtAdminDTO()");

	    itemList = null;
	} catch (Exception e) {
	    logger.info("Exception caught in getGoldenMrtAdminDTO():", e);

	    throw new DAOException(e.getMessage(), e);
	}
	return (itemList == null || itemList.isEmpty()) ? null : itemList.get(0);// only return the first one
    }
    
    
    private static String getGoldenMrtAdminDTOByOrderIdSQL = "SELECT \n"
	    + "     ORDER_ID,\n" + "     REQUEST_DATE,\n"
	    + "     FIRST_NAME,\n" + "     LAST_NAME,\n"
	    + "     GOLDEN_SUFFIX,\n" + "     MSISDN,\n" + "     MSISDNLVL,\n"
	    + "     MONTHLY_CHARGE,\n" + "     CONTRACT_PERIOD,\n"
	    + "     RESERVE_ID,\n" + "     APPROVED_BY,\n"
	    + "     APPROVED_DATE,\n" + "     REMARKS,\n"
	    + "     CREATED_BY,\n" + "     CREATED_DATE,\n"
	    + "     LAST_UPD_BY,\n" + "     LAST_UPD_DATE,\n"
	    + "     TITLE,\n" + "     SALES_NAME,\n" + "      REQUEST_STATUS\n"
	    + "     , rowid ROW_ID\n"
	    + " from BOMWEB_GOLDEN_MRT_ADMIN Where ORDER_ID=?\n";

    public GoldenMrtAdminDTO getGoldenMrtAdminDTOByOrderId(String orderId)
	    throws DAOException {
	logger.info(" getGoldenMrtAdminDTOByOrderId is called");
	GoldenMrtAdminDTO item = new GoldenMrtAdminDTO();
	/**** ==ParameterizedRowMapper start== *********************************************************/
	ParameterizedRowMapper<GoldenMrtAdminDTO> mapper = this.getRowMapper();
	/**** ==ParameterizedRowMapper end== *********************************************************/
	try {
	    logger.info("getGoldenMrtAdminDTO() @ GoldenMrtAdminDTO: "
		    + getGoldenMrtAdminDTOByOrderIdSQL);
	    item = simpleJdbcTemplate.queryForObject(getGoldenMrtAdminDTOByOrderIdSQL,
		    mapper, orderId);
	} catch (EmptyResultDataAccessException erdae) {
	    logger.info("EmptyResultDataAccessException in getGoldenMrtAdminDTOByOrderId()");

	    item = null;
	} catch (Exception e) {
	    logger.info("Exception caught in getGoldenMrtAdminDTOByOrderId():", e);

	    throw new DAOException(e.getMessage(), e);
	}
	return item;
    }


    private static String getGoldenMrtAdminDTOAllSQL = "SELECT \n"
	    + "     ORDER_ID,\n" + "     REQUEST_DATE,\n"
	    + "     FIRST_NAME,\n" + "     LAST_NAME,\n"
	    + "     GOLDEN_SUFFIX,\n" + "     MSISDN,\n" + "     MSISDNLVL,\n"
	    + "     MONTHLY_CHARGE,\n" + "     CONTRACT_PERIOD,\n"
	    + "     RESERVE_ID,\n" + "     APPROVED_BY,\n"
	    + "     APPROVED_DATE,\n" + "     REMARKS,\n"
	    + "     CREATED_BY,\n" + "     CREATED_DATE,\n"
	    + "     LAST_UPD_BY,\n" + "     LAST_UPD_DATE,\n"
	    + "     TITLE,\n" + "     SALES_NAME,\n"
	    + "     REQUEST_STATUS\n"
	    + "     , rowid ROW_ID\n"
	    + " from BOMWEB_GOLDEN_MRT_ADMIN Where ORDER_ID=?\n";

    public List<GoldenMrtAdminDTO> getGoldenMrtAdminDTOALL(String orderId)
	    throws DAOException {
	logger.info(" getGoldenMrtAdminDTO is called");
	List<GoldenMrtAdminDTO> itemList = new ArrayList<GoldenMrtAdminDTO>();
	/**** ==ParameterizedRowMapper start== *********************************************************/
	ParameterizedRowMapper<GoldenMrtAdminDTO> mapper = this.getRowMapper();
	/**** ==ParameterizedRowMapper end== *********************************************************/
	try {
	    logger.info("getGoldenMrtAdminDTO() @ GoldenMrtAdminDTO: "
		    + getGoldenMrtAdminDTOAllSQL);
	    itemList = simpleJdbcTemplate.query(getGoldenMrtAdminDTOAllSQL,
		    mapper, orderId);
	} catch (EmptyResultDataAccessException erdae) {
	    logger.info("EmptyResultDataAccessException in getGoldenMrtAdminDTO()");

	    itemList = null;
	} catch (Exception e) {
	    logger.info("Exception caught in getGoldenMrtAdminDTO():", e);

	    throw new DAOException(e.getMessage(), e);
	}
	return itemList;// only return the first one
    }


    private static String getGoldenMrtAdminDTOByDateSQL = "SELECT \n"
	    + "     ORDER_ID,\n" + "     REQUEST_DATE,\n"
	    + "     FIRST_NAME,\n" + "     LAST_NAME,\n"
	    + "     GOLDEN_SUFFIX,\n" + "     MSISDN,\n" + "     MSISDNLVL,\n"
	    + "     MONTHLY_CHARGE,\n" + "     CONTRACT_PERIOD,\n"
	    + "     RESERVE_ID,\n" + "     APPROVED_BY,\n"
	    + "     APPROVED_DATE,\n" + "     REMARKS,\n"
	    + "     CREATED_BY,\n" + "     CREATED_DATE,\n"
	    + "     LAST_UPD_BY,\n" + "     LAST_UPD_DATE,\n"
	    + "     TITLE,\n" + "     SALES_NAME,\n"
	    + "     REQUEST_STATUS\n"
	    + "     , rowid ROW_ID\n"
	    + " FROM BOMWEB_GOLDEN_MRT_ADMIN WHERE REQUEST_DATE >= trunc(?) AND REQUEST_DATE < trunc(? + 1) \n"
	    + " ORDER BY REQUEST_DATE, ORDER_ID\n";

    public List<GoldenMrtAdminDTO> getGoldenMrtAdminDTOByDate(Date startDate, Date endDate)
	    throws DAOException {
    	logger.info(" getGoldenMrtAdminDTO is called");
    	List<GoldenMrtAdminDTO> itemList = new ArrayList<GoldenMrtAdminDTO>();
    	/**** ==ParameterizedRowMapper start== *********************************************************/
    	ParameterizedRowMapper<GoldenMrtAdminDTO> mapper = this.getRowMapper();
    	/**** ==ParameterizedRowMapper end== *********************************************************/
    	try {
    	    logger.info("getGoldenMrtAdminDTOByDate() @ GoldenMrtAdminDTO: "
    		    + getGoldenMrtAdminDTOByDateSQL);
    	    itemList = simpleJdbcTemplate.query(getGoldenMrtAdminDTOByDateSQL,
    		    mapper, new Object[] { startDate, endDate });
    	} catch (EmptyResultDataAccessException erdae) {
    	    logger.info("EmptyResultDataAccessException in getGoldenMrtAdminDTOByDate()");

    	    itemList = null;
    	} catch (Exception e) {
    	    logger.info("Exception caught in getGoldenMrtAdminDTOByDate():", e);

    	    throw new DAOException(e.getMessage(), e);
    	}
    	return itemList;// only return the first one
    }
    
	private ParameterizedRowMapper<GoldenMrtAdminDTO> getRowMapper() {
		return new ParameterizedRowMapper<GoldenMrtAdminDTO>() {
			public GoldenMrtAdminDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				GoldenMrtAdminDTO dto = new GoldenMrtAdminDTO();
				dto.setOrderId(rs.getString("ORDER_ID"));
				dto.setRequestDate(rs.getTimestamp("REQUEST_DATE"));
				dto.setFirstName(rs.getString("FIRST_NAME"));
				dto.setLastName(rs.getString("LAST_NAME"));
				dto.setGoldenSuffix(rs.getString("GOLDEN_SUFFIX"));
				dto.setMsisdn(rs.getString("MSISDN"));
				dto.setMsisdnlvl(rs.getString("MSISDNLVL"));
				dto.setMonthlyCharge(rs.getString("MONTHLY_CHARGE"));
				dto.setContractPeriod(rs.getString("CONTRACT_PERIOD"));
				dto.setReserveId(rs.getString("RESERVE_ID"));
				dto.setApprovedBy(rs.getString("APPROVED_BY"));
				dto.setApprovedDate(rs.getTimestamp("APPROVED_DATE"));
				dto.setRemarks(rs.getString("REMARKS"));
				dto.setCreatedBy(rs.getString("CREATED_BY"));
				dto.setCreatedDate(rs.getTimestamp("CREATED_DATE"));
				dto.setLastUpdBy(rs.getString("LAST_UPD_BY"));
				dto.setLastUpdDate(rs.getTimestamp("LAST_UPD_DATE"));
				dto.setTitle(rs.getString("TITLE"));
				dto.setSalesName(rs.getString("SALES_NAME"));
				dto.setRequestStatus(rs.getString("REQUEST_STATUS"));
				dto.setRowId(rs.getString("ROW_ID"));
				return dto;
			}
		};
	}

    // === Start of INSERT function ===
    private static final String insertGoldenMrtAdminSQL = " INSERT INTO BOMWEB_GOLDEN_MRT_ADMIN ( \n"
	    + "    ORDER_ID,  \n"
	    + "    REQUEST_DATE,  \n"
	    + "    FIRST_NAME,  \n"
	    + "    LAST_NAME,  \n"
	    + "    GOLDEN_SUFFIX,  \n"
	    + "    MSISDN,  \n"
	    + "    MSISDNLVL,  \n"
	    + "    MONTHLY_CHARGE,  \n"
	    + "    CONTRACT_PERIOD,  \n"
	    + "    RESERVE_ID,  \n"
	    + "    APPROVED_BY,  \n"
	    + "    APPROVED_DATE,  \n"
	    + "    REMARKS,  \n"
	    + "    CREATED_BY,  \n"
	    + "    CREATED_DATE,  \n"
	    + "    LAST_UPD_BY,  \n"
	    + "    LAST_UPD_DATE,  \n"
	    + "    TITLE,  \n"
	    + "    SALES_NAME,  \n"
	    + "    REQUEST_STATUS  \n"
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
	    + "? , \n"
	    + "? , \n"
	    + "? , \n"
	    + "? , \n"
	    + "?  \n" 
	    + " ) \n";

    public int insertGoldenMrtAdmin(GoldenMrtAdminDTO dto) throws DAOException {
	logger.info("insertGoldenMrtAdmin is called");

	try {
	    logger.info("insertGoldenMrtAdmin() @ GoldenMrtAdminDAO: "
		    + insertGoldenMrtAdminSQL);
	    return simpleJdbcTemplate.update(
		    insertGoldenMrtAdminSQL,
		    // start sql mapping
		    dto.getOrderId(), dto.getRequestDate(), dto.getFirstName(),
		    dto.getLastName(), dto.getGoldenSuffix(), dto.getMsisdn(),
		    dto.getMsisdnlvl(), dto.getMonthlyCharge(),
		    dto.getContractPeriod(), dto.getReserveId(),
		    dto.getApprovedBy(), dto.getApprovedDate(),
		    dto.getRemarks(), dto.getCreatedBy(), dto.getCreatedDate(),
		    dto.getLastUpdBy(), dto.getLastUpdDate(), dto.getTitle(), dto.getSalesName(),
		    dto.getRequestStatus()
	    // end sql mapping
		    );
	} catch (Exception e) {
	    logger.error("Exception caught in insertGoldenMrtAdmin()", e);
	    throw new DAOException(e.getMessage(), e);
	}
    }

    // === End of INSERT function ===
    // === Start of UPDATE function ===
    private static final String updateGoldenMrtAdminSQL = " UPDATE BOMWEB_GOLDEN_MRT_ADMIN SET \n"
	    + "     ORDER_ID = ? ,\n"
	    + "     REQUEST_DATE = ? ,\n"
	    + "     FIRST_NAME = ? ,\n"
	    + "     LAST_NAME = ? ,\n"
	    + "     GOLDEN_SUFFIX = ? ,\n"
	    + "     MSISDN = ? ,\n"
	    + "     MSISDNLVL = ? ,\n"
	    + "     MONTHLY_CHARGE = ? ,\n"
	    + "     CONTRACT_PERIOD = ? ,\n"
	    + "     RESERVE_ID = ? ,\n"
	    + "     APPROVED_BY = ? ,\n"
	    + "     APPROVED_DATE = ? ,\n"
	    + "     REMARKS = ? ,\n"
	    + "     CREATED_BY = ? ,\n"
	    + "     CREATED_DATE = ? ,\n"
	    + "     LAST_UPD_BY = ? ,\n"
	    + "     LAST_UPD_DATE = ? \n" + " WHERE  rowid = ? \n";

    public int updateGoldenMrtAdmin(GoldenMrtAdminDTO dto) throws DAOException {
	logger.info("updateGoldenMrtAdmin is called");

	try {
	    logger.info("updateGoldenMrtAdmin() @ GoldenMrtAdminDAO: "
		    + updateGoldenMrtAdminSQL);
	    return simpleJdbcTemplate.update(
		    updateGoldenMrtAdminSQL,
		    // start sql mapping
		    dto.getOrderId(), dto.getRequestDate(), dto.getFirstName(),
		    dto.getLastName(), dto.getGoldenSuffix(), dto.getMsisdn(),
		    dto.getMsisdnlvl(), dto.getMonthlyCharge(),
		    dto.getContractPeriod(), dto.getReserveId(),
		    dto.getApprovedBy(), dto.getApprovedDate(),
		    dto.getRemarks(), dto.getCreatedBy(), dto.getCreatedDate(),
		    dto.getLastUpdBy(), dto.getLastUpdDate(), dto.getRowId());

	} catch (Exception e) {
	    logger.error("Exception caught in updateGoldenMrtAdmin()", e);
	    throw new DAOException(e.getMessage(), e);
	}

    }
    
    private static final String updateGoldenMrtAdminForAdminSQL = " UPDATE BOMWEB_GOLDEN_MRT_ADMIN SET \n"
    	    + "     MSISDN = ? \n"
    	    + "     , MSISDNLVL = ? \n"
    	    + "     , RESERVE_ID = ? \n"
    	    + "     , REQUEST_STATUS = ? \n"
    	    + "     , LAST_UPD_BY = ? \n"
    	    + "     , LAST_UPD_DATE = ? \n" 
    	    + " WHERE rowid = ? \n" 
    	    + " AND REQUEST_STATUS = ?";

	public int updateGoldenMrtAdminForAdmin(GoldenMrtAdminDTO dto) throws DAOException {
		logger.info("updateGoldenMrtAdminForAdmin is called");		
		
		try {
			logger.info("updateGoldenMrtAdminForAdmin() @ GoldenMrtAdminDAO: "
					+ updateGoldenMrtAdminForAdminSQL);
			return simpleJdbcTemplate.update(updateGoldenMrtAdminForAdminSQL,
					// start sql mapping
					dto.getMsisdn(), dto.getMsisdnlvl(), dto.getReserveId()
					, dto.getRequestStatus()
					, dto.getLastUpdBy(), dto.getLastUpdDate()
					, dto.getRowId(), BomWebPortalCcsConstant.MRT_GOLDEN_NUM_REQUEST);

		} catch (Exception e) {
			logger.error("Exception caught in updateGoldenMrtAdminForAdmin()", e);
			throw new DAOException(e.getMessage(), e);
		}

	}
	
    private static final String updateGoldenMrtAdminForManagerSQL = " UPDATE BOMWEB_GOLDEN_MRT_ADMIN SET \n"
    	    + "     REQUEST_STATUS = ? \n"
    	    + "     , APPROVED_BY = ? \n"
    	    + "     , APPROVED_DATE = ? \n" 
    	    + "     , LAST_UPD_BY = ? \n"
    	    + "     , LAST_UPD_DATE = ? \n" 
    	    + " WHERE rowid = ? \n"
    	    + " AND REQUEST_STATUS = ?";

	public int updateGoldenMrtAdminForManager(GoldenMrtAdminDTO dto) throws DAOException {
		logger.info("updateGoldenMrtAdminForManager is called");

		try {
			logger.info("updateGoldenMrtAdminForManager() @ GoldenMrtAdminDAO: "
					+ updateGoldenMrtAdminForManagerSQL);
			return simpleJdbcTemplate.update(updateGoldenMrtAdminForManagerSQL,
					// start sql mapping
					dto.getRequestStatus()
					, dto.getApprovedBy(), dto.getApprovedDate()
					, dto.getLastUpdBy(), dto.getLastUpdDate()
					, dto.getRowId(), BomWebPortalCcsConstant.MRT_GOLDEN_NUM_RESERVE);

		} catch (Exception e) {
			logger.error("Exception caught in updateGoldenMrtAdminForManager()", e);
			throw new DAOException(e.getMessage(), e);
		}

	}

    // === End of UPDATE function ===
    // === Start of DELETE function ===
    private static final String deleteGoldenMrtAdminSQL = "  delete from BOMWEB_GOLDEN_MRT_ADMIN where ORDER_ID=?";

    public int deleteGoldenMrtAdmin(GoldenMrtAdminDTO dto) throws DAOException {
	logger.info("deleteGoldenMrtAdmin is called");

	try {
	    logger.info("deleteGoldenMrtAdmin() @ GoldenMrtAdminDAO: "
		    + deleteGoldenMrtAdminSQL);
	    return simpleJdbcTemplate.update(deleteGoldenMrtAdminSQL,
		    dto.getOrderId());
	} catch (Exception e) {
	    logger.error("Exception caught in deleteGoldenMrtAdmin()", e);
	    throw new DAOException(e.getMessage(), e);
	}
    }
    // === End of DELETE function ===
    
    private static final String updateGoldenMrtAdminMsisdnStatusSQL = "UPDATE bomweb_golden_mrt_admin SET request_status=? WHERE order_id=?";
    
    public int updateGoldenMrtAdminMsisdnStatus(String orderId, String status) throws DAOException {
	logger.info("updateGoldenMrtAdminMsisdnStatus is called");

	try {
	    logger.info("updateGoldenMrtAdminMsisdnStatus() @ GoldenMrtAdminDAO: "
				+ updateGoldenMrtAdminMsisdnStatusSQL);
	    return simpleJdbcTemplate.update(updateGoldenMrtAdminMsisdnStatusSQL, status, orderId);

	} catch (Exception e) {
	    logger.error("Exception caught in updateGoldenMrtAdminMsisdnStatus()", e);
	    throw new DAOException(e.getMessage(), e);
	}
    }

}
