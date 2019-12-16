package com.bomwebportal.lts.dao.bom;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.bomwebportal.dao.BaseDAO;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.lts.dto.profile.AccountDetailProfileLtsDTO;
import com.bomwebportal.lts.dto.profile.AccountServiceLtsDTO;
import com.bomwebportal.lts.dto.profile.AddressDetailProfileLtsDTO;
import com.bomwebportal.lts.dto.profile.CustomerDetailProfileLtsDTO;
import com.bomwebportal.lts.dto.profile.PendingOrdStatusDetailDTO;
import com.bomwebportal.lts.dto.profile.ServiceDetailProfileLtsDTO;
import com.bomwebportal.lts.dto.profile.ServiceGroupMemberProfileDTO;
import com.bomwebportal.lts.dto.profile.ServiceGroupProfileDTO;

public class ServiceProfileLtsDAO extends BaseDAO {

	private static final String SQL_GET_SRV_PROFILE_V = 
			"select * " +
			"from b_lts_profile_v " +
			"where mem_srv_num = ? " +
			"and service_type = ?";
	
	private static final String SQL_GET_SRV_PROFILE_BY_CUST_V = 
			"select * " +
			"from b_lts_profile_v " +
			"where cust_num = ? " +
			"and service_type = ?" + 
			"order by service_id";
	
	private static final String SQL_GET_PENDING_ORDER_BY_DN = 
			"select os.oc_id, os.dtl_id, ols.bom_status, ols.status, to_char(od.srv_req_date, 'dd/mm/yyyy') as srv_req_date, od.ord_type " +
			"from b_ord_srv os, b_ord_latest_status ols, b_ord_dtl od " + 
			"where os.oc_id = ols.oc_id " + 
			"and os.oc_id = od.oc_id " + 
			"and os.dtl_id = ols.dtl_id " + 
			"and os.dtl_id = od.dtl_id " + 
			"and os.dtl_seq = ols.dtl_seq " + 
			"and (NVL(ols.bom_status,' ') NOT IN ('02','07') AND (NVL(ols.bom_status,' ')<>'04' AND NVL(ols.status,' ')<>'C')) " + 
			"and (os.srv_id = ? or os.ex_srv_id = ?) " +
			"and od.type_of_srv = ?";

	private static final String SQL_GET_SRV_MEM_NUM = 
		"select service_mem_num " +
		"from b_service_member " +
		"where system_id = 'DRG' " + 
		"and eff_start_date <= sysdate and (eff_end_date is null or eff_end_date > sysdate) " +
		"and service_id = ? and srv_num = ?";
	
	private static final String SQL_GET_EYE_GRP = 
		"select grp.srv_grp_type, grp.srv_grp_num, grp.service_id, nvl(srv.srv_num, grp.service_id) srv_num, nvl(srv_assgn.dat_cd,grp.system_id) dat_cd " +
		"from b_srv_grp_assgn grp, b_service_type_assgn srv_assgn, b_service srv " +
		"where grp.service_id = srv_assgn.service_id(+) and grp.service_id = srv.service_id " +
		"and grp.srv_grp_num in (select srv_grp_num " + 
								"from b_srv_grp_assgn " + 
								"where service_id = ? and srv_grp_type in (select bom_desc " + 
																		  "from b_lookup " + 
																		  "where bom_grp_id = 'EYE_GROUP' and bom_status = 'A') " + 
		"and eff_start_date <= sysdate and (eff_end_date is null or eff_end_date > sysdate)) " + 
		"and grp.eff_start_date <= sysdate and (grp.eff_end_date is null or grp.eff_end_date > sysdate) " +
		"and srv.eff_start_date <= sysdate and (srv.eff_end_date is null or srv.eff_end_date > sysdate) " +  
		"and srv_assgn.eff_start_date(+) <= sysdate and (srv_assgn.eff_end_date is null or srv_assgn.eff_end_date > sysdate)";
	
	private static final String SQL_GET_TERM_SRV_EYE_GRP = 
		"select grp.srv_grp_type, grp.srv_grp_num, grp.service_id, nvl(srv.srv_num, grp.service_id) srv_num, nvl(srv_assgn.dat_cd,grp.system_id) dat_cd " +
		"from b_srv_grp_assgn grp, b_service_type_assgn srv_assgn, b_service srv " + 
		"where grp.service_id = srv_assgn.service_id(+) and grp.service_id = srv.service_id " + 
		"and grp.srv_grp_num in (select srv_grp_num " + 
								"from b_srv_grp_assgn srv_assgn, b_service srv " + 
								"where srv.system_id = srv_assgn.system_id and srv.service_id = srv_assgn.service_id " +
								"and srv_assgn.srv_grp_type in (select bom_desc " + 
															   "from b_lookup " +
															   "where bom_grp_id = 'EYE_GROUP' and bom_status = 'A') " + 
		"and srv.eff_end_date = srv_assgn.eff_end_date " + 
		"and srv_assgn.service_id = ? ) " + 
		"and srv.eff_end_date = grp.eff_end_date";
	
	private static final String SQL_GET_SIMPLE_SRV_PROFILE = 
		"select srv.SERVICE_ID, srv.SYSTEM_ID, srv.SRV_NUM, srv.MEM_SRV_NUM, srv.NGFL_3G_IND, srv.SERVICE_TYPE, srv.EFF_START_DATE, srv.EFF_END_DATE, " + 
		"srv.SRV_TENUE, srv.DAT_CD, srv.TARIFF, srv.INVENT_STS_CD, srv.WAIVE_COUNT, srv.DUPLEX_NUM, srv.TWIN_LINE_IND, srv.ADDR_ID, " + 
		"srv.UNIT_NUM, srv.FLOOR_NUM, srv.BUILD_NAME, srv.SECTDSC, srv.HLOT_NUM,  srv.STREET_NUM, srv.STREET_NAME, srv.STCATDSC, srv.DISTDSC, srv.AREADSC, " + 
		"srv.SRVBDRY_NUM, srv.SECTCD, srv.STREET_CAT_CD, srv.DISTCD, srv.AREACD, srv.ADDRESS " +
		"from B_LTS_SRV_PROFILE_V srv, b_service_member srv_mem " +
		"where  srv.service_id = srv_mem.service_id and service_mem_num = 1 " + 
		"and srv.mem_srv_num = srv_mem.srv_num " +
		"and srv_mem.eff_start_date <= sysdate and (srv_mem.eff_end_date is null or srv_mem.eff_end_date > sysdate) " +
		"and srv_mem.service_id = ? and srv.system_id = ?";
	
	private static final String SQL_GET_ACCT_BY_SRV = 
		"select acct.acct_num, acct.acct_type, acct_assg.chrg_type, acct.cust_num, acct.acct_name, to_char(acct_assg.eff_start_date, 'dd/mm/yyyy') eff_start_date, to_char(acct_assg.eff_end_date, 'dd/mm/yyyy') eff_end_date " +
		"from B_LTS_ACCT_PROFILE_V acct, b_account_service_assgn acct_assg " +
		"where acct.acct_num = acct_assg.acct_num and acct.system_id = acct_assg.system_id " +
		"and acct_assg.service_id = ? and acct.system_id = ?";
	
	private static final String SQL_GET_TERM_SRV_PROFILE = 
		"select srv.SERVICE_ID, srv.SYSTEM_ID, srv.SRV_NUM, srv.MEM_SRV_NUM, srv.NGFL_3G_IND, srv.SERVICE_TYPE, srv.EFF_START_DATE, srv.EFF_END_DATE, " + 
			   "srv.SRV_TENUE, srv.DAT_CD, srv.TARIFF, srv.WAIVE_COUNT, srv.DUPLEX_NUM, srv.ADDR_ID, " + 
			   "srv.UNIT_NUM, srv.FLOOR_NUM, srv.BUILD_NAME, srv.SECTDSC, srv.HLOT_NUM,  srv.STREET_NUM, srv.STREET_NAME, srv.STCATDSC, srv.DISTDSC, srv.AREADSC, " + 
			   "srv.SRVBDRY_NUM, srv.SECTCD, srv.STREET_CAT_CD, srv.DISTCD, srv.AREACD, srv.ADDRESS, null TWIN_LINE_IND, null INVENT_STS_CD " +
		"from B_LTS_TERM_SRV_PROFILE_V srv, b_service_member srv_mem " + 
		"where  srv.service_id = srv_mem.service_id and service_mem_num = 1 " + 
		"and srv.mem_srv_num = srv_mem.srv_num " + 
		"and to_date(srv.eff_end_date, 'dd/mm/yyyy') = srv_mem.eff_end_date " +
		"and srv_mem.service_id = ? and srv.system_id = ?";
	
		
	public ServiceDetailProfileLtsDTO getServiceProfile(String pSrvNum, String pSrvType) throws DAOException {
			
		final ServiceDetailProfileLtsDTO srvDtlProfileLts = new ServiceDetailProfileLtsDTO();
		final List<AccountDetailProfileLtsDTO> accountList = new ArrayList<AccountDetailProfileLtsDTO>();
			
		ParameterizedRowMapper<ServiceDetailProfileLtsDTO> mapper = new ParameterizedRowMapper<ServiceDetailProfileLtsDTO>() {
			public ServiceDetailProfileLtsDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
					
				String chargeType = rs.getString("CHRG_TYPE");
					
				if (StringUtils.contains(chargeType, "R")) {
					srvDtlProfileLts.setCcSrvId(rs.getString("SERVICE_ID"));
					srvDtlProfileLts.setSrvNum(rs.getString("SRV_NUM"));
					srvDtlProfileLts.setNgfl3gInd(rs.getString("NGFL_3G_IND"));
					srvDtlProfileLts.setSrvType(rs.getString("SERVICE_TYPE"));
					srvDtlProfileLts.setDatCd(rs.getString("DAT_CD"));
					srvDtlProfileLts.setTariff(rs.getString("TARIFF"));
					srvDtlProfileLts.setDuplexNum(rs.getString("DUPLEX_NUM"));
					srvDtlProfileLts.setTwinLineInd(StringUtils.equals(rs.getString("TWIN_LINE_IND"), "Y"));
					srvDtlProfileLts.setWaiveCount(rs.getString("WAIVE_COUNT"));
					srvDtlProfileLts.setLtsSrvTenure(rs.getDouble("SRV_TENUE"));
					srvDtlProfileLts.setEffEndDate(rs.getString("EFF_END_DATE"));
					srvDtlProfileLts.setEffStartDate(rs.getString("EFF_START_DATE"));
					srvDtlProfileLts.setInventStatus(rs.getString("INVENT_STS_CD"));
						
					AddressDetailProfileLtsDTO address = new AddressDetailProfileLtsDTO();
					address.setAddrId(rs.getString("ADDR_ID"));
					address.setUnitNum(rs.getString("UNIT_NUM"));
					address.setFloorNum(rs.getString("FLOOR_NUM"));
					address.setBuildName(rs.getString("BUILD_NAME"));
					address.setSectDesc(rs.getString("SECTDSC"));
					address.setHlotNum(rs.getString("HLOT_NUM"));
					address.setStreetNum(rs.getString("STREET_NUM"));
					address.setStreetName(rs.getString("STREET_NAME"));
					address.setStreetCat(rs.getString("STCATDSC"));
					address.setDistrict(rs.getString("DISTDSC"));
					address.setArea(rs.getString("AREADSC"));
					address.setSectCd(rs.getString("SECTCD"));
					address.setStreetCatCd(rs.getString("STREET_CAT_CD"));
					address.setDistrictCd(rs.getString("DISTCD"));
					address.setAreaCd(rs.getString("AREACD"));
					address.setSrvBdry(rs.getString("SRVBDRY_NUM"));
					address.setFullAddress(rs.getString("ADDRESS"));
					srvDtlProfileLts.setAddress(address);
					
					CustomerDetailProfileLtsDTO customer = new CustomerDetailProfileLtsDTO();
					customer.setCustNum(rs.getString("CUST_NUM"));
					customer.setDocNum(rs.getString("ID_DOC_NUM"));
					customer.setDocType(rs.getString("ID_DOC_TYPE"));
					customer.setFirstName(rs.getString("CUST_FIRST_NAME"));
					customer.setLastName(rs.getString("CUST_LAST_NAME"));
					customer.setCompanyName(rs.getString("COMPANY_NAME"));
					customer.setDob(rs.getString("DATE_OF_BIRTH"));
					customer.setParentCustNum(rs.getString("PARENT_CUST_NUM"));
					customer.setIdVerifyInd(StringUtils.equals(rs.getString("ID_VERIFY_IND"), "Y"));
					customer.setWipInd(rs.getString("WRITTEN_APPROVAL_REQUIRED"));
					customer.setBlacklistCustInd(StringUtils.equals(rs.getString("BLACKLIST_CUST_IND"), "Y"));
					customer.setTitle(rs.getString("TITLE"));
					srvDtlProfileLts.setPrimaryCust(customer);
				}
				AccountDetailProfileLtsDTO account = new AccountDetailProfileLtsDTO();
				account.setAcctNum(rs.getString("ACCT_NUM"));
				account.setCreditStatus(rs.getString("CREDIT_STATUS"));
				account.setAcctStatus(rs.getString("ACCT_STATUS"));
				account.setAcctChrgType(chargeType);
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
				account.setRedemMedia(rs.getString("REDEM_MEDIA"));
				account.setBillAddr(rs.getString("BILL_ADDR"));
				
				if (StringUtils.contains(chargeType, "R")) {
					account.setPrimaryAcctInd(true);
				}
				accountList.add(account);
				
				CustomerDetailProfileLtsDTO customer = new CustomerDetailProfileLtsDTO();
				customer.setCustNum(rs.getString("CUST_NUM"));
				customer.setDocNum(rs.getString("ID_DOC_NUM"));
				customer.setDocType(rs.getString("ID_DOC_TYPE"));
				customer.setFirstName(rs.getString("CUST_FIRST_NAME"));
				customer.setLastName(rs.getString("CUST_LAST_NAME"));
				customer.setDob(rs.getString("DATE_OF_BIRTH"));
				customer.setParentCustNum(rs.getString("PARENT_CUST_NUM"));
				customer.setIdVerifyInd(StringUtils.equals(rs.getString("ID_VERIFY_IND"), "Y"));
				customer.setWipInd(rs.getString("WRITTEN_APPROVAL_REQUIRED"));
				customer.setBlacklistCustInd(StringUtils.equals(rs.getString("BLACKLIST_CUST_IND"), "Y"));
				customer.setCustCatg(rs.getString("CUST_CATG"));
				customer.setSpecialHandle(StringUtils.equals(rs.getString("SPEC_HANDLE_IND"), "Y"));
				customer.setPremierType(rs.getString("PREMIER_TYPE"));
				account.setCustomerProfile(customer);
				return null;
			}
		};
		
		try {		
			simpleJdbcTemplate.query(SQL_GET_SRV_PROFILE_V, mapper, pSrvNum, pSrvType);
		} catch (EmptyResultDataAccessException erdae) {
			throw new AppRuntimeException(erdae);
		} catch (Exception e) {
			logger.error("Error in getServiceProfile() DN: " +  pSrvNum, e);
			throw new DAOException(e.getMessage(), e);
		}
		if (StringUtils.isEmpty(srvDtlProfileLts.getCcSrvId())) {
			return null;
		}
		srvDtlProfileLts.setAccounts((AccountDetailProfileLtsDTO[])accountList.toArray(new AccountDetailProfileLtsDTO[accountList.size()]));
		srvDtlProfileLts.setSearchCriteriaDn(pSrvNum);
		return srvDtlProfileLts;
	}
	
	public ServiceDetailProfileLtsDTO[] getServiceProfileByCustomer(String pCustNum, String pSrvType) throws DAOException {
		
		final Map<String,ServiceDetailProfileLtsDTO> srvMap = new HashMap<String,ServiceDetailProfileLtsDTO>();
		final Map<String,List<AccountDetailProfileLtsDTO>> acctMap = new HashMap<String,List<AccountDetailProfileLtsDTO>>();
			
		ParameterizedRowMapper<ServiceDetailProfileLtsDTO> mapper = new ParameterizedRowMapper<ServiceDetailProfileLtsDTO>() {
			public ServiceDetailProfileLtsDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				
				String ccSrvID = rs.getString("SERVICE_ID");
				ServiceDetailProfileLtsDTO srvDtlProfileLts = null;
				
				if (srvMap.containsKey(ccSrvID)) {
					srvDtlProfileLts = srvMap.get(ccSrvID);
				} else {
					srvDtlProfileLts = new ServiceDetailProfileLtsDTO();
					srvDtlProfileLts.setCcSrvId(ccSrvID);
					srvMap.put(ccSrvID, srvDtlProfileLts);
				}
				String chargeType = rs.getString("CHRG_TYPE");

				if (StringUtils.contains(chargeType, "R")) {
					srvDtlProfileLts.setSrvNum(rs.getString("SRV_NUM"));
					srvDtlProfileLts.setNgfl3gInd(rs.getString("NGFL_3G_IND"));
					srvDtlProfileLts.setSrvType(rs.getString("SERVICE_TYPE"));
					srvDtlProfileLts.setDatCd(rs.getString("DAT_CD"));
					srvDtlProfileLts.setTariff(rs.getString("TARIFF"));
					srvDtlProfileLts.setDuplexNum(rs.getString("DUPLEX_NUM"));
					srvDtlProfileLts.setTwinLineInd(StringUtils.equals(rs.getString("TWIN_LINE_IND"), "Y"));
					srvDtlProfileLts.setWaiveCount(rs.getString("WAIVE_COUNT"));
					srvDtlProfileLts.setLtsSrvTenure(rs.getDouble("SRV_TENUE"));
					srvDtlProfileLts.setEffEndDate(rs.getString("EFF_END_DATE"));
					srvDtlProfileLts.setEffStartDate(rs.getString("EFF_START_DATE"));
					srvDtlProfileLts.setInventStatus(rs.getString("INVENT_STS_CD"));
						
					AddressDetailProfileLtsDTO address = new AddressDetailProfileLtsDTO();
					address.setAddrId(rs.getString("ADDR_ID"));
					address.setUnitNum(rs.getString("UNIT_NUM"));
					address.setFloorNum(rs.getString("FLOOR_NUM"));
					address.setBuildName(rs.getString("BUILD_NAME"));
					address.setSectDesc(rs.getString("SECTDSC"));
					address.setHlotNum(rs.getString("HLOT_NUM"));
					address.setStreetNum(rs.getString("STREET_NUM"));
					address.setStreetName(rs.getString("STREET_NAME"));
					address.setStreetCat(rs.getString("STCATDSC"));
					address.setDistrict(rs.getString("DISTDSC"));
					address.setArea(rs.getString("AREADSC"));
					address.setSectCd(rs.getString("SECTCD"));
					address.setStreetCatCd(rs.getString("STREET_CAT_CD"));
					address.setDistrictCd(rs.getString("DISTCD"));
					address.setAreaCd(rs.getString("AREACD"));
					address.setSrvBdry(rs.getString("SRVBDRY_NUM"));
					address.setFullAddress(rs.getString("ADDRESS"));
					srvDtlProfileLts.setAddress(address);
					
					CustomerDetailProfileLtsDTO customer = new CustomerDetailProfileLtsDTO();
					customer.setCustNum(rs.getString("CUST_NUM"));
					customer.setDocNum(rs.getString("ID_DOC_NUM"));
					customer.setDocType(rs.getString("ID_DOC_TYPE"));
					customer.setFirstName(rs.getString("CUST_FIRST_NAME"));
					customer.setLastName(rs.getString("CUST_LAST_NAME"));
					customer.setDob(rs.getString("DATE_OF_BIRTH"));
					customer.setCompanyName(rs.getString("COMPANY_NAME"));
					customer.setParentCustNum(rs.getString("PARENT_CUST_NUM"));
					customer.setIdVerifyInd(StringUtils.equals(rs.getString("ID_VERIFY_IND"), "Y"));
					customer.setWipInd(rs.getString("WRITTEN_APPROVAL_REQUIRED"));
					customer.setBlacklistCustInd(StringUtils.equals(rs.getString("BLACKLIST_CUST_IND"), "Y"));
					customer.setTitle(rs.getString("TITLE"));
					srvDtlProfileLts.setPrimaryCust(customer);
				}
				AccountDetailProfileLtsDTO account = new AccountDetailProfileLtsDTO();
				account.setAcctNum(rs.getString("ACCT_NUM"));
				account.setCreditStatus(rs.getString("CREDIT_STATUS"));
				account.setAcctStatus(rs.getString("ACCT_STATUS"));
				account.setAcctChrgType(chargeType);
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
				
				if (StringUtils.contains(chargeType, "R")) {
					account.setPrimaryAcctInd(true);
				}
				List<AccountDetailProfileLtsDTO> accountList = null;
				
				if (acctMap.containsKey(ccSrvID)) {
					accountList = acctMap.get(ccSrvID);
				} else {
					accountList = new ArrayList<AccountDetailProfileLtsDTO>();
					acctMap.put(ccSrvID, accountList);
				}
				accountList.add(account);
				
				CustomerDetailProfileLtsDTO customer = new CustomerDetailProfileLtsDTO();
				customer.setCustNum(rs.getString("CUST_NUM"));
				customer.setDocNum(rs.getString("ID_DOC_NUM"));
				customer.setDocType(rs.getString("ID_DOC_TYPE"));
				customer.setFirstName(rs.getString("CUST_FIRST_NAME"));
				customer.setLastName(rs.getString("CUST_LAST_NAME"));
				customer.setDob(rs.getString("DATE_OF_BIRTH"));
				customer.setParentCustNum(rs.getString("PARENT_CUST_NUM"));
				customer.setIdVerifyInd(StringUtils.equals(rs.getString("ID_VERIFY_IND"), "Y"));
				customer.setWipInd(rs.getString("WRITTEN_APPROVAL_REQUIRED"));
				customer.setBlacklistCustInd(StringUtils.equals(rs.getString("BLACKLIST_CUST_IND"), "Y"));
				customer.setCustCatg(rs.getString("CUST_CATG"));
				customer.setSpecialHandle(StringUtils.equals(rs.getString("SPEC_HANDLE_IND"), "Y"));
				customer.setPremierType(rs.getString("PREMIER_TYPE"));
				account.setCustomerProfile(customer);
				return null;
			}
		};
		try {		
			simpleJdbcTemplate.query(SQL_GET_SRV_PROFILE_BY_CUST_V, mapper, pCustNum, pSrvType);
		} catch (EmptyResultDataAccessException erdae) {
			throw new AppRuntimeException(erdae);
		} catch (Exception e) {
			logger.error("Error in getServiceProfileByCustomer() Cust Num: " +  pCustNum, e);
			throw new DAOException(e.getMessage(), e);
		}
		if (srvMap.size() == 0) {
			return null;
		}
		ServiceDetailProfileLtsDTO[] srvProfiles = srvMap.values().toArray(new ServiceDetailProfileLtsDTO[srvMap.size()]);
		
		for (int i=0; i<srvProfiles.length; ++i) {
			srvProfiles[i].setAccounts(acctMap.get(srvProfiles[i].getCcSrvId()).toArray(new AccountDetailProfileLtsDTO[0]));
		}
		return srvProfiles;
	}
	
	public PendingOrdStatusDetailDTO getPendingOrder(String pSrvNum, String pSrvType) throws DAOException {

		List<PendingOrdStatusDetailDTO> statusList = null;
		
		try {
			statusList = this.simpleJdbcTemplate.query(SQL_GET_PENDING_ORDER_BY_DN, this.getStatusMapper(), pSrvNum, pSrvNum, pSrvType);
		} catch (EmptyResultDataAccessException erdae) {
			return null;
		} catch (Exception e) {
			logger.error("Error in getPendingOrderBySrvNum() DN: " + pSrvNum, e);
			throw new DAOException(e.getMessage(), e);
		}
		if (statusList.size() == 0) {
			return null;
		}
		return statusList.get(0);
	}
	
	private ParameterizedRowMapper<PendingOrdStatusDetailDTO> getStatusMapper() {
		return new ParameterizedRowMapper<PendingOrdStatusDetailDTO>() {
			public PendingOrdStatusDetailDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				PendingOrdStatusDetailDTO status = new PendingOrdStatusDetailDTO();
				status.setBomStatus(rs.getString("BOM_STATUS"));
				status.setDtlId(rs.getString("DTL_ID"));
				status.setLegacyStatus(rs.getString("STATUS"));
				status.setOcid(rs.getString("OC_ID"));
				status.setOrderType(rs.getString("ORD_TYPE"));
				status.setSrd(rs.getString("SRV_REQ_DATE"));
				return status;
			}
		};
	}
	
	public String getServiceMemNum(String pCcSrvId, String pSrvNum) throws DAOException {
		
		try {
			return (String)simpleJdbcTemplate.queryForObject(SQL_GET_SRV_MEM_NUM, String.class, pCcSrvId, pSrvNum);
		} catch (EmptyResultDataAccessException erdae) {
			throw new AppRuntimeException(erdae);
		} catch (Exception e) {
			logger.error("Error in getServiceMemNum() DN: " + pSrvNum, e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public ServiceGroupProfileDTO getEyeGrp(String pCcSrvId) throws DAOException {
		
		final ServiceGroupProfileDTO srvGrp = new ServiceGroupProfileDTO();
		
		ParameterizedRowMapper<ServiceGroupMemberProfileDTO> mapper = new ParameterizedRowMapper<ServiceGroupMemberProfileDTO>() {
			public ServiceGroupMemberProfileDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				
				if (srvGrp.getGrpId() == null) {
					srvGrp.setGrpId(rs.getString("SRV_GRP_NUM"));
					srvGrp.setGrpType(rs.getString("SRV_GRP_TYPE"));
				}
				ServiceGroupMemberProfileDTO member = new ServiceGroupMemberProfileDTO();
				member.setCcSrvId(rs.getString("SERVICE_ID"));
				member.setDatCd(rs.getString("DAT_CD"));
				member.setSrvNum(rs.getString("SRV_NUM"));
				srvGrp.appendGrpMemeber(member);
				return null;
			}
		};
		try {
			this.simpleJdbcTemplate.query(SQL_GET_EYE_GRP, mapper, pCcSrvId);
		} catch (EmptyResultDataAccessException erdae) {
			return null;
		} catch (Exception e) {
			logger.error("Error in getEyeGrp()", e);
			throw new DAOException(e.getMessage(), e);
		}
		if (srvGrp.getGrpId() == null) {
			return null;
		}
		return srvGrp;
	}
	
	public ServiceGroupProfileDTO getTerminatedSrvEyeGrp(String pCcSrvId) throws DAOException {
		
		final ServiceGroupProfileDTO srvGrp = new ServiceGroupProfileDTO();
		
		ParameterizedRowMapper<ServiceGroupMemberProfileDTO> mapper = new ParameterizedRowMapper<ServiceGroupMemberProfileDTO>() {
			public ServiceGroupMemberProfileDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				
				if (srvGrp.getGrpId() == null) {
					srvGrp.setGrpId(rs.getString("SRV_GRP_NUM"));
					srvGrp.setGrpType(rs.getString("SRV_GRP_TYPE"));
				}
				ServiceGroupMemberProfileDTO member = new ServiceGroupMemberProfileDTO();
				member.setCcSrvId(rs.getString("SERVICE_ID"));
				member.setDatCd(rs.getString("DAT_CD"));
				member.setSrvNum(rs.getString("SRV_NUM"));
				srvGrp.appendGrpMemeber(member);
				return null;
			}
		};
		try {
			this.simpleJdbcTemplate.query(SQL_GET_TERM_SRV_EYE_GRP, mapper, pCcSrvId);
		} catch (EmptyResultDataAccessException erdae) {
			return null;
		} catch (Exception e) {
			logger.error("Error in getTerminatedSrvEyeGrp()", e);
			throw new DAOException(e.getMessage(), e);
		}
		if (srvGrp.getGrpId() == null) {
			return null;
		}
		return srvGrp;
	}
	
	public ServiceDetailProfileLtsDTO getSimpleServiceProfile(String pCcSrvId, String pSystemId) throws DAOException {
		
		try {
			List<ServiceDetailProfileLtsDTO> srvList = this.simpleJdbcTemplate.query(SQL_GET_SIMPLE_SRV_PROFILE, this.getServiceMapper(), pCcSrvId, pSystemId);
			
			if (srvList == null || srvList.size() == 0) {
				return null;
			}
			return srvList.get(0);
		} catch (EmptyResultDataAccessException erdae) {
			return null;
		} catch (Exception e) {
			logger.error("Error in getSimpleServiceProfile()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public ServiceDetailProfileLtsDTO getTerminatedServiceProfile(String pCcSrvId, String pSystemId) throws DAOException {
		
		try {
			List<ServiceDetailProfileLtsDTO> srvList = this.simpleJdbcTemplate.query(SQL_GET_TERM_SRV_PROFILE, this.getServiceMapper(), pCcSrvId, pSystemId);
			
			if (srvList == null || srvList.size() == 0) {
				return null;
			}
			return srvList.get(0);
		} catch (EmptyResultDataAccessException erdae) {
			return null;
		} catch (Exception e) {
			logger.error("Error in getTerminatedServiceProfile()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public AccountServiceLtsDTO[] getServiceAccoutAssgn(String pCcSrvId, String pSystemId) throws DAOException {
		
		ParameterizedRowMapper<AccountServiceLtsDTO> mapper = new ParameterizedRowMapper<AccountServiceLtsDTO>() {
			public AccountServiceLtsDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				AccountServiceLtsDTO accctSrv = new AccountServiceLtsDTO();
				accctSrv.setAcctNum(rs.getString("ACCT_NUM"));
				accctSrv.setAcctType(rs.getString("ACCT_TYPE"));
				accctSrv.setChargeType(rs.getString("CHRG_TYPE"));
				accctSrv.setAcctName(rs.getString("ACCT_NAME"));
				accctSrv.setCustNum(rs.getString("CUST_NUM"));
				accctSrv.setEffEndStart(rs.getString("EFF_END_DATE"));
				accctSrv.setEffStartDate(rs.getString("EFF_START_DATE"));
				return accctSrv;
			}
		};		
		try {
			List<AccountServiceLtsDTO> acctSrvList = this.simpleJdbcTemplate.query(SQL_GET_ACCT_BY_SRV, mapper, pCcSrvId, pSystemId);
			
			if (acctSrvList == null || acctSrvList.size() == 0) {
				return null;
			}
			return acctSrvList.toArray(new AccountServiceLtsDTO[acctSrvList.size()]);
		} catch (EmptyResultDataAccessException erdae) {
			return null;
		} catch (Exception e) {
			logger.error("Error in getServiceAccoutAssgn()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	private ParameterizedRowMapper<ServiceDetailProfileLtsDTO> getServiceMapper() {
		return new ParameterizedRowMapper<ServiceDetailProfileLtsDTO>() {
			public ServiceDetailProfileLtsDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				
				ServiceDetailProfileLtsDTO srvDtlProfileLts = new ServiceDetailProfileLtsDTO();
				srvDtlProfileLts.setCcSrvId(rs.getString("SERVICE_ID"));
				srvDtlProfileLts.setSrvNum(rs.getString("SRV_NUM"));
				srvDtlProfileLts.setNgfl3gInd(rs.getString("NGFL_3G_IND"));
				srvDtlProfileLts.setSrvType(rs.getString("SERVICE_TYPE"));
				srvDtlProfileLts.setDatCd(rs.getString("DAT_CD"));
				srvDtlProfileLts.setTariff(rs.getString("TARIFF"));
				srvDtlProfileLts.setDuplexNum(rs.getString("DUPLEX_NUM"));
				srvDtlProfileLts.setTwinLineInd(StringUtils.equals(rs.getString("TWIN_LINE_IND"), "Y"));
				srvDtlProfileLts.setWaiveCount(rs.getString("WAIVE_COUNT"));
				srvDtlProfileLts.setLtsSrvTenure(rs.getDouble("SRV_TENUE"));
				srvDtlProfileLts.setEffEndDate(rs.getString("EFF_END_DATE"));
				srvDtlProfileLts.setEffStartDate(rs.getString("EFF_START_DATE"));
				srvDtlProfileLts.setInventStatus(rs.getString("INVENT_STS_CD"));
				
				AddressDetailProfileLtsDTO address = new AddressDetailProfileLtsDTO();
				address.setAddrId(rs.getString("ADDR_ID"));
				address.setUnitNum(rs.getString("UNIT_NUM"));
				address.setFloorNum(rs.getString("FLOOR_NUM"));
				address.setBuildName(rs.getString("BUILD_NAME"));
				address.setSectDesc(rs.getString("SECTDSC"));
				address.setHlotNum(rs.getString("HLOT_NUM"));
				address.setStreetNum(rs.getString("STREET_NUM"));
				address.setStreetName(rs.getString("STREET_NAME"));
				address.setStreetCat(rs.getString("STCATDSC"));
				address.setDistrict(rs.getString("DISTDSC"));
				address.setArea(rs.getString("AREADSC"));
				address.setSectCd(rs.getString("SECTCD"));
				address.setStreetCatCd(rs.getString("STREET_CAT_CD"));
				address.setDistrictCd(rs.getString("DISTCD"));
				address.setAreaCd(rs.getString("AREACD"));
				address.setSrvBdry(rs.getString("SRVBDRY_NUM"));
				address.setFullAddress(rs.getString("ADDRESS"));
				srvDtlProfileLts.setAddress(address);	
				return srvDtlProfileLts;
			}
		};
	}
}
