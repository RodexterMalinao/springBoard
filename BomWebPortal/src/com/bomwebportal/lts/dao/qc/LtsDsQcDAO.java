package com.bomwebportal.lts.dao.qc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.bomwebportal.dao.BaseDAO;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.ims.dto.ImsAlertMsgDTO;
import com.bomwebportal.lts.dto.qc.LtsDsQcProcessDTO;
import com.bomwebportal.lts.dto.qc.LtsDsQcSearchCriteriaDTO;

public class LtsDsQcDAO extends BaseDAO {

	private final String SQL_LTS_QC_ENQUIRY = 
			"SELECT o.ORDER_ID, ASSIGNEE, ASSIGN_DATE, sqa.SYS_F, sqa.LOB, oso.LOGIN_ID, c.ID_DOC_NUM, " +
			"DECODE(c.ID_DOC_TYPE, 'BS', c.company_name, c.last_name || ' ' || c.first_name)  AS CUST_NAME, " +
			"p.PAY_MTD_TYPE, p.THIRD_PARTY_IND, ai.BUILDING_TYPE, " +
			"to_char(o.APP_DATE,'YYYY/MM/DD HH24:MI:SS') APP_DATE, " +
			"to_char(o.SRV_REQ_DATE,'YYYY/MM/DD HH24:MI:SS') SRV_REQ_DATE, " +
			"to_char(o.SIGN_OFF_DATE,'YYYY/MM/DD HH24:MI:SS') SIGN_OFF_DATE, " +
			"o.SALES_CD, o.STAFF_NUM, o.SALES_CHANNEL, o.SALES_TEAM, w.DESCRIPTION SB_STATUS, OQA.QC_FINDINGS, oqa.QC_STATUS, " +
			"nvl((select distinct 'Y' from BOMWEB_ORDER_LTS_AMEND_DTL where order_id = o.order_id),'N') AMEND_IND " +
			"FROM BOMWEB_ORDER O, BOMWEB_SERVICE_ACCT_ASSGN SAA, bomweb_acct A, BOMWEB_CUSTOMER C, " +
			"BOMWEB_SALES_QC_ASSIGN SQA, BOMWEB_ORDER_QC_ASSIGN OQA, bomweb_order_latest_status ols, " +
			"BOMWEB_ORDER_SERVICE_OTHER oso, BOMWEB_PAYMENT p, bomweb_addr_inventory ai, w_code_lkup w " +
			"WHERE O.ORDER_ID = SQA.ORDERS_ID (+) " +
			"AND o.order_id = OQA.orderS_id (+) " +
			"AND O.ORDER_ID = C.ORDER_ID " +
			"AND o.order_id = a.order_id " +
			"AND o.order_id = saa.order_id " +
			"AND o.order_id = ols.order_id " +
			"AND o.order_id = oso.order_id(+) " +
			"AND o.order_id = p.order_id " +
			"AND o.order_id = ai.order_id " +
			"AND( p.action = 'I' OR p.action = ' ') "+
			"AND saa.acct_no = a.acct_no " +
			"AND a.cust_no = c.cust_no " +
			"AND saa.dtl_id = ols.dtl_id " +
			"AND (saa.ACTION = 'I' OR saa.ACTION = ' ') " +
			"AND saa.chrg_type = 'R' " +
			"AND saa.dtl_id = 1 " +
			"AND w.grp_id = 'LTS_QC_ORD_STATUS' " +
			"AND w.code = ols.SB_STATUS ";
			
	
	private final String SQL_LTS_QC_PROCESS_DETAIL = 
			"SELECT OLS.ORDER_ID, OL.QC_CALL_TIME, OQA.QC_DATE, OQA.QC_FINDINGS, " + 
			"OQA.ORDER_DISTRICT, OQA.ORDER_PLACE, OQA.QC_STATUS, OQA.REASON_CODE, " + 
			"SQA.ASSIGNEE, TO_CHAR(SQA.ASSIGN_DATE,'YYYY/MM/DD HH24:MI:SS') ASSIGN_DATE, " +
			"OL.MUST_QC, OLS.SB_STATUS " + 
			"FROM BOMWEB_ORDER_LTS OL, BOMWEB_ORDER_QC_ASSIGN OQA, " +
			"(SELECT * FROM BOMWEB_SALES_QC_ASSIGN  " +
			" WHERE ORDERS_ID = ? AND ROWNUM = 1 ORDER BY create_date DESC) SQA, " +
			"bomweb_order_latest_status ols " + 
			"WHERE OL.ORDER_ID = OQA.ORDERS_ID (+) " + 
			"AND OL.ORDER_ID = SQA.ORDERS_ID " +
			"AND OL.ORDER_ID = ? " +
			"AND OLS.ORDER_ID = OL.ORDER_ID " +
			"AND OLS.DTL_ID = 1 "; 
	
	private final String SQL_LTS_INSERT_QC_PROCESS = 
			"Insert into BOMWEB_ORDER_QC_ASSIGN "+
			"(ORDERS_ID, QC_DATE, QC_FINDINGS, ORDER_DISTRICT, ORDER_PLACE, QC_Status, Reason_code, " +
			"CREATE_DATE, CREATE_BY, LAST_UPD_DATE, LAST_UPD_BY) Values "+
			"(:orderId, TO_DATE( :qcDate ,'YYYY/MM/DD HH24:MI:SS'), :qcFindings, :ordDist, :orderPlace, " +
			":qcStatus, :reasonCode, sysdate, :createBy , sysdate, :lastUpdBy)";
	
	private final String SQL_LTS_GET_CHANNEL_CODE = 
			"select distinct(channel_cd) from bomweb_sales_assignment "+
			"where channel_id = ? ";

	public List<ImsAlertMsgDTO> searchLtsQcOrder( LtsDsQcSearchCriteriaDTO pQuiteria, Integer userChannelId) throws DAOException{
		
		List<ImsAlertMsgDTO> result = new ArrayList<ImsAlertMsgDTO>();
		MapSqlParameterSource params = new MapSqlParameterSource();

		StringBuilder sqlSb = new StringBuilder(SQL_LTS_QC_ENQUIRY);
		
		ParameterizedRowMapper<ImsAlertMsgDTO> mapper = new ParameterizedRowMapper<ImsAlertMsgDTO>(){
			public ImsAlertMsgDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				ImsAlertMsgDTO result = new ImsAlertMsgDTO();
				result.setOrderId((String) rs.getString("ORDER_ID"));
				result.setAssignee((String) rs.getString("ASSIGNEE"));
				result.setAssignDate((String) rs.getString("ASSIGN_DATE"));
				result.setCustName((String) rs.getString("CUST_NAME"));
				result.setAppDate((String) rs.getString("APP_DATE"));
				result.setServiceReqDate((String) rs.getString("SRV_REQ_DATE"));
				result.setSignoffDate((String) rs.getString("SIGN_OFF_DATE"));
				result.setSalesCd((String) rs.getString("SALES_CD"));
				result.setOrderStatus((String) rs.getString("SB_STATUS"));
				result.setSalesChannel((String) rs.getString("SALES_CHANNEL"));
				result.setSalesTeam((String) rs.getString("SALES_TEAM"));
				result.setCreateBy((String) rs.getString("STAFF_NUM"));
				result.setQcRemarks((String) rs.getString("QC_FINDINGS"));
				result.setQcStatus((String) rs.getString("QC_STATUS"));
				result.setSysF((String) rs.getString("SYS_F"));
				result.setLoginId((String) rs.getString("LOGIN_ID"));
				result.setIdDocNum((String) rs.getString("ID_DOC_NUM"));
				result.setPaymentMtd((String) rs.getString("PAY_MTD_TYPE"));
				result.setHousingType((String) rs.getString("BUILDING_TYPE"));
				result.setIs3rdParty((String) rs.getString("THIRD_PARTY_IND"));
				result.setAmendment((String) rs.getString("AMEND_IND"));
				
				return result;
			}
		};

		if(StringUtils.isNotBlank(pQuiteria.getOrderId())){
			sqlSb.append("AND o.ORDER_ID = :orderId ");
			params.addValue("orderId", pQuiteria.getOrderId());
		}else{
			String dateName = null;
			if(StringUtils.equals(pQuiteria.getDateType(), "S")){
				dateName = "o.SRV_REQ_DATE";
			}else if(StringUtils.equals(pQuiteria.getDateType(), "A")){
				dateName = "o.APP_DATE";
			}if(StringUtils.equals(pQuiteria.getDateType(), "F")){
				dateName = "o.SIGN_OFF_DATE";
			}
		
			if(StringUtils.isNotBlank(dateName) && StringUtils.isNotBlank(pQuiteria.getStartDate())){
				sqlSb.append("AND " + dateName + " > TO_DATE(:startDate, 'dd/mm/yyyy') ");
				params.addValue("startDate", pQuiteria.getStartDate());
			}
			if(StringUtils.isNotBlank(dateName) && StringUtils.isNotBlank(pQuiteria.getEndDate())){
				sqlSb.append("AND " + dateName + " < TO_DATE(:endDate, 'dd/mm/yyyy')+1 ");
				params.addValue("endDate", pQuiteria.getEndDate());
			}
		}
		 
		if(pQuiteria.getOrderStatusList() != null && pQuiteria.getOrderStatusList().size() > 0){
			sqlSb.append("AND ols.SB_STATUS in (:orderStatus) ");
			params.addValue("orderStatus", pQuiteria.getOrderStatusList());
		}

		if(StringUtils.isNotBlank(pQuiteria.getQcStatus())){
			sqlSb.append("AND oqa.QC_STATUS = :qcStatus ");
			params.addValue("qcStatus", pQuiteria.getQcStatus());
		}

		if(StringUtils.isNotBlank(pQuiteria.getAssignee())){
			sqlSb.append("AND ASSIGNEE = :assignee ");
			params.addValue("assignee", pQuiteria.getAssignee());
		}
		
		if(StringUtils.isNotBlank(pQuiteria.getIdDocNum())){
			sqlSb.append("AND C.ID_DOC_NUM = :idDocNum ");
			params.addValue("idDocNum", pQuiteria.getIdDocNum());
			
			if(StringUtils.isNotBlank(pQuiteria.getIdDocType())){
				sqlSb.append("AND C.ID_DOC_TYPE = :idDocType ");
				params.addValue("idDocType", pQuiteria.getIdDocType());
			}
			
		}
		
		if(StringUtils.isNotBlank(pQuiteria.getHouseType())){
			sqlSb.append("AND UPPER(ai.BUILDING_TYPE) = :houseType ");
			params.addValue("houseType", pQuiteria.getHouseType());
		}
		
		if(StringUtils.isNotBlank(pQuiteria.getSalesCode())){
			sqlSb.append("AND o.SALES_CD = :salesCode ");
			params.addValue("salesCode", pQuiteria.getSalesCode());
		}
		
		if(StringUtils.isNotBlank(pQuiteria.getTeamCode())){
			sqlSb.append("AND o.SALES_TEAM = :teamCode ");
			params.addValue("teamCode", pQuiteria.getTeamCode());
		}
		
		if(pQuiteria.getSalesChannel() != null && pQuiteria.getSalesChannel().size() > 0){
			sqlSb.append("AND o.create_channel in (:salesChannelList) ");
			params.addValue("salesChannelList", pQuiteria.getSalesChannel());
		}
		
		if(StringUtils.isNotBlank(pQuiteria.getAmendment())){
			if(StringUtils.equals(pQuiteria.getAmendment(), "Y")){
				sqlSb.append("AND exists (select 1 from BOMWEB_ORDER_LTS_AMEND_DTL where order_id = o.order_id)");
			}else if(StringUtils.equals(pQuiteria.getAmendment(), "N")){
				sqlSb.append("AND not exists (select 1 from BOMWEB_ORDER_LTS_AMEND_DTL where order_id = o.order_id)");
			}
		}
		
		if(StringUtils.isNotBlank(pQuiteria.getIsThirdParty())&& StringUtils.isNotBlank(pQuiteria.getPayMethod()) ){
			if(StringUtils.equals(pQuiteria.getIsThirdParty(), "Y") && StringUtils.equals(pQuiteria.getPayMethod(), "C")){
				sqlSb.append("AND p.PAY_MTD_TYPE = 'C' AND p.THIRD_PARTY_IND= 'Y' ");
			}
		}
	
		if(StringUtils.isNotBlank(pQuiteria.getIsFilterAssigned()) && StringUtils.equals(pQuiteria.getIsFilterAssigned(), "Y")){
			sqlSb.append("AND not exists (select 1 from BOMWEB_SALES_QC_ASSIGN where orders_id = o.order_id) ");
		}
		
		if(pQuiteria.isAssigned()){
			sqlSb.append("AND o.order_id = SQA.orderS_id ");
		}
		
		try{
			result = simpleJdbcTemplate.query(sqlSb.toString(), mapper, params);
		}catch (EmptyResultDataAccessException erdae) {
			logger.debug("EmptyResultDataAccessException");
			result = null;
		} catch (Exception e) {
			logger.debug("Exception caught in searchLtsQcOrder():", e);
			throw new DAOException(e.getMessage(), e);
		}	
		
		return result;
	}
	
	public LtsDsQcProcessDTO getQcProcessDetail(String orderId) throws DAOException{
		StringBuilder sqlSb = new StringBuilder(SQL_LTS_QC_PROCESS_DETAIL);
		
		ParameterizedRowMapper<LtsDsQcProcessDTO> mapper = new ParameterizedRowMapper<LtsDsQcProcessDTO>(){
			public LtsDsQcProcessDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				LtsDsQcProcessDTO result = new LtsDsQcProcessDTO();
				result.setOrderId((String) rs.getString("ORDER_ID"));
				result.setAssignee((String) rs.getString("ASSIGNEE"));
				result.setAssignDate((String) rs.getString("ASSIGN_DATE"));
				result.setQcRemarks((String) rs.getString("QC_FINDINGS"));
				result.setQcStatus((String) rs.getString("QC_STATUS"));
				result.setOrderDistrict((String) rs.getString("ORDER_DISTRICT"));
				result.setOrderPlace((String) rs.getString("ORDER_PLACE"));
				result.setMustQc((String) rs.getString("MUST_QC"));
				result.setSbStatus((String) rs.getString("SB_STATUS"));
//				result.setSysF((String) rs.getString("SYS_F"));
				return result;
			}
		};
		
		try{
			return simpleJdbcTemplate.queryForObject(sqlSb.toString(), mapper, orderId, orderId);
		}catch (EmptyResultDataAccessException erdae) {
			logger.debug("EmptyResultDataAccessException");
			return null;
		} catch (Exception e) {
			logger.debug("Exception caught in getQcProcessDetail():", e);
			throw new DAOException(e.getMessage(), e);
		}	
	}


public boolean isQcProcessExist(String orderId) throws DAOException{
	String sql = "SELECT count(*) FROM BOMWEB_ORDER_QC_ASSIGN WHERE ORDERS_ID = ?";
	try{
		int count = simpleJdbcTemplate.queryForInt(sql, orderId);
		return count > 0;
	}catch (Exception e) {
		logger.error("Exception caught in isQcProcessExist()", e);
		throw new DAOException(e.getMessage(), e);
	}	
}

public void insertQcProcess(String orderId, String qcDate, String qcFindings, String district, String place, 
		String qcStatus, String reasonCode, String userId) throws DAOException{
	StringBuilder sqlSb = new StringBuilder(SQL_LTS_INSERT_QC_PROCESS);

	MapSqlParameterSource params = new MapSqlParameterSource();
	params.addValue("orderId", orderId);
	params.addValue("qcDate", qcDate);
	params.addValue("qcFindings", qcFindings);
	//params.addValue("qcCallTime", imsDsQcProcessDetailUI.getQcCallTime());
	params.addValue("ordDist", district);
	params.addValue("orderPlace", place);
	params.addValue("qcStatus", qcStatus);
	logger.debug("qcStatus: " + qcStatus);
	params.addValue("reasonCode", reasonCode);
	params.addValue("createBy", userId);
	params.addValue("lastUpdBy", userId);
	
	try{
		logger.debug("insertQcProcess : " + sqlSb.toString());
		simpleJdbcTemplate.update(sqlSb.toString(), params);
		
	}catch (Exception e) {
		logger.error("Exception caught in insertQcProcess()", e);
		throw new DAOException(e.getMessage(), e);
	}		
}


public void updateQcProcess(String orderId, String qcDate, String qcFindings, String district, String place, 
		String qcStatus, String reasonCode, String userId) throws DAOException{
	
	if(StringUtils.isBlank(orderId)){
		return;
	}
	
	StringBuilder sqlSb = new StringBuilder("update BOMWEB_ORDER_QC_ASSIGN ");
	sqlSb.append("SET ");
	
	MapSqlParameterSource params = new MapSqlParameterSource();
	
	int updatedFieldCount = 0;
	if(StringUtils.isNotBlank(qcDate)){
		sqlSb.append("qc_date = TO_DATE( :qcDate ,'YYYY/MM/DD HH24:MI:SS'), ");
		params.addValue("qcDate", qcDate);
		updatedFieldCount++;
	}

	if(StringUtils.isNotBlank(qcFindings)){
		sqlSb.append("qc_Findings = :qcFindings || chr(10) || chr(10) || qc_Findings, ");
		params.addValue("qcFindings", qcFindings);
		updatedFieldCount++;
	}

	if(StringUtils.isNotBlank(district)){
		sqlSb.append("order_district = :district, ");
		params.addValue("district", district);
		updatedFieldCount++;
	}
	if(StringUtils.isNotBlank(place)){
		sqlSb.append("order_place = :place, ");
		params.addValue("place", place);
		updatedFieldCount++;
	}
	if(StringUtils.isNotBlank(qcStatus)){
		sqlSb.append("qc_status = :qcStatus, ");
		params.addValue("qcStatus", qcStatus);
		updatedFieldCount++;
	}
	if(StringUtils.isNotBlank(reasonCode)){
		sqlSb.append("Reason_code = :reasonCode, ");
		params.addValue("reasonCode", reasonCode);
		updatedFieldCount++;
	}

	sqlSb.append("LAST_UPD_BY = :lastUpdBy, ");
	params.addValue("lastUpdBy", userId);
	sqlSb.append("LAST_UPD_DATE = sysdate ");
	sqlSb.append("where orders_id = :orderId ");
	params.addValue("orderId", orderId);

	logger.debug("updatedFieldCount: " + updatedFieldCount);
	if(updatedFieldCount == 0){
		return;
	}

	try{
		logger.debug("updateQcProcess: " + sqlSb.toString());
		simpleJdbcTemplate.update(sqlSb.toString(), params);
	}catch (Exception e) {
		logger.error("Exception caught in updateQcProcess()", e);
		throw new DAOException(e.getMessage(), e);
	}
}

public List<String> getSalesChannelCodeByChannelID(int channelID)throws DAOException{
	
	ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>(){
		public String mapRow(ResultSet rs, int rowNum)
				throws SQLException {
			return (String)rs.getString("CHANNEL_CD");
		}
	};

	try{
		return simpleJdbcTemplate.query(SQL_LTS_GET_CHANNEL_CODE, mapper, channelID);
	}catch (EmptyResultDataAccessException erdae) {
		logger.debug("EmptyResultDataAccessException");
		return null;
	} catch (Exception e) {
		logger.debug("Exception caught in searchLtsQcOrder():", e);
		throw new DAOException(e.getMessage(), e);
	}	
}
}
