package com.bomltsportal.dao.email;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.bomltsportal.dto.email.OrderDTO;
import com.bomltsportal.dto.email.OrderDTO.CollectMethod;
import com.bomltsportal.dto.email.OrderDTO.DisMode;
import com.bomltsportal.dto.email.OrderDTO.EsigEmailLang;
import com.bomwebportal.dao.BaseDAO;
import com.bomwebportal.exception.DAOException;

public class OrderDAO extends BaseDAO {

	protected final Log logger = LogFactory.getLog(getClass());

	public OrderDTO getOrder(String orderId) throws DAOException {
		logger.info("getOrder() is called");

		List<OrderDTO> orderList = new ArrayList<OrderDTO>();

		String SQL="select O.ORDER_ID,\n" +
		"       O.OCID,\n" + 
		"       O.SRC,\n" + 
		"       O.ORDER_TYPE,\n" + 
		"       O.MSISDN,\n" + 
		"       O.MSISDNLVL,\n" + 
		"       O.MNP_IND,\n" + 
		"       O.SHOP_CD,\n" + 
		"       O.BOM_CUST_NO,\n" + 
		"       O.MOB_CUST_NO,\n" + 
		"       O.ACCT_NO,\n" + 
		"       O.SRV_REQ_DATE,\n" + 
		"       O.AGREE_NUM,\n" + 
		"       O.APP_DATE,\n" + 
		"       O.SALES_TYPE,\n" + 
		"       O.SALES_CD,\n" + 
		"       O.DEP_WAIVE,\n" + 
		"       O.ON_HOLD_IND,\n" + 
		"       O.ON_HOLD_REA_CD,\n" + 
		"       O.IMEI,\n" + 
		"       O.WARR_START_DATE,\n" + 
		"       O.WARR_PERIOD,\n" + 
		"       O.ORDER_STATUS,\n" + 
		"       O.CREATE_DATE,\n" + 
		"       O.SALES_NAME,\n" + 
		"       O.AO_IND,\n" + 
		"       O.LAST_UPDATE_BY,\n" + 
		"       O.LOB,\n" + 
		"       O.CHECK_POINT,\n" + 
		"       O.REASON_CD,\n" + 
		"       O.CLONE_ORDER_ID,\n" + 
		"       O.BOM_CREATION_DATE,\n" + 
		"       (select DECODE(count(*), 0, 'N', 'Y')\n" + 
		"          from BOMWEB_PAYMENT_TRANS PT\n" + 
		"         where PT.ORDER_ID = O.ORDER_ID\n" + 
		"           and PT.TRANS_STATUS = 'SETTLED'\n" + 
		"           and PT.PAY_MTD_TYPE in ('C', 'I')) TRX_IND,\n" + 
		"       O.DIS_MODE,\n" + 
		"       O.COLLECT_METHOD,\n" + 
		"       O.ESIG_EMAIL_ADDR,\n" + 
		"       O.ESIG_EMAIL_LANG,\n" + 
		"       O.DMS_IND,\n" + 
		"       O.BOM_CREATION_DATE,\n" + 
		"       M.IGUARD_SN\n" + 
		"  from BOMWEB_ORDER O, BOMWEB_ORDER_MOB M\n" + 
		" where O.ORDER_ID = M.ORDER_ID(+)\n" + 
		"   and O.ORDER_ID = ?";




		ParameterizedRowMapper<OrderDTO> mapper = new ParameterizedRowMapper<OrderDTO>() {
			public OrderDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				OrderDTO dto = new OrderDTO();

				dto.setOrderId(rs.getString("ORDER_ID"));
				dto.setOcid(rs.getString("OCID"));
				dto.setSource(rs.getString("SRC"));
				dto.setBusTxnType(rs.getString("ORDER_TYPE"));
				dto.setMsisdn(rs.getString("MSISDN"));
				dto.setMsisdnLvl(rs.getString("MSISDNLVL"));
				dto.setMnpInd(rs.getString("MNP_IND"));
				dto.setShopCode(rs.getString("SHOP_CD"));
				dto.setBomCustNum(rs.getString("BOM_CUST_NO"));
				dto.setMobCustNum(rs.getString("MOB_CUST_NO"));
				dto.setAcctNum(rs.getString("ACCT_NO"));
				dto.setSrvReqDate(rs.getTimestamp("SRV_REQ_DATE"));
				dto.setAgreementNum(rs.getString("AGREE_NUM"));
				dto.setAppInDate(rs.getTimestamp("APP_DATE"));
				dto.setSalesType(rs.getString("SALES_TYPE"));
				dto.setSalesCd(rs.getString("SALES_CD"));
				dto.setDepositWaiveInd(rs.getString("DEP_WAIVE"));
				dto.setOnHoldInd(rs.getString("ON_HOLD_IND"));
				dto.setOnHoldReaCd(rs.getString("ON_HOLD_REA_CD"));
				dto.setImei(rs.getString("IMEI"));
				dto.setWarrantyStartDate(rs.getString("WARR_START_DATE"));
				dto.setWarrantPeriod(rs.getString("WARR_PERIOD"));
				dto.setOrderStatus(rs.getString("ORDER_STATUS"));
				// add by eliot 20110627
				dto.setSalesName(rs.getString("SALES_NAME"));
				// add by herbert 20110707
				dto.setAoInd(rs.getString("AO_IND"));
				// add by eliot 20110829
				dto.setLastUpdateBy(rs.getString("LAST_UPDATE_BY"));
				dto.setOrderSumLob(rs.getString("LOB"));
				// add by erichui 20120313
				dto.setCheckPoint(rs.getString("CHECK_POINT"));
				dto.setCreateDate(rs.getTimestamp("create_date"));
				// add by erichui 20120405
				dto.setReasonCd(rs.getString("REASON_CD"));
				dto.setCloneOrderId(rs.getString("CLONE_ORDER_ID"));
				//dto.setHistSeqNo(rs.getString("MAX_SEQ_NO"));
				//max = (a > b) ? a : b;
				//dto.setCreditCardTrxInd(!"0".equals(rs.getString("MAX_SEQ_NO"))? "Y":"N");
				dto.setCreditCardTrxInd(rs.getString("TRX_IND"));
				/*if (!"0".equals(rs.getString("MAX_SEQ_NO"))){
					dto.setCreditCardTrxInd("Y");
				}else{
					dto.setCreditCardTrxInd("N");
				}*/
				String disMode = rs.getString("DIS_MODE"); 
				if (disMode instanceof String) {
					dto.setDisMode(DisMode.valueOf(disMode));
				}
				String collectMethod = rs.getString("COLLECT_METHOD");
				if (collectMethod instanceof String) {
					dto.setCollectMethod(CollectMethod.valueOf(collectMethod));
				}
				// ESIG_EMAIL_LANG can be null in Paper disMode
				dto.setEsigEmailAddr(rs.getString("ESIG_EMAIL_ADDR"));
				String esigEmailLang = rs.getString("ESIG_EMAIL_LANG");
				if (esigEmailLang instanceof String) {
					dto.setEsigEmailLang(EsigEmailLang.valueOf(esigEmailLang));
				}
				
				dto.setDmsInd(rs.getString("DMS_IND"));
				dto.setBomCreationDate(rs.getTimestamp("BOM_CREATION_DATE"));//add by wilson 20120830
				dto.setiGuardSerialNo(rs.getString("IGUARD_SN")); //add by wilson 2012
				
				return dto;
			}
		};

		try {
			//herbert 20111110 - remove useless SQL logger
			logger.info("getOrder() @ OrderDAO: " );
			logger.debug("getOrder() @ OrderDAO: " + SQL);

			orderList = simpleJdbcTemplate.query(SQL, mapper, orderId);

		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException");

			orderList = null;
		} catch (Exception e) {
			logger.info("Exception caught in getOrder():", e);

			throw new DAOException(e.getMessage(), e);
		}

		if (orderList == null || orderList.size() == 0) {
			return null;
		} else {
			return orderList.get(0);
		}

	}
	

	public int updateEsigEmailAddr(String orderId, String esigEmailAddr, String lastUpdateBy) throws DAOException {
		String sql = "update bomweb_order" +
				" set esig_email_addr = :esigEmailAddr" +
				" , LAST_UPDATE_BY = :lastUpdateBy" +
				" , LAST_UPDATE_DATE = sysdate" +
				" where ORDER_ID = :orderId" +
				" and DIS_MODE = :disMode";
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("esigEmailAddr", esigEmailAddr);
		params.addValue("lastUpdateBy", lastUpdateBy);
		params.addValue("orderId", orderId);
		params.addValue("disMode", DisMode.E.toString());
		try {
			return this.simpleJdbcTemplate.update(sql, params);
		} catch (Exception e) {
			logger.error("Exception caught in updateEsigEmailAddr()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
}

