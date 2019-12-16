package com.bomwebportal.lts.theclub.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.bomwebportal.dao.BaseDAO;
/**
 * This class connects to BomDS and retrieves notification details. 
 * This class intenses to replace the old SMS sending task on BOM batch job
 * as Oracle 9i does not support SHA-256 certificate, and job sending SMS out using
 * SP on Oracle 9i failed.
 * 
 * This class mainly run SQL, and the logic of sending SMS resides on controller. 
 * 
 * @author 01517028
 *
 */
import com.bomwebportal.dto.AllDocDTO;
import com.bomwebportal.dto.AllDocDTO.DocType;
import com.bomwebportal.dto.AllDocDTO.Type;
import com.bomwebportal.lts.theclub.dto.CustomerDocInfoDTO;
import com.bomwebportal.lts.theclub.dto.BLtsTheClubTxnDTO;
import com.bomwebportal.lts.theclub.dto.BLtsTheClubTxnLogDTO;
import com.bomwebportal.lts.theclub.service.LtsTheClubPointConstant;

public class LtsTheClubTxnDAO extends BaseDAO implements LtsTheClubPointConstant {
	private static final Integer TXN_ID_IDX = 0, CUST_NUM_IDX = 1, MEMBER_ID_IDX = 2, OC_ID_IDX = 3, ITEM_ID_IDX = 4,
			OFFER_ID_IDX = 5, PSEF_CD_IDX = 6, TRANS_ID_IDX = 7, REQUEST_STR_IDX = 8, RESPONSE_STR_IDX = 9,
			CLUB_POINTS_IDX = 10, TRANS_STATUS_IDX = 11, CREATE_DATE_IDX = 12, LAST_UPD_DATE_IDX = 13,
			ID_DOC_NUM_IDX = 14, ID_DOC_TYPE_IDX = 15, AGREEMENT_NUM_IDX = 16, THE_CLUB_ORDER_ID_IDX = 17,
			DTL_ID_IDX = 18, DTL_SEQ_IDX = 19, TXN_LOG_ID_IDX = 20, RETRY_CNT_IDX = 21, RETRY_LMT_IDX = 22,
			ACTION_IDX = 23, RTN_CODE_IDX = 24, RTN_MSG_IDX = 25, CHANNEL_IDX = 26, PACKAGE_CODE_IDX = 27, REVERSE_TRANS_ID_IDX=28;
	private static final String TXN_TABLE_NAME = "B_LTS_THE_CLUB_TXN";
	private static final String TXN_LOG_TABLE_NAME = "B_LTS_THE_CLUB_TXN_LOG";
	private static final String[] COLUMN_NAMES = { "TXN_ID", "CUST_NUM", "MEMBER_ID", "OC_ID", "ITEM_ID", "OFFER_ID",
			"PSEF_CD", "TRANS_ID", "REQUEST_STR", "RESPONSE_STR", "CLUB_POINTS", "TRANS_STATUS", "CREATE_DATE",
			"LAST_UPD_DATE", "ID_DOC_NUM", "ID_DOC_TYPE", "AGREEMENT_NUM", "THE_CLUB_ORDER_ID", "DTL_ID", "DTL_SEQ",
			"TXN_LOG_ID", "RETRY_CNT", "RETRY_LMT", "ACTION", "RTN_CODE", "RTN_MSG", "CHANNEL", "PACKAGE_CODE", "REVERSE_TRANS_ID" };

	private static final String TXN_SELECT_ALL_COLUMN_NAME_STR;
	private static final String TXN_LOG_SELECT_ALL_COLUMN_NAME_STR;
	private static final String TXN_INSERT_ALL_COLUMN_NAME_STR;
	private static final String TXN_LOG_INSERT_ALL_COLUMN_NAME_STR;
	private static final String TXN_INSERT_ALL_VALUE_STR;
	private static final String TXN_LOG_INSERT_ALL_VALUE_STR;
	static {
		TreeMap<Integer, Object> txnColNames = new TreeMap<Integer, Object>();
		txnColNames.put(THE_CLUB_ORDER_ID_IDX, COLUMN_NAMES[THE_CLUB_ORDER_ID_IDX]);
		txnColNames.put(CUST_NUM_IDX, COLUMN_NAMES[CUST_NUM_IDX]);
		txnColNames.put(MEMBER_ID_IDX, COLUMN_NAMES[MEMBER_ID_IDX]);
		txnColNames.put(OC_ID_IDX, COLUMN_NAMES[OC_ID_IDX]);
		txnColNames.put(DTL_ID_IDX, COLUMN_NAMES[DTL_ID_IDX]);
		txnColNames.put(DTL_SEQ_IDX, COLUMN_NAMES[DTL_SEQ_IDX]);
		txnColNames.put(AGREEMENT_NUM_IDX, COLUMN_NAMES[AGREEMENT_NUM_IDX]);
		txnColNames.put(ID_DOC_NUM_IDX, COLUMN_NAMES[ID_DOC_NUM_IDX]);
		txnColNames.put(ID_DOC_TYPE_IDX, COLUMN_NAMES[ID_DOC_TYPE_IDX]);
		txnColNames.put(ITEM_ID_IDX, COLUMN_NAMES[ITEM_ID_IDX]);
		txnColNames.put(OFFER_ID_IDX, COLUMN_NAMES[OFFER_ID_IDX]);
		txnColNames.put(PSEF_CD_IDX, COLUMN_NAMES[PSEF_CD_IDX]);
		txnColNames.put(TRANS_ID_IDX, COLUMN_NAMES[TRANS_ID_IDX]);
		txnColNames.put(CLUB_POINTS_IDX, COLUMN_NAMES[CLUB_POINTS_IDX]);
		txnColNames.put(TRANS_STATUS_IDX, COLUMN_NAMES[TRANS_STATUS_IDX]);
		txnColNames.put(CREATE_DATE_IDX, COLUMN_NAMES[CREATE_DATE_IDX]);
		txnColNames.put(LAST_UPD_DATE_IDX, COLUMN_NAMES[LAST_UPD_DATE_IDX]);
		txnColNames.put(TXN_ID_IDX, COLUMN_NAMES[TXN_ID_IDX]);
		txnColNames.put(RETRY_CNT_IDX, COLUMN_NAMES[RETRY_CNT_IDX]);
		txnColNames.put(RETRY_LMT_IDX, COLUMN_NAMES[RETRY_LMT_IDX]);
		txnColNames.put(ACTION_IDX, COLUMN_NAMES[ACTION_IDX]);
		txnColNames.put(RTN_CODE_IDX, COLUMN_NAMES[RTN_CODE_IDX]);
		txnColNames.put(RTN_MSG_IDX, COLUMN_NAMES[RTN_MSG_IDX]);
		txnColNames.put(CHANNEL_IDX, COLUMN_NAMES[CHANNEL_IDX]);
		txnColNames.put(PACKAGE_CODE_IDX, COLUMN_NAMES[PACKAGE_CODE_IDX]);
		txnColNames.put(REVERSE_TRANS_ID_IDX, COLUMN_NAMES[REVERSE_TRANS_ID_IDX]);
		TXN_INSERT_ALL_COLUMN_NAME_STR = convertToColumnName(txnColNames);
		TXN_SELECT_ALL_COLUMN_NAME_STR = convertToColumnName(txnColNames);
		TreeMap<Integer, Object> txnValues = new TreeMap<Integer, Object>();

		txnValues.put(THE_CLUB_ORDER_ID_IDX, "?");
		txnValues.put(CUST_NUM_IDX, "?");
		txnValues.put(MEMBER_ID_IDX, "?");
		txnValues.put(OC_ID_IDX, "?");
		txnValues.put(DTL_ID_IDX, "?");

		txnValues.put(DTL_SEQ_IDX, "?");
		txnValues.put(AGREEMENT_NUM_IDX, "?");
		txnValues.put(ID_DOC_NUM_IDX, "?");
		txnValues.put(ID_DOC_TYPE_IDX, "?");
		txnValues.put(ITEM_ID_IDX, "?");

		txnValues.put(OFFER_ID_IDX, "?");
		txnValues.put(PSEF_CD_IDX, "?");
		txnValues.put(TRANS_ID_IDX, "?");
		txnValues.put(CLUB_POINTS_IDX, "?");
		txnValues.put(TRANS_STATUS_IDX, "?");

		txnValues.put(CREATE_DATE_IDX, "SYSDATE");
		txnValues.put(LAST_UPD_DATE_IDX, "SYSDATE");
		txnValues.put(TXN_ID_IDX, "?");
		txnValues.put(RETRY_CNT_IDX, "?");
		txnValues.put(RETRY_LMT_IDX, "?");

		txnValues.put(ACTION_IDX, "?");
		txnValues.put(RTN_CODE_IDX, "?");
		txnValues.put(RTN_MSG_IDX, "?");
		txnValues.put(CHANNEL_IDX, "?");
		txnValues.put(PACKAGE_CODE_IDX, "?");
		
		txnValues.put(REVERSE_TRANS_ID_IDX, "?");
		TXN_INSERT_ALL_VALUE_STR = convertToColumnName(txnValues);

		TreeMap<Integer, Object> txnLogColNames = new TreeMap<Integer, Object>();
		txnLogColNames.put(TXN_LOG_ID_IDX, COLUMN_NAMES[TXN_LOG_ID_IDX]);
		txnLogColNames.put(TXN_ID_IDX, COLUMN_NAMES[TXN_ID_IDX]);
		txnLogColNames.put(REQUEST_STR_IDX, COLUMN_NAMES[REQUEST_STR_IDX]);
		txnLogColNames.put(RESPONSE_STR_IDX, COLUMN_NAMES[RESPONSE_STR_IDX]);
		txnLogColNames.put(CREATE_DATE_IDX, COLUMN_NAMES[CREATE_DATE_IDX]);
		txnLogColNames.put(LAST_UPD_DATE_IDX, COLUMN_NAMES[LAST_UPD_DATE_IDX]);
		txnLogColNames.put(RTN_CODE_IDX, COLUMN_NAMES[RTN_CODE_IDX]);
		txnLogColNames.put(RTN_MSG_IDX, COLUMN_NAMES[RTN_MSG_IDX]);

		TXN_LOG_SELECT_ALL_COLUMN_NAME_STR = convertToColumnName(txnLogColNames);
		TXN_LOG_INSERT_ALL_COLUMN_NAME_STR = convertToColumnName(txnLogColNames);
		TreeMap<Integer, Object> txnLogValueNames = new TreeMap<Integer, Object>();
		txnLogValueNames.put(TXN_LOG_ID_IDX, "?");
		txnLogValueNames.put(TXN_ID_IDX, "?");
		txnLogValueNames.put(REQUEST_STR_IDX, "?");
		txnLogValueNames.put(RESPONSE_STR_IDX, "?");
		txnLogValueNames.put(CREATE_DATE_IDX, "SYSDATE");
		txnLogValueNames.put(LAST_UPD_DATE_IDX, "SYSDATE");
		txnLogValueNames.put(RTN_CODE_IDX, "?");
		txnLogValueNames.put(RTN_MSG_IDX, "?");

		TXN_LOG_INSERT_ALL_VALUE_STR = convertToColumnName(txnLogValueNames);
	}
	private static final String TXN_SQL_SELECT_ALL = "SELECT " + TXN_SELECT_ALL_COLUMN_NAME_STR + " FROM "
			+ TXN_TABLE_NAME + " ";

	private static final String TXN_LOG_SQL_SELECT_ALL = "SELECT " + TXN_LOG_SELECT_ALL_COLUMN_NAME_STR + "FROM "
			+ TXN_LOG_TABLE_NAME + " ";

	private static final String SQL_SELECT_TXN_BY_STATUS = TXN_SQL_SELECT_ALL + " WHERE "
			+ COLUMN_NAMES[TRANS_STATUS_IDX] + "= ? ";

	private static final String TXN_SQL_UPDATE_STATUS_RETRY_BY_TXN_ID = "UPDATE " + TXN_TABLE_NAME + " SET "
			+ COLUMN_NAMES[TRANS_STATUS_IDX] + "=?, " + COLUMN_NAMES[TRANS_ID_IDX] + "=? , "
			+ COLUMN_NAMES[RETRY_CNT_IDX] + "=? ," + COLUMN_NAMES[RTN_CODE_IDX] + "=? , " + COLUMN_NAMES[RTN_MSG_IDX]
			+ "=? , " + " LAST_UPD_DATE=SYSDATE WHERE " + COLUMN_NAMES[TXN_ID_IDX] + "=?";

	private static final String TXN_SQL_UPDATE_STATUS_BY_TXN_ID = "UPDATE " + TXN_TABLE_NAME + " SET "
			+ COLUMN_NAMES[TRANS_STATUS_IDX] + "=?, " + "LAST_UPD_DATE=SYSDATE WHERE " + COLUMN_NAMES[TXN_ID_IDX]
			+ "=?";
	private static final String TXN_SQL_UPDATE_STATUS_TRANS_BY_TXN_ID = "UPDATE " + TXN_TABLE_NAME + " SET "
			+ COLUMN_NAMES[TRANS_STATUS_IDX] + "=?, " + COLUMN_NAMES[TRANS_ID_IDX] + "=? , "
			+ COLUMN_NAMES[RETRY_CNT_IDX] + "=? , " + "LAST_UPD_DATE=SYSDATE WHERE " + COLUMN_NAMES[TXN_ID_IDX] + "=?";
	private static final String TXN_LOG_SQL_UPDATE_RESPONSE_BY_TXN_ID = "UPDATE " + TXN_LOG_TABLE_NAME + " SET "
			+ COLUMN_NAMES[RESPONSE_STR_IDX] + "=?, " + COLUMN_NAMES[RTN_CODE_IDX] + "=?, " + COLUMN_NAMES[RTN_MSG_IDX]
			+ "=?, " + "LAST_UPD_DATE=SYSDATE WHERE " + COLUMN_NAMES[TXN_LOG_ID_IDX] + "=?";

	private static final String TXN_LOG_SQL_UPDATE_REP_BY_TXN_ID = "UPDATE " + TXN_LOG_TABLE_NAME + " SET "
			+ COLUMN_NAMES[RESPONSE_STR_IDX] + "=?, +" + COLUMN_NAMES[TRANS_ID_IDX]
			+ "=? , LAST_UPD_DATE=SYSDATE WHERE " + COLUMN_NAMES[TXN_LOG_ID_IDX] + "=?";
	// private static final String TXN_LOG_SQL_UPDATE_REQ_REQ_BY_TXN_ID =
	// "UPDATE "+TXN_LOG_TABLE_NAME+" SET "+COLUMN_NAMES[REQUEST_STR_IDX]+"=?,
	// "+COLUMN_NAMES[RESPONSE_STR_IDX]+"=? LAST_UPD_DATE=SYSDATE WHERE
	// "+COLUMN_NAMES[0]+"=?";

	private static final String TXN_SQL_INSERT = "INSERT INTO " + TXN_TABLE_NAME + " (" + TXN_INSERT_ALL_COLUMN_NAME_STR
			+ " ) VALUES ( " + TXN_INSERT_ALL_VALUE_STR + " ) ";

	private static final String TXN_LOG_SQL_INSERT = "INSERT INTO " + TXN_LOG_TABLE_NAME + " ("
			+ TXN_LOG_INSERT_ALL_COLUMN_NAME_STR + " ) VALUES ( " + TXN_LOG_INSERT_ALL_VALUE_STR + " ) ";

	private static final String TXN_SQL_SELECT_NEW_ID = "select B_LTS_THE_CLUB_TXN_SEQ.nextval from dual";
	private static final String TXN_LOG_SQL_SELECT_NEW_ID = "select B_LTS_THE_CLUB_TXN_LOG_SEQ.nextval from dual";

	private static final String SQL_SELECT_CUSTOMER_ID = " SELECT CUST_NUM, ID_DOC_NUM, ID_DOC_TYPE from B_CUSTOMER where CUST_NUM=? and SYSTEM_ID='DRG' ";

	private static final String SQL_SELECT_ORDER_WITH_CLUB_POINT = " SELECT bsoo.OC_ID , "
			+ " bsoo.ITEM_ID     ITEM_ID, " + " bsoo.OFFER_ID    OFFER_ID, " + " bocp.PSEF_CD     PSEF_CD, "
			+ " bc.CUST_NUM      CUST_NUM, " + " bc.ID_DOC_NUM    ID_DOC_NUM, " + " bc.ID_DOC_TYPE   ID_DOC_TYPE, "
			+ " bl.LTS_CODE CLUB_POINTS " + " FROM b_ord_latest_status bols" + " INNER JOIN b_srv_ord_offer bsoo "
			+ " ON "// -- join the get offer detail of the order
			+ " bols.OC_ID=bsoo.OC_ID " + " INNER JOIN b_srv_ord_prod bsop " + " ON "// --
																						// join
																						// to
																						// get
																						// product
																						// detail
																						// of
																						// the
																						// offer
			+ " bsoo.OC_ID    =bsop.OC_ID " + " AND bsoo.ITEM_ID=bsop.ITEM_ID "
			+ " INNER JOIN b_ord_component_psef bocp " + " ON "// -- join to get
																// psef code of
																// the component
			+ " bsoo.OC_ID    =bocp.OC_ID " + " AND bsoo.ITEM_ID=bocp.ITEM_ID " + " INNER JOIN b_srv_ord bso "
			+ " ON bso.oc_id = bsoo.oc_id " + " INNER JOIN b_customer bc " + " ON "// --
																					// join
																					// b_customer
																					// table
																					// to
																					// get
																					// customer's
																					// detail
			+ " bso.CUST_NUM                      =bc.CUST_NUM " + " INNER JOIN B_LOOKUP bl " + "    ON "
			+ "    bl.BOM_CODE=bocp.PSEF_CD " + " AND bl.BOM_GRP_ID  ='LTS_CLUB_POINT' "
			+ " WHERE NVL(bsoo.MARK_DEL_IND,'N') <> 'Y' " + " AND NVL(bsop.MARK_DEL_IND,'N')     <> 'Y' "
			+ " AND NVL(bocp.MARK_DEL_IND,'N')     <> 'Y' " + " AND bols.status                     ='D' ";
	private static final String TXN_SQL_SELECT_EXISTING_TXN = "SELECT "
			// + " TXN_ID, THE_CLUB_ORDER_ID, CUST_NUM, MEMBER_ID, OC_ID,
			// DTL_ID, DTL_SEQ, AGREEMENT_NUM, ID_DOC_NUM, ID_DOC_TYPE, ITEM_ID,
			// OFFER_ID, PSEF_CD, TRANS_ID, CLUB_POINTS, TRANS_STATUS,
			// CREATE_DATE, LAST_UPD_DATE "
			+ TXN_SELECT_ALL_COLUMN_NAME_STR + " FROM B_LTS_THE_CLUB_TXN bltct "
//			+ " where (bltct.THE_CLUB_ORDER_ID=? or bltct.AGREEMENT_NUM=?) and bltct.PACKAGE_CODE=? and bltct.ACTION=? "
			+ " where bltct.THE_CLUB_ORDER_ID=? and bltct.PACKAGE_CODE=? and bltct.ACTION=? "
			+ " AND (rtn_code NOT IN ( SELECT bom_code FROM b_lookup WHERE bom_grp_id = 'CLUB_PT_NO_RETRY' and bom_status = 'A' ) OR rtn_code IS NULL) ";
	// THE_CLUB_ORDER_ID+PSEF_CD to identify a club point
	// transaction,THE_CLUB_ORDER_ID either=SB order id or OC_ID+DTL_ID+DTL_SEQ
	private static final String SQL_SELECT_BY_OC_ID_CLAUSE = " AND bsoo.OC_ID=? ";
	
	private static final String SQL_UPDATE_TXN_MEMBER_ID = " update "+TXN_TABLE_NAME+" SET "+COLUMN_NAMES[MEMBER_ID_IDX] +"=? WHERE "+COLUMN_NAMES[TXN_ID_IDX]+"=?";

	public List<BLtsTheClubTxnDTO> getOrderByOcId(Integer ocId) {
		Object[] args = new Object[1];
		args[0] = ocId;
		String sqlStr = SQL_SELECT_ORDER_WITH_CLUB_POINT + SQL_SELECT_BY_OC_ID_CLAUSE;
		List<BLtsTheClubTxnDTO> result = this.jdbcTemplate.query(sqlStr, args, getLtsTheClubTxnDTOMapper());
		return result;
	}

	/**
	 * 
	 * Retrieving records.
	 * 
	 * @param msgId
	 * @return
	 */

	public int getNewTxnId() {
		String sqlStr = TXN_SQL_SELECT_NEW_ID;
		int result = this.jdbcTemplate.queryForInt(sqlStr);
		return result;
	}

	public int getNewTxnLogId() {
		String sqlStr = TXN_LOG_SQL_SELECT_NEW_ID;
		int result = this.jdbcTemplate.queryForInt(sqlStr);
		return result;
	}

	public List<BLtsTheClubTxnDTO> getTxnByStatus(String status) {
		Object[] args = new Object[1];
		args[0] = status;
		String sqlStr = SQL_SELECT_TXN_BY_STATUS;
		List<BLtsTheClubTxnDTO> result = this.jdbcTemplate.query(sqlStr, args, getLtsTheClubTxnDTOMapper());
		return result;
	}

	public List<BLtsTheClubTxnDTO> getExistingTxn(String theClubOrderId, String packageCode,
			String action) {
		// Object[] args = new Object[4];
		// args[0] = theClubOrderId;
		// args[1] = agreementNum;
		// args[2] = psefCd;
		// args[3] = action;
		Object[] args = null;
		ArrayList<Object> argList = new ArrayList<Object>();
		argList.add(theClubOrderId);
//		argList.add(agreementNum);
		argList.add(packageCode);
		argList.add(action);
		args = argList.toArray();
		String sqlStr = TXN_SQL_SELECT_EXISTING_TXN;
		List<BLtsTheClubTxnDTO> result = this.jdbcTemplate.query(sqlStr, args, getLtsTheClubTxnDTOMapper());
		return result;
	}

	public int updateStatusByTxnId(String status, String transId, Integer retryCnt, String rtnCode, String rtnMsg,
			Integer txnId) {
		String sql = "UPDATE " + TXN_TABLE_NAME + " SET ";
		Object[] args = null;

		ArrayList<Object> argList = new ArrayList<Object>();
		if (status != null) {
			sql = sql + COLUMN_NAMES[TRANS_STATUS_IDX] + "=?, ";
			argList.add(status);
		}

		if (transId != null) {
			sql = sql + COLUMN_NAMES[TRANS_ID_IDX] + "=?, ";
			argList.add(transId);
		}

		if (retryCnt != null) {
			sql = sql + COLUMN_NAMES[RETRY_CNT_IDX] + "=?, ";
			argList.add(retryCnt);
		}

		if (rtnCode != null) {
			sql = sql + COLUMN_NAMES[RTN_CODE_IDX] + "=?, ";
			argList.add(rtnCode);
		}

		if (rtnMsg != null) {
			sql = sql + COLUMN_NAMES[RTN_MSG_IDX] + "=?, ";
			argList.add(rtnMsg);
		}
		argList.add(txnId);
		args = argList.toArray();
		sql = sql + "LAST_UPD_DATE=SYSDATE WHERE " + COLUMN_NAMES[TXN_ID_IDX] + "=?";

		int ret = this.jdbcTemplate.update(sql, args);
		return ret;
	}

	public int updateMemberIdByTxnId(String memberId, Integer txnId){
		int ret=0;
		if(memberId!=null && txnId != null){
			String sql = SQL_UPDATE_TXN_MEMBER_ID;
			Object[] args = null;
			ArrayList<Object> argList = new ArrayList<Object>();
			argList.add(memberId);
			argList.add(txnId);
			args = argList.toArray();
			ret = this.jdbcTemplate.update(sql, args);
		}
		return ret;
	}
	
	public int updateStatusByTxnLogId(String requestStr, String responseStr, String rtnCode, String rtnMsg,
			Integer txnLogId) {
		String sql = "UPDATE " + TXN_LOG_TABLE_NAME + " SET ";
		Object[] args = null;

		ArrayList<Object> argList = new ArrayList<Object>();

		if (requestStr != null) {
			sql = sql + COLUMN_NAMES[REQUEST_STR_IDX] + "=?, ";
			argList.add(requestStr);
		}

		if (responseStr != null) {
			sql = sql + COLUMN_NAMES[RESPONSE_STR_IDX] + "=?, ";
			argList.add(responseStr);
		}

		if (rtnCode != null) {
			sql = sql + COLUMN_NAMES[RTN_CODE_IDX] + "=?, ";
			argList.add(rtnCode);
		}

		if (rtnMsg != null) {
			sql = sql + COLUMN_NAMES[RTN_MSG_IDX] + "=?, ";
			argList.add(rtnMsg);
		}
		argList.add(txnLogId);
		args = argList.toArray();
		sql = sql + "LAST_UPD_DATE=SYSDATE WHERE " + COLUMN_NAMES[TXN_LOG_ID_IDX] + "=?";
		String sqlStr = TXN_LOG_SQL_UPDATE_RESPONSE_BY_TXN_ID;
		int ret = this.jdbcTemplate.update(sqlStr, args);
		return ret;
	}

	public int insertTxn(BLtsTheClubTxnDTO ltsTheClubTxnDTO) {

		TreeMap<Integer, Object> values = new TreeMap<Integer, Object>();
		// values.put(THE_CLUB_ORDER_ID_IDX,
		// ltsTheClubTxnDTO.getTheClubOrderId());
		// values.put(CUST_NUM_IDX, ltsTheClubTxnDTO.getCustNum());
		// values.put(MEMBER_ID_IDX, ltsTheClubTxnDTO.getMemberId());
		// values.put(OC_ID_IDX, ltsTheClubTxnDTO.getOcId());
		// values.put(DTL_ID_IDX, ltsTheClubTxnDTO.getDtlId());
		//
		// values.put(DTL_SEQ_IDX, ltsTheClubTxnDTO.getDtlSeq());
		// values.put(AGREEMENT_NUM_IDX, ltsTheClubTxnDTO.getAgreementNum());
		// values.put(ID_DOC_NUM_IDX, ltsTheClubTxnDTO.getIdDocNum());
		// values.put(ID_DOC_TYPE_IDX, ltsTheClubTxnDTO.getIdDocType());
		// values.put(ITEM_ID_IDX, ltsTheClubTxnDTO.getItemId());
		//
		// values.put(OFFER_ID_IDX, ltsTheClubTxnDTO.getOfferId());
		// values.put(PSEF_CD_IDX, ltsTheClubTxnDTO.getPsefCd());
		// values.put(TRANS_ID_IDX, ltsTheClubTxnDTO.getTransId());
		// values.put(CLUB_POINTS_IDX, ltsTheClubTxnDTO.getClubPoints());
		// values.put(TRANS_STATUS_IDX, ltsTheClubTxnDTO.getTransStatus());
		//
		// values.put(TXN_ID_IDX, ltsTheClubTxnDTO.getTransStatus());

		values.put(THE_CLUB_ORDER_ID_IDX, ltsTheClubTxnDTO.getTheClubOrderId());
		values.put(CUST_NUM_IDX, ltsTheClubTxnDTO.getCustNum());
		values.put(MEMBER_ID_IDX, ltsTheClubTxnDTO.getMemberId());
		values.put(OC_ID_IDX, ltsTheClubTxnDTO.getOcId());
		values.put(DTL_ID_IDX, ltsTheClubTxnDTO.getDtlId());

		values.put(DTL_SEQ_IDX, ltsTheClubTxnDTO.getDtlSeq());
		values.put(AGREEMENT_NUM_IDX, ltsTheClubTxnDTO.getAgreementNum());
		values.put(ID_DOC_NUM_IDX, ltsTheClubTxnDTO.getIdDocNum());
		values.put(ID_DOC_TYPE_IDX, ltsTheClubTxnDTO.getIdDocType());
		values.put(ITEM_ID_IDX, ltsTheClubTxnDTO.getItemId());

		values.put(OFFER_ID_IDX, ltsTheClubTxnDTO.getOfferId());
		values.put(PSEF_CD_IDX, ltsTheClubTxnDTO.getPsefCd());
		values.put(TRANS_ID_IDX, ltsTheClubTxnDTO.getTransId());
		values.put(CLUB_POINTS_IDX, ltsTheClubTxnDTO.getClubPoints());
		values.put(TRANS_STATUS_IDX, ltsTheClubTxnDTO.getTransStatus());

		values.put(TXN_ID_IDX, ltsTheClubTxnDTO.getTxnId());
		values.put(RETRY_CNT_IDX, ltsTheClubTxnDTO.getRetryCnt());
		values.put(RETRY_LMT_IDX, ltsTheClubTxnDTO.getRetryLmt());
		values.put(ACTION_IDX, ltsTheClubTxnDTO.getAction());
		values.put(RTN_CODE_IDX, ltsTheClubTxnDTO.getRtnCode());
		
		values.put(RTN_MSG_IDX, ltsTheClubTxnDTO.getRtnMsg());
		values.put(CHANNEL_IDX, ltsTheClubTxnDTO.getChannel());
		values.put(PACKAGE_CODE_IDX, ltsTheClubTxnDTO.getPackageCode());
		values.put(REVERSE_TRANS_ID_IDX, ltsTheClubTxnDTO.getReverseTransId());

		final Object[] args = mapToStringArray(values);
		final String sqlStr = TXN_SQL_INSERT;
		int ret = this.jdbcTemplate.update(sqlStr, args);

		return ret;
	}

	public int insertTxnLog(BLtsTheClubTxnLogDTO ltsTheClubTxnDTO) {
		Object[] args = new Object[1];

		TreeMap<Integer, Object> values = new TreeMap<Integer, Object>();
		values.put(TXN_LOG_ID_IDX, ltsTheClubTxnDTO.getTxnLogId());
		values.put(TXN_ID_IDX, ltsTheClubTxnDTO.getTxnId());
		values.put(REQUEST_STR_IDX, ltsTheClubTxnDTO.getRequestStr());
		values.put(RESPONSE_STR_IDX, ltsTheClubTxnDTO.getResponseStr());
		values.put(RTN_CODE_IDX, ltsTheClubTxnDTO.getRtnCode());
		values.put(RTN_MSG_IDX, ltsTheClubTxnDTO.getRtnMsg());
		
		args = mapToStringArray(values);
		String sqlStr = TXN_LOG_SQL_INSERT;

		int ret = this.jdbcTemplate.update(sqlStr, args);
		return ret;
	}

	public List<CustomerDocInfoDTO> getCustDocByCustNum(String custNum) {
		Object[] args = new Object[1];
		args[0] = custNum;
		String sqlStr = SQL_SELECT_CUSTOMER_ID;
		List<CustomerDocInfoDTO> result = this.jdbcTemplate.query(sqlStr, args, getCustomerDocInfoDTOMapper());
		return result;
	}

	private static ParameterizedRowMapper<CustomerDocInfoDTO> getCustomerDocInfoDTOMapper() {
		return new ParameterizedRowMapper<CustomerDocInfoDTO>() {

			public CustomerDocInfoDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				CustomerDocInfoDTO ret = new CustomerDocInfoDTO();
				String custNum = rs.getString("CUST_NUM");
				String idDocNum = rs.getString("ID_DOC_NUM");
				String idDocType = rs.getString("ID_DOC_TYPE");
				ret.setCustNum(custNum);
				ret.setIdDocNum(idDocNum);
				ret.setIdDocType(idDocType);
				return ret;
			}

		};
	}

	public static Object[] mapToStringArray(TreeMap<Integer, Object> treeMap) {
		Object[] ret = null;
		ret = treeMap.values().toArray();
		return ret;
	}

	public static String concatWithSeperator(Object[] values, String seperator) {
		if (values == null || values.length == 0) {
			return "";
		}
		StringBuilder sb = new StringBuilder();
		sb.append(values[0]);
		for (int i = 1; i < values.length; i++) {
			sb.append(seperator).append(values[i]);
		}
		return sb.toString();
	}

	public static String convertToColumnName(TreeMap<Integer, Object> treeMap) {
		String ret = null;
		Object[] names = mapToStringArray(treeMap);
		ret = concatWithSeperator(names, ",");
		return ret;
	}

	public static Integer rsGetInt(ResultSet rs, String key) {
		Integer ret = null;
		try {
			ret = rs.getInt(key);
		} catch (Exception e) {

		}
		return ret;
	}

	public static String rsGetString(ResultSet rs, String key) {
		String ret = null;
		try {
			ret = rs.getString(key);
		} catch (Exception e) {

		}
		return ret;
	}

	public static Date rsGetDate(ResultSet rs, String key) {
		Date ret = null;
		try {
			ret = rs.getDate(key);
		} catch (Exception e) {

		}
		return ret;
	}

	private static ParameterizedRowMapper<BLtsTheClubTxnDTO> getLtsTheClubTxnDTOMapper() {
		return new ParameterizedRowMapper<BLtsTheClubTxnDTO>() {

			public BLtsTheClubTxnDTO mapRow(ResultSet rs, int rowNum) throws SQLException {

				Integer txnLogId = null;
				Integer txnId = null;
				String theClubOrderId = null;
				String custNum = null;
				String memberId = null;
				Integer ocId = null;
				Integer dtlId = null;
				Integer dtlSeq = null;
				String action = null;
				String agreementNum = null;
				String idDocNum = null;
				String idDocType = null;
				Integer itemId = null;
				Integer offerId = null;
				String psefCd = null;
				String packageCode = null;
				String reverseTransId = null;
				String transId = null;
				Integer retryCnt = null;
				Integer retryLmt = null;
				Integer clubPoints = null;
				String transStatus = null;
				String rtnCode = null;
				String rtnMsg = null;
				String channel = null;
				Date createDate = null;
				Date lastUpdDate = null;

				txnId = rsGetInt(rs, COLUMN_NAMES[TXN_ID_IDX]);
				theClubOrderId = rsGetString(rs, COLUMN_NAMES[THE_CLUB_ORDER_ID_IDX]);
				custNum = rsGetString(rs, COLUMN_NAMES[CUST_NUM_IDX]);
				memberId = rsGetString(rs, COLUMN_NAMES[MEMBER_ID_IDX]);
				ocId = rsGetInt(rs, COLUMN_NAMES[OC_ID_IDX]);
				dtlId = rsGetInt(rs, COLUMN_NAMES[DTL_ID_IDX]);
				dtlSeq = rsGetInt(rs, COLUMN_NAMES[DTL_SEQ_IDX]);
				action = rsGetString(rs, COLUMN_NAMES[ACTION_IDX]);
				agreementNum = rsGetString(rs, COLUMN_NAMES[AGREEMENT_NUM_IDX]);
				idDocNum = rsGetString(rs, COLUMN_NAMES[ID_DOC_NUM_IDX]);
				idDocType = rsGetString(rs, COLUMN_NAMES[ID_DOC_TYPE_IDX]);
				itemId = rsGetInt(rs, COLUMN_NAMES[ITEM_ID_IDX]);
				offerId = rsGetInt(rs, COLUMN_NAMES[OFFER_ID_IDX]);
				psefCd = rsGetString(rs, COLUMN_NAMES[PSEF_CD_IDX]);
				packageCode = rsGetString(rs, COLUMN_NAMES[PACKAGE_CODE_IDX]);
				reverseTransId = rsGetString(rs, COLUMN_NAMES[REVERSE_TRANS_ID_IDX]);
				transId = rsGetString(rs, COLUMN_NAMES[TRANS_ID_IDX]);
				retryCnt = rsGetInt(rs, COLUMN_NAMES[RETRY_CNT_IDX]);
				retryLmt = rsGetInt(rs, COLUMN_NAMES[RETRY_LMT_IDX]);
				clubPoints = rsGetInt(rs, COLUMN_NAMES[CLUB_POINTS_IDX]);
				transStatus = rsGetString(rs, COLUMN_NAMES[TRANS_STATUS_IDX]);
				rtnCode = rsGetString(rs, COLUMN_NAMES[RTN_CODE_IDX]);
				rtnMsg = rsGetString(rs, COLUMN_NAMES[RTN_MSG_IDX]);
				channel = rsGetString(rs, COLUMN_NAMES[CHANNEL_IDX]);
				createDate = rsGetDate(rs, COLUMN_NAMES[CREATE_DATE_IDX]);
				lastUpdDate = rsGetDate(rs, COLUMN_NAMES[LAST_UPD_DATE_IDX]);

				BLtsTheClubTxnDTO obj = new BLtsTheClubTxnDTO(txnId, theClubOrderId, custNum, memberId, ocId, dtlId,
						dtlSeq, action, agreementNum, idDocNum, idDocType, itemId, offerId, psefCd, packageCode, reverseTransId, transId, retryCnt,
						retryLmt, clubPoints, transStatus, rtnCode, rtnMsg, channel, createDate, lastUpdDate);
				return obj;
			}
		};
	}

	private static ParameterizedRowMapper<BLtsTheClubTxnLogDTO> getLtsTheClubTxnLogDTOMapper() {
		return new ParameterizedRowMapper<BLtsTheClubTxnLogDTO>() {

			public BLtsTheClubTxnLogDTO mapRow(ResultSet rs, int rowNum) throws SQLException {

				Integer txnLogId = null;
				Integer txnId = null;
				String requestStr = null;
				String responseStr = null;
				String rtnCode = null;
				String rtnMsg = null;
				Date createDate = null;
				Date lastUpdDate = null;

				txnLogId = rsGetInt(rs, COLUMN_NAMES[TXN_LOG_ID_IDX]);
				txnLogId = rsGetInt(rs, COLUMN_NAMES[TXN_ID_IDX]);
				requestStr = rsGetString(rs, COLUMN_NAMES[REQUEST_STR_IDX]);
				responseStr = rsGetString(rs, COLUMN_NAMES[RESPONSE_STR_IDX]);
				rtnCode = rsGetString(rs, COLUMN_NAMES[RTN_CODE_IDX]);
				rtnMsg = rsGetString(rs, COLUMN_NAMES[RTN_MSG_IDX]);
				createDate = rsGetDate(rs, COLUMN_NAMES[CREATE_DATE_IDX]);
				lastUpdDate = rsGetDate(rs, COLUMN_NAMES[LAST_UPD_DATE_IDX]);

				BLtsTheClubTxnLogDTO obj = new BLtsTheClubTxnLogDTO(txnLogId, txnId, requestStr, responseStr, rtnCode,
						rtnMsg, createDate, lastUpdDate);
				return obj;
			}
		};
	}
}
