package com.bomwebportal.mob.dao.bom;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.bomwebportal.exception.DAOException;
import com.bomwebportal.mob.ccs.dto.BomOrderPposDTO;
import com.bomwebportal.util.Utility;

public class BomOrderPposDAO extends BaseDAO {

	protected final Log logger = LogFactory.getLog(getClass());
	
	public List<BomOrderPposDTO> getBomOrderPpos(String ccsMode) throws DAOException {
		
    /*  String oldSQL = "SELECT oc_id, sm_num, sm_total_amt, ppos_payment_status, sm_type, "
				 + "       sm.bom_desc sm_type_desc "
				 + "  FROM b_ord_ppos a, (SELECT bom_code, bom_desc "
				 + "                        FROM b_lookup lo "
				 + "                       WHERE bom_grp_id = 'PPOS_SM_TYPE') sm "
				 + " WHERE TO_NUMBER (sm_type) = sm.bom_code " 
				 + " AND to_char(last_upd_date,'yyyymmdd') = to_char(sysdate, 'yyyymmdd') " 
				 + " AND sm_num like ? " 
				 + " and agreement_num like 'C%' ";*/
		
		String SQL = "SELECT oc_id, sm_num, sm_total_amt, ppos_payment_status, sm_type, "
				+ "  sm.bom_desc sm_type_desc "
				+ " FROM b_ord_ppos a, (SELECT bom_code, bom_desc "
				+ "							 FROM b_lookup lo "
				+ " 						WHERE bom_grp_id = 'PPOS_SM_TYPE') sm "
                + " WHERE TO_NUMBER (sm_type) = sm.bom_code "
                + " AND to_char(last_upd_date,'yyyymmdd') = to_char(sysdate, 'yyyymmdd') "
                + " and (agreement_num like 'C%' or agreement_num like 'D%')";
		
		ParameterizedRowMapper<BomOrderPposDTO> mapper = new ParameterizedRowMapper<BomOrderPposDTO>() {

			public BomOrderPposDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				BomOrderPposDTO bomOrder = new BomOrderPposDTO();
				bomOrder.setSmNum(rs.getString("sm_num"));
				bomOrder.setSmTotalAmt(rs.getBigDecimal("sm_total_amt"));
				bomOrder.setPposPaymentStatus(rs.getString("ppos_payment_status"));
				bomOrder.setSmType(rs.getString("sm_type"));
				bomOrder.setSmTypeDesc(rs.getString("sm_type_desc"));
				bomOrder.setOcid(rs.getString("oc_id"));
				return bomOrder;
			}
		};
		
		try {
			logger.debug("getBomOrderPpos @ BomOrderPposDAO: " + SQL);
			return simpleJdbcTemplate.query(SQL, mapper, "%" + ccsMode + "%");
			
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException");
		} catch (Exception e) {
			logger.error("Exception caught in getBomOrderPpos()", e);
			throw new DAOException(e.getMessage(), e);
		}
		return null;

	}	
	
	public List<BomOrderPposDTO> getUpdatedBomOrder(String ccsMode) throws DAOException {
		
		String SQL = "SELECT so.oc_id, so.status, od.srv_req_date "
				 + "  FROM b_srv_ord so, b_ord_dtl od "
				 + " WHERE so.oc_id = od.oc_id "
				 + "   AND so.oc_id IN ( "
				 + "                 SELECT DISTINCT (oc_id) "
				 + "                            FROM b_mob_bulk_ord_ctrl "
				 + "                           WHERE shop_code = ? "
				 + "                             AND last_updated_date >= SYSDATE - 60) "
				 + "   AND TO_CHAR (so.last_upd_date, 'yyyymmdd') = TO_CHAR (SYSDATE, 'yyyymmdd') ";

		
		ParameterizedRowMapper<BomOrderPposDTO> mapper = new ParameterizedRowMapper<BomOrderPposDTO>() {

			public BomOrderPposDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				BomOrderPposDTO bomOrder = new BomOrderPposDTO();
				bomOrder.setOcid(rs.getString("oc_id"));
				bomOrder.setBomStatus(rs.getString("status"));
				bomOrder.setBomServReqDate(rs.getDate("srv_req_date"));
				return bomOrder;
			}
		};
		
		try {
			logger.debug("getUpdatedBomOrder @ BomOrderPposDAO: " + SQL);
			return simpleJdbcTemplate.query(SQL, mapper, ccsMode);
			
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException");
		} catch (Exception e) {
			logger.error("Exception caught in getUpdatedBomOrder()", e);
			throw new DAOException(e.getMessage(), e);
		}
		return null;

	}
	
	
	public List<BomOrderPposDTO> getYesterdayRetailShopBomOrderStatus(String toDateString) throws DAOException {
		
	
		String SQL = "select SO.OC_ID, SO.STATUS, OD.SRV_REQ_DATE, OD.AGREEMENT_NUM ORDER_ID\n" +
			"  from B_SRV_ORD SO,\n" + 
			"       B_ORD_DTL OD,\n" + 
			"       (select distinct OLS.OC_ID\n" + 
			"          from B_MOB_BULK_ORD_CTRL MBOC, B_ORD_LATEST_STATUS OLS\n" + 
			"         where MBOC.OC_ID = OLS.OC_ID\n" + 
			"           and MBOC.SHOP_CODE <> 'CCS'\n" + 
			"           and MBOC.LAST_UPDATED_DATE >= sysdate - 90\n" + 
			"           and MBOC.CHECK_PT = 'C09999'\n" + 
			"           and TO_CHAR(OLS.LAST_UPD_DATE, 'dd/mm/yyyy') =\n" + 
			"               TO_CHAR(TO_DATE(?, 'dd/mm/yyyy') - 1, 'dd/mm/yyyy')\n" + 
			"           and OLS.BOM_STATUS = '02'\n" + 
			"           and OLS.DTL_ID = '1'\n" + 
			"           and OLS.DTL_SEQ = '1') A\n" + 
			" where SO.OC_ID = A.OC_ID\n" + 
			"   and SO.OC_ID = OD.OC_ID\n" + 
			"   and OD.OC_ID = A.OC_ID";



		
		ParameterizedRowMapper<BomOrderPposDTO> mapper = new ParameterizedRowMapper<BomOrderPposDTO>() {

			public BomOrderPposDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				BomOrderPposDTO bomOrder = new BomOrderPposDTO();
				bomOrder.setOcid(rs.getString("OC_ID"));
				bomOrder.setBomStatus(rs.getString("STATUS"));
				bomOrder.setBomServReqDate(rs.getDate("SRV_REQ_DATE"));
				bomOrder.setOrderId(rs.getString("ORDER_ID"));
				return bomOrder;
			}
		};
		
		try {
			logger.debug("getRetailShopBomOrderStatus @ BomOrderPposDAO: " + SQL);
			return simpleJdbcTemplate.query(SQL, mapper,toDateString );
			
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException");
		} catch (Exception e) {
			logger.error("Exception caught in getRetailShopBomOrderStatus()", e);
			throw new DAOException(e.getMessage(), e);
		}
		return null;

	}
}
