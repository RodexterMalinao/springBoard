package com.bomwebportal.mob.ccs.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;

import com.bomwebportal.dao.BaseDAO;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.mob.ccs.dto.CodeLkupDTO;
import com.bomwebportal.mob.ccs.dto.StockAssgnDTO;
import com.bomwebportal.mob.ccs.dto.StockCatalogDTO;
import com.bomwebportal.mob.ccs.dto.StockDTO;
import com.bomwebportal.mob.ccs.dto.StockManualDTO;
import com.bomwebportal.mob.ccs.dto.StockManualDetailDTO;
import com.bomwebportal.mob.ccs.dto.StockModelDetailsDTO;
import com.bomwebportal.mob.ccs.dto.StockModelResultDTO;
import com.bomwebportal.mob.ccs.dto.StockQuantityEnquiryDTO;
import com.bomwebportal.mob.ccs.dto.StockResultDTO;
import com.bomwebportal.mob.ccs.dto.StockUpdateDTO;
import com.bomwebportal.mob.ccs.dto.ui.HottestModelManagementUI;
import com.bomwebportal.mob.ccs.dto.ui.StockManualAssgnUI;

public class StockDAO extends BaseDAO {

    protected final Log logger = LogFactory.getLog(getClass());

    private static String getTempMiddleListByItemCodeSQL = "SELECT DISTINCT item_type type, "
	    + "  item_code item_code, "
	    + "  item_desc model, "
	    + "  assign_mode "
	    + "FROM bomweb_stock_catalog "
	    + "WHERE item_code LIKE ?";

    public List<StockCatalogDTO> getTempMiddleListByItemCode(String itemCode)
	    throws DAOException {
	logger.info("getTempMiddleListByItemCode @ StockDAO is called");
	List<StockCatalogDTO> itemList = new ArrayList<StockCatalogDTO>();

	ParameterizedRowMapper<StockCatalogDTO> mapper = new ParameterizedRowMapper<StockCatalogDTO>() {
	    public StockCatalogDTO mapRow(ResultSet rs, int rowNum)
		    throws SQLException {

		StockCatalogDTO tempDto = new StockCatalogDTO();
		tempDto.setScItemType(rs.getString("type"));
		tempDto.setScItemCode(rs.getString("item_code"));
		tempDto.setScItemDesc(rs.getString("model"));
		tempDto.setScAssignMode(rs.getString("assign_mode"));
		return tempDto;
	    }
	};

	try {
	    logger.info("getTempMiddleListByItemCode() @ StockDAO: "
		    + getTempMiddleListByItemCodeSQL);

	    itemList = simpleJdbcTemplate.query(getTempMiddleListByItemCodeSQL,
		    mapper, "%" + itemCode + "%");
	} catch (EmptyResultDataAccessException erdae) {
	    logger.info("EmptyResultDataAccessException in getTempMiddleListByItemCode()");

	    itemList = null;
	} catch (Exception e) {
	    logger.info("Exception caught in getTempMiddleListByItemCode():", e);

	    throw new DAOException(e.getMessage(), e);
	}
	return itemList;
    }

    private static String getTempMiddleListSQL = "SELECT DISTINCT item_type type, "
	    + "  item_code item_code, "
	    + "  item_desc model, "
	    + "  assign_mode "
	    + "FROM bomweb_stock_catalog "
	    + "WHERE item_type = nvl(?, item_type) " + "AND item_desc LIKE ?";

    public List<StockCatalogDTO> getTempMiddleList(String type, String model)
	    throws DAOException {
	logger.debug("getTempMiddleList @ StockDAO is called");
	List<StockCatalogDTO> itemList = new ArrayList<StockCatalogDTO>();

	ParameterizedRowMapper<StockCatalogDTO> mapper = new ParameterizedRowMapper<StockCatalogDTO>() {
	    public StockCatalogDTO mapRow(ResultSet rs, int rowNum)
		    throws SQLException {

		StockCatalogDTO tempDto = new StockCatalogDTO();
		tempDto.setScItemType(rs.getString("type"));
		tempDto.setScItemCode(rs.getString("item_code"));
		tempDto.setScItemDesc(rs.getString("model"));
		tempDto.setScAssignMode(rs.getString("assign_mode"));
		return tempDto;
	    }
	};

	try {
	    logger.info("getTempMiddleList() @ StockDAO: "
		    + getTempMiddleListSQL);

	    if ("ALL".equalsIgnoreCase(type)) {
		type = null;
	    }

	    itemList = simpleJdbcTemplate.query(getTempMiddleListSQL, mapper,
		    type, "%" + model + "%");
	} catch (EmptyResultDataAccessException erdae) {
	    logger.info("EmptyResultDataAccessException in getTempMiddleList()");

	    itemList = null;
	} catch (Exception e) {
	    logger.info("Exception caught in getTempMiddleList():", e);

	    throw new DAOException(e.getMessage(), e);
	}
	return itemList;
    }

    private static String getStockCatalogListSQL = "SELECT DISTINCT item_type type, "
	    + "  item_code item_code, "
	    + "  item_desc model "
	    + "FROM bomweb_stock_catalog " + "ORDER BY type";

    public List<StockCatalogDTO> getStockCatalogList() throws DAOException {

	logger.debug("getStockCatalogList @ StockDAO is called");
	List<StockCatalogDTO> itemList = new ArrayList<StockCatalogDTO>();

	ParameterizedRowMapper<StockCatalogDTO> mapper = new ParameterizedRowMapper<StockCatalogDTO>() {
	    public StockCatalogDTO mapRow(ResultSet rs, int rowNum)
		    throws SQLException {

		StockCatalogDTO tempDto = new StockCatalogDTO();
		tempDto.setScItemType(rs.getString("type"));
		tempDto.setScItemCode(rs.getString("item_code"));
		tempDto.setScItemDesc(rs.getString("model"));
		return tempDto;
	    }
	};

	try {
	    logger.info("getStockCatalogList() @ StockDAO: "
		    + getStockCatalogListSQL);

	    itemList = simpleJdbcTemplate.query(getStockCatalogListSQL, mapper);
	} catch (EmptyResultDataAccessException erdae) {
	    logger.info("EmptyResultDataAccessException in getStockCatalogList()");

	    itemList = null;
	} catch (Exception e) {
	    logger.info("Exception caught in getStockCatalogList():", e);

	    throw new DAOException(e.getMessage(), e);
	}
	return itemList;
    }

    private static String getStockMainStatusListSQL = "SELECT DISTINCT bcl.code_id status_id, "
    	    + "bcl.code_desc status "
    	    + "from bomweb_code_lkup bcl, bomweb_maint_parm_lkup bmpl " 
    	    + "where bcl.code_type = 'STOCK_STS' "
    	    + "and bmpl.parm_type = 'STOCK_STS' "
    	    + "and bcl.code_id not in ('24') "
    	    + "and bcl.code_desc = bmpl.parm_value "
    	    + "and bmpl.lob = 'MOB' "
    	    + "and bmpl.function_cd = 'STOCK QUANTITY ENQUIRY' "
    	    + "and bmpl.channel_cd = :channel_cd "
    	    + "order by bcl.code_id";
    
    public List<CodeLkupDTO> getStockMainStatusList(String channel_cd) throws DAOException {

	logger.info("getStockMainStatusList @ MobccsstockmodelDAO is called");
	List<CodeLkupDTO> itemList = new ArrayList<CodeLkupDTO>();

	ParameterizedRowMapper<CodeLkupDTO> mapper = new ParameterizedRowMapper<CodeLkupDTO>() {
	    public CodeLkupDTO mapRow(ResultSet rs, int rowNum)
		    throws SQLException {

		CodeLkupDTO tempDto = new CodeLkupDTO();
		tempDto.setCodeDesc(rs.getString("status"));
		tempDto.setCodeId(rs.getString("status_id"));
		return tempDto;
	    }
	};

	try {
	    logger.info("getStockMainStatusList() @ MobccsstockmodelDAO: "
		    + getStockMainStatusListSQL);

	    itemList = simpleJdbcTemplate.query(getStockMainStatusListSQL,
		    mapper, channel_cd);
	} catch (EmptyResultDataAccessException erdae) {
	    logger.info("EmptyResultDataAccessException in getStockMainStatusList()");

	    itemList = null;
	} catch (Exception e) {
	    logger.info("Exception caught in getStockMainStatusList():", e);

	    throw new DAOException(e.getMessage(), e);
	}
	return itemList;
    }

    private static String getStatusListSQL = "SELECT DISTINCT code_id, "
	    + "  code_desc " + "FROM bomweb_code_lkup "
	    + "WHERE code_type='STOCK_STS' " + "AND code_id   IN ('02','18') "
	    + "ORDER BY code_id";

    public List<CodeLkupDTO> getStatusList() throws DAOException {

	logger.info("getStatusList @ StockDAO is called");
	List<CodeLkupDTO> itemList = new ArrayList<CodeLkupDTO>();

	ParameterizedRowMapper<CodeLkupDTO> mapper = new ParameterizedRowMapper<CodeLkupDTO>() {
	    public CodeLkupDTO mapRow(ResultSet rs, int rowNum)
		    throws SQLException {
		CodeLkupDTO temp = new CodeLkupDTO();
		temp.setCodeId(rs.getString("code_id"));
		temp.setCodeDesc(rs.getString("code_desc"));
		return temp;
	    }
	};

	try {
	    logger.info("getStatusList() @ MobccsstockmodelDAO: "
		    + getStatusListSQL);

	    itemList = simpleJdbcTemplate.query(getStatusListSQL, mapper);
	} catch (EmptyResultDataAccessException erdae) {
	    logger.info("EmptyResultDataAccessException in getStatusList()");

	    itemList = null;
	} catch (Exception e) {
	    logger.info("Exception caught in getStatusList():", e);

	    throw new DAOException(e.getMessage(), e);
	}
	return itemList;
    }

    private static String getLocationListSQL = "Select distinct code_id, code_desc "
	    + "From bomweb_code_lkup " + "Where code_type='STOCK_LOC' ";

    public List<String> getLocationList() throws DAOException {

	logger.debug("getLocationList @ MobccsstockmodelDAO is called");
	List<String> itemList = new ArrayList<String>();

	ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
	    public String mapRow(ResultSet rs, int rowNum) throws SQLException {
		return rs.getString("code_desc");
	    }
	};

	try {
	    logger.info("getLocationList() @ MobccsstockmodelDAO: "
		    + getLocationListSQL);

	    itemList = simpleJdbcTemplate.query(getLocationListSQL, mapper);
	} catch (EmptyResultDataAccessException erdae) {
	    logger.info("EmptyResultDataAccessException in getLocationList()");

	    itemList = null;
	} catch (Exception e) {
	    logger.info("Exception caught in getLocationList():", e);

	    throw new DAOException(e.getMessage(), e);
	}
	return itemList;
    }

    private static String getModelListSQL = "select distinct item_type type, "
	    + "item_code item_code, " + "item_desc model "
	    + "from bomweb_stock_catalog";

    public List<StockCatalogDTO> getModelList() throws DAOException {

	logger.debug("getModelList @ MobccsstockmodelDAO is called");
	List<StockCatalogDTO> itemList = new ArrayList<StockCatalogDTO>();

	ParameterizedRowMapper<StockCatalogDTO> mapper = new ParameterizedRowMapper<StockCatalogDTO>() {
	    public StockCatalogDTO mapRow(ResultSet rs, int rowNum)
		    throws SQLException {

		StockCatalogDTO tempDto = new StockCatalogDTO();
		tempDto.setScItemType(rs.getString("type"));
		tempDto.setScItemCode(rs.getString("item_code"));
		tempDto.setScItemDesc(rs.getString("model"));
		return tempDto;
	    }
	};

	try {
	    logger.info("getModelList() @ MobccsstockmodelDAO: "
		    + getModelListSQL);

	    itemList = simpleJdbcTemplate.query(getModelListSQL, mapper);
	} catch (EmptyResultDataAccessException erdae) {
	    logger.info("EmptyResultDataAccessException in getModelList()");

	    itemList = null;
	} catch (Exception e) {
	    logger.info("Exception caught in getModelList():", e);

	    throw new DAOException(e.getMessage(), e);
	}
	return itemList;
    }

    private static String getStockModelResultDTOSQL = "Select item_code, item_desc, assign_mode "
	    + "from bomweb_stock_catalog "
	    + "where item_type = ? "
	    + "and item_desc like ? " + "order by item_desc";

    public List<StockModelResultDTO> getStockModelResultDTO(String type,
	    String model) throws DAOException {
	logger.debug(" getStockModelResultDTO is called");
	List<StockModelResultDTO> itemList = new ArrayList<StockModelResultDTO>();

	ParameterizedRowMapper<StockModelResultDTO> mapper = new ParameterizedRowMapper<StockModelResultDTO>() {
	    public StockModelResultDTO mapRow(ResultSet rs, int rowNum)
		    throws SQLException {

		StockModelResultDTO dto = new StockModelResultDTO();
		dto.setItemCode(rs.getString("item_code"));
		dto.setModel(rs.getString("item_desc"));
		dto.setMode(rs.getString("assign_mode"));

		return dto;
	    }
	};

	try {
	    logger.info("getStockModelResultDTO() @ StockDAO: "
		    + getStockModelResultDTOSQL);

	    itemList = simpleJdbcTemplate.query(getStockModelResultDTOSQL,
		    mapper, type, "%" + model + "%");
	} catch (EmptyResultDataAccessException erdae) {
	    logger.info("EmptyResultDataAccessException in getStockModelResultDTO()");

	    itemList = null;
	} catch (BadSqlGrammarException bsge) {
	    logger.info("BadSqlGrammarException in getStockModelResultDTO()",
		    bsge);

	    itemList = null;
	} catch (Exception e) {
	    logger.info("Exception caught in getStockModelResultDTO():", e);

	    throw new DAOException(e.getMessage(), e);
	}
	return itemList;
    }

    private static String getStockCatalogListByItemCodeSQL = "select item_type, item_code, item_desc, assign_mode, sim_type, hs_extra_function, assign_mode_ret "
	    + "from bomweb_stock_catalog where item_code = ?";

    public List<StockModelDetailsDTO> getStockCatalogListByItemCode(
	    String itemCode) throws DAOException {

	logger.info("getStockCatalogListByItemCode @ MobccsstockDAO is called");
	List<StockModelDetailsDTO> itemList = new ArrayList<StockModelDetailsDTO>();

	ParameterizedRowMapper<StockModelDetailsDTO> mapper = new ParameterizedRowMapper<StockModelDetailsDTO>() {
	    public StockModelDetailsDTO mapRow(ResultSet rs, int rowNum)
		    throws SQLException {

		StockModelDetailsDTO dto = new StockModelDetailsDTO();
		dto.setType(rs.getString("item_type"));
		dto.setItemCode(rs.getString("item_code"));
		dto.setModel(rs.getString("item_desc"));
		dto.setAssignMode(rs.getString("assign_mode"));
		dto.setSimType(rs.getString("sim_type"));
		dto.setHsExtraFunction(rs.getString("hs_extra_function"));
		dto.setAssignModeRet(rs.getString("assign_mode_ret"));
		return dto;
	    }
	};

	try {
	    logger.info("getStockCatalogListByItemCode() @ MobccsstockDAO: "
		    + getStockCatalogListByItemCodeSQL);

	    itemList = simpleJdbcTemplate.query(
		    getStockCatalogListByItemCodeSQL, mapper, itemCode);
	} catch (BadSqlGrammarException bsge) {
	    logger.info("BadSqlGrammarException in getStockCatalogListByItemCode()");

	    itemList = null;
	} catch (EmptyResultDataAccessException erdae) {
	    logger.info("EmptyResultDataAccessException in getStockCatalogListByItemCode()");

	    itemList = null;
	} catch (Exception e) {
	    logger.info(
		    "Exception caught in getStockCatalogListByItemCode(): ", e);

	    throw new DAOException(e.getMessage(), e);
	}
	return itemList;
    }

    public int insertModelDetails(StockModelDetailsDTO dto) throws DAOException {
		logger.debug("insertModelDetails() is called");
	
		// define SQL string
		String SQL = "Insert into bomweb_stock_catalog \n"
			+ "(item_type, item_code, item_desc , assign_mode, sim_type, location, \n"
			+ "create_by, create_date, last_upd_by, last_upd_date" +
			"  , hs_extra_function, assign_mode_ret " +
			" )\n"
			+ "values (?, ?, ?, ?, ?, '01', \n" + "?, Sysdate, ?, Sysdate" +
					", ?, ? " +
			")";
	
		// insert to table
		try {
			// only handset has hs_extra_function
			if (!"01".equals(dto.getType())) {
				dto.setHsExtraFunction(null);
			}
		    if (!("01".equals(dto.getType()) || "04".equals(dto.getType()))) {
			dto.setSimType(null);
		    }
		    if (("01".equals(dto.getType()) || "04".equals(dto.getType()))) {//  Athena 20131218
		    	if ("NONE".equals(dto.getSimType())) {
		    		dto.setSimType("02");
		    	}
		    }
	
		    simpleJdbcTemplate.update(SQL, dto.getType(), dto.getItemCode(),
			    dto.getModel(), dto.getAssignMode(), dto.getSimType(),
			    dto.getUsername(), dto.getUsername()
			    , dto.getHsExtraFunction(), dto.getAssignModeRet());
	
		    return 1;
	
		} catch (Exception e) {
		    logger.error("Exception caught in insertModelDetails()", e);
		    throw new DAOException(e.getMessage(), e);
		}

    }

    public int updateModelDetails(StockModelDetailsDTO dto) throws DAOException {
		logger.debug("updateModelDetails()@STOCKDAO is called");
	
		// define SQL string
		String SQL = "Update bomweb_stock_catalog " + "Set item_desc = ?, "
			+ " assign_mode = ?, " + " sim_type = ?, "
			+ "last_upd_by = ?, " + "last_upd_date = Sysdate " 
			+ " , hs_extra_function = ? "
			+ " , assign_mode_ret = ? "
			+ "where item_code = ?";
	
		// update to table
		try {
			// only handset has hs_extra_function
			if (!"01".equals(dto.getType())) {
				dto.setHsExtraFunction(null);
			}
		    if (!("01".equals(dto.getType()) || "04".equals(dto.getType()))) {
		    	dto.setSimType(null);
		    }
		    if (("01".equals(dto.getType()) || "04".equals(dto.getType()))) {//  Athena 20131218
		    	if ("NONE".equals(dto.getSimType())) {
		    		dto.setSimType("02");
		    	}
		    }
	
		    return simpleJdbcTemplate.update(SQL, dto.getModel(), dto.getAssignMode(),
			    dto.getSimType(), dto.getUsername(), dto.getHsExtraFunction(),
			    dto.getAssignModeRet(), dto.getItemCode());
		} catch (Exception e) {
		    logger.error("Exception caught in updateModelDetails()", e);
		    throw new DAOException(e.getMessage(), e);
		}
    }

    private static String getStockResultDTOSQL = "SELECT DISTINCT st.stock_type,bsi.item_code, bsc.item_desc, bsi.item_serial, ss.status, bsa.order_id, sl.LOCATION, bsi.create_date stock_in_date, bsi.remarks, bsi.batch_ref" +
    		" ,bsi.stock_pool "
	    + "FROM bomweb_stock_inventory bsi, bomweb_stock_catalog bsc, "
	    + "(SELECT DISTINCT code_id item_type,code_desc stock_type FROM bomweb_code_lkup WHERE code_type='STOCK_TYPE') st, "
	    + "     (SELECT DISTINCT code_id LOCATION_id,code_desc LOCATION FROM bomweb_code_lkup WHERE code_type='STOCK_LOC') sl, "
	    + "    (SELECT DISTINCT code_id status_id,code_desc status FROM bomweb_code_lkup WHERE code_type='STOCK_STS') ss, "
	    + "	 (SELECT DISTINCT order_id, item_code, status_id ,item_serial FROM bomweb_stock_assgn WHERE item_code= :itemCode AND status_id <>'24') bsa "
	    + "WHERE bsi.item_code = bsc.item_code "
	    + "AND bsc.item_type = st.item_type "
	    + "AND bsi.LOCATION = sl.LOCATION_id (+)"
	    + "AND bsi.status_id = ss.status_id "
	    + "AND bsi.item_serial = bsa.item_serial (+) "
	    + "AND bsi.item_code= :itemCode" +
	    " AND bsi.stock_pool = :stockPool ";

    // private static String getStockResultDTOSQL2
    // = "select si.item_code item_code, " +
    // "si.item_serial imei, " +
    // "si.location location, " +
    // "cl.code_desc status, " +
    // "si.create_date stock_in_date " +
    // "from bomweb_stock_inventory si, bomweb_code_lkup cl " +
    // "where si.item_serial = ? " +
    // "and cl.code_type = 'STOCK_STS' " +
    // "and si.status_id = cl.code_id";

    public List<StockResultDTO> getStockResultDTO(StockDTO dto)
	    throws DAOException {

		logger.debug(" getStockResultDTO is called");
		List<StockResultDTO> itemList = new ArrayList<StockResultDTO>();
	
		ParameterizedRowMapper<StockResultDTO> mapper = new ParameterizedRowMapper<StockResultDTO>() {
		    public StockResultDTO mapRow(ResultSet rs, int rowNum)
			    throws SQLException {
	
			StockResultDTO dto = new StockResultDTO();
	
			dto.setType(rs.getString("STOCK_TYPE"));
			dto.setItemCode(rs.getString("item_code"));
			dto.setModel(rs.getString("ITEM_DESC"));
			dto.setImei(rs.getString("ITEM_SERIAL"));
			dto.setStatus(rs.getString("status"));
			dto.setOrderId(rs.getString("ORDER_ID"));
			dto.setLocation(rs.getString("location"));
			dto.setStockInDate(rs.getDate("stock_in_date"));
			dto.setRemarks(rs.getString("remarks"));
			dto.setBatchRef(rs.getString("batch_ref"));
			dto.setStockPool(rs.getString("stock_pool"));
			return dto;
		    }
		};
	
		try {
	
		    logger.info("getStockResultDTO() @ StockDAO: "
			    + getStockResultDTOSQL);
	
		    // by model/ status
		    StringBuilder sql = new StringBuilder(getStockResultDTOSQL);
		    MapSqlParameterSource params = new MapSqlParameterSource();
		    params.addValue("itemCode", dto.getItemCode());
		    params.addValue("stockPool", dto.getStockPool());
		    if ("ALL".equalsIgnoreCase(dto.getStatus())) {
		    	sql.append("AND bsi.status_id in (SELECT code_id FROM bomweb_code_lkup WHERE code_type = 'STOCK_STS' and code_id not in ('24'))");
			} else {
				sql.append("AND bsi.status_id in (:status)");
				params.addValue("status", dto.getStatus());
		    }
	
		    itemList = simpleJdbcTemplate.query(sql.toString(), mapper, params);
	
		} catch (EmptyResultDataAccessException erdae) {
		    logger.info("EmptyResultDataAccessException in getStockResultDTO()");
	
		    itemList = null;
		} catch (Exception e) {
		    logger.info("Exception caught in getStockResultDTO():", e);
	
		    throw new DAOException(e.getMessage(), e);
		}
		return itemList;
    }

    // modified by Joyce 20120327
    private static String getStockUpdateDTObyImeiSQL 
    = "SELECT DISTINCT st.item_type, " 
    		+"  bsi.item_code, " 
    		+"  bsc.item_desc, " 
    		+"  bsi.item_serial, " 
    		+"  ss.status_id, " 
    		+"  bsa.order_id, " 
    		+"  sl.LOCATION_id, " 
    		+"  bsi.create_date stock_in_date, " 
    		+"  bsi.remarks, " 
    		+"  bsi.batch_ref" +
    		"   , bsi.STOCK_POOL " 
    		+ ", bsi.item_serial_ccs_real  " 
    		+"FROM bomweb_stock_inventory bsi, " 
    		+"  bomweb_stock_catalog bsc, " 
    		+"  (SELECT DISTINCT code_id item_type, " 
    		+"    code_desc stock_type " 
    		+"  FROM bomweb_code_lkup " 
    		+"  WHERE code_type='STOCK_TYPE' " 
    		+"  ) st, " 
    		+"  (SELECT DISTINCT code_id LOCATION_id, " 
    		+"    code_desc LOCATION " 
    		+"  FROM bomweb_code_lkup " 
    		+"  WHERE code_type='STOCK_LOC' " 
    		+"  ) sl, " 
    		+"  (SELECT DISTINCT code_id status_id, " 
    		+"    code_desc status " 
    		+"  FROM bomweb_code_lkup " 
    		+"  WHERE code_type='STOCK_STS' " 
    		+"  ) ss, " 
    		+"  (SELECT DISTINCT order_id, " 
    		+"    item_code, " 
    		+"    status_id , " 
    		+"    item_serial, "
    		+ "	  item_serial_ccs_real " 
    		+"  FROM bomweb_stock_assgn " 
    		+"  WHERE item_serial = ? " 
    		+"  AND status_id    <>'24' " 
    		+"  ) bsa " 
    		+"WHERE bsi.item_code = bsc.item_code " 
    		+"AND bsc.item_type   = st.item_type " 
    		+"AND bsi.LOCATION    = sl.LOCATION_id " 
    		+"AND bsi.status_id   = ss.status_id " 
    		+"AND bsi.item_serial = bsa.item_serial (+) " 
    		+"AND bsi.item_serial = ?";

    public List<StockUpdateDTO> getStockUpdateDTObyImei(String serialNumber)
	    throws DAOException {

		logger.debug("getStockUpdateDTObyImei @ StockDAO is called");
		List<StockUpdateDTO> itemList = new ArrayList<StockUpdateDTO>();
	
		ParameterizedRowMapper<StockUpdateDTO> mapper = new ParameterizedRowMapper<StockUpdateDTO>() {
		    public StockUpdateDTO mapRow(ResultSet rs, int rowNum)
			    throws SQLException {
	
			StockUpdateDTO tempDto = new StockUpdateDTO();
			tempDto.setType(rs.getString("ITEM_TYPE"));
			tempDto.setItemCode(rs.getString("item_code"));
			tempDto.setModel(rs.getString("ITEM_DESC"));
			tempDto.setImei(rs.getString("ITEM_SERIAL"));
			tempDto.setStatus(rs.getString("status_id"));
			tempDto.setOrderId(rs.getString("ORDER_ID"));
			tempDto.setLocation(rs.getString("location_id"));
			tempDto.setRemarks(rs.getString("remarks"));
			tempDto.setBatchRef(rs.getString("batch_ref"));
			tempDto.setStockPool(rs.getString("STOCK_POOL"));
			tempDto.setItemSerialReal(rs.getString("item_serial_ccs_real"));
			return tempDto;
		    }
		};
	
		try {
		    logger.info("getStockCatalogList() @ StockDAO: "
			    + getStockUpdateDTObyImeiSQL);
	
		    itemList = simpleJdbcTemplate.query(getStockUpdateDTObyImeiSQL,
			    mapper, serialNumber, serialNumber);
		} catch (EmptyResultDataAccessException erdae) {
		    logger.info("EmptyResultDataAccessException in getStockUpdateDTObyImei()");
	
		    itemList = null;
		} catch (Exception e) {
		    logger.info("Exception caught in getStockUpdateDTObyImei():", e);
	
		    throw new DAOException(e.getMessage(), e);
		}
		return itemList;
    }
    
    private static String getStockUpdateDTObyImeiRealSQL 
    = "SELECT DISTINCT st.item_type,   "
				+"    		    bsi.item_code,   "
				+"    		    bsc.item_desc,   "
				+"    		    ss.status_id,   "
				+"    		    sl.LOCATION_id,   "
				+"    		    bsi.create_date stock_in_date,   "
				+"    		    bsi.remarks,   "
				+"    		    bsi.batch_ref,   "
				+"				bsi.item_serial_ccs_real    "
				+"    		  FROM bomweb_stock_inv_ccs bsi,   "
				+"    		    bomweb_stock_catalog bsc,   "
				+"    		    (SELECT DISTINCT code_id item_type,   "
				+"    		      code_desc stock_type   "
				+"    		    FROM bomweb_code_lkup   "
				+"    		    WHERE code_type='STOCK_TYPE'   "
				+"    		    ) st,   "
				+"    		    (SELECT DISTINCT code_id LOCATION_id,   "
				+"    		      code_desc LOCATION   "
				+"    		    FROM bomweb_code_lkup   "
				+"    		    WHERE code_type='STOCK_LOC'   "
				+"    		    ) sl,   "
				+"    		    (SELECT DISTINCT code_id status_id,   "
				+"    		      code_desc status   "
				+"    		    FROM bomweb_code_lkup   "
				+"    		    WHERE code_type='STOCK_STS'   "
				+"    		    ) ss"
				+"    		  WHERE bsi.item_code = bsc.item_code   "
				+"    		  AND bsc.item_type   = st.item_type   "
				+"    		  AND bsi.LOCATION    = sl.LOCATION_id   "
				+"    		  AND bsi.status_id   = ss.status_id   "
				+"    		  AND bsi.ITEM_SERIAL_CCS_REAL = ?";

    public List<StockUpdateDTO> getStockUpdateDTObyImeiReal(String serialNumber)
	    throws DAOException {

		logger.debug("getStockUpdateDTObyImeiReal @ StockDAO is called");
		List<StockUpdateDTO> itemList = new ArrayList<StockUpdateDTO>();
	
		ParameterizedRowMapper<StockUpdateDTO> mapper = new ParameterizedRowMapper<StockUpdateDTO>() {
		    public StockUpdateDTO mapRow(ResultSet rs, int rowNum)
			    throws SQLException {
	
			StockUpdateDTO tempDto = new StockUpdateDTO();
			tempDto.setType(rs.getString("ITEM_TYPE"));
			tempDto.setItemCode(rs.getString("item_code"));
			tempDto.setModel(rs.getString("ITEM_DESC"));
			tempDto.setStatus(rs.getString("status_id"));
			tempDto.setLocation(rs.getString("location_id"));
			tempDto.setRemarks(rs.getString("remarks"));
			tempDto.setBatchRef(rs.getString("batch_ref"));
			tempDto.setItemSerialReal(rs.getString("item_serial_ccs_real"));
			return tempDto;
		    }
		};
	
		try {
		    logger.info("getStockCatalogList() @ StockDAO: "
			    + getStockUpdateDTObyImeiRealSQL);
	
		    itemList = simpleJdbcTemplate.query(getStockUpdateDTObyImeiRealSQL,
			    mapper, serialNumber);
		} catch (EmptyResultDataAccessException erdae) {
		    logger.info("EmptyResultDataAccessException in getStockUpdateDTObyImeiReal()");
	
		    itemList = null;
		} catch (Exception e) {
		    logger.info("Exception caught in getStockUpdateDTObyImeiReal():", e);
	
		    throw new DAOException(e.getMessage(), e);
		}
		return itemList;
    }

    // add by Joyce 20120214, modified 20120307
    private static String getStockManualSQL = "SELECT DISTINCT a.order_id, "
	    + "  TO_CHAR(b.delivery_date, 'DD/MM/YYYY') delivery_date, "
	    + "  TO_CHAR(a.app_date, 'DD/MM/YYYY hh24:mi') apply_date, "
	    + "  a.sales_cd, " + "  a.reason_cd, " + "  fc.code_desc status, "
	    + "  b.delivery_date del_date, a.app_date "
	    + "FROM bomweb_order a, " + "  bomweb_delivery b, "
	    + "  (SELECT DISTINCT code_id, " + "    code_desc "
	    + "  FROM bomweb_code_lkup "
	    + "  WHERE code_type='ORD_FALLOUT_CODE' " + "  ) fc "
	    + "WHERE a.order_status='99' " + "AND a.check_point   ='999' "
	    + "AND a.order_id      = b.order_id "
	    + "AND a.reason_cd     = fc.code_id ";

    public List<StockManualDTO> getStockManual(String startDate,
	    String endDate, String orderStatus, String orderId,
	    String searchCriteria) throws DAOException {

		logger.info("getStockManual @ StockDAO is called");
		List<StockManualDTO> itemList = new ArrayList<StockManualDTO>();
	
		ParameterizedRowMapper<StockManualDTO> mapper = new ParameterizedRowMapper<StockManualDTO>() {
		    public StockManualDTO mapRow(ResultSet rs, int rowNum)
			    throws SQLException {
	
				StockManualDTO tempDto = new StockManualDTO();
				tempDto.setOrderId(rs.getString("order_id"));
				tempDto.setDeliveryDate(rs.getString("delivery_date"));
				tempDto.setRequestDate(rs.getString("apply_date"));
				tempDto.setSalesId(rs.getString("sales_cd"));
				tempDto.setReasonCd(rs.getString("reason_cd"));
				tempDto.setOrderStatus(rs.getString("status"));
				return tempDto;
		    }
		};
	
		try {
		    StringBuilder sql = new StringBuilder(getStockManualSQL);
		    MapSqlParameterSource params = new MapSqlParameterSource();
	
		    if ("1".equalsIgnoreCase(searchCriteria)) {
				sql.append("and trunc(delivery_date) \n"
					+ "between trunc(TO_DATE(:startDate, 'DD/MM/YYYY')) \n"
					+ "    and trunc(nvl(TO_DATE(:endDate, 'DD/MM/YYYY'), sysdate)) ");
				params.addValue("startDate", startDate);
				params.addValue("endDate", endDate);
				if ("ALL".equalsIgnoreCase(orderStatus)) {
				    sql.append("and a.reason_cd in ('A001', 'A002', 'A003', 'N002', 'N004', 'G002', 'G004') \n"
					    + "order by a.reason_cd desc , del_date asc, a.app_date asc, a.order_id asc");
				} else {
				    sql.append("and a.reason_cd in (:orderStatus) \n"
					    + "order by a.reason_cd desc , del_date asc, a.app_date asc, a.order_id asc");
				    params.addValue("orderStatus", orderStatus);
				}
				
				logger.info("getStockManual() @ StockDAO: " + sql.toString());
			    itemList = simpleJdbcTemplate.query(sql.toString(), mapper, params);
			    
		    } else if ("2".equalsIgnoreCase(searchCriteria)) {
				if ("ALL".equalsIgnoreCase(orderStatus)) {
				    sql.append("and a.reason_cd in ('A001', 'A002', 'A003', 'N002', 'N004', 'G002', 'G004') \n"
					    + "order by a.reason_cd desc , del_date asc, a.app_date asc, a.order_id asc");
				} else {
				    sql.append("and a.reason_cd in (:orderStatus) \n"
					    + "order by a.reason_cd desc , del_date asc, a.app_date asc, a.order_id asc");
				    params.addValue("orderStatus", orderStatus);
				}
				
				logger.info("getStockManual() @ StockDAO: " + sql.toString());
			    itemList = simpleJdbcTemplate.query(sql.toString(), mapper, params);
			    
		    } else if ("3".equalsIgnoreCase(searchCriteria)) {
		    	String sqlSearchByOrderId 
		    		= "SELECT DISTINCT a.order_id, " 
		    				+"  TO_CHAR(b.delivery_date, 'DD/MM/YYYY') delivery_date, " 
		    				+"  TO_CHAR(a.app_date, 'DD/MM/YYYY hh24:mi') apply_date, " 
		    				+"  A.sales_cd, " 
		    				+"  A.order_status, " 
		    				+"  A.reason_cd, " 
		    				+"  A.check_point, " 
		    				+"  decode(A.order_status, "
		    				+"         '01', (SELECT code_desc " 
		    				+"               FROM bomweb_code_lkup " 
		    				+"               WHERE code_type='ORD_CHECK_POINT'"
		    				+"               AND code_id = A.check_point), " 
		    				+"         '99', (SELECT code_desc " 
		    				+"                FROM bomweb_code_lkup " 
		    				+"                WHERE code_type='ORD_FALLOUT_CODE'"
		    				+"                AND code_id = A.reason_cd) "
		    				+"  ) status " 
		    				+"FROM bomweb_order a, " 
		    				+"  bomweb_delivery b "
		    				+"WHERE ((a.check_point >= '199' " 
		    				+"AND a.check_point  <= '399') "
		    				+ "    OR a.reason_cd in('M001','A001','A002')) "
		    				+"AND A.order_id      = b.order_id " 
		    				+"AND A.order_id      = :orderId";
				
				params.addValue("orderId", orderId);
				
				logger.info("getStockManual() @ StockDAO: " + sqlSearchByOrderId);
			    itemList = simpleJdbcTemplate.query(sqlSearchByOrderId, mapper, params);
		    }

		} catch (EmptyResultDataAccessException erdae) {
		    logger.info("EmptyResultDataAccessException in getStockManual()");
	
		    itemList = null;
		} catch (Exception e) {
		    logger.info("Exception caught in getStockManual():", e);
	
		    throw new DAOException(e.getMessage(), e);
		}
		return itemList;
    }
    
    private static String getDSStockManualSQL = 
    		"SELECT DISTINCT a.order_id, \n" + 
    		"    TO_CHAR(a.app_date, 'DD/MM/YYYY hh24:mi') apply_date, \n" + 
    		"    a.sales_cd, fc.code_desc status, a.app_date \n" + 
    		"FROM bomweb_order a \n" + 
    		"join bomweb_code_lkup fc on \n" + 
    		"    decode(a.order_status, \n" +
    		"        '01', decode(a.check_point, '610', 'REVIEWING', '620', 'REJECTED', 'INITIAL'), \n" +
    		"        '02', 'SUCCESS', \n" + 
    		"        '03', 'CANCELLING', \n" + 
    		"        '04', 'CANCELLED', \n" +
    		"        '99', 'FAILED', \n" + 
    		"        a.order_status) = fc.code_desc \n" + 
    		"    and code_type='DS_ORD_STATUS' \n" + 
    		"WHERE a.order_id like 'D%' \n";

        public List<StockManualDTO> getDSStockManual(String startDate,
    	    String endDate, String orderStatus, String orderId,
    	    String searchCriteria, String channelCd) throws DAOException {

    		logger.info("getDSStockManual @ StockDAO is called");
    		List<StockManualDTO> itemList = new ArrayList<StockManualDTO>();
    	
    		ParameterizedRowMapper<StockManualDTO> mapper = new ParameterizedRowMapper<StockManualDTO>() {
    		    public StockManualDTO mapRow(ResultSet rs, int rowNum)
    			    throws SQLException {
    	
    				StockManualDTO tempDto = new StockManualDTO();
    				tempDto.setOrderId(rs.getString("order_id"));
    				tempDto.setRequestDate(rs.getString("apply_date"));
    				tempDto.setSalesId(rs.getString("sales_cd"));
    				tempDto.setOrderStatus(rs.getString("status"));
    				return tempDto;
    		    }
    		};
    	
    		try {
    		    StringBuilder sql = new StringBuilder(getDSStockManualSQL);
    		    MapSqlParameterSource params = new MapSqlParameterSource();
    		    
    		    if ("SIS".equalsIgnoreCase(channelCd) || "MDV".equalsIgnoreCase(channelCd)) {
    		    	sql.append("AND decode(a.lob, 'MOB', substr(a.order_id, 2, INSTR(a.order_id, 'M', -1)-2), a.shop_cd) in ");
	    			sql.append("(SELECT shop_cd FROM bomweb_shop WHERE sales_channel = :channelCd) ");
    		    	params.addValue("channelCd", channelCd);
    		    } 
    		    
    		    //app date / app date + status
    		    if ("1".equalsIgnoreCase(searchCriteria)) {
    				sql.append("and trunc(app_date) \n");
					sql.append("between trunc(TO_DATE(:startDate, 'DD/MM/YYYY')) ");
					sql.append("    and trunc(nvl(TO_DATE(:endDate, 'DD/MM/YYYY'), sysdate)) ");
    				params.addValue("startDate", startDate);
    				params.addValue("endDate", endDate);
    				if ("ALL".equalsIgnoreCase(orderStatus)) {
    				    sql.append("order by fc.code_desc desc, a.app_date asc, a.order_id asc");
    				} else {
    				    sql.append("and fc.code_id = (:orderStatus) ");
			    		sql.append("order by fc.code_desc desc, a.app_date asc, a.order_id asc");
    				    params.addValue("orderStatus", orderStatus);
    				}
    				
    				logger.info("getStockManual() @ StockDAO: " + sql.toString());
    			    itemList = simpleJdbcTemplate.query(sql.toString(), mapper, params);
    			    
    			//no select / status
    		    } else if ("2".equalsIgnoreCase(searchCriteria)) {
    				if ("ALL".equalsIgnoreCase(orderStatus)) {
    				    sql.append("order by fc.code_desc desc, a.app_date asc, a.order_id asc");
    				} else {
    				    sql.append("and fc.code_id = (:orderStatus) ");
			    		sql.append("order by fc.code_desc desc, a.app_date asc, a.order_id asc");
    				    params.addValue("orderStatus", orderStatus);
    				}
    				
    				logger.info("getStockManual() @ StockDAO: " + sql.toString());
    			    itemList = simpleJdbcTemplate.query(sql.toString(), mapper, params);
    			    
    			 // order id
    		    } else if ("3".equalsIgnoreCase(searchCriteria)) {
    				
    		    	 sql.append("and a.order_id = :orderId ");
    		    	 if ("ALL".equalsIgnoreCase(orderStatus)) {
     				    sql.append("order by fc.code_desc desc, a.app_date asc, a.order_id asc");
     				} else {
     				    sql.append("and fc.code_id = (:orderStatus) ");
			    		sql.append("order by fc.code_desc desc, a.app_date asc, a.order_id asc");
     				    params.addValue("orderStatus", orderStatus);
     				}
    				params.addValue("orderId", orderId);

    				logger.info("getStockManual() @ StockDAO: " + sql.toString());
    				itemList = simpleJdbcTemplate.query(sql.toString(), mapper, params);
    		    }

    		} catch (EmptyResultDataAccessException erdae) {
    		    logger.info("EmptyResultDataAccessException in getStockManual()");
    	
    		    itemList = null;
    		} catch (Exception e) {
    		    logger.info("Exception caught in getStockManual():", e);
    	
    		    throw new DAOException(e.getMessage(), e);
    		}

    		return itemList;
        }
        
        private static String getItemColorListSQL = 
        		"select distinct bsc.item_code, " +
        		"bsc.item_desc " +
        		"from W_DISPLAY_LKUP DL, " +
        		"W_ITEM_DTL_HS IDH, " +
        		"W_ITEM_PRODUCT_POS_ASSGN IPPA, " +
        		"bomweb_stock_catalog bsc, " +
        		" (select * from W_HANDSET_NS_PRICE " +
        		"where TRUNC(sysdate) between EFF_START_DATE and " +
        		" TRUNC(NVL(EFF_END_DATE, sysdate))) HNP  " +
        		"where IDH.COLOR_ID = DL.ID " +
        		" and DL.TYPE = 'COLOR' " +
        		" and DL.LOCALE = 'en' " +
        		" and IDH.ID = IPPA.ITEM_ID " +
        		" and IDH.MODEL_ID in (select distinct model_id from W_ITEM_DTL_HS where id in " +
        		"(select item_id from  W_ITEM_PRODUCT_POS_ASSGN  where pos_item_cd = ?)) " +
        		"and IPPA.POS_ITEM_CD = HNP.POS_ITEM_CD(+) " +
        		" and IPPA.POS_ITEM_CD = bsc.item_code";
        
        public List<CodeLkupDTO> getItemColorlist(String itemCode) throws DAOException {
        	logger.debug("getItemColorlist @ StockDAO is called");
    		List<CodeLkupDTO> itemList = new ArrayList<CodeLkupDTO>();
    	
    		ParameterizedRowMapper<CodeLkupDTO> mapper = new ParameterizedRowMapper<CodeLkupDTO>() {
    		    public CodeLkupDTO mapRow(ResultSet rs, int rowNum)
    			    throws SQLException {
    	
    		    	CodeLkupDTO tempDto = new CodeLkupDTO();
    			tempDto.setCodeId(rs.getString("item_code"));
    			tempDto.setCodeDesc(rs.getString("item_desc") + " (" + rs.getString("item_code") + ")");
    			return tempDto;
    		    }
    		};
    	
    		try {
    		    logger.info("getItemColorlist() @ StockDAO: "
    			    + getItemColorListSQL);
    	
    		    itemList = simpleJdbcTemplate.query(getItemColorListSQL,
    			    mapper, itemCode);
    		} catch (EmptyResultDataAccessException erdae) {
    		    logger.info("EmptyResultDataAccessException in getItemColorlist()");
    	
    		    itemList = null;
    		} catch (Exception e) {
    		    logger.info("Exception caught in getItemColorlist():", e);
    	
    		    throw new DAOException(e.getMessage(), e);
    		}
        	return itemList;
        }

    private static String getStockManualDetailSQL = "SELECT stock_assgn.order_id,stock_assgn.item_type,stock_assgn.item_code,stock_assgn.item_desc,stock_assgn.item_serial,stock_info.LOCATION_DESC,stock_info.LOCATION_ID, doa_old_item_serial \n"
	    + " FROM  \n"
	    + " (SELECT a.order_id,b.item_type item_type_id,c.code_desc item_type,a.item_code,b.item_desc,a.item_serial,a.doa_old_item_serial  \n"
	    + " FROM bomweb_stock_assgn a, bomweb_stock_catalog b, bomweb_code_lkup c \n"
	    + " WHERE a.order_id=? \n"
	    + " AND a.item_code = b.item_code \n"
	    + " AND NVL(a.status_id,0) <>'24' \n"
	    + " AND c.code_type='STOCK_TYPE' \n"
	    + " AND c.code_id = b.item_type) stock_assgn, \n"
	    + " (SELECT c.item_code,c.item_serial,d.code_desc LOCATION_DESC, c.LOCATION LOCATION_ID FROM bomweb_stock_inventory c, bomweb_code_lkup d \n"
	    + " WHERE c.LOCATION=d.code_id \n"
	    + " AND d.code_type='STOCK_LOC') stock_info \n"
	    + " WHERE stock_assgn.item_code = stock_info.item_code (+) \n"
	    + " AND stock_assgn.item_serial = stock_info.item_serial (+) \n"
	    + " ORDER BY stock_assgn.item_type_id ASC";

    public List<StockManualDetailDTO> getStockManualDetail(String orderId)
	    throws DAOException {
		logger.debug("getStockManualDetail @ StockDAO is called");
		List<StockManualDetailDTO> itemList = new ArrayList<StockManualDetailDTO>();
	
		ParameterizedRowMapper<StockManualDetailDTO> mapper = new ParameterizedRowMapper<StockManualDetailDTO>() {
		    public StockManualDetailDTO mapRow(ResultSet rs, int rowNum)
			    throws SQLException {
	
			StockManualDetailDTO tempDto = new StockManualDetailDTO();
			tempDto.setOrderId(rs.getString("order_id"));
			tempDto.setItemType(rs.getString("item_type"));
			tempDto.setItemCode(rs.getString("item_code"));
			tempDto.setDescriptions(rs.getString("item_desc"));
			tempDto.setSerialNum(rs.getString("item_serial"));
			tempDto.setLocation(rs.getString("location_desc"));
			tempDto.setLocationId(rs.getString("location_id"));
			tempDto.setOldSerialNum(rs.getString("doa_old_item_serial"));
			return tempDto;
		    }
		};
	
		try {
		    logger.info("getStockManualDetail() @ StockDAO: "
			    + getStockManualDetailSQL);
	
		    itemList = simpleJdbcTemplate.query(getStockManualDetailSQL,
			    mapper, orderId);
		} catch (EmptyResultDataAccessException erdae) {
		    logger.info("EmptyResultDataAccessException in getStockManualDetail()");
	
		    itemList = null;
		} catch (Exception e) {
		    logger.info("Exception caught in getStockManualDetail():", e);
	
		    throw new DAOException(e.getMessage(), e);
		}
		return itemList;
    }

    private static String getStockManualAssgnSQL = 
    		"SELECT DISTINCT (ITEM_SERIAL) " 
    		+"FROM BOMWEB_STOCK_INVENTORY " 
    		+"WHERE ITEM_CODE = ? " 
    		+"AND location    = ? " 
    		+"AND STATUS_ID   = ? " 
    		+"AND STOCK_POOL  = ? ";

    public List<String> getStockManualAssgn(String itemCode, String locCd,
	    String statusCd, String stockPool) throws DAOException {
		logger.info("getStockManualAssgn @ StockDAO is called");
		List<String> itemList = new ArrayList<String>();
	
		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
		    public String mapRow(ResultSet rs, int rowNum) throws SQLException {
	
			return rs.getString("item_serial");
		    }
		};
	
		try {
		    logger.info("getStockManualAssgn() @ StockDAO: "
			    + getStockManualAssgnSQL);
	
		    itemList = simpleJdbcTemplate.query(getStockManualAssgnSQL, mapper,
			    itemCode, locCd, statusCd, stockPool);
		} catch (EmptyResultDataAccessException erdae) {
		    logger.info("EmptyResultDataAccessException in getStockManualAssgn()");
	
		    itemList = null;
		} catch (Exception e) {
		    logger.info("Exception caught in getStockManualAssgn():", e);
	
		    throw new DAOException(e.getMessage(), e);
		}
		return itemList;
    }
    
    private static String getDSStockManualAssgnSQL = 
    		"SELECT DISTINCT (ITEM_SERIAL) " +
    		"FROM BOMWEB_STOCK_INVENTORY " +
    		"WHERE ITEM_CODE = :itemCode " +
    		"AND STATUS_ID IN ( " +
    		"  SELECT code_id FROM bomweb_code_lkup " +
    		"  WHERE code_type = 'STOCK_STS' " +
    		"  AND code_desc IN ('SOS', 'RSS') " +
    		") " +
    		"AND STOCK_POOL = 'DS' " +
    		"AND (store_cd = (select centre_cd from bomweb_shop where shop_cd = substr(:orderId, 2, 3)) " +
    		"OR store_cd = (select centre_cd from bomweb_shop where shop_cd = substr(:orderId, 2, 4))) " +
    		"AND (staff_id = " +
    		"  (select distinct sales_cd from bomweb_order where order_id = :orderId) " +
    		"   OR staff_id is null) " +
    		"ORDER BY ITEM_SERIAL";

    public List<String> getDSStockManualAssgn(String itemCode, 
	    String orderId) throws DAOException {
		logger.debug("getStockManualAssgn @ StockDAO is called");
		List<String> itemList = new ArrayList<String>();
	
		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
		    public String mapRow(ResultSet rs, int rowNum) throws SQLException {
	
			return rs.getString("item_serial");
		    }
		};
	
		MapSqlParameterSource params = new MapSqlParameterSource();
	    params.addValue("itemCode", itemCode);
	    params.addValue("orderId", orderId);
		
		try {
		    logger.info("getStockManualAssgn() @ StockDAO: "
			    + getDSStockManualAssgnSQL);
	
		    itemList = simpleJdbcTemplate.query(getDSStockManualAssgnSQL, mapper,
			    params);
		} catch (EmptyResultDataAccessException erdae) {
		    logger.info("EmptyResultDataAccessException in getStockManualAssgn()");
	
		    itemList = null;
		} catch (Exception e) {
		    logger.info("Exception caught in getStockManualAssgn():", e);
	
		    throw new DAOException(e.getMessage(), e);
		}
		return itemList;
    }

    // Stock Main Page (mobccsstock.html)
    // - search by item serial
    // - Stock Item Search Result
    private static String getStockResultDTOByItemSerialSQL = "SELECT DISTINCT st.stock_type,bsi.item_code, bsc.item_desc, bsi.item_serial, ss.status, bsa.order_id, sl.LOCATION, bsi.create_date stock_in_date, bsi.remarks, bsi.batch_ref" +
    		", bsi.stock_pool, bsi.event_cd, bsi.store_cd, bsi.team_cd, bsi.staff_id "
	    + "FROM bomweb_stock_inventory bsi, bomweb_stock_catalog bsc, "
	    + "(SELECT DISTINCT code_id item_type,code_desc stock_type FROM bomweb_code_lkup WHERE code_type='STOCK_TYPE') st, "
	    + "     (SELECT DISTINCT code_id LOCATION_id,code_desc LOCATION FROM bomweb_code_lkup WHERE code_type='STOCK_LOC') sl, "
	    + "     (SELECT DISTINCT code_id status_id,code_desc status FROM bomweb_code_lkup WHERE code_type='STOCK_STS') ss, "
	    + "	 (SELECT DISTINCT order_id, item_code, status_id ,item_serial FROM bomweb_stock_assgn WHERE item_serial = ? AND status_id <>'24') bsa "
	    + "WHERE bsi.item_code = bsc.item_code "
	    + "AND bsc.item_type = st.item_type "
	    + "AND bsi.LOCATION = sl.LOCATION_id "
	    + "AND bsi.status_id = ss.status_id "
	    + "AND bsi.item_serial = bsa.item_serial (+) "
	    + "AND bsi.item_serial = ? ";

    public List<StockResultDTO> getStockResultDTOByItemSerial(String itemSerial)
	    throws DAOException {

		logger.debug(" getStockResultDTOByItemSerial is called");
		List<StockResultDTO> itemList = new ArrayList<StockResultDTO>();
	
		ParameterizedRowMapper<StockResultDTO> mapper = new ParameterizedRowMapper<StockResultDTO>() {
		    public StockResultDTO mapRow(ResultSet rs, int rowNum)
			    throws SQLException {
	
			StockResultDTO dto = new StockResultDTO();
	
			dto.setType(rs.getString("STOCK_TYPE"));
			dto.setItemCode(rs.getString("item_code"));
			dto.setModel(rs.getString("ITEM_DESC"));
			dto.setImei(rs.getString("ITEM_SERIAL"));
			dto.setStatus(rs.getString("status"));
			dto.setOrderId(rs.getString("ORDER_ID"));
			dto.setLocation(rs.getString("location"));
			dto.setStockInDate(rs.getDate("stock_in_date"));
			dto.setRemarks(rs.getString("remarks"));
			dto.setBatchRef(rs.getString("batch_ref"));
			dto.setStockPool(rs.getString("stock_pool"));
			dto.setEventCode(rs.getString("event_cd"));
			dto.setStoreCode(rs.getString("store_cd"));
			dto.setTeamCode(rs.getString("team_cd"));
			dto.setStaffID(rs.getString("staff_id"));
			return dto;
		    }
		};
	
		try {
	
		    logger.info("getStockResultDTOByItemSerial() @ StockDAO: "
			    + getStockResultDTOByItemSerialSQL);
	
		    itemList = simpleJdbcTemplate.query(
			    getStockResultDTOByItemSerialSQL, mapper, itemSerial,
			    itemSerial);
	
		} catch (EmptyResultDataAccessException erdae) {
		    logger.info("EmptyResultDataAccessException in getStockResultDTOByItemSerial()");
	
		    itemList = null;
		} catch (BadSqlGrammarException bsge) {
		    logger.info(
			    "BadSqlGrammarException in getStockResultDTOByItemSerial()",
			    bsge);
	
		    itemList = null;
		} catch (Exception e) {
		    logger.info("Exception caught in getStockResultDTOByItemSerial():",
			    e);
	
		    throw new DAOException(e.getMessage(), e);
		}
		return itemList;
    }
    
    

    // Stock Main Page (mobccsstock.html)
    // - search by item serial
    // - Stock Item Search Result
    private static String getStockResultDTOByOrderIdSQL = "SELECT DISTINCT st.stock_type, bsa.item_code, bsc.ITEM_DESC, bsa.item_serial, ss.status, bsa.order_id ,sl.LOCATION, bsi.CREATE_DATE stock_in_date, bsi.remarks, bsi.batch_ref" +
    		", bsi.stock_pool, bsi.event_cd, bsi.store_cd, bsi.team_cd, bsi.staff_id  "
	    + "FROM bomweb_stock_inventory bsi, bomweb_stock_catalog bsc, "
	    + "(SELECT DISTINCT code_id item_type,code_desc stock_type FROM bomweb_code_lkup WHERE code_type='STOCK_TYPE') st, "
	    + "(SELECT DISTINCT code_id LOCATION_id,code_desc LOCATION FROM bomweb_code_lkup WHERE code_type='STOCK_LOC') sl, "
	    + "(SELECT DISTINCT code_id status_id,code_desc status FROM bomweb_code_lkup WHERE code_type='STOCK_STS') ss, "
	    + " (SELECT DISTINCT order_id, item_code, status_id,item_serial FROM bomweb_stock_assgn WHERE order_id= ? AND status_id<>'24')  bsa "
	    + "WHERE bsi.item_code = bsc.item_code "
	    + "AND bsa.item_code = bsi.item_code "
	    + "AND bsc.item_type = st.item_type "
	    + "AND bsi.status_id=ss.status_id "
	    + "AND bsi.LOCATION = sl.LOCATION_id "
	    + "AND bsi.item_serial = bsa.ITEM_SERIAL ";

    public List<StockResultDTO> getStockResultDTOByOrderId(String orderId)
	    throws DAOException {

		logger.debug(" getStockResultDTOByOrderId is called");
		List<StockResultDTO> itemList = new ArrayList<StockResultDTO>();
	
		ParameterizedRowMapper<StockResultDTO> mapper = new ParameterizedRowMapper<StockResultDTO>() {
		    public StockResultDTO mapRow(ResultSet rs, int rowNum)
			    throws SQLException {
	
			StockResultDTO dto = new StockResultDTO();
	
			dto.setType(rs.getString("STOCK_TYPE"));
			dto.setItemCode(rs.getString("item_code"));
			dto.setModel(rs.getString("ITEM_DESC"));
			dto.setImei(rs.getString("ITEM_SERIAL"));
			dto.setStatus(rs.getString("status"));
			dto.setOrderId(rs.getString("ORDER_ID"));
			dto.setLocation(rs.getString("location"));
			dto.setStockInDate(rs.getDate("stock_in_date"));
			dto.setRemarks(rs.getString("remarks"));
			dto.setBatchRef(rs.getString("batch_ref"));
			dto.setStockPool(rs.getString("stock_pool"));
			dto.setEventCode(rs.getString("event_cd"));
			dto.setStoreCode(rs.getString("store_cd"));
			dto.setTeamCode(rs.getString("team_cd"));
			dto.setStaffID(rs.getString("staff_id"));
	
			return dto;
		    }
		};
	
		try {
	
		    logger.info("getStockResultDTOByOrderId() @ StockDAO: "
			    + getStockResultDTOByOrderIdSQL);
	
		    itemList = simpleJdbcTemplate.query(getStockResultDTOByOrderIdSQL,
			    mapper, orderId);
	
		} catch (EmptyResultDataAccessException erdae) {
		    logger.info("EmptyResultDataAccessException in getStockResultDTOByOrderId()");
	
		    itemList = null;
		} catch (BadSqlGrammarException bsge) {
		    logger.info(
			    "BadSqlGrammarException in getStockResultDTOByOrderId()",
			    bsge);
	
		    itemList = null;
		} catch (Exception e) {
		    logger.info("Exception caught in getStockResultDTOByOrderId():", e);
	
		    throw new DAOException(e.getMessage(), e);
		}
		return itemList;
    }
    
    // add by Joyce 20120229
    public boolean deassignAllItemByOrder(String orderId) throws DAOException {

		logger.debug("deassignAllItemByOrder is called");
	
		boolean result = false;
	
		try {
	
		    SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
			    .withSchemaName("OPS$CNM")
			    .withCatalogName("pkg_stock_assign")
			    .withProcedureName("deassign_item_by_order");
		    jdbcCall.setAccessCallParameterMetaData(false);
		    jdbcCall.declareParameters(new SqlParameter("i_order_id",
			    Types.VARCHAR), new SqlOutParameter("gnRetVal",
			    Types.INTEGER), new SqlOutParameter("gnErrCode",
			    Types.INTEGER), new SqlOutParameter("gnErrText",
			    Types.VARCHAR));
	
		    MapSqlParameterSource inMap = new MapSqlParameterSource();
		    inMap.addValue("i_order_id", orderId);
	
		    SqlParameterSource in = inMap;
		    Map<String, Object> out = jdbcCall.execute(in);
	
		    int error_code = -10;
		    int retVal = -10;
		    String error_text = null;
	
		    if (((Integer) out.get("gnErrCode")) != null) {
			error_code = ((Integer) out.get("gnErrCode")).intValue();
			logger.info("OPS$CNM.pkg_stock_assign.deassign_item_by_order() output gnErrCode = "
				+ error_code);
		    }
	
		    if (((Integer) out.get("gnRetVal")) != null) {
			retVal = ((Integer) out.get("gnRetVal")).intValue();
			logger.info("OPS$CNM.pkg_stock_assign.deassign_item_by_order() output gnRetVal = "
				+ retVal);
		    }
	
		    if ((out.get("gnErrText")) != null) {
			error_text = out.get("gnErrText").toString();
			logger.info("OPS$CNM.pkg_stock_assign.deassign_item_by_order() output gnErrText = "
				+ error_text);
		    } else {
			error_text = null;
			logger.info("OPS$CNM.pkg_stock_assign.deassign_item_by_order() output gnErrText = "
				+ error_text);
		    }
	
		    if (error_text == null) {
			result = true;
		    }
	
		    logger.info("deassignAllItemByOrder result = " + result);
	
		    return result;
	
		} catch (Exception e) {
		    logger.error("Exception caught in deassignAllItemByOrder()", e);
		    throw new DAOException(e.getMessage(), e);
		}
    }

    // add by Joyce 20120301
    public boolean manualStockAssgn(StockManualAssgnUI dto) throws DAOException {

		logger.debug("manualStockAssgn is called");
	
		boolean result = false;
	
		try {
	
		    SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
			    .withSchemaName("OPS$CNM")
			    .withCatalogName("pkg_stock_assign")
			    .withProcedureName("manual_stock_assgn");
		    jdbcCall.setAccessCallParameterMetaData(false);
		    jdbcCall.declareParameters(new SqlParameter("i_order_id",
			    Types.VARCHAR), new SqlParameter("i_item_code",
			    Types.VARCHAR), new SqlParameter("i_item_serial",
			    Types.VARCHAR), new SqlParameter("i_location",
			    Types.VARCHAR), new SqlParameter("i_sales_cd",
			    Types.VARCHAR), new SqlOutParameter("gnRetVal",
			    Types.INTEGER), new SqlOutParameter("gnErrCode",
			    Types.INTEGER), new SqlOutParameter("gnErrText",
			    Types.VARCHAR));
	
		    MapSqlParameterSource inMap = new MapSqlParameterSource();
		    inMap.addValue("i_order_id", dto.getOrderId());
		    inMap.addValue("i_item_code", dto.getItemCode());
		    inMap.addValue("i_item_serial", dto.getItemSerial());
		    inMap.addValue("i_location", dto.getLocation());
		    inMap.addValue("i_sales_cd", dto.getUsername());
	
		    SqlParameterSource in = inMap;
		    Map<String, Object> out = jdbcCall.execute(in);
	
		    int error_code = -10;
		    int retVal = -10;
		    String error_text;
	
		    if (((Integer) out.get("gnErrCode")) != null) {
				error_code = ((Integer) out.get("gnErrCode")).intValue();
				System.out.println(error_code);
				logger.info("OPS$CNM.pkg_stock_assign.manual_stock_assgn() output gnErrCode = "
					+ error_code);
		    }
	
		    if (((Integer) out.get("gnRetVal")) != null) {
				retVal = ((Integer) out.get("gnRetVal")).intValue();
				System.out.println(retVal);
				logger.info("OPS$CNM.pkg_stock_assign.manual_stock_assgn() output gnRetVal = "
					+ retVal);
		    }
	
		    if ((out.get("gnErrText")) != null) {
				error_text = out.get("gnErrText").toString();
				System.out.println(error_text);
				logger.info("OPS$CNM.pkg_stock_assign.manual_stock_assgn() output gnErrText = "
					+ error_text);
		    } else {
				error_text = null;
				logger.info("OPS$CNM.pkg_stock_assign.manual_stock_assgn() output gnErrText = "
					+ error_text);
		    }
	
		    if (error_code >= 0) {
		    	result = true;
		    }
	
		    logger.info("manualStockAssgn result = " + result);
	
		    return result;
	
		} catch (Exception e) {
		    logger.error("Exception caught in manualStockAssgn()", e);
		    throw new DAOException(e.getMessage(), e);
		}
    }

    // add by Joyce 20120306
    public boolean manualOrderStatusProcess(String orderId) throws DAOException {

		logger.debug("manualOrderStatusProcess is called");
	
		boolean result = false;
	
		try {
	
		    SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
			    .withSchemaName("OPS$CNM")
			    .withCatalogName("PKG_SB_MOB_ORDER")
			    .withProcedureName("manual_order_status_process");
		    jdbcCall.setAccessCallParameterMetaData(false);
		    jdbcCall.declareParameters(new SqlParameter("i_order_id",
			    Types.VARCHAR), new SqlOutParameter("o_errCode",
			    Types.INTEGER), new SqlOutParameter("o_errText",
			    Types.VARCHAR));
	
		    MapSqlParameterSource inMap = new MapSqlParameterSource();
		    inMap.addValue("i_order_id", orderId);
	
		    SqlParameterSource in = inMap;
		    Map<String, Object> out = jdbcCall.execute(in);
	
		    int error_code = -10;
		    String error_text = null;
	
		    if (((Integer) out.get("o_errCode")) != null) {
		    	error_code = ((Integer) out.get("o_errCode")).intValue();
		    }
	
		    if ((out.get("o_errText")) != null) {
		    	error_text = out.get("o_errText").toString();
		    }
	
		    logger.info("OPS$CNM.pkg_sb_mob_order.manual_order_status_process() output o_errCode = "
			    + error_code);
		    logger.info("OPS$CNM.pkg_sb_mob_order.manual_order_status_process() output o_errText = "
			    + error_text);
	
		    if (error_code >= 0) {
		    	result = true;
		    }
	
		    return result;
	
		} catch (Exception e) {
		    logger.error("Exception caught in manualOrderStatusProcess()", e);
		    throw new DAOException(e.getMessage(), e);
		}
    }
    
    public boolean manualCosOrderStatusProcess(String orderId) throws DAOException {

		logger.debug("manualOrderStatusProcess is called");
	
		boolean result = false;
	
		try {
	
		    SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
			    .withSchemaName("OPS$CNM")
			    .withCatalogName("PKG_SB_MOB_COS_ORDER")
			    .withProcedureName("manual_order_status_process");
		    jdbcCall.setAccessCallParameterMetaData(false);
		    jdbcCall.declareParameters(new SqlParameter("i_order_id",
			    Types.VARCHAR), new SqlOutParameter("o_errCode",
			    Types.INTEGER), new SqlOutParameter("o_errText",
			    Types.VARCHAR));
	
		    MapSqlParameterSource inMap = new MapSqlParameterSource();
		    inMap.addValue("i_order_id", orderId);
	
		    SqlParameterSource in = inMap;
		    Map<String, Object> out = jdbcCall.execute(in);
	
		    int error_code = -10;
		    String error_text = null;
	
		    if (((Integer) out.get("o_errCode")) != null) {
		    	error_code = ((Integer) out.get("o_errCode")).intValue();
		    }
	
		    if ((out.get("o_errText")) != null) {
		    	error_text = out.get("o_errText").toString();
		    }
	
		    logger.info("OPS$CNM.pkg_sb_mob_cos_order.manual_order_status_process() output o_errCode = "
			    + error_code);
		    logger.info("OPS$CNM.pkg_sb_mob_cos_order.manual_order_status_process() output o_errText = "
			    + error_text);
	
		    if (error_code >= 0) {
		    	result = true;
		    }
	
		    return result;
	
		} catch (Exception e) {
		    logger.error("Exception caught in manualOrderStatusProcess()", e);
		    throw new DAOException(e.getMessage(), e);
		}
    }

    // add by Joyce 20120313
    
    /* modified by Joyce 20120416
     * modified by Wilson 20121112, using outer join
     * 
     * Modification Details:
     * 
     * For CCS Sales & CPA (channel id = 2 & 68):
     * Display only "status = free" qty
	 * For manual assign item, display qty as "N/A"
     * 
     * For CCS Admin:
     * Display All
    */ 
    

    public List<StockQuantityEnquiryDTO> getStockQuantityEnquiry(String stockPool, String staffId, String type, String itemCode, String model)
	    throws DAOException {

        // Item Quantity Enquiry
        final String getStockQuantityEnquirySQL 
    	    = "SELECT C.CODE_DESC ITEM_TYPE , " 
    	    		+"  B.ITEM_DESC, " 
    	    		+"  B.ITEM_CODE, " // A -> B
    	    		+"  DECODE(d.channel_id,2,DECODE(b.assign_mode,'02','NA','01',NVL(A.FREE_COUNT,0)),NVL(A.FREE_COUNT,0)) FREE, " 
    	    		+"  DECODE(d.channel_id,2,DECODE(b.assign_mode,'02','NA','01',NVL(A.RESERVE_COUNT,0)),NVL(A.RESERVE_COUNT,0))RESERVE, " 
    	    		+"  DECODE(d.channel_id,2,DECODE(b.assign_mode,'02','NA','01',NVL(ASSIGN_COUNT,0)),NVL(ASSIGN_COUNT,0))ASSIGN, " 
    	    		+"  DECODE(d.channel_id,2,DECODE(b.assign_mode,'02','NA','01',NVL(ALLOCATED_COUNT,0)),NVL(ALLOCATED_COUNT,0))ALLOCATE, " 
    	    		+"  DECODE(d.channel_id,2,DECODE(b.assign_mode,'02','NA','01',NVL(TRANSFER_COUNT,0)),NVL(TRANSFER_COUNT,0))TRANSFER, " 
    	    		+"  DECODE(d.channel_id,2,DECODE(b.assign_mode,'02','NA','01',NVL(RETURN_COUNT,0)),NVL(RETURN_COUNT,0))RETURN, " 
    	    		+"  DECODE(d.channel_id,2,DECODE(b.assign_mode,'02','NA','01',NVL(DOA_COUNT,0)),NVL(DOA_COUNT,0)) DOA " 
    	    		+"FROM BOMWEB_STOCK_QUANTITY_VIEW A, " 
    	    		+"  BOMWEB_STOCK_CATALOG B, " 
    	    		+"  (SELECT DISTINCT channel_id " 
    	    		+"  FROM BOMWEB_SALES_ASSIGNMENT " 
    	    		+"  WHERE staff_id = :staffId " 
    	    		+"  AND sysdate BETWEEN start_date AND NVL(end_date,sysdate) " 
    	    		+"  ) d, " 
    	    		+"  (SELECT DISTINCT CODE_ID, " 
    	    		+"    CODE_DESC " 
    	    		+"  FROM BOMWEB_CODE_LKUP " 
    	    		+"  WHERE CODE_TYPE='STOCK_TYPE' " 
    	    		+"  ) C " 
    	    		+" WHERE A.ITEM_CODE(+) = B.ITEM_CODE " // add (+)
    	    		+" AND B.ITEM_TYPE  =C.CODE_ID " 
    	    		+" AND A.STOCK_POOL = :stockPool " 
    	    		+" AND C.CODE_ID    = NVL(:type, C.CODE_ID) " 
    	    		+" AND B.ITEM_CODE = NVL(:itemCode, B.ITEM_CODE) "
    	    		+" AND upper(b.item_desc) LIKE upper(:model) "
    	    		+" ORDER BY c.code_desc ASC, " 
    	    		+"  B.ITEM_CODE";
		logger.debug(" getStockQuantityEnquiry is called");
		List<StockQuantityEnquiryDTO> itemList = new ArrayList<StockQuantityEnquiryDTO>();
	
		ParameterizedRowMapper<StockQuantityEnquiryDTO> mapper = new ParameterizedRowMapper<StockQuantityEnquiryDTO>() {
		    public StockQuantityEnquiryDTO mapRow(ResultSet rs, int rowNum)
			    throws SQLException {
	
			StockQuantityEnquiryDTO dto = new StockQuantityEnquiryDTO();
	
			dto.setType(rs.getString("ITEM_TYPE"));
			dto.setItemDesc(rs.getString("ITEM_DESC"));
			dto.setItemCode(rs.getString("ITEM_CODE"));
			dto.setFree(rs.getString("FREE"));
			dto.setReserve(rs.getString("RESERVE"));
			dto.setAssign(rs.getString("ASSIGN"));
			dto.setAllocate(rs.getString("ALLOCATE"));
			dto.setTransfer(rs.getString("TRANSFER"));
			dto.setReturnField(rs.getString("RETURN"));
			dto.setDoa(rs.getString("DOA"));
	
			return dto;
		    }
		};
	
		try {
	
		    logger.info("getStockQuantityEnquiry() @ StockDAO: "
			    + getStockQuantityEnquirySQL);
		    MapSqlParameterSource params = new MapSqlParameterSource();
		    params.addValue("stockPool", stockPool);
		    params.addValue("staffId", staffId);
		    params.addValue("type", type);
		    params.addValue("itemCode", itemCode);
		    params.addValue("model", "%" + model + "%");
		    itemList = simpleJdbcTemplate.query(getStockQuantityEnquirySQL, mapper, params);
	
		} catch (EmptyResultDataAccessException erdae) {
		    logger.info("EmptyResultDataAccessException in getStockQuantityEnquiry()");
	
		    itemList = null;
		} catch (BadSqlGrammarException bsge) {
		    logger.info("BadSqlGrammarException in getStockQuantityEnquiry()",
			    bsge);
	
		    itemList = null;
		} catch (Exception e) {
		    logger.info("Exception caught in getStockQuantityEnquiry():", e);
	
		    throw new DAOException(e.getMessage(), e);
		}
		return itemList;
    }

    // add by eliot for hottest model management 20120314
    private static String getModelSearchSQL = "SELECT lk.code_desc, "
    	    + "  sc.item_code, " + "  sc.item_desc "
    	    + "FROM bomweb_stock_catalog sc, " + "  bomweb_code_lkup lk "
    	    + "WHERE lk.code_type = 'STOCK_TYPE' "
    	    + "AND lk.code_id     = sc.item_type " + "AND sc.item_code LIKE ? "
    	    + "AND sc.item_desc LIKE ? " + "AND lk.code_id LIKE ? "
    	    + "AND lk.code_id not in ('02','03','06') "
    	    + "ORDER BY sc.item_code";

    public List<String[]> getModelSearch(String itemCode, String itemDesc,
    	    String codeId) throws DAOException {
    	logger.debug("getModelSearch @ StockDAO is called");
    	List<String[]> itemList = new ArrayList<String[]>();

    	ParameterizedRowMapper<String[]> mapper = new ParameterizedRowMapper<String[]>() {
    	    public String[] mapRow(ResultSet rs, int rowNum)
    		    throws SQLException {
    		String temp[] = new String[3];
    		temp[0] = rs.getString("code_desc");
    		temp[1] = rs.getString("item_code");
    		temp[2] = rs.getString("item_desc");
    		return temp;
    	    }
    	};

    	try {
    	    logger.info("getModelSearch() @ StockDAO: " + getModelSearchSQL);

    	    itemList = simpleJdbcTemplate.query(getModelSearchSQL, mapper,
    		    itemCode, itemDesc, codeId);
    	} catch (EmptyResultDataAccessException erdae) {
    	    logger.info("EmptyResultDataAccessException in getModelSearch()");

    	    itemList = null;
    	} catch (Exception e) {
    	    logger.info("Exception caught in getModelSearch():", e);

    	    throw new DAOException(e.getMessage(), e);
    	}
    	return itemList;
    }

    // add by eliot for hottest model management 20120314
    private static String getHottestModelAndPeriodSQL = "SELECT lk.code_desc, "
	    + "  sc.item_code, " + "  sc.item_desc, " + "  ht.start_date, "
	    + "  ht.end_date " + "FROM bomweb_stock_catalog sc, "
	    + "  bomweb_code_lkup lk, " + "  bomweb_hottest_model ht "
	    + "WHERE lk.code_type = 'STOCK_TYPE' "
	    + "AND lk.code_id     = sc.item_type "
	    + "AND ht.item_code   = sc.item_code " + "AND ht.item_code   = ? "
	    + "ORDER BY sc.item_code";

    public List<HottestModelManagementUI> getHottestModelAndPeriod(String itemCode)
	    throws DAOException {
		logger.debug("getHottestModelAndPeriod @ StockDAO is called");
		List<HottestModelManagementUI> itemList = new ArrayList<HottestModelManagementUI>();
	
		ParameterizedRowMapper<HottestModelManagementUI> mapper = new ParameterizedRowMapper<HottestModelManagementUI>() {
		    public HottestModelManagementUI mapRow(ResultSet rs, int rowNum)
			    throws SQLException {
			HottestModelManagementUI ui = new HottestModelManagementUI();
			ui.setType(rs.getString("code_desc"));
			ui.setItemCode(rs.getString("item_code"));
			ui.setModel(rs.getString("item_desc"));
			ui.setStartDate(rs.getDate("start_date"));
			ui.setEndDate(rs.getDate("end_date"));
			return ui;
		    }
		};
	
		try {
		    logger.info("getHottestModelAndPeriod() @ StockDAO: " + getHottestModelAndPeriodSQL);
	
		    itemList = simpleJdbcTemplate.query(getHottestModelAndPeriodSQL, mapper, itemCode);
		} catch (EmptyResultDataAccessException erdae) {
		    logger.info("EmptyResultDataAccessException in getHottestModelAndPeriod()");
	
		    itemList = null;
		} catch (Exception e) {
		    logger.info("Exception caught in getHottestModelAndPeriod():", e);
	
		    throw new DAOException(e.getMessage(), e);
		}
		return itemList;
    }

    // add by eliot for hottest model management 20120314
    public int updateHottestModelStartDate(Date endDate, String user, String itemCode, Date startDate)
	    throws DAOException {
		logger.debug("updateHottestModelStartDate()@STOCKDAO is called");
	
		// define SQL string
		String SQL = "UPDATE bomweb_hottest_model " + "SET end_date   =?, "
			+ "  last_upd_by  =?, " + "  last_upd_date=sysdate "
			+ "WHERE item_code=? " + "AND start_date =?";

		// update to table
		try {
		    simpleJdbcTemplate.update(SQL, endDate, user, itemCode, startDate);
	
		    logger.debug("updateHottestModelStartDate successful! return 1");
		    return 1;
		} catch (Exception e) {
		    logger.error("Exception caught in updateHottestModelStartDate()", e);
		    throw new DAOException(e.getMessage(), e);
		}

    }

    // add by eliot for hottest model management 20120314
    private static String insertHottestModelSQL = "INSERT "
	    + "INTO bomweb_hottest_model " + "  ( " + "    item_code, "
	    + "    start_date, " + "    end_date, " + "    create_by, "
	    + "    create_date, " + "    last_upd_by, " + "    last_upd_date "
	    + "  ) " + "  VALUES " + "  ( " + "    ?, " + "    ?, " + "    ?, "
	    + "    ?, " + "    sysdate, " + "    ?, " + "    sysdate " + "  )";

    public int insertHottestModel(String itemCode, Date startDate, Date endDate, String user)
	    throws DAOException {
		logger.debug("insertHottestModel is called");
	
		try {
		    logger.info("insertHottestModelSQL() @ STOCKDAO: "
			    + insertHottestModelSQL);
		    return simpleJdbcTemplate.update(insertHottestModelSQL,
		    		// start sql mapping
		    		itemCode, startDate, endDate, user, user
		    		// end sql mapping
		    		);
		} catch (Exception e) {
		    logger.error("Exception caught in insertHottestModelSQL()", e);
		    throw new DAOException(e.getMessage(), e);
		}
    }
    
    private static String validHottestModelManagementOverlapSQL 
    	= "SELECT " 
	    +"  CASE " 
	    +"    WHEN (trunc(start_date),trunc(end_date)) "
	    +"    overlaps (trunc(?), trunc(?))" 
	    +"    THEN 'Y' " 
	    +"    ELSE 'N' " 
	    +"  END AS is_Overlap " 
	    +"FROM bomweb_hottest_model " 
	    +"WHERE item_code=? ";
    
    public boolean validHottestModelManagementOverlap(String actionType, String itemCode, 
    		Date startDate, Date endDate) throws DAOException {
	
		logger.debug("validHottestModelManagementOverlap is called");
		boolean result = false;
		
		List<String> tempResult = new ArrayList<String>();
	
		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
		    public String mapRow(ResultSet rs, int rowNum)
			    throws SQLException {
		    	return rs.getString("is_Overlap");
		    }
		};
	
		try {
			
			StringBuilder validSQL = new StringBuilder(validHottestModelManagementOverlapSQL);
			
			if ("update".equalsIgnoreCase(actionType)) {
				validSQL.append("and start_date != trunc(?)");
				logger.info("validHottestModelManagementOverlap() @ STOCKDAO: "
								+ validSQL);
				tempResult = simpleJdbcTemplate.query(validSQL.toString(), 
			    				mapper, 
			    				startDate,
			    				endDate,
			    				itemCode,
			    				startDate);
			} else {
				logger.info("validHottestModelManagementOverlap() @ STOCKDAO: "
					+ validSQL);
				tempResult = simpleJdbcTemplate.query(validSQL.toString(), 
    				mapper, 
    				startDate,
    				endDate,
    				itemCode);
			}
			
		    int count = 0;
		    	    
		    if(tempResult.size() > 0){
				Iterator<String> resultItr = tempResult.iterator();
				while(resultItr.hasNext()){
				    if("Y".equals(resultItr.next())){
				    	count+=1;
				    }
				}
		    }
		    
		    if(count == 0){
		    	result = true;
		    }
		    
		    return result;
			    	
		} catch (Exception e) {
		    logger.error("Exception caught in validHottestModelManagementOverlap()", e);
		    throw new DAOException(e.getMessage(), e);
		}
    }
    
    public String doaDeassignItem (String orderId, String itemCode, String itemSerial, String oldItemCode) throws DAOException {

    	logger.debug("doaDeassignItem is called");

    	try {

    	    SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
    		    .withSchemaName("OPS$CNM")
    		    .withCatalogName("pkg_stock_assign")
    		    .withProcedureName("doa_deassign_item");
    	    jdbcCall.setAccessCallParameterMetaData(false);
    	    jdbcCall.declareParameters(
    	    		new SqlParameter("i_order_id", Types.VARCHAR),
    	    		new SqlParameter("i_item_code", Types.VARCHAR),
    	    		new SqlParameter("i_item_serial", Types.VARCHAR),
    	    		new SqlParameter("i_old_item_code", Types.VARCHAR),
    	    		new SqlOutParameter("gnRetVal", Types.INTEGER),
    	    		new SqlOutParameter("gnErrCode", Types.INTEGER), 
    	    		new SqlOutParameter("gsErrText", Types.VARCHAR));

    	    MapSqlParameterSource inMap = new MapSqlParameterSource();
    	    inMap.addValue("i_order_id", orderId);
    	    inMap.addValue("i_item_code", itemCode);
    	    inMap.addValue("i_item_serial", itemSerial);
    	    inMap.addValue("i_old_item_code", oldItemCode);

    	    SqlParameterSource in = inMap;
    	    Map<String, Object> out = jdbcCall.execute(in);

    	    int error_code = -10;
    	    String error_text = null, ret_val = null;

    	    if (((Integer) out.get("gnErrCode")) != null) {
    	    	error_code = ((Integer) out.get("gnErrCode")).intValue();
    	    }

    	    if ((out.get("gsErrText")) != null) {
    	    	error_text = out.get("gsErrText").toString();
    	    }
    	    
    	    if ((out.get("gnRetVal")) != null) {
    	    	ret_val = out.get("gnRetVal").toString();
    	    }

    	    logger.info("OPS$CNM.pkg_stock_assign.doa_deassign_item() output gnErrCode = "
    		    + error_code);
    	    logger.info("OPS$CNM.pkg_stock_assign.doa_deassign_item() output gsErrText = "
    		    + error_text);
    	    logger.info("OPS$CNM.pkg_stock_assign.doa_deassign_item() output gnRetVal = "
        		    + ret_val);

    	    return ret_val;

    	} catch (Exception e) {
    	    logger.error("Exception caught in doaDeassignItem()", e);
    	    throw new DAOException(e.getMessage(), e);
    	}
    }
    
    // add by Joyce for Stock Manual Assign 20120430
    // extracting the order info
    // modified 20121003 loose the rule to 15:00
    private static String getOrderInfoSQL = 
    		"SELECT ord.order_id, " 
			+"  ord.order_type, " 
			+"  ord.order_status, " 
			+"  ord.check_point, " 
			+"  nvl(ord.reason_cd, 'NULL') reason_cd, " 
			+"  deli.delivery_date, " 
			+"  DECODE(SIGN((TRUNC(SYSDATE) + 15/24) - sysdate), 1, 'Y', 0, 'Y', 'N') b4_1500 " 
			+"FROM bomweb_order ord, " 
			+"  bomweb_delivery deli " 
			+"WHERE ord.order_id = ? " 
			+"AND ord.order_id   = deli.order_id";

    public List<Object[]> getOrderInfo(String orderId)
	    throws DAOException {
		logger.debug("getOrderInfo @ StockDAO is called");
		List<Object[]> itemList = new ArrayList<Object[]>();
	
		ParameterizedRowMapper<Object[]> mapper = new ParameterizedRowMapper<Object[]>() {
		    public Object[] mapRow(ResultSet rs, int rowNum)
			    throws SQLException {
		    	Object[] ui = new Object[7];
				ui[0] = rs.getString("order_id");
				ui[1] = rs.getString("order_type");
				ui[2] = rs.getString("order_status");
				ui[3] = rs.getString("check_point");
				ui[4] = rs.getString("reason_cd");
				ui[5] = rs.getDate("delivery_date");
				ui[6] = rs.getString("b4_1500");
				return ui;
		    }
		};
	
		try {
		    logger.info("getOrderInfo() @ StockDAO: " + getOrderInfoSQL);
	
		    itemList = simpleJdbcTemplate.query(getOrderInfoSQL, mapper, orderId);
		} catch (EmptyResultDataAccessException erdae) {
		    logger.info("EmptyResultDataAccessException in getOrderInfo()");
	
		    itemList = null;
		} catch (Exception e) {
		    logger.info("Exception caught in getOrderInfo():", e);
	
		    throw new DAOException(e.getMessage(), e);
		}
		return itemList;
    }
    
    public List<StockManualDetailDTO> getDoaDetail(String orderId) throws DAOException {
    	String SQL = " SELECT   stock_assgn.order_id, stock_assgn.item_type, stock_assgn.item_code, "
    			+ "         stock_assgn.item_desc, stock_assgn.item_serial, stock_info.LOCATION, "
    			+ "         doa_old_item_serial, NVL (doa_item.issue_ind, 'N') issue_ind "
    			+ "    FROM (SELECT a.order_id, b.item_type item_type_id, c.code_desc item_type, "
    			+ "                 a.item_code, b.item_desc, a.item_serial, "
    			+ "                 a.doa_old_item_serial "
    			+ "            FROM bomweb_stock_assgn a, "
    			+ "                 bomweb_stock_catalog b, "
    			+ "                 bomweb_code_lkup c "
    			+ "           WHERE a.order_id = ? "
    			+ "             AND a.item_code = b.item_code "
    			+ "             AND NVL (a.status_id, 0) <> '24' "
    			+ "             AND c.code_type = 'STOCK_TYPE' "
    			+ "             AND c.code_id = b.item_type) stock_assgn, "
    			+ "         (SELECT c.item_code, c.item_serial, d.code_desc LOCATION "
    			+ "            FROM bomweb_stock_inventory c, bomweb_code_lkup d "
    			+ "           WHERE c.LOCATION = d.code_id AND d.code_type = 'STOCK_LOC') stock_info, "
    			+ "         (SELECT a.order_id, "
    			+ "                 CASE order_id "
    			+ "                    WHEN ? "
    			+ "                       THEN 'Y' "
    			+ "                    ELSE 'N' "
    			+ "                 END issue_ind, a.doa_item_code "
    			+ "            FROM bomweb_doa_item a, (SELECT MAX (seq_no) seq_no "
    			+ "                                       FROM bomweb_doa_item where order_id = ?) b "
    			+ "           WHERE a.seq_no = b.seq_no AND a.order_id = ?) doa_item "
    			+ "   WHERE stock_assgn.item_code = stock_info.item_code(+) "
    			+ "     AND stock_assgn.item_serial = stock_info.item_serial(+) "
    			+ "     AND doa_item.doa_item_code(+) = stock_assgn.item_code "
    			+ "ORDER BY stock_assgn.item_type_id ASC ";
    	
    	logger.debug("getDoaDetail @ StockDAO is called");
		List<StockManualDetailDTO> itemList = new ArrayList<StockManualDetailDTO>();
	
		ParameterizedRowMapper<StockManualDetailDTO> mapper = new ParameterizedRowMapper<StockManualDetailDTO>() {
		    public StockManualDetailDTO mapRow(ResultSet rs, int rowNum)
			    throws SQLException {
	
			StockManualDetailDTO tempDto = new StockManualDetailDTO();
			tempDto.setOrderId(rs.getString("order_id"));
			tempDto.setItemType(rs.getString("item_type"));
			tempDto.setItemCode(rs.getString("item_code"));
			tempDto.setDescriptions(rs.getString("item_desc"));
			tempDto.setSerialNum(rs.getString("item_serial"));
			tempDto.setLocation(rs.getString("location"));
			tempDto.setOldSerialNum(rs.getString("doa_old_item_serial"));
			tempDto.setIssueInd(rs.getString("issue_ind"));
			return tempDto;
		    }
		};
	
		try {
		    logger.info("getDoaDetail() @ StockDAO: "
			    + SQL);
	
		    itemList = simpleJdbcTemplate.query(SQL,
			    mapper, orderId, orderId, orderId, orderId);
		} catch (EmptyResultDataAccessException erdae) {
		    logger.info("EmptyResultDataAccessException in getDoaDetail()");
	
		    itemList = null;
		} catch (Exception e) {
		    logger.info("Exception caught in getDoaDetail():", e);
	
		    throw new DAOException(e.getMessage(), e);
		}
		return itemList;
    }
    
    // add by Joyce 20120229
    public boolean deassignPerItem(String orderId, String itemCode, String itemSerial) throws DAOException {

		logger.debug("deassignPerItem is called");
	
		boolean result = false;
	
		try {
	
		    SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
			    .withSchemaName("OPS$CNM")
			    .withCatalogName("pkg_stock_assign")
			    .withProcedureName("deassign_by_item");
		    jdbcCall.setAccessCallParameterMetaData(false);
		    jdbcCall.declareParameters(
		    		new SqlParameter("i_order_id", Types.VARCHAR), 
		    		new SqlParameter("i_item_code", Types.VARCHAR), 
		    		new SqlParameter("i_item_serial", Types.VARCHAR), 
		    		new SqlOutParameter("gnRetVal", Types.INTEGER), 
		    		new SqlOutParameter("gnErrCode", Types.INTEGER), 
		    		new SqlOutParameter("gnErrText", Types.VARCHAR));
	
		    MapSqlParameterSource inMap = new MapSqlParameterSource();
		    inMap.addValue("i_order_id", orderId);
		    inMap.addValue("i_item_code", itemCode);
		    inMap.addValue("i_item_serial", itemSerial);
	
		    SqlParameterSource in = inMap;
		    Map<String, Object> out = jdbcCall.execute(in);
	
		    int error_code = -10;
		    int retVal = -10;
		    String error_text = null;
	
		    if (((Integer) out.get("gnErrCode")) != null) {
			error_code = ((Integer) out.get("gnErrCode")).intValue();
			logger.info("OPS$CNM.pkg_stock_assign.deassign_by_item() output gnErrCode = "
				+ error_code);
		    }
	
		    if (((Integer) out.get("gnRetVal")) != null) {
			retVal = ((Integer) out.get("gnRetVal")).intValue();
			logger.info("OPS$CNM.pkg_stock_assign.deassign_by_item() output gnRetVal = "
				+ retVal);
		    }
	
		    if ((out.get("gnErrText")) != null) {
			error_text = out.get("gnErrText").toString();
			logger.info("OPS$CNM.pkg_stock_assign.deassign_by_item() output gnErrText = "
				+ error_text);
		    } else {
			error_text = null;
			logger.info("OPS$CNM.pkg_stock_assign.deassign_by_item() output gnErrText = "
				+ error_text);
		    }
	
		    if (error_text == null) {
			result = true;
		    }
	
		    logger.info("deassignPerItem result = " + result);
	
		    return result;
	
		} catch (Exception e) {
		    logger.error("Exception caught in deassignPerItem()", e);
		    throw new DAOException(e.getMessage(), e);
		}
    }
    
    public List<String> checkStockQty(String orderId) throws DAOException {
    	
    	// MIP.P4 modification
    	logger.debug("MIP.P4 modification: pkg_sb_mob_stock.get_stock_pool_mip4 (6)");
    	
    	String SQL = "SELECT (RPAD(ITEM_CODE, 20, ' ') " 
    			+"  || RPAD(LOCATION_DESC, 20, ' ') " 
    			+"  || QTY) TEXT " 
    			+"FROM " 
    			+"  (SELECT ITEM_CODE, " 
    			+"    LOCATION_DESC, "  
    			+"    DECODE(ASSIGN_MODE_DESC, 'AUTO', TO_CHAR(STOCK_QTY), 'No stock/manual assign') QTY " 
    			+"  FROM " 
    			+"    (SELECT a.ITEM_CODE, " 
    			+"      B.CODE_DESC LOCATION_DESC, " 
    			+"      E.CODE_DESC ASSIGN_MODE_DESC, " 
    			+"      COUNT(C.STATUS_ID) STOCK_QTY " 
    			+"    FROM BOMWEB_STOCK_ASSGN a, " 
    			+"      (SELECT CODE_ID, " 
    			+"        CODE_DESC " 
    			+"      FROM BOMWEB_CODE_LKUP " 
    			+"      WHERE CODE_TYPE = 'STOCK_LOC' " 
    			+"      ) B, " 
    			+"      (SELECT ITEM_CODE, " 
    			+"        ITEM_SERIAL, " 
    			+"        STATUS_ID, " 
    			+"        location " 
    			+"      FROM BOMWEB_STOCK_INVENTORY " 
    			+"      WHERE STATUS_ID = '02' " 
    			
    			+"      AND STOCK_POOL = pkg_sb_mob_stock.get_stock_pool_mip4(?) " 
    			
    			+"      ) C, " 
    			+"      BOMWEB_STOCK_CATALOG D, " 
    			+"      (SELECT CODE_ID, " 
    			+"        CODE_DESC " 
    			+"      FROM BOMWEB_CODE_LKUP " 
    			+"      WHERE CODE_TYPE = 'STOCK_MODE' " 
    			+"      ) E " 
    			+"    WHERE a.ITEM_CODE = C.ITEM_CODE(+) " 
    			+"    AND a.ORDER_ID    = ? " 
    			+"    and NVL(A.STATUS_ID, '19') = '19' "  
    			+"    AND C.location    = B.CODE_ID(+) " 
    			+"    AND C.ITEM_CODE   = D.ITEM_CODE(+) " 
    			+"    AND D.ASSIGN_MODE = E.CODE_ID(+) " 
    			+"    GROUP BY a.ITEM_CODE, " 
    			+"      C.location, " 
    			+"      B.CODE_DESC, " 
    			+"      D.ASSIGN_MODE, " 
    			+"      E.CODE_DESC " 
    			+"    ORDER BY a.ITEM_CODE, " 
    			+"      C.location, " 
    			+"      B.CODE_DESC, " 
    			+"      D.ASSIGN_MODE, " 
    			+"      E.CODE_DESC " 
    			+"    ) " 
    			+"  )";
    	
    	logger.debug("checkStockQty @ StockDAO is called");
		List<String> itemList = new ArrayList<String>();
	
		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
		    public String mapRow(ResultSet rs, int rowNum) throws SQLException {
		    	return rs.getString("TEXT");
		    }
		};
	
		try {
		    logger.debug("checkStockQty() @ StockDAO: " + SQL);
		    itemList = simpleJdbcTemplate.query(SQL, mapper, orderId, orderId);
		    
		} catch (EmptyResultDataAccessException erdae) {
		    logger.info("EmptyResultDataAccessException in checkStockQty()");
		    itemList = null;
		    
		} catch (Exception e) {
		    logger.info("Exception caught in checkStockQty():", e);
		    throw new DAOException(e.getMessage(), e);
		    
		}
		return itemList;
    }

    public List<StockAssgnDTO> getStockAssgnList(String basketID, String[] vasItemList) throws DAOException {
    	logger.info("getStockAssgnList @ StockDAO is called");
    	MapSqlParameterSource params = new MapSqlParameterSource();
    	
    	String getStockAssgnListSQL	= "SELECT \n"
				    			   + "	itemlist.item_id, \n"
				    			   + "	i.type, bsc.item_type, \n"
				    			   + "	bsc.item_code, \n"
				    			   + "	bsc.item_desc \n"
				    			   + "FROM \n"
				    			   + "	bomweb_stock_catalog bsc \n"
				    			   + "INNER JOIN \n"
				    			   + "	(SELECT item_id, pos_item_cd FROM w_item_product_pos_assgn \n"
				    			   + "	WHERE item_id IN (SELECT item_id FROM w_basket_item_assgn bia WHERE bia.basket_id = :basketId) \n"
				    			   + "	UNION \n"
				    			   + "		SELECT item_id, pos_item_cd FROM w_item_product_pos_assgn \n"
				    			   + "		WHERE item_id IN (:vasList)) itemlist \n"
				    			   + "	ON itemlist.pos_item_cd = bsc.item_code \n"
				    			   + "JOIN \n"
				    			   + "	w_item i \n"
				    			   + "	ON itemlist.item_id = i.id \n"
				    			   + "WHERE \n"
				    			   + "	bsc.item_type != '02' \n";
    	
    	if (!this.isEmpty(vasItemList)) {
    		params.addValue("vasList", Arrays.asList(vasItemList));
    	} else {
    		params.addValue("vasList", null);
    	}
    	params.addValue("basketId", basketID);
    	
    	/*String getStockAssgnListSQL = 
    			"select itemlist.item_id, i.type, bsc.item_type, bsc.item_code, bsc.item_desc "
    			+ "from bomweb_stock_catalog bsc "
    			+ "inner join "
    			+ "    (select item_id, pos_item_cd from w_item_product_pos_assgn"
    			+ "    where (item_id in"
    			+ "       (select item_id from w_basket_item_assgn bia"
    			+ "       where bia.basket_id = :basketId)";
    	if (!this.isEmpty(vasItemList)) {
    		getStockAssgnListSQL += "    or item_id in (:vasList)";
    		params.addValue("vasList", Arrays.asList(vasItemList));
    	}
    	getStockAssgnListSQL += "    )) itemlist "
    			+ "on itemlist.pos_item_cd = bsc.item_code "
    			+ "join w_item i on itemlist.item_id = i.id "
    			+ "where bsc.item_type != '02'";
    	params.addValue("basketId", basketID);*/
    	
    	ParameterizedRowMapper<StockAssgnDTO> mapper = new ParameterizedRowMapper<StockAssgnDTO>() {
    		public StockAssgnDTO mapRow(ResultSet rs, int rowNum)
    			    throws SQLException {
    			StockAssgnDTO dto = new StockAssgnDTO();
    			dto.setItemId(rs.getString("item_id"));
    			dto.setType(rs.getString("type"));
    			dto.setItemType(rs.getString("item_type"));
    			dto.setItemCode(rs.getString("item_code"));
    			dto.setItemDesc(rs.getString("item_desc"));
    			return dto;
		    }
    	};
    	
    	List<StockAssgnDTO> itemList = new ArrayList<StockAssgnDTO>();
    	try {
		    logger.info("getStockAssgnList() @ StockDAO: " + getStockAssgnListSQL);
		    itemList = simpleJdbcTemplate.query(getStockAssgnListSQL, mapper, params);
		} catch (EmptyResultDataAccessException erdae) {
		    logger.info("EmptyResultDataAccessException in getStockAssgnList()");
		    itemList = null;
		} catch (Exception e) {
		    logger.info("Exception caught in getStockAssgnList():", e);
		    throw new DAOException(e.getMessage(), e);
		}
		return itemList;
    
    }
    public List<String> getMDVSimNum(Date checkDate, String staffID, int simType) throws DAOException {
    	logger.debug("getMDVSimNum @ StockDAO is called");
    	String SQL = "select item_serial "
    			+ "from bomweb_stock_inventory bsi, bomweb_stock_catalog bsc, bomweb_event be "
    			+ "where stock_pool = 'DS' "
    			+ "and bsi.event_cd = be.event_cd (+) "
    			+ "and bsi.item_code = bsc.item_code "
    			+ "and ((status_id = 27 and staff_id = ?) "
    			+ "		or (status_id = 28 and (trunc(?) between eff_start_date and eff_end_date) "
    			+ "		and ? in (select staff_id from BOMWEB_EVENT_ASSGN bea "
    			+ "			where bea.event_cd = bsi.event_cd "
    			+ "			and trunc(?) between bea.eff_start_date and bea.eff_end_date)"
    			+ "		)"
    			+ ") "
    			+ "and bsc.item_type = 2 "
    			+ "and bsc.hs_extra_function = ?";
    	
    	ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
    		public String mapRow(ResultSet rs, int rowNum)
    			    throws SQLException {
    			return rs.getString("item_serial");
		    }
    	};
    	List<String> simList = new ArrayList<String>();
    	try {
    		logger.debug("sql in getMDVSimNum = " + SQL);
    		simList = simpleJdbcTemplate.query(SQL, mapper,
				    staffID, checkDate, staffID, checkDate, simType);
			if (simList.size() > 0) {
				return simList;
			} else {
				return null;
			}
		} catch (EmptyResultDataAccessException erdae) {
		    logger.info("EmptyResultDataAccessException in getMDVSimNum()");
		    return null;
		} catch (Exception e) {
		    logger.info("Exception caught in getMDVSimNum():", e);
		    throw new DAOException(e.getMessage(), e);
		}
    }
    
    public List<String> getSISSimNum(Date checkDate, String staffId, int simType, String storeCd, String teamCd) throws DAOException {
    	logger.debug("getSISSimNum @ StockDAO is called");
    	String SQL = "select item_serial "
    			+ "from bomweb_stock_inventory bsi, bomweb_stock_catalog bsc "
    			+ "where stock_pool = 'DS' "
    			+ "and bsi.item_code = bsc.item_code "
    			+ "and bsc.item_type = 2 "
    			+ "and status_id  = 27 "
    			+ "and store_cd = ? "
    			+ "and team_cd = ? "
    			+ "and bsc.hs_extra_function = ?";
    	ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
    		public String mapRow(ResultSet rs, int rowNum)
    			    throws SQLException {
    			return rs.getString("item_serial");
		    }
    	};
    	
    	List<String> simList = new ArrayList<String>();
    	try {
    		logger.info("sql in getSISSimNum = " + SQL);
    		simList = simpleJdbcTemplate.query(SQL, mapper, storeCd, teamCd, simType);
			if (simList.size() > 0) {
				return simList;
			} else {
				return null;
			}
		} catch (EmptyResultDataAccessException erdae) {
		    logger.info("EmptyResultDataAccessException in getSISSimNum()");
		    return null;
		} catch (Exception e) {
		    logger.info("Exception caught in getSISSimNum():", e);
		    throw new DAOException(e.getMessage(), e);
		}
    }
    
    public List<String> getMDVSerialNum(Date checkDate, String staffID, String itemCd) throws DAOException {
    	logger.debug("getMDVSerialNum @ StockDAO is called");
		String SQL = "select * from "
    			+ "bomweb_stock_inventory bsi "
    			+ "left join bomweb_event be on bsi.event_cd = be.event_cd "
    			+ "where stock_pool = 'DS' "
    			+ "and ((status_id = 27 and staff_id = ?) "
    			+ "		or (status_id = 28 and (trunc(?) between eff_start_date and eff_end_date) "
    			+ "		and ? in (select staff_id from BOMWEB_EVENT_ASSGN bea "
    			+ "			where bea.event_cd = bsi.event_cd "
    			+ "			and trunc(?) between bea.eff_start_date and bea.eff_end_date)"
    			+ "		)"
    			+ ") "
    			+ "and item_code=?";
		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
    		public String mapRow(ResultSet rs, int rowNum)
    			    throws SQLException {
    			return rs.getString("item_serial");
		    }
    	};
    	List<String> serialList = new ArrayList<String>();
    	try {
    		serialList = simpleJdbcTemplate.query(SQL, mapper,
				    staffID, checkDate, staffID, checkDate, itemCd);
			if (serialList.size() > 0) {
				return serialList;
			} else {
				return null;
			}
		} catch (EmptyResultDataAccessException erdae) {
		    logger.info("EmptyResultDataAccessException in getMDVSerialNum()");
		    return null;
		} catch (Exception e) {
		    logger.info("Exception caught in getMDVSerialNum():", e);
		    throw new DAOException(e.getMessage(), e);
		}
    }
    
    public List<String> getSISSerialNum(String itemCd, String storeCd, String teamCd) throws DAOException {
    	logger.debug("getSISSerialNum @ StockDAO is called");
    	String SQL = "select * from "
    			+ "bomweb_stock_inventory "
    			+ "where stock_pool = 'DS' "
    			+ "and status_id in (27) "
    			+ "and store_cd = ? "
    			+ "and team_cd = ? "
    			+ "and item_code=?";
    	ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
    		public String mapRow(ResultSet rs, int rowNum)
    			    throws SQLException {
    			return rs.getString("item_serial");
		    }
    	};
    	List<String> serialList = new ArrayList<String>();
    	try {
    		serialList = simpleJdbcTemplate.query(SQL, mapper,
    				storeCd, teamCd, itemCd);
			if (serialList.size() > 0) {
				return serialList;
			} else {
				return null;
			}
		} catch (EmptyResultDataAccessException erdae) {
		    logger.info("EmptyResultDataAccessException in getSISSerialNum()");
		    return null;
		} catch (Exception e) {
		    logger.info("Exception caught in getSISSerialNum():", e);
		    throw new DAOException(e.getMessage(), e);
		}
    }
    
    public int isValidSerial_MDV(Date checkDate, String staffID, String itemCd, String serialNum) throws DAOException {
    	logger.debug("isValidSerialMDV @ StockDAO is called");
    	String SQL = "select * from "
    			+ "bomweb_stock_inventory bsi "
    			+ "left join bomweb_event be on bsi.event_cd = be.event_cd "
    			+ "where stock_pool = 'DS' "
    			+ "and ((status_id = 27 and staff_id = ?) "
    			+ "		or (status_id = 28 and (trunc(?) between eff_start_date and eff_end_date) "
    			+ "		and ? in (select staff_id from BOMWEB_EVENT_ASSGN bea "
    			+ "			where bea.event_cd = bsi.event_cd "
    			+ "			and trunc(?) between bea.eff_start_date and bea.eff_end_date)"
    			+ "		)"
    			+ "		or (status_id not in (27, 28))) "
    			+ "and item_serial=? "
    			+ "and item_code=?";
    	
    	ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
    		public String mapRow(ResultSet rs, int rowNum)
    			    throws SQLException {
    			return rs.getString("status_id");
		    }
    	};
    	List<String> itemList = new ArrayList<String>();
    	try {
			itemList = simpleJdbcTemplate.query(SQL, mapper,
				    staffID, checkDate, staffID, checkDate, serialNum, itemCd);
			if (itemList.size() > 0) {
				return Integer.parseInt(itemList.get(0));
			} else {
				return 0;
			}
		} catch (EmptyResultDataAccessException erdae) {
		    logger.info("EmptyResultDataAccessException in isValidSerialMDV()");
		    return -1;
		} catch (Exception e) {
		    logger.info("Exception caught in isValidSerialMDV():", e);
		    throw new DAOException(e.getMessage(), e);
		}
    }
    
    public int isValidSerial_SIS(Date checkDate, String staffId, String itemCd, String serialNum, String storeCd, String teamCd) throws DAOException {
    	logger.debug("isValidSerialSIS @ StockDAO is called");
    	String SQL = "select * from "
    			+ "bomweb_stock_inventory "
    			+ "where stock_pool = 'DS' "
    			+ "and status_id not in (28) "
    			+ "and store_cd = ? "
    			+ "and team_cd = ? "
    			+ "and item_serial=? "
    			+ "and item_code=?";
    	
    	ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
    		public String mapRow(ResultSet rs, int rowNum)
    			    throws SQLException {
    			return rs.getString("status_id");
		    }
    	};
    	List<String> itemList = new ArrayList<String>();
    	try {
			itemList = simpleJdbcTemplate.query(SQL, mapper,
					storeCd, teamCd, serialNum, itemCd);
			if (itemList.size() > 0) {
				return Integer.parseInt(itemList.get(0));
			} else {
				return 0;
			}
		} catch (EmptyResultDataAccessException erdae) {
		    logger.info("EmptyResultDataAccessException in isValidSerialSIS()");
		    return -1;
		} catch (Exception e) {
		    logger.info("Exception caught in isValidSerialSIS():", e);
		    throw new DAOException(e.getMessage(), e);
		}
    }
    
    public String getSimType(String itemCd) throws DAOException {
    	logger.debug("getSimType @ StockDAO is called");
    	String SQL = "select * from "
    			+ "bomweb_stock_catalog "
    			+ "where item_code = ? "
    			+ "and item_type = '02' ";
    	ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
    		public String mapRow(ResultSet rs, int rowNum)
    			    throws SQLException {
    			return rs.getString("hs_extra_function");
		    }
    	};
    	List<String> serialList = new ArrayList<String>();
    	try {
    		serialList = simpleJdbcTemplate.query(SQL, mapper, itemCd);
			if (serialList.size() > 0) {
				return serialList.get(0);
			} else {
				return null;
			}
		} catch (EmptyResultDataAccessException erdae) {
		    logger.info("EmptyResultDataAccessException in getSimType()");
		    return null;
		} catch (Exception e) {
		    logger.info("Exception caught in getSimType():", e);
		    throw new DAOException(e.getMessage(), e);
		}
    }
    
    public String getPreviousStockStatusID(String item_code, String item_serial) throws DAOException {
    	String SQL = "select status_id_from from bomweb_stock_inventory_hist "
    			+ "where status_id_to = (select status_id from bomweb_stock_inventory "
    			+ " 	where item_serial=? "
    			+ " 	and item_code =?) "
    			+ "and item_serial=? "
    			+ "and item_code=? "
    			//+ "and rownum <=1 "
    			+ "order by create_date desc";
    	ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
    		public String mapRow(ResultSet rs, int rowNum)
    			    throws SQLException {
    			return rs.getString("status_id_from");
		    }
    	};
    	List<String> itemList = new ArrayList<String>();
    	try {
			itemList = simpleJdbcTemplate.query(SQL, mapper,
				    item_serial, item_code, item_serial, item_code);
			if (itemList.size() > 0) {
				return itemList.get(0);
			} 
		} catch (EmptyResultDataAccessException erdae) {
		    logger.info("EmptyResultDataAccessException in isValidSerialMDV()");
		    return null;
		} catch (Exception e) {
		    logger.info("Exception caught in isValidSerialMDV():", e);
		    throw new DAOException(e.getMessage(), e);
		}
    	return null;
    }
    
    public void insertBomWebStockAssgn(StockAssgnDTO stockAssgnDTO, String orderId, String status) throws DAOException {
		String SQL = "insert into bomweb_stock_assgn "
				+ "(order_id, item_code, item_serial, status_id, ao_ind, sm_no, sm2_no, sm_issued_ind, ao_report_sent, "
				+ "apply_date, create_by, create_date, last_upd_by, last_upd_date, member_num) "
				+ "VALUES "
				+ "(?, ?, ?, ?, ?, ?, ?, ?, ?, "
				+ "sysdate, 'SBMDS', sysdate, 'SBMDS', sysdate, ?)";
		/*String SQLHist = "insert into bomweb_stock_assgn_hist "
				+ "(order_id, item_code, b_item_serial, b_status_id, a_item_serial, a_status_id, "
				+ "ao_ind, sm_no, sm2_no, sm_issued_ind, "
				+ "action, create_by, create_date, hist_seq) "
				+ "VALUES "
				+ "(?, ?, null, null, ?, ?, "
				+ "?, ?, ?, ?, "
				+ "'INSERT', 'SBMDS', sysdate, (select max(hist_seq) from bomweb_stock_assgn_hist) + 1)";*/
		try {
			logger.debug("insertBomWebStockAssgn is called");
			simpleJdbcTemplate.update(SQL, orderId,
					stockAssgnDTO.getItemCode(),
					stockAssgnDTO.getItemSerial(),
					status,
					stockAssgnDTO.getAoInd(),
					stockAssgnDTO.getSalesMemoNum(),
					stockAssgnDTO.getSalesMemoNum2(),
					stockAssgnDTO.getSalesMemoInd(),
					stockAssgnDTO.getAoReportSent(),
					stockAssgnDTO.getMemberNum());
			/*simpleJdbcTemplate.update(SQLHist, orderId,
					stockAssgnDTO.getItemCode(),
					stockAssgnDTO.getItemSerial(),
					status,
					stockAssgnDTO.getAoInd(),
					stockAssgnDTO.getSalesMemoNum(),
					stockAssgnDTO.getSalesMemoNum2(),
					stockAssgnDTO.getSalesMemoInd());*/
		}catch (Exception e) {
			logger.error("Exception caught in insertBomWebStockAssgn()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
    public List<StockAssgnDTO> getStockAssgnListByOrderId(String orderId) throws DAOException {
    	logger.debug("getStockAssgnListByOrderId @ StockDAO is called");
    	
    	String getStockAssgnListSQL = "select bsa.item_code, bsa.item_serial, bsa.ao_ind, bsa.sm_no, bsa.sm2_no, bsa.sm_issued_ind, bsa.status_id, "
    			+ "bsc.item_type, bsc.item_desc, bsa.ao_report_sent, bsa.member_num "
    			+ "from bomweb_stock_assgn bsa "
    			+ "left join bomweb_stock_catalog bsc "
    			+ "on bsa.ITEM_CODE = bsc.item_code "
    			+ "where order_id=? ";
    	
    	ParameterizedRowMapper<StockAssgnDTO> mapper = new ParameterizedRowMapper<StockAssgnDTO>() {
    		public StockAssgnDTO mapRow(ResultSet rs, int rowNum)
    			    throws SQLException {
    			StockAssgnDTO dto = new StockAssgnDTO();
    			dto.setItemCode(rs.getString("item_code"));
    			dto.setItemSerial(rs.getString("item_serial"));
    			dto.setAoInd(rs.getString("AO_IND"));
    			dto.setSalesMemoNum(rs.getString("SM_NO"));
    			dto.setSalesMemoNum2(rs.getString("SM2_NO"));
    			dto.setSalesMemoInd(rs.getString("SM_ISSUED_IND"));
    			dto.setStatusId(rs.getString("status_id"));
    			dto.setItemDesc(rs.getString("item_desc"));
    			if ("UATSIMSIGNOFF".equals(dto.getItemSerial())) {
    				dto.setItemType("02");
    			} else {
    				dto.setItemType(rs.getString("item_type"));
    			}
    			dto.setAoReportSent(rs.getString("ao_report_sent"));
    			dto.setMemberNum(rs.getString("member_num"));
    			return dto;
		    }
    	};
    	
    	List<StockAssgnDTO> itemList = new ArrayList<StockAssgnDTO>();
    	try {
		    logger.info("getStockAssgnListByOrderId() @ StockDAO: " + getStockAssgnListSQL);
		    itemList = simpleJdbcTemplate.query(getStockAssgnListSQL, mapper, orderId);
		} catch (EmptyResultDataAccessException erdae) {
		    logger.info("EmptyResultDataAccessException in getStockAssgnListByOrderId()");
		    itemList = null;
		} catch (Exception e) {
		    logger.info("Exception caught in getStockAssgnListByOrderId():", e);
		    throw new DAOException(e.getMessage(), e);
		}
		return itemList;
    }
    
	public int updateStockInventory(String item_code, String item_serial, String status_id)
			throws DAOException {
		logger.debug("updateStockInventory() is called");
		
		String SQLHist = "insert into bomweb_stock_inventory_hist "
				+ "(item_code, item_serial, status_id_from, status_id_to, "
				+ "event_cd, store_cd, team_cd, book_out_date, staff_id, "
				+ "create_by, create_date) "
				+ "(select item_code, item_serial, status_id, ?, "
				+ "event_cd, store_cd, team_cd, book_out_date, staff_id, "
				+ "'SBMDS', sysdate "
				+ "from bomweb_stock_inventory "
				+ "where item_code=? "
				+ "and item_serial=?)";
		String SQL = "update bomweb_stock_inventory " +
				"set status_id = ?, " +
				"last_upd_by = 'SBMDS', " +
				"last_upd_date = sysdate " +
				"where item_serial = ? " +
				"and   item_code = ?";

		try {
			simpleJdbcTemplate.update(SQLHist, 
					status_id, 
					item_code, 
					item_serial
					);
			return simpleJdbcTemplate.update(SQL, 
					status_id, 
					item_serial, 
					item_code
					);
			 
		} catch (Exception e) {
			logger.error("Exception caught in updateStockInventory()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public int cancelDSOrderReleaseStock(String orderId)
			throws DAOException {
		logger.debug("updateStockInventory() is called");
		
		String SQLHist = "insert into bomweb_stock_inventory_hist "
				+ "(item_code, item_serial, status_id_from, status_id_to, "
				+ "event_cd, store_cd, team_cd, book_out_date, staff_id, "
				+ "create_by, create_date) "
				+ "(select item_code, item_serial, status_id, "
				+ "    decode(status_id, 32, 20, 33, 30, 34, 26, status_id), "
				+ "    event_cd, store_cd, team_cd, book_out_date, staff_id, "
				+ "    'AutoProcess', sysdate "
				+ "    from bomweb_stock_inventory "
				+ "    where (item_code, item_serial) in "
				+ "    (select item_code, item_serial "
				+ "        from bomweb_stock_assgn "
				+ "        where order_id=?) "
				+ "    and status_id in (32, 33, 34)"
				+ ")";
		String SQL = "update bomweb_stock_inventory "
				+ "set status_id=decode(status_id, 32, 20, 33, 30, 34, 26, status_id) "
				+ "where (item_code, item_serial) in "
				+ "(select item_code, item_serial from bomweb_stock_assgn "
				+ "where order_id=?)";

		try {
			simpleJdbcTemplate.update(SQLHist, orderId);
			return simpleJdbcTemplate.update(SQL, orderId);
		} catch (Exception e) {
			logger.error("Exception caught in cancelDSOrderReleaseStock()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public void deleteBomWebStockAssgn(String orderId) throws DAOException {
		logger.debug("deleteBomWebStockAssgn is called");
		String SQL = "delete bomweb_stock_assgn where order_id= ?";
		/*String SQLHist = "insert into bomweb_stock_assgn_hist "
				+ "(order_id, item_code, b_item_serial, b_status_id, a_item_serial, a_status_id, "
				+ "ao_ind, sm_no, sm2_no, sm_issued_ind, "
				+ "action, create_by, create_date, hist_seq) "
				+ "(select order_id, item_code, item_serial, status_id, item_serial, 24, "
				+ "ao_ind, sm_no, sm2_no, sm_issued_ind, "
				+ "'UPDATE', 'SBMDS', sysdate, (select max(hist_seq) from bomweb_stock_assgn_hist) + rownum "
				+ "from bomweb_stock_assgn "
				+ "where order_id=? and status_id is not null)";*/

		try {
			//simpleJdbcTemplate.update(SQLHist, orderId);
			simpleJdbcTemplate.update(SQL, orderId);
		} catch (Exception e) {
			logger.error("Exception caught in deleteBomWebStockAssgn()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public int updateBomWebStockAssgnStatus(String status_id, String orderId, String itemCode, String itemSerial) throws DAOException {
		logger.debug("updateBomWebStockAssgnStatus is called");
		String SQL = "update bomweb_stock_assgn set status_id = ? where order_id= ? and item_code=? and item_serial=?";
		/*String SQLHist = "insert into bomweb_stock_assgn_hist "
				+ "(order_id, item_code, b_item_serial, b_status_id, a_item_serial, a_status_id, "
				+ "ao_ind, sm_no, sm2_no, sm_issued_ind, "
				+ "action, create_by, create_date, hist_seq) "
				+ "(select order_id, item_code, item_serial, status_id, item_serial, ?, "
				+ "ao_ind, sm_no, sm2_no, sm_issued_ind, "
				+ "'UPDATE', 'SBMDS', sysdate, (select max(hist_seq) from bomweb_stock_assgn_hist) + rownum "
				+ "from bomweb_stock_assgn "
				+ "where order_id=? and item_code=? and item_serial=?)";*/

		try {
			//simpleJdbcTemplate.update(SQLHist, status_id, orderId, itemCode, itemSerial);
			return simpleJdbcTemplate.update(SQL, status_id, orderId, itemCode, itemSerial);
		} catch (Exception e) {
			logger.error("Exception caught in updateBomWebStockAssgnStatus()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public int backupBomWebStockAssgn(String orderId) throws DAOException {
		logger.debug("backupBomWebStockAssgn is called");
		
		String SQL = "insert into BOMWEB_DS_STOCK_ASSGN_HIST "
				+ "(order_id, item_code, item_serial, status_id, "
				+ "ao_ind, sm_no, sm2_no, sm_issued_ind, "
				+ "apply_date, doa_old_item_serial, doa_old_item_code, doa_return_ind, doa_reject_ind, "
				+ "create_by, create_date, last_upd_by, last_upd_date, "
				+ "seq_no, ao_report_sent, member_num) "
				+ "(select order_id, item_code, item_serial, status_id, "
				+ "ao_ind, sm_no, sm2_no, sm_issued_ind, "
				+ "apply_date, doa_old_item_serial, doa_old_item_code, doa_return_ind, doa_reject_ind, "
				+ "create_by, create_date, last_upd_by, last_upd_date, "
				+ "(select max(seq_no) from bomweb_order_hist where order_id=?) seq_no, ao_report_sent, member_num "
				+ "from bomweb_stock_assgn "
				+ "where order_id=?)";

		try {
			return simpleJdbcTemplate.update(SQL, orderId, orderId);
		} catch (Exception e) {
			logger.error("Exception caught in backupBomWebStockAssgn()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
    
	public String getStockPool(String orderId) throws DAOException {
    	
		logger.debug("getStockPool @ StockDAO is called");
		
    	// MIP.P4 modification
    	System.out.println("MIP.P4 modification: pkg_sb_mob_stock.get_stock_pool_mip4 (7)");
    	
		String SQL = "select pkg_sb_mob_stock.get_stock_pool_mip4(?) stock_pool from dual";
		String stockPool = "";
		
		try {
		    logger.info("getStockPool() @ StockDAO: " + SQL);
		    stockPool = (String) simpleJdbcTemplate.queryForObject(SQL, String.class, orderId);
		} catch (EmptyResultDataAccessException erdae) {
		    logger.info("EmptyResultDataAccessException in getStockPool()");
		    stockPool = "";
		} catch (Exception e) {
		    logger.info("Exception caught in getStockPool():", e);
		    throw new DAOException(e.getMessage(), e);
		}
		
		return stockPool;
	}
	
	public String getCCSStockPool(String channelCd) throws DAOException {
		logger.debug("getCCSStockPool @ StockDAO is called");
		
    	// MIP.P4 modification
    	System.out.println("MIP.P4 modification: pkg_sb_mob_stock.get_stock_pool_mip4 (8)");
    	
		String SQL = "select pkg_sb_mob_stock.get_stock_pool_mip4('PEND', ?) stock_pool from dual";
		String stockPool = "";
		
		try {
		    logger.info("getCCSStockPool() @ StockDAO: " + SQL);
		    stockPool = (String) simpleJdbcTemplate.queryForObject(SQL, String.class, channelCd);
		} catch (EmptyResultDataAccessException erdae) {
		    logger.info("EmptyResultDataAccessException in getCCSStockPool()");
		    stockPool = "";
		} catch (Exception e) {
		    logger.info("Exception caught in getCCSStockPool():", e);
		    throw new DAOException(e.getMessage(), e);
		}
		
		return stockPool;
	}
	
	public String[] getShopFromOrder (String orderid) throws DAOException {
		
		String sql = 
				  "select sales_channel, centre_cd, shop_cd, bom_shop_cd "
				+ "from bomweb_shop "
				+ "where shop_cd = substr(:orderid, 2, INSTR(:orderid, 'M', -1)-2) ";
		
		logger.debug("getShopFromOrder SQL: " + sql);
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("orderid", orderid);
		ParameterizedRowMapper<String[]> mapper = new ParameterizedRowMapper<String[]>() {
			public String[] mapRow(ResultSet rs, int row) throws SQLException {
				String[] shop = new String[4];
				shop[0] = rs.getString("sales_channel");
				shop[1] = rs.getString("centre_cd");
				shop[2] = rs.getString("shop_cd");
				shop[3] = rs.getString("bom_shop_cd");
				return shop;
			}
		};
		
		try {
			List<String[]> result = simpleJdbcTemplate.query(sql, mapper, params);
			
			logger.debug("getSalesChannelCd SQL result list: " + result);
			
			if (result == null || result.isEmpty()) {
				return null;
			} else {
				return result.get(0);
			}
			
		} catch (EmptyResultDataAccessException erdae) {
			logger.error("getSalesChannelCd EmptyResultDataAccessException: " + erdae);
		} catch (Exception e) {
			logger.error("getSalesChannelCd Exception: " + e, e);
			throw new DAOException(e.getMessage(), e);
		}
		
		return null;
	}
	
    private <T> boolean isEmpty(T[] values) {
		return values == null || values.length == 0;
	}
}
