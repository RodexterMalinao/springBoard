package com.bomwebportal.mob.ccs.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
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
import com.bomwebportal.mob.ccs.dto.StockCatalogDTO;
import com.bomwebportal.mob.ccs.dto.StockInDTO;
import com.bomwebportal.mob.ccs.dto.StockUpdateDTO;

public class StockInDAO extends BaseDAO {

	protected final Log logger = LogFactory.getLog(getClass());
	
	public List<StockInDTO> checkDuplicateStockIn(String itemCode, String imei)
			throws DAOException {
	
		logger.info("checkDuplicateStockIn @ StockInDAO is called");
		List<StockInDTO> itemList = new ArrayList<StockInDTO>();
	
		String checkDuplicateStockInSQL 
		= "SELECT item_code, item_serial, status_id, remarks," +
			"batch_ref, event_cd, store_cd, team_cd, to_char(book_out_date, 'dd/mm/yyyy') book_out_date, staff_id "
			+"   FROM bomweb_stock_inventory "
			+"  WHERE item_code = ? "
			+"    AND item_serial = ? ";

		ParameterizedRowMapper<StockInDTO> mapper = new ParameterizedRowMapper<StockInDTO>() {
			public StockInDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				StockInDTO dto = new StockInDTO();
				dto.setItemCode(rs.getString("item_code"));
				dto.setImei(rs.getString("item_serial"));
				dto.setStatus(rs.getString("status_id"));
				dto.setRemarks(rs.getString("remarks"));
				dto.setBatchRef(rs.getString("batch_ref"));
				dto.setEventCode(rs.getString("event_cd"));
				dto.setStoreCode(rs.getString("store_cd"));
				dto.setTeamCode(rs.getString("team_cd"));
				dto.setBookOutDate(rs.getString("book_out_date"));
				dto.setStaffId(rs.getString("staff_id"));
				return dto;
			}
		};
	
		try {
			logger.info("checkDuplicateStockIn() @ StockInDAO: "
					+ checkDuplicateStockInSQL);
	
			itemList = simpleJdbcTemplate.query(checkDuplicateStockInSQL, mapper,
					itemCode, imei);
		} catch (BadSqlGrammarException bsge) {
			logger.info("BadSqlGrammarException in checkDuplicateStockIn()");
	
			itemList = null;
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException in checkDuplicateStockIn()");
	
			itemList = null;
		} catch (Exception e) {
			logger.info("Exception caught in checkDuplicateStockIn(): ", e);
	
			throw new DAOException(e.getMessage(), e);
		}
		return itemList;
	}
	
	public String[] insertStockInventory(StockInDTO dto, String username)
			throws DAOException {
		logger.info("insertStockInventory() is called");

		/*// define SQL string
		String SQL = "insert into bomweb_stock_inventory " +
				"(item_code, item_serial, status_id, location, remarks, batch_ref, " +
				"create_by, create_date, last_upd_by, last_upd_date, stock_pool) " +
				"values (?, ?, ?, ?, upper(?), upper(?), " +
				"?, sysdate, ?, sysdate, ?)";

		// insert to table
		try {
			return simpleJdbcTemplate.update(SQL, 
					dto.getItemCode(), 
					dto.getImei(),
					dto.getStatus(), 
					dto.getLocation(),
					dto.getRemarks(),
					dto.getBatchRef(),
					username,
					username
					, dto.getStockPool());

		} catch (Exception e) {
			logger.error("Exception caught in insertStockInventory()", e);
			throw new DAOException(e.getMessage(), e);
		}*/
		

		logger.info("insertStockInventory is called");
	
		try {
	
		    SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
			    .withSchemaName("OPS$CNM")
			    .withCatalogName("pkg_stock_assign")
			    .withProcedureName("insert_stock_item_fe");
		    jdbcCall.setAccessCallParameterMetaData(false);
		    jdbcCall.declareParameters(
		    		new SqlParameter("i_item_code", Types.VARCHAR), 
		    		new SqlParameter("i_item_type", Types.VARCHAR), 
		    		new SqlParameter("i_item_serial_real", Types.VARCHAR), 
		    		new SqlParameter("i_item_qty", Types.VARCHAR), 
		    		new SqlParameter("i_status_id", Types.VARCHAR), 
		    		new SqlParameter("i_location", Types.VARCHAR), 
		    		new SqlParameter("i_batch_ref", Types.VARCHAR), 
		    		new SqlParameter("i_stock_pool", Types.VARCHAR), 
		    		new SqlOutParameter("o_start_serial", Types.VARCHAR), 
		    		new SqlOutParameter("o_end_serial", Types.VARCHAR), 
		    		new SqlOutParameter("gnRetVal", Types.INTEGER), 
		    		new SqlOutParameter("gnErrCode", Types.INTEGER), 
		    		new SqlOutParameter("gnErrText", Types.VARCHAR));
	
		    MapSqlParameterSource inMap = new MapSqlParameterSource();
		    inMap.addValue("i_item_code", dto.getItemCode());
		    logger.debug("SP itemcode: " + dto.getItemCode());
		    inMap.addValue("i_item_type", dto.getType());
		    logger.debug("SP type: " + dto.getType());
		    inMap.addValue("i_item_serial_real", dto.getImei());
		    logger.debug("i_item_serial_real: " + dto.getImei());
		    inMap.addValue("i_item_qty", dto.getQuantity());
		    logger.debug("i_item_qty: " + dto.getImei());
		    inMap.addValue("i_status_id", dto.getStatus());
		    logger.debug("SP status: " + dto.getStatus());
		    inMap.addValue("i_location", dto.getLocation());
		    logger.debug("SP location: " + dto.getLocation());
		    inMap.addValue("i_batch_ref", dto.getBatchRef());
		    logger.debug("SP batch ref: " + dto.getBatchRef());
		    inMap.addValue("i_stock_pool", dto.getStockPool());
		    logger.debug("SP stock pool: " + dto.getStockPool());
	
		    SqlParameterSource in = inMap;
		    Map out = jdbcCall.execute(in);
	
		    int error_code = -1;
		    int retVal = -1;
		    String error_text = "";
	
		    if (((Integer) out.get("gnErrCode")) != null) {
				error_code = ((Integer) out.get("gnErrCode")).intValue();
				logger.info("OPS$CNM.pkg_stock_assign.insert_stock_inv_item() output gnErrCode = "
					+ error_code);
		    }
	
		    if (((Integer) out.get("gnRetVal")) != null) {
				retVal = ((Integer) out.get("gnRetVal")).intValue();
				logger.info("OPS$CNM.pkg_stock_assign.insert_stock_inv_item() output gnRetVal = "
					+ retVal);
		    }
	
		    if ((out.get("gnErrText")) != null) {
				error_text = out.get("gnErrText").toString();
				logger.info("OPS$CNM.pkg_stock_assign.insert_stock_inv_item() output gnErrText = "
					+ error_text);
		    } else {
				error_text = null;
				logger.info("OPS$CNM.pkg_stock_assign.insert_stock_inv_item() output gnErrText = "
					+ error_text);
		    }
		    
		    String startSerial = "";
		    
		    if ((out.get("o_start_serial")) != null) {
		    	startSerial = out.get("o_start_serial").toString();
				logger.info("OPS$CNM.pkg_stock_assign.insert_free_gift_item() output o_start_serial = "
					+ startSerial);
		    } else {
		    	startSerial = null;
				logger.info("OPS$CNM.pkg_stock_assign.insert_free_gift_item() output o_start_serial = "
					+ startSerial);
		    }
		    
		    String endSerial = "";
		    
		    if ((out.get("o_end_serial")) != null) {
		    	endSerial = out.get("o_end_serial").toString();
				logger.info("OPS$CNM.pkg_stock_assign.insert_free_gift_item() output o_end_serial = "
					+ endSerial);
		    } else {
		    	endSerial = null;
				logger.info("OPS$CNM.pkg_stock_assign.insert_free_gift_item() output o_end_serial = "
					+ endSerial);
		    }
		    	
		    return new String[]{ ""+retVal, ""+error_code, error_text, startSerial ,endSerial };
	
		} catch (Exception e) {
		    logger.error("Exception caught in insertFreeGiftItem()", e);
		    throw new DAOException(e.getMessage(), e);
		}
    
		
		
		
		
	}
	
	public int insertDsStockInventory(StockInDTO dto, String username)
			throws DAOException {
		logger.info("insertDsStockInventory() is called");

		// define SQL string
		String SQL = "insert into bomweb_stock_inventory " +
				"(item_code, item_serial, status_id, remarks, batch_ref, " +
				"create_by, create_date, last_upd_by, last_upd_date, stock_pool," +
				"event_cd, store_cd, team_cd, book_out_date, staff_id) " +
				"values (?, ?, ?, ?, upper(?), " +
				"?, sysdate, ?, sysdate, ?, " +
				"?, ?, ?, to_date(?, 'dd/mm/yyyy'), ?)";

		// insert to table
		try {
			return simpleJdbcTemplate.update(SQL, 
					dto.getItemCode(), 
					dto.getImei(),
					dto.getStatus(), 
					dto.getRemarks(),
					dto.getBatchRef(),
					username,
					username,
					dto.getStockPool(),
					dto.getEventCode(),
					dto.getStoreCode(),
					dto.getTeamCode(),
					dto.getBookOutDate(),
					dto.getStaffId());

		} catch (Exception e) {
			logger.error("Exception caught in insertDsStockInventory()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}

	public String updateStockInventory(StockUpdateDTO dto, String username)
			throws DAOException {
		logger.info("updateStockInventory() is called");

		 SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
				    .withSchemaName("OPS$CNM")
				    .withCatalogName("pkg_stock_assign")
				    .withProcedureName("update_stock_item_fe");
			    jdbcCall.setAccessCallParameterMetaData(false);
			    jdbcCall.declareParameters( 
			    		new SqlParameter("i_item_code", Types.VARCHAR), 
			    		new SqlParameter("i_item_type", Types.VARCHAR), 
			    		new SqlParameter("i_item_serial", Types.VARCHAR),
			    		new SqlParameter("i_batch_ref", Types.VARCHAR),
			    		new SqlParameter("i_old_status_id", Types.VARCHAR),
			    		new SqlParameter("i_new_status_id", Types.VARCHAR),
			    		new SqlParameter("i_location", Types.VARCHAR),
			    		new SqlParameter("i_old_stock_pool", Types.VARCHAR), 
			    		new SqlParameter("i_new_stock_pool", Types.VARCHAR), 
			    		new SqlParameter("i_item_serial_inv_ccs",Types.VARCHAR),
			    		new SqlParameter("i_remark", Types.VARCHAR),	
			      		new SqlOutParameter("o_action", Types.INTEGER), 
			    		new SqlOutParameter("o_item_serial", Types.VARCHAR), 
			    		new SqlOutParameter("o_item_serial_ccs_real", Types.VARCHAR),
			    		new SqlOutParameter("gnRetVal", Types.INTEGER), 
			    		new SqlOutParameter("gnErrCode", Types.INTEGER), 
			    		new SqlOutParameter("gnErrText", Types.VARCHAR)
			    		);
		
			    MapSqlParameterSource inMap = new MapSqlParameterSource();   
			    inMap.addValue("i_item_code", dto.getItemCode());
			    logger.debug("SP itemcode: " + dto.getItemCode());
			    
			    inMap.addValue("i_item_type", dto.getType());
			    logger.debug("SP type: " + dto.getType());
			    
			    inMap.addValue("i_item_serial", dto.getImei());
			    logger.debug("i_item_serial: " + dto.getImei());
			    
			    inMap.addValue("i_batch_ref", dto.getBatchRef());
			    logger.debug("SP batch ref: " + dto.getBatchRef());
			    
			    inMap.addValue("i_old_status_id", dto.getOldStatus());
			    logger.debug("SP old status: " + dto.getOldStatus());
			    
			    inMap.addValue("i_new_status_id", dto.getStatus());
			    logger.debug("SP new status: " + dto.getStatus());
			    
			    inMap.addValue("i_location", dto.getLocation());
			    logger.debug("SP location: " + dto.getLocation());
			    
			    inMap.addValue("i_old_stock_pool", dto.getOldStockPool());
			    logger.debug("SP old stock pool: " + dto.getStockPool());
			    
			    inMap.addValue("i_new_stock_pool", dto.getStockPool());
			    logger.debug("SP new stock pool: " + dto.getStockPool());
			    
			    inMap.addValue("i_item_serial_inv_ccs", dto.getItemSerialReal());
			    logger.debug("SP i_item_serial_inv_ccs : " + dto.getItemSerialReal());
			    
			    inMap.addValue("i_remark", dto.getRemarks());			   
			    logger.debug("SP i_remark : " + dto.getRemarks());
		
			    SqlParameterSource in = inMap;
			    Map out = jdbcCall.execute(in);
		
			    int error_code = -1;
			    int retVal = -1;
			    String error_text = "";
		
			    if (((Integer) out.get("gnErrCode")) != null) {
					error_code = ((Integer) out.get("gnErrCode")).intValue();
					logger.info("OPS$CNM.pkg_stock_assign.update_stock_item_fe() output gnErrCode = "
						+ error_code);
			    }
		
			    if (((Integer) out.get("gnRetVal")) != null) {
					retVal = ((Integer) out.get("gnRetVal")).intValue();
					logger.info("OPS$CNM.pkg_stock_assign.update_stock_item_fe() output gnRetVal = "
						+ retVal);
			    }
		
			    if ((out.get("gnErrText")) != null) {
					error_text = out.get("gnErrText").toString();
					logger.info("OPS$CNM.pkg_stock_assign.update_stock_item_fe() output gnErrText = "
						+ error_text);
			    } else {
					error_text = null;
					logger.info("OPS$CNM.pkg_stock_assign.update_stock_item_fe() output gnErrText = "
						+ error_text);
			    }
			    String resultText = retVal +  "&"+ error_code + "&" + error_text;
			    return resultText ;

//Remove from RDM
/*		// define SQL string
		String SQL = "update bomweb_stock_inventory " +
				"set status_id = ?, " +
				"location = ?, " +
				"last_upd_by = ?, " +
				"last_upd_date = sysdate, " +
				"remarks = ?" +
				", stock_pool = ? " +
				"where item_serial = ? " +
				"and   item_code = ?";

		// update to table
		try {			
			return simpleJdbcTemplate.update(SQL, 
					dto.getStatus(), 
					dto.getLocation(),
					username,
					dto.getRemarks(),
					dto.getStockPool(),
					dto.getImei(), 
					dto.getItemCode()
					);

		} catch (Exception e) {
			logger.error("Exception caught in updateStockInventory()", e);
			throw new DAOException(e.getMessage(), e);
		}*/

	}
	
	private static String getStockInventoryListSQL 
		= "select imei_id from bomweb_stock_inventory " +
				"where item_code = ? and imei_id = ?";

	public List<String> getStockInventoryList(String itemCode, String imei)
			throws DAOException {

		logger.info("getStockInventoryList @ MobccsstockInDAO is called");
		List<String> itemList = new ArrayList<String>();

		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {

				return rs.getString("imei_id");
			}
		};

		try {
			logger.info("getStockInventoryList() @ StockInDAO: "
					+ getStockInventoryListSQL);

			itemList = simpleJdbcTemplate.query(getStockInventoryListSQL, mapper,
					itemCode, imei);
		} catch (BadSqlGrammarException bsge) {
			logger.info("BadSqlGrammarException in getStockInventoryList()");

			itemList = null;
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException in getStockInventoryList()");

			itemList = null;
		} catch (Exception e) {
			logger.info("Exception caught in getStockInventoryList(): ", e);

			throw new DAOException(e.getMessage(), e);
		}
		return itemList;
	}
	
	private static String getStockInDTOSQL 
	= "select item_type, item_desc " +
			"from bomweb_stock_catalog " +
			"where item_code = ?";
	
	public List<StockInDTO> getStockInDTO(String itemCode) throws DAOException{
		logger.info(" getStockResultDTO is called");
		List<StockInDTO> itemList = new ArrayList<StockInDTO>();
		
		ParameterizedRowMapper<StockInDTO> mapper = new ParameterizedRowMapper<StockInDTO>() {
			public StockInDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
		
				StockInDTO dto = new StockInDTO();
				
				dto.setType(rs.getString("item_type"));
				dto.setModel(rs.getString("item_desc"));
		
				return dto;
			}
		};
		
		try {
				logger.info("getStockResultDTO() @ StockInDAO: "
						+ getStockInDTOSQL);
				
				itemList = simpleJdbcTemplate.query(getStockInDTOSQL, mapper, itemCode);
			
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException in getStockResultDTO()");
		
			itemList = null;
		} catch (BadSqlGrammarException bsge) {
			logger.info(
					"BadSqlGrammarException in getStockResultDTO()",
					bsge);
		
			itemList = null;
		} catch (Exception e) {
			logger.info("Exception caught in getStockResultDTO():",
					e);
		
			throw new DAOException(e.getMessage(), e);
		}
		return itemList;
	}
	
	private static String getTypeNModelSQL 
		= "SELECT item_type, " 
			+"  item_code, " 
			+"  item_desc " 
			+"  ,mip_sim_type " 
			+"FROM bomweb_stock_catalog " 
			+"WHERE item_code = ?";
	
	public List<StockCatalogDTO> getTypeNModel(String itemCode) throws DAOException{
		logger.info(" getTypeNModel is called");
		List<StockCatalogDTO> itemList = new ArrayList<StockCatalogDTO>();
		
		ParameterizedRowMapper<StockCatalogDTO> mapper = new ParameterizedRowMapper<StockCatalogDTO>() {
			public StockCatalogDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
		
				StockCatalogDTO dto = new StockCatalogDTO();
				
				dto.setScItemType(rs.getString("item_type"));
				dto.setScItemCode(rs.getString("item_code"));
				dto.setScItemDesc(rs.getString("item_desc"));
				dto.setMipSimType(rs.getString("mip_sim_type"));
				
				return dto;
			}
		};
		
		try {
				logger.info("getTypeNModel() @ StockInDAO: "
						+ getTypeNModelSQL);
				
				itemList = simpleJdbcTemplate.query(getTypeNModelSQL, mapper, itemCode);
			
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException in getTypeNModel()");
		
			itemList = null;
		} catch (BadSqlGrammarException bsge) {
			logger.info(
					"BadSqlGrammarException in getTypeNModel()",
					bsge);
		
			itemList = null;
		} catch (Exception e) {
			logger.info("Exception caught in getTypeNModel():",
					e);
		
			throw new DAOException(e.getMessage(), e);
		}
		return itemList;
	}
	
	private static String getStockUpdateStatusListSQL = 
		"SELECT code_type, " 
		+"  code_id, " 
		+"  code_desc " 
		+"FROM bomweb_code_lkup " 
		+"WHERE code_type = 'STOCK_STS' " 
		+"AND code_id    IN ('02', '18', '21', '22', '23')";

	public List<CodeLkupDTO> getStockUpdateStatusList() throws DAOException {

		logger.info("getStockUpdateStatusList @ StockInDAO is called");
		List<CodeLkupDTO> itemList = new ArrayList<CodeLkupDTO>();

		ParameterizedRowMapper<CodeLkupDTO> mapper = new ParameterizedRowMapper<CodeLkupDTO>() {
			public CodeLkupDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				
				CodeLkupDTO tempDto = new CodeLkupDTO();
				tempDto.setCodeDesc(rs.getString("code_desc"));
				tempDto.setCodeId(rs.getString("code_id"));
				tempDto.setCodeType(rs.getString("code_type"));
				return tempDto;
			}
		};

		try {
			logger.info("getStockUpdateStatusList() @ StockInDAO: "
					+ getStockUpdateStatusListSQL);

			itemList = simpleJdbcTemplate.query(getStockUpdateStatusListSQL, mapper);
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException in getStockUpdateStatusList()");

			itemList = null;
		} catch (Exception e) {
			logger.info("Exception caught in getStockUpdateStatusList():", e);

			throw new DAOException(e.getMessage(), e);
		}
		return itemList;
	}
	
	// add by Joyce 20120321
    // Stock In (GIFT-MISC & GIFT-PC)
    public String[] insertFreeGiftItem(StockInDTO dto) throws DAOException {

		logger.info("insertFreeGiftItem is called");
	
		try {
	
		    SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
			    .withSchemaName("OPS$CNM")
			    .withCatalogName("pkg_stock_assign")
			    .withProcedureName("insert_free_gift_item");
		    jdbcCall.setAccessCallParameterMetaData(false);
		    jdbcCall.declareParameters(
		    		new SqlParameter("i_item_code", Types.VARCHAR), 
		    		new SqlParameter("i_item_type", Types.VARCHAR), 
		    		new SqlParameter("i_item_qty", Types.INTEGER), 
		    		new SqlParameter("i_status_id", Types.VARCHAR), 
		    		new SqlParameter("i_location", Types.VARCHAR), 
		    		new SqlParameter("i_batch_ref", Types.VARCHAR), 
		    		new SqlParameter("i_stock_pool", Types.VARCHAR), 
		    		new SqlOutParameter("o_start_serial", Types.VARCHAR), 
		    		new SqlOutParameter("o_end_serial", Types.VARCHAR), 
		    		new SqlOutParameter("gnRetVal", Types.INTEGER), 
		    		new SqlOutParameter("gnErrCode", Types.INTEGER), 
		    		new SqlOutParameter("gnErrText", Types.VARCHAR));
	
		    MapSqlParameterSource inMap = new MapSqlParameterSource();
		    inMap.addValue("i_item_code", dto.getItemCode());
		    logger.debug("SP itemcode: " + dto.getItemCode());
		    inMap.addValue("i_item_type", dto.getType());
		    logger.debug("SP type: " + dto.getType());
		    inMap.addValue("i_item_qty", Integer.parseInt(dto.getQuantity()));
		    logger.debug("SP quantity: " + dto.getQuantity());
		    inMap.addValue("i_status_id", dto.getStatus());
		    logger.debug("SP status: " + dto.getStatus());
		    inMap.addValue("i_location", dto.getLocation());
		    logger.debug("SP location: " + dto.getLocation());
		    inMap.addValue("i_batch_ref", dto.getBatchRef());
		    logger.debug("SP batch ref: " + dto.getBatchRef());
		    inMap.addValue("i_stock_pool", dto.getStockPool());
		    logger.debug("SP stock pool: " + dto.getStockPool());
	
		    SqlParameterSource in = inMap;
		    Map out = jdbcCall.execute(in);
	
		    int error_code = -1;
		    int retVal = -1;
		    String error_text = "";
	
		    if (((Integer) out.get("gnErrCode")) != null) {
				error_code = ((Integer) out.get("gnErrCode")).intValue();
				logger.info("OPS$CNM.pkg_stock_assign.insert_free_gift_item() output gnErrCode = "
					+ error_code);
		    }
	
		    if (((Integer) out.get("gnRetVal")) != null) {
				retVal = ((Integer) out.get("gnRetVal")).intValue();
				logger.info("OPS$CNM.pkg_stock_assign.insert_free_gift_item() output gnRetVal = "
					+ retVal);
		    }
	
		    if ((out.get("gnErrText")) != null) {
				error_text = out.get("gnErrText").toString();
				logger.info("OPS$CNM.pkg_stock_assign.insert_free_gift_item() output gnErrText = "
					+ error_text);
		    } else {
				error_text = null;
				logger.info("OPS$CNM.pkg_stock_assign.insert_free_gift_item() output gnErrText = "
					+ error_text);
		    }
		    
		    String startSerial = "";
		    
		    if ((out.get("o_start_serial")) != null) {
		    	startSerial = out.get("o_start_serial").toString();
				logger.info("OPS$CNM.pkg_stock_assign.insert_free_gift_item() output o_start_serial = "
					+ startSerial);
		    } else {
		    	startSerial = null;
				logger.info("OPS$CNM.pkg_stock_assign.insert_free_gift_item() output o_start_serial = "
					+ startSerial);
		    }
		    
		    String endSerial = "";
		    
		    if ((out.get("o_end_serial")) != null) {
		    	endSerial = out.get("o_end_serial").toString();
				logger.info("OPS$CNM.pkg_stock_assign.insert_free_gift_item() output o_end_serial = "
					+ endSerial);
		    } else {
		    	endSerial = null;
				logger.info("OPS$CNM.pkg_stock_assign.insert_free_gift_item() output o_end_serial = "
					+ endSerial);
		    }
	
		    return new String[]{startSerial, endSerial, ""+retVal, ""+error_code, error_text};
	
		} catch (Exception e) {
		    logger.error("Exception caught in insertFreeGiftItem()", e);
		    throw new DAOException(e.getMessage(), e);
		}
    }
    
    /*
     * getItemSerialLengthByTypeId():
     * Retrieve the length of item serial according to the type (01 handset, 02 sim, ...)
     * */
    private static String getItemSerialLengthByTypeIdSQL 
	    = "SELECT TYPE.code_id AS id, " 
    		+"  TYPE.code_desc    AS stock_type, " 
    		+"  LENGTH.code_desc  AS stock_type_item_serial_length " 
    		+"FROM bomweb_code_lkup TYPE, " 
    		+"  bomweb_code_lkup LENGTH " 
    		+"WHERE TYPE.code_type = 'STOCK_TYPE' " 
    		+"AND LENGTH.code_type = 'STOCK_TYPE_LENGTH' " 
    		+"AND TYPE.code_id     = LENGTH.code_id " 
    		+"AND TYPE.code_id     = ?";

	public int getItemSerialLengthByTypeId(String typeId)
	    throws DAOException {
		
		logger.info("getItemSerialLengthByTypeId @ StockDAO is called");
		List<String> itemList = new ArrayList<String>();
	
		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
		    public String mapRow(ResultSet rs, int rowNum)
			    throws SQLException {
		    	
		    	return rs.getString("stock_type_item_serial_length");
		    }
		};
	
		try {
		    logger.info("getItemSerialLengthByTypeId() @ StockDAO: " + getItemSerialLengthByTypeIdSQL);
	
		    itemList = simpleJdbcTemplate.query(getItemSerialLengthByTypeIdSQL, mapper, typeId);
		} catch (EmptyResultDataAccessException erdae) {
		    logger.info("EmptyResultDataAccessException in getItemSerialLengthByTypeId()");
	
		    itemList = null;
		} catch (Exception e) {
		    logger.info("Exception caught in getItemSerialLengthByTypeId():", e);
	
		    throw new DAOException(e.getMessage(), e);
		}
		
		if (itemList != null && itemList.size() > 0) {
			if (StringUtils.isNumeric(itemList.get(0))) {
				return Integer.parseInt(itemList.get(0)); 
			} else {
				return 0;
			}
		} else {
			return 0;
		}
    }

	private static String checkValidStaffSQL 
	= "SELECT staff_id, centre_cd, team_cd FROM bomweb_sales_assignment " +
	  "WHERE staff_id = :staffId " +
	  "AND centre_cd = :storeCd " +
	  "AND team_cd = decode(nvl(:teamCode, ''), '', team_cd, :teamCode) " +
	  "AND end_date is null";

	public List<StockInDTO> checkValidStaff(String staffId, String storeCd, String teamCd)
			throws DAOException {
	
		logger.info("checkValidStaff @ StockInDAO is called");
		List<StockInDTO> itemList = new ArrayList<StockInDTO>();
	
		ParameterizedRowMapper<StockInDTO> mapper = new ParameterizedRowMapper<StockInDTO>() {
			public StockInDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				StockInDTO dto = new StockInDTO();
				dto.setStaffId("staffId");
				dto.setStoreCode("centre_cd");
				dto.setTeamCode("team_cd");
				return dto;
			}
		};
	
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("staffId", staffId);
		params.addValue("storeCd", storeCd);
		params.addValue("teamCode", teamCd);
		
		try {
			logger.info("checkValidStaff() @ StockInDAO: "
					+ checkValidStaffSQL);
	
			itemList = simpleJdbcTemplate.query(checkValidStaffSQL, mapper, params);
		} catch (BadSqlGrammarException bsge) {
			logger.info("BadSqlGrammarException in checkValidStaff()");
	
			itemList = null;
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException in checkValidStaff()");
	
			itemList = null;
		} catch (Exception e) {
			logger.info("Exception caught in checkValidStaff(): ", e);
	
			throw new DAOException(e.getMessage(), e);
		}
		return itemList;
	}
	
	public int updateDsRWHStockInventory(StockInDTO originalDto, StockInDTO dto, String username)
			throws DAOException {
		logger.info("updateDsRWHStockInventory @ StockInDAO is called");
		
		if (originalDto == null || dto == null) {
			return -1;
		}
		
		int result = insertDsRWHStockInventoryHistory(originalDto, dto, username);
		if (result <= 0) {
			return -1;
		}
		
		String sql = "update bomweb_stock_inventory " +
				"set status_id = :statusID, " +
				"location = :location, " +
				"stock_pool = :stockPool, " +
				"batch_ref = :batchRef, " +
				"event_cd = :eventCd, " +
				"store_cd = :storeCd, " +
				"team_cd = :teamCd, " +
				"book_out_date = to_date(:bookOutDate, 'dd/mm/yyyy'), " +
				"staff_id = :staffId, " +
				"remarks = :remarks, " +
				"last_upd_date = sysdate, " +
				"last_upd_by = :username " +
				"where item_code = :itemCode " +
				"and item_serial = :imei ";

		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("itemCode", dto.getItemCode());
		params.addValue("imei", dto.getImei());
		params.addValue("statusID", dto.getStatus());
		params.addValue("location", dto.getLocation());
		params.addValue("stockPool", dto.getStockPool());
		params.addValue("batchRef", dto.getBatchRef());
		params.addValue("eventCd", dto.getEventCode());
		params.addValue("storeCd", dto.getStoreCode());
		params.addValue("teamCd", dto.getTeamCode());
		params.addValue("bookOutDate", dto.getBookOutDate());
		params.addValue("staffId", dto.getStaffId());
		params.addValue("remarks", dto.getRemarks());
		params.addValue("username", username);
		
		try {
			logger.info("updateDsRWHStockInventory() @ StockInDAO: "
				    + sql);
			return simpleJdbcTemplate.update(sql, params);

		} catch (Exception e) {
			logger.error("Exception caught in updateDsRWHStockInventory():", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public int insertDsRWHStockInventoryHistory(StockInDTO originalDto, StockInDTO dto, String username)
			throws DAOException {
		logger.info("insertDsRWHStockInventoryHistory @ StockInDAO is called");
		
		if (originalDto == null || dto == null) {
			return -1;
		}
		
		String sql = "insert into bomweb_stock_inventory_hist " +
				" (item_code, item_serial, status_id_from, status_id_to, remarks, " +
				" location_from, location_to, " +
				"  create_by, create_date, batch_ref_from, batch_ref_to, event_cd, store_cd, " +
				"  team_cd, book_out_date, staff_id) values (" +
				":itemCode, " +
				":imei, " +
				":statusIDFrom, " +
				":statusIDTo, " +
				":remarks, " +
				":locationFrom, " +
				":locationTo, " +
				":createBy, " +
				"sysdate, " +
				":batchRefFrom, " +
				":batchRefTo, " +
				":eventCode, " +
				":storeCode, " +
				":teamCode, " +
				"to_date(:bookOutDate, 'dd/mm/yyyy'), " +
				":staffID) ";

		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("itemCode", dto.getItemCode());
		params.addValue("imei", dto.getImei());
		params.addValue("statusIDFrom", originalDto.getStatus());
		params.addValue("statusIDTo", dto.getStatus());
		params.addValue("locationFrom", originalDto.getLocation());
		params.addValue("locationTo", dto.getLocation());
		params.addValue("remarks", originalDto.getRemarks());
		params.addValue("createBy", username);
		params.addValue("batchRefFrom", originalDto.getBatchRef());
		params.addValue("batchRefTo", dto.getBatchRef());
		params.addValue("eventCode", originalDto.getEventCode());
		params.addValue("storeCode", originalDto.getStoreCode());
		params.addValue("teamCode", originalDto.getTeamCode());
		params.addValue("bookOutDate", originalDto.getBookOutDate());
		params.addValue("staffID", originalDto.getStaffId());
		
		try {
			logger.info("insertDsRWHStockInventoryHistory() @ StockInDAO: "
				    + sql);
			return simpleJdbcTemplate.update(sql, params);

		} catch (Exception e) {
			logger.error("Exception caught in insertDsRWHStockInventoryHistory():", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
}
