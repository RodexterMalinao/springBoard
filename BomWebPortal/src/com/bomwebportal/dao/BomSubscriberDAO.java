package com.bomwebportal.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.dto.BomOrderDTO;
import com.bomwebportal.dto.BomMupInfoDTO;

public class BomSubscriberDAO extends BaseDAO {

	protected final Log logger = LogFactory.getLog(getClass());
	
	public List<BomMupInfoDTO> getBomMupInfoDTOList(String srvNum) throws DAOException {
		List<BomMupInfoDTO> bomMupInfoDTOList = new ArrayList<BomMupInfoDTO>();
		logger.info("getBomMupInfoDTOList List is called");
		String SQL = "select /*+leading(SA) */\n"
				+ " s.srv_num\n"
				+ ",sa.acct_num\n"
				+ ",s.service_id\n"
				+ ",ata.attb_type_desc --sql only, no field in DTO\n"
				+ ",aa.attb_value pri_sec_ind_value\n"
				+ ",p.prod_id\n"
				+ ",p.prod_cd\n"
				+ ",p.prod_desc\n"
				+ "from b_account_service_assgn sa\n"
				+ "    ,b_service               s\n"
				+ "    ,b_subc_grp_assgn        ga\n"
				+ "    ,b_offer_sub             os\n"
				+ "    ,b_subc_offer_prod       op\n"
				+ "    ,b_product_attb_a        paa\n"
				+ "    ,b_attb_a                aa\n"
				+ "    ,b_attb_type_a           ata\n"
				+ "    ,b_product_a             p\n"
				+ "where s.system_id = 'MOB'\n"
				+ "and s.service_id = sa.service_id\n"
				+ "and s.service_id = ga.subc_mem_num\n"
				+ "and s.eff_end_date is null\n"
				+ "and ga.subc_grp_num = os.subc_grp_num\n"
				+ "and os.eff_end_date is null\n"
				+ "and os.offer_sub_key = op.offer_sub_key\n"
				+ "and op.eff_end_date is null\n"
				+ "and op.prod_id = p.prod_id\n"
				+ "and op.prod_id = paa.prod_id\n"
				+ "and paa.prod_attb_id = aa.attb_id\n"
				+ "and aa.attb_type_id = ata.attb_type_id\n"
				+ "and ata.attb_type_desc = 'PRI_SEC_IND'\n"
				+ "and aa.attb_value in ('Primary', 'Secondary')\n"
				+ "and sa.system_id = 'MOB'\n"
				+ "and sa.acct_num = (select sa.acct_num\n"
				+ "                  from b_account_service_assgn sa, b_service s\n"
				+ "                  where s.system_id = 'MOB'\n"
				+ "                  and s.service_id = sa.service_id\n"
				+ "                  and s.srv_num = :v_srv_num\n"
				+ "                  and sa.eff_end_date is null)";
		
		ParameterizedRowMapper<BomMupInfoDTO> mapper = new ParameterizedRowMapper<BomMupInfoDTO>() {
			public BomMupInfoDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				BomMupInfoDTO dto = new BomMupInfoDTO();
				dto.setSrvNum(rs.getString("srv_num"));
				dto.setAcctNum(rs.getString("acct_num"));
				dto.setServiceId(rs.getString("service_id"));
				dto.setPriSecIndValue(rs.getString("pri_sec_ind_value"));
				dto.setProdId(rs.getString("prod_id"));
				dto.setProdCd(rs.getString("prod_cd"));
				dto.setProdDesc(rs.getString("prod_desc"));
				return dto;
			}
		};
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("v_srv_num", srvNum);

		try {
			logger.debug("getBomMupInfoDTOList List: " + SQL);
			bomMupInfoDTOList = simpleJdbcTemplate.query(SQL, mapper, params);

		} catch (EmptyResultDataAccessException erdae) {
			logger.info(
					"EmptyResultDataAccessException caught in getBomMupInfoDTOList()",
					erdae);
			bomMupInfoDTOList = null;
		} catch (Exception e) {
			logger.error("Exception caught in getBomMupInfoDTOList()", e);
			throw new DAOException(e.getMessage(), e);
		}
		return bomMupInfoDTOList;
	}
	
	public String getBomServiceId(String msisdn) throws DAOException {
		
		String sql 	= 	"SELECT SERVICE_ID "
					+ 	"FROM B_SERVICE "
					+	"WHERE SRV_NUM = :msisdn "
					+	"AND SYSTEM_ID = 'MOB' "
					+	"AND EFF_END_DATE IS NULL";

		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("msisdn", msisdn);
	
		try {
			logger.debug("getServiceId() @ SubscriberDAO:" + sql);
			String serviceId = (String) simpleJdbcTemplate.queryForObject(sql,String.class,params);
			if (serviceId != null && serviceId.length()>0) {
				return serviceId;
			} else {
				return null;
			}
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException in getBomServiceId()");
		} catch (Exception e) {
			logger.info("Exception caught in getBomServiceId():", e);
			throw new DAOException(e.getMessage(), e);
		}
		return null;
	}
	
	
	/////////////////////////////////////////////////////
	public String getItemCodeLkup(String itemCd) throws DAOException {
		logger.debug("getItemCodeLkup @ BomSubscriberDAO");
		
		String sql = "select item_cd_desc from b_item_code_a where item_cd = :itemCd";
		
		if (itemCd == null || itemCd == ""){
			return null;
		}		
		String desc = null;						
		try {
		     MapSqlParameterSource params = new MapSqlParameterSource();
             params.addValue("itemCd", itemCd);
          
			
			desc = (String) simpleJdbcTemplate.queryForObject(sql, String.class, params);
	
			if (desc!=null){
				return desc;
			}
		
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException");
		} catch (Exception e) {
			logger.error("Exception caught in getItemCodeLkup()", e);
			throw new DAOException(e.getMessage(), e);
		}
		return null;

	}
	
	public List<BomOrderDTO> getBomPendingOrder(String serviceId) throws DAOException {
		List <BomOrderDTO> list = null;
		String SQL = "SELECT so.oc_id, " +
				"  so.bus_txn_type, " +
				"  od.srv_req_date, " +
				"  so.shop_code " +
				"FROM b_ord_dtl od, " +
				"  b_srv_ord so " +
				"WHERE od.cc_service_id = :serviceId " +
				"AND od.oc_id           = so.oc_id " +
				"AND so.status         IN ('01','03')";

		ParameterizedRowMapper<BomOrderDTO> mapper = new ParameterizedRowMapper<BomOrderDTO>() {

			public BomOrderDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				BomOrderDTO bomOrder = new BomOrderDTO();
				bomOrder.setOcid(rs.getString("oc_id"));
				bomOrder.setBusTxnType(rs.getString("bus_txn_type"));
				bomOrder.setShopCode(rs.getString("shop_code"));
				bomOrder.setSrvReqDate(rs.getDate("srv_req_date"));
				return bomOrder;
			}
		};
		
		try {
		     MapSqlParameterSource params = new MapSqlParameterSource();
             params.addValue("serviceId", serviceId);
          
			
			logger.debug("getBomPendingOrder @ BomOrderDAO: " + SQL);
			list = simpleJdbcTemplate.query(SQL, mapper, params);
			
			return list;
		
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException");
		} catch (Exception e) {
			logger.error("Exception caught in getBomPendingOrder()", e);
			throw new DAOException(e.getMessage(), e);
		}
		return null;

	}
	
	public int getCouponAccountInfo(String msisdn, String idDocType, String idDocNum) throws DAOException {
		logger.info("getCouponAccountInfo() is called");
		int result;
		String SQL = 
			"select " +
			"count(1) \n " +
			"from \n " +
			"b_customer c \n " +
			",b_account a \n " +
			",b_account_details_mob adm \n " +
			",b_service s \n " +
			",b_account_service_assgn asa \n " +
			"where \n " +
			"c.system_id = 'MOB' \n " +
			"and c.id_doc_type = :v_doc_type \n " +
			"and c.id_doc_num = :v_doc_num \n " +
			"and c.cust_num = a.cust_num \n " +
			"and s.system_id = 'MOB' \n " +
			"and S. srv_num = :v_srv_num \n " +
			"and s.eff_end_date is null \n " +
			"and s.service_id = asa.service_id \n " +
			"and asa.system_id = 'MOB' \n " +
			"and asa.eff_end_date is null \n " +
			"and asa.acct_num = a.acct_num \n " +
			"and a.system_id = 'MOB' \n " +
			"and a.acct_num = adm.acct_num \n " +
			"and a.eff_end_date is null \n ";
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("v_doc_type", idDocType);
		params.addValue("v_doc_num", idDocNum);
		params.addValue("v_srv_num", msisdn);
		try {
			result = (int) simpleJdbcTemplate.queryForObject(SQL, Integer.class, params);
			return result;
		} catch (Exception e) {
			logger.info("Exception caught in getCouponAccountInfo():", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public int checkOneNumber(String serviceId) throws DAOException {
		logger.info("checkOneNumber() is called");
		String SQL = "SELECT COUNT(*) FROM b_cc_epp_lkup_mob WHERE epp_grp_id = (SELECT DISTINCT epp_grp_id FROM b_cc_epp_lkup_mob WHERE service_id = :V_SERVICE_ID AND epp_ind = 'P' AND eff_end_date is null) AND epp_ind = 'S' and eff_end_date is null";
		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("V_SERVICE_ID", serviceId);
			int result = (int) simpleJdbcTemplate.queryForObject(SQL, Integer.class, params);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception caught in checkOneNumber()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
}
