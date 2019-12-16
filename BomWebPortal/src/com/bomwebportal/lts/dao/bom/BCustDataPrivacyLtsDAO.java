package com.bomwebportal.lts.dao.bom;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;

import com.bomwebportal.dao.BaseDAO;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.lts.dto.order.CustOptOutInfoLtsDTO;
import com.bomwebportal.lts.dto.profile.CustPdpoProfileDTO;
import com.bomwebportal.lts.util.LtsConstant;
import com.bomwebportal.lts.util.LtsDateFormatHelper;

public class BCustDataPrivacyLtsDAO extends BaseDAO{
	final String SQL_GET_CUST_DATA_PRIVACY_INFO = "select * "+
			" from" +
			"   B_CUST_DATA_PRIVACY_LTS " +
			" where" +
			"   CUST_NUM = :custNum" +
			"   and SYSTEM_ID = :systemId";
	
	public CustPdpoProfileDTO[] getCustDataPrivacyInfo(String pCustNum, String pSystemId) throws DAOException {
		
		ParameterizedRowMapper<CustPdpoProfileDTO> mapper = new ParameterizedRowMapper<CustPdpoProfileDTO>() {
			public CustPdpoProfileDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				CustPdpoProfileDTO info = new CustPdpoProfileDTO();
				info.setCustNum(rs.getString("CUST_NUM"));
				info.setLob(rs.getString("BOM_LOB"));
				info.setOptInd(rs.getString("OPT_IND"));
				info.setBillInsert(rs.getString("BILL_INSERT"));
				info.setBm(rs.getString("BM"));
				info.setCdOutdial(rs.getString("CD_OUTDIAL"));
				info.setDatacast(rs.getString("DATACAST"));
				info.setDirectMailing(rs.getString("DIRECT_MAILING"));
				info.setEmail(rs.getString("EMAIL"));
				info.setOutbound(rs.getString("OUTBOUND"));
				info.setSms(rs.getString("SMS"));
				info.setSmsEye(rs.getString("SMS_EYE"));
				info.setWebBill(rs.getString("WEB_BILL"));
				return info;
			}
		};
		List<CustPdpoProfileDTO> dataPrivacyList = null;
		
		try {
			dataPrivacyList = simpleJdbcTemplate.query(SQL_GET_CUST_DATA_PRIVACY_INFO, mapper, pCustNum, pSystemId);
		} catch (EmptyResultDataAccessException erdae) {
			return null;
		} catch (Exception e) {
			logger.error("Error in getCustDataPrivacyInfo() cust num " + pCustNum, e);
			throw new DAOException(e.getMessage(), e);
		}
		return dataPrivacyList.toArray(new CustPdpoProfileDTO[dataPrivacyList.size()]);
	}
	
	// for upgrade/retention order
	public String updateCustDataPrivayInfo(CustOptOutInfoLtsDTO custOptoutInfo, String billAddress, String userId, boolean isCommit)  throws DAOException {
		String lob = LtsConstant.DATA_PRIVACY_BOM_LOB_LTS;
		return updateCustDataPrivayInfoWithLob(custOptoutInfo, billAddress, userId, isCommit, lob); 
	}
	
	public String updateCustDataPrivayInfoWithLob(CustOptOutInfoLtsDTO custOptoutInfo, String billAddress, String userId, boolean isCommit, String lob)  throws DAOException {
		
		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
				.withSchemaName("OPS$BOM")
				.withCatalogName("b_cc_data_privacy_lts_pkg")
				.withProcedureName("updateCustDataPrivacyBySB")
				.declareParameters(
						new SqlParameter("i_cust_num", Types.VARCHAR),
						new SqlParameter("i_system_id", Types.VARCHAR),
						new SqlParameter("i_bom_lob", Types.VARCHAR),
						new SqlParameter("i_opt_ind", Types.VARCHAR),
						new SqlParameter("i_direct_mailing", Types.VARCHAR),
						new SqlParameter("i_outbound", Types.VARCHAR),
						new SqlParameter("i_sms", Types.VARCHAR),
						new SqlParameter("i_email", Types.VARCHAR),
						new SqlParameter("i_web_bill", Types.VARCHAR),
						new SqlParameter("i_bill_insert", Types.VARCHAR),
						new SqlParameter("i_cd_outdial", Types.VARCHAR),
						new SqlParameter("i_bm", Types.VARCHAR),
						new SqlParameter("i_sms_eye", Types.VARCHAR),
						new SqlParameter("i_datacast", Types.VARCHAR),
						new SqlParameter("i_agree_date", Types.VARCHAR),
						new SqlParameter("i_media_type", Types.VARCHAR),
						new SqlParameter("i_media_value", Types.VARCHAR),
						new SqlParameter("i_sales_cd", Types.VARCHAR),
						new SqlParameter("i_last_upd_by", Types.VARCHAR),
						new SqlParameter("i_last_upd_process", Types.VARCHAR),
						new SqlParameter("i_commit_flag", Types.VARCHAR),
						new SqlOutParameter("o_error_code", Types.VARCHAR),
						new SqlOutParameter("o_error_msg", Types.VARCHAR));
				jdbcCall.setAccessCallParameterMetaData(false);
				MapSqlParameterSource inMap = new MapSqlParameterSource();
				inMap.addValue("i_cust_num", custOptoutInfo.getCustNo());
				inMap.addValue("i_system_id", LtsConstant.SYSTEM_ID_DRG);	
				inMap.addValue("i_bom_lob", lob);
				inMap.addValue("i_opt_ind", custOptoutInfo.getOptInd());
				inMap.addValue("i_direct_mailing", custOptoutInfo.getDirectMailing());	
				inMap.addValue("i_outbound", custOptoutInfo.getOutbound());	
				inMap.addValue("i_sms", custOptoutInfo.getSms());	
				inMap.addValue("i_email", custOptoutInfo.getEmail());	
				inMap.addValue("i_web_bill", custOptoutInfo.getWebBill());
				inMap.addValue("i_bill_insert", custOptoutInfo.getBillInsert());	
				inMap.addValue("i_cd_outdial", custOptoutInfo.getCdOutdial());	
				inMap.addValue("i_bm", custOptoutInfo.getBm());	
				inMap.addValue("i_sms_eye", custOptoutInfo.getSmsEye());
				inMap.addValue("i_datacast", null);
				inMap.addValue("i_agree_date", LtsDateFormatHelper.getSysDate("ddMMyyyy"));	
				inMap.addValue("i_media_type", null);
				inMap.addValue("i_media_value", null);
				inMap.addValue("i_sales_cd", userId);
				inMap.addValue("i_last_upd_by", userId);
				inMap.addValue("i_last_upd_process", "SBLTS");
				inMap.addValue("i_commit_flag", isCommit ? "Y" : "N");
				Map<String, Object> out = null;
		try {
			out = jdbcCall.execute(inMap);
			if (out != null && !StringUtils.equals((String)out.get("o_error_code"), "0")) {
				return (String)out.get("o_error_msg");
			}
		} catch (Exception e) {
			logger.error("Exception caught in getServiceByCustomer()", e);
			throw new DAOException(e.getMessage(), e);
		}
		return null;
	}
}
