package com.bomwebportal.lts.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.bomwebportal.dao.BaseDAO;
import com.bomwebportal.dto.LookupItemDTO;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.lts.dto.ItemDetailDTO;
import com.bomwebportal.lts.dto.LtsPrePaymentDTO;
import com.bomwebportal.lts.util.LtsConstant;

public class LtsPaymentDAO extends BaseDAO {

	private static final String SQL_GET_TENURE_CODE = 
			"SELECT tenure_cd FROM bomweb_tenure_lkup" +
			" WHERE upper_tenure >= ?" + 
			" AND lower_tenure <= ?";
	
	private static final String SQL_GET_NEW_PAY_METHOD = 
			"SELECT new_pay_mtd_key FROM bomweb_tenure_pay_method_lkup " +
			" WHERE tenure_cd = ?" + 
			" AND exist_pay_mtd_key = ?";
	
	private static final String SQL_GET_ALL_PREPAY_ITEM = 
			"SELECT new_pay_mtd_key, prepay_item_id FROM bomweb_tenure_prepay_lkup " + 
			"WHERE tenure_cd = ?" +
			" AND house_type = ?" +
			" AND device_type = ?";
	
	private static final String SQL_GET_PREPAY_ITEM = "SELECT prepay_item_id FROM bomweb_tenure_prepay_lkup " + 
			"WHERE tenure_cd = ?" +
			" AND new_pay_mtd_key = ?" +
			" AND house_type = ?" +
			" AND device_type = ?";
	
	private static final String SQL_GET_ORDER_LTS_PREPAYMENT = 
			"select * from bomweb_order_lts_prepay " + 
		    " where order_id = ? " +
		    " and prepay_type = 'P' ";				
	
	public String getTenureCode(double tenure) throws DAOException{
		List<String> list = new ArrayList<String>();
		
		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
			public String mapRow(ResultSet rs, int rowNum)	
					throws SQLException {
				return rs.getString("TENURE_CD");
			}
		};
		
		try {
			list = simpleJdbcTemplate.query(SQL_GET_TENURE_CODE, mapper, tenure, tenure);
		} catch (EmptyResultDataAccessException erdae) {
			throw new AppRuntimeException(erdae);
		} catch (Exception e) {
			logger.error("Exception caught in getTenureCode(int)", e);
			throw new DAOException(e.getMessage(), e);
		}
		return list.get(0);
		
	}
	
	public List<String> getNewPayMethods(String tenureCode, String existPayMethod) throws DAOException{
		List<String> list = new ArrayList<String>();
		
		
		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
			public String mapRow(ResultSet rs, int rowNum)	
					throws SQLException {
				return rs.getString("new_pay_mtd_key");
			}
		};
		
		try {
			list = simpleJdbcTemplate.query(SQL_GET_NEW_PAY_METHOD, mapper, tenureCode, existPayMethod);
		} catch (EmptyResultDataAccessException erdae) {
			throw new AppRuntimeException(erdae);
		} catch (Exception e) {
			logger.error("Exception caught in newPayMethod(String, String)", e);
			throw new DAOException(e.getMessage(), e);
		}
		return list;
	}
	
	public List<String[]> getPrePayItem(String tenureCode, String houseType, String deviceType) throws DAOException{
		List<String[]> list = new ArrayList<String[]>();
		
		ParameterizedRowMapper<String[]> mapper = new ParameterizedRowMapper<String[]>() {
			public String[] mapRow(ResultSet rs, int rowNum)	
					throws SQLException {
				String[] item = {rs.getString("prepay_item_id"), rs.getString("new_pay_mtd_key")};
				return item;
			}
		};
		
		try {
			list = simpleJdbcTemplate.query(SQL_GET_ALL_PREPAY_ITEM, mapper, tenureCode, houseType, deviceType);
		} catch (EmptyResultDataAccessException erdae) {
			throw new AppRuntimeException(erdae);
		} catch (Exception e) {
			logger.error("Exception caught in getPrePayItem(String, String)", e);
			throw new DAOException(e.getMessage(), e);
		}
		if (list.size() == 0){
			return null;
		}
		return list;
	}
	
	public String getPrePayItem(String tenureCode, String newPaymethod, String houseType, String deviceType) throws DAOException{
		List<String> list = new ArrayList<String>();
		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
			public String mapRow(ResultSet rs, int rowNum)	
					throws SQLException {
				String item = rs.getString("prepay_item_id");
				return item;
			}
		};
		
		try {
			list = simpleJdbcTemplate.query(SQL_GET_PREPAY_ITEM, mapper, tenureCode, newPaymethod, houseType, deviceType);
		} catch (EmptyResultDataAccessException erdae) {
			throw new AppRuntimeException(erdae);
		} catch (Exception e) {
			logger.error("Exception caught in getPrePayItem(String, String, String)", e);
			throw new DAOException(e.getMessage(), e);
		}
		if (list.size() == 0){
			return null;
		}
		return list.get(0);
	}
	
	private static final String SQL_GET_ITEM_DETAIL_1 = 
			"SELECT i.ID, i.LOB, i.TYPE, d.description, NVL(i.mdo_Ind, 'O'), d.html, pd.locale," +
			" pd.onetime_amt_txt, p.onetime_amt, pd.recurrent_amt_txt, p.recurrent_amt," + 
			" pd.mth_to_mth_rate_txt, p.mth_to_mth_rate, pd.PENALTY_AMT_TXT, pd.REBATE_AMT_TXT," +
			" pd.EFF_END_DATE, p.EFF_END_DATE" +
			" FROM W_ITEM i, W_ITEM_DISPLAY_LKUP d,"; 
			
	private static final String SQL_GET_ITEM_DETAIL_2 = 
			" WHERE i.ID = d.item_ID" +
			" AND i.ID = pd.item_ID (+)" +
			" AND i.ID = p.ID";
	
	private static final String SQL_GET_ITEM_DETAIL_3 = 
			" AND (pd.EFF_END_DATE IS NULL OR pd.EFF_END_DATE >= SYSDATE)" +
			" AND (p.EFF_END_DATE IS NULL OR p.EFF_END_DATE >= SYSDATE)";
	
	public List<ItemDetailDTO> getItemDetail(String itemId, String displayType, 
			String locale, String type)throws DAOException {
		
		String SQL = SQL_GET_ITEM_DETAIL_1 + 
				" (SELECT * FROM W_ITEM_PRICING_DISPLAY_LKUP WHERE locale = '" + 
				locale + "') pd, W_ITEM_PRICING p" +
				SQL_GET_ITEM_DETAIL_2 +
				" AND i.LOB = '" + LtsConstant.LOB_LTS + "'"+
				" AND i.ID = '" + itemId + "'" +
				" AND d.DISPLAY_TYPE = '" + displayType + "'" +
				" AND d.locale = '" + locale + "'" +
				SQL_GET_ITEM_DETAIL_3;
		
//		String SQL = "SELECT i.ID, i.LOB, i.TYPE, d.description, NVL(i.mdo_Ind, 'O'), d.html, pd.locale," +
//				" pd.onetime_amt_txt, p.onetime_amt, pd.recurrent_amt_txt, p.recurrent_amt," + 
//				" pd.mth_to_mth_rate_txt, p.mth_to_mth_rate, pd.PENALTY_AMT_TXT, pd.REBATE_AMT_TXT," +
//				" pd.EFF_END_DATE, p.EFF_END_DATE" +
//				" FROM W_ITEM i, W_ITEM_DISPLAY_LKUP d," + 
//				" (SELECT * FROM W_ITEM_PRICING_DISPLAY_LKUP WHERE locale = '" + locale + "') pd, W_ITEM_PRICING p" +
//				" WHERE i.ID = d.item_ID" +
//				" AND i.ID = pd.item_ID (+)" +
//				" AND i.ID = p.ID" +
//				" AND i.LOB = '" + LtsConstant.LOB_LTS + "'"+
//				" AND i.ID = '" + itemId + "'" +
//				" AND d.DISPLAY_TYPE = '" + displayType + "'" +
//				" AND d.locale = '" + locale + "'" +
//				" AND (pd.EFF_END_DATE IS NULL OR pd.EFF_END_DATE >= SYSDATE)" +
//				" AND (p.EFF_END_DATE IS NULL OR p.EFF_END_DATE >= SYSDATE)";
		
		if(type != null){
			SQL = SQL.concat(" AND i.TYPE = '" + type + "'");
		}
		
		return getItemDetail(SQL);
	}
	
	public List<ItemDetailDTO> getItemDetailByType(String type, String displayType, 
			String locale)throws DAOException {
		
		String SQL = SQL_GET_ITEM_DETAIL_1 + 
				" (SELECT * FROM W_ITEM_PRICING_DISPLAY_LKUP WHERE locale = '" + 
				locale + "') pd, W_ITEM_PRICING p" +
				SQL_GET_ITEM_DETAIL_2 +
				" AND i.LOB = '" + LtsConstant.LOB_LTS + "'" +
				" AND TYPE = '" + type + "'" +
				" AND d.DISPLAY_TYPE = '" + displayType + "'" +
				" AND d.locale = '" + locale + "'" +
				SQL_GET_ITEM_DETAIL_3;
		
//		String SQL = "SELECT i.ID, i.LOB, i.TYPE, d.description, NVL(i.mdo_Ind, 'O'), d.html, pd.locale," +
//				" pd.onetime_amt_txt, p.onetime_amt, pd.recurrent_amt_txt, p.recurrent_amt," + 
//				" pd.mth_to_mth_rate_txt, p.mth_to_mth_rate, pd.PENALTY_AMT_TXT, pd.REBATE_AMT_TXT," +
//				" pd.EFF_END_DATE, p.EFF_END_DATE" +
//				" FROM W_ITEM i, W_ITEM_DISPLAY_LKUP d," + 
//				" (SELECT * FROM W_ITEM_PRICING_DISPLAY_LKUP WHERE locale = '" + locale + "') pd, W_ITEM_PRICING p" +
//				" WHERE i.ID = d.item_ID" +
//				" AND i.ID = pd.item_ID (+)" +
//				" AND i.ID = p.ID" +
//				" AND i.LOB = '" + LtsConstant.LOB_LTS + "'" +
//				" AND TYPE = '" + type + "'" +
//				" AND d.DISPLAY_TYPE = '" + displayType + "'" +
//				" AND d.locale = '" + locale + "'" +
//				" AND (pd.EFF_END_DATE IS NULL OR pd.EFF_END_DATE >= SYSDATE)" +
//				" AND (p.EFF_END_DATE IS NULL OR p.EFF_END_DATE >= SYSDATE)";
		
		return getItemDetail(SQL);
	}
	
	public List<ItemDetailDTO> getItemDetail(String SQL)throws DAOException {
		List<ItemDetailDTO> list = new ArrayList<ItemDetailDTO>();

		ParameterizedRowMapper<ItemDetailDTO> mapper = 
				new ParameterizedRowMapper<ItemDetailDTO>() {
			public ItemDetailDTO mapRow(ResultSet rs, int rowNum)	
					throws SQLException {
				ItemDetailDTO itemDetail = new ItemDetailDTO();
				itemDetail.setItemId(rs.getString("ID"));
				itemDetail.setItemDesc(rs.getString("description"));
				itemDetail.setItemDisplayHtml(rs.getString("html"));
				itemDetail.setItemLob(rs.getString("lob"));
				itemDetail.setItemType(rs.getString("type"));
				itemDetail.setLocale(rs.getString("locale"));
				itemDetail.setMthToMthAmtTxt(rs.getString("mth_to_mth_rate"));
				itemDetail.setMthToMthAmt(rs.getString("mth_to_mth_rate_txt"));
				itemDetail.setOneTimeAmt(rs.getString("onetime_amt"));
				itemDetail.setOneTimeAmtTxt(rs.getString("onetime_amt_txt"));
				itemDetail.setRecurrentAmt(rs.getString("recurrent_amt"));
				itemDetail.setRecurrentAmtTxt(rs.getString("recurrent_amt_txt"));
				itemDetail.setPenaltyAmtTxt(rs.getString("penalty_amt_txt"));
				itemDetail.setRebateAmtTxt(rs.getString("rebate_amt_txt"));
				itemDetail.setMdoInd(rs.getString("NVL(i.mdo_Ind,'O')"));
				
				return itemDetail;
			}
		};
		
		try {
			list = simpleJdbcTemplate.query(SQL, mapper);
		} catch (EmptyResultDataAccessException erdae) {
			throw new AppRuntimeException(erdae);
		} catch (Exception e) {
			logger.error("Exception caught in getItemDetail(String, String, String)", e);
			throw new DAOException(e.getMessage(), e);
		}
		if (list.size() == 0){
			return null;
		}
		return list;
	}
	
	
	public List<LookupItemDTO> getBankCode() throws DAOException {
		List<LookupItemDTO> list = new ArrayList<LookupItemDTO>();
		String SQL = "SELECT * FROM W_ISSUEBANKLKUP";
		ParameterizedRowMapper<LookupItemDTO> mapper = 
				new ParameterizedRowMapper<LookupItemDTO>() {
			public LookupItemDTO mapRow(ResultSet rs, int rowNum)	
					throws SQLException {
				LookupItemDTO bankInfo = new LookupItemDTO();
				bankInfo.setItemKey(rs.getString("bank_code"));
				bankInfo.setItemValue(rs.getString("bank_code")+" "+rs.getString("bank_name"));
				
				return bankInfo;
			}
		};
		
		try {
			list = simpleJdbcTemplate.query(SQL, mapper);
		} catch (EmptyResultDataAccessException erdae) {
			throw new AppRuntimeException(erdae);
		} catch (Exception e) {
			logger.error("Exception caught in getBankCode()", e);
			throw new DAOException(e.getMessage(), e);
		}
		if (list.size() == 0){
			return null;
		}
		return list;
	}
	
	
	public List<LookupItemDTO> getBranchCode(String bankCode) throws DAOException {

		List<LookupItemDTO> list = new ArrayList<LookupItemDTO>();
		String SQL = "SELECT * FROM W_AP_BANKBRANCHLKUP" + 
		" WHERE bank_code = '" + bankCode +"' ORDER BY branch_code";
		
		ParameterizedRowMapper<LookupItemDTO> mapper = 
				new ParameterizedRowMapper<LookupItemDTO>() {
			public LookupItemDTO mapRow(ResultSet rs, int rowNum)	
					throws SQLException {
				LookupItemDTO branchInfo = new LookupItemDTO();
				branchInfo.setItemKey(rs.getString("branch_code"));
				branchInfo.setItemValue(rs.getString("branch_code")+" " + rs.getString("branch_name"));
				
				return branchInfo;
			}
		};
		
		try {
			list = simpleJdbcTemplate.query(SQL, mapper);
		} catch (EmptyResultDataAccessException erdae) {
			throw new AppRuntimeException(erdae);
		} catch (Exception e) {
			logger.error("Exception caught in getBranchCode(String)", e);
			throw new DAOException(e.getMessage(), e);
		}
		if (list.size() == 0){
			return null;
		}
		return list;
	}
	
	public List<String> getCancelRemarkItemIdList() throws DAOException {
		String SQL = "SELECT distinct prepay_item_id FROM bomweb_tenure_prepay_lkup where CREATE_CANCEL_REMARK = 'Y'";
		List<String> list = new ArrayList<String>();
		ParameterizedRowMapper<String> mapper = 
				new ParameterizedRowMapper<String>() {
			public String mapRow(ResultSet rs, int rowNum)	
					throws SQLException {
				String itemId = rs.getString("prepay_item_id");
				return itemId;
			}
		};
		
		try {
			list = simpleJdbcTemplate.query(SQL, mapper);
			return list;
		} catch (EmptyResultDataAccessException erdae) {
			throw new AppRuntimeException(erdae);
		} catch (Exception e) {
			logger.error("Exception caught in getBranchNameByCode()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public LtsPrePaymentDTO getOrderLtsPrepayment(String orderId) throws DAOException{
		ParameterizedRowMapper<LtsPrePaymentDTO> mapper = new ParameterizedRowMapper<LtsPrePaymentDTO>() {
			public LtsPrePaymentDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				LtsPrePaymentDTO ltsPrePaymentDTO = new LtsPrePaymentDTO();
				ltsPrePaymentDTO.setOrderId(rs.getString("order_id"));
				ltsPrePaymentDTO.setPrepayType(rs.getString("prepay_type"));
				ltsPrePaymentDTO.setAcctNo(rs.getString("acct_no"));
				ltsPrePaymentDTO.setPrepayAmt(Double.parseDouble((rs.getString("prepay_amt"))));
				ltsPrePaymentDTO.setMerchantCode(rs.getString("mercahnt_code"));
				ltsPrePaymentDTO.setBillType(rs.getString("bill_type"));
				ltsPrePaymentDTO.setShopCode(rs.getString("shop_code"));
				ltsPrePaymentDTO.setTranCode(rs.getString("tran_code"));
				ltsPrePaymentDTO.setPaymentStatus(rs.getString("payment_status"));				
				return ltsPrePaymentDTO;
			}
		};		
		try {			
			List<LtsPrePaymentDTO> itemList = simpleJdbcTemplate.query(SQL_GET_ORDER_LTS_PREPAYMENT, mapper, orderId);
			return itemList.isEmpty() ? null : itemList.get(0);
		} catch (EmptyResultDataAccessException erdae) {
			throw new AppRuntimeException(erdae);
		} catch (Exception e) {
			logger.error("Exception caught in getOrderLtsPrepayment", e);
			throw new DAOException(e.getMessage(), e);
		}		
	}
	
}



