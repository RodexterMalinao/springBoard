package com.bomwebportal.mob.dao.bom;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import com.bomwebportal.dto.ActualUserDTO;
import com.bomwebportal.dto.AccountDTO;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.util.Utility;

public class BomOrderDAO extends BaseDAO {
	protected final Log logger = LogFactory.getLog(getClass());

	private static String getBomOrderStatusSQL = "select DECODE(SO.STATUS,\n" + "              '01',\n" + "              'Pending',\n" + "              '02',\n" + "              'Completed',\n" + "              '03',\n" + "              'Cancelling',\n" + "              '04',\n" + "              'Cancelled',\n" + "              'Others') as STATUS\n" + "  from B_SRV_ORD SO\n" + " where OC_ID = ?";

	public String getBomOrderStaus(String ocid) throws DAOException {
		String bomOrderStatus = "";

		try {

			bomOrderStatus = (String) simpleJdbcTemplate.queryForObject(getBomOrderStatusSQL, String.class, ocid);

		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException");
			bomOrderStatus = "Either OCID not found or BOM house-kept.";
		} catch (Exception e) {
			logger.error("Exception caught in getBomOrderStaus()", e);
			throw new DAOException(e.getMessage(), e);
		}
		return bomOrderStatus;

	}

	private static String checkBomPendingOrderSQL = "select 'CHECK MRT:' || B.SRV_ID || ' have pending order in BOM (OCID:' ||\n" + "       A.OC_ID || ' / Shop code:' || A.SHOP_CODE || ')' MESSAGE\n" + "  from B_SRV_ORD A, B_ORD_SRV B\n" + " where A.OC_ID = B.OC_ID\n" + "   and A.STATUS = '01'\n" + "   and A.BUS_TXN_TYPE in ('ACT', 'CMN', 'TOO1', 'TOO2')\n" + "   and B.SRV_ID = ?\n" + "   and B.SRV_ID_TYPE = 'S'\n" + "   and ROWNUM = 1";

	public String checkBomPendingOrder(String mobileNumber) throws DAOException {
		String checkBomPendingOrderMessage = "";

		try {

			checkBomPendingOrderMessage = (String) simpleJdbcTemplate.queryForObject(checkBomPendingOrderSQL, String.class, mobileNumber);

		} catch (EmptyResultDataAccessException erdae) {
			logger.info("checkBomPendingOrder");
			checkBomPendingOrderMessage = "";
		} catch (Exception e) {
			logger.error("Exception caught in checkBomPendingOrder()", e);
			throw new DAOException(e.getMessage(), e);
		}
		return checkBomPendingOrderMessage;

	}

	private static String getBomServiceIdSql = "select SERVICE_ID from B_SERVICE \n" + " where SRV_NUM = :msisdn and SYSTEM_ID = 'MOB' and eff_end_date is null";

	public String getBomServiceId(String msisdn) throws DAOException {

		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("msisdn", msisdn);

		try {
			logger.debug("getServiceId() @ SubscriberDAO:" + getBomServiceIdSql);
			String serviceId = (String) simpleJdbcTemplate.queryForObject(getBomServiceIdSql, String.class, msisdn);
			if (serviceId != null && serviceId.length() > 0) {
				return serviceId;
			} else {
				return null;
			}
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException in getBomServiceId()");
		} catch (Exception e) {
			logger.info("Exception caught in getBomServiceId():", e);
			throw new DAOException(e.getMessage(), e);
		}
		return null;
	}

	/* copy! */
	public AccountDTO getAccountInfo(String srvNum, String idDocType, String idDocNum, String brand) throws DAOException {
		logger.info("getAccountInfo() is called");
		List<AccountDTO> accountInfoList = new ArrayList<AccountDTO>();

		String SQL = "select " + "      a.acct_name\n" + " 	   ,a.acct_num\n" + "      ,a.bill_lang\n" + "      ,a.bill_period\n" + "      ,a.email_addr\n" + "      ,a.bill_freq\n" + "      ,adm.sms_num\n" + "      ,s.srv_num\n" + "      ,c.id_doc_type\n" + "      ,c.id_doc_num\n" + "      ,c.cust_num        mob_cust_num\n" + "      ,c.parent_cust_num bom_cust_num\n" + "from b_customer              c\n" + " ,b_account               a\n" + ",b_account_details_mob   adm\n" + ",b_service               s\n" + ",b_account_service_assgn asa\n" +

		"where c.system_id = 'MOB'\n" + "and c.id_doc_type = :v_doc_type \n" + "  and c.id_doc_num = :v_doc_num \n" + " and c.cust_num = a.cust_num\n" + "and s.system_id = 'MOB'\n" + "and S. srv_num = :v_srv_num\n" + "and s.eff_end_date is null\n" + "and s.service_id = asa.service_id\n" + "and asa.system_id = 'MOB'\n" + "and asa.eff_end_date is null\n" + "and asa.acct_num = a.acct_num\n" + "and a.system_id = 'MOB'\n" + "and a.acct_num = adm.acct_num\n" + "and a.eff_end_date is null\n" + "and adm.brand = :v_brand \n";/*-- brand of order*/

		ParameterizedRowMapper<AccountDTO> mapper = new ParameterizedRowMapper<AccountDTO>() {
			public AccountDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				AccountDTO accountInfoList = new AccountDTO();

				accountInfoList.setAcctName(rs.getString("ACCT_NAME"));
				accountInfoList.setAcctNum(rs.getString("ACCT_NUM"));
				accountInfoList.setBillLang(rs.getString("BILL_LANG"));
				accountInfoList.setBillPeriod(rs.getString("BILL_PERIOD"));
				accountInfoList.setEmailAddr(rs.getString("EMAIL_ADDR"));
				accountInfoList.setBillFreq(rs.getString("BILL_FREQ"));
				accountInfoList.setSmsNum(rs.getString("SMS_NUM"));
				accountInfoList.setSrvNum(rs.getString("SRV_NUM"));
				accountInfoList.setIdDocNum(rs.getString("ID_DOC_NUM"));
				accountInfoList.setIdDocType(rs.getString("ID_DOC_TYPE"));
				accountInfoList.setMobCustNum(rs.getString("MOB_CUST_NUM"));
				accountInfoList.setBomCustNum(rs.getString("BOM_CUST_NUM"));

				return accountInfoList;
			}
		};

		try {
			logger.debug("getAccountInfo() @ CustomerProfileDAO: " + SQL);
			logger.info("getActualUser() @ CustomerProfileDAO: ");
			// input field
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("v_srv_num", srvNum);
			params.addValue("v_doc_num", idDocNum);
			params.addValue("v_doc_type", idDocType);
			params.addValue("v_brand", brand);

			accountInfoList = simpleJdbcTemplate.query(SQL, mapper, params);

			if (accountInfoList == null || accountInfoList.isEmpty()) {
				return null;
			}

		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException");

			accountInfoList = null;
		} catch (Exception e) {
			logger.info("Exception caught in getAccountInfo():", e);
			throw new DAOException(e.getMessage(), e);
		}

		return accountInfoList.get(0);// only return the first one
	}

	public List<AccountDTO> getBarredFraud(String idDocType, String idDocNum) throws DAOException {
		logger.info("getBarredFraud() is called");
		List<AccountDTO> accountInfoList = new ArrayList<AccountDTO>();
		
		String SQL = "SELECT F.CUST_NUM, " +
				"  F.ACCT_NUM, " +
				"  F.SRV_NUM, " +
				"  F.FRAUD_MSG " +
				"FROM B_FRAUD_STATUS_MOB F, " +
				"  B_CUSTOMER C " +
				"WHERE C.CUST_NUM  = F.CUST_NUM " +
				"AND C.SYSTEM_ID   = 'MOB' " +
				"AND C.ID_DOC_TYPE = :v_doc_type " +
				"AND C.ID_DOC_NUM  = :v_doc_num " +
				"AND F.VALID_IND   = 'Y' " +
				"ORDER BY F.LAST_UPD_DATE";
		
		ParameterizedRowMapper<AccountDTO> mapper = new ParameterizedRowMapper<AccountDTO>() {
			public AccountDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				AccountDTO accountInfoList = new AccountDTO();

				accountInfoList.setMobCustNum(rs.getString("CUST_NUM"));
				accountInfoList.setAcctNum(rs.getString("ACCT_NUM"));
				accountInfoList.setSrvNum(rs.getString("SRV_NUM"));
				accountInfoList.setFraudMsg(rs.getString("FRAUD_MSG"));

				return accountInfoList;
			}
		};

		try {
			accountInfoList = simpleJdbcTemplate.query(SQL, mapper, idDocType, idDocNum);

			if ( CollectionUtils.isEmpty(accountInfoList) ) {
				logger.info("accountInfoList is null");
				return null;
			}
			
		}

		catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException");

		} catch (Exception e) {
			logger.info("Exception caught in getBarredFraud():", e);
			throw new DAOException(e.getMessage(), e);
		}

		return accountInfoList;
	}
}
