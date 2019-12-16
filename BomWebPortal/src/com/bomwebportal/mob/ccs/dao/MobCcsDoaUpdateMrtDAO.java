package com.bomwebportal.mob.ccs.dao;

import java.sql.Types;
import java.util.Date;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;

import com.bomwebportal.dao.BaseDAO;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.util.BomWebPortalConstant;

public class MobCcsDoaUpdateMrtDAO extends BaseDAO {
    protected final Log logger = LogFactory.getLog(getClass());
    
    public String createOrderHist(String orderId) throws DAOException {
		try {
			SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
					.withSchemaName("OPS$CNM")
					.withCatalogName("PKG_SB_MOB_ORDER")
					.withProcedureName("ORDER_HIST_PROCESS");
			jdbcCall.setAccessCallParameterMetaData(false);
			jdbcCall.declareParameters(new SqlParameter("i_order_id", Types.VARCHAR)
										, new SqlOutParameter("o_errCode", Types.INTEGER)
										, new SqlOutParameter("o_errText", Types.VARCHAR));

			MapSqlParameterSource inMap = new MapSqlParameterSource();
			inMap.addValue("i_order_id", orderId);
			SqlParameterSource in = inMap;

			Map<String, Object> out = jdbcCall.execute(in);
			int errorCode = BomWebPortalConstant.ERRCODE_FAIL;

			if (((Integer) out.get("o_errCode")) != null) {
				errorCode = ((Integer) out.get("o_errCode")).intValue();
			}

			String errorText = (String) out.get("o_errText");

			logger.info("PKG_SB_MOB_ORDER.ORDER_MRT_PROCESS() output error_code = " + errorCode);
			logger.info("PKG_SB_MOB_ORDER.ORDER_MRT_PROCESS() output error_text = " + errorText);

			return errorText;
		} catch (Exception e) {
			logger.error("Exception caught in createOrderHist()", e);
			throw new DAOException(e.getMessage(), e);
		}
    }
	
	public int updateBomwebOrder(String orderId
			, String msisdn, Date srvReqDate
			, String username) throws DAOException {
		String sql = "UPDATE bomweb_order SET" +
				" MSISDN = :msisdn" +
				" , SRV_REQ_DATE = :srvReqDate" +
				" , LAST_UPDATE_BY = :lastUpdateBy" +
				" , LAST_UPDATE_DATE = sysdate" +
				" WHERE ORDER_ID = :orderId";
		try {
		    logger.info("updateBomwebOrder() @ MobCcsDoaUpdateMrtDao: " + sql);
		    
		    MapSqlParameterSource params = new MapSqlParameterSource();
		    params.addValue("msisdn", msisdn);
		    params.addValue("srvReqDate", srvReqDate, Types.DATE);
		    params.addValue("lastUpdateBy", username);
		    params.addValue("orderId", orderId);
		    
		    return simpleJdbcTemplate.update(sql, params);
		} catch (Exception e) {
		    logger.error("Exception caught in updateBomwebOrder()", e);
		    throw new DAOException(e.getMessage(), e);
		}
	}
	
	public int updateBomwebOrderWhenMnpWindowFrozen(String orderId
			, String msisdn
			, String username) throws DAOException {
		String sql = "UPDATE bomweb_order SET" +
				" MSISDN = :msisdn" +
				" , LAST_UPDATE_BY = :lastUpdateBy" +
				" , LAST_UPDATE_DATE = sysdate" +
				" WHERE ORDER_ID = :orderId";
		try {
		    logger.info("updateBomwebOrderWhenMnpWindowFrozen() @ MobCcsDoaUpdateMrtDao: " + sql);
		    
		    MapSqlParameterSource params = new MapSqlParameterSource();
		    params.addValue("msisdn", msisdn);
		    params.addValue("lastUpdateBy", username);
		    params.addValue("orderId", orderId);
		    
		    return simpleJdbcTemplate.update(sql, params);
		} catch (Exception e) {
		    logger.error("Exception caught in updateBomwebOrderWhenMnpWindowFrozen()", e);
		    throw new DAOException(e.getMessage(), e);
		}
	}
	
	public int updateBomwebOrderForNewNumber(String orderId
			, Date srvReqDate
			, String username) throws DAOException {
		String sql = "UPDATE bomweb_order SET" +
				" SRV_REQ_DATE = :srvReqDate" +
				" , LAST_UPDATE_BY = :lastUpdateBy" +
				" , LAST_UPDATE_DATE = sysdate" +
				" WHERE ORDER_ID = :orderId";
		try {
		    logger.info("updateBomwebOrderForNewNumber() @ MobCcsDoaUpdateMrtDao: " + sql);
		    
		    MapSqlParameterSource params = new MapSqlParameterSource();
		    params.addValue("srvReqDate", srvReqDate, Types.DATE);
		    params.addValue("lastUpdateBy", username);
		    params.addValue("orderId", orderId);
		    
		    return simpleJdbcTemplate.update(sql, params);
		} catch (Exception e) {
		    logger.error("Exception caught in updateBomwebOrderForNewNumber()", e);
		    throw new DAOException(e.getMessage(), e);
		}
	}
	
	public int updateBomwebMrt(String orderId, int seqId
			, String msisdn, String mnpTicketNum, Date cutOverDate, String cutOverTime
			, String custName, String docNo, String dno, String actDno
			, String username) throws DAOException {
		String sql = "UPDATE bomweb_mrt SET" +
				" MSISDN = :msisdn" +
				" , MNP_TICKET_NUM = :mnpTicketNum" +
				" , CUT_OVER_DATE = :cutOverDate" +
				" , CUT_OVER_TIME = :cutOverTime" +
				" , CUST_NAME = :custName" +
				" , DOC_NO = :docNo" +
				" , DNO = :dno" +
				" , ACT_DNO = :actDno" +
				" , LAST_UPD_BY = :lastUpdBy" +
				" , LAST_UPD_DATE = sysdate" +
				" WHERE ORDER_ID = :orderId" +
				" AND SEQ_ID = :seqId";
		try {
		    logger.info("updateBomwebMrt() @ MobCcsDoaUpdateMrtDao: " + sql);
		    
		    MapSqlParameterSource params = new MapSqlParameterSource();
		    params.addValue("msisdn", msisdn);
		    params.addValue("mnpTicketNum", mnpTicketNum);
		    params.addValue("cutOverDate", cutOverDate, Types.DATE);
		    params.addValue("cutOverTime", cutOverTime);
		    params.addValue("custName", custName);
		    params.addValue("docNo", docNo);
		    params.addValue("dno", dno);
		    params.addValue("actDno", actDno);
		    params.addValue("lastUpdBy", username);
		    params.addValue("orderId", orderId);
		    params.addValue("seqId", seqId);
		    
		    return simpleJdbcTemplate.update(sql, params);
		} catch (Exception e) {
		    logger.error("Exception caught in updateBomwebMrt()", e);
		    throw new DAOException(e.getMessage(), e);
		}
	}
	
	public int updateBomwebMrtWhenMnpWindowFrozen(String orderId, int seqId
			, String msisdn, String mnpTicketNum
			, String custName, String docNo, String dno, String actDno
			, String username) throws DAOException {
		String sql = "UPDATE bomweb_mrt SET" +
				" MSISDN = :msisdn" +
				" , MNP_TICKET_NUM = :mnpTicketNum" +
				" , CUST_NAME = :custName" +
				" , DOC_NO = :docNo" +
				" , DNO = :dno" +
				" , ACT_DNO = :actDno" +
				" , LAST_UPD_BY = :lastUpdBy" +
				" , LAST_UPD_DATE = sysdate" +
				" WHERE ORDER_ID = :orderId" +
				" AND SEQ_ID = :seqId";
		try {
		    logger.info("updateBomwebMrtWhenMnpWindowFrozen() @ MobCcsDoaUpdateMrtDao: " + sql);
		    
		    MapSqlParameterSource params = new MapSqlParameterSource();
		    params.addValue("msisdn", msisdn);
		    params.addValue("mnpTicketNum", mnpTicketNum);
		    params.addValue("custName", custName);
		    params.addValue("docNo", docNo);
		    params.addValue("dno", dno);
		    params.addValue("actDno", actDno);
		    params.addValue("lastUpdBy", username);
		    params.addValue("orderId", orderId);
		    params.addValue("seqId", seqId);
		    
		    return simpleJdbcTemplate.update(sql, params);
		} catch (Exception e) {
		    logger.error("Exception caught in updateBomwebMrtWhenMnpWindowFrozen()", e);
		    throw new DAOException(e.getMessage(), e);
		}
	}
	
	public int updateBomwebMrtForNewNumber(String orderId, int seqId
			, Date srvReqDate
			, String username) throws DAOException {
		String sql = "UPDATE bomweb_mrt SET" +
				" SRV_REQ_DATE = :srvReqDate" +
				" , LAST_UPD_BY = :lastUpdBy" +
				" , LAST_UPD_DATE = sysdate" +
				" WHERE ORDER_ID = :orderId" +
				" AND SEQ_ID = :seqId";
		try {
		    logger.info("updateBomwebMrtForNewNumber() @ MobCcsDoaUpdateMrtDao: " + sql);
		    
		    MapSqlParameterSource params = new MapSqlParameterSource();
		    params.addValue("srvReqDate", srvReqDate, Types.DATE);
		    params.addValue("lastUpdBy", username);
		    params.addValue("orderId", orderId);
		    params.addValue("seqId", seqId);
		    
		    return simpleJdbcTemplate.update(sql, params);
		} catch (Exception e) {
		    logger.error("Exception caught in updateBomwebMrtForNewNumber()", e);
		    throw new DAOException(e.getMessage(), e);
		}
	}
	
	public int updateBomwebMnp(String orderId, String mnpTicketNum
			, Date cutOverDate, String cutOverTime
			, String custName, String docNo, String dno, String actDno
			, String username) throws DAOException {
		String sql = "UPDATE bomweb_mnp SET" +
				" MNP_TICKET_NUM = :mnpTicketNum" +
				" , CUT_OVER_DATE = :cutOverDate" +
				" , CUT_OVER_TIME = :cutOverTime" +
				" , CUST_NAME = :custName" +
				" , DOC_NO = :docNo" +
				" , DNO = :dno" +
				" , ACT_DNO = :actDno" +
				" , LAST_UPD_BY = :lastUpdBy" +
				" , LAST_UPD_DATE = sysdate" +
				" WHERE ORDER_ID = :orderId";
		try {
		    logger.info("updateBomwebMnp() @ MobCcsDoaUpdateMrtDao: " + sql);
		    
		    MapSqlParameterSource params = new MapSqlParameterSource();
		    params.addValue("mnpTicketNum", mnpTicketNum);
		    params.addValue("cutOverDate", cutOverDate, Types.DATE);
		    params.addValue("cutOverTime", cutOverTime);
		    params.addValue("custName", custName);
		    params.addValue("docNo", docNo);
		    params.addValue("dno", dno);
		    params.addValue("actDno", actDno);
		    params.addValue("lastUpdBy", username);
		    params.addValue("orderId", orderId);
		    
		    return simpleJdbcTemplate.update(sql, params);
		} catch (Exception e) {
		    logger.error("Exception caught in updateBomwebMnp()", e);
		    throw new DAOException(e.getMessage(), e);
		}
	}
	
	public int updateBomwebMnpWhenMnpWindowFrozen(String orderId, String mnpTicketNum
			, String custName, String docNo, String dno , String actDno
			, String username) throws DAOException {
		String sql = "UPDATE bomweb_mnp SET" +
				" MNP_TICKET_NUM = :mnpTicketNum" +
				" , CUST_NAME = :custName" +
				" , DOC_NO = :docNo" +
				" , DNO = :dno" +
				" , ACT_DNO = :actDno" +
				" , LAST_UPD_BY = :lastUpdBy" +
				" , LAST_UPD_DATE = sysdate" +
				" WHERE ORDER_ID = :orderId";
		try {
		    logger.info("updateBomwebMnpWhenMnpWindowFrozen() @ MobCcsDoaUpdateMrtDao: " + sql);
		    
		    MapSqlParameterSource params = new MapSqlParameterSource();
		    params.addValue("mnpTicketNum", mnpTicketNum);
		    params.addValue("custName", custName);
		    params.addValue("docNo", docNo);
		    params.addValue("dno", dno);
		    params.addValue("actDno", actDno);
		    params.addValue("lastUpdBy", username);
		    params.addValue("orderId", orderId);
		    
		    return simpleJdbcTemplate.update(sql, params);
		} catch (Exception e) {
		    logger.error("Exception caught in updateBomwebMnpWhenMnpWindowFrozen()", e);
		    throw new DAOException(e.getMessage(), e);
		}
	}

}
