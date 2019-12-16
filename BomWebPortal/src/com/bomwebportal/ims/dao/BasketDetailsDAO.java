package com.bomwebportal.ims.dao;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.bomwebportal.dao.*;
import com.bomwebportal.dto.*;
import com.bomwebportal.ims.dto.*;
import com.bomwebportal.exception.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BasketDetailsDAO extends BaseDAO{
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	public List<BasketDetailsDTO> getBasketDetailsList (String locale, String basketID)throws DAOException {
		List<BasketDetailsDTO> basketDetailsList = new ArrayList<BasketDetailsDTO>();

		String SQL=

		
		/*	"SELECT BIA.BASKET_ID, 											 "+
			"  		CBA.PRIORITY, 											 "+
			"  		BDL.DESCRIPTION TITLE,		 							 "+
			"  		SUM.BASKET_SUM SUMMARY,		 							 "+
			"  		CBA.DISPLAY_TAB,										 "+
			"  		NVL(IDRV.CONTRACT_PERIOD,'0') CONTRACT_PERIOD,			 "+
			"  		I.ID,													 "+
			"  		IOA.OFFER_CD,											 "+
			"  		NVL(BDL.IMAGE_PATH,'NA') IMAGE_PATH,					 "+
			"		IP.RECURRENT_AMT,										 "+
			"		IP.MTH_TO_MTH_RATE,										 "+
			"		NVL(IDI.BANDWIDTH, '') BANDWIDTH,						 "+
			"		NVL(IDI.NOW_TV_FORM_TYPE, '') NOW_TV_FORM_TYPE,			 "+
			"		NVL(IDI.IS_COUPON_PLAN, '')	IS_COUPON_PLAN,				 "+
			"		NVL(IDI.CAN_SUBC_OPT_SRV, '') CAN_SUBC_OPT_SRV,			 "+
			"		NVL(IDI.IS_PREINSTALLATION,	'')	IS_PREINSTALLATION,		 "+
			"		NVL(IDI.TECHNOLOGY,	'')	TECHNOLOGY,						 "+
			"		NVL(IDI.OT_INSTALL_CHRG_REQ, '') OT_INSTALL_CHRG_REQ,	 "+
			"		NVL(IDI.PLAN_ID, '') PLAN_ID,							 "+
			"		I.TYPE													 "+
			"FROM  	W_CHANNEL_BASKET_ASSGN  CBA,							 "+ 
			"  		W_BASKET                B,								 "+
			"  		W_BASKET_ITEM_ASSGN     BIA,							 "+
			"  		W_ITEM                  I,  							 "+
			"  		W_ITEM_DTL_RP_RB_VAS    IDRV,							 "+
			"  		W_ITEM_DISPLAY_LKUP     IDL,							 "+
			"  		W_BASKET_DISPLAY_LKUP   BDL,							 "+
			"		W_ITEM_PRICING			IP,								 "+
			"  		W_ITEM_OFFER_ASSGN	   	IOA,							 "+
			"		W_ITEM_DTL_IMS		   	IDI,							 "+
			"		(SELECT   				SBDL.BASKET_ID, 				 "+
			"		 						SBDL.HTML BASKET_SUM			 "+
			"		 FROM	  				W_BASKET_DISPLAY_LKUP SBDL		 "+
			" 		 WHERE	  				SBDL.DISPLAY_TYPE = 'SUMMARY') SUM		 "+
			"WHERE  B.ID = BIA.BASKET_ID									 "+
			"AND  	BIA.BASKET_ID = ?										 "+
			"AND    B.ID = CBA.BASKET_ID									 "+
			"AND    B.ID = BDL.BASKET_ID(+)									 "+
			"AND	B.ID = SUM.BASKET_ID										 "+
			"AND    BDL.LOCALE(+) = ? 				--LOCALE--				 "+
			"AND	BDL.DISPLAY_TYPE = 'TITLE'								 "+
			"AND    BIA.ITEM_ID = I.ID 										 "+
			"AND    BIA.ITEM_ID = IP.ID 									 "+
			"AND    BIA.ITEM_ID = IDL.ITEM_ID 								 "+
			"AND    BIA.ITEM_ID = IDRV.ID									 "+
			"AND    BIA.ITEM_ID = IOA.ITEM_ID								 "+
			"AND    BIA.ITEM_ID = IDI.ITEM_ID								 "+
			"AND	IOA.OFFER_TYPE IN ('S','V')								 "+
			"AND    IDL.LOCALE = ? 		  			--LOCALE--				 "+ 
			"AND    I.TYPE IN ('PROG','PRE-INST')  	--BASKETTYPE--			 "+
			"AND    CBA.CUSTOMER_TIER = ?   		--CUSTOMERTIER--		 "+
			"AND    CBA.CHANNEL_ID= '1'   			--CHANNEL_ID-- 			 "+
			"ORDER BY I.TYPE DESC			\n";*/

			"SELECT  BIA.BASKET_ID, 							 "+
			"		B.TYPE BASKET_TYPE_ID,							 "+
			"		BIA.ITEM_ID,							 "+														
			"   	TBDL.HTML TITLE,						 "+
			"		SBDL.HTML SUMMARY,						 "+
			"		DIDL.HTML ITEM_DETAIL,						 "+
			"		IP.RECURRENT_AMT,						 "+
			"		MFIDL.HTML MTHFIX, 						 "+
			"		IP.MTH_TO_MTH_RATE,						 "+
			"		MTMIDL.HTML MTH2MTH,						 "+		
			"		BWDIDL.HTML bw_desc,								 "+
			"		NVL(IDRV.CONTRACT_PERIOD,'0') CONTRACT_PERIOD,			 "+	
			" 		NVL(TBDL.IMAGE_PATH,'NA') IMAGE_PATH,					 "+												
			"		NVL(IDI.BANDWIDTH, '') BANDWIDTH,			 "+			
			"		NVL(IDI.NOW_TV_FORM_TYPE, '') NOW_TV_FORM_TYPE,			 "+
			"		NVL(IDI.IS_COUPON_PLAN, '')IS_COUPON_PLAN,			 "+
			"		NVL(IDI.CAN_SUBC_OPT_SRV, '') CAN_SUBC_OPT_SRV,			 "+
			"		NVL(IDI.IS_PREINSTALLATION,'')IS_PREINSTALLATION,			 "+
			"		NVL(IDI.TECHNOLOGY,'')TECHNOLOGY,			 "+
			"		NVL(IDI.OT_INSTALL_CHRG_REQ, 'Y') OT_INSTALL_CHRG_REQ,			 "+
			"		NVL(IDI.PLAN_ID, '') PLAN_ID,				 "+
			"		NVL(IDI.WAIVE_ISF_IND, 'N') WAIVE_ISF_IND,				 "+
			"		NVL(IDI.WAIVE_ASF_IND, 'N') WAIVE_ASF_IND,				 "+
			" 		I.TYPE,	IP.ONETIME_AMT,										 "+
			"		IDRV.TERM_EXTENSION,									 "+	
			"		WIOPA.PRODUCT_ID,						" +
			"		IOA.OFFER_CD							 "+	
			"FROM  	W_BASKET                B,						 "+
			"  		W_DIC_BASKET_ITEM_ASSGN BIA,					 "+
			"  		W_ITEM                  I,  					 "+
			"  		W_ITEM_DTL_RP_RB_VAS    IDRV,					 "+			
			"  		W_BASKET_DISPLAY_LKUP   TBDL,					 "+
			"  		W_BASKET_DISPLAY_LKUP   SBDL,					 "+		
			"		W_ITEM_PRICING			IP,				 "+		
			"  		W_ITEM_OFFER_ASSGN	   	IOA,				 "+
			"		W_ITEM_DTL_IMS		   	IDI,				 "+
			"		W_ITEM_DISPLAY_LKUP   DIDL,					 "+
			"		W_ITEM_DISPLAY_LKUP   MFIDL,					 "+
			"		W_ITEM_DISPLAY_LKUP   MTMIDL,					 "+	
			"		W_ITEM_DISPLAY_LKUP   BWDIDL,							 "+			
			"		W_ITEM_OFFER_PRODUCT_ASSGN WIOPA					"+	
			"WHERE  B.ID = ?							 "+//--BASKET ID--
			"AND	   B.ID = BIA.BASKET_ID							 "+																				
			"AND    B.ID = TBDL.BASKET_ID(+)						 "+
			"AND    B.ID = SBDL.BASKET_ID(+)						 "+										
			"AND    TBDL.LOCALE(+) = ? 						 "+//
			"AND	   TBDL.DISPLAY_TYPE = 'TITLE'						 "+
			"AND    SBDL.LOCALE(+) = ? 						 "+		//--LOCALE--
			"AND	   SBDL.DISPLAY_TYPE = 'SUMMARY'					 "+
			"AND    BIA.ITEM_ID = I.ID 							 "+
			"AND    BIA.ITEM_ID = IP.ID 							 "+			
			"AND    BIA.ITEM_ID = IDRV.ID							 "+
			"AND    I.ID = DIDL.ITEM_ID(+)							 "+													
			"AND    DIDL.LOCALE(+) = ? 						 "+		//--LOCALE--
			"AND	   DIDL.DISPLAY_TYPE = 'DETAIL'						 "+
			"AND    I.ID = MFIDL.ITEM_ID(+)							 "+
			"AND    I.ID = MTMIDL.ITEM_ID(+)						 "+		
			"AND    I.ID = BWDIDL.ITEM_ID(+)								 "+													
			"AND    MFIDL.LOCALE(+) = ? 						 "+//--LOCALE--
			"AND	   MFIDL.DISPLAY_TYPE = 'MTHFIX'					 "+
			"AND    MTMIDL.LOCALE(+) = ? 						 "+		//--LOCALE--
			"AND	   MTMIDL.DISPLAY_TYPE = 'MTH2MTH'					 "+	
			"AND    BWDIDL.LOCALE(+) = ? 							         "+	//--LOCALE--
			"AND	BWDIDL.DISPLAY_TYPE = 'BW_DESC'							 "+	
			"AND    BIA.ITEM_ID = IOA.ITEM_ID						 "+
			"AND    BIA.ITEM_ID = IDI.ITEM_ID						 "+
			"AND	   IOA.OFFER_TYPE IN ('S','V')						 "+
			"AND    I.TYPE IN ('PROG')  							 "+
			"AND	   I.LOB = 'IMS'							 "+
			"AND    I.ID = WIOPA.ITEM_ID(+)						"+
			"ORDER BY IDI.BANDWIDTH DESC, I.TYPE DESC					";	
		
		ParameterizedRowMapper<BasketDetailsDTO> mapper = new ParameterizedRowMapper<BasketDetailsDTO>() {
	        public BasketDetailsDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
	        	BasketDetailsDTO basketDetails = new BasketDetailsDTO();
	        	basketDetails.setBasketId(rs.getString("basket_id"));
	        	basketDetails.setBasketTypeId(rs.getString("BASKET_TYPE_ID"));
	        	basketDetails.setItemID(rs.getString("item_id"));
	        	basketDetails.setTitle(rs.getString("TITLE"));
	        	basketDetails.setSummary(rs.getString("SUMMARY"));
	        	basketDetails.setItemDetail(rs.getString("ITEM_DETAIL"));
	        	basketDetails.setRecurrentAmt(rs.getString("recurrent_amt"));
	        	basketDetails.setMthFixText(rs.getString("MTHFIX"));
	        	basketDetails.setMthToMthRate(rs.getString("mth_to_mth_rate"));
	        	basketDetails.setMthToMthText(rs.getString("MTH2MTH"));
	        	basketDetails.setBandwidth_desc(rs.getString("bw_desc"));
	        	basketDetails.setContractPeriod(rs.getString("contract_period"));
	        	basketDetails.setImagePath(rs.getString("image_path"));
	        	basketDetails.setBandwidth(rs.getString("bandwidth"));
	        	basketDetails.setNowTvFormType(rs.getString("NOW_TV_FORM_TYPE"));		
	        	basketDetails.setIsCouponPlan(rs.getString("IS_COUPON_PLAN"));
	        	basketDetails.setCanSubcOptSrv(rs.getString("CAN_SUBC_OPT_SRV"));
	        	basketDetails.setIsPreintalltion(rs.getString("IS_PREINSTALLATION"));
	        	basketDetails.setTechnology(rs.getString("TECHNOLOGY"));
	        	basketDetails.setOtInstallChrgReq(rs.getString("OT_INSTALL_CHRG_REQ"));
	        	basketDetails.setPlanId(rs.getString("PLAN_ID"));
	        	basketDetails.setWaiveISFInd(rs.getString("WAIVE_ISF_IND"));
	        	basketDetails.setWaiveASFInd(rs.getString("WAIVE_ASF_IND"));
	        	basketDetails.setItemType(rs.getString("type"));
	        	basketDetails.setOnetimeAmt(rs.getString("ONETIME_AMT"));	        //TT	
	        	basketDetails.setTermExt(rs.getString("TERM_EXTENSION"));
	        	basketDetails.setProductId(rs.getString("PRODUCT_ID"));
	        	basketDetails.setOfferCode(rs.getString("OFFER_CD"));
	        	
	            return basketDetails;
	        }
	    };

		try {
			logger.debug("getBasketDetailsList @ BasketDetailsDAO: " + SQL);
			basketDetailsList = simpleJdbcTemplate.query(SQL, mapper,basketID,locale,locale,locale,locale,locale,locale);
		//	basketDetailsList = simpleJdbcTemplate.query(SQL, mapper,"200000034","en","en","en","en","en");
		} catch (EmptyResultDataAccessException erdae) {
			logger.debug("EmptyResultDataAccessException");
			basketDetailsList = null;
		} catch (Exception e) {
			logger.debug("Exception caught in getBasketDetailsList():", e);
			throw new DAOException(e.getMessage(), e);
		}	
		return basketDetailsList;
	}
	
	public List<BasketDetailsDTO> getBasketPaymentList (String basketID)throws DAOException {
		List<BasketDetailsDTO> basketPaymentList = new ArrayList<BasketDetailsDTO>();

		String SQL=

		
			"SELECT BASKET_ID, TYPE_DESC FROM W_BASKET_TYPE 	 "+
			"		WHERE TYPE = 'PAYMENT'						 "+
			"		AND BASKET_ID = ?  			";//--BASKET ID--
					
		ParameterizedRowMapper<BasketDetailsDTO> mapper = new ParameterizedRowMapper<BasketDetailsDTO>() {
	        public BasketDetailsDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
	        	BasketDetailsDTO basketPayment = new BasketDetailsDTO();
	        	basketPayment.setBasketId(rs.getString("basket_id"));        	
	        	basketPayment.setItemType(rs.getString("TYPE_DESC"));
	            return basketPayment;
	        }
	    };

		try {
			logger.debug("getBasketPaymentList @ BasketDetailsDAO: " + SQL);
			basketPaymentList = simpleJdbcTemplate.query(SQL, mapper, basketID);
		} catch (EmptyResultDataAccessException erdae) {
			logger.debug("EmptyResultDataAccessException");
			basketPaymentList = null;
		} catch (Exception e) {
			logger.debug("Exception caught in getBasketPaymentList():", e);
			throw new DAOException(e.getMessage(), e);
		}	
		return basketPaymentList;
	}
	
	
	public Boolean checkEligibleForNowTV (String basketId)
	throws DAOException {

		List<String> result = new ArrayList<String>();
		
		String SQL = 
			 "select decode(count(*),'0','N','Y') ind from w_dic_basket_item_assgn a " +
			 "where a.basket_id = :basket_id "+
			 "and exists (select * from w_item where id = a.item_id and type = 'NTV_FREE')";

		MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("basket_id", basketId);
			
		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				String dto = new String();
				dto = (String) rs.getString("ind");
				return dto;
			}
		};
		
		try {
			logger.info("checkEligibleForNowTV() @ NowTVCheckDAO: " + SQL);
			result = simpleJdbcTemplate.query(SQL, mapper, params);
		} catch (Exception e) {
			logger.info("Exception caught in checkEligibleForNowTV():", e);
			return true;
		}
		
		return result.size()>0?"Y".equals(result.get(0)):false;
	}
	
}

