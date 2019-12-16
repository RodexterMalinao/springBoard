package com.bomwebportal.mob.ccs.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;

import com.bomwebportal.dao.BaseDAO;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.mob.ccs.dto.MobCcsBulkPrintDTO;

public class MobCcsBulkPrintDAO extends BaseDAO {
    protected final Log logger = LogFactory.getLog(getClass());
    
    public Date getNextNWorkingDay(Date jobDate, int nDay) throws DAOException {
		try {			
			SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
				.withSchemaName("OPS$CNM")
            	.withCatalogName("PKG_SB_MOB_UTIL")
            	.withFunctionName("GET_NEXT_N_WORKING_DAY");
			jdbcCall.setAccessCallParameterMetaData(false);
			jdbcCall.declareParameters(
					new SqlOutParameter("RESULT", Types.DATE)
					, new SqlParameter("i_job_date", Types.DATE)
					, new SqlParameter("i_n_day", Types.INTEGER));

			/*
FUNCTION get_next_n_working_day (
   i_job_date              IN DATE,
   i_n_day                 IN NUMBER
) RETURN DATE;
			 */
			
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("i_job_date", jobDate);
			params.addValue("i_n_day", nDay);
			
			Date nextNWorkingDay = jdbcCall.executeFunction(Date.class, params);
			
			if (logger.isInfoEnabled()) {
				logger.info("PKG_SB_MOB_UTIL.GET_NEXT_N_WORKING_DAY() output nextNWorkingDay = " + nextNWorkingDay);
			}
			
			return nextNWorkingDay;
			
		} catch (Exception e) {
			logger.error("Exception caught in getNextNWorkingDate()", e);
			throw new DAOException(e.getMessage(), e);
		}
    }

    public List<MobCcsBulkPrintDTO> getMobCcsBulkPrintDTOALLBySearch(Date processDate, List<Map<String, String>> statuses, String location) throws DAOException {
		logger.info(" getMobCcsBulkPrintDTOALLBySearch is called");
		List<MobCcsBulkPrintDTO> itemList = new ArrayList<MobCcsBulkPrintDTO>();
		
		String sql = "SELECT" + 
	    		" bcod.ORDER_ID" +
	    		" , bcod.LOCATION" +
	    		" , bo.OCID" +
	    		" , bo.LOB" +
	    		" , bc.FIRST_NAME CUST_FIRST_NAME" +
	    		" , bc.LAST_NAME CUST_LAST_NAME" +
	    		" , bc.TITLE CUST_TITLE" +
	    		" , ba.BILL_LANG CUST_BILL_LANG" +
	    		" , bs.SMS_LANG CUST_SMS_LANG" +
	    		" , bo.MSISDN" +
	    		" , bo.SRV_REQ_DATE" +
	    		" , bd.DELIVERY_DATE" +
	    		" , bcod.PROCESS_DATE" +
	    		" , bd.URGENT_IND" + 
	    		" , bo.ORDER_STATUS" +
	    		" , bcl.CODE_DESC ORDER_STATUS_DESC" +
	    		" , bo.CHECK_POINT" +
	    		" , bo.REASON_CD" +
	    		" , bcl2.CODE_DESC CHECK_POINT_DESC" +
	    		" , bo.ORDER_TYPE" +
	    		" , bo.SHOP_CD" +
	    		" FROM bomweb_ccsmob_order_delivery bcod" +
	    		" JOIN bomweb_order bo ON (bcod.ORDER_ID = bo.ORDER_ID)" +
	    		" JOIN bomweb_delivery bd ON (bcod.ORDER_ID = bd.ORDER_ID)" +
	    		" JOIN bomweb_customer bc ON (bcod.ORDER_ID = bc.ORDER_ID)" +
	    		" JOIN bomweb_acct ba ON (bcod.ORDER_ID = ba.ORDER_ID)" +
	    		" JOIN bomweb_sub bs ON (bcod.ORDER_ID = bs.ORDER_ID)" +
	    		" LEFT JOIN bomweb_code_lkup bcl ON (bo.ORDER_STATUS = bcl.CODE_ID AND bcl.CODE_TYPE = 'ORD_STATUS')" +
	    		" LEFT JOIN bomweb_code_lkup bcl2 ON (bo.CHECK_POINT = bcl2.CODE_ID AND bcl2.CODE_TYPE = 'ORD_CHECK_POINT')" +
	    		" WHERE bo.LOB = 'MOB'";
		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			StringBuilder searchSQL = new StringBuilder(sql);
			if (processDate != null) {
				searchSQL.append(" AND bcod.PROCESS_DATE = trunc(:processDate)");
				params.addValue("processDate", processDate);
			}
			if (statuses != null && !statuses.isEmpty()) {
				int index = 0;
				searchSQL.append(" AND (");
				for (Map<String, String> status : statuses) {
					String orderStatus = status.get("orderStatus");
					String checkPoint = status.get("checkPoint");
					String reasonCd = status.get("reasonCd");
					if (index > 0) {
						searchSQL.append(" OR ");
					}
					searchSQL.append(" (");
					searchSQL.append(" bo.ORDER_STATUS = :orderStatus" + index + " AND bo.CHECK_POINT = :checkPoint" + index);
					params.addValue("orderStatus" + index, orderStatus);
					params.addValue("checkPoint" + index, checkPoint);
					if (StringUtils.isNotBlank(reasonCd)) {
						searchSQL.append(" AND bo.reason_cd = :reasonCd" + index);
						params.addValue("reasonCd" + index, reasonCd);
					}
					searchSQL.append(" )");
					index++;
				}
				searchSQL.append(" )");
			}
			if (StringUtils.isNotEmpty(location)) {
				searchSQL.append(" AND NVL(bcod.location,'#') = DECODE(:location, 'ALL', NVL(bcod.location,'#'), :location)");
				params.addValue("location", location);
			}
		    searchSQL.append(" ORDER BY bcod.ORDER_ID, bcod.PROCESS_DATE");
		    logger.info("getMobCcsBulkPrintDTOALLBySearch() @ MobCcsBulkPrintDTO: "
			    + searchSQL);
		    itemList = simpleJdbcTemplate.query(searchSQL.toString(), this.getRowMapper(), params);
		} catch (EmptyResultDataAccessException erdae) {
		    logger.info("EmptyResultDataAccessException in getMobCcsBulkPrintDTOALLBySearch()");
	
		    itemList = null;
		} catch (Exception e) {
		    logger.info("Exception caught in getMobCcsBulkPrintDTOALLBySearch():", e);
	
		    throw new DAOException(e.getMessage(), e);
		}
		return itemList;
    }

	private ParameterizedRowMapper<MobCcsBulkPrintDTO> getRowMapper() {
		return new ParameterizedRowMapper<MobCcsBulkPrintDTO>() {
			public MobCcsBulkPrintDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				MobCcsBulkPrintDTO dto = new MobCcsBulkPrintDTO();
				dto.setOrderId(rs.getString("ORDER_ID"));
				dto.setOcId(rs.getString("OCID"));
				dto.setLob(rs.getString("LOB"));
				dto.setCustFirstName(rs.getString("CUST_FIRST_NAME"));
				dto.setCustLastName(rs.getString("CUST_LAST_NAME"));
				dto.setCustTitle(rs.getString("CUST_TITLE"));
				dto.setCustBillLang(rs.getString("CUST_BILL_LANG"));
				dto.setCustSmsLang(rs.getString("CUST_SMS_LANG"));
				dto.setMsisdn(rs.getString("MSISDN"));
				dto.setSrvReqDate(rs.getTimestamp("SRV_REQ_DATE"));
				dto.setDeliveryDate(rs.getTimestamp("DELIVERY_DATE"));
				dto.setProcessDate(rs.getTimestamp("PROCESS_DATE"));
				dto.setUrgentInd(rs.getString("URGENT_IND"));
				dto.setOrderStatus(rs.getString("ORDER_STATUS"));
				dto.setOrderStatusDesc(rs.getString("ORDER_STATUS_DESC"));
				dto.setCheckPoint(rs.getString("CHECK_POINT"));
				dto.setCheckPointDesc(rs.getString("CHECK_POINT_DESC"));
				dto.setReasonCd(rs.getString("REASON_CD"));
				dto.setOrderType(rs.getString("ORDER_TYPE"));
				dto.setShopCd(rs.getString("SHOP_CD"));
				dto.setLocation(rs.getString("LOCATION"));
				return dto;
			}
		};
	}
}
