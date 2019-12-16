package com.bomwebportal.sbs.dao;

import java.sql.Types;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;

import com.bomwebportal.dao.BaseDAO;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.sbs.dto.SbsContactInfoDTO;
import com.bomwebportal.sbs.dto.SbsDeliveryInfoDTO;
import com.bomwebportal.sbs.dto.StOrderPosSmDTO;
import com.bomwebportal.sbs.dto.StSubscribedItemDTO;
import com.bomwebportal.sbs.dto.StSubscribedVkkItemDTO;
import com.bomwebportal.util.BomWebPortalConstant;

public class SbsOrderDAO extends BaseDAO {
	
	//protected final Log logger = LogFactory.getLog(getClass());
	
	public SbsContactInfoDTO getContactInfo(String orderId) throws DAOException {
		String sql = "SELECT " 
				+ " cu.ORDER_ID, "
				+ " cu.TITLE, "
				+ " cu.FIRST_NAME, "
				+ " cu.LAST_NAME, "
				+ " co.CONTACT_NAME, "
				+ " co.CONTACT_PHONE, "
				+ " co.OTHER_PHONE, "
				+ " co.EMAIL_ADDR, "
				+ " co.CONTACT_TYPE, "
				+ " co.CLUB_MEM_ID, "
				+ " co.CREATE_BY, "
				+ " co.CREATE_DATE, "
				+ " co.LAST_UPD_BY, "
				+ " co.LAST_UPD_DATE "
				+ " FROM "
				+ " BOMWEB_CUSTOMER cu, BOMWEB_CONTACT co "
				+ " WHERE "
				+ " cu.ORDER_ID=co.ORDER_ID "
				+ " AND co.CONTACT_TYPE='CC' "
				+ " AND cu.ORDER_ID=:orderId ";
		
		try {
			logger.info("getContactInfo() @ SbsOrderDAO: " + sql);
			
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("orderId", orderId);
			List<SbsContactInfoDTO> list = this.simpleJdbcTemplate.query(sql,
					ParameterizedBeanPropertyRowMapper.newInstance(SbsContactInfoDTO.class), params);
			
			return CollectionUtils.isEmpty(list) ? null : list.get(0);
			
		} catch (EmptyResultDataAccessException erdae) {
			if (logger.isDebugEnabled()) {
				logger.debug("EmptyResultDataAccessException in getContactInfo()");
			}
			return null;
		} catch (Exception e) {
			logger.info("Exception caught in getContactInfo():", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public SbsDeliveryInfoDTO getDeliveryInfo(String orderId) throws DAOException {
		String sql = "select "
				+ " ca.ORDER_ID, "
				+ " ca.ADDR_USAGE, "
				+ " ca.AREA_CD, "
				+ " ca.DIST_NO, "
				+ " ca.SECT_CD, "
				+ " ca.STR_NAME, "
				+ " ca.HI_LOT_NO, "
				+ " ca.STR_CAT_CD, "
				+ " ca.BUILD_NO, "
				+ " ca.FOREIGN_ADDR_FLAG, "
				+ " ca.FLOOR_NO, ca.UNIT_NO, "
				+ " ca.PO_BOX_NO, ca.ADDR_TYPE, "
				+ " ca.STR_NO, "
				+ " ca.SECT_DEP_IND, "
				+ " ca.AREA_DESC, "
				+ " ca.DIST_DESC, "
				+ " ca.SECT_DESC, "
				+ " ca.STR_CAT_DESC, "
				+ " ca.SERBDYNO, "
				+ " d.DELIVERY_IND, "
				+ " d.DELIVERY_DATE, "
				+ " d.ACTUAL_DELIVERY_DATE, "
				+ " d.DELIVERY_TIME_SLOT, "
				+ " ca.CREATE_BY, ca.CREATE_DATE, ca.LAST_UPD_BY, ca.LAST_UPD_DATE "
				+ " from "
				+ " BOMWEB_CUST_ADDR ca, BOMWEB_DELIVERY d "
				+ " where "
				+ " ca.ORDER_ID=d.ORDER_ID "
				+ " AND ca.ORDER_ID=:orderId "
				+ " AND ca.ADDR_USAGE='DA' " ;
		
		try {
			logger.info("getDeliveryInfo() @ SbsOrderDAO: " + sql);
			
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("orderId", orderId);
			List<SbsDeliveryInfoDTO> list = this.simpleJdbcTemplate.query(sql,
					ParameterizedBeanPropertyRowMapper.newInstance(SbsDeliveryInfoDTO.class), params);
			
			return CollectionUtils.isEmpty(list) ? null : list.get(0);
			
		} catch (EmptyResultDataAccessException erdae) {
			if (logger.isDebugEnabled()) {
				logger.debug("EmptyResultDataAccessException in getDeliveryInfo()");
			}
			return null;
		} catch (Exception e) {
			logger.info("Exception caught in getDeliveryInfo():", e);
			throw new DAOException(e.getMessage(), e);
		}		
		
	}
	
	
	
	public void updateBomwebCustomer(SbsContactInfoDTO dto) throws DAOException {
		String sql = "UPDATE  BOMWEB_CUSTOMER cu "
				+ " set "
				+ " cu.TITLE=:title, "
				+ " cu.FIRST_NAME=:firstName, "
				+ " cu.LAST_NAME=:lastName, "
				+ " cu.LAST_UPD_BY=:lastUpdBy, "
				+ " cu.LAST_UPD_DATE=sysdate "
				+ " WHERE "
				+ " cu.ORDER_ID=:orderId ";
		
		try {
			logger.info("updateBomwebCustomer() @ SbsOrderDAO: "
					+ sql);

			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("orderId", dto.getOrderId());
			params.addValue("title", dto.getTitle());
			params.addValue("firstName", dto.getFirstName());
			params.addValue("lastName", dto.getLastName());
			params.addValue("lastUpdBy", dto.getLastUpdBy());

			simpleJdbcTemplate.update(sql,
					params);
		} catch (Exception e) {
			logger.error("Exception caught in updateBomwebCustomer()", e);
			throw new DAOException(e.getMessage(), e);
		}		
		
	}
	
	public void updateBomwebContactAll(SbsContactInfoDTO dto) throws DAOException {
		String sql = "update BOMWEB_CONTACT co "
				+ " set "
				+ " co.TITLE=:title, "
				+ " co.CONTACT_NAME=:contactName, "
				+ " co.CONTACT_PHONE=:contactPhone, "
				+ " co.OTHER_PHONE=:otherPhone, "
				+ " co.EMAIL_ADDR=:emailAddr, "
				+ " co.LAST_UPD_BY=:lastUpdBy, "
				+ " co.LAST_UPD_DATE=sysdate "
				+ " where "
				+ " co.ORDER_ID=:orderId ";
				//+ " AND co.CONTACT_TYPE=:contactType ";
		
		try {
			logger.info("updateBomwebContact() @ SbsOrderDAO: "
					+ sql);

			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("orderId", dto.getOrderId());
			params.addValue("title", dto.getTitle());
			params.addValue("contactName", dto.getContactName());
			params.addValue("contactPhone", dto.getContactPhone());
			params.addValue("otherPhone", dto.getOtherPhone());
			params.addValue("emailAddr", dto.getEmailAddr());
			//params.addValue("contactType", dto.getContactType());
			params.addValue("lastUpdBy", dto.getLastUpdBy());

			simpleJdbcTemplate.update(sql,	params);
		} catch (Exception e) {
			logger.error("Exception caught in updateBomwebContactDC()", e);
			throw new DAOException(e.getMessage(), e);
		}		
		
	}
	
	public void updateBomwebCustAddrAll(SbsDeliveryInfoDTO dto) throws DAOException {
		String sql = "update BOMWEB_CUST_ADDR ca "
				+ " set "
				+ " ca.AREA_CD=:areaCd, "
				+ " ca.DIST_NO=:distNo, "
				+ " ca.SECT_CD=:sectCd, "
				+ " ca.STR_NAME=:strName, "
				+ " ca.HI_LOT_NO=:hiLotNo, "
				+ " ca.STR_CAT_CD=:strCatCd, "
				+ " ca.BUILD_NO=:buildNo, "
				+ " ca.FOREIGN_ADDR_FLAG=:foreignAddrFlag, "
				+ " ca.FLOOR_NO=:floorNo, "
				+ " ca.UNIT_NO=:unitNo, "
				+ " ca.PO_BOX_NO=:poBoxNo,"
				+ " ca.ADDR_TYPE=:addrType, "
				+ " ca.STR_NO=:strNo, "
				+ " ca.SECT_DEP_IND=:sectDepInd, "
				+ " ca.AREA_DESC=:areaDesc, "
				+ " ca.DIST_DESC=:distDesc, "
				+ " ca.SECT_DESC=:sectDesc, "
				+ " ca.STR_CAT_DESC=:strCatDesc, "
				+ " ca.SERBDYNO=:serbdyno, "
				+ " ca.LAST_UPD_BY=:lastUpdBy, "
				+ " ca.free_input_ind= 'N' , "
				+ " ca.LAST_UPD_DATE=sysdate "
				+ " where "
				+ " ca.ORDER_ID=:orderId ";
				//+ " AND ca.ADDR_USAGE=:type " ;
		
		try {
			logger.info("updateBomwebCustAddrDA() @ SbsOrderDAO: "
					+ sql);

			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("areaCd", dto.getAreaCd());
			params.addValue("distNo", dto.getDistNo());
			params.addValue("sectCd", dto.getSectCd());
			params.addValue("strName", dto.getStrName());
			params.addValue("hiLotNo", dto.getHiLotNo());
			params.addValue("strCatCd", dto.getStrCatCd());
			params.addValue("buildNo", dto.getBuildNo());
			params.addValue("foreignAddrFlag", dto.getForeignAddrFlag());
			params.addValue("floorNo", dto.getFloorNo());
			params.addValue("unitNo", dto.getUnitNo());
			params.addValue("poBoxNo", dto.getPoBoxNo());
			params.addValue("addrType", dto.getAddrType());
			params.addValue("strNo", dto.getStrNo());
			params.addValue("sectDepInd", dto.getSectDepInd());
			params.addValue("areaDesc", dto.getAreaDesc());
			params.addValue("distDesc", dto.getDistDesc());
			params.addValue("sectDesc", dto.getSectDesc());
			params.addValue("strCatDesc", dto.getStrCatDesc());
			params.addValue("serbdyno", dto.getSerbdyno());
			params.addValue("lastUpdBy", dto.getLastUpdBy());
			params.addValue("orderId", dto.getOrderId());

			simpleJdbcTemplate.update(sql,	params);
		} catch (Exception e) {
			logger.error("Exception caught in updateBomwebCustAddrDA()", e);
			throw new DAOException(e.getMessage(), e);
		}		
		
	}
	
	
	public List<? extends StSubscribedItemDTO> getSubscribedItems(String orderId) throws DAOException {
		/*
		String sql = "select "
				+ " si.ORDER_ID, "
				+ " si.SEQ, "
				+ " si.PARENT_ITEM_ID, "
				+ " si.ITEM_ID, "
				+ " si.ITEM_TYPE, "
				+ " si.ITEM_DESC, "
				+ " si.ITEM_SERIAL, "
				+ " si.CREATE_BY, "
				+ " p.ONETIME_AMT as upfront_amt , "
				+ " d.POS_ITEM_CD, "
				+ " si.CREATE_DATE, "
				+ " si.LAST_UPD_BY, "
				+ " si.LAST_UPD_DATE "
				+ " from "
				+ " BOMWEB_ST_SUBSCRIBED_ITEM si, BOMWEB_ORDER o, W_ST_ITEM_DTL d, W_ST_ITEM_PRICING p "
				//+ " BOMWEB_ST_SUBSCRIBED_ITEM si, BOMWEB_ORDER o, W_ST_ITEM_PRICING p "
				+ " where "
				+ " si.ORDER_ID=o.order_id "
				+ " and si.ITEM_ID=d.item_id(+) "
				+ " and d.POS_ITEM_CD=p.POS_ITEM_CD(+) "
				//+ " and TRUNC(nvl(o.APP_DATE, sysdate)) between trunc(nvl(p.EFF_START_DATE, sysdate)) and trunc(nvl(p.EFF_END_DATE, sysdate)) "
				+ " and si.ORDER_ID=:orderId "
				+ " order by si.SEQ" ;
			*/
		String sql = "select "
				+ " si.ORDER_ID, "
				+ " si.SEQ, "
				+ " si.PARENT_ITEM_ID, "
				+ " si.ITEM_ID, "
				+ " si.ITEM_TYPE, "
				+ " si.ITEM_DESC, "
				+ " a.ITEM_SERIAL, "
				+ " si.ONETIME_AMT , "
				+ " si.POS_ITEM_CD, "
				+ " si.CHARGE_CLASS, "
				+ " si.PROMO_CODE, "
				+ " si.PROMO_CODE_MRT, "
				+ " m.msisdn, "
				+ " m.exp_date, "
				+ " m.password, "
				+ " a.doa_old_item_serial, "
				+ " a.doa_old_item_code, "
				+ " si.CREATE_BY, "				
				+ " si.CREATE_DATE, "
				+ " si.LAST_UPD_BY, "
				+ " si.LAST_UPD_DATE "
				+ " from "
				+ " BOMWEB_ST_SUBSCRIBED_ITEM si "
				+ " inner join BOMWEB_ORDER o on ( "
				+ "   o.order_id=si.order_id "
				+ " ) "
				+ " left join BOMWEB_STOCK_ASSGN a on ( "
				+ "   si.order_id=a.order_id "
				+ "   and nvl(a.status_id, '#') != '24' "
				+ "   and si.pos_item_cd=a.item_code "
				+ "   and si.seq=a.seq "
				+ " ) "
				+ " left join BOMWEB_MRT_INVENTORY m on ( "
				+ "   si.item_serial=m.msisdn "
				+ "   and si.item_type='SVAS' "
				+ "   and m.channel_cd='SBS' "
				+ "   and m.msisdn=o.msisdn "
				+ " ) "
				+ " where "
				+ " si.ORDER_ID=:orderId "
				+ " order by si.SEQ" ;
		

		try {
			logger.info("getSubscribedItems() @ SbsOrderDAO: " + sql);

			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("orderId", orderId);
			List<? extends StSubscribedItemDTO> list = this.simpleJdbcTemplate.query(sql,
					ParameterizedBeanPropertyRowMapper.newInstance(StSubscribedVkkItemDTO.class), params);
			
			return list;
			
		} catch (EmptyResultDataAccessException erdae) {
			if (logger.isDebugEnabled()) {
				logger.debug("EmptyResultDataAccessException in getSubscribedItems()");
			}
			return null;
		} catch (Exception e) {
			logger.info("Exception caught in getSubscribedItems():", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	
	public void updateBomwebDelivery(SbsDeliveryInfoDTO dto) throws DAOException {
		String sql = "update BOMWEB_DELIVERY d "
				+ " set "
				+ " d.DELIVERY_IND=:deliveryInd, "
				+ " d.DELIVERY_DATE=:deliveryDate, "
				+ " d.DELIVERY_TIME_SLOT=:deliveryTimeSlot, "
				+ " d.LAST_UPD_BY=:lastUpdBy, "
				+ " d.LAST_UPD_DATE=sysdate "
				+ " where "
				+ " d.ORDER_ID=:orderId ";

		
		try {
			logger.info("updateBomwebDelivery() @ SbsOrderDAO: "
					+ sql);

			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("deliveryInd", dto.getDeliveryInd());
			params.addValue("deliveryDate", dto.getDeliveryDate());
			params.addValue("deliveryTimeSlot", dto.getDeliveryTimeSlot());
			params.addValue("orderId", dto.getOrderId());
			params.addValue("lastUpdBy", dto.getLastUpdBy());

			simpleJdbcTemplate.update(sql,	params);
		} catch (Exception e) {
			logger.error("Exception caught in updateBomwebDelivery()", e);
			throw new DAOException(e.getMessage(), e);
		}		
		
	}
	
	public List<StOrderPosSmDTO> getOrderPosSms(String orderId) throws DAOException {
		
		String sql = " select "
				+ " ORDER_ID, "
				+ " SM_NUM, "
				+ " SM_TYPE_DESC, "
				+ " REMARK, "
				+ " CREATE_DATE, "
				+ " CREATE_BY, "
				+ " LAST_UPD_DATE, "
				+ " LAST_UPD_BY "
				+ " from BOMWEB_ST_ORDER_POS_ASSIGN "
				+ " where ORDER_ID=:orderId "
				+ " order by CREATE_DATE ";
		
		try {
			logger.info("getOrderPosSms() @ SbsOrderDAO: " + sql);

			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("orderId", orderId);
			List<StOrderPosSmDTO> list = this.simpleJdbcTemplate.query(sql,
					ParameterizedBeanPropertyRowMapper.newInstance(StOrderPosSmDTO.class), params);
			
			return list;
			
		} catch (EmptyResultDataAccessException erdae) {
			if (logger.isDebugEnabled()) {
				logger.debug("EmptyResultDataAccessException in getOrderPosSms()");
			}
			return null;
		} catch (Exception e) {
			logger.info("Exception caught in getOrderPosSms():", e);
			throw new DAOException(e.getMessage(), e);
		}		
	}
	
	
	public int insertStOrderPosSm(StOrderPosSmDTO dto) throws DAOException {
		String sql = "insert into BOMWEB_ST_ORDER_POS_ASSIGN ( ORDER_ID, "
				+ " SM_NUM, "
				+ " SM_TYPE_DESC, "
				+ " REMARK, "
				+ " CREATE_DATE, "
				+ " CREATE_BY, "
				+ " LAST_UPD_DATE, "
				+ " LAST_UPD_BY "
				+ " ) VALUES ( "
				+ " :orderId, "
				+ " :smNum, "
				+ " :smTypeDesc, "
				+ " :remark, "
				+ " sysdate, "
				+ " :createBy,  sysdate, :lastUpdBy "
				+ " )";
		try {
			logger.info("Create Parms");
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("orderId", dto.getOrderId());
			params.addValue("smNum", dto.getSmNum());
			params.addValue("smTypeDesc", dto.getSmTypeDesc());
			params.addValue("remark", dto.getRemark());
			params.addValue("createBy", dto.getCreateBy());
			params.addValue("lastUpdBy", dto.getLastUpdBy());

			logger.info("insertStOrderPosSm() @ SbsOrderDAO: "
					+ sql);
			return simpleJdbcTemplate.update(sql, params);
		} catch (Exception e) {
			logger.error("Exception caught in insertStOrderPosSm()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	
	
	
	
	public int insertOrderRemark(String orderId, String remark, String createBy) throws DAOException {
		
		String SQL = "INSERT INTO bomweb_order_remark" +
				" (" +
				" ORDER_ID" +
				" , REMARK" +
				" , CREATE_BY" +
				" , CREATE_DATE" +
				" , LAST_UPD_BY" +
				" , LAST_UPD_DATE" +
				" , SEQ" +
				" ) VALUES (" +
				" :ORDER_ID" +
				" , :REMARK" +
				" , :CREATE_BY" +
				" , sysdate" +
				" , :LAST_UPD_BY" +
				" , sysdate" +
				" , BOMWEB_ORDER_REMARK_SEQ.nextVal" +
				" )";
				
				
				/*"INSERT INTO BOMWEB_ORDER_REMARK ( " +
				" ORDER_ID, REMARK, SEQ, " +
				" CREATE_BY, CREATE_DATE, LAST_UPD_BY, LAST_UPD_DATE ) " +
				" SELECT " +
				" :ORDER_ID, :REMARK, NVL(MAX(SEQ),0)+1, "+ 
				" :CREATE_BY, sysdate, :LAST_UPD_BY, sysdate " +
				" FROM BOMWEB_ORDER_REMARK bor " +
				" 	WHERE bor.ORDER_ID=:ORDER_ID";*/
		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("ORDER_ID", orderId);
			params.addValue("REMARK", remark);
			params.addValue("CREATE_BY", createBy);
			params.addValue("LAST_UPD_BY", createBy);
			int result = this.simpleJdbcTemplate.update(SQL, params);
			return result;
		} catch (Exception e) {
			logger.error("Exception caught in insertOrderRemark(): ", e);
			throw new DAOException(e.getMessage(), e);
		}

	}
	
	
	
	
	
	
	
	public String releaseStOrderStockProcess(String orderId) throws DAOException {
		try {
			SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
					.withSchemaName("OPS$CNM")
					.withCatalogName("pkg_stock_assign_st")
					.withProcedureName("release_stock_by_order_st");
			jdbcCall.setAccessCallParameterMetaData(false);
			jdbcCall.declareParameters(
					new SqlParameter("i_order_id", Types.VARCHAR),
					new SqlOutParameter("gnRetVal",	Types.INTEGER),
					new SqlOutParameter("gnErrCode", Types.INTEGER),
					new SqlOutParameter("gsErrText", Types.VARCHAR));

			MapSqlParameterSource inMap = new MapSqlParameterSource();
			inMap.addValue("i_order_id", orderId);
			SqlParameterSource in = inMap;

			Map out = jdbcCall.execute(in);
			int error_code = BomWebPortalConstant.ERRCODE_FAIL;

			if (((Integer) out.get("gnErrCode")) != null) {
				error_code = ((Integer) out.get("gnErrCode")).intValue();
			}
			String error_text = (String) out.get("gsErrText");

			logger.info("pkg_stock_assign_st.release_stock_by_order_st() output error_code = "
					+ error_code);
			logger.info("pkg_stock_assign_st.release_stock_by_order_st() output error_text = "
					+ error_text);

			return error_text;

		} catch (Exception e) {
			logger.error("Exception caught in releaseStOrderStockProcess()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}	
	
	public void updateOrderStatus(String orderId, String status, String lastUpdBy) throws DAOException {
		logger.info(" updateOrderStatus is called");
		String SQL = "UPDATE BOMWEB_ORDER SET "
				+ " ORDER_STATUS=:ORDER_STATUS, "
				+ " LAST_UPDATE_BY=:LAST_UPDATE_BY, "
				+ " LAST_UPDATE_DATE=sysdate "
				+ " WHERE ORDER_ID=:ORDER_ID ";
		
		logger.debug("updateOrderStatus : " + "orderId=" + orderId + ", status="+status );
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("ORDER_STATUS", status)
			.addValue("LAST_UPDATE_BY", lastUpdBy)
			.addValue("ORDER_ID", orderId);
		try {
			logger.info("updateOrderStatus() : " + SQL);
			simpleJdbcTemplate.update(SQL, params);
		} catch (Exception e) {
			logger.error("Exception caught in updateOrderStatus()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public void updateOrderStatus(String orderId, String status, String reasonCode, String lastUpdBy) throws DAOException {
		logger.info(" updateOrderStatus is called");
		String SQL = "UPDATE BOMWEB_ORDER SET "
				+ " ORDER_STATUS=:ORDER_STATUS, "
				+ " REASON_CD=:REASON_CD, "
				+ " LAST_UPDATE_BY=:LAST_UPDATE_BY, "
				+ " LAST_UPDATE_DATE=sysdate "
				+ " WHERE ORDER_ID=:ORDER_ID ";
		
		logger.debug("updateOrderStatus : " + "orderId=" + orderId + ", status="+status + ", reasonCode="+reasonCode);
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("ORDER_STATUS", status)
			.addValue("REASON_CD", reasonCode)
			.addValue("LAST_UPDATE_BY", lastUpdBy)
			.addValue("ORDER_ID", orderId);
		try {
			logger.info("updateOrderStatus() : " + SQL);
			simpleJdbcTemplate.update(SQL, params);
		} catch (Exception e) {
			logger.error("Exception caught in updateOrderStatus()", e);
			throw new DAOException(e.getMessage(), e);
		}
		
		
	}
	
	public void updateOrderCheckPoint(String orderId, String checkPoint, String lastUpdBy) throws DAOException {
		logger.info(" updateOrderCheckPoint is called");
		String SQL = "UPDATE BOMWEB_ORDER SET "
				+ " CHECK_POINT=:CHECK_POINT, "
				+ " LAST_UPDATE_BY=:LAST_UPDATE_BY, "
				+ " LAST_UPDATE_DATE=sysdate "
				+ " WHERE ORDER_ID=:ORDER_ID ";
		
		logger.debug("updateOrderCheckPoint : " + "orderId=" + orderId + ", checkPoint="+checkPoint);
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("CHECK_POINT", checkPoint)
			.addValue("LAST_UPDATE_BY", lastUpdBy)
			.addValue("ORDER_ID", orderId);
		try {
			logger.info("updateOrderCheckPoint() : " + SQL);
			simpleJdbcTemplate.update(SQL, params);
		} catch (Exception e) {
			logger.error("Exception caught in updateOrderCheckPoint()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	
	public int changeDoaRequestStatus(String orderId, String toStatus, String fromStatus, String updateBy) throws DAOException {
		logger.info("changeDoaRequestStatus() is called");
		final String sql = "UPDATE bomweb_doa_request SET" +
				" status = :toStatus, " +
				" last_upd_by=:lastUpdBy, " +
				" last_upd_date=sysdate " +
				" WHERE order_id=:orderId " +
				" and status=:fromStatus ";
		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("toStatus", toStatus);
			params.addValue("orderId", orderId);
			params.addValue("fromStatus", fromStatus);
			params.addValue("lastUpdBy", updateBy);
			if (logger.isInfoEnabled()) {
				logger.info("changeDoaRequestStatus(): " + sql);
			}
			return this.simpleJdbcTemplate.update(sql, params);
		} catch (Exception e) {
			logger.info("Exception caught in changeDoaRequestStatus():", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
}
