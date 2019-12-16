package com.bomwebportal.mob.cos.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import oracle.jdbc.OracleTypes;

import org.apache.axis.utils.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.SqlInOutParameter;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.util.CollectionUtils;

import com.bomwebportal.dao.BaseDAO;
import com.bomwebportal.dto.BasketDTO;
import com.bomwebportal.dto.BasketQuotaDTO;
import com.bomwebportal.dto.VasDetailDTO;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.mob.ccs.dto.CodeLkupDTO;
import com.bomwebportal.mob.cos.dto.MobCosCampaignDtlDTO;
import com.bomwebportal.mob.cos.dto.MobCosCampaignHdrDTO;
import com.bomwebportal.mob.cos.dto.MobCosCampaignVasDTO;

public class MobCosCampaignDAO extends BaseDAO {
	protected final Log logger = LogFactory.getLog(getClass());
	
	/****** Campaign Page (header) ******/
	public List<MobCosCampaignHdrDTO> getCampaignTitle (String campTitle, String campName, String hsDesc) throws DAOException {
		List<MobCosCampaignHdrDTO> campList = new ArrayList<MobCosCampaignHdrDTO>();
		String sql = "select * from w_ret_campaign_hdr "
				+ "where upper(campaign_title) like :campTitle "
				+ "and upper(campaign_name) like :campName "
				+ "and ((upper(NVL(handset_desc, 'SIM')) LIKE :hsDesc "
				+ "and upper(NVL(handset_desc, 'SIM')) <> 'SIM') "
				+ "or (upper(NVL(handset_desc, 'SIM')) = DECODE(LENGTH(:hsDesc), 2, 'SIM', '') "
				+ "and upper(NVL(handset_desc, 'SIM')) = 'SIM'))";		
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("campTitle", "%" + campTitle.trim().toUpperCase() + "%");
		params.addValue("campName", "%" + campName.trim().toUpperCase() + "%");
		params.addValue("hsDesc", "%" + hsDesc.trim().toUpperCase() + "%");
		ParameterizedRowMapper<MobCosCampaignHdrDTO> mapper = new ParameterizedRowMapper<MobCosCampaignHdrDTO>() {
			public MobCosCampaignHdrDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				MobCosCampaignHdrDTO camp = new MobCosCampaignHdrDTO();
				camp.setCampaignId(rs.getString("campaign_id"));
				camp.setCampaignTitle(rs.getString("campaign_title"));
				camp.setCampaignName(rs.getString("campaign_name"));
				return camp;
			}
		};
		
		try {
			campList = simpleJdbcTemplate.query(sql, mapper, params);
		} catch (DataAccessException e) {
			logger.info("Exception caught in getCampaignTitle():", e);
		} catch (Exception e) {
			logger.info("Exception caught in getCampaignTitle():", e);
			throw new DAOException(e.getMessage(), e);
		}
		return campList;
	}
	
	public List<MobCosCampaignHdrDTO> getResultOption(String campId) throws DAOException {
		List<MobCosCampaignHdrDTO> campList = new ArrayList<MobCosCampaignHdrDTO>();
		String sql = "SELECT Campaign_Title , " +
				"  Campaign_Name " +
				"FROM W_RET_CAMPAIGN_HDR " +
				"WHERE Campaign_Id = :cpgId";
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("cpgId", campId);
		
		ParameterizedRowMapper<MobCosCampaignHdrDTO> mapper = new ParameterizedRowMapper<MobCosCampaignHdrDTO>() {
			public MobCosCampaignHdrDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				MobCosCampaignHdrDTO camp = new MobCosCampaignHdrDTO();
				camp.setCampaignTitle(rs.getString("campaign_title"));
				camp.setCampaignName(rs.getString("campaign_name"));
				return camp;
			}
		};
		
		try {
			campList = simpleJdbcTemplate.query(sql, mapper, params);
		} catch (DataAccessException e) {
			logger.info("Exception caught in getResultOption():", e);
		} catch (Exception e) {
			logger.info("Exception caught in getResultOption():", e);
			throw new DAOException(e.getMessage(), e);
		}
		return campList;
	}
	
	public MobCosCampaignHdrDTO getCpgHdr(String cpgId) throws DAOException {
		logger.info("getCpgHdr() @ MobCosCampaignDAO is called");
		List<MobCosCampaignHdrDTO> resultList = new ArrayList<MobCosCampaignHdrDTO> ();
		String sql = "SELECT Campaign_Id , " +
				"  Campaign_Title , " +
				"  Campaign_Name , " +
				"  Handset_Desc , " +
				"  Eff_Start_Date , " +
				"  Eff_End_Date " +
				"FROM W_RET_CAMPAIGN_HDR " +
				"WHERE Campaign_Id = :campId";
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("campId", cpgId);
		
		ParameterizedRowMapper<MobCosCampaignHdrDTO> mapper = new ParameterizedRowMapper<MobCosCampaignHdrDTO>() {
			public MobCosCampaignHdrDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				MobCosCampaignHdrDTO dto = new MobCosCampaignHdrDTO();
				dto.setCampaignId(rs.getString("Campaign_Id"));
				dto.setCampaignTitle(rs.getString("Campaign_Title"));
				dto.setCampaignName(rs.getString("Campaign_Name"));
				dto.setHandsetDesc(rs.getString("Handset_Desc"));
				DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
				Date tmpStartDate = rs.getDate("Eff_Start_Date");
				Date tmpEndDate = rs.getDate("Eff_End_Date");
				dto.setEffStartDate(tmpStartDate == null ? null : df.format(tmpStartDate));
				dto.setEffEndDate(tmpEndDate==null ? null : df.format(tmpEndDate));
				return dto;
			}
		};
		
		try {
			resultList = simpleJdbcTemplate.query(sql, mapper,params);
		} catch (EmptyResultDataAccessException erdae) {
			System.out.println("EmptyResultDataAccessException");
			resultList = null;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception caught in getCpgHdr()", e);
			throw new DAOException(e.getMessage(), e);
		}
		return CollectionUtils.isEmpty(resultList) ? null : resultList.get(0);
	}
	
	public int createCpgHdr(String cpgTitle, String cpgName, String cpgHS, String userId) throws DAOException {
		logger.info("createCpgHdr @ MobCosCampaignDAO is called");
		try {
			SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
					.withSchemaName("OPS$CNM")
					.withCatalogName("PKG_SB_MOB_RET_CPG")
					.withProcedureName("CREATE_CPG_HDR");
			jdbcCall.setAccessCallParameterMetaData(false);
			jdbcCall.declareParameters(new SqlParameter("i_campaign_title",
					Types.VARCHAR), new SqlParameter("i_campaign_name",
					Types.VARCHAR), new SqlParameter("i_handset_desc",
					Types.VARCHAR), new SqlParameter("i_eff_start_date",
					Types.DATE), new SqlParameter("i_eff_end_date",
					Types.DATE), new SqlParameter("i_user_id",
					Types.VARCHAR), new SqlOutParameter("o_campaign_id",
					Types.INTEGER), new SqlOutParameter("gnRetVal",
					Types.INTEGER), new SqlOutParameter("gnErrCode",
					Types.INTEGER), new SqlOutParameter("gsErrText",
					Types.VARCHAR));
			
			logger.info("cpgTitle:" + cpgTitle);
			logger.info("cpgName:" + cpgName);
			logger.info("cpgHS:" + cpgHS);
			logger.info("userId:" + userId);
			
			MapSqlParameterSource inMap = new MapSqlParameterSource();
			inMap.addValue("i_campaign_title", cpgTitle);
			inMap.addValue("i_campaign_name", cpgName);
			inMap.addValue("i_handset_desc", cpgHS);
			inMap.addValue("i_eff_start_date", null);
			inMap.addValue("i_eff_end_date", null);
			inMap.addValue("i_user_id", userId);
			SqlParameterSource in = inMap;

			Map<String, Object> out = jdbcCall.execute(in);
			int cpgId = 0;
			int retVal = -10;
			int error_code = -10;
			String error_text = null;
			
			if (((Integer) out.get("gnRetVal")) != null) {
				retVal = ((Integer) out.get("gnRetVal")).intValue();
				logger.info("PKG_SB_MOB_RET_CPG.CREATE_CPG_HDR() output gnRetVal = " +  retVal);
			}
			if((Integer) out.get("gnErrCode") != null) {
				error_code = ((Integer) out.get("gnErrCode")).intValue();
				logger.info("PKG_SB_MOB_RET_CPG.CREATE_CPG_HDR() output gnErrCode = " + error_code);
				
			} 
			if((String) out.get("gsErrText") != null) {
				error_text = out.get("gsErrText").toString();
				logger.info("PKG_SB_MOB_RET_CPG.CREATE_CPG_HDR() output gsErrText = " + error_text);
			}
			if (((Integer) out.get("o_campaign_id")) != null) {
				cpgId = ((Integer) out.get("o_campaign_id")).intValue();
				logger.info("PKG_SB_MOB_RET_CPG.CREATE_CPG_HDR() output o_campaign_id = " +  cpgId);
			}
			return cpgId;
		} catch (Exception e) {
			logger.error("Exception caught in createCpgHdr()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public boolean createCpgChannelAssgn(String channelId, String cpgId, String customerTier, String userId) throws DAOException {
		logger.info("createCpgChannelAssgn @ MobCosCampaignDAO is called");
		try {
			SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
					.withSchemaName("OPS$CNM")
					.withCatalogName("PKG_SB_MOB_RET_CPG")
					.withProcedureName("NEW_CHANNEL_CPG");
			jdbcCall.setAccessCallParameterMetaData(false);
			jdbcCall.declareParameters(new SqlParameter("i_channel_id",
					Types.INTEGER), new SqlParameter("i_campaign_id",
					Types.INTEGER), new SqlParameter("i_customer_tier",
					Types.INTEGER), new SqlParameter("i_action",
					Types.VARCHAR), new SqlParameter("i_user_id",
					Types.VARCHAR), new SqlOutParameter("gnRetVal",
					Types.INTEGER), new SqlOutParameter("gnErrCode",
					Types.INTEGER), new SqlOutParameter("gsErrText",
					Types.VARCHAR));

			MapSqlParameterSource inMap = new MapSqlParameterSource();
			inMap.addValue("i_channel_id", channelId);
			inMap.addValue("i_campaign_id", cpgId);
			inMap.addValue("i_customer_tier", customerTier);
			inMap.addValue("i_action", "Y");
			inMap.addValue("i_user_id", userId);
			SqlParameterSource in = inMap;

			Map<String, Object> out = jdbcCall.execute(in);
			int retVal = -10;
			int error_code = -10;
			String error_text = null;
			
			if (((Integer) out.get("gnRetVal")) != null) {
				retVal = ((Integer) out.get("gnRetVal")).intValue();
				logger.info("PKG_SB_MOB_RET_CPG.NEW_CHANNEL_CPG() output gnRetVal = " +  retVal);
			}
			if((Integer) out.get("gnErrCode") != null) {
				error_code = ((Integer) out.get("gnErrCode")).intValue();
				logger.info("PKG_SB_MOB_RET_CPG.NEW_CHANNEL_CPG() output gnErrCode = " + error_code);
				
			} 
			if((String) out.get("gsErrText") != null) {
				error_text = out.get("gsErrText").toString();
				logger.info("PKG_SB_MOB_RET_CPG.NEW_CHANNEL_CPG() output gsErrText = " + error_text);
			}
			return (error_code >= 0);
		} catch (Exception e) {
			logger.error("Exception caught in createCpgChannelAssgn()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	/****** Campaign Page (detail) ******/
	public List<MobCosCampaignDtlDTO> getCpgDtl(String cpgId) throws DAOException {
		logger.info("getCpgDtl() @ MobCosCampaignDAO is called");
		List<MobCosCampaignDtlDTO> resultList = new ArrayList<MobCosCampaignDtlDTO> ();
		
		String sql = "SELECT DTL.CAMPAIGN_ID, " +
				"  DTL.BASKET_ID, " +
				"  DTL.BASKET_DESC, " +
				"  DTL.SOURCE_BASKET_ID, " +
				"  DTL.CAMPAIGN_BASKET_SEQ, " +
				"  DTL.TIER, " +
				"  EFF_START_DATE, " +
				"  EFF_END_DATE, " +
				"  NVL(DTL.ACTIVE_IND, 'N') ACTIVE_IND, " +
				"  MV.RP_OFFER_CD, " +
				"  MV.BP_OFFER_CD, " +
				"  MV.MCO_PROD_CD, " +
				"  MV.HS_POS_ITEM_CD " +
				"FROM W_RET_CAMPAIGN_DTL DTL " +
				"LEFT JOIN W_BASKET_ATTRIBUTE_MV MV " +
				"ON DTL.SOURCE_BASKET_ID = MV.BASKET_ID " +
				"WHERE DTL.CAMPAIGN_ID   = :campId " +
				"ORDER BY decode(ACTIVE_IND, 'Y', 1, 2), nvl(DTL.EFF_END_DATE, sysdate) desc, DTL.TIER,  " +
				"  DTL.CAMPAIGN_BASKET_SEQ";
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("campId", cpgId);
		
		ParameterizedRowMapper<MobCosCampaignDtlDTO> mapper = new ParameterizedRowMapper<MobCosCampaignDtlDTO>() {
			public MobCosCampaignDtlDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				MobCosCampaignDtlDTO dto = new MobCosCampaignDtlDTO();
				dto.setCampaignId(rs.getString("CAMPAIGN_ID"));
				dto.setBasketId(rs.getString("BASKET_ID"));
				dto.setBasketDesc(rs.getString("BASKET_DESC"));
				dto.setSourceBasketId(rs.getString("SOURCE_BASKET_ID"));
				dto.setCampaignBasketSeq(rs.getString("CAMPAIGN_BASKET_SEQ"));
				dto.setTier(rs.getString("TIER"));
				Date tmpStartDate = rs.getDate("EFF_START_DATE");
				Date tmpEndDate = rs.getDate("EFF_END_DATE");
				dto.setActiveInd(rs.getString("ACTIVE_IND"));
				dto.setRatePlan(rs.getString("RP_OFFER_CD"));
				dto.setBundle(rs.getString("BP_OFFER_CD"));
				dto.setContract(rs.getString("MCO_PROD_CD"));
				dto.setHandset(rs.getString("HS_POS_ITEM_CD"));
				
				DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
				dto.setEffStartDate(tmpStartDate == null ? null : df.format(tmpStartDate));
				dto.setEffEndDate(tmpEndDate == null ? null : df.format(tmpEndDate));
				return dto;
			}
		};
		
		try {
			resultList = simpleJdbcTemplate.query(sql, mapper,params);
		} catch (EmptyResultDataAccessException erdae) {
			resultList = null;
		}
		catch (Exception e) {
			logger.error("Exception caught in getCpgDtl()", e);
			throw new DAOException(e.getMessage(), e);
		}
		return CollectionUtils.isEmpty(resultList) ? null : resultList;
	}

	public List<CodeLkupDTO> getTierOption() throws DAOException {
		logger.info("getTierOption @ MobCosCampaignDAO is called");
		String sql = "SELECT code_type, " +
				"  code_id, " +
				"  code_desc " +
				"FROM bomweb_code_lkup " +
				"WHERE code_type = 'MOB_RET_TIER_LVL'";
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		
		ParameterizedRowMapper<CodeLkupDTO> mapper = new ParameterizedRowMapper<CodeLkupDTO>() {
			public CodeLkupDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				CodeLkupDTO dto = new CodeLkupDTO();
				dto.setCodeType(rs.getString("code_type"));
				dto.setCodeId(rs.getString("code_id"));
				dto.setCodeDesc(rs.getString("code_desc"));
				return dto;
			}
		};
		
		try {
			return simpleJdbcTemplate.query(sql, mapper, params);
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException in getTierOption()");
		} catch (Exception e) {
			logger.info("Exception caught in getTierOption():", e);
			throw new DAOException(e.getMessage(), e);
		}
		return null;
	}
	
	public String genBasketId(String cpgId, String basketSeq, String basketId, String basketDesc, String userId) throws DAOException {
		logger.info("genBasketId @ MobCosCampaignDAO is called");
		try {
			SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
					.withSchemaName("OPS$CNM")
					.withCatalogName("PKG_SB_MOB_RET_CPG")
					.withProcedureName("GENERATE_CPG_BASKET");
			jdbcCall.setAccessCallParameterMetaData(false);
			jdbcCall.declareParameters(new SqlParameter("i_campaign_id",
					Types.INTEGER), new SqlParameter("i_campaign_basket_seq",
					Types.INTEGER), new SqlParameter("i_basket_id",
					Types.INTEGER), new SqlParameter("i_basket_desc",
					Types.VARCHAR), new SqlParameter("i_user_id",
					Types.VARCHAR), new SqlOutParameter("o_basket_id",
					Types.INTEGER), new SqlOutParameter("gnRetVal",
					Types.INTEGER), new SqlOutParameter("gnErrCode",
					Types.INTEGER), new SqlOutParameter("gsErrText",
					Types.VARCHAR));

			MapSqlParameterSource inMap = new MapSqlParameterSource();
			inMap.addValue("i_campaign_id", cpgId);
			inMap.addValue("i_campaign_basket_seq", basketSeq);
			inMap.addValue("i_basket_id", basketId);
			inMap.addValue("i_basket_desc", basketDesc);
			inMap.addValue("i_user_id", userId);
			SqlParameterSource in = inMap;

			Map<String, Object> out = jdbcCall.execute(in);
			int genBasketId = 0;
			int retVal = -10;
			int error_code = -10;
			String error_text = null;
			
			if (((Integer) out.get("gnRetVal")) != null) {
				retVal = ((Integer) out.get("gnRetVal")).intValue();
				logger.info("PKG_SB_MOB_RET_CPG.GENERATE_CPG_BASKET() output gnRetVal = " +  retVal);
			}
			if((Integer) out.get("gnErrCode") != null) {
				error_code = ((Integer) out.get("gnErrCode")).intValue();
				logger.info("PKG_SB_MOB_RET_CPG.GENERATE_CPG_BASKET() output gnErrCode = " + error_code);
				
			} 
			if((String) out.get("gsErrText") != null) {
				error_text = out.get("gsErrText").toString();
				logger.info("PKG_SB_MOB_RET_CPG.GENERATE_CPG_BASKET() output gsErrText = " + error_text);
			}
			if (((Integer) out.get("o_basket_id")) != null) {
				genBasketId = ((Integer) out.get("o_basket_id")).intValue();
				logger.info("PKG_SB_MOB_RET_CPG.GENERATE_CPG_BASKET() output o_basket_id = " +  genBasketId);
			}
			return String.valueOf(genBasketId);
		} catch (Exception e) {
			logger.error("Exception caught in genBasketId()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	/****** Add Campaign Basket Page ******/
	
	public boolean activeCpgBasket(String cpgId, String basketId, String userId) throws DAOException {
		logger.info("activeCpgBasket @ MobCosCampaignDAO is called");
		try {
			SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
					.withSchemaName("OPS$CNM")
					.withCatalogName("PKG_SB_MOB_RET_CPG")
					.withProcedureName("ACTIVATE_CPG_BASKET");
			jdbcCall.setAccessCallParameterMetaData(false);
			jdbcCall.declareParameters(new SqlParameter("i_campaign_id",
					Types.INTEGER), new SqlParameter("i_basket_id",
					Types.INTEGER), new SqlParameter("i_user_id",
					Types.VARCHAR), new SqlOutParameter("gnRetVal",
					Types.INTEGER), new SqlOutParameter("gnErrCode",
					Types.INTEGER), new SqlOutParameter("gsErrText",
					Types.VARCHAR));

			MapSqlParameterSource inMap = new MapSqlParameterSource();
			inMap.addValue("i_campaign_id", cpgId);
			inMap.addValue("i_basket_id", basketId);
			inMap.addValue("i_user_id", userId);
			SqlParameterSource in = inMap;

			Map<String, Object> out = jdbcCall.execute(in);
			int retVal = -10;
			int error_code = -10;
			String error_text = null;
			
			if (((Integer) out.get("gnRetVal")) != null) {
				retVal = ((Integer) out.get("gnRetVal")).intValue();
				logger.info("PKG_SB_MOB_RET_CPG.ACTIVATE_CPG_BASKET() output gnRetVal = " +  retVal);
			}
			if((Integer) out.get("gnErrCode") != null) {
				error_code = ((Integer) out.get("gnErrCode")).intValue();
				logger.info("PKG_SB_MOB_RET_CPG.ACTIVATE_CPG_BASKET() output gnErrCode = " + error_code);
				
			} 
			if((String) out.get("gsErrText") != null) {
				error_text = out.get("gsErrText").toString();
				logger.info("PKG_SB_MOB_RET_CPG.ACTIVATE_CPG_BASKET() output gsErrText = " + error_text);
			}
			return (error_code >= 0);
		} catch (Exception e) {
			logger.error("Exception caught in activeCpgBasket()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public boolean deactiveCpgBasket(String cpgId, String basketId, String userId) throws DAOException {
		logger.info("deactiveCpgBasket @ MobCosCampaignDAO is called");
		try {
			SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
					.withSchemaName("OPS$CNM")
					.withCatalogName("PKG_SB_MOB_RET_CPG")
					.withProcedureName("DEACTIVATE_CPG_BASKET");
			jdbcCall.setAccessCallParameterMetaData(false);
			jdbcCall.declareParameters(new SqlParameter("i_campaign_id",
					Types.INTEGER), new SqlParameter("i_basket_id",
					Types.INTEGER), new SqlParameter("i_user_id",
					Types.VARCHAR), new SqlOutParameter("gnRetVal",
					Types.INTEGER), new SqlOutParameter("gnErrCode",
					Types.INTEGER), new SqlOutParameter("gsErrText",
					Types.VARCHAR));

			MapSqlParameterSource inMap = new MapSqlParameterSource();
			inMap.addValue("i_campaign_id", cpgId);
			inMap.addValue("i_basket_id", basketId);
			inMap.addValue("i_user_id", userId);
			SqlParameterSource in = inMap;

			Map<String, Object> out = jdbcCall.execute(in);
			int retVal = -10;
			int error_code = -10;
			String error_text = null;
			
			if (((Integer) out.get("gnRetVal")) != null) {
				retVal = ((Integer) out.get("gnRetVal")).intValue();
				logger.info("PKG_SB_MOB_RET_CPG.DEACTIVATE_CPG_BASKET() output gnRetVal = " +  retVal);
			}
			if((Integer) out.get("gnErrCode") != null) {
				error_code = ((Integer) out.get("gnErrCode")).intValue();
				logger.info("PKG_SB_MOB_RET_CPG.DEACTIVATE_CPG_BASKET() output gnErrCode = " + error_code);
				
			} 
			if((String) out.get("gsErrText") != null) {
				error_text = out.get("gsErrText").toString();
				logger.info("PKG_SB_MOB_RET_CPG.DEACTIVATE_CPG_BASKET() output gsErrText = " + error_text);
			}
			return (error_code >= 0);
		} catch (Exception e) {
			logger.error("Exception caught in deactiveCpgBasket()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	/****** Edit Campaign VAS Page******/
	public List<MobCosCampaignVasDTO> getCpgVasList() throws DAOException {
		logger.info("getCpgVasList() @ MobCosCampaignDAO is called");
		
		try {
			SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
					.withSchemaName("OPS$CNM")
					.withCatalogName("PKG_SB_MOB_RET_CPG")
					.withProcedureName("GET_CAMPAIGN_VAS_LIST")
					.returningResultSet("mycursor", 
							new ParameterizedRowMapper<MobCosCampaignVasDTO>() {
								public MobCosCampaignVasDTO mapRow(ResultSet rs, int i) throws SQLException{ 
									MobCosCampaignVasDTO dto = new MobCosCampaignVasDTO();
									dto.setItemId(rs.getString(1));
									dto.setOfferCd(rs.getString(3));
									dto.setOfferDesc(rs.getString(4));
									dto.setProdCd(rs.getString(5));
									dto.setProdDesc(rs.getString(6));
									return dto;
								}
							});
			jdbcCall.setAccessCallParameterMetaData(false);
			jdbcCall.declareParameters(new SqlInOutParameter("mycursor",
					OracleTypes.CURSOR), new SqlOutParameter("gnRetVal",
					Types.INTEGER), new SqlOutParameter("gnErrCode",
					Types.INTEGER), new SqlOutParameter("gsErrText",
					Types.VARCHAR));

			SqlParameterSource in = new MapSqlParameterSource();

			Map<String, Object> out = jdbcCall.execute(in);
			int retVal = -10;
			int error_code = -10;
			String error_text = null;
			
			if (((Integer) out.get("gnRetVal")) != null) {
				retVal = ((Integer) out.get("gnRetVal")).intValue();
				logger.info("PKG_SB_MOB_RET_CPG.GET_CAMPAIGN_VAS_LIST() output gnRetVal = " +  retVal);
			}
			if((Integer) out.get("gnErrCode") != null) {
				error_code = ((Integer) out.get("gnErrCode")).intValue();
				logger.info("PKG_SB_MOB_RET_CPG.GET_CAMPAIGN_VAS_LIST() output gnErrCode = " + error_code);
				
			} 
			if((String) out.get("gsErrText") != null) {
				error_text = out.get("gsErrText").toString();
				logger.info("PKG_SB_MOB_RET_CPG.GET_CAMPAIGN_VAS_LIST() output gsErrText = " + error_text);
			}
			if(!CollectionUtils.isEmpty((List<MobCosCampaignVasDTO>) out.get("mycursor"))) {
				return (List<MobCosCampaignVasDTO>) out.get("mycursor");
			} else {
				return new ArrayList<MobCosCampaignVasDTO> ();
			}
		} catch (Exception e) {
			logger.error("Exception caught in getCpgVasList()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}

	public String getDisplayVasList(String itemId, String locale) throws DAOException {
		logger.info("getDisplayVasList() @ MobCosCampaignDAO is called");
		List<String> resultList = new ArrayList<String>();
		
		String sql = "SELECT IDL.HTML, " +
				"  I.DESCRIPTION " +
				"FROM W_ITEM_DISPLAY_LKUP IDL, " +
				"  W_ITEM I " +
				"WHERE I.ID             = IDL.ITEM_ID(+) " +
				"AND ((IDL.DISPLAY_TYPE = 'SS_FORM_VAS' " +
				"AND IDL.ITEM_ID        = :itemId " +
				"AND IDL.LOCALE         = :locale) " +
				"OR (I.ID               = :itemId " +
				"AND IDL.HTML          IS NULL))";
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("itemId", itemId);
		params.addValue("locale", locale);
		
		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
			public String mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				String html = rs.getString("html");
				String result = StringUtils.isEmpty(html) ? 
						rs.getString("description") : html;
				return result;
			}
		};
		
		try {
			resultList = simpleJdbcTemplate.query(sql, mapper, params);
		} catch (DataAccessException e) {
			logger.info("Exception caught in getDisplayVasList():", e);
		} catch (Exception e) {
			logger.info("Exception caught in getDisplayVasList():", e);
			throw new DAOException(e.getMessage(), e);
		}
		return CollectionUtils.isEmpty(resultList) ? null : resultList.get(0);
	}
	
	public List<MobCosCampaignVasDTO> getBasVasList(String basketId) throws DAOException {
		logger.info("getBasVasList() @ MobCosCampaignDAO is called");
		
		try {
			SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
					.withSchemaName("OPS$CNM")
					.withCatalogName("PKG_SB_MOB_RET_CPG")
					.withProcedureName("GET_BASKET_ADDED_VAS_LIST")
					.returningResultSet("mycursor", 
							new ParameterizedRowMapper<MobCosCampaignVasDTO>() {
								public MobCosCampaignVasDTO mapRow(ResultSet rs, int i) throws SQLException{ 
									MobCosCampaignVasDTO dto = new MobCosCampaignVasDTO();
									dto.setBasketId(rs.getString(1));
									dto.setItemId(rs.getString(2));
									dto.setOfferCd(rs.getString(3));
									dto.setOfferDesc(rs.getString(4));
									dto.setProdCd(rs.getString(5));
									dto.setProdDesc(rs.getString(6));
									dto.setAction(true);
									return dto;
								}
							});
			jdbcCall.setAccessCallParameterMetaData(false);
			jdbcCall.declareParameters(new SqlInOutParameter("mycursor",
					OracleTypes.CURSOR), new SqlParameter("i_basket_id",
					Types.INTEGER), new SqlOutParameter("gnRetVal",
					Types.INTEGER), new SqlOutParameter("gnErrCode",
					Types.INTEGER), new SqlOutParameter("gsErrText",
					Types.VARCHAR));

			MapSqlParameterSource inMap = new MapSqlParameterSource();
			inMap.addValue("i_basket_id", basketId);
			SqlParameterSource in = inMap;
			
			Map<String, Object> out = jdbcCall.execute(in);
			int retVal = -10;
			int error_code = -10;
			String error_text = null;
			
			if (((Integer) out.get("gnRetVal")) != null) {
				retVal = ((Integer) out.get("gnRetVal")).intValue();
				logger.info("PKG_SB_MOB_RET_CPG.GET_BASKET_ADDED_VAS_LIST() output gnRetVal = " +  retVal);
			}
			if((Integer) out.get("gnErrCode") != null) {
				error_code = ((Integer) out.get("gnErrCode")).intValue();
				logger.info("PKG_SB_MOB_RET_CPG.GET_BASKET_ADDED_VAS_LIST() output gnErrCode = " + error_code);
				
			} 
			if((String) out.get("gsErrText") != null) {
				error_text = out.get("gsErrText").toString();
				logger.info("PKG_SB_MOB_RET_CPG.GET_BASKET_ADDED_VAS_LIST() output gsErrText = " + error_text);
			}
			if(!CollectionUtils.isEmpty((List<MobCosCampaignVasDTO>) out.get("mycursor"))) {
				return (List<MobCosCampaignVasDTO>) out.get("mycursor");
			} else {
				return new ArrayList<MobCosCampaignVasDTO> ();
			}
		} catch (Exception e) {
			logger.error("Exception caught in getBasVasList()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	/****** Add Campaign Basket Page ******/
	public List<CodeLkupDTO> getRpList() throws DAOException {
		logger.info("getRpList() @ MobCosCampaignDAO is called");
		
		try {
			SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
					.withSchemaName("OPS$CNM")
					.withCatalogName("PKG_SB_MOB_RET_CPG")
					.withProcedureName("GET_RATE_PLAN_LIST")
					.returningResultSet("mycursor", 
							new ParameterizedRowMapper<CodeLkupDTO>() {
								public CodeLkupDTO mapRow(ResultSet rs, int i) throws SQLException{ 
									CodeLkupDTO dto = new CodeLkupDTO();
									dto.setCodeId(rs.getString(1));
									dto.setCodeDesc(rs.getString(2));
									return dto;
								}
							});
			jdbcCall.setAccessCallParameterMetaData(false);
			jdbcCall.declareParameters(new SqlInOutParameter("mycursor",
					OracleTypes.CURSOR), new SqlOutParameter("gnRetVal",
					Types.INTEGER), new SqlOutParameter("gnErrCode",
					Types.INTEGER), new SqlOutParameter("gsErrText",
					Types.VARCHAR));

			SqlParameterSource in = new MapSqlParameterSource();

			Map<String, Object> out = jdbcCall.execute(in);
			int retVal = -10;
			int error_code = -10;
			String error_text = null;
			
			if (((Integer) out.get("gnRetVal")) != null) {
				retVal = ((Integer) out.get("gnRetVal")).intValue();
				logger.info("PKG_SB_MOB_RET_CPG.GET_RATE_PLAN_LIST() output gnRetVal = " +  retVal);
			}
			if((Integer) out.get("gnErrCode") != null) {
				error_code = ((Integer) out.get("gnErrCode")).intValue();
				logger.info("PKG_SB_MOB_RET_CPG.GET_RATE_PLAN_LIST() output gnErrCode = " + error_code);
				
			} 
			if((String) out.get("gsErrText") != null) {
				error_text = out.get("gsErrText").toString();
				logger.info("PKG_SB_MOB_RET_CPG.GET_RATE_PLAN_LIST() output gsErrText = " + error_text);
			}
			if(!CollectionUtils.isEmpty((List<CodeLkupDTO>) out.get("mycursor"))) {
				return (List<CodeLkupDTO>) out.get("mycursor");
			} else {
				return new ArrayList<CodeLkupDTO> ();
			}
		} catch (Exception e) {
			logger.error("Exception caught in getRpList()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public List<CodeLkupDTO> getBundleList(String ratePlan) throws DAOException {
		logger.info("getBundleList() @ MobCosCampaignDAO is called");
		
		try {
			SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
					.withSchemaName("OPS$CNM")
					.withCatalogName("PKG_SB_MOB_RET_CPG")
					.withProcedureName("GET_BUNDLE_LIST")
					.returningResultSet("mycursor", 
							new ParameterizedRowMapper<CodeLkupDTO>() {
								public CodeLkupDTO mapRow(ResultSet rs, int i) throws SQLException{ 
									CodeLkupDTO dto = new CodeLkupDTO();
									dto.setCodeId(rs.getString(1));
									dto.setCodeDesc(rs.getString(2));
									return dto;
								}
							});
			jdbcCall.setAccessCallParameterMetaData(false);
			jdbcCall.declareParameters(new SqlInOutParameter("mycursor",
					OracleTypes.CURSOR), new SqlParameter("i_rp_cd",
					Types.VARCHAR), new SqlOutParameter("gnRetVal",
					Types.INTEGER), new SqlOutParameter("gnErrCode",
					Types.INTEGER), new SqlOutParameter("gsErrText",
					Types.VARCHAR));

			MapSqlParameterSource inMap = new MapSqlParameterSource();
			inMap.addValue("i_rp_cd", ratePlan);
			SqlParameterSource in = inMap;

			Map<String, Object> out = jdbcCall.execute(in);
			int retVal = -10;
			int error_code = -10;
			String error_text = null;
			
			if (((Integer) out.get("gnRetVal")) != null) {
				retVal = ((Integer) out.get("gnRetVal")).intValue();
				logger.info("PKG_SB_MOB_RET_CPG.GET_BUNDLE_LIST() output gnRetVal = " +  retVal);
			}
			if((Integer) out.get("gnErrCode") != null) {
				error_code = ((Integer) out.get("gnErrCode")).intValue();
				logger.info("PKG_SB_MOB_RET_CPG.GET_BUNDLE_LIST() output gnErrCode = " + error_code);
				
			} 
			if((String) out.get("gsErrText") != null) {
				error_text = out.get("gsErrText").toString();
				logger.info("PKG_SB_MOB_RET_CPG.GET_BUNDLE_LIST() output gsErrText = " + error_text);
			}
			if(!CollectionUtils.isEmpty((List<CodeLkupDTO>) out.get("mycursor"))) {
				return (List<CodeLkupDTO>) out.get("mycursor");
			} else {
				return new ArrayList<CodeLkupDTO> ();
			}
		} catch (Exception e) {
			logger.error("Exception caught in getBundleList()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public List<CodeLkupDTO> getContractList(String ratePlan, String bundle) throws DAOException {
		logger.info("getContractList() @ MobCosCampaignDAO is called");
		
		try {
			SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
					.withSchemaName("OPS$CNM")
					.withCatalogName("PKG_SB_MOB_RET_CPG")
					.withProcedureName("GET_CONTRACT_LIST")
					.returningResultSet("mycursor", 
							new ParameterizedRowMapper<CodeLkupDTO>() {
								public CodeLkupDTO mapRow(ResultSet rs, int i) throws SQLException{ 
									CodeLkupDTO dto = new CodeLkupDTO();
									dto.setCodeId(rs.getString(1));
									dto.setCodeDesc(rs.getString(2));
									return dto;
								}
							});
			jdbcCall.setAccessCallParameterMetaData(false);
			jdbcCall.declareParameters(new SqlInOutParameter("mycursor",
					OracleTypes.CURSOR), new SqlParameter("i_rp_cd",
					Types.VARCHAR), new SqlParameter("i_bp_cd",
					Types.VARCHAR), new SqlOutParameter("gnRetVal",
					Types.INTEGER), new SqlOutParameter("gnErrCode",
					Types.INTEGER), new SqlOutParameter("gsErrText",
					Types.VARCHAR));

			MapSqlParameterSource inMap = new MapSqlParameterSource();
			inMap.addValue("i_rp_cd", ratePlan);
			inMap.addValue("i_bp_cd", bundle);
			SqlParameterSource in = inMap;

			Map<String, Object> out = jdbcCall.execute(in);
			int retVal = -10;
			int error_code = -10;
			String error_text = null;
			
			if (((Integer) out.get("gnRetVal")) != null) {
				retVal = ((Integer) out.get("gnRetVal")).intValue();
				logger.info("PKG_SB_MOB_RET_CPG.GET_CONTRACT_LIST() output gnRetVal = " +  retVal);
			}
			if((Integer) out.get("gnErrCode") != null) {
				error_code = ((Integer) out.get("gnErrCode")).intValue();
				logger.info("PKG_SB_MOB_RET_CPG.GET_CONTRACT_LIST() output gnErrCode = " + error_code);
				
			} 
			if((String) out.get("gsErrText") != null) {
				error_text = out.get("gsErrText").toString();
				logger.info("PKG_SB_MOB_RET_CPG.GET_CONTRACT_LIST() output gsErrText = " + error_text);
			}
			if(!CollectionUtils.isEmpty((List<CodeLkupDTO>) out.get("mycursor"))) {
				return (List<CodeLkupDTO>) out.get("mycursor");
			} else {
				return new ArrayList<CodeLkupDTO> ();
			}
		} catch (Exception e) {
			logger.error("Exception caught in getContractList()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public List<CodeLkupDTO> getHandsetList(String ratePlan, String bundle, String contract) throws DAOException {
		logger.info("getHandsetList() @ MobCosCampaignDAO is called");
		
		try {
			SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
					.withSchemaName("OPS$CNM")
					.withCatalogName("PKG_SB_MOB_RET_CPG")
					.withProcedureName("GET_HANDSET_LIST")
					.returningResultSet("mycursor", 
							new ParameterizedRowMapper<CodeLkupDTO>() {
								public CodeLkupDTO mapRow(ResultSet rs, int i) throws SQLException{ 
									CodeLkupDTO dto = new CodeLkupDTO();
									dto.setCodeId(rs.getString(7));
									dto.setCodeDesc(rs.getString(1) + " " + rs.getString(3) 
											+ "(" + rs.getString(5) + ")");
									return dto;
								}
							});
			jdbcCall.setAccessCallParameterMetaData(false);
			jdbcCall.declareParameters(new SqlInOutParameter("mycursor",
					OracleTypes.CURSOR), new SqlParameter("i_rp_cd",
					Types.VARCHAR), new SqlParameter("i_bp_cd",
					Types.VARCHAR), new SqlParameter("i_mco_cd",
					Types.VARCHAR), new SqlOutParameter("gnRetVal",
					Types.INTEGER), new SqlOutParameter("gnErrCode",
					Types.INTEGER), new SqlOutParameter("gsErrText",
					Types.VARCHAR));

			MapSqlParameterSource inMap = new MapSqlParameterSource();
			inMap.addValue("i_rp_cd", ratePlan);
			inMap.addValue("i_bp_cd", bundle);
			inMap.addValue("i_mco_cd", contract);
			SqlParameterSource in = inMap;

			Map<String, Object> out = jdbcCall.execute(in);
			int retVal = -10;
			int error_code = -10;
			String error_text = null;
			
			if (((Integer) out.get("gnRetVal")) != null) {
				retVal = ((Integer) out.get("gnRetVal")).intValue();
				logger.info("PKG_SB_MOB_RET_CPG.GET_HANDSET_LIST() output gnRetVal = " +  retVal);
			}
			if((Integer) out.get("gnErrCode") != null) {
				error_code = ((Integer) out.get("gnErrCode")).intValue();
				logger.info("PKG_SB_MOB_RET_CPG.GET_HANDSET_LIST() output gnErrCode = " + error_code);
				
			} 
			if((String) out.get("gsErrText") != null) {
				error_text = out.get("gsErrText").toString();
				logger.info("PKG_SB_MOB_RET_CPG.GET_HANDSET_LIST() output gsErrText = " + error_text);
			}
			if(!CollectionUtils.isEmpty((List<CodeLkupDTO>) out.get("mycursor"))) {
				return (List<CodeLkupDTO>) out.get("mycursor");
			} else {
				return new ArrayList<CodeLkupDTO> ();
			}
		} catch (Exception e) {
			logger.error("Exception caught in getHandsetList()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public List<CodeLkupDTO> getBasketList(String ratePlan, String bundle, String contract) throws DAOException {
		logger.info("getBasketList() @ MobCosCampaignDAO is called");
		
		try {
			SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
					.withSchemaName("OPS$CNM")
					.withCatalogName("PKG_SB_MOB_RET_CPG")
					.withProcedureName("GET_BASKET_LIST")
					.returningResultSet("mycursor", 
							new ParameterizedRowMapper<CodeLkupDTO>() {
								public CodeLkupDTO mapRow(ResultSet rs, int i) throws SQLException{ 
									CodeLkupDTO dto = new CodeLkupDTO();
									dto.setCodeId(rs.getString(1));
									dto.setCodeDesc(rs.getString(2));
									return dto;
								}
							});
			jdbcCall.setAccessCallParameterMetaData(false);
			jdbcCall.declareParameters(new SqlInOutParameter("mycursor",
					OracleTypes.CURSOR), new SqlParameter("i_rp_cd",
					Types.VARCHAR), new SqlParameter("i_bp_cd",
					Types.VARCHAR), new SqlParameter("i_mco_cd",
					Types.VARCHAR), new SqlOutParameter("gnRetVal",
					Types.INTEGER), new SqlOutParameter("gnErrCode",
					Types.INTEGER), new SqlOutParameter("gsErrText",
					Types.VARCHAR));

			MapSqlParameterSource inMap = new MapSqlParameterSource();
			inMap.addValue("i_rp_cd", ratePlan);
			inMap.addValue("i_bp_cd", bundle);
			inMap.addValue("i_mco_cd", contract);
			SqlParameterSource in = inMap;

			Map<String, Object> out = jdbcCall.execute(in);
			int retVal = -10;
			int error_code = -10;
			String error_text = null;
			
			if (((Integer) out.get("gnRetVal")) != null) {
				retVal = ((Integer) out.get("gnRetVal")).intValue();
				logger.info("PKG_SB_MOB_RET_CPG.GET_BASKET_LIST() output gnRetVal = " +  retVal);
			}
			if((Integer) out.get("gnErrCode") != null) {
				error_code = ((Integer) out.get("gnErrCode")).intValue();
				logger.info("PKG_SB_MOB_RET_CPG.GET_BASKET_LIST() output gnErrCode = " + error_code);
				
			} 
			if((String) out.get("gsErrText") != null) {
				error_text = out.get("gsErrText").toString();
				logger.info("PKG_SB_MOB_RET_CPG.GET_BASKET_LIST() output gsErrText = " + error_text);
			}
			if(!CollectionUtils.isEmpty((List<CodeLkupDTO>) out.get("mycursor"))) {
				return (List<CodeLkupDTO>) out.get("mycursor");
			} else {
				return new ArrayList<CodeLkupDTO> ();
			}
		} catch (Exception e) {
			logger.error("Exception caught in getBasketList()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public boolean createCpgBasket(String cpgId, String basketId, String basketDesc, String tier, String userId) throws DAOException {
		logger.info("createCpgBasket() @ MobCosCampaignDAO is called");
		
		try {
			SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
					.withSchemaName("OPS$CNM")
					.withCatalogName("PKG_SB_MOB_RET_CPG")
					.withProcedureName("NEW_CPG_BASKET");
			jdbcCall.setAccessCallParameterMetaData(false);
			jdbcCall.declareParameters(new SqlParameter("i_campaign_id",
					Types.INTEGER), new SqlParameter("i_basket_id",
					Types.INTEGER), new SqlParameter("i_basket_desc",
					Types.VARCHAR), new SqlParameter("i_tier",
					Types.VARCHAR), new SqlParameter("i_user_id",
					Types.VARCHAR), new SqlOutParameter("gnRetVal",
					Types.INTEGER), new SqlOutParameter("gnErrCode",
					Types.INTEGER), new SqlOutParameter("gsErrText",
					Types.VARCHAR));

			MapSqlParameterSource inMap = new MapSqlParameterSource();
			inMap.addValue("i_campaign_id", cpgId);
			inMap.addValue("i_basket_id", basketId);
			inMap.addValue("i_basket_desc", basketDesc);
			inMap.addValue("i_tier", tier);
			inMap.addValue("i_user_id", userId);
			SqlParameterSource in = inMap;

			Map<String, Object> out = jdbcCall.execute(in);
			int retVal = -10;
			int error_code = -10;
			String error_text = null;
			
			if (((Integer) out.get("gnRetVal")) != null) {
				retVal = ((Integer) out.get("gnRetVal")).intValue();
				logger.info("PKG_SB_MOB_RET_CPG.NEW_CPG_BASKET() output gnRetVal = " +  retVal);
			}
			if((Integer) out.get("gnErrCode") != null) {
				error_code = ((Integer) out.get("gnErrCode")).intValue();
				logger.info("PKG_SB_MOB_RET_CPG.NEW_CPG_BASKET() output gnErrCode = " + error_code);
				
			} 
			if((String) out.get("gsErrText") != null) {
				error_text = out.get("gsErrText").toString();
				logger.info("PKG_SB_MOB_RET_CPG.NEW_CPG_BASKET() output gsErrText = " + error_text);
			}
			return (error_code >= 0);
		} catch (Exception e) {
			logger.error("Exception caught in createCpgBasket()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}

	/****** Update Campaign ******/
	public boolean updCpgHdr(String cpgId, String cpgTitle, String cpgName, String handset, Date effStartDate, Date effEndDate, String userId) throws DAOException {
		logger.info("updCpgHdr() @ MobCosCampaignDAO is called");
		
		try {
			SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
					.withSchemaName("OPS$CNM")
					.withCatalogName("PKG_SB_MOB_RET_CPG")
					.withProcedureName("UPDATE_CPG_HDR");
			jdbcCall.setAccessCallParameterMetaData(false);
			jdbcCall.declareParameters(new SqlParameter("i_campaign_title",
					Types.VARCHAR), new SqlParameter("i_campaign_name",
					Types.VARCHAR), new SqlParameter("i_handset_desc",
					Types.VARCHAR), new SqlParameter("i_eff_start_date",
					Types.DATE), new SqlParameter("i_eff_end_date",
					Types.DATE),  new SqlParameter("i_user_id",
					Types.VARCHAR), new SqlInOutParameter("i_campaign_id",
					Types.INTEGER), new SqlOutParameter("gnRetVal",
					Types.INTEGER), new SqlOutParameter("gnErrCode",
					Types.INTEGER), new SqlOutParameter("gsErrText",
					Types.VARCHAR));

			MapSqlParameterSource inMap = new MapSqlParameterSource();
			inMap.addValue("i_campaign_title", cpgTitle);
			inMap.addValue("i_campaign_name", cpgName);
			inMap.addValue("i_handset_desc", handset);
			inMap.addValue("i_eff_start_date", effStartDate);
			inMap.addValue("i_eff_end_date", effEndDate);
			inMap.addValue("i_user_id", userId);
			inMap.addValue("i_campaign_id", cpgId);
			SqlParameterSource in = inMap;

			Map<String, Object> out = jdbcCall.execute(in);
			int retVal = -10;
			int error_code = -10;
			String error_text = null;
			
			if (((Integer) out.get("gnRetVal")) != null) {
				retVal = ((Integer) out.get("gnRetVal")).intValue();
				logger.info("PKG_SB_MOB_RET_CPG.UPDATE_CPG_HDR() output gnRetVal = " +  retVal);
			}
			if((Integer) out.get("gnErrCode") != null) {
				error_code = ((Integer) out.get("gnErrCode")).intValue();
				logger.info("PKG_SB_MOB_RET_CPG.UPDATE_CPG_HDR() output gnErrCode = " + error_code);
				
			} 
			if((String) out.get("gsErrText") != null) {
				error_text = out.get("gsErrText").toString();
				logger.info("PKG_SB_MOB_RET_CPG.UPDATE_CPG_HDR() output gsErrText = " + error_text);
			}
			return (error_code >= 0);
		} catch (Exception e) {
			logger.error("Exception caught in updCpgHdr()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}

	public boolean updCpgDtl(String cpgId, String basketId, String basketDesc, String seq, String tier, 
			Date effStartDate, Date effEndDate, String userId) throws DAOException {
		logger.info("updCpgDtl() @ MobCosCampaignDAO is called");
		
		try {
			SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
					.withSchemaName("OPS$CNM")
					.withCatalogName("PKG_SB_MOB_RET_CPG")
					.withProcedureName("UPDATE_CPG_BASKET");
			jdbcCall.setAccessCallParameterMetaData(false);
			jdbcCall.declareParameters(new SqlParameter("i_campaign_id",
					Types.INTEGER), new SqlParameter("i_basket_id",
					Types.INTEGER), new SqlParameter("i_basket_desc",
					Types.VARCHAR), new SqlParameter("i_campaign_basket_seq",
					Types.INTEGER), new SqlParameter("i_tier",
					Types.VARCHAR), new SqlParameter("i_eff_start_date",
					Types.DATE), new SqlParameter("i_eff_end_date",
					Types.DATE), new SqlParameter("i_user_id",
					Types.VARCHAR), new SqlOutParameter("gnRetVal",
					Types.INTEGER), new SqlOutParameter("gnErrCode",
					Types.INTEGER), new SqlOutParameter("gsErrText",
					Types.VARCHAR));

			MapSqlParameterSource inMap = new MapSqlParameterSource();
			inMap.addValue("i_campaign_id", cpgId);
			inMap.addValue("i_basket_id", basketId);
			inMap.addValue("i_basket_desc", basketDesc);
			inMap.addValue("i_campaign_basket_seq", seq);
			inMap.addValue("i_tier", tier);
			inMap.addValue("i_eff_start_date", effStartDate);
			inMap.addValue("i_eff_end_date", effEndDate);
			inMap.addValue("i_user_id", userId);
			SqlParameterSource in = inMap;

			Map<String, Object> out = jdbcCall.execute(in);
			int retVal = -10;
			int error_code = -10;
			String error_text = null;
			
			if (((Integer) out.get("gnRetVal")) != null) {
				retVal = ((Integer) out.get("gnRetVal")).intValue();
				logger.info("PKG_SB_MOB_RET_CPG.UPDATE_CPG_BASKET() output gnRetVal = " +  retVal);
			}
			if((Integer) out.get("gnErrCode") != null) {
				error_code = ((Integer) out.get("gnErrCode")).intValue();
				logger.info("PKG_SB_MOB_RET_CPG.UPDATE_CPG_BASKET() output gnErrCode = " + error_code);
				
			} 
			if((String) out.get("gsErrText") != null) {
				error_text = out.get("gsErrText").toString();
				logger.info("PKG_SB_MOB_RET_CPG.UPDATE_CPG_BASKET() output gsErrText = " + error_text);
			}
			return (error_code >= 0);
		} catch (Exception e) {
			logger.error("Exception caught in updCpgDtl()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}

	public boolean updCpgVas(String basketId, String itemId, String action, String userId) throws DAOException {
		logger.info("updCpgVas() @ MobCosCampaignDAO is called");
		
		try {
			SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
					.withSchemaName("OPS$CNM")
					.withCatalogName("PKG_SB_MOB_RET_CPG")
					.withProcedureName("UPDATE_VAS_UNDER_BASKET");
			jdbcCall.setAccessCallParameterMetaData(false);
			jdbcCall.declareParameters(new SqlParameter("i_basket_id",
					Types.INTEGER), new SqlParameter("i_item_id",
					Types.INTEGER), new SqlParameter("i_action",
					Types.VARCHAR), new SqlParameter("i_user_id",
					Types.VARCHAR), new SqlOutParameter("gnRetVal",
					Types.INTEGER), new SqlOutParameter("gnErrCode",
					Types.INTEGER), new SqlOutParameter("gsErrText",
					Types.VARCHAR));

			MapSqlParameterSource inMap = new MapSqlParameterSource();
			inMap.addValue("i_basket_id", basketId);
			inMap.addValue("i_item_id", itemId);
			inMap.addValue("i_action", action);
			inMap.addValue("i_user_id", userId);
			logger.info("i_basket_id = " + basketId);
			logger.info("i_item_id = " + itemId);
			logger.info("i_action = " + action);
			logger.info("i_user_id = " + userId);
			SqlParameterSource in = inMap;

			Map<String, Object> out = jdbcCall.execute(in);
			int retVal = -10;
			int error_code = -10;
			String error_text = null;
			
			if (((Integer) out.get("gnRetVal")) != null) {
				retVal = ((Integer) out.get("gnRetVal")).intValue();
				logger.info("PKG_SB_MOB_RET_CPG.UPDATE_VAS_UNDER_BASKET() output gnRetVal = " +  retVal);
			}
			if((Integer) out.get("gnErrCode") != null) {
				error_code = ((Integer) out.get("gnErrCode")).intValue();
				logger.info("PKG_SB_MOB_RET_CPG.UPDATE_VAS_UNDER_BASKET() output gnErrCode = " + error_code);
				
			} 
			if((String) out.get("gsErrText") != null) {
				error_text = out.get("gsErrText").toString();
				logger.info("PKG_SB_MOB_RET_CPG.UPDATE_VAS_UNDER_BASKET() output gsErrText = " + error_text);
			}
			return (error_code >= 0);
		} catch (Exception e) {
			logger.error("Exception caught in updCpgVas()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}

	public boolean updCpgChannelAssgn(String channelId, String cpgId, String customerTier, String userId) throws DAOException {
		logger.info("updCpgChannelAssgn @ MobCosCampaignDAO is called");
		try {
			SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
					.withSchemaName("OPS$CNM")
					.withCatalogName("PKG_SB_MOB_RET_CPG")
					.withProcedureName("NEW_CHANNEL_CPG");
			jdbcCall.setAccessCallParameterMetaData(false);
			jdbcCall.declareParameters(new SqlParameter("i_channel_id",
					Types.INTEGER), new SqlParameter("i_campaign_id",
					Types.INTEGER), new SqlParameter("i_customer_tier",
					Types.INTEGER), new SqlParameter("i_action",
					Types.VARCHAR), new SqlParameter("i_user_id",
					Types.VARCHAR), new SqlOutParameter("gnRetVal",
					Types.INTEGER), new SqlOutParameter("gnErrCode",
					Types.INTEGER), new SqlOutParameter("gsErrText",
					Types.VARCHAR));

			MapSqlParameterSource inMap = new MapSqlParameterSource();
			inMap.addValue("i_channel_id", channelId);
			inMap.addValue("i_campaign_id", cpgId);
			inMap.addValue("i_customer_tier", customerTier);
			inMap.addValue("i_action", "N");
			inMap.addValue("i_user_id", userId);
			SqlParameterSource in = inMap;

			Map<String, Object> out = jdbcCall.execute(in);
			int retVal = -10;
			int error_code = -10;
			String error_text = null;
			
			if (((Integer) out.get("gnRetVal")) != null) {
				retVal = ((Integer) out.get("gnRetVal")).intValue();
				logger.info("PKG_SB_MOB_RET_CPG.NEW_CHANNEL_CPG() output gnRetVal = " +  retVal);
			}
			if((Integer) out.get("gnErrCode") != null) {
				error_code = ((Integer) out.get("gnErrCode")).intValue();
				logger.info("PKG_SB_MOB_RET_CPG.NEW_CHANNEL_CPG() output gnErrCode = " + error_code);
				
			} 
			if((String) out.get("gsErrText") != null) {
				error_text = out.get("gsErrText").toString();
				logger.info("PKG_SB_MOB_RET_CPG.NEW_CHANNEL_CPG() output gsErrText = " + error_text);
			}
			return (error_code >= 0);
		} catch (Exception e) {
			logger.error("Exception caught in updCpgChannelAssgn()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}

	
	
	// MIP.P4 modification
	public List<BasketQuotaDTO> getBasketQuotaList(String basketId)	{
		List<BasketQuotaDTO> basketQuotaList = new ArrayList<BasketQuotaDTO>();

		String SQL = "select P.BASKET_ID,\n" + "       QL.QUOTA_ID,\n"
				+ "       QL.QUOTA_DESC,\n" + "       QL.QUOTA_CEILING,\n"
				+ "       QL.VALIDITY_MTH\n"
				+ "  from W_BASKET_PARM P, W_QUOTA_LKUP QL\n"
				+ " where P.PARM_TYPE_VAL = QL.QUOTA_ID\n"
				+ "   and P.PARM_TYPE = 'QUOTA'\n" + "   and P.BASKET_ID = ?";

		ParameterizedRowMapper<BasketQuotaDTO> mapper = new ParameterizedRowMapper<BasketQuotaDTO>() {
			public BasketQuotaDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				BasketQuotaDTO basketQuotaDto = new BasketQuotaDTO();
				basketQuotaDto.setBasketId(rs.getString("BASKET_ID"));
				basketQuotaDto.setQuotaId(rs.getString("QUOTA_ID"));
				basketQuotaDto.setQuotaDesc(rs.getString("QUOTA_DESC"));
				basketQuotaDto.setQuotaCeiling(rs.getString("QUOTA_CEILING"));
				basketQuotaDto.setValidityMth(rs.getString("VALIDITY_MTH"));

				return basketQuotaDto;
			}
		};

		try {

			logger.info("getBasketQuotaList @ VasDetailDAO: ");
			logger.debug("getBasketQuotaList @ VasDetailDAO: " + SQL);

			basketQuotaList = simpleJdbcTemplate.query(SQL, mapper, basketId);

		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException");
			basketQuotaList = null;
			return null;
		} catch (Exception e) {
			logger.info("Exception caught in getBasketQuotaList():", e);
			return null;
		}
		
		return basketQuotaList;
		

	}
	
	// MIP.P4 modification
	public BasketDTO getBasketAttribute(String basketId, String appDate) throws DAOException {
		List<BasketDTO> basketList = new ArrayList<BasketDTO>();

		String SQLModel ="select BAMV.BASKET_ID,BAMV.DESCRIPTION,\n" +
			"       BAMV.OFFER_TYPE,\n" + 
			"       BAMV.OFFER_TYPE_ID,\n" + 
			"       BAMV.RATE_PLAN_ID, --499\n" + 
			"       BAMV.RP_GROSS_PLAN_FEE, \n" + 
			"       DECODE(BAMV.OFFER_TYPE_ID, '1', 'Y', 'N') PH_IND,\n" + 
			"       BAMV.CONTRACT_PERIOD_ID,\n" +
			"       BAMV.BRAND,\n" + 
			"       BAMV.COLOR,\n" + 
			"       BAMV.CUSTOMER_TIER_ID,\n" + 
			"       BAMV.CUSTOMER_TIER,\n" + 
			"       BAMV.MODEL,\n" + 
			"       BAMV.OFFER_TYPE,\n" + 
			"       BAMV.OFFER_TYPE_ID,\n" + 
			"       BAMV.RATE_PLAN,\n" + 
			"       BAMV.RATE_PLAN_ID,\n" + 
			"       BAMV.RP_TYPE,\n" + 
			"       BAMV.RP_TYPE_ID,\n" + 
			"       BAMV.SIM_TYPE,\n" + 
			"       BAMV.SIM_TYPE_ID,\n" + 
			"       BAMV.BRAND_ID,\n" + 
			"       BAMV.MODEL_ID,\n" + 
			"       BAMV.COLOR_ID,bamv.basket_type, bamv.basket_type_id, \n" + 
			"		BAMV.CREDIT_CARD_IND, B.DUMMY_IND,\n" +
			"		BAMV.data_only_ind, \n" +	
			"		BAMV.UPFRONT_CC_IND, \n" +
			"       NVL(BAMV.MIP_BRAND, '1') MIP_BRAND, \n" +
			"       nvl((select sum(IP.ONETIME_AMT) UPFRONT_AMT\n" + 
			"          from W_BASKET_ITEM_ASSGN BIA, W_ITEM I, W_ITEM_PRICING IP \n" + 
			"         where BIA.ITEM_ID = I.ID \n" + 
			"           and BIA.ITEM_ID = IP.ID \n" + 
			"           and TO_DATE(:appDate, 'DD/MM/YYYY') between BIA.EFF_START_DATE and\n" + 
			"               NVL(BIA.EFF_END_DATE, sysdate)\n" + 
			"           and TO_DATE(:appDate, 'DD/MM/YYYY') between IP.EFF_START_DATE and\n" + 
			"               NVL(IP.EFF_END_DATE, sysdate)\n" + 
			"           and I.TYPE = 'HS'\n" + 
			"           and BIA.BASKET_ID = BAMV.BASKET_ID),0) UPFRONT_AMT,\n" + 
			"       (select sum(nvl(IP.ONETIME_AMT,0)) UPFRONT_AMT\n" + 
			"          from W_BASKET_ITEM_ASSGN BIA, W_ITEM I, W_ITEM_PRICING IP \n" + 
			"         where BIA.ITEM_ID = I.ID \n" + 
			"           and BIA.ITEM_ID = IP.ID \n" + 
			"           and TO_DATE(:appDate, 'DD/MM/YYYY') between BIA.EFF_START_DATE and\n" + 
			"               NVL(BIA.EFF_END_DATE, sysdate)\n" + 
			"           and TO_DATE(:appDate, 'DD/MM/YYYY') between IP.EFF_START_DATE and\n" + 
			"               NVL(IP.EFF_END_DATE, sysdate)\n" + 
			"           and I.TYPE in ('RP', 'BFEE', 'BVAS', 'VAS')\n" +
			"           and BIA.BASKET_ID = BAMV.BASKET_ID) PRE_PAYMENT_AMT ,\n" + 
			"(select IPPA.POS_ITEM_CD\n" +
			"         from W_BASKET_ITEM_ASSGN        BIA,\n" + 
			"              W_ITEM                     I,\n" + 
			"              W_ITEM_OFFER_ASSGN         IOA,\n" + 
			"              W_ITEM_OFFER_PRODUCT_ASSGN IOPA,\n" + 
			"              W_ITEM_PRODUCT_POS_ASSGN   IPPA\n" + 
			"        where BIA.ITEM_ID = I.ID\n" + 
			"          and I.ID = IOA.ITEM_ID\n" + 
			"          and IOA.ITEM_ID = IOPA.ITEM_ID\n" + 
			"          and IOA.ITEM_OFFER_SEQ = IOPA.ITEM_OFFER_SEQ\n" + 
			"          and IOPA.ITEM_ID = IPPA.ITEM_ID\n" + 
			"          and IOPA.ITEM_OFFER_SEQ = IPPA.ITEM_PRODUCT_SEQ\n" + 
			"          and IOPA.ITEM_PRODUCT_SEQ = IPPA.ITEM_PRODUCT_SEQ\n" + 
			"          and TO_DATE(:appDate, 'DD/MM/YYYY') between\n" + 
			"              BIA.EFF_START_DATE and NVL(BIA.EFF_END_DATE, sysdate)\n" + 
			"          and I.TYPE = 'HS'\n" + 
			"          and BIA.BASKET_ID = BAMV.BASKET_ID) HS_POS_ITEM_CD ,\n" +
					 " (select A.HS_EXTRA_FUNCTION " +
					 " 		FROM BOMWEB_STOCK_CATALOG A " +
					 "		LEFT JOIN W_ITEM_PRODUCT_POS_ASSGN IPPA ON (A.ITEM_CODE = IPPA.POS_ITEM_CD)" +
					 "		LEFT JOIN W_ITEM I ON (IPPA.ITEM_ID = I.ID)" +
					 "		LEFT JOIN W_BASKET_ITEM_ASSGN BIA ON (BIA.ITEM_ID = I.ID)" +
					 "		WHERE IPPA.ITEM_ID = BIA.ITEM_ID" +
					 "		AND TO_DATE(:appDate, 'DD/MM/YYYY') between BIA.EFF_START_DATE and NVL(BIA.EFF_END_DATE, sysdate)" + 
					 "		AND I.TYPE = 'HS'" +
					 "		AND BIA.BASKET_ID = BAMV.BASKET_ID) HS_EXTRA_FUNCTION, " +
					 " (SELECT bcl.code_desc " +
					 " 		FROM BOMWEB_STOCK_CATALOG A " +
					 " 		LEFT JOIN W_ITEM_PRODUCT_POS_ASSGN IPPA ON (A.ITEM_CODE = IPPA.POS_ITEM_CD) " +
					 " 		LEFT JOIN W_ITEM I ON (IPPA.ITEM_ID = I.ID) " +
					 " 		LEFT JOIN W_BASKET_ITEM_ASSGN BIA ON (BIA.ITEM_ID = I.ID) " +
					 " 		left join bomweb_code_lkup bcl on bcl.code_type = 'SIM_SIZE' and bcl.code_id = a.sim_type " +
					 " 		WHERE     IPPA.ITEM_ID = BIA.ITEM_ID " +
					 " 		AND TO_DATE ( :appDate, 'DD/MM/YYYY') BETWEEN BIA.EFF_START_DATE AND NVL (BIA.EFF_END_DATE,SYSDATE) " +
					 " 		AND I.TYPE = 'HS' " +
					 " 		AND BIA.BASKET_ID = BAMV.BASKET_ID) HS_SIM_SIZE, " +
			" (select DECODE(count(*), 0, 'N', 'Y')\n" +
			" from W_BASKET_ITEM_ASSGN        BIA,\n" + 
			"      W_ITEM                     I,\n" + 
			"      W_ITEM_OFFER_ASSGN         IOA,\n" + 
			"      W_ITEM_OFFER_PRODUCT_ASSGN IOPA,\n" + 
			"      W_ITEM_PRODUCT_POS_ASSGN   IPPA,\n" + 
			"      BOMWEB_HOTTEST_MODEL       HM\n" + 
			"where BIA.ITEM_ID = I.ID\n" + 
			"  and I.ID = IOA.ITEM_ID\n" + 
			"  and IOA.ITEM_ID = IOPA.ITEM_ID\n" + 
			"  and IOA.ITEM_OFFER_SEQ = IOPA.ITEM_OFFER_SEQ\n" + 
			"  and IOPA.ITEM_ID = IPPA.ITEM_ID\n" + 
			"  and IOPA.ITEM_OFFER_SEQ = IPPA.ITEM_PRODUCT_SEQ\n" + 
			"  and IOPA.ITEM_PRODUCT_SEQ = IPPA.ITEM_PRODUCT_SEQ\n" + 
			"  and IPPA.POS_ITEM_CD = HM.ITEM_CODE\n" + 
			"  and TO_DATE(:appDate, 'DD/MM/YYYY') between BIA.EFF_START_DATE and NVL(BIA.EFF_END_DATE, sysdate)\n" + 
			"  and I.TYPE = 'HS'\n" + 
			"  and BIA.BASKET_ID = BAMV.BASKET_ID ) HOTTEST_MODEL_IND ,\n" +
			" (select ML.MAP_TO\n" +
			"  from W_MAPPING_LKUP ML\n" + 
			" where ML.MAP_TYPE = 'DUMMY_BASKET'\n" + 
			"   and ML.MAP_FROM = BAMV.BASKET_ID) REAL_BASKET_ID \n" +
			
			" , NVL(BAMV.NATURE,'ACQ') NATURE \n" +
			
			"  from W_BASKET_ATTRIBUTE_MV BAMV, W_BASKET B\n" + 
			" where BAMV.BASKET_ID = B.ID \n"+
			" and  BAMV.BASKET_ID = :basketId";


		ParameterizedRowMapper<BasketDTO> mapper = new ParameterizedRowMapper<BasketDTO>() {
			public BasketDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				BasketDTO basket = new BasketDTO();
				basket.setBasketId(rs.getString("BASKET_ID"));
				basket.setBasketOfferTypeCd(rs.getString("OFFER_TYPE_ID"));
				basket.setBasketOfferTypeDesc(rs.getString("OFFER_TYPE"));
				basket.setRecurrentAmt(rs.getString("RATE_PLAN_ID"));
				basket.setGrossPlanFee(rs.getString("RP_GROSS_PLAN_FEE"));
				basket.setContractPeriod(rs.getString("CONTRACT_PERIOD_ID"));
				basket.setUpfrontAmt(rs.getString("UPFRONT_AMT"));
				basket.setBrandId(rs.getString("brand_id"));
				basket.setModelId(rs.getString("model_id"));
				basket.setColorId(rs.getString("color_id"));
				basket.setPublicHouseBaksetInd(rs.getString("PH_IND"));//PH_IND, add 20120213 wilson
				basket.setPrePaymentAmt(rs.getString("PRE_PAYMENT_AMT"));
				basket.setBasketTypeId(rs.getString("basket_type_id"));
				basket.setBasketTypeDesc(rs.getString("basket_type"));
				basket.setDescription(rs.getString("DESCRIPTION"));
				////////////////
				basket.setBrandDesc(rs.getString("BRAND"));
				basket.setColorDesc(rs.getString("COLOR"));
				basket.setCustomerTierId(rs.getString("CUSTOMER_TIER_ID"));
				basket.setCustomerTierDesc(rs.getString("CUSTOMER_TIER"));
				basket.setModelDesc(rs.getString("MODEL"));
				basket.setOfferTypeDesc(rs.getString("OFFER_TYPE"));
				basket.setOfferTypeId(rs.getString("OFFER_TYPE_ID"));
				basket.setRatePlanDesc(rs.getString("RATE_PLAN"));
				basket.setRatePlanId(rs.getString("RATE_PLAN_ID"));
				basket.setRpTypeDesc(rs.getString("RP_TYPE"));
				basket.setRpTypeId(rs.getString("RP_TYPE_ID"));
				basket.setSimTypeDesc(rs.getString("SIM_TYPE"));
				basket.setSimTypeId(rs.getString("SIM_TYPE_ID"));
				////////////////
				basket.setCreditCardInd(rs.getString("CREDIT_CARD_IND"));
				basket.setHsPosItemCd(rs.getString("HS_POS_ITEM_CD"));// add by wilson 20120319
				basket.setDummyBasketInd(rs.getString("DUMMY_IND"));//add by wilson 20120319
				basket.setRealBasketId(rs.getString("REAL_BASKET_ID"));//add by wilson 20120319
				basket.setDataOnlyInd(rs.getString("data_only_ind"));
				basket.setHottestModelInd(rs.getString("HOTTEST_MODEL_IND"));
				
				basket.setBasketQuotaList(getBasketQuotaList(basket.getBasketId()));
				String hsExtraFunction = rs.getString("HS_EXTRA_FUNCTION");
				basket.setHandsetExtraFunction(hsExtraFunction);
				basket.setHandsetSimSize(rs.getString("HS_SIM_SIZE"));
				basket.setUpfrontCCInd(rs.getString("UPFRONT_CC_IND"));
				
				basket.setMipBrand(rs.getString("MIP_BRAND"));
				/*if (StringUtils.isNotBlank(hsExtraFunction)) {
					basket.setHsExtraFunction(HsExtraFunction.getHsExtraFunction(hsExtraFunction));
				}*/
				
				// MIP.P4 modification
				basket.setNature(rs.getString("NATURE"));
				
				return basket;
			}
		};

		try {

			logger.info("getBasketAttribute @ VasDetailDAO: ");
			logger.debug("getBasketAttribute @ VasDetailDAO: " + SQLModel);
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("appDate", appDate);
			params.addValue("basketId", basketId);
			basketList = simpleJdbcTemplate.query(SQLModel, mapper, params);

		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException");
			basketList = null;
			return null;
		} catch (Exception e) {
			logger.info("Exception caught in getBasketAttribute():", e);
			throw new DAOException(e.getMessage(), e);
		}
		if (basketList.size() > 0) {
			return basketList.get(0);// only return the first one
		} else {

			return null;
		}

	}
	
	

	
	/****** Preview Campaign Basket******/
	// MIP.P4 modification
	public List<VasDetailDTO> getPreviewBundleList(String basketId, String locale, String appDate, String channelCd, String offerNature) throws DAOException {
		List<VasDetailDTO> vasDetailList = new ArrayList<VasDetailDTO>();
		logger.info("getBundleList is called");
		
		// MIP.P4 modification
		System.out.println("MIP.P4 modification: pkg_sb_mob_stock.get_stock_count_disp_mip4 (9)");
		
		
		String sql = "SELECT ITEM_T.ITEM_ID, " +
				"  ITEM_T.ITEM_LOB, " +
				"  ITEM_T.ITEM_TYPE, " +
				"  ITEM_T.ITEM_MDO_IND, " +
				"  ITEM_DESC_T.DESCRIPTION, " +
				"  ITEM_HTML_T.HTML, " +
				"  ITEM_HTML_T.HTML2, " +
				"  ITEM_HTML_T.LOCALE, " +
				"  ITEM_HTML_T.DISPLAY_TYPE, " +
				"  IP_T.ONETIME_AMT, " +
				"  IP_T.RECURRENT_AMT, " +
				"  (SELECT MIN(IPPA.POS_ITEM_CD) " +
				"  FROM W_ITEM_PRODUCT_POS_ASSGN IPPA " +
				"  WHERE IPPA.ITEM_ID = ITEM_T.ITEM_ID " +
				"  ) POS_ITEM_CD, " +
				"  ITEM_T.DISPLAY_SEQ, " +
				
				"  (SELECT pkg_sb_mob_stock.get_stock_count_disp_mip4('PEND', :channelCd, IPPA.POS_ITEM_CD, :offerNature) STOCK_COUNT " +
				
				"  FROM W_ITEM_PRODUCT_POS_ASSGN IPPA " +
				"  WHERE IPPA.ITEM_ID  = ITEM_T.ITEM_ID " +
				"  ) STOCK_COUNT " +
				"FROM ( " +
				"  (SELECT I.ID ITEM_ID, " +
				"    I.LOB ITEM_LOB, " +
				"    I.TYPE ITEM_TYPE, " +
				"    BIA.MDO_IND ITEM_MDO_IND, " +
				"    BIA.DISPLAY_SEQ " +
				"  FROM W_BASKET_ITEM_ASSGN BIA, " +
				"    W_ITEM I " +
				"  WHERE BIA.ITEM_ID = I.ID " +
				"  AND BIA.BASKET_ID = :basketId " +
				"  AND TO_DATE(:appDate, 'DD/MM/YYYY') BETWEEN BIA.EFF_START_DATE AND TRUNC(NVL(BIA.EFF_END_DATE, SYSDATE)) " +
				"  ) " +
				"UNION " +
				"  (SELECT I.ID ITEM_ID, " +
				"    I.LOB ITEM_LOB, " +
				"    I.TYPE ITEM_TYPE, " +
				"    DBIA.MDO_IND ITEM_MDO_IND, " +
				"    DBIA.DISPLAY_SEQ " +
				"  FROM W_RET_CAMPAIGN_BASKET_VAS RCBV " +
				"  LEFT JOIN W_ITEM I " +
				"  ON RCBV.ITEM_ID = I.ID " +
				"  LEFT JOIN W_DIC_BASKET_ITEM_ASSGN DBIA " +
				"  ON I.ID                = DBIA.ITEM_ID " +
				"  WHERE RCBV.BASKET_ID   = :basketId " +
				"  AND DBIA.BASKET_ID     = RCBV.BASKET_ID " +
				"  AND RCBV.EFF_END_DATE IS NULL " +
				"  )) ITEM_T, " +
				"  (SELECT HTML, " +
				"    HTML2, " +
				"    LOCALE, " +
				"    ITEM_ID, " +
				"    DISPLAY_TYPE, " +
				"    NULL DESCRIPTION " +
				"  FROM W_ITEM_DISPLAY_LKUP " +
				"  WHERE DISPLAY_TYPE = 'ITEM_SELECT' " +
				"  AND HTML          IS NOT NULL " +
				"  ) ITEM_HTML_T, " +
				"  (SELECT I.ID ITEM_ID, " +
				"    I.DESCRIPTION " +
				"  FROM W_ITEM I, " +
				"    W_RET_CAMPAIGN_BASKET_VAS RCBV " +
				"  WHERE I.ID             = RCBV.ITEM_ID " +
				"  AND RCBV.BASKET_ID     = :basketId " +
				"  AND RCBV.EFF_END_DATE IS NULL " +
				"  )ITEM_DESC_T, " +
				"  (SELECT IP.ID ITEM_ID, " +
				"    IP.ONETIME_AMT, " +
				"    IP.RECURRENT_AMT " +
				"  FROM W_ITEM_PRICING IP " +
				"  WHERE TO_DATE(:appDate, 'DD/MM/YYYY') BETWEEN IP.EFF_START_DATE AND TRUNC(NVL(IP.EFF_END_DATE, sysdate)) " +
				"  ) IP_T " +
				"WHERE ITEM_T.ITEM_ID      = ITEM_HTML_T.ITEM_ID(+) " +
				"AND ITEM_T.ITEM_ID        = IP_T.ITEM_ID(+) " +
				"AND ITEM_T.ITEM_ID        = ITEM_DESC_T.ITEM_ID(+) " +
				"AND ((ITEM_T.ITEM_TYPE   IN ('HS', 'RP', 'HSRB', 'VAS', 'BVAS', 'BFEE') " +
				"AND ITEM_T.ITEM_MDO_IND  IN ('M', 'D')) " +
				"OR ITEM_T.ITEM_TYPE      IN ('AP_INC', 'MNP_INC')) " +
				"AND ITEM_HTML_T.LOCALE(+) = :locale " +
				"ORDER BY ITEM_T.DISPLAY_SEQ";
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("basketId", basketId);
		params.addValue("locale", locale);
		params.addValue("appDate", appDate);
		params.addValue("channelCd", channelCd);
		
		// MIP.P4 modification
		params.addValue("offerNature", offerNature);
		
		ParameterizedRowMapper<VasDetailDTO> mapper = new ParameterizedRowMapper<VasDetailDTO>() {
			public VasDetailDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				VasDetailDTO vasDto = new VasDetailDTO();
				String html = rs.getString("html");
				String desc = rs.getString("description");
				if(!StringUtils.isEmpty(desc)) {
					desc = "<span class=\"item_title_vas\">" + desc + "</span>";
				}
				vasDto.setItemHtml(StringUtils.isEmpty(html) ? desc : html);
				vasDto.setItemId(rs.getString("item_id"));
				vasDto.setItemLob(rs.getString("item_lob"));
				vasDto.setItemType(rs.getString("item_type"));
				vasDto.setItemMdoInd(rs.getString("item_mdo_ind"));
				vasDto.setItemHtml2(rs.getString("html2"));
				vasDto.setItemLocale(rs.getString("locale"));
				vasDto.setItemDisplayType(rs.getString("display_type"));
				vasDto.setItemOnetimeAmt(rs.getString("onetime_amt"));
				vasDto.setItemRecurrentAmt(rs.getString("recurrent_amt"));
				vasDto.setStockCount(rs.getString("STOCK_COUNT"));
				vasDto.setPosItemCd(rs.getString("POS_ITEM_CD"));
				return vasDto;
			}
		};
		
		try {
			vasDetailList = simpleJdbcTemplate.query(sql, mapper, params);
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException");
			vasDetailList = null;
		} catch (Exception e) {
			logger.error("Exception caught in getBundleList()", e);
			throw new DAOException(e.getMessage(), e);
		}
		return vasDetailList;
	}

	// MIP.P4 modification
	public List<VasDetailDTO> getPreviewSimList(String basketId, String locale, String appDate, String channelCd, String offerNature) throws DAOException {
		
		// MIP.P4 modification
		System.out.println("MIP.P4 modification: pkg_sb_mob_stock.get_stock_count_disp_mip4 (10)");
		
		List<VasDetailDTO> vasDetailList = new ArrayList<VasDetailDTO>();
		logger.info("getPreviewSimList is called");
		
		String sql = "SELECT BIA.ITEM_ID, " +
				"  BIA.DISPLAY_SEQ, " +
				"  BIA.MDO_IND, " +
				"  IDL.HTML , " +
				"  (SELECT A.SIM_TYPE " +
				"  FROM BOMWEB_STOCK_CATALOG A, " +
				"    W_ITEM_PRODUCT_POS_ASSGN IPPA " +
				"  WHERE A.ITEM_CODE = IPPA.POS_ITEM_CD " +
				"  AND IPPA.ITEM_ID  = BIA.ITEM_ID " +
				"  ) SIM_TYPE, " +
				"  (SELECT a.item_code " +
				"  FROM bomweb_stock_catalog a " +
				"  LEFT JOIN w_item_product_pos_assgn ippa " +
				"  ON a.item_code     = ippa.pos_item_cd " +
				"  WHERE ippa.item_id = bia.item_id " +
				"  ) pos_item_code, " +
				
				"  (SELECT pkg_sb_mob_stock.get_stock_count_disp_mip4('PEND', :channelCd, IPPA.POS_ITEM_CD, :offerNature) STOCK_COUNT " +
				
				"  FROM W_ITEM_PRODUCT_POS_ASSGN IPPA " +
				"  WHERE IPPA.ITEM_ID = BIA.ITEM_ID " +
				"  ) STOCK_COUNT " +
				"FROM W_BASKET_ITEM_ASSGN BIA, " +
				"  W_ITEM I, " +
				"  W_ITEM_DISPLAY_LKUP IDL " +
				"WHERE BIA.ITEM_ID    = I.ID " +
				"AND BIA.ITEM_ID      = IDL.ITEM_ID " +
				"AND IDL.LOCALE       = :locale " +
				"AND IDL.DISPLAY_TYPE = 'ITEM_SELECT' " +
				"AND I.TYPE           = 'SIM' " +
				"AND BIA.BASKET_ID    = :basketId " +
				"AND TO_DATE(:appDate, 'DD/MM/YYYY') BETWEEN BIA.EFF_START_DATE AND TRUNC(NVL(BIA.EFF_END_DATE, sysdate)) " +
				"ORDER BY BIA.DISPLAY_SEQ";
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("basketId", basketId);
		params.addValue("locale", locale);
		params.addValue("appDate", appDate);
		params.addValue("channelCd", channelCd);
		
		// MIP.P4 modification
		params.addValue("offerNature", offerNature);
		
		ParameterizedRowMapper<VasDetailDTO> mapper = new ParameterizedRowMapper<VasDetailDTO>() {
			public VasDetailDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				VasDetailDTO vasDto = new VasDetailDTO();
				vasDto.setItemHtml(rs.getString("html"));
				vasDto.setItemId(rs.getString("item_id"));
				vasDto.setItemMdoInd(rs.getString("mdo_ind"));
				vasDto.setStockCount(rs.getString("stock_count"));
				vasDto.setSimType(rs.getString("SIM_TYPE"));
				vasDto.setPosItemCd(rs.getString("pos_item_code"));
				return vasDto;
			}
		};
		
		try {
			vasDetailList = simpleJdbcTemplate.query(sql, mapper, params);
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException");
			vasDetailList = null;
		} catch (Exception e) {
			logger.error("Exception caught in getPreviewSimList()", e);
			throw new DAOException(e.getMessage(), e);
		}
		return vasDetailList;
	}

	// MIP.P4 modification
	public List<VasDetailDTO> getPreviewOptionList(String basketId, String locale, String appDate, String channelId, String channelCd, String offerNature) throws DAOException {
		
		// MIP.P4 modification
		System.out.println("MIP.P4 modification: pkg_sb_mob_stock.get_stock_count_disp_mip4 (11)");
		
		List<VasDetailDTO> vasDetailList = new ArrayList<VasDetailDTO>();
		logger.info("getPreviewOptionList is called");
		
		String sql = "SELECT ITEM_DISP.ITEM_ID, " +
				"  ITEM_DISP.ITEM_LOB, " +
				"  ITEM_DISP.ITEM_TYPE, " +
				"  ITEM_DISP.ITEM_MDO_IND, " +
				"  ITEM_DISP.HTML, " +
				"  ITEM_DISP.HTML2, " +
				"  ITEM_DISP.LOCALE, " +
				"  ITEM_DISP.DISPLAY_TYPE, " +
				"  ITEM_DISP.ONETIME_AMT, " +
				"  ITEM_DISP.RECURRENT_AMT, " +
				"  ITEM_DISP.ONETIME_TYPE, " +
				"  NVL(ITEM_DISP.CATEGORY_ID, 9999999) CATEGORY_ID, " +
				"  (SELECT DL.DESCRIPTION " +
				"  FROM W_DISPLAY_LKUP DL " +
				"  WHERE DL.TYPE = 'ITEM_CATEGORY' " +
				"  AND DL.LOCALE = :locale " +
				"  AND DL.ID     = NVL(ITEM_DISP.CATEGORY_ID, 9999999) " +
				"  ) CATEGORY_DESC, " +
				"  (SELECT MIN(IPPA.POS_ITEM_CD) " +
				"  FROM W_ITEM_PRODUCT_POS_ASSGN IPPA " +
				"  WHERE IPPA.ITEM_ID = ITEM_DISP.ITEM_ID " +
				"  ) POS_ITEM_CD, " +
				"  NVL(NVL( " +
				"  (SELECT MIN(IPRO.DISPLAY_SEQ) " +
				"  FROM W_ITEM_PRE_REQUISITE_OR IPRO, " +
				"    W_BASKET_ITEM_ASSGN BIA " +
				"  WHERE IPRO.REQUIRED_ITEM_ID = BIA.ITEM_ID " +
				"  AND BIA.BASKET_ID           = :basketId " +
				"  AND IPRO.ITEM_ID            = ITEM_DISP.ITEM_ID " +
				"  AND TO_DATE(:appDate, 'DD/MM/YYYY') BETWEEN IPRO.EFF_START_DATE AND NVL(IPRO.EFF_END_DATE, sysdate) " +
				"  ), " +
				"  (SELECT MIN(NVL(VIGA.DISPLAY_SEQ, 5)) " +
				"  FROM W_VAS_ITEM_GRP_ASSGN VIGA, " +
				"    W_CHANNEL_BASKET_ASSGN CBA " +
				"  WHERE CBA.CHANNEL_ID    = :channelId " +
				"  AND CBA.BASKET_ID       = :basketId " +
				"  AND CBA.VAS_ITEM_GRP_ID = VIGA.GRP_ID " +
				"  AND VIGA.ITEM_ID        = ITEM_DISP.ITEM_ID " +
				"  )),0) DISPLAY_SEQ, " +
				
				"  (SELECT pkg_sb_mob_stock.get_stock_count_disp_mip4('PEND', :channelCd, IPPA.POS_ITEM_CD, :offerNature) STOCK_COUNT " +
				
				"  FROM W_ITEM_PRODUCT_POS_ASSGN IPPA " +
				"  WHERE IPPA.ITEM_ID  = ITEM_DISP.ITEM_ID " +
				"  ) STOCK_COUNT " +
				"FROM " +
				"  (SELECT I.ID ITEM_ID, " +
				"    I.LOB ITEM_LOB, " +
				"    I.TYPE ITEM_TYPE, " +
				"    'O' ITEM_MDO_IND, " +
				"    IDL.HTML, " +
				"    IDL.HTML2, " +
				"    IDL.LOCALE, " +
				"    IDL.DISPLAY_TYPE, " +
				"    IP.ONETIME_TYPE, " +
				"    IP.ONETIME_AMT, " +
				"    IP.RECURRENT_AMT, " +
				"    I.CATEGORY_ID " +
				"  FROM W_ITEM I, " +
				"    W_ITEM_DISPLAY_LKUP IDL, " +
				"    W_ITEM_PRICING IP " +
				"  WHERE I.ID           = IDL.ITEM_ID " +
				"  AND IDL.LOCALE       = :locale " +
				"  AND IDL.DISPLAY_TYPE = 'ITEM_SELECT' " +
				"  AND I.ID             = IP.ID " +
				"  AND TO_DATE(:appDate, 'DD/MM/YYYY') BETWEEN IP.EFF_START_DATE AND NVL(IP.EFF_END_DATE, SYSDATE) " +
				"  ) ITEM_DISP, " +
				"  ( " +
				"  (SELECT VIGA.ITEM_ID " +
				"  FROM W_CHANNEL_BASKET_ASSGN CBA, " +
				"    W_VAS_ITEM_GRP_ASSGN VIGA " +
				"  WHERE CBA.VAS_ITEM_GRP_ID = VIGA.GRP_ID " +
				"  AND CBA.CHANNEL_ID        = :channelId " +
				"  AND SYSDATE BETWEEN VIGA.EFF_START_DATE AND NVL(VIGA.EFF_END_DATE, SYSDATE) " +
				"  AND CBA.BASKET_ID = :basketId " +
				"  UNION " +
				"  SELECT IPRO.ITEM_ID " +
				"  FROM W_CHANNEL_BASKET_ASSGN CBA, " +
				"    W_BASKET_ITEM_ASSGN BIA, " +
				"    W_ITEM_PRE_REQUISITE_OR IPRO " +
				"  WHERE BIA.ITEM_ID  = IPRO.REQUIRED_ITEM_ID " +
				"  AND CBA.CHANNEL_ID = :channelId " +
				"  AND TO_DATE(:appDate, 'DD/MM/YYYY') BETWEEN IPRO.EFF_START_DATE AND NVL(IPRO.EFF_END_DATE, SYSDATE) " +
				"  AND BIA.BASKET_ID = :basketId " +
				"  ) " +
				"MINUS " +
				"  (SELECT ITEM_ID_A " +
				"  FROM W_MOB_ITEM_EXCLUSIVE_LKUP A " +
				"  WHERE ITEM_ID_B IN " +
				"    (SELECT ITEM_ID " +
				"    FROM W_BASKET_ITEM_ASSGN BIA " +
				"    WHERE BIA.BASKET_ID = :basketId " +
				"    AND BIA.MDO_IND     = 'M' " +
				"    ) " +
				"  AND ITEM_TYPE_B IN ('BVAS', 'RP') " +
				"  AND ITEM_TYPE_A  = 'VAS' " +
				"  UNION " +
				"    (SELECT DISTINCT A.ITEM_ID " +
				"    FROM W_ITEM_PRE_REQUISITE_OR A " +
				"    WHERE TO_DATE(:appDate, 'DD/MM/YYYY') BETWEEN A.EFF_START_DATE AND NVL(A.EFF_END_DATE, SYSDATE) " +
				"    MINUS " +
				"    SELECT IPRO.ITEM_ID " +
				"    FROM W_ITEM_PRE_REQUISITE_OR IPRO, " +
				"      W_BASKET_ITEM_ASSGN BIA " +
				"    WHERE IPRO.REQUIRED_ITEM_ID = BIA.ITEM_ID " +
				"    AND TO_DATE(:appDate, 'DD/MM/YYYY') BETWEEN IPRO.EFF_START_DATE AND NVL(IPRO.EFF_END_DATE, SYSDATE) " +
				"    AND BIA.BASKET_ID = :basketId " +
				"    ) " +
				"  ) ) ITEM_LIST " +
				"WHERE ITEM_DISP.ITEM_ID = ITEM_LIST.ITEM_ID " +
				"ORDER BY DISPLAY_SEQ";
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("basketId", basketId);
		params.addValue("locale", locale);
		params.addValue("appDate", appDate);
		params.addValue("channelId", channelId);
		params.addValue("channelCd", channelCd);
		
		// MIP.P4 modification
		params.addValue("offerNature", offerNature);
		
		ParameterizedRowMapper<VasDetailDTO> mapper = new ParameterizedRowMapper<VasDetailDTO>() {
			public VasDetailDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				VasDetailDTO vasDto = new VasDetailDTO();
				vasDto.setItemHtml(rs.getString("html"));
				vasDto.setItemId(rs.getString("item_id"));
				vasDto.setItemLob(rs.getString("item_lob"));
				vasDto.setItemType(rs.getString("item_type"));
				vasDto.setItemMdoInd(rs.getString("item_mdo_ind"));
				vasDto.setItemHtml2(rs.getString("html2"));
				vasDto.setItemLocale(rs.getString("locale"));
				vasDto.setItemDisplayType(rs.getString("display_type"));
				vasDto.setItemOnetimeAmt(rs.getString("onetime_amt"));
				vasDto.setItemRecurrentAmt(rs.getString("recurrent_amt"));
				vasDto.setStockCount(rs.getString("STOCK_COUNT"));
				vasDto.setPosItemCd(rs.getString("POS_ITEM_CD"));
				vasDto.setCategoryDesc(rs.getString("CATEGORY_DESC"));
				vasDto.setCategoryId(rs.getString("CATEGORY_ID"));
				vasDto.setItemOneTimeType(rs.getString("ONETIME_TYPE"));
				return vasDto;
			}
		};
		
		try {
			vasDetailList = simpleJdbcTemplate.query(sql, mapper, params);
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException");
			vasDetailList = null;
		} catch (Exception e) {
			logger.error("Exception caught in getPreviewOptionList()", e);
			throw new DAOException(e.getMessage(), e);
		}
		return vasDetailList;
	}
}
