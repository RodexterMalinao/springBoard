package com.bomwebportal.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.bomwebportal.dto.BomCustomerVerificationDTO;

public class BomCustomerValidationDAO extends BaseDAO {
	
	final String SQL_GET_CUSTOMER_INFO_FROM_BOM = "select ID_DOC_TYPE, ID_DOC_NUM, CUST_NUM, CUST_FIRST_NAME, CUST_LAST_NAME, SYSTEM_ID from B_CUSTOMER " +
			" where ID_DOC_TYPE = ? and ID_DOC_NUM = ? and SYSTEM_ID in ('DRG','IMS') ";
	
	final String SQL_GET_CUSTOMER_INFO_FROM_BOM_LTS_PROFILE = "select ID_DOC_TYPE, ID_DOC_NUM, CUST_NUM, CUST_FIRST_NAME, CUST_LAST_NAME, SYSTEM_ID from B_CUSTOMER " +
			" where ID_DOC_TYPE = ? and ID_DOC_NUM = ? and SYSTEM_ID in ('DRG') ";
	
	final String SQL_GET_CUSTOMER_INFO_FROM_BOM_IMS_PROFILE = "select ID_DOC_TYPE, ID_DOC_NUM, CUST_NUM, CUST_FIRST_NAME, CUST_LAST_NAME, SYSTEM_ID from B_CUSTOMER " +
			" where ID_DOC_TYPE = ? and ID_DOC_NUM = ? and SYSTEM_ID in ('IMS') ";
	
	public BomCustomerVerificationDTO validateUserWithBom(String pDocType, String pDocNum, String pCustFirstName, String pCustLastName){
		ParameterizedRowMapper<BomCustomerVerificationDTO> mapper = new ParameterizedRowMapper<BomCustomerVerificationDTO>() {
			public BomCustomerVerificationDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				BomCustomerVerificationDTO info = new BomCustomerVerificationDTO();
				info.setBomIdDocType(rs.getString("ID_DOC_TYPE"));
				info.setBomIdDocNum(rs.getString("ID_DOC_NUM"));
				info.setBomFirstName(rs.getString("CUST_FIRST_NAME"));
				info.setBomLastName(rs.getString("CUST_LAST_NAME"));
				info.setBomCustNum(rs.getString("CUST_NUM"));
				info.setBomSystemId(rs.getString("SYSTEM_ID"));
				return info;
			}
		};
		
		List<BomCustomerVerificationDTO> result = simpleJdbcTemplate.query(SQL_GET_CUSTOMER_INFO_FROM_BOM, mapper, pDocType, pDocNum);
		BomCustomerVerificationDTO verifyResult = new BomCustomerVerificationDTO();
		verifyResult.setInputFirstName(pCustFirstName);
		verifyResult.setInputLastName(pCustLastName);
		verifyResult.setInputIdDocType(pDocType);
		verifyResult.setInputIdDocNum(pDocNum);
		
		if(result == null || result.size() < 1){
			verifyResult.setAlertMessage("Customer Information (" + pDocType + ": " + pDocNum + ") does not exist in BOM");
			verifyResult.setMatchWithBomName(false);
		}
		else{
			for(BomCustomerVerificationDTO info : result){
				if("DRG".equals(info.getBomSystemId())){
					verifyResult.setLtsCustNum(info.getBomCustNum());
					verifyResult.setContainLtsProfile(true);
				}
			}
			
			for(BomCustomerVerificationDTO info : result){
				verifyResult.setBomCustNum(info.getBomCustNum());
				verifyResult.setBomFirstName(info.getBomFirstName());
				verifyResult.setBomLastName(info.getBomLastName());
				verifyResult.setBomIdDocType(info.getBomIdDocType());
				verifyResult.setBomIdDocNum(info.getBomIdDocNum());
				verifyResult.setBomSystemId(info.getBomSystemId());
				
				if(!info.getBomFirstName().replaceAll(" ", "").equalsIgnoreCase(pCustFirstName.replaceAll(" ", ""))
					|| !info.getBomLastName().replaceAll(" ", "").equalsIgnoreCase(pCustLastName.replaceAll(" ", ""))){
					verifyResult.setAlertMessage("Customer name mismatches the input of the customer name!");
					verifyResult.setMatchWithBomName(false);
					return verifyResult;
				}
			}
		}
		verifyResult.setAlertMessage("");
		verifyResult.setMatchWithBomName(true);
		return verifyResult;
	}
	
	public BomCustomerVerificationDTO validateUserWithBomLtsProfile(String pDocType, String pDocNum, String pCustFirstName, String pCustLastName){
		ParameterizedRowMapper<BomCustomerVerificationDTO> mapper = new ParameterizedRowMapper<BomCustomerVerificationDTO>() {
			public BomCustomerVerificationDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				BomCustomerVerificationDTO info = new BomCustomerVerificationDTO();
				info.setBomIdDocType(rs.getString("ID_DOC_TYPE"));
				info.setBomIdDocNum(rs.getString("ID_DOC_NUM"));
				info.setBomFirstName(rs.getString("CUST_FIRST_NAME"));
				info.setBomLastName(rs.getString("CUST_LAST_NAME"));
				info.setBomCustNum(rs.getString("CUST_NUM"));
				info.setBomSystemId(rs.getString("SYSTEM_ID"));
				return info;
			}
		};
		
		List<BomCustomerVerificationDTO> result = simpleJdbcTemplate.query(SQL_GET_CUSTOMER_INFO_FROM_BOM_LTS_PROFILE, mapper, pDocType, pDocNum);
		BomCustomerVerificationDTO verifyResult = new BomCustomerVerificationDTO();
		verifyResult.setInputFirstName(pCustFirstName);
		verifyResult.setInputLastName(pCustLastName);
		verifyResult.setInputIdDocType(pDocType);
		verifyResult.setInputIdDocNum(pDocNum);
		
		if(result == null || result.size() < 1){
			verifyResult.setAlertMessage("Customer Information (" + pDocType + ": " + pDocNum + ") does not exist in BOM");
			verifyResult.setMatchWithBomName(false);
		}
		else{
			for(BomCustomerVerificationDTO info : result){
				if("DRG".equals(info.getBomSystemId())){
					verifyResult.setLtsCustNum(info.getBomCustNum());
					verifyResult.setContainLtsProfile(true);
				}
			}
			
			for(BomCustomerVerificationDTO info : result){
				verifyResult.setBomCustNum(info.getBomCustNum());
				verifyResult.setBomFirstName(info.getBomFirstName());
				verifyResult.setBomLastName(info.getBomLastName());
				verifyResult.setBomIdDocType(info.getBomIdDocType());
				verifyResult.setBomIdDocNum(info.getBomIdDocNum());
				verifyResult.setBomSystemId(info.getBomSystemId());
				
				if(!info.getBomFirstName().replaceAll(" ", "").equalsIgnoreCase(pCustFirstName.replaceAll(" ", ""))
					|| !info.getBomLastName().replaceAll(" ", "").equalsIgnoreCase(pCustLastName.replaceAll(" ", ""))){
					verifyResult.setAlertMessage("Customer name mismatches the input of the customer name!");
					verifyResult.setMatchWithBomName(false);
					return verifyResult;
				}
			}
		}
		verifyResult.setAlertMessage("");
		verifyResult.setMatchWithBomName(true);
		return verifyResult;
	}
	
	public BomCustomerVerificationDTO validateUserWithBomImsProfile(String pDocType, String pDocNum, String pCustFirstName, String pCustLastName){
		ParameterizedRowMapper<BomCustomerVerificationDTO> mapper = new ParameterizedRowMapper<BomCustomerVerificationDTO>() {
			public BomCustomerVerificationDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				BomCustomerVerificationDTO info = new BomCustomerVerificationDTO();
				info.setBomIdDocType(rs.getString("ID_DOC_TYPE"));
				info.setBomIdDocNum(rs.getString("ID_DOC_NUM"));
				info.setBomFirstName(rs.getString("CUST_FIRST_NAME"));
				info.setBomLastName(rs.getString("CUST_LAST_NAME"));
				info.setBomCustNum(rs.getString("CUST_NUM"));
				info.setBomSystemId(rs.getString("SYSTEM_ID"));
				return info;
			}
		};
		
		List<BomCustomerVerificationDTO> result = simpleJdbcTemplate.query(SQL_GET_CUSTOMER_INFO_FROM_BOM_IMS_PROFILE, mapper, pDocType, pDocNum);
		BomCustomerVerificationDTO verifyResult = new BomCustomerVerificationDTO();
		verifyResult.setInputFirstName(pCustFirstName);
		verifyResult.setInputLastName(pCustLastName);
		verifyResult.setInputIdDocType(pDocType);
		verifyResult.setInputIdDocNum(pDocNum);
		
		if(result == null || result.size() < 1){
			verifyResult.setAlertMessage("Customer Information (" + pDocType + ": " + pDocNum + ") does not exist in BOM");
			verifyResult.setMatchWithBomName(false);
		}
		else{
			for(BomCustomerVerificationDTO info : result){
				if("DRG".equals(info.getBomSystemId())){
					verifyResult.setLtsCustNum(info.getBomCustNum());
					verifyResult.setContainLtsProfile(true);
				}
			}
			
			for(BomCustomerVerificationDTO info : result){
				verifyResult.setBomCustNum(info.getBomCustNum());
				verifyResult.setBomFirstName(info.getBomFirstName());
				verifyResult.setBomLastName(info.getBomLastName());
				verifyResult.setBomIdDocType(info.getBomIdDocType());
				verifyResult.setBomIdDocNum(info.getBomIdDocNum());
				verifyResult.setBomSystemId(info.getBomSystemId());
				
				if(!info.getBomFirstName().replaceAll(" ", "").equalsIgnoreCase(pCustFirstName.replaceAll(" ", ""))
					|| !info.getBomLastName().replaceAll(" ", "").equalsIgnoreCase(pCustLastName.replaceAll(" ", ""))){
					verifyResult.setAlertMessage("Customer name mismatches the input of the customer name!");
					verifyResult.setMatchWithBomName(false);
					return verifyResult;
				}
			}
		}
		verifyResult.setAlertMessage("");
		verifyResult.setMatchWithBomName(true);
		return verifyResult;
	}
}
