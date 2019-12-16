package com.bomwebportal.mob.ccs.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.bomwebportal.dao.BaseDAO;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.mob.ccs.dto.MobCcsOrderRescueDTO;

public class MobCcsOrderRescueDAO extends BaseDAO {
	protected final Log logger = LogFactory.getLog(getClass());

	public String getNextIncidentNo() throws DAOException {
		logger.info("getMobCcsOrderRescueDTO() is called");
		final String getNextIncidentNoSQL = "SELECT to_char(BOMWEB_INCIDENT_NO.nextval, 'FM0000000000') NEXT_VAL FROM dual";
		List<String> itemList = null;
		try {
			if (logger.isInfoEnabled()) {
				logger.info("getNextIncidentNo() @ MobCcsOrderRescueDAO: " + getNextIncidentNoSQL);
			}
			itemList = this.simpleJdbcTemplate.query(getNextIncidentNoSQL, new ParameterizedRowMapper<String>() {
				public String mapRow(ResultSet rs, int rowNum)
						throws SQLException {
					return rs.getString("NEXT_VAL");
				}
			});
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException in getNextIncidentNo()");
			itemList = null;
		} catch (Exception e) {
			logger.info("Exception caught in getNextIncidentNo():", e);
			throw new DAOException(e.getMessage(), e);
		}
		return itemList == null || itemList.isEmpty() ? null : itemList.get(0);
	}

	public MobCcsOrderRescueDTO getMobCcsOrderRescueDTO(String rowId) throws DAOException {
		logger.info("getMobCcsOrderRescueDTO() is called");
		final String getMobCcsOrderRescueDTOSQL = "SELECT" +
				" bor.INCIDENT_NO" +
				" , bo.ORDER_ID" +
				" , bor.HANDLE_BY" +
				" , bor.HANDLE_TIME" +
				" , bor.SERIAL_CANCEL" +
				" , bor.VISIT_MT1" +
				" , bor.REMARK" +
				" , bor.ACTION" +
				" , bor.COURIER_STAFF_ID" +
				" , bor.AMENDMENT" +
				" , bor.ORDER_SAVED" +
				" , bor.CREATE_DATE" +
				" , bor.rowid ROW_ID" +
				" , bor.DOA" +
				" , bor.DEL_CONTRACT_ONLY" +
				" , bo.MSISDN" +
				" , bo.ORDER_STATUS" +
				" , bo.CHECK_POINT" +
				" , bo.REASON_CD" +
				" , bo.SALES_NAME" +
				" , bd.DELIVERY_DATE" +
				" , bd.DELIVERY_TIME_SLOT" +
				" , dt.TIMEFROM" +
				" , dt.TIMETO" +
				" , bo.SRV_REQ_DATE" +
				" , bo.shop_cd" +
				" , bcl.CODE_DESC DELIVERY_CENTRE_DESC" +
				" , bcl2.CODE_DESC ORDER_STATUS_DESC" +
				" , bcl3.CODE_DESC CHECK_POINT_DESC" +
				" , bcl4.CODE_DESC LOCATION_DESC" +
				" FROM BOMWEB_ORDER bo" +
				" LEFT JOIN BOMWEB_ORDER_RESCUE bor ON (bo.ORDER_ID = bor.ORDER_ID)" +
				" LEFT JOIN BOMWEB_DELIVERY bd ON (bo.ORDER_ID = bd.ORDER_ID)" +
				" LEFT JOIN BOMWEB_CODE_LKUP bcl ON (bd.DELIVERY_CENTRE = bcl.CODE_ID AND bcl.CODE_TYPE = 'PICK_CENTRE')" +
				" LEFT JOIN BOMWEB_CODE_LKUP bcl2 ON (bo.ORDER_STATUS = bcl2.CODE_ID AND bcl2.CODE_TYPE = 'ORD_STATUS')" +
				" LEFT JOIN BOMWEB_CODE_LKUP bcl3 ON (bo.CHECK_POINT = bcl3.CODE_ID AND bcl3.CODE_TYPE = 'ORD_CHECK_POINT')" +
				" LEFT JOIN BOMWEB_CODE_LKUP bcl4 ON (bd.LOCATION = bcl4.CODE_ID AND bcl4.CODE_TYPE = 'STOCK_LOC')" +
				" LEFT JOIN W_DELIVERY_TIMESLOT dt ON (bd.DELIVERY_TIME_SLOT = dt.TIMESLOT)" +
				" WHERE bo.LOB = 'MOB'" +
				" AND bor.rowid = :rowId";
		List<MobCcsOrderRescueDTO> itemList = null;
		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("rowId", rowId);
			if (logger.isInfoEnabled()) {
				logger.info("getMobCcsOrderRescueDTO() @ MobCcsOrderRescueDAO: " + getMobCcsOrderRescueDTOSQL);
			}
			itemList = this.simpleJdbcTemplate.query(getMobCcsOrderRescueDTOSQL, this.getRowMapper(), params);
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException in getMobCcsOrderRescueDTO()");
			itemList = null;
		} catch (Exception e) {
			logger.info("Exception caught in getMobCcsOrderRescueDTO():", e);
			throw new DAOException(e.getMessage(), e);
		}
		return itemList == null || itemList.isEmpty() ? null : itemList.get(0);
	}

	public MobCcsOrderRescueDTO getMobCcsOrderRescueDTOByIncidentNo(String incidentNo) throws DAOException {
		logger.info("getMobCcsOrderRescueDTOByIncidentNo() is called");
		final String getMobCcsOrderRescueDTOByIncidentNoSQL = "SELECT" +
				" bor.INCIDENT_NO" +
				" , bo.ORDER_ID" +
				" , bor.HANDLE_BY" +
				" , bor.HANDLE_TIME" +
				" , bor.SERIAL_CANCEL" +
				" , bor.VISIT_MT1" +
				" , bor.REMARK" +
				" , bor.ACTION" +
				" , bor.COURIER_STAFF_ID" +
				" , bor.AMENDMENT" +
				" , bor.ORDER_SAVED" +
				" , bor.CREATE_DATE" +
				" , bor.rowid ROW_ID" +
				" , bor.DOA" +
				" , bor.DEL_CONTRACT_ONLY" +
				" , bo.MSISDN" +
				" , bo.ORDER_STATUS" +
				" , bo.CHECK_POINT" +
				" , bo.REASON_CD" +
				" , bo.SALES_NAME" +
				" , bd.DELIVERY_DATE" +
				" , bd.DELIVERY_TIME_SLOT" +
				" , dt.TIMEFROM" +
				" , dt.TIMETO" +
				" , bo.SRV_REQ_DATE" +
				" , bo.shop_cd" +
				" , bcl.CODE_DESC DELIVERY_CENTRE_DESC" +
				" , bcl2.CODE_DESC ORDER_STATUS_DESC" +
				" , bcl3.CODE_DESC CHECK_POINT_DESC" +
				" , bcl4.CODE_DESC LOCATION_DESC" +
				" FROM BOMWEB_ORDER bo" +
				" LEFT JOIN BOMWEB_ORDER_RESCUE bor ON (bo.ORDER_ID = bor.ORDER_ID)" +
				" LEFT JOIN BOMWEB_DELIVERY bd ON (bo.ORDER_ID = bd.ORDER_ID)" +
				" LEFT JOIN BOMWEB_CODE_LKUP bcl ON (bd.DELIVERY_CENTRE = bcl.CODE_ID AND bcl.CODE_TYPE = 'PICK_CENTRE')" +
				" LEFT JOIN BOMWEB_CODE_LKUP bcl2 ON (bo.ORDER_STATUS = bcl2.CODE_ID AND bcl2.CODE_TYPE = 'ORD_STATUS')" +
				" LEFT JOIN BOMWEB_CODE_LKUP bcl3 ON (bo.CHECK_POINT = bcl3.CODE_ID AND bcl3.CODE_TYPE = 'ORD_CHECK_POINT')" +
				" LEFT JOIN BOMWEB_CODE_LKUP bcl4 ON (bd.LOCATION = bcl4.CODE_ID AND bcl4.CODE_TYPE = 'STOCK_LOC')" +
				" LEFT JOIN W_DELIVERY_TIMESLOT dt ON (bd.DELIVERY_TIME_SLOT = dt.TIMESLOT)" +
				" WHERE bo.LOB = 'MOB'" +
				" AND bor.INCIDENT_NO = :incidentNo";
		List<MobCcsOrderRescueDTO> itemList = null;
		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("incidentNo", incidentNo);
			if (logger.isInfoEnabled()) {
				logger.info("getMobCcsOrderRescueDTOByIncidentNo() @ MobCcsOrderRescueDAO: " + getMobCcsOrderRescueDTOByIncidentNoSQL);
			}
			itemList = this.simpleJdbcTemplate.query(getMobCcsOrderRescueDTOByIncidentNoSQL, this.getRowMapper(), params);
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException in getMobCcsOrderRescueDTOByIncidentNo()");
			itemList = null;
		} catch (Exception e) {
			logger.info("Exception caught in getMobCcsOrderRescueDTOByIncidentNo():", e);
			throw new DAOException(e.getMessage(), e);
		}
		return itemList == null || itemList.isEmpty() ? null : itemList.get(0);
	}

	public List<MobCcsOrderRescueDTO> getMobCcsOrderRescueDTOByOrderId(String orderId) throws DAOException {
		logger.info("getMobCcsOrderRescueDTOByOrderId() is called");
		final String getMobCcsOrderRescueDTOByOrderIdSQL = "SELECT" +
				" bor.INCIDENT_NO" +
				" , bo.ORDER_ID" +
				" , bor.HANDLE_BY" +
				" , bor.HANDLE_TIME" +
				" , bor.SERIAL_CANCEL" +
				" , bor.VISIT_MT1" +
				" , bor.REMARK" +
				" , bor.ACTION" +
				" , bor.COURIER_STAFF_ID" +
				" , bor.AMENDMENT" +
				" , bor.ORDER_SAVED" +
				" , bor.CREATE_DATE" +
				" , bor.rowid ROW_ID" +
				" , bor.DOA" +
				" , bor.DEL_CONTRACT_ONLY" +
				" , bo.MSISDN" +
				" , bo.ORDER_STATUS" +
				" , bo.CHECK_POINT" +
				" , bo.REASON_CD" +
				" , bo.SALES_NAME" +
				" , bd.DELIVERY_DATE" +
				" , bd.DELIVERY_TIME_SLOT" +
				" , dt.TIMEFROM" +
				" , dt.TIMETO" +
				" , bo.SRV_REQ_DATE" +
				" , bo.shop_cd" +
				" , bcl.CODE_DESC DELIVERY_CENTRE_DESC" +
				" , bcl2.CODE_DESC ORDER_STATUS_DESC" +
				" , bcl3.CODE_DESC CHECK_POINT_DESC" +
				" , bcl4.CODE_DESC LOCATION_DESC" +
				" FROM BOMWEB_ORDER bo " +
				" LEFT JOIN BOMWEB_ORDER_RESCUE bor ON (bo.ORDER_ID = bor.ORDER_ID)" +
				" LEFT JOIN BOMWEB_DELIVERY bd ON (bo.ORDER_ID = bd.ORDER_ID)" +
				" LEFT JOIN BOMWEB_CODE_LKUP bcl ON (bd.DELIVERY_CENTRE = bcl.CODE_ID AND bcl.CODE_TYPE = 'PICK_CENTRE')" +
				" LEFT JOIN BOMWEB_CODE_LKUP bcl2 ON (bo.ORDER_STATUS = bcl2.CODE_ID AND bcl2.CODE_TYPE = 'ORD_STATUS')" +
				" LEFT JOIN BOMWEB_CODE_LKUP bcl3 ON (bo.CHECK_POINT = bcl3.CODE_ID AND bcl3.CODE_TYPE = 'ORD_CHECK_POINT')" +
				" LEFT JOIN BOMWEB_CODE_LKUP bcl4 ON (bd.LOCATION = bcl4.CODE_ID AND bcl4.CODE_TYPE = 'STOCK_LOC')" +
				" LEFT JOIN W_DELIVERY_TIMESLOT dt ON (bd.DELIVERY_TIME_SLOT = dt.TIMESLOT)" +
				" WHERE bo.LOB = 'MOB'" +
				" AND bo.ORDER_ID = :orderId";
		List<MobCcsOrderRescueDTO> itemList = null;
		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("orderId", orderId);
			if (logger.isInfoEnabled()) {
				logger.info("getMobCcsOrderRescueDTOByOrderId() @ MobCcsOrderRescueDAO: " + getMobCcsOrderRescueDTOByOrderIdSQL);
			}
			itemList = this.simpleJdbcTemplate.query(getMobCcsOrderRescueDTOByOrderIdSQL, this.getRowMapper(), params);
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException in getMobCcsOrderRescueDTOByOrderId()");
			itemList = null;
		} catch (Exception e) {
			logger.info("Exception caught in getMobCcsOrderRescueDTOByOrderId():", e);
			throw new DAOException(e.getMessage(), e);
		}
		return itemList;
	}

	public List<MobCcsOrderRescueDTO> getMobCcsOrderRescueDTOBySearch(String orderId, String incidentNo, String msisdn) throws DAOException {
		logger.info("getMobCcsOrderRescueDTOBySearch() is called");
		final String getMobCcsOrderRescueDTOBySearchSQL = "SELECT" +
				" bor.INCIDENT_NO" +
				" , bo.ORDER_ID" +
				" , bor.HANDLE_BY" +
				" , bor.HANDLE_TIME" +
				" , bor.SERIAL_CANCEL" +
				" , bor.VISIT_MT1" +
				" , bor.REMARK" +
				" , bor.ACTION" +
				" , bor.COURIER_STAFF_ID" +
				" , bor.AMENDMENT" +
				" , bor.ORDER_SAVED" +
				" , bor.CREATE_DATE" +
				" , bor.rowid ROW_ID" +
				" , bor.DOA" +
				" , bor.DEL_CONTRACT_ONLY" +
				" , bo.MSISDN" +
				" , bo.ORDER_STATUS" +
				" , bo.CHECK_POINT" +
				" , bo.REASON_CD" +
				" , bo.SALES_NAME" +
				" , bd.DELIVERY_DATE" +
				" , bd.DELIVERY_TIME_SLOT" +
				" , dt.TIMEFROM" +
				" , dt.TIMETO" +
				" , bo.SRV_REQ_DATE" +
				" , bo.shop_cd" +
				" , bcl.CODE_DESC DELIVERY_CENTRE_DESC" +
				" , bcl2.CODE_DESC ORDER_STATUS_DESC" +
				" , bcl3.CODE_DESC CHECK_POINT_DESC" +
				" , bcl4.CODE_DESC LOCATION_DESC" +
				" FROM BOMWEB_ORDER bo " +
				" LEFT JOIN BOMWEB_ORDER_RESCUE bor ON (bo.ORDER_ID = bor.ORDER_ID)" +
				" LEFT JOIN BOMWEB_DELIVERY bd ON (bo.ORDER_ID = bd.ORDER_ID)" +
				" LEFT JOIN BOMWEB_CODE_LKUP bcl ON (bd.DELIVERY_CENTRE = bcl.CODE_ID AND bcl.CODE_TYPE = 'PICK_CENTRE')" +
				" LEFT JOIN BOMWEB_CODE_LKUP bcl2 ON (bo.ORDER_STATUS = bcl2.CODE_ID AND bcl2.CODE_TYPE = 'ORD_STATUS')" +
				" LEFT JOIN BOMWEB_CODE_LKUP bcl3 ON (bo.CHECK_POINT = bcl3.CODE_ID AND bcl3.CODE_TYPE = 'ORD_CHECK_POINT')" +
				" LEFT JOIN BOMWEB_CODE_LKUP bcl4 ON (bd.LOCATION = bcl4.CODE_ID AND bcl4.CODE_TYPE = 'STOCK_LOC')" +
				" LEFT JOIN W_DELIVERY_TIMESLOT dt ON (bd.DELIVERY_TIME_SLOT = dt.TIMESLOT)" +
				" WHERE bo.LOB = 'MOB'";
		List<MobCcsOrderRescueDTO> itemList = null;
		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			StringBuilder searchSQL = new StringBuilder(getMobCcsOrderRescueDTOBySearchSQL);
			if (StringUtils.isNotBlank(orderId)) {
				searchSQL.append(" AND bo.ORDER_ID = :orderId");
				params.addValue("orderId", orderId);
			}
			if (StringUtils.isNotBlank(incidentNo)) {
				searchSQL.append(" AND bor.INCIDENT_NO = :incidentNo");
				params.addValue("incidentNo", incidentNo);
			}
			if (StringUtils.isNotBlank(msisdn)) {
				searchSQL.append(" AND bo.MSISDN = :msisdn");
				params.addValue("msisdn", msisdn);
			}
			searchSQL.append(" ORDER BY bo.ORDER_ID, bor.INCIDENT_NO, bo.MSISDN");
			if (logger.isInfoEnabled()) {
				logger.info("getMobCcsOrderRescueDTOBySearch() @ MobCcsOrderRescueDAO: " + searchSQL);
			}
			itemList = this.simpleJdbcTemplate.query(searchSQL.toString(), this.getRowMapper(), params);
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException in getMobCcsOrderRescueDTOBySearch()");
			itemList = null;
		} catch (Exception e) {
			logger.info("Exception caught in getMobCcsOrderRescueDTOBySearch():", e);
			throw new DAOException(e.getMessage(), e);
		}
		return itemList;
	}
	
	private ParameterizedRowMapper<MobCcsOrderRescueDTO> getRowMapper() {
		return new ParameterizedRowMapper<MobCcsOrderRescueDTO>() {

			public MobCcsOrderRescueDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				MobCcsOrderRescueDTO dto = new MobCcsOrderRescueDTO();
				dto.setIncidentNo(rs.getString("INCIDENT_NO"));
				dto.setOrderId(rs.getString("ORDER_ID"));
				dto.setHandleBy(rs.getString("HANDLE_BY"));
				dto.setHandleTime(rs.getTimestamp("HANDLE_TIME"));
				dto.setSerialCancel(rs.getString("SERIAL_CANCEL"));
				dto.setVisitMt1(rs.getString("VISIT_MT1"));
				dto.setRemark(rs.getString("REMARK"));
				dto.setAction(rs.getString("ACTION"));
				dto.setCourierStaffId(rs.getString("COURIER_STAFF_ID"));
				dto.setAmendment(rs.getString("AMENDMENT"));
				dto.setOrderSaved(rs.getString("ORDER_SAVED"));
				dto.setCreateDate(rs.getTimestamp("CREATE_DATE"));
				dto.setRowId(rs.getString("ROW_ID"));
				dto.setDoa(rs.getString("DOA"));
				dto.setDelContractOnly(rs.getString("DEL_CONTRACT_ONLY"));
				
				dto.setMsisdn(rs.getString("MSISDN"));
				dto.setOrderStatus(rs.getString("ORDER_STATUS"));
				dto.setCheckPoint(rs.getString("CHECK_POINT"));
				dto.setReasonCd(rs.getString("REASON_CD"));
				dto.setSalesName(rs.getString("SALES_NAME"));
				dto.setDeliveryDate(rs.getTimestamp("DELIVERY_DATE"));
				dto.setDeliveryTimeSlot(rs.getString("DELIVERY_TIME_SLOT"));
				dto.setDeliveryTimeFrom(rs.getString("TIMEFROM"));
				dto.setDeliveryTimeTo(rs.getString("TIMETO"));
				dto.setSrvReqDate(rs.getTimestamp("SRV_REQ_DATE"));
				dto.setLocationDesc(rs.getString("LOCATION_DESC"));
				dto.setOrderStatusDesc(rs.getString("ORDER_STATUS_DESC"));
				dto.setCheckPointDesc(rs.getString("CHECK_POINT_DESC"));
				dto.setDeliveryCentreDesc(rs.getString("DELIVERY_CENTRE_DESC"));
				dto.setShopCode(rs.getString("SHOP_CD"));
				return dto;
			}
			
		};
	}

	public void insertMobCcsOrderRescueDTO(MobCcsOrderRescueDTO dto) throws DAOException {
		logger.info("insertMobCcsOrderRescueDTO() is called");
		final String insertMobCcsOrderRescueDTOSQL = "INSERT INTO BOMWEB_ORDER_RESCUE" +
				" (" +
				" INCIDENT_NO" +
				" , ORDER_ID" +
				" , HANDLE_BY" +
				" , HANDLE_TIME" +
				" , SERIAL_CANCEL" +
				" , VISIT_MT1" +
				" , REMARK" +
				" , ACTION" +
				" , COURIER_STAFF_ID" +
				" , AMENDMENT" +
				" , ORDER_SAVED" +
				" , CREATE_DATE" +
				" , DOA" +
				" , DEL_CONTRACT_ONLY" +
				" )" +
				" VALUES" +
				" (" +
				" :incidentNo" +
				" , :orderId" +
				" , :handleBy" +
				" , :handleTime" +
				" , :serialCancel" +
				" , :visitMt1" +
				" , :remark" +
				" , :action" +
				" , :courierStaffId" +
				" , :amendment" +
				" , :orderSaved" +
				" , :createDate" +
				" , :doa" +
				" , :delContractOnly" +
				" )";
		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("incidentNo", dto.getIncidentNo());
			params.addValue("orderId", dto.getOrderId());
			params.addValue("handleBy", dto.getHandleBy());
			params.addValue("handleTime", dto.getHandleTime());
			params.addValue("serialCancel", dto.getSerialCancel());
			params.addValue("visitMt1", dto.getVisitMt1());
			params.addValue("remark", dto.getRemark());
			params.addValue("action", dto.getAction());
			params.addValue("courierStaffId", dto.getCourierStaffId());
			params.addValue("amendment", dto.getAmendment());
			params.addValue("orderSaved", dto.getOrderSaved());
			params.addValue("createDate", dto.getCreateDate());
			params.addValue("doa", dto.getDoa());
			params.addValue("delContractOnly", dto.getDelContractOnly());
			if (logger.isInfoEnabled()) {
				logger.info("insertMobCcsOrderRescueDTO() @ MobCcsOrderRescueDAO: " + insertMobCcsOrderRescueDTOSQL);
			}
			this.simpleJdbcTemplate.update(insertMobCcsOrderRescueDTOSQL, params);
		} catch (Exception e) {
			logger.info("Exception caught in insertMobCcsOrderRescueDTO():", e);
			throw new DAOException(e.getMessage(), e);
		}
	}

	public void updateMobCcsOrderRescueDTO(MobCcsOrderRescueDTO dto) throws DAOException {
		logger.info("updateMobCcsOrderRescueDTO() is called");
		final String updateMobCcsOrderRescueDTOSQL = "UPDATE BOMWEB_ORDER_RESCUE SET" +
				" HANDLE_BY = :handleBy" +
				" , HANDLE_TIME = :handleTime" +
				" , SERIAL_CANCEL = :serialCancel" +
				" , VISIT_MT1 = :visitMt1" +
				" , REMARK = :remark" +
				" , ACTION = :action" +
				" , COURIER_STAFF_ID = :courierStaffId" +
				" , AMENDMENT = :amendment" +
				" , ORDER_SAVED = :orderSaved" +
				" , DOA = :doa" +
				" , DEL_CONTRACT_ONLY = :delContractOnly" +
				" WHERE rowid = :rowId";
		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("handleBy", dto.getHandleBy());
			params.addValue("handleTime", dto.getHandleTime());
			params.addValue("serialCancel", dto.getSerialCancel());
			params.addValue("visitMt1", dto.getVisitMt1());
			params.addValue("remark", dto.getRemark());
			params.addValue("action", dto.getAction());
			params.addValue("courierStaffId", dto.getCourierStaffId());
			params.addValue("amendment", dto.getAmendment());
			params.addValue("orderSaved", dto.getOrderSaved());
			params.addValue("doa", dto.getDoa());
			params.addValue("delContractOnly", dto.getDelContractOnly());
			params.addValue("rowId", dto.getRowId());
			if (logger.isInfoEnabled()) {
				logger.info("updateMobCcsOrderRescueDTO() @ MobCcsOrderRescueDAO: " + updateMobCcsOrderRescueDTOSQL);
			}
			this.simpleJdbcTemplate.update(updateMobCcsOrderRescueDTOSQL, params);
		} catch (Exception e) {
			logger.info("Exception caught in updateMobCcsOrderRescueDTO():", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
}
