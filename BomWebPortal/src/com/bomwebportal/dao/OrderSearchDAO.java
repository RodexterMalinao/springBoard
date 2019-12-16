package com.bomwebportal.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.bomwebportal.dto.OrderDTO;
import com.bomwebportal.dto.OrderDTO.CollectMethod;
import com.bomwebportal.dto.OrderDTO.DisMode;
import com.bomwebportal.exception.DAOException;
import com.google.gson.Gson;

public class OrderSearchDAO extends BaseDAO {

	protected final Log logger = LogFactory.getLog(getClass());

	private Gson gson = new Gson();    
	
	public String getAllowSearchAllInd(String loginStaffId) throws DAOException {
		String status = "";

		String SQL = 
			"select DECODE(count(1), 0, 'N', 'Y') STATUS\n" +
			"  from BOMWEB_SALES_ASSIGNMENT SA\n" + 
			" where SA.TEAM_CD = 'TTW' --only allow ttw can search all\n" + 
			"   and sysdate between SA.START_DATE and NVL(SA.END_DATE, sysdate)\n" + 
			"   and SA.STAFF_ID = ?";


		try {
			logger.debug("getAllowSearchAllInd @ OrderSearchDAO: " + SQL);
			status = (String) simpleJdbcTemplate.queryForObject(SQL,
					String.class,loginStaffId );

		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException");
			status = "";
		} catch (Exception e) {
			logger.error("Exception caught in getAllowSearchAllInd()", e);
			throw new DAOException(e.getMessage(), e);
		}
		return status;

	}
	
	public String getMultiSimErrorMsg(String orderId) throws DAOException {
		logger.debug("getMultiSimErrorMsg is called");
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		
		String sql =  "select * from bomweb_ord_mob_member "
				+ "where parent_order_id = :orderId "
				+ "and err_msg is not null";
				
		params.addValue("orderId", orderId);

		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {

			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				
				String errmsg = "<b>Member " + rs.getString("MEMBER_NUM") + " " + rs.getString("MSISDN") + ":</b> " 
						+ rs.getString("MEMBER_STATUS") + " - " + rs.getString("ERR_MSG");
				return errmsg;
			}
		};
		
		try {
			List<String> dto = simpleJdbcTemplate.query(sql, mapper, params);
			
			String out = "";	
			for  (int i = 0; i < dto.size(); i++) {
				if (out.length() > 0) {
					out += "<br/>";
				}
				out = out + dto.get(i).trim();
			}
			return out;
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException");
		} catch (Exception e) {
			logger.error("Exception caught in getMultiSimErrorMsg()", e);
			throw new DAOException(e.getMessage(), e);
		}
		
		return null;
	}

	public List<OrderDTO> getOrderList(String shopCode, String lob,
			String orderStatus, String startDate, String endDate,
			String saleCd, String orderIdStr, String serviceNum, String dmsInd,
			String bomStartDate, String bomEndDate, String orderType) throws DAOException {
		List<OrderDTO> orderList = new ArrayList<OrderDTO>();

		String SQLsearchByOrderId = "SELECT bo.order_id,   \n"
				+ "     DECODE(basket_type_t.basket_type, 6, 'Y', DECODE(TRUNC(bo.APP_DATE), TRUNC(sysdate), 'Y', 'N')) today_order_flag,   \n"
				+ "     bo.shop_cd,   \n"
				+ "     bo.order_status,   \n"
				+ "     bo.ocid,   \n"
				+ "     bo.app_date,   \n"
				+ "     bo.ERR_MSG,   \n"
				/*+ "    trim( bo.err_msg|| \n"
				+ "           decode(decode(bo.err_msg, null, null, (select distinct parent_order_id "
				+ "               from bomweb_ord_mob_member "
				+ "               where parent_order_id=bo.order_id "
				+ "               and err_msg is not null)), null, '', '<br/>')|| \n"
				+ "		     (select LISTAGG(err_msg, '<br/>') WITHIN GROUP (ORDER BY parent_order_id) from \n"
				+ "               (select parent_order_id, \n"
				+ "               '<b>Member '||member_num||' '||msisdn||':</b> '||member_status||' - '||err_msg err_msg \n"
				+ "               from bomweb_ord_mob_member \n"
				+ "               where err_msg is not null) \n"
				+ "          where parent_order_id = bo.order_id)) err_msg,\n"*/
				+ "     basket_type_t.basket_type,   \n"
				+ "     bo.lob,   \n"
				+ "     DECODE(BO.LOB, 'MOB', BO.MSISDN, BC.SERVICE_NUM) SERVICE_NUM,  \n"
				+ "     boi.login_id IMS_LOGIN_ID,   \n"
				+ "     bo.reason_cd,  \n"
				+ "	   bo.check_point,		\n"
				+ "     BC.LAST_NAME ||' ' ||BC.FIRST_NAME cust_full_name,   \n"
				+ "     decode(BO.LOB, 'MOB',   (select SBO.SRV_REQ_DATE from BOMWEB_SB_BOM_ORDER SBO where SBO.OCID = BO.OCID and ROWNUM = 1), bo.bom_creation_date) BOM_CREATION_DATE ,\n" 	
				+ "     bo.dis_mode, \n"
				+ "     bo.collect_method, \n"
				+ "     bo.dms_ind \n"
				+ "   ,(select OM.BOM_FROZEN_IND from BOMWEB_ORDER_MOB OM where OM.ORDER_ID = BO.ORDER_ID and ROWNUM = 1) BOM_FROZEN_IND  \n"
				+ "   ,decode(BO.ORDER_TYPE, 'COS' , (select OM.Order_Nature from BOMWEB_ORDER_MOB OM where OM.ORDER_ID = BO.ORDER_ID and ROWNUM = 1), BO.ORDER_TYPE) ORDER_TYPE\n" 
				+ "   ,(select DECODE(count(1), 0, 'N', 'Y')\n" 
				+ "        from BOMWEB_ORD_MOB_CHG_SIM_TXN OMCST\n" 
				+ "        where NVL(OMCST.MARK_DEL_IND, 'N') != 'Y'\n" 
				+ "        and OMCST.ORDER_ID = BO.ORDER_ID) CHANGE_SIM_IND\n" 
				+ "   ,(select BRAND\n" 
				+ "        from BOMWEB_ACCT BA\n" 
				+ "        where BA.ORDER_ID = BO.ORDER_ID) BRAND\n" 
				+ "   FROM bomweb_order bo,   \n"
				+ "     (SELECT DISTINCT bsi.order_id,   \n"
				+ "       b.type basket_type   \n"
				+ "     FROM bomweb_subscribed_item bsi,   \n"
				+ "       w_basket b   \n"
				+ "     WHERE b.id = bsi.basket_id   \n"
				+ "     ) basket_type_t,   \n"
				+ "     bomweb_customer bc,   \n"
				+ "     bomweb_order_ims boi   \n"
				+ "   WHERE bo.order_id = basket_type_t.order_id   \n"
				+ "   AND bo.order_id   = :orderId  \n"
				+ "   AND bo.shop_cd    = DECODE(:shopCd, 'ALL', BO.SHOP_CD, :shopCd)   \n"
				+ "   AND bo.ORDER_ID   = bc.ORDER_ID   \n"
				+ "   AND bo.ORDER_ID   = boi.ORDER_ID(+)   \n"
				+ "   AND bo.lob not in ('LTS','IMS') \n"
				+ "   AND bo.lob != 'MOB' \n"
				+" and BO.SHOP_CD not in\n" +
				"      (select CL.CODE_ID\n" + 
				"         from BOMWEB_CODE_LKUP CL\n" + 
				"        where CL.CODE_TYPE like 'CCS_CH') --ex CCS order \n"
				
				+ " and BO.SHOP_CD in (SELECT DISTINCT BOM_SHOP_CD FROM BOMWEB_SHOP WHERE CHANNEL_ID in ('1','3')) \n"
				
				+" and not exists (" +
				"	Select 1 from BOMWEB_CODE_LKUP sbof " +
				"	where sbof.code_type = 'IMS_NON_RETAIL_SHOP_CD' and BO.SHOP_CD = sbof.CODE_ID) -- Filter IMS non-retail orders\n"

				+ " UNION \n"
				+ "     -- MOB Order \n"
				+ "SELECT bo.order_id,   \n"
				+ "     DECODE(basket_type_t.basket_type, 6, 'Y', DECODE(TRUNC(bo.APP_DATE), TRUNC(sysdate), 'Y', 'N')) today_order_flag,   \n"
				+ "     bo.shop_cd,   \n"
				+ "     bo.order_status,   \n"
				+ "     bo.ocid,   \n"
				+ "     bo.app_date,   \n"
				+ "     bo.ERR_MSG,   \n"
				/*+ "    trim( bo.err_msg|| \n"
				+ "           decode(decode(bo.err_msg, null, null, (select distinct parent_order_id "
				+ "               from bomweb_ord_mob_member "
				+ "               where parent_order_id=bo.order_id "
				+ "               and err_msg is not null)), null, '', '<br/>')|| \n"
				+ "		     (select LISTAGG(err_msg, '<br/>') WITHIN GROUP (ORDER BY parent_order_id) from \n"
				+ "               (select parent_order_id, \n"
				+ "               '<b>Member '||member_num||' '||msisdn||':</b> '||member_status||' - '||err_msg err_msg \n"
				+ "               from bomweb_ord_mob_member \n"
				+ "               where err_msg is not null) \n"
				+ "          where parent_order_id = bo.order_id)) err_msg,\n"*/
				+ "     basket_type_t.basket_type,   \n"
				+ "     bo.lob,   \n"
				+ "     DECODE(BO.LOB, 'MOB', BO.MSISDN, BC.SERVICE_NUM) SERVICE_NUM,  \n"
				+ "     boi.login_id IMS_LOGIN_ID,   \n"
				+ "     bo.reason_cd,  \n"
				+ "	   bo.check_point,		\n"
				+ "     BC.LAST_NAME ||' ' ||BC.FIRST_NAME cust_full_name,   \n"
				+ "     decode(BO.LOB, 'MOB',   (select SBO.SRV_REQ_DATE from BOMWEB_SB_BOM_ORDER SBO where SBO.OCID = BO.OCID and ROWNUM = 1), bo.bom_creation_date) BOM_CREATION_DATE ,\n" 	
				+ "     bo.dis_mode, \n"
				+ "     bo.collect_method, \n"
				+ "     bo.dms_ind \n"
				+ "   ,(select OM.BOM_FROZEN_IND from BOMWEB_ORDER_MOB OM where OM.ORDER_ID = BO.ORDER_ID and ROWNUM = 1) BOM_FROZEN_IND  \n"
				+ "   ,decode(BO.ORDER_TYPE, 'COS' , (select OM.Order_Nature from BOMWEB_ORDER_MOB OM where OM.ORDER_ID = BO.ORDER_ID and ROWNUM = 1), BO.ORDER_TYPE) ORDER_TYPE\n" 
				+ "   ,(select DECODE(count(1), 0, 'N', 'Y')\n" 
				+ "        from BOMWEB_ORD_MOB_CHG_SIM_TXN OMCST\n" 
				+ "        where NVL(OMCST.MARK_DEL_IND, 'N') != 'Y'\n" 
				+ "        and OMCST.ORDER_ID = BO.ORDER_ID) CHANGE_SIM_IND\n" 
				+ "   ,(select BRAND\n" 
				+ "        from BOMWEB_ACCT BA\n" 
				+ "        where BA.ORDER_ID = BO.ORDER_ID) BRAND\n" 
				+ "   FROM bomweb_order bo,   \n"
				+ "     (SELECT DISTINCT bsi.order_id,   \n"
				+ "       b.type basket_type   \n"
				+ "     FROM bomweb_subscribed_item bsi,   \n"
				+ "       w_basket b   \n"
				+ "     WHERE b.id = bsi.basket_id   \n"
				+ "     ) basket_type_t,   \n"
				+ "     bomweb_customer bc,   \n"
				+ "     bomweb_order_ims boi   \n"
				+ "   WHERE bo.order_id = basket_type_t.order_id(+)   \n"
				+ "   AND bo.order_id   = :orderId  \n"
				+ "   AND bo.shop_cd    = DECODE(:shopCd, 'ALL', BO.SHOP_CD, :shopCd)   \n"
				+ "   AND bo.ORDER_ID   = bc.ORDER_ID   \n"
				+ "   AND bo.ORDER_ID   = boi.ORDER_ID(+)   \n"
				+ "   AND bo.lob = 'MOB' \n"
				+"    AND substrb(bo.ORDER_ID,1,1) in ('R','P') \n"
				/*+" and BO.SHOP_CD not in\n" +
				"      (select CL.CODE_ID\n" + 
				"         from BOMWEB_CODE_LKUP CL\n" + 
				"        where CL.CODE_TYPE like 'CCS_CH') --ex CCS order \n"*/
				
				+ " and BO.SHOP_CD in (SELECT DISTINCT BOM_SHOP_CD FROM BOMWEB_SHOP WHERE CHANNEL_ID in ('1','3')) \n"
				
			/*	+" and not exists (" +
				"	Select 1 from BOMWEB_CODE_LKUP sbof " +
				"	where sbof.code_type = 'IMS_NON_RETAIL_SHOP_CD' and BO.SHOP_CD = sbof.CODE_ID) -- Filter IMS non-retail orders\n"*/
								
				
				+ " UNION \n"
				+ "     -- LTS Order \n"
				+ "     SELECT bo.order_id,   \n"
				+ "     DECODE(bols.sb_status, 'S', 'Y', 'N') today_order_flag,  -- allow recall LTS suspend order \n"
				+ "     bo.shop_cd,   \n"
				+ "     bols.sb_status order_status,   \n"
				+ "     bo.ocid,   \n" + "     bo.app_date,   \n"
				+ "     bo.ERR_MSG,   \n" + "     null BASKET_TYPE,   \n"
				+ "     bo.lob,  \n" + "     bos.srv_num SERVICE_NUM,  \n"
				+ "     null IMS_LOGIN_ID,   \n"
				+ "     bols.rea_cd reason_cd,  \n" + "	   bo.check_point,		\n"
				+ "     BC.LAST_NAME ||' ' ||BC.FIRST_NAME cust_full_name,   \n"
				+ " 	bo.bom_creation_date, \n"
				+ "     bo.dis_mode, \n"
				+ "     bo.collect_method, \n"
				+ "     bo.dms_ind \n"
				+ "   ,null BOM_FROZEN_IND  \n"
				+ "   ,null ORDER_TYPE\n" 
				+ "   ,null CHANGE_SIM_IND\n" 
				+ "   ,null BRAND\n" 
				+ "   FROM bomweb_order bo,   \n"
				+ "        bomweb_customer bc,   \n"
				+ "        bomweb_order_service bos, \n"
				+ "        bomweb_order_latest_status bols, \n"
				+ "        bomweb_service_acct_assgn bsaa, \n"
				+ "        bomweb_acct ba     \n"
				+ "   WHERE bo.order_id = bos.order_id \n"
				+ "   AND bos.order_id = bols.order_id \n"
				+ "   AND bos.dtl_id =  bols.dtl_id \n"
				+ "   AND bo.order_id   = :orderId   \n"
				+ "   AND (bo.shop_cd    = DECODE(:shopCd, 'ALL', BO.SHOP_CD, :shopCd)   \n"
				+ "     OR bo.SALES_TEAM    = DECODE(:shopCd, 'ALL', BO.SALES_TEAM, :shopCd)) \n"
				+ " and not exists (" +
				"	Select 1 from BOMWEB_CODE_LKUP sbof " +
				"	where sbof.code_type = 'LTS_NON_RETAIL_SHOP_CD' and BO.SHOP_CD = sbof.CODE_ID) \n"
				+ "   AND bos.ORDER_ID = ba.ORDER_ID \n"
				+ "   AND bos.dtl_id = bsaa.dtl_id  \n"
				+ "   AND bos.order_id = bsaa.order_id  \n"
				+ "   AND ((bsaa.action = 'I' OR bsaa.action = ' ') OR (BO.ORDER_TYPE = 'SBD' AND bsaa.action = 'O'))  \n"
				+ "   AND bsaa.CHRG_TYPE = 'R'  \n"
				+ "   AND bsaa.acct_no = ba.acct_no  \n"
				+ "   AND bc.ORDER_ID = ba.ORDER_ID  \n"
				+ "   AND bc.cust_no = ba.cust_no  \n"
				+ "   AND bo.lob = 'LTS' \n" 
				
				+ " UNION \n"
				+ "     -- IMS Order \n"
				+ "		select BO.ORDER_ID,\n"
				+ "       DECODE(TRUNC(BO.APP_DATE), TRUNC(sysdate), 'Y', 'N') TODAY_ORDER_FLAG,\n"
				+ "       BO.SHOP_CD,\n"
				+ "       BO.ORDER_STATUS,\n"
				+ "       BO.OCID,\n"
				+ "       BO.APP_DATE,\n"
				+ "       BO.ERR_MSG,\n"
				+ "       null BASKET_TYPE,\n"
				+ "       BO.LOB,\n"
				+ "       DECODE(BO.LOB, 'MOB', BO.MSISDN, BC.SERVICE_NUM) SERVICE_NUM,\n"
				+ "       BOI.LOGIN_ID IMS_LOGIN_ID,\n"
				+ "       BO.REASON_CD,\n"
				+ "       BO.CHECK_POINT,\n"
				+ "       decode(bc.ID_DOC_TYPE, 'BS', bc.company_name, bc.last_name || ' ' || bc.first_name) CUST_FULL_NAME,\n"
				+ " 	  bo.bom_creation_date, \n"
				+ "       BO.dis_mode,\n"
				+ "       BO.collect_method,\n"
				+ "       BO.dms_ind,\n"	
				+ "       null BOM_FROZEN_IND,\n"
				//+ "       BO.ORDER_TYPE ORDER_TYPE,\n" 
				+ "       nvl(BO.ORDER_TYPE, boi.ims_order_type) ORDER_TYPE,\n"	//ims 20150901
				+ "   	  null CHANGE_SIM_IND,\n" 
				+ "   	  null BRAND\n" 
				+ "    from BOMWEB_ORDER BO, BOMWEB_CUSTOMER BC, BOMWEB_ORDER_IMS BOI	\n"
				+ "    where BO.LOB = 'IMS' -- IMS query\n"
				+ "    and bo.shop_cd    = DECODE(:shopCd, 'ALL', BO.SHOP_CD, :shopCd)   \n"
				+ "    and BO.ORDER_ID = :orderId   \n"
				+ "    and BO.ORDER_ID = BC.ORDER_ID\n"
				+ "    and BO.ORDER_ID = BOI.ORDER_ID(+)\n"
				+ "    and not exists (\n" 
				+ "		select 1 from BOMWEB_CODE_LKUP sbof\n" 
				+ "	    where sbof.code_type = 'IMS_NON_RETAIL_SHOP_CD'\n" 
				+ "     and BO.SHOP_CD = sbof.CODE_ID )\n" 
				+ "     and BO.SHOP_CD in (SELECT DISTINCT BOM_SHOP_CD FROM BOMWEB_SHOP WHERE CHANNEL_ID='1') -- Filter IMS non-retail orders\n"
				+ "   ORDER BY order_id ";

		String SQLSearchByMOB = "select BO.ORDER_ID,\n"
				+ "       DECODE(BASKET_TYPE_T.BASKET_TYPE,\n"
				+ "              6,\n"
				+ "              'Y',\n"
				+ "              DECODE(TRUNC(BO.APP_DATE), TRUNC(sysdate), 'Y', 'N')) TODAY_ORDER_FLAG,\n"
				+ "       BO.SHOP_CD,\n"
				+ "       BO.ORDER_STATUS,\n"
				+ "       BO.OCID,\n"
				+ "       BO.APP_DATE,\n"
				+ "       BO.ERR_MSG,\n"
				/*+ "       trim(bo.err_msg||"
				+ "           decode(decode(bo.err_msg, null, null, (select distinct parent_order_id "
				+ "               from bomweb_ord_mob_member "
				+ "               where parent_order_id=bo.order_id "
				+ "               and err_msg is not null)), null, '', '<br/>')|| \n"
				+ "		     (select LISTAGG(err_msg, '<br/>') WITHIN GROUP (ORDER BY parent_order_id) from \n"
				+ "               (select parent_order_id, \n"
				+ "               '<b>Member '||member_num||' '||msisdn||':</b> '||member_status||' - '||err_msg err_msg \n"
				+ "               from bomweb_ord_mob_member \n"
				+ "               where err_msg is not null) \n"
				+ "          where parent_order_id = bo.order_id)) err_msg,\n"*/
				+ "       BASKET_TYPE_T.BASKET_TYPE,\n"
				+ "       BO.LOB,\n"
				+ "       DECODE(BO.LOB, 'MOB', BO.MSISDN, BC.SERVICE_NUM) SERVICE_NUM,\n"
				+ "       null IMS_LOGIN_ID,\n"
				+ "       BO.REASON_CD,\n"
				+ "       BO.CHECK_POINT,\n"
				+ "       BC.LAST_NAME || ' ' || BC.FIRST_NAME CUST_FULL_NAME, \n"
				+ "       (select SBO.SRV_REQ_DATE from BOMWEB_SB_BOM_ORDER SBO where SBO.OCID = BO.OCID and ROWNUM = 1) BOM_CREATION_DATE ,\n" 
				+ "     BO.dis_mode, \n"
				+ "     BO.collect_method, \n"
				+ "     BO.dms_ind \n"
				+ "   ,(select OM.BOM_FROZEN_IND from BOMWEB_ORDER_MOB OM where OM.ORDER_ID = BO.ORDER_ID and ROWNUM = 1) BOM_FROZEN_IND  \n"
				+ "   ,decode(BO.ORDER_TYPE, 'COS' , (select OM.Order_Nature from BOMWEB_ORDER_MOB OM where OM.ORDER_ID = BO.ORDER_ID and ROWNUM = 1), BO.ORDER_TYPE) ORDER_TYPE \n" 
				+ "   ,(select DECODE(count(1), 0, 'N', 'Y')\n" 
				+ "        from BOMWEB_ORD_MOB_CHG_SIM_TXN OMCST\n" 
				+ "        where NVL(OMCST.MARK_DEL_IND, 'N') != 'Y'\n" 
				+ "        and OMCST.ORDER_ID = BO.ORDER_ID) CHANGE_SIM_IND\n"
				+ "   ,(select BRAND\n" 
				+ "        from BOMWEB_ACCT BA\n" 
				+ "        where BA.ORDER_ID = BO.ORDER_ID) BRAND\n" 
				+ "  from BOMWEB_ORDER BO,\n"
				+ "       (select distinct BSI.ORDER_ID, B.TYPE BASKET_TYPE\n"
				+ "          from BOMWEB_SUBSCRIBED_ITEM BSI, W_BASKET B\n"
				+ "         where B.ID = BSI.BASKET_ID) BASKET_TYPE_T,\n"
				+ "       BOMWEB_CUSTOMER BC, \n"
				+ "       BOMWEB_SB_BOM_ORDER BOM \n"
				+ " where BO.LOB = 'MOB' \n"
				//+ "   AND bo.shop_cd    = DECODE(:shopCd, 'ALL', BO.SHOP_CD, :shopCd)   \n"
				/*	+ "   AND bo.order_id NOT LIKE 'D%'\n"*/
				+"    AND substrb(bo.ORDER_ID,1,1) in ('R','P') \n"
				/*+"    and BO.SHOP_CD not in\n" +
				"      (select CL.CODE_ID\n" + 
				"         from BOMWEB_CODE_LKUP CL\n" + 
				"        where CL.CODE_TYPE like 'CCS_CH') --ex CCS shop order\n "*/
				//+ "   and BO.SHOP_CD in (SELECT DISTINCT BOM_SHOP_CD FROM BOMWEB_SHOP WHERE CHANNEL_ID in ('1', '3')) \n"
				+ "   and BO.sales_cd = nvl(:saleCd, BO.sales_cd)\n"
				+ "   and TRUNC(BO.app_DATE) between --range search\n"
				+ "       TRUNC(TO_DATE(:startDate, 'dd/mm/yyyy')) and\n"
				+ "       TRUNC(TO_DATE(:endDate, 'dd/mm/yyyy'))\n"
				+ "   and BO.ORDER_ID = BASKET_TYPE_T.ORDER_ID(+) \n"
				+ "   and BO.ORDER_ID = BC.ORDER_ID\n"
				+ "   and BO.ORDER_ID = BOM.ORDER_ID(+) \n"
				+ "   and (NVL(:serviceNum,'*')='*' OR BO.MSISDN=:serviceNum \n"
				+ "       OR :serviceNum in \n"
				+ "           (select MSISDN from BOMWEB_ORD_MOB_MEMBER \n"
				+ "            WHERE PARENT_ORDER_ID = BO.ORDER_ID))\n"
				+ "   and (NVL(:collectMethod,'*')='*' OR BO.COLLECT_METHOD=:collectMethod)\n"
				+ "   and (NVL(:dmsInd,'*')='*' OR NVL(BO.DMS_IND,'N')=:dmsInd)\n"
				;
		if ("ALL".equalsIgnoreCase(shopCode)) {
			SQLSearchByMOB += "   and BO.SHOP_CD in (SELECT DISTINCT BOM_SHOP_CD FROM BOMWEB_SHOP WHERE CHANNEL_ID in ('1')) \n";
		} else {
			SQLSearchByMOB += "   AND bo.shop_cd = :shopCd \n";
		}

		if ("PENDING".equalsIgnoreCase(orderStatus)) {
			SQLSearchByMOB = SQLSearchByMOB
					+ "   and BO.ORDER_STATUS  in( 'INITIAL' ,'SIGNOFF','PENDING','PROCESS','FAILED', '01' , '99')"; //ret add '01'
		}else if ("CANCEL".equalsIgnoreCase(orderStatus)){
			SQLSearchByMOB = SQLSearchByMOB
			+ "   and BO.ORDER_STATUS  in ( 'VOID', '03', '04')";//ret add 03,04
		}

		String SQLSearchByIMS = "select BO.ORDER_ID,\n"
				+ "       DECODE(TRUNC(BO.APP_DATE), TRUNC(sysdate), 'Y', 'N') TODAY_ORDER_FLAG,\n"
				+ "       BO.SHOP_CD,\n"
				+ "       BO.ORDER_STATUS,\n"
				+ "       BO.OCID,\n"
				+ "       BO.APP_DATE,\n"
				+ "       BO.ERR_MSG,\n"
				+ "       null BASKET_TYPE,\n"
				+ "       BO.LOB,\n"
				+ "       DECODE(BO.LOB, 'MOB', BO.MSISDN, BC.SERVICE_NUM) SERVICE_NUM,\n"
				+ "       BOI.LOGIN_ID IMS_LOGIN_ID,\n"
				+ "       BO.REASON_CD,\n"
				+ "       BO.CHECK_POINT,\n"
				+ "       decode(bc.ID_DOC_TYPE, 'BS', bc.company_name, bc.last_name || ' ' || bc.first_name) CUST_FULL_NAME,\n"
				+ " 	bo.bom_creation_date, \n"
				+ "     BO.dis_mode, \n"
				+ "     BO.collect_method, \n"
				+ "     BO.dms_ind \n"	
				+ "   ,	null BOM_FROZEN_IND  \n"    
				+ "   , nvl(BO.ORDER_TYPE, boi.ims_order_type) ORDER_TYPE\n"	//ims 20150901
//				+ "   ,null ORDER_TYPE\n" 
				+ "   ,	null CHANGE_SIM_IND\n"
				+ "   , null BRAND\n" 
				+ "  from BOMWEB_ORDER BO, BOMWEB_CUSTOMER BC, BOMWEB_ORDER_IMS BOI\n"
				+ " where BO.LOB = 'IMS' -- IMS query\n"
				+ "   AND bo.shop_cd    = DECODE(:shopCd, 'ALL', BO.SHOP_CD, :shopCd)   \n"
				+ "   and TRUNC(BO.APP_DATE) between --range search\n"
				+ "       TRUNC(TO_DATE(:startDate, 'dd/mm/yyyy')) and\n"
				+ "       TRUNC(TO_DATE(:endDate, 'dd/mm/yyyy'))\n"
				+ "   and BO.ORDER_ID = BC.ORDER_ID\n"
				+ "   and BO.ORDER_ID = BOI.ORDER_ID(+)\n"
				+" and not exists (" +
				"	Select 1 from BOMWEB_CODE_LKUP sbof " +
				"	where sbof.code_type = 'IMS_NON_RETAIL_SHOP_CD' " + 
				"         and BO.SHOP_CD = sbof.CODE_ID )" +
				"     and BO.SHOP_CD in (SELECT DISTINCT BOM_SHOP_CD FROM BOMWEB_SHOP WHERE CHANNEL_ID='1') -- Filter IMS non-retail orders\n"
				+ "   and (NVL(:serviceNum,'*')='*' OR BC.SERVICE_NUM=:serviceNum)\n"
				+ "   and (NVL(:collectMethod,'*')='*' OR BO.COLLECT_METHOD=:collectMethod)\n"
				+ "   and (NVL(:dmsInd,'*')='*' OR NVL(BO.DMS_IND,'N')=:dmsInd)\n"				
				;

		if (StringUtils.isNotBlank(saleCd)) {
			String SQLsubIMSsaleCd = "   and BO.SALES_CD in -- first convert to original staff id and then search\n"
					+ "       (select ORG_STAFF_ID\n"
					+ "          from (select ORG_STAFF_ID, STAFF_ID\n"
					+ "                  from BOMWEB_SALES_PROFILE\n"
					+ "                 where STAFF_ID = :saleCd\n"
					+ "                 order by END_DATE desc) TMP\n"
					+ "         where ROWNUM = 1)\n";

			SQLSearchByIMS += SQLsubIMSsaleCd;
		}

		if ("PENDING".equalsIgnoreCase(orderStatus)) {
			SQLSearchByIMS = SQLSearchByIMS
					+ "   and BO.ORDER_STATUS in \n"
					+ "       (select CODE from W_CODE_LKUP where GRP_ID = 'SB_IMS_ACQ_PENDING')";
		}else if ("CANCEL".equalsIgnoreCase(orderStatus)){
			SQLSearchByIMS = SQLSearchByIMS
			+ "   and BO.order_status='20' -- cancelled Springboard IMS order and displayed as cancelled \n";
		}
		
		if(orderType!=null && !orderType.isEmpty()){
			if(orderType.equalsIgnoreCase("NTV-A"))
				SQLSearchByIMS += " and BO.ORDER_TYPE = 'NTV-A' ";
			else if(orderType.equalsIgnoreCase("NTVAO"))
				SQLSearchByIMS += " and BO.ORDER_TYPE = 'NTVAO' ";
			else if(orderType.equalsIgnoreCase("NTVUS"))
				SQLSearchByIMS += " and BO.ORDER_TYPE = 'NTVUS' ";
			else if(orderType.equalsIgnoreCase("NTVRE"))
				SQLSearchByIMS += " and BO.ORDER_TYPE = 'NTVRE' ";
			else if(orderType.equalsIgnoreCase("PCD-A"))
				SQLSearchByIMS += " and (BO.ORDER_TYPE is null or BO.ORDER_TYPE = 'PCD-A') "
							   +  " and BOI.IMS_ORDER_TYPE is null ";
			else if(orderType.equalsIgnoreCase("RET"))
				SQLSearchByIMS += " and BOI.IMS_ORDER_TYPE = 'PCD_R' ";
			else if(orderType.equalsIgnoreCase("TEMP"))
				SQLSearchByIMS += " and BOI.IMS_ORDER_TYPE = 'PCD_T' ";
		}

		String SQLSearchByLTS =

		"select \n"
				+ "       BO.ORDER_ID,\n"
				+ "       DECODE(BOLS.SB_STATUS, 'S', 'Y', 'N') TODAY_ORDER_FLAG,\n"
				+ "       BO.SHOP_CD,\n"
				+ "       BOLS.SB_STATUS ORDER_STATUS,\n"
				+ "       BO.OCID,\n"
				+ "       BO.APP_DATE,\n"
				+ "       BO.ERR_MSG,\n"
				+ "       null BASKET_TYPE,\n"
				+ "       BO.LOB,\n"
				+ "       BOS.SRV_NUM SERVICE_NUM,\n"
				+ "       null IMS_LOGIN_ID,\n"
				+ "       BOLS.REA_CD REASON_CD,\n"
				+ "       BO.CHECK_POINT,\n"
				+ "       BC.LAST_NAME || ' ' || BC.FIRST_NAME CUST_FULL_NAME, \n"
				+ " 	bo.bom_creation_date, \n"
				+ "     BO.dis_mode, \n"
				+ "     BO.collect_method, \n"
				+ "     BO.dms_ind \n"	
				+ "   ,null BOM_FROZEN_IND  \n"
				+ "   ,null ORDER_TYPE\n" 
				+ "   ,null CHANGE_SIM_IND\n" 
				+ "   ,null BRAND\n" 
				+ "  from BOMWEB_ORDER               BO,\n"
				+ "       BOMWEB_CUSTOMER            BC,\n"
				+ "       BOMWEB_ORDER_SERVICE       BOS,\n"
				+ "       BOMWEB_ORDER_LATEST_STATUS BOLS,\n"
				+ "        bomweb_service_acct_assgn bsaa, \n"
				+ "       BOMWEB_ACCT                BA\n"
				+ " where BO.LOB = 'LTS'\n" 
				+ "   AND (bo.shop_cd    = DECODE(:shopCd, 'ALL', BO.SHOP_CD, :shopCd)   \n"
				+ "     OR bo.SALES_TEAM    = DECODE(:shopCd, 'ALL', BO.SALES_TEAM, :shopCd)) \n" 
				+ " and not exists (" +
				"	Select 1 from BOMWEB_CODE_LKUP sbof " +
				"	where sbof.code_type = 'LTS_NON_RETAIL_SHOP_CD' and BO.SHOP_CD = sbof.CODE_ID) \n"
				+ "   and BO.SHOP_CD in (SELECT DISTINCT BOM_SHOP_CD FROM BOMWEB_SHOP WHERE CHANNEL_ID='1')\n"
				+ "   and TRUNC(BO.APP_DATE) between --range search\n"
				+ "       TRUNC(TO_DATE(:startDate, 'dd/mm/yyyy')) and\n"
				+ "       TRUNC(TO_DATE(:endDate, 'dd/mm/yyyy'))\n"
				+ "   and BO.ORDER_ID = BOS.ORDER_ID\n"
				+ "   and BOS.ORDER_ID = BOLS.ORDER_ID\n"
				+ "   and BOS.DTL_ID = BOLS.DTL_ID\n"
				+ "   and BOS.ORDER_ID = BA.ORDER_ID\n"
				+ "   AND bos.dtl_id = bsaa.dtl_id  \n"
				+ "   AND bos.order_id = bsaa.order_id  \n"
				+ "   AND ((bsaa.action = 'I' OR bsaa.action = ' ') OR (BO.ORDER_TYPE = 'SBD' AND bsaa.action = 'O'))  \n"
				+ "   AND bsaa.CHRG_TYPE = 'R'  \n"
				+ "   AND bsaa.acct_no = ba.acct_no  \n"
				+ "   and BC.ORDER_ID = BA.ORDER_ID\n"
				+ "   and BC.CUST_NO = BA.CUST_NO\n"
				+ "   and BO.order_id =BA.ORDER_ID\n"
				+ "   and (NVL(:serviceNum,'*')='*' OR BOS.SRV_NUM=:serviceNum)\n"
				+ "   and (NVL(:collectMethod,'*')='*' OR BO.COLLECT_METHOD=:collectMethod)\n"
				+ "   and (NVL(:dmsInd,'*')='*' OR NVL(BO.DMS_IND,'N')=:dmsInd)\n"					
				;
		if (StringUtils.isNotBlank(saleCd)) {
			String subSQLSearchByLTS = "   and BO.STAFF_NUM = (select ORG_STAFF_ID\n"
					+ "                         from BOMWEB_SALES_PROFILE\n"
					+ "                        where STAFF_ID = :saleCd\n"
					+ "                          and ROWNUM = 1)\n";

			SQLSearchByLTS += subSQLSearchByLTS;
		}

		if ("PENDING".equalsIgnoreCase(orderStatus)) {
			SQLSearchByLTS = SQLSearchByLTS
					+ "   and BOLS.SB_STATUS in (select CODE from W_CODE_LKUP where GRP_ID = 'LTS_RT_PENDING_STS')";
		}else if ("CANCEL".equalsIgnoreCase(orderStatus)){
			SQLSearchByLTS = SQLSearchByLTS
					+ "   and BOLS.SB_STATUS IN (SELECT CODE FROM W_CODE_LKUP WHERE GRP_ID = 'LTS_RT_CANCEL_STS')	 -- FILTER CANCEL STATUS)";
		}

		ParameterizedRowMapper<OrderDTO> mapper = new ParameterizedRowMapper<OrderDTO>() {

			public OrderDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				OrderDTO order = new OrderDTO();

				order.setOrderId(rs.getString("order_id"));
				order.setShopCode(rs.getString("shop_cd"));
				order.setOrderStatus(rs.getString("order_status"));
				order.setOcid(rs.getString("ocid"));
				order.setAppInDate(rs.getTimestamp("app_date"));
				order.setErrorMessage(rs.getString("ERR_MSG"));
				order.setTodayOrderFlag(rs.getString("today_order_flag"));
				order.setOrderSumLob(rs.getString("LOB"));
				order.setOrderSumServiceNum(rs.getString("SERVICE_NUM"));
				order.setImsLoginId(rs.getString("IMS_LOGIN_ID"));
				order.setReasonCd(rs.getString("reason_cd"));
				order.setCheckPoint(rs.getString("check_point"));
				order.setOrderSumCustName(rs.getString("cust_full_name"));
				order.setBomCreationDate(rs.getTimestamp("bom_creation_date"));
				String disMode = rs.getString("DIS_MODE"); 
				order.setDisMode(disMode==null?null:DisMode.valueOf(disMode));

				String collectMethod = rs.getString("COLLECT_METHOD");
				order.setCollectMethod(collectMethod==null?null:CollectMethod.valueOf(collectMethod));

				order.setDmsInd(rs.getString("DMS_IND"));
				order.setBomFrozenInd("Y".equals(rs.getString("BOM_FROZEN_IND")) ? "Y" : "N");
		    	order.setOrderType(rs.getString("ORDER_TYPE"));
				order.setChangeSimInd(rs.getString("CHANGE_SIM_IND"));
				order.setBrand(rs.getString("BRAND"));
				

				return order;
			}
		};
		MapSqlParameterSource params = new MapSqlParameterSource();

		// parm
		if (StringUtils.isNotBlank(orderIdStr)) {
			params.addValue("orderId", orderIdStr);
			params.addValue("shopCd", shopCode);
		} else {

			
			params.addValue("shopCd", shopCode);
			params.addValue("startDate", startDate);
			params.addValue("endDate", endDate);
			params.addValue("saleCd", saleCd);
			params.addValue("serviceNum", StringUtils.trimToNull(serviceNum));
			
			
			String pCollectMethod = null;
			String pDmsInd = null;
			if ("Y".equals(dmsInd)) {
				pCollectMethod = "D";
				pDmsInd = "Y";
			} else if ("N".equals(dmsInd)) {
				pCollectMethod = "D";
				pDmsInd = "N";
			} else if (StringUtils.isNotEmpty(dmsInd)) {
				// not applicable
				pCollectMethod = "P";
				pDmsInd = null;
			}
			
			params.addValue("dmsInd", pDmsInd);
			params.addValue("collectMethod", pCollectMethod);
			
			if ("MOB".equals(lob) && StringUtils.isNotEmpty(bomStartDate) && StringUtils.isNotEmpty(bomEndDate)){
				SQLSearchByMOB += 
						" and ( TRUNC(BOM.srv_req_date) between \n "
						+ " TRUNC(TO_DATE(:bomStartDate, 'dd/mm/yyyy')) and \n "
						+ " TRUNC(TO_DATE(:bomEndDate, 'dd/mm/yyyy')) ) \n ";
				
				params.addValue("bomStartDate", bomStartDate);
				params.addValue("bomEndDate", bomEndDate);
			}
		}

		try {
			

			if (StringUtils.isNotBlank(orderIdStr)) {
				logger.info("getOrderSummaryList @ OrderDAO: "
						+ SQLsearchByOrderId);
				logger.info("orderSearch by orderId :" + orderIdStr);
				orderList = simpleJdbcTemplate.query(SQLsearchByOrderId,
						mapper, params);
			} else {
				logger.debug("orderSearch by other parameter");
				logger.debug("shopCode :" + shopCode);
				logger.debug("lob :" + lob);
				logger.debug("orderStatus :" + orderStatus);
				logger.debug("startDate :" + startDate);
				logger.debug("endDate :" + endDate);
				logger.debug("saleCd :" + saleCd);
				logger.info("params :" + gson.toJson(params));
				logger.debug("SQLSearchByMOB :" + SQLSearchByMOB);
				logger.debug("SQLSearchByIMS :" + SQLSearchByIMS);
				logger.debug("SQLSearchByLTS :" + SQLSearchByLTS);
				String finialSQL = "";
				if ("MOB".equals(lob)) {
					finialSQL = SQLSearchByMOB;

				} else if ("IMS".equals(lob)) {
					finialSQL = SQLSearchByIMS;

				} else if ("LTS".equals(lob)) {
					finialSQL = SQLSearchByLTS;

				} else {
					finialSQL = SQLSearchByMOB + "union " + SQLSearchByIMS
							+ "union " + SQLSearchByLTS;
				}

				finialSQL += "order by order_id";
				logger.debug("finialSQL :" + finialSQL);

				orderList = simpleJdbcTemplate.query(finialSQL, mapper, params);
			}
			if (orderList != null && orderList.size() > 0) {
				for (OrderDTO dto: orderList) {
					try {
						String multiSimErrMsg = getMultiSimErrorMsg(dto.getOrderId());
						if (multiSimErrMsg != null && multiSimErrMsg.length() > 0) {
							if (dto.getErrorMessage() != null && dto.getErrorMessage().length() > 0) {
								dto.setErrorMessage(dto.getErrorMessage() + "<br/>" + multiSimErrMsg);
							} else {
								dto.setErrorMessage(multiSimErrMsg);
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("getOrderList EmptyResultDataAccessException");

			orderList = null;
		} catch (Exception e) {
			logger.error("Exception caught in getOrderList()", e);

			throw new DAOException(e.getMessage(), e);
		}
		return orderList;

	}

}
