package com.bomwebportal.lts.dao.bom;

import org.apache.commons.lang.StringUtils;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import com.bomwebportal.dao.BaseDAO;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.lts.util.LtsBackendConstant;
import com.bomwebportal.lts.util.LtsProfileHelper;

public class EyeProfileCountDAO extends BaseDAO {
	
	private static final String SQL_GET_EYE_PROFILE_COUNT_BY_CUST = 
			"select count(*) from " +
			"b_customer cust, " +
			"b_account acct, " +
			"b_account_service_assgn acct_assgn, " +
			"b_service srv, " +
			"b_subc_compt compt " +
			"where cust.system_id = :systemId " +
			"and cust.id_doc_type = :docType " +
			"and cust.id_doc_num = :docNum " +
			"and cust.cust_num = acct.CUST_NUM " +
			"and cust.system_id = acct.SYSTEM_ID " +
			"and trunc(acct.EFF_START_DATE) <= trunc(SYSDATE) " +
			"and (trunc(acct.EFF_END_DATE) > trunc(SYSDATE) OR acct.EFF_END_DATE IS NULL) " +
			"and acct.ACCT_NUM = acct_assgn.ACCT_NUM " +
			"and acct.system_id = acct_assgn.system_id " +
			"and trunc(acct_assgn.EFF_START_DATE) <= trunc(SYSDATE) " +
			"and (trunc(acct_assgn.EFF_END_DATE) > trunc(SYSDATE) OR acct_assgn.EFF_END_DATE IS NULL)" +
			"and acct_assgn.service_id = srv.SERVICE_ID " +
			"and acct_assgn.system_id = srv.SYSTEM_ID " +
			"and trunc(srv.EFF_START_DATE) <= trunc(SYSDATE) " +
			"and (trunc(srv.EFF_END_DATE) > trunc(SYSDATE) OR srv.EFF_END_DATE IS NULL) " +
			"and compt.SUBSCRIBER_NUM = srv.SERVICE_ID " +
			"and compt.SUBSCRIBER_SYSTEM_ID =  srv.system_id " +
			"and compt.PSEF_CD IN (SELECT bom_code from B_lookup where bom_grp_id = 'EYE3A_FEAT_CD') " +
			"and trunc(compt.EFF_START_DATE) <= trunc(SYSDATE) " +
			"and (trunc(compt.EFF_END_DATE) > trunc(SYSDATE) OR compt.EFF_END_DATE IS NULL) " +
			"and acct_assgn.CHRG_TYPE LIKE '%R%' ";

	private static final String SQL_GET_EYE_PROFILE_COUNT_BY_ADDR = 
			"select count(*) from " +
			"b_address_dtl addr, " +
			"b_service srv, " +
			"b_subc_compt compt " +
			"where (srvbdry_num = :sb1 or srvbdry_num = :sb2)" + 
			"and addr.addr_id = srv.install_addr " +
			"and trunc(srv.EFF_START_DATE) <= trunc(SYSDATE) " +
			"and (trunc(srv.EFF_END_DATE) > trunc(SYSDATE) OR SRV.EFF_END_DATE IS NULL) " +
			"and srv.system_id = :systemId " +
			"and compt.SUBSCRIBER_NUM = srv.SERVICE_ID " +
			"and compt.SUBSCRIBER_SYSTEM_ID = srv.system_id " +
			"and compt.psef_cd IN (SELECT bom_code from B_lookup where bom_grp_id = 'EYE3A_FEAT_CD') " +
			"and trunc(compt.EFF_START_DATE) <= trunc(SYSDATE) " +
			"and (trunc(compt.EFF_END_DATE) > trunc(SYSDATE) OR compt.EFF_END_DATE IS NULL) ";
	
	
	public int getEyeProfileCountByCust(String pDocType, String pDocNum) throws DAOException {
		try {
			MapSqlParameterSource paramSource = new MapSqlParameterSource();
			paramSource.addValue("systemId", LtsBackendConstant.SYSTEM_ID_DRG);
			paramSource.addValue("docType", pDocType);
			paramSource.addValue("docNum", pDocNum);
			
			return this.simpleJdbcTemplate.queryForInt(SQL_GET_EYE_PROFILE_COUNT_BY_CUST, paramSource);
		} catch (EmptyResultDataAccessException erdae) {
			return 0;
		} catch (Exception e) {
			logger.error("Error in getRenewalDeviceOfferIdList()", e);
			throw new DAOException(e.getMessage(), e);
		}
		
	}
	

	public int getEyeProfileCountByAddr(String pFlat, String pFloor, String pSrvBdry) throws DAOException {
		try {
			MapSqlParameterSource paramSource = new MapSqlParameterSource();
			StringBuilder sb = new StringBuilder(SQL_GET_EYE_PROFILE_COUNT_BY_ADDR);
			if(StringUtils.isBlank(pFlat)){
				sb.append("and addr.unit_num is null ");
			}else{
				sb.append("and ltrim(addr.unit_num, '0') = :flat ");
				paramSource.addValue("flat", pFlat);
			}
			if(StringUtils.isBlank(pFloor)){
				sb.append("and addr.floor_num is null ");
			}else{
				sb.append("and ltrim(addr.floor_num, '0') = :floor ");
				paramSource.addValue("floor", pFloor);
			}
			String[] srvBdrys = LtsProfileHelper.reformatSrvBoundary(pSrvBdry);
			paramSource.addValue("sb1", srvBdrys[0]);
			paramSource.addValue("sb2", srvBdrys[1]);
			paramSource.addValue("systemId", LtsBackendConstant.SYSTEM_ID_DRG);
			
			return this.simpleJdbcTemplate.queryForInt(sb.toString(), paramSource);
		} catch (EmptyResultDataAccessException erdae) {
			return 0;
		} catch (Exception e) {
			logger.error("Error in getRenewalDeviceOfferIdList()", e);
			throw new DAOException(e.getMessage(), e);
		}
		
	}
	
}
			