package com.bomwebportal.ims.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;

import com.bomwebportal.dao.BaseDAO;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.ims.dto.IOOfferOTCSchemeDTO;
import com.bomwebportal.ims.dto.OrderImsDTO;
import com.bomwebportal.util.BomWebPortalConstant;

public class ImsBomOrderDAO extends BaseDAO{
	
	public List<OrderImsDTO> getBomOcDetail(String orderId) throws DAOException{
//		logger.info("getBomOcDetail with order id:"+orderId);
		String SQL = "	SELECT od.oc_id, os.srv_id, bot.order_id,	"+
		"	       (SELECT cust_num	"+
		"	          FROM b_ord_srv_acct	"+
		"	         WHERE oc_id = od.oc_id AND chrg_catg_cd = 'N') cust_num	"+
		"	  FROM bomweb_order_tmp bot, b_ord_request bor, b_ord_dtl od, b_ord_srv os, B_ORD_LATEST_STATUS bols, b_srv_ord bso	"+
		"	 WHERE bot.order_id = ?		   " +
		"	   and od.oc_id = bols.oc_id " +
		"	   and (bols.bom_status not in ('03','04','06','07') or (bols.rea_cd <> '00000' and bols.rea_cd is not null ))  "+// steven added 20140709 for ret
		"	   AND bot.LOB = 'IMS'	"+
		"	   AND bot.req_id = bor.req_id	" +
		"	   and bor.STATUS = '02'  "+ // steven added 20140630 for ret
		"	   AND bor.related_oc_id IS NOT NULL	"+
		"	   AND TO_NUMBER (bor.related_oc_id) = od.oc_id	"+
		"	   AND od.oc_id = os.oc_id	"+
		"	   AND od.oc_id = bso.oc_id	"+
		"	   AND od.dtl_id = os.dtl_id	"+
		"	   and (bso.in_user <> 'OCMOE' or bso.in_user is null)	";
		
		ParameterizedRowMapper<OrderImsDTO> mapper = new ParameterizedRowMapper<OrderImsDTO>() {

			public OrderImsDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				OrderImsDTO dto = new OrderImsDTO();
				
				dto.setOrderId(rs.getString("order_id"));
				dto.setOcId(rs.getString("oc_id"));
				dto.setMsisdn(rs.getString("srv_id"));
				dto.setBomCustNo(rs.getString("cust_num"));

				return dto;
			}
		};
		
		try {
//			logger.debug("getBomOcDetail @ OrderDAO: " + SQL);
			List<OrderImsDTO> orders = simpleJdbcTemplate
					.query(SQL, mapper, orderId);
			
			return orders;
		
		} catch (Exception e) {
			logger.error("Exception caught in getBomOcDetail()", e);
			throw new DAOException(e.getMessage(), e);
		}
		
	}
	
	public List<Map<String, String>> getVimServiceType(String orderId) throws DAOException{
		logger.info("getVimServiceType");
		String SQL = "	SELECT od.oc_id, osi.l1_prod, bot.order_id, lp.vi_bit_rate,	"+
				"	       (SELECT DECODE (COUNT (*), 0, 'SD', 'HD')	"+
				"	          FROM b_srv_ord_prod a	"+
				"	         WHERE a.oc_id = od.oc_id	"+
				"	           AND io_ind = 'I'	"+
				"	           AND EXISTS (	"+
				"	                  SELECT *	"+
				"	                    FROM ims_parm b, ims_prdassoc c	"+
				"	                   WHERE c.rel_prd_id = LPAD (a.prod_id, 7, '0')	"+
				"	                     AND b.prd_id = c.prd_id	"+
				"	                     AND b.parm_typ_upr_desc = 'TV TYPE'	"+
				"	                     AND b.parm_val = 'HD')) tv_type	"+
				"	  FROM l1product lp,	"+
				"	       bomweb_order_tmp bot,	"+
				"	       b_ord_request bor,	"+
				"	       b_ord_dtl od,	"+
				"	       b_ord_srv os,	"+
				"	       b_ord_srv_ims osi	"+
				"	 WHERE bot.order_id = ?	"+
				"	   AND bot.LOB = 'IMS'	"+
				"	   AND bot.req_id = bor.req_id	"+
				"	   AND bor.related_oc_id IS NOT NULL	"+
				"	   AND TO_NUMBER (bor.related_oc_id) = od.oc_id	"+
				"	   AND od.dtl_id = 1	"+
				"	   AND od.oc_id = os.oc_id	"+
				"	   AND od.dtl_id = os.dtl_id	"+
				"	   AND od.oc_id = osi.oc_id	"+
				"	   AND od.dtl_id = osi.dtl_id	"+
				"	   AND lp.prd_cd = osi.l1_prod	";
		
		ParameterizedRowMapper<Map<String, String>> mapper = new ParameterizedRowMapper<Map<String, String>>() {

			public Map<String, String> mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				Map<String, String> map = new HashMap<String, String>();
				
				map.put("vi_bit_rate", rs.getString("vi_bit_rate"));
				map.put("tv_type", rs.getString("tv_type"));

				return map;
			}
		};
		
		try {
			logger.debug("getVimServiceType @ OrderDAO: " + SQL);
			List<Map<String, String>> map = simpleJdbcTemplate
					.query(SQL, mapper, orderId);
			
			return map;
		
		}catch (EmptyResultDataAccessException erdae) {
			return null;
		}catch (Exception e) {
			logger.error("Exception caught in getVimServiceType()", e);
			throw new DAOException(e.getMessage(), e);
		}

	}
	
	//Gary
	public List<String> getBrmVasDesc(String sbNum) throws DAOException{
		
		List<String> bomDescList;
		
		logger.debug("getBrmVasDesc");
		String SQL = "select l.bom_desc "+
			" 		FROM b_lookup l, b_service_rollout sr "+
			" 		WHERE sr.serbdyno= ? "+ 
			" 		AND l.BOM_GRP_ID='SB_SRV_CD_REMIND' "+
			" 		AND sr.service_code=l.BOM_CODE "+
			" 		AND nvl(sr.obsolete,'N')='N' "; 
		
		
		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {

			public String mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				String map = new String();
				
				map = (String)(rs.getString("bom_desc"));				

				return map;
			}
		};
		
		
		
		try {
			bomDescList =  simpleJdbcTemplate.query(SQL,mapper, sbNum);

		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException in getBrmVasDesc()");
			bomDescList = null;
		} catch (Exception e) {
			logger.error("Exception caught in getBrmVasDesc()", e);
			throw new DAOException(e.getMessage(), e);
		}
		
		return bomDescList;
		
	}
   public String getDragenAcct(String prmtAcct) throws DAOException{
		logger.info("getDragenAcct");
		String drgAcct;
		try{
							
			SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
	        	.withSchemaName("OPS$BOM")
	        	.withCatalogName("PKG_OC_IMS_SB")
	        	.withProcedureName("GET_DRG_ACCOUNT")
	        	.declareParameters(
	        			new SqlParameter("i_acctnb", Types.VARCHAR),
	        			new SqlOutParameter("i_drg_acctnb", Types.VARCHAR),	        		
	        			new SqlOutParameter("gnRetVal", Types.INTEGER),
						new SqlOutParameter("gnErrCode", Types.INTEGER),
						new SqlOutParameter("gsErrText", Types.VARCHAR));
			 
			MapSqlParameterSource inMap = new MapSqlParameterSource();
			inMap.addValue("i_acctnb", prmtAcct);
						
			SqlParameterSource in = inMap;
			
			Map out = jdbcCall.execute(in);
	        
			int error_code = BomWebPortalConstant.ERRCODE_FAIL;
			int ret_val = BomWebPortalConstant.ERRCODE_FAIL; 
			
			if (((Integer) out.get("gnErrCode"))!= null ) {
				error_code = ((Integer) out.get("gnErrCode")).intValue();
			}
			
			if (((Integer) out.get("gnRetVal"))!= null ) {
				ret_val = ((Integer) out.get("gnRetVal")).intValue();
			}
			
			String error_text = (String)out.get("gsErrText");
			
			
			logger.debug("getServiceSrd() output ret_val = " + ret_val);
			logger.debug("getServiceSrd() output error_code = " + error_code);
			logger.debug("getServiceSrd() output error_text = " + error_text);
			
			if ( (error_code != BomWebPortalConstant.ERRCODE_SUCCESS)) {
				drgAcct = null;
				throw new AppRuntimeException("Exception caught in getDragenAcct()");
			} else {
				logger.debug("getDragenAcct() output i_drg_acctnb = " + (String)out.get("i_drg_acctnb"));
							
				drgAcct = (String)out.get("i_drg_acctnb");							
				
			}
	        
			return drgAcct;
			
		} catch (Exception e) {
			logger.error("Exception caught in getDragenAcct()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
   public String getPrimaryAcct() throws DAOException{
		logger.info("getPrimaryAcct");
		String PrimaryAcct=null;
		try{
							
			SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
	        	.withSchemaName("OPS$IMS")
	        	.withCatalogName("netsop_main_pkg")
	        	.withFunctionName("new_net_acctnb");
//				jdbcCall.setAccessCallParameterMetaData(false);
	        	jdbcCall.declareParameters(	        	        		
	        			new SqlOutParameter("gnRetVal", Types.INTEGER),
						new SqlOutParameter("gnErrCode", Types.INTEGER),
						new SqlOutParameter("gsErrText", Types.VARCHAR));
			 
			MapSqlParameterSource inMap = new MapSqlParameterSource();

			SqlParameterSource in = inMap;
	        	
			Map out = jdbcCall.execute(in);
			
			if ((Integer)out.get("gnRetVal") != 0 ) {
				logger.error("Exception caught in saveNewOrder()");
				throw new AppRuntimeException("Exception caught in getPrimaryAcct()");
			} else {
				Number acctnum = (Number)out.get("return");
				PrimaryAcct = acctnum.toString();
			}
			
			//Number out = jdbcCall.executeFunction(Number.class, inMap);
			
			return PrimaryAcct;
			
		} catch (Exception e) {
			logger.error("Exception caught in getPrimaryAcct()", e);
			throw new AppRuntimeException("Exception caught in getPrimaryAcct()");
		}
   }
   
   public String getVIDepDrgnAcctnb(String depInd, String prmtAcct) throws DAOException {
	   logger.info("getViDepDrgnAcctNB");
	   String DrgnAcct = null;
	   try {
			SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
        	.withSchemaName("ops$ims")
        	.withCatalogName("PKG_CBSNG02")
        	.withProcedureName("SP_CBGETVIDEPDRGNACCTNB")
        	.declareParameters(
        			new SqlParameter("IACCTNB", Types.VARCHAR),
        			new SqlParameter("IDEPIND", Types.VARCHAR),
        			new SqlOutParameter("ODRGNACCTNB", Types.VARCHAR),	        		
        			new SqlOutParameter("OERRCODE", Types.INTEGER),
					new SqlOutParameter("OERRMSG", Types.VARCHAR));
		 
		MapSqlParameterSource inMap = new MapSqlParameterSource();
		
		inMap.addValue("IACCTNB", prmtAcct);
		inMap.addValue("IDEPIND", depInd);
					
		SqlParameterSource in = inMap;
		
		Map out = jdbcCall.execute(in);
		   
			int error_code = -1;
			String error_msg = "";
			if (((Integer) out.get("OERRCODE"))!= null ) {
				error_code = ((Integer) out.get("OERRCODE")).intValue();
			}
			if (((String) out.get("OERRMSG"))!= null ) {
				error_msg = (String) out.get("OERRMSG");
			}
			logger.info("OERRCODE: " + error_code);
			logger.info("OERRMSG: " + error_msg);
			
			if (error_code != 0 ) {
				logger.error("Exception caught in getViDepDrgnAcctNB()");
				throw new AppRuntimeException("Exception caught in getViDepDrgnAcctNB()");
			} else {
				DrgnAcct = (String) out.get("ODRGNACCTNB");
			}
			return DrgnAcct;
	   } catch (Exception e) {
		   logger.error("Exception caught in getViDepDrgnAcctNB()", e);
		   throw new AppRuntimeException(e.getMessage(), e);
	   }
   }
   
   public String getL1orderNum(String orderId) throws DAOException{
	   String l1OrdNum=null;
	   
	   String sql = "select c.l1_ord_num from b_legacy_ord_dtl c, b_ord_request b, bomweb_order_tmp a		"+
	   "where a.order_id = ?                                                   "+
	   "and a.req_id = b.req_id                                                            "+
	   "and b.related_oc_id = c.oc_id                                                      ";
	   
	   try {
		   l1OrdNum = simpleJdbcTemplate.queryForObject(sql, String.class, orderId);
	   } catch (Exception e){
		   l1OrdNum = "";
	   }
	   
	   return l1OrdNum;
   }
   
   public boolean testConnection() throws DAOException {
	   String sql = "select 1 from dual";
	   logger.info("Testing connection - " + sql);
	   
       try {
    	   simpleJdbcTemplate.queryForObject(sql, Integer.class, new HashMap<String,Object>());
    	   logger.info("Testing connection ok");
    	   return true;
       } catch (Exception e) {
    	   logger.error("Error while testing db connection");
    	   throw new DAOException("Test Connection Failed - " + e.getMessage(), e);
       }
   }
   
	public List<Map<String, String>> getCslCostomer(String mrt, String idDocType, String idDocNum) throws DAOException{
	
		   
		   String sql = "select cust_first_name, cust_last_name from b_ims_mrt_src		"+
		   "where mrt = ?                                                   "+
		   "and id_doc_type = ?                                                            "+
		   "and id_doc_num = ?                                                      ";
		   
	       ParameterizedRowMapper<Map<String, String>> mapper = new ParameterizedRowMapper<Map<String, String>>() {

				public Map<String, String> mapRow(ResultSet rs, int rowNum)
						throws SQLException {
					Map<String, String> map = new HashMap<String, String>();
					
					map.put("cust_first_name", rs.getString("cust_first_name"));
					map.put("cust_last_name", rs.getString("cust_last_name"));

					return map;
				}
			};
			try {
				logger.debug("getCslCostomer @ OrderDAO: " + sql);
				List<Map<String, String>> map = simpleJdbcTemplate
						.query(sql, mapper, mrt, idDocType, idDocNum);			
				
				return map;
			
			}catch (EmptyResultDataAccessException erdae) {
				return null;
			}catch (Exception e) {
				logger.error("Exception caught in getCslCostomer()", e);
				throw new DAOException(e.getMessage(), e);
			}
	}
	
	public List<IOOfferOTCSchemeDTO> getIOOneTimeCharge (String newPrdIdSrting, String oldPrdIdString)	throws DAOException {
		
		String SQL = "SELECT 'I' io_ind, prc_sch_cd, prc_sch_desc "+
					"FROM b_pricing_scheme_a WHERE prc_sch_id in "+
					"(SELECT prc_sch_id FROM b_pricing_assgn_a WHERE item_id in "+ 
					"(SELECT prod_id FROM b_product_a WHERE prod_id in "+ 
					"(select prod_id from b_offer_product_assgn_a where offer_sub_id in "+ 
					"(select offer_sub_id from b_offer_assgn_a where offer_id in "+ 
					"(SELECT offer_id FROM b_offer_a where offer_cd in ( "+
					"select parm_val from ims_parm "+ 
					"where prd_id in "+ 
					"(select prd_id from ims_prdassoc where rel_prd_id in "+ 
					"(select lpad(prod_id,7,0) from b_product_a where prod_id in ("+newPrdIdSrting+"))) "+ 
					"and parm_typ_upr_desc = 'ONETIME OFFER CD')))) "+
					"and prod_type='O') "+
					"AND item_type='P') "+
					"UNION "+
					"SELECT 'O' io_ind, prc_sch_cd, prc_sch_desc "+ 
					"FROM b_pricing_scheme_a WHERE prc_sch_id in "+ 
					"(SELECT prc_sch_id FROM b_pricing_assgn_a WHERE item_id in "+ 
					"(SELECT prod_id FROM b_product_a WHERE prod_id in "+ 
					"(select prod_id from b_offer_product_assgn_a where offer_sub_id in "+ 
					"(select offer_sub_id from b_offer_assgn_a where offer_id in "+ 
					"(SELECT offer_id FROM b_offer_a where offer_cd in ( "+
					"select parm_val from ims_parm "+ 
					"where prd_id in "+ 
					"(select prd_id from ims_prdassoc where rel_prd_id in "+ 
					"(select lpad(prod_id,7,0) from b_product_a where prod_id in ("+oldPrdIdString+"))) "+ 
					"and parm_typ_upr_desc = 'ONETIME OFFER CD')))) "+
					"and prod_type='O') "+
					"AND item_type='P') ";

		
		ParameterizedRowMapper<IOOfferOTCSchemeDTO> mapper = new ParameterizedRowMapper<IOOfferOTCSchemeDTO>() {

			public IOOfferOTCSchemeDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				IOOfferOTCSchemeDTO dto = new IOOfferOTCSchemeDTO();

				dto.setIOInd(rs.getString("io_ind"));
				dto.setSchemeCode(rs.getString("prc_sch_cd"));
				dto.setSchemeDesc(rs.getString("prc_sch_desc"));
			
				return dto;
			}
		};
		
		try {
			logger.debug("getIOOneTimeCharge @ OrderDAO: " + SQL);
			List<IOOfferOTCSchemeDTO> dtos = simpleJdbcTemplate
					.query(SQL, mapper);
			
			return dtos;
		
		} catch (Exception e) {
			logger.error("Exception caught in getIOOneTimeCharge()", e);

			throw new DAOException(e.getMessage(), e);
		}		
		
	}

}
