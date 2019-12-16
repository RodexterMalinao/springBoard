package com.bomwebportal.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.stereotype.Repository;

import com.bomwebportal.dto.OrderHsrmLogDTO;
import com.bomwebportal.exception.DAOException;

@Repository
public class OrderHsrmDAO extends BaseDAO {
	protected final Log logger = LogFactory.getLog(getClass());
	
	private static final String insertOrderHsrmLogSQL = 
			  "INSERT INTO BOMWEB_MOB_ORDER_HSRM_LOG ( \n"
			+ "     ORDER_ID  \n"
			+ "    ,REF_ID  \n"
			+ "    ,ITEM_CODE  \n"
			+ "    ,SALES_CD  \n"
			+ "    ,ACTION_IND  \n"
			+ "    ,STATUS  \n"
			+ "    ,ERR_CD  \n"
			+ "    ,ERR_MSG  \n"
			+ "    ,CREATE_BY  \n"
			+ "    ,CREATE_DATE  \n"
			+ "    ,LAST_UPD_BY  \n"
			+ "    ,LAST_UPD_DATE  \n"
			+ "    ,QUEUE_STATUS  \n"
			+ "    ,QUEUE_BRAND  \n"
			+ "    ,OFFER_BRAND  \n"
			+ ") VALUES ( \n"
			+ "     :orderId  \n"
			+ "    ,:refId  \n"
			+ "    ,:itemCode  \n"
			+ "    ,:salesCd  \n"
			+ "    ,:actionInd  \n"
			+ "    ,:status  \n"
			+ "    ,:errCd  \n"
			+ "    ,:errMsg  \n"
			+ "    ,:createBy  \n"
			+ "    ,sysdate  \n"
			+ "    ,:lastUpdBy  \n"
			+ "    ,sysdate  \n"
			+ "    ,:queueStatus  \n"
			+ "    ,:queueBrand  \n"
			+ "    ,:offerBrand  \n"
			+ " ) \n";

	public int insertOrderHsrmLog(OrderHsrmLogDTO dto) throws DAOException {
		logger.info("insertOrderHsrmLog is called");

		try {
			logger.info("Create Parms");
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("orderId", dto.getOrderId());
			params.addValue("refId", dto.getRefId());
			params.addValue("itemCode", dto.getItemCode());
			params.addValue("salesCd", dto.getSalesCd());
			params.addValue("actionInd", dto.getActionInd());
			params.addValue("status", dto.getStatus());
			params.addValue("errCd", dto.getErrCd());
			params.addValue("errMsg", dto.getErrMsg());
			params.addValue("createBy", dto.getSalesCd());
			params.addValue("lastUpdBy", dto.getSalesCd());
			params.addValue("queueStatus", dto.getQueueStatus());
			params.addValue("queueBrand", dto.getQueueBrand());
			params.addValue("offerBrand", dto.getBasketBrand());

			logger.info("insertOrderHsrmLog() @ OrderHsrmDAO: " + insertOrderHsrmLogSQL);
			return simpleJdbcTemplate.update(insertOrderHsrmLogSQL, params);
		} catch (Exception e) {
			logger.error("Exception caught in insertOrderHsrmLog()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	private static final String getOrderHsrmLogSQL = 
			  "SELECT \n"
			+ "     ORDER_ID  \n"
			+ "    ,REF_ID  \n"
			+ "    ,ITEM_CODE  \n"
			+ "    ,SALES_CD  \n"
			+ "    ,ACTION_IND  \n"
			+ "    ,STATUS  \n"
			+ "    ,ERR_CD  \n"
			+ "    ,ERR_MSG  \n"
			+ "    ,CREATE_BY  \n"
			+ "    ,CREATE_DATE  \n"
			+ "    ,LAST_UPD_BY  \n"
			+ "    ,LAST_UPD_DATE  \n"
			+ "    ,QUEUE_STATUS  \n"
			+ "    ,QUEUE_BRAND  \n"
			+ "    ,OFFER_BRAND  \n"
			+ "FROM BOMWEB_MOB_ORDER_HSRM_LOG  \n"
			+ "WHERE order_id = :orderId  \n"
			+ "AND ref_id = :refId  \n";

	public List<OrderHsrmLogDTO> getOrderHsrmLog(String orderId, String refId) throws DAOException {
		logger.info("insertOrderHsrmLog is called");

		ParameterizedRowMapper<OrderHsrmLogDTO> mapper = new ParameterizedRowMapper<OrderHsrmLogDTO>() {
			public OrderHsrmLogDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				OrderHsrmLogDTO log = new OrderHsrmLogDTO();
				log.setOrderId(rs.getString("ORDER_ID"));
				log.setRefId(rs.getString("REF_ID"));
				log.setItemCode(rs.getString("ITEM_CODE"));
				log.setSalesCd(rs.getString("SALES_CD"));
				log.setActionInd(rs.getString("ACTION_IND"));
				log.setStatus(rs.getString("STATUS"));
				log.setErrCd(rs.getString("ERR_CD"));
				log.setErrMsg(rs.getString("ERR_MSG"));
				log.setCreateBy(rs.getString("CREATE_BY"));
				log.setCreateDate(rs.getDate("CREATE_DATE"));
				log.setLastUpdBy(rs.getString("LAST_UPD_BY"));
				log.setLastUpdDate(rs.getDate("LAST_UPD_DATE"));
				log.setQueueStatus(rs.getString("QUEUE_STATUS"));
				log.setQueueBrand(rs.getString("QUEUE_BRAND"));
				log.setBasketBrand(rs.getString("OFFER_BRAND"));
				return log;
			}
		};
		
		try {
			logger.info("Create Parms");
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("orderId", orderId);
			params.addValue("refId", refId);

			logger.info("getOrderHsrmLog() @ OrderHsrmDAO: " + getOrderHsrmLogSQL);
			return simpleJdbcTemplate.query(getOrderHsrmLogSQL, mapper, params);
		} catch (Exception e) {
			logger.error("Exception caught in getOrderHsrmLog()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	
	
	
	
	
	private static final String getOrderConfirmedHsrmLogSQL = 
			"SELECT ORDER_ID , " +
					"  REF_ID , " +
					"  ITEM_CODE , " +
					"  SALES_CD , " +
					"  ACTION_IND , " +
					"  STATUS , " +
					"  ERR_CD , " +
					"  ERR_MSG , " +
					"  CREATE_BY , " +
					"  CREATE_DATE , " +
					"  LAST_UPD_BY , " +
					"  LAST_UPD_DATE ," +
					"  QUEUE_STATUS , " +
					"  QUEUE_BRAND ," +
					"  OFFER_BRAND " +
					"FROM BOMWEB_MOB_ORDER_HSRM_LOG " +
					"WHERE order_id = :orderId " +
					"AND action_ind = 'Order Confirmed' " +
					"AND status     = 'Succeed' " +
					"ORDER BY create_date DESC ";
	
	public OrderHsrmLogDTO getOrderConfirmedHsrmLog(String orderId) throws DAOException {
		logger.info("getOrderConfirmedHsrmLog is called");

		List<OrderHsrmLogDTO> resultList = null;
		
		ParameterizedRowMapper<OrderHsrmLogDTO> mapper = new ParameterizedRowMapper<OrderHsrmLogDTO>() {
			public OrderHsrmLogDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				OrderHsrmLogDTO log = new OrderHsrmLogDTO();
				log.setOrderId(rs.getString("ORDER_ID"));
				log.setRefId(rs.getString("REF_ID"));
				log.setItemCode(rs.getString("ITEM_CODE"));
				log.setSalesCd(rs.getString("SALES_CD"));
				log.setActionInd(rs.getString("ACTION_IND"));
				log.setStatus(rs.getString("STATUS"));
				log.setErrCd(rs.getString("ERR_CD"));
				log.setErrMsg(rs.getString("ERR_MSG"));
				log.setCreateBy(rs.getString("CREATE_BY"));
				log.setCreateDate(rs.getDate("CREATE_DATE"));
				log.setLastUpdBy(rs.getString("LAST_UPD_BY"));
				log.setLastUpdDate(rs.getDate("LAST_UPD_DATE"));
				log.setQueueStatus(rs.getString("QUEUE_STATUS"));
				log.setQueueBrand(rs.getString("QUEUE_BRAND"));
				log.setBasketBrand(rs.getString("OFFER_BRAND"));
				return log;
			}
		};
		
		try {
			logger.info("Create Parms");
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("orderId", orderId);
	
			logger.info("getOrderConfirmedHsrmLog() @ OrderHsrmDAO: " + getOrderConfirmedHsrmLogSQL);
			
			resultList = simpleJdbcTemplate.query(getOrderConfirmedHsrmLogSQL, mapper, params);
			if (CollectionUtils.isNotEmpty(resultList)){
				return resultList.get(0);
			}else{
				return null;
			}
		} catch (Exception e) {
			logger.error("Exception caught in getOrderConfirmedHsrmLog()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	
	private static final String getOrderCompletedHsrmLogSQL = 
			"SELECT ORDER_ID , " +
					"  REF_ID , " +
					"  ITEM_CODE , " +
					"  SALES_CD , " +
					"  ACTION_IND , " +
					"  STATUS , " +
					"  ERR_CD , " +
					"  ERR_MSG , " +
					"  CREATE_BY , " +
					"  CREATE_DATE , " +
					"  LAST_UPD_BY , " +
					"  LAST_UPD_DATE , " +
					"  QUEUE_STATUS , " +
					"  QUEUE_BRAND ," +
					"  OFFER_BRAND " +
					"FROM BOMWEB_MOB_ORDER_HSRM_LOG " +
					"WHERE order_id = :orderId " +
					"AND action_ind = 'Completed' " +
					"AND status     = 'Succeed' " +
					"ORDER BY create_date DESC ";
	
	public OrderHsrmLogDTO getOrderCompletedHsrmLog(String orderId) throws DAOException {
		logger.info("getOrderCompletedHsrmLog is called");

		List<OrderHsrmLogDTO> resultList = null;
		
		ParameterizedRowMapper<OrderHsrmLogDTO> mapper = new ParameterizedRowMapper<OrderHsrmLogDTO>() {
			public OrderHsrmLogDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				OrderHsrmLogDTO log = new OrderHsrmLogDTO();
				log.setOrderId(rs.getString("ORDER_ID"));
				log.setRefId(rs.getString("REF_ID"));
				log.setItemCode(rs.getString("ITEM_CODE"));
				log.setSalesCd(rs.getString("SALES_CD"));
				log.setActionInd(rs.getString("ACTION_IND"));
				log.setStatus(rs.getString("STATUS"));
				log.setErrCd(rs.getString("ERR_CD"));
				log.setErrMsg(rs.getString("ERR_MSG"));
				log.setCreateBy(rs.getString("CREATE_BY"));
				log.setCreateDate(rs.getDate("CREATE_DATE"));
				log.setLastUpdBy(rs.getString("LAST_UPD_BY"));
				log.setLastUpdDate(rs.getDate("LAST_UPD_DATE"));
				log.setQueueStatus(rs.getString("QUEUE_STATUS"));
				log.setQueueBrand(rs.getString("QUEUE_BRAND"));
				log.setBasketBrand(rs.getString("OFFER_BRAND"));
				return log;
			}
		};
		
		try {
			logger.info("Create Parms");
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("orderId", orderId);
	
			logger.info("getOrderCompletedHsrmLog() @ OrderHsrmDAO: " + getOrderCompletedHsrmLogSQL);
			
			resultList = simpleJdbcTemplate.query(getOrderCompletedHsrmLogSQL, mapper, params);
			if (CollectionUtils.isNotEmpty(resultList)){
				return resultList.get(0);
			}else{
				return null;
			}
		} catch (Exception e) {
			logger.error("Exception caught in getOrderCompletedHsrmLog()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	
	private static final String isOrderCompletedHsrmLogExistSQL = 
			"SELECT COUNT(*) " +
					"FROM bomweb_mob_order_hsrm_log " +
					"WHERE order_id = :orderId " +
					"AND action_ind ='Completed' " +
					"AND status    IN ('Succeed','Skipped')";
	
	public boolean isOrderCompletedHsrmLogExist(String orderId) throws DAOException {
		logger.info("isOrderCompletedHsrmLogExist is called");

		
		ParameterizedRowMapper<OrderHsrmLogDTO> mapper = new ParameterizedRowMapper<OrderHsrmLogDTO>() {
			public OrderHsrmLogDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				OrderHsrmLogDTO log = new OrderHsrmLogDTO();
				log.setOrderId(rs.getString("ORDER_ID"));
				log.setRefId(rs.getString("REF_ID"));
				log.setItemCode(rs.getString("ITEM_CODE"));
				log.setSalesCd(rs.getString("SALES_CD"));
				log.setActionInd(rs.getString("ACTION_IND"));
				log.setStatus(rs.getString("STATUS"));
				log.setErrCd(rs.getString("ERR_CD"));
				log.setErrMsg(rs.getString("ERR_MSG"));
				log.setCreateBy(rs.getString("CREATE_BY"));
				log.setCreateDate(rs.getDate("CREATE_DATE"));
				log.setLastUpdBy(rs.getString("LAST_UPD_BY"));
				log.setLastUpdDate(rs.getDate("LAST_UPD_DATE"));
				return log;
			}
		};
		
		try {
			logger.info("Create Parms");
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("orderId", orderId);
	
			logger.info("isOrderCompletedHsrmLogExist() @ OrderHsrmDAO: " + isOrderCompletedHsrmLogExistSQL);
			Integer count = (Integer) this.simpleJdbcTemplate.queryForObject(
					isOrderCompletedHsrmLogExistSQL,  Integer.class, params);
			if (logger.isDebugEnabled()) {
				logger.debug("SQL: " + isOrderCompletedHsrmLogExistSQL);
				logger.debug("count: " + count);
			}
			return count > 0;
			
		} catch (Exception e) {
			logger.error("Exception caught in isOrderCompletedHsrmLogExist()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
}
