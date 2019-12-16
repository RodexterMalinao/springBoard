package com.bomwebportal.mob.ds.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.bomwebportal.dao.BaseDAO;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.mob.ccs.dto.StockManualDetailDTO;
import com.bomwebportal.mob.ccs.dto.ui.StockManualAssgnUI;

public class MobDsStockManualDAO extends BaseDAO {
    
    private static String updateOrderItemSQL = 
    		"UPDATE bomweb_stock_assgn " +
    		"SET item_code = ? " +
    		"WHERE order_id = ? " +
    		"AND item_code = ? " +
    		"AND nvl(status_id, 0) <> 24";
    
    public int updateOrderItem(String orderId, String newItemCode, String oldItemCode) throws DAOException {
    	logger.info("updateOrderItem @ MobDsStockManualDAO is called");
    	try {
    	    logger.info("updateOrderItemSQL() @ MobDsStockManualDAO: "
    		    + updateOrderItemSQL);

    	    return simpleJdbcTemplate.update(updateOrderItemSQL,
    	    		newItemCode, orderId, oldItemCode);
    	} catch (Exception e) {
    	    logger.error("Exception caught in updateOrderItem():", e);

    	    throw new DAOException(e.getMessage(), e);
    	}
    }
    
    private static String manualDSStockAssgnSQL = 
    		"UPDATE bomweb_stock_assgn " +
    		"SET item_code = :newItemCode, " +
    		"item_serial = :itemSerial, " +
    		"status_id = :status," +
    		"last_upd_date = sysdate, " +
    		"last_upd_by = :username ";
    		
    public int manualDSStockAssgn(StockManualAssgnUI dto, StockManualAssgnUI oDto, String orderId, String username, String action) throws DAOException {
    	logger.info("manualDSStockAssgn @ MobDsStockManualDAO is called");
    	try {
    	    logger.info("manualDSStockAssgnSQL() @ MobDsStockManualDAO: "
    		    + manualDSStockAssgnSQL);
    	    //change handset color (item_code) -> deassign old item -> assign new item

    	    //Insert assign history
    	    int histResult = this.manualDSStockAssgnHist(dto, oDto, orderId, username, action);
    	    if (histResult < 0) {
    	    	return histResult;
    	    }
    	    
    	    //determine assign / deassign (null)
    	    if (dto != null && dto.getItemSerial() != null) {
        	    dto.setStatus(this.getStatusChange(orderId, dto.getStatus(), dto.getItemCode(), action));
    	    }
    	    if (oDto != null && oDto.getItemSerial() != null) {
    	    	oDto.setStatus(this.getStatusChange(orderId, oDto.getStatus(), oDto.getItemCode(), action));
    	    }
    	    
    	    /*****deassign original stock*****/
    	    if (oDto.getItemSerial() != null) {
        	    
    	    	//insert inventory history first
    	    	int oInventoryHist = this.insertDSStockInventoryHistory(oDto, username);
        	    if (oInventoryHist < 0) {
        	    	return oInventoryHist;
        	    }
        	    
        	    //deassign
    	    	int deassignResult = this.manualDSStockInventory(oDto, username);
        	    if (deassignResult < 0) {
        	    	return deassignResult;
        	    }
    	    }
    	    
    	    /*****assign new stock*****/
    	    if (dto.getItemSerial() != null) {
    	    	
    	    	//insert inventory history first
        	    int inventoryHist = this.insertDSStockInventoryHistory(dto, username);
        	    if (inventoryHist < 0) {
        	    	return inventoryHist;
        	    }
        	    
        	    //assign
        	    int inventoryResult = this.manualDSStockInventory(dto, username);
        	    if (inventoryResult < 0) {
        	    	return inventoryResult;
        	    }
    	    }
    	    
    	    StringBuilder sql = new StringBuilder(manualDSStockAssgnSQL);
    		if (!"DOA".equalsIgnoreCase(action) && dto.getItemSerial() != null) {
    			//assign new item -> modify AO indicator
    			sql.append(", ao_ind = decode(nvl(ao_ind, '#'),'#',null,'Y','R',ao_ind) ");
    		}
    		sql.append("WHERE order_id = :orderId " +
		    		"AND item_code = :itemCode " +
		    		"AND nvl(status_id, 0) <> 24");
    	    
    	    MapSqlParameterSource params = new MapSqlParameterSource();
    	    params.addValue("newItemCode", dto.getItemCode());
    	    params.addValue("itemSerial", dto.getItemSerial());
    	    params.addValue("status", dto.getStatus());
    	    params.addValue("username", username);
    	    params.addValue("orderId", dto.getOrderId());
    		params.addValue("itemCode", oDto.getItemCode());
    		
    	    return simpleJdbcTemplate.update(sql.toString(),
    	    		params);
    	} catch (Exception e) {
    	    logger.error("Exception caught in manualDSStockAssgn():", e);

    	    throw new DAOException(e.getMessage(), e);
    	}
    }
    
    private static String manualDSStockAssgnHistSQL = 
    		"INSERT into bomweb_stock_assgn_hist " +
    		"  (action, order_id, item_code, " +
    		"    b_item_serial, b_status_id, " +
    		"    a_item_serial, a_status_id, " +
    		"    create_by, create_date, hist_seq) " +
    		"VALUES (" +
    		"  decode(nvl(:bItemStatus,'#'),'#','INSERT','UPDATE'), " +
    		"  :orderId, :itemCode, " +
    		"  :bItemSerial, :bItemStatus, " +
    		"  :aItemSerial, nvl(:aItemStatus,'19'), " +
    		"  :username, sysdate, " +
    		"  (select max(hist_seq)+1 " +
    		"    from bomweb_stock_assgn_hist)) ";
    
    public int manualDSStockAssgnHist(StockManualAssgnUI dto, StockManualAssgnUI oDto, String orderId, String username, String action) throws DAOException {
    	logger.info("manualDSStockAssgnHist @ MobDsStockManualDAO is called");
    	try {
    	    logger.info("manualDSStockAssgnHist() @ MobDsStockManualDAO: "
    		    + manualDSStockAssgnHistSQL);
    	    MapSqlParameterSource params = new MapSqlParameterSource();
    	    if (oDto != null && oDto.getItemSerial() != null) {
    	    	params.addValue("orderId", oDto.getOrderId());
        	    params.addValue("itemCode", oDto.getItemCode());
        	    params.addValue("bItemSerial", oDto.getItemSerial());
        	    params.addValue("bItemStatus", oDto.getStatus());
    	    } else {
    	    	params.addValue("bItemSerial", oDto.getSelItemSerial());
        	    params.addValue("bItemStatus", oDto.getStatus());
    	    }
    	    if (dto != null && dto.getItemSerial() != null) {
    	    	params.addValue("orderId", dto.getOrderId());
        	    params.addValue("itemCode", dto.getItemCode());
        	    params.addValue("aItemSerial", dto.getItemSerial());
        	    params.addValue("aItemStatus", dto.getStatus());
    	    } else {
    	    	params.addValue("aItemSerial", oDto.getItemSerial());
        	    params.addValue("aItemStatus", this.getStatusChange(orderId, oDto.getStatus(), oDto.getItemCode(), action));
    	    }
    	    params.addValue("username", username);
    	    
    	    return simpleJdbcTemplate.update(manualDSStockAssgnHistSQL,
    	    		params);
    	} catch (Exception e) {
    	    logger.error("Exception caught in manualDSStockAssgnHist():", e);

    	    throw new DAOException(e.getMessage(), e);
    	}
    }
    
    private static String manualDSStockInventorySQL = 
    		"UPDATE bomweb_stock_inventory " +
    		"SET status_id = ?, " +
    		"book_out_date = sysdate, " +
//    		"staff_id = ?, " +
    		"last_upd_by = ?, " +
    		"last_upd_date = sysdate " +
    		"WHERE item_code = ? " +
    		"AND item_serial = ? ";
    
    public int manualDSStockInventory(StockManualAssgnUI dto, String username) throws DAOException {
    	logger.info("manualDSStockInventory @ MobDsStockManualDAO is called");
    	try {
    	    logger.info("manualDSStockInventory() @ MobDsStockManualDAO: "
    		    + manualDSStockInventorySQL);
    	    
    	    
    	    return simpleJdbcTemplate.update(manualDSStockInventorySQL,
    	    		dto.getStatus(), username, dto.getItemCode(), dto.getItemSerial());
    	} catch (Exception e) {
    	    logger.error("Exception caught in manualDSStockInventory():", e);

    	    throw new DAOException(e.getMessage(), e);
    	}
    }
    
    private static String insertDSStockInventoryHistorySQL = 
    		"INSERT INTO bomweb_stock_inventory_hist " +
    				"(item_code, item_serial, status_id_from, remarks, " +
    				"batch_ref_from, event_cd, store_cd, " +
    				"team_cd, book_out_date, staff_id) " +
    				"  (select item_code, item_serial, status_id, remarks, " +
    				"  batch_ref, event_cd, store_cd, " +
    				"  team_cd, book_out_date, staff_id " +
    				"  FROM bomweb_stock_inventory " +
    				"  WHERE item_code = ? " +
    				"  AND item_serial = ?)";
    
    private static String updateDSStockInventoryHistorySQL = 
    		"UPDATE bomweb_stock_inventory_hist " +
    		"SET status_id_to = :statusTo, " +
    		"batch_ref_to = 'SBMOB', " +
    		"create_by = :username, " +
    		"create_date = sysdate " +
    		"WHERE item_code = :itemCode " +
    		"AND item_serial = :itemSerial " +
    		"AND create_by is null " +
    		"AND trunc(create_date) = trunc(sysdate) " +
    		"AND batch_ref_to is null";
    
    public int insertDSStockInventoryHistory(StockManualAssgnUI dto, String username)
			throws DAOException {
		logger.info("insertDSStockInventoryHistory @ MobDsStockDAO is called");
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("statusTo", dto.getStatus());
		params.addValue("username", username);
		params.addValue("itemCode", dto.getItemCode());
		params.addValue("itemSerial", dto.getItemSerial());
		
		try {
			logger.info("insertDSStockInventoryHistory() @ MobDsStockDAO: "
				    + insertDSStockInventoryHistorySQL);
			int temp = simpleJdbcTemplate.update(insertDSStockInventoryHistorySQL, dto.getItemCode(), dto.getItemSerial());
			if (temp < 0) {
				return temp;
			}

			logger.info("insertDSStockInventoryHistory() @ MobDsStockDAO: "
				    + updateDSStockInventoryHistorySQL);
			return simpleJdbcTemplate.update(updateDSStockInventoryHistorySQL, params);
		} catch (Exception e) {
			logger.error("Exception caught in insertDSStockInventoryHistory():", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
    
    private static String getAssignedItemSerialStatusSQL = 
    		"SELECT distinct item_serial, status_id from bomweb_stock_assgn " +
    		"WHERE order_id = ? " +
    		"AND item_code = ? " +
    		"AND nvl(status_id, 0) <> '24'";
    
    public StockManualAssgnUI getAssignedItemSerialStatus(String orderId, String itemCode) throws DAOException {
    	logger.info("getAssignedItemSerialStatus @ MobDsStockManualDAO is called");
    	List<StockManualAssgnUI> result = new ArrayList<StockManualAssgnUI>();
    	
    	try {
    	    logger.info("getAssignedItemSerialStatus() @ MobDsStockManualDAO: "
    		    + getAssignedItemSerialStatusSQL);
    	    
    	    ParameterizedRowMapper<StockManualAssgnUI> mapper = new ParameterizedRowMapper<StockManualAssgnUI>() {
    		    public StockManualAssgnUI mapRow(ResultSet rs, int rowNum)
    			    throws SQLException {

    		    	StockManualAssgnUI tempDto = new StockManualAssgnUI();
    		    	tempDto.setItemSerial(rs.getString("item_serial"));
    		    	tempDto.setStatus(rs.getString("status_id"));

    		    return tempDto;
    		    }
    		};
    	    
    		result = simpleJdbcTemplate.query(getAssignedItemSerialStatusSQL, mapper, orderId, itemCode);
    	}  catch (EmptyResultDataAccessException erdae) {
    	    logger.error("EmptyResultDataAccessException in getAssignedItemSerialStatus()");
    	    result = null;
    	} catch (Exception e) {
    	    logger.error("Exception caught in getAssignedItemSerialStatus():", e);

    	    throw new DAOException(e.getMessage(), e);
    	}
    	if (result == null || result.isEmpty()) {
    		return null;
    	}
    	return result.get(0);
    }
    
    public String getItemType(String itemCode) throws DAOException {
    	logger.info("getItemType @ MobDsStockManualDAO is called");
    	String sql = 
    			"SELECT distinct item_type from bomweb_stock_catalog " +
    			"WHERE item_code = ?";
    	
    	ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
		    public String mapRow(ResultSet rs, int rowNum)
			    throws SQLException {
		    return rs.getString("item_type");
		    }
		};
		try {
			logger.info("getItemType() @ MobDsStockManualDAO: "
	    		    + sql);
			return simpleJdbcTemplate.query(sql, mapper, itemCode).get(0);
		} catch (Exception e) {
    	    logger.error("Exception caught in getItemType():", e);
    	    throw new DAOException(e.getMessage(), e);
    	}
    }
    
    private String getOcid(String orderId) throws DAOException {
    	logger.info("getOcid @ MobDsStockManualDAO is called");
    	String sql = 
    			"SELECT ocid from bomweb_order " +
    			"WHERE order_id = ?";
    	
    	List<String> result = new ArrayList<String>();
    	
    	ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
		    public String mapRow(ResultSet rs, int rowNum)
			    throws SQLException {
		    return rs.getString("ocid");
		    }
		};
		try {
			logger.info("getOcid() @ MobDsStockManualDAO: "
	    		    + sql);
			
			result = simpleJdbcTemplate.query(sql, mapper, orderId);
			
			if (result == null || result.size() == 0) {
				return null;
			}
			return result.get(0);
		} catch (Exception e) {
    	    logger.error("Exception caught in getOcid():", e);
    	    throw new DAOException(e.getMessage(), e);
    	}
    }
    
    public String getStatusChange(String orderId, String status, String itemCode, String action) throws DAOException {
    	String result = "";
		if (status == null || "27".equals(status) || "28".equals(status)) {
			//SOS / RSS to Reserve (Assign)
			if (this.getOcid(orderId) == null) {
				result = "19";
			} else {
				//submitted to bom -> status = sold
				result = "20";
			}
		} else/* if ("19".equals(status) || "20".equals(status))*/ {
			if ("DOA".equalsIgnoreCase(action)) {
				//Assign -> DRN
				result = "31";
			} else {
				//from Reserve back to SOS (Deassign)
    			result = "27";
			}
		} /*else {
			return status;
		}*/
		/*else if ("20".equals(status)) {
			//from sold to Abandon (SIM) / Faulty (Others)
			if ("02".equals(this.getItemType(itemCode))) {
				//SIM
				result = "30";
			} else {
				result = "26";
			}
		}*/
	    return result;
    }
    
    private String getPreviousItemSingleItemCodeSql = 
    		"SELECT bsah.order_id, bsah.item_code, bsah.b_item_serial old_item_serial " +
    		"FROM bomweb_stock_assgn_hist bsah, " +
    		"(select max(create_date) create_date, order_id, item_code " +
    		"    from bomweb_stock_assgn_hist WHERE (a_status_id = '27' or a_status_id = '31') " +
    		"    group by order_id, item_code ORDER BY CREATE_DATE desc) hist " +
    		"WHERE (a_status_id = '27' or a_status_id = '31') " +
    		"AND bsah.b_item_serial is not null " +
    		"AND bsah.order_id = :orderId " +
    		"AND bsah.order_id = hist.order_id " +
    		"AND bsah.item_code = hist.item_code " +
    		"AND bsah.create_date = hist.create_date " +
    		"AND bsah.item_code = :itemCode " +
    		"AND ROWNUM = 1";
    
    public StockManualDetailDTO getPreviousItemSingleItemCode(String orderId, String itemCode) throws DAOException {
    	logger.info("getPreviousItemSingleItemCode @ MobDsStockManualDAO is called");
    	List<StockManualDetailDTO> temp = new ArrayList<StockManualDetailDTO>();

    	ParameterizedRowMapper<StockManualDetailDTO> mapper = new ParameterizedRowMapper<StockManualDetailDTO>() {
    	    public StockManualDetailDTO mapRow(ResultSet rs, int rowNum)
    		    throws SQLException {
    	    	StockManualDetailDTO dto = new StockManualDetailDTO();
    	    	dto.setOldItemCode(rs.getString("item_code"));
    	    	dto.setOldSerialNum(rs.getString("old_item_serial"));
    		return dto;
    	    }
    	};
    	
    	MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("orderId", orderId);
		params.addValue("itemCode", itemCode);
    	
    	try {
    	    logger.info("getPreviousItemSingleItemCode() @ MobDsStockManualDAO: "
    		    + getPreviousItemSingleItemCodeSql);

    	    temp = simpleJdbcTemplate.query(getPreviousItemSingleItemCodeSql,
    		    mapper, params);
    	} catch (EmptyResultDataAccessException erdae) {
    	    logger.error("EmptyResultDataAccessException in getPreviousItemSingleItemCode()");

    	    temp = null;
    	} catch (Exception e) {
    	    logger.error("Exception caught in getPreviousItemSingleItemCode():", e);

    	    throw new DAOException(e.getMessage(), e);
    	}
    	if (temp == null || temp.isEmpty()) {
    		return null;
    	}
    	return temp.get(0);
    }
    
    private String getPreviousItemSql = 
    		"SELECT bsah.order_id, bsah.item_code, bsah.b_item_serial old_item_serial " +
    		"FROM bomweb_stock_assgn_hist bsah, " +
    		"(select max(create_date) create_date, order_id, item_code " +
    		"    from bomweb_stock_assgn_hist WHERE " +
    		"    (a_status_id = '27' or a_status_id = '31') " +
    		"    group by order_id, item_code ORDER BY CREATE_DATE desc) hist " +
    		"WHERE (a_status_id = '27' or a_status_id = '31') " +
    		"AND bsah.b_item_serial is not null " +
    		"AND bsah.order_id = :orderId " +
    		"AND bsah.order_id = hist.order_id " +
    		"AND bsah.item_code = hist.item_code " +
    		"AND bsah.create_date = hist.create_date " +
    		"AND bsah.item_code in ( " +
    		"    select distinct bsc.item_code" +
    		"    from W_DISPLAY_LKUP DL, " +
    		"    W_ITEM_DTL_HS IDH, " +
    		"    W_ITEM_PRODUCT_POS_ASSGN IPPA, " +
    		"    bomweb_stock_catalog bsc, " +
    		"    (select * " +
    		"        from W_HANDSET_NS_PRICE " +
    		"        where TRUNC(to_date(sysdate, 'dd/MM/yyyy')) between EFF_START_DATE " +
    		"        and TRUNC(NVL(EFF_END_DATE, sysdate))) HNP " +
    		"where IDH.COLOR_ID = DL.ID " +
    		"and DL.TYPE = 'COLOR' " +
    		"and DL.LOCALE = 'en' " +
    		"and IDH.ID = IPPA.ITEM_ID " +
    		"and IDH.MODEL_ID = (select distinct model_id from W_ITEM_DTL_HS where id in " +
    		"(select item_id from  W_ITEM_PRODUCT_POS_ASSGN  where pos_item_cd = :itemCode)) " +
    		"and IPPA.POS_ITEM_CD = HNP.POS_ITEM_CD(+) " +
    		"and IPPA.POS_ITEM_CD = bsc.item_code) " +
    		"AND ROWNUM = 1";
    
    public StockManualDetailDTO getPreviousItem(String orderId, String itemCode) throws DAOException {
    	logger.info("getPreviousItem @ MobDsStockManualDAO is called");
    	List<StockManualDetailDTO> temp = new ArrayList<StockManualDetailDTO>();

    	ParameterizedRowMapper<StockManualDetailDTO> mapper = new ParameterizedRowMapper<StockManualDetailDTO>() {
    	    public StockManualDetailDTO mapRow(ResultSet rs, int rowNum)
    		    throws SQLException {
    	    	StockManualDetailDTO dto = new StockManualDetailDTO();
    	    	dto.setOldItemCode(rs.getString("item_code"));
    	    	dto.setOldSerialNum(rs.getString("old_item_serial"));
    		return dto;
    	    }
    	};
    	
    	MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("orderId", orderId);
		params.addValue("itemCode", itemCode);
    	
    	try {
    	    logger.info("getPreviousItem() @ MobDsStockManualDAO: "
    		    + getPreviousItemSql);

    	    temp = simpleJdbcTemplate.query(getPreviousItemSql,
    		    mapper, params);
    	} catch (EmptyResultDataAccessException erdae) {
    	    logger.error("EmptyResultDataAccessException in getPreviousItem()");

    	    temp = null;
    	} catch (Exception e) {
    	    logger.error("Exception caught in getPreviousItem():", e);

    	    throw new DAOException(e.getMessage(), e);
    	}
    	if (temp == null || temp.isEmpty()) {
    		return null;
    	}
    	return temp.get(0);
    }
}
