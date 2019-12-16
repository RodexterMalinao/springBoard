package com.bomltsportal.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.bomwebportal.dao.BaseDAO;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.lts.dto.profile.CustomerDetailProfileLtsDTO;

public class CustomerDetailDAO extends BaseDAO {

	private static String SQL_GET_CUST_BY_DOC = 
			"select distinct cust.cust_num CUST_NUM, cust.id_doc_num ID_DOC_NUM, cust.id_doc_type ID_DOC_TYPE, cust.title TITLE, " +
			"cust.cust_first_name CUST_FIRST_NAME, cust.cust_last_name CUST_LAST_NAME, cust.parent_cust_num PARENT_CUST_NUM, " +
			"cust_dtl.id_verify_ind ID_VERIFY_IND, cust_dtl.written_approval_required WRITTEN_APPROVAL_REQUIRED, " +
			"decode(bl_cust.cust_num, null, 'N', 'Y') BLACKLIST_CUST_IND " +
			"from b_customer cust, b_customer_details cust_dtl, b_blacklist_customer bl_cust " +
			"where cust.system_id = 'DRG' " + 
			"and cust.cust_num = cust_dtl.cust_num and cust.system_id = cust_dtl.system_id " + 
			"and cust.cust_num = bl_cust.cust_num(+) and cust.system_id = bl_cust.system_id(+) " +
			"and cust.id_doc_num = ? and cust.id_doc_type = ?"; 
	
	
	public CustomerDetailProfileLtsDTO getLtsCustomerDetailByDocId(String pDocId, String pDocType) throws DAOException {
		
		ParameterizedRowMapper<CustomerDetailProfileLtsDTO> mapper = new ParameterizedRowMapper<CustomerDetailProfileLtsDTO>() {
			public CustomerDetailProfileLtsDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				CustomerDetailProfileLtsDTO customer = new CustomerDetailProfileLtsDTO();
				customer.setCustNum(rs.getString("CUST_NUM"));
				customer.setDocNum(rs.getString("ID_DOC_NUM"));
				customer.setDocType(rs.getString("ID_DOC_TYPE"));
				customer.setFirstName(rs.getString("CUST_FIRST_NAME"));
				customer.setLastName(rs.getString("CUST_LAST_NAME"));
				customer.setParentCustNum(rs.getString("PARENT_CUST_NUM"));
				customer.setIdVerifyInd(StringUtils.equals(rs.getString("ID_VERIFY_IND"), "Y"));
				customer.setWipInd(rs.getString("WRITTEN_APPROVAL_REQUIRED"));
				customer.setBlacklistCustInd(StringUtils.equals(rs.getString("BLACKLIST_CUST_IND"), "Y"));
				customer.setTitle(rs.getString("TITLE"));
				return customer;
			}
		};
		try {
			
			@SuppressWarnings("unchecked")
			List<CustomerDetailProfileLtsDTO> customerDetailList = (List<CustomerDetailProfileLtsDTO>) simpleJdbcTemplate.query(SQL_GET_CUST_BY_DOC, mapper, pDocId, pDocType);
			
			if (customerDetailList == null || customerDetailList.isEmpty()) {
				return null;
			}
			return customerDetailList.get(0);
			
		} catch (EmptyResultDataAccessException erdae) {
			return null;
		} catch (Exception e) {
			logger.error("Error in getCustomerDetailByDocId() Doc id: " +  pDocId + " Doc Type: pDocType", e);
			throw new DAOException(e.getMessage(), e);
		}

	}
}
