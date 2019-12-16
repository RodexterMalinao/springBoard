package com.bomwebportal.mob.cos.dao;

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
import com.bomwebportal.mob.cos.dto.MobCosCopDTO;

public class MobCosCopDAO extends BaseDAO {
	protected final Log logger = LogFactory.getLog(getClass());
	
	private static String getCosCopProcessOrderDetailSQL = 
			"SELECT order_id, ocid, msisdn, cust_name, "
			+ "addrln1, addrln2, addrln3, addrln4, addrln5, post_req_ind, "
			+ "req_file_name "
		    + "FROM w_full_cop_request_work "
		    + "WHERE order_id = ? ";

    public MobCosCopDTO getCosCopProcessOrderDetail(String orderId)
	    throws DAOException {
	logger.info("getCosCopProcessOrderDetail @ MobCosCopDAO is called");
	List<MobCosCopDTO> result = new ArrayList<MobCosCopDTO>();

	ParameterizedRowMapper<MobCosCopDTO> mapper = new ParameterizedRowMapper<MobCosCopDTO>() {
	    public MobCosCopDTO mapRow(ResultSet rs, int rowNum)
		    throws SQLException {

	    	MobCosCopDTO tempDto = new MobCosCopDTO();
	    	tempDto.setOrderId(rs.getString("order_id"));
	    	tempDto.setOcid(rs.getString("ocid"));
	    	tempDto.setMsisdn(rs.getString("msisdn"));
	    	tempDto.setCustName(rs.getString("cust_name"));
	    	tempDto.setAddrLn1(rs.getString("addrln1"));
	    	tempDto.setAddrLn2(rs.getString("addrln2"));
	    	tempDto.setAddrLn3(rs.getString("addrln3"));
	    	tempDto.setAddrLn4(rs.getString("addrln4"));
	    	tempDto.setAddrLn5(rs.getString("addrln5"));
	    	tempDto.setPostingInd(rs.getString("post_req_ind"));
	    	tempDto.setReqFilename(rs.getString("req_file_name"));
	    	return tempDto;
	    }
	};

	try {
	    logger.info("getCosCopProcessOrderDetail() @ MobCosCopDAO: "
		    + getCosCopProcessOrderDetailSQL);

	    result = simpleJdbcTemplate.query(getCosCopProcessOrderDetailSQL, mapper, orderId);
	} catch (EmptyResultDataAccessException erdae) {
	    logger.info("EmptyResultDataAccessException in getCosCopProcessOrderDetail()");

	    result = null;
	} catch (Exception e) {
	    logger.info("Exception caught in getCosCopProcessOrderDetail():", e);

	    throw new DAOException(e.getMessage(), e);
	}
		return (result == null || result.isEmpty()) ? null : result.get(0);
    }
	
}
