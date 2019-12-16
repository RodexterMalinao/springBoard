package com.bomwebportal.ims.dao;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.bomwebportal.dao.*;
import com.bomwebportal.dto.*;
import com.bomwebportal.ims.dto.*;
import com.bomwebportal.ims.dto.ui.SubscribedItemUI;
import com.bomwebportal.ims.dto.ui.VASDetailUI;
import com.bomwebportal.util.NTVUtil;
import com.bomwebportal.exception.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VASDetailDAO extends BaseDAO{
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	public List<VASDetailUI> getBVASDetailList (String locale, String basketID, String appDate )throws DAOException {
		List<VASDetailUI> BVASDetailsList = new ArrayList<VASDetailUI>();

		String SQL=

			"SELECT NVL(VT.DESCRIPTION, '') VAS_TYPE_DETAIL,							 "+ 		
			"	BIA.BASKET_ID, 							 "+	  																			
			"	NVL(DIDL.HTML, '') ITEM_DETAIL,			 "+	  		
			"	BIA.MDO_IND,							 "+	  		
			"	VT.TYPE VAS_TYPE,						 "+	  		
			"	VT.ID VAS_SEQ,							 "+	  		
			"	IP.RECURRENT_AMT,						 "+	  		
			"	MFIDL.HTML MTHFIX, 						 "+	  		
			"	IP.MTH_TO_MTH_RATE,						 "+	  		
			"	MTMIDL.HTML MTH2MTH,						 "+	  								
			"	NVL(IDRV.CONTRACT_PERIOD,'0') CONTRACT_PERIOD,			 "+	  		
			"   	I.ID,								 "+	  		
			" 	I.TYPE,								 "+
			" 	ATTB.ATTB_ID,							 "+
			" 	ATTB.ATTB_DESC,							 "+
			" 	ATTB.INPUT_METHOD,							 "+	
			" 	WIOPA.PRODUCT_ID							 "+	
			"FROM  	W_BASKET                B,					 "+	  		
			"  	W_DIC_BASKET_ITEM_ASSGN     BIA,					 "+	  		
			"  	W_ITEM                  I,  					 "+	  		
			"  	W_ITEM_DTL_RP_RB_VAS    IDRV,					 "+	  		
			"	W_ITEM_DTL_IMS    		IDI,				 "+	  											
			"	W_ITEM_PRICING			IP,				 "+	  		
			"	W_ITEM_DISPLAY_LKUP   DIDL,					 "+	  		
			"	W_ITEM_DISPLAY_LKUP   MFIDL,					 "+	  		
			"	W_ITEM_DISPLAY_LKUP   MTMIDL,					 "+	  		
			"	W_DISPLAY_LKUP   VT,						 "+
			"	W_ITEM_OFFER_PRODUCT_ASSGN   WIOPA,						 "+
			"	(SELECT IOPA.ITEM_ID, 				 "+
			"	   		AL.ATTB_ID, 				 "+
			"	   		AL.DESCRIPTION ATTB_DESC, 				 "+
			"	   		AL.INPUT_METHOD				 "+
			"	 FROM   W_ITEM_OFFER_PRODUCT_ASSGN IOPA,				 "+
			"	  		W_PRODUCT_ATTB_ASSGN		  PAA,				 "+
			"	   		W_ATTB_LKUP				  AL				 "+
			"	 WHERE  IOPA.PRODUCT_ID = PAA.PRODUCT_ID				 "+
			"	 AND    PAA.ATTB_ID = AL.ATTB_ID						 "+
			"	 	AND    AL.LOCALE = ? ) ATTB					 "+//--LOCALE--
			"WHERE  B.ID = ?						 "+	  		//--BASKET ID--
			"AND	   B.ID = BIA.BASKET_ID						 "+	  																								
			"AND    BIA.ITEM_ID = I.ID 						 "+	  		
			"AND    BIA.ITEM_ID = IP.ID 						 "+	  		
			"AND    BIA.ITEM_ID = IDRV.ID						 "+
			"AND    BIA.ITEM_ID = ATTB.ITEM_ID(+)				 "+
			"AND    I.ID = DIDL.ITEM_ID(+)						 "+	  											
			"AND    DIDL.LOCALE(+) = ? 					 "+	  		//--LOCALE--
			"AND	   DIDL.DISPLAY_TYPE = 'DETAIL'					 "+	  		
			"AND    I.ID = MFIDL.ITEM_ID(+)						 "+	  		
			"AND    I.ID = MTMIDL.ITEM_ID(+)					 "+	  									
			"AND    MFIDL.LOCALE(+) = ? 					 "+	  	//	--LOCALE--
			"AND	   MFIDL.DISPLAY_TYPE = 'MTHFIX'				 "+	  		
			"AND    MTMIDL.LOCALE(+) = ?	 				 "+	  		//--LOCALE--
			"AND	   MTMIDL.DISPLAY_TYPE = 'MTH2MTH'				 "+	  		
			"AND	   VT.TYPE = 'IMS_ITYPE_' || I.TYPE				 "+	  		
			"AND    VT.LOCALE(+) = ? 							 "+	  		//--LOCALE--
			"AND    NVL(BIA.EFF_END_DATE,"+appDate+")>="+appDate+"				 "+	  		
			"AND    BIA.EFF_START_DATE<="+appDate+" 					 "+	  		
			"AND    NVL(IP.EFF_END_DATE,"+appDate+")>="+appDate+"				 "+	  		
			"AND    IP.EFF_START_DATE<="+appDate+" 					 "+	  				
			"AND    BIA.ITEM_ID = IDI.ITEM_ID(+)					 "+	  
			"AND    I.ID = WIOPA.ITEM_ID(+)							 "+
			//"AND    I.TYPE NOT IN ('PROG','NTV_FREE','NTV_PAY','NTV_OTHER')  				 "+
			"AND    (I.TYPE IN (select code From W_CODE_LKUP where grp_id='SB_IMS_OPT_SRV')	  "+
			"		OR I.TYPE IN ('PRE_INST','BVAS','OTHER'))								 "+
			"AND	   I.LOB = 'IMS'						 "+	  		
			"ORDER BY VAS_SEQ ASC, ID ASC							";
		
		ParameterizedRowMapper<VASDetailUI> mapper = new ParameterizedRowMapper<VASDetailUI>() {
	        public VASDetailUI mapRow(ResultSet rs, int rowNum) throws SQLException {
	        	VASDetailUI BVASDetail = new VASDetailUI();
	        	
	        	BVASDetail.setVASTypeDtl(rs.getString("VAS_TYPE_DETAIL")); 
	        	BVASDetail.setBasketId(rs.getString("BASKET_ID")); 																						
	    		BVASDetail.setVASHtml(rs.getString("ITEM_DETAIL"));
	    		BVASDetail.setItemMdoInd(rs.getString("MDO_IND"));  
	    		BVASDetail.setVASType(rs.getString("VAS_TYPE"));   
	    		BVASDetail.setRecurrentAmt(rs.getString("RECURRENT_AMT"));
	    		BVASDetail.setMthFixText(rs.getString("MTHFIX"));	
	    		BVASDetail.setMthToMthRate(rs.getString("MTH_TO_MTH_RATE"));		
	    		BVASDetail.setMthToMthText(rs.getString("MTH2MTH"));																	
	    		BVASDetail.setItemId(rs.getString("ID"));													
	    		BVASDetail.setItemType(rs.getString("TYPE"));
	    		BVASDetail.setAttbId(rs.getString("ATTB_ID"));	
	    		BVASDetail.setAttbDesc(rs.getString("ATTB_DESC"));
	    		BVASDetail.setInputMethod(rs.getString("INPUT_METHOD"));
	    		BVASDetail.setProductId(rs.getString("PRODUCT_ID"));        	
	            return BVASDetail;
	        }
	    };

		try {
			logger.debug("getBVASDetailsList @ VASDetailDAO: " + SQL);
			BVASDetailsList = simpleJdbcTemplate.query(SQL, mapper,locale,basketID,locale,locale,locale,locale);
		} catch (EmptyResultDataAccessException erdae) {
			logger.debug("EmptyResultDataAccessException");
			BVASDetailsList = null;
		} catch (Exception e) {
			logger.debug("Exception caught in getBVASDetailList():", e);
			throw new DAOException(e.getMessage(), e);
		}	
		return BVASDetailsList;
	}
	public List<VASDetailUI> getVASDetailList (String locale, String basketID, String appDate,String bandwidth, String technology,String housingType,  Boolean sbFilterVas, String sales_channel, String preInstallInd, String like100Ind, String mobileOfferInd, String preUseInd, String sbNo, String supremeFsInd,
			String channelTeamCd, String staffGroup, String serviceCdStr, String progOfferCd, String otChrgInd)throws DAOException {
		List<VASDetailUI> VASDetailsList = new ArrayList<VASDetailUI>();
		
		String dummyBasket_id =  "200999999";//retail
		String dummyBasket_id_b =  "200999998";
		//String dummyBasket_id_all = "200999999,200999998";
		
		if("Y".equalsIgnoreCase(mobileOfferInd)){
			dummyBasket_id = "200999994";//retail mobile offer
		}
		
		if(sales_channel.equals("Y"))
		{
			if("Y".equalsIgnoreCase(mobileOfferInd)){
				dummyBasket_id = "200999993"; //call center mobile offer
			}else{
				dummyBasket_id = "200999998"; //call center
			}
			dummyBasket_id_b = "200999999";
		}
		
		final String preInstInd = preInstallInd;
		
		String SQL=

			"SELECT  VT.DESCRIPTION VAS_TYPE_DETAIL,																						 "+
			"		NVL(DIDL.HTML, '') ITEM_DETAIL, "+
			"		VT.TYPE VAS_TYPE, "+
			"		VT.ID VAS_SEQ, "+
			"		IP.RECURRENT_AMT, "+
			"		MFIDL.HTML MTHFIX, 	 "+
			"		IP.MTH_TO_MTH_RATE,		 "+
			"		MTMIDL.HTML MTH2MTH,															 "+
			"		NVL(IDRV.CONTRACT_PERIOD,'0') VAS_CONTRACT_PERIOD,			 "+
			"   		I.ID,													 "+
			" 		I.TYPE,  "+
			"		ALM.description alertMsg ,	"+	//Gary
			"		ALM.html preInstAlertMsg ,	"+	
			"      	NVL(RP_T.CONTRACT_PERIOD, 0) RP_CONTRACT_PERIOD,  "+
			"        	DECODE (SUBSTR (TO_CHAR ( "+
			" 								 NVL (IDRV.CONTRACT_PERIOD, 0) - NVL(RP_T.CONTRACT_PERIOD, 0) "+
			" 						), 1, 1), '0', 0, '-', 1, 2) RP_VAS_CONTRACT_MATCH,								 "+
			" 		ATTB.ATTB_ID,							 "+
			" 		ATTB.ATTB_DESC,							 "+
			" 		ATTB.INPUT_METHOD,							 "+
			" 		WIOPA.PRODUCT_ID,							 "+
			"      	NVL(widi.WAIVE_ISF_IND, 'N') WAIVE_ISF_IND , "+
			"      	NVL(widi.WAIVE_ASF_IND, 'N') WAIVE_ASF_IND , "+
			" 		(SELECT TYPE_DESC FROM W_ITEM_TYPE A WHERE A.ITEM_ID = I.ID AND A.TYPE = 'SB_NO' AND nvl(A.TYPE_DESC, '"+ sbNo +"') = '"+ sbNo +"') DEDI_SB, 	 "+
			" 		(SELECT TYPE_DESC FROM W_ITEM_TYPE B WHERE B.ITEM_ID = I.ID AND B.TYPE = 'VAS_DE_OFFER' AND nvl(B.TYPE_DESC, '"+ progOfferCd +"') = '"+ progOfferCd +"') DEDI_OFFER ,	 "+
			" 		(SELECT TYPE_DESC FROM W_ITEM_TYPE C WHERE C.ITEM_ID = I.ID AND C.TYPE = 'PAYMENT' ) PAYMENT_MTD ,	 "+
			"       (SELECT nvl(MDO_IND,'O') FROM W_DIC_BASKET_ITEM_ASSGN WDIA WHERE BASKET_ID = "+dummyBasket_id+" AND WDIA.ITEM_ID = I.ID) VAS_MDO_ID "+		
			"FROM  	W_ITEM                  I,  							 "+
			"  		W_ITEM_DTL_RP_RB_VAS    IDRV,																			 "+
			"		W_ITEM_PRICING			IP,							 "+ 
			"		W_ITEM_DISPLAY_LKUP   DIDL, "+
			"		W_ITEM_DISPLAY_LKUP   MFIDL, "+
			"		W_ITEM_DISPLAY_LKUP   ALM,			"+//Gary
			"		W_ITEM_DISPLAY_LKUP   MTMIDL,	 "+
			"		W_DISPLAY_LKUP   VT, "+
			"    	w_item_dtl_ims widi,                                                       	           "+
			"		W_ITEM_OFFER_PRODUCT_ASSGN WIOPA, "+
			"		(SELECT DISTINCT IOPA.ITEM_ID ID  "+
			"		FROM   W_ITEM_OFFER_PRODUCT_ASSGN IOPA, "+
			"			   W_ITEM I, "+
			"			   W_ITEM_TYPE   TEAMIT, "+
			"			   W_ITEM_TYPE   STAFFIT, "+
			"			   W_ITEM_TYPE   SRVCODEIT, "+
			"			   W_ITEM_TYPE   LIKEIT, "+
			"			   W_ITEM_TYPE   PROGOFFERIT "+
			"		WHERE PRODUCT_ID IN  "+
			"			  (	SELECT DISTINCT PRODUCT_ID 					  "+
			"		  	FROM   W_ITEM_OFFER_PRODUCT_ASSGN					  "+
			"		   	WHERE  ITEM_ID	IN(							  "+
			"			SELECT distinct wi.id												    	 "+
			"  			FROM W_DIC_BASKET_ITEM_ASSGN wbia, w_item wi, w_item_pricing wip                "+
			" 			WHERE wi.TYPE IN (SELECT code FROM w_code_lkup WHERE grp_id = 'SB_IMS_OPT_SRV') "+
			"   		AND wi.ID = wbia.item_id                                                  "+
			"   		AND wi.ID = wip.id                                                "+
			"   		AND nvl(wip.eff_end_date,"+appDate+") >= "+appDate+"                                                "+
			"   		AND wip.eff_start_date <= "+appDate+"                                                "+
			"   		AND nvl(wbia.eff_end_date,"+appDate+") >= "+appDate+"                                                "+
			"   		AND wbia.eff_start_date <= "+appDate+"                                                 "+
			"   		AND basket_id = "+dummyBasket_id+
			"			)									  "+
			"			MINUS								  "+
			"			SELECT DISTINCT IOPA.PRODUCT_ID					  "+
			"			FROM   W_DIC_BASKET_ITEM_ASSGN BIA,					  "+
			"				   W_ITEM_OFFER_PRODUCT_ASSGN IOPA			  "+
			"			WHERE  IOPA.ITEM_ID = BIA.ITEM_ID				  "+
			"			AND    BIA.BASKET_ID = ?				  "+//--BASKET ID --
			"			AND    NVL(BIA.EFF_END_DATE,"+appDate+")>="+appDate+"		  "+
			"			AND    BIA.EFF_START_DATE<="+appDate+" 				  "+
			"			  ) "+
			"		AND I.ID = IOPA.ITEM_ID "+
			"		AND I.LOB = 'IMS' "+
			"		AND I.ID IN (SELECT ITEM_ID from W_DIC_BASKET_ITEM_ASSGN WHERE BASKET_ID = "+dummyBasket_id+") "+
			"		AND I.ID = TEAMIT.ITEM_ID(+)		 "+
			"		AND I.ID = STAFFIT.ITEM_ID(+)               "+
			"		AND I.ID = SRVCODEIT.ITEM_ID(+)               "+
			"		AND I.ID = PROGOFFERIT.ITEM_ID(+)               "+
			"		AND I.ID = LIKEIT.ITEM_ID               "+
			"		AND TEAMIT.TYPE(+) = 'VAS_DE_TEAM'		 "+
			"		AND STAFFIT.TYPE(+) = 'VAS_DE_STAFF'		 "+
			"		AND SRVCODEIT.TYPE(+) = 'APPL_SRVCD'		 "+
			"		AND PROGOFFERIT.TYPE(+) = 'VAS_DE_OFFER'		 "+
			"		AND LIKEIT.TYPE = 'BRAND'		 "+
			"		AND nvl(TEAMIT.TYPE_DESC, '"+ channelTeamCd +"') = '"+ channelTeamCd +"'             "+
			"		AND ','||'"+ staffGroup +"'||',' like ',%'||nvl(STAFFIT.TYPE_DESC, '"+ staffGroup +"')||'%,'              "+
			"		AND ','||'"+ serviceCdStr +"'||',' like ',%'||nvl(SRVCODEIT.TYPE_DESC, '"+ serviceCdStr +"')||'%,'              "+
			"		AND nvl(PROGOFFERIT.TYPE_DESC, '"+ progOfferCd +"') = '"+ progOfferCd +"'             "+
			"		AND LIKEIT.TYPE_DESC = DECODE('"+like100Ind+"','Y','LIKE','NETVIGATOR')            "+
//			"		MINUS									 	 "+
//			"		SELECT DISTINCT I.ID			 "+
//			"		FROM   W_DIC_BASKET_ITEM_ASSGN BIA,			 "+
//			"		       W_ITEM  I			 "+
//			"		WHERE  I.ID = BIA.ITEM_ID				 "+
//			"		AND    NVL(BIA.EFF_END_DATE,SYSDATE)>=SYSDATE	 "+
//			"		AND    BIA.EFF_START_DATE<=SYSDATE 	 "+
//			"		and bia.BASKET_ID not in ( "+dummyBasket_id+")" + 
//					"and not exists (select * from W_DIC_BASKET_ITEM_ASSGN where item_id = i.id and basket_id =  "+dummyBasket_id_b+")"+	//by jacky
//			"		AND	   I.LOB = 'IMS'			 "+
//			"		UNION								 "+
//			"		SELECT DISTINCT I.ID  "+
//			"		FROM   W_ITEM I, "+
//			"			   W_DIC_BASKET_ITEM_ASSGN BIA "+
//			"		WHERE  BIA.BASKET_ID =?			 "+//--BASKET ID --
//			"		AND	   BIA.ITEM_ID = I.ID	    "+
//			"		AND    NVL(BIA.EFF_END_DATE,"+appDate+")>="+appDate+"	 "+
//			"		AND    BIA.EFF_START_DATE<="+appDate+" 	 "+
////			"		AND    I.TYPE IN ('OPT_PREM','WL_BB','ANTI_VIR','MEDIA') "+
//			"		AND    I.TYPE IN (select code From W_CODE_LKUP where grp_id='SB_IMS_OPT_SRV') "+
//			"		AND    I.LOB = 'IMS' "+
			"		) ITEM_FILTER, "+
			"		(SELECT MAX(IDRRV.CONTRACT_PERIOD) CONTRACT_PERIOD   "+
			"  	   	  FROM 	 W_DIC_BASKET_ITEM_ASSGN BIA,  "+
			" 	  			 W_ITEM I,  "+
			" 				 W_ITEM_DTL_RP_RB_VAS IDRRV   "+
			"  	   	  WHERE  BIA.ITEM_ID = I.ID   "+
			"  	   	  AND 	 I.ID = IDRRV.ID   "+
			"  	   	  AND 	 I.TYPE IN ('PROG','PRE_INST')   "+
			"  	   	  AND 	 BIA.BASKET_ID = ? 			  "+//--BASKET ID --
			"	   	 ) RP_T,						 "+
			"		(SELECT IOPA.ITEM_ID, 				 "+
			"	   			AL.ATTB_ID, 				 "+
			"	   			AL.DESCRIPTION ATTB_DESC, 				 "+
			"	   			AL.INPUT_METHOD				 "+
			"	 	FROM   W_ITEM_OFFER_PRODUCT_ASSGN IOPA,				 "+
			"	  			W_PRODUCT_ATTB_ASSGN		  PAA,				 "+
			"	   			W_ATTB_LKUP				  AL				 "+
			"	 	WHERE  IOPA.PRODUCT_ID = PAA.PRODUCT_ID				 "+
			"	 	AND    PAA.ATTB_ID = AL.ATTB_ID						 "+
			"	 	AND    AL.LOCALE = ? ) ATTB					 "+//--LOCALE--
			"WHERE  I.ID = ITEM_FILTER.ID "+
			"AND	   I.ID = DIDL.ITEM_ID	 "+
			"AND    I.ID = IP.ID		 "+
			"AND    I.ID = WIOPA.ITEM_ID(+)							 "+
			"AND	   I.ID = IDRV.ID														 "+
			"AND    I.ID = ATTB.ITEM_ID(+)				 "+
			"AND 	I.ID = WIDI.ITEM_ID(+)               "+
			"AND 	(exists (select 1 from w_item_type IT where I.ID = IT.ITEM_ID and IT.TYPE = 'SB_NO' and nvl(IT.TYPE_DESC, '"+ sbNo +"') = '"+ sbNo +"')             "+
			"OR (widi.housing_type is null or ','||replace(widi.housing_type,' ','')||',' like '%,'||'"+housingType +"'||',%')) "+ 
			"AND nvl(decode(widi.technology,' ',null,technology),'"+technology+"') like '%"+technology+"%' " +
			"AND to_number('"+bandwidth+"')>=to_number(nvl(widi.bandwidth,'"+bandwidth+"')) "+ 
//			"AND nvl('"+like100Ind+"','N')=nvl(widi.is_like_100,'N') "+ 
			"AND    DIDL.LOCALE = ? 								 "+//--LOCALE--
			"AND	   DIDL.DISPLAY_TYPE = 'DETAIL'			 "+
			"AND    I.ID = MFIDL.ITEM_ID			 "+
			"AND    I.ID = MTMIDL.ITEM_ID																 "+
			"AND    MFIDL.LOCALE = ? 								 "+//--LOCALE--
			"AND	   MFIDL.DISPLAY_TYPE = 'MTHFIX'			 "+
			"AND    MTMIDL.LOCALE = ? 								 "+//--LOCALE--
			"AND	   MTMIDL.DISPLAY_TYPE = 'MTH2MTH'	 "+
			"AND	   VT.TYPE = 'IMS_ITYPE_' || I.TYPE "+
			"AND    VT.LOCALE = ? 										 "+//--LOCALE--
			"AND    NVL(IP.EFF_END_DATE,"+appDate+")>="+appDate+"	 "+
			"AND    IP.EFF_START_DATE<="+appDate+" 												 "+
			"AND	   I.LOB = 'IMS' "+
			"AND	decode('"+preInstallInd+"','Y','Y',nvl(widi.is_preinstallation,'N'))=nvl(widi.is_preinstallation,'N') "+
			"AND	decode('"+preUseInd+"','Y','Y',nvl(widi.is_preuse,'N'))=nvl(widi.is_preuse,'N') "+
			"AND (I.id = ALM.item_id(+) and ALM.locale(+) = 'en' and ALM.display_type(+) = 'ALERTMSG' )			"+//Gary
			"AND	I.TYPE <> 'BVAS' ";
			if(sbFilterVas) SQL += "and not exists (select * from w_code_lkup where grp_Id='IMS_FILTERBY_ITEM_ID' and code = i.id)	";
			if("Y".equalsIgnoreCase(supremeFsInd)) SQL+= "and not exists (select * from w_item a where a.id = i.id and a.type = 'SUP_FIELD')	";
			if("W".equalsIgnoreCase(otChrgInd)){
				SQL +=  "AND NVL(widi.WAIVE_ISF_IND,'N')||NVL(widi.WAIVE_ASF_IND,'N') = 'NN' AND I.TYPE NOT IN ('CLUB_PT_REDM_ISF')		\n";
			}else if("I".equalsIgnoreCase(otChrgInd)){
				SQL +=  "AND NVL(widi.WAIVE_ISF_IND,'N')||NVL(widi.WAIVE_ASF_IND,'N') <> 'NY'		\n";
			}else if("A".equalsIgnoreCase(otChrgInd)){
				SQL +=  "AND NVL(widi.WAIVE_ISF_IND,'N')||NVL(widi.WAIVE_ASF_IND,'N') <> 'YN'		\n";
			}
			SQL +="ORDER BY VAS_SEQ ASC, DEDI_OFFER nulls last, DEDI_SB nulls last, I.DISPLAY_SEQ, ID ASC		";

		ParameterizedRowMapper<VASDetailUI> mapper = new ParameterizedRowMapper<VASDetailUI>() {
	        public VASDetailUI mapRow(ResultSet rs, int rowNum) throws SQLException {
	        	VASDetailUI VASDetail = new VASDetailUI();
        	
	        	VASDetail.setVASTypeDtl(rs.getString("VAS_TYPE_DETAIL"));  																						
	        	VASDetail.setVASHtml(rs.getString("ITEM_DETAIL"));  
	        	VASDetail.setVASType(rs.getString("VAS_TYPE"));   
	        	VASDetail.setRecurrentAmt(rs.getString("RECURRENT_AMT"));
	        	VASDetail.setMthFixText(rs.getString("MTHFIX"));	
	        	VASDetail.setMthToMthRate(rs.getString("MTH_TO_MTH_RATE"));		
	        	VASDetail.setMthToMthText(rs.getString("MTH2MTH"));
	        	if("Y".equalsIgnoreCase(preInstInd)){
		        	VASDetail.setVASPreInstAlertMsg(rs.getString("preInstAlertMsg"));
	        	}else{
	        		VASDetail.setVASPreInstAlertMsg("");
	        	}
	        	VASDetail.setVASAlertMsg(rs.getString("alertMsg"));
	    		VASDetail.setVASContractPeriod(rs.getString("vas_contract_period"));
	    		VASDetail.setItemId(rs.getString("ID"));													
	    		VASDetail.setItemType(rs.getString("TYPE"));	
	    		VASDetail.setRPContractPeriod(rs.getString("rp_contract_period"));
	        	VASDetail.setRPVASContractMatch(rs.getString("rp_vas_contract_match"));
	    		VASDetail.setAttbId(rs.getString("ATTB_ID"));	
	    		VASDetail.setAttbDesc(rs.getString("ATTB_DESC"));
	        	VASDetail.setInputMethod(rs.getString("INPUT_METHOD"));
	        	VASDetail.setProductId(rs.getString("PRODUCT_ID"));	        	
	        	VASDetail.setWaiveISFInd(rs.getString("WAIVE_ISF_IND"));
	        	VASDetail.setWaiveASFInd(rs.getString("WAIVE_ASF_IND"));
	        	VASDetail.setPaymentMtd(rs.getString("PAYMENT_MTD"));
	    		VASDetail.setItemMdoInd(rs.getString("VAS_MDO_ID")); 
	            return VASDetail;
	        }
	    };

		try {
			logger.debug("getVASDetailsList @ VASDetailDAO: " + SQL);
			logger.debug("technology = "+ technology);
			//VASDetailsList = simpleJdbcTemplate.query(SQL, mapper,basketID,basketID,basketID,locale,locale,locale,locale,locale);
			VASDetailsList = simpleJdbcTemplate.query(SQL, mapper,basketID,basketID,locale,locale,locale,locale,locale);
			
			if (VASDetailsList != null && VASDetailsList.size() > 0) {
				for (VASDetailUI v: VASDetailsList) {
					v.setVASTypeDtl(NTVUtil.defaultString(v.getVASTypeDtl()));
					v.setVASHtml(NTVUtil.defaultString(v.getVASHtml()));
				}
			}
		} catch (EmptyResultDataAccessException erdae) {
			logger.debug("EmptyResultDataAccessException");
			VASDetailsList = null;
		} catch (Exception e) {
			logger.debug("Exception caught in getVASDetailList():", e);
			throw new DAOException(e.getMessage(), e);
		}	
		return VASDetailsList;
	}
	
	public List<VASDetailUI>getExclusiveItemList(String locale, List<String> selectedList)
	throws DAOException {
		return getExclusiveItemList(locale,selectedList,selectedList);
	}
	
	public List<VASDetailUI> getExclusiveItemList(String locale, List<String> selectedVasList,List<String> selectedGiftList)
	throws DAOException {

		List<VASDetailUI> exclusiveItemList = new ArrayList<VASDetailUI>();
		logger.info("getExclusiveItemList is called");
		
		String SQL = "";
		
		SQL = " SELECT IELM.ITEM_ID_A,  					 "+
					 "	   IELM.ITEM_TYPE_A,  					 "+
					 " 	   IELM.ITEM_ID_B, 						 "+
					 "       IELM.ITEM_TYPE_B, 					 "+ 
					 " 	   IA.HTML ITEM_HTML_A,	 				 "+ 
					 "	   IB.HTML ITEM_HTML_B, 	 			 "+
					 " 	   IA.DESCRIPTION ITEM_DESCRIPTION_A,	 "+ 
					 "	   IB.DESCRIPTION ITEM_DESCRIPTION_B 	 "+
					 "FROM   W_ITEM_EXCLUSIVE_LKUP_MV IELM, 	 "+
					 "	   (SELECT ITEM_ID ID,					 "+
					 "	   		   HTML HTML,				 	 "+
					 "	   		   DESCRIPTION DESCRIPTION		 "+
					 "		FROM   W_ITEM_DISPLAY_LKUP			 "+
					 "		WHERE  DISPLAY_TYPE = 'DETAIL'		 "+
					 "		AND    LOCALE = '"+locale+"'		 "+
					 "	   ) IA, 								 "+
					 "	   (SELECT ITEM_ID ID,					 "+
					 "	   		   HTML HTML,				 	 "+
					 "	   		   DESCRIPTION DESCRIPTION		 "+
					 "		FROM   W_ITEM_DISPLAY_LKUP			 "+
					 "		WHERE  DISPLAY_TYPE = 'DETAIL'		 "+
					 "		AND    LOCALE = '"+locale+"'		 "+
					 "	   ) IB 								 "+
					 "WHERE  IA.ID = IELM.ITEM_ID_A 			 "+
					 "AND    IB.ID = IELM.ITEM_ID_B 			 ";


		String itemASql = "";
		String itemBSql = "";

		if ((selectedVasList != null && selectedVasList.size() > 0) 
				|| (selectedGiftList!=null && selectedGiftList.size() > 0)) {
			itemASql += "AND    IELM.ITEM_ID_A IN (";
			itemBSql += "AND    IELM.ITEM_ID_B IN (";
			if(selectedVasList != null && selectedVasList.size() > 0){
				for (int i = 0; i < selectedVasList.size(); i++) {
					itemASql += selectedVasList.get(i) + ",";
				}
			}
			if(selectedGiftList != null && selectedGiftList.size() > 0){
				for (int i = 0; i < selectedGiftList.size(); i++) {
					itemASql += selectedGiftList.get(i) + ",";
				}
			}
			if(selectedVasList != null && selectedVasList.size() > 0){
				for (int i = 0; i < selectedVasList.size(); i++) {
					itemBSql += selectedVasList.get(i) + ",";
				}
			}
			if(selectedGiftList != null && selectedGiftList.size() > 0){
				for (int i = 0; i < selectedGiftList.size(); i++) {
					itemBSql += selectedGiftList.get(i) + ",";
				}
			}
			itemASql += ") ";
			itemBSql += ") ";
			itemASql = itemASql.replace(",)", ")");// replace the last comma
			itemBSql = itemBSql.replace(",)", ")");// replace the last comma
		} else {
			itemASql = "";
			itemBSql = "";
		}

		SQL = SQL + itemASql + itemBSql;
		ParameterizedRowMapper<VASDetailUI> mapper = new ParameterizedRowMapper<VASDetailUI>() {

			public VASDetailUI mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				VASDetailUI vasUI = new VASDetailUI();
				int newlineInx =0;
				vasUI.setItemId(rs.getString("item_id_a"));
				vasUI.setItemType(rs.getString("item_type_a"));
				vasUI.setItemId_B(rs.getString("item_id_b"));
				vasUI.setItemType_B(rs.getString("item_type_b"));
				vasUI.setVASHtml(rs.getString("item_html_a"));
				vasUI.setVASHtml_B(rs.getString("item_html_b"));
				vasUI.setVASTitle(rs.getString("item_description_a"));
				vasUI.setVASTitle_B(rs.getString("item_description_b"));
				if(vasUI.getVASTitle()==null||vasUI.getVASTitle().isEmpty()){
				newlineInx = vasUI.getVASHtml().indexOf((char)10+"");
				if(newlineInx>0){
					vasUI.setVASTitle(vasUI.getVASHtml().substring(0, newlineInx));
					vasUI.setVASDetail(vasUI.getVASHtml().substring(newlineInx+1).replaceAll(((char)10)+"", "<br>"));
				}else if(vasUI.getVASHtml().indexOf("<br/>") >= 0)
				{
					newlineInx = vasUI.getVASHtml().indexOf("<br/>");
					vasUI.setVASTitle(vasUI.getVASHtml().substring(0, newlineInx));
					vasUI.setVASDetail(vasUI.getVASHtml().substring(newlineInx+5).replaceAll(((char)10)+"", "<br>"));
				}
				else{
					vasUI.setVASTitle(vasUI.getVASHtml());
					vasUI.setVASDetail("");
				}
				}
				if(vasUI.getVASTitle_B()==null||vasUI.getVASTitle_B().isEmpty()){
				newlineInx = vasUI.getVASHtml_B().indexOf((char)10+"");
				if(newlineInx>0){
					vasUI.setVASTitle_B(vasUI.getVASHtml_B().substring(0, newlineInx));
					vasUI.setVASDetail_B(vasUI.getVASHtml_B().substring(newlineInx+1).replaceAll(((char)10)+"", "<br>"));
				}else if(vasUI.getVASHtml_B().indexOf("<br/>") >= 0)
				{
					newlineInx = vasUI.getVASHtml_B().indexOf("<br/>");
					vasUI.setVASTitle_B(vasUI.getVASHtml_B().substring(0, newlineInx));
					vasUI.setVASDetail_B(vasUI.getVASHtml_B().substring(newlineInx+5).replaceAll(((char)10)+"", "<br>"));
				}
				else{
					vasUI.setVASTitle_B(vasUI.getVASHtml_B());
					vasUI.setVASDetail_B("");
				}
				}

				return vasUI;
			}
		};

		try {
			logger.debug("getExclusiveItemList @ VASDetailDAO: " + SQL);
			exclusiveItemList = simpleJdbcTemplate.query(SQL, mapper);

		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException");
			exclusiveItemList = null;
		} catch (Exception e) {
			logger.error("Exception caught in getExclusiveItemList()", e);
			throw new DAOException(e.getMessage(), e);
		}
		return exclusiveItemList;

	}
	

	
	public List<VASDetailUI> getNowExclusiveItemList(String locale, List<String> selectedList)
	throws DAOException {

		List<VASDetailUI> nowExclusiveItemList = new ArrayList<VASDetailUI>();
		logger.info("getNowExclusiveItemList is called");

		String SQL = " select a.id, a.type, b.html				 "+
					 " from w_item a, w_item_display_lkup b		 "+
					 " where a.id = b.item_id					 "+
					 " and a.type like 'AIO_PUR%' 				 "+
					 " and b.display_type = 'DETAIL'			 "+
					 " and locale = '"+locale+"'				 ";


		String itemASql = "";

		if (selectedList != null) {
			if (selectedList.size() > 0) {
				itemASql += "AND    a.ID IN (";
				for (int i = 0; i < selectedList.size(); i++) {
					itemASql += selectedList.get(i) + ",";
				}
				itemASql += ") ";
				itemASql = itemASql.replace(",)", ")");// replace the last comma
			} else {
				itemASql = "";
			}

		}

		SQL = SQL + itemASql;
		ParameterizedRowMapper<VASDetailUI> mapper = new ParameterizedRowMapper<VASDetailUI>() {

			public VASDetailUI mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				VASDetailUI vasUI = new VASDetailUI();
				int newlineInx =0;
				vasUI.setItemId(rs.getString("id"));
				vasUI.setItemType(rs.getString("type"));
				vasUI.setVASHtml(rs.getString("html"));
				newlineInx = vasUI.getVASHtml().indexOf((char)10+"");
				if(newlineInx>0){
					vasUI.setVASTitle(vasUI.getVASHtml().substring(0, newlineInx));
					vasUI.setVASDetail(vasUI.getVASHtml().substring(newlineInx+1).replaceAll(((char)10)+"", "<br>"));
				}else if(vasUI.getVASHtml().indexOf("<br/>") >= 0)
				{
					newlineInx = vasUI.getVASHtml().indexOf("<br/>");
					vasUI.setVASTitle(vasUI.getVASHtml().substring(0, newlineInx));
					vasUI.setVASDetail(vasUI.getVASHtml().substring(newlineInx+5).replaceAll(((char)10)+"", "<br>"));
				}
				else{
					vasUI.setVASTitle(vasUI.getVASHtml());
					vasUI.setVASDetail("");
				}

				return vasUI;
			}
		};

		try {
			logger.debug("getNowExclusiveItemList @ VASDetailDAO: " + SQL);
			nowExclusiveItemList = simpleJdbcTemplate.query(SQL, mapper);

		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException");
			nowExclusiveItemList = null;
		} catch (Exception e) {
			logger.error("Exception caught in getExclusiveItemList()", e);
			throw new DAOException(e.getMessage(), e);
		}
		return nowExclusiveItemList;

	}
	
	public List<VASDetailUI> getFilterItemList(String FilterType)
	throws DAOException {

		List<VASDetailUI> filterItemList = new ArrayList<VASDetailUI>();
		logger.info("getFilterItemList is called");

		String SQL = "SELECT 	CODE,DESCRIPTION 						 "+
					 "FROM 		W_CODE_LKUP 			 "+
					 "WHERE GRP_ID = ?   "; //--FilterType-- 
					 
		ParameterizedRowMapper<VASDetailUI> mapper = new ParameterizedRowMapper<VASDetailUI>() {

			public VASDetailUI mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				VASDetailUI filterItem = new VASDetailUI();
				
				filterItem.setItemId(rs.getString("DESCRIPTION"));
				filterItem.setItemType(rs.getString("CODE"));
			
				return filterItem;
			}
		};

		try {
			logger.debug("getFilterItemList @ VASDetailDAO: " + SQL);
			filterItemList = simpleJdbcTemplate.query(SQL, mapper, FilterType);

		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException");
			filterItemList = null;
		} catch (Exception e) {
			logger.error("Exception caught in getExclusiveItemList()", e);
			throw new DAOException(e.getMessage(), e);
		}
		return filterItemList;

	}

	public List<SubscribedItemUI> getAutoTieItemList(String pcdVasSelected) throws DAOException {
		// TODO Auto-generated method stub

		List<SubscribedItemUI> autoTieItemList = new ArrayList<SubscribedItemUI>();
		logger.info("getAutoTieItemList is called");

		String SQL = "  select wi.id, wi.type  "+
			"  from bomweb_code_lkup bcl, w_item wi WHERE (bcl.code_type  ='NTV_BE_S' OR bcl.code_type LIKE 'PCD_AUTOTIE_ACQ%') "+
			"  and bcl.code_id in (select type from w_item a where a.id in ("+pcdVasSelected+")) "+
			"  and bcl.code_desc = wi.id ";

					 
		ParameterizedRowMapper<SubscribedItemUI> mapper = new ParameterizedRowMapper<SubscribedItemUI>() {

			public SubscribedItemUI mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				SubscribedItemUI autoTieItem = new SubscribedItemUI();
				autoTieItem.setActionInd("A");
				autoTieItem.setBasketId("");
				autoTieItem.setId(rs.getString("ID"));
				autoTieItem.setType(rs.getString("TYPE"));
			
				return autoTieItem;
			}
		};

		try {
			logger.debug("getAutoTieItemList @ VASDetailDAO: " + SQL);
			autoTieItemList = simpleJdbcTemplate.query(SQL, mapper);

		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException");
			autoTieItemList = null;
		} catch (Exception e) {
			logger.error("Exception caught in getAutoTieItemList()", e);
			throw new DAOException(e.getMessage(), e);
		}
		return autoTieItemList;

	}
	
	public List<VasParmDTO> getImsVasParmList (String locale)throws DAOException {
		List<VasParmDTO> imsVasParmList = new ArrayList<VasParmDTO>();
		
		String SQL=
			
			"select attb_id, vas_type, item_type, " +
			"parm_a_label, parm_a_cd, parm_a_input_method, parm_a_input_format, parm_a_min_length, parm_a_max_length, "+
			"parm_b_label, parm_b_cd, parm_b_input_method, parm_b_input_format, parm_b_min_length, parm_b_max_length, "+ 
			"parm_c_label, parm_c_cd, parm_c_input_method, parm_c_input_format, parm_c_min_length, parm_c_max_length from W_ATTB_IMSPARM_LKUP "+
			"where locale = ?";


		ParameterizedRowMapper<VasParmDTO> mapper = new ParameterizedRowMapper<VasParmDTO>() {
	        public VasParmDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
	        	VasParmDTO imsVasParm = new VasParmDTO();
	        	
	        	imsVasParm.setVas_type(rs.getString("vas_type"));  
	        	imsVasParm.setItem_type(rs.getString("item_type"));   
	        	imsVasParm.setVas_parm_a_label(rs.getString("parm_a_label"));
	        	imsVasParm.setVas_parm_a_cd(rs.getString("parm_a_cd"));	
	        	imsVasParm.setVas_parm_a_input_method(rs.getString("parm_a_input_method"));		
	        	imsVasParm.setVas_parm_a_input_format(rs.getString("parm_a_input_format"));
	        	imsVasParm.setVas_parm_a_min_length(rs.getString("parm_a_min_length"));
	        	imsVasParm.setVas_parm_a_max_length(rs.getString("parm_a_max_length"));			
	        	imsVasParm.setVas_parm_b_label(rs.getString("parm_b_label"));
	        	imsVasParm.setVas_parm_b_cd(rs.getString("parm_b_cd"));	
	        	imsVasParm.setVas_parm_b_input_method(rs.getString("parm_b_input_method"));		
	        	imsVasParm.setVas_parm_b_input_format(rs.getString("parm_b_input_format"));
	        	imsVasParm.setVas_parm_b_min_length(rs.getString("parm_b_min_length"));
	        	imsVasParm.setVas_parm_b_max_length(rs.getString("parm_b_max_length"));		
	        	imsVasParm.setVas_parm_c_label(rs.getString("parm_c_label"));
	        	imsVasParm.setVas_parm_c_cd(rs.getString("parm_c_cd"));	
	        	imsVasParm.setVas_parm_c_input_method(rs.getString("parm_c_input_method"));		
	        	imsVasParm.setVas_parm_c_input_format(rs.getString("parm_c_input_format"));
	        	imsVasParm.setVas_parm_c_min_length(rs.getString("parm_c_min_length"));
	        	imsVasParm.setVas_parm_c_max_length(rs.getString("parm_c_max_length"));		      	
	            return imsVasParm;
	        }
	    };

		try {
			logger.debug("getImsVasParmList @ VASDetailDAO: " + SQL);
			imsVasParmList = simpleJdbcTemplate.query(SQL, mapper,locale);
		} catch (EmptyResultDataAccessException erdae) {
			logger.debug("EmptyResultDataAccessException");
			imsVasParmList = null;
		} catch (Exception e) {
			logger.debug("Exception caught in getImsVasParmList():", e);
			throw new DAOException(e.getMessage(), e);
		}	
		return imsVasParmList;
	}
	

	
	public List<VasParmDTO> getImsVasParmListByItemId (String itemIds, String locale)throws DAOException {
		List<VasParmDTO> imsVasParmList = new ArrayList<VasParmDTO>();
		
		String SQL=
			
			"select b.id item_id, a.attb_id, a.vas_type, a.item_type, " +
			"a.parm_a_label, a.parm_a_cd, a.parm_a_input_method, a.parm_a_input_format, a.parm_a_min_length, a.parm_a_max_length, "+
			"a.parm_b_label, a.parm_b_cd, a.parm_b_input_method, a.parm_b_input_format, a.parm_b_min_length, a.parm_b_max_length, "+ 
			"a.parm_c_label, a.parm_c_cd, a.parm_c_input_method, a.parm_c_input_format, a.parm_c_min_length, a.parm_c_max_length " +
			"from W_ATTB_IMSPARM_LKUP a, w_item b "+
			"where a.locale = ? " +
			"and b.id in ("+itemIds+") " +
			"and a.item_type = b.type ";


		ParameterizedRowMapper<VasParmDTO> mapper = new ParameterizedRowMapper<VasParmDTO>() {
	        public VasParmDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
	        	VasParmDTO imsVasParm = new VasParmDTO();
	        	
	        	imsVasParm.setItem_id(rs.getString("item_id"));  
	        	imsVasParm.setVas_type(rs.getString("vas_type"));  
	        	imsVasParm.setItem_type(rs.getString("item_type"));   
	        	imsVasParm.setVas_parm_a_label(rs.getString("parm_a_label"));
	        	imsVasParm.setVas_parm_a_cd(rs.getString("parm_a_cd"));	
	        	imsVasParm.setVas_parm_a_input_method(rs.getString("parm_a_input_method"));		
	        	imsVasParm.setVas_parm_a_input_format(rs.getString("parm_a_input_format"));
	        	imsVasParm.setVas_parm_a_min_length(rs.getString("parm_a_min_length"));
	        	imsVasParm.setVas_parm_a_max_length(rs.getString("parm_a_max_length"));			
	        	imsVasParm.setVas_parm_b_label(rs.getString("parm_b_label"));
	        	imsVasParm.setVas_parm_b_cd(rs.getString("parm_b_cd"));	
	        	imsVasParm.setVas_parm_b_input_method(rs.getString("parm_b_input_method"));		
	        	imsVasParm.setVas_parm_b_input_format(rs.getString("parm_b_input_format"));
	        	imsVasParm.setVas_parm_b_min_length(rs.getString("parm_b_min_length"));
	        	imsVasParm.setVas_parm_b_max_length(rs.getString("parm_b_max_length"));		
	        	imsVasParm.setVas_parm_c_label(rs.getString("parm_c_label"));
	        	imsVasParm.setVas_parm_c_cd(rs.getString("parm_c_cd"));	
	        	imsVasParm.setVas_parm_c_input_method(rs.getString("parm_c_input_method"));		
	        	imsVasParm.setVas_parm_c_input_format(rs.getString("parm_c_input_format"));
	        	imsVasParm.setVas_parm_c_min_length(rs.getString("parm_c_min_length"));
	        	imsVasParm.setVas_parm_c_max_length(rs.getString("parm_c_max_length"));		      	
	            return imsVasParm;
	        }
	    };

		try {
			logger.debug("getImsVasParmListByItemId @ VASDetailDAO: " + SQL);
			imsVasParmList = simpleJdbcTemplate.query(SQL, mapper,locale);
		} catch (EmptyResultDataAccessException erdae) {
			logger.debug("EmptyResultDataAccessException");
			imsVasParmList = null;
		} catch (Exception e) {
			logger.debug("Exception caught in getImsVasParmListByItemId():", e);
			throw new DAOException(e.getMessage(), e);
		}	
		return imsVasParmList;
	}
	
	public List<VASDetailUI> getVasExclusiveGiftType(List<String> selectedVasList,List<String> selectedGiftList, String locale) throws DAOException
	{			
		
		List<VASDetailUI> vasExclusiveGiftTypeItemList = new ArrayList<VASDetailUI>();
		String selectedItems = "";
		
		if (selectedVasList != null && selectedVasList.size() > 0) {
			for (int i = 0; i < selectedVasList.size(); i++) 
				selectedItems += selectedVasList.get(i) + ",";
		}
		if (selectedGiftList!=null && selectedGiftList.size() > 0) {
			for (int j = 0; j < selectedGiftList.size(); j++) 
				selectedItems += selectedGiftList.get(j) + ",";
		}
				
		selectedItems = selectedItems.substring(0, selectedItems.length()-1);// replace the last comma
		
		String SQL = " select b1.id_b1, b1.type_b1, b1.html_b1, b1.description_b1, b2.id_b2, b2.type_b2, b2.html_b2, b2.description_b2 from BOMWEB_code_lkup a,  "
			+ "(select b1.id id_b1, b1.type type_b1, c1.html html_b1, c1.description description_b1 from W_ITEM b1, W_ITEM_DISPLAY_LKUP c1 where b1.id in ("+selectedItems+") and b1.id = c1.item_id and c1.DISPLAY_TYPE = 'DETAIL' and c1.LOCALE = '"+locale+"') b1, "
			+ "(select b2.id id_b2, b2.type type_b2, c2.html html_b2, c2.description description_b2 from W_ITEM b2, W_ITEM_DISPLAY_LKUP c2 where b2.id in ("+selectedItems+") and b2.id = c2.item_id and c2.DISPLAY_TYPE = 'DETAIL' and c2.LOCALE = '"+locale+"') b2 "
			+ "where a.code_type = 'IMS_VAS_GIFT_TYPE_EXCLUSIVE' and a.code_id = b1.type_b1 and a.code_desc = b2.type_b2 ";
					
		
		

		ParameterizedRowMapper<VASDetailUI> mapper = new ParameterizedRowMapper<VASDetailUI>() {

			public VASDetailUI mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				VASDetailUI vasUI = new VASDetailUI();
				int newlineInx =0;
				vasUI.setItemId(rs.getString("id_b1"));
				vasUI.setItemType(rs.getString("type_b1"));
				vasUI.setVASHtml(rs.getString("html_b1"));
				vasUI.setVASTitle(rs.getString("description_b1"));
				vasUI.setItemId_B(rs.getString("id_b2"));
				vasUI.setItemType_B(rs.getString("type_b2"));
				vasUI.setVASHtml_B(rs.getString("html_b2"));
				vasUI.setVASTitle_B(rs.getString("description_b2"));
				if(vasUI.getVASTitle()==null||vasUI.getVASTitle().isEmpty()){
				newlineInx = vasUI.getVASHtml().indexOf((char)10+"");
				if(newlineInx>0){
					vasUI.setVASTitle(vasUI.getVASHtml().substring(0, newlineInx));
					vasUI.setVASDetail(vasUI.getVASHtml().substring(newlineInx+1).replaceAll(((char)10)+"", "<br>"));
				}else if(vasUI.getVASHtml().indexOf("<br/>") >= 0)
				{
					newlineInx = vasUI.getVASHtml().indexOf("<br/>");
					vasUI.setVASTitle(vasUI.getVASHtml().substring(0, newlineInx));
					vasUI.setVASDetail(vasUI.getVASHtml().substring(newlineInx+5).replaceAll(((char)10)+"", "<br>"));
				}
				else{
					vasUI.setVASTitle(vasUI.getVASHtml());
					vasUI.setVASDetail("");
				}
				}
				if(vasUI.getVASTitle_B()==null||vasUI.getVASTitle_B().isEmpty()){
				newlineInx = vasUI.getVASHtml_B().indexOf((char)10+"");
				if(newlineInx>0){
					vasUI.setVASTitle_B(vasUI.getVASHtml_B().substring(0, newlineInx));
					vasUI.setVASDetail_B(vasUI.getVASHtml_B().substring(newlineInx+1).replaceAll(((char)10)+"", "<br>"));
				}else if(vasUI.getVASHtml_B().indexOf("<br/>") >= 0)
				{
					newlineInx = vasUI.getVASHtml_B().indexOf("<br/>");
					vasUI.setVASTitle_B(vasUI.getVASHtml_B().substring(0, newlineInx));
					vasUI.setVASDetail_B(vasUI.getVASHtml_B().substring(newlineInx+5).replaceAll(((char)10)+"", "<br>"));
				}
				else{
					vasUI.setVASTitle_B(vasUI.getVASHtml_B());
					vasUI.setVASDetail_B("");
				}
				}

				return vasUI;
			}
		};
		


		try {
			logger.debug("getVasExclusiveGiftType @ VASDetailDAO: " + SQL);
			vasExclusiveGiftTypeItemList = simpleJdbcTemplate.query(SQL, mapper);

		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException");
			vasExclusiveGiftTypeItemList = null;
		} catch (Exception e) {
			logger.error("Exception caught in getVasExclusiveGiftType()", e);
			throw new DAOException(e.getMessage(), e);
		}
		return vasExclusiveGiftTypeItemList;
		
	}
	

	public List<SubscribedItemUI> getVASAutoTieDummyGiftList(String pcdVasSelected) throws DAOException {
		// TODO Auto-generated method stub

		List<SubscribedItemUI> autoTieItemList = new ArrayList<SubscribedItemUI>();
		logger.info("getAutoTieItemList is called");

		String SQL = " select wi.id, wi.type " +
			" from w_item_type wit, w_item wi " +
			" where wit.type = 'VAS_DUMMY_GIFT' " +
			" and wit.type_desc = wi.id||'' " +
			" and wit.item_id in ("+pcdVasSelected+")"; 
					 
		ParameterizedRowMapper<SubscribedItemUI> mapper = new ParameterizedRowMapper<SubscribedItemUI>() {

			public SubscribedItemUI mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				SubscribedItemUI autoTieItem = new SubscribedItemUI();
				autoTieItem.setActionInd("A");
				autoTieItem.setBasketId("");
				autoTieItem.setId(rs.getString("ID"));
				autoTieItem.setType(rs.getString("TYPE"));
			
				return autoTieItem;
			}
		};

		try {
			logger.debug("getVASAutoTieDummyGiftList @ VASDetailDAO: " + SQL);
			autoTieItemList = simpleJdbcTemplate.query(SQL, mapper);

		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException");
			autoTieItemList = null;
		} catch (Exception e) {
			logger.error("Exception caught in getVASAutoTieDummyGiftList()", e);
			throw new DAOException(e.getMessage(), e);
		}
		return autoTieItemList;

	}
}

