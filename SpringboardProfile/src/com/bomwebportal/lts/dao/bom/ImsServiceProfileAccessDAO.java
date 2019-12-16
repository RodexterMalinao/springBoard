package com.bomwebportal.lts.dao.bom;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import oracle.jdbc.OracleTypes;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;

import com.bomwebportal.dao.BaseDAO;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.lts.dto.profile.AddressDetailProfileLtsDTO;
import com.bomwebportal.lts.dto.profile.CustomerDetailProfileLtsDTO;
import com.bomwebportal.lts.dto.profile.PendingOrdStatusDetailDTO;
import com.bomwebportal.lts.dto.profile.TenureDTO;
import com.bomwebportal.lts.dto.srvAccess.AddrRolloutDTO;
import com.bomwebportal.lts.dto.srvAccess.BandwidthDTO;
import com.bomwebportal.lts.dto.srvAccess.FsaServiceDetailOutputDTO;
import com.bomwebportal.lts.dto.srvAccess.TechnologyDTO;
import com.bomwebportal.lts.dto.srvAccess.UimBlockageDTO;

public class ImsServiceProfileAccessDAO extends BaseDAO{

	private final Log logger = LogFactory.getLog(getClass());
	
	private static final String SQL_GET_IMS_CUST_DTL_BY_FSA = 
			"select bc.id_doc_num, bc.id_doc_type, bc.cust_first_name, bc.cust_last_name " +
			"from b_customer bc, b_account ba, b_account_service_assgn bas " +
			"where bas.acct_num = ba.acct_num  and bas.system_id = ba.system_id " +
			"and ba.cust_num = bc.cust_num  and bas.system_id = bc.system_id " +
			"and bas.eff_start_date <= sysdate and (bas.eff_end_date is null or bas.eff_end_date > sysdate) " +
			"and ba.eff_start_date <= sysdate and (ba.eff_end_date is null or ba.eff_end_date > sysdate) " +
			"and ba.acct_type = 'N' " +
			"and bas.system_id = 'IMS' " +
			"and bas.service_id = ?";
	
	private static final String SQL_GET_PEND_ORDER = 
			"select os.oc_id, os.dtl_id, ols.bom_status, ols.status, to_char(od.srv_req_date, 'dd/mm/yyyy') as srv_req_date, od.ord_type " +
			"from b_ord_srv os, b_ord_latest_status ols, b_ord_dtl od " + 
			"where os.oc_id = ols.oc_id " + 
			"and os.oc_id = od.oc_id " +
			"and os.dtl_id = ols.dtl_id " +
			"and os.dtl_id = od.dtl_id " +
			"and nvl(ols.status,'00') <> '03' and nvl(ols.status,'00') <> '06' and nvl(ols.bom_status,'00') <> '07' " + 
			"and od.type_of_srv = 'IMS' " + 
			"and os.srv_id = ?";
	
	private static final String SQL_GET_IMS_CUST_DOC_BY_PARENT_CUST = 
			"SELECT id_doc_type, id_doc_num FROM b_customer " +
			"WHERE parent_cust_num = ? " +
			"AND system_id = 'IMS'";
	

	private static final String SQL_GET_IMS_SRV_TENURE_BY_ADDR_ID = 
			"SELECT " +
			" (SELECT DISTINCT bc.parent_cust_num " +
			"    FROM B_ACCOUNT_SERVICE_ASSGN bas, B_ACCOUNT ba, B_CUSTOMER bc " +
			"    WHERE bas.service_id = a.service_id " +
			"    AND bas.acct_num = ba.acct_num " +
			"    AND ba.cust_num = bc.cust_num " +
			"    AND bas.eff_end_date IS NULL) AS BOM_CUST_NUM, " +
			" a.service_id AS SERVICE_ID, " +
			" MONTHS_BETWEEN(TRUNC(SYSDATE, 'MONTH'), TRUNC(a.eff_start_date, 'MONTH')) AS TENURE " +
			"FROM B_SERVICE a " +
			"WHERE install_Addr = :addrId " +
			"AND system_id = 'IMS' " +
			"AND eff_end_date IS NULL ";
	
	
	
	private static final String SQL_GET_IMS_ADDR_ID_BY_SB_FLT_FLR = 
			" SELECT addrid " +			
			" FROM address " +
			" WHERE serbdyno = :in_sb " +
			" AND NVL(:in_floor,' ') = NVL(ltrim(floornb, '0'),' ') " +
			" AND NVL(:in_flat,' ') = NVL(ltrim(unitnb, '0'),' ')";
     
	
	public String[] getImsCustDocByParentCust(String pParentCustNum) throws DAOException {
		try{
			List<String[]> results = simpleJdbcTemplate.query(SQL_GET_IMS_CUST_DOC_BY_PARENT_CUST, getImsCustDocMapper(), pParentCustNum);
			return results.get(0);
		} catch (EmptyResultDataAccessException erdae) {
			return new String[2];
		} catch (Exception e) {
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public List<UimBlockageDTO> getUimBlockageList(String flat, String floor, String sb) throws DAOException {
		
		StringBuilder sql = new StringBuilder(); 
		MapSqlParameterSource mapSqlSource = new MapSqlParameterSource();
		
		sql.append("SELECT UNITNB, FLOORNB, SERBDYNO, ");
		sql.append(" BLOCKAGE_CODE, BLOCKAGE_DESC, TO_CHAR(BLOCKAGE_DATE, 'yyyy/MM/dd') BLOCKAGE_DATE ");
		sql.append(" FROM B_UIM_BLOCKAGE ");
		sql.append(" WHERE SERBDYNO = :sb ");
		mapSqlSource.addValue("sb", StringUtils.leftPad(sb, 6, "0"));
		
		if (StringUtils.isNotEmpty(floor)) {
			sql.append(" AND FLOORNB = :floor");
			mapSqlSource.addValue("floor", floor);
		}
		if (StringUtils.isNotEmpty(flat)) {
			sql.append(" AND UNITNB = :flat");
			mapSqlSource.addValue("flat", flat);
		}
			
		try {
			return simpleJdbcTemplate.query(sql.toString(), this.getUimBlockageMapper(), mapSqlSource);
		} catch (EmptyResultDataAccessException erdae) {
			return null;
		} catch (Exception e) {
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	//TODO
	public TenureDTO[] getImsTenureByAddress(String pFlat, String pFloor, String pSb) throws DAOException {
		
		String[] addIds = this.getAddridByAddress(pFlat, pFloor, pSb);
		List<TenureDTO> tenureList = new ArrayList<TenureDTO>();
		TenureDTO[] tenures = null;
		
		for (int i=0; addIds!=null && i<addIds.length; ++i) {
			tenures = this.getImsSrvTenureByAddrid(addIds[i]);
			if (ArrayUtils.isEmpty(tenures)) {
				continue;
			}
			tenureList.addAll(Arrays.asList(tenures));
		}
		return tenureList.toArray(new TenureDTO[0]);
	}
	
	private TenureDTO[] getImsSrvTenureByAddrid(String pAddrid) throws DAOException {
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue("addrId", pAddrid);
		
		ParameterizedRowMapper<TenureDTO> mapper = new ParameterizedRowMapper<TenureDTO>() {
			public TenureDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				TenureDTO tenure = new TenureDTO();
				tenure.setCustNum(rs.getString("BOM_CUST_NUM"));
				tenure.setServiceId(rs.getString("SERVICE_ID"));
				tenure.setTenure(rs.getInt("TENURE"));
				return tenure;
			}
		};
		
		try{
			List<TenureDTO> tenureList = simpleJdbcTemplate.query(SQL_GET_IMS_SRV_TENURE_BY_ADDR_ID, mapper, paramSource);
			
			if(tenureList == null || tenureList.size() == 0){
				return null;
			}
			return tenureList.toArray(new TenureDTO[0]);
		}catch (Exception e) {
			logger.error("Error in getImsSrvTenureByAddrid() pAddrid: " + pAddrid, e);
			throw new DAOException(e.getMessage(), e);
		}
		
	}
	
	@SuppressWarnings("unchecked")
	public FsaServiceDetailOutputDTO[] getServiceByCustomer(String pCustNum) throws DAOException {
		
		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
				.withSchemaName("OPS$BOM")
				.withCatalogName("B_IMS_API_PKG")
				.withProcedureName("getServiceByCustomer")
				.declareParameters(
						new SqlParameter("(in_cust_num", Types.VARCHAR),
						new SqlOutParameter("out_list_servicedtl_rslt", OracleTypes.CURSOR, new ImsServiceProfileAccessMapper()),
						new SqlOutParameter("out_error_code", Types.VARCHAR),
						new SqlOutParameter("out_error_desc", Types.VARCHAR));
		jdbcCall.setAccessCallParameterMetaData(false);
		MapSqlParameterSource inMap = new MapSqlParameterSource();
		inMap.addValue("in_cust_num", pCustNum);	
		Map<String, Object> out = null;
		
		try {
			out = jdbcCall.execute(inMap);
		} catch (Exception e) {
			logger.error("Exception caught in getServiceByCustomer()", e);
			throw new DAOException(e.getMessage(), e);
		}
		List<FsaServiceDetailOutputDTO> fsaSrvList = (List<FsaServiceDetailOutputDTO>)out.get("out_list_servicedtl_rslt");
		FsaServiceDetailOutputDTO fsaServiceDetailOutputDTO = new FsaServiceDetailOutputDTO();
		
		if (StringUtils.equals((String) out.get("out_error_code"), "0") == false) {
			fsaServiceDetailOutputDTO.setErrorCode((String) out.get("out_error_code"));
			fsaServiceDetailOutputDTO.setErrorDesc((String) out.get("out_error_desc"));
			fsaSrvList.add(fsaServiceDetailOutputDTO);
		} else if (fsaSrvList.size() > 0) {
			for (int i = 0; i < fsaSrvList.size(); ++i) {
				fsaSrvList.get(i).setAddressDtl(this.getAddressDTLByAddressID(fsaSrvList.get(i).getAddressID()));
				if (fsaSrvList.get(i).getAddressDtl() == null) {
					fsaSrvList.get(i).setErrorDesc("AddressDtl is empty");
					fsaSrvList.get(i).setErrorCode("-1");
				}
			}
		}
		return fsaSrvList.toArray(new FsaServiceDetailOutputDTO[0]);
	}
	
	@SuppressWarnings("unchecked")
	public FsaServiceDetailOutputDTO getServiceDetailByFSA(String pFsa) throws DAOException {
		
		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
				.withSchemaName("OPS$BOM")
				.withCatalogName("B_IMS_API_PKG")
				.withProcedureName("getServiceDetailByFSA")
				.declareParameters(
						new SqlParameter("in_fsa", Types.VARCHAR),
						new SqlOutParameter("out_list_servicedtl_rslt", OracleTypes.CURSOR, new ImsServiceProfileAccessMapper()),
						new SqlOutParameter("out_error_code", Types.VARCHAR),
						new SqlOutParameter("out_error_desc", Types.VARCHAR));	
		jdbcCall.setAccessCallParameterMetaData(false);
		MapSqlParameterSource inMap = new MapSqlParameterSource();
		inMap.addValue("in_fsa", pFsa);
		Map<String, Object> out = null;
		
		try {
			out = jdbcCall.execute(inMap);
		} catch (Exception e) {
			logger.error("Exception caught in getServiceDetailByFSA()", e);
			throw new DAOException(e.getMessage(), e);
		}
		List<FsaServiceDetailOutputDTO> fsaSrvList = (List<FsaServiceDetailOutputDTO>)out.get("out_list_servicedtl_rslt");
		FsaServiceDetailOutputDTO fsaServiceDetailOutputDTO = new FsaServiceDetailOutputDTO();
		
		if (StringUtils.equals((String) out.get("out_error_code"), "0") == false) {
			fsaServiceDetailOutputDTO.setErrorCode((String) out.get("out_error_code"));
			fsaServiceDetailOutputDTO.setErrorDesc((String) out.get("out_error_desc"));
		} else if (fsaSrvList.size() > 0) {
			fsaSrvList.get(0).setAddressDtl(this.getAddressDTLByAddressID(fsaSrvList.get(0).getAddressID()));
			if (fsaSrvList.get(0).getAddressDtl() == null) {
				fsaSrvList.get(0).setErrorDesc("AddressDtl is empty");
				fsaSrvList.get(0).setErrorCode("-1");
			}
			fsaServiceDetailOutputDTO = fsaSrvList.get(0);
		} else if (fsaSrvList.size() == 0) {
			fsaServiceDetailOutputDTO = null;
		}
		return fsaServiceDetailOutputDTO;	
	}
	
	@SuppressWarnings("unchecked")
	public FsaServiceDetailOutputDTO[] getServiceByDocument(String pDocType, String pDocNum) throws DAOException {
		
		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
				.withSchemaName("OPS$BOM")
				.withCatalogName("B_IMS_API_PKG")
				.withProcedureName("getServiceByDocument")
				.declareParameters(
						new SqlParameter("in_doc_type", Types.VARCHAR),
						new SqlParameter("in_doc_num", Types.VARCHAR),
						new SqlOutParameter("out_list_servicedtl_rslt", OracleTypes.CURSOR, new ImsServiceProfileAccessMapper()),
						new SqlOutParameter("out_error_code", Types.VARCHAR),
						new SqlOutParameter("out_error_desc", Types.VARCHAR));
		jdbcCall.setAccessCallParameterMetaData(false);
		MapSqlParameterSource inMap = new MapSqlParameterSource();
		inMap.addValue("in_doc_type", pDocType);
		inMap.addValue("in_doc_num", pDocNum);	
		Map<String, Object> out = null;
		
		try {
			out = jdbcCall.execute(inMap);
		} catch (Exception e) {
			logger.error("Exception caught in getServiceByDocument()", e);
			throw new DAOException(e.getMessage(), e);
		}
		List<FsaServiceDetailOutputDTO> fsaSrvList = (List<FsaServiceDetailOutputDTO>)out.get("out_list_servicedtl_rslt");
		FsaServiceDetailOutputDTO fsaServiceDetailOutputDTO = new FsaServiceDetailOutputDTO();
		
		if (StringUtils.equals((String) out.get("out_error_code"), "0") == false) {
			fsaServiceDetailOutputDTO.setErrorCode((String) out.get("out_error_code"));
			fsaServiceDetailOutputDTO.setErrorDesc((String) out.get("out_error_desc"));
			fsaSrvList.add(fsaServiceDetailOutputDTO);
		} else if (fsaSrvList.size() > 0) {
			for (int i = 0; i < fsaSrvList.size(); ++i) {
				fsaSrvList.get(i).setAddressDtl(this.getAddressDTLByAddressID(fsaSrvList.get(i).getAddressID()));
				if (fsaSrvList.get(i).getAddressDtl() == null) {
					fsaSrvList.get(i).setErrorDesc("AddressDtl is empty");
					fsaSrvList.get(i).setErrorCode("-1");
				}
			}
		}
		return fsaSrvList.toArray(new FsaServiceDetailOutputDTO[0]);
	}
	
	@SuppressWarnings("unchecked")
	public FsaServiceDetailOutputDTO[] getServiceByLogin(String pLogin, String pDomainType) throws DAOException {
		
		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
				.withSchemaName("OPS$BOM")
				.withCatalogName("B_IMS_API_PKG")
				.withProcedureName("getServiceByLogin")
				.declareParameters(
						new SqlParameter("in_login", Types.VARCHAR),
						new SqlParameter("in_domain_type", Types.VARCHAR),
						new SqlOutParameter("out_list_servicedtl_rslt", OracleTypes.CURSOR, new ImsServiceProfileAccessMapper()),
						new SqlOutParameter("out_error_code", Types.VARCHAR),
						new SqlOutParameter("out_error_desc", Types.VARCHAR));
		jdbcCall.setAccessCallParameterMetaData(false);
		MapSqlParameterSource inMap = new MapSqlParameterSource();
		inMap.addValue("in_login", pLogin);	
		inMap.addValue("in_domain_type", pDomainType);	
		Map<String, Object> out = null;
		
		try {
			out = jdbcCall.execute(inMap);
		} catch (Exception e) {
			logger.error("Exception caught in getServiceByLogin()", e);
			throw new DAOException(e.getMessage(), e);
		}
		List<FsaServiceDetailOutputDTO> fsaSrvList = (List<FsaServiceDetailOutputDTO>)out.get("out_list_servicedtl_rslt");
		FsaServiceDetailOutputDTO fsaServiceDetailOutputDTO = new FsaServiceDetailOutputDTO();
		
		if (StringUtils.equals((String) out.get("out_error_code"), "0") == false) {
			fsaServiceDetailOutputDTO.setErrorCode((String) out.get("out_error_code"));
			fsaServiceDetailOutputDTO.setErrorDesc((String) out.get("out_error_desc"));
			fsaSrvList.add(fsaServiceDetailOutputDTO);
		} else if (fsaSrvList.size() > 0) {
			for (int i = 0; i < fsaSrvList.size(); ++i) {
				fsaSrvList.get(i).setAddressDtl(this.getAddressDTLByAddressID(fsaSrvList.get(i).getAddressID()));
				if (fsaSrvList.get(i).getAddressDtl() == null) {
					fsaSrvList.get(i).setErrorDesc("AddressDtl is empty");
					fsaSrvList.get(i).setErrorCode("-1");
				}
			}
		}
		return fsaSrvList.toArray(new FsaServiceDetailOutputDTO[0]);
	}
	
	@SuppressWarnings("unchecked")
	public AddressDetailProfileLtsDTO getAddressDTLByAddressID(String pAddress) throws DAOException {
		
		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
				.withSchemaName("OPS$BOM")
				.withCatalogName("B_IMS_API_PKG")
				.withProcedureName("getAddressDTLByAddressID")
				.declareParameters(
						new SqlParameter("in_address", Types.VARCHAR),
						new SqlOutParameter("out_list_address_rslt", OracleTypes.CURSOR, new ImsServiceAddressMapper()),
						new SqlOutParameter("out_error_code", Types.VARCHAR),
						new SqlOutParameter("out_error_desc", Types.VARCHAR));
		jdbcCall.setAccessCallParameterMetaData(false);
		MapSqlParameterSource inMap = new MapSqlParameterSource();
		inMap.addValue("in_address", pAddress);	
		
		Map<String, Object> out = null;
		try {
			out = jdbcCall.execute(inMap);
		} catch (Exception e) {
			logger.error("Exception caught in getAddressDTLByAddressID()", e);
			throw new DAOException(e.getMessage(), e);
		}
		List<AddressDetailProfileLtsDTO> addrDtlList = (List<AddressDetailProfileLtsDTO>)out.get("out_list_address_rslt");
		AddressDetailProfileLtsDTO addressDetailProfileLtsDTO = new AddressDetailProfileLtsDTO();
		
		if (StringUtils.equals((String)out.get("out_error_code"), "0") && addrDtlList.size() > 0) {
			addressDetailProfileLtsDTO=addrDtlList.get(0);
		} else {
			addressDetailProfileLtsDTO=null;
		}
		return addressDetailProfileLtsDTO;
	}

	// edited by Markball on 25-02-2016
	// procedure is changed from "get_addr_rollout_dtl" to "get_addr_rollout_dtl_lts"
	public AddrRolloutDTO getAddrRolloutDtl(String pIFlat, String pIFloor, String pIServiceBoundary) throws DAOException {
		
		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
				.withSchemaName("OPS$BOM")
				.withCatalogName("PKG_OC_IMS_SB")
				.withProcedureName("get_addr_rollout_dtl_lts")
				.declareParameters(
						new SqlParameter("i_flat", Types.VARCHAR),
						new SqlParameter("i_floor", Types.VARCHAR),
						new SqlParameter("i_service_boundary", Types.VARCHAR),
						new SqlOutParameter("o_cover_eyex", Types.VARCHAR),
						new SqlOutParameter("o_cover_pe", Types.VARCHAR),
						new SqlOutParameter("o_housing_type", Types.VARCHAR),
						new SqlOutParameter("o_housing_type_cd", Types.VARCHAR),
						new SqlOutParameter("o_housing_type_desc", Types.VARCHAR),
						new SqlOutParameter("o_n2_building", Types.VARCHAR),
						new SqlOutParameter("o_field_permit_day", Types.INTEGER),
						new SqlOutParameter("o_is_fiber_blockage", Types.VARCHAR),
						new SqlOutParameter("o_is_fttc_ind", Types.VARCHAR),
						new SqlOutParameter("o_is_blacklist_addr", Types.VARCHAR),
						new SqlParameter("technology_cursor", OracleTypes.CURSOR),
						new SqlOutParameter("technology_cursor", OracleTypes.CURSOR, new AddrRolloutDtlMapper()),
						new SqlOutParameter("o_pon_villa_ind", Types.VARCHAR),
						new SqlOutParameter("o_addr_tag_cd", Types.VARCHAR),
						new SqlOutParameter("gnRetVal", Types.INTEGER),
						new SqlOutParameter("gnErrCode", Types.INTEGER),
						new SqlOutParameter("gsErrText", Types.VARCHAR));
		jdbcCall.setAccessCallParameterMetaData(false);
		MapSqlParameterSource inMap = new MapSqlParameterSource();
		inMap.addValue("i_flat", pIFlat);	
		inMap.addValue("i_floor", pIFloor);
		inMap.addValue("i_service_boundary", pIServiceBoundary);
		inMap.addValue("technology_cursor", null);
		Map<String, Object> out = null;
		
		try {
			out = jdbcCall.execute(inMap);
		} catch (Exception e) {
			logger.error("Exception caught in getAddrRolloutDtl()", e);
			throw new DAOException(e.getMessage(), e);
		}
		AddrRolloutDTO addrRolloutDTO = new AddrRolloutDTO();
		addrRolloutDTO.setGnRetVal(out.get("gnRetVal").toString());
		addrRolloutDTO.setGnErrCode(out.get("gnErrCode").toString());
		addrRolloutDTO.setGsErrText((String)out.get("gsErrText"));
		addrRolloutDTO.setoCoverEyex((String)out.get("o_cover_eyex"));
		addrRolloutDTO.setoCoverPe((String)out.get("o_cover_pe"));
		//addrRolloutDTO.setoIsPh((String)out.get("o_is_ph"));
		addrRolloutDTO.setoHousingType((String)out.get("o_housing_type"));
		addrRolloutDTO.setoHousingTypeCd((String)out.get("o_housing_type_cd"));
		addrRolloutDTO.setoHousingTypeDesc((String)out.get("o_housing_type_desc"));
		
		addrRolloutDTO.setoN2Building((String)out.get("o_n2_building"));
		
		if (out.get("o_field_permit_day") != null) {
			addrRolloutDTO.setoFieldPermitDay(out.get("o_field_permit_day").toString());
		} else {
			addrRolloutDTO.setoFieldPermitDay("0");
		}
		addrRolloutDTO.setoIsFiberBlockage((String)out.get("o_is_fiber_blockage"));
		addrRolloutDTO.setoIsFttcInd((String)out.get("o_is_fttc_ind"));
		addrRolloutDTO.setoIsBlacklistAddr((String)out.get("o_is_blacklist_addr"));
		addrRolloutDTO.setoPonVillaInd((String)out.get("o_pon_villa_ind"));
		addrRolloutDTO.setoAddrTagCd((String)out.get("o_addr_tag_cd"));
		
		@SuppressWarnings("unchecked")
		List<TechnologyDTO> technologyList = (List<TechnologyDTO>)out.get("technology_cursor");
		addrRolloutDTO.setTechnologyDTO(technologyList.toArray(new TechnologyDTO[technologyList.size()]));
		BandwidthDTO[] bandwidth=new BandwidthDTO[addrRolloutDTO.getTechnologyDTO().length];
		
		for (int i = 0; i < addrRolloutDTO.getTechnologyDTO().length; i++) {
        	bandwidth[i] = getBandwidthByTech(addrRolloutDTO.getTechnologyDTO()[i].getTechnology(),pIServiceBoundary);
        	addrRolloutDTO.getTechnologyDTO()[i].setBandwidthDTO(bandwidth[i]);
		}
		return addrRolloutDTO;
	}

	public BandwidthDTO getBandwidthByTech(String pITechnology, String pIServiceBoundary) throws DAOException {
		
		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
				.withSchemaName("OPS$BOM")
				.withCatalogName("PKG_OC_IMS_SB")
				.withProcedureName("get_bandwidth_by_tech")
				.declareParameters(
						new SqlParameter("i_technology", Types.VARCHAR),
						new SqlParameter("i_service_boundary", Types.VARCHAR),
						new SqlParameter("bandwidth_cursor", OracleTypes.CURSOR),
						new SqlOutParameter("bandwidth_cursor", OracleTypes.CURSOR, new RowMapper() {
									public String mapRow(ResultSet rs,
											int rowNum) throws SQLException {
										return rs.getString("bandwidth");
									}
								}),
						new SqlOutParameter("gnRetVal", Types.INTEGER),
						new SqlOutParameter("gnErrCode", Types.INTEGER),
						new SqlOutParameter("gsErrText", Types.VARCHAR));
		jdbcCall.setAccessCallParameterMetaData(false);
		MapSqlParameterSource inMap = new MapSqlParameterSource();
		inMap.addValue("i_technology", pITechnology);
		inMap.addValue("i_service_boundary", pIServiceBoundary);
		inMap.addValue("bandwidth_cursor", null);
		Map<String, Object> out = null;
		
		try {
			out = jdbcCall.execute(inMap);
		} catch (Exception e) {
			logger.error("Exception caught in getAddrRolloutDtl()", e);
			throw new DAOException(e.getMessage(), e);
		}
		@SuppressWarnings("unchecked")
		List<String> bandwidthList = (List<String>)out.get("bandwidth_cursor");
		BandwidthDTO bandwidthDTO = new BandwidthDTO();
		
		if ((Integer)out.get("gnErrCode") == 0 && bandwidthList.size() > 0) {
			String[] bandwidth=new String[bandwidthList.size()];
			bandwidthDTO.setBandwidth(bandwidthList.toArray(bandwidth));
		} else {
			bandwidthDTO.setGnRetVal(out.get("gnRetVal").toString());
			bandwidthDTO.setGnErrCode(out.get("gnErrCode").toString());
			bandwidthDTO.setGsErrText((String)out.get("gsErrText"));
		}
		return bandwidthDTO;
	}
	
//	@SuppressWarnings("unchecked")
//	private String[] getAddridByAddress(String pFlat, String pFloor, String pSb) throws DAOException{
//		
//		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
//			.withSchemaName("OPS$BOM")
//	 		.withCatalogName("B_IMS_API_PKG")
//	 		.withProcedureName("getAddridByAddress")
//	 		.declareParameters(
//	 				new SqlParameter("in_flat", Types.VARCHAR),
//	 				new SqlParameter("in_floor", Types.VARCHAR),
//	 				new SqlParameter("in_sb", Types.VARCHAR),
//	 				new SqlOutParameter("out_list_addrid_rslt", OracleTypes.CURSOR, new RowMapper(){
//	 						public String mapRow(ResultSet rs, int rowNum)throws SQLException{
//	 						return rs.getString("addrid");
//	 						}
//	 				}),
//	 				new SqlOutParameter("out_error_code", Types.VARCHAR),
//	 				new SqlOutParameter("out_error_desc", Types.VARCHAR)
//	 				);
//		jdbcCall.setAccessCallParameterMetaData(false);
//		MapSqlParameterSource inMap = new MapSqlParameterSource();
//		inMap.addValue("in_flat", pFlat);	
//		inMap.addValue("in_floor", pFloor);
//		inMap.addValue("in_sb", StringUtils.leftPad(pSb, 6, '0'));
//		SqlParameterSource in = inMap;
//		Map<String, Object> out = null;
//		
//		try {
//			out = jdbcCall.execute(in);
//		} catch (Exception e) {
//			logger.error("Exception caught in getAddridByAddress()", e);
//			throw new DAOException(e.getMessage(), e);
//		}
//		return ((List<String>)out.get("out_list_addrid_rslt")).toArray(new String[0]); 
//	}
	
	
	private String[] getAddridByAddress(String pFlat, String pFloor, String pSb) throws DAOException{
		
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue("in_sb", pSb);
		paramSource.addValue("in_floor", pFloor);
		paramSource.addValue("in_flat", pFlat);
		
		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
			public String mapRow(ResultSet rs, int rowNum)
					throws SQLException {				
				return rs.getString("addrid");
			}
		};
		
		try{
			 List<String> result = simpleJdbcTemplate.query(SQL_GET_IMS_ADDR_ID_BY_SB_FLT_FLR, mapper, paramSource);
			
			if(result == null || result.size() == 0){
				return null;
			}
			
			return result.toArray(new String[0]);
			
		}catch (Exception e) {
			logger.error("Error in getAddridByAddress() pFlat: " + pFlat + " pFloor:" + pFloor + " pSb:" + pSb, e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	
	@SuppressWarnings("unchecked")
	public TenureDTO[] getServiceByAddrid(String pAddrid) throws DAOException {
		
		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
	 		.withSchemaName("OPS$BOM")
	 		.withCatalogName("B_IMS_API_PKG")
	 		.withProcedureName("getServiceByAddrid")
	 		.declareParameters(
	 				new SqlParameter("in_addrid", Types.VARCHAR),
	 				new SqlOutParameter("out_list_service_rslt", OracleTypes.CURSOR, new AddrMapper()),
	 				new SqlOutParameter("out_error_code", Types.VARCHAR),
	 				new SqlOutParameter("out_error_desc", Types.VARCHAR)
	 				);
		jdbcCall.setAccessCallParameterMetaData(false);
		MapSqlParameterSource inMap = new MapSqlParameterSource();
		inMap.addValue("in_addrid", pAddrid);	
		Map<String, Object> out = null;
		
		try {
			out = jdbcCall.execute(inMap);
		} catch (Exception e) {
			logger.error("Exception caught in getServiceByAddrid()", e);
			throw new DAOException(e.getMessage(), e);
		}
 		if (StringUtils.equals((String)out.get("out_error_code"), "0")==false) {
 			return null;
 		}
		return ((List<TenureDTO>)out.get("out_list_service_rslt")).toArray(new TenureDTO[0]);
	}
	
	public String getServiceMiscByFSA(String pFsa) throws DAOException {
		
		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
				.withSchemaName("OPS$BOM")
				.withCatalogName("B_IMS_API_PKG")
				.withProcedureName("getServiceMiscByFSA")
				.declareParameters(new SqlParameter("in_fsa", Types.VARCHAR),
						new SqlOutParameter("out_sertype", Types.VARCHAR),
						new SqlOutParameter("out_error_code", Types.VARCHAR),
						new SqlOutParameter("out_error_desc", Types.VARCHAR));
		jdbcCall.setAccessCallParameterMetaData(false);
		MapSqlParameterSource inMap = new MapSqlParameterSource();
		inMap.addValue("in_fsa", pFsa);	
		Map<String, Object> out = null;
		
		try {
			out = jdbcCall.execute(inMap);
		} catch (Exception e) {
			logger.error("Exception caught in getServiceByAddrid()", e);
			throw new DAOException(e.getMessage(), e);
		}
 		if (StringUtils.equals((String)out.get("out_error_code"), "0") == false) {
 			return null;
 		}
		return (String)out.get("out_sertype");
	}
	
	public CustomerDetailProfileLtsDTO getImsCustomerDetailByFsa(String pFsa) throws DAOException {

		ParameterizedRowMapper<CustomerDetailProfileLtsDTO> mapper = new ParameterizedRowMapper<CustomerDetailProfileLtsDTO>() {
			public CustomerDetailProfileLtsDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				CustomerDetailProfileLtsDTO custDtl = new CustomerDetailProfileLtsDTO();			
				custDtl.setDocNum(rs.getString("ID_DOC_NUM"));
				custDtl.setDocType(rs.getString("ID_DOC_TYPE"));
				custDtl.setFirstName(rs.getString("CUST_FIRST_NAME"));
				custDtl.setLastName(rs.getString("CUST_LAST_NAME"));
				return custDtl;
			}
		};
		List<CustomerDetailProfileLtsDTO> custDtl = null; 
				
		try {
			custDtl = simpleJdbcTemplate.query(SQL_GET_IMS_CUST_DTL_BY_FSA, mapper, pFsa);
		} catch (EmptyResultDataAccessException erdae) {
			return null;
		} catch (Exception e) {
			logger.error("Error in getImsCustomerDetailByFsa() FSA: " + pFsa, e);
			throw new DAOException(e.getMessage(), e);
		}
		if (custDtl == null  || custDtl.size() == 0) {
			return null;
		}
		return custDtl.get(0);
	}
	
	public PendingOrdStatusDetailDTO getPendingOrder(String pFsa) throws DAOException {
		
		List<PendingOrdStatusDetailDTO> status = null;
		
		try {
			status = simpleJdbcTemplate.query(SQL_GET_PEND_ORDER, this.getStatusMapper(), pFsa);
		} catch (EmptyResultDataAccessException erdae) {
			return null;
		} catch (Exception e) {
			logger.error("Error in getPendingOcidByFsa() FSA: " + pFsa, e);
			throw new DAOException(e.getMessage(), e);
		}
		if (status == null || status.size() == 0) {
			return null;
		}
		return status.get(0);
	}

	public String getIsTvServiceByFSA(String pFsa) throws DAOException {
		
		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
				.withSchemaName("OPS$BOM")
				.withCatalogName("pkg_OC_ims_1ams")
				.withProcedureName("getOneAMSInfoWithoutPending")
				.declareParameters(new SqlParameter("i_FSA", Types.VARCHAR),
						new SqlOutParameter("o_oc_id", Types.VARCHAR),
						new SqlOutParameter("o_order_type", Types.VARCHAR),
						new SqlOutParameter("o_srd_date", Types.VARCHAR),
						new SqlOutParameter("o_isPCD", Types.VARCHAR),
						new SqlOutParameter("o_isStandAloneTV", Types.VARCHAR),
						new SqlOutParameter("o_isStandAloneEYE", Types.VARCHAR),
						new SqlOutParameter("o_isStandAloneEasyWatch", Types.VARCHAR),
						new SqlOutParameter("o_isEYE", Types.VARCHAR),
						new SqlOutParameter("o_isEYEX", Types.VARCHAR),
						new SqlOutParameter("o_isTV", Types.VARCHAR),
						new SqlOutParameter("o_isPCDTV", Types.VARCHAR),
						new SqlOutParameter("o_isEYETV", Types.VARCHAR),
						new SqlOutParameter("o_Pid", Types.VARCHAR),
						new SqlOutParameter("o_is1L1B", Types.VARCHAR),
						new SqlOutParameter("o_isVI", Types.VARCHAR),
						new SqlOutParameter("o_cust_name", Types.VARCHAR),
						new SqlOutParameter("o_PCD_acc_status", Types.VARCHAR),
						new SqlOutParameter("o_VI_acc_status", Types.VARCHAR),
						new SqlOutParameter("o_isILRC", Types.VARCHAR),
						new SqlOutParameter("gnRetVal", Types.INTEGER),
						new SqlOutParameter("gnErrCode", Types.INTEGER),
						new SqlOutParameter("gsErrText", Types.VARCHAR));
		jdbcCall.setAccessCallParameterMetaData(false);
		MapSqlParameterSource inMap = new MapSqlParameterSource();
		inMap.addValue("i_FSA", pFsa);	
		Map<String, Object> out = null;
		
		try {
			out = jdbcCall.execute(inMap);
		} catch (Exception e) {
			logger.error("Exception caught in getIsTvServiceByFSA()", e);
			throw new DAOException(e.getMessage(), e);
		}
		if ((Integer)out.get("gnErrCode") == 0 ) {
			return (String)out.get("o_isTV");	
		}
		return null;
		
	}

	public String checkProductParmByFsa(String fsa, String parmName) throws DAOException {
		
		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
				.withSchemaName("OPS$BOM")
				.withCatalogName("B_Ims_Api_Pkg")
				.withProcedureName("CheckProdParmByFsa")
				.declareParameters(new SqlParameter("i_fsa", Types.VARCHAR),
						new SqlParameter("i_parm", Types.VARCHAR),
						new SqlOutParameter("o_ind", Types.VARCHAR),
						new SqlOutParameter("oErrCode", Types.VARCHAR),
						new SqlOutParameter("oErrText", Types.VARCHAR));
		jdbcCall.setAccessCallParameterMetaData(false);
		MapSqlParameterSource inMap = new MapSqlParameterSource();
		inMap.addValue("i_fsa", fsa);
		inMap.addValue("i_parm", parmName);	
		Map<String, Object> out = null;
		
		try {
			out = jdbcCall.execute(inMap);
		} catch (Exception e) {
			logger.error("Exception caught in checkProductParmByFsa()", e);
			throw new DAOException(e.getMessage(), e);
		}
		if (StringUtils.equals("0", (String)out.get("oErrCode"))) {
			return (String)out.get("o_ind");	
		}
		return (String)out.get("oErrText");
		
	}
	
	private final class ImsServiceProfileAccessMapper implements RowMapper {
	    
	    public FsaServiceDetailOutputDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
	    	FsaServiceDetailOutputDTO fsaServiceDetailOutputDTO = new FsaServiceDetailOutputDTO();
	    	try {
		    	fsaServiceDetailOutputDTO.setFsa(rs.getString("fsa"));
		    	fsaServiceDetailOutputDTO.setExistingSrv(rs.getString("existingSrv"));
		    	fsaServiceDetailOutputDTO.setLoginID(rs.getString("loginid"));
		    	fsaServiceDetailOutputDTO.setEffStratDate(rs.getString("effStartDate"));
		    	fsaServiceDetailOutputDTO.setAddressID(rs.getString("addressID"));
		    	fsaServiceDetailOutputDTO.setBandwidth(rs.getString("bandwidth"));
		    	fsaServiceDetailOutputDTO.setPendingOcid(rs.getString("OCID"));
		    	fsaServiceDetailOutputDTO.setPendingOrderType(rs.getString("ORDTYPE"));
		    	fsaServiceDetailOutputDTO.setPendingOrderSrd(rs.getString("SRD"));
		    	fsaServiceDetailOutputDTO.setDocNum(rs.getString("docNum"));
		    	fsaServiceDetailOutputDTO.setDocType(rs.getString("docType"));
		    	fsaServiceDetailOutputDTO.setCustFirstName(rs.getString("custFirstName"));
		    	fsaServiceDetailOutputDTO.setCustLastName(rs.getString("custLastName"));
		    	fsaServiceDetailOutputDTO.setIsEye(rs.getString("ISEYE"));
		    	fsaServiceDetailOutputDTO.setTechnology(rs.getString("technology"));
		    	fsaServiceDetailOutputDTO.setIsTos(rs.getString("TOS"));
		    	fsaServiceDetailOutputDTO.setDeactNowtvInd(rs.getString("DEACT_NOWTV_IND"));
		    	fsaServiceDetailOutputDTO.setExistModem(rs.getString("STB_MODE"));
		    	fsaServiceDetailOutputDTO.setNoEyeInd(rs.getString("NO_EYE"));
	    	}
	    	catch (Exception e) {
		    	return fsaServiceDetailOutputDTO;
			}
	    	return fsaServiceDetailOutputDTO;
	    }
	}
        
        
	private final class ImsServiceAddressMapper implements RowMapper {
    	    
		public AddressDetailProfileLtsDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
			AddressDetailProfileLtsDTO addressDetailProfileLtsDTO = new AddressDetailProfileLtsDTO();
			addressDetailProfileLtsDTO.setAreaCd(rs.getString("AREACD"));
			addressDetailProfileLtsDTO.setArea(rs.getString("AREADSC"));
			addressDetailProfileLtsDTO.setAddrId(rs.getString("ADDR_ID"));
			addressDetailProfileLtsDTO.setUnitNum(rs.getString("UNIT_NUM"));
			addressDetailProfileLtsDTO.setFloorNum(rs.getString("FLOOR_NUM"));
			addressDetailProfileLtsDTO.setBuildName(rs.getString("BUILD_NAME"));
			addressDetailProfileLtsDTO.setDistrictCd(rs.getString("DISTR_NUM"));
			addressDetailProfileLtsDTO.setDistrict(rs.getString("DISTRICT"));
			addressDetailProfileLtsDTO.setHlotNum(rs.getString("HLOT_NUM"));
			addressDetailProfileLtsDTO.setLdlotNum(rs.getString("LDLOT_NUM"));
			addressDetailProfileLtsDTO.setSectCd(rs.getString("SECT_CD"));
			addressDetailProfileLtsDTO.setSectDesc(rs.getString("SECT_DSC"));
			addressDetailProfileLtsDTO.setStreetNum(rs.getString("STREET_NUM"));
			addressDetailProfileLtsDTO.setStreetName(rs.getString("STREET_NAME"));
			addressDetailProfileLtsDTO.setStreetCat(rs.getString("ST_CATG_CD"));
			addressDetailProfileLtsDTO.setSrvBdry(rs.getString("SRVBDRY_NUM"));
			addressDetailProfileLtsDTO.setLotHseInd(rs.getString("LOT_HSE_IND"));
			addressDetailProfileLtsDTO.setBuildID(rs.getString("BUILD_ID"));
			addressDetailProfileLtsDTO.setGridID(rs.getString("GRID_ID"));
			return addressDetailProfileLtsDTO;
		}
	}
        
	public final class AddrRolloutDtlMapper implements RowMapper {
    	    
		public TechnologyDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
			TechnologyDTO technologyDTO = new TechnologyDTO();
			technologyDTO.setMaximumBandwidth(rs.getString("max_bb"));
    	    technologyDTO.setTechnology(rs.getString("technology"));
    	    technologyDTO.setIsResrcShort(rs.getString("is_resrc_short"));
    	    technologyDTO.setIsDeadCase(rs.getString("is_dead"));
    	    return technologyDTO;
		}
	}
        
	public final class AddrMapper implements RowMapper {
    	    
		public TenureDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
			TenureDTO tenure = new TenureDTO();
			tenure.setCustNum(rs.getString("BOM_CUST_NUM"));
			tenure.setServiceId(rs.getString("service_id"));
			tenure.setTenure(rs.getDouble("Tenure"));
			return tenure;
		}
	}
	
	private ParameterizedRowMapper<PendingOrdStatusDetailDTO> getStatusMapper() {
		return new ParameterizedRowMapper<PendingOrdStatusDetailDTO>() {
			public PendingOrdStatusDetailDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				PendingOrdStatusDetailDTO status = new PendingOrdStatusDetailDTO();
				status.setBomStatus(rs.getString("BOM_STATUS"));
				status.setDtlId(rs.getString("DTL_ID"));
				status.setLegacyStatus(rs.getString("STATUS"));
				status.setOcid(rs.getString("OC_ID"));
				status.setSrd(rs.getString("SRV_REQ_DATE"));
				status.setOrderType(rs.getString("ORD_TYPE"));
				return status;
			}
		};
	}
	
	private ParameterizedRowMapper<UimBlockageDTO> getUimBlockageMapper() {
		return new ParameterizedRowMapper<UimBlockageDTO>() {
			public UimBlockageDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				UimBlockageDTO uimBlockage = new UimBlockageDTO();
				uimBlockage.setBlockageCd(rs.getString("BLOCKAGE_CODE"));
				uimBlockage.setBlockageDesc(rs.getString("BLOCKAGE_DESC"));
				uimBlockage.setBlockageDate(rs.getString("BLOCKAGE_DATE"));
				uimBlockage.setFlat(rs.getString("UNITNB"));
				uimBlockage.setFloor(rs.getString("FLOORNB"));
				uimBlockage.setServiceBoundary(rs.getString("SERBDYNO"));
				return uimBlockage;
			}	
		};
	}

	private ParameterizedRowMapper<String[]> getImsCustDocMapper() {
		return new ParameterizedRowMapper<String[]>() {
			public String[] mapRow(ResultSet rs, int rowNum) throws SQLException {
				String[] stringArray = {rs.getString("ID_DOC_TYPE"), rs.getString("ID_DOC_NUM")};
				return stringArray;
			}	
		};
	}
}
