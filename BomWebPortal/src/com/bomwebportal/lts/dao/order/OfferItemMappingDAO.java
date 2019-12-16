package com.bomwebportal.lts.dao.order;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.bomwebportal.exception.DAOException;
import com.bomwebportal.lts.dto.order.OfferItemRelationDTO;
import com.bomwebportal.lts.dto.order.OfferTypeDTO;
import com.bomwebportal.lts.dto.profile.OfferDetailProfileLtsDTO;
import com.bomwebportal.lts.util.LtsConstant;

public class OfferItemMappingDAO extends ItemOfferMappingDAO {
	
	private static final String SQL_GET_NO_TP_ITEM =
			"select exist_tp_offer_id, exist_tp_cd, exist_auto_renew_offer_id, exist_auto_renew_cd, exist_cross_lob_offer_id, exist_cross_lob_cd, " + 
			"exist_premium_offer_id, exist_premium_cd, exist_promotion_offer_id, exist_promotion_cd, change_to_offer_id, change_to_cd, term_exist_cd_ind, " +
			"exist_item_id, change_to_item_id, idd_free_min " +
			"from W_LTS_VAS_MAPPING " +
			"where exist_tp_offer_id is null " +
			"and eff_start_date(+) <= sysdate and (eff_end_date is null or eff_end_date > sysdate)";
	
	private static final String SQL_GET_FREE_VAS = 
			"select item_id " +
			"from w_lts_free_vas " +
			"where basket_type = ? and channel_id = ? " +
			"and nvl(contract_period, 0) = nvl(?, 0)";
	
	private static final String SQL_MAP_VAS_ITEM = 
			"select * from ( select * from ( " +
			"select exist_tp_offer_id, exist_tp_cd, exist_auto_renew_offer_id, exist_auto_renew_cd, exist_cross_lob_offer_id, exist_cross_lob_cd, " +
			"exist_premium_offer_id, exist_premium_cd, exist_promotion_offer_id, exist_promotion_cd, exist_item_id, allow_cancel, item_type, tp_premium, " +
			"(decode(exist_auto_renew_offer_id,NULL,0,1) + decode(exist_cross_lob_offer_id,NULL,0,1) + decode(exist_premium_offer_id,NULL,0,1) + " +
			"decode(exist_promotion_offer_id,NULL,0,1) + decode(exist_tp_offer_id,NULL,0,1)) weight " +
			"from w_lts_vas_lkup " +
			"where item_type <> 'LTS_VTP' and exist_promotion_offer_id in (:offerList) " +
			"and (exist_tp_offer_id in (:offerList) or exist_tp_offer_id is null) " +
			"and (exist_auto_renew_offer_id in (:offerList) or exist_auto_renew_offer_id is null) " +
			"and (exist_cross_lob_offer_id in (:offerList) or exist_cross_lob_offer_id is null) " +
			"and (exist_premium_offer_id in (:offerList) or exist_premium_offer_id is null) " +
			"and eff_start_date <= sysdate and (eff_end_date is null or eff_end_date > sysdate)) order by weight desc) where rownum < 2 ";

	private static final String SQL_MAP_VAS_TP_ITEM = 
			"select exist_tp_offer_id, exist_tp_cd, exist_auto_renew_offer_id, exist_auto_renew_cd, exist_cross_lob_offer_id, exist_cross_lob_cd, " +
			"exist_premium_offer_id, exist_premium_cd, exist_promotion_offer_id, exist_promotion_cd, exist_item_id, allow_cancel, item_type, tp_premium " +
			"from w_lts_vas_lkup " +
			"where item_type = :itemType and exist_tp_offer_id in (:offerList)" +
			"and exist_promotion_offer_id is null and exist_auto_renew_offer_id is null and exist_cross_lob_offer_id is null and exist_premium_offer_id is null ";

	private static final String SQL_MAP_IMS_OFFER = 
			"select offer_cd, item_id, item_type " +
			"from W_EYE_IMS_OFFER_ITEM_MAPPING " +
			"where offer_cd = ? and tp_expired_ind = ?";

	private static final String SQL_GET_OFFERS_BY_ITEM = 
			"select offer_id " +
			"from W_ITEM_OFFER_ASSGN " +
			"where item_id = ?";
	
	
	public OfferItemRelationDTO getVasOfferItemMapping(OfferDetailProfileLtsDTO[] pOfferDtls) throws DAOException {
		

		if (ArrayUtils.isEmpty(pOfferDtls)) {
			return null;
		}
		List<OfferItemRelationDTO> offerItemRelationList = null;
		List<String> offerCdList = this.generateOfferIdList(pOfferDtls);
		
		try {
			offerItemRelationList = simpleJdbcTemplate.query(SQL_MAP_VAS_ITEM, this.getVasOfferItemMapper(), Collections.singletonMap("offerList", offerCdList));
		} catch (EmptyResultDataAccessException erdae) {
			return null;
		} catch (Exception e) {
			logger.error("Error in getVasOfferItemMapping()", e);
			throw new DAOException(SQL_MAP_VAS_ITEM + e.getMessage(), e);
		}
		if (offerItemRelationList.size() == 0) {
			return null;
		}
		return offerItemRelationList.get(0);
	}
	
	public OfferItemRelationDTO[] getVasTpOfferItem(OfferDetailProfileLtsDTO[] pOfferDtls, String pItemType) throws DAOException {
		
		if (ArrayUtils.isEmpty(pOfferDtls)) {
			return null;
		}
		List<OfferItemRelationDTO> offerItemRelationList = null;
		List<String> offerCdList = this.generateOfferIdList(pOfferDtls);
		Map<String,Object> inputMap = new HashMap<String,Object>();
		inputMap.put("offerList", offerCdList);
		inputMap.put("itemType", pItemType);
		
		try {
			offerItemRelationList = simpleJdbcTemplate.query(SQL_MAP_VAS_TP_ITEM, this.getVasOfferItemMapper(), inputMap);
		} catch (EmptyResultDataAccessException erdae) {
			return new OfferItemRelationDTO[0];
		} catch (Exception e) {
			logger.error("Error in getVasOfferItemMapping()", e);
			throw new DAOException(SQL_MAP_VAS_TP_ITEM + e.getMessage(), e);
		}
		if (offerItemRelationList.size() == 0) {
			return new OfferItemRelationDTO[0];
		}
		return offerItemRelationList.toArray(new OfferItemRelationDTO[0]);
	}
	
	private ParameterizedRowMapper<OfferItemRelationDTO> getVasOfferItemMapper() {
		
		return new ParameterizedRowMapper<OfferItemRelationDTO>() {
			public OfferItemRelationDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				OfferItemRelationDTO offerItemRelation = new OfferItemRelationDTO();
				offerItemRelation.setExistItemId(rs.getString("exist_item_id"));
				offerItemRelation.setAllowCancel(rs.getString("allow_cancel"));
				offerItemRelation.setItemType(rs.getString("item_type"));
				offerItemRelation.setTpPremium(rs.getString("tp_premium"));
				
				String vasOfferId = rs.getString("exist_promotion_offer_id");
				if (StringUtils.isNotEmpty(vasOfferId)) {
					offerItemRelation.addOffer(this.createOffer(vasOfferId, rs.getString("exist_promotion_cd"), LtsConstant.OFFER_TYPE_VAS));
				}	
				String tpOfferId = rs.getString("exist_tp_offer_id");
				if (StringUtils.isNotEmpty(tpOfferId)) {
					offerItemRelation.addOffer(this.createOffer(tpOfferId, rs.getString("exist_tp_cd"), LtsConstant.OFFER_TYPE_TERM_PLAN));
				}
				String autoRenewOfferId = rs.getString("exist_auto_renew_offer_id");
				if (StringUtils.isNotEmpty(autoRenewOfferId)) {
					offerItemRelation.addOffer(this.createOffer(autoRenewOfferId, rs.getString("exist_auto_renew_cd"), LtsConstant.OFFER_TYPE_AUTO_RENEW));
				}
				String crossLobOfferId = rs.getString("exist_cross_lob_offer_id");
				if (StringUtils.isNotEmpty(crossLobOfferId)) {
					offerItemRelation.addOffer(this.createOffer(crossLobOfferId, rs.getString("exist_cross_lob_cd"), LtsConstant.OFFER_TYPE_CROSS_LOB));
				}
				String premiumOfferId = rs.getString("exist_premium_offer_id");
				if (StringUtils.isNotEmpty(premiumOfferId)) {
					offerItemRelation.addOffer(this.createOffer(premiumOfferId, rs.getString("exist_premium_cd"), LtsConstant.OFFER_TYPE_PREMIUM));
				}
			
				return offerItemRelation;
			}
			
			private OfferTypeDTO createOffer(String pOfferId, String pPsefCd, String pOfferType) {
				OfferTypeDTO offer = new OfferTypeDTO();
				offer.setOfferId(pOfferId);
				offer.setOfferType(pOfferType);
				offer.setPsefCd(pPsefCd);
				return offer;
			}
		};
	}

	public OfferItemRelationDTO[] getNoTpItems() throws DAOException {
		
		try {
			return simpleJdbcTemplate.query(SQL_GET_NO_TP_ITEM, this.getTermPlanOfferItemMapper()).toArray(new OfferItemRelationDTO[0]);
		} catch (EmptyResultDataAccessException erdae) {
			return null;
		} catch (Exception e) {
			logger.error("Error in getVasOfferItemMapping()", e);
			throw new DAOException(SQL_GET_NO_TP_ITEM + e.getMessage(), e);
		}
	}

	public String[] getFreeVasItemIds(String pBasketType, String pPromotionExpMonths, String pContractPeriod, String pChannelId) throws DAOException {
		
		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getString("ITEM_ID");
			}
		};
		StringBuilder sqlSb = new StringBuilder(SQL_GET_FREE_VAS);
		
		try {
			if (StringUtils.isEmpty(pPromotionExpMonths)) {
				sqlSb.append(" and promotion_from_exp_mth is null and promotion_to_exp_mth is null");
				return simpleJdbcTemplate.query(sqlSb.toString(), mapper, pBasketType, pChannelId, pContractPeriod).toArray(new String[0]);	
			} else {
				sqlSb.append(" and promotion_from_exp_mth <= ? and promotion_to_exp_mth >= ?");
				return simpleJdbcTemplate.query(sqlSb.toString(), mapper, pBasketType, pChannelId, pContractPeriod, pPromotionExpMonths, pPromotionExpMonths).toArray(new String[0]);
			}
			
		} catch (EmptyResultDataAccessException erdae) {
			return null;
		} catch (Exception e) {
			logger.error("Error in getFreeVasItemIds()", e);
			throw new DAOException(sqlSb.toString() + e.getMessage(), e);
		}
	}
	
	public OfferItemRelationDTO getImsOfferItemMapping(OfferDetailProfileLtsDTO pOfferDtl) throws DAOException {
		
		ParameterizedRowMapper<OfferItemRelationDTO> mapper = new ParameterizedRowMapper<OfferItemRelationDTO>() {
			public OfferItemRelationDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				OfferItemRelationDTO itemRelation = new OfferItemRelationDTO();
				OfferTypeDTO offer = new OfferTypeDTO();
				itemRelation.setExistItemId(rs.getString("ITEM_ID"));
				offer.setOfferCd(rs.getString("OFFER_CD"));
				offer.setOfferType(rs.getString("ITEM_TYPE"));
				itemRelation.setItemType(rs.getString("ITEM_TYPE"));
				itemRelation.setOffers(new OfferTypeDTO[] {offer});
				return itemRelation;
			}
		};		
		try {
			List<OfferItemRelationDTO> itemList = simpleJdbcTemplate.query(SQL_MAP_IMS_OFFER, mapper, pOfferDtl.getOfferSubCd(), pOfferDtl.getExpiredMonths() > 0 ? "Y" : "N");
			
			if (itemList.size() > 0) {
				return itemList.get(0);
			}
			return null;
		} catch (EmptyResultDataAccessException erdae) {
			return null;
		} catch (Exception e) {
			logger.error("Error in getServiceOfferItemMapping()", e);
			throw new DAOException(SQL_MAP_IMS_OFFER + e.getMessage(), e);
		}
	}

	public String[] getOffersByItemId(String pItemId) throws DAOException {
		
		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getString("OFFER_ID");
			}
		};		
		try {
			return simpleJdbcTemplate.query(SQL_GET_OFFERS_BY_ITEM, mapper, pItemId).toArray(new String[0]);
	
		} catch (EmptyResultDataAccessException erdae) {
			return null;
		} catch (Exception e) {
			logger.error("Error in getOffersByItemId()", e);
			throw new DAOException(SQL_GET_OFFERS_BY_ITEM + e.getMessage(), e);
		}
		
	}
}
