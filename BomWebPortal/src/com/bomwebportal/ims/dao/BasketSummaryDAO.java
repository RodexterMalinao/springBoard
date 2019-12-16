package com.bomwebportal.ims.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;

import com.bomwebportal.dao.BaseDAO;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.ims.dto.ui.BasketSumUI;
import com.bomwebportal.ims.dto.ui.ChannelSumUI;
import com.bomwebportal.ims.dto.ui.ImsSummaryUI;

public class BasketSummaryDAO extends BaseDAO{
	protected final Log logger = LogFactory.getLog(getClass());
	
	public List<ImsSummaryUI> basketTitleDetail(String basketId, String locale) throws DAOException{
		logger.info("basketTitleDetail is called");
		
		try{
			SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate);
			String SQL = "SELECT BDL.BASKET_ID,  " +
						 " BDL.HTML BASKET_TITLE,  " +
						 " SUM.BASKET_DETAIL BASKET_DETAIL,  " +
						 " BDL.IMAGE_PATH IMAGE_PATH,  " +
						 " MFIDL.HTML MTHFIX,  " +
						 " MTMIDL.HTML MTH2MTH,  " +
						 " IP.RECURRENT_AMT RECURRENT_AMT,  " +
						 " IP.MTH_TO_MTH_RATE MTH_TO_MTH_RATE,  " +
						 " IDRV.CONTRACT_PERIOD CONTRACT_PERIOD  " +
						 " FROM W_BASKET_DISPLAY_LKUP BDL,  " +
						 " W_DIC_BASKET_ITEM_ASSGN BIA,  " +
						 " W_ITEM I,  " +
						 " W_ITEM_PRICING IP,  " +
						 " W_ITEM_DTL_RP_RB_VAS IDRV,  " +
						 " W_ITEM_DISPLAY_LKUP MFIDL,  " +
						 " W_ITEM_DISPLAY_LKUP MTMIDL,	  " +
						 " (SELECT SBDL.BASKET_ID,  " +
						 " SBDL.HTML BASKET_DETAIL  " +
						 " FROM W_BASKET_DISPLAY_LKUP SBDL  " +
						 " WHERE SBDL.DISPLAY_TYPE = 'SUMMARY') SUM  " +
						 " WHERE  BDL.BASKET_ID = ?  " +
						 " AND BDL.BASKET_ID = SUM.BASKET_ID  " +
						 " AND BDL.BASKET_ID = BIA.BASKET_ID  " +
						 " AND BIA.ITEM_ID = I.ID  " +
						 " AND BIA.ITEM_ID = IP.ID  " +
						 " AND BIA.ITEM_ID = IDRV.ID  " +
						 " AND I.ID = MFIDL.ITEM_ID(+)  " +
						 " AND I.ID = MTMIDL.ITEM_ID(+)  " +
						 " AND MFIDL.LOCALE(+) = ?  " +
						 " AND MFIDL.DISPLAY_TYPE = 'MTHFIX'  " +
						 " AND MTMIDL.LOCALE(+) = ?  " +
						 " AND MTMIDL.DISPLAY_TYPE = 'MTH2MTH'  " +
						 " AND I.TYPE = 'PROG'  " +
						 " AND BDL.LOCALE = ?  " +
						 " AND BDL.DISPLAY_TYPE ='TITLE'";
			
			
			ParameterizedRowMapper<ImsSummaryUI> mapper = new ParameterizedRowMapper<ImsSummaryUI>() {
				public ImsSummaryUI mapRow(ResultSet rs, int rowNum) throws SQLException {
					ImsSummaryUI summary = new ImsSummaryUI();
					summary.setBasketTitle(rs.getString("BASKET_TITLE"));
					summary.setBasketDetail(rs.getString("BASKET_DETAIL"));
					summary.setImagePath(rs.getString("IMAGE_PATH"));
					summary.setMthFix(rs.getString("MTHFIX"));
					summary.setMthToMth(rs.getString("MTH2MTH"));
					if(rs.getString("RECURRENT_AMT") == null){
						summary.setRecurrentAmt("N/A");
					}else{
						summary.setRecurrentAmt(rs.getString("RECURRENT_AMT"));
					}
					if(rs.getString("MTH_TO_MTH_RATE") == null){
						summary.setMthToMthRate("N/A");
					}else{
						summary.setMthToMthRate(rs.getString("MTH_TO_MTH_RATE"));
					}
					summary.setContractPeriod(rs.getString("CONTRACT_PERIOD"));
					
					return summary;
				}
			};
			logger.debug("basketTitleDetail @ BasketSummaryDAO: " + SQL);
			return simpleJdbcTemplate.query(SQL, mapper, basketId, locale, locale, locale);
			
		}catch (Exception e) {
				logger.error("Exception caught in basketTitleDetail()", e);
				throw new DAOException(e.getMessage(), e);
		}
	}
	
	public List<BasketSumUI> getBasketSumList(String itemIdStr, String locale) throws DAOException{
		logger.info("basketVASDetail is called");
		
		logger.info("itemIdStr:"+itemIdStr);
		logger.info("locale:"+locale);
		System.out.println(itemIdStr);
		
		List<BasketSumUI> BasketSumList = new ArrayList<BasketSumUI>();

		String SQL = "SELECT  " +
			 " I.ID ITEM_ID,  " +
			 " IDL.HTML ITEM_DETAIL,  " +
			 " IDL.HTML2 VAS_DES,  " +
			 " IDL.DISPLAY_TYPE VAS_TYPE,  " +
			 " I.TYPE ITEM_TYPE,  " +
			 " MFIDL.HTML MTHFIX,  " +
			 " MTMIDL.HTML MTH2MTH,  " +
			 " IP.RECURRENT_AMT,  " +
			 " IP.MTH_TO_MTH_RATE  " +
			 " FROM W_ITEM_DISPLAY_LKUP IDL,  " +
			 " W_ITEM_PRICING IP,  " +
			 " W_ITEM I,  " +
			 " W_ITEM_DISPLAY_LKUP MFIDL,  " +
			 " W_ITEM_DISPLAY_LKUP MTMIDL,  " +
			 " (SELECT VTIDL.ITEM_ID,  " +
			 " VTIDL.DISPLAY_TYPE VAS_TYPE,  " +
			 " VTCL.DESCRIPTION VT_SEQ  " +
			 " FROM	W_ITEM_DISPLAY_LKUP VTIDL,  " +
			 " W_CODE_LKUP VTCL  " +
			 " WHERE VTIDL.DISPLAY_TYPE = VTCL.CODE  " +
			 " AND VTCL.GRP_ID = 'IMS_VAS_TYPE') VT  " +
			 " WHERE IDL.ITEM_ID IN ('" +itemIdStr+"')  " +
			 " AND I.ID = IDL.ITEM_ID  " +
			 " AND I.ID = IP.ID  " +
			 " AND IDL.LOCALE = ?  " +
			 " AND I.ID = MFIDL.ITEM_ID(+)  " +
			 " AND I.ID = MTMIDL.ITEM_ID(+)  " +
			 " AND MFIDL.LOCALE(+) = ?   " + //--LOCALE--
			 " AND MFIDL.DISPLAY_TYPE = 'MTHFIX'  " +
			 " AND MTMIDL.LOCALE(+) = ?   " + //--LOCALE--
			 " AND MTMIDL.DISPLAY_TYPE = 'MTH2MTH'  " +
			 " AND IDL.DISPLAY_TYPE = 'DETAIL'  " +
			 " AND I.TYPE NOT IN('PROG','PRE-INST')  " +
			 " AND VT.ITEM_ID(+) = IDL.ITEM_ID  " +
			 " ORDER BY VT.VT_SEQ";
			
		ParameterizedRowMapper<BasketSumUI> mapper = new ParameterizedRowMapper<BasketSumUI>() {
			public BasketSumUI mapRow(ResultSet rs, int rowNum) throws SQLException {
		       	BasketSumUI basketSumDetails = new BasketSumUI();
		       	basketSumDetails.setItemId(rs.getString("ITEM_ID"));
		       	basketSumDetails.setItemDetail(rs.getString("ITEM_DETAIL"));
		       	basketSumDetails.setItemType(rs.getString("ITEM_TYPE"));
		       	basketSumDetails.setVasDesc(rs.getString("VAS_DES"));
		       	basketSumDetails.setVasType(rs.getString("VAS_TYPE"));
		       	basketSumDetails.setMthFix(rs.getString("MTHFIX"));
		        basketSumDetails.setMthToMth(rs.getString("MTH2MTH"));
		       	if(rs.getString("RECURRENT_AMT") == null){
		       		basketSumDetails.setRecurrentAmt("N/A");
		       	}else{
		       		basketSumDetails.setRecurrentAmt(rs.getString("recurrent_amt"));
		       	}
		       	if(rs.getString("MTH_TO_MTH_RATE") == null){
		       		basketSumDetails.setMthToMthRate("N/A");
		       	}else{
		       		basketSumDetails.setMthToMthRate(rs.getString("mth_to_mth_rate"));
		       	}
		       	return basketSumDetails;
		       }
		};
		     
		try {
			logger.debug("getBasketSumList @ BasketSummaryDAO: " + SQL);
		    BasketSumList = simpleJdbcTemplate.query(SQL, mapper, locale, locale, locale);
		} catch (EmptyResultDataAccessException erdae) {
			logger.debug("EmptyResultDataAccessException");
			BasketSumList = null;
		} catch (Exception e) {
			logger.debug("Exception caught in getBasketSumList():", e);
			throw new DAOException(e.getMessage(), e);
		}	
		return BasketSumList;
	}
	
	public List<ChannelSumUI> getChannelSumList(String channelIdStr, String locale) throws DAOException{
		logger.info("basketChannelDetail is called");
		
		logger.info("channelId:"+channelIdStr);
		logger.info("locale:"+locale);
		System.out.println(channelIdStr);
		
		List<ChannelSumUI> channelSumList = new ArrayList<ChannelSumUI>();
		
		String SQL = "SELECT  " +
		 " TC.CHANNEL_ID,  " +
		 " TC.CHANNEL_CD,  " +
		 " TC.DISPLAY_SEQ,TV_TYPE,  " +
		 " TDL.HTML  " +
//		 " TC.DISPLAY_SEQ  " +
		 " FROM W_TV_CHANNEL TC,  " +
		 " W_TV_DISPLAY_LKUP TDL  " +
		 " WHERE TC.CHANNEL_ID IN ('" +channelIdStr+"')  " +
		 " AND TC.CHANNEL_ID = TDL.ID  " +
		 " AND TDL.LOCALE = ?  " +
		 " ORDER BY TC.DISPLAY_SEQ";
		
		ParameterizedRowMapper<ChannelSumUI> mapper = new ParameterizedRowMapper<ChannelSumUI>() {
			public ChannelSumUI mapRow(ResultSet rs, int rowNum) throws SQLException {
		       	ChannelSumUI channelSumDetails = new ChannelSumUI();
		       	channelSumDetails.setChannelId(rs.getString("CHANNEL_ID"));
		       	channelSumDetails.setChannelCd(rs.getString("CHANNEL_CD"));
		       	channelSumDetails.setDisplaySeq(rs.getString("DISPLAY_SEQ"));
		       	channelSumDetails.setTvType(rs.getString("TV_TYPE"));
		       	channelSumDetails.setHtml(rs.getString("HTML"));
		       	
		       	return channelSumDetails;
		       }
		};
		
		try{
			logger.debug("getChannelSumList @ BasketSummaryDAO: " + SQL);
		    channelSumList = simpleJdbcTemplate.query(SQL, mapper, locale);
		} catch (EmptyResultDataAccessException erdae) {
			logger.debug("EmptyResultDataAccessException");
			channelSumList = null;
		} catch (Exception e) {
			logger.debug("Exception caught in getChannelSumList():", e);
			throw new DAOException(e.getMessage(), e);
		}
		return channelSumList;
	}
}
