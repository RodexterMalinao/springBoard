package com.bomwebportal.ims.dao;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;

import com.bomwebportal.ims.dto.AttbDTO;
import com.bomwebportal.ims.dto.ImsRptBasketDtlDTO;
import com.bomwebportal.ims.dto.ImsRptBasketItemDTO;
import com.bomwebportal.ims.dto.ImsRptChannelDTO;
import com.bomwebportal.ims.dto.ImsRptGiftDTO;
import com.bomwebportal.ims.dto.report.RptServiceInfoDTO;
import com.bomwebportal.ims.dto.ui.GiftUI;
import com.bomwebportal.ims.dto.ui.VASDetailUI;
import com.bomwebportal.util.NTVUtil;
import com.bomwebportal.dto.HSTradeDescDTO;
import com.bomwebportal.dto.report.ReportDTO;
import com.bomwebportal.dto.report.RptHSTradeDescDTO;
import com.bomwebportal.exception.DAOException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ImsReport2DAO extends BaseDAO{
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	public ImsRptBasketDtlDTO getBasketDetail (String basketId, String locale) 
			throws DAOException {

		List<ImsRptBasketDtlDTO> basketDtlList = new ArrayList<ImsRptBasketDtlDTO>();

		String SQL=
			"SELECT I.TYPE TYPE,  								 "+
			"       NVL(IDI.BANDWIDTH,'')   BANDWIDTH,  		 "+
			"		BWDIDL.HTML bw_desc,					 "+
			"       IDI.CAN_SUBC_OPT_SRV    CAN_SUBC_OPT_SRV, 	 "+
			"       NVL(BDL.HTML,'')        BASKET_TITLE, 		 "+
			"       NVL(irrv.contract_period,'') contract_period,"+
			"       NVL(BDL.IMAGE_PATH,'')  IMAGE_PATH,			 "+
			"	         (SELECT   RTRIM " +
			"	                      (XMLAGG (XMLELEMENT (e, dis_cd || ', ')).EXTRACT" +
			"	                                                                   ('//text()')," +
			"	                       ', '" +
			"	                      ) dis_cd" +
			"	              FROM w_item_product_discount" +
			"	             WHERE item_id = I.ID" +
			"	          GROUP BY item_id) incentive_cd, " +
			"       NVL(IOA.OFFER_CD,'')  OFFER_CD	 			 "+
			"FROM   W_DIC_BASKET_ITEM_ASSGN BIA, 				 "+
			"       W_ITEM_DTL_IMS IDI, 						 "+
			"       W_BASKET_DISPLAY_LKUP BDL, 					 "+
			"		W_ITEM_DISPLAY_LKUP   BWDIDL,				 "+
			"       W_ITEM I, 									 "+
			"       w_item_dtl_rp_rb_vas irrv, 					 "+
			"       W_ITEM_OFFER_ASSGN IOA 						 "+
			"WHERE  BIA.BASKET_ID = ? 							 "+
			"AND    I.TYPE IN ('PROG') 							 "+
			"AND    BIA.ITEM_ID = I.ID 							 "+
			"AND    i.id = irrv.id 								"+
			"AND    IDI.ITEM_ID = I.ID 							 "+
			"AND    IOA.ITEM_ID = I.ID 							 "+
			"AND    I.ID = BWDIDL.ITEM_ID(+)					"+	
			"AND    BWDIDL.LOCALE(+) = ? 							         "+	//--LOCALE--
			"AND	BWDIDL.DISPLAY_TYPE = 'BW_DESC'							 "+
			"AND    BDL.BASKET_ID = BIA.BASKET_ID 				 "+
			"AND    BDL.DISPLAY_TYPE = 'TITLE' 					 "+
			"AND    BDL.LOCALE = ? 								";
		
		ParameterizedRowMapper<ImsRptBasketDtlDTO> mapper = new ParameterizedRowMapper<ImsRptBasketDtlDTO>() {
	        public ImsRptBasketDtlDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
	        	ImsRptBasketDtlDTO basketDtl = new ImsRptBasketDtlDTO();
	        	basketDtl.setType(rs.getString("TYPE"));
	        	basketDtl.setBandwidth(rs.getString("BANDWIDTH"));
	        	basketDtl.setBandwidth_desc(rs.getString("bw_desc"));
	        	basketDtl.setCanSubcOptSrv(rs.getString("CAN_SUBC_OPT_SRV"));
	        	basketDtl.setBasketTitle(rs.getString("BASKET_TITLE"));
	        	basketDtl.setImagePath(rs.getString("IMAGE_PATH"));
	        	basketDtl.setIncentiveCd(rs.getString("incentive_cd"));
	        	basketDtl.setOfferCd(rs.getString("OFFER_CD"));
	        	basketDtl.setContractPeriod(rs.getInt("contract_period"));
	        	
	            return basketDtl;
	        }
	    };

		try {
			logger.info("Basket ID:" + basketId);
			logger.debug("getBasketDetail @ ImsReportDAO: " + SQL);
			basketDtlList = simpleJdbcTemplate.query(SQL, mapper, basketId, locale,locale);
		} catch (EmptyResultDataAccessException erdae) {
			logger.debug("EmptyResultDataAccessException");
			basketDtlList = null;
		} catch (Exception e) {
			logger.debug("Exception caught in getBasketDetail():", e);
			throw new DAOException(e.getMessage(), e);
		}
		return (basketDtlList.size() > 0?basketDtlList.get(0):null);
	}
		
	
	public List<ImsRptBasketItemDTO> getBasketItem (String basketId, String itemIdStr, String locale)
			throws DAOException {
		List<ImsRptBasketItemDTO> basketItemList = new ArrayList<ImsRptBasketItemDTO>();
		itemIdStr = itemIdStr.replace(",","','");
		
		String SQL=		
			"SELECT I.TYPE,											 "+
			"		NVL(BIA.MDO_IND,'')         MDO_IND, 			 "+															
			"		NVL(IDL_DETAIL.HTML,'')     ITEM_DETAILS, 		 "+															
			"   	NVL(IDL_MTHFIX.HTML,'')     ITEM_MTHFIX,		 "+
			"		NVL(IDL_MTH2MTH.HTML,'')    ITEM_MTH2MTH, 		 "+
			"		IP.RECURRENT_AMT            RECURRENT_AMT, 		 "+
			"       NVL(irrv.contract_period,'') contract_period, 	"+
			"		IP.MTH_TO_MTH_RATE          MTH_TO_MTH_RATE, 	 "+
			"		NVL(IOA.OFFER_CD,'')  		OFFER_CD,	 	 "+
			"	         (SELECT   RTRIM" +
			"	                      (XMLAGG (XMLELEMENT (e, dis_cd || ', ')).EXTRACT" +
			"	                                                                   ('//text()')," +
			"	                       ', '" +
			"	                      ) dis_cd" +
			"	              FROM w_item_product_discount" +
			"	             WHERE item_id = I.ID" +
			"	          GROUP BY item_id) incentive_cd, " +
			"		NVL(IDI.PRE_INST_OFFER_CD,'') PRE_INST_OFFER_CD, "+
			"		NVL(IDI.PRE_USE_OFFER_CD,'') PRE_USE_OFFER_CD,   "+
			" 		I.ID											 "+
			"FROM  	W_ITEM_DISPLAY_LKUP  IDL_DETAIL,				 "+
			"  		W_ITEM_DISPLAY_LKUP  IDL_MTHFIX,				 "+
			"  		W_ITEM_DISPLAY_LKUP  IDL_MTH2MTH, 				 "+
			" 		W_DIC_BASKET_ITEM_ASSGN  BIA,					 "+	
			"       w_item_dtl_rp_rb_vas irrv, 						"+		
			"  		W_ITEM               I,							 "+
			"  		W_ITEM_PRICING       IP,							 "+
			"		W_ITEM_DTL_IMS		 IDI,						"+
			"  		W_ITEM_OFFER_ASSGN IOA								 "+
			"WHERE  I.ID IN ('" + itemIdStr + "')					 "+					
			"AND    BIA.BASKET_ID = ?								 "+
			"AND    (I.TYPE IN (select code From W_CODE_LKUP where grp_id='SB_IMS_OPT_SRV')	  "+
			"		OR I.TYPE IN ('PROG', 'BVAS', 'PRE_INST', 'OTHER'))								 "+
			"AND    IDL_DETAIL.ITEM_ID = I.ID						 "+									
			"AND    IDL_DETAIL.DISPLAY_TYPE = 'DETAIL'				 "+
			"AND    IDL_DETAIL.LOCALE = ? 							 "+	
			"AND	IDL_MTHFIX.ITEM_ID = I.ID						 "+
			"AND    i.id = irrv.id 									"+
			"AND    IDL_MTHFIX.DISPLAY_TYPE = 'MTHFIX' 				 "+
			"AND    IDL_MTHFIX.LOCALE = ? 							 "+
			"AND	IDL_MTH2MTH.ITEM_ID=I.ID						 "+
			"AND	IDL_MTH2MTH.DISPLAY_TYPE='MTH2MTH'				 "+
			"AND    IDL_MTH2MTH.LOCALE= ?							 "+
			"AND 	I.ID = IDI.ITEM_ID(+)							 "+
			"AND    BIA.ITEM_ID=I.ID								 "+
			"AND    I.ID=IP.ID										 "+
			"AND    IOA.ITEM_ID =	IDL_MTHFIX.ITEM_ID			     "+
			"ORDER BY BIA.DISPLAY_SEQ, I.ID							";	
			
		ParameterizedRowMapper<ImsRptBasketItemDTO> mapper = new ParameterizedRowMapper<ImsRptBasketItemDTO>() {
	        public ImsRptBasketItemDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
	        	ImsRptBasketItemDTO basketItem = new ImsRptBasketItemDTO();
	        	basketItem.setType(rs.getString("TYPE"));
	        	basketItem.setMdoInd(rs.getString("MDO_IND"));
	        	basketItem.setItemDetails(rs.getString("ITEM_DETAILS"));
	        	basketItem.setItemMthFix(rs.getString("ITEM_MTHFIX"));
	        	basketItem.setItemMth2Mth(rs.getString("ITEM_MTH2MTH"));
	        	basketItem.setItemMthFixAmt(rs.getString("RECURRENT_AMT"));
	        	basketItem.setItemMth2MthAmt(rs.getString("MTH_TO_MTH_RATE"));
	        	basketItem.setContractPeriod(rs.getInt("contract_period"));
	        	basketItem.setOfferCode(rs.getString("OFFER_CD"));
	        	basketItem.setIncentiveCd(rs.getString("incentive_cd"));
	        	basketItem.setPreInstOfferCd(rs.getString("PRE_INST_OFFER_CD"));
	        	basketItem.setPreUseOfferCd(rs.getString("PRE_USE_OFFER_CD"));
	        	basketItem.setItemID(rs.getString("id"));
	            return basketItem;
	        }
	    };

		try {
			logger.info("Basket ID :" + basketId + " Item ID:" + itemIdStr);
			logger.debug("getBasketItem @ ImsReportDAO: " + SQL);
			basketItemList = simpleJdbcTemplate.query(SQL, mapper, basketId, locale, locale, locale);
		} catch (EmptyResultDataAccessException erdae) {
			logger.debug("EmptyResultDataAccessException");
			basketItemList = null;
		} catch (Exception e) {
			logger.debug("Exception caught in getBasketItem():", e);
			throw new DAOException(e.getMessage(), e);
		}	
		return basketItemList;
	}

	
	public List<ImsRptBasketItemDTO> getOptServiceY (String basketId, String itemIdStr, String locale, String orderId, boolean isAF)
			throws DAOException {
		List<ImsRptBasketItemDTO> basketItemList = new ArrayList<ImsRptBasketItemDTO>();
		itemIdStr = itemIdStr.replace(",","','");
		
		String SQL=		
			"SELECT I.TYPE,													 "+
			"		NVL(DL.DESCRIPTION,'')      DESCRIPTION, 				 "+															
			"		NVL(IDL_DETAIL.HTML,'')     ITEM_DETAILS, 				 "+															
			"   	NVL(IDL_MTHFIX.HTML,'')     ITEM_MTHFIX,				 "+
			"		NVL(IDL_MTH2MTH.HTML,'')    ITEM_MTH2MTH, 				 "+
			"       NVL(irrv.contract_period,'') contract_period, 			"+
			"		IP.RECURRENT_AMT            RECURRENT_AMT, 				 "+
			"	         (SELECT al.description || ' ' || bc.attb_value" +
			"	            FROM bomweb_component bc," +
			"	                 w_attb_lkup al," +
			"	                 w_item_offer_product_assgn iopa," +
			"	                 w_product_attb_assgn paa" +
			"	           WHERE bc.order_id = '"+orderId+"'             " +
			"	             AND al.attb_id = bc.attb_id" +
			"	             AND al.locale = 'en'" +
			"	             AND iopa.product_id = paa.product_id" +
			"	             AND paa.attb_id = al.attb_id" +
			"	             AND iopa.item_id = i.ID) attb_info," +
			"	         (SELECT   RTRIM" +
			"	                      (XMLAGG (XMLELEMENT (e, dis_cd || ', ')).EXTRACT" +
			"	                                                                   ('//text()')," +
			"	                       ', '" +
			"	                      ) dis_cd" +
			"	              FROM w_item_product_discount" +
			"	             WHERE item_id = i.ID" +
			"	          GROUP BY item_id) incentive_cd, "+
			"		(select wiopa.prod_desc		 						 	 "+
			"		from w_item_type wit, w_item_offer_product_assgn wiopa   "+
			"		where wit.type = 'VAS_DUMMY_GIFT'		 				 "+
			"		and wit.type_desc = wiopa.item_id||''		 			 "+
			"		and wit.item_id = i.ID ) vas_dummy_gift_desc,		 	 "+
			"		NVL(IDI.PRE_INST_OFFER_CD,'') PRE_INST_OFFER_CD, 		 "+
			"		IP.MTH_TO_MTH_RATE          MTH_TO_MTH_RATE, 			 "+
			"       NVL(IOA.OFFER_CD,'')  		OFFER_CD,					 "+
			"       I.ID					 "+
			"FROM   W_ITEM_DISPLAY_LKUP IDL_DETAIL,							 "+
			"  		W_ITEM_DISPLAY_LKUP IDL_MTHFIX,							 "+
			"  		W_ITEM_DISPLAY_LKUP IDL_MTH2MTH, 						 "+
			"       w_item_dtl_rp_rb_vas irrv, 								"+	
			" 		W_ITEM              I,									 "+
			"		W_ITEM_DTL_IMS		IDI,								 "+		
			"  		W_DISPLAY_LKUP      DL,									 "+
			"       W_ITEM_OFFER_ASSGN IOA,									 "+
			"  		W_ITEM_PRICING      IP									 "+
			"WHERE  I.ID IN ('" + itemIdStr + "')							 "+
			"AND	I.ID NOT IN (select item_id from w_dic_basket_item_assgn where basket_id = ? )"+
//			"AND    I.TYPE IN ('OPT_PREM','WL_BB','ANTI_VIR','MEDIA')		 "+
			"AND	I.TYPE IN (select code From W_CODE_LKUP where grp_id = 'SB_IMS_OPT_SRV')	 "+
			"AND    IDL_DETAIL.ITEM_ID = I.ID								 "+									
			"AND    IDL_DETAIL.DISPLAY_TYPE = 'DETAIL'						 "+
			"AND    IDL_DETAIL.LOCALE = ? 									 "+	
			"AND	IDL_MTHFIX.ITEM_ID = I.ID								 "+
			"AND    IDL_MTHFIX.DISPLAY_TYPE = 'MTHFIX' 						 "+
			"AND    IDL_MTHFIX.LOCALE = ? 									 "+
			"AND    i.id = irrv.id 											"+
			"AND	IDL_MTH2MTH.ITEM_ID = I.ID								 "+
			"AND	IDL_MTH2MTH.DISPLAY_TYPE = 'MTH2MTH'					 "+
			"AND    IOA.ITEM_ID = I.ID 										 "+
			"AND    IDL_MTH2MTH.LOCALE = ?									 "+
			"AND	I.ID=IDI.ITEM_ID										 "+
			"AND    I.ID=IP.ID												 "+
			"AND    DL.TYPE = 'IMS_ITYPE_' || I.TYPE						 "+
			"AND    DL.LOCALE = ?											 "+	
			"ORDER BY I.ID					 								";	
			
		ParameterizedRowMapper<ImsRptBasketItemDTO> mapper = new ParameterizedRowMapper<ImsRptBasketItemDTO>() {
	        public ImsRptBasketItemDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
	        	ImsRptBasketItemDTO basketItem = new ImsRptBasketItemDTO();
	        	basketItem.setType(rs.getString("TYPE"));
	        	basketItem.setItemTitle(rs.getString("DESCRIPTION"));
	        	basketItem.setItemDetails(rs.getString("ITEM_DETAILS"));
	        	basketItem.setItemMthFix(rs.getString("ITEM_MTHFIX"));
	        	basketItem.setItemMth2Mth(rs.getString("ITEM_MTH2MTH"));
	        	basketItem.setItemMthFixAmt(rs.getString("RECURRENT_AMT"));
	        	basketItem.setItemMth2MthAmt(rs.getString("MTH_TO_MTH_RATE"));
	        	basketItem.setOfferCode(rs.getString("OFFER_CD"));
	        	basketItem.setContractPeriod(rs.getInt("contract_period"));
	        	basketItem.setIncentiveCd(rs.getString("incentive_cd"));
	        	basketItem.setVasDummyGiftDesc(rs.getString("vas_dummy_gift_desc"));
	        	basketItem.setMobileNum(rs.getString("attb_info"));
	        	basketItem.setPreInstOfferCd(rs.getString("PRE_INST_OFFER_CD"));
	        	basketItem.setItemID(rs.getString("id"));
	            return basketItem;
	        }
	    };

		try {
			logger.debug("Item ID:" + itemIdStr);
			logger.debug("getOptServiceY @ ImsReportDAO: " + SQL);
			basketItemList = simpleJdbcTemplate.query(SQL, mapper, basketId, locale, locale, locale, locale);
			
			if (basketItemList != null && basketItemList.size() > 0) {
				if (isAF) {
					for (ImsRptBasketItemDTO b: basketItemList) {
						b.setItemDetails(NTVUtil.defaultStringRpt(b.getItemDetails()));
					}
				} else {
					for (ImsRptBasketItemDTO b: basketItemList) {
						b.setItemDetails(NTVUtil.defaultString(b.getItemDetails()));
					}
				}
			}
		} catch (EmptyResultDataAccessException erdae) {
			logger.debug("EmptyResultDataAccessException");
			basketItemList = null;
		} catch (Exception e) {
			logger.debug("Exception caught in getOptServiceY():", e);
			throw new DAOException(e.getMessage(), e);
		}	
		return basketItemList;
	}

	
	public List<ImsRptBasketItemDTO> getOptServiceN (String basketId, String itemIdStr, String locale, String orderId, boolean isAF)
			throws DAOException {
		List<ImsRptBasketItemDTO> basketItemList = new ArrayList<ImsRptBasketItemDTO>();
		itemIdStr = itemIdStr.replace(",","','");
		
		String SQL=		
			"SELECT I.TYPE,													 "+
			"		NVL(DL.DESCRIPTION,'')      DESCRIPTION, 				 "+															
			"		NVL(IDL_DETAIL.HTML,'')     ITEM_DETAILS, 				 "+															
			"   	NVL(IDL_MTHFIX.HTML,'')     ITEM_MTHFIX,				 "+
			"       NVL(irrv.contract_period,'') contract_period, 			"+
			"		NVL(IDL_MTH2MTH.HTML,'')    ITEM_MTH2MTH, 				 "+
			"		IP.RECURRENT_AMT            RECURRENT_AMT, 				 " +
			"			NVL (ioa.offer_cd, '') offer_cd," +
			"	         (SELECT al.description || ' ' || bc.attb_value" +
			"	            FROM bomweb_component bc," +
			"	                 w_attb_lkup al," +
			"	                 w_item_offer_product_assgn iopa," +
			"	                 w_product_attb_assgn paa" +
			"	           WHERE bc.order_id = '"+orderId+"'                    " +
			"	             AND al.attb_id = bc.attb_id" +
			"	             AND al.locale = 'en'" +
			"	             AND iopa.product_id = paa.product_id" +
			"	             AND paa.attb_id = al.attb_id" +
			"	             AND iopa.item_id = i.ID) attb_info," +
			"	         (SELECT   RTRIM" +
			"	                      (XMLAGG (XMLELEMENT (e, dis_cd || ', ')).EXTRACT" +
			"	                                                                   ('//text()')," +
			"	                       ', '" +
			"	                      ) dis_cd" +
			"	              FROM w_item_product_discount" +
			"	             WHERE item_id = i.ID" +
			"	          GROUP BY item_id) incentive_cd, " +
			"		IP.MTH_TO_MTH_RATE          MTH_TO_MTH_RATE, 			 "+
			"       I.ID					 "+
			"FROM   W_ITEM_DISPLAY_LKUP IDL_DETAIL,							 "+
			"  		W_ITEM_DISPLAY_LKUP IDL_MTHFIX,							 "+
			"  		W_ITEM_DISPLAY_LKUP IDL_MTH2MTH, 						 "+
			" 		W_DIC_BASKET_ITEM_ASSGN BIA,							 "+		
			" 		W_ITEM              I,									 "+		
			"       w_item_dtl_rp_rb_vas irrv, 								"+	
			"  		W_DISPLAY_LKUP      DL,									 "+
			"       W_ITEM_OFFER_ASSGN IOA,									 "+
			"  		W_ITEM_PRICING      IP									 "+
			"WHERE  I.ID IN ('" + itemIdStr + "')							 "+
			"AND    BIA.BASKET_ID = ?										 "+
//			"AND    I.TYPE IN ('OPT_PREM','WL_BB','ANTI_VIR','MEDIA')		 "+
			"AND	I.TYPE IN (select code From W_CODE_LKUP where grp_id = 'SB_IMS_OPT_SRV')	 "+
			"AND    IDL_DETAIL.ITEM_ID = I.ID								 "+									
			"AND    IDL_DETAIL.DISPLAY_TYPE = 'DETAIL'						 "+
			"AND    IDL_DETAIL.LOCALE = ? 									 "+	
			"AND	IDL_MTHFIX.ITEM_ID = I.ID								 "+
			"AND    i.id = irrv.id 											"+
			"AND    IDL_MTHFIX.DISPLAY_TYPE = 'MTHFIX' 						 "+
			"AND    IDL_MTHFIX.LOCALE = ? 									 "+
			"AND	IDL_MTH2MTH.ITEM_ID = I.ID								 "+
			"AND	IDL_MTH2MTH.DISPLAY_TYPE = 'MTH2MTH'					 "+
			"AND    IDL_MTH2MTH.LOCALE = ?									 "+
			"AND    IOA.ITEM_ID = I.ID 										 "+
			"AND    BIA.ITEM_ID = I.ID										 "+
			"AND    I.ID=IP.ID												 "+
			"AND    DL.TYPE = 'IMS_ITYPE_' || I.TYPE						 "+
			"AND    DL.LOCALE = ?											 "+	
			"ORDER BY I.ID					 								";	
			
		ParameterizedRowMapper<ImsRptBasketItemDTO> mapper = new ParameterizedRowMapper<ImsRptBasketItemDTO>() {
	        public ImsRptBasketItemDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
	        	ImsRptBasketItemDTO basketItem = new ImsRptBasketItemDTO();
	        	basketItem.setType(rs.getString("TYPE"));
	        	basketItem.setItemTitle(rs.getString("DESCRIPTION"));
	        	basketItem.setItemDetails(rs.getString("ITEM_DETAILS"));
	        	basketItem.setItemMthFix(rs.getString("ITEM_MTHFIX"));
	        	basketItem.setItemMth2Mth(rs.getString("ITEM_MTH2MTH"));
	        	basketItem.setItemMthFixAmt(rs.getString("RECURRENT_AMT"));
	        	basketItem.setItemMth2MthAmt(rs.getString("MTH_TO_MTH_RATE"));
	        	basketItem.setOfferCode(rs.getString("OFFER_CD"));
	        	basketItem.setIncentiveCd(rs.getString("incentive_cd"));
	        	basketItem.setContractPeriod(rs.getInt("contract_period"));
	        	basketItem.setMobileNum(rs.getString("attb_info"));
	        	basketItem.setItemID(rs.getString("id"));
	            return basketItem;
	        }
	    };

		try {
			logger.debug("Basket ID:" + basketId);
			logger.debug("Item ID:" + itemIdStr);
			logger.debug("getOptServiceN @ ImsReportDAO: " + SQL);
			basketItemList = simpleJdbcTemplate.query(SQL, mapper, basketId, locale, locale, locale, locale);
			
			if (basketItemList != null && basketItemList.size() > 0) {
				if (isAF) {
					for (ImsRptBasketItemDTO b: basketItemList) {
						b.setItemDetails(NTVUtil.defaultStringRpt(b.getItemDetails()));
					}
				} else {
					for (ImsRptBasketItemDTO b: basketItemList) {
						b.setItemDetails(NTVUtil.defaultString(b.getItemDetails()));
					}
				}
			}
		} catch (EmptyResultDataAccessException erdae) {
			logger.debug("EmptyResultDataAccessException");
			basketItemList = null;
		} catch (Exception e) {
			logger.debug("Exception caught in getOptServiceN():", e);
			throw new DAOException(e.getMessage(), e);
		}	
		return basketItemList;
	}

	public List<ImsRptBasketItemDTO> getBackendVas ( String orderId)    //Gary
	throws DAOException {
List<ImsRptBasketItemDTO> backendVasList = new ArrayList<ImsRptBasketItemDTO>();


String SQL=		
	"SELECT 'BE_VAS' TYPE,          "   +                                                               
	 "           ''      DESCRIPTION, "+                    
	  "          ''     ITEM_DETAILS,                      "+   
	   "   'NA'     ITEM_MTHFIX,                    "+
	    "        'NA'    ITEM_MTH2MTH,               "+          
	     "       0            RECURRENT_AMT,          "+              
	      "      0          MTH_TO_MTH_RATE,           "     +
	       " '' contract_period,              "+
	        "    bsoi.item_id                               ITEM_ID, "+                            
	       " NVL(bsoi.offer_cd,'')         OFFER_CD,                  "+         
	        "        (SELECT RTRIM										"+
            "  (XMLAGG (XMLELEMENT (e, code_desc || ', ')).EXTRACT		"+
            "  ('//text()'),											"+
            "  ', '														"+
            "  ) dis_cd													"+
            "   FROM bomweb_code_lkup WHERE code_type='SB_VIOFFER_DISC'  AND code_id=bsoi.offer_cd	"+
            "   GROUP BY code_id) incentive_cd, "+
	"       ''                    ITEM_PARM_TITLE,    "+          
	 "      ''                  ITEM_PARM              "+                                       
	" from bomweb_subscribed_offer_ims bsoi "+ 
	" where bsoi.order_id = '"+orderId+"'"+
	" and bsoi.update_by = 'BACKEND' "
;	
	
ParameterizedRowMapper<ImsRptBasketItemDTO> mapper = new ParameterizedRowMapper<ImsRptBasketItemDTO>() {
    public ImsRptBasketItemDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
    	ImsRptBasketItemDTO basketItem = new ImsRptBasketItemDTO();
    	basketItem.setType(rs.getString("TYPE"));
    	basketItem.setItemTitle(rs.getString("DESCRIPTION"));
    	basketItem.setItemDetails(rs.getString("ITEM_DETAILS"));
    	basketItem.setItemMthFix(rs.getString("ITEM_MTHFIX"));
    	basketItem.setItemMth2Mth(rs.getString("ITEM_MTH2MTH"));
    	basketItem.setItemMthFixAmt(rs.getString("RECURRENT_AMT"));
    	basketItem.setItemMth2MthAmt(rs.getString("MTH_TO_MTH_RATE"));
    	basketItem.setOfferCode(rs.getString("OFFER_CD"));
    	basketItem.setIncentiveCd(rs.getString("incentive_cd"));
    	basketItem.setContractPeriod(rs.getInt("contract_period"));
//    	basketItem.setMobileNum(rs.getString("attb_info"));
        return basketItem;
    }
};

try {
	
	logger.debug("getBackendVas @ ImsReportDAO: " + SQL);
	backendVasList = simpleJdbcTemplate.query(SQL, mapper);
} catch (EmptyResultDataAccessException erdae) {
	logger.debug("EmptyResultDataAccessException");
	backendVasList = null;
} catch (Exception e) {
	logger.debug("Exception caught in getBackendVas():", e);
	throw new DAOException(e.getMessage(), e);
}	
return backendVasList;
}

	public List<ImsRptChannelDTO> getBackendChannel ( String orderId)    //Gary
	throws DAOException {
List<ImsRptChannelDTO> backendChannelList = new ArrayList<ImsRptChannelDTO>();


String SQL=		
	                   
	" select vi_campaign, offer_cd "+ 
	" from bomweb_subscribed_offer_ims "+
	" where order_id = '"+orderId+"' "+
	" and sb_offer_type = 'NTVCH' "
;	
	
ParameterizedRowMapper<ImsRptChannelDTO> mapper = new ParameterizedRowMapper<ImsRptChannelDTO>() {
    public ImsRptChannelDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
    	ImsRptChannelDTO channel = new ImsRptChannelDTO();
    	channel.setCampaignCd(rs.getString("vi_campaign"));    	
    	channel.setOfferCd(rs.getString("OFFER_CD"));
    	
//    	basketItem.setMobileNum(rs.getString("attb_info"));
        return channel;
    }
};

try {
	
	logger.debug("getBackendChannel @ ImsReportDAO: " + SQL);
	backendChannelList = simpleJdbcTemplate.query(SQL, mapper);
} catch (EmptyResultDataAccessException erdae) {
	logger.debug("EmptyResultDataAccessException");
	backendChannelList = null;
} catch (Exception e) {
	logger.debug("Exception caught in getBackendVas():", e);
	throw new DAOException(e.getMessage(), e);
}	
return backendChannelList;
}

	public List<ImsRptGiftDTO> getGift (String itemIdStr, String locale, String channel)
	throws DAOException {
		List<ImsRptGiftDTO> giftList = new ArrayList<ImsRptGiftDTO>();
		itemIdStr = itemIdStr.replace(",","','");
		
		final String final_locale = locale;
		
		final String tempItemIdStr = itemIdStr;		
		
		String dummyBasket_id = "";
		
		if(channel.equals("DS"))
		{
			dummyBasket_id = "212999999"; 
		}else if(channel.equals("CC")||channel.equals("PT")){
			dummyBasket_id = "212999998"; 
		}else if(channel.equals("RS")){
			dummyBasket_id = "212999997"; 
		}else if(channel.equals("CC_M")||channel.equals("PT_M")){
			dummyBasket_id = "212999993"; 
		}else if(channel.equals("RS_M")){
			dummyBasket_id = "212999994"; 
		}
		
		String SQL=		"SELECT DL.DESCRIPTION type_desc, 	" +
		" I.ID , I.TYPE," +
		" NVL(IDL_DETAIL.HTML,'')     ITEM_DETAILS,    " +
		" IP.RECURRENT_AMT            RECURRENT_AMT,     " +
		" IP.MTH_TO_MTH_RATE          MTH_TO_MTH_RATE," +
		" to_char(ip.EFF_START_DATE, 'yyyy/mm/dd') gift_start_date, " +
		" to_char(NVL (ip.EFF_START_DATE, TO_DATE ('12/31/2099', 'MM/DD/YYYY')), 'yyyy/mm/dd') gift_end_date " +
		" FROM   W_ITEM_DISPLAY_LKUP IDL_DETAIL, " +
		" W_ITEM              I,  " +
		" W_ITEM_PRICING      IP, " +
		" W_DISPLAY_LKUP      DL " +
		" WHERE  I.ID IN ('" + itemIdStr + "')  " +
		" AND    I.ID in (select item_id from w_dic_basket_item_assgn where basket_id = "+ dummyBasket_id +" )" +
		" AND    IDL_DETAIL.ITEM_ID = I.ID  " +
		" AND    IDL_DETAIL.DISPLAY_TYPE = 'DETAIL'  " +
		" AND    IDL_DETAIL.LOCALE = ? " +
		" AND    I.ID=IP.ID  " +
        " AND DL.TYPE = 'IMS_ITYPE_'||I.TYPE                              \n" +
        " AND DL.LOCALE = ?              --LOCALE--                             \n" +
		" ORDER BY I.ID  ";

		
		ParameterizedRowMapper<ImsRptGiftDTO> mapper = new ParameterizedRowMapper<ImsRptGiftDTO>() {
		    public ImsRptGiftDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				
				
				ParameterizedRowMapper<AttbDTO> mapper2 = new ParameterizedRowMapper<AttbDTO>() {
				    public AttbDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				    	AttbDTO gift_attb = new AttbDTO();
				    	gift_attb.setAttbId(rs.getString("ATTB_ID"));
				    	gift_attb.setAttbDesc(rs.getString("DESCRIPTION"));
				    	gift_attb.setVisualInd(rs.getString("visual_ind"));
				        return gift_attb;
				    }
				};
				
				ParameterizedRowMapper<ImsRptBasketItemDTO> mapper3 = new ParameterizedRowMapper<ImsRptBasketItemDTO>() {
			        public ImsRptBasketItemDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
			        	ImsRptBasketItemDTO basketItem = new ImsRptBasketItemDTO();
			        	basketItem.setType(rs.getString("vas_type"));
//			        	basketItem.setItemTitle(rs.getString("DESCRIPTION"));
//			        	basketItem.setItemDetails(rs.getString("ITEM_DETAILS"));
//			        	basketItem.setItemMthFix(rs.getString("ITEM_MTHFIX"));
//			        	basketItem.setItemMth2Mth(rs.getString("ITEM_MTH2MTH"));
//			        	basketItem.setItemMthFixAmt(rs.getString("RECURRENT_AMT"));
//			        	basketItem.setItemMth2MthAmt(rs.getString("MTH_TO_MTH_RATE"));
			        	basketItem.setOfferCode(rs.getString("vas_offer_cd"));
//			        	basketItem.setContractPeriod(rs.getInt("contract_period"));
			        	basketItem.setIncentiveCd(rs.getString("vas_incentive_cd"));
//			        	basketItem.setMobileNum(rs.getString("attb_info"));
			        	basketItem.setPreInstOfferCd(rs.getString("vas_pre_inst_offer_cd"));
			        	basketItem.setItemID(rs.getString("vas_item_id"));
			            return basketItem;
			        }
			    };
				List<AttbDTO> giftAttbList = new ArrayList<AttbDTO>();
				List<ImsRptBasketItemDTO> dediVasList = new ArrayList<ImsRptBasketItemDTO>();
		    	
		    	ImsRptGiftDTO gift = new ImsRptGiftDTO();
		    	gift.setItemID(rs.getString("ID"));
		    	gift.setItemDetails(rs.getString("ITEM_DETAILS"));
		    	gift.setGift_start_date(rs.getString("gift_start_date"));
				gift.setGift_end_date(rs.getString("gift_end_date"));
				gift.setGift_type(rs.getString("type_desc"));

				String gift_attb_WQ_AF_SQL=		
					"select ATTB_ID, DESCRIPTION, visual_ind  " +
					"from w_attb_lkup " +
					"where attb_id in (select ATTB_ID from w_product_attb_assgn where product_id in ('" + rs.getString("ID") + "')) " +
					"and locale = 'en'";
				
				 gift_attb_WQ_AF_SQL=
					 "select ATTB_ID, DESCRIPTION, visual_ind  " +
					 "from w_attb_lkup " +
					 "where attb_id in " +
					 "(select ATTB_ID from w_product_attb_assgn where product_id in " +
					 "(select PRODUCT_ID  from w_item_offer_product_assgn where item_id in ('" + rs.getString("ID") + "')) " +
					 ") " +
					 "and locale = '" +final_locale + "'";
				
				 logger.debug("gift_attb_WQ_AF_SQL:"+gift_attb_WQ_AF_SQL);
				giftAttbList = simpleJdbcTemplate.query(gift_attb_WQ_AF_SQL, mapper2);
				gift.setGiftAttbList(giftAttbList);
				
				
				String gift_dedi_VAS_SQL=
					"select wt.item_id gift_item_id, wi.id vas_item_id, wi.type vas_type, wioa.offer_cd vas_offer_cd, nvl(widi.pre_inst_offer_cd,'') vas_pre_inst_offer_cd,  \n"+
					"	         (SELECT   RTRIM" +
					"	                      (XMLAGG (XMLELEMENT (e, dis_cd || ', ')).EXTRACT" +
					"	                                                                   ('//text()')," +
					"	                       ', '" +
					"	                      ) dis_cd" +
					"	              FROM w_item_product_discount" +
					"	             WHERE item_id = wi.ID" +
					"	          GROUP BY item_id) vas_incentive_cd "+
					"from w_item_type wt, \n"+
					"w_item wi, \n"+
					"w_item_offer_assgn wioa, \n"+
					"w_item_dtl_ims widi \n"+
					"where wt.item_id in ("+rs.getString("ID")+") \n"+
					"and wt.type = 'GIFT_DE_VAS' \n"+
					"and wi.id||'' = wt.type_desc \n"+
					"and wi.id = widi.item_id \n"+
					"and wi.id = wioa.item_id \n"; 
				dediVasList = simpleJdbcTemplate.query(gift_dedi_VAS_SQL, mapper3);
				gift.setDediVASList(dediVasList);
				
		        return gift;
		    }
		};
		
		try {
			logger.debug("Item ID:" + itemIdStr);
			logger.debug("getGift @ ImsReportDAO: " + SQL);
			giftList = simpleJdbcTemplate.query(SQL, mapper, locale, locale);
		} catch (EmptyResultDataAccessException erdae) {
			logger.debug("EmptyResultDataAccessException");
			giftList = null;
		} catch (Exception e) {
			logger.debug("Exception caught in getGift():", e);
			throw new DAOException(e.getMessage(), e);
		}	
		return giftList;
	}

	public List<ImsRptBasketItemDTO> getNtvItem (String itemIdStr, String locale, boolean isAF)
			throws DAOException {
		List<ImsRptBasketItemDTO> basketItemList = new ArrayList<ImsRptBasketItemDTO>();
		itemIdStr = itemIdStr.replace(",","','");
		
		String nowTVGroupStr = "SB_IMS_NTV_VAS2";
		
		String SQL=		
			"SELECT I.TYPE,													 "+
			"		NVL(IDL_DETAIL.HTML,'')     ITEM_DETAILS, 				 "+															
			"   	NVL(IDL_MTHFIX.HTML,'')     ITEM_MTHFIX,				 "+
			"		NVL(IDL_MTH2MTH.HTML,'')    ITEM_MTH2MTH, 				 "+
			"		IP.RECURRENT_AMT            RECURRENT_AMT, 				 "+
			"		IP.MTH_TO_MTH_RATE          MTH_TO_MTH_RATE, 			 " +
			"       NVL (idt.tv_type, '') tv_type, " +
			"		NVL (ioa.offer_cd, '') offer_cd," +
			"       (SELECT   RTRIM" +
			"                    (XMLAGG (XMLELEMENT (e, dis_cd || ', ')).EXTRACT" +
			"                                                                   ('//text()')," +
			"                     ', '" +
			"                    ) dis_cd" +
			"            FROM w_item_product_discount" +
			"           WHERE item_id = i.ID" +
			"        GROUP BY item_id) incentive_cd  " +
			"FROM   W_ITEM_DISPLAY_LKUP IDL_DETAIL,							 "+
			"  		W_ITEM_DISPLAY_LKUP IDL_MTHFIX,							 "+
			"  		W_ITEM_DISPLAY_LKUP IDL_MTH2MTH, 						 "+
			" 		W_ITEM              I,									 "+		
			"  		W_ITEM_PRICING      IP,									 "+
			"       W_ITEM_OFFER_ASSGN IOA, 								 "+
			"		W_CODE_LKUP			CL,									 "+
			"  		W_ITEM_DTL_TV       IDT									 "+
			"WHERE  I.ID IN ('" + itemIdStr + "')							 "+
			//"AND    I.TYPE IN ('NTV_FREE', 'NTV_PAY', 'NTV_P_30F6', 'NTV_OTHER')	 "+
			"AND	CL.GRP_ID = '" + nowTVGroupStr + "'						 "+
			"AND    IOA.ITEM_ID = I.ID 										 "+
			"AND	CL.CODE = I.TYPE										 "+
			"AND    IDL_DETAIL.ITEM_ID = I.ID								 "+									
			"AND    IDL_DETAIL.DISPLAY_TYPE = 'DETAIL'						 "+
			"AND    IDL_DETAIL.LOCALE = ? 									 "+	
			"AND	IDL_MTHFIX.ITEM_ID = I.ID								 "+
			"AND    IDL_MTHFIX.DISPLAY_TYPE = 'MTHFIX' 						 "+
			"AND    IDL_MTHFIX.LOCALE = ? 									 "+
			"AND	IDL_MTH2MTH.ITEM_ID = I.ID								 "+
			"AND	IDL_MTH2MTH.DISPLAY_TYPE = 'MTH2MTH'					 "+
			"AND    IDL_MTH2MTH.LOCALE = ?									 "+	
			"AND    I.ID=IP.ID												 "+
			"AND    IDT.ITEM_ID (+) = I.ID									";
			
		ParameterizedRowMapper<ImsRptBasketItemDTO> mapper = new ParameterizedRowMapper<ImsRptBasketItemDTO>() {
	        public ImsRptBasketItemDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
	        	ImsRptBasketItemDTO basketItem = new ImsRptBasketItemDTO();
	        	basketItem.setType(rs.getString("TYPE"));
	        	basketItem.setItemDetails(rs.getString("ITEM_DETAILS"));
	        	basketItem.setItemMthFix(rs.getString("ITEM_MTHFIX"));
	        	basketItem.setItemMth2Mth(rs.getString("ITEM_MTH2MTH"));
	        	basketItem.setItemMthFixAmt(rs.getString("RECURRENT_AMT"));
	        	basketItem.setItemMth2MthAmt(rs.getString("MTH_TO_MTH_RATE"));
	        	basketItem.setItemTvType(rs.getString("TV_TYPE"));
	        	basketItem.setOfferCode(rs.getString("OFFER_CD"));
	        	basketItem.setIncentiveCd(rs.getString("incentive_cd"));
	            return basketItem;
	        }
	    };

		try {
			logger.debug("Item ID:" + itemIdStr);
			logger.debug("getNtvItem @ ImsReportDAO: " + SQL);
			basketItemList = simpleJdbcTemplate.query(SQL, mapper, locale, locale, locale);
			
			if (basketItemList != null && basketItemList.size() > 0) {
				if (isAF) {
					for (ImsRptBasketItemDTO b: basketItemList) {
						b.setItemDetails(NTVUtil.defaultStringRpt(b.getItemDetails()));
					}
				} else {
					for (ImsRptBasketItemDTO b: basketItemList) {
						b.setItemDetails(NTVUtil.defaultString(b.getItemDetails()));
					}
				}
			}
		} catch (EmptyResultDataAccessException erdae) {
			logger.debug("EmptyResultDataAccessException");
			basketItemList = null;
		} catch (Exception e) {
			logger.debug("Exception caught in getNtvItem():", e);
			throw new DAOException(e.getMessage(), e);
		}	
		return basketItemList;
	}

	
	public List<ImsRptChannelDTO> getNtvChannelFree (String itemIdStr, String locale, String warrPeriod, String tvCredit)
			throws DAOException {
		List<ImsRptChannelDTO> channelList = new ArrayList<ImsRptChannelDTO>();
		itemIdStr = itemIdStr.replace(",","','");
		
//		String SQL=		
//			"SELECT TCG.CHANNEL_GROUP_CD   CHANNEL_GROUP_CD,							 "+
//			"       NVL(TDL_G.HTML,'')     CHANNEL_GROUP_DESC,							 "+
//			"		NVL(TC.CHANNEL_ID,'')  CHANNEL_ID, 									 "+															
//			"   	NVL(TC.CHANNEL_CD,'')  CHANNEL_CD,									 "+
//			"		NVL(TC.TV_TYPE,'')     TV_TYPE, 									 "+
//			"		NVL(TC.CREDIT,'')      CREDIT, 										 "+
//			"		NVL(TC.MDO_IND,'')     MDO_IND, 									 "+
//			"		NVL(TDL_C.HTML,'')     CHANNEL_DESC,								 "+
//			"		NVL(TC.PARENT_CHANNEL_ID, '')	PARENT_CHANNEL_ID					 "+
//			"FROM   W_TV_CHANNEL TC,													 "+
//			"  		W_TV_CHANNEL_GROUP TCG,												 "+
//			"  		W_TV_DISPLAY_LKUP TDL_C, 											 "+
//			" 		W_TV_DISPLAY_LKUP TDL_G												 "+		
//			"WHERE  TC.CHANNEL_ID IN ('" + itemIdStr + "')								 "+
//			"AND    TCG.CHANNEL_GROUP_ID = TC.CHANNEL_GROUP_ID							 "+
//			"AND    TCG.CHANNEL_GROUP_CD IN ('STARTERPACK','ENTPACK','FREE2HD')			 "+									
//			"AND    TDL_C.TYPE = 'CHANNEL'												 "+
//			"AND    TC.CHANNEL_ID = TDL_C.ID 											 "+	
//			"AND	TDL_C.LOCALE = ?													 "+
//			"AND    TDL_G.TYPE = 'GROUP' 												 "+
//			"AND    TCG.CHANNEL_GROUP_ID = TDL_G.ID										 "+
//			"AND	TDL_G.LOCALE = ?													 "+
//			"ORDER BY TCG.DISPLAY_SEQ, TC.DISPLAY_SEQ, TC.CHANNEL_ID					";	
		
		//steven updated on 20131220, order by nanon
		String SQL = "SELECT TCG.CHANNEL_GROUP_CD   CHANNEL_GROUP_CD,							 " +
				"       NVL(TDL_G.HTML,'')     CHANNEL_GROUP_DESC,							 " +
				"		NVL(TC.CHANNEL_ID,'')  CHANNEL_ID, 									 " +
				"   	NVL(TC.CHANNEL_CD,'')  CHANNEL_CD,									 " +
				"		NVL(TC.TV_TYPE,'')     TV_TYPE, 									 " +
				"		NVL(TC.CREDIT,'')      CREDIT, 										 " +
				"		NVL(TC.MDO_IND,'')     MDO_IND, 									 " +
				"		NVL(TDL_C.HTML,'')     CHANNEL_DESC,								 " +
				"		NVL(TC.PARENT_CHANNEL_ID, '')	PARENT_CHANNEL_ID," +
				"		(select distinct campaign_cd from w_tv_channel_assgn where channel_id = tc.channel_id and contract_period = '"+warrPeriod+"' and offer_credit = '"+tvCredit+"' and is_coupon_plan = 'N') CAMPAIGN_CD, " +
				" 		(select rtrim (xmlagg (xmlelement (e, plan_cd || ',')).extract ('//text()'), ',') plan_cd from w_tv_channel_assgn " +
				"	where tc.channel_id = channel_id and contract_period = '"+warrPeriod+"' and offer_credit = '"+tvCredit+"' and is_coupon_plan = 'N' group by channel_id) plan_cd   	" +
				"		 FROM   W_TV_CHANNEL TC,													 " +
				"  		W_TV_CHANNEL_GROUP TCG,												 " +
				"  		W_TV_DISPLAY_LKUP TDL_C, 											 " +
				" 		W_TV_DISPLAY_LKUP TDL_G												 " +
				"	WHERE  TC.CHANNEL_ID  IN ('" + itemIdStr + "')								 " +
				"	AND    TCG.CHANNEL_GROUP_ID = TC.CHANNEL_GROUP_ID							 " +
				"	AND    TCG.CHANNEL_GROUP_CD IN ('STARTERPACK','ENTPACK','FREE2HD')			 " +
				"	AND    TDL_C.TYPE = 'CHANNEL'												 " +
				"	AND    TC.CHANNEL_ID = TDL_C.ID 											 " +
				"	AND	TDL_C.LOCALE = '" + locale + "'													 " +
				"	AND    TDL_G.TYPE = 'GROUP' 												 " +
				"	AND    TCG.CHANNEL_GROUP_ID = TDL_G.ID										 " +
				"	AND	TDL_G.LOCALE = '" + locale + "'													 " +
				"	ORDER BY TCG.DISPLAY_SEQ, TC.DISPLAY_SEQ, TC.CHANNEL_ID	";
			
		ParameterizedRowMapper<ImsRptChannelDTO> mapper = new ParameterizedRowMapper<ImsRptChannelDTO>() {
	        public ImsRptChannelDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
	        	ImsRptChannelDTO channel = new ImsRptChannelDTO();
	        	channel.setChannelGroupCd(rs.getString("CHANNEL_GROUP_CD"));
	        	channel.setChannelGroupDesc(rs.getString("CHANNEL_GROUP_DESC"));
	        	channel.setChannelId(rs.getString("CHANNEL_ID"));
	        	channel.setChannelCd(rs.getString("CHANNEL_CD"));
	        	channel.setTvType(rs.getString("TV_TYPE"));
	        	channel.setCredit(rs.getString("CREDIT"));
	        	channel.setMdoInd(rs.getString("MDO_IND"));
	        	channel.setChannelDesc(rs.getString("CHANNEL_DESC"));
	        	channel.setParentChannelId(rs.getString("PARENT_CHANNEL_ID"));
	        	channel.setCampaignCd(rs.getString("CAMPAIGN_CD"));
	        	channel.setPlanCd(rs.getString("plan_cd"));
	            return channel;
	        }
	    };

		try {
			logger.debug("Item ID:" + itemIdStr);
			logger.debug("getNtvChannelFree @ ImsReportDAO: " + SQL);
			channelList = simpleJdbcTemplate.query(SQL, mapper);
		} catch (EmptyResultDataAccessException erdae) {
			logger.debug("EmptyResultDataAccessException");
			channelList = null;
		} catch (Exception e) {
			logger.debug("Exception caught in getNtvChannelFree():", e);
			throw new DAOException(e.getMessage(), e);
		}	
		return channelList;
	}

	
	public List<ImsRptChannelDTO> getNtvChannelPay (String itemIdStr, String locale, String warrPeriod, String tvCredit)
			throws DAOException {
		List<ImsRptChannelDTO> channelList = new ArrayList<ImsRptChannelDTO>();
		itemIdStr = itemIdStr.replace(",","','");
		
//		String SQL=		
//			"SELECT NVL(TDL_G.HTML,'')     CHANNEL_GROUP_DESC,							 "+
//			"		NVL(TC.CHANNEL_ID,'')  CHANNEL_ID, 									 "+															
//			"   	NVL(TC.CHANNEL_CD,'')  CHANNEL_CD,									 "+
//			"		NVL(TC.TV_TYPE,'')     TV_TYPE, 									 "+
//			"		NVL(TC.CREDIT,'')      CREDIT,										 "+
//			"		NVL(TC.MDO_IND,'')     MDO_IND, 									 "+
//			"		NVL(TDL_C.HTML,'')     CHANNEL_DESC,								 "+
//			"		NVL(TC.PARENT_CHANNEL_ID, '')	PARENT_CHANNEL_ID					 "+
//			"FROM   W_TV_CHANNEL TC,													 "+
//			"  		W_TV_CHANNEL_GROUP TCG,												 "+
//			"  		W_TV_DISPLAY_LKUP TDL_C, 											 "+
//			" 		W_TV_DISPLAY_LKUP TDL_G												 "+		
//			"WHERE  TC.CHANNEL_ID IN ('" + itemIdStr + "')								 "+
//			"AND    TCG.CHANNEL_GROUP_ID=TC.CHANNEL_GROUP_ID							 "+
//			"AND    TCG.CHANNEL_GROUP_CD NOT IN ('STARTERPACK','ENTPACK','FREE2HD')		 "+									
//			"AND    TDL_C.TYPE='CHANNEL'												 "+
//			"AND    TC.CHANNEL_ID = TDL_C.ID 											 "+	
//			"AND	TDL_C.LOCALE = ?													 "+
//			"AND    TDL_G.TYPE='GROUP' 													 "+
//			"AND    TCG.CHANNEL_GROUP_ID=TDL_G.ID										 "+
//			"AND	TDL_G.LOCALE = ?													 "+
//			"ORDER BY TCG.DISPLAY_SEQ, TC.DISPLAY_SEQ, TC.CHANNEL_ID					";	
		
		
		//steven updated on 20131220, order by nanon
		String SQL = "SELECT NVL(TDL_G.HTML,'')     CHANNEL_GROUP_DESC,							 " +
				"		NVL(TC.CHANNEL_ID,'')  CHANNEL_ID, 									 " +
				"   	NVL(TC.CHANNEL_CD,'')  CHANNEL_CD,									 " +
				"		NVL(TC.TV_TYPE,'')     TV_TYPE, 									 " +
				"		NVL(TC.CREDIT,'')      CREDIT,										 " +
				"		NVL(TC.MDO_IND,'')     MDO_IND, 									 " +
				"		NVL(TDL_C.HTML,'')     CHANNEL_DESC,								 " +
				"		NVL(TC.PARENT_CHANNEL_ID, '')	PARENT_CHANNEL_ID," +
				"		(select distinct campaign_cd from w_tv_channel_assgn where channel_id = tc.channel_id and contract_period = '"+warrPeriod+"' and offer_credit = '"+tvCredit+"' and is_coupon_plan = 'N') CAMPAIGN_CD," +
				" 		(select rtrim (xmlagg (xmlelement (e, plan_cd || ',')).extract ('//text()'), ',') plan_cd from w_tv_channel_assgn " +
				"	where tc.channel_id = channel_id and contract_period = '"+warrPeriod+"' and offer_credit = '"+tvCredit+"' and is_coupon_plan = 'N' group by channel_id) plan_cd" +
				"  					 FROM   W_TV_CHANNEL TC,													 " +
				"  		W_TV_CHANNEL_GROUP TCG,												 " +
				"  		W_TV_DISPLAY_LKUP TDL_C, 											 " +
				" 		W_TV_DISPLAY_LKUP TDL_G												 " +
				"	WHERE  TC.CHANNEL_ID IN ('" + itemIdStr + "')								 " +
				"	AND    TCG.CHANNEL_GROUP_ID=TC.CHANNEL_GROUP_ID							 " +
				"	AND    TCG.CHANNEL_GROUP_CD NOT IN ('STARTERPACK','ENTPACK','FREE2HD')		 " +
				"	AND    TDL_C.TYPE='CHANNEL'												 " +
				"	AND    TC.CHANNEL_ID = TDL_C.ID 	" +
				"	AND	TDL_C.LOCALE = '" + locale + "'													 " +
				"	AND    TDL_G.TYPE='GROUP' 													 " +
				"	AND    TCG.CHANNEL_GROUP_ID=TDL_G.ID										 " +
				"	AND	TDL_G.LOCALE = '" + locale + "'													 " +
				"	ORDER BY TCG.DISPLAY_SEQ, TC.DISPLAY_SEQ, TC.CHANNEL_ID			";
			
		ParameterizedRowMapper<ImsRptChannelDTO> mapper = new ParameterizedRowMapper<ImsRptChannelDTO>() {
	        public ImsRptChannelDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
	        	ImsRptChannelDTO channel = new ImsRptChannelDTO();
	        	channel.setChannelGroupDesc(rs.getString("CHANNEL_GROUP_DESC"));
	        	channel.setChannelId(rs.getString("CHANNEL_ID"));
	        	channel.setChannelCd(rs.getString("CHANNEL_CD"));
	        	channel.setTvType(rs.getString("TV_TYPE"));
	        	channel.setCredit(rs.getString("CREDIT"));
	        	channel.setMdoInd(rs.getString("MDO_IND"));
	        	channel.setChannelDesc(rs.getString("CHANNEL_DESC"));
	        	channel.setParentChannelId(rs.getString("PARENT_CHANNEL_ID"));
	        	channel.setCampaignCd(rs.getString("CAMPAIGN_CD"));
	        	channel.setPlanCd(rs.getString("plan_cd"));
	            return channel;
	        }
	    };

		try {
			logger.debug("Item ID:" + itemIdStr);
			logger.debug("getNtvChannelPay @ ImsReportDAO: " + SQL);
			channelList = simpleJdbcTemplate.query(SQL, mapper);
		} catch (EmptyResultDataAccessException erdae) {
			logger.debug("EmptyResultDataAccessException");
			channelList = null;
		} catch (Exception e) {
			logger.debug("Exception caught in getNtvChannelPay():", e);
			throw new DAOException(e.getMessage(), e);
		}	
		return channelList;
	}
	
	
	public String getProdIdForQosMeasure (String itemIdStr)
			throws DAOException {
		
		List<String> prodIdList = new ArrayList<String>();
		itemIdStr = itemIdStr.replace(",","','");
		String prodIdStr = "";

		String SQL=		
			"SELECT LPAD(PRODUCT_ID, 7, '0')    PRODUCT_ID		 "+
			"FROM   W_ITEM_OFFER_PRODUCT_ASSGN IOPA				 "+
			"WHERE  IOPA.ITEM_ID IN ('" + itemIdStr + "')		";	
	
		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				String dto = new String();
				dto = (String) rs.getString("PRODUCT_ID");
				return dto;
			}
		};

		try {
			logger.debug("Item ID:" + itemIdStr);
			logger.debug("getProdIdForQosMeasure @ ImsReport2DAO: " + SQL);
			prodIdList = simpleJdbcTemplate.query(SQL, mapper);
			for (int i=0; i < prodIdList.size(); i++) {
				if (i==0) {
					prodIdStr = prodIdList.get(i);
				} else {
					prodIdStr = prodIdStr + "," + prodIdList.get(i);
				}
			}

		} catch (EmptyResultDataAccessException erdae) {
			logger.debug("EmptyResultDataAccessException");
			prodIdStr = "";
		} catch (Exception e) {
			logger.debug("Exception caught in getProdIdForQosMeasure():", e);
			throw new DAOException(e.getMessage(), e);
		}	
		return prodIdStr;
	}
	
	public String getPreInstRouterPurchased (String itemIdStr)
			throws DAOException {
		
		List<String> result = new ArrayList<String>();
		itemIdStr = itemIdStr.replace(",","','");
		String retValue = "N";

		String SQL=		
			"SELECT decode(count(*),0,'N','Y') result		 "+
			"FROM   w_item				 "+
			"WHERE  id in ('" + itemIdStr + "')		" +
			"AND  type in (select code from w_code_lkup where grp_id = 'IMS_PREINST_ROUTER_TYPE') ";	
	
		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				String dto = new String();
				dto = (String) rs.getString("result");
				return dto;
			}
		};

		try {
			logger.debug("Item ID:" + itemIdStr);
			logger.debug("getPreInstRouterPurchased @ ImsReport2DAO: " + SQL);
			result = simpleJdbcTemplate.query(SQL, mapper);
			if(result.size()>0){
				retValue = result.get(0);
			}

		} catch (EmptyResultDataAccessException erdae) {
			logger.debug("EmptyResultDataAccessException");
			retValue = "N";
		} catch (Exception e) {
			logger.debug("Exception caught in getPreInstRouterPurchased():", e);
			throw new DAOException(e.getMessage(), e);
		}	
		return retValue;
	}


	public List<RptHSTradeDescDTO> getMobHSTradeList(String mobItemID) throws DAOException {
		List<RptHSTradeDescDTO> mobList = new ArrayList<RptHSTradeDescDTO>();

		String SQL=		
			"SELECT " +
			"htd.id , "+
			"htd.brand , "+
			"htd.model , "+
			"htd.place_of_manufacture , "+
			"htd.network_freq , "+
			"htd.key_features , "+     
			"htd.operation_system , "+     
			"htd.internal_memory , "+     
			"htd.storage_type , "+
			"htd.storage_capacity , "+
			"htd.display , "+     
			"htd.network_protocol , "+
			"htd.camera_resolution , "+
			"htd.packaging_list , "+     
			"htd.price , "+     
			"htd.add_delivery_charge , "+     
			"htd.repair_srv_prdr , "+
			"htd.repair_srv_addr , "+
			"htd.exchange_policy , "+     
			"htd.warranty_period_hs , "+
			"htd.warranty_period_acc "+	
			"FROM   W_HS_TRADE_DESC htd, W_POS_TRADE_DESC_ASSIGN b , W_ITEM_PRODUCT_POS_ASSGN c  " +
			"where htd.ID = b.TRADE_DESC_ID " +
			"and b.POS_ITEM_CD = c.POS_ITEM_CD " +
			"and item_id = '"+mobItemID+"'		";	
	
		 ParameterizedRowMapper<RptHSTradeDescDTO> mapper=  new ParameterizedRowMapper<RptHSTradeDescDTO>() {
				public RptHSTradeDescDTO mapRow(ResultSet rs, int rowNum)	throws SQLException {
					
					RptHSTradeDescDTO dto = new RptHSTradeDescDTO();

					dto.setBrand(rs.getString("brand"));
					dto.setModel(rs.getString("model"));
					dto.setPlaceOfManufacture(rs.getString("place_of_manufacture"));
					dto.setNetworkFrequency(rs.getString("network_freq"));
					dto.setKeyFeatures(rs.getString("key_features"));    
					dto.setOperatingSystem(rs.getString("operation_system"));  
					dto.setInternalMemory(rs.getString("internal_memory"));		 	
					dto.setStorageType(rs.getString("storage_type"));
					dto.setStorageCapacity(rs.getString("storage_capacity"));
					dto.setDisplay(rs.getString("display"));                  
					dto.setNetworkProtocol(rs.getString("network_protocol"));
					dto.setCameraResolution(rs.getString("camera_resolution"));
					dto.setPackagingList(rs.getString("packaging_list"));       
					dto.setPrice(rs.getString("price"));                     
					dto.setAdditionalDeliveryCharge(rs.getString("add_delivery_charge")); 				
					dto.setRepairSrvProvider(rs.getString("repair_srv_prdr"));
					dto.setRepairSrvAddress(rs.getString("repair_srv_addr"));
					dto.setExchangePolicy(rs.getString("exchange_policy"));    
					dto.setWarrantyHandset(rs.getString("warranty_period_hs"));
					dto.setWarrantyAccessory(rs.getString("warranty_period_acc"));
					return dto;
				}
			};
		

		try {
			logger.debug("Item ID:" + mobItemID);
			logger.debug("getProdIdForQosMeasure @ ImsReport2DAO: " + SQL);
			mobList = simpleJdbcTemplate.query(SQL, mapper);

		} catch (EmptyResultDataAccessException erdae) {
			logger.debug("EmptyResultDataAccessException");
		} catch (Exception e) {
			logger.debug("Exception caught in getProdIdForQosMeasure():", e);
			throw new DAOException(e.getMessage(), e);
		}	
		return mobList;
	}

	//Gary
	public List<String> getPISPdf (String itemIdStr, String locale)
	throws DAOException {
		List<String> tempResult = new ArrayList<String>();
		List<String> result = new ArrayList<String>();
		itemIdStr = itemIdStr.replace(",","','");
		
		String SQL = 
				"select distinct html2 from                     " +
				"w_item_display_lkup idl                        " +
				"where idl.item_id in ('"+itemIdStr+"')         " +
				"and idl.locale = '"+locale+"'                  " + // locale
				"and idl.DISPLAY_TYPE = 'PIS'                   " ;

	ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
		public String mapRow(ResultSet rs, int rowNum) throws SQLException {
			String dto = new String();
			dto = (String) rs.getString("html2");
			return dto;
		}
	};

	try {
		logger.debug("getPISPdf @ ImsReport2DAO: " + SQL);
		tempResult = simpleJdbcTemplate.query(SQL, mapper);
		for(int i = 0; i<tempResult.size();i++){
			String temp = tempResult.get(i);
			if(temp!=null){
				while(temp.indexOf((char)10)!=-1){
					result.add(temp.substring(0, temp.indexOf((char)10)-1));
					temp = temp.substring(temp.indexOf((char)10)+1);
				}
			}
			result.add(temp);
		}

	} catch (EmptyResultDataAccessException erdae) {
		logger.debug("EmptyResultDataAccessException");
		result = null;
	} catch (Exception e) {
		logger.debug("Exception caught in getPISPdf():", e);
		throw new DAOException(e.getMessage(), e);
	}	
		return result;
	}	
	
	public HashMap<String, RptServiceInfoDTO> getStaticReportWords(String lang, Boolean isPT,Boolean isDs)
	throws DAOException {
		List<String[]> tempResult = new ArrayList<String[]>();
		HashMap<String, RptServiceInfoDTO> result  = new HashMap<String, RptServiceInfoDTO>();
		String SQL="";

		if (isPT==null){
			isPT=false;
			logger.debug("isPT==null");
		}else if(isPT){
			logger.debug("isPT");
		}else {
			logger.debug("!isPT");
		}
		logger.debug("getStaticReportWords lang:"+lang);
		
		
		if (isDs){
			 SQL = 
				" select ATTRIBUTE,CONTENTS   " +
				" from bomweb_rpt_template " +
				" where (rpt_name like 'ims/BomWebPortal/DirectSales%')" +
				" and language = '"+lang+"' " +
				" order by rpt_name, attribute " ;
		}else {
			 SQL = 
				" select ATTRIBUTE,CONTENTS   " +
				" from bomweb_rpt_template " +
				" where (rpt_name like 'ims/BomWebPortal/"+(isPT?"Premier":"")+"%')" +
				" and language = '"+lang+"' " +
				" order by rpt_name, attribute " ;
		}

	ParameterizedRowMapper<String[]> mapper = new ParameterizedRowMapper<String[]>() {
		public String[] mapRow(ResultSet rs, int rowNum) throws SQLException {
			String[] temp2DStringArray;
			temp2DStringArray = new String[2]; 
			temp2DStringArray[0] = (String) rs.getString("ATTRIBUTE");
			temp2DStringArray[1] = (String) rs.getString("CONTENTS");
			return temp2DStringArray;
		}
	};

	try {
		logger.debug("getStaticReportWords @ ImsReport2DAO: " + SQL);
		tempResult = simpleJdbcTemplate.query(SQL, mapper);
		for(int i = 0; i<tempResult.size();i++){
			String temp[] = tempResult.get(i);
			RptServiceInfoDTO tempDTO = new RptServiceInfoDTO();
			tempDTO.setItemHtml(temp[1]);
			result.put(temp[0],tempDTO);
		}

	} catch (EmptyResultDataAccessException erdae) {
		logger.debug("EmptyResultDataAccessException");
		result = null;
	} catch (Exception e) {
		logger.debug("Exception caught in getStaticReportWords():", e);
		throw new DAOException(e.getMessage(), e);
	}	
	
//	logger.info("steven test static words");
//	for (Object key : result.keySet()) {
//		logger.info(key + " : " + result.get(key).getItemHtml());
//    }	
	
		return result;
	}	
	
	// martin 20160422, third party credit card form setup
	public HashMap<String, String> get3rdPartyCreditCardFormStaticReportWords(String rptName)
	throws DAOException {		
		logger.info("get3rdPartyCreditCardFormStaticReportWords with rptName:"+rptName);
		List<String[]> tempResult = new ArrayList<String[]>();
		HashMap<String, String> result  = new HashMap<String, String>();
		String SQL=
			"SELECT ATTRIBUTE, CONTENTS" +
			"  FROM bomweb_rpt_template" +
			" WHERE rpt_name LIKE :rptName ";

		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("rptName", rptName);		
		
		ParameterizedRowMapper<String[]> mapper = new ParameterizedRowMapper<String[]>() {
			public String[] mapRow(ResultSet rs, int rowNum) throws SQLException {
				String[] temp2DStringArray;
				temp2DStringArray = new String[2]; 
				temp2DStringArray[0] = (String) rs.getString("ATTRIBUTE");
				temp2DStringArray[1] = (String) rs.getString("CONTENTS");
				return temp2DStringArray;
			}
		};

		try {
			tempResult = simpleJdbcTemplate.query(SQL, mapper,params);
			
			for(String temp[]:tempResult){
				result.put(temp[0],NTVUtil.defaultStringRpt(temp[1]));
			}
	
		} catch (EmptyResultDataAccessException erdae) {
			logger.debug("EmptyResultDataAccessException");
			result = null;
		} catch (Exception e) {
			logger.debug("Exception caught in get3rdPartyCreditCardFormStaticReportWords():", e);
			throw new DAOException(e.getMessage(), e);
		}
	
		return result;
	}
	
	public String isUseDBStaticWord ( )
	throws DAOException {

		List<String> result = new ArrayList<String>();
		
		String SQL = 
				" select CONTENTS from " +
				" bomweb_rpt_template " +
				" where rpt_name = 'ims/BomWebPortal' " +
				" and attribute = 'isUseDBwords' " ;
		
		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				String dto = new String();
				dto = (String) rs.getString("CONTENTS");
				return dto;
			}
		};
		
		try {
			logger.debug("isUseDBStaticWord() @ ImsReport1DAO: " + SQL);
			result = simpleJdbcTemplate.query(SQL, mapper);
			if(result.size()>0){
				logger.info("isUseDBStaticWord result.get(0):"+result.get(0));
			}else{
				logger.info("isUseDBStaticWord result.size()<0");
			}
		} catch (Exception e) {
			logger.info("Exception caught in isUseDBStaticWord():", e);
//			throw new DAOException(e.getMessage(), e);
			return "N";
		}
		
		return result.size()>0?result.get(0):"N";
	}
			
	public String isAFShowALL ( )
	throws DAOException {

		List<String> result = new ArrayList<String>();
		
		String SQL = 
				" select CONTENTS from " +
				" bomweb_rpt_template " +
				" where rpt_name = 'ims/BomWebPortal' " +
				" and attribute = 'isAFShowALL' " ;
		
		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				String dto = new String();
				dto = (String) rs.getString("CONTENTS");
				return dto;
			}
		};
		
		try {
			logger.debug("isAFShowALL() @ ImsReport1DAO: " + SQL);
			result = simpleJdbcTemplate.query(SQL, mapper);
			if(result.size()>0){
				logger.info("isAFShowALL result.get(0):"+result.get(0));
			}else{
				logger.info("isAFShowALL result.size()<0");
			}
		} catch (Exception e) {
			logger.info("Exception caught in isAFShowALL():", e);
//			throw new DAOException(e.getMessage(), e);
			return "N";
		}
		
		return result.size()>0?result.get(0):"N";
	}
	
	
	public String get_wq_remark(String order_id)
	throws DAOException {
		logger.debug("get_wq_remark(stor prod)@SbFlatFloorDAO is called");
		logger.info("order_id:" + order_id );
		
		try {
			SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
					.withSchemaName("OPS$CNM").withCatalogName("pkg_sb_ims_lts")
					.withProcedureName("get_sb_wq_remark");
			jdbcCall.setAccessCallParameterMetaData(false);
			jdbcCall.declareParameters(
					new SqlParameter("i_order_id",Types.VARCHAR), 
					new SqlOutParameter("o_RemarkText", Types.VARCHAR),
					new SqlOutParameter("gnretval", Types.INTEGER),
					new SqlOutParameter("gnerrcode", Types.INTEGER),
					new SqlOutParameter("gserrtext", Types.VARCHAR));			
			MapSqlParameterSource inMap = new MapSqlParameterSource();
			inMap.addValue("i_order_id", order_id);
			SqlParameterSource in = inMap;
		
			Map out = jdbcCall.execute(in);
			int error_code = -1;
		
			if (((Integer) out.get("gnerrcode")) != null) {
				error_code = ((Integer) out.get("gnerrcode")).intValue();
			}
			int gnRetVal=0;
			String error_text = (String) out.get("gserrtext");
			if (((Integer) out.get("gnretval")) != null) {
				 gnRetVal =  (Integer) out.get("gnretval");
			}
//			logger.info("  gnRetVal = " + gnRetVal);
//			logger.info("  error_code = " + error_code);
//			logger.info("  error_text = " + error_text);
			String remark = (String) out.get("o_RemarkText");
//			logger.info("remark:" + remark);
			return remark;
		
		} catch (Exception e) {
			logger.error("Exception caught in getSB()", e);
			throw new DAOException(e.getMessage(), e);
		}
}
	
	public Boolean isDBSignOffed (String orderid)
	throws DAOException {

		List<String> result = new ArrayList<String>();
		
		String SQL = 
				" select decode(count(*),'0','N','Y') isSignOffEd from bomweb_order " +
				" where order_id = :order_id  " +
				" and sign_off_date is not null  " ;

		MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("order_id", orderid);
			
		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				String dto = new String();
				dto = (String) rs.getString("isSignOffEd");
				return dto;
			}
		};
		
		try {
			logger.debug("key search: " + params.getValues());
			logger.debug("dBNotSignOff() @ ImsReport1DAO: " + SQL);
			result = simpleJdbcTemplate.query(SQL, mapper, params);
			if(result.size()>0){
				logger.info("dBNotSignOff result.get(0):"+result.get(0));
			}else{
				logger.info("dBNotSignOff result.size()<0");
			}
		} catch (Exception e) {
			logger.info("Exception caught in dBNotSignOff():", e);
//			throw new DAOException(e.getMessage(), e);
			return true;
		}
		
		return result.size()>0?"Y".equals(result.get(0)):true;
	}
	//Gary added for top up offer
	public List<String> getTopUpOffer (String order_id, String locale)
	throws DAOException {

		List<String> topUpOfferList = new ArrayList<String>();
		logger.info("order_id:" + order_id );
		logger.info("locale:" + locale );
		
		String SQL=		
			"select distinct b.description 		 "+
			"from w_item_display_lkup b, bomweb_subscribed_item a				 "+
			"where a.order_id = '"+order_id+		"' "+
			"and b.item_id = a.id "+
			"and b.display_type = 'TOP_UP_DESC'		"+
			"and b.locale = '"+ locale +"'                  ";	
		
		  
		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				
				String dto = new String();
				dto = (String) rs.getString("description");		
				return dto;
			}
		};
		
		try {
			logger.debug("getTopUpOffer @ ImsReport2DAO: " + SQL);
			topUpOfferList = simpleJdbcTemplate.query(SQL, mapper);
			
		
		} catch (EmptyResultDataAccessException erdae) {
			logger.debug("EmptyResultDataAccessException");
			topUpOfferList = null;
		} catch (Exception e) {
			logger.debug("Exception caught in getTopUpOfferList():", e);
			throw new DAOException(e.getMessage(), e);
		}	
		return topUpOfferList;
	}
	
	public String getSalesChannel (String shop_cd, String staff_id)
	throws DAOException {
		logger.debug("getSalesChannel is called");
		
		String salesCd = null;
		
		try{
			
			String SQL = "	 select NVL ((SELECT sales_channel  " +                                                   
			             "      FROM bomweb_shop                 " +                                  
			             "     WHERE shop_cd = ? AND channel_id = 1),   " +                   
			             "   (SELECT channel_cd                            " +                        
			             "      FROM bomweb_sales_assignment x, bomweb_sales_profile y  " +           
			             "     WHERE (y.staff_id = ? or y.org_staff_id = ?)  " +    
			         	 "and x.staff_id = y.staff_id                                    " +              
			      		 "AND TRUNC (SYSDATE) BETWEEN x.start_date                         " +               
			             "                               AND NVL (x.end_date, SYSDATE)     " +        
			             "       AND TRUNC (SYSDATE) BETWEEN y.start_date                 " +         
			             "                               AND NVL (y.end_date, SYSDATE)     " +        
			             "       AND ROWNUM = 1)                                            " +       
			             "  ) shop_cd from dual";			
			
			salesCd = simpleJdbcTemplate.queryForObject(SQL, String.class, shop_cd, staff_id, staff_id);
		}catch (EmptyResultDataAccessException erdae){
			logger.debug("EmptyResultDataAccessException");
			salesCd = "";
		}catch (Exception e) {
				logger.error("Exception caught in getSalesChannel()", e);
				throw new DAOException(e.getMessage(), e);
		}
		return salesCd;
	}
}

