package com.bomwebportal.lts.dao;

import org.apache.commons.lang.StringUtils;
import org.springframework.dao.EmptyResultDataAccessException;

import com.bomwebportal.dao.BaseDAO;
import com.bomwebportal.dto.AlertOrderDTO;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.lts.util.LtsConstant;
import com.pccw.util.db.OracleSelectHelper;
import com.pccw.util.db.stringOracleType.OraArrayVarChar2;

public class LtsAlertMessageDAO extends BaseDAO {

	private static final String SQL_GET_ORDER_INFO_LTS = 
		   "  SELECT bolsv.order_id \"orderId\",    "+
	       "     bolsv.shop_cd \"shopCode\",    "+
	       "     bolsv.sb_status \"orderStatus\",    "+
	       "     bolsv.ocid \"ocid\",    "+
	       "     TO_CHAR(bolsv.app_date, 'DD/MM/YYYY') \"applnDate\",  "+  
	       "     bolsv.ERR_MSG \"errorMessage\",    "+
	       "     bolsv.lob \"orderSumLob\",   "+
	       "     bolsv.rea_cd \"reasonCd\", "+
		   "     bolsv.check_point \"checkPoint\",		 "+
	       "     bolsv.CUST_NAME \"orderSumCustName\",    "+
	       "     TO_CHAR(bolsv.bom_creation_date, 'DD/MM/YYYY') \"bomCreationDate\",  "+
	       "     bolsv.dis_mode \"disMode\",  "+
	       "     bolsv.collect_method \"collectMethod\",  "+
	       "     bolsv.dms_ind  \"dmsInd\", "+
	       "     RTRIM(XMLAGG (XMLELEMENT(e, bolsv.srv_num || chr(13)) ORDER BY bolsv.srv_num).EXTRACT('//text()'),',') \"orderSumServiceNum\" "+
	       " FROM (SELECT DISTINCT "+
		   "		ORDER_ID, "+
		   "		SHOP_CD, "+
		   "		SB_STATUS, "+
		   "		OCID,"+
		   "	 	APP_DATE,"+
		   "		ERR_MSG,"+
		   "		LOB,"+
		   "		SRV_NUM,"+
		   "		REA_CD,"+
		   "		CHECK_POINT,"+
		   "		CUST_NAME,"+
		   "		BOM_CREATION_DATE,"+
	       "		DIS_MODE,"+
		   "		COLLECT_METHOD,"+
		   "		ESIG_EMAIL_ADDR,"+
		   "		DMS_IND"+
		   "		FROM BOMWEB_ORDER_LTS_SEARCH_V) bolsv     "+
	       " WHERE bolsv.order_id MEMBER OF ?"+
	       " GROUP BY bolsv.order_id,   "+
	       "      bolsv.shop_cd, "+
		   "      bolsv.sb_status, "+
		   "      bolsv.ocid, "+
		   "      bolsv.app_date, "+
	       "      bolsv.ERR_MSG, "+
	       "      bolsv.lob, "+
	       "      bolsv.rea_cd, "+
	       "      bolsv.check_point,"+
	       "      bolsv.CUST_NAME, "+
	       "      bolsv.bom_creation_date, "+
	       "      bolsv.dis_mode, "+
	       "      bolsv.collect_method, "+
	       "      bolsv.ESIG_EMAIL_ADDR, "+
	       "      bolsv.dms_ind"+
	       " ORDER BY bolsv.order_id";  

	
	private static final String SQL_GET_ORDER_INFO_IMS = 
			  " SELECT bo.order_id \"orderId\",    "
			+ "        bo.shop_cd \"shopCode\" ,    "
			+ "        bo.order_status \"orderStatus\",    "
			+ "        bo.ocid \"ocid\",    "
			+ "        TO_CHAR(bo.app_date, 'DD/MM/YYYY') \"applnDate\",    "
			+ "        bo.ERR_MSG \"errorMessage\",    "
			+ "        bo.lob \"orderSumLob\",    "
			+ "        DECODE(BO.LOB, 'MOB', BO.MSISDN, BC.SERVICE_NUM) \"orderSumServiceNum\",   "
			+ "        boi.login_id \"imsLoginId\",    "
			+ "        bo.reason_cd \"reasonCd\",   "
			+ "	       bo.check_point \"checkPoint\",		 "
			+ "        BC.LAST_NAME ||' ' ||BC.FIRST_NAME \"orderSumCustName\",    "
			+ "        TO_CHAR(bo.bom_creation_date, 'DD/MM/YYYY') \"bomCreationDate\",  "
			+ "        bo.dis_mode \"disMode\",  "
			+ "        bo.collect_method \"collectMethod\",  "
			+ "        bo.dms_ind \"dmsInd\" "
			+ "   FROM bomweb_order bo,  "
			+ "        bomweb_customer bc,    "
			+ "        bomweb_order_ims boi    "
			+ "  WHERE bo.order_id MEMBER OF ?    "
			+ "    AND bo.ORDER_ID   = bc.ORDER_ID    "
			+ "    AND bo.ORDER_ID   = boi.ORDER_ID(+)    "
			+ "    AND bo.lob = 'IMS'  "
			+ "  ORDER BY bo.order_id  ";

	public AlertOrderDTO[] getOrderListWithOutstandingWq(String[] pSbIds, String pSrvType)
			throws DAOException {
		AlertOrderDTO[] orderList = null;

		try {
			String sql = null;
			if (StringUtils.equals(pSrvType, LtsConstant.ALERT_MSG_SRV_TYPE_TEL)){
				sql = SQL_GET_ORDER_INFO_LTS;
			} else if (StringUtils.equals(pSrvType, LtsConstant.ALERT_MSG_SRV_TYPE_IMS)) {
				sql = SQL_GET_ORDER_INFO_IMS;
			}
			orderList = OracleSelectHelper.getSqlResultObjects("BomWebPortalDS", 
					sql, new Object[] { new OraArrayVarChar2("OPS$CNM.TABLE_VARCHAR2", pSbIds)}, AlertOrderDTO.class);

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