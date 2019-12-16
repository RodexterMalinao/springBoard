package com.bomwebportal.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.bomwebportal.dto.OrderMobDTO;
import com.bomwebportal.exception.DAOException;
import com.pccw.bom.mob.schemas.ProductDTO;

public class OrderMobDAO extends BaseDAO {
	protected final Log logger = LogFactory.getLog(getClass());

	public OrderMobDTO getOrderMobDTO(String orderId) throws DAOException {
		logger.debug("getOrderMobDTO is called");
		final String sql = "select" +
				" ORDER_ID" +
				" , SHOP_CD" +
				" , IGUARD_SN" +
				" , CREATE_BY" +
				" , CREATE_DATE" +
				" , LAST_UPD_BY" +
				" , LAST_UPD_DATE" +
				" , NFC_IND" +
				" , STAFF_ID" + 
				" , CCC" +
				" , SPONSOR_LEVEL" +
				" , ORDER_SUB_TYPE" +
				" , MANUAL_AF_NO" +
				" , CARE_STATUS"+
				" , CARE_OPT_IND"+
				" , CARE_DM_SUP_IND"+
				" , LOCATION_CD"+
				" , CSUB_IND"+
				" , HKBN_IND "+
				" , STOCK_ASSGN_DATE "+
				" , CAMPAIGN_ID "+   // MBU2019003 -- Add CAMPAIGN_ID  
				" FROM" +
				"   BOMWEB_ORDER_MOB" +
				" WHERE" +
				"  ORDER_ID = :orderId";
		if (logger.isDebugEnabled()) {
			logger.debug(sql);
			logger.debug("orderId: " + orderId);
		}
		
		ParameterizedRowMapper<OrderMobDTO> mapper = new ParameterizedRowMapper<OrderMobDTO>() {
			public OrderMobDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				OrderMobDTO orderMobDTO = new OrderMobDTO();
				orderMobDTO.setOrderId(rs.getString("ORDER_ID"));
				orderMobDTO.setShopCd(rs.getString("SHOP_CD"));
				orderMobDTO.setIguardSn(rs.getString("IGUARD_SN"));
				orderMobDTO.setNfcInteger(rs.getString("NFC_IND"));
				orderMobDTO.setStaffId(rs.getString("STAFF_ID"));
				orderMobDTO.setCcc(rs.getString("CCC"));
				orderMobDTO.setSponsorLevel(rs.getString("SPONSOR_LEVEL"));
				orderMobDTO.setOrderSubType(rs.getString("ORDER_SUB_TYPE"));
				orderMobDTO.setManualAfNo(rs.getString("MANUAL_AF_NO"));
				orderMobDTO.setCareStatus(rs.getString("CARE_STATUS"));
				orderMobDTO.setCareOptInd(rs.getString("CARE_OPT_IND"));
				orderMobDTO.setCareDmSupInd(rs.getString("CARE_DM_SUP_IND"));
				orderMobDTO.setLocationCd(rs.getString("LOCATION_CD"));
				orderMobDTO.setCsubInd(rs.getString("CSUB_IND"));
				orderMobDTO.setHkbnInd(rs.getString("HKBN_IND"));
				orderMobDTO.setStockAssgnDate(rs.getTimestamp("STOCK_ASSGN_DATE"));
				// MBU2019003 -- Add CAMPAIGN_ID 
				orderMobDTO.setCampaignId(rs.getString("CAMPAIGN_ID"));
				return orderMobDTO;
			}
		};
		
		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("orderId", orderId);
			List<OrderMobDTO> list = this.simpleJdbcTemplate.query(sql, mapper, params);
			return this.isEmpty(list) ? null : list.get(0);
		} catch (EmptyResultDataAccessException erdae) {
			if (logger.isDebugEnabled()) {
				logger.debug("EmptyResultDataAccessException in getOrderMobDTO()");
			}
			return null;
		} catch (Exception e) {
			logger.info("Exception caught in getOrderMobDTO():", e);
			throw new DAOException(e.getMessage(), e);
		}
	}

	public int insertBomwebOrderMob(OrderMobDTO dto) throws DAOException {
		logger.debug("insertBomwebOrderMob is called");

		final String sql = "insert into" +
				" BOMWEB_ORDER_MOB" +
				" (" +
				" ORDER_ID" +
				" , SHOP_CD" +
				" , IGUARD_SN" +
				" , CREATE_BY" +
				" , CREATE_DATE" +
				" , LAST_UPD_BY" +
				" , LAST_UPD_DATE" +
				" , NFC_IND" +
				" , STAFF_ID" + 
				" , CCC" +
				" , SPONSOR_LEVEL" +
				" , MANUAL_AF_NO"+
				" , CARE_STATUS"+
				" , CARE_OPT_IND"+
				" , CARE_DM_SUP_IND"+
				" , LOCATION_CD"+
				" , CSUB_IND"+
				" , HKBN_IND"+
				" , STOCK_ASSGN_DATE"+
				" , CAMPAIGN_ID"+   // MBU2019003 -- Add CAMPAIGN_ID 
				" ) values (" +
				" :orderId" +
				" , :shopCd" +
				" , :iguardSn" +
				" , :createBy" +
				" , sysdate" +
				" , :lastUpdBy" +
				" , sysdate" +
				" , :nfcInd" +
				" , :staffId" +
				" , :ccc" +
				" , :sponsorLevel" +
				" , :manualAfNo" +
				" , :careStatus"+
				" , :careOptInd"+
				" , :careDmSupInd"+
				" , :locationCd"+
				" , :csubInd"+
				" , :hkbnInd"+
				" , :stockAssgnDate"+
				" , :campaignId"+   // MBU2019003 -- Add CAMPAIGN_ID
				" )";
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("orderId", dto.getOrderId());
		params.addValue("shopCd", dto.getShopCd());
		params.addValue("iguardSn", dto.getIguardSn());
		params.addValue("createBy", dto.getCreateBy());
		params.addValue("lastUpdBy", dto.getLastUpdBy());
		params.addValue("nfcInd", dto.getNfcInteger()); //201309
		params.addValue("staffId", dto.getStaffId());
		params.addValue("ccc", dto.getCcc());
		params.addValue("sponsorLevel", dto.getSponsorLevel());
		params.addValue("manualAfNo", dto.getManualAfNo());
		params.addValue("careStatus", dto.getCareStatus());
		params.addValue("careOptInd", dto.getCareOptInd());
		params.addValue("careDmSupInd", dto.getCareDmSupInd());
		params.addValue("locationCd", dto.getLocationCd());
		params.addValue("csubInd", dto.getCsubInd());
		params.addValue("hkbnInd", dto.getHkbnInd());
		params.addValue("stockAssgnDate", dto.getStockAssgnDate());
		// MBU2019003 -- Add CAMPAIGN_ID
		params.addValue("campaignId", dto.getCampaignId());
		try {
			logger.debug("insertBomwebOrderMob() @ BomwebOrderMobDAO: " + sql);
			return simpleJdbcTemplate.update(sql, params);
		} catch (Exception e) {
			logger.error("Exception caught in insertBomwebOrderMob()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}

	
	private static final String deleteBomwebOrderMobSQL = "delete from BOMWEB_ORDER_MOB where ORDER_ID=? ";

	public int deleteBomwebOrderMob(String orderId) throws DAOException {
		logger.debug("deleteBomwebOrderMob is called");

		try {
			logger.info("deleteBomwebOrderMob() @ BomwebOrderMobDAO: "
					+ deleteBomwebOrderMobSQL);
			return simpleJdbcTemplate.update(deleteBomwebOrderMobSQL,
					orderId);
		} catch (Exception e) {
			logger.error("Exception caught in deleteBomwebOrderMob()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	private <T> boolean isEmpty(List<?> list) {
		return list == null || list.isEmpty();
	}

}
