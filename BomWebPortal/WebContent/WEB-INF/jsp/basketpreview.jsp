
<%@ page contentType="text/html;charset=BIG5" import="java.sql.*"%>
<%@ page import="java.sql.Connection,java.sql.ResultSet,java.sql.Statement,javax.naming.*,javax.sql.*"%>

<HTML>
<head>

 
<style type="text/css" title="currentStyle">
@import "css/demo_page.css";

@import "css/demo_table.css";
</style>
 
<script type="text/javascript" language="javascript"	src="js/jquery-Tables.js"></script>
<script type="text/javascript" language="javascript"	src="js/jquery.dataTables.js"></script>
 

<link href="css/ssc2.css" rel="stylesheet" type="text/css">
<script type="text/javascript">
	$(document).ready(function() {
		//$('#tableOffer').dataTable();
	});
</script>
</head>

<BODY >
<%
	Class.forName("oracle.jdbc.driver.OracleDriver");

	 Context initContext = new InitialContext();
	 Context envContext  = (Context)initContext.lookup("java:/comp/env");
	 DataSource datasource = (DataSource)envContext.lookup("jdbc/BomWebPortalDS");
	 Connection con2 = datasource.getConnection();


	Statement stmt2 = con2.createStatement();

	String basketId = (String) request.getParameter("basketId");

	String sqlBatchInfo=
		"select H.BASKET_ID,\n" +
		"       H.BATCH_ID,\n" + 
		"       H.BASKET_DESC,\n" + 
		"       H.RP_CD,\n" + 
		"       H.BUNDEL_CD,\n" + 
		"       H.MCO_CD,\n" + 
		"       H.RATE_PLAN,\n" + 
		"       H.REBATE_AMT\n" + 
		"  from W_CREATE_BASE_BASKET_HEADER H\n" + 
		" where H.BASKET_ID = " + basketId;


	String sqlBasketBasicInfo =

	"select B.ID BASKET_ID,cba.channel_id, \n"
			+ "       DECODE(B.BASE_BASKET_ID, null, 'base basket', 'device basket')BASKET_INFO,\n"
			+ "       B.DESCRIPTION,\n"
			+ "       B.CREATE_BY,\n"
			+ "       B.CREATE_DATE, "
			+ "	DECODE(B.BASE_BASKET_ID, null,'http://uat.0060everywhere.com/BomWebPortal/vasdetail.html?basket='||B.ID||'&color=&model=&brand=', null) basket_basket_path"
			+ "  from W_BASKET B, w_channel_basket_assgn cba\n"
			+ " where b.id= cba.basket_id(+)  and  B.ID =" + basketId;

	String sqlDeviceBasketPath = "select b.id, idh.brand_id, idh.model_id, idh.color_id, 'http://uat.0060everywhere.com/BomWebPortal/vasdetail.html?basket='||b.id||'&color='||idh.color_id||'&model='||idh.model_id||'&brand='||idh.brand_id||'' PATH\n"
			+ "    from W_BASKET B, W_BASKET_ITEM_ASSGN BIA, W_ITEM_DTL_HS IDH\n"
			+ "   where B.ID = BIA.BASKET_ID\n"
			+ "     and BIA.ITEM_ID = IDH.ID\n"
			+ "     and B.ID ="
			+ basketId;

	String sqlBasketTitleDisplay = "select dbl.basket_id, dbl.html from W_BASKET_DISPLAY_LKUP DBL where DBL.BASKET_ID = "
			+ basketId;

	//--HS basket will display  HSRB_PROMOT
	//--base basket will display RP_PROMOT
	String sqlBasketBlock = "select IDL.ITEM_ID, IDL.LOCALE, IDL.DISPLAY_TYPE, IDL.HTML\n"
			+ "  from W_ITEM_DISPLAY_LKUP IDL, W_BASKET_ITEM_ASSGN BIA\n"
			+ " where BIA.ITEM_ID = IDL.ITEM_ID\n"
			+ "   and IDL.DISPLAY_TYPE = DECODE((select count(B.BASE_BASKET_ID)\n"
			+ "                                   from W_BASKET B\n"
			+ "                                  where B.ID = BIA.BASKET_ID),\n"
			+ "                                 1,\n"
			+ "                                 'HSRB_PROMOT',\n"
			+ "                                 'RP_PROMOT')\n"
			+ "   and BIA.BASKET_ID = " + basketId;

	String NO_USE_sqlBasketBlock = "select  IDL.Item_Id, idl.locale, IDL.DISPLAY_TYPE, IDL.HTML\n"
			+ "  from W_ITEM_DISPLAY_LKUP IDL, W_BASKET_ITEM_ASSGN BIA\n"
			+ " where BIA.ITEM_ID = IDL.ITEM_ID\n"
			+ "   and IDL.DISPLAY_TYPE in ('HSRB_PROMOT', 'RP_PROMOT')\n"
			+ "   and bia.Basket_Id=" + basketId;

	String sqlBasketDisplay = "select I.ITEM_ID,\n"
			+ "       BIA.DISPLAY_SEQ,\n"
			+ "       BIA.EFF_START_DATE,\n"
			+ "       BIA.EFF_END_DATE,\n"
			+ "       WEB_ENG.WEB_ENG_HTML,\n"
			+ "       WEB_CHI.WEB_CHI_HTML,\n"
			+ "       SS_ENG.SS_ENG_HTML,\n"
			+ "       SS_CHI.SS_CHI_HTML,\n"
			+ "       IP.ONETIME_AMT,\n"
			+ "       IP.RECURRENT_AMT\n"
			+ "  from (select distinct ITEM_ID from W_ITEM_DISPLAY_LKUP) I,\n"
			+ "       (select ITEM_ID, HTML WEB_ENG_HTML\n"
			+ "          from W_ITEM_DISPLAY_LKUP\n"
			+ "         where DISPLAY_TYPE = 'ITEM_SELECT'\n"
			+ "           and LOCALE = 'en') WEB_ENG,\n"
			+ "       (select ITEM_ID, HTML WEB_CHI_HTML\n"
			+ "          from W_ITEM_DISPLAY_LKUP\n"
			+ "         where DISPLAY_TYPE = 'ITEM_SELECT'\n"
			+ "           and LOCALE = 'zh_HK') WEB_CHI,\n"
			+ "       (select ITEM_ID, HTML SS_ENG_HTML\n"
			+ "          from W_ITEM_DISPLAY_LKUP\n"
			+ "         where LOCALE = 'en'\n"
			+ "           and DISPLAY_TYPE in ('SS_FORM_RP', 'SS_FORM_VAS')) SS_ENG,\n"
			+ "       (select ITEM_ID, HTML SS_CHI_HTML\n"
			+ "          from W_ITEM_DISPLAY_LKUP\n"
			+ "         where LOCALE = 'zh_HK'\n"
			+ "           and DISPLAY_TYPE in ('SS_FORM_RP', 'SS_FORM_VAS')) SS_CHI,\n"
			+ "       W_BASKET_ITEM_ASSGN BIA,\n"
			+ "       W_ITEM_PRICING IP\n"
			+ " where I.ITEM_ID = WEB_ENG.ITEM_ID(+)\n"
			+ "   and I.ITEM_ID = WEB_CHI.ITEM_ID(+)\n"
			+ "   and I.ITEM_ID = SS_ENG.ITEM_ID(+)\n"
			+ "   and I.ITEM_ID = SS_CHI.ITEM_ID(+)\n"
			+ "   and BIA.ITEM_ID = I.ITEM_ID\n"
			+ "   and BIA.ITEM_ID = IP.ID(+)\n"
			+ "   and BIA.BASKET_ID = " + basketId
			+ " order by BIA.DISPLAY_SEQ";

	String sqlPreRequisiteOr =

	"select I.ITEM_ID,\n"
			+ "       I.EFF_START_DATE,\n"
			+ "       I.EFF_END_DATE,\n"
			+ "       WEB_ENG.WEB_ENG_HTML,\n"
			+ "       WEB_ENG.WEB_ENG_HTML,\n"
			+ "       WEB_CHI.WEB_CHI_HTML,\n"
			+ "       SS_ENG.SS_ENG_HTML,\n"
			+ "       SS_CHI.SS_CHI_HTML ,\n"
			+ "            IP.ONETIME_AMT,\n"
			+ "           IP.RECURRENT_AMT\n"
			+ "  from (select IPRO.ITEM_ID, IPRO.EFF_START_DATE, IPRO.EFF_END_DATE\n"
			+ "          from W_BASKET_ITEM_ASSGN BIA, W_ITEM_PRE_REQUISITE_OR IPRO\n"
			+ "         where BIA.ITEM_ID = IPRO.REQUIRED_ITEM_ID\n"
			+ "           and BIA.BASKET_ID = "
			+ basketId
			+ "           ) I,\n"
			+ "       (select ITEM_ID, HTML WEB_ENG_HTML\n"
			+ "          from W_ITEM_DISPLAY_LKUP\n"
			+ "         where DISPLAY_TYPE = 'ITEM_SELECT'\n"
			+ "           and LOCALE = 'en') WEB_ENG,\n"
			+ "       (select ITEM_ID, HTML WEB_CHI_HTML\n"
			+ "          from W_ITEM_DISPLAY_LKUP\n"
			+ "         where DISPLAY_TYPE = 'ITEM_SELECT'\n"
			+ "           and LOCALE = 'zh_HK') WEB_CHI,\n"
			+ "       (select ITEM_ID, HTML SS_ENG_HTML\n"
			+ "          from W_ITEM_DISPLAY_LKUP\n"
			+ "         where LOCALE = 'en'\n"
			+ "           and DISPLAY_TYPE in ('SS_FORM_RP', 'SS_FORM_VAS')) SS_ENG,\n"
			+ "       (select ITEM_ID, HTML SS_CHI_HTML\n"
			+ "          from W_ITEM_DISPLAY_LKUP\n"
			+ "         where LOCALE = 'zh_HK'\n"
			+ "           and DISPLAY_TYPE in ('SS_FORM_RP', 'SS_FORM_VAS')) SS_CHI ,\n"
			+ "               W_ITEM_PRICING IP\n"
			+ " where I.ITEM_ID = WEB_ENG.ITEM_ID(+)\n"
			+ "   and I.ITEM_ID = WEB_CHI.ITEM_ID(+)\n"
			+ "   and I.ITEM_ID = SS_ENG.ITEM_ID(+)\n"
			+ "   and I.ITEM_ID = SS_CHI.ITEM_ID(+)\n"
			+ "       and i.item_id=ip.id(+)";

	String sqlBaksetPreRequisiteItemRebateinfo = "  select BIA.ITEM_ID               PRE_REQUISITE_ITEM_ID,\n"
			+ "         IDRRV.ID                  ITEM_ID,\n"
			+ "         IDRRV.REBATE_SCHEDULE,\n"
			+ "         IDRRV.REBATE_SCHEDULE_CHI,\n"
			+ "         IDRRV.REBATE_AMT\n"
			+ "    from W_ITEM_DTL_RP_RB_VAS    IDRRV,\n"
			+ "         W_BASKET_ITEM_ASSGN     BIA,\n"
			+ "         W_ITEM_PRE_REQUISITE_OR IPRO\n"
			+ "   where BIA.ITEM_ID = IPRO.REQUIRED_ITEM_ID\n"
			+ "     and IDRRV.ID = IPRO.ITEM_ID\n"
			+ "     and IDRRV.REBATE_AMT <> 0\n"
			+ "     and BIA.BASKET_ID = " + basketId;

	String sqlBasketOffer = "select IOA.ITEM_ID,\n"
			
			+ "       IOA.OFFER_TYPE ||'/' ||IOA.OFFER_ID OFFER_TYPE_ID,\n"
		//	+ "       IOA.OFFER_ID || ,\n"
			+ "       IOA.OFFER_CD,\n"
			+ "       IOA.OFFER_desc,\n"
			+ "       IOPA.PRODUCT_TYPE ||'/' || IOPA.PRODUCT_ID PRODUCT_TYPE_ID,\n"
			//+ "       IOPA.PRODUCT_ID  ,\n"
			+ "       IOPA.PROD_CD,\n"
			+ "       IOPA.PROD_DESC,\n"
			+
			//	"       IOA.ITEM_OFFER_SEQ,\n" + 
			//	"       IOA.OFFER_SUB_ID,\n" + 
			//	"       IOA.OFFER_DESC,\n" + 
			//	"       IOA.SELECT_QTY OFFER_QTY,\n" + 
			//	"       IOPA.SELECT_QTY                 PRODUCT_QTY,\n" + 
			//	"       IPPA.COMPT_ID,\n" + 
			"       IPPA.POS_ITEM_CD,\n"
			//+ "       FEATURE_DISPLAY.FEATURE_DISPLAY,\n"
			
			//	"       PCM_PRODUCT.PCM_PRODUCT,\n" + 
			//	"       PCM_OFFER.PCM_OFFER\n" + 
			+ "		DBIA.EFF_START_DATE,\n"
			+ "		DBIA.EFF_END_DATE,\n"
			+ "		DBIA.DISPLAY_SEQ\n"
			+ "  from W_ITEM_OFFER_ASSGN IOA,\n"
			+ "       W_ITEM_OFFER_PRODUCT_ASSGN IOPA,\n"
			+ "       W_ITEM_PRODUCT_POS_ASSGN IPPA,\n"
			//+ "       (select PRODUCT_ID, HTML FEATURE_DISPLAY\n"
			//+ "          from W_PRODUCT_DISPLAY_LKUP PDL\n"
			//+ "         where PDL.DISPLAY_TYPE = 'FEATURE_DISPLAY') FEATURE_DISPLAY,\n"
			+ "       (select PRODUCT_ID, HTML PCM_PRODUCT\n"
			+ "          from W_PRODUCT_DISPLAY_LKUP PDL\n"
			+ "         where PDL.DISPLAY_TYPE = 'PCM_PRODUCT') PCM_PRODUCT,\n"
			+ "       (select PRODUCT_ID, HTML PCM_OFFER\n"
			+ "          from W_PRODUCT_DISPLAY_LKUP PDL\n"
			+ "         where PDL.DISPLAY_TYPE = 'PCM_OFFER') PCM_OFFER,\n"
			+ "       W_BASKET_ITEM_ASSGN DBIA\n"
			+ " where IOA.ITEM_ID = IOPA.ITEM_ID\n"
			//+ "   and IOPA.PRODUCT_ID = FEATURE_DISPLAY.PRODUCT_ID(+) --FEATURE_DISPLAY out join\n"
			+ "   and IOPA.PRODUCT_ID = PCM_PRODUCT.PRODUCT_ID(+) --PCM_PRODUCT out join\n"
			+ "   and IOA.OFFER_ID = PCM_OFFER.PRODUCT_ID(+) --PCM_OFFERout join\n"
			+ "   and IOA.ITEM_OFFER_SEQ = IOPA.ITEM_OFFER_SEQ\n"
			+ "   and IPPA.ITEM_ID(+) = IOPA.ITEM_ID\n"
			+ "   and IPPA.ITEM_OFFER_SEQ(+) = IOPA.ITEM_OFFER_SEQ\n"
			+ "   and IPPA.ITEM_PRODUCT_SEQ(+) = IOPA.ITEM_PRODUCT_SEQ\n"
			+ "   and IOA.ITEM_ID = DBIA.ITEM_ID\n"
			+ "   and DBIA.BASKET_ID = "
			+ basketId
			+ " order by DBIA.DISPLAY_SEQ, IOA.ITEM_ID,IOPA.PRODUCT_TYPE, IOA.OFFER_ID, IOPA.PRODUCT_ID ";

	String sqlBaksetRebateinfo = "select BIA.ITEM_ID,\n"
			+ "       IDRRV.REBATE_SCHEDULE,\n"
			+ "       IDRRV.REBATE_SCHEDULE_CHI,\n"
			+ "       IDRRV.REBATE_AMT\n"
			+ "  from W_ITEM_DTL_RP_RB_VAS IDRRV, W_BASKET_ITEM_ASSGN BIA\n"
			+ " where IDRRV.ID = BIA.ITEM_ID\n"
			+ "   and IDRRV.REBATE_AMT <> 0\n"
			+ "   and BIA.BASKET_ID = " + basketId;

	String sqlDeviceBasketBaseBasketRelation = "select B.ID DEVICD_BASKET_ID, B.TYPE, B.DESCRIPTION, CBIA.CHANNEL_ID, CBIA.CUSTOMER_TIER\n"
			+ "  from W_BASKET B, W_CHANNEL_BASKET_ASSGN CBIA\n"
			+ " where B.ID = CBIA.BASKET_ID\n"
			+ "   and B.BASE_BASKET_ID =" + basketId;

	//Gary's Part
	//SIM item count check, basket must have SIM(s) item. If not error. 
	String sqlSIMItemCountCheck = "select (DECODE(count(1), 0, 'NO SIM item, please check.', count(1) || ' SIM item assign to this basket'))SIM_item_assign \n"
			+ "  from W_BASKET_ITEM_ASSGN BIA, W_ITEM I\n"
			+ " where BIA.ITEM_ID = I.ID\n"
			+ "   and I.TYPE = 'SIM'\n"
			+ "   and BIA.BASKET_ID = " + basketId;

	//Rebate Item Error
	String sqlRebateItemError = "SELECT idrrv.ID, idrrv.rebate_schedule, idrrv.rebate_schedule_chi, "
			+ "       idrrv.rebate_amt "
			+ "  FROM w_item_dtl_rp_rb_vas idrrv "
			+ " WHERE (idrrv.rebate_schedule_chi IS NULL OR idrrv.rebate_schedule IS NULL) "
			+ "   AND idrrv.rebate_amt IS NOT NULL "
			+ "   AND idrrv.rebate_amt <> 0 "
			+ "   AND idrrv.ID > 0 "
			+ "   AND idrrv.ID IN (SELECT bia.item_id "
			+ "                      FROM w_basket_item_assgn bia "
			+ "                     WHERE bia.basket_id ="
			+ basketId
			+ ")";

	//TradeDescCheck
	String sqlTradeDescCheck = "SELECT (DECODE (COUNT (1), "
			+ "               0, 'Trade desc not found', "
			+ "                  'Trade desc Assign ==>' "
			+ "               || MAX (htd.brand) "
			+ "               || ' ' "
			+ "               || MAX (htd.model) "
			+ "              ))Trade_Desc_Assign "
			+ "  FROM w_pos_trade_desc_assign ptda, "
			+ "       w_hs_trade_desc htd, "
			+ "       w_item_product_pos_assgn ippa "
			+ " WHERE ptda.trade_desc_id = htd.ID "
			+ "   AND ptda.pos_item_cd = ippa.pos_item_cd "
			+ "   AND ippa.item_id IN (SELECT bia.item_id "
			+ "                          FROM w_basket_item_assgn bia "
			+ "                         WHERE bia.basket_id ="
			+ basketId + ")";

	//Same item check
	String sqlSameItemCheck = "SELECT   bia.basket_id, bia.item_id, (COUNT (bia.item_id))No_of_Same_Item_Duplicate "
			+ "    FROM w_basket_item_assgn bia "
			+ "   WHERE bia.basket_id = "
			+ basketId
			+ "GROUP BY bia.basket_id, bia.item_id "
			+ "  HAVING COUNT (bia.item_id) > 1 ";

	//same product check
	String sqlSameProductCheck = "SELECT   bia.basket_id, iopa.product_id, (COUNT (iopa.product_id))No_of_Same_Product_Duplicate "
			+ "    FROM w_item_offer_product_assgn iopa, w_basket_item_assgn bia "
			+ "   WHERE iopa.item_id = bia.item_id AND bia.basket_id = "
			+ basketId
			+ "GROUP BY bia.basket_id, iopa.product_id "
			+ "  HAVING COUNT (iopa.product_id) > 1";

	//Check id exist in w_display_lkup 
	//brand color MODEL display check
	String sqlIdExistinDL = "SELECT * "
			+ "  FROM w_item_dtl_hs idh, w_item i, w_basket_item_assgn bia, "
			+ "       w_display_lkup dl " + " WHERE idh.ID = i.ID "
			+ "   AND idh.ID = bia.item_id " + "   AND i.TYPE = 'HS' "
			+ "   AND dl.TYPE = 'MODEL' "
			+ "   AND dl.ID = idh.model_id "
			+ "   AND bia.basket_id = " + basketId;

	//hs display check
	//Check have data in w_hs_display_lkup
	String sqlDataExistinHDL = "SELECT (COUNT (*)) Data_In_w_hs_display_lkup "
			+ "  FROM w_item_dtl_hs idh, "
			+ "       w_item i, "
			+ "       w_basket_item_assgn bia, "
			+ "       w_hs_display_lkup hdl "
			+ " WHERE idh.ID = i.ID "
			+ "   AND idh.ID = bia.item_id "
			+ "   AND i.TYPE = 'HS' "
			+ "   AND idh.model_id = hdl.model_id "
			+ "   AND bia.basket_id = " + basketId;

	//Check have data in W_BASKET_ATTRIBUTE_MV
	/* String sqlDataExistinBAM = "SELECT * "
			+ "  FROM w_basket_attribute_mv " + " WHERE basket_id = "
			+ basketId;

	//if not found need to refresh view
	String sqlRefreshView1 = "BEGIN  null;"
			+ "  -- DBMS_SNAPSHOT.REFRESH ('\"OPS$CNM\".\"W_BASKET_ATTRIBUTE_MV\"', 'C'); "
			+ "END;";
	String sqlRefreshView2 = "BEGIN null;"
			+ "   --DBMS_SNAPSHOT.REFRESH ('\"OPS$CNM\".\"W_BASKET_HS_HSRB_MV\"', 'C'); "
			+ "END;"; */

	/*
	sqlSIMItemCountCheck
	sqlRebateItemError
	sqlTradeDescCheck
	sqlSameItemCheck
	sqlSameProductCheck
	sqlIdExistinDL
	sqlDataExistinHDL
	sqlDataExistinBAM
	sqlRefreshView1
	sqlRefreshView2
	 */

	//how to find basket from front end
	String sqlFindBasket = "SELECT cba.channel_id, "
			+ "       (SELECT dl.description "
			+ "          FROM w_display_lkup dl "
			+ "         WHERE dl.locale = 'en' "
			+ "           AND dl.TYPE = 'CUSTOMER_TIER' "
			+ "           AND dl.ID = cba.customer_tier) customer_tier_desc, "
			+ "       (SELECT dl.description "
			+ "          FROM w_display_lkup dl "
			+ "         WHERE dl.locale = 'en' "
			+ "           AND dl.TYPE = 'BASKET_TYPE' "
			+ "           AND dl.ID = b.TYPE) basket_type_desc, "
			+ "       (SELECT dl.description "
			+ "          FROM w_display_lkup dl "
			+ "         WHERE dl.locale = 'en' "
			+ "           AND dl.TYPE = 'RP_TYPE' "
			+ "           AND dl.ID = rp_info.rp_type) rp_type_desc "
			+ "  FROM w_channel_basket_assgn cba, "
			+ "       w_basket b, "
			+ "       w_basket_item_assgn bia, "
			+ "       (SELECT i.ID item_id, idrv.ID, idrv.rp_type, idrv.contract_period "
			+ "          FROM w_item_dtl_rp_rb_vas idrv, w_item i "
			+ "         WHERE i.ID = idrv.ID AND i.TYPE = 'RP') rp_info "
			+ " WHERE b.ID = cba.basket_id "
			+ "   AND b.ID = bia.basket_id "
			+ "   AND bia.item_id = rp_info.item_id "
			+ "   AND cba.basket_id =  " + basketId;

	//hs amount
	String sqlHSAmount = "SELECT SUM (ip.onetime_amt) hs_amt "
			+ "  FROM w_basket_item_assgn bia, w_item_pricing ip, w_item i "
			+ " WHERE bia.item_id = ip.ID "
			+ "   AND bia.item_id = i.ID "
			+ "   AND TRUNC (SYSDATE) BETWEEN ip.eff_start_date "
			+ "                           AND (NVL (ip.eff_end_date, TRUNC (SYSDATE))) "
			+ "   AND TRUNC (SYSDATE) BETWEEN bia.eff_start_date "
			+ "                           AND (NVL (bia.eff_end_date, TRUNC (SYSDATE))) "
			+ "   AND i.TYPE = 'HS' " + "   AND bia.basket_id = "
			+ basketId;

	//total rebate amt
	String sqlRebateAmt = "SELECT SUM (idrrv.rebate_amt) rebate_amt "
			+ "  FROM w_basket_item_assgn bia, w_item_dtl_rp_rb_vas idrrv, w_item i "
			+ " WHERE bia.item_id = idrrv.ID "
			+ "   AND bia.item_id = i.ID "
			+ "   AND TRUNC (SYSDATE) BETWEEN bia.eff_start_date "
			+ "                           AND (NVL (bia.eff_end_date, TRUNC (SYSDATE))) "
			+ "   AND bia.basket_id = " + basketId;
	
	//Vas Group Info (added by F.Chan)
	String sqlVasGroupInfo= "select B.ID, B.DESCRIPTION, CBA.VAS_ITEM_GRP_ID, CBA.CHANNEL_ID, VIG.DESCRIPTION VAS_GRP_DESC"
			
			+"  from W_CHANNEL_BASKET_ASSGN CBA, W_BASKET B, W_VAS_ITEM_GRP VIG"
			
			+" where B.ID = CBA.BASKET_ID"
			
			+" and CBA.VAS_ITEM_GRP_ID = VIG.ID"
			
			+" and B.ID=" + basketId;
	
	//Handset Basket Batch Info (added by F.Chan)
	String sqlHandsetBasketBatchInfo= "select B.ID, B.DESCRIPTION, HU.BATCH_ID"

		    +" from W_HANDSET_UPLOAD HU, W_BASKET B"
		
		    +" where HU.BASKET_ID = B.ID"
		
		    +" and B.BASE_BASKET_ID is not null"
		
		    +"  and B.ID =" + basketId;
	

	//HS original price (added by F.Chan) Jan 3 2013
	String sqlSSFormDescAndOriginalPrice = "select idh.brand_id, (select DL.DESCRIPTION from W_DISPLAY_LKUP DL where DL.TYPE = 'BRAND' and DL.ID = IDH.BRAND_ID) BRAND_DESC,"
			+ " idh.model_id, (select DL.DESCRIPTION from W_DISPLAY_LKUP DL where DL.TYPE = 'MODEL' and DL.ID = IDH.MODEL_ID) MODEL_DESC,"
			+ " idh.color_id, (select DL.DESCRIPTION from W_DISPLAY_LKUP DL where DL.TYPE = 'COLOR' and DL.ID = IDH.COLOR_ID) COLOR_DESC,"
			+ " idh.ss_form_desc, hdl.original_price"
			+ "  FROM w_item_dtl_hs idh,"
			+ "       w_item i,"
			+ "       w_basket_item_assgn bia,"
			+ "       w_hs_display_lkup hdl"
			+ " WHERE idh.ID = i.ID"
			+ "   AND idh.ID = bia.item_id"
			+ "   AND i.TYPE = 'HS' "
			+ "   AND idh.model_id = hdl.model_id"
			+ "   AND bia.basket_id = " + basketId;	
	

	
	

	/*
	sqlFindBasket
	sqlHSAmount
	sqlRebateAmt
	 */

	//out.append("<BR> " + url + "<hr> ");
	out.append("<BR> Basket Preview <BR><hr> ");

	out.append("<BR> Health Check <BR> ");

	String[] stringArray = new String[] {sqlBatchInfo, sqlSIMItemCountCheck,
			sqlRebateItemError, sqlTradeDescCheck, sqlIdExistinDL,
			sqlDataExistinHDL,  sqlSameItemCheck,
			sqlSameProductCheck };

	try {

		Statement stmt = con2.createStatement();
		for (int sqlrun = 0; sqlrun < stringArray.length; sqlrun++) {
			if (stmt.execute(stringArray[sqlrun])) {
				// There's a ResultSet to be had
				ResultSet rs = stmt.getResultSet();
				//class='display'
				
				
				if (stringArray[sqlrun].equals(sqlBatchInfo)) {
					out.append("sqlBatchInfo");
				}
				if (stringArray[sqlrun].equals(sqlSameItemCheck)) {
					out.append("Duplicate Error");
				}
				if (stringArray[sqlrun].equals(sqlSIMItemCountCheck)) {
					out.append("sqlSIMItemCountCheck");
				}
				if (stringArray[sqlrun].equals(sqlRebateItemError)) {
					out.append("sqlRebateItemError");
				}
				if (stringArray[sqlrun].equals(sqlTradeDescCheck)) {
					out.append("sqlTradeDescCheck");
				}
				if (stringArray[sqlrun].equals(sqlIdExistinDL)) {
					out.append("sqlIdExistinDL");
				}
				if (stringArray[sqlrun].equals(sqlDataExistinHDL)) {
					out.append("sqlDataExistinHDL");
				}
				
				if (stringArray[sqlrun].equals(sqlSameItemCheck)) {
					out.append("sqlSameItemCheck");
				}
				if (stringArray[sqlrun].equals(sqlSameProductCheck)) {
					out.append("sqlSameProductCheck");
				}

				

				ResultSetMetaData rsmd = rs.getMetaData();
				
				
				int numcols = rsmd.getColumnCount();
				int countRow = 0;
				while (rs.next()) {
					if (countRow==0){//move title block here, no data will not show the title
						
						out.append("<TABLE  id='table1' border='1'>\n");
						// Title the table with the result set's column labels header
						out.append("<thead><TR>");
						for (int i = 1; i <= numcols; i++)
							out.append("<TH>" + rsmd.getColumnLabel(i)
									+ "</TH>");
						out.append("</TR></thead>\n");
						out.append("<tbody>\n");
					}
					countRow++;
					out.append("<TR bgcolor=\"#FF0000\">"); // start a new row

					for (int i = 1; i <= numcols; i++) {
						out.append("<TD>"); // start a new data element
						Object obj = null;
						try {
							obj = rs.getObject(i);
						} catch (Exception e) {
							obj = null;
						}
						if (obj != null) {
							out.append(obj.toString());
							out.append("</TD>"); // start a new data element
						} else {
							out.append("&nbsp;");
							out.append("</TD>"); // start a new data element
						}
					}

					out.append("</TR>\n");

				}
				out.append("</tbody>\n");

				// End the table
				out.append("</TABLE>\n <BR>");
				//refresh the view
				/* if (countRow == 0
						&& stringArray[sqlrun]
								.equals(sqlDataExistinBAM)) {
					//out.println("countCheck = "+stringArray);
					stmt.execute(sqlRefreshView1);

				} */
			} else {
				// There's a count to be had
				out.append("<B>Records Affected:</B> 2"
						+ stmt.getUpdateCount());
			}

		}
	} catch (SQLException e) {
		out.append("</TABLE><H1>ERROR:</H1> 1" + e.getMessage());

	}

	
	
	//F. Chan's part
	out.append("<hr> <BR>S&S Form Description & Handset Original Price<BR>");
			

			try {

				Statement stmt = con2.createStatement();

				if (stmt.execute(sqlSSFormDescAndOriginalPrice)) {
					// There's a ResultSet to be had
					ResultSet rs = stmt.getResultSet();
					//class='display'
					out.append("<TABLE  id='table1' border='1'>\n");

					ResultSetMetaData rsmd = rs.getMetaData();

					int numcols = rsmd.getColumnCount();

					// Title the table with the result set's column labels
					//header
					out.append("<thead><TR>");
					for (int i = 1; i <= numcols; i++)
						out.append("<TH>" + rsmd.getColumnLabel(i) + "</TH>");
					out.append("</TR></thead>\n");

					//body
					out.append("<tbody>\n");
					while (rs.next()) {
						out.append("<TR>"); // start a new row
						for (int i = 1; i <= numcols; i++) {
							out.append("<TD>"); // start a new data element
							Object obj = null;
							try {
								obj = rs.getObject(i);
							} catch (Exception e) {
								obj = null;
							}
							if (obj != null) {
								out.append(obj.toString());
								out.append("</TD>"); // start a new data element
							} else {
								out.append("&nbsp;");
								out.append("</TD>"); // start a new data element
							}
						}

						out.append("</TR>\n");
					}
					out.append("</tbody>\n");

					// End the table
					out.append("</TABLE>\n");
				} else {
					// There's a count to be had
					out.append("<B>Records Affected:</B> 2"
							+ stmt.getUpdateCount());
				}
			} catch (SQLException e) {
				out.append("</TABLE><H1>ERROR:</H1> 1" + e.getMessage());
			}
		
	
	out.append("<hr> <BR> Find Basket <BR>");
	//how to find basket
	try {

		Statement stmt = con2.createStatement();

		if (stmt.execute(sqlFindBasket)) {
			// There's a ResultSet to be had
			ResultSet rs = stmt.getResultSet();
			//class='display'
			out.append("<TABLE  id='table1' border='1'>\n");

			ResultSetMetaData rsmd = rs.getMetaData();

			int numcols = rsmd.getColumnCount();

			// Title the table with the result set's column labels
			//header
			out.append("<thead><TR>");
			for (int i = 1; i <= numcols; i++)
				out.append("<TH>" + rsmd.getColumnLabel(i) + "</TH>");
			out.append("</TR></thead>\n");

			//body
			out.append("<tbody>\n");
			while (rs.next()) {
				out.append("<TR>"); // start a new row

				for (int i = 1; i <= numcols; i++) {
					out.append("<TD>"); // start a new data element
					Object obj = null;
					try {
						obj = rs.getObject(i);
					} catch (Exception e) {
						obj = null;
					}
					if (obj != null) {
						out.append(obj.toString());
						out.append("</TD>"); // start a new data element
					} else {
						out.append("&nbsp;");
						out.append("</TD>"); // start a new data element
					}
				}

				out.append("</TR>\n");
			}
			out.append("</tbody>\n");

			/*  //foot
			out.append("<tfoot><TR>");
			for (int i = 1; i <= numcols; i++)
				out.append("<TH>" + rsmd.getColumnLabel(i) + "</TH>");
			out.append("</TR></tfoot>\n");  */

			// End the table
			out.append("</TABLE>\n");
		} else {
			// There's a count to be had
			out.append("<B>Records Affected:</B> 2"
					+ stmt.getUpdateCount());
		}
	} catch (SQLException e) {
		out.append("</TABLE><H1>ERROR:</H1> 1" + e.getMessage());
	}

	out.append("<hr> ");

	out.append("<BR> Basket Basic information <BR>");

	try {

		Statement stmt = con2.createStatement();

		if (stmt.execute(sqlBasketBasicInfo)) {
			// There's a ResultSet to be had
			ResultSet rs = stmt.getResultSet();
			//class='display'
			out.append("<TABLE  id='table1' border='1'>\n");

			ResultSetMetaData rsmd = rs.getMetaData();

			int numcols = rsmd.getColumnCount();

			// Title the table with the result set's column labels
			//header
			out.append("<thead><TR>");
			for (int i = 1; i <= numcols; i++)
				out.append("<TH>" + rsmd.getColumnLabel(i) + "</TH>");
			out.append("</TR></thead>\n");

			//body
			out.append("<tbody>\n");
			while (rs.next()) {
				out.append("<TR>"); // start a new row

				for (int i = 1; i <= numcols; i++) {
					out.append("<TD>"); // start a new data element
					Object obj = null;
					try {
						obj = rs.getObject(i);
					} catch (Exception e) {
						obj = null;
					}
					if (obj != null) {
						out.append(obj.toString());
						out.append("</TD>"); // start a new data element
					} else {
						out.append("&nbsp;");
						out.append("</TD>"); // start a new data element
					}
				}

				out.append("</TR>\n");
			}
			out.append("</tbody>\n");

			/*  //foot
			out.append("<tfoot><TR>");
			for (int i = 1; i <= numcols; i++)
				out.append("<TH>" + rsmd.getColumnLabel(i) + "</TH>");
			out.append("</TR></tfoot>\n");  */

			// End the table
			out.append("</TABLE>\n");
		} else {
			// There's a count to be had
			out.append("<B>Records Affected:</B> 2"
					+ stmt.getUpdateCount());
		}
	} catch (SQLException e) {
		out.append("</TABLE><H1>ERROR:</H1> 1" + e.getMessage());
	}

	out.append("<hr> <BR> Basket path <BR>");
	//basket display 

	try {

		Statement stmt = con2.createStatement();

		if (stmt.execute(sqlDeviceBasketPath)) {
			// There's a ResultSet to be had
			ResultSet rs = stmt.getResultSet();
			//class='display'
			out.append("<TABLE  id='table1' border='1'>\n");

			ResultSetMetaData rsmd = rs.getMetaData();

			int numcols = rsmd.getColumnCount();

			// Title the table with the result set's column labels
			//header
			out.append("<thead><TR>");
			for (int i = 1; i <= numcols; i++)
				out.append("<TH>" + rsmd.getColumnLabel(i) + "</TH>");
			out.append("</TR></thead>\n");

			//body
			out.append("<tbody>\n");
			while (rs.next()) {
				out.append("<TR>"); // start a new row
				for (int i = 1; i <= numcols; i++) {
					out.append("<TD>"); // start a new data element
					Object obj = null;
					try {
						obj = rs.getObject(i);
					} catch (Exception e) {
						obj = null;
					}
					if (obj != null) {
						out.append(obj.toString());
						out.append("</TD>"); // start a new data element
					} else {
						out.append("&nbsp;");
						out.append("</TD>"); // start a new data element
					}
				}

				out.append("</TR>\n");
			}
			out.append("</tbody>\n");

			// End the table
			out.append("</TABLE>\n");
		} else {
			// There's a count to be had
			out.append("<B>Records Affected:</B> 2"
					+ stmt.getUpdateCount());
		}
	} catch (SQLException e) {
		out.append("</TABLE><H1>ERROR:</H1> 1" + e.getMessage());
	}

	out.append("<hr> <BR> Basket Display Title Information <BR>");
	//basket display 

	try {

		Statement stmt = con2.createStatement();

		if (stmt.execute(sqlBasketTitleDisplay)) {
			// There's a ResultSet to be had
			ResultSet rs = stmt.getResultSet();
			//class='display'
			out.append("<TABLE  id='table1' border='1'>\n");

			ResultSetMetaData rsmd = rs.getMetaData();

			int numcols = rsmd.getColumnCount();

			// Title the table with the result set's column labels
			//header
			out.append("<thead><TR>");
			for (int i = 1; i <= numcols; i++)
				out.append("<TH>" + rsmd.getColumnLabel(i) + "</TH>");
			out.append("</TR></thead>\n");

			//body
			out.append("<tbody>\n");
			while (rs.next()) {
				out.append("<TR>"); // start a new row
				for (int i = 1; i <= numcols; i++) {
					out.append("<TD>"); // start a new data element
					Object obj = null;
					try {
						obj = rs.getObject(i);
					} catch (Exception e) {
						obj = null;
					}
					if (obj != null) {
						out.append(obj.toString());
						out.append("</TD>"); // start a new data element
					} else {
						out.append("&nbsp;");
						out.append("</TD>"); // start a new data element
					}
				}

				out.append("</TR>\n");
			}
			out.append("</tbody>\n");

			// End the table
			out.append("</TABLE>\n");
		} else {
			// There's a count to be had
			out.append("<B>Records Affected:</B> 2"
					+ stmt.getUpdateCount());
		}
	} catch (SQLException e) {
		out.append("</TABLE><H1>ERROR:</H1> 1" + e.getMessage());
	}

	out.append("<hr> <BR> Basket BasketBlock Display Information <BR>");
	//basket display 

	try {

		Statement stmt = con2.createStatement();

		if (stmt.execute(sqlBasketBlock)) {
			// There's a ResultSet to be had
			ResultSet rs = stmt.getResultSet();
			//class='display'
			out.append("<TABLE  id='table1' border='1'>\n");

			ResultSetMetaData rsmd = rs.getMetaData();

			int numcols = rsmd.getColumnCount();

			// Title the table with the result set's column labels
			//header
			out.append("<thead><TR>");
			for (int i = 1; i <= numcols; i++)
				out.append("<TH>" + rsmd.getColumnLabel(i) + "</TH>");
			out.append("</TR></thead>\n");

			//body
			out.append("<tbody>\n");
			while (rs.next()) {
				out.append("<TR>"); // start a new row
				for (int i = 1; i <= numcols; i++) {
					out.append("<TD>"); // start a new data element
					Object obj = null;
					try {
						obj = rs.getObject(i);
					} catch (Exception e) {
						obj = null;
					}
					if (obj != null) {
						out.append(obj.toString());
						out.append("</TD>"); // start a new data element
					} else {
						out.append("&nbsp;");
						out.append("</TD>"); // start a new data element
					}
				}

				out.append("</TR>\n");
			}
			out.append("</tbody>\n");

			// End the table
			out.append("</TABLE>\n");
		} else {
			// There's a count to be had
			out.append("<B>Records Affected:</B> 2"
					+ stmt.getUpdateCount());
		}
	} catch (SQLException e) {
		out.append("</TABLE><H1>ERROR:</H1> 1" + e.getMessage());
	}

	out.append("<hr> <BR> Basket Display Information <BR>");
	//basket display 

	try {

		Statement stmt = con2.createStatement();

		if (stmt.execute(sqlBasketDisplay)) {
			// There's a ResultSet to be had
			ResultSet rs = stmt.getResultSet();
			//class='display'
			out.append("<TABLE  id='table1' border='1'>\n");

			ResultSetMetaData rsmd = rs.getMetaData();

			int numcols = rsmd.getColumnCount();

			// Title the table with the result set's column labels
			//header
			out.append("<thead><TR>");
			for (int i = 1; i <= numcols; i++)
				out.append("<TH>" + rsmd.getColumnLabel(i) + "</TH>");
			out.append("</TR></thead>\n");

			//body
			out.append("<tbody>\n");
			while (rs.next()) {
				out.append("<TR>"); // start a new row
				for (int i = 1; i <= numcols; i++) {
					out.append("<TD>"); // start a new data element
					Object obj = null;
					try {
						obj = rs.getObject(i);
					} catch (Exception e) {
						obj = null;
					}
					if (obj != null) {
						out.append(obj.toString());
						out.append("</TD>"); // start a new data element
					} else {
						out.append("&nbsp;");
						out.append("</TD>"); // start a new data element
					}
				}

				out.append("</TR>\n");
			}
			out.append("</tbody>\n");

			// End the table
			out.append("</TABLE>\n");
		} else {
			// There's a count to be had
			out.append("<B>Records Affected:</B> 2"
					+ stmt.getUpdateCount());
		}
	} catch (SQLException e) {
		out.append("</TABLE><H1>ERROR:</H1> 1" + e.getMessage());
	}

	out.append("<hr> <BR> Basket Pre Requisite Or item <BR>");

	try {

		Statement stmt = con2.createStatement();

		if (stmt.execute(sqlPreRequisiteOr)) {
			// There's a ResultSet to be had
			ResultSet rs = stmt.getResultSet();
			//class='display'
			out.append("<TABLE  id='table1' border='1'>\n");

			ResultSetMetaData rsmd = rs.getMetaData();

			int numcols = rsmd.getColumnCount();

			// Title the table with the result set's column labels
			//header
			out.append("<thead><TR>");
			for (int i = 1; i <= numcols; i++)
				out.append("<TH>" + rsmd.getColumnLabel(i) + "</TH>");
			out.append("</TR></thead>\n");

			//body
			out.append("<tbody>\n");
			while (rs.next()) {
				out.append("<TR>"); // start a new row
				for (int i = 1; i <= numcols; i++) {
					out.append("<TD>"); // start a new data element
					Object obj = null;
					try {
						obj = rs.getObject(i);
					} catch (Exception e) {
						obj = null;
					}
					if (obj != null) {
						out.append(obj.toString());
						out.append("</TD>"); // start a new data element
					} else {
						out.append("&nbsp;");
						out.append("</TD>"); // start a new data element
					}
				}

				out.append("</TR>\n");
			}
			out.append("</tbody>\n");

			// End the table
			out.append("</TABLE>\n");
		} else {
			// There's a count to be had
			out.append("<B>Records Affected:</B> 2"
					+ stmt.getUpdateCount());
		}
	} catch (SQLException e) {
		out.append("</TABLE><H1>ERROR:</H1> 1" + e.getMessage());
	}

	out.append("<hr> <BR> Basket Pre Requisite Or Rebate item <BR>");

	try {

		Statement stmt = con2.createStatement();

		if (stmt.execute(sqlBaksetPreRequisiteItemRebateinfo)) {
			// There's a ResultSet to be had
			ResultSet rs = stmt.getResultSet();
			//class='display'
			out.append("<TABLE  id='table1' border='1'>\n");

			ResultSetMetaData rsmd = rs.getMetaData();

			int numcols = rsmd.getColumnCount();

			// Title the table with the result set's column labels
			//header
			out.append("<thead><TR>");
			for (int i = 1; i <= numcols; i++)
				out.append("<TH>" + rsmd.getColumnLabel(i) + "</TH>");
			out.append("</TR></thead>\n");

			//body
			out.append("<tbody>\n");
			while (rs.next()) {
				out.append("<TR>"); // start a new row
				for (int i = 1; i <= numcols; i++) {
					out.append("<TD>"); // start a new data element
					Object obj = null;
					try {
						obj = rs.getObject(i);
					} catch (Exception e) {
						obj = null;
					}
					if (obj != null) {
						out.append(obj.toString());
						out.append("</TD>"); // start a new data element
					} else {
						out.append("&nbsp;");
						out.append("</TD>"); // start a new data element
					}
				}

				out.append("</TR>\n");
			}
			out.append("</tbody>\n");

			// End the table
			out.append("</TABLE>\n");
		} else {
			// There's a count to be had
			out.append("<B>Records Affected:</B> 2"
					+ stmt.getUpdateCount());
		}
	} catch (SQLException e) {
		out.append("</TABLE><H1>ERROR:</H1> 1" + e.getMessage());
	}

	out.append("<hr> <BR> basket offer information <BR>");
	//offer check  

	try {

		Statement stmt = con2.createStatement();

		if (stmt.execute(sqlBasketOffer)) {
			// There's a ResultSet to be had
			ResultSet rs = stmt.getResultSet();
			//class='display'
			out.append("<TABLE  id='tableOffer' border='1'>\n");

			ResultSetMetaData rsmd = rs.getMetaData();

			int numcols = rsmd.getColumnCount();

			// Title the table with the result set's column labels
			//header
			out.append("<thead><TR>");
			//out.append("<TD>CHECK<TD>");
			for (int i = 1; i <= numcols; i++)
				out.append("<TH>" + rsmd.getColumnLabel(i) + "</TH>");
			out.append("</TR></thead>\n");

			//body
			out.append("<tbody>\n");
			while (rs.next()) {
				out.append("<TR>"); // start a new row

				//out.append("<TD> <INPUT TYPE=CHECKBOX NAME='check1'><TD>");
				for (int i = 1; i <= numcols; i++) {
					out.append("<TD>"); // start a new data element
					Object obj = null;
					try {
						obj = rs.getObject(i);
					} catch (Exception e) {
						obj = null;
					}
					if (obj != null) {
						out.append(obj.toString());
						out.append("</TD>"); // start a new data element
					} else {
						out.append("&nbsp;");
						out.append("</TD>"); // start a new data element
					}
				}

				out.append("</TR>\n");
			}
			out.append("</tbody>\n");
			//foot
			out.append("<tfoot><TR>");
			//out.append("<TD>CHECK<TD>");
			for (int i = 1; i <= numcols; i++)
				out.append("<TH>" + rsmd.getColumnLabel(i) + "</TH>");
			out.append("</TR></tfoot>\n");

			// End the table
			out.append("</TABLE>\n");
		} else {
			// There's a count to be had
			out.append("<B>Records Affected:</B> 2"
					+ stmt.getUpdateCount());
		}
	} catch (SQLException e) {
		out.append("</TABLE><H1>ERROR:</H1> 1" + e.getMessage());
	}

	//sqlBaksetRebateinfo

	out.append("<hr> <BR> basket Rebate information <BR>");
	//offer check  

	try {

		Statement stmt = con2.createStatement();

		if (stmt.execute(sqlBaksetRebateinfo)) {
			// There's a ResultSet to be had
			ResultSet rs = stmt.getResultSet();
			//class='display'
			out.append("<TABLE  id='table1' border='1'>\n");

			ResultSetMetaData rsmd = rs.getMetaData();

			int numcols = rsmd.getColumnCount();

			// Title the table with the result set's column labels
			//header
			out.append("<thead><TR>");
			for (int i = 1; i <= numcols; i++)
				out.append("<TH>" + rsmd.getColumnLabel(i) + "</TH>");
			out.append("</TR></thead>\n");

			//body
			out.append("<tbody>\n");
			while (rs.next()) {
				out.append("<TR>"); // start a new row
				for (int i = 1; i <= numcols; i++) {
					out.append("<TD>"); // start a new data element
					Object obj = null;
					try {
						obj = rs.getObject(i);
					} catch (Exception e) {
						obj = null;
					}
					if (obj != null) {
						out.append(obj.toString());
						out.append("</TD>"); // start a new data element
					} else {
						out.append("&nbsp;");
						out.append("</TD>"); // start a new data element
					}
				}

				out.append("</TR>\n");
			}
			out.append("</tbody>\n");

			// End the table
			out.append("</TABLE>\n");
		} else {
			// There's a count to be had
			out.append("<B>Records Affected:</B> 2"
					+ stmt.getUpdateCount());
		}
	} catch (SQLException e) {
		out.append("</TABLE><H1>ERROR:</H1> 1" + e.getMessage());
	}

	out.append(" Basket Rebate Amount <BR>");

	try {

		Statement stmt = con2.createStatement();

		if (stmt.execute(sqlRebateAmt)) {
			// There's a ResultSet to be had
			ResultSet rs = stmt.getResultSet();
			//class='display'
			out.append("<TABLE  id='table1' border='1'>\n");

			ResultSetMetaData rsmd = rs.getMetaData();

			int numcols = rsmd.getColumnCount();

			// Title the table with the result set's column labels
			//header
			out.append("<thead><TR>");
			for (int i = 1; i <= numcols; i++)
				out.append("<TH>" + rsmd.getColumnLabel(i) + "</TH>");
			out.append("</TR></thead>\n");

			//body
			out.append("<tbody>\n");
			while (rs.next()) {
				out.append("<TR>"); // start a new row
				for (int i = 1; i <= numcols; i++) {
					out.append("<TD>"); // start a new data element
					Object obj = null;
					try {
						obj = rs.getObject(i);
					} catch (Exception e) {
						obj = null;
					}
					if (obj != null) {
						out.append(obj.toString());
						out.append("</TD>"); // start a new data element
					} else {
						out.append("&nbsp;");
						out.append("</TD>"); // start a new data element
					}
				}

				out.append("</TR>\n");
			}
			out.append("</tbody>\n");

			// End the table
			out.append("</TABLE>\n");
		} else {
			// There's a count to be had
			out.append("<B>Records Affected:</B> 2"
					+ stmt.getUpdateCount());
		}
	} catch (SQLException e) {
		out.append("</TABLE><H1>ERROR:</H1> 1" + e.getMessage());
	}

	out.append("<hr> <BR> base basket relation information <BR>");
	//offer check  

	try {

		Statement stmt = con2.createStatement();

		if (stmt.execute(sqlDeviceBasketBaseBasketRelation)) {
			// There's a ResultSet to be had
			ResultSet rs = stmt.getResultSet();
			//class='display'
			out.append("<TABLE  id='table1' border='1'>\n");

			ResultSetMetaData rsmd = rs.getMetaData();

			int numcols = rsmd.getColumnCount();

			// Title the table with the result set's column labels
			//header
			out.append("<thead><TR>");
			for (int i = 1; i <= numcols; i++)
				out.append("<TH>" + rsmd.getColumnLabel(i) + "</TH>");
			out.append("</TR></thead>\n");

			//body
			out.append("<tbody>\n");
			while (rs.next()) {
				out.append("<TR>"); // start a new row
				for (int i = 1; i <= numcols; i++) {
					out.append("<TD>"); // start a new data element
					Object obj = null;
					try {
						obj = rs.getObject(i);
					} catch (Exception e) {
						obj = null;
					}
					if (obj != null) {
						out.append(obj.toString());
						out.append("</TD>"); // start a new data element
					} else {
						out.append("&nbsp;");
						out.append("</TD>"); // start a new data element
					}
				}

				out.append("</TR>\n");
			}
			out.append("</tbody>\n");

			// End the table
			out.append("</TABLE>\n");
		} else {
			// There's a count to be had
			out.append("<B>Records Affected:</B> 2"
					+ stmt.getUpdateCount());
		}
	} catch (SQLException e) {
		out.append("</TABLE><H1>ERROR:</H1> 1" + e.getMessage());
	}

	//F. Chan's part
	out.append("<hr> <BR>VAS Group Info<BR>");
	

	try {

		Statement stmt = con2.createStatement();

		if (stmt.execute(sqlVasGroupInfo)) {
			// There's a ResultSet to be had
			ResultSet rs = stmt.getResultSet();
			//class='display'
			out.append("<TABLE  id='table1' border='1'>\n");

			ResultSetMetaData rsmd = rs.getMetaData();

			int numcols = rsmd.getColumnCount();

			// Title the table with the result set's column labels
			//header
			out.append("<thead><TR>");
			for (int i = 1; i <= numcols; i++)
				out.append("<TH>" + rsmd.getColumnLabel(i) + "</TH>");
			out.append("</TR></thead>\n");

			//body
			out.append("<tbody>\n");
			while (rs.next()) {
				out.append("<TR>"); // start a new row
				for (int i = 1; i <= numcols; i++) {
					out.append("<TD>"); // start a new data element
					Object obj = null;
					try {
						obj = rs.getObject(i);
					} catch (Exception e) {
						obj = null;
					}
					if (obj != null) {
						out.append(obj.toString());
						out.append("</TD>"); // start a new data element
					} else {
						out.append("&nbsp;");
						out.append("</TD>"); // start a new data element
					}
				}

				out.append("</TR>\n");
			}
			out.append("</tbody>\n");

			// End the table
			out.append("</TABLE>\n");
		} else {
			// There's a count to be had
			out.append("<B>Records Affected:</B> 2"
					+ stmt.getUpdateCount());
		}
	} catch (SQLException e) {
		out.append("</TABLE><H1>ERROR:</H1> 1" + e.getMessage());
	}
	
	//F. Chan's part
	out.append("<hr> <BR>Handset Basket Batch Info<BR>");
	

	try {

		Statement stmt = con2.createStatement();

		if (stmt.execute(sqlHandsetBasketBatchInfo)) {
			// There's a ResultSet to be had
			ResultSet rs = stmt.getResultSet();
			//class='display'
			out.append("<TABLE  id='table1' border='1'>\n");

			ResultSetMetaData rsmd = rs.getMetaData();

			int numcols = rsmd.getColumnCount();

			// Title the table with the result set's column labels
			//header
			out.append("<thead><TR>");
			for (int i = 1; i <= numcols; i++)
				out.append("<TH>" + rsmd.getColumnLabel(i) + "</TH>");
			out.append("</TR></thead>\n");

			//body
			out.append("<tbody>\n");
			while (rs.next()) {
				out.append("<TR>"); // start a new row
				for (int i = 1; i <= numcols; i++) {
					out.append("<TD>"); // start a new data element
					Object obj = null;
					try {
						obj = rs.getObject(i);
					} catch (Exception e) {
						obj = null;
					}
					if (obj != null) {
						out.append(obj.toString());
						out.append("</TD>"); // start a new data element
					} else {
						out.append("&nbsp;");
						out.append("</TD>"); // start a new data element
					}
				}

				out.append("</TR>\n");
			}
			out.append("</tbody>\n");

			// End the table
			out.append("</TABLE>\n");
		} else {
			// There's a count to be had
			out.append("<B>Records Affected:</B> 2"
					+ stmt.getUpdateCount());
		}
	} catch (SQLException e) {
		out.append("</TABLE><H1>ERROR:</H1> 1" + e.getMessage());
	}
	
	
	
	stmt2.close();
	con2.close();
%>

</BODY>
</HTML>