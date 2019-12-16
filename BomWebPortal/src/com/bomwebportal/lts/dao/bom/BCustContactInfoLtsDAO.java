package com.bomwebportal.lts.dao.bom;

import java.sql.Types;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;

import com.bomwebportal.dao.BaseDAO;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.lts.dto.order.ContactLtsDTO;

public class BCustContactInfoLtsDAO extends BaseDAO{
	
	public void updateCustContactInfo(String orderId, ContactLtsDTO contactLtsDTO, String userId, String salesChannel)  throws DAOException {
		
		if(contactLtsDTO != null){
			String errText = null;
			SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
					.withSchemaName("OPS$BOM")
					.withCatalogName("B_ORD_BSBE_LTS_ACQ_PKG")
					.withProcedureName("update_customer_contact_info")
					.declareParameters(
							new SqlParameter("iv_order_id", Types.VARCHAR),
							new SqlParameter("iv_cust_no", Types.VARCHAR),
							new SqlParameter("iv_create_by", Types.VARCHAR),
							new SqlParameter("iv_commit_flag", Types.VARCHAR),
							new SqlParameter("iv_contact_name", Types.VARCHAR),
							new SqlParameter("iv_title", Types.VARCHAR),
							new SqlParameter("iv_contact_fixed_line", Types.VARCHAR),
							new SqlParameter("iv_email_addr", Types.VARCHAR),
							new SqlParameter("iv_contact_mobile", Types.VARCHAR),
							new SqlParameter("iv_sales_channel", Types.VARCHAR),
							new SqlOutParameter("on_RetVal", Types.VARCHAR),
							new SqlOutParameter("on_ErrCode", Types.VARCHAR),
							new SqlOutParameter("ov_ErrText", Types.VARCHAR));
					jdbcCall.setAccessCallParameterMetaData(false);
					MapSqlParameterSource inMap = new MapSqlParameterSource();
					inMap.addValue("iv_order_id", orderId);
					inMap.addValue("iv_cust_no", contactLtsDTO.getCustNo());	
					inMap.addValue("iv_create_by", userId);
					inMap.addValue("iv_commit_flag", "N");
					inMap.addValue("iv_contact_name", contactLtsDTO.getContactName());
					inMap.addValue("iv_title", contactLtsDTO.getTitle());
					inMap.addValue("iv_contact_fixed_line", contactLtsDTO.getContactFixedLine());
					inMap.addValue("iv_email_addr", contactLtsDTO.getEmailAddr());
					inMap.addValue("iv_contact_mobile", contactLtsDTO.getContactMobile());
					inMap.addValue("iv_sales_channel", salesChannel);
					Map<String, Object> out = null;
			try {
				out = jdbcCall.execute(inMap);
				if (out != null && !StringUtils.equals((String)out.get("on_ErrCode"), "0")) {
					errText = (String)out.get("ov_ErrText");
				}
			} catch (Exception e) {
				logger.error("Exception caught in updateCustContactInfo(), error "+errText+", ", e);
				throw new DAOException(e.getMessage(), e);
			}
		}
		
	}
}
