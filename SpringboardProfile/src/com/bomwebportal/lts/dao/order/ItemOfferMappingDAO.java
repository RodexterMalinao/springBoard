package com.bomwebportal.lts.dao.order;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.bomwebportal.dao.BaseDAO;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.lts.dto.order.OfferItemRelationDTO;
import com.bomwebportal.lts.dto.order.OfferTypeDTO;
import com.bomwebportal.lts.dto.profile.OfferDetailProfileLtsDTO;
import com.bomwebportal.lts.util.LtsProfileConstant;

public class ItemOfferMappingDAO extends BaseDAO {

	private static final String SQL_MAP_SERVICE_ITEM = 
			"select exist_tp_offer_id, exist_tp_cd, exist_auto_renew_offer_id, exist_auto_renew_cd, exist_cross_lob_offer_id, exist_cross_lob_cd, " +
			"exist_premium_offer_id, exist_premium_cd, exist_promotion_offer_id, exist_promotion_cd, change_to_offer_id, change_to_cd, term_exist_cd_ind, " +
			"exist_item_id, change_to_item_id, idd_free_min " +
			"from W_LTS_VAS_MAPPING " +
			"where exist_promotion_offer_id in (:offerList) and exist_tp_offer_id is null and eff_start_date <= sysdate and (eff_end_date is null or eff_end_date > sysdate) ";
	
	private static final String SQL_MAP_TP_ITEM = 
			"select * from ( select * from ( " +
			"select exist_tp_offer_id, exist_tp_cd, exist_cross_lob_offer_id, exist_cross_lob_cd, exist_premium_offer_id, exist_premium_cd, " +
			"exist_promotion_offer_id, exist_promotion_cd, change_to_offer_id, change_to_cd, term_exist_cd_ind, " +
			"exist_item_id, change_to_item_id, idd_free_min, " +
			"(decode(exist_auto_renew_offer_id,NULL,0,1) + decode(exist_cross_lob_offer_id,NULL,0,1) + decode(exist_premium_offer_id,NULL,0,1) + " +
			"decode(exist_promotion_offer_id,NULL,0,1) + decode(exist_tp_offer_id,NULL,0,1)) weight " +
			"from W_LTS_VAS_MAPPING " +
			"where exist_tp_offer_id in (:offerList) " +
			"and (exist_cross_lob_offer_id in (:offerList) or exist_cross_lob_offer_id is null) " +
			"and (exist_premium_offer_id in (:offerList) or exist_premium_offer_id is null) ";
			
	private static final String SQL_GET_IDD_FREE_MIN = 
			"select distinct idd_free_min from w_lts_vas_mapping " +
			" where exist_tp_offer_id = :offerId " +
			" and idd_free_min <> '0' " +
			" and rownum = 1";
	
	public OfferItemRelationDTO getTeamPlanOfferItemMapping(OfferDetailProfileLtsDTO[] pOfferDtls, boolean pExpiredTp) throws DAOException {
		
		List<OfferItemRelationDTO> offerItemRelationList = null;
		List<String> offerCdList = this.generateOfferIdList(pOfferDtls);
		
		try {
			StringBuilder sql = new StringBuilder();
			sql.append(SQL_MAP_TP_ITEM);
			
			if (pExpiredTp) {
				sql.append(" and exist_promotion_offer_id is null ");				
			}
			else {
				sql.append(" and (exist_promotion_offer_id in (:offerList) or exist_promotion_offer_id is null) ");
			}
			
			sql.append(" and eff_start_date <= sysdate and (eff_end_date is null or eff_end_date > sysdate)) order by weight desc) where rownum < 2");
			offerItemRelationList = simpleJdbcTemplate.query(sql.toString(), this.getTermPlanOfferItemMapper(), Collections.singletonMap("offerList", offerCdList));
		} catch (EmptyResultDataAccessException erdae) {
			return null;
		} catch (Exception e) {
			logger.error("Error in getTeamPlanOfferItemMapping()", e);
			throw new DAOException(SQL_MAP_TP_ITEM + e.getMessage(), e);
		}
		if (offerItemRelationList.size() == 0) {
			return null;
		}
		return offerItemRelationList.get(0);
	}
	
	public OfferItemRelationDTO getServiceOfferItemMapping(OfferDetailProfileLtsDTO[] pOfferDtls, boolean pExpiredTp) throws DAOException {
		
		List<OfferItemRelationDTO> offerItemRelationList = null;
		List<String> offerCdList = this.generateOfferIdList(pOfferDtls);

		try {
			StringBuilder sql = new StringBuilder();
			sql.append(SQL_MAP_SERVICE_ITEM);
			
			if (pExpiredTp) {
				sql.append(" and exist_promotion_offer_id is null ");				
			}
			else {
				sql.append(" and (exist_promotion_offer_id in (:offerList) or exist_promotion_offer_id is null) ");
			}
			
			offerItemRelationList = simpleJdbcTemplate.query(sql.toString(), this.getTermPlanOfferItemMapper(), Collections.singletonMap("offerList", offerCdList));
		} catch (EmptyResultDataAccessException erdae) {
			return null;
		} catch (Exception e) {
			logger.error("Error in getServiceOfferItemMapping()", e);
			throw new DAOException(SQL_MAP_SERVICE_ITEM + e.getMessage(), e);
		}
		if (offerItemRelationList.size() == 0) {
			return null;
		}
		return offerItemRelationList.get(0);
	}
	
	protected ParameterizedRowMapper<OfferItemRelationDTO> getTermPlanOfferItemMapper() {
		
		return new ParameterizedRowMapper<OfferItemRelationDTO>() {
			public OfferItemRelationDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				
				OfferItemRelationDTO offerItemRelation = new OfferItemRelationDTO();
				offerItemRelation.setFreeVasInd(StringUtils.isNotEmpty(rs.getString("change_to_cd")));				
				offerItemRelation.setExistItemId(rs.getString("exist_item_id"));
				offerItemRelation.setIddFreeMin(rs.getString("idd_free_min"));
				offerItemRelation.setTermExistCdInd(rs.getString("term_exist_cd_ind"));
				
				String tpOfferId = rs.getString("exist_tp_offer_id");
				if (StringUtils.isNotEmpty(tpOfferId)) {
					offerItemRelation.addOffer(this.createOffer(tpOfferId, rs.getString("exist_tp_cd"), LtsProfileConstant.OFFER_TYPE_TERM_PLAN));
				}
				String crossLobOfferId = rs.getString("exist_cross_lob_offer_id");
				if (StringUtils.isNotEmpty(crossLobOfferId)) {
					offerItemRelation.addOffer(this.createOffer(crossLobOfferId, rs.getString("exist_cross_lob_cd"), LtsProfileConstant.OFFER_TYPE_CROSS_LOB));
				}
				String premiumOfferId = rs.getString("exist_premium_offer_id");
				if (StringUtils.isNotEmpty(premiumOfferId)) {
					offerItemRelation.addOffer(this.createOffer(premiumOfferId, rs.getString("exist_premium_cd"), LtsProfileConstant.OFFER_TYPE_PREMIUM));
				}
				String promotionOfferId = rs.getString("exist_promotion_offer_id");
				if (StringUtils.isNotEmpty(promotionOfferId)) {
					offerItemRelation.addOffer(this.createOffer(promotionOfferId, rs.getString("exist_promotion_cd"), LtsProfileConstant.OFFER_TYPE_PROMOTION));
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
	
	protected List<String> generateOfferIdList(OfferDetailProfileLtsDTO[] pOfferDtls) {
		
		List<String> offerIdList = new ArrayList<String>();
		
		for (int i=0; pOfferDtls!=null && i<pOfferDtls.length; ++i) {
			offerIdList.add(pOfferDtls[i].getOfferId());
		}
		return offerIdList;
	}
	
	public String getIddFreeMin(String existTpOfferId) throws DAOException {
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue("offerId", existTpOfferId);
		
		try {
			return (String)simpleJdbcTemplate.getNamedParameterJdbcOperations().queryForObject(SQL_GET_IDD_FREE_MIN, paramSource, String.class);
		} catch (EmptyResultDataAccessException erdae) {
			return null;
		} catch (Exception e) {
			logger.error("Exception caught in getIddFreeMin():", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
}
