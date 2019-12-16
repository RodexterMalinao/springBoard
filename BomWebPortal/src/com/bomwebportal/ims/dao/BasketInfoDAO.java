package com.bomwebportal.ims.dao;

import oracle.jdbc.driver.OracleTypes;

import org.apache.axis.utils.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.datasource.DataSourceUtils;

import com.bomwebportal.dao.*;
import com.bomwebportal.dto.*;
import com.bomwebportal.ims.dto.*;
import com.bomwebportal.ims.dto.ui.InstallFeeUI;
import com.bomwebportal.util.BomWebPortalConstant;
import com.bomwebportal.exception.*;
import com.google.gson.Gson;
import com.pccw.util.db.stringOracleType.OraArrayVarChar2;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BasketInfoDAO extends BaseDAO{
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	private Gson gson = new Gson();
	private static final String ORACLE_NUM_ARRAY = "OPS$CNM.TABLE_VARCHAR2";
	
	public List<BasketDetailsDTO> getBasketInfo (String basketID)throws DAOException {
		List<BasketDetailsDTO> basketInfoList = new ArrayList<BasketDetailsDTO>();

		String SQL=

		
			"SELECT bdl.html pkg_desc, 												\n"+
			"		idl.html pkg_amount,											\n"+
			"		nvl(idi.ot_install_chrg_req,'Y') ot_install_chrg_req			\n"+						
			"FROM  	w_basket_display_lkup bdl,					\n"+
			"  		w_dic_basket_item_assgn bia,				\n"+
			"  		w_item_display_lkup idl,					\n"+
			"		w_item_dtl_ims idi,							\n"+
			"  		w_item i									\n"+					
			"WHERE  bdl.locale = 'en' 							\n"+
			"AND    bdl.basket_id = ?							\n"+																				
			"AND    bdl.DISPLAY_TYPE = 'TITLE'					\n"+
			"AND    bdl.basket_id = bia.basket_id				\n"+										
			"AND    bia.item_id = idl.ITEM_ID   				\n"+
			"AND	idl.locale = 'en'							\n"+
			"AND    idl.DISPLAY_TYPE = 'MTHFIX'   				\n"+		
			"AND	idl.item_id = i.id							\n"+
			"AND 	idi.item_id = i.id							\n"+
			"AND    i.type = 'PROG'								\n"+
			"AND    i.LOB = 'IMS' 								  ";	
		
		ParameterizedRowMapper<BasketDetailsDTO> mapper = new ParameterizedRowMapper<BasketDetailsDTO>() {
	        public BasketDetailsDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
	        	BasketDetailsDTO basketInfo = new BasketDetailsDTO();
	        	basketInfo.setTitle(rs.getString("pkg_desc"));
	        	basketInfo.setMthFixText(rs.getString("pkg_amount"));
	        	basketInfo.setOtInstallChrgReq(rs.getString("ot_install_chrg_req"));
	            return basketInfo;
	        }
	    };

		try {
			logger.debug("getBasketInfo @ BasketInfoDAO: " + SQL);
			basketInfoList = simpleJdbcTemplate.query(SQL, mapper,basketID);
		} catch (EmptyResultDataAccessException erdae) {
			logger.debug("EmptyResultDataAccessException");
			basketInfoList = null;
		} catch (Exception e) {
			logger.debug("Exception caught in getBasketDetailsList():", e);
			throw new DAOException(e.getMessage(), e);
		}	
		return basketInfoList;
	}
	
	public InstallFeeUI getOTCByBasketId(String basketId)throws DAOException {
		logger.info("getOTCByBasketId is called");
		
		InstallFeeUI InstallFee = new InstallFeeUI();
				
		try {			
	        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
	        	.withSchemaName("ops$cnm")
	        	.withCatalogName("pkg_SB_IMS_LTS")
	        	.withProcedureName("get_basket_install_ot")  
	        	.declareParameters(
	        			new SqlParameter("i_basket_id", Types.VARCHAR),
						new SqlOutParameter("o_install_detail_cur", OracleTypes.CURSOR, new InstallationInstallmentPlanMapper()),
						new SqlOutParameter("gnRetVal", Types.INTEGER),
						new SqlOutParameter("gnErrCode", Types.INTEGER),
						new SqlOutParameter("gsErrText", Types.VARCHAR));
	       
			MapSqlParameterSource inMap = new MapSqlParameterSource();
			inMap.addValue("i_basket_id", basketId);

			SqlParameterSource in = inMap;
			logger.debug("getOTCByBasketId human input:"+gson.toJson(in));			
			Map out = jdbcCall.execute(in);
			logger.debug("getOTCByBasketId human output:"+gson.toJson(out));
	        
			int error_code = BomWebPortalConstant.ERRCODE_FAIL;
			int ret_val = BomWebPortalConstant.ERRCODE_FAIL; 
			
			if (((Integer) out.get("gnErrCode"))!= null ) {
				error_code = ((Integer) out.get("gnErrCode")).intValue();
			}
			
			if (((Integer) out.get("gnRetVal"))!= null ) {
				ret_val = ((Integer) out.get("gnRetVal")).intValue();
			}
			
			String error_text = (String)out.get("gsErrText");
			
			
			if ( (error_code != BomWebPortalConstant.ERRCODE_SUCCESS)) {
				InstallFee = null;
			} else {
				InstallFee.setImsInstallationInstallmentPlanList((List) out.get("o_install_detail_cur"));			
				InstallFee.setErrorCode(error_code);
				InstallFee.setErrorText(error_text);	
				logger.debug("getOTCByBasketId() output record = " + gson.toJson(InstallFee));
			}
			
	    	return InstallFee;
			
		} catch (Exception e) {
			logger.error("Exception caught in getOTCByBasketId()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	public InstallFeeUI getSpecialOTC(String basketId,String[] itemIdStr)throws DAOException {
		InstallFeeUI InstallFee = new InstallFeeUI();
		logger.info("getSpecialOTC is called");
		Connection conn = DataSourceUtils.getConnection(jdbcTemplate.getDataSource());
		
		OraArrayVarChar2 a = new OraArrayVarChar2(ORACLE_NUM_ARRAY, itemIdStr);
			try {	
				
		        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
		        	.withSchemaName("ops$cnm")
		        	.withCatalogName("pkg_SB_IMS_LTS")
		        	.withProcedureName("get_special_install_ot")  
		        	.declareParameters(
		        			new SqlParameter("i_basket_id", Types.VARCHAR),
		        			new SqlParameter("i_item_id_str", Types.ARRAY),
		        			new SqlOutParameter("o_install_detail_cur", OracleTypes.CURSOR, new InstallationInstallmentPlanMapper()),
							new SqlOutParameter("gnRetVal", Types.INTEGER),
							new SqlOutParameter("gnErrCode", Types.INTEGER),
							new SqlOutParameter("gsErrText", Types.VARCHAR));
		       
				MapSqlParameterSource inMap = new MapSqlParameterSource();
				inMap.addValue("i_basket_id", basketId);
				inMap.addValue("i_item_id_str", a.getOracleArray(conn));
				SqlParameterSource in = inMap;
//				logger.debug("getSpecialOTC human input:"+gson.toJson(in));			
				Map out = jdbcCall.execute(in);
				logger.debug("getSpecialOTC human output:"+gson.toJson(out));
		        
				int error_code = BomWebPortalConstant.ERRCODE_FAIL;
				int ret_val = BomWebPortalConstant.ERRCODE_FAIL; 
				
				if (((Integer) out.get("gnErrCode"))!= null ) {
					error_code = ((Integer) out.get("gnErrCode")).intValue();
				}
				
				if (((Integer) out.get("gnRetVal"))!= null ) {
					ret_val = ((Integer) out.get("gnRetVal")).intValue();
				}				
				String error_text = (String)out.get("gsErrText");
				if ( (error_code != BomWebPortalConstant.ERRCODE_SUCCESS)) {
					InstallFee = null;
				} else {
					InstallFee.setImsInstallationInstallmentPlanList((List) out.get("o_install_detail_cur"));			
					InstallFee.setErrorCode(error_code);
					InstallFee.setErrorText(error_text);	
					logger.debug("getOTCByBasketId() output record = " + gson.toJson(InstallFee));
				}		
			} catch (Exception e) {
				logger.error("Exception caught in getOTCByBasketId()", e);
				throw new DAOException(e.getMessage(), e);
			}
			
		return  InstallFee;
			
	}

}

