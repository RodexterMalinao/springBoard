package com.bomwebportal.lts.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.bomwebportal.dao.BaseDAO;
import com.bomwebportal.dto.LookupItemDTO;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.lts.dto.BasketCriteriaDTO;
import com.bomwebportal.lts.dto.BasketDetailDTO;
import com.bomwebportal.lts.dto.ret.RenewBasketPolicyDTO;
import com.bomwebportal.lts.util.LtsConstant;
import com.bomwebportal.lts.util.LtsDateFormatHelper;

public class BasketDetailDAO extends BaseDAO {
	
	private static final String SQL_GET_BASKET_ELIGIBLE_PSEF = 
		"select distinct eligible_subc_psef from w_basket_dtl_lts " +
			" where eligible_subc_psef is not null ";
	
	private static final String SQL_ALLOW_ARPU_CHANGE = 
			"select count(*) from w_lts_arpu_change_lkup " +
			" where UPPER(to_prod) = :toProd " +
			" and exist_arpu = :existArpu " +
			" and new_arpu = :newArpu ";
			
	private static final String SQL_GET_PROJECT_CD = 
			"select project_cd, project_desc " +
			" from w_project_code_lkup " +
			" where channel_cd in ('*', :channelCd) " +
			" and team_cd in ('*', :teamCd) " +
			" and order_type = :orderType " +
			" order by channel_cd desc, team_cd desc ";
	
	
	private static final String SQL_GET_CONTRACT_PERIOD = 
			"select contract_period " +
			"from w_basket_dtl_lts " +
			"where id = ?";
	
	private static final String SQL_GET_RENEWAL_POLICY = 
			"select new_tp_catg, extend_contract_period, earliest_srv_req_date, warning_msg " +
			"  from w_lts_tp_renewal_lkup" +
			"  where channel_id = :channelId " +
			"  and exist_tp_catg = :existTpCatg " +
			"  and :remainContractMth between lower_remain_mth and upper_remain_mth";
	
	
	private static final String SQL_SBPCD_ORDER_NOT_CANC_NOR_COMP = 
			"select count(*) " +
			"from bomweb_order bo " +
			"where bo.lob = 'IMS' " +
			"and bo.order_status not in " +
			"( " +
			"  SELECT wcl.code FROM " +
			"	w_code_lkup wcl WHERE (wcl.grp_id = 'SB_IMS_ACQ_CANC_LTS' or wcl.grp_id = 'SB_IMS_ACQ_COMPL_LTS') " +
			") " +
			"and bo.order_id = :orderId";

	
	private static final String SQL_GET_BASKET_PREMIER_IND =
		"select rtrim (xmlagg (xmlelement (e, type_desc || ', ')).extract ('//text()'), ' ') " + 
		" from w_basket_type " +
		" where basket_id = :basketId" +
		" and type = :type ";
	
	public List<String> getEligiblePsefList() throws DAOException{
		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
			public String mapRow(ResultSet rs, int rowNum)	
					throws SQLException {
				return rs.getString("eligible_subc_psef");
			}
		};
		
		try {
			return simpleJdbcTemplate.query(SQL_GET_BASKET_ELIGIBLE_PSEF, mapper);
		} catch (EmptyResultDataAccessException erdae) {
			return null;
		} catch (Exception e) {
			logger.error("Exception caught in getEligiblePsefList()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public String getAllowArpuChange(String toProd, String existArpu, String newArpu) throws DAOException {
		
		MapSqlParameterSource mapSqlSource = new MapSqlParameterSource();
		mapSqlSource.addValue("toProd", toProd.toUpperCase());
		mapSqlSource.addValue("newArpu", Double.parseDouble(newArpu));
		mapSqlSource.addValue("existArpu", Double.parseDouble(existArpu));
		
		try {
			return simpleJdbcTemplate.queryForObject(SQL_ALLOW_ARPU_CHANGE, String.class, mapSqlSource);
		} catch (EmptyResultDataAccessException erdae) {
			return null;
		} catch (Exception e) { 
			logger.error(ExceptionUtils.getFullStackTrace(e));
			throw new DAOException(e.getMessage(), e);
		}
		
	}

	public String checkSbpcdOrderNotCancNorComp(String orderId) throws DAOException {
		
		MapSqlParameterSource mapSqlSource = new MapSqlParameterSource();
		mapSqlSource.addValue("orderId", orderId);
		try {
			return simpleJdbcTemplate.queryForObject(SQL_SBPCD_ORDER_NOT_CANC_NOR_COMP, String.class, mapSqlSource);
		} catch (EmptyResultDataAccessException erdae) {
			return null;
		} catch (Exception e) { 
			logger.error(ExceptionUtils.getFullStackTrace(e));
			throw new DAOException(e.getMessage(), e);
		}
		
	}
	
	public List<RenewBasketPolicyDTO> getRenewBasketPolicyList(String basketChannelId, String existTpCatg, String newBasketCatg, String remainContractMth) 
			throws DAOException {
		
		ParameterizedRowMapper<RenewBasketPolicyDTO> mapper = new ParameterizedRowMapper<RenewBasketPolicyDTO>() {
			public RenewBasketPolicyDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				RenewBasketPolicyDTO renewBasketPolicy = new RenewBasketPolicyDTO();
				renewBasketPolicy.setNewBasketCatg(rs.getString("new_tp_catg"));
				renewBasketPolicy.setExtendContractPeriod(rs.getString("extend_contract_period"));
				renewBasketPolicy.setEarliestSrd(rs.getString("earliest_srv_req_date"));
				renewBasketPolicy.setWarningMsg(rs.getString("warning_msg"));
				return renewBasketPolicy;
			}
		};

		try {
			StringBuilder sql = new StringBuilder(SQL_GET_RENEWAL_POLICY);
			
			MapSqlParameterSource paramSource = new MapSqlParameterSource();
			paramSource.addValue("channelId", basketChannelId);
			paramSource.addValue("existTpCatg", existTpCatg);
			paramSource.addValue("remainContractMth", remainContractMth);
			
			
			if (StringUtils.isNotBlank(newBasketCatg)) {
				sql.append(" and new_tp_catg = :newTpCatg");
				paramSource.addValue("newTpCatg", newBasketCatg);	
			}
			
			if (logger.isDebugEnabled()) {
				logger.debug("getRenewBasketPolicyList() Sql: " + sql.toString());	
			}
			return simpleJdbcTemplate.getNamedParameterJdbcOperations().query(sql.toString(), paramSource, mapper);

		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException in getRenewBasketPolicyList()");
			return null;
		} catch (Exception e) {
			logger.info("Exception caught in getRenewBasketPolicyList():", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	
	public List<BasketDetailDTO> getAcqBasketList(String basketType, String paralleExtension, String locale, String deviceType,
			String basketCategory, String teamCode, String basketChannelId, String[] bridgingMth,
			boolean contractPeriodGt24, boolean contractPeriodEq18, boolean contractPeriodLt12, boolean staffPlan, String housingType, boolean is2NCoverage, 
			String[] filterProjectCds, String[] defaultProjectCds, String hktPremier, String pServiceBoundary, String pAreaCode, String pRole, String pOsType, String ltsHousingType) throws DAOException {
		
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		StringBuilder sql= new StringBuilder();
		
		sql.append("SELECT  /*+ LEADING (WBDL) */ ") 
	 		.append(" WB.ID, WCBA.CHANNEL_ID, WB.BASE_BASKET_ID, ")
		 	.append(" WB.TYPE, WBDLKUP.LOCALE, WBDLKUP.DESCRIPTION, ") 
			.append(" WBDLKUP.HTML, WBDLKUP.IMAGE_PATH, WBDLKUP.DISPLAY_TYPE, ")
			.append(" WBDL.IDD_FREE_MIN, WBDL.PE_IND, WBDL.BASKET_CATG, ")
			.append(" WBDL.CONTRACT_PERIOD, WBDL.BRIDGING_MTH, WBDL.DEVICE_TYPE, ")
			.append(" WBDL.STAFF_PLAN, WBDL.IS2N_COVERAGE, WBDL.PRE_PAY_IND, WBDL.PAY_MTD_TYPES, WBDL.EFFECTIVE_PRICE, ")
			.append(" WBDL.PCD_BUNDLE_FREE_TYPE, WBDL.PRE_SER_TERM_PERIOD, ")
			.append(" WBDL.PREINSTALL_CHECK, WBDL.DN_OPTION, WBDL.OS_TYPE, WBDL.PROGRAM_CD ")
			.append(" FROM")
			.append(" W_CHANNEL_BASKET_ASSGN WCBA,") 
			.append(" W_BASKET WB,") 
			.append(" W_BASKET_DTL_LTS WBDL,") 
			.append(" W_BASKET_DISPLAY_LKUP WBDLKUP,")
		    .append(" ( SELECT /*+ PUSH_PRED(WBIA) */ WBIA.BASKET_ID, WIP.RECURRENT_AMT") 
			.append(" FROM W_BASKET_ITEM_ASSGN WBIA,")
			.append(" W_ITEM WI, ")
			.append(" W_ITEM_PRICING WIP")
			.append(" WHERE WI.ID = WIP.ID")
			.append(" AND WBIA.ITEM_ID = WI.ID")
			.append(" AND WI.TYPE = 'PLAN'")
			.append(" AND WI.LOB = 'LTS') BP")
			.append(" WHERE WCBA.CHANNEL_ID = :channelId")
			.append(" AND WCBA.BASKET_ID = WB.ID")
			.append(" AND WB.ID = WBDL.ID ")
			.append(" AND WB.ID = WBDLKUP.BASKET_ID")
			.append(" AND WB.ID = BP.BASKET_ID")
			.append(" AND WBDLKUP.DISPLAY_TYPE = 'RP_PROMOT'")
			.append(" AND WBDLKUP.LOCALE = :locale")
			.append(" AND WCBA.EFF_START_DATE <= SYSDATE ")
			.append(" AND (WCBA.EFF_END_DATE IS NULL OR WCBA.EFF_END_DATE > SYSDATE )");
		
		paramSource.addValue("locale", StringUtils.isBlank(locale) ? "en" : locale);
		paramSource.addValue("channelId", basketChannelId);
		
		if (StringUtils.isNotBlank(basketType)) {
			sql.append(" AND WB.TYPE = :basketType");
			paramSource.addValue("basketType", basketType);
		}
		
		//JT2017062 - ACQ basket
		if (LtsConstant.CHANNEL_ID_SPRINGBOARD_ACQ_PT_DEL.equals(basketChannelId)
				|| LtsConstant.CHANNEL_ID_SPRINGBOARD_ACQ_PT_EYE.equals(basketChannelId)) {
			sql.append(" AND WB.ID IN ")
			.append(" ( SELECT DISTINCT BASKET_ID FROM W_BASKET_TYPE")
			.append("    WHERE TYPE = 'HOUSING'")
			.append("    AND TYPE_DESC in ( SELECT HOUSING_TYPE FROM W_TEAM_HOUSING_TYPE_ASSGN ")
			.append("        WHERE UPPER(HOUSING_TYPE) = UPPER(:hktPremier)  ");
			if (!(Arrays.asList(LtsConstant.SALES_ROLES_ALL_MANAGERS).contains(pRole))) {
			    sql.append("    AND UPPER(TEAM_CD) = UPPER(:teamCode) ");
			    paramSource.addValue("teamCode", teamCode);
			}
			sql.append("))");	
			paramSource.addValue("hktPremier", hktPremier);
		}
		
		if (StringUtils.isNotBlank(basketCategory)) {
			if (LtsConstant.BASKET_CATEGORY_HOT.equals(basketCategory)) {
				sql.append(" AND WBDL.HOT_BASKET_IND = 'Y'");	
			}
			else {
				sql.append(" AND WBDL.BASKET_CATG = :basketCategory");
				paramSource.addValue("basketCategory", basketCategory);
			}
		}
		
		if (StringUtils.isNotBlank(paralleExtension) && !"Y".equals(paralleExtension)) {
			sql.append(" AND WBDL.PE_IND = :paralleExtension");
			paramSource.addValue("paralleExtension", paralleExtension);	
		}
		
		if (StringUtils.isNotBlank(teamCode) || StringUtils.isNotBlank(pAreaCode)) {
			sql.append(" AND ((UPPER(WBDL.ELIGIBLE_TEAM) LIKE UPPER(:likeTeamCode) OR UPPER(WBDL.ELIGIBLE_TEAM) LIKE UPPER(:likeAreaCode)) OR WBDL.ELIGIBLE_TEAM IS NULL)");
			sql.append(" AND (( " +
					(StringUtils.isNotBlank(teamCode)?"UPPER(WBDL.NON_ELIGIBLE_TEAM) NOT LIKE UPPER(:likeTeamCode) AND ":"") +
					(StringUtils.isNotBlank(pAreaCode)?"UPPER(WBDL.NON_ELIGIBLE_TEAM) NOT LIKE UPPER(:likeAreaCode) AND ":"") +
					"WBDL.NON_ELIGIBLE_TEAM IS NOT NULL) OR WBDL.NON_ELIGIBLE_TEAM IS NULL)");
			paramSource.addValue("likeTeamCode", "%| " + teamCode + " |%");
			paramSource.addValue("likeAreaCode", "%| " + pAreaCode + " |%");
		}
		
		if (StringUtils.isNotBlank(deviceType)) {
			sql.append(" AND WBDL.DEVICE_TYPE like :deviceType");
			paramSource.addValue("deviceType", "%" + deviceType);
		}
		
		if(bridgingMth != null && bridgingMth.length > 0){
			boolean containZeroMth = false;
			for(int i = 0; i < bridgingMth.length; i++){
				if("0".equals(bridgingMth[i])){
					containZeroMth = true;
				}
			}
			
			if(containZeroMth){
				sql.append(" AND (WBDL.BRIDGING_MTH IN( ");
			}
			else{
				sql.append(" AND WBDL.BRIDGING_MTH IN( ");
			}
			
			for(int i = 0; i < bridgingMth.length; i++){
				if(i > 0) sql.append(", ");
				sql.append(":bridgingMth"+i);
				paramSource.addValue("bridgingMth"+i, bridgingMth[i]);
			}
			
			sql.append(" ) ");
			
			if(containZeroMth){
				sql.append(" OR WBDL.BRIDGING_MTH IS NULL) ");
			}
		}
		
		if (contractPeriodGt24 || contractPeriodEq18 || contractPeriodLt12) {
			sql.append(" AND (");
			if (contractPeriodGt24) {
				sql.append(" WBDL.CONTRACT_PERIOD >= 24 ");	
			}
			if (contractPeriodEq18) {
				if (contractPeriodGt24) {
					sql.append(" OR ");
				}
				sql.append(" (WBDL.CONTRACT_PERIOD > 12 AND WBDL.CONTRACT_PERIOD < 24 ) ");	
			}
			if (contractPeriodLt12) {
				if (contractPeriodGt24 || contractPeriodEq18) {
					sql.append(" OR ");
				}
				sql.append(" WBDL.CONTRACT_PERIOD <= 12 ");	
			}
			sql.append(" )");
		}
		else {
			sql.append(" AND WBDL.CONTRACT_PERIOD IS NULL");
		}
		
		if(staffPlan){
			sql.append(" AND WBDL.STAFF_PLAN = 'Y' ");
		}
		else{
			sql.append(" AND (WBDL.STAFF_PLAN IS NULL OR WBDL.STAFF_PLAN = 'N')");
		}
		
/*		if(StringUtils.isNotBlank(housingType)){
		sql.append(" AND (WBDL.ELIGIBLE_HOUSING_TYPE is null OR WBDL.ELIGIBLE_HOUSING_TYPE = :housingType)");
		paramSource.addValue("housingType", housingType.toUpperCase());
		}
*/
		
		if(is2NCoverage){
			sql.append(" AND (WBDL.IS2N_COVERAGE is null OR WBDL.IS2N_COVERAGE = 'Y')");
		}
		else{
			sql.append(" AND (WBDL.IS2N_COVERAGE is null OR WBDL.IS2N_COVERAGE = 'N')");
		}		

		if (ArrayUtils.isNotEmpty(filterProjectCds)) {
			sql.append(" AND WBDL.PROJECT_CD IN ( :projectCds ) ");
			paramSource.addValue("projectCds", Arrays.asList(filterProjectCds));
		}
		else if (ArrayUtils.isNotEmpty(defaultProjectCds)) {
			sql.append(" AND ( WBDL.PROJECT_CD IN ( :projectCds ) OR ( WBDL.PROJECT_CD IS NULL AND WBDL.EXTEND_CONTRACT_PERIOD = :extendContractMth ) ) ");
			paramSource.addValue("projectCds", Arrays.asList(defaultProjectCds));
			paramSource.addValue("extendContractMth", "0");
		}
		else {
			sql.append(" AND WBDL.PROJECT_CD IS NULL ");
			sql.append(" AND (WBDL.EXTEND_CONTRACT_PERIOD = :extendContractMth OR WBDL.EXTEND_CONTRACT_PERIOD IS NULL)");
			paramSource.addValue("extendContractMth", "0");
		}	
		
		if(StringUtils.isNotBlank(pServiceBoundary)){
			sql.append(" AND (WBDL.ELIGIBLE_HOUSING_TYPE in (select HOUSING_TYPE from W_LTS_HOUSING_SRV_BOUNDARY where SERVICE_BOUNDARY = :srvBoundary) ");
			sql.append(" 		OR WBDL.ELIGIBLE_HOUSING_TYPE IS NULL)");
			paramSource.addValue("srvBoundary", pServiceBoundary);
		}

		//TODO LTS HOUSING CAT fix, 05-07-2019
		if(StringUtils.isNotBlank(ltsHousingType) && StringUtils.isNotBlank(housingType)){
			sql.append(" AND (");
			sql.append("     (WBDL.LTS_HOUSING_CAT LIKE :likeLtsHousingType AND WBDL.LTS_HOUSING_CAT IS NOT NULL) ");
			sql.append("     OR (WBDL.LTS_HOUSING_CAT IS NULL AND (WBDL.HOUSING_TYPE LIKE :likeImsHousingType OR WBDL.HOUSING_TYPE IS NULL) ");
			sql.append("       ) ");
			sql.append(" ) ");
			paramSource.addValue("likeLtsHousingType", "%|" + ltsHousingType + "|%");
			paramSource.addValue("likeImsHousingType", "%" + housingType + "%");
		}else if(StringUtils.isNotBlank(ltsHousingType)){
			sql.append(" AND (WBDL.LTS_HOUSING_CAT LIKE :likeLtsHousingType OR WBDL.LTS_HOUSING_CAT IS NULL) ");
			paramSource.addValue("likeLtsHousingType", "%|" + ltsHousingType + "|%");
		}else if(StringUtils.isNotBlank(housingType)){
			sql.append(" AND (WBDL.HOUSING_TYPE LIKE :likeImsHousingType OR WBDL.HOUSING_TYPE IS NULL) ");
			paramSource.addValue("likeImsHousingType", "%" + housingType + "%");
		}else{
			sql.append(" AND WBDL.LTS_HOUSING_CAT IS NULL AND WBDL.HOUSING_TYPE IS NULL ");
		}
		//
		
		if(StringUtils.isNotBlank(pOsType)){
			if(StringUtils.equals(LtsConstant.OS_TYPE_AND, pOsType)){
				sql.append(" AND (WBDL.OS_TYPE = :osType or WBDL.OS_TYPE is null) ");
				paramSource.addValue("osType", pOsType);
			}
			if(StringUtils.equals(LtsConstant.OS_TYPE_IOS, pOsType)){
				sql.append(" AND WBDL.OS_TYPE = :osType ");
				paramSource.addValue("osType", pOsType);
			}
		}
		
		sql.append(" ORDER BY WBDL.PE_IND DESC, WBDL.CONTRACT_PERIOD DESC, BP.RECURRENT_AMT DESC, WBDL.IDD_FREE_MIN DESC");
		
		ParameterizedRowMapper<BasketDetailDTO> mapper = new ParameterizedRowMapper<BasketDetailDTO>() {
			public BasketDetailDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				BasketDetailDTO basketDetail = new BasketDetailDTO();
				basketDetail.setBasketId(rs.getString("ID"));
				basketDetail.setType(rs.getString("TYPE"));
				basketDetail.setLocale(rs.getString("LOCALE"));
				basketDetail.setDesc(rs.getString("DESCRIPTION"));
				basketDetail.setHtml(rs.getString("HTML"));
				basketDetail.setImagePath(rs.getString("IMAGE_PATH"));
				basketDetail.setDisplayType(rs.getString("DISPLAY_TYPE"));
				basketDetail.setIddFreeMin(rs.getString("IDD_FREE_MIN"));
				basketDetail.setPeInd(rs.getString("PE_IND"));
				basketDetail.setContractPeriod(rs.getString("CONTRACT_PERIOD"));
				basketDetail.setBridgingMth(rs.getString("BRIDGING_MTH"));
				basketDetail.setDeviceType(rs.getString("DEVICE_TYPE"));
				basketDetail.setBasketCatg(rs.getString("BASKET_CATG"));
				basketDetail.setBasketChannelId(rs.getString("CHANNEL_ID"));
				basketDetail.setStaffPlan(rs.getString("STAFF_PLAN"));
				basketDetail.setStaffPlan(rs.getString("IS2N_COVERAGE"));
				basketDetail.setStaffPlan(rs.getString("PRE_PAY_IND"));
				basketDetail.setPayMtdTypes(rs.getString("PAY_MTD_TYPES"));
				basketDetail.setEffectivePrice(rs.getString("EFFECTIVE_PRICE"));
				basketDetail.setBaseBasketId(rs.getString("BASE_BASKET_ID"));
				basketDetail.setPrevSerTermMth(rs.getString("PRE_SER_TERM_PERIOD"));
				basketDetail.setPcdBundleFreeType(rs.getString("PCD_BUNDLE_FREE_TYPE"));
				basketDetail.setPreinstallCheck(rs.getString("PREINSTALL_CHECK"));
				basketDetail.setDnOption(rs.getString("DN_OPTION"));
				basketDetail.setOsType(rs.getString("OS_TYPE"));
				basketDetail.setProgramCd(rs.getString("PROGRAM_CD"));
				return basketDetail;
			}
		};

		try {
			if (logger.isDebugEnabled()) {
				logger.debug("getAcqBasketList() Sql: " + sql.toString());	
			}
			return simpleJdbcTemplate.getNamedParameterJdbcOperations().query(sql.toString(), paramSource, mapper);

		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException in getAcqBasketList()");
			return null;
		} catch (Exception e) {
			logger.info("Exception caught in getAcqBasketList():", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public List<BasketDetailDTO> getStandaloneVasDummyBasket(BasketCriteriaDTO basketCriteria) throws DAOException{

		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		StringBuilder sql= new StringBuilder();
		
		sql.append("SELECT  /*+ LEADING (WBDL) */ ") 
		 	.append(" WB.ID, WCBA.CHANNEL_ID, ")
		 	.append(" WB.TYPE, WBDLKUP.LOCALE, WBDLKUP.DESCRIPTION,") 
			.append(" WBDLKUP.HTML, WBDLKUP.IMAGE_PATH, WBDLKUP.DISPLAY_TYPE, ")
			.append(" WBDL.IDD_FREE_MIN, WBDL.PE_IND, WBDL.BASKET_CATG, WBDL.DUPLEX_IND, ")
			.append(" WBDL.CONTRACT_PERIOD, WBDL.BUNDLE2G_IND, WBDL.DEVICE_TYPE,")
			.append(" WBDL.PREINSTALL_CHECK, WBDL.DN_OPTION, WBDL.EXTEND_CONTRACT_PERIOD, WBDL.PROGRAM_CD ")
			.append(" FROM")
			.append(" W_CHANNEL_BASKET_ASSGN WCBA,") 
			.append(" W_BASKET WB,") 
			.append(" W_BASKET_DTL_LTS WBDL,") 
			.append(" W_BASKET_DISPLAY_LKUP WBDLKUP,")
		    .append(" ( SELECT /*+ PUSH_PRED(WBIA) */  WBIA.BASKET_ID, WIP.RECURRENT_AMT") 
			.append(" FROM W_BASKET_ITEM_ASSGN WBIA,")
			.append(" W_ITEM WI, ")
			.append(" W_ITEM_PRICING WIP")
			.append(" WHERE WI.ID = WIP.ID")
			.append(" AND WBIA.ITEM_ID = WI.ID")
			.append(" AND WI.TYPE = 'PLAN'")
			.append(" AND WI.LOB = 'LTS') BP")
			.append(" WHERE WCBA.CHANNEL_ID = :channelId")
			.append(" AND WCBA.BASKET_ID = WB.ID")
			.append(" AND WB.ID = WBDL.ID ")
			.append(" AND WB.ID = WBDLKUP.BASKET_ID")
			.append(" AND WB.ID = BP.BASKET_ID")
			.append(" AND WBDLKUP.DISPLAY_TYPE = 'RP_PROMOT'")
			.append(" AND WBDLKUP.LOCALE = :locale")
			.append(" AND WCBA.EFF_START_DATE <= to_date(:applicationDate , 'DD/MM/YYYY') ")
			.append(" AND (WCBA.EFF_END_DATE IS NULL OR WCBA.EFF_END_DATE > SYSDATE )");
		
		paramSource.addValue("locale", StringUtils.isBlank(basketCriteria.getLocale()) ? "en" : basketCriteria.getLocale());
		paramSource.addValue("channelId", basketCriteria.getBasketChannelId());
		paramSource.addValue("applicationDate", StringUtils.defaultIfEmpty(basketCriteria.getApplicationDate(), LtsDateFormatHelper.getSysDate("dd/MM/yyyy")));
		
		ParameterizedRowMapper<BasketDetailDTO> mapper = new ParameterizedRowMapper<BasketDetailDTO>() {
			public BasketDetailDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				BasketDetailDTO basketDetail = new BasketDetailDTO();
				basketDetail.setBasketId(rs.getString("ID"));
				basketDetail.setType(rs.getString("TYPE"));
				basketDetail.setLocale(rs.getString("LOCALE"));
				basketDetail.setDesc(rs.getString("DESCRIPTION"));
				basketDetail.setHtml(rs.getString("HTML"));
				basketDetail.setImagePath(rs.getString("IMAGE_PATH"));
				basketDetail.setDisplayType(rs.getString("DISPLAY_TYPE"));
				basketDetail.setIddFreeMin(rs.getString("IDD_FREE_MIN"));
				basketDetail.setPeInd(rs.getString("PE_IND"));
				basketDetail.setContractPeriod(rs.getString("CONTRACT_PERIOD"));
				basketDetail.setDeviceType(rs.getString("DEVICE_TYPE"));
				basketDetail.setBasketCatg(rs.getString("BASKET_CATG"));
				basketDetail.setBundle2G(rs.getString("BUNDLE2G_IND"));
				basketDetail.setBasketChannelId(rs.getString("CHANNEL_ID"));
				basketDetail.setDuplexInd(rs.getString("DUPLEX_IND"));
				basketDetail.setPreinstallCheck(rs.getString("PREINSTALL_CHECK"));
				basketDetail.setDnOption(rs.getString("DN_OPTION"));
				basketDetail.setExtendContractPeriod(rs.getString("EXTEND_CONTRACT_PERIOD"));
				basketDetail.setProgramCd(rs.getString("PROGRAM_CD"));
				return basketDetail;
			}
		};

		try {
			if (logger.isDebugEnabled()) {
				logger.debug("getStandaloneVasDummyBasket() Sql: " + sql.toString());	
			}
			basketCriteria.setRelatedSql(sql.toString());
			return simpleJdbcTemplate.getNamedParameterJdbcOperations().query(sql.toString(), paramSource, mapper);
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException in getStandaloneVasDummyBasket()");
			return null;
		}catch (Exception e) {
			logger.info("Exception caught in getStandaloneVasDummyBasket():", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public List<BasketDetailDTO> getRetBasketList(BasketCriteriaDTO basketCriteria) throws DAOException {
		
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		
		StringBuilder sql= new StringBuilder();
		
		sql.append("SELECT  /*+ LEADING (WBDL) */ ") 
		 	.append(" WB.ID, WCBA.CHANNEL_ID, ")
		 	.append(" WB.TYPE, WBDLKUP.LOCALE, WBDLKUP.DESCRIPTION,") 
			.append(" WBDLKUP.HTML, WBDLKUP.IMAGE_PATH, WBDLKUP.DISPLAY_TYPE, ")
			.append(" WBDL.IDD_FREE_MIN, WBDL.PE_IND, WBDL.BASKET_CATG, WBDL.DUPLEX_IND, ")
			.append(" WBDL.CONTRACT_PERIOD, WBDL.BUNDLE2G_IND, WBDL.DEVICE_TYPE, WBDL.HOT_BASKET_IND, ")
			.append(" WBDL.PREINSTALL_CHECK, WBDL.DN_OPTION, WBDL.EXTEND_CONTRACT_PERIOD, WBDL.OS_TYPE, WBDL.PROGRAM_CD ")
			.append(" FROM")
			.append(" W_CHANNEL_BASKET_ASSGN WCBA,") 
			.append(" W_BASKET WB,") 
			.append(" W_BASKET_DTL_LTS WBDL,") 
			.append(" W_BASKET_DISPLAY_LKUP WBDLKUP,")
		    .append(" ( SELECT /*+ PUSH_PRED(WBIA) */  WBIA.BASKET_ID, WIP.RECURRENT_AMT") 
			.append(" FROM W_BASKET_ITEM_ASSGN WBIA,")
			.append(" W_ITEM WI, ")
			.append(" W_ITEM_PRICING WIP")
			.append(" WHERE WI.ID = WIP.ID")
			.append(" AND WBIA.ITEM_ID = WI.ID")
			.append(" AND WI.TYPE = 'PLAN'")
			.append(" AND WI.LOB = 'LTS') BP")
			.append(" WHERE WCBA.CHANNEL_ID = :channelId")
			.append(" AND WCBA.BASKET_ID = WB.ID")
			.append(" AND WB.ID = WBDL.ID ")
			.append(" AND WB.ID = WBDLKUP.BASKET_ID")
			.append(" AND WB.ID = BP.BASKET_ID")
			.append(" AND WBDLKUP.DISPLAY_TYPE = 'RP_PROMOT'")
			.append(" AND WBDLKUP.LOCALE = :locale")
			.append(" AND WCBA.EFF_START_DATE <= to_date(:applicationDate , 'DD/MM/YYYY') ")
			.append(" AND (WCBA.EFF_END_DATE IS NULL OR WCBA.EFF_END_DATE > SYSDATE )");
			
		
		paramSource.addValue("locale", StringUtils.isBlank(basketCriteria.getLocale()) ? "en" : basketCriteria.getLocale());
		paramSource.addValue("channelId", basketCriteria.getBasketChannelId());
		paramSource.addValue("applicationDate", StringUtils.defaultIfEmpty(basketCriteria.getApplicationDate(), LtsDateFormatHelper.getSysDate("dd/MM/yyyy")));
		
		if (StringUtils.isNotBlank(basketCriteria.getBasketType())) {
			sql.append(" AND WB.TYPE = :basketType");
			paramSource.addValue("basketType", basketCriteria.getBasketType());
		}
		
		//JT2017062 - Retention basket
		if (LtsConstant.CHANNEL_ID_SPRINGBOARD_RET_PT_DEL.equals(basketCriteria.getBasketChannelId())
				|| LtsConstant.CHANNEL_ID_SPRINGBOARD_RET_PT_EYE.equals(basketCriteria.getBasketChannelId())) {
			sql.append(" AND WB.ID IN ")
			.append(" ( SELECT DISTINCT BASKET_ID FROM W_BASKET_TYPE")
			.append("    WHERE TYPE = 'HOUSING'")
			.append("    AND TYPE_DESC IN ( ")
			.append("      SELECT HOUSING_TYPE ")
			.append("        FROM W_TEAM_HOUSING_TYPE_ASSGN ")
			.append("        WHERE UPPER(HOUSING_TYPE) = UPPER(:hktPremier)  ");
			if (!(Arrays.asList(LtsConstant.SALES_ROLES_ALL_MANAGERS).contains(basketCriteria.getRole()))) {
			    sql.append("    AND UPPER(TEAM_CD) = UPPER(:teamCode) ");
			    paramSource.addValue("teamCode", basketCriteria.getTeamCode());
			}
			sql.append("))");	
			paramSource.addValue("hktPremier", basketCriteria.getHktPremier());
		}

		//TODO LTS HOUSING CAT fix, 05-07-2019
		if(StringUtils.isNotBlank(basketCriteria.getLtsHousingType()) && StringUtils.isNotBlank(basketCriteria.getImsHousingType())){
			sql.append(" AND (");
			sql.append("     (WBDL.LTS_HOUSING_CAT LIKE :likeLtsHousingType AND WBDL.LTS_HOUSING_CAT IS NOT NULL) ");
			sql.append("     OR (WBDL.LTS_HOUSING_CAT IS NULL AND (WBDL.HOUSING_TYPE LIKE :likeImsHousingType OR WBDL.HOUSING_TYPE IS NULL) ");
			sql.append("       ) ");
			sql.append(" ) ");
			paramSource.addValue("likeLtsHousingType", "%|" + basketCriteria.getLtsHousingType() + "|%");
			paramSource.addValue("likeImsHousingType", "%" + basketCriteria.getImsHousingType() + "%");
		}else if(StringUtils.isNotBlank(basketCriteria.getLtsHousingType())){
			sql.append(" AND (WBDL.LTS_HOUSING_CAT LIKE :likeLtsHousingType OR WBDL.LTS_HOUSING_CAT IS NULL) ");
			paramSource.addValue("likeLtsHousingType", "%|" + basketCriteria.getLtsHousingType() + "|%");
		}else if(StringUtils.isNotBlank(basketCriteria.getImsHousingType())){
			sql.append(" AND (WBDL.HOUSING_TYPE LIKE :likeImsHousingType OR WBDL.HOUSING_TYPE IS NULL) ");
			paramSource.addValue("likeImsHousingType", "%" + basketCriteria.getImsHousingType() + "%");
		}else{
			sql.append(" AND WBDL.LTS_HOUSING_CAT IS NULL AND WBDL.HOUSING_TYPE IS NULL ");
		}
		//
		
		if (StringUtils.isBlank(basketCriteria.getIddFreeMin())) {
			sql.append(" AND WBDL.IDD_FREE_MIN IS NULL");
		}
		else {
			sql.append(" AND (WBDL.IDD_FREE_MIN <= :iddFreeMin OR WBDL.IDD_FREE_MIN IS NULL)");
			paramSource.addValue("iddFreeMin", basketCriteria.getIddFreeMin());
		}
		
//		if (StringUtils.isNotBlank(basketCriteria.getBasketCategory())) {
//				sql.append(" AND WBDL.BASKET_CATG = :basketCategory");
//				paramSource.addValue("basketCategory", basketCriteria.getBasketCategory());
//		}
//		
//		if (basketCriteria.isHotBasketInd()) {
//			sql.append(" AND WBDL.HOT_BASKET_IND = 'Y'");	
//		}
//		else {
//			sql.append(" AND (WBDL.HOT_BASKET_IND = 'Y' OR WBDL.HOT_BASKET_IND = 'N' OR WBDL.HOT_BASKET_IND IS NULL) ");
//		}
		
		if (!StringUtils.equals("Y", basketCriteria.getBundle2G())) {
			sql.append(" AND WBDL.BUNDLE2G_IND IS NULL ");
		}
		
		sql.append(" AND (WBDL.ELIGIBLE_TEAM IS NULL ");
		if (StringUtils.isNotBlank(basketCriteria.getAreaCode())) {
			sql.append(" OR UPPER(WBDL.ELIGIBLE_TEAM) LIKE UPPER(:likeAreaCode) "); 
			paramSource.addValue("likeAreaCode", "%| " + basketCriteria.getAreaCode() + " |%");
		}
		if (StringUtils.isNotBlank(basketCriteria.getTeamCode())) {
			sql.append(" OR UPPER(WBDL.ELIGIBLE_TEAM) LIKE UPPER(:likeTeamCode) "); 
			paramSource.addValue("likeTeamCode", "%| " + basketCriteria.getTeamCode() + " |%");
		}
		sql.append(" ) ");
				
		sql.append(" AND (WBDL.NON_ELIGIBLE_TEAM IS NULL ");
		if (StringUtils.isNotBlank(basketCriteria.getAreaCode()) || StringUtils.isNotBlank(basketCriteria.getTeamCode())) {
			sql.append(" OR (WBDL.NON_ELIGIBLE_TEAM IS NOT NULL ");
			if (StringUtils.isNotBlank(basketCriteria.getAreaCode())) {
				sql.append(" AND UPPER(WBDL.NON_ELIGIBLE_TEAM) NOT LIKE UPPER(:likeAreaCode) "); 
				paramSource.addValue("likeAreaCode", "%| " + basketCriteria.getAreaCode() + " |%");
			}
			if (StringUtils.isNotBlank(basketCriteria.getTeamCode())) {
				sql.append(" AND UPPER(WBDL.NON_ELIGIBLE_TEAM) NOT LIKE UPPER(:likeTeamCode) "); 
				paramSource.addValue("likeTeamCode", "%| " + basketCriteria.getTeamCode() + " |%");
			}
			sql.append(" ) ");
		}
		sql.append(" ) ");
				
		if (StringUtils.isNotBlank(basketCriteria.getDeviceType())) {
			sql.append(" AND WBDL.DEVICE_TYPE = :deviceType");
			paramSource.addValue("deviceType", basketCriteria.getDeviceType());
		}
		else {
			sql.append(" AND WBDL.DEVICE_TYPE IS NULL");
		}
		
		if (StringUtils.isNotBlank(basketCriteria.getCustCampaignCd())
				&& (LtsConstant.CHANNEL_ID_SPRINGBOARD_RET_MASS_DEL.equals(basketCriteria.getBasketChannelId()) 
						|| LtsConstant.CHANNEL_ID_SPRINGBOARD_RET_MASS_EYE.equals(basketCriteria.getBasketChannelId()))) {
			sql.append(" AND ( wbdl.customer_campaign_cd IS NULL OR UPPER(WBDL.CUSTOMER_CAMPAIGN_CD) LIKE UPPER(:custCampaignCd) ) ");
			paramSource.addValue("custCampaignCd", "%" + basketCriteria.getCustCampaignCd() + "%");
		}
		
		if (ArrayUtils.isNotEmpty(basketCriteria.getBasketCampaignCds())) {
			sql.append(" AND ( WBDL.cust_dedicated_campaign_cd IN (:basketCampaignCds) AND WBDL.cust_dedicated_campaign_cd IS NULL )");
			paramSource.addValue("basketCampaignCds", basketCriteria.getBasketCampaignCds());
		}
		
		if (StringUtils.isNotEmpty(basketCriteria.getTpExpiredMonths())) {
			sql.append(" AND (:tpExpiredMonths BETWEEN WBDL.UPPER_TP_EXPIRE_MTH AND WBDL.LOWER_TP_EXPIRE_MTH OR (WBDL.UPPER_TP_EXPIRE_MTH IS NULL AND WBDL.LOWER_TP_EXPIRE_MTH IS NULL) )  ");
			paramSource.addValue("tpExpiredMonths", basketCriteria.getTpExpiredMonths());
		}
		
		if (!basketCriteria.isDuplexProfile() ) {
			sql.append(" AND (WBDL.DUPLEX_IND = 'N' OR WBDL.DUPLEX_IND IS NULL)");
		}

		if (!LtsConstant.DEVICE_TYPE_DEL.equals(basketCriteria.getBasketType())) {
			sql.append(" AND WBDL.PE_IND = :paralleExtension");
			paramSource.addValue("paralleExtension", basketCriteria.getParalleExtension());
		}
		
		if (!basketCriteria.isNonNowTvCust()) {
			sql.append(" AND (WBDL.NON_NOWTV_CUST_ONLY = 'N' OR WBDL.NON_NOWTV_CUST_ONLY IS NULL)");
		}
		
		if (!basketCriteria.isSubmitDisForm()) {
			sql.append(" AND WBDL.D_FORM_IND IS NULL");
		}
		
		if (basketCriteria.isContractPeriodGt24()
				|| basketCriteria.isContractPeriodEq21()
				|| basketCriteria.isContractPeriodEq18()
				|| basketCriteria.isContractPeriodEq15()
				|| basketCriteria.isContractPeriodLt12()) {
			sql.append(" AND (");
			if (basketCriteria.isContractPeriodGt24()) {
				sql.append(" WBDL.CONTRACT_PERIOD >= 24 ");	
			}
			if (basketCriteria.isContractPeriodEq21()) {
				if (basketCriteria.isContractPeriodGt24()) {
					sql.append(" OR ");
				}
				sql.append(" WBDL.CONTRACT_PERIOD BETWEEN 18.1 AND 23.9 ");	
			}
			if (basketCriteria.isContractPeriodEq18()) {
				if (basketCriteria.isContractPeriodGt24() || basketCriteria.isContractPeriodEq21()) {
					sql.append(" OR ");
				}
				sql.append(" WBDL.CONTRACT_PERIOD = 18 ");	
			}
			if (basketCriteria.isContractPeriodEq15()) {
				if (basketCriteria.isContractPeriodGt24() || basketCriteria.isContractPeriodEq21() || basketCriteria.isContractPeriodEq18()) {
					sql.append(" OR ");
				}
				sql.append(" WBDL.CONTRACT_PERIOD BETWEEN 12.1 AND 17.9 ");	
			}
			if (basketCriteria.isContractPeriodLt12()) {
				if (basketCriteria.isContractPeriodGt24() || basketCriteria.isContractPeriodEq21() || basketCriteria.isContractPeriodEq18() || basketCriteria.isContractPeriodEq15()) {
					sql.append(" OR ");
				}
				sql.append(" WBDL.CONTRACT_PERIOD <= 12 ");	
			}
			sql.append(" )");
		}
		else {
			sql.append(" AND WBDL.CONTRACT_PERIOD IS NULL");
		}
		

		if (ArrayUtils.isNotEmpty(basketCriteria.getFilterProjectCds())) {
			sql.append(" AND WBDL.PROJECT_CD IN ( :projectCds ) ");
			paramSource.addValue("projectCds", Arrays.asList(basketCriteria.getFilterProjectCds()));
		}
		else if (ArrayUtils.isNotEmpty(basketCriteria.getDefaultProjectCds())) {
			sql.append(" AND ( WBDL.PROJECT_CD IN ( :projectCds ) OR WBDL.PROJECT_CD IS NULL ) ");
			paramSource.addValue("projectCds", Arrays.asList(basketCriteria.getDefaultProjectCds()));
		}
		else {
			sql.append(" AND WBDL.PROJECT_CD IS NULL ");
		}
		
		if (ArrayUtils.isNotEmpty(basketCriteria.getProfilePsefCds())) {
			sql.append(" AND ( ");
			
			for (String profilePsefCd : basketCriteria.getProfilePsefCds()) {
				sql.append(" WBDL.eligible_subc_psef like '%").append(profilePsefCd).append("%' OR ");	
			}
			sql.append(" WBDL.eligible_subc_psef IS NULL ) ");
		}
		else {
			sql.append(" AND WBDL.eligible_subc_psef IS NULL ");
		}
		
		if (StringUtils.isNotEmpty(basketCriteria.getBundleTvCredit())) {
			sql.append(" AND (WBDL.bundle_tv_credit IS NULL OR WBDL.bundle_tv_credit = :bundleTvCredit )");
			paramSource.addValue("bundleTvCredit", basketCriteria.getExtendContractMth());
		}
		
		if (StringUtils.isNotEmpty(basketCriteria.getProfileArpu())) {
			sql.append(" AND (:profileArpu BETWEEN WBDL.LOWER_ARPU AND WBDL.UPPER_ARPU OR (WBDL.UPPER_ARPU IS NULL AND WBDL.LOWER_ARPU IS NULL) )  ");
			paramSource.addValue("profileArpu", basketCriteria.getProfileArpu());
		}
		else {
			sql.append(" AND WBDL.UPPER_ARPU IS NULL AND WBDL.LOWER_ARPU IS NULL ");
		}
		
		if (StringUtils.isNotEmpty(basketCriteria.getOsType())) {
			if(LtsConstant.OS_TYPE_AND.equals(basketCriteria.getOsType())){
				sql.append(" AND (WBDL.OS_TYPE IS NULL OR WBDL.OS_TYPE = :osType ) ");
				paramSource.addValue("osType", basketCriteria.getOsType());
			}
			if(LtsConstant.OS_TYPE_IOS.equals(basketCriteria.getOsType())){
				sql.append(" AND (WBDL.OS_TYPE = :osType ) ");
				paramSource.addValue("osType", basketCriteria.getOsType());
			}
		}
		
		sql.append(" ORDER BY WBDL.PE_IND DESC, WBDL.CONTRACT_PERIOD DESC, BP.RECURRENT_AMT DESC, WBDL.IDD_FREE_MIN DESC, WBDL.ID");
		
		ParameterizedRowMapper<BasketDetailDTO> mapper = new ParameterizedRowMapper<BasketDetailDTO>() {
			public BasketDetailDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				BasketDetailDTO basketDetail = new BasketDetailDTO();
				basketDetail.setBasketId(rs.getString("ID"));
				basketDetail.setType(rs.getString("TYPE"));
				basketDetail.setLocale(rs.getString("LOCALE"));
				basketDetail.setDesc(rs.getString("DESCRIPTION"));
				basketDetail.setHtml(rs.getString("HTML"));
				basketDetail.setImagePath(rs.getString("IMAGE_PATH"));
				basketDetail.setDisplayType(rs.getString("DISPLAY_TYPE"));
				basketDetail.setIddFreeMin(rs.getString("IDD_FREE_MIN"));
				basketDetail.setPeInd(rs.getString("PE_IND"));
				basketDetail.setContractPeriod(rs.getString("CONTRACT_PERIOD"));
				basketDetail.setDeviceType(rs.getString("DEVICE_TYPE"));
				basketDetail.setBasketCatg(rs.getString("BASKET_CATG"));
				basketDetail.setBundle2G(rs.getString("BUNDLE2G_IND"));
				basketDetail.setBasketChannelId(rs.getString("CHANNEL_ID"));
				basketDetail.setDuplexInd(rs.getString("DUPLEX_IND"));
				basketDetail.setHotBasketInd(rs.getString("HOT_BASKET_IND"));
				basketDetail.setExtendContractPeriod(rs.getString("EXTEND_CONTRACT_PERIOD"));
				basketDetail.setPreinstallCheck(rs.getString("PREINSTALL_CHECK"));
				basketDetail.setDnOption(rs.getString("DN_OPTION"));
				basketDetail.setOsType(rs.getString("OS_TYPE"));
				basketDetail.setProgramCd(rs.getString("PROGRAM_CD"));
				return basketDetail;
			}
		};

		try {
			if (logger.isDebugEnabled()) {
				logger.debug("getRetBasketList() Sql: " + sql.toString());	
			}
			basketCriteria.setRelatedSql(sql.toString());
			return simpleJdbcTemplate.getNamedParameterJdbcOperations().query(sql.toString(), paramSource, mapper);

		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException in getRetBasketList()");
			return null;
		} catch (Exception e) {
			logger.info("Exception caught in getRetBasketList():", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	
	@SuppressWarnings("unchecked")
	public List<BasketDetailDTO> getBasketList(BasketCriteriaDTO basketCriteria) throws DAOException {
		
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		
		StringBuilder sql= new StringBuilder();
		
		sql.append("SELECT  /*+ LEADING (WBDL) */ ") 
		 	.append(" WB.ID, WCBA.CHANNEL_ID, ")
			.append(" WB.TYPE, WBDLKUP.LOCALE, WBDLKUP.DESCRIPTION,") 
			.append(" WBDLKUP.HTML, WBDLKUP.IMAGE_PATH, WBDLKUP.DISPLAY_TYPE, ")
			.append(" WBDL.IDD_FREE_MIN, WBDL.PE_IND, ")
			.append(" WBDL.CONTRACT_PERIOD, WBDL.EXTEND_CONTRACT_PERIOD, ")
			.append(" WBDL.PREINSTALL_CHECK, WBDL.DN_OPTION, WBDL.OS_TYPE, WBDL.PROGRAM_CD ")
			.append(" FROM")
			.append(" W_CHANNEL_BASKET_ASSGN WCBA,") 
			.append(" W_BASKET WB,") 
			.append(" W_BASKET_DTL_LTS WBDL,") 
			.append(" W_BASKET_DISPLAY_LKUP WBDLKUP,")
		    .append(" ( SELECT /*+ PUSH_PRED(WBIA) */ WBIA.BASKET_ID, WIP.RECURRENT_AMT") 
			.append(" FROM W_BASKET_ITEM_ASSGN WBIA,")
			.append(" W_ITEM WI, ")
			.append(" W_ITEM_PRICING WIP")
			.append(" WHERE WI.ID = WIP.ID")
			.append(" AND WBIA.ITEM_ID = WI.ID")
			.append(" AND WI.TYPE = 'PLAN'")
			.append(" AND WI.LOB = 'LTS') BP")
			.append(" WHERE WCBA.CHANNEL_ID = :channelId")
			.append(" AND WCBA.BASKET_ID = WB.ID")
			.append(" AND WB.ID = WBDL.ID ")
			.append(" AND WB.ID = WBDLKUP.BASKET_ID")
			.append(" AND WB.ID = BP.BASKET_ID")
			.append(" AND WBDLKUP.DISPLAY_TYPE = 'RP_PROMOT'")
			.append(" AND WCBA.EFF_START_DATE <= to_date(:applicationDate,'DD/MM/YYYY')")
			.append(" AND (WCBA.EFF_END_DATE IS NULL OR WCBA.EFF_END_DATE > SYSDATE )");
		
		sql.append(" AND WBDLKUP.LOCALE = :locale");
		paramSource.addValue("locale", StringUtils.isBlank(basketCriteria.getLocale()) ? "en" : basketCriteria.getLocale());
		paramSource.addValue("channelId", basketCriteria.getBasketChannelId());
		
		paramSource.addValue("applicationDate", StringUtils.defaultIfEmpty(basketCriteria.getApplicationDate(),LtsDateFormatHelper.getSysDate("dd/MM/yyyy")));
		
		//JT2017062 - Upgrade basket
		if (LtsConstant.CHANNEL_ID_SPRINGBOARD_PREMIER.equals(basketCriteria.getBasketChannelId())) {
			sql.append(" AND WB.ID IN ")
			.append(" ( SELECT DISTINCT BASKET_ID FROM W_BASKET_TYPE")
			.append("    WHERE TYPE = 'HOUSING'")
			.append("    AND TYPE_DESC IN ( ")
			.append("      SELECT HOUSING_TYPE ")
			.append("        FROM W_TEAM_HOUSING_TYPE_ASSGN ")
			.append("        WHERE UPPER(HOUSING_TYPE) = UPPER(:hktPremier)  ");
			if (!(Arrays.asList(LtsConstant.SALES_ROLES_ALL_MANAGERS).contains(basketCriteria.getRole()))) {
			    sql.append("    AND UPPER(TEAM_CD) = UPPER(:teamCode) ");
			    paramSource.addValue("teamCode", basketCriteria.getTeamCode());
			}
			sql.append("))");	
			paramSource.addValue("hktPremier", basketCriteria.getHktPremier());
		}
		
		//TODO LTS HOUSING CAT fix, 05-07-2019
		if(StringUtils.isNotBlank(basketCriteria.getLtsHousingType()) && StringUtils.isNotBlank(basketCriteria.getImsHousingType())){
			sql.append(" AND (");
			sql.append("     (WBDL.LTS_HOUSING_CAT LIKE :likeLtsHousingType AND WBDL.LTS_HOUSING_CAT IS NOT NULL) ");
			sql.append("     OR (WBDL.LTS_HOUSING_CAT IS NULL AND (WBDL.HOUSING_TYPE LIKE :likeImsHousingType OR WBDL.HOUSING_TYPE IS NULL) ");
			sql.append("       ) ");
			sql.append(" ) ");
			paramSource.addValue("likeLtsHousingType", "%|" + basketCriteria.getLtsHousingType() + "|%");
			paramSource.addValue("likeImsHousingType", "%" + basketCriteria.getImsHousingType() + "%");
		}else if(StringUtils.isNotBlank(basketCriteria.getLtsHousingType())){
			sql.append(" AND (WBDL.LTS_HOUSING_CAT LIKE :likeLtsHousingType OR WBDL.LTS_HOUSING_CAT IS NULL) ");
			paramSource.addValue("likeLtsHousingType", "%|" + basketCriteria.getLtsHousingType() + "|%");
		}else if(StringUtils.isNotBlank(basketCriteria.getImsHousingType())){
			sql.append(" AND (WBDL.HOUSING_TYPE LIKE :likeImsHousingType OR WBDL.HOUSING_TYPE IS NULL) ");
			paramSource.addValue("likeImsHousingType", "%" + basketCriteria.getImsHousingType() + "%");
		}else{
			sql.append(" AND WBDL.LTS_HOUSING_CAT IS NULL AND WBDL.HOUSING_TYPE IS NULL ");
		}
		//
		
		if (StringUtils.isNotBlank(basketCriteria.getFromProduct())) {
			sql.append(" AND WB.ID IN ")
			.append(" (SELECT DISTINCT BASKET_ID FROM W_BASKET_TYPE")
			.append("    WHERE TYPE = 'UPGRADE'")
			.append("    AND UPPER(TYPE_DESC) = UPPER(:fromProduct)) ");
			paramSource.addValue("fromProduct", basketCriteria.getFromProduct());
		}
		
		if (StringUtils.isNotBlank(basketCriteria.getBasketType())) {
			sql.append(" AND WB.TYPE = :basketType");
			paramSource.addValue("basketType", basketCriteria.getBasketType());
		}
		
		if (StringUtils.isBlank(basketCriteria.getIddFreeMin())) {
			sql.append(" AND WBDL.IDD_FREE_MIN IS NULL");
		}
		else if (StringUtils.equals(LtsConstant.BASKET_CATEGORY_HOT, basketCriteria.getBasketCategory())) {
			sql.append(" AND WBDL.IDD_FREE_MIN = :iddFreeMin");
			paramSource.addValue("iddFreeMin", basketCriteria.getIddFreeMin());
		}
		else if (StringUtils.equals(LtsConstant.BASKET_CATEGORY_REGULAR, basketCriteria.getBasketCategory())) {
			sql.append(" AND (WBDL.IDD_FREE_MIN <= :iddFreeMin OR WBDL.IDD_FREE_MIN IS NULL)");
			paramSource.addValue("iddFreeMin", basketCriteria.getIddFreeMin());
		}	
		
		if (!basketCriteria.isNonNowTvCust()) {
			sql.append(" AND (WBDL.NON_NOWTV_CUST_ONLY = 'N' OR WBDL.NON_NOWTV_CUST_ONLY IS NULL)");
		}
		
		if (StringUtils.equals(LtsConstant.BASKET_CATEGORY_HOT, basketCriteria.getBasketCategory())) {
			sql.append(" AND WBDL.PE_IND = :paralleExtension");
			paramSource.addValue("paralleExtension", basketCriteria.getParalleExtension());	
		}
		else if (!StringUtils.equals("N", basketCriteria.getParalleExtension())){
			sql.append(" AND WBDL.PE_IND = 'N'");
		}

		if (ArrayUtils.isNotEmpty(basketCriteria.getFilterProjectCds())) {
			sql.append(" AND WBDL.PROJECT_CD IN ( :projectCds ) ");
			paramSource.addValue("projectCds", Arrays.asList(basketCriteria.getFilterProjectCds()));
		}
		else if (ArrayUtils.isNotEmpty(basketCriteria.getDefaultProjectCds())) {
			sql.append(" AND ( ( WBDL.PROJECT_CD IN ( :projectCds ) OR WBDL.PROJECT_CD IS NULL ) AND WBDL.EXTEND_CONTRACT_PERIOD = :extendContractPeriod  ) ");
			paramSource.addValue("projectCds", Arrays.asList(basketCriteria.getDefaultProjectCds()));
			paramSource.addValue("extendContractPeriod", StringUtils.isBlank(basketCriteria.getExtendContractMth()) ? "0" : basketCriteria.getExtendContractMth());
		}
		else {
			sql.append(" AND WBDL.PROJECT_CD IS NULL ");
			sql.append(" AND WBDL.EXTEND_CONTRACT_PERIOD = :extendContractPeriod");
			paramSource.addValue("extendContractPeriod", StringUtils.isBlank(basketCriteria.getExtendContractMth()) ? "0" : basketCriteria.getExtendContractMth());
		}
		
		sql.append(" AND (WBDL.ELIGIBLE_TEAM IS NULL ");
		if (StringUtils.isNotBlank(basketCriteria.getAreaCode())) {
			sql.append(" OR UPPER(WBDL.ELIGIBLE_TEAM) LIKE UPPER(:likeAreaCode) "); 
			paramSource.addValue("likeAreaCode", "%| " + basketCriteria.getAreaCode() + " |%");
		}
		if (StringUtils.isNotBlank(basketCriteria.getTeamCode())) {
			sql.append(" OR UPPER(WBDL.ELIGIBLE_TEAM) LIKE UPPER(:likeTeamCode) "); 
			paramSource.addValue("likeTeamCode", "%| " + basketCriteria.getTeamCode() + " |%");
		}
		sql.append(" ) ");
		
		sql.append(" AND (WBDL.NON_ELIGIBLE_TEAM IS NULL ");
		if (StringUtils.isNotBlank(basketCriteria.getAreaCode()) || StringUtils.isNotBlank(basketCriteria.getTeamCode())) {
			sql.append(" OR (WBDL.NON_ELIGIBLE_TEAM IS NOT NULL ");
			if (StringUtils.isNotBlank(basketCriteria.getAreaCode())) {
				sql.append(" AND UPPER(WBDL.NON_ELIGIBLE_TEAM) NOT LIKE UPPER(:likeAreaCode) "); 
				paramSource.addValue("likeAreaCode", "%| " + basketCriteria.getAreaCode() + " |%");
			}
			if (StringUtils.isNotBlank(basketCriteria.getTeamCode())) {
				sql.append(" AND UPPER(WBDL.NON_ELIGIBLE_TEAM) NOT LIKE UPPER(:likeTeamCode) "); 
				paramSource.addValue("likeTeamCode", "%| " + basketCriteria.getTeamCode() + " |%");
			}
			sql.append(" ) ");
		}
		sql.append(" ) ");
		
		if (StringUtils.isNotEmpty(basketCriteria.getTpExpiredMonths())) {
			sql.append(" AND (:tpExpiredMonths BETWEEN WBDL.UPPER_TP_EXPIRE_MTH AND WBDL.LOWER_TP_EXPIRE_MTH OR (WBDL.UPPER_TP_EXPIRE_MTH IS NULL AND WBDL.LOWER_TP_EXPIRE_MTH IS NULL) )  ");
			paramSource.addValue("tpExpiredMonths", basketCriteria.getTpExpiredMonths());
		}
		
		if (StringUtils.isNotEmpty(basketCriteria.getProfileArpu())) {
			sql.append(" AND (:profileArpu BETWEEN WBDL.LOWER_ARPU AND WBDL.UPPER_ARPU OR (WBDL.UPPER_ARPU IS NULL AND WBDL.LOWER_ARPU IS NULL) )  ");
			paramSource.addValue("profileArpu", basketCriteria.getProfileArpu());
		}
		else {
			sql.append(" AND WBDL.UPPER_ARPU IS NULL AND WBDL.LOWER_ARPU IS NULL ");
		}
		
		if (ArrayUtils.isNotEmpty(basketCriteria.getProfilePsefCds())) {
			sql.append(" AND ( ");
			
			for (String profilePsefCd : basketCriteria.getProfilePsefCds()) {
				sql.append(" WBDL.eligible_subc_psef like '%").append(profilePsefCd).append("%' OR ");	
			}
			sql.append(" WBDL.eligible_subc_psef IS NULL ) ");
		}
		else {
			sql.append(" AND WBDL.eligible_subc_psef IS NULL ");
		}
		
		if (StringUtils.isNotBlank(basketCriteria.getCustCampaignCd())) {
			sql.append(" AND ( wbdl.customer_campaign_cd IS NULL OR UPPER(WBDL.CUSTOMER_CAMPAIGN_CD) LIKE UPPER(:custCampaignCd) ) ");
			paramSource.addValue("custCampaignCd", "%" + basketCriteria.getCustCampaignCd() + "%");
		}
		
		if (ArrayUtils.isNotEmpty(basketCriteria.getBasketCampaignCds())) {
			sql.append(" AND ( WBDL.cust_dedicated_campaign_cd IN (:basketCampaignCds) AND WBDL.cust_dedicated_campaign_cd IS NULL )");
			paramSource.addValue("basketCampaignCds", basketCriteria.getBasketCampaignCds());
		}
		
		if (StringUtils.isNotEmpty(basketCriteria.getOsType())) {
			if(LtsConstant.OS_TYPE_AND.equals(basketCriteria.getOsType())){
				sql.append(" AND (WBDL.OS_TYPE IS NULL OR WBDL.OS_TYPE = :osType ) ");
				paramSource.addValue("osType", basketCriteria.getOsType());
			}
			if(LtsConstant.OS_TYPE_IOS.equals(basketCriteria.getOsType())){
				sql.append(" AND (WBDL.OS_TYPE = :osType ) ");
				paramSource.addValue("osType", basketCriteria.getOsType());
			}
		}
		
		sql.append(" ORDER BY WBDL.PE_IND DESC, WBDL.CONTRACT_PERIOD DESC, BP.RECURRENT_AMT DESC, WBDL.IDD_FREE_MIN DESC");
		
		ParameterizedRowMapper<BasketDetailDTO> mapper = new ParameterizedRowMapper<BasketDetailDTO>() {
			public BasketDetailDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				BasketDetailDTO basketDetail = new BasketDetailDTO();
				basketDetail.setBasketId(rs.getString("ID"));
				basketDetail.setType(rs.getString("TYPE"));
				basketDetail.setLocale(rs.getString("LOCALE"));
				basketDetail.setDesc(rs.getString("DESCRIPTION"));
				basketDetail.setHtml(rs.getString("HTML"));
				basketDetail.setImagePath(rs.getString("IMAGE_PATH"));
				basketDetail.setDisplayType(rs.getString("DISPLAY_TYPE"));
				basketDetail.setIddFreeMin(rs.getString("IDD_FREE_MIN"));
				basketDetail.setPeInd(rs.getString("PE_IND"));
				basketDetail.setContractPeriod(rs.getString("CONTRACT_PERIOD"));
				basketDetail.setBasketChannelId(rs.getString("CHANNEL_ID"));
				basketDetail.setPreinstallCheck(rs.getString("PREINSTALL_CHECK"));
				basketDetail.setDnOption(rs.getString("DN_OPTION"));
				basketDetail.setExtendContractPeriod(rs.getString("EXTEND_CONTRACT_PERIOD"));
				basketDetail.setOsType(rs.getString("OS_TYPE"));
				basketDetail.setProgramCd(rs.getString("PROGRAM_CD"));
				return basketDetail;
			}
		};

		try {
			if (logger.isDebugEnabled()) {
				logger.debug("getBasketList() Sql: " + sql.toString());	
			}
			basketCriteria.setRelatedSql(sql.toString());
			return simpleJdbcTemplate.getNamedParameterJdbcOperations().query(sql.toString(), paramSource, mapper);

		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException in getBasketList()");
			return null;
		} catch (Exception e) {
			logger.info("Exception caught in getEyeDeviceList():", e);
			throw new DAOException(e.getMessage(), e);
		}
		
	}
	
	public BasketDetailDTO getBasket(String basketId, String locale, String displayType) throws DAOException {

		MapSqlParameterSource paramSource = new MapSqlParameterSource();

		StringBuilder sql = new StringBuilder();

		sql.append("SELECT WB.ID, WB.BASE_BASKET_ID, WCBA.CHANNEL_ID, WBDL.EXTEND_CONTRACT_PERIOD,")
				.append(" WB.TYPE, WBDLKUP.LOCALE, WBDLKUP.DESCRIPTION,")
				.append(" WBDL.IDD_FREE_MIN, WBDL.CONTRACT_PERIOD, WBDL.PE_IND, WBDL.DUPLEX_IND, ")
				.append(" WBDL.BUNDLE2G_IND, WBDL.BRIDGING_MTH, WBDL.BASKET_CATG, WBDL.PROJECT_CD, WBDL.EFFECTIVE_PRICE, ")
				.append(" WBDLKUP.HTML, WBDLKUP.IMAGE_PATH, WBDLKUP.DISPLAY_TYPE, WBDL.STAFF_PLAN, WBDL.BUNDLE_TV_CREDIT, ")
				.append(" WBDL.PAY_MTD_TYPES, ")
				.append(" WBDL.PREINSTALL_CHECK, WBDL.DN_OPTION, WBDL.OS_TYPE, WBDL.PROGRAM_CD ")
				.append(" FROM").append(" W_BASKET WB,")
				.append(" W_BASKET_DTL_LTS WBDL,")
				.append(" W_BASKET_DISPLAY_LKUP WBDLKUP,")
				.append(" W_CHANNEL_BASKET_ASSGN WCBA " )
				.append(" WHERE WB.ID = WBDL.ID ")
				.append(" AND WB.ID = WBDLKUP.BASKET_ID")
				.append(" AND WB.ID = WCBA.BASKET_ID (+)")
				.append(" AND WB.ID = :basketId")
				.append(" AND WBDLKUP.LOCALE = :locale")
				.append(" AND WBDLKUP.DISPLAY_TYPE = :displayType");


		paramSource.addValue("basketId", basketId);
		paramSource.addValue("locale", StringUtils.isBlank(locale) ? "en" : locale);
		paramSource.addValue("displayType",
				StringUtils.isBlank(displayType) ? "ITEM_SELECT" : displayType);

		ParameterizedRowMapper<BasketDetailDTO> mapper = new ParameterizedRowMapper<BasketDetailDTO>() {
			public BasketDetailDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				BasketDetailDTO basketDetail = new BasketDetailDTO();
				basketDetail.setBasketId(rs.getString("ID"));
				basketDetail.setType(rs.getString("TYPE"));
				basketDetail.setLocale(rs.getString("LOCALE"));
				basketDetail.setDesc(rs.getString("DESCRIPTION"));
				basketDetail.setHtml(rs.getString("HTML"));
				basketDetail.setImagePath(rs.getString("IMAGE_PATH"));
				basketDetail.setDisplayType(rs.getString("DISPLAY_TYPE"));
				basketDetail.setIddFreeMin(rs.getString("IDD_FREE_MIN"));
				basketDetail.setContractPeriod(rs.getString("CONTRACT_PERIOD"));
				basketDetail.setPeInd(rs.getString("PE_IND"));
				basketDetail.setBaseBasketId(rs.getString("BASE_BASKET_ID"));
				basketDetail.setExtendContractPeriod(rs.getString("EXTEND_CONTRACT_PERIOD"));
				basketDetail.setDuplexInd(rs.getString("DUPLEX_IND"));
				basketDetail.setBundle2G(rs.getString("BUNDLE2G_IND"));
				basketDetail.setBridgingMth(rs.getString("BRIDGING_MTH"));
				basketDetail.setBasketCatg(rs.getString("BASKET_CATG"));
				basketDetail.setStaffPlan("STAFF_PLAN");
				basketDetail.setBasketChannelId(rs.getString("CHANNEL_ID"));
				basketDetail.setBundleTvCredit(rs.getString("BUNDLE_TV_CREDIT"));
				basketDetail.setProjectCd(rs.getString("PROJECT_CD"));
				basketDetail.setEffectivePrice(rs.getString("EFFECTIVE_PRICE"));
				basketDetail.setPayMtdTypes(rs.getString("PAY_MTD_TYPES"));
				basketDetail.setPreinstallCheck(rs.getString("PREINSTALL_CHECK"));
				basketDetail.setDnOption(rs.getString("DN_OPTION"));
				basketDetail.setOsType(rs.getString("OS_TYPE"));
				basketDetail.setProgramCd(rs.getString("PROGRAM_CD"));
				return basketDetail;
			}
		};
		
		
		try {
			if (logger.isDebugEnabled()) {
				logger.debug("getBasketList() Sql: " + sql.toString());
			}
			return (BasketDetailDTO) simpleJdbcTemplate
					.getNamedParameterJdbcOperations().queryForObject(
							sql.toString(), paramSource, mapper);

		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException in getBasketList()");
			return null;
		} catch (Exception e) {
			logger.info("Exception caught in getBasketList():", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public String getMaxIddFreeMin(String basketChannelId, String basketType, String iddFreeMin, boolean parallelExtension, String locale, String extendContractPeriod, boolean isNonNowTvCustOnly, String applicationDate) throws DAOException {

		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		
		StringBuilder sql= new StringBuilder();
		
		sql.append("SELECT IDD_FREE_MIN FROM (") 
		   .append(" SELECT WBDL.IDD_FREE_MIN FROM ") 
		   .append("   W_BASKET WB,") 
		   .append("   W_BASKET_DTL_LTS WBDL,") 
		   .append("   W_BASKET_DISPLAY_LKUP WBDLKUP, ")
		   .append("   W_CHANNEL_BASKET_ASSGN WCBA")
		   .append(" WHERE WB.ID = WBDL.ID ")
		   .append("   AND WB.ID = WBDLKUP.BASKET_ID")
	       .append("   AND WBDLKUP.DISPLAY_TYPE = 'RP_PROMOT'")
		   .append("   AND WCBA.BASKET_ID = WB.ID ")
		   .append("   AND WCBA.CHANNEL_ID = :basketChannelId ")
		   .append("   AND WCBA.EFF_START_DATE <= to_date(:applicationDate, 'DD/MM/YYYY') ")
		   .append("   AND (WCBA.EFF_END_DATE IS NULL OR WCBA.EFF_END_DATE > SYSDATE) ");
		
		paramSource.addValue("basketChannelId", basketChannelId);
		
		sql.append(" AND WBDLKUP.LOCALE = :locale");
		paramSource.addValue("locale", StringUtils.isBlank(locale) ? "en" : locale);
		
		if (StringUtils.isNotBlank(basketType)) {
			sql.append(" AND WB.TYPE = :basketType");
			paramSource.addValue("basketType", basketType);
		}
		
		sql.append(" AND WBDL.PE_IND = :paralleExtension");
		paramSource.addValue("paralleExtension", parallelExtension ? "Y" : "N");
		
		sql.append(" AND WBDL.IDD_FREE_MIN <= :iddFreeMin");
		paramSource.addValue("iddFreeMin", iddFreeMin);
		
		sql.append(" AND WBDL.EXTEND_CONTRACT_PERIOD = :extendContractPeriod");
		paramSource.addValue("extendContractPeriod", StringUtils.isBlank(extendContractPeriod) ? "0" : extendContractPeriod);
		
		paramSource.addValue("applicationDate", StringUtils.defaultIfEmpty(applicationDate,LtsDateFormatHelper.getSysDate("dd/MM/yyyy")));

		
		if (!isNonNowTvCustOnly) {
			sql.append(" AND (WBDL.NON_NOWTV_CUST_ONLY = 'N' OR WBDL.NON_NOWTV_CUST_ONLY IS NULL)");
		}
		
		sql.append(" ORDER BY IDD_FREE_MIN DESC ) ")
		   .append(" WHERE ROWNUM  = 1 ");
		
		try {
			if (logger.isDebugEnabled()) {
				logger.debug("getMaxIddFreeMin() Sql: " + sql.toString());
			}
			return (String) simpleJdbcTemplate.queryForObject(sql.toString(), String.class, paramSource);
			
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException in getMaxIddFreeMin()");
			return null;
		} catch (Exception e) {
			logger.info("Exception caught in getMaxIddFreeMin():", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public String getBasketContractPeriod(String pBasketId) throws DAOException {

		try {
			return  this.simpleJdbcTemplate.queryForObject(SQL_GET_CONTRACT_PERIOD, String.class, pBasketId);
		} catch (EmptyResultDataAccessException erdae) {
			return null;
		} catch (Exception e) {
			logger.error("Error in getBasketContractPeriod()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public List<LookupItemDTO> getProjectCdList(String channelCd, String teamCd, String orderType) throws DAOException {
		
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue("channelCd", channelCd);
		paramSource.addValue("teamCd", teamCd);
		paramSource.addValue("orderType", orderType);
		
		ParameterizedRowMapper<LookupItemDTO> mapper = new ParameterizedRowMapper<LookupItemDTO>() {
			public LookupItemDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				LookupItemDTO lookupItem = new LookupItemDTO();
				lookupItem.setItemKey(rs.getString("project_cd"));
				lookupItem.setItemValue(rs.getString("project_desc"));
				return lookupItem;
			}
		};
		
		try {
			return  this.simpleJdbcTemplate.query(SQL_GET_PROJECT_CD, mapper, paramSource);
		} catch (EmptyResultDataAccessException erdae) {
			return null;
		} catch (Exception e) {
			logger.error("Error in getProjectCdList()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public String getBasketPremierInd(String basketId) throws DAOException {
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue("basketId", basketId);
		paramSource.addValue("type", "HOUSING");
		
		try {
			return  this.simpleJdbcTemplate.queryForObject(SQL_GET_BASKET_PREMIER_IND, String.class, paramSource);
		} catch (EmptyResultDataAccessException erdae) {
			return null;
		} catch (Exception e) {
			logger.error("Error in getBasketPremierInd()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
}
