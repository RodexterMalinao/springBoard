package com.bomwebportal.ims.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openuri.www.CustomerBasicInfoDTO;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.bomwebportal.dao.BaseDAO;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.ims.dto.ui.ContactUI;
import com.bomwebportal.ims.dto.ui.ImsInstallationUI;

public class GetImsCustomerDAO extends BaseDAO{
	protected final Log logger = LogFactory.getLog(getClass());
	
	public List<ImsInstallationUI> getImsCustomer(String idDocType, String idDocNum) throws DAOException{
		logger.info("getImsCustomer is called");
		logger.info("idDocType:" + idDocType);
		logger.info("idDocNum:" + idDocNum);
		
		
		try {
			/*String SQL = " SELECT A.TITLE, A.GENDER, A.CUST_FIRST_NAME, A.CUST_LAST_NAME, A.DATE_OF_BIRTH, C.CNCT_MEDIA_NUM, A.CUST_NUM " +
				         " FROM b_customer A, b_customer_contact_info B, b_customer_contact_method C " +
						 " WHERE A.CUST_NUM=B.CUST_NUM AND B.CUST_NUM=C.CUST_NUM(+) " + 
						 " AND A.ID_DOC_TYPE = ? " +
						 " AND A.ID_DOC_NUM = ? " +
						 " AND A.SYSTEM_ID='IMS' " +
						 " AND B.PRIMARY_IND='Y' AND B.CNTCT_KEY=C.CNTCT_KEY AND C.CNCT_MEDIA_TYPE='M' ";*/
			
			
			String SQL = " SELECT A.TITLE, A.GENDER, A.CUST_FIRST_NAME, A.CUST_LAST_NAME,				       	                            " +              
						"  A.DATE_OF_BIRTH, C.COMPANY_NAME, C.CONTACT_PER_NAME,                                                              " +                
						"            (SELECT C.CNCT_MEDIA_NUM FROM b_customer A, b_customer_contact_info B, b_customer_contact_method C      " +               
						"             WHERE B.CUST_NUM=A.CUST_NUM                                                                            " +               
						"               AND B.CUST_NUM=C.CUST_NUM                                                                            " +               
						"               AND A.ID_DOC_TYPE = ?                                                                	           " +                 
						"               AND A.ID_DOC_NUM = ?                                                                 		     " +             
						"               AND A.SYSTEM_ID='IMS'                                                                                " +               
						"               AND B.PRIMARY_IND='Y'                                                                                " +               
						"               AND B.CNTCT_KEY=C.CNTCT_KEY                                                                          " +         
						"               AND C.CNCT_MEDIA_TYPE='M') MOBILE_NUM,                                                               " +         
						"              (SELECT C.CNCT_MEDIA_NUM FROM b_customer A, b_customer_contact_info B, b_customer_contact_method C    " +               
						"               WHERE B.CUST_NUM=A.CUST_NUM                                                                          " +               
						"                 AND B.CUST_NUM=C.CUST_NUM                                                                          " +               
						"                 AND A.ID_DOC_TYPE = ?                                                             	            " +                 
						"                 AND A.ID_DOC_NUM = ?                                                          		          " +             
						"                 AND A.SYSTEM_ID='IMS'                                                                              " +               
						"                 AND B.PRIMARY_IND='Y'                                                                              " +               
						"                 AND B.CNTCT_KEY=C.CNTCT_KEY                                                                        " +               
						"                 AND C.CNCT_MEDIA_TYPE='P'                                                                          " +               
						"                 AND C.CNCT_MEDIA_KEY='1') FIX_LINE_NUM,                                                            " +               
						"               A.CUST_NUM                                                                                           " +               
						"  FROM b_customer A                                                                                                 " +         
						"  left join b_customer_details C                                                                                    " +
						"  on C.cust_num = A.cust_num                                                                                        " +
						"  WHERE A.ID_DOC_TYPE = ?                                                                 		                     " +                 
						"  AND A.ID_DOC_NUM = ?                                                                        			           " +             
						"  AND A.SYSTEM_ID='IMS'                                                                                            ";
			
			
			ParameterizedRowMapper<ImsInstallationUI> mapper = new ParameterizedRowMapper<ImsInstallationUI>() {
				public ImsInstallationUI mapRow(ResultSet rs, int rowNum) throws SQLException {
					ImsInstallationUI cust = new ImsInstallationUI();
					ContactUI contact = new ContactUI();
					cust.setContact(contact);
					cust.setTitle(rs.getString("TITLE"));
					cust.setLastName(rs.getString("CUST_LAST_NAME"));
					cust.setFirstName(rs.getString("CUST_FIRST_NAME"));
					cust.setDob(rs.getDate("DATE_OF_BIRTH"));
					cust.setCompanyName(rs.getString("COMPANY_NAME"));
					cust.setContactPersonName(rs.getString("CONTACT_PER_NAME"));
					
					if(rs.getString("MOBILE_NUM")==null){
						cust.getContact().setContactPhone("");
					}else{
						cust.getContact().setContactPhone(rs.getString("MOBILE_NUM"));
					}
					if(rs.getString("FIX_LINE_NUM")==null){
						cust.getContact().setOtherPhone("");
					}else{
						cust.getContact().setOtherPhone(rs.getString("FIX_LINE_NUM"));
					}
					cust.setCustNo(rs.getString("CUST_NUM"));
					
					return cust;
				}
			};
			
			return simpleJdbcTemplate.query(SQL, mapper, idDocType, idDocNum, idDocType, idDocNum, idDocType, idDocNum);
			
		}catch (Exception e) {
			logger.error("Exception caught in getImsCustomer()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	

	
	public List<CustomerBasicInfoDTO> getImsCustomerForRetention(String custNum) throws DAOException{
		logger.info("getImsCustomer is called, custNum:" + custNum);
		
		
		try {			
			String SQL 	
			 = "select cust_num, CUST_FIRST_NAME ,CUST_LAST_NAME ,ID_DOC_NUM,ID_DOC_TYPE ," +
			 		"  to_char(DATE_OF_BIRTH,'dd/MM/yyyy') DATE_OF_BIRTH,TITLE  " +
			 		"  from b_customer " +
			 		"  where cust_num =?  AND SYSTEM_ID='IMS'  ";
			

			ParameterizedRowMapper<CustomerBasicInfoDTO> mapper = new ParameterizedRowMapper<CustomerBasicInfoDTO>() {

				public CustomerBasicInfoDTO mapRow(ResultSet rs, int rowNum)
						throws SQLException {
					CustomerBasicInfoDTO dto = new CustomerBasicInfoDTO();
						
//					dto.setOrderId(rs.getString("order_id"));
//					dto.setCustNo(rs.getString("cust_no"));
//					dto.setMobCustNo(rs.getString("mob_cust_no"));
					dto.setFirstName(rs.getString("CUST_FIRST_NAME"));
					dto.setLastName(rs.getString("CUST_LAST_NAME"));
					dto.setIdDocType(rs.getString("ID_DOC_TYPE"));
					dto.setIdDocNum(rs.getString("ID_DOC_NUM"));
					dto.setDob(rs.getString("DATE_OF_BIRTH"));
					dto.setTitle(rs.getString("TITLE"));
//					dto.setCompanyName(rs.getString("company_name"));
					dto.setSystemId("IMS");
//					dto.setIndType(rs.getString("ind_type"));
//					dto.setIndSubType(rs.getString("ind_sub_type"));
//					dto.setNationality(rs.getString("nationality"));				
//					dto.setAddrProofInd(rs.getString("addr_proof_ind"));
//					dto.setLob(rs.getString("lob"));
//					dto.setServiceNum(rs.getString("service_num"));				
//					dto.setIdVerified(rs.getString("id_verified_ind"));
//					dto.setBlacklistInd(rs.getString("blacklist_ind"));	
//					dto.setCsPortalInd(rs.getString("cs_portal_ind"));///Gary
//					dto.setCsPortalLogin(rs.getString("cs_portal_login"));
//					dto.setCsPortalMobile(rs.getString("cs_portal_mobile"));

					return dto;
				}
			};
			
			return simpleJdbcTemplate.query(SQL, mapper, custNum);
			
		}catch (Exception e) {
			logger.error("Exception caught in getImsCustomerForRetention()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
}
