package com.bomwebportal.lts.dao.order;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import java.util.Map;

import oracle.jdbc.OracleTypes;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;

import com.bomwebportal.dao.BaseDAO;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.lts.dto.order.ContactLtsDTO;
import com.bomwebportal.lts.dto.order.ImsSbOrderDTO;

public class ImsSbOrderDAO extends BaseDAO {

	private final Log logger = LogFactory.getLog(getClass());
	
	private static String SQL_CREATE_IMS_ORDER = 
			"insert into bomweb_order_ims (order_id, login_id, process_vim, adult_view_allow, lang_preference, fixed_line_no, source_cd, applmthd, create_by, create_date, last_upd_by, last_upd_date, ims_order_type, field_ind) " +
			"values (?, ?, ?, ?, ?, ?, ?, ?, ?, sysdate, ?, sysdate, ?, ?)";
	
	private static String SQL_CREATE_IMS_CONTACT_INFO = 
			"insert into bomweb_contact (order_id, contact_name, contact_phone, email_addr, create_date, create_by, last_upd_date, last_upd_by, cust_no) " +
			"values (?, ?, ?, ?, sysdate, ?, sysdate, ?, ?)"; 
	
	private static String SQL_GET_FSA_NUM_ON_IMS_ORDER =
			"select service_num " +
			"from bomweb_customer " +
			"where lob = 'IMS' and order_id = ?";
	
	private static String SQL_GET_LANG_PREFERENCE = 
			"select lang_preference " +
			"from bomweb_order_ims " +
			"where order_id = ?";
	
	private static String SQL_UPDATE_IMS_CONTACT_INFO = 
		    "update bomweb_contact set title = ?, contact_name = ?, contact_phone = ?, " + 
		    "email_addr = ?, other_phone = ?, contact_mobile = ?, contact_fixed_line = ?, " +
		    "last_upd_by = ?, last_upd_date = sysdate " +
		    "where order_id = ? ";	
	
	private static String SQL_GET_PRE_INSTALL_IND = 
			"select pre_inst_ind from bomweb_order_ims " +
			" where order_id = :orderid";
	
	public void createEyeImsOrder(String pOrderId, String pLoginId, String pVimInd, String pViewAvInd, String pLangPref, String pSrvNum, String pSourceCd, String pApplMethod, String pUser, String pImsOrderType, String pFieldInd) throws DAOException {
		
		try {
			this.simpleJdbcTemplate.update(SQL_CREATE_IMS_ORDER, pOrderId, pLoginId, pVimInd, pViewAvInd, pLangPref, pSrvNum, pSourceCd, pApplMethod, pUser, pUser, pImsOrderType, pFieldInd);
		} catch (Exception e) {
			logger.error("Error in createEyeImsOrder()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public void createImsContact(String pOrderId, String pContactName, String pContactNum, String pEmail, String pUser, String pCustNum) throws DAOException {
		
		try {
			this.simpleJdbcTemplate.update(SQL_CREATE_IMS_CONTACT_INFO, pOrderId, pContactName, pContactNum, pEmail, pUser, pUser, pCustNum);
		} catch (Exception e) {
			logger.error("Error in createImsContact()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
    public void updateImsContact(String pOrderId, ContactLtsDTO contact, String pUser) throws DAOException {
		
		try {
			this.simpleJdbcTemplate.update(SQL_UPDATE_IMS_CONTACT_INFO, 
				contact.getTitle(), contact.getContactName(), contact.getContactPhone(), 
				contact.getEmailAddr(), contact.getOtherPhone(), contact.getContactMobile(), 
				contact.getContactFixedLine(), pUser, pOrderId);
		} catch (Exception e) {
			logger.error("Error in updateImsContact()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public ImsSbOrderDTO getPcdSbOrder(String pIOrderId) throws DAOException {
		
		ImsSbOrderDTO imsSbOrderDTO = new ImsSbOrderDTO();
		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
			.withSchemaName("OPS$CNM")
			.withCatalogName("PKG_SB_IMS_LTS")
			.withProcedureName("get_pcd_sb_order")
			.declareParameters(
				new SqlParameter("i_order_id", Types.VARCHAR),
				new SqlParameter("order_detail_cursor", OracleTypes.CURSOR),
				new SqlOutParameter("order_detail_cursor", OracleTypes.CURSOR, new OrderDetailMapper()),
				new SqlOutParameter("gnRetVal", Types.INTEGER),
				new SqlOutParameter("gnErrCode", Types.INTEGER),
				new SqlOutParameter("gsErrText", Types.VARCHAR)
				);
		MapSqlParameterSource inMap = new MapSqlParameterSource();
		inMap.addValue("i_order_id", pIOrderId);
		inMap.addValue("order_detail_cursor", null);	
		Map<String, Object> out = null;
		
		try {
			out = jdbcCall.execute(inMap);
		} catch (Exception e) {
			logger.error("Exception caught in getPcdSbOrder()", e);
			throw new DAOException(e.getMessage(), e);
		}
		@SuppressWarnings("unchecked")
		List<ImsSbOrderDTO> addrDtlList = (List<ImsSbOrderDTO>)out.get("order_detail_cursor");
		
		if ((Integer)out.get("gnErrCode") != 0) {
			imsSbOrderDTO.setRetVal(out.get("gnRetVal").toString());
			imsSbOrderDTO.setErrCode(out.get("gnErrCode").toString());
			imsSbOrderDTO.setErrText((String)out.get("gsErrText"));
		} else if(addrDtlList.size()==0) {
			imsSbOrderDTO = null;
		} else if(addrDtlList.size()!=0) {
			imsSbOrderDTO = addrDtlList.get(0);
		}
		return imsSbOrderDTO;
	}
	
	public final class OrderDetailMapper implements RowMapper {
	    
	    public ImsSbOrderDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
	    	ImsSbOrderDTO imsSbOrderDTO = new ImsSbOrderDTO();
	    	imsSbOrderDTO.setOrderId(rs.getString("order_id"));
	    	imsSbOrderDTO.setIdDocType(rs.getString("ID_DOC_TYPE"));
	    	imsSbOrderDTO.setIdDocNum(rs.getString("ID_DOC_NUM"));
	    	imsSbOrderDTO.setSerbdyno(rs.getString("serbdyno"));
	    	imsSbOrderDTO.setUnitNo(rs.getString("unit_no"));
	    	imsSbOrderDTO.setFloorNo(rs.getString("floor_no"));
	    	imsSbOrderDTO.setHiLotNo(rs.getString("hi_lot_no"));
	    	imsSbOrderDTO.setBuildNo(rs.getString("BUILD_NO"));
	    	imsSbOrderDTO.setStrNo(rs.getString("STR_NO"));
	    	imsSbOrderDTO.setStrName(rs.getString("STR_NAME"));
	    	imsSbOrderDTO.setStrCatCd(rs.getString("STR_CAT_CD"));
	    	imsSbOrderDTO.setStrCatDesc(rs.getString("STR_CAT_DESC"));
	    	imsSbOrderDTO.setSectCd(rs.getString("SECT_CD"));
	    	imsSbOrderDTO.setSectDesc(rs.getString("SECT_DESC"));
	    	imsSbOrderDTO.setDistNo(rs.getString("DIST_NO"));
	    	imsSbOrderDTO.setDistDesc(rs.getString("DIST_DESC"));
	    	imsSbOrderDTO.setAreaCd(rs.getString("AREA_CD"));
	    	imsSbOrderDTO.setAreaDesc(rs.getString("AREA_DESC"));
	    	imsSbOrderDTO.setSerialNum(rs.getString("SERIAL_NUM"));
	    	imsSbOrderDTO.setAppntStartTime(rs.getString("APPNT_START_TIME"));
	    	imsSbOrderDTO.setAppntEndTime(rs.getString("APPNT_END_TIME"));
	    	imsSbOrderDTO.setInstContactName(rs.getString("INST_CONTACT_NAME"));
	    	imsSbOrderDTO.setInstContactNum(rs.getString("INST_CONTACT_NUM"));
	    	imsSbOrderDTO.setInstSmsNum(rs.getString("INST_SMS_NUM"));
	    	imsSbOrderDTO.setSrvType(rs.getString("srv_type"));
	    	imsSbOrderDTO.setTechnology(rs.getString("technology"));
	    	imsSbOrderDTO.setBandwidth(rs.getString("bandwidth"));
	    	imsSbOrderDTO.setSrvReqDate(rs.getString("SRV_REQ_DATE"));
	    	imsSbOrderDTO.setAppntType(rs.getString("APPNT_TYPE"));
	        return imsSbOrderDTO;
	    }
	}
	
	public String getFsaNumOnImsSbOrder(String pImsSbOrderId) throws DAOException {
		
		try {
			return this.simpleJdbcTemplate.queryForObject(SQL_GET_FSA_NUM_ON_IMS_ORDER, String.class, pImsSbOrderId);
		} catch (EmptyResultDataAccessException erdae) {
			return null;
		} catch (Exception e) {
			logger.error("Error in getFsaNumOnImsSbOrder()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public String getLangPreferenceImsSbOrder(String pOrderId) throws DAOException {
		
		try {
			return this.simpleJdbcTemplate.queryForObject(SQL_GET_LANG_PREFERENCE, String.class, pOrderId);
		} catch (EmptyResultDataAccessException erdae) {
			return null;
		} catch (Exception e) {
			logger.error("Error in getLangPreferenceImsSbOrder()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public String getPreInstallInd(String orderId) throws DAOException {
		try {
			return this.simpleJdbcTemplate.queryForObject(SQL_GET_PRE_INSTALL_IND, String.class, orderId);
		} catch (EmptyResultDataAccessException erdae) {
			return null;
		} catch (Exception e) {
			logger.error("Error in getPreInstallInd()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
}
