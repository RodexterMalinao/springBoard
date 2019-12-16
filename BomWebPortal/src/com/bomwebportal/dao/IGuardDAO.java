package com.bomwebportal.dao;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.bomwebportal.exception.DAOException;


public class IGuardDAO extends BaseDAO {
		
	//-----------------------------------------------------------------------------------------------
	String getNsPriceSQL = "select ns_price"
		 + " from w_handset_ns_price  "
		 + " where pos_item_cd = :posItemCd"
		 + " and TRUNC(:appDate) BETWEEN TRUNC(NVL(EFF_START_DATE,:appDate))"
		 + " AND NVL(TRUNC(EFF_END_DATE), TRUNC(:appDate))"; 
	//-----------------------------------------------------------------------------------------------
	public BigDecimal getNsPrice(String posItemCd, Date appDate) throws DAOException {
		if (logger.isDebugEnabled()) {
			logger.debug("getNsPrice() is called");
		}
		List<BigDecimal> itemList = null;
		try {
			
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("posItemCd", posItemCd);
			params.addValue("appDate", appDate);
			if (logger.isDebugEnabled()) {
				logger.debug("getNsPrice() @ IGuardDAO: " + getNsPriceSQL);
			}
			itemList = this.simpleJdbcTemplate.query(getNsPriceSQL, this.getBigDecimalRowMapper(), params);

		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException in getNsPrice()");
			itemList = null;
		} catch (Exception e) {
			logger.info("Exception caught in getNsPrice():", e);
			throw new DAOException(e.getMessage(), e);
		}
		return this.isEmpty(itemList) ? null : itemList.get(0);
	}
	

	
	//-----------------------------------------------------------------------------------------------
	String getIGuardPlanSQL = "select srv_plan_cd from bomweb_iguard_lkup  "
			 + " where min_contract_period = ( "
			 + "       select MAX(min_contract_period)  "
			 + "       from bomweb_iguard_lkup  "
			 + "       where min_contract_period <= :contractPeriod"
			 + "       and min_hs_ns_price <= :nsPrice"
			 + "       and max_hs_ns_price >= :nsPrice" 
			 + "       and TRUNC(:appDate) BETWEEN TRUNC(NVL(EFF_START_DATE,:appDate))"
			 + "       AND TRUNC(NVL(EFF_END_DATE, :appDate)))"
			 + " and min_hs_ns_price <= :nsPrice "
			 + " and max_hs_ns_price >= :nsPrice "
			 + " and TRUNC(:appDate) BETWEEN TRUNC(NVL(EFF_START_DATE,:appDate))"
			 + " AND TRUNC(NVL(EFF_END_DATE, :appDate))"
			 + " and srv_plan_type= :srvPlanType"; 

						
	//-----------------------------------------------------------------------------------------------				
	
	public String getIGuardPlan(BigDecimal nsPrice, int contractPeriod, String srvPlanType, Date appDate) throws DAOException {
		if (logger.isDebugEnabled()) {
			logger.debug("getIGuardPlan() is called");
		}
		String item = null;
		try {
			
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("nsPrice", nsPrice);
			params.addValue("contractPeriod", contractPeriod);
			params.addValue("appDate", appDate);
			params.addValue("srvPlanType", srvPlanType);
			 
			if (logger.isDebugEnabled()) {
				logger.debug("getIGuardPlan() @ IGuardDAO: " + getIGuardPlanSQL);
			}
			item = this.simpleJdbcTemplate.queryForObject(getIGuardPlanSQL, String.class, params);
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException in getIGuardPlan()");
			item = null;
		} catch (Exception e) {
			logger.info("Exception caught in getIGuardPlan():", e);
			throw new DAOException(e.getMessage(), e);
		}
		return item;
		
	}
	
	// Project Eagle
	public String getProjectEaglePlan(BigDecimal nsPrice, String srvPlanCd, Date appDate) throws DAOException {
		
		if (logger.isDebugEnabled()) {
			logger.debug("getProjectEaglePlan() is called");
		}
		
		String sql = getIGuardPlanSQL + " and SRV_PLAN_CD = :srvPlanCd ";
		
		String item = null;
		
		try {
			
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("nsPrice", nsPrice);
			params.addValue("contractPeriod", 0); // contract period is not considered
			params.addValue("appDate", appDate);
			params.addValue("srvPlanType", "EAGLE");
			params.addValue("srvPlanCd", srvPlanCd);
			 
			if (logger.isDebugEnabled()) {
				logger.debug("getProjectEaglePlan() @ IGuardDAO: " + sql);
			}
			
			item = this.simpleJdbcTemplate.queryForObject(sql, String.class, params);
			
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException in getProjectEaglePlan()");
			item = null;
			
		} catch (Exception e) {
			logger.info("Exception caught in getProjectEaglePlan():", e);
			throw new DAOException(e.getMessage(), e);
		}
		
		return item;
		
	}
	
	//-----------------------------------------------------------------------------------------------				

	String getIGuardPlanPriceSQL = "select mthly_fee from bomweb_iguard_lkup where srv_plan_cd = :iGuardPlan"; 

						
	//-----------------------------------------------------------------------------------------------				
	
	public String getIGuardPlanPrice(String iGuardPlan) throws DAOException {
		if (logger.isDebugEnabled()) {
			logger.debug("getIGuardPlanPrice() is called");
		}
		String item = null;
		
		try {
			
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("iGuardPlan", iGuardPlan);

			 
			if (logger.isInfoEnabled()) {
				logger.info("getIGuardPlanPrice() @ IGuardDAO: " + getIGuardPlanPriceSQL);
			}
			item = this.simpleJdbcTemplate.queryForObject(getIGuardPlanPriceSQL, String.class, params);
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException in getIGuardPlanPrice()");
			item = null;
		} catch (Exception e) {
			logger.info("Exception caught in getIGuardPlanPrice():", e);
			throw new DAOException(e.getMessage(), e);
		}
		return item;
		
	}
	//-----------------------------------------------------------------------------------------------
	

			
	//-----------------------------------------------------------------------------------------------				
	
	public List<String> isIGuardOrder(String basketId, String[] vasList, Date appDate) throws DAOException {
		if (logger.isDebugEnabled()) {
			logger.debug("isIGuardOrder() is called");
		}

			MapSqlParameterSource params = new MapSqlParameterSource();
			String sql = " select DISTINCT ISGA.GRP_ID "
					 + "  from W_ITEM_SELECTION_GRP_ASSGN ISGA "
					 + " where ISGA.GRP_ID in ('6666666666','6666666667','6666666669') "
					 + "   and ( 1 <> 1";
			
			if (vasList != null) {
				List<String> list = new ArrayList<String>();
				for (String vas : vasList) {
					if (StringUtils.isNotBlank(vas)) {
						list.add(vas);
					}
				}
				if (!list.isEmpty()) {
					sql += " or ISGA.ITEM_ID in (:vasList)";
					params.addValue("vasList", list);
				}else
					sql += " and ISGA.ITEM_ID in ('')";
			}
			
			if (StringUtils.isNotBlank(basketId)) {
			sql		 += (" or ISGA.ITEM_ID in "
					 + "       (select BIA.ITEM_ID "
					 + "           from W_BASKET_ITEM_ASSGN BIA "
					 + "          where TRUNC(:appDate) between BIA.EFF_START_DATE and TRUNC(NVL(BIA.EFF_END_DATE, sysdate))"
					 + " 			and BIA.BASKET_ID = :basketid) "); 
				params.addValue("basketid", basketId);
			}
			sql += ")";
			params.addValue("appDate", appDate, Types.DATE);
			
			if (logger.isDebugEnabled()) {
				logger.debug("isIGuardOrder() @ IGuardDAO: " + sql);
			}

		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {

			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getString("GRP_ID");
			}
		};
		List<String> iGuardTypeList = new ArrayList();
		try {
			List<String> vasItemList = simpleJdbcTemplate.query(sql, mapper,params);

			for(int i=0; i<vasItemList.size(); i++){
				if ("6666666666".equals(vasItemList.get(i))){
					iGuardTypeList.add("LDS");
				}else if("6666666667".equals(vasItemList.get(i))){
					iGuardTypeList.add("AD");
				}else if("6666666669".equals(vasItemList.get(i))){
					iGuardTypeList.add("UAD");
				}else{
					return null;
				}
			}
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("isIGuardOrder() EmptyResultDataAccessException");
		} catch (Exception e) {
			logger.error("Exception caught in isIGuardOrder()", e);
			throw new DAOException(e.getMessage(), e);
		}
		return iGuardTypeList;
		
	}
	
	private <E> boolean isEmpty(E[] array) {
		return array == null || array.length == 0;
	}
	
	

	
	//-----------------------------------------------------------------------------------------------				
	String getIGuardSnSQL = "SELECT bomweb_iguard_s.NEXTVAL from DUAL";

	//-----------------------------------------------------------------------------------------------				
	
	
	public String getIGuardSn() throws DAOException {
		if (logger.isDebugEnabled()) {
			logger.debug("getIGuardPlan() is called");
		}

		Integer item = null;
		String snid = null;
		try {
			
			if (logger.isDebugEnabled()) {
				logger.debug("getIGuardSn() @ IGuardDAO: " + getIGuardSnSQL);
			}
			item = this.simpleJdbcTemplate.queryForInt(getIGuardSnSQL);

			Integer sn = item;
			
			if (sn != null){
				NumberFormat formatter = new DecimalFormat("000000");
				String snString = formatter.format(sn);
				snid = "E" + snString;
				
				
			}else 
				return null;
			
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException in getIGuardSn()");
			item = null;
		} catch (Exception e) {
			logger.info("Exception caught in getIGuardSn():", e);
			throw new DAOException(e.getMessage(), e);
		}
		return snid;
	}
	
	//-----------------------------------------------------------------------------------------------				
		
	private ParameterizedRowMapper<BigDecimal> getBigDecimalRowMapper() {
		return new ParameterizedRowMapper<BigDecimal>() {
			public BigDecimal mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getBigDecimal(1);
			}
		};
	}
	
	
	private boolean isEmpty(List<?> list) {
		return list == null || list.isEmpty();
	}

	String getIGuardSnByOrderSQL = "SELECT iguard_sn from bomweb_order_mob where order_id = :orderId";
	
	public String getIGuardSnByOrder(String orderId) throws DAOException {
		if (logger.isDebugEnabled()) {
			logger.debug("getIGuardSnByOrder() is called");
		}
		String item = null;
		
		try {
			
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("orderId", orderId);

			 
			if (logger.isDebugEnabled()) {
				logger.debug("getIGuardSnByOrder() @ IGuardDAO: " + getIGuardSnByOrderSQL);
			}
			item = this.simpleJdbcTemplate.queryForObject(getIGuardSnByOrderSQL, String.class, params);
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException in getIGuardSnByOrder()");
			item = null;
		} catch (Exception e) {
			logger.info("Exception caught in getIGuardSnByOrder():", e);
			throw new DAOException(e.getMessage(), e);
		}
		return item;
		
	}
	
	public Boolean isIGuardUADPlanSubscribed (String orderId,String grpId) throws DAOException{
		String isIGuardPlanSubscribedSql = "select count(*) as noOfRecord from bomweb_subscribed_item bsi "
				+ "left join W_ITEM_SELECTION_GRP_ASSGN wisga on (bsi.id = wisga.item_id) "
				+ "where order_id = :orderId and wisga.grp_id = :grpId";
		
		if (logger.isDebugEnabled()) {
			logger.debug("isIGuardPlanSubscribedSql() is called");
		}
		
		try {

			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("orderId", orderId);
			params.addValue("grpId", grpId);

			if (logger.isDebugEnabled()) {
				logger.debug("isIGuardPlanSubscribedSql() @ IGuardDAO: " + isIGuardPlanSubscribedSql);
			}
			Integer noOfRecord = this.simpleJdbcTemplate.queryForObject(isIGuardPlanSubscribedSql, Integer.class, params);
			if (noOfRecord>0){
				return true;
			}
			else{
				return false;
			}
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException in isIGuardPlanSubscribedSql()");
			return false;
		} catch (Exception e) {
			logger.info("Exception caught in isIGuardPlanSubscribedSql():", e);
			throw new DAOException(e.getMessage(), e);
		}
		
	}
	
	public String getIGuardUADDateOfBirth (Date appDate, String dateOfBirth) throws DAOException{
		String getIGuardUADDateOfBirthSql = "select round(months_between(:appDate,to_date(:dateOfBirth,'dd/MM/yyyy'))/12) year from dual ";
		
		if (logger.isDebugEnabled()) {
			logger.debug("getIGuardUADDateOfBirthSql() is called");
		}
		
		try {

			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("appDate", appDate);
			params.addValue("dateOfBirth", dateOfBirth);

			if (logger.isDebugEnabled()) {
				logger.debug("getIGuardUADDateOfBirth() @ IGuardDAO: " + getIGuardUADDateOfBirthSql);
			}
			Integer age = this.simpleJdbcTemplate.queryForObject(getIGuardUADDateOfBirthSql, Integer.class, params);
			if (age !=null){
				if (age>=18 && age<=30){
					return " 18-30 ";
				}else if (age>=31 && age<=40){
					return " 31-40 ";
				}else if (age>=41 && age<=50){
					return " 41-50 ";
				}else if (age>=51 && age<=60){
					return " 51-60 ";
				}else{
					return " 61 or above ";
				}
			}else{
				return " ";
			}
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException in getIGuardUADDateOfBirth()");
			return " ";
		} catch (Exception e) {
			logger.info("Exception caught in getIGuardUADDateOfBirth():", e);
			throw new DAOException(e.getMessage(), e);
		}
		
	}

	public int updateUADOptInd(String orderId, String optInInd) throws DAOException {
		logger.debug("updateUADOptInd is called");
		String SQL = "update bomweb_order_mob set uad_opt_ind=? , last_upd_by='uadSign', last_upd_date=sysdate where order_id=? ";
		try {
			return simpleJdbcTemplate.update(SQL, optInInd, orderId);
		} catch (DataAccessException e) {
			logger.error("Exception caught in updateUADOptInd()", e);
		}
		return 0;
	}
	
	// for HKTCare and Project Eagle
	public String getProductCodeBySelectGrpAndLineGrp(String itemId, String itemSelectionGroupId, String serviceLineGroupCategory) throws DAOException {
		
		if (logger.isDebugEnabled()) {
			logger.debug("getProductCodeBySelectGrpAndLineGrp() is called");
		}
		
		String sql = "SELECT prod_cd FROM w_item_offer_product_assgn  \n"+
				"WHERE item_id IN\n"+
				"(SELECT item_id FROM w_item_selection_grp_assgn WHERE grp_id = :itemSelectionGroupId AND item_id = :itemId)\n"+
				"AND prod_cd IN\n"+
				"(SELECT offer_cd FROM w_srv_line_group_lkup WHERE grp_cat = :serviceLineGroupCategory)\n"+
				"UNION\n"+
				"SELECT offer_cd FROM w_item_offer_assgn \n"+
				"WHERE item_id IN\n"+
				"(SELECT item_id FROM w_item_selection_grp_assgn WHERE grp_id = :itemSelectionGroupId AND item_id = :itemId)\n"+
				"AND offer_cd IN\n"+
				"(SELECT offer_cd FROM w_srv_line_group_lkup WHERE grp_cat = :serviceLineGroupCategory)";
		
		String item = null;
		
		try {
			
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("itemId", itemId);
			params.addValue("itemSelectionGroupId", itemSelectionGroupId);
			params.addValue("serviceLineGroupCategory", serviceLineGroupCategory);
			 
			if (logger.isDebugEnabled()) {
				logger.debug("getProductCodeBySelectGrpAndLineGrp() @ IGuardDAO: " + sql);
			}
			
			item = this.simpleJdbcTemplate.queryForObject(sql, String.class, params);
			
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException in getProductCodeBySelectGrpAndLineGrp()");
			item = null;
			
		} catch (Exception e) {
			logger.info("Exception caught in getProductCodeBySelectGrpAndLineGrp():", e);
			throw new DAOException(e.getMessage(), e);
		}
		
		return item;
		
	}
	
}