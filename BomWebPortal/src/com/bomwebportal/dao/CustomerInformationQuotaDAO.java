package com.bomwebportal.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.bomwebportal.dto.CustomerInformationQuotaDTO;
import com.bomwebportal.exception.DAOException;

public class CustomerInformationQuotaDAO extends BaseDAO {

	//edit by wilson , add bomweb_order not count CCS: cancelling 03 or cancelled 04 , RS: VOID order
	private static String getCustomerInformationQuotaDTOListSQL = "select A.ID_DOC_TYPE,\n" +
		"       A.ID_DOC_NUM,\n" + 
		"       A.QUOTA_ID,\n" + 
		"       B.QUOTA_DESC,\n" + 
		"       B.QUOTA_CEILING,\n" + 
		"       count(*) QUOTA_IN_USE\n" + 
		"  from BOMWEB_CUST_QUOTA_IN_USE A, W_QUOTA_LKUP B, BOMWEB_ORDER O\n" + 
		" where A.QUOTA_ID = B.QUOTA_ID\n" + 
		"   and A.ORDER_ID = O.ORDER_ID\n" + 
		"   and O.ORDER_STATUS not in ('03', '04', 'VOID') --not count CCS: cancelling 03 or cancelled 04, RS: VOID order\n" + 
		"   and A.ID_DOC_TYPE = ?\n" + 
		"   and A.ID_DOC_NUM = ?\n" + 
		"   and (A.END_DATE is null or A.END_DATE > sysdate)\n" + 
		"   and A.ORDER_ID <> NVL(?, ' ')\n" + 
		" group by A.ID_DOC_TYPE,\n" + 
		"          A.ID_DOC_NUM,\n" + 
		"          A.QUOTA_ID,\n" + 
		"          B.QUOTA_DESC,\n" + 
		"          B.QUOTA_CEILING\n" + 
		" order by A.ID_DOC_TYPE, A.ID_DOC_NUM";


	public List<CustomerInformationQuotaDTO> getCustomerInformationQuotaDTOList(
			String idDocType, String idDocNum, String orderId) throws DAOException {
		logger.debug(" getCustomerInformationQuotaDTOList is called");
		List<CustomerInformationQuotaDTO> itemList = new ArrayList<CustomerInformationQuotaDTO>();

		ParameterizedRowMapper<CustomerInformationQuotaDTO> mapper = new ParameterizedRowMapper<CustomerInformationQuotaDTO>() {
			public CustomerInformationQuotaDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				CustomerInformationQuotaDTO dto = new CustomerInformationQuotaDTO();
				dto.setIdDocNum(rs.getString("ID_DOC_NUM"));
				dto.setQuotaId(rs.getString("QUOTA_ID"));
				dto.setQuotaDesc(rs.getString("QUOTA_DESC"));
				dto.setQuotaCeiling(rs.getInt("QUOTA_CEILING"));
				dto.setQuotaInUse(rs.getInt("QUOTA_IN_USE"));

				return dto;
			}
		};

		try {
			logger.debug("getCustomerInformationQuotaDTOList() @ CustomerInformationQuotaDTO: "
					+ getCustomerInformationQuotaDTOListSQL);

			itemList = simpleJdbcTemplate.query(
					getCustomerInformationQuotaDTOListSQL, mapper, idDocType,
					idDocNum, orderId);
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException in getCustomerInformationQuotaDTOList()");

			itemList = null;
		} catch (Exception e) {
			logger.info(
					"Exception caught in getCustomerInformationQuotaDTOList():",
					e);

			throw new DAOException(e.getMessage(), e);
		}

		return itemList;

	}

}
