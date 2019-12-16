package com.bomwebportal.lts.dao.bom;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import oracle.jdbc.driver.OracleTypes;

import org.apache.commons.lang.StringUtils;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;

import com.bomwebportal.dao.BaseDAO;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.lts.dto.ImsBomPCDOrderDetailsDTO;
import com.bomwebportal.lts.dto.ImsPendingOrderDTO;
import com.bomwebportal.lts.dto.bom.OrderAccountDTO;
import com.bomwebportal.lts.dto.bom.OrderServiceDTO;
import com.bomwebportal.lts.dto.profile.AddressDetailProfileLtsDTO;

public class BomOrderInfoDAO extends BaseDAO {

	private static final String SQL_GET_INSTALL_PENDING_BOM_ORD = 
		"select distinct OC_ID, DTL_ID, DTL_SEQ, TYPE_OF_SRV, DAT_CD, SRV_ID, EX_SRV_ID, CLS_OF_SRV_CD, DUP_SRV_ID, BOM_STATUS, to_char(SRV_REQ_DATE, 'dd/mm/yyyy') SRV_REQ_DATE, " + 
		"decode (GRP_TYPE, 'EYE', GRP_TYPE || GRP_ID, 'EYEX', GRP_TYPE || GRP_ID, 'EYE3', GRP_TYPE || GRP_ID) EYE_GRP_ID, " + 
		"ACCT_NUM, CHRG_CATG_CD, CUST_NUM, ACCT_TYPE, " + 
		"ADDR_ID, UNIT_NUM, FLOOR_NUM, BUILD_NAME, SECTDSC, HLOT_NUM, STREET_NUM, STREET_NAME, STCATDSC, DISTDSC, AREADSC, SRVBDRY_NUM, SECT_CD, ST_CATG_CD, DISTR_NUM, AREACD, ADDRESS " +
		"from B_LTS_PEND_ORD_V " +
		"where ex_srv_id is null and cust_num = ? and system_id = ? " + 
		"order by OC_ID, DTL_ID, DTL_SEQ";
	
	
	public OrderServiceDTO[] getLtsInstallPendingOrderByCust(String pCustNum, String pSystemId) throws DAOException {
		
		final Map<String, OrderServiceDTO> srvMap = new HashMap<String, OrderServiceDTO>();
		
		ParameterizedRowMapper<OrderServiceDTO> mapper = new ParameterizedRowMapper<OrderServiceDTO>() {
			public OrderServiceDTO mapRow(ResultSet rs, int rowNum) throws SQLException {

				String ocid = rs.getString("OC_ID");
				String key = ocid + rs.getString("DTL_ID");
				OrderServiceDTO srv = null;
				
				if (srvMap.containsKey(key)) {
					srv = srvMap.get(key);
				} else {
					srv = new OrderServiceDTO();
					srv.setDatCd(rs.getString("DAT_CD"));
					srv.setDuplexNum(rs.getString("DUP_SRV_ID"));
					srv.setEyeGrpId(rs.getString("EYE_GRP_ID"));
					srv.setPendingOcid(ocid);
					srv.setPendingOcSrd(rs.getString("SRV_REQ_DATE"));
					srv.setSrvNum(rs.getString("SRV_ID"));
					srv.setSrvType(rs.getString("TYPE_OF_SRV"));
					srv.setTariff(rs.getString("CLS_OF_SRV_CD"));
					srvMap.put(key, srv);
										
					String addId = rs.getString("ADDR_ID");
					
					if (StringUtils.isNotBlank(addId)) {
						AddressDetailProfileLtsDTO addr = new AddressDetailProfileLtsDTO();
						addr.setAddrId(addId);
						addr.setUnitNum(rs.getString("UNIT_NUM"));
						addr.setFloorNum(rs.getString("FLOOR_NUM"));
						addr.setBuildName(rs.getString("BUILD_NAME"));
						addr.setSectDesc(rs.getString("SECTDSC"));
						addr.setHlotNum(rs.getString("HLOT_NUM"));
						addr.setStreetNum(rs.getString("STREET_NUM"));
						addr.setStreetName(rs.getString("STREET_NAME"));
						addr.setStreetCat(rs.getString("STCATDSC"));
						addr.setDistrict(rs.getString("DISTDSC"));
						addr.setArea(rs.getString("AREADSC"));
						addr.setSrvBdry(rs.getString("SRVBDRY_NUM"));
						addr.setSectCd(rs.getString("SECT_CD"));
						addr.setStreetCatCd(rs.getString("ST_CATG_CD"));
						addr.setDistrictCd(rs.getString("DISTR_NUM"));
						addr.setAreaCd(rs.getString("AREACD"));
						addr.setFullAddress(rs.getString("ADDRESS"));
						srv.setAddress(addr);
					}
				}				
				OrderAccountDTO acct = new OrderAccountDTO();
				acct.setAcctNum(rs.getString("ACCT_NUM"));
				acct.setAcctType(rs.getString("ACCT_TYPE"));
				acct.setChrgCatgCd(rs.getString("CHRG_CATG_CD"));
				acct.setCustNum(rs.getString("CUST_NUM"));
				srv.appendAccount(acct);
				
				return null;
			}
		};		
		try {
			simpleJdbcTemplate.query(SQL_GET_INSTALL_PENDING_BOM_ORD, mapper, pCustNum, pSystemId);
			
			if (srvMap.size() == 0) {
				return null;
			}
			return srvMap.values().toArray((new OrderServiceDTO[srvMap.size()]));
		} catch (EmptyResultDataAccessException erdae) {
			return null;
		} catch (Exception e) {
			logger.error("Error in getLtsInstallPendingOrder().  CustNum: " + pCustNum, e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public ImsBomPCDOrderDetailsDTO getBomPcdSbOrderAmendDetail(String orderId,String amendType) throws DAOException
	{
		logger.info("getBomPcdSbOrderDetail is called");
		logger.info("orderId:" + orderId);
		
		ImsBomPCDOrderDetailsDTO result = new ImsBomPCDOrderDetailsDTO();
	
		try {
			
			SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
			.withSchemaName("ops$bom")
			.withCatalogName("pkg_oc_ims_sb")
			.withProcedureName("get_sb_oc_amend_detail")
			.declareParameters(
					new SqlParameter("i_order_id", Types.VARCHAR),
					new SqlParameter("i_amend_type", Types.VARCHAR),
					new SqlOutParameter("o_oc_id", Types.VARCHAR),
					new SqlOutParameter("o_fsa", Types.VARCHAR),
					new SqlOutParameter("o_cust_num", Types.VARCHAR),
					new SqlOutParameter("o_appnt_start_date", Types.TIMESTAMP),
					new SqlOutParameter("o_appnt_end_date", Types.TIMESTAMP),
					new SqlOutParameter("gnRetVal", Types.INTEGER), 
					new SqlOutParameter("gnErrCode", Types.INTEGER),
					new SqlOutParameter("gsErrText", Types.VARCHAR)
					);
			 
			
			MapSqlParameterSource in = new MapSqlParameterSource();
			in.addValue("i_order_id",  orderId);
			in.addValue("i_amend_type",  amendType);
			
			Map out = jdbcCall.execute(in); 
			
			if(out.get("gnErrCode") != null){
				logger.error(out.get("gnErrCode"));
			}
			if(out.get("gsErrText") != null){
				logger.error(out.get("gsErrText"));
			}
	
			result.setFsa((String)out.get("o_fsa"));
			result.setCustNo((String)out.get("o_cust_num"));
			result.setApplicationStartDate((Date)out.get("o_appnt_start_date"));
			result.setApplicationEndDate((Date)out.get("o_appnt_end_date"));

			
			return result; 
			
		}catch (Exception e) {
			logger.error("Exception caught in getBomPcdSbOrderAmendDetail()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public String checkAnyActiveServiceWithinXMonths(String srvbdry_num, String floor_num, String unit_num, String prevSerTermMth) throws DAOException
	{
		logger.info("checkAnyActiveServiceWithinXMonths is called");
		logger.info("srvbdry_num:" + srvbdry_num + ", floor_num:" + floor_num + ", unit_num:" + unit_num + ", prevSerTermMth:" + prevSerTermMth);
		
		String result = "";
	
		try {
			
			SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
			.withSchemaName("ops$bom")
			.withCatalogName("lts_svc_check_pkg")
			.withProcedureName("chkLtsSvcInXMthbyIA")
			.declareParameters(
					new SqlParameter("in_srvbdry_num", Types.VARCHAR),
					new SqlParameter("in_floor_num", Types.VARCHAR),
					new SqlParameter("in_unit_num", Types.VARCHAR),
					new SqlParameter("prevSerTermMth", Types.VARCHAR),
					new SqlOutParameter("lts_srv_ind", Types.VARCHAR),
					new SqlOutParameter("rtnCode", Types.VARCHAR),
					new SqlOutParameter("rtnMsg", Types.VARCHAR)
					);
			 
			
			MapSqlParameterSource in = new MapSqlParameterSource();
			in.addValue("in_srvbdry_num", srvbdry_num);
			in.addValue("in_floor_num", floor_num);
			in.addValue("in_unit_num", unit_num);
			in.addValue("prevSerTermMth", prevSerTermMth);
			
			Map out = jdbcCall.execute(in); 
			
			if(out.get("rtnCode") != null && out.get("rtnCode").toString().equalsIgnoreCase("9")){
				logger.error("rtnCode:"+out.get("rtnCode")+" ");
				logger.error("rtnMsg:"+out.get("rtnMsg"));
			}
	
			result = (String)out.get("lts_srv_ind");
			
			return result; 
			
		}catch (Exception e) {
			logger.error("Exception caught in checkAnyActiveServiceWithinXMonths", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public List<ImsPendingOrderDTO> getBomImsLatestPendingOrderBySrvNum(String pSrvNum) throws DAOException
	{
		logger.info("getSbImsLatestPendingOrderBySrvNum is called");
		logger.info("FSA:" + pSrvNum);
		
		List<ImsPendingOrderDTO> rtnList = new ArrayList<ImsPendingOrderDTO>();
		String pendOrderInd;
		
		try {
			
			SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
			.withSchemaName("ops$bom")
			.withCatalogName("pkg_CC_IMS_SB")
			.withProcedureName("get_ims_pending_order_by_fsa")
			.declareParameters(
					new SqlParameter("i_fsa", Types.VARCHAR),
					new SqlOutParameter("o_pending_order_ind", Types.VARCHAR),
					new SqlOutParameter("o_order_id_list", OracleTypes.CURSOR, new Mapper1()),
					new SqlOutParameter("gnretval", Types.INTEGER), 
					new SqlOutParameter("gnerrcode", Types.INTEGER),
					new SqlOutParameter("gserrtext", Types.VARCHAR)
					);
			 
			
			MapSqlParameterSource in = new MapSqlParameterSource();
			in.addValue("i_fsa",  pSrvNum);
			
			Map out = jdbcCall.execute(in); 
			
			pendOrderInd = (String)out.get("o_pending_order_ind");
			
			if (pendOrderInd.equalsIgnoreCase("Y")){
				rtnList = (List)out.get("o_order_id_list");
			}else{
				rtnList = null;
			}
			
			return rtnList; 
			
		}catch (Exception e) {
			logger.error("Exception caught in getBomImsLatestPendingOrderBySrvNum()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public final class Mapper1 implements RowMapper {
	    
	    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
	    	ImsPendingOrderDTO dto = new ImsPendingOrderDTO();
	    	dto.setOrderId(rs.getString("order_id"));
	    	dto.setOcId(rs.getString("ocid"));
	    	dto.setEdfRef(rs.getString("edf_ref"));
	    	dto.setErInd(rs.getString("er_ind"));
	    	dto.setSrdStart(rs.getString("srv_start_day"));
	    	dto.setSrdEnd(rs.getString("srv_end_day"));
	    	dto.setSbPendingInd("N");
	    	dto.setBomPendingInd(rs.getString("pending_bom_ind"));
	    	dto.setBossPendingInd(rs.getString("pending_boss_ind"));
	    	dto.setL2OrderNum(rs.getString("l2_order_num"));
	    	
	        return dto;
	    }
	    
	}
}
