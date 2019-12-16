package com.bomwebportal.ims.dao;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.bomwebportal.dao.*;
import com.bomwebportal.dto.*;
import com.bomwebportal.ims.dto.*;
import com.bomwebportal.exception.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

public class BasketSelectDAO extends BaseDAO{
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	public List<ImsBasketDTO> getImsBasketList (String locale,
												String bandwidthStr,
												String housingTypeStr,
												String technologyStr,
												String IsPON,
												String appDate,
												String Sales_channel,
												String teamCd,
												Boolean sbFilterVas,
												String mobileOfferInd,
												String sbNo,
												String ltsServiceInd,
												String serviceCdStr,
												String channelTeamCd,
												String staffGroup)
												throws DAOException {
		List<ImsBasketDTO> imsBasketList = new ArrayList<ImsBasketDTO>();
		bandwidthStr = bandwidthStr.replace(",","','");
		housingTypeStr = housingTypeStr.replace(",","','");
		technologyStr = technologyStr.replace(",","','");
		String CHANNEL_ID = null;
		if("DS".equals(Sales_channel)){
			CHANNEL_ID = "7";//Direct sales
		}
		else if("CC".equals(Sales_channel)){
			if("Y".equalsIgnoreCase(mobileOfferInd))
				CHANNEL_ID = "6";//callcenter mobile offer
			else
				CHANNEL_ID = "3";//callcenter
		}else{
			if("Y".equalsIgnoreCase(mobileOfferInd))
				CHANNEL_ID = "5";//retail mobile offer
			else
				CHANNEL_ID = "1";//retail
		}
		String SQL=
			
			"SELECT PT.DESCRIPTION PLAN_TYPE,								 "+
			"		BIA.BASKET_ID, 											 "+															
			"   	TBDL.HTML TITLE,										 "+
			"		SBDL.HTML SUMMARY,										 "+
			"		IP.RECURRENT_AMT,										 "+
			"		MFIDL.HTML MTHFIX, 										 "+
			"		IP.MTH_TO_MTH_RATE,										 "+
			"		MTMIDL.HTML MTH2MTH,									 "+			
			"		BWDIDL.HTML bw_desc,						 "+
			"		NVL(IDRV.CONTRACT_PERIOD,'0') CONTRACT_PERIOD,			 "+
			"   		I.ID,												 "+	
			" 		IOA.OFFER_CD,											 "+
			"   		NVL(TBDL.IMAGE_PATH,'NA') IMAGE_PATH,				 "+	
			" 		IDI.BANDWIDTH,											 "+
			" 		I.TYPE,													 "+
			"		NVL(IDI.IS_PREINSTALLATION, 'N') IS_PREINSTALLATION,"+
			"		NVL(IDI.IS_PREUSE, 'N') IS_PREUSE,					 "+
			"		idi.technology,											 "+
			"		(select decode(count(*),0,'N','Y') from w_dic_basket_item_assgn c, w_item d where c.basket_id = b.ID and c.item_id = d.id and d.type in (select code from w_code_lkup where grp_id = 'IMS_PREINST_ROUTER_TYPE')) HAS_GOOGLE_ROUTER "+			
			"FROM  	W_BASKET                B,								 "+
			"  		W_DIC_BASKET_ITEM_ASSGN     BIA,						 "+
			"  		W_ITEM                  I,  							 "+
			" 		W_ITEM_DTL_RP_RB_VAS    IDRV,							 "+		
			"  		W_BASKET_DISPLAY_LKUP   TBDL,							 "+
			" 		W_BASKET_DISPLAY_LKUP   SBDL,							 "+	
			"		W_ITEM_PRICING			IP,								 "+
			" 		W_ITEM_OFFER_ASSGN	   	IOA,							 "+
			"		W_ITEM_DTL_IMS		   	IDI,							 "+
			"		W_ITEM_DISPLAY_LKUP   MFIDL,							 "+
			"		W_ITEM_DISPLAY_LKUP   MTMIDL,							 "+
			"		W_ITEM_DISPLAY_LKUP   BWDIDL,							 "+			
			"		W_DISPLAY_LKUP   PT,									 "+	
			"		(SELECT DISTINCT B.ID, btsb.type_desc					 "+
			"		FROM  	W_BASKET                B,						 "+
			"				W_DIC_BASKET_ITEM_ASSGN     BIA,					 "+
			"				W_ITEM                  I,  					 "+
			"				W_BASKET_TYPE			BT,						 "+
			"				w_basket_type 			btteam,	 				 "+
			"				w_basket_type 			btstaff,	 			 "+			 
			"				w_basket_type 			btsrvcd,	 			 "+	
			"				w_basket_type 			btsb,	 				 "+
			"				W_ITEM_DTL_IMS		   	IDI						 "+
			"		WHERE  B.ID = BIA.BASKET_ID								 "+	
			"		AND    B.ID = BT.BASKET_ID								 "+
			"		AND	   BT.TYPE = 'HOUSING'								 "+
			"		AND	   BT.TYPE_DESC IN('" + housingTypeStr + "')		 "+
			"		AND    btteam.basket_id(+) = b.id						 "+
			"		AND    btteam.type(+) = 'CORE_DE_TEAM'					 "+
			"		AND    nvl(btteam.type_desc, '"+ channelTeamCd +"') = '"+ channelTeamCd +"'	 	 "+
			"		AND    btstaff.basket_id(+) = b.id						 "+
			"		AND    btstaff.type(+) = 'CORE_DE_STAFF'				 "+
			"		AND    ','||'"+ staffGroup +"'||',' like ',%'||nvl(btstaff.type_desc, '"+ staffGroup +"')||'%,' 	 	 "+
			"		AND    btsrvcd.basket_id(+) = b.id						 "+
			"		AND    btsrvcd.type(+) = 'APPL_SRVCD'						 "+
			"		AND    ','||'"+ serviceCdStr +"'||',' like ',%'||nvl(btsrvcd.type_desc, '"+ serviceCdStr +"')||'%,'						 "+
			"		AND 	btsb.basket_id(+) = b.id						 "+
			"		AND 	btsb.type(+) = 'SB_NO'							 "+
			"		AND    nvl(btsb.type_desc, '"+ sbNo +"') = '"+ sbNo +"'	 "+
			"		AND	   IDI.BANDWIDTH IN ('"	+ bandwidthStr + "') 		 "+
			"		AND	   IDI.TECHNOLOGY IN ('" + technologyStr + "')		 "+
			"		AND    BIA.ITEM_ID = I.ID 								 "+
			"		AND    BIA.ITEM_ID = IDI.ITEM_ID						 "+
			"		UNION													 "+
			"		SELECT 	DISTINCT B.ID, btsb.type_desc 					 "+
			"		FROM  	W_BASKET                B,						 "+
			"		  		W_DIC_BASKET_ITEM_ASSGN     BIA,					 "+	
			"		  		W_ITEM                  I,  					 "+
			"				W_BASKET_TYPE			BT,						 "+
			"				w_basket_type 			btteam,	 				 "+
			"				w_basket_type 			btstaff,	 			 "+		 
			"				w_basket_type 			btsrvcd,	 			 "+	
			"				w_basket_type 			btsb,	 				 "+
			"				W_ITEM_DTL_IMS		   	IDI						 "+
			"		WHERE  B.ID = BIA.BASKET_ID								 "+							
			"		AND    B.ID = BT.BASKET_ID								 "+
			"		AND	   BT.TYPE = 'HOUSING'								 "+
			"		AND	   BT.TYPE_DESC IN('" + housingTypeStr + "')		 "+
			"		AND	   IDI.TECHNOLOGY = ?							     "+//--PON--
			"		AND    BIA.ITEM_ID = I.ID 								 "+ 
			"		AND    btstaff.basket_id(+) = b.id							 	 "+
			"		AND    btteam.basket_id(+) = b.id							 	 "+
			"		AND    btteam.type(+) = 'CORE_DE_TEAM'							 "+
			"		AND    nvl(btteam.type_desc, '"+ channelTeamCd +"') = '"+ channelTeamCd +"'	 	 "+
			"		AND    btstaff.type(+) = 'CORE_DE_STAFF'							 "+
			"		AND    ','||'"+ staffGroup +"'||',' like ',%'||nvl(btstaff.type_desc, '"+ staffGroup +"')||'%,' 	 	 "+
			"		AND    btsrvcd.basket_id(+) = b.id						 "+
			"		AND    btsrvcd.type(+) = 'APPL_SRVCD'						 "+
			"		AND    ','||'"+ serviceCdStr +"'||',' like ',%'||nvl(btsrvcd.type_desc, '"+ serviceCdStr +"')||'%,'						 "+
			"		AND 	btsb.basket_id(+) = b.id						 "+
			"		AND 	btsb.type(+) = 'SB_NO'							 "+
			"		AND    nvl(btsb.type_desc, '"+ sbNo +"') = '"+ sbNo +"'	 "+
			"		AND    BIA.ITEM_ID = IDI.ITEM_ID) BASKET_FILTER,		 "+	
			"		w_basket_type btteam_cd 	 							 "+
			"WHERE 	btteam_cd.basket_id(+) = b.id							 "+
			"AND 	btteam_cd.type(+) = 'TEAM_CD'							 "+
			"AND    nvl(btteam_cd.type_desc, '"+ teamCd +"') = '"+teamCd+"'	 "+
			"AND 	 B.ID = BIA.BASKET_ID									 "+					
			"AND    BASKET_FILTER.ID = B.ID									 "+
			"AND    B.ID = TBDL.BASKET_ID(+)								 "+
			"AND    B.ID = SBDL.BASKET_ID(+)								 "+
			"AND    TBDL.LOCALE(+) = ? 								         "+//--LOCALE--
			"AND	TBDL.DISPLAY_TYPE = 'TITLE'								 "+
			"AND    SBDL.LOCALE(+) = ?	 							         "+	//--LOCALE--
			"AND	SBDL.DISPLAY_TYPE = 'SUMMARY'							 "+
			"AND    BIA.ITEM_ID = I.ID 										 "+
			"AND    BIA.ITEM_ID = IP.ID 									 "+		
			"AND    BIA.ITEM_ID = IDRV.ID									 "+
			"AND    I.ID = MFIDL.ITEM_ID(+)									 "+
			"AND    I.ID = MTMIDL.ITEM_ID(+)								 "+										
			"AND    I.ID = BWDIDL.ITEM_ID(+)								 "+													
			"AND    MFIDL.LOCALE(+) = ? 							         "+//--LOCALE--
			"AND	MFIDL.DISPLAY_TYPE = 'MTHFIX'							 "+
			"AND    MTMIDL.LOCALE(+) = ? 							         "+	//--LOCALE--
			"AND	MTMIDL.DISPLAY_TYPE = 'MTH2MTH'							 "+
			"AND    BWDIDL.LOCALE(+) = ? 							         "+	//--LOCALE--
			"AND	BWDIDL.DISPLAY_TYPE = 'BW_DESC'							 "+			
			"AND	PT.ID = B.TYPE											 "+
			"AND	PT.TYPE = 'IMS_PLAN_TYPE'								 "+
			"AND    PT.LOCALE(+) = ? 								         "+//--LOCALE--
			"AND    NVL(BIA.EFF_END_DATE,"+appDate+")>="+appDate+"			 "+
			"AND    BIA.EFF_START_DATE<="+appDate+" 						 "+
			"AND    NVL(IP.EFF_END_DATE,"+appDate+")>="+appDate+"			 "+
			"AND    IP.EFF_START_DATE<="+appDate+" 							 "+
			"AND    BIA.ITEM_ID = IOA.ITEM_ID								 "+
			"AND    BIA.ITEM_ID = IDI.ITEM_ID								 "+
			"AND	IOA.OFFER_TYPE IN ('S','V')								 "+
			"AND    I.TYPE IN ('PROG')  									 "+
			"AND	I.LOB = 'IMS'											 "+ 
			"AND exists (SELECT * FROM W_CHANNEL_BASKET_ASSGN WHERE BASKET_ID = B.ID AND CHANNEL_ID = "+CHANNEL_ID+")" ;
			if(sbFilterVas) SQL += "and not exists (select * from w_code_lkup where grp_Id='IMS_FILTERBY_BASK_ID' and code = b.id)	";
			if("Y".equalsIgnoreCase(ltsServiceInd)) SQL += "AND NOT EXISTS (SELECT *  " +
														   "FROM w_code_lkup wcl, w_item_offer_product_assgn wiopa " +
														   "WHERE wiopa.item_id=I.ID " +
														   "AND wiopa.prod_cd=wcl.code " +
														   "AND grp_id = 'IMSSYNCPRDHASDEL' )   ";			
			
			SQL +=  "ORDER BY IDI.BANDWIDTH DESC, B.TYPE ASC, BASKET_FILTER.type_desc nulls last, 	"+
			"		  IDRV.CONTRACT_PERIOD DESC, BIA.EFF_START_DATE DESC, 		 "+
			"		  IP.RECURRENT_AMT DESC 								";	
		
		ParameterizedRowMapper<ImsBasketDTO> mapper = new ParameterizedRowMapper<ImsBasketDTO>() {
	        public ImsBasketDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
	        	ImsBasketDTO basket = new ImsBasketDTO();
	        	basket.setPlanType(rs.getString("PLAN_TYPE"));
	        	basket.setBasketId(rs.getString("BASKET_ID"));
	        	basket.setTitle(rs.getString("TITLE"));
	        	basket.setSummary(rs.getString("SUMMARY"));
	        	basket.setRecurrentAmt(rs.getString("recurrent_amt"));
	        	basket.setMthFixText(rs.getString("MTHFIX"));
	        	basket.setMthToMthRate(rs.getString("MTH_TO_MTH_RATE"));
	        	basket.setMthToMthText(rs.getString("MTH2MTH"));
	        	basket.setBandwidth_desc(rs.getString("bw_desc"));
	        	basket.setContractPeriod(rs.getString("CONTRACT_PERIOD"));
	        	basket.setItemId(rs.getString("ID"));	        	
	        	basket.setOfferCode(rs.getString("OFFER_CD"));
	        	basket.setImagePath(rs.getString("IMAGE_PATH"));        	
	        	basket.setBandwidth(rs.getString("BANDWIDTH"));
	        	basket.setItemType(rs.getString("TYPE")); 
	        	basket.setIsPreInst(rs.getString("IS_PREINSTALLATION"));
	        	basket.setTechnology(rs.getString("technology"));
	        	basket.setIsPreUse(rs.getString("IS_PREUSE"));
	        	basket.setHasGoogleRouter(rs.getString("HAS_GOOGLE_ROUTER"));

	            return basket;
	        }
	    };

		try {
			logger.info("Bandwidth Type:" + bandwidthStr);
			logger.info("Technology Type:" + technologyStr);
			logger.info("Housing Type:" + housingTypeStr);
			logger.info("serviceCdStr:" + serviceCdStr);
			logger.info("channelTeamCd:" + channelTeamCd);
			logger.info("staffGroup:" + staffGroup);
			logger.info("PON:" + IsPON);
			logger.debug("getImsBasketList @ BasketSelectDAO: " + SQL);
			imsBasketList = simpleJdbcTemplate.query(SQL, mapper,IsPON,locale,locale,locale,locale,locale,locale);
		} catch (EmptyResultDataAccessException erdae) {
			logger.debug("EmptyResultDataAccessException");
			imsBasketList = null;
		} catch (Exception e) {
			logger.debug("Exception caught in getBasketList():", e);
			throw new DAOException(e.getMessage(), e);
		}	
		return imsBasketList;
	}
	
	public List<String> getImsPlanTypeList(String locale) throws DAOException {
		List<String> imsPlanTypeList = new ArrayList<String>();

		String SQL =

			"SELECT * FROM W_DISPLAY_LKUP										\n"
			+ "		WHERE TYPE = 'IMS_PLAN_TYPE'								\n"
			+ "		AND LOCALE = ? 					--LOCALE--					\n"
			+ "		ORDER BY ID 												";

		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
			public String mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				String ImsPlanType = new String();
				ImsPlanType = rs.getString("DESCRIPTION");
				return ImsPlanType;
			}
		};

		try {
			logger.debug("getImsPlanTypeList @ BasketSelectDAO: " + SQL);
			imsPlanTypeList = simpleJdbcTemplate.query(SQL, mapper, locale);
		} catch (EmptyResultDataAccessException erdae) {
			logger.debug("EmptyResultDataAccessException");
			imsPlanTypeList = null;
		} catch (Exception e) {
			logger.debug("Exception caught in getBasketList():", e);
			throw new DAOException(e.getMessage(), e);
		}
		return imsPlanTypeList;
	}
	
	public List<String> getImsPreTickPlanTypeList(String locale) throws DAOException {
		List<String> imsPlanTypeList = new ArrayList<String>();

		String SQL =

			"SELECT a.description FROM W_DISPLAY_LKUP a, w_code_lkup b			\n"
			+ "		WHERE a.TYPE = 'IMS_PLAN_TYPE'								\n"
			+ "		and b.GRP_ID = 'IMS_PRE_TICK_PLAN_ACQ'						\n"
			+ "		and a.id = b.code											\n"
			+ "		AND LOCALE = ? 					--LOCALE--					\n"
			+ "		ORDER BY ID 												";

		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
			public String mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				String ImsPlanType = new String();
				ImsPlanType = rs.getString("DESCRIPTION");
				return ImsPlanType;
			}
		};

		try {
			logger.debug("getImsPlanTypeList @ BasketSelectDAO: " + SQL);
			imsPlanTypeList = simpleJdbcTemplate.query(SQL, mapper, locale);
		} catch (EmptyResultDataAccessException erdae) {
			logger.debug("EmptyResultDataAccessException");
			imsPlanTypeList = null;
		} catch (Exception e) {
			logger.debug("Exception caught in getBasketList():", e);
			throw new DAOException(e.getMessage(), e);
		}
		return imsPlanTypeList;
	}
	
	public String getStaffGroup(String staffId)throws DAOException  {
		List<String> staffGroup = new ArrayList<String>();
			
		String SQL =
			"select rtrim (xmlagg (xmlelement (e, code_id||',' )).extract ('//text()'), ',') staff_group " +
			"from bomweb_code_lkup " +
			"where code_type = 'IMS_ACQ_STAFFLIST' " +
			"and code_desc = '"+staffId+"'";
		
		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
			public String mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				String aa = rs.getString("staff_group");

				return aa;
			}
		};
		
		staffGroup  = simpleJdbcTemplate.query(SQL, mapper);		
		
		return (staffGroup==null || staffGroup.size()==0) ? "":staffGroup.get(0);
	}
	
}

