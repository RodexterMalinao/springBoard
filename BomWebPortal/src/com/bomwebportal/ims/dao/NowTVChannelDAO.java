/*package com.bomwebportal.ims.dao;

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

public class NowTVChannelDAO extends BaseDAO{
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	public List<ChannelDTO> getNowTVChannelList (String locale, String channelId , String customerTier, String basketID)throws DAOException {
		List<ChannelDTO> NowTVChannelList = new ArrayList<ChannelDTO>();

		String SQL=

		
			"SELECT BIA.BASKET_ID, 											 "+
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
			"		IDI.BANDWIDTH,											 "+
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
			"AND    BDL.LOCALE(+) = ? 								 "+ //--LOCALE--
			"AND	BDL.DISPLAY_TYPE = 'TITLE'								 "+
			"AND    BIA.ITEM_ID = I.ID 										 "+
			"AND    BIA.ITEM_ID = IP.ID 									 "+
			"AND    BIA.ITEM_ID = IDL.ITEM_ID 								 "+
			"AND    BIA.ITEM_ID = IDRV.ID									 "+
			"AND    BIA.ITEM_ID = IOA.ITEM_ID								 "+
			"AND    BIA.ITEM_ID = IDI.ITEM_ID								 "+
			"AND	IOA.OFFER_TYPE IN ('S','V')								 "+
			"AND    IDL.LOCALE = ? 		  							 "+  //--LOCALE--
			"AND    I.TYPE IN ('PROG','PRE-INST')  				 "+ //--BASKETTYPE--
			"AND    CBA.CUSTOMER_TIER = ?   				 "+//--CUSTOMERTIER--
			"AND    CBA.CHANNEL_ID= '1'   			 			 "+//--CHANNEL_ID--
			"ORDER BY I.TYPE DESC			 ";

			
		
		ParameterizedRowMapper<ChannelDTO> mapper = new ParameterizedRowMapper<ChannelDTO>() {
	        public ChannelDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
	        	ChannelDTO ChannelDetails = new ChannelDTO();
	        	ChannelDetails.getChannelID(rs.getString("basket_id"));
	        	ChannelDetails.getChannelCD(rs.getString("TITLE"));
	        	ChannelDetails.getChannelDesc(rs.getString("SUMMARY"));
	            return ChannelDetails;
	        }
	    };

		try {
			logger.debug("getNowTVChannelList @ BasketDetailsDAO: " + SQL);
			NowTVChannelList = simpleJdbcTemplate.query(SQL, mapper,basketID,locale,locale,customerTier);
		} catch (EmptyResultDataAccessException erdae) {
			logger.debug("EmptyResultDataAccessException");
			NowTVChannelList = null;
		} catch (Exception e) {
			logger.debug("Exception caught in getBasketDetailsList():", e);
			throw new DAOException(e.getMessage(), e);
		}	
		return NowTVChannelList;
	}
}

*/