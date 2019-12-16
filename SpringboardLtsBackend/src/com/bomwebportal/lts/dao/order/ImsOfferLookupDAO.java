package com.bomwebportal.lts.dao.order;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.bomwebportal.dao.BaseDAO;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.lts.dto.order.ImsOfferDetailDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailOtherLtsDTO;
import com.bomwebportal.lts.util.LtsBackendConstant;

public class ImsOfferLookupDAO extends BaseDAO {

	private static final String SQL_GET_IMS_OFFER_BY_GRP_ID = 
			"select device_type, offer_id, prod_id " +
			"from w_eye_ims_offer_lkup " +
			"where ims_offer_grp_id = ? and device_type = ? " +
			"and WG_IND = ? and rental_router_ind = ? ";
	
	private static final String SQL_GET_IMS_NOWTV_OFFER = 
			"select offer_id, prod_id " +
			"from w_eye_ims_nowtv_offer_lkup " +
			"where ims_offer_grp_id = ? " +
			"and wall_garden_ind = ? " +
			"and lower_arpu <= ? " +
			"and upper_arpu >= ?";
	
	private static final String SQL_GET_IMS_TECH_CHANGE_OFFER = 
			"select offer_id, prod_id " +
			"from w_eye_ims_tech_offer_lkup " +
			"where offer_id is not null";
	
	
	public ImsOfferDetailDTO[] getImsOfferByGroupId(String pGrpId, String pDeviceType, String pWgOfferInd, String pRentalRouterInd) throws DAOException {
		
		try {
			return this.simpleJdbcTemplate.query(SQL_GET_IMS_OFFER_BY_GRP_ID, this.getOfferMapper(), pGrpId, pDeviceType, pWgOfferInd, pRentalRouterInd).toArray(new ImsOfferDetailDTO[0]);
		} catch (EmptyResultDataAccessException erdae) {
			throw new AppRuntimeException(erdae);
		} catch (Exception e) {
			logger.error("Error in getImsOfferByGroupId()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public ImsOfferDetailDTO[] getImsNowtvOffer(String pGrpId, ServiceDetailOtherLtsDTO pSrvDtl) throws DAOException {
		
		try {
			List<ImsOfferDetailDTO> imsOfferList = null;
			StringBuilder sqlSb = new StringBuilder(SQL_GET_IMS_NOWTV_OFFER);
			sqlSb.append(" and exist_modem = ?");
			
			imsOfferList = this.simpleJdbcTemplate.query(sqlSb.toString(), this.getOfferMapper(), pGrpId, 
					StringUtils.equals(LtsBackendConstant.ORDER_TYPE_INSTALL, pSrvDtl.getOrderType()) ? "Y" : "N", 
					pSrvDtl.getExistArpu(), pSrvDtl.getExistArpu(), pSrvDtl.getExistModem());
			
			if (imsOfferList.size() == 0) {
				sqlSb = new StringBuilder(SQL_GET_IMS_NOWTV_OFFER);
				sqlSb.append(" and exist_modem is null");
				
				imsOfferList = this.simpleJdbcTemplate.query(sqlSb.toString(), this.getOfferMapper(), pGrpId, 
						StringUtils.equals(LtsBackendConstant.ORDER_TYPE_INSTALL, pSrvDtl.getOrderType()) ? "Y" : "N", 
						pSrvDtl.getExistArpu(), pSrvDtl.getExistArpu());
			}
			for (int i=0; i<imsOfferList.size(); ++i) {
				imsOfferList.get(i).setDiscIncInd("Y");
			}
			return imsOfferList.toArray(new ImsOfferDetailDTO[imsOfferList.size()]);
		} catch (EmptyResultDataAccessException erdae) {
			throw new AppRuntimeException(erdae);
		} catch (Exception e) {
			logger.error("Error in getImsNowtvOffer()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public ImsOfferDetailDTO[] getImsTechnologyOffer(String pExistTech, String pNewTech) throws DAOException {
		
		StringBuilder sb = new StringBuilder(SQL_GET_IMS_TECH_CHANGE_OFFER);
		
		if (StringUtils.isEmpty(pExistTech)) {
			sb.append(" and exist_tech is null");
		} else {
			sb.append(" and exist_tech = ");
			sb.append("'");
			sb.append(pExistTech);
			sb.append("'");
		}
		if (StringUtils.isEmpty(pNewTech)) {
			sb.append(" and new_tech is null");
		} else {
			sb.append(" and new_tech = ");
			sb.append("'");
			sb.append(pNewTech);
			sb.append("'");
		}
		try {
			return this.simpleJdbcTemplate.query(sb.toString(), this.getOfferMapper()).toArray(new ImsOfferDetailDTO[0]);
		} catch (EmptyResultDataAccessException erdae) {
			throw new AppRuntimeException(erdae);
		} catch (Exception e) {
			logger.error("Error in getImsTechnologyOffer()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	private ParameterizedRowMapper<ImsOfferDetailDTO> getOfferMapper() {
		return new ParameterizedRowMapper<ImsOfferDetailDTO>() {
			public ImsOfferDetailDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				ImsOfferDetailDTO imsOfferDtl = new ImsOfferDetailDTO();
				imsOfferDtl.setOfferId(rs.getString("OFFER_ID"));
				imsOfferDtl.setProductId(rs.getString("PROD_ID"));
				return imsOfferDtl;
			}
		};
	}
}
