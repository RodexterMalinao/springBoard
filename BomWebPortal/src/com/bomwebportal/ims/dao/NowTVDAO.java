package com.bomwebportal.ims.dao;

import oracle.jdbc.driver.OracleTypes;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;

import com.bigmachines.soap.EachRecordType;
import com.bigmachines.soap.NotificationPreferenceType;
import com.bigmachines.soap.UserType2;
import com.bigmachines.soap.UserType2Group_list;
import com.bigmachines.soap.UserType2Group_listGroup;
import com.bomwebportal.dao.*;
import com.bomwebportal.dto.*;
import com.bomwebportal.ims.dto.*;
import com.bomwebportal.ims.dto.ui.ChannelUI;
import com.bomwebportal.ims.dto.ui.ImsDecodeTypeUI;
import com.bomwebportal.ims.dto.ui.NowTVVasUI;
import com.bomwebportal.ims.service.ImsNowTVService.NowTvList;
import com.bomwebportal.exception.*;
import com.bomwebportal.ims.dto.TempUseNowTvDTO;
import com.bomwebportal.ims.dto.ui.NowTVAddUI;
import com.bomwebportal.ims.dto.ui.NowTvCampaignUI;
import com.bomwebportal.ims.dto.ui.NowTvChannelUI;
import com.bomwebportal.ims.dto.ui.NowTvPackUI;
import com.bomwebportal.ims.dto.ui.NowTvTopUpUI;
import com.bomwebportal.ims.dto.ui.NowTvVasBundle;
import com.bomwebportal.util.NTVUtil;
import com.bomwebportal.exception.DAOException;
import com.google.gson.Gson;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NowTVDAO extends BaseDAO{
	private Gson gson = new Gson();
	protected final Log logger = LogFactory.getLog(getClass());


	public List<Map<String, String>> getCpqJsonRecordForTest(String testingOrderType) throws DAOException{
		String SQL = "  select * from " +
		" (SELECT TXN_ID,gson_obj FROM W_CPQ_TXN_GSON A, BOMWEB_ORDER B WHERE TXN_ID = CPQ_TXN_ID AND ORDER_TYPE = :testingOrderType ORDER BY A.CREATE_DATE DESC) " +
		" where rownum =1";	
		ParameterizedRowMapper<Map<String, String>> mapper = new ParameterizedRowMapper<Map<String, String>>() {
			public Map<String, String> mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				HashMap<String, String> map = new HashMap<String, String>();				
				map.put("TXN_ID", rs.getString("TXN_ID"));
				map.put("gson_obj", rs.getString("gson_obj"));				
				return map;
			}
		};		
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("testingOrderType",testingOrderType);
		try {
			List<Map<String, String>> map = simpleJdbcTemplate.query(SQL, mapper, params);			
			return map;		
		}catch (EmptyResultDataAccessException erdae) {
			return null;
		}catch (Exception e) {
			logger.error("Exception caught in getPaymentMthLookupCode()", e);
			throw new DAOException(e.getMessage(), e);
		}

	}
//	public String getCpqJsonRecordForTest(String testingOrderType) {
//		String SQL = "  select * from " +
//				" (SELECT gson_obj FROM W_CPQ_TXN_GSON A, BOMWEB_ORDER B WHERE TXN_ID = CPQ_TXN_ID AND ORDER_TYPE = :testingOrderType ORDER BY A.CREATE_DATE DESC) " +
//				" where rownum =1";
//		MapSqlParameterSource params = new MapSqlParameterSource();
//		params.addValue("testingOrderType",testingOrderType);
//		return (String) simpleJdbcTemplate.queryForObject(SQL,String.class, params);
//	}
	public String getCpqJsonRecord(String txnId) {
		String SQL = "select * from (select gson_obj from W_CPQ_TXN_GSON where txn_id = :txnId order by create_date desc) where rownum = 1";
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("txnId",txnId);
		return (String) simpleJdbcTemplate.queryForObject(SQL,String.class, params);		
	}
	
	public int insertGsonTxn(String txnId,
			String gsonObj,
			String createBy, String cpqEnv) throws DAOException {
		try {
			String SQL = "INSERT INTO "
					+ " W_CPQ_TXN_GSON ("
					+ " TXN_ID, GSON_OBJ, CREATE_BY, CPQ_ENV "
					+ " ) VALUES ( "
					+ " :txnId, :gsonObj, :createBy, :cpqEnv"
					+ " ) "
					;			
					
			MapSqlParameterSource params = new MapSqlParameterSource()
				.addValue("txnId", txnId)
				.addValue("gsonObj", gsonObj)
				.addValue("cpqEnv", cpqEnv)
				.addValue("createBy", createBy);
				
			return simpleJdbcTemplate.update(SQL, params);
						
		} catch (Exception e) {
			throw new DAOException("Failed to insert csp txn.", e);
		}
		
	}
	
	public List<NowTVVasUI> getNowTVStarterList (String locale, String basketID, String appDate)throws DAOException {
		List<NowTVVasUI> NowTVStarterList = new ArrayList<NowTVVasUI>();

		String SQL= 
			"SELECT I.ID 			ITEM_ID,		 "+
			"	IDL.HTML 		ITEM_DETAIL,		 "+
			"	IDL.DISPLAY_TYPE 	VAS_TYPE,		 "+
			"	I.TYPE			ITEM_TYPE,		 "+
			"	MFIDL.HTML RECURRENT_AMT,				 "+		
			"	MTMIDL.HTML MTH_TO_MTH_RATE						 "+
			"FROM   W_ITEM_DISPLAY_LKUP  	IDL,			 "+
			"		W_ITEM_DISPLAY_LKUP   MFIDL,		 "+
			"		W_ITEM_DISPLAY_LKUP   MTMIDL,		 "+
			"		W_ITEM_PRICING			IP,							 "+
			"	W_ITEM			I,			 "+
			"	W_DIC_BASKET_ITEM_ASSGN 		BIA			 "+
			"WHERE  I.ID = IDL.ITEM_ID				 "+
			"AND	   BIA.ITEM_ID = I.ID  			 "+
			"AND    BIA.BASKET_ID = ?	 			 "+//--BASKET ID --
			"AND    IDL.LOCALE = ? 			 "+ // --LOCALE------
			"AND	I.TYPE like 'NTV_FREE'				 "+
			"AND	I.lob = 'IMS'					 " +
			"AND    I.ID = MFIDL.ITEM_ID					 "+
			"AND    I.ID = MTMIDL.ITEM_ID								 "+										
			"AND    MFIDL.LOCALE = ? 										 "+//--LOCALE--
			"AND	   MFIDL.DISPLAY_TYPE = 'MTHFIX'					 "+
			"AND    MTMIDL.LOCALE = ? 									 "+	//--LOCALE--
			"AND	   MTMIDL.DISPLAY_TYPE = 'MTH2MTH'			 "+
			"AND    I.ID = IP.ID		 "+
			"AND    NVL(IP.EFF_END_DATE,"+appDate+")>="+appDate+"	 "+
			"AND    IP.EFF_START_DATE<="+appDate+"	 "+ 
			"AND    NVL(BIA.EFF_END_DATE,"+appDate+")>="+appDate+"	 "+
			"AND    BIA.EFF_START_DATE<="+appDate+"	 "+ 
			"AND 	IDL.DISPLAY_TYPE = 'DETAIL'		";
		
		ParameterizedRowMapper<NowTVVasUI> mapper = new ParameterizedRowMapper<NowTVVasUI>() {
	        public NowTVVasUI mapRow(ResultSet rs, int rowNum) throws SQLException {
	        	NowTVVasUI NowTVStarter = new NowTVVasUI();
	        	NowTVStarter.setItemID(rs.getString("ITEM_ID"));
	        	NowTVStarter.setItemDesc(rs.getString("ITEM_DETAIL"));
	        	NowTVStarter.setVasType(rs.getString("VAS_TYPE"));
	        	NowTVStarter.setItemType(rs.getString("ITEM_TYPE"));
	        	NowTVStarter.setRecurrentAmt(rs.getString("RECURRENT_AMT"));
	        	NowTVStarter.setMthToMthRate(rs.getString("MTH_TO_MTH_RATE"));
	        	
	            return NowTVStarter;
	        }
	    };

		try {
			logger.debug("getNowTVStarterList @ NowTVDAO: " + SQL);
			NowTVStarterList = simpleJdbcTemplate.query(SQL, mapper,basketID,locale,locale,locale);
		} catch (EmptyResultDataAccessException erdae) {
			logger.debug("EmptyResultDataAccessException");
			NowTVStarterList = null;
		} catch (Exception e) {
			logger.debug("Exception caught in getNowTVStarterList():", e);
			throw new DAOException(e.getMessage(), e);
		}	
		return NowTVStarterList;
	}
	
	public List<NowTVVasUI> getNowTVDescList (String locale, String NowTVFormType, String DescType, String appDate)throws DAOException {
		List<NowTVVasUI> NowTVDescList = new ArrayList<NowTVVasUI>();

		String SQL=
			"SELECT TC.CHANNEL_ID,								 " +
			"			GTDL.HTML GRP_DESC,				 "+
			"   	   TDL.HTML CHANNEL_DESC,				 "+
			"	   TC.TV_TYPE						 "+
			"FROM   W_TV_CHANNEL TC,					 "+	
			"	   W_TV_DISPLAY_LKUP TDL,				 "+	
			"	   W_TV_DISPLAY_LKUP GTDL,				 "+	
			"   	    W_TV_CHANNEL_GROUP TCG,				 "+	
			"	   	W_TV_FORM TF					 "+		
			"WHERE  TF.FORM_ID = TCG.FORM_ID		 "+
			"AND    TCG.CHANNEL_GROUP_ID = TC.CHANNEL_GROUP_ID		 "+
			"AND	   TCG.CHANNEL_GROUP_ID = GTDL.ID		 "+
			"AND	   TDL.LOCALE = ? 	  		 		 "+//--LOCALE------
			"AND	   GTDL.LOCALE = ? 	  		 		 "+//--LOCALE------
			"AND	   TC.CHANNEL_ID = TDL.ID		 "+
			"AND    TF.FORM_TYPE = ?	 		 "+//--NOW TV from TYPE --
			"AND	   TDL.TYPE = 'CHANNEL'		 "+
			"AND	   GTDL.TYPE = 'GROUP'		 "+
			"AND	   TC.TV_TYPE = 'NA'		 "+
			"AND	   TCG.CHANNEL_GROUP_CD = ?	 "+
			"AND    NVL(TC.EFF_END_DATE,"+appDate+")>="+appDate+"		 "+
			"AND    TC.EFF_START_DATE<="+appDate+" 			 "+
			"AND    	TF.LOB='IMS'								 "+
			"AND   TC.IMS_CHANNEL_TYPE IN (select code from w_code_lkup where grp_id = 'IMS_NTV_PCD_TYPE')	 "+
			"ORDER BY TCG.DISPLAY_SEQ, TC.DISPLAY_SEQ		";
		
		ParameterizedRowMapper<NowTVVasUI> mapper = new ParameterizedRowMapper<NowTVVasUI>() {
	        public NowTVVasUI mapRow(ResultSet rs, int rowNum) throws SQLException {
	        	NowTVVasUI NowTVDesc = new NowTVVasUI();
	        	NowTVDesc.setChannelID(rs.getString("CHANNEL_ID"));
	        	NowTVDesc.setGrpDesc(rs.getString("GRP_DESC"));
	        	NowTVDesc.setChannelDesc(rs.getString("CHANNEL_DESC"));
	        	NowTVDesc.setTVType(rs.getString("TV_TYPE"));
	        	
	        	
	            return NowTVDesc;
	        }
	    };

		try {
			logger.debug("getNowTVDescList @ NowTVDAO: " + SQL);
			NowTVDescList = simpleJdbcTemplate.query(SQL, mapper,locale,locale,NowTVFormType,DescType);
		} catch (EmptyResultDataAccessException erdae) {
			logger.debug("EmptyResultDataAccessException");
			NowTVDescList = null;
		} catch (Exception e) {
			logger.debug("Exception caught in getNowTVDescList():", e);
			throw new DAOException(e.getMessage(), e);
		}	
		return NowTVDescList;
	}
	

	
	public List<NowTVVasUI> getNowTVVasList (String locale,
											 String basketID,
											 String NowTVFormType, 
											 String IsCoupon, 
											 String TVTypeStr, 
											 String HOSTypeStr, 
											 String ContractPeriod, 
											 String appDate,
											 String housing_type)throws DAOException {
		List<NowTVVasUI> NowTVVasList = new ArrayList<NowTVVasUI>();

		String nowTVGroupStr = "SB_IMS_NTV_VAS";
		String SQL=

			
			"SELECT I.ID 			ITEM_ID,		 "+
			"	DIDL.HTML 		ITEM_DETAIL,			 "+	
			"	DIDL.HTML 	VAS_TYPE,				 "+
			"	I.TYPE			ITEM_TYPE,			 "+	
			"	MFIDL.HTML RECURRENT_AMT,				 "+		
			"	MTMIDL.HTML MTH_TO_MTH_RATE,						 "+
			"	IDT.CREDIT,								 "+
			"	IDT.TV_TYPE,							 "+
			"	COUPON_DISP.DESCRIPTION COUPON_DESC,									 "+
			"	HOS_FILTER.PRICE_FROM,								 "+
			"	HOS_FILTER.PRICE_TO,									 "+
			"	CORE_PRICE.PRICE,											 "+
			"	CL2.DESCRIPTION TV_LIST											 "+
			"FROM   W_ITEM_DISPLAY_LKUP  	DIDL,					 "+
			"	W_ITEM_PRICING		IP,					 "+
			"	W_ITEM			I,					 "+
			"	W_ITEM_DTL_TV		IDT,					 "+
			"	W_ITEM_DTL_RP_RB_VAS 	IDRV,			 "+
			"		W_ITEM_DISPLAY_LKUP   MFIDL,		 "+
			"		W_ITEM_DISPLAY_LKUP   MTMIDL,		 "+
			"		W_DIC_BASKET_ITEM_ASSGN 		BIA,			 "+
			"		W_CODE_LKUP						CL,			 "+
			"		W_CODE_LKUP						CL2,	 "+
			"		(SELECT IP_CORE.RECURRENT_AMT PRICE																						 "+
			"		 FROM   W_ITEM_PRICING IP_CORE,																								 "+
			"		   			W_DIC_BASKET_ITEM_ASSGN BIA_CORE,																					 "+
			"		   			W_ITEM I_CORE																													 "+
			"		 WHERE  BIA_CORE.BASKET_ID = ?  																	 "+//-- BASKET ID
			"		 AND	   BIA_CORE.ITEM_ID = I_CORE.ID																					 "+
			"		 AND    I_CORE.TYPE = 'PROG'																									 "+
			"		 AND    I_CORE.ID = IP_CORE.ID) CORE_PRICE,																		 "+
			"		(SELECT   SP_TYPE.DESCRIPTION TYPE, 																					 "+
			"							DECODE(SP_TYPE.CODE,' ',NULL,SP_TYPE.CODE) HOUSIMG_TYPE, 						 "+
			"							DECODE(SP_FR.DESCRIPTION,' ',NULL,SP_FR.DESCRIPTION) PRICE_FROM,		 "+
			"							DECODE(SP_TO.DESCRIPTION,' ',NULL,SP_TO.DESCRIPTION) PRICE_TO				 "+
			"		 FROM 	 W_CODE_LKUP SP_TYPE, 																								 "+		
			"		   			W_CODE_LKUP SP_FR, 																										 "+
			"		   			W_CODE_LKUP SP_TO 																										 "+
			"		 WHERE 	 SP_TYPE.GRP_ID = 'IMS_ACQ_FILTER_SP' 																 "+
			"		 AND	 SP_FR.GRP_ID = 'IMS_ACQ_FLT_SP_FR'																			 "+
			"		 AND	 SP_TO.GRP_ID = 'IMS_ACQ_FLT_SP_TO' 																		 "+
			"		 AND 	 SP_TYPE.DESCRIPTION = SP_FR.CODE 																			 "+
			"		 AND 	 SP_TYPE.DESCRIPTION = SP_TO.CODE " +
			"union SELECT code,null,null,null from w_code_lkup where grp_id = 'IMS_ACQ_FILTER_ALL'		)                  HOS_FILTER,  			 "+
			"		W_DISPLAY_LKUP		  COUPON_DISP			" +
			"WHERE  I.ID = IP.ID									" +
			"AND	BIA.ITEM_ID = I.ID									 "+
			"AND    BIA.BASKET_ID = ?  						 "+	//-- BASKET ID
			"AND	I.ID = IDRV.ID					 "+
			"AND	I.ID = IDT.ITEM_ID					 "+
			"AND	IDT.TV_TYPE IN ('"+TVTypeStr+"')					 "+//--TV TYPE--	
			"AND    I.ID = DIDL.ITEM_ID											 "+								
			"AND    DIDL.LOCALE = ? 										 "+//--LOCALE--
			"AND	   DIDL.DISPLAY_TYPE = 'DETAIL'					 "+
			"AND    I.ID = MFIDL.ITEM_ID					 "+
			"AND    I.ID = MTMIDL.ITEM_ID								 "+										
			"AND    MFIDL.LOCALE = ? 										 "+//--LOCALE--
			"AND	   MFIDL.DISPLAY_TYPE = 'MTHFIX'					 "+
			"AND    MTMIDL.LOCALE = ? 									 "+	//--LOCALE--
			"AND	   MTMIDL.DISPLAY_TYPE = 'MTH2MTH'			 "+
//			"AND	I.TYPE LIKE 'NTV%'						 "+
			"AND	   CL.GRP_ID = '"+nowTVGroupStr+"'		 "+
			"AND	   CL.CODE = I.TYPE					 "+
			"AND	   CL2.CODE(+) = I.TYPE			 "+
			"AND    CL2.GRP_ID(+) = 'SB_IMS_NTV_LIST'			 "+
			"AND    NVL(HOS_FILTER.HOUSIMG_TYPE,'"+HOSTypeStr+"') = '"+HOSTypeStr+"'	 "+
//			"AND    HOS_FILTER.TYPE = I.TYPE" +
			" AND    HOS_FILTER.TYPE = SUBSTR(I.TYPE,1,9)       																			 "+
			"AND   	NVL(HOS_FILTER.PRICE_FROM,CORE_PRICE.PRICE) <= CORE_PRICE.PRICE		 "+
			"AND	 	NVL(HOS_FILTER.PRICE_TO,CORE_PRICE.PRICE) >= CORE_PRICE.PRICE			 "+
			"AND   COUPON_DISP.TYPE like 'IMS_COUPON%'			 "+
			"AND	  IDT.CREDIT = COUPON_DISP.ID				 "+
			"AND	  COUPON_DISP.LOCALE = 'en'								 "+
			"AND	I.LOB = 'IMS'							 "+
			"AND    NVL(IP.EFF_END_DATE,"+appDate+")>="+appDate+"	 "+
			"AND    IP.EFF_START_DATE<="+appDate+"	 "+ 
			"AND    NVL(BIA.EFF_END_DATE,"+appDate+")>="+appDate+"	 "+
			"AND    BIA.EFF_START_DATE<="+appDate+"	 "+ 
			"ORDER BY I.TYPE, IDT.TV_TYPE DESC, IDT.CREDIT		";
		
		ParameterizedRowMapper<NowTVVasUI> mapper = new ParameterizedRowMapper<NowTVVasUI>() {
	        public NowTVVasUI mapRow(ResultSet rs, int rowNum) throws SQLException {
	        	NowTVVasUI NowTVVas = new NowTVVasUI();
	        	NowTVVas.setItemID(rs.getString("ITEM_ID"));
	        	NowTVVas.setItemDesc(rs.getString("ITEM_DETAIL"));
	        	NowTVVas.setVasType(rs.getString("VAS_TYPE"));
	        	NowTVVas.setItemType(rs.getString("ITEM_TYPE"));
	        	NowTVVas.setRecurrentAmt(rs.getString("RECURRENT_AMT"));
	        	NowTVVas.setMthToMthRate(rs.getString("MTH_TO_MTH_RATE"));
	        	NowTVVas.setCredit(rs.getString("CREDIT"));
	        	NowTVVas.setTVType(rs.getString("TV_TYPE"));
	        	NowTVVas.setCouponDesc(rs.getString("COUPON_DESC"));
	        	NowTVVas.setTVList(rs.getString("TV_LIST"));
	        	return NowTVVas;
	        }
	    };

		try {
			logger.debug("getNowTVVasList @ NowTVDAO: " + SQL);
			NowTVVasList = simpleJdbcTemplate.query(SQL, mapper,basketID,basketID,locale,locale,locale);
		} catch (EmptyResultDataAccessException erdae) {
			logger.debug("EmptyResultDataAccessException");
			NowTVVasList = null;
		} catch (Exception e) {
			logger.debug("Exception caught in getNowTVVasList():", e);
			throw new DAOException(e.getMessage(), e);
		}	
		return NowTVVasList;
	}
	
	public List<NowTVVasUI> getNowTVOtherList (String locale, 
											   String contractPeriod, 
											   String TVTypeStr, 
											   String appDate,
											   String housing_type, 
											   String pcdLike100Ind,
											   String pcdNowOneBoxInd,
											   String bandwidth)throws DAOException {
		List<NowTVVasUI> NowTVOtherList = new ArrayList<NowTVVasUI>();

		String SQL=
		
			"SELECT I.ID 						ITEM_ID,			 "+
			"		DIDL.HTML 					ITEM_DETAIL,		 "+		
			"		DIDL.HTML 					VAS_TYPE,			 "+
			"		I.TYPE						ITEM_TYPE,			 "+
			"		BIA.MDO_IND,									 "+
			"		MFIDL.HTML 					RECURRENT_AMT,		 "+	
			"		MTMIDL.HTML 				MTH_TO_MTH_RATE,	 "+	
			"		IDT.CREDIT,										 "+
			"		IDT.TV_TYPE,									 "+
			"		COUPON_DISP.DESCRIPTION 	COUPON_DESC,		 "+
			"		VAS_GROUP.DESCRIPTION 		GROUP_DESC,			 "+
			"		VAS_GROUP.ID 				GROUP_ORDER,		 "+								
			"		CL2.DESCRIPTION 			TV_LIST				 "+											
			"FROM   W_ITEM_DISPLAY_LKUP  		DIDL,				 "+	
			"		W_ITEM_PRICING				IP,					 "+
			"		W_ITEM						I,					 "+
			"		W_ITEM_TYPE					IT,					 "+
			"		W_ITEM_TYPE					IT2,				 "+			
			"		W_ITEM_DTL_TV				IDT,				 "+	
			"		W_ITEM_DTL_RP_RB_VAS 		IDRV,				 "+
			"		W_ITEM_DISPLAY_LKUP   		MFIDL,				 "+
			"		W_ITEM_DISPLAY_LKUP   		MTMIDL,				 "+
			"		W_DIC_BASKET_ITEM_ASSGN 	BIA,				 "+
			"		W_CODE_LKUP					CL,					 "+
			"		W_CODE_LKUP					CL2,				 "+	
			"		W_DISPLAY_LKUP		  		VAS_GROUP, 			 "+
			"		W_DISPLAY_LKUP		  		COUPON_DISP,			 "+
			"		w_item_dtl_ims				idi		 "+
			"WHERE  I.ID = IP.ID									 "+					
			"AND	BIA.ITEM_ID = I.ID								 "+
			"AND 	I.ID = IT.ITEM_ID								 "+
			"AND 	IT.TYPE = 'BRAND'							 	 "+
			"AND 	I.ID = IT2.ITEM_ID								 "+
			"AND 	IT2.TYPE = 'NOWONESPEC'							 "+
			"AND 	IT.TYPE_DESC = DECODE('"+pcdLike100Ind+"','Y','LIKE','NETVIGATOR')	"+
			"AND 	IT2.TYPE_DESC = DECODE('"+pcdNowOneBoxInd+"','Y',IT2.TYPE_DESC,'N')		"+	
			"AND    BIA.BASKET_ID = 200888889  					 "+		//-- BASKET ID
			"AND	I.ID = IDRV.ID									 "+
			"AND	I.ID = IDT.ITEM_ID								 "+
			"and i.id = idi.item_id(+)	"+
			"AND	(IDT.TV_TYPE IN ('"+TVTypeStr+"')		 "+//--TV TYPE--
			"		OR	IDT.TV_TYPE IS NULL)						 "+
			"AND    I.ID = DIDL.ITEM_ID								 "+											
			"AND    DIDL.LOCALE = ? 						 "+		//		--LOCALE--
			"AND	DIDL.DISPLAY_TYPE = 'DETAIL'					 "+
			"AND    I.ID = MFIDL.ITEM_ID							 "+
			"AND    I.ID = MTMIDL.ITEM_ID							 "+											
			"AND    MFIDL.LOCALE = DIDL.LOCALE 						 "+			
			"AND	MFIDL.DISPLAY_TYPE = 'MTHFIX'					 "+	
			"AND    MTMIDL.LOCALE = DIDL.LOCALE 					 "+
			"AND	MTMIDL.DISPLAY_TYPE = 'MTH2MTH'					 "+		
			"AND	CL.CODE = I.TYPE								 "+			
			"AND	CL2.CODE(+) = I.TYPE							 "+
			"AND    CL2.GRP_ID(+) = 'SB_IMS_NTV_LIST'				 "+
			"AND	CL.GRP_ID = VAS_GROUP.TYPE						 "+
			"AND	VAS_GROUP.TYPE <> 'IMS_ITYPE_NTV_VAS'			 "+
			"AND	VAS_GROUP.LOCALE = DIDL.LOCALE 					 "+
			"AND   	COUPON_DISP.TYPE like 'IMS_COUPON%'				 "+
			"AND	NVL(IDT.CREDIT,0) = COUPON_DISP.ID				 "+
			"AND	COUPON_DISP.LOCALE = 'en'						 "+		
			"AND	I.LOB = 'IMS'									 "+
			"and ','||replace(nvl(idi.housing_type,'"+housing_type+"'),' ','')||',' like '%,'||'"+housing_type +"'||',%'	"+
			"AND    NVL(IP.EFF_END_DATE,"+appDate+")>="+appDate+"	 "+
			"AND    IP.EFF_START_DATE<="+appDate+"	 				 "+
			"AND    NVL(BIA.EFF_END_DATE,"+appDate+")>="+appDate+"	 "+
			"AND    BIA.EFF_START_DATE<="+appDate+"	 				 "+
			"AND	IDRV.CONTRACT_PERIOD IN ('"+contractPeriod+"')	 "+
			"AND	to_number('"+bandwidth+"')>=to_number(nvl(IDT.MIN_BW,'"+bandwidth+"'))"+   	
			"AND	to_number('"+bandwidth+"')<=to_number(nvl(IDT.MAX_BW,'"+bandwidth+"'))"+   	
			"ORDER BY VAS_GROUP.ID, I.TYPE, IDT.TV_TYPE DESC, IDT.CREDIT ";
		
		ParameterizedRowMapper<NowTVVasUI> mapper = new ParameterizedRowMapper<NowTVVasUI>() {
	        public NowTVVasUI mapRow(ResultSet rs, int rowNum) throws SQLException {
	        	NowTVVasUI NowTVOther = new NowTVVasUI();
        	
	        	NowTVOther.setItemID(rs.getString("ITEM_ID"));
	        	NowTVOther.setItemDesc(rs.getString("ITEM_DETAIL"));
	        	NowTVOther.setVasType(rs.getString("VAS_TYPE"));
	        	NowTVOther.setItemType(rs.getString("ITEM_TYPE"));
	        	NowTVOther.setRecurrentAmt(rs.getString("RECURRENT_AMT"));
	        	NowTVOther.setMthToMthRate(rs.getString("MTH_TO_MTH_RATE"));
	        	NowTVOther.setCredit(rs.getString("CREDIT"));
	        	NowTVOther.setTVType(rs.getString("TV_TYPE"));
	        	NowTVOther.setCouponDesc(rs.getString("COUPON_DESC"));
	        	NowTVOther.setTVList(rs.getString("TV_LIST"));
	        	NowTVOther.setGrpDesc(rs.getString("GROUP_DESC"));
	        	NowTVOther.setMDOInd(rs.getString("MDO_IND"));
	        	
	        	
	            return NowTVOther;
	        }
	    };

		try {
			logger.debug("getNowTVOtherListt @ NowTVDAO: " + SQL);
			NowTVOtherList = simpleJdbcTemplate.query(SQL, mapper,locale);			
			if (NowTVOtherList != null && NowTVOtherList.size() > 0) {
				for (NowTVVasUI n:NowTVOtherList) {
					n.setGrpDesc(NTVUtil.defaultString(n.getGrpDesc()));
					n.setItemDesc(NTVUtil.defaultString(n.getItemDesc()));
				}
			}
		} catch (EmptyResultDataAccessException erdae) {
			logger.debug("EmptyResultDataAccessException");
			NowTVOtherList = null;
		} catch (Exception e) {
			logger.debug("Exception caught in getNowTVOtherList():", e);
			throw new DAOException(e.getMessage(), e);
		}	
		return NowTVOtherList;
	}
	
	public List<ChannelUI> getNowTVChannelList (String locale, 
												String NowTVFormType ,
												String TVTypeStr,
												boolean IsLamma, 
												String appDate)throws DAOException {
		List<ChannelUI> NowTVChannelList = new ArrayList<ChannelUI>();

		if(IsLamma && 
				(NowTVFormType.equals("PCD List")
						||NowTVFormType.equals("PON List")
						||NowTVFormType.equals("PCD List - Lamma"))){
			NowTVFormType = "PCD List - Lamma";
		}
		
		String SQL=

/*			"SELECT TC.CHANNEL_ID,									 "+
			"	   	NVL(TC.CHANNEL_CD, ' ') CHANNEL_CD,				 "+
			"	   	TC.CREDIT,										 "+
			"	   	TC.IS_ADULT_CHANNEL,							 "+
			"	   	GTDL.HTML GRP_DESC,						 "+
			"   	TDL.HTML CHANNEL_DESC,							 "+
			"		TC.MDO_IND,										 "+
			"		TC.TV_TYPE										 "+
			"FROM   W_TV_CHANNEL TC,								 "+
			"	   	W_TV_DISPLAY_LKUP TDL,							 "+
			"	   	W_TV_DISPLAY_LKUP GTDL,							 "+
			"   	W_TV_CHANNEL_GROUP TCG,							 "+
			"	   	W_TV_FORM TF									 "+
			"WHERE  TF.FORM_ID = TCG.FORM_ID						 "+
			"AND    TCG.CHANNEL_GROUP_ID = TC.CHANNEL_GROUP_ID		 "+
			"AND	   TCG.CHANNEL_GROUP_ID = GTDL.ID				 "+
			"AND	   TDL.LOCALE = ? 	  		 		 "+//--LOCALE------
			"AND	   GTDL.LOCALE = ? 	  		 		 "+//--LOCALE------
			"AND	   TC.CHANNEL_ID = TDL.ID						 "+
			"AND    	TF.FORM_TYPE = ?	 		 "+//
			"AND	   TDL.TYPE = 'CHANNEL'							 "+
			"AND	   GTDL.TYPE = 'GROUP'							 "+
			"AND	  TCG.CHANNEL_GROUP_CD <> 'FREE2HD'				 "+
			"AND	   TC.TV_TYPE IN ('"+TVTypeStr+"')				 "+
			"AND    	TF.LOB='IMS'								 "+
			"ORDER BY TCG.DISPLAY_SEQ, TC.DISPLAY_SEQ				";*/
			
			"SELECT TC.CHANNEL_ID,							 "+
			"	NVL(TC.CHANNEL_CD, ' ') CHANNEL_CD,				 "+
			"	TC.CREDIT,							 "+
			"	TC.IS_ADULT_CHANNEL,						 "+
			"	GTDL.HTML GRP_DESC,						 "+
			"   	TDL.HTML CHANNEL_DESC,						 "+
			"	TC.MDO_IND,							 "+
			"	TC.TV_TYPE,							 "+
			"	CHILD_CH.CHANNEL_ID	  CHILD_CH_ID,				 "+
			"	CHILD_CH.CHANNEL_CD  	  CHILD_CH_CD,				 "+
			"	CHILD_CH.CREDIT 	  CHILD_CH_CREDIT, 			 "+
			"	CHILD_CH.IS_ADULT_CHANNEL CHILD_CH_IS_ADULT, 			 "+
			"	CHILD_CH.CHANNEL_DESC 	  CHILD_CH_DESC,			 "+
			"	CHILD_CH.MDO_IND	  CHILD_CH_MDO_IND,			 "+
			"	CHILD_CH.TV_TYPE	  CHILD_CH_TV_TYPE			 "+
			"FROM   W_TV_CHANNEL TC,						 "+
			"	W_TV_DISPLAY_LKUP TDL,						 "+
			"	W_TV_DISPLAY_LKUP GTDL,						 "+
			"   	W_TV_CHANNEL_GROUP TCG,						 "+
			"	W_TV_FORM TF,							 "+
			"	(SELECT   TC.CHANNEL_ID, 					 "+
			"		  NVL(TC.CHANNEL_CD, ' ') CHANNEL_CD,			 "+
			"		  TC.CREDIT, 						 "+
			"		  TC.IS_ADULT_CHANNEL, 					 "+
			"		  TDL.HTML CHANNEL_DESC,				 "+
			"		  TC.MDO_IND,						 "+
			"		  TC.TV_TYPE,						 "+
			"		  TC.PARENT_CHANNEL_ID					 "+
		 	"	FROM  	  W_TV_CHANNEL TC,					 "+
		 	"		  W_TV_DISPLAY_LKUP TDL					 "+
		 	"	WHERE 	  PARENT_CHANNEL_ID IS NOT NULL				 "+
			"	AND	  TC.TV_TYPE IN ('"+TVTypeStr+"','NA')			 "+
		 	"	AND	  TDL.LOCALE = ? 	 			 "+//--LOCALE------	
		 	"	AND	  TDL.TYPE = 'CHANNEL'					 "+
		 	"	AND    NVL(TC.EFF_END_DATE,"+appDate+")>="+appDate+"		 "+
			"	AND    TC.EFF_START_DATE<="+appDate+" 			 "+
		 	"	AND	  TC.CHANNEL_ID = TDL.ID) CHILD_CH			 "+
			"WHERE  TF.FORM_ID = TCG.FORM_ID					 "+
			"AND    TCG.CHANNEL_GROUP_ID = TC.CHANNEL_GROUP_ID			 "+
			"AND	TCG.CHANNEL_GROUP_ID = GTDL.ID					 "+
			"AND	TDL.LOCALE = ? 	  	 				 "+//--LOCALE------	
			"AND	GTDL.LOCALE = ? 	  	 		 "+//--LOCALE------	
			"AND	TC.CHANNEL_ID = TDL.ID						 "+
			"AND    TF.FORM_TYPE = ?	 			 "+//--NOW TV from TYPE --
			"AND	TDL.TYPE = 'CHANNEL'						 "+
			"AND	GTDL.TYPE = 'GROUP'						 "+
			"AND	TCG.CHANNEL_GROUP_CD NOT IN ('FREE2HD','STARTERPACK','ENTPACK')		 "+
			"AND	TC.TV_TYPE IN ('"+TVTypeStr+"', 'NA')					 "+
			"AND	TC.PARENT_CHANNEL_ID IS NULL					 "+
			"AND	TC.CHANNEL_ID = CHILD_CH.PARENT_CHANNEL_ID(+)			 "+
			"AND    NVL(TC.EFF_END_DATE,"+appDate+")>="+appDate+"		 "+
			"AND    TC.EFF_START_DATE<="+appDate+" 			 "+	
			"AND    TF.LOB='IMS'							 "+
			"AND TC.IMS_CHANNEL_TYPE IN (select code from w_code_lkup where grp_id = 'IMS_NTV_PCD_TYPE')	"+
			"ORDER BY TCG.DISPLAY_SEQ, TC.DISPLAY_SEQ				";

		ParameterizedRowMapper<ChannelUI> mapper = new ParameterizedRowMapper<ChannelUI>() {
	        public ChannelUI mapRow(ResultSet rs, int rowNum) throws SQLException {
	        	ChannelUI ChannelDetails = new ChannelUI();
	        	ChannelDetails.setChannelID(rs.getString("CHANNEL_ID"));
	        	ChannelDetails.setChannelCD(rs.getString("CHANNEL_CD"));
	        	ChannelDetails.setCredit(rs.getString("CREDIT"));
	        	ChannelDetails.setIsAdult(rs.getString("IS_ADULT_CHANNEL"));
	        	ChannelDetails.setGroupDesc(rs.getString("GRP_DESC"));
	        	ChannelDetails.setChannelDesc(rs.getString("CHANNEL_DESC"));
	        	ChannelDetails.setMDOInd(rs.getString("MDO_IND"));
	        	ChannelDetails.setTVType(rs.getString("TV_TYPE"));
	        	
	        	ChannelDetails.setChildChID(rs.getString("CHILD_CH_ID"));
	        	ChannelDetails.setChildChCD(rs.getString("CHILD_CH_CD"));
	        	ChannelDetails.setChildCredit(rs.getString("CHILD_CH_CREDIT"));
	        	ChannelDetails.setChildIsAdult(rs.getString("CHILD_CH_IS_ADULT"));
	        	ChannelDetails.setChildChDesc(rs.getString("CHILD_CH_DESC"));
	        	ChannelDetails.setChildChMDOInd(rs.getString("CHILD_CH_MDO_IND"));
	        	ChannelDetails.setChildChTVType(rs.getString("CHILD_CH_TV_TYPE"));

	            return ChannelDetails;
	        }
	    };

		try {
			logger.debug("getNowTVChannelList @ NowTVDAO: " + SQL);
			NowTVChannelList = simpleJdbcTemplate.query(SQL, mapper,locale,locale,locale,NowTVFormType);
		} catch (EmptyResultDataAccessException erdae) {
			logger.debug("EmptyResultDataAccessException");
			NowTVChannelList = null;
		} catch (Exception e) {
			logger.debug("Exception caught in getNowTVChannelList():", e);
			throw new DAOException(e.getMessage(), e);
		}	
		return NowTVChannelList;
	}
	
	public List<ChannelUI> getNowTVHDList(String locale,
			String NowTVFormType, String TVTypeStr, String appDate)
			throws DAOException {
		List<ChannelUI> NowTVHDList = new ArrayList<ChannelUI>();


		String SQL =

		"SELECT TC.CHANNEL_ID,									 "
				+ "	   	NVL(TC.CHANNEL_CD, ' ') CHANNEL_CD,				 "
				+ "	   	TC.CREDIT,										 "
				+ "	   	TC.IS_ADULT_CHANNEL,							 "
				+ "	   	GTDL.HTML GRP_DESC,						 "
				+ "   	TDL.HTML CHANNEL_DESC,							 "
				+ "		TC.MDO_IND,										 " 
				+ "		TC.TV_TYPE										 "
				+ "FROM   W_TV_CHANNEL TC,								 "
				+ "	   	W_TV_DISPLAY_LKUP TDL,							 "
				+ "	   	W_TV_DISPLAY_LKUP GTDL,							 "
				+ "   	W_TV_CHANNEL_GROUP TCG,							 "
				+ "	   	W_TV_FORM TF									 "
				+ "WHERE  TF.FORM_ID = TCG.FORM_ID						 "
				+ "AND    TCG.CHANNEL_GROUP_ID = TC.CHANNEL_GROUP_ID		 "
				+ "AND	   TCG.CHANNEL_GROUP_ID = GTDL.ID				 "
				+ "AND	   TDL.LOCALE = ? 	  		 		 "//--LOCALE------
				+ "AND	   GTDL.LOCALE = ? 	  		 		 "//--LOCALE------
				+ "AND	   TC.CHANNEL_ID = TDL.ID						 "
				+ "AND    TF.FORM_TYPE = ?	 		 "//--NOW TV from TYPE --
				+ "AND	   TDL.TYPE = 'CHANNEL'							 "
				+ "AND	   GTDL.TYPE = 'GROUP'							 "
				+ "AND	  TCG.CHANNEL_GROUP_CD = 'FREE2HD'				 "
				+ "AND	   TC.TV_TYPE IN ('" + TVTypeStr + "')				 "
				+ "AND    NVL(TC.EFF_END_DATE,"+appDate+")>="+appDate+"		 "
				+ "AND    TC.EFF_START_DATE<="+appDate+" 			 "
				+ "AND    	TF.LOB='IMS'								 "
				+ "ORDER BY TCG.DISPLAY_SEQ, TC.DISPLAY_SEQ				";

		ParameterizedRowMapper<ChannelUI> mapper = new ParameterizedRowMapper<ChannelUI>() {
			public ChannelUI mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				ChannelUI HDChannelDetails = new ChannelUI();
				HDChannelDetails.setChannelID(rs.getString("CHANNEL_ID"));
				HDChannelDetails.setChannelCD(rs.getString("CHANNEL_CD"));
				HDChannelDetails.setCredit(rs.getString("CREDIT"));
				HDChannelDetails.setIsAdult(rs.getString("IS_ADULT_CHANNEL"));
				HDChannelDetails.setGroupDesc(rs.getString("GRP_DESC"));
				HDChannelDetails.setChannelDesc(rs.getString("CHANNEL_DESC"));
				HDChannelDetails.setMDOInd(rs.getString("MDO_IND"));
				HDChannelDetails.setTVType(rs.getString("TV_TYPE"));

				return HDChannelDetails;
			}
		};

		try {
			logger.debug("getNowTVHDList @ NowTVDAO: " + SQL);
			NowTVHDList = simpleJdbcTemplate.query(SQL, mapper, locale,locale,
					NowTVFormType);
		} catch (EmptyResultDataAccessException erdae) {
			logger.debug("EmptyResultDataAccessException");
			NowTVHDList = null;
		} catch (Exception e) {
			logger.debug("Exception caught in getNowTVHDList():", e);
			throw new DAOException(e.getMessage(), e);
		}
		return NowTVHDList;
	}
	
	public List<ChannelUI> getExclusiveList(String locale, List<String> selectedList, String appDate)
			throws DAOException {
		List<ChannelUI> ExclusiveList = new ArrayList<ChannelUI>();

		String SQL =

		"SELECT TCE.CHANNEL_ID_A, TCE.CHANNEL_ID_B, TA.CHANNEL_CD CHANNEL_CD_A,	 "
		+ "		TB.CHANNEL_CD CHANNEL_CD_B,										 "
		+ "		TA.CH_DESC CH_DESC_A,											 "
		+ "		TB.CH_DESC CH_DESC_B											 "
		+ "		FROM   W_TV_CHANNEL_EXCLUSIVE TCE,								 "
		+ "		(SELECT TC.CHANNEL_ID, TC.CHANNEL_CD, TDL.HTML CH_DESC			 "
		+ "				FROM W_TV_CHANNEL TC, W_TV_DISPLAY_LKUP TDL				 "
		+ "				WHERE TC.CHANNEL_ID = TDL.ID							 "
		+ "				AND TDL.LOCALE = ?							 "//-- locale --
		+ "				AND    NVL(TC.EFF_END_DATE,"+appDate+")>="+appDate+"		 "
		+ "				AND    TC.EFF_START_DATE<="+appDate+" 			 "
		+ "				AND TDL.TYPE = 'CHANNEL') TA,							 "
		+ "		(SELECT TC.CHANNEL_ID, TC.CHANNEL_CD, TDL.HTML CH_DESC			 "
		+ "				FROM W_TV_CHANNEL TC, W_TV_DISPLAY_LKUP TDL				 "
		+ "				WHERE TC.CHANNEL_ID = TDL.ID							 "
		+ "				AND TDL.LOCALE = ?							 "//-- locale --
		+ "				AND    NVL(TC.EFF_END_DATE,"+appDate+")>="+appDate+"		 "
		+ "				AND    TC.EFF_START_DATE<="+appDate+" 			 "
		
		+ "				AND TDL.TYPE = 'CHANNEL') TB							 "
		+ "WHERE TA.CHANNEL_ID = TCE.CHANNEL_ID_A AND TB.CHANNEL_ID = TCE.CHANNEL_ID_B			";

		String ChASql = "";
		String ChBSql = "";

		if (selectedList != null) {
			if (selectedList.size() > 0) {
				ChASql += "   AND TCE.CHANNEL_ID_A IN (";
				ChBSql += "   AND TCE.CHANNEL_ID_B IN (";
				for (int i = 0; i < selectedList.size(); i++) {
					ChASql += selectedList.get(i) + ",";
					ChBSql += selectedList.get(i) + ",";
				}

				ChASql += ") ";
				ChBSql += ") ";
				ChASql = ChASql.replace(",)", ")");// replace the last comma
				ChBSql = ChBSql.replace(",)", ")");// replace the last comma
			} else {
				ChASql = "";
				ChBSql = "";
			}

		}
		
		SQL = SQL + ChASql + ChBSql;
		
		ParameterizedRowMapper<ChannelUI> mapper = new ParameterizedRowMapper<ChannelUI>() {
			public ChannelUI mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				ChannelUI ExclusiveChannel = new ChannelUI();
				ExclusiveChannel.setChannelID(rs.getString("CHANNEL_ID_A"));
				ExclusiveChannel.setChannelID_B(rs.getString("CHANNEL_ID_B"));
				ExclusiveChannel.setChannelCD(rs.getString("CHANNEL_CD_A"));
				ExclusiveChannel.setChannelCD_B(rs.getString("CHANNEL_CD_B"));
				ExclusiveChannel.setChannelDesc(rs.getString("CH_DESC_A"));
				ExclusiveChannel.setChannelDesc_B(rs.getString("CH_DESC_B"));

				return ExclusiveChannel;
			}
		};

		try {
			logger.debug("getExclusiveList @ NowTVDAO: " + SQL);
			ExclusiveList = simpleJdbcTemplate.query(SQL, mapper, locale,locale);
		} catch (EmptyResultDataAccessException erdae) {
			logger.debug("EmptyResultDataAccessException");
			ExclusiveList = null;
		} catch (Exception e) {
			logger.debug("Exception caught in getExclusiveList():", e);
			throw new DAOException(e.getMessage(), e);
		}
		
		for(int i = 0; i < ExclusiveList.size(); ++i)
		{
			ExclusiveList.get(i).setChannelDesc(ExclusiveList.get(i).getChannelDesc().replace("<br/>", (char)10+""));
			ExclusiveList.get(i).setChannelDesc_B(ExclusiveList.get(i).getChannelDesc_B().replace("<br/>", (char)10+""));
			
			ExclusiveList.get(i).setChannelDesc(ExclusiveList.get(i).getChannelDesc().replace("&nbsp;", ""));
			ExclusiveList.get(i).setChannelDesc_B(ExclusiveList.get(i).getChannelDesc_B().replace("&nbsp;", ""));
		}
		return ExclusiveList;
	}

	
	public List<NowTvList> getNowTvListCodeMapping() throws DAOException{
		
		String SQL = "select code, description from w_code_lkup where grp_id = 'SB_IMS_NTV_LIST' ";
		
		List<NowTvList> mapping = null;
		
		ParameterizedRowMapper<NowTvList> mapper = new ParameterizedRowMapper<NowTvList>() {
			public NowTvList mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				NowTvList list = new NowTvList();
				list.listCode = rs.getString("code");
				list.listDescription = rs.getString("description");
				return list;
			}
		};
		
		try{
			logger.debug("getNowTvListCodeMapping @ NowTVDAO: " + SQL);
			mapping = simpleJdbcTemplate.query(SQL, mapper);
		}catch (EmptyResultDataAccessException erdae) {
			logger.debug("EmptyResultDataAccessException");
			mapping = null;
		}catch(Exception ex){
			logger.debug("Exception caught in getNowTvListCodeMapping():", ex);
			throw new DAOException(ex.getMessage(), ex);
		}

		return mapping;
	}
	
	public List<ImsDecodeTypeUI> getDecodeType()
			throws DAOException {
		List<ImsDecodeTypeUI> decodeTypeList = new ArrayList<ImsDecodeTypeUI>();


		String SQL =

		"select code, description  " +
		"from w_code_lkup  " +
		"where grp_id='SB_IMS_DECODER'";

		ParameterizedRowMapper<ImsDecodeTypeUI> mapper = new ParameterizedRowMapper<ImsDecodeTypeUI>() {
			public ImsDecodeTypeUI mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				ImsDecodeTypeUI imsDecodeType = new ImsDecodeTypeUI();
				imsDecodeType.setCode(rs.getString("code"));
				imsDecodeType.setDescription(rs.getString("description"));

				return imsDecodeType;
			}
		};

		try {
			logger.debug("getDecodeType @ NowTVDAO: " + SQL);
			decodeTypeList = simpleJdbcTemplate.query(SQL, mapper);
		} catch (EmptyResultDataAccessException erdae) {
			logger.debug("EmptyResultDataAccessException");
			decodeTypeList = null;
		} catch (Exception e) {
			logger.debug("Exception caught in getNowTVHDList():", e);
			throw new DAOException(e.getMessage(), e);
		}
		return decodeTypeList;
	}
	public String getDecoderType(String parmStr)throws DAOException  {
		List<String> decoderType = new ArrayList<String>();
			
		String SQL =
			"select description  " +
			"from w_code_lkup  " +
			"where grp_id='SB_IMS_DECODER_DESC' and code = '"+parmStr+"'";
		
		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
			public String mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				String aa = rs.getString("description");

				return aa;
			}
		};
		
		decoderType  = simpleJdbcTemplate.query(SQL, mapper);		
		
		return (decoderType==null || decoderType.size()==0) ? "":decoderType.get(0);
	}
	public List<String> getPTTVList(){
	List<String> PTTVList = new ArrayList<String>();
	
	String SQL =
	"select code from w_code_lkup where GRP_ID = 'SBIMS_PT_TV_LIST'";
	
	ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
		public String mapRow(ResultSet rs, int rowNum)
				throws SQLException {
			String aa = rs.getString("code");

			return aa;
		}
	};
	
	PTTVList  = simpleJdbcTemplate.query(SQL, mapper);
	
	
	return PTTVList;
	}
	
	
	
	
	public Map<String, List> getNtvBaskets(String i_order_id, String i_system, String i_housing_type, String i_customer_type, 
			String i_quality_constraint, String i_contract_period, String i_locale, String i_app_date, String i_is_like_offer,String i_is_nowone_offer) throws SQLException{
		
		Map<String, List> rtnMap = new HashMap<String, List>();
		
		try{
			SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
			.withSchemaName("ops$cnm")  
			.withCatalogName("pkg_sb_ims_ntv_baskets")  
			.withProcedureName("get_ntv_baskets_nowone")
			.declareParameters( 
					new SqlParameter("i_order_id", Types.VARCHAR),
					new SqlParameter("i_system", Types.VARCHAR),
					new SqlParameter("i_housing_type", Types.VARCHAR),
					new SqlParameter("i_customer_type", Types.VARCHAR),
					new SqlParameter("i_quality_constraint", Types.VARCHAR),
					new SqlParameter("i_contract_period", Types.VARCHAR),
					new SqlParameter("i_locale", Types.VARCHAR),
					new SqlParameter("i_app_date", Types.VARCHAR),
					new SqlParameter("i_is_like_offer", Types.VARCHAR),
					new SqlParameter("i_is_nowone_offer", Types.VARCHAR),
					new SqlOutParameter("o_campaign_cur", OracleTypes.CURSOR, new CursorMapper()),
					new SqlOutParameter("o_pack_cur", OracleTypes.CURSOR, new CursorMapper()),
					new SqlOutParameter("o_channel_icon_cur", OracleTypes.CURSOR, new CursorMapper()),
					new SqlOutParameter("o_paymant_cur", OracleTypes.CURSOR, new CursorMapper()),
					new SqlOutParameter("o_campaign_pack_code_cur", OracleTypes.CURSOR, new CursorMapper()),
					new SqlOutParameter("o_opt_vas_cur", OracleTypes.CURSOR, new CursorMapper()),
					new SqlOutParameter("gnretval", Types.INTEGER), 
					new SqlOutParameter("gnerrcode", Types.INTEGER),
					new SqlOutParameter("gserrtext", Types.VARCHAR)
					);
			
			logger.debug("************************ get_ntv_baskets_2 ************************");
//			logger.debug("i_order_id " + i_order_id);
//			logger.debug("i_system " + i_system);
//			logger.debug("i_housing_type " + i_housing_type);
//			logger.debug("i_customer_type " + i_customer_type);
//			logger.debug("i_quality_constraint " + i_quality_constraint);
//			logger.debug("i_contract_period " + i_contract_period);
//			logger.debug("i_locale " + i_locale);
//			logger.debug("i_app_date " + i_app_date);			
//			logger.debug("i_is_like_offer " + i_is_like_offer);
//			logger.debug("i_is_nowone_offer " + i_is_nowone_offer);
			
			
			MapSqlParameterSource in = new MapSqlParameterSource();
			in.addValue("i_order_id",  i_order_id);
			in.addValue("i_system",  i_system);
			in.addValue("i_housing_type",  i_housing_type);
			in.addValue("i_customer_type",  i_customer_type);
			in.addValue("i_quality_constraint",  i_quality_constraint);
			in.addValue("i_contract_period",  i_contract_period);
			in.addValue("i_locale",  i_locale);
			in.addValue("i_app_date",  i_app_date);			
			in.addValue("i_is_like_offer",  i_is_like_offer);
			in.addValue("i_is_nowone_offer",  i_is_nowone_offer);

			logger.info("get_ntv_baskets in:" + new Gson().toJson(in));
			Map out = jdbcCall.execute(in);
			
			logger.debug("get_ntv_baskets out:" + new Gson().toJson(out));
			logger.debug("************************ get_ntv_baskets_2 (end)************************");
			
			rtnMap.put("o_campaign_cur", (List<Map<String, String>>) out.get("o_campaign_cur"));
			rtnMap.put("o_pack_cur", (List<Map<String, String>>) out.get("o_pack_cur"));
			rtnMap.put("o_channel_icon_cur", (List<Map<String, String>>) out.get("o_channel_icon_cur"));
			rtnMap.put("o_paymant_cur", (List<Map<String, String>>) out.get("o_paymant_cur"));
			rtnMap.put("o_campaign_pack_code_cur", (List<Map<String, String>>) out.get("o_campaign_pack_code_cur"));
			rtnMap.put("o_opt_vas_cur", (List<Map<String, String>>) out.get("o_opt_vas_cur"));
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			
		}
		
		return rtnMap;
	}
	
	public final class CursorMapper implements RowMapper {
	    
	    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
	    	Map<String, String> map = new HashMap<String, String>();
	    	for(int i =1;i<=rs.getMetaData().getColumnCount();i++){
	    		map.put(rs.getMetaData().getColumnName(i), rs.getString(i));
	    	}
	        return map;
	    }
	}
	
	public void saveCPQTransaction(CPQNTVInfo cpqNTVInfo,String txn_id, String createBy) throws SQLException{
		String deleteSQL = "DELETE w_cpq_txn WHERE txn_id = ? ";
		
		try {			
			simpleJdbcTemplate.update(deleteSQL, txn_id);
			if(cpqNTVInfo != null){
				if(cpqNTVInfo.campaigns != null && cpqNTVInfo.campaigns.length>0)
					for(int i=0;i<cpqNTVInfo.campaigns.length;i++){
						if (cpqNTVInfo.campaigns[i] != null) {
							if(cpqNTVInfo.campaigns[i].pack_id != null && cpqNTVInfo.campaigns[i].pack_id.length>0)
								for(int j=0;j<cpqNTVInfo.campaigns[i].pack_id.length;j++)								
									this.genInsertSQL(txn_id, cpqNTVInfo.campaigns[i].campaign_id, cpqNTVInfo.campaigns[i].pack_id[j], null, null, null, createBy, createBy, null);
							if(cpqNTVInfo.campaigns[i].gift_id != null && cpqNTVInfo.campaigns[i].gift_id.length>0)
								for(int j=0;j<cpqNTVInfo.campaigns[i].gift_id.length;j++)
									this.genInsertSQL(txn_id, cpqNTVInfo.campaigns[i].campaign_id, null, cpqNTVInfo.campaigns[i].gift_id[j], null, null, createBy, createBy, null);
							if(cpqNTVInfo.campaigns[i].channel_gift_id != null && cpqNTVInfo.campaigns[i].channel_gift_id.length>0)
								for(int j=0;j<cpqNTVInfo.campaigns[i].channel_gift_id.length;j++)
									this.genInsertSQL(txn_id, cpqNTVInfo.campaigns[i].campaign_id, null, null, cpqNTVInfo.campaigns[i].channel_gift_id[j], null, createBy, createBy, null);
						}
					}
				if(cpqNTVInfo.vas != null && cpqNTVInfo.vas.length>0)
					for(int i=0;i<cpqNTVInfo.vas.length;i++)
						this.genInsertSQL(txn_id, null, null, null, null, cpqNTVInfo.vas[i], createBy,createBy, null);
			}
		} catch(Exception e){
			logger.error("Exception caught in saveCPQTransaction()", e);
			e.printStackTrace();
		}
		finally{
			
		}
		
	}

	public void saveCPQTransactionforAmendRet(CPQNTVRetInfo cpqNTVRetInfo,String txn_id,String amendSeq) throws SQLException{
		String deleteSQL = "DELETE w_cpq_txn_amend WHERE txn_id = ? and amend_seq = ?";
		
		try {			
			simpleJdbcTemplate.update(deleteSQL, txn_id, amendSeq);
		    CPQNTVInfo in=new CPQNTVInfo();
		    CPQNTVInfo keep=new CPQNTVInfo();
		    CPQNTVInfo out=new CPQNTVInfo();
			if(cpqNTVRetInfo != null){
				in=cpqNTVRetInfo.IN;
				keep=cpqNTVRetInfo.KEEP;
				out=cpqNTVRetInfo.OUT;
				if(in != null){insertCpqTxnNowRetAmend(in, txn_id, amendSeq,"I");}
				if(keep != null){insertCpqTxnNowRetAmend(keep, txn_id, amendSeq," ");}
				if(out != null){insertCpqTxnNowRetAmend(out, txn_id, amendSeq,"O");}
			}
		} catch(Exception e){
			logger.error("Exception caught in saveCPQTransactionforAmend()", e);
			e.printStackTrace();
		}
		
	}
	public void insertCpqTxnNowRetAmend(CPQNTVInfo cpqNTVInfo,String txn_id, String amendSeq, String io_ind) {
		if(cpqNTVInfo.campaigns != null && cpqNTVInfo.campaigns.length>0)
			for(int i=0;i<cpqNTVInfo.campaigns.length;i++){
				if (cpqNTVInfo.campaigns[i] != null) {
					if(cpqNTVInfo.campaigns[i].pack_id != null && cpqNTVInfo.campaigns[i].pack_id.length>0)
						for(int j=0;j<cpqNTVInfo.campaigns[i].pack_id.length;j++)								
							this.genInsertSQLforAmend(txn_id, cpqNTVInfo.campaigns[i].campaign_id, cpqNTVInfo.campaigns[i].pack_id[j], null, null, null, "SBIMS", "SBIMS", amendSeq,io_ind);
					if(cpqNTVInfo.campaigns[i].gift_id != null && cpqNTVInfo.campaigns[i].gift_id.length>0)
						for(int j=0;j<cpqNTVInfo.campaigns[i].gift_id.length;j++)
							this.genInsertSQLforAmend(txn_id, cpqNTVInfo.campaigns[i].campaign_id, null, cpqNTVInfo.campaigns[i].gift_id[j], null, null, "SBIMS", "SBIMS", amendSeq,io_ind);
					if(cpqNTVInfo.campaigns[i].channel_gift_id != null && cpqNTVInfo.campaigns[i].channel_gift_id.length>0)
						for(int j=0;j<cpqNTVInfo.campaigns[i].channel_gift_id.length;j++)
							this.genInsertSQLforAmend(txn_id, cpqNTVInfo.campaigns[i].campaign_id, null, null, cpqNTVInfo.campaigns[i].channel_gift_id[j], null, "SBIMS", "SBIMS", amendSeq,io_ind);
				}
			}
		if(cpqNTVInfo.vas != null && cpqNTVInfo.vas.length>0)
			for(int i=0;i<cpqNTVInfo.vas.length;i++)
				this.genInsertSQLforAmend(txn_id, null, null, null, null, cpqNTVInfo.vas[i], "SBIMS", "SBIMS", amendSeq,io_ind);
	}
	public void saveCPQTransactionRet(CPQNTVRetInfo cpqNTVRetInfo,String txn_id,String createBy) throws SQLException{//not using? by steven 20160718
		String deleteSQL = "DELETE w_cpq_txn WHERE txn_id = ? ";
		
		try {			
			simpleJdbcTemplate.update(deleteSQL, txn_id);
		    CPQNTVInfo in=new CPQNTVInfo();
		    CPQNTVInfo keep=new CPQNTVInfo();
		    CPQNTVInfo out=new CPQNTVInfo();
			if(cpqNTVRetInfo != null){
				in=cpqNTVRetInfo.IN;
				keep=cpqNTVRetInfo.KEEP;
				out=cpqNTVRetInfo.OUT;
				if(in != null){insertCpqTxnNowRet(in, txn_id,"I",createBy);}
				if(keep != null){insertCpqTxnNowRet(keep, txn_id," ",createBy);}
				if(out != null){insertCpqTxnNowRet(out, txn_id,"O",createBy);}
			}
		} catch(Exception e){
			logger.error("Exception caught in saveCPQTransactionRet()", e);
			e.printStackTrace();
		}		
	}
	public void insertCpqTxnNowRet(CPQNTVInfo cpqNTVRetInfo, String txn_id, String io_ind, String createBy) {
		if(cpqNTVRetInfo.campaigns != null && cpqNTVRetInfo.campaigns.length>0)
			for(int i=0;i<cpqNTVRetInfo.campaigns.length;i++){
				if (cpqNTVRetInfo.campaigns[i] != null) {
					if(cpqNTVRetInfo.campaigns[i].pack_id != null && cpqNTVRetInfo.campaigns[i].pack_id.length>0)
						for(int j=0;j<cpqNTVRetInfo.campaigns[i].pack_id.length;j++)								
							this.genInsertSQL(txn_id, cpqNTVRetInfo.campaigns[i].campaign_id, cpqNTVRetInfo.campaigns[i].pack_id[j], null, null, null, createBy, createBy,io_ind);
					if(cpqNTVRetInfo.campaigns[i].gift_id != null && cpqNTVRetInfo.campaigns[i].gift_id.length>0)
						for(int j=0;j<cpqNTVRetInfo.campaigns[i].gift_id.length;j++)
							this.genInsertSQL(txn_id, cpqNTVRetInfo.campaigns[i].campaign_id, null, cpqNTVRetInfo.campaigns[i].gift_id[j], null, null, createBy, createBy,io_ind);
					if(cpqNTVRetInfo.campaigns[i].channel_gift_id != null && cpqNTVRetInfo.campaigns[i].channel_gift_id.length>0)
						for(int j=0;j<cpqNTVRetInfo.campaigns[i].channel_gift_id.length;j++)
							this.genInsertSQL(txn_id, cpqNTVRetInfo.campaigns[i].campaign_id, null, null, cpqNTVRetInfo.campaigns[i].channel_gift_id[j], null, createBy, createBy,io_ind);
				}
			}
		if(cpqNTVRetInfo.vas != null && cpqNTVRetInfo.vas.length>0)
			for(int i=0;i<cpqNTVRetInfo.vas.length;i++)
				this.genInsertSQL(txn_id, null, null, null, null, cpqNTVRetInfo.vas[i], createBy, createBy,io_ind );
	}
	
	private void genInsertSQL(String TXN_ID,String  CAMPAIGN_CD,String  PACK_CD,String  GIFT_CD,String  CHANNEL_GIFT_CD,String  VAS_CD,
			String  CREATE_BY, String  LAST_UPD_BY, String ink_ind){
		String insertSQL = "Insert into w_cpq_txn (" +
		" TXN_ID, " +
		" CAMPAIGN_CD, " +
		" PACK_CD, " +
		" GIFT_CD, " +								
		" CHANNEL_GIFT_CD, " +
		" VAS_CD, " +
		" CREATE_BY, " +
		" CREATE_DATE, " +
		" LAST_UPD_BY," +								
		" LAST_UPD_DATE," +
		" io_ind ) " +
		" Values ( "+Long.parseLong(TXN_ID)+" ,?,?,?,?,?,?,sysdate,?,sysdate, ?) "; 
		
//		logger.info("Insert w_cpq_txn");
		
		try{
			
			simpleJdbcTemplate.update(insertSQL,
					CAMPAIGN_CD, PACK_CD, GIFT_CD, CHANNEL_GIFT_CD, VAS_CD, CREATE_BY, LAST_UPD_BY , ink_ind
					);

		}catch(Exception e){
			logger.error("Exception caught in Insert w_cpq_txn(): "+insertSQL, e);
			e.printStackTrace();
		}	
	}
	public void saveCPQTransactionforAmend(CPQNTVInfo cpqNTVInfo,String txn_id,String amendSeq) throws SQLException{
		String deleteSQL = "DELETE w_cpq_txn_amend WHERE txn_id = ? and amend_seq = ?";
		
		try {			
			simpleJdbcTemplate.update(deleteSQL, txn_id, amendSeq);
			if(cpqNTVInfo != null){
				if(cpqNTVInfo.campaigns != null && cpqNTVInfo.campaigns.length>0)
					for(int i=0;i<cpqNTVInfo.campaigns.length;i++){
						if (cpqNTVInfo.campaigns[i] != null) {
							if(cpqNTVInfo.campaigns[i].pack_id != null && cpqNTVInfo.campaigns[i].pack_id.length>0)
								for(int j=0;j<cpqNTVInfo.campaigns[i].pack_id.length;j++)								
									this.genInsertSQLforAmend(txn_id, cpqNTVInfo.campaigns[i].campaign_id, cpqNTVInfo.campaigns[i].pack_id[j], null, null, null, "SBIMS", "SBIMS", amendSeq,null);
							if(cpqNTVInfo.campaigns[i].gift_id != null && cpqNTVInfo.campaigns[i].gift_id.length>0)
								for(int j=0;j<cpqNTVInfo.campaigns[i].gift_id.length;j++)
									this.genInsertSQLforAmend(txn_id, cpqNTVInfo.campaigns[i].campaign_id, null, cpqNTVInfo.campaigns[i].gift_id[j], null, null, "SBIMS", "SBIMS", amendSeq,null);
							if(cpqNTVInfo.campaigns[i].channel_gift_id != null && cpqNTVInfo.campaigns[i].channel_gift_id.length>0)
								for(int j=0;j<cpqNTVInfo.campaigns[i].channel_gift_id.length;j++)
									this.genInsertSQLforAmend(txn_id, cpqNTVInfo.campaigns[i].campaign_id, null, null, cpqNTVInfo.campaigns[i].channel_gift_id[j], null, "SBIMS", "SBIMS", amendSeq,null);
						}
					}
				if(cpqNTVInfo.vas != null && cpqNTVInfo.vas.length>0)
					for(int i=0;i<cpqNTVInfo.vas.length;i++)
						this.genInsertSQLforAmend(txn_id, null, null, null, null, cpqNTVInfo.vas[i], "SBIMS", "SBIMS", amendSeq,null);
			}
		} catch(Exception e){
			logger.error("Exception caught in saveCPQTransactionforAmend()", e);
			e.printStackTrace();
		}
		finally{
			
		}
		
	}
	public void genInsertSQLforAmend(String TXN_ID,String  CAMPAIGN_CD,String  PACK_CD,String  GIFT_CD,String  CHANNEL_GIFT_CD,String  VAS_CD,
			String  CREATE_BY, String  LAST_UPD_BY, String amendSeq, String io_ind){
		String insertSQL = "Insert into w_cpq_txn_amend (" +
		" TXN_ID, " +
		" AMEND_SEQ, " +
		" CAMPAIGN_CD, " +
		" PACK_CD, " +
		" GIFT_CD, " +								
		" CHANNEL_GIFT_CD, " +
		" VAS_CD, " +
		" CREATE_BY, " +
		" CREATE_DATE, " +
		" LAST_UPD_BY," +								
		" LAST_UPD_DATE," +
		" io_ind ) " +
		" Values ( "+Long.parseLong(TXN_ID)+" ,?" +
				",?,?,?,?,?,?,sysdate,?,sysdate,? ) "; 
		
		logger.info("Insert w_cpq_txn_amend");
		
		try{
			
			simpleJdbcTemplate.update(insertSQL,
					amendSeq, CAMPAIGN_CD, PACK_CD, GIFT_CD, CHANNEL_GIFT_CD, VAS_CD, CREATE_BY, LAST_UPD_BY ,io_ind
					);

		}catch(Exception e){
			logger.error("Exception caught in Insert w_cpq_txn_amend(): "+insertSQL, e);
			e.printStackTrace();
		}		
	}
	public void insertSubItemAmd(String orderId,String  itemId,String  itemType,String  offerCd,String  offerId,String  prodCd,
			String  prodId, String amendSeq, String ioInd, String createBy){//steven added for nowRet 20160717
		String insertSQL = "INSERT INTO BOMWEB_SUB_ITEM_AMD (ORDER_ID,                                 ID," +
				"                                 TYPE,                                 CREATE_BY," +
				"                                 AMEND_SEQ,                                 IO_IND," +
				"                                 OFFER_CD,                                 CAMPAIGN_CD," +
				"                                 PACK_CD,                                 TOP_UP," +
				"            create_date)" +
				"  VALUES   (?,            ?," +
				"            ?,            ?," +
				"            ?,            ?," +
				"            ?,            ?," +
				"            ?,            ?," +
				"            sysdate) "; 

		Map<String, Object> logMap = new HashMap<String, Object>();
		logMap.put("ORDER_ID", orderId);
		logMap.put("itemId", itemId);
		logMap.put("itemType", itemType);
		logMap.put("offerCd", offerCd);
		logMap.put("offerId", offerId);
		logMap.put("prodCd", prodCd);
		logMap.put("prodId", prodId);
		logMap.put("amendSeq", amendSeq);
		logMap.put("ioInd", ioInd);
		logMap.put("createBy", createBy);		
		logger.info(" insert BOMWEB_SUB_ITEM_AMD:"+gson.toJson(logMap));		
		try{			
			simpleJdbcTemplate.update(insertSQL,
					orderId, itemId, 
					itemType, createBy, 
					amendSeq, ioInd, 
					offerCd, offerId ,
					prodCd,prodId
					);

		}catch(Exception e){
			logger.error("Exception caught in Insert insertSubItemAmd(): "+insertSQL, e);
			e.printStackTrace();
		}		
	}
	public void deleteSubItemAmd(String orderId, String amendSeq){//steven added for nowRet 20160722
		logger.info(" insert deleteSubItemAmd:"+orderId +" amendSeq:"+amendSeq);	
		String updateSQL="";
		try{
			 updateSQL = "delete BOMWEB_SUB_ITEM_AMD where ORDER_ID = ? and AMEND_SEQ=? ";
			simpleJdbcTemplate.update(updateSQL,orderId,amendSeq);

		}catch(Exception e){
			logger.error("Exception caught in Insert deleteSubItemAmd(): "+updateSQL, e);
			e.printStackTrace();
		}		
	}


	public UserType2[] getCPQUserList(String action) throws SQLException{
		List<UserType2> users = new ArrayList<UserType2>();


		String SQL = "select * from BOMWEB_CPQ_USER where action = LOWER('"+action+"') ";
		

		ParameterizedRowMapper<UserType2> mapper = new ParameterizedRowMapper<UserType2>() {
			public UserType2 mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				UserType2 user = new UserType2();		
				
				user.setCompany_name(rs.getString("company_name"));
				user.setLogin(rs.getString("login"));
				user.setPassword(rs.getString("password"));
				user.setType(rs.getString("type"));
				user.setFirst_name(rs.getString("first_name"));
				user.setLast_name(rs.getString("last_name"));
				user.setEmail(rs.getString("email"));
				user.setPhone(rs.getString("phone"));
		//m		users[i].setSeparate_ship_addr(new BooleanOrBlank(true));
				user.setNotification_pref(new NotificationPreferenceType(false,false));
				user.setBm_language(rs.getString("bm_language"));
				user.setCurrency_preference(rs.getString("currency_preference"));
				user.setBm_units(rs.getString("bm_units"));
				user.setDate_format(rs.getString("date_format"));
				user.setTime_zone(rs.getString("time_zone"));
				user.setNumber_format(rs.getString("number_format"));
				user.setStatus(rs.getString("status"));
				if(rs.getString("Super_user_access_perm")!=null)user.setSuper_user_access_perm(rs.getString("Super_user_access_perm").equalsIgnoreCase("FALSE")?false:true);
				user.setEnabled_for_sso(rs.getInt("Enabled_for_sso"));
				user.setExternal_sso_id(rs.getString("external_sso_id"));
				user.setMobile_enabled(rs.getInt("Mobile_enabled"));
				user.setWeb_services_only(rs.getInt("Web_services_only"));
				user.setPartner_login(rs.getString("partner_login"));
				user.setPartner_password(rs.getString("partner_password"));
				
				String[] tmpStr;
				if(StringUtils.isNotBlank(rs.getString("group_list"))){
					if( rs.getString("group_list").contains("~")){
						tmpStr = rs.getString("group_list").split("~");
					}	
					else {
						tmpStr = new String[1];
						tmpStr[0] = rs.getString("group_list");					
					}		
					UserType2Group_listGroup[] group =  new UserType2Group_listGroup[tmpStr.length];
					for(int i= 0; i<tmpStr.length ; i++)
						group[i]=new UserType2Group_listGroup(tmpStr[i]);
					
					user.setGroup_list(new UserType2Group_list(group));
				}	
				
				return user;
			}
		};

		try {
			logger.debug("getCPQUserList @ NowTVDAO: " + SQL);
			users = simpleJdbcTemplate.query(SQL, mapper);
//			logger.info("getCPQUserList: "+gson.toJson(users));
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException");
//			users = null;
		} catch (Exception e) {
			logger.info("Exception caught in getCPQUserList():", e);
			e.printStackTrace();
			throw new SQLException(e.getMessage(), e);
		}
		logger.info("getCPQUserList: "+gson.toJson(users));
		UserType2[] rtnList =  new UserType2[users.size()];
		for(int i= 0; i<users.size() ; i++)
			rtnList[i]=users.get(i);		
		
//		if(!users.isEmpty())
//			this.updateCPQUser(action);
			
		return rtnList;
	}
	
	public void updateCPQUser(String action) throws SQLException{
		String updateSQL = "update BOMWEB_CPQ_USER set action = '' , last_upd_by = 'SBIMS',last_upd_date = sysdate where action = LOWER('"+action+"') ";
		logger.info("updateCPQUser");
		
		try{
			
			simpleJdbcTemplate.update(updateSQL);

		}catch(Exception e){
			logger.error("Exception caught in updateCPQUser(): "+updateSQL, e);
			e.printStackTrace();
		}	
	}
	
	public EachRecordType[] getCPQUserTeamList(String action) throws SQLException{
		List<EachRecordType> record = new ArrayList<EachRecordType>();

		String SQL = "select * from BOMWEB_CPQ_USER_TEAM where action = LOWER('"+action+"') ";
		
		ParameterizedRowMapper<EachRecordType> mapper = new ParameterizedRowMapper<EachRecordType>() {
			public EachRecordType mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				EachRecordType record = new EachRecordType();		
				
				record.setId(rs.getLong("Id"));
				record.setLogin(rs.getString("Login"));
				record.setSales_channel(rs.getString("Sales_channel"));
				record.setSales_team(rs.getString("Sales_team"));
				record.setUser_group(rs.getString("User_group"));
						
				return record;
			}
		};

		try {
			logger.debug("getCPQUserTeamList @ NowTVDAO: " + SQL);
			record = simpleJdbcTemplate.query(SQL, mapper);
//			logger.info("getCPQUserList: "+gson.toJson(users));
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException");
//			users = null;
		} catch (Exception e) {
			logger.info("Exception caught in getCPQUserTeamList():", e);
			e.printStackTrace();
			throw new SQLException(e.getMessage(), e);
		}
		
		logger.info("getCPQUserTeamList: "+gson.toJson(record));
		EachRecordType[] rtnList =  new EachRecordType[record.size()];
		for(int i= 0; i<record.size() ; i++)
			rtnList[i]=record.get(i);		
			
		return rtnList;
	}
	
	public void updateCPQUserTeam(String action) throws SQLException{
		String updateSQL;
		if(action.equalsIgnoreCase("delete"))
			updateSQL = "delete BOMWEB_CPQ_USER_TEAM where action = 'delete' ";
		else
			updateSQL = "update BOMWEB_CPQ_USER_TEAM set action = '' , last_upd_by = 'SBIMS',last_upd_date = sysdate where action = LOWER('"+action+"') ";
		
		try{			
			simpleJdbcTemplate.update(updateSQL);
		}catch(Exception e){
			logger.error("Exception caught in updateCPQUserTeam(): "+updateSQL, e);
			e.printStackTrace();
		}	
	}

	public List<String> getPremierComboCampaignCode(){
	List<String> campaignList = new ArrayList<String>();
	
	String SQL =
	"select code from w_code_lkup where GRP_ID = 'IMS_NOWTV_PREMIER_COMBO'";
	
	ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
		public String mapRow(ResultSet rs, int rowNum)
				throws SQLException {
			String aa = rs.getString("code");

			return aa;
		}
	};
	
	campaignList  = simpleJdbcTemplate.query(SQL, mapper);
	
	return campaignList;
	}
	
	
}


