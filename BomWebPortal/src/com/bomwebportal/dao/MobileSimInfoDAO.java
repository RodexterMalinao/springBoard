package com.bomwebportal.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;






import org.springframework.jdbc.core.simple.SimpleJdbcCall;

import com.bomwebportal.dto.SimDTO;
//import com.bomwebportal.dto.*;
import com.bomwebportal.exception.*;

//import com.pccw.bom.mob.schemas.ProductDTO;

public class MobileSimInfoDAO extends BaseDAO {

	protected final Log logger = LogFactory.getLog(getClass());

	// for summary page use. add by wilson 20110503
	public String getBomWebSimItemId(String basketId, String posItemCd)
			throws DAOException {

		String bomWebSimItemId = "";

		String SQL = "select distinct ippa.item_id\n"
				+ "  from w_item_product_pos_assgn ippa, w_item i, w_basket_item_assgn bia\n"
				+ " where i.id = ippa.item_id\n"
				+ "   and i.id = bia.item_id\n" 
				+ "   and i.type = 'SIM'\n"
				+ "   and bia.basket_id = ? and ippa.POS_ITEM_CD =? ";

		try {
			logger.debug("getBomWebSimItemId @ MobileSimInfoDAO: " + SQL);
			bomWebSimItemId = (String) simpleJdbcTemplate.queryForObject(SQL,
					String.class, basketId, posItemCd);

		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException in getBomWebSimItemId()");
			bomWebSimItemId = "";
		} catch (Exception e) {
			logger.error("Exception caught in getBomWebSimItemId()", e);
			throw new DAOException(e.getMessage(), e);
		}
		return bomWebSimItemId;
	}
	
	public String getSimWaiveReason(String orderId, String simItemId)
			throws DAOException {

		String simWaiveReason = "";

		String SQL = "select WAIVE_REASON from bomweb_subscribed_item "
				+ "where order_id = :orderId "
				+ "and id = :itemId "
				+ "and type='SIM' "
				+ "and rownum = 1";

		try {
			logger.debug("getSimWaiveReason @ MobileSimInfoDAO: " + SQL);
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("itemId",simItemId);
			params.addValue("orderId",orderId);
			simWaiveReason = (String) simpleJdbcTemplate.queryForObject(SQL,
					String.class, params);

		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException in getSimWaiveReason()");
			simWaiveReason = "";
		} catch (Exception e) {
			logger.error("Exception caught in getSimWaiveReason()", e);
			throw new DAOException(e.getMessage(), e);
		}
		return simWaiveReason;
	}
	
	public List<String[]> getSimPrice(String itemId, Date appDate)
			throws DAOException {

		List<String[]> list = new ArrayList<String[]>();

		String SQL = "select ip.WAIVABLE, ip.ONETIME_AMT "
				+ "from w_item_pricing ip "
				+ "join w_item i on ip.id = i.id "
				+ "where i.type = 'SIM' "
				+ "and trunc(:appDate) between trunc(ip.eff_start_date) and trunc(nvl(ip.eff_end_date, sysdate)) "
				+ "and i.id = :itemId";

		ParameterizedRowMapper<String[]> mapper = new ParameterizedRowMapper<String[]>() {

			public String[] mapRow(ResultSet rs, int rowNum) throws SQLException {
				String[] result = new String[2];
				result[0] = rs.getString("WAIVABLE");
				result[1] = rs.getString("ONETIME_AMT");
				return result;
			}
		};
		
		try {
			logger.debug("getBomWebSimItemId @ MobileSimInfoDAO: " + SQL);
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("itemId",itemId);
			params.addValue("appDate",appDate, Types.DATE);
			list = simpleJdbcTemplate.query(SQL, mapper,params);

		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException in getBomWebSimItemId()");
			list = null;
		} catch (Exception e) {
			logger.error("Exception caught in getBomWebSimItemId()", e);
			throw new DAOException(e.getMessage(), e);
		}
		return list;
	}
	
	//for MobileSimInfoController, to load list for checking  
	public List<String[]> getBomWebItemCdList(String basketId, String appDate)
			throws DAOException {

		List<String[]> simItemList = new ArrayList(); //[0]POS_ITEM_CD, [1]item_id
	
		String SQL = "select ippa.POS_ITEM_CD, ippa.item_id\n"
				+ "  from w_item_product_pos_assgn ippa, w_item i, w_basket_item_assgn bia\n"
				+ " where i.id = ippa.item_id\n"
				+ "   and i.id = bia.item_id\n" + "   and i.type = 'SIM'\n"
				+ "   and bia.basket_id = ? "
				+ "   and trunc(TO_DATE(?, 'DD/MM/YYYY')) between bia.eff_start_date and trunc(nvl(bia.eff_end_date, sysdate))";
		ParameterizedRowMapper<String[]> mapper = new ParameterizedRowMapper<String[]>() {

			public String[] mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				String[] item = new String[2];
				item[0] = rs.getString("POS_ITEM_CD");
				item[1] = rs.getString("item_id");
				return item;
			}
		};
		try {
			simItemList = simpleJdbcTemplate.query(SQL, mapper, basketId, appDate);
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException");
			simItemList = null;
		} catch (Exception e) {
			logger.error("Exception caught in getBomWebItemCdList()", e);
			throw new DAOException(e.getMessage(), e);
		}
		return simItemList;
	}
	
	public List<String[]> getDSLocationList() throws DAOException {

			List<String[]> locationList = new ArrayList(); //[0]POS_ITEM_CD, [1]item_id
		
			String SQL = "select location_cd, type, venue "
					+ "from BOMWEB_DS_LOCATION "
					+ "order by type asc, venue asc";
			ParameterizedRowMapper<String[]> mapper = new ParameterizedRowMapper<String[]>() {

				public String[] mapRow(ResultSet rs, int rowNum)
						throws SQLException {
					String[] item = new String[3];
					item[0] = rs.getString("location_cd");
					item[1] = rs.getString("type");
					item[2] = rs.getString("venue");
					return item;
				}
			};
			try {
				locationList = simpleJdbcTemplate.query(SQL, mapper);
			} catch (EmptyResultDataAccessException erdae) {
				logger.info("EmptyResultDataAccessException");
				locationList = null;
			} catch (Exception e) {
				logger.error("Exception caught in getDSLocationList()", e);
				throw new DAOException(e.getMessage(), e);
			}
			return locationList;
		}

	
	public String getMockSimItemId(String basketId, String simType, String stockPool, java.util.Date appDate) throws DAOException {

		try {			
			SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
				.withSchemaName("OPS$CNM")
            	.withCatalogName("PKG_SB_MOB_UTIL")
            	.withProcedureName("MOCK_SIM_ITEM");
			jdbcCall.setAccessCallParameterMetaData(false);
			jdbcCall.declareParameters(
					new SqlParameter("i_basket_id", Types.VARCHAR),
					new SqlParameter("i_nfc_ind", Types.VARCHAR),
					new SqlParameter("i_app_date", Types.DATE),
					new SqlParameter("i_stock_pool", Types.VARCHAR),
					new SqlOutParameter("o_item_id", Types.INTEGER),
					new SqlOutParameter("gnRetVal", Types.INTEGER), 
					new SqlOutParameter("gnErrCode", Types.INTEGER), 
					new SqlOutParameter("gnErrText", Types.VARCHAR));
			
			MapSqlParameterSource inMap = new MapSqlParameterSource();
			inMap.addValue("i_basket_id", basketId);
			inMap.addValue("i_nfc_ind", simType);
			inMap.addValue("i_app_date", appDate);
			inMap.addValue("i_stock_pool", stockPool);
			SqlParameterSource in = inMap;
			
			Map out = jdbcCall.execute(in);
			
			int error_code = -10;
		    int retVal = -10;
		    String error_text = null;
	
		    if (((Integer) out.get("gnErrCode")) != null) {
				error_code = ((Integer) out.get("gnErrCode")).intValue();
				logger.info("OPS$CNM.PKG_SB_MOB_UTIL.MOCK_SIM_ITEM() output gnErrCode = "
					+ error_code);
		    }
	
		    if (((Integer) out.get("gnRetVal")) != null) {
				retVal = ((Integer) out.get("gnRetVal")).intValue();
				logger.info("OPS$CNM.PKG_SB_MOB_UTIL.MOCK_SIM_ITEM() output gnRetVal = "
					+ retVal);
		    }
	
		    if ((out.get("gnErrText")) != null) {
				error_text = out.get("gnErrText").toString();
				logger.info("OPS$CNM.PKG_SB_MOB_UTIL.MOCK_SIM_ITEM() output gnErrText = "
					+ error_text);
		    } else {
				error_text = null;
				logger.info("OPS$CNM.PKG_SB_MOB_UTIL.MOCK_SIM_ITEM() output gnErrText = "
					+ error_text);
		    }
	
		    
			
			String itemId = out.get("o_item_id").toString();
			
			logger.info("PKG_SB_MOB_UTIL.MOCK_SIM_ITEM() o_item_id = " + itemId);
			
			return itemId;
			
		} catch (Exception e) {
			logger.error("Exception caught in getMockSimItemId()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public String getPendingOrderExistWithIccidFromBomwebSim(String iccid)
			throws DAOException {

		String bomWebOrderId = "";

		String SQL = "SELECT O.ORDER_ID " +
				"FROM BOMWEB_ORDER O, " +
				"  BOMWEB_SIM SIM " +
				"WHERE O.ORDER_ID    = SIM.ORDER_ID " +
				"AND O.ORDER_STATUS IN ('INITIAL', 'REVIEWING', 'FAILED', 'REJECTED') " +
				"AND SIM.ICCID       = ? " +
				"AND ROWNUM          = 1 ";

		try {
			logger.debug("getBomWebSimItemId @ MobileSimInfoDAO: " + SQL);
			bomWebOrderId = (String) simpleJdbcTemplate.queryForObject(SQL,
					String.class, iccid);

		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException in getBomWebSimItemId()");
			bomWebOrderId = "";
		} catch (Exception e) {
			logger.error("Exception caught in getBomWebSimItemId()", e);
			throw new DAOException(e.getMessage(), e);
		}
		return bomWebOrderId;
	}
	
	public String getPendingOrderExistWithIccidOrderIdFromBomwebSim(String iccid, String orderId)
			throws DAOException {

		String bomWebOrderId = "";

		String SQL = "SELECT O.ORDER_ID " +
				"FROM BOMWEB_ORDER O, " +
				"  BOMWEB_SIM SIM " +
				"WHERE O.ORDER_ID    = SIM.ORDER_ID " +
				"AND O.ORDER_STATUS IN ('INITIAL', 'REVIEWING', 'FAILED', 'REJECTED') " +
				"AND SIM.ICCID       = ? " +
				"AND O.ORDER_ID     != ? " +
				"AND ROWNUM          = 1 ";

		try {
			logger.debug("getBomWebSimItemId @ MobileSimInfoDAO: " + SQL);
			bomWebOrderId = (String) simpleJdbcTemplate.queryForObject(SQL,
					String.class, iccid, orderId);

		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException in getBomWebSimItemId()");
			bomWebOrderId = "";
		} catch (Exception e) {
			logger.error("Exception caught in getBomWebSimItemId()", e);
			throw new DAOException(e.getMessage(), e);
		}
		return bomWebOrderId;
	}
	
	public String getPendingOrderExistWithIccidFromBomwebOrdMobMember(String iccid)
			throws DAOException {

		String bomWebOrderId = "";

		String SQL = "SELECT O.ORDER_ID " +
				"FROM bomweb_order O, " +
				"  bomweb_ord_mob_member OMM " +
				"WHERE O.ORDER_ID    = OMM.PARENT_ORDER_ID " +
				"AND O.ORDER_STATUS IN ('INITIAL', 'REVIEWING', 'FAILED', 'REJECTED') " +
				"AND OMM.iccid       = ? " +
				"AND ROWNUM          = 1 ";

		try {
			logger.debug("getBomWebSimItemId @ MobileSimInfoDAO: " + SQL);
			bomWebOrderId = (String) simpleJdbcTemplate.queryForObject(SQL,
					String.class, iccid);

		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException in getBomWebSimItemId()");
			bomWebOrderId = "";
		} catch (Exception e) {
			logger.error("Exception caught in getBomWebSimItemId()", e);
			throw new DAOException(e.getMessage(), e);
		}
		return bomWebOrderId;
	}
	
	public String getPendingOrderExistWithIccidOrderIdFromBomwebOrdMobMember(String iccid, String orderId)
			throws DAOException {

		String bomWebOrderId = "";

		String SQL = "SELECT O.ORDER_ID " +
				"FROM bomweb_order O, " +
				"  bomweb_ord_mob_member OMM " +
				"WHERE O.ORDER_ID    = OMM.PARENT_ORDER_ID " +
				"AND O.ORDER_STATUS IN ('INITIAL', 'REVIEWING', 'FAILED', 'REJECTED') " +
				"AND OMM.iccid       = ? " +
				"AND O.ORDER_ID     != ? " +
				"AND ROWNUM          = 1 ";

		try {
			logger.debug("getBomWebSimItemId @ MobileSimInfoDAO: " + SQL);
			bomWebOrderId = (String) simpleJdbcTemplate.queryForObject(SQL,
					String.class, iccid, orderId);

		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException in getBomWebSimItemId()");
			bomWebOrderId = "";
		} catch (Exception e) {
			logger.error("Exception caught in getBomWebSimItemId()", e);
			throw new DAOException(e.getMessage(), e);
		}
		return bomWebOrderId;
	}
	
}
