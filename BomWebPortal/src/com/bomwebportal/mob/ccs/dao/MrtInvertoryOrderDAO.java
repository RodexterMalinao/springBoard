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
import com.bomwebportal.mob.ccs.dto.MrtInventoryOrderDTO;

public class MrtInvertoryOrderDAO extends BaseDAO {
    protected final Log logger = LogFactory.getLog(getClass());

    private static String getMrtInventoryOrderDTOSQL = "SELECT\n"
    		+ "  a.ORDER_ID\n"
    		+ ", a.MSISDN\n"
    		+ ", a.MSISDNLVL\n"
    		+ ", a.SRV_REQ_DATE\n"
    		+ ", a.APP_DATE\n"
    		+ ", a.SALES_CD\n"
    		+ ", a.SALES_NAME\n"
    		+ ", a.ORDER_STATUS\n"
    		+ ", b.CODE_DESC ORDER_STATUS_DESC\n"
    		+ " FROM bomweb_order a, bomweb_code_lkup b, bomweb_mrt_assgn c\n"
    		+ " WHERE c.msisdn = ? \n"
    		+ " AND TRUNC(c.stock_in_date) = TRUNC(?) \n"
    		+ " AND a.ORDER_ID = c.ORDER_ID \n"
    		+ " AND b.code_type= ? \n"
    		+ " AND a.order_status=b.code_id \n" 
    		+ " ORDER BY APP_DATE DESC";

    public MrtInventoryOrderDTO getMrtInventoryOrderDTO(String msisdn, Date stockInDate) throws DAOException {
		logger.info(" getMrtInventoryOrderDTO is called");
		List<MrtInventoryOrderDTO> itemList = new ArrayList<MrtInventoryOrderDTO>();
		/**** ==ParameterizedRowMapper start== *********************************************************/
		ParameterizedRowMapper<MrtInventoryOrderDTO> mapper = this.getRowMapper();
		/**** ==ParameterizedRowMapper end== *********************************************************/
		try {
		    logger.info("getMrtInventoryOrderDTO() @ MrtInventoryOrderDTO: "
			    + getMrtInventoryOrderDTOSQL);
		    itemList = simpleJdbcTemplate.query(getMrtInventoryOrderDTOSQL, mapper, msisdn, stockInDate, "ORDER_STS");
		} catch (EmptyResultDataAccessException erdae) {
		    logger.info("EmptyResultDataAccessException in getMrtInventoryOrderDTO()");
	
		    itemList = null;
		} catch (Exception e) {
		    logger.info("Exception caught in getMrtInventoryOrderDTO():", e);
	
		    throw new DAOException(e.getMessage(), e);
		}
		return itemList == null || itemList.isEmpty() ? null : itemList.get(0);// only return the first one
	}

    private static String getMrtInventoryOrderDTOAllSQL = "SELECT\n"
    		+ "  ORDER_ID\n"
    		+ ", MSISDN\n"
    		+ ", MSISDNLVL\n"
    		+ ", SRV_REQ_DATE\n"
    		+ ", APP_DATE\n"
    		+ ", SALES_CD\n"
    		+ ", SALES_NAME\n"
    		+ ", ORDER_STATUS\n"
    		+ ", b.CODE_DESC ORDER_STATUS_DESC\n"
    		+ " FROM bomweb_order a, bomweb_code_lkup b\n"
    		+ " WHERE msisdn = ? \n"
    		+ " AND b.code_type= ? \n"
    		+ " AND a.order_status=b.code_id \n" 
    		+ " ORDER BY APP_DATE DESC";

    public List<MrtInventoryOrderDTO> getMrtInventoryOrderDTOALL(String msisdn) throws DAOException {
		logger.info(" getMrtInventoryOrderDTOALL is called");
		List<MrtInventoryOrderDTO> itemList = new ArrayList<MrtInventoryOrderDTO>();
		/**** ==ParameterizedRowMapper start== *********************************************************/
		ParameterizedRowMapper<MrtInventoryOrderDTO> mapper = this.getRowMapper();
		/**** ==ParameterizedRowMapper end== *********************************************************/
		try {
		    logger.info("getMrtInventoryOrderDTOALL() @ MrtInventoryOrderDTO: "
			    + getMrtInventoryOrderDTOAllSQL);
		    itemList = simpleJdbcTemplate.query(getMrtInventoryOrderDTOAllSQL, mapper, msisdn, "ORDER_STS");
		} catch (EmptyResultDataAccessException erdae) {
		    logger.info("EmptyResultDataAccessException in getMrtInventoryOrderDTOALL()");
	
		    itemList = null;
		} catch (Exception e) {
		    logger.info("Exception caught in getMrtInventoryOrderDTO():", e);
	
		    throw new DAOException(e.getMessage(), e);
		}
		return itemList;// only return the first one
	}

	private ParameterizedRowMapper<MrtInventoryOrderDTO> getRowMapper() {
		return new ParameterizedRowMapper<MrtInventoryOrderDTO>() {
			public MrtInventoryOrderDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				MrtInventoryOrderDTO dto = new MrtInventoryOrderDTO();
				dto.setOrderId(rs.getString("ORDER_ID"));
				dto.setMsisdn(rs.getString("MSISDN"));
				dto.setMsisdnlvl(rs.getString("MSISDNLVL"));
				dto.setSrvReqDate(rs.getTimestamp("SRV_REQ_DATE"));
				dto.setAppDate(rs.getTimestamp("APP_DATE"));
				dto.setSalesCode(rs.getString("SALES_CD"));
				dto.setSalesName(rs.getString("SALES_NAME"));
				dto.setOrderStatus(rs.getString("ORDER_STATUS"));
				dto.setOrderStatusDesc(rs.getString("ORDER_STATUS_DESC"));
				return dto;
			}
		};
	}
}
