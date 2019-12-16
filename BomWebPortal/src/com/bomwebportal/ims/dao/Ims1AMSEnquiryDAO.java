package com.bomwebportal.ims.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import java.util.Map;

import oracle.jdbc.OracleTypes;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;

import com.bomwebportal.dao.BaseDAO;
import com.bomwebportal.exception.DAOException;

import com.bomwebportal.ims.dto.Ims1AMSFSAInfoDTO;
import com.bomwebportal.ims.dto.Ims1AMSFSAInfoListDTO;
import com.bomwebportal.ims.dto.Ims1AMSInfoWithoutPendingDTO;


public class Ims1AMSEnquiryDAO extends BaseDAO{
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	public final class Ims1AMSAddrInfoMapper2 implements RowMapper {

		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			Ims1AMSFSAInfoDTO dto = new Ims1AMSFSAInfoDTO();
			
			dto.setFlat(rs.getString("flat"));
			dto.setFloor(rs.getString("FLOOR"));
			dto.setHLotNO(rs.getString("h_lot_no"));
			dto.setAddrId(rs.getString("addr_id"));
			dto.setFSA(rs.getString("FSA"));
			dto.setIS1L1B(rs.getString("IS1L1B"));
			dto.setCustName(rs.getString("CUST_NAME"));
			dto.setPID(rs.getString("PID"));
			dto.setStatus(rs.getString("Status"));
			dto.setLineType(rs.getString("BANDWIDTH"));
			dto.setFakeLineType(rs.getString("FAKELINETYPE"));
			
			return dto;
		}

	}
	public Ims1AMSFSAInfoListDTO getIms1AMSFSAInfoList2(String i_serbdyno, String i_flat, String i_floor, String i_h_lot_no) throws DAOException{
		logger.debug("getIms1AMSFSAInfoList");
		Connection conn = null;
		try{
		
			Ims1AMSFSAInfoListDTO ims1AMSFSAInfoListDTO = new Ims1AMSFSAInfoListDTO();  
			SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
	        	.withSchemaName("OPS$BOM")
	        	.withCatalogName("pkg_CC_IMS_SB")
	        	.withProcedureName("get1amsAddressInfo")
	        	.declareParameters(
	        			new SqlParameter("i_serbdyno", Types.VARCHAR),
	        			new SqlParameter("i_flat", Types.VARCHAR),
	        			new SqlParameter("i_floor", Types.VARCHAR),
	        			new SqlParameter("i_h_lot_no", Types.VARCHAR),
						new SqlOutParameter("o_address_info_cursor", OracleTypes.CURSOR, new Ims1AMSAddrInfoMapper2()),
	        			new SqlOutParameter("gnRetVal", Types.INTEGER),
						new SqlOutParameter("gnErrCode", Types.INTEGER),
						new SqlOutParameter("gsErrText", Types.VARCHAR));
	
			MapSqlParameterSource inMap = new MapSqlParameterSource();
			inMap.addValue("i_serbdyno", i_serbdyno);
			inMap.addValue("i_flat", i_flat);
			inMap.addValue("i_floor", i_floor);	
			inMap.addValue("i_h_lot_no", i_h_lot_no);	
			SqlParameterSource in = inMap;
			
			Map out = jdbcCall.execute(in);
						
			logger.debug("output ret_val = " + out.get("gnRetVal") + ";error_code = " + out.get("gnErrCode") + ";error_text = " + out.get("gsErrText"));
			
			ims1AMSFSAInfoListDTO.setIms1AMSFSAInfoList((List)out.get("o_address_info_cursor"));
	        
			return ims1AMSFSAInfoListDTO;
	        	
		} catch (Exception e) {
			logger.error("Exception caught in getIms1AMSFSAInfoList()", e);
			throw new DAOException(e.getMessage(), e);
		}
		
	}
	
	
	public Ims1AMSInfoWithoutPendingDTO getIms1AMSInfoWithoutPending(String in_FSA) throws DAOException{
		logger.info("getIms1AMSInfoWithoutPending");
		try{
		
			Ims1AMSInfoWithoutPendingDTO ims1AMSInfoWithoutPendingDTO = new Ims1AMSInfoWithoutPendingDTO();  
			SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
	        	.withSchemaName("OPS$BOM")
	        	.withCatalogName("PKG_OC_IMS_1AMS")
	        	.withProcedureName("getOneAMSInfoWithoutPending") 
	        	.withoutProcedureColumnMetaDataAccess()
	        	.declareParameters(
                        new SqlParameter("i_FSA", Types.VARCHAR),
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
	
			MapSqlParameterSource inMap = new MapSqlParameterSource();
			inMap.addValue("i_FSA", in_FSA);	
			SqlParameterSource in = inMap;
			
			Map out = jdbcCall.execute(in);
						
			logger.info("output ret_val = " + out.get("gnRetVal") + ";error_code = " + out.get("gnErrCode") + ";error_text = " + out.get("gsErrText"));
			
			ims1AMSInfoWithoutPendingDTO.setCustName((String)out.get("o_cust_name"));
			
			ims1AMSInfoWithoutPendingDTO.setIs1L1B((String)out.get("o_is1L1B"));
			ims1AMSInfoWithoutPendingDTO.setIsEYE((String)out.get("o_isEYE"));
			ims1AMSInfoWithoutPendingDTO.setIsEYEX((String)out.get("o_isEYEX"));
			ims1AMSInfoWithoutPendingDTO.setIsILRC((String)out.get("o_isILRC"));
			ims1AMSInfoWithoutPendingDTO.setIsPCD((String)out.get("o_isPCD"));
			ims1AMSInfoWithoutPendingDTO.setIsPCDTV((String)out.get("o_isPCDTV"));
			ims1AMSInfoWithoutPendingDTO.setIsStandAloneEasyWatch((String)out.get("o_isStandAloneEasyWatch"));
			ims1AMSInfoWithoutPendingDTO.setIsStandAloneEYE((String)out.get("o_isStandAloneEYE"));
			ims1AMSInfoWithoutPendingDTO.setIsStandAloneTV((String)out.get("o_isStandAloneTV"));
			ims1AMSInfoWithoutPendingDTO.setIsTV((String)out.get("o_isTV"));
			ims1AMSInfoWithoutPendingDTO.setIsVI((String)out.get("o_isVI"));
			ims1AMSInfoWithoutPendingDTO.setOcid((String)out.get("o_oc_id"));
			ims1AMSInfoWithoutPendingDTO.setOrderType((String)out.get("o_order_type"));
			ims1AMSInfoWithoutPendingDTO.setpCDAccStatus((String)out.get("o_PCD_acc_status"));
			ims1AMSInfoWithoutPendingDTO.setpID((String)out.get("o_Pid"));			
			ims1AMSInfoWithoutPendingDTO.setSrdDate((String)out.get("o_srd_date"));
			ims1AMSInfoWithoutPendingDTO.setvIAccStatus((String)out.get("o_VI_acc_status"));
	
			return ims1AMSInfoWithoutPendingDTO;
			
		} catch (Exception e) {
			logger.error("Exception caught in getIms1AMSInfoWithoutPending()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}	
	
	
	public String getIms1AMSBandwidth(String i_pid) throws DAOException{
		logger.info("getIms1AMSBandwidth");
		try{
		
			SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
	        	.withSchemaName("OPS$BOM")
	        	.withCatalogName("PKG_OC_IMS_1AMS")
	        	.withProcedureName("getBandwidth")
	        	.declareParameters(
	        			new SqlParameter("i_Pid", Types.VARCHAR),
						new SqlOutParameter("o_Bandwidth", Types.VARCHAR),
						new SqlOutParameter("gnRetVal", Types.INTEGER),
						new SqlOutParameter("gnErrCode", Types.INTEGER),
						new SqlOutParameter("gsErrText", Types.VARCHAR));
	
			MapSqlParameterSource inMap = new MapSqlParameterSource();
			inMap.addValue("i_Pid", i_pid);	 
			SqlParameterSource in = inMap;
			
			Map out = jdbcCall.execute(in);
						
			logger.info("output ret_val = " + out.get("gnRetVal") + ";error_code = " + out.get("gnErrCode") + ";error_text = " + out.get("gsErrText"));
	
			return (String) (out.get("o_Bandwidth"));
			
		} catch (Exception e) {
			logger.error("Exception caught in getIms1AMSBandwidth()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public final class Ims1AMSAddrInfoMapper3 implements RowMapper {

		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			Ims1AMSFSAInfoDTO dto = new Ims1AMSFSAInfoDTO();
			
			dto.setAddrId(rs.getString("addr_id"));
			dto.setFlat(rs.getString("flat"));
			dto.setFloor(rs.getString("FLOOR"));
			dto.setHLotNO(rs.getString("h_lot_no"));
			
			return dto;
		}

	}

	public boolean checkDs1D1ISimilarAddress(String i_serbdyno, String i_flat,
			String i_floor, String i_h_lot_no) throws DAOException {
		logger.info("checkDs1D1ISimilarAddress");
		Connection conn = null;
		boolean isSimilar = false;
		
		try{
		
			Ims1AMSFSAInfoListDTO ims1AMSFSAInfoListDTO = new Ims1AMSFSAInfoListDTO();  
			SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
	        	.withSchemaName("OPS$BOM")
	        	.withCatalogName("pkg_OC_ims_1ams")
	        	.withProcedureName("getAddressInfo")
	        	.declareParameters(
	        			new SqlParameter("i_serbdyno", Types.VARCHAR),
	        			new SqlParameter("i_flat", Types.VARCHAR),
	        			new SqlParameter("i_floor", Types.VARCHAR),
	        			new SqlParameter("i_h_lot_no", Types.VARCHAR),
						new SqlOutParameter("o_address_info_cursor", OracleTypes.CURSOR, new Ims1AMSAddrInfoMapper3()),
	        			new SqlOutParameter("gnRetVal", Types.INTEGER),
						new SqlOutParameter("gnErrCode", Types.INTEGER),
						new SqlOutParameter("gsErrText", Types.VARCHAR));
	
			MapSqlParameterSource inMap = new MapSqlParameterSource();
			inMap.addValue("i_serbdyno", i_serbdyno);
			inMap.addValue("i_flat", i_flat);
			inMap.addValue("i_floor", i_floor);	
			inMap.addValue("i_h_lot_no", i_h_lot_no);	
			SqlParameterSource in = inMap;
			
			Map out = jdbcCall.execute(in);
						
			logger.info("output ret_val = " + out.get("gnRetVal") + ";error_code = " + out.get("gnErrCode") + ";error_text = " + out.get("gsErrText"));
			logger.info("similar address size" + ((List)out.get("o_address_info_cursor")).size() );
			
			ims1AMSFSAInfoListDTO.setIms1AMSFSAInfoList((List)out.get("o_address_info_cursor"));
			
			if (ims1AMSFSAInfoListDTO.getIms1AMSFSAInfoList() != null){
				for (int i=0;i<ims1AMSFSAInfoListDTO.getIms1AMSFSAInfoList().size();i++){
					logger.info("ims1AMSFSAInfoListDTO.getIms1AMSFSAInfoList().get(i).getFlat()"+ ims1AMSFSAInfoListDTO.getIms1AMSFSAInfoList().get(i).getFlat());
					logger.info("ims1AMSFSAInfoListDTO.getIms1AMSFSAInfoList().get(i).getFloor()"+ ims1AMSFSAInfoListDTO.getIms1AMSFSAInfoList().get(i).getFloor());
					logger.info("ims1AMSFSAInfoListDTO.getIms1AMSFSAInfoList().get(i).getAddrId()"+ ims1AMSFSAInfoListDTO.getIms1AMSFSAInfoList().get(i).getAddrId());
				}
			}
			if ( ((List)out.get("o_address_info_cursor")).size() >0)	        
				return true;
			else return false;
	        	
		} catch (Exception e) {
			logger.error("Exception caught in checkDs1D1ISimilarAddress()", e);
			throw new DAOException(e.getMessage(), e);
		}
		
	}	

}