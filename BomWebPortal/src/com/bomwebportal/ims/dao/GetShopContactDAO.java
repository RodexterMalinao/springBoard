package com.bomwebportal.ims.dao;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;

import com.bomwebportal.dao.BaseDAO;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.ims.dto.OrderDTO;

public class GetShopContactDAO extends BaseDAO{
	protected final Log logger = LogFactory.getLog(getClass());
	
	public String getOrgSalesCd(String username) throws DAOException{
		logger.debug("getOrgSalesCd is called");
		
		String orgSalesCd = null;
		
		try{
			SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate);
			
			//String SQL = "select org_staff_id from BOMWEB_SALES_PROFILE where staff_id=?";
			String SQL = "	SELECT org_staff_id	"+
					"	  FROM (SELECT   org_staff_id	"+
					"	            FROM bomweb_sales_profile	"+
					"	           WHERE staff_id = ?	"+
					"	        ORDER BY start_date DESC, end_date DESC NULLS FIRST)	"+
					"		WHERE rownum = 1 ";			
			
			orgSalesCd = simpleJdbcTemplate.queryForObject(SQL, String.class, username);
		}catch (EmptyResultDataAccessException erdae){
			logger.debug("EmptyResultDataAccessException");
			orgSalesCd = null;
		}catch (Exception e) {
				logger.error("Exception caught in getOrgSalesCd()", e);
				throw new DAOException(e.getMessage(), e);
		}
		return orgSalesCd;
	}
	
	
	public String getReferrerStaffNameByNo(String referrerStaffNo) throws DAOException{
		logger.debug("getReferrerStaffNameByNo is called");
		
		String referrerStaffName = null;
		
		try{
			
			String SQL = "	select bsp.staff_name referrerStaffName	"+
						 "	from bomweb_sales_profile bsp, BOMWEB_SALES_ASSIGNMENT bsa "+
						 "	where bsa.STAFF_ID = bsp.STAFF_ID "+
						 "	and trunc(sysdate) BETWEEN bsp.START_DATE AND NVL( bsp.END_DATE,sysdate) "+
						 "	and trunc(sysdate) BETWEEN bsa.START_DATE AND NVL( bsa.END_DATE,sysdate) "+
						 "	and bsp.org_staff_id = ? "+
						 "	union "+
						 "	select bsp.staff_name referrerStaffName "+
						 "	from bomweb_sales_profile bsp, BOMWEB_SALES_ASSIGNMENT bsa "+
						 "	where bsa.STAFF_ID = bsp.STAFF_ID "+
						 "	and trunc(sysdate) BETWEEN bsp.START_DATE AND NVL( bsp.END_DATE,sysdate) "+
						 "	and trunc(sysdate) BETWEEN bsa.START_DATE AND NVL( bsa.END_DATE,sysdate) "+
						 "	and bsp.staff_id =  ? ";
		
			referrerStaffName = simpleJdbcTemplate.queryForObject(SQL, String.class, referrerStaffNo, referrerStaffNo);
		
		}catch (EmptyResultDataAccessException erdae){
			logger.debug("EmptyResultDataAccessException");
			referrerStaffName = null;
		}catch (Exception e) {
				logger.error("Exception caught in getReferrerStaffNameByNo()", e);
				throw new DAOException(e.getMessage(), e);
		}
		return referrerStaffName;
	}
	
	
	
	public String getStaffNameByOrgSalesCd(String username) throws DAOException{
		logger.debug("getStaffNameByOrgSalesCd is called");
		
		String staffName = null;
		
		try{
			SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate);
			
			//String SQL = "select staff_name from BOMWEB_SALES_PROFILE where staff_id=?";
			
			String SQL = "	SELECT staff_name	"+
			"	  FROM (SELECT   staff_name	"+
			"	            FROM bomweb_sales_profile	"+
			"	           WHERE staff_id = ?	"+
			"	        ORDER BY start_date DESC, end_date DESC NULLS FIRST)	"+
			"		WHERE rownum = 1 ";			
			
			staffName = simpleJdbcTemplate.queryForObject(SQL, String.class, username);
		}catch (EmptyResultDataAccessException erdae){
			logger.debug("EmptyResultDataAccessException");
			staffName = null;
		}catch (Exception e) {
				logger.error("Exception caught in getStaffNameByOrgSalesCd()", e);
				throw new DAOException(e.getMessage(), e);
		}
		return staffName;
	}
	
	public String getStaffNameBySalesCd(String salesCd) throws DAOException{
		logger.debug("getStaffNameBySalesCd is called");
		
		String staffName = null;
		
		try{
			SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate);
			
			//String SQL = "select staff_name from BOMWEB_SALES_PROFILE where org_staff_id=?";
			String SQL = "	SELECT staff_name	"+
					"	  FROM (SELECT   staff_name	"+
					"	            FROM bomweb_sales_profile	"+
					"	           WHERE org_staff_id = ?	"+
					"	        ORDER BY start_date DESC, end_date DESC NULLS FIRST)	"+
					"		WHERE rownum = 1 ";
			
			staffName = simpleJdbcTemplate.queryForObject(SQL, String.class, salesCd);
		}catch (EmptyResultDataAccessException erdae){
			logger.debug("EmptyResultDataAccessException");
			staffName = null;
		}catch (Exception e) {
				logger.error("Exception caught in getStaffNameBySalesCd()", e);
				throw new DAOException(e.getMessage(), e);
		}
		return staffName;
	}
	
	public String getStaffChannelCdBySalesCd(String salesCd) throws DAOException{
		logger.debug("getStaffChannelCdBySalesCd is called");
		
		String staffName = null;
		
		try{
			SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate);
			
			//String SQL = "select staff_name from BOMWEB_SALES_PROFILE where org_staff_id=?";
			String SQL = "		SELECT channel_cd												" +	
				"			  FROM (	                                                        " +
				"			  SELECT  channel_cd                                                " +
				"			      FROM bomweb_sales_profile a, bomweb_sales_assignment b        " +
				"			        WHERE a.org_staff_id = ?	                    		    " +
				"					  and a.staff_id = b.staff_id                           " +
				"					  ORDER BY a.start_date DESC, a.end_date DESC NULLS FIRST   " +
				"					)	                                                        " +
				"				WHERE rownum = 1                                                ";
			
			staffName = simpleJdbcTemplate.queryForObject(SQL, String.class, salesCd);
		}catch (EmptyResultDataAccessException erdae){
			logger.debug("EmptyResultDataAccessException");
			staffName = null;
		}catch (Exception e) {
				logger.error("Exception caught in getStaffNameBySalesCd()", e);
				throw new DAOException(e.getMessage(), e);
		}
		return staffName;
	}
	
	public String getShopContact(String shopCd) throws DAOException{
		logger.debug("getShopContact is called");
		String contactPhone = null;
		try{
			SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate);
			
			
			String SQL = "select contact_phone from BOMWEB_SHOP where shop_cd=?";
			contactPhone = simpleJdbcTemplate.queryForObject(SQL, String.class, shopCd);
			
			
		}
		catch (EmptyResultDataAccessException erdae)
		{
			logger.debug("EmptyResultDataAccessException");
			contactPhone = null;
		}catch (Exception e) {
				logger.error("Exception caught in getShopContact()", e);
				throw new DAOException(e.getMessage(), e);
		}
		return contactPhone;
	}
	
	//kinman 20140428
	public String getDeflaultSourceCode(String username) throws DAOException{
		logger.debug("getDeflaultSourceCode is called");
		
		String sourceCode  = null;
		
		try{
			SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate);
			
			String SQL = "	select DECODE (bsa.source_code, 'HKTP', bsa.team_cd, 'HKCR', bsa.team_cd, bsa.source_code) source_code	"+
			"	from bomweb_sales_profile bsp, BOMWEB_SALES_ASSIGNMENT bsa "+
			"	where bsa.STAFF_ID = bsp.STAFF_ID "+
			"	and trunc(sysdate) BETWEEN bsp.START_DATE AND NVL( bsp.END_DATE,sysdate) "+
			"	and trunc(sysdate) BETWEEN bsa.START_DATE AND NVL( bsa.END_DATE,sysdate) "+
			"	and bsp.org_staff_id = ? "+
			"	union "+
			"	select DECODE (bsa.source_code, 'HKTP', bsa.team_cd, 'HKCR', bsa.team_cd, bsa.source_code) source_code "+
			"	from bomweb_sales_profile bsp, BOMWEB_SALES_ASSIGNMENT bsa "+
			"	where bsa.STAFF_ID = bsp.STAFF_ID "+
			"	and trunc(sysdate) BETWEEN bsp.START_DATE AND NVL( bsp.END_DATE,sysdate) "+
			"	and trunc(sysdate) BETWEEN bsa.START_DATE AND NVL( bsa.END_DATE,sysdate) "+
			"	and bsp.staff_id =  ? ";

			
			sourceCode = simpleJdbcTemplate.queryForObject(SQL, String.class, username, username);
		}catch (EmptyResultDataAccessException erdae){
			logger.debug("EmptyResultDataAccessException");
			sourceCode = null;
		}catch (Exception e) {
				logger.error("Exception caught in getDeflaultSourceCode()", e);
				throw new DAOException(e.getMessage(), e);
		}
		return sourceCode;
	}
	
	
	public void insertCreditCardLog(String orderId,String loginId, String srvNo , String cardNo, String staffNo) throws DAOException{
		logger.debug("insertCreditCardLog");
		String SQL = "	INSERT INTO bomweb_ceks_log	" +
		"	            (ORDER_ID,LOGIN_ID,SRV_NO, STAFF_ID, CREDIT_CARD_NO, LOG_DATE)" +
		"	     VALUES (?, ?, ?,?,?,sysdate)	";

		
		try{
			simpleJdbcTemplate.update(SQL,orderId,loginId ,srvNo,staffNo, cardNo);
		}catch (Exception e) {
			logger.error("Exception caught in insertCreditCardLog()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	// 20190503, BOM2019038, order_id and reference_no = null
	public void insertCeksRecord(String order_id, String app_id, String reference_no, String ret_code, String pay_amount, String card_pan, 
			String exp_date, String ret_parm, String create_by, String gateway_code) throws DAOException {
		logger.debug("insertCeksRecord");
		String SQL = " INSERT INTO w_online_payment_txn "
				+ " (APP_ID, RET_CODE, PAY_AMOUNT, CARD_PAN, EXP_DATE, RET_PARM, CREATE_BY, CREATE_DATE, LAST_UPD_BY, LAST_UPD_DATE, GATEWAY_CODE) "
				+ " VALUES (?, ?, ?, ?, ?, ?, ?, sysdate, null, null, ?) ";
		
		try {
			simpleJdbcTemplate.update(SQL, app_id, ret_code, pay_amount, card_pan, exp_date, ret_parm, create_by, gateway_code);
		} catch (Exception e) {
			logger.error("Exception caught in insertCeksRecord()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public String getSalemanContactNumByStaffId(String staff_id) {
		logger.debug("getSalemanContactNumByStaffId is called");
		String c = null;
		
		String SQL = "SELECT contact_number FROM " +
				"(SELECT contact_number " +
				"FROM BOMWEB_SALES_PROFILE " +
				"WHERE staff_id = ? " +
				"ORDER BY START_DATE DESC, END_DATE DESC NULLS FIRST) " +
				"WHERE ROWNUM = 1";
		
		try {
			c = simpleJdbcTemplate.queryForObject(SQL, String.class, staff_id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return c;
	}

	
}
