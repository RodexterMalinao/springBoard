package com.bomwebportal.ims.dao;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.bomwebportal.dao.*;
import com.bomwebportal.dto.*;
import com.bomwebportal.ims.dto.*;
import com.bomwebportal.ims.dto.ui.GiftUI;
import com.bomwebportal.ims.dto.ui.NowTVCampaignCdDTO;
import com.bomwebportal.ims.dto.ui.VASDetailUI;
import com.bomwebportal.exception.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GiftDetailDAO extends BaseDAO{
	
	protected final Log logger = LogFactory.getLog(getClass());
	private static boolean bInit = false;
	
	public List<GiftUI> getGiftList(String housingType, String locale, 
			String appDate, String SB, String technology, String channel, String like100Ind, String progOfferCd, String otChrgInd, String channelTeamCd, String staffGroup, String staffOfferInd, String nowTVGiftInd) throws DAOException {
		List<GiftUI> giftList = new ArrayList<GiftUI>();
		
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

		String SQL = "SELECT DL.DESCRIPTION type_desc, 													\n"
				+ "NVL(DIDL.HTML, '') ITEM_DETAIL,												\n"
				+ "NVL(DIDL.DESCRIPTION, '') TITLE_PLAIN,												\n"
				+ "      I.ID,                                                                  \n"
				+ "      ISET.ELIGIBLE_ITEM_TYPE type,                                                                \n"
				+ "      ISET.BANDWIDTH,                                                        \n"
				+ "      ISET.PAYMENT_METHOD,                                                   \n"
				+ "      ISET.SERVICE_BOUNDARY ,                                                 \n"
				+ "      ISET.REQUIRED_INST_DATE ,                                                 \n"
				+ "      ISET.MIN_VAS_PRICE ,                                                 \n"				
				+ "      NVL(ISET.WAIVE_ISF_IND, 'N') WAIVE_ISF_IND ,                                                 \n"
				+ "      NVL(ISET.WAIVE_ASF_IND, 'N') WAIVE_ASF_IND ,                                                 \n"
				+ "      DIDL.IMAGE_PATH ,                                                      \n"
				+ "      DIDL.html2 gift_desc,  	                                                \n"
				+ "		MFIDL.HTML MTHFIX,                                                                  \n"
				+ "		decode(IT.TYPE_DESC,'ALL','N','Y') dedi_offer_ind,                        \n"
				+ "  to_char(appl_start_date, 'yyyy/mm/dd') gift_start_date, 					  \n"
				+ " to_char(NVL (appl_end_date, TO_DATE ('12/31/2099', 'MM/DD/YYYY')), 'yyyy/mm/dd')    gift_end_date, \n"
				+ " (select decode(count(*),0,'Y','N') from w_item_type where item_id = i.id and type = 'LINK_VAS') show_ind, \n"
				+ " (select rtrim (xmlagg (xmlelement (e, 'LINK'||type_desc || ' ')).extract ('//text()'), ' ')  from w_item_type where i.id = item_id and type = 'LINK_VAS') link_vas \n"
				+ "FROM        W_ITEM                  I,                                       \n"
				+ "      W_ITEM_DTL_IMS                IDI,                                     \n"
				+ "      W_ITEM_TYPE                IT,                                     	\n"
				+ "      W_ITEM_TYPE                IT2,                                     	\n"
				+ "      W_ITEM_PRICING                      IP,                                \n"
				+ "      W_ITEM_SET                       ISET,                                 \n"
				+ "      W_ITEM_SET_ASSGN           ISA,                                        \n"
				+ "      W_ITEM_SET_APPL_RANGE      ISAR,                                       \n"
				+ "      W_ITEM_DISPLAY_LKUP              DIDL,                                  \n"
				+ "	W_ITEM_DISPLAY_LKUP   MFIDL,                                                        \n"
				+ "	W_DISPLAY_LKUP   DL,                                                       \n"
				+ "		(select distinct dbia.item_id " 
				+ "		   from w_dic_basket_item_assgn dbia," 
				+ "		 		W_ITEM_TYPE   TEAMIT, 													\n"
				+ "		 		W_ITEM_TYPE   STAFFIT 												\n"
				+ "		  where basket_id = "+ dummyBasket_id +" and nvl(eff_end_date,"+ appDate+ ") >= "+ appDate +" and eff_start_date <= "+ appDate +" \n"
				+ "			AND dbia.item_id = TEAMIT.ITEM_ID(+)		 \n"
				+ "			AND TEAMIT.TYPE(+) = 'GIFT_DE_TEAM'		 \n"
				+ "			AND	nvl(TEAMIT.TYPE_DESC, '"+ channelTeamCd +"') = '"+ channelTeamCd +"'             \n"
				+ "			AND dbia.item_id = STAFFIT.ITEM_ID(+)		 \n"
				+ "			AND STAFFIT.TYPE(+) = 'GIFT_DE_STAFF'		 \n"
				+ "			AND ','||'"+ staffGroup +"'||',' like ',%'||nvl(STAFFIT.TYPE_DESC, '"+ staffGroup +"')||'%,'              \n"
				+ "		) ITEM_FILTER \n"
				+ "WHERE  I.ID = IP.ID                                                          \n"
				+ "AND 	  I.TYPE NOT IN ('PROM_GIFT')											\n" 
				+ "AND 	  I.ID = IT.ITEM_ID											\n" 
				+ "AND 	  IT.TYPE = 'GIFT_DE_OFFER' 											\n" 
				+ "AND 	  DECODE(IT.TYPE_DESC, 'ALL','"+ progOfferCd +"',IT.TYPE_DESC) = '"+ progOfferCd +"' 											\n" 
				+ "AND 	  I.ID = IT2.ITEM_ID											\n" 
				+ "AND 	  IT2.TYPE = 'BRAND' 											\n" 
				+ "AND 	  IT2.TYPE_DESC = DECODE('"+like100Ind+"','Y','LIKE','NETVIGATOR')	\n"
				+ "AND    I.ID = ITEM_FILTER.ITEM_ID                           \n"
				+ "AND    I.ID = DIDL.ITEM_ID(+)                                                \n"
				+ "AND    I.ID = MFIDL.ITEM_ID(+)                                                       \n"
				+ "AND    DIDL.LOCALE(+) = ?              --LOCALE--                         \n"
				+ "AND      DIDL.DISPLAY_TYPE(+) = 'DETAIL'                                         \n"
				+ "AND    MFIDL.LOCALE(+) = ? 			--LOCALE--                                          \n"
				+ "AND	   MFIDL.DISPLAY_TYPE(+) = 'MTHFIX'                                                \n"
				+ "AND    NVL(IP.EFF_END_DATE , "
				+ appDate
				+ " ) >= "
				+ appDate
				+ "           --APPDATE--      \n"
				+ "AND    IP.EFF_START_DATE <= "
				+ appDate
				+ "                         --APPDATE--      \n"
				+ "AND    I.ID = IDI.ITEM_ID(+)                                                 \n"
				+ "AND      I.LOB = 'IMS'                                                       \n"
				+ "AND ISET.ITEM_SET_ID = ISA.ITEM_SET_ID                                       \n"
				+ "AND ISA.ITEM_SET_ID = ISAR.ITEM_SET_ID                                       \n"
				+ "AND ISET.LOB = 'IMS'                                                         \n"
				+ "AND nvl(ISET.technology,'"+technology+"') like '%"+technology+"%' 			\n"
				+ "AND ','||replace(nvl(ISET.HOUSING_TYPE,'"+housingType+"'),' ','')||',' like '%,'||'"+housingType +"'||',%' 	 --HOUSING_TYPE--\n" 
				+ "AND nvl(ISET.SERVICE_BOUNDARY,'"+SB+"') like '%"+SB+"%'    --SERVICE_BOUNDARY--\n"
				+ "AND ISET.ELIGIBLE_ITEM_TYPE = I.TYPE    \n"
				+ "AND ISA.ITEM_ID = I.ID                                                       \n"
		        + "AND ISAR.APPL_START_DATE is null --APPDATE--                              \n"
		        + "AND DL.TYPE = 'IMS_ITYPE_'||ISET.ELIGIBLE_ITEM_TYPE                              \n"
		        + "AND DL.LOCALE = ?              --LOCALE--                             \n";
				if("W".equalsIgnoreCase(otChrgInd)){
					SQL +=  "AND NVL(ISET.WAIVE_ISF_IND,'N')||NVL(ISET.WAIVE_ASF_IND,'N') = 'NN' AND I.TYPE NOT IN ('CLUB_PT_REDM_GIFT')		\n";
				}else if("I".equalsIgnoreCase(otChrgInd)){
					SQL +=  "AND NVL(ISET.WAIVE_ISF_IND,'N')||NVL(ISET.WAIVE_ASF_IND,'N') <> 'NY'		\n";
				}else if("A".equalsIgnoreCase(otChrgInd)){
					SQL +=  "AND NVL(ISET.WAIVE_ISF_IND,'N')||NVL(ISET.WAIVE_ASF_IND,'N') <> 'YN'		\n";
				}
				if("Y".equalsIgnoreCase(staffOfferInd)){
					SQL +=  "AND I.TYPE IN (select code from w_code_lkup where grp_id = 'IMS_STAFF_GIFT_TYPE')		\n";
				}else{
					SQL +=  "AND I.TYPE NOT IN (select code from w_code_lkup where grp_id = 'IMS_STAFF_GIFT_TYPE')		\n";
				}
				if("Y".equalsIgnoreCase(nowTVGiftInd)){
					SQL +=  "AND I.TYPE IN (select code from w_code_lkup where grp_id = 'IMS_NTV_PCD_GIFT_TYPE')		\n";
				}else{
					SQL +=  "AND I.TYPE NOT IN (select code from w_code_lkup where grp_id = 'IMS_NTV_PCD_GIFT_TYPE')		\n";
				}
				SQL +=	"AND NOT EXISTS (select 1 from w_item_type where item_id = I.ID and type = 'GIFT_EX_OFFER' and type_desc = '"+ progOfferCd +"')	\n";//do not show excluded gifts
		        SQL +=  "order by DL.ID asc, ISET.ELIGIBLE_ITEM_TYPE asc, I.DISPLAY_SEQ, I.ID desc			\n"; 

		ParameterizedRowMapper<GiftUI> mapper = new ParameterizedRowMapper<GiftUI>() {
			public GiftUI mapRow(ResultSet rs, int rowNum) throws SQLException {
				GiftUI gift = new GiftUI();
				gift.setItemDetail(rs.getString("item_detail"));
				gift.setGiftTitlePlainText(rs.getString("TITLE_PLAIN"));
				gift.setGiftValue(rs.getString("MTHFIX"));
				if(rs.getString("GIFT_DESC")!=null) gift.setGiftDesc(rs.getString("GIFT_DESC").replaceAll(((char)10)+"", "<br>"));
				else gift.setGiftDesc(" ");
				gift.setId(rs.getString("id"));
				gift.setBandwidth(rs.getString("bandwidth"));
				gift.setPaymentMethod(rs.getString("payment_method"));
				gift.setServiceBoundary(rs.getString("service_boundary"));
				gift.setGift_start_date(rs.getString("gift_start_date"));
				gift.setGift_end_date(rs.getString("gift_end_date"));
				gift.setImagePath(rs.getString("image_path"));
				gift.setType(rs.getString("type"));
				gift.setTypeDesc(rs.getString("type_desc"));
				gift.setRequiredInstDate(rs.getString("REQUIRED_INST_DATE"));
				gift.setDediOfferInd(rs.getString("dedi_offer_ind"));
				gift.setLinkVas(rs.getString("link_vas"));
				gift.setShowInd(rs.getString("show_ind"));
				gift.setWaiveISFInd(rs.getString("WAIVE_ISF_IND"));
				gift.setWaiveASFInd(rs.getString("WAIVE_ASF_IND"));
				gift.setMinVasPrice(rs.getString("MIN_VAS_PRICE"));
//				gift.setInputMethod(rs.getString("INPUT_METHOD"));
//				gift.setAttbId(rs.getString("ATTB_ID"));
//				gift.setAttbDesc(rs.getString("ATTB_DESC"));
				return gift;
			}
		};
 
		try {
			logger.info("channel = " + channel);
			logger.info("housingType = " + housingType);
			logger.info("SB = " + SB);
			logger.info("technology = "+technology);
			logger.debug("getGiftList @ GiftDetailDAO: " + SQL);
			giftList = simpleJdbcTemplate.query(SQL, mapper, locale,
					locale, locale);
		} catch (EmptyResultDataAccessException erdae) {
			logger.debug("EmptyResultDataAccessException");
			giftList = null;
		} catch (Exception e) {
			logger.debug("Exception caught in getgiftList():", e);
			throw new DAOException(e.getMessage(), e);
		}
		return giftList;
	}
	


	public List<AttbDTO> getAttb(String itemids,String locale) throws DAOException{
		List<AttbDTO> attbList = new ArrayList<AttbDTO>();
		
		String SQL=
			"select wiopa.item_id,wiopa.product_id,wal.attb_id, wal.INPUT_METHOD, " +
			"wal.INPUT_FORMAT, wal.MIN_LENGTH, wal.MAX_LENGTH ,wal.DESCRIPTION, wal.default_value, wal.visual_ind \n"+
			"from w_item_offer_product_assgn wiopa,w_attb_lkup wal, w_product_attb_assgn wpaa                        \n"+
			"where item_id in ("+itemids+")                                                                          \n"+
			"and wiopa.product_id = wpaa.product_id                                                                  \n"+
			"and wpaa.attb_id = wal.attb_id                                                                          \n"+
			"and wal.locale = ?                                                                                   \n"+
			"order by item_id, wal.visual_ind                                                                     \n";
		
		ParameterizedRowMapper<AttbDTO> mapper = new ParameterizedRowMapper<AttbDTO>() {
			public AttbDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				AttbDTO attb = new AttbDTO();
				attb.setItemId(rs.getString("item_id"));
				attb.setAttbId(rs.getString("attb_id"));
				attb.setInputMethod(rs.getString("input_method"));
				attb.setInputFormat(rs.getString("input_format"));
				attb.setAttbDesc(rs.getString("description"));
				attb.setDefaultValue(rs.getString("default_value"));
				attb.setMinLength(rs.getString("min_length"));
				attb.setMaxLength(rs.getString("max_length"));
				attb.setVisualInd(rs.getString("visual_ind"));
				//attb.setInputValue(attb.getDefaultValue());//by jacky
				return attb;
			}
		};
		
		try {
//			logger.info("getAttb = " + SQL);
//			logger.info("itemids = " + itemids);
			attbList = simpleJdbcTemplate.query(SQL, mapper, locale);
		} catch (EmptyResultDataAccessException erdae) {
			logger.debug("EmptyResultDataAccessException");
			attbList = null;
		} catch (Exception e) {
			logger.debug("Exception caught in getgiftList():", e);
			throw new DAOException(e.getMessage(), e);
		}
		return attbList;
	}
	


	public List<VASDetailUI> getDediVASList(String itemids) throws DAOException{
		List<VASDetailUI> vasList = new ArrayList<VASDetailUI>();
		
		String SQL=
			"select wt.item_id gift_item_id, wi.id vas_item_id, wi.type vas_type, wiopa.product_id vas_prod_id \n"+
			"from w_item_type wt, \n"+
			"w_item wi, \n"+
			"w_item_offer_product_assgn wiopa \n"+
			"where wt.item_id in ("+itemids+") \n"+
			"and wt.type = 'GIFT_DE_VAS' \n"+
			"and wi.id||'' = wt.type_desc \n"+
			"and wi.id = wiopa.item_id \n";
		
		ParameterizedRowMapper<VASDetailUI> mapper = new ParameterizedRowMapper<VASDetailUI>() {
			public VASDetailUI mapRow(ResultSet rs, int rowNum) throws SQLException {
				VASDetailUI vas = new VASDetailUI();
				vas.setItemId(rs.getString("vas_item_id"));
				vas.setGiftItemId(rs.getString("gift_item_id"));
				vas.setItemType(rs.getString("vas_type"));
				vas.setProductId(rs.getString("vas_prod_id"));
				return vas;
			}
		};
		
		try {
//			logger.info("getAttb = " + SQL);
//			logger.info("itemids = " + itemids);
			vasList = simpleJdbcTemplate.query(SQL, mapper);
		} catch (EmptyResultDataAccessException erdae) {
			logger.debug("EmptyResultDataAccessException");
			vasList = null;
		} catch (Exception e) {
			logger.debug("Exception caught in getDediVASList():", e);
			throw new DAOException(e.getMessage(), e);
		}
		return vasList;
	}
	


	public List<NowTVCampaignCdDTO> getGiftSophieSub(String itemids) throws DAOException{
		List<NowTVCampaignCdDTO> sophieSubList = new ArrayList<NowTVCampaignCdDTO>();
		
		String SQL=
			"select item_id, campaign_code, pack_code, topup_code \n" +
			"from w_tv_campaign_pack_assgn\n"+
			"where item_id in ("+itemids+")                                                                          \n"+
			"order by item_id                                                                     \n";
		
		ParameterizedRowMapper<NowTVCampaignCdDTO> mapper = new ParameterizedRowMapper<NowTVCampaignCdDTO>() {
			public NowTVCampaignCdDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				NowTVCampaignCdDTO dto = new NowTVCampaignCdDTO();
				dto.setItemId(rs.getString("item_id"));
				dto.setCampaignCd(rs.getString("campaign_code"));
				dto.setPackCd(rs.getString("pack_code"));
				dto.setTopUpCd(rs.getString("topup_code"));
				return dto;
			}
		};
		
		try {
//			logger.info("getAttb = " + SQL);
//			logger.info("itemids = " + itemids);
			sophieSubList = simpleJdbcTemplate.query(SQL, mapper);
		} catch (EmptyResultDataAccessException erdae) {
			logger.debug("EmptyResultDataAccessException");
			sophieSubList = null;
		} catch (Exception e) {
			logger.debug("Exception caught in getgiftList():", e);
			throw new DAOException(e.getMessage(), e);
		}
		return sophieSubList;
	}

	
	//Tony promo gift
	public List<GiftUI> getPromoGiftList(String locale, String housingType, String SB, String channel, String technology, String like100Ind, String progOfferCd) throws DAOException{
		List<GiftUI> giftList = new ArrayList<GiftUI>();
		GiftUI gift = new GiftUI();
		
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
		
		
		String SQL=
			"select idt.GIFT_QTY	, idt.PROM_CODE, i.id item_id   ,  \n"+
			"  i.type              ,                                      \n"+
			"  idl.image_path      ,                                      \n"+
			"  idl.html,                                                  \n"+
			"  wis.BANDWIDTH                                              \n"+
			"   from w_item i          ,                                  \n"+
			"  w_item_type it ,                                  		  \n"+
			"  w_item_type it2 ,                                  		  \n"+
			"  w_item_display_lkup idl ,                                  \n"+
			"  w_item_dtl_tv idt,                                   \n"+
			"  w_item_pricing ip,                                          \n"+
			"   w_item_set wis,												\n"+
			"   w_item_set_assgn wisa 										\n"+
			"  where i.id                             = ip.id             \n"+
			"and i.id                                 = idl.item_id       \n"+
			"and i.type                               = 'PROM_GIFT'        \n"+
			"and i.id = it.item_id                                        \n"+
			"and it.type = 'GIFT_DE_OFFER' 								  \n"+ 
			"and decode(it.type_desc, 'ALL','"+ progOfferCd +"',it.type_desc) = '"+ progOfferCd +"'\n"+
			"and i.id = it2.item_id                                        \n"+
			"and it2.type = 'BRAND' 								  \n"+ 
			"and it2.type_desc = decode('"+like100Ind+"','Y','LIKE','NETVIGATOR')	\n"+
			"and nvl(ip.eff_end_date,trunc(sysdate)) >=trunc(sysdate)     \n"+
			"and ip.eff_start_date                   <= trunc(sysdate)    \n"+
			"and idl.display_type                     = 'DETAIL'          \n"+
			"and idl.locale                           = ? --locale        \n"+
			"and idt.ITEM_ID = i.id 				 \n"+
			"and i.id = wisa.item_id 				 \n"+
			"and wisa.item_set_id = wis.item_set_id 				 \n"+
			"and nvl(wis.technology,'"+technology+"') like '%"+technology+"%' 			\n"+
			"and ','||replace(nvl(wis.HOUSING_TYPE,'"+housingType+"'),' ','')||',' like '%,'||'"+housingType +"'||',%' 	 --HOUSING_TYPE--\n"+
			"and nvl(wis.SERVICE_BOUNDARY,'"+SB+"') like '%"+SB+"%'    --SERVICE_BOUNDARY--\n"+
			"and exists (select 1 from w_dic_basket_item_assgn where basket_id = "+ dummyBasket_id +" and item_id = i.id)";
		
		ParameterizedRowMapper<GiftUI> mapper = new ParameterizedRowMapper<GiftUI>() {
			public GiftUI mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				GiftUI gift = new GiftUI();
				
				gift.setQuota(rs.getString("GIFT_QTY"));
				gift.setPromoCode(rs.getString("PROM_CODE"));
				gift.setItemDetail(rs.getString("html"));
				
				//gift.setGiftPromoTitle(rs.getString("promotitle"));
				//gift.setGiftURL(rs.getString("url"));
				gift.setId(rs.getString("item_id"));
				gift.setType(rs.getString("type"));
				gift.setImagePath(rs.getString("image_path"));
				gift.setBandwidth(rs.getString("BANDWIDTH"));
				
				gift.setGiftTitle(this.splitString(gift.getItemDetail())[0]); 
				gift.setGiftDetail(this.splitString(gift.getItemDetail())[1].replaceAll(((char)10)+"", "<br>"));
				
				return gift;
			}
			
			private String[] splitString(String pString) {
				
				if(pString!=null){
					String[] strArray = new String[2];
					int newlineInx = pString.indexOf((char)10+"");
					int newlineInx2 = pString.indexOf("<br/>");
					
					if(newlineInx>0){
						strArray[0] = pString.substring(0, newlineInx);
						strArray[1] = pString.substring(newlineInx+1);
					}
					else if (newlineInx2>0){
						strArray[0] = pString.substring(0, newlineInx2);
						strArray[1] = pString.substring(newlineInx2+5);
					}
					else {
						strArray[0] = pString;
						strArray[1] = "";
					}
					
					return strArray;
				}
				else return null;
			}
		};

		try {
			logger.debug("getPromoGiftList @ GiftDetailDAO");
			giftList = simpleJdbcTemplate.query(SQL, mapper, locale);
		} catch (EmptyResultDataAccessException erdae) {
			logger.debug("EmptyResultDataAccessException");
			gift = null;
		} catch (Exception e) {
			logger.debug("Exception caught in getPromoGiftList():", e);
			throw new DAOException(e.getMessage(), e);
		}
		
		if (giftList.size() > 0){
			return giftList;
		}else{
			return null;
		}
	}	
	
	public HashMap<String, HashMap<String, Object>> getGiftSelectCnt(String channel,String locale) throws SQLException{
		
		HashMap<String, HashMap<String, Object>> giftSelectCnt = new HashMap<String, HashMap<String, Object>>();
		HashMap<String, Object> cntMap;
		
		String SQL=
		   "select substr(a.code,0,decode(instr(a.code,'MAX'),0,instr(a.code,'MIN'),instr(a.code,'MAX'))-2) gift_type, 		"+
		   "substr(a.code,decode(instr(a.code,'MAX'),0,instr(a.code,'MIN'),instr(a.code,'MAX'))) cnt,				   		"+
		   "a.description,  																					   	   		"+
		   "b.description type_desc																					   		"+
		   "from w_code_lkup a, w_display_lkup b																	   		"+
		   "where a.grp_id = 'IMS_"+channel+"_GIFT_CNT'																	"+
		   "and b.type like '%'||substr(a.code,0,decode(instr(a.code,'MAX'),0,instr(a.code,'MIN'),instr(a.code,'MAX'))-2)	"+
		   "and b.locale = '"+locale+"'																								";													
		

		Connection conn = dataSource.getConnection();
		if(conn == null || conn.isClosed()){
			conn = dataSource.getConnection();
		}

		Statement stmt = null;
		ResultSet rs = null;
		try {
			logger.debug("getGiftSelectCnt @ GiftDetailDAO");
			stmt = conn.createStatement();
			rs = stmt.executeQuery(SQL);
			
	    	while (rs.next()){
	    		if(giftSelectCnt.containsKey(rs.getString("gift_type"))){
	    			cntMap = giftSelectCnt.get(rs.getString("gift_type"));
	    			cntMap.put(rs.getString("cnt"), Integer.parseInt(rs.getString("description")));
	    		}
	    		else
	    		{
	    			cntMap = new HashMap<String, Object>();
	    			cntMap.put(rs.getString("cnt"), Integer.parseInt(rs.getString("description")));
	    			cntMap.put("TYPE_DESC", rs.getString("type_desc"));
	    			cntMap.put("SELECTED_CNT", 0);
	    			cntMap.put("GIFT_CNT", 0);
	    			giftSelectCnt.put(rs.getString("gift_type"), cntMap);
	    		}
	    		
	    	}
	    	rs.close();
			
		}  finally{
			if(rs != null)
				rs.close();
			if(stmt != null)
				stmt.close();
			if(conn != null)
				conn.close();
		}
		
		return giftSelectCnt;
		
	}
	
	public List<VASDetailUI> getGiftExclusiveProgItemList(String locale, List<String> selectedList, String progOfferCd)
	throws DAOException {

		List<VASDetailUI> exclusiveItemList = new ArrayList<VASDetailUI>();
		logger.info("getExclusiveItemList is called");
		
		String SQL = "";

		SQL = " SELECT b.ITEM_ID item_id,  					 "+
					 " 	   b.HTML ITEM_DESCRIPTION	 			 "+ 
					 "	   from w_item_type a, 	 				 "+
					 "	   w_item_display_lkup b				 "+
					 "where a.type = 'GIFT_EX_OFFER'		 	 "+
					 "and 	a.type_desc = '"+progOfferCd+"'		 "+
					 "and   a.item_id = b.item_id		 	 	 "+
					 "and   b.display_type = 'DETAIL'		 	 "+
					 "and   b.locale = '"+locale+"'		 		 ";


		String itemSql = "";

		if (selectedList != null) {
			if (selectedList.size() > 0) {
				itemSql += "AND    a.ITEM_ID IN (";
				for (int i = 0; i < selectedList.size(); i++) {
					itemSql += selectedList.get(i) + ",";
				}

				itemSql += ") ";
				itemSql = itemSql.replace(",)", ")");// replace the last comma
			} else {
				itemSql = "";
			}

		}

		SQL = SQL + itemSql;
		ParameterizedRowMapper<VASDetailUI> mapper = new ParameterizedRowMapper<VASDetailUI>() {

			public VASDetailUI mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				VASDetailUI vasUI = new VASDetailUI();
				int newlineInx =0;
				vasUI.setItemId(rs.getString("item_id"));
				vasUI.setVASHtml(rs.getString("item_description"));
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
			logger.debug("getGiftExclusiveProgItemList @ GiftDetailDAO: " + SQL);
			exclusiveItemList = simpleJdbcTemplate.query(SQL, mapper);

		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException");
			exclusiveItemList = null;
		} catch (Exception e) {
			logger.error("Exception caught in getGiftExclusiveProgItemList()", e);
			throw new DAOException(e.getMessage(), e);
		}
		return exclusiveItemList;

	}
	
	
	
}

