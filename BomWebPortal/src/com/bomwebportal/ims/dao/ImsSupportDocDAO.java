package com.bomwebportal.ims.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.bomwebportal.dao.BaseDAO;
import com.bomwebportal.dto.DocTypeDTO;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.ims.dto.ImsSupportDocDTO;


public class ImsSupportDocDAO extends BaseDAO{

	public String isImsShowChangeLang() throws DAOException{		
		String result;
		try{
			String SQL = " select code from w_code_lkup WHERE GRP_ID = 'IMS_CHANGE_LANG'";		
			result = simpleJdbcTemplate.queryForObject(SQL, String.class);			
			logger.info("isImsShowChangeLang:"+result);
			return result;	
		}catch(Exception e){
			logger.error("Exception caught in isImsShowChangeLang()", e);
			return "N";
		}	
	}

	public String isSupportDocCollected(String orderId, String docType) throws DAOException{
		
		String isCollected;
		try{
			String SQL = " select decode(count(*), 0, 'N', 'Y') " +
						 " from  bomweb_all_ord_doc_assgn " +
						 " where order_id = ?" +
						 " and doc_type = ? " +
						 " and collected_ind = 'Y'";
		
			isCollected = simpleJdbcTemplate.queryForObject(SQL, String.class, orderId, docType);
			
			return isCollected;	
		}catch(Exception e){
			logger.error("Exception caught in isSupportDocCollected()", e);
			return "N";
		}	
	}
	
	private final static ParameterizedRowMapper<ImsSupportDocDTO> docTypeDTOMapper = new ParameterizedRowMapper<ImsSupportDocDTO>() {
		public ImsSupportDocDTO mapRow(ResultSet rs, int rowNum)
				throws SQLException {
			ImsSupportDocDTO dto = new ImsSupportDocDTO();
			dto.setPay_Mtd_Type(rs.getString("PAY_MTD_TYPE"));
			dto.setDis_Mode(rs.getString("DIS_MODE"));
			dto.setCreditCardNum(rs.getString("CC_NUM"));
			dto.setTHIRD_PARTY_IND(rs.getString("THIRD_PARTY_IND"));
			dto.setCC_HOLD_NAME(rs.getString("CC_HOLD_NAME"));
			dto.setCC_EXP_DATE(rs.getString("CC_EXP_DATE"));
			return dto;
		}
	};
	

	public ImsSupportDocDTO getImsSupportDoc(String orderId) throws DAOException {
		
		String SQL = "select bp.CC_NUM, bp.PAY_MTD_TYPE, bo.DIS_MODE, bp.THIRD_PARTY_IND , bp.CC_HOLD_NAME , bp.CC_EXP_DATE  \n"
			+" from bomweb_order bo, bomweb_payment bp\n"
			+" where bo.order_id = bp.order_id\n"
			+" AND bo.order_id=:orderId " ;

		List<ImsSupportDocDTO> list = new ArrayList<ImsSupportDocDTO>();
		
		try {
			logger.debug("getImsSupportDoc @ ImsSupportDocDAO: \n" + SQL);

			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("orderId", orderId);
			list = simpleJdbcTemplate.query(SQL, docTypeDTOMapper, params);

		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException");
			list = null;
			return null;
		} catch (Exception e) {
			logger.error("Exception caught in getImsSupportDoc()", e);
			throw new DAOException(e.getMessage(), e);
		}

		return (list.size() > 0 )? list.get(0) : null;	
	}
	
}
