package com.bomwebportal.lts.dao.bom;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.bomwebportal.dao.BaseDAO;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.lts.dto.profile.AccountDetailProfileAcqDTO;
import com.bomwebportal.lts.dto.profile.AccountDetailProfileLtsDTO;
import com.bomwebportal.lts.dto.profile.CustomerContactProfileLtsDTO;
import com.bomwebportal.lts.dto.profile.CustomerDetailProfileLtsDTO;
import com.bomwebportal.lts.dto.profile.CustomerRemarkProfileLtsDTO;
import com.bomwebportal.lts.dto.profile.RecontractRemarkDTO;
import com.bomwebportal.lts.util.LtsProfileConstant;
import com.bomwebportal.lts.util.LtsProfileHelper;
import com.pccw.springboard.svc.server.dto.AmAsmDTO;
import com.pccw.springboard.svc.server.dto.CampaignDTO;
import com.pccw.springboard.svc.server.dto.lts.LtsDataPrivacyDTO;

public class CustomerProfileLtsDAO extends BaseDAO {

	private static final String SQL_GET_MAX_TENURE_LTS = 
			"SELECT MAX(MONTHS_BETWEEN(TRUNC(SYSDATE, 'MONTH'), TRUNC(srv.eff_start_date, 'MONTH'))) " + 
			"from b_account_service_assgn acct_assg, b_account acct, b_customer cust, b_service srv, b_address_dtl addr_dtl " +
			"where acct.acct_num = acct_assg.acct_num and acct.system_id = acct_assg.system_id " + 
			"and acct_assg.service_id = srv.service_id and acct_assg.system_id = srv.system_id " +
			"and acct.cust_num = cust.cust_num and acct.system_id = cust.system_id " +
			"and acct_assg.eff_start_date <= sysdate and (acct_assg.eff_end_date is null or acct_assg.eff_end_date > sysdate) " + 
			"and acct.eff_start_date <= sysdate and (acct.eff_end_date is null or acct.eff_end_date > sysdate) " + 
			"and srv.eff_start_date <= sysdate and (srv.eff_end_date is null or srv.eff_end_date > sysdate) " + 
			"and ((srv.service_type = 'TEL' and substr(srv.srv_num,1,4) = '0000' ) or srv.service_type <> 'TEL') " +
			"and addr_dtl.addr_id = srv.install_addr " +
			"and cust.parent_cust_num = ? and acct.system_id = 'DRG' and (addr_dtl.srvbdry_num = ? or addr_dtl.srvbdry_num = ?)";
	
	private static final String SQL_GET_CUST_REMARK = 
			"select r.remark_seq, r.remarks, to_char(r.last_upd_date, 'dd/mm/yyyy HH24:MI:SS') as last_upd_date, r.last_upd_by " +
			"from b_customer_remark r " +
			"where r.cust_num = ? " +
			"and r.system_id = ? " +
			"order by r.last_upd_date desc, r.remark_seq";
	
	private static final String SQL_GET_CUST_SPECIAL_REMARK = 
			"select remark_seq, remarks, to_char(last_upd_date, 'dd/mm/yyyy HH24:MI:SS') as last_upd_date, last_upd_by " + 
			"from b_customer_spec_remarks " +
			"where cust_num = ? and system_id = ? " +
			"order by remark_seq";
	
	private static final String SQL_GET_ACCT_CUST_BY_DOC = 
			"SELECT acct.acct_num, acct.credit_status, acct.acct_status, acct.bill_lang, acct.bill_freq, " +
			" pay_assg.bill_fmt, pay_assg.bill_media, (CASE WHEN pay.pay_method_type IS NULL THEN 'M' ELSE pay.pay_method_type END) PAY_METHOD_TYPE, " +
			" pay.autopay_statement_ind, pay.serial_num, pay.bank_code, pay.branch_code, pay.bank_acct_num, DECODE(pay.credit_card_num, NULL, NULL, " +
			" ops$ims.sop_sum_pkg.str_encrypt(pay.credit_card_num, LENGTH(pay.credit_card_num), 'HKTIMS', 'D')) CREDIT_CARD_NUM, acct_dtl.curr_os_hkt_amt, " +
			" cust.cust_num, cust.id_doc_num, cust.id_doc_type, cust.title, cust.cust_first_name, cust.cust_last_name, cust_dtl.company_name, cust.parent_cust_num, " +
			" cust_dtl.id_verify_ind, cust_dtl.written_approval_required, DECODE(bl_cust.cust_num, NULL, 'N', 'Y') BLACKLIST_CUST_IND, acct.email_addr" +
			" FROM  b_account acct, b_account_pay_method_assgn pay_assg, b_customer_pay_method pay, b_account_details_lts acct_dtl, " +
			" b_customer cust, b_customer_details cust_dtl, b_blacklist_customer bl_cust " +
			" WHERE  acct.acct_num = pay_assg.acct_num(+) AND acct.system_id = pay_assg.system_id(+) " +
			" AND acct.acct_num = acct_dtl.acct_num AND pay_assg.cust_num = pay.cust_num(+) " +
			" AND pay_assg.pay_method_key = pay.pay_method_key(+) AND pay_assg.system_id = pay.system_id(+) " +
			" AND acct.cust_num = cust.cust_num AND acct.system_id = cust.system_id " +
			" AND cust.cust_num = cust_dtl.cust_num AND cust.system_id = cust_dtl.system_id " +
			" AND cust.cust_num = bl_cust.cust_num(+) AND cust.system_id = bl_cust.system_id(+) " +
			" AND (pay_assg.eff_end_date is null or pay_assg.eff_end_date > sysdate) " +
			" AND cust.id_doc_type = ? AND cust.id_doc_num = ? " +
			" AND cust.system_id = ? ";
	
	
	
	private static final String SQL_GET_CUST_BY_DOC = 
			"SELECT cust.cust_num, cust.date_of_birth, cust.id_doc_num, cust.id_doc_type, cust.title, cust_dtl.company_name, cust.cust_first_name, " +
			"cust.cust_last_name, cust.parent_cust_num, cust_dtl.id_verify_ind, cust_dtl.written_approval_required, " +
			"DECODE(bl_cust.cust_num, NULL, 'N', 'Y') BLACKLIST_CUST_IND, cust_pb.paper_bill " +
			"FROM  b_customer cust, b_customer_details cust_dtl, b_blacklist_customer bl_cust, b_cust_paper_bill cust_pb " +
			"WHERE cust.cust_num = cust_dtl.cust_num AND cust.system_id = cust_dtl.system_id " +
			"AND cust.cust_num = bl_cust.cust_num(+) AND cust.system_id = bl_cust.system_id(+) " +
			"AND cust.cust_num = cust_pb.cust_num(+) AND cust.system_id = cust_pb.system_id(+) " +
			"AND cust.id_doc_type = ? AND cust.id_doc_num = ? " +
			"AND cust.system_id = ? ";
	
	private static final String SQL_GET_CUST_BY_CUST_NUM = 
			"select CUST_NUM, ID_DOC_NUM, ID_DOC_TYPE, TITLE, CUST_FIRST_NAME, CUST_LAST_NAME, COMPANY_NAME, PARENT_CUST_NUM, ID_VERIFY_IND, " +
				   "WRITTEN_APPROVAL_REQUIRED, BLACKLIST_CUST_IND, CUST_CATG, SPEC_HANDLE_IND, PREMIER_TYPE, PAPER_BILL " + 
			"from B_LTS_CUST_PROFILE_V " +
			"where cust_num = ? and system_id = ?";	
	
	private static final String SQL_GET_ACCT_BY_ACCT_NUM = 
			"select ACCT_NUM, (select bom_desc from b_lookup where bom_grp_id = 'ACCT_STATUS' and bom_code = CREDIT_STATUS and bom_status = 'A') CREDIT_STATUS, " +
			"ACCT_STATUS, BILL_LANG, BILL_FREQ, BILL_FMT, BILL_MEDIA, PAY_METHOD_TYPE, AUTOPAY_STATEMENT_IND, SERIAL_NUM, BANK_CODE, BRANCH_CODE, BANK_ACCT_NUM, " +
			"CREDIT_CARD_NUM, CURR_OS_HKT_AMT, BILL_PERIOD, EMAIL_ADDR, " +
			"ACCT_NAME, BILL_ADDR, " +
			"WAIVE_PAPER_REA_CD, WAIVE_PAPER_IND " +
			"from B_LTS_ACCT_PROFILE_V " +
			"where acct_num = ? and system_id = ?";
	
	private static final String SQL_GET_PRIMARY_CONTACT_INFO =
			"select info.title || ' ' || info.cntct_name cntct_name, method.cnct_media_type, method.cnct_media_num, method.cnct_media_key, to_char(method.last_upd_date, 'dd/mm/yyyy HH24:MI') last_upd_date " +
			"from B_CUSTOMER_CONTACT_INFO info, B_CUSTOMER_CONTACT_METHOD method " +
			"where info.cust_num = method.cust_num and info.system_id = method.system_id and info.cntct_key = method.cntct_key " +
			"and info.primary_ind = 'Y' and info.cust_num = ? and info.system_id = ?";
	
	private static final String SQL_GET_WIP_REMARK = 
			"select remarks, last_upd_by, to_char(last_upd_date, 'dd/mm/yyyy HH24:MI:SS') last_upd_date, remark_seq " +
			"from B_CUSTOMER_WIP_REMARKS " +
			"where cust_num = ? and system_id = ? " + 
			"order by remark_seq desc";
	
	private static final String SQL_GET_RECONTRACT_REMARK = 
			"select SRV_NUM, SRC_CUST_NUM, SRC_CUST_NAME, SRC_ACCT_NUM, SRC_ID_DOC_TYPE, SRC_ID_DOC_NUM, " + 
					"TGT_CUST_NAME, TGT_CUST_NUM, TGT_ACCT_NUM, TGT_ID_DOC_TYPE, TGT_ID_DOC_NUM, REMARKS, LAST_UPD_BY, to_char(LAST_UPD_DATE, 'dd/mm/yyyy HH24:MI:SS') LAST_UPD_DATE " +
			"from " +
				"(select SRV_NUM, SRC_CUST_NUM, SRC_CUST_NAME, SRC_ACCT_NUM, SRC_ID_DOC_TYPE, SRC_ID_DOC_NUM, " +  
						"TGT_CUST_NAME, TGT_CUST_NUM, TGT_ACCT_NUM, TGT_ID_DOC_TYPE, TGT_ID_DOC_NUM, REMARKS, LAST_UPD_BY, LAST_UPD_DATE " +
				 "from B_RECONTRACT_REMARKS " +
				 "where src_cust_num = ? and src_system_id = ? " + 
				 "union " +
				 "select SRV_NUM, SRC_CUST_NUM, SRC_CUST_NAME, SRC_ACCT_NUM, SRC_ID_DOC_TYPE, SRC_ID_DOC_NUM, " + 
				 		"TGT_CUST_NAME, TGT_CUST_NUM, TGT_ACCT_NUM, TGT_ID_DOC_TYPE, TGT_ID_DOC_NUM, REMARKS, LAST_UPD_BY, LAST_UPD_DATE " +
				 "from B_RECONTRACT_REMARKS " +
				 "where tgt_cust_num = ? and tgt_system_id = ?) " +
				 "order by LAST_UPD_DATE desc";
	
	private static final String SQL_GET_AMASM_BY_CUST_NUM =
		"select am_cntct_name, am_cntct_phone, asm_cntct_name, asm_cntct_phone " +
        "from b_cust_am_asm_info " +
		"where cust_num = ? " +
		"and system_id = ? ";
	
	private static final String SQL_GET_CAMPAIGN_BY_CUST_NUM =
	    "select cust_lob, cust_num, description, campaign_name, campaign_lob, sales_channel, " +
	    "campaign_id, trim(to_char(campaign_start_date, 'DD/MM/YYYY')) as campaign_start_date, " +
	    "trim(to_char(campaign_end_date, 'DD/MM/YYYY')) as campaign_end_date, srv_num, super_cust_num, value_prop_id " +
		"from b_cust_campaign " +
		"where cust_num = ? " +
		"and system_id = ? " +
		"and description like '%' || ? || '%' ";	
	
	private static final String SQL_GET_LTS_DATA_PRIVACY_BY_CUST_NUM =
	    "select bom_lob, opt_ind, direct_mailing, outbound, sms, email, web_bill, bill_insert, cd_outdial, " +
	    "bm, sms_eye, datacast, trim(to_char(last_upd_date,'DD/MM/YYYY hh24:mi:ss')) last_upd_date, last_upd_by " +
	    "from b_cust_data_privacy_lts " +
	    "where cust_num = ? and system_id = ? " +
	    "order by decode(bom_lob, 'LTS', 'A') asc ";
	
	private static final String SQL_GET_LTS_ACQ_ACCT_INFO_BY_CUST_NUM =
			"select a.acct_num, a.cust_num, b.service_id, c.srv_num, b.chrg_type, c.service_type " +
			"from b_account a, b_account_service_assgn b, b_service c " +
			"where a.system_id = 'DRG'" +
			"and a.credit_status not in ('08','09','10') " +
			"and a.system_id = b.system_id(+) " +
			"and a.acct_num = b.acct_num(+) " +
			"and a.eff_end_date is null " +
			"and b.eff_end_date is null " +
			"and a.cust_num = ? " +
			"and b.service_id = c.service_id(+) " + 
			"order by srv_num ";
	
	private static final String SQL_GET_BOM_DUMMY_CUST_NUM_SEQ = 
		"select b_dummy_cust_num_seq.nextval cust_num " +
		"from dual ";
	
	private static final String SQL_GET_BOM_DUMMY_ACCT_NUM_SEQ = 
		"select b_dummy_acct_num_seq.nextval acct_num " +
		"from dual ";
	
	public List<AccountDetailProfileAcqDTO> getAcctListByCustNum(String pCustNum) throws DAOException{
		List<AccountDetailProfileAcqDTO> acctList = null;
		ParameterizedRowMapper<AccountDetailProfileAcqDTO> mapper = new ParameterizedRowMapper<AccountDetailProfileAcqDTO>() {
			public AccountDetailProfileAcqDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				AccountDetailProfileAcqDTO dto = new AccountDetailProfileAcqDTO();
				dto.setAcctNum(rs.getString("ACCT_NUM"));
				dto.setSrvNum(rs.getString("SRV_NUM"));
				dto.setServiceType(rs.getString("SERVICE_TYPE"));
				dto.setChargeType(rs.getString("CHRG_TYPE"));
				dto.setCustNum(rs.getString("CUST_NUM"));
				return dto;
			}
		};
		try{
			acctList = simpleJdbcTemplate.query(SQL_GET_LTS_ACQ_ACCT_INFO_BY_CUST_NUM, mapper, pCustNum);
		} catch (EmptyResultDataAccessException erdae) {
			throw new AppRuntimeException(erdae);
		} catch (Exception e) {
			logger.error("Error in getAcctListByCustNum() CustNum " + pCustNum, e);
			throw new DAOException(e.getMessage(), e);
		}
		
		if(acctList != null && acctList.size() > 0){
			return acctList;
		}
		
		return null;
	}
	
	public int getMaxLtsTenure(String pBomCustNum, String pUnit, String pFloor, String pSrvBdry) throws DAOException {
		
		StringBuilder sb = new StringBuilder(SQL_GET_MAX_TENURE_LTS);
		
		if (StringUtils.isEmpty(pUnit)) {
			sb.append(" and addr_dtl.unit_num is null");
		} else {
			sb.append(" and addr_dtl.unit_num = '");
			sb.append(pUnit);
			sb.append("'");
		}
		if (StringUtils.isEmpty(pFloor)) {
			sb.append(" and addr_dtl.floor_num is null");
		} else {
			sb.append(" and addr_dtl.floor_num = '");
			sb.append(pFloor);
			sb.append("'");
		}
		String[] srvBdies = LtsProfileHelper.reformatSrvBoundary(pSrvBdry);
		
		try {
			String tenure = simpleJdbcTemplate.queryForObject(sb.toString(), String.class, pBomCustNum, srvBdies[0], srvBdies[1]);
			
			if (StringUtils.isEmpty(tenure)) {
				return 0;
			}
			return Integer.valueOf(tenure);
		} catch (Exception e) {
			logger.error("Error in getMaxLtsTenure().  Cust No. " + pBomCustNum + " Unit: " + pUnit + " Floor: " + pFloor + " SB: " + pSrvBdry, e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public CustomerRemarkProfileLtsDTO[] getCustomerRemark(String pCustNum, String pSytemId, CustomerDetailProfileLtsDTO pCust) throws DAOException {
		
		List<CustomerRemarkProfileLtsDTO> remarkList = null; 
				
		try {
			remarkList = simpleJdbcTemplate.query(SQL_GET_CUST_REMARK, this.getRemarkMapper(), pCustNum, pSytemId);
		} catch (EmptyResultDataAccessException erdae) {
			throw new AppRuntimeException(erdae);
		} catch (Exception e) {
			logger.error("Error in getCustomerRemark() Cust No. " + pCustNum, e);
			throw new DAOException(e.getMessage(), e);
		}
		CustomerRemarkProfileLtsDTO[] resultRmks = (CustomerRemarkProfileLtsDTO[])remarkList.toArray(new CustomerRemarkProfileLtsDTO[remarkList.size()]);
		
		if (pCust != null) {
			pCust.setCustRemarks(resultRmks);
		}
		return resultRmks;
	}
	
	public CustomerRemarkProfileLtsDTO[] getCustomerSpecialRemark(String pCustNum, String pSystemId, CustomerDetailProfileLtsDTO pCust) throws DAOException {
		
		List<CustomerRemarkProfileLtsDTO> remarkList = null; 
				
		try {
			remarkList = simpleJdbcTemplate.query(SQL_GET_CUST_SPECIAL_REMARK, this.getRemarkMapper(), pCustNum, pSystemId);
		} catch (EmptyResultDataAccessException erdae) {
			throw new AppRuntimeException(erdae);
		} catch (Exception e) {
			logger.error("Error in getCustomerRemark() Cust No. " + pCustNum, e);
			throw new DAOException(e.getMessage(), e);
		}
		CustomerRemarkProfileLtsDTO[] resultRmks = (CustomerRemarkProfileLtsDTO[])remarkList.toArray(new CustomerRemarkProfileLtsDTO[remarkList.size()]);
		
		if (pCust != null) {
			pCust.setCustSpecialRemarks(resultRmks);
		}
		return resultRmks;
	}
	
	private ParameterizedRowMapper<CustomerRemarkProfileLtsDTO> getRemarkMapper() {
		return new ParameterizedRowMapper<CustomerRemarkProfileLtsDTO>() {
			public CustomerRemarkProfileLtsDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				
				CustomerRemarkProfileLtsDTO remark = new CustomerRemarkProfileLtsDTO();
				remark.setRemarks(rs.getString("REMARKS"));
				remark.setLastUpdBy(rs.getString("LAST_UPD_BY"));
				remark.setLastUpdDate(rs.getString("LAST_UPD_DATE"));
				remark.setRemarkSeq(rs.getString("REMARK_SEQ"));
				return remark;
			}
		};
	}
	
	public AccountDetailProfileLtsDTO[] getAcctCustByDoc(String pDocType, String pDocId, String sysId) throws DAOException{
		List<AccountDetailProfileLtsDTO> accountList = null;
		
		try{
			accountList = simpleJdbcTemplate.query(SQL_GET_ACCT_CUST_BY_DOC, this.getAccountMapper(), pDocType, pDocId, sysId);
		} catch (EmptyResultDataAccessException erdae) {
			throw new AppRuntimeException(erdae);
		} catch (Exception e) {
			logger.error("Error in getCustAttcByDocId() DocType/DocId " + pDocType + "/" + pDocId, e);
			throw new DAOException(e.getMessage(), e);
		}
		
		if(accountList != null && accountList.size() > 0){
			return accountList.toArray(new AccountDetailProfileLtsDTO[accountList.size()]);
		}
		
		return null;
	}
	
	public CustomerDetailProfileLtsDTO getCustByDoc(String pDocType, String pDocId, String sysId) throws DAOException {
		
		List<CustomerDetailProfileLtsDTO> custList = null;
		
		try {
			custList = simpleJdbcTemplate.query(SQL_GET_CUST_BY_DOC, this.getCustMapper(), pDocType, pDocId, sysId);
		} catch (EmptyResultDataAccessException erdae) {
			throw new AppRuntimeException(erdae);
		} catch (Exception e) {
			logger.error("Error in getCustByDocId() DocType/DocId " + pDocType + "/" + pDocId, e);
			throw new DAOException(e.getMessage(), e);
		}
		
		if (custList != null && custList.size() > 0) {
			return custList.get(0);
		}
		
		return null;
	}
	
	public CustomerDetailProfileLtsDTO getCustByCustNum(String pCustNum, String pSystemId) throws DAOException {
		
		ParameterizedRowMapper<CustomerDetailProfileLtsDTO> mapper = new ParameterizedRowMapper<CustomerDetailProfileLtsDTO>() {
			public CustomerDetailProfileLtsDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				CustomerDetailProfileLtsDTO customer = new CustomerDetailProfileLtsDTO();
				customer.setTitle(rs.getString("TITLE"));
				customer.setCustNum(rs.getString("CUST_NUM"));
				customer.setDocNum(rs.getString("ID_DOC_NUM"));
				customer.setDocType(rs.getString("ID_DOC_TYPE"));
				customer.setFirstName(rs.getString("CUST_FIRST_NAME"));
				customer.setLastName(rs.getString("CUST_LAST_NAME"));
				customer.setCompanyName(rs.getString("COMPANY_NAME"));
				customer.setParentCustNum(rs.getString("PARENT_CUST_NUM"));
				customer.setIdVerifyInd(StringUtils.equals(rs.getString("ID_VERIFY_IND"), "Y"));
				customer.setWipInd(rs.getString("WRITTEN_APPROVAL_REQUIRED"));
				customer.setBlacklistCustInd(StringUtils.equals(rs.getString("BLACKLIST_CUST_IND"), "Y"));
				customer.setCustCatg(rs.getString("CUST_CATG"));
				customer.setSpecialHandle(StringUtils.equals(rs.getString("SPEC_HANDLE_IND"), "Y"));
				customer.setPremierType(rs.getString("PREMIER_TYPE"));
				customer.setPaperBill(rs.getString("PAPER_BILL"));
				return customer;
			}
		};
		List<CustomerDetailProfileLtsDTO> custList = null;
		
		try {
			custList = simpleJdbcTemplate.query(SQL_GET_CUST_BY_CUST_NUM, mapper, pCustNum, pSystemId);
		} catch (EmptyResultDataAccessException erdae) {
			throw new AppRuntimeException(erdae);
		} catch (Exception e) {
			logger.error("Error in getCustByCustNum() cust num " + pCustNum, e);
			throw new DAOException(e.getMessage(), e);
		}
		if (custList != null && custList.size() > 0) {
			return custList.get(0);
		}
		return null;
	}
	
	public AccountDetailProfileLtsDTO getAccountbyAcctNum(String pAcctNum, String pSystemId) throws DAOException {
		
		ParameterizedRowMapper<AccountDetailProfileLtsDTO> mapper = new ParameterizedRowMapper<AccountDetailProfileLtsDTO>() {
			public AccountDetailProfileLtsDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				AccountDetailProfileLtsDTO account = new AccountDetailProfileLtsDTO();				
				account.setAcctNum(rs.getString("ACCT_NUM"));
				account.setCreditStatus(rs.getString("CREDIT_STATUS"));
				account.setAcctStatus(rs.getString("ACCT_STATUS"));
				account.setPayMethod(rs.getString("PAY_METHOD_TYPE"));
				account.setOutstandingAmount(rs.getString("CURR_OS_HKT_AMT"));
				account.setBillFmt(rs.getString("BILL_FMT"));
				account.setBillFreq(rs.getString("BILL_FREQ"));
				account.setBillLang(rs.getString("BILL_LANG"));
				account.setBillMedia(rs.getString("BILL_MEDIA"));
				account.setAutopayStatementInd(StringUtils.equals(rs.getString("AUTOPAY_STATEMENT_IND"), "Y"));
				account.setSerialNum(rs.getString("SERIAL_NUM"));
				account.setBankCd(rs.getString("BANK_CODE"));
				account.setBranchCd(rs.getString("BRANCH_CODE"));
				account.setBankAcctNum(rs.getString("BANK_ACCT_NUM"));
				account.setCreditCardNum(rs.getString("CREDIT_CARD_NUM"));
				account.setBillPeriod(rs.getString("BILL_PERIOD"));
				account.setEmailAddr(rs.getString("EMAIL_ADDR"));
				account.setAcctName(rs.getString("ACCT_NAME"));
				account.setBillAddr(rs.getString("BILL_ADDR"));
				account.setWaivePaperReaCd(rs.getString("WAIVE_PAPER_REA_CD"));
				account.setWaivePaperInd(rs.getString("WAIVE_PAPER_IND"));
				return account;
			}
		};
		List<AccountDetailProfileLtsDTO> acctList = null;
		
		try {
			acctList = simpleJdbcTemplate.query(SQL_GET_ACCT_BY_ACCT_NUM, mapper, pAcctNum, pSystemId);
		} catch (EmptyResultDataAccessException erdae) {
			throw new AppRuntimeException(erdae);
		} catch (Exception e) {
			logger.error("Error in getAccountbyAcctNum() acct num " + pAcctNum, e);
			throw new DAOException(e.getMessage(), e);
		}
		if (acctList != null && acctList.size() > 0) {
			return acctList.get(0);
		}
		return null;
	}
	
	public CustomerContactProfileLtsDTO[] getCustContactInfo(String pCustNum, String pSystemId) throws DAOException {
		
		ParameterizedRowMapper<CustomerContactProfileLtsDTO> mapper = new ParameterizedRowMapper<CustomerContactProfileLtsDTO>() {
			public CustomerContactProfileLtsDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				CustomerContactProfileLtsDTO contact = new CustomerContactProfileLtsDTO();
				contact.setContactName(rs.getString("CNTCT_NAME"));
				contact.setMediaNum(rs.getString("CNCT_MEDIA_NUM"));
				contact.setMediaType(rs.getString("CNCT_MEDIA_TYPE"));
				contact.setMediaKey(rs.getString("CNCT_MEDIA_KEY"));
				contact.setLastUpdDate(rs.getString("LAST_UPD_DATE"));
				return contact;
			}
		};
		List<CustomerContactProfileLtsDTO> contactList = null;
		
		try {
			contactList = simpleJdbcTemplate.query(SQL_GET_PRIMARY_CONTACT_INFO, mapper, pCustNum, pSystemId);
		} catch (EmptyResultDataAccessException erdae) {
			throw new AppRuntimeException(erdae);
		} catch (Exception e) {
			logger.error("Error in getCustContactInfo() cust num " + pCustNum, e);
			throw new DAOException(e.getMessage(), e);
		}
		return contactList.toArray(new CustomerContactProfileLtsDTO[contactList.size()]);
	}
	
	public CustomerRemarkProfileLtsDTO[] getWipRemark(String pCustNum, String pSystemId) throws DAOException {
		
		List<CustomerRemarkProfileLtsDTO> remarkList = null; 
		
		try {
			remarkList = simpleJdbcTemplate.query(SQL_GET_WIP_REMARK, this.getRemarkMapper(), pCustNum, pSystemId);
		} catch (EmptyResultDataAccessException erdae) {
			throw new AppRuntimeException(erdae);
		} catch (Exception e) {
			logger.error("Error in getWipRemark() Cust No. " + pCustNum, e);
			throw new DAOException(e.getMessage(), e);
		}
		return (CustomerRemarkProfileLtsDTO[])remarkList.toArray(new CustomerRemarkProfileLtsDTO[remarkList.size()]);
		
	}
	
	public RecontractRemarkDTO[] getRecontractRemark(String pCustNum, String pSystemId) throws DAOException {
		
		List<RecontractRemarkDTO> remarkList = null; 
		
		try {
			remarkList = simpleJdbcTemplate.query(SQL_GET_RECONTRACT_REMARK, this.getRecontractRemarkMapper(), pCustNum, pSystemId, pCustNum, pSystemId);
		} catch (EmptyResultDataAccessException erdae) {
			throw new AppRuntimeException(erdae);
		} catch (Exception e) {
			logger.error("Error in getWipRemark() Cust No. " + pCustNum, e);
			throw new DAOException(e.getMessage(), e);
		}
		return (RecontractRemarkDTO[])remarkList.toArray(new RecontractRemarkDTO[remarkList.size()]);
		
	}
	
	private ParameterizedRowMapper<RecontractRemarkDTO> getRecontractRemarkMapper() {
		return new ParameterizedRowMapper<RecontractRemarkDTO>() {
			public RecontractRemarkDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				RecontractRemarkDTO remark = new RecontractRemarkDTO();
				remark.setLastUpdBy(rs.getString("LAST_UPD_BY"));
				remark.setLastUpdDate(rs.getString("LAST_UPD_DATE"));
				remark.setRemark(rs.getString("REMARKS"));
				remark.setSrcAcctNum(rs.getString("SRC_ACCT_NUM"));
				remark.setSrcCustName(rs.getString("SRC_CUST_NAME"));
				remark.setSrcCustNum(rs.getString("SRC_CUST_NUM"));
				remark.setSrcDocNum(rs.getString("SRC_ID_DOC_NUM"));
				remark.setSrcDocType(rs.getString("SRC_ID_DOC_TYPE"));
				remark.setSrvNum(rs.getString("SRV_NUM"));
				remark.setTargetAcctNum(rs.getString("TGT_ACCT_NUM"));
				remark.setTargetCustName(rs.getString("TGT_CUST_NAME"));
				remark.setTargetCustNum(rs.getString("TGT_CUST_NUM"));
				remark.setTargetDocNum(rs.getString("TGT_ID_DOC_NUM"));
				remark.setTargetDocType(rs.getString("TGT_ID_DOC_TYPE"));
				return remark;
			}
		};
	}
	
	private ParameterizedRowMapper<AccountDetailProfileLtsDTO> getAccountMapper() {
		return new ParameterizedRowMapper<AccountDetailProfileLtsDTO>() {
			public AccountDetailProfileLtsDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
//				String chargeType = rs.getString("CHRG_TYPE");
				
				AccountDetailProfileLtsDTO account = new AccountDetailProfileLtsDTO();
				account.setAcctNum(rs.getString("ACCT_NUM"));
				account.setCreditStatus(rs.getString("CREDIT_STATUS"));
				account.setAcctStatus(rs.getString("ACCT_STATUS"));
//				--account.setAcctChrgType(chargeType);
				account.setPayMethod(rs.getString("PAY_METHOD_TYPE"));
				account.setOutstandingAmount(rs.getString("CURR_OS_HKT_AMT"));
				account.setBillFmt(rs.getString("BILL_FMT"));
				account.setBillFreq(rs.getString("BILL_FREQ"));
				account.setBillLang(rs.getString("BILL_LANG"));
				account.setBillMedia(rs.getString("BILL_MEDIA"));
				account.setAutopayStatementInd(StringUtils.equals(rs.getString("AUTOPAY_STATEMENT_IND"), "Y"));
				account.setSerialNum(rs.getString("SERIAL_NUM"));
				account.setBankCd(rs.getString("BANK_CODE"));
				account.setBranchCd(rs.getString("BRANCH_CODE"));
				account.setBankAcctNum(rs.getString("BANK_ACCT_NUM"));
				account.setCreditCardNum(rs.getString("CREDIT_CARD_NUM"));
				account.setEmailAddr(rs.getString("EMAIL_ADDR"));

//				if (StringUtils.contains(chargeType, "R")) {
//					account.setPrimaryAcctInd(true);
//				}
				
				CustomerDetailProfileLtsDTO customer = mapCustRow(rs, rowNum);
				account.setCustomerProfile(customer);
				
				return account;
			}
		};
	}
	
	private ParameterizedRowMapper<CustomerDetailProfileLtsDTO> getCustMapper() {
		return new ParameterizedRowMapper<CustomerDetailProfileLtsDTO>() {
			public CustomerDetailProfileLtsDTO mapRow(ResultSet rs, int rowNum) throws SQLException {				
				CustomerDetailProfileLtsDTO newCust = new CustomerDetailProfileLtsDTO();
				newCust = mapCustRow(rs, rowNum);
				newCust.setPaperBill(rs.getString("PAPER_BILL"));
				newCust.setDob(rs.getDate("DATE_OF_BIRTH")!=null?new SimpleDateFormat("dd/MM/yyyy").format(rs.getDate("DATE_OF_BIRTH")):null);				
				return newCust;
			}
		};
	}
	
	private CustomerDetailProfileLtsDTO mapCustRow(ResultSet rs, int rowNum) throws SQLException {
		CustomerDetailProfileLtsDTO customer = new CustomerDetailProfileLtsDTO();
		customer.setTitle(rs.getString("TITLE"));
		customer.setCustNum(rs.getString("CUST_NUM"));
		customer.setDocNum(rs.getString("ID_DOC_NUM"));
		customer.setDocType(rs.getString("ID_DOC_TYPE"));
		customer.setFirstName(rs.getString("CUST_FIRST_NAME"));
		customer.setLastName(rs.getString("CUST_LAST_NAME"));
		customer.setCompanyName(rs.getString("COMPANY_NAME"));
		customer.setParentCustNum(rs.getString("PARENT_CUST_NUM"));
		customer.setIdVerifyInd(StringUtils.equals(rs.getString("ID_VERIFY_IND"), "Y"));
		customer.setWipInd(rs.getString("WRITTEN_APPROVAL_REQUIRED"));
		customer.setBlacklistCustInd(StringUtils.equals(rs.getString("BLACKLIST_CUST_IND"), "Y"));
		return customer;
	}
	
	public AmAsmDTO getAmAsm(String systemId, String custNum) throws DAOException {
		try {
			List<AmAsmDTO> itemList = simpleJdbcTemplate.query(
					SQL_GET_AMASM_BY_CUST_NUM, amAsmMapper, custNum, systemId);
			return itemList.isEmpty() ? null : itemList.get(0);
		} catch (EmptyResultDataAccessException erdae) {
			throw new AppRuntimeException(erdae);
		} catch (Exception e) {
			logger.error("Error in getAmAsm - ", e);
			throw new DAOException(e.getMessage(), e);
		}		
	}
    
	private ParameterizedRowMapper<AmAsmDTO> amAsmMapper = new ParameterizedRowMapper<AmAsmDTO>() {
		public AmAsmDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
			AmAsmDTO amAsm = new AmAsmDTO();
			amAsm.setAmName(rs.getString("AM_CNTCT_NAME"));
			amAsm.setAmContactNo(rs.getString("AM_CNTCT_PHONE"));
			amAsm.setAsmName(rs.getString("ASM_CNTCT_NAME"));
			amAsm.setAsmContactNo(rs.getString("ASM_CNTCT_PHONE"));
			return amAsm;
		}
	};
	
	public List<CampaignDTO> getCampaign(String systemId, String custNum, String desc) throws DAOException {		
	    try{
	    	List<CampaignDTO> itemList = simpleJdbcTemplate.query(
	    			SQL_GET_CAMPAIGN_BY_CUST_NUM, campaignMapper, custNum, systemId, desc);	    	    	
	    	return itemList.isEmpty() ? null : itemList;
	    } catch (EmptyResultDataAccessException erdae) {
			throw new AppRuntimeException(erdae);
		} catch (Exception e) {
			logger.error("Error in getCampaign - ", e);
			throw new DAOException(e.getMessage(), e);
		}
	}	
	
	public List<CampaignDTO> getCampaign(String systemId, String custNum, String serviceNum, String campaignLob) throws DAOException {		
	    
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		
		StringBuilder sql = new StringBuilder();
		sql.append("select bcc.cust_lob, bcc.cust_num, bcc.description, bcc.campaign_name, bcc.campaign_lob, bcc.sales_channel, ");
		sql.append("bcc.campaign_id, trim(to_char(bcc.campaign_start_date, 'DD/MM/YYYY')) as campaign_start_date, ");
		sql.append("trim(to_char(bcc.campaign_end_date, 'DD/MM/YYYY')) as campaign_end_date, bcc.srv_num, bcc.super_cust_num, bcc.value_prop_id ");
		sql.append("from B_CUST_CAMPAIGN bcc, b_lookup  bl ");
		sql.append("where bcc.cust_num = :custNum ");
		sql.append("and bcc.system_id = :systemId ");
		sql.append("and bcc.campaign_lob = :campaignLob ");
		sql.append("and (bcc.campaign_end_date is null or bcc.campaign_end_date > sysdate) ");
		sql.append("and bcc.value_prop_id = bl.BOM_CODE ");
		sql.append("and bl.bom_grp_id in ( 'CUST_CAMPAIGN', 'IDD_CAMPAIGN', 'PREM_CAMPAIGN' ) ");
		
		
		paramSource.addValue("custNum", custNum);
		paramSource.addValue("systemId", systemId);
		paramSource.addValue("campaignLob", campaignLob);
		
		if (StringUtils.isNotEmpty(serviceNum)) {
			sql.append("and bcc.srv_num = :serviceNum ");
			paramSource.addValue("serviceNum", StringUtils.right(serviceNum, 8));
		}
		sql.append("order by bcc.campaign_start_date desc ");
		
		try{
	    	List<CampaignDTO> itemList = simpleJdbcTemplate.query(sql.toString(), campaignMapper, paramSource);	    	    	
	    	return itemList.isEmpty() ? null : itemList;
	    } catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException in getCampaign()");
			return null;
		} catch (Exception e) {
			logger.error("Error in getCampaign - ", e);
			throw new DAOException(e.getMessage(), e);
		}
	}	
	
	
	private ParameterizedRowMapper<CampaignDTO> campaignMapper = new ParameterizedRowMapper<CampaignDTO>() {
		public CampaignDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
			CampaignDTO campaign = new CampaignDTO();			
			campaign.setCustLOB(rs.getString("CUST_LOB"));
			campaign.setCustNum(rs.getString("CUST_NUM"));
			campaign.setDescription(rs.getString("DESCRIPTION"));
			campaign.setCampaignName(rs.getString("CAMPAIGN_NAME"));
			campaign.setCampaignLOB(rs.getString("CAMPAIGN_LOB"));
			campaign.setSalesChannel(rs.getString("SALES_CHANNEL"));
			campaign.setCampaignId(rs.getString("CAMPAIGN_ID"));
			campaign.setCampaignStartDate(rs.getString("CAMPAIGN_START_DATE"));
			campaign.setCampaignEndDate(rs.getString("CAMPAIGN_END_DATE"));
			campaign.setServiceNum(rs.getString("SRV_NUM"));
			campaign.setSuperCustNum(rs.getString("SUPER_CUST_NUM"));
			campaign.setValuePropId(rs.getString("VALUE_PROP_ID"));
			return campaign;
		}
	};
	
	public List<LtsDataPrivacyDTO> getLtsDataPrivacy(String systemId, String custNum) throws DAOException {		
	    try{
	    	List<LtsDataPrivacyDTO> itemList = simpleJdbcTemplate.query(
	    			SQL_GET_LTS_DATA_PRIVACY_BY_CUST_NUM, ltsDataPrivacyMapper, custNum, systemId);	    	    	
	    	return itemList.isEmpty() ? null : itemList;
	    } catch (EmptyResultDataAccessException erdae) {
			throw new AppRuntimeException(erdae);
		} catch (Exception e) {
			logger.error("Error in getLtsDataPrivacy - ", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
		
	private ParameterizedRowMapper<LtsDataPrivacyDTO> ltsDataPrivacyMapper = new ParameterizedRowMapper<LtsDataPrivacyDTO>() {
		public LtsDataPrivacyDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
			LtsDataPrivacyDTO ltsDataPrivacy = new LtsDataPrivacyDTO();			
			ltsDataPrivacy.setBomLob(rs.getString("BOM_LOB"));
			ltsDataPrivacy.setOptInd(rs.getString("OPT_IND"));
			ltsDataPrivacy.setOptDesc(LtsProfileConstant.DATA_PRIVACY_OPT_IND_OOA_CD.equals(rs.getString("OPT_IND")) 
					? LtsProfileConstant.DATA_PRIVACY_OPT_IND_OOA
							: LtsProfileConstant.DATA_PRIVACY_OPT_IND_OOP_CD.equals(rs.getString("OPT_IND")) 
							? LtsProfileConstant.DATA_PRIVACY_OPT_IND_OOP
									: LtsProfileConstant.DATA_PRIVACY_OPT_IND_OIA_CD.equals(rs.getString("OPT_IND")) 
									? LtsProfileConstant.DATA_PRIVACY_OPT_IND_OIA : null);
			ltsDataPrivacy.setDirectMailing("Y".equals(rs.getString("DIRECT_MAILING"))?true:false);
			ltsDataPrivacy.setOutbound("Y".equals(rs.getString("OUTBOUND"))?true:false);
			ltsDataPrivacy.setSms("Y".equals(rs.getString("SMS"))?true:false);
			ltsDataPrivacy.setEmail("Y".equals(rs.getString("EMAIL"))?true:false);
			ltsDataPrivacy.setWebBill("Y".equals(rs.getString("WEB_BILL"))?true:false);
			ltsDataPrivacy.setBillInsert("Y".equals(rs.getString("BILL_INSERT"))?true:false);
			ltsDataPrivacy.setCdOutdial("Y".equals(rs.getString("CD_OUTDIAL"))?true:false);			
			// for LTS only
			if (LtsProfileConstant.DATA_PRIVACY_BOM_LOB_LTS.equals(rs.getString("BOM_LOB"))) {
				ltsDataPrivacy.setBm("Y".equals(rs.getString("BM"))?true:false);
				ltsDataPrivacy.setSmsEye("Y".equals(rs.getString("SMS_EYE"))?true:false);
			}
			// for future only
			if (rs.getString("DATACAST")!=null) {
				ltsDataPrivacy.setDataCast("Y".equals(rs.getString("DATACAST"))?true:false);
			}
			ltsDataPrivacy.setUpdatedTime(rs.getString("LAST_UPD_DATE"));
			ltsDataPrivacy.setUpdatedBy(rs.getString("LAST_UPD_BY"));			
			return ltsDataPrivacy;
		}
	};
	
	public String getBomDummyCustNumSeq() throws DAOException {
		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
			public String mapRow(ResultSet rs, int rowNum)
					throws SQLException {				
				return rs.getString("CUST_NUM");
			}
		};
		try {
			List<String> itemList = this.simpleJdbcTemplate.query(SQL_GET_BOM_DUMMY_CUST_NUM_SEQ, mapper);
			return itemList.isEmpty() ? "0" : itemList.get(0);
		}
		catch (Exception e) {
			logger.error("Error in getBomDummyCustNumSeq - " , e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public String getBomDummyAcctNumSeq() throws DAOException {
		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
			public String mapRow(ResultSet rs, int rowNum)
					throws SQLException {				
				return rs.getString("ACCT_NUM");
			}
		};
		try {
			List<String> itemList = this.simpleJdbcTemplate.query(SQL_GET_BOM_DUMMY_ACCT_NUM_SEQ, mapper);
			return itemList.isEmpty() ? "0" : itemList.get(0);
		}
		catch (Exception e) {
			logger.error("Error in getBomDummyAcctNumSeq - " , e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
}
