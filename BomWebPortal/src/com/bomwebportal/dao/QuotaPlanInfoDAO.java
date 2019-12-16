package com.bomwebportal.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.bomwebportal.dto.MobBuoQuotaDTO;
import com.bomwebportal.dto.QuotaPlanInfoDTO;
import com.bomwebportal.exception.DAOException;

public class QuotaPlanInfoDAO extends BaseDAO {
	protected final Log logger = LogFactory.getLog(getClass());

	public List<QuotaPlanInfoDTO> getQuotaPlanOptions(String itemId, String appDate) throws DAOException {
		logger.debug("getQuotaPlanOptions Called");
		List<QuotaPlanInfoDTO> quotaPlanInfoDTOList = new ArrayList<QuotaPlanInfoDTO>();

		String sql = "select wpba.product_id, wpba.buo_id, wpba.quota_name, wpba.auto_topup_ind, wpba.val_period,wpba.max_count,wpba.supress_local,wpba.SUPRESS_ROAM, wbl.rch_val, wbl.eng_desc, wbl.chi_desc, wbl.rch_amt "
				+ "from w_item_offer_product_assgn wiopa "
				+ "join w_product_buo_assgn wpba on wiopa.product_id = wpba.product_id "
				+ "join w_buo_lkup wbl on wbl.buo_id = wpba.buo_id " + "where wiopa.item_id = :itemId "
				+ "and to_date(:appDate, 'dd/MM/yyyy') between trunc(wpba.start_date) and trunc(nvl(wpba.end_date, to_date(:appDate, 'dd/MM/yyyy'))) "
				+ "and to_date(:appDate, 'dd/MM/yyyy') between trunc(wbl.start_date) and trunc(nvl(wbl.end_date, to_date(:appDate, 'dd/MM/yyyy'))) "
				+ "and wpba.auto_topup_ind in( 'Y','P') ";

		MapSqlParameterSource params = new MapSqlParameterSource();

		params.addValue("itemId", itemId);
		params.addValue("appDate", appDate);

		try {
			quotaPlanInfoDTOList = simpleJdbcTemplate.query(sql, new ParameterizedRowMapper<QuotaPlanInfoDTO>() {

				public QuotaPlanInfoDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
					QuotaPlanInfoDTO quotaPlanInfoDto = new QuotaPlanInfoDTO();
					quotaPlanInfoDto.setProductId(rs.getString("product_id"));
					quotaPlanInfoDto.setBuoId(rs.getString("buo_id"));
					quotaPlanInfoDto.setQuotaName(rs.getString("quota_name"));
					quotaPlanInfoDto.setAutoTopupInd(rs.getString("auto_topup_ind"));
					quotaPlanInfoDto.setValPeriod(rs.getString("val_period"));
					quotaPlanInfoDto.setRchVal(rs.getString("rch_val"));
					quotaPlanInfoDto.setMaxCount(rs.getString("max_count"));
					quotaPlanInfoDto.setSupressLocal(rs.getString("supress_local"));
					quotaPlanInfoDto.setSupressRoam(rs.getString("supress_roam"));
					quotaPlanInfoDto.setEngDesc(rs.getString("eng_desc"));
					quotaPlanInfoDto.setChiDesc(rs.getString("chi_desc"));
					quotaPlanInfoDto.setRchAmt(rs.getString("rch_amt"));
					return quotaPlanInfoDto;
				}

			}, params);
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException",erdae);
			

		} catch (Exception e) {
			logger.error("Exception caught in getQuotaPlanOptions()", e);
			throw new DAOException(e.getMessage(), e);
		}
		return quotaPlanInfoDTOList;

	}
	
	public void insertBomWebBuoQuota(MobBuoQuotaDTO mobBuoQuotaDto,String orderId) throws DAOException {
		logger.debug("Insert quotaPlanInfo is called");
		final String sql = "insert into bomweb_buo_quota (order_id,item_id,auto_topup_ind,max_cnt_auto_topup,buo_id,create_by,create_date,last_upd_by,last_upd_date)"
				+ "values(:orderId,:itemId,:autoTopUpInd,:maxCntAutoTopUp,:buoId,:createBy,sysDate,:lastUpdBy,sysDate)";
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("orderId",orderId);
		params.addValue("itemId", mobBuoQuotaDto.getItemId());
		params.addValue("autoTopUpInd", mobBuoQuotaDto.getAutoTopUpInd());
		params.addValue("maxCntAutoTopUp",mobBuoQuotaDto.getMaxCntAutoTopUp());
		params.addValue("buoId", mobBuoQuotaDto.getBuoId());
		params.addValue("createBy", mobBuoQuotaDto.getCreateBy());
		params.addValue("lastUpdBy", mobBuoQuotaDto.getLastUpdBy());
		

		try {
			simpleJdbcTemplate.update(sql, params);
		} catch (Exception e) {
			logger.error("Exception caught in insertBomwebBuoQuota()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public void deleteBomWebBuoQuota(String orderId) throws DAOException {
		logger.debug("Insert quotaPlanInfo is called");
		final String sql = "delete from bomweb_buo_quota where order_id = :orderId";
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("orderId", orderId);
		

		try {
			simpleJdbcTemplate.update(sql, params);
		} catch (Exception e) {
			logger.error("Exception caught in insert to BomwebBuoQuota()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public List<MobBuoQuotaDTO> getBomWebBuoQuota(String orderId) throws DAOException{
		final String sql = "select bbq.order_id, bbq.item_id,bbq.AUTO_TOPUP_IND,bbq.MAX_CNT_AUTO_TOPUP,bbq.BUO_ID,wbl.ENG_DESC from bomweb_buo_quota bbq left join w_buo_lkup wbl on bbq.buo_id = wbl.buo_id  where order_id = :orderId";
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("orderId", orderId );
		
		ParameterizedRowMapper<MobBuoQuotaDTO> mapper = new ParameterizedRowMapper<MobBuoQuotaDTO>() {
			public MobBuoQuotaDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				MobBuoQuotaDTO dto = new MobBuoQuotaDTO();
				dto.setOrderId(rs.getString("order_id"));
				dto.setItemId(rs.getString("item_id"));
				dto.setAutoTopUpInd(rs.getString("AUTO_TOPUP_IND"));
				dto.setMaxCntAutoTopUp(rs.getString("MAX_CNT_AUTO_TOPUP"));
				dto.setBuoId(rs.getString("buo_id"));
				dto.setEngDesc(rs.getString("eng_Desc"));
				return dto;
			}
		};
		try {
			return simpleJdbcTemplate.query(sql,mapper, params);
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException",erdae);
			

		} catch (Exception e) {
			logger.error("Exception caught in getQuotaPlanOptions()", e);
			throw new DAOException(e.getMessage(), e);
		}
		return null;
	}
}
