package com.bomwebportal.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.core.support.AbstractSqlTypeValue;
import org.springframework.jdbc.support.nativejdbc.CommonsDbcpNativeJdbcExtractor;

import com.bomwebportal.dto.BasketDTO;
import com.bomwebportal.dto.BasketMinVasLkupDTO;
import com.bomwebportal.dto.BasketParmDTO;
import com.bomwebportal.dto.BasketQuotaDTO;
import com.bomwebportal.dto.ExclusiveItemDTO;
import com.bomwebportal.dto.ProductInfoDTO;
import com.bomwebportal.dto.ResultDTO;
import com.bomwebportal.dto.SimDTO;
import com.bomwebportal.dto.VasAttbComponentDTO;
import com.bomwebportal.dto.VasDetailDTO;
import com.bomwebportal.dto.report.RptVasDetailDTO;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.mob.ccs.dto.ui.MRTUI;
import com.bomwebportal.mob.ccs.dto.ui.PaymentMonthyUI;
import com.bomwebportal.util.Utility;
import com.bomwebportal.web.util.GenericReportHelper;
import com.bomwebportal.web.util.ProjectEagleReportHelper;

import oracle.jdbc.OracleTypes;
import oracle.sql.ARRAY;
import oracle.sql.ArrayDescriptor;

public class VasDetailDAO extends BaseDAO {
	protected final Log logger = LogFactory.getLog(getClass());
	
	// MIP.P4 modification
	public List<VasDetailDTO> getVasDetailList(String channelId,
			String locale, String basketId, String[] selectedList,
			String displayType, String appDate, String channelCd,
			String mipBrand, String mipSimType, String offerNature) throws DAOException {
		
		// MIP.P4 modification
		logger.debug("MIP.P4 modification: pkg_sb_mob_stock.get_stock_count_disp_mip4 (5)");
		Utility.toPrettyJson(offerNature);
		
		List<VasDetailDTO> vasDetailList = new ArrayList<VasDetailDTO>();
		logger.debug("getVasDetailtList is called");
		String[] precSelectedList = null;// for recall change MOD_IND
		if ("ITEM_SELECT".equals(displayType)) {
 
			if (selectedList != null) {
				precSelectedList = selectedList;
			}
			selectedList = null;
		}

		String SQL =

"select ITEM_DISP.ITEM_ID\n" +
"              ,ITEM_DISP.ITEM_LOB\n" + 
"              ,ITEM_DISP.ITEM_TYPE\n" + 
"              ,ITEM_DISP.ITEM_MDO_IND\n" + 
"              ,ITEM_DISP.HTML\n" + 
"              ,ITEM_DISP.HTML2\n" + 
"              ,ITEM_DISP.LOCALE\n" + 
"              ,ITEM_DISP.DISPLAY_TYPE\n" + 
"              ,ITEM_DISP.ONETIME_AMT\n" + 
"              ,ITEM_DISP.RECURRENT_AMT\n" + 
"              ,ITEM_DISP.ONETIME_TYPE\n" + 
"              ,ITEM_DISP.MIP_BRAND\n" + 
"              ,ITEM_DISP.MIP_SIM_TYPE\n" + 
"              ,NVL(ITEM_DISP.CATEGORY_ID, 9999999) CATEGORY_ID\n" + 
"              ,ITEM_DISP.VAS_GROUP\n" +
"              ,(select DL.DESCRIPTION\n" + 
"                from W_DISPLAY_LKUP DL\n" + 
"                where DL.TYPE = 'ITEM_CATEGORY'\n" + 
"                and DL.LOCALE = :V_LOCALE\n" + 
"                and DL.ID = NVL(ITEM_DISP.CATEGORY_ID, 9999999)) CATEGORY_DESC\n" + 
"              ,(select min(IPPA.POS_ITEM_CD)\n" + 
"                from W_ITEM_PRODUCT_POS_ASSGN IPPA\n" + 
"                where IPPA.ITEM_ID = ITEM_DISP.ITEM_ID) POS_ITEM_CD\n" + 
"              ,NVL(NVL((select min(IPRO.DISPLAY_SEQ)\n" + 
"                       from W_ITEM_PRE_REQUISITE_OR IPRO, W_BASKET_ITEM_ASSGN BIA\n" + 
"                       where IPRO.REQUIRED_ITEM_ID = BIA.ITEM_ID\n" + 
"                       and BIA.BASKET_ID = :V_BASKET_ID ---------------------100000738\n" + 
"                       and IPRO.ITEM_ID = ITEM_DISP.ITEM_ID\n" + 
"                       and TO_DATE(:V_APP_DATE_STR, 'DD/MM/YYYY') between\n" + 
"                             BIA.EFF_START_DATE and NVL(BIA.EFF_END_DATE, sysdate) --':INPUT_DATE'--app_date--\n" + 
"                       and TO_DATE(:V_APP_DATE_STR, 'DD/MM/YYYY') between\n" + 
"                             IPRO.EFF_START_DATE and\n" + 
"                             NVL(IPRO.EFF_END_DATE, sysdate) --app_date--\n" + 
"                       ), (select min(NVL(VIGA.DISPLAY_SEQ, 5))\n" + 
"                         from W_VAS_ITEM_GRP_ASSGN VIGA, W_CHANNEL_BASKET_ASSGN CBA\n" + 
"                         where CBA.CHANNEL_ID = :V_CHANNEL_ID ------------------2\n" + 
"                         and CBA.BASKET_ID = :V_BASKET_ID ------------------100000738\n" + 
"                         and CBA.VAS_ITEM_GRP_ID = VIGA.GRP_ID\n" + 
"                         and VIGA.ITEM_ID = ITEM_DISP.ITEM_ID\n" + 
"                         and TO_DATE(:V_APP_DATE_STR, 'DD/MM/YYYY') between\n" + 
"                               VIGA.EFF_START_DATE and\n" + 
"                               NVL(VIGA.EFF_END_DATE, sysdate))), 0) DISPLAY_SEQ\n" + 
"              ,\n" + 
"               ----STOCK_COUNT sql\n" + 
"               (select pkg_sb_mob_stock.get_stock_count_disp_mip4('PEND', :V_CHANNEL_CD, IPPA.POS_ITEM_CD, :offerNature) STOCK_COUNT\n" + 

"                from W_ITEM_PRODUCT_POS_ASSGN IPPA\n" + 
"                where IPPA.ITEM_ID = ITEM_DISP.ITEM_ID) STOCK_COUNT\n" + 
"        -----END STOCK_COUNT sql\n" + 
"               ,ITEM_DISP.CONTRACT_PERIOD \n"+
"               ,ITEM_DISP.REBATE_AMT \n"+
"        from ( --item display,pricing info\n" + 
"              select I.ID ITEM_ID\n" + 
"                     ,I.LOB ITEM_LOB\n" + 
"                     ,I.TYPE ITEM_TYPE\n" + 
"                     ,'O' ITEM_MDO_IND\n" + 
"                     ,IDL.HTML\n" + 
"                     ,IDL.HTML2\n" + 
"                     ,IDL.LOCALE\n" + 
"                     ,IDL.DISPLAY_TYPE\n" + 
"                     ,IP.ONETIME_TYPE\n" + 
"                     ,IP.ONETIME_AMT\n" + 
"                     ,IP.RECURRENT_AMT\n" + 
"                     ,I.CATEGORY_ID\n" + 
"                     ,I.VAS_GROUP\n" +
"       ,NVL(i.MIP_BRAND, '1') MIP_BRAND\n" + 
"       ,NVL(i.MIP_SIM_TYPE, 'H') MIP_SIM_TYPE\n" +  
"       ,IDRRV.CONTRACT_PERIOD \n"+
"       ,IDRRV.REBATE_AMT \n"+
"              from W_ITEM I, W_ITEM_DISPLAY_LKUP IDL, W_ITEM_PRICING IP\n" + 
"                  , W_ITEM_DTL_RP_RB_VAS IDRRV \n" +
"              where I.ID = IDL.ITEM_ID\n" + 
"              and I.ID = IDRRV.ID \n" +
"              and IDL.LOCALE = :V_LOCALE ------------------- LOCALE\n" + 
"              and IDL.DISPLAY_TYPE = 'ITEM_SELECT'\n" + 
"              and I.ID = IP.ID\n" + 
"              and TO_DATE(:V_APP_DATE_STR, 'DD/MM/YYYY') between IP.EFF_START_DATE and\n" + 
"                    NVL(IP.EFF_END_DATE, sysdate) ------------------app_date--\n" +
"		    and nvl(decode (I.MIP_BRAND, '9', :mipBrand, I.MIP_BRAND ), '1') = :mipBrand \n" +
"			and nvl(decode (I.MIP_SIM_TYPE, 'X', :mipSimType, I.MIP_SIM_TYPE ), 'H') = :mipSimType \n" +
"              ) ITEM_DISP\n" + 
"            ,( --basket vas group item\n" + 
"              (select VIGA.ITEM_ID\n" + 
"               from W_CHANNEL_BASKET_ASSGN CBA, W_VAS_ITEM_GRP_ASSGN VIGA\n" + 
"               where CBA.VAS_ITEM_GRP_ID = VIGA.GRP_ID\n" + 
"               and CBA.CHANNEL_ID = :V_CHANNEL_ID ------------------- CHANNEL_ID\n" + 
"               and TO_DATE(:V_APP_DATE_STR, 'DD/MM/YYYY') between VIGA.EFF_START_DATE and\n" + 
"                     NVL(VIGA.EFF_END_DATE, sysdate) ------------------app_date--\n" + 
"               and CBA.BASKET_ID = :V_BASKET_ID -------------------100000738\n" + 
"               union\n" + 
"               --item pre item\n" + 
"               select IPRO.ITEM_ID\n" + 
"               from W_CHANNEL_BASKET_ASSGN  CBA\n" + 
"                   ,W_BASKET_ITEM_ASSGN     BIA\n" + 
"                   ,W_ITEM_PRE_REQUISITE_OR IPRO\n" + 
"               where BIA.ITEM_ID = IPRO.REQUIRED_ITEM_ID\n" + 
"               and CBA.BASKET_ID = BIA.BASKET_ID\n" +  //add 20160304
"               and CBA.CHANNEL_ID = :V_CHANNEL_ID ------------------- CHANNEL_ID\n" + 
"               and TO_DATE(:V_APP_DATE_STR, 'DD/MM/YYYY') between BIA.EFF_START_DATE and\n" + 
"                     NVL(BIA.EFF_END_DATE, sysdate) ------------------app_date--\n" + 
"               and TO_DATE(:V_APP_DATE_STR, 'DD/MM/YYYY') between IPRO.EFF_START_DATE and\n" + 
"                     NVL(IPRO.EFF_END_DATE, sysdate) ------------------app_date--\n" + 
"               and BIA.BASKET_ID = :V_BASKET_ID -------------------100000738\n" + 
"               ) minus\n" + 
"              (select ITEM_ID_A\n" + 
"               from W_MOB_ITEM_EXCLUSIVE_LKUP A\n" + 
"               where ITEM_ID_B in\n" + 
"                     (select ITEM_ID\n" + 
"                      from W_BASKET_ITEM_ASSGN BIA\n" + 
"                      where BIA.BASKET_ID = :V_BASKET_ID ------------- 100000738\n" + 
"                      and BIA.MDO_IND = 'M'\n" + 
"                      and TO_DATE(:V_APP_DATE_STR, 'DD/MM/YYYY') between\n" + 
"                            BIA.EFF_START_DATE and NVL(BIA.EFF_END_DATE, sysdate)) ------------------app_date--\n" + 
"               and ITEM_TYPE_B in ('BVAS', 'RP')\n" + 
"               and ITEM_TYPE_A = 'VAS'\n" + 
"               union\n" + 
"\n" + 
"               select ITEM_ID_b\n" + 
"               from W_MOB_ITEM_EXCLUSIVE_LKUP\n" + 
"               where ITEM_ID_a in\n" + 
"                     (select ITEM_ID\n" + 
"                      from W_BASKET_ITEM_ASSGN BIA\n" + 
"                      where BIA.BASKET_ID = :V_BASKET_ID ------------- 100000738\n" + 
"                      and BIA.MDO_IND = 'M'\n" + 
"                      and TO_DATE(:V_APP_DATE_STR, 'DD/MM/YYYY') between\n" + 
"                            BIA.EFF_START_DATE and NVL(BIA.EFF_END_DATE, sysdate)) ------------------app_date--\n" + 
"               and ITEM_TYPE_a in ('BVAS', 'RP')\n" + 
"               and ITEM_TYPE_b = 'VAS'\n" + 
"\n" + 
"                     )) ITEM_LIST\n" + 
"        where ITEM_DISP.ITEM_ID = ITEM_LIST.ITEM_ID\n" + 
"        order by DISPLAY_SEQ";

		ParameterizedRowMapper<VasDetailDTO> mapper = new ParameterizedRowMapper<VasDetailDTO>() {
			public VasDetailDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				VasDetailDTO vasDto = new VasDetailDTO();
				vasDto.setItemHtml(rs.getString("html"));
				vasDto.setItemId(rs.getString("item_id"));
				vasDto.setItemLob(rs.getString("item_lob"));
				vasDto.setItemType(rs.getString("item_type"));
				vasDto.setItemMdoInd(rs.getString("item_mdo_ind"));
				vasDto.setItemHtml2(rs.getString("html2"));
				vasDto.setItemLocale(rs.getString("locale"));
				vasDto.setItemDisplayType(rs.getString("display_type"));
				vasDto.setItemOnetimeAmt(rs.getString("onetime_amt"));
				vasDto.setItemRecurrentAmt(rs.getString("recurrent_amt"));
				vasDto.setStockCount(rs.getString("STOCK_COUNT"));
				vasDto.setPosItemCd(rs.getString("POS_ITEM_CD"));
				vasDto.setCategoryDesc(rs.getString("CATEGORY_DESC"));
				vasDto.setCategoryId(rs.getString("CATEGORY_ID"));
				vasDto.setItemOneTimeType(rs.getString("ONETIME_TYPE"));
				vasDto.setMipBrand(rs.getString("MIP_BRAND"));
				vasDto.setMipSimType(rs.getString("MIP_SIM_TYPE"));
				vasDto.setVasGroup(rs.getString("VAS_GROUP"));
				vasDto.setContractPeriod(rs.getString("CONTRACT_PERIOD"));
				vasDto.setNetMonthlyAmt(calculateNetMonthlyFee(rs.getString("recurrent_amt"),rs.getString("CONTRACT_PERIOD"),rs.getString("REBATE_AMT")));					
				return vasDto;
			}
		};

		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("V_LOCALE", locale);
		params.addValue("V_BASKET_ID", basketId);
		params.addValue("V_APP_DATE_STR", appDate);
		params.addValue("V_CHANNEL_ID", channelId);
		params.addValue("V_CHANNEL_CD", channelCd);
		params.addValue("mipBrand", mipBrand);
		params.addValue("mipSimType", mipSimType);
		
		// MIP.P4 modification
		params.addValue("offerNature", offerNature);

		try {
			logger.debug("getVasDetailtList @ VasDetaillDTO: ");
			vasDetailList = simpleJdbcTemplate.query(SQL, mapper,

			params);

			if ("ITEM_SELECT".equals(displayType)) {
				if (precSelectedList != null) {
					// check exist, if exist set it "D", default tick
					for (int i = 0; i < vasDetailList.size(); i++) {

						if (Utility.isContainString(precSelectedList,
								vasDetailList.get(i).getItemId())) {
							vasDetailList.get(i).setItemMdoInd("D");
						}
					}

				}

			}

		} catch (EmptyResultDataAccessException erdae) {
			logger.info("getVasDetailtList() EmptyResultDataAccessException");
			vasDetailList = null;
		} catch (Exception e) {
			logger.error("Exception caught in getVasDetailtList()", e);
			throw new DAOException(e.getMessage(), e);
		}
		return vasDetailList;

	}

	// MIP.P4 modification
	public List<VasDetailDTO> getHardBundleVasDetailList(String channelId,
			String locale, String basketId, String[] selectedList,
			String displayType, String appDate, String channelCd,
			String mipBrand, String mipSimType, String hardBundleGrpId, String offerNature) throws DAOException {
		
		// MIP.P4 modification
		logger.debug("MIP.P4 modification: pkg_sb_mob_stock.get_stock_count_disp_mip4 (4)");
		Utility.toPrettyJson(offerNature);
		
		List<VasDetailDTO> vasDetailList = new ArrayList<VasDetailDTO>();
		logger.debug("getHardBundleVasDetailList is called");
		String[] precSelectedList = null;// for recall change MOD_IND
		if ("ITEM_SELECT".equals(displayType)) {

			if (selectedList != null) {
				precSelectedList = selectedList;
			}
			selectedList = null;
		}
		

		String SQL =

"select ITEM_DISP.ITEM_ID\n" +
"              ,ITEM_DISP.ITEM_LOB\n" + 
"              ,ITEM_DISP.ITEM_TYPE\n" + 
"              ,ITEM_DISP.ITEM_MDO_IND\n" + 
"              ,ITEM_DISP.HTML\n" + 
"              ,ITEM_DISP.HTML2\n" + 
"              ,ITEM_DISP.LOCALE\n" + 
"              ,ITEM_DISP.DISPLAY_TYPE\n" + 
"              ,ITEM_DISP.ONETIME_AMT\n" + 
"              ,ITEM_DISP.RECURRENT_AMT\n" + 
"              ,ITEM_DISP.ONETIME_TYPE\n" + 
"              ,ITEM_DISP.MIP_BRAND\n" + 
"              ,ITEM_DISP.MIP_SIM_TYPE\n" + 
"              ,NVL(ITEM_DISP.CATEGORY_ID, 9999999) CATEGORY_ID\n" + 
"			   ,ITEM_DISP.VAS_GROUP\n" + 
"              ,(select DL.DESCRIPTION\n" + 
"                from W_DISPLAY_LKUP DL\n" + 
"                where DL.TYPE = 'ITEM_CATEGORY'\n" + 
"                and DL.LOCALE = :V_LOCALE\n" + 
"                and DL.ID = NVL(ITEM_DISP.CATEGORY_ID, 9999999)) CATEGORY_DESC\n" + 
"              ,(select min(IPPA.POS_ITEM_CD)\n" + 
"                from W_ITEM_PRODUCT_POS_ASSGN IPPA\n" + 
"                where IPPA.ITEM_ID = ITEM_DISP.ITEM_ID) POS_ITEM_CD\n" + 
"              ,(select display_seq\n" +
"                from W_HARD_BUNDLE_VAS_GRP_ASSGN HBVGA\n" +
"              where HBVGA.grp_id = :grpId\n" +
"                and HBVGA.item_id = ITEM_DISP.ITEM_ID\n" +
"                and TO_DATE(:V_APP_DATE_STR, 'DD/MM/YYYY') between trunc(HBVGA.eff_start_date) and trunc(nvl(HBVGA.eff_end_date, sysdate))\n" +  
"              and ROWNUM = 1) DISPLAY_SEQ\n" + 
"              ,\n" + 
"               ----STOCK_COUNT sql\n" + 

"               (select pkg_sb_mob_stock.get_stock_count_disp_mip4('PEND', :V_CHANNEL_CD, IPPA.POS_ITEM_CD, :offerNature) STOCK_COUNT\n" + 

"                from W_ITEM_PRODUCT_POS_ASSGN IPPA\n" + 
"                where IPPA.ITEM_ID = ITEM_DISP.ITEM_ID) STOCK_COUNT\n" + 
"        -----END STOCK_COUNT sql\n" + 
"            ,ITEM_DISP.CONTRACT_PERIOD \n" +
"            ,ITEM_DISP.REBATE_AMT \n"+
"        from ( --item display,pricing info\n" + 
"              select I.ID ITEM_ID\n" + 
"                     ,I.LOB ITEM_LOB\n" + 
"                     ,I.TYPE ITEM_TYPE\n" + 
"                     ,'O' ITEM_MDO_IND\n" + 
"                     ,IDL.HTML\n" + 
"                     ,IDL.HTML2\n" + 
"                     ,IDL.LOCALE\n" + 
"                     ,IDL.DISPLAY_TYPE\n" + 
"                     ,IP.ONETIME_TYPE\n" + 
"                     ,IP.ONETIME_AMT\n" + 
"                     ,IP.RECURRENT_AMT\n" + 
"                     ,I.CATEGORY_ID\n" + 
"                     ,I.VAS_GROUP\n" + 
"       ,NVL(i.MIP_BRAND, '1') MIP_BRAND\n" + 
"       ,NVL(i.MIP_SIM_TYPE, 'H') MIP_SIM_TYPE\n" +  
"       ,IDRRV.CONTRACT_PERIOD \n"+
"       ,IDRRV.REBATE_AMT \n"+
"              from W_ITEM I, W_ITEM_DISPLAY_LKUP IDL, W_ITEM_PRICING IP\n" + 
"              , W_ITEM_DTL_RP_RB_VAS IDRRV \n" + 
"              where I.ID = IDL.ITEM_ID\n" + 
"              and I.ID = IDRRV.ID \n" +
"              and IDL.LOCALE = :V_LOCALE ------------------- LOCALE\n" + 
"              and IDL.DISPLAY_TYPE = 'ITEM_SELECT'\n" + 
"              and I.ID = IP.ID\n" + 
"              and TO_DATE(:V_APP_DATE_STR, 'DD/MM/YYYY') between IP.EFF_START_DATE and\n" + 
"                    NVL(IP.EFF_END_DATE, sysdate) ------------------app_date--\n" +
"		    and nvl(decode (I.MIP_BRAND, '9', :mipBrand, I.MIP_BRAND ), '1') = :mipBrand \n" +
"			and nvl(decode (I.MIP_SIM_TYPE, 'X', :mipSimType, I.MIP_SIM_TYPE ), 'H') = :mipSimType \n" +
"              ) ITEM_DISP\n" + 
"            ,( --basket vas group item\n" + 
"              (select item_id\n" +
"            from W_HARD_BUNDLE_VAS_GRP_ASSGN HBVGA\n" +
"            where HBVGA.grp_id = :grpId\n" +
"            and TO_DATE(:V_APP_DATE_STR, 'DD/MM/YYYY') between trunc(HBVGA.eff_start_date) and trunc(nvl(HBVGA.eff_end_date, sysdate))\n" +
"               ) minus\n" + 
"              (select ITEM_ID_A\n" + 
"               from W_MOB_ITEM_EXCLUSIVE_LKUP A\n" + 
"               where ITEM_ID_B in\n" + 
"                     (select ITEM_ID\n" + 
"                      from W_BASKET_ITEM_ASSGN BIA\n" + 
"                      where BIA.BASKET_ID = :V_BASKET_ID ------------- 100000738\n" + 
"                      and BIA.MDO_IND = 'M'\n" + 
"                      and TO_DATE(:V_APP_DATE_STR, 'DD/MM/YYYY') between\n" + 
"                            BIA.EFF_START_DATE and NVL(BIA.EFF_END_DATE, sysdate)) ------------------app_date--\n" + 
"               and ITEM_TYPE_B in ('BVAS', 'RP')\n" + 
"               and ITEM_TYPE_A = 'VAS'\n" + 
"               union\n" + 
"\n" + 
"               select ITEM_ID_b\n" + 
"               from W_MOB_ITEM_EXCLUSIVE_LKUP\n" + 
"               where ITEM_ID_a in\n" + 
"                     (select ITEM_ID\n" + 
"                      from W_BASKET_ITEM_ASSGN BIA\n" + 
"                      where BIA.BASKET_ID = :V_BASKET_ID ------------- 100000738\n" + 
"                      and BIA.MDO_IND = 'M'\n" + 
"                      and TO_DATE(:V_APP_DATE_STR, 'DD/MM/YYYY') between\n" + 
"                            BIA.EFF_START_DATE and NVL(BIA.EFF_END_DATE, sysdate)) ------------------app_date--\n" + 
"               and ITEM_TYPE_a in ('BVAS', 'RP')\n" + 
"               and ITEM_TYPE_b = 'VAS'\n" + 
"\n" + 
"                     )) ITEM_LIST\n" + 
"        where ITEM_DISP.ITEM_ID = ITEM_LIST.ITEM_ID\n" + 
"        order by DISPLAY_SEQ";

		
		ParameterizedRowMapper<VasDetailDTO> mapper = new ParameterizedRowMapper<VasDetailDTO>() {
			public VasDetailDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				VasDetailDTO vasDto = new VasDetailDTO();
				vasDto.setItemHtml(rs.getString("html"));
				vasDto.setItemId(rs.getString("item_id"));
				vasDto.setItemLob(rs.getString("item_lob"));
				vasDto.setItemType(rs.getString("item_type"));
				vasDto.setItemMdoInd(rs.getString("item_mdo_ind"));
				vasDto.setItemHtml2(rs.getString("html2"));
				vasDto.setItemLocale(rs.getString("locale"));
				vasDto.setItemDisplayType(rs.getString("display_type"));
				vasDto.setItemOnetimeAmt(rs.getString("onetime_amt"));
				vasDto.setItemRecurrentAmt(rs.getString("recurrent_amt"));
				vasDto.setStockCount(rs.getString("STOCK_COUNT"));
				vasDto.setPosItemCd(rs.getString("POS_ITEM_CD"));
				vasDto.setCategoryDesc(rs.getString("CATEGORY_DESC"));
				vasDto.setCategoryId(rs.getString("CATEGORY_ID"));
				vasDto.setItemOneTimeType(rs.getString("ONETIME_TYPE"));
				vasDto.setMipBrand(rs.getString("MIP_BRAND"));
				vasDto.setMipSimType(rs.getString("MIP_SIM_TYPE"));
				vasDto.setVasGroup(rs.getString("VAS_GROUP"));
				vasDto.setContractPeriod(rs.getString("CONTRACT_PERIOD"));	
				vasDto.setNetMonthlyAmt(calculateNetMonthlyFee(rs.getString("recurrent_amt"),rs.getString("CONTRACT_PERIOD"),rs.getString("REBATE_AMT")));											
				return vasDto;
			}
		};

		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("V_LOCALE", locale);
		params.addValue("V_BASKET_ID", basketId);
		params.addValue("V_APP_DATE_STR", appDate);
		params.addValue("V_CHANNEL_ID", channelId);
		params.addValue("V_CHANNEL_CD", channelCd);
		params.addValue("mipBrand", mipBrand);
		params.addValue("mipSimType", mipSimType);
		params.addValue("grpId", hardBundleGrpId);
		
		// MIP.P4 modification
		params.addValue("offerNature", offerNature);

		try {
			logger.debug("getHardBundleVasDetailList @ VasDetaillDTO: ");
			vasDetailList = simpleJdbcTemplate.query(SQL, mapper,

			params);

			if ("ITEM_SELECT".equals(displayType)) {
				if (precSelectedList != null) {
					// check exist, if exist set it "D", default tick
					for (int i = 0; i < vasDetailList.size(); i++) {

						if (Utility.isContainString(precSelectedList,
								vasDetailList.get(i).getItemId())) {
							vasDetailList.get(i).setItemMdoInd("D");
						}
					}

				}

			}

		} catch (EmptyResultDataAccessException erdae) {
			logger.info("getVasDetailtList() EmptyResultDataAccessException");
			vasDetailList = null;
		} catch (Exception e) {
			logger.error("Exception caught in getHardBundleVasDetailList()", e);
			throw new DAOException(e.getMessage(), e);
		}
		return vasDetailList;

	}

	// for summary page use
	public List<VasDetailDTO> getVasDetailSelectedList(String locale,
			String orderId) throws DAOException {
		List<VasDetailDTO> vasDetailList = new ArrayList<VasDetailDTO>();

		String SQL = "select idl.item_id,\n"
				+ "       i.lob item_lob,\n"
				+ "       i.type item_type,\n"
				+ "       bia.mdo_ind item_mdo_ind,\n"
				+ "       idl.html,\n"
				+ "       idl.html2,\n"
				+ "       idl.locale,\n"
				+ "       idl.display_type,\n"
				+ "       ip.onetime_amt,\n"
				+ "       ip.recurrent_amt,\n"
				+ "       bia.display_seq,\n"
				+ "       ip.payment_group\n"
				+ "  from (select bwsi.order_id,\n"
				+ "               item_id,\n"
				+ "               display_type,\n"
				+ "               html,\n"
				+ "               html2,\n"
				+ "               locale,\n"
				+ "               basket_id,\n"
				+ "               dense_rank() OVER(PARTITION BY item_id order by item_id asc, display_type desc) rank\n"
				+ "          from w_item_display_lkup widl, bomweb_subscribed_item bwsi\n"
				+ "         where widl.item_id = bwsi.id\n"
				+ "           AND bwsi.order_id = ?\n"
				+ "           and widl.display_type in ('ITEM_SELECT', 'QUOTATION')\n"
				+ "           and widl.locale = ?) idl, -----------\n"
				+ "       w_basket_item_assgn bia,\n"
				+ "       w_item i,\n"
				+ "       w_item_pricing ip,\n"
				+ "       bomweb_order bwo\n"
				+ " where bwo.order_id = idl.order_id\n"
				+ "   and idl.item_id = bia.item_id(+)\n"
				+ "   and idl.basket_id = bia.basket_id(+)\n"
				+ "   and idl.item_id = i.id\n"
				+ "   and i.id = ip.id\n"
				+ "   and trunc(bwo.app_date) between ip.eff_start_date and nvl(ip.eff_end_date, sysdate) \n"
				// + "   and trunc(bwo.app_date) between bia.eff_start_date and nvl(bia.eff_end_date, sysdate) \n"
				+ "   and idl.rank = 1\n" + " order by bia.display_seq";

		ParameterizedRowMapper<VasDetailDTO> mapper = new ParameterizedRowMapper<VasDetailDTO>() {

			// notice the return type with respect to Java 5 covariant return
			// types
			public VasDetailDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				VasDetailDTO vasDto = new VasDetailDTO();
				vasDto.setItemHtml(rs.getString("html"));
				vasDto.setItemId(rs.getString("item_id"));
				vasDto.setItemLob(rs.getString("item_lob"));
				vasDto.setItemType(rs.getString("item_type"));
				vasDto.setItemMdoInd(rs.getString("item_mdo_ind"));
				vasDto.setItemHtml2(rs.getString("html2"));
				vasDto.setItemLocale(rs.getString("locale"));
				vasDto.setItemDisplayType(rs.getString("display_type"));
				vasDto.setItemOnetimeAmt(rs.getString("onetime_amt"));
				vasDto.setItemRecurrentAmt(rs.getString("recurrent_amt"));
				vasDto.setItemPaymentGroup(rs.getString("payment_group"));
				return vasDto;
			}
		};

		try {
			logger.debug("getVasDetailtSelectedList @ VasDetailDTO: " + SQL);
			vasDetailList = simpleJdbcTemplate.query(SQL, mapper, orderId,
					locale);

		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException");
			vasDetailList = null;
		} catch (Exception e) {
			logger.error("Exception caught in getBrandList()", e);
			throw new DAOException(e.getMessage(), e);
		}
		return vasDetailList;

	}

	// for vasdetail
	public String getBasketDisplayTitle(String locale, String basketId)
			throws DAOException {
		String displayTitle = "";

		String SQL = "select html\n" + "  from w_basket_display_lkup\n"
				+ " where locale = ?\n" + "   and basket_id = ?\n"
				+ "   and display_type = 'TITLE'";

		try {
			logger.debug("getBasketDisplayTitle @ VasDetailDAO: " + SQL);
			displayTitle = (String) simpleJdbcTemplate.queryForObject(SQL,
					String.class, locale, basketId);

		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException");
			displayTitle = "";
		} catch (Exception e) {
			logger.error("Exception caught in getBasketDisplayTitle()", e);
			throw new DAOException(e.getMessage(), e);
		}
		return displayTitle;

	}

	// for vasdetail page use, BFEE move out
	//REMOVE_20121005
	/*public List<VasDetailDTO> getBFEEList(String locale, String basketId)
			throws DAOException {
		List<VasDetailDTO> vasDetailList = new ArrayList<VasDetailDTO>();

		String SQL = "SELECT i.ID             item_id,\n"
				+ "       i.LOB            item_lob,\n"
				+ "       i.TYPE           item_type,\n"
				+ "       bia.mdo_ind      item_mdo_ind,\n"
				+ " 		idl.html,\n"
				+ " 		idl.html2,\n"
				+ "		idl.locale,\n"
				+ "       bia.display_seq,\n"
				+ "       idl.display_type,\n"
				+ "       ip.onetime_type,\n"
				+ "       ip.onetime_amt,\n"
				+ "       ip.recurrent_amt\n"
				+ "  FROM w_basket_item_assgn bia,\n"
				+ "       w_item i,\n"
				+ "       (SELECT sysdate eff_date FROM DUAL) d,\n"
				+ "       w_item_display_lkup idl,\n"
				+ "       w_item_pricing ip\n"
				+ " WHERE bia.item_id = i.ID\n"
				+ "   AND idl.item_id = i.ID\n"
				+ "   AND bia.eff_start_date <= d.eff_date\n"
				+ "   AND (bia.eff_end_date >= d.eff_date OR bia.eff_end_date IS NULL)\n"
				+ "   and idl.display_type = 'ITEM_SELECT'\n"
				+ "   and ip.id = i.id\n"
				+ "   AND ip.eff_start_date <= d.eff_date\n"
				+ "   AND (ip.eff_end_date >= d.eff_date OR ip.eff_end_date IS NULL)\n"
				+ "   and i.type = 'BFEE'\n"
				+ "   and idl.locale = ? ---------\n"
				+ "   AND bia.basket_id = ? ---------\n"
				+ " order by bia.display_seq";

		ParameterizedRowMapper<VasDetailDTO> mapper = new ParameterizedRowMapper<VasDetailDTO>() {
			public VasDetailDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				VasDetailDTO vasDto = new VasDetailDTO();
				vasDto.setItemHtml(rs.getString("html"));
				vasDto.setItemId(rs.getString("item_id"));
				vasDto.setItemLob(rs.getString("item_lob"));
				vasDto.setItemType(rs.getString("item_type"));
				vasDto.setItemMdoInd(rs.getString("item_mdo_ind"));
				
				vasDto.setItemHtml2(rs.getString("html2"));
				vasDto.setItemLocale(rs.getString("locale"));
				vasDto.setItemDisplayType(rs.getString("display_type"));
				vasDto.setItemOnetimeAmt(rs.getString("onetime_amt"));
				vasDto.setItemRecurrentAmt(rs.getString("recurrent_amt"));

				return vasDto;
			}
		};

		try {
			logger.debug("getBFEEList: " + SQL);
			vasDetailList = simpleJdbcTemplate.query(SQL, mapper, locale,
					basketId);

		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException");
			vasDetailList = null;
		} catch (Exception e) {
			logger.error("Exception caught in getBFEEList", e);
			throw new DAOException(e.getMessage(), e);
		}
		return vasDetailList;

	}*/

	// only get RPHSRP
	// use appDate to where condition add pos item cd , add AP_INC and MNP_INC	
	// MIP.P4 modification
	public List<VasDetailDTO> getRPHSRPList(String locale, String basketId,
			String appDate, String channelCd, String mipBrand, String mipSimType, String offerNature) throws DAOException {
		
		// MIP.P4 modification
		logger.debug("MIP.P4 modification: pkg_sb_mob_stock.get_stock_count_disp_mip4 (3)");
		Utility.toPrettyJson(offerNature);
		
		List<VasDetailDTO> vasDetailList = new ArrayList<VasDetailDTO>();

		String SQL ="select ITEM_T.ITEM_ID,\n" +
		"       ITEM_T.ITEM_LOB,\n" + 
		"       ITEM_T.ITEM_TYPE,\n" + 
		"       ITEM_T.MIP_BRAND,\n" + 
		"       ITEM_T.MIP_SIM_TYPE,\n" + 
		"       ITEM_T.ITEM_MDO_IND,\n" + 
		"       ITEM_HTML_T.HTML,\n" + 
		"       ITEM_HTML_T.HTML2,\n" + 
		"       ITEM_HTML_T.LOCALE,\n" + 
		"       ITEM_HTML_T.DISPLAY_TYPE,\n" + 
		"       IP_T.ONETIME_AMT,\n" + 
		"       IP_T.RECURRENT_AMT,\n" + 
		"       nvl(IP_T.WAIVABLE, 'N') waivable, " + 
		"       IP_T.CHARGE_ITEM_CD,\n" + 
		"       (select min(IPPA.POS_ITEM_CD)\n" + 
		"          from W_ITEM_PRODUCT_POS_ASSGN IPPA\n" + 
		"         where IPPA.ITEM_ID = ITEM_T.ITEM_ID) POS_ITEM_CD,\n" + 
		"       ITEM_T.DISPLAY_SEQ,\n" + 
		
		"       (select pkg_sb_mob_stock.get_stock_count_disp_mip4('PEND', :channelCd, IPPA.POS_ITEM_CD, :offerNature) STOCK_COUNT "
		
		+ "      from W_ITEM_PRODUCT_POS_ASSGN IPPA "
		+ "      where IPPA.ITEM_ID = ITEM_T.ITEM_ID) STOCK_COUNT\n" + 
		"  from (select I.ID            ITEM_ID,\n" + 
		"               I.LOB           ITEM_LOB,\n" + 
		"               I.TYPE          ITEM_TYPE,\n" + 
		"      			NVL(I.MIP_BRAND, '9') MIP_BRAND,\n" + 
		"       		NVL(I.MIP_SIM_TYPE, 'X') MIP_SIM_TYPE,\n" +  
		"               BIA.MDO_IND     ITEM_MDO_IND,\n" + 
		"               BIA.DISPLAY_SEQ\n" + 
		"          from W_BASKET_ITEM_ASSGN BIA, W_ITEM I\n" + 
		"         where BIA.ITEM_ID = I.ID\n" + 
		"           and BIA.BASKET_ID = :basketId -------basketId\n" + 
		"           and TO_DATE(:appDate, 'DD/MM/YYYY') between BIA.EFF_START_DATE and\n" + 
		"               TRUNC(NVL(BIA.EFF_END_DATE, sysdate))\n" +
	    "		    and nvl(decode (I.MIP_BRAND, '9', :mipBrand, I.MIP_BRAND ), '1') = :mipBrand \n" +
		"			and nvl(decode (I.MIP_SIM_TYPE, 'X', :mipSimType, I.MIP_SIM_TYPE ), 'H') = :mipSimType ) ITEM_T,\n" + 
		"       (select HTML, HTML2, LOCALE, ITEM_ID, DISPLAY_TYPE\n" + 
		"          from W_ITEM_DISPLAY_LKUP\n" + 
		"         where DISPLAY_TYPE = 'ITEM_SELECT') ITEM_HTML_T,\n" + 
		"       (select IP.ID ITEM_ID, IP.ONETIME_AMT, IP.RECURRENT_AMT, IP.WAIVABLE, IP.CHARGE_ITEM_CD\n" + 
		"          from W_ITEM_PRICING IP\n" + 
		"         where TO_DATE(:appDate, 'DD/MM/YYYY') between IP.EFF_START_DATE and\n" + 
		"               TRUNC(NVL(IP.EFF_END_DATE, sysdate))) IP_T\n" + 
		" where ITEM_T.ITEM_ID = ITEM_HTML_T.ITEM_ID(+)\n" + 
		"   and ITEM_T.ITEM_ID = IP_T.ITEM_ID(+)\n" + 
		"   and ITEM_HTML_T.DISPLAY_TYPE = 'ITEM_SELECT'\n" + 

		" and ((ITEM_T.ITEM_TYPE in ('HS', 'RP', 'HSRB', 'VAS', 'BVAS', 'BFEE') and\n" +
		"      ITEM_T.ITEM_MDO_IND in ('M', 'D')) or\n" + 
		"      ITEM_T.ITEM_TYPE in ('AP_INC', 'MNP_INC')) \n" +

		"   and ITEM_HTML_T.LOCALE(+) = :locale ------locale\n" + 
		" order by ITEM_T.DISPLAY_SEQ";


		ParameterizedRowMapper<VasDetailDTO> mapper = new ParameterizedRowMapper<VasDetailDTO>() {

			public VasDetailDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				VasDetailDTO vasDto = new VasDetailDTO();
				vasDto.setItemHtml(rs.getString("html"));
				vasDto.setItemId(rs.getString("item_id"));
				vasDto.setItemLob(rs.getString("item_lob"));
				vasDto.setItemType(rs.getString("item_type"));
				vasDto.setItemMdoInd(rs.getString("item_mdo_ind"));
				vasDto.setItemHtml2(rs.getString("html2"));
				vasDto.setItemLocale(rs.getString("locale"));
				vasDto.setItemDisplayType(rs.getString("display_type"));
				vasDto.setItemOnetimeAmt(rs.getString("onetime_amt"));
				vasDto.setItemRecurrentAmt(rs.getString("recurrent_amt"));
				vasDto.setStockCount(rs.getString("STOCK_COUNT"));
				vasDto.setPosItemCd(rs.getString("POS_ITEM_CD"));
				vasDto.setRpWaivable("Y".equals(rs.getString("waivable")));
				vasDto.setRpWaiveReason(rs.getString("charge_item_cd"));
				vasDto.setMipBrand(rs.getString("MIP_BRAND"));
				vasDto.setMipSimType(rs.getString("MIP_SIM_TYPE"));
				return vasDto;
			}
		};

		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("locale", locale);
		params.addValue("basketId", basketId);
		params.addValue("appDate", appDate);
		params.addValue("channelCd", channelCd);
		params.addValue("mipBrand", mipBrand);
		params.addValue("mipSimType", mipSimType);
		
		// MIP.P4 modification
		params.addValue("offerNature", offerNature);
		
		try {
			logger.debug("getRPHSRPList @ VasDetailDAO: " + SQL);
			logger.info(vasDetailList.size());
			vasDetailList = simpleJdbcTemplate.query(SQL, mapper, params);

		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException");
			vasDetailList = null;
		} catch (Exception e) {
			logger.error("Exception caught in getRPHSRPList()", e);
			throw new DAOException(e.getMessage(), e);
		}
		return vasDetailList;

	}

	//2012-02-16 ok
	public List<VasDetailDTO> getReportUseRPHSRPList(String locale,
			String basketId, String displayType, String orderId)
			throws DAOException {
		List<VasDetailDTO> vasDetailList = new ArrayList<VasDetailDTO>();

		/*
		 * String SQL = "SELECT item_t.item_id,\n" + "       item_t.item_lob,\n"
		 * + "       item_t.item_type,\n" + "       item_t.item_mdo_ind,\n" +
		 * "       item_html_t.html,\n" + "       item_html_t.locale,\n" +
		 * "       item_html_t.display_type,\n" + "       ip_t.onetime_amt,\n" +
		 * "       ip_t.recurrent_amt,\n" + "       item_t.display_seq \n"+
		 * "  FROM (SELECT i.ID        item_id,\n" +
		 * "               i.LOB       item_lob,\n" +
		 * "               i.TYPE      item_type,\n" +
		 * "               bia.mdo_ind item_mdo_ind,\n" +
		 * "                bia.display_seq \n"+ //add
		 * "          FROM w_basket_item_assgn bia,\n" +
		 * "               w_item i,\n" +
		 * "               (SELECT sysdate eff_date FROM DUAL) d\n" +
		 * //TO_DATE('20110106', 'yyyymmdd')
		 * "         WHERE bia.item_id = i.ID\n" +
		 * "           AND bia.basket_id = ? ------------------------------------------\n"
		 * + "           AND bia.eff_start_date <= d.eff_date\n" +
		 * "           AND (bia.eff_end_date >= d.eff_date OR bia.eff_end_date IS NULL)\n"
		 * + "        UNION\n" + "        SELECT ID, LOB, TYPE, 'O', 9999999\n"
		 * + "          FROM w_item\n" + "         WHERE ID IN (SELECT ID\n" +
		 * "                        FROM w_item\n" +
		 * "                       WHERE TYPE = 'VAS'\n" +
		 * "                      MINUS\n" +
		 * "                      SELECT item_id\n" +
		 * "                        FROM w_basket_item_assgn bia,\n" +
		 * "                             (SELECT sysdate eff_date\n" +
		 * //TO_DATE('20110106', 'yyyymmdd')
		 * "                                FROM DUAL) d\n" +
		 * "                       WHERE bia.basket_id = ? -----------------------------\n"
		 * + "                         AND bia.eff_start_date <= d.eff_date\n" +
		 * "                         AND (bia.eff_end_date >= d.eff_date OR\n" +
		 * "                             bia.eff_end_date IS NULL))) item_t,\n"
		 * + "       (SELECT html, locale, item_id, display_type\n" +
		 * "          FROM w_item_display_lkup\n" +
		 * "         WHERE display_type = ?) item_html_t,\n" +
		 * "       --price table\n" +
		 * "       (SELECT ip.ID item_id, ip.onetime_amt, ip.recurrent_amt\n" +
		 * "          FROM w_item_pricing ip,\n" +
		 * "               (SELECT sysdate EFF_DATE FROM DUAL) d\n" +
		 * //TO_DATE('20110111', 'yyyymmdd')
		 * "         WHERE ip.eff_start_date <= d.EFF_DATE\n" +
		 * "           AND (ip.eff_end_date >= d.EFF_DATE OR ip.eff_end_date IS NULL)) ip_t\n"
		 * + " WHERE item_t.item_id = item_html_t.item_id(+)\n" +
		 * "      --join price table\n" +
		 * "   and item_t.item_id = ip_t.item_id(+)\n" +
		 * "   and item_html_t.display_type = ? --only show the ITEM_SELECT type\n"
		 * +
		 * "      and item_t.item_type in ('HS','RP', 'HSRB', 'VAS', 'BVAS','BFEE')\n"
		 * + //"      and item_t.item_mdo_ind ='M'\n" +
		 * "   AND item_html_t.locale(+) = ?-------------\n"+
		 * " order by item_t.display_seq ";
		 */

		String SQL = " select idl.item_id, \n"
				+ " 	       i.lob item_lob,  \n"
				+ " 	       i.type item_type,  \n"
				+ " 	       bia.mdo_ind item_mdo_ind,  \n"
				+ " 	       idl.html,  \n"
				+ " 	       idl.html2,  \n"
				+ " 	       idl.locale,  \n"
				+ " 	       idl.display_type,  \n"
				+ " 	       ip.onetime_amt,  \n"
				+ " 	       ip.recurrent_amt,  \n"
				+ " 	       bia.display_seq  \n"
				+ " 	  from (select bwsi.order_id,  \n"
				+ " 	               item_id,  \n"
				+ " 	               display_type,  \n"
				+ " 	               html,  \n"
				+ " 	               html2,  \n"
				+ " 	               locale \n"
				+ " 	          from w_item_display_lkup widl, bomweb_subscribed_item bwsi  \n"
				+ " 	         where widl.item_id = bwsi.id  \n"
				+ " 	           AND bwsi.order_id = ? -------------- \n"
				+ " 	           and widl.display_type = 'SS_FORM_RP'  \n"
				+ " 	           and widl.locale = ?) idl, ------------ \n"
				+ " 	       w_basket_item_assgn bia,  \n"
				+ " 	       w_item i,  \n"
				+ " 	       w_item_pricing ip,  \n"
				+ " 	       bomweb_order bwo  \n"
				+ " 	 where bwo.order_id = idl.order_id  \n"
				+ " 	   and idl.item_id = bia.item_id(+)  \n"
				+ " 	   and bia.basket_id(+) = ? ------------ \n"
				+ " 	   and idl.item_id = i.id  \n"
				+ " 	   and i.id = ip.id  \n"
				+ "        and trunc(bwo.app_date) between ip.eff_start_date and nvl(ip.eff_end_date, sysdate) \n"
				+ " 	 order by bia.display_seq \n";

		ParameterizedRowMapper<VasDetailDTO> mapper = new ParameterizedRowMapper<VasDetailDTO>() {

			// notice the return type with respect to Java 5 covariant return
			// types
			public VasDetailDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				VasDetailDTO vasDto = new VasDetailDTO();
				vasDto.setItemHtml(rs.getString("html"));
				vasDto.setItemId(rs.getString("item_id"));
				vasDto.setItemLob(rs.getString("item_lob"));
				vasDto.setItemType(rs.getString("item_type"));
				vasDto.setItemMdoInd(rs.getString("item_mdo_ind"));
				
				vasDto.setItemHtml2(rs.getString("html2"));
				vasDto.setItemLocale(rs.getString("locale"));
				vasDto.setItemDisplayType(rs.getString("display_type"));

				vasDto.setItemOnetimeAmt(rs.getString("onetime_amt"));
				vasDto.setItemRecurrentAmt(rs.getString("recurrent_amt"));

				return vasDto;
			}
		};

		try {
			logger.debug("getBrandList @ HandsetModelDTO: " + SQL);
			logger.info(vasDetailList.size());
			vasDetailList = simpleJdbcTemplate.query(SQL, mapper, orderId,
					locale, basketId);

		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException");
			vasDetailList = null;
		} catch (Exception e) {
			logger.error("Exception caught in getBrandList()", e);
			throw new DAOException(e.getMessage(), e);
		}
		return vasDetailList;

	}

	//basket VAS, 2012-02-16 ok
	public List<VasDetailDTO> getReportUseVasDetailtList(String locale,
			String orderId, String basketId) throws DAOException {
		List<VasDetailDTO> vasDetailList = new ArrayList<VasDetailDTO>();

		// edit sql 20110726, not include gif group
		String SQL = " select idl.item_id, \n"
				+ " 	       i.lob item_lob,  \n"
				+ " 	       i.type item_type,  \n"
				+ " 	       bia.mdo_ind item_mdo_ind,  \n"
				+ " 	       idl.html,  \n"
				+ " 	       idl.html2,  \n"
				+ " 	       idl.locale,  \n"
				+ " 	       idl.display_type,  \n"
				+ " 	       ip.onetime_amt,  \n"
				+ " 	       ip.recurrent_amt,  \n"
				+ " 	       bia.display_seq  \n"
				+ " 	  from (select bwsi.order_id,  \n"
				+ " 	               item_id,  \n"
				+ " 	               display_type,  \n"
				+ " 	               html,  \n"
				+ " 	               html2,  \n"
				+ " 	               locale \n"
				+ " 	          from w_item_display_lkup widl, bomweb_subscribed_item bwsi  \n"
				+ " 	         where widl.item_id = bwsi.id  \n"
				+ " 	           AND bwsi.order_id = ? -------------- \n"
				+ " 	           and widl.display_type = 'SS_FORM_VAS'  \n"
				+ " 	           and widl.locale = ? and bwsi.basket_id is not null) idl, ------------ \n"
				+ " 	       w_basket_item_assgn bia,  \n"
				+ " 	       w_item i,  \n"
				+ " 	       w_item_pricing ip,  \n"
				+ " 	       bomweb_order bwo  \n"
				+ " 	 where bwo.order_id = idl.order_id  \n"
				+ " 	   and idl.item_id = bia.item_id(+)  \n"
				+ " 	   and bia.basket_id(+) = ? ------------ \n"
				+ " 	   and idl.item_id = i.id  \n"
				+ " 	   and i.id = ip.id  \n"
				+ "        and trunc(bwo.app_date) between ip.eff_start_date and nvl(ip.eff_end_date, sysdate) \n"
				+ "   and I.ID not in ((select ISGA.ITEM_ID --free gifs group\n"
				+ "                  from W_ITEM_SELECTION_GRP_ASSGN ISGA\n"
				+ "                 where ISGA.GRP_ID = 9999999999))\n"
				+ " 	 order by bia.display_seq \n";

		ParameterizedRowMapper<VasDetailDTO> mapper = new ParameterizedRowMapper<VasDetailDTO>() {

			public VasDetailDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				VasDetailDTO vasDto = new VasDetailDTO();
				vasDto.setItemHtml(rs.getString("html"));
				vasDto.setItemId(rs.getString("item_id"));
				vasDto.setItemLob(rs.getString("item_lob"));
				vasDto.setItemType(rs.getString("item_type"));
				vasDto.setItemMdoInd(rs.getString("item_mdo_ind"));
				
				vasDto.setItemHtml2(rs.getString("html2"));
				vasDto.setItemLocale(rs.getString("locale"));
				vasDto.setItemDisplayType(rs.getString("display_type"));
				vasDto.setItemOnetimeAmt(rs.getString("onetime_amt"));
				vasDto.setItemRecurrentAmt(rs.getString("recurrent_amt"));
				return vasDto;
			}
		};

		try {
			logger.debug("getReportUseVasDetailtList: " + SQL);
			vasDetailList = simpleJdbcTemplate.query(SQL, mapper, orderId,	locale, basketId);

		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException");
			vasDetailList = null;
		} catch (Exception e) {
			logger.error("Exception caught in getReportUseVasDetailtList()", e);
			throw new DAOException(e.getMessage(), e);
		}
		return vasDetailList;

	}



	public List<ExclusiveItemDTO> getExclusiveItemList(List<String> selectedList)
			throws DAOException {

		List<ExclusiveItemDTO> exclusiveItemList = new ArrayList<ExclusiveItemDTO>();
		logger.debug("getExclusiveItemList is called");

		/*
		 * String
		 * SQL=" SELECT item_id_a, item_type_a, item_id_b, item_type_b \n"
		 * +"   FROM W_MOB_ITEM_EXCLUSIVE_LKUP \n" +"  WHERE (1=1)" ;
		 */
		// +"	  AND ITEM_ID_A IN (106000003, 106000004) \n"
		// +"    AND ITEM_ID_B IN (106000003, 106000004) \n";

		String SQL = " SELECT DISTINCT ielm.item_id_a,  \n"
				+ " 	   			ielm.item_type_a,  \n"
				+ " 				ielm.item_id_b, \n"
				+ "                 ielm.item_type_b,  \n"
				+ " 				ia.description item_description_a, \n"
				+ "                 ib.description item_description_b \n"
				+ "            FROM W_MOB_ITEM_EXCLUSIVE_LKUP ielm, w_item ia, w_item ib \n"
				+ "           WHERE ia.ID = ielm.item_id_a \n"
				+ "             AND ib.ID = ielm.item_id_b \n";
		// +"             AND ielm.item_id_a IN (106000003, 106000004) \n"
		// +"             AND ielm.item_id_b IN (106000003, 106000004) \n"

		// "   and item_t.item_id in ('8', '12')"
		String itemASql = "";
		String itemBSql = "";

		if (selectedList != null) {
			if (selectedList.size() > 0) {
				itemASql += "   and ielm.item_id_a in (";
				itemBSql += "   and ielm.item_id_b in (";
				for (int i = 0; i < selectedList.size(); i++) {
					itemASql += selectedList.get(i) + ",";
					itemBSql += selectedList.get(i) + ",";
				}

				itemASql += ")\n";
				itemBSql += ")\n";
				itemASql = itemASql.replace(",)", ")");// replace the last comma
				itemBSql = itemBSql.replace(",)", ")");// replace the last comma
			} else {
				itemASql = "";
				itemBSql = "";
			}

		}

		SQL = SQL + itemASql + itemBSql;
		ParameterizedRowMapper<ExclusiveItemDTO> mapper = new ParameterizedRowMapper<ExclusiveItemDTO>() {

			public ExclusiveItemDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				ExclusiveItemDTO vasDto = new ExclusiveItemDTO();
				vasDto.setItemIdA(rs.getString("item_id_a"));
				vasDto.setItemTypeA(rs.getString("item_type_a"));
				vasDto.setItemIdB(rs.getString("item_id_b"));
				vasDto.setItemTypeB(rs.getString("item_type_b"));
				vasDto.setItemDescriptionA(rs.getString("item_description_a"));
				vasDto.setItemDescriptionB(rs.getString("item_description_b"));

				return vasDto;
			}
		};

		try {
			logger.debug("getExclusiveItemList @ VasDetaillDTO: ");
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

	public List<ProductInfoDTO> getBomProductList(String itemId)
			throws DAOException {
		List<ProductInfoDTO> productList = new ArrayList<ProductInfoDTO>();

		/*
		 * String SQL= "select ioa.item_id,\n" + "       ioa.offer_id,\n" +
		 * "       ioa.offer_sub_id,\n" + "       ioa.offer_type,\n" +
		 * "       ioa.select_qty    OFFER_QTY,\n" + "       iopa.product_id,\n"
		 * + "       iopa.product_type,\n" +
		 * "       iopa.select_qty   PRODUCT_QTY,\n" + "       ippa.compt_id,\n"
		 * + "       ippa.pos_item_cd\n" +
		 * "  from w_item_offer_assgn         ioa,\n" +
		 * "       w_item_offer_product_assgn iopa,\n" +
		 * "       w_item_product_pos_assgn   ippa\n" +
		 * " where ioa.item_id = iopa.item_id\n" +
		 * "   and ioa.item_offer_seq = iopa.item_offer_seq\n" +
		 * "   and ippa.item_id(+) = iopa.item_id\n" +
		 * "   and ippa.item_offer_seq(+) = iopa.item_offer_seq\n" +
		 * "   and ippa.item_product_seq(+) = iopa.item_product_seq\n" +
		 * "   and ioa.item_id  = ?";
		 */

		String SQL = " SELECT ioa.item_id, ioa.offer_id, ioa.offer_sub_id, ioa.offer_type, \n"
				+ "        ioa.select_qty offer_qty, iopa.product_id, iopa.product_type, \n"
				+ "        iopa.select_qty product_qty, ippa.compt_id, ippa.pos_item_cd, \n"
				+ "        feature_display.feature_display, pcm_product.pcm_product, pcm_offer.pcm_offer \n"
				+ "   FROM w_item_offer_assgn ioa, \n"
				+ "        w_item_offer_product_assgn iopa, \n"
				+ "        w_item_product_pos_assgn ippa, \n"
				+ "        (SELECT product_id, html  feature_display \n"
				+ "           FROM w_product_display_lkup pdl \n"
				+ "          WHERE pdl.display_type = 'FEATURE_DISPLAY') feature_display, \n"
				+ "        (SELECT product_id, html pcm_product \n"
				+ "           FROM w_product_display_lkup pdl \n"
				+ "          WHERE pdl.display_type = 'PCM_PRODUCT') pcm_product, \n"
				+ "        (SELECT product_id, html pcm_offer \n"
				+ "           FROM w_product_display_lkup pdl \n"
				+ "          WHERE pdl.display_type = 'PCM_OFFER') pcm_offer \n"
				+ "  WHERE ioa.item_id = iopa.item_id \n"
				+ "    AND iopa.product_id = feature_display.product_id(+) --FEATURE_DISPLAY out join \n"
				+ "    AND iopa.product_id = pcm_product.product_id(+)      --PCM_PRODUCT out join \n"
				+ "    AND ioa.offer_id = pcm_offer.product_id(+)              --PCM_OFFERout join \n"
				+ "    AND ioa.item_offer_seq = iopa.item_offer_seq \n"
				+ "    AND ippa.item_id(+) = iopa.item_id \n"
				+ "    AND ippa.item_offer_seq(+) = iopa.item_offer_seq \n"
				+ "    AND ippa.item_product_seq(+) = iopa.item_product_seq \n"
				+ "    AND ioa.item_id = ? \n";

		ParameterizedRowMapper<ProductInfoDTO> mapper = new ParameterizedRowMapper<ProductInfoDTO>() {

			public ProductInfoDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				ProductInfoDTO product = new ProductInfoDTO();

				product.setOfferId(rs.getString("offer_id"));
				product.setOfferType(rs.getString("offer_type"));
				product.setProdId(rs.getString("product_id"));
				product.setProdType(rs.getString("product_type"));
				product.setPcmProduct(rs.getString("pcm_product"));
				product.setFeatureDisplay(rs.getString("feature_display"));
				product.setPcmOffer(rs.getString("pcm_offer"));
				product.setPosItemCode(rs.getString("pos_item_cd"));
				product.setIoInd("I");
				return product;
			}
		};

		try {
			logger.debug("getBomProductList @ VasDetailDAO: " + SQL);

			productList = simpleJdbcTemplate.query(SQL, mapper, itemId);

		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException");
			productList = null;
		} catch (Exception e) {
			logger.error("Exception caught in getBomProductList()", e);
			throw new DAOException(e.getMessage(), e);
		}
		return productList;

	}

	public List<VasAttbComponentDTO> getVasAttbComponentList(
			List<String> vasList, String channelId) throws DAOException {

		String subSQL = new String();
		if (vasList.size()==0){
			return null;
		}

		for (int i = 0; vasList!=null && i < vasList.size(); i++) {
			subSQL += vasList.get(i) + ",";
		}
		subSQL = (subSQL + "END").replace(",END", " ");
		// subSQL = subSQL.replace(",)", ")");
		//by herbert 20111110 remove all unwanted logger
		logger.debug("subSQL: "+ subSQL);
		//System.out.println("subSQL: "+ subSQL);
		List<VasAttbComponentDTO> vasAttbComponentList = new ArrayList<VasAttbComponentDTO>();

		/*StringBuffer SQL = new StringBuffer();
		SQL.append(
				"select T_AI.ATTB_ID ATTB_ID, NVL(DL.DESCRIPTION, T_AI.ATTB_DESC) ATTB_DESC,")
				.append("T_AI.INPUT_METHOD, T_AI.INPUT_FORMAT, T_AI.MIN_LENGTH, T_AI.MAX_LENGTH,")
				.append("T_AI.DEFAULT_VALUE, T_AI.VISUAL_IND, T_AI.ATTB_VALUE, NVL(AID.ATTB_VALUE_DESC, T_AI.ATTB_VALUE_DESC) ATTB_VALUE_DESC")
				.append(" FROM W_DISPLAY_LKUP DL, W_ATTB_INFO_DIC AID,")
				.append("(select AL.ATTB_ID ATTB_ID, NVL(DL.DESCRIPTION, AL.DESCRIPTION) ATTB_DESC,")
				.append("NVL(CAO.INPUT_METHOD, AL.INPUT_METHOD) INPUT_METHOD, INPUT_FORMAT, MIN_LENGTH, MAX_LENGTH,")
				.append("NVL(CAO.DEFAULT_VALUE, AL.DEFAULT_VALUE) DEFAULT_VALUE, NVL(CAO.VISUAL_IND, AL.VISUAL_IND) VISUAL_IND,")
				.append("AID.ATTB_INFO_KEY, AID.ATTB_VALUE, AID.ATTB_VALUE_DESC")
				.append(" from W_ATTB_LKUP AL, W_CHANNEL_ATTB_OVERIDE CAO, W_ATTB_INFO_DIC AID, W_DISPLAY_LKUP DL, W_PRODUCT_ATTB_ASSGN PAA")
				.append(" where PAA.PRODUCT_ID in (select PRODUCT_ID from W_ITEM_OFFER_ASSGN IOA, W_ITEM_OFFER_PRODUCT_ASSGN IOPA")
				.append(" where IOA.ITEM_ID = IOPA.ITEM_ID")
				.append(" and IOA.ITEM_OFFER_SEQ = IOPA.ITEM_OFFER_SEQ")
				.append(" and IOA.ITEM_ID in (" + subSQL + ")")
				.append(" AND PAA.ATTB_ID = AL.ATTB_ID")
				.append(" AND TO_NUMBER(AL.ATTB_ID) = DL.id(+)")
				.append(" AND DL.type(+) = 'ATTB_DESC'")
				.append(" and DL.Locale(+) = 'en'")
				.append(" AND AL.ATTB_ID = CAO.ATTB_ID(+)")
				.append(" AND CAO.CHANNEL_ID(+) = ?")
				.append(" and AL.ATTB_INFO_KEY = AID.ATTB_INFO_KEY(+)")
				.append(" and AID.LOCALE(+) = 'en') T_AI")
				.append(" where TO_NUMBER(T_AI.ATTB_ID) = DL.id(+)")
				.append(" and DL.type(+) = 'ATTB_DESC'")
				.append(" and DL.LOCALE(+) = 'en'")
				.append(" and T_AI.ATTB_INFO_KEY = AID.ATTB_INFO_KEY(+)")
				.append(" AND T_AI.ATTB_VALUE = AID.ATTB_VALUE(+)")
				.append(" and AID.LOCALE(+) = 'en'") //
				.append(" AND T_AI.DEFAULT_VALUE IS NULL");*/
		
		String SQL=
			"select T_AI.ATTB_ID ATTB_ID,\n" +
			"       NVL(DL.DESCRIPTION, T_AI.ATTB_DESC) ATTB_DESC,\n" + 
			"       T_AI.INPUT_METHOD,\n" + 
			"       T_AI.INPUT_FORMAT,\n" + 
			"       T_AI.MIN_LENGTH,\n" + 
			"       T_AI.MAX_LENGTH,\n" + 
			"       T_AI.DEFAULT_VALUE,\n" + 
			"       T_AI.VISUAL_IND,\n" + 
			"       T_AI.ATTB_VALUE,\n" + 
			"       NVL(AID.ATTB_VALUE_DESC, T_AI.ATTB_VALUE_DESC) ATTB_VALUE_DESC\n" + 
			"  from W_DISPLAY_LKUP DL,\n" + 
			"       W_ATTB_INFO_DIC AID,\n" + 
			"       (select AL.ATTB_ID ATTB_ID,\n" + 
			"               NVL(DL.DESCRIPTION, AL.DESCRIPTION) ATTB_DESC,\n" + 
			"               NVL(CAO.INPUT_METHOD, AL.INPUT_METHOD) INPUT_METHOD,\n" + 
			"               INPUT_FORMAT,\n" + 
			"               MIN_LENGTH,\n" + 
			"               MAX_LENGTH,\n" + 
			"               NVL(CAO.DEFAULT_VALUE, AL.DEFAULT_VALUE) DEFAULT_VALUE,\n" + 
			"               NVL(CAO.VISUAL_IND, AL.VISUAL_IND) VISUAL_IND,\n" + 
			"               AID.ATTB_INFO_KEY,\n" + 
			"               AID.ATTB_VALUE,\n" + 
			"               AID.ATTB_VALUE_DESC\n" + 
			"          from W_ATTB_LKUP            AL,\n" + 
			"               W_CHANNEL_ATTB_OVERIDE CAO,\n" + 
			"               W_ATTB_INFO_DIC        AID,\n" + 
			"               W_DISPLAY_LKUP         DL,\n" + 
			"               W_PRODUCT_ATTB_ASSGN   PAA\n" + 
			"         where PAA.PRODUCT_ID in\n" + 
			"               (select PRODUCT_ID\n" + 
			"                  from W_ITEM_OFFER_ASSGN         IOA,\n" + 
			"                       W_ITEM_OFFER_PRODUCT_ASSGN IOPA\n" + 
			"                 where IOA.ITEM_ID = IOPA.ITEM_ID\n" + 
			"                   and IOA.ITEM_OFFER_SEQ = IOPA.ITEM_OFFER_SEQ\n" + 
			"                   and IOA.ITEM_ID in (" + subSQL + "))\n" + 
			"           and PAA.ATTB_ID = AL.ATTB_ID\n" + 
			"           and TO_NUMBER(AL.ATTB_ID) = DL.ID(+)\n" + 
			"           and DL.TYPE(+) = 'ATTB_DESC'\n" + 
			"           and DL.LOCALE(+) = 'en'\n" + 
			"           and AL.ATTB_ID = CAO.ATTB_ID(+)\n" + 
			"           and CAO.CHANNEL_ID(+) = ?\n" + 
			"           and AL.ATTB_INFO_KEY = AID.ATTB_INFO_KEY(+)\n" + 
			"           and nvl(AL.VISUAL_IND, 'Y') != 'N'\n" + 
			"           and AID.LOCALE(+) = 'en') T_AI\n" + 
			" where TO_NUMBER(T_AI.ATTB_ID) = DL.ID(+)\n" + 
			"   and DL.TYPE(+) = 'ATTB_DESC'\n" + 
			"   and DL.LOCALE(+) = 'en'\n" + 
			"   and T_AI.ATTB_INFO_KEY = AID.ATTB_INFO_KEY(+)\n" + 
			"   and T_AI.ATTB_VALUE = AID.ATTB_VALUE(+)\n" + 
			"   and AID.LOCALE(+) = 'en'\n" + 
			"   and T_AI.DEFAULT_VALUE is null\n" + 
			"union\n" + 
			"select T_AI.ATTB_ID ATTB_ID,\n" + 
			"       NVL(DL.DESCRIPTION, T_AI.ATTB_DESC) ATTB_DESC,\n" + 
			"       T_AI.INPUT_METHOD,\n" + 
			"       T_AI.INPUT_FORMAT,\n" + 
			"       T_AI.MIN_LENGTH,\n" + 
			"       T_AI.MAX_LENGTH,\n" + 
			"       T_AI.DEFAULT_VALUE,\n" + 
			"       T_AI.VISUAL_IND,\n" + 
			"       T_AI.ATTB_VALUE,\n" + 
			"       NVL(AID.ATTB_VALUE_DESC, T_AI.ATTB_VALUE_DESC) ATTB_VALUE_DESC\n" + 
			"  from W_DISPLAY_LKUP DL,\n" + 
			"       W_ATTB_INFO_DIC AID,\n" + 
			"       (select AL.ATTB_ID ATTB_ID,\n" + 
			"               NVL(DL.DESCRIPTION, AL.DESCRIPTION) ATTB_DESC,\n" + 
			"               NVL(CAO.INPUT_METHOD, AL.INPUT_METHOD) INPUT_METHOD,\n" + 
			"               INPUT_FORMAT,\n" + 
			"               MIN_LENGTH,\n" + 
			"               MAX_LENGTH,\n" + 
			"               NVL(CAO.DEFAULT_VALUE, AL.DEFAULT_VALUE) DEFAULT_VALUE,\n" + 
			"               NVL(CAO.VISUAL_IND, AL.VISUAL_IND) VISUAL_IND,\n" + 
			"               AID.ATTB_INFO_KEY,\n" + 
			"               AID.ATTB_VALUE,\n" + 
			"               AID.ATTB_VALUE_DESC\n" + 
			"          from W_ATTB_LKUP            AL,\n" + 
			"               W_CHANNEL_ATTB_OVERIDE CAO,\n" + 
			"               W_ATTB_INFO_DIC        AID,\n" + 
			"               W_DISPLAY_LKUP         DL,\n" + 
			"               W_ITEM_ATTB_ASSGN      PAAI\n" + 
			"         where PAAI.ITEM_ID in (" + subSQL + ")\n" + 
			"           and PAAI.ATTB_ID = AL.ATTB_ID\n" + 
			"           and TO_NUMBER(AL.ATTB_ID) = DL.ID(+)\n" + 
			"           and DL.TYPE(+) = 'ATTB_DESC'\n" + 
			"           and DL.LOCALE(+) = 'en'\n" + 
			"           and AL.ATTB_ID = CAO.ATTB_ID(+)\n" + 
			"           and CAO.CHANNEL_ID(+) = ?\n" + 
			"           and AL.ATTB_INFO_KEY = AID.ATTB_INFO_KEY(+)\n" + 
			"           and nvl(AL.VISUAL_IND, 'Y') != 'N'\n" + 
			"           and AID.LOCALE(+) = 'en') T_AI\n" + 
			" where TO_NUMBER(T_AI.ATTB_ID) = DL.ID(+)\n" + 
			"   and DL.TYPE(+) = 'ATTB_DESC'\n" + 
			"   and DL.LOCALE(+) = 'en'\n" + 
			"   and T_AI.ATTB_INFO_KEY = AID.ATTB_INFO_KEY(+)\n" + 
			"   and T_AI.ATTB_VALUE = AID.ATTB_VALUE(+)\n" + 
			"   and AID.LOCALE(+) = 'en'\n" + 
			"   and T_AI.DEFAULT_VALUE is null order by ATTB_ID";


		ParameterizedRowMapper<VasAttbComponentDTO> mapper = new ParameterizedRowMapper<VasAttbComponentDTO>() {
			public VasAttbComponentDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				VasAttbComponentDTO vasAttbComponent = new VasAttbComponentDTO();
				vasAttbComponent.setCompAttbId(rs.getString("ATTB_ID"));
				vasAttbComponent.setCompAttbDesc(rs.getString("ATTB_DESC"));
				vasAttbComponent.setCompAttbValue(rs.getString("ATTB_VALUE"));
				vasAttbComponent.setCompAttbValueDesc(rs.getString("ATTB_VALUE_DESC"));
				vasAttbComponent.setInputMethod(rs.getString("INPUT_METHOD"));
				vasAttbComponent.setInputFormat(rs.getString("INPUT_FORMAT"));
				vasAttbComponent.setMaxLength(rs.getInt("MAX_LENGTH"));
				vasAttbComponent.setMinLength(rs.getInt("MIN_LENGTH"));

				return vasAttbComponent;
			}
		};

		try {
			logger.debug("getVasAttbComponent list: " + SQL);

			vasAttbComponentList = simpleJdbcTemplate.query(SQL,
					mapper, channelId, channelId);

		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException");
			vasAttbComponentList = null;
		} catch (Exception e) {
			logger.error("Exception caught in getVasAttbComponentList()", e);
			throw new DAOException(e.getMessage(), e);
		}
		return vasAttbComponentList;
	}

	public List<ProductInfoDTO> getBomProductNonDisplayItemList(String basketId)
			throws DAOException {
		List<ProductInfoDTO> productList = new ArrayList<ProductInfoDTO>();

		String SQL = "SELECT ioa.item_id, ioa.offer_id, ioa.offer_sub_id, ioa.offer_type,\n"
				+ "            ioa.select_qty offer_qty, iopa.product_id, iopa.product_type,\n"
				+ "            iopa.select_qty product_qty, ippa.compt_id, ippa.pos_item_cd,\n"
				+ "            feature_display.feature_display, pcm_product.pcm_product, pcm_offer.pcm_offer\n"
				+ "       FROM w_item_offer_assgn ioa,\n"
				+ "            w_item_offer_product_assgn iopa,\n"
				+ "            w_item_product_pos_assgn ippa,\n"
				+ "            (SELECT product_id, html feature_display\n"
				+ "               FROM w_product_display_lkup pdl\n"
				+ "              WHERE pdl.display_type = 'FEATURE_DISPLAY') feature_display,\n"
				+ "            (SELECT product_id, html pcm_product\n"
				+ "               FROM w_product_display_lkup pdl\n"
				+ "              WHERE pdl.display_type = 'PCM_PRODUCT') pcm_product,\n"
				+ "            (SELECT product_id, html pcm_offer\n"
				+ "               FROM w_product_display_lkup pdl\n"
				+ "              WHERE pdl.display_type = 'PCM_OFFER') pcm_offer\n"
				+ "      WHERE ioa.item_id = iopa.item_id\n"
				+ "        AND iopa.product_id = feature_display.product_id(+) --FEATURE_DISPLAY out join\n"
				+ "        AND iopa.product_id = pcm_product.product_id(+)      --PCM_PRODUCT out join\n"
				+ "        AND ioa.offer_id = pcm_offer.product_id(+)              --PCM_OFFERout join\n"
				+ "        AND ioa.item_offer_seq = iopa.item_offer_seq\n"
				+ "        AND ippa.item_id(+) = iopa.item_id\n"
				+ "        AND ippa.item_offer_seq(+) = iopa.item_offer_seq\n"
				+ "        AND ippa.item_product_seq(+) = iopa.item_product_seq\n"
				+ "        AND ioa.item_id  in (select item_id from w_basket_item_assgn bia\n"
				+ "        where bia.Item_Id not in (select idl.item_id from w_item_display_lkup idl where idl.display_type='ITEM_SELECT')\n"
				+ "        and bia.Basket_Id=?)";

		ParameterizedRowMapper<ProductInfoDTO> mapper = new ParameterizedRowMapper<ProductInfoDTO>() {

			public ProductInfoDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				ProductInfoDTO product = new ProductInfoDTO();

				product.setOfferId(rs.getString("offer_id"));
				product.setOfferType(rs.getString("offer_type"));
				product.setProdId(rs.getString("product_id"));
				product.setProdType(rs.getString("product_type"));
				product.setPcmProduct(rs.getString("pcm_product"));
				product.setFeatureDisplay(rs.getString("feature_display"));
				product.setPcmOffer(rs.getString("pcm_offer"));
				product.setPosItemCode(rs.getString("pos_item_cd"));
				product.setIoInd("I");
				product.setItemId(rs.getString("item_id"));
				return product;
			}
		};

		try {
			logger.debug("getBomProductNonDisplayItemList @ VasDetailDAO: "
					+ SQL);

			productList = simpleJdbcTemplate.query(SQL, mapper, basketId);

		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException");
			productList = null;
		} catch (Exception e) {
			logger.error("Exception caught in getBomProductList()", e);
			throw new DAOException(e.getMessage(), e);
		}
		return productList;

	}

	// add by 20110726, for report gif section use
	public List<VasDetailDTO> getReportUseFreeGifsDetailtList(String locale,
			String orderId, String basketId) throws DAOException {
		List<VasDetailDTO> vasDetailList = new ArrayList<VasDetailDTO>();

		String SQL = "select IDL.ITEM_ID,\n"
				+ "       I.LOB            ITEM_LOB,\n"
				+ "       I.TYPE           ITEM_TYPE,\n"
				+ "       BIA.MDO_IND      ITEM_MDO_IND,\n"
				+ "       IDL.HTML,\n"
				+ "       IDL.HTML2,\n"
				+ "       IDL.LOCALE,\n"
				+ "       IDL.DISPLAY_TYPE,\n"
				+ "       IP.ONETIME_AMT,\n"
				+ "       IP.RECURRENT_AMT,\n"
				+ "       BIA.DISPLAY_SEQ\n"
				+ "  from (select BWSI.ORDER_ID, ITEM_ID, DISPLAY_TYPE, HTML, HTML2, LOCALE\n"
				+ "          from w_item_display_lkup WIDL, BOMWEB_SUBSCRIBED_ITEM BWSI\n"
				+ "         where WIDL.ITEM_ID = BWSI.ID\n"
				+ "           and BWSI.ORDER_ID = ? --------------\n"
				+ "           and WIDL.DISPLAY_TYPE = 'SS_FORM_VAS'\n"
				+ "           and WIDL.LOCALE = ?) IDL, ------------\n"
				+ "       W_BASKET_ITEM_ASSGN BIA,\n"
				+ "       W_ITEM I,\n"
				+ "       W_ITEM_PRICING IP,\n"
				+ "       BOMWEB_ORDER BWO\n"
				+ " where BWO.ORDER_ID = IDL.ORDER_ID\n"
				+ "   and IDL.ITEM_ID = BIA.ITEM_ID(+)\n"
				+ "   and BIA.BASKET_ID(+) = ? ------------\n"
				+ "   and IDL.ITEM_ID = I.ID\n"
				+ "   and I.ID = IP.ID\n"
				+ "   and TRUNC(BWO.APP_DATE) BETWEEN IP.EFF_START_DATE AND NVL(IP.EFF_END_DATE, SYSDATE) \n"
				+ "   and I.ID in ((select ISGA.ITEM_ID --free gifs group\n"
				+ "                  from W_ITEM_SELECTION_GRP_ASSGN ISGA\n"
				+ "                 where ISGA.GRP_ID = 9999999999))\n"
				+ " order by BIA.DISPLAY_SEQ";

		ParameterizedRowMapper<VasDetailDTO> mapper = new ParameterizedRowMapper<VasDetailDTO>() {
			public VasDetailDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				VasDetailDTO vasDto = new VasDetailDTO();
				vasDto.setItemHtml(rs.getString("html"));
				vasDto.setItemId(rs.getString("item_id"));
				vasDto.setItemLob(rs.getString("item_lob"));
				vasDto.setItemType(rs.getString("item_type"));
				vasDto.setItemMdoInd(rs.getString("item_mdo_ind"));
				
				vasDto.setItemHtml2(rs.getString("html2"));
				vasDto.setItemLocale(rs.getString("locale"));
				vasDto.setItemDisplayType(rs.getString("display_type"));
				vasDto.setItemOnetimeAmt(rs.getString("onetime_amt"));
				vasDto.setItemRecurrentAmt(rs.getString("recurrent_amt"));

				return vasDto;
			}
		};

		try {
			logger.debug("getReportUseFreeGifsDetailtList: " + SQL);
			vasDetailList = simpleJdbcTemplate.query(SQL, mapper, orderId,
					locale, basketId);

		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException");
			vasDetailList = null;
		} catch (Exception e) {
			logger.error("Exception caught in getReportUseFreeGifsDetailtList()", e);
			throw new DAOException(e.getMessage(), e);
		}
		return vasDetailList;

	}
	
	//Optional VAS
	public List<VasDetailDTO> getReportUseVasOptionalDetailtList(String locale,
			String orderId, String basketId) throws DAOException {
		List<VasDetailDTO> vasDetailList = new ArrayList<VasDetailDTO>();

		// edit sql 20110726, not include gif group
		String SQL = " select idl.item_id, \n"
				+ " 	       i.lob item_lob,  \n"
				+ " 	       i.type item_type,  \n"
				+ " 	       bia.mdo_ind item_mdo_ind,  \n"
				+ " 	       idl.html,  \n"
				+ " 	       idl.html2,  \n"
				+ " 	       idl.locale,  \n"
				+ " 	       idl.display_type,  \n"
				+ " 	       ip.onetime_amt,  \n"
				+ " 	       ip.recurrent_amt,  \n"
				+ " 	       bia.display_seq  \n"
				+ " 	  from (select bwsi.order_id,  \n"
				+ " 	               item_id,  \n"
				+ " 	               display_type,  \n"
				+ " 	               html,  \n"
				+ " 	               html2,  \n"
				+ " 	               locale \n"
				+ " 	          from w_item_display_lkup widl, bomweb_subscribed_item bwsi  \n"
				+ " 	         where widl.item_id = bwsi.id  \n"
				+ " 	           AND bwsi.order_id = ? -------------- \n"
				+ " 	           and widl.display_type = 'SS_FORM_VAS'  \n"
				+ " 	           and widl.locale = ? and bwsi.basket_id is null) idl, ------------ \n"
				+ " 	       w_basket_item_assgn bia,  \n"
				+ " 	       w_item i,  \n"
				+ " 	       w_item_pricing ip,  \n"
				+ " 	       bomweb_order bwo  \n"
				+ " 	 where bwo.order_id = idl.order_id  \n"
				+ " 	   and idl.item_id = bia.item_id(+)  \n"
				+ " 	   and bia.basket_id(+) = ? ------------ \n"
				+ " 	   and idl.item_id = i.id  \n"
				+ " 	   and i.id = ip.id  \n"
				+ "        and trunc(bwo.app_date) between ip.eff_start_date and nvl(ip.eff_end_date, sysdate) \n"
				+ "   and I.ID not in ((select ISGA.ITEM_ID --free gifs group\n"
				+ "                  from W_ITEM_SELECTION_GRP_ASSGN ISGA\n"
				+ "                 where ISGA.GRP_ID = 9999999999))\n"
				+ " 	 order by bia.display_seq \n";

		ParameterizedRowMapper<VasDetailDTO> mapper = new ParameterizedRowMapper<VasDetailDTO>() {

			public VasDetailDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				VasDetailDTO vasDto = new VasDetailDTO();
				vasDto.setItemHtml(rs.getString("html"));
				vasDto.setItemId(rs.getString("item_id"));
				vasDto.setItemLob(rs.getString("item_lob"));
				vasDto.setItemType(rs.getString("item_type"));
				vasDto.setItemMdoInd(rs.getString("item_mdo_ind"));
				vasDto.setItemHtml2(rs.getString("html2"));
				vasDto.setItemLocale(rs.getString("locale"));
				vasDto.setItemDisplayType(rs.getString("display_type"));
				vasDto.setItemOnetimeAmt(rs.getString("onetime_amt"));
				vasDto.setItemRecurrentAmt(rs.getString("recurrent_amt"));
				return vasDto;
			}
		};

		try {
			logger.debug("getReportUseVasOptionalDetailtList: " + SQL);
			vasDetailList = simpleJdbcTemplate.query(SQL, mapper, orderId,	locale, basketId);

		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException");
			vasDetailList = null;
		} catch (Exception e) {
			logger.error("Exception caught in getReportUseVasOptionalDetailtList()", e);
			throw new DAOException(e.getMessage(), e);
		}
		return vasDetailList;

	}
	
	//add appDate 20120216
	public List<String> getBundleVASList(String basketId, String appDate) throws DAOException {
		logger.debug("getExcludedBundleVASList is called");
		List<String> bundleVASList = new ArrayList();
		/*String SQL = "SELECT i.ID as ID FROM w_basket_item_assgn bia, w_item i, (SELECT sysdate eff_da"
				+ "te FROM DUAL) d  WHERE bia.item_id = i.ID and bia.basket_id = ? AND i.type = 'BV"
				+ "AS' AND bia.eff_start_date <= d.eff_date AND (bia.eff_end_date >= d.eff_date OR "
				+ "bia.eff_end_date IS NULL)";*/
		String SQL=
			"select I.ID as ID\n" +
			"  from W_BASKET_ITEM_ASSGN BIA, W_ITEM I\n" + 
			" where BIA.ITEM_ID = I.ID\n" + 
			"   and BIA.BASKET_ID = ? ----basketId--100000738\n" + 
			"   and I.TYPE = 'BVAS'\n" + 
			"   and TO_DATE(?, 'DD/MM/YYYY') between BIA.EFF_START_DATE and\n" + 
			"       NVL(BIA.EFF_END_DATE, sysdate) ----appDate--";


		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {

			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				String brand = new String();
				brand = rs.getString("ID");
				return brand;
			}
		};
		try {
			bundleVASList = simpleJdbcTemplate.query(SQL, mapper,
					basketId, appDate );
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("getBundleVASList() EmptyResultDataAccessException");
			bundleVASList = null;
		} catch (Exception e) {
			logger.error("Exception caught in getBundleVASList()", e);
			throw new DAOException(e.getMessage(), e);
		}
		return bundleVASList;
	}
	
	// MIP.P4 modification
	public List<SimDTO> getSimSelection(String locale, String appDate, String basketId, String orderId, String channelCd, String mipSimType, String mipBrand, String ivOfferNature) throws DAOException {
		// MIP.P4 modification
		logger.debug("MIP.P4 modification: pkg_sb_mob_stock.get_stock_count_mip4 (1)");
		Utility.toPrettyJson(ivOfferNature);
		
		logger.info("locale " + locale);
		logger.info("appDate " + appDate);
		logger.info("basketId " + basketId);		
		logger.info("orderId " + orderId);		
		logger.info("channelCd " + channelCd);
		logger.info("mipSimType " + mipSimType);
		logger.info("ivOfferNature " + ivOfferNature);
		
		String sql = "select i.id item_id, " + 
					"	  NVL(i.mip_sim_type, 'H') MIP_SIM_TYPE, " +
					" 	  ippa.pos_item_cd item_code, " + 
					"     idl.html item_display, " + 
					"     nvl(ip.onetime_amt, 0) charge, " +  
					"     nvl(IP.WAIVABLE, 'N') waivable, " + 
					"     ip.charge_item_cd, " + 
					"     bsc.sim_type SIM_TYPE, " + 
					"     bsc.HS_EXTRA_FUNCTION extra_function, " + 
					
					"     pkg_sb_mob_stock.get_stock_count_mip4('PEND', :channelCd, IPPA.POS_ITEM_CD,:ivOfferNature) STOCK_COUNT " +   
			
					" from w_basket_item_assgn bia " + 
					" join w_item i on bia.item_id = i.id " + 
					" left join w_item_pricing ip on i.id = ip.id " + 
					" join w_item_display_lkup idl on idl.item_id = i.id " + 
					" join w_item_product_pos_assgn ippa on ippa.item_id = i.id " + 
					" join bomweb_stock_catalog bsc on bsc.item_code = ippa.pos_item_cd " + 
					" where basket_id = :basketId " + 
					" and i.type = 'SIM' " + 
					" and TO_DATE(:appDate, 'DD/MM/YYYY') between bia.eff_start_date and nvl(bia.eff_end_date, TO_DATE(:appDate, 'DD/MM/YYYY')) " +
					" and TO_DATE(:appDate, 'DD/MM/YYYY') between ip.eff_start_date and nvl(ip.eff_end_date, TO_DATE(:appDate, 'DD/MM/YYYY')) " + 
					" and idl.locale = :locale " + 
					" and idl.display_type = 'ITEM_SELECT' " +
					" and nvl(decode (I.MIP_SIM_TYPE, 'X', :mipSimType, I.MIP_SIM_TYPE ), 'H') = :mipSimType \n" +
					" and nvl(decode (I.MIP_BRAND, '9', :mipBrand, I.MIP_BRAND ), '1') = :mipBrand \n" +
					" order by BIA.DISPLAY_SEQ";
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("appDate", appDate);
		params.addValue("basketId", basketId);
		params.addValue("locale", locale);
		params.addValue("channelCd", channelCd);
		params.addValue("mipSimType", mipSimType);
		params.addValue("mipBrand", mipBrand);
		
		// MIP.P4 modification
		params.addValue("ivOfferNature", ivOfferNature);
		
		System.out.println("getSimSelection " + sql);
		
		ParameterizedRowMapper<SimDTO> mapper = new ParameterizedRowMapper<SimDTO>() {

			public SimDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				SimDTO simDto = new SimDTO();
				simDto.setItemId(rs.getString("item_id"));
				simDto.setItemCode(rs.getString("item_code"));
				simDto.setItemDisplay(rs.getString("item_display"));
				simDto.setCharge(rs.getDouble("charge"));
				simDto.setWaivable("Y".equals(rs.getString("waivable")));
				simDto.setChargeItemCd(rs.getString("charge_item_cd"));
				simDto.setSimType(rs.getString("sim_type"));
				simDto.setExtraFunction(rs.getInt("extra_function"));
				simDto.setStockCount(rs.getInt("STOCK_COUNT"));
				simDto.setMipSimType(rs.getString("MIP_SIM_TYPE"));
				
				return simDto;
			}
		};
		
		try {
			logger.debug("getSimTypeSelection @ VasDetailDAO: " + sql);
			return simpleJdbcTemplate.query(sql, mapper, params);

		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException",erdae);
		} catch (Exception e) {
			logger.error("Exception caught in getSimTypeSelection()", e);
			throw new DAOException(e.getMessage(), e);
		}
		
		return null;
	}
	
	// MIP.P4 modification
	public List<VasDetailDTO> getSimTypeSelection(String locale, String appDate, String basketId, String orderId, String channelCd, String mipSimType, String mipBrand, String offerNature) throws DAOException {

		//DENNIS MIP3
		
		/*String sql = "select BIA.ITEM_ID, BIA.DISPLAY_SEQ, BIA.MDO_IND, IDL.HTML " +
					 " , (select A.HS_EXTRA_FUNCTION " +
					 " from BOMWEB_STOCK_CATALOG A, " +
					 " W_ITEM_PRODUCT_POS_ASSGN IPPA WHERE A.ITEM_CODE = IPPA.POS_ITEM_CD " +
					 " AND IPPA.ITEM_ID = BIA.ITEM_ID) SIM_EXTRA_FUNCTION " +
					 " , (select A.SIM_TYPE " +
					 " from BOMWEB_STOCK_CATALOG A, " +
					 " W_ITEM_PRODUCT_POS_ASSGN IPPA WHERE A.ITEM_CODE = IPPA.POS_ITEM_CD " +
					 " AND IPPA.ITEM_ID = BIA.ITEM_ID) SIM_TYPE, " +
					 "(SELECT a.item_code FROM bomweb_stock_catalog a left join w_item_product_pos_assgn ippa on a.item_code = ippa.pos_item_cd WHERE ippa.item_id = bia.item_id) pos_item_code, " + 
					 "(select pkg_sb_mob_sto ck.get_stock_count('PEND', ?, IPPA.POS_ITEM_CD) STOCK_COUNT " +
					 "  from W_ITEM_PRODUCT_POS_ASSGN IPPA " +
					 "  where IPPA.ITEM_ID = BIA.ITEM_ID) STOCK_COUNT " +
					 "from W_BASKET_ITEM_ASSGN BIA, W_ITEM I, W_ITEM_DISPLAY_LKUP IDL " +
					 "where BIA.ITEM_ID = I.ID and BIA.ITEM_ID = IDL.ITEM_ID and IDL.LOCALE = ? " +
					 "and IDL.DISPLAY_TYPE = 'ITEM_SELECT' and TO_DATE(?, 'DD/MM/YYYY') between BIA.EFF_START_DATE and NVL(BIA.EFF_END_DATE, sysdate) " +
					 "and I.TYPE = 'SIM' and BIA.BASKET_ID = ? " +
					 "order by BIA.DISPLAY_SEQ";*/
		
		// MIP.P4 modification
		logger.debug("MIP.P4 modification: pkg_sb_mob_stock.get_stock_count_mip4 (2)");
		logger.debug(offerNature);
		
		MapSqlParameterSource params = new MapSqlParameterSource();

		StringBuffer sql = new StringBuffer("select BIA.ITEM_ID, BIA.DISPLAY_SEQ, BIA.MDO_IND, IDL.HTML, I.MIP_SIM_TYPE " +
				 " , (select A.HS_EXTRA_FUNCTION " +
				 " from BOMWEB_STOCK_CATALOG A, " +
				 " W_ITEM_PRODUCT_POS_ASSGN IPPA WHERE A.ITEM_CODE = IPPA.POS_ITEM_CD " +
				 " AND IPPA.ITEM_ID = BIA.ITEM_ID) SIM_EXTRA_FUNCTION " +
				 " , (select A.SIM_TYPE " +
				 " from BOMWEB_STOCK_CATALOG A, " +
				 " W_ITEM_PRODUCT_POS_ASSGN IPPA WHERE A.ITEM_CODE = IPPA.POS_ITEM_CD " +
				 " AND IPPA.ITEM_ID = BIA.ITEM_ID) SIM_TYPE, " +
				 "(SELECT a.item_code FROM bomweb_stock_catalog a left join w_item_product_pos_assgn ippa on a.item_code = ippa.pos_item_cd WHERE ippa.item_id = bia.item_id) pos_item_code, " + 
				
				 "(select pkg_sb_mob_stock.get_stock_count_mip4('PEND', :channelCd, IPPA.POS_ITEM_CD, :offerNature) STOCK_COUNT " +
				 
				 "  from W_ITEM_PRODUCT_POS_ASSGN IPPA " +
				 "  where IPPA.ITEM_ID = BIA.ITEM_ID) STOCK_COUNT " +
				 "from W_BASKET_ITEM_ASSGN BIA, W_ITEM I, W_ITEM_DISPLAY_LKUP IDL " +
				 "where BIA.ITEM_ID = I.ID and BIA.ITEM_ID = IDL.ITEM_ID and IDL.LOCALE = :locale " +
				 "and IDL.DISPLAY_TYPE = 'ITEM_SELECT' and TO_DATE(:appDate, 'DD/MM/YYYY') between BIA.EFF_START_DATE and NVL(BIA.EFF_END_DATE, sysdate) " +
				 "and I.TYPE = 'SIM' and BIA.BASKET_ID = :basketId ");
		
		params.addValue("channelCd", channelCd);
		params.addValue("appDate", appDate);
		params.addValue("locale", locale);
		params.addValue("basketId", basketId);
		
		// MIP.P4 modification
		params.addValue("offerNature", offerNature);
		
		if (mipSimType != null) {
			sql.append(" and nvl(decode (I.MIP_SIM_TYPE, 'X', :mipSimType, I.MIP_SIM_TYPE ), 'H') = :mipSimType ");
			params.addValue("mipSimType", mipSimType);
		}
		if (mipBrand != null) {
			sql.append(" and nvl(decode (I.MIP_BRAND, '9', :mipBrand, I.MIP_BRAND ), '1') = :mipBrand  ");
			params.addValue("mipBrand", mipBrand);
		}
		
		sql.append("order by BIA.DISPLAY_SEQ");
		
		ParameterizedRowMapper<VasDetailDTO> mapper = new ParameterizedRowMapper<VasDetailDTO>() {

			public VasDetailDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				VasDetailDTO vasDto = new VasDetailDTO();
				vasDto.setItemHtml(rs.getString("html"));
				vasDto.setItemId(rs.getString("item_id"));
				vasDto.setItemMdoInd(rs.getString("mdo_ind"));
				//vasDto.setStockCount(rs.getString("stock_count"));
				vasDto.setStockCount(rs.getString("STOCK_COUNT"));
				vasDto.setSimType(rs.getString("SIM_TYPE"));
				vasDto.setSimExtraFunction(rs.getString("SIM_EXTRA_FUNCTION"));
				vasDto.setPosItemCd(rs.getString("pos_item_code"));
				vasDto.setMipSimType(rs.getString("MIP_SIM_TYPE"));
				return vasDto;
			}
		};
		
		try {
			logger.debug("getSimTypeSelection @ HandsetModelDTO: " + sql);
		
			return simpleJdbcTemplate.query(sql.toString(), mapper, params);

		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException",erdae);
		} catch (Exception e) {
			logger.error("Exception caught in getSimTypeSelection()", e);
			throw new DAOException(e.getMessage(), e);
		}
		
		return null;
	}
	
	public Double getVasHSAmt(String selectedItemList[], String appDate) throws DAOException {
		
		String SQL="select sum(onetime_amt) hsamt" +
				" from w_item i INNER JOIN w_item_pricing ip on i.id=ip.id" +
				" where i.type='VAS'" +
				" and to_date(?, 'DD/MM/YYYY') between ip.eff_start_date and nvl(ip.eff_end_date, sysdate)\n" +
				" and ip.payment_group='HANDSET'" +
				" and ip.id in ";
		
		String appendSQLItemList="";
		if (selectedItemList ==null || selectedItemList.length ==0){
			selectedItemList = new String[]{"999999999"};
		}
		if (selectedItemList.length > 0) {
			 appendSQLItemList = "(";
			for (int i = 0; i < selectedItemList.length; i++) {
				appendSQLItemList = (new StringBuilder(String.valueOf(appendSQLItemList)))
						.append(selectedItemList[i])
						.append(",").toString();
			}

			appendSQLItemList = (new StringBuilder(String.valueOf(appendSQLItemList))).append(
					")").toString();
			appendSQLItemList = appendSQLItemList.replace(",)", ")");
		}
		SQL += appendSQLItemList;
		//System.out.println("appDate: " + appDate);
		//System.out.println("selectedItemList: " + appendSQLItemList);
		
		try {
			Double result = (Double) simpleJdbcTemplate.queryForObject(SQL,
					Double.class, appDate);
			return (result == null ? new Double(0) : result);
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("getVasHSAmt() EmptyResultDataAccessException");
			return new Double(0);
		} catch (Exception e) {
			logger.error("Exception caught in getBundleVASList()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	

	public Double getVasAmt(String selectedItemList[], String appDate) throws DAOException {
		
		String SQL="select sum(onetime_amt) hsamt" +
				" from w_item i INNER JOIN w_item_pricing ip on i.id=ip.id" +
				" where i.type='VAS'" +
				" and to_date(?, 'DD/MM/YYYY') between ip.eff_start_date and nvl(ip.eff_end_date, sysdate)\n" +
				" and ip.id in ";
		
		String appendSQLItemList="";
		if (selectedItemList ==null || selectedItemList.length ==0){
			selectedItemList = new String[]{"999999999"};
		}
		if (selectedItemList.length > 0) {
			 appendSQLItemList = "(";
			for (int i = 0; i < selectedItemList.length; i++) {
				appendSQLItemList = (new StringBuilder(String.valueOf(appendSQLItemList)))
						.append(selectedItemList[i])
						.append(",").toString();
			}

			appendSQLItemList = (new StringBuilder(String.valueOf(appendSQLItemList))).append(
					")").toString();
			appendSQLItemList = appendSQLItemList.replace(",)", ")");
		}
		SQL += appendSQLItemList;
		//System.out.println("appDate: " + appDate);
		//System.out.println("selectedItemList: " + appendSQLItemList);
		
		try {
			Double result = (Double) simpleJdbcTemplate.queryForObject(SQL,
					Double.class, appDate);
			return (result == null ? new Double(0) : result);
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("getVasAmt() EmptyResultDataAccessException");
			return new Double(0);
		} catch (Exception e) {
			logger.error("Exception caught in getBundleVASList()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public Double getVasHSAmt(String orderId) throws DAOException {
		String SQL="select sum(onetime_amt) hsamt " +
				"from bomweb_subscribed_item si inner join w_item_pricing ip on si.id=ip.id " +
				"join bomweb_order bo on si.order_id = bo.order_id " +
				"where si.type='VAS' " +
				"and trunc(bo.app_date) between ip.eff_start_date and nvl(ip.eff_end_date, sysdate) \n" +
				"and ip.payment_group='HANDSET' " +
				"and si.order_id =?";
		
		try {
			Double result = (Double) simpleJdbcTemplate.queryForObject(SQL, Double.class, orderId);
			return (result == null ? new Double(0) : result);
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("getVasHSAmt() EmptyResultDataAccessException");
			return new Double(0);
		} catch (Exception e) {
			logger.error("Exception caught in getBundleVASList()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	public Double getAdminCharge(String orderId) throws DAOException {//Athena 20130909 copy the whole function
		String SQL="select sum(onetime_amt) adminamt " +
				"from bomweb_subscribed_item si inner join w_item_pricing ip on si.id=ip.id " +
				"join bomweb_order bo on si.order_id = bo.order_id " +
				"where nvl(si.type,' ')!='HS' " +
				"and trunc(bo.app_date) between ip.eff_start_date and nvl(ip.eff_end_date, sysdate) \n" +
				"and nvl(ip.payment_group,' ')!='HANDSET' " +
				"and ip.onetime_type='A' " +
				"and si.order_id =?";
		
		try {
			Double result = (Double) simpleJdbcTemplate.queryForObject(SQL, Double.class, orderId);
			return (result == null ? new Double(0) : result);
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("getAdminCharge() EmptyResultDataAccessException");
			return new Double(0);
		} catch (Exception e) {
			logger.error("Exception caught in getAdminCharge()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public Double getMupAdminChargeAmount(String orderId) throws DAOException {
		String SQL="  select nvl(sum(too1_admin_charge),0) as tooAdminCharge"
				+ "   from bomweb_ord_mob_member "
				+ "   where parent_order_id = ? "
				+ "   and too1_ind='Y' and too1_waive_reason_cd is null ";
		
		try {
			Double result = (Double) simpleJdbcTemplate.queryForObject(SQL, Double.class, orderId);
			return (result == null ? new Double(0) : result);
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("getMupAdminChargeAmount() EmptyResultDataAccessException");
			return new Double(0);
		} catch (Exception e) {
			logger.error("Exception caught in getMupAdminChargeAmount()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public List<VasDetailDTO> getUserSelectedBasketItemList(String locale, String appDate, String basketId,String selectedItemList[]/*, String mnpInc, String apInc*/
			 ) throws DAOException {
		List<VasDetailDTO> vasDetailList = new ArrayList<VasDetailDTO>();
		String displayType="ITEM_SELECT";
		
		List exclusiveItemIdList = getBundleVASList(basketId, appDate);
		
		String notInVAStypeSql=" and item_t.item_type not in ('MNP_INC','AP_INC')\n";
		
		/*if ("Y".equals(mnpInc)){
			notInVAStypeSql=notInVAStypeSql.replace( "'MNP_INC'," , "");
		}
		
		if ("Y".equals(apInc)){
			notInVAStypeSql= notInVAStypeSql.replace(",'AP_INC'", "");
		}
		
		if ("Y".equals(mnpInc) && "Y".equals(apInc)){
			notInVAStypeSql="";
		}*/

		
		String appendSQLItemList="";
		
		if (selectedItemList ==null || selectedItemList.length ==0){
			
			selectedItemList = new String[]{"999999999"};
		}
		
		if (selectedItemList.length > 0) {
			 appendSQLItemList = " And ii.ID  in (";
			for (int i = 0; i < selectedItemList.length; i++) {
				appendSQLItemList = (new StringBuilder(String.valueOf(appendSQLItemList)))
						.append(selectedItemList[i])
						.append(",").toString();
			}

			appendSQLItemList = (new StringBuilder(String.valueOf(appendSQLItemList))).append(
					")").toString();
			appendSQLItemList = appendSQLItemList.replace(",)", ")");
			
		}
		
		String SQL =
			"select ITEM_T.ITEM_ID,\n" +
			"       ITEM_T.ITEM_LOB,\n" + 
			"       ITEM_T.ITEM_TYPE,\n" + 
			"       ITEM_T.ITEM_MDO_IND,\n" + 
			"       ITEM_HTML_T.HTML,\n" + 
			"       ITEM_HTML_T.HTML2,\n" + 
			"       ITEM_HTML_T.LOCALE,\n" + 
			"       ITEM_HTML_T.DISPLAY_TYPE,\n" + 
			"       IP_T.ONETIME_AMT,\n" + 
			"       IP_T.RECURRENT_AMT,\n" + 
			"       ITEM_T.DISPLAY_SEQ\n" + 
			"  from (select I.ID            ITEM_ID,\n" + 
			"               I.LOB           ITEM_LOB,\n" + 
			"               I.TYPE          ITEM_TYPE,\n" + 
			"               BIA.MDO_IND     ITEM_MDO_IND,\n" + 
			"               BIA.DISPLAY_SEQ\n" + 
			"          from W_BASKET_ITEM_ASSGN BIA,\n" + 
			"               W_ITEM I\n" + 
	
			"         where BIA.ITEM_ID = I.ID\n" + 
			"           and BIA.BASKET_ID = ? ------------------------------------------\n" + 

			"    and  i.type !='SIM' and TO_DATE(?, 'DD/MM/YYYY') between BIA.EFF_START_DATE and   NVL(BIA.EFF_END_DATE, sysdate) ----appDate--\n "+

			"        union \n" + 
			"        select II.ID, II.LOB, II.TYPE, 'O', 9999999\n" + 
			"          from W_ITEM II where ii.type='VAS' \n" + appendSQLItemList +
			"        \n-----\n" + 
			"        --vas item list\n" + 
			"        -----\n" + 
			"        ) ITEM_T,\n" + 
			"       (select HTML, HTML2, LOCALE, ITEM_ID, DISPLAY_TYPE\n" + 
			"          from W_ITEM_DISPLAY_LKUP\n" + 
			"         where DISPLAY_TYPE = 'ITEM_SELECT') ITEM_HTML_T,\n" + 
			"       (select IP.ID ITEM_ID, IP.ONETIME_AMT, IP.RECURRENT_AMT\n" + 
			"          from W_ITEM_PRICING IP\n"+ 
			"         where TO_DATE(?, 'DD/MM/YYYY') between IP.EFF_START_DATE and NVL(IP.EFF_END_DATE, sysdate)) IP_T\n" + 
			" where ITEM_T.ITEM_ID = ITEM_HTML_T.ITEM_ID(+)\n" + 
			"   and ITEM_T.ITEM_ID = IP_T.ITEM_ID(+)\n" + 
			"   and ITEM_HTML_T.DISPLAY_TYPE = 'ITEM_SELECT'\n" + notInVAStypeSql+
			" \n  and ITEM_HTML_T.LOCALE(+) = ?----LOCALE----\n";

		
		
		
		
				/*if (exclusiveItemIdList.size() > 0) {
					String appendSQL = " AND item_t.item_id not in (";
					for (int i = 0; i < exclusiveItemIdList.size(); i++) {
						appendSQL = (new StringBuilder(String.valueOf(appendSQL)))
								.append((String) exclusiveItemIdList.get(i))
								.append(",").toString();
					}

					appendSQL = (new StringBuilder(String.valueOf(appendSQL))).append(
							")").toString();
					appendSQL = appendSQL.replace(",)", ")");
					SQL = (new StringBuilder(String.valueOf(SQL))).append(appendSQL)
							.toString();
				}*/
		SQL+=		 " order by item_t.display_seq ";

		ParameterizedRowMapper<VasDetailDTO> mapper = new ParameterizedRowMapper<VasDetailDTO>() {

			public VasDetailDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				VasDetailDTO vasDto = new VasDetailDTO();
				vasDto.setItemHtml(rs.getString("html"));
				vasDto.setItemId(rs.getString("item_id"));
				vasDto.setItemLob(rs.getString("item_lob"));
				vasDto.setItemType(rs.getString("item_type"));
				vasDto.setItemMdoInd(rs.getString("item_mdo_ind"));
				
				vasDto.setItemHtml2(rs.getString("html2"));
				vasDto.setItemLocale(rs.getString("locale"));
				vasDto.setItemDisplayType(rs.getString("display_type"));
				vasDto.setItemOnetimeAmt(rs.getString("onetime_amt"));
				vasDto.setItemRecurrentAmt(rs.getString("recurrent_amt"));

				return vasDto;
			}
		};

		try {
			logger.debug("getBrandList @ HandsetModelDTO: " + SQL);
		
			vasDetailList = simpleJdbcTemplate.query(SQL, mapper, basketId,appDate,	appDate, locale);

		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException");
			vasDetailList = null;
		} catch (Exception e) {
			logger.error("Exception caught in getBrandList()", e);
			throw new DAOException(e.getMessage(), e);
		}
		return vasDetailList;

	}
	
	public String getSimOnlyBasketSimType(String basketId, String appDate) throws DAOException {
		List<Integer> simTypeList = new ArrayList<Integer>();
		String sql = "SELECT DISTINCT A.HS_EXTRA_FUNCTION"
				+ " FROM BOMWEB_STOCK_CATALOG A"
				+ " LEFT JOIN W_ITEM_PRODUCT_POS_ASSGN IPPA ON (A.ITEM_CODE = IPPA.POS_ITEM_CD)"
				+ " LEFT JOIN W_BASKET_ITEM_ASSGN BIA ON (BIA.ITEM_ID = IPPA.ITEM_ID)"
				+ " WHERE TO_DATE(?, 'DD/MM/YYYY') BETWEEN BIA.EFF_START_DATE and NVL(BIA.EFF_END_DATE, sysdate) "
				+ " AND A.ITEM_TYPE = '02'"
				+ " AND BIA.BASKET_ID = ?";
		ParameterizedRowMapper<Integer> mapper = new ParameterizedRowMapper<Integer>() {
			public Integer mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return rs.getInt("HS_EXTRA_FUNCTION");
			}
		};
		
		try {
			logger.debug("getSimOnlyBasketSimType @ VasDetailDAO: ");
			logger.debug("getSimOnlyBasketSimType @ VasDetailDAO: " + sql);
			simTypeList = simpleJdbcTemplate.query(sql, mapper, appDate, basketId);
		} catch (DataAccessException e) {
			logger.info("Exception caught in getSimOnlyBasketSimType():", e);
			throw new DAOException(e.getMessage(), e);
		}
		if (simTypeList.contains(5) && !simTypeList.contains(1) && !simTypeList.contains(2)) {
			return "5";
		} else if (simTypeList.contains(5) && (simTypeList.contains(1) || simTypeList.contains(2))) {
			return "3";
		} else if (simTypeList.contains(1) && simTypeList.contains(2)) {
			return "3";
		} else if (simTypeList.contains(1)) {
			return "1";
		} else if (simTypeList.contains(2)) {
			return "2";
		} 
		return "0";
	}
	
	public BasketDTO getBasketAttribute(String basketId, String appDate) throws DAOException {
		List<BasketDTO> basketList = new ArrayList<BasketDTO>();

		String SQLModel ="select BAMV.BASKET_ID,BAMV.DESCRIPTION,\n" +
			"       BAMV.OFFER_TYPE,\n" + 
			"       BAMV.OFFER_TYPE_ID,\n" + 
			"       BAMV.RATE_PLAN_ID, --499\n" + 
			"       BAMV.RP_GROSS_PLAN_FEE, \n" + 
			"       DECODE(BAMV.OFFER_TYPE_ID, '1', 'Y', 'N') PH_IND,\n" + 
			"       BAMV.CONTRACT_PERIOD_ID,\n" +
			"       BAMV.BRAND,\n" + 
			"       BAMV.COLOR,\n" + 
			"       BAMV.CUSTOMER_TIER_ID,\n" + 
			"       BAMV.CUSTOMER_TIER,\n" + 
			"       BAMV.MODEL,\n" + 
			"       BAMV.OFFER_TYPE,\n" + 
			"       BAMV.OFFER_TYPE_ID,\n" + 
			"       BAMV.RATE_PLAN,\n" + 
			"       BAMV.RATE_PLAN_ID,\n" + 
			"       BAMV.RP_TYPE,\n" + 
			"       BAMV.RP_TYPE_ID,\n" + 
			"       BAMV.SIM_TYPE,\n" + 
			"       BAMV.SIM_TYPE_ID,\n" + 
			"       BAMV.BRAND_ID,\n" + 
			"       BAMV.MODEL_ID,\n" + 
			"       BAMV.COLOR_ID,bamv.basket_type, bamv.basket_type_id, \n" + 
			"		BAMV.CREDIT_CARD_IND, B.DUMMY_IND,\n" +
			"		BAMV.data_only_ind, \n" +	
			"		BAMV.UPFRONT_CC_IND, \n" +
			"		BAMV.DESCRIPTION, \n" +
			"       NVL(BAMV.MIP_BRAND, '1') MIP_BRAND, \n" +
			"       nvl((select sum(IP.ONETIME_AMT) UPFRONT_AMT\n" + 
			"          from W_BASKET_ITEM_ASSGN BIA, W_ITEM I, W_ITEM_PRICING IP \n" + 
			"         where BIA.ITEM_ID = I.ID \n" + 
			"           and BIA.ITEM_ID = IP.ID \n" + 
			"           and TO_DATE(:appDate, 'DD/MM/YYYY') between BIA.EFF_START_DATE and\n" + 
			"               NVL(BIA.EFF_END_DATE, sysdate)\n" + 
			"           and TO_DATE(:appDate, 'DD/MM/YYYY') between IP.EFF_START_DATE and\n" + 
			"               NVL(IP.EFF_END_DATE, sysdate)\n" + 
			"           and I.TYPE = 'HS'\n" + 
			"           and BIA.BASKET_ID = BAMV.BASKET_ID),0) UPFRONT_AMT,\n" + 
			"       (select sum(nvl(IP.ONETIME_AMT,0)) UPFRONT_AMT\n" + 
			"          from W_BASKET_ITEM_ASSGN BIA, W_ITEM I, W_ITEM_PRICING IP \n" + 
			"         where BIA.ITEM_ID = I.ID \n" + 
			"           and BIA.ITEM_ID = IP.ID \n" + 
			"           and TO_DATE(:appDate, 'DD/MM/YYYY') between BIA.EFF_START_DATE and\n" + 
			"               NVL(BIA.EFF_END_DATE, sysdate)\n" + 
			"           and TO_DATE(:appDate, 'DD/MM/YYYY') between IP.EFF_START_DATE and\n" + 
			"               NVL(IP.EFF_END_DATE, sysdate)\n" + 
			"           and I.TYPE in ('RP', 'BFEE', 'BVAS', 'VAS')\n" +
			"           and BIA.BASKET_ID = BAMV.BASKET_ID) PRE_PAYMENT_AMT ,\n" + 
			"(select IPPA.POS_ITEM_CD\n" +
			"         from W_BASKET_ITEM_ASSGN        BIA,\n" + 
			"              W_ITEM                     I,\n" + 
			"              W_ITEM_OFFER_ASSGN         IOA,\n" + 
			"              W_ITEM_OFFER_PRODUCT_ASSGN IOPA,\n" + 
			"              W_ITEM_PRODUCT_POS_ASSGN   IPPA\n" + 
			"        where BIA.ITEM_ID = I.ID\n" + 
			"          and I.ID = IOA.ITEM_ID\n" + 
			"          and IOA.ITEM_ID = IOPA.ITEM_ID\n" + 
			"          and IOA.ITEM_OFFER_SEQ = IOPA.ITEM_OFFER_SEQ\n" + 
			"          and IOPA.ITEM_ID = IPPA.ITEM_ID\n" + 
			"          and IOPA.ITEM_OFFER_SEQ = IPPA.ITEM_PRODUCT_SEQ\n" + 
			"          and IOPA.ITEM_PRODUCT_SEQ = IPPA.ITEM_PRODUCT_SEQ\n" + 
			"          and TO_DATE(:appDate, 'DD/MM/YYYY') between\n" + 
			"              BIA.EFF_START_DATE and NVL(BIA.EFF_END_DATE, sysdate)\n" + 
			"          and I.TYPE = 'HS'\n" + 
			"          and BIA.BASKET_ID = BAMV.BASKET_ID) HS_POS_ITEM_CD ,\n" +
					 " (select A.HS_EXTRA_FUNCTION " +
					 " 		FROM BOMWEB_STOCK_CATALOG A " +
					 "		LEFT JOIN W_ITEM_PRODUCT_POS_ASSGN IPPA ON (A.ITEM_CODE = IPPA.POS_ITEM_CD)" +
					 "		LEFT JOIN W_ITEM I ON (IPPA.ITEM_ID = I.ID)" +
					 "		LEFT JOIN W_BASKET_ITEM_ASSGN BIA ON (BIA.ITEM_ID = I.ID)" +
					 "		WHERE IPPA.ITEM_ID = BIA.ITEM_ID" +
					 "		AND TO_DATE(:appDate, 'DD/MM/YYYY') between BIA.EFF_START_DATE and NVL(BIA.EFF_END_DATE, sysdate)" + 
					 "		AND I.TYPE = 'HS'" +
					 "		AND BIA.BASKET_ID = BAMV.BASKET_ID) HS_EXTRA_FUNCTION, " +
					 " (SELECT bcl.code_desc " +
					 " 		FROM BOMWEB_STOCK_CATALOG A " +
					 " 		LEFT JOIN W_ITEM_PRODUCT_POS_ASSGN IPPA ON (A.ITEM_CODE = IPPA.POS_ITEM_CD) " +
					 " 		LEFT JOIN W_ITEM I ON (IPPA.ITEM_ID = I.ID) " +
					 " 		LEFT JOIN W_BASKET_ITEM_ASSGN BIA ON (BIA.ITEM_ID = I.ID) " +
					 " 		left join bomweb_code_lkup bcl on bcl.code_type = 'SIM_SIZE' and bcl.code_id = a.sim_type " +
					 " 		WHERE     IPPA.ITEM_ID = BIA.ITEM_ID " +
					 " 		AND TO_DATE ( :appDate, 'DD/MM/YYYY') BETWEEN BIA.EFF_START_DATE AND NVL (BIA.EFF_END_DATE,SYSDATE) " +
					 " 		AND I.TYPE = 'HS' " +
					 " 		AND BIA.BASKET_ID = BAMV.BASKET_ID) HS_SIM_SIZE, " +
			" (select DECODE(count(*), 0, 'N', 'Y')\n" +
			" from W_BASKET_ITEM_ASSGN        BIA,\n" + 
			"      W_ITEM                     I,\n" + 
			"      W_ITEM_OFFER_ASSGN         IOA,\n" + 
			"      W_ITEM_OFFER_PRODUCT_ASSGN IOPA,\n" + 
			"      W_ITEM_PRODUCT_POS_ASSGN   IPPA,\n" + 
			"      BOMWEB_HOTTEST_MODEL       HM\n" + 
			"where BIA.ITEM_ID = I.ID\n" + 
			"  and I.ID = IOA.ITEM_ID\n" + 
			"  and IOA.ITEM_ID = IOPA.ITEM_ID\n" + 
			"  and IOA.ITEM_OFFER_SEQ = IOPA.ITEM_OFFER_SEQ\n" + 
			"  and IOPA.ITEM_ID = IPPA.ITEM_ID\n" + 
			"  and IOPA.ITEM_OFFER_SEQ = IPPA.ITEM_PRODUCT_SEQ\n" + 
			"  and IOPA.ITEM_PRODUCT_SEQ = IPPA.ITEM_PRODUCT_SEQ\n" + 
			"  and IPPA.POS_ITEM_CD = HM.ITEM_CODE\n" + 
			"  and TO_DATE(:appDate, 'DD/MM/YYYY') between BIA.EFF_START_DATE and NVL(BIA.EFF_END_DATE, sysdate)\n" + 
			"  and I.TYPE = 'HS'\n" + 
			"  and BIA.BASKET_ID = BAMV.BASKET_ID ) HOTTEST_MODEL_IND ,\n" +
			" (select ML.MAP_TO\n" +
			"  from W_MAPPING_LKUP ML\n" + 
			" where ML.MAP_TYPE = 'DUMMY_BASKET'\n" + 
			"   and ML.MAP_FROM = BAMV.BASKET_ID) REAL_BASKET_ID \n" +
			
			" , NVL(BAMV.NATURE,'ACQ') NATURE \n" +
			
			"  from W_BASKET_ATTRIBUTE_MV BAMV, W_BASKET B\n" + 
			" where BAMV.BASKET_ID = B.ID \n"+
			" and  BAMV.BASKET_ID = :basketId";


		ParameterizedRowMapper<BasketDTO> mapper = new ParameterizedRowMapper<BasketDTO>() {
			public BasketDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				BasketDTO basket = new BasketDTO();
				basket.setBasketId(rs.getString("BASKET_ID"));
				basket.setBasketOfferTypeCd(rs.getString("OFFER_TYPE_ID"));
				basket.setBasketOfferTypeDesc(rs.getString("OFFER_TYPE"));
				basket.setRecurrentAmt(rs.getString("RATE_PLAN_ID"));
				basket.setGrossPlanFee(rs.getString("RP_GROSS_PLAN_FEE"));
				basket.setContractPeriod(rs.getString("CONTRACT_PERIOD_ID"));
				basket.setUpfrontAmt(rs.getString("UPFRONT_AMT"));
				basket.setBrandId(rs.getString("brand_id"));
				basket.setModelId(rs.getString("model_id"));
				basket.setColorId(rs.getString("color_id"));
				basket.setPublicHouseBaksetInd(rs.getString("PH_IND"));
				basket.setPrePaymentAmt(rs.getString("PRE_PAYMENT_AMT"));
				basket.setBasketTypeId(rs.getString("basket_type_id"));
				basket.setBasketTypeDesc(rs.getString("basket_type"));
				basket.setDescription(rs.getString("DESCRIPTION"));
				////////////////
				basket.setBrandDesc(rs.getString("BRAND"));
				basket.setColorDesc(rs.getString("COLOR"));
				basket.setCustomerTierId(rs.getString("CUSTOMER_TIER_ID"));
				basket.setCustomerTierDesc(rs.getString("CUSTOMER_TIER"));
				basket.setModelDesc(rs.getString("MODEL"));
				basket.setOfferTypeDesc(rs.getString("OFFER_TYPE"));
				basket.setOfferTypeId(rs.getString("OFFER_TYPE_ID"));
				basket.setRatePlanDesc(rs.getString("RATE_PLAN"));
				basket.setRatePlanId(rs.getString("RATE_PLAN_ID"));
				basket.setRpTypeDesc(rs.getString("RP_TYPE"));
				basket.setRpTypeId(rs.getString("RP_TYPE_ID"));
				basket.setSimTypeDesc(rs.getString("SIM_TYPE"));
				basket.setSimTypeId(rs.getString("SIM_TYPE_ID"));
				////////////////
				basket.setCreditCardInd(rs.getString("CREDIT_CARD_IND"));
				basket.setHsPosItemCd(rs.getString("HS_POS_ITEM_CD"));
				basket.setDummyBasketInd(rs.getString("DUMMY_IND"));
				basket.setRealBasketId(rs.getString("REAL_BASKET_ID"));
				basket.setDataOnlyInd(rs.getString("data_only_ind"));
				basket.setHottestModelInd(rs.getString("HOTTEST_MODEL_IND"));
				
				basket.setBasketQuotaList(getBasketQuotaList(basket.getBasketId()));
				String hsExtraFunction = rs.getString("HS_EXTRA_FUNCTION");
				basket.setHandsetExtraFunction(hsExtraFunction);
				basket.setHandsetSimSize(rs.getString("HS_SIM_SIZE"));
				basket.setUpfrontCCInd(rs.getString("UPFRONT_CC_IND"));
				
				basket.setMipBrand(rs.getString("MIP_BRAND"));
				basket.setDescription(rs.getString("DESCRIPTION"));
				/*if (StringUtils.isNotBlank(hsExtraFunction)) {
					basket.setHsExtraFunction(HsExtraFunction.getHsExtraFunction(hsExtraFunction));
				}*/
				
				// MIP.P4 modification
				basket.setNature(rs.getString("NATURE"));
				
				return basket;
			}
		};

		try {

			logger.debug("getBasketAttribute @ VasDetailDAO: ");
			logger.debug("getBasketAttribute @ VasDetailDAO: " + SQLModel);
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("appDate", appDate);
			params.addValue("basketId", basketId);
			basketList = simpleJdbcTemplate.query(SQLModel, mapper, params);

		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException");
			basketList = null;
			return null;
		} catch (Exception e) {
			logger.info("Exception caught in getBasketAttribute():", e);
			throw new DAOException(e.getMessage(), e);
		}
		if (basketList.size() > 0) {
			return basketList.get(0);// only return the first one
		} else {

			return null;
		}

	}
	
	//get sim only basket- default sim item
	public String getDefaultSimItemId(String basketId, String appDate) throws DAOException {
		String simItemId = "";

		String SQL = 

			"select BIA.ITEM_ID\n" +
			"  from W_BASKET_ITEM_ASSGN BIA, W_ITEM I, W_BASKET B\n" + 
			" where BIA.ITEM_ID = I.ID\n" + 
			"   and BIA.BASKET_ID = B.ID\n" + 
			"   and B.TYPE in (2, 3)\n" + 
			"   and I.TYPE = 'SIM'\n" + 
			"   and BIA.BASKET_ID = ?\n" + 
			"   and TO_DATE(?, 'DD/MM/YYYY') between BIA.EFF_START_DATE and\n" + 
			"       NVL(BIA.EFF_END_DATE, sysdate)\n" + 
			"   and ROWNUM = 1";



		try {
			logger.debug("getDefaultSimItemId @ VasDetailDAO: " + SQL);
			simItemId = (String) simpleJdbcTemplate.queryForObject(SQL,
					String.class, basketId, appDate);

		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException");
			simItemId = "";
		} catch (Exception e) {
			logger.error("Exception caught in getDefaultSimItemId()", e);
			throw new DAOException(e.getMessage(), e);
		}
		return simItemId;

	}
	
	
	// for vasdetail page
	public List<String> getItemSelectionGrpList(String grpId) throws DAOException {
		logger.debug("getItemSelectionGrpList is called");
		List<String> itemList = new ArrayList();
		String SQL=
			"select ISGA.ITEM_ID\n" +
			"  from W_ITEM_SELECTION_GRP_ASSGN ISGA\n" + 
			" where ISGA.GRP_ID = ?\n" + 
			" order by ISGA.ITEM_ID";

		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				String brand = new String();
				brand = rs.getString("ITEM_ID");
				return brand;
			}
		};
		
		try {
			itemList = simpleJdbcTemplate.query(SQL, mapper, grpId );
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("getItemSelectionGrpList() EmptyResultDataAccessException");
			itemList = null;
		} catch (Exception e) {
			logger.error("Exception caught in getItemSelectionGrpList()", e);
			throw new DAOException(e.getMessage(), e);
		}
		return itemList;
	}
	
	public List<BasketQuotaDTO> getBasketQuotaList(String basketId)	{
		List<BasketQuotaDTO> basketQuotaList = new ArrayList<BasketQuotaDTO>();

		String SQL = "select P.BASKET_ID,\n" + "       QL.QUOTA_ID,\n"
				+ "       QL.QUOTA_DESC,\n" + "       QL.QUOTA_CEILING,\n"
				+ "       QL.VALIDITY_MTH\n"
				+ "  from W_BASKET_PARM P, W_QUOTA_LKUP QL\n"
				+ " where P.PARM_TYPE_VAL = QL.QUOTA_ID\n"
				+ "   and P.PARM_TYPE = 'QUOTA'\n" + "   and P.BASKET_ID = ?";

		ParameterizedRowMapper<BasketQuotaDTO> mapper = new ParameterizedRowMapper<BasketQuotaDTO>() {
			public BasketQuotaDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				BasketQuotaDTO basketQuotaDto = new BasketQuotaDTO();
				basketQuotaDto.setBasketId(rs.getString("BASKET_ID"));
				basketQuotaDto.setQuotaId(rs.getString("QUOTA_ID"));
				basketQuotaDto.setQuotaDesc(rs.getString("QUOTA_DESC"));
				basketQuotaDto.setQuotaCeiling(rs.getString("QUOTA_CEILING"));
				basketQuotaDto.setValidityMth(rs.getString("VALIDITY_MTH"));

				return basketQuotaDto;
			}
		};

		try {

			logger.debug("getBasketQuotaList @ VasDetailDAO: ");
			logger.debug("getBasketQuotaList @ VasDetailDAO: " + SQL);

			basketQuotaList = simpleJdbcTemplate.query(SQL, mapper, basketId);

		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException");
			basketQuotaList = null;
			return null;
		} catch (Exception e) {
			logger.info("Exception caught in getBasketQuotaList():", e);
			return null;
		}
		
		return basketQuotaList;
		

	}
	
	public boolean isSecretarialItem(String itemId) throws DAOException {
		try {
			String sql = "SELECT" +
					" count(*) count" +
					" FROM W_ITEM_OFFER_PRODUCT_ASSGN IOPA" +
					" WHERE IOPA.ITEM_ID = :itemId" +
					" AND IOPA.PRODUCT_ID IN" +
					" ( " +
					"   SELECT TO_NUMBER(CODE)" +
					"   FROM W_CODE_LKUP " +
					"   WHERE GRP_ID = 'SECRETARIAL_PROD_ID'" +
					" )";
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("itemId", itemId);
			int count = simpleJdbcTemplate.queryForInt(sql, params);
			return count > 0;
		} catch (Exception e) {
			logger.info("Exception caught in getBasketQuotaList():", e);
			throw new DAOException(e);
		}
	}
	
	public String getOneCardTwoSimInd(String[] selectedItemList) throws DAOException {
		String result = "N";

		String sub_SQL = "";
		if (selectedItemList == null || selectedItemList.length == 0) {
			return result;
		} else {

			for (int i = 0; i < selectedItemList.length; i++) {

				if (selectedItemList.length == 1
						|| selectedItemList.length == i + 1) {
					sub_SQL += "'" + selectedItemList[i] + "'";
				} else {
					sub_SQL += "'" + selectedItemList[i] + "',";
				}
			}

			sub_SQL += ")";

		}

		String SQL = "select DECODE(count(*), 0, 'N', 'Y') IND\n"
				+ "  from W_ITEM_SELECTION_GRP_ASSGN G\n"
				+ " where G.GRP_ID = 7777777777\n" + "   and G.ITEM_ID in ("
				+ sub_SQL;

		ParameterizedRowMapper<String[]> mapper = new ParameterizedRowMapper<String[]>() {
			public String[] mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				String[] item = new String[2];
				item[0] = rs.getString("id");
				item[1] = rs.getString("description");

				return item;
			}
		};

		try {
			result = (String) simpleJdbcTemplate.queryForObject(SQL,
					String.class);

		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException");
			result="N";
		} catch (Exception e) {
			logger.error("Exception caught in getOneCardTwoSimInd()", e);
			throw new DAOException(e.getMessage(), e);
		}
		return result;

	}
	
	public List<VasDetailDTO> getUserSelectedBasketItemList(String locale,
			String appDate, String basketId, String selectedItemList[], MRTUI mrtUI, PaymentMonthyUI paymentMonthyUI
	) throws DAOException {
		List<VasDetailDTO> vasDetailList = new ArrayList<VasDetailDTO>();

		String mnpIncSQL= "";
		String apIncSQL= "";
		if(mrtUI !=null && ("Y".equals(mrtUI.getMnpInd()) || "A".equals(mrtUI.getMnpInd()))){
			 mnpIncSQL="";
		}else{
			 mnpIncSQL= "and I.TYPE != 'MNP_INC' \n";
		}
		
		if(paymentMonthyUI !=null && "C".equals(paymentMonthyUI.getPayMethodType())){
			apIncSQL= "";
		}else{
			apIncSQL ="and I.TYPE != 'AP_INC' \n";
		}


		String appendSQLItemList = "";

		if (selectedItemList == null || selectedItemList.length == 0) {

			selectedItemList = new String[] { "999999999" };
		}

		if (selectedItemList.length > 0) {
			appendSQLItemList = " And ii.ID  in (";
			for (int i = 0; i < selectedItemList.length; i++) {
				appendSQLItemList = (new StringBuilder(
						String.valueOf(appendSQLItemList)))
						.append(selectedItemList[i]).append(",").toString();
			}

			appendSQLItemList = (new StringBuilder(
					String.valueOf(appendSQLItemList))).append(")").toString();
			appendSQLItemList = appendSQLItemList.replace(",)", ")");

		}
		String removeBVASSQL=
			"   --REMOVE EXCLUSIVE BVAS item\n" +
			"and ITEM_T.ITEM_ID not in\n" + 
			"    (select BIA.ITEM_ID\n" + 
			"       from W_BASKET_ITEM_ASSGN BIA\n" + 
			"      where BIA.BASKET_ID = :basketId\n" + 
			"        and BIA.ITEM_ID in\n" + 
			"            ((select E.ITEM_ID_B\n" + 
			"               from W_MOB_ITEM_EXCLUSIVE_LKUP E\n" + 
			"              where E.ITEM_ID_A in (:vasItemIds)"+
			"                and E.ITEM_TYPE_B = 'BVAS'\n" + 
			"             union\n" + 
			"             select E.ITEM_ID_A\n" + 
			"               from W_MOB_ITEM_EXCLUSIVE_LKUP E\n" + 
			"              where E.ITEM_ID_B in  (:vasItemIds) \n"+
			"                and E.ITEM_TYPE_A = 'BVAS'))) \n";
		
		if (!this.isEmpty(selectedItemList)) {
			removeBVASSQL="";
		}
		
		

		String SQL = "select ITEM_T.ITEM_ID,\n"
				+ "       ITEM_T.ITEM_LOB,\n"
				+ "       ITEM_T.ITEM_TYPE,\n"
				+ "       ITEM_T.ITEM_MDO_IND,\n"
				+ "       ITEM_HTML_T.HTML,\n"
				+ "       ITEM_HTML_T.HTML2,\n"
				+ "       ITEM_HTML_T.LOCALE,\n"
				+ "       ITEM_HTML_T.DISPLAY_TYPE,\n"
				+ "       IP_T.ONETIME_AMT,\n"
				+ "       IP_T.RECURRENT_AMT,\n"
				+ "       ITEM_T.DISPLAY_SEQ\n"
				+ "  from (select I.ID            ITEM_ID,\n"
				+ "               I.LOB           ITEM_LOB,\n"
				+ "               I.TYPE          ITEM_TYPE,\n"
				+ "               BIA.MDO_IND     ITEM_MDO_IND,\n"
				+ "               BIA.DISPLAY_SEQ\n"
				+ "          from W_BASKET_ITEM_ASSGN BIA,\n"
				+ "               W_ITEM I\n"
				+ "         where BIA.ITEM_ID = I.ID\n"
				+ "           and BIA.BASKET_ID = :basketId ------------------------------------------\n"
				+ "    and  i.type !='SIM' and TO_DATE(:appDate, 'DD/MM/YYYY') between BIA.EFF_START_DATE and   NVL(BIA.EFF_END_DATE, sysdate) ----appDate--\n "
				+ apIncSQL 
				+ mnpIncSQL
				+ "        union \n"
				+ "        select II.ID, II.LOB, II.TYPE, 'O', 9999999\n"
				+ "          from W_ITEM II where ii.type='VAS' \n"
				+ appendSQLItemList
			
				+ "        --vas item list\n"
				+ "        -----\n"
				+ "        ) ITEM_T,\n"
				+ "       (select HTML, HTML2, LOCALE, ITEM_ID, DISPLAY_TYPE\n"
				+ "          from W_ITEM_DISPLAY_LKUP\n"
				+ "         where DISPLAY_TYPE = 'ITEM_SELECT') ITEM_HTML_T,\n"
				+ "       (select IP.ID ITEM_ID, IP.ONETIME_AMT, IP.RECURRENT_AMT\n"
				+ "          from W_ITEM_PRICING IP\n"
				+ "         where TO_DATE(:appDate, 'DD/MM/YYYY') between IP.EFF_START_DATE and NVL(IP.EFF_END_DATE, sysdate)) IP_T\n"
				+ " where ITEM_T.ITEM_ID = ITEM_HTML_T.ITEM_ID(+)\n"
				+ "   and ITEM_T.ITEM_ID = IP_T.ITEM_ID(+)\n"
				+ "   and ITEM_HTML_T.DISPLAY_TYPE = 'ITEM_SELECT'\n"
				+ " \n  and ITEM_HTML_T.LOCALE(+) = :locale----LOCALE----\n"
				+ removeBVASSQL;
	
		SQL += " order by item_t.display_seq ";
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("basketId", basketId);
		params.addValue("appDate", appDate);
		params.addValue("locale", locale);
		params.addValue("vasItemIds", Arrays.asList(selectedItemList));
		
		

		ParameterizedRowMapper<VasDetailDTO> mapper = new ParameterizedRowMapper<VasDetailDTO>() {

			public VasDetailDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				VasDetailDTO vasDto = new VasDetailDTO();
				vasDto.setItemHtml(rs.getString("html"));
				vasDto.setItemId(rs.getString("item_id"));
				vasDto.setItemLob(rs.getString("item_lob"));
				vasDto.setItemType(rs.getString("item_type"));
				vasDto.setItemMdoInd(rs.getString("item_mdo_ind"));
				vasDto.setItemHtml2(rs.getString("html2"));
				vasDto.setItemLocale(rs.getString("locale"));
				vasDto.setItemDisplayType(rs.getString("display_type"));
				vasDto.setItemOnetimeAmt(rs.getString("onetime_amt"));
				vasDto.setItemRecurrentAmt(rs.getString("recurrent_amt"));
				return vasDto;
			}
		};

		try {
			//System.out.print(SQL);
			logger.debug("getUserSelectedBasketItemList " + SQL);
			vasDetailList = simpleJdbcTemplate.query(SQL, mapper, basketId,	appDate, appDate, locale);

		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException");
			vasDetailList = null;
		} catch (Exception e) {
			logger.error("Exception caught in getUserSelectedBasketItemList()", e);
			throw new DAOException(e.getMessage(), e);
		}
		return vasDetailList;

	}
	
	private <T> boolean isEmpty(T[] values) {
		return values == null || values.length == 0;
	}
	
	public String getSimTypeByItemCd(String itemCd) throws DAOException {
		final String sql = "select SIM_TYPE " +
							 " FROM" +
							 "  BOMWEB_STOCK_CATALOG" +
							 " WHERE" +
							 "  ITEM_CODE = :itemCd";
		if (logger.isDebugEnabled()) {
			logger.debug(sql);
			logger.debug("itemCd: " + itemCd);
		}
		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("itemCd", itemCd);
			return this.simpleJdbcTemplate.queryForObject(sql, String.class, params);
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException");
			return null;
		} catch (Exception e) {
			logger.error("Exception caught in getSimTypeByItemId()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public String getHandsetDescriptionByItemCd(String itemCd) throws DAOException {
		final String sql = "select ITEM_DESC " +
							 " FROM" +
							 "  BOMWEB_STOCK_CATALOG" +
							 " WHERE" +
							 "  ITEM_CODE = :itemCd";
		if (logger.isDebugEnabled()) {
			logger.debug(sql);
			logger.debug("itemCd: " + itemCd);
		}
		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("itemCd", itemCd);
			return this.simpleJdbcTemplate.queryForObject(sql, String.class, params);
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException in getHandsetDescriptionByItemCd()");
			return null;
		} catch (Exception e) {
			logger.error("Exception caught in getHandsetDescriptionByItemCd()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public List<String> getItemIdByPosItemCd(String posItemCd) throws DAOException {
		final String sql = "select ITEM_ID " +
							 " FROM" +
							 "  W_ITEM_PRODUCT_POS_ASSGN" +
							 " WHERE" +
							 "  POS_ITEM_CD = :posItemCd";
		if (logger.isDebugEnabled()) {
			logger.debug(sql);
			logger.debug("posItemCd: " + posItemCd);
		}
		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("posItemCd", posItemCd);
			return this.simpleJdbcTemplate.query(sql, new ParameterizedRowMapper<String>() {
				public String mapRow(ResultSet rs, int rowNum) throws SQLException {
					return rs.getString("ITEM_ID");
				}
			}, params);
		} catch (EmptyResultDataAccessException erdae) {
			logger.debug("EmptyResultDataAccessException");
			return Collections.emptyList();
		} catch (Exception e) {
			logger.error("Exception caught in getItemIdByPosItemCd()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	
	public List<String> getPosItemCdByItemId(String itemId) throws DAOException {
		final String sql = "select POS_ITEM_CD " +
							 " FROM" +
							 "  W_ITEM_PRODUCT_POS_ASSGN" +
							 " WHERE" +
							 "  ITEM_ID = :itemId";
		if (logger.isDebugEnabled()) {
			logger.debug(sql);
			logger.debug("itemId: " + itemId);
		}
		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("itemId", itemId);
			return this.simpleJdbcTemplate.query(sql, new ParameterizedRowMapper<String>() {
				public String mapRow(ResultSet rs, int rowNum) throws SQLException {
					return rs.getString("POS_ITEM_CD");
				}
			}, params);
		} catch (EmptyResultDataAccessException erdae) {
			logger.debug("EmptyResultDataAccessException");
			return Collections.emptyList();
		} catch (Exception e) {
			logger.error("Exception caught in getPosItemCdByItemId()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public boolean isItemsInGroup(String basketId, String[] vasList, Date appDate, String groupId) throws DAOException {
		if (logger.isDebugEnabled()) {
			logger.debug("isItemsInGroup() is called");
		}
		int count = 0;
		try {
			if (logger.isDebugEnabled()) {
				logger.debug("basketId: " + basketId 
						+ ", vasList: " + (vasList == null ? "(null)" : StringUtils.join(vasList, ' ')) 
						+ ", appDate: " + appDate 
						+ ", groupId: " + groupId);
			}
			String sql = " select count(*) " +
					" from W_ITEM_SELECTION_GRP_ASSGN ISGA " +
					" where ISGA.GRP_ID = :groupId " +
					" and ( 1 <> 1";
			MapSqlParameterSource params = new MapSqlParameterSource();
			if (!this.isEmpty(vasList)) {
				sql += " or ISGA.ITEM_ID in (:vasList)";
				params.addValue("vasList", Arrays.asList(vasList));
			}
			if (StringUtils.isNotBlank(basketId)) {
				sql	+= (
						" or ISGA.ITEM_ID in " + 
					 "       (select BIA.ITEM_ID " + 
					 "           from W_BASKET_ITEM_ASSGN BIA " + 
					 "          where TRUNC(:appDate) between BIA.EFF_START_DATE and TRUNC(NVL(BIA.EFF_END_DATE, sysdate))" + 
					 " 			and BIA.BASKET_ID = :basketid" +
					 " ) "); 
					params.addValue("basketid", basketId);
			}
			sql += ")";
			params.addValue("appDate", appDate, Types.DATE);
			params.addValue("groupId", groupId);
			
			if (logger.isDebugEnabled()) {
				logger.debug("sql: " + sql);
			}
			count = this.simpleJdbcTemplate.queryForInt(sql, params);
			logger.debug("count: " + count);
		} catch (EmptyResultDataAccessException erdae) {
			logger.debug("EmptyResultDataAccessException in isItemsInGroup()");
		} catch (Exception e) {
			logger.warn("Exception caught in isItemsInGroup():", e);
			throw new DAOException(e.getMessage(), e);
		}
		return count > 0;
		
	}
	
	public boolean hasProductionInfo(String[] vasList) throws DAOException {
		if (vasList == null || vasList.length == 0) {
			return false;
		}

		if (logger.isDebugEnabled()) {
			logger.debug("hasProductionInfo() is called");
		}
		int count = 0;
		try {
			if (logger.isDebugEnabled()) {
				logger.debug(", vasList: "
						+ (vasList == null ? "(null)" : StringUtils.join(
								vasList, ' ')));
			}
			String sql = "select count(*) count\n"
					+ "from W_ITEM_PRODUCT_POS_ASSGN IPPA\n"
					+ "    ,W_POS_TRADE_DESC_ASSIGN  PTDA\n"
					+ "    ,W_HS_TRADE_DESC          HTD\n"
					+ "where IPPA.POS_ITEM_CD = PTDA.POS_ITEM_CD\n"
					+ "and PTDA.TRADE_DESC_ID = HTD.ID\n"
					+ "and IPPA.ITEM_ID in (:vasList)";

			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("vasList", Arrays.asList(vasList));

			if (logger.isDebugEnabled()) {
				logger.debug("sql: " + sql);
			}
			count = this.simpleJdbcTemplate.queryForInt(sql, params);
			logger.debug("count: " + count);
		} catch (EmptyResultDataAccessException erdae) {
			logger.debug("EmptyResultDataAccessException in hasProductionInfo()");
		} catch (Exception e) {
			logger.warn("Exception caught in hasProductionInfo():", e);
			throw new DAOException(e.getMessage(), e);
		}
		return count > 0;
	}
	public String getSelectedSimExTraFunction(String appDate, String basketId,  String itemId) throws DAOException {
		String simType ="";
		String SQL = "select (select A.HS_EXTRA_FUNCTION "
					+ " from BOMWEB_STOCK_CATALOG A, " 
					+ " W_ITEM_PRODUCT_POS_ASSGN IPPA WHERE A.ITEM_CODE = IPPA.POS_ITEM_CD " 
					+ " AND IPPA.ITEM_ID = BIA.ITEM_ID) SIM_TYPE "
					+ " from W_BASKET_ITEM_ASSGN BIA, W_ITEM I, W_ITEM_DISPLAY_LKUP IDL " 
					+ " where BIA.ITEM_ID = I.ID and BIA.ITEM_ID = IDL.ITEM_ID and IDL.LOCALE = 'en' " 
					+ " and IDL.DISPLAY_TYPE = 'ITEM_SELECT' " 
					+ " and TO_DATE(?, 'DD/MM/YYYY') between BIA.EFF_START_DATE and NVL(BIA.EFF_END_DATE, sysdate) "
					+ " and I.TYPE = 'SIM' and BIA.BASKET_ID = ? "
                    + " and BIA.ITEM_ID = ? ";
	
		try{
			logger.debug("getSelectedSimType @ VasDetailDAO: " + SQL);
			simType = (String) simpleJdbcTemplate.queryForObject(SQL,
					String.class, appDate, basketId, itemId);
		}catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException");
			simType = "";
		} catch (Exception e) {
			logger.error("Exception caught in getSelectedSimType()", e);
			throw new DAOException(e.getMessage(), e);
		}
		return simType;
					
	}
	
	public String getWaiveVasItemCd(String basketId, String appDate,String channelId) throws DAOException {
		String itemCd = "";
		/*String SQL = "select VIGA.ITEM_ID "
				+ "from W_CHANNEL_BASKET_ASSGN CBA "
				+ "left join W_VAS_ITEM_GRP_ASSGN VIGA on CBA.VAS_ITEM_GRP_ID = VIGA.GRP_ID "
				+ "left join W_ITEM_PRICING IP on VIGA.ITEM_ID = IP.ID "
				+ "left join bomweb_code_lkup CL on VIGA.ITEM_ID = CL.CODE_ID "
				+ "where CBA.BASKET_ID = ? "
				+ "and CL.CODE_TYPE = 'OCTOPUS_ADMIN_WAIVE' "
				+ "and TO_DATE(?, 'DD/MM/YYYY') between CBA.EFF_START_DATE and NVL(CBA.EFF_END_DATE, sysdate) "
				+ "and TO_DATE(?, 'DD/MM/YYYY') between VIGA.EFF_START_DATE and NVL(viga.EFF_END_DATE, sysdate) "
				+ "and TO_DATE(?, 'DD/MM/YYYY') between IP.EFF_START_DATE and NVL(ip.EFF_END_DATE, sysdate) "
				+ "and IP.ONETIME_TYPE = 'A' "
				+ "and CBA.CHANNEL_ID = ?";*/
		String SQL = "select VIGA.ITEM_ID "
				+ "from W_CHANNEL_BASKET_ASSGN CBA, "
				+ "W_VAS_ITEM_GRP_ASSGN VIGA, "
				+ "W_ITEM_PRICING IP, "
				+ "BOMWEB_CODE_LKUP CL "
				+ "where CBA.VAS_ITEM_GRP_ID = VIGA.GRP_ID "
				+ "and VIGA.ITEM_ID = IP.ID "
				+ "and VIGA.ITEM_ID = CL.CODE_ID "
				+ "and CBA.BASKET_ID = ? "
				+ "and CL.CODE_TYPE = 'OCTOPUS_ADMIN_WAIVE' "
				+ "and TO_DATE(?, 'DD/MM/YYYY') between CBA.EFF_START_DATE and NVL(CBA.EFF_END_DATE, sysdate) "
				+ "and TO_DATE(?, 'DD/MM/YYYY') between VIGA.EFF_START_DATE and NVL(viga.EFF_END_DATE, sysdate) "
				+ "and TO_DATE(?, 'DD/MM/YYYY') between IP.EFF_START_DATE and NVL(ip.EFF_END_DATE, sysdate) "
				+ "and IP.ONETIME_TYPE = 'A' "
				+ "and CBA.CHANNEL_ID = ?";
		try{
			logger.debug("getWaiveVasItemCd @ VasDetailDAO: " + SQL);
			itemCd = (String) simpleJdbcTemplate.queryForObject(SQL,
					String.class, basketId, appDate, appDate, appDate, channelId);
		}catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException");
			itemCd = "";
		} catch (Exception e) {
			logger.error("Exception caught in getWaiveVasItemCd()", e);
			throw new DAOException(e.getMessage(), e);
		}
		return itemCd;
	}
	
	public List<String> getOverMaxCountItemList(String[] selectedList, String basketId) throws DAOException {
		if (selectedList == null || selectedList.length == 0) {
			return null;
		}
		
		String SQL = "select html from w_item_display_lkup \n"
				+ " where item_id in ( \n"
				+ "	select distinct item_id from w_item_offer_product_assgn \n"
				+ "	where product_id in ( \n"
				+ "		select product_id from w_max_sub_count msc \n"
				+ "		left join ( \n"
				+ "			select prod_grp_id, count(distinct product_id) c from w_max_sub_count \n"
				+ "			where product_id in ( \n"
				+ "				select iopa.product_id \n"
				+ "				from w_item_offer_product_assgn iopa \n"
				+ "				where iopa.item_id in (:vasList) \n"
				+ "				or iopa.item_id in ( \n"
				+ "				    select item_id \n"
				+ "					from w_basket_item_assgn bia \n"
				+ "					where basket_id=:basketid \n"
				+ "					and sysdate between bia.eff_start_date and nvl(bia.eff_end_date, sysdate) \n"
				+ "				) \n"
				+ "			) \n"
				+ "			group by prod_grp_id \n"
				+ "		) pc on msc.prod_grp_id = pc.prod_grp_id \n"
				+ "		where pc.c > msc.max_sub_count \n"
				+ "	) \n"
				+ "	and item_id in (:vasList) \n"
				+ " ) \n"
				+ "	and locale = 'en' \n"
				+ " and display_type='ITEM_SELECT' \n";

		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
			public String mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return rs.getString("html");
			}
		};

		try {
			logger.debug("getOverMaxCountItemList : " + SQL);
			//System.out.println(basketId);
			//System.out.println(Arrays.toString(selectedList));
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("vasList", Arrays.asList(selectedList));
			params.addValue("basketid", basketId);
			
			return this.simpleJdbcTemplate.query(SQL, mapper, params);
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException caught in getOverMaxCountItemList()");
			return null;
		} catch (Exception e) {
			logger.error("Exception caught in getOverMaxCountItemList()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public BasketParmDTO getBasketParmByType(String basketId, String parmType) throws DAOException {
		logger.debug("getBasketParmByType is called");
		String sql = "SELECT BASKET_ID, PARM_TYPE, PARM_TYPE_VAL, PARM_TYPE_ID \n "
				+ " FROM W_BASKET_PARM \n "
				+ " WHERE BASKET_ID=:basketId \n "
				+ " AND PARM_TYPE=:parmType \n ";
		
		if (logger.isDebugEnabled()) {
			logger.debug(sql);
			logger.debug("basketId: " + basketId + ", parmType: " + parmType);
		}
		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("basketId", basketId);
			params.addValue("parmType", parmType);
			List<BasketParmDTO> list = this.simpleJdbcTemplate
					.query(sql,
					ParameterizedBeanPropertyRowMapper.newInstance(BasketParmDTO.class),
					params);
			
			return CollectionUtils.isEmpty(list) ? null : list.get(0);
			
		} catch (EmptyResultDataAccessException erdae) {
			if (logger.isDebugEnabled()) {
				logger.debug("EmptyResultDataAccessException in getBasketParmByType()");
			}
			return null;
		} catch (Exception e) {
			logger.info("Exception caught in getBasketParmByType():", e);
			throw new DAOException(e.getMessage(), e);
		}
	}	

	public List<RptVasDetailDTO> getReportUseMultiSimRPHSRPList(String locale, //MultiSim Athena 20140128
			String basketId, String orderId,String memberNum)
			throws DAOException {
		List<RptVasDetailDTO> vasDetailList = new ArrayList<RptVasDetailDTO>();
		String SQL = 	"select idl.item_id, " +
				"  i.lob item_lob, " +
				"  i.type item_type, " +
				"  bia.mdo_ind item_mdo_ind, " +
				"  idl.html, " +
				"  idl.html2, " +
				"  idl.locale, " +
				"  idl.display_type, " +
				"  ip.onetime_amt, " +
				"  ip.recurrent_amt, " +
				"  bia.display_seq " +
				"FROM " +
				"  (SELECT bwsi.parent_order_id " +
				"    ||bwsi.member_num order_id, " +
				"    bwsi.parent_order_id, " +
				"    bwsi.item_id, " +
				"    display_type, " +
				"    html, " +
				"    html2, " +
				"    locale " +
				"  from w_item_display_lkup widl, " +
				"    BOMWEB_ORD_MOB_MEMBER_VAS bwsi " +
				"  WHERE widl.item_id = bwsi.item_ID " +
				"  AND bwsi.parent_order_id = :orderId "  +
				"  AND bwsi.member_num = :memberNum " +
				"  AND widl.display_type = 'SS_FORM_RP' " +
				"  and widl.locale       =  :locale" +
				"  ) idl, " +
				"  w_basket_item_assgn bia, " +
				"  w_item i, " +
				"  w_item_pricing ip, " +
				"  bomweb_order bwo " +
				"where bwo.order_id     = idl.parent_order_id " +
				"AND idl.item_id        = bia.item_id(+) " +
				"and bia.basket_id(+)   = :basketId " +
				"and idl.item_id        = i.id " +
				"and i.id               = ip.id " +
				"and trunc(bwo.app_date) between ip.eff_start_date and nvl(ip.eff_end_date, sysdate) \n " +
				"order by bia.display_seq";

		ParameterizedRowMapper<RptVasDetailDTO> mapper = new ParameterizedRowMapper<RptVasDetailDTO>() {

		
			public RptVasDetailDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				RptVasDetailDTO vasDto = new RptVasDetailDTO();
				vasDto.setItemHtml(rs.getString("html"));
				vasDto.setItemType(rs.getString("item_type"));			
				vasDto.setItemHtml2(rs.getString("html2"));
				vasDto.setItemRecurrentAmt(rs.getString("recurrent_amt"));

				return vasDto;
			}
		};
		try {
			logger.debug("getReportUseMultiSimRPHSRPList @ VasDetailDAO: " + SQL);
			logger.info(vasDetailList.size());
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("orderId",orderId);
			params.addValue("memberNum",memberNum);
			params.addValue("locale",locale);
			params.addValue("basketId",basketId);

			vasDetailList = simpleJdbcTemplate.query(SQL, mapper,params);

		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException");
			vasDetailList = null;
		} catch (Exception e) {
			logger.error("Exception caught in getReportUseMultiSimRPHSRPList()", e);
			throw new DAOException(e.getMessage(), e);
		}
		return vasDetailList;

	}
	public List<RptVasDetailDTO> getReportUseMultiSimVasDetailtList(String locale, //MultiSim Athena 20140128
			String basketId, String orderId,String memberNum)
			throws DAOException {

		List<RptVasDetailDTO> vasDetailList = new ArrayList<RptVasDetailDTO>();
		String SQL = "select idl.item_id, " +
				"  i.lob item_lob, " +
				"  i.type item_type, " +
				"  bia.mdo_ind item_mdo_ind, " +
				"  idl.html, " +
				"  idl.html2, " +
				"  idl.locale, " +
				"  idl.display_type, " +
				"  ip.onetime_amt, " +
				"  ip.recurrent_amt, " +
				"  bia.display_seq " +
				"from " +
				"  (select bwsi.parent_order_id " +
				"    ||bwsi.member_num order_id, " +
				"    bwsi.parent_order_id, " +
				"    bwsi.item_id, " +
				"    display_type, " +
				"    html, " +
				"    html2, " +
				"    locale " +
				"  from w_item_display_lkup widl, " +
				"    BOMWEB_ORD_MOB_MEMBER_VAS bwsi " +
				"  WHERE widl.item_id = bwsi.item_id " +
				"  AND bwsi.parent_order_id =:orderId " +
				"  AND bwsi.member_num   = :memberNum " +
				"  AND widl.display_type = 'SS_FORM_VAS' " +
				"  AND widl.locale       = :locale " +
				"  AND bwsi.basket_id   IS NOT NULL " +
				"  and bwsi.item_type    ='BVAS' " +
				"  ) idl," +
				"  w_basket_item_assgn bia, " +
				"  w_item i, " +
				"  w_item_pricing ip, " +
				"  bomweb_order bwo " +
				"where bwo.order_id     = idl.parent_order_id " +
				"AND idl.item_id        = bia.item_id(+) " +
				"and bia.basket_id(+)   = :basketId " +
				"and idl.item_id        = i.id " +
				"and i.id               = ip.id " +
				"and trunc(bwo.app_date) between ip.eff_start_date and nvl(ip.eff_end_date, sysdate) \n " +
				"and I.ID not          in ( " +
				"  (select ISGA.ITEM_ID " +
				"  from W_ITEM_SELECTION_GRP_ASSGN ISGA " +
				"  where ISGA.GRP_ID = 9999999999 " +
				"  )) " +
				"order by bia.display_seq";

		ParameterizedRowMapper<RptVasDetailDTO> mapper = new ParameterizedRowMapper<RptVasDetailDTO>() {

		
			public RptVasDetailDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				RptVasDetailDTO vasDto = new RptVasDetailDTO();
				vasDto.setItemHtml(rs.getString("html"));
				vasDto.setItemType(rs.getString("item_type"));
				vasDto.setItemHtml2(rs.getString("html2"));
				vasDto.setItemRecurrentAmt(rs.getString("recurrent_amt"));

				return vasDto;
			}
		};
		try {
			logger.debug("getReportUseMultiSimVasDetailtList @ VasDetailDAO: " + SQL);
			logger.info(vasDetailList.size());
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("orderId",orderId);
			params.addValue("memberNum",memberNum);
			params.addValue("locale",locale);
			params.addValue("basketId",basketId);

			vasDetailList = simpleJdbcTemplate.query(SQL, mapper,params);

		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException");
			vasDetailList = null;
		} catch (Exception e) {
			logger.error("Exception caught in getReportUseMultiSimVasDetailtList()", e);
			throw new DAOException(e.getMessage(), e);
		}
		return vasDetailList;

	}
	public List<RptVasDetailDTO> getReportUseMultiSimVasOptionalDetailtList(String locale, //MultiSim Athena 20140128
			String basketId, String orderId,String memberNum)
			throws DAOException {

		List<RptVasDetailDTO> vasDetailList = new ArrayList<RptVasDetailDTO>();
		String SQL = "select idl.item_id, " +
				"  i.lob item_lob, " +
				"  i.type item_type, " +
				"  bia.mdo_ind item_mdo_ind, " +
				"  idl.html, " +
				"  idl.html2, " +
				"  idl.locale, " +
				"  idl.display_type, " +
				"  ip.onetime_amt, " +
				"  ip.recurrent_amt, " +
				"  bia.display_seq " +
				"FROM " +
				"  (SELECT bwsi.parent_order_id " +
				"    ||bwsi.member_num order_id, " +
				"    bwsi.parent_order_id, " +
				"    bwsi.item_id, " +
				"    display_type, " +
				"    html, " +
				"    html2, " +
				"    locale " +
				"  from w_item_display_lkup widl, " +
				"    BOMWEB_ORD_MOB_MEMBER_VAS bwsi " +
				"  WHERE widl.item_id = bwsi.item_ID " +
				"  AND bwsi.parent_order_id =:orderId " +
				"  AND bwsi.member_num   = :memberNum " +
				"  AND widl.display_type = 'SS_FORM_VAS' " +
				"  AND widl.locale       = :locale " +
				"  and bwsi.item_type    ='VAS' " +
				"  ) idl, " +
				"  w_basket_item_assgn bia, " +
				"  w_item i, " +
				"  w_item_pricing ip, " +
				"  bomweb_order bwo " +
				"where bwo.order_id     = idl.parent_order_id " +
				"AND idl.item_id        = bia.item_id(+) " +
				"and bia.basket_id(+)   = :basketId " +
				"and idl.item_id        = i.id " +
				"and i.id               = ip.id " +
				"and trunc(bwo.app_date) between ip.eff_start_date and nvl(ip.eff_end_date, sysdate) \n " +
				"and I.ID not          in ( " +
				"  (select ISGA.ITEM_ID " +
				"  from W_ITEM_SELECTION_GRP_ASSGN ISGA " +
				"  WHERE ISGA.GRP_ID = 9999999999 " +
				"  )) " +
				"order by bia.display_seq";

		ParameterizedRowMapper<RptVasDetailDTO> mapper = new ParameterizedRowMapper<RptVasDetailDTO>() {

		
			public RptVasDetailDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				RptVasDetailDTO vasDto = new RptVasDetailDTO();
				vasDto.setItemHtml(rs.getString("html"));
				vasDto.setItemType(rs.getString("item_type"));
				vasDto.setItemHtml2(rs.getString("html2"));
				vasDto.setItemRecurrentAmt(rs.getString("recurrent_amt"));

				return vasDto;
			}
		};
		try {
			logger.debug("getReportUseMultiSimVasOptionalDetailtList @ VasDetailDAO: " + SQL);
			logger.info(vasDetailList.size());
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("orderId",orderId);
			params.addValue("memberNum",memberNum);
			params.addValue("locale",locale);
			params.addValue("basketId",basketId);

			vasDetailList = simpleJdbcTemplate.query(SQL, mapper,params);

		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException");
			vasDetailList = null;
		} catch (Exception e) {
			logger.error("Exception caught in getReportUseMultiSimVasOptionalDetailtList()", e);
			throw new DAOException(e.getMessage(), e);
		}
		return vasDetailList;

	}
	
	public List<String[]> getOctopusMspItemList(String basketId, String appDate,String channelId) throws DAOException {
		List<String[]> list = new ArrayList<String[]>();
		
		String SQL = "SELECT VIGA.ITEM_ID, I.description " +
				"FROM W_CHANNEL_BASKET_ASSGN CBA, " +
				"  W_VAS_ITEM_GRP_ASSGN VIGA, " +
				"  W_ITEM I, " +
				"  BOMWEB_CODE_LKUP CL " +
				"WHERE CBA.VAS_ITEM_GRP_ID = VIGA.GRP_ID " +
				"AND VIGA.ITEM_ID          = I.ID " +
				"AND VIGA.ITEM_ID          = CL.CODE_ID " +
				"AND CBA.BASKET_ID         = :basketId " +
				"AND CL.CODE_TYPE          = 'OCTOPUS_MSP_ITEM' " +
				"AND TO_DATE(:appDate, 'DD/MM/YYYY') BETWEEN CBA.EFF_START_DATE AND NVL(CBA.EFF_END_DATE, sysdate) " +
				"AND TO_DATE(:appDate, 'DD/MM/YYYY') BETWEEN VIGA.EFF_START_DATE AND NVL(viga.EFF_END_DATE, sysdate) " +
				"AND CBA.CHANNEL_ID = :channelId";
		
		ParameterizedRowMapper<String[]> mapper = new ParameterizedRowMapper<String[]>() {

			public String[] mapRow(ResultSet rs, int rowNum) throws SQLException {
				String[] result = new String[2];
				result[0] = rs.getString("ITEM_ID");
				result[1] = rs.getString("description");
				return result;
			}
		};
		
		try{
			logger.debug("getOctopusMspItemList @ VasDetailDAO: " + SQL);
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("basketId",basketId);
			params.addValue("appDate",appDate);
			params.addValue("channelId",channelId);

			list = simpleJdbcTemplate.query(SQL, mapper,params);
		}catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException");
			list = null;
		} catch (Exception e) {
			logger.error("Exception caught in getOctopusMspItemList()", e);
			e.printStackTrace();
			throw new DAOException(e.getMessage(), e);
		}
		return list;
	}

	public boolean hasSimInBasket(String basketId,String appDate) throws DAOException { 
		logger.debug("hasSimInBasket() is called");
		String SQL="SELECT count(1) FROM W_BASKET_ITEM_ASSGN A, W_ITEM B WHERE BASKET_ID =:basketId AND A.ITEM_ID=B.ID AND B.TYPE='SIM' AND TO_DATE(:appDate, 'DD/MM/YYYY') BETWEEN EFF_START_DATE AND NVL(EFF_END_DATE, SYSDATE)";
		int cnt=0;

		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("basketId", basketId);
			params.addValue("appDate", appDate);
			cnt =  simpleJdbcTemplate.queryForInt(SQL,  params);
			
		} catch (EmptyResultDataAccessException erdae) {
			throw new AppRuntimeException(erdae);

		} catch (Exception e) {
			logger.error("Exception caught in hasSimInBasket()", e);
			throw new DAOException(e.getMessage(), e);
		}
		
		return (cnt>0) ? true:false;
	}
	
	public BigDecimal getChangeSimChargeForOrder(String orderId) throws DAOException {
		String sql = "SELECT ADMIN_CHARGE " +
				"FROM BOMWEB_ORD_MOB_CHG_SIM_TXN " +
				"WHERE mark_del_ind  <> 'Y' " +
				"AND waive_reason_cd IS NULL " +
				"AND order_id         = :orderId";
		try {
			logger.debug("getChangeSimChargeForOrder() @ VasDetailDAO: " + sql);

			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("orderId", orderId);
			BigDecimal amount = this.simpleJdbcTemplate.queryForObject(sql, BigDecimal.class, params);
			return amount;
			
		} catch (EmptyResultDataAccessException erdae) {
			if (logger.isDebugEnabled()) {
				logger.debug("EmptyResultDataAccessException in getChangeSimChargeForOrder()");
			}
			return null;
		} catch (Exception e) {
			logger.info("Exception caught in getChangeSimChargeForOrder():", e);
			throw new DAOException(e.getMessage(), e);
		}
	}

	public BigDecimal getPrePaymentForOrder(String orderId) throws DAOException {
		String sql = "SELECT NVL(SUM(ONETIME_AMT),0) PREPAYMENT " 
				+"FROM BOMWEB_SUBSCRIBED_ITEM A, " 
				+"  W_ITEM_PRICING B, " 
				+"  BOMWEB_ORDER BO " 
				+"WHERE A.ORDER_ID= :orderId " 
				+"AND A.ID=B.ID " 
				+"AND A.ORDER_ID=BO.ORDER_ID " 
				+"AND TRUNC(BO.APP_DATE) BETWEEN TRUNC(NVL(B.EFF_START_DATE,SYSDATE)) AND TRUNC(NVL(B.EFF_END_DATE,SYSDATE)) " 
				//+"AND NVL(B.ONETIME_TYPE,'#')!='A' " 
				+"AND NVL(B.PAYMENT_GROUP,'#')!='HANDSET'"
				+"AND A.WAIVE_REASON IS NULL";
		try {
			logger.debug("getPrePaymentForOrder() @ VasDetailDAO: " + sql);

			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("orderId", orderId);
			BigDecimal amount = this.simpleJdbcTemplate.queryForObject(sql, BigDecimal.class, params);
			return amount;
			
		} catch (EmptyResultDataAccessException erdae) {
			if (logger.isDebugEnabled()) {
				logger.debug("EmptyResultDataAccessException in getPrePaymentForOrder()");
			}
			return null;
		} catch (Exception e) {
			logger.info("Exception caught in getPrePaymentForOrder():", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public BigDecimal getBasketHsAmtForOrder(String orderId) throws DAOException {
		String sql = "SELECT NVL(SUM(ONETIME_AMT),0) PREPAYMENT " 
				+"FROM BOMWEB_SUBSCRIBED_ITEM A, " 
				+"  W_ITEM_PRICING B, " 
				+"  W_ITEM I, " 
				+"  BOMWEB_ORDER BO " 
				+"WHERE A.ORDER_ID= :orderId " 
				+"AND A.ID=B.ID " 
				+"AND I.ID=B.ID " 
				+"AND A.ORDER_ID=BO.ORDER_ID " 
				+"AND TRUNC(BO.APP_DATE) BETWEEN TRUNC(NVL(B.EFF_START_DATE,SYSDATE)) AND TRUNC(NVL(B.EFF_END_DATE,SYSDATE)) " 
				+"AND NVL(I.TYPE,'#')='HS'";
				//+"AND NVL(B.PAYMENT_GROUP,'#')='HANDSET'";

		try {
			logger.debug("getBasketHsAmtForOrder() @ VasDetailDAO: " + sql);

			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("orderId", orderId);
			BigDecimal amount = this.simpleJdbcTemplate.queryForObject(sql, BigDecimal.class, params);
			return amount;
			
		} catch (EmptyResultDataAccessException erdae) {
			if (logger.isDebugEnabled()) {
				logger.debug("EmptyResultDataAccessException in getBasketHsAmtForOrder()");
			}
			return null;
		} catch (Exception e) {
			logger.info("Exception caught in getBasketHsAmtForOrder():", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public List<String> getUnmatchDocAssignedVas(String[] selectedList, String docType, Date appDate) throws DAOException {
		if (selectedList == null || selectedList.length == 0) {
			return null;
		}
		
		String SQL = "select distinct i.description "
				+ "from w_item_cat_doc_assgn icda "
				+ "join w_item i on icda.category_id = i.category_id "
				+ "where i.id in (:vasList) "
				+ "and not exists ( "
				+ "  select 1 from w_item_cat_doc_assgn a where a.category_id = i.category_id and a.doc_type = :docType "
				+ "  and trunc(:appDate) between trunc(nvl(start_date, :appDate)) and trunc(nvl(end_date, sysdate))) "
				+ "and trunc(:appDate) between trunc(nvl(start_date, :appDate)) and trunc(nvl(end_date, sysdate))";

		try {
			logger.debug("getUnmatchDocAssignedVas : " + SQL);
			
			ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
				public String mapRow(ResultSet rs, int rowNum) throws SQLException {
					return rs.getString("description");
				}
			};
			
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("vasList", Arrays.asList(selectedList));
			params.addValue("docType", docType);
			params.addValue("appDate", appDate);
			
			return this.simpleJdbcTemplate.query(SQL, mapper, params);
			
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException caught in getUnmatchDocAssignedVas()");
			return null;
		} catch (Exception e) {
			logger.error("Exception caught in getUnmatchDocAssignedVas()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public List<VasDetailDTO> getSystemAssignVas(String vasCodeType, String appDate, String locale, String mipBrand, String mipSimType) throws DAOException {
		if (StringUtils.isBlank(vasCodeType)) {
			return null;
		}
		
		String SQL = 
				  "select bcl.code_id, bcl.code_type, widl.html, wip.onetime_amt, wip.recurrent_amt, NVL(i.mip_brand, '1') MIP_BRAND, NVL(i.mip_sim_type, 'H') MIP_SIM_TYPE "
				+ "from bomweb_code_lkup bcl "
				+ "join w_item_display_lkup widl on bcl.code_type = :vasCodeType and bcl.code_id = widl.item_id "
				+ "join w_item_pricing wip on bcl.code_id = wip.id "
				+ "join w_item i on widl.item_id = i.id "
				+ "where widl.display_type = 'ITEM_SELECT' "
				+ "and widl.locale = :locale "
				+ "and to_date(:appDate, 'DD/MM/YYYY') between wip.eff_start_date and nvl(wip.eff_end_date, sysdate) "
				+ "and nvl(decode (i.MIP_BRAND, '9', :mipBrand, i.MIP_BRAND ), '1') = :mipBrand "
				+ "and nvl(decode (i.MIP_SIM_TYPE, 'X', :mipSimType, i.MIP_SIM_TYPE ), 'H') = :mipSimType ";

		try {
			logger.debug("getSystemAssignVas : " + SQL);
			
			ParameterizedRowMapper<VasDetailDTO> mapper = new ParameterizedRowMapper<VasDetailDTO>() {
				public VasDetailDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
					VasDetailDTO dto = new VasDetailDTO();
					dto.setItemId(rs.getString("code_id"));
					dto.setCategoryDesc(rs.getString("code_type"));
					dto.setItemHtml(rs.getString("html"));
					dto.setItemOnetimeAmt(rs.getString("onetime_amt"));
					dto.setItemRecurrentAmt(rs.getString("recurrent_amt"));
					dto.setMipBrand(rs.getString("MIP_BRAND"));
					dto.setMipSimType(rs.getString("MIP_SIM_TYPE"));
					return dto;
				}
			};
			
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("vasCodeType", vasCodeType);
			params.addValue("locale", locale);
			params.addValue("appDate", appDate);
			params.addValue("mipBrand", mipBrand);
			params.addValue("mipSimType", mipSimType);
			
			return this.simpleJdbcTemplate.query(SQL, mapper, params);
			
		} catch (Exception e) {
			logger.error("Exception caught in getSystemAssignVas()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public String checkBasketVas(String basketId, String appDate, String[] vasItemId) throws DAOException {
		final String[] inItemId = vasItemId;
		String result = null;
		try {
			SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
						.withSchemaName("OPS$CNM")
						.withCatalogName("PKG_SB_MOB_ITEM_VALIDATE")
						.withProcedureName("check_basket_vas");
			simpleJdbcCall.declareParameters(
						 new SqlParameter("i_basket_id", Types.VARCHAR),
						 new SqlParameter("i_app_date", Types.VARCHAR),
						 new SqlParameter("i_vas_item", OracleTypes.ARRAY, "VARR_ITEM_ID"),
						 new SqlOutParameter("o_nRetVal", Types.INTEGER),
						 new SqlOutParameter("o_nErrCode", Types.INTEGER),
						 new SqlOutParameter("o_sErrText", Types.VARCHAR)
					);
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("i_basket_id", basketId);
			params.addValue("i_app_date", appDate);
			params.addValue("i_vas_item", new AbstractSqlTypeValue() {
				@Override
				protected Object createTypeValue(Connection conn, int sqlType, String typeName) throws SQLException {
					final Connection dConn = (new CommonsDbcpNativeJdbcExtractor()).getNativeConnection(conn);
					ArrayDescriptor arrayDescriptor = new ArrayDescriptor(typeName, dConn);
					return new ARRAY(arrayDescriptor, dConn, inItemId);
				}
			});
			Map<String, Object> out = simpleJdbcCall.execute(params);
			if (out.get("o_nRetVal") instanceof Integer) {
				int oReturnValue = (Integer) out.get("o_nRetVal");
				logger.info("checkBasketVas() Result: " + (String) out.get("o_sErrText"));
				if (oReturnValue != 0) {
					result = (String) out.get("o_sErrText");
				}
			}
		} catch (Exception e) {
			logger.error("Exception caught in checkBasketVas()", e);
		}
		return result;
	}
	
	public List<String> getHsItemIdByBasket(String basketId, Date appDate) throws DAOException {
		if (StringUtils.isBlank(basketId)) {
			return null;
		}
		
		String SQL = 
				  "select bia.item_id "
				  + "from w_basket_item_assgn bia "
				  + "join w_item i on bia.item_id = i.id "
				  + "where i.type = 'HS' "
				  + "and trunc(:appDate) between trunc(bia.eff_start_date) and trunc(nvl(bia.eff_end_date, sysdate)) "
				  + "and bia.basket_id = :basketId ";

		try {
			logger.debug("getSystemAssignVas : " + SQL);
			
			ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
				public String mapRow(ResultSet rs, int rowNum) throws SQLException {
					return rs.getString("item_id");
				}
			};
			
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("basketId", basketId);
			params.addValue("appDate", appDate);
			
			return this.simpleJdbcTemplate.query(SQL, mapper, params);
		} catch (Exception e) {
			logger.error("Exception caught in getSystemAssignVas()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public List<String> getDummyWaiveRpPrepayItemExist(String basketId, String appDate) throws DAOException {
		if (appDate == null || appDate == "") {
			appDate = Utility.date2String(new Date(), "dd/MM/yyyy");
		}
		
		String SQL = "SELECT BIA.ITEM_ID " +
				"FROM w_basket b, " +
				"  w_item i, " +
				"  W_BASKET_ITEM_ASSGN bia, " +
				"  bomweb_code_lkup cl " +
				"WHERE b.id      = bia.basket_id " +
				"AND bia.item_id = i.id " +
				"AND bia.item_id = cl.code_id " +
				"AND bia.basket_id = :basketId " +
				"AND TO_DATE(:appDate, 'DD/MM/YYYY') BETWEEN trunc(BIA.EFF_START_DATE) AND trunc(NVL(BIA.EFF_END_DATE, sysdate)) " +
				"AND cl.code_type = 'DEFAULT_WAIVE_RP_PRE_PAYMENT_ITEM' ";

		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
			public String mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return rs.getString("ITEM_ID");
			}
		};

		try {
			logger.debug("getDummyWaiveRpPrepayItemExist : " + SQL);
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("basketId", basketId);
			params.addValue("appDate", appDate);
			
			return this.simpleJdbcTemplate.query(SQL, mapper, params);
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException caught in getDummyWaiveRpPrepayItemExist()");
			return null;
		} catch (Exception e) {
			logger.error("Exception caught in getDummyWaiveRpPrepayItemExist()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}

	public boolean isYahooCoupon(String orderId)throws DAOException {
		
		try {
			    String sql = "SELECT COUNT(id) " 
			    		+"FROM bomweb_subscribed_item " 
			    		+"WHERE order_id = :orderId " 
			    		+"AND id        IN " 
			    		+"  (SELECT a.item_id " 
			    		+"  FROM w_item_offer_product_assgn a, " 
			    		+"    bomweb_code_lkup b " 
			    		+"  WHERE a.product_id =b.code_id " 
			    		+"  AND b.code_type    ='YAHOO_COUPON_PROD' " 
			    		+"  ) " ;

		        MapSqlParameterSource params = new MapSqlParameterSource();
		        params.addValue("orderId", orderId);
		        int count = simpleJdbcTemplate.queryForInt(sql, params);
		        return count > 0;
	    }catch (Exception e) {
			logger.info("Exception caught in isYahooCoupon():", e);
			throw new DAOException(e);
		}
     }

	public String getYahooCouponSerial(String orderId) throws DAOException {
		
		String SQL = "SELECT comp.attb_value " 
				+"FROM bomweb_subscribed_item bsi " 
				+"JOIN w_item_offer_product_assgn prod " 
				+"ON bsi.id = prod.item_id " 
				+"JOIN bomweb_code_lkup yahoo " 
				+"ON yahoo.code_type  = 'YAHOO_COUPON_PROD' " 
				+"AND prod.product_id = yahoo.code_id " 
				+"JOIN w_product_attb_assgn attb " 
				+"ON prod.product_id = attb.product_id " 
				+"JOIN bomweb_component comp " 
				+"ON bsi.order_id    = comp.order_id " 
				+"AND attb.attb_id   = comp.attb_id " 
				+"WHERE bsi.order_id = :orderId";
		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
			public String mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return rs.getString("ATTB_VALUE");
			}
		};
		
		try {
			logger.debug("getYahooCouponSerial : " + SQL);
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("orderId", orderId);
			
			List<String> resultList = this.simpleJdbcTemplate.query(SQL, mapper, params);
			return CollectionUtils.isEmpty(resultList) ? null : resultList.get(0);
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException caught in getYahooCouponSerial()");
			return null;
		} catch (Exception e) {
			logger.error("Exception caught in getYahooCouponSerial()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	
	
	public String getMipSimType(String itemId)
			throws DAOException {
		String simType = "";

		String SQL = "SELECT DECODE (I.MIP_SIM_TYPE, 'X', 'H', I.MIP_SIM_TYPE ) sim_type " +
				"FROM w_item i " +
				"WHERE i.id =:itemId ";

		try {
			logger.debug("getMipSimType @ VasDetailDAO: " + SQL);
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("itemId", itemId);
			
			simType = (String) simpleJdbcTemplate.queryForObject(SQL,
					String.class, params);

		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException");
			simType = "";
		} catch (Exception e) {
			logger.error("Exception caught in getMipSimType()", e);
			throw new DAOException(e.getMessage(), e);
		}
		return simType;

	}

	public String getBasketHardBundleGrpId(String basketId, String mipBrand, String appDate) throws DAOException {
		logger.debug("getBasketHardBundleGrpId is called");

		String SQL = "select hbvg.grp_id\n" +
				"from w_hard_bundle_vas_grp hbvg join w_basket_attribute_mv bamv\n" +
				//"on hbvg.mip_brand = bamv.mip_brand\n" +
				"on hbvg.mip_brand = nvl(decode (bamv.MIP_BRAND, '9', :mipBrand, bamv.MIP_BRAND ), '1')\n" +
				"and hbvg.nature = nvl(bamv.nature, 'ACQ')\n" +
				"and hbvg.contract_period_id = bamv.contract_period_id\n" +
				"where bamv.basket_id = :V_BASKET_ID\n" +
				"and TO_DATE(:V_APP_DATE_STR, 'DD/MM/YYYY') between trunc(hbvg.eff_start_date) and trunc(nvl(hbvg.eff_end_date, sysdate))";

		
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("mipBrand", mipBrand);
		params.addValue("V_BASKET_ID", basketId);
		params.addValue("V_APP_DATE_STR", appDate);

		try {
			logger.debug("getHardBundleVasDetailList @ VasDetaillDTO: " + SQL);
			return (String) simpleJdbcTemplate.queryForObject(SQL,String.class,params);
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("getHardBundleVasDetailList() EmptyResultDataAccessException");
		} catch (Exception e) {
			logger.error("Exception caught in getHardBundleVasDetailList()", e);
			throw new DAOException(e.getMessage(), e);
		}
		return null;

	}
	
	public BasketMinVasLkupDTO getBasketMinVasLkup(String basketId, String appDate) throws DAOException {
		logger.debug("getBasketMinVasLkup is called");

		String SQL = "select bmvl.basket_id, bmvl.min_vas, bmvl.eff_start_date, bmvl.eff_end_date\n" +
				"from w_basket_min_vas_lkup bmvl \n" +
				"where bmvl.basket_id = :V_BASKET_ID\n" +
				"and TO_DATE(:V_APP_DATE_STR, 'DD/MM/YYYY') between trunc(bmvl.eff_start_date) and trunc(nvl(bmvl.eff_end_date, sysdate))\n";

		ParameterizedRowMapper<BasketMinVasLkupDTO> mapper = new ParameterizedRowMapper<BasketMinVasLkupDTO>() {
			public BasketMinVasLkupDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				BasketMinVasLkupDTO dto = new BasketMinVasLkupDTO();
				dto.setBasketId(rs.getString("basket_id"));
				dto.setMinVas(rs.getFloat("min_vas"));
				dto.setEffStartDate(rs.getDate("eff_start_date"));
				dto.setEffEndDate(rs.getDate("eff_end_date"));
				return dto;
			}
		};
		
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("V_BASKET_ID", basketId);
		params.addValue("V_APP_DATE_STR", appDate);

		try {
			logger.debug("getBasketMinVasLkup @ VasDetaillDTO: " + SQL);
			List<BasketMinVasLkupDTO> result = simpleJdbcTemplate.query(SQL,mapper,params);
			if (CollectionUtils.isNotEmpty(result)) {
				return result.get(0);
			}
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("getBasketMinVasLkup() EmptyResultDataAccessException");
		} catch (Exception e) {
			logger.error("Exception caught in getBasketMinVasLkup()", e);
			throw new DAOException(e.getMessage(), e);
		}
		return null;
	}
	
	
	public ResultDTO basketValidate(String basketId, Date appDate) throws DAOException {
		Boolean resultBool= false;
		String errorMessage="";
		

	

		try {
			SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
					.withSchemaName("OPS$CNM")
					.withCatalogName("pkg_sb_mob_util")
					.withProcedureName("basket_validation");
			jdbcCall.setAccessCallParameterMetaData(false);
			jdbcCall.declareParameters(new SqlParameter("iv_basket_id",Types.VARCHAR),
					new SqlParameter("id_app_date", Types.DATE),
					new SqlOutParameter("on_retval", Types.INTEGER),
			new SqlOutParameter("on_errcode", Types.INTEGER),
					new SqlOutParameter("ov_errtext", Types.VARCHAR)
			);

			MapSqlParameterSource inMap = new MapSqlParameterSource();
			inMap.addValue("iv_basket_id", basketId);
			inMap.addValue("id_app_date", appDate);
			SqlParameterSource in = inMap;

			Map<String, Object> out = jdbcCall.execute(in);

			logger.info("basketValidate [basketId][appDate]= " + basketId + appDate);

			int errcode = 0;
			if (((Integer) out.get("on_errcode")) != null) {
				errcode = ((Integer) out.get("on_errcode")).intValue();
			}
			
			int retVal = 0;
			if (((Integer) out.get("on_retval")) != null) {
				retVal = ((Integer) out.get("on_retval")).intValue();
			}

			String errorText = (String) out.get("ov_errtext");
			logger.info("basket_validation output [errcode]= " + errcode
					+ " [errorText]" + errorText);
			
			if (retVal == 0){
				resultBool =true;
				
			}else{
				resultBool = false;

				errorMessage = String.format("Basket Validation Failed: %s [code: %d]", errorText, errcode);
			}

		} catch (Exception e) {
			logger.error("Exception caught in basket_validation()", e);
			throw new DAOException(e.getMessage(), e);

		}
		ResultDTO re= new ResultDTO();
		re.setReturnBool(resultBool);
		re.setReturnMessage(errorMessage);
		
		return re;

	}
	public boolean getVasSelected(String orderId) throws DAOException {
		String sql = "select count(*) as noOfRecord from bomweb_subscribed_item where order_id = :orderId and type = 'VAS'";
		ParameterizedRowMapper<Integer> mapper = new ParameterizedRowMapper<Integer>() {
			public Integer mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				Integer noOfRecord = rs.getInt("noOfRecord");
				return noOfRecord;
			}
		};
		
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("orderId", orderId);

		try {
			logger.debug("getVasSelected @ VasDetaillDTO: " + sql);
			Integer noOfRecord = simpleJdbcTemplate.queryForObject(sql,mapper,params);
			if (noOfRecord > 0)
				return true;
			else {
				return false;
			}
			
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("getVasSelected() EmptyResultDataAccessException");
		} catch (Exception e) {
			logger.error("Exception caught in getVasSelected()", e);
			throw new DAOException(e.getMessage(), e);
		}
		return false;
	}
	
	////////////////
	public boolean validJOC(String basketId, List<String> vasList, Date appDate) throws DAOException {
		logger.debug("validJOC @ VasDetailDAO is called");
		List<String> resultList = new ArrayList<String> ();
		MapSqlParameterSource params = new MapSqlParameterSource();

			String sql = " select * from W_ITEM_SELECTION_GRP_ASSGN ISGA "
					 + " where ISGA.GRP_ID in ('7777777777','7777777778') "
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
			ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
				public String mapRow(ResultSet rs, int rowNum)
						throws SQLException {
					String temp =rs.getString("ITEM_ID");
					return temp;
				}
			};
		try {
			logger.debug("validJOC() @ VasDetailDAO:" + sql);
			resultList = simpleJdbcTemplate.query(sql, mapper,params);
			if (CollectionUtils.isEmpty(resultList)){
				return false;
			}else{
				return true;
			}
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("validJOC() EmptyResultDataAccessException");
			return false;
		} catch (Exception e) {
			logger.error("Exception caught in validJOC()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public List<VasDetailDTO> getOnlineReportUseRPHSRPList(String orderId)
			throws DAOException {
		List<VasDetailDTO> vasDetailList = new ArrayList<VasDetailDTO>();

		String sql = "select service_plan_remark_eng, service_plan_remark_chi from bomweb_ord_mob_cur_prod "
				+ "where order_id=:orderId and prod_type = 'R'";

		ParameterizedRowMapper<VasDetailDTO> mapper = new ParameterizedRowMapper<VasDetailDTO>() {

			public VasDetailDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				VasDetailDTO vasDto = new VasDetailDTO();
				vasDto.setItemHtml(rs.getString("service_plan_remark_eng"));
				vasDto.setItemHtml2(rs.getString("service_plan_remark_chi"));
				return vasDto;
			}
		};

		try {
			logger.debug("getOnlineReportUseRPHSRPList @ HandsetModelDTO: " + sql);
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("orderId", orderId);
			vasDetailList = simpleJdbcTemplate.query(sql, mapper,params);

		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException");
			vasDetailList = null;
		} catch (Exception e) {
			logger.error("Exception caught in getOnlineReportUseRPHSRPList()", e);
			throw new DAOException(e.getMessage(), e);
		}
		return vasDetailList;

	}
	///////////////
	private String calculateNetMonthlyFee(String recurrentAmt, String contractPeriod,String rebateAmt ){
		double netMonthlyFee;
		String netMonthlyFeeStr = "";
		int contractPeriodInt;	
		double recurrentAmtDbl;		
		double rebateAmtDbl;
		
		if(StringUtils.isBlank(contractPeriod)) {
			//System.out.println("string blank");
			return netMonthlyFeeStr;
		}
		
		try {
			 //System.out.println("contract period to int");
			 contractPeriodInt = Integer.parseInt(contractPeriod);			
	       } catch(Exception e) {
	    	  // System.out.println("contract period to int ee");
	    	   return netMonthlyFeeStr;
	       }
		
						
		try {
			//System.out.println("recurrent amout to double");
			recurrentAmtDbl = Double.parseDouble(recurrentAmt);
	       } catch(Exception e) {
	    	   //System.out.println("recurrent amout to double ee");
	    	   recurrentAmtDbl = 0.00;
	       }
		
		try {
			//System.out.println("rebate amout to double");
			rebateAmtDbl = Double.parseDouble(rebateAmt);
	       } catch(Exception e) {
	    	   //System.out.println("rebate amout to double ee");
	    	   rebateAmtDbl = 0.00;
	       }
		
		netMonthlyFee = (( recurrentAmtDbl * contractPeriodInt ) - rebateAmtDbl)/ contractPeriodInt;		
		netMonthlyFeeStr = String.valueOf(Math.round(netMonthlyFee));
				
		return netMonthlyFeeStr;
	}
	
	// for HKTCare, Eagle use only
	public VasDetailDTO getVasItemDetail(String itemId) throws DAOException {
		
		List<String> itemSelectionGroupIds = Arrays.asList(
				GenericReportHelper.HELPERCARE_INSURANCE_FORM_ITEM_SELECTTION_GROUP_ID,
				GenericReportHelper.TRAVEL_INSURANCE_FORM_ITEM_SELECTTION_GROUP_ID,
				ProjectEagleReportHelper.ITEM_SELECTTION_GROUP_ID);
		
		List<String> bomGroupCategories = Arrays.asList(
				"HKTCARE",
				"RESTART");

		String sql = "SELECT iopa.item_id,\n"+
				"iopa.prod_cd,  \n"+
				"idl.HTML,  \n"+
				"idrrv.contract_period  \n"+
				"FROM w_item_offer_product_assgn iopa,  \n"+
				"w_item_display_lkup idl,  \n"+
				"w_item_dtl_rp_rb_vas idrrv  \n"+
				"WHERE iopa.item_id   = idl.item_id  \n"+
				"AND idl.item_id     = idrrv.ID  \n"+
				"AND idl.display_type= 'SS_FORM_VAS'  \n"+
				"AND idl.locale      = 'en'  \n"+
				"AND iopa.item_id     = :itemId\n"+
				"AND iopa.prod_cd IN\n"+
				"(\n"+
				"SELECT prod_cd FROM w_item_offer_product_assgn  \n"+
				"WHERE item_id IN\n"+
				"(SELECT item_id FROM w_item_selection_grp_assgn WHERE grp_id IN (:itemSelectionGroupIds) AND item_id = :itemId)\n"+
				"AND prod_cd IN\n"+
				"(SELECT offer_cd FROM w_srv_line_group_lkup WHERE grp_cat IN (:bomGroupCategories))\n"+
				"UNION\n"+
				"SELECT offer_cd FROM w_item_offer_assgn\n"+
				"WHERE item_id IN\n"+
				"(SELECT item_id FROM w_item_selection_grp_assgn WHERE grp_id IN (:itemSelectionGroupIds) AND item_id = :itemId)\n"+
				"AND offer_cd IN\n"+
				"(SELECT offer_cd FROM w_srv_line_group_lkup WHERE grp_cat IN (:bomGroupCategories))\n"+
				")";

		ParameterizedRowMapper<VasDetailDTO> mapper = new ParameterizedRowMapper<VasDetailDTO>() {

			public VasDetailDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				VasDetailDTO vasDto = new VasDetailDTO();
				vasDto.setItemId(rs.getString("item_id"));
				vasDto.setItemHtml(rs.getString("html"));
				vasDto.setProdCd(rs.getString("prod_cd"));
				vasDto.setContractPeriod(rs.getString("contract_period"));
				return vasDto;
			}
		};
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("itemId", itemId);
		params.addValue("itemSelectionGroupIds", itemSelectionGroupIds);
		params.addValue("bomGroupCategories", bomGroupCategories);

		try {
			logger.debug("getVasItemDetail() @ VasDetailDTO: " + sql);
			List<VasDetailDTO> result = simpleJdbcTemplate.query(sql,mapper,params);
			if (CollectionUtils.isNotEmpty(result)) {
				return result.get(0);
			}
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException");
		} catch (Exception e) {
			logger.error("Exception caught in getVasItemDetail()", e);
			throw new DAOException(e.getMessage(), e);
		}
		return null;
	}
}
