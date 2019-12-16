package com.bomwebportal.sbo.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.bomwebportal.dao.BaseDAO;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.sbo.dto.SboMobOrderDTO;

public class SboOrderDAO extends BaseDAO {

	ParameterizedRowMapper<SboMobOrderDTO> sboMobOrderMapper = new ParameterizedRowMapper<SboMobOrderDTO>() {

		public SboMobOrderDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
			SboMobOrderDTO o = new SboMobOrderDTO();
			o.setLob(rs.getString("LOB"));
			o.setOrderId(rs.getString("ORDER_ID"));
			o.setOcid(rs.getString("OCID"));
			o.setIdDocType(rs.getString("ID_DOC_TYPE"));
			o.setIdDocNum(rs.getString("ID_DOC_NUM"));

			o.setCustName(rs.getString("CUST_FULL_NAME"));
			o.setMsisdn(rs.getString("MSISDN"));
			o.setAppDate(rs.getDate("APP_DATE"));
			o.setOrderStatus(rs.getString("ORDER_STATUS"));
			o.setEsigEmailAddr(rs.getString("ESIG_EMAIL_ADDR"));
			o.setDeliveryDate(rs.getDate("DELIVERY_DATE"));
			o.setDeliveryTimeSlot(rs.getString("DELIVERY_TIME_SLOT"));
			o.setSrvReqDate(rs.getDate("SRV_REQ_DATE"));
			o.setBomOrderStatus(rs.getString("BOM_ORDER_STATUS"));
			o.setActSrvReqDate(rs.getDate("ACT_SRV_REQ_DATE"));
			o.setAllowCancelInd(rs.getString("ALLOW_CANCEL_IND"));
			
			o.setBusTxnType(rs.getString("ORDER_TYPE"));
			o.setCheckPoint(rs.getString("CHECK_POINT"));
			o.setReasonCd(rs.getString("REASON_CD"));
			
			return o;
		}
	};
			

	public List<SboMobOrderDTO> findSboMobOrder(String orderId,
			String idDocType, String idDocNum,
			String srvNum,
			String contactEmail,
			String contactNum) throws DAOException {
		logger.info("findSboMobOrder is called");
		
		String SQL = "SELECT "
				
				+ " O.LOB, O.ORDER_ID, O.OCID, "
				+ " C.ID_DOC_TYPE, C.ID_DOC_NUM, "
				+ " C.LAST_NAME || ' ' || C.FIRST_NAME CUST_FULL_NAME, "
				+ " O.MSISDN, O.APP_DATE, O.ORDER_STATUS, "
				+ " O.ESIG_EMAIL_ADDR, D.DELIVERY_DATE, "
				+ " T.TIMEFROM || '-' || T.TIMETO DELIVERY_TIME_SLOT, "
				+ " O.SRV_REQ_DATE, "
				+ " O.CHECK_POINT, "
				+ " O.REASON_CD, "
				+ " O.ORDER_TYPE, "				
				+ " S.BOM_ORDER_STATUS, "
				+ " S.SRV_REQ_DATE ACT_SRV_REQ_DATE,  "
				+ " (select DECODE(count(1), 0, 'N', 'Y') "
				+ "		from BOMWEB_CODE_LKUP CL, BOMWEB_ORDER OC "
				+ "		where OC.REASON_CD = CL.CODE_ID "
				+ "		and CL.CODE_TYPE in ( 'ALLOW_CANCEL_FO_CODE', 'FORCE_CANCEL_FO_CODE') "
				+ "		and OC.ORDER_ID = o.order_id ) ALLOW_CANCEL_IND "
				
				+ " FROM "
				+ " BOMWEB_ORDER O, BOMWEB_CUSTOMER C, BOMWEB_DELIVERY D, BOMWEB_SB_BOM_ORDER S, BOMWEB_CONTACT CO, W_DELIVERY_TIMESLOT T "
				+ " WHERE "
				
				+ " O.ORDER_ID=C.ORDER_ID "
				+ " AND O.ORDER_ID=D.ORDER_ID(+) "
				+ " AND D.DELIVERY_TIME_SLOT = T.TIMESLOT(+) "
				+ " AND S.ORDER_ID(+) = O.ORDER_ID " 
				+ " AND C.ORDER_ID=CO.ORDER_ID(+) AND CO.CONTACT_TYPE(+)='CC' "
				+ " AND O.SHOP_CD IN ('SBO' , 'SBS') "
				+ " AND O.LOB IN ('MOB')"
				
				+ " AND (NVL(:orderId, 'ANY')='ANY' OR O.ORDER_ID=:orderId) "
				+ " AND (NVL(:idDocType, 'ANY')='ANY' OR C.ID_DOC_TYPE=:idDocType) "
				+ " AND (NVL(:idDocNum, 'ANY')='ANY' OR C.ID_DOC_NUM=:idDocNum) "
				+ " AND (NVL(:srvNum, 'ANY')='ANY' OR O.MSISDN=:srvNum) "
				+ " AND (NVL(:contactEmail, 'ANY')='ANY' OR UPPER(O.ESIG_EMAIL_ADDR) LIKE :contactEmailWC) "
				+ " AND (NVL(:contactNum, 'ANY')='ANY' OR CO.CONTACT_PHONE=:contactNum) "
				+ " ORDER BY O.CREATE_DATE DESC"
				;
		
		logger.debug("SQL:\n" + SQL);
		
		idDocType = (StringUtils.isEmpty(idDocNum) ? null : idDocType);
		idDocNum = StringUtils.upperCase(idDocNum);
		orderId = StringUtils.upperCase(orderId);
		contactEmail = StringUtils.upperCase(contactEmail);

		 
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("orderId", orderId);
		params.addValue("idDocType", idDocType);
		params.addValue("idDocNum", idDocNum);
		params.addValue("srvNum", srvNum);
		params.addValue("contactEmail", contactEmail);
		params.addValue("contactEmailWC", "%"+contactEmail+"%");
		params.addValue("contactNum", contactNum);

		
		try {
			return simpleJdbcTemplate.query(SQL, sboMobOrderMapper, params);
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException");
		} catch (Exception e) {
			logger.error("Exception caught in findOrderEnquiry()", e);
			throw new DAOException(e.getMessage(), e);
		}
		
		return null;
	}
	
	
	public int countSboMobOrder(String orderId,
			String idDocType, String idDocNum,
			String srvNum,
			String contactEmail) throws DAOException {
		logger.info("countSboMobOrder is called");
		
		String SQL = "SELECT "
				+ " count(O.ORDER_ID) "
				
				+ " FROM "
				+ " BOMWEB_ORDER O, BOMWEB_CUSTOMER C, BOMWEB_DELIVERY D, BOMWEB_SB_BOM_ORDER S, W_DELIVERY_TIMESLOT T "
				+ " WHERE "
				
				+ " O.ORDER_ID=C.ORDER_ID "
				+ " AND O.ORDER_ID=D.ORDER_ID "
				+ " AND D.DELIVERY_TIME_SLOT = T.TIMESLOT(+) "
				+ " AND S.ORDER_ID(+) = O.ORDER_ID " 
				+ " AND O.SHOP_CD='SBO' "
				
				+ " AND (NVL(:orderId, 'ANY')='ANY' OR O.ORDER_ID=:orderId) "
				+ " AND (NVL(:idDocType, 'ANY')='ANY' OR C.ID_DOC_TYPE=:idDocType) "
				+ " AND (NVL(:idDocNum, 'ANY')='ANY' OR C.ID_DOC_NUM=:idDocNum) "
				+ " AND (NVL(:srvNum, 'ANY')='ANY' OR O.MSISDN=:srvNum) "
				+ " AND (NVL(:contactEmail, 'ANY')='ANY' OR UPPER(O.ESIG_EMAIL_ADDR) LIKE :contactEmailWC) "
				
				+ " ORDER BY O.CREATE_DATE DESC"
				;
		
		logger.debug("SQL:\n" + SQL);
		
		idDocType = (StringUtils.isEmpty(idDocNum) ? null : idDocType);
		idDocNum = StringUtils.upperCase(idDocNum);
		orderId = StringUtils.upperCase(orderId);
		contactEmail = StringUtils.upperCase(contactEmail);

		 
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("orderId", orderId);
		params.addValue("idDocType", idDocType);
		params.addValue("idDocNum", idDocNum);
		params.addValue("srvNum", srvNum);
		params.addValue("contactEmail", contactEmail);
		params.addValue("contactEmailWC", "%"+contactEmail+"%");
		
		try {
			return simpleJdbcTemplate.queryForInt(SQL, params);
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException");
		} catch (Exception e) {
			logger.error("Exception caught in findOrderEnquiry()", e);
			throw new DAOException(e.getMessage(), e);
		}
		
		return 0;
	}
}
