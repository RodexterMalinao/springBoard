package com.bomwebportal.lts.dao.qc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.bomwebportal.dao.BaseDAO;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.lts.dto.qc.OrderLtsSystemFindingDTO;

public class OrderLtsSystemFindingDAO extends BaseDAO{
	private static final String ORACLE_NUM_ARRAY = "OPS$CNM.TABLE_VARCHAR2";
	
	public List<OrderLtsSystemFindingDTO> sysFchecking() throws DAOException{
		logger.info("sysFchecking");
		
		String SQL =    " select a.order_id, d.CUST_NO,C.SERBDYNO,C.FLOOR_NO, C.UNIT_NO,C.HI_LOT_NO,D.SERVICE_NUM,D.ID_DOC_TYPE,D.ID_DOC_NUM   "+
				        " from bomweb_order_lts b, bomweb_order a,bomweb_cust_addr c,bomweb_customer d, bomweb_order_latest_status e  "+
				        " where a.order_id = b.order_id   "+
				        " and a.order_id = c.order_id  "+
			       	    " and a.order_id = d.order_id  "+
                        " and a.order_id = e.order_id  "+
				        " and (b.order_id like 'D___E%' or b.order_id like 'V___E%'or b.order_id like 'D___D%' or b.order_id like 'V___D%')"+
//                        " and substr(b.order_id,1,1) = 'C' "+
				        " and A.SIGN_OFF_DATE is not null    "+
				        " and e.sb_status in ('P', 'D', 'E', 'B') "+
//                        " and b.dtl_id = '1' "+
//                        " and b.dtl_id = e.dtl_id "+
				        " and b.SYS_F is null  "+
				        " AND trunc(a.create_date) = trunc(sysdate) ";
		ParameterizedRowMapper<OrderLtsSystemFindingDTO> mapper = new ParameterizedRowMapper<OrderLtsSystemFindingDTO>() {

			public OrderLtsSystemFindingDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				OrderLtsSystemFindingDTO dto = new OrderLtsSystemFindingDTO();

				dto.setOrderID(rs.getString("order_id"));
				dto.setBomCustNo(rs.getString("CUST_NO"));
				dto.setSerbdyno(rs.getString("SERBDYNO"));
				dto.setFloorNo(rs.getString("FLOOR_NO"));
				dto.setUnitNo(rs.getString("UNIT_NO"));
				dto.setHiLotNo(rs.getString("HI_LOT_NO"));
				dto.setIdDocType(rs.getString("ID_DOC_TYPE"));
				dto.setIdDocNum(rs.getString("ID_DOC_NUM"));
				dto.setServiceNum(rs.getString("SERVICE_NUM"));
//				dto.setIsNewCustomer(rs.getString("IS_NEW_CUST_IND"));
//				dto.getAddressDTO().setSerbdyno(rs.getString("SERBDYNO"));
//				dto.getAddressDTO().setFloorNo(rs.getString("FLOOR_NO"));
//				dto.getAddressDTO().setUnitNo(rs.getString("UNIT_NO"));
//				dto.getAddressDTO().setHiLotNo(rs.getString("HI_LOT_NO"));
			
				return dto;
			}
		};
		
		try {
			logger.debug("sysFchecking @ OrderDAO: " + SQL);
			List<OrderLtsSystemFindingDTO> orders = simpleJdbcTemplate.query(SQL, mapper);
			
			return orders;
		
		} catch (Exception e) {
			logger.error("Exception caught in sysFchecking()", e);

			throw new DAOException(e.getMessage(), e);
		}		
	}
	
	public int updateDSSysFinding(OrderLtsSystemFindingDTO dto) throws DAOException{
		logger.info("updateDSSysFinding");
		logger.info("getSysF(): " + dto.getSysF());
		int row=0;
		
		String SQL = "	UPDATE bomweb_order_lts	" +
					 "  SET    SYS_F = ? 	    " +
					 "	WHERE order_id = ?	" ;
		
		try {
			row =  simpleJdbcTemplate.update(SQL,
									  dto.getSysF(),
									  dto.getOrderID()
									  );

		} catch (Exception e) {
			logger.error("Exception caught in updateDSSysFinding()", e);
			throw new DAOException(e.getMessage(), e);
		}
		
		return row;
	}

}
